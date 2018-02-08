package com.born.fcs.pm.biz.service.projectcreditcondition;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.am.ws.enums.AssetStatusEnum;
import com.born.fcs.am.ws.info.pledgeasset.PledgeAssetInfo;
import com.born.fcs.am.ws.info.pledgetypeattribute.PledgeTypeAttributeInfo;
import com.born.fcs.am.ws.order.pledgeasset.AssetRelationProjectBindOrder;
import com.born.fcs.am.ws.order.pledgeasset.AssetStatusOrder;
import com.born.fcs.am.ws.order.pledgetypeattribute.PledgeTypeAttributeOrder;
import com.born.fcs.am.ws.service.pledgeasset.PledgeAssetService;
import com.born.fcs.am.ws.service.pledgetypeattribute.PledgeTypeAttributeService;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.daointerface.ProjectCreditAssetAttachmentDAO;
import com.born.fcs.pm.dal.daointerface.ProjectCreditMarginDAO;
import com.born.fcs.pm.dal.dataobject.FCreditConditionConfirmDO;
import com.born.fcs.pm.dal.dataobject.FCreditConditionConfirmItemDO;
import com.born.fcs.pm.dal.dataobject.FProjectContractItemDO;
import com.born.fcs.pm.dal.dataobject.ProjectCreditAssetAttachmentDO;
import com.born.fcs.pm.dal.dataobject.ProjectCreditConditionDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CheckStatusEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.ContractStatusEnum;
import com.born.fcs.pm.ws.enums.CreditCheckStatusEnum;
import com.born.fcs.pm.ws.enums.CreditConditionTypeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.enums.managerbchange.ChangeManagerbPhaseEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

