package com.born.fcs.face.web.controller.project.brokerbusiness;

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
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.CommonUtil;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.brokerbusiness.BrokerBusinessFormInfo;
import com.born.fcs.pm.ws.info.brokerbusiness.FBrokerBusinessInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.brokerbusiness.BrokerBusinessQueryOrder;
import com.born.fcs.pm.ws.order.brokerbusiness.FBrokerBusinessOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Controller
@RequestMapping("projectMg/brokerBusiness")
public class BrokerBusinessController extends WorkflowBaseController {
	
	private static final String vm_path = "/projectMg/brokerbusiness/";
	
	@RequestMapping("form.htm")
	public String form(Model model) {
		FBrokerBusinessInfo busiInfo = new FBrokerBusinessInfo();
		model.addAttribute("busiInfo", busiInfo);
		return vm_path + "apply.vm";
	}
	
	@RequestMapping("list.htm")
	public String list(BrokerBusinessQueryOrder order, Model model) {
		setSessionLocalInfo2Order(order);
		QueryBaseBatchResult<BrokerBusinessFormInfo> batchResult = brokerBusinessServiceClient
			.queryPage(order);
		model.addAttribute("queryOrder", order);
		List<Object> statusList = Lists.newArrayList();
		statusList.addAll(FormStatusEnum.getAllEnum());
		statusList.add(CommonUtil.newJson("{'code':'COUNCIL_WAITING','message':'上会中'}"));
		statusList.add(CommonUtil.newJson("{'code':'COUNCIL_APPROVAL','message':'上会通过'}"));
		statusList.add(CommonUtil.newJson("{'code':'COUNCIL_DENY','message':'上会未通过'}"));
		model.addAttribute("statusList", statusList);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		return vm_path + "list.vm";
	}
	
	@RequestMapping("view.htm")
	public String view(long formId, Model model) {
		viewForm(formId, model);
		return vm_path + "viewAuditApply.vm";
	}
	
	@RequestMapping("audit.htm")
	public String audit(long formId, HttpServletRequest request, Model model) {
		FormInfo form = viewForm(formId, model);
		initWorkflow(model, form, request.getParameter("taskId"));
		return vm_path + "viewAuditApply.vm";
	}
	
	@RequestMapping("audit/chooseLegalMan.htm")
	public String chooseLegalMan(long formId, HttpServletRequest request, Model model) {
		model.addAttribute("chooseLegalManager", "YES");
		return audit(formId, request, model);
	}
	
	@RequestMapping("edit.htm")
	public String edit(long formId, Model model) {
		viewForm(formId, model);
		return vm_path + "apply.vm";
	}
	
	@RequestMapping("print.htm")
	public String print(long formId, Model model) {
		viewForm(formId, model);
		return vm_path + "viewAuditApply.vm";
	}
	
	@ResponseBody
	@RequestMapping("save.htm")
	public JSONObject save(FBrokerBusinessOrder order, HttpServletRequest request, Model model) {
		JSONObject json = new JSONObject();
		try {
			setSessionLocalInfo2Order(order);
			order.setFormCode(FormCodeEnum.BROKER_BUSINESS);
			order.setCheckIndex(0);
			order.setCheckStatus(1);
			FormBaseResult result = brokerBusinessServiceClient.save(order);
			if (result != null && result.isSuccess()) {
				
				//上传其他附件
				addAttachfile(FormCodeEnum.BROKER_BUSINESS.code() + "_"
								+ result.getFormInfo().getFormId(), request,
					order.getProjectCode(), null, CommonAttachmentTypeEnum.FORM_ATTACH);
			}
			toJSONResult(json, result, "保存成功", null);
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "保存出错");
			logger.error("保存经纪业务出错：{}", e);
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
		
		FBrokerBusinessInfo busiInfo = brokerBusinessServiceClient.findByFormId(formId);
		
		model.addAttribute("busiInfo", busiInfo);
		model.addAttribute("form", formInfo);
		
		//查询附件
		queryCommonAttachmentData(model,
			FormCodeEnum.BROKER_BUSINESS.code() + "_" + formInfo.getFormId(),
			CommonAttachmentTypeEnum.FORM_ATTACH);
		
		return formInfo;
	}
}
