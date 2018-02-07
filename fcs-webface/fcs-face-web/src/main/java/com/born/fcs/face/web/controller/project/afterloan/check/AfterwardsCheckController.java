package com.born.fcs.face.web.controller.project.afterloan.check;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.crm.ws.service.enums.ChangeTypeEnum;
import com.born.fcs.crm.ws.service.info.ChannalInfo;
import com.born.fcs.crm.ws.service.info.CustomerDetailInfo;
import com.born.fcs.crm.ws.service.order.query.ChannalQueryOrder;
import com.born.fcs.face.integration.bpm.service.info.UserInfo;
import com.born.fcs.face.integration.bpm.service.info.WorkflowProcessLog;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.bean.AttachmentBean;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.util.DataFinancial;
import com.born.fcs.pm.util.DataFinancialHelper;
import com.born.fcs.pm.util.DataRow;
import com.born.fcs.pm.util.DataSheet;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CaseStatusEnum;
import com.born.fcs.pm.ws.enums.CheckReportEditionEnums;
import com.born.fcs.pm.ws.enums.CollectCodeEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.CreditConditionTypeEnum;
import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.info.check.AfterwardsCheckInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckBaseInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckLitigationInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckReportContentInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckReportProjectInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCreditConditionInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCustomerInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectLgLitigationDetailInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectVOInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectBondInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectEntrustedInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectGuaranteeInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectLgLitigationInfo;
import com.born.fcs.pm.ws.info.financialkpi.FinancialKpiInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationFinancialReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FinancialReviewKpiInfo;
import com.born.fcs.pm.ws.info.finvestigation.InvestigationInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.ProjectCreditConditionInfo;
import com.born.fcs.pm.ws.info.risklevel.RiskLevelInfo;
import com.born.fcs.pm.ws.info.setup.FProjectCustomerBaseInfo;
import com.born.fcs.pm.ws.info.setup.FProjectLgLitigationInfo;
import com.born.fcs.pm.ws.order.check.AfterwordsCheckQueryOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckBaseOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckLitigationOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckQueryOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckReportContentOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckReportProjectOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckReportProjectSimpleOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCustomerOrder;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.finvestigation.InvestigationQueryOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.ProjectCreditConditionQueryOrder;
import com.born.fcs.pm.ws.order.risklevel.RiskLevelQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 保后检查报告
 * 
 * 
 * @author Fei
 * 
 */
@Controller
@RequestMapping("projectMg/afterwardsCheck")
public class AfterwardsCheckController extends WorkflowBaseController {
	
	private static final String VM_PATH = "/projectMg/afterLoanMg/afterConfirminSpect/";
	
	private static final Map<String, String> VM_SUFFIX = new HashMap<>();
	static {
		//通用
		VM_SUFFIX.put(CheckReportEditionEnums.V_COMMON.code(), "Base");
		//房地产开发
		VM_SUFFIX.put(CheckReportEditionEnums.V_REAL_ESTATE.code(), "RealEstate");
		//建筑行业
		VM_SUFFIX.put(CheckReportEditionEnums.V_CONSTRUCTION.code(), "Construction");
		//生产制造业
		VM_SUFFIX.put(CheckReportEditionEnums.V_MANUFACTURING.code(), "Manufacturing");
		//小贷公司
		VM_SUFFIX.put(CheckReportEditionEnums.V_LOAN.code(), "Loan");
		//城市开发
		VM_SUFFIX.put(CheckReportEditionEnums.V_CITY.code(), "City");
		//固定资产授信
		//		VM_SUFFIX.put(CheckReportEditionEnums.V_FIXED_ASSETS.code(), "FixedAssets");
	}
	
