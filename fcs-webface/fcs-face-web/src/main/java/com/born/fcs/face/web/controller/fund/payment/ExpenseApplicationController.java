package com.born.fcs.face.web.controller.fund.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONArray;
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
import com.born.fcs.fm.ws.enums.BranchPayStatusEnum;
import com.born.fcs.fm.ws.enums.CostCategoryStatusEnum;
import com.born.fcs.fm.ws.enums.CostDirectionEnum;
import com.born.fcs.fm.ws.enums.ReportCompanyEnum;
import com.born.fcs.fm.ws.enums.VoucherStatusEnum;
import com.born.fcs.fm.ws.info.payment.CostCategoryInfo;
import com.born.fcs.fm.ws.info.payment.ExpenseCxDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationDetailAllInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationInfo;
import com.born.fcs.fm.ws.order.payment.ConfirmBranchPayOrder;
import com.born.fcs.fm.ws.order.payment.CostCategoryQueryOrder;
import com.born.fcs.fm.ws.order.payment.ExpenseApplicationDetailOrder;
import com.born.fcs.fm.ws.order.payment.ExpenseApplicationOrder;
import com.born.fcs.fm.ws.order.payment.ExpenseApplicationQueryOrder;
import com.born.fcs.fm.ws.order.payment.ExpenseCxDetailOrder;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
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
@RequestMapping("fundMg/expenseApplication/")
public class ExpenseApplicationController extends FundBaseController {
	final static String vm_path = "/fundMg/paymentMg/cost/";
	
	final static HashMap<String, FormCodeEnum> fmap = new HashMap<String, FormCodeEnum>();
	final static List<String> notShowList = new ArrayList<String>();
	/** 劳资 */
	final static List<String> lzList = new ArrayList<String>();
	/** 税金 */
	final static List<String> sjList = new ArrayList<String>();
	/** 信惠无金额 */
	final static List<String> xhwjeList = new ArrayList<String>();
	static {
		//费用支付(无金额规则类)审核
		fmap.put("#公证费#", FormCodeEnum.EXPENSE_APPLICATION_NLIMIT);
		//费用支付(无金额规则类 税金类)审核
		//		fmap.put("#房产税#车船使用税#土地使用税#印花税#", FormCodeEnum.EXPENSE_APPLICATION_NLIMIT);
		//上面四类合并为一类
		fmap.put("#税金#", FormCodeEnum.EXPENSE_APPLICATION_NLIMIT);
		//费用支付(还款类)审批
		fmap.put("#还款#", FormCodeEnum.EXPENSE_APPLICATION_REFUND);
		//费用支付(党费)审批 
		fmap.put("#党建经费#", FormCodeEnum.EXPENSE_APPLICATION_DF);
		//费用支付(团委费)审批 
		fmap.put("#团委费#", FormCodeEnum.EXPENSE_APPLICATION_TF);
		//费用支付(借款类)审批流程
		fmap.put("#借款#", FormCodeEnum.EXPENSE_APPLICATION_LOAN);
		//费用支付(冲预付款类)审批
		fmap.put("#退预付款#冲预付款#", FormCodeEnum.EXPENSE_APPLICATION_CPREPAY);
		//费用支付(预付款类)审批
		fmap.put("#预付款#", FormCodeEnum.EXPENSE_APPLICATION_PREPAY);
		
		fmap.put("#工会经费#", FormCodeEnum.EXPENSE_APPLICATION_UNION_FUNDS);
		//费用支付(劳资类)审核
		fmap.put("#工资#养老保险#失业保险#医疗保险#工伤保险#生育保险#住房公积金#补充医疗保险#企业年金#补充养老保险#劳务费#福利费（其他）#",
			FormCodeEnum.EXPENSE_APPLICATION_LZ);
		//费用支付(金额规则类)审批
		fmap.put(
			"#职教经费#广告宣传费#业务招待费#办公费#会务费#水电费#物管费#租赁费#邮电通讯费#修理费#外事费#招聘费#车辆运行费#董事会经费#开办费#装修费#其他#福利费（食堂）#会员费#劳动保护费#审计评估费#咨询费#聘请律师及诉讼费#",
			FormCodeEnum.EXPENSE_APPLICATION_LIMIT);
		
		//		xhwjeList.add("审计评估费");
		//		xhwjeList.add("咨询费");
		xhwjeList.add("公证费");
		//		xhwjeList.add("聘请律师及诉讼费");
		
		lzList.add("工资");
		lzList.add("养老保险");
		lzList.add("失业保险");
		lzList.add("医疗保险");
		lzList.add("工伤保险");
		lzList.add("生育保险");
		lzList.add("住房公积金");
		lzList.add("补充医疗保险");
		lzList.add("企业年金");
		lzList.add("补充养老保险");
		lzList.add("劳务费");
		lzList.add("福利费（其他）");
		//		lzList.add("劳动保护费");
		//		sjList.add("房产税");
		//		sjList.add("车船使用税");
		//		sjList.add("土地使用税");
		//		sjList.add("印花税");
		sjList.add("税金");
		
		//		notShowList.add("工资");
		//		notShowList.add("养老保险");
		//		notShowList.add("失业保险");
		//		notShowList.add("医疗保险");
		//		notShowList.add("工伤保险");
		//		notShowList.add("生育保险");
		//		notShowList.add("住房公积金");
		//		notShowList.add("补充医疗保险");
		//		notShowList.add("企业年金");
		//		notShowList.add("补充养老保险");
		//		notShowList.add("外派劳务费");
		//		notShowList.add("其他（劳资）");
		//		notShowList.add("福利费（其他）");
		//		notShowList.add("房产税");
		//		notShowList.add("车船使用税");
		//		notShowList.add("土地使用税");
		//		notShowList.add("印花税");
		//		notShowList.add("其他（税金）");
	}
	
	@RequestMapping("ealist.htm")
	public String ealist(HttpServletRequest request, HttpServletResponse response, Model model) {
		buildSystemNameDefaultUrl(request, model);
		ExpenseApplicationQueryOrder order = getExpenseApplicationQueryOrder(request, model);
		if (!StringUtil.equals(request.getParameter("from"), "query") && order.getAuditor() == 0) {
			SessionLocal session = ShiroSessionUtils.getSessionLocal();
			order.setAuditor(session.getUserId());
		}
		// 费用类型
		// 获取类型
		String expenseTypes = request.getParameter("expenseType");
		if (StringUtil.isNotBlank(expenseTypes) && expenseTypes.indexOf(",") > 0) {
			List<String> expenses = new ArrayList<>();
			String[] strs = expenseTypes.split(",");
			for (String str : strs) {
				expenses.add(str);
			}
			order.setExpenseTypes(expenses);
			order.setExpenseType(null);
		}
		QueryBaseBatchResult<FormExpenseApplicationDetailAllInfo> queryResult = expenseApplicationServiceClient
			.queryPageAll(order);
		CostCategoryQueryOrder order2 = new CostCategoryQueryOrder();
		order2.setStatusList(new ArrayList<CostCategoryStatusEnum>());
		order2.getStatusList().add(CostCategoryStatusEnum.NORMAL);
		order2.setPageSize(10000);
		QueryBaseBatchResult<CostCategoryInfo> batchResult = costCategoryServiceClient
			.queryPage(order2);
		if (queryResult != null && ListUtil.isNotEmpty(queryResult.getPageList())
			&& batchResult != null && ListUtil.isNotEmpty(batchResult.getPageList())) {
			for (FormExpenseApplicationDetailAllInfo info : queryResult.getPageList()) {
				String expenseType = info.getExpenseType();
				
				for (CostCategoryInfo category : batchResult.getPageList()) {
					if (expenseType.equals(String.valueOf(category.getCategoryId()))) {
						expenseType = category.getName();
					}
				}
				info.setExpenseType(expenseType);
			}
		}
		
		model.addAttribute("page", PageUtil.getCovertPage(queryResult));
		model.addAttribute("totalAmount",
			(queryResult == null || queryResult.getReturnObject() == null) ? Money.zero()
				: queryResult.getReturnObject());
		model.addAttribute("conditions", order);
		
		JSONArray jsons = new JSONArray();
		for (CostCategoryInfo info : getCostCategoryList(null, false)) {
			JSONObject json = new JSONObject();
			json.put("id", info.getCategoryId());
			json.put("name", info.getName());
			jsons.add(json);
		}
		model.addAttribute("costCategorysJson", jsons.toJSONString().replaceAll("\"", "'"));
		
		model.addAttribute("isBranchPayPos", DataPermissionUtil.isBranchPayPos());
		if (ShiroSessionUtils.getSessionLocal() != null)
			model.addAttribute("me", ShiroSessionUtils.getSessionLocal().getUserDetailInfo());
		
		return vm_path + "list.vm";
	}
	
