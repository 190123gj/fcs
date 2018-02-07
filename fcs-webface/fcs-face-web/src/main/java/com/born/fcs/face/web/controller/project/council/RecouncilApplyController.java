package com.born.fcs.face.web.controller.project.council;

import javax.servlet.http.HttpServletRequest;

import com.born.fcs.pm.ws.info.common.CommonAttachmentInfo;
import com.born.fcs.pm.ws.order.common.CommonAttachmentQueryOrder;
import com.google.common.collect.Lists;
import com.yjf.common.lang.util.ArrayUtil;
import com.yjf.common.lang.util.ListUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.info.council.FReCouncilApplyInfo;
import com.born.fcs.pm.ws.info.council.ReCouncilApplyFormInfo;
import com.born.fcs.pm.ws.order.council.FReCouncilApplyOrder;
import com.born.fcs.pm.ws.order.council.ReCouncilApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 复议申请
 * 
 * @author wuzj
 */
@Controller
@RequestMapping("projectMg/recouncil")
public class RecouncilApplyController extends WorkflowBaseController {
	
	final static String vm_path = "/projectMg/beforeLoanMg/reconsider/";
	
	@RequestMapping("list.htm")
	public String list(ReCouncilApplyQueryOrder order, HttpServletRequest request, Model model) {
		setSessionLocalInfo2Order(order);
		model.addAttribute("queryOrder", order);
		model.addAttribute("statusList", FormStatusEnum.getAllEnum());
		QueryBaseBatchResult<ReCouncilApplyFormInfo> batchResult = recouncilApplyServiceClient
			.queryForm(order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		return vm_path + "list.vm";
	}
	
	/**
	 * 填写复议申请
	 * 
	 * @param councilId
	 * @param spId
	 * @return
	 */
	@RequestMapping("form.htm")
	public String form(String projectCode, HttpServletRequest request, Model model) {
		
		if (StringUtil.isNotBlank(projectCode)) {
			
			ProjectSimpleDetailInfo project = projectServiceClient
				.querySimpleDetailInfo(projectCode);
			
			if (project == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "项目不存在");
			}
			
			//			if (!DataPermissionUtil.isBusiManager(projectCode)) {
			//				throw ExceptionFactory.newFcsException(FcsResultEnum.NO_ACCESS, "只有改项目的客户经理才能申请复议");
			//			}
			
			FormInfo form = new FormInfo();
			form.setFormCode(FormCodeEnum.PROJET_RECOUNCIL_APPLY);
			model.addAttribute("form", form);
			model.addAttribute("project", project);
		}
		return vm_path + "apply.vm";
	}
	
	/**
	 * 修改复议申请
	 * 
	 * @param councilId
	 * @param spId
	 * @param model
	 * @return
	 */
	@RequestMapping("edit.htm")
	public String edit(long formId, HttpServletRequest request, Model model) {
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		view(form, model);
		return vm_path + "apply.vm";
	}
	
	/**
	 * 查看复议申请
	 * 
	 * @param formId
	 * @param spId
	 * @param model
	 * @return
	 */
	@RequestMapping("view.htm")
	public String view(long formId, HttpServletRequest request, Model model) {
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		setAuditHistory2Page(form, model);
		return view(form, model);
	}
	
	/**
	 * 查看查复议申请
	 * 
	 * @param summaryId
	 * @param model
	 */
	private String view(FormInfo form, Model model) {
		
		model.addAttribute("form", form);
		
		FReCouncilApplyInfo apply = recouncilApplyServiceClient
			.queryApplyByFormId(form.getFormId());
		
		if (apply == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "复议申请单不存在");
		}
		ProjectSimpleDetailInfo project = projectServiceClient.querySimpleDetailInfo(apply
			.getProjectCode());
		if (project == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "项目不存在");
		}
		model.addAttribute("apply", apply);
		model.addAttribute("project", project);
		List<CommonAttachmentInfo> allList = Lists.newArrayList();
		allList.addAll(queryCommonAttachmentDataList(form.getFormId() + "_recouncil",
				CommonAttachmentTypeEnum.FORM_ATTACH, ArrayUtil.EMPTY_STRING_ARRAY));
		allList.addAll(queryCommonAttachmentDataList(form.getFormId()+"",
		CommonAttachmentTypeEnum.FORM_ATTACH,ArrayUtil.EMPTY_STRING_ARRAY));
		setModelAttachment(allList,model);
		return vm_path + "auditViewApply.vm";
	}



	
	/**
	 * 审核
	 * 
	 * @param summaryId
	 * @return
	 */
	@RequestMapping("audit.htm")
	public String audit(long formId, HttpServletRequest request, Model model) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		initWorkflow(model, form, request.getParameter("taskId"));
		view(form, model);
		return vm_path + "auditViewApply.vm";
	}



	@RequestMapping("audit/uploadAttach.htm")
	public String auditUpload(long formId, HttpServletRequest request, Model model) {
		model.addAttribute("uploadAttach", "YES");
		model.addAttribute("editMyAttach", true);
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		initWorkflow(model, form, request.getParameter("taskId"));
		view(form, model);
		return vm_path + "auditViewApply.vm";
	}
	
	/**
	 * 保存复议申请
	 * 
	 * @return
	 */
	@RequestMapping("save.htm")
	@ResponseBody
	public JSONObject save(FReCouncilApplyOrder order, HttpServletRequest request, Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			
			setSessionLocalInfo2Order(order);
			FormBaseResult result = recouncilApplyServiceClient.saveApply(order);
			if (null != result && result.isSuccess()) {
				commonAttachmentServiceClient.deleteByBizNoModuleType(result.getFormInfo().getFormId()+"_recouncil", CommonAttachmentTypeEnum.FORM_ATTACH);
				addAttachfile(result.getFormInfo().getFormId() + "_recouncil", request,
					order.getProjectCode(), "复议申请附件", CommonAttachmentTypeEnum.FORM_ATTACH);
			}
			
			jsonObject = toJSONResult(jsonObject, result, "复议申请保存成功", null);
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e.getMessage());
			logger.error("保存复议申请出错", e);
		}
		
		return jsonObject;
	}
}
