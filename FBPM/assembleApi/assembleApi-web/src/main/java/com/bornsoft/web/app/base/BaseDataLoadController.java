package com.bornsoft.web.app.base;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.bpm.service.client.user.SysOrg;
import com.born.bpm.service.client.user.UserDetailsServiceProxy;
import com.born.fcs.crm.ws.service.info.CompanyCustomerInfo;
import com.born.fcs.crm.ws.service.info.CustomerBaseInfo;
import com.born.fcs.crm.ws.service.info.PersonalCustomerInfo;
import com.born.fcs.crm.ws.service.order.query.CustomerQueryOrder;
import com.born.fcs.face.integration.bpm.service.OrgService;
import com.born.fcs.face.integration.bpm.service.PermissionUtil;
import com.born.fcs.face.integration.bpm.service.info.UserInfo;
import com.born.fcs.face.integration.bpm.service.info.WorkflowProcessLog;
import com.born.fcs.face.integration.comparator.MenuComparator;
import com.born.fcs.face.integration.crm.service.customer.PersonalCustomerClient;
import com.born.fcs.face.integration.fm.service.payment.BudgetServiceClient;
import com.born.fcs.face.integration.info.MenuInfo;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.fm.ws.enums.CostCategoryStatusEnum;
import com.born.fcs.fm.ws.info.bank.BankMessageInfo;
import com.born.fcs.fm.ws.info.payment.CostCategoryInfo;
import com.born.fcs.fm.ws.info.payment.ExpenseCxDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationInfo;
import com.born.fcs.fm.ws.order.bank.BankMessageQueryOrder;
import com.born.fcs.fm.ws.order.payment.CostCategoryQueryOrder;
import com.born.fcs.fm.ws.order.payment.ExpenseApplicationQueryOrder;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CertTypeEnum;
import com.born.fcs.pm.ws.enums.CheckStatusEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.EnterpriseNatureEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.info.common.BusiTypeInfo;
import com.born.fcs.pm.ws.info.common.IndustryInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.RegionInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.ProjectCreditConditionInfo;
import com.born.fcs.pm.ws.info.user.UserExtraMessageInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.user.UserExtraMessageQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.basicmaintain.AssessCompanyService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.council.CouncilApplyService;
import com.born.fcs.pm.ws.service.fund.RefundApplicationService;
import com.born.fcs.pm.ws.service.notice.ConsentIssueNoticeService;
import com.born.fcs.pm.ws.service.projectcreditcondition.ProjectCreditConditionService;
import com.born.fcs.pm.ws.service.projectissueinformation.ProjectIssueInformationService;
import com.born.fcs.pm.ws.service.setup.ProjectSetupService;
import com.bornsoft.api.service.BornApiServiceEnum;
import com.bornsoft.api.service.app.DataPermissionUtil;
import com.bornsoft.api.service.app.JckBaseService;
import com.bornsoft.facade.api.apix.ApixCreditInvestigationFacade;
import com.bornsoft.facade.api.risk.RiskSystemFacade;
import com.bornsoft.pub.enums.ApixResultEnum;
import com.bornsoft.pub.enums.UserTypeEnum;
import com.bornsoft.pub.order.apix.ApixDishonestQueryOrder;
import com.bornsoft.pub.order.apix.ApixDishonestQueryOrder.ApixUserTypeEnum;
import com.bornsoft.pub.order.apix.ApixValidateIdCardOrder;
import com.bornsoft.pub.order.risk.QuerySimilarEnterpriseOrder;
import com.bornsoft.pub.order.risk.VerifyOrganizationOrder;
import com.bornsoft.pub.result.apix.ApixDishonestQueryResult;
import com.bornsoft.pub.result.apix.ApixDishonestQueryResult.DishonestInfo;
import com.bornsoft.pub.result.apix.ApixValidateIdCardResult;
import com.bornsoft.pub.result.risk.QuerySimilarEnterpriseResult;
import com.bornsoft.pub.result.risk.QuerySimilarEnterpriseResult.CustomInfo;
import com.bornsoft.pub.result.risk.VerifyOrganizationResult;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.bornsoft.utils.enums.CommonResultEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.AppUtils;
import com.bornsoft.utils.tool.BornApiRequestUtils;
import com.bornsoft.utils.tool.JsonParseUtil;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;

@Controller
@RequestMapping("baseDataLoad")
public class BaseDataLoadController extends BaseController {

	@Autowired
	protected ProjectSetupService projectSetupServiceClient;

	@Autowired
	protected ProjectService projectServiceClient;

	@Autowired
	protected CouncilApplyService councilApplyServiceClient;

	@Autowired
	protected OrgService orgService;

	@Autowired
	protected AssessCompanyService assessCompanyServiceClient;

	@Autowired
	protected ProjectCreditConditionService projectCreditConditionServiceClient;

	@Autowired
	protected ProjectIssueInformationService projectIssueInformationServiceClient;

	@Autowired
	protected ConsentIssueNoticeService consentIssueNoticeServiceClient;
	@Autowired
	protected RefundApplicationService refundApplicationServiceClient;
	@Autowired
	protected PersonalCustomerClient personalCustomerClient;
	@Autowired
	protected BudgetServiceClient budgetServiceClient;
	@Autowired
	private RiskSystemFacade riskSystemFacadeApi;
	@Autowired
	private ApixCreditInvestigationFacade apixCreditInvestigationFacadeApi;
	