	@RequestMapping("export.json")
	public String export(HttpServletRequest request, HttpServletResponse response, Model model) {
		buildSystemNameDefaultUrl(request, model);
		ExpenseApplicationQueryOrder order = getExpenseApplicationQueryOrder(request, model);
		if (!StringUtil.equals(request.getParameter("from"), "query") && order.getAuditor() == 0) {
			SessionLocal session = ShiroSessionUtils.getSessionLocal();
			order.setAuditor(session.getUserId());
		}
		// 费用类型
		// 获取类型
		String expenseTypes = request.getParameter("expenseType");
		if (StringUtil.isNotBlank(expenseTypes) && expenseTypes.indexOf(",") > 0) {
			List<String> expenses = new ArrayList<>();
			String[] strs = expenseTypes.split(",");
			for (String str : strs) {
				expenses.add(str);
			}
			order.setExpenseTypes(expenses);
			order.setExpenseType(null);
		}
		order.setPageNumber(1);
		order.setPageSize(999);
		QueryBaseBatchResult<FormExpenseApplicationDetailAllInfo> queryResult = expenseApplicationServiceClient
			.queryPageAll(order);
		CostCategoryQueryOrder order2 = new CostCategoryQueryOrder();
		order2.setStatusList(new ArrayList<CostCategoryStatusEnum>());
		order2.getStatusList().add(CostCategoryStatusEnum.NORMAL);
		order2.setPageSize(999);
		QueryBaseBatchResult<CostCategoryInfo> batchResult = costCategoryServiceClient
			.queryPage(order2);
		if (queryResult != null && ListUtil.isNotEmpty(queryResult.getPageList())
			&& batchResult != null && ListUtil.isNotEmpty(batchResult.getPageList())) {
			for (FormExpenseApplicationDetailAllInfo info : queryResult.getPageList()) {
				String expenseType = info.getExpenseType();
				for (CostCategoryInfo category : batchResult.getPageList()) {
					if (expenseType.equals(String.valueOf(category.getCategoryId()))) {
						expenseType = category.getName();
					}
				}
				info.setExpenseType(expenseType);
			}
		}
		if (queryResult != null && ListUtil.isNotEmpty(queryResult.getPageList())) {
			makeExcel(queryResult.getPageList(), request, response);
		}
		return null;
	}
	
	public void makeExcel(List<FormExpenseApplicationDetailAllInfo> infos,
							HttpServletRequest request, HttpServletResponse response) {
		ReportDataResult dataResult = makeResult(infos);
		ReportTemplate reportTemplate = new ReportTemplate();
		reportTemplate.setReportName("费用支付申请单");
		reportTemplate.setMergeRow(false);
		reportTemplate.setMergeColCount(2);
		TableBuilder builder = new TableBuilder(dataResult, reportTemplate,
			TableBuilder.Table_Option_Excel, false);
		builder.init();
		builder.dataBind(request, response);
	}
	
