package com.born.fcs.face.web.controller.fund.payment;

import java.util.ArrayList;
import java.util.Date;
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
import com.born.face.webui.table.ReportTemplate;
import com.born.face.webui.table.TableBuilder;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.FundBaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.fm.ws.enums.AccountStatusEnum;
import com.born.fcs.fm.ws.enums.BranchPayStatusEnum;
import com.born.fcs.fm.ws.enums.VoucherStatusEnum;
import com.born.fcs.fm.ws.info.payment.FormTravelExpenseInfo;
import com.born.fcs.fm.ws.order.payment.ConfirmBranchPayOrder;
import com.born.fcs.fm.ws.order.payment.TravelExpenseDetailOrder;
import com.born.fcs.fm.ws.order.payment.TravelExpenseOrder;
import com.born.fcs.fm.ws.order.payment.TravelExpenseQueryOrder;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.user.UserExtraMessageResult;
import com.born.fcs.pm.ws.service.report.field.DataTypeEnum;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;
import com.born.fcs.pm.ws.service.report.result.ReportItem;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

@Controller
@RequestMapping("fundMg/travelExpense/")
public class TravelExpenseController extends FundBaseController {
	final static String vm_path = "/fundMg/";
	
	@RequestMapping("travellist.htm")
	public String travelList(HttpServletRequest request, HttpServletResponse response, Model model) {
		buildSystemNameDefaultUrl(request, model);
		TravelExpenseQueryOrder order = new TravelExpenseQueryOrder();
		setSessionLocalInfo2Order(order);
		WebUtil.setPoPropertyByRequest(order, request);
		if (!StringUtil.equals(request.getParameter("from"), "query") && order.getAuditor() == 0) {
			SessionLocal session = ShiroSessionUtils.getSessionLocal();
			order.setAuditor(session.getUserId());
		}
		// 添加资金类型
		String accountStatus = request.getParameter("accountStatus");
		if (StringUtil.isNotBlank(accountStatus)) {
			order.setAccountStatus(AccountStatusEnum.getByCode(accountStatus));
		}
		
		QueryBaseBatchResult<FormTravelExpenseInfo> queryResult = getTravelExpenseResult(order,
			request, response, model);
		model.addAttribute("page", PageUtil.getCovertPage(queryResult));
		model.addAttribute("conditions", order);
		
		//		List statusList = Lists.newArrayList();
		//		statusList.addAll(FormStatusEnum.getAllEnum());
		//		statusList.add(CommonUtil.newJson("{'code':'REDEEMED','message':'已赎回'}"));
		//		model.addAttribute("isFinancialPersonnel", DataPermissionUtil.isFinancialZjlc()
		//													|| DataPermissionUtil.isXinHuiBusiManager());
		//		model.addAttribute("statusList", statusList);
		
		model.addAttribute("isBranchPayPos", DataPermissionUtil.isBranchPayPos());
		if (ShiroSessionUtils.getSessionLocal() != null)
			model.addAttribute("me", ShiroSessionUtils.getSessionLocal().getUserDetailInfo());
		
		return vm_path + "paymentMg/travel/list.vm";
	}
	
	@RequestMapping("export.json")
	public String export(HttpServletRequest request, HttpServletResponse response, Model model) {
		buildSystemNameDefaultUrl(request, model);
		TravelExpenseQueryOrder order = new TravelExpenseQueryOrder();
		setSessionLocalInfo2Order(order);
		WebUtil.setPoPropertyByRequest(order, request);
		if (!StringUtil.equals(request.getParameter("from"), "query") && order.getAuditor() == 0) {
			SessionLocal session = ShiroSessionUtils.getSessionLocal();
			order.setAuditor(session.getUserId());
		}
		// 添加资金类型
		String accountStatus = request.getParameter("accountStatus");
		if (StringUtil.isNotBlank(accountStatus)) {
			order.setAccountStatus(AccountStatusEnum.getByCode(accountStatus));
		}
		
		order.setPageNumber(1);
		order.setPageSize(999);
		QueryBaseBatchResult<FormTravelExpenseInfo> queryResult = getTravelExpenseResult(order,
			request, response, model);
		if (queryResult != null && ListUtil.isNotEmpty(queryResult.getPageList())) {
			makeExcel(queryResult.getPageList(), request, response);
		}
		return null;
	}
	
