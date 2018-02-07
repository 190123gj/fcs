package com.born.fcs.face.web.controller.customer.channal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.crm.ws.service.enums.ChanalTypeEnum;
import com.born.fcs.crm.ws.service.info.ChannalContractInfo;
import com.born.fcs.crm.ws.service.order.ChannalContractOrder;
import com.born.fcs.crm.ws.service.order.query.ChannalContractQueryOrder;
import com.born.fcs.face.integration.crm.service.channal.ChannalClient;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.StringUtil;

@Controller
@RequestMapping("/customerMg/channalContract")
public class ChannalContractController extends WorkflowBaseController {
	private static final String VM_PATH = "/customerMg/channel/";
	@Autowired
	private ChannalClient channalClient;
	
	@ResponseBody
	@RequestMapping("save.htm")
	public JSONObject save(ChannalContractOrder order) {
		
		JSONObject jsonObject = new JSONObject();
		try {
			setSessionLocalInfo2Order(order);
			FcsBaseResult saveResult = channalContractClient.save(order);
			jsonObject = toJSONResult(jsonObject, saveResult, "保存合同成功", null);
			jsonObject.put("formId", saveResult.getKeyId());
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e.getMessage());
			logger.error("保存渠道合同出错：", e);
		}
		
		return jsonObject;
	}
	
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model) {
		ChannalContractQueryOrder queryOrder = new ChannalContractQueryOrder();
		WebUtil.setPoPropertyByRequest(queryOrder, request);
		QueryBaseBatchResult<ChannalContractInfo> batchResult = channalContractClient
			.list(queryOrder);
		setCustomerEnums(model);
		model.addAttribute("queryOrder", queryOrder);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		
		return VM_PATH + "contractList.vm";
	}
	
	@RequestMapping("add.htm")
	public String add(Long formId, String channalType, Model model) {
		ChannalContractInfo info = null;
		if (formId != null && formId > 0) {
			info = channalContractClient.info(formId);
		} else {
			info = new ChannalContractInfo();
			info.setChannalCode(channalClient.createChannalCode(StringUtil.defaultIfBlank(
				channalType, ChanalTypeEnum.YH.code())));
		}
		model.addAttribute("info", info);
		setCustomerEnums(model);
		return VM_PATH + "contractAdd.vm";
	}
	
	@RequestMapping("info.htm")
	public String info(Long formId, Model model) {
		if (formId != null && formId > 0) {
			ChannalContractInfo info = channalContractClient.info(formId);
			model.addAttribute("info", info);
		}
		return VM_PATH + "contractView.vm";
	}
	
	@RequestMapping("view.htm")
	public String view(Long formId, Model model) {
		
		if (formId != null && formId > 0) {
			ChannalContractInfo info = channalContractClient.info(formId);
			model.addAttribute("info", info);
			model.addAttribute("form", info);
			model.addAttribute("formId", info.getFormId());
		}
		return VM_PATH + "contractView.vm";
	}
	
	@RequestMapping("audit.htm")
	public String audit(HttpServletRequest request, Long formId, Model model) {
		ChannalContractInfo info = channalContractClient.info(formId);
		FormInfo formInfo = formServiceCrmClient.findByFormId(formId);
		model.addAttribute("info", info);
		model.addAttribute("form", formInfo);
		model.addAttribute("formId", formInfo.getFormId());
		initWorkflow(model, formInfo, request.getParameter("taskId"));
		return VM_PATH + "contractView.vm";
	}
	
	/**
	 * 审核选择法务经理
	 * */
	@RequestMapping("auditChooseLegalManager.htm")
	public String auditChooseLegalManager(long formId, HttpServletRequest request, Model model,
											HttpSession session) {
		
		FormInfo form = formServiceCrmClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		ChannalContractInfo info = channalContractClient.info(formId);
		
		model.addAttribute("info", info);
		model.addAttribute("form", form);// 表单信息
		model.addAttribute("formId", form.getFormId());
		model.addAttribute("formCode", form.getFormCode());
		model.addAttribute("needLegalManager", "IS");
		initWorkflow(model, form, request.getParameter("taskId"));
		model.addAttribute("requestUrl", getRequestUrl(request));
		model.addAttribute("isChecker", true);
		
		return VM_PATH + "contractView.vm";
	}
	
	/**
	 * 合同回传
	 * @param contractReturn 回传合同url
	 * @param formId 合同表单Id
	 * 
	 * */
	@ResponseBody
	@RequestMapping("returnContract.json")
	public JSONObject returnContract(long formId, String contractReturn) {
		JSONObject json = new JSONObject();
		if (StringUtil.isBlank(contractReturn) || formId == 0) {
			json.put("success", false);
			json.put("message", "回传合同或表单能为空");
			return json;
		}
		FcsBaseResult result = channalContractClient.returnContract(formId, contractReturn);
		json.put("success", result.isSuccess());
		json.put("message", result.getMessage());
		return json;
		
	}
}
