/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午5:44:40 创建
 */
package com.born.fcs.face.web.controller.fund;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.born.fcs.pm.util.RateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.born.face.webui.table.ReportTemplate;
import com.born.face.webui.table.TableBuilder;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.fm.ws.order.report.ProjectDepositQueryOrder;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目保证金明细
 * @author wuzj
 */
@Controller
@RequestMapping("fundMg/report")
public class FundProjectDepositController extends BaseController {
	
	final static String vm_path = "/fundMg/fundMgModule/reportAnalyze/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "startTime", "endTime" };
	}

	@RequestMapping("projectDepositPaid.htm")
	public String projectDepositPaid(ProjectDepositQueryOrder order, HttpServletRequest request,
										Model model) {
		if (order == null)
			order = new ProjectDepositQueryOrder();
		order.setOut(true);
		model.addAttribute("isOut", true);
		return projectDeposit(order, request, model);
	}

	/***
	 * 导出
	 */
	@RequestMapping("projectDepositPaidExport.htm")
	public String projectDepositPaidExport(ProjectDepositQueryOrder order,
											HttpServletRequest request,
											HttpServletResponse response, Model model) {
		if (order == null)
			order = new ProjectDepositQueryOrder();
		order.setOut(true);
		model.addAttribute("isOut", true);
		return projectDepositExport(order, request, response, model);
	}

	/**
	 * 查询
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("projectDeposit.htm")
	public String projectDeposit(ProjectDepositQueryOrder order, HttpServletRequest request,
									Model model) {

		if (order == null)
			order = new ProjectDepositQueryOrder();

		String selectType = request.getParameter("selectType");
		if (StringUtil.isBlank(selectType))
			selectType = "thisMonth";

		if (order.getStartTime() != null) {
			order.setStartTime(DateUtil.getStartTimeOfTheDate(order.getStartTime()));
			selectType = null;
		}
		if (order.getEndTime() != null) {
			order.setEndTime(DateUtil.getEndTimeOfTheDate(order.getEndTime()));
			selectType = null;
		}

		boolean fromSelectType = false;
		if (StringUtil.isNotBlank(selectType)) {
			Date startDate = null;
			Date endDate = null;
			Calendar calendar = Calendar.getInstance();
			if ("thisMonth".equals(selectType)) {
				calendar.set(Calendar.DAY_OF_MONTH,
					calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				endDate = DateUtil.getEndTimeOfTheDate(calendar.getTime());
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				startDate = DateUtil.getStartTimeOfTheDate(calendar.getTime());
			} else if ("thisSeason".equals(selectType)) {
				startDate = DateUtil.getCurrentQuarterStartTime();
				calendar.setTime(DateUtil.getCurrentQuarterStartTime());
				calendar.add(Calendar.MONTH, 2);
				calendar.set(Calendar.DAY_OF_MONTH,
					calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				endDate = DateUtil.getEndTimeOfTheDate(calendar.getTime());
			} else if ("thisYear".equals(selectType)) {
				calendar.set(Calendar.MONTH, 11);
				calendar.set(Calendar.DAY_OF_MONTH, 31);
				endDate = DateUtil.getEndTimeOfTheDate(calendar.getTime());
				calendar.set(Calendar.MONTH, 0);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				startDate = DateUtil.getStartTimeOfTheDate(calendar.getTime());
			}
			if (startDate != null)
				order.setStartTime(startDate);
			if (endDate != null)
				order.setEndTime(endDate);
			fromSelectType = true;
		}

		model.addAttribute("selectType", selectType);
		ReportDataResult result = reportFormAnalysisServiceClient.getProjectDepositDetail(order);
		if (result != null && result.isSuccess()) {
			model.addAttribute("result", result);
			Money all = Money.zero();
			// item.get("");
			List<List<String>> pages = new ArrayList<List<String>>();
			List<DataListItem> items = result.getDataList();
			if (items != null) {
				for (DataListItem item : items) {
					List<String> strs = new ArrayList<String>();
					HashMap<String, Object> data = item.getMap();
					if (data != null) {
						String str = (String) data.get("project_code");
						strs.add(str);
						str = (String) data.get("project_name");
						strs.add(str);
						str = (String) data.get("customer_name");
						strs.add(str);
						str = (String) data.get("busi_type_name");
						strs.add(str);
						str = (String) data.get("busi_manager_name");
						strs.add(str);
						str = (String) data.get("dept_name");
						strs.add(str);
						Money amount = (Money) data.get("pay_amount");
						strs.add(amount.toStandardString());
						Date date = (Date) data.get("pay_time");
						strs.add(DateUtil.dtSimpleFormat(date));
						all.addTo(amount);
						pages.add(strs);
					}
				}
			}
			model.addAttribute("pages", pages);
			if (all.getCent() > 0)
				model.addAttribute("all", all.toStandardString());
		}

		if (fromSelectType) {
			order.setStartTime(null);
			order.setEndTime(null);
		}
		model.addAttribute("queryOrder", order);
		model.addAttribute("busiTypeName", request.getParameter("busiTypeName"));

		return vm_path + "projectCashDepositDetail.vm";
	}

	/***
	 * 导出
	 */
	@RequestMapping("projectDepositExport.htm")
	public String projectDepositExport(ProjectDepositQueryOrder order, HttpServletRequest request,
										HttpServletResponse response, Model model) {

		if (order == null)
			order = new ProjectDepositQueryOrder();

		String selectType = request.getParameter("selectType");
		if (StringUtil.isBlank(selectType))
			selectType = "thisMonth";

		if (order.getStartTime() != null) {
			order.setStartTime(DateUtil.getStartTimeOfTheDate(order.getStartTime()));
			selectType = null;
		}
		if (order.getEndTime() != null) {
			order.setEndTime(DateUtil.getEndTimeOfTheDate(order.getEndTime()));
			selectType = null;
		}

		if (StringUtil.isNotBlank(selectType)) {
			Date startDate = null;
			Date endDate = null;
			Calendar calendar = Calendar.getInstance();
			if ("thisMonth".equals(selectType)) {
				calendar.set(Calendar.DAY_OF_MONTH,
					calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				endDate = DateUtil.getEndTimeOfTheDate(calendar.getTime());
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				startDate = DateUtil.getStartTimeOfTheDate(calendar.getTime());
			} else if ("thisSeason".equals(selectType)) {
				startDate = DateUtil.getCurrentQuarterStartTime();
				calendar.setTime(DateUtil.getCurrentQuarterStartTime());
				calendar.add(Calendar.MONTH, 2);
				calendar.set(Calendar.DAY_OF_MONTH,
					calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				endDate = DateUtil.getEndTimeOfTheDate(calendar.getTime());
			} else if ("thisYear".equals(selectType)) {
				calendar.set(Calendar.MONTH, 11);
				calendar.set(Calendar.DAY_OF_MONTH, 31);
				endDate = DateUtil.getEndTimeOfTheDate(calendar.getTime());
				calendar.set(Calendar.MONTH, 0);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				startDate = DateUtil.getStartTimeOfTheDate(calendar.getTime());
			}
			if (startDate != null)
				order.setStartTime(startDate);
			if (endDate != null)
				order.setEndTime(endDate);
		}

		ReportDataResult result = reportFormAnalysisServiceClient.getProjectDepositDetail(order);

		// 添加总计
		Money all = Money.zero();
		List<DataListItem> items = result.getDataList();
		if (ListUtil.isNotEmpty(items)) {
			for (DataListItem item : items) {
				Money mon = (Money) item.getMap().get("pay_amount");
				Date date = (Date) item.getMap().get("pay_time");
				item.getMap().put("pay_time", DateUtil.dtSimpleFormat(date));
				all.addTo(mon);
			}

			DataListItem item = new DataListItem();
			item.getMap().put("pay_amount", all.toStandardString());
			item.getMap().put("project_code", "总计");
			result.getDataList().add(item);
		} else {
			result.setDataList(new ArrayList<DataListItem>());
		}
		ReportTemplate reportTemplate = new ReportTemplate();
		if (order.isOut()) {
			reportTemplate.setReportName("存出保证金明细表");
		} else {
			reportTemplate.setReportName("存入保证金明细表");
		}
		TableBuilder builder = new TableBuilder(result, reportTemplate,
			TableBuilder.Table_Option_Excel, false);
		builder.init();
		builder.dataBind(request, response);
		model.addAttribute("busiTypeName", request.getParameter("busiTypeName"));
		return null;
	}




	/**
	 * 利息计提导出查询
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("projectDepositPaidInterest.htm")
	public String projectDepositPaidInterest(ProjectDepositQueryOrder order, HttpServletRequest request,
									 Model model) {
		if (order == null)
			order = new ProjectDepositQueryOrder();
		order.setOut(true);
		model.addAttribute("isOut", true);
		return projectDepositInterest(order, request, model);
	}

	/***
	 * 利息计提导出
	 */
	@RequestMapping("projectDepositPaidInterestExport.htm")
	public String projectDepositPaidInterestExport(ProjectDepositQueryOrder order,
										   HttpServletRequest request,
										   HttpServletResponse response, Model model) {
		if (order == null)
			order = new ProjectDepositQueryOrder();
		order.setOut(true);
		model.addAttribute("isOut", true);
		return projectDepositInterestExport(order, request, response, model);
	}

	/**
	 * 利息计提查询
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("projectDepositInterest.htm")
	public String projectDepositInterest(ProjectDepositQueryOrder order, HttpServletRequest request,
								 Model model) {

		if (order == null)
			order = new ProjectDepositQueryOrder();

		String selectType = request.getParameter("selectType");
		if (StringUtil.isBlank(selectType))
			selectType = "thisMonth";

		if (order.getStartTime() != null) {
			order.setStartTime(DateUtil.getStartTimeOfTheDate(order.getStartTime()));
			selectType = null;
		}
		if (order.getEndTime() != null) {
			order.setEndTime(DateUtil.getEndTimeOfTheDate(order.getEndTime()));
			selectType = null;
		}

		boolean fromSelectType = false;
		if (StringUtil.isNotBlank(selectType)) {
			Date startDate = null;
			Date endDate = null;
			Calendar calendar = Calendar.getInstance();
			if ("thisMonth".equals(selectType)) {
				calendar.set(Calendar.DAY_OF_MONTH,
						calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				endDate = DateUtil.getEndTimeOfTheDate(calendar.getTime());
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				startDate = DateUtil.getStartTimeOfTheDate(calendar.getTime());
			} else if ("thisSeason".equals(selectType)) {
				startDate = DateUtil.getCurrentQuarterStartTime();
				calendar.setTime(DateUtil.getCurrentQuarterStartTime());
				calendar.add(Calendar.MONTH, 2);
				calendar.set(Calendar.DAY_OF_MONTH,
						calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				endDate = DateUtil.getEndTimeOfTheDate(calendar.getTime());
			} else if ("thisYear".equals(selectType)) {
				calendar.set(Calendar.MONTH, 11);
				calendar.set(Calendar.DAY_OF_MONTH, 31);
				endDate = DateUtil.getEndTimeOfTheDate(calendar.getTime());
				calendar.set(Calendar.MONTH, 0);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				startDate = DateUtil.getStartTimeOfTheDate(calendar.getTime());
			}
			if (startDate != null)
				order.setStartTime(startDate);
			if (endDate != null)
				order.setEndTime(endDate);
			fromSelectType = true;
		}

		model.addAttribute("selectType", selectType);
		ReportDataResult result = reportFormAnalysisServiceClient.getProjectDepositInterest(order);
		if (result != null && result.isSuccess()) {
			model.addAttribute("result", result);
			Money all = Money.zero();
			Money leftAmountAll=Money.zero();
			Money accruedInterestAll=Money.zero();
			List<List<String>> pages = new ArrayList<List<String>>();
			List<DataListItem> items = result.getDataList();
			if (items != null) {
				for (DataListItem item : items) {
					List<String> strs = new ArrayList<String>();
					HashMap<String, Object> data = item.getMap();
					if (data != null) {
						String str = (String) data.get("project_code");
						strs.add(str);
						str = (String) data.get("project_name");
						strs.add(str);
						str = (String) data.get("customer_name");
						strs.add(str);
						str = (String) data.get("busi_type_name");
						strs.add(str);
						str = (String) data.get("busi_manager_name");
						strs.add(str);
						str = (String) data.get("dept_name");
						strs.add(str);
						Date date = (Date) data.get("pay_time");
						strs.add(DateUtil.dtSimpleFormat(date));
						Money amount = (Money) data.get("pay_amount");
						strs.add(amount.toStandardString());
						Money leftAmount = (Money) data.get("left_amount");
						strs.add(leftAmount.toStandardString());
						String marginRate = RateUtil.formatRateStr((String) data.get("margin_rate"));
						strs.add(marginRate);
						String period = (String) data.get("period");
						strs.add(period);
						Money accruedInterest = (Money) data.get("accrued_interest");
						strs.add(accruedInterest.toStandardString());
						all.addTo(amount);
						leftAmountAll.addTo(leftAmount);
						accruedInterestAll.addTo(accruedInterest);
						pages.add(strs);
					}
				}
			}
			model.addAttribute("pages", pages);
			if (all.getCent() > 0)
				model.addAttribute("all", all.toStandardString());
				model.addAttribute("leftAmountAll", leftAmountAll.toStandardString());
				model.addAttribute("accruedInterestAll", accruedInterestAll.toStandardString());
		}

		if (fromSelectType) {
			order.setStartTime(null);
			order.setEndTime(null);
		}
		model.addAttribute("queryOrder", order);
		model.addAttribute("busiTypeName", request.getParameter("busiTypeName"));

		return vm_path + "projectCashDepositInterest.vm";
	}

	/***
	 * 利息计提导出
	 */
	@RequestMapping("projectDepositInterestExport.htm")
	public String projectDepositInterestExport(ProjectDepositQueryOrder order, HttpServletRequest request,
									   HttpServletResponse response, Model model) {

		if (order == null)
			order = new ProjectDepositQueryOrder();

		String selectType = request.getParameter("selectType");
		if (StringUtil.isBlank(selectType))
			selectType = "thisMonth";

		if (order.getStartTime() != null) {
			order.setStartTime(DateUtil.getStartTimeOfTheDate(order.getStartTime()));
			selectType = null;
		}
		if (order.getEndTime() != null) {
			order.setEndTime(DateUtil.getEndTimeOfTheDate(order.getEndTime()));
			selectType = null;
		}

		if (StringUtil.isNotBlank(selectType)) {
			Date startDate = null;
			Date endDate = null;
			Calendar calendar = Calendar.getInstance();
			if ("thisMonth".equals(selectType)) {
				calendar.set(Calendar.DAY_OF_MONTH,
						calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				endDate = DateUtil.getEndTimeOfTheDate(calendar.getTime());
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				startDate = DateUtil.getStartTimeOfTheDate(calendar.getTime());
			} else if ("thisSeason".equals(selectType)) {
				startDate = DateUtil.getCurrentQuarterStartTime();
				calendar.setTime(DateUtil.getCurrentQuarterStartTime());
				calendar.add(Calendar.MONTH, 2);
				calendar.set(Calendar.DAY_OF_MONTH,
						calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				endDate = DateUtil.getEndTimeOfTheDate(calendar.getTime());
			} else if ("thisYear".equals(selectType)) {
				calendar.set(Calendar.MONTH, 11);
				calendar.set(Calendar.DAY_OF_MONTH, 31);
				endDate = DateUtil.getEndTimeOfTheDate(calendar.getTime());
				calendar.set(Calendar.MONTH, 0);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				startDate = DateUtil.getStartTimeOfTheDate(calendar.getTime());
			}
			if (startDate != null)
				order.setStartTime(startDate);
			if (endDate != null)
				order.setEndTime(endDate);
		}

		ReportDataResult result = reportFormAnalysisServiceClient.getProjectDepositInterest(order);

		// 添加总计
		Money all = Money.zero();
		Money leftAmount=Money.zero();
		Money accruedInterest=Money.zero();
		List<DataListItem> items = result.getDataList();
		if (ListUtil.isNotEmpty(items)) {
			for (DataListItem item : items) {
				Money mon = (Money) item.getMap().get("pay_amount");
				Money left = (Money) item.getMap().get("left_amount");
				Money interest = (Money) item.getMap().get("accrued_interest");
				Date date = (Date) item.getMap().get("pay_time");
				String rate= RateUtil.formatRateStr((String)item.getMap().get("margin_rate"));
				item.getMap().put("margin_rate", rate);
				item.getMap().put("pay_time", DateUtil.dtSimpleFormat(date));
				all.addTo(mon);
				leftAmount.addTo(left);
				accruedInterest.addTo(interest);
				}

			DataListItem item = new DataListItem();
			item.getMap().put("pay_amount", all.toStandardString());
			item.getMap().put("left_amount", leftAmount.toStandardString());
			item.getMap().put("accrued_interest", accruedInterest.toStandardString());
			item.getMap().put("project_code", "总计");
			result.getDataList().add(item);
		} else {
			result.setDataList(new ArrayList<DataListItem>());
		}
		ReportTemplate reportTemplate = new ReportTemplate();
		if (order.isOut()) {
			reportTemplate.setReportName("存出保证金利息计提表");
		} else {
			reportTemplate.setReportName("存入保证金利息计提表");
		}
		TableBuilder builder = new TableBuilder(result, reportTemplate,
				TableBuilder.Table_Option_Excel, false);
		builder.init();
		builder.dataBind(request, response);
		model.addAttribute("busiTypeName", request.getParameter("busiTypeName"));
		return null;
	}


}
