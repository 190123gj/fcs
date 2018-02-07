/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午5:44:40 创建
 */
package com.born.fcs.face.web.controller.fund;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.born.face.webui.table.ReportTemplate;
import com.born.face.webui.table.TableBuilder;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.pm.ws.order.common.ProjectOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 委贷明细表
 * @author hjiajie
 * 
 */
@Controller
@RequestMapping("fundMg/report")
public class FundEntrustedLoanController extends BaseController {
	
	final static String vm_path = "/fundMg/fundMgModule/reportAnalyze/";
	
	/**
	 * 查询
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("entrustedLoanDetail.htm")
	public String entrustedLoanDetail(ProjectOrder order, HttpServletRequest request, Model model) {
		
		ReportDataResult result = reportFormAnalysisServiceClient.getEntrustedLoanDetail(order);
		
		if (result.isExecuted() && result.isSuccess()) {
			
			model.addAttribute("result", result);
			Money all = Money.zero();
			// item.get("");
			List<List<String>> pages = new ArrayList<List<String>>();
			List<DataListItem> items = result.getDataList();
			if (items != null && ListUtil.isNotEmpty(items)) {
				
				for (DataListItem item : items) {
					List<String> strs = new ArrayList<String>();
					String str = (String) item.getMap().get("project_code");
					strs.add(str);
					str = (String) item.getMap().get("project_name");
					strs.add(str);
					str = (String) item.getMap().get("customer_name");
					strs.add(str);
					str = (String) item.getMap().get("dept_name");
					strs.add(str);
					str = (String) item.getMap().get("busi_manager_name");
					strs.add(str);
					str = (String) item.getMap().get("capital_channel_name");
					strs.add(str);
					Date date = (Date) item.getMap().get("actual_loan_time");
					strs.add(DateUtil.dtSimpleFormat(date));
					date = (Date) item.getMap().get("end_time");
					strs.add(DateUtil.dtSimpleFormat(date));
					BigDecimal decimal = (BigDecimal) item.getMap().get("interest_rate");
					String formatString = "######0.00";
					DecimalFormat decimalFormat = new DecimalFormat(formatString);
					String temp = decimalFormat.format(decimal);
					strs.add(temp);
					Money mon = (Money) item.getMap().get("actual_amount");
					strs.add(mon.toStandardString());
					all.addTo(mon);
					
					pages.add(strs);
				}
			}
			model.addAttribute("pages", pages);
			model.addAttribute("all", all);
		}
		model.addAttribute("conditions", order);
		return vm_path + "entrustLoanDetail.vm";
	}
	
	/***
	 * 导出
	 */
	@RequestMapping("entrustedLoanDetailExport.htm")
	public String entrustedLoanDetailExport(ProjectOrder order, HttpServletRequest request,
											HttpServletResponse response, Model model) {
		ReportDataResult result = reportFormAnalysisServiceClient.getEntrustedLoanDetail(order);
		// 添加总计
		
		Money all = Money.zero();
		// item.get("");
		List<DataListItem> items = result.getDataList();
		if (items != null && ListUtil.isNotEmpty(items)) {
			for (DataListItem item : items) {
				Date date = (Date) item.getMap().get("actual_loan_time");
				item.getMap().put("actual_loan_time", DateUtil.dtSimpleFormat(date));
				
				date = (Date) item.getMap().get("end_time");
				item.getMap().put("end_time", DateUtil.dtSimpleFormat(date));
				
				BigDecimal decimal = (BigDecimal) item.getMap().get("interest_rate");
				String formatString = "######0.00";
				DecimalFormat decimalFormat = new DecimalFormat(formatString);
				String temp = decimalFormat.format(decimal);
				item.getMap().put("interest_rate", temp);
				
				Money mon = (Money) item.getMap().get("actual_amount");
				item.getMap().put("actual_amount", mon.toStandardString());
				all.addTo(mon);
			}
			
			DataListItem item = new DataListItem();
			item.getMap().put("actual_amount", all.toStandardString());
			item.getMap().put("project_code", "总计");
			result.getDataList().add(item);
		} else {
			result.setDataList(new ArrayList<DataListItem>());
		}
		ReportTemplate reportTemplate = new ReportTemplate();
		reportTemplate.setReportName("委托贷款明细表");
		TableBuilder builder = new TableBuilder(result, reportTemplate,
			TableBuilder.Table_Option_Excel, false);
		builder.init();
		builder.dataBind(request, response);
		return null;
	}
	
}
