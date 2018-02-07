package com.born.fcs.face.web.controller.project.fund;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.CommonUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.PaymentMenthodEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectChannelRelationInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.info.financeaffirm.ProjectChargePayInfo;
import com.born.fcs.pm.ws.info.fund.FLoanUseApplyFeeInfo;
import com.born.fcs.pm.ws.info.fund.FLoanUseApplyInfo;
import com.born.fcs.pm.ws.info.fund.FLoanUseApplyReceiptInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.ProjectCreditAssetAttachmentInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.ProjectCreditConditionInfo;
import com.born.fcs.pm.ws.info.projectissueinformation.ProjectIssueInformationInfo;
import com.born.fcs.pm.ws.order.financeaffirm.ProjectChargePayQueryOrder;
import com.born.fcs.pm.ws.order.fund.FLoanUseApplyOrder;
import com.born.fcs.pm.ws.order.fund.FLoanUseApplyReceiptBatchOrder;
import com.born.fcs.pm.ws.order.fund.LoanUseApplyQueryOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.ProjectCreditConditionQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.fund.LoanUseApplyResult;
import com.born.fcs.pm.ws.service.report.ProjectReportService;
import com.born.fcs.pm.ws.service.report.info.ProjectChargeDetailInfo;
import com.born.fcs.pm.ws.service.report.order.ProjectChargeDetailQueryOrder;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 放用款申请
 * @author wuzj
 *
 */
@Controller
@RequestMapping("projectMg/loanUseApply")
public class LoanUseApplyController extends WorkflowBaseController {
	
	private final String vm_path = "/projectMg/cashMg/putInMoney/";
	
