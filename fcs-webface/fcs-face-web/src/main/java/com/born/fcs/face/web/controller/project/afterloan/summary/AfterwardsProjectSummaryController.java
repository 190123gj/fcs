package com.born.fcs.face.web.controller.project.afterloan.summary;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONObject;
import com.born.bpm.service.client.user.SysOrg;
import com.born.fcs.face.integration.bpm.service.info.UserInfo;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.summary.AfterwardsProjectSummaryInfo;
import com.born.fcs.pm.ws.order.summary.AfterwardsProjectSummaryOrder;
import com.born.fcs.pm.ws.order.summary.AfterwardsProjectSummaryQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.summary.AfterwardsProjectSummaryResult;

@Controller
@RequestMapping("projectMg/afterwardsSummary")
public class AfterwardsProjectSummaryController extends WorkflowBaseController {
	
	final static String vm_path = "/projectMg/afterLoanMg/afterConfirmProject/";
	
	/**
	 * 保后项目汇总列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String summaryList(AfterwardsProjectSummaryQueryOrder order, Model model) {
		
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			UserInfo userInfo = sessionLocal.getUserInfo();
			//业务 分管副总 可以看全部，总经理，董事长全部
			if (DataPermissionUtil.isChairMan() || DataPermissionUtil.isGeneralManager()
				|| DataPermissionUtil.isSystemAdministrator()) {//拥有所有数据权限
				order.setLoginUserId(0);
				order.setDeptIdList(null);
			} else if (userInfo != null) {
				order.setDeptIdList(userInfo.getDeptIdList());
			}
			order.setDraftUserId(sessionLocal.getUserId());
		}
		QueryBaseBatchResult<AfterwardsProjectSummaryInfo> batchResult = afterwardsProjectSummaryServiceClient
			.query(order);
		//		model.addAttribute("isBusinessDirector", isBusinessDirector());
		model.addAttribute("isBusinessZHG", DataPermissionUtil.isBusinessZHG());//是否业务部综合岗
		model.addAttribute("conditions", order);
		model.addAttribute("formStatus", FormStatusEnum.getAllEnum());
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		JSONObject result = new JSONObject();
		if (sessionLocal == null) {
			result.put("success", false);
			result.put("message", "您未登陆或登陆已失效");
		} else {
			SysOrg dept = sessionLocal.getUserInfo().getDept();
			if (dept != null) {
				Calendar now = Calendar.getInstance();
				int year = now.get(Calendar.YEAR);//当前年
				int season = getSeason(councilServiceClient.getSysDate());//当前季度
				String reportPeriod = "";
				AfterwardsProjectSummaryInfo summaryInfo = null;
				if (season == 1) {
					year = year - 1; //用来计算期数
					season = 4;
					reportPeriod = year + "年第" + season + "期";
					summaryInfo = afterwardsProjectSummaryServiceClient
						.findByDeptCodeAndReportPeriod(dept.getCode(), reportPeriod);
				} else {
					season = season - 1;
					reportPeriod = year + "年第" + season + "期";
					summaryInfo = afterwardsProjectSummaryServiceClient
						.findByDeptCodeAndReportPeriod(dept.getCode(), reportPeriod);
				}
				if (summaryInfo != null) {
					model.addAttribute("formId", summaryInfo.getFormId());
				}
			}
		}
		return vm_path + "list.vm";
	}
	
	/**
	 * 去新增保后项目汇总
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("toAddSummary.htm")
	public String toAddSummary(HttpServletRequest request, HttpServletResponse response, Model model) {
		JSONObject result = new JSONObject();
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal == null) {
			result.put("success", false);
			result.put("message", "您未登陆或登陆已失效");
			
		} else {
			SysOrg dept = sessionLocal.getUserInfo().getDept();
			if (dept != null) {
				//				dept.getOrgId();
				//				dept.getCode();
				//				dept.getOrgName();
				//				dept.getPath();
				//				dept.getOrgPathname();
				Calendar now = Calendar.getInstance();
				int year = now.get(Calendar.YEAR);//当前年
				int season = getSeason(councilServiceClient.getSysDate());//当前季度
				String reportPeriod = "";
				AfterwardsProjectSummaryInfo summaryInfo = null;
				if (season == 1) {
					year = year - 1; //用来计算期数
					season = 4;
					reportPeriod = year + "年第" + season + "期";
					summaryInfo = afterwardsProjectSummaryServiceClient
						.findByDeptCodeAndReportPeriod(dept.getCode(), reportPeriod);
				} else {
					season = season - 1;
					reportPeriod = year + "年第" + season + "期";
					summaryInfo = afterwardsProjectSummaryServiceClient
						.findByDeptCodeAndReportPeriod(dept.getCode(), reportPeriod);
					model.addAttribute("afterwardsProjectSummaryInfo", summaryInfo);
				}
				if (summaryInfo != null) {
					model.addAttribute("formId", summaryInfo.getFormId());
				}
				model.addAttribute("year", year);
				model.addAttribute("season", season);//季度
				DateUtil.simpleFormat(new Date());
				List<String> busiTypeList = Lists.newArrayList();
				if (ProjectUtil.isGuarantee("1")) {
					busiTypeList.add("1");
				}
				if (ProjectUtil.isGuarantee("2")) {
					busiTypeList.add("2");
				}
				
				if (ProjectUtil.isGuarantee("3")) {
					busiTypeList.add("3");
				}
				
				AfterwardsProjectSummaryResult summaryResult = afterwardsProjectSummaryServiceClient
					.findProjectSummaryInfoByCondition(true, getCurrentQuarterStartTime(),
						dept.getCode());
				if (summaryResult != null) {
					model.addAttribute("guaranteeHouseholds", summaryResult.getHs());
					model.addAttribute("guaranteeReleasingAmount", summaryResult.getAmount());
				}
				//				//担保在保余额
				//				Money guaranteeReleasingAmount = afterwardsProjectSummaryServiceClient
				//					.findReleasingAmountByCondition(busiTypeList, getCurrentQuarterStartTime(),
				//						dept.getCode());
				
				//				List<String> busiTypeList1 = Lists.newArrayList();
				//				busiTypeList1.add("4");
				//计算委贷户数  在保余额
				//				int loanHouseholds = afterwardsProjectSummaryServiceClient
				//					.findHouseholdsByCondition(busiTypeList1, getCurrentQuarterStartTime(),
				//						dept.getCode());
				AfterwardsProjectSummaryResult summaryResult1 = afterwardsProjectSummaryServiceClient
					.findProjectSummaryInfoByCondition(false, getCurrentQuarterStartTime(),
						dept.getCode());
				
				if (summaryResult1 != null) {
					model.addAttribute("loanHouseholds", summaryResult1.getHs());
					model.addAttribute("loanReleasingAmount", summaryResult1.getAmount());
				}
				//委贷在保余额
				//				Money loanReleasingAmount = afterwardsProjectSummaryServiceClient
				//					.findReleasingAmountByCondition(busiTypeList1, getCurrentQuarterStartTime(),
				//						dept.getCode());
				
				model.addAttribute("dept", dept);
			}
		}
		
		return vm_path + "addProject.vm";
	}
	
	/**
	 * 保存保后项目汇总信息
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveSummary.htm")
	@ResponseBody
	public JSONObject saveSummary(HttpServletRequest request, HttpServletResponse response,
									AfterwardsProjectSummaryOrder order, Model model) {
		String tipPrefix = " 保存保后项目汇总信息";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			// 初始化Form验证信息
			FormBaseResult result = null;
			order.setCheckIndex(0);
			order.setFormId(order.getFormId());
			order.setFormCode(FormCodeEnum.AFTERWARDS_PROJECT_SUMMARY);
			order.setUserId(sessionLocal.getUserId());
			order.setUserName(sessionLocal.getRealName());
			order.setUserAccount(sessionLocal.getUserName());
			
			order.setSubmitManId(sessionLocal.getUserId());
			order.setSubmitManAccount(sessionLocal.getUserName());
			order.setSubmitManName(sessionLocal.getRealName());
			
			order.setUserId(sessionLocal.getUserId());
			order.setUserName(sessionLocal.getRealName());
			order.setUserAccount(sessionLocal.getUserName());
			setSessionLocalInfo2Order(order);
			result = afterwardsProjectSummaryServiceClient.save(order);
			jsonObject.put("formId", result.getFormInfo().getFormId());
			if (result.isSuccess() && order.getCheckStatus() == 1) {
				jsonObject.put("success", true);
				jsonObject.put("status", "SUBMIT");
				jsonObject.put("message", "提交成功");
			} else if (result.isSuccess() && order.getCheckStatus() == 0) {
				jsonObject.put("success", true);
				jsonObject.put("status", "hold");
				jsonObject.put("message", "暂存成功");
			} else {
				jsonObject.put("success", false);
				jsonObject.put("message", result.getMessage());
			}
		} catch (Exception e) {
			jsonObject = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 查看保后项目汇总详情
	 *
	 * @param companyId
	 * @param model
	 * @return
	 */
	@RequestMapping("viewSummary.htm")
	public String viewSummary(long formId, Model model) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		String tipPrefix = "查看保后项目汇总详情";
		try {
			AfterwardsProjectSummaryInfo afterwardsProjectSummaryInfo = afterwardsProjectSummaryServiceClient
				.findByFormId(formId);
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			SysOrg dept = sessionLocal.getUserInfo().getDept();
			model.addAttribute("afterwardsProjectSummaryInfo", afterwardsProjectSummaryInfo);
			model.addAttribute("guaranteeHouseholds",
				afterwardsProjectSummaryInfo.getGuaranteeHouseholds());
			model.addAttribute("guaranteeReleasingAmount",
				afterwardsProjectSummaryInfo.getGuaranteeReleasingAmount());
			model.addAttribute("loanHouseholds", afterwardsProjectSummaryInfo.getLoanHouseholds());
			model.addAttribute("loanReleasingAmount",
				afterwardsProjectSummaryInfo.getLoanReleasingAmount());
			model.addAttribute("dept", dept);
			model.addAttribute("form", form);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		
		return vm_path + "viewProject.vm";
	}
	
