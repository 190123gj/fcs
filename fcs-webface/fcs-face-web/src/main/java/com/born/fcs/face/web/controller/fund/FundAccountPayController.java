/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午5:44:40 创建
 */
package com.born.fcs.face.web.controller.fund;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.face.webui.table.ReportTemplate;
import com.born.face.webui.table.TableBuilder;
import com.born.fcs.face.integration.bpm.service.WorkflowEngineWebClient;
import com.born.fcs.face.integration.bpm.service.info.WorkflowProcessLog;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.controller.fund.info.FundAccountPayInfo;
import com.born.fcs.fm.ws.enums.AccountStatusEnum;
import com.born.fcs.fm.ws.enums.CostCategoryStatusEnum;
import com.born.fcs.fm.ws.enums.CostDirectionEnum;
import com.born.fcs.fm.ws.info.payment.CostCategoryInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationInfo;
import com.born.fcs.fm.ws.info.payment.FormLabourCapitalDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormLabourCapitalInfo;
import com.born.fcs.fm.ws.info.payment.FormTravelExpenseInfo;
import com.born.fcs.fm.ws.order.payment.AccountPayQueryOrder;
import com.born.fcs.fm.ws.order.payment.CostCategoryQueryOrder;
import com.born.fcs.fm.ws.order.payment.ExpenseApplicationQueryOrder;
import com.born.fcs.fm.ws.order.payment.LabourCapitalQueryOrder;
import com.born.fcs.fm.ws.order.payment.TravelExpenseQueryOrder;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 财务支付打款明细表
 * @author hjiajie
 * 
 */
@Controller
@RequestMapping("fundMg/report")
public class FundAccountPayController extends BaseController {
	
	final static String vm_path = "/fundMg/fundMgModule/reportAnalyze/";
	
	@Autowired
	WorkflowEngineWebClient workflowEngineWebClient;
	
	@Override
	protected String[] getDateInputNameArray() {
		return new String[] { "applyTimeStart", "applyTimeEnd", "voucherSyncFinishTimeStart",
								"voucherSyncFinishTimeEnd" };
	}
	
	@Override
	protected String[] getMoneyInputNameArray() {
		return new String[] { "amountStart", "amountEnd" };
	}
	
