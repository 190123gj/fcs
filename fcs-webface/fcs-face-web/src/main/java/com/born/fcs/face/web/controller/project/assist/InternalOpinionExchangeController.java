package com.born.fcs.face.web.controller.project.assist;

import java.util.ArrayList;
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
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.InternalOpinionExTypeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.assist.FInternalOpinionExchangeInfo;
import com.born.fcs.pm.ws.info.assist.InternalOpinionExchangeFormInfo;
import com.born.fcs.pm.ws.info.common.CommonAttachmentInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.assist.FInternalOpinionExchangeOrder;
import com.born.fcs.pm.ws.order.assist.InternalOpinionExchangeQueryOrder;
import com.born.fcs.pm.ws.order.common.CommonAttachmentQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.ListUtil;

/**
 * 内审全部门全人员都参与 所以已经排除资源权限检查了
 * 
 * @author wuzj
 */
@Controller
@RequestMapping("projectMg/internalOpinionExchange")
public class InternalOpinionExchangeController extends WorkflowBaseController {
	
	private static final String vm_path = "/projectMg/assistSys/internalAudit/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "feedbackTime" };
	}
	
	@RequestMapping("form.htm")
	public String form(InternalOpinionExTypeEnum exType, Model model) {
		if (exType == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.INCOMPLETE_REQ_PARAM, "请选择类型");
		}
		FInternalOpinionExchangeInfo initInfo = new FInternalOpinionExchangeInfo();
		initInfo.setExType(exType);
		model.addAttribute("exInfo", initInfo);
		return vm_path + "addOpinion.vm";
	}
	
	@RequestMapping("list.htm")
	public String list(InternalOpinionExchangeQueryOrder order, Model model) {
		setSessionLocalInfo2Order(order);
		QueryBaseBatchResult<InternalOpinionExchangeFormInfo> batchResult = internalOpinionExchangeServiceClient
			.searchForm(order);
		model.addAttribute("queryOrder", order);
		model.addAttribute("statusList", FormStatusEnum.getAllEnum());
		model.addAttribute("exTypeList", InternalOpinionExTypeEnum.getAllEnum());
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		return vm_path + "list.vm";
	}
	
	@RequestMapping("view.htm")
	public String view(long formId, Model model) {
		viewForm(formId, model);
		return vm_path + "viewAuditOpinion.vm";
	}
	
	@RequestMapping("audit.htm")
	public String audit(long formId, HttpServletRequest request, Model model) {
		FormInfo form = viewForm(formId, model);
		initWorkflow(model, form, request.getParameter("taskId"));
		return vm_path + "viewAuditOpinion.vm";
	}
	
	@RequestMapping("audit/uploadAttach.htm")
	public String auditUpload(long formId, HttpServletRequest request, Model model) {
		model.addAttribute("uploadAttach", "YES");
		model.addAttribute("editMyAttach", true);
		return audit(formId, request, model);
	}
	
	/**
	 * 检查部门人员
	 * @param order 需要deptIds deptNames userIds
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("checkDeptUser.htm")
	@ResponseBody
	public JSONObject checkDeptUser(FInternalOpinionExchangeOrder order,
									HttpServletRequest request, Model model) {
		JSONObject result = new JSONObject();
		try {
			FcsBaseResult cResult = internalOpinionExchangeServiceClient.checkDeptUser(order);
			result.put("success", cResult.isSuccess());
			result.put("message", cResult.getMessage());
		} catch (Exception e) {
			logger.error("检查部门人员出错{}", e);
			result.put("success", false);
			result.put("message", "检查部门人员出错");
		}
		return result;
	}
	
	@RequestMapping("edit.htm")
	public String edit(long formId, Model model) {
		viewForm(formId, model);
		model.addAttribute("editMyAttach", true);
		return vm_path + "addOpinion.vm";
	}
	
	@RequestMapping("print.htm")
	public String print(long formId, Model model) {
		setAuditHistory2Page(viewForm(formId, model), model);
		return vm_path + "printOpinion.vm";
	}
	
	@ResponseBody
	@RequestMapping("save.htm")
	public JSONObject save(FInternalOpinionExchangeOrder order, HttpServletRequest request,
							Model model) {
		JSONObject json = new JSONObject();
		try {
			setSessionLocalInfo2Order(order);
			order.setCheckIndex(0);
			order.setCheckStatus(1);
			if (order.getFeedbackTime() != null) {
				order.setFeedbackTime(DateUtil.getEndTimeOfTheDate(order.getFeedbackTime()));
			}
			FormBaseResult result = internalOpinionExchangeServiceClient.save(order);
			if (result != null && result.isSuccess()) {
				addAttachfile(String.valueOf(result.getFormInfo().getFormId()),
					String.valueOf(order.getUserId()), request, "合规检查", null,
					CommonAttachmentTypeEnum.FORM_ATTACH);
			}
			toJSONResult(json, result, "保存成功", null);
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "保存出错");
			logger.error("保存内审交换意见出错：{}", e);
		}
		return json;
	}
	
	/**
	 * 查询表单信息
	 * @param formId
	 * @param model
	 * @return
	 */
	private FormInfo viewForm(long formId, Model model) {
		
		FormInfo formInfo = formServiceClient.findByFormId(formId);
		if (formInfo == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在 ");
		}
		
		FInternalOpinionExchangeInfo exInfo = internalOpinionExchangeServiceClient
			.findByFormId(formId);
		
		model.addAttribute("exInfo", exInfo);
		model.addAttribute("form", formInfo);
		
		//查询所有附件
		CommonAttachmentQueryOrder attachQueryOrder = new CommonAttachmentQueryOrder();
		attachQueryOrder.setBizNo(String.valueOf(formInfo.getFormId()));
		List<CommonAttachmentTypeEnum> moduleTypeList = new ArrayList<>();
		moduleTypeList.add(CommonAttachmentTypeEnum.FORM_ATTACH);
		attachQueryOrder.setModuleTypeList(moduleTypeList);
		QueryBaseBatchResult<CommonAttachmentInfo> attaches = commonAttachmentServiceClient
			.queryCommonAttachment(attachQueryOrder);
		//当前用户
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		long currentUser = sessionLocal.getUserId();
		if (null != attaches && ListUtil.isNotEmpty(attaches.getPageList())) {
			List<CommonAttachmentInfo> attachList = attaches.getPageList(); //所有附件
			List<CommonAttachmentInfo> myAttachList = Lists.newArrayList();//我的附件
			List<CommonAttachmentInfo> otherAttachList = Lists.newArrayList();//所有附件（不包含我的附件）
			StringBuffer allAttachUrl = new StringBuffer();
			StringBuffer myAttachUrl = new StringBuffer();
			StringBuffer otherAttachUrl = new StringBuffer();
			for (CommonAttachmentInfo attach : attachList) {
				allAttachUrl.append(attach.getFileName()).append(",")
					.append(attach.getFilePhysicalPath()).append(",")
					.append(attach.getRequestPath()).append(";");
				if (currentUser == attach.getUploaderId()) {
					myAttachList.add(attach);
					myAttachUrl.append(attach.getFileName()).append(",")
						.append(attach.getFilePhysicalPath()).append(",")
						.append(attach.getRequestPath()).append(";");
				} else {
					otherAttachList.add(attach);
					otherAttachUrl.append(attach.getFileName()).append(",")
						.append(attach.getFilePhysicalPath()).append(",")
						.append(attach.getRequestPath()).append(";");
				}
			}
			if (StringUtil.isNotEmpty(allAttachUrl.toString())) {
				allAttachUrl.deleteCharAt(allAttachUrl.length() - 1);
			}
			if (StringUtil.isNotEmpty(myAttachUrl.toString())) {
				myAttachUrl.deleteCharAt(myAttachUrl.length() - 1);
			}
			if (StringUtil.isNotEmpty(otherAttachUrl.toString())) {
				otherAttachUrl.deleteCharAt(otherAttachUrl.length() - 1);
			}
			
			model.addAttribute("allAttachUrl", allAttachUrl);
			model.addAttribute("myAttachUrl", myAttachUrl);
			model.addAttribute("otherAttachUrl", otherAttachUrl);
			
			model.addAttribute("allAttachList", attachList);
			model.addAttribute("myAttachList", myAttachList);
			model.addAttribute("otherAttachList", otherAttachList);
		}
		
		return formInfo;
	}
}
