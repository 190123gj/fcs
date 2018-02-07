package com.born.fcs.face.web.controller.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.rm.service.report.ReportBySqlClient;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.page.Page;
import com.born.fcs.pm.page.PageParam;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.util.rpt.common.ReportData;
import com.born.fcs.pm.util.rpt.common.ReportExcel;
import com.born.fcs.pm.util.rpt.common.ReportHead;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.rm.ws.info.report.ReportRuleData;
import com.born.fcs.rm.ws.order.report.ReportQueryParam;
import com.born.fcs.rm.ws.order.report.ReportSqlQueryOrder;

@Controller
@RequestMapping("/baseDataLoad/report")
public class ReportSqlController extends BaseController {
	
	@Autowired
	private ReportBySqlClient reportBySqlClient;
	
	/** 通用页面路径 */
	String VM_PATH = "/reportMg/sqlReport/";
	
	@RequestMapping("list.htm")
	public String queryAll(ReportSqlQueryOrder reportQueryOrder, HttpServletResponse response,
							PageParam pageParam, Model model) {
		try {
			reportQueryOrder.setPageNumber(pageParam.getPageNo());
			reportQueryOrder.setPageSize(pageParam.getPageSize());
			
			QueryBaseBatchResult<ReportRuleData> page = reportBySqlClient
				.getQueryRules(reportQueryOrder);
			
			model.addAttribute("page", PageUtil.getCovertPage(page));
			model.addAttribute("queryConditions", reportQueryOrder);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return VM_PATH + "reportList.vm";
	}
	
	@RequestMapping("toQuery.htm")
	public String toQuery(long reportId, HttpServletResponse response, Model model) {
		
		try {
			ReportRuleData queryRule = reportBySqlClient.getQueryRule(reportId);
			model.addAttribute("queryRule", queryRule);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return VM_PATH + "reportQuery.vm";
	}
	
	@RequestMapping("toAdd.htm")
	public String toAdd(Long reportId, HttpServletResponse response, Model model) {
		//		ReportRuleData queryRule = new ReportRuleData();
		//		try {
		//			if (reportId != 0) {
		//				queryRule = reportBySqlClient.getQueryRule(reportId);
		//			}
		//			model.addAttribute("queryRule", queryRule);
		//			
		//		} catch (Exception e) {
		//			logger.error(e.getMessage(), e);
		//		}
		
		return VM_PATH + "reportAdd.vm";
	}
	
	@RequestMapping("delete.htm")
	public String doDelete(long reportId, HttpServletResponse response, Model model) {
		ReportRuleData queryRule = new ReportRuleData();
		try {
			if (reportId != 0) {
				reportBySqlClient.deleteQueryRule(reportId);
			}
			model.addAttribute("queryRule", queryRule);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return "redirect:" + "/backstage/report/list";
	}
	
	@RequestMapping("refresh")
	public String refresh(HttpServletResponse response, Model model) {
		try {
			int i = reportBySqlClient.refreshData();
			logger.info("手动更新数据: " + i);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return "redirect:" + "/backstage/report/list";
	}
	
	@ResponseBody
	@RequestMapping("update.json")
	public Object update(ReportRuleData reportRule, HttpServletResponse response, Model model) {
		JSONObject jsonobj = new JSONObject();
		try {
			long reportId = reportRule.getReportId();
			
			//检验json 字符串是否正确
			if (StringUtil.isNotEmpty(reportRule.getFilter1Options())) {
				MiscUtil.parseJSONWithException(reportRule.getFilter1Options());
			}
			if (StringUtil.isNotEmpty(reportRule.getFilter2Options())) {
				MiscUtil.parseJSONWithException(reportRule.getFilter2Options());
			}
			if (StringUtil.isNotEmpty(reportRule.getFilter3Options())) {
				MiscUtil.parseJSONWithException(reportRule.getFilter3Options());
			}
			if (StringUtil.isNotEmpty(reportRule.getFilter4Options())) {
				MiscUtil.parseJSONWithException(reportRule.getFilter4Options());
			}
			if (StringUtil.isNotEmpty(reportRule.getFilter5Options())) {
				MiscUtil.parseJSONWithException(reportRule.getFilter5Options());
			}
			if (StringUtil.isNotEmpty(reportRule.getFilter6Options())) {
				MiscUtil.parseJSONWithException(reportRule.getFilter6Options());
			}
			
			if (reportId != 0) {
				reportId = reportBySqlClient.udpateQueryRule(reportRule);
			} else {
				reportId = reportBySqlClient.addQueryRule(reportRule);
				
			}
			ReportRuleData queryRule = reportBySqlClient.getQueryRule(reportId);
			model.addAttribute("queryRule", queryRule);
			
			jsonobj.put("code", 1);
			jsonobj.put("message", "保存成功！");
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonobj.put("code", 0);
			jsonobj.put("message", "保存失败！" + e.getMessage());
		}
		
		//return VM_PATH +"reportAdd.vm";
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping("downloadResult.htm")
	public Object downloadResult(HttpSession session, HttpServletResponse response,
									HttpServletRequest request, Model model,
									ReportQueryParam queryParam) {
		JSONObject jsonobj = new JSONObject();
		try {
			//			Object lastDownloadTime = session.getAttribute("lastDownloadTime");
			//			long lastTime = 0;
			//			if (lastDownloadTime != null && StringUtil.isNotEmpty(lastDownloadTime + "")) {
			//				lastTime = Long.parseLong(lastDownloadTime + "");
			//			}
			//			long nowTime = System.currentTimeMillis();
			//			
			//			if (lastTime > 0
			//				&& nowTime < (lastTime + (AppConstantsUtil.getReportTimeInterval() * 1000))) {
			//				jsonobj.put("code", 0);
			//				jsonobj.put("message", "亲，你报表查询太频繁了");
			//				return jsonobj;
			//			}
			ReportRuleData rule = reportBySqlClient.getQueryRule(queryParam.getReportId());
			
			String reportName = rule.getReportName();
			
			FcsBaseResult result = reportBySqlClient.queryReportData(queryParam, rule);
			List<Map<String, String>> list = new ArrayList<>();
			if (result.isSuccess()) {
				String rs = (String) result.getReturnObject();
				list = (List<Map<String, String>>) JSONObject.parseObject(rs).get("data");
			}
			if (list.size() > 0) {
				response.addHeader(
					"Content-Disposition",
					"attachment; filename="
							+ new String(
								(reportName + DateUtil.dtSimpleFormat(new Date()) + ".xls")
									.getBytes("GB2312"), "ISO8859-1"));
				
				String CONTENT_TYPE_EXCEL = "application/vnd.ms-excel";
				String serverRealPath = session.getServletContext().getRealPath("/");
				response.setContentType(CONTENT_TYPE_EXCEL);
				
				Map<String, String> firstRow = list.get(0);
				ReportHead head = new ReportHead();
				
				String filters = reportBySqlClient.getQueryParam(queryParam, rule);
				
				head.setDate(DateUtil.simpleFormat(new Date()));
				head.setFilters(filters.toString());
				
				ReportData report = new ReportData(head, list);
				ReportExcel excel = new ReportExcel(response.getOutputStream());
				
				excel.print(report.getXMLDocument(),
					excel.getDefaultDocument(serverRealPath, reportName, firstRow));
				jsonobj.put("code", 1);
				
				session.setAttribute("lastDownloadTime", System.currentTimeMillis());
				
				return jsonobj;
				
			} else {
				jsonobj.put("code", 1);
				jsonobj.put("message", "未查到满足条件的数据！");
				return jsonobj;
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response.setContentType("application/json");
			jsonobj.put("code", 0);
			jsonobj.put("message", e.getMessage());
		}
		return jsonobj;
		
	}
	
	@RequestMapping("queryResult.htm")
	public String queryResult(HttpSession session, HttpServletResponse response, Model model,
								ReportQueryParam queryParam, String returnUrl, PageParam pageParam) {
		try {
			ReportRuleData rule = reportBySqlClient.getQueryRule(queryParam.getReportId());
			
			//			Object lastDownloadTime = session.getAttribute("lastDownloadTime");
			//			long lastTime = 0;
			//			if (lastDownloadTime != null && StringUtil.isNotEmpty(lastDownloadTime + "")) {
			//				lastTime = Long.parseLong(lastDownloadTime + "");
			//			}
			//			long nowTime = System.currentTimeMillis();
			//			if (lastTime > 0
			//				&& nowTime < (lastTime + (AppConstantsUtil.getReportTimeInterval() * 1000))) {
			//				model.addAttribute("queryRule", rule);
			//				model.addAttribute("queryParam", queryParam);
			//				model.addAttribute("message", "亲，你报表查询太频繁了");
			//				return VM_PATH + "reportQuery.vm";
			//			}
			//			
			FcsBaseResult result = reportBySqlClient.queryReportPage(queryParam, pageParam, rule);
			Page<Map<String, String>> page = new Page<Map<String, String>>();
			if (result.isSuccess()) {
				String rs = (String) result.getReturnObject();
				JSONObject json = (JSONObject) JSONObject.parseObject(rs).get("data");
				page.setResult((List<Map<String, String>>) json.get("result"));
				page.setCurrentPageNo((Integer) json.get("currentPageNo"));
				page.setNexPagetNo((Integer) json.get("nexPagetNo"));
				page.setPageSize((Integer) json.get("pageSize"));
				page.setPreviosPageNo((Integer) json.get("previosPageNo"));
				page.setStart((Integer) json.get("start"));
				page.setTotalCount((Integer) json.get("totalCount"));
				page.setTotalPageCount((Integer) json.get("totalPageCount"));
			}
			model.addAttribute("page", page);
			model.addAttribute("queryParam", queryParam);
			if (StringUtil.isNotEmpty(rule.getFilter1Options())) {
				model.addAttribute("filter1Options", MiscUtil.parseJSON(rule.getFilter1Options()));
			}
			if (StringUtil.isNotEmpty(rule.getFilter2Options())) {
				model.addAttribute("filter2Options", MiscUtil.parseJSON(rule.getFilter2Options()));
			}
			if (StringUtil.isNotEmpty(rule.getFilter3Options())) {
				model.addAttribute("filter3Options", MiscUtil.parseJSON(rule.getFilter3Options()));
			}
			if (StringUtil.isNotEmpty(rule.getFilter4Options())) {
				model.addAttribute("filter4Options", MiscUtil.parseJSON(rule.getFilter4Options()));
			}
			if (StringUtil.isNotEmpty(rule.getFilter5Options())) {
				model.addAttribute("filter5Options", MiscUtil.parseJSON(rule.getFilter5Options()));
			}
			if (StringUtil.isNotEmpty(rule.getFilter6Options())) {
				model.addAttribute("filter6Options", MiscUtil.parseJSON(rule.getFilter6Options()));
			}
			model.addAttribute("queryRule", rule);
			model.addAttribute("returnUrl", returnUrl);
			session.setAttribute("lastDownloadTime", System.currentTimeMillis());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return VM_PATH + "reportQuery.vm";
		
	}
	
	@RequestMapping("test")
	public void testExcel(HttpSession session, HttpServletResponse response, Model model) {
		
		try {
			
			String CONTENT_TYPE_EXCEL = "application/vnd.ms-excel";
			String serverRealPath = session.getServletContext().getRealPath("/");
			response.setContentType(CONTENT_TYPE_EXCEL);
			
			String extName = "Excel报表.xls";
			
			response.addHeader("Content-Disposition",
				"attachment; filename=" + new String(extName.getBytes("GB2312"), "ISO8859-1"));
			
			ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
			
			HashMap<String, String> date = new HashMap<String, String>();
			date.put("appointment_date", "1001");
			date.put("stand_prem", "1001");
			date.put("life_money", "1001");
			date.put("bonus_rate", "1001");
			date.put("agent_code", "1001");
			date.put("money", "1001");
			date.put("dept_id", "1001");
			date.put("real_name", "1001");
			date.put("agent_age", "1001");
			list.add(date);
			list.add(date);
			list.add(date);
			
			ReportHead head = new ReportHead();
			head.setDate("时间2014-08-28");
			
			ReportData report = new ReportData(head, list);
			ReportExcel excel = new ReportExcel(response.getOutputStream());
			excel.print(report.getXMLDocument(), excel.toDocument(serverRealPath, "example1.xml"));
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}
	
}
