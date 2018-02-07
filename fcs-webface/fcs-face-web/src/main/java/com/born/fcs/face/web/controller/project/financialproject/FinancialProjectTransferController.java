package com.born.fcs.face.web.controller.project.financialproject;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialTansferApplyInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialTradeTansferInfo;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.FProjectFinancialTansferApplyOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectTransferFormQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialTradeTansferOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.financialproject.FinancialProjectInterestResult;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 理财项目立项
 * @author wuzj
 */
@Controller
@RequestMapping("projectMg/financialProject/transfer")
public class FinancialProjectTransferController extends WorkflowBaseController {
	
	private final String vm_path = "/projectMg/financialMg/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "transferTimeStart", "transferTimeEnd", "transferTime", "buyBackTime" };
	}
	
	@Override
	protected String[] getMoneyInputNameArray() {
		return new String[] { "transferPrice", "transferInterest", "buyBackPrice" };
	}
	
	/**
	 * 列表
	 * @param order
	 * @param model
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("list.htm")
	public String list(FinancialProjectTransferFormQueryOrder order, Model model) {
		//权限暂时没加
		//setSessionLocalInfo2Order(order);
		order.setDraftUserId(ShiroSessionUtils.getSessionLocal().getUserId());
		model.addAttribute("queryOrder", order);
		List statusList = Lists.newArrayList();
		statusList.addAll(FormStatusEnum.getAllEnum());
		statusList.add(CommonUtil.newJson("{'code':'COUNCIL_WAITING','message':'上会中'}"));
		statusList.add(CommonUtil.newJson("{'code':'COUNCIL_DENY','message':'上会不通过'}"));
		statusList.add(CommonUtil.newJson("{'code':'COUNCIL_APPROVAL','message':'上会通过'}"));
		statusList.add(CommonUtil.newJson("{'code':'TRANSFERED','message':'转让完成'}"));
		statusList.add(CommonUtil.newJson("{'code':'BUY_BACK_WAITING','message':'待回购'}"));
		statusList.add(CommonUtil.newJson("{'code':'BUY_BACK_FINISH','message':'已回购'}"));
		model.addAttribute("statusList", statusList);
		model.addAttribute("isFinancialPersonnel", DataPermissionUtil.isFinancialZjlc()
													|| DataPermissionUtil.isXinHuiBusiManager());
		model.addAttribute("page",
			PageUtil.getCovertPage(financialProjectTransferServiceClient.queryPage(order)));
		
		return vm_path + "transferList.vm";
	}
	
	/**
	 * 填写申请单
	 * @param productId
	 * @param model
	 * @return
	 */
	@RequestMapping("form.htm")
	public String form(String projectCode, Model model) {
		
		model.addAttribute("formCode", FormCodeEnum.FINANCING_TRANSFER.code());
		
		if (StringUtil.isNotBlank(projectCode)) {
			ProjectFinancialInfo project = financialProjectServiceClient.queryByCode(projectCode);
			if (project == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "理财项目不存在");
			}
			
			model.addAttribute("project", project);
			
			double transferingNum = financialProjectServiceClient.transferingNum(projectCode, 0);
			double redeemingNum = financialProjectServiceClient.redeemingNum(projectCode, 0);
			
			model.addAttribute("transferingNum", transferingNum);
			model.addAttribute("redeemingNum", redeemingNum);
			BigDecimal a = new BigDecimal(Double.toString(project.getOriginalHoldNum()));
			BigDecimal t = new BigDecimal(Double.toString(transferingNum));
			BigDecimal r = new BigDecimal(Double.toString(redeemingNum));
			model.addAttribute("canTransferNum", a.subtract(t).subtract(r).doubleValue());
			
			FinancialProjectInterestResult cResult = financialProjectServiceClient
				.caculateInterest(projectCode, null);
			if (cResult != null && cResult.isSuccess()) {
				model.addAttribute("holdingPeriodInterest", cResult.getHoldingPeriodInterest());
				model.addAttribute("withdrawedInterest", cResult.getWithdrawedInterest());
			}
		}
		
		return vm_path + "transferApply.vm";
	}
	
	/**
	 * 编辑
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("edit.htm")
	public String edit(long formId, HttpServletRequest request, Model model) {
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		model.addAttribute("isEdit", true);
		viewApply(form, request, model);
		return vm_path + "transferApply.vm";
	}
	
	/**
	 * 转让信息维护
	 * @param applyId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("maintain.htm")
	public String maintain(long formId, HttpServletRequest request, Model model) {
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		viewApply(form, request, model);
		if (model.containsAttribute("hasTrade")) {
			return vm_path + "transferMaintainView.vm";
		} else {
			return vm_path + "transferMaintain.vm";
		}
	}
	
	/**
	 * 计算转让收益
	 * @param projectCode
	 * @param transferPrice
	 * @param transferNum
	 * @param transferDate
	 * @return
	 */
	@RequestMapping("caculateTransferInterest.htm")
	@ResponseBody
	public JSONObject caculateTransferInterest(String projectCode, String transferPrice,
												Double transferNum, String transferTime) {
		JSONObject json = new JSONObject();
		try {
			
			if (StringUtil.isNotBlank(projectCode) && StringUtil.isNotBlank(transferPrice)
				&& transferNum != null && StringUtil.isNotBlank(transferTime)) {
				Money price = Money.amout(transferPrice);
				Date time = DateUtil.parse(transferTime);
				Money transferInterest = financialProjectServiceClient.caculateTransferInterest(
					projectCode, price, transferNum, time);
				json.put("success", true);
				json.put("message", "计算成功");
				json.put("transferInterest", transferInterest.getAmount());
			} else {
				json.put("success", false);
				json.put("message", "计算转让收益参数不完整");
			}
			
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "计算转让收益出错");
		}
		
		return json;
	}
	
	/**
	 * 查看
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("view.htm")
	public String view(long formId, HttpServletRequest request, Model model) {
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		viewApply(form, request, model);
		return vm_path + "transferApplyView.vm";
	}
	
	/**
	 * 审核
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("audit.htm")
	public String audit(long formId, HttpServletRequest request, Model model) {
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		initWorkflow(model, form, request.getParameter("taskId"));
		viewApply(form, request, model);
		return vm_path + "transferApplyAudit.vm";
	}
	
	/**
	 * 信惠选择是否上会
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("audit/XHChooseCouncil.htm")
	public String xhChooseCouncil(long formId, HttpServletRequest request, Model model) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		
		initWorkflow(model, form, request.getParameter("taskId"));
		
		ProjectFinancialInfo project = viewApply(form, request, model);
		//信惠人员需要选择是否需要上会
		if (DataPermissionUtil.isBelong2Xinhui(project.getCreateUserId())) {
			model.addAttribute("chooseCouncil", "YES");
			List<ProjectCouncilEnum> councilType = Lists.newArrayList();
			councilType.add(ProjectCouncilEnum.SELF_GW);
			councilType.add(ProjectCouncilEnum.SELF_MOTHER_GW);
			councilType.add(ProjectCouncilEnum.SELF_GW_MONTHER_PR);
			model.addAttribute("councilType", councilType);
		}
		
		return vm_path + "transferApplyAudit.vm";
	}
	
	/**
	 * 选择是否上会
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("audit/chooseCouncil.htm")
	public String chooseCouncil(long formId, HttpServletRequest request, Model model) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		
		initWorkflow(model, form, request.getParameter("taskId"));
		
		ProjectFinancialInfo project = viewApply(form, request, model);
		//非信惠选择是否需要上会
		if (!DataPermissionUtil.isBelong2Xinhui(project.getCreateUserId())) {
			model.addAttribute("chooseCouncil", "YES");
			List<ProjectCouncilEnum> councilType = Lists.newArrayList();
			councilType.add(ProjectCouncilEnum.SELF_GW);
			councilType.add(ProjectCouncilEnum.SELF_PR);
			model.addAttribute("councilType", councilType);
		}
		
		return vm_path + "transferApplyAudit.vm";
	}
	
	/**
	 * 查询转让申请单
	 * @param formId
	 * @param request
	 * @param model
	 */
	private ProjectFinancialInfo viewApply(FormInfo form, HttpServletRequest request, Model model) {
		
		FProjectFinancialTansferApplyInfo applyInfo = financialProjectTransferServiceClient
			.queryApplyByFormId(form.getFormId());
		
		if (applyInfo == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "申请单不存在");
		}
		
		ProjectFinancialTradeTansferInfo trade = financialProjectTransferServiceClient
			.queryTradeByApplyId(applyInfo.getApplyId());
		
		ProjectFinancialInfo project = financialProjectServiceClient.queryByCode(applyInfo
			.getProjectCode());
		
		String projectCode = applyInfo.getProjectCode();
		
		double transferingNum = financialProjectServiceClient.transferingNum(projectCode,
			applyInfo.getApplyId());
		double redeemingNum = financialProjectServiceClient.redeemingNum(projectCode,
			applyInfo.getApplyId());
		
		model.addAttribute("transferingNum", transferingNum);
		model.addAttribute("redeemingNum", redeemingNum);
		BigDecimal a = new BigDecimal(Double.toString(project.getOriginalHoldNum()));
		BigDecimal t = new BigDecimal(Double.toString(transferingNum));
		BigDecimal r = new BigDecimal(Double.toString(redeemingNum));
		model.addAttribute("canTransferNum", a.subtract(t).subtract(r).doubleValue());
		
		FinancialProjectInterestResult cResult = financialProjectServiceClient.caculateInterest(
			projectCode, null);
		if (cResult != null && cResult.isSuccess()) {
			model.addAttribute("holdingPeriodInterest", cResult.getHoldingPeriodInterest());
			model.addAttribute("withdrawedInterest", cResult.getWithdrawedInterest());
		}
		
		model.addAttribute("form", form);
		model.addAttribute("project", project);
		model.addAttribute("applyInfo", applyInfo);
		model.addAttribute("trade", trade);
		if (trade != null) {
			model.addAttribute("hasTrade", true);
			queryCommonAttachmentData(model, String.valueOf(trade.getTradeId()),
				CommonAttachmentTypeEnum.FINANCIAL_TRANSFER_MAINTAIN);
		}
		queryCommonAttachmentData(model, "PM_" + form.getFormId(),
			CommonAttachmentTypeEnum.FORM_ATTACH);
		return project;
	}
	
	/**
	 * 保存申请单
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("save.htm")
	@ResponseBody
	public JSONObject save(FProjectFinancialTansferApplyOrder order, HttpServletRequest request,
							Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			setSessionLocalInfo2Order(order);
			FormBaseResult result = financialProjectTransferServiceClient.save(order);
			
			jsonObject = toJSONResult(jsonObject, result, "转让申请保存成功", null);
			if (result != null && result.isSuccess()) {
				addAttachfile("PM_" + result.getFormInfo().getFormId(), request,
					order.getProjectCode(), "理财产品转让", CommonAttachmentTypeEnum.FORM_ATTACH);
			}
			
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存转让申请出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 信息维护保存
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("confirm.htm")
	@ResponseBody
	public JSONObject confirm(ProjectFinancialTradeTansferOrder order, HttpServletRequest request,
								Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			setSessionLocalInfo2Order(order);
			FcsBaseResult result = financialProjectTransferServiceClient.saveTrade(order);
			
			jsonObject = toJSONResult(jsonObject, result, "保存成功", null);
			if (result != null && result.isSuccess()) {
				addAttachfile(String.valueOf(result.getKeyId()), request, order.getProjectCode(),
					"理财产品转让信息维护", CommonAttachmentTypeEnum.FINANCIAL_TRANSFER_MAINTAIN);
			}
			
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", "保存转让信息出错");
			logger.error("保存转让信息出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 回购确认
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("buyBack.htm")
	@ResponseBody
	public JSONObject buyBack(ProjectFinancialTradeTansferOrder order, Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			setSessionLocalInfo2Order(order);
			order.setIsConfirm(BooleanEnum.IS);
			FcsBaseResult result = financialProjectTransferServiceClient.saveTrade(order);
			
			jsonObject = toJSONResult(jsonObject, result, "确认成功", null);
			
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("回购确认出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 回购确认 跳转到资金划付
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("buyBackApply.htm")
	public String buyBack(long applyId, Model model) {
		ProjectFinancialTradeTansferInfo trade = financialProjectTransferServiceClient
			.queryTradeByApplyId(applyId);
		if (trade == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "转让信息未找到");
		}
		
		//查询已经存在的资金划付单
		FCapitalAppropriationApplyInfo zjApplyInfo = null;
		FCapitalAppropriationApplyQueryOrder qOrder = new FCapitalAppropriationApplyQueryOrder();
		qOrder.setProjectCode(trade.getProjectCode());
		qOrder.setSortCol("f.form_id");
		qOrder.setSortOrder("desc");
		QueryBaseBatchResult<FCapitalAppropriationApplyInfo> applys = fCapitalAppropriationApplyServiceClient
			.query(qOrder);
		if (applys != null && applys.isSuccess()) {
			for (FCapitalAppropriationApplyInfo info : applys.getPageList()) {
				if (info.getOutBizNo() == applyId) {
					zjApplyInfo = info;
					break;
				}
			}
		}
		
		if (zjApplyInfo != null) {
			if (zjApplyInfo.getFormStatus() == FormStatusEnum.BACK
				|| zjApplyInfo.getFormStatus() == FormStatusEnum.CANCEL
				|| zjApplyInfo.getFormStatus() == FormStatusEnum.DRAFT) {
				model.addAttribute("formId", zjApplyInfo.getFormId());
				return "redirect:/projectMg/fCapitalAppropriationApply/editCapitalAppropriationApply.htm";
			} else if (zjApplyInfo.getFormStatus() == FormStatusEnum.APPROVAL
						|| zjApplyInfo.getFormStatus() == FormStatusEnum.SUBMIT
						|| zjApplyInfo.getFormStatus() == FormStatusEnum.AUDITING) {
				model.addAttribute("formId", zjApplyInfo.getFormId());
				return "redirect:/projectMg/fCapitalAppropriationApply/viewCapitalAppropriationApply.htm";
			}
		}
		
		model.addAttribute("projectCode", trade.getProjectCode());
		model.addAttribute("projectType", "FINANCIAL_PRODUCT");
		model.addAttribute("outBizNo", trade.getApplyId());
		model.addAttribute("backAmount", trade.getBuyBackPrice().multiply(trade.getTransferNum())
			.getAmount());
		
		return "redirect:/projectMg/fCapitalAppropriationApply/addCapitalAppropriationApply.htm";
	}
}
