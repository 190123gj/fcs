package com.born.fcs.pm.biz.service.investigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.am.ws.enums.AssetStatusEnum;
import com.born.fcs.am.ws.order.pledgeasset.AssetRelationProjectBindOrder;
import com.born.fcs.am.ws.order.pledgeasset.AssetStatusOrder;
import com.born.fcs.am.ws.service.pledgeasset.PledgeAssetService;
import com.born.fcs.crm.ws.service.CompanyCustomerService;
import com.born.fcs.crm.ws.service.enums.ChangeTypeEnum;
import com.born.fcs.crm.ws.service.info.CompanyQualificationInfo;
import com.born.fcs.crm.ws.service.order.CustomerDetailOrder;
import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.dal.dataobject.FInvestigationCreditSchemeDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationCreditSchemePledgeAssetDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationFinancialReviewDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationFinancialReviewKpiDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationLitigationDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationUnderwritingDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.util.CommonUtil;
import com.born.fcs.pm.util.DataFinancialHelper;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.enums.AssetReviewStatusEnum;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.CouncilApplyStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectExtendPropertyEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.enums.managerbchange.ChangeManagerbPhaseEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCreditSchemeInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCreditSchemePledgeAssetInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMainlyReviewAssetAndLiabilityInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMainlyReviewBankInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMainlyReviewCertificateInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMainlyReviewCreditStatusInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMainlyReviewExternalGuaranteeInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMainlyReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationOpabilityReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationOpabilityReviewUpdownStreamInfo;
import com.born.fcs.pm.ws.info.finvestigation.checking.InvestigationCheckingInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.ChangeProjectStatusOrder;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.ProjectExtendOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.order.council.CouncilApplyOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationAssetReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.declare.UpdateCouncilTypeOrder;
import com.born.fcs.pm.ws.order.riskreview.FRiskReviewOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.common.ProjectResult;
import com.born.fcs.pm.ws.service.common.ProjectExtendService;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.council.CouncilApplyService;
import com.born.fcs.pm.ws.service.investigation.AssetReviewService;
import com.born.fcs.pm.ws.service.investigation.InvestigationService;
import com.born.fcs.pm.ws.service.riskreview.RiskReviewReportService;
import com.born.fcs.pm.ws.service.setup.ProjectSetupService;
import com.bornsoft.facade.api.risk.RiskSystemFacade;
import com.bornsoft.pub.enums.BudgetRelationEnum;
import com.bornsoft.pub.order.risk.SynsCustomInfoOrder;
import com.bornsoft.pub.order.risk.SynsCustomInfoOrder.BusinessRelationShipInfo;
import com.bornsoft.pub.order.risk.SynsCustomInfoOrder.CreditStatusInfo;
import com.bornsoft.pub.order.risk.SynsCustomInfoOrder.DebtInfo;
import com.bornsoft.pub.order.risk.SynsCustomInfoOrder.GuaranteeInfo;
import com.bornsoft.utils.base.BornSynResultBase;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 尽职调查报告审核流程处理
 * 
 * @author lirz
 * 
 * 2016-4-23 下午1:46:05
 */
@Service("investigationProcessService")
public class InvestigationProcessServiceImpl extends BaseProcessService {
	
	private static final String FLAG = "尽职调查报告流程处理：";
	
	@Autowired
	protected ProjectSetupService projectSetupService;
	@Autowired
	protected RiskReviewReportService riskReviewReportService;
	@Autowired
	protected CouncilApplyService councilApplyService;
	@Autowired
	protected InvestigationService investigationService;
	@Autowired
	protected SiteMessageService siteMessageService;
	@Autowired
	ProjectRelatedUserService projectRelatedUserService;
	@Autowired
	CompanyCustomerService companyCustomerServiceClient;
	@Autowired
	PledgeAssetService pledgeAssetServiceClient;
	@Autowired
	protected AssetReviewService assetReviewService;
	@Autowired
	protected RiskSystemFacade riskSystemFacadeClient;
	@Autowired
	ProjectExtendService projectExtendService;
	
	@Override
	public List<SimpleUserInfo> resultNotifyUserList(FormInfo formInfo) {
		List<SimpleUserInfo> notifyUserList = super.resultNotifyUserList(formInfo);
		
		try {
			if (notifyUserList == null)
				notifyUserList = Lists.newArrayList();
			
			//客户经理
			ProjectRelatedUserInfo userInfo = projectRelatedUserService.getBusiManager(formInfo
				.getRelatedProjectCode());
			if (userInfo != null) {
				notifyUserList.add(userInfo.toSimpleUserInfo());
			}
			
			//风险经理
			userInfo = projectRelatedUserService.getRiskManager(formInfo.getRelatedProjectCode());
			if (userInfo != null) {
				notifyUserList.add(userInfo.toSimpleUserInfo());
				
			}
			
			//法务经理
			userInfo = projectRelatedUserService.getLegalManager(formInfo.getRelatedProjectCode());
			if (userInfo != null) {
				notifyUserList.add(userInfo.toSimpleUserInfo());
				
			}
		} catch (Exception e) {
			logger.error("获取立项结果通知人员出错{}", e);
		}
		return notifyUserList;
	}
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		
		FInvestigationDO investigation = FInvestigationDAO.findByFormId(formInfo.getFormId());
		
		if (null == investigation) {
			return null;
		}
		
		List<FlowVarField> fields = Lists.newArrayList();
		
		//设置B角
		ProjectRelatedUserInfo busiManagerb = projectRelatedUserService.getBusiManagerbByPhase(
			investigation.getProjectCode(), ChangeManagerbPhaseEnum.INVESTIGATING_PHASES);
		if (null != busiManagerb) {
			FlowVarField managerBID = new FlowVarField();
			managerBID.setVarName("BID");
			managerBID.setVarType(FlowVarTypeEnum.STRING);
			managerBID.setVarVal(busiManagerb.getUserId() + "");
			fields.add(managerBID);
		}
		
