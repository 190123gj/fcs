/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午3:48:57 创建
 */
package com.born.fcs.face.web.controller.fund;

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
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.fm.ws.info.payment.FormTravelExpenseInfo;
import com.born.fcs.fm.ws.order.payment.TravelExpenseQueryOrder;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
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
 * @author hjiajie 差旅费报销单导出
 */
@Controller
@RequestMapping("fundMg/report")
public class FundTravelExpenseController extends FundBaseController {
	final static String vm_path = "/fundMg/fundMgModule/reportAnalyze/";
	
	/***
	 * 查询
	 */
	@RequestMapping("travelExpenseList.htm")
	public String travelExpenseList(HttpServletRequest request, HttpServletResponse response,
									Model model) {
		TravelExpenseQueryOrder order = new TravelExpenseQueryOrder();
		WebUtil.setPoPropertyByRequest(order, request);
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
		order.setLoginUserId(0);
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
		order.setPageNumber(1);
		order.setPageSize(999);
		QueryBaseBatchResult<FormTravelExpenseInfo> queryResult = travelExpenseServiceClient
			.queryPage(order);
		order.setApplyTimeStart(orgTimeStart);
		order.setApplyTimeEnd(orgTimeEnd);
		Money allAmount = Money.zero();
		if (queryResult != null) {
			
			for (FormTravelExpenseInfo info : queryResult.getPageList()) {
				allAmount.addTo(info.getAmount());
			}
		}
		
		model.addAttribute("period", period);
		model.addAttribute("allAmount", allAmount);
		model.addAttribute("page", PageUtil.getCovertPage(queryResult));
		model.addAttribute("result", queryResult);
		model.addAttribute("conditions", order);
		return vm_path + "travelDetail.vm";
	}
	
	/***
	 * 导出
	 */
	@RequestMapping("travelExpenseExport.htm")
	public String innerLoanExport(HttpServletRequest request, HttpServletResponse response,
									Model model) {
		TravelExpenseQueryOrder order = new TravelExpenseQueryOrder();
		WebUtil.setPoPropertyByRequest(order, request);
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
		order.setLoginUserId(0);
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
		order.setPageNumber(1);
		order.setPageSize(999);
		QueryBaseBatchResult<FormTravelExpenseInfo> queryResult = travelExpenseServiceClient
			.queryPage(order);
		order.setApplyTimeStart(orgTimeStart);
		order.setApplyTimeEnd(orgTimeEnd);
		makeExcel(queryResult, request, response);
		return null;
	}
	
	public void makeExcel(QueryBaseBatchResult<FormTravelExpenseInfo> result,
							HttpServletRequest request, HttpServletResponse response) {
		ReportDataResult dataResult = makeResult(result);
		ReportTemplate reportTemplate = new ReportTemplate();
		reportTemplate.setReportName("差旅费报销单明细表");
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
	
	private static ReportDataResult makeResult(QueryBaseBatchResult<FormTravelExpenseInfo> result) {
		ReportDataResult dataResult = new ReportDataResult();
		if (result == null || ListUtil.isEmpty(result.getPageList())) {
			return dataResult;
		}
		//		  <th class="onbreaks">单据号</th>
		//          <th class="onbreaks">事由</th>
		//          <th class="onbreaks">报销人</th>
		//          <th class="onbreaks">部门</th>
		//          <th class="onbreaks">收款人</th>
		//          
		//          <th class="onbreaks">开户行</th>
		//          <th class="onbreaks">帐号</th>
		//          <th class="onbreaks">金额（元）</th>
		//          <th class="onbreaks">申请时间</th>
		//          <th class="onbreaks">审核通过时间</th>
		//          <th class="onbreaks">过账时间</th>
		//          
		dataResult.getFcsFields().add(new FcsField("billNo", "单据号", null));
		dataResult.getFcsFields().add(new FcsField("reasons", "事由", null));
		dataResult.getFcsFields().add(new FcsField("applyUserName", "报销人", null));
		dataResult.getFcsFields().add(new FcsField("deptName", "部门", null));
		dataResult.getFcsFields().add(new FcsField("payee", "收款人", null));
		
		dataResult.getFcsFields().add(new FcsField("bank", "开户行", null));
		dataResult.getFcsFields().add(new FcsField("bankAccount", "帐号", null));
		dataResult.getFcsFields().add(new FcsField("amount", "金额(元)", null));
		dataResult.getFcsFields().add(new FcsField("applicationTime", "申请日期", null));
		dataResult.getFcsFields().add(new FcsField("finishTime", "审核日期", null));
		
		dataResult.getFcsFields().add(new FcsField("voucherSyncFinishTime", "过账日期", null));
		
		List<DataListItem> dataListItems = Lists.newArrayList();
		// 信息
		Money allAmount = Money.zero();
		for (FormTravelExpenseInfo info : result.getPageList()) {
			DataListItem item = new DataListItem();
			List<ReportItem> valueList = Lists.newArrayList();
			
			//1
			ReportItem reportItem = new ReportItem();
			reportItem.setKey("billNo");
			reportItem.setValue(info.getBillNo());
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			//2
			reportItem = new ReportItem();
			reportItem.setKey("reasons");
			reportItem.setValue(info.getReasons());
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			//3
			reportItem = new ReportItem();
			reportItem.setKey("applyUserName");
			reportItem.setValue(info.getFormUserName());
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			//4
			reportItem = new ReportItem();
			reportItem.setKey("deptName");
			reportItem.setValue(info.getDeptName());
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
			reportItem.setKey("bankAccount");
			reportItem.setValue(info.getBankAccount());
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			//8
			reportItem = new ReportItem();
			reportItem.setKey("amount");
			reportItem.setValue(info.getAmount().toStandardString());
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			allAmount.addTo(info.getAmount());
			
			//9
			reportItem = new ReportItem();
			reportItem.setKey("applicationTime");
			reportItem.setValue(DateUtil.dtSimpleFormat(info.getApplicationTime()));
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			//10
			reportItem = new ReportItem();
			reportItem.setKey("finishTime");
			reportItem.setValue(DateUtil.dtSimpleFormat(info.getFinishTime()));
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			//11
			reportItem = new ReportItem();
			reportItem.setKey("voucherSyncFinishTime");
			reportItem.setValue(DateUtil.dtSimpleFormat(info.getVoucherSyncFinishTime()));
			reportItem.setDataTypeEnum(DataTypeEnum.STRING);
			valueList.add(reportItem);
			
			item.setValueList(valueList);
			dataListItems.add(item);
		}
		
		DataListItem item = new DataListItem();
		List<ReportItem> valueList = Lists.newArrayList();
		
		//1
		ReportItem reportItem = new ReportItem();
		reportItem.setKey("billNo");
		reportItem.setValue("合计");
		reportItem.setDataTypeEnum(DataTypeEnum.STRING);
		valueList.add(reportItem);
		
		//10
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
