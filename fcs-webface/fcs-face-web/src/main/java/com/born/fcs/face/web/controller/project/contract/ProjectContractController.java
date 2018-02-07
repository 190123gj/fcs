package com.born.fcs.face.web.controller.project.contract;

import java.io.*;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.pm.ws.enums.*;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.com.google.common.collect.Maps;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.bpm.service.client.user.SysUser;
import com.born.fcs.am.ws.info.pledgeasset.AssetSimpleInfo;
import com.born.fcs.am.ws.info.pledgetype.PledgeTypeSimpleInfo;
import com.born.fcs.am.ws.order.pledgeasset.AssetQueryOrder;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.handle.iWebOffice;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.face.web.util.UploadFileUtils;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.enums.managerbchange.ChangeManagerbPhaseEnum;
import com.born.fcs.pm.ws.info.basicmaintain.DecisionInstitutionInfo;
import com.born.fcs.pm.ws.info.basicmaintain.DecisionMemberInfo;
import com.born.fcs.pm.ws.info.common.BusiTypeInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectChannelRelationInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.info.contract.ContractTemplateInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractInvalidResultInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractItemInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractItemInvalidInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractResultInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContrctInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectPledgeAssetInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.ProjectCreditAssetAttachmentInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.ProjectCreditConditionInfo;
import com.born.fcs.pm.ws.info.stampapply.StampAFileInfo;
import com.born.fcs.pm.ws.order.common.ProjectChannelRelationQueryOrder;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.contract.ContractTemplateQueryOrder;
import com.born.fcs.pm.ws.order.contract.ProjectContractItemInvalidOrder;
import com.born.fcs.pm.ws.order.contract.ProjectContractItemOrder;
import com.born.fcs.pm.ws.order.contract.ProjectContractOrder;
import com.born.fcs.pm.ws.order.contract.ProjectContractQueryOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.ProjectCreditConditionQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.kinggrid.demo.web.iMsgServer2000;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

