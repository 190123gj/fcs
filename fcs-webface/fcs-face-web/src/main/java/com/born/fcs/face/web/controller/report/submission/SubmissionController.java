package com.born.fcs.face.web.controller.report.submission;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.bpm.service.client.user.SysOrg;
import com.born.fcs.face.integration.bpm.service.info.UserInfo;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.ExcelData;
import com.born.fcs.face.web.util.ExcelUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.rm.ws.enums.ReportStatusEnum;
import com.born.fcs.rm.ws.enums.SubmissionCodeEnum;
import com.born.fcs.rm.ws.enums.VersionEnum;
import com.born.fcs.rm.ws.info.accountbalance.AccountBalanceInfo;
import com.born.fcs.rm.ws.info.submission.SubmissionDataInfo;
import com.born.fcs.rm.ws.info.submission.SubmissionInfo;
import com.born.fcs.rm.ws.order.accountbalance.AccountBalanceDataOrder;
import com.born.fcs.rm.ws.order.accountbalance.AccountBalanceOrder;
import com.born.fcs.rm.ws.order.accountbalance.AccountBalanceQueryOrder;
import com.born.fcs.rm.ws.order.submission.SubmissionOrder;
import com.born.fcs.rm.ws.order.submission.SubmissionQueryOrder;
import com.google.common.collect.Lists;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

@Controller
@RequestMapping("reportMg/submission")
public class SubmissionController extends BaseController {
	final static String vm_path = "/reportMg/dataTransmit/";
	