	public void makeExcel(List<FormTravelExpenseInfo> infos, HttpServletRequest request,
							HttpServletResponse response) {
		ReportDataResult dataResult = makeResult(infos);
		ReportTemplate reportTemplate = new ReportTemplate();
		reportTemplate.setReportName("差旅费报销单");
		
		reportTemplate.setMergeRow(false);
		reportTemplate.setMergeColCount(2);
		TableBuilder builder = new TableBuilder(dataResult, reportTemplate,
			TableBuilder.Table_Option_Excel, false);
		builder.init();
		builder.dataBind(request, response);
	}
	
	private static ReportDataResult makeResult(List<FormTravelExpenseInfo> infos) {
		
		ReportDataResult dataResult = new ReportDataResult();
		try {
			dataResult.getFcsFields().add(new FcsField("billNo", "单据号", null));
			dataResult.getFcsFields().add(new FcsField("applicationTime", "申请日期", null));
			dataResult.getFcsFields().add(new FcsField("deptName", "申请部门", null));
			
			dataResult.getFcsFields().add(new FcsField("reasons", "出差事由", null));
			dataResult.getFcsFields().add(new FcsField("payee", "收款人", null));
			dataResult.getFcsFields().add(new FcsField("bank", "开户银行", null));
			dataResult.getFcsFields().add(new FcsField("bankAccount", "银行账号", null));
			
			dataResult.getFcsFields().add(new FcsField("userName", "申请人", null));
			dataResult.getFcsFields().add(
				new FcsField("amount", "差旅费金额（元）", null, DataTypeEnum.MONEY));
			
			dataResult.getFcsFields().add(new FcsField("accountStatus", "单据状态", null));
			dataResult.getFcsFields().add(new FcsField("voucherStatus", "是否过账", null));
			dataResult.getFcsFields().add(new FcsField("branchPayStatus", "是否已确认付款[分支机构]", null));
			dataResult.getFcsFields().add(new FcsField("voucherNo", "凭证号", null));
			
			List<DataListItem> dataListItems = Lists.newArrayList();
			
			for (FormTravelExpenseInfo info : infos) {
				// 信息
				List<ReportItem> valueList = Lists.newArrayList();
				ReportItem reportItem = new ReportItem();
				DataListItem item = new DataListItem();
				reportItem = new ReportItem();
				reportItem.setKey("billNo");
				reportItem.setValue(info.getBillNo());
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("applicationTime");
				reportItem.setValue(DateUtil.dtSimpleFormat(info.getApplicationTime()));
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("deptName");
				reportItem.setValue(info.getFormDeptName());
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("reasons");
				reportItem.setValue(info.getReasons());
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("payee");
				reportItem.setValue(info.getPayee());
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				
				valueList.add(reportItem);
				reportItem = new ReportItem();
				reportItem.setKey("bank");
				reportItem.setValue(info.getBank());
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("bankAccount");
				reportItem.setValue(info.getBankAccount());
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("userName");
				reportItem.setValue(info.getFormUserName());
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("amount");
				reportItem.setValue(info.getAmount().toStandardString());
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("accountStatus");
				if (info.getAccountStatus() != null) {
					reportItem.setValue(info.getAccountStatus().message());
				} else {
					reportItem.setValue("-");
				}
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("voucherStatus");
				if (info.getVoucherStatus() == VoucherStatusEnum.SYNC_DELETE) {
					reportItem.setValue("已删除");
				} else if (info.getVoucherStatus() == VoucherStatusEnum.SYNC_FINISH) {
					reportItem.setValue("是");
				} else if (info.getVoucherStatus() == VoucherStatusEnum.SEND_SUCCESS) {
					reportItem.setValue("否(已发送)");
				} else {
					reportItem.setValue("否");
				}
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("branchPayStatus");
				if (info.getBranchPayStatus() == BranchPayStatusEnum.PAID) {
					reportItem.setValue("是");
				} else if (info.getBranchPayStatus() == BranchPayStatusEnum.NOT_PAY) {
					reportItem.setValue("否");
				} else {
					reportItem.setValue("-");
				}
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				item.setValueList(valueList);
				dataListItems.add(item);
				
			}
			
			dataResult.setDataList(dataListItems);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataResult;
	}
	
	@RequestMapping("travelAdd.htm")
	public String travelAdd(HttpServletRequest request, HttpServletResponse response, Model model) {
		buildSystemNameDefaultUrl(request, model);
		String travelId = request.getParameter("id");
		String formId = request.getParameter("formId");
		
		FormTravelExpenseInfo info = null;
		
		if (StringUtil.isNotBlank(travelId)) {
			info = travelExpenseServiceClient.queryById(Long.valueOf(travelId));
			
		} else if (StringUtil.isNotBlank(formId)) {
			info = travelExpenseServiceClient.queryByFormId(Long.valueOf(formId));
		}
		
		if (info != null) {
			model.addAttribute("info", info);
			
			FormInfo form = formServiceFmClient.findByFormId(info.getFormId());
			if (form == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
			}
			model.addAttribute("form", form);
			
			queryCommonAttachmentData(model, String.valueOf(info.getBillNo()),
				CommonAttachmentTypeEnum.FORM_ATTACH);
		}
		
		// 抓取经办人的银行帐号信息
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			UserExtraMessageResult userResult = userExtraMessageServiceClient
				.findByUserId(sessionLocal.getUserId());
			if (userResult.isSuccess() && userResult.isExecuted()
				&& userResult.getUserExtraMessageInfo() != null) {
				model.addAttribute("localUserExtra", userResult.getUserExtraMessageInfo());
			}
		}
		
		model.addAttribute("now", new Date());
		return vm_path + "paymentMg/travel/addForm.vm";
	}
	
