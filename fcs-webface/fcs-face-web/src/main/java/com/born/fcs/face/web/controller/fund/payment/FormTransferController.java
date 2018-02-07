package com.born.fcs.face.web.controller.fund.payment;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.fm.ws.enums.TExpQueryProcessEnum;
import com.born.fcs.fm.ws.info.payment.FormTransferInfo;
import com.born.fcs.fm.ws.order.payment.FormTransferDetailOrder;
import com.born.fcs.fm.ws.order.payment.FormTransferOrder;
import com.born.fcs.fm.ws.order.payment.FormTransferQueryOrder;
import com.born.fcs.fm.ws.result.bank.BankMessageResult;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

@Controller
@RequestMapping("fundMg/transfer/")
public class FormTransferController extends WorkflowBaseController {
	final static String vm_path = "/fundMg/paymentMg/transfer/";
	
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		buildSystemNameDefaultUrl(request, model);
		FormTransferQueryOrder order = new FormTransferQueryOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		setSessionLocalInfo2Order(order);
		order.setLoginUserId(0);
		long curUserId = ShiroSessionUtils.getSessionLocal().getUserId();
		String process = request.getParameter("process");
		if (StringUtil.isNotBlank(process)) {//查询流程
			order.setProcess(TExpQueryProcessEnum.getByCode(process));
			model.addAttribute("process", process);
		}
		
		String sef = request.getParameter("sef");
		if (StringUtil.isNotBlank(sef)) {//用户发起
			order.setAgent(String.valueOf(curUserId));
			model.addAttribute("sef", sef);
		}
		
		if (!DataPermissionUtil.isSystemAdministrator()) {
			
			order.setCurUserId(curUserId);//发起人仅能看到自己的，审核人能看到自己审核的所有单据
		}
		
		String searchUserId = request.getParameter("searchUserId");
		if (StringUtil.isNotBlank(searchUserId)) {
			List searchUserIdList = Lists.newArrayList();
			String[] uid = searchUserId.split(",");
			for (String id : uid) {
				searchUserIdList.add(id);
			}
			order.setSearchUserIdList(searchUserIdList);
		}
		model.addAttribute("searchUserId", searchUserId);
		model.addAttribute("searchUserName", request.getParameter("searchUserName"));
		
		List statusList = Lists.newArrayList();
		statusList.add(FormStatusEnum.DELETED);
		//		if("WAIT".equals(process)){//待办流程
		//			statusList.add(FormStatusEnum.DELETED);
		//		}
		order.setExcFormStatusList(statusList);
		
		if (StringUtil.isBlank(order.getSortCol())) {
			order.setSortCol("raw_add_time");
			order.setSortOrder("DESC");
		}
		
		QueryBaseBatchResult<FormTransferInfo> queryResult = formTransferServiceClient
			.queryPage(order);
		model.addAttribute("page", PageUtil.getCovertPage(queryResult));
		model.addAttribute("conditions", order);
		
		model.addAttribute("CW", DataPermissionUtil.isFinancialPersonnel());
		
