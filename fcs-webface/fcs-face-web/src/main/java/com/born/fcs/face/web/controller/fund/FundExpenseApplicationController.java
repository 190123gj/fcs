/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午3:48:57 创建
 */
package com.born.fcs.face.web.controller.fund;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.face.webui.table.ReportTemplate;
import com.born.face.webui.table.TableBuilder;
import com.born.fcs.face.web.controller.base.FundBaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.fm.ws.enums.CostCategoryStatusEnum;
import com.born.fcs.fm.ws.info.payment.CostCategoryInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationDetailAllInfo;
import com.born.fcs.fm.ws.order.payment.CostCategoryQueryOrder;
import com.born.fcs.fm.ws.order.payment.ExpenseApplicationQueryOrder;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.field.DataTypeEnum;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;
import com.born.fcs.pm.ws.service.report.result.ReportItem;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 
 * @author hjiajie 费用支付申请单导出
 */
@Controller
@RequestMapping("fundMg/report")
public class FundExpenseApplicationController extends FundBaseController {
	final static String vm_path = "/fundMg/fundMgModule/reportAnalyze/";
	
	/***
	 * 列表
	 */
	@RequestMapping("expenseApplicationList.htm")
	public String expenseApplicationList(HttpServletRequest request, HttpServletResponse response,
											Model model) {
		ExpenseApplicationQueryOrder order = getExpenseApplicationQueryOrder(request, model, false);
		String period = request.getParameter("period");
		if (period == null) {
			period = com.yjf.common.lang.util.DateUtil.dtSimpleYmFormat(new Date());
		}
		String orgTimeStart = order.getApplyTimeStart();
		String orgTimeEnd = order.getApplyTimeEnd();
		if (StringUtil.isNotBlank(period)) {
			String newPeriod = "9999";
			if (period.matches("^\\d{4}-?\\d?\\d")) {
				String[] periods = period.split("-");
				if (periods.length > 1) {
					newPeriod = periods[0] + "-" + StringUtil.alignRight(periods[1], 2, '0');
				} else {
					newPeriod = periods[0].substring(0, 4)
								+ "-"
								+ StringUtil.alignRight(
									periods[0].substring(4, periods[0].length()), 2, '0');
				}
			}
			order.setApplyTimeStart(newPeriod + "-01 00:00:00");
			order.setApplyTimeEnd(newPeriod + "-31 23:59:59");
			
			orgTimeStart = null;
			orgTimeEnd = null;
		}
		// 添加公务卡条件
		order.setOfficialCard(BooleanEnum.IS.code());
		order.setPageSize(999);
		order.setPageNumber(1);
		QueryBaseBatchResult<FormExpenseApplicationDetailAllInfo> queryResult = expenseApplicationServiceClient
			.queryPageAll(order);
		order.setApplyTimeStart(orgTimeStart);
		order.setApplyTimeEnd(orgTimeEnd);
		
		CostCategoryQueryOrder order2 = new CostCategoryQueryOrder();
		order2.setStatusList(new ArrayList<CostCategoryStatusEnum>());
		order2.getStatusList().add(CostCategoryStatusEnum.NORMAL);
		order2.setPageSize(10000);
		QueryBaseBatchResult<CostCategoryInfo> batchResult = costCategoryServiceClient
			.queryPage(order2);
		Money allAmount = Money.zero();
		for (FormExpenseApplicationDetailAllInfo info : queryResult.getPageList()) {
			for (CostCategoryInfo cost : batchResult.getPageList()) {
				if (cost.getCategoryId() > 0
					&& String.valueOf(cost.getCategoryId()).equals(info.getExpenseType())) {
					info.setExpenseType(cost.getName());
					break;
				}
			}
			allAmount.addTo(info.getAmount());
		}
		
		// 抓取费用种类 
		CostCategoryQueryOrder order23 = new CostCategoryQueryOrder();
		order23.setStatusList(new ArrayList<CostCategoryStatusEnum>());
		order23.getStatusList().add(CostCategoryStatusEnum.NORMAL);
		order23.setPageSize(10000);
		QueryBaseBatchResult<CostCategoryInfo> batchResult2 = costCategoryServiceClient
			.queryPage(order23);
		model.addAttribute("categoryList", batchResult2.getPageList());
		
		model.addAttribute("period", period);
		model.addAttribute("allAmount", allAmount);
		model.addAttribute("page", PageUtil.getCovertPage(queryResult));
		model.addAttribute("conditions", order);
		return vm_path + "expenseApplicationDetail.vm";
	}
	
