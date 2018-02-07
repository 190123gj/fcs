package com.born.fcs.face.web.controller.project.managerbchange;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.enums.managerbchange.ChangeManagerbPhaseEnum;
import com.born.fcs.pm.ws.enums.managerbchange.ChangeManagerbWayEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.managerbchange.FManagerbChangeApplyInfo;
import com.born.fcs.pm.ws.info.managerbchange.ManagerbChangeApplyFormInfo;
import com.born.fcs.pm.ws.order.managerbchange.FManagerbChangeApplyOrder;
import com.born.fcs.pm.ws.order.managerbchange.ManagerbChangeApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Controller
@RequestMapping("projectMg/managerbchange")
public class ManagerbChangeController extends WorkflowBaseController {
	
	private String vm_path = "/projectMg/project/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "changeStartTime", "changeEndTime" };
	}
	
	@RequestMapping("form.htm")
	public String form(String projectCode, HttpServletRequest request, Model model) {
		
		//List<ChangeManagerbPhaseEnum> phaseList = Lists.newArrayList();
		if (StringUtil.isNotEmpty(projectCode)) {
			model
				.addAttribute("isBusiFzr", DataPermissionUtil.isBusiFZR(projectCode) ? "IS" : "NO");
			ProjectInfo project = projectServiceClient.queryByCode(projectCode, false);
			if (project == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "项目不存在");
			}
			ProjectRelatedUserInfo b = projectRelatedUserServiceClient.getBusiManagerb(projectCode);
			model.addAttribute("project", project);
			model.addAttribute("oldManagerb", b);
		} else {
			//phaseList = ChangeManagerbPhaseEnum.getAllEnum();
		}
		model.addAttribute("phaseList", ChangeManagerbPhaseEnum.getAllEnum());
		model.addAttribute("changeWayList", ChangeManagerbWayEnum.getAllEnum());
		return vm_path + "addReplaceB.vm";
	}
	
	/**
	 * 更换B角申请单列表
	 * @param order
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(ManagerbChangeApplyQueryOrder order, HttpServletRequest request, Model model) {
		setSessionLocalInfo2Order(order);
		QueryBaseBatchResult<ManagerbChangeApplyFormInfo> batchResult = managerbChangeServiceClient
			.searchForm(order);
		model.addAttribute("queryOrder", order);
		List<Object> statusList = Lists.newArrayList();
		statusList.addAll(FormStatusEnum.getAllEnum());
		model.addAttribute("statusList", statusList);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		return vm_path + "listReplaceB.vm";
	}
	
	/**
	 * 保存B角更换申请单
	 * @param order
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("save.htm")
	@ResponseBody
	public JSONObject save(FManagerbChangeApplyOrder order, HttpServletRequest request, Model model) {
		JSONObject json = new JSONObject();
		try {
			setSessionLocalInfo2Order(order);
			order.setCheckIndex(0);
			FormBaseResult result = managerbChangeServiceClient.saveApply(order);
			toJSONResult(json, result, "保存成功", null);
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "保存出错");
			logger.error("保存申请单出错：{}", e);
		}
		return json;
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
		initWorkflow(model, viewApply(formId, request, model), request.getParameter("taskId"));
		return vm_path + "viewAuditReplaceB.vm";
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
		viewApply(formId, request, model);
		return vm_path + "addReplaceB.vm";
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
		viewApply(formId, request, model);
		return vm_path + "viewAuditReplaceB.vm";
	}
	
	/**
	 * 查看
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("print.htm")
	public String print(long formId, HttpServletRequest request, Model model) {
		viewApply(formId, request, model);
		return vm_path + "viewAuditReplaceB.vm";
	}
	
	/**
	 * 查看申请单
	 * @param formId
	 * @param request
	 * @param model
	 */
	private FormInfo viewApply(long formId, HttpServletRequest request, Model model) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		
		FManagerbChangeApplyInfo apply = managerbChangeServiceClient.queryApplyByFormId(formId);
		
		if (apply == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "申请单不存在");
		}
		
		ProjectInfo project = projectServiceClient.queryByCode(apply.getProjectCode(), false);
		if (project == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "项目不存在");
		}
		
		ProjectRelatedUserInfo b = projectRelatedUserServiceClient.getBusiManagerb(apply
			.getProjectCode());
		model.addAttribute("oldManagerb", b);
		model.addAttribute("project", project);
		model.addAttribute("form", form);
		model.addAttribute("apply", apply);
		model.addAttribute("isBusiFzr", DataPermissionUtil.isBusiFZR(apply.getProjectCode()) ? "IS"
			: "NO");
		model.addAttribute("phaseList", ChangeManagerbPhaseEnum.getAllEnum());
		model.addAttribute("changeWayList", ChangeManagerbWayEnum.getAllEnum());
		
		return form;
	}
}
