package com.born.fcs.face.web.controller.project.financialproject;

import java.math.BigDecimal;
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
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialRedeemApplyInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialTradeRedeemInfo;
import com.born.fcs.pm.ws.order.financialproject.FProjectFinancialRedeemApplyOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectRedeemFormQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialTradeRedeemOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.financialproject.FinancialProjectInterestResult;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 理财项目立项
 * @author wuzj
 */
@Controller
@RequestMapping("projectMg/financialProject/redeem")
public class FinancialProjectRedeemController extends WorkflowBaseController {
	
	private final String vm_path = "/projectMg/financialMg/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "redeemTime", "applyTimeStart", "applyTimeEnd", "redeemTimeStart",
								"redeemTimeEnd" };
	}
	
	@Override
	protected String[] getMoneyInputNameArray() {
		return new String[] { "redeemPrincipal", "redeemPrice", "redeemInterest" };
	}
	
	/**
	 * 列表
	 * @param order
	 * @param model
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("list.htm")
	public String list(FinancialProjectRedeemFormQueryOrder order, Model model) {
		
		//setSessionLocalInfo2Order(order);
		order.setDraftUserId(ShiroSessionUtils.getSessionLocal().getUserId());
		model.addAttribute("queryOrder", order);
		List statusList = Lists.newArrayList();
		statusList.addAll(FormStatusEnum.getAllEnum());
		statusList.add(CommonUtil.newJson("{'code':'REDEEMED','message':'已赎回'}"));
		model.addAttribute("isFinancialPersonnel", DataPermissionUtil.isFinancialZjlc()
													|| DataPermissionUtil.isXinHuiBusiManager());
		model.addAttribute("statusList", statusList);
		model.addAttribute("page",
			PageUtil.getCovertPage(financialProjectRedeemServiceClient.queryPage(order)));
		
		return vm_path + "redeemList.vm";
	}
	
	/**
	 * 填写申请单
	 * @param productId
	 * @param model
	 * @return
	 */
	@RequestMapping("form.htm")
	public String form(String projectCode, Model model) {
		
		model.addAttribute("formCode", FormCodeEnum.FINANCING_REDEEM.code());
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
			model.addAttribute("canRedeemNum", a.subtract(t).subtract(r).doubleValue());
			
			FinancialProjectInterestResult cResult = financialProjectServiceClient
				.caculateInterest(projectCode, null);
			if (cResult != null && cResult.isSuccess()) {
				model.addAttribute("holdingPeriodInterest", cResult.getHoldingPeriodInterest());
				model.addAttribute("withdrawedInterest", cResult.getWithdrawedInterest());
			}
			
			model.addAttribute("project", project);
		}
		
		return vm_path + "redeemApply.vm";
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
		viewApply(form, request, model);
		return vm_path + "redeemApply.vm";
	}
	
	/**
	 * 赎回信息维护
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
			return vm_path + "redeemMaintainView.vm";
		} else {
			return vm_path + "redeemMaintain.vm";
		}
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
		return viewApply(form, request, model);
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
		return vm_path + "redeemApplyAudit.vm";
	}
	
	/**
	 * 查询赎回申请单
	 * @param formId
	 * @param request
	 * @param model
	 */
	private String viewApply(FormInfo form, HttpServletRequest request, Model model) {
		
		FProjectFinancialRedeemApplyInfo applyInfo = financialProjectRedeemServiceClient
			.queryApplyByFormId(form.getFormId());
		
		if (applyInfo == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "申请单不存在");
		}
		
		ProjectFinancialTradeRedeemInfo trade = financialProjectRedeemServiceClient
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
		model.addAttribute("canRedeemNum", a.subtract(t).subtract(r).doubleValue());
		
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
		}
		
		return vm_path + "redeemApplyView.vm";
	}
	
	/**
	 * 计算赎回收益
	 * @param projectCode
	 * @param transferPrice
	 * @param transferNum
	 * @param transferDate
	 * @return
	 */
	@RequestMapping("caculateRedeemInterest.htm")
	@ResponseBody
	public JSONObject caculateRedeemInterest(String projectCode, Long redeemNum) {
		JSONObject json = new JSONObject();
		try {
			
			if (StringUtil.isNotBlank(projectCode) && redeemNum != null) {
				
				ProjectFinancialInfo project = financialProjectServiceClient
					.queryByCode(projectCode);
				
				Money redeemInterest = financialProjectServiceClient.caculateRedeemInterest(
					projectCode, null, redeemNum, null);
				json.put("success", true);
				json.put("message", "计算成功");
				json.put("redeemInterest", redeemInterest.getAmount());
				if (project != null) {
					json.put("redeemPrincipal", project.getActualPrice().multiply(redeemNum)
						.getAmount());
				}
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
	 * 保存申请单
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("save.htm")
	@ResponseBody
	public JSONObject save(FProjectFinancialRedeemApplyOrder order, Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			setSessionLocalInfo2Order(order);
			order.setCheckIndex(0);
			FormBaseResult result = financialProjectRedeemServiceClient.save(order);
			
			jsonObject = toJSONResult(jsonObject, result, "赎回申请保存成功", null);
			
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存赎回申请出错", e);
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
	public JSONObject confirm(ProjectFinancialTradeRedeemOrder order, Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			setSessionLocalInfo2Order(order);
			
			FcsBaseResult result = financialProjectRedeemServiceClient.saveTrade(order);
			
			jsonObject = toJSONResult(jsonObject, result, "赎回信息保存成功", null);
			
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存赎回信息出错", e);
		}
		
		return jsonObject;
	}
	
}
