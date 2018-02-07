/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午3:09:17 创建
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
import com.born.fcs.fm.ws.order.payment.ReportAccountDetailOrder;
import com.born.fcs.fm.ws.result.report.AccountDetailResult;
import com.born.fcs.pm.util.MoneyUtil;
import com.born.fcs.pm.ws.service.report.field.DataTypeEnum;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;
import com.born.fcs.pm.ws.service.report.result.ReportItem;

/**
 * 
 * 资金状况表
 * @author hjiajie
 * 
 */
@Controller
@RequestMapping("fundMg/report")
public class FundAccountDetailController extends BaseController {
	final static String vm_path = "/fundMg/fundMgModule/reportAnalyze/";
	
	/**
	 * 查询
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("accountDetail.htm")
	public String accountDetail(ReportAccountDetailOrder order, HttpServletRequest request,
								Model model) {
		AccountDetailResult result = reportFormAnalysisServiceClient.accountDetail(order);
		if (result.isExecuted() && result.isSuccess()) {
			List<List<String>> lists = new ArrayList<List<String>>();
			
			lists.add(result.getIntitle());
			lists.add(result.getInyjqckyje());
			lists.add(result.getInqtck());
			lists.add(result.getInccdbbzj());
			lists.add(result.getInwtdk());
			lists.add(result.getIndqlc());
			lists.add(result.getInzcqlc());
			lists.add(result.getInnbzjcj());
			lists.add(result.getIndcksh());
			lists.add(result.getInqt());
			lists.add(result.getInzjlrhj());
			
			lists.add(result.getOutzjzfxm());
			lists.add(result.getOutqtck());
			lists.add(result.getOutccdbbzj());
			lists.add(result.getOutwtdk());
			lists.add(result.getOutdqlc());
			lists.add(result.getOutzcqlc());
			lists.add(result.getOutnbzjcj());
			lists.add(result.getOutdcksh());
			lists.add(result.getOutqt());
			lists.add(result.getOutjhykhj());
			lists.add(result.getYjqmky());
			
			model.addAttribute("result", result);
			model.addAttribute("lists", lists);
			
			//			if (result != null && ListUtil.isNotEmpty(result.getPageList())) {
			//				model.addAttribute("size", result.getPageList().get(0).size());
			//			}
		}
		model.addAttribute("conditions", order);
		return vm_path + "fundStateDetail.vm";
	}
	
	/***
	 * 导出
	 */
	@RequestMapping("accountDetailExport.htm")
	public String accountDetailExport(ReportAccountDetailOrder order, HttpServletRequest request,
										HttpServletResponse response, Model model) {
		AccountDetailResult result = reportFormAnalysisServiceClient.accountDetail(order);
		makeExcel(result, request, response);
		return null;
	}
	
	public void makeExcel(AccountDetailResult result, HttpServletRequest request,
							HttpServletResponse response) {
		ReportDataResult dataResult = makeResult(result);
		ReportTemplate reportTemplate = new ReportTemplate();
		reportTemplate.setReportName("资金状况表(单位:元)");
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
		//System.out.print(builder.getString());
	}
	
	private static ReportDataResult makeResult(AccountDetailResult result) {
		List<List<String>> lists = new ArrayList<List<String>>();
		
		//		lists.add(result.getIntitle());
		List<String> intitles = result.getIntitle();
		lists.add(result.getInyjqckyje());
		lists.add(result.getInqtck());
		lists.add(result.getInccdbbzj());
		lists.add(result.getInwtdk());
		lists.add(result.getIndqlc());
		lists.add(result.getInzcqlc());
		lists.add(result.getInnbzjcj());
		lists.add(result.getIndcksh());
		lists.add(result.getInqt());
		lists.add(result.getInzjlrhj());
		
		lists.add(result.getOutzjzfxm());
		lists.add(result.getOutqtck());
		lists.add(result.getOutccdbbzj());
		lists.add(result.getOutwtdk());
		lists.add(result.getOutdqlc());
		lists.add(result.getOutzcqlc());
		lists.add(result.getOutnbzjcj());
		lists.add(result.getOutdcksh());
		lists.add(result.getOutqt());
		lists.add(result.getOutjhykhj());
		lists.add(result.getYjqmky());
		
		ReportDataResult dataResult = new ReportDataResult();
		// 头
		for (int i = 0; i <= result.getRows(); i++) {
			dataResult.getFcsFields().add(new FcsField("name" + i, intitles.get(i), null));
		}
		List<DataListItem> dataListItems = Lists.newArrayList();
		// 信息
		for (int i = 0; i < lists.size(); i++) {
			DataListItem item = new DataListItem();
			List<ReportItem> valueList = Lists.newArrayList();
			for (int j = 0; j <= result.getRows(); j++) {
				ReportItem reportItem = new ReportItem();
				reportItem.setKey("name" + j);
				reportItem.setValue(MoneyUtil.getMoneyStrW((String) lists.get(i).get(j)));
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
			}
			item.setValueList(valueList);
			dataListItems.add(item);
		}
		dataResult.setDataList(dataListItems);
		return dataResult;
	}
	
}
