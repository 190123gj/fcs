package com.born.fcs.face.web.controller.project.projectcreditcondition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.am.ws.enums.LatestEntryFormEnum;
import com.born.fcs.am.ws.info.pledgeasset.PledgeAssetInfo;
import com.born.fcs.am.ws.info.pledgeimage.PledgeImageCustomInfo;
import com.born.fcs.am.ws.order.pledgeimage.PledgeImageCustomQueryOrder;
import com.born.fcs.face.integration.bpm.service.info.UserInfo;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.MoneyUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CreditCheckStatusEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.enums.managerbchange.ChangeManagerbPhaseEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractItemInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.CounterGuaranteeMeasuresInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.FCreditConditionConfirmInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.FCreditConditionConfirmItemInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.ProjectCreditConditionInfo;
import com.born.fcs.pm.ws.order.projectcreditcondition.FCreditConditionConfirmOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.FCreditConditionConfirmQueryOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.ProjectCreditConditionOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.ProjectCreditConditionQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;

@Controller
@RequestMapping("projectMg/projectCreditCondition")
public class ProjectCreditConditionController extends WorkflowBaseController {
	
	final static String vm_path = "/projectMg/beforeLoanMg/projectCreditCondition/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "confirmDate", "projectCreditMarginOrder.inTime",
								"projectCreditConditionOrders[0].confirmDate",
								"projectCreditConditionOrders[1].confirmDate",
								"projectCreditConditionOrders[2].confirmDate",
								"projectCreditConditionOrders[3].confirmDate",
								"projectCreditConditionOrders[4].confirmDate",
								"projectCreditConditionOrders[5].confirmDate" };
	}
	
	@Override
	protected String[] getMoneyInputNameArray() {
		return new String[] { "projectCreditMarginOrder.marginAmount" };
	}
	
	/**
	 * 项目列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String projectList(FCreditConditionConfirmQueryOrder queryOrder, String applyTimeStart,
								String applyTimeEnd, Model model) {
		try {
			if (queryOrder == null)
				queryOrder = new FCreditConditionConfirmQueryOrder();
			
			if (StringUtil.isNotBlank(applyTimeStart)) {
				queryOrder.setSubmitTimeStart(DateUtil.string2DateTimeByAutoZero(applyTimeStart));
			}
			
			if (StringUtil.isNotBlank(applyTimeEnd)) {
				queryOrder.setSubmitTimeEnd(DateUtil.string2DateTimeBy23(applyTimeEnd));
			}
			setSessionLocalInfo2Order(queryOrder);
			model.addAttribute("applyTimeStart", applyTimeStart);
			model.addAttribute("applyTimeEnd", applyTimeEnd);
			model.addAttribute("isBusiManager", isBusiManager() || isLegalManager());
			if (StringUtil.isEmpty(queryOrder.getSortCol())) {
				queryOrder.setSortCol("form_add_time");
				queryOrder.setSortOrder("DESC");
			}
			QueryBaseBatchResult<FCreditConditionConfirmInfo> batchResult = projectCreditConditionServiceClient
				.query(queryOrder);
			model.addAttribute("conditions", queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		} catch (Exception e) {
			logger.error("查询授信落实条件列表出错");
		}
		
		return vm_path + "list.vm";
	}
	
	/**
	 * 查看授信条件落实情况
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("viewProjectCreditCondition.htm")
	public String viewProjectCreditCondition(long formId, Model model) {
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		FCreditConditionConfirmInfo confirmInfo = projectCreditConditionServiceClient
			.findCreditConditionByFormId(formId);
		
		showProjectApproval(confirmInfo.getProjectCode(), model);
		//		confirmInfo.setAmount(new Money(MoneyUtil.getMoneyByw2(confirmInfo.getAmount())));
		ProjectSimpleDetailInfo projectInfo = projectServiceClient
			.querySimpleDetailInfo(confirmInfo.getProjectCode());
		
		List<FCreditConditionConfirmItemInfo> fCreditConditionConfirmItemInfoList = projectCreditConditionServiceClient
			.findByConfirmId(confirmInfo.getConfirmId());
		
		//已落实的授信条件落实情况
		ProjectCreditConditionQueryOrder order = new ProjectCreditConditionQueryOrder();
		order.setProjectCode(confirmInfo.getProjectCode());
		order.setIsConfirm(BooleanEnum.IS.code());
		logger.info("-my qch ProjectCreditConditionQueryOrder order ={}", order);
		QueryBaseBatchResult<ProjectCreditConditionInfo> batchResult = projectCreditConditionServiceClient
			.queryCreditCondition(order);
		logger.info("-my qch projectCreditConditionServiceClient batchResult ={}", batchResult);
		model.addAttribute("confirmInfo", confirmInfo);//授信条件落实情况
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("form", form);
		model.addAttribute("listProjectCreditConditioninfoEnd", batchResult.getPageList());
		model.addAttribute("fCreditConditionConfirmItemInfoList",
			fCreditConditionConfirmItemInfoList);
		model.addAttribute("listTimeUnit", TimeUnitEnum.getAllEnum());//期限单位
		return vm_path + "viewProjectCreditCondition.vm";
	}
	
	/**
	 * 编辑授信条件落实情况
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("editProjectCredit.htm")
	public String editProjectCredit(long formId, Model model) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		model.addAttribute("form", form);
		
		FCreditConditionConfirmInfo confirmInfo = projectCreditConditionServiceClient
			.findCreditConditionByFormId(formId);
		showProjectApproval(confirmInfo.getProjectCode(), model);
		//		confirmInfo.setAmount(new Money(MoneyUtil.getMoneyByw2(confirmInfo.getAmount())));
		ProjectSimpleDetailInfo projectInfo = projectServiceClient
			.querySimpleDetailInfo(confirmInfo.getProjectCode());
		//		projectInfo.setAmount(new Money(MoneyUtil.getMoneyByw2(projectInfo.getAmount())));
		
		List<FCreditConditionConfirmItemInfo> fCreditConditionConfirmItemInfoList = projectCreditConditionServiceClient
			.findByConfirmId(confirmInfo.getConfirmId());
		if (fCreditConditionConfirmItemInfoList != null) {
			for (FCreditConditionConfirmItemInfo itemInfo : fCreditConditionConfirmItemInfoList) {
				if (itemInfo.getContractNo() != null) {
					ProjectContractItemInfo contractInfo = projectContractServiceClient
						.findByContractCodeProjectCreditConditionUse(itemInfo.getContractNo());
					Date signedTime = contractInfo.getSignedTime();
					
					if (null != contractInfo && null != signedTime) {
						itemInfo.setIsPostBack("true");
					} else {
						itemInfo.setIsPostBack("false");
					}
				}
			}
		}
		//未申请的授信条件落实情况
		//		List<ProjectCreditConditionInfo> listProjectCreditConditioninfo = projectCreditConditionServiceClient.findProjectCreditConditionByProjectCodeAndStatus(projectCode, CheckStatusEnum.NOT_APPLY.code());	
		//已落实的授信条件落实情况
		ProjectCreditConditionQueryOrder order = new ProjectCreditConditionQueryOrder();
		order.setProjectCode(confirmInfo.getProjectCode());
		order.setIsConfirm(BooleanEnum.IS.code());
		QueryBaseBatchResult<ProjectCreditConditionInfo> batchResult = projectCreditConditionServiceClient
			.queryCreditCondition(order);
		
		model.addAttribute("confirmInfo", confirmInfo);//授信条件落实情况
		model.addAttribute("projectInfo", projectInfo);
		
		model.addAttribute("listProjectCreditConditioninfoEnd", batchResult.getPageList());
		model.addAttribute("fCreditConditionConfirmItemInfoList",
			fCreditConditionConfirmItemInfoList);
		model.addAttribute("isEdit", "true");//是否编辑
		model.addAttribute("listTimeUnit", TimeUnitEnum.getAllEnum());//期限单位
		return vm_path + "addProjectCreditCondition.vm";
	}
	
	/**
	 * 编辑的时候检测当前单子是否数据
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("checkIsExistData.htm")
	@ResponseBody
	public JSONObject checkIsExistData(long formId, Model model) {
		String tipPrefix = "编辑的时候检测当前单子是否数据";
		JSONObject jsonObject = new JSONObject();
		//		JSONArray dataList = new JSONArray();
		try {
			FormInfo form = formServiceClient.findByFormId(formId);
			if (form == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
			}
			model.addAttribute("form", form);
			FCreditConditionConfirmInfo confirmInfo = projectCreditConditionServiceClient
				.findCreditConditionByFormId(formId);
			List<FCreditConditionConfirmItemInfo> fCreditConditionConfirmItemInfoList = projectCreditConditionServiceClient
				.findByConfirmId(confirmInfo.getConfirmId());
			if (ListUtil.isNotEmpty(fCreditConditionConfirmItemInfoList)) {
				jsonObject.put("isExist", true);
			} else {
				jsonObject.put("isExist", false);
				jsonObject.put("message", "当前表单无授信落实数据");
			}
			jsonObject.put("success", true);
		} catch (Exception e) {
			jsonObject = toJSONResult(tipPrefix, e);
			jsonObject.put("success", false);
			logger.error(tipPrefix, e);
		}
		return jsonObject;
	}
	
	/**
	 * 新增授信条件落实情况
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("editProjectCreditCondition.htm")
	public String addProjectCreditCondition(String projectCode, Model model) {
		showProjectApproval(projectCode, model);
		//		FCreditConditionConfirmInfo confirmInfo = projectCreditConditionServiceClient.findFCreditConditionConfirmByProjectCode(projectCode);
		ProjectSimpleDetailInfo projectInfo = projectServiceClient
			.querySimpleDetailInfo(projectCode);
		//已经暂存的数据
		//		List<FCreditConditionConfirmItemInfo> listFCreditConditionConfirmItemInfo = projectCreditConditionServiceClient.findFCreditConditionConfirmItemByProjectCodeAndStatus(projectCode,"HOLDING");
		
		//未申请的授信条件落实情况
		ProjectCreditConditionQueryOrder creditConditionQueryOrder = new ProjectCreditConditionQueryOrder();
		creditConditionQueryOrder.setProjectCode(projectCode);
		creditConditionQueryOrder.setIsConfirm(BooleanEnum.NO.code());
		creditConditionQueryOrder.setContractNo("IS");//代表合同编号不能为空
		List<String> status = Lists.newArrayList();
		status.add(CreditCheckStatusEnum.NOT_APPLY.code());
		//		status.add(CreditCheckStatusEnum.CHECK_NOT_PASS.code());
		status.add(CreditCheckStatusEnum.CHECK_PASS.code());
		creditConditionQueryOrder.setStatusList(status);//未落实
		creditConditionQueryOrder.setPageSize(9999);
		QueryBaseBatchResult<ProjectCreditConditionInfo> creditConditionBatch = projectCreditConditionServiceClient
			.queryCreditCondition(creditConditionQueryOrder);
		List<ProjectCreditConditionInfo> listProjectCreditConditioninfo = Lists.newArrayList();
		if (creditConditionBatch != null) {
			listProjectCreditConditioninfo = creditConditionBatch.getPageList();
		}
		//		List<ProjectCreditConditionInfo> listProjectCreditConditioninfo = projectCreditConditionServiceClient
		//			.findProjectCreditConditionByProjectCodeAndStatus(projectCode,
		//				CheckStatusEnum.NOT_APPLY.code());
		//客户经理A角信息
		long busiManagerId = projectInfo.getBusiManagerId();
		String busiManagerName = projectInfo.getBusiManagerName();
		String busiManagerAccount = projectInfo.getBusiManagerAccount();
		//客户经理B角信息
		ProjectRelatedUserInfo relatedUserInfo = projectRelatedUserServiceClient
			.getBusiManagerbByPhase(projectCode, ChangeManagerbPhaseEnum.CONFIRM_CREDIT_CONDITION);
		long busiManagerbId = relatedUserInfo.getUserId();
		String busiManagerbName = relatedUserInfo.getUserName();
		String busiManagerbAccount = relatedUserInfo.getUserAccount();
		if (ListUtil.isNotEmpty(listProjectCreditConditioninfo)) {
			for (ProjectCreditConditionInfo projectCreditConditionInfo : listProjectCreditConditioninfo) {
				
				projectCreditConditionInfo.setConfirmManId(busiManagerId + "," + busiManagerbId);
				projectCreditConditionInfo.setConfirmManName(busiManagerName + ","
																+ busiManagerbName);
				projectCreditConditionInfo.setConfirmManAccount(busiManagerAccount + ","
																+ busiManagerbAccount);
				if (projectCreditConditionInfo.getContractNo() != null) {
					ProjectContractItemInfo contractInfo = projectContractServiceClient
						.findByContractCodeProjectCreditConditionUse(projectCreditConditionInfo
							.getContractNo());
					String contractScanUrl = contractInfo.getContractScanUrl();
					Date signedTime = contractInfo.getSignedTime();
					if (null != contractInfo && null != signedTime) {
						projectCreditConditionInfo.setContractAgreementUrl(contractScanUrl);
						projectCreditConditionInfo.setConfirmDate(signedTime);
					}
				}
			}
		}
		//查询附件信息-资产图像信息
		
		//已落实的授信条件落实情况
		ProjectCreditConditionQueryOrder order = new ProjectCreditConditionQueryOrder();
		order.setProjectCode(projectCode);
		order.setIsConfirm(BooleanEnum.IS.code());
		QueryBaseBatchResult<ProjectCreditConditionInfo> batchResult = projectCreditConditionServiceClient
			.queryCreditCondition(order);
		
		model.addAttribute("projectInfo", projectInfo);
		//		model.addAttribute("confirmInfo", confirmInfo);
		model.addAttribute("listProjectCreditConditioninfoEnd", batchResult.getPageList());
		model.addAttribute("listProjectCreditConditioninfo", listProjectCreditConditioninfo);
		model.addAttribute("listTimeUnit", TimeUnitEnum.getAllEnum());//期限单位
		//		model.addAttribute("listFCreditConditionConfirmItemInfo", listFCreditConditionConfirmItemInfo);
		return vm_path + "addProjectCreditCondition.vm";
	}
	
	/**
	 * 加载资产附件信息
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("loadAssetAtachment.htm")
	@ResponseBody
	public JSONObject loadAssetAtachment(Long assetId, Model model) {
		JSONObject jsonObject = new JSONObject();
		String tipPrefix = "加载资产附件信息";
		try {
			if (assetId != null && assetId > 0) {
				PledgeAssetInfo assetInfo = pledgeAssetServiceClient.findById(assetId);
				if (assetInfo == null) {
					jsonObject.put("success", false);
					jsonObject.put("message", "资产信息不存在");
				} else {
					
					PledgeImageCustomQueryOrder order = new PledgeImageCustomQueryOrder();
					order.setTypeId(assetInfo.getTypeId());
					order.setLatestEntryForm(LatestEntryFormEnum.CREDIT.code());
					order.setPageSize(100);
					QueryBaseBatchResult<PledgeImageCustomInfo> resultImage = pledgeImageCustomServiceClient
						.query(order);
					List<PledgeImageCustomInfo> listImageInfo = new ArrayList<PledgeImageCustomInfo>();
					if (resultImage != null) {
						listImageInfo = resultImage.getPageList();
					}
					
					jsonObject.put("listImageInfo", listImageInfo);
					jsonObject.put("success", true);
				}
			} else {
				jsonObject.put("success", false);
				jsonObject.put("message", "资产信息不存在");
			}
			
		} catch (Exception e) {
			jsonObject = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 新增授信条件落实情况表
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("addProjectCreditCondition.htm")
	public String toAddConsignmentSales(Model model) {
		FCreditConditionConfirmInfo confirmInfo = new FCreditConditionConfirmInfo();
		
		model.addAttribute("confirmInfo", confirmInfo);
		return vm_path + "addProjectCreditCondition.vm";
	}
	
	/**
	 * 保存授信条件落实情况
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveProjectCreditCondition.htm")
	@ResponseBody
	public JSONObject saveProjectCreditCondition(HttpServletRequest request,
													HttpServletResponse response,
													FCreditConditionConfirmOrder order,
													String projectCode, Model model) {
		String tipPrefix = "保存授信条件落实情况";
		JSONObject result = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				result.put("success", false);
				result.put("message", "您未登陆或登陆已失效");
				return result;
			}
			// 初始化Form验证信息
			FormBaseResult saveResult = null;
			//			FcsBaseResult holdResult = null;
			setSessionLocalInfo2Order(order);
			//				order.setCheckStatus(1);
			if (order.getProjectCreditMarginOrder() != null) {
				order.getProjectCreditMarginOrder().setPeriod(
					NumberUtil.parseInt(order.getProjectCreditMarginOrder().getStrPeriod()));
			}
			List<ProjectCreditConditionOrder> listCreditOrder = order
				.getProjectCreditConditionOrders();
			if (listCreditOrder != null) {
				for (ProjectCreditConditionOrder projectCreditConditionOrder : listCreditOrder) {
					projectCreditConditionOrder.setConfirmDate(DateUtil
						.strToDtSimpleFormat(projectCreditConditionOrder.getConfirmDateStr()));
				}
			}
			order.setProjectCreditConditionOrders(listCreditOrder);
			ProjectSimpleDetailInfo projectInfo = projectServiceClient.querySimpleDetailInfo(order
				.getProjectCode());
			if (projectInfo != null && projectInfo.getStatus() != ProjectStatusEnum.PAUSE) {
				order.setCheckIndex(0);
				order.setRelatedProjectCode(order.getProjectCode());
				order.setFormCode(FormCodeEnum.PROJECT_CREDIT_CONDITION_CONFIRM);
				
				if (order.getCheckStatus() == 1) {
					saveResult = projectCreditConditionServiceClient
						.saveProjectCreditCondition(order);
					result = toJSONResult(result, saveResult, "提交授信落实条件成功", null);
					result.put("formId", saveResult.getFormInfo().getFormId());
					result.put("status", "SUBMIT");
					if (saveResult.isSuccess()) {
						result.put("success", true);
						result.put("message", "提交成功");
					} else {
						result.put("success", false);
						result.put("message", saveResult.getMessage());
					}
				} else {
					saveResult = projectCreditConditionServiceClient
						.saveProjectCreditCondition(order);
					result = toJSONResult(result, saveResult, "暂存授信落实条件成功", null);
					result.put("formId", saveResult.getFormInfo().getFormId());
					result.put("status", "HOLD");
					if (saveResult.isSuccess()) {
						result.put("success", true);
						result.put("message", "暂存成功");
					} else {
						result.put("success", false);
						result.put("message", saveResult.getMessage());
					}
				}
				
			} else {
				result.put("success", false);
				result.put("message", "项目暂缓,不能提交");
			}
			
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
	}
	
	/**
	 * 授信落实条件审核
	 * 
	 * @param formId
	 * @param model
	 * @return
	 */
	
	@RequestMapping("audit.htm")
	public String audit(long formId, String toPage, HttpServletRequest request, Integer toIndex,
						Model model, HttpSession session) {
		FCreditConditionConfirmInfo confirmInfo = projectCreditConditionServiceClient
			.findCreditConditionByFormId(formId);
		ProjectSimpleDetailInfo projectInfo = projectServiceClient
			.querySimpleDetailInfo(confirmInfo.getProjectCode());
		showProjectApproval(confirmInfo.getProjectCode(), model);
		confirmInfo.setAmount(new Money(MoneyUtil.getMoneyByw2(confirmInfo.getAmount())));
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		List<FCreditConditionConfirmItemInfo> creditConditionInfoList = projectCreditConditionServiceClient
			.findByConfirmId(confirmInfo.getConfirmId());
		model.addAttribute("creditConditionInfoList", creditConditionInfoList);
		
		//已落实的授信条件落实情况
		ProjectCreditConditionQueryOrder order = new ProjectCreditConditionQueryOrder();
		order.setProjectCode(confirmInfo.getProjectCode());
		order.setIsConfirm(BooleanEnum.IS.code());
		QueryBaseBatchResult<ProjectCreditConditionInfo> batchResult = projectCreditConditionServiceClient
			.queryCreditCondition(order);
		model.addAttribute("listProjectCreditConditioninfoEnd", batchResult.getPageList());
		model.addAttribute("confirmInfo", confirmInfo);
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("currPage", toPage);
		
		model.addAttribute("form", form);// 表单信息
		model.addAttribute("formCode", form.getFormCode());
		
		if (toIndex == null)
			toIndex = 0;
		model.addAttribute("toIndex", toIndex);
		//WebNodeInfo nodeInfo = initWorkflow(model, form, request.getParameter("taskId"));
		//if(nodeInfo.getFormUrl())
		initWorkflow(model, form, request.getParameter("taskId"));
		
		return vm_path + "auditProjectCreditCondition.vm";
	}
	
	/**
	 * 打印
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("print.htm")
	public String print(long formId, Model model) {
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		FCreditConditionConfirmInfo confirmInfo = projectCreditConditionServiceClient
			.findCreditConditionByFormId(formId);
		showProjectApproval(confirmInfo.getProjectCode(), model);
		confirmInfo.setAmount(new Money(MoneyUtil.getMoneyByw2(confirmInfo.getAmount())));
		ProjectSimpleDetailInfo projectInfo = projectServiceClient
			.querySimpleDetailInfo(confirmInfo.getProjectCode());
		
		List<FCreditConditionConfirmItemInfo> fCreditConditionConfirmItemInfoList = projectCreditConditionServiceClient
			.findByConfirmId(confirmInfo.getConfirmId());
		//已落实的授信条件落实情况
		ProjectCreditConditionQueryOrder order = new ProjectCreditConditionQueryOrder();
		order.setProjectCode(confirmInfo.getProjectCode());
		order.setIsConfirm(BooleanEnum.IS.code());
		QueryBaseBatchResult<ProjectCreditConditionInfo> batchResult = projectCreditConditionServiceClient
			.queryCreditCondition(order);
		
		model.addAttribute("confirmInfo", confirmInfo);//授信条件落实情况
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("form", form);
		model.addAttribute("listProjectCreditConditioninfoEnd", batchResult.getPageList());
		model.addAttribute("fCreditConditionConfirmItemInfoList",
			fCreditConditionConfirmItemInfoList);
		
		return vm_path + "viewProjectCreditCondition.vm";
	}
	
	/**
	 * 反担保措施表
	 * @param request
	 * @param response
	 * @param model
	 * @param queryOrder
	 * @return
	 */
	@RequestMapping("measuresList.htm")
	public String counterGuaranteeMeasuresist(HttpServletRequest request,
												HttpServletResponse response, Model model,
												FCreditConditionConfirmQueryOrder queryOrder) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (DataPermissionUtil.isZHYYdept()) { //综合营运部可以查看全公司的报表
			
			} else if (DataPermissionUtil.isDirector() || DataPermissionUtil.isNQRole()) {//各部门总监和内勤可以看本部门全部的数据
				queryOrder.setDeptIdList(userInfo.getDeptIdList());
			} else if (DataPermissionUtil.isBusiManager() || DataPermissionUtil.isLegalManager()) { //业务人员可以看个人的数据
				queryOrder.setBusiManagerId(sessionLocal.getUserId());
			} else {//其他人没权限
				return vm_path + "counterGuaranteeMeasures.vm";
			}
		}
		QueryBaseBatchResult<CounterGuaranteeMeasuresInfo> batchResult = projectCreditConditionServiceClient
			.measuresList(queryOrder);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		model.addAttribute("conditions", queryOrder);
		return vm_path + "counterGuaranteeMeasures.vm";
	}
}