	/**
	 * 编辑保后项目汇总
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("editSummary.htm")
	public String editSummary(long formId, Model model) {
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		String tipPrefix = "编辑保后汇总";
		try {
			//			AfterwardsProjectSummaryInfo afterwardsProjectSummaryInfo = afterwardsProjectSummaryServiceClient
			//				.findById(id);
			AfterwardsProjectSummaryInfo afterwardsProjectSummaryInfo = afterwardsProjectSummaryServiceClient
				.findByFormId(formId);
			
			model.addAttribute("afterwardsProjectSummaryInfo", afterwardsProjectSummaryInfo);
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			SysOrg dept = sessionLocal.getUserInfo().getDept();
			if (dept != null) {
				Calendar now = Calendar.getInstance();
				int year = now.get(Calendar.YEAR);//当前年
				int season = getSeason(councilServiceClient.getSysDate());//当前季度
				if (season == 1) {
					year = year - 1; //用来计算期数
					season = 4;
					
				} else {
					season = season - 1;
				}
				model.addAttribute("year", year);
				model.addAttribute("season", season);//季度
				DateUtil.simpleFormat(new Date());
				model.addAttribute("guaranteeHouseholds",
					afterwardsProjectSummaryInfo.getGuaranteeHouseholds());
				model.addAttribute("guaranteeReleasingAmount",
					afterwardsProjectSummaryInfo.getGuaranteeReleasingAmount());
				model.addAttribute("loanHouseholds",
					afterwardsProjectSummaryInfo.getLoanHouseholds());
				model.addAttribute("loanReleasingAmount",
					afterwardsProjectSummaryInfo.getLoanReleasingAmount());
				model.addAttribute("dept", dept);
				model.addAttribute("isEdit", "true");//是否编辑
				model.addAttribute("form", form);
			}
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		
		return vm_path + "addProject.vm";
	}
	
	/**
	 * 保后汇总审核
	 *
	 * @param formId
	 * @param model
	 * @return
	 */
	
