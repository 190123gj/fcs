package com.born.fcs.face.web.controller.project.capitalappropriationapply;

import java.util.ArrayList;
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
import com.born.face.webui.table.ReportTemplate;
import com.born.face.webui.table.TableBuilder;
import com.born.fcs.crm.ws.service.info.ChannalInfo;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.fm.ws.enums.SubjectAccountTypeEnum;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.FCapitalAppropriationApplyTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.PaymentMenthodEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.capitalappropriationapply.CapitalExportInfo;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyFeeInfo;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectChannelRelationInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.info.council.CouncilInfo;
import com.born.fcs.pm.ws.info.council.CouncilProjectVoteResultInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryRiskHandleInfo;
import com.born.fcs.pm.ws.info.financeaffirm.FFinanceAffirmInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.born.fcs.pm.ws.info.forCrm.ViewChannelProjectAllPhasInfo;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyOrder;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyQueryOrder;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyReceiptOrder;
import com.born.fcs.pm.ws.order.council.CouncilVoteProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.field.DataTypeEnum;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;
import com.born.fcs.pm.ws.service.report.result.ReportItem;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;

@Controller
@RequestMapping("projectMg/fCapitalAppropriationApply")
public class FCapitalAppropriationApplyController extends WorkflowBaseController {
	