		return vm_path + "list.vm";
	}
	
	@RequestMapping("add.htm")
	public String add(HttpServletRequest request, HttpServletResponse response, Model model) {
		buildSystemNameDefaultUrl(request, model);
		String transferId = request.getParameter("id");
		if (StringUtil.isNotBlank(transferId)) {
			FormTransferInfo info = formTransferServiceClient.queryById(Long.valueOf(transferId));
			model.addAttribute("info", info);
			
			FormInfo form = formServiceFmClient.findByFormId(info.getFormId());
			if (form == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
			}
			model.addAttribute("form", form);
			
			queryCommonAttachmentData(model, String.valueOf(info.getBillNo()),
				CommonAttachmentTypeEnum.FORM_ATTACH);
		}
		return vm_path + "addForm.vm";
	}
	
	@ResponseBody
	@RequestMapping("save.json")
	public JSONObject save(HttpServletRequest request, Model model) {
		FormTransferOrder order = new FormTransferOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		order.setFormCode(FormCodeEnum.TRANSFER_APPLICATION);
		
		order.setCheckIndex(0);
		order.setCheckStatus(1);
		setSessionLocalInfo2Order(order);
		order.setDetailList(getDetailList(request));
		FormBaseResult result = formTransferServiceClient.save(order);
		if (result != null && result.isSuccess()) {
			addAttachfile(result.getMessage(), request, null, "转账申请单附件 ",
				CommonAttachmentTypeEnum.FORM_ATTACH);
		}
		return toJSONResult(result, "转账申请单保存成功");
	}
	
	@RequestMapping("view.htm")
	public String view(long formId, HttpSession session, HttpServletRequest request, Model model) {
		buildSystemNameDefaultUrl(request, model);
		if (formId > 0) {
			FormInfo form = formServiceFmClient.findByFormId(formId);
			if (form == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
			}
			FormTransferInfo info = formTransferServiceClient.queryByFormId(formId);
			model.addAttribute("info", info);
			model.addAttribute("form", form);
			
			queryCommonAttachmentData(model, String.valueOf(info.getBillNo()),
				CommonAttachmentTypeEnum.FORM_ATTACH);
			
			setAuditHistory2Page(form, model);
		}
		return vm_path + "viewAudit.vm";
	}
	
	@RequestMapping("edit.htm")
	public String edit(long formId, HttpServletRequest request, HttpSession session, Model model) {
		FormInfo form = formServiceFmClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		
		FormTransferInfo info = formTransferServiceClient.queryByFormId(formId);
		model.addAttribute("info", info);
		model.addAttribute("form", form);
		
		// 查询 
		BankMessageResult bankResult = bankMessageServiceClient
			.findByAccount(info.getBankAccount());
		if (bankResult.isSuccess()) {
			model.addAttribute("defaultbankInfo", bankResult.getBankMessageInfo());
		}
		
		queryCommonAttachmentData(model, String.valueOf(info.getBillNo()),
			CommonAttachmentTypeEnum.FORM_ATTACH);
		
		return vm_path + "addForm.vm";
	}
	
	//	@ResponseBody
	//	@RequestMapping("update.json")
	//	public JSONObject update(HttpServletRequest request, Model model) {
	//		TravelExpenseOrder order = new TravelExpenseOrder();
	//		WebUtil.setPoPropertyByRequest(order, request);
	//		order.setFormCode(FormCodeEnum.TRAVEL_EXPENSE_APPLY);
	//		if(StringUtil.isNotBlank(request.getParameter("officialCard"))){
	//			order.setIsOfficialCard(BooleanEnum.getByCode(request.getParameter("officialCard")));
	//		}else{
	//			order.setIsOfficialCard(BooleanEnum.NO);
	//		}
	//		order.setCheckIndex(0);
	//		order.setCheckStatus(1);
	//		setSessionLocalInfo2Order(order);
	//		order.setDetailList(getDetailList(request));
	//		FormBaseResult result = travelExpenseServiceClient.save(order);
	//		if (result != null && result.isSuccess()) {
	//			addAttachfile(result.getMessage(), request,
	//					CommonAttachmentTypeEnum.FORM_ATTACH);
	//		}
	//		return toJSONResult(result, "转账申请单更新成功");
	//		
	//	}
	
	/**
	 * 审核
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("audit.htm")
	public String audit(long formId, HttpServletRequest request, Model model) {
		model.addAttribute("_SYSNAME", "FM");
		FormInfo formInfo = formServiceFmClient.findByFormId(formId);
		if (formInfo == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "转账申请单不存在");
		}
		model.addAttribute("form", formInfo);
		
		FormTransferInfo info = formTransferServiceClient.queryByFormId(formId);
		model.addAttribute("info", info);
		
		queryCommonAttachmentData(model, String.valueOf(info.getBillNo()),
			CommonAttachmentTypeEnum.FORM_ATTACH);
		initWorkflow(model, formInfo, request.getParameter("taskId"));
		return vm_path + "viewAudit.vm";
	}
	
	private ArrayList<FormTransferDetailOrder> getDetailList(HttpServletRequest request) {
		String[] detailIds = request.getParameterValues("detailId");
		if (detailIds != null && detailIds.length > 0) {
			String[] detailamounts = request.getParameterValues("detailamount");
			String[] detailbankIds = request.getParameterValues("detailbankId");
			String[] detailbankNames = request.getParameterValues("detailbankName");
			String[] detaibankAccounts = request.getParameterValues("detaibankAccount");
			
			ArrayList<FormTransferDetailOrder> detailOrderList = new ArrayList<FormTransferDetailOrder>(
				detailIds.length);
			for (int i = 0; i < detailIds.length; i++) {
				FormTransferDetailOrder detailOrder = new FormTransferDetailOrder();
				if (StringUtil.isNotBlank(detailIds[i]))
					detailOrder.setDetailId(Long.valueOf(detailIds[i]));
				if (StringUtil.isNotBlank(detailamounts[i]))
					detailOrder.setAmount(new Money(detailamounts[i]));
				if (StringUtil.isNotBlank(detailbankIds[i]))
					detailOrder.setBankId(Long.valueOf(detailbankIds[i]));
				if (StringUtil.isNotBlank(detailbankNames[i]))
					detailOrder.setBankName(detailbankNames[i]);
				if (StringUtil.isNotBlank(detaibankAccounts[i]))
					detailOrder.setBankAccount(detaibankAccounts[i]);
				detailOrderList.add(detailOrder);
			}
			
			return detailOrderList;
		}
		
		return null;
	}
	
}
