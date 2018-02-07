package com.born.fcs.face.web.controller.base;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.com.google.common.collect.Maps;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.crm.ws.service.enums.AuditStatusEnum;
import com.born.fcs.crm.ws.service.enums.CertTypesEnum;
import com.born.fcs.crm.ws.service.enums.ChanalTypeEnum;
import com.born.fcs.crm.ws.service.enums.CitizenTypeEnum;
import com.born.fcs.crm.ws.service.enums.ComparEnums;
import com.born.fcs.crm.ws.service.enums.CustomerRelationEnum;
import com.born.fcs.crm.ws.service.enums.CustomerSourceEnum;
import com.born.fcs.crm.ws.service.enums.EvaluetingLevelEnum;
import com.born.fcs.crm.ws.service.enums.FinancialInstitutionsEnum;
import com.born.fcs.crm.ws.service.enums.MaritalStatusEnum;
import com.born.fcs.crm.ws.service.enums.SexEnum;
import com.born.fcs.crm.ws.service.info.VerifyMessageInfo;
import com.born.fcs.crm.ws.service.order.CustomerBaseOrder;
import com.born.fcs.crm.ws.service.order.VerifyMessageOrder;
import com.born.fcs.face.integration.bpm.service.info.UserInfo;
import com.born.fcs.face.integration.bpm.service.info.WorkflowProcessLog;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.bean.AttachmentBean;
import com.born.fcs.face.web.controller.login.GeetestLib;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.fm.ws.enums.CostDirectionEnum;
import com.born.fcs.fm.ws.enums.ReportCompanyEnum;
import com.born.fcs.fm.ws.enums.SubjectStatusEnum;
import com.born.fcs.fm.ws.info.bank.BankMessageInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationInfo;
import com.born.fcs.fm.ws.info.payment.FormLabourCapitalInfo;
import com.born.fcs.fm.ws.info.payment.FormTravelExpenseInfo;
import com.born.fcs.fm.ws.order.bank.BankMessageQueryOrder;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.base.QueryFormBase;
import com.born.fcs.pm.ws.base.QueryPermissionPageBase;
import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CheckStatusEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.CompanyNameEnum;
import com.born.fcs.pm.ws.enums.ContractStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.EnterpriseNatureEnum;
import com.born.fcs.pm.ws.enums.EnterpriseScaleEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.info.common.CommonAttachmentInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.council.CouncilInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryRiskHandleInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeApplySearchInfo;
import com.born.fcs.pm.ws.info.projectissueinformation.ProjectBondReinsuranceInformationInfo;
import com.born.fcs.pm.ws.order.base.FormOrderBase;
import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.born.fcs.pm.ws.order.base.ProjectFormOrderBase;
import com.born.fcs.pm.ws.order.common.CommonAttachmentOrder;
import com.born.fcs.pm.ws.order.common.CommonAttachmentQueryOrder;
import com.born.fcs.pm.ws.order.common.SimpleFormOrder;
import com.born.fcs.pm.ws.order.council.CouncilQueryOrder;
import com.born.fcs.pm.ws.order.formchange.FormChangeApplyQueryOrder;
import com.born.fcs.pm.ws.order.formchange.FormCheckCanChangeOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.FormService;
import com.born.fcs.pm.ws.service.report.order.ProjectReportQueryOrder;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.bornsoft.pub.enums.UserTypeEnum;
import com.bornsoft.pub.order.risk.SynWatchListOrder;
import com.bornsoft.pub.order.risk.SynWatchListOrder.WatchListInfo;
import com.bornsoft.utils.base.BornSynResultBase;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.result.ResultBase;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class BaseController extends BaseAutowiredController {
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		String[] nameArray = getDateInputNameArray();
		if (nameArray != null && nameArray.length > 0) {
			for (int i = 0; i < nameArray.length; i++) {
				binder.registerCustomEditor(Date.class, nameArray[i], new CustomDateEditor(
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
				
			}
		}
		String[] dateDayNameArray = getDateInputDayNameArray();
		if (dateDayNameArray != null && dateDayNameArray.length > 0) {
			for (int i = 0; i < dateDayNameArray.length; i++) {
				binder.registerCustomEditor(Date.class, dateDayNameArray[i], new CustomDateEditor(
					new SimpleDateFormat("yyyy-MM-dd"), true));
				
			}
		}
		String[] moneyNameArray = getMoneyInputNameArray();
		if (dateDayNameArray != null && moneyNameArray.length > 0) {
			for (int i = 0; i < moneyNameArray.length; i++) {
				binder.registerCustomEditor(Money.class, moneyNameArray[i],
					new CommonBindingInitializer());
			}
		}
	}
	
	protected String[] getDateInputNameArray() {
		return new String[0];
	}
	
	protected String[] getDateInputDayNameArray() {
		return new String[0];
	}
	
	protected String[] getMoneyInputNameArray() {
		return new String[0];
	}
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected String sendUrl(HttpServletResponse response, String url) {
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	protected String sendUrl(HttpServletResponse response, String url, Map<String, String> params) {
		try {
			if (params != null && !params.isEmpty()) {
				params.remove(GeetestLib.fn_geetest_challenge);
				params.remove(GeetestLib.fn_geetest_validate);
				params.remove(GeetestLib.fn_geetest_seccode);
				int i = 0;
				for (String key : params.keySet()) {
					if (i == 0) {
						url += "?" + key + "=" + params.get(key);
					} else {
						url += "&" + key + "=" + params.get(key);
					}
					i++;
				}
			}
			logger.info("url=" + url);
			response.sendRedirect(url);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 页面审核历史
	 * @param form
	 * @param model
	 */
	protected void setAuditHistory2Page(FormInfo form, Model model) {
		if (form != null && form.getActInstId() > 0) {
			QueryBaseBatchResult<WorkflowProcessLog> batchResult = workflowEngineWebClient
				.getProcessOpinionByActInstId(String.valueOf(form.getActInstId()));
			if (batchResult != null) {
				List<WorkflowProcessLog> list = batchResult.getPageList();
				Collections.reverse(list);
				model.addAttribute("auditHisList", list);
			}
		}
	}
	
	/**
	 * 签报的时候带过来的信息
	 * @param session
	 * @param model
	 */
	protected void setFormChangeApplyInfo(HttpServletRequest request, HttpSession session,
											Model model) {
		model.addAttribute("changeApply", session.getAttribute("changeApply"));
		model.addAttribute("changeRecord", session.getAttribute("changeRecord"));
		model.addAttribute("changeApplyForm", session.getAttribute("changeApplyForm"));
		model.addAllAttributes(request.getParameterMap());
	}
	
	/**
	 * 检查是否可签报
	 * @param projectCode
	 * @param changeForm
	 */
	protected void checkCanApplyChange(FormCheckCanChangeOrder checkOrder) {
		FcsBaseResult checkResult = formChangeApplyServiceClient.checkCanChange(checkOrder);
		if (!checkResult.isSuccess()) {
			throw ExceptionFactory.newFcsException(checkResult.getFcsResultEnum(),
				checkResult.getMessage());
		}
	}
	
	protected JSONObject toJSONResult(JSONObject json, ResultBase baseResult, String extraTrueMsg,
										String extraFalseMsg) {
		if (baseResult != null && baseResult.isSuccess()) {
			json.put("success", true);
			if (StringUtil.isNotEmpty(extraTrueMsg)) {
				json.put("message", extraTrueMsg);
			} else {
				json.put("message", baseResult.getMessage());
			}
			if (baseResult instanceof FormBaseResult) {
				FormInfo form = ((FormBaseResult) baseResult).getFormInfo();
				if (form != null)
					json.put("form", form.toJson());
			}
		} else {
			json.put("success", false);
			if (StringUtil.isNotEmpty(extraFalseMsg)) {
				json.put("message", extraFalseMsg);
			} else {
				json.put("message", baseResult == null ? "" : baseResult.getMessage());
			}
		}
		return json;
	}
	
	/**
	 * ResultBase 转换为 JSONObject Result
	 * 
	 * @param baseResult
	 * @param extraTrueMsg 格外指定成功消息
	 * @param extraFalseMsg 格外指定失败消息
	 * @return
	 */
	protected JSONObject toJSONResult(ResultBase baseResult, String extraTrueMsg,
										String extraFalseMsg) {
		JSONObject result = new JSONObject();
		if (baseResult != null && baseResult.isSuccess()) {
			result.put("success", true);
			if (StringUtil.isNotEmpty(extraTrueMsg)) {
				result.put("message", extraTrueMsg);
			} else {
				result.put("message", baseResult.getMessage());
			}
			
		} else {
			result.put("success", false);
			if (StringUtil.isNotEmpty(extraFalseMsg)) {
				result.put("message", extraFalseMsg);
			} else {
				result.put("message", baseResult == null ? "" : baseResult.getMessage());
			}
		}
		return result;
	}
	
	/**
	 * ResultBase 转换为 JSONObject Result
	 * 
	 * @param baseResult
	 * @return
	 */
	protected JSONObject toJSONResult(ResultBase baseResult) {
		return toJSONResult(baseResult, null, null);
	}
	
	/**
	 * ResultBase 转换为 JSONObject Result
	 * 
	 * @param baseResult
	 * @return
	 */
	protected JSONObject toJSONResult(JSONObject json, ResultBase baseResult) {
		return toJSONResult(json, baseResult, null, null);
	}
	
	/**
	 * 处理保存结果
	 * 
	 * @param result 保存结果
	 * @param tipPrefix 提示信息前缀
	 * @return
	 */
	protected JSONObject toJSONResult(FcsBaseResult result, String tipPrefix) {
		JSONObject json = new JSONObject();
		if (null != result && result.isSuccess()) {
			json.put("success", true);
			json.put("message", tipPrefix + "成功");
			if (result instanceof FormBaseResult) {
				FormInfo form = ((FormBaseResult) result).getFormInfo();
				if (form != null)
					json.put("form", form.toJson());
			}
			json.put("id", result.getKeyId());
		} else {
			json.put("success", false);
			json.put("message", tipPrefix + "失败 :" + (result == null ? "" : result.getMessage()));
		}
		return json;
	}
	
	/**
	 * 标准化返回处理保存结果
	 * @param data null表示处理失败
	 * @param tipPrefix
	 * @return
	 */
	protected JSONObject toStandardResult(JSON data, String tipPrefix) {
		JSONObject json = new JSONObject();
		if (null != data) {
			json.put("success", true);
			json.put("message", tipPrefix + "成功");
			json.put("data", data);
			
		} else {
			json.put("success", false);
			json.put("message", tipPrefix + "失败");
		}
		return json;
	}
	
	/**
	 * 处理保存异常信息
	 * 
	 * @param tipPrefix 提示信息前缀
	 * @param exMsg 异常消息
	 * @return
	 */
	protected JSONObject toJSONResult(String tipPrefix, Exception e) {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", tipPrefix + "出错[" + e.getMessage() + "]");
		return json;
	}
	
	protected void printHttpResponse(HttpServletResponse response,
										com.alibaba.fastjson.JSONAware json) {
		try {
			
			response.setContentType("json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(json.toJSONString());
		} catch (IOException e) {
			logger.error("response. getWriter print error ", e);
		}
	}
	
	/**
	 * 将登陆的信息设置到Order
	 * @param sessionLocal
	 * @param order
	 */
	protected void setSessionLocalInfo2Order(FormOrderBase order) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			order.setUserId(sessionLocal.getUserId());
			order.setUserAccount(sessionLocal.getUserName());
			order.setUserName(sessionLocal.getRealName());
			order.setUserIp(sessionLocal.getRemoteAddr());
			
			UserDetailInfo userInfo = sessionLocal.getUserDetailInfo();
			if (userInfo != null) {
				order.setUserMobile(userInfo.getMobile());
				order.setUserEmail(userInfo.getEmail());
				Org dept = userInfo.getPrimaryOrg();
				if (dept != null) {
					order.setDeptId(dept.getId());
					order.setDeptCode(dept.getCode());
					order.setDeptName(dept.getName());
					order.setDeptPath(dept.getPath());
					order.setDeptPathName(dept.getPathName());
				}
			}
		}
	}
	
	/**
	 * 将登陆的信息设置到Order
	 * @param sessionLocal
	 * @param order
	 */
	protected void setSessionLocalInfo2Order(SimpleFormOrder order) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			order.setUserId(sessionLocal.getUserId());
			order.setUserAccount(sessionLocal.getUserName());
			order.setUserName(sessionLocal.getRealName());
			order.setUserIp(sessionLocal.getRemoteAddr());
			
			UserDetailInfo userInfo = sessionLocal.getUserDetailInfo();
			if (userInfo != null) {
				order.setUserMobile(userInfo.getMobile());
				order.setUserEmail(userInfo.getEmail());
				Org dept = userInfo.getPrimaryOrg();
				if (dept != null) {
					order.setDeptId(dept.getId());
					order.setDeptCode(dept.getCode());
					order.setDeptName(dept.getName());
					order.setDeptPath(dept.getPath());
					order.setDeptPathName(dept.getPathName());
				}
			}
		}
	}
	
	/**
	 * 将登陆的信息设置到Order
	 * @param order
	 */
	protected void setSessionLocalInfo2Order(ProcessOrder order) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			order.setUserId(sessionLocal.getUserId());
			order.setUserAccount(sessionLocal.getUserName());
			order.setUserName(sessionLocal.getRealName());
			order.setUserIp(sessionLocal.getRemoteAddr());
		}
	}
	
	/**
	 * 将登陆的信息设置到Order
	 * @param order
	 */
	protected void setSessionLocalInfo2Order(QueryProjectBase order) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (hasAllDataPermission()) {//拥有所有数据权限
				order.setLoginUserId(0);
				order.setDeptIdList(null);
			} else if (hasPrincipalDataPermission() && userInfo != null) { //拥有所负责的数据权限，按照部门维度查询数据
				order.setLoginUserId(sessionLocal.getUserId());
				order.setDeptIdList(userInfo.getDeptIdList());
			} else { //按照人员维度查询数据
				order.setLoginUserId(sessionLocal.getUserId());
				order.setDeptIdList(null);
			}
			//查看草稿的人员
			order.setDraftUserId(sessionLocal.getUserId());
		}
	}
	
	/**
	 * 将登陆的信息设置到Order
	 * @param order
	 */
	protected void setSessionLocalInfo2Order(QueryFormBase order) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (hasAllDataPermission()) {//拥有所有数据权限
				order.setLoginUserId(0);
				order.setDeptIdList(null);
			} else if (hasPrincipalDataPermission() && userInfo != null) { //拥有所负责的数据权限，按照部门维度查询数据
				order.setLoginUserId(sessionLocal.getUserId());
				order.setDeptIdList(userInfo.getDeptIdList());
			} else { //按照人员维度查询数据
				order.setLoginUserId(sessionLocal.getUserId());
				order.setDeptIdList(null);
			}
			//查看草稿的人员
			order.setDraftUserId(sessionLocal.getUserId());
		}
	}
	
	/**
	 * 将登陆的信息设置到Order
	 * @param order
	 */
	protected void setSessionLocalInfo2Order(QueryPermissionPageBase order) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (hasAllDataPermission()) {//拥有所有数据权限
				order.setLoginUserId(0);
				order.setDeptIdList(null);
			} else if (hasPrincipalDataPermission() && userInfo != null) { //拥有所负责的数据权限，按照部门维度查询数据
				order.setLoginUserId(sessionLocal.getUserId());
				order.setDeptIdList(userInfo.getDeptIdList());
			} else { //按照人员维度查询数据
				order.setLoginUserId(sessionLocal.getUserId());
				order.setDeptIdList(null);
			}
			//查看草稿的人员
			order.setDraftUserId(sessionLocal.getUserId());
		}
	}
	
	/**
	 * 将登陆的信息设置到Order
	 * @param order
	 */
	protected void setSessionLocalInfo2Order(ProjectReportQueryOrder order) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (hasAllDataPermission()) {//拥有所有数据权限
				order.setLoginUserId(0);
				order.setDeptIdList(null);
			} else if (hasPrincipalDataPermission() && userInfo != null) { //拥有所负责的数据权限，按照部门维度查询数据
				order.setLoginUserId(sessionLocal.getUserId());
				order.setDeptIdList(userInfo.getDeptIdList());
			} else { //按照人员维度查询数据
				order.setLoginUserId(sessionLocal.getUserId());
				order.setDeptIdList(null);
			}
		}
	}
	
	/** 查看客户权限设置 */
	protected void queryCustomerPermissionSet(QueryPermissionPageBase order) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (hasAllDataPermission() || DataPermissionUtil.isBelong2Xinhui()) {//拥有所有数据权限
				order.setLoginUserId(0);
				order.setDeptIdList(null);
			} else if (DataPermissionUtil.isBusinessFgfz()
						|| DataPermissionUtil.isXinHuiFGFZ(sessionLocal.getUserDetailInfo())
						|| DataPermissionUtil.isBusiFZR() || DataPermissionUtil.isXinHuiFzr()) { //分管副总和总监
				order.setLoginUserId(0);
				order.setDeptIdList(userInfo.getDeptIdList());
			} else { //按照人员维度查询数据
				order.setLoginUserId(sessionLocal.getUserId());
				order.setDeptIdList(null);
			}
			
		}
	}
	
	/**
	 * 将登陆的信息设置到Order
	 * @param sessionLocal
	 * @param order
	 */
	protected void setSessionLocalInfo2Order(ReportOrder order) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			order.setOperatorId(sessionLocal.getUserId());
			order.setOperatorAccount(sessionLocal.getUserName());
			order.setOperatorName(sessionLocal.getRealName());
			UserDetailInfo userInfo = sessionLocal.getUserDetailInfo();
			if (userInfo != null) {
				Org dept = userInfo.getPrimaryOrg();
				if (dept != null) {
					order.setDeptId(dept.getId());
					order.setDeptCode(dept.getCode());
					order.setDeptName(dept.getName());
					order.setDeptPath(dept.getPath());
					order.setDeptPathName(dept.getPathName());
				}
			}
		}
	}
	
	/**
	 * 显示项目批复侧边栏
	 * @param projectCode
	 * @param model
	 */
	protected void showProjectApproval(String projectCode, Model model) {
		ProjectInfo projectInfo = projectServiceClient.queryByCode(projectCode, false);
		if (projectInfo != null && projectInfo.getIsApproval() == BooleanEnum.IS) {
			model.addAttribute("approvalCode", projectInfo.getSpCode());
		}
	}
	
	/**
	 * 不显示作废侧边栏
	 * @param model
	 */
	protected void showNoEndForm(Model model) {
		model.addAttribute("showNoEndForm", true);
	}
	
	/**
	 * 显示签报侧边栏
	 * @param projectCode
	 * @param model
	 */
	protected boolean showFormChangeApply(String projectCode) {
		FormChangeApplyQueryOrder order = new FormChangeApplyQueryOrder();
		order.setProjectCode(projectCode);
		order.setFormStatus(FormStatusEnum.APPROVAL.code());
		QueryBaseBatchResult<FormChangeApplySearchInfo> batchResult = formChangeApplyServiceClient
			.searchForm(order);
		if (batchResult != null && ListUtil.isNotEmpty(batchResult.getPageList())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 是否业务经理
	 * @return
	 */
	protected boolean isBusiManager() {
		return DataPermissionUtil.isBusiManager();
		
	}
	
	/**
	 * 是否风险经理
	 * @return
	 */
	protected boolean isRiskManager() {
		return DataPermissionUtil.isRiskManager();
		
	}
	
	/**
	 * 是否风控秘书
	 * @return
	 */
	protected boolean isRiskSecretary() {
		return DataPermissionUtil.isRiskSecretary();
		
	}
	
	/**
	 * 是否总经理秘书
	 * @return
	 */
	protected boolean isManagerSecretary() {
		return DataPermissionUtil.isManagerSecretary();
		
	}
	
	/**
	 * 是否信汇总经理秘书
	 * @return
	 */
	protected boolean isManagerSecretaryXH() {
		return DataPermissionUtil.isManagerSecretaryXH();
		
	}
	
	/**
	 * 是否法务经理
	 * @return
	 */
	protected boolean isLegalManager() {
		return DataPermissionUtil.isLegalManager();
	}
	
	/**
	 * 是否法务经理-领导
	 * @return
	 */
	protected boolean isLegalManagerLD() {
		return DataPermissionUtil.isLegalManagerLD();
	}
	
	/**
	 * 是否财务人员
	 * @return
	 */
	protected boolean isFinancialPersonnel() {
		return DataPermissionUtil.isFinancialPersonnel();
	}
	
	/**
	 * 是否财务应付岗
	 * @return
	 */
	protected boolean isFinancialYFG() {
		return DataPermissionUtil.isFinancialYFG();
	}
	
	/**
	 * 是否财务应收岗
	 * @return
	 */
	protected boolean isFinancialYSG() {
		return DataPermissionUtil.isFinancialYSG();
	}
	
	/**
	 * 是否信惠人员
	 * @return
	 */
	protected boolean isXinHuiPersonnel() {
		return DataPermissionUtil.isXinHuiPersonnel();
	}
	
	/**
	 * 是否业务总监
	 * @return
	 */
	protected boolean isBusinessDirector() {
		return DataPermissionUtil.isBusiFZR();
		
	}
	
	/**
	 * 是否风险管理部职员（风险经理）
	 * @return
	 */
	protected boolean isRiskZY() {
		return DataPermissionUtil.isRiskZY();
	}
	
	/**
	 * 是否风险管理部领导
	 * @return
	 */
	protected boolean isRiskLD() {
		return DataPermissionUtil.isRiskLD();
	}
	
	/**
	 * 是否拥有所有数据权限
	 * @return
	 */
	protected boolean hasAllDataPermission() {
		return DataPermissionUtil.hasAllDataPermission();
	}
	
	/**
	 * 是拥有所负责数据权限
	 * @return
	 */
	protected boolean hasPrincipalDataPermission() {
		return DataPermissionUtil.hasPrincipalDataPermission();
	}
	
	/**
	 * 业务类型是否是承销
	 * 
	 * @param busiType
	 * @return 是：true
	 */
	protected boolean isUnderwriting(String busiType) {
		return ProjectUtil.isUnderwriting(busiType);
	}
	
	/**
	 * 业务类型是否是诉讼保函
	 * 
	 * @param busiType
	 * @return 是：true
	 */
	protected boolean isLitigation(String busiType) {
		return ProjectUtil.isLitigation(busiType);
		
	}
	
	/**
	 * 业务类型是否发债
	 * @param busiType
	 * @return
	 */
	protected boolean isBond(String busiType) {
		return ProjectUtil.isBond(busiType);
		
	}
	
	/**
	 * 业务类型是否委贷
	 * @param busiType
	 * @return
	 */
	protected boolean isEntrusted(String busiType) {
		return ProjectUtil.isEntrusted(busiType);
	}
	
	/**
	 * 添加附件
	 * @param keyId
	 * @param request
	 * @param types
	 * @return
	 */
	protected FcsBaseResult addAttachfile(String keyId, HttpServletRequest request,
											String projectCode, String remark,
											CommonAttachmentTypeEnum... types) {
		FcsBaseResult result = new FcsBaseResult();
		if (null == types || types.length <= 0) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
			result.setMessage("没有附件数据");
			return result;
		}
		
		long uploaderId = 0L;
		String uploaderAccount = "";
		String uploaderName = "";
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			uploaderId = sessionLocal.getUserId();
			uploaderAccount = sessionLocal.getUserName();
			uploaderName = sessionLocal.getRealName();
		}
		
		List<CommonAttachmentOrder> orders = new ArrayList<CommonAttachmentOrder>();
		//先删除，再保存
		commonAttachmentServiceClient.deleteByBizNoModuleType(keyId, types);
		
		for (CommonAttachmentTypeEnum type : types) {
			if (null == type) {
				continue;
			}
			String pathValues = request.getParameter("pathName_" + type.code());
			if (StringUtil.isNotBlank(pathValues)) {
				String[] attachPaths = pathValues.split(";");
				int j = 1;
				for (String path : attachPaths) {
					String paths[] = path.split(",");
					if (null != paths && paths.length >= 3) {
						CommonAttachmentOrder commonAttachmentOrder = new CommonAttachmentOrder();
						commonAttachmentOrder.setUploaderId(uploaderId);
						commonAttachmentOrder.setUploaderAccount(uploaderAccount);
						commonAttachmentOrder.setUploaderName(uploaderName);
						commonAttachmentOrder.setBizNo(keyId);
						commonAttachmentOrder.setModuleType(type);
						commonAttachmentOrder.setIsort(j++);
						commonAttachmentOrder.setFileName(paths[0]);
						commonAttachmentOrder.setFilePhysicalPath(paths[1]);
						commonAttachmentOrder.setRequestPath(paths[2]);
						commonAttachmentOrder.setCheckStatus(CheckStatusEnum.CHECK_PASS.code());
						if (StringUtil.isBlank(projectCode)) {
							projectCode = request.getParameter("projectCode");
						}
						if (StringUtil.isBlank(projectCode)) {
							projectCode = request.getParameter("relatedProjectCode");
						}
						commonAttachmentOrder.setProjectCode(projectCode);
						commonAttachmentOrder.setRemark(remark);
						orders.add(commonAttachmentOrder);
					}
				}
			}
		}
		
		if (ListUtil.isNotEmpty(orders)) {
			return commonAttachmentServiceClient.insertAll(orders);
		} else {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
			result.setMessage("没有附件数据");
			return result;
		}
	}
	
	/**
	 * 添加附件
	 * @param keyId
	 * @param childId
	 * @param request
	 * @param types
	 * @return
	 */
	protected FcsBaseResult addAttachfile(String keyId, String childId, HttpServletRequest request,
											String projectCode, String remark,
											CommonAttachmentTypeEnum... types) {
		FcsBaseResult result = new FcsBaseResult();
		if (null == types || types.length <= 0) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
			result.setMessage("没有附件数据");
			return result;
		}
		
		long uploaderId = 0L;
		String uploaderAccount = "";
		String uploaderName = "";
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			uploaderId = sessionLocal.getUserId();
			uploaderAccount = sessionLocal.getUserName();
			uploaderName = sessionLocal.getRealName();
		}
		
		List<CommonAttachmentOrder> orders = new ArrayList<CommonAttachmentOrder>();
		//先删除，再保存
		commonAttachmentServiceClient.deleteByBizNoAndChildIdModuleType(keyId, childId, types);
		
		for (CommonAttachmentTypeEnum type : types) {
			if (null == type) {
				continue;
			}
			String pathValues = request.getParameter("pathName_" + type.code());
			if (StringUtil.isNotBlank(pathValues)) {
				String[] attachPaths = pathValues.split(";");
				int j = 1;
				for (String path : attachPaths) {
					String paths[] = path.split(",");
					if (null != paths && paths.length >= 3) {
						CommonAttachmentOrder commonAttachmentOrder = new CommonAttachmentOrder();
						commonAttachmentOrder.setUploaderId(uploaderId);
						commonAttachmentOrder.setUploaderAccount(uploaderAccount);
						commonAttachmentOrder.setUploaderName(uploaderName);
						commonAttachmentOrder.setBizNo(keyId);
						commonAttachmentOrder.setChildId(childId);
						commonAttachmentOrder.setModuleType(type);
						commonAttachmentOrder.setIsort(j++);
						commonAttachmentOrder.setFileName(paths[0]);
						commonAttachmentOrder.setFilePhysicalPath(paths[1]);
						commonAttachmentOrder.setRequestPath(paths[2]);
						commonAttachmentOrder.setCheckStatus(CheckStatusEnum.CHECK_PASS.code());
						if (StringUtil.isBlank(projectCode)) {
							projectCode = request.getParameter("projectCode");
						}
						if (StringUtil.isBlank(projectCode)) {
							projectCode = request.getParameter("relatedProjectCode");
						}
						if (StringUtil.isBlank(remark)) {
							remark = request.getParameter("attachRemark");
						}
						commonAttachmentOrder.setProjectCode(projectCode);
						commonAttachmentOrder.setRemark(remark);
						orders.add(commonAttachmentOrder);
					}
				}
			}
		}
		
		if (ListUtil.isNotEmpty(orders)) {
			return commonAttachmentServiceClient.insertAll(orders);
		} else {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
			result.setMessage("没有附件数据");
			return result;
		}
	}
	
	protected void addAttachment(HttpServletRequest request, String bizNo, String childId,
									CommonAttachmentTypeEnum type) {
		AttachmentBean attachment = new AttachmentBean();
		attachment.setBizNo(bizNo);
		attachment.setChildId(childId);
		attachment.setPathValues(request.getParameter("pathName_" + type.code()));
		attachment.addTypes(type);
		addAttachfile(attachment);
	}
	
	private boolean checkAttachment(AttachmentBean attachment) {
		if (null == attachment) {
			return false;
		}
		
		if (null == attachment.getTypes() || attachment.getTypes().length <= 0) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 保存附件
	 * @param attachment
	 * @return
	 */
	protected FcsBaseResult addAttachfile(AttachmentBean attachment) {
		FcsBaseResult result = new FcsBaseResult();
		if (!checkAttachment(attachment)) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
			result.setMessage("没有附件数据");
			return result;
		}
		
		long uploaderId = 0L;
		String uploaderAccount = "";
		String uploaderName = "";
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			uploaderId = sessionLocal.getUserId();
			uploaderAccount = sessionLocal.getUserName();
			uploaderName = sessionLocal.getRealName();
		}
		
		String bizNo = attachment.getBizNo();
		CommonAttachmentTypeEnum[] types = attachment.getTypes();
		//先删除，再保存
		if (StringUtil.isNotBlank(attachment.getChildId())) {
			commonAttachmentServiceClient.deleteByBizNoAndChildIdModuleType(bizNo,
				attachment.getChildId(), types);
		} else {
			commonAttachmentServiceClient.deleteByBizNoModuleType(bizNo, types);
		}
		List<CommonAttachmentOrder> orders = new ArrayList<CommonAttachmentOrder>();
		
		for (CommonAttachmentTypeEnum type : types) {
			if (null == type) {
				continue;
			}
			String pathValues = attachment.getPathValues();
			if (StringUtil.isNotBlank(pathValues)) {
				String[] attachPaths = pathValues.split(";");
				int j = 1;
				for (String path : attachPaths) {
					String paths[] = path.split(",");
					if (null != paths && paths.length >= 3) {
						CommonAttachmentOrder commonAttachmentOrder = new CommonAttachmentOrder();
						commonAttachmentOrder.setUploaderId(uploaderId);
						commonAttachmentOrder.setUploaderAccount(uploaderAccount);
						commonAttachmentOrder.setUploaderName(uploaderName);
						commonAttachmentOrder.setBizNo(bizNo);
						commonAttachmentOrder.setChildId(attachment.getChildId());
						commonAttachmentOrder.setModuleType(type);
						commonAttachmentOrder.setIsort(j++);
						commonAttachmentOrder.setFileName(paths[0]);
						commonAttachmentOrder.setFilePhysicalPath(paths[1]);
						commonAttachmentOrder.setRequestPath(paths[2]);
						commonAttachmentOrder.setCheckStatus(CheckStatusEnum.CHECK_PASS.code());
						if (StringUtil.isBlank(attachment.getProjectCode())) {
							commonAttachmentOrder
								.setProjectCode(attachment.getRelatedProjectCode());
						} else {
							commonAttachmentOrder.setProjectCode(attachment.getProjectCode());
						}
						if (StringUtil.isBlank(attachment.getRemark())) {
							commonAttachmentOrder.setRemark(type.message());
						}
						orders.add(commonAttachmentOrder);
					}
				}
			}
		}
		
		if (ListUtil.isNotEmpty(orders)) {
			return commonAttachmentServiceClient.insertAll(orders);
		} else {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
			result.setMessage("没有附件数据");
			return result;
		}
	}
	
	/**
	 * 查询附件列表
	 * 
	 * @param model
	 * @param bizNo 交易(引用外部数据id)流水号
	 * @param type 模块
	 * @param childId 长度 0 或者 1
	 */
	protected void queryCommonAttachmentData(Model model, String bizNo,
												CommonAttachmentTypeEnum type, String... childId) {
		CommonAttachmentQueryOrder attachQueryOrder = new CommonAttachmentQueryOrder();
		if (childId != null && childId.length > 0) {
			attachQueryOrder.setBizNo(childId[0]);
		}
		attachQueryOrder.setBizNo(bizNo);
		List<CommonAttachmentTypeEnum> moduleTypeList = new ArrayList<>();
		moduleTypeList.add(type);
		attachQueryOrder.setModuleTypeList(moduleTypeList);
		QueryBaseBatchResult<CommonAttachmentInfo> attaches = commonAttachmentServiceClient
			.queryCommonAttachment(attachQueryOrder);
		if (null != attaches && ListUtil.isNotEmpty(attaches.getPageList())) {
			model.addAttribute("attaches_" + type.code(), attaches.getPageList());
			StringBuilder urls = new StringBuilder();
			for (CommonAttachmentInfo attach : attaches.getPageList()) {
				urls.append(attach.getFileName()).append(",").append(attach.getFilePhysicalPath())
					.append(",").append(attach.getRequestPath()).append(";");
			}
			urls.deleteCharAt(urls.length() - 1);
			model.addAttribute("hiddenUrls_" + type.code(), urls.toString());
			
			//所有附件
			model.addAttribute("commonAttachementList", attaches.getPageList());
		}
	}
	
	/**
	 * 查询附件列表
	 *
	 * @param bizNo 交易(引用外部数据id)流水号
	 * @param type 模块
	 * @param childId 长度 0 或者 1
	 */
	protected List<CommonAttachmentInfo> queryCommonAttachmentDataList(	String bizNo,
																		CommonAttachmentTypeEnum type,
																		String... childId) {
		CommonAttachmentQueryOrder attachQueryOrder = new CommonAttachmentQueryOrder();
		if (childId != null && childId.length > 0) {
			attachQueryOrder.setBizNo(childId[0]);
		}
		attachQueryOrder.setBizNo(bizNo);
		List<CommonAttachmentTypeEnum> moduleTypeList = new ArrayList<>();
		moduleTypeList.add(type);
		attachQueryOrder.setModuleTypeList(moduleTypeList);
		QueryBaseBatchResult<CommonAttachmentInfo> attaches = commonAttachmentServiceClient
			.queryCommonAttachment(attachQueryOrder);
		return attaches.getPageList();
	}
	
	protected void setModelAttachment(List<CommonAttachmentInfo> list, Model model) {
		//当前用户
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		long currentUser = sessionLocal.getUserId();
		if (null != list && ListUtil.isNotEmpty(list)) {
			List<CommonAttachmentInfo> attachList = list; //所有附件
			List<CommonAttachmentInfo> myAttachList = Lists.newArrayList();//我的附件
			List<CommonAttachmentInfo> otherAttachList = Lists.newArrayList();//所有附件（不包含我的附件）
			StringBuffer allAttachUrl = new StringBuffer();
			StringBuffer myAttachUrl = new StringBuffer();
			StringBuffer otherAttachUrl = new StringBuffer();
			for (CommonAttachmentInfo attach : attachList) {
				allAttachUrl.append(attach.getFileName()).append(",")
					.append(attach.getFilePhysicalPath()).append(",")
					.append(attach.getRequestPath()).append(";");
				if (currentUser == attach.getUploaderId()) {
					myAttachList.add(attach);
					myAttachUrl.append(attach.getFileName()).append(",")
						.append(attach.getFilePhysicalPath()).append(",")
						.append(attach.getRequestPath()).append(";");
				} else {
					otherAttachList.add(attach);
					otherAttachUrl.append(attach.getFileName()).append(",")
						.append(attach.getFilePhysicalPath()).append(",")
						.append(attach.getRequestPath()).append(";");
				}
			}
			if (com.born.fcs.pm.util.StringUtil.isNotEmpty(allAttachUrl.toString())) {
				allAttachUrl.deleteCharAt(allAttachUrl.length() - 1);
			}
			if (com.born.fcs.pm.util.StringUtil.isNotEmpty(myAttachUrl.toString())) {
				myAttachUrl.deleteCharAt(myAttachUrl.length() - 1);
			}
			if (com.born.fcs.pm.util.StringUtil.isNotEmpty(otherAttachUrl.toString())) {
				otherAttachUrl.deleteCharAt(otherAttachUrl.length() - 1);
			}
			
			model.addAttribute("allAttachUrl", allAttachUrl);
			model.addAttribute("myAttachUrl", myAttachUrl);
			model.addAttribute("otherAttachUrl", otherAttachUrl);
			
			model.addAttribute("allAttachList", attachList);
			model.addAttribute("myAttachList", myAttachList);
			model.addAttribute("otherAttachList", otherAttachList);
		}
	}
	
	protected String queryCommonAttachmentUrls(String bizNo, CommonAttachmentTypeEnum type,
												String... childId) {
		CommonAttachmentQueryOrder attachQueryOrder = new CommonAttachmentQueryOrder();
		if (childId != null && childId.length > 0) {
			attachQueryOrder.setBizNo(childId[0]);
		}
		attachQueryOrder.setBizNo(bizNo);
		List<CommonAttachmentTypeEnum> moduleTypeList = new ArrayList<>();
		moduleTypeList.add(type);
		attachQueryOrder.setModuleTypeList(moduleTypeList);
		QueryBaseBatchResult<CommonAttachmentInfo> attaches = commonAttachmentServiceClient
			.queryCommonAttachment(attachQueryOrder);
		if (null != attaches && ListUtil.isNotEmpty(attaches.getPageList())) {
			StringBuilder urls = new StringBuilder();
			for (CommonAttachmentInfo attach : attaches.getPageList()) {
				urls.append(attach.getFileName()).append(",").append(attach.getFilePhysicalPath())
					.append(",").append(attach.getRequestPath()).append(";");
			}
			urls.deleteCharAt(urls.length() - 1);
			return urls.toString();
		}
		
		return "";
	}
	
	/**
	 * 构建跳转地址
	 * @param request
	 * @param model
	 */
	protected void buildSystemNameDefaultUrl(HttpServletRequest request, Model model) {
		Map<String, String> params = WebUtil.getRequestMap(request);
		if (params.containsKey("systemNameDefautUrl")) {
			int index = 0;
			String systemNameDefautUrl = params.get("systemNameDefautUrl");
			if (StringUtil.isNotBlank(systemNameDefautUrl)) {
				for (String pName : params.keySet()) {
					if ("systemNameDefautUrl".equals(pName))
						continue;
					String pValue = params.get(pName);
					if (StringUtil.isNotBlank(pValue)) {
						if (index == 0 && systemNameDefautUrl.indexOf("?") == -1) {
							systemNameDefautUrl += "?" + pName + "=" + pValue;
						} else {
							systemNameDefautUrl += "&" + pName + "=" + pValue;
						}
						index++;
					}
				}
			}
			params.put("systemNameDefautUrl", systemNameDefautUrl);
		}
		model.addAllAttributes(params);
	}
	
	/**
	 * 验证登录状态
	 * 
	 * @param json 失效提示信息
	 * @return 未登录或失效返回true
	 */
	protected boolean isLoginExpire(JSONObject json) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (null == sessionLocal) {
			json.put("success", false);
			json.put("message", "您未登陆或登陆已失效");
			return true;
		}
		return false;
	}
	
	/**
	 * 验证重复提交
	 * 
	 * @param json 失效提示信息
	 * @return 重复提交返回true
	 */
	protected boolean isRepeatSubmit(JSONObject json, HttpSession session, String token) {
		return false;
		//去掉防重复提交
		//		String getToken = (String) session.getAttribute("token");
		//		if (StringUtil.equals(token, getToken)) {
		//			return false;
		//		} else {
		//			json.put("success", false);
		//			json.put("message", "请不要重复提交");
		//			return true;
		//		}
	}
	
	protected boolean isNomalProject(JSONObject json, ProjectFormOrderBase order) {
		if (null != order && StringUtil.isNotBlank(order.getProjectCode())) {
			ProjectInfo project = projectServiceClient.queryByCode(order.getProjectCode(), false);
			if (null != project && ProjectStatusEnum.NORMAL == project.getStatus()) {
				return true;
			}
			
			json.put("success", false);
			json.put("message", "该项目不能修改");
			logger.error("该项目不能修改：" + order);
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * 提交表单到流程
	 * 
	 * @param order
	 * @param formId
	 * @param json 保存结果
	 * @return
	 */
	protected void formSubmit(FormOrderBase order, long formId, JSONObject json, String sysName) {
		//提交审核
		if (null != order.getCheckStatus() && order.getCheckStatus() == 1) {
			UserInfo userInfo = ShiroSessionUtils.getSessionLocal().getUserInfo();
			if (userInfo != null) {
				order.setUserEmail(userInfo.getEmail());
				order.setUserMobile(userInfo.getMoblie());
			}
			SimpleFormOrder formOrder = new SimpleFormOrder();
			BeanCopier.staticCopy(order, formOrder);
			formOrder.setFormId(formId);
			FormBaseResult submitResult = getSystemMatchedFormService(sysName).submit(formOrder);
			if (submitResult.isSuccess()) {
				json.put("success", true);
				json.put("message", submitResult.getMessage());
				json.put("form", submitResult.getFormInfo().toJson());
			} else {
				json.put("success", false);
				json.put("message", submitResult.getMessage());
				json.put("form", submitResult.getFormInfo());
			}
		}
	}
	
	/**
	 * 返回和子系统匹配的表单客户端
	 * @param request
	 * @return
	 */
	protected FormService getSystemMatchedFormService(String sysName) {
		//默认PM 项目管理客户端
		FormService formService = formServiceClient;
		if ("FM".equals(sysName)) {//资金
			formService = formServiceFmClient;
		} else if ("AM".equals(sysName)) {//资产
			formService = formServiceAmClient;
		} else if ("RM".equals(sysName)) { //报表
		
		} else if ("CRM".equals(sysName)) { //客户管理
			formService = formServiceCrmClient;
		}
		return formService;
	}
	
	/**
	 * 返回和子系统匹配的表单客户端
	 * @param request
	 * @return
	 */
	protected FormService getSystemMatchedFormServiceByModule(String module) {
		//默认PM 项目管理客户端
		FormService formService = formServiceClient;
		if ("fundMg".equals(module)) {//资金
			formService = formServiceFmClient;
		} else if ("assetMg".equals(module)) {//资产
			formService = formServiceAmClient;
		} else if ("reportMg".equals(module)) { //报表
		
		} else if ("customerMg".equals(module)) { //客户管理
			formService = formServiceCrmClient;
		}
		
		return formService;
	}
	
	/**
	 * 
	 * 获取近几年的年份值:2016,2015...
	 * 
	 * @param n
	 * @return
	 */
	protected int[] getYears(int n) {
		int[] years = new int[n];
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int currentYear = c.get(Calendar.YEAR);
		for (int y = currentYear, i = 0; i < n; i++) {
			years[i] = y--;
		}
		return years;
	}
	
	protected void returnJson(HttpServletResponse response, boolean isIE, JSONObject jsonobj)
																								throws IOException {
		response.reset();
		if (isIE) {
			response.setHeader("ContentType", "text/html");
			response.setContentType("text/html");
		} else {
			response.setHeader("ContentType", "application/json");
			response.setContentType("application/json");
		}
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonobj.toJSONString());
	}
	
	/** 客户系统加载需要的枚举 */
	protected void setCustomerEnums(Model model) {
		//评级审核状态
		model.addAttribute("auditStatusEnum", AuditStatusEnum.getAllEnum());
		//证件类型
		model.addAttribute("certTypesEnum", CertTypesEnum.getAllEnum());
		//公民类型
		model.addAttribute("citizenTypeEnum", CitizenTypeEnum.getAllEnum());
		//客户类型
		model.addAttribute("customerTypeEnum", CustomerTypeEnum.getAllEnum());
		//客户与我公司关系
		model.addAttribute("customerRelationEnum", CustomerRelationEnum.getAllEnum());
		//客户来源
		model.addAttribute("customerSourceEnum", CustomerSourceEnum.getAllEnum());
		//企业性质
		model.addAttribute("enterpriseNatureEnum", EnterpriseNatureEnum.getAllEnum());
		//企业规模
		model.addAttribute("enterpriseScaleEnum", EnterpriseScaleEnum.getAllEnum());
		
		//婚姻状况 
		model.addAttribute("maritalStatusEnum", MaritalStatusEnum.getAllEnum());
		//性别
		model.addAttribute("sexEnum", SexEnum.getAllEnum());
		//客户来源
		model.addAttribute("customerSourceEnum", CustomerSourceEnum.getAllEnum());
		//渠道分类
		model.addAttribute("chanalTypeEnum", ChanalTypeEnum.getAllEnum());
		//金融机构属性
		model.addAttribute("financialInstitutionsEnum", FinancialInstitutionsEnum.getAllEnum());
		//合同状态
		model.addAttribute("contractStatusEnum", ContractStatusEnum.getAllEnum());
		
		//审核状态枚举
		model.addAttribute("formStatusEnum", FormStatusEnum.getAllEnum());
		//评价等级枚举
		model.addAttribute("evaluetingLevelEnum", EvaluetingLevelEnum.getAllEnum());
		//比较枚举
		model.addAttribute("comparEnums", ComparEnums.getAllEnum());
		
	}
	
	/**
	 * 会议列表，用于会议列表和工作台会议列表
	 * @param order
	 * @param model
	 */
	protected QueryBaseBatchResult<CouncilInfo> councilListQuery(CouncilQueryOrder order,
																	Model model) {
		List<String> councilTypeCodes = new ArrayList<>();
		List<String> companyNames = new ArrayList<String>();
		if (!DataPermissionUtil.isSystemAdministrator()) {
			boolean isRiskSecretary = isRiskSecretary();
			boolean isManagerSecretary = isManagerSecretary();
			boolean isManagerSecretaryXH = isManagerSecretaryXH();
			if (isRiskSecretary) {
				councilTypeCodes.add(CouncilTypeEnum.PROJECT_REVIEW.code());
				councilTypeCodes.add(CouncilTypeEnum.RISK_HANDLE.code());
				companyNames.add(CompanyNameEnum.NORMAL.code());
			}
			if (isManagerSecretary) {
				councilTypeCodes.add(CouncilTypeEnum.GM_WORKING.code());
				companyNames.add(CompanyNameEnum.NORMAL.code());
			}
			if (isManagerSecretaryXH) {
				councilTypeCodes.add(CouncilTypeEnum.GM_WORKING.code());
				companyNames.add(CompanyNameEnum.XINHUI.code());
			}
			order.setCompanyNames(companyNames);
			// 非风险经理/非总经理秘书 只能看与自己相关的项目
			if (!isRiskSecretary && !isManagerSecretary && !isManagerSecretaryXH) {
				SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
				if (sessionLocal != null) {
					order.setRelatveId(sessionLocal.getUserId());
				}
			}
			order.setCouncilTypeCodes(councilTypeCodes);
		}
		QueryBaseBatchResult<CouncilInfo> batchResult = councilServiceClient.queryCouncil(order);
		return batchResult;
		
	}
	
	/** 被监控名录同步接口 */
	public BornSynResultBase synWatchList(CustomerBaseOrder customerBaseOrder) {
		try {
			SynWatchListOrder order = new SynWatchListOrder();
			List<WatchListInfo> list = new ArrayList<>();
			WatchListInfo info = new WatchListInfo();
			info.setCustomName(customerBaseOrder.getCustomerName());
			info.setOperator(ShiroSessionUtils.getSessionLocal().getUserName());
			if (CustomerTypeEnum.PERSIONAL.code().equals(customerBaseOrder.getCustomerType())) {
				info.setUserType(UserTypeEnum.PERSONAL);
				info.setLicenseNo(customerBaseOrder.getCertNo());
			} else {
				info.setUserType(UserTypeEnum.BUSINESS);
				info.setLicenseNo(customerBaseOrder.getBusiLicenseNo());
			}
			list.add(info);
			order.setList(list);
			order.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
			BornSynResultBase result = riskSystemFacadeClient.synWatchList(order);
			logger.info("被监控名录同步接口：同步客户={},result={}", info, result);
		} catch (Exception e) {
			logger.info("被监控名录同步接口异常：", e);
			
		}
		return null;
		
	}
	
	/** 风险验证消息保存 */
	public void verifyMsgSave(HttpServletRequest request, String errorKey) {
		VerifyMessageOrder order = new VerifyMessageOrder();
		try {
			if (StringUtil.isBlank(errorKey)) {
				return;
			}
			String[] messages = request.getParameterValues("messageInfo");
			String message = "";
			int i = 1;
			if (messages != null && messages.length > 0) {
				for (String s : messages) {
					if (i == 1) {
						message += s;
						i++;
					} else {
						message += "&" + s;
					}
					
				}
				order.setCardMessage(message);
				order.setErrorKey(errorKey);
				FcsBaseResult result = verifyMessageSaveClient.save(order);
				logger.info("风险验证消息保存结果：order={},result={}", order, result);
			}
			
		} catch (Exception e) {
			logger.info("风险验证消息保存出现异常：order={}", order);
		}
		
	}
	
	/** 风险验证消息查询 */
	public void verifyMsgQuery(String errorKey, Model model) {
		try {
			if (StringUtil.isBlank(errorKey)) {
				return;
			}
			VerifyMessageInfo info = verifyMessageSaveClient.queryById(errorKey);
			if (info != null) {
				List<String> list = new ArrayList<>();
				if (StringUtil.isNotBlank(info.getCardMessage())) {
					String[] messages = info.getCardMessage().split("&");
					for (String s : messages) {
						list.add(s);
					}
				}
				model.addAttribute("messageList", list);
			}
		} catch (Exception e) {
			logger.info("查询风险提示消息异常：errorKey={},e:", errorKey, e);
			
		}
		
	}
	
	/**
	 * 展示发债分保信息
	 * @param projectCode
	 * @param model
	 */
	protected void showFenbaoInfo(String projectCode, Model model) {
		if (StringUtil.isNotBlank(projectCode) && !projectCode.startsWith("JJ")
			&& !projectCode.startsWith("QB")
			&& ProjectUtil.isBond(ProjectUtil.getBusiTypeByCode(projectCode))) {
			List<ProjectBondReinsuranceInformationInfo> fbList = projectBondReinsuranceInformationServiceClient
				.findByProjectCode(projectCode);
			if (fbList != null && !fbList.isEmpty()) {
				model.addAttribute("fenbaoList", fbList);
			}
		}
	}
	
	/**
	 * 设置表单自定义参数
	 * @param order
	 * @param request
	 */
	protected void setCustomizFieldMap(FormOrderBase order, HttpServletRequest request) {
		Map<String, Object> fieldMap = order.getCustomizeFieldMap();
		if (fieldMap == null)
			fieldMap = Maps.newHashMap();
		Map<String, String> requestMap = WebUtil.getRequestMap(request);
		if (requestMap != null) {
			for (String key : requestMap.keySet()) {
				if (key.startsWith("customizeFieldMap_")) {
					fieldMap.put(key.replace("customizeFieldMap_", ""), requestMap.get(key));
				}
			}
		}
		order.setCustomizeFieldMap(fieldMap);
	}
	
	/**
	 * siteurl
	 * @param request
	 * @return
	 */
	protected String getRequestUrl(HttpServletRequest request) {
		String str = request.getScheme() + "://" + request.getServerName() + ":"
						+ request.getServerPort();
		return str;
	}
	
	protected void getPayBank(FormExpenseApplicationInfo info) {
		BankMessageQueryOrder order = new BankMessageQueryOrder();
		if (CostDirectionEnum.PRIVATE == info.getDirection()) {
			order.setDefaultPersonalAccount(BooleanEnum.YES);
		} else {
			order.setDefaultCompanyAccount(BooleanEnum.YES);
		}
		
		int setCount = 0;
		order.setStatus(SubjectStatusEnum.NORMAL);
		QueryBaseBatchResult<BankMessageInfo> bankMessageInfo = bankMessageServiceClient
			.queryBankMessageInfo(order);
		if (bankMessageInfo != null && bankMessageInfo.getPageList() != null
			&& !bankMessageInfo.getPageList().isEmpty()) {
			// 20161104 添加判断，按照部门选择默认银行信息
			//					SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			//					String localCode = sessionLocal.getUserDetailInfo().getPrimaryOrg().getCode(); //id code name
			Long localId = info.getExpenseDeptId();
			Org orgXH = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.XINHUI.getDeptCode());
			Org orgBF = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.BEIJING.getDeptCode());
			// 诚本公司
			Org orgCB = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.XHTZ_CB.getDeptCode());
			// 诚鑫公司
			Org orgCX = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.XHTZ_CX.getDeptCode());
			// 诚融公司
			Org orgCR = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.XHTZ_CR.getDeptCode());
			// 诚正公司
			Org orgCZ = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.XHTZ_CZ.getDeptCode());
			// 诚远公司
			Org orgCY = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.XHTZ_CY.getDeptCode());
			List<Long> spcDeptIds = new ArrayList<Long>();
			if (orgCB != null) {
				spcDeptIds.add(orgCB.getId());
			}
			if (orgCX != null) {
				spcDeptIds.add(orgCX.getId());
			}
			if (orgCR != null) {
				spcDeptIds.add(orgCR.getId());
			}
			if (orgCZ != null) {
				spcDeptIds.add(orgCZ.getId());
			}
			if (orgCY != null) {
				spcDeptIds.add(orgCY.getId());
			}
			if (orgXH.getId() == localId) {
				// 信汇
				for (BankMessageInfo bankInfo : bankMessageInfo.getPageList()) {
					if (bankInfo.getDeptId() == orgXH.getId()) {
						// 添加判断，出现多个对应部门的默认账户，不给默认值
						if (setCount == 0) {
							setCount++;
							info.setPayBank(bankInfo.getBankName());
							info.setPayBankAccount(bankInfo.getAccountNo());
						} else {
							info.setPayBank("");
							info.setPayBankAccount("");
						}
					}
				}
			} else if (orgBF.getId() == localId) {
				// 北分
				for (BankMessageInfo bankInfo : bankMessageInfo.getPageList()) {
					if (bankInfo.getDeptId() == orgBF.getId()) {
						// 添加判断，出现多个对应部门的默认账户，不给默认值
						if (setCount == 0) {
							setCount++;
							info.setPayBank(bankInfo.getBankName());
							info.setPayBankAccount(bankInfo.getAccountNo());
						} else {
							info.setPayBank("");
							info.setPayBankAccount("");
						}
					}
				}
			} else if (spcDeptIds.contains(localId)) {
				for (Long spcId : spcDeptIds) {
					if (spcId.equals(localId)) {
						
						for (BankMessageInfo bankInfo : bankMessageInfo.getPageList()) {
							if (bankInfo.getDeptId() == spcId) {
								// 添加判断，出现多个对应部门的默认账户，不给默认值
								if (setCount == 0) {
									setCount++;
									info.setPayBank(bankInfo.getBankName());
									info.setPayBankAccount(bankInfo.getAccountNo());
								} else {
									info.setPayBank("");
									info.setPayBankAccount("");
								}
							}
						}
					}
				}
				// 有多个的情况 先找自己 再找公司 
				// 若找完自己时没有值，再找所有
				if (StringUtil.isBlank(info.getPayBank())) {
					for (Long spcId : spcDeptIds) {
						for (BankMessageInfo bankInfo : bankMessageInfo.getPageList()) {
							if (bankInfo.getDeptId() == spcId) {
								// 添加判断，出现多个对应部门的默认账户，不给默认值
								if (setCount == 0) {
									setCount++;
									info.setPayBank(bankInfo.getBankName());
									info.setPayBankAccount(bankInfo.getAccountNo());
								} else {
									info.setPayBank("");
									info.setPayBankAccount("");
								}
							}
						}
					}
				}
			}
			//				else if(ReportCompanyEnum.XINHUI.getDeptCode().equals(localCode)){
			//					
			//				}
			else {
				// 主部门
				for (BankMessageInfo bankInfo : bankMessageInfo.getPageList()) {
					// 添加判断。优先寻找部门相同的部门信息
					
					if (localId == bankInfo.getDeptId()) {
						info.setPayBank(bankInfo.getBankName());
						info.setPayBankAccount(bankInfo.getAccountNo());
						break;
					}
					
					if (bankInfo.getDeptId() != orgBF.getId()
						&& bankInfo.getDeptId() != orgXH.getId()
						&& !spcDeptIds.contains(bankInfo.getDeptId())) {
						logger.info("满足条件的setCount=" + setCount + ",bank=" + bankInfo);
						// 添加判断，出现多个对应部门的默认账户，不给默认值
						if (setCount == 0) {
							setCount++;
							info.setPayBank(bankInfo.getBankName());
							info.setPayBankAccount(bankInfo.getAccountNo());
						} else {
							info.setPayBank("");
							info.setPayBankAccount("");
						}
					}
				}
			}
			
			//				info.setPayBank(bankMessageInfo.getPageList().get(0).getBankName());
			//				info.setPayBankAccount(bankMessageInfo.getPageList().get(0).getAccountNo());}
		}
	}
	
	protected void getPayBank(FormTravelExpenseInfo info) {
		BankMessageQueryOrder order = new BankMessageQueryOrder();
		// 公务卡对公，其他对私
		if (BooleanEnum.IS == info.getIsOfficialCard()) {
			order.setDefaultCompanyAccount(BooleanEnum.YES);
		} else {
			order.setDefaultPersonalAccount(BooleanEnum.YES);
		}
		
		int setCount = 0;
		order.setStatus(SubjectStatusEnum.NORMAL);
		QueryBaseBatchResult<BankMessageInfo> bankMessageInfo = bankMessageServiceClient
			.queryBankMessageInfo(order);
		if (bankMessageInfo != null && bankMessageInfo.getPageList() != null
			&& !bankMessageInfo.getPageList().isEmpty()) {
			// 20161104 添加判断，按照部门选择默认银行信息
			//				SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			//				String localCode = sessionLocal.getUserDetailInfo().getPrimaryOrg().getCode(); //id code name
			Long localId = info.getExpenseDeptId();
			Org orgXH = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.XINHUI.getDeptCode());
			Org orgBF = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.BEIJING.getDeptCode());
			// 诚本公司
			Org orgCB = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.XHTZ_CB.getDeptCode());
			// 诚鑫公司
			Org orgCX = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.XHTZ_CX.getDeptCode());
			// 诚融公司
			Org orgCR = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.XHTZ_CR.getDeptCode());
			// 诚正公司
			Org orgCZ = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.XHTZ_CZ.getDeptCode());
			// 诚远公司
			Org orgCY = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.XHTZ_CY.getDeptCode());
			List<Long> spcDeptIds = new ArrayList<Long>();
			if (orgCB != null) {
				spcDeptIds.add(orgCB.getId());
			}
			if (orgCX != null) {
				spcDeptIds.add(orgCX.getId());
			}
			if (orgCR != null) {
				spcDeptIds.add(orgCR.getId());
			}
			if (orgCZ != null) {
				spcDeptIds.add(orgCZ.getId());
			}
			if (orgCY != null) {
				spcDeptIds.add(orgCY.getId());
			}
			if (orgXH.getId() == localId) {
				// 信汇
				for (BankMessageInfo bankInfo : bankMessageInfo.getPageList()) {
					if (bankInfo.getDeptId() == orgXH.getId()) {
						// 添加判断，出现多个对应部门的默认账户，不给默认值
						if (setCount == 0) {
							setCount++;
							info.setPayBank(bankInfo.getBankName());
							info.setPayBankAccount(bankInfo.getAccountNo());
						} else {
							info.setPayBank("");
							info.setPayBankAccount("");
						}
					}
				}
			} else if (orgBF.getId() == localId) {
				// 北分
				for (BankMessageInfo bankInfo : bankMessageInfo.getPageList()) {
					if (bankInfo.getDeptId() == orgBF.getId()) {
						// 添加判断，出现多个对应部门的默认账户，不给默认值
						if (setCount == 0) {
							setCount++;
							info.setPayBank(bankInfo.getBankName());
							info.setPayBankAccount(bankInfo.getAccountNo());
						} else {
							info.setPayBank("");
							info.setPayBankAccount("");
						}
					}
				}
			} else if (spcDeptIds.contains(localId)) {
				for (Long spcId : spcDeptIds) {
					if (spcId.equals(localId)) {
						
						for (BankMessageInfo bankInfo : bankMessageInfo.getPageList()) {
							if (bankInfo.getDeptId() == spcId) {
								// 添加判断，出现多个对应部门的默认账户，不给默认值
								if (setCount == 0) {
									setCount++;
									info.setPayBank(bankInfo.getBankName());
									info.setPayBankAccount(bankInfo.getAccountNo());
								} else {
									info.setPayBank("");
									info.setPayBankAccount("");
								}
							}
						}
					}
				}
				// 有多个的情况 先找自己 再找公司 
				// 若找完自己时没有值，再找所有
				if (StringUtil.isBlank(info.getPayBank())) {
					for (Long spcId : spcDeptIds) {
						for (BankMessageInfo bankInfo : bankMessageInfo.getPageList()) {
							if (bankInfo.getDeptId() == spcId) {
								// 添加判断，出现多个对应部门的默认账户，不给默认值
								if (setCount == 0) {
									setCount++;
									info.setPayBank(bankInfo.getBankName());
									info.setPayBankAccount(bankInfo.getAccountNo());
								} else {
									info.setPayBank("");
									info.setPayBankAccount("");
								}
							}
						}
					}
				}
			}
			//				else if(ReportCompanyEnum.XINHUI.getDeptCode().equals(localCode)){
			//					
			//				}
			else {
				// 主部门
				for (BankMessageInfo bankInfo : bankMessageInfo.getPageList()) {
					// 添加判断。优先寻找部门相同的部门信息
					
					if (localId == bankInfo.getDeptId()) {
						info.setPayBank(bankInfo.getBankName());
						info.setPayBankAccount(bankInfo.getAccountNo());
						break;
					}
					
					if (bankInfo.getDeptId() != orgBF.getId()
						&& bankInfo.getDeptId() != orgXH.getId()
						&& !spcDeptIds.contains(bankInfo.getDeptId())) {
						logger.info("满足条件的setCount=" + setCount + ",bank=" + bankInfo);
						// 添加判断，出现多个对应部门的默认账户，不给默认值
						if (setCount == 0) {
							setCount++;
							info.setPayBank(bankInfo.getBankName());
							info.setPayBankAccount(bankInfo.getAccountNo());
						} else {
							info.setPayBank("");
							info.setPayBankAccount("");
						}
					}
				}
			}
			
			//				info.setPayBank(bankMessageInfo.getPageList().get(0).getBankName());
			//				info.setPayBankAccount(bankMessageInfo.getPageList().get(0).getAccountNo());}
		}
	}
	
	protected void getPayBank(FormLabourCapitalInfo info) {
		BankMessageQueryOrder order = new BankMessageQueryOrder();
		//		if (CostDirectionEnum.PRIVATE == info.getDirection()) {
		//			order.setDefaultPersonalAccount(BooleanEnum.YES);
		//		} else {
		//			order.setDefaultCompanyAccount(BooleanEnum.YES);
		//		}
		order.setDefaultCompanyAccount(BooleanEnum.YES);
		int setCount = 0;
		order.setStatus(SubjectStatusEnum.NORMAL);
		QueryBaseBatchResult<BankMessageInfo> bankMessageInfo = bankMessageServiceClient
			.queryBankMessageInfo(order);
		if (bankMessageInfo != null && bankMessageInfo.getPageList() != null
			&& !bankMessageInfo.getPageList().isEmpty()) {
			// 20161104 添加判断，按照部门选择默认银行信息
			//					SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			//					String localCode = sessionLocal.getUserDetailInfo().getPrimaryOrg().getCode(); //id code name
			Long localId = info.getExpenseDeptId();
			Org orgXH = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.XINHUI.getDeptCode());
			Org orgBF = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.BEIJING.getDeptCode());
			// 诚本公司
			Org orgCB = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.XHTZ_CB.getDeptCode());
			// 诚鑫公司
			Org orgCX = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.XHTZ_CX.getDeptCode());
			// 诚融公司
			Org orgCR = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.XHTZ_CR.getDeptCode());
			// 诚正公司
			Org orgCZ = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.XHTZ_CZ.getDeptCode());
			// 诚远公司
			Org orgCY = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.XHTZ_CY.getDeptCode());
			List<Long> spcDeptIds = new ArrayList<Long>();
			if (orgCB != null) {
				spcDeptIds.add(orgCB.getId());
			}
			if (orgCX != null) {
				spcDeptIds.add(orgCX.getId());
			}
			if (orgCR != null) {
				spcDeptIds.add(orgCR.getId());
			}
			if (orgCZ != null) {
				spcDeptIds.add(orgCZ.getId());
			}
			if (orgCY != null) {
				spcDeptIds.add(orgCY.getId());
			}
			if (orgXH.getId() == localId) {
				// 信汇
				for (BankMessageInfo bankInfo : bankMessageInfo.getPageList()) {
					if (bankInfo.getDeptId() == orgXH.getId()) {
						// 添加判断，出现多个对应部门的默认账户，不给默认值
						if (setCount == 0) {
							setCount++;
							info.setPayBank(bankInfo.getBankName());
							info.setPayBankAccount(bankInfo.getAccountNo());
						} else {
							info.setPayBank("");
							info.setPayBankAccount("");
						}
					}
				}
			} else if (orgBF.getId() == localId) {
				// 北分
				for (BankMessageInfo bankInfo : bankMessageInfo.getPageList()) {
					if (bankInfo.getDeptId() == orgBF.getId()) {
						// 添加判断，出现多个对应部门的默认账户，不给默认值
						if (setCount == 0) {
							setCount++;
							info.setPayBank(bankInfo.getBankName());
							info.setPayBankAccount(bankInfo.getAccountNo());
						} else {
							info.setPayBank("");
							info.setPayBankAccount("");
						}
					}
				}
			} else if (spcDeptIds.contains(localId)) {
				for (Long spcId : spcDeptIds) {
					if (spcId.equals(localId)) {
						
						for (BankMessageInfo bankInfo : bankMessageInfo.getPageList()) {
							if (bankInfo.getDeptId() == spcId) {
								// 添加判断，出现多个对应部门的默认账户，不给默认值
								if (setCount == 0) {
									setCount++;
									info.setPayBank(bankInfo.getBankName());
									info.setPayBankAccount(bankInfo.getAccountNo());
								} else {
									info.setPayBank("");
									info.setPayBankAccount("");
								}
							}
						}
					}
				}
				// 有多个的情况 先找自己 再找公司 
				// 若找完自己时没有值，再找所有
				if (StringUtil.isBlank(info.getPayBank())) {
					for (Long spcId : spcDeptIds) {
						for (BankMessageInfo bankInfo : bankMessageInfo.getPageList()) {
							if (bankInfo.getDeptId() == spcId) {
								// 添加判断，出现多个对应部门的默认账户，不给默认值
								if (setCount == 0) {
									setCount++;
									info.setPayBank(bankInfo.getBankName());
									info.setPayBankAccount(bankInfo.getAccountNo());
								} else {
									info.setPayBank("");
									info.setPayBankAccount("");
								}
							}
						}
					}
				}
			}
			//				else if(ReportCompanyEnum.XINHUI.getDeptCode().equals(localCode)){
			//					
			//				}
			else {
				// 主部门
				for (BankMessageInfo bankInfo : bankMessageInfo.getPageList()) {
					// 添加判断。优先寻找部门相同的部门信息
					
					if (localId == bankInfo.getDeptId()) {
						info.setPayBank(bankInfo.getBankName());
						info.setPayBankAccount(bankInfo.getAccountNo());
						break;
					}
					
					if (bankInfo.getDeptId() != orgBF.getId()
						&& bankInfo.getDeptId() != orgXH.getId()
						&& !spcDeptIds.contains(bankInfo.getDeptId())) {
						logger.info("满足条件的setCount=" + setCount + ",bank=" + bankInfo);
						// 添加判断，出现多个对应部门的默认账户，不给默认值
						if (setCount == 0) {
							setCount++;
							info.setPayBank(bankInfo.getBankName());
							info.setPayBankAccount(bankInfo.getAccountNo());
						} else {
							info.setPayBank("");
							info.setPayBankAccount("");
						}
					}
				}
			}
			
			//				info.setPayBank(bankMessageInfo.getPageList().get(0).getBankName());
			//				info.setPayBankAccount(bankMessageInfo.getPageList().get(0).getAccountNo());}
		}
	}
	
	/**
	 * 显示重新授信的会议纪要
	 * @param riskHandleId
	 * @param model
	 */
	protected void showRedoSummary(long riskHandleId, Model model) {
		FCouncilSummaryRiskHandleInfo riskhandle = councilSummaryServiceClient
			.queryRiskHandleCsByHandleId(riskHandleId);
		model.addAttribute("riskhandle", riskhandle);
		if (riskhandle != null) {
			model.addAttribute("redoSummary", riskhandle.getRedo());
		}
	}
	
	/**
	 * 显示重新授信的会议纪要
	 * @param riskHandleId
	 * @param model
	 */
	protected void showRedoSummary(String redoProjectCode, Model model) {
		FCouncilSummaryRiskHandleInfo riskhandle = councilSummaryServiceClient
			.queryRiskHandleCsByRedoProjectCode(redoProjectCode);
		model.addAttribute("riskhandle", riskhandle);
		if (riskhandle != null) {
			model.addAttribute("redoSummary", riskhandle.getRedo());
		}
	}
}