	/**
	 * 选择行业
	 * 
	 * @param parentCode
	 * @param model
	 * @return
	 */
	@RequestMapping("industry.json")
	@ResponseBody
	public JSONObject industry(String parentCode, Model model) {
		JSONObject result = new JSONObject();
		try {
			List<IndustryInfo> data = basicDataCacheService
					.queryIndustry(parentCode);
			JSONArray finalData = new JSONArray();
			result = toJSONResult(AppResultCodeEnum.SUCCESS, "查询成功");
			for (IndustryInfo info : data) {
				JSONObject json = new JSONObject();
				json.put("code", info.getCode());
				json.put("name", info.getName());
				json.put("level", info.getLevel());
				json.put("remark",
						info.getRemark() == null ? "" : info.getRemark());
				if (info.getLevel() >= 3) {
					List<IndustryInfo> tmpData = basicDataCacheService
							.queryIndustry(info.getCode());
					if (tmpData != null && tmpData.size() > 0) {
						json.put("hasChildren", BooleanEnum.IS.code());
					} else {
						json.put("hasChildren", BooleanEnum.NO.code());
					}
				} else {
					json.put("hasChildren", BooleanEnum.IS.code());
				}
				finalData.add(json);
			}
			result.put("data", finalData);
		} catch (Exception e) {
			logger.error("查询行业信息失败", e);
			result = toJSONResult(AppResultCodeEnum.FAILED, "查询行业信息失败");
		}

		return result;
	}
	
	
	/**
	 * 选择职员
	 * @param order
	 * @return
	 */
	@RequestMapping("chooseClerk.json")
	@ResponseBody
	public JSONObject chooseClerk(String clerkName) {
		JSONObject result = new JSONObject();
		try {
			JSONObject page = new JSONObject();

			UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
					propertyConfigService.getBmpServiceUserDetailsService());
			String qryResult = serviceProxy.loadUserOrgByFullName("%"+StringUtils.defaultIfBlank(clerkName, "_")+"%");
			JSONObject resultMap = JsonParseUtil.parseObject(qryResult, JSONObject.class);
			if("1".equals(resultMap.get("result"))){
				JSONArray dataList = new JSONArray();
				JSONArray sysUserData = (JSONArray) resultMap.get("data");
				for(Object d : sysUserData){
					JSONObject tmp = (JSONObject) d;
					JSONObject user = tmp.getJSONObject("user");
					JSONObject sysOrg = tmp.getJSONObject("sysOrg");
					if(user!=null && sysOrg!=null){
						JSONObject json = new JSONObject();
						json.put("clerkId", user.getString("userId"));// 职员ID
						json.put("clerkIdName", user.getString("fullname"));//职员名字
						json.put("clerkAccount", user.get("account"));//职员帐号
						json.put("deptName", sysOrg.getString("orgName"));
						dataList.add(json);
					}
				}

				page.put("result", dataList);
				page.put("totalPageCount", 1);
				page.put("currentPageNo", 1);
				page.put("pageSize", dataList.size());
				page.put("totalCount", 1);
				
				result.put("page", page);
				
				//项目移交时才用的单选按钮
				// 是否业务总监
				boolean isBusiDirector = DataPermissionUtil.isBusiFZR();
				// 是否风险总监
				boolean isRiskDirector = DataPermissionUtil.isRiskFZR();
				// 是否系统管理员
				boolean isAdmin = DataPermissionUtil.isSystemAdministrator();
				JSONArray btnList = new JSONArray();
				JSONObject e = null;
				if (isBusiDirector || isAdmin) {
					e = new JSONObject();
					e.put("value", "BUSI_MANAGER");
					e.put("name", "客户经理");
					btnList.add(e);
				}
				if (isRiskDirector || isAdmin) {
					e = new JSONObject();
					e.put("value", "RISK_MANAGER");
					e.put("name", "风险经理");
					btnList.add(e);
				}
				result.put("btnList", btnList);
				toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
			}else{
				toJSONResult(result, AppResultCodeEnum.FAILED, "选择职员出错:" + resultMap.get("message"));
			}

		} catch (Exception e) {
			logger.error("查询职员出错 {} ", e);
			toJSONResult(result, AppResultCodeEnum.FAILED, "选择职员出错：" + e.getMessage());
		}
		return result;
	}
	
	/**
	 * 查询用户明细
	 * @param order
	 * @return
	 */
	@RequestMapping("userDetail.json")
	@ResponseBody
	public JSONObject userDetail(Long userId, String userAccount, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			UserDetailInfo userDetail = null;
			if (userId != null && userId > 0) {
				userDetail = bpmUserQueryService.findUserDetailByUserId(userId);
			} else if (StringUtil.isNotEmpty(userAccount)) {
				userDetail = bpmUserQueryService.findUserDetailByAccount(userAccount);
			}
			if (userDetail != null) {
				JSONObject userInfo = new JSONObject();
				result.put("userDetail", userInfo);
				userInfo.put("orgList", JSON.toJSON(userDetail.getOrgList()));
				toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
			} else {
				toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
			}
		} catch (Exception e) {
			toJSONResult(result, AppResultCodeEnum.FAILED, "获取用户信息失败");
			logger.error("查询用户信息出错 {} ", e);
		}
		
		return result;
	}
	private static enum AddressQueryType{
		Clerk("clerk"),Custom("custom");
		private String code;

		private AddressQueryType(String code) {
			this.code = code;
		}
	}
	/**
	 * 查询通讯录
	 * @param order
	 * @return
	 */
	@RequestMapping("queryAddressList.json")
	@ResponseBody
	public JSONObject queryAddressList(String clerkName,String queryType, HttpServletRequest request){
		JSONObject result = null;
		try {
			if(AddressQueryType.Custom.code.equals(queryType)){
				result = queryCustom(clerkName, request);
			}else{
				result = queryClerk(clerkName);
			}
		} catch (Exception e) {
			logger.error("查询通讯录出错：", e);
			result = toJSONResult(AppResultCodeEnum.FAILED, "查询通讯录出错：" + e.getMessage());
		}
		return result;
	}
	
	/**
	 * 查询我的客户通讯录
	 * @param clerkName
	 * @param request 
	 * @return
	 */
	private JSONObject queryCustom(String clerkName, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		
		CustomerQueryOrder order = new CustomerQueryOrder();
		order.setLikeCustomerName(clerkName);
		order.setPageSize(BornApiRequestUtils.getParamterInteger(request, "pageSize", 10));
		order.setPageNumber(BornApiRequestUtils.getParamterInteger(request, "pageNumber", 1));;
		JSONObject data = null;
		//设置权限
		queryCustomerPermissionSet(order);
		
		order.setStatus("on");
		order.setMobileQuery(BooleanEnum.IS.code());
		QueryBaseBatchResult<CustomerBaseInfo> batchResult = 
				customerServiceClient.list(order);
		
		if (batchResult != null && batchResult.isSuccess()) {
			data = new JSONObject();
			JSONArray dataList = new JSONArray();
			List<CustomerBaseInfo> list = batchResult.getPageList();
			for (CustomerBaseInfo info : list) {
				JSONObject json = new JSONObject();
				json.put("personId", info.getUserId());//info中customerId crm自用
				json.put("personName", info.getCustomerName());
				json.put("phone", info.getContactMobile());
				json.put("mobile", info.getContactMobile());
				dataList.add(json);
			}
			data.put("totalCount", batchResult.getTotalCount());
			data.put("totalPageCount", batchResult.getPageCount());
			data.put("currentPageNo", batchResult.getPageNumber());
			data.put("pageSize", batchResult.getPageSize());
			data.put("result", dataList);
			result.put("page", data);
			toJSONResult(result,AppResultCodeEnum.SUCCESS, "");
		}else{
			toJSONResult(result,AppResultCodeEnum.FAILED, batchResult.getMessage());
		}
		return result;
	}


	/**
	 * 查询职员通讯录
	 * @param clerkName
	 * @param result
	 * @throws RemoteException
	 */
	private JSONObject queryClerk(String clerkName)
			throws RemoteException {
		JSONObject result = new JSONObject();

		UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
				propertyConfigService.getBmpServiceUserDetailsService());
		String qryResult = serviceProxy.loadUserOrgByFullName("%"
				+ StringUtils.defaultIfBlank(clerkName, "_") + "%");
		JSONObject resultMap = JsonParseUtil.parseObject(qryResult,
				JSONObject.class);
		if ("1".equals(resultMap.get("result"))) {
			JSONObject page = new JSONObject();
			JSONObject data = new JSONObject();
			page.put("result", data);
			JSONArray sysUserData = (JSONArray) resultMap.get("data");
			String phone = null;
			int count = 1;
			for (Object d : sysUserData) {
				JSONObject tmp = (JSONObject) d;
				JSONObject user = tmp.getJSONObject("user");
				JSONObject sysOrg = tmp.getJSONObject("sysOrg");
				if (user != null && sysOrg != null) {
					phone = StringUtils.defaultString(
							user.getString("mobile"),
							user.getString("phone"));
					if (StringUtils.isNotBlank(phone)) {
						String orgName = sysOrg.getString("orgName");
						if (StringUtils.isNotBlank(orgName)) {
							JSONArray deptTmp = data
									.getJSONArray(orgName);
							if (deptTmp == null) {
								deptTmp = new JSONArray();
								data.put(orgName, deptTmp);
							}
							JSONObject json = new JSONObject();
							json.put("personId",
									user.getString("userId"));// 职员ID
							json.put("personName",
									user.getString("fullname"));// 职员名字
							json.put("phone", phone);// 职员帐号
							deptTmp.add(json);
							count++;
						}
					}
				}
			}
			page.put("totalPageCount", 1);
			page.put("currentPageNo", 1);
			page.put("pageSize", count);
			page.put("totalCount", count);
			result.put("page", page);
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
		} else {
			toJSONResult(result, AppResultCodeEnum.FAILED, "查询职员通讯录出错:"
					+ resultMap.get("message"));
		}
	
		return result;
	}
	
	/**
	 * 选择部门
	 * @param order
	 * @return
	 */
	@RequestMapping("chooseDept.json")
	@ResponseBody
	public JSONObject chooseDept(String deptName) {
		JSONObject result = new JSONObject();
		try {
			JSONObject page = new JSONObject();

			UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
					propertyConfigService.getBmpServiceUserDetailsService());
			String qryResult = serviceProxy.loadOrgByName("%"+StringUtils.defaultIfBlank(deptName, "_")+"%");
			
			JSONObject resultMap = JsonParseUtil.parseObject(qryResult, JSONObject.class);
			if("1".equals(resultMap.get("result"))){
				JSONArray dataList = new JSONArray();
				JSONArray sysUserData = (JSONArray) resultMap.get("data");
				for(Object d : sysUserData){
					JSONObject tmp = (JSONObject) d;
					JSONObject json = new JSONObject();
					json.put("deptId", tmp.getString("orgId"));
					json.put("deptName", tmp.getString("orgName"));
					dataList.add(json);
				
				}

				page.put("result", dataList);
				page.put("totalPageCount", 1);
				page.put("currentPageNo", 1);
				page.put("pageSize", dataList.size());
				page.put("totalCount", dataList.size());
				result.put("page", page);
				
				toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
			}else{
				toJSONResult(result, AppResultCodeEnum.FAILED, "选择部门出错:" + resultMap.get("message"));
			}

		} catch (Exception e) {
			logger.error("查询部门出错 {} ", e);
			toJSONResult(result, AppResultCodeEnum.FAILED, "选择部门出错：" + e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 查询部门信息
	 * @param order
	 * @return
	 */
	@RequestMapping("deptFzr.json")
	@ResponseBody
	public JSONObject deptFzr(Long deptId, String deptCode, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			result = getDeptHead(deptId, deptCode, result);
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
		} catch (Exception e) {
			toJSONResult(result, AppResultCodeEnum.FAILED, "查询部门负责人出错");
			logger.error("查询部门负责人出错 {} ", e);
		}
		return result;
	}


	
	
	/**
	 * 银行账户信息维护-账户选择 ji
	 * @param order
	 * @return
	 */
	@RequestMapping("chooseBankMessage.json")
	@ResponseBody
	public JSONObject chooseBankMessage(BankMessageQueryOrder order) {
		JSONObject result = new JSONObject();
		try {
			order.setDepositAccount(BooleanEnum.YES);
			JSONObject data = null;
			QueryBaseBatchResult<BankMessageInfo> batchResult = bankMessageServiceClient
				.queryBankMessageInfo(order);
			
			if (batchResult != null && batchResult.isSuccess()) {
				data = new JSONObject();
				JSONArray dataList = new JSONArray();
				for (BankMessageInfo info : batchResult.getPageList()) {
					JSONObject json = new JSONObject();
					json.put("bankName", info.getBankName());// 开户银行 
					json.put("accountNo", info.getAccountNo());//账户号码
					dataList.add(json);
				}
				data.put("totalPageCount", batchResult.getPageCount());
				data.put("currentPageNo", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("totalCount", batchResult.getTotalCount());
				data.put("result", dataList);
			}
			result.put("page", data);
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
		} catch (Exception e) {
			logger.error("查询银行账户信息维护-账户选择出错 {} ", e);
			toJSONResult(result, AppResultCodeEnum.FAILED, "选择银行账户出错：" + e.getMessage());
		}
		
		return result;
	}

	/**
	 * 选择区域
	 * 
	 * @param parentCode
	 * @param model
	 * @return
	 */
	@RequestMapping("region.json")
	@ResponseBody
	public JSONObject region(String parentCode, Model model) {
		JSONObject result = new JSONObject();
		try {
			List<RegionInfo> data = basicDataCacheService
					.queryRegion(parentCode);
			JSONArray finalData = new JSONArray();
			for (RegionInfo info : data) {
				JSONObject json = new JSONObject();
				json.put("code", info.getCode());
				json.put("name", info.getName());
				json.put("hasChildren", info.getHasChildren());
				json.put("level", info.getLevel());
				finalData.add(json);
			}
			result.put("data", finalData);
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "查询成功");

		} catch (Exception e) {
			toJSONResult(result, AppResultCodeEnum.FAILED, e.getMessage());
			logger.error("", e);
		}
		return result;
	}

	/**
	 * 构建业务类型JSON数据
	 * 
	 * @param customerType
	 * @param formCode
	 * @param parentId
	 * @return
	 */
	private JSONArray makeBusiTypeJson(String customerType, String formCode,
			int parentId) {

		JSONArray data = new JSONArray();
		boolean isXinhui = DataPermissionUtil.isBelong2Xinhui();
		List<BusiTypeInfo> list = basicDataCacheService.queryBusiType(
				customerType, formCode, parentId);
		
		for (BusiTypeInfo info : list) {
			if ((!isXinhui && !ProjectUtil.isUnderwriting(info.getCode()))
					|| (isXinhui && ProjectUtil.isUnderwriting(info.getCode()))) {
				JSONObject t = new JSONObject();
				t.put("code", info.getCode());
				t.put("name", info.getName());
				t.put("customerType", info.getCustomerType().code());
				if (info.getHasChildren() == BooleanEnum.IS) {
					t.put("children",
							makeBusiTypeJson(customerType, formCode,
									info.getId()));
				} else {
					t.put("setupFormCode", info.getSetupFormCode());
				}
				data.add(t);
			}
		}
		return data;
	}
	
	/**
	 * 查询我的客户信息
	 * @param order
	 * @return
	 */
	@RequestMapping("customer.json")
	@ResponseBody
	public JSONObject customer(HttpServletRequest request,CustomerQueryOrder order) {
		JSONObject result = new JSONObject();
		try {
			String codeOrName =  StringUtils.defaultIfBlank(request.getParameter(LIKE_CODE_OR_NAME),
					request.getParameter("likeCustomerName"));
			order.setLikeNameOrId(codeOrName);
			order.setLikeCustomerName("");
			JSONObject data = null;
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (!DataPermissionUtil.isBelong2Xinhui() && 
					sessionLocal != null && DataPermissionUtil.isBusiManager()) {
				order.setCustomerManagerId(sessionLocal.getUserId());
			}
			order.setStatus("on");
			
			//个人客户
			if (CustomerTypeEnum.PERSIONAL.code().equals(order.getCustomerType())) {
				order.setCustomerType(CustomerTypeEnum.PERSIONAL.code());
			} else if(CustomerTypeEnum.ENTERPRISE.code().equals(order.getCustomerType())){
				//企业客户
				order.setCustomerType(CustomerTypeEnum.ENTERPRISE.code());
			}

			QueryBaseBatchResult<CustomerBaseInfo> batchResult = 
					  customerServiceClient.list(order);
			
			if (batchResult != null && batchResult.isSuccess()) {
				data = new JSONObject();
				JSONArray dataList = new JSONArray();
				List<CustomerBaseInfo> list = batchResult.getPageList();
				for (CustomerBaseInfo info : list) {
					JSONObject json = new JSONObject();
					json.put("customerId", info.getUserId());//info中customerId crm自用
					json.put("customerName", info.getCustomerName());
					//查询详细信息
					if (CustomerTypeEnum.PERSIONAL.code().equals(info.getCustomerType())) {
						PersonalCustomerInfo cInfo = personalCustomerClient.queryById(info
							.getCustomerId());
						if (cInfo != null) {
							CertTypeEnum certType = CertTypeEnum.getByCode(info.getCertType());
							json.put("busiLicenseNo", info.getCertNo());//兼容旧版本
							json.put("certNo", info.getCertNo());
							json.put("certType", info.getCertType());
							json.put("certTypeName", certType == null ? "" : certType.message());
							json.put("mobile", cInfo.getMobile());
							json.put("address", cInfo.getAddress());
							json.put("company", cInfo.getCompany());
							json.put("customerType", CustomerTypeEnum.PERSIONAL);
							
						}
					} else if(CustomerTypeEnum.ENTERPRISE.code().equals(info.getCustomerType())) {
						CompanyCustomerInfo cInfo = companyCustomerClient.queryById(
							info.getCustomerId(), null);
						if (cInfo != null) {
							json.put("busiLicenseNo", info.getBusiLicenseNo());
							json.put("industry", cInfo.getIndustryName());
							json.put("address", cInfo.getAddress());
							EnterpriseNatureEnum et = EnterpriseNatureEnum.getByCode(cInfo
								.getEnterpriseType());
							json.put("enterpriseType",
								et == null ? cInfo.getEnterpriseType() : et.message());
							if (StringUtil.equals(cInfo.getIsOneCert(), BooleanEnum.IS.code())) {
								json.put("certNo", cInfo.getBusiLicenseNo());
								json.put("oneCert", BooleanEnum.IS.code());
							} else {
								json.put("oneCert", BooleanEnum.NO.code());
								json.put("certNo", cInfo.getOrgCode());
							}
							json.put("mobile", cInfo.getContactNo());
							json.put("customerType", CustomerTypeEnum.ENTERPRISE);
							json.put("orgCode", cInfo.getOrgCode());
						}
					}
					dataList.add(json);
				}
				data.put("totalCount", batchResult.getTotalCount());
				data.put("totalPageCount", batchResult.getPageCount());
				data.put("currentPageNo", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("result", dataList);
			}
			result.put("page", data);
			toJSONResult(result,AppResultCodeEnum.SUCCESS, "");
		} catch (Exception e) {
			toJSONResult(result,AppResultCodeEnum.FAILED, e.getMessage());
			logger.error("查询客户出错 {} ", e);
		}
		return result;
	}

	/**
	 * 选择业务类型
	 * 
	 * @param formCode
	 * @param customerType
	 * @param model
	 * @return
	 */
	@RequestMapping("busiType.json")
	@ResponseBody
	public JSONObject busiType(String formCode, String customerType, Model model) {
		JSONObject result = new JSONObject();
		try {
			// 默认企业
			if (StringUtil.isEmpty(customerType)) {
				customerType = CustomerTypeEnum.ENTERPRISE.code();
			}
			result.put("data", makeBusiTypeJson(customerType, formCode, -1));
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "查询成功");
		} catch (Exception e) {
			toJSONResult(result, AppResultCodeEnum.FAILED, e.getMessage());
			logger.error("查询失败 ", e);
		}
		return result;
	}

	/**
	 * 获取菜单
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("loadMenuData.json")
	public JSONObject loadMenuJsonb(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		JSONObject result = new JSONObject();
		String currentString = request.getParameter("currentUrl");
		currentString = PermissionUtil.filterUrl(currentString);
		String[] stringSplit = currentString.split("/");
		if (stringSplit.length > 1) {
			List<MenuInfo> menuInfos = ShiroSessionUtils.getSessionLocal()
					.getUserMenuInfoTreeBySysAlias(stringSplit[1]);
			JSONArray jsonArray = new JSONArray();
			Collections.sort(menuInfos, new MenuComparator<MenuInfo>());
			makeMenuJsonTree(menuInfos, jsonArray, 0);
			result.put("list", jsonArray);
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "查询成功");

		} else {
			toJSONResult(result, AppResultCodeEnum.FAILED, "获取菜单失败");
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private void makeMenuJsonTree(List<MenuInfo> menuInfos,
			JSONArray jsonArray, long index) {
		for (MenuInfo menuInfo : menuInfos) {
			menuInfo.setRank(index);
			Map<String, Object> map = MiscUtil.covertPoToMap(menuInfo);
			map.remove("parentId");
			map.remove("resId");
			List<MenuInfo> childMenuInfos = (List<MenuInfo>) map
					.get("subclass");
			Collections.sort(childMenuInfos, new MenuComparator<MenuInfo>());
			JSONArray childJsonArray = new JSONArray();
			makeMenuJsonTree(childMenuInfos, childJsonArray, index + 1);
			map.put("subclass", childMenuInfos);
			jsonArray.add(map);
		}
	}

	/**
	 * 查询收款人
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping("payee.json")
	@ResponseBody
	public JSONObject payee(UserExtraMessageQueryOrder order) {
		JSONObject result = new JSONObject();
		try {
			JSONObject data = null;
			QueryBaseBatchResult<UserExtraMessageInfo> batchResult = userExtraMessageServiceClient
					.queryUserExtraMessage(order);

			if (batchResult != null && batchResult.isSuccess()) {
				data = new JSONObject();
				JSONArray dataList = new JSONArray();
				for (UserExtraMessageInfo info : batchResult.getPageList()) {
					JSONObject json = new JSONObject();
					json.put("userId", info.getUserId());
					json.put("realName", info.getUserName());
					json.put("bankName", info.getBankName());
					json.put("bankAccountNo", info.getBankAccountNo());
					dataList.add(json);
				}
				data.put("totalPageCount", batchResult.getPageCount());
				data.put("currentPageNo", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("totalCount", batchResult.getTotalCount());
				data.put("result", dataList);
				result.put("page", data);
				toJSONResult(result,AppResultCodeEnum.SUCCESS, "查询成功");
			} else {
				toJSONResult(result,AppResultCodeEnum.FAILED,
						batchResult.getMessage());
			}
		} catch (Exception e) {
			toJSONResult(result,AppResultCodeEnum.FAILED, "查询失败");
			logger.error("查询渠道出错 {} ", e);
		}

		return result;
	}
	
	@ResponseBody
	@RequestMapping("expenseApplication.json")
	public Object expenseApplication(String formId, String expenseType) {
		JSONObject result = new JSONObject();
		try {
			if (StringUtil.isBlank(expenseType)) {
				result = toJSONResult(result, AppResultCodeEnum.FAILED, "费用类型不能为空");
				return result;
			}
			
			String ci = "";
			for (String category : expenseType.split(",")) {
				if (StringUtil.isNotBlank(category)) {
					CostCategoryInfo costInfo = costCategoryServiceClient.queryById(Long
						.valueOf(category));
					if(costInfo!=null){
						if ("还款".equals(costInfo.getName())) {
							ci = ci + "借款";
						}
						if ("冲预付款".equals(costInfo.getName())) {
							ci = ci + "预付款";
						}
					}
				}
			}
			
			if (StringUtil.isBlank(ci)) {
				result = toJSONResult(result, AppResultCodeEnum.FAILED, "未找到费用类型");
				return result;
			}
			
			HashMap<Long, ExpenseCxDetailInfo> cxdetailMap = new HashMap<Long, ExpenseCxDetailInfo>();
			if (StringUtil.isNotBlank(formId)) {
				FormExpenseApplicationInfo expenseInfo = expenseApplicationServiceClient
					.queryByFormId(Long.valueOf(formId));
				if (expenseInfo.getCxdetailList() != null) {
					for (ExpenseCxDetailInfo info : expenseInfo.getCxdetailList()) {
						cxdetailMap.put(info.getFromDetailId(), info);
					}
				}
			}
			
			HashMap<String, CostCategoryInfo> costMap = new HashMap<String, CostCategoryInfo>();
			CostCategoryQueryOrder order0 = new CostCategoryQueryOrder();
			order0.setStatusList(new ArrayList<CostCategoryStatusEnum>());
			order0.getStatusList().add(CostCategoryStatusEnum.NORMAL);
			QueryBaseBatchResult<CostCategoryInfo> batchResult0 = costCategoryServiceClient
				.queryPage(order0);
			for (CostCategoryInfo info : batchResult0.getPageList()) {
				if (ci.contains(info.getName())) {
					costMap.put(String.valueOf(info.getCategoryId()), info);
				}
			}
			
			ExpenseApplicationQueryOrder order = new ExpenseApplicationQueryOrder();
			long curUserId = ShiroSessionUtils.getSessionLocal().getUserId();
			order.setAgent(String.valueOf(curUserId));
			order.setDetail(true);
			order.setPageSize(100000);
			List<FormStatusEnum> statusList = Lists.newArrayList();
			statusList.add(FormStatusEnum.APPROVAL);
			order.setFormStatusList(statusList);
			
			QueryBaseBatchResult<FormExpenseApplicationInfo> batchResult = expenseApplicationServiceClient
				.queryPage(order);
			
			JSONArray data = new JSONArray();
			Money allcx = Money.zero();
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (FormExpenseApplicationInfo info : batchResult.getPageList()) {
					if (info.getDetailList() == null)
						continue;
					for (FormExpenseApplicationDetailInfo detailInfo : info.getDetailList()) {
						CostCategoryInfo costCategory = costMap.get(detailInfo.getExpenseType());
						ExpenseCxDetailInfo cxDetailInfo = cxdetailMap
							.get(detailInfo.getDetailId());
						if (costCategory != null) {
							JSONObject json = new JSONObject();
							Money fpcx = cxDetailInfo == null ? Money.zero() : cxDetailInfo
								.getFpAmount();
							Money xjcx = cxDetailInfo == null ? Money.zero() : cxDetailInfo
								.getXjAmount();
							Money sykcx = detailInfo.getAmount().subtract(
								detailInfo.getFpAmount().add(detailInfo.getXjAmount()));
							
							if (sykcx.compareTo(Money.zero()) <= 0)
								continue;
							
							json.put("sqrq", DateUtil.dtSimpleFormat(info.getApplicationTime()));
							json.put("billno", info.getBillNo());
							json.put("fyzl", costCategory.getName());
							json.put("sqje", AppUtils.toString(detailInfo.getAmount()));
							json.put("sykcx", AppUtils.toString(sykcx));
							json.put("fpcx", AppUtils.toString(fpcx));
							json.put("xjcx", AppUtils.toString(xjcx));
							json.put("expenseId", detailInfo.getExpenseApplicationId());
							json.put("detailId", detailInfo.getDetailId());
							data.add(json);
							//allcx = allcx.add(fpcx).add(xjcx);
							allcx = allcx.add(detailInfo.getAmount());
						}
					}
				}
			}
			result.put("dataList", data);
			result.put("allcx", allcx.toString());
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
		} catch (Exception e) {
			logger.error("查询项目风险处置小组出错", e);
			toJSONResult(result, AppResultCodeEnum.FAILED, "查询项目风险处置小组出错");
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("deptBudgetBalance.json")
	public JSONObject getBalance(String budgetDeptId, String applicationTime, String costId,String queryType) {
		JSONObject result = new JSONObject();
		Money budgetDept = Money.zero();
		try {
			if(StringUtils.isBlank(queryType)){
				toJSONResult(result, AppResultCodeEnum.FAILED, "queryType 不能为空");
				return result;
			}else{
				if("travel".equals(queryType)){
					costId = "1";
				}
				if(StringUtils.isBlank(costId)){
					toJSONResult(result, AppResultCodeEnum.FAILED, "费用种类不能为空");
					return result;
				}
				if (StringUtil.isNotBlank(budgetDeptId) && StringUtil.isNotBlank(applicationTime)) {
					Date applyTime = DateUtil.parse(applicationTime);
					budgetDept = budgetServiceClient.queryBalanceByDeptCategoryId(
							Long.valueOf(budgetDeptId), Long.valueOf(costId), applyTime, false);
				}
			}
			result.put("balance", AppUtils.toString(budgetDept));
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
		} catch (Exception e) {
			logger.error("部门预算余额错误", e);
			toJSONResult(result, AppResultCodeEnum.FAILED, "部门预算余额查询错误");
		}
		
		return result;
	}
	
	/**
	 * 查询当前登录用户的所属部门
	 * @return
	 */
	@RequestMapping("queryUserOrgList.json")
	@ResponseBody
	public JSONObject queryUserOrgList(){
		JSONObject result = new JSONObject();
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal == null || sessionLocal.getUserDetailInfo() == null) {
			toJSONResult(result, AppResultCodeEnum.FAILED, "登陆已失效");
			return result;
		}
		JSONArray orgList = new JSONArray();
		result.put("orgList", orgList );
		result.put("hasDept", false );
		if(sessionLocal != null && sessionLocal.getUserDetailInfo()!=null 
				&& ListUtil.isNotEmpty(sessionLocal.getUserDetailInfo().getOrgList())){
			result.put("hasDept", true );
			Org curOrg = getLocalSession().getUserDetailInfo().getPrimaryOrg();
			for(Org org: sessionLocal.getUserDetailInfo().getOrgList() ){
				JSONObject e = new JSONObject();
				orgList.add(e);
				e.put("orgId", org.getId());
				e.put("name", org.getName());
				if(curOrg!=null && curOrg.getId() == org.getId()){
					e.put("isCur", true);
				}else{
					e.put("isCur", false);
				}
			}
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
		}else{
			toJSONResult(result, AppResultCodeEnum.FAILED, "未获取到当前用户所属部门");
		}
		return result;
	}
	@RequestMapping("switchPrimaryOrg.json")
	@ResponseBody
	public JSONObject switchPrimaryOrg(long orgId) {
		JSONObject json = new JSONObject();
		if (orgId <= 0) {
			toJSONResult(json, AppResultCodeEnum.FAILED, "请选择部门");
			return json;
		}
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal == null || sessionLocal.getUserDetailInfo() == null) {
			toJSONResult(json, AppResultCodeEnum.FAILED, "登陆已失效");
			return json;
		}
		UserDetailInfo userDetailInfo = sessionLocal.getUserDetailInfo();
		if (ListUtil.isNotEmpty(userDetailInfo.getOrgList())) {
			Org primaryOrg = userDetailInfo.getPrimaryOrg();
			for (Org org : userDetailInfo.getOrgList()) {
				if (orgId == org.getId())
					primaryOrg = org;
			}
			userDetailInfo.setPrimaryOrg(primaryOrg);
			for (Org org : userDetailInfo.getOrgList()) {
				org.setPrimary(false);
				if (primaryOrg.getId() == org.getId())
					org.setPrimary(true);
			}
		}
		//兼容老数据
		UserInfo userInfo = sessionLocal.getUserInfo();
		if (userInfo != null && userInfo.getDeptList() != null) {
			SysOrg primaryOrg = userInfo.getPrimaryOrg();
			for (SysOrg org : userInfo.getDeptList()) {
				if (orgId == org.getOrgId())
					primaryOrg = org;
			}
			userInfo.setPrimaryOrg(primaryOrg);
		}
		toJSONResult(json, AppResultCodeEnum.SUCCESS, "已切换至" + userDetailInfo.getPrimaryOrg().getName());
		return json;
	}
	
	/**
	 * 选择授信条件落实情况项目
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping("loanCreditProject.json")
	@ResponseBody
	public JSONObject loanCreditProject(HttpServletRequest request,ProjectQueryOrder order) {
		SessionLocal session = ShiroSessionUtils.getSessionLocal();
		JSONObject result = new JSONObject();
		try {
			JSONObject data = new JSONObject();
			JSONArray dataList = new JSONArray();
			if (order == null)
				order = new ProjectQueryOrder();
			order.setProjectCodeOrName(order.getProjectName());
			String likeCodeOrName = request.getParameter(LIKE_CODE_OR_NAME);
			if(StringUtils.isNotBlank(likeCodeOrName)){
				order.setProjectCodeOrName(likeCodeOrName);
			}
			order.setProjectName("");
			order.setSortCol("p.raw_update_time");
			order.setSortOrder("DESC");
			order.setBusiManagerId(session.getUserId()); // 查询当前业务经理的项目
			setSessionLocalInfo2Order(order);
			List<ProjectStatusEnum> pStatusList = Lists.newArrayList();
			// 除开暂缓的项目
			pStatusList.add(ProjectStatusEnum.NORMAL);
			pStatusList.add(ProjectStatusEnum.FAILED);
			pStatusList.add(ProjectStatusEnum.EXPIRE);
			pStatusList.add(ProjectStatusEnum.OVERDUE);
			pStatusList.add(ProjectStatusEnum.RECOVERY);
			order.setStatusList(pStatusList);
			QueryBaseBatchResult<ProjectInfo> batchResult = projectCreditConditionServiceClient
					.queryProjectAndCredit(order);
			List<ProjectInfo> list = batchResult.getPageList();
			
			for (ProjectInfo info : list) {
				JSONObject json = new JSONObject();
				List<ProjectCreditConditionInfo> listInfo = new ArrayList<ProjectCreditConditionInfo>();
				listInfo = projectCreditConditionServiceClient
						.findProjectCreditConditionByProjectCodeAndStatus(
								info.getProjectCode(),
								CheckStatusEnum.NOT_APPLY.code());
				// 未落实长度
				int size = 0;
				if (listInfo != null) {
					size = listInfo.size();
				}
				json.put("notCreditSize", size);
				json.put("projectCode", info.getProjectCode());
				json.put("projectName", info.getProjectName());
				json.put("customerId", info.getCustomerId());
				json.put("customerName", info.getCustomerName());
				json.put("contractNo", info.getContractNo());// 合同
				if (info.getCustomerType() != null) {
					json.put("customerType", info.getCustomerType().code());
					json.put("amount", info.getAmount().toStandardString());
					json.put("busiType", info.getBusiType());
					json.put("busiTypeName", info.getBusiTypeName());

					if (info.getTimeUnit() != null) {
						json.put("timeLimit", info.getTimeLimit()
								+ info.getTimeUnit().viewName());
					} else {
						json.put("timeLimit", "-");
					}
					dataList.add(json);
				}
			}
			data.put("result", dataList);

			data.put("totalPageCount", batchResult.getPageCount());
			data.put("currentPageNo", batchResult.getPageNumber());
			data.put("pageSize", batchResult.getPageSize());
			data.put("totalCount", batchResult.getTotalCount());
			
			result.put("page", data);
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
		} catch (Exception e) {
			toJSONResult(result, AppResultCodeEnum.FAILED, "查询失败");
			logger.error("信条件落实情况项目查询失败", e);
		}
		return result;
	}
	
	/**
	 * 类似企业信息查询接口 (风险监控)
	 * @param licenseNo 证件号码
	 * @param customName 企业名称或个人姓名
	 * @return
	 */
	@ResponseBody
	@RequestMapping("querySimilarEnterprise.json")
	public JSONObject querySimilarEnterprise(String licenseNo, String customName) {
		JSONObject result = new JSONObject();
		try {
			if (StringUtil.isBlank(customName)) {
				toJSONResult(result, AppResultCodeEnum.FAILED, "客户名称不能为空");
				return result;
			} else if (StringUtil.isBlank(licenseNo)) {
				toJSONResult(result, AppResultCodeEnum.FAILED, "信用统一代码/营业执照号不能为空");
				return result;
			}
			
			//增加企业信息校验【只有校验通过的才查询相似】
			VerifyOrganizationOrder order = new VerifyOrganizationOrder();
			order.setUserType(UserTypeEnum.BUSINESS);
			order.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
			order.setCustomName(customName);
			order.setLicenseNo(licenseNo);
			VerifyOrganizationResult vryResult = riskSystemFacadeApi.verifyOrganizationInfo(order);
			if (vryResult.getResultCode() == CommonResultEnum.EXECUTE_SUCCESS) {
				if (vryResult.isVerySuccess()) {
					String operator = ShiroSessionUtils.getSessionLocal().getUserName();
					Map<String, String> dataMap = new HashMap<>();
					dataMap.put("operator", operator);
					dataMap.put("customName", order.getCustomName());
					dataMap.put("licenseNo", order.getLicenseNo());
					String detailUrl = buildBornApiUrl(dataMap, BornApiServiceEnum.QUERY_RISK_INFO);
					result.put("detailUrl", detailUrl);
					
					result.put("vrySuccess", true);
					result.put("vryMsg", "校验成功");
					toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
					
					//校验通过查询相似
					JSONArray data = new JSONArray();
					result.put("dataList", data);
					
					try {
						QuerySimilarEnterpriseOrder queryOrder = new QuerySimilarEnterpriseOrder();
						queryOrder.setLicenseNo(licenseNo);
						queryOrder.setCustomName(customName);
						queryOrder.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
						queryOrder.setNotifyUrl("no_check");
						QuerySimilarEnterpriseResult batchResult = riskSystemFacadeApi
												.querySimilarEnterprise(queryOrder);
						if (batchResult.getResultCode() == CommonResultEnum.EXECUTE_SUCCESS) {
							if (ListUtil.isNotEmpty(batchResult.getList())) {
								for (CustomInfo info : batchResult.getList()) {
									JSONObject json = new JSONObject();
									json.put("licenseNo", info.getLicenseNo());
									json.put("custoName", info.getCustomName());
									//构建跳转detailUrl
									dataMap.clear();
									dataMap.put("operator", operator);
									dataMap.put("toUrl", info.getDetailUrl());
									detailUrl = buildBornApiUrl(dataMap, BornApiServiceEnum.LOGIN_RISK_SYSTEM);
									json.put("detailUrl", detailUrl);
									data.add(json);
								}
							}
						}else{
							toJSONResult(result, AppResultCodeEnum.FAILED, "校验通过,但查询相似企业失败");
						}
					} catch (Exception e) {
						logger.error("校验成功,但查询类似企业失败:",e);
						data.clear();
						toJSONResult(result, AppResultCodeEnum.FAILED, "校验通过,但查询相似企业失败");
					}
					
				} else {
					toJSONResult(result, AppResultCodeEnum.SUCCESS, vryResult.getResultMessage());
					result.put("vrySuccess", false);
					result.put("vryMsg", vryResult.getResultMessage());
				}
			} else {
				toJSONResult(result, AppResultCodeEnum.FAILED, vryResult.getResultMessage());

			}
		} catch (Exception e) {
			toJSONResult(result, AppResultCodeEnum.FAILED, e.getMessage());
			logger.error("类似企业信息查询失败： ", e);
		}
		logger.info("类似企业信息查询={}",result);
		return result;
	}
	
	/**
	 * 失信黑名单
	 * @return
	 * @param certNo 身份证号
	 * @param name=姓名
	 * @param type "person", "个人" ;"company", "公司"
	 * name=河北骑岸建筑工程有限公司&certNo=567352437&type=company
	 */
	@ResponseBody
	@RequestMapping("dishonestQuery.json")
	public JSONObject dishonestQuery(String customName,String certNo,String userType,String oneCert) {
		JSONObject result = new JSONObject();
		try {
			ApixDishonestQueryOrder queryOrder = new  ApixDishonestQueryOrder();
			queryOrder.setName(customName);

			queryOrder.setCertNo(certNo);
			queryOrder.setLicenseNo(certNo);
			queryOrder.setOrgCode(certNo);
			
			CustomerTypeEnum customerType = CustomerTypeEnum
					.getByCode(userType);
			queryOrder.setType(customerType != CustomerTypeEnum.PERSIONAL ? ApixUserTypeEnum.COMPANY
					: ApixUserTypeEnum.PERSON);
			
			if(queryOrder.getType() == ApixUserTypeEnum.COMPANY){
				if(StringUtils.isBlank(oneCert)){
					throw new BornApiException("三证合一标志不能为空");
				}else{
					queryOrder.setOneCert(checkBoolean(oneCert));
				}
				
			}
			queryOrder.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
			
			ApixDishonestQueryResult batchResult = apixCreditInvestigationFacadeApi
				.dishonestQuery(queryOrder);
			
			JSONArray data = new JSONArray();
			result.put("dataList", data);
			if (batchResult.getResultCode() == CommonResultEnum.EXECUTE_SUCCESS) {
				//无失信记录
				if(StringUtils.equals(ApixResultEnum.NO_DISHONEST.code(), batchResult.getCode())){
					toJSONResult(result, AppResultCodeEnum.SUCCESS, StringUtil.defaultIfBlank(batchResult.getMsg(),
							batchResult.getResultMessage()));
				}else if(StringUtil.equals(ApixResultEnum.EXECUTE_SUCCESS.code(), batchResult.getCode()))
				{
					if (ListUtil.isNotEmpty(batchResult.getDishonestList())) {
						for (DishonestInfo info : batchResult.getDishonestList()) {
							JSONObject json = new JSONObject();
							json.put("code", info.getCode());//案号
							json.put("duty", info.getDisruptType());//法律文书确定的义务
							json.put("disruptType", info.getDuty());//具体情形
							json.put("pubTime", info.getPubTime());//发布时间
							json.put("court", info.getCourt());//执行法院
							json.put("performance", info.getPerformance());//履行情况
							json.put("sex", info.getSex());//性别
							json.put("name", info.getName());//姓名
							json.put("area", info.getArea());//省份
							json.put("age", info.getAge());//年龄
							json.put("certNo", info.getCertNo());//证件号码
							data.add(json);
						}
						toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
					} else {
						toJSONResult(result, AppResultCodeEnum.SUCCESS, StringUtil.defaultIfBlank(batchResult.getMsg(),
								batchResult.getResultMessage()));
					}
				} else {
					toJSONResult(result, AppResultCodeEnum.FAILED, "校验失败,请检查账户余额是否充足");
				}
			} else {
				toJSONResult(result, AppResultCodeEnum.FAILED, StringUtil.defaultIfBlank(batchResult.getMsg(),
						batchResult.getResultMessage()));
			}
		} catch (Exception e) {
			toJSONResult(result, AppResultCodeEnum.FAILED, "查询失信记录失败");
			logger.error( "查询失信记录失败", e);
		}
		logger.info("查询失信记录={}",result);
		return result;
	}
	
	/** 校验企业信息 **/
	@ResponseBody
	@RequestMapping("verifyOrganizationInfo.json")
	public JSONObject verifyOrganizationInfo(VerifyOrganizationOrder order) {
		JSONObject json = new JSONObject();
		try {
			if (StringUtil.isBlank(order.getCustomName()) || StringUtil.isBlank(order.getLicenseNo())) {
				toJSONResult(json, AppResultCodeEnum.FAILED,"客户名与证件号不能为空");
				return json;
			}
			order.setUserType(UserTypeEnum.BUSINESS);
			order.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
			VerifyOrganizationResult result = riskSystemFacadeApi.verifyOrganizationInfo(order);
			if (result.getResultCode() == CommonResultEnum.EXECUTE_SUCCESS) {
				if (result.isVerySuccess()) {
					String operator = ShiroSessionUtils.getSessionLocal().getUserName();
					Map<String, String> dataMap = new HashMap<>();
					dataMap.put("operator", operator);
					dataMap.put("customName", order.getCustomName());
					dataMap.put("licenseNo", order.getLicenseNo());
					String detailUrl = buildBornApiUrl(dataMap, BornApiServiceEnum.QUERY_RISK_INFO);
					json.put("detailUrl", detailUrl);
					toJSONResult(json, AppResultCodeEnum.SUCCESS, "");
					
				} else {
					toJSONResult(json, AppResultCodeEnum.FAILED, result.getResultMessage());
				}
			} else {
				toJSONResult(json, AppResultCodeEnum.FAILED, result.getResultMessage());
			}
		} catch (Exception e) {
			logger.error("校验企业信息失败", e);
			toJSONResult(json, AppResultCodeEnum.FAILED, "校验企业信息失败:" + e.getMessage());
		}
		logger.info("校验企业信息={}",json);
		return json;
	}
	
	@RequestMapping("validateCustomInfo.json")
	@ResponseBody
	public JSONObject validateCustomInfo(String customName ,String certNo,String userType) {
		JSONObject json = new JSONObject();
		try {
			if (StringUtil.isBlank(customName)
				|| StringUtil.isBlank(certNo) || StringUtil.isBlank(userType)) {
				toJSONResult(json, AppResultCodeEnum.FAILED, "参数不完整");
				return json;
			}
			if(CustomerTypeEnum.PERSIONAL.code().equals(userType)){
				ApixValidateIdCardOrder order = new ApixValidateIdCardOrder();
				order.setName(customName);
				order.setCertNo(certNo);
				order.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
				
				ApixValidateIdCardResult result = apixCreditInvestigationFacadeApi
						.validateIdCard(order);
				if (CommonResultEnum.EXECUTE_SUCCESS == result.getResultCode()) {
					toJSONResult(json, AppResultCodeEnum.SUCCESS, StringUtil.isNotBlank(result.getMsg()) ? result.getMsg()
							: result.getResultMessage());
					json.put("vrySuccess", ApixResultEnum.EXECUTE_SUCCESS.code().equals(result.getCode()));
				}else{
					toJSONResult(json, AppResultCodeEnum.FAILED, StringUtil.isNotBlank(result.getMsg()) ? result.getMsg()
							: result.getResultMessage());
				}
				json.put("vryMsg", json.get(JckBaseService.MESSAGE));
			}else if(CustomerTypeEnum.ENTERPRISE.code().equals(userType)){
				VerifyOrganizationOrder order = new VerifyOrganizationOrder();
				order.setCustomName(customName);
				order.setLicenseNo(certNo);
				order.setUserType(UserTypeEnum.BUSINESS);
				order.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
				VerifyOrganizationResult result = riskSystemFacadeApi.verifyOrganizationInfo(order);
				if (result.getResultCode() == CommonResultEnum.EXECUTE_SUCCESS) {
					json.put("vrySuccess", result.isVerySuccess());
					toJSONResult(json, AppResultCodeEnum.SUCCESS, "");
					if (result.isVerySuccess()) {
						String operator = ShiroSessionUtils.getSessionLocal().getUserName();
						Map<String, String> dataMap = new HashMap<>();
						dataMap.put("operator", operator);
						dataMap.put("customName", order.getCustomName());
						dataMap.put("licenseNo", order.getLicenseNo());
						String detailUrl = buildBornApiUrl(dataMap, BornApiServiceEnum.QUERY_RISK_INFO);
						json.put("detailUrl", detailUrl);
						json.put("vryMsg", "校验通过");
					}else{
						json.put("vryMsg", result.getResultMessage());
					}
				} else {
					toJSONResult(json, AppResultCodeEnum.FAILED, result.getResultMessage());
				}
			}else{
				toJSONResult(json, AppResultCodeEnum.FAILED, "用户类型不合法" + userType);
			}
			
		} catch (Exception e) {
			toJSONResult(json, AppResultCodeEnum.FAILED, e.getMessage());
			logger.error("用户信息校验出错 {}", e);
		}
		logger.error("用户信息校验出参= {}", json);
		return json;
	}

	
	/**
	 * 风险检索：用户信息复合查询
	 * @return
	 * @param certNo 身份证号
	 * @param name=姓名
	 * @param type "person", "个人" ;"company", "公司"
	 * name=河北骑岸建筑工程有限公司&certNo=567352437&type=company
	 */
	@ResponseBody
	@RequestMapping("riskQuery.json")
	public Object riskQuery(String userType,String customName, String certNo,String orgCode, String licenseNo,String oneCert) {
		JSONObject result = new JSONObject();
		try {
			if(StringUtils.isBlank(userType)){
				toJSONResult(result, AppResultCodeEnum.FAILED, "用户类型不能为空");
			}else{
				CustomerTypeEnum userTypeEnum = CustomerTypeEnum.getByCode(userType);
				if(userTypeEnum==null){
					toJSONResult(result, AppResultCodeEnum.FAILED, "用户类型非法:" + userType);
				}else{
					if(userTypeEnum == CustomerTypeEnum.PERSIONAL){
						//校验用户信息
						JSONObject vryRs = validateCustomInfo(customName,certNo, userType);
						result.putAll(vryRs);
						if(JckBaseService.isSuccess(vryRs)){
							 vryRs = dishonestQuery(customName,certNo, userType,"");
							 if(JckBaseService.isSuccess(vryRs)){
								 result.put("dishonestDataList", vryRs.get("dataList"));
								 result.put("dishonestMsg", "查询成功");
								 result.put("dishonestFlag", true);
							 }else{
								 result.put("dishonestFlag", false);
								 result.put("dishonestMsg", "查询失信记录失败");
							 }
						}
					}else if(userTypeEnum == CustomerTypeEnum.ENTERPRISE){
						//校验用户信息
						JSONObject vryRs = validateCustomInfo(customName,licenseNo, userType);
						result.putAll(vryRs);
						if(JckBaseService.isSuccess(vryRs)){
							String cardNo = "";
							if(checkBoolean(oneCert)){
								cardNo =  licenseNo;
							}else{
								cardNo =  orgCode;
							}
							 vryRs = dishonestQuery(customName,cardNo, userType,oneCert);
							 if(JckBaseService.isSuccess(vryRs)){
								 result.put("dishonestDataList", vryRs.get("dataList"));
								 result.put("dishonestMsg", "查询成功");
								 result.put("dishonestFlag", true);
							 }else{
								 result.put("dishonestMsg", "查询失信记录失败");
								 result.put("dishonestFlag", false);
							 }
							 //相似企业查询
							 vryRs = querySimilarEnterprise(licenseNo, customName);
							 if(JckBaseService.isSuccess(vryRs)){
								 result.put("similarDataList", vryRs.get("dataList"));
								 result.put("similarQueryMsg", "查询成功");
								 result.put("similarQueryFlag", true);
							 }else{
								 result.put("similarQueryMsg", "查询相似企业信息失败");
								 result.put("similarQueryFlag", false);
							 }
						}
					}
				}

			}
		} catch (Exception e) {
			result.clear();
			toJSONResult(result, AppResultCodeEnum.FAILED, "风险检索失败:" + e.getMessage());
			logger.error( "风险检索失败", e);
		}
		logger.info("风险检索成功={}",result);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("loadWorkflowProcessList.json")
	public JSONObject loadWorkflowProcessList(HttpServletRequest request, String actInstId) {
		JSONObject result = new JSONObject();
		try {
			if(StringUtils.isBlank(actInstId)){
				throw new BornApiException("actInstId为空");
			}
			QueryBaseBatchResult<WorkflowProcessLog> batchResult = workflowEngineWebClient
				.getProcessOpinionByActInstId(actInstId);
			JSONArray list = new JSONArray();
			for(WorkflowProcessLog p : batchResult.getPageList()){
				JSONObject e = new JSONObject();
				e.put("taskName",  p.getTaskName());
				e.put("exeFullname",  p.getExeFullname());
				e.put("startTime",  p.getStartTime());
				e.put("endTime",  StringUtils.defaultIfBlank(p.getEndTime(), ""));
				e.put("checkStatusMessage",  p.getCheckStatusMessage());
				e.put("opinion",  StringUtils.defaultIfBlank(p.getOpinion(), ""));
				list.add(e);
			}
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
			result.put("dataList", list);
		} catch (Exception e) {
			logger.error("查询审批历史记录失败",e);
			toJSONResult(result, AppResultCodeEnum.FAILED, e.getMessage());
		}
		return result;
	}
	
}