	/**
	 * 查询
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("accountPay.htm")
	public String accountPay(AccountPayQueryOrder order, HttpServletRequest request, Model model) {
		if (null != order && null == order.getAccountStatus()) {
			order.setAccountStatus(AccountStatusEnum.WAIT_PAY);
		}
		// 20170616 8.支付明细表去掉报表期间，最大显示500条
		/*String period = request.getParameter("period");
		if (period == null) {
			period = com.yjf.common.lang.util.DateUtil.dtSimpleYmFormat(new Date());
			// 20170116 初始查询条件为 单据状态待付款
			order.setAccountStatus(AccountStatusEnum.WAIT_PAY);
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
		}*/
		// 获取类型
		String expenseType = request.getParameter("expenseType");
		model.addAttribute("expenseType", expenseType);
		String expenseName = request.getParameter("expenseName");
		model.addAttribute("expenseName", expenseName);
		if (StringUtil.isNotBlank(expenseType)) {
			List<String> expenses = new ArrayList<>();
			String[] strs = expenseType.split(",");
			for (String str : strs) {
				expenses.add(str);
			}
			order.setExpenseTypes(expenses);
		}
		ReportDataResult result = reportFormAnalysisServiceClient.getAccountPay(order);
		/*order.setApplyTimeStart(orgTimeStart);
		order.setApplyTimeEnd(orgTimeEnd);*/
		List<CostCategoryInfo> costCategorys = getCategoryList();
		if (result.isExecuted() && result.isSuccess()) {
			
			model.addAttribute("result", result);
			Money all = Money.zero();
			String billNos = "";
			// item.get("");
			List<List<String>> pages = new ArrayList<List<String>>();
			List<DataListItem> items = result.getDataList();
			if (items != null && ListUtil.isNotEmpty(items)) {
				
				for (DataListItem item : items) {
					List<String> strs = new ArrayList<String>();
					String billNo = "";
					String str = (String) item.getMap().get("bill_no");
					billNo = str;
					// 增添billno，billno 格式数据
					if (!billNos.contains(billNo)) {
						if (StringUtil.isNotBlank(billNos)) {
							billNos += ",";
						}
						billNos += billNo;
					}
					strs.add(str);
					
					str = (String) item.getMap().get("voucher_no");
					strs.add(str);
					
					str = (String) item.getMap().get("reason");
					strs.add(str);
					str = (String) item.getMap().get("apply_name");
					strs.add(str);
					str = (String) item.getMap().get("dept_name");
					strs.add(str);
					str = (String) item.getMap().get("payee");
					strs.add(str);
					str = (String) item.getMap().get("bank");
					strs.add(str);
					str = (String) item.getMap().get("bank_account");
					strs.add(str);
					str = (String) item.getMap().get("direction");
					String str2 = (String) item.getMap().get("is_official_card");
					if ("IS".equals(str2)) {
						strs.add("公务卡");
					} else {
						CostDirectionEnum cost = CostDirectionEnum.getByCode(str);
						if (cost != null) {
							strs.add(cost.message());
						} else {
							strs.add(str);
						}
					}
					String expenseTypeStr = "";
					str = (String) item.getMap().get("expense_type");
					String findStr = null;
					for (CostCategoryInfo cost : costCategorys) {
						if ((cost.getCategoryId() > 0 || cost.getCategoryId() == -1)
							&& String.valueOf(cost.getCategoryId()).equals(str)) {
							findStr = cost.getName();
							break;
						}
					}
					if (StringUtil.isNotBlank(findStr)) {
						strs.add(findStr);
						expenseTypeStr = findStr;
					} else {
						strs.add(str);
						expenseTypeStr = str;
					}
					
					Money mon = (Money) item.getMap().get("amount");
					strs.add(mon.toStandardString());
					all.addTo(mon);
					Date date = (Date) item.getMap().get("application_time");
					strs.add(DateUtil.dtSimpleFormat(date));
					date = (Date) item.getMap().get("finish_time");
					strs.add(DateUtil.dtSimpleFormat(date));
					str = (String) item.getMap().get("account_status");
					if (StringUtil.isNotBlank(str)) {
						AccountStatusEnum accountStatus = AccountStatusEnum.getByCode(str);
						if (accountStatus != null) {
							strs.add(accountStatus.message());
						} else {
							strs.add(str);
						}
					} else {
						strs.add(str);
					}
					date = (Date) item.getMap().get("wait_pay_time");
					strs.add(DateUtil.dtSimpleFormat(date));
					
					date = (Date) item.getMap().get("voucher_sync_finish_time");
					strs.add(DateUtil.dtSimpleFormat(date));
					
					str = (String) item.getMap().get("selectId");
					strs.add(str + "&billNo=" + billNo + "&amountStart=" + mon.toString()
								+ "&expenseType=" + expenseTypeStr);
					pages.add(strs);
				}
			}
			JSONArray jsons = new JSONArray();
			for (CostCategoryInfo info : costCategorys) {
				if (StringUtil.equals(info.getName(), "还款"))
					continue;
				JSONObject json = new JSONObject();
				json.put("id", info.getCategoryId());
				json.put("name", info.getName());
				jsons.add(json);
			}
			model.addAttribute("billNos", billNos);
			model.addAttribute("costCategorysJson", jsons.toJSONString().replaceAll("\"", "'"));
			model.addAttribute("costCategorys", costCategorys);
			/*model.addAttribute("period", period);*/
			model.addAttribute("pages", pages);
			model.addAttribute("all", all);
		}
		model.addAttribute("conditions", order);
		return vm_path + "payDetail.vm";
	}
	
	private List<CostCategoryInfo> getCategoryList() {
		CostCategoryQueryOrder order2 = new CostCategoryQueryOrder();
		order2.setStatusList(new ArrayList<CostCategoryStatusEnum>());
		order2.getStatusList().add(CostCategoryStatusEnum.NORMAL);
		order2.setPageSize(10000);
		QueryBaseBatchResult<CostCategoryInfo> batchResult = costCategoryServiceClient
			.queryPage(order2);
		List<CostCategoryInfo> categoryList = new ArrayList<>();
		if (batchResult != null && ListUtil.isNotEmpty(batchResult.getPageList())) {
			categoryList = batchResult.getPageList();
		}
		
		// 差率费换算为-1，避免数据库更定之后数据丢失
		boolean hasCLF = false;
		for (CostCategoryInfo info : batchResult.getPageList()) {
			if (info.getName().equals("差旅费")) {
				info.setCategoryId(-1L);
				hasCLF = true;
				break;
			}
		}
		if (!hasCLF) {
			CostCategoryInfo clInfo = new CostCategoryInfo();
			clInfo.setCategoryId(-1L);
			clInfo.setName("差旅费");
			categoryList.add(clInfo);
		}
		
		return categoryList;
	}
	
	/***
	 * 导出
	 */
	@RequestMapping("accountPayExport.htm")
	public String accountPayExport(AccountPayQueryOrder order, HttpServletRequest request,
									HttpServletResponse response, Model model) {
		if (null != order && null == order.getAccountStatus()) {
			order.setAccountStatus(AccountStatusEnum.WAIT_PAY);
		}
		/*String period = request.getParameter("period");
		if (period == null) {
			period = com.yjf.common.lang.util.DateUtil.dtSimpleYmFormat(new Date());
		}
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
			
		}*/
		// 获取类型
		String expenseType = request.getParameter("expenseType");
		model.addAttribute("expenseType", expenseType);
		String expenseName = request.getParameter("expenseName");
		model.addAttribute("expenseName", expenseName);
		if (StringUtil.isNotBlank(expenseType)) {
			List<String> expenses = new ArrayList<>();
			String[] strs = expenseType.split(",");
			for (String str : strs) {
				expenses.add(str);
			}
			order.setExpenseTypes(expenses);
		}
		ReportDataResult result = reportFormAnalysisServiceClient.getAccountPay(order);
		// 添加总计
		
		Money all = Money.zero();
		// item.get("");
		List<DataListItem> items = result.getDataList();
		if (items != null && ListUtil.isNotEmpty(items)) {
			List<CostCategoryInfo> costCategorys = getCategoryList();
			
			// 20161114 修订为主表为主进行展示
			List<DataListItem> newItems = new ArrayList<DataListItem>();
			
			// map无法排序，放弃
			Map<String, DataListItem> maps = new TreeMap<String, DataListItem>(
			//			new Comparator<String>() {
			//				public int compare(String obj1, String obj2) {
			//					// 降序排序
			//					return Integer.valueOf(obj1) - Integer.valueOf(obj2);
			//					//					return obj2.compareTo(obj1);
			//				}
			//			}
			);
			for (DataListItem item : items) {
				String billNo = (String) item.getMap().get("bill_no");
				
				if (maps.get(billNo) != null) {
					/// item信息
					expenseType = (String) item.getMap().get("expense_type");
					for (CostCategoryInfo cost : costCategorys) {
						if ((cost.getCategoryId() > 0 || cost.getCategoryId() == -1)
							&& String.valueOf(cost.getCategoryId()).equals(expenseType)) {
							expenseType = cost.getName();
							break;
						}
					}
					Money mon = (Money) item.getMap().get("amount");
					// map 原信息
					DataListItem mapItem = maps.get(billNo);
					String mapExpenseType = (String) mapItem.getMap().get("expense_type");
					// 计算新值
					Money mapMon = (Money) mapItem.getMap().get("amount");
					mapItem.getMap().put("expense_type", mapExpenseType + "," + expenseType);
					mapItem.getMap().put("amount", mapMon.add(mon));
					// 放入map
					maps.put(billNo, mapItem);
				} else {
					// 放入基础值
					expenseType = (String) item.getMap().get("expense_type");
					for (CostCategoryInfo cost : costCategorys) {
						if ((cost.getCategoryId() > 0 || cost.getCategoryId() == -1)
							&& String.valueOf(cost.getCategoryId()).equals(expenseType)) {
							expenseType = cost.getName();
							break;
						}
					}
					item.getMap().put("expense_type", expenseType);
					item.getMap().remove("selectId");
					maps.put(billNo, item);
				}
			}
			List<Map.Entry<String, DataListItem>> itemList = new ArrayList<Map.Entry<String, DataListItem>>(
				maps.entrySet());
			Collections.sort(itemList, new Comparator<Map.Entry<String, DataListItem>>() {
				@Override
				public int compare(Entry<String, DataListItem> o1, Entry<String, DataListItem> o2) {
					//				Date addTime1 = (Date) o1.getValue().getMap().get("raw_add_time");
					//				Date addTime2 = (Date) o2.getValue().getMap().get("raw_add_time");
					//				String addtime1Str = DateUtil.dtSimpleFormat(addTime1);
					//				String addtime2Str = DateUtil.dtSimpleFormat(addTime2);
					//				return Integer.valueOf(addtime1Str) - Integer.valueOf(addtime2Str);
					return ((Date) o2.getValue().getMap().get("raw_add_time")).compareTo(((Date) o1
						.getValue().getMap().get("raw_add_time")));
				}
			});
			
			// 将maps放入list
			for (Map.Entry<String, DataListItem> mapping : itemList) {
				newItems.add(maps.get(mapping.getKey()));
			}
			items.clear();
			items = newItems;
			
			for (DataListItem item : items) {
				Date date = (Date) item.getMap().get("application_time");
				item.getMap().put("application_time", DateUtil.dtSimpleFormat(date));
				date = (Date) item.getMap().get("finish_time");
				item.getMap().put("finish_time", DateUtil.dtSimpleFormat(date));
				date = (Date) item.getMap().get("voucher_sync_finish_time");
				item.getMap().put("voucher_sync_finish_time", DateUtil.dtSimpleFormat(date));
				date = (Date) item.getMap().get("wait_pay_time");
				item.getMap().put("wait_pay_time", DateUtil.dtSimpleFormat(date));
				
				Money mon = (Money) item.getMap().get("amount");
				item.getMap().put("mon", mon.toStandardString());
				all.addTo(mon);
				
				String str = (String) item.getMap().get("expense_type");
				//				for (CostCategoryInfo cost : costCategorys) {
				//					if ((cost.getCategoryId() > 0 || cost.getCategoryId() == -1)
				//						&& String.valueOf(cost.getCategoryId()).equals(str)) {
				//						item.getMap().put("expense_type", cost.getName());
				//						break;
				//					}
				//				}
				
				str = (String) item.getMap().get("direction");
				String str2 = (String) item.getMap().get("is_official_card");
				if ("IS".equals(str2)) {
					item.getMap().put("direction", "公务卡");
				} else {
					CostDirectionEnum cost = CostDirectionEnum.getByCode(str);
					if (cost != null) {
						item.getMap().put("direction", cost.message());
					}
				}
				str = (String) item.getMap().get("account_status");
				if (StringUtil.isNotBlank(str)) {
					AccountStatusEnum accountStatus = AccountStatusEnum.getByCode(str);
					if (accountStatus != null) {
						item.getMap().put("account_status", accountStatus.message());
					}
				}
				
			}
			
			DataListItem item = new DataListItem();
			item.getMap().put("amount", all.toStandardString());
			item.getMap().put("bill_no", "总计");
			items.add(item);
			result.setDataList(items);
		} else {
			result.setDataList(new ArrayList<DataListItem>());
		}
		//		result.getFcsFields()
		ReportTemplate reportTemplate = new ReportTemplate();
		reportTemplate.setReportName("支付明细表");
		TableBuilder builder = new TableBuilder(result, reportTemplate,
			TableBuilder.Table_Option_Excel, false);
		builder.init();
		builder.dataBind(request, response);
		return null;
	}
	
	/***
	 * 打印
	 * @throws ParseException
	 */
	@RequestMapping("accountPayPrint.htm")
	public String accountPayPrint(AccountPayQueryOrder order, String expenseType,
									HttpServletRequest request, HttpServletResponse response,
									Model model) throws ParseException {
		
		model.addAttribute("order", order);
		model.addAttribute("expenseType", expenseType);
		
		List<FundAccountPayInfo> infos = new ArrayList<FundAccountPayInfo>();
		
		String allBillNo = order.getBillNo();
		
		if (StringUtil.isNotBlank(allBillNo)) {
			String[] billNos = allBillNo.split(",");
			List<CostCategoryInfo> costCategorys = getCategoryList();
			for (String billNo : billNos) {
				FundAccountPayInfo accountInfo = getFundAccountInfo(model, costCategorys, billNo);
				
				infos.add(accountInfo);
			}
			model.addAttribute("infos", infos);
		}
		// 查询单条信息
		
		// 查询审核记录
		
		// 返回页面
		return vm_path + "reimbursementPrint.vm";
		
	}
	
	private FundAccountPayInfo getFundAccountInfo(Model model,
													List<CostCategoryInfo> costCategorys,
													String billNo) throws ParseException {
		FundAccountPayInfo accountInfo = new FundAccountPayInfo();
		if (StringUtil.isNotBlank(billNo) && billNo.indexOf("CLF") != -1) {
			TravelExpenseQueryOrder queryOrder = new TravelExpenseQueryOrder();
			queryOrder.setBillNo(billNo);
			queryOrder.setDetail(true);
			QueryBaseBatchResult<FormTravelExpenseInfo> result = travelExpenseServiceClient
				.queryPage(queryOrder);
			if (result != null && ListUtil.isNotEmpty(result.getPageList())) {
				FormTravelExpenseInfo info = result.getPageList().get(0);
				FormInfo form = formServiceFmClient.findByFormId(info.getFormId());
				
				QueryBaseBatchResult<WorkflowProcessLog> batchResult = workflowEngineWebClient
					.getProcessOpinionByActInstId(String.valueOf(form.getActInstId()));
				//						model.addAttribute("info", info);
				MiscUtil.copyPoObject(accountInfo, info);
				accountInfo.setReason(info.getReasons());
				//						model.addAttribute("form", form);
				// 只展示一个完好的，一切正常的流程
				// 一个人一个步骤只展示最新的
				accountInfo.setCheckList(getLogList(model, batchResult));
				// 添加费用类型和对公对私
				//model.addAttribute("expenseType", "差旅费");
				accountInfo.setExpenseType("差旅费");
				String costType = "";
				if (BooleanEnum.IS == info.getIsOfficialCard()) {
					costType = "公务卡";
					//model.addAttribute("costType", costType);
					accountInfo.setCostType(costType);
				}
			}
		} else if (StringUtil.isNotBlank(billNo)
					&& (billNo.indexOf("ZF") != -1 || billNo.indexOf("BX") != -1)) {
			ExpenseApplicationQueryOrder queryOrder = new ExpenseApplicationQueryOrder();
			queryOrder.setBillNo(billNo);
			queryOrder.setDetail(true);
			QueryBaseBatchResult<FormExpenseApplicationInfo> result = expenseApplicationServiceClient
				.queryPage(queryOrder);
			if (result != null && ListUtil.isNotEmpty(result.getPageList())) {
				FormExpenseApplicationInfo info = result.getPageList().get(0);
				FormExpenseApplicationInfo info2 = expenseApplicationServiceClient
					.queryByFormId(info.getFormId());
				FormInfo form = formServiceFmClient.findByFormId(info.getFormId());
				
				QueryBaseBatchResult<WorkflowProcessLog> batchResult = workflowEngineWebClient
					.getProcessOpinionByActInstId(String.valueOf(form.getActInstId()));
				//						model.addAttribute("info", info2);
				//						model.addAttribute("form", form);
				MiscUtil.copyPoObject(accountInfo, info2);
				accountInfo.setReason(info2.getReimburseReason());
				//				model.addAttribute("checkList", batchResult.getPageList());
				// 只展示一个完好的，一切正常的流程
				// 一个人一个步骤只展示最新的
				accountInfo.setCheckList(getLogList(model, batchResult));
				
				// 添加费用类型和对公对私
				
				List<FormExpenseApplicationDetailInfo> details = info2.getDetailList();
				String showName = "";
				for (FormExpenseApplicationDetailInfo detailInfo : details) {
					String expenseTypeId = detailInfo.getExpenseType();
					String expenseTypeName = "";
					for (CostCategoryInfo cost : costCategorys) {
						if ((cost.getCategoryId() > 0 || cost.getCategoryId() == -1)
							&& String.valueOf(cost.getCategoryId()).equals(expenseTypeId)) {
							expenseTypeName = cost.getName();
							break;
						}
					}
					// 还不存在并且不为空
					if (StringUtil.isNotBlank(expenseTypeName)
						&& !showName.contains(expenseTypeName)) {
						String newShowName = showName;
						if (StringUtil.isNotBlank(newShowName)) {
							newShowName = newShowName + ",";
						}
						newShowName = newShowName + expenseTypeName;
						// 30字符以内展示
						if (newShowName.length() < 30) {
							showName = newShowName;
						}
					}
				}
				accountInfo.setExpenseType(showName);
				
				String costType = "";
				if (BooleanEnum.IS == info.getIsOfficialCard()) {
					costType = "公务卡";
				} else {
					
					CostDirectionEnum cost = info.getDirection();
					if (cost != null) {
						costType = info.getDirection().getMessage();
					}
				}
				//model.addAttribute("costType", costType);
				accountInfo.setCostType(costType);
			}
			
		} else if (StringUtil.isNotBlank(billNo) && billNo.indexOf("LZSJ") != -1) {
			LabourCapitalQueryOrder queryOrder = new LabourCapitalQueryOrder();
			queryOrder.setBillNo(billNo);
			queryOrder.setDetail(true);
			QueryBaseBatchResult<FormLabourCapitalInfo> result = labourCapitalServiceClient
				.queryPage(queryOrder);
			if (result != null && ListUtil.isNotEmpty(result.getPageList())) {
				FormLabourCapitalInfo info = result.getPageList().get(0);
				FormLabourCapitalInfo info2 = labourCapitalServiceClient.queryByFormId(info
					.getFormId());
				FormInfo form = formServiceFmClient.findByFormId(info.getFormId());
				
				QueryBaseBatchResult<WorkflowProcessLog> batchResult = workflowEngineWebClient
					.getProcessOpinionByActInstId(String.valueOf(form.getActInstId()));
				//						model.addAttribute("info", info2);
				//						model.addAttribute("form", form);
				MiscUtil.copyPoObject(accountInfo, info2);
				accountInfo.setReason(info2.getReimburseReason());
				//				model.addAttribute("checkList", batchResult.getPageList());
				// 只展示一个完好的，一切正常的流程
				// 一个人一个步骤只展示最新的
				accountInfo.setCheckList(getLogList(model, batchResult));
				
				// 添加费用类型和对公对私
				
				List<FormLabourCapitalDetailInfo> details = info2.getDetailList();
				String showName = "";
				for (FormLabourCapitalDetailInfo detailInfo : details) {
					String expenseTypeId = detailInfo.getExpenseType();
					String expenseTypeName = "";
					for (CostCategoryInfo cost : costCategorys) {
						if ((cost.getCategoryId() > 0 || cost.getCategoryId() == -1)
							&& String.valueOf(cost.getCategoryId()).equals(expenseTypeId)) {
							expenseTypeName = cost.getName();
							break;
						}
					}
					// 还不存在并且不为空
					if (StringUtil.isNotBlank(expenseTypeName)
						&& !showName.contains(expenseTypeName)) {
						String newShowName = showName;
						if (StringUtil.isNotBlank(newShowName)) {
							newShowName = newShowName + ",";
						}
						newShowName = newShowName + expenseTypeName;
						// 30字符以内展示
						if (newShowName.length() < 30) {
							showName = newShowName;
						}
					}
				}
				accountInfo.setExpenseType(showName);
				
				String costType = "";
				if (BooleanEnum.IS == info.getIsOfficialCard()) {
					costType = "公务卡";
				} else {
					
					CostDirectionEnum cost = info.getDirection();
					if (cost != null) {
						costType = info.getDirection().getMessage();
					}
				}
				//model.addAttribute("costType", costType);
				accountInfo.setCostType(costType);
			}
			
		}
		return accountInfo;
	}
	
	private List<WorkflowProcessLog> getLogList(Model model,
												QueryBaseBatchResult<WorkflowProcessLog> batchResult)
																										throws ParseException {
		if (ListUtil.isNotEmpty(batchResult.getPageList())) {
			//					model.addAttribute("checkList", batchResult.getPageList());
			List<WorkflowProcessLog> checkList = new ArrayList<WorkflowProcessLog>();
			Map<String, WorkflowProcessLog> maps = new TreeMap<String, WorkflowProcessLog>();
			for (WorkflowProcessLog log : batchResult.getPageList()) {
				String key = log.getExeUserId() + "_" + log.getTaskName();
				// 只保留同意的数据
				if (1 != log.getCheckStatus()) {
					continue;
				}
				if (
				//						StringUtil.equals("UserTask1", log.getTaskKey())
				//					|| 
				StringUtil.equals("发起人提交", log.getTaskName())
				//					|| StringUtil.equals("task1", log.getTaskKey())
				) {
					continue;
				}
				if (maps.get(key) != null) {
					WorkflowProcessLog mapLog = (WorkflowProcessLog) maps.get(key);
					Date mapLogTime = DateUtil.string2DateTime(mapLog.getStartTime());
					Date logTime = DateUtil.string2DateTime(log.getStartTime());
					// 若新数据的开始时间大于老数据的开始时间，代表后执行，代表需要更新
					if (logTime.getTime() > mapLogTime.getTime()) {
						maps.put(key, log);
					}
				} else {
					maps.put(key, log);
				}
			}
			
			List<Map.Entry<String, WorkflowProcessLog>> itemList = new ArrayList<Map.Entry<String, WorkflowProcessLog>>(
				maps.entrySet());
			Collections.sort(itemList, new Comparator<Map.Entry<String, WorkflowProcessLog>>() {
				@Override
				public int compare(Entry<String, WorkflowProcessLog> o1,
									Entry<String, WorkflowProcessLog> o2) {
					try {
						return ((Date) DateUtil.string2DateTime(o1.getValue().getStartTime()))
							.compareTo((Date) DateUtil
								.string2DateTime(o2.getValue().getStartTime()));
					} catch (ParseException e) {
						logger.error(e.getMessage(), e);
					}
					return 0;
				}
			});
			
			// 将maps放入list
			for (Map.Entry<String, WorkflowProcessLog> mapping : itemList) {
				checkList.add(maps.get(mapping.getKey()));
			}
			//model.addAttribute("checkList", checkList);
			return checkList;
		}
		return null;
	}
}