	/***
	 * 导出
	 */
	@RequestMapping("expenseApplicationExport.htm")
	public String expenseApplicationExport(HttpServletRequest request,
											HttpServletResponse response, Model model) {
		ExpenseApplicationQueryOrder order = getExpenseApplicationQueryOrder(request, model, false);
		String period = request.getParameter("period");
		String orgTimeStart = order.getApplyTimeStart();
		String orgTimeEnd = order.getApplyTimeEnd();
		if (StringUtil.isNotBlank(period)) {
			String newPeriod = "9999";
			if (period.matches("^\\d{4}-?\\d?\\d")) {
				String[] periods = period.split("-");
				if (periods.length > 1) {
					newPeriod = periods[0] + "-" + StringUtil.alignRight(periods[1], 2, '0');
				} else {
					newPeriod = periods[0].substring(0, 4)
								+ "-"
								+ StringUtil.alignRight(
									periods[0].substring(4, periods[0].length()), 2, '0');
				}
			}
			order.setApplyTimeStart(newPeriod + "-01 00:00:00");
			order.setApplyTimeEnd(newPeriod + "-31 23:59:59");
			
			orgTimeStart = null;
			orgTimeEnd = null;
		}
		// 添加公务卡条件
		order.setOfficialCard(BooleanEnum.IS.code());
		order.setPageSize(999);
		order.setPageNumber(1);
		QueryBaseBatchResult<FormExpenseApplicationDetailAllInfo> queryResult = expenseApplicationServiceClient
			.queryPageAll(order);
		order.setApplyTimeStart(orgTimeStart);
		order.setApplyTimeEnd(orgTimeEnd);
		model.addAttribute("page", PageUtil.getCovertPage(queryResult));
		model.addAttribute("conditions", order);
		makeExcel(queryResult, request, response);
		return null;
	}
	
	public void makeExcel(QueryBaseBatchResult<FormExpenseApplicationDetailAllInfo> result,
							HttpServletRequest request, HttpServletResponse response) {
		
		CostCategoryQueryOrder order2 = new CostCategoryQueryOrder();
		order2.setStatusList(new ArrayList<CostCategoryStatusEnum>());
		order2.getStatusList().add(CostCategoryStatusEnum.NORMAL);
		order2.setPageSize(10000);
		QueryBaseBatchResult<CostCategoryInfo> batchResult = costCategoryServiceClient
			.queryPage(order2);
		
		ReportDataResult dataResult = makeResult(result, batchResult.getPageList());
		ReportTemplate reportTemplate = new ReportTemplate();
		reportTemplate.setReportName("费用支付申请单明细表");
		
		//		//多行表头
		//		String[][] head = new String[2][3];
		//		head[0] = new String[] { "大类", "大类", "数据" };
		//		head[1] = new String[] { "类型", "名称", "数据" };
		//		reportTemplate.setColHeadString(head);
		//		
		//		reportTemplate.setMergeRow(true);
		//		reportTemplate.setMergeColCount(2);
		TableBuilder builder = new TableBuilder(dataResult, reportTemplate,
			TableBuilder.Table_Option_Excel, false);
		builder.init();
		builder.dataBind(request, response);
	}
	