	/**
	 * 数据报送新增/编辑
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("submission.htm")
	public String submission(Long submissionId, SubmissionCodeEnum submissionCode,
								HttpServletRequest request, HttpServletResponse response,
								Model model) {
		SubmissionInfo info = new SubmissionInfo();
		if (submissionId != null && submissionId > 0) {//编辑
			info = submissionServiceClient.findById(submissionId);
		} else {//新增
			if (submissionCode == SubmissionCodeEnum.ANNUAL_OBJECTIVE
				|| submissionCode == SubmissionCodeEnum.BFSRCWB) {//年度报表单独处理
				info = setAnnualObjective(submissionCode);
			}
			info.setReportCode(submissionCode);
		}
		model.addAttribute("info", info);
		return vm_path + info.getReportCode().editUrl();
	}
	
	/**
	 * 查看详情
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("view.htm")
	public String view(Long submissionId, HttpServletRequest request, HttpServletResponse response,
						Model model) {
		SubmissionInfo info = submissionServiceClient.findById(submissionId);
		model.addAttribute("view", true);
		model.addAttribute("info", info);
		return vm_path + info.getReportCode().editUrl();
	}
	
	/**
	 * 数据报送
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("save.htm")
	@ResponseBody
	public JSONObject save(SubmissionOrder order, Model model) {
		String tipPrefix = " 数据报送";
		JSONObject result = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				result.put("success", false);
				result.put("message", "您未登陆或登陆已失效");
				return result;
			}
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (userInfo != null) {
				order.setReporterId(sessionLocal.getUserId());
				order.setReporterName(sessionLocal.getRealName());
				order.setReporterAccount(sessionLocal.getUserName());
				SysOrg dept = sessionLocal.getUserInfo().getDept();
				if (dept != null) {
					order.setDeptId(dept.getOrgId());
					order.setDeptCode(dept.getCode());
					order.setDeptName(dept.getOrgName());
					order.setDeptPath(dept.getPath());
					order.setDeptPathName(dept.getOrgPathname());
				}
			}
			if ("1".equals(order.getCheckStatus())) {
				order.setReporterStatus(ReportStatusEnum.SUBMITTED);
			} else {
				order.setReporterStatus(ReportStatusEnum.DRAFT);
			}
			order.setReportName(order.getReportCode().message());
			FcsBaseResult saveResult = submissionServiceClient.save(order);
			if (saveResult.isSuccess()) {
				result.put("success", true);
				result.put("message", "保存成功");
			} else {
				result.put("success", false);
				result.put("message", saveResult.getMessage());
			}
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
	}
	
	/**
	 * 数据报送记录列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(SubmissionQueryOrder order, Model model) {
		if (order.getYear() != null) {
			order.setReportYear(Integer.parseInt(order.getYear()));
		}
		if (order.getMonth() != null) {
			order.setReportMonth(Integer.parseInt(order.getMonth()));
		}
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (hasAllDataPermission()) {//拥有所有数据权限
				order.setReporterId(0);
				order.setDeptIdList(null);
			} else if (hasPrincipalDataPermission() && userInfo != null) { //拥有所负责的数据权限，按照部门维度查询数据
				order.setReporterId(0);
				order.setDeptIdList(userInfo.getDeptIdList());
			} else { //按照人员维度查询数据
				order.setReporterId(sessionLocal.getUserId());
				order.setDeptIdList(null);
			}
			//查看草稿的人员
			order.setDraftUserId(sessionLocal.getUserId());
		}
		QueryBaseBatchResult<SubmissionInfo> batchResult = submissionServiceClient.query(order);
		model.addAttribute("reportStatus", ReportStatusEnum.getAllEnum());
		model.addAttribute("conditions", order);
		model.addAttribute("depts", submissionServiceClient.getDepts());
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		return vm_path + "list.vm";
	}
	
	/**
	 * 删除报送记录
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("delete.htm")
	@ResponseBody
	public JSONObject delete(SubmissionOrder order, Model model) {
		String tipPrefix = "删除报送记录";
		JSONObject result = new JSONObject();
		try {
			FcsBaseResult delResult = submissionServiceClient.deleteById(order);
			if (delResult.isSuccess()) {
				result.put("success", true);
				result.put("message", "删除成功");
			} else {
				result.put("success", false);
				result.put("message", delResult.getMessage());
			}
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		return result;
	}
	
	//---------------------------------------科目余额表-------------------------------------------
	
	final static String vm_path_balance = "/reportMg/dataTransmit/subjectBalance/";
	
	/** 科目余额表列表 */
	@RequestMapping("listBalance.htm")
	public String listBalance(HttpServletRequest request, Model model) {
		AccountBalanceQueryOrder order = new AccountBalanceQueryOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		order.setVersion(VersionEnum.NOW.code());
		String year = request.getParameter("year");
		order.setReportYear(NumberUtil.parseInt(year));
		String month = request.getParameter("month");
		order.setReportMonth(NumberUtil.parseInt(month));
		QueryBaseBatchResult<AccountBalanceInfo> batchResult = accountBalanceServiceClient
			.queryList(order);
		model.addAttribute("queryConditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		
		model.addAttribute("years", getYears(5));
		return vm_path_balance + "subjectBalanceList.vm";
	}
	
	/** 科目余额表查看 */
	@RequestMapping("viewBalance.htm")
	public String viewBalance(Model model, long id) {
		AccountBalanceInfo info = accountBalanceServiceClient.findById(id);
		model.addAttribute("info", info);
		
		return vm_path_balance + "viewAccountBalance.vm";
	}
	
	@RequestMapping("downloadModel.htm")
	public void downloadModel(HttpServletRequest request, HttpServletResponse response) {
		BufferedOutputStream output = null;
		BufferedInputStream input = null;
		
		try {
			String fileName = "科目余额表";
			String filePath = request.getServletContext().getRealPath("/WEB-INF/")
								+ "/xsl/excelModel/" + fileName + ".xls";
			File file = new File(filePath);
			response.setHeader("Content-disposition",
				"attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1")
						+ ".xls");
			response.setContentType("application/msword");
			response.setContentLength((int) file.length());
			byte[] buffer = new byte[4096];// 缓冲区
			output = new BufferedOutputStream(response.getOutputStream());
			input = new BufferedInputStream(new FileInputStream(file));
			int n = -1;
			//遍历，开始下载
			while ((n = input.read(buffer, 0, 4096)) > -1) {
				output.write(buffer, 0, n);
			}
			output.flush(); //不可少
			response.flushBuffer();//不可少
		} catch (Exception e) {
			//异常自己捕捉
			logger.error("读取excel模板异常：", e);
		} finally {
			//关闭流，不可少
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	
	/***
	 * 
	 * 上传科目余额表
	 * 
	 * @param request
	 * @param response
	 * @param reportYear
	 * @param reportMonth
	 */
	@RequestMapping("uploadAccountBalance.json")
	public void uploadAccountBalance(	HttpServletRequest request,
										HttpServletResponse response,
										@RequestParam(value = "year", required = true) int reportYear,
										@RequestParam(value = "month", required = true) int reportMonth) {
		JSONObject json = new JSONObject();
		try {
			ExcelData excel = ExcelUtil.uploadExcel(request);
			if (null != excel && excel.getRows() > 1 && excel.getColumns() == 11) {
				AccountBalanceOrder order = new AccountBalanceOrder();
				order.setReportYear(reportYear);
				order.setReportMonth(reportMonth);
				SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
				if (sessionLocal != null) {
					order.setOperatorId(sessionLocal.getUserId());
					order.setOperatorAccount(sessionLocal.getUserName());
					order.setOperatorName(sessionLocal.getRealName());
				}
				
				List<AccountBalanceDataOrder> datas = new ArrayList<>();
				for (int i = 1; i < excel.getRows(); i++) { //去掉第一行，表头
					String[] data = excel.getDatas()[i];
					AccountBalanceDataOrder abdOrder = new AccountBalanceDataOrder();
					abdOrder.setCode(data[0]);
					abdOrder.setName(data[1]);
					abdOrder.setCurrency(data[2]);
					abdOrder.setInitialDebitBalance(parseMoney(data[3]));
					abdOrder.setInitialCreditBalance(parseMoney(data[4]));
					abdOrder.setCurrentDebitAmount(parseMoney(data[5]));
					abdOrder.setCurrentCreditAmount(parseMoney(data[6]));
					abdOrder.setYearDebitAmount(parseMoney(data[7]));
					abdOrder.setYearCreditAmount(parseMoney(data[8]));
					abdOrder.setEndingDebitBalance(parseMoney(data[9]));
					abdOrder.setEndingCreditBalance(parseMoney(data[10]));
					datas.add(abdOrder);
				}
				order.setDatas(datas);
				
				FcsBaseResult result = accountBalanceServiceClient.save(order);
				if (result.isSuccess()) {
					json.put("success", true);
					json.put("message", "上传成功");
				} else {
					json.put("success", false);
					json.put("message", "上传失败：" + result.getMessage());
				}
				
			} else {
				json.put("success", false);
				json.put("message", "上传失败：数据模板不正确");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			json.put("success", false);
			json.put("message", "上传异常" + e.getMessage());
		}
		
		try {
			returnJson(response, true, json);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private static Money parseMoney(String s) {
		if (StringUtil.isBlank(s)) {
			return new Money(0L);
		}
		
		Double d = Double.valueOf(s);
		return new Money(d);
	}
	
	private SubmissionInfo setAnnualObjective(SubmissionCodeEnum submissionCodeEnum) {
		SubmissionInfo info = new SubmissionInfo();
		List<SubmissionDataInfo> dataInfos = Lists.newArrayList();
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
	//	UserDetailInfo userDetail = bpmUserQueryService.findUserDetailByUserId(sessionLocal
	//		.getUserId());
	//	List<Org> orgList = userDetail.getOrgList();
		SysOrg sysOrg=sessionLocal.getUserInfo().getPrimaryOrg();
		String userDeptCode="";
		if(sysOrg!=null){
			userDeptCode=sessionLocal.getUserInfo().getPrimaryOrg().getCode();
		}
		String deptCodes = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_ANNUAL_OBJECTIVE_DEPT_CODE.code());
		if (deptCodes != null) {
			String deptCode[] = deptCodes.split(",");
			if (submissionCodeEnum == SubmissionCodeEnum.ANNUAL_OBJECTIVE) {
				for (String code : deptCode) {
//					for (Org org : orgList) {
						if (StringUtil.isNotBlank(userDeptCode)&&userDeptCode.equals(code)) {
							dataInfos.add(setDataInfo(code));
							break;
						}
//					}
				}
			} else {
				for (String code : deptCode) {
					dataInfos.add(setDataInfo(code));
				}
			}
		}
		
		info.setData(dataInfos);
		return info;
	}
	
	private SubmissionDataInfo setDataInfo(String code) {
		SubmissionDataInfo dataInfo = new SubmissionDataInfo();
		String result = submissionServiceClient.findDeptInfoByDeptCode(code);
		if (result != null) {
			HashMap<String, Object> resultMap = MiscUtil.parseJSON(result);
			SysOrg sysOrg = makeOrg(resultMap);
			dataInfo.setData2(sysOrg.getCode());
			dataInfo.setData3(sysOrg.getOrgName());
		}
		return dataInfo;
	}
	
	private SysOrg makeOrg(HashMap<String, Object> resultMap) {
		if ("1".equals(String.valueOf(resultMap.get("result")))) {
			Map<String, Object> sysOrgMap = (Map<String, Object>) resultMap.get("sysOrg");
			SysOrg sysOrg = new SysOrg();
			sysOrg.setCode(String.valueOf(sysOrgMap.get("code")));
			sysOrg.setCreateBy(Long.parseLong(String.valueOf(sysOrgMap.get("createBy"))));
			sysOrg.setOrgId(Long.parseLong(String.valueOf(sysOrgMap.get("orgId"))));
			sysOrg.setOrgName(String.valueOf(sysOrgMap.get("orgName")));
			return sysOrg;
		} else {
			return null;
		}
	}
	
}
