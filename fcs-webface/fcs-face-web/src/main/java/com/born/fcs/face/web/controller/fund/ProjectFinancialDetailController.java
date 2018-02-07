/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午2:31:11 创建
 */
package com.born.fcs.face.web.controller.fund;

import java.util.ArrayList;
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
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.fm.ws.result.report.ProjectFinancialDetailResult;
import com.born.fcs.pm.util.RateUtil;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectQueryOrder;
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
 * 理财项目明细
 * @author Ji
 * 
 */
@Controller
@RequestMapping("fundMg/report")
public class ProjectFinancialDetailController extends BaseController {
	final static String vm_path = "/fundMg/fundMgModule/reportAnalyze/";
	
	/**
	 * 查询
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("projectFinancialDetailList.htm")
	public String projectFinancialDetailList(HttpServletRequest request, Model model) {
		FinancialProjectQueryOrder order = new FinancialProjectQueryOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		ProjectFinancialDetailResult result = reportFormAnalysisServiceClient
			.projectFinancialDetail(order);
		Money realHasAmount = Money.zero();
		Money totalBuyAmount = Money.zero();
		if (result.isExecuted() && result.isSuccess()) {
			List<ProjectFinancialInfo> list = result.getList();
			model.addAttribute("itemList", list);
			if (list != null && list.size() > 0) {
				for (ProjectFinancialInfo projectFinancialInfo : list) {
					realHasAmount.addTo(projectFinancialInfo.getActualPrice().multiply(
						projectFinancialInfo.getOriginalHoldNum()));
					totalBuyAmount.addTo(projectFinancialInfo.getActualPrice().multiply(
						projectFinancialInfo.getActualBuyNum()));
				}
			}
		}
		model.addAttribute("conditions", order);
		model.addAttribute("realHasAmount", realHasAmount);
		model.addAttribute("totalBuyAmount", totalBuyAmount);
		if (realHasAmount.getCent() > 0 || totalBuyAmount.getCent() > 0)
			model.addAttribute("all", "true");
		return vm_path + "manageDetail.vm";
	}
	
	/***
	 * 导出
	 */
	@RequestMapping("projectFinancialDetailExport.htm")
	public String deptAccountTypeBankMessageExport(HttpServletRequest request,
													HttpServletResponse response, Model model) {
		FinancialProjectQueryOrder order = new FinancialProjectQueryOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		ProjectFinancialDetailResult result = reportFormAnalysisServiceClient
			.projectFinancialDetail(order);
		if (result != null && result.isSuccess()) {
			makeExcel(result, request, response);
		}
		return null;
	}
	
	public void makeExcel(ProjectFinancialDetailResult result, HttpServletRequest request,
							HttpServletResponse response) {
		ReportDataResult dataResult = makeResult(result);
		if (dataResult != null) {
			ReportTemplate reportTemplate = new ReportTemplate();
			reportTemplate.setReportName("理财项目明细表");
			//多行表头
			//		String[][] head = new String[1][7];
			//		head[0] = new String[] { "项目编码", "发行机构", "申购日期", "到期日", "购买期限", "利率（%/年）", "购买金额（元）" };
			//		reportTemplate.setColHeadString(head);
			
			reportTemplate.setMergeRow(false);
			reportTemplate.setMergeColCount(2);
			TableBuilder builder = new TableBuilder(dataResult, reportTemplate,
				TableBuilder.Table_Option_Excel, false);
			builder.init();
			builder.dataBind(request, response);
			//System.out.print(builder.getString());
		}
	}
	
	private static ReportDataResult makeResult(ProjectFinancialDetailResult result) {
		List<ProjectFinancialInfo> financial = result.getList();
		ReportDataResult dataResult = new ReportDataResult();
		if (ListUtil.isEmpty(financial)) {
			dataResult.setDataList(new ArrayList<DataListItem>());
			return dataResult;
		}
		
		// 头
		dataResult.getFcsFields().add(new FcsField("projectCode", "项目编号", null));
		dataResult.getFcsFields().add(new FcsField("issueInstitution", "发行机构", null));
		dataResult.getFcsFields().add(new FcsField("actualBuyDate", "申购日期", null));
		dataResult.getFcsFields().add(new FcsField("actualExpireDate", "到期日", null));
		dataResult.getFcsFields().add(new FcsField("timeLimit", "购买期限", null));
		dataResult.getFcsFields().add(new FcsField("interestRate", "利率（%/年）", null));
		dataResult.getFcsFields().add(
			new FcsField("realHasPrice", "持有期金额（元）", null, DataTypeEnum.MONEY));
		dataResult.getFcsFields().add(
			new FcsField("actualPrice", "实际购买金额（元）", null, DataTypeEnum.MONEY));
		
		List<DataListItem> dataListItems = Lists.newArrayList();
		Money realHasAmount = Money.zero();
		Money totalBuyAmount = Money.zero();
		// 信息
		for (int i = 0; i < financial.size() + 1; i++) {
			
			ReportItem reportItem = new ReportItem();
			List<ReportItem> valueList = Lists.newArrayList();
			DataListItem item = new DataListItem();
			if (i != financial.size()) {
				ProjectFinancialInfo info = financial.get(i);
				realHasAmount.addTo(info.getActualPrice().multiply(info.getOriginalHoldNum()));
				totalBuyAmount.addTo(info.getActualPrice().multiply(info.getActualBuyNum()));
				reportItem.setKey("projectCode");
				reportItem.setValue(info.getProjectCode());
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("issueInstitution");
				reportItem.setValue(info.getIssueInstitution());
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("actualBuyDate");
				reportItem.setValue(DateUtil.dtSimpleFormat(info.getActualBuyDate()));
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("actualExpireDate");
				reportItem.setValue(DateUtil.dtSimpleFormat(info.getActualExpireDate()));
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("timeLimit");
				reportItem.setValue(info.getTimeLimit() + info.getTimeUnit().message());
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("interestRate");
				reportItem.setValue(RateUtil.formatRate(info.getInterestRate()) + "");
				reportItem.setDataTypeEnum(DataTypeEnum.BIGDECIMAL);
				valueList.add(reportItem);
				
				// 持有金额
				reportItem = new ReportItem();
				reportItem.setKey("realHasPrice");
				reportItem.setValue(info.getActualPrice().multiply(info.getOriginalHoldNum())
					.toStandardString()
									+ "");
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("actualPrice");
				reportItem.setValue(info.getActualPrice().multiply(info.getActualBuyNum())
					.toStandardString()
									+ "");
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				item.setValueList(valueList);
				dataListItems.add(item);
			} else {
				reportItem = new ReportItem();
				reportItem.setKey("projectCode");
				reportItem.setValue("合计");
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("realHasPrice");
				reportItem.setValue(realHasAmount.toStandardString() + "");
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("actualPrice");
				reportItem.setValue(totalBuyAmount.toStandardString() + "");
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				item.setValueList(valueList);
				dataListItems.add(item);
				
			}
		}
		
		dataResult.setDataList(dataListItems);
		return dataResult;
	}
}