		if (formInfo.getFormCode() == FormCodeEnum.INVESTIGATION_LITIGATION) { //诉讼保函
			//法务专员
			ProjectRelatedUserInfo legalManger = projectRelatedUserService
				.getLegalManager(investigation.getProjectCode());
			if (null != legalManger) {
				FlowVarField lawManagerID = new FlowVarField();
				lawManagerID.setVarName("LawManagerID");
				lawManagerID.setVarType(FlowVarTypeEnum.STRING);
				lawManagerID.setVarVal(legalManger.getUserId() + "");
				fields.add(lawManagerID);
			}
		} else if (formInfo.getFormCode() == FormCodeEnum.INVESTIGATION_BASE) { //担保委贷
			//风险经理ID
			ProjectRelatedUserInfo riskManager = projectRelatedUserService
				.getRiskManager(investigation.getProjectCode());
			if (null != riskManager) {
				FlowVarField riskManagerId = new FlowVarField();
				riskManagerId.setVarName("RiskManagerID");
				riskManagerId.setVarType(FlowVarTypeEnum.STRING);
				riskManagerId.setVarVal(riskManager.getUserId() + "");
				fields.add(riskManagerId);
			}
		}
		
		return fields;
	}
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		long formId = order.getFormInfo().getFormId();
		FInvestigationDO investigation = FInvestigationDAO.findByFormId(formId);
		ProjectDO project = projectDAO.findByProjectCode(investigation.getProjectCode());
		if (ProjectStatusEnum.PAUSE.code().equals(project.getStatus())) {
			result.setFcsResultEnum(FcsResultEnum.DO_ACTION_STATUS_ERROR);
			result.setMessage("项目暂缓，不能提交");
			result.setSuccess(false);
			return result;
		}
		
		//自定义待办任务名称
		WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get().getAttribute(
			"startOrder");
		if (startOrder != null) {
			startOrder.setCustomTaskName(investigation.getProjectName() + "-项目送审");
		}
		result.setSuccess(true);
		
		deleteCheckingHistoryData(formId);
		
		return result;
	}
	
	/**
	 * 删除前一次审核过程修改的历史数据
	 * @param formId
	 */
	private void deleteCheckingHistoryData(long formId) {
		List<InvestigationCheckingInfo> checkList = investigationService
			.findCheckingByRelatedFormId(formId);
		if (ListUtil.isNotEmpty(checkList)) {
			for (InvestigationCheckingInfo check : checkList) {
				FInvestigationCheckingDAO.deleteById(check.getId());
			}
		}
	}
	
	@Override
	public FcsBaseResult doNextBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = createResult();
		try {
			
			Map<String, Object> customizeMap = order.getCustomizeMap();
			logger.info("尽职调查流程审核前处理开始 ：{}", customizeMap);
			long formId = order.getFormInfo().getFormId();
			Object editRiskReport = customizeMap.get("editRiskReport");
			if (null != editRiskReport
				&& StringUtil.equals(editRiskReport.toString(), BooleanEnum.YES.code())) {
				//诉讼保函 法务填写风险意见
				FInvestigationDO investigation = FInvestigationDAO.findByFormId(formId);
				FRiskReviewOrder riskReviewOrder = new FRiskReviewOrder();
				BeanCopier.staticCopy(investigation, riskReviewOrder);
				riskReviewOrder.setFormId(formId);
				riskReviewOrder.setCheckIndex(-1);
				riskReviewOrder.setCheckStatus(1);
				Object id = customizeMap.get("riskReportId");
				if (null != id) {
					riskReviewOrder.setId(Long.valueOf(id.toString()));
				}
				Object auditOpinion = customizeMap.get("auditOpinion");
				if (null != auditOpinion) {
					riskReviewOrder.setAuditOpinion(auditOpinion.toString());
				}
				Object synthesizeOpinion = customizeMap.get("synthesizeOpinion");
				if (null != synthesizeOpinion) {
					riskReviewOrder.setSynthesizeOpinion(synthesizeOpinion.toString());
				}
				Object reportContent = customizeMap.get("reportContent");
				if (null != reportContent) {
					riskReviewOrder.setReportContent(reportContent.toString());
				}
				riskReviewReportService.save(riskReviewOrder);
			}
			
			//承销项目尽职调查报告 是否上母公司会议
			if (order.getFormInfo().getFormCode() == FormCodeEnum.INVESTIGATION_UNDERWRITING) {
				String chooseCouncil = (String) customizeMap.get("chooseCouncil");
				if (BooleanEnum.YES.code().equals(chooseCouncil)) { //选择会议节点的时候才处理,防止数据丢失
					String isCouncil = (String) customizeMap.get("isCouncil");
					UpdateCouncilTypeOrder updateCouncilTypeOrder = new UpdateCouncilTypeOrder();
					FInvestigationDO investigation = FInvestigationDAO.findByFormId(formId);
					updateCouncilTypeOrder.setInvestigateId(investigation.getInvestigateId());
					if (BooleanEnum.YES.code().equals(isCouncil)) {
						String councilCode = (String) customizeMap.get("councilCode");
						updateCouncilTypeOrder.setCouncilType(councilCode);
						updateCouncilTypeOrder.setCouncilStatus(null);
						updateCouncilTypeOrder.setCouncilApplyId(0l);
					} else {
						updateCouncilTypeOrder.setCouncilType(null);
						updateCouncilTypeOrder.setCouncilStatus(null);
						updateCouncilTypeOrder.setCouncilApplyId(0l);
					}
					investigationService.update(updateCouncilTypeOrder);
				}
			}
			
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			logger.error("尽职调查流程审核前处理出错{}", e);
		}
		return result;
	}
	
	@Override
	public void doNextAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 审核后业务处理(BASE)
		try {
			Map<String, Object> customizeMap = workflowResult.getCustomizeMap();
			logger.info(FLAG + "流程审核后处理开始 ：{}", customizeMap);
			long formId = formInfo.getFormId();
			
			Object hiddenUrls = customizeMap.get("pathName_RISK_REVIEW");
			if (null != hiddenUrls && StringUtil.isNotBlank(hiddenUrls.toString())) {
				FInvestigationDO investigation = FInvestigationDAO.findByFormId(formId);
				CommonAttachmentTypeEnum type = CommonAttachmentTypeEnum.RISK_REVIEW;
				SimpleUserInfo currentUser = (SimpleUserInfo) FcsPmDomainHolder.get().getAttribute(
					"currentUser");
				
				FcsBaseResult result = addAttachfile(investigation.getFormId() + "", currentUser,
					investigation.getProjectCode(), type.message(), hiddenUrls.toString(), type);
				logger.info(FLAG + "更新附件信息，" + result);
				
			}
			
			Object remark = customizeMap.get("customizeFieldMap_formRemark_riskReview");
			if (null != remark) {
				Map<String, Object> map = new HashMap<>();
				map.put("formRemark_riskReview", remark);
				FcsBaseResult result = updateFormRemark(formId, map);
				logger.info(FLAG + "更新备注信息，" + result);
			}
		} catch (Exception e) {
			logger.error(FLAG + "审核后处理出错{}", e);
			e.printStackTrace();
		}
	}
	
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		
		//项目终止资料
		Map<String, Object> customizeMap = workflowResult.getCustomizeMap();
		Object projectEndFile = customizeMap.get("projectEndFile");
		if (null != projectEndFile && StringUtil.isNotBlank(projectEndFile.toString())) {
			CommonAttachmentTypeEnum type = CommonAttachmentTypeEnum.PROJECT_END_FILE;
			SimpleUserInfo currentUser = (SimpleUserInfo) FcsPmDomainHolder.get().getAttribute(
				"currentUser");
			FcsBaseResult result = addAttachfile(formInfo.getFormId() + "", currentUser,
				formInfo.getRelatedProjectCode(), type.message(), projectEndFile.toString(), type);
			logger.info(FLAG + "项目终止附件，" + result);
		}
		
		//项目变成未成立
		ChangeProjectStatusOrder order = new ChangeProjectStatusOrder();
		order.setProjectCode(formInfo.getRelatedProjectCode());
		//项目失败
		order.setPhase(ProjectPhasesEnum.INVESTIGATING_PHASES);
		order.setPhaseStatus(ProjectPhasesStatusEnum.NOPASS);
		order.setStatus(ProjectStatusEnum.FAILED);
		projectService.changeProjectStatus(order);
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		long formId = formInfo.getFormId();
		FInvestigationDO investigation = FInvestigationDAO.findByFormId(formId);
		ProjectDO project = projectDAO.findByProjectCode(investigation.getProjectCode());
		//项目进入尽职调查阶段
		project.setPhases(ProjectPhasesEnum.COUNCIL_PHASES.code());
		project.setStatus(ProjectStatusEnum.NORMAL.code());
		project.setPhasesStatus(ProjectPhasesStatusEnum.WAITING.code());
		project.setAmount(investigation.getAmount());
		// 将有关的信息写回project表中
		if (ProjectUtil.isLitigation(project.getBusiType())) {
			FInvestigationLitigationDO litigation = FInvestigationLitigationDAO
				.findByFormId(formId);
			if (null != litigation) {
				project.setAmount(litigation.getGuaranteeAmount());
			}
			//XXX 同步修改的数据到客户系统
			syncCustomer2CrmLitigation(formInfo, ChangeTypeEnum.JD);
		} else if (ProjectUtil.isUnderwriting(project.getBusiType())) {
			FInvestigationUnderwritingDO underwriting = FInvestigationUnderwritingDAO
				.findByFormId(formId);
			if (null != underwriting) {
				project.setTimeLimit(underwriting.getTimeLimit());
				project.setTimeUnit(underwriting.getTimeUnit());
			}
		} else {
			FInvestigationCreditSchemeDO creditScheme = FInvestigationCreditSchemeDAO
				.findByFormId(formId);
			if (null != creditScheme) {
				project.setAmount(creditScheme.getCreditAmount());
				project.setTimeLimit(creditScheme.getTimeLimit());
				project.setTimeUnit(creditScheme.getTimeUnit());
			}
			
			//XXX 同步修改的数据到客户系统
			syncCustomer2Crm(formInfo, ChangeTypeEnum.JD);
			
			//XXX 关联资产
			syncPledgeAssert(formInfo, project);
		}
		
		projectDAO.update(project);
		
		//记录尽调审核通过时间
		try {
			ProjectExtendOrder extendOrder = new ProjectExtendOrder();
			extendOrder.setProjectCode(investigation.getProjectCode());
			extendOrder.setProperty(ProjectExtendPropertyEnum.INVESTIGATE_TIME);
			extendOrder.setPropertyValue(DateUtil.simpleFormatYmdhms(getSysdate()));
			extendOrder.setUpdateOld(true);
			projectExtendService.saveExtendInfo(extendOrder);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("保存尽调审核通过时间异常：" + e.getMessage());
		}
		
		CouncilApplyOrder order = new CouncilApplyOrder();
		//流程结束，申请上会
		if (formInfo.getFormCode() == FormCodeEnum.INVESTIGATION_UNDERWRITING) {
			// 承销 总经理办公会
			order.setCouncilCode(CouncilTypeEnum.GM_WORKING.code());
			order.setCouncilTypeDesc(CouncilTypeEnum.GM_WORKING.message());
			if (StringUtil.isNotBlank(investigation.getCouncilType())) {
				if (ProjectCouncilEnum.SELF_GW_MONTHER_PR.code().equals(
					investigation.getCouncilType())) {
					order.setMotherCompanyApply(BooleanEnum.YES);
					order.setMotherCouncilCode(CouncilTypeEnum.PROJECT_REVIEW.code());
				} else if (ProjectCouncilEnum.SELF_MOTHER_GW.code().equals(
					investigation.getCouncilType())) {
					order.setMotherCompanyApply(BooleanEnum.YES);
					order.setMotherCouncilCode(CouncilTypeEnum.GM_WORKING.code());
				}
			} else {
				//更新自己的上会类型为总经理办公会-方便扫描筛选数据
				investigation.setCouncilType(ProjectCouncilEnum.SELF_GW.code());
			}
		} else {
			// 其它 项目评审会
			order.setCouncilCode(CouncilTypeEnum.PROJECT_REVIEW.code());
			order.setCouncilTypeDesc(CouncilTypeEnum.PROJECT_REVIEW.message());
		}
		order.setFormId(formId);
		
		order.setProjectCode(investigation.getProjectCode());
		order.setProjectName(investigation.getProjectName());
		order.setCustomerId(investigation.getCustomerId());
		order.setCustomerName(investigation.getCustomerName());
		order.setAmount(investigation.getAmount());
		order.setApplyManId(formInfo.getUserId());
		order.setApplyManName(formInfo.getUserName());
		order.setApplyDeptId(formInfo.getDeptId());
		order.setApplyDeptName(formInfo.getDeptName());
		order.setApplyTime(getSysdate());
		order.setStatus(CouncilApplyStatusEnum.WAIT.code());
		order.setTimeLimit(project.getTimeLimit());
		order.setTimeUnit(project.getTimeUnit());
		FcsBaseResult result = councilApplyService.saveCouncilApply(order);
		if (!result.isSuccess()) {
			logger.info("写入上会数据异常(尽调)：{}", result);
		} else if (formInfo.getFormCode() == FormCodeEnum.INVESTIGATION_UNDERWRITING) {
			investigation.setCouncilApplyId(result.getKeyId());
			investigation.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_WAITING.code());
			FInvestigationDAO.update(investigation);
			logger.info("承销业务申明变成上会中 {} ", result);
		}
		
		//复议的不再发送
		if (project.getLastRecouncilTime() == null) {
			try {
				//流程结束后通知客户经理填写项目小结
				ProjectRelatedUserInfo busiManager = projectRelatedUserService
					.getBusiManager(project.getProjectCode());
				if (busiManager != null) {
					MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
					String url = CommonUtil.getRedirectUrl("/projectMg/summary/form.htm")
									+ "&projectCode=" + project.getProjectCode();
					
					String messageContent = "项目编号：" + project.getProjectCode() + "，客户名称："
											+ project.getCustomerName()
											+ "尽职调查报告审核已通过，请在归档前填写《尽职调查报告项目小结》，<a href='" + url
											+ "'>点击填写</>";
					
					messageOrder.setMessageSubject("项目小结填写提醒");
					messageOrder.setMessageTitle(messageOrder.getMessageSubject());
					messageOrder.setMessageContent(messageContent);
					messageOrder
						.setSendUsers(new SimpleUserInfo[] { busiManager.toSimpleUserInfo() });
					siteMessageService.addMessageInfo(messageOrder);
				}
			} catch (Exception e) {
				logger.error("通知客户经理填写项目小结出错：{}", e);
			}
		}
		
		if (formInfo.getFormCode() == FormCodeEnum.INVESTIGATION_BASE) {
			List<FInvestigationCreditSchemePledgeAssetDO> list = FInvestigationCreditSchemePledgeAssetDAO
				.findByFormId(formId);
			if (ListUtil.isNotEmpty(list)) {
				//审核通过后，添加到资产复评列表 (资产列表不为空)
				FInvestigationAssetReviewOrder assetReviewOrder = new FInvestigationAssetReviewOrder();
				assetReviewOrder.setFormId(formId);
				assetReviewOrder.setReview(investigation.getReview());
				assetReviewOrder.setProjectCode(investigation.getProjectCode());
				assetReviewOrder.setProjectName(investigation.getProjectName());
				assetReviewOrder.setCustomerId(investigation.getCustomerId());
				assetReviewOrder.setCustomerName(investigation.getCustomerName());
				assetReviewOrder.setBusiType(investigation.getBusiType());
				assetReviewOrder.setBusiTypeName(investigation.getBusiTypeName());
				assetReviewOrder.setAmount(investigation.getAmount());
				assetReviewOrder.setBusiManagerId(project.getBusiManagerId());
				assetReviewOrder.setBusiManagerAccount(project.getBusiManagerAccount());
				assetReviewOrder.setBusiManagerName(project.getBusiManagerName());
				//风险经理
				ProjectRelatedUserInfo riskManager = projectRelatedUserService
					.getRiskManager(formInfo.getRelatedProjectCode());
				if (riskManager != null) {
					assetReviewOrder.setRiskManagerId(riskManager.getUserId());
					assetReviewOrder.setRiskManagerAccount(riskManager.getUserAccount());
					assetReviewOrder.setRiskManagerName(riskManager.getUserName());
					
				}
				assetReviewOrder.setStatus(AssetReviewStatusEnum.NO_REVIEW.code());
				assetReviewService.save(assetReviewOrder);
			}
			
			//同步信息到风险监控
			synCustomInfo(formId);
		}
	}
	
	private void synCustomInfo(long formId) {
		SynsCustomInfoOrder customOrder = new SynsCustomInfoOrder();
		FInvestigationMainlyReviewInfo mainlyReview = investigationService
			.findInvestigationMainlyReviewByFormId(formId);
		customOrder.setLicenseNo(mainlyReview.getBusiLicenseNo());
		customOrder.setCustomName(mainlyReview.getCustomerName());
		
		List<CreditStatusInfo> creditStatus = new ArrayList<>();
		List<GuaranteeInfo> guaranteeInfo = new ArrayList<>();
		List<DebtInfo> debtInfo = new ArrayList<>();
		List<BusinessRelationShipInfo> businessRelationship = new ArrayList<>();
		//信用状态
		if (ListUtil.isNotEmpty(mainlyReview.getCustomerCredits())) {
			for (FInvestigationMainlyReviewCreditStatusInfo info : mainlyReview
				.getCustomerCredits()) {
				CreditStatusInfo orderInfo = new CreditStatusInfo();
				orderInfo.setOrganization(info.getLoanInstitution());
				orderInfo.setFinanceAmount(info.getLoanBalance());
				orderInfo.setStartTime(DateUtil.simpleFormatYmdhms(info.getLoanStartDate()));
				orderInfo.setEndTime(DateUtil.simpleFormatYmdhms(info.getLoanEndDate()));
				orderInfo.setFinanceCost(info.getLoanCost() + "");
				orderInfo.setGuaranteeWay(info.getGuaranteePledge());
				//					orderInfo.setGuaranty(guaranty);
				//					orderInfo.setConsideration(consideration);
				creditStatus.add(orderInfo);
			}
		}
		//对外担保信息
		if (ListUtil.isNotEmpty(mainlyReview.getCustomerGuarantees())) {
			for (FInvestigationMainlyReviewExternalGuaranteeInfo info : mainlyReview
				.getCustomerGuarantees()) {
				GuaranteeInfo orderInfo = new GuaranteeInfo();
				orderInfo.setVouchee(info.getWarrantee());
				orderInfo.setGuaranteeAmount(info.getAmount());
				orderInfo.setGuaranteeWay(info.getGuarranteeWay());
				orderInfo.setExpiryDate(info.getTimeLimit());
				//					orderInfo.setConsideration(consideration);
				guaranteeInfo.add(orderInfo);
			}
		}
		//负债状况
		if (ListUtil.isNotEmpty(mainlyReview.getAssetAndLiabilities())) {
			for (FInvestigationMainlyReviewAssetAndLiabilityInfo info : mainlyReview
				.getAssetAndLiabilities()) {
				if (StringUtil.isNotBlank(info.getHasFolkLoan())
					&& info.getHasFolkLoan().contains("F")) {
					DebtInfo orderInfo = new DebtInfo();
					orderInfo.setDebtType("民间借贷");
					//orderInfo.setBank(bank);
					orderInfo.setLoanAmount(info.getFolkLoanAmount());
					//orderInfo.setStartTime(startTime);
					//orderInfo.setEndTime(endTime);
					debtInfo.add(orderInfo);
				}
				if (StringUtil.isNotBlank(info.getHasBankLoan())
					&& info.getHasBankLoan().contains("B")) {
					if (info.getHasBankLoan().contains("C")) {
						DebtInfo orderInfo = new DebtInfo();
						orderInfo.setDebtType("消费类借款");
						orderInfo.setBank(info.getConsumerLoanBank());
						orderInfo.setLoanAmount(info.getConsumerLoanAmount());
						orderInfo.setStartTime(DateUtil.simpleFormatYmdhms(info
							.getConsumerLoanStartDate()));
						orderInfo.setEndTime(DateUtil.simpleFormatYmdhms(info
							.getConsumerLoanEndDate()));
						debtInfo.add(orderInfo);
					}
					if (info.getHasBankLoan().contains("P")) {
						DebtInfo orderInfo = new DebtInfo();
						orderInfo.setDebtType("个人经营性贷款");
						orderInfo.setBank(info.getBusinesLoanBank());
						orderInfo.setLoanAmount(info.getBusinesLoanAmount());
						orderInfo.setStartTime(DateUtil.simpleFormatYmdhms(info
							.getBusinesLoanStartDate()));
						orderInfo.setEndTime(DateUtil.simpleFormatYmdhms(info
							.getBusinesLoanEndDate()));
						debtInfo.add(orderInfo);
					}
					if (info.getHasBankLoan().contains("M")) {
						DebtInfo orderInfo = new DebtInfo();
						orderInfo.setDebtType("按揭类贷款");
						orderInfo.setBank(info.getMortgageLoanBank());
						orderInfo.setLoanAmount(info.getMortgageLoanAmount());
						orderInfo.setStartTime(DateUtil.simpleFormatYmdhms(info
							.getMortgageLoanStartDate()));
						orderInfo.setEndTime(DateUtil.simpleFormatYmdhms(info
							.getMortgageLoanEndDate()));
						debtInfo.add(orderInfo);
					}
				}
			}
		}
		
		FInvestigationOpabilityReviewInfo opability = investigationService
			.findFInvestigationOpabilityReviewByFormId(formId);
		//重大经营关系 上游
		if (ListUtil.isNotEmpty(opability.getUpStreams())) {
			for (FInvestigationOpabilityReviewUpdownStreamInfo info : opability.getUpStreams()) {
				BusinessRelationShipInfo orderInfo = new BusinessRelationShipInfo();
				orderInfo.setBudgetRelation(BudgetRelationEnum.UP);
				orderInfo.setEnterpriseName(info.getName());
				orderInfo.setTradeAmount(info.getEndingBalance());
				businessRelationship.add(orderInfo);
			}
		}
		//重大经营关系 下游
		if (ListUtil.isNotEmpty(opability.getDownStreams())) {
			for (FInvestigationOpabilityReviewUpdownStreamInfo info : opability.getDownStreams()) {
				BusinessRelationShipInfo orderInfo = new BusinessRelationShipInfo();
				orderInfo.setBudgetRelation(BudgetRelationEnum.DOWN);
				orderInfo.setEnterpriseName(info.getName());
				orderInfo.setTradeAmount(info.getEndingBalance());
				businessRelationship.add(orderInfo);
			}
		}
		
		customOrder.setCreditStatus(creditStatus);
		customOrder.setGuaranteeInfo(guaranteeInfo);
		customOrder.setDebtInfo(debtInfo);
		customOrder.setBusinessRelationship(businessRelationship);
		customOrder.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
		
		BornSynResultBase riskResult = riskSystemFacadeClient.synCustomInfo(customOrder);
		logger.info("发送客户基础信息到风险监控：" + riskResult);
	}
	
	@Override
	public void deleteAfterProcess(FormInfo formInfo) {
		FInvestigationDO investigation = FInvestigationDAO.findByFormId(formInfo.getFormId());
		if (null != investigation) {
			ChangeProjectStatusOrder changeOrder = new ChangeProjectStatusOrder();
			changeOrder.setPhase(ProjectPhasesEnum.INVESTIGATING_PHASES);
			changeOrder.setStatus(ProjectStatusEnum.NORMAL);
			changeOrder.setPhaseStatus(ProjectPhasesStatusEnum.WAITING);
			changeOrder.setProjectCode(investigation.getProjectCode());
			ProjectResult presult = projectService.changeProjectStatus(changeOrder);
			if (!presult.isSuccess()) {
				logger.error("更新项目状态出错：" + presult.getMessage());
			}
		}
		
		/** 删除对应的附件数据 start */
		investigationService.deleteInvestigationCommonAttachment(formInfo.getFormId());
//		commonAttachmentService.deleteByBizNoModuleType(formInfo.getFormId() + "",
//			CommonAttachmentTypeEnum.COUNTER_GUARANTEE);
//		commonAttachmentService.deleteByBizNoModuleType(formInfo.getFormId() + "_index0",
//			CommonAttachmentTypeEnum.FORM_ATTACH);
//		commonAttachmentService.deleteByBizNoModuleType(formInfo.getFormId() + "_index1",
//			CommonAttachmentTypeEnum.FORM_ATTACH);
//		commonAttachmentService.deleteByBizNoModuleType(formInfo.getFormId() + "_index2",
//			CommonAttachmentTypeEnum.FORM_ATTACH);
//		commonAttachmentService.deleteByBizNoModuleType(formInfo.getFormId() + "_index3",
//			CommonAttachmentTypeEnum.FORM_ATTACH);
//		commonAttachmentService.deleteByBizNoModuleType(formInfo.getFormId() + "_index4",
//			CommonAttachmentTypeEnum.FORM_ATTACH);
//		commonAttachmentService.deleteByBizNoModuleType(formInfo.getFormId() + "_index5",
//			CommonAttachmentTypeEnum.FORM_ATTACH);
//		commonAttachmentService.deleteByBizNoModuleType(formInfo.getFormId() + "_index6",
//			CommonAttachmentTypeEnum.FORM_ATTACH);
//		commonAttachmentService.deleteByBizNoModuleType(formInfo.getFormId() + "_index7",
//			CommonAttachmentTypeEnum.FORM_ATTACH);
//		commonAttachmentService.deleteByBizNoModuleType(formInfo.getFormId() + "_index8",
//			CommonAttachmentTypeEnum.FORM_ATTACH);
//		
//		if (FormCodeEnum.INVESTIGATION_UNDERWRITING == formInfo.getFormCode()) {
//			FInvestigationUnderwritingInfo underwritingInfo = investigationService
//				.findFInvestigationUnderwritingByFormId(formInfo.getFormId());
//			if (underwritingInfo.getUnderwritingId() > 0) {
//				commonAttachmentService.deleteByBizNoModuleType(
//					underwritingInfo.getUnderwritingId() + "",
//					CommonAttachmentTypeEnum.UNDERWRITING_PROJECT);
//			}
//		}
		/** 删除对应的附件数据 end */
	}
	
	/**
	 * 同步客户信息到CRM
	 * @param formInfo
	 * @param changeType 修改来源
	 */
	private void syncCustomer2CrmLitigation(FormInfo formInfo, ChangeTypeEnum changeType) {
		try {
			FInvestigationLitigationDO litigation = FInvestigationLitigationDAO
				.findByFormId(formInfo.getFormId());
			if (litigation != null) {
				//成立时间、 法定代表人、 企业类型 页面无法修改 不同步
				//客户原始数据
				CustomerDetailOrder customerDetailOrder = getCustomerInfo(litigation
					.getCustomerId());
				//修改来源设置
				customerDetailOrder.setChangeType(changeType);
				//修改人设置
				customerDetailOrder.setOperId(formInfo.getUserId());
				customerDetailOrder.setOperName(formInfo.getUserName());
				//新数据覆盖	
				customerDetailOrder.setContactNo(litigation.getContactNo());
				if (!StringUtil.equals(customerDetailOrder.getCustomerType(),
					CustomerTypeEnum.PERSIONAL.code())) {
					customerDetailOrder.setContactMan(litigation.getContactPerson());
				}
				
				//同步到CRM
				logger.info("同步尽职调查客户数据到CRM :{}", customerDetailOrder);
				
				FcsBaseResult syncResult = customerServiceClient
					.updateByUserId(customerDetailOrder);
				//				FcsBaseResult syncResult = companyCustomerServiceClient.update(fullInfo);
				logger.info("同步尽职调查客户数据到CRM结果 :{}", syncResult);
				
			}
		} catch (Exception e) {
			logger.error("同步客户数据到CRM出错{}", e);
		}
	}
	
	/**
	 * 同步客户信息到CRM
	 * @param formInfo
	 * @param changeType 修改来源
	 */
	private void syncCustomer2Crm(FormInfo formInfo, ChangeTypeEnum changeType) {
		try {
			FInvestigationMainlyReviewInfo mr = investigationService
				.findInvestigationMainlyReviewByFormId(formInfo.getFormId());
			if (mr != null) {
				//成立时间、 法定代表人、 企业类型 页面无法修改 不同步
				//客户原始数据
				CustomerDetailOrder customerDetailOrder = getCustomerInfo(mr.getCustomerId());
				//修改来源设置
				customerDetailOrder.setChangeType(changeType);
				//修改人设置
				customerDetailOrder.setOperId(formInfo.getUserId());
				customerDetailOrder.setOperName(formInfo.getUserName());
				//新数据覆盖	
				customerDetailOrder.setLegalPersionAddress(mr.getLivingAddress());
				customerDetailOrder.setActualControlMan(mr.getActualControlPerson());
				customerDetailOrder.setAddress(mr.getWorkAddress());
				customerDetailOrder.setLoanCardNo(mr.getLoanCardNo());
				customerDetailOrder.setBusiScope(mr.getBusiScope());
				customerDetailOrder.setFinalYearCheck(mr.getLastCheckYear());
				customerDetailOrder.setLocalTaxCertNo(mr.getLocalTaxNo());
				customerDetailOrder.setTaxCertificateNo(mr.getTaxCertificateNo());
				
				//资格证书 
				List<CompanyQualificationInfo> cList = Lists.newArrayList();
				if (ListUtil.isNotEmpty(mr.getCertificates())) {
					for (FInvestigationMainlyReviewCertificateInfo c : mr.getCertificates()) {
						CompanyQualificationInfo ci = new CompanyQualificationInfo();
						ci.setCustomerId(customerDetailOrder.getCustomerId());
						ci.setQualificationCode(c.getCertificateCode());
						ci.setQualificationName(c.getCertificateName());
						if (c.getValidDate() != null)
							ci.setExperDate(DateUtil.dtSimpleFormat(c.getValidDate()));
						cList.add(ci);
					}
				}
				customerDetailOrder.setCompanyQualification(cList);
				
				//开户行信息
				if (ListUtil.isNotEmpty(mr.getBanks())) {
					for (FInvestigationMainlyReviewBankInfo bank : mr.getBanks()) {
						if ("基本账户开户行".equals(bank.getBankDesc())) {
							customerDetailOrder.setBankAccount(bank.getBankName());
							customerDetailOrder.setAccountNo(bank.getAccountNo());
						} else if ("主要结算账户开户行1".equals(bank.getBankDesc())) {
							customerDetailOrder.setSettleBankAccount1(bank.getBankName());
							customerDetailOrder.setSettleAccountNo1(bank.getAccountNo());
						} else if ("主要结算账户开户行2".equals(bank.getBankDesc())) {
							customerDetailOrder.setSettleBankAccount2(bank.getBankName());
							customerDetailOrder.setSettleAccountNo2(bank.getAccountNo());
						} else if ("其他结算账户开户行".equals(bank.getBankDesc())) {
							customerDetailOrder.setSettleBankAccount3(bank.getBankName());
							customerDetailOrder.setSettleAccountNo3(bank.getAccountNo());
						}
					}
				} else {
					customerDetailOrder.setBankAccount(null);
					customerDetailOrder.setAccountNo(null);
					customerDetailOrder.setSettleBankAccount1(null);
					customerDetailOrder.setSettleAccountNo1(null);
					customerDetailOrder.setSettleBankAccount2(null);
					customerDetailOrder.setSettleAccountNo2(null);
					customerDetailOrder.setSettleBankAccount3(null);
					customerDetailOrder.setSettleAccountNo3(null);
				}
				
				//1.获取财务报表的数据，同步到过去
				addFinancialInfo(customerDetailOrder, formInfo.getFormId());
				
				//同步到CRM
				logger.info("同步尽职调查客户数据到CRM :{}", customerDetailOrder);
				
				FcsBaseResult syncResult = customerServiceClient
					.updateByUserId(customerDetailOrder);
				//				FcsBaseResult syncResult = companyCustomerServiceClient.update(fullInfo);
				logger.info("同步尽职调查客户数据到CRM结果 :{}", syncResult);
				
			}
		} catch (Exception e) {
			logger.error("同步客户数据到CRM出错{}", e);
		}
	}
	
	private void addFinancialInfo(CustomerDetailOrder order, long formId) {
		FInvestigationFinancialReviewDO review = findFinancial(formId);
		if (null == review) {
			return;
		}
		List<FInvestigationFinancialReviewKpiDO> kpies = FInvestigationFinancialReviewKpiDAO
			.findByReviewId(review.getFReviewId());
		if (ListUtil.isEmpty(kpies)) {
			return;
		}
		
		Map<String, List<String>> map = new HashMap<>(kpies.size() / 4);
		for (FInvestigationFinancialReviewKpiDO kpi : kpies) {
			List<String> list = map.get(kpi.getKpiCode());
			if (null == list) {
				list = new ArrayList<>(4);
				map.put(kpi.getKpiCode(), list);
			}
			list.add(kpi.getKpiValue());
		}
		
		//总资产
		String kpiCode = DataFinancialHelper.BALANCE_TOTAL_CAPITAL;
		Money totalAsset = queryFinancialKpi(map.get(kpiCode));
		if (null != totalAsset) {
			order.setTotalAsset(totalAsset); //设置总资产
		}
		//总负债
		kpiCode = DataFinancialHelper.BALANCE_TOTAL_LIABILITY;
		Money balance = queryFinancialKpi(map.get(kpiCode));
		if (null != totalAsset && null != balance) {
			order.setNetAsset(totalAsset.subtract(balance)); //设置净资产
		}
		//去年总资产
		kpiCode = DataFinancialHelper.BALANCE_TOTAL_CAPITAL;
		totalAsset = queryFinancialKpi(map.get(kpiCode), 1);
		if (null != totalAsset) {
			order.setTotalAssetLastYear(totalAsset); //设置去年总资产
		}
		//去年总负债
		kpiCode = DataFinancialHelper.BALANCE_TOTAL_LIABILITY;
		balance = queryFinancialKpi(map.get(kpiCode), 1);
		if (null != totalAsset && null != balance) {
			order.setNetAssetLastYear(totalAsset.subtract(balance)); //设置去年净资产
		}
		//去年资产负债率
		kpiCode = DataFinancialHelper.ASSETLIABILITY_RATIO;
		Double assetLiabilityRatio = queryFinancialKpid(map.get(kpiCode));
		if (null != assetLiabilityRatio) {
			order.setAssetLiabilityRatio(assetLiabilityRatio); //设置资产负债率
		}
		assetLiabilityRatio = queryFinancialKpid(map.get(kpiCode), 1);
		if (null != assetLiabilityRatio) {
			order.setAssetLiabilityRatioLastYear(assetLiabilityRatio); //设置去年资产负债率
		}
		//流动比率
		kpiCode = DataFinancialHelper.IQUIDITY_RATIO;
		assetLiabilityRatio = queryFinancialKpid(map.get(kpiCode));
		if (null != assetLiabilityRatio) {
			order.setLiquidityRatio(assetLiabilityRatio); //设置流动比率
		}
		//速动比率
		kpiCode = DataFinancialHelper.QUICK_RATIO;
		assetLiabilityRatio = queryFinancialKpid(map.get(kpiCode));
		if (null != assetLiabilityRatio) {
			order.setQuickRatio(assetLiabilityRatio); //设置速动比率
		}
		
		//一、主营业务收入
		kpiCode = DataFinancialHelper.PROFIT_MAIN_BUSINESS_INCOME;
		Money income = queryFinancialKpi(map.get(kpiCode), 1);
		if (null != income) {
			order.setSalesProceedsLastYear(income); //设置去年销售收入
		}
		//四、利润总额
		kpiCode = DataFinancialHelper.PROFIT_TOTAL_PROFIT;
		income = queryFinancialKpi(map.get(kpiCode), 1);
		if (null != income) {
			order.setTotalProfitLastYear(income); //设置去年利润总额
		}
		//一、主营业务收入
		kpiCode = DataFinancialHelper.PROFIT_MAIN_BUSINESS_INCOME;
		income = queryFinancialKpi(map.get(kpiCode));
		if (null != income) {
			order.setSalesProceedsThisYear(income); //设置今年销售收入
		}
		//四、利润总额
		kpiCode = DataFinancialHelper.PROFIT_TOTAL_PROFIT;
		income = queryFinancialKpi(map.get(kpiCode));
		if (null != income) {
			order.setTotalProfitThisYear(income); //设置去今利润总额
		}
	}
	
	private Money queryFinancialKpi(List<String> list) {
		return queryFinancialKpi(list, 0);
	}
	
	private Money queryFinancialKpi(List<String> list, int index) {
		try {
			if (null != list && list.size() >= index) {
				return Money.amout(list.get(index));
			}
		} catch (Exception e) {
			
		}
		return null;
	}
	
	private Double queryFinancialKpid(List<String> list) {
		return queryFinancialKpid(list, 0);
	}
	
	private Double queryFinancialKpid(List<String> list, int index) {
		try {
			if (null != list && list.size() >= index) {
				return Double.parseDouble(list.get(index));
			}
		} catch (Exception e) {
			
		}
		return null;
	}
	
	/**
	 * 同步资产和项目的关联关系
	 * @param formInfo
	 * @param project
	 */
	private void syncPledgeAssert(FormInfo formInfo, ProjectDO project) {
		try {
			
			FInvestigationCreditSchemeInfo sInfo = investigationService
				.findInvestigationCreditSchemeByFormId(formInfo.getFormId());
			
			if (sInfo != null) {
				
				List<FInvestigationCreditSchemePledgeAssetInfo> mortgages = sInfo.getMortgages();
				List<FInvestigationCreditSchemePledgeAssetInfo> pledges = sInfo.getPledges();
				
				AssetRelationProjectBindOrder bindOrder = new AssetRelationProjectBindOrder();
				BeanCopier.staticCopy(project, bindOrder);
				bindOrder.setDelOld(true);
				List<AssetStatusOrder> listOrder = Lists.newArrayList();
				//抵押
				if (ListUtil.isNotEmpty(pledges)) {
					for (FInvestigationCreditSchemePledgeAssetInfo p : pledges) {
						AssetStatusOrder as = new AssetStatusOrder();
						as.setAssetId(p.getAssetsId());
						as.setStatus(AssetStatusEnum.QUASI_PLEDGE);
						listOrder.add(as);
					}
				}
				//质押
				if (ListUtil.isNotEmpty(mortgages)) {
					for (FInvestigationCreditSchemePledgeAssetInfo m : mortgages) {
						AssetStatusOrder as = new AssetStatusOrder();
						as.setAssetId(m.getAssetsId());
						as.setStatus(AssetStatusEnum.QUASI_MORTGAGE);
						listOrder.add(as);
					}
				}
				bindOrder.setAssetList(listOrder);
				if (listOrder.size() > 0) {
					logger.info("同步资产项目关系 {} ", bindOrder);
					pledgeAssetServiceClient.assetRelationProject(bindOrder);
				}
			}
		} catch (Exception e) {
			logger.error("同步资产项目管理关系出错 : {}", e);
		}
	}
	
}