	@ResponseBody
	@RequestMapping("add.json")
	public JSONObject add(HttpServletRequest request, Model model) {
		TravelExpenseOrder order = new TravelExpenseOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		order.setFormCode(FormCodeEnum.TRAVEL_EXPENSE_APPLY);
		if (StringUtil.isNotBlank(request.getParameter("officialCard"))) {
			order.setIsOfficialCard(BooleanEnum.getByCode(request.getParameter("officialCard")));
		} else {
			order.setIsOfficialCard(BooleanEnum.NO);
		}
		order.setCheckIndex(0);
		order.setCheckStatus(1);
		setSessionLocalInfo2Order(order);
		setDetailList(order, request);
		FormBaseResult result = travelExpenseServiceClient.save(order);
		if (result != null && result.isSuccess()) {
			addAttachfile(result.getMessage(), request, null, "差旅费报销申请单",
				CommonAttachmentTypeEnum.FORM_ATTACH);
		}
		JSONObject json = toJSONResult(result, "保存");
		Object obj = result.getReturnObject();
		if (obj != null) {
			FormTravelExpenseInfo info = (FormTravelExpenseInfo) obj;
			if (info != null) {
				json.put("id", info.getTravelId());
				json.put("billNo", info.getBillNo());
			}
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("conformBranchPay.json")
	public JSONObject conformBranchPay(ConfirmBranchPayOrder order, Model model) {
		JSONObject json = new JSONObject();
		try {
			setSessionLocalInfo2Order(order);
			FcsBaseResult result = travelExpenseServiceClient.confirmBranchPay(order);
			if (result != null && result.isSuccess()) {
				json.put("success", true);
				json.put("message", "确认成功");
			} else {
				json.put("success", false);
				json.put("message", "确认出错[" + (result == null ? "" : result.getMessage()) + "]");
			}
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "确认付款出错");
			logger.error("确认付款出错{}", e);
		}
		return json;
	}
	
	@RequestMapping("view.htm")
	public String view(long formId, HttpSession session, HttpServletRequest request, Model model) {
		buildSystemNameDefaultUrl(request, model);
		if (formId > 0) {
			FormInfo form = formServiceFmClient.findByFormId(formId);
			if (form == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
			}
			FormTravelExpenseInfo info = travelExpenseServiceClient.queryByFormId(formId);
			model.addAttribute("info", info);
			model.addAttribute("form", form);
			model.addAttribute("isview", true);
			queryCommonAttachmentData(model, String.valueOf(info.getBillNo()),
				CommonAttachmentTypeEnum.FORM_ATTACH);
		}
		return vm_path + "paymentMg/travel/viewAudit.vm";
	}
	
	@RequestMapping("edit.htm")
	public String edit(long formId, HttpServletRequest request, HttpSession session, Model model) {
		FormInfo form = formServiceFmClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		
		FormTravelExpenseInfo info = travelExpenseServiceClient.queryByFormId(formId);
		model.addAttribute("info", info);
		model.addAttribute("form", form);
		
		queryCommonAttachmentData(model, String.valueOf(info.getBillNo()),
			CommonAttachmentTypeEnum.FORM_ATTACH);
		
		return vm_path + "paymentMg/travel/addForm.vm";
	}
	
	@ResponseBody
	@RequestMapping("proPayBank.json")
	public JSONObject proPayBank(long formId, String payBank, String payBankAccount) {
		TravelExpenseOrder order = new TravelExpenseOrder();
		order.setFormId(formId);
		order.setPayBank(payBank);
		order.setPayBankAccount(payBankAccount);
		travelExpenseServiceClient.updatePayBank(order);
		JSONObject json = new JSONObject();
		json.put("success", true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("update.json")
	public JSONObject update(HttpServletRequest request, Model model) {
		TravelExpenseOrder order = new TravelExpenseOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		String onlyChange = request.getParameter("onlyChangeDetailList");
		if (StringUtil.isNotBlank(onlyChange)) {
			order.setOnlyChangeDetailList(BooleanEnum.getByCode(onlyChange));
		}
		order.setFormCode(FormCodeEnum.TRAVEL_EXPENSE_APPLY);
		if (StringUtil.isNotBlank(request.getParameter("officialCard"))) {
			order.setIsOfficialCard(BooleanEnum.getByCode(request.getParameter("officialCard")));
		} else {
			order.setIsOfficialCard(BooleanEnum.NO);
		}
		order.setCheckIndex(0);
		order.setCheckStatus(1);
		setSessionLocalInfo2Order(order);
		setDetailList(order, request);
		FormBaseResult result = travelExpenseServiceClient.save(order);
		if (result != null && result.isSuccess()) {
			addAttachfile(result.getMessage(), request, null, "差旅费报销申请单",
				CommonAttachmentTypeEnum.FORM_ATTACH);
		}
		JSONObject json = toJSONResult(result, "保存");
		Object obj = null;
		if (result != null)
			obj = result.getReturnObject();
		if (obj != null) {
			FormTravelExpenseInfo info = (FormTravelExpenseInfo) obj;
			if (info != null) {
				json.put("id", info.getTravelId());
				json.put("billNo", info.getBillNo());
			}
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
		doAudit(formId, request, model);
		
		return vm_path + "paymentMg/travel/viewAudit.vm";
	}
	
	/**
	 * 审核【在财务分管副总驳回后，能修改费用种类和金额，金额只能修改为比之前的金额小】
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("audit/canModify.htm")
	public String auditCanModify(long formId, HttpServletRequest request, Model model) {
		model.addAttribute("onlyChangeDetailList", BooleanEnum.YES.code());
		doAudit(formId, request, model, true);
		
		return vm_path + "paymentMg/travel/viewAudit.vm";
	}
	
	/**
	 * 审核【确认打款的识别】
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("audit/canAccount.htm")
	public String auditCanAccount(long formId, HttpServletRequest request, Model model) {
		doAudit(formId, request, model, false);
		
		return vm_path + "paymentMg/travel/viewAudit.vm";
	}
	
	private void doAudit(long formId, HttpServletRequest request, Model model) {
		doAudit(formId, request, model, false);
	}
	
	private void doAudit(long formId, HttpServletRequest request, Model model,
							boolean getBankMessage) {
		model.addAttribute("_SYSNAME", "FM");
		FormInfo formInfo = formServiceFmClient.findByFormId(formId);
		if (formInfo == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "差旅费报销单不存在");
		}
		model.addAttribute("form", formInfo);
		
		FormTravelExpenseInfo info = travelExpenseServiceClient.queryByFormId(formId);
		model.addAttribute("info", info);
		// 获取默认支付银行信息
		model.addAttribute("defaultPayBank",
			findDefaultPaymentBankInfo(null == info ? 0L : info.getExpenseDeptId()));
		queryCommonAttachmentData(model, String.valueOf(info.getBillNo()),
			CommonAttachmentTypeEnum.FORM_ATTACH);
		initWorkflow(model, formInfo, request.getParameter("taskId"));
		//boolean isFYFG = DataPermissionUtil.isFinancialYFG();
		boolean isFYFG = false;
		if (getBankMessage) {
			isFYFG = true;
			model.addAttribute("isFYFG", isFYFG);
		}
		if (isFYFG
			&& getBankMessage
			&& (StringUtil.isBlank(info.getPayBankAccount()) && StringUtil.isBlank(info
				.getPayBank()))) {
			getPayBank(info);
			
		}
	}
	
	private void setDetailList(TravelExpenseOrder order, HttpServletRequest request) {
		String[] startTimes = request.getParameterValues("startTime");
		String onlyChange = request.getParameter("onlyChangeDetailList");
		order.setAmount(Money.zero());
		if ((startTimes != null && startTimes.length > 0) || StringUtil.isNotBlank(onlyChange)) {
			String[] detailIds = request.getParameterValues("detailId");
			String[] endTimes = request.getParameterValues("endTime");
			String[] dayss = request.getParameterValues("days");
			String[] startPlaces = request.getParameterValues("startPlace");
			String[] endPlaces = request.getParameterValues("endPlace");
			String[] trafficAmounts = request.getParameterValues("trafficAmount");
			String[] hotelAmounts = request.getParameterValues("hotelAmount");
			String[] taxAmounts = request.getParameterValues("taxAmount");
			String[] mealsAmounts = request.getParameterValues("mealsAmount");
			String[] allowanceAmounts = request.getParameterValues("allowanceAmount");
			String[] otherAmounts = request.getParameterValues("otherAmount");
			String[] totalAmounts = request.getParameterValues("totalAmount");
			String[] orgNames = request.getParameterValues("orgName");
			String[] orgIds = request.getParameterValues("orgId");
			
			ArrayList<TravelExpenseDetailOrder> detailOrderList = new ArrayList<TravelExpenseDetailOrder>(
				startTimes.length);
			for (int i = 0; i < startTimes.length; i++) {
				TravelExpenseDetailOrder detailOrder = new TravelExpenseDetailOrder();
				if (StringUtil.isNotBlank(detailIds[i]))
					detailOrder.setDetailId(Long.valueOf(detailIds[i]));
				if (StringUtil.isNotBlank(startTimes[i]))
					detailOrder.setStartTime(DateUtil.parse(startTimes[i]));
				if (StringUtil.isNotBlank(endTimes[i]))
					detailOrder.setEndTime(DateUtil.parse(endTimes[i]));
				detailOrder.setDays(dayss[i]);
				detailOrder.setStartPlace(startPlaces[i]);
				detailOrder.setEndPlace(endPlaces[i]);
				if (StringUtil.isNotBlank(trafficAmounts[i]))
					detailOrder.setTrafficAmount(new Money(trafficAmounts[i]));
				if (StringUtil.isNotBlank(hotelAmounts[i]))
					detailOrder.setHotelAmount(new Money(hotelAmounts[i]));
				if (StringUtil.isNotBlank(taxAmounts[i]))
					detailOrder.setTaxAmount(new Money(taxAmounts[i]));
				if (StringUtil.isNotBlank(mealsAmounts[i]))
					detailOrder.setMealsAmount(new Money(mealsAmounts[i]));
				if (StringUtil.isNotBlank(allowanceAmounts[i]))
					detailOrder.setAllowanceAmount(new Money(allowanceAmounts[i]));
				if (StringUtil.isNotBlank(otherAmounts[i]))
					detailOrder.setOtherAmount(new Money(otherAmounts[i]));
				
				detailOrder.setTotalAmount(detailOrder.getTotalAmount()
					.add(detailOrder.getTrafficAmount()).add(detailOrder.getHotelAmount())
					.add(detailOrder.getMealsAmount()).add(detailOrder.getAllowanceAmount())
					.add(detailOrder.getOtherAmount()));
				
				detailOrder.setDeptId(orgIds[i]);
				detailOrder.setDeptName(orgNames[i]);
				detailOrderList.add(detailOrder);
				
				order.setAmount(order.getAmount().add(detailOrder.getTotalAmount()));
			}
			
			order.setDetailList(detailOrderList);
		}
	}
}