	final static String vm_path = "/projectMg/cashMg/fundPayment/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "payeeOrder.plannedTime", "strokeTime" };
	}
	
	@Override
	protected String[] getMoneyInputNameArray() {
		return new String[] { "paymentAmount" };
	}
	
	/**
	 * 资金划付申请单列表
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String capitalAppropriationApplyList(FCapitalAppropriationApplyQueryOrder queryOrder,
												Model model) {
		try {
			if (queryOrder == null)
				queryOrder = new FCapitalAppropriationApplyQueryOrder();
			
			setSessionLocalInfo2Order(queryOrder);
			model.addAttribute("isBusiManager", isBusiManager());//客户经理
			model.addAttribute("isLegalManager", isLegalManager());//法务经理
			model.addAttribute("isLegalManagerLD", isLegalManagerLD());//法务经理-领导
			model.addAttribute("isFinancialYFG", isFinancialYFG());//财务应付岗
			model.addAttribute("isRiskZY", isRiskZY());//风险部职员（风险经理）
			model.addAttribute("isRiskLD", isRiskLD());//风险部领导
			model.addAttribute("isXinHuiBusiManager", DataPermissionUtil.isXinHuiBusiManager());
			model.addAttribute("isFinancialZjlc", DataPermissionUtil.isFinancialZjlc());//资金理财
			if (StringUtil.isEmpty(queryOrder.getSortCol())) {
				queryOrder.setSortCol("form_add_time");
				queryOrder.setSortOrder("DESC");
			}
			if (!StringUtil.equals(queryOrder.getIsSimple(), "IS")) {
				queryOrder.setIsSimple("NO");
			}
			QueryBaseBatchResult<FCapitalAppropriationApplyInfo> batchResult = fCapitalAppropriationApplyServiceClient
				.query(queryOrder);
			model.addAttribute("conditions", queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("formStatus", FormStatusEnum.getAllEnum());
		} catch (Exception e) {
			logger.error("查询资金划付申请单列表出错");
		}
		
		return vm_path + "list.vm";
	}
	
	/**
	 * 查看资金划付详情
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("viewCapitalAppropriationApply.htm")
	public String viewCapitalAppropriationApply(long formId, HttpServletRequest request, Model model) {
		viewApply(formId, request, model);
		return vm_path + "view_apply.vm";
	}
	
	/**
	 * 编辑资金划付
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("editCapitalAppropriationApply.htm")
	public String editCapitalAppropriationApply(long formId, HttpServletRequest request, Model model) {
		FormInfo form = formServiceClient.findByFormId(formId);
		
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		model.addAttribute("form", form);
		FCapitalAppropriationApplyInfo applyInfo = fCapitalAppropriationApplyServiceClient
			.findCapitalAppropriationApplyByFormId(formId);
		if (applyInfo.getProjectType().code().equals("NOT_FINANCIAL_PRODUCT")) {//非理财类
			List<Money> amountList = fCapitalAppropriationApplyServiceClient.getLimitByProject(
				applyInfo.getProjectCode(), applyInfo.getProjectType(), applyInfo.getApplyId(),
				true);
			ProjectSimpleDetailInfo projectInfo = projectServiceClient
				.querySimpleDetailInfo(applyInfo.getProjectCode());
			
			boolean isBankFinancing = false;
			if (ProjectUtil.isBankFinancing(projectInfo.getBusiType())) {
				isBankFinancing = true;
			}
			//是否银行融资渠道
			model.addAttribute("isBankFinancing", isBankFinancing);
			//委贷放款  金额不能超过目前可放款的总额
			model.addAttribute("commissionLoan", amountList.get(0));
			model.addAttribute("compensatoryPrincipal", amountList.get(1));
			model.addAttribute("compensatoryInterest", amountList.get(2));
			//退还客户保证金额额度限制
			model.addAttribute("customerDepositRefund", amountList.get(3));
			model.addAttribute("refundAmount", amountList.get(4));
			//资产受让
			model.addAttribute("assetsTransferee", amountList.get(5));
			model.addAttribute("projectInfo", projectInfo);
			List<PaymentMenthodEnum> listEnum = fCapitalAppropriationApplyServiceClient
				.getPaymentMenthodEnum(applyInfo.getProjectCode());
			
			if (StringUtil.equals(applyInfo.getIsSimple(), "IS")) {//被扣划冻结
				listEnum.clear();
				listEnum.add(PaymentMenthodEnum.COMPENSATORY_PRINCIPAL);
				listEnum.add(PaymentMenthodEnum.COMPENSATORY_INTEREST);
				listEnum.add(PaymentMenthodEnum.COMPENSATORY_PENALTY);
				listEnum.add(PaymentMenthodEnum.COMPENSATORY_LIQUIDATED_DAMAGES);
				listEnum.add(PaymentMenthodEnum.COMPENSATORY_OTHER);
			}
			
			if (ProjectUtil.isEntrusted(projectInfo.getBusiType())) {
				model.addAttribute("isEntrusted", "true");
				//showProjectApproval(applyInfo.getProjectCode(), model);//批复
			}
			model.addAttribute("paymentMenthod", listEnum);//付款方式
			model.addAttribute("outBizNo", 0);
			//渠道信息提示  3代表评审阶段
			//			ChannalInfo channalInfo = chancel(applyInfo.getProjectCode());
			//			if (channalInfo != null) {
			//				model.addAttribute("channelName", channalInfo.getChannelName());//渠道名称
			//				model.addAttribute("compensatoryLimit", channalInfo.getCompensatoryLimit());//代偿期限
			//				model.addAttribute("dayType", channalInfo.getDayType() == "ZR" ? "自然日" : "工作日");//工作日，自然日
			//			}
			setChannelInfo(applyInfo.getProjectCode(), model);
			model.addAttribute("ishaveApproval", isHaveApproval(projectInfo));
		} else {//理财类
		
			model.addAttribute("financialProjectCode", applyInfo.getProjectCode()); //通过这个查看会议纪要
			
			List<PaymentMenthodEnum> listEnum = new ArrayList<PaymentMenthodEnum>();
			long trasferApplyId = applyInfo.getOutBizNo();
			model.addAttribute("outBizNo", trasferApplyId);
			
			if (applyInfo.getOutBizNo() > 0) {
				listEnum.add(PaymentMenthodEnum.FINANCIAL_PRODUCT_BUY_BACK);
				ProjectFinancialInfo projectFinancialInfo = financialProjectServiceClient
					.queryByCode(applyInfo.getProjectCode());
				model.addAttribute("projectFinancialInfo", projectFinancialInfo);
				model.addAttribute("price", projectFinancialInfo.getPrice()); //单价
				model.addAttribute("transferTrade",
					financialProjectTransferServiceClient.queryTradeByApplyId(trasferApplyId));
				model.addAttribute("isBack", "true");
			} else {
				List<Money> amountList = fCapitalAppropriationApplyServiceClient.getLimitByProject(
					applyInfo.getProjectCode(), applyInfo.getProjectType(), applyInfo.getApplyId(),
					true);
				model.addAttribute("financialProduct", amountList.get(0));
				listEnum.add(PaymentMenthodEnum.FINANCIAL_PRODUCT);
				FProjectFinancialInfo projectFinancialInfo = financialProjectSetupServiceClient
					.queryByProjectCode(applyInfo.getProjectCode());
				model.addAttribute("projectFinancialInfo", projectFinancialInfo);
				model.addAttribute("price", projectFinancialInfo.getPrice()); //单价
			}
			
			model.addAttribute("paymentMenthod", listEnum);//付款方式 
			model.addAttribute("isHasSummary", isHasSummary(applyInfo.getProjectCode()));
			model.addAttribute("councilInfo", councilProjectVote(applyInfo.getProjectCode()));
		}
		
		List<FCapitalAppropriationApplyFeeInfo> fCapitalAppropriationApplyFeeInfoList = fCapitalAppropriationApplyServiceClient
			.findByApplyId(applyInfo.getApplyId());
		model.addAttribute("projectType", applyInfo.getProjectType().code());//项目类型
		
		model.addAttribute("applyInfo", applyInfo);//资金划付
		
		model.addAttribute("isEdit", "true");//是否编辑
		model.addAttribute("fCapitalAppropriationApplyFeeInfoList",
			fCapitalAppropriationApplyFeeInfoList);
		model.addAttribute("isCompensatory", isCompensatory(applyInfo.getProjectCode()));
		model.addAttribute("accountTypeList", SubjectAccountTypeEnum.getAllEnum());
		model.addAttribute("isSimple", applyInfo.getIsSimple());
		queryCommonAttachmentData(model, "PM_" + form.getFormId(),
			CommonAttachmentTypeEnum.FORM_ATTACH);
		queryCommonAttachmentData(model, "PM_" + form.getFormId(),
				CommonAttachmentTypeEnum.OTHER);
		
		return vm_path + "add_apply.vm";
	}
	
	/**
	 * 新增资金划付
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("addCapitalAppropriationApply.htm")
	public String addCapitalAppropriationApply(String projectCode, String projectType,
												String outBizNo, String backAmount,
												HttpServletRequest request, Model model) {
		
		if (projectType.equals("NOT_FINANCIAL_PRODUCT")) {//非理财类
			List<Money> amountList = fCapitalAppropriationApplyServiceClient.getLimitByProject(
				projectCode, FCapitalAppropriationApplyTypeEnum.NOT_FINANCIAL_PRODUCT, -1, false);
			ProjectSimpleDetailInfo projectInfo = projectServiceClient
				.querySimpleDetailInfo(projectCode);
			boolean isBankFinancing = false;
			if (ProjectUtil.isBankFinancing(projectInfo.getBusiType())) {
				isBankFinancing = true;
			}
			//是否银行融资渠道
			model.addAttribute("isBankFinancing", isBankFinancing);
			
			//委贷放款  金额不能超过目前可放款的总额
			model.addAttribute("commissionLoan", amountList.get(0));
			model.addAttribute("compensatoryPrincipal", amountList.get(1));
			model.addAttribute("compensatoryInterest", amountList.get(2));
			//退还客户保证金额度限制
			model.addAttribute("customerDepositRefund", amountList.get(3));
			model.addAttribute("refundAmount", amountList.get(4));
			//资产受让
			model.addAttribute("assetsTransferee", amountList.get(5));
			List<PaymentMenthodEnum> listEnum = fCapitalAppropriationApplyServiceClient
				.getPaymentMenthodEnum(projectCode);
			
			if (StringUtil.equals(request.getParameter("isSimple"), "IS")) {//被扣划冻结
				listEnum.clear();
				listEnum.add(PaymentMenthodEnum.COMPENSATORY_PRINCIPAL);
				listEnum.add(PaymentMenthodEnum.COMPENSATORY_INTEREST);
				listEnum.add(PaymentMenthodEnum.COMPENSATORY_PENALTY);
				listEnum.add(PaymentMenthodEnum.COMPENSATORY_LIQUIDATED_DAMAGES);
				listEnum.add(PaymentMenthodEnum.COMPENSATORY_OTHER);
			}
			
			if (ProjectUtil.isEntrusted(projectInfo.getBusiType())) {
				model.addAttribute("isEntrusted", "true");
				//showProjectApproval(projectCode, model);//批复
			}
			
			model.addAttribute("paymentMenthod", listEnum);//付款方式
			model.addAttribute("projectInfo", projectInfo);
			model.addAttribute("outBizNo", 0);
			//渠道信息提示  3代表评审阶段
			//			ChannalInfo channalInfo = chancel(projectCode);
			//			if (channalInfo != null) {
			//				model.addAttribute("channelName", channalInfo.getChannelName());//渠道名称
			//				model.addAttribute("compensatoryLimit", channalInfo.getCompensatoryLimit());//代偿期限
			//				model.addAttribute("dayType", channalInfo.getDayType() == "ZR" ? "自然日" : "工作日");//工作日，自然日
			//			}
			setChannelInfo(projectCode, model);
			
			model.addAttribute("ishaveApproval", isHaveApproval(projectInfo));
		} else {//理财类
		
			model.addAttribute("financialProjectCode", projectCode); //通过这个查看会议纪要
			List<PaymentMenthodEnum> listEnum = new ArrayList<PaymentMenthodEnum>();
			if (StringUtil.isNotEmpty(outBizNo) && StringUtil.isNotEmpty(backAmount)) {
				//划付种类
				listEnum.add(PaymentMenthodEnum.FINANCIAL_PRODUCT_BUY_BACK);
				model.addAttribute("paymentMenthod", listEnum);
				
				//回购查项目
				ProjectFinancialInfo projectFinancialInfo = financialProjectServiceClient
					.queryByCode(projectCode);
				model.addAttribute("projectFinancialInfo", projectFinancialInfo);
				//model.addAttribute("financialProduct", amountList.get(0));
				model.addAttribute("price", projectFinancialInfo.getPrice());
				
				long trasferApplyId = Long.parseLong(outBizNo);
				model.addAttribute("outBizNo", trasferApplyId);
				model.addAttribute("backAmount", new Money(backAmount));
				model.addAttribute("transferTrade",
					financialProjectTransferServiceClient.queryTradeByApplyId(trasferApplyId));
				model.addAttribute("isBack", "true");
			} else {
				List<Money> amountList = fCapitalAppropriationApplyServiceClient.getLimitByProject(
					projectCode, FCapitalAppropriationApplyTypeEnum.FINANCIAL_PRODUCT, -1, true);
				//划付种类
				listEnum.add(PaymentMenthodEnum.FINANCIAL_PRODUCT);
				model.addAttribute("paymentMenthod", listEnum);
				
				//购买查立项
				FProjectFinancialInfo projectFinancialInfo = financialProjectSetupServiceClient
					.queryByProjectCode(projectCode);
				model.addAttribute("projectFinancialInfo", projectFinancialInfo);
				model.addAttribute("financialProduct", amountList.get(0));
				model.addAttribute("price", projectFinancialInfo.getPrice());
				model.addAttribute("outBizNo", 0);
				
			}
			model.addAttribute("isHasSummary", isHasSummary(projectCode));
			model.addAttribute("councilInfo", councilProjectVote(projectCode));
		}
		model.addAttribute("projectType", projectType);//项目类型
		model.addAttribute("accountTypeList", SubjectAccountTypeEnum.getAllEnum());
		model.addAttribute("isCompensatory", isCompensatory(projectCode));
		model.addAttribute("isSimple", request.getParameter("isSimple"));
		return vm_path + "add_apply.vm";
	}
	
	/**
	 * 去新增资金划付
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("toAddCapitalAppropriationApply.htm")
	public String toAddCapitalAppropriationApply(HttpServletRequest request, Model model) {
		FCapitalAppropriationApplyInfo applyInfo = new FCapitalAppropriationApplyInfo();
		applyInfo.setIsSimple(request.getParameter("isSimple"));
		model.addAttribute("paymentMenthod", PaymentMenthodEnum.getAllEnum());//付款方式
		if (StringUtil.equals(request.getParameter("isSimple"), "IS")) {//被扣划冻结
			List<PaymentMenthodEnum> listEnum = Lists.newArrayList();
			listEnum.add(PaymentMenthodEnum.COMPENSATORY_PRINCIPAL);
			listEnum.add(PaymentMenthodEnum.COMPENSATORY_INTEREST);
			listEnum.add(PaymentMenthodEnum.COMPENSATORY_PENALTY);
			listEnum.add(PaymentMenthodEnum.COMPENSATORY_LIQUIDATED_DAMAGES);
			listEnum.add(PaymentMenthodEnum.COMPENSATORY_OTHER);
			model.addAttribute("paymentMenthod", listEnum);//付款方式
		}
		model.addAttribute("applyInfo", applyInfo);
		model.addAttribute("projectType", request.getParameter("projectType"));
		model.addAttribute("accountTypeList", SubjectAccountTypeEnum.getAllEnum());
		model.addAttribute("isSimple", request.getParameter("isSimple"));
		return vm_path + "add_apply.vm";
	}
	
	/**
	 * 保存资金划付
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveCapitalAppropriationApply.htm")
	@ResponseBody
	public JSONObject saveCapitalAppropriationApply(HttpServletRequest request,
													HttpServletResponse response,
													FCapitalAppropriationApplyOrder order,
													String projectCode, Model model) {
		String tipPrefix = "保存资金划付申请单";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			// 初始化Form验证信息
			FormBaseResult result = null;
			order.setCheckIndex(0);
			order.setRelatedProjectCode(order.getProjectCode());
			order.setFormId(order.getFormId());
			if (StringUtil.equals(order.getIsSimple(), "IS")) {
				order.setFormCode(FormCodeEnum.FCAPITAL_APPROPRIATION_APPLY_COMP);
			} else {
				order.setFormCode(FormCodeEnum.FCAPITAL_APPROPRIATION_APPLY);
			}
			order.setUserId(sessionLocal.getUserId());
			order.setUserName(sessionLocal.getRealName());
			order.setUserAccount(sessionLocal.getUserName());
			setSessionLocalInfo2Order(order);
			result = fCapitalAppropriationApplyServiceClient.save(order);
			jsonObject = toJSONResult(jsonObject, result, "提交资金划付申请单成功", null);
			jsonObject.put("formId", result.getFormInfo().getFormId());
			
			if (result != null && result.isSuccess()) {
				addAttachment(request, "PM_" + result.getFormInfo().getFormId(), null,
					CommonAttachmentTypeEnum.FORM_ATTACH);
				addAttachment(request, "PM_" + result.getFormInfo().getFormId(), null,
						CommonAttachmentTypeEnum.OTHER);
			}
			
			if (result != null && result.isSuccess() && order.getCheckStatus() == 1) {
				jsonObject.put("success", true);
				jsonObject.put("status", "SUBMIT");
				jsonObject.put("message", "提交成功");
			} else if (result != null && result.isSuccess() && order.getCheckStatus() == 0) {
				jsonObject.put("success", true);
				jsonObject.put("status", "hold");
				jsonObject.put("message", "暂存成功");
			} else {
				jsonObject.put("success", false);
				jsonObject.put("message", result.getMessage());
			}
		} catch (Exception e) {
			jsonObject = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 保存资金划付-回执
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveReceipt.htm")
	@ResponseBody
	public JSONObject saveReceipt(HttpServletRequest request, HttpServletResponse response,
									FCapitalAppropriationApplyReceiptOrder order,
									String projectCode, Model model) {
		String tipPrefix = "保存资金划付申请单-回执信息";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			// 初始化Form验证信息
			FcsBaseResult result = null;
			order.setFormId(order.getFormId());
			order.setUserId(sessionLocal.getUserId());
			order.setUserName(sessionLocal.getRealName());
			order.setUserAccount(sessionLocal.getUserName());
			setSessionLocalInfo2Order(order);
			result = fCapitalAppropriationApplyServiceClient.saveReceipt(order);
			jsonObject = toJSONResult(jsonObject, result, "资金划付申请单回执信息保存成功", null);
			
			if (result.isSuccess()) {
				jsonObject.put("success", true);
				jsonObject.put("message", "保存成功");
			} else {
				jsonObject.put("success", false);
				jsonObject.put("message", result.getMessage());
			}
		} catch (Exception e) {
			jsonObject = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 查看资金划付详情-回执信息
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("viewReceipt.htm")
	public String viewReceipt(long formId, Model model) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		FCapitalAppropriationApplyInfo applyInfo = fCapitalAppropriationApplyServiceClient
			.findCapitalAppropriationApplyByFormId(formId);
		if (applyInfo.getProjectType().code().equals("NOT_FINANCIAL_PRODUCT")) {//非理财类
			ProjectSimpleDetailInfo projectInfo = projectServiceClient
				.querySimpleDetailInfo(applyInfo.getProjectCode());
			model.addAttribute("projectInfo", projectInfo);
			model.addAttribute("outBizNo", 0);
			//showProjectApproval(applyInfo.getProjectCode(), model);
		} else {
			long trasferApplyId = applyInfo.getOutBizNo();
			model.addAttribute("outBizNo", trasferApplyId);
			if (trasferApplyId > 0) { //回购
				ProjectFinancialInfo projectFinancialInfo = financialProjectServiceClient
					.queryByCode(applyInfo.getProjectCode());
				model.addAttribute("projectFinancialInfo", projectFinancialInfo);
				model.addAttribute("transferTrade",
					financialProjectTransferServiceClient.queryTradeByApplyId(trasferApplyId));
			} else { //购买
				FProjectFinancialInfo projectFinancialInfo = financialProjectSetupServiceClient
					.queryByProjectCode(applyInfo.getProjectCode());
				model.addAttribute("projectFinancialInfo", projectFinancialInfo);
			}
			
		}
		List<FCapitalAppropriationApplyFeeInfo> fCapitalAppropriationApplyFeeInfoList = fCapitalAppropriationApplyServiceClient
			.findByApplyId(applyInfo.getApplyId());
		model.addAttribute("projectType", applyInfo.getProjectType().code());//项目类型
		model.addAttribute("paymentMenthod", PaymentMenthodEnum.getAllEnum());//付款方式
		model.addAttribute("applyInfo", applyInfo);//资金划付
		model.addAttribute("form", form);
		
		model.addAttribute("fCapitalAppropriationApplyFeeInfoList",
			fCapitalAppropriationApplyFeeInfoList);
		model.addAttribute("isCompensatory", isCompensatory(applyInfo.getProjectCode()));
		model.addAttribute("accountTypeList", SubjectAccountTypeEnum.getAllEnum());
		return vm_path + "view_apply.vm";
	}
	
	/**
	 * 资金划付审核 - 财务应付岗审核（确认财务信息）
	 */
	@RequestMapping("audit/financeAffirm.htm")
	public String chooseAssessCompany(long formId, String toPage, HttpServletRequest request,
										Integer toIndex, Model model, HttpSession session) {
		//SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		model.addAttribute("financeAffirm", "YES");
		model.addAttribute("isEdit", "true");//是否编辑
		audit(formId, toPage, request, toIndex, model, session);
		FCapitalAppropriationApplyInfo applyInfo = fCapitalAppropriationApplyServiceClient
			.findCapitalAppropriationApplyByFormId(formId);
		if (applyInfo.getFinanceAffirmInfo() != null) {//说明已有财务确认信息
			viewApply(formId, request, model);
			return vm_path + "view_apply.vm";
		} else {
			return vm_path + "audit_finance_affirm.vm";
		}
	}
	
	/**
	 * 资金划付审核
	 *
	 * @param formId
	 * @param model
	 * @return
	 */
	
	@RequestMapping("audit.htm")
	public String audit(long formId, String toPage, HttpServletRequest request, Integer toIndex,
						Model model, HttpSession session) {
		initWorkflow(model, viewApply(formId, request, model), request.getParameter("taskId"));
		queryCommonAttachmentData(model, "PM_" + formId, CommonAttachmentTypeEnum.FORM_ATTACH);
		queryCommonAttachmentData(model, "PM_" + formId, CommonAttachmentTypeEnum.OTHER);
		model.addAttribute("chooseLegalManager", request.getParameter("chooseLegalManager"));
		return vm_path + "audit_apply.vm";
	}
	
	/**
	 * 打印
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("print.htm")
	public String print(long formId, HttpServletRequest request, Model model) {
		viewApply(formId, request, model);
		return vm_path + "view_apply.vm";
	}
	
	/**
	 * 查看划付单
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	private FormInfo viewApply(long formId, HttpServletRequest request, Model model) {
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		model.addAttribute("form", form);
		
		FCapitalAppropriationApplyInfo applyInfo = fCapitalAppropriationApplyServiceClient
			.findCapitalAppropriationApplyByFormId(formId);
		if (applyInfo.getProjectType().code().equals("NOT_FINANCIAL_PRODUCT")) {//非理财类
			ProjectSimpleDetailInfo projectInfo = projectServiceClient
				.querySimpleDetailInfo(applyInfo.getProjectCode());
			
			boolean isBankFinancing = false;
			if (ProjectUtil.isBankFinancing(projectInfo.getBusiType())) {
				isBankFinancing = true;
			}
			//是否银行融资渠道
			model.addAttribute("isBankFinancing", isBankFinancing);
			model.addAttribute("projectInfo", projectInfo);
			model.addAttribute("outBizNo", 0);
			if (ProjectUtil.isEntrusted(projectInfo.getBusiType())) {
				model.addAttribute("isEntrusted", "true");
				//showProjectApproval(applyInfo.getProjectCode(), model);//批复
			}
			model.addAttribute("ishaveApproval", isHaveApproval(projectInfo));//是否有批復 
		} else {
			model.addAttribute("financialProjectCode", applyInfo.getProjectCode()); //通过这个查看会议纪要
			//回购
			long trasferApplyId = applyInfo.getOutBizNo();
			if (trasferApplyId > 0) {
				ProjectFinancialInfo projectFinancialInfo = financialProjectServiceClient
					.queryByCode(applyInfo.getProjectCode());
				model.addAttribute("projectFinancialInfo", projectFinancialInfo);
				model.addAttribute("outBizNo", trasferApplyId);
				model.addAttribute("transferTrade",
					financialProjectTransferServiceClient.queryTradeByApplyId(trasferApplyId));
			} else { //购买
				FProjectFinancialInfo projectFinancialInfo = financialProjectSetupServiceClient
					.queryByProjectCode(applyInfo.getProjectCode());
				model.addAttribute("projectFinancialInfo", projectFinancialInfo);
			}
			model.addAttribute("isHasSummary", isHasSummary(applyInfo.getProjectCode()));
			model.addAttribute("councilInfo", councilProjectVote(applyInfo.getProjectCode()));
		}
		List<FCapitalAppropriationApplyFeeInfo> fCapitalAppropriationApplyFeeInfoList = fCapitalAppropriationApplyServiceClient
			.findByApplyId(applyInfo.getApplyId());
		
		model.addAttribute("projectType", applyInfo.getProjectType().code());//项目类型
		model.addAttribute("paymentMenthod", PaymentMenthodEnum.getAllEnum());//付款方式
		model.addAttribute("applyInfo", applyInfo);//资金划付
		
		model.addAttribute("fCapitalAppropriationApplyFeeInfoList",
			fCapitalAppropriationApplyFeeInfoList);
		model.addAttribute("isCompensatory", isCompensatory(applyInfo.getProjectCode()));
		model.addAttribute("isShowChanal", isShowChanal(fCapitalAppropriationApplyFeeInfoList));
		//渠道信息提示  3代表评审阶段
		//		ChannalInfo channalInfo = chancel(applyInfo.getProjectCode());
		//		if (channalInfo != null) {
		//			model.addAttribute("channelName", channalInfo.getChannelName());//渠道名称
		//			model.addAttribute("compensatoryLimit", channalInfo.getCompensatoryLimit());//代偿期限
		//			model.addAttribute("dayType", channalInfo.getDayType() == "ZR" ? "自然日" : "工作日");//工作日，自然日
		//		}
		setChannelInfo(applyInfo.getProjectCode(), model);
		
		Money totalMoney = Money.zero();
		for (FCapitalAppropriationApplyFeeInfo feeInfo : fCapitalAppropriationApplyFeeInfoList) {
			totalMoney = totalMoney.add(feeInfo.getAppropriateAmount());
		}
		model.addAttribute("totalMoney", totalMoney);
		
		model.addAttribute("isFinancialYFG", isFinancialYFG());//财务应付岗
		model.addAttribute("isFinancialYSG", isFinancialYSG());//财务应收岗
		FFinanceAffirmInfo financeAffirmInfo = applyInfo.getFinanceAffirmInfo();
		if (financeAffirmInfo != null) {
			model.addAttribute("financeAffirmInfo", financeAffirmInfo);
			model.addAttribute("isAffirm", "true");
		}
		model.addAttribute("isCapitalAppropriation", "true");
		setAuditHistory2Page(form, model);
		
		model.addAttribute("accountTypeList", SubjectAccountTypeEnum.getAllEnum());
		
		queryCommonAttachmentData(model, "PM_" + form.getFormId(),
			CommonAttachmentTypeEnum.FORM_ATTACH);
		queryCommonAttachmentData(model, "PM_" + form.getFormId(),
				CommonAttachmentTypeEnum.OTHER);
		
		return form;
	}
	
	private Boolean isCompensatory(String projectCode) {
		Boolean isCompensatory = false;
		List<FCouncilSummaryRiskHandleInfo> listCouncilSummaryRiskHandleInfo = councilSummaryServiceClient
			.queryRiskHandleCsByProjectCode(projectCode);
		if (listCouncilSummaryRiskHandleInfo != null) {
			for (FCouncilSummaryRiskHandleInfo fCouncilSummaryRiskHandleInfo : listCouncilSummaryRiskHandleInfo) {
				if (fCouncilSummaryRiskHandleInfo.getIsComp() == BooleanEnum.IS) {//代偿
					isCompensatory = true;
					break;
				}
			}
		}
		return isCompensatory;
	}
	
	//渠道信息
	private ChannalInfo chancel(String projectCode) {
		ChannalInfo channalInfo = new ChannalInfo();
		//渠道信息提示  3代表评审阶段
		ViewChannelProjectAllPhasInfo viewChannelProjectAllPhasInfo = crmUseServiceClient
			.queryChannelByprojectCodeAndPhas(projectCode, 3);
		if (viewChannelProjectAllPhasInfo != null) {
			channalInfo = channalClient.queryById(viewChannelProjectAllPhasInfo
				.getCapitalChannelId());
		}
		return channalInfo;
	}
	
	//渠道信息提示
	private void setChannelInfo(String projectCode, Model model) {
		List<ProjectChannelRelationInfo> capitalChannels = projectChannelRelationServiceClient
			.queryCapitalChannel(projectCode);
		if (ListUtil.isNotEmpty(capitalChannels)) {
			String channelName = null;
			String channelTips = "代偿期限为：";
			for (ProjectChannelRelationInfo capitalChannel : capitalChannels) {
				ChannalInfo channalInfo = channalClient.queryById(capitalChannel.getChannelId());
				if (channalInfo != null) {
					if (channelName == null) {
						channelName = channalInfo.getChannelName();
						channelTips += (channalInfo.getCompensatoryLimit() == -1 ? "无代偿期限"
							: channalInfo.getCompensatoryLimit())
										+ (channalInfo.getDayType() == "ZR" ? "自然日" : "工作日");
					} else {
						channelName += "，" + channalInfo.getChannelName();
						channelTips += "，"
										+ (channalInfo.getCompensatoryLimit() == -1 ? "无代偿期限"
											: channalInfo.getCompensatoryLimit())
										+ (channalInfo.getDayType() == "ZR" ? "自然日" : "工作日");
					}
				}
			}
			model.addAttribute("channelName", channelName);
			model.addAttribute("channelTips", channelTips);
		}
	}
	
	private Boolean isShowChanal(	List<FCapitalAppropriationApplyFeeInfo> fCapitalAppropriationApplyFeeInfoList) {
		Boolean isShow = false;
		if (fCapitalAppropriationApplyFeeInfoList != null) {
			for (FCapitalAppropriationApplyFeeInfo feeInfo : fCapitalAppropriationApplyFeeInfoList) {
				if (feeInfo.getAppropriateReason() == PaymentMenthodEnum.COMPENSATORY_PRINCIPAL
					|| feeInfo.getAppropriateReason() == PaymentMenthodEnum.COMPENSATORY_INTEREST
					|| feeInfo.getAppropriateReason() == PaymentMenthodEnum.COMPENSATORY_PENALTY
					|| feeInfo.getAppropriateReason() == PaymentMenthodEnum.COMPENSATORY_LIQUIDATED_DAMAGES
					|| feeInfo.getAppropriateReason() == PaymentMenthodEnum.COMPENSATORY_OTHER) {
					isShow = true;
					break;
				}
			}
		}
		return isShow;
	}
	
	private CouncilInfo councilProjectVote(String projectCode) {
		CouncilInfo info = null;
		CouncilVoteProjectQueryOrder order = new CouncilVoteProjectQueryOrder();
		order.setProjectCode(projectCode);
		QueryBaseBatchResult<CouncilProjectVoteResultInfo> result = councilProjectServiceClient
			.queryProjectVoteResult(order);
		List<CouncilProjectVoteResultInfo> infos = result.getPageList();
		CouncilProjectVoteResultInfo resultInfo = new CouncilProjectVoteResultInfo();
		if (infos != null) {
			for (CouncilProjectVoteResultInfo councilProjectVoteResultInfo : infos) {
				resultInfo = councilProjectVoteResultInfo;
				break;
			}
		}
		if (resultInfo != null) {
			info = councilServiceClient.queryCouncilById(resultInfo.getCouncilId());
		}
		return info;
	}
	
	private String isHasSummary(String projectCode) {
		String isHasSummary = "false";
		CouncilInfo councilInfo = councilProjectVote(projectCode);
		if (councilInfo != null) {
			if (councilInfo.getCouncilTypeCode() == CouncilTypeEnum.GM_WORKING) {//总经理办公会
				isHasSummary = "true";
			}
			if (councilInfo.getCouncilTypeCode() == CouncilTypeEnum.PROJECT_REVIEW) {
				FCouncilSummaryProjectInfo councilSummaryProject = councilSummaryServiceClient
					.queryProjectCsByProjectCode(projectCode);
				if (councilSummaryProject == null) {
					isHasSummary = "false";
				} else {
					isHasSummary = "true";
				}
			}
		} else {
			isHasSummary = "false";
		}
		return isHasSummary;
	}
	
	//承销项目判断是否有批复
	private String isHaveApproval(ProjectSimpleDetailInfo info) {
		if (info == null) {
			return null;
		}
		if (ProjectUtil.isUnderwriting(info.getBusiType()) && StringUtil.isEmpty(info.getSpCode())) {
			return "NO";
		}
		if (StringUtil.isEmpty(info.getSpCode())) {
			return "NO";
		}
		return "IS";
	}
	
	/***
	 * 导出
	 */
	@RequestMapping("export.htm")
	public String export(HttpServletRequest request, HttpServletResponse response, Model model) {
		FCapitalAppropriationApplyQueryOrder order = new FCapitalAppropriationApplyQueryOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		if (StringUtil.isEmpty(order.getSortCol())) {
			order.setSortCol("form_add_time");
			order.setSortOrder("DESC");
		}
		setSessionLocalInfo2Order(order);
		if (!StringUtil.equals(order.getIsSimple(), "IS")) {
			order.setIsSimple("NO");
		}
		QueryBaseBatchResult<CapitalExportInfo> batchResult = fCapitalAppropriationApplyServiceClient
			.capitalExport(order);
		List<CapitalExportInfo> infos = batchResult.getPageList();
		if (infos != null) {
			makeExcel(infos, request, response);
		}
		return null;
	}
	
	public void makeExcel(List<CapitalExportInfo> infos, HttpServletRequest request,
							HttpServletResponse response) {
		ReportDataResult dataResult = makeResult(infos);
		ReportTemplate reportTemplate = new ReportTemplate();
		if (StringUtil.equals(request.getParameter("isSimple"), "IS")) {
			reportTemplate.setReportName("被扣划冻结明细表");
		} else {
			reportTemplate.setReportName("资金划付明细表");
		}
		//多行表头
		//		String[][] head = new String[1][7];
		//		head[0] = new String[] { "项目编码", "发行机构", "申购日期", "到期日", "购买期限", "利率（%/年）", "购买金额（元）" };
		//		reportTemplate.setColHeadString(head);
		
		reportTemplate.setMergeRow(false);
		reportTemplate.setMergeColCount(2);
		TableBuilder builder = new TableBuilder(dataResult, reportTemplate,
			TableBuilder.Table_Option_Excel, false);
		builder.init();
		builder.dataBind(request, response);
		//System.out.print(builder.getString());
	}
	
	private static ReportDataResult makeResult(List<CapitalExportInfo> infos) {
		
		ReportDataResult dataResult = new ReportDataResult();
		try {
			// 头
			//		dataResult.getFcsFields().add(new FcsField("xuhao", "序号", null));
			dataResult.getFcsFields().add(new FcsField("projectCode", "项目编号", null));
			dataResult.getFcsFields().add(new FcsField("customerName", "客户名称", null));
			dataResult.getFcsFields().add(new FcsField("busiTypeName", "业务品种", null));
			dataResult.getFcsFields().add(new FcsField("amount", "授信金额（元）", null));
			dataResult.getFcsFields().add(new FcsField("appropriateReason", "资金划付事由", null));
			dataResult.getFcsFields().add(
				new FcsField("appropriateAmount", "申请划付金额（元）", null, DataTypeEnum.MONEY));
			dataResult.getFcsFields().add(
				new FcsField("payAmount", "实际付款金额（元）", null, DataTypeEnum.MONEY));
			dataResult.getFcsFields().add(new FcsField("payTime", "付款时间", null));
			
			List<DataListItem> dataListItems = Lists.newArrayList();
			
			// 信息
			for (int i = 0; i < infos.size(); i++) {
				ReportItem reportItem = new ReportItem();
				List<ReportItem> valueList = Lists.newArrayList();
				DataListItem item = new DataListItem();
				CapitalExportInfo info = infos.get(i);
				//非理财
				if (info.getProjectType() != null
					&& info.getProjectType() == FCapitalAppropriationApplyTypeEnum.NOT_FINANCIAL_PRODUCT) {
					
					//				realHasAmount.addTo(info.getActualPrice().multiply(info.getOriginalHoldNum()));
					//				totalBuyAmount.addTo(info.getActualPrice().multiply(info.getActualBuyNum()));
					//						reportItem = new ReportItem();
					//						reportItem.setKey("xuhao");
					//						reportItem.setValue((i + 1) + "");
					//						reportItem.setDataTypeEnum(DataTypeEnum.STRING);
					//						valueList.add(reportItem);
					
					reportItem = new ReportItem();
					reportItem.setKey("projectCode");
					reportItem.setValue(info.getProjectCode());
					reportItem.setDataTypeEnum(DataTypeEnum.STRING);
					valueList.add(reportItem);
					
					reportItem = new ReportItem();
					reportItem.setKey("customerName");
					reportItem.setValue(info.getCustomerName());
					reportItem.setDataTypeEnum(DataTypeEnum.STRING);
					valueList.add(reportItem);
					
					reportItem = new ReportItem();
					reportItem.setKey("busiTypeName");
					reportItem.setValue(info.getBusiTypeName());
					reportItem.setDataTypeEnum(DataTypeEnum.STRING);
					valueList.add(reportItem);
					
					reportItem = new ReportItem();
					reportItem.setKey("amount");
					reportItem.setValue(info.getAmount().toStandardString());
					reportItem.setDataTypeEnum(DataTypeEnum.STRING);
					valueList.add(reportItem);
					
					reportItem = new ReportItem();
					reportItem.setKey("appropriateReason");
					reportItem.setValue(info.getAppropriateReason().message());
					reportItem.setDataTypeEnum(DataTypeEnum.STRING);
					valueList.add(reportItem);
					
					reportItem = new ReportItem();
					reportItem.setKey("appropriateAmount");
					reportItem.setValue(info.getAppropriateAmount().toStandardString());
					reportItem.setDataTypeEnum(DataTypeEnum.STRING);
					valueList.add(reportItem);
					
					reportItem = new ReportItem();
					reportItem.setKey("payAmount");
					reportItem.setValue(info.getPayAmount().toStandardString());
					reportItem.setDataTypeEnum(DataTypeEnum.STRING);
					valueList.add(reportItem);
					
					reportItem = new ReportItem();
					reportItem.setKey("payTime");
					reportItem.setValue(DateUtil.dtSimpleFormat(info.getPayTime()));
					reportItem.setDataTypeEnum(DataTypeEnum.STRING);
					valueList.add(reportItem);
					
					item.setValueList(valueList);
					dataListItems.add(item);
				}
				
			}
			ReportItem reportItem = new ReportItem();
			List<ReportItem> valueList = Lists.newArrayList();
			DataListItem item = new DataListItem();
			reportItem = new ReportItem();
			reportItem.setKey("projectCode");
			reportItem.setValue("项目编号");
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			reportItem = new ReportItem();
			reportItem.setKey("customerName");
			reportItem.setValue("发行机构");
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			reportItem = new ReportItem();
			reportItem.setKey("busiTypeName");
			reportItem.setValue("产品期限");
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			reportItem = new ReportItem();
			reportItem.setKey("amount");
			reportItem.setValue("票面单价");
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			reportItem = new ReportItem();
			reportItem.setKey("appropriateReason");
			reportItem.setValue("资金划付事由");
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			reportItem = new ReportItem();
			reportItem.setKey("appropriateAmount");
			reportItem.setValue("申请划付金额（元）");
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			reportItem = new ReportItem();
			reportItem.setKey("payAmount");
			reportItem.setValue("实际付款金额（元）");
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			reportItem = new ReportItem();
			reportItem.setKey("payTime");
			reportItem.setValue("付款时间");
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			item.setValueList(valueList);
			dataListItems.add(item);
			// 信息
			for (int i = 0; i < infos.size(); i++) {
				ReportItem reportItem1 = new ReportItem();
				List<ReportItem> valueList1 = Lists.newArrayList();
				DataListItem item1 = new DataListItem();
				CapitalExportInfo info = infos.get(i);
				//理财
				if (info.getProjectType() != null
					&& info.getProjectType() == FCapitalAppropriationApplyTypeEnum.FINANCIAL_PRODUCT) {
					
					//				realHasAmount.addTo(info.getActualPrice().multiply(info.getOriginalHoldNum()));
					//				totalBuyAmount.addTo(info.getActualPrice().multiply(info.getActualBuyNum()));
					//						reportItem = new ReportItem();
					//						reportItem.setKey("xuhao");
					//						reportItem.setValue((i + 1) + "");
					//						reportItem.setDataTypeEnum(DataTypeEnum.STRING);
					//						valueList.add(reportItem);
					
					reportItem1 = new ReportItem();
					reportItem1.setKey("projectCode");
					reportItem1.setValue(info.getProjectCode());
					reportItem1.setDataTypeEnum(DataTypeEnum.STRING);
					valueList1.add(reportItem1);
					
					reportItem1 = new ReportItem();
					reportItem1.setKey("customerName");
					reportItem1.setValue(info.getIssueInstitution());
					reportItem1.setDataTypeEnum(DataTypeEnum.STRING);
					valueList1.add(reportItem1);
					
					reportItem1 = new ReportItem();
					reportItem1.setKey("busiTypeName");
					if (info.getTimeUnit() != null) {
						reportItem1.setValue(info.getTimeLimit() + info.getTimeUnit().message());
						reportItem1.setDataTypeEnum(DataTypeEnum.STRING);
						valueList1.add(reportItem1);
					} else {
						reportItem1.setValue("");
						reportItem1.setDataTypeEnum(DataTypeEnum.STRING);
						valueList1.add(reportItem1);
					}
					reportItem1 = new ReportItem();
					reportItem1.setKey("amount");
					reportItem1.setValue(info.getPrice().toStandardString());
					reportItem1.setDataTypeEnum(DataTypeEnum.STRING);
					valueList1.add(reportItem1);
					
					reportItem1 = new ReportItem();
					reportItem1.setKey("appropriateReason");
					reportItem1.setValue(info.getAppropriateReason().message());
					reportItem1.setDataTypeEnum(DataTypeEnum.STRING);
					valueList1.add(reportItem1);
					
					reportItem1 = new ReportItem();
					reportItem1.setKey("appropriateAmount");
					reportItem1.setValue(info.getAppropriateAmount().toStandardString());
					reportItem1.setDataTypeEnum(DataTypeEnum.STRING);
					valueList1.add(reportItem1);
					
					reportItem = new ReportItem();
					reportItem.setKey("payAmount");
					reportItem.setValue(info.getPayAmount().toStandardString());
					reportItem.setDataTypeEnum(DataTypeEnum.STRING);
					valueList.add(reportItem);
					
					reportItem1 = new ReportItem();
					reportItem1.setKey("payTime");
					reportItem1.setValue(DateUtil.dtSimpleFormat(info.getPayTime()));
					reportItem1.setDataTypeEnum(DataTypeEnum.STRING);
					valueList1.add(reportItem1);
					
					item1.setValueList(valueList1);
					dataListItems.add(item1);
				}
				
			}
			dataResult.setDataList(dataListItems);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataResult;
	}
}
