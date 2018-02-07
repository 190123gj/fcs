/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午3:48:57 创建
 */
package com.born.fcs.face.web.controller.fund;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.fm.ws.enums.FormInnerLoanTypeEnum;
import com.born.fcs.fm.ws.info.innerLoan.FormInnerLoanFormInfo;
import com.born.fcs.fm.ws.order.innerLoan.FormInnerLoanQueryOrder;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.field.DataTypeEnum;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;
import com.born.fcs.pm.ws.service.report.result.ReportItem;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 
 * @author hjiajie 使用内部借款单列表，此处废弃
 */
@Controller
@RequestMapping("fundMg/report")
public class FundInnerLoanController extends BaseController {
	final static String vm_path = "/fundMg/fundMgModule/reportAnalyze/";
	private static String formatString = "######0.00";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "useTime", "backTime", "backTimeEnd", "interestTime" };
	}
	
	/**
	 * 查询 使用列表查询
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("innerLoanList.htm")
	public String innerLoanList(FormInnerLoanQueryOrder order, HttpServletRequest request,
								Model model) {
		
		//setSessionLocalInfo2Order(order);
		
		// 20161201 from禅道 进入页面默认展示还款时间未到期的 判断是否是页面过来
		String invm = request.getParameter("invm");
		if (!BooleanEnum.YES.code().equals(invm)) {
			order.setBackTime(DateUtil.getStartTimeOfTheDate(new Date()));
		}
		
		order.setPageNumber(1);
		order.setPageSize(999);
		order.setInnerLoanType(FormInnerLoanTypeEnum.LOAN_AGREEMENT);
		order.setFormStatus(FormStatusEnum.APPROVAL);
		QueryBaseBatchResult<FormInnerLoanFormInfo> result = formInnerLoanServiceClient
			.searchForm(order);
		Money allAmount = Money.zero();
		Money allInterest = Money.zero();
		if (result != null && ListUtil.isNotEmpty(result.getPageList())) {
			for (FormInnerLoanFormInfo info : result.getPageList()) {
				allAmount.addTo(info.getLoanAmount());
				allInterest.addTo(info.getInterest());
				if (StringUtil.isNotBlank(info.getInterestRate())) {
					BigDecimal decimal = new BigDecimal(info.getInterestRate());
					
					DecimalFormat decimalFormat = new DecimalFormat(formatString);
					String temp = decimalFormat.format(decimal);
					info.setInterestRate(temp);
				}
			}
		}
		model.addAttribute("page", PageUtil.getCovertPage(result));
		model.addAttribute("result", result);
		model.addAttribute("conditions", order);
		model.addAttribute("allAmount", allAmount);
		model.addAttribute("allInterest", allInterest);
		return vm_path + "interiorLoadDetail.vm";
	}
	
	/***
	 * 导出
	 */
	@RequestMapping("innerLoanExport.htm")
	public String innerLoanExport(FormInnerLoanQueryOrder order, HttpServletRequest request,
									HttpServletResponse response, Model model) {
		order.setPageNumber(1);
		order.setPageSize(999);
		order.setInnerLoanType(FormInnerLoanTypeEnum.LOAN_AGREEMENT);
		order.setFormStatus(FormStatusEnum.APPROVAL);
		QueryBaseBatchResult<FormInnerLoanFormInfo> batchResult = formInnerLoanServiceClient
			.searchForm(order);
		makeExcel(batchResult, request, response);
		return null;
	}
	
	public void makeExcel(QueryBaseBatchResult<FormInnerLoanFormInfo> result,
							HttpServletRequest request, HttpServletResponse response) {
		ReportDataResult dataResult = makeResult(result);
		ReportTemplate reportTemplate = new ReportTemplate();
		reportTemplate.setReportName("协议借款明细表");
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
	
	private static ReportDataResult makeResult(QueryBaseBatchResult<FormInnerLoanFormInfo> result) {
		ReportDataResult dataResult = new ReportDataResult();
		if (result == null || ListUtil.isEmpty(result.getPageList())) {
			return dataResult;
		}
		dataResult.getFcsFields().add(new FcsField("protocol_code", "协议编码", null));
		dataResult.getFcsFields().add(new FcsField("creditor_name", "债权人", null));
		dataResult.getFcsFields().add(new FcsField("apply_dept_name", "借款单位", null));
		dataResult.getFcsFields().add(new FcsField("use_time", "用款时间", null));
		dataResult.getFcsFields().add(new FcsField("back_time", "还款时间", null));
		dataResult.getFcsFields().add(new FcsField("interest_rate", "利率(%/年)", null));
		dataResult.getFcsFields().add(new FcsField("loan_amount", "借款金额(元)", null));
		dataResult.getFcsFields().add(new FcsField("interest", "预估利息(元)", null));
		
		List<DataListItem> dataListItems = Lists.newArrayList();
		// 信息
		Money allAmount = Money.zero();
		Money allInterest = Money.zero();
		for (FormInnerLoanFormInfo info : result.getPageList()) {
			DataListItem item = new DataListItem();
			try {
				List<ReportItem> valueList = Lists.newArrayList();
				
				//1
				ReportItem reportItem = new ReportItem();
				reportItem.setKey("protocol_code");
				reportItem.setValue(info.getProtocolCode());
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				//2
				reportItem = new ReportItem();
				reportItem.setKey("creditor_name");
				reportItem.setValue(info.getCreditorName());
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				//3
				reportItem = new ReportItem();
				reportItem.setKey("apply_dept_name");
				reportItem.setValue(info.getApplyDeptName());
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				//4
				reportItem = new ReportItem();
				reportItem.setKey("use_time");
				reportItem.setValue(DateUtil.dtSimpleFormat(info.getUseTime()));
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				//5
				reportItem = new ReportItem();
				reportItem.setKey("back_time");
				reportItem.setValue(DateUtil.dtSimpleFormat(info.getBackTime()));
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				//6
				if (StringUtil.isNotEmpty(info.getInterestRate())) {
					
					reportItem = new ReportItem();
					reportItem.setKey("interest_rate");
					
					BigDecimal decimal = new BigDecimal(info.getInterestRate());
					
					DecimalFormat decimalFormat = new DecimalFormat(formatString);
					String temp = decimalFormat.format(decimal);
					
					reportItem.setValue(temp);
					reportItem.setDataTypeEnum(DataTypeEnum.STRING);
					valueList.add(reportItem);
				}
				
				//7
				reportItem = new ReportItem();
				reportItem.setKey("loan_amount");
				reportItem.setValue(info.getLoanAmount().toStandardString());
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				allAmount.addTo(info.getLoanAmount());
				
				//8
				reportItem = new ReportItem();
				reportItem.setKey("interest");
				reportItem.setValue(info.getInterest().toStandardString());
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				allInterest.addTo(info.getInterest());
				
				item.setValueList(valueList);
				dataListItems.add(item);
			} catch (Exception e) {
			}
		}
		DataListItem item = new DataListItem();
		List<ReportItem> valueList = Lists.newArrayList();
		
		//1
		ReportItem reportItem = new ReportItem();
		reportItem.setKey("protocol_code");
		reportItem.setValue("总计");
		reportItem.setDataTypeEnum(DataTypeEnum.STRING);
		valueList.add(reportItem);
		//7
		reportItem = new ReportItem();
		reportItem.setKey("loan_amount");
		reportItem.setValue(allAmount.toStandardString());
		reportItem.setDataTypeEnum(DataTypeEnum.STRING);
		valueList.add(reportItem);
		
		//8
		reportItem = new ReportItem();
		reportItem.setKey("interest");
		reportItem.setValue(allInterest.toStandardString());
		reportItem.setDataTypeEnum(DataTypeEnum.STRING);
		valueList.add(reportItem);
		
		item.setValueList(valueList);
		dataListItems.add(item);
		
		dataResult.setDataList(dataListItems);
		return dataResult;
	}
	
}
