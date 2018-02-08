package com.born.fcs.pm.biz.service.setup;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.crm.ws.service.CompanyCustomerService;
import com.born.fcs.crm.ws.service.PersonalCustomerService;
import com.born.fcs.crm.ws.service.enums.ChangeTypeEnum;
import com.born.fcs.crm.ws.service.info.CompanyOwnershipStructureInfo;
import com.born.fcs.crm.ws.service.order.CustomerDetailOrder;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.job.AsynchronousTaskJob;
import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.FProjectBankLoanDO;
import com.born.fcs.pm.dal.dataobject.FProjectCounterGuaranteeGuarantorDO;
import com.born.fcs.pm.dal.dataobject.FProjectCounterGuaranteePledgeDO;
import com.born.fcs.pm.dal.dataobject.FProjectCustomerBaseInfoDO;
import com.born.fcs.pm.dal.dataobject.FProjectDO;
import com.born.fcs.pm.dal.dataobject.FProjectEquityStructureDO;
import com.born.fcs.pm.dal.dataobject.FProjectExternalGuaranteeDO;
import com.born.fcs.pm.dal.dataobject.FProjectGuaranteeEntrustedDO;
import com.born.fcs.pm.dal.dataobject.FProjectLgLitigationDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CertTypeEnum;
import com.born.fcs.pm.ws.enums.CheckStatusEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.common.ChangeProjectStatusOrder;
import com.born.fcs.pm.ws.order.common.CommonAttachmentBatchOrder;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.order.common.SimpleFormOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.common.CommonAttachmentService;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("projectSetupProcessService")
public class ProjectSetupProcessServiceImpl extends BaseProcessService {
	
	@Autowired
	AsynchronousTaskJob asynchronousTaskJob;
	
	@Autowired
	PersonalCustomerService personalCustomerServiceClient;
	
	@Autowired
	CompanyCustomerService companyCustomerServiceClient;
	
	@Autowired
	ProjectRelatedUserService projectRelatedUserService;
	
	@Autowired
	CommonAttachmentService commonAttachmentService;
	
	@Override
	public List<SimpleUserInfo> resultNotifyUserList(FormInfo formInfo) {
		List<SimpleUserInfo> notifyUserList = super.resultNotifyUserList(formInfo);
		
		try {
			if (notifyUserList == null)
				notifyUserList = Lists.newArrayList();
			
			//客户经理
			ProjectRelatedUserInfo userInfo = projectRelatedUserService.getBusiManager(formInfo
				.getRelatedProjectCode());
			if (userInfo != null) {
				notifyUserList.add(userInfo.toSimpleUserInfo());
			}
			
			//风险经理
			userInfo = projectRelatedUserService.getRiskManager(formInfo.getRelatedProjectCode());
			if (userInfo != null) {
				notifyUserList.add(userInfo.toSimpleUserInfo());
				
			}
			
			//法务经理
			userInfo = projectRelatedUserService.getLegalManager(formInfo.getRelatedProjectCode());
			if (userInfo != null) {
				notifyUserList.add(userInfo.toSimpleUserInfo());
				
			}
		} catch (Exception e) {
			logger.error("获取立项结果通知人员出错{}", e);
		}
		return notifyUserList;
	}
	
	//	@Override
	//	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
	//		
	//		//担保委贷立项设置 评估公司的变量
	//		if (formInfo.getFormCode() == FormCodeEnum.SET_UP_GUARANTEE_ENTRUSTED) {
	//			
	//			FProjectDO project = FProjectDAO.findByFormId(formInfo.getFormId());
	//			FProjectGuaranteeEntrustedDO geProject = FProjectGuaranteeEntrustedDAO
	//				.findByProjectId(project.getProjectId());
	//			
	//			String hasEvaluateCompany = geProject.getHasEvaluateCompany(); //是否需要评估公司
	//			String region = geProject.getEvaluateCompanyRegion(); //评估公司是市内或市外
	//			
	//			List<FlowVarField> fields = Lists.newArrayList();
	//			FlowVarField flowVarField = new FlowVarField();
	//			flowVarField.setVarName("evaluate_company");
	//			flowVarField.setVarType(FlowVarTypeEnum.STRING);
	//			if (BooleanEnum.IS.code().equals(hasEvaluateCompany)) { //需要评估公司
	//				if (EvaluateCompanyRegionEnum.INSIDE_CITY.code().equals(region)) {
	//					flowVarField.setVarVal("inCity");
	//				} else {
	//					flowVarField.setVarVal("outCity");
	//				}
	//			} else {
	//				flowVarField.setVarVal("no");
	//			}
	//			fields.add(flowVarField);
	//			return fields;
	//		}
	//		
	//		return null;
	//	}
	