	private static final String EXCEL_BASE = "acr_b";
	private static final String EXCEL_BASE_O = "acr_b_o";
	private static final String EXCEL_BASE_J = "acr_b_j";
	private static final String EXCEL_CONTENT_CREDIT = "acr_c_c_f";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "checkDate", "openingDate", "closingDate", "judgeDate", "date1",
								"date2", "date3", "startTime", "endTime" };
	}
	
	@Override
	protected String[] getMoneyInputNameArray() {
		return new String[] { "amount", "usedAmount", "repayedAmount", "receivedAmount",
								"receivingAmount", "salingAmount", "paidLandAmount",
								"paidProjectAmount", "planProjectAmount", "planInvestAmount",
								"openingArea", "salingArea", "planArea", "loanedAmount",
								"availableAmount", "releaseBalance", "guaranteeAmount",
								"guaranteeDeposit" };
	}
	
	/**
	 * 查询列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(AfterwordsCheckQueryOrder order, Model model) {
		
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (hasAllDataPermission()) {//拥有所有数据权限
				order.setLoginUserId(0);
				order.setDeptIdList(null);
			} else if ((hasPrincipalDataPermission() || DataPermissionUtil.isBusinessZHG())
						&& userInfo != null) { //拥有所负责的数据权限，按照部门维度查询数据
				order.setLoginUserId(sessionLocal.getUserId());
				order.setDeptIdList(userInfo.getDeptIdList());
			} else { //按照人员维度查询数据
				order.setLoginUserId(sessionLocal.getUserId());
				order.setDeptIdList(null);
			}
			//查看草稿的人员
			order.setDraftUserId(sessionLocal.getUserId());
		}
		
		QueryBaseBatchResult<AfterwardsCheckInfo> batchResult = afterwardsCheckServiceClient
			.list(order);
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		
		return VM_PATH + "list.vm";
	}
	
	//	行业和模板的对应关系：
	//	通用：适用除特殊行业制定模板外的所有行业
	//	生产制造业：C生产制造业
	//	建筑业：E类建筑业
	//	小贷公司模板：J金融业
	//	房地产开发类：K房地产业
	//	城市开发类：L租赁和商务服务业
	
	@RequestMapping("edit.htm")
	public String edit(Model model, @RequestParam(value = "formId", required = false,
			defaultValue = "0") long formId,
						@RequestParam(value = "projectCode", required = false) String projectCode) {
		
		FAfterwardsCheckInfo afterCheckInfo = null;
		CustomerDetailInfo customerInfo = null;
		if (formId > 0) {
			FormInfo form = formServiceClient.findByFormId(formId);
			model.addAttribute("form", form);
			afterCheckInfo = afterwardsCheckServiceClient.findByFormId(formId);
		} else {
			if (StringUtil.isNotBlank(projectCode)) {
				afterCheckInfo = new FAfterwardsCheckInfo();
				ProjectInfo project = projectServiceClient.queryByCode(projectCode, false);
				if (null != project) {
					afterCheckInfo.setProjectCode(projectCode);
					afterCheckInfo.setProjectName(project.getProjectName());
					afterCheckInfo.setCustomerId(project.getCustomerId());
					afterCheckInfo.setCustomerName(project.getCustomerName());
					FAfterwardsCheckQueryOrder queryOrder = new FAfterwardsCheckQueryOrder();
					queryOrder.setProjectCode(projectCode);
					//					queryOrder.setEdition(edition)
					FAfterwardsCheckInfo oldInfo = afterwardsCheckServiceClient
						.queryLastEdition(queryOrder);
					Calendar c = Calendar.getInstance();
					c.setTime(new Date());
					int year = c.get(Calendar.YEAR);
					int time = 1;
					if (null != oldInfo) {
						if (oldInfo.getRoundYear() == year) {
							time = oldInfo.getRoundTime() + 1;
						}
					}
					afterCheckInfo.setRoundYear(year);
					afterCheckInfo.setRoundTime(time);
				}
				
				//获取历史版本
				FAfterwardsCheckQueryOrder queryOrder = new FAfterwardsCheckQueryOrder();
				queryOrder.setProjectCode(projectCode);
				queryOrder.setPageNumber(1L);
				queryOrder.setPageSize(99L);
				QueryBaseBatchResult<FAfterwardsCheckInfo> batchResult = afterwardsCheckServiceClient
					.queryAfterwardsCheckReport(queryOrder);
				model.addAttribute("page", PageUtil.getCovertPage(batchResult));
				
				// 查询客户信息
				customerInfo = customerServiceClient.queryByUserId(afterCheckInfo.getCustomerId());
				if (null != customerInfo) {
					if (StringUtil.isNotBlank(customerInfo.getIndustryCode())) {
						if (customerInfo.getIndustryCode().startsWith("C")) {
							afterCheckInfo.setSpecialIndustry(true);
							afterCheckInfo.setIndustryName("生产制造业");
							afterCheckInfo.setEdition(CheckReportEditionEnums.V_MANUFACTURING);
						} else if (customerInfo.getIndustryCode().startsWith("E")) {
							afterCheckInfo.setSpecialIndustry(true);
							afterCheckInfo.setIndustryName("建筑业");
							afterCheckInfo.setEdition(CheckReportEditionEnums.V_CONSTRUCTION);
						} else if (customerInfo.getIndustryCode().startsWith("J")) {
							afterCheckInfo.setSpecialIndustry(true);
							afterCheckInfo.setIndustryName("金融业");
							afterCheckInfo.setEdition(CheckReportEditionEnums.V_LOAN);
						} else if (customerInfo.getIndustryCode().startsWith("K")) {
							afterCheckInfo.setSpecialIndustry(true);
							afterCheckInfo.setIndustryName("房地产业");
							afterCheckInfo.setEdition(CheckReportEditionEnums.V_REAL_ESTATE);
						} else if (customerInfo.getIndustryCode().startsWith("L")) {
							afterCheckInfo.setSpecialIndustry(true);
							afterCheckInfo.setIndustryName("租赁和商务服务业");
							afterCheckInfo.setEdition(CheckReportEditionEnums.V_CITY);
						}
					}
				}
			}
		}
		
		if (null != afterCheckInfo) {
			model.addAttribute("info", afterCheckInfo);
			// 查询客户信息
			if (null == customerInfo) {
				customerInfo = customerServiceClient.queryByUserId(afterCheckInfo.getCustomerId());
			}
			model.addAttribute("customerInfo", customerInfo);
		}
		
		List<CheckReportEditionEnums> editions = CheckReportEditionEnums.getAllEnum();
		model.addAttribute("editions", editions);
		
		return VM_PATH + "addSpect_DB_WD.vm";
	}
	
	@RequestMapping("save.json")
	@ResponseBody
	public JSONObject save(FAfterwardsCheckOrder order) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		
		String tipPrefix = "保存保后检查报告";
		try {
			//			CheckReportEditionEnums edition = CheckReportEditionEnums.getByCode(order.getEdition());
			setSessionLocalInfo2Order(order);
			order.setFormCode(FormCodeEnum.AFTERWARDS_CHECK_GUARANTEE);
			order.setCheckIndex(0); // 选择项目页面默认通过
			order.setCheckStatus(1);
			FormBaseResult result = afterwardsCheckServiceClient.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	@RequestMapping("copy.json")
	@ResponseBody
	public JSONObject copy(FAfterwardsCheckOrder order) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		
		String tipPrefix = "复制保后检查报告";
		try {
			setSessionLocalInfo2Order(order);
			order.setFormCode(FormCodeEnum.AFTERWARDS_CHECK_GUARANTEE);
			order.setCheckIndex(0); // 选择项目页面默认通过
			order.setCheckStatus(1);
			FormBaseResult result = afterwardsCheckServiceClient.copy(order);
			//返回新的formId，然后跳转到编辑页面
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	//审核
	@RequestMapping("audit.htm")
	public String audit(HttpServletRequest request, Model model, @RequestParam(value = "formId",
			required = true) long formId) {
		String suffix = queryCommon(request, formId, model);
		model.addAttribute("suffix", suffix);
		return VM_PATH + "audit.vm";
	}
	
	@RequestMapping("view.htm")
	public String view(Model model, long formId) {
		String suffix = queryCommon(null, formId, model);
		return VM_PATH + "viewReport" + suffix + ".vm";
	}
	
	private String queryCommon(HttpServletRequest request, long formId, Model model) {
		CheckReportEditionEnums edition = CheckReportEditionEnums.V_COMMON;
		
		FormInfo form = formServiceClient.findByFormId(formId);
		model.addAttribute("form", form);// 表单信息
		if (null != request) {
			model.addAttribute("formCode", form.getFormCode());
			initWorkflow(model, form, request.getParameter("taskId"));
		}
		FAfterwardsCheckInfo afterCheckInfo = afterwardsCheckServiceClient.findByFormId(formId);
		model.addAttribute("afterCheckInfo", afterCheckInfo);
		String projectCode = afterCheckInfo.getProjectCode();
		edition = afterCheckInfo.getEdition();
		
		FAfterwardsCheckBaseInfo baseInfo = afterwardsCheckServiceClient
			.findBaseInfoByFormId(formId);
		
		model.addAttribute("baseInfo", baseInfo);
		if (null != baseInfo) {
			//附件列表
			queryCommonAttachmentData(model, baseInfo.getBaseId() + "",
				CommonAttachmentTypeEnum.CUSTOMER_OPINION);
			queryCommonAttachmentData(model, baseInfo.getBaseId() + "",
				CommonAttachmentTypeEnum.AFTER_DATA_COLLECT);
			queryCommonAttachmentData(model, baseInfo.getBaseId() + "",
				CommonAttachmentTypeEnum.AFTER_REPORT);
		} else {
			//查询项目相关信息
			queryProjectInfo(projectCode, model, baseInfo);
		}
		
		//		ProjectCreditConditionQueryOrder queryOrder = new ProjectCreditConditionQueryOrder();
		//		// 授信条件落实情况的显示 (条件：审核通过)
		//		queryOrder.setStatus(CreditCheckStatusEnum.CHECK_PASS.code());
		//		queryOrder.setProjectCode(projectCode);
		//		QueryBaseBatchResult<ProjectCreditConditionInfo> batchResult = projectCreditConditionServiceClient
		//			.queryCreditCondition(queryOrder);
		//		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		model.addAttribute("counters", CollectCodeEnum.getCounterGuaranteeEnum());
		model.addAttribute("collections", CollectCodeEnum.getCollectionEnum());
		processCountered(projectCode, model, baseInfo);
		
		FAfterwardsCheckReportContentInfo contentInfo = afterwardsCheckServiceClient
			.findContentByFomrId(formId);
		if (null != contentInfo && contentInfo.getContentId() > 0) {
			queryCommonAttachmentData(model, contentInfo.getContentId() + "",
				CommonAttachmentTypeEnum.CONTENT_ATTACHMENT);
			queryCommonAttachmentData(model, contentInfo.getContentId() + "",
				CommonAttachmentTypeEnum.TAX_CERTIFICATE);
			queryCommonAttachmentData(model, contentInfo.getContentId() + "",
				CommonAttachmentTypeEnum.BANK_STATEMENT);
		}
		model.addAttribute("contentInfo", contentInfo);
		
		long riskLevelFormId = queryRiskLevelFormId(projectCode);
		model.addAttribute("riskLevelFormId", riskLevelFormId);
		FAfterwardsCheckQueryOrder queryOrder = new FAfterwardsCheckQueryOrder();
		queryOrder.setProjectCode(projectCode);
		queryOrder.setEdition(edition.code());
		FAfterwardsCheckInfo last = afterwardsCheckServiceClient.queryLastEdition(queryOrder);
		if (null != last) {
			model.addAttribute("lastCheckFormId", last.getFormId());
		}
		
		CustomerDetailInfo customerInfo = customerServiceClient.queryByUserId(afterCheckInfo
			.getCustomerId());
		model.addAttribute("customerInfo", customerInfo);
		
		//添加客户信息
		addCustomerInfo(formId, afterCheckInfo.getCustomerId(), model);
		model.addAttribute("showCustomer", showCustomer());
		
		return VM_SUFFIX.get(edition.code());
	}
	
	private void processCountered(String projectCode, Model model, FAfterwardsCheckBaseInfo baseInfo) {
		ProjectCreditConditionQueryOrder queryOrder = new ProjectCreditConditionQueryOrder();
		// 授信条件落实情况的显示 (条件：审核通过)
		//		queryOrder.setStatus(CreditCheckStatusEnum.CHECK_PASS.code());
		queryOrder.setIsConfirm(BooleanEnum.IS.code());
		queryOrder.setProjectCode(projectCode);
		queryOrder.setPageNumber(1L);
		queryOrder.setPageSize(999L);
		QueryBaseBatchResult<ProjectCreditConditionInfo> batchResult = projectCreditConditionServiceClient
			.queryCreditCondition(queryOrder);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		if (null != batchResult && ListUtil.isNotEmpty(batchResult.getPageList())) {
			Set<String> countered = new HashSet<>();
			for (ProjectCreditConditionInfo credit : batchResult.getPageList()) {
				if (credit.getType() == CreditConditionTypeEnum.PLEDGE) {
					countered.add(CollectCodeEnum.PLEDGE.code());
				} else if (credit.getType() == CreditConditionTypeEnum.MORTGAGE) {
					countered.add(CollectCodeEnum.MORTGAGE.code());
				} else if (credit.getType() == CreditConditionTypeEnum.GUARANTOR) {
					if (null != credit.getItemDesc()) {
						if (credit.getItemDesc().contains("法人连带")
							|| credit.getItemDesc().contains("法人一般")) {
							countered.add(CollectCodeEnum.LEGAL_GUARANTEE.code());
						} else if (credit.getItemDesc().contains("个人连带")
									|| credit.getItemDesc().contains("个人一般")) {
							countered.add(CollectCodeEnum.PERSONAL_GUARANTEE.code());
						}
					}
				}
			}
			model.addAttribute("countered", new ArrayList<>(countered));
		}
		
		if (null != baseInfo && ListUtil.isNotEmpty(baseInfo.getConditions())) {
			Set<String> countered = new HashSet<>();
			for (FAfterwardsCreditConditionInfo credit : baseInfo.getConditions()) {
				if (StringUtil.equals(credit.getType(), CreditConditionTypeEnum.PLEDGE.code())) {
					countered.add(CollectCodeEnum.PLEDGE.code());
				} else if (StringUtil.equals(credit.getType(),
					CreditConditionTypeEnum.MORTGAGE.code())) {
					countered.add(CollectCodeEnum.MORTGAGE.code());
				} else if (StringUtil.equals(credit.getType(),
					CreditConditionTypeEnum.GUARANTOR.code())) {
					if (null != credit.getItemDesc()) {
						if (credit.getItemDesc().contains("法人连带")
							|| credit.getItemDesc().contains("法人一般")) {
							countered.add(CollectCodeEnum.LEGAL_GUARANTEE.code());
						} else if (credit.getItemDesc().contains("个人连带")
									|| credit.getItemDesc().contains("个人一般")) {
							countered.add(CollectCodeEnum.PERSONAL_GUARANTEE.code());
						}
					}
				}
			}
			model.addAttribute("countered", new ArrayList<>(countered));
		}
	}
	
	@RequestMapping("editBaseInfo.htm")
	public String editBaseInfo(HttpServletRequest request, Model model, long formId) {
		CheckReportEditionEnums edition = CheckReportEditionEnums.V_COMMON;
		FAfterwardsCheckInfo afterCheckInfo = afterwardsCheckServiceClient.findByFormId(formId);
		
		model.addAttribute("afterCheckInfo", afterCheckInfo);
		String projectCode = afterCheckInfo.getProjectCode();
		edition = afterCheckInfo.getEdition();
		
		FAfterwardsCheckBaseInfo baseInfo = afterwardsCheckServiceClient
			.findBaseInfoByFormId(formId);
		if (null == baseInfo) {
			baseInfo = new FAfterwardsCheckBaseInfo();
			baseInfo.setFormId(afterCheckInfo.getFormId());
			model.addAttribute("counters", CollectCodeEnum.getCounterGuaranteeEnum());
			model.addAttribute("collections", CollectCodeEnum.getCollectionEnum());
			
		} else {
			model.addAttribute("baseInfo", baseInfo);
			//附件列表
			queryCommonAttachmentData(model, baseInfo.getBaseId() + "",
				CommonAttachmentTypeEnum.CUSTOMER_OPINION);
			queryCommonAttachmentData(model, baseInfo.getBaseId() + "",
				CommonAttachmentTypeEnum.AFTER_DATA_COLLECT);
			queryCommonAttachmentData(model, baseInfo.getBaseId() + "",
				CommonAttachmentTypeEnum.AFTER_REPORT);
		}
		
		//查询项目相关信息
		queryProjectInfo(projectCode, model, baseInfo);
		//授信落实条件相关
		processCountered(projectCode, model, baseInfo);
		
		//添加客户信息
		addCustomerInfo(formId, afterCheckInfo.getCustomerId(), model);
		
		FormInfo form = formServiceClient.findByFormId(formId);
		model.addAttribute("formInfo", form);
		model.addAttribute("form", form);
		
		//生成最近的5年年份
		model.addAttribute("years", getYears(5));
		
		FAfterwardsCheckReportContentInfo contentInfo = afterwardsCheckServiceClient
			.findContentByFomrId(formId);
		if (null != contentInfo && contentInfo.getContentId() > 0) {
			queryCommonAttachmentData(model, contentInfo.getContentId() + "",
				CommonAttachmentTypeEnum.CONTENT_ATTACHMENT);
			queryCommonAttachmentData(model, contentInfo.getContentId() + "",
				CommonAttachmentTypeEnum.TAX_CERTIFICATE);
			queryCommonAttachmentData(model, contentInfo.getContentId() + "",
				CommonAttachmentTypeEnum.BANK_STATEMENT);
			addKpiClass(contentInfo);
			
		}
		model.addAttribute("contentInfo", contentInfo);
		
		//获取财务报表模板
		getFinancialTemplate(request, projectCode, model);
		
		caculateLast3Month(model, afterCheckInfo.getCheckDate());
		
		String downLoadType = EXCEL_BASE;
		FProjectCustomerBaseInfo customerBaseInfo = projectSetupServiceClient
			.queryCustomerBaseInfoByProjectCode(projectCode);
		if (null != customerBaseInfo) {
			if (StringUtil.isNotBlank(customerBaseInfo.getIndustryCode())
				&& customerBaseInfo.getIndustryCode().startsWith("J")) {
				downLoadType = EXCEL_BASE;
			}
		}
		model.addAttribute("downLoadType", downLoadType);
		
		if (null != afterCheckInfo) {
			CustomerDetailInfo customerInfo = customerServiceClient.queryByUserId(afterCheckInfo
				.getCustomerId());
			model.addAttribute("customerInfo", customerInfo);
		}
		
		String suffix = VM_SUFFIX.get(edition.code());
		return VM_PATH + "editReport" + suffix + ".vm";
	}
	
	private boolean showCustomer() {
		return true;
	}
	
	private void addCustomerInfo(long formId, long customerId, Model model) {
		model.addAttribute("showCustomer", showCustomer());
		
		if (formId > 0) {
			FAfterwardsCustomerInfo customerFormInfo = afterwardsCheckServiceClient
				.findCustomerByFomrId(formId);
			model.addAttribute("customerFormInfo", customerFormInfo);
		}
		
		CustomerDetailInfo info = customerServiceClient.queryByUserId(customerId);
		if (info != null) {
			//签报允许修改
			info.setProjectStatus(BooleanEnum.NO.code());
			model.addAttribute("customerBaseInfo", info);
			if (StringUtil.isNotBlank(info.getChannalType())) {
				ChannalQueryOrder queryChannalOrder = new ChannalQueryOrder();
				queryChannalOrder.setChannelType(info.getChannalType());
				queryChannalOrder.setStatus("on");
				QueryBaseBatchResult<ChannalInfo> channalList = channalClient
					.list(queryChannalOrder);
				model.addAttribute("channalList", channalList);
			}
			
			if (ListUtil.isNotEmpty(info.getReqList())) {
				model.addAttribute("companyInfos", info.getReqList());
			}
		}
		setCustomerEnums(model);
		model.addAttribute("changeType", ChangeTypeEnum.BH);
	}
	
	private void addKpiClass(FAfterwardsCheckReportContentInfo info) {
		if (null != info) {
			addKpiClass(info.getFinancials());
			addKpiClass(info.getProfits());
			addKpiClass(info.getFlows());
		}
	}
	
	private void addKpiClass(List<FinancialKpiInfo> kpiInfoes) {
		if (ListUtil.isNotEmpty(kpiInfoes)) {
			for (FinancialKpiInfo info : kpiInfoes) {
				if (StringUtil.isNotBlank(info.getKpiName())) {
					String kpiClass = DataFinancialHelper.getKpiClass(info.getKpiName().trim());
					info.setKpiClass(kpiClass);
				}
			}
		}
	}
	
	private void queryProjectInfo(String projectCode, Model model, FAfterwardsCheckBaseInfo baseInfo) {
		if (null == baseInfo) {
			baseInfo = new FAfterwardsCheckBaseInfo();
		}
		ProjectInfo project = projectServiceClient.queryByCode(projectCode, false);
		if (null != project) {
			baseInfo.setProjectCode(projectCode);
			baseInfo.setProjectName(project.getProjectName());
			baseInfo.setCustomerId(project.getCustomerId());
			baseInfo.setCustomerName(project.getCustomerName());
			baseInfo.setBusiType(project.getBusiType());
			baseInfo.setBusiTypeName(project.getBusiTypeName());
			baseInfo.setAmount(project.getAmount());
			baseInfo.setStartTime(project.getStartTime());
			baseInfo.setEndTime(project.getEndTime());
			baseInfo.setBusiManagerName(project.getBusiManagerName());
			baseInfo.setLoanPurpose(project.getLoanPurpose());
			baseInfo.setTimeLimit(project.getTimeLimit());
			baseInfo.setTimeUnit(project.getTimeUnit());
			baseInfo.setLoanedAmount(project.getLoanedAmount());
			//已解保金额+已代偿本金
			baseInfo.setRepayedAmount(project.getReleasedAmount().add(
				project.getCompPrincipalAmount()));
		}
		
		//授信机构
		String guaranteeInstitution = "";
		if (ProjectUtil.isBond(project.getBusiType())) {
			FCouncilSummaryProjectBondInfo giInfo = councilSummaryServiceClient
				.queryBondProjectCsByProjectCode(projectCode, false);
			if (null != giInfo) {
				guaranteeInstitution = giInfo.getCapitalChannelName();
			}
		} else if (ProjectUtil.isEntrusted(project.getBusiType())) {
			FCouncilSummaryProjectEntrustedInfo giInfo = councilSummaryServiceClient
				.queryEntrustedProjectCsByProjectCode(projectCode, false);
			if (null != giInfo) {
				guaranteeInstitution = giInfo.getCapitalChannelName();
			}
		} else if (ProjectUtil.isGuarantee(project.getBusiType())) {
			FCouncilSummaryProjectGuaranteeInfo giInfo = councilSummaryServiceClient
				.queryGuaranteeProjectCsByProjectCode(projectCode, false);
			if (null != giInfo) {
				guaranteeInstitution = giInfo.getCapitalChannelName();
			}
		}
		baseInfo.setCapitalChannelName(guaranteeInstitution);
		
		FCouncilSummaryProjectInfo summary = councilSummaryServiceClient
			.queryProjectCsByProjectCode(projectCode);
		if (null != summary) {
			baseInfo.setAmount(summary.getAmount()); //授信金额：取自会议纪要
			baseInfo.setLoanPurpose(summary.getLoanPurpose());
		}
		
		Money availableAmount = new Money(0L);
		//		在保金额：放款金额累计-解保金额累计
		//		Money releaseBalance = project.getLoanedAmount().subtract(project.getReleasedAmount());
		//		if (ProjectUtil.isBond(project.getBusiType())) {
		//			//发债类 已发售-已解保
		//			releaseBalance = project.getAccumulatedIssueAmount().subtract(project.getReleasedAmount());
		//		}
		
		// 分保情况下
		//其他项目的可用款金额采用的是合同金额撒，
		//分保的情况下，是采用的分保金额作为授信的总金额，然后减去用款的金额。
		if (project.getReinsuranceAmount().greaterThan(Money.zero())) {
			availableAmount = project.getReinsuranceAmount().subtract(project.getUsedAmount());
		} else if (BooleanEnum.IS == project.getIsMaximumAmount()) {
			//			availableAmount = project.getAmount().subtract(releaseBalance);
			availableAmount = project.getAmount().subtract(project.getBalance());
		} else {
			availableAmount = project.getAmount().subtract(project.getLoanedAmount());
		}
		
		baseInfo.setAvailableAmount(availableAmount);
		baseInfo.setReleaseBalance(project.getBalance());
		//		baseInfo.setReleaseBalance(releaseBalance);
		//		授信金额：取自会议纪要
		//		累计使用金额：放款金额累计
		//		累计还款金额：解保金额累计
		//		可用金额：（最高授信）授信额度-在保金额   （else）授信额度-累计使用金额
		//		在保金额：放款金额累计-解保金额累计
		//累计还款金额：（担保）解保金额累计；（委贷）收款通知单中委贷本金收回累计
		ProjectRelatedUserInfo riskManager = projectRelatedUserServiceClient
			.getOrignalRiskManager(projectCode);
		if (null != riskManager) {
			baseInfo.setRiskManagerName(riskManager.getUserName());
		}
		
		//担保费
		Money guaranteeAmount = chargeNotificationServiceClient.queryChargeTotalAmount(projectCode,
			FeeTypeEnum.GUARANTEE_FEE).getOther();
		baseInfo.setGuaranteeAmount(guaranteeAmount);
		//存入保证金
		Money guaranteeDeposit = chargeNotificationServiceClient.queryChargeTotalAmount(
			projectCode, FeeTypeEnum.GUARANTEE_DEPOSIT).getOther();
		baseInfo.setGuaranteeDeposit(guaranteeDeposit);
		
		model.addAttribute("baseInfo", baseInfo);
	}
	
	private void caculateLast3Month(Model model, Date checkDate) {
		if (null == checkDate) {
			return;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(checkDate);
		int day = c.get(Calendar.DAY_OF_MONTH);
		int month = c.get(Calendar.MONTH);
		
		if (day <= 15) {
			c.set(Calendar.MONTH, month - 2);
		} else {
			c.set(Calendar.MONTH, month - 1);
		}
		model.addAttribute("data1", DateUtil.dtSimpleYmFormat(c.getTime()));
		month = c.get(Calendar.MONTH);
		c.set(Calendar.MONTH, month - 1);
		model.addAttribute("data2", DateUtil.dtSimpleYmFormat(c.getTime()));
		month = c.get(Calendar.MONTH);
		c.set(Calendar.MONTH, month - 1);
		model.addAttribute("data3", DateUtil.dtSimpleYmFormat(c.getTime()));
	}
	
	private String getDownloadType(String projectCode) {
		String downLoadType = EXCEL_BASE;
		FProjectCustomerBaseInfo customerBaseInfo = projectSetupServiceClient
			.queryCustomerBaseInfoByProjectCode(projectCode);
		if (null != customerBaseInfo) {
			if (StringUtil.isNotBlank(customerBaseInfo.getIndustryCode())
				&& customerBaseInfo.getIndustryCode().startsWith("J")) {
				downLoadType = EXCEL_BASE;
			}
		}
		
		return downLoadType;
	}
	
	private String getFileName(String type) {
		String fileName = "";
		if (EXCEL_BASE.equals(type) || EXCEL_BASE_O.equals(type) || EXCEL_BASE_J.equals(type) || EXCEL_CONTENT_CREDIT.equals(type)) {
			fileName = "一般企业企业报表标准格式-模板--保后";
		} 
		
		if (StringUtil.isNotBlank(fileName) && !type.endsWith("_f")) {
			Date now = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(now);
			int year = c.get(Calendar.YEAR);
			
			//动态的，就是需要在表格名称后面添加_$year
			fileName = fileName + "_" + year;
			if (EXCEL_BASE_O.equals(type)) {
				fileName = fileName + "_old";
			}
			
		}
		
		return fileName;
	}
	
	private String getFilePath(HttpServletRequest request, String fileName) {
		String filePath = request.getServletContext().getRealPath("/WEB-INF/") + "/xsl/excelModel/"
							+ fileName + ".xls";
		return filePath;
	}
	
	private DataFinancial getDataFinancialByPath(String filePath) throws BiffException, IOException {
		Workbook workbook = Workbook.getWorkbook(new File(filePath));
		DataFinancial dataFinancial = new DataFinancial();
		Sheet balanceSheet = workbook.getSheet(0);
		dataFinancial.setBalance(DataFinancialHelper.installDataSheet(balanceSheet, 4));
		Sheet profitSheet = workbook.getSheet(1);
		dataFinancial.setProfit(DataFinancialHelper.installDataSheet(profitSheet, 4));
		Sheet cashFlowSheet = workbook.getSheet(2);
		dataFinancial.setCashFlow(DataFinancialHelper.installDataSheet(cashFlowSheet, 4));
		return dataFinancial;
	}
	
	private static final String FINANCIALS = "financials";
	private static final String PROFITS = "profits";
	private static final String FLOWS = "flows";
	
	private JSONObject findFinancialDatas(HttpServletRequest request, String type,
											String projectCode) {
		try {
			KpiBean kpiBean = queryInvestigationFinancialData(projectCode);
			List<String> rowNameList = kpiBean.getColSortList().get(FINANCIALS);
			List<String> rowNameList1=	kpiBean.getColSortList().get(PROFITS);
			List<String> rowNameList2=	kpiBean.getColSortList().get(FLOWS);
			if (rowNameList.size() == 79 && rowNameList1.size() == 33 && rowNameList2.size() == 35) {
				type = "acr_b_o";
			}
				
			String fileName = getFileName(type);
			String filePath = getFilePath(request, fileName);
			DataFinancial dataFinancial = getDataFinancialByPath(filePath);
			//KpiBean kpiBean = queryInvestigationFinancialData(projectCode);
			Map<String, Map<String, String>> map = kpiBean.getMap();
			//			Map<String, Map<String, String>> map2 = queryHistoryFinancialData(projectCode);
			JSONObject json = new JSONObject();
			json.put(
				FINANCIALS,
				convertToKpi("balance", dataFinancial.getBalance(),
					kpiBean.getColSortList().get(FINANCIALS), map.get(FINANCIALS), null));
			//			convertToKpi(dataFinancial.getBalance(), map.get(FINANCIALS), map2.get(FINANCIALS)));
			json.put(
				PROFITS,
				convertToKpi("profit", dataFinancial.getProfit(),
					kpiBean.getColSortList().get(PROFITS), map.get(PROFITS), null));
			//			convertToKpi(dataFinancial.getProfit(), map.get(PROFITS), map2.get(PROFITS)));
			json.put(
				FLOWS,
				convertToKpi("cashflow", dataFinancial.getCashFlow(),
					kpiBean.getColSortList().get(FLOWS), map.get(FLOWS), null));
			//			convertToKpi(dataFinancial.getCashFlow(), map.get(FLOWS), map2.get(FLOWS)));
			//			if (map2.size() > 0) {
			//				json.put("skip", BooleanEnum.YES.code());
			//			}
			return json;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	private List<FinancialKpiInfo> convertToKpi(String codeAffix, DataSheet sheet,
												List<String> rowNameList, Map<String, String> map,
												Map<String, String> map2) {
		if (null == sheet) {
			return null;
		}
		
		List<FinancialKpiInfo> kpies = new ArrayList<>();
		FinancialKpiInfo kpi = convertDataRowToKpi(sheet.getHeader());
		
		if (rowNameList == null)
			return kpies;
		
		for (int i = 0; i < rowNameList.size(); i++) {
			String colName = rowNameList.get(i);
			boolean isCreate = false;
			if (i < sheet.getDataRows().size()) {
				
				for (int j = 1; j < sheet.getDataRows().size(); j++) {
					DataRow row = sheet.getDataRows().get(j);
					if (StringUtil.equals(colName,
						StringUtil.trim(sheet.getDataRows().get(j).getColumnName()))) {
						kpi = convertDataRowToKpi(row);
						isCreate = true;
						break;
					}
				}
				if (!isCreate) {
					kpi = new FinancialKpiInfo();
					kpi.setKpiName(colName);
					kpi.setKpiCode(codeAffix + "_" + BusinessNumberUtil.gainNumber());
				}
			}
			if (null != kpi) {
				String value2 = "";
				if (null != map2) {
					value2 = map2.get(DataFinancialHelper.NAMES_KEY.get(kpi.getKpiName().trim()));
				}
				if (StringUtil.isNotBlank(value2)) {
					kpi.setKpiValue2(value2);
				}
				
				String value3 = "";
				if (null != map) {
					value3 = map.get(colName);
				}
				if (StringUtil.isNotBlank(value3)) {
					kpi.setKpiValue3(value3);
				}
				kpi.setKpiValue1("");
				kpi.setKpiValue2("");
				kpies.add(kpi);
			}
		}
		
		return kpies;
	}
	
	private FinancialKpiInfo convertDataRowToKpi(DataRow row) {
		if (null == row) {
			return null;
		}
		
		FinancialKpiInfo kpi = new FinancialKpiInfo();
		kpi.setKpiName(row.getColumnName());
		kpi.setKpiCode(row.getColumnCode());
		kpi.setKpiClass(row.getRowClass());
		
		String[] datas = row.getDatas();
		if (null != datas) {
			int len = datas.length;
			if (len >= 1) {
				kpi.setKpiValue1(datas[0]);
			}
			if (len >= 2) {
				kpi.setKpiValue2(datas[1]);
			}
			if (len >= 3) {
				kpi.setKpiValue3(datas[2]);
			}
		}
		
		return kpi;
	}
	
	/**
	 * 获取财务报表模板
	 * 
	 * @param projectCode
	 * @param request
	 * @param model
	 */
	private void getFinancialTemplate(HttpServletRequest request, String projectCode, Model model) {
		String type = getDownloadType(projectCode);
		
		JSONObject json = findFinancialDatas(request, type, projectCode);
		if (null != json) {
			for (Object obj : json.keySet()) {
				model.addAttribute(obj.toString(), json.get(obj));
			}
		}
		int financials_count = ((List<FinancialKpiInfo>) json.get(FINANCIALS)).size();
		int profits_count = ((List<FinancialKpiInfo>) json.get(PROFITS)).size();
		int flows_count = ((List<FinancialKpiInfo>) json.get(FLOWS)).size();
		model.addAttribute("financials_count", financials_count);
		model.addAttribute("profits_count", profits_count);
		model.addAttribute("flows_count", flows_count);
		if (financials_count == 79 && profits_count == 33 && flows_count == 35) {
			model.addAttribute("oldTemplateType", "acr_b_o");
		}
		model.addAttribute("templateType", "acr_b");
	}
	
	@RequestMapping("downloadModel.htm")
	public void downloadModel(HttpServletRequest request, HttpServletResponse response, String type) {
		BufferedOutputStream output = null;
		BufferedInputStream input = null;
		
		try {
			String fileName = getFileName(type);
			if (StringUtil.isNotBlank(fileName)) {
				String filePath = getFilePath(request, fileName);
				File file = new File(filePath);
				response.setHeader("Content-disposition",
					"attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1")
							+ ".xls");
				response.setContentType("application/msexcel");
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
			} else {
				if ("customer_opinion".equals(type)) {
					fileName = "《授信客户意见书》模板";
					String filePath = request.getServletContext().getRealPath("/WEB-INF/")
										+ "/xsl/excelModel/" + fileName + ".docx";
					File file = new File(filePath);
					response.setHeader("Content-disposition",
						"attachment; filename="
								+ new String(fileName.getBytes("GB2312"), "ISO8859-1") + ".docx");
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
				}
			}
		} catch (Exception e) {
			//异常自己捕捉
			logger.error("读取excel模板异常：" + e);
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
	
	//上传excel数据
	@RequestMapping("uploadFinancial.htm")
	public void uploadFinancial(HttpServletRequest request, String type,
								HttpServletResponse response) {
		JSONObject json = new JSONObject();
		try {
			DataFinancial df = installDataFinancial(request);
			if (null != df) {
				if (df.getBalance().getDataRows().size() == NumberUtil.parseInt(request
					.getParameter("financials_count"))
					&& df.getProfit().getDataRows().size() == NumberUtil.parseInt(request
						.getParameter("profits_count"))
					&& df.getCashFlow().getDataRows().size() == NumberUtil.parseInt(request
						.getParameter("flows_count"))) {
					JSONObject data = DataFinancialHelper.parseToJson(df, type);
					if (null != data) {
						json.put("success", true);
						json.put("message", "上传成功");
						json.put("datas", data);
						returnJson(response, true, json);
						return;
					}
				} else {
					json.put("success", false);
					json.put("message", "上传失败，财务报表行数和上一期财务报表行数不一致，请调整");
					returnJson(response, true, json);
					return;
				}
				
			}
			
			json.put("success", false);
			json.put("message", "上传失败");
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
	
	private DataFinancial installDataFinancial(HttpServletRequest request) throws IOException {
		ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
		fileUpload.setHeaderEncoding("utf-8");
		List<FileItem> fileList = null;
		try {
			fileList = fileUpload.parseRequest(request);
		} catch (FileUploadException ex) {
			logger.error(ex.getMessage(), ex);
			return null;
		}
		Iterator<FileItem> it = fileList.iterator();
		InputStream is = null;
		while (it.hasNext()) {
			FileItem item = it.next();
			if (!item.isFormField()) {
				is = item.getInputStream();
				break;
			}
		}
		
		if (null == is) {
			logger.error("财务报表解析失败：未找到上传的文件流");
			return null;
		}
		
		try {
			DataFinancial dataFinancial = new DataFinancial();
			Workbook model = Workbook.getWorkbook(is);
			Sheet balanceSheet = model.getSheet(0);
			dataFinancial.setBalance(DataFinancialHelper.installDataSheet(balanceSheet, 4));
			Sheet profitSheet = model.getSheet(1);
			dataFinancial.setProfit(DataFinancialHelper.installDataSheet(profitSheet, 4));
			Sheet cashFlowSheet = model.getSheet(2);
			dataFinancial.setCashFlow(DataFinancialHelper.installDataSheet(cashFlowSheet, 4));
			
			return dataFinancial;
		} catch (BiffException | IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	@RequestMapping("saveBaseInfo.json")
	@ResponseBody
	public JSONObject saveBaseInfo(HttpServletRequest request, FAfterwardsCheckBaseOrder order,
									Model model) {
		String tipPrefix = " 保后检查-保存项目基本情况";
		JSONObject json = new JSONObject();
		try {
			order.setFormCode(FormCodeEnum.AFTERWARDS_CHECK_GUARANTEE);
			order.setCheckIndex(1);
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			FcsBaseResult result = afterwardsCheckServiceClient.saveBaseInfo(order);
			if (result.isSuccess()) {
				long keyId = result.getKeyId();
				//添加附件
				addAttachfile(keyId + "", request, order.getProjectCode(), null,
					CommonAttachmentTypeEnum.CUSTOMER_OPINION);
				addAttachfile(keyId + "", request, order.getProjectCode(), null,
					CommonAttachmentTypeEnum.AFTER_DATA_COLLECT);
				addAttachment(request, keyId + "", "", CommonAttachmentTypeEnum.AFTER_REPORT);
			}
			
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			json = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		return json;
	}
	
	@RequestMapping(value = "saveContent.json")
	@ResponseBody
	public JSONObject saveContent(HttpServletRequest request, HttpServletResponse response,
									FAfterwardsCheckReportContentOrder order, Model model) {
		//		FAfterwardsCheckReportContentOrder order = new FAfterwardsCheckReportContentOrder();
		//		WebUtil.setPoPropertyByRequest(order, request);
		String tipPrefix = " 保后检查-保存监管内容";
		JSONObject json = new JSONObject();
		try {
			order.setFormCode(FormCodeEnum.AFTERWARDS_CHECK_GUARANTEE);
			order.setCheckIndex(2);
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			FormBaseResult result = afterwardsCheckServiceClient.saveContent(order);
			FAfterwardsCheckInfo afterCheckInfo = null;
			if (result.isSuccess()) {
				afterCheckInfo = afterwardsCheckServiceClient.findByFormId(order.getFormId());
				long keyId = result.getKeyId();
				//添加附件
				addAttachfile(keyId + "", request, afterCheckInfo == null ? order.getProjectCode()
					: afterCheckInfo.getProjectCode(), null,
					CommonAttachmentTypeEnum.CONTENT_ATTACHMENT);
				addAttachfile(keyId + "", request, afterCheckInfo == null ? order.getProjectCode()
					: afterCheckInfo.getProjectCode(), null,
					CommonAttachmentTypeEnum.TAX_CERTIFICATE);
				addAttachfile(keyId + "", request, afterCheckInfo == null ? order.getProjectCode()
					: afterCheckInfo.getProjectCode(), null,
					CommonAttachmentTypeEnum.BANK_STATEMENT);
				
				//保存在建项目附件
				saveProjectingAttachment(order,
					afterCheckInfo == null ? null : afterCheckInfo.getEdition());
			}
			
			json = toJSONResult(result, tipPrefix);
			
		} catch (Exception e) {
			json = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		return json;
	}
	
	/** 保存在建项目附件 */
	private void saveProjectingAttachment(FAfterwardsCheckReportContentOrder order,
											CheckReportEditionEnums edition) {
		if (edition != CheckReportEditionEnums.V_REAL_ESTATE) {
			return;
		}
		//先移除所有此类型的附件，再添加
		commonAttachmentServiceClient.deleteByBizNoModuleType(order.getFormId() + "",
			CommonAttachmentTypeEnum.AFTER_REAL_ESTATE_PROJECTING);
		
		if (null == order || ListUtil.isEmpty(order.getProjects())) {
			return;
		}
		
		for (FAfterwardsCheckReportProjectSimpleOrder projecting : order.getProjects()) {
			if ("PROJECTING".equals(projecting.getProjectType())) {
				AttachmentBean attachment = new AttachmentBean();
				attachment.setBizNo(order.getFormId() + "");
				attachment.setChildId(projecting.getId() + "");
				attachment.setPathValues(projecting.getAttachmentInfo());
				attachment.setRemark(CommonAttachmentTypeEnum.AFTER_REAL_ESTATE_PROJECTING
					.message());
				attachment.addTypes(CommonAttachmentTypeEnum.AFTER_REAL_ESTATE_PROJECTING);
				addAttachfile(attachment);
			}
		}
	}
	
	/**
	 * 查询尽职调查报告中的财务报表数据
	 * 
	 * @param projectCode
	 */
	private KpiBean queryInvestigationFinancialData(String projectCode) {
		KpiBean kpiBean = new KpiBean();
		Map<String, Map<String, String>> map = new HashMap<>();
		kpiBean.setMap(map);
		InvestigationQueryOrder queryOrder = new InvestigationQueryOrder();
		queryOrder.setProjectCode(projectCode);
		queryOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
		queryOrder.setPageSize(1L);
		queryOrder.setPageNumber(1L);
		QueryBaseBatchResult<InvestigationInfo> batchResult = investigationServiceClient
			.queryInvestigation(queryOrder);
		
		//		FcsBaseResult result = new FcsBaseResult();
		if (ListUtil.isEmpty(batchResult.getPageList())) {
			return kpiBean;
			//			result.setSuccess(false);
			//			result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
			//			result.setMessage("尽调预设:没有审核通过的尽职调查报告");
			//			return result;
		}
		
		//审核通过后的尽调表单formId
		SimpleFormProjectVOInfo info = batchResult.getPageList().get(0);
		long oldFormId = info.getFormId();
		FInvestigationFinancialReviewInfo financialReviewInfo = investigationServiceClient
			.findFInvestigationFinancialReviewByFormId(oldFormId);
		if (null == financialReviewInfo) {
			return kpiBean;
		} else {
			List<String> rowNameList = new ArrayList<String>();
			kpiBean.getColSortList().put(FINANCIALS, rowNameList);
			map.put(
				FINANCIALS,
				convertKpi(financialReviewInfo.getFinancialHeader(),
					financialReviewInfo.getFinancialTables(), rowNameList));
			List<String> rowNameList1 = new ArrayList<String>();
			kpiBean.getColSortList().put(PROFITS, rowNameList1);
			map.put(
				PROFITS,
				convertKpi(financialReviewInfo.getProfitHeader(),
					financialReviewInfo.getProfitTables(), rowNameList1));
			List<String> rowNameList2 = new ArrayList<String>();
			kpiBean.getColSortList().put(FLOWS, rowNameList2);
			map.put(
				FLOWS,
				convertKpi(financialReviewInfo.getFlowHeader(),
					financialReviewInfo.getFlowTables(), rowNameList2));
			return kpiBean;
		}
	}
	
	private Map<String, String> convertKpi(FinancialReviewKpiInfo header,
											List<FinancialReviewKpiInfo> kpies,
											List<String> rowNameList) {
		if (ListUtil.isEmpty(kpies)) {
			return null;
		}
		
		Map<String, String> map = new HashMap<>();
		if (null != header) {
			map.put("header", header.getKpiValue());
		}
		
		for (FinancialReviewKpiInfo kpi : kpies) {
			if (!StringUtils.isBlank(kpi.getKpiName())) {
				map.put(kpi.getKpiName(), kpi.getKpiValue());
				rowNameList.add(kpi.getKpiName());
			}
		}
		return map;
	}
	
	/**
	 * 
	 * 查询上一期保后检查报告的财务数据
	 * 
	 * @param projectCode
	 * @return
	 */
	private Map<String, Map<String, String>> queryHistoryFinancialData(String projectCode) {
		Map<String, Map<String, String>> map = new HashMap<>();
		FAfterwardsCheckInfo check = queryTheFirstEditionOfThisYear(projectCode);
		if (null == check) {
			return map;
		}
		
		FAfterwardsCheckReportContentInfo content = afterwardsCheckServiceClient
			.findContentByFomrId(check.getFormId());
		if (null == content) {
			return map;
		}
		
		map.put(FINANCIALS, convertFinancial(content.getFinancials()));
		map.put(PROFITS, convertFinancial(content.getProfits()));
		map.put(FLOWS, convertFinancial(content.getFlows()));
		
		return map;
	}
	
	private FAfterwardsCheckInfo queryTheFirstEditionOfThisYear(String projectCode) {
		FAfterwardsCheckQueryOrder queryOrder = new FAfterwardsCheckQueryOrder();
		queryOrder.setProjectCode(projectCode);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		queryOrder.setRoundYear(c.get(Calendar.YEAR));
		queryOrder.setSortCol("ac.round_time");
		queryOrder.setSortOrder("ASC");
		queryOrder.setPageNumber(1L);
		queryOrder.setPageSize(1L);
		QueryBaseBatchResult<FAfterwardsCheckInfo> batchResult = afterwardsCheckServiceClient
			.queryAfterwardsCheckReport(queryOrder);
		if (ListUtil.isNotEmpty(batchResult.getPageList())) {
			return batchResult.getPageList().get(0);
		}
		
		return null;
	}
	
	private Map<String, String> convertFinancial(List<FinancialKpiInfo> kpies) {
		Map<String, String> map = new HashMap<>();
		if (ListUtil.isNotEmpty(kpies)) {
			for (FinancialKpiInfo kpi : kpies) {
				if (StringUtil.isBlank(kpi.getKpiCode())) {
					map.put("header", kpi.getKpiValue2());
				} else {
					map.put(kpi.getKpiCode(), kpi.getKpiValue2());
				}
			}
		}
		return map;
	}
	
	@RequestMapping("editProject.htm")
	public String editProject(	HttpServletRequest request,
								Model model,
								@RequestParam(value = "id", required = true) long id,
								@RequestParam(value = "projectCode", required = true) String projectCode) {
		FAfterwardsCheckReportProjectOrder order = new FAfterwardsCheckReportProjectOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		order.setId(id);
		FAfterwardsCheckReportProjectInfo project = afterwardsCheckServiceClient
			.findContentProjectById(id);
		if (null == project) {
			project = new FAfterwardsCheckReportProjectInfo();
		}
		
		if (StringUtil.isNotBlank(order.getProjectName())) {
			project.setProjectName(order.getProjectName());
		}
		if (StringUtil.isNotBlank(order.getProjectType())) {
			project.setProjectType(order.getProjectType());
		}
		model.addAttribute("project", project);
		
		FAfterwardsCheckQueryOrder queryOrder = new FAfterwardsCheckQueryOrder();
		queryOrder.setProjectCode(projectCode);
		//		queryOrder.setEdition(edition.code());
		FAfterwardsCheckInfo check = afterwardsCheckServiceClient.queryLastEdition(queryOrder);
		model.addAttribute("isNew", (null == check));
		
		return VM_PATH + "realEstateDetail.vm";
	}
	
	@RequestMapping("saveProject.json")
	@ResponseBody
	public JSONObject saveProject(FAfterwardsCheckReportProjectOrder order) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		
		String tipPrefix = "保存保后检查报告项目完成情况";
		try {
			FcsBaseResult result = afterwardsCheckServiceClient.saveContentProject(order);
			json = toJSONResult(result, tipPrefix);
			FAfterwardsCheckInfo check = afterwardsCheckServiceClient.findByFormId(order
				.getFormId());
			StringBuilder sb = new StringBuilder();
			sb.append("/projectMg/afterwardsCheck/editProject.htm?id=").append(result.getKeyId())
				.append("&projectCode=").append(check.getProjectCode());
			json.put("url", sb.toString());
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	@RequestMapping(value = "checkContentProject.json")
	@ResponseBody
	public JSONObject checkContentProject(HttpServletRequest request, HttpServletResponse response,
											long formId) {
		String tipPrefix = " 保后检查-验证开发项目完成情况";
		JSONObject json = new JSONObject();
		try {
			FcsBaseResult result = afterwardsCheckServiceClient.checkContentProject(formId);
			json = toJSONResult(result, tipPrefix);
			if (!result.isSuccess()) {
				json.put("ids", result.getUrl());
			}
			
		} catch (Exception e) {
			json = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return json;
	}
	
	@RequestMapping("viewProject.htm")
	public String viewProject(Model model, @RequestParam(value = "id", required = true) long id) {
		
		FAfterwardsCheckReportProjectInfo project = afterwardsCheckServiceClient
			.findContentProjectById(id);
		model.addAttribute("project", project);
		
		return VM_PATH + "viewRealEstateDetail.vm";
	}
	
	@ResponseBody
	@RequestMapping("queryProjects.json")
	public Object queryProjects(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String tipPrefix = "查询项目信息";
		
		try {
			ProjectQueryOrder queryOrder = new ProjectQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal != null
				&& (DataPermissionUtil.isBusiManager() || (DataPermissionUtil.isLegalManager() && StringUtil
					.equals("my", request.getParameter("select"))))) {
				queryOrder.setBusiManagerId(sessionLocal.getUserId());
			}
			String phasesStrList = request.getParameter("phasesList");
			if (StringUtil.isNotBlank(phasesStrList)) {
				List<ProjectPhasesEnum> phasesList = new ArrayList<>();
				String[] phases = phasesStrList.split(",");
				for (String s : phases) {
					ProjectPhasesEnum e = ProjectPhasesEnum.getByCode(s);
					if (null != e) {
						phasesList.add(e);
					}
				}
				queryOrder.setPhasesList(phasesList);
			}
			
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			QueryBaseBatchResult<ProjectInfo> batchResult = afterwardsCheckServiceClient
				.queryProjects(queryOrder);
			
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (ProjectInfo info : batchResult.getPageList()) {
					JSONObject json = new JSONObject();
					json.put("projectCode", info.getProjectCode());
					json.put("projectName", info.getProjectName());
					json.put("customerId", info.getCustomerId());
					json.put("customerName", info.getCustomerName());
					json.put("amount", info.getAmount().toStandardString());
					//						NumberUtil.format(MoneyUtil.getMoneyByw(info.getAmount()), "0.00"));
					json.put("busiType", info.getBusiType());
					json.put("busiTypeName", info.getBusiTypeName());
					dataList.add(json);
				}
			}
			data.put("pageCount", batchResult.getPageCount());
			data.put("pageNumber", batchResult.getPageNumber());
			data.put("pageSize", batchResult.getPageSize());
			data.put("totalCount", batchResult.getTotalCount());
			data.put("pageList", dataList);
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		return result;
	}
	
	/**
	 * 
	 * 获取风险最近一次等级评定的表单formId
	 * 
	 * @param projectCode
	 * @return
	 */
	private long queryRiskLevelFormId(String projectCode) {
		long formId = 0L;
		RiskLevelQueryOrder queryOrder = new RiskLevelQueryOrder();
		queryOrder.setProjectCode(projectCode);
		queryOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
		queryOrder.setPageNumber(1L);
		queryOrder.setPageSize(1L);
		QueryBaseBatchResult<RiskLevelInfo> batchResult = riskLevelServiceClient
			.queryList(queryOrder);
		if (ListUtil.isNotEmpty(batchResult.getPageList())) {
			formId = batchResult.getPageList().get(0).getFormId();
		}
		return formId;
	}
	
	private long queryLastCheckReportFormId(String projectCode, long formId) {
		long fid = 0L;
		RiskLevelQueryOrder queryOrder = new RiskLevelQueryOrder();
		queryOrder.setProjectCode(projectCode);
		queryOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
		queryOrder.setPageNumber(1L);
		queryOrder.setPageSize(1L);
		QueryBaseBatchResult<RiskLevelInfo> batchResult = riskLevelServiceClient
			.queryList(queryOrder);
		if (ListUtil.isNotEmpty(batchResult.getPageList())) {
			fid = batchResult.getPageList().get(0).getFormId();
		}
		return fid;
	}
	
	@RequestMapping("editLitigation.htm")
	public String editLitigation(	Model model,
									@RequestParam(value = "formId", required = false,
											defaultValue = "0") long formId,
									@RequestParam(value = "projectCode", required = false) String projectCode) {
		FAfterwardsCheckLitigationInfo litigation = null;
		if (formId > 0) {
			FormInfo form = formServiceClient.findByFormId(formId);
			model.addAttribute("formInfo", form);
			model.addAttribute("form", form);
			FAfterwardsCheckInfo afterCheckInfo = afterwardsCheckServiceClient.findByFormId(formId);
			model.addAttribute("afterCheckInfo", afterCheckInfo);
			ProjectInfo project = projectServiceClient.queryByCode(afterCheckInfo.getProjectCode(),
				false);
			model.addAttribute("projectInfo", project);
			litigation = afterwardsCheckServiceClient.findLitigationByFormId(formId);
			
			queryCommonAttachmentData(model, formId + "", CommonAttachmentTypeEnum.LEGAL_DOCUMENTS);
			projectCode = afterCheckInfo.getProjectCode();
		} else {
			if (StringUtil.isNotBlank(projectCode)) {
				litigation = new FAfterwardsCheckLitigationInfo();
				litigation.setProjectCode(projectCode);
			}
		}
		
		if (null != litigation && StringUtil.isNotBlank(projectCode)) {
			ProjectInfo project = projectServiceClient.queryByCode(projectCode, true);
			if (null != project) {
				litigation.setProjectName(project.getProjectName());
				litigation.setCustomerId(project.getCustomerId());
				litigation.setCustomerName(project.getCustomerName());
				litigation.setAmount(project.getAmount());
				litigation.setBusiManagerName(project.getBusiManagerName());
				ProjectLgLitigationDetailInfo lgInfo = project.getLgLitigationDetailInfo();
				if (null != lgInfo) {
					litigation.setAssureObject(lgInfo.getAssureObject());
				}
			}
			
			FCouncilSummaryProjectLgLitigationInfo lg = councilSummaryServiceClient
				.queryLgLitigationProjectCsByProjectCode(projectCode, false);
			if (null != lg) {
				litigation.setGuaranteeFee(lg.getGuaranteeFee());
				litigation.setGuaranteeFeeType(lg.getGuaranteeFeeType());
				litigation.setCoInstitutionId(lg.getCoInstitutionId());
				litigation.setCoInstitutionName(lg.getCoInstitutionName());
				litigation.setCoInstitutionCharge(lg.getCoInstitutionCharge());
				litigation.setCoInstitutionChargeType(lg.getCoInstitutionChargeType());
			}
			
			FProjectLgLitigationInfo lg2 = projectSetupServiceClient
				.queryLgLitigationProjectByCode(projectCode);
			if (null != lg2) {
				litigation.setRiskManagerName(lg2.getLegalManagerName());
				//				litigation.setAssureObject(lg2.getAssureObject());
			}
		}
		
		model.addAttribute("litigation", litigation);
		model.addAttribute("caseStatusList", CaseStatusEnum.getAllEnum());
		
		if (null != litigation) {
			CustomerDetailInfo customerInfo = customerServiceClient.queryByUserId(litigation
				.getCustomerId());
			model.addAttribute("customerInfo", customerInfo);
			
			addCustomerInfo(formId, litigation.getCustomerId(), model);
		}
		
		return VM_PATH + "addSpect_DB_SS.vm";
	}
	
	@RequestMapping("saveLitigation.json")
	@ResponseBody
	public JSONObject saveLitigation(HttpServletRequest request,
										FAfterwardsCheckLitigationOrder order) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		
		String tipPrefix = "保存保后检查报告(诉讼保函)";
		try {
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			order.setFormCode(FormCodeEnum.AFTERWARDS_CHECK_LITIGATION);
			order.setCheckIndex(0);
			//			order.setCheckStatus(1);
			FormBaseResult result = afterwardsCheckServiceClient.saveLitigation(order);
			json = toJSONResult(result, tipPrefix);
			if (result.isSuccess()) {
				addAttachfile(result.getFormInfo().getFormId() + "", request,
					order.getProjectCode(), null, CommonAttachmentTypeEnum.LEGAL_DOCUMENTS);
			}
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	//审核
	@RequestMapping("auditLitigation.htm")
	public String auditLitigation(HttpServletRequest request, Model model, @RequestParam(
			value = "formId", required = true) long formId) {
		queryCommonLitigation(request, formId, model);
		return VM_PATH + "auditLitigation.vm";
	}
	
	//法务经理审核
	@RequestMapping("lawManagerAudit.htm")
	public String lawManagerAudit(HttpServletRequest request, Model model, @RequestParam(
			value = "formId", required = true) long formId) {
		queryCommonLitigation(request, formId, model);
		return VM_PATH + "commissionerAuditLitigation.vm";
	}
	
	@RequestMapping("viewLitigation.htm")
	public String viewLitigation(Model model, long formId) {
		queryCommonLitigation(null, formId, model);
		return VM_PATH + "viewReportLitigation.vm";
	}
	
	private void queryCommonLitigation(HttpServletRequest request, long formId, Model model) {
		FormInfo form = formServiceClient.findByFormId(formId);
		model.addAttribute("form", form);
		if (null != request) {
			model.addAttribute("formCode", form.getFormCode());
			initWorkflow(model, form, request.getParameter("taskId"));
		}
		FAfterwardsCheckInfo afterCheckInfo = afterwardsCheckServiceClient.findByFormId(formId);
		model.addAttribute("afterCheckInfo", afterCheckInfo);
		FAfterwardsCheckLitigationInfo litigation = afterwardsCheckServiceClient
			.findLitigationByFormId(formId);
		model.addAttribute("litigation", litigation);
		queryCommonAttachmentData(model, formId + "", CommonAttachmentTypeEnum.LEGAL_DOCUMENTS);
		
		if (null != afterCheckInfo) {
			//添加客户信息
			addCustomerInfo(formId, afterCheckInfo.getCustomerId(), model);
		}
	}
	
	public static void main(String[] args) {
		String str = "GUARANTEE_YEAR_END_EXPECT";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length();) {
			char c = str.charAt(i++);
			if (i == 1) {
				sb.append(c);
			} else if (c == '_') {
				c = str.charAt(i++);
				sb.append(c);
			} else {
				String C = c + "";
				sb.append(C.toLowerCase());
			}
		}
		System.out.println(sb.toString());
	}
	
	// 导出word
	@RequestMapping("toExportDoc.htm")
	public String toExportDoc(HttpServletRequest request, Model model, long formId) {
		FAfterwardsCheckInfo afterCheckInfo = afterwardsCheckServiceClient.findByFormId(formId);
		model.addAttribute("afterCheckInfo", afterCheckInfo);
		if (null == afterCheckInfo) {
			return VM_PATH + "exportError.vm";
		}
		FormInfo form = formServiceClient.findByFormId(formId);
		model.addAttribute("form", form);
		
		QueryBaseBatchResult<WorkflowProcessLog> workflows = workflowEngineWebClient
			.getProcessOpinionByActInstId(form.getActInstId() + "");
		model.addAttribute("workflows", workflows);
		
		ProjectInfo project = projectServiceClient.queryByCode(afterCheckInfo.getProjectCode(),
			false);
		if (ProjectUtil.isLitigation(project.getBusiType())) {
			FAfterwardsCheckLitigationInfo litigation = afterwardsCheckServiceClient
				.findLitigationByFormId(formId);
			model.addAttribute("litigation", litigation);
			return VM_PATH + "newViewReportLitigation.vm";
		} else {
			CheckReportEditionEnums edition = CheckReportEditionEnums.V_COMMON;
			
			FAfterwardsCheckBaseInfo baseInfo = afterwardsCheckServiceClient
				.findBaseInfoByFormId(formId);
			if (null == baseInfo) {
				baseInfo = new FAfterwardsCheckBaseInfo();
				baseInfo.setFormId(afterCheckInfo.getFormId());
				model.addAttribute("counters", CollectCodeEnum.getCounterGuaranteeEnum());
				model.addAttribute("collections", CollectCodeEnum.getCollectionEnum());
				
			} else {
				model.addAttribute("baseInfo", baseInfo);
				//附件列表
				queryCommonAttachmentData(model, baseInfo.getBaseId() + "",
					CommonAttachmentTypeEnum.CUSTOMER_OPINION);
				queryCommonAttachmentData(model, baseInfo.getBaseId() + "",
					CommonAttachmentTypeEnum.AFTER_DATA_COLLECT);
			}
			
			//查询项目相关信息
			model.addAttribute("baseInfo", baseInfo);
			queryProjectInfo(afterCheckInfo.getProjectCode(), model, baseInfo);
			edition = afterCheckInfo.getEdition();
			
			ProjectCreditConditionQueryOrder queryOrder = new ProjectCreditConditionQueryOrder();
			// 授信条件落实情况的显示 (条件：审核通过)
			//			queryOrder.setStatus(CreditCheckStatusEnum.CHECK_PASS.code());
			queryOrder.setIsConfirm(BooleanEnum.IS.code());
			queryOrder.setProjectCode(afterCheckInfo.getProjectCode());
			queryOrder.setPageNumber(1L);
			queryOrder.setPageSize(999L);
			QueryBaseBatchResult<ProjectCreditConditionInfo> batchResult = projectCreditConditionServiceClient
				.queryCreditCondition(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			
			FAfterwardsCheckReportContentInfo contentInfo = afterwardsCheckServiceClient
				.findContentByFomrId(formId);
			model.addAttribute("contentInfo", contentInfo);
			
			String suffix = VM_SUFFIX.get(edition.code());
			return VM_PATH + "newViewReport" + suffix + ".vm";
		}
		
	}
	
	@RequestMapping("saveCustomer.json")
	@ResponseBody
	public JSONObject saveCustomer(FAfterwardsCustomerOrder order) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		
		String tipPrefix = "保存保后检查报告客户信息";
		try {
			//			String pa = com.born.fcs.pm.util.StringUtil.decodeURI(order.getFormData());
			setSessionLocalInfo2Customer(order);
			FcsBaseResult result = afterwardsCheckServiceClient.saveCustomerInfo(order);
			json = toJSONResult(result, tipPrefix);
			
			//			saveCustomerInfo(order.getFormData());
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	private void setSessionLocalInfo2Customer(FAfterwardsCustomerOrder order) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			order.setUserId(sessionLocal.getUserId());
			order.setUserAccount(sessionLocal.getUserName());
			order.setUserName(sessionLocal.getRealName());
			order.setUserIp(sessionLocal.getRemoteAddr());
			
			UserDetailInfo userInfo = sessionLocal.getUserDetailInfo();
			if (userInfo != null) {
				//				order.setUserMobile(userInfo.getMobile());
				//				order.setUserEmail(userInfo.getEmail());
				Org dept = userInfo.getPrimaryOrg();
				if (dept != null) {
					order.setDeptId(dept.getId());
					//					order.setDeptCode(dept.getCode());
					order.setDeptName(dept.getName());
					//					order.setDeptPath(dept.getPath());
					//					order.setDeptPathName(dept.getPathName());
				}
			}
		}
	}
	
}