@Service("projectCreditConditionProcessService")
public class ProjectCreditConditionProcessServiceImpl extends BaseProcessService {
	@Autowired
	protected ProjectRelatedUserService projectRelatedUserService;
	@Autowired
	PledgeTypeAttributeService pledgeTypeAttributeWebService;
	@Autowired
	ProjectCreditAssetAttachmentDAO projectCreditAssetAttachmentDAO;
	@Autowired
	ProjectCreditMarginDAO projectCreditMarginDAO;
	@Autowired
	PledgeAssetService pledgeAssetServiceClient;
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			long formId = order.getFormInfo().getFormId();
			//授信条件项目信息
			FCreditConditionConfirmDO project = FCreditConditionConfirmDAO.findByFormId(formId);
			if (project == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "授信条件项目信息未找到");
			}
			//自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
				.getAttribute("startOrder");
			if (startOrder != null) {
				startOrder.setCustomTaskName(project.getProjectName() + "-授信条件");
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("授信落实流程启动前置处理出错 ： {}", e);
		}
		return result;
	}
	
	/**
	 * 提交表单的时更新授信条件项目信息
	 * @param order
	 * @return
	 * @see com.born.fcs.pm.biz.service.common.WorkflowExtProcessService#startBeforeProcess(com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder)
	 */
	@Override
	public void startAfterProcess(FormInfo form, WorkflowResult workflowResult) {
		
		long formId = form.getFormId();
		
		//授信条件项目信息
		FCreditConditionConfirmDO project = FCreditConditionConfirmDAO.findByFormId(formId);
		if (project == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "授信条件项目信息未找到");
		}
		
		//更新当前授信条件状态
		
		List<FCreditConditionConfirmItemDO> listItemDO = FCreditConditionConfirmItemDAO
			.findByConfirmId(project.getConfirmId());
		if (ListUtil.isNotEmpty(listItemDO)) {
			for (FCreditConditionConfirmItemDO fCreditConditionConfirmItemDO : listItemDO) {
				
				fCreditConditionConfirmItemDO.setStatus(CreditCheckStatusEnum.APPLYING.code());
				FCreditConditionConfirmItemDAO.update(fCreditConditionConfirmItemDO);
				
				//更新project_credit_contidion提交数据的状态
				ProjectCreditConditionDO projectCreditConditionDO = projectCreditConditionDAO
					.findByProjectCodeAndItemIdAndType(
						fCreditConditionConfirmItemDO.getProjectCode(),
						fCreditConditionConfirmItemDO.getItemId(),
						fCreditConditionConfirmItemDO.getType());
				if ("IS".equals(fCreditConditionConfirmItemDO.getIsConfirm())
					|| "YES".equals(fCreditConditionConfirmItemDO.getIsConfirm())) {
					projectCreditConditionDO.setStatus(CreditCheckStatusEnum.APPLYING.code());
				}
				projectCreditConditionDAO.updateCreditById(projectCreditConditionDO);
				
			}
		}
	}
	
	/**
	 * 流程结束处理 审查通过
	 * @param formInfo
	 * @param workflowResult
	 * @see com.born.fcs.pm.biz.service.common.WorkflowExtProcessService#endFlowProcess(com.born.fcs.pm.ws.info.common.FormInfo,
	 * com.born.fcs.pm.integration.bpm.result.WorkflowResult)
	 */
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		//授信条件项目信息
		FCreditConditionConfirmDO project = FCreditConditionConfirmDAO.findByFormId(formInfo
			.getFormId());
		if (project == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "授信条件项目信息未找到");
		}
		FcsBaseResult result = new FcsBaseResult();
		try {
			String projectCode = project.getProjectCode();
			List<FCreditConditionConfirmItemDO> listItem = FCreditConditionConfirmItemDAO
				.findByConfirmId(project.getConfirmId());
			if (ListUtil.isNotEmpty(listItem)) {
				for (FCreditConditionConfirmItemDO fCreditConditionConfirmItemDO : listItem) {//流程完后反写数据到授信条件表中
					ProjectCreditConditionDO creditDO = projectCreditConditionDAO
						.findByProjectCodeAndItemIdAndType(projectCode,
							fCreditConditionConfirmItemDO.getItemId(),
							fCreditConditionConfirmItemDO.getType());
					if (StringUtil.equals(fCreditConditionConfirmItemDO.getIsConfirm(), "IS")
						|| StringUtil.equals(fCreditConditionConfirmItemDO.getIsConfirm(), "YES")) {
						//把授信落实审核通过的落实人 写到合同回传表中签订人B角  附件地址也写到对应的地址中去，并更新为  已回传状态
						if (fCreditConditionConfirmItemDO.getContractNo() != null) {
							FProjectContractItemDO contractItemDO = fProjectContractItemDAO
								.findByContractCode(fCreditConditionConfirmItemDO.getContractNo());
							ProjectDO projectDO = projectDAO
								.findByProjectCode(fCreditConditionConfirmItemDO.getProjectCode());
							
							if (contractItemDO != null) {
								contractItemDO.setSignPersonA(projectDO.getBusiManagerName());
								contractItemDO.setSignPersonAId(projectDO.getBusiManagerId());
								
								contractItemDO.setSignPersonBId(fCreditConditionConfirmItemDO
									.getConfirmManId());
								contractItemDO.setSignPersonB(fCreditConditionConfirmItemDO
									.getConfirmManName());
								contractItemDO.setContractScanUrl(fCreditConditionConfirmItemDO
									.getContractAgreementUrl());
								contractItemDO.setContractStatus(ContractStatusEnum.RETURN.code());
								contractItemDO.setSignedTime(new Date());
							}
							fProjectContractItemDAO.update(contractItemDO);
						}
						
						FCreditConditionConfirmItemDAO.update(fCreditConditionConfirmItemDO);
						if (creditDO != null) {
							creditDO.setProjectCode(projectCode);
							creditDO.setItemId(fCreditConditionConfirmItemDO.getItemId());
							creditDO.setItemDesc(fCreditConditionConfirmItemDO.getItemDesc());
							creditDO.setType(fCreditConditionConfirmItemDO.getType());
							creditDO.setIsConfirm(fCreditConditionConfirmItemDO.getIsConfirm());
							creditDO.setConfirmManId(fCreditConditionConfirmItemDO
								.getConfirmManId());
							creditDO.setConfirmManAccount(fCreditConditionConfirmItemDO
								.getConfirmManAccount());
							creditDO.setContractAgreementUrl(fCreditConditionConfirmItemDO
								.getContractAgreementUrl());
							creditDO.setConfirmManName(fCreditConditionConfirmItemDO
								.getConfirmManName());
							creditDO.setConfirmDate(fCreditConditionConfirmItemDO.getConfirmDate());
							creditDO.setContractNo(fCreditConditionConfirmItemDO.getContractNo());
							creditDO.setRightVouche(fCreditConditionConfirmItemDO.getRightVouche());
							creditDO.setRemark(fCreditConditionConfirmItemDO.getRemark());
							creditDO.setTextInfo(fCreditConditionConfirmItemDO.getTextInfo());
							creditDO.setStatus(CheckStatusEnum.CHECK_PASS.getCode());
							projectCreditConditionDAO.updateCreditById(creditDO);
							//将附件信息写入资产图像信息里面
							PledgeAssetInfo assetInfo = pledgeAssetServiceClient.findById(creditDO
								.getAssetId());
							List<ProjectCreditAssetAttachmentDO> listDO = projectCreditAssetAttachmentDAO
								.findByCreditId(creditDO.getId());
							
							if (listDO != null) {
								for (ProjectCreditAssetAttachmentDO projectCreditAssetAttachmentDO : listDO) {
									PledgeTypeAttributeInfo attributeInfo = pledgeTypeAttributeWebService
										.findByassetsIdAndAttributeKeyAndCustomType(
											projectCreditAssetAttachmentDO.getAssetId(),
											projectCreditAssetAttachmentDO.getImageKey(), "IMAGE");
									if (attributeInfo == null) {
										PledgeTypeAttributeOrder order = new PledgeTypeAttributeOrder();
										order.setAssetsId(assetInfo.getAssetsId());
										order.setAttributeKey(projectCreditAssetAttachmentDO
											.getImageKey());
										order
											.setAttributeValue((projectCreditAssetAttachmentDO
												.getImageTextValue() == null ? ""
												: projectCreditAssetAttachmentDO
													.getImageTextValue())
																+ "&&"
																+ (projectCreditAssetAttachmentDO
																	.getImageValue() == null ? ""
																	: projectCreditAssetAttachmentDO
																		.getImageTextValue()));
										order.setCustomType("IMAGE");
										order.setTypeId(assetInfo.getTypeId());
										pledgeTypeAttributeWebService.save(order);
									} else {
										attributeInfo
											.setAttributeValue((projectCreditAssetAttachmentDO
												.getImageTextValue() == null ? ""
												: projectCreditAssetAttachmentDO
													.getImageTextValue())
																+ "&&"
																+ (attributeInfo
																	.getAttributeValue() == null ? ""
																	: (attributeInfo
																		.getAttributeValue() + ";"))
																+ (projectCreditAssetAttachmentDO
																	.getImageValue() == null ? ""
																	: projectCreditAssetAttachmentDO
																		.getImageValue()));
										PledgeTypeAttributeOrder order = new PledgeTypeAttributeOrder();
										order.setAssetsId(attributeInfo.getAssetsId());
										order.setAttributeId(attributeInfo.getAttributeId());
										order.setAttributeKey(attributeInfo.getAttributeKey());
										order.setAttributeValue(attributeInfo.getAttributeValue());
										order.setCustomType(attributeInfo.getCustomType());
										order.setTypeId(attributeInfo.getTypeId());
										pledgeTypeAttributeWebService.save(order);
									}
								}
							}
							
							if (creditDO.getAssetId() > 0
								&& StringUtil.equals("IS",
									fCreditConditionConfirmItemDO.getIsConfirm())) {
								try {
									ProjectDO projetDO = projectDAO.findByProjectCode(projectCode);
									//同步关联信息到资产
									List<AssetStatusOrder> listOrder = Lists.newArrayList();
									//构建资产项目关联的Order
									AssetStatusOrder as = new AssetStatusOrder();
									as.setAssetId(creditDO.getAssetId());
									if (CreditConditionTypeEnum.PLEDGE.code().equals(
										creditDO.getType())) {//抵押
										as.setStatus(AssetStatusEnum.SECURED_PLEDGE);
									} else if (CreditConditionTypeEnum.MORTGAGE.code().equals(
										creditDO.getType())) {
										as.setStatus(AssetStatusEnum.SECURED_MORTGAGE);
									}
									
									listOrder.add(as);
									///XXX 同步资产项目关系
									AssetRelationProjectBindOrder bindOrder = new AssetRelationProjectBindOrder();
									BeanCopier.staticCopy(projetDO, bindOrder);
									bindOrder.setDelOld(false);
									bindOrder.setAssetList(listOrder);
									logger.info("同步资产项目关系 {} ", bindOrder);
									pledgeAssetServiceClient.assetRelationProject(bindOrder);
								} catch (Exception e) {
									logger.error("同步资产项目关系出错 {} ", e);
									
								}
							}
						}
					} else {
						//						ProjectCreditConditionDO creditDO = projectCreditConditionDAO
						//							.findByProjectCodeAndItemIdAndType(projectCode,
						//								fCreditConditionConfirmItemDO.getItemId(),
						//								fCreditConditionConfirmItemDO.getType());
						if (creditDO != null) {
							creditDO.setStatus(CheckStatusEnum.CHECK_PASS.getCode());
							projectCreditConditionDAO.updateCreditById(creditDO);
						}
					}
					
				}
			} else {
				throw ExceptionFactory
					.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "反写授信条件项目信息未找到");
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("授信落实流程结束审核通过后反写数据出错 ： {}", e);
		}
	}
	
	//流程结束处理 审查未通过
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		//授信条件项目信息
		FCreditConditionConfirmDO project = FCreditConditionConfirmDAO.findByFormId(formInfo
			.getFormId());
		if (project == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "授信条件项目信息未找到");
		}
		
		String projectCode = project.getProjectCode();
		List<FCreditConditionConfirmItemDO> listItem = FCreditConditionConfirmItemDAO
			.findByConfirmId(project.getConfirmId());
		if (ListUtil.isNotEmpty(listItem)) {
			for (FCreditConditionConfirmItemDO fCreditConditionConfirmItemDO : listItem) {//流程完后反写数据到授信条件表中
				if (fCreditConditionConfirmItemDO.getIsConfirm() != null
					&& "IS".equals(fCreditConditionConfirmItemDO.getIsConfirm())) {
					ProjectCreditConditionDO creditDO = projectCreditConditionDAO
						.findByProjectCodeAndItemIdAndType(projectCode,
							fCreditConditionConfirmItemDO.getItemId(),
							fCreditConditionConfirmItemDO.getType());
					
					//			//更新f_project_credit_contidion的状态
					//			FCreditConditionConfirmItemDO FCreditConditionConfirmItemDO = FCreditConditionConfirmItemDAO
					//				.findByProjectCodeAndItemIdAndType(fCreditConditionConfirmItemDO.getProjectCode(),
					//					fCreditConditionConfirmItemDO.getItemId(),
					//					fCreditConditionConfirmItemDO.getType());
					fCreditConditionConfirmItemDO.setStatus(CreditCheckStatusEnum.CHECK_NOT_PASS
						.code());
					FCreditConditionConfirmItemDAO.update(fCreditConditionConfirmItemDO);
					if (creditDO != null) {
						creditDO.setIsConfirm(BooleanEnum.NO.code());
						creditDO.setStatus(CreditCheckStatusEnum.NOT_APPLY.code());//未通过就回到初始状态
						projectCreditConditionDAO.updateCreditById(creditDO);
					} else {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"反写授信条件项目信息未找到");
						
					}
				}
			}
		}
	}
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		FCreditConditionConfirmDO confirmDO = FCreditConditionConfirmDAO.findByFormId(formInfo
			.getFormId());
		if (null == confirmDO) {
			return null;
		}
		
		List<FlowVarField> fields = Lists.newArrayList();
		//风险经理ID
		ProjectRelatedUserInfo riskManager = projectRelatedUserService.getRiskManager(confirmDO
			.getProjectCode());
		FlowVarField riskManagerId = new FlowVarField();
		riskManagerId.setVarName("RiskManagerID");
		riskManagerId.setVarType(FlowVarTypeEnum.STRING);
		if (null != riskManager) {
			riskManagerId.setVarVal(String.valueOf(riskManager.getUserId()));
			
		} else {
			riskManagerId.setVarVal("0");
		}
		
		//授信条件项目信息
		FCreditConditionConfirmDO confirm = FCreditConditionConfirmDAO.findByFormId(formInfo
			.getFormId());
		if (confirm == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "授信条件项目信息未找到");
		}
		List<FCreditConditionConfirmItemDO> listProjectCreditConditionItemDO = FCreditConditionConfirmItemDAO
			.findByConfirmId(confirm.getConfirmId());
		
		//找出不重复落实人的id并且去除落实人包括当前登录用户的id
		HashSet<String> confirmIdSet = new HashSet<String>();
		if (ListUtil.isNotEmpty(listProjectCreditConditionItemDO)) {
			for (FCreditConditionConfirmItemDO fCreditConditionConfirmItemDO : listProjectCreditConditionItemDO) {
				String confirmId = fCreditConditionConfirmItemDO.getConfirmManId();
				String[] confirmIdArr = confirmId.split(",");
				for (String string : confirmIdArr) {
					if (!String.valueOf(formInfo.getUserId()).equals(string)) {
						confirmIdSet.add(string);
					}
				}
			}
		} else {
			//			ProjectSimpleDetailInfo projectInfo = projectService.querySimpleDetailInfo(confirm
			//				.getProjectCode());
			//			long busiManagerId = projectInfo.getBusiManagerId();
			//			String busiManagerName = projectInfo.getBusiManagerName();
			//			String busiManagerAccount = projectInfo.getBusiManagerAccount();
			//客户经理B角信息
			ProjectRelatedUserInfo relatedUserInfo = projectRelatedUserService
				.getBusiManagerbByPhase(confirm.getProjectCode(),
					ChangeManagerbPhaseEnum.CONFIRM_CREDIT_CONDITION);
			long busiManagerbId = relatedUserInfo.getUserId();
			//			String busiManagerbName = relatedUserInfo.getUserName();
			//			String busiManagerbAccount = relatedUserInfo.getUserAccount();
			confirmIdSet.add(busiManagerbId + "");
		}
		
		String strConfrimManId = "";
		if (!confirmIdSet.isEmpty()) {
			int index = 0;
			for (String id : confirmIdSet) {
				if (index == 0) {
					strConfrimManId = id;
				} else {
					strConfrimManId += "," + id;
				}
				index++;
			}
		}
		
		FlowVarField confrimMan = new FlowVarField();
		confrimMan.setVarName("managers");
		confrimMan.setVarType(FlowVarTypeEnum.STRING);
		//TODO 
		if (StringUtil.isBlank(strConfrimManId)) {
			confrimMan.setVarVal("0"); //落实协办人
		} else {
			confrimMan.setVarVal(strConfrimManId); //落实协办人
		}
		fields.add(confrimMan);
		fields.add(riskManagerId);
		
		return fields;
	}
	
	//测回
	@Override
	public void cancelAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		//授信条件项目信息
		FCreditConditionConfirmDO confirm = FCreditConditionConfirmDAO.findByFormId(formInfo
			.getFormId());
		if (confirm == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "授信条件项目信息未找到");
		}
		List<FCreditConditionConfirmItemDO> listProjectCreditConditionItemDO = FCreditConditionConfirmItemDAO
			.findByConfirmId(confirm.getConfirmId());
		if (ListUtil.isNotEmpty(listProjectCreditConditionItemDO)) {
			for (FCreditConditionConfirmItemDO fCreditConditionConfirmItemDO : listProjectCreditConditionItemDO) {
				fCreditConditionConfirmItemDO.setStatus("HOLDING");
				FCreditConditionConfirmItemDAO.update(fCreditConditionConfirmItemDO);
			}
		}
	}
	
	/**
	 * 提交人终止作废表单处理
	 * @param formInfo
	 */
	@Override
	public void selfEndProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		//授信条件项目信息
		FCreditConditionConfirmDO confirm = FCreditConditionConfirmDAO.findByFormId(formInfo
			.getFormId());
		if (confirm == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "授信条件项目信息未找到");
		}
		List<FCreditConditionConfirmItemDO> listProjectCreditConditionItemDO = FCreditConditionConfirmItemDAO
			.findByConfirmId(confirm.getConfirmId());
		if (ListUtil.isNotEmpty(listProjectCreditConditionItemDO)) {
			for (FCreditConditionConfirmItemDO fCreditConditionConfirmItemDO : listProjectCreditConditionItemDO) {
				ProjectCreditConditionDO DO = projectCreditConditionDAO
					.findByProjectCodeAndItemIdAndType(
						fCreditConditionConfirmItemDO.getProjectCode(),
						fCreditConditionConfirmItemDO.getItemId(),
						fCreditConditionConfirmItemDO.getType());
				DO.setStatus("NOT_APPLY");
				DO.setIsConfirm("NO");
				projectCreditConditionDAO.update(DO);
			}
		}
	}
	
	//删除
	@Override
	public void deleteAfterProcess(FormInfo formInfo) {
		//授信条件项目信息
		FCreditConditionConfirmDO confirm = FCreditConditionConfirmDAO.findByFormId(formInfo
			.getFormId());
		if (confirm == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "授信条件项目信息未找到");
		}
		List<FCreditConditionConfirmItemDO> listProjectCreditConditionItemDO = FCreditConditionConfirmItemDAO
			.findByConfirmId(confirm.getConfirmId());
		FCreditConditionConfirmDAO.deleteByFormId(formInfo.getFormId());
		if (ListUtil.isNotEmpty(listProjectCreditConditionItemDO)) {
			for (FCreditConditionConfirmItemDO fCreditConditionConfirmItemDO : listProjectCreditConditionItemDO) {
				ProjectCreditConditionDO DO = projectCreditConditionDAO
					.findByProjectCodeAndItemIdAndType(
						fCreditConditionConfirmItemDO.getProjectCode(),
						fCreditConditionConfirmItemDO.getItemId(),
						fCreditConditionConfirmItemDO.getType());
				DO.setStatus("NOT_APPLY");
				projectCreditConditionDAO.update(DO);
				FCreditConditionConfirmItemDAO.deleteById(fCreditConditionConfirmItemDO.getId());
				
				projectCreditAssetAttachmentDAO.deleteByCreditId(DO.getId());
				
				//附件管理附件删除
				commonAttachmentDAO.deleteByBizNoModuleType(
					"PM_" + fCreditConditionConfirmItemDO.getId(),
					CommonAttachmentTypeEnum.PROJECT_CREDIT_CONDITION_CONTRACT_AGREEMENT.code());
				commonAttachmentDAO.deleteByBizNoModuleType(
					"PM_" + fCreditConditionConfirmItemDO.getId(),
					CommonAttachmentTypeEnum.PROJECT_CREDIT_CONDITION_ATTACHMENT.code());
				
			}
		}
		projectCreditMarginDAO.deleteByConfirmId(confirm.getConfirmId());
		
	}
}
