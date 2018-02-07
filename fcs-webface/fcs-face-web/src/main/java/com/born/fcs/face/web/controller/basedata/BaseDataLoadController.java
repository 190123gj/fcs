package com.born.fcs.face.web.controller.basedata;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.read.biff.BiffException;

import org.apache.commons.fileupload.FileUploadException;
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
import com.born.bpm.service.client.user.SysUser;
import com.born.bpm.service.client.user.UserDetailsService;
import com.born.fcs.am.ws.enums.AssessCompanyStatusEnum;
import com.born.fcs.am.ws.enums.LiquidaterStatusEnum;
import com.born.fcs.am.ws.info.assesscompany.AssetsAssessCompanyInfo;
import com.born.fcs.am.ws.info.pledgeasset.AssetRelationProjectInfo;
import com.born.fcs.am.ws.info.pledgeasset.AssetSimpleInfo;
import com.born.fcs.am.ws.info.transfer.FAssetsTransferApplicationInfo;
import com.born.fcs.am.ws.order.assesscompany.AssetsAssessCompanyQueryOrder;
import com.born.fcs.am.ws.order.pledgeasset.AssetQueryOrder;
import com.born.fcs.am.ws.order.pledgeasset.AssetRelationProjectQueryOrder;
import com.born.fcs.am.ws.order.transfer.FAssetsTransferApplicationQueryOrder;
import com.born.fcs.crm.ws.service.enums.ChanalTypeEnum;
import com.born.fcs.crm.ws.service.info.ChannalContractInfo;
import com.born.fcs.crm.ws.service.info.ChannalInfo;
import com.born.fcs.crm.ws.service.info.CompanyCustomerInfo;
import com.born.fcs.crm.ws.service.info.CustomerBaseInfo;
import com.born.fcs.crm.ws.service.info.CustomerDetailInfo;
import com.born.fcs.crm.ws.service.info.PersonalCustomerInfo;
import com.born.fcs.crm.ws.service.order.query.ChannalContractQueryOrder;
import com.born.fcs.crm.ws.service.order.query.ChannalQueryOrder;
import com.born.fcs.crm.ws.service.order.query.CustomerQueryOrder;
import com.born.fcs.face.integration.bpm.service.OrgService;
import com.born.fcs.face.integration.bpm.service.PermissionUtil;
import com.born.fcs.face.integration.bpm.service.info.UserInfo;
import com.born.fcs.face.integration.comparator.MenuComparator;
import com.born.fcs.face.integration.crm.service.busyRegion.BusyRegionClient;
import com.born.fcs.face.integration.crm.service.channal.ChannalClient;
import com.born.fcs.face.integration.crm.service.customer.PersonalCustomerClient;
import com.born.fcs.face.integration.fm.service.payment.BudgetServiceClient;
import com.born.fcs.face.integration.info.MenuInfo;
import com.born.fcs.face.integration.info.OrgInfo;
import com.born.fcs.face.integration.risk.service.KingdeeTogetheFacadeClient;
import com.born.fcs.face.integration.service.BasicDataCacheService;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.CnyResult;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.integration.util.YahooExchangeRateUtil;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.controller.fund.payment.DeptBalanceCheckLock;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.ExcelData;
import com.born.fcs.face.web.util.ExcelUtil;
import com.born.fcs.face.web.util.PakoGzipUtils;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.fm.ws.enums.CostCategoryStatusEnum;
import com.born.fcs.fm.ws.enums.CostDirectionEnum;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.enums.ReceiptPaymentFormStatusEnum;
import com.born.fcs.fm.ws.enums.SubjectStatusEnum;
import com.born.fcs.fm.ws.info.bank.BankMessageInfo;
import com.born.fcs.fm.ws.info.common.ReceiptPaymentFormInfo;
import com.born.fcs.fm.ws.info.payment.CostCategoryInfo;
import com.born.fcs.fm.ws.info.payment.ExpenseCxDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationDetailAllInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationInfo;
import com.born.fcs.fm.ws.order.bank.BankMessageQueryOrder;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormQueryOrder;
import com.born.fcs.fm.ws.order.common.SyncKingdeeBasicDataOrder;
import com.born.fcs.fm.ws.order.payment.CostCategoryQueryOrder;
import com.born.fcs.fm.ws.order.payment.ExpenseApplicationQueryOrder;
import com.born.fcs.fm.ws.order.payment.TravelExpenseOrder;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.util.CommonUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.util.MoneyUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.QueryPageBase;
import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CertTypeEnum;
import com.born.fcs.pm.ws.enums.CheckReportEditionEnums;
import com.born.fcs.pm.ws.enums.CheckStatusEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.CompanyNameEnum;
import com.born.fcs.pm.ws.enums.CouncilApplyStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.CreditCheckStatusEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.EnterpriseNatureEnum;
import com.born.fcs.pm.ws.enums.FinancialProductSellStatusEnum;
import com.born.fcs.pm.ws.enums.FinancialProjectStatusEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.PaymentMenthodEnum;
import com.born.fcs.pm.ws.enums.PledgePropertyEnum;
import com.born.fcs.pm.ws.enums.PledgeTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.enums.ReleaseProjectStatusEnum;
import com.born.fcs.pm.ws.enums.SubsystemDockFormTypeEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.info.basicmaintain.AssessCompanyInfo;
import com.born.fcs.pm.ws.info.basicmaintain.DecisionInstitutionInfo;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyInfo;
import com.born.fcs.pm.ws.info.check.AfterwardsCheckInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckInfo;
import com.born.fcs.pm.ws.info.common.BusiTypeInfo;
import com.born.fcs.pm.ws.info.common.CommonAttachmentInfo;
import com.born.fcs.pm.ws.info.common.FinancialProductInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.IndustryInfo;
import com.born.fcs.pm.ws.info.common.ProjectChannelRelationInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.info.common.RegionInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractItemInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractResultInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContrctInfo;
import com.born.fcs.pm.ws.info.council.CouncilApplyInfo;
import com.born.fcs.pm.ws.info.council.CouncilSummaryRiskHandleInfo;
import com.born.fcs.pm.ws.info.council.CouncilTypeInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectInfo;
import com.born.fcs.pm.ws.info.creditrefrerenceapply.CreditRefrerenceApplyReusltInfo;
import com.born.fcs.pm.ws.info.file.FileInOutInfo;
import com.born.fcs.pm.ws.info.file.FileInfo;
import com.born.fcs.pm.ws.info.file.FileInputListInfo;
import com.born.fcs.pm.ws.info.financeaffirm.ProjectChargePayInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectSetupFormInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationInfo;
import com.born.fcs.pm.ws.info.finvestigation.InvestigationInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeApplySearchInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.ProjectCreditConditionInfo;
import com.born.fcs.pm.ws.info.projectissueinformation.ProjectBondReinsuranceInformationInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryInfo;
import com.born.fcs.pm.ws.info.setup.FProjectCustomerBaseInfo;
import com.born.fcs.pm.ws.info.setup.FProjectInfo;
import com.born.fcs.pm.ws.info.setup.SetupFormProjectInfo;
import com.born.fcs.pm.ws.info.stampapply.StampConfigureListInfo;
import com.born.fcs.pm.ws.info.user.UserExtraMessageInfo;
import com.born.fcs.pm.ws.order.basicmaintain.AssessCompanyQueryOrder;
import com.born.fcs.pm.ws.order.basicmaintain.DecisionInstitutionQueryOrder;
import com.born.fcs.pm.ws.order.basicmaintain.FinancialProductQueryOrder;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyQueryOrder;
import com.born.fcs.pm.ws.order.check.AfterwordsCheckQueryOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckQueryOrder;
import com.born.fcs.pm.ws.order.common.CommonAttachmentOrder;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserQueryOrder;
import com.born.fcs.pm.ws.order.contract.ProjectContractQueryOrder;
import com.born.fcs.pm.ws.order.council.CouncilApplyQueryOrder;
import com.born.fcs.pm.ws.order.council.CouncilSummaryRiskHandleQueryOrder;
import com.born.fcs.pm.ws.order.council.CouncilTypeQueryOrder;
import com.born.fcs.pm.ws.order.creditrefrerenceapply.CreditRefrerenceApplyQueryOrder;
import com.born.fcs.pm.ws.order.file.FileQueryOrder;
import com.born.fcs.pm.ws.order.financeaffirm.ProjectChargePayQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectQueryOrder;
import com.born.fcs.pm.ws.order.finvestigation.InvestigationQueryOrder;
import com.born.fcs.pm.ws.order.formchange.FormChangeApplyQueryOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.ProjectCreditConditionQueryOrder;
import com.born.fcs.pm.ws.order.setup.SetupFormQueryOrder;
import com.born.fcs.pm.ws.order.user.UserExtraMessageQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.common.CommonAttachmentResult;
import com.born.fcs.pm.ws.result.common.ProjectCandoResult;
import com.born.fcs.pm.ws.service.basicmaintain.AssessCompanyService;
import com.born.fcs.pm.ws.service.basicmaintain.DecisionInstitutionService;
import com.born.fcs.pm.ws.service.basicmaintain.FinancialProductService;
import com.born.fcs.pm.ws.service.capitalappropriationapply.FCapitalAppropriationApplyService;
import com.born.fcs.pm.ws.service.common.BasicDataService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.council.CouncilApplyService;
import com.born.fcs.pm.ws.service.fund.RefundApplicationService;
import com.born.fcs.pm.ws.service.notice.ConsentIssueNoticeService;
import com.born.fcs.pm.ws.service.projectcreditcondition.ProjectCreditConditionService;
import com.born.fcs.pm.ws.service.projectissueinformation.ProjectIssueInformationService;
import com.born.fcs.pm.ws.service.setup.ProjectSetupService;
import com.bornsoft.facade.api.apix.ApixCreditInvestigationFacade;
import com.bornsoft.facade.api.risk.RiskSystemFacade;
import com.bornsoft.pub.enums.ApixResultEnum;
import com.bornsoft.pub.order.apix.ApixDishonestQueryOrder;
import com.bornsoft.pub.order.apix.ApixDishonestQueryOrder.ApixUserTypeEnum;
import com.bornsoft.pub.order.apix.ApixOrderBase;
import com.bornsoft.pub.order.apix.ApixValidateBankCardOrder;
import com.bornsoft.pub.order.apix.ApixValidateBankCardOrder.ValidateTypeEnum;
import com.bornsoft.pub.order.apix.ApixValidateIdCardOrder;
import com.bornsoft.pub.order.apix.ApixValidateMobileOrder;
import com.bornsoft.pub.order.kingdee.KingdeeQueryAccountOrder;
import com.bornsoft.pub.order.kingdee.KingdeeQueryGuaranteeOrder;
import com.bornsoft.pub.order.risk.QuerySimilarEnterpriseOrder;
import com.bornsoft.pub.result.apix.ApixDishonestQueryResult;
import com.bornsoft.pub.result.apix.ApixDishonestQueryResult.DishonestInfo;
import com.bornsoft.pub.result.apix.ApixValidateBankCardResult;
import com.bornsoft.pub.result.apix.ApixValidateIdCardResult;
import com.bornsoft.pub.result.apix.ApixValidateMobileResult;
import com.bornsoft.pub.result.kingdee.KingdeeQueryAccountResult;
import com.bornsoft.pub.result.kingdee.KingdeeQueryAccountResult.AccountInfo;
import com.bornsoft.pub.result.kingdee.KingdeeQueryGuaranteeResult;
import com.bornsoft.pub.result.kingdee.KingdeeQueryGuaranteeResult.GuaranteeInfo;
import com.bornsoft.pub.result.risk.QuerySimilarEnterpriseResult;
import com.bornsoft.pub.result.risk.QuerySimilarEnterpriseResult.CustomInfo;
import com.bornsoft.utils.enums.CommonResultEnum;
import com.yjf.common.env.Env;
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
	private BasicDataService basicDataServiceClient;
	
	@Autowired
	private BasicDataCacheService basicDataCacheService;
	
	@Autowired
	private FinancialProductService financialProductServiceClient;
	
	@Autowired
	private DecisionInstitutionService decisionInstitutionServiceClient;
	
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
	protected FCapitalAppropriationApplyService fCapitalAppropriationApplyServiceClient;
	@Autowired
	protected ApixCreditInvestigationFacade apixCreditInvestigationFacadeClient;
	
	@Autowired
	protected RiskSystemFacade riskSystemFacadeClient;
	
	@Autowired
	protected KingdeeTogetheFacadeClient kingdeeTogetheFacadeClient;
	
	@Autowired
	protected BusyRegionClient busyRegionClient;
	
	@Autowired
	protected ChannalClient channalClient;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	/**
	 * 选择待上会项目
	 * 
	 * @return
	 */
	@RequestMapping("councilApply.json")
	@ResponseBody
	public JSONObject councilApply(String councilCode, Long typeId, String ruleCheck) {
		String tipPrefix = "查询待上会项目";
		JSONObject result = new JSONObject();
		try {
			JSONArray data = new JSONArray();
			CouncilApplyQueryOrder order = new CouncilApplyQueryOrder();
			order.setStatus(CouncilApplyStatusEnum.WAIT);
			if (StringUtil.isNotEmpty(councilCode)) {
				order.setCouncilCode(councilCode);
			}
			order.setPageSize(1000L);
			// 20170310 需要进行权限校验的
			if (StringUtil.isNotBlank(ruleCheck)) {
				// 总经理秘书只能看总经理办公会，风控委秘书不能看总经理办公会
				List<String> companyNames = new ArrayList<String>();
				List<String> councilTypeCodes = new ArrayList<String>();
				if (isRiskSecretary()) {
					councilTypeCodes.add(CouncilTypeEnum.PROJECT_REVIEW.code());
					councilTypeCodes.add(CouncilTypeEnum.RISK_HANDLE.code());
					companyNames.add(CompanyNameEnum.NORMAL.code());
				}
				if (isManagerSecretary()) {
					councilTypeCodes.add(CouncilTypeEnum.GM_WORKING.code());
					companyNames.add(CompanyNameEnum.NORMAL.code());
				}
				if (isManagerSecretaryXH()) {
					councilTypeCodes.add(CouncilTypeEnum.GM_WORKING.code());
					companyNames.add(CompanyNameEnum.XINHUI.code());
				}
				order.setCompanyNames(companyNames);
				order.setCouncilTypeCodes(councilTypeCodes);
			}
			List<CouncilApplyInfo> list = councilApplyServiceClient.queryCouncilApply(order)
				.getPageList();
			CouncilTypeInfo councilTypeInfo = null;
			if (typeId != null && typeId >= 0) {
				councilTypeInfo = councilTypeServiceClient.findById(typeId);
			}
			for (CouncilApplyInfo info : list) {
				JSONObject json = new JSONObject();
				// 添加判断，若不满足会议类型的部门判定。不予展示到页面上
				if (councilTypeInfo != null) {
					if (councilTypeInfo.getApplyDeptId() == null
						|| !Arrays.asList(councilTypeInfo.getApplyDeptId().split(",")).contains(
							String.valueOf(info.getApplyDeptId()))) {
						// 为空或者 不包含id 不展示
						continue;
					}
				}
				json.put("applyId", info.getApplyId());
				json.put("projectCode", info.getProjectCode());
				json.put("projectName", info.getProjectName());
				json.put("councilTypeDesc", info.getCouncilTypeDesc());
				data.add(json);
			}
			
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		
		return result;
	}
	
	/**
	 * 会议类型选择列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("councilTypeList.json")
	@ResponseBody
	public JSONObject councilTypeList(CouncilTypeQueryOrder order, Model model) {
		String tipPrefix = "查询会议类型";
		if (StringUtil.isEmpty(order.getSortCol())) {
			order.setSortCol("raw_add_time");
			order.setSortOrder("DESC");
		}
		JSONObject result = new JSONObject();
		try {
			
			List<String> councilTypeCodes = new ArrayList<>();
			List<String> companyNames = new ArrayList<>();
			if (isRiskSecretary()) {
				councilTypeCodes.add(CouncilTypeEnum.PROJECT_REVIEW.code());
				councilTypeCodes.add(CouncilTypeEnum.RISK_HANDLE.code());
				companyNames.add(CompanyNameEnum.NORMAL.code());
			}
			if (isManagerSecretary()) {
				councilTypeCodes.add(CouncilTypeEnum.GM_WORKING.code());
				companyNames.add(CompanyNameEnum.NORMAL.code());
			}
			if (isManagerSecretaryXH()) {
				councilTypeCodes.add(CouncilTypeEnum.GM_WORKING.code());
				companyNames.add(CompanyNameEnum.XINHUI.code());
			}
			order.setCompanyNames(companyNames);
			order.setCouncilTypeCodes(councilTypeCodes);
			
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			
			QueryBaseBatchResult<CouncilTypeInfo> batchResult = councilTypeServiceClient
				.query(order);
			List<CouncilTypeInfo> list = batchResult.getPageList();
			for (CouncilTypeInfo info : list) {
				JSONObject json = new JSONObject();
				json.put("typeId", info.getTypeId());
				json.put("typeCodeMessage", info.getTypeCode().getMessage());
				json.put("typeName", info.getTypeName());
				json.put("ifVote", info.getIfVote().message());
				json.put("decisionInstitutionName", info.getDecisionInstitutionName());
				json.put("applyDeptId", info.getApplyDeptId());
				json.put("applyCompany", info.getApplyCompany());
				json.put("majorNum", info.getMajorNum());
				json.put("lessNum", info.getLessNum());
				dataList.add(json);
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
	 * 选择待风险审查项目
	 * @param order
	 * @return
	 */
	@RequestMapping("waitRiskReviewProject.json")
	@ResponseBody
	public JSONObject waitRiskReviewProject(ProjectQueryOrder order) {
		String tipPrefix = "查询待风险审查项目";
		JSONObject result = new JSONObject();
		try {
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			
			List<ProjectStatusEnum> status = Lists.newArrayList();
			/*TODO 设置要查询的状态
			 * status.add(ProjectStatusEnum.XXXX);*/
			order.setStatusList(status);
			QueryBaseBatchResult<ProjectInfo> batchResult = projectServiceClient
				.queryProject(order);
			List<ProjectInfo> list = batchResult.getPageList();
			for (ProjectInfo info : list) {
				JSONObject json = new JSONObject();
				json.put("projectCode", info.getProjectCode());
				json.put("projectName", info.getProjectName());
				json.put("customerId", info.getCustomerId());
				json.put("customerName", info.getCustomerName());
				json.put("amount", MoneyUtil.getMoneyByw(info.getAmount()));
				json.put("busiType", info.getBusiType());
				json.put("busiTypeName", info.getBusiTypeName());
				dataList.add(json);
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
	 * 获取保后检查-报告类型（默认）
	 * 
	 * @param parentCode
	 * @param model
	 * @return
	 */
	@RequestMapping("checkReportEdition.json")
	@ResponseBody
	public JSONObject checkReportEdition(String projectCode, Model model) {
		String tipPrefix = "获取保后检查-报告类型";
		JSONObject result = new JSONObject();
		try {
			ProjectInfo project = projectServiceClient.queryByCode(projectCode, false);
			String industryCode = project.getIndustryCode();
			//String industryCode = "A-01";
			CheckReportEditionEnums edition = CheckReportEditionEnums.V_COMMON;
			if (StringUtil.isNotEmpty(industryCode)) {
				String startChar = industryCode.substring(0, 1);
				if ("A,B,D,F,G,H,I,M,N,O,P,Q,R,S,T".indexOf(startChar) >= 0) {
					edition = CheckReportEditionEnums.V_COMMON;
				} else if ("K,".indexOf(startChar) >= 0) {
					edition = CheckReportEditionEnums.V_REAL_ESTATE;
				} else if ("E,".indexOf(startChar) >= 0) {
					edition = CheckReportEditionEnums.V_CONSTRUCTION;
				} else if ("C,".indexOf(startChar) >= 0) {
					edition = CheckReportEditionEnums.V_MANUFACTURING;
				} else if ("J,".indexOf(startChar) >= 0) {
					edition = CheckReportEditionEnums.V_LOAN;
				} else if ("L,".indexOf(startChar) >= 0) {
					edition = CheckReportEditionEnums.V_CITY;
				} else {
					edition = CheckReportEditionEnums.V_COMMON;
				}
			}
			JSONObject data = new JSONObject();
			data.put("code", edition.getCode());
			data.put("message", edition.getMessage());
			result = toStandardResult(data, tipPrefix);
			
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		
		return result;
	}
	
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
		String tipPrefix = "查询行业信息";
		JSONObject result = new JSONObject();
		try {
			
			List<IndustryInfo> data = basicDataCacheService.queryIndustry(parentCode);
			JSONArray finalData = new JSONArray();
			for (IndustryInfo info : data) {
				JSONObject json = new JSONObject();
				json.put("code", info.getCode());
				json.put("name", info.getName());
				json.put("level", info.getLevel());
				json.put("remark", info.getRemark() == null ? "" : info.getRemark());
				finalData.add(json);
			}
			result = toStandardResult(finalData, tipPrefix);
			
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		
		return result;
	}
	
	/**
	 * 选择区域
	 * 
	 * @param parentCode
	 * @param model
	 * @param edit 编辑有区域限制
	 * @return
	 */
	@RequestMapping("region.json")
	@ResponseBody
	public JSONObject region(String parentCode, Boolean edit, Model model) {
		String tipPrefix = "选择区域";
		JSONObject result = new JSONObject();
		try {
			List<RegionInfo> data = basicDataCacheService.queryRegion(parentCode);
			JSONArray finalData = new JSONArray();
			//包含业务区域
			Map<String, String> regionMap = null;
			//全国
			boolean all = false;
			if (edit != null && edit) {
				all = true;
			}
			//仅业务部 国内 分区域
			if (!all && DataPermissionUtil.isBusiMenber() && "China".equals(parentCode)) {
				//是否启用区域功能
				if (busyRegionClient.queryStatus(null).isSuccess()) {
					SessionLocal sessionlocal = ShiroSessionUtils.getSessionLocal();
					UserDetailInfo user = sessionlocal.getUserDetailInfo();
					if (user.getPrimaryOrg() != null) {
						boolean coperAll = true;
						if (DataPermissionUtil.isBusiFZR() || DataPermissionUtil.isBusinessFgfz()) {
							coperAll = false;
						}
						regionMap = busyRegionClient.busyRegMap(user.getPrimaryOrg().getPath(),
							coperAll);
						if (regionMap != null && regionMap.containsKey("China"))
							all = true;
					} else {
						regionMap = null;
					}
				}
				
			}
			
			for (RegionInfo info : data) {
				if (!all && regionMap != null && !regionMap.containsKey(info.getCode())) {
					continue;
				}
				JSONObject json = new JSONObject();
				json.put("code", info.getCode());
				json.put("name", info.getName());
				json.put("hasChildren", info.getHasChildren());
				json.put("level", info.getLevel());
				finalData.add(json);
			}
			result = toStandardResult(finalData, tipPrefix);
			
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
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
	private JSONArray makeBusiTypeJson(String customerType, String formCode, int parentId,
										boolean isXinhui, boolean isAdmin) {
		
		JSONArray data = new JSONArray();
		
		List<BusiTypeInfo> list = basicDataCacheService.queryBusiType(customerType, formCode,
			parentId);
		
		for (BusiTypeInfo info : list) {
			if (!isAdmin) {
				if (!isXinhui && ProjectUtil.isUnderwriting(info.getCode())) {
					continue;
				}
			}
			JSONObject t = new JSONObject();
			t.put("code", info.getCode());
			t.put("name", info.getName());
			t.put("customerType", info.getCustomerType().code());
			if (info.getHasChildren() == BooleanEnum.IS) {
				t.put("children",
					makeBusiTypeJson(customerType, formCode, info.getId(), isXinhui, isAdmin));
			} else {
				t.put("setupFormCode", info.getSetupFormCode());
			}
			data.add(t);
			
		}
		return data;
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
		String tipPrefix = "选择业务类型";
		JSONObject result = new JSONObject();
		try {
			// 默认企业
			if (StringUtil.isEmpty(customerType)) {
				customerType = CustomerTypeEnum.ENTERPRISE.code();
			}
			boolean isXinhui = DataPermissionUtil.isBelong2Xinhui();
			boolean isAdmin = DataPermissionUtil.isCompanyLeader()
								|| DataPermissionUtil.isSystemAdministrator()
								|| DataPermissionUtil.isRiskSecretary();
			result = toStandardResult(
				makeBusiTypeJson(customerType, formCode, -1, isXinhui, isAdmin), tipPrefix);
			
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		
		return result;
	}
	
	/**
	 * 选择理财产品
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("financialProduct.json")
	@ResponseBody
	public JSONObject financialProduct(FinancialProductQueryOrder order, Model model) {
		String tipPrefix = "理财产品";
		JSONObject result = new JSONObject();
		try {
			
			if (order == null) {
				order = new FinancialProductQueryOrder();
			}
			order.setSellStatus(FinancialProductSellStatusEnum.SELLING.code());
			QueryBaseBatchResult<FinancialProductInfo> batchResult = financialProductServiceClient
				.query(order);
			
			if (batchResult != null && batchResult.isSuccess()) {
				JSONObject data = new JSONObject();
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("pageCount", batchResult.getPageCount());
				List<JSONObject> dataList = Lists.newArrayList();
				if (batchResult.getPageList() != null) {
					for (FinancialProductInfo info : batchResult.getPageList()) {
						JSONObject json = (JSONObject) JSONObject.toJSON(info);
						json.put("timeUnitName", info.getTimeUnit().viewName());
						json.put("productTypeName", info.getProductType().message());
						json.put("termTypeName", info.getTermType().message());
						json.put("interestTypeName", info.getInterestType().message());
						dataList.add(json);
					}
				}
				data.put("pageList", dataList);
				result = toStandardResult(data, tipPrefix);
			} else {
				result = toStandardResult(null, tipPrefix);
			}
			
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		
		return result;
	}
	
	/**
	 * 选择理财项目
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("projectFinancial.json")
	@ResponseBody
	public JSONObject productFinancial(FinancialProjectQueryOrder order,
										HttpServletRequest request, Model model) {
		String tipPrefix = "查询理财项目";
		JSONObject result = new JSONObject();
		try {
			
			if (order == null) {
				order = new FinancialProjectQueryOrder();
			}
			
			if (StringUtil.equals(request.getParameter("qType"), "my")) {
				order.setCreateUserId(ShiroSessionUtils.getSessionLocal().getUserId());
			}
			
			//查询到期信息维护的项目
			if (StringUtil.equals(request.getParameter("qs"), "expire")) {
				order.setActualExpireDate(new Date());
				List<String> statusList = Lists.newArrayList();
				statusList.add(FinancialProjectStatusEnum.PURCHASED.code());
				statusList.add(FinancialProjectStatusEnum.EXPIRE.code());
				order.setStatusList(statusList);
			}
			
			//查询结息选择项目
			if (StringUtil.equals(request.getParameter("qs"), "settlement")) {
				List<String> statusList = Lists.newArrayList();
				statusList.add(FinancialProjectStatusEnum.PURCHASED.code());
				statusList.add(FinancialProjectStatusEnum.EXPIRE.code());
				order.setStatusList(statusList);
			}
			
			QueryBaseBatchResult<ProjectFinancialInfo> batchResult = financialProjectServiceClient
				.query(order);
			
			if (batchResult != null && batchResult.isSuccess()) {
				JSONObject data = new JSONObject();
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("pageCount", batchResult.getPageCount());
				List<JSONObject> dataList = Lists.newArrayList();
				if (batchResult.getPageList() != null) {
					for (ProjectFinancialInfo info : batchResult.getPageList()) {
						JSONObject json = new JSONObject();
						json.put("projectCode", info.getProjectCode());//项目编号
						json.put("productName", info.getProductName());//产品名称
						json.put("issueInstitution", info.getIssueInstitution());//发行机构
						json.put("timeLimit", info.getTimeLimit());//产品期限
						json.put("timeUnit", info.getTimeUnit());//产品期限 单位
						json.put("price", info.getPrice().toStandardString());//票面单价
						json.put("timeUnitName", info.getTimeUnit().viewName());
						json.put("productTypeName", info.getProductType().message());
						json.put("termTypeName", info.getTermType().message());
						json.put("interestTypeName", info.getInterestType().message());
						json.put("interestRate", info.getInterestRate());
						dataList.add(json);
					}
				}
				data.put("pageList", dataList);
				result = toStandardResult(data, tipPrefix);
			} else {
				result = toStandardResult(null, tipPrefix);
			}
			
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询理财项目出错 {}", e);
		}
		
		return result;
	}
	
	/**
	 * 选择资金划付理财项目
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("purchasingFinancialProject.json")
	@ResponseBody
	public JSONObject purchasingFinancialProject(FinancialProjectQueryOrder order, Model model) {
		String tipPrefix = "查询资金划付理财项目";
		JSONObject result = new JSONObject();
		try {
			if (order == null) {
				order = new FinancialProjectQueryOrder();
			}
			setSessionLocalInfo2Order(order);
			order.setBusiManagerId(ShiroSessionUtils.getSessionLocal().getUserId());
			QueryBaseBatchResult<ProjectFinancialInfo> batchResult = financialProjectServiceClient
				.queryPurchasing(order);
			if (batchResult != null && batchResult.isSuccess()) {
				JSONObject data = new JSONObject();
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("pageCount", batchResult.getPageCount());
				List<JSONObject> dataList = Lists.newArrayList();
				if (batchResult.getPageList() != null) {
					for (ProjectFinancialInfo info : batchResult.getPageList()) {
						JSONObject json = new JSONObject();
						json.put("projectCode", info.getProjectCode());//项目编号
						json.put("productName", info.getProductName());//产品名称
						json.put("issueInstitution", info.getIssueInstitution());//发行机构
						json.put("timeLimit", info.getTimeLimit());//产品期限
						json.put("timeUnit", info.getTimeUnit());//产品期限 单位
						json.put("price", info.getPrice().toStandardString());//票面单价
						if (info.getTimeUnit() != null)
							json.put("timeUnitName", info.getTimeUnit().viewName());
						if (info.getProductType() != null)
							json.put("productTypeName", info.getProductType().message());
						if (info.getTermType() != null)
							json.put("termTypeName", info.getTermType().message());
						if (info.getInterestType() != null)
							json.put("interestTypeName", info.getInterestType().message());
						json.put("interestRate", info.getInterestRate());
						dataList.add(json);
					}
				}
				data.put("pageList", dataList);
				result = toStandardResult(data, tipPrefix);
			} else {
				result = toStandardResult(null, tipPrefix);
			}
			
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询资金划付理财项目{}", e);
		}
		
		return result;
	}
	
	/**
	 * 选择资金划付项目
	 * @param order
	 * @return
	 */
	@RequestMapping("loanCapitalAppropriation.json")
	@ResponseBody
	public JSONObject loanCapitalAppropriation(ProjectQueryOrder order) {
		
		SessionLocal session = ShiroSessionUtils.getSessionLocal();
		String tipPrefix = "查询资金划付项目";
		JSONObject result = new JSONObject();
		try {
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			
			//			order.setHasBothAmount(BooleanEnum.IS);
			order.setHasContract(BooleanEnum.IS);
			List<ProjectStatusEnum> pStatusList = Lists.newArrayList();
			//代偿和正常
			pStatusList.add(ProjectStatusEnum.NORMAL);
			pStatusList.add(ProjectStatusEnum.RECOVERY);
			pStatusList.add(ProjectStatusEnum.TRANSFERRED);
			pStatusList.add(ProjectStatusEnum.SELL_FINISH);
			pStatusList.add(ProjectStatusEnum.FINISH);
			pStatusList.add(ProjectStatusEnum.EXPIRE);
			pStatusList.add(ProjectStatusEnum.OVERDUE);
			order.setStatusList(pStatusList);
			//			order.setStatus(ProjectStatusEnum.NORMAL);
			if (isBusiManager()) {
				order.setBusiManagerId(session.getUserId()); //查询当前业务经理的项目
			}
			if (StringUtil.isEmpty(order.getSortCol())) {
				order.setSortCol("p.raw_update_time");
				order.setSortOrder("DESC");
			}
			QueryBaseBatchResult<ProjectSimpleDetailInfo> batchResult = projectServiceClient
				.queryProjectSimpleDetail(order);
			List<ProjectSimpleDetailInfo> list = batchResult.getPageList();
			for (ProjectSimpleDetailInfo info : list) {
				JSONObject json = new JSONObject();
				json.put("projectCode", info.getProjectCode());
				json.put("projectName", info.getProjectName());
				json.put("customerId", info.getCustomerId());
				json.put("customerName", info.getCustomerName());
				//				json.put("amount",
				//					NumberUtil.format(MoneyUtil.getMoneyByw(info.getAmount()), "0.00"));
				//				json.put("amount", info.getAmount().toString());
				json.put("amount", info.getAmount().toStandardString());
				json.put("busiType", info.getBusiType());
				json.put("busiTypeName", info.getBusiTypeName());
				if (info.getChargeType() != null) {
					json.put("chargeFee", CommonUtil.numberFormat(info.getChargeFee(), 2)
											+ info.getChargeType().unit());
				} else {
					json.put("chargeFee", CommonUtil.numberFormat(info.getChargeFee(), 2));
				}
				json.put("chargeName", info.getChargeName());
				if (info.getTimeUnit() != null) {
					json.put("timeLimit", info.getTimeLimit() + info.getTimeUnit().viewName());
				} else {
					json.put("timeLimit", "-");
				}
				dataList.add(json);
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
	 * 资金划付加载资金渠道
	 * @param order
	 * @return
	 */
	@RequestMapping("loadCapitalChannel.json")
	@ResponseBody
	public JSONArray loadCapitalChannel(String projectCode, HttpServletRequest request,
										HttpServletResponse response, Model model) {
		String tipPrefix = "资金划付加载资金渠道";
		JSONArray result = new JSONArray();
		try {
			//查询资金渠道
			List<ProjectChannelRelationInfo> capitalChannels = projectChannelRelationServiceClient
				.queryCapitalChannel(projectCode);
			result = makeCapitalAppropriation(capitalChannels);
			//			result = toStandardResult(json, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		return result;
	}
	
	private JSONArray makeCapitalAppropriation(List<ProjectChannelRelationInfo> capitalChannels) {
		JSONArray jsonArray = new JSONArray();
		for (ProjectChannelRelationInfo info : capitalChannels) {
			JSONObject json = new JSONObject();
			json.put("capitalChannelId", info.getChannelId());//资金渠道id
			json.put("capitalChannelCode", info.getChannelCode());//资金渠道编码
			json.put("capitalChannelType", info.getChannelType());//资金渠道类型
			json.put("capitalChannelName", info.getChannelName());//资金渠道名称
			json.put("capitalSubChannelId", info.getSubChannelId());//二级资金渠道id
			json.put("capitalSubChannelCode", info.getSubChannelCode());//二级资金渠道编码
			json.put("capitalSubChannelType", info.getSubChannelType());//二级资金渠道类型
			json.put("capitalSubChannelName", info.getSubChannelName());//二级资金渠道名称
			
			//			json.put("actual_deposit_amount", info.getSubChannelName());//二级资金渠道名称
			//			json.put("liquidity_loan_amount", info.getLiquidityLoansAmount());//流动资金贷款
			//			json.put("fixed_assets_financing_amount", info.getSubChannelName());//二级资金渠道名称
			//			json.put("acceptance_bill_amount", info.getSubChannelName());//二级资金渠道名称
			//			json.put("credit_letter_amount", info.getSubChannelName());//二级资金渠道名称
			//			JSONObject json = toJsonObj(info);
			
			jsonArray.add(json);
		}
		return jsonArray;
	}
	
	/**
	 * 获取项目信息
	 * @param projectCode
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("loadFProjectData.json")
	@ResponseBody
	public JSONObject loadFProjectData(String projectCode, HttpServletRequest request,
										HttpServletResponse response, Model model) {
		String tipPrefix = "查询项目信息";
		JSONObject result = new JSONObject();
		try {
			FProjectInfo project = projectSetupServiceClient.queryProjectByCode(projectCode);
			JSONObject json = toJsonObj(project);
			result = toStandardResult(json, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		return result;
	}
	
	/**
	 * 获取项目信息
	 * @param projectCode
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("loadProjectData.json")
	@ResponseBody
	public JSONObject loadProjectData(String projectCode, HttpServletRequest request,
										HttpServletResponse response, Model model) {
		String tipPrefix = "查询项目信息";
		JSONObject result = new JSONObject();
		try {
			ProjectSimpleDetailInfo info = projectServiceClient.querySimpleDetailInfo(projectCode);
			JSONObject json = new JSONObject();
			json.put("projectCode", info.getProjectCode());
			json.put("projectName", info.getProjectName());
			json.put("customerId", info.getCustomerId());
			json.put("customerName", info.getCustomerName());
			json.put("amount", info.getAmount().toStandardString());
			json.put("formatAmountW", MoneyUtil.formatW(info.getAmount()));
			json.put("formatAmount", MoneyUtil.format(info.getAmount()));
			json.put("busiType", info.getBusiType());
			json.put("busiTypeName", info.getBusiTypeName());
			json.put("contractNo", info.getContractNo());
			json.put("spCode", info.getSpCode());
			json.put("showFormChangeApply", showFormChangeApply(projectCode));
			json.put("contractAmount", info.getContractAmount());
			json.put("balance", info.getBalance());
			if (info.getChargeType() != null) {
				json.put("chargeFee", CommonUtil.numberFormat(info.getChargeFee(), 2)
										+ info.getChargeType().unit());
			} else {
				json.put("chargeFee", CommonUtil.numberFormat(info.getChargeFee(), 2));
			}
			json.put("chargeName", info.getChargeName());
			json.put("institutionId", info.getInstitutionId());
			json.put("institutionName", info.getInstitutionName());
			json.put("institutionTypeName", info.getInstitutionTypeName());
			json.put("institutionIds", info.getInstitutionIds());
			json.put("institutionNames", info.getInstitutionNames());
			if (info.getTimeUnit() != null) {
				json.put("timeLimit", info.getTimeLimit() + info.getTimeUnit().viewName());
			} else {
				json.put("timeLimit", "-");
			}
			
			ProjectRelatedUserInfo busiManager = projectRelatedUserServiceClient
				.getBusiManagerb(info.getProjectCode());
			if (null != busiManager) {
				json.put("bid", busiManager.getUserId());
				json.put("bname", busiManager.getUserName());
			}
			
			result = toStandardResult(json, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		return result;
	}
	
	@RequestMapping("loadProjectAndCustomerData.json")
	@ResponseBody
	public JSONObject loadProjectAndCustomerData(String projectCode, HttpServletRequest request,
													HttpServletResponse response, Model model) {
		String tipPrefix = "查询项目和客户信息";
		JSONObject result = new JSONObject();
		try {
			ProjectInfo project = projectServiceClient.queryByCode(projectCode, false);
			FProjectCustomerBaseInfo customer = projectSetupServiceClient
				.queryCustomerBaseInfoByProjectCode(projectCode);
			JSONObject json = new JSONObject();
			json.put("project", project);
			if (null != project && null != project.getAmount()) {
				project.setAmount(project.getAmount().divide(10000d));
			}
			json.put("customer", customer);
			result = toStandardResult(json, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
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
	@RequestMapping("loadMenuData.json")
	public String loadMenuJsonb(HttpServletRequest request, HttpServletResponse response,
								Model model) {
		String tipPrefix = "获取菜单";
		String currentString = request.getParameter("currentUrl");
		currentString = PermissionUtil.filterUrl(currentString);
		String[] stringSplit = currentString.split("/");
		JSONObject jsonObject = new JSONObject();
		if (stringSplit.length > 1) {
			List<MenuInfo> menuInfos = ShiroSessionUtils.getSessionLocal()
				.getUserMenuInfoTreeBySysAlias(stringSplit[1]);
			
			JSONObject dataObject = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			Collections.sort(menuInfos, new MenuComparator<MenuInfo>());
			makeMenuJsonTree(menuInfos, jsonArray, 0);
			dataObject.put("list", jsonArray);
			jsonObject = toStandardResult(dataObject, tipPrefix);
			
		} else {
			jsonObject = toStandardResult(null, tipPrefix);
		}
		printHttpResponse(response, jsonObject);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private void makeMenuJsonTree(List<MenuInfo> menuInfos, JSONArray jsonArray, long index) {
		for (MenuInfo menuInfo : menuInfos) {
			menuInfo.setRank(index);
			Map<String, Object> map = MiscUtil.covertPoToMap(menuInfo);
			map.remove("parentId");
			map.remove("resId");
			List<MenuInfo> childMenuInfos = (List<MenuInfo>) map.get("subclass");
			Collections.sort(childMenuInfos, new MenuComparator<MenuInfo>());
			JSONArray childJsonArray = new JSONArray();
			makeMenuJsonTree(childMenuInfos, childJsonArray, index + 1);
			map.put("subclass", childMenuInfos);
			jsonArray.add(map);
		}
	}
	
	/**
	 * 获取部门/组织结构下的人员
	 * 
	 * @param deptCode
	 * @param model
	 * @return
	 */
	@RequestMapping("loadOrgMenbers.json")
	@ResponseBody
	public JSONObject loadOrgMenbersJson(String orgCode, Model model) {
		
		String tipPrefix = "获取部门/组织结构下的人员";
		JSONObject result = new JSONObject();
		try {
			List<UserInfo> userList = orgService.findOrgMenbersByCode(orgCode);
			result = toStandardResult(makeOrgMenbersJson(userList), tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		
		return result;
	}
	
	/**
	 * 获取部门/组织结构
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("loadOrgData.json")
	public String loadOrgJson(HttpServletRequest request, HttpServletResponse response,
								String orgCode, Model model) {
		String tipPrefix = "获取部门/组织结构";
		JSONObject jsonObject = new JSONObject();
		List<OrgInfo> orgInfos = Lists.newArrayList();
		
		OrgInfo orgInfo = orgService.findOrgInSystemByCode(orgCode);
		orgInfos.add(orgInfo);
		JSONObject dataObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		makeOrgJsonTree(orgInfos, jsonArray, 0);
		dataObject.put("list", jsonArray);
		jsonObject = toStandardResult(dataObject, tipPrefix);
		printHttpResponse(response, jsonObject);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private void makeOrgJsonTree(List<OrgInfo> orgInfos, JSONArray jsonArray, long index) {
		for (OrgInfo orgInfo : orgInfos) {
			orgInfo.setRank(index);
			Map<String, Object> map = MiscUtil.covertPoToMap(orgInfo);
			List<OrgInfo> childOrgInfos = (List<OrgInfo>) map.get("subOrg");
			// Collections.sort(childOrgInfos, new MenuComparator<OrgInfo>());
			JSONArray childJsonArray = new JSONArray();
			makeOrgJsonTree(childOrgInfos, childJsonArray, index + 1);
			map.put("subOrg", childOrgInfos);
			jsonArray.add(map);
		}
	}
	
	private JSONArray makeOrgMenbersJson(List<UserInfo> userList) {
		JSONArray jsonArray = new JSONArray();
		for (UserInfo user : userList) {
			JSONObject jso = new JSONObject();
			jso.put("userId", user.getUserId());
			jso.put("userName", user.getUserName());
			jso.put("realName", user.getRealName());
			jsonArray.add(jso);
		}
		return jsonArray;
	}
	
	/**
	 * 选择放用款项目
	 * @param order
	 * @return
	 */
	@RequestMapping("loanUseProject.json")
	@ResponseBody
	public JSONObject loanUseProject(QueryProjectBase order) {
		
		SessionLocal session = ShiroSessionUtils.getSessionLocal();
		String tipPrefix = "查询放用款申请项目";
		JSONObject result = new JSONObject();
		try {
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			
			setSessionLocalInfo2Order(order);
			
			//当前业务经理
			order.setBusiManagerId(session.getUserId());
			
			order.setSortCol("project_id");
			order.setSortOrder("desc");
			QueryBaseBatchResult<ProjectSimpleDetailInfo> batchResult = loanUseApplyServiceClient
				.selectLoanUseApplyProject(order);
			List<ProjectSimpleDetailInfo> list = batchResult.getPageList();
			for (ProjectSimpleDetailInfo info : list) {
				JSONObject json = new JSONObject();
				json.put("projectCode", info.getProjectCode());
				json.put("projectName", info.getProjectName());
				json.put("customerId", info.getCustomerId());
				json.put("customerName", info.getCustomerName());
				json.put("amount", info.getAmount().toStandardString());
				json.put("busiType", info.getBusiType());
				json.put("busiTypeName", info.getBusiTypeName());
				if (info.getChargeType() != null) {
					json.put("chargeFee", CommonUtil.numberFormat(info.getChargeFee(), 2)
											+ info.getChargeType().unit());
				} else {
					json.put("chargeFee", CommonUtil.numberFormat(info.getChargeFee(), 2));
				}
				json.put("chargeName", info.getChargeName());
				if (info.getTimeUnit() != null) {
					json.put("timeLimit", info.getTimeLimit() + info.getTimeUnit().viewName());
				} else {
					json.put("timeLimit", "-");
				}
				dataList.add(json);
			}
			data.put("pageCount", batchResult.getPageCount());
			data.put("pageNumber", batchResult.getPageNumber());
			data.put("pageSize", batchResult.getPageSize());
			data.put("totalCount", batchResult.getTotalCount());
			data.put("pageList", dataList);
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查看可放用款项目出错 {}", e);
		}
		
		return result;
		
	}
	
	/**
	 * 选择复议项目
	 * @param order
	 * @return
	 */
	@RequestMapping("loanRecouncliProject.json")
	@ResponseBody
	public JSONObject loanRecouncliProject(ProjectQueryOrder order) {
		
		SessionLocal session = ShiroSessionUtils.getSessionLocal();
		String tipPrefix = "查询复议申请项目";
		JSONObject result = new JSONObject();
		try {
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			
			order.setBusiManagerId(session.getUserId()); //查询当前业务经理的项目
			setSessionLocalInfo2Order(order);
			order.setIsRecouncil(BooleanEnum.YES);//查询符合复议条件的项目
			order.setIsApproval(BooleanEnum.IS); //过会的
			order.setSortCol("project_id");
			order.setSortOrder("desc");
			List<ProjectStatusEnum> pStatusList = Lists.newArrayList();
			//除开暂缓的项目
			pStatusList.add(ProjectStatusEnum.NORMAL);
			pStatusList.add(ProjectStatusEnum.FAILED);
			pStatusList.add(ProjectStatusEnum.RECOVERY);
			pStatusList.add(ProjectStatusEnum.EXPIRE);
			pStatusList.add(ProjectStatusEnum.OVERDUE);
			pStatusList.add(ProjectStatusEnum.TRANSFERRED);
			pStatusList.add(ProjectStatusEnum.SELL_FINISH);
			order.setStatusList(pStatusList);
			QueryBaseBatchResult<ProjectSimpleDetailInfo> batchResult = projectServiceClient
				.queryProjectSimpleDetail(order);
			List<ProjectSimpleDetailInfo> list = batchResult.getPageList();
			for (ProjectSimpleDetailInfo info : list) {
				JSONObject json = new JSONObject();
				json.put("projectCode", info.getProjectCode());
				json.put("projectName", info.getProjectName());
				json.put("customerId", info.getCustomerId());
				json.put("customerName", info.getCustomerName());
				json.put("amount",
					NumberUtil.format(MoneyUtil.getMoneyByw(info.getAmount()), "0.00"));
				json.put("busiType", info.getBusiType());
				json.put("busiTypeName", info.getBusiTypeName());
				if (info.getChargeType() != null) {
					json.put("chargeFee", CommonUtil.numberFormat(info.getChargeFee(), 2)
											+ info.getChargeType().unit());
				} else {
					json.put("chargeFee", CommonUtil.numberFormat(info.getChargeFee(), 2));
				}
				json.put("chargeName", info.getChargeName());
				if (info.getTimeUnit() != null) {
					json.put("timeLimit", info.getTimeLimit() + info.getTimeUnit().viewName());
				} else {
					json.put("timeLimit", "-");
				}
				dataList.add(json);
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
	 * 选择可转让或者可赎回理财项目
	 * @param order
	 * @return
	 */
	@RequestMapping("financialTransferOrRedeemProject.json")
	@ResponseBody
	public JSONObject financialTransferOrRedeemProject(FinancialProjectQueryOrder order,
														HttpServletRequest request) {
		String tipPrefix = "查询可转让或者可赎回理财项目";
		JSONObject result = new JSONObject();
		try {
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			
			if (StringUtil.equals(request.getParameter("from"), "redeem")) { //转让
				order.setCanRedeem(BooleanEnum.YES.code());
			} else if (StringUtil.equals(request.getParameter("from"), "transfer")) { //赎回
			}
			//order.setHasHoldNum(BooleanEnum.IS);
			order.setHasOrignalHoldNum(BooleanEnum.IS);
			order.setStatus(FinancialProjectStatusEnum.PURCHASED.code());
			//setSessionLocalInfo2Order(order);
			order.setCreateUserId(ShiroSessionUtils.getSessionLocal().getUserId());
			QueryBaseBatchResult<ProjectFinancialInfo> batchResult = financialProjectServiceClient
				.query(order);
			List<ProjectFinancialInfo> list = batchResult.getPageList();
			for (ProjectFinancialInfo info : list) {
				JSONObject json = new JSONObject();
				json.put("projectCode", info.getProjectCode());
				json.put("productName", info.getProductName());
				json.put("issueInstitution", info.getIssueInstitution());
				json.put("buyDate", DateUtil.dtSimpleFormat(info.getActualBuyDate()));
				json.put("expireDate", DateUtil.dtSimpleFormat(info.getActualExpireDate()));
				//				//减去正在申请中的
				//				double redeemingNum = financialProjectServiceClient.redeemingNum(
				//					info.getProjectCode(), 0);
				//				double transferingNum = financialProjectServiceClient.transferingNum(
				//					info.getProjectCode(), 0);
				json.put("holdNum", info.getActualHoldNum());
				json.put("originalHoldNum", info.getOriginalHoldNum());
				json.put("interestRate", info.getInterestRate());
				if (info.getTimeUnit() != null) {
					json.put("timeLimit", info.getTimeLimit() + info.getTimeUnit().viewName());
				} else {
					json.put("timeLimit", "-");
				}
				dataList.add(json);
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
	 * 选择送审/合同申请理财项目（立项）
	 * @param order
	 * @return
	 */
	@RequestMapping("financialReviewOrContractProject.json")
	@ResponseBody
	public JSONObject financialReviewOrContractProject(FinancialProjectQueryOrder order,
														HttpServletRequest request) {
		
		String tipPrefix = "查询可送审/合同申请的理财项目";
		JSONObject result = new JSONObject();
		try {
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal != null)
				order.setBusiManagerId(sessionLocal.getUserId());
			if (StringUtil.equals(request.getParameter("from"), "review")) { //送审
				order.setStatus(ProjectCouncilStatusEnum.COUNCIL_SENDING.code());
			} else if (StringUtil.equals(request.getParameter("from"), "contract")) { //合同
				order.setChooseForContract("YES");
			}
			
			QueryBaseBatchResult<FinancialProjectSetupFormInfo> batchResult = financialProjectSetupServiceClient
				.query(order);
			List<FinancialProjectSetupFormInfo> list = batchResult.getPageList();
			for (FinancialProjectSetupFormInfo info : list) {
				JSONObject json = new JSONObject();
				json.put("projectCode", info.getProjectCode());
				json.put("productName", info.getProductName());
				json.put("issueInstitution", info.getIssueInstitution());
				json.put("expectBuyDate", DateUtil.dtSimpleFormat(info.getExpectBuyDate()));
				json.put("expectExpireDate", DateUtil.dtSimpleFormat(info.getExpectExpireDate()));
				json.put("expectBuyNum", info.getExpectBuyNum());
				json.put("rateRangeStart", info.getRateRangeStart());
				json.put("rateRangeEnd", info.getRateRangeEnd());
				if (info.getTimeUnit() != null) {
					json.put("timeLimit", info.getTimeLimit() + info.getTimeUnit().viewName());
				} else {
					json.put("timeLimit", "-");
				}
				dataList.add(json);
			}
			data.put("pageCount", batchResult.getPageCount());
			data.put("pageNumber", batchResult.getPageNumber());
			data.put("pageSize", batchResult.getPageSize());
			data.put("totalCount", batchResult.getTotalCount());
			data.put("pageList", dataList);
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询可送审/合同申请的理财项目出错 {}", e);
		}
		
		return result;
		
	}
	
	/**
	 * 选择授信条件落实情况项目
	 * @param order
	 * @return
	 */
	@RequestMapping("loanCreditProject.json")
	@ResponseBody
	public JSONObject loanCreditProject(ProjectQueryOrder order) {
		SessionLocal session = ShiroSessionUtils.getSessionLocal();
		String tipPrefix = "选择授信条件落实情况项目";
		JSONObject result = new JSONObject();
		try {
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			if (order == null)
				order = new ProjectQueryOrder();
			//			List<ProjectStatusEnum> status = Lists.newArrayList();
			//			order.setStatusList(status);
			//			order.setHasBothAmount(BooleanEnum.IS);
			order.setSortCol("p.raw_update_time");
			order.setSortOrder("DESC");
			//			List<String> busiTypeList = new ArrayList<String>();
			//			busiTypeList.add("51");
			//			busiTypeList.add("");
			//			order.setBusiTypeList(busiTypeList);
			//			QueryBaseBatchResult<ProjectInfo> batchResult = projectServiceClient
			//				.queryProjectSimpleDetail(order);
			//order.setBusiManagerId(session.getUserId()); //查询当前业务经理的项目
			//2018-01-16 改为放款准备岗发起,能看到所有
			setSessionLocalInfo2Order(order);
			if (DataPermissionUtil.isLoanPrepareRole()) {
				order.setLoginUserId(0);
				order.setDeptIdList(null);
			}
			
			List<ProjectStatusEnum> pStatusList = Lists.newArrayList();
			//除开暂缓的项目
			pStatusList.add(ProjectStatusEnum.NORMAL);
			pStatusList.add(ProjectStatusEnum.FAILED);
			pStatusList.add(ProjectStatusEnum.EXPIRE);
			pStatusList.add(ProjectStatusEnum.OVERDUE);
			pStatusList.add(ProjectStatusEnum.SELL_FINISH);
			pStatusList.add(ProjectStatusEnum.FINISH);
			pStatusList.add(ProjectStatusEnum.RECOVERY);
			order.setStatusList(pStatusList);
			QueryBaseBatchResult<ProjectInfo> batchResult = projectCreditConditionServiceClient
				.queryProjectAndCredit(order);
			List<ProjectInfo> list = batchResult.getPageList();
			for (ProjectInfo info : list) {
				JSONObject json = new JSONObject();
				//				List<ProjectCreditConditionInfo> listInfo = new ArrayList<ProjectCreditConditionInfo>();
				//				listInfo = projectCreditConditionServiceClient
				//					.findProjectCreditConditionByProjectCodeAndStatus(info.getProjectCode(),
				//						CheckStatusEnum.NOT_APPLY.code());
				
				ProjectCreditConditionQueryOrder creditConditionQueryOrder = new ProjectCreditConditionQueryOrder();
				creditConditionQueryOrder.setProjectCode(info.getProjectCode());
				creditConditionQueryOrder.setIsConfirm(BooleanEnum.NO.code());
				creditConditionQueryOrder.setContractNo("IS");
				creditConditionQueryOrder.setPageSize(9999);
				List<String> status = Lists.newArrayList();
				status.add(CreditCheckStatusEnum.NOT_APPLY.code());
				//				status.add(CreditCheckStatusEnum.CHECK_NOT_PASS.code());
				status.add(CreditCheckStatusEnum.CHECK_PASS.code());
				creditConditionQueryOrder.setStatusList(status);//未落实
				QueryBaseBatchResult<ProjectCreditConditionInfo> creditConditionBatch = projectCreditConditionServiceClient
					.queryCreditCondition(creditConditionQueryOrder);
				List<ProjectCreditConditionInfo> listInfo = Lists.newArrayList();
				if (creditConditionBatch != null) {
					listInfo = creditConditionBatch.getPageList();
				}
				//未落实长度
				int size = 0;
				if (listInfo != null) {
					size = listInfo.size();
				}
				json.put("notCreditSize", size);
				json.put("projectCode", info.getProjectCode());
				json.put("projectName", info.getProjectName());
				json.put("customerId", info.getCustomerId());
				json.put("customerName", info.getCustomerName());
				//				json.put("institutionId", info.getInstitutionId());//机构信息
				//				json.put("institutionTypeName", info.getInstitutionName());
				//				json.put("institutionName", info.getInstitutionName());
				json.put("contractNo", info.getContractNo());//合同
				if (info.getCustomerType() != null) {
					json.put("customerType", info.getCustomerType().code());
				}
				//				json.put("amount",
				//					NumberUtil.format(MoneyUtil.getMoneyByw(info.getAmount()), "0.00"));
				json.put("amount", info.getAmount().toStandardString());
				json.put("busiType", info.getBusiType());
				json.put("busiTypeName", info.getBusiTypeName());
				
				if (info.getTimeUnit() != null) {
					json.put("timeLimit", info.getTimeLimit() + info.getTimeUnit().viewName());
				} else {
					json.put("timeLimit", "-");
				}
				dataList.add(json);
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
	 * 获取授信落实条件审核通过的项目信息
	 * @param projectCode
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("loadProjectCreditConditionPass.json")
	@ResponseBody
	public JSONObject loadProjectCreditConditionPass(ProjectCreditConditionQueryOrder order,
														Model model) {
		String tipPrefix = "查询落实条件通过的项目信息";
		JSONObject result = new JSONObject();
		try {
			if (order == null)
				order = new ProjectCreditConditionQueryOrder();
			//			order.setStatus(CheckStatusEnum.CHECK_PASS.code());
			order.setIsConfirm(BooleanEnum.IS.code());
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			
			QueryBaseBatchResult<ProjectCreditConditionInfo> batchResult = projectCreditConditionServiceClient
				.queryCreditCondition(order);
			String projectCode = order.getProjectCode();
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (ProjectCreditConditionInfo creditInfo : batchResult.getPageList()) {
					JSONObject json = new JSONObject();
					
					json.put("itemDesc", creditInfo.getItemDesc());
					json.put("confirmManName", creditInfo.getConfirmManName());
					json.put("confirmDate", DateUtil.dtSimpleFormat(creditInfo.getConfirmDate()));
					dataList.add(json);
				}
			}
			data.put("marginAmount",
				projectCreditConditionServiceClient.findMarginAmountByProjectCode(projectCode));//保证金额金额
			FCapitalAppropriationApplyQueryOrder queryOrder = new FCapitalAppropriationApplyQueryOrder();
			queryOrder.setProjectCode(projectCode);
			queryOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
			queryOrder.setPageSize(9999L);
			List<String> appropriateReasonList = new ArrayList<String>();
			appropriateReasonList.add(PaymentMenthodEnum.CUSTOMER_DEPOSIT_REFUND.code());
			queryOrder.setAppropriateReasonList(appropriateReasonList);
			QueryBaseBatchResult<FCapitalAppropriationApplyInfo> batchResult1 = fCapitalAppropriationApplyServiceClient
				.query(queryOrder);
			List<FCapitalAppropriationApplyInfo> listApplyInfo = batchResult1.getPageList();
			Money customerDeposit = Money.zero();//保证金划回
			for (FCapitalAppropriationApplyInfo fCapitalAppropriationApplyInfo : listApplyInfo) {
				customerDeposit = customerDeposit.add(fCapitalAppropriationApplyInfo
					.getAppropriateAmount());
			}
			data.put("customerDeposit", customerDeposit);
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
	 * 决策机构
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("decisionInstitution.json")
	@ResponseBody
	public JSONObject decisionInstitution(DecisionInstitutionQueryOrder order, Model model) {
		String tipPrefix = "决策机构";
		JSONObject result = new JSONObject();
		try {
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			if (order == null) {
				order = new DecisionInstitutionQueryOrder();
			}
			QueryBaseBatchResult<DecisionInstitutionInfo> batchResult = decisionInstitutionServiceClient
				.query(order);
			
			List<DecisionInstitutionInfo> list = batchResult.getPageList();
			for (DecisionInstitutionInfo info : list) {
				JSONObject json = new JSONObject();
				json.put("institutionId", info.getInstitutionId());
				json.put("institutionName", info.getInstitutionName());
				json.put("institutionMembers", info.getInstitutionMembers());
				json.put("decisionMembers", info.getDecisionMembers());
				dataList.add(json);
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
	 * 选择评估公司
	 * @param order
	 * @return
	 */
	@RequestMapping("loadAssessCompany.json")
	@ResponseBody
	public JSONObject loadAssessCompany(AssessCompanyQueryOrder order) {
		String tipPrefix = "选择评估公司";
		JSONObject result = new JSONObject();
		try {
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			QueryBaseBatchResult<AssessCompanyInfo> batchResult = assessCompanyServiceClient
				.query(order);
			List<AssessCompanyInfo> list = batchResult.getPageList();
			for (AssessCompanyInfo info : list) {
				JSONObject json = new JSONObject();
				json.put("cityCode", info.getCityCode());
				json.put("cityName", info.getCity());
				json.put("companyId", info.getCompanyId());
				json.put("companyName", info.getCompanyName());
				json.put("region", info.getRegion().getMessage());
				json.put("contactName", info.getContactName());
				json.put("contactNumber", info.getContactNumber());
				dataList.add(json);
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
	 * 获取承销项目信息
	 * @param projectCode
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("loadConsignmentSalesProjectData.json")
	@ResponseBody
	public JSONObject loadConsignmentSalesProjectData(ProjectQueryOrder order) {
		SessionLocal session = ShiroSessionUtils.getSessionLocal();
		String tipPrefix = "查询承销项目信息";
		JSONObject result = new JSONObject();
		try {
			if (order == null)
				order = new ProjectQueryOrder();
			
			//业务类型
			//			List<String> busiTypeList = Lists.newArrayList();
			//			busiTypeList.add("5");
			//			order.setBusiTypeList(busiTypeList);
			order.setBusiType("5");
			order.setIsContinue(BooleanEnum.NO);
			
			order.setBusiManagerId(session.getUserId()); //查询当前业务经理的项目
			setSessionLocalInfo2Order(order);
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			List<ProjectStatusEnum> pStatusList = Lists.newArrayList();
			//除开暂缓的项目
			pStatusList.add(ProjectStatusEnum.NORMAL);
			pStatusList.add(ProjectStatusEnum.FAILED);
			pStatusList.add(ProjectStatusEnum.EXPIRE);
			pStatusList.add(ProjectStatusEnum.OVERDUE);
			pStatusList.add(ProjectStatusEnum.RECOVERY);
			order.setStatusList(pStatusList);
			QueryBaseBatchResult<ProjectInfo> batchResult = projectIssueInformationServiceClient
				.queryBond(order);
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (ProjectInfo info : batchResult.getPageList()) {
					JSONObject json = new JSONObject();
					json.put("projectCode", info.getProjectCode());
					json.put("projectName", info.getProjectName());
					json.put("customerId", info.getCustomerId());
					json.put("customerName", info.getCustomerName());
					json.put("amount", info.getAmount().toStandardString());
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
	 * 获取发债项目信息
	 * @param projectCode
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("loadBondProjectData.json")
	@ResponseBody
	public JSONObject loadBondProjectData(ProjectQueryOrder order) {
		SessionLocal session = ShiroSessionUtils.getSessionLocal();
		String tipPrefix = "查询发债项目信息";
		JSONObject result = new JSONObject();
		try {
			if (order == null)
				order = new ProjectQueryOrder();
			
			order.setBusiType("12");
			order.setIsContinue(BooleanEnum.NO);
			order.setBusiManagerId(session.getUserId()); //查询当前业务经理的项目
			setSessionLocalInfo2Order(order);
			
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			List<ProjectStatusEnum> pStatusList = Lists.newArrayList();
			//除开暂缓的项目
			pStatusList.add(ProjectStatusEnum.NORMAL);
			pStatusList.add(ProjectStatusEnum.FAILED);
			pStatusList.add(ProjectStatusEnum.EXPIRE);
			pStatusList.add(ProjectStatusEnum.OVERDUE);
			pStatusList.add(ProjectStatusEnum.RECOVERY);
			order.setStatusList(pStatusList);
			QueryBaseBatchResult<ProjectInfo> batchResult = projectIssueInformationServiceClient
				.queryBond(order);
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (ProjectInfo info : batchResult.getPageList()) {
					
					JSONObject json = new JSONObject();
					json.put("projectCode", info.getProjectCode());
					json.put("projectName", info.getProjectName());
					json.put("customerId", info.getCustomerId());
					json.put("customerName", info.getCustomerName());
					//					json.put("amount", MoneyUtil.getMoneyByw(info.getAmount()));
					json.put("amount", info.getAmount().toStandardString());
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
	 * 发债类项目 同意发行通知书
	 * @param projectCode
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("loadBondProjectDataByNotice.json")
	@ResponseBody
	public JSONObject loadBondProjectDataByNotice(ProjectQueryOrder order) {
		SessionLocal session = ShiroSessionUtils.getSessionLocal();
		String tipPrefix = "查询发债项目信息";
		JSONObject result = new JSONObject();
		try {
			if (order == null)
				order = new ProjectQueryOrder();
			
			order.setBusiType("12");
			//			order.setIsContinue(BooleanEnum.NO);
			order.setBusiManagerId(session.getUserId()); //查询当前业务经理的项目
			setSessionLocalInfo2Order(order);
			
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			List<ProjectStatusEnum> pStatusList = Lists.newArrayList();
			//除开暂缓的项目
			pStatusList.add(ProjectStatusEnum.NORMAL);
			pStatusList.add(ProjectStatusEnum.FAILED);
			pStatusList.add(ProjectStatusEnum.EXPIRE);
			pStatusList.add(ProjectStatusEnum.OVERDUE);
			pStatusList.add(ProjectStatusEnum.RECOVERY);
			order.setStatusList(pStatusList);
			QueryBaseBatchResult<ProjectInfo> batchResult = consentIssueNoticeServiceClient
				.queryConsentIssueNotice(order);
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (ProjectInfo info : batchResult.getPageList()) {
					
					JSONObject json = new JSONObject();
					json.put("projectCode", info.getProjectCode());
					json.put("projectName", info.getProjectName());
					json.put("customerId", info.getCustomerId());
					json.put("customerName", info.getCustomerName());
					//					json.put("amount", MoneyUtil.getMoneyByw(info.getAmount()));
					json.put("amount", info.getAmount().toStandardString());
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
	 * 退费申请 选择(收费通知)
	 * @param projectCode
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("loadRefundApplicationByCharge.json")
	@ResponseBody
	public JSONObject loadRefundApplicationByCharge(ProjectQueryOrder order) {
		SessionLocal session = ShiroSessionUtils.getSessionLocal();
		String tipPrefix = "查询可退费申请项目信息";
		JSONObject result = new JSONObject();
		try {
			if (order == null)
				order = new ProjectQueryOrder();
			order.setBusiManagerId(session.getUserId()); //查询当前业务经理的项目
			setSessionLocalInfo2Order(order);
			
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			QueryBaseBatchResult<ProjectInfo> batchResult = refundApplicationServiceClient
				.queryRefundApplicationByCharge(order);
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (ProjectInfo info : batchResult.getPageList()) {
					
					JSONObject json = new JSONObject();
					json.put("projectCode", info.getProjectCode());
					json.put("projectName", info.getProjectName());
					json.put("customerId", info.getCustomerId());
					json.put("customerName", info.getCustomerName());
					//					json.put("amount", MoneyUtil.getMoneyByw(info.getAmount()));
					json.put("amount", info.getAmount().toStandardString());
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
	
	private JSONObject toJsonObj(Object object) {
		String jsonString = JSON.toJSONString(object);
		JSONObject json = JSON.parseObject(jsonString);
		return json;
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
			
			if (StringUtil.equals("YES", request.getParameter("setAuthCondition"))) {
				setSessionLocalInfo2Order(queryOrder);
			}
			
			if (sessionLocal != null
				&& (DataPermissionUtil.isBusiManager() || (DataPermissionUtil.isLegalManager() && StringUtil
					.equals("my", request.getParameter("select"))))) {
				queryOrder.setBusiManagerId(sessionLocal.getUserId());
				//				if ("YES".equals(request.getParameter("isAdd"))) {
				//当是业务经理增加表单的时候
				//					queryOrder.setBusiManagerbId(sessionLocal.getUserId());
				//				}
			}
			
			if (null != request.getParameter("busiManagerId")) {
				queryOrder.setBusiManagerId(NumberUtil.parseLong(request
					.getParameter("busiManagerId")));
			}
			
			if (null != request.getParameter("fromStamp")
				&& "IS".equals(request.getParameter("fromStamp"))) {//用印
				if (DataPermissionUtil.isRiskSecretary()) {//风控委秘书查所有项目
					queryOrder.setBusiManagerId(0);
				}
			}
			
			//风险处置
			if (StringUtil.equals("YES", request.getParameter("fromRiskCouncil"))) {
				queryOrder.setBusiManagerId(0);
				queryOrder.setDeptIdList(null);
				queryOrder.setLoginUserId(sessionLocal.getUserId());
				List<ProjectRelatedUserTypeEnum> relatedRoleList = Lists.newArrayList();
				relatedRoleList.add(ProjectRelatedUserTypeEnum.RISK_TEAM_MEMBER);
				queryOrder.setRelatedRoleList(relatedRoleList);
			}
			if (StringUtil.isNotBlank(request.getParameter("isApprovalDel"))) {
				queryOrder.setIsApprovalDel(BooleanEnum.getByCode(request
					.getParameter("isApprovalDel")));
			}
			
			queryOrder.setPhases(ProjectPhasesEnum.getByCode(request.getParameter("phases")));
			queryOrder.setStatus(ProjectStatusEnum.getByCode(request.getParameter("status")));
			queryOrder.setPhasesStatus(ProjectPhasesStatusEnum.getByCode(request
				.getParameter("phasesStatus")));
			queryOrder.setCustomerType(CustomerTypeEnum.getByCode(request
				.getParameter("customerType")));
			queryOrder.setHasContract(BooleanEnum.getByCode(request.getParameter("hasContract")));
			queryOrder.setSortCol("p.raw_update_time");
			queryOrder.setSortOrder("DESC");
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
			String phasesStatusStrList = request.getParameter("phasesStatusList");
			if (StringUtil.isNotBlank(phasesStatusStrList)) {
				List<ProjectPhasesStatusEnum> phasesStatusList = new ArrayList<>();
				String[] phases = phasesStatusStrList.split(",");
				for (String s : phases) {
					ProjectPhasesStatusEnum e = ProjectPhasesStatusEnum.getByCode(s);
					if (null != e) {
						phasesStatusList.add(e);
					}
				}
				queryOrder.setPhasesStatusList(phasesStatusList);
			}
			String statusStrList = request.getParameter("statusList");
			if (StringUtil.isNotBlank(statusStrList)) {
				List<ProjectStatusEnum> statusList = new ArrayList<>();
				String[] phases = statusStrList.split(",");
				for (String s : phases) {
					ProjectStatusEnum e = ProjectStatusEnum.getByCode(s);
					if (null != e) {
						statusList.add(e);
					}
				}
				queryOrder.setStatusList(statusList);
			}
			
			String busiTypes = request.getParameter("busiTypes");
			if (StringUtil.isNotBlank(busiTypes)) {
				List<String> busiTypeList = new ArrayList<>();
				String[] types = busiTypes.split(",");
				for (String s : types) {
					if (StringUtil.isNotEmpty(s)) {
						busiTypeList.add(s.trim());
					}
				}
				queryOrder.setBusiTypeList(busiTypeList);
			}
			
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			QueryBaseBatchResult<ProjectInfo> batchResult = projectServiceClient
				.queryProject(queryOrder);
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (ProjectInfo info : batchResult.getPageList()) {
					JSONObject json = new JSONObject();
					json.put("projectCode", info.getProjectCode());
					json.put("projectName", info.getProjectName());
					json.put("customerId", info.getCustomerId());
					json.put("customerName", info.getCustomerName());
					json.put("busiManagerId", info.getBusiManagerId());
					json.put("busiManagerName", info.getBusiManagerName());
					json.put("deptCode", info.getDeptCode());
					json.put("phases", info.getPhases().message());
					json.put("status", info.getStatus().message());
					json.put("timeLimit", info.getTimeUnit() == null ? "-" : info.getTimeLimit()
																				+ info
																					.getTimeUnit()
																					.viewName());
					if (ProjectUtil.isInnovativeProduct(info.getBusiType())) {
						json.put("amount", "");
						json.put("amountW", "");
					} else {
						json.put("amount", info.getAmount().toStandardString());
						json.put("amountW",
							NumberUtil.format(MoneyUtil.getMoneyByw(info.getAmount()), "0.00"));
					}
					
					json.put("busiType", info.getBusiType());
					json.put("busiTypeName", info.getBusiTypeName());
					//已代偿金额
					json.put("compAmount",
						info.getCompInterestAmount().add(info.getCompPrincipalAmount())
							.toStandardString());
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
	 * 档案选择项目
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("loadFileProjects.json")
	public Object loadFileProjects(FileQueryOrder order) {
		JSONObject result = new JSONObject();
		String tipPrefix = "档案选择项目信息";
		
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			//是否信惠
			boolean isXinhui = DataPermissionUtil.isXinHuiBusiManager();
			//是否业务经理
			boolean isBusiManager = DataPermissionUtil.isBusiManager();
			//是否法务经理
			boolean isLegalManager = DataPermissionUtil.isLegalManager();
			//是否风险经理
			boolean isRiskManager = DataPermissionUtil.isRiskManager();
			//是否放款档案岗
			boolean isLoanFile = DataPermissionUtil.isLoanFile();
			if (isXinhui) {//信惠立项审核通过后可以归档
				List<ProjectPhasesEnum> phasesList = Lists.newArrayList();
				phasesList.add(ProjectPhasesEnum.INVESTIGATING_PHASES);
				phasesList.add(ProjectPhasesEnum.COUNCIL_PHASES);
				phasesList.add(ProjectPhasesEnum.CONTRACT_PHASES);
				phasesList.add(ProjectPhasesEnum.LOAN_USE_PHASES);
				phasesList.add(ProjectPhasesEnum.FUND_RAISING_PHASES);
				phasesList.add(ProjectPhasesEnum.AFTERWARDS_PHASES);
				phasesList.add(ProjectPhasesEnum.RECOVERY_PHASES);
				phasesList.add(ProjectPhasesEnum.FINISH_PHASES);
				order.setIsHasLoanAmount(BooleanEnum.NO);
				order.setPhasesList(phasesList);
			} else {
				order.setIsHasLoanAmount(BooleanEnum.NO);
			}
			if (isBusiManager&&!isLoanFile)
				order.setBusiManagerId(sessionLocal.getUserId());
			if (isLegalManager&&!isLoanFile)
				order.setLegalManagerId(sessionLocal.getUserId());
			if (isRiskManager&&!isLoanFile)
				order.setRiskManagerId(sessionLocal.getUserId());
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			QueryBaseBatchResult<ProjectInfo> batchResult = fileServiceClient
				.querySelectableProject(order);
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (ProjectInfo info : batchResult.getPageList()) {
					JSONObject json = new JSONObject();
					json.put("projectCode", info.getProjectCode());
					json.put("projectName", info.getProjectName());
					json.put("customerId", info.getCustomerId());
					json.put("customerName", info.getCustomerName());
					json.put("busiManagerId", info.getBusiManagerId());
					json.put("busiManagerName", info.getBusiManagerName());
					json.put("deptCode", info.getDeptCode());
					json.put("phases", info.getPhases().message());
					json.put("status", info.getStatus().message());
					json.put("timeLimit", info.getTimeUnit() == null ? "-" : info.getTimeLimit()
																				+ info
																					.getTimeUnit()
																					.viewName());
					json.put("amount", info.getAmount().toStandardString());
					json.put("amountW",
						NumberUtil.format(MoneyUtil.getMoneyByw(info.getAmount()), "0.00"));
					json.put("busiType", info.getBusiType());
					json.put("busiTypeName", info.getBusiTypeName());
					//已代偿金额
					json.put("compAmount",
						info.getCompInterestAmount().add(info.getCompInterestAmount()));
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
	
	@ResponseBody
	@RequestMapping("queryInvestigationProjects.json")
	public Object queryInvestigationProjects(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String tipPrefix = "查询项目信息";
		
		try {
			InvestigationQueryOrder queryOrder = new InvestigationQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			String fstatus = request.getParameter("fstatus");
			if (StringUtil.isNotBlank(fstatus)) {
				List<String> status = new ArrayList<>();
				for (String s : fstatus.split(",")) {
					status.add(s);
				}
				queryOrder.setFormStatusList(status);
			}
			//			QueryBaseBatchResult<InvestigationInfo> batchResult = new QueryBaseBatchResult<>();
			if (queryOrder.getCustomerId() > 0 && StringUtil.isNotBlank(queryOrder.getBusiType())) {
				String bType = "";
				if (ProjectUtil.isUnderwriting(queryOrder.getBusiType())) {
					bType = "UNDERWRITING";
				} else if (ProjectUtil.isLitigation(queryOrder.getBusiType())) {
					bType = "LITIGATION";
					//				} else if (ProjectUtil.isBankFinancing(queryOrder.getBusiType())) {
					//					bType = "BANK_FINANCING";
				} else {
					bType = "GUARANTEE";
				}
				queryOrder.setbType(bType);
				queryOrder.setBusiType("");
			}
			QueryBaseBatchResult<InvestigationInfo> batchResult = investigationServiceClient
				.queryInvestigation(queryOrder);
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (InvestigationInfo info : batchResult.getPageList()) {
					JSONObject json = new JSONObject();
					json.put("copiedFormId", info.getFormId());
					json.put("projectCode", info.getProjectCode());
					json.put("projectName", info.getProjectName());
					json.put("customerId", info.getCustomerId());
					json.put("customerName", info.getCustomerName());
					json.put("amount", info.getAmount().toStandardString());
					json.put("amountW",
						NumberUtil.format(MoneyUtil.getMoneyByw(info.getAmount()), "0.00"));
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
	
	@ResponseBody
	@RequestMapping("queryAfterwards.json")
	public Object queryAfterwards(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String tipPrefix = "查询保后项目信息";
		
		try {
			AfterwordsCheckQueryOrder queryOrder = new AfterwordsCheckQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			queryOrder.setIsLatest(BooleanEnum.YES.code());
			QueryBaseBatchResult<AfterwardsCheckInfo> batchResult = new QueryBaseBatchResult<>();
			if (queryOrder.getCustomerId() > 0 && StringUtil.isNotBlank(queryOrder.getEdition())) {
				batchResult = afterwardsCheckServiceClient.list(queryOrder);
			}
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (AfterwardsCheckInfo info : batchResult.getPageList()) {
					JSONObject json = new JSONObject();
					json.put("formId", info.getFormId());
					json.put("projectCode", info.getProjectCode());
					json.put("projectName", info.getProjectName());
					json.put("customerId", info.getCustomerId());
					json.put("customerName", info.getCustomerName());
					json.put("amount", info.getAmount().toStandardString());
					json.put("amountW",
						NumberUtil.format(MoneyUtil.getMoneyByw(info.getAmount()), "0.00"));
					json.put("busiType", info.getBusiType());
					json.put("busiTypeName", info.getBusiTypeName());
					json.put("edition", info.getEdition().code());
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
	
	@ResponseBody
	@RequestMapping("queryAfterwardsEdition.json")
	public Object queryAfterwardsEdition(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String tipPrefix = "查询保后项目版本信息";
		
		try {
			FAfterwardsCheckQueryOrder queryOrder = new FAfterwardsCheckQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			queryOrder.setPageNumber(1L);
			queryOrder.setPageSize(99L);
			QueryBaseBatchResult<FAfterwardsCheckInfo> batchResult = afterwardsCheckServiceClient
				.queryAfterwardsCheckReport(queryOrder);
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (FAfterwardsCheckInfo info : batchResult.getPageList()) {
					JSONObject json = new JSONObject();
					json.put("formId", info.getFormId());
					json.put("round", info.getRoundYear() + "第" + info.getRoundTime() + "期");
					json.put("projectCode", info.getProjectCode());
					json.put("projectName", info.getProjectName());
					json.put("customerId", info.getCustomerId());
					json.put("customerName", info.getCustomerName());
					json.put("amount", info.getAmount().toStandardString());
					json.put("amountW",
						NumberUtil.format(MoneyUtil.getMoneyByw(info.getAmount()), "0.00"));
					json.put("busiType", info.getBusiType());
					json.put("busiTypeName", info.getBusiTypeName());
					json.put("edition", info.getEdition().code());
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
	
	@RequestMapping("downLoad.htm")
	public void downLoad(HttpServletRequest request, HttpServletResponse response, long id,
							String fileName, String path) {
		BufferedOutputStream output = null;
		BufferedInputStream input = null;
		
		try {
			File file = null;
			if (StringUtil.isNotEmpty(path)) {
				file = new File(path);
			}
			// 允许自定义上传ｆｉｌｅＮａｍｅ和　ｐａｔｈ,　如果有id 依然以id查出的属性为优先。
			if (id > 0) {
				CommonAttachmentResult result = commonAttachmentServiceClient.findById(id);
				if (null != result && null != result.getAttachmentInfo()) {
					CommonAttachmentInfo info = result.getAttachmentInfo();
					fileName = info.getFileName();
					file = new File(info.getFilePhysicalPath());
				} else {
					return;
				}
			}
			response.setHeader("Content-disposition",
				"attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
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
			
		} catch (Exception e) {
			//异常自己捕捉
			logger.error("下载附件异常：" + e);
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
	
	@RequestMapping("queryPledgeProperties.json")
	@ResponseBody
	public JSONObject queryPledgeProperties() {
		String tipPrefix = "查询抵押物性质";
		JSONObject result = new JSONObject();
		try {
			Map<PledgeTypeEnum, PledgePropertyEnum[]> map = new LinkedHashMap<>();
			map.put(PledgeTypeEnum.LAND, new PledgePropertyEnum[] { PledgePropertyEnum.SELL,
																	PledgePropertyEnum.TRANSFER,
																	PledgePropertyEnum.RESERVE });
			map.put(PledgeTypeEnum.HOUSE, new PledgePropertyEnum[] { PledgePropertyEnum.HOUSE,
																	PledgePropertyEnum.RESIDENTIAL,
																	PledgePropertyEnum.BUSINESS });
			map.put(PledgeTypeEnum.INVENTORY,
				new PledgePropertyEnum[] { PledgePropertyEnum.SUPERVISE,
											PledgePropertyEnum.UN_SUPERVISE });
			map.put(PledgeTypeEnum.EQUIPMENT,
				new PledgePropertyEnum[] { PledgePropertyEnum.GENERAL, PledgePropertyEnum.SPECIAL });
			map.put(PledgeTypeEnum.FUNDS, new PledgePropertyEnum[] { PledgePropertyEnum.SIGNED,
																	PledgePropertyEnum.UN_SIGNED });
			map.put(PledgeTypeEnum.STOCK, new PledgePropertyEnum[] { PledgePropertyEnum.CIRCULATE,
																	PledgePropertyEnum.LIMITED });
			
			result.put("success", true);
			result.put("message", "成功");
			result.put("data", fillPledgeType(map));
			return result;
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		
		return result;
	}
	
	private JSONObject fillPledgeType(Map<PledgeTypeEnum, PledgePropertyEnum[]> map) {
		JSONObject obj = new JSONObject();
		for (Map.Entry<PledgeTypeEnum, PledgePropertyEnum[]> entry : map.entrySet()) {
			JSONArray array = new JSONArray();
			for (PledgePropertyEnum property : entry.getValue()) {
				JSONObject json = new JSONObject();
				json.put("name", property.message());
				json.put("code", property.code());
				array.add(json);
			}
			obj.put(entry.getKey().code(), array);
		}
		
		return obj;
	}
	
	/**
	 * 资产-资产转让
	 * @param order
	 * @return
	 */
	@RequestMapping("loanAssetsTransfer.json")
	@ResponseBody
	public JSONObject loanAssetsTransfer(ProjectQueryOrder order) {
		
		SessionLocal session = ShiroSessionUtils.getSessionLocal();
		String tipPrefix = "查询可资产转让项目信息";
		JSONObject result = new JSONObject();
		try {
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			
			List<ProjectStatusEnum> status = Lists.newArrayList();
			
			status.add(ProjectStatusEnum.RECOVERY);
			
			order.setStatusList(status);
			
			order.setHasCompensatoryAmount(BooleanEnum.IS);
			order.setIsReleasing(ReleaseProjectStatusEnum.RELEASING.code());//排除正在解保中的项目
			order.setBusiManagerId(session.getUserId()); //查询当前业务经理的项目
			setSessionLocalInfo2Order(order);
			order.setDockFormType(SubsystemDockFormTypeEnum.ASSETS_TRANSFER.code());//资产转让
			if (StringUtil.isEmpty(order.getSortCol())) {
				order.setSortCol("p.raw_update_time");
				order.setSortOrder("DESC");
			}
			QueryBaseBatchResult<ProjectSimpleDetailInfo> batchResult = projectServiceClient
				.queryProjectSimpleDetail(order);
			List<ProjectSimpleDetailInfo> list = batchResult.getPageList();
			for (ProjectSimpleDetailInfo info : list) {
				JSONObject json = new JSONObject();
				json.put("projectCode", info.getProjectCode());
				json.put("projectName", info.getProjectName());
				json.put("customerId", info.getCustomerId());
				json.put("customerName", info.getCustomerName());
				//				json.put("amount", info.getAmount().toString());
				json.put("amount", info.getAmount().toStandardString());
				json.put("busiType", info.getBusiType());
				json.put("busiTypeName", info.getBusiTypeName());
				json.put("compensatoryAmount",
					info.getCompInterestAmount().add(info.getCompPrincipalAmount()).toString());
				dataList.add(json);
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
	 * 资产-资产受让
	 * @param order
	 * @return
	 */
	@RequestMapping("loanAssetsTransferee.json")
	@ResponseBody
	public JSONObject loanAssetsTransferee(FAssetsTransferApplicationQueryOrder order) {
		
		SessionLocal session = ShiroSessionUtils.getSessionLocal();
		String tipPrefix = "查询可资产受让的项目信息";
		JSONObject result = new JSONObject();
		try {
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			order.setBusiManagerId(session.getUserId()); //查询当前业务经理的项目
			setSessionLocalInfo2Order(order);
			order.setFormStatus(FormStatusEnum.APPROVAL.code());//审核通过的项目
			//清收状态 -未清收
			order.setLiquidaterStatus(LiquidaterStatusEnum.NO_LIQUIDATER.code());
			//是否清收
			order.setIsTrusteeLiquidate(BooleanEnum.IS.code());
			//清收时间 今天之后
			order.setLiquidateTimeEnd(DateUtil.dtSimpleFormat(new Date()));
			if (StringUtil.isEmpty(order.getSortCol())) {
				order.setSortCol("p.raw_update_time");
				order.setSortOrder("DESC");
			}
			QueryBaseBatchResult<FAssetsTransferApplicationInfo> batchResult = assetsTransfereeApplicationServiceClient
				.queryTransferProject(order);
			List<FAssetsTransferApplicationInfo> list = batchResult.getPageList();
			for (FAssetsTransferApplicationInfo info : list) {
				JSONObject json = new JSONObject();
				json.put("projectCode", info.getProjectCode());
				json.put("projectName", info.getProjectName());
				json.put("transferCompany", info.getTransfereeCompany());
				json.put("isTrusteeLiquidate", info.getIsTrusteeLiquidate() == null ? "-" : info
					.getIsTrusteeLiquidate().message());
				json.put(
					"liquidateTime",
					info.getLiquidateTime() == null ? "-" : DateUtil.dtSimpleFormat(info
						.getLiquidateTime()));
				json.put("liquidatePrice", info.getLiquidaterPrice() == null ? "-" : info
					.getLiquidaterPrice().toStandardString());
				dataList.add(json);
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
	 * 资产-评估公司申请单(根据项目编号加载客户信息)
	 * @param order
	 * @return
	 */
	@RequestMapping("loanProjectForCompany.json")
	@ResponseBody
	public JSONObject loanProjectForCompany(ProjectQueryOrder order) {
		
		SessionLocal session = ShiroSessionUtils.getSessionLocal();
		String tipPrefix = "查询所有项目信息";
		JSONObject result = new JSONObject();
		try {
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			
			List<ProjectStatusEnum> status = Lists.newArrayList();
			
			order.setStatusList(status);
			//order.setStatus(ProjectStatusEnum.NORMAL);
			
			List<ProjectPhasesEnum> phasesList = new ArrayList<>();
			//phasesList.add(ProjectPhasesEnum.INVESTIGATING_PHASES);//尽调阶段
			
			order.setPhasesList(phasesList);
			List<String> busiType = Lists.newArrayList();
			
			busiType.add("1");
			busiType.add("4");
			busiType.add("221");
			busiType.add("231");
			busiType.add("3");
			busiType.add("511");
			busiType.add("6");
			busiType.add("7");
			order.setBusiTypeList(busiType);
			order.setBusiManagerId(session.getUserId()); //查询当前业务经理的项目
			setSessionLocalInfo2Order(order);
			if (StringUtil.isEmpty(order.getSortCol())) {
				order.setSortCol("p.raw_update_time");
				order.setSortOrder("DESC");
			}
			QueryBaseBatchResult<ProjectSimpleDetailInfo> batchResult = projectServiceClient
				.queryProjectSimpleDetail(order);
			List<ProjectSimpleDetailInfo> list = batchResult.getPageList();
			for (ProjectSimpleDetailInfo info : list) {
				JSONObject json = new JSONObject();
				json.put("projectCode", info.getProjectCode());
				json.put("projectName", info.getProjectName());
				json.put("customerId", info.getCustomerId());
				if (info.getCustomerId() > 0) {
					CompanyCustomerInfo companyCustomerInfo = companyCustomerClient.queryByUserId(
						info.getCustomerId(), null);
					if (companyCustomerInfo != null) {
						json.put("customerAddress", companyCustomerInfo.getAddress());//客户地址
						
						json.put("countryName", companyCustomerInfo.getCountryName());//所属区域 - 国家名称
						json.put("countryCode", companyCustomerInfo.getCountryCode());//所属区域 - 国家编码
						
						json.put("provinceName", companyCustomerInfo.getProvinceName());//所属区域 - 省名称
						json.put("provinceCode", companyCustomerInfo.getProvinceCode());//所属区域 - 省编码
						
						json.put("cityName", companyCustomerInfo.getCityName());//所属区域 - 市名称
						json.put("cityCode", companyCustomerInfo.getCityCode());//所属区域 - 市编码
						
						json.put("countyName", companyCustomerInfo.getCountyName());//所属区域 - 地区名称
						json.put("countyCode", companyCustomerInfo.getCountyCode());//所属区域 - 地区编码
					}
				}
				json.put("projectName", info.getProjectName()); //項目名称
				json.put("customerName", info.getCustomerName()); //客户名称
				json.put("customerType", info.getCustomerType() == null ? "" : info
					.getCustomerType().code());
				//				json.put("amount", info.getAmount().toString());
				json.put("amount", info.getAmount().toStandardString());
				json.put("busiType", info.getBusiType());
				json.put("busiTypeName", info.getBusiTypeName());
				json.put("compensatoryAmount",
					info.getCompInterestAmount().add(info.getCompPrincipalAmount())
						.toStandardString());
				dataList.add(json);
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
	 * 资产-评估公司加载
	 * @param order
	 * @return
	 */
	@RequestMapping("loanAssetsCompany.json")
	@ResponseBody
	public JSONObject loanAssetsCompany(AssetsAssessCompanyQueryOrder order) {
		
		//SessionLocal session = ShiroSessionUtils.getSessionLocal();
		String tipPrefix = "查询所有评估公司";
		JSONObject result = new JSONObject();
		try {
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			order.setStatus(AssessCompanyStatusEnum.NORMAL.code());
			QueryBaseBatchResult<AssetsAssessCompanyInfo> batchResult = assetsAssessCompanyServiceClient
				.query(order);
			List<AssetsAssessCompanyInfo> list = batchResult.getPageList();
			for (AssetsAssessCompanyInfo info : list) {
				JSONObject json = new JSONObject();
				json.put("companyName", info.getCompanyName());//评估公司名称
				json.put("companyId", info.getId());//评估公司id
				json.put("countryName", info.getCountryName());//国家名称
				json.put("cityName", info.getCity() == null ? "--" : info.getCity());//城市
				json.put("address", info.getContactAddr());//地址
				json.put("contactInfos", info.getContactInfos());//联系人集合
				String temp = "";
				if (info.getQualityAssets() != null) {
					temp += "资产（" + info.getQualityAssets() + "）;";
				}
				if (info.getQualityHouse() != null) {
					temp += "房产（" + info.getQualityHouse() + "）;";
				}
				if (info.getQualityLand() != null) {
					temp += "土地（" + info.getQualityLand() + "）;";
				}
				json.put("quality", temp);//资质
				json.put("workSituation", info.getWorkSituation());//现场工作情况
				json.put("attachment", info.getAttachment());//附件齐全程度
				json.put("technicalLevel", info.getTechnicalLevel());//评估报告技术水平
				json.put("evaluationEfficiency", info.getEvaluationEfficiency());//评估效率
				json.put("cooperationSituation", info.getCooperationSituation());//合作情况
				json.put("serviceAttitude", info.getServiceAttitude());//服务态度
				dataList.add(json);
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
	
	//QueryBaseBatchResult<AssetSimpleInfo>
	@ResponseBody
	@RequestMapping("queryAssetSimple.json")
	public Object queryAssetSimple(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String tipPrefix = "查询资产信息";
		
		try {
			AssetQueryOrder queryOrder = new AssetQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			if (StringUtil.isNotBlank(request.getParameter("ids"))) {
				String[] ss = request.getParameter("ids").split(",");
				List<Long> ids = new ArrayList<>();
				for (String id : ss) {
					ids.add(NumberUtil.parseLong(id, 0L));
				}
				queryOrder.setExclusives(ids);
			}
			
			if (!DataPermissionUtil.isSystemAdministrator()
				&& !DataPermissionUtil.isCompanyLeader() && !DataPermissionUtil.isRiskSecretary()) { //系统管理员
				String projectCode = request.getParameter("projectCode");
				if (StringUtil.isNotEmpty(projectCode)) {
					ProjectRelatedUserInfo busiManager = projectRelatedUserServiceClient
						.getBusiManager(projectCode);
					if (busiManager != null) {
						queryOrder.setUserId(busiManager.getUserId());
						queryOrder.setUserAccount(busiManager.getUserAccount());
						queryOrder.setUserName(busiManager.getUserName());
					}
				} else {
					SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
					queryOrder.setUserId(sessionLocal.getUserId());
					queryOrder.setUserAccount(sessionLocal.getUserName());
					queryOrder.setUserName(sessionLocal.getRealName());
				}
			}
			
			JSONObject data = new JSONObject();
			QueryBaseBatchResult<AssetSimpleInfo> batchResult = pledgeAssetServiceClient
				.queryAssetSimple(queryOrder);
			if (batchResult != null && batchResult.isSuccess()) {
				data.put("pageCount", batchResult.getPageCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("totalCount", batchResult.getTotalCount());
				JSONArray pageList = new JSONArray();
				for (AssetSimpleInfo asset : batchResult.getPageList()) {
					JSONObject assetJson = new JSONObject();
					assetJson.put("assetsId", asset.getAssetsId());
					assetJson.put("typeId", asset.getTypeId());
					assetJson.put(
						"assetType",
						asset.getAssetType().substring(asset.getAssetType().lastIndexOf('-') + 1,
							asset.getAssetType().length()));
					assetJson.put("assetTypeDesc", asset.getAssetTypeDesc());
					assetJson.put("assetRemarkInfo", asset.getAssetRemarkInfo());
					assetJson.put("ownershipId", asset.getOwnershipId());
					assetJson.put("ownershipName", asset.getOwnershipName());
					assetJson.put("isCustomer", asset.getIsCustomer());
					assetJson.put("warrantNo", asset.getWarrantNo());
					assetJson.put("evaluationPrice", asset.getEvaluationPrice().getAmount());
					assetJson.put("status", asset.getStatus());
					assetJson.put("pledgeRate", asset.getPledgeRate());
					assetJson.put("mortgagePrice", asset.getMortgagePrice().getAmount());
					assetJson.put("rawAddTime", DateUtil.simpleFormat(asset.getRawAddTime()));
					pageList.add(assetJson);
				}
				data.put("pageList", pageList);
				result = toStandardResult(data, tipPrefix);
			} else {
				result = toStandardResult(null, tipPrefix);
			}
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		return result;
	}
	
	@RequestMapping("queryAssetRelationProject.json")
	@ResponseBody
	public Object queryAssetRelationProject(AssetRelationProjectQueryOrder order) {
		//SessionLocal session = ShiroSessionUtils.getSessionLocal();
		String tipPrefix = "查询资产关联的项目信息";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = null;
			order.setAssetsId(order.getAssetsId());
			QueryBaseBatchResult<AssetRelationProjectInfo> batchResult = pledgeAssetServiceClient
				.queryAssetRelationProject(order);
			if (batchResult != null && batchResult.isSuccess()) {
				data = new JSONObject();
				JSONArray dataList = new JSONArray();
				List<AssetRelationProjectInfo> list = batchResult.getPageList();
				for (AssetRelationProjectInfo info : list) {
					JSONObject json = new JSONObject();
					json.put("projectCode", info.getProjectCode());
					json.put("projectName", info.getProjectName());
					json.put("customerId", info.getCustomerId());
					json.put("customerName", info.getCustomerName());
					json.put("busiType", info.getBusiType());
					json.put("busiTypeName", info.getBusiTypeName());
					json.put("assetsStatus", info.getAssetsStatus().message());
					dataList.add(json);
				}
				data.put("pageCount", batchResult.getPageCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageList", dataList);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		
		return result;
	}
	
	/**
	 * 收款单关联单据
	 * @param order
	 * @return
	 */
	@RequestMapping("receiptForm.json")
	@ResponseBody
	public JSONObject receiptForm(ReceiptPaymentFormQueryOrder order, String type) {
		String tipPrefix = "查询待处理收款单关联单据";
		JSONObject result = new JSONObject();
		try {
			if (StringUtil.isEmpty(order.getSortCol())) {
				order.setSortCol("raw_add_time");
				order.setSortOrder("DESC");
			}
			JSONObject data = null;
			order.setStatus(ReceiptPaymentFormStatusEnum.NOT_PROCESS);
			order.setFundDirection(FundDirectionEnum.IN);
			if ("payment".equals(type)) {
				
				order.setFundDirection(FundDirectionEnum.OUT);
			}
			QueryBaseBatchResult<ReceiptPaymentFormInfo> batchResult = receiptPaymentFormServiceClient
				.query(order);
			if (batchResult != null && batchResult.isSuccess()) {
				JSONArray dataList = new JSONArray();
				data = new JSONObject();
				List<ReceiptPaymentFormInfo> list = batchResult.getPageList();
				for (ReceiptPaymentFormInfo info : list) {
					JSONObject json = new JSONObject();
					//来源ID
					json.put("sourceId", info.getSourceId());
					//来源单据
					json.put("sourceForm", info.getSourceForm() == null ? "" : info.getSourceForm()
						.message());
					//来源单据ID
					json.put("sourceFormId", info.getSourceFormId());
					//单据来源系统
					json.put("sourceFormSys", info.getSourceFormSys() == null ? "" : info
						.getSourceFormSys().message());
					//资金流向
					json.put("fundDirection", info.getFundDirection() == null ? "" : info
						.getFundDirection().message());
					//发起人
					json.put("applyUserId", info.getUserId());
					json.put("applyUserAccount", info.getUserAccount());
					json.put("applyUserName", info.getUserName());
					//发起部门
					json.put("applyDeptId", info.getDeptId());
					json.put("applyDeptName", info.getDeptName());
					//金额
					json.put("amount", info.getAmount().toStandardString());
					//项目编号
					json.put("projectCode", info.getProjectCode());
					//项目名称
					json.put("projectName", info.getProjectName());
					//客户ID
					json.put("customerId", info.getCustomerId());
					//客户名称
					json.put("customerName", info.getCustomerName());
					//数据添加时间
					json.put("applyTime", DateUtil.simpleFormat(info.getRawAddTime()));
					dataList.add(json);
				}
				data.put("pageCount", batchResult.getPageCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageList", dataList);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询收款单关联单据出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 付款单关联单据
	 * @param order
	 * @return
	 */
	@RequestMapping("paymentForm.json")
	@ResponseBody
	public JSONObject paymentForm(ReceiptPaymentFormQueryOrder order) {
		String tipPrefix = "查询待处理付款单关联单据";
		JSONObject result = new JSONObject();
		try {
			
			JSONObject data = null;
			order.setStatus(ReceiptPaymentFormStatusEnum.NOT_PROCESS);
			order.setFundDirection(FundDirectionEnum.OUT);
			QueryBaseBatchResult<ReceiptPaymentFormInfo> batchResult = receiptPaymentFormServiceClient
				.query(order);
			if (batchResult != null && batchResult.isSuccess()) {
				data = new JSONObject();
				JSONArray dataList = new JSONArray();
				List<ReceiptPaymentFormInfo> list = batchResult.getPageList();
				for (ReceiptPaymentFormInfo info : list) {
					JSONObject json = new JSONObject();
					//来源ID
					json.put("sourceId", info.getSourceId());
					//来源单据
					json.put("sourceForm", info.getSourceForm() == null ? "" : info.getSourceForm()
						.message());
					//来源单据ID
					json.put("sourceFormId", info.getSourceFormId());
					//单据来源系统
					json.put("sourceFormSys", info.getSourceFormSys() == null ? "" : info
						.getSourceFormSys().message());
					//资金流向
					json.put("fundDirection", info.getFundDirection() == null ? "" : info
						.getFundDirection().message());
					//发起人
					json.put("applyUserId", info.getUserId());
					json.put("applyUserAccount", info.getUserAccount());
					json.put("applyUserName", info.getUserName());
					//发起部门
					json.put("applyDeptId", info.getDeptId());
					json.put("applyDeptName", info.getDeptName());
					//金额
					json.put("amount", info.getAmount().toStandardString());
					//项目编号
					json.put("projectCode", info.getProjectCode());
					//项目名称
					json.put("projectName", info.getProjectName());
					//客户ID
					json.put("customerId", info.getCustomerId());
					//客户名称
					json.put("customerName", info.getCustomerName());
					//数据添加时间
					json.put("applyTime", DateUtil.simpleFormat(info.getRawAddTime()));
					dataList.add(json);
				}
				data.put("pageCount", batchResult.getPageCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageList", dataList);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询收款单关联单据出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 查询银行账户信息
	 * @param order
	 * @return
	 */
	@RequestMapping("bankMessage.json")
	@ResponseBody
	public JSONObject bankMessage(BankMessageQueryOrder order) {
		String tipPrefix = "查询银行账户";
		JSONObject result = new JSONObject();
		try {
			if (StringUtil.isEmpty(order.getSortCol())) {
				order.setSortCol("raw_add_time");
				order.setSortOrder("DESC");
			}
			JSONObject data = null;
			order.setStatus(SubjectStatusEnum.NORMAL);
			QueryBaseBatchResult<BankMessageInfo> batchResult = bankMessageServiceClient
				.queryBankMessageInfo(order);
			if (batchResult != null && batchResult.isSuccess()) {
				data = new JSONObject();
				JSONArray dataList = new JSONArray();
				List<BankMessageInfo> list = batchResult.getPageList();
				for (BankMessageInfo info : list) {
					JSONObject json = new JSONObject();
					json.put("bankId", info.getBankId());
					json.put("bankCode", info.getBankCode());
					json.put("bankName", info.getBankName());
					json.put("accountType", info.getAccountType() == null ? "" : info
						.getAccountType().message());
					json.put("accountNo", info.getAccountNo());
					json.put("accountName", info.getAccountName());
					json.put("atCode", info.getAtCode());
					json.put("atName", info.getAtName());
					json.put("amount", info.getAmount().toStandardString());
					json.put("cashDepositCode", info.getCashDepositCode());
					json.put("deptId", info.getDeptId());
					json.put("deptName", info.getDeptName());
					dataList.add(json);
				}
				data.put("pageCount", batchResult.getPageCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageList", dataList);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询银行账户信息出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 查询会计科目
	 * @param pageBase
	 * @param atCode
	 * @param atName
	 * @return
	 */
	@RequestMapping("accountTitle.json")
	@ResponseBody
	public JSONObject accountTitle(QueryPageBase pageBase, String deptId, String deptCode,
									String atCode, String atName) {
		String tipPrefix = "查询会计科目";
		JSONObject result = new JSONObject();
		try {
			
			JSONObject data = new JSONObject();
			JSONArray dataList = new JSONArray();
			if (StringUtil.isBlank(deptCode)) {
				if (StringUtil.isNotBlank(deptId)) {
					Org org = bpmUserQueryService.findDeptById(Long.valueOf(deptId));
					if (org != null) {
						deptCode = org.getCode();
					}
				}
			}
			if (StringUtil.isBlank(deptCode)) {
				result.put("success", false);
				result.put("message", "部门编码不能为空");
				return result;
			}
			//查询真实数据
			KingdeeQueryAccountOrder order = new KingdeeQueryAccountOrder();
			order.setKeyword("");
			if (StringUtil.isNotBlank(atCode)) {
				order.setKeyword(atCode);
			}
			if (StringUtil.isNotBlank(atName)) {
				order.setKeyword(order.getKeyword() + "," + atName);
			}
			order.setDeptCode(deptCode);
			order.setPageSize((int) pageBase.getPageSize());
			order.setPageNumber((int) pageBase.getPageNumber());
			order.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
			logger.info("请求金碟的入参order：" + order);
			
			//先同步部门基础数据
			SyncKingdeeBasicDataOrder basicData = new SyncKingdeeBasicDataOrder();
			basicData.setDeptCode(deptCode);
			logger.info("同步基础数据入参：" + basicData);
			FcsBaseResult syncResult = kingdeeTogetheFacadeClient.syncBasicData(basicData);
			logger.info("同步基础数据结果：" + syncResult);
			if (!syncResult.isSuccess()) {
				result.put("success", false);
				result.put("message", "同步部门信息失败");
				return result;
			}
			KingdeeQueryAccountResult accountResult = kingdeeTogetheFacadeClient
				.queryAccount(order);
			if (accountResult != null && ListUtil.isNotEmpty(accountResult.getDataList())) {
				dataList.clear(); //清除测试数据
				for (AccountInfo info : accountResult.getDataList()) {
					JSONObject json = new JSONObject();
					json.put("atCode", info.getCode());
					json.put("atName", info.getName());
					dataList.add(json);
				}
				data.put("pageCount", accountResult.getTotalPage());
				data.put("pageNumber", accountResult.getPageNumber());
				data.put("pageSize", accountResult.getPageSize());
				data.put("totalCount", accountResult.getTotalCount());
			}
			
			data.put("pageList", dataList);
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询会计科目信息出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 查询保证金账户
	 * @param pageBase
	 * @param atCode
	 * @param atName
	 * @return
	 */
	@RequestMapping("queryGuarantee.json")
	@ResponseBody
	public JSONObject queryGuarantee(QueryPageBase pageBase, String atCode, String atName) {
		String tipPrefix = "保证金账户";
		JSONObject result = new JSONObject();
		try {
			
			JSONObject data = new JSONObject();
			JSONArray dataList = new JSONArray();
			
			//查询真实数据
			KingdeeQueryGuaranteeOrder order = new KingdeeQueryGuaranteeOrder();
			order.setKeyword("");
			if (StringUtil.isNotBlank(atCode)) {
				order.setKeyword(atCode);
			}
			if (StringUtil.isNotBlank(atName)) {
				order.setKeyword(order.getKeyword() + "," + atName);
			}
			order.setPageSize((int) pageBase.getPageSize());
			order.setPageNumber((int) pageBase.getPageNumber());
			order.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
			logger.info("请求金碟的入参order：" + order);
			KingdeeQueryGuaranteeResult guaranteeResult = kingdeeTogetheFacadeClient
				.queryGuarantee(order);
			if (guaranteeResult != null && ListUtil.isNotEmpty(guaranteeResult.getDataList())) {
				dataList.clear(); //清除测试数据
				for (GuaranteeInfo info : guaranteeResult.getDataList()) {
					JSONObject json = new JSONObject();
					json.put("atCode", info.getCode());
					json.put("atName", info.getName());
					dataList.add(json);
				}
				data.put("pageCount", guaranteeResult.getTotalPage());
				data.put("pageNumber", guaranteeResult.getPageNumber());
				data.put("pageSize", guaranteeResult.getPageSize());
				data.put("totalCount", guaranteeResult.getTotalCount());
			}
			
			data.put("pageList", dataList);
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询保证金账户信息出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 查询我的客户信息
	 * @param order
	 * @return
	 */
	@RequestMapping("customer.json")
	@ResponseBody
	public JSONObject customer(CustomerQueryOrder order) {
		String tipPrefix = "查询客户";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = null;
			QueryBaseBatchResult<CustomerBaseInfo> batchResult = null;
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (!StringUtil.equals("IS", order.getIncludePublic())
				&& !DataPermissionUtil.isBelong2Xinhui()) {
				if (sessionLocal != null && DataPermissionUtil.isBusiManager()) {
					order.setCustomerManagerId(sessionLocal.getUserId());
				}
			}
			order.setStatus("on");
			//个人客户
			boolean isPersional = false;
			if (CustomerTypeEnum.PERSIONAL.code().equals(order.getCustomerType())) {
				order.setCustomerType(CustomerTypeEnum.PERSIONAL.code());
				//				batchResult = personalCustomerClient.list(order);
				isPersional = true;
			} else {
				//企业客户
				order.setCustomerType(CustomerTypeEnum.ENTERPRISE.code());
				//				batchResult = companyCustomerClient.list(order);
			}
			batchResult = customerServiceClient.list(order);
			if (batchResult != null && batchResult.isSuccess()) {
				data = new JSONObject();
				JSONArray dataList = new JSONArray();
				List<CustomerBaseInfo> list = batchResult.getPageList();
				for (CustomerBaseInfo info : list) {
					JSONObject json = new JSONObject();
					json.put("customerId", info.getUserId());//info中customerId crm自用
					json.put("customerName", info.getCustomerName());
					//查询详细信息
					CustomerDetailInfo cInfo = customerServiceClient
						.queryByUserId(info.getUserId());
					if (isPersional) {
						//						PersonalCustomerInfo cInfo = personalCustomerClient.queryById(info
						//							.getCustomerId());
						if (cInfo != null) {
							CertTypeEnum certType = CertTypeEnum.getByCode(info.getCertType());
							json.put("certNo", info.getCertNo());
							json.put("certType", info.getCertType());
							json.put("certTypeName", certType == null ? "" : certType.message());
							json.put("mobile", cInfo.getMobile());
							json.put("address", cInfo.getAddress());
							json.put("company", cInfo.getCompany());
						}
					} else {
						//						CompanyCustomerInfo cInfo = companyCustomerClient.queryById(
						//							info.getCustomerId(), null);
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
							} else {
								json.put("certNo", cInfo.getOrgCode());
							}
						}
					}
					dataList.add(json);
				}
				data.put("pageCount", batchResult.getPageCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageList", dataList);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询客户出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 查询单个客户
	 * @param order
	 * @return
	 */
	@RequestMapping("singleCustomer.json")
	@ResponseBody
	public JSONObject singleCustomer(String certNo, String busiLicenseNo,
										HttpServletRequest request, Model model) {
		
		String tipPrefix = "根据证件号查询客户";
		JSONObject result = new JSONObject();
		try {
			JSONObject json = new JSONObject();
			if (StringUtil.isNotEmpty(certNo)) {
				PersonalCustomerInfo cInfo = personalCustomerClient.queryByCertNo(certNo);
				if (cInfo != null) {
					json.put("customerId", cInfo.getUserId());//info中customerId crm自用
					json.put("customerName", cInfo.getCustomerName());
					json.put("certNo", cInfo.getCertNo());
					json.put("certType", cInfo.getCertType());
					json.put("mobile", cInfo.getMobile());
					json.put("address", cInfo.getAddress());
					json.put("company", cInfo.getCompany());
					json.put("status", cInfo.getStatus());
				}
			} else if (StringUtil.isNotEmpty(busiLicenseNo)) {
				CompanyCustomerInfo cInfo = companyCustomerClient
					.queryByBusiLicenseNo(busiLicenseNo);
				if (cInfo != null) {
					json.put("customerId", cInfo.getUserId());//info中customerId crm自用
					json.put("customerName", cInfo.getCustomerName());
					json.put("busiLicenseNo", cInfo.getBusiLicenseNo());
					json.put("industry", cInfo.getIndustryName());
					json.put("address", cInfo.getAddress());
					json.put("status", cInfo.getStatus());
					EnterpriseNatureEnum et = EnterpriseNatureEnum.getByCode(cInfo
						.getEnterpriseType());
					json.put("enterpriseType",
						et == null ? cInfo.getEnterpriseType() : et.message());
				}
			}
			result = toStandardResult(json, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("根据证件号查询客户出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 查询渠道
	 * @param order
	 * @return
	 */
	@RequestMapping("channel.json")
	@ResponseBody
	public JSONObject channel(ChannalQueryOrder order) {
		String tipPrefix = "查询渠道";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = null;
			QueryBaseBatchResult<ChannalInfo> batchResult = channalClient.list(order);
			if (batchResult != null && batchResult.isSuccess()) {
				data = new JSONObject();
				JSONArray dataList = new JSONArray();
				List<ChannalInfo> list = batchResult.getPageList();
				for (ChannalInfo info : list) {
					JSONObject json = new JSONObject();
					json.put("channelId", info.getId());
					json.put("channelCode", info.getChannelCode());
					json.put("channelName", info.getChannelName());
					json.put("creditAmount", info.getCreditAmount().toStandardString());
					json.put("singleLimit", info.getSingleLimit().toStandardString());
					json.put("channelType", ChanalTypeEnum.getMsgByCode(info.getChannelType()));
					dataList.add(json);
				}
				data.put("pageCount", batchResult.getPageCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageList", dataList);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询渠道出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 查询渠道
	 * @param order
	 * @return
	 */
	@RequestMapping("allChannel.json")
	@ResponseBody
	public JSONObject allChannel(HttpServletRequest request, Model model) {
		String tipPrefix = "查询渠道";
		JSONObject result = new JSONObject();
		try {
			JSONArray data = new JSONArray();
			List<ChanalTypeEnum> channelTypes = ChanalTypeEnum.getAllEnum();
			for (ChanalTypeEnum channelType : channelTypes) {
				JSONObject channel = new JSONObject();
				channel.put("code", channelType.code());
				channel.put("name", channelType.message());
				JSONArray dataList = new JSONArray();
				List<ChannalInfo> cList = basicDataCacheService.queryChannelByType(channelType);
				if (cList != null) {
					for (ChannalInfo info : cList) {
						JSONObject json = new JSONObject();
						json.put("channelId", info.getId());
						json.put("channelCode", info.getChannelCode());
						json.put("channelType", info.getChannelType());
						json.put("channelName", info.getChannelName());
						json.put("creditAmount", info.getCreditAmount().toStandardString());
						json.put("singleLimit", info.getSingleLimit().toStandardString());
						dataList.add(json);
					}
				}
				channel.put("channelList", dataList);
				data.add(channel);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询渠道出错 {} ", e);
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
	public JSONObject userDetail(Long userId, String userAccount, HttpServletRequest request,
									Model model) {
		String tipPrefix = "查询用户";
		JSONObject result = new JSONObject();
		try {
			UserDetailInfo userDetail = null;
			if (userId != null && userId > 0) {
				userDetail = bpmUserQueryService.findUserDetailByUserId(userId);
			} else if (StringUtil.isNotEmpty(userAccount)) {
				userDetail = bpmUserQueryService.findUserDetailByAccount(userAccount);
			}
			if (userDetail != null) {
				result = toStandardResult((JSONObject) JSONObject.toJSON(userDetail), tipPrefix);
			} else {
				result = toStandardResult(new JSONObject(), tipPrefix);
			}
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询用户信息出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 查询部门信息
	 * @param order
	 * @return
	 */
	@RequestMapping("dept.json")
	@ResponseBody
	public JSONObject dept(Long deptId, String deptCode, HttpServletRequest request, Model model) {
		String tipPrefix = "查询部门";
		JSONObject result = new JSONObject();
		try {
			Org org = null;
			if (deptId != null && deptId > 0) {
				org = bpmUserQueryService.findDeptById(deptId);
			} else if (StringUtil.isNotEmpty(deptCode)) {
				org = bpmUserQueryService.findDeptByCode(deptCode);
			}
			
			if (org != null) {
				result = toStandardResult((JSONObject) JSONObject.toJSON(org), tipPrefix);
			} else {
				result = toStandardResult(new JSONObject(), tipPrefix);
			}
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询部门出错 {} ", e);
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
	public JSONObject deptFzr(Long deptId, String deptCode, HttpServletRequest request, Model model) {
		
		String tipPrefix = "查询部门负责人";
		JSONObject result = new JSONObject();
		try {
			
			List<SysUser> fzrList = null;
			if (deptId != null && deptId > 0) {
				fzrList = bpmUserQueryService.findChargeByOrgId(deptId);
			} else if (StringUtil.isNotEmpty(deptCode)) {
				fzrList = bpmUserQueryService.findChargeByOrgCode(deptCode);
			}
			if (ListUtil.isNotEmpty(fzrList)) {
				SysUser userInfo = fzrList.get(0);
				JSONObject json = new JSONObject();
				json.put("id", userInfo.getUserId());
				json.put("name", userInfo.getFullname());
				json.put("account", userInfo.getAccount());
				json.put("mobile", userInfo.getMobile());
				json.put("email", userInfo.getEmail());
				result = toStandardResult(json, tipPrefix);
			} else {
				JSONObject json = new JSONObject();
				json.put("id", "");
				json.put("name", "");
				json.put("account", "");
				json.put("mobile", "");
				json.put("email", "");
				result = toStandardResult(json, tipPrefix);
			}
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询部门负责人出错 {} ", e);
		}
		return result;
	}
	
	/**
	 * 查询收款人
	 * @param order
	 * @return
	 */
	@RequestMapping("payee.json")
	@ResponseBody
	public JSONObject payee(UserExtraMessageQueryOrder order) {
		String tipPrefix = "查询收款人";
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
					json.put("userName", info.getUserName());
					json.put("bankName", info.getBankName());
					json.put("bankAccountNo", info.getBankAccountNo());
					dataList.add(json);
				}
				data.put("pageCount", batchResult.getPageCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageList", dataList);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询渠道出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 查询合同项目
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("contractProjects.json")
	public Object contractProjects(ProjectContractQueryOrder order) {
		JSONObject result = new JSONObject();
		String tipPrefix = "查询合同项目集";
		try {
			setSessionLocalInfo2Order(order);
			order.setChooseProject(BooleanEnum.IS.code());
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			QueryBaseBatchResult<ProjectContractResultInfo> batchResult = projectContractServiceClient
				.query(order);
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (ProjectContractResultInfo info : batchResult.getPageList()) {
					JSONObject json = new JSONObject();
					json.put("projectCode", info.getProjectCode());
					json.put("projectName", info.getProjectName());
					json.put("customerName", info.getCustomerName());
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
	 * 查询费用种类
	 * @param order
	 * @return
	 */
	@RequestMapping("costCategory.json")
	@ResponseBody
	public JSONObject costCategory(String categoryNm, CostCategoryQueryOrder order) {
		String tipPrefix = "查询费用种类";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = null;
			if (StringUtil.isNotBlank(categoryNm)) {
				order.setName("%" + categoryNm + "%");
			}
			order.setStatusList(new ArrayList<CostCategoryStatusEnum>());
			order.getStatusList().add(CostCategoryStatusEnum.NORMAL);
			QueryBaseBatchResult<CostCategoryInfo> batchResult = costCategoryServiceClient
				.queryPage(order);
			
			if (batchResult != null && batchResult.isSuccess()) {
				data = new JSONObject();
				JSONArray dataList = new JSONArray();
				for (CostCategoryInfo info : batchResult.getPageList()) {
					JSONObject json = new JSONObject();
					json.put("categoryId", info.getCategoryId());
					json.put("categoryNm", info.getName());
					json.put("accountCode", info.getAccountCode());
					json.put("accountName", info.getAccountName());
					dataList.add(json);
				}
				data.put("pageCount", batchResult.getPageCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageList", dataList);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询费用种类出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 查询费用种类
	 * @param order
	 * @return
	 */
	@RequestMapping("costCategoryById.json")
	@ResponseBody
	public JSONObject costCategoryById(long categoryId) {
		JSONObject result = new JSONObject();
		try {
			CostCategoryInfo costInfo = costCategoryServiceClient.queryById(categoryId);
			result.put("name", costInfo.getName());
			result.put("accountCode", costInfo.getAccountCode());
			result.put("accountName", costInfo.getAccountName());
			result.put("success", true);
		} catch (Exception e) {
			logger.error("查询费用种类出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 查询可复制的立项表单
	 * @param order
	 * @return
	 */
	@RequestMapping("canCopySetupForm.json")
	@ResponseBody
	public JSONObject canCopySetupForm(SetupFormQueryOrder order) {
		String tipPrefix = "查询可复制的立项表单";
		JSONObject result = new JSONObject();
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		try {
			if (sessionLocal == null) {
				result.put("success", false);
				result.put("message", "当前未登陆或已掉线");
				return result;
			}
			if (order.getCustomerId() <= 0 || StringUtil.isBlank(order.getBusiType())) {
				result.put("success", false);
				result.put("message", "客户ID或业务类型为空");
				return result;
			}
			
			order.setFormStatus("fromCopy");
			order.setBusiManagerId(sessionLocal.getUserId());
			if (ProjectUtil.isGuarantee(order.getBusiType())
				|| ProjectUtil.isBond(order.getBusiType())
				|| ProjectUtil.isEntrusted(order.getBusiType())) {
				order.setBusiType(null);
				List<String> busiTypeList = Lists.newArrayList();
				busiTypeList.add("1");
				busiTypeList.add("22");
				busiTypeList.add("23");
				busiTypeList.add("24");
				busiTypeList.add("3");
				busiTypeList.add("4");
				busiTypeList.add("6");
				busiTypeList.add("7");
				order.setBusiTypeList(busiTypeList);
			}
			JSONObject data = null;
			QueryBaseBatchResult<SetupFormProjectInfo> batchResult = projectSetupServiceClient
				.querySetupForm(order);
			
			if (batchResult != null && batchResult.isSuccess()) {
				data = new JSONObject();
				JSONArray dataList = new JSONArray();
				for (SetupFormProjectInfo info : batchResult.getPageList()) {
					JSONObject json = new JSONObject();
					json.put("formId", info.getFormId());
					json.put("projectCode", info.getProjectCode());
					json.put("projectName", info.getProjectName());
					json.put("amount", info.getAmount().toStandardString());
					json.put("setupTime", DateUtil.simpleFormat(info.getFormAddTime()));
					dataList.add(json);
				}
				data.put("pageCount", batchResult.getPageCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageList", dataList);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询可复制的立项表单出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 加载签报事项
	 * 
	 * @param
	 * @param model
	 * @return
	 */
	
	@RequestMapping("loadFormChangeApply.json")
	@ResponseBody
	public JSONObject loadFormChangeApply(FormChangeApplyQueryOrder order, Model model) {
		String tipPrefix = "加载签报事项";
		JSONObject result = new JSONObject();
		try {
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			setSessionLocalInfo2Order(order);
			QueryBaseBatchResult<FormChangeApplySearchInfo> batchResult = formChangeApplyServiceClient
				.searchForm(order);
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (FormChangeApplySearchInfo info : batchResult.getPageList()) {
					JSONObject json = new JSONObject();
					json.put("applyFormId", info.getFormId());
					json.put("applyCode", info.getApplyCode());
					json.put("applyTitle", info.getApplyTitle());
					json.put("applyType", info.getApplyType());
					json.put("applyTypeMessage", info.getApplyType().message());
					json.put("author", info.getFormUserName());
					json.put("isCouncil", info.getIsNeedCouncil().message());
					json.put("submitTime", info.getSubmitTime());
					dataList.add(json);
				}
			}
			model.addAttribute("conditions", order);
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
	
	@ResponseBody
	@RequestMapping("deptBudgetBalance.json")
	public JSONObject getBalance(String budgetDeptId, String applicationTime, String categoryId) {
		String tipPrefix = "部门预算余额查询";
		JSONObject result = new JSONObject();
		Money budgetDept = Money.zero();
		try {
			JSONObject data = new JSONObject();
			if (StringUtil.isNotBlank(budgetDeptId) && StringUtil.isNotBlank(applicationTime)) {
				Date applyTime = DateUtil.parse(applicationTime);
				budgetDept = budgetServiceClient.queryBalanceByDeptCategoryId(
					Long.valueOf(budgetDeptId), Long.valueOf(categoryId), applyTime, false);
			}
			data.put("balance", budgetDept.toStandardString());
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error(tipPrefix + "错误", e);
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
		String tipPrefix = "查询银行账户信息维护-账户选择";
		JSONObject result = new JSONObject();
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		try {
			if (sessionLocal == null) {
				result.put("success", false);
				result.put("message", "当前未登陆或已掉线");
				return result;
			}
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
				data.put("pageCount", batchResult.getPageCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageList", dataList);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询银行账户信息维护-账户选择出错 {} ", e);
		}
		
		return result;
	}
	
	@RequestMapping("projectCando.json")
	@ResponseBody
	public JSONObject projectCando(String projectCode, HttpServletRequest request) {
		
		JSONObject json = new JSONObject();
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		try {
			if (sessionLocal == null) {
				json.put("success", false);
				json.put("message", "当前未登陆或已掉线");
				return json;
			}
			ProjectCandoResult result = projectServiceClient.getCando(projectCode);
			return result.toJson();
		} catch (Exception e) {
			json = toStandardResult(null, "查询项目可操作项");
			logger.error("查询项目可操作项出错 {} ", e);
		}
		
		return json;
	}
	
	@RequestMapping("validateIdCard.json")
	@ResponseBody
	public JSONObject validateIdCard(ApixValidateIdCardOrder order) {
		JSONObject json = new JSONObject();
		try {
			if (order == null || StringUtil.isBlank(order.getName())
				|| StringUtil.isBlank(order.getCertNo())) {
				json.put("success", false);
				json.put("message", "参数不完整");
				return json;
			}
			ApixValidateIdCardResult result = apixCreditInvestigationFacadeClient
				.validateIdCard(order);
			if (result == null) {
				json.put("success", false);
				json.put("message", "调用接口出错");
			} else {
				json.put("success", CommonResultEnum.EXECUTE_SUCCESS == result.getResultCode());
				json.put("message", StringUtil.isNotBlank(result.getMsg()) ? result.getMsg()
					: result.getResultMessage());
				if (CommonResultEnum.EXECUTE_SUCCESS == result.getResultCode()) {
					JSONObject checkResult = new JSONObject();
					ApixResultEnum codeEnum = ApixResultEnum.getByCode(result.getCode());
					checkResult.put("checkResult", codeEnum == ApixResultEnum.EXECUTE_SUCCESS);
					checkResult.put("checkMessage", result.getMsg() == null ? "" : result.getMsg());
					checkResult.put("idcardphoto", result.getPersonInfo() == null ? "" : result
						.getPersonInfo().getIdcardphoto());
					json.put("data", checkResult);
				}
			}
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "验证身份证号出错");
			logger.error("验证身份证号出错 {}", e);
		}
		return json;
	}
	
	/**
	 * 银行卡实名验证
	 * @param order
	 * @return
	 */
	@RequestMapping("validateBankCard.json")
	@ResponseBody
	public JSONObject validateBankCard(ApixValidateBankCardOrder order) {
		JSONObject json = new JSONObject();
		try {
			if (order == null || StringUtil.isBlank(order.getName())
				|| StringUtil.isBlank(order.getBankCardNo())) {
				json.put("success", false);
				json.put("message", "参数不完整");
				return json;
			}
			order.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
			order.setValidateType(ValidateTypeEnum.BANK_CARD_NAME);
			ApixValidateBankCardResult result = apixCreditInvestigationFacadeClient
				.validateBankCard(order);
			if (result == null) {
				json.put("success", false);
				json.put("message", "调用接口出错");
			} else {
				json.put("success", CommonResultEnum.EXECUTE_SUCCESS == result.getResultCode());
				json.put("message", StringUtil.isNotBlank(result.getMsg()) ? result.getMsg()
					: result.getResultMessage());
				if (CommonResultEnum.EXECUTE_SUCCESS == result.getResultCode()) {
					JSONObject checkResult = new JSONObject();
					ApixResultEnum codeEnum = ApixResultEnum.getByCode(result.getCode());
					checkResult.put("checkResult", codeEnum == ApixResultEnum.EXECUTE_SUCCESS);
					checkResult.put("checkMessage", result.getMsg() == null ? "" : result.getMsg());
					json.put("data", checkResult);
				}
			}
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "验证银行卡实名出错");
			logger.error("验证银行卡实名出错 {}", e);
		}
		return json;
	}
	
	/**
	 * 查询合同项目
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("riskTeamMember.json")
	public Object riskTeamMember(String projectCode) {
		JSONObject result = new JSONObject();
		String tipPrefix = "查询项目风险处置小组";
		try {
			if (StringUtil.isBlank(projectCode)) {
				result.put("success", false);
				result.put("message", "项目编号为空");
			}
			JSONArray data = new JSONArray();
			ProjectRelatedUserQueryOrder relatedUserQueryOrder = new ProjectRelatedUserQueryOrder();
			relatedUserQueryOrder.setProjectCode(projectCode);
			relatedUserQueryOrder.setUserType(ProjectRelatedUserTypeEnum.RISK_TEAM_MEMBER);
			relatedUserQueryOrder.setPageSize(100);
			QueryBaseBatchResult<ProjectRelatedUserInfo> batchResult = projectRelatedUserServiceClient
				.queryPage(relatedUserQueryOrder);
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (ProjectRelatedUserInfo info : batchResult.getPageList()) {
					JSONObject json = new JSONObject();
					json.put("projectCode", info.getProjectCode());
					json.put("userId", info.getUserId());
					json.put("userName", info.getUserName());
					json.put("userAccount", info.getUserAccount());
					json.put("userEmail", info.getUserEmail());
					json.put("userMobile", info.getUserMobile());
					data.add(json);
				}
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询项目风险处置小组出错", e);
		}
		return result;
	}
	
	/** 设置查询orderNo */
	private void setOrderNum(ApixOrderBase order) {
		order.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
	}
	
	/**
	 * 类似企业信息查询接口 (风险监控)
	 * @param licenseNo 证件号码
	 * @param customName 企业名称或个人姓名
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@ResponseBody
	@RequestMapping("querySimilarEnterprise.json")
	public Object querySimilarEnterprise(String licenseNo, String customName) {
		JSONObject result = new JSONObject();
		String tipPrefix = "类似企业信息查询";
		try {
			if (StringUtil.isBlank(customName)) {
				result.put("success", false);
				result.put("message", "客户名称不能为空");
				return result;
			} else if (StringUtil.isBlank(licenseNo)) {
				result.put("success", false);
				result.put("message", "信用统一代码/营业执照号不能为空");
				return result;
			}
			
			if (Env.isNet() || Env.isPre()) {
				result.put("success", false);
				result.put("message", "当前环境不调用风险监控");
				return result;
			}
			
			QuerySimilarEnterpriseOrder queryOrder = new QuerySimilarEnterpriseOrder();
			queryOrder.setLicenseNo(licenseNo);
			queryOrder.setCustomName(customName);
			queryOrder.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
			JSONArray data = new JSONArray();
			QuerySimilarEnterpriseResult batchResult = riskSystemFacadeClient
				.querySimilarEnterprise(queryOrder);
			if (null != batchResult
				&& batchResult.getResultCode() == CommonResultEnum.EXECUTE_SUCCESS) {
				if (ListUtil.isNotEmpty(batchResult.getList())) {
					for (CustomInfo info : batchResult.getList()) {
						JSONObject json = new JSONObject();
						json.put("licenseNo", info.getLicenseNo());
						json.put("custoName", info.getCustomName());
						json.put("detailUrl", "/riskMg/page/loginRiskSystem.htm?toUrl="
												+ URLEncoder.encode(info.getDetailUrl()));
						data.add(json);
					}
					result = toStandardResult(data, tipPrefix);
				} else {
					result.put("success", true);
					result.put("data", data);
					result.put("message", batchResult.getResultMessage());
				}
			} else {
				result.put("success", false);
				result.put("message", batchResult.getResultMessage());
			}
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error(tipPrefix + "出错", e);
		}
		return result;
	}
	
	/**
	 * 企业失信黑名单
	 * @return
	 * @param certNo 身份证号
	 * @param name=姓名
	 * @param type "person", "个人" ;"company", "公司"
	 * name=河北骑岸建筑工程有限公司&certNo=567352437&type=company
	 */
	@ResponseBody
	@RequestMapping("dishonestQuery.json")
	public Object dishonestQuery(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String tipPrefix = "查询企业失信黑名单";
		try {
			ApixDishonestQueryOrder queryOrder = new ApixDishonestQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			CustomerTypeEnum customerType = CustomerTypeEnum
				.getByCode(request.getParameter("type"));
			queryOrder
				.setType(customerType != CustomerTypeEnum.PERSIONAL ? ApixUserTypeEnum.COMPANY
					: ApixUserTypeEnum.PERSON);
			queryOrder.setOneCert(StringUtil.equals(request.getParameter("oneCert"),
				BooleanEnum.IS.code()));
			setOrderNum(queryOrder);
			JSONArray data = new JSONArray();
			
			if (Env.isNet() || Env.isPre()) {
				result.put("success", false);
				result.put("message", "当前环境不调用风险监控");
				return result;
			}
			
			ApixDishonestQueryResult batchResult = apixCreditInvestigationFacadeClient
				.dishonestQuery(queryOrder);
			if (null != batchResult
				&& batchResult.getResultCode() == CommonResultEnum.EXECUTE_SUCCESS) {
				if (StringUtil.notEquals("0", batchResult.getCode())) {
					result.put("success", false);
					result.put(
						"message",
						StringUtil.defaultIfBlank(batchResult.getMsg(),
							batchResult.getResultMessage()));
				} else {
					if (ListUtil.isNotEmpty(batchResult.getDishonestList())) {
						for (DishonestInfo info : batchResult.getDishonestList()) {
							JSONObject json = new JSONObject();
							json.put("code", info.getCode());//案号
							json.put("duty", info.getDuty());//法律文书确定的义务
							json.put("disruptType", info.getDisruptType());//具体情形
							json.put("pubTime", info.getPubTime());//发布时间
							json.put("court", info.getCourt());//执行法院
							json.put("performance", info.getPerformance());//履行情况
							json.put("sex", info.getSex());//性别
							json.put("name", info.getName());//姓名
							json.put("area", info.getArea());//省份
							json.put("age", info.getAge());//年龄
							json.put("idCardNo", info.getCertNo());//证件号码
							data.add(json);
						}
						result = toStandardResult(data, tipPrefix);
					} else {
						result.put("success", true);
						result.put("data", data);
						result.put(
							"message",
							StringUtil.defaultIfBlank(batchResult.getMsg(),
								batchResult.getResultMessage()));
					}
				}
			} else {
				result.put("success", false);
				result
					.put(
						"message",
						StringUtil.defaultIfBlank(batchResult.getMsg(),
							batchResult.getResultMessage()));
			}
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error(tipPrefix + "出错", e);
		}
		return result;
	}
	
	/**
	 * 实名校验
	 * @param certNo 身份证号
	 * @param name 姓名
	 * */
	@ResponseBody
	@RequestMapping("validateCard.json")
	public JSONObject validateCard(HttpServletRequest request) {
		ApixValidateIdCardOrder queryOrder = new ApixValidateIdCardOrder();
		WebUtil.setPoPropertyByRequest(queryOrder, request);
		JSONObject json = new JSONObject();
		setOrderNum(queryOrder);
		ApixValidateIdCardResult result = apixCreditInvestigationFacadeClient
			.validateIdCard(queryOrder);
		boolean success = false;
		String message = "接口调用失败";
		if (result != null) {
			if (CommonResultEnum.EXECUTE_SUCCESS.code().equals(result.getResultCode())
				&& ApixResultEnum.EXECUTE_SUCCESS.code().equals(result.getCode())) {
				success = true;
			}
			message = StringUtil.defaultIfBlank(result.getMsg(), result.getResultMessage());
		}
		json.put("success", success);
		json.put("message", message);
		return json;
	}
	
	/**
	 * 手机号校验
	 * @param mobile;
	 * @param name; 客户名
	 * @param certNo; 证件号
	 * */
	@ResponseBody
	@RequestMapping("validateMobile.json")
	public JSONObject validateMobile(HttpServletRequest request, ApixValidateMobileOrder queryOrder) {
		WebUtil.setPoPropertyByRequest(queryOrder, request);
		JSONObject json = new JSONObject();
		setOrderNum(queryOrder);
		ApixValidateMobileResult result = apixCreditInvestigationFacadeClient
			.validateMobile(queryOrder);
		logger.info("电话校验{}", result);
		boolean success = false;
		String message = "接口调用失败";
		if (result != null) {
			if ("EXECUTE_SUCCESS".equals(result.getResultCode())) {
				if (ApixResultEnum.EXECUTE_SUCCESS.code().equals(result.getCode())) {
					success = true;
				}
				
			}
			message = StringUtil.defaultIfBlank(result.getMsg(), result.getResultMessage());
		}
		json.put("success", success);
		json.put("message", message);
		return json;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
	@RequestMapping("expenseApplication.json")
	public Object expenseApplication(String formId, String expenseType) {
		JSONObject result = new JSONObject();
		String tipPrefix = "查询项目风险处置小组";
		try {
			if (StringUtil.isBlank(expenseType)) {
				result = toStandardResult(null, tipPrefix);
				return result;
			}
			
			String ci = "";
			for (String category : expenseType.split(",")) {
				if (StringUtil.isNotBlank(category)) {
					CostCategoryInfo costInfo = costCategoryServiceClient.queryById(Long
						.valueOf(category));
					if ("还款".equals(costInfo.getName())) {
						ci = ci + "借款";
					}
					if ("冲预付款".equals(costInfo.getName())) {
						ci = ci + "预付款";
					}
				}
			}
			
			if (StringUtil.isBlank(ci)) {
				result = toStandardResult(null, tipPrefix);
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
			List statusList = Lists.newArrayList();
			statusList.add(FormStatusEnum.APPROVAL);
			order.setFormStatusList(statusList);
			
			QueryBaseBatchResult<FormExpenseApplicationInfo> batchResult = expenseApplicationServiceClient
				.queryPage(order);
			
			JSONObject data0 = new JSONObject();
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
							json.put("sqje", detailInfo.getAmount().toString());
							json.put("sykcx", sykcx.toString());
							json.put("fpcx", fpcx.toString());
							json.put("xjcx", xjcx.toString());
							json.put("expenseId", detailInfo.getExpenseApplicationId());
							json.put("detailId", detailInfo.getDetailId());
							data.add(json);
							//allcx = allcx.add(fpcx).add(xjcx);
							allcx = allcx.add(detailInfo.getAmount());
						}
					}
				}
			}
			
			data0.put("list", data);
			data0.put("allcx", allcx.toString());
			
			if (data.isEmpty()) {
				result = toStandardResult(null, tipPrefix);
			} else {
				result = toStandardResult(data0, tipPrefix);
			}
			
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询项目风险处置小组出错", e);
		}
		return result;
	}
	
	/**
	 * 
	 * @param formId 表单formid
	 * @param payee 收款人
	 * @param direction 费用方向 PRIVATE PUBLIC
	 * @param getLoginUser 是否查看更多
	 * @return
	 */
	@ResponseBody
	@RequestMapping("expenseApplicationReverse.json")
	public Object expenseApplicationReverse(String expenseApplicationId, String payee,
											String direction, boolean getLoginUser) {
		JSONObject result = new JSONObject();
		String tipPrefix = "查询冲销信息";
		try {
			//			FormExpenseApplicationInfo expenseInfo = expenseApplicationServiceClient
			//				.queryByFormId(formId);
			//			if (expenseInfo == null) {
			//				result.put("success", false);
			//				result.put("message", tipPrefix + "失败");
			//				return result;
			//			}
			
			// 抓取 还款冲销信息和预付款冲销信息
			long jkId = 0;
			long yfkId = 0;
			long hkId = 0;
			long tyfkId = 0;
			CostCategoryQueryOrder costOrder = new CostCategoryQueryOrder();
			costOrder.setStatusList(new ArrayList<CostCategoryStatusEnum>());
			costOrder.getStatusList().add(CostCategoryStatusEnum.NORMAL);
			costOrder.setPageSize(10000);
			QueryBaseBatchResult<CostCategoryInfo> batchResult = costCategoryServiceClient
				.queryPage(costOrder);
			for (CostCategoryInfo cInfo : batchResult.getPageList()) {
				if ("借款".equals(cInfo.getName())) {
					jkId = cInfo.getCategoryId();
				} else if ("预付款".equals(cInfo.getName())) {
					yfkId = cInfo.getCategoryId();
				} else if ("还款".equals(cInfo.getName())) {
					hkId = cInfo.getCategoryId();
				} else if ("退预付款".equals(cInfo.getName())) {
					tyfkId = cInfo.getCategoryId();
				}
			}
			JSONArray data = new JSONArray();
			
			// 对公抓去预付款信息 对私抓取借款信息
			if (CostDirectionEnum.PUBLIC.code().equals(direction)) {
				// 预付款
				Money yfkAllAmount = Money.zero();
				Money tyfkAllAmount = Money.zero();
				Money waitReverseAmount = Money.zero();
				if (yfkId > 0) {
					ExpenseApplicationQueryOrder queryOrder = new ExpenseApplicationQueryOrder();
					queryOrder.setPageSize(10000);
					queryOrder.setExpenseType(String.valueOf(yfkId));
					if (!getLoginUser) {
						queryOrder.setPayee(payee);
					}
					queryOrder.setAgent(String.valueOf(ShiroSessionUtils.getSessionLocal()
						.getUserId()));
					// 查询审核通过的
					List<FormStatusEnum> formStatuss = new ArrayList<FormStatusEnum>();
					//					formStatuss.add(FormStatusEnum.DRAFT);
					//					formStatuss.add(FormStatusEnum.SUBMIT);
					//					formStatuss.add(FormStatusEnum.CANCEL);
					//					formStatuss.add(FormStatusEnum.AUDITING);
					//					formStatuss.add(FormStatusEnum.BACK);
					formStatuss.add(FormStatusEnum.APPROVAL);
					queryOrder.setFormStatusList(formStatuss);
					QueryBaseBatchResult<FormExpenseApplicationDetailAllInfo> queryResult = expenseApplicationServiceClient
						.queryPageAll(queryOrder);
					if (queryResult != null && ListUtil.isNotEmpty(queryResult.getPageList())) {
						
						for (FormExpenseApplicationDetailAllInfo info : queryResult.getPageList()) {
							// 待冲销是全部金额，已冲销是选中且审核通过
							yfkAllAmount.addTo(info.getAmount());
							//  判断已被冲销的为已冲销金额,未冲销的为等待冲销金额
							if (BooleanEnum.YES == info.getReverse()
								&& FormStatusEnum.APPROVAL == info.getFormStatus()) {
								// 已冲销
								tyfkAllAmount.addTo(info.getAmount());
							} else {
								// 待冲销 
								waitReverseAmount.addTo(info.getAmount());
								
								JSONObject json = new JSONObject();
								json.put("detailId", info.getDetailId());
								json.put("applicationTime",
									DateUtil.dtSimpleFormat(info.getApplicationTime()));
								json.put("billNo", info.getBillNo());
								json.put("expenseApplicationId", info.getExpenseApplicationId());
								for (CostCategoryInfo cost : batchResult.getPageList()) {
									if ((cost.getCategoryId() > 0 || cost.getCategoryId() == -1)
										&& String.valueOf(cost.getCategoryId()).equals(
											info.getExpenseType())) {
										json.put("expenseType", cost.getName());
										break;
									}
								}
								json.put("amount", info.getAmount());
								data.add(json);
							}
							
						}
					}
					
					result.put("data", data);
					result.put("waitReverseAmount", waitReverseAmount);
					result.put("totalAmount", yfkAllAmount);
					result.put("usedAmount", tyfkAllAmount);
					
					result = toStandardResult(result, tipPrefix);
					
				}
			} else if (CostDirectionEnum.PRIVATE.code().equals(direction)) {
				// 借款
				Money jkAllAmount = Money.zero();
				Money hkAllAmount = Money.zero();
				Money waitReverseAmount = Money.zero();
				if (jkId > 0) {
					ExpenseApplicationQueryOrder queryOrder = new ExpenseApplicationQueryOrder();
					queryOrder.setPageSize(10000);
					queryOrder.setExpenseType(String.valueOf(jkId));
					queryOrder.setPayee(payee);
					queryOrder.setAgent(String.valueOf(ShiroSessionUtils.getSessionLocal()
						.getUserId()));
					// 查询审核通过的
					List<FormStatusEnum> formStatuss = new ArrayList<FormStatusEnum>();
					formStatuss.add(FormStatusEnum.APPROVAL);
					queryOrder.setFormStatusList(formStatuss);
					QueryBaseBatchResult<FormExpenseApplicationDetailAllInfo> queryResult = expenseApplicationServiceClient
						.queryPageAll(queryOrder);
					if (queryResult != null && ListUtil.isNotEmpty(queryResult.getPageList())) {
						for (FormExpenseApplicationDetailAllInfo info : queryResult.getPageList()) {
							jkAllAmount.addTo(info.getAmount());
							JSONObject json = new JSONObject();
							json.put("detailId", info.getDetailId());
							json.put("applicationTime",
								DateUtil.dtSimpleFormat(info.getApplicationTime()));
							json.put("billNo", info.getBillNo());
							json.put("expenseApplicationId", info.getExpenseApplicationId());
							for (CostCategoryInfo cost : batchResult.getPageList()) {
								if ((cost.getCategoryId() > 0 || cost.getCategoryId() == -1)
									&& String.valueOf(cost.getCategoryId()).equals(
										info.getExpenseType())) {
									json.put("expenseType", cost.getName());
									break;
								}
							}
							json.put("amount", info.getAmount());
							data.add(json);
						}
					}
					// 还款
					queryOrder.setExpenseType(String.valueOf(hkId));
					formStatuss.clear();
					formStatuss.add(FormStatusEnum.DRAFT);
					formStatuss.add(FormStatusEnum.SUBMIT);
					//					formStatuss.add(FormStatusEnum.CANCEL);
					formStatuss.add(FormStatusEnum.AUDITING);
					//					formStatuss.add(FormStatusEnum.BACK);
					formStatuss.add(FormStatusEnum.APPROVAL);
					queryOrder.setFormStatusList(formStatuss);
					queryResult = expenseApplicationServiceClient.queryPageAll(queryOrder);
					if (queryResult != null && ListUtil.isNotEmpty(queryResult.getPageList())) {
						for (FormExpenseApplicationDetailAllInfo info : queryResult.getPageList()) {
							
							// 剔除自己的还款数据  发现是自己的数据，不计算入已还款
							if (StringUtil.equals(expenseApplicationId,
								String.valueOf(info.getExpenseApplicationId()))) {
								
							}
							hkAllAmount.addTo(info.getAmount());
						}
					}
					if (StringUtil.isNotEmpty(expenseApplicationId)) {
						FormExpenseApplicationInfo expenseInfo = expenseApplicationServiceClient
							.queryById(Long.valueOf(expenseApplicationId));
						/// 审核中且自己，才将金额加回去，用于兼容审核页面的修改
						if (FormStatusEnum.DRAFT == expenseInfo.getFormStatus()
							|| FormStatusEnum.SUBMIT == expenseInfo.getFormStatus()
							|| FormStatusEnum.AUDITING == expenseInfo.getFormStatus()
							|| FormStatusEnum.APPROVAL == expenseInfo.getFormStatus()) {
							
							// 20161123 剔除自己的还款数据  发现是自己的数据，不计算入已还款 减去自己的待还款记录
							for (FormExpenseApplicationDetailInfo detailInfo : expenseInfo
								.getDetailList()) {
								if (StringUtil.equals(String.valueOf(hkId),
									detailInfo.getExpenseType())) {
									hkAllAmount.subtractFrom(detailInfo.getAmount());
								}
							}
						}
					}
					waitReverseAmount = jkAllAmount.subtract(hkAllAmount);
				}
				
				result.put("data", data);
				result.put("waitReverseAmount", waitReverseAmount);
				result.put("totalAmount", jkAllAmount);
				result.put("usedAmount", hkAllAmount);
				
				result = toStandardResult(result, tipPrefix);
			}
			
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询冲销信息出错", e);
		}
		return result;
	}
	
	/**
	 * 部门预算CheckLock
	 * @param acquire;
	 * */
	@ResponseBody
	@RequestMapping("deptBalanceCheckLock.json")
	public JSONObject deptBalanceCheckLock(boolean acquire) {
		DeptBalanceCheckLock clock = DeptBalanceCheckLock.getInstance();
		if (acquire) {
			if (!clock.acquire())
				clock.release();
		} else {
			clock.release();
		}
		JSONObject json = new JSONObject();
		json.put("success", true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("proPayBank.json")
	public JSONObject proPayBank(long formId, String payBank, String payBankAccount) {
		TravelExpenseOrder order = new TravelExpenseOrder();
		order.setFormId(formId);
		order.setPayBank(payBank);
		order.setPayBankAccount(payBankAccount);
		travelExpenseServiceClient.updatePayBank(order);
		JSONObject json = new JSONObject();
		json.put("success", true);
		json.put("message", "操作成功");
		return json;
	}
	
	/**
	 * 查看出入库记录
	 * 
	 * @param inputListId
	 * @return
	 */
	@RequestMapping("inOutHistory.json")
	@ResponseBody
	public JSONObject inOutHistory(Long inputListId) {
		String tipPrefix = "查看出入库记录";
		JSONObject result = new JSONObject();
		if (inputListId == null || inputListId > 0) {
			result.put("success", false);
			result.put("message", "id不能为空");
		}
		try {
			JSONObject data = new JSONObject();
			JSONArray dataList = new JSONArray();
			//档案信息
			FileInputListInfo listInfo = fileServiceClient.findByInputId(inputListId);
			if (listInfo.getStatus() == null) {
				FileInfo fileInfo = fileServiceClient.findById(listInfo.getFileId());
				FormInfo formInfo = formServiceClient.findByFormId(fileInfo.getFormId());
				data.put("fileStatus", formInfo.getStatus().code());
				data.put("fileStatusMessage", formInfo.getStatus().message());
			} else {
				data.put("fileStatus", listInfo.getStatus().code());
				data.put("fileStatusMessage", listInfo.getStatus().message());
			}
			List<FileInOutInfo> fileInOutInfos = fileServiceClient.getInOutHistory(inputListId);
			if (ListUtil.isNotEmpty(fileInOutInfos)) {
				for (FileInOutInfo info : fileInOutInfos) {
					JSONObject json = new JSONObject();
					json.put("applyType", info.getApplyType());
					json.put("applyUserName", info.getFormUserName());
					json.put("status", info.getFormStatus().code());
					json.put("statusUpdateTime",
						DateUtil.dtSimpleFormat(info.getStatusUpdateTime()));
					json.put("statusMessage", info.getFormStatus().message());
					json.put("rawAddTime", DateUtil.dtSimpleFormat(info.getRawAddTime()));
					if ("入库申请".equals(info.getApplyType())) {
						json.put("viewDetialUrl",
							"/projectMg/file/inputView.htm?formId=" + info.getFormId());
					} else if ("出库申请".equals(info.getApplyType())) {
						json.put("viewDetialUrl",
							"/projectMg/file/outputView.htm?formId=" + info.getFormId());
					} else if ("查阅申请".equals(info.getApplyType())) {
						json.put("viewDetialUrl",
							"/projectMg/file/outputView.htm?formId=" + info.getFormId());
					} else {
						json.put("viewDetialUrl",
							"/projectMg/file/borrowView.htm?formId=" + info.getFormId());
					}
					dataList.add(json);
				}
			}
			data.put("dataList", dataList);
			data.put("fileName", listInfo.getFileName());
			data.put("archiveFileName", listInfo.getArchiveFileName());
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		return result;
	}
	
	/**
	 * 风险处置会会议纪要
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("projectRiskHandle.json")
	@ResponseBody
	public JSONObject projectRiskHandle(CouncilSummaryRiskHandleQueryOrder order, Model model) {
		String tipPrefix = "查询项目风险处置方案";
		JSONObject result = new JSONObject();
		try {
			if (order == null) {
				order = new CouncilSummaryRiskHandleQueryOrder();
			}
			if (DataPermissionUtil.isBusiManager()) {
				order.setBusiManagerId(ShiroSessionUtils.getSessionLocal().getUserId());
			}
			QueryBaseBatchResult<CouncilSummaryRiskHandleInfo> batchResult = councilSummaryServiceClient
				.queryApprovalRiskHandleCs(order);
			if (batchResult != null && batchResult.isSuccess()) {
				JSONObject data = new JSONObject();
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("pageCount", batchResult.getPageCount());
				List<JSONObject> dataList = Lists.newArrayList();
				if (batchResult.getPageList() != null) {
					for (CouncilSummaryRiskHandleInfo info : batchResult.getPageList()) {
						JSONObject json = new JSONObject();
						json.put("projectCode", info.getProjectCode());
						json.put("productName", info.getProjectName());
						json.put("customerId", info.getCustomerId());
						json.put("handleId", info.getHandleId());
						json.put("customerName", info.getCustomerName());
						json.put("summaryCode", info.getSummaryCode());
						json.put("councilId", info.getCouncilId());
						json.put("summaryId", info.getSummaryId());
						json.put("summaryFormId", info.getSummaryFormId());
						json.put("councilSubject", info.getCouncilSubject());
						json.put("councilBeginTime",
							DateUtil.simpleFormat(info.getCouncilBeginTime()));
						dataList.add(json);
					}
				}
				data.put("pageList", dataList);
				result = toStandardResult(data, tipPrefix);
			} else {
				result = toStandardResult(null, tipPrefix);
			}
			
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询项目风险处置方案{}", e);
		}
		
		return result;
	}
	
	/**
	 * 合同
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("projectContract.json")
	@ResponseBody
	public JSONObject projectContract(ProjectContractQueryOrder order, Model model) {
		String tipPrefix = "查询合同信息";
		JSONObject result = new JSONObject();
		try {
			if (order == null) {
				order = new ProjectContractQueryOrder();
			}
			setSessionLocalInfo2Order(order);
			QueryBaseBatchResult<ProjectContractResultInfo> batchResult = projectContractServiceClient
				.query(order);
			if (batchResult != null && batchResult.isSuccess()) {
				JSONObject data = new JSONObject();
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("pageCount", batchResult.getPageCount());
				List<JSONObject> dataList = Lists.newArrayList();
				if (batchResult.getPageList() != null) {
					for (ProjectContractResultInfo info : batchResult.getPageList()) {
						JSONObject json = new JSONObject();
						json.put("projectCode", info.getProjectCode());
						json.put("productName", info.getProjectName());
						json.put("contractCode", info.getContractCode());//合同编号
						json.put("contractName", info.getContractName());//合同名称
						json.put("signedTime", DateUtil.dtSimpleFormat(info.getSignedTime()));//合同签订时间
						
						dataList.add(json);
					}
				}
				data.put("pageList", dataList);
				result = toStandardResult(data, tipPrefix);
			} else {
				result = toStandardResult(null, tipPrefix);
			}
			
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询合同信息{}", e);
		}
		
		return result;
	}
	
	/**
	 * 校验件号是否重复
	 * 
	 * @param model
	 * @return
	 */
	
	@RequestMapping("validataNo.json")
	@ResponseBody
	public boolean validataNo(String no, String projectCode, String type, Long id,
								HttpServletRequest request, Model model) {
		String tipPrefix = "校验件号的合法性";
		try {
			List<FileInputListInfo> listInfos = fileServiceClient.searchArchivedByProjectCode(0,
				type, projectCode, null, no);
			if (ListUtil.isEmpty(listInfos)) {
				return true;
			} else {
				for (FileInputListInfo info : listInfos) {
					if (id != null && id == info.getInputListId()) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		return false;
	}
	
	/**
	 * 检查理财产品名称是否存在
	 * @param model
	 * @return
	 */
	@RequestMapping("checkFinancialProductName.json")
	@ResponseBody
	public boolean checkFinancialProductName(String productName, HttpServletRequest request,
												Model model) {
		FinancialProductInfo product = financialProductServiceClient.findByUnique(productName);
		if (product == null) {
			return true;
		} else {
			return false;
		}
	}
	
	@RequestMapping("createDoc.json")
	@ResponseBody
	public JSONObject createDoc(HttpServletRequest request, String formId, String type,
								String htmlData) {
		String tipPrefix = "生成word文档";
		JSONObject result = new JSONObject();
		try {
			
			String fileName = DOC_MAP.get(type) + formId;
			if (StringUtil.isNotBlank(fileName)) {
				String filePath = fileName + ".doc";
				if (Env.isDev()) {
					filePath = "D:/" + filePath;
				} else {
					filePath = sysParameterServiceClient
						.getSysParameterValue(SysParamEnum.SYS_PARAM_WORD_DOWNLOAD_FOLDER.code())
								+ File.separator + filePath;
				}
				File file = new File(filePath);
				FileOutputStream fos = new FileOutputStream(file);
				Writer os = new OutputStreamWriter(fos, "UTF-8");
				String button = "<div style=\"position: fixed; right: 0;top:50%; background:#e15450;color:#fff;cursor: pointer;padding: 5px;border-radius: 5px;\"><a id=\"btn\">导出</a><br>";
				String button2 = "<a style=\"color: #fff;text-decoration: blink; margin-top:5px; display: block;\" href=\"javascript:history.back(-1)\">返回</a></div>";
				String button3 = "<a style=\"color: #fff;text-decoration: blink; margin-top:5px; display: block;\" href=\"history.back(-1)\">返回</a></div>";
				//保后
				String button4 = "<div id=\"btn\" style=\"position: fixed; right: 0;top:50%; background:#e15450;color:#fff;cursor: pointer;padding: 5px;border-radius: 5px;\">导出</div>";
				String button5 = "<div id=\"btnPrint\" style=\"position: fixed; right: 0;top:50%; margin-top: 30px; background:#e15450;color:#fff;cursor: pointer;padding: 5px;border-radius: 5px;\">打印</div>";
				if (htmlData != null && StringUtil.equals(request.getParameter("decode"), "yes")) {
					//htmlData = URLDecoder.decode(htmlData, "utf-8");
					htmlData = PakoGzipUtils.unCompressURI(htmlData);
				}
				String realPicPath = sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.SYS_PARAM_FACE_WEB_URL.code());
				htmlData = htmlData.replaceAll("/styles/tmbp/img/nopic.jpg",
					realPicPath + "/styles/tmbp/img/nopic.jpg");
				htmlData = htmlData.replaceAll("/styles/tmbp/img/not_img.jpg",
					realPicPath + "/styles/tmbp/img/not_img.jpg");
				htmlData = htmlData.replaceAll(button, "");
				htmlData = htmlData.replaceAll(button2, "");
				htmlData = htmlData.replaceAll(button3, "");
				htmlData = htmlData.replaceAll(button4, "");
				htmlData = htmlData.replaceAll(button5, "");
				
				String ns = "xmlns:o=\"urn:schemas-microsoft-com:office:office\" "
							+ "xmlns:w=\"urn:schemas-microsoft-com:office:word\" "
							+ "xmlns:m=\"http://schemas.microsoft.com/office/2004/12/omml\" "
							+ "xmlns=\"http://www.w3.org/TR/REC-html40\">";
				htmlData = htmlData.replace("<html>", "<html " + ns + ">");
				os.write(htmlData);
				os.flush();
				fos.close();
			}
			
			result.put("success", true);
			result.put("message", "成功");
			result.put("url", "/baseDataLoad/exportDoc.htm?formId=" + formId + "&type=" + type);
			return result;
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		
		return result;
	}
	
	private static final Map<String, String> DOC_MAP = new HashMap<>();
	static {
		DOC_MAP.put("INVESTIGATING_PHASES", "尽调_");
		DOC_MAP.put("AFTERWARDS_PHASES", "保后_");
		DOC_MAP.put("RISK_LEVEL", "风险评级_");
		DOC_MAP.put("SET_UP", "立项_");
		DOC_MAP.put("RISK_REPORT_MONTH", "风险项目情况跟踪月报_");
		DOC_MAP.put("RISK_REPORT_FINAL", "风险项目总结报告_");
		DOC_MAP.put("RISK_REPORT_MAJOR", "重大事项专报_");
		DOC_MAP.put("RISK_HANDLE", "风险处置项目_");
		DOC_MAP.put("FILE_INPUT", "档案入库_");
	}
	
	@RequestMapping("exportDoc.htm")
	public void exportDoc(HttpServletRequest request, HttpServletResponse response, String formId,
							String type) {
		BufferedOutputStream output = null;
		BufferedInputStream input = null;
		
		try {
			String fileName = DOC_MAP.get(type) + formId;
			if (StringUtil.isNotBlank(fileName)) {
				String filePath = sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.SYS_PARAM_WORD_DOWNLOAD_FOLDER.code())
									+ File.separator + fileName + ".doc";
				File file = new File(filePath);
				String exportFileName = fileName;
				if (StringUtil.equals(type, "INVESTIGATING_PHASES")) {
					FInvestigationInfo baseInfo = investigationServiceClient
						.findInvestigationByFormId(NumberUtil.parseLong(formId));
					if (baseInfo != null) {
						exportFileName = baseInfo.getProjectName();
					}
				}
				response.setHeader("Content-disposition",
					"attachment; filename="
							+ new String(exportFileName.getBytes("GB2312"), "ISO8859-1") + ".doc");
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
				file.delete(); //删除临时文件
				output.flush(); //不可少
				response.flushBuffer();//不可少
			}
		} catch (Exception e) {
			//异常自己捕捉
			logger.error("导出WORD异常：" + e);
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
	
	/** 查询可用渠道合同列表 */
	@ResponseBody
	@RequestMapping("channalContrat.json")
	public JSONObject channalContrat(HttpServletRequest request) {
		String tipPrefix = "查询可用渠道合同";
		JSONObject result = new JSONObject();
		try {
			ChannalContractQueryOrder queryOrder = new ChannalContractQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			//合同状态设置成 已回传
			queryOrder.setStatus("RETURN");
			
			if (StringUtil.isNotBlank(request.getParameter(("channelType")))) {
				queryOrder.setChannalType(request.getParameter(("channelType")));
			}
			if (StringUtil.isNotBlank(request.getParameter(("channelCode")))) {
				queryOrder.setChannalCode(request.getParameter(("channelCode")));
			}
			if (StringUtil.isBlank(queryOrder.getChannalCode())) {
				//渠道号为空，默认为新增渠道 排除已存在渠道的合同
				queryOrder.setInfo1("IS");
				
			}
			QueryBaseBatchResult<ChannalContractInfo> batchResult = channalContractClient
				.list(queryOrder);
			
			if (batchResult != null && batchResult.isSuccess()) {
				JSONObject data = new JSONObject();
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("pageCount", batchResult.getPageCount());
				List<JSONObject> dataList = Lists.newArrayList();
				if (batchResult.getPageList() != null) {
					for (ChannalContractInfo info : batchResult.getPageList()) {
						JSONObject json = new JSONObject();
						json.put("contractNo", info.getContractNo());
						json.put("channalName", info.getChannalName());
						json.put("channalCode", info.getChannalCode());
						json.put("channalType", ChanalTypeEnum.getMsgByCode(info.getChannalType()));
						json.put("contract", info.getContract());
						json.put("contractReturn", info.getContractReturn());
						json.put("rawAddTime", DateUtil.simpleFormat(info.getRawAddTime()));
						dataList.add(json);
					}
				}
				data.put("pageList", dataList);
				result = toStandardResult(data, tipPrefix);
			} else {
				result = toStandardResult(null, tipPrefix);
			}
			
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询可用渠道合同异常：", e);
		}
		
		return result;
	}
	
	@RequestMapping("fenbaoInfo.htm")
	public String fenbaoInfo(String projectCode, Model model) {
		if (StringUtil.isNotBlank(projectCode) && !projectCode.startsWith("JJ")
			&& !projectCode.startsWith("QB")
			&& ProjectUtil.isBond(ProjectUtil.getBusiTypeByCode(projectCode))) {
			List<ProjectBondReinsuranceInformationInfo> fbList = projectBondReinsuranceInformationServiceClient
				.findByProjectCode(projectCode);
			if (fbList != null && !fbList.isEmpty()) {
				model.addAttribute("fenbaoList", fbList);
			}
		}
		return "/layout/common/fenbaoList.vm";
	}
	
	/** 查询项目资金划付/收费财务确认信息 */
	@ResponseBody
	@RequestMapping("loadChargePay.json")
	public JSONObject loadChargePay(HttpServletRequest request) {
		String tipPrefix = "查询项目资金划付/收费财务确认信息";
		JSONObject result = new JSONObject();
		try {
			ProjectChargePayQueryOrder order = new ProjectChargePayQueryOrder();
			WebUtil.setPoPropertyByRequest(order, request);
			order
				.setExpectFormId(StringUtil.isNotBlank(request.getParameter("expectFormId")) ? Long
					.parseLong(request.getParameter("expectFormId")) : 0);
			order.setReturnAmountStr("1");//用于查询退还剩余金额大于0，传任何值都可以
			if (StringUtil.isEmpty(order.getSortCol())) {
				order.setSortCol("p.return_customer_amount");
				order.setSortOrder("ASC");
			}
			QueryBaseBatchResult<ProjectChargePayInfo> batchResult = financeAffirmServiceClient
				.queryProjectChargePayDetailChoose(order);
			if (batchResult != null && batchResult.isSuccess()) {
				JSONObject data = new JSONObject();
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("pageCount", batchResult.getPageCount());
				List<JSONObject> dataList = Lists.newArrayList();
				if (batchResult.getPageList() != null) {
					for (ProjectChargePayInfo info : batchResult.getPageList()) {
						JSONObject json = new JSONObject();
						if ("CHARGE_NOTIFICATION".equals(info.getAffirmFormType())) {
							json.put("feeType", info.getChargeType().code());
							json.put("feeTypeName", info.getChargeType().message());
						} else if ("CAPITAL_APPROPROATION_APPLY".equals(info.getAffirmFormType())) {
							json.put("feeType", info.getPayType().code());
							json.put("feeTypeName", info.getPayType().message());
						}
						json.put("payId", info.getPayId());
						json.put("payTime", DateUtil.dtSimpleFormat(info.getPayTime()));
						json.put("payAmount", info.getPayAmount());
						json.put("payAmountStr", info.getPayAmount().toStandardString());
						json.put("returnAmount", info.getReturnCustomerAmount());
						json.put("returnAmountStr", info.getReturnCustomerAmount()
							.toStandardString());
						dataList.add(json);
					}
				}
				data.put("pageList", dataList);
				result = toStandardResult(data, tipPrefix);
			} else {
				result = toStandardResult(null, tipPrefix);
			}
			
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询项目资金划付/收费财务确认信息 ：", e);
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("newChannalCode.json")
	public JSONObject newChannalCode(String channalType) {
		JSONObject json = new JSONObject();
		if (StringUtil.isBlank(channalType)) {
			json.put("success", false);
			json.put("message", "渠道类型不能为空！");
		} else {
			String code = channalClient.createChannalCode(channalType);
			if (StringUtil.isNotBlank(code)) {
				json.put("success", true);
				json.put("message", "生成渠道号成功！");
				json.put("data", code);
			} else {
				json.put("success", false);
				json.put("message", "生成渠道号失败！");
			}
		}
		return json;
	}
	
	/**
	 * 
	 * @param depPath 接收人部门path
	 * @param provinceCode 待分配客户省编号
	 * 
	 * */
	@ResponseBody
	@RequestMapping("orgId.json")
	public JSONObject orgId(String depName) {
		JSONObject json = new JSONObject();
		if (StringUtil.isBlank(depName)) {
			json.put("success", false);
			json.put("message", "部门名不能为空");
		} else {
			FcsBaseResult result = customerServiceClient.allCustomerDept();
			if (result.isSuccess()) {
				JSONObject jData = JSONObject.parseObject(result.getMessage());
				BigInteger orgId = jData.getBigInteger(depName);
				json.put("success", true);
				json.put("data", orgId);
				json.put("message", "部门查询成功");
			} else {
				json.put("success", false);
				json.put("message", "查询失败");
			}
			
		}
		
		return json;
	}
	
	@RequestMapping("switchPrimaryOrg.json")
	@ResponseBody
	public JSONObject switchPrimaryOrg(long orgId) {
		JSONObject json = new JSONObject();
		if (orgId <= 0) {
			json.put("success", false);
			json.put("message", "请选择部门");
			return json;
		}
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal == null || sessionLocal.getUserDetailInfo() == null) {
			json.put("success", false);
			json.put("message", "登陆已失效");
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
		json.put("success", true);
		json.put("message", "切换成功");
		return json;
	}
	
	@RequestMapping("getContractItem.json")
	@ResponseBody
	public JSONObject getContractItem(long formId) {
		String tipPrefix = "查询表单所有子合同";
		JSONObject result = new JSONObject();
		if (formId <= 0) {
			result.put("success", false);
			result.put("message", "请选择要删除的合同");
			return result;
		}
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal == null || sessionLocal.getUserDetailInfo() == null) {
			result.put("success", false);
			result.put("message", "登陆已失效");
			return result;
		}
		List<JSONObject> dataList = Lists.newArrayList();
		ProjectContrctInfo info = projectContractServiceClient.findByFormId(formId);
		JSONObject data = new JSONObject();
		List<ProjectContractItemInfo> itemInfos = info.getProjectContractItemInfos();
		if (ListUtil.isNotEmpty(itemInfos)) {
			for (ProjectContractItemInfo itemInfo : itemInfos) {
				JSONObject json = new JSONObject();
				json.put("contractCode", itemInfo.getContractCode());
				json.put("contractName", itemInfo.getContractName());
				dataList.add(json);
			}
		}
		data.put("pageList", dataList);
		result = toStandardResult(data, tipPrefix);
		return result;
	}
	
	/**
	 * 选择收费、函通知书项目
	 * @param request
	 * @return
	 */
	@RequestMapping("chargeNotificationOrLetterProject.json")
	@ResponseBody
	public JSONObject chargeNotificationOrLetterProject(HttpServletRequest request) {
		
		SessionLocal session = ShiroSessionUtils.getSessionLocal();
		String tipPrefix = "查询收费、函通知申请项目";
		JSONObject result = new JSONObject();
		try {
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			ProjectQueryOrder queryOrder = new ProjectQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			queryOrder.setHasContract(BooleanEnum.getByCode(request.getParameter("hasContract")));
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
			String phasesStatusStrList = request.getParameter("phasesStatusList");
			if (StringUtil.isNotBlank(phasesStatusStrList)) {
				List<ProjectPhasesStatusEnum> phasesStatusList = new ArrayList<>();
				String[] phases = phasesStatusStrList.split(",");
				for (String s : phases) {
					ProjectPhasesStatusEnum e = ProjectPhasesStatusEnum.getByCode(s);
					if (null != e) {
						phasesStatusList.add(e);
					}
				}
				queryOrder.setPhasesStatusList(phasesStatusList);
			}
			String statusStrList = request.getParameter("statusList");
			if (StringUtil.isNotBlank(statusStrList)) {
				List<ProjectStatusEnum> statusList = new ArrayList<>();
				String[] phases = statusStrList.split(",");
				for (String s : phases) {
					ProjectStatusEnum e = ProjectStatusEnum.getByCode(s);
					if (null != e) {
						statusList.add(e);
					}
				}
				queryOrder.setStatusList(statusList);
			}
			
			setSessionLocalInfo2Order(queryOrder);
			
			//当前业务经理
			queryOrder.setBusiManagerId(session.getUserId());
			
			QueryBaseBatchResult<ProjectSimpleDetailInfo> batchResult = chargeNotificationServiceClient
				.selectChargeNotificationProject(queryOrder);
			List<ProjectSimpleDetailInfo> list = batchResult.getPageList();
			for (ProjectSimpleDetailInfo info : list) {
				JSONObject json = new JSONObject();
				json.put("projectCode", info.getProjectCode());
				json.put("projectName", info.getProjectName());
				json.put("customerId", info.getCustomerId());
				json.put("customerName", info.getCustomerName());
				json.put("amount", info.getAmount().toStandardString());
				json.put("busiType", info.getBusiType());
				json.put("busiTypeName", info.getBusiTypeName());
				if (info.getChargeType() != null) {
					json.put("chargeFee", CommonUtil.numberFormat(info.getChargeFee(), 2)
											+ info.getChargeType().unit());
				} else {
					json.put("chargeFee", CommonUtil.numberFormat(info.getChargeFee(), 2));
				}
				json.put("chargeName", info.getChargeName());
				if (info.getTimeUnit() != null) {
					json.put("timeLimit", info.getTimeLimit() + info.getTimeUnit().viewName());
				} else {
					json.put("timeLimit", "-");
				}
				dataList.add(json);
			}
			data.put("pageCount", batchResult.getPageCount());
			data.put("pageNumber", batchResult.getPageNumber());
			data.put("pageSize", batchResult.getPageSize());
			data.put("totalCount", batchResult.getTotalCount());
			data.put("pageList", dataList);
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询收费、函通知申请项目 {}", e);
		}
		
		return result;
		
	}
	
	/**
	 * 印章配置
	 * @param request
	 * @return
	 */
	@RequestMapping("stampConfig.json")
	@ResponseBody
	public JSONObject stampConfig(HttpServletRequest request) {
		
		String tipPrefix = "印章配置";
		JSONObject result = new JSONObject();
		try {
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			List<StampConfigureListInfo> listInfos = stampApplyServiceClient.getStampConfig();
			for (StampConfigureListInfo info : listInfos) {
				JSONObject json = new JSONObject();
				json.put("orgId", info.getOrgId());
				json.put("orgName", info.getOrgName());
				json.put("gzRoleCode", info.getGzRoleCode());
				json.put("frzRoleCode", info.getFrzRoleCode());
				dataList.add(json);
			}
			data.put("pageList", dataList);
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询印章配置 {}", e);
		}
		return result;
	}
	
	//上传excel数据(单表)
	@RequestMapping("uploadExcel.htm")
	public void uploadExcel(HttpServletRequest request, HttpServletResponse response, String type) {
		JSONObject json = new JSONObject();
		try {
			ExcelData excelData = ExcelUtil.uploadExcel1(request);
			if (null != excelData) {
				json.put("success", true);
				json.put("message", "上传成功");
				json.put("datas", excelData.getList());
			} else {
				json.put("success", false);
				json.put("message", "上传失败");
			}
			
		} catch (BiffException | FileUploadException | IOException e) {
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
	
	@RequestMapping("toCny.json")
	@ResponseBody
	public JSONObject toCny(String currencyCode, String currencyAmount) {
		JSONObject json = new JSONObject();
		CnyResult result = YahooExchangeRateUtil.toCny(currencyCode, currencyAmount);
		if (result != null && result.isSuccess()) {
			json.put("success", true);
			json.put("message", "转换成功");
			JSONObject data = new JSONObject();
			data.put("amount", result.getAmount().toStandardString());
			data.put("currencyAmount", result.getCurrencyAmount());
			data.put("currencyCode", result.getCurrencyCode());
			data.put("exchangeRate", result.getExchangeRate());
			data.put("exchangeTime", DateUtil.simpleDate(result.getExchangeTime()));
			json.put("data", data);
		} else {
			json.put("success", false);
			json.put("message", result == null ? "转换失败" : result.getMessage());
		}
		return json;
	}
	
	@RequestMapping("saveAttach.json")
	@ResponseBody
	public JSONObject saveAttach(String bizNo, String childId, String moduleType, String attach,
									String projectCode, String remark, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {
			
			if (StringUtil.isBlank(moduleType) && StringUtil.isBlank(bizNo)) {
				json.put("success", false);
				json.put("message", "请求数据不完整");
				return json;
			}
			
			CommonAttachmentTypeEnum mType = CommonAttachmentTypeEnum.getByCode(moduleType);
			if (mType == null) {
				json.put("success", false);
				json.put("message", "附件模块未定义");
				return json;
			}
			
			//上传人
			long uploaderId = 0L;
			String uploaderAccount = "";
			String uploaderName = "";
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal != null) {
				uploaderId = sessionLocal.getUserId();
				uploaderAccount = sessionLocal.getUserName();
				uploaderName = sessionLocal.getRealName();
			}
			
			FcsBaseResult result = null;
			//先删除，再保存
			if (StringUtil.isNotBlank(childId)) {
				result = commonAttachmentServiceClient.deleteByBizNoAndChildIdModuleType(bizNo,
					childId, mType);
			} else {
				result = commonAttachmentServiceClient.deleteByBizNoModuleType(bizNo, mType);
			}
			
			if (StringUtil.isNotBlank(attach)) {
				List<CommonAttachmentOrder> orders = Lists.newArrayList();
				String[] attachPaths = attach.split(";");
				int j = 1;
				for (String path : attachPaths) {
					String paths[] = path.split(",");
					if (null != paths && paths.length >= 3) {
						CommonAttachmentOrder commonAttachmentOrder = new CommonAttachmentOrder();
						commonAttachmentOrder.setUploaderId(uploaderId);
						commonAttachmentOrder.setUploaderAccount(uploaderAccount);
						commonAttachmentOrder.setUploaderName(uploaderName);
						commonAttachmentOrder.setBizNo(bizNo);
						commonAttachmentOrder.setChildId(childId);
						commonAttachmentOrder.setModuleType(mType);
						commonAttachmentOrder.setFileName(paths[0]);
						commonAttachmentOrder.setFilePhysicalPath(paths[1]);
						commonAttachmentOrder.setRequestPath(paths[2]);
						commonAttachmentOrder.setCheckStatus(CheckStatusEnum.CHECK_PASS.code());
						commonAttachmentOrder.setProjectCode(projectCode);
						commonAttachmentOrder.setRemark(remark);
						commonAttachmentOrder.setIsort(j++);
						orders.add(commonAttachmentOrder);
					}
				}
				result = commonAttachmentServiceClient.insertAll(orders);
			}
			
			if (result != null && result.isSuccess()) {
				json.put("success", true);
				json.put("message", "上传附件成功");
			} else {
				if (result != null && result.isSuccess()) {
					json.put("success", false);
					json.put("message", "上传附件出错");
					logger.error("上传附件出错:{}", result);
				}
			}
		} catch (Exception e) {
			logger.error("上传附件出错:{}", e);
			json.put("success", false);
			json.put("message", "上传附件出错");
		}
		return json;
	}
	
	@RequestMapping("runRegularReport.json")
	@ResponseBody
	public JSONObject runRegularReport(String reportDate) {
		JSONObject json = new JSONObject();
		FcsBaseResult result = reportServiceClient.runRegularReport(reportDate);
		if (result != null && result.isSuccess()) {
			json.put("success", true);
			json.put("message", "调用成功");
		} else {
			json.put("success", false);
			json.put("message", result == null ? "调用失败" : result.getMessage());
		}
		return json;
	}
	
	@RequestMapping("checkOneVote.json")
	@ResponseBody
	public JSONObject checkOneVote(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		long summaryId = NumberUtil.parseLong(request.getParameter("summaryId"));
		if (summaryId > 0) {
			//尽调附件管理 (项目编号：2017-0300-011-058)
			List<FCouncilSummaryProjectInfo> projects = councilSummaryServiceClient
				.queryProjectCsBySummaryId(summaryId);
			String message = null;
			if (ListUtil.isNotEmpty(projects)) {
				for (FCouncilSummaryProjectInfo project : projects) {
					if (project.getVoteResult() == ProjectVoteResultEnum.END_PASS
						&& project.getOneVoteDown() == null) {
						if (message == null) {
							message = project.getProjectName() + "(项目编号："
										+ project.getProjectCode() + ")";
						} else {
							message += "、" + project.getProjectName() + "(项目编号："
										+ project.getProjectCode() + ")";
						}
					}
				}
				if (message != null) {
					message += "，尚未发表意见";
					json.put("success", false);
					json.put("message", message);
				} else {
					json.put("success", true);
					json.put("message", "可提交");
				}
			} else {
				json.put("success", false);
				json.put("message", "检查是否发表意见出错[未查询到会议纪要项目]");
			}
		} else {
			json.put("success", false);
			json.put("message", "检查是否发表意见出错[会议纪要ID未知]");
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("queryRecovery.json")
	public Object queryRecovery(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String tipPrefix = "查询项目追偿信息信息";
		
		try {
			ProjectQueryOrder queryOrder = new ProjectQueryOrder();
			
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			List<ProjectRecoveryInfo> recoveryList = projectRecoveryServiceClient
				.findByProjectCode(request.getParameter("projectCode"), false);
			String recoveryIdStr = request.getParameter("recoveryId");
			long currRecoveryId = recoveryIdStr == null ? 0 : NumberUtil.parseLong(recoveryIdStr);
			if (ListUtil.isNotEmpty(recoveryList)) {
				for (ProjectRecoveryInfo info : recoveryList) {
					if (info.getId() == currRecoveryId)
						continue;
					JSONObject json = new JSONObject();
					json.put("projectCode", info.getProjectCode());
					json.put("recoveryId", info.getId());
					json.put("recoveryStatus", info.getRecoveryStatus() == null ? "-" : info
						.getRecoveryStatus().message());
					json.put("statusUpdateTime", info.getStatusUpdateTime() == null ? "-"
						: DateUtil.simpleFormat(info.getStatusUpdateTime()));
					if (StringUtil.isBlank(info.getRecoveryName())) {
						json.put("recoveryName",
							info.getProjectCode() + "-" + json.getString("recoveryStatus"));
					} else {
						json.put("recoveryName", info.getRecoveryName());
					}
					dataList.add(json);
				}
			}
			data.put("pageList", dataList);
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		return result;
	}
	
	/**
	 * 征信报告查询
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("queryCredit.json")
	public Object queryCredit(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String tipPrefix = "查询征信报告";
		
		try {
			CreditRefrerenceApplyQueryOrder queryOrder = new CreditRefrerenceApplyQueryOrder();
			
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			QueryBaseBatchResult<CreditRefrerenceApplyReusltInfo> batchResult = creditRefrerenceApplyServiceClient
				.findCreditRefrerenceApplyInfos(queryOrder);
			List<CreditRefrerenceApplyReusltInfo> recoveryList = batchResult.getPageList();
			if (ListUtil.isNotEmpty(recoveryList)) {
				for (CreditRefrerenceApplyReusltInfo info : recoveryList) {
					JSONObject json = new JSONObject();
					json.put("projectCode", info.getProjectCode());
					json.put("id", info.getId());
					json.put("formId", info.getFormId());
					json.put("companyName", info.getCompanyName());
					json.put("projectName", info.getProjectName());
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
}
