package com.born.fcs.face.web.controller.project.financialproject;

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
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialReviewInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectReviewFormInfo;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialReviewFormQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialReviewOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialRiskReviewOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectReviewService;
import com.yjf.common.lang.util.StringUtil;

/**
 * 理财项目立项
 * @author wuzj
 */
@Controller
@RequestMapping("projectMg/financialProject/review")
public class FinancialProjectReviewController extends WorkflowBaseController {
	
	@Autowired
	FinancialProjectReviewService financialProjectReviewServiceClient;
	
	private final String vm_path = "/projectMg/financialMg/";
	
	/**
	 * 列表
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(ProjectFinancialReviewFormQueryOrder order, Model model) {
		//权限暂时没加
		//setSessionLocalInfo2Order(order);
		order.setDraftUserId(ShiroSessionUtils.getSessionLocal().getUserId());
		model.addAttribute("queryOrder", order);
		List<Object> statusList = Lists.newArrayList();
		statusList.addAll(FormStatusEnum.getAllEnum());
		statusList.add(CommonUtil.newJson("{'code':'COUNCIL_WAITING','message':'上会中'}"));
		statusList.add(CommonUtil.newJson("{'code':'COUNCIL_DENY','message':'上会不通过'}"));
		statusList.add(CommonUtil.newJson("{'code':'COUNCIL_APPROVAL','message':'上会通过'}"));
		model.addAttribute("statusList", statusList);
		model.addAttribute("isFinancialPersonnel", DataPermissionUtil.isFinancialZjlc()
													|| DataPermissionUtil.isXinHuiBusiManager());
		model.addAttribute("page",
			PageUtil.getCovertPage(financialProjectReviewServiceClient.queryPage(order)));
		
		return vm_path + "reviewList.vm";
	}
	
	/**
	 * 填写申请单
	 * @param productId
	 * @param model
	 * @return
	 */
	@RequestMapping("form.htm")
	public String form(String projectCode, HttpServletRequest request, Model model) {
		
		model.addAttribute("formCode", FormCodeEnum.FINANCING_REVIEW.code());
		
		if (StringUtil.isNotBlank(projectCode)) {
			
			FProjectFinancialInfo project = financialProjectSetupServiceClient
				.queryByProjectCode(projectCode);
			
			if (project == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "理财项目立项不存在");
			}
			
			model.addAttribute("project", project);
			
			ProjectFinancialReviewFormQueryOrder reviewOrder = new ProjectFinancialReviewFormQueryOrder();
			reviewOrder.setProjectCode(projectCode);
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal != null) {
				reviewOrder.setDraftUserId(sessionLocal.getUserId());
			}
			QueryBaseBatchResult<FinancialProjectReviewFormInfo> page = financialProjectReviewServiceClient
				.queryPage(reviewOrder);
			if (page != null && page.getTotalCount() > 0) {
				for (FinancialProjectReviewFormInfo review : page.getPageList()) {
					if (review.getFormStatus() == FormStatusEnum.DRAFT
						|| review.getFormStatus() == FormStatusEnum.BACK
						|| review.getFormStatus() == FormStatusEnum.CANCEL) {
						return edit(review.getFormId(), request, model);
					} else if (review.getFormStatus() == FormStatusEnum.AUDITING
								|| review.getFormStatus() == FormStatusEnum.SUBMIT
								|| review.getFormStatus() == FormStatusEnum.APPROVAL) {
						return view(review.getFormId(), request, model);
					}
				}
			}
		}
		
		return vm_path + "reviewApply.vm";
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
		return vm_path + "reviewApply.vm";
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
		return vm_path + "reviewApplyAuditView.vm";
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
		return vm_path + "reviewApplyAuditView.vm";
	}
	
	/**
	 * 风险经理填写风险审查报告
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("audit/riskReview.htm")
	public String riskReview(long formId, HttpServletRequest request, Model model) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		
		initWorkflow(model, form, request.getParameter("taskId"));
		//FProjectFinancialReviewInfo review = 
		viewApply(form, request, model);
		//projectRelatedUserServiceClient.getRiskManager(review.getProjectCode());
		model.addAttribute("riskReview", "YES");
		return vm_path + "reviewApplyAuditView.vm";
	}
	
	/**
	 * 查询送审申请单
	 * @param formId
	 * @param request
	 * @param model
	 */
	private FProjectFinancialReviewInfo viewApply(FormInfo form, HttpServletRequest request,
													Model model) {
		
		FProjectFinancialReviewInfo review = financialProjectReviewServiceClient.queryByFormId(form
			.getFormId());
		
		if (review == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "送审单不存在");
		}
		model.addAttribute("form", form);
		model.addAttribute("project",
			financialProjectSetupServiceClient.queryByProjectCode(review.getProjectCode()));
		model.addAttribute("review", review);
		return review;
	}
	
	/**
	 * 保存申请单
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("save.json")
	@ResponseBody
	public JSONObject save(ProjectFinancialReviewOrder order, HttpServletRequest request,
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
			order.setCheckIndex(0);
			if (order.getFormId() == null)
				order.setFormId(0L);
			FormBaseResult result = financialProjectReviewServiceClient.save(order);
			
			jsonObject = toJSONResult(jsonObject, result, "送审申请保存成功", null);
			
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存送审申请出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 保存申请单
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveRiskReview.json")
	@ResponseBody
	public JSONObject saveRiskReview(ProjectFinancialRiskReviewOrder order,
										HttpServletRequest request, Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			setSessionLocalInfo2Order(order);
			FcsBaseResult result = financialProjectReviewServiceClient.saveRiskReview(order);
			
			jsonObject = toJSONResult(jsonObject, result, "风险审查报告保存成功", null);
			
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("风险审查报告保存出错", e);
		}
		
		return jsonObject;
	}
}
