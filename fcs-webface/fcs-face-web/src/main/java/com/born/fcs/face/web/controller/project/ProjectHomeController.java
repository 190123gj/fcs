package com.born.fcs.face.web.controller.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.com.google.common.collect.Maps;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.face.webui.table.ReportTemplate;
import com.born.face.webui.table.TableBuilder;
import com.born.fcs.crm.ws.service.info.CompanyCustomerInfo;
import com.born.fcs.crm.ws.service.info.EvaluetingListAuditInfo;
import com.born.fcs.crm.ws.service.info.PersonalCustomerInfo;
import com.born.fcs.crm.ws.service.order.query.EvaluetingListQueryOrder;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.pm.service.common.ProjectImportServiceClient;
import com.born.fcs.face.integration.result.ProjectCompResult;
import com.born.fcs.face.integration.service.ProjectRelatedDataService;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.ExcelData;
import com.born.fcs.face.web.util.ExcelUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CheckStatusEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.ExeStatusEnum;
import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.LoanUseApplyTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectExtendPropertyEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyInfo;
import com.born.fcs.pm.ws.info.check.AfterwardsCheckInfo;
import com.born.fcs.pm.ws.info.common.CommonAttachmentInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.FormRelatedUserInfo;
import com.born.fcs.pm.ws.info.common.ProjectApprovaInfo;
import com.born.fcs.pm.ws.info.common.ProjectChannelRelationInfo;
import com.born.fcs.pm.ws.info.common.ProjectExtendInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractResultInfo;
import com.born.fcs.pm.ws.info.council.CouncilApplyRiskHandleInfo;
import com.born.fcs.pm.ws.info.council.CouncilSummaryRiskHandleInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectCreditConditionInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectPledgeAssetInfo;
import com.born.fcs.pm.ws.info.council.ReCouncilApplyFormInfo;
import com.born.fcs.pm.ws.info.creditrefrerenceapply.CreditRefrerenceApplyReusltInfo;
import com.born.fcs.pm.ws.info.excel.OldProjectExcelDetailInfo;
import com.born.fcs.pm.ws.info.excel.OldProjectExcelInfo;
import com.born.fcs.pm.ws.info.financeaffirm.ProjectChargePayInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationAssetReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCreditSchemeInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCreditSchemePledgeAssetInfo;
import com.born.fcs.pm.ws.info.finvestigation.InvestigationInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeApplySearchInfo;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationFeeInfo;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationInfo;
import com.born.fcs.pm.ws.info.fund.FLoanUseApplyReceiptInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.ProjectCreditConditionInfo;
import com.born.fcs.pm.ws.info.risklevel.RiskLevelInfo;
import com.born.fcs.pm.ws.info.setup.SetupFormProjectInfo;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyQueryOrder;
import com.born.fcs.pm.ws.order.check.AfterwordsCheckQueryOrder;
import com.born.fcs.pm.ws.order.common.CommonAttachmentQueryOrder;
import com.born.fcs.pm.ws.order.common.FormRelatedUserQueryOrder;
import com.born.fcs.pm.ws.order.common.ProjectApprovalQueryOrder;
import com.born.fcs.pm.ws.order.common.ProjectExtendFormOrder;
import com.born.fcs.pm.ws.order.common.ProjectExtendOrder;
import com.born.fcs.pm.ws.order.common.ProjectExtendQueryOrder;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.common.TransferProjectOrder;
import com.born.fcs.pm.ws.order.contract.ProjectContractQueryOrder;
import com.born.fcs.pm.ws.order.council.CouncilApplyRiskHandleQueryOrder;
import com.born.fcs.pm.ws.order.council.CouncilSummaryRiskHandleQueryOrder;
import com.born.fcs.pm.ws.order.council.ReCouncilApplyQueryOrder;
import com.born.fcs.pm.ws.order.creditrefrerenceapply.CreditRefrerenceApplyQueryOrder;
import com.born.fcs.pm.ws.order.financeaffirm.ProjectChargePayQueryOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationAssetReviewQueryOrder;
import com.born.fcs.pm.ws.order.finvestigation.InvestigationQueryOrder;
import com.born.fcs.pm.ws.order.formchange.FormChangeApplyQueryOrder;
import com.born.fcs.pm.ws.order.project.ProjectRedoOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.ProjectCreditConditionQueryOrder;
import com.born.fcs.pm.ws.order.risklevel.RiskLevelQueryOrder;
import com.born.fcs.pm.ws.order.setup.SetupFormQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.common.ProjectImportBatchResult;
import com.born.fcs.pm.ws.result.common.ProjectImportResult;
import com.born.fcs.pm.ws.service.common.ProjectChannelRelationService;
import com.born.fcs.pm.ws.service.report.ProjectReportService;
import com.born.fcs.pm.ws.service.report.info.ProjectChargeDetailInfo;
import com.born.fcs.pm.ws.service.report.info.ProjectRepayDetailInfo;
import com.born.fcs.pm.ws.service.report.order.ProjectChargeDetailQueryOrder;
import com.born.fcs.pm.ws.service.report.order.ProjectRepayDetailQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;

@Controller
@RequestMapping("projectMg")
public class ProjectHomeController extends WorkflowBaseController {
	
	final static String vm_path = "/projectMg/";
	
	@Autowired
	ProjectRelatedDataService projectRelatedDataService;
	@Autowired
	ProjectChannelRelationService projectChannelRelationServiceClient;
	@Autowired
	ProjectReportService projectReportServiceClient;
	@Autowired
	ProjectImportServiceClient projectImportServiceClient;
	
	@RequestMapping("index.htm")
	public String mainIndex(HttpServletRequest request, HttpServletResponse response, Model model) {
		buildSystemNameDefaultUrl(request, model);
		return vm_path + "index.vm";
	}
	
	@RequestMapping("list.htm")
	public String list(ProjectQueryOrder order, HttpServletRequest request, Model model) {
		
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		
		setSessionLocalInfo2Order(order);
		
		model.addAttribute("queryOrder", order);
		model.addAttribute("phaseList", ProjectPhasesEnum.getAllEnum());
		model.addAttribute("isBusiManager", isBusiManager());
		//是否业务总监
		boolean isBusiDirector = DataPermissionUtil.isBusiFZR()
									|| DataPermissionUtil.isBusinessFgfz();
		//是否风险总监
		boolean isRiskDirector = DataPermissionUtil.isRiskFZR() || DataPermissionUtil.isRiskFgfz();
		//是否法务部负责人
		String legalLeader = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_LEGAL_MANAGER_LD_ROLE_NAME.code());//法务部领导
		boolean isLegalDirector = DataPermissionUtil.hasRole(legalLeader)
									|| DataPermissionUtil.isLegalFgfz();
		//是否系统管理员
		boolean isAdmin = DataPermissionUtil.isSystemAdministrator()
							|| DataPermissionUtil.isChairMan()
							|| DataPermissionUtil.isGeneralManager();
		model.addAttribute("isBusiDirector", isBusiDirector);
		model.addAttribute("isRiskDirector", isRiskDirector);
		model.addAttribute("isLegalDirector", isLegalDirector);
		model.addAttribute("isAdmin", isAdmin);
		
		if (StringUtil.isBlank(order.getSortCol())) {
			order.setSortCol("project_id");
			order.setSortOrder("DESC");
		}
		
