package com.born.fcs.face.web.controller.report;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.bpm.service.info.UserInfo;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.order.projectcreditcondition.FCreditConditionConfirmQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.ProjectReportService;
import com.born.fcs.pm.ws.service.report.info.AmountRecordInfo;
import com.born.fcs.rm.ws.enums.ReportCodeEnum;
import com.born.fcs.rm.ws.enums.ReportTypeEnum;
import com.born.fcs.rm.ws.enums.VersionEnum;
import com.born.fcs.rm.ws.info.report.ReportInfo;
import com.born.fcs.rm.ws.order.report.ReportBaseQueryOrder;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;
import com.born.fcs.rm.ws.order.report.project.BatchSaveOrder;
import com.born.fcs.rm.ws.order.report.project.ProjectCustomerQueryOrder;
import com.yjf.common.lang.util.StringUtil;

/**
 * 
 * 报表生成记录
 * 
 * @author lirz
 * 
 * 2016-8-10 下午3:49:46
 */
@Controller
@RequestMapping("reportMg/report")
public class ReportController extends BaseController {
	
	final static String vm_path = "/reportMg/report/";
	
	@Autowired
	private ProjectReportService projectReportServiceClient;
	
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, HttpServletResponse response, Model model,
						@RequestParam(value = "year", required = false) String reportYear,
						@RequestParam(value = "month", required = false) String reportMonth,
						ReportQueryOrder queryOrder) {
		//		ReportQueryOrder queryOrder = new ReportQueryOrder();
		//		WebUtil.setPoPropertyByRequest(queryOrder, request);
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (hasAllDataPermission()) {//拥有所有数据权限
				queryOrder.setOperatorId(0);
				queryOrder.setDeptIdList(null);
			} else if (hasPrincipalDataPermission() && userInfo != null) { //拥有所负责的数据权限，按照部门维度查询数据
				queryOrder.setOperatorId(0);
				queryOrder.setDeptIdList(userInfo.getDeptIdList());
			} else { //按照人员维度查询数据
				queryOrder.setOperatorId(sessionLocal.getUserId());
				queryOrder.setDeptIdList(null);
			}
		}
		queryOrder.setVersion(VersionEnum.NOW.code());
		queryOrder.setReportYear(NumberUtil.parseInt(reportYear));
		queryOrder.setReportMonth(NumberUtil.parseInt(reportMonth));
		QueryBaseBatchResult<ReportInfo> batchResult = reportServiceClient.queryList(queryOrder);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		model.addAttribute("queryConditions", queryOrder);
		model.addAttribute("reportYear", reportYear);
		model.addAttribute("reportMonth", reportMonth);
		model.addAttribute("years", getYears(5));
		return vm_path + "list.vm";
	}
	
	@RequestMapping("edit.htm")
	public String edit(	HttpServletRequest request,
						HttpServletResponse response,
						Model model,
						@RequestParam(value = "code", required = true) String reportCode,
						@RequestParam(value = "year", required = false, defaultValue = "0") int reportYear,
						@RequestParam(value = "month", required = false, defaultValue = "0") int reportMonth) {
		//		Calendar c = Calendar.getInstance();
		//		c.setTime(new Date());
		//		if (reportYear == 0) {
		//			reportYear = c.get(Calendar.YEAR);
		//		}
		//		if (reportMonth == 0) {
		//			reportMonth = c.get(Calendar.MONTH) + 1;
		//		}
		if (reportYear > 0 && reportMonth > 0) {
			ReportBaseQueryOrder queryOrder = new ReportBaseQueryOrder();
			queryOrder.setReportCode(reportCode);
			queryOrder.setReportYear(reportYear);
			queryOrder.setReportMonth(reportMonth);
			//			ReportInfo report = queryReport(queryOrder);
			ReportInfo report = reportServiceClient.findByAccountPeriod(reportCode, reportYear,
				reportMonth);
			//System.out.println(report);
			model.addAttribute("report", report);
		} else {
			//内部报表初始化部门信息
			if (ReportCodeEnum.getByCode(reportCode).getType().code()
				.equals(ReportTypeEnum.INNER.getCode())) {
				List<Org> orgs = initOrg();
				if (orgs != null) {
					List<String> depts = Lists.newArrayList();
					for (Org org : orgs) {
						depts.add(org.getName());
					}
					model.addAttribute("orgs", orgs);
					model.addAttribute("depts", depts);
				}
			}
		}
		String quarter = "";
		//季度表
		if (1 == reportMonth || 2 == reportMonth || 3 == reportMonth) {
			quarter = reportYear + "年" + "1季度";
		}
		if (4 == reportMonth || 5 == reportMonth || 6 == reportMonth) {
			quarter = reportYear + "年" + "2季度";
		}
		if (7 == reportMonth || 8 == reportMonth || 9 == reportMonth) {
			quarter = reportYear + "年" + "3季度";
		}
		if (10 == reportMonth || 11 == reportMonth || 12 == reportMonth) {
			quarter = reportYear + "年" + "4季度";
		}
		model.addAttribute("quarter", quarter);
		model.addAttribute("reportCode", ReportCodeEnum.getByCode(reportCode));
		model.addAttribute("reportYear", reportYear == 0 ? "" : reportYear + "");
		model.addAttribute("reportMonth", reportMonth == 0 ? "" : reportMonth + "");
		
		return vm_path + "editReport.vm";
	}
	
	@RequestMapping("view.htm")
	public String view(	HttpServletRequest request,
						HttpServletResponse response,
						Model model,
						@RequestParam(value = "reportId", required = false, defaultValue = "0") long reportId,
						@RequestParam(value = "code", required = false, defaultValue = "0") String reportCode,
						@RequestParam(value = "year", required = false, defaultValue = "0") int reportYear,
						@RequestParam(value = "month", required = false, defaultValue = "0") int reportMonth) {
		ReportInfo report = null;
		if (reportId > 0) {
			report = reportServiceClient.findById(reportId);
		} else {
			report = reportServiceClient.findByAccountPeriod(reportCode, reportYear, reportMonth);
		}
		
		model.addAttribute("report", report);
		if (null != report) {
			model.addAttribute("reportCode", report.getReportCode());
		}
		if (null != report && report.getReportCode() == ReportCodeEnum.BASE_REPORT)
			return vm_path + "editBaseReport.vm";
		else
			return vm_path + "viewReport.vm";
	}
	
	@RequestMapping("save.json")
	@ResponseBody
	public JSONObject save(ReportOrder order, Model model) {
		String tipPrefix = "报表生成";
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		
		try {
			setSessionLocalInfo2Order(order);
			FcsBaseResult result = reportServiceClient.save(order);
			json = toJSONResult(result, tipPrefix);
			
		} catch (Exception e) {
			json = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return json;
	}
	
	@RequestMapping("editBase.htm")
	public String editBase(HttpServletRequest request, HttpServletResponse response, Model model,
							ProjectCustomerQueryOrder queryOrder) {
		model.addAttribute("years", getYears(5));
		
		//		ProjectCustomerInfo info = projectInfoServiceClient.queryCustomerInfo(queryOrder);
		//		model.addAttribute("info", info);
		model.addAttribute("queryConditions", queryOrder);
		return vm_path + "editBaseReport.vm";
	}
	
	@RequestMapping("viewBase.htm")
	public String viewBase(	HttpServletRequest request,
							HttpServletResponse response,
							Model model,
							@RequestParam(value = "reportId", required = false, defaultValue = "0") long reportId,
							@RequestParam(value = "code", required = false, defaultValue = "0") String reportCode,
							@RequestParam(value = "year", required = false, defaultValue = "0") int reportYear,
							@RequestParam(value = "month", required = false, defaultValue = "0") int reportMonth) {
		//		ReportInfo report = null;
		//		if (reportId > 0) {
		//			report = reportServiceClient.findById(reportId);
		//		} else {
		//			report = reportServiceClient.findByAccountPeriod(reportCode, reportYear, reportMonth);
		//		}
		//		
		//		model.addAttribute("report", report);
		//		if (null != report) {
		//			model.addAttribute("reportCode", report.getReportCode());
		//		}
		return vm_path + "viewReport.vm";
	}
	
	@RequestMapping("saveBase.json")
	@ResponseBody
	public JSONObject saveBase(BatchSaveOrder order, Model model) {
		String tipPrefix = "基层定期报表批量保存";
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		
		try {
			setSessionLocalInfo2Order(order);
			FcsBaseResult result = projectInfoServiceClient.saveBatch(order);
			json = toJSONResult(result, tipPrefix);
			
		} catch (Exception e) {
			json = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return json;
	}
	
	@RequestMapping("listDef.htm")
	public String listDef(HttpServletRequest request, HttpServletResponse response, Model model,
							ReportQueryOrder queryOrder) {
		queryOrder.setVersion(VersionEnum.NOW.code());
		QueryBaseBatchResult<ReportInfo> batchResult = reportServiceClient.queryList(queryOrder);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		model.addAttribute("queryConditions", queryOrder);
		
		model.addAttribute("years", getYears(5));
		return vm_path + "listDef.vm";
	}
	
	private List<String> initDept() {
		List<String> depts = new ArrayList<>();
		String deptCodes = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_ANNUAL_OBJECTIVE_DEPT_CODE.code());
		if (deptCodes != null) {
			String deptCode[] = deptCodes.split(",");
			for (String code : deptCode) {
				String result = submissionServiceClient.findDeptInfoByDeptCode(code);
				if (result != null) {
					HashMap<String, Object> resultMap = MiscUtil.parseJSON(result);
					Map<String, Object> sysOrgMap = (Map<String, Object>) resultMap.get("sysOrg");
					depts.add(String.valueOf(sysOrgMap.get("orgName")));
				}
			}
		}
		return depts;
	}
	
	private List<Org> initOrg() {
		
		List<Org> orgs = new ArrayList<>();
		String deptCodes = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_ANNUAL_OBJECTIVE_DEPT_CODE.code());
		if (StringUtil.isNotBlank(deptCodes)) {
			String deptCode[] = deptCodes.split(",");
			for (String code : deptCode) {
				Org org = bpmUserQueryService.findDeptByCode(code);
				if (org != null) {
					orgs.add(org);
				}
			}
		}
		//重新排序
		Collections.sort(orgs, new Comparator<Org>() {
			@Override
			public int compare(Org o1, Org o2) {
				if (o1.getCode() == null)
					return 1;
				if (o2.getCode() == null)
					return -1;
				return o1.getCode().compareToIgnoreCase(o2.getCode());
			}
		});
		return orgs;
	}
	
	public static void main(String[] args) {
		List<ReportCodeEnum> list = ReportCodeEnum.getAllEnum();
		String path = "F:\\test\\vm\\";
		for (ReportCodeEnum reportCode : list) {
			String fileName = "t" + reportCode.getSuffix() + ".vm";
			try {
				//打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
				FileWriter writer = new FileWriter(path + fileName, true);
				writer.write("<input type=\"hidden\" name=\"reportName\" value=\""
								+ reportCode.getMessage() + "\" />");
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//			System.out.println("报表名称：" + reportCode.message() + "\t\t" + "t" + reportCode.getSuffix() + ".vm");
			System.out.println("/reportMg/report/edit.htm?code=" + reportCode.code() + "\t\t\t"
								+ reportCode.message());
		}
	}
	
	@RequestMapping("print.json")
	public String print(HttpServletRequest request, HttpServletResponse response, Model model)
																								throws IOException {
		
		String titleDC = request.getParameter("titleDC");
		String timeDC = request.getParameter("timeDC");
		if (StringUtil.isBlank(titleDC)) {
			titleDC = "测试";
		}
		if (StringUtil.isNotBlank(timeDC)) {
			titleDC = titleDC + "_" + timeDC;
		}
		titleDC += ".xls";
		
		response.setContentType("application/octet-stream"); // response.setContentType() 的作用是使客户端浏览器，区分不同种类的数据，并根据不同的MIME调用浏览器内不同的程序嵌入模块来处理相应的数据。
		response.setHeader("Content-disposition",
			"attachment;filename="
					+ (new String((titleDC).getBytes("GBK"), "ISO8859_1") + ";charset=UTF-8"));// 设置了文件的类型和名字 这个格式也避免乱码
		ServletOutputStream out = response.getOutputStream();
		String s1 = request.getParameter("s1");
		StringBuffer sb = new StringBuffer(s1);
		out.write(sb.toString().getBytes("UTF-8"));
		
		//		for (int i = 0; i < 5; i++) {
		//			// 这边就是拼数据
		//			
		//			out.write(sb.toString().getBytes("GBK"));
		//		}
		response.flushBuffer();
		out.close();
		return null;
	}
	
	@RequestMapping("queryRepayRecord.htm")
	public String queryRepayRecord(HttpServletRequest request,
						HttpServletResponse response,
						Model model) {
		FCreditConditionConfirmQueryOrder queryOrder = new FCreditConditionConfirmQueryOrder();
		WebUtil.setPoPropertyByRequest(queryOrder, request);
		
//		QueryBaseBatchResult<AmountRecordInfo> batchResult = null;
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (DataPermissionUtil.isZHYYdept()) { //综合营运部可以查看全公司的报表
			
			} else if (DataPermissionUtil.isDirector() || DataPermissionUtil.isNQRole()) {//各部门总监和内勤可以看本部门全部的数据
				queryOrder.setDeptIdList(userInfo.getDeptIdList());
			} else if (DataPermissionUtil.isBusiManager()) { //业务人员可以看个人的数据
				queryOrder.setBusiManagerId(sessionLocal.getUserId());
			} else {//其他人没权限
				return vm_path + "table/tRepayRecord.vm";
			}
		}
		QueryBaseBatchResult<AmountRecordInfo> batchResult = projectReportServiceClient
			.queryRepayRecord(queryOrder);
		model.addAttribute("max", batchResult.getKeyId());
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		model.addAttribute("conditions", queryOrder);
		
		return vm_path + "table/tRepayRecord.vm";
	}
	
	@RequestMapping("queryLoanRecord.htm")
	public String queryLoanRecord(HttpServletRequest request,
	                               HttpServletResponse response,
	                               Model model) {
		FCreditConditionConfirmQueryOrder queryOrder = new FCreditConditionConfirmQueryOrder();
		WebUtil.setPoPropertyByRequest(queryOrder, request);
		
		QueryBaseBatchResult<AmountRecordInfo> batchResult = null;
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (DataPermissionUtil.isZHYYdept()) { //综合营运部可以查看全公司的报表
			
			} else if (DataPermissionUtil.isDirector() || DataPermissionUtil.isNQRole()) {//各部门总监和内勤可以看本部门全部的数据
				queryOrder.setDeptIdList(userInfo.getDeptIdList());
			} else if (DataPermissionUtil.isBusiManager()) { //业务人员可以看个人的数据
				queryOrder.setBusiManagerId(sessionLocal.getUserId());
			} else {//其他人没权限
				return vm_path + "table/tLoanRecord.vm";
			}
		}
		batchResult = projectReportServiceClient.queryLoanRecord(queryOrder);
		model.addAttribute("max", batchResult.getKeyId());
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		model.addAttribute("conditions", queryOrder);
		
		return vm_path + "table/tLoanRecord.vm";
	}
	
}
