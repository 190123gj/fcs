/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午2:31:11 创建
 */
package com.born.fcs.face.web.controller.fund;

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
import com.born.fcs.fm.ws.info.bank.BankMessageInfo;
import com.born.fcs.fm.ws.info.report.AccountTypeBankMessageInfo;
import com.born.fcs.fm.ws.info.report.DeptAccountTypeBankMessageInfo;
import com.born.fcs.fm.ws.result.report.DeptAccountTypeBankMessageResult;
import com.born.fcs.pm.ws.service.report.field.DataTypeEnum;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;
import com.born.fcs.pm.ws.service.report.result.ReportItem;

/**
 * 
 * 可用资金明细
 * @author hjiajie
 * 
 */
@Controller
@RequestMapping("fundMg/report")
public class FundDeptAccountTypeBankMessageController extends BaseController {
	final static String vm_path = "/fundMg/fundMgModule/reportAnalyze/";
	
	/**
	 * 查询
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("deptAccountTypeBankMessageList.htm")
	public String officardlist(HttpServletRequest request, Model model) {
		DeptAccountTypeBankMessageResult result = reportFormAnalysisServiceClient
			.usedAccountDetail();
		if (result.isExecuted() && result.isSuccess()) {
			model.addAttribute("itemList", result.getList());
		}
		return vm_path + "usableFundDetail.vm";
	}
	
	/***
	 * 导出
	 */
	@RequestMapping("deptAccountTypeBankMessageExport.htm")
	public String deptAccountTypeBankMessageExport(HttpServletRequest request,
													HttpServletResponse response, Model model) {
		DeptAccountTypeBankMessageResult result = reportFormAnalysisServiceClient
			.usedAccountDetail();
		if (result != null && result.isSuccess()) {
			makeExcel(result, request, response);
		}
		return null;
	}
	
	public void makeExcel(DeptAccountTypeBankMessageResult result, HttpServletRequest request,
							HttpServletResponse response) {
		ReportDataResult dataResult = makeResult(result);
		ReportTemplate reportTemplate = new ReportTemplate();
		reportTemplate.setReportName("资金明细表");
		//		//多行表头
		//		String[][] head = new String[2][3];
		//		head[0] = new String[] { "大类", "大类", "数据" };
		//		head[1] = new String[] { "类型", "名称", "数据" };
		//		reportTemplate.setColHeadString(head);
		//		
		reportTemplate.setMergeRow(true);
		reportTemplate.setMergeColCount(2);
		TableBuilder builder = new TableBuilder(dataResult, reportTemplate,
			TableBuilder.Table_Option_Excel, false);
		builder.init();
		builder.dataBind(request, response);
		//System.out.print(builder.getString());
	}
	
	private static ReportDataResult makeResult(DeptAccountTypeBankMessageResult result) {
		List<DeptAccountTypeBankMessageInfo> banks = result.getList();
		
		ReportDataResult dataResult = new ReportDataResult();
		// 头
		dataResult.getFcsFields().add(new FcsField("company", banks.get(0).getCompany(), null));
		dataResult.getFcsFields().add(
			new FcsField("type", banks.get(0).getAccountBanks().get(0).getAccountType(), null));
		dataResult.getFcsFields().add(
			new FcsField("bank", banks.get(0).getAccountBanks().get(0).getBankMessages().get(0)
				.getBankName(), null));
		dataResult.getFcsFields().add(
			new FcsField("account", banks.get(0).getAccountBanks().get(0).getBankMessages().get(0)
				.getAccountNo(), null));
		dataResult.getFcsFields().add(
			new FcsField("date", banks.get(0).getAccountBanks().get(0).getBankMessages().get(0)
				.getShowAmount(), null));
		
		List<DataListItem> dataListItems = Lists.newArrayList();
		// 信息
		for (int i = 1; i < banks.size(); i++) {
			DeptAccountTypeBankMessageInfo info = banks.get(i);
			for (AccountTypeBankMessageInfo typeBank : info.getAccountBanks()) {
				for (BankMessageInfo bank : typeBank.getBankMessages()) {
					DataListItem item = new DataListItem();
					List<ReportItem> valueList = Lists.newArrayList();
					ReportItem reportItem = new ReportItem();
					reportItem.setKey("company");
					reportItem.setValue(info.getCompany());
					reportItem.setDataTypeEnum(DataTypeEnum.STRING);
					valueList.add(reportItem);
					
					reportItem = new ReportItem();
					reportItem.setKey("type");
					reportItem.setValue(typeBank.getAccountType());
					reportItem.setDataTypeEnum(DataTypeEnum.STRING);
					valueList.add(reportItem);
					
					reportItem = new ReportItem();
					reportItem.setKey("bank");
					reportItem.setValue(bank.getBankName());
					reportItem.setDataTypeEnum(DataTypeEnum.STRING);
					valueList.add(reportItem);
					
					reportItem = new ReportItem();
					reportItem.setKey("account");
					reportItem.setValue(bank.getAccountNo());
					reportItem.setDataTypeEnum(DataTypeEnum.STRING);
					valueList.add(reportItem);
					
					reportItem = new ReportItem();
					reportItem.setKey("date");
					reportItem.setValue(bank.getShowAmount());
					reportItem.setDataTypeEnum(DataTypeEnum.STRING);
					valueList.add(reportItem);
					item.setValueList(valueList);
					dataListItems.add(item);
				}
			}
		}
		
		//		for (int i = 0; i < lists.size(); i++) {
		//			DataListItem item = new DataListItem();
		//			List<ReportItem> valueList = Lists.newArrayList();
		//			for (int j = 0; j <= result.getRows(); j++) {
		//				ReportItem reportItem = new ReportItem();
		//				reportItem.setKey("name" + j);
		//				reportItem.setValue(lists.get(i).get(j));
		//				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
		//				valueList.add(reportItem);
		//			}
		//			item.setValueList(valueList);
		//			dataListItems.add(item);
		//		}
		dataResult.setDataList(dataListItems);
		return dataResult;
	}
}