	@Autowired
	ProjectReportService projectReportServiceClient;
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "expectLoanDate", "actualLoanTime", "cashDepositEndTime" };
	}
	
	@Override
	protected String[] getMoneyInputNameArray() {
		return new String[] { "amount", "cashDepositAmount", "actualDepositAmount",
								"liquidityLoanAmount", "fixedAssetsFinancingAmount",
								"acceptanceBillAmount", "creditLetterAmount" };
	}
	
	@RequestMapping("list.htm")
	public String list(LoanUseApplyQueryOrder order, Model model) {
		setSessionLocalInfo2Order(order);
		model.addAttribute("queryOrder", order);
		model.addAttribute("isBusiManager", isBusiManager() || isLegalManager());
		model.addAttribute("formStatus", FormStatusEnum.getAllEnum());
		if (order.getHasReceipt() != null) {
			order.setFormStatus(FormStatusEnum.APPROVAL.code());
		}
		model.addAttribute("page",
			PageUtil.getCovertPage(loanUseApplyServiceClient.searchApplyForm(order)));
		
		List<JSONObject> busiSubTypeList = CommonUtil
			.newJsonList("[{'busiType':'111','busiTypeName':'流动资金贷款'},{'busiType':'112','busiTypeName':'固定资产融资'},{'busiType':'113','busiTypeName':'承兑汇票担保'},{'busiType':'114','busiTypeName':'信用证担保'}]");
		model.addAttribute("busiSubTypeList", busiSubTypeList);
		
		return vm_path + "list.vm";
	}
	
	@RequestMapping("form.htm")
	public String form(String projectCode, HttpServletRequest request, Model model) {
		
		model.addAttribute("feeType", FeeTypeEnum.getAllEnum());
		model.addAttribute("formCode", FormCodeEnum.LOAN_USE_APPLY);
		if (StringUtil.isNotBlank(projectCode)) {
			if (!DataPermissionUtil.isBusiManager(projectCode)) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.NO_ACCESS, "只有客户经理才能发起放用款");
			}
			model.addAttribute("showChageFee", true);
			applyProjectInfo(projectCode, request, model);
			
			showFenbaoInfo(projectCode, model);
		}
		return vm_path + "add_apply.vm";
	}
	
	@RequestMapping("edit.htm")
	public String edit(long formId, HttpServletRequest request, Model model) {
		model.addAttribute("isEdit", true);
		model.addAttribute("showChageFee", true);
		viewApply(formId, request, model);
		return vm_path + "add_apply.vm";
	}
	
	@RequestMapping("view.htm")
	public String view(long formId, HttpServletRequest request, Model model) {
		viewApply(formId, request, model);
		return vm_path + "view_apply.vm";
	}
	
	@RequestMapping("viewReceipt.htm")
	public String viewReceipt(long applyId, Model model) {
		List<FLoanUseApplyReceiptInfo> receipts = loanUseApplyServiceClient
			.queryReceiptByApplyId(applyId);
		model.addAttribute("receipts", receipts);
		model.addAttribute("receiptCount", receipts == null ? 0 : receipts.size());
		return vm_path + "receipt.vm";
	}
	
	@RequestMapping("audit.htm")
	public String audit(long formId, HttpServletRequest request, Model model) {
		
		FormInfo form = viewApply(formId, request, model);
		
		initWorkflow(model, form, request.getParameter("taskId"));
		
		return vm_path + "audit_apply.vm";
	}
	
	@RequestMapping("saveApply.htm")
	@ResponseBody
	public JSONObject saveApply(FLoanUseApplyOrder order, HttpServletRequest request) {
		
		JSONObject json = new JSONObject();
		
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				json.put("success", false);
				json.put("message", "您未登陆或登陆已失效");
				return json;
			}
			
			setSessionLocalInfo2Order(order);
			
			LoanUseApplyResult applyResult = loanUseApplyServiceClient.saveApply(order);
			if (null != applyResult && applyResult.isSuccess()) {
				//				AttachmentBean attachment = new AttachmentBean();
				//				attachment.setProjectCode(order.getProjectCode());
				//				attachment.setBizNo(applyResult.getFormInfo().getFormId() + "");
				//				CommonAttachmentTypeEnum type = CommonAttachmentTypeEnum.FORM_ATTACH;
				//				attachment.setPathValues(request.getParameter("pathName_" + type.code()));
				//				attachment.setRemark("放用款申请附件");
				//				attachment.addTypes(type);
				//				addAttachfile(attachment);
				addAttachfile(applyResult.getFormInfo().getFormId() + "_loan", request,
					order.getProjectCode(), "放用款申请附件", CommonAttachmentTypeEnum.FORM_ATTACH);
				
			}
			toJSONResult(json, applyResult, "申请单保存成功", null);
			json.put("applyingLoanAmount", applyResult.getApplyingLoanAmount());
			json.put("applyingUseAmount", applyResult.getApplyingUseAmount());
			json.put("loanedAmount", applyResult.getLoanedAmount());
			json.put("usedAmount", applyResult.getUsedAmount());
			json.put("balanceLoanAmount", applyResult.getBalanceLoanAmount());
			json.put("balanceUseAmount", applyResult.getBalanceUseAmount());
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "保存放用款申请单出错");
			logger.error("保存放用款申请单出错：{}", e);
		}
		
		return json;
	}
	
	@RequestMapping("receiptForm.htm")
	public String receiptForm(long applyId, HttpServletRequest request, Model model) {
		
		FLoanUseApplyInfo apply = loanUseApplyServiceClient.queryByApplyId(applyId);
		ProjectInfo project = projectServiceClient.queryByCode(apply.getProjectCode(), false);
		
		Money applyAmount = (apply == null ? Money.zero() : apply.getAmount());
		model.addAttribute("apply", apply);
		model.addAttribute("project", project);
		model.addAttribute("applyAmount", applyAmount);
		
		//回执
		List<FLoanUseApplyReceiptInfo> receipts = loanUseApplyServiceClient
			.queryReceiptByApplyId(applyId);
		model.addAttribute("receipts", receipts);
		model.addAttribute("receiptCount", receipts == null ? 0 : receipts.size());
		Money receiptAmount = Money.zero();
		if (receipts != null) {
			for (FLoanUseApplyReceiptInfo receipt : receipts) {
				receiptAmount.addTo(receipt.getActualAmount());
			}
		}
		model.addAttribute("receiptAmount", receiptAmount);
		
		//还能放款回执
		if (applyAmount.subtract(receiptAmount).getCent() > 0) {
			//发债类项目查询监管机构批复金额
			if (project.isBond()) {
				List<ProjectIssueInformationInfo> iif = projectIssueInformationServiceClient
					.findProjectIssueInformationByProjectCode(apply.getProjectCode());
				if (ListUtil.isNotEmpty(iif)) {
					//监管机构批复金额
					model.addAttribute("approvalAmount", iif.get(0).getAmount());
				}
			}
			//查询资金渠道
			List<ProjectChannelRelationInfo> capitalChannels = projectChannelRelationServiceClient
				.queryCapitalChannel(project.getProjectCode());
			//是否银行融资渠道
			model.addAttribute("isBankFinancing", project.isBankFinancing());
			model.addAttribute("capitalChannels", capitalChannels);
			//资金渠道个数
			model.addAttribute("capitalChannelCount",
				capitalChannels == null ? 0 : capitalChannels.size());
			
			model.addAttribute("canReceipt", true);
		}
		
		return vm_path + "receiptForm.vm";
	}
	
	@RequestMapping("saveReceipt.htm")
	@ResponseBody
	public JSONObject saveReceipt(FLoanUseApplyReceiptBatchOrder order, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				json.put("success", false);
				json.put("message", "您未登陆或登陆已失效");
				return json;
			}
			
			setSessionLocalInfo2Order(order);
			
			FcsBaseResult result = loanUseApplyServiceClient.saveApplyReceipt(order);
			toJSONResult(json, result, "上传回执成功", null);
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "上传回执出错");
			logger.error("上传回执出错：{}", e);
		}
		return json;
	}
	
	@RequestMapping("receiptBusiType.htm")
	@ResponseBody
	public JSONObject receiptBusiType(String projectCode) {
		JSONObject json = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				json.put("success", false);
				json.put("message", "您未登陆或登陆已失效");
				return json;
			}
			
			List<FLoanUseApplyReceiptInfo> result = loanUseApplyServiceClient
				.queryReceipt(projectCode);
			if (ListUtil.isNotEmpty(result)) {
				json.put("success", true);
				json.put("message", "查询成功");
				FLoanUseApplyReceiptInfo receipt = result.get(0);
				json.put("busiType", receipt.getBusiSubType());
				json.put("busiTypeName", receipt.getBusiSubTypeName());
			} else {
				json.put("success", false);
				json.put("message", "无历史记录");
			}
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "查询历史细分业务出错");
			logger.error("查询历史细分业务出错：{}", e);
		}
		return json;
	}
	
	/**
	 * 查看申请单
	 * @param projectCode
	 * @param model
	 * @return
	 */
	private FormInfo viewApply(long formId, HttpServletRequest request, Model model) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		
		FLoanUseApplyInfo applyInfo = loanUseApplyServiceClient.queryByFormId(formId);
		
		if (applyInfo == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		
		model.addAttribute("feeType", FeeTypeEnum.getAllEnum());
		model.addAttribute("form", form);
		model.addAttribute("formCode", FormCodeEnum.LOAN_USE_APPLY);
		
		applyProjectInfo(applyInfo, form, request, model);
		
		viewReceipt(applyInfo.getApplyId(), model);
		
		showFenbaoInfo(applyInfo.getProjectCode(), model);
		
		setAuditHistory2Page(form, model);
		
		queryCommonAttachmentData(model, form.getFormId() + "_loan",
			CommonAttachmentTypeEnum.FORM_ATTACH);
		
		return form;
	}
	
	/**
	 * 异步分页加载授信条件
	 * @param projectCode
	 * @param isConfirm
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("creditPageList.json")
	public String creditPageList(ProjectCreditConditionQueryOrder order,
									HttpServletRequest request, Model model) {
		if (order != null && StringUtil.isNotEmpty(order.getProjectCode())) {
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
		return vm_path + "creditPageList.vm";
	}
	
	/**
	 * 申请单项目相关信息
	 * @param applyInfo
	 * @param form
	 * @param model
	 */
	private void applyProjectInfo(FLoanUseApplyInfo applyInfo, FormInfo form,
									HttpServletRequest request, Model model) {
		
		try {
			
			ProjectSimpleDetailInfo project = projectServiceClient.querySimpleDetailInfo(applyInfo
				.getProjectCode());
			
			if (project == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "项目不存在");
			}
			
			model.addAttribute("apply", applyInfo);
			model.addAttribute("project", project);
			
			boolean isDraft = false;
			LoanUseApplyResult result = null;
			//未提交、撤销、驳回后显示实时的余额
			if (form == null || form.getStatus() == FormStatusEnum.DRAFT
				|| form.getStatus() == FormStatusEnum.CANCEL
				|| form.getStatus() == FormStatusEnum.BACK) {
				isDraft = true;
				result = loanUseApplyServiceClient.queryProjectAmount(project,
					applyInfo.getApplyId());
				ProjectChargePayQueryOrder queryOrder = new ProjectChargePayQueryOrder();
				queryOrder.setProjectCode(applyInfo.getProjectCode());
				List<String> feeTypeList = Lists.newArrayList();
				feeTypeList.add(FeeTypeEnum.GUARANTEE_DEPOSIT.code()); //存入保证金
				feeTypeList.add(PaymentMenthodEnum.CUSTOMER_DEPOSIT_REFUND.code()); //退换客户保证金
				queryOrder.setFeeTypeList(feeTypeList);
				queryOrder.setPageSize(999);
				QueryBaseBatchResult<ProjectChargePayInfo> depositList = financeAffirmServiceClient
					.queryProjectChargePayDetail(queryOrder);
				Money customerDepositCharge = Money.zero();
				Money customerDepositRefund = Money.zero();
				if (depositList != null && ListUtil.isNotEmpty(depositList.getPageList())) {
					for (ProjectChargePayInfo fee : depositList.getPageList()) {
						if (StringUtil.equals(fee.getFeeType(),
							FeeTypeEnum.GUARANTEE_DEPOSIT.code())) {
							customerDepositCharge.addTo(fee.getPayAmount());
						} else {
							customerDepositRefund.addTo(fee.getPayAmount());
						}
					}
				}
				//客户保证金
				model.addAttribute("customerDepositCharge", customerDepositCharge);
				//退换保证金
				model.addAttribute("customerDepositRefund", customerDepositRefund);
			} else {//显示表单的余额
				result = loanUseApplyServiceClient.queryFormAmount(applyInfo);
				//客户保证金
				model.addAttribute("customerDepositCharge", applyInfo.getCustomerDepositCharge());
				//退换保证金
				model.addAttribute("customerDepositRefund", applyInfo.getCustomerDepositRefund());
			}
			
			if (result != null) {
				//申请中放款
				model.addAttribute("applyingLoanAmount", result.getApplyingLoanAmount());
				//申请中用款
				if (isDraft && project.isBond()) {
					model.addAttribute("applyingUseAmount",
						result.getApplyingUseAmount().subtract(result.getApplyingLoanUseAmount()));
				} else {
					model.addAttribute("applyingUseAmount", result.getApplyingUseAmount());
				}
				//授信金额
				model.addAttribute("orignalAmount", result.getOrignalAmount());
				//已放款金额
				model.addAttribute("loanedAmount", result.getLoanedAmount());
				//已用款金额
				model.addAttribute("usedAmount", result.getUsedAmount());
				//已还款金额
				model.addAttribute("releasedAmount", result.getReleasedAmount());
				//可放款余额
				model.addAttribute("balanceLoanAmount", result.getBalanceLoanAmount());
				//可用款余额
				model.addAttribute("balanceUseAmount", result.getBalanceUseAmount());
				//是否最高授信额
				model.addAttribute("isMaximumAmount", result.getIsMaximumAmount());
			}
			
			//授信条件
			ProjectCreditConditionQueryOrder cOrder = new ProjectCreditConditionQueryOrder();
			cOrder.setProjectCode(applyInfo.getProjectCode());
			creditPageList(cOrder, request, model);
			
			//重新授信的项目
			if (!model.containsAttribute("hasCreditConditon")) {
				model.addAttribute("isRedoProject", project.getIsRedoProject() == BooleanEnum.IS);
				showRedoSummary(project.getProjectCode(), model);
			}
			
			//实时查询收费明细
			if (model.containsAttribute("showChageFee")) {
				List<FLoanUseApplyFeeInfo> applyFeeList = applyInfo.getFeeList();
				if (applyFeeList == null)
					applyFeeList = Lists.newArrayList();
				ProjectChargeDetailQueryOrder chargeOrder = new ProjectChargeDetailQueryOrder();
				chargeOrder.setProjectCode(applyInfo.getProjectCode());
				QueryBaseBatchResult<ProjectChargeDetailInfo> chargeDetail = projectReportServiceClient
					.projectChargeDetail(chargeOrder);
				if (chargeDetail != null && ListUtil.isNotEmpty(chargeDetail.getPageList())) {
					applyFeeList.clear();
					for (ProjectChargeDetailInfo charge : chargeDetail.getPageList()) {
						FLoanUseApplyFeeInfo feeInfo = new FLoanUseApplyFeeInfo();
						BeanCopier.staticCopy(charge, feeInfo);
						feeInfo.setStartTime(charge.getChargeStartTime());
						feeInfo.setEndTime(charge.getChargeEndTime());
						feeInfo.setApplyId(applyInfo.getApplyId());
						feeInfo.setId(0);
						applyFeeList.add(feeInfo);
					}
				}
				applyInfo.setFeeList(applyFeeList);
			}
			model.addAttribute("feeType", FeeTypeEnum.getAllEnum());
		} catch (Exception e) {
			logger.error("加载表单数据出错", e);
		}
	}
	
	/**
	 * 设置各种金额
	 * @param project
	 * @param model
	 */
	private void applyProjectInfo(String projectCode, HttpServletRequest request, Model model) {
		FLoanUseApplyInfo applyInfo = new FLoanUseApplyInfo();
		applyInfo.setProjectCode(projectCode);
		applyProjectInfo(applyInfo, null, request, model);
	}
}