		String projectRelationship = request.getParameter("projectRelationship");
		if (StringUtil.equals(projectRelationship, "my")) { //我发起的
			order.setBusiManagerId(sessionLocal.getUserId());
		} else if (StringUtil.equals(projectRelationship, "participation")) { //我参与的
			order.setLoginUserId(sessionLocal.getUserId());
			List<ProjectRelatedUserTypeEnum> relatedRoleList = Lists.newArrayList();
			relatedRoleList.add(ProjectRelatedUserTypeEnum.BUSI_MANAGERB);
			relatedRoleList.add(ProjectRelatedUserTypeEnum.COUNCIL_JUDGE);
			relatedRoleList.add(ProjectRelatedUserTypeEnum.LEGAL_MANAGER);
			relatedRoleList.add(ProjectRelatedUserTypeEnum.RISK_MANAGER);
			order.setRelatedRoleList(relatedRoleList);
		}
		model.addAttribute("projectRelationship", projectRelationship);
		
		QueryBaseBatchResult<ProjectInfo> pList = projectServiceClient.queryProject(order);
		Map<String, String> riskManagerMap = Maps.newHashMap();
		Map<String, String> legalManagerMap = Maps.newHashMap();
		Map<String, Money> compBackMap = Maps.newHashMap();
		Map<String, FormInfo> summaryFormMap = Maps.newHashMap();
		if (pList != null) {
			ProjectChargeDetailQueryOrder sfOrder = new ProjectChargeDetailQueryOrder();
			List<String> feeTypeList = Lists.newArrayList();
			feeTypeList.add(FeeTypeEnum.COMPENSATORY_PRINCIPAL_WITHDRAWAL.code());
			sfOrder.setFeeTypeList(feeTypeList);
			SetupFormQueryOrder setupQueryOrder = new SetupFormQueryOrder();
			setupQueryOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
			setupQueryOrder.setPageNumber(1L);
			setupQueryOrder.setPageSize(1L);
			for (ProjectInfo project : pList.getPageList()) {
				//查询风险经理
				//if ((isRiskDirector || isAdmin)) {
				ProjectRelatedUserInfo relatedUser = projectRelatedUserServiceClient
					.getRiskManager(project.getProjectCode());
				if (relatedUser != null) {
					riskManagerMap.put(project.getProjectCode(), relatedUser.getUserName());
				}
				//}
				//查询法务经理
				if ((isLegalDirector || isAdmin)) {
					ProjectRelatedUserInfo legalManager = projectRelatedUserServiceClient
						.getLegalManager(project.getProjectCode());
					if (legalManager != null) {
						legalManagerMap.put(project.getProjectCode(), legalManager.getUserName());
					}
				}
				//查询项目小结
				ProjectExtendQueryOrder qOrder = new ProjectExtendQueryOrder();
				qOrder.setProjectCode(project.getProjectCode());
				qOrder.setPageSize(100);
				QueryBaseBatchResult<ProjectExtendInfo> qResult = projectExtendServiceClient
					.query(qOrder);
				if (qResult != null && qResult.isSuccess() && qResult.getTotalCount() > 0) {
					summaryFormMap.put(project.getProjectCode(),
						formServiceClient.findByFormId(qResult.getPageList().get(0).getFormId()));
				}
				//查询代偿本金收回
				sfOrder.setProjectCode(project.getProjectCode());
				QueryBaseBatchResult<ProjectChargeDetailInfo> compBack = projectReportServiceClient
					.projectChargeDetail(sfOrder);
				Money compBackAmount = Money.zero();
				if (compBack != null && ListUtil.isNotEmpty(compBack.getPageList())) {
					for (ProjectChargeDetailInfo back : compBack.getPageList()) {
						compBackAmount.addTo(back.getChargeAmount());
					}
				}
				compBackMap.put(project.getProjectCode(), compBackAmount);
				
				//获取项目立项审核通过时间 2017-04-24 lirz
				setupQueryOrder.setProjectCode(project.getProjectCode());
				QueryBaseBatchResult<SetupFormProjectInfo> setups = projectSetupServiceClient
					.querySetupForm(setupQueryOrder);
				if (null != setups && ListUtil.isNotEmpty(setups.getPageList())) {
					project.setRawAddTime(setups.getPageList().get(0).getFinishTime());
				}
			}
		}
		
		//风险经理
		model.addAttribute("riskManagerMap", riskManagerMap);
		model.addAttribute("legalManagerMap", legalManagerMap);
		model.addAttribute("summaryFormMap", summaryFormMap);
		model.addAttribute("compBackMap", compBackMap);
		model.addAttribute("page", PageUtil.getCovertPage(pList));
		