@Controller
@RequestMapping("projectMg/contract")
public class ProjectContractController extends WorkflowBaseController {
	final static String vm_path = "/projectMg/beforeLoanMg/contract/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "operateDate", "signedTime", "approvedTime", "courtRulingTime" };
	}
	
	/**
	 * 合同列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String projectContractList(ProjectContractQueryOrder order, Model model) {
		if(DataPermissionUtil.isLoanLeader()&&(order.getApplyType()==null||order.getApplyType()==ProjectContractTypeEnum.PROJECT_CONTRACT)){
			order.setLoginUserId(0);
			order.setDeptIdList(null);
		}else{
		setSessionLocalInfo2Order(order);
		}
		QueryBaseBatchResult<ProjectContractResultInfo> batchResult = projectContractServiceClient
			.query(order);
		model.addAttribute("conditions", order);
		model.addAttribute("contractStatus", ContractStatusEnum.getAllEnum());
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		if (order.getApplyType() == ProjectContractTypeEnum.PROJECT_WRIT) {
			return "/projectMg/assistSys/writ/list.vm";
		} else if (order.getApplyType() == ProjectContractTypeEnum.PROJECT_LETTER) {
			return "/projectMg/assistSys/letter/list.vm";
		} else if (order.getApplyType() == ProjectContractTypeEnum.PROJECT_DB_LETTER) {
			return "/projectMg/assistSys/dbLetter/list.vm";
		}
		return vm_path + "list.vm";
	}
	
	/**
	 * 新增合同
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("addProjectContract.htm")
	public String addProjectContract(String projectCode, String applyType, String id,
										String reApply, HttpServletRequest request, Model model) {
		if (applyType != null) {
			if (applyType.equals(ProjectContractTypeEnum.PROJECT_LETTER.code()))
				return addProjectLetter(projectCode, request, model);
			if (applyType.equals(ProjectContractTypeEnum.PROJECT_WRIT.code()))
				return addProjecWrit(projectCode, request, model);
			if (applyType.equals(ProjectContractTypeEnum.PROJECT_DB_LETTER.code()))
				return addProjectDBLetter(projectCode, request, model);
		}
		ProjectContractItemInfo contractInfo = null;
		if (null != id) {
			contractInfo = projectContractServiceClient.findContractItemById(Long.parseLong(id));
		}
		ProjectContrctInfo info = new ProjectContrctInfo();
		List<ProjectContractItemInfo> itemInfos = Lists.newArrayList();
		String ishaveMain = "NO";
		if (contractInfo != null) {
			ContractTemplateInfo templateInfo = contractTemplateServiceClient.findById(contractInfo
				.getTemplateId());
			if (templateInfo != null) {
				contractInfo.setCreditConditionType(templateInfo.getCreditConditionType());
				if (contractInfo.getIsMain() != null && contractInfo.getIsMain().equals("IS")) {
					ishaveMain = "IS";
				}
			}
			itemInfos.add(contractInfo);
		}
		//关联授信措施
		JSONArray guarantorJson = new JSONArray();
		JSONArray pledgeJson = new JSONArray();
		JSONArray mortgageJson = new JSONArray();
		JSONArray textJson = new JSONArray();
		List<ProjectCreditConditionInfo> creditConditions = projectCreditConditionServiceClient
			.findProjectCreditConditionByProjectCode(projectCode);
		List<ContractTemplateQueryOrder> couditionList = Lists.newArrayList();
		if (creditConditions != null) {
			for (ProjectCreditConditionInfo creditInfo : creditConditions) {
				ContractTemplateQueryOrder order = new ContractTemplateQueryOrder();
				JSONObject json = new JSONObject();
				json.put("code", creditInfo.getId());
				if (creditInfo.getItemDesc().startsWith("关键信息")) {
					json.put("txt", creditInfo.getItemDesc());
				} else {
					json.put("txt", "关键信息 ：" + getWarrantCode(creditInfo.getAssetId()) + "  "
									+ creditInfo.getItemDesc());
				}
				json.put("type", creditInfo.getType().message());
				if (creditInfo.getType() != null) {
					if (creditInfo.getType().code().equals("GUARANTOR")) {
						order.setCreditConditionType("保证");
						order.setPledgeType(null);
						boolean notHas = true;
						for (ContractTemplateQueryOrder order1 : couditionList) {
							if (order1.getCreditConditionType().equals("保证")) {
								notHas = false;
							}
						}
						if (notHas) {
							couditionList.add(order);
						}
					} else {
						FCouncilSummaryProjectPledgeAssetInfo pledgeInfo = councilSummaryServiceClient
							.queryPledgeAssetById(creditInfo.getItemId());
						if (pledgeInfo != null) {
							order.setCreditConditionType(pledgeInfo.getType().message());
							order.setPledgeType(pledgeInfo.getAssetType());
							boolean notHas = true;
							for (ContractTemplateQueryOrder order1 : couditionList) {
								if (StringUtil.equals(order1.getCreditConditionType(), pledgeInfo
									.getType().message())
									&& order1.getPledgeType().equals(pledgeInfo.getAssetType())) {
									notHas = false;
								}
							}
							if (notHas) {
								couditionList.add(order);
							}
						}
					}
				}
			}
			
		}
		
		ProjectSimpleDetailInfo projectInfo = new ProjectSimpleDetailInfo();
		BusiTypeInfo busiTypeInfo = new BusiTypeInfo();
		if ("IS".equals(reApply)) {
			projectInfo = projectServiceClient.querySimpleDetailInfo(projectCode);
			info.setProjectContractItemInfos(itemInfos);
			ishaveMain = "IS";
			for (ProjectContractItemInfo itemInfo : itemInfos) {
				if ("IS".equals(itemInfo.getIsMain())
					&& itemInfo.getContractStatus() != ContractStatusEnum.END) {
					ishaveMain = "NO";
				}
			}
		} else {
			if (null != projectCode) {
				projectInfo = projectServiceClient.querySimpleDetailInfo(projectCode);
				ContractTemplateQueryOrder queryOrder = new ContractTemplateQueryOrder();
				queryOrder.setBusiType(projectInfo.getBusiType());
				queryOrder.setStatus("IN_USE");
				queryOrder.setPageSize(9999L);
				queryOrder.setNeedNullPledgeType(null);
				//a、当前业务类型主合同；b、关联了当前业务类型，未关联反担保措施的合同；
				queryOrder.setIsGetTemplates("IS");
				QueryBaseBatchResult<ContractTemplateInfo> batchResult = contractTemplateServiceClient
					.query(queryOrder);
				queryOrder.setIsGetTemplates(null);
				List<ContractTemplateInfo> contractTemplateInfos = batchResult.getPageList();
				//c、关联了当前业务类型，反担保方式选择了抵押，押品选择了具体抵押质押物的合同
				if (couditionList.size() > 0) {
					for (ContractTemplateQueryOrder order : couditionList) {
						queryOrder.setCreditConditionType(order.getCreditConditionType());
						queryOrder.setPledgeType(order.getPledgeType());
						queryOrder.setNeedNullPledgeType(null);
						if (!"保证".equals(order.getCreditConditionType())) {
							queryOrder.setNeedNullPledgeType("IS");
						}
						batchResult = contractTemplateServiceClient.query(queryOrder);
						if (batchResult != null && batchResult.getPageList().size() > 0) {
							for (ContractTemplateInfo tempInfo : batchResult.getPageList()) {
								contractTemplateInfos.add(tempInfo);
							}
						}
					}
				}
				//List<ContractTemplateInfo> contractTemplateInfos = contractTemplateServiceClient
				//	.queryTemplates(projectInfo.getBusiType());
				if (contractTemplateInfos != null) {
					if (contractTemplateInfos.size() > 0) {
						for (ContractTemplateInfo templateInfo : contractTemplateInfos) {
							if (templateInfo.getIsMain() != null
								&& templateInfo.getIsMain().equals("IS")) {
								ishaveMain = "IS";
							}
							itemInfos.add(setContractTemplateInfo(templateInfo));
						}
					}
					if (!ishaveMain.equals("IS") && !projectInfo.getBusiType().equals("211")) {//没有主合同向上级查找并且不是诉讼类
						String busiType = projectInfo.getBusiType();
						if (busiType.length() > 1) {//不是顶级
							String type = busiType.substring(0, busiType.length() - 1);
							for (int i = 0; i < busiType.length() - 1; i++) {
								queryOrder.setBusiType(type);
								queryOrder.setStatus("IN_USE");
								queryOrder.setPageSize(9999L);
								queryOrder.setCreditConditionType(null);
								queryOrder.setPledgeType(null);
								queryOrder.setNeedNullPledgeType(null);
								//a、当前业务类型主合同；b、关联了当前业务类型，未关联反担保措施的合同；
								queryOrder.setIsGetTemplates("IS");
								batchResult = contractTemplateServiceClient.query(queryOrder);
								queryOrder.setIsGetTemplates(null);
								contractTemplateInfos = batchResult.getPageList();
								//c、关联了当前业务类型，反担保方式选择了抵押，押品选择了具体抵押质押物的合同
								if (couditionList.size() > 0) {
									for (ContractTemplateQueryOrder order : couditionList) {
										queryOrder.setCreditConditionType(order
											.getCreditConditionType());
										queryOrder.setPledgeType(order.getPledgeType());
										queryOrder.setNeedNullPledgeType(null);
										if (!"保证".equals(order.getCreditConditionType())) {
											queryOrder.setNeedNullPledgeType("IS");
										}
										batchResult = contractTemplateServiceClient
											.query(queryOrder);
										if (batchResult != null
											&& batchResult.getPageList().size() > 0) {
											for (ContractTemplateInfo tempInfo : batchResult
												.getPageList()) {
												contractTemplateInfos.add(tempInfo);
											}
										}
									}
								}
								//							contractTemplateInfos = contractTemplateServiceClient
								//								.queryTemplates(type);
								if (null != contractTemplateInfos
									&& contractTemplateInfos.size() > 0) {
									for (ContractTemplateInfo templateInfo : contractTemplateInfos) {
										if (templateInfo.getIsMain() != null
											&& templateInfo.getIsMain().equals("IS")) {
											ishaveMain = "IS";
										}
										itemInfos.add(setContractTemplateInfo(templateInfo));
									}
									break;
								}
								type = type.substring(0, type.length() - 1);
							}
						}
					}
					info.setProjectContractItemInfos(itemInfos);
				} else {//向上级查找
					if (!projectInfo.getBusiType().equals("211")) {//诉讼类不往上找
						String busiType = projectInfo.getBusiType();
						if (busiType.length() > 1) {//不是顶级
							String type = busiType.substring(0, busiType.length() - 1);
							for (int i = 0; i < busiType.length() - 1; i++) {
								queryOrder.setBusiType(type);
								queryOrder.setStatus("IN_USE");
								queryOrder.setPageSize(9999L);
								queryOrder.setCreditConditionType(null);
								queryOrder.setPledgeType(null);
								queryOrder.setNeedNullPledgeType(null);
								//a、当前业务类型主合同；b、关联了当前业务类型，未关联反担保措施的合同；
								queryOrder.setIsGetTemplates("IS");
								batchResult = contractTemplateServiceClient.query(queryOrder);
								contractTemplateInfos = batchResult.getPageList();
								queryOrder.setIsGetTemplates(null);
								//c、关联了当前业务类型，反担保方式选择了抵押，押品选择了具体抵押质押物的合同
								if (couditionList.size() > 0) {
									for (ContractTemplateQueryOrder order : couditionList) {
										queryOrder.setCreditConditionType(order
											.getCreditConditionType());
										queryOrder.setPledgeType(order.getPledgeType());
										queryOrder.setNeedNullPledgeType(null);
										if (!"保证".equals(order.getCreditConditionType())) {
											queryOrder.setNeedNullPledgeType("IS");
										}
										batchResult = contractTemplateServiceClient
											.query(queryOrder);
										if (batchResult != null
											&& batchResult.getPageList().size() > 0) {
											for (ContractTemplateInfo tempInfo : batchResult
												.getPageList()) {
												contractTemplateInfos.add(tempInfo);
											}
										}
									}
								}
								//							contractTemplateInfos = contractTemplateServiceClient
								//									.queryTemplates(type);
								if (contractTemplateInfos != null) {
									break;
								}
								type = type.substring(0, type.length() - 1);
							}
							if (null != contractTemplateInfos && contractTemplateInfos.size() > 0) {
								for (ContractTemplateInfo templateInfo : contractTemplateInfos) {
									if (templateInfo.getIsMain() != null
										&& templateInfo.getIsMain().equals("IS")) {
										ishaveMain = "IS";
									}
									itemInfos.add(setContractTemplateInfo(templateInfo));
								}
								info.setProjectContractItemInfos(itemInfos);
							}
						}
					}
				}
				
			}
		}
		if (projectCode != null) {
			//去重
			List<ProjectContractItemInfo> finalInfos = Lists.newArrayList();
			Map<Long, ProjectContractItemInfo> tempMap = new HashMap<Long, ProjectContractItemInfo>();
			if (itemInfos != null && itemInfos.size() > 0) {
				for (ProjectContractItemInfo tempInfo : itemInfos) {
					tempMap.put(tempInfo.getTemplateId(), tempInfo);
				}
			}
			ProjectContractQueryOrder order = new ProjectContractQueryOrder();
			order.setProjectCode(projectCode);
			order.setPageSize(9999l);
			order.setIsMain("IS");
			QueryBaseBatchResult<ProjectContractResultInfo> batchResult = projectContractServiceClient
				.query(order);
			if (!"IS".equals(reApply)) {
				if (tempMap != null && tempMap.size() > 0) {
					for (Long itemId : tempMap.keySet()) {
						if (null != projectCode) {
							//多次申请去掉主合同 排除作废状态的
							boolean hasMainFlag = false;
							if (ListUtil.isNotEmpty(batchResult.getPageList())) {
								for (ProjectContractResultInfo nof : batchResult.getPageList()) {
									if (nof.getContractStatus() != ContractStatusEnum.END) {
										hasMainFlag = true;
									}
								}
							}
							if (hasMainFlag) {//去掉主合同
								ProjectContractItemInfo itemInfo = tempMap.get(itemId);
								if (itemInfo.getIsMain().equals("IS")) {
									continue;
								}
							}
						}
						finalInfos.add(tempMap.get(itemId));
					}
				}
				if (batchResult != null && batchResult.getPageList().size() > 0) {
					ishaveMain = "IS";
				}
			}
			//2次申请关联过的授信措施不带出来
			order.setIsMain(null);
			QueryBaseBatchResult<ProjectContractResultInfo> batchResultAll = projectContractServiceClient
				.query(order);
			//			String applyFormIds = "";//关联过的签报formId;
			//			for (ProjectContractResultInfo itemInfo : batchResultAll.getPageList()) {
			//				if (StringUtil.isNotEmpty(itemInfo.getBasisOfDecision())) {
			//					applyFormIds = applyFormIds + itemInfo.getBasisOfDecision() + ",";
			//				}
			//			}
			//			if (applyFormIds.length() > 0) {
			//				applyFormIds = applyFormIds.substring(0, applyFormIds.length() - 1);
			//			}
			//			model.addAttribute("applyFormIds", applyFormIds);
			//合同类函关联过的
			order.setApplyType(ProjectContractTypeEnum.PROJECT_LETTER);
			order.setLetterType(LetterTypeEnum.CONTRACT);
			QueryBaseBatchResult<ProjectContractResultInfo> batchResult_contractLetter = projectContractServiceClient
				.query(order);
			if (creditConditions != null) {
				guarantorJson = new JSONArray();//保证
				pledgeJson = new JSONArray();
				mortgageJson = new JSONArray();
				for (ProjectCreditConditionInfo creditInfo : creditConditions) {//合同申请关联过的授信措施
					boolean ishave = false;
					if (batchResultAll != null && batchResultAll.getPageList().size() > 0) {
						for (ProjectContractResultInfo itemInfo : batchResultAll.getPageList()) {
							if (StringUtil.isNotEmpty(itemInfo.getCreditMeasure())
								&& itemInfo.getContractStatus() != ContractStatusEnum.END) {
								boolean isReApply = false;
								if ("IS".equals(reApply)) {
									if (Long.toString(itemInfo.getId()).equals(id))
										isReApply = true;
								}
								if (!isReApply) {
									String creditIds[] = itemInfo.getCreditMeasure().split(",");
									for (String creditId : creditIds) {
										if (StringUtil.isNotEmpty(creditId)
											&& creditInfo.getId() == Long.parseLong(creditId)) {
											ishave = true;
										}
									}
								}
								
							}
						}
					}
					if (batchResult_contractLetter != null
						&& batchResult_contractLetter.getPageList().size() > 0) {//合同类函关联过的授信措施
						for (ProjectContractResultInfo itemInfo : batchResult_contractLetter
							.getPageList()) {
							if (StringUtil.isNotEmpty(itemInfo.getCreditMeasure())
								&& itemInfo.getContractStatus() != ContractStatusEnum.END) {
								String creditIds[] = itemInfo.getCreditMeasure().split(",");
								for (String creditId : creditIds) {
									if (StringUtil.isNotEmpty(creditId)
										&& creditInfo.getId() == Long.parseLong(creditId)) {
										ishave = true;
									}
								}
								
							}
						}
					}
					if (!ishave) {
						JSONObject json = new JSONObject();
						json.put("code", creditInfo.getId());
						if (creditInfo.getItemDesc().startsWith("关键信息")) {
							json.put("txt", creditInfo.getItemDesc());
						} else {
							json.put("txt", "关键信息 ：" + getWarrantCode(creditInfo.getAssetId())
											+ "  " + creditInfo.getItemDesc());
						}
						json.put("type", creditInfo.getType().message());
						json.put("warrantCode", getWarrantCode(creditInfo.getAssetId()));
						if (creditInfo.getType() != null) {
							if (creditInfo.getType().code().equals("GUARANTOR")) {
								guarantorJson.add(json);
							} else if (creditInfo.getType().code().equals("PLEDGE")) {
								pledgeJson.add(json);
							} else if (creditInfo.getType().code().equals("TEXT")) {
								textJson.add(json);
							} else {
								mortgageJson.add(json);
							}
						}
					}
				}
			}
			boolean haveNotFree = false;//有非自由流程的单子必定有主合同
			//判断是否一次申请，是否有非自由流程的单子
			if (batchResultAll != null && batchResultAll.getPageList().size() > 0) {
				for (ProjectContractResultInfo itemInfo : batchResultAll.getPageList()) {
					if (itemInfo.getFreeFlow() == null || (!"IS".equals(itemInfo.getFreeFlow()))) {
						haveNotFree = true;
						break;
					}
				}
				//不是第一次申请且没有主合同 针对自由流程，非自由流程第一次必定有主合同 并且本次自动带出来的没有主合同
				if (ListUtil.isEmpty(batchResult.getPageList()) && haveNotFree) {
					boolean thisHaveMain = false;
					if (finalInfos != null) {
						for (ProjectContractItemInfo contractItemInfo : finalInfos) {
							if ("IS".equals(contractItemInfo.getIsMain())) {
								thisHaveMain = true;
								break;
							}
						}
					}
					if (!thisHaveMain) {
						ishaveMain = "NO";
					}
				}
			} else {
				model.addAttribute("isFirst", "IS");
			}
			if (!"IS".equals(reApply)) {
				info.setProjectContractItemInfos(finalInfos);
			}
		}
		info.setApplyType(ProjectContractTypeEnum.PROJECT_CONTRACT);
		model.addAttribute("reApply", reApply);
		model.addAttribute("pledgeType", getPledgeType());
		model.addAttribute("guarantorJson", guarantorJson.toJSONString().replaceAll("\"", "'"));
		//前端取抵质押反了
		model.addAttribute("mortgageJson", pledgeJson.toJSONString().replaceAll("\"", "'"));
		model.addAttribute("pledgeJson", mortgageJson.toJSONString().replaceAll("\"", "'"));
		model.addAttribute("textJson", textJson.toJSONString().replaceAll("\"", "'"));
		model.addAttribute("busiTypeCode", busiTypeInfo.getCode());
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("info", info);
		model.addAttribute("ishaveMain", ishaveMain);
		model.addAttribute("ishaveApproval", isHaveApproval(projectInfo));
		model.addAttribute("requestUrl", getRequestUrl(request));
		return vm_path + "add_contract.vm";
	}
	
	public ProjectContractItemInfo setContractTemplateInfo(ContractTemplateInfo templateInfo) {
		ProjectContractItemInfo contractItemInfo = new ProjectContractItemInfo();
		contractItemInfo.setTemplateId(templateInfo.getTemplateId());
		contractItemInfo.setContractName(templateInfo.getName());
		contractItemInfo.setIsMain(templateInfo.getIsMain());
		contractItemInfo.setStampPhase(templateInfo.getStampPhase());
		if (templateInfo.getContractType() == ContractTemplateTypeEnum.STANDARD) {
			contractItemInfo.setContractType(ContractTypeEnum.STANDARD);
		} else {
			contractItemInfo.setContractType(ContractTypeEnum.NOTSTANDARD);
			
		}
		contractItemInfo.setTemplateFileUrl(templateInfo.getTemplateFile());
		contractItemInfo.setCreditConditionType(templateInfo.getCreditConditionType());
		contractItemInfo.setPledgeType(templateInfo.getPledgeType());
		contractItemInfo.setBasisOfDecision(null);
		contractItemInfo.setDecisionType(BasisOfDecisionEnum.PROJECT_APPROVAL);
		return contractItemInfo;
	}
	
	private String addProjectLetter(String projectCode, HttpServletRequest request, Model model) {
		ProjectContrctInfo info = new ProjectContrctInfo();
		info.setApplyType(ProjectContractTypeEnum.PROJECT_LETTER);
		if (projectCode != null) {
			ProjectSimpleDetailInfo projectInfo = projectServiceClient
				.querySimpleDetailInfo(projectCode);
			//关联授信措施
			JSONArray guarantorJson = new JSONArray();
			JSONArray pledgeJson = new JSONArray();
			JSONArray mortgageJson = new JSONArray();
			JSONArray textJson = new JSONArray();
			List<ProjectCreditConditionInfo> creditConditions = projectCreditConditionServiceClient
				.findProjectCreditConditionByProjectCode(projectCode);
			//关联过的授信措施不带出来
			ProjectContractQueryOrder order = new ProjectContractQueryOrder();
			order.setProjectCode(projectCode);
			order.setPageSize(9999l);
			QueryBaseBatchResult<ProjectContractResultInfo> batchResultAll = projectContractServiceClient
				.query(order);
			order.setApplyType(ProjectContractTypeEnum.PROJECT_LETTER);
			order.setLetterType(LetterTypeEnum.CONTRACT);
			QueryBaseBatchResult<ProjectContractResultInfo> batchResult_contractLetter = projectContractServiceClient
				.query(order);
			if (creditConditions != null) {
				guarantorJson = new JSONArray();
				pledgeJson = new JSONArray();
				mortgageJson = new JSONArray();
				for (ProjectCreditConditionInfo creditInfo : creditConditions) {//合同申请关联过的授信措施
					boolean ishave = false;
					if (batchResultAll != null && batchResultAll.getPageList().size() > 0) {
						for (ProjectContractResultInfo itemInfo : batchResultAll.getPageList()) {
							if (StringUtil.isNotEmpty(itemInfo.getCreditMeasure())
								&& itemInfo.getContractStatus() != ContractStatusEnum.END) {
								String creditIds[] = itemInfo.getCreditMeasure().split(",");
								for (String creditId : creditIds) {
									if (StringUtil.isNotEmpty(creditId)
										&& creditInfo.getId() == Long.parseLong(creditId)) {
										ishave = true;
									}
								}
								
							}
						}
					}
					if (batchResult_contractLetter != null
						&& batchResult_contractLetter.getPageList().size() > 0) {//合同类函关联过的授信措施
						for (ProjectContractResultInfo itemInfo : batchResult_contractLetter
							.getPageList()) {
							if (StringUtil.isNotEmpty(itemInfo.getCreditMeasure())
								&& itemInfo.getContractStatus() != ContractStatusEnum.END) {
								String creditIds[] = itemInfo.getCreditMeasure().split(",");
								for (String creditId : creditIds) {
									if (StringUtil.isNotEmpty(creditId)
										&& creditInfo.getId() == Long.parseLong(creditId)) {
										ishave = true;
									}
								}
								
							}
						}
					}
					if (!ishave) {
						JSONObject json = new JSONObject();
						json.put("code", creditInfo.getId());
						if (creditInfo.getItemDesc().startsWith("关键信息")) {
							json.put("txt", creditInfo.getItemDesc());
						} else {
							json.put("txt", "关键信息 ：" + getWarrantCode(creditInfo.getAssetId())
											+ "  " + creditInfo.getItemDesc());
						}
						json.put("type", creditInfo.getType().message());
						json.put("warrantCode", getWarrantCode(creditInfo.getAssetId()));
						if (creditInfo.getType() != null) {
							if (creditInfo.getType().code().equals("GUARANTOR")) {
								guarantorJson.add(json);
							} else if (creditInfo.getType().code().equals("PLEDGE")) {
								pledgeJson.add(json);
							} else if (creditInfo.getType().code().equals("TEXT")) {
								textJson.add(json);
							} else {
								mortgageJson.add(json);
							}
						}
					}
				}
			}
			
			//前端取抵质押反了
			model.addAttribute("mortgageJson", pledgeJson.toJSONString().replaceAll("\"", "'"));
			model.addAttribute("pledgeJson", mortgageJson.toJSONString().replaceAll("\"", "'"));
			model.addAttribute("guarantorJson", guarantorJson.toJSONString().replaceAll("\"", "'"));
			model.addAttribute("textJson", textJson.toJSONString().replaceAll("\"", "'"));
			model.addAttribute("projectInfo", projectInfo);
			model.addAttribute("letterTypes", LetterTypeEnum.getAllEnum());
			model.addAttribute("ishaveApproval", isHaveApproval(projectInfo));
			model.addAttribute("requestUrl", getRequestUrl(request));
			model.addAttribute("canAddOtherLetter", canAddOtherLetter(projectCode));
		}
		model.addAttribute("info", info);
		model.addAttribute("pledgeType", getPledgeType());
		return "/projectMg/assistSys/letter/addLetter.vm";
	}
	
	private String addProjectDBLetter(String projectCode, HttpServletRequest request, Model model) {
		ProjectContrctInfo info = new ProjectContrctInfo();
		info.setApplyType(ProjectContractTypeEnum.PROJECT_DB_LETTER);
		if (projectCode != null) {
			ProjectSimpleDetailInfo projectInfo = projectServiceClient
				.querySimpleDetailInfo(projectCode);
			model.addAttribute("projectInfo", projectInfo);
			model.addAttribute("letterTypes", LetterTypeEnum.getAllEnum());
			model.addAttribute("ishaveApproval", isHaveApproval(projectInfo));
			model.addAttribute("requestUrl", getRequestUrl(request));
			model.addAttribute("canAddOtherLetter", "IS");
			ProjectCreditConditionQueryOrder queryOrder = new ProjectCreditConditionQueryOrder();
			queryOrder.setProjectCode(projectCode);
			creditPageList(queryOrder, request, model);
			
			//重新授信的不显示授信落实
			if (!model.containsAttribute("hasCreditConditon")) {
				model.addAttribute("isRedoProject",
					projectInfo.getIsRedoProject() == BooleanEnum.IS);
				showRedoSummary(projectCode, model);
			}
		}
		model.addAttribute("info", info);
		model.addAttribute("pledgeType", getPledgeType());
		return "/projectMg/assistSys/dbLetter/addLetter.vm";
	}
	
	private String addProjecWrit(String projectCode, HttpServletRequest request, Model model) {
		ProjectSimpleDetailInfo projectInfo = projectServiceClient
			.querySimpleDetailInfo(projectCode);
		ProjectContrctInfo info = new ProjectContrctInfo();
		info.setApplyType(ProjectContractTypeEnum.PROJECT_WRIT);
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("info", info);
		model.addAttribute("ishaveApproval", isHaveApproval(projectInfo));
		model.addAttribute("requestUrl", getRequestUrl(request));
		return "/projectMg/assistSys/writ/addWrit.vm";
	}
	
	/**
	 * 异步分页加载授信条件
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("creditPageList.json")
	public String creditPageList(ProjectCreditConditionQueryOrder order,
									HttpServletRequest request, Model model) {
		if (order != null && com.born.fcs.pm.util.StringUtil.isNotEmpty(order.getProjectCode())) {
			QueryBaseBatchResult<ProjectCreditConditionInfo> batchResult = projectCreditConditionServiceClient
				.queryCreditCondition(order);
			if (batchResult != null && batchResult.getTotalCount() > 0) {
				for (ProjectCreditConditionInfo condition : batchResult.getPageList()) {
					List<ProjectCreditAssetAttachmentInfo> listAttachmentInfo = projectCreditConditionServiceClient
						.findByCreditId(condition.getId());
					condition.setListAttachmentInfos(listAttachmentInfo);
				}
				model.addAttribute("hasCreditConditon", true);
				model.addAttribute("creditCondition", PageUtil.getCovertPage(batchResult));
			}
			model.addAttribute("projectCode", order.getProjectCode());
		}
		model.addAttribute("conditions", order);
		return "/projectMg/cashMg/putInMoney/creditPageList.vm";
	}
	
	/**
	 * 保存合同
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveProjectContract.htm")
	@ResponseBody
	public JSONObject saveProjectContract(ProjectContractOrder order, HttpServletRequest request,
											Model model) {
		String tipPrefix = " 保存合同";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			ProjectInfo projectInfo = projectServiceClient.queryByCode(order.getProjectCode(),
				false);
			if (projectInfo.getStatus() == ProjectStatusEnum.PAUSE) {
				jsonObject.put("success", false);
				jsonObject.put("message", "该项目已被暂缓，不允许保存！");
				logger.error("保存合同出错", "该项目已被暂缓，不允许保存！");
				return jsonObject;
			}
			// 初始化Form验证信息
			//order.setCheckStatus(0);
			order.setRelatedProjectCode(order.getProjectCode());
			order.setCheckIndex(0);
			if (order.getApplyType() == ProjectContractTypeEnum.PROJECT_WRIT) {//文书
				order.setFormCode(FormCodeEnum.PROJECT_WRIT);
			} else if (order.getApplyType() == ProjectContractTypeEnum.PROJECT_LETTER) {//函
				order.setFormCode(FormCodeEnum.PROJECT_LETTER);
			} else if (order.getApplyType() == ProjectContractTypeEnum.PROJECT_DB_LETTER) {//函
				order.setFormCode(FormCodeEnum.PROJECT_DB_LETTER);
			} else {//合同
				order.setFormCode(FormCodeEnum.PROJECT_CONTRACT);
			}
			order.setDeptCode(sessionLocal.getUserInfo().getDept().getCode());
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			FormBaseResult saveResult = projectContractServiceClient.save(order);
			jsonObject = toJSONResult(jsonObject, saveResult, "保存合同成功", null);
			jsonObject.put("formId", saveResult.getFormInfo().getFormId());
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存合同出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 修改份数
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("modfiyCnt.json")
	@ResponseBody
	public JSONObject modfiyCnt(ProjectContractOrder order, HttpServletRequest request, Model model) {
		String tipPrefix = " 修改份数";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			
			FcsBaseResult saveResult = projectContractServiceClient.modfiyCnt(order);
			jsonObject = toJSONResult(jsonObject, saveResult, "修改份数成功", null);
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("修改份数出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 合同详情
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("viewProjectContract.htm")
	public String viewProject(Long id, Model model) {
		String tipPrefix = "合同详情";
		try {
			ProjectContrctInfo info = projectContractServiceClient.findById(id);
			model.addAttribute("info", info);
			
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		return vm_path + "viewContract.vm";
	}
	
	@RequestMapping("viewAudit.htm")
	public String viewAudit(@RequestParam(value = "formId", required = false, defaultValue = "0") long formId,
							@RequestParam(value = "contractNo", required = false, defaultValue = "") String contractNo,
							HttpServletRequest request, Model model, HttpSession session) {
		if (StringUtil.isNotBlank(contractNo)) {
			ProjectContractItemInfo itemInfo = projectContractServiceClient
				.findByContractCode(contractNo);
			ProjectContrctInfo contrctInfo = projectContractServiceClient.findById(itemInfo
				.getContractId());
			formId = contrctInfo.getFormId();
		}
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		ProjectContrctInfo info = projectContractServiceClient.findByFormId(formId);
		ProjectSimpleDetailInfo projectInfo = projectServiceClient.querySimpleDetailInfo(info
			.getProjectCode());
		List<ProjectContractItemInfo> projectContractItemInfoList = Lists.newArrayList();
		if (info.getProjectContractItemInfos().size() > 0) {
			ProjectContractItemInfo contractItemInfo = new ProjectContractItemInfo();
			for (ProjectContractItemInfo itemInfo : info.getProjectContractItemInfos()) {
				contractItemInfo = itemInfo;
				ContractTemplateInfo templateInfo = contractTemplateServiceClient.findById(itemInfo
					.getTemplateId());
				if (templateInfo != null && null != templateInfo.getCreditConditionType()) {
					contractItemInfo.setCreditConditionType(templateInfo.getCreditConditionType());
				}
				projectContractItemInfoList.add(contractItemInfo);
			}
			info.setProjectContractItemInfos(projectContractItemInfoList);
		}
		setAuditHistory2Page(form, model);
		model.addAttribute("info", info);
		model.addAttribute("form", form);// 表单信息
		model.addAttribute("formCode", form.getFormCode());
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("ishaveApproval", isHaveApproval(projectInfo));
		model.addAttribute("requestUrl", getRequestUrl(request));
		if (info.getApplyType() == ProjectContractTypeEnum.PROJECT_WRIT) {
			return "/projectMg/assistSys/writ/viewAudit.vm";
		} else if (info.getApplyType() == ProjectContractTypeEnum.PROJECT_LETTER) {
			return "/projectMg/assistSys/letter/viewAudit.vm";
		} else if (info.getApplyType() == ProjectContractTypeEnum.PROJECT_DB_LETTER) {
			//重新授信的不显示授信落实
			ProjectCreditConditionQueryOrder queryOrder = new ProjectCreditConditionQueryOrder();
			queryOrder.setProjectCode(info.getProjectCode());
			creditPageList(queryOrder, request, model);
			if (!model.containsAttribute("hasCreditConditon")) {
				model.addAttribute("isRedoProject",
					projectInfo.getIsRedoProject() == BooleanEnum.IS);
				showRedoSummary(info.getProjectCode(), model);
			}
			return "/projectMg/assistSys/dbLetter/viewAudit.vm";
		}
		return vm_path + "viewContract.vm";
	}
	
	/**
	 * 合同回传
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveContractBack.htm")
	@ResponseBody
	public JSONObject saveContractBack(ProjectContractItemOrder order, Model model) {
		String tipPrefix = " 合同回传";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			setSessionLocalInfo2Order(order);
			ProjectContractItemInfo itemInfo = projectContractServiceClient
				.findContractItemById(order.getId());
			ProjectContrctInfo contractInfo = projectContractServiceClient.findById(itemInfo
				.getContractId());
			ProjectInfo projectInfo = projectServiceClient.queryByCode(
				contractInfo.getProjectCode(), false);
			if (projectInfo.getStatus() == ProjectStatusEnum.PAUSE) {
				jsonObject.put("success", false);
				jsonObject.put("message", "该项目已被暂缓，不允许回传！");
				logger.error("合同回传出错", "该项目已被暂缓，不允许回传！");
				return jsonObject;
			}
			// 初始化Form验证信息
			FcsBaseResult saveResult = projectContractServiceClient.saveContractBack(order);
			jsonObject = toJSONResult(jsonObject, saveResult, "合同回传成功", null);
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("合同回传出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 上传法院裁定书
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("addCourtRuling.htm")
	public String addCourtRuling(long id, Model model) {
		ProjectContractItemInfo info = projectContractServiceClient.findContractItemById(id);
		model.addAttribute("info", info);
		return "/projectMg/assistSys/letter/courtOrders.vm";
	}
	
	/**
	 * 保存法院裁定书
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveCourtRuling.htm")
	@ResponseBody
	public JSONObject saveCourtRuling(ProjectContractItemOrder order, Model model) {
		String tipPrefix = " 上传法院裁定书";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			setSessionLocalInfo2Order(order);
			ProjectContractItemInfo itemInfo = projectContractServiceClient
				.findContractItemById(order.getId());
			ProjectContrctInfo contractInfo = projectContractServiceClient.findById(itemInfo
				.getContractId());
			ProjectInfo projectInfo = projectServiceClient.queryByCode(
				contractInfo.getProjectCode(), false);
			if (projectInfo.getStatus() == ProjectStatusEnum.PAUSE) {
				jsonObject.put("success", false);
				jsonObject.put("message", "该项目已被暂缓，不允许上传！");
				logger.error("上传法院裁定书出错", "该项目已被暂缓，不允许上传！");
				return jsonObject;
			}
			// 初始化Form验证信息
			FcsBaseResult saveResult = projectContractServiceClient.saveCourtRuling(order);
			jsonObject = toJSONResult(jsonObject, saveResult, "上传法院裁定书成功", null);
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("合同回传出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 合同审核
	 * 
	 * @param formId
	 * @param model
	 * @return
	 */
	
	@RequestMapping("audit.htm")
	public String audit(long formId, HttpServletRequest request, Model model, HttpSession session) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		ProjectContrctInfo info = projectContractServiceClient.findByFormId(formId);
		ProjectSimpleDetailInfo projectInfo = projectServiceClient.querySimpleDetailInfo(info
			.getProjectCode());
		List<ProjectContractItemInfo> projectContractItemInfoList = Lists.newArrayList();
		if (info.getProjectContractItemInfos().size() > 0) {
			ProjectContractItemInfo contractItemInfo = new ProjectContractItemInfo();
			for (ProjectContractItemInfo itemInfo : info.getProjectContractItemInfos()) {
				contractItemInfo = itemInfo;
				ContractTemplateInfo templateInfo = contractTemplateServiceClient.findById(itemInfo
					.getTemplateId());
				if (templateInfo != null && null != templateInfo.getCreditConditionType()) {
					contractItemInfo.setCreditConditionType(templateInfo.getCreditConditionType());
				}
				projectContractItemInfoList.add(contractItemInfo);
			}
			info.setProjectContractItemInfos(projectContractItemInfoList);
		}
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("info", info);
		model.addAttribute("form", form);// 表单信息
		model.addAttribute("formCode", form.getFormCode());
		model.addAttribute("needLegalManager", "NO");
		model.addAttribute("ishaveApproval", isHaveApproval(projectInfo));
		initWorkflow(model, form, request.getParameter("taskId"));
		model.addAttribute("isChecker", true);
		model.addAttribute("requestUrl", getRequestUrl(request));
		if (info.getApplyType() == ProjectContractTypeEnum.PROJECT_WRIT) {
			return "/projectMg/assistSys/writ/viewAudit.vm";
		} else if (info.getApplyType() == ProjectContractTypeEnum.PROJECT_LETTER) {
			return "/projectMg/assistSys/letter/viewAudit.vm";
		} else if (info.getApplyType() == ProjectContractTypeEnum.PROJECT_DB_LETTER) {
			ProjectCreditConditionQueryOrder queryOrder = new ProjectCreditConditionQueryOrder();
			queryOrder.setProjectCode(info.getProjectCode());
			creditPageList(queryOrder, request, model);
			if (!model.containsAttribute("hasCreditConditon")) {
				model.addAttribute("isRedoProject",
					projectInfo.getIsRedoProject() == BooleanEnum.IS);
				showRedoSummary(info.getProjectCode(), model);
			}
			return "/projectMg/assistSys/dbLetter/viewAudit.vm";
		}
		return vm_path + "auditContract.vm";
	}
	
	/**
	 * 法务部合同审核能修改份数
	 * 
	 * @param formId
	 * @param model
	 * @return
	 */
	
	@RequestMapping("auditAndModfiyCnt.htm")
	public String auditAndModfiyCnt(long formId, HttpServletRequest request, Model model,
									HttpSession session) {
		model.addAttribute("modfiyCnt", true);
		return audit(formId, request, model, session);
	}
	
	/**
	 * 选择法务人员--合同审核
	 * 
	 * @param formId
	 * @param model
	 * @return
	 */
	
	@RequestMapping("auditChooseLegalManager.htm")
	public String auditChooseLegalManager(long formId, HttpServletRequest request, Model model,
											HttpSession session) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		ProjectContrctInfo info = projectContractServiceClient.findByFormId(formId);
		ProjectSimpleDetailInfo projectInfo = projectServiceClient.querySimpleDetailInfo(info
			.getProjectCode());
		List<ProjectContractItemInfo> projectContractItemInfoList = Lists.newArrayList();
		if (info.getProjectContractItemInfos().size() > 0) {
			ProjectContractItemInfo contractItemInfo = new ProjectContractItemInfo();
			for (ProjectContractItemInfo itemInfo : info.getProjectContractItemInfos()) {
				contractItemInfo = itemInfo;
				ContractTemplateInfo templateInfo = contractTemplateServiceClient.findById(itemInfo
					.getTemplateId());
				if (templateInfo != null && null != templateInfo.getCreditConditionType()) {
					contractItemInfo.setCreditConditionType(templateInfo.getCreditConditionType());
				}
				projectContractItemInfoList.add(contractItemInfo);
			}
			info.setProjectContractItemInfos(projectContractItemInfoList);
		}
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("info", info);
		model.addAttribute("form", form);// 表单信息
		model.addAttribute("formCode", form.getFormCode());
		model.addAttribute("needLegalManager", "IS");
		initWorkflow(model, form, request.getParameter("taskId"));
		model.addAttribute("ishaveApproval", isHaveApproval(projectInfo));
		model.addAttribute("requestUrl", getRequestUrl(request));
		model.addAttribute("isChecker", true);
		if (info.getApplyType() == ProjectContractTypeEnum.PROJECT_WRIT) {
			return "/projectMg/assistSys/writ/viewAudit.vm";
		} else if (info.getApplyType() == ProjectContractTypeEnum.PROJECT_LETTER) {
			return "/projectMg/assistSys/letter/viewAudit.vm";
		} else if (info.getApplyType() == ProjectContractTypeEnum.PROJECT_DB_LETTER) {
			ProjectCreditConditionQueryOrder queryOrder = new ProjectCreditConditionQueryOrder();
			queryOrder.setProjectCode(info.getProjectCode());
			creditPageList(queryOrder, request, model);
			if (!model.containsAttribute("hasCreditConditon")) {
				model.addAttribute("isRedoProject",
					projectInfo.getIsRedoProject() == BooleanEnum.IS);
				showRedoSummary(info.getProjectCode(), model);
			}
			return "/projectMg/assistSys/dbLetter/viewAudit.vm";
		}
		//modfiyCnt=true 能修改份数
		model.addAttribute("modfiyCnt", true);
		return vm_path + "auditContract.vm";
	}
	
	/**
	 * 新增合同回传
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("addBackContract.htm")
	public String addBackContract(long id, Model model) {
		ProjectContractItemInfo info = projectContractServiceClient.findContractItemById(id);
		ProjectContrctInfo contrctInfo = projectContractServiceClient
			.findById(info.getContractId());
		ProjectInfo projectInfo = projectServiceClient.queryByCode(contrctInfo.getProjectCode(),
			false);
		ProjectRelatedUserInfo managerB = projectRelatedUserServiceClient.getBusiManagerbByPhase(
			projectInfo.getProjectCode(), ChangeManagerbPhaseEnum.CONTRACT_PHASES);
		
		if (info.getSignedTime() == null || info.getSignPersonA() == null) {
			info.setSignPersonAId(projectInfo.getBusiManagerId());
			info.setSignPersonA(projectInfo.getBusiManagerName());
			info.setSignPersonBId(managerB.getUserId() + "");
			info.setSignPersonB(managerB.getUserName());
		}
		//银团类项目资金渠道
		if ("11".equals(projectInfo.getBusiType())) {
			ProjectChannelRelationQueryOrder queryOrder = new ProjectChannelRelationQueryOrder();
			queryOrder.setProjectCode(projectInfo.getProjectCode());
			queryOrder.setPhases(ProjectPhasesEnum.COUNCIL_PHASES);
			queryOrder.setChannelRelation(ChannelRelationEnum.CAPITAL_CHANNEL);
			QueryBaseBatchResult<ProjectChannelRelationInfo> baseBatchResult = projectChannelRelationServiceClient
				.query(queryOrder);
			if (baseBatchResult != null && ListUtil.isNotEmpty(baseBatchResult.getPageList())) {
				info.setChannels(baseBatchResult.getPageList());
			}
		}
		model.addAttribute("isBond", ProjectUtil.isBond(projectInfo.getBusiType()));
		model.addAttribute("info", info);
		model.addAttribute("contrctInfo", contrctInfo);
		model.addAttribute("consortiumbank", "11".equals(projectInfo.getBusiType()));
		return vm_path + "passBackContract.vm";
	}
	
	/**
	 * 合同确认
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("contractConfirm.htm")
	@ResponseBody
	public JSONObject contractConfirm(ProjectContractItemOrder order, String projectCode,
										Model model) {
		String tipPrefix = " 合同确认";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			order.setContractStatus(ContractStatusEnum.CONFIRMED.code());
			FcsBaseResult saveResult = projectContractServiceClient.updateContractStatus(order);
			//jsonObject = toJSONResult(jsonObject, saveResult, "合同确认成功", null);
			if(saveResult.isSuccess()==true){
				jsonObject.put("success", true);
				StringBuffer strMessage=new StringBuffer("确认成功");
				if(StringUtil.equals("IS",order.getIsContract())){
					ProjectRelatedUserInfo signPos=DataPermissionUtil.isSignPos(projectCode);
					ProjectRelatedUserInfo pledgePos=DataPermissionUtil.isSignPos(projectCode);
					if(signPos.getUserId()==0||pledgePos.getUserId()==0){
					//放款中心负责人
						String loanLeaderRoleName = sysParameterServiceClient
								.getSysParameterValue(SysParamEnum.SYS_PARAM_LOAN_LEADER_ROLE_NAME
										.code());
						List<SysUser> loanLeaders = bpmUserQueryService
								.findUserByRoleAlias(loanLeaderRoleName);
						if(loanLeaders.size()>0){
							strMessage.append(",由放款中心负责人["+loanLeaders.get(0).getFullname()+"]指定面签岗和抵质押岗!");
						}
					}
				}
				jsonObject.put("message", strMessage.toString());
			}



			//检查项目合同是否确认完
			long count = projectContractServiceClient.checkISConfirmAll(projectCode);
			if (count > 0) {
				jsonObject.put("isConfirmAll", false);
			} else {
				List<ProjectContractResultInfo> contractInfo = projectContractServiceClient
					.searchIsConfirmAll(projectCode);
				if (contractInfo != null && contractInfo.size() > 0) {
					jsonObject.put("contractInfo", contractInfo);
					jsonObject.put("isConfirmAll", true);
				} else {
					jsonObject.put("isConfirmAll", false);
				}
			}

		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("合同确认出错", e);
		}
		return jsonObject;
	}
	
	/**
	 * 合同作废
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("contractInvalid.htm")
	@ResponseBody
	public JSONObject contractInvalid(ProjectContractItemOrder order, Model model) {
		String tipPrefix = " 合同作废";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			order.setContractStatus(ContractStatusEnum.INVALID.code());
			FcsBaseResult saveResult = projectContractServiceClient.updateContractStatus(order);
			jsonObject = toJSONResult(jsonObject, saveResult, "合同作废成功", null);
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("合同作废出错", e);
		}
		return jsonObject;
	}
	
	/**
	 * 编辑合同
	 * 
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping("editContract.htm")
	public String editContract(Long formId, Model model, HttpServletRequest request) {
		String tipPrefix = "编辑合同";
		try {
			FormInfo form = formServiceClient.findByFormId(formId);
			ProjectContrctInfo info = projectContractServiceClient.findByFormId(formId);
			ProjectSimpleDetailInfo projectInfo = projectServiceClient.querySimpleDetailInfo(info
				.getProjectCode());
			List<ProjectContractItemInfo> projectContractItemInfoList = Lists.newArrayList();
			String ishaveMain = "NO";
			if (info.getProjectContractItemInfos().size() > 0) {
				ProjectContractItemInfo contractItemInfo = new ProjectContractItemInfo();
				for (ProjectContractItemInfo itemInfo : info.getProjectContractItemInfos()) {
					contractItemInfo = itemInfo;
					ContractTemplateInfo templateInfo = contractTemplateServiceClient
						.findById(itemInfo.getTemplateId());
					if (templateInfo != null && null != templateInfo.getCreditConditionType()) {
						contractItemInfo.setCreditConditionType(templateInfo
							.getCreditConditionType());
					}
					projectContractItemInfoList.add(contractItemInfo);
					if (contractItemInfo.getIsMain() != null
						&& "IS".equals(contractItemInfo.getIsMain())
						&& itemInfo.getContractStatus() != ContractStatusEnum.END) {
						ishaveMain = "IS";
					}
				}
				info.setProjectContractItemInfos(projectContractItemInfoList);
			}
			
			//查看项目所有合同
			ProjectContractQueryOrder order = new ProjectContractQueryOrder();
			order.setPageSize(9999l);
			order.setProjectCode(info.getProjectCode());
			String applyFormIds = "";//关联过的签报formId;
			String isFirst = "IS";//是否第一次申请
			QueryBaseBatchResult<ProjectContractResultInfo> batchResult = projectContractServiceClient
				.query(order);
			if (batchResult != null && batchResult.getPageList().size() > 0) {//是否有主合同
				for (ProjectContractResultInfo itemInfo : batchResult.getPageList()) {
					if ((itemInfo.getIsMain() != null && "IS".equals(itemInfo.getIsMain()))
						&& itemInfo.getContractStatus() != ContractStatusEnum.END) {
						if ("IS".equals(info.getFreeFlow())) {
							ishaveMain = "NO";
							if (itemInfo.getFormId() != formId) {//自由合同剔除本张单子
								ishaveMain = "IS";
								break;
							}
						} else {
							ishaveMain = "IS";
						}
						break;
					}
				}
				for (ProjectContractResultInfo itemInfo : batchResult.getPageList()) {
					//					if (StringUtil.isNotEmpty(itemInfo.getBasisOfDecision())) {
					//						applyFormIds = applyFormIds + itemInfo.getBasisOfDecision() + ",";
					//					}
					if (itemInfo.getFormId() != formId) {
						isFirst = null;
					}
				}
			}
			//			if (applyFormIds.length() > 0) {
			//				applyFormIds = applyFormIds.substring(0, applyFormIds.length() - 1);
			//			}
			//			model.addAttribute("applyFormIds", applyFormIds);
			JSONArray guarantorJson = new JSONArray();
			JSONArray pledgeJson = new JSONArray();
			JSONArray mortgageJson = new JSONArray();
			JSONArray textJson = new JSONArray();
			List<ProjectCreditConditionInfo> creditConditions = projectCreditConditionServiceClient
				.findProjectCreditConditionByProjectCode(info.getProjectCode());
			//合同类函关联过的
			order.setApplyType(ProjectContractTypeEnum.PROJECT_LETTER);
			order.setLetterType(LetterTypeEnum.CONTRACT);
			QueryBaseBatchResult<ProjectContractResultInfo> batchResult_contractLetter = projectContractServiceClient
				.query(order);
			//2次申请关联过的授信措施不带出来
			if (creditConditions != null) {
				guarantorJson = new JSONArray();
				pledgeJson = new JSONArray();
				mortgageJson = new JSONArray();
				for (ProjectCreditConditionInfo creditInfo : creditConditions) {
					boolean ishave = false;
					if (batchResult != null && batchResult.getPageList().size() > 0) {//去掉合同申请的关联授信措施
						for (ProjectContractResultInfo itemInfo : batchResult.getPageList()) {
							if (StringUtil.isNotEmpty(itemInfo.getCreditMeasure())
								&& itemInfo.getContractStatus() != ContractStatusEnum.END) {
								String creditIds[] = itemInfo.getCreditMeasure().split(",");
								for (String creditId : creditIds) {
									if (StringUtil.isNotEmpty(creditId)
										&& creditInfo.getId() == Long.parseLong(creditId)
										&& formId != itemInfo.getFormId()) {
										ishave = true;
									}
								}
							}
						}
					}
					if (batchResult_contractLetter != null
						&& batchResult_contractLetter.getPageList().size() > 0) {//去掉合同类函关联的授信措施
						for (ProjectContractResultInfo itemInfo : batchResult_contractLetter
							.getPageList()) {
							if (StringUtil.isNotEmpty(itemInfo.getCreditMeasure())
								&& itemInfo.getContractStatus() != ContractStatusEnum.END) {
								String creditIds[] = itemInfo.getCreditMeasure().split(",");
								for (String creditId : creditIds) {
									if (StringUtil.isNotEmpty(creditId)
										&& creditInfo.getId() == Long.parseLong(creditId)
										&& formId != itemInfo.getFormId()) {
										ishave = true;
									}
								}
							}
						}
					}
					if (!ishave) {
						JSONObject json = new JSONObject();
						json.put("code", creditInfo.getId());
						if (creditInfo.getItemDesc().startsWith("关键信息")) {
							json.put("txt", creditInfo.getItemDesc());
						} else {
							json.put("txt", "关键信息 ：" + getWarrantCode(creditInfo.getAssetId())
											+ "  " + creditInfo.getItemDesc());
						}
						json.put("type", creditInfo.getType().message());
						//从资产查找关键信息
						json.put("warrantCode", getWarrantCode(creditInfo.getAssetId()));
						if (creditInfo.getType() != null) {
							if (creditInfo.getType().code().equals("GUARANTOR")) {
								guarantorJson.add(json);
							} else if (creditInfo.getType().code().equals("PLEDGE")) {
								pledgeJson.add(json);
							} else if (creditInfo.getType().code().equals("TEXT")) {
								textJson.add(json);
							} else {
								mortgageJson.add(json);
							}
						}
					}
				}
			}
			model.addAttribute("isFirst", isFirst);
			model.addAttribute("guarantorJson", guarantorJson.toJSONString().replaceAll("\"", "'"));
			//前端取抵质押反了
			model.addAttribute("mortgageJson", pledgeJson.toJSONString().replaceAll("\"", "'"));
			model.addAttribute("pledgeJson", mortgageJson.toJSONString().replaceAll("\"", "'"));
			model.addAttribute("textJson", textJson.toJSONString().replaceAll("\"", "'"));
			model.addAttribute("ishaveMain", ishaveMain);
			model.addAttribute("info", info);
			model.addAttribute("projectInfo", projectInfo);
			model.addAttribute("checkStatus", form.getCheckStatus());
			model.addAttribute("formCode", form.getFormCode());
			model.addAttribute("formStatus", form.getStatus());
			model.addAttribute("basisOfDecision", BasisOfDecisionEnum.getAllEnum());
			model.addAttribute("ishaveApproval", isHaveApproval(projectInfo));
			model.addAttribute("pledgeType", getPledgeType());
			model.addAttribute("form", form);
			model.addAttribute("requestUrl", getRequestUrl(request));
			
			if (info.getApplyType() == ProjectContractTypeEnum.PROJECT_WRIT) {
				return "/projectMg/assistSys/writ/addWrit.vm";
			} else if (info.getApplyType() == ProjectContractTypeEnum.PROJECT_LETTER) {
				model.addAttribute("canAddOtherLetter", canAddOtherLetter(info.getProjectCode()));
				model.addAttribute("letterTypes", LetterTypeEnum.getAllEnum());
				return "/projectMg/assistSys/letter/addLetter.vm";
			} else if (info.getApplyType() == ProjectContractTypeEnum.PROJECT_DB_LETTER) {
				model.addAttribute("canAddOtherLetter", "IS");
				model.addAttribute("letterTypes", LetterTypeEnum.getAllEnum());
				//重新授信的不显示授信落实
				ProjectCreditConditionQueryOrder queryOrder = new ProjectCreditConditionQueryOrder();
				queryOrder.setProjectCode(info.getProjectCode());
				creditPageList(queryOrder, request, model);
				if (!model.containsAttribute("hasCreditConditon")) {
					model.addAttribute("isRedoProject",
						projectInfo.getIsRedoProject() == BooleanEnum.IS);
					showRedoSummary(info.getProjectCode(), model);
				}
				return "/projectMg/assistSys/dbLetter/addLetter.vm";
			}
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		return vm_path + "add_contract.vm";
	}
	
	//能否操作填写其他的函
	private String canAddOtherLetter(String projectCode) {
		SessionLocal session = ShiroSessionUtils.getSessionLocal();
		ProjectInfo projectInfo = projectServiceClient.queryByCode(projectCode, false);
		if (ProjectUtil.isUnderwriting(projectInfo.getBusiType())
			|| ProjectUtil.isLitigation(projectInfo.getBusiType())
			|| projectInfo.getIsRedoProject() == BooleanEnum.IS) {
			return "IS";
		}
		ProjectQueryOrder queryOrder = new ProjectQueryOrder();
		queryOrder.setProjectCode(projectCode);
		List<ProjectPhasesEnum> phasesList = new ArrayList<>();
		phasesList.add(ProjectPhasesEnum.CONTRACT_PHASES);
		phasesList.add(ProjectPhasesEnum.LOAN_USE_PHASES);
		phasesList.add(ProjectPhasesEnum.FUND_RAISING_PHASES);
		phasesList.add(ProjectPhasesEnum.AFTERWARDS_PHASES);
		phasesList.add(ProjectPhasesEnum.RECOVERY_PHASES);
		queryOrder.setPhasesList(phasesList);
		List<ProjectStatusEnum> statusList = new ArrayList<>();
		statusList.add(ProjectStatusEnum.NORMAL);
		statusList.add(ProjectStatusEnum.RECOVERY);
		statusList.add(ProjectStatusEnum.TRANSFERRED);
		statusList.add(ProjectStatusEnum.SELL_FINISH);
		statusList.add(ProjectStatusEnum.EXPIRE);
		statusList.add(ProjectStatusEnum.OVERDUE);
		queryOrder.setStatusList(statusList);
		setSessionLocalInfo2Order(queryOrder);
		
		//当前业务经理
		queryOrder.setBusiManagerId(session.getUserId());
		
		QueryBaseBatchResult<ProjectSimpleDetailInfo> batchResult = chargeNotificationServiceClient
			.selectChargeNotificationProject(queryOrder);
		List<ProjectSimpleDetailInfo> list = batchResult.getPageList();
		if (ListUtil.isNotEmpty(list) && list.size() > 0) {
			return "IS";
		}
		return "NO";
	}
	
	private String getWarrantCode(long assetId) {
		if (assetId > 0) {
			AssetQueryOrder assetQueryOrder = new AssetQueryOrder();
			List<Long> assetsIdList = Lists.newArrayList();
			assetsIdList.add(assetId);
			assetQueryOrder.setAssetsIdList(assetsIdList);
			QueryBaseBatchResult<AssetSimpleInfo> queryAssetSimple = pledgeAssetServiceClient
				.queryAssetSimple(assetQueryOrder);
			if (ListUtil.isNotEmpty(queryAssetSimple.getPageList())) {
				AssetSimpleInfo assetSimpleInfo = queryAssetSimple.getPageList().get(0);
				return StringUtil.isNotBlank(assetSimpleInfo.getAssetRemarkInfo()) ? assetSimpleInfo
					.getAssetRemarkInfo() : "无";
			}
		}
		return "无";
	}
	
	/**
	 * 筛选合同
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("contractChoose.htm")
	@ResponseBody
	public JSONObject contractChoose(ProjectContractQueryOrder order, Model model) {
		String tipPrefix = "筛选合同";
		JSONObject result = new JSONObject();
		try {
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			//order.setIsChargeNotification("IS");
			QueryBaseBatchResult<ProjectContractResultInfo> batchResult = projectContractServiceClient
				.query(order);
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (ProjectContractResultInfo info : batchResult.getPageList()) {
					
					JSONObject json = new JSONObject();
					json.put("contractCode", info.getContractCode());
					json.put("contractName", info.getContractName());
					json.put("contractType", info.getContractType().code());
					json.put("contractTypeMessage", info.getContractType().message());
					if (info.getContractType().code().equals(ContractTypeEnum.STANDARD.code())) {
						json.put("url", "/projectMg/contract/standardContractMessage.htm?id="
										+ info.getId() + "&isChecker=");
					} else {
						json.put("url", StringUtil.isBlank(info.getFileUrl()) ? null : info
							.getFileUrl().split(",")[2]);
					}
					dataList.add(json);
				}
			}
			data.put("pageCount", batchResult.getPageCount());
			data.put("pageNumber", batchResult.getPageNumber());
			data.put("pageSize", batchResult.getPageSize());
			data.put("totalCount", batchResult.getTotalCount());
			data.put("pageList", dataList);
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		return result;
	}
	
	// 抓取关联授信措施
	
	/**
	 * 抓取关联授信措施
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("getProjectCreditCondition.htm")
	@ResponseBody
	public JSONObject getProjectCreditCondition(String projectCode, Model model) {
		//TODO 暂时没用这个方法
		JSONObject json = new JSONObject();
		String tipPrefix = "抓取关联授信措施";
		List<ProjectCreditConditionInfo> creditConditions = projectCreditConditionServiceClient
			.findProjectCreditConditionByProjectCode(projectCode);
		json.put("creditConditions", creditConditions);
		//		json.put("pageCount", batchResult.getPageCount());
		//		json.put("pageNumber", batchResult.getPageNumber());
		//		json.put("pageSize", batchResult.getPageSize());
		//		json.put("totalCount", batchResult.getTotalCount());
		json = toStandardResult(json, tipPrefix);
		return json;
	}
	
	/**
	 * 合同打印
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("print.htm")
	public String print(long formId, HttpServletRequest request, Model model) {
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		ProjectContrctInfo info = projectContractServiceClient.findByFormId(formId);
		ProjectSimpleDetailInfo projectInfo = projectServiceClient.querySimpleDetailInfo(info
			.getProjectCode());
		List<ProjectContractItemInfo> projectContractItemInfoList = Lists.newArrayList();
		if (info.getProjectContractItemInfos().size() > 0) {
			ProjectContractItemInfo contractItemInfo = new ProjectContractItemInfo();
			for (ProjectContractItemInfo itemInfo : info.getProjectContractItemInfos()) {
				contractItemInfo = itemInfo;
				ContractTemplateInfo templateInfo = contractTemplateServiceClient.findById(itemInfo
					.getTemplateId());
				if (templateInfo != null && null != templateInfo.getCreditConditionType()) {
					contractItemInfo.setCreditConditionType(templateInfo.getCreditConditionType());
				}
				projectContractItemInfoList.add(contractItemInfo);
			}
			info.setProjectContractItemInfos(projectContractItemInfoList);
		}
		setAuditHistory2Page(form, model);
		model.addAttribute("info", info);
		model.addAttribute("form", form);// 表单信息
		model.addAttribute("formCode", form.getFormCode());
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("requestUrl", getRequestUrl(request));
		return vm_path + "viewContract.vm";
	}
	
	//抓取资产系统的抵质押物
	private List<PledgeTypeSimpleInfo> getPledgeType() {
		//		List<String> pledgeTypes = com.google.common.collect.Lists.newArrayList();
		//		PledgeTypeQueryOrder order = new PledgeTypeQueryOrder();
		//		order.setPageSize(1000);
		//		QueryBaseBatchResult<PledgeTypeInfo> batchResult = pledgeTypeServiceClient.query(order);
		//		if (batchResult != null && batchResult.getPageList().size() > 0) {
		//			for (PledgeTypeInfo info : batchResult.getPageList()) {
		//				String pledgeType = info.getLevelOne() + " - " + info.getLevelTwo() + " - "
		//									+ info.getLevelThree();
		//				pledgeTypes.add(pledgeType);
		//			}
		//		}
		//		return pledgeTypes;
		return pledgeTypeServiceClient.queryAll().getPageList();
	}
	
	@RequestMapping("/excelMessage.htm")
	public String excelMessage(ModelMap model, HttpServletRequest request,
								HttpServletResponse response) {
		//		
		//		一般企业企业报表标准格式-模板--保后_2016.xls,
		//		/opt/apache-tomcat-7.0.39-yrd2/webapps/ROOT/uploadfile/images/2016-08/08/110_3334062372.xls,
		//		http://192.169.2.245:30000/uploadfile/images/2016-08/08/110_3334062372.xls
		
		String contractItemId = request.getParameter("contractItemId");
		ProjectContractItemInfo itemInfo = null;
		if (StringUtil.isNotBlank(contractItemId)) {
			itemInfo = projectContractServiceClient.findContractItemById(Long
				.valueOf(contractItemId));
		}
		// 是否只读
		String read = request.getParameter("read");
		boolean isRead = false;
		if ("read".equals(read)) {
			isRead = true;
		}
		// 是否隐藏标题
		String hidTitle = request.getParameter("hidTitle");
		if ("hidTitle".equals(hidTitle)) {
			model.addAttribute("hidTitle", true);
		}
		
		//当前登录的用户名[规则： 用户名+当前日期]
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		
		String userName = "";
		if (sessionLocal == null) {
			userName = new SimpleDateFormat("yyyyMMdd").format(new Date());
		} else {
			
			userName = sessionLocal.getUserName() + "-"
						+ new SimpleDateFormat("yyyyMMdd").format(new Date());
		}
		
		//文件名称[当要新建一个空白文档时、请将该值设空]
		String fileName = request.getParameter("fileName");// 文件名
		String fileUrl = request.getParameter("fileUrl");// 物理地址
		if (itemInfo != null) {
			String url = itemInfo.getFileUrl();
			if (StringUtil.isNotBlank(url) && url.indexOf(",") > 0) {
				String[] pathArray = url.split(",");
				fileName = pathArray[0];
				//				com.born.fcs.pm.util.StringUtil.encodeURI(fileName)
				fileUrl = pathArray[1];
			}
		}
		// 若是新增，新建一个文件名和地址过去，若是修改，文件名和地址使用老的
		String fileNewUrl = "";
		String requestPath = "";
		String saveType = "";
		if (StringUtil.isNotBlank(fileUrl)) {
			// 代表有值，是修改
			fileNewUrl = fileUrl;
			// 需要加入修改标记，有修改标记的，保存后不需要重新save进合同信息
			saveType = "modify";
		} else {
			// 无值的时候是新增
			String templateId = request.getParameter("templateId");
			if (StringUtil.isBlank(templateId) && itemInfo != null) {
				templateId = String.valueOf(itemInfo.getTemplateId());
			}
			if (StringUtil.isBlank(templateId)) {
				// 代表新增空白页面 自定义name
				// 
				fileName = "自定义合同word.doc";
				saveType = "templateAdd";
			} else {
				// 代表加载模版页面
				ContractTemplateInfo info = contractTemplateServiceClient.findById(new Long(
					templateId));
				if (info != null) {
					String infoName = info.getName();
					//					// 清除小数点
					//					if(infoName.indexOf(".") > 0){
					//						infoName= infoName.substring(0, infoName.indexOf("."));
					//					}
					// 将小数点化为-
					infoName = infoName.replace(".", "-");
					fileName = infoName + ".doc";
					
					// 获取物理地址
					String uploadFolder = sysParameterServiceClient
						.getSysParameterValue(SysParamEnum.SYS_PARAM_UPLOAD_FOLDER.code());
					String itemUrl = info.getTemplateFile();
					if (StringUtil.isNotBlank(itemUrl)) {
						// 转化访问地址为物理地址
						if (itemUrl.indexOf(",") > 0) {
							// 新数据
							fileUrl = itemUrl.split(",")[1];
						} else {
							// 老数据
							String str = itemUrl;
							if (str.indexOf("://") > 0) {
								str = str.split("://")[1];
								if (str.indexOf("/") > 0) {
									str = str.substring(str.indexOf("/"), str.length());
								}
							}
							fileUrl = uploadFolder + str;
						}
					}
					//fileUrl=
				} else {
					fileName = "默认合同word.doc";
				}
				saveType = "noteAdd";
			}
			String[] pathArray = UploadFileUtils
				.getFilePath(request, "uploadfile", "pfd", fileName);
			fileNewUrl = pathArray[0];// 物理地址
			requestPath = pathArray[1]; //访问地址
		}
		//文件后缀
		String suffix = ".doc";
		if (!"".equals(fileName) && fileName != null) {
			int index = fileName.indexOf(".");
			if (index > 0) {
				suffix = fileName.substring(index, fileName.length());
			}
		}
		//后台服务器地址、用于解决后台与控件的交互
		String serviceUrl = "/projectMg/contract/excelShow.htm?contractItemId=" + contractItemId;
		serviceUrl += "&fileUrl=" + fileUrl.replace("\\", "/");// 物理地址
		serviceUrl += "&fileNewUrl=" + fileNewUrl.replace("\\", "/");// 新物理地址
		serviceUrl += "&requestPath=" + requestPath;// 访问地址
		serviceUrl += "&saveType=" + saveType;// 保存类型
		
		//		serviceUrl = serviceUrl.replace(".docx", ".doc");
		//		suffix = suffix.replace(".docx", ".doc");
		//		fileName = "a.doc";
		
		model.addAttribute("userName", userName);
		model.addAttribute("serviceUrl", serviceUrl);
		model.addAttribute("fileName", fileName);
		model.addAttribute("suffix", suffix);
		model.addAttribute("read", isRead);
		return vm_path + "excelShow.vm";
	}
	
	@RequestMapping("/excelShow.htm")
	public void excelShow(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			iWebOffice officeServer = new iWebOffice();
			iMsgServer2000 msgObj = new iMsgServer2000();
			if (!request.getMethod().equalsIgnoreCase("POST")) {
				msgObj.MsgError("客户端发送数据必须POST!");
				return ;
			}
			byte[] iwebOfficeParam = (byte[]) request.getAttribute("iwebofficeParam");
			//officeServer.ExecuteRun(request, response, iwebOfficeParam);
			
			msgObj.Load(request, iwebOfficeParam);
			logger.info("msgObj.GetMsgByName(\"DBSTEP\")==" + msgObj.GetMsgByName("DBSTEP"));
			if (msgObj.GetMsgByName("DBSTEP").equalsIgnoreCase("DBSTEP")) {
				String option = msgObj.GetMsgByName("OPTION"); //取得操作信息
				String userName = msgObj.GetMsgByName("USERNAME"); //取得系统用户
				String fileName = msgObj.GetMsgByName("FILENAME"); //获取文件名称
				String fileUrl = request.getParameter("fileUrl"); //旧物理地址
				String fileNewUrl = request.getParameter("fileNewUrl"); //新物理地址
				String requestPath = request.getParameter("requestPath"); //获取文件名称
				String saveType = request.getParameter("saveType"); //获取保存类型
				String contractItemId = request.getParameter("contractItemId"); //子合同id
				logger.info("OPTION:" + option + ", userName:" + userName);
				logger.info("fileName:" + fileName + ", fileUrl:" + fileUrl);
				logger.info("fileNewUrl:" + fileNewUrl + ", fileNewUrl:" + fileNewUrl);
				logger.info("saveType:" + saveType + ", contractItemId:" + contractItemId);
				if ("LOADFILE".equals(option)) { //远程获取文件
					byte[] bytes = getFileBytes(fileUrl);
					if (bytes == null) {
						bytes = new byte[0];
					}
					msgObj.MsgFileBody(bytes);
					msgObj.SetMsgByName("STATUS", "1"); // 设置状态信息
					msgObj.MsgError(""); // 清除错误信息
				} else if ("SAVEFILE".equals(option)) { //远程保存文件
					String isEmpty = msgObj.GetMsgByName("EMPTY"); // 是否是空内容文档的标识
					if (isEmpty.equalsIgnoreCase("TRUE")) {
						// 此时接收的文档中内容是空白的，请用日志记录保存时间、保存用户、记录编号等信息，用于将来发现文档内容丢失时排查用。
						logger.info("保存路径：" + fileNewUrl);
					}
					String code = saveFileBytes(fileNewUrl, msgObj.MsgFileBody());
					msgObj.MsgFileClear();
					msgObj.MsgError("");
					if ("1".equals(code)) {
						msgObj.SetMsgByName("STATUS", "保存成功"); // 设置状态信息
					}
					// 若是新增，添加保存任务
					if (!"modify".equals(saveType)) {
						// 调用合同的Url保存
						String url = fileName + "," + fileNewUrl + "," + requestPath;
						ProjectContractItemOrder order = new ProjectContractItemOrder();
						order.setId(new Long(contractItemId));
						order.setFileUrl(url);
						FcsBaseResult saveResult = projectContractServiceClient
							.updateComtractItemUrl(order);
						if (saveResult.isSuccess() && saveResult.isExecuted()) {
							// 本就是成功的，所以什么都不做
							msgObj.SetMsgByName("STATUS", "保存成功"); // 设置状态信息
							msgObj.MsgError("");
						} else {
							msgObj.MsgError("");
							msgObj.MsgError(saveResult.getMessage());
						}
					}
				}
			} else {
				logger.info("进入加载异常{}", msgObj.MsgError());
				msgObj.MsgError("客户端发送数据包错误!");
				
				msgObj.MsgTextClear();
				msgObj.MsgFileClear();
			}
			msgObj.Send(response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			iMsgServer2000 msgObj = new iMsgServer2000();
			msgObj.Send(response);
		}
		
	}
	
	private static String fileDir = "D:\\";
	
	public String saveFileBytes(String fileUrl, byte[] fileArray) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			file = new File(fileUrl);
			if (file.exists()) {
				file.delete();
			} else {
				String downloadFolder = fileUrl.substring(0, fileUrl.lastIndexOf("/"));
				File fileFolder = new File(downloadFolder);
				//先创建文件夹
				if (!fileFolder.isFile()) {
					fileFolder.mkdirs();
				}
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(fileArray);
			bos.flush();
			bos.close();
			fos.close();
			return "1";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "0";
		}
	}
	
	public byte[] getFileBytes(String fileUrl) {
		byte[] buffer = null;
		try {
			if (fileUrl != null && !"".equals(fileUrl)) {
				File file = new File(fileUrl);
				if (file.exists()) {
					FileInputStream fis = new FileInputStream(file);
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					byte[] b = new byte[1024];
					int n;
					while ((n = fis.read(b)) != -1) {
						bos.write(b, 0, n);
					}
					fis.close();
					bos.close();
					buffer = bos.toByteArray();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return buffer;
	}
	
	//判断项目是否有批复
	private String isHaveApproval(ProjectSimpleDetailInfo info) {
		if (info == null) {
			return null;
		}
		if (ProjectUtil.isUnderwriting(info.getBusiType()) && StringUtil.isEmpty(info.getSpCode())) {
			return "NO";
		}
		if (StringUtil.isEmpty(info.getSpCode()) || info.getIsApprovalDel() == BooleanEnum.IS) {
			return "NO";
		}
		return "IS";
	}
	
	//----函、通知书流程改成与签报一致
	/**
	 * 选择分支路径
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("audit/selPath.htm")
	public String selPath(long formId, HttpServletRequest request, Model model) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		
		initWorkflow(model, form, request.getParameter("taskId"));
		ProjectContrctInfo info = projectContractServiceClient.findByFormId(formId);
		ProjectSimpleDetailInfo projectInfo = projectServiceClient.querySimpleDetailInfo(info
			.getProjectCode());
		List<ProjectContractItemInfo> projectContractItemInfoList = Lists.newArrayList();
		if (info.getProjectContractItemInfos().size() > 0) {
			ProjectContractItemInfo contractItemInfo = new ProjectContractItemInfo();
			for (ProjectContractItemInfo itemInfo : info.getProjectContractItemInfos()) {
				contractItemInfo = itemInfo;
				ContractTemplateInfo templateInfo = contractTemplateServiceClient.findById(itemInfo
					.getTemplateId());
				if (templateInfo != null && null != templateInfo.getCreditConditionType()) {
					contractItemInfo.setCreditConditionType(templateInfo.getCreditConditionType());
				}
				projectContractItemInfoList.add(contractItemInfo);
			}
			info.setProjectContractItemInfos(projectContractItemInfoList);
		}
		setAuditHistory2Page(form, model);
		model.addAttribute("info", info);
		model.addAttribute("form", form);// 表单信息
		model.addAttribute("formCode", form.getFormCode());
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("requestUrl", getRequestUrl(request));
		//公司领导们
		String ldDeptCode = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.COMPANY_LEADER_DEPT_CODE.code());
		if (StringUtil.isNotBlank(ldDeptCode)) {
			List<SysUser> leaders = bpmUserQueryService.findUserByDeptCode(ldDeptCode);
			model.addAttribute("leaders", leaders);
		}
		//会签部门
		String signDepts = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.FORM_CHANGE_SIGN_DEPARTMENT.code());
		if (StringUtil.isNotEmpty(signDepts)) {
			signDepts = signDepts.replaceAll("\r", "").replaceAll("\n", "");
			String[] depts = signDepts.split(";");
			if (depts != null && depts.length > 0) {
				List<JSONObject> deptList = Lists.newArrayList();
				for (String dept : depts) {
					if (StringUtil.isBlank(dept))
						continue;
					String[] code_name = dept.split("_");
					if (code_name != null && code_name.length == 2) {
						JSONObject deptObj = new JSONObject();
						deptObj.put("deptCode", code_name[0].trim());
						deptObj.put("deptName", code_name[1]);
						deptList.add(deptObj);
					}
				}
				model.addAttribute("deptList", deptList);
			}
		}
		String leaderTaskNodeId = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.FORM_CHANGE_LEADER_TASK_NODE_ID.code());
		model.addAttribute("leaderTaskNodeId", leaderTaskNodeId);
		
		String deptTaskNodeId = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.FORM_CHANGE_SIGN_DEPARTMENT_TASK_NODE_ID.code());
		model.addAttribute("deptTaskNodeId", deptTaskNodeId);
		
		//nodeId,项目评审委员会,风险处置委员会
		String wyhTaskNode = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.FORM_CHANGE_WYH_TASK_NODE.code());
		if (StringUtil.isNotBlank(wyhTaskNode)) {
			//wyhTaskNode.replaceAll("，", ",");
			String[] taskNodeAndWyh = wyhTaskNode.split(",");
			model.addAttribute("wyhTaskNodeId", taskNodeAndWyh[0]);
			Map<String, List<DecisionMemberInfo>> wyhUserMap = Maps.newHashMap();
			for (String wyh : taskNodeAndWyh) {
				if (StringUtil.equals(taskNodeAndWyh[0], wyh))
					continue;
				//查询对应决策机构人员
				DecisionInstitutionInfo institutionInfo = decisionInstitutionServiceClient
					.findDecisionInstitutionByInstitutionName(wyh);
				List<DecisionMemberInfo> members = null;
				if (institutionInfo != null)
					members = decisionMemberServiceClient.queryDecisionMemberInfo(institutionInfo
						.getInstitutionId());
				wyhUserMap.put(wyh, members);
			}
			model.addAttribute("wyhMemberMap", wyhUserMap);
		}
		model.addAttribute("isChecker", true);
		return "/projectMg/assistSys/letter/viewAudit.vm";
	}
	
	/**
	 * 选择分支路径
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("audit/selManager.htm")
	public String selManager(long formId, HttpServletRequest request, Model model) {
		model.addAttribute("selManager", "YES");
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		ProjectContrctInfo info = projectContractServiceClient.findByFormId(formId);
		ProjectSimpleDetailInfo projectInfo = projectServiceClient.querySimpleDetailInfo(info
			.getProjectCode());
		List<ProjectContractItemInfo> projectContractItemInfoList = Lists.newArrayList();
		if (info.getProjectContractItemInfos().size() > 0) {
			ProjectContractItemInfo contractItemInfo = new ProjectContractItemInfo();
			for (ProjectContractItemInfo itemInfo : info.getProjectContractItemInfos()) {
				contractItemInfo = itemInfo;
				ContractTemplateInfo templateInfo = contractTemplateServiceClient.findById(itemInfo
					.getTemplateId());
				if (templateInfo != null && null != templateInfo.getCreditConditionType()) {
					contractItemInfo.setCreditConditionType(templateInfo.getCreditConditionType());
				}
				projectContractItemInfoList.add(contractItemInfo);
			}
			info.setProjectContractItemInfos(projectContractItemInfoList);
		}
		initWorkflow(model, form, request.getParameter("taskId"));
		setAuditHistory2Page(form, model);
		model.addAttribute("info", info);
		model.addAttribute("form", form);// 表单信息
		model.addAttribute("formCode", form.getFormCode());
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("requestUrl", getRequestUrl(request));
		model.addAttribute("isChecker", true);
		return "/projectMg/assistSys/letter/viewAudit.vm";
	}
	
	/**
	 * 合同作废申请单
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("addProjectContractItemInvlid.htm")
	private String addProjectContractItemInvlid(String formId, String contractCode,
												HttpServletRequest request, Model model) {
		if (formId == null) {//新增
			//根据用印列表去查用印申请单
			ProjectContractItemInvalidInfo info = new ProjectContractItemInvalidInfo();
			ProjectContractItemInfo itemInfo = projectContractServiceClient
				.findByContractCode(contractCode);
			ProjectContrctInfo contrctInfo = projectContractServiceClient.findById(itemInfo
				.getContractId());
			int cnt = 0;//份数
			List<StampAFileInfo> stampList = stampApplyServiceClient
				.findByContractCode(contractCode);
			if (ListUtil.isNotEmpty(stampList)) {
				for (StampAFileInfo fileInfo : stampList) {
					int maxCnt=fileInfo.getLegalChapterNum();
					if(fileInfo.getCachetNum()>maxCnt){
						maxCnt=fileInfo.getCachetNum();
					}
					cnt=cnt+maxCnt;
				}
				info.setCnt(cnt);
			}
			info.setProjectName(contrctInfo.getProjectName());
			info.setProjectCode(contrctInfo.getProjectCode());
			info.setCustomerId(contrctInfo.getCustomerId());
			info.setCustomerName(contrctInfo.getCustomerName());
			ProjectContractItemInfo contractItemInfo = projectContractServiceClient
				.findByContractCode(contractCode);
			info.setContractName(contractItemInfo.getContractName());
			info.setContractCode(contractCode);
			info.setFileUrl(contractItemInfo.getFileUrl());
			info.setContractScanUrl(contractItemInfo.getContractScanUrl());
			model.addAttribute("info", info);
		} else {//编辑
			ProjectContractItemInvalidInfo info = projectContractServiceClient
				.findContractInvalidByFormId(Long.parseLong(formId));
			FormInfo form = formServiceClient.findByFormId(Long.parseLong(formId));
			model.addAttribute("form", form);
			model.addAttribute("info", info);
		}
		model.addAttribute("requestUrl", getRequestUrl(request));
		return vm_path + "add_contract_invalid.vm";
	}
	
	/**
	 * 合同作废详情
	 * 
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping("viewInvalid.htm")
	public String viewInvalidAudit(Long formId, String contractNo, HttpServletRequest request,
									Model model, HttpSession session) {
		ProjectContractItemInvalidInfo info = projectContractServiceClient
			.findContractInvalidByFormId(formId);
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		setAuditHistory2Page(form, model);
		model.addAttribute("info", info);
		model.addAttribute("form", form);// 表单信息
		model.addAttribute("formCode", form.getFormCode());
		model.addAttribute("requestUrl", getRequestUrl(request));
		return vm_path + "auditContractInvalid.vm";
	}
	
	/**
	 * 合同作废审核
	 * 
	 * @param formId
	 * @param model
	 * @return
	 */
	
	@RequestMapping("auditInvalid.htm")
	public String auditInvalid(long formId, HttpServletRequest request, Model model,
								HttpSession session) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		ProjectContractItemInvalidInfo info = projectContractServiceClient
			.findContractInvalidByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		model.addAttribute("info", info);
		model.addAttribute("form", form);// 表单信息
		model.addAttribute("formCode", form.getFormCode());
		initWorkflow(model, form, request.getParameter("taskId"));
		model.addAttribute("requestUrl", getRequestUrl(request));
		return vm_path + "auditContractInvalid.vm";
	}
	
	/**
	 * 保存合同作废申请单
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveProjectContractInvlid.json")
	@ResponseBody
	public JSONObject saveProjectContractInvlid(ProjectContractItemInvalidOrder order,
												HttpServletRequest request, Model model) {
		String tipPrefix = " 保存合同作废申请单";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			ProjectInfo projectInfo = projectServiceClient.queryByCode(order.getProjectCode(),
				false);
			if (projectInfo.getStatus() == ProjectStatusEnum.PAUSE) {
				jsonObject.put("success", false);
				jsonObject.put("message", "该项目已被暂缓，不允许保存！");
				logger.error("保存合同出错", "该项目已被暂缓，不允许保存！");
				return jsonObject;
			}
			
			// 初始化Form验证信息
			//order.setCheckStatus(0);
			order.setRelatedProjectCode(order.getProjectCode());
			order.setCheckIndex(0);
			order.setFormCode(FormCodeEnum.PROJECT_CONTRACT_INVALID);
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			//复制一份作废副本
			ProjectContractItemInfo itemInfo = projectContractServiceClient
					.findByContractCode(order.getContractCode());
			String fileUrls[]=itemInfo.getFileUrl().split(";");
			String scanUrls[]={};
			if(StringUtil.isNotBlank(itemInfo.getContractScanUrl())){
			 scanUrls=itemInfo.getContractScanUrl().split(";");
			}
			String orderFileUrl="";
			String orderScanUrl="";
			for(String fileUrl:fileUrls){
				if(StringUtil.isNotBlank(fileUrl)){
					orderFileUrl=orderFileUrl+cpoyFile(fileUrl)+";";
				}
			}
			if(StringUtil.isNotBlank(orderFileUrl)){
				order.setFileUrl(orderFileUrl.substring(0,orderFileUrl.length()-1));
			}
			for(String scanUrl:scanUrls){
				if(StringUtil.isNotBlank(scanUrl)){
					orderScanUrl=orderScanUrl+cpoyFile(scanUrl)+";";
				}
			}
			if(StringUtil.isNotBlank(orderScanUrl)){
				order.setContractScanUrl(orderScanUrl.substring(0,orderScanUrl.length()-1));
			}
			FormBaseResult saveResult = projectContractServiceClient.saveContractInvalid(order);
			jsonObject = toJSONResult(jsonObject, saveResult, "保存合同作废成功", null);
			jsonObject.put("formId", saveResult.getFormInfo().getFormId());
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存合同作废出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 合同作废列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("invalidList.htm")
	public String invalidList(ProjectContractQueryOrder order, Model model) {
		setSessionLocalInfo2Order(order);
		QueryBaseBatchResult<ProjectContractInvalidResultInfo> batchResult = projectContractServiceClient
			.queryInvalidList(order);
		model.addAttribute("conditions", order);
		model.addAttribute("statusList", FormStatusEnum.getAllEnum());
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		return vm_path + "invalidList.vm";
	}


	/**
	 * 放款中心负责人指定面签岗人员、抵质押岗人员
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("saveRelevantPersonnel.json")
	@ResponseBody
	public JSONObject saveRelevantPersonnel(HttpServletRequest request, Model model) {
		String tipPrefix = " 保存放款中心负责人指定面签岗人员、抵质押岗人员";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();

			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			String projectCode=request.getParameter("projectCode");

			ProjectInfo projectInfo = projectServiceClient.queryByCode(projectCode,
					false);
			if (projectInfo.getStatus() == ProjectStatusEnum.PAUSE) {
				jsonObject.put("success", false);
				jsonObject.put("message", "该项目已被暂缓，不允许保存！");
				logger.error("保存签岗人员、抵质押岗人员出错", "该项目已被暂缓，不允许保存！");
				return jsonObject;
			}
			long signPostId=Long.parseLong(request.getParameter("SignPostId"));
			String signPostAccount=request.getParameter("SignPostAccount");
			String signPostName=request.getParameter("SignPostName");

			ProjectRelatedUserOrder relatedUser = new ProjectRelatedUserOrder();
			relatedUser.setProjectCode(projectCode);
			relatedUser.setUserType(ProjectRelatedUserTypeEnum.POS_VISA_INTERVIEW);
			relatedUser.setRemark("合同面签岗");
			relatedUser.setUserId(signPostId);
			relatedUser.setUserAccount(signPostAccount);
			relatedUser.setUserName(signPostName);
			FcsBaseResult saveResult = projectRelatedUserServiceClient.setRelatedUser(relatedUser);
			if(!saveResult.isSuccess()){
				jsonObject.put("success", false);
				jsonObject.put("message", "保存面签岗失败");
				return jsonObject;
			}
			relatedUser = new ProjectRelatedUserOrder();
			relatedUser.setProjectCode(projectCode);
			long offsetPostId=Long.parseLong(request.getParameter("OffsetPostId"));
			String offsetPostAccount=request.getParameter("OffsetPostAccount");
			String offsetPostName=request.getParameter("OffsetPostName");
			relatedUser.setUserType(ProjectRelatedUserTypeEnum.POS_PLEDGE);
			relatedUser.setRemark("合同抵质押岗");
			relatedUser.setUserId(offsetPostId);
			relatedUser.setUserAccount(offsetPostAccount);
			relatedUser.setUserName(offsetPostName);
			FcsBaseResult saveResult2 = projectRelatedUserServiceClient.setRelatedUser(relatedUser);
			if(!saveResult2.isSuccess()){
				jsonObject.put("success", false);
				jsonObject.put("message", "合同抵质押岗失败");
				return jsonObject;
			}
			jsonObject.put("success", true);
			jsonObject.put("message", "保存成功");
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存放款中心负责人指定面签岗人员、抵质押岗人员出错", e);
		}

		return jsonObject;
	}


	/**
	 * 复制合同副本
	 *
	 * @param
	 * @return
	 */
	public static  String cpoyFile(String filePath) {
		String str[]=filePath.split(",");
		String fileName=str[0];
		String serverPath=str[1];
		String fileUrl=str[2];
		File oldfile = new File(serverPath);
		if (!oldfile.exists()) {
			System.out.println("原合同文件不存在!");
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "原合同文件不存在!");
		}
		String urls[]=fileUrl.split("/");
		//处理名字
		String names[]=urls[urls.length-1].split("\\.");
		String finalUrl="";
		String finalName=names[0]+"zffb";//作废副本
		String suffix=names[1];
		for(int i=0;i<urls.length-1;i++){
			finalUrl=finalUrl+urls[i]+"/";
		}
		finalUrl=finalUrl+finalName+"."+suffix;
		//处理服务器地址
		String serverUrls[]=serverPath.split("/");
		String serverFinalUrl="";
		for(int i=0;i<serverUrls.length-1;i++){
			serverFinalUrl=serverFinalUrl+serverUrls[i]+"/";
		}
		serverFinalUrl=serverFinalUrl+finalName+"."+suffix;
		File newfile = new File(serverFinalUrl);
		FileInputStream fis=null;
		FileOutputStream fos=null;
		FileChannel inC=null;
		FileChannel outC=null;
		try {
			fis = new FileInputStream(oldfile);
			fos = new FileOutputStream(newfile);
			inC=fis.getChannel();
			outC=fos.getChannel();
			outC.transferFrom(inC, 0, inC.size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				inC.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				outC.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileName+","+serverFinalUrl+","+finalUrl;
	}
}