	private static ReportDataResult makeResult(List<FormExpenseApplicationDetailAllInfo> infos) {
		
		ReportDataResult dataResult = new ReportDataResult();
		try {
			dataResult.getFcsFields().add(new FcsField("billNo", "单据号", null));
			dataResult.getFcsFields().add(new FcsField("applicationTime", "申请日期", null));
			dataResult.getFcsFields().add(new FcsField("deptName", "申请部门", null));
			
			dataResult.getFcsFields().add(new FcsField("reimburseReason", "报销事由", null));
			dataResult.getFcsFields().add(new FcsField("payee", "收款人", null));
			dataResult.getFcsFields().add(new FcsField("bank", "开户银行", null));
			dataResult.getFcsFields().add(new FcsField("bankAccount", "银行账号", null));
			
			dataResult.getFcsFields().add(new FcsField("userName", "申请人", null));
			dataResult.getFcsFields().add(new FcsField("expenseType", "费用名称", null));
			dataResult.getFcsFields().add(
				new FcsField("amount", "费用申请金额（元）", null, DataTypeEnum.MONEY));
			dataResult.getFcsFields().add(new FcsField("status", "流程状态", null));
			dataResult.getFcsFields().add(new FcsField("accountStatus", "单据状态", null));
			dataResult.getFcsFields().add(new FcsField("waitPayTime", "待打款时间", null));
			dataResult.getFcsFields().add(new FcsField("voucherStatus", "是否过账", null));
			dataResult.getFcsFields().add(new FcsField("branchPayStatus", "是否已确认付款[分支机构]", null));
			dataResult.getFcsFields().add(new FcsField("voucherNo", "凭证号", null));
			
			List<DataListItem> dataListItems = Lists.newArrayList();
			
			for (FormExpenseApplicationDetailAllInfo info : infos) {
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
				reportItem.setKey("reimburseReason");
				reportItem.setValue(info.getReimburseReason());
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
				reportItem.setKey("expenseType");
				reportItem.setValue(info.getExpenseType());
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("amount");
				reportItem.setValue(info.getAmount().toStandardString());
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("status");
				reportItem.setValue(info.getFormStatus().message());
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("accountStatus");
				if (info.getAccountstatus() != null) {
					reportItem.setValue(info.getAccountstatus().message());
				} else {
					reportItem.setValue("-");
				}
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("waitPayTime");
				reportItem.setValue(DateUtil.dtSimpleFormat(info.getWaitPayTime()));
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
	
	@RequestMapping("add.htm")
	public String add(HttpServletRequest request, HttpServletResponse response, Model model) {
		buildSystemNameDefaultUrl(request, model);
		String travelId = request.getParameter("id");
		if (StringUtil.isNotBlank(travelId)) {
			FormExpenseApplicationInfo info = expenseApplicationServiceClient.queryById(Long
				.valueOf(travelId));
			model.addAttribute("info", info);
			
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
		
		model.addAttribute("categoryList", getCostCategoryList(null, true));
		model.addAttribute("now", new Date());
		return vm_path + "addForm.vm";
	}
	
	@ResponseBody
	@RequestMapping("save.json")
	public JSONObject save(HttpServletRequest request, Model model) {
		ExpenseApplicationOrder order = new ExpenseApplicationOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		String onlyChange = request.getParameter("onlyChangeDetailList");
		if (StringUtil.isNotBlank(onlyChange)) {
			order.setOnlyChangeDetailList(BooleanEnum.getByCode(onlyChange));
		}
		if (StringUtil.isNotBlank(request.getParameter("officialCard"))) {
			order.setIsOfficialCard(BooleanEnum.getByCode(request.getParameter("officialCard")));
		} else {
			order.setIsOfficialCard(BooleanEnum.NO);
		}
		if (StringUtil.isNotBlank(request.getParameter("direction"))) {
			order.setDirection(CostDirectionEnum.getByCode(request.getParameter("direction")));
		} else {
			order.setDirection(CostDirectionEnum.PUBLIC);
		}
		
		if (order.getDirection() == CostDirectionEnum.PRIVATE) {
			order.setBank(request.getParameter("bank1"));
			order.setBankAccount(request.getParameter("bankAccount1"));
			order.setPayee(request.getParameter("payee1"));
		}
		
		order.setCheckIndex(0);
		order.setCheckStatus(1);
		setSessionLocalInfo2Order(order);
		try {
			if (request.getParameterValues("expenseType") == null) {
				FormBaseResult result = new FormBaseResult();
				result.setMessage("费用类型不能为空！");
				return toJSONResult(result, "保存");
			}
			// 获取冲销id 
			String[] clid = request.getParameterValues("clid");
			String cxids = "";
			List<String> reverseIds = new ArrayList<String>();
			if (clid != null) {
				for (String str : clid) {
					reverseIds.add(str);
					if (StringUtil.isNotBlank(cxids)) {
						cxids += ",";
					}
					cxids += str;
				}
			}
			if (BooleanEnum.YES.code().equals(request.getParameter("isReverse"))) {
				order.setCxids(cxids);
				order.setReverseIds(reverseIds);
				//				if (StringUtil.isBlank(cxids)) {
				//					order.setIsReverse(BooleanEnum.NO.code());
				//				}
			} else {
				order.setIsReverse(BooleanEnum.NO.code());
				order.setCxids("");
				order.setReverseIds(new ArrayList<String>());
			}
			setDetailList(order, request);
		} catch (Exception e) {
			FormBaseResult result = new FormBaseResult();
			result.setMessage(e.getMessage());
			return toJSONResult(result, "保存");
		}
		
		FormBaseResult result = expenseApplicationServiceClient.save(order);
		if (result != null && result.isSuccess()) {
			addAttachfile(result.getMessage(), request, null, "费用支付申请单附件",
				CommonAttachmentTypeEnum.FORM_ATTACH);
		}
		JSONObject json = toJSONResult(result, "保存");
		Object obj = null;
		if (result != null)
			obj = result.getReturnObject();
		
		if (obj != null) {
			FormExpenseApplicationInfo info = (FormExpenseApplicationInfo) obj;
			if (info != null) {
				json.put("id", info.getExpenseApplicationId());
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
			FcsBaseResult result = expenseApplicationServiceClient.confirmBranchPay(order);
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
		FormInfo form = formServiceFmClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		model.addAttribute("form", form);
		
		FormExpenseApplicationInfo info = expenseApplicationServiceClient.queryByFormId(formId);
		setCxinfo(model, info, false);
		model.addAttribute("info", info);
		model.addAttribute("isview", true);
		boolean isFYFG = DataPermissionUtil.isFinancialYFG();
		model.addAttribute("isFYFG", isFYFG);
		queryCommonAttachmentData(model, String.valueOf(info.getBillNo()),
			CommonAttachmentTypeEnum.FORM_ATTACH);
		
		model.addAttribute("categoryList", getCostCategoryList(null, false));
		return vm_path + "viewAudit.vm";
	}
	
	@RequestMapping("edit.htm")
	public String edit(long formId, HttpServletRequest request, HttpSession session, Model model) {
		buildSystemNameDefaultUrl(request, model);
		FormInfo form = formServiceFmClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		model.addAttribute("form", form);
		
		FormExpenseApplicationInfo expenseInfo = expenseApplicationServiceClient
			.queryByFormId(formId);
		model.addAttribute("info", expenseInfo);
		
		queryCommonAttachmentData(model, String.valueOf(expenseInfo.getBillNo()),
			CommonAttachmentTypeEnum.FORM_ATTACH);
		
		model.addAttribute("categoryList", getCostCategoryList(form, true));
		
		//		// 抓取 还款冲销信息和预付款冲销信息
		//		long jkId = 0;
		//		long yfkId = 0;
		//		long hkId = 0;
		//		long tyfkId = 0;
		//		CostCategoryQueryOrder costOrder = new CostCategoryQueryOrder();
		//		costOrder.setStatusList(new ArrayList<CostCategoryStatusEnum>());
		//		costOrder.getStatusList().add(CostCategoryStatusEnum.NORMAL);
		//		costOrder.setPageSize(10000);
		//		QueryBaseBatchResult<CostCategoryInfo> batchResult = costCategoryServiceClient
		//			.queryPage(costOrder);
		//		for (CostCategoryInfo cInfo : batchResult.getPageList()) {
		//			if ("借款".equals(cInfo.getName())) {
		//				jkId = cInfo.getCategoryId();
		//			} else if ("预付款".equals(cInfo.getName())) {
		//				yfkId = cInfo.getCategoryId();
		//			}
		//		}
		//		
		//		// 借款
		//		Money jkAllAmount = Money.zero();
		//		if (jkId > 0) {
		//			ExpenseApplicationQueryOrder queryOrder = new ExpenseApplicationQueryOrder();
		//			queryOrder.setExpenseType(String.valueOf(jkId));
		//			queryOrder.setPayee(expenseInfo.getPayee());
		//			QueryBaseBatchResult<FormExpenseApplicationDetailAllInfo> queryResult = expenseApplicationServiceClient
		//				.queryPageAll(queryOrder);
		//			if (queryResult != null && ListUtil.isNotEmpty(queryResult.getPageList())) {
		//				model.addAttribute("jkList", queryResult.getPageList());
		//				for (FormExpenseApplicationDetailAllInfo info : queryResult.getPageList()) {
		//					jkAllAmount.addTo(info.getAmount());
		//				}
		//			}
		//			// 还款
		//			queryOrder.setExpenseType(expenseType)
		//			expenseApplicationServiceClient.queryPage(queryOrder);
		//			
		//		}
		//		
		//		
		//		
		//		// 预付款
		//		Money yfkAllAmount = Money.zero();
		//		if (yfkId > 0) {
		//			ExpenseApplicationQueryOrder queryOrder = new ExpenseApplicationQueryOrder();
		//			queryOrder.setExpenseType(String.valueOf(yfkId));
		//			QueryBaseBatchResult<FormExpenseApplicationDetailAllInfo> queryResult = expenseApplicationServiceClient
		//				.queryPageAll(queryOrder);
		//			if (queryResult != null && ListUtil.isNotEmpty(queryResult.getPageList())) {
		//				model.addAttribute("jkList", queryResult.getPageList());
		//				for (FormExpenseApplicationDetailAllInfo info : queryResult.getPageList()) {
		//					yfkAllAmount.addTo(info.getAmount());
		//				}
		//			}
		//		}
		getcxDetailInfo(expenseInfo, model, false);
		return vm_path + "addForm.vm";
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
		
		return vm_path + "viewAudit.vm";
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
		//		model.addAttribute("categoryList", getCostCategoryList(null, true));
		return vm_path + "viewAudit.vm";
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
		
		return vm_path + "viewAudit.vm";
	}
	
	/**
	 * 审核[分公司的识别]
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("audit/canFenGongSi.htm")
	public String canFenGongSi(long formId, HttpServletRequest request, Model model) {
		doAudit(formId, request, model);
		
		return vm_path + "viewAudit.vm";
	}
	
	private void doAudit(long formId, HttpServletRequest request, Model model) {
		doAudit(formId, request, model, false);
	}
	
	private void doAudit(long formId, HttpServletRequest request, Model model,
							boolean getBankMessage) {
		model.addAttribute("_SYSNAME", "FM");
		FormInfo formInfo = formServiceFmClient.findByFormId(formId);
		if (formInfo == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "申请单不存在");
		}
		model.addAttribute("form", formInfo);
		
		FormExpenseApplicationInfo info = expenseApplicationServiceClient.queryByFormId(formId);
		setCxinfo(model, info, true);
		model.addAttribute("info", info);
		// 获取默认支付银行信息
		model.addAttribute("defaultPayBank",
			findDefaultPaymentBankInfo(null == info ? 0L : info.getExpenseDeptId()));
		model.addAttribute("categoryList", getCostCategoryList(formInfo, false));
		getcxDetailInfo(info, model, true);
		
		queryCommonAttachmentData(model, String.valueOf(info.getBillNo()),
			CommonAttachmentTypeEnum.FORM_ATTACH);
		initWorkflow(model, formInfo, request.getParameter("taskId"));
		
		//		boolean isFYFG = DataPermissionUtil.isFinancialYFG();
		boolean isFYFG = false;
		if (getBankMessage) {
			isFYFG = true;
			model.addAttribute("isFYFG", isFYFG);
		}
		if (isFYFG
			&& getBankMessage
			&& (StringUtil.isBlank(info.getPayBankAccount()) && StringUtil.isBlank(info
				.getPayBank()))) {
			// 先判定是否有值,有值就不抓取默认值了
			if (StringUtil.isBlank(info.getPayBankAccount())
				&& StringUtil.isBlank(info.getPayBank())) {
				getPayBank(info);
			}
		}
	}
	
	/**
	 * 获取设置还款、冲预付费财务审核信息
	 * @param model
	 * @param info
	 */
	private void setCxinfo(Model model, FormExpenseApplicationInfo info, boolean isAdd) {
		String expenseType = info.getDetailList().get(0).getExpenseType();
		CostCategoryInfo costInfo = costCategoryServiceClient.queryById(Long.valueOf(expenseType));
		if ("还款".equals(costInfo.getName())) {
			model.addAttribute("hk", true);
		}
		List<ExpenseCxDetailInfo> cxdetailList = info.getCxdetailList();
		if (cxdetailList != null) {
			List<ExpenseCxDetailInfo> cxdetailList1 = new ArrayList<ExpenseCxDetailInfo>();
			List<ExpenseCxDetailInfo> cxdetailList2 = new ArrayList<ExpenseCxDetailInfo>();
			Money cxMoney1 = Money.zero();//预付款总金额
			Money cxMoney2 = Money.zero();//费用金额
			Money xjMoney = Money.zero();//现金
			Money fpMoney = Money.zero();//发票
			Money yfkMoney = Money.zero();//财务-预付款
			Money yhMoney = Money.zero();//财务-银行存款
			for (ExpenseCxDetailInfo cxinfo : cxdetailList) {
				if (cxinfo.getFromApplicationId() > 0) {
					cxdetailList1.add(cxinfo);
					//					cxMoney1 = cxMoney1.add(cxinfo.getFpAmount().add(cxinfo.getXjAmount()));
					cxMoney1 = cxMoney1.add(cxinfo.getBxAmount());
					cxMoney2 = cxMoney2.add(cxinfo.getFpAmount().add(cxinfo.getXjAmount()));
					
					if (info.getFormCode() == FormCodeEnum.EXPENSE_APPLICATION_CPREPAY) {//费用支付(冲预付款类)审批
					
						if (cxinfo.getFyAmount().compareTo(cxinfo.getFpAmount()) == 0
							&& cxinfo.getXjAmount().compareTo(Money.zero()) == 0) {//预付款 = 发票冲销金额 
							yfkMoney = yfkMoney.add(cxinfo.getFpAmount());//财务-预付款  = 发票冲销金额
							
						} else if (cxinfo.getFyAmount().compareTo(cxinfo.getXjAmount()) == 0
									&& cxinfo.getFpAmount().compareTo(Money.zero()) == 0) {//预付款 = 现金冲销金额 
							yfkMoney = yfkMoney.add(cxinfo.getXjAmount());//财务-预付款  = 现金冲销金额
							yhMoney = yhMoney.add(cxinfo.getXjAmount());//财务-银行存款 = 现金冲销金额
							
						} else if (cxinfo.getFyAmount().compareTo(
							cxinfo.getFpAmount().add(cxinfo.getXjAmount())) == 0) {//预付款 = 发票冲销金额 + 现金冲销金额
							yfkMoney = yfkMoney.add(cxinfo.getFpAmount().add(cxinfo.getXjAmount()));//财务-预付款  = 发票冲销金额 + 现金冲销金额
							yhMoney = yhMoney.add(cxinfo.getXjAmount());//财务-银行存款 = 现金冲销金额
							
						} else if (cxinfo.getFyAmount().compareTo(cxinfo.getFpAmount()) < 0) {//预付款 < 发票冲销金额 
							yfkMoney = yfkMoney.add(cxinfo.getFyAmount());//财务-预付款  = 预付款
							yhMoney = yhMoney.add(cxinfo.getFpAmount().subtract(
								cxinfo.getFyAmount()));//财务-银行存款 = 发票冲销金额  - 预付款
						} else {
							yfkMoney = yfkMoney.add(cxinfo.getFpAmount().add(cxinfo.getXjAmount()));//财务-预付款  = 发票冲销金额 + 现金冲销金额
							yhMoney = yhMoney.add(cxinfo.getXjAmount());//财务-银行存款 = 现金冲销金额
						}
					} else {
						if (cxinfo.getXjAmount().compareTo(Money.zero()) > 0) {
							xjMoney = xjMoney.add(cxinfo.getXjAmount());
						}
						fpMoney = fpMoney.add(cxinfo.getFpAmount());
					}
					
				} else {
					if (StringUtil.isNotBlank(cxinfo.getAccountCode())) {
						model.addAttribute("hk", true);
					}
					cxdetailList2.add(cxinfo);
				}
			}
			if (!cxdetailList1.isEmpty()) {
				model.addAttribute("cxMoney1", cxMoney1);
				model.addAttribute("cxinfo1", cxdetailList1);
			}
			if (!cxdetailList2.isEmpty()) {
				model.addAttribute("cxinfo2", cxdetailList2);
			} else {
				if (!isAdd)
					return;
				
				if (info.getFormCode() == FormCodeEnum.EXPENSE_APPLICATION_CPREPAY) {//费用支付(冲预付款类)审批
					if (yhMoney.compareTo(Money.zero()) > 0) {
						CostCategoryInfo cost = getCostCategory("银行存款");
						if (cost != null) {
							ExpenseCxDetailInfo yhckInfo = new ExpenseCxDetailInfo();
							yhckInfo.setCategory(cost.getName());
							yhckInfo.setCategoryId(cost.getCategoryId());
							yhckInfo.setAccountCode(cost.getAccountCode());
							yhckInfo.setAccountName(cost.getAccountName());
							yhckInfo.setFyAmount(yhMoney);
							cxdetailList2.add(yhckInfo);
						}
						model.addAttribute("hk", true);
					}
					
					if (yfkMoney.compareTo(Money.zero()) > 0) {
						CostCategoryInfo cost = getCostCategory("预付款");
						if (cost != null) {
							ExpenseCxDetailInfo yfckInfo = new ExpenseCxDetailInfo();
							yfckInfo.setCategory(cost.getName());
							yfckInfo.setCategoryId(cost.getCategoryId());
							yfckInfo.setAccountCode(cost.getAccountCode());
							yfckInfo.setAccountName(cost.getAccountName());
							yfckInfo.setFyAmount(yfkMoney);
							//							yfckInfo.setFyAmount(fpMoney);
							cxdetailList2.add(yfckInfo);
						}
					}
				} else {
					if (xjMoney.compareTo(Money.zero()) > 0) {
						CostCategoryInfo cost = getCostCategory("银行存款");
						if (cost != null) {
							ExpenseCxDetailInfo yhckInfo = new ExpenseCxDetailInfo();
							yhckInfo.setCategory(cost.getName());
							yhckInfo.setCategoryId(cost.getCategoryId());
							yhckInfo.setAccountCode(cost.getAccountCode());
							yhckInfo.setAccountName(cost.getAccountName());
							yhckInfo.setFyAmount(xjMoney);
							cxdetailList2.add(yhckInfo);
						}
						model.addAttribute("hk", true);
					}
					
					if (fpMoney.compareTo(Money.zero()) > 0) {
						CostCategoryInfo cost = getCostCategory("预付款");
						if (cost != null) {
							ExpenseCxDetailInfo yfckInfo = new ExpenseCxDetailInfo();
							yfckInfo.setCategory(cost.getName());
							yfckInfo.setCategoryId(cost.getCategoryId());
							yfckInfo.setAccountCode(cost.getAccountCode());
							yfckInfo.setAccountName(cost.getAccountName());
							yfckInfo.setFyAmount(cxMoney2);
							//							yfckInfo.setFyAmount(fpMoney);
							cxdetailList2.add(yfckInfo);
						}
					}
				}
				
				model.addAttribute("cxinfo2", cxdetailList2);
			}
		} else {
			if (!isAdd)
				return;
			
			List<ExpenseCxDetailInfo> cxdetailListadd = new ArrayList<ExpenseCxDetailInfo>();
			
			if ("还款".equals(costInfo.getName())) {
				model.addAttribute("cxinfo2", cxdetailListadd);
				CostCategoryInfo cost = getCostCategory("银行存款");
				if (cost != null) {
					ExpenseCxDetailInfo yhckInfo = new ExpenseCxDetailInfo();
					yhckInfo.setCategory(cost.getName());
					yhckInfo.setCategoryId(cost.getCategoryId());
					yhckInfo.setAccountCode(cost.getAccountCode());
					yhckInfo.setAccountName(cost.getAccountName());
					yhckInfo.setFyAmount(Money.zero());
					cxdetailListadd.add(yhckInfo);
				}
				model.addAttribute("hk", true);
			}
			if ("冲预付款".equals(costInfo.getName())) {
				model.addAttribute("cxinfo2", cxdetailListadd);
				CostCategoryInfo cost = getCostCategory("预付款");
				if (cost != null) {
					ExpenseCxDetailInfo yfckInfo = new ExpenseCxDetailInfo();
					yfckInfo.setCategory(cost.getName());
					yfckInfo.setCategoryId(cost.getCategoryId());
					yfckInfo.setAccountCode(cost.getAccountCode());
					yfckInfo.setAccountName(cost.getAccountName());
					yfckInfo.setFyAmount(Money.zero());
					cxdetailListadd.add(yfckInfo);
				}
			}
			
		}
	}
	
	private void setDetailList(ExpenseApplicationOrder order, HttpServletRequest request) {
		String[] expenseTypes = request.getParameterValues("expenseType");
		order.setAmount(Money.zero());
		if (expenseTypes != null && expenseTypes.length > 0) {
			String[] detailIds = request.getParameterValues("detailId");
			String[] amounts = request.getParameterValues("damount");
			String[] taxAmounts = request.getParameterValues("taxAmount");
			String[] orgNames = request.getParameterValues("orgName");
			String[] orgIds = request.getParameterValues("orgId");
			
			ArrayList<ExpenseApplicationDetailOrder> detailOrderList = new ArrayList<ExpenseApplicationDetailOrder>(
				expenseTypes.length);
			for (int i = 0; i < expenseTypes.length; i++) {
				ExpenseApplicationDetailOrder detailOrder = new ExpenseApplicationDetailOrder();
				if (StringUtil.isNotBlank(detailIds[i]))
					detailOrder.setDetailId(Long.valueOf(detailIds[i]));
				if (StringUtil.isNotBlank(amounts[i]))
					detailOrder.setAmount(new Money(amounts[i]));
				if (StringUtil.isNotBlank(taxAmounts[i]))
					detailOrder.setTaxAmount(new Money(taxAmounts[i]));
				if (StringUtil.isNotBlank(orgIds[i]))
					detailOrder.setDeptId(Long.valueOf(orgIds[i]));
				detailOrder.setDeptName(orgNames[i]);
				detailOrder.setExpenseType(expenseTypes[i]);
				detailOrderList.add(detailOrder);
				
				order.setAmount(order.getAmount().add(detailOrder.getAmount()));
			}
			order.setDetailList(detailOrderList);
		}
		
		String[] cx_expenseId = request.getParameterValues("cx_expenseId");
		if (cx_expenseId != null && cx_expenseId.length > 0) {
			String[] cx_fpamount = request.getParameterValues("cx_fpamount");
			String[] cx_xjamount = request.getParameterValues("cx_xjamount");
			String[] cx_detailId = request.getParameterValues("cx_detailId");
			
			List<ExpenseCxDetailOrder> cxDetailList = new ArrayList<ExpenseCxDetailOrder>(
				cx_expenseId.length);
			for (int i = 0; i < cx_expenseId.length; i++) {
				ExpenseCxDetailOrder cxDetailOrder = new ExpenseCxDetailOrder();
				if (StringUtil.isNotBlank(cx_fpamount[i]))
					cxDetailOrder.setFpAmount(new Money(cx_fpamount[i]));
				if (StringUtil.isNotBlank(cx_xjamount[i]))
					cxDetailOrder.setXjAmount(new Money(cx_xjamount[i]));
				if (StringUtil.isNotBlank(cx_detailId[i]))
					cxDetailOrder.setFromDetailId(Long.valueOf(cx_detailId[i]));
				if (StringUtil.isNotBlank(cx_expenseId[i]))
					cxDetailOrder.setFromApplicationId(Long.valueOf(cx_expenseId[i]));
				cxDetailList.add(cxDetailOrder);
			}
			order.setCxDetailList(cxDetailList);
		}
		setFormCode(order);
	}
	
	/**
	 * 根据费用类型设置审核流程
	 * @param order
	 */
	private void setFormCode(ExpenseApplicationOrder order) {
		FormCodeEnum curFormCodeEnum = null;
		// 20170327劳资和税金的信惠判定。因为不会出现退预付款所以先行判定
		//  20170315添加判断 劳资类 添加信惠申请人判断
		
		final List<String> lzxhList = new ArrayList<String>();
		lzxhList.addAll(lzList);
		lzxhList.addAll(sjList);
		boolean isXJLZ = false;
		if (order.getFormId() > 0) {
			
			FormExpenseApplicationInfo oldInfo = expenseApplicationServiceClient
				.queryByFormId(order.getFormId());
			if (FormCodeEnum.EXPENSE_APPLICATION_LZ_XH == oldInfo.getFormCode()) {
				isXJLZ = true;
			}
		}
		// 判定是信惠发起
		Org userOrg = ShiroSessionUtils.getSessionLocal().getUserDetailInfo().getPrimaryOrg();
		if (StringUtil.equals(userOrg.getCode(), ReportCompanyEnum.XINHUI.getDeptCode()) || isXJLZ) {
			//  判定是否无金额类别的超出
			boolean isXHSJ = false;
			boolean isWJE = false;
			// 判定全部属于劳资及税金
			boolean allLzxh = true;
			for (ExpenseApplicationDetailOrder detailOrder : order.getDetailList()) {
				CostCategoryInfo costInfo = costCategoryServiceClient.queryById(Long
					.valueOf(detailOrder.getExpenseType()));
				if (sjList.contains(costInfo.getName())) {
					isXHSJ = true;
					continue;
				} else if (lzList.contains(costInfo.getName())) {
					continue;
				} else if (xhwjeList.contains(costInfo.getName())) {
					isWJE = true;
					allLzxh = false;
					continue;
				} else {
					allLzxh = false;
				}
				//				if (!lzxhList.contains(costInfo.getName())) {
				//					allLzxh = false;
				//					break;
				//				}
			}
			////// 20170615 接漆老师电话需求：非劳资信惠且信惠发起都应该走入信惠流程。
			//			if (isXHSJ && isWJE) {
			//				// 添加判定 无金额规则类和信惠类税金不能同时存在xhwjeList
			//				throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM,
			//					"费用类型存在多个不同审核流程");
			//			}
			if (allLzxh) {
				curFormCodeEnum = FormCodeEnum.EXPENSE_APPLICATION_LZ_XH;
			} else {
				curFormCodeEnum = FormCodeEnum.EXPENSE_APPLICATION_JE_XH;
			}
			
		}
		if (curFormCodeEnum == null) {
			
			boolean expenseApplicationCprepayGet = false;
			for (ExpenseApplicationDetailOrder detailOrder : order.getDetailList()) {
				CostCategoryInfo costInfo = costCategoryServiceClient.queryById(Long
					.valueOf(detailOrder.getExpenseType()));
				boolean isExit = true;
				for (String key : fmap.keySet()) {
					if (key.contains("#" + costInfo.getName() + "#")) {
						FormCodeEnum keyFormCode = fmap.get(key);
						if (FormCodeEnum.EXPENSE_APPLICATION_CPREPAY == keyFormCode) {
							expenseApplicationCprepayGet = true;
							isExit = false;
							continue;
						}
						// 20161118添加判断 退预付款单独进入自己退预付款流程，退预付款和其他流程一起的时候进入其他流程
						
						if (curFormCodeEnum != null && curFormCodeEnum != keyFormCode) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM,
								"费用类型存在多个不同审核流程");
						}
						curFormCodeEnum = keyFormCode;
						isExit = false;
					}
				}
				
				if (isExit) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM,
						"费用类型【" + costInfo.getName() + "】无对应审核流程");
				}
				
				// 20170406 添加判断 无金额规则类的税金和其他不能在一起
				boolean isXHSJ = false;
				boolean isWJE = false;
				if (sjList.contains(costInfo.getName())) {
					isXHSJ = true;
					continue;
				} else if (xhwjeList.contains(costInfo.getName())) {
					isWJE = true;
					continue;
				}
				if (isXHSJ && isWJE) {
					// 添加判定 无金额规则类和信惠类税金不能同时存在xhwjeList
					throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM,
						"不同单据编号的费用类型不能同时申请");
				}
			}
			
			if (curFormCodeEnum == null) {
				
				// 20161118添加判断 退预付款单独进入自己退预付款流程，退预付款和其他流程一起的时候进入其他流程
				if (expenseApplicationCprepayGet) {
					curFormCodeEnum = FormCodeEnum.EXPENSE_APPLICATION_CPREPAY;
				} else {
					curFormCodeEnum = FormCodeEnum.EXPENSE_APPLICATION_LIMIT;
				}
			}
			
			// 20161221 添加判断 金额规则类 添加信惠申请人判断
			if (FormCodeEnum.EXPENSE_APPLICATION_LIMIT == curFormCodeEnum) {
				//				Org userOrg = ShiroSessionUtils.getSessionLocal().getUserDetailInfo().getPrimaryOrg();
				if (StringUtil.equals(userOrg.getCode(), ReportCompanyEnum.XINHUI.getDeptCode())) {
					curFormCodeEnum = FormCodeEnum.EXPENSE_APPLICATION_JE_XH;
				}
				//			if (projectRelatedUserServiceClient.isBelong2Dept(ShiroSessionUtils.getSessionLocal()
				//				.getUserId(), sysParameterServiceClient
				//				.getSysParameterValue(SysParamEnum.SYS_PARAM_XINHUI_DEPT_CODE.code()))) {
				//				curFormCodeEnum = FormCodeEnum.EXPENSE_APPLICATION_JE_XH;
				//				
				//			}
				// 2017-0328 金额规则类别，若是分公司。进入EXPENSE_APPLICATION_FZJG分支机构流程
				List<ExpenseApplicationDetailOrder> detailList = order.getDetailList();
				boolean allFGX = true;
				for (ExpenseApplicationDetailOrder detailOrder : detailList) {
					if (!checkIsFenGongSi(detailOrder.getDeptName())) {
						allFGX = false;
						break;
					}
				}
				if (allFGX) {
					curFormCodeEnum = FormCodeEnum.EXPENSE_APPLICATION_FZJG;
				}
			}
		}
		// 20170329 分支机构类还原为金额类,暂时不上线
		if (FormCodeEnum.EXPENSE_APPLICATION_FZJG == curFormCodeEnum) {
			curFormCodeEnum = FormCodeEnum.EXPENSE_APPLICATION_LIMIT;
		}
		order.setFormCode(curFormCodeEnum);
	}
	
	private List<CostCategoryInfo> getCostCategoryList(FormInfo formInfo, boolean isCheckXCZY) {
		
		List<CostCategoryInfo> retlist = new ArrayList<CostCategoryInfo>();
		FormCodeEnum limtFormCodeEnum = null;
		FormExpenseApplicationInfo expenseInfo = null;
		if (formInfo != null) {
			expenseInfo = expenseApplicationServiceClient.queryByFormId(formInfo.getFormId());
			//formServiceFmClient.findByFormId(formInfo.getFormId());
			
			//			FormRelatedUserQueryOrder forder = new FormRelatedUserQueryOrder();
			//			forder.setFormId(formInfo.getFormId());
			//			long count = formRelatedUserServiceClient.queryCount(forder);
			//			if (count > 0) {//已提交，只能选择相同流程费用类型
			//				limtFormCodeEnum = formInfo.getFormCode();
			//			}
			limtFormCodeEnum = formInfo.getFormCode();
			// 20161221 信惠类型是金额规则类别   若信惠流程支持多种，需要先抓取一条子记录，再获取formcode
			// ////// 20170615 接漆老师电话需求：非劳资信惠且信惠发起都应该走入信惠流程。 此处不予回转为金额类，且下方返回的类型应为取消税金和劳资的全部
			if (FormCodeEnum.EXPENSE_APPLICATION_JE_XH == limtFormCodeEnum) {
				//				limtFormCodeEnum = FormCodeEnum.EXPENSE_APPLICATION_LIMIT;
			}
			// 20170316 信惠类型是劳资类别   若信惠流程支持多种，需要先抓取一条子记录，再获取formcode
			if (FormCodeEnum.EXPENSE_APPLICATION_LZ_XH == limtFormCodeEnum) {
				// 判定
				// 20170327 要添加税金和劳资两类
				//				limtFormCodeEnum = FormCodeEnum.EXPENSE_APPLICATION_LZ;
			}
			// 20170328 分支机构类还原为金额类
			if (FormCodeEnum.EXPENSE_APPLICATION_FZJG == limtFormCodeEnum) {
				limtFormCodeEnum = FormCodeEnum.EXPENSE_APPLICATION_LIMIT;
			}
			
		}
		boolean isXCZY = true;//薪酬专员
		if (isCheckXCZY) {
			isXCZY = DataPermissionUtil.hasRole("xczy");//薪酬专员
		}
		CostCategoryQueryOrder order = new CostCategoryQueryOrder();
		order.setStatusList(new ArrayList<CostCategoryStatusEnum>());
		order.getStatusList().add(CostCategoryStatusEnum.NORMAL);
		order.setPageSize(10000);
		QueryBaseBatchResult<CostCategoryInfo> batchResult = costCategoryServiceClient
			.queryPage(order);
		for (CostCategoryInfo info : batchResult.getPageList()) {
			// 20170515 剔除停用的数据
			if (BooleanEnum.IS != info.getUsed()) {
				continue;
			}
			
			if (info.getName().equals("差旅费"))
				continue;
			
			// 20161228 不展示的类别剔除
			if (notShowList.contains(info.getName())) {
				continue;
			}
			
			// 20161102 所有人都可以申请
			//			if (!isXCZY) {
			//				if ("工资、养老保险、失业保险、医疗保险、工伤保险、生育保险、住房公积金".contains(info.getName()))
			//					continue;
			//			}
			if (FormCodeEnum.EXPENSE_APPLICATION_LZ_XH == limtFormCodeEnum) {
				// 20170327 劳资信惠类别要添加劳资和税金两类
				if (lzList.contains(info.getName()) || sjList.contains(info.getName())) {
					retlist.add(info);
					continue;
				}
			}
			// 20170406 金额规则类要判定是bx还是zf
			else if (FormCodeEnum.EXPENSE_APPLICATION_NLIMIT == limtFormCodeEnum) {
				if (expenseInfo != null) {
					if (expenseInfo.getBillNo().startsWith("ZF")) {
						// zf 添加税金类
						if (sjList.contains(info.getName())) {
							retlist.add(info);
							continue;
						}
					} else if (expenseInfo.getBillNo().startsWith("BX")) {
						// BX添加无金额类
						if (xhwjeList.contains(info.getName())) {
							retlist.add(info);
							continue;
						}
					}
				}
			}
			///// 20170615 接漆老师电话需求：非劳资信惠且信惠发起都应该走入信惠流程。 此处不予回转为金额类，且下方返回的类型应为取消税金和劳资的全部
			else if (FormCodeEnum.EXPENSE_APPLICATION_JE_XH == limtFormCodeEnum) {
				if (sjList.contains(info.getName())) {
					continue;
				}
				if (lzList.contains(info.getName())) {
					continue;
				}
				retlist.add(info);
				continue;
			}
			if (limtFormCodeEnum != null) {
				boolean isAdd = false;
				for (String key : fmap.keySet()) {
					if (key.contains("#" + info.getName() + "#")) {
						FormCodeEnum keyFormCode = fmap.get(key);
						if (limtFormCodeEnum == keyFormCode) {//已提交，只能选择相同流程费用类型
							isAdd = true;
						}
						/// 20161118 添加退预付款在页面上，由页面判定是否展示
						else if (FormCodeEnum.EXPENSE_APPLICATION_CPREPAY == keyFormCode) {
							isAdd = true;
						}
						
						break;
					}
				}
				if (!isAdd)
					continue;
			}
			
			retlist.add(info);
		}
		
		return retlist;
	}
	
	private CostCategoryInfo getCostCategory(String costName) {
		CostCategoryQueryOrder order = new CostCategoryQueryOrder();
		order.setStatusList(new ArrayList<CostCategoryStatusEnum>());
		order.getStatusList().add(CostCategoryStatusEnum.NORMAL);
		order.setPageSize(10000);
		QueryBaseBatchResult<CostCategoryInfo> batchResult = costCategoryServiceClient
			.queryPage(order);
		for (CostCategoryInfo info : batchResult.getPageList()) {
			if (info.getName().equals(costName)) {
				return info;
			}
		}
		
		return null;
	}
	
	private void getcxDetailInfo(FormExpenseApplicationInfo expenseInfo, Model model, boolean audit) {
		
		String expenseApplicationId = String.valueOf(expenseInfo.getExpenseApplicationId());
		String payee = expenseInfo.getPayee();
		String direction = "";
		if (expenseInfo.getDirection() != null) {
			direction = expenseInfo.getDirection().code();
		}
		String cxids = expenseInfo.getCxids();
		String agent = String.valueOf(expenseInfo.getAgentId());
		List<String> idsList = new ArrayList<String>();
		if (StringUtil.isNotBlank(cxids)) {
			String[] strs = cxids.split(",");
			for (String s : strs) {
				idsList.add(s);
			}
		}
		long jkId = 0;
		long yfkId = 0;
		long hkId = 0;
		long tyfkId = 0;
		CostCategoryQueryOrder costOrder = new CostCategoryQueryOrder();
		costOrder.setStatusList(new ArrayList<CostCategoryStatusEnum>());
		costOrder.getStatusList().add(CostCategoryStatusEnum.NORMAL);
		costOrder.setPageSize(10000);
		QueryBaseBatchResult<CostCategoryInfo> batchResult = costCategoryServiceClient
			.queryPage(costOrder);
		for (CostCategoryInfo cInfo : batchResult.getPageList()) {
			if ("借款".equals(cInfo.getName())) {
				jkId = cInfo.getCategoryId();
			} else if ("预付款".equals(cInfo.getName())) {
				yfkId = cInfo.getCategoryId();
			} else if ("还款".equals(cInfo.getName())) {
				hkId = cInfo.getCategoryId();
			} else if ("退预付款".equals(cInfo.getName())) {
				tyfkId = cInfo.getCategoryId();
			}
		}
		JSONArray data = new JSONArray();
		
		// 对公抓去预付款信息 对私抓取借款信息
		if (CostDirectionEnum.PUBLIC.code().equals(direction)) {
			// 预付款
			Money yfkAllAmount = Money.zero();
			Money tyfkAllAmount = Money.zero();
			Money waitReverseAmount = Money.zero();
			Money waitAuditReverseAmount = Money.zero();
			if (yfkId > 0) {
				ExpenseApplicationQueryOrder queryOrder = new ExpenseApplicationQueryOrder();
				queryOrder.setPageSize(10000);
				queryOrder.setExpenseType(String.valueOf(yfkId));
				if (!audit) {
					queryOrder.setPayee(payee);
				}
				//				queryOrder
				//					.setAgent(String.valueOf(ShiroSessionUtils.getSessionLocal().getUserId()));
				queryOrder.setAgent(agent);
				// 查询审核通过的
				List<FormStatusEnum> formStatuss = new ArrayList<FormStatusEnum>();
				formStatuss.add(FormStatusEnum.APPROVAL);
				queryOrder.setFormStatusList(formStatuss);
				QueryBaseBatchResult<FormExpenseApplicationDetailAllInfo> queryResult = expenseApplicationServiceClient
					.queryPageAll(queryOrder);
				List<FormExpenseApplicationDetailAllInfo> waitReverseList = new ArrayList<FormExpenseApplicationDetailAllInfo>();
				List<FormExpenseApplicationDetailAllInfo> waitAuditReverseList = new ArrayList<FormExpenseApplicationDetailAllInfo>();
				if (queryResult != null && ListUtil.isNotEmpty(queryResult.getPageList())) {
					
					for (FormExpenseApplicationDetailAllInfo info : queryResult.getPageList()) {
						// 20161119  审核的时候只需要抓取已被自己选中的记录
						// 20161122 审核时剩余未冲销金额应该锁定为已选取的记录
						if (idsList.contains(String.valueOf(info.getDetailId()))) {
							waitAuditReverseList.add(info);
							waitAuditReverseAmount.addTo(info.getAmount());
						}
						// 待冲销是全部金额，已冲销是选中且审核通过
						yfkAllAmount.addTo(info.getAmount());
						//  判断已被冲销的为已冲销金额,未冲销的为等待冲销金额
						if (BooleanEnum.YES == info.getReverse()
							&& FormStatusEnum.APPROVAL == info.getFormStatus()) {
							// 已冲销
							tyfkAllAmount.addTo(info.getAmount());
						} else {
							// 待冲销 
							waitReverseAmount.addTo(info.getAmount());
							
							// 20161118 判断未冲销的记录是否有曾经选择的记录，有就加上已选择标记
							if (idsList.contains(String.valueOf(info.getDetailId()))) {
								info.setReverse(BooleanEnum.YES);
							}
							
							waitReverseList.add(info);
						}
						for (CostCategoryInfo cost : batchResult.getPageList()) {
							if ((cost.getCategoryId() > 0 || cost.getCategoryId() == -1)
								&& String.valueOf(cost.getCategoryId()).equals(
									info.getExpenseType())) {
								info.setExpenseType(cost.getName());
								break;
							}
						}
						
					}
				}
				// 20161122 审核时剩余未冲销金额应该锁定为已选取的记录
				if (audit) {
					model.addAttribute("waitReverseAmount", waitAuditReverseAmount);
				} else {
					model.addAttribute("waitReverseAmount", waitReverseAmount);
				}
				model.addAttribute("totalAmount", yfkAllAmount);
				model.addAttribute("usedAmount", tyfkAllAmount);
				model.addAttribute("cxPublicDetailInfos", waitReverseList);
				// 20161123 审核的时候需要有列表数据才展示,同时需要已选中冲销才展示
				if (BooleanEnum.YES.code().equals(expenseInfo.getIsReverse())) {
					model.addAttribute("cxPublicDetailAuditInfos", waitAuditReverseList);
				}
				
				// 若无需冲销，置为 不冲销
				if (!audit && !waitReverseAmount.greaterThan(Money.zero())) {
					expenseInfo.setIsReverse(BooleanEnum.NO.code());
				}
			}
		} else if (CostDirectionEnum.PRIVATE.code().equals(direction)) {
			// 借款
			Money jkAllAmount = Money.zero();
			Money hkAllAmount = Money.zero();
			Money waitReverseAmount = Money.zero();
			if (jkId > 0) {
				ExpenseApplicationQueryOrder queryOrder = new ExpenseApplicationQueryOrder();
				queryOrder.setPageSize(10000);
				queryOrder.setExpenseType(String.valueOf(jkId));
				queryOrder.setPayee(payee);
				//				queryOrder
				//					.setAgent(String.valueOf(ShiroSessionUtils.getSessionLocal().getUserId()));
				queryOrder.setAgent(agent);
				// 查询审核通过的
				List<FormStatusEnum> formStatuss = new ArrayList<FormStatusEnum>();
				formStatuss.add(FormStatusEnum.APPROVAL);
				queryOrder.setFormStatusList(formStatuss);
				QueryBaseBatchResult<FormExpenseApplicationDetailAllInfo> queryResult = expenseApplicationServiceClient
					.queryPageAll(queryOrder);
				List<FormExpenseApplicationDetailAllInfo> waitAuditReverseList = new ArrayList<FormExpenseApplicationDetailAllInfo>();
				if (queryResult != null && ListUtil.isNotEmpty(queryResult.getPageList())) {
					for (FormExpenseApplicationDetailAllInfo info : queryResult.getPageList()) {
						
						jkAllAmount.addTo(info.getAmount());
						for (CostCategoryInfo cost : batchResult.getPageList()) {
							if ((cost.getCategoryId() > 0 || cost.getCategoryId() == -1)
								&& String.valueOf(cost.getCategoryId()).equals(
									info.getExpenseType())) {
								info.setExpenseType(cost.getName());
								break;
							}
						}
					}
				}
				model.addAttribute("cxPrivateDetailInfos", queryResult.getPageList());
				// 20161123 审核的时候需要有列表数据才展示,同时需要已选中冲销才展示
				if (BooleanEnum.YES.code().equals(expenseInfo.getIsReverse())) {
					model.addAttribute("cxPrivateDetailAuditInfos", queryResult.getPageList());
					//				model.addAttribute("cxPrivateDetailAuditInfos", waitAuditReverseList);
				}
				// 还款
				queryOrder.setExpenseType(String.valueOf(hkId));
				formStatuss.clear();
				formStatuss.add(FormStatusEnum.DRAFT);
				formStatuss.add(FormStatusEnum.SUBMIT);
				//				formStatuss.add(FormStatusEnum.CANCEL);
				formStatuss.add(FormStatusEnum.AUDITING);
				//				formStatuss.add(FormStatusEnum.BACK);
				formStatuss.add(FormStatusEnum.APPROVAL);
				queryOrder.setFormStatusList(formStatuss);
				queryResult = expenseApplicationServiceClient.queryPageAll(queryOrder);
				if (queryResult != null && ListUtil.isNotEmpty(queryResult.getPageList())) {
					for (FormExpenseApplicationDetailAllInfo info : queryResult.getPageList()) {
						// 剔除自己的还款数据  发现是自己的数据，不计算入已还款
						hkAllAmount.addTo(info.getAmount());
						//						if (StringUtil.equals(expenseApplicationId,
						//							String.valueOf(info.getExpenseApplicationId()))) {
						//							
						//						} else {
						//							
						//						}
					}
				}
				/// 审核中且自己，才将金额加回去，用于兼容审核页面的修改
				if (FormStatusEnum.DRAFT == expenseInfo.getFormStatus()
					|| FormStatusEnum.SUBMIT == expenseInfo.getFormStatus()
					|| FormStatusEnum.AUDITING == expenseInfo.getFormStatus()
					|| FormStatusEnum.APPROVAL == expenseInfo.getFormStatus()) {
					// 20161123 剔除自己的还款数据  发现是自己的数据，不计算入已还款 减去自己的待还款记录
					for (FormExpenseApplicationDetailInfo detailInfo : expenseInfo.getDetailList()) {
						if (StringUtil.equals(String.valueOf(hkId), detailInfo.getExpenseType())) {
							hkAllAmount.subtractFrom(detailInfo.getAmount());
						}
					}
				}
				
				waitReverseAmount = jkAllAmount.subtract(hkAllAmount);
			}
			
			model.addAttribute("waitReverseAmount", waitReverseAmount);
			model.addAttribute("totalAmount", jkAllAmount);
			model.addAttribute("usedAmount", hkAllAmount);
			
			// 若无需冲销，置为 不冲销
			if (!audit && !waitReverseAmount.greaterThan(Money.zero())) {
				expenseInfo.setIsReverse(BooleanEnum.NO.code());
			}
			
		}
	}
	
	private boolean checkIsFenGongSi(String orgName) {
		return orgName.contains("分公司") || StringUtil.equals(orgName, "四川代表处")
				|| StringUtil.equals(orgName, "云南代表处") || StringUtil.equals(orgName, "湖南代表处");
	}
}
