package com.born.fcs.face.web.controller.project.financialproject;

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
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductInterestTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductSellStatusEnum;
import com.born.fcs.pm.ws.enums.FinancialProductTermTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.InterestSettlementWayEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FinancialProductInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialInfo;
import com.born.fcs.pm.ws.order.financialproject.FProjectFinancialOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;

/**
 * 理财项目立项
 * @author wuzj
 */
@Controller
@RequestMapping("projectMg/financialProject/setUp")
public class FinancialProjectSetupController extends WorkflowBaseController {
	
	private final String vm_path = "/projectMg/financialMg/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "expectBuyDate", "expectExpireDate" };
	}
	
	@Override
	protected String[] getMoneyInputNameArray() {
		return new String[] { "price" };
	}
	
	/**
	 * 列表
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(FinancialProjectQueryOrder order, Model model) {
		
		//setSessionLocalInfo2Order(order);
		//order.setCreateUserId(order.getBusiManagerId());
		order.setDraftUserId(ShiroSessionUtils.getSessionLocal().getUserId());
		model.addAttribute("queryOrder", order);
		model.addAttribute("isFinancialPersonnel", DataPermissionUtil.isFinancialZjlc()
													|| DataPermissionUtil.isXinHuiBusiManager());
		List<Object> statusList = Lists.newArrayList();
		statusList.addAll(FormStatusEnum.getAllEnum());
		statusList.add(CommonUtil.newJson("{'code':'COUNCIL_SENDING','message':'送审中'}"));
		statusList.add(CommonUtil.newJson("{'code':'COUNCIL_WAITING','message':'上会中'}"));
		statusList.add(CommonUtil.newJson("{'code':'COUNCIL_DENY','message':'上会不通过'}"));
		statusList.add(CommonUtil.newJson("{'code':'COUNCIL_APPROVAL','message':'上会通过'}"));
		model.addAttribute("statusList", statusList);
		
		model.addAttribute("page",
			PageUtil.getCovertPage(financialProjectSetupServiceClient.query(order)));
		
		return vm_path + "applyList.vm";
	}
	
	/**
	 * 填写申请单
	 * @param productId
	 * @param model
	 * @return
	 */
	@RequestMapping("form.htm")
	public String form(Long productId, Model model) {
		
		if (DataPermissionUtil.isFinancialZjlc() || DataPermissionUtil.isXinHuiBusiManager()) {
			
			model.addAttribute("productTypeList", FinancialProductTypeEnum.getAllEnum());
			model.addAttribute("termTypeList", FinancialProductTermTypeEnum.getAllEnum());
			model.addAttribute("interestTypeList", FinancialProductInterestTypeEnum.getAllEnum());
			model.addAttribute("interestSettlementWayList", InterestSettlementWayEnum.getAllEnum());
			model.addAttribute("formCode", FormCodeEnum.SET_UP_FINANCING);
			
			if (productId != null && productId > 0) {
				FinancialProductInfo product = financialProductServiceClient.findById(productId);
				if (product == null
					|| product.getSellStatus() == FinancialProductSellStatusEnum.STOP) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
						"理财产品不存在或已停售");
				}
				model.addAttribute("product", product);
			}
		} else {
			throw ExceptionFactory.newFcsException(FcsResultEnum.NO_ACCESS, "无权立项");
		}
		return vm_path + "apply.vm";
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
		return vm_path + "apply.vm";
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
		return vm_path + "applyView.vm";
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
		return vm_path + "applyViewAudit.vm";
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
		
		FProjectFinancialInfo project = viewApply(form, request, model);
		if (DataPermissionUtil.isBelong2Xinhui(project.getCreateUserId())) { //信惠人员选择是否需要上会
			model.addAttribute("chooseCouncil", "YES");
			List<ProjectCouncilEnum> councilType = Lists.newArrayList();
			councilType.add(ProjectCouncilEnum.SELF_GW);
			councilType.add(ProjectCouncilEnum.SELF_MOTHER_GW);
			councilType.add(ProjectCouncilEnum.SELF_GW_MONTHER_PR);
			model.addAttribute("councilType", councilType);
		}
		
		return vm_path + "applyViewAudit.vm";
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
		FProjectFinancialInfo project = viewApply(form, request, model);
		//非信惠
		if (!DataPermissionUtil.isBelong2Xinhui(project.getCreateUserId())) {
			model.addAttribute("chooseCouncil", "YES");
			List<ProjectCouncilEnum> councilType = Lists.newArrayList();
			councilType.add(ProjectCouncilEnum.SELF_GW);
			councilType.add(ProjectCouncilEnum.SELF_PR);
			model.addAttribute("councilType", councilType);
		}
		return vm_path + "applyViewAudit.vm";
	}
	
	/**
	 * 选择是否上会
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("audit/chooseRiskManager.htm")
	public String chooseRiskManager(long formId, HttpServletRequest request, Model model) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		initWorkflow(model, form, request.getParameter("taskId"));
		FProjectFinancialInfo project = viewApply(form, request, model);
		//非信惠
		if (!DataPermissionUtil.isBelong2Xinhui(project.getCreateUserId())) {
			model.addAttribute("chooseRiskManager", "YES");
		}
		return vm_path + "applyViewAudit.vm";
	}
	
	/**
	 * 查询立项申请单
	 * @param formId
	 * @param request
	 * @param model
	 */
	private FProjectFinancialInfo viewApply(FormInfo form, HttpServletRequest request, Model model) {
		
		FProjectFinancialInfo applyInfo = financialProjectSetupServiceClient.queryByFormId(form
			.getFormId());
		
		if (applyInfo == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "申请单不存在");
		}
		
		if (applyInfo.getProductId() > 0) {
			FinancialProductInfo product = financialProductServiceClient.findById(applyInfo
				.getProductId());
			model.addAttribute("product", product);
		}
		model.addAttribute("form", form);
		model.addAttribute("applyInfo", applyInfo);
		model.addAttribute("productTypeList", FinancialProductTypeEnum.getAllEnum());
		model.addAttribute("termTypeList", FinancialProductTermTypeEnum.getAllEnum());
		model.addAttribute("interestTypeList", FinancialProductInterestTypeEnum.getAllEnum());
		model.addAttribute("interestSettlementWayList", InterestSettlementWayEnum.getAllEnum());
		
		queryCommonAttachmentData(model, "PM_" + form.getFormId(),
			CommonAttachmentTypeEnum.FORM_ATTACH);
		
		return applyInfo;
	}
	
	/**
	 * 保存
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("save.htm")
	@ResponseBody
	public JSONObject save(FProjectFinancialOrder order, HttpServletRequest request, Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			
			order.setCheckIndex(0);
			setSessionLocalInfo2Order(order);
			
			FormBaseResult result = financialProjectSetupServiceClient.save(order);
			jsonObject = toJSONResult(jsonObject, result, "保存理财项目信息成功", null);
			if (result != null && result.isSuccess()) {
				addAttachfile("PM_" + result.getFormInfo().getFormId(), request,
					order.getProjectCode(), "理财项目立项", CommonAttachmentTypeEnum.FORM_ATTACH);
			}
			
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存理财项目出错", e);
		}
		
		return jsonObject;
	}
}
