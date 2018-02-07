/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午5:43:04 创建
 */
package com.born.fcs.face.web.controller.fund;

import java.util.Calendar;
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
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.fm.ws.info.payment.OfficialCardInfo;
import com.born.fcs.fm.ws.order.payment.OfficialCardQueryOrder;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.field.DataTypeEnum;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;
import com.born.fcs.pm.ws.service.report.result.ReportItem;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 公务卡报销明细表
 * 
 * @author lzb
 * 
 */
@Controller
@RequestMapping("fundMg/report")
public class FundReportController extends BaseController {
	final static String vm_path = "/fundMg/fundMgModule/basisDataManage/";
	
	/**
	 * 列表查询
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("officardlist.htm")
	public String officardlist(HttpServletRequest request, Model model) {
		buildSystemNameDefaultUrl(request, model);
		OfficialCardQueryOrder order = new OfficialCardQueryOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		//setSessionLocalInfo2Order(order);
		
		String period = request.getParameter("period");
		if (period == null) {
			period = com.yjf.common.lang.util.DateUtil.dtSimpleYmFormat(new Date());
		}
		boolean isFromQuery = StringUtil.equals(request.getParameter("from"), "query");
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
		
		if (!isFromQuery && StringUtil.isBlank(order.getApplyTimeStart())
			&& StringUtil.isBlank(order.getApplyTimeEnd())) {
			Calendar caneldar = Calendar.getInstance();
			caneldar.set(Calendar.DAY_OF_MONTH, 1);
			Date startDate = DateUtil.getStartTimeOfTheDate(caneldar.getTime());
			order.setApplyTimeStart(DateUtil.simpleFormat(startDate));
			orgTimeStart = DateUtil.dtSimpleFormat(startDate);
			caneldar.set(Calendar.DAY_OF_MONTH, caneldar.getActualMaximum(Calendar.DAY_OF_MONTH));
			Date endDate = DateUtil.getEndTimeOfTheDate(caneldar.getTime());
			order.setApplyTimeEnd(DateUtil.simpleFormat(endDate));
			orgTimeEnd = DateUtil.dtSimpleFormat(endDate);
		}
		
		if (StringUtil.isEmpty(order.getSortCol())) {
			order.setSortCol("raw_add_time");
			order.setSortOrder("DESC");
		}
		
		if (DataPermissionUtil.isFinancialPersonnel() || DataPermissionUtil.isSystemAdministrator()
			|| DataPermissionUtil.isCompanyLeader()) {
		} else {
			long curUserId = ShiroSessionUtils.getSessionLocal().getUserId();
			order.setCurUserId(curUserId);
		}
		
		order.setPageSize(9999);
		QueryBaseBatchResult<OfficialCardInfo> batchResult2 = reportFormAnalysisServiceClient
			.queryOfficialCards(order);
		if (batchResult2.getPageList() != null) {
			Money allMaony = Money.zero();
			for (OfficialCardInfo ainfo : batchResult2.getPageList()) {
				allMaony = allMaony.add(ainfo.getAmount());
			}
			model.addAttribute("allMaony", allMaony);
		}
		order.setApplyTimeStart(orgTimeStart);
		order.setApplyTimeEnd(orgTimeEnd);
		model.addAttribute("conditions", order);
		model.addAttribute("period", period);
		model.addAttribute("statusList", FormStatusEnum.getAllEnum());
		model.addAttribute("page", PageUtil.getCovertPage(batchResult2));
		return vm_path + "officardlist.vm";
	}
	
	/**
	 * 列表查询后导出
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("cardDownLoad.htm")
	public String cardDownLoad(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		buildSystemNameDefaultUrl(request, model);
		OfficialCardQueryOrder order = new OfficialCardQueryOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		setSessionLocalInfo2Order(order);
		
		String period = request.getParameter("period");
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
		}
		if (StringUtil.isEmpty(order.getSortCol())) {
			order.setSortCol("raw_add_time");
			order.setSortOrder("DESC");
		}
		QueryBaseBatchResult<OfficialCardInfo> batchResult = reportFormAnalysisServiceClient
			.queryOfficialCards(order);
		List<OfficialCardInfo> infos = batchResult.getPageList();
		if (infos != null) {
			makeExcel(infos, request, response);
		}
		return null;
		//		Map<String, Object> header = new HashMap<String, Object>();
		//		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		//		if (ListUtil.isNotEmpty(infos)) {
		//			Money allAmount = Money.zero();
		//			for (OfficialCardInfo info : infos) {
		//				Map<String, Object> map = new HashMap<>();
		//				// 转换为模版需要的map
		//				map = MiscUtil.covertPoToMap(info);
		//				if (info.getAmount() != null) {
		//					allAmount = allAmount.add(info.getAmount());
		//					map.put("amount", info.getAmount().toStandardString());
		//				}
		//				if (info.getSubmitTime() != null) {
		//					map.put("submitTime", DateUtil.dtSimpleFormat(info.getSubmitTime()));
		//				}
		//				if (info.getFinishTime() != null) {
		//					map.put("finishTime", DateUtil.dtSimpleFormat(info.getFinishTime()));
		//				}
		//				if (info.getVoucherTime() != null) {
		//					map.put("voucherTime", DateUtil.dtSimpleFormat(info.getVoucherTime()));
		//				}
		//				dataList.add(map);
		//			}
		//			header.put("allAmount", allAmount.toStandardString());
		//		}
		//		ExcelExportUtil.exportExcelFile(request, "officardlist.xls", response, header, dataList,
		//			"officardlistDownload");
		//		return null;
	}
	
	public void makeExcel(List<OfficialCardInfo> infos, HttpServletRequest request,
							HttpServletResponse response) {
		ReportDataResult dataResult = makeResult(infos);
		ReportTemplate reportTemplate = new ReportTemplate();
		reportTemplate.setReportName("公务卡报销明细表");
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
	
	private static ReportDataResult makeResult(List<OfficialCardInfo> infos) {
		List<OfficialCardInfo> financial = infos;
		
		ReportDataResult dataResult = new ReportDataResult();
		// 头
		dataResult.getFcsFields().add(new FcsField("index", "序号", null));
		dataResult.getFcsFields().add(new FcsField("billNo", "报销单号", null));
		dataResult.getFcsFields().add(new FcsField("category", "费用种类", null));
		dataResult.getFcsFields().add(new FcsField("userName", "报销人", null));
		dataResult.getFcsFields().add(new FcsField("deptName", "报销部门", null));
		dataResult.getFcsFields().add(
			new FcsField("amount", "公务卡支付金额（元）", null, DataTypeEnum.MONEY));
		dataResult.getFcsFields().add(new FcsField("submitTime", "申请日期", null));
		dataResult.getFcsFields().add(new FcsField("finishTime", "审核通过时间", null));
		dataResult.getFcsFields().add(new FcsField("voucherTime", "过账时间", null));
		
		List<DataListItem> dataListItems = Lists.newArrayList();
		Money totalAmount = Money.zero();
		// 信息
		for (int i = 0; i < financial.size() + 1; i++) {
			
			ReportItem reportItem = new ReportItem();
			List<ReportItem> valueList = Lists.newArrayList();
			DataListItem item = new DataListItem();
			if (i != financial.size()) {
				OfficialCardInfo info = financial.get(i);
				totalAmount.addTo(info.getAmount());
				reportItem = new ReportItem();
				reportItem.setKey("index");
				reportItem.setValue((i + 1) + "");
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("billNo");
				reportItem.setValue(info.getBillNo());
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("category");
				reportItem.setValue(info.getCategory());
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("userName");
				reportItem.setValue(info.getUserName());
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("deptName");
				reportItem.setValue(info.getDeptName());
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("amount");
				reportItem.setValue(info.getAmount().toStandardString());
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("submitTime");
				reportItem.setValue(DateUtil.dtSimpleFormat(info.getSubmitTime()));
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("finishTime");
				reportItem.setValue(DateUtil.dtSimpleFormat(info.getFinishTime()));
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("voucherTime");
				reportItem.setValue(DateUtil.dtSimpleFormat(info.getVoucherTime()));
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				item.setValueList(valueList);
				dataListItems.add(item);
			} else {
				reportItem = new ReportItem();
				reportItem.setKey("billNo");
				reportItem.setValue("合计");
				reportItem.setDataTypeEnum(DataTypeEnum.STRING);
				valueList.add(reportItem);
				
				reportItem = new ReportItem();
				reportItem.setKey("amount");
				reportItem.setValue(totalAmount.toStandardString());
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
