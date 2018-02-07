package com.born.fcs.face.web.controller.project.financialproject;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialContractInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialInfo;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialContractFormQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialContractOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectContractService;
import com.yjf.common.lang.util.StringUtil;

/**
 * 理财项目立项
 * @author wuzj
 */
@Controller
@RequestMapping("projectMg/financialProject/contract")
public class FinancialProjectContractController extends WorkflowBaseController {
	
	@Autowired
	FinancialProjectContractService financialProjectContractServiceClient;
	
	private final String vm_path = "/projectMg/financialMg/";
	
	/**
	 * 列表
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(ProjectFinancialContractFormQueryOrder order, Model model) {
		//权限暂时没加
		//setSessionLocalInfo2Order(order);
		order.setDraftUserId(ShiroSessionUtils.getSessionLocal().getUserId());
		model.addAttribute("queryOrder", order);
		model.addAttribute("statusList", FormStatusEnum.getAllEnum());
		model.addAttribute("isFinancialPersonnel", DataPermissionUtil.isFinancialZjlc()
													|| DataPermissionUtil.isXinHuiBusiManager());
		model.addAttribute("page",
			PageUtil.getCovertPage(financialProjectContractServiceClient.queryPage(order)));
		
		return vm_path + "contractList.vm";
	}
	
	/**
	 * 填写申请单
	 * @param productId
	 * @param model
	 * @return
	 */
	@RequestMapping("form.htm")
	public String form(String projectCode, HttpServletRequest request, Model model) {
		
		model.addAttribute("formCode", FormCodeEnum.FINANCING_CONTRACT.code());
		
		if (StringUtil.isNotBlank(projectCode)) {
			
			FProjectFinancialInfo project = financialProjectSetupServiceClient
				.queryByProjectCode(projectCode);
			
			if (project == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "理财项目立项不存在");
			}
			
			model.addAttribute("project", project);
			
			//			ProjectFinancialContractFormQueryOrder contractOrder = new ProjectFinancialContractFormQueryOrder();
			//			ContractOrder.setProjectCode(projectCode);
			//			QueryBaseBatchResult<FinancialProjectContractFormInfo> page = financialProjectContractServiceClient
			//				.queryPage(ContractOrder);
			//			if (page != null && page.getTotalCount() > 0) {
			//				for (FinancialProjectContractFormInfo Contract : page.getPageList()) {
			//					if (Contract.getFormStatus() == FormStatusEnum.DRAFT
			//						|| Contract.getFormStatus() == FormStatusEnum.BACK
			//						|| Contract.getFormStatus() == FormStatusEnum.CANCEL) {
			//						return edit(Contract.getFormId(), request, model);
			//					} else if (Contract.getFormStatus() == FormStatusEnum.AUDITING
			//								|| Contract.getFormStatus() == FormStatusEnum.SUBMIT
			//								|| Contract.getFormStatus() == FormStatusEnum.APPROVAL) {
			//						return view(Contract.getFormId(), request, model);
			//					}
			//				}
			//			}
		}
		
		return vm_path + "contractApply.vm";
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
		return vm_path + "contractApply.vm";
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
		return vm_path + "contractApplyAuditView.vm";
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
		model.addAttribute("legalAudit", request.getParameter("legalAudit"));
		return vm_path + "contractApplyAuditView.vm";
	}
	
	/**
	 * 指定法务经理
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("audit/chooseLegalManager.htm")
	public String xhChooseCouncil(long formId, HttpServletRequest request, Model model) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		
		initWorkflow(model, form, request.getParameter("taskId"));
		//FProjectFinancialContractInfo Contract = 
		viewApply(form, request, model);
		//projectRelatedUserServiceClient.getRiskManager(Contract.getProjectCode());
		model.addAttribute("chooseLegalManager", "YES");
		model.addAttribute("legalAudit", request.getParameter("legalAudit"));
		return vm_path + "contractApplyAuditView.vm";
	}
	
	/**
	 * 查询合同申请单
	 * @param formId
	 * @param request
	 * @param model
	 */
	private FProjectFinancialContractInfo viewApply(FormInfo form, HttpServletRequest request,
													Model model) {
		
		FProjectFinancialContractInfo contract = financialProjectContractServiceClient
			.queryByFormId(form.getFormId());
		
		if (contract == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "合同单不存在");
		}
		model.addAttribute("form", form);
		model.addAttribute("project",
			financialProjectSetupServiceClient.queryByProjectCode(contract.getProjectCode()));
		model.addAttribute("contract", contract);
		return contract;
	}
	
	/**
	 * 保存申请单
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("save.json")
	@ResponseBody
	public JSONObject save(ProjectFinancialContractOrder order, HttpServletRequest request,
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
			FormBaseResult result = financialProjectContractServiceClient.save(order);
			jsonObject = toJSONResult(jsonObject, result, "合同申请保存成功", null);
			
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存合同申请出错", e);
		}
		
		return jsonObject;
	}
}