		return vm_path + "project/list.vm";
	}
	
	@RequestMapping("search.htm")
	public String search(ProjectQueryOrder order, HttpServletRequest request, Model model) {
		
		QueryBaseBatchResult<ProjectInfo> pList = new QueryBaseBatchResult<ProjectInfo>();
		if (order == null)
			order = new ProjectQueryOrder();
		if (StringUtil.isBlank(order.getProjectCodeOrName())
			&& StringUtil.isBlank(order.getCustomerName())
			&& StringUtil.isBlank(order.getProjectName())
			&& StringUtil.isBlank(order.getBusiType())) {
			model.addAttribute("noCondition", true);
		} else {
			//setSessionLocalInfo2Order(order);
			model.addAttribute("queryOrder", order);
			//model.addAttribute("phaseList", ProjectPhasesEnum.getAllEnum());
			model.addAttribute("isBusiManager", isBusiManager());
			
			if (StringUtil.isBlank(order.getSortCol())) {
				order.setSortCol("project_id");
				order.setSortOrder("DESC");
			}
			
			pList = projectServiceClient.queryProject(order);
			Map<String, Money> compBackMap = Maps.newHashMap();
			if (pList != null) {
				ProjectChargeDetailQueryOrder sfOrder = new ProjectChargeDetailQueryOrder();
				List<String> feeTypeList = Lists.newArrayList();
				feeTypeList.add(FeeTypeEnum.COMPENSATORY_PRINCIPAL_WITHDRAWAL.code());
				sfOrder.setFeeTypeList(feeTypeList);
				for (ProjectInfo project : pList.getPageList()) {
					//查询代偿本金收回
					sfOrder.setProjectCode(project.getProjectCode());
					QueryBaseBatchResult<ProjectChargeDetailInfo> compBack = projectReportServiceClient
						.projectChargeDetail(sfOrder);
					Money compBackAmount = Money.zero();
					if (compBack != null && ListUtil.isNotEmpty(compBack.getPageList())) {
						for (ProjectChargeDetailInfo back : compBack.getPageList()) {
							compBackAmount.addTo(back.getChargeAmount());
						}
					}
					compBackMap.put(project.getProjectCode(), compBackAmount);
				}
			}
			model.addAttribute("compBackMap", compBackMap);
		}
		
		model.addAttribute("page", PageUtil.getCovertPage(pList));
		
		return vm_path + "project/search.vm";
	}
	
	/***
	 * 导出
	 */
	@RequestMapping("projectExport.htm")
	public String projectExport(ProjectQueryOrder order, HttpServletRequest request,
								HttpServletResponse response, Model model) {
		
		if (order == null)
			order = new ProjectQueryOrder();
		
		try {
			setSessionLocalInfo2Order(order);
			
			ReportDataResult result = projectRelatedDataService.projectListExportData(order);
			
			if (result != null && result.isSuccess()) {
				List<DataListItem> items = result.getDataList();
				if (ListUtil.isNotEmpty(items)) {
					for (DataListItem item : items) {
						Date startTime = (Date) item.getMap().get("start_time");
						Date endTime = (Date) item.getMap().get("end_time");
						if (startTime != null)
							item.getMap().put("start_time", DateUtil.dtSimpleFormat(startTime));
						if (endTime != null)
							item.getMap().put("end_time", DateUtil.dtSimpleFormat(endTime));
						
					}
					ReportTemplate reportTemplate = new ReportTemplate();
					reportTemplate.setReportName("项目明细表");
					TableBuilder builder = new TableBuilder(result, reportTemplate,
						TableBuilder.Table_Option_Excel, false);
					builder.init();
					builder.dataBind(request, response);
				}
			}
		} catch (Exception e) {
			logger.error("导出项目明细表出错：{}", e);
		}
		return null;
	}
	
	/**
	 * 项目批复列表
	 * @param order
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("approvalList.htm")
	public String approvalList(ProjectApprovalQueryOrder order, HttpServletRequest request,
								Model model) {
		
		setSessionLocalInfo2Order(order);
		
		model.addAttribute("queryOrder", order);
		model.addAttribute("busiTypeName", request.getParameter("busiTypeName"));
		
		if (StringUtil.isBlank(order.getSortCol())) {
			order.setSortCol("approval_time");
			order.setSortOrder("DESC");
		}
		
		QueryBaseBatchResult<ProjectApprovaInfo> pList = projectServiceClient
			.queryProjectApproval(order);
		model.addAttribute("page", PageUtil.getCovertPage(pList));
		
		return vm_path + "project/approvalList.vm";
	}
	
	/**
	 * 项目批复
	 * @param order
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("approval.htm")
	public String approvalList(long spId, HttpServletRequest request, Model model) {
		
		if (spId <= 0)
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "批复ID必须大于0 ");
		
		FCouncilSummaryProjectInfo councilSummaryProject = councilSummaryServiceClient
			.queryProjectCsBySpId(spId);
		
		if (councilSummaryProject == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "批复未找到 ");
		}
		
		// 会议纪要
		//		FCouncilSummaryInfo summary = councilSummaryServiceClient
		//			.queryCouncilSummaryById(councilSummaryProject.getSummaryId());
		ProjectInfo project = projectServiceClient.queryByCode(
			councilSummaryProject.getProjectCode(), false);
		if (ProjectUtil.isInnovativeProduct(project.getBusiType())) {
			// 会议纪要
			FCouncilSummaryInfo summary = councilSummaryServiceClient
				.queryCouncilSummaryById(councilSummaryProject.getSummaryId());
			councilSummaryProject.setOverview(summary.getOverview());
		}
		model.addAttribute("approval", councilSummaryProject);
		model.addAttribute("project", project);
		
		return vm_path + "project/approval.vm";
	}
	
	@RequestMapping("pause.htm")
	@ResponseBody
	public JSONObject pause(String projectCode, HttpServletRequest request, Model model) {
		JSONObject result = new JSONObject();
		try {
			if (DataPermissionUtil.isBusiManager(projectCode)) {
				FcsBaseResult pauseResult = projectServiceClient.pauseProject(projectCode);
				return toJSONResult(result, pauseResult);
			} else {
				result.put("success", false);
				result.put("message", "业务经理才能暂缓项目");
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "项目暂缓出错");
			logger.error("项目暂缓出错：{}", e);
		}
		return result;
	}
	
	@RequestMapping("restart.htm")
	@ResponseBody
	public JSONObject restart(String projectCode, HttpServletRequest request, Model model) {
		JSONObject result = new JSONObject();
		try {
			if (DataPermissionUtil.isBusiManager(projectCode)) {
				FcsBaseResult pauseResult = projectServiceClient.restartProject(projectCode);
				return toJSONResult(result, pauseResult);
			} else {
				result.put("success", false);
				result.put("message", "业务经理才能重启项目");
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "项目重启出错");
			logger.error("项目重启出错：{}", e);
		}
		return result;
	}
	
	@RequestMapping("transfer.htm")
	@ResponseBody
	public JSONObject transfer(TransferProjectOrder order, HttpServletRequest request, Model model) {
		JSONObject result = new JSONObject();
		try {
			setSessionLocalInfo2Order(order);
			FcsBaseResult pauseResult = projectServiceClient.transferProject(order);
			return toJSONResult(result, pauseResult);
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "项目移交出错");
			logger.error("项目移交出错：{}", e);
		}
		return result;
	}
	
	/**
	 * 项目详情
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("viewProjectAllMessage.htm")
	public String viewProjectAllMessage(String projectCode, HttpServletRequest request,
										HttpServletResponse response, Model model) {
		if (StringUtil.isBlank(projectCode)) {
			return "error.vm";
		}
		// 项目
		ProjectInfo projectInfo = projectServiceClient.queryByCode(projectCode, true);
		model.addAttribute("projectInfo", projectInfo);
		
		//被重新授信了
		if (projectInfo.getIsRedo() == BooleanEnum.IS) {
			ProjectQueryOrder redoProjectQueryOrder = new ProjectQueryOrder();
			redoProjectQueryOrder.setRedoFrom(projectCode);
			redoProjectQueryOrder.setPageSize(999);
			QueryBaseBatchResult<ProjectInfo> redoProjects = projectServiceClient
				.queryProject(redoProjectQueryOrder);
			if (redoProjects != null && ListUtil.isNotEmpty(redoProjects.getPageList())) {
				model.addAttribute("redoProjects", redoProjects.getPageList());
			}
		}
		
		//是否有项目台帐
		boolean hasProjectAccount = false;
		
		//还款明细
		ProjectRepayDetailQueryOrder repayQueryOrder = new ProjectRepayDetailQueryOrder();
		repayQueryOrder.setProjectCode(projectCode);
		repayQueryOrder.setSortCol("repay_time");
		QueryBaseBatchResult<ProjectRepayDetailInfo> repayBatchResult = projectReportServiceClient
			.projectRepayDetail(repayQueryOrder);
		List<JSONObject> repayList = Lists.newArrayList();
		if (repayBatchResult != null && repayBatchResult.getTotalCount() > 0) {
			for (ProjectRepayDetailInfo repay : repayBatchResult.getPageList()) {
				JSONObject json = new JSONObject();
				json.put("repayAmount", repay.getRepayAmount().toStandardString());
				json.put("repayTime", repay.getRepayTime());
				if (StringUtil.equals(repay.getRepayType(), "代偿")) {
					json.put("isComp", true);
				} else {
					json.put("isComp", false);
				}
				repayList.add(json);
				hasProjectAccount = true;
			}
		}
		
		if (repayList.size() > 0)
			model.addAttribute("repayList", repayList);
		
		//代偿
		ProjectCompResult compResult = projectRelatedDataService.projectCompData(projectCode);
		model.addAttribute("compResult", compResult);
		
		//放款明细
		List<FLoanUseApplyReceiptInfo> loanResult = loanUseApplyServiceClient
			.queryReceipt(projectCode);
		if (ListUtil.isNotEmpty(loanResult)) {
			List<FLoanUseApplyReceiptInfo> loanList = Lists.newArrayList();
			for (FLoanUseApplyReceiptInfo receipt : loanResult) {
				if (receipt.getApplyType() == LoanUseApplyTypeEnum.LOAN
					|| receipt.getApplyType() == LoanUseApplyTypeEnum.BOTH) {
					loanList.add(receipt);
				}
			}
			if (loanList.size() > 0) {
				model.addAttribute("loanList", loanList);
				hasProjectAccount = true;
			}
		}
		
		//收费明细
		List<FChargeNotificationInfo> chargeFormList = chargeNotificationServiceClient
			.queryChargeNotificationByProjectCode(projectCode);
		if (ListUtil.isNotEmpty(chargeFormList)) {
			List<FChargeNotificationFeeInfo> chargeList = Lists.newArrayList();
			for (FChargeNotificationInfo charge : chargeFormList) {
				if (StringUtil.equals("APPROVAL", charge.getStatus())) {
					if (ListUtil.isNotEmpty(charge.getFeeList())) {
						chargeList.addAll(charge.getFeeList());
					}
				}
			}
			//查询财务确认的时间
			ProjectChargePayQueryOrder queryOrder = new ProjectChargePayQueryOrder();
			queryOrder.setProjectCode(projectCode);
			queryOrder.setPageSize(999);
			queryOrder.setAffirmFormType("CHARGE_NOTIFICATION");
			QueryBaseBatchResult<ProjectChargePayInfo> chargeConfirmList = financeAffirmServiceClient
				.queryProjectChargePayDetail(queryOrder);
			if (chargeConfirmList.isSuccess() && chargeConfirmList.getTotalCount() > 0) {
				for (FChargeNotificationFeeInfo fee : chargeList) {
					//防止没有找到财务确认时间显示错误 
					fee.setRemark(null);
					for (ProjectChargePayInfo confirm : chargeConfirmList.getPageList()) {
						if (confirm.getDetailId() == fee.getId()) {
							fee.setChargeAmount(confirm.getPayAmount());
							//设置收费时间到备注字段上页面上直接取
							if (confirm.getPayTime() != null)
								fee.setRemark(DateUtil.dtSimpleFormat(confirm.getPayTime()));
							break;
						}
					}
					
				}
				Collections.sort(chargeList, new Comparator<FChargeNotificationFeeInfo>() {
					@Override
					public int compare(FChargeNotificationFeeInfo o1, FChargeNotificationFeeInfo o2) {
						Date date1 = DateUtil.parse(o1.getRemark());
						Date date2 = DateUtil.parse(o2.getRemark());
						if (date1 == null || date2 == null)
							return 0;
						return date1.compareTo(date2);
					}
				});
				hasProjectAccount = true;
				model.addAttribute("chargeList", chargeList);
			}
		}
		model.addAttribute("hasProjectAccount", hasProjectAccount);
		
		RiskLevelQueryOrder riskLevelQueryOrder = new RiskLevelQueryOrder();
		riskLevelQueryOrder.setProjectCode(projectCode);
		riskLevelQueryOrder.setPageSize(1L);
		riskLevelQueryOrder.setSortCol("f.finish_time");
		riskLevelQueryOrder.setSortOrder("DESC");
		riskLevelQueryOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
		// 项目风险
		QueryBaseBatchResult<RiskLevelInfo> riskInfos = riskLevelServiceClient
			.queryList(riskLevelQueryOrder);
		if (ListUtil.isNotEmpty(riskInfos.getPageList())) {
			RiskLevelInfo riskLevelInfo = riskInfos.getPageList().get(0);
			model.addAttribute("riskLevelInfo", riskLevelInfo);
		}
		ProjectRelatedUserInfo relatedUserInfo = new ProjectRelatedUserInfo();
		// 风险经理
		if (ProjectUtil.isLitigation(projectInfo.getBusiType())) {//诉讼保函，取法务经理
			relatedUserInfo = projectRelatedUserServiceClient.getLegalManager(projectCode);
			model.addAttribute("isLitigation", "true");
		} else {
			relatedUserInfo = projectRelatedUserServiceClient.getRiskManager(projectCode);
		}
		model.addAttribute("relatedUserInfo", relatedUserInfo);
		// 代偿 用于判定风险处置会会议纪要是否出现[查询已通过的]
		CouncilSummaryRiskHandleQueryOrder riskHandleQOrder = new CouncilSummaryRiskHandleQueryOrder();
		riskHandleQOrder.setProjectCode(projectCode);
		QueryBaseBatchResult<CouncilSummaryRiskHandleInfo> councilSummaryRiskHandleInfos = councilSummaryServiceClient
			.queryApprovalRiskHandleCs(riskHandleQOrder);
		if (councilSummaryRiskHandleInfos != null
			&& councilSummaryRiskHandleInfos.getTotalCount() > 0) {
			model.addAttribute("hasCouncilSummaryRiskHandleInfo", "YES");
		}
		
		// 资金渠道 项目渠道在下面尽职之后
		if (ProjectUtil.isBond(projectInfo.getBusiType())) {
			model.addAttribute("capitalChannelName", projectInfo.getBondDetailInfo() == null ? ""
				: projectInfo.getBondDetailInfo().getCapitalChannelName());
		} else if (ProjectUtil.isEntrusted(projectInfo.getBusiType())) {
			model.addAttribute("capitalChannelName",
				projectInfo.getEntrustedDetailInfo() == null ? "" : projectInfo
					.getEntrustedDetailInfo().getCapitalChannelName());
		} else if (ProjectUtil.isGuarantee(projectInfo.getBusiType())) {
			model.addAttribute("capitalChannelName",
				projectInfo.getGuaranteeDetailInfo() == null ? "" : projectInfo
					.getGuaranteeDetailInfo().getCapitalChannelName());
		}
		
		// 20170110 添加风险处置上会申报列表
		CouncilApplyRiskHandleQueryOrder riskHandleOrder = new CouncilApplyRiskHandleQueryOrder();
		riskHandleOrder.setProjectCode(projectCode);
		// 只判定通过的
		riskHandleOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
		QueryBaseBatchResult<CouncilApplyRiskHandleInfo> batchRiskHandleResult = councilApplyRiskHandleServiceClient
			.queryList(riskHandleOrder);
		if (batchRiskHandleResult != null
			&& ListUtil.isNotEmpty(batchRiskHandleResult.getPageList())) {
			model.addAttribute("hasRishHandle", "yes");
		}
		
		/**
		 * ---------------------项目风险等级:riskLevelInfo.reevaluationLevel.message
		 * ----------------------------评定时间 :riskLevelInfo.finishTime
		 * ------------项目编号：projectInfo.projectCode 业务品种：busiTypeName
		 * 客户名称：projectInfo.customerName 项目名称：projectInfo.projectName
		 * 授信期限：projectInfo.timeLimit|timeUnit 授信金额 projectInfo.amount
		 * 担保费率：projectInfo.guaranteeDetailInfo.interestRate | interestRateFloat
		 * ---------------------------------客户经理：projectInfo.busiManagerName
		 * 风险经理：--relatedUserInfo.userName ----------授信期间：startTime---》endTime
		 * 
		 * -----------------代偿本息：project.compPrincipalAmount 处置实现金额：---未找到
		 * -------代偿收入： --未找到 --------------------------分摊损失金额： --未找到
		 * -------------损失认定金额： --未找到 --
		 */
		
		// 客户
		//		FProjectCustomerBaseInfo projectCustomerBaseInfo = projectSetupServiceClient
		//			.queryCustomerBaseInfoByProjectCode(projectCode);
		//		model.addAttribute("projectCustomerBaseInfo", projectCustomerBaseInfo);
		String customerId = null;
		// 999999999999999990 以上表示虚拟的客户 不在客户系统中 
		if (projectInfo.getCustomerId() > 0 && projectInfo.getCustomerId() < 999999999999999990L) {
			if (CustomerTypeEnum.ENTERPRISE == projectInfo.getCustomerType()) {
				CompanyCustomerInfo customerInfo = companyCustomerClient.queryByUserId(
					projectInfo.getCustomerId(), null);
				model.addAttribute("customerInfo", customerInfo);
				if (null != customerInfo) {
					customerId = customerInfo.getCustomerId();
				}
			} else {
				PersonalCustomerInfo customerInfo = personalCustomerClient
					.queryByUserId(projectInfo.getCustomerId());
				model.addAttribute("customerInfo", customerInfo);
				if (null != customerInfo) {
					customerId = customerInfo.getCustomerId();
				}
			}
			// 查询客户评级
			EvaluetingListQueryOrder evaluetingListQueryOrder = new EvaluetingListQueryOrder();
			evaluetingListQueryOrder.setCustomerId(String.valueOf(customerId));
			evaluetingListQueryOrder.setStatus(FormStatusEnum.APPROVAL.code());
			QueryBaseBatchResult<EvaluetingListAuditInfo> evalueingBatchResult = evaluetingClient
				.list(evaluetingListQueryOrder);
			if (evalueingBatchResult != null
				&& ListUtil.isNotEmpty(evalueingBatchResult.getPageList())) {
				model.addAttribute("evaluetingTime", evalueingBatchResult.getPageList().get(0)
					.getRawAddTime());
				model.addAttribute("evaluetingId", evalueingBatchResult.getPageList().get(0)
					.getFormId());
			}
		}
		
		// 查询该客户所有项目信息用于组合
		Money releasedAmount = Money.zero();
		Money releasingAmount = Money.zero();
		if (projectInfo.getCustomerId() > 0) {
			
			ProjectQueryOrder projectQueryOrder = new ProjectQueryOrder();
			projectQueryOrder.setCustomerId(projectInfo.getCustomerId());
			QueryBaseBatchResult<ProjectInfo> projectInfos = projectServiceClient
				.queryProject(projectQueryOrder);
			List<ProjectInfo> codes = new ArrayList<ProjectInfo>();
			if (projectInfos != null && projectInfos.getPageList() != null) {
				for (ProjectInfo info : projectInfos.getPageList()) {
					releasedAmount.addTo(info.getReleasedAmount());
					//				releasingAmount.addTo(info.getReleasingAmount());
					releasingAmount.addTo(info.getBalance());
					// 将在保金额大于0的项目收集起来
					if (info.getBalance().greaterThan(Money.zero())) {
						codes.add(info);
					}
				}
			}
			model.addAttribute("codes", codes);
		}
		model.addAttribute("releasedAmount", releasedAmount);
		model.addAttribute("releasingAmount", releasingAmount);
		// 换为json
		JSONArray jsons = new JSONArray();
		JSONObject json1 = new JSONObject();
		json1.put("moneyName", "解保金额");
		json1.put("money", releasedAmount.getAmount());
		json1.put("moneyCent", releasedAmount.getCent());
		jsons.add(json1);
		JSONObject json2 = new JSONObject();
		json2.put("moneyName", "在保金额");
		json2.put("money", releasingAmount.getAmount());
		json2.put("moneyCent", releasingAmount.getCent());
		jsons.add(json2);
		model.addAttribute("moneyJson", jsons.toJSONString().replaceAll("\"", "'"));
		
		// 查询是否存在关联授信
		boolean hascredit = false;
		CreditRefrerenceApplyQueryOrder order = new CreditRefrerenceApplyQueryOrder();
		order.setProjectCode(projectCode);
		order.setSortCol("raw_add_time");
		order.setSortOrder("DESC");
		QueryBaseBatchResult<CreditRefrerenceApplyReusltInfo> creditResult = creditRefrerenceApplyServiceClient
			.query(order);
		if (creditResult != null && ListUtil.isNotEmpty(creditResult.getPageList())) {
			for (CreditRefrerenceApplyReusltInfo info : creditResult.getPageList()) {
				if (StringUtil.isNotEmpty(info.getCreditReport())) {
					hascredit = true;
					break;
				}
			}
		}
		if (hascredit) {
			model.addAttribute("hascredit", "yes");
		}
		
		// 资金  --- 未找到
		
		// 资产 
		// 抵押
		//		List<FCouncilSummaryProjectPledgeInfo> pledgeInfos = councilSummaryServiceClient
		//			.queryPledgeBySpId(projectInfo.getSpId());
		//		if (pledgeInfos == null) {
		//			pledgeInfos = new ArrayList<FCouncilSummaryProjectPledgeInfo>();
		//		}
		//		// 质押
		//		List<FCouncilSummaryProjectPledgeInfo> MortgageInfos = councilSummaryServiceClient
		//			.queryMortgageBySpId(projectInfo.getSpId());
		//		if (MortgageInfos == null) {
		//			MortgageInfos = new ArrayList<FCouncilSummaryProjectPledgeInfo>();
		//		}
		
		//所有授信条件
		
		FCouncilSummaryProjectCreditConditionInfo conditionInfo = councilSummaryServiceClient
			.queryCreditConditionBySpId(projectInfo.getSpId(), false);
		List<FCouncilSummaryProjectPledgeAssetInfo> pledgeAssetInfos = new ArrayList<FCouncilSummaryProjectPledgeAssetInfo>();
		if (conditionInfo.getPledges() != null) {
			pledgeAssetInfos.addAll(conditionInfo.getPledges());
		}
		if (conditionInfo.getMortgages() != null) {
			pledgeAssetInfos.addAll(conditionInfo.getMortgages());
		}
		// 20161201  批复没有数据的时候判断尽职是否审核通过，若尽职审核通过，展示尽职数据
		if (ListUtil.isEmpty(pledgeAssetInfos)) {
			InvestigationQueryOrder queryOrder = new InvestigationQueryOrder();
			queryOrder.setProjectCode(projectCode);
			queryOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
			queryOrder.setPageSize(1L);
			queryOrder.setPageNumber(1L);
			QueryBaseBatchResult<InvestigationInfo> batchResult = investigationServiceClient
				.queryInvestigation(queryOrder);
			if (batchResult != null && ListUtil.isNotEmpty(batchResult.getPageList())) {
				FInvestigationCreditSchemeInfo base0 = investigationServiceClient
					.findInvestigationCreditSchemeByFormId(batchResult.getPageList().get(0)
						.getFormId());
				if (base0 != null) {
					List<FInvestigationCreditSchemePledgeAssetInfo> infos = base0.getMortgages();
					if (infos == null) {
						infos = base0.getPledges();
					} else {
						if (base0.getPledges() != null) {
							infos.addAll(base0.getPledges());
						}
					}
					
					model.addAttribute("pledgeEstimateAmount",
						base0.getPledgeAssessPrice().add(base0.getMortgageAssessPrice()));
					model.addAttribute("pledgeAllAmount",
						base0.getPledgePrice().add(base0.getMortgagePrice()));
					if (ListUtil.isNotEmpty(infos)) {
						model.addAttribute("pledgeInfos", infos);
					}
				}
			}
		} else {
			
			model.addAttribute("pledgeEstimateAmount",
				conditionInfo.getPledgeAssessPrice().add(conditionInfo.getMortgageAssessPrice()));
			model.addAttribute("pledgeAllAmount",
				conditionInfo.getPledgePrice().add(conditionInfo.getMortgagePrice()));
			if (ListUtil.isNotEmpty(pledgeAssetInfos)) {
				model.addAttribute("pledgeInfos", pledgeAssetInfos);
			}
		}
		
		//		pledgeInfos.addAll(MortgageInfos);
		/**
		 * --------------------抵押资产评估总额：xxxxxx元,pledgeEstimateAmount
		 * 抵押总额：xxxxxx元 【抵押评估金额乘以抵押率】,pledgeAllAmount
		 */
		//		Money pledgeEstimateAmount = Money.zero();
		//		Money pledgeAllAmount = Money.zero();
		//		for (FCouncilSummaryProjectPledgeInfo info : MortgageInfos) {
		//			pledgeEstimateAmount.addTo(info.getAmount());
		//			pledgeAllAmount.addTo(info.getAmount().multiply(info.getRatio()).divide(100));
		//		}
		//		model.addAttribute("pledgeEstimateAmount", pledgeEstimateAmount);
		//		model.addAttribute("pledgeAllAmount", pledgeAllAmount);
		//		model.addAttribute("pledgeInfos", pledgeInfos);
		
		// 立项formid
		SetupFormQueryOrder setupFormQueryOrder = new SetupFormQueryOrder();
		setupFormQueryOrder.setProjectCode(projectCode);
		QueryBaseBatchResult<SetupFormProjectInfo> setupForms = projectSetupServiceClient
			.querySetupForm(setupFormQueryOrder);
		if (setupForms != null && setupForms.getPageList() != null
			&& setupForms.getPageList().size() > 0) {
			Long formId = setupForms.getPageList().get(0).getFormId();
			model.addAttribute("declareFormId", formId);
			
		}
		
		// 尽职formid
		Long riskreviewFormId = investigationServiceClient
			.queryInvestigationFormIdByProjectCode(projectCode);
		model.addAttribute("riskreviewFormId", riskreviewFormId);
		
		// 获取尽职调查报告的项目渠道
		//		FInvestigationCreditSchemeInfo schemeInfo = investigationServiceClient
		//			.findInvestigationCreditSchemeByFormId(riskreviewFormId);
		//		model.addAttribute("schemeInfo", schemeInfo);
		// 2016-11-21 查询项目资金渠道和项目渠道
		List<ProjectChannelRelationInfo> capitalChannels = projectChannelRelationServiceClient
			.queryCapitalChannel(projectCode);
		model.addAttribute("capitalChannels", capitalChannels);
		ProjectChannelRelationInfo projectChannel = projectChannelRelationServiceClient
			.queryProjectChannel(projectCode);
		model.addAttribute("projectChannel", projectChannel);
		
		// 风险 同上
		// 合同 
		ProjectContractQueryOrder projectContractQueryOrder = new ProjectContractQueryOrder();
		projectContractQueryOrder.setProjectCode(projectCode);
		QueryBaseBatchResult<ProjectContractResultInfo> contractInfos = projectContractServiceClient
			.query(projectContractQueryOrder);
		if (contractInfos != null && contractInfos.getPageList() != null
			&& contractInfos.getPageList().size() > 0) {
			model.addAttribute("contractFormId", contractInfos.getPageList().get(0).getFormId());
		}
		// 项目授信
		//		FCreditConditionConfirmQueryOrder queryOrder = new FCreditConditionConfirmQueryOrder();
		//		setSessionLocalInfo2Order(queryOrder);
		//		queryOrder.setProjectCode(projectCode);
		//		QueryBaseBatchResult<FCreditConditionConfirmInfo> batchResult = projectCreditConditionServiceClient
		//			.query(queryOrder);
		//		if (batchResult != null && batchResult.getPageList() != null
		//			&& batchResult.getPageList().size() > 0) {
		//			model.addAttribute("hasCreditCondition", "yes");
		//		}
		//          查所有
		//		projectCreditConditionServiceClient.findProjectCreditConditionByProjectCode(projectCode);
		// 判定有落实的才展示
		//		List<ProjectCreditConditionInfo> infos = projectCreditConditionServiceClient
		//			.findProjectCreditConditionByProjectCodeAndStatus(projectCode,
		//				CreditCheckStatusEnum.CHECK_PASS.code());
		//		if (infos != null && infos.size() > 0) {
		//			model.addAttribute("hasCreditCondition", "yes");
		//		}
		ProjectCreditConditionQueryOrder creditConditionOrder = new ProjectCreditConditionQueryOrder();
		creditConditionOrder.setProjectCode(projectCode);
		creditConditionOrder.setIsConfirm(BooleanEnum.IS.code());
		QueryBaseBatchResult<ProjectCreditConditionInfo> creditConditionBatchResult = projectCreditConditionServiceClient
			.queryCreditCondition(creditConditionOrder);
		if (creditConditionBatchResult != null && creditConditionBatchResult.getPageList() != null
			&& creditConditionBatchResult.getPageList().size() > 0) {
			model.addAttribute("hasCreditCondition", "yes");
		}
		// 报后检查报告
		if (ProjectPhasesEnum.AFTERWARDS_PHASES == projectInfo.getPhases()
			|| ProjectPhasesEnum.RECOVERY_PHASES == projectInfo.getPhases()
			|| ProjectPhasesEnum.FINISH_PHASES == projectInfo.getPhases()) {
			AfterwordsCheckQueryOrder afterwardsCheckOrder = new AfterwordsCheckQueryOrder();
			setSessionLocalInfo2Order(afterwardsCheckOrder);
			afterwardsCheckOrder.setProjectCode(projectInfo.getProjectCode());
			// 设定为审核通过的状态
			afterwardsCheckOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
			QueryBaseBatchResult<AfterwardsCheckInfo> batchResult = afterwardsCheckServiceClient
				.list(afterwardsCheckOrder);
			if (batchResult != null && batchResult.getPageList() != null
				&& batchResult.getPageList().size() > 0) {
				model.addAttribute("hasAfterwardsCheck", "yes");
			}
		}
		// 判断是否复议
		if (projectInfo.getLastRecouncilTime() != null) {
			model.addAttribute("hasRecouncils", "yes");
			
			//查询是否有复议申请单
			ReCouncilApplyQueryOrder reCouncilApplyQueryOrder = new ReCouncilApplyQueryOrder();
			reCouncilApplyQueryOrder.setProjectCode(projectCode);
			//reCouncilApplyQueryOrder.setStatus(FormStatusEnum.APPROVAL.code());
			QueryBaseBatchResult<ReCouncilApplyFormInfo> recouncils = recouncilApplyServiceClient
				.queryForm(reCouncilApplyQueryOrder);
			if (recouncils != null && ListUtil.isNotEmpty(recouncils.getPageList())) {
				model.addAttribute("hasRecouncilApplys", "yes");
			}
		}
		
		//////////////20161021 资金划付申请单  /projectMg/fCapitalAppropriationApply/list.htm
		FCapitalAppropriationApplyQueryOrder queryOrder = new FCapitalAppropriationApplyQueryOrder();
		queryOrder.setProjectCode(projectCode);
		QueryBaseBatchResult<FCapitalAppropriationApplyInfo> batchResult = fCapitalAppropriationApplyServiceClient
			.query(queryOrder);
		if (batchResult != null && batchResult.isSuccess()
			&& ListUtil.isNotEmpty(batchResult.getPageList())) {
			model.addAttribute("hasCapital", "yes");
		}
		
		// 20161021 放用款
		//projectInfo.getLoanedAmount().cent()>0
		// 20161021 收费通知单
		//		 projectInfo.getChargedAmount()
		// 20161021 资产复评
		FInvestigationAssetReviewQueryOrder assetReviewQueryOrder = new FInvestigationAssetReviewQueryOrder();
		assetReviewQueryOrder.setProjectCode(projectCode);
		QueryBaseBatchResult<FInvestigationAssetReviewInfo> assetReviewBatchResult = assetReviewServiceClient
			.queryList(assetReviewQueryOrder);
		if (assetReviewBatchResult != null && assetReviewBatchResult.isSuccess()
			&& ListUtil.isNotEmpty(assetReviewBatchResult.getPageList())) {
			model.addAttribute("hasAssetReview", "yes");
		}
		
		// 项目小结
		
		//查询项目小结
		ProjectExtendQueryOrder qOrder = new ProjectExtendQueryOrder();
		qOrder.setProjectCode(projectCode);
		qOrder.setProperty(ProjectExtendPropertyEnum.SUMMARY);
		List<ProjectExtendInfo> qList = projectExtendServiceClient
			.findApprovalProjectExtendInfo(qOrder);
		if (ListUtil.isNotEmpty(qList)) {
			model.addAttribute("hasQList", "yes");
			model.addAttribute("qListId", qList.get(0).getFormId());
		}
		
		// 判断是否客户经理，并查询项目备注
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			if (sessionLocal.getUserId() == projectInfo.getBusiManagerId()) {
				model.addAttribute("hasMemoQList", "yes");
				qOrder.setProperty(ProjectExtendPropertyEnum.PROJECT_MEMO);
				QueryBaseBatchResult<ProjectExtendInfo> qList2 = projectExtendServiceClient
					.query(qOrder);
				if (qList2 != null && ListUtil.isNotEmpty(qList2.getPageList())) {
					model.addAttribute("memoQInfo", qList2.getPageList().get(0));
				}
			}
		}
		
		//查询项目所有流程
		FormRelatedUserQueryOrder flowOrder = new FormRelatedUserQueryOrder();
		List<String> exeStatusList = Lists.newArrayList();
		exeStatusList.add(ExeStatusEnum.IN_PROGRESS.code());
		//exeStatusList.add(ExeStatusEnum.END_AGREE.code());
		//exeStatusList.add(ExeStatusEnum.END_DISAGREE.code());
		flowOrder.setExeStatusList(exeStatusList);
		flowOrder.setProjectCode(projectCode);
		flowOrder.setUserType(FormRelatedUserTypeEnum.FLOW_SUBMIT_MAN);
		QueryBaseBatchResult<FormRelatedUserInfo> relatedUserInfos = formRelatedUserServiceClient
			.queryPage(flowOrder);
		List<FormInfo> projectFlows = Lists.newArrayList();
		if (relatedUserInfos != null && ListUtil.isNotEmpty(relatedUserInfos.getPageList())) {
			for (FormRelatedUserInfo flow : relatedUserInfos.getPageList()) {
				if (flow.getFormId() > 0) {
					FormInfo form = formServiceClient.findByFormId(flow.getFormId());
					if (form != null) {
						if (form.getActInstId() > 0) { //查看流程图需要actInstId
							projectFlows.add(form);
						}
					}
				}
			}
		}
		model.addAttribute("projectFlows", projectFlows);
		
		//查看签报
		FormChangeApplyQueryOrder formChangeApplyQueryOrder = new FormChangeApplyQueryOrder();
		formChangeApplyQueryOrder.setProjectCode(projectCode);
		formChangeApplyQueryOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
		QueryBaseBatchResult<FormChangeApplySearchInfo> formChangeApplyQueryResult = formChangeApplyServiceClient
			.searchForm(formChangeApplyQueryOrder);
		if (formChangeApplyQueryResult != null && formChangeApplyQueryResult.isSuccess()
			&& formChangeApplyQueryResult.getTotalCount() > 0) {
			model.addAttribute("hasChangeApply", "YES");
		}
		
		//分保信息
		showFenbaoInfo(projectCode, model);
		
		//附件
		//if (PermissionUtil.checkPermission("/projectMg/projectAttach.htm")) {
		//	model.addAttribute("hasAttachPermission", true);
		CommonAttachmentQueryOrder atOrder = new CommonAttachmentQueryOrder();
		atOrder.setProjectCode(projectCode);
		projectAttach(atOrder, request, model);
		//}
		
		return vm_path + "project/projectDetails.vm";
	}
	
	/**
	 * 项目附件
	 * @param order
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("projectAttach.htm")
	public String projectAttach(CommonAttachmentQueryOrder order, HttpServletRequest request,
								Model model) {
		//项目附件
		if (order == null)
			order = new CommonAttachmentQueryOrder();
		if (StringUtil.isNotBlank(order.getProjectCode())) {
			order.setCheckStatus(CheckStatusEnum.CHECK_PASS);
			order.setSortCol("attachment_id");
			order.setSortOrder("desc");
			QueryBaseBatchResult<CommonAttachmentInfo> attachement = commonAttachmentServiceClient
				.queryPage(order);
			model.addAttribute("pageAttach", PageUtil.getCovertPage(attachement));
			model.addAttribute("hasAttach", attachement != null && attachement.getTotalCount() > 0);
			model.addAttribute("projectCode", order.getProjectCode());
		}
		return vm_path + "project/projectAttachList.vm";
	}
	
	@RequestMapping("summary/form.htm")
	public String summaryForm(String projectCode, Model model) {
		
		ProjectInfo project = projectServiceClient.queryByCode(projectCode, false);
		if (project == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "项目不存在");
		}
		
		model.addAttribute("project", project);
		
		ProjectExtendQueryOrder order = new ProjectExtendQueryOrder();
		order.setProjectCode(projectCode);
		QueryBaseBatchResult<ProjectExtendInfo> qResult = projectExtendServiceClient.query(order);
		if (qResult != null && qResult.isSuccess()) {
			ProjectExtendInfo summary = null;
			if (qResult.getTotalCount() > 0) {
				summary = qResult.getPageList().get(0);
				model.addAttribute("projectSummary", summary);
				model.addAttribute("form", formServiceClient.findByFormId(summary.getFormId()));
				return vm_path + "project/viewAuditProjectSummary.vm";
			} else {
				summary = new ProjectExtendInfo();
				summary.setProjectCode(projectCode);
				summary.setProperty(ProjectExtendPropertyEnum.SUMMARY);
				model.addAttribute("projectSummary", summary);
			}
		} else {
			throw ExceptionFactory.newFcsException(FcsResultEnum.NO_ACCESS, "查询项目小结出错");
		}
		return vm_path + "project/projectSummary.vm";
	}
	
	@RequestMapping("summary/edit.htm")
	public String summaryEdit(long formId, Model model) {
		viewSummary(formId, model);
		return vm_path + "project/projectSummary.vm";
	}
	
	@RequestMapping("summary/audit.htm")
	public String summaryAudit(long formId, HttpServletRequest request, Model model) {
		FormInfo form = viewSummary(formId, model);
		initWorkflow(model, form, request.getParameter("taskId"));
		return vm_path + "project/viewAuditProjectSummary.vm";
	}
	
	@RequestMapping("summary/view.htm")
	public String summaryView(long formId, HttpServletRequest request, Model model) {
		viewSummary(formId, model);
		return vm_path + "project/viewAuditProjectSummary.vm";
	}
	
	@RequestMapping("summary/print.htm")
	public String summaryPrint(long formId, HttpServletRequest request, Model model) {
		viewSummary(formId, model);
		return vm_path + "project/printProjectSummary.vm";
	}
	
	private FormInfo viewSummary(long formId, Model model) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		
		model.addAttribute("form", form);
		
		ProjectExtendQueryOrder order = new ProjectExtendQueryOrder();
		order.setFormId(formId);
		QueryBaseBatchResult<ProjectExtendInfo> qResult = projectExtendServiceClient.query(order);
		if (qResult != null && qResult.isSuccess()) {
			ProjectExtendInfo exInfo = qResult.getPageList().get(0);
			model.addAttribute("projectSummary", exInfo);
			model.addAttribute("project",
				projectServiceClient.queryByCode(exInfo.getProjectCode(), false));
		}
		return form;
	}
	
	@RequestMapping("summary/save.htm")
	@ResponseBody
	public JSONObject summarySave(ProjectExtendFormOrder order, Model model) {
		JSONObject json = new JSONObject();
		try {
			order.setCheckIndex(0);
			order.setCheckStatus(1);
			order.setFormCode(FormCodeEnum.PROJECT_SUMMARY);
			order.setProperty(ProjectExtendPropertyEnum.SUMMARY);
			order.setRelatedProjectCode(order.getProjectCode());
			setSessionLocalInfo2Order(order);
			FormBaseResult result = projectExtendServiceClient.saveExtendInfoWithForm(order);
			toJSONResult(json, result, "保存项目小结成功", null);
		} catch (Exception e) {
			logger.error("保存项目小结出错：{}", e);
		}
		return json;
	}
	
	/**
	 * 保存项目备注
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("saveExtendMessage.htm")
	@ResponseBody
	public JSONObject saveExtendMessage(ProjectExtendOrder order, HttpServletRequest request,
										Model model) {
		
		String tipPrefix = " 保存项目备注";
		JSONObject result = new JSONObject();
		
		try {
			order.setProperty(ProjectExtendPropertyEnum.PROJECT_MEMO);
			order.setUpdateOld(true);
			FcsBaseResult saveResult = projectExtendServiceClient.saveExtendInfo(order);
			return toJSONResult(saveResult, tipPrefix);
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
	}
	
	/**
	 * excel导入
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("excelParse.json")
	@ResponseBody
	public JSONObject excelParse(HttpServletRequest request, HttpServletResponse response,
									Model model) {
		String tipPrefix = "excel导入";
		JSONObject json = new JSONObject();
		try {
			ExcelData excelData = ExcelUtil.uploadExcel(request);
			// 抓取数据生成order并存库
			int columns = excelData.getColumns(); //列数
			int rows = excelData.getRows(); //行数
			
			String[][] datas = excelData.getDatas(); //数据
			List<OldProjectExcelInfo> oldInfos = new ArrayList<OldProjectExcelInfo>(); // order
			int outNum = 99999;
			int backNum = 99999;
			//循环得到每一行的数据
			for (int i = 0; i < rows; i++) {
				// 第一行代表 表头，忽略
				if (i == 0) {
					continue;
				}
				if (i == 1) {
					// 创建一个关系集合 从第十列开始是循环子项的list，可能是借款可能是还款
					for (int j = 0; j < columns; j++) {
						String str = datas[i][j];
						if (str.contains("放款时间")) {
							if (outNum < 99999) {
								continue;
							} else {
								outNum = j;
							}
						} else if (str.contains("还款时间")) {
							if (backNum < 99999) {
								continue;
							} else {
								backNum = j;
							}
						}
					}
					continue;
				}
				// 每一行
				String[] rowData = datas[i];
				OldProjectExcelInfo oldInfo = new OldProjectExcelInfo();
				// 循环列，生成info
				for (int j = 0; j < columns; j++) {
					String str = rowData[j];
					
					if (j == 0) {
						// 第0行为组织机构代码
						oldInfo.setOrgCode(str);
					} else if (j == 1) {
						// 第1行为 客户名称
						oldInfo.setCustomerName(str);
					} else if (j == 2) {
						// 第2行为 省（自治区、直辖市）
						oldInfo.setProvinceName(str);
					} else if (j == 3) {
						// 第3行为 市（区、地、州、盟）
						oldInfo.setCountyName(str);
					} else if (j == 4) {
						// 第4行为 县（区、市、旗 ）
						oldInfo.setCityName(str);
					} else if (j == 5) {
						// 第5行为 行业类别 代码（大类）
						oldInfo.setTypeCode(str);
					} else if (j == 6) {
						// 第6行为 行业类别 代码（小类）
						oldInfo.setChildTypeCode(str);
					} else if (j == 7) {
						// 第7行为 企业类型 代码
						oldInfo.setEnterpriseType(str);
					} else if (j == 8) {
						// 第8行为 企业类型 是否融资平台
						oldInfo.setIsLocalGovPlatform(str);
					} else if (j == 9) {
						// 第9行为 企业规模 代码
						oldInfo.setScale(str);
					} else if (j == 10) {
						// 第10行为 客户首次入库时间 （年\月\日）
						oldInfo.setFirstInsertTime(str);
					} else if (j == 11) {
						// 第11行为 是否外向型经济客户
						oldInfo.setIsExportOrientedEconomy(str);
					} else if (j == 12) {
						// 第12行为 项目名称
						oldInfo.setProjectName(str);
					} else if (j == 13) {
						// 第13行为 立项时间
						oldInfo.setProjectAddTime(str);
					} else if (j == 14) {
						// 第14行为 归属部门 代码
						oldInfo.setDeptName(str);
					} else if (j == 15) {
						// 第15行为 渠道 项目渠道代码
						oldInfo.setProjectChannelName(str);
					} else if (j == 16) {
						// 第16行为 渠道 资金渠道代码 
						oldInfo.setCapitalChannelName(str);
					} else if (j == 17) {
						// 第17行为 产品类型 代码
						oldInfo.setBusiTypeName(str);
					} else if (j == 18) {
						// 第18行为 产品类型 代码
						oldInfo.setBusiSubTypeName(str);
					} else if (j == 19) {
						// 第19行为 授信情况 授信合同金额（万元，两位小数）
						oldInfo.setAmount(str);
					} else if (j == 20) {
						// 第20行为 授信情况 授信开始时间（年\月\日）
						oldInfo.setStartTime(str);
					} else if (j == 21) {
						// 第21行为 授信情况 授信结束时间（年\月\日）
						oldInfo.setEndTime(str);
					} else if (j == 22) {
						// 第22行为 授信情况 授信期限（以月为单位）
						oldInfo.setTimeLimit(str);
					} else if (j == 23) {
						// 第23行为 项目费率（百分数，两位小数）
						if (StringUtil.isNotBlank(str))
							str = str.replace("%", "");
						oldInfo.setRate(str);
					} else if (j == 24) {
						// 第24行为 项目责任人员 业务经理
						oldInfo.setBusiManagerName(str);
					} else if (j == 25) {
						// 第25行为 项目责任人员 风险经理
						oldInfo.setRiskManagerName(str);
					} else if (j == 26) {
						// 第26行为 项目责任人员 法务经理
						oldInfo.setLegalManagerName(str);
					} else if (j >= outNum && j < backNum) {
						// 判断是第一行还是第二行
						if ((j - outNum) % 2 == 0) {
							// 余数为0 代表 第一列 放款时间
							try {
								String str2 = rowData[j + 1];
								//Date date = DateUtil.string2Date(str);
								OldProjectExcelDetailInfo outInfo = new OldProjectExcelDetailInfo();
								outInfo.setTime(str);
								outInfo.setAmount(str2);
								// 剔除两项都为空的数据
								if (StringUtil.isNotBlank(str) || StringUtil.isNotBlank(str2)) {
									oldInfo.getOutInfo().add(outInfo);
								}
							} catch (Exception e) {
							}
						} else {
							// 余数不为0 代表第二列 放款金额
						}
					} else if (j >= backNum) {
						// 判断是第一行还是第二行
						if ((j - outNum) % 2 == 0) {
							// 余数为0 代表 第一列 放款时间
							try {
								String str2 = rowData[j + 1];
								//Date date = DateUtil.string2Date(str);
								OldProjectExcelDetailInfo outInfo = new OldProjectExcelDetailInfo();
								outInfo.setTime(str);
								outInfo.setAmount(str2);
								// 剔除两项都为空的数据
								if (StringUtil.isNotBlank(str) || StringUtil.isNotBlank(str2)) {
									oldInfo.getBackInfo().add(outInfo);
								}
							} catch (Exception e) {
							}
						} else {
							// 余数不为0 代表第二列 放款金额
						}
					}
					
				}
				if (StringUtil.isBlank(oldInfo.getCustomerName()))
					continue;
				// 添加到order集合
				setSessionLocalInfo2Order(oldInfo);
				oldInfos.add(oldInfo);
			}
			if (!ListUtil.isEmpty(oldInfos)) {
				json.put("success", false);
				json.put("message", "数据为空");
			}
			ProjectImportBatchResult importResult = null;
			if (StringUtil.equals(request.getParameter("update"), "yes")) {
				importResult = projectImportServiceClient.reImportBatch(oldInfos);
			} else {
				importResult = projectImportServiceClient.importBatch(oldInfos);
			}
			
			if (importResult == null || !importResult.isSuccess()) {
				return toJSONResult(importResult, tipPrefix);
			} else {
				json.put("success", true);
				List<ProjectImportResult> detailResults = importResult.getBatchResult();
				JSONArray message = new JSONArray();
				for (ProjectImportResult iResult : detailResults) {
					JSONObject r = new JSONObject();
					r.put("projectCode",
						iResult.getProjectCode() == null ? "" : iResult.getProjectCode());
					r.put("projectName", iResult.getProjectName());
					r.put("customerName", iResult.getCustomerName());
					r.put("importResult", iResult.isSuccess() ? "导入成功" : iResult.getMessage());
					message.add(r);
				}
				json.put("message", message);
				return json;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			json.put("success", false);
			json.put("message", "上传异常" + e.getMessage());
		}
		
		return json;
	}
	
	/**
	 * 授信授信，后台执行不成功时前端重新发起(一般不从这里调用)
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("redoProject.htm")
	@ResponseBody
	public JSONObject redoProject(ProjectRedoOrder order, Model model) {
		FcsBaseResult result = projectServiceClient.redoProject(order);
		return toJSONResult(result, "重新授信");
	}
}