	private static ReportDataResult makeResult(	QueryBaseBatchResult<FormExpenseApplicationDetailAllInfo> result,
												List<CostCategoryInfo> costs) {
		ReportDataResult dataResult = new ReportDataResult();
		if (result == null || ListUtil.isEmpty(result.getPageList())) {
			return dataResult;
		}
		//		 <th class="onbreaks">报销单号</th>
		//         <th class="onbreaks">事由</th>
		//         <th class="onbreaks">报销人</th>
		//         <th class="onbreaks">部门</th>
		//         <th class="onbreaks">收款人</th>
		
		//         <th class="onbreaks">开户行</th>
		//         <th class="onbreaks">帐号</th>
		//         <th class="onbreaks">费用方向</th>
		//         <th class="onbreaks">费用类型</th>
		//         <th class="onbreaks">金额（元）</th>
		
		//         <th class="onbreaks">申请时间</th>
		//         <th class="onbreaks">审核通过时间</th>
		//         <th class="onbreaks">过账时间</th>
		dataResult.getFcsFields().add(new FcsField("bill_no", "单据号", null));
		dataResult.getFcsFields().add(new FcsField("reimburse_reason", "事由", null));
		dataResult.getFcsFields().add(new FcsField("agent", "报销人", null));
		dataResult.getFcsFields().add(new FcsField("dept_name", "申请部门", null));
		dataResult.getFcsFields().add(new FcsField("payee", "收款人", null));
		
		dataResult.getFcsFields().add(new FcsField("bank", "开户行", null));
		dataResult.getFcsFields().add(new FcsField("bank_account", "帐号", null));
		dataResult.getFcsFields().add(new FcsField("direction", "费用方向", null));
		dataResult.getFcsFields().add(new FcsField("expense_type", "费用类型", null));
		dataResult.getFcsFields().add(new FcsField("amount", "金额(元)", null));
		
		dataResult.getFcsFields().add(new FcsField("application_time", "申请日期", null));
		dataResult.getFcsFields().add(new FcsField("finish_time", "审核通过日期", null));
		dataResult.getFcsFields().add(new FcsField("voucherSyncFinishTime", "过账时间", null));
		
		List<DataListItem> dataListItems = Lists.newArrayList();
		// 信息
		Money allAmount = Money.zero();
		for (FormExpenseApplicationDetailAllInfo info : result.getPageList()) {
			DataListItem item = new DataListItem();
			List<ReportItem> valueList = Lists.newArrayList();
			
			//1
			ReportItem reportItem = new ReportItem();
			reportItem.setKey("bill_no");
			reportItem.setValue(info.getBillNo());
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			//2
			reportItem = new ReportItem();
			reportItem.setKey("reimburse_reason");
			reportItem.setValue(info.getReimburseReason());
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			//3
			reportItem = new ReportItem();
			reportItem.setKey("agent");
			reportItem.setValue(info.getAgent());
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			//4
			reportItem = new ReportItem();
			reportItem.setKey("dept_name");
			reportItem.setValue(info.getIndexDeptName());
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			//5
			reportItem = new ReportItem();
			reportItem.setKey("payee");
			reportItem.setValue(info.getPayee());
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			//6
			reportItem = new ReportItem();
			reportItem.setKey("bank");
			reportItem.setValue(info.getBank());
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			//7
			reportItem = new ReportItem();
			reportItem.setKey("bank_account");
			reportItem.setValue(info.getBankAccount());
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			//8
			reportItem = new ReportItem();
			reportItem.setKey("direction");
			reportItem.setValue(info.getDirection().message());
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			//9
			reportItem = new ReportItem();
			reportItem.setKey("expense_type");
			String expenseValue = info.getExpenseType();
			for (CostCategoryInfo cost : costs) {
				if (cost.getCategoryId() > 0
					&& String.valueOf(cost.getCategoryId()).equals(info.getExpenseType())) {
					expenseValue = cost.getName();
					break;
				}
			}
			reportItem.setValue(expenseValue);
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			//10
			reportItem = new ReportItem();
			reportItem.setKey("amount");
			reportItem.setValue(info.getAmount().toStandardString());
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			// 11
			reportItem = new ReportItem();
			reportItem.setKey("application_time");
			reportItem.setValue(DateUtil.dtSimpleFormat(info.getApplicationTime()));
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			//12
			reportItem = new ReportItem();
			reportItem.setKey("finish_time");
			reportItem.setValue(DateUtil.dtSimpleFormat(info.getFinishTime()));
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			//13
			reportItem = new ReportItem();
			reportItem.setKey("voucherSyncFinishTime");
			reportItem.setValue(DateUtil.dtSimpleFormat(info.getVoucherSyncFinishTime()));
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			allAmount.addTo(info.getAmount());
			
			item.setValueList(valueList);
			dataListItems.add(item);
		}
		
		DataListItem item = new DataListItem();
		List<ReportItem> valueList = Lists.newArrayList();
		
		ReportItem reportItem = new ReportItem();
		reportItem.setKey("bill_no");
		reportItem.setValue("合计");
		reportItem.setDataTypeEnum(DataTypeEnum.STRING);
		valueList.add(reportItem);
		
		reportItem = new ReportItem();
		reportItem.setKey("amount");
		reportItem.setValue(allAmount.toStandardString());
		reportItem.setDataTypeEnum(DataTypeEnum.STRING);
		valueList.add(reportItem);
		
		item.setValueList(valueList);
		dataListItems.add(item);
		
		dataResult.setDataList(dataListItems);
		return dataResult;
	}
	
}