	@RequestMapping("audit.htm")
	public String audit(long formId, String toPage, HttpServletRequest request, Integer toIndex,
						Model model, HttpSession session) {
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		String tipPrefix = "保后项目汇总审核";
		try {
			AfterwardsProjectSummaryInfo afterwardsProjectSummaryInfo = afterwardsProjectSummaryServiceClient
				.findByFormId(formId);
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			SysOrg dept = sessionLocal.getUserInfo().getDept();
			model.addAttribute("afterwardsProjectSummaryInfo", afterwardsProjectSummaryInfo);
			model.addAttribute("guaranteeHouseholds",
				afterwardsProjectSummaryInfo.getGuaranteeHouseholds());
			model.addAttribute("guaranteeReleasingAmount",
				afterwardsProjectSummaryInfo.getGuaranteeReleasingAmount());
			model.addAttribute("loanHouseholds", afterwardsProjectSummaryInfo.getLoanHouseholds());
			model.addAttribute("loanReleasingAmount",
				afterwardsProjectSummaryInfo.getLoanReleasingAmount());
			model.addAttribute("dept", dept);
			model.addAttribute("form", form);
			if (toIndex == null)
				toIndex = 0;
			model.addAttribute("toIndex", toIndex);
			
			initWorkflow(model, form, request.getParameter("taskId"));
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		return vm_path + "viewProject.vm";
	}
	
	/**
	 * 保后汇总审核 部门负责人
	 */
	@RequestMapping("audit/departmentHead.htm")
	public String departmentHead(long formId, String toPage, HttpServletRequest request,
									Integer toIndex, Model model, HttpSession session) {
		model.addAttribute("departmentHead", "YES");
		model.addAttribute("formId", formId);
		audit(formId, toPage, request, toIndex, model, session);
		return vm_path + "viewProject.vm";
	}
	
	/**
	 * 删除保后项目汇总信息
	 *
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping("deleteSummary.htm")
	@ResponseBody
	public JSONObject deleteSummary(Long summaryId, Model model) {
		JSONObject result = new JSONObject();
		int num = afterwardsProjectSummaryServiceClient.deleteById(summaryId);
		if (num > 0) {
			result.put("success", true);
			result.put("message", "删除成功");
		} else {
			result.put("false", false);
			result.put("message", "删除失败");
		}
		return result;
	}
	
	/**
	 * 当前季度的开始时间，即2012-01-1 00:00:00
	 *
	 * @return
	 */
	public Date getCurrentQuarterStartTime() {
		final SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
		;
		final SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		Date now = null;
		try {
			if (currentMonth >= 1 && currentMonth <= 3)
				c.set(Calendar.MONTH, 0);
			else if (currentMonth >= 4 && currentMonth <= 6)
				c.set(Calendar.MONTH, 3);
			else if (currentMonth >= 7 && currentMonth <= 9)
				c.set(Calendar.MONTH, 6);
			else if (currentMonth >= 10 && currentMonth <= 12)
				c.set(Calendar.MONTH, 9);
			c.set(Calendar.DATE, 1);
			//            DateUtil.simpleFormat(
			now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}
	
	/**
	 * 
	 * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
	 * 
	 * @param date
	 * @return
	 */
	public static int getSeason(Date date) {
		
		int season = 0;
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);
		switch (month) {
			case Calendar.JANUARY:
			case Calendar.FEBRUARY:
			case Calendar.MARCH:
				season = 1;
				break;
			case Calendar.APRIL:
			case Calendar.MAY:
			case Calendar.JUNE:
				season = 2;
				break;
			case Calendar.JULY:
			case Calendar.AUGUST:
			case Calendar.SEPTEMBER:
				season = 3;
				break;
			case Calendar.OCTOBER:
			case Calendar.NOVEMBER:
			case Calendar.DECEMBER:
				season = 4;
				break;
			default:
				break;
		}
		return season;
	}
}