	/**
	 * 最后提交表单的时候统一调用来更新项目信息和客户信息
	 * @param order
	 * @return
	 * @see com.born.fcs.pm.biz.service.common.WorkflowExtProcessService#startBeforeProcess(com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder)
	 */
	@Override
	public FcsBaseResult startBeforeProcess(final WorkFlowBeforeProcessOrder order) {
		
		return commonProcess(order, "立项流程启动前处理", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				if (order.getFormInfo().getStatus() == FormStatusEnum.CANCEL
					|| order.getFormInfo().getStatus() == FormStatusEnum.BACK) {
					//重新提交不做处理
					long formId = order.getFormInfo().getFormId();
					FProjectDO project = FProjectDAO.findByFormId(formId);
					String projectName = project.getProjectName();
					FcsPmDomainHolder.get().addAttribute("projectName", projectName);
				} else {
					long formId = order.getFormInfo().getFormId();
					FormCodeEnum formCode = order.getFormInfo().getFormCode();
					// 2016-09-20 立项不在新增客户，同步更新数据放到审核后
					//				FProjectCustomerBaseInfoDO customerBaseInfo = syncCustomerInfo2Crm(order
					//					.getFormInfo());
					FProjectCustomerBaseInfoDO customerBaseInfo = FProjectCustomerBaseInfoDAO
						.findByFormId(formId);
					long customerId = customerBaseInfo.getCustomerId();
					String customerName = customerBaseInfo.getCustomerName();
					String customerType = customerBaseInfo.getCustomerType();
					
					//项目信息
					FProjectDO project = FProjectDAO.findByFormId(formId);
					if (project == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"项目信息未找到");
					}
					
					String projectCode = project.getProjectCode();
					String projectName = project.getProjectName();
					FcsPmDomainHolder.get().addAttribute("projectName", projectName);
					
					try {
						SimpleFormOrder submitOrder = (SimpleFormOrder) FcsPmDomainHolder.get()
							.getAttribute("submitFormOrder");
						if (submitOrder != null)
							submitOrder.setRelatedProjectCode(projectCode);
					} catch (Exception e) {
						logger.error("获取提交Order出错：{}", e);
					}
					
					if (formCode == FormCodeEnum.SET_UP_GUARANTEE_ENTRUSTED) {//担保/委贷项目
						syncProject(project, customerId, customerName, customerType,
							customerBaseInfo.getIndustryCode(), customerBaseInfo.getIndustryName());
						syncCustomerInfo(formId, projectCode, projectName);
						syncEquityStructure(formId, customerId, customerName, projectCode,
							projectName);
						syncBankLoan(formId, customerId, customerName, projectCode, projectName);
						syncExternalGuarantee(formId, customerId, customerName, projectCode,
							projectName);
						syncCounterGuarantee(formId, customerId, customerName, projectCode,
							projectName);
					} else if (formCode == FormCodeEnum.SET_UP_UNDERWRITING) {//承销类项目
						syncProject(project, customerId, customerName, customerType,
							customerBaseInfo.getIndustryCode(), customerBaseInfo.getIndustryName());
						syncCustomerInfo(formId, projectCode, projectName);
						syncEquityStructure(formId, customerId, customerName, projectCode,
							projectName);
						
					} else if (formCode == FormCodeEnum.SET_UP_LITIGATION_ENTERPRISE) {//企业诉保
						syncProject(project, customerId, customerName, customerType,
							customerBaseInfo.getIndustryCode(), customerBaseInfo.getIndustryName());
						syncCustomerInfo(formId, projectCode, projectName);
						syncEquityStructure(formId, customerId, customerName, projectCode,
							projectName);
						syncBankLoan(formId, customerId, customerName, projectCode, projectName);
						syncExternalGuarantee(formId, customerId, customerName, projectCode,
							projectName);
					} else if (formCode == FormCodeEnum.SET_UP_LITIGATION_PERSIONAL) {//个人诉保
						syncProject(project, customerId, customerName, customerType,
							customerBaseInfo.getIndustryCode(), customerBaseInfo.getIndustryName());
						syncCustomerInfo(formId, projectCode, projectName);
						syncBankLoan(formId, customerId, customerName, projectCode, projectName);
						syncExternalGuarantee(formId, customerId, customerName, projectCode,
							projectName);
					}
					
					//生成project
					genProject(project);
					
					//保存业务经理到相关人员表
					ProjectRelatedUserOrder relatedUser = new ProjectRelatedUserOrder();
					relatedUser.setProjectCode(projectCode);
					relatedUser.setUserType(ProjectRelatedUserTypeEnum.BUSI_MANAGER);
					relatedUser.setRemark("业务经理");
					relatedUser.setUserId(project.getBusiManagerId());
					relatedUser.setUserAccount(project.getBusiManagerAccount());
					relatedUser.setUserName(project.getBusiManagerName());
					relatedUser.setDeptId(project.getDeptId());
					relatedUser.setDeptCode(project.getDeptCode());
					relatedUser.setDeptName(project.getDeptName());
					relatedUser.setDeptPath(project.getDeptPath());
					relatedUser.setDeptPathName(project.getDeptPathName());
					projectRelatedUserService.setRelatedUser(relatedUser);
				}
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				
				//自定义待办任务名称
				WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
					.getAttribute("startOrder");
				if (startOrder != null) {
					String projectName = (String) FcsPmDomainHolder.get().getAttribute(
						"projectName");
					if (StringUtil.isNotBlank(projectName)) {
						startOrder.setCustomTaskName(projectName + "-立项申请单");
					}
				}
				
				return null;
			}
		});
	}
	
	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		logger.info("立项提交后处理开始 formId:{} time:{}", formInfo.getFormId(), new Date());
		try {
			FProjectDO fproject = FProjectDAO.findByFormId(formInfo.getFormId());
			genProject(fproject);
		} catch (Exception e) {
			logger.info("立项提交后处理出错{}", e);
		}
	}
	
	@Override
	public FcsBaseResult doNextBeforeProcess(final WorkFlowBeforeProcessOrder order) {
		
		return commonProcess(order, "项目立项审核前置处理", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				FormInfo formInfo = order.getFormInfo();
				
				Map<String, Object> customizeMap = order.getCustomizeMap();
				
				if (customizeMap != null) {
					
					FProjectDO project = FProjectDAO.findByFormId(formInfo.getFormId());
					boolean hasChange = false;
					
					//客户经理
					ProjectRelatedUserInfo busiManager = projectRelatedUserService
						.getBusiManager(project.getProjectCode());
					
					/**
					 * 担保/委贷
					 */
					if (formInfo.getFormCode() == FormCodeEnum.SET_UP_GUARANTEE_ENTRUSTED) {
						
						FProjectGuaranteeEntrustedDO geProject = FProjectGuaranteeEntrustedDAO
							.findByProjectId(project.getProjectId());
						
						boolean hasDetailChange = false;
						
						//选择评估公司
						//						if (customizeMap.get("evaluateCompany") != null) {
						//							long evaluateCompanyId = NumberUtil.parseLong((String) customizeMap
						//								.get("evaluateCompanyId"));
						//							String evaluateCompanyName = (String) customizeMap
						//								.get("evaluateCompanyName");
						//							geProject.setEvaluateCompanyId(evaluateCompanyId);
						//							geProject.setEvaluateCompanyName(evaluateCompanyName);
						//							hasDetailChange = true;
						//						}
						
						//选择业务经理B角
						String chooseBusiManagerb = (String) customizeMap.get("chooseBusiManagerb");
						if (BooleanEnum.YES.code().equals(chooseBusiManagerb)) {
							long busiManagerbId = NumberUtil.parseLong((String) customizeMap
								.get("busiManagerbId"));
							String busiManagerbAccount = (String) customizeMap
								.get("busiManagerbAccount");
							String busiManagerbName = (String) customizeMap.get("busiManagerbName");
							project.setBusiManagerbId(busiManagerbId);
							project.setBusiManagerbAccount(busiManagerbAccount);
							project.setBusiManagerbName(busiManagerbName);
							hasChange = true;
							
							if (busiManager.getUserId() == busiManagerbId) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.NO_ACCESS,
									"业务经理B角不能和业务经理相同");
							}
							
							//保存业务经理B到相关人员表
							ProjectRelatedUserOrder relatedUser = new ProjectRelatedUserOrder();
							relatedUser.setProjectCode(project.getProjectCode());
							relatedUser.setUserType(ProjectRelatedUserTypeEnum.BUSI_MANAGERB);
							relatedUser.setRemark("担保/委贷,立项审核,选择业务经理B");
							relatedUser.setUserId(busiManagerbId);
							relatedUser.setUserAccount(busiManagerbAccount);
							relatedUser.setUserName(busiManagerbName);
							projectRelatedUserService.setRelatedUser(relatedUser);
						}
						
						//选择风险经理
						String selectRiskManager = (String) customizeMap.get("chooseRiskManager");
						if (BooleanEnum.YES.code().equals(selectRiskManager)) {
							long riskManagerId = NumberUtil.parseLong((String) customizeMap
								.get("riskManagerId"));
							String riskManagerAccount = (String) customizeMap
								.get("riskManagerAccount");
							String riskManagerName = (String) customizeMap.get("riskManagerName");
							geProject.setRiskManagerId(riskManagerId);
							geProject.setRiskManagerAccount(riskManagerAccount);
							geProject.setRiskManagerName(riskManagerName);
							hasDetailChange = true;
							
							if (busiManager.getUserId() == riskManagerId) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.NO_ACCESS,
									"风险经理不能和业务经理相同");
							}
							
							//保存风险到相关人员表
							ProjectRelatedUserOrder relatedUser = new ProjectRelatedUserOrder();
							relatedUser.setProjectCode(project.getProjectCode());
							relatedUser.setUserType(ProjectRelatedUserTypeEnum.RISK_MANAGER);
							relatedUser.setRemark("担保/委贷,立项审核,选择风险经理");
							relatedUser.setUserId(riskManagerId);
							relatedUser.setUserAccount(riskManagerAccount);
							relatedUser.setUserName(riskManagerName);
							projectRelatedUserService.setRelatedUser(relatedUser);
						}
						
						if (hasDetailChange) {
							FProjectGuaranteeEntrustedDAO.update(geProject);
						}
						
						/**
						 * 承销
						 */
					} else if (formInfo.getFormCode() == FormCodeEnum.SET_UP_UNDERWRITING) {
						
						//选择协作人（用业务经理B角处理）
						String chooseBusiManagerb = (String) customizeMap.get("chooseBusiManagerb");
						if (BooleanEnum.YES.code().equals(chooseBusiManagerb)) {
							long busiManagerbId = NumberUtil.parseLong((String) customizeMap
								.get("busiManagerbId"));
							String busiManagerbAccount = (String) customizeMap
								.get("busiManagerbAccount");
							String busiManagerbName = (String) customizeMap.get("busiManagerbName");
							project.setBusiManagerbId(busiManagerbId);
							project.setBusiManagerbAccount(busiManagerbAccount);
							project.setBusiManagerbName(busiManagerbName);
							hasChange = true;
							
							if (busiManager.getUserId() == busiManagerbId) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.NO_ACCESS,
									"协作人不能和业务经理相同");
							}
							
							//保存业务经理B到相关人员表
							ProjectRelatedUserOrder relatedUser = new ProjectRelatedUserOrder();
							relatedUser.setProjectCode(project.getProjectCode());
							relatedUser.setUserType(ProjectRelatedUserTypeEnum.BUSI_MANAGERB);
							relatedUser.setRemark("承销,立项审核,选择协作人(业务经理B)");
							relatedUser.setUserId(busiManagerbId);
							relatedUser.setUserAccount(busiManagerbAccount);
							relatedUser.setUserName(busiManagerbName);
							projectRelatedUserService.setRelatedUser(relatedUser);
						}
						
						/**
						 * 诉讼担保
						 */
					} else if (formInfo.getFormCode() == FormCodeEnum.SET_UP_LITIGATION_ENTERPRISE
								|| formInfo.getFormCode() == FormCodeEnum.SET_UP_LITIGATION_PERSIONAL) {
						
						FProjectLgLitigationDO lgProject = FProjectLgLitigationDAO
							.findByProjectId(project.getProjectId());
						boolean hasDetailChange = false;
						
						//选择业务经理B角
						String chooseBusiManagerb = (String) customizeMap.get("chooseBusiManagerb");
						if (BooleanEnum.YES.code().equals(chooseBusiManagerb)) {
							long busiManagerbId = NumberUtil.parseLong((String) customizeMap
								.get("busiManagerbId"));
							String busiManagerbAccount = (String) customizeMap
								.get("busiManagerbAccount");
							String busiManagerbName = (String) customizeMap.get("busiManagerbName");
							project.setBusiManagerbId(busiManagerbId);
							project.setBusiManagerbAccount(busiManagerbAccount);
							project.setBusiManagerbName(busiManagerbName);
							hasChange = true;
							
							if (busiManager.getUserId() == busiManagerbId) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.NO_ACCESS,
									"业务经理B角不能和业务经理相同");
							}
							
							//保存业务经理B到相关人员表
							ProjectRelatedUserOrder relatedUser = new ProjectRelatedUserOrder();
							relatedUser.setProjectCode(project.getProjectCode());
							relatedUser.setUserType(ProjectRelatedUserTypeEnum.BUSI_MANAGERB);
							relatedUser.setRemark("诉讼担保,立项审核,选择业务经理B");
							relatedUser.setUserId(busiManagerbId);
							relatedUser.setUserAccount(busiManagerbAccount);
							relatedUser.setUserName(busiManagerbName);
							projectRelatedUserService.setRelatedUser(relatedUser);
						}
						
						//选择法务经理
						String chooseLegalManager = (String) customizeMap.get("chooseLegalManager");
						if (BooleanEnum.YES.code().equals(chooseLegalManager)) {
							long legalManagerId = NumberUtil.parseLong((String) customizeMap
								.get("legalManagerId"));
							String legalManagerAccount = (String) customizeMap
								.get("legalManagerAccount");
							String legalManagerName = (String) customizeMap.get("legalManagerName");
							lgProject.setLegalManagerId(legalManagerId);
							lgProject.setLegalManagerAccount(legalManagerAccount);
							lgProject.setLegalManagerName(legalManagerName);
							hasDetailChange = true;
							
							if (busiManager.getUserId() == legalManagerId) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.NO_ACCESS,
									"法务经理不能和业务经理相同");
							}
							
							//保存法务经理到相关人员表
							ProjectRelatedUserOrder relatedUser = new ProjectRelatedUserOrder();
							relatedUser.setProjectCode(project.getProjectCode());
							relatedUser.setUserType(ProjectRelatedUserTypeEnum.LEGAL_MANAGER);
							relatedUser.setRemark("诉讼担保,立项审核,选择法务经理");
							relatedUser.setUserId(legalManagerId);
							relatedUser.setUserAccount(legalManagerAccount);
							relatedUser.setUserName(legalManagerName);
							projectRelatedUserService.setRelatedUser(relatedUser);
						}
						
						if (hasDetailChange) {
							FProjectLgLitigationDAO.update(lgProject);
						}
					}
					
					if (hasChange) {
						FProjectDAO.update(project);
					}
				}
				
				return null;
			}
		}, null, null);
		
	}
	
	/**
	 * 流程结束处理
	 * @param formInfo
	 * @param workflowResult
	 * @see com.born.fcs.pm.biz.service.common.WorkflowExtProcessService#endFlowProcess(com.born.fcs.pm.ws.info.common.FormInfo,
	 * com.born.fcs.pm.integration.bpm.result.WorkflowResult)
	 */
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		
		logger.info("立项审核通过处理开始 formId:{} time:{}", formInfo.getFormId(), new Date());
		try {
			FProjectDO fproject = FProjectDAO.findByFormId(formInfo.getFormId());
			ProjectDO project = projectDAO.findByProjectCode(fproject.getProjectCode());
			//同步项目信息
			long projectId = project.getProjectId(); //防止copy掉了
			BeanCopier.staticCopy(fproject, project);
			project.setProjectId(projectId);
			//项目进入尽职调查阶段
			project.setPhases(ProjectPhasesEnum.INVESTIGATING_PHASES.code());
			project.setStatus(ProjectStatusEnum.NORMAL.code());
			project.setPhasesStatus(ProjectPhasesStatusEnum.WAITING.code());
			projectDAO.update(project);
		} catch (Exception e) {
			logger.info("立项审核通过处理出错{}", e);
		}
		
		syncCustomerInfo2Crm(formInfo, ChangeTypeEnum.LX);
		
		logger.info("立项审核通过处理结束 formId:{} time:{}", formInfo.getFormId(), new Date());
		
	}
	
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		ChangeProjectStatusOrder order = new ChangeProjectStatusOrder();
		order.setProjectCode(formInfo.getRelatedProjectCode());
		//项目失败
		order.setPhase(ProjectPhasesEnum.SET_UP_PHASES);
		order.setPhaseStatus(ProjectPhasesStatusEnum.NOPASS);
		order.setStatus(ProjectStatusEnum.FAILED);
		projectService.changeProjectStatus(order);
	}
	
	@Override
	public void deleteAfterProcess(FormInfo formInfo) {
		if (StringUtil.isNotEmpty(formInfo.getRelatedProjectCode())
			&& formInfo.getStatus() != FormStatusEnum.DRAFT) {
			projectDAO.deleteByProjectCode(formInfo.getRelatedProjectCode());
		}
	}
	
	/**
	 * 同步项目信息
	 * @param project
	 * @param customerId
	 * @param customerName
	 * @param customerType
	 */
	private void syncProject(FProjectDO project, long customerId, String customerName,
								String customerType, String industryCode, String industryName) {
		logger
			.info(
				"开始同步立项项目表数据[formId:{},projectCode:{},customerId:{},customerName:{},customerType:{},industryCode:{},industryName:{}]",
				project.getFormId(), project.getProjectCode(), customerId, customerName,
				customerType, industryCode, industryName);
		project.setCustomerId(customerId);
		project.setCustomerName(customerName);
		project.setCustomerType(customerType);
		project.setIndustryCode(industryCode);
		project.setIndustryName(industryName);
		FProjectDAO.updateCustomInfoByFormId(project);
		logger.info("同步立项项目表数据完成{}", project);
	}
	
	/**
	 * 同步客户信息
	 * @param formId
	 * @param projectCode
	 * @param projectName
	 */
	private void syncCustomerInfo(long formId, String projectCode, String projectName) {
		logger.info("开始同步客户表数据[formId:{},projectCode:{},projectName:{}]", formId, projectCode,
			projectName);
		FProjectCustomerBaseInfoDAO.updateProjectInfoByFormId(projectCode, projectName, formId);
		logger.info("同步客户表数据完成[formId:{},projectCode:{},projectName:{}]", formId, projectCode,
			projectName);
	}
	
	/**
	 * 同步股东结构
	 * @param customerId
	 * @param projectCode
	 * @param projectName
	 */
	private void syncEquityStructure(long formId, long customerId, String customerName,
										String projectCode, String projectName) {
		logger.info(
			"开始同步股权结构表数据[formId:{},projectCode:{},projectName:{},customerId:{},customerName:{}]",
			formId, projectCode, projectName, customerId, customerName);
		FProjectEquityStructureDO equityStructure = new FProjectEquityStructureDO();
		equityStructure.setFormId(formId);
		equityStructure.setProjectCode(projectCode);
		equityStructure.setProjectName(projectName);
		equityStructure.setCustomerId(customerId);
		equityStructure.setCustomerName(customerName);
		FProjectEquityStructureDAO.updateProjectAndCustomerInfoByFormId(equityStructure);
		logger.info(
			"同步股权结构表数据完成[formId:{},projectCode:{},projectName:{},customerId:{},customerName:{}]",
			formId, projectCode, projectName, customerId, customerName);
	}
	
	/**
	 * 同步银行贷款情况
	 * @param customerId
	 * @param projectCode
	 * @param projectName
	 */
	private void syncBankLoan(long formId, long customerId, String customerName,
								String projectCode, String projectName) {
		logger.info(
			"开始同步银行贷款表数据[formId:{},projectCode:{},projectName:{},customerId:{},customerName:{}]",
			formId, projectCode, projectName, customerId, customerName);
		
		FProjectBankLoanDO bankLoan = new FProjectBankLoanDO();
		bankLoan.setFormId(formId);
		bankLoan.setProjectCode(projectCode);
		bankLoan.setProjectName(projectName);
		bankLoan.setCustomerId(customerId);
		bankLoan.setCustomerName(customerName);
		FProjectBankLoanDAO.updateProjectAndCustomerInfoByFormId(bankLoan);
		logger.info(
			"同步银行贷款表数据完成[formId:{},projectCode:{},projectName:{},customerId:{},customerName:{}]",
			formId, projectCode, projectName, customerId, customerName);
	}
	
	/**
	 * 同步对外担保情况
	 * @param customerId
	 * @param projectCode
	 * @param projectName
	 */
	private void syncExternalGuarantee(long formId, long customerId, String customerName,
										String projectCode, String projectName) {
		logger.info(
			"开始同步对外担保情况表数据[formId:{},projectCode:{},projectName:{},customerId:{},customerName:{}]",
			formId, projectCode, projectName, customerId, customerName);
		FProjectExternalGuaranteeDO externalGuarantee = new FProjectExternalGuaranteeDO();
		externalGuarantee.setFormId(formId);
		externalGuarantee.setProjectCode(projectCode);
		externalGuarantee.setProjectName(projectName);
		externalGuarantee.setCustomerId(customerId);
		externalGuarantee.setCustomerName(customerName);
		FProjectExternalGuaranteeDAO.updateProjectAndCustomerInfoByFormId(externalGuarantee);
		logger.info(
			"同步对外担保情况表数据完成[formId:{},projectCode:{},projectName:{},customerId:{},customerName:{}]",
			formId, projectCode, projectName, customerId, customerName);
	}
	
	/**
	 * 同步反担保情况
	 * @param formId
	 * @param customerId
	 * @param customerName
	 * @param projectCode
	 * @param projectName
	 */
	private void syncCounterGuarantee(long formId, long customerId, String customerName,
										String projectCode, String projectName) {
		
		logger.info(
			"开始同步反担保表数据[formId：{},projectCode:{},projectName:{},customerId:{},customerName:{}]",
			formId, projectCode, projectName, customerId, customerName);
		
		// 反担保-担保人
		FProjectCounterGuaranteeGuarantorDO guarantor = new FProjectCounterGuaranteeGuarantorDO();
		guarantor.setFormId(formId);
		guarantor.setProjectCode(projectCode);
		guarantor.setProjectName(projectName);
		guarantor.setCustomerId(customerId);
		guarantor.setCustomerName(customerName);
		
		// 反担保- 抵（质）押物
		FProjectCounterGuaranteePledgeDO pledge = new FProjectCounterGuaranteePledgeDO();
		pledge.setFormId(formId);
		pledge.setProjectCode(projectCode);
		pledge.setProjectName(projectName);
		pledge.setCustomerId(customerId);
		pledge.setCustomerName(customerName);
		
		FProjectCounterGuaranteeGuarantorDAO.updateProjectAndCustomerInfoByFormId(guarantor);
		FProjectCounterGuaranteePledgeDAO.updateProjectAndCustomerInfoByFormId(pledge);
		
		logger.info(
			"同步反担保表数据完成[formId：{},projectCode:{},projectName:{},customerId:{},customerName:{}]",
			formId, projectCode, projectName, customerId, customerName);
	}
	
	/**
	 * 提交时,生成项目信息
	 * @param project
	 */
	private void genProject(FProjectDO project) {
		if (project == null || StringUtil.isEmpty(project.getProjectCode())) {
			return;
		}
		try {
			logger.info("开始生产项目数据:{}", project);
			//生成project
			ProjectDO realProject = projectDAO.findByProjectCode(project.getProjectCode());
			boolean isUpdate = true;
			if (realProject == null) {
				isUpdate = false;
				realProject = new ProjectDO();
				BeanCopier.staticCopy(project, realProject);
				realProject.setIsApproval(BooleanEnum.NO.code());
				realProject.setIsApprovalDel(BooleanEnum.NO.code());
				realProject.setIsContinue(BooleanEnum.NO.code());
				realProject.setIsMaximumAmount(BooleanEnum.NO.code());
				realProject.setIsRecouncil(BooleanEnum.NO.code());
				realProject.setProjectId(0);
			} else {
				long realId = realProject.getProjectId();
				BeanCopier.staticCopy(project, realProject);
				realProject.setProjectId(realId);
			}
			realProject.setPhases(ProjectPhasesEnum.SET_UP_PHASES.code());
			realProject.setStatus(ProjectStatusEnum.NORMAL.code());
			realProject.setPhasesStatus(ProjectPhasesStatusEnum.AUDITING.code());
			realProject.setBusiSubType(project.getBusiType());
			realProject.setBusiSubTypeName(project.getBusiTypeName());
			if (StringUtil.isBlank(realProject.getBelongToNc())) {
				realProject.setBelongToNc(BooleanEnum.NO.code());
			}
			if (isUpdate) {
				projectDAO.update(realProject);
			} else {
				realProject.setProjectId(projectDAO.insert(realProject));
			}
			logger.info("项目数据生成完成：{}", realProject);
		} catch (Exception e) {
			logger.error("生成项目出错：{}", e);
		}
	}
	
	//	/**
	//	 * 新增客户后同步客户信息到各个表
	//	 * @param customerBaseInfo
	//	 * @param formInfo
	//	 */
	//	private void syncNewCustomerInfo2FormTable(FProjectCustomerBaseInfoDO customerBaseInfo,
	//												FormInfo formInfo) {
	//		if (customerBaseInfo != null) {
	//			
	//			FormCodeEnum formCode = formInfo.getFormCode();
	//			long formId = formInfo.getFormId();
	//			//项目信息
	//			FProjectDO project = FProjectDAO.findByFormId(formId);
	//			
	//			long customerId = customerBaseInfo.getCustomerId();
	//			String customerName = customerBaseInfo.getCustomerName();
	//			String customerType = customerBaseInfo.getCustomerType();
	//			String projectCode = project.getProjectCode();
	//			String projectName = project.getProjectName();
	//			if (formCode == FormCodeEnum.SET_UP_GUARANTEE_ENTRUSTED) {//担保/委贷项目
	//				syncProject(project, customerId, customerName, customerType,
	//					customerBaseInfo.getIndustryCode(), customerBaseInfo.getIndustryName());
	//				//syncCustomerInfo(formId, projectCode, projectName);
	//				syncEquityStructure(formId, customerId, customerName, projectCode, projectName);
	//				syncBankLoan(formId, customerId, customerName, projectCode, projectName);
	//				syncExternalGuarantee(formId, customerId, customerName, projectCode, projectName);
	//				syncCounterGuarantee(formId, customerId, customerName, projectCode, projectName);
	//			} else if (formCode == FormCodeEnum.SET_UP_UNDERWRITING) {//承销类项目
	//				syncProject(project, customerId, customerName, customerType,
	//					customerBaseInfo.getIndustryCode(), customerBaseInfo.getIndustryName());
	//				//syncCustomerInfo(formId, projectCode, projectName);
	//				syncEquityStructure(formId, customerId, customerName, projectCode, projectName);
	//			} else if (formCode == FormCodeEnum.SET_UP_LITIGATION_ENTERPRISE) {//企业诉保
	//				syncProject(project, customerId, customerName, customerType,
	//					customerBaseInfo.getIndustryCode(), customerBaseInfo.getIndustryName());
	//				//syncCustomerInfo(formId, projectCode, projectName);
	//				syncEquityStructure(formId, customerId, customerName, projectCode, projectName);
	//				syncBankLoan(formId, customerId, customerName, projectCode, projectName);
	//				syncExternalGuarantee(formId, customerId, customerName, projectCode, projectName);
	//			} else if (formCode == FormCodeEnum.SET_UP_LITIGATION_PERSIONAL) {//个人诉保
	//				syncProject(project, customerId, customerName, customerType,
	//					customerBaseInfo.getIndustryCode(), customerBaseInfo.getIndustryName());
	//				//syncCustomerInfo(formId, projectCode, projectName);
	//				syncBankLoan(formId, customerId, customerName, projectCode, projectName);
	//				syncExternalGuarantee(formId, customerId, customerName, projectCode, projectName);
	//			}
	//		}
	//	}
	
	/**
	 * XXX 同步客户数据到客户系统
	 * @param formId
	 * @return newCustomerId 新增的客户ID
	 * @param changeType 客户信息修改来源
	 */
	private FProjectCustomerBaseInfoDO syncCustomerInfo2Crm(FormInfo formInfo,
															ChangeTypeEnum changeType) {
		FProjectCustomerBaseInfoDO customerInfo = FProjectCustomerBaseInfoDAO.findByFormId(formInfo
			.getFormId());
		
		try {
			//附件
			if (StringUtil.isNotEmpty(customerInfo.getApplyScanningUrl())) {
				CommonAttachmentBatchOrder attachOrder = new CommonAttachmentBatchOrder();
				attachOrder.setProjectCode(customerInfo.getProjectCode());
				attachOrder.setBizNo("PM_" + formInfo.getFormId());
				attachOrder.setCheckStatus(CheckStatusEnum.CHECK_PASS.code());
				attachOrder.setModuleType(CommonAttachmentTypeEnum.PROJECT_SETUP);
				attachOrder.setRemark("立项-客户申请书");
				attachOrder.setUploaderId(formInfo.getUserId());
				attachOrder.setUploaderName(formInfo.getUserName());
				attachOrder.setUploaderAccount(formInfo.getUserAccount());
				attachOrder.setPath(customerInfo.getApplyScanningUrl());
				commonAttachmentService.addNewDelOldByMoudleAndBizNo(attachOrder);
			}
			//客户原始数据
			CustomerDetailOrder customerDetailOrder = getCustomerInfo(customerInfo.getCustomerId());
			//修改来源设置
			customerDetailOrder.setChangeType(changeType);
			//修改人设置
			customerDetailOrder.setOperId(formInfo.getUserId());
			customerDetailOrder.setOperName(formInfo.getUserName());
			//修改记录覆盖
			BeanCopier.staticCopy(customerInfo, customerDetailOrder);
			
			//个人客户
			if (CustomerTypeEnum.PERSIONAL.code().equals(customerInfo.getCustomerType())) {
				customerDetailOrder.setCertNo(customerInfo.getCertNo());
				if (StringUtil
					.equals(CertTypeEnum.IDENTITY_CARD.code(), customerInfo.getCertType())) {
					customerDetailOrder.setCertImgFont(customerInfo.getBusiLicenseUrl());//身份证正面
					customerDetailOrder.setCertImgBack(customerInfo.getOrgCodeUrl()); //身份证反面
				} else {
					customerDetailOrder.setCertImgFont(customerInfo.getBusiLicenseUrl());//证件照
				}
				customerDetailOrder.setMobile(customerInfo.getContactNo());
				customerDetailOrder.setCertType(customerInfo.getCertType());
				//个人客户立项不会修改以下字段
				customerDetailOrder.setBusiLicenseUrl(null);
				customerDetailOrder.setOrgCodeUrl(null);
				customerDetailOrder.setNetAsset(customerDetailOrder.getNetAsset());
				customerDetailOrder.setTotalAsset(customerDetailOrder.getTotalAsset());
			} else {
				//企业客户
				//查询股权结构
				List<FProjectEquityStructureDO> sList = FProjectEquityStructureDAO
					.findByFormId(formInfo.getFormId());
				if (ListUtil.isNotEmpty(sList)) {
					List<CompanyOwnershipStructureInfo> crmStructureList = Lists.newArrayList();
					for (FProjectEquityStructureDO structure : sList) {
						CompanyOwnershipStructureInfo sInfo = new CompanyOwnershipStructureInfo();
						sInfo.setEquity(String.valueOf(structure.getEquityRatio()));//股权比例
						sInfo.setMethord(structure.getContributionWay()); //出资方式
						sInfo.setShareholdersName(structure.getStockholder()); //主要股东名称
						sInfo.setAmount(structure.getCapitalContributions()); //出资额（万元）
						sInfo.setAmountType(structure.getAmountType());
						crmStructureList.add(sInfo);
					}
					customerDetailOrder.setCompanyOwnershipStructure(crmStructureList);
				}
			}
			
			logger.info("同步客户数据到CRM：{}", customerInfo);
			FcsBaseResult syncResult = customerServiceClient.updateByUserId(customerDetailOrder);
			logger.info("同步客户数据到CRM result：{}", syncResult);
		} catch (Exception e) {
			logger.error("同步客户数据到CRM出错{}", e);
			throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAILURE, "同步到客户信息到客户系统出错");
		}
		return customerInfo;
		
		//		FProjectCustomerBaseInfoDO customerInfo = FProjectCustomerBaseInfoDAO.findByFormId(formInfo
		//			.getFormId());
		//		try {
		//			logger.info("同步客户数据到CRM：{}", customerInfo);
		//			FcsBaseResult syncResult = createResult();
		//			//个人客户
		//			if (CustomerTypeEnum.PERSIONAL.code().equals(customerInfo.getCustomerType())) {
		//				if (customerInfo.getCustomerId() > 0) {
		//					PersonalCustomerInfo fullInfo = personalCustomerServiceClient
		//						.queryByUserId(customerInfo.getCustomerId());
		//					if (fullInfo != null) {
		//						BeanCopier.staticCopy(customerInfo, fullInfo);
		//						fullInfo.setCertNo(customerInfo.getCertNo());
		//						if (StringUtil.equals(CertTypeEnum.IDENTITY_CARD.code(),
		//							customerInfo.getCertType())) {
		//							fullInfo.setCertImgFont(customerInfo.getBusiLicenseUrl());//身份证正面
		//							fullInfo.setCertImgBack(customerInfo.getOrgCodeUrl()); //身份证反面
		//						} else {
		//							fullInfo.setCertImgFont(customerInfo.getBusiLicenseUrl());//证件照
		//						}
		//						fullInfo.setMobile(customerInfo.getContactNo());
		//						fullInfo.setCertType(customerInfo.getCertType());
		//						logger.info("更新个人客户到CRM {}", fullInfo);
		//						syncResult = personalCustomerServiceClient.update(fullInfo);
		//					}
		//				} else {
		//					// 2016-9-20 需求变更，立项不再新增客户
		//					//					PersonalCustomerInfo exists = personalCustomerServiceClient
		//					//						.queryByCertNo(customerInfo.getCertNo());
		//					//					if (exists != null) {
		//					//						throw ExceptionFactory.newFcsException(
		//					//							FcsResultEnum.DO_ACTION_STATUS_ERROR, "客户已存在");
		//					//					}
		//					//					//新增个人客户
		//					//					PersonalCustomerDetailOrder order = new PersonalCustomerDetailOrder();
		//					//					BeanCopier.staticCopy(customerInfo, order);
		//					//					order.setCertNo(customerInfo.getCertNo());
		//					//					order.setCertImgFont(customerInfo.getBusiLicenseUrl());
		//					//					order.setCertImgBack(customerInfo.getOrgCodeUrl());
		//					//					order.setMobile(customerInfo.getContactNo());
		//					//					order.setCustomerManagerId(formInfo.getUserId());//直接挂在当前客户经理
		//					//					order.setCustomerManager(formInfo.getUserName());
		//					//					order.setDepId(formInfo.getDeptId()); //所属部门
		//					//					order.setDepName(formInfo.getDeptName());
		//					//					order.setInputPersonId(formInfo.getUserId());
		//					//					order.setInputPerson(formInfo.getUserName());
		//					//					order.setResoursFrom(CustomerSourceEnum.GRFZ.code());
		//					//					
		//					//					if (StringUtil.equals(CertTypeEnum.IDENTITY_CARD.code(),
		//					//						customerInfo.getCertType())) {
		//					//						order.setCertImgFont(customerInfo.getBusiLicenseUrl());//身份证正面
		//					//						order.setCertImgBack(customerInfo.getOrgCodeUrl()); //身份证反面
		//					//					} else {
		//					//						order.setCertImgFont(customerInfo.getBusiLicenseUrl());//证件照
		//					//					}
		//					//					order.setCertType(customerInfo.getCertType());
		//					//					
		//					//					//查询部门负责人
		//					//					List<SimpleUserInfo> busiFzr = projectRelatedUserService.getRoleDeptUser(
		//					//						formInfo.getDeptId(), sysParameterService
		//					//							.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code()));
		//					//					if (ListUtil.isNotEmpty(busiFzr)) {
		//					//						SimpleUserInfo user = busiFzr.get(0);
		//					//						order.setDirector(user.getUserName()); //总监
		//					//						order.setDirectorId(user.getUserId());
		//					//					}
		//					//					order.setIsDistribution(BooleanEnum.IS.code()); //已分配
		//					//					order.setStatus("on");
		//					//					logger.info("新增个人客户到CRM {}", order);
		//					//					syncResult = personalCustomerServiceClient.add(order);
		//				}
		//			} else {//企业客户
		//				if (customerInfo.getCustomerId() > 0) {
		//					CompanyCustomerInfo fullInfo = companyCustomerServiceClient.queryByUserId(
		//						customerInfo.getCustomerId(), null);
		//					if (fullInfo != null) {
		//						BeanCopier.staticCopy(customerInfo, fullInfo);
		//						
		//						//查询股权结构
		//						List<FProjectEquityStructureDO> sList = FProjectEquityStructureDAO
		//							.findByFormId(formInfo.getFormId());
		//						if (ListUtil.isNotEmpty(sList)) {
		//							List<CompanyOwnershipStructureInfo> crmStructureList = Lists
		//								.newArrayList();
		//							for (FProjectEquityStructureDO structure : sList) {
		//								CompanyOwnershipStructureInfo sInfo = new CompanyOwnershipStructureInfo();
		//								sInfo.setEquity(String.valueOf(structure.getEquityRatio()));//股权比例
		//								sInfo.setMethord(structure.getContributionWay()); //出资方式
		//								sInfo.setShareholdersName(structure.getStockholder()); //主要股东名称
		//								sInfo.setAmount(structure.getCapitalContributions()); //出资额（万元）
		//								crmStructureList.add(sInfo);
		//							}
		//							fullInfo.setCompanyOwnershipStructure(crmStructureList);
		//						}
		//						logger.info("更新企业客户到CRM {}", fullInfo);
		//						syncResult = companyCustomerServiceClient.update(fullInfo);
		//					}
		//				} else {
		//					// 2016-9-20 需求变更，立项不再新增客户
		//					//					CompanyCustomerInfo exists = companyCustomerServiceClient
		//					//						.queryByBusiLicenseNo(customerInfo.getBusiLicenseNo());
		//					//					if (exists != null) {
		//					//						throw ExceptionFactory.newFcsException(
		//					//							FcsResultEnum.DO_ACTION_STATUS_ERROR, "客户已存在");
		//					//					}
		//					//					//新增企业客户
		//					//					CompanyCustomerDetailOrder order = new CompanyCustomerDetailOrder();
		//					//					BeanCopier.staticCopy(customerInfo, order);
		//					//					
		//					//					order.setCustomerManagerId(formInfo.getUserId());//直接挂在当前客户经理
		//					//					order.setCustomerManager(formInfo.getUserName());
		//					//					order.setDepId(formInfo.getDeptId()); //所属部门
		//					//					order.setDepName(formInfo.getDeptName());
		//					//					order.setInputPersonId(formInfo.getUserId());
		//					//					order.setInputPerson(formInfo.getUserName());
		//					//					order.setResoursFrom(CustomerSourceEnum.GRFZ.code());
		//					//					
		//					//					//查询部门负责人
		//					//					List<SimpleUserInfo> busiFzr = projectRelatedUserService.getRoleDeptUser(
		//					//						formInfo.getDeptId(), sysParameterService
		//					//							.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code()));
		//					//					if (ListUtil.isNotEmpty(busiFzr)) {
		//					//						SimpleUserInfo user = busiFzr.get(0);
		//					//						order.setDirector(user.getUserName()); //总监
		//					//						order.setDirectorId(user.getUserId());
		//					//					}
		//					//					order.setIsDistribution(BooleanEnum.IS.code()); //已分配
		//					//					order.setStatus("on");
		//					//					
		//					//					//查询股权结构
		//					//					List<FProjectEquityStructureDO> sList = FProjectEquityStructureDAO
		//					//						.findByFormId(formInfo.getFormId());
		//					//					if (ListUtil.isNotEmpty(sList)) {
		//					//						List<CompanyOwnershipStructureInfo> crmStructureList = Lists.newArrayList();
		//					//						for (FProjectEquityStructureDO structure : sList) {
		//					//							CompanyOwnershipStructureInfo sInfo = new CompanyOwnershipStructureInfo();
		//					//							sInfo.setEquity(String.valueOf(structure.getEquityRatio()));//股权比例
		//					//							sInfo.setMethord(structure.getContributionWay()); //出资方式
		//					//							sInfo.setShareholdersName(structure.getStockholder()); //主要股东名称
		//					//							sInfo.setAmount(structure.getCapitalContributions()); //出资额（万元）
		//					//							crmStructureList.add(sInfo);
		//					//						}
		//					//						order.setCompanyOwnershipStructure(crmStructureList);
		//					//					}
		//					//					logger.info("新增企业客户到CRM {}", order);
		//					//					syncResult = companyCustomerServiceClient.add(order);
		//				}
		//			}
		//			
		//			logger.info("同步客户数据到CRM result：{}", syncResult);
		//			
		//			//			if (syncResult.isSuccess()) {
		//			//				//更新customerId
		//			//				long newCustomerId = syncResult.getKeyId();
		//			//				if (customerInfo.getCustomerId() == 0 && newCustomerId > 0) {
		//			//					customerInfo.setCustomerId(newCustomerId);
		//			//				}
		//			//			}
		//		} catch (Exception e) {
		//			logger.error("同步客户数据到CRM出错{}", e);
		//			throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAILURE, "同步到客户信息到客户系统出错");
		//		}
		//		return customerInfo;
	}
}
