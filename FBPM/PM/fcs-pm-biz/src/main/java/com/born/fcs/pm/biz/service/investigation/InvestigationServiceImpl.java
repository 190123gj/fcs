package com.born.fcs.pm.biz.service.investigation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.crm.ws.service.info.CompanyQualificationInfo;
import com.born.fcs.crm.ws.service.info.CustomerDetailInfo;
import com.born.fcs.pm.biz.convert.DoConvert;
import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.convert.VoConvert;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.CommonAttachmentDO;
import com.born.fcs.pm.dal.dataobject.FFinancialKpiDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationCheckingDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationCreditSchemeDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationCreditSchemeGuarantorDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationCreditSchemePledgeAssetDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationCreditSchemeProcessControlDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationCsRationalityReviewDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationCsRationalityReviewFinancialKpiDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationCsRationalityReviewGuarantorAbilityDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationCsRationalityReviewGuarantorDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationFinancialReviewDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationFinancialReviewKpiDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationLitigationDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationMabilityReviewDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationMabilityReviewLeadingTeamDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationMabilityReviewLeadingTeamResumeDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationMainlyReviewAssetAndLiabilityDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationMainlyReviewBankInfoDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationMainlyReviewCertificateDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationMainlyReviewCreditInfoDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationMainlyReviewCreditStatusDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationMainlyReviewDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationMainlyReviewExternalGuaranteeDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationMainlyReviewRelatedCompanyDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationMainlyReviewStockholderDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationMajorEventDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationOpabilityReviewDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationOpabilityReviewProductStructureDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationOpabilityReviewUpdownStreamDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationPersonDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationProjectStatusDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationProjectStatusFundDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationRiskDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationUnderwritingDO;
import com.born.fcs.pm.dal.dataobject.FormDO;
import com.born.fcs.pm.dal.dataobject.ProjectChannelRelationDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dataobject.InvestigationDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.crm.service.customer.CustomerServiceClient;
import com.born.fcs.pm.util.DataFinancialHelper;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.AmountUnitEnum;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ChannelRelationEnum;
import com.born.fcs.pm.ws.enums.ChargePhaseEnum;
import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.ChargeWayEnum;
import com.born.fcs.pm.ws.enums.CheckPointEnum;
import com.born.fcs.pm.ws.enums.CheckStatusEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.CompanyTypeEnum;
import com.born.fcs.pm.ws.enums.CreditTypeEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.EnterpriseNatureEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.GuaranteeTypeEnum;
import com.born.fcs.pm.ws.enums.GuarantorTypeEnum;
import com.born.fcs.pm.ws.enums.HolderTypeEnum;
import com.born.fcs.pm.ws.enums.KpiTypeEnum;
import com.born.fcs.pm.ws.enums.OwnerEnum;
import com.born.fcs.pm.ws.enums.ProcessControlEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.SiteStatusEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.UnderwritingChargeWaytEnum;
import com.born.fcs.pm.ws.enums.UpAndDownEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.AttachmentModuleType;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectChannelRelationInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.financialkpi.FinancialKpiInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCreditSchemeGuarantorInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCreditSchemeInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCreditSchemePledgeAssetInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCreditSchemeProcessControlInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCsRationalityReviewGuarantorAbilityInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCsRationalityReviewGuarantorInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCsRationalityReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationFinancialDataExplainInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationFinancialReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationFinancialReviewKpiInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationLitigationInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMabilityReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMabilityReviewLeadingTeamInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMabilityReviewLeadingTeamResumeInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMainlyReviewAssetAndLiabilityInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMainlyReviewBankInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMainlyReviewCertificateInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMainlyReviewCreditInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMainlyReviewCreditStatusInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMainlyReviewExternalGuaranteeInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMainlyReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMainlyReviewRelatedCompanyInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMainlyReviewStockholderInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMajorEventInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationOpabilityReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationOpabilityReviewProductStructureInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationOpabilityReviewUpdownStreamInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationPersonInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationProjectStatusFundInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationProjectStatusInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationRiskInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationUnderwritingInfo;
import com.born.fcs.pm.ws.info.finvestigation.FinancialReviewKpiInfo;
import com.born.fcs.pm.ws.info.finvestigation.InvestigationBaseInfo;
import com.born.fcs.pm.ws.info.finvestigation.InvestigationInfo;
import com.born.fcs.pm.ws.info.finvestigation.checking.InvestigationCheckingInfo;
import com.born.fcs.pm.ws.info.setup.FProjectGuaranteeEntrustedInfo;
import com.born.fcs.pm.ws.info.setup.FProjectInfo;
import com.born.fcs.pm.ws.order.common.CommonAttachmentQueryOrder;
import com.born.fcs.pm.ws.order.common.ProjectChannelRelationBatchOrder;
import com.born.fcs.pm.ws.order.common.ProjectChannelRelationOrder;
import com.born.fcs.pm.ws.order.financialkpi.FinancialKpiOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationAddOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationCopyOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationCreditSchemeGuarantorOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationCreditSchemeOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationCreditSchemePledgeAssetOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationCreditSchemeProcessControlOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationCsRationalityReviewGuarantorAbilityOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationCsRationalityReviewGuarantorOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationCsRationalityReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationFinancialDataExplainOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationFinancialReviewKpiOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationFinancialReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationLitigationOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationMabilityReviewLeadingTeamOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationMabilityReviewLeadingTeamResumeOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationMabilityReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationMainlyReviewAssetAndLiabilityOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationMainlyReviewBankInfoOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationMainlyReviewCertificateOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationMainlyReviewCreditInfoOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationMainlyReviewCreditStatusOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationMainlyReviewExternalGuaranteeOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationMainlyReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationMainlyReviewRelatedCompanyOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationMainlyReviewStockholderOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationMajorEventOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationOpabilityReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationOpabilityReviewProductStructureOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationOpabilityReviewUpdownStreamOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationProjectStatusFundOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationProjectStatusOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationRiskOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationUnderwritingOrder;
import com.born.fcs.pm.ws.order.finvestigation.FinancialReviewKpiOrder;
import com.born.fcs.pm.ws.order.finvestigation.InvestigationQueryOrder;
import com.born.fcs.pm.ws.order.finvestigation.UpdateInvestigationCustomerOrder;
import com.born.fcs.pm.ws.order.finvestigation.base.FInvestigationBaseOrder;
import com.born.fcs.pm.ws.order.finvestigation.base.InvestigationBaseOrder;
import com.born.fcs.pm.ws.order.finvestigation.checking.InvestigationCheckingOrder;
import com.born.fcs.pm.ws.order.finvestigation.declare.DeclareBaseOrder;
import com.born.fcs.pm.ws.order.finvestigation.declare.FInvestigationPersonOrder;
import com.born.fcs.pm.ws.order.project.UpdateProjectRecouncilOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.CommonAttachmentService;
import com.born.fcs.pm.ws.service.common.ProjectChannelRelationService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.financialkpi.FinancialKpiService;
import com.born.fcs.pm.ws.service.investigation.InvestigationService;
import com.born.fcs.pm.ws.service.setup.ProjectSetupService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * 
 * @author lirz
 * 
 * 2016-3-8 下午2:00:36
 */
@Service("investigationService")
public class InvestigationServiceImpl extends BaseFormAutowiredDomainService implements
																			InvestigationService {
	
	@Autowired
	private ProjectService projectService;
	@Autowired
	protected CustomerServiceClient customerServiceClient;
	@Autowired
	private ProjectSetupService projectSetupService;
	@Autowired
	private FinancialKpiService financialKpiService;
	@Autowired
	private CommonAttachmentService commonAttachmentService;
	
	
	@Override
	protected FormBaseResult createResult() {
		return new FormBaseResult();
	}
	
	@Override
	public FcsBaseResult updateInvestigation(final InvestigationBaseOrder order) {
		return commonProcess(order, "修改尽调基本信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				if (StringUtil.equals(BooleanEnum.YES.code(), order.getReview())) {
					long newFormId = processReview(order);
					FcsBaseResult result = (FcsBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					result.setKeyId(newFormId);
				} else if (StringUtil.equals(BooleanEnum.YES.code(), order.getCouncilBack())) {
					processCouncilBack(order);
				} else {
					FInvestigationDO investigation = FInvestigationDAO.findByFormId(order
						.getFormId());
					if (null == investigation) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到表单对应的尽调数据");
					}
					
					BeanCopier.staticCopy(order, investigation);
					FInvestigationDAO.update(investigation);
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FormBaseResult saveInvestigation(final FInvestigationOrder order) {
		//除了承销 和 诉讼 2个页面之外，其余都是3个
		if (StringUtils.isBlank(order.getVersion()) && !ProjectUtil.isLitigation(order.getBusiType()) && !ProjectUtil.isUnderwriting(order.getBusiType()) && ! ProjectUtil.isInnovativeProduct(order.getBusiType())) {
			order.setDefaultCheckStatus("000110");
		}
		return commonFormSaveProcess(order, "保存尽职调查声明", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				copyForAudit(order); //必须最先执行
				checkProjectPause(order.getProjectCode());
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				FInvestigationDO fInvestigationDO = null;
				if (null != order.getFormId() && order.getFormId() > 0) {
					fInvestigationDO = FInvestigationDAO.findByFormId(order.getFormId());
				}
				if (null == fInvestigationDO) {
					fInvestigationDO = new FInvestigationDO();
					BeanCopier.staticCopy(order, fInvestigationDO);
					fInvestigationDO.setFormId(form.getFormId());
					fInvestigationDO.setRawAddTime(now);
					ProjectDO project = projectDAO.findByProjectCodeForUpdate(order
						.getProjectCode());
					fInvestigationDO.setAmount(project.getAmount());
					if (StringUtil.isBlank(fInvestigationDO.getReview())) {
						fInvestigationDO.setReview(BooleanEnum.NO.code());
					}
					
					//一些基础属性直接复制过来
					fInvestigationDO.setProjectName(project.getProjectName());
					fInvestigationDO.setCustomerId(project.getCustomerId());
					fInvestigationDO.setCustomerName(project.getCustomerName());
					fInvestigationDO.setBusiType(project.getBusiType());
					fInvestigationDO.setBusiTypeName(project.getBusiTypeName());
					fInvestigationDO.setAmount(project.getAmount());
					
					FInvestigationDAO.insert(fInvestigationDO);
					
					project.setPhases(ProjectPhasesEnum.INVESTIGATING_PHASES.code());
					project.setStatus(ProjectStatusEnum.NORMAL.code());
					project.setPhasesStatus(ProjectPhasesStatusEnum.DRAFT.code());
					projectDAO.update(project);
					
					if (order.getCopiedFormId() > 0) {
						copy(form.getFormId(), order.getCopiedFormId(), fInvestigationDO);
					}
				} else {
					String review = StringUtil.isBlank(fInvestigationDO.getReview()) ? BooleanEnum.NO
						.code() : fInvestigationDO.getReview();
					//					if (order.getInvestigateId() != fInvestigationDO.getInvestigateId()) {
					//						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
					//							"表单已经过期，请刷新页面重新操作");
					//					}
					BeanCopier.staticCopy(order, fInvestigationDO);
					if (StringUtil.isBlank(fInvestigationDO.getReview())) {
						fInvestigationDO.setReview(review);
					}
					FInvestigationDAO.updateByFormId(fInvestigationDO);
					
					FInvestigationLitigationDO litigation = FInvestigationLitigationDAO
						.findByFormId(order.getFormId());
					if (null != litigation
						&& StringUtil.notEquals(fInvestigationDO.getInvestigatePersion(),
							litigation.getInvestigatePersion())) {
						litigation.setInvestigatePersion(fInvestigationDO.getInvestigatePersion());
						FInvestigationLitigationDAO.update(litigation);
					}
				}
				
				//保存调查情况人员
				savePerson(form.getFormId(), order.getPersons());
				
				return null;
			}
		}, null, null);
	}
	
	private void savePerson(long formId, List<FInvestigationPersonOrder> orders) {
		if (formId <= 0) {
			return;
		}
		
		if (ListUtil.isEmpty(orders)) {
			FInvestigationPersonDAO.deleteByFormId(formId);
			return;
		}
		
		List<FInvestigationPersonDO> items = FInvestigationPersonDAO.findByFormId(formId);
		Map<Long, FInvestigationPersonDO> map = new HashMap<>();
		if (ListUtil.isNotEmpty(items)) {
			for (FInvestigationPersonDO doObj : items) {
				map.put(doObj.getId(), doObj);
			}
		}
		
		int sortOrder = 1;
		Date now = getSysdate();
		for (FInvestigationPersonOrder order : orders) {
			if (order.isNull()) {
				continue;
			}
			
			order.setSortOrder(sortOrder++);
			order.setFormId(formId);
			FInvestigationPersonDO doObj = map.get(order.getId());
			if (null == doObj) {
				doObj = new FInvestigationPersonDO();
				BeanCopier.staticCopy(order, doObj);
				doObj.setRawAddTime(now);
				FInvestigationPersonDAO.insert(doObj);
			} else {
				if (!isEqual(order, doObj)) {
					BeanCopier.staticCopy(order, doObj);
					FInvestigationPersonDAO.update(doObj);
				}
			}
			
			map.remove(order.getId());
		}
		
		if (null != map && map.size() > 0) {
			for (long id : map.keySet()) {
				FInvestigationPersonDAO.deleteById(id);
			}
		}
	}
	
	private boolean isEqual(FInvestigationPersonOrder order, FInvestigationPersonDO doObj) {
		return order.getSortOrder() == doObj.getSortOrder()
				&& isEqual(order.getInvestigateDate(), doObj.getInvestigateDate())
				&& isEqual(order.getInvestigatePlace(), doObj.getInvestigatePlace())
				&& order.getMainInvestigatorId() == doObj.getMainInvestigatorId()
				&& isEqual(order.getMainInvestigatorAccount(), doObj.getMainInvestigatorAccount())
				&& isEqual(order.getMainInvestigatorName(), doObj.getMainInvestigatorName())
				&& isEqual(order.getAssistInvestigatorId(), doObj.getAssistInvestigatorId())
				&& isEqual(order.getAssistInvestigatorName(), doObj.getAssistInvestigatorName())
				&& isEqual(order.getReceptionPersion(), doObj.getReceptionPersion());
	}
	
	@Override
	public FcsBaseResult update(final DeclareBaseOrder order) {
		return commonProcess(order, "更新尽调申明信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				FInvestigationDO doObj = FInvestigationDAO.findById(order.getInvestigateId());
				if (null != doObj) {
					BeanCopier.staticCopy(order, doObj);
					FInvestigationDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FcsBaseResult updateToShow(long formId) {
		FcsBaseResult result = new FcsBaseResult();
		FInvestigationDO investigation = FInvestigationDAO.findByFormId(formId);
		if (null != investigation) {
			investigation.setReview(BooleanEnum.YES.code());
			FInvestigationDAO.update(investigation);
			
			FormDO form = formDAO.findByFormId(formId);
			form.setStatus(FormStatusEnum.DRAFT.code());
			form.setRelatedProjectCode(investigation.getProjectCode());
			form.setSubmitTime(null);
			form.setFinishTime(null);
			formDAO.update(form);
			
			UpdateProjectRecouncilOrder recouncilOrder = new UpdateProjectRecouncilOrder();
			recouncilOrder.setProjectCode(investigation.getProjectCode());
			recouncilOrder.setIsRecouncil(BooleanEnum.NO.code());
			recouncilOrder.setLastRecouncilTime(getSysdate());
			recouncilOrder.setStatus(ProjectStatusEnum.NORMAL.code());
			recouncilOrder.setPhases(ProjectPhasesEnum.INVESTIGATING_PHASES.code());
			recouncilOrder.setPhasesStatus(ProjectPhasesStatusEnum.DRAFT.code());
			projectService.updateByProjectCode(recouncilOrder);
			
			result.setSuccess(true);
			return result;
		}
		result.setSuccess(false);
		result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
		result.setMessage("没有找到相应的数据");
		return result;
	}
	
	private FormBaseResult addCopy(final FInvestigationAddOrder order) {
		return commonFormSaveProcess(order, "添加尽职调查申明", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				FInvestigationDO fInvestigationDO = new FInvestigationDO();
				BeanCopier.staticCopy(order, fInvestigationDO);
				fInvestigationDO.setFormId(form.getFormId());
				fInvestigationDO.setCustomerId(order.getCustomerId());
				fInvestigationDO.setRawAddTime(now);
				FInvestigationDAO.insert(fInvestigationDO);
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FInvestigationInfo findInvestigationByFormId(long formId) {
		FInvestigationDO fInvestigationDO = FInvestigationDAO.findByFormId(formId);
		if (null == fInvestigationDO) {
			return null;
		} else {
			FInvestigationInfo info = new FInvestigationInfo();
			info.setCurIndex(-1);
			BeanCopier.staticCopy(fInvestigationDO, info);
			info.setCouncilType(ProjectCouncilEnum.getByCode(fInvestigationDO.getCouncilType()));
			info.setCouncilStatus(ProjectCouncilStatusEnum.getByCode(fInvestigationDO
				.getCouncilStatus()));
			info.setCouncilBack(BooleanEnum.getByCode(fInvestigationDO.getCouncilBack()));
			// 查询调查情况人员
			queryPersons(info);
			return info;
		}
	}
	
	// 查询调查情况人员
	private void queryPersons(FInvestigationInfo info) {
		if (null == info || info.getFormId() <= 0) {
			return;
		}
		
		List<FInvestigationPersonDO> items = FInvestigationPersonDAO.findByFormId(info.getFormId());
		if (ListUtil.isNotEmpty(items)) {
			List<FInvestigationPersonInfo> persons = new ArrayList<>(items.size());
			for (FInvestigationPersonDO doObj : items) {
				FInvestigationPersonInfo person = new FInvestigationPersonInfo();
				BeanCopier.staticCopy(doObj, person);
				persons.add(person);
			}
			info.setPersons(persons);
		}
	}
	
	@Override
	public FormBaseResult saveInvestigationCreditScheme(final FInvestigationCreditSchemeOrder order) {
		
		return commonFormSaveProcess(order, "保存授信方案", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				copyForAudit(order); //必须最先执行
				checkProjectPause(order.getProjectCode());
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				FInvestigationDO investigation = FInvestigationDAO.findByFormId(form.getFormId());
				if (null == investigation) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "没有声明数据");
				}
				
				if (!investigation.getAmount().equals(order.getCreditAmount())) {
					investigation.setAmount(order.getCreditAmount());
					FInvestigationDAO.update(investigation);
				}
				
				long formId = form.getFormId();
				long schemeId = order.getSchemeId();
				FInvestigationCreditSchemeDO scheme = FInvestigationCreditSchemeDAO
					.findByFormId(order.getFormId());
				if (null == scheme) {
					scheme = new FInvestigationCreditSchemeDO();
					BeanCopier.staticCopy(order, scheme, UnBoxingConverter.getInstance());
					scheme.setTimeUnit(order.getTimeUnitStr());
					scheme.setChargePhase(order.getChargePhaseStr());
					scheme.setChargeWay(order.getChargeWayStr());
					scheme.setFormId(form.getFormId());
					scheme.setCreditAmount(order.getCreditAmount());
					scheme.setRawAddTime(now);
					schemeId = FInvestigationCreditSchemeDAO.insert(scheme);
					
				} else {
					//					if (scheme.getSchemeId() != schemeId) {
					//						throw ExceptionFactory.newFcsException(
					//							FcsResultEnum.DO_ACTION_STATUS_ERROR, "表单已经过期，请刷新页面重新操作");
					//					}
					BeanCopier.staticCopy(order, scheme, UnBoxingConverter.getInstance());
					scheme.setTimeUnit(order.getTimeUnitStr());
					scheme.setChargePhase(order.getChargePhaseStr());
					scheme.setChargeWay(order.getChargeWayStr());
					scheme.setCreditAmount(order.getCreditAmount());
					FInvestigationCreditSchemeDAO.updateByFormId(scheme);
					
				}
				//保存资金/项目渠道
				saveChannels(formId, ChannelRelationEnum.PROJECT_CHANNEL, order.getProjectChannel());
				saveChannels(formId, ChannelRelationEnum.CAPITAL_CHANNEL, order.getCapitalChannel());
				//保存抵质押信息
				savePledgeAssets(formId, order);
				//保存保证人信息
				saveGuarantors(schemeId, order.getGuarantorOrders());
				//新增 控制过程 客户主体评价
				//新增 控制过程 资产负债率
				//新增 控制过程 其他
				saveProcesses(formId, order);
				
				return null;
			}
		}, null, null);
	}
	
	@Autowired
	private ProjectChannelRelationService projectChannelRelationService;
	
	private void saveChannels(long formId, ChannelRelationEnum type,
								List<ProjectChannelRelationOrder> channels) {
		FInvestigationDO investigation = FInvestigationDAO.findByFormId(formId);
		if (null == investigation) {
			return;
		}
		
		if (formId <= 0) {
			return;
		}
		
		ProjectChannelRelationBatchOrder order = new ProjectChannelRelationBatchOrder();
		order.setBizNo(formId + "");
		order.setChannelRelation(type);
		order.setProjectCode(investigation.getProjectCode());
		order.setRelations(channels);
		order.setPhases(ProjectPhasesEnum.INVESTIGATING_PHASES);
		FcsBaseResult result = projectChannelRelationService.saveByBizNoAndType(order);
		logger.info("保存项目/资金渠道结果：" + result);
	}
	
	private void savePledgeAssets(long formId, FInvestigationCreditSchemeOrder order) {
		Map<GuaranteeTypeEnum, List<FInvestigationCreditSchemePledgeAssetDO>> map = new HashMap<>();
		List<FInvestigationCreditSchemePledgeAssetDO> list = FInvestigationCreditSchemePledgeAssetDAO
			.findByFormId(formId);
		if (ListUtil.isNotEmpty(list)) {
			for (FInvestigationCreditSchemePledgeAssetDO doObj : list) {
				GuaranteeTypeEnum type = GuaranteeTypeEnum.getByCode(doObj.getType());
				List<FInvestigationCreditSchemePledgeAssetDO> items = map.get(type);
				if (null == items) {
					items = new ArrayList<>();
					map.put(type, items);
				}
				items.add(doObj);
			}
		}
		savePledgeAssets(formId, GuaranteeTypeEnum.PLEDGE, order.getPledgeOrders(),
			map.get(GuaranteeTypeEnum.PLEDGE));
		savePledgeAssets(formId, GuaranteeTypeEnum.MORTGAGE, order.getMortgageOrders(),
			map.get(GuaranteeTypeEnum.MORTGAGE));
	}
	
	private void savePledgeAssets(long formId, GuaranteeTypeEnum type,
									List<FInvestigationCreditSchemePledgeAssetOrder> pledges,
									List<FInvestigationCreditSchemePledgeAssetDO> items) {
		if (formId <= 0 || null == type) {
			return;
		}
		
		if (ListUtil.isEmpty(pledges)) {
			FInvestigationCreditSchemePledgeAssetDAO.deleteByFormIdAndType(formId, type.code());
			return;
		}
		
		Map<Long, FInvestigationCreditSchemePledgeAssetDO> map = new HashMap<>();
		if (ListUtil.isNotEmpty(items)) {
			for (FInvestigationCreditSchemePledgeAssetDO doObj : items) {
				map.put(doObj.getId(), doObj);
			}
		}
		
		int sortOrder = 1;
		Date now = getSysdate();
		for (FInvestigationCreditSchemePledgeAssetOrder order : pledges) {
			order.setFormId(formId);
			order.setSortOrder(sortOrder++);
			order.setType(type.code());
			FInvestigationCreditSchemePledgeAssetDO doObj = map.get(order.getId());
			if (null == doObj) {
				doObj = new FInvestigationCreditSchemePledgeAssetDO();
				BeanCopier.staticCopy(order, doObj);
				doObj.setFormId(formId);
				doObj.setType(type.code());
				doObj.setTypeDesc(type.message());
				doObj.setDebtedAmount(order.getDebtedAmount());
				doObj.setRawAddTime(now);
				FInvestigationCreditSchemePledgeAssetDAO.insert(doObj);
			} else {
				if (!isEqual(order, doObj)) {
					BeanCopier.staticCopy(order, doObj);
					doObj.setType(type.code());
					doObj.setTypeDesc(type.message());
					doObj.setDebtedAmount(order.getDebtedAmount());
					FInvestigationCreditSchemePledgeAssetDAO.update(doObj);
				}
			}
			map.remove(order.getId());
		}
		
		if (null != map && map.size() > 0) {
			for (long id : map.keySet()) {
				FInvestigationCreditSchemePledgeAssetDAO.deleteById(id);
			}
		}
	}
	
	private boolean isEqual(FInvestigationCreditSchemePledgeAssetOrder order,
							FInvestigationCreditSchemePledgeAssetDO doObj) {
		return order.getAssetsId() == doObj.getAssetsId()
				&& StringUtil.equals(order.getAssetType(), doObj.getAssetType())
				&& StringUtil.equals(order.getOwnershipName(), doObj.getOwnershipName())
				&& order.getEvaluationPrice().equals(doObj.getEvaluationPrice())
				&& new Double(order.getPledgeRate()).equals(new Double(doObj.getPledgeRate()))
				&& StringUtil.equals(order.getSynPosition(), doObj.getSynPosition())
				&& StringUtil.equals(order.getPostposition(), doObj.getPostposition())
				&& order.getDebtedAmount().equals(doObj.getDebtedAmount())
				&& order.getSortOrder() == doObj.getSortOrder();
	}
	
	private void saveProcesses(long formId, FInvestigationCreditSchemeOrder order) {
		Map<ProcessControlEnum, List<FInvestigationCreditSchemeProcessControlDO>> map = new HashMap<>();
		List<FInvestigationCreditSchemeProcessControlDO> list = FInvestigationCreditSchemeProcessControlDAO
			.findByFormId(formId);
		if (ListUtil.isNotEmpty(list)) {
			for (FInvestigationCreditSchemeProcessControlDO doObj : list) {
				ProcessControlEnum type = ProcessControlEnum.getByCode(doObj.getType());
				List<FInvestigationCreditSchemeProcessControlDO> items = map.get(type);
				if (null == items) {
					items = new ArrayList<>();
					map.put(type, items);
				}
				items.add(doObj);
			}
		}
		
		saveProcesses(formId, ProcessControlEnum.CUSTOMER_GRADE, order.getCustomerGrades(),
			map.get(ProcessControlEnum.CUSTOMER_GRADE),
			order.isSave(ProcessControlEnum.CUSTOMER_GRADE));
		saveProcesses(formId, ProcessControlEnum.DEBT_RATIO, order.getDebtRatios(),
			map.get(ProcessControlEnum.DEBT_RATIO), order.isSave(ProcessControlEnum.DEBT_RATIO));
		saveProcesses(formId, ProcessControlEnum.OTHER, order.getOthers(),
			map.get(ProcessControlEnum.OTHER), order.isSave(ProcessControlEnum.OTHER));
	}
	
	private void saveProcesses(long formId, ProcessControlEnum type,
								List<FInvestigationCreditSchemeProcessControlOrder> orders,
								List<FInvestigationCreditSchemeProcessControlDO> items,
								boolean isSave) {
		if (formId <= 0 || type == null) {
			return;
		}
		
		if (!isSave || ListUtil.isEmpty(orders)) {
			FInvestigationCreditSchemeProcessControlDAO.deleteByFormIdAndType(formId, type.code());
			return;
		}
		
		Map<Long, FInvestigationCreditSchemeProcessControlDO> map = new HashMap<>();
		if (ListUtil.isNotEmpty(items)) {
			for (FInvestigationCreditSchemeProcessControlDO item : items) {
				map.put(item.getId(), item);
			}
		}
		
		int sortOrder = 1;
		Date now = getSysdate();
		for (FInvestigationCreditSchemeProcessControlOrder order : orders) {
			if (order.isNull()) {
				continue;
			}
			
			order.setType(type.code());
			order.setSortOrder(sortOrder++);
			order.setFormId(formId);
			FInvestigationCreditSchemeProcessControlDO doObj = map.get(order.getId());
			if (null == doObj) {
				doObj = new FInvestigationCreditSchemeProcessControlDO();
				BeanCopier.staticCopy(order, doObj);
				doObj.setRawAddTime(now);
				FInvestigationCreditSchemeProcessControlDAO.insert(doObj);
			} else {
				if (!isEqual(order, doObj)) {
					BeanCopier.staticCopy(order, doObj);
					FInvestigationCreditSchemeProcessControlDAO.update(doObj);
				}
			}
			map.remove(order.getId());
		}
		
		if (null != map && map.size() > 0) {
			for (long id : map.keySet()) {
				FInvestigationCreditSchemeProcessControlDAO.deleteById(id);
			}
		}
	}
	
	private boolean isEqual(FInvestigationCreditSchemeProcessControlOrder order,
							FInvestigationCreditSchemeProcessControlDO doObj) {
		return order.getSortOrder() == doObj.getSortOrder()
				&& StringUtil.equals(order.getUpRate(), doObj.getUpRate())
				&& StringUtil.equals(order.getUpBp(), doObj.getUpBp())
				&& StringUtil.equals(order.getDownRate(), doObj.getDownRate())
				&& StringUtil.equals(order.getDownBp(), doObj.getDownBp())
				&& StringUtil.equals(order.getContent(), doObj.getContent());
	}
	
	//增加保证人
	private void saveGuarantors(long schemeId,
								List<FInvestigationCreditSchemeGuarantorOrder> guarantors) {
		if (schemeId <= 0) {
			return;
		}
		
		if (ListUtil.isEmpty(guarantors)) {
			FInvestigationCreditSchemeGuarantorDAO.deleteBySchemeId(schemeId);
			return;
		}
		
		List<FInvestigationCreditSchemeGuarantorDO> items = FInvestigationCreditSchemeGuarantorDAO
			.findBySchemeId(schemeId);
		Map<Long, FInvestigationCreditSchemeGuarantorDO> map = new HashMap<>();
		if (ListUtil.isNotEmpty(items)) {
			for (FInvestigationCreditSchemeGuarantorDO doObj : items) {
				map.put(doObj.getId(), doObj);
			}
		}
		
		Date now = getSysdate();
		int sortOrder = 1;
		for (FInvestigationCreditSchemeGuarantorOrder order : guarantors) {
			if (!order.isNull()) {
				order.setSchemeId(schemeId);
				order.setSortOrder(sortOrder++);
				FInvestigationCreditSchemeGuarantorDO doObj = map.get(order.getId());
				if (null == doObj) {
					doObj = new FInvestigationCreditSchemeGuarantorDO();
					BeanCopier.staticCopy(order, doObj);
					doObj.setGuaranteeAmount(order.getGuaranteeAmount());
					doObj.setRawAddTime(now);
					FInvestigationCreditSchemeGuarantorDAO.insert(doObj);
				} else {
					if (!isEqual(order, doObj)) {
						BeanCopier.staticCopy(order, doObj);
						doObj.setGuaranteeAmount(order.getGuaranteeAmount());
						FInvestigationCreditSchemeGuarantorDAO.update(doObj);
					}
				}
				map.remove(order.getId());
			}
		}
		
		if (null != map && map.size() > 0) {
			for (long id : map.keySet()) {
				FInvestigationCreditSchemeGuarantorDAO.deleteById(id);
			}
		}
	}
	
	private boolean isEqual(FInvestigationCreditSchemeGuarantorOrder order,
							FInvestigationCreditSchemeGuarantorDO doObj) {
		return order.getSortOrder() == doObj.getSortOrder()
				&& StringUtil.equals(order.getGuarantorType(), doObj.getGuarantorType())
				&& StringUtil.equals(order.getGuarantor(), doObj.getGuarantor())
				&& StringUtil.equals(order.getGuaranteeWay(), doObj.getGuaranteeWay())
				&& order.getGuaranteeAmount().equals(doObj.getGuaranteeAmount());
	}
	
	@Override
	public FInvestigationCreditSchemeInfo findInvestigationCreditSchemeByFormId(long formId) {
		FInvestigationCreditSchemeInfo info = findInvestigationCreditScheme(FInvestigationCreditSchemeDAO
			.findByFormId(formId));
		
		queryCommon(info, formId + "", CommonAttachmentTypeEnum.COUNTER_GUARANTEE,
			CommonAttachmentTypeEnum.INVESTIGATION_REPORT);
		return info;
	}
	
	private FInvestigationCreditSchemeInfo findInvestigationCreditScheme(	FInvestigationCreditSchemeDO scheme) {
		if (null == scheme) {
			return null;
		}
		long schemeId = scheme.getSchemeId();
		FInvestigationCreditSchemeInfo schemeInfo = new FInvestigationCreditSchemeInfo();
		schemeInfo.setCurIndex(0);
		BeanCopier.staticCopy(scheme, schemeInfo);
		schemeInfo.setTimeUnit(TimeUnitEnum.getByCode(scheme.getTimeUnit()));
		schemeInfo.setChargePhase(ChargePhaseEnum.getByCode(scheme.getChargePhase()));
		schemeInfo.setChargeWay(ChargeWayEnum.getByCode(scheme.getChargeWay()));
		schemeInfo.setChargeType(ChargeTypeEnum.getByCode(scheme.getChargeType()));
		schemeInfo.setDepositType(ChargeTypeEnum.getByCode(scheme.getDepositType()));
		
		List<FInvestigationCreditSchemePledgeAssetInfo> pledges = new ArrayList<>();
		List<FInvestigationCreditSchemePledgeAssetInfo> mortgages = new ArrayList<>();
		List<FInvestigationCreditSchemePledgeAssetDO> list = FInvestigationCreditSchemePledgeAssetDAO
			.findByFormId(scheme.getFormId());
		Money pledgeAssessPrice = new Money(0L); //抵押评估价格
		Money pledgePrice = new Money(0L); //抵押价格
		Money mortgageAssessPrice = new Money(0L); //质押评估价格
		Money mortgagePrice = new Money(0L); //质押价格
		if (ListUtil.isNotEmpty(list)) {
			for (FInvestigationCreditSchemePledgeAssetDO doObj : list) {
				GuaranteeTypeEnum type = GuaranteeTypeEnum.getByCode(doObj.getType());
				FInvestigationCreditSchemePledgeAssetInfo info = new FInvestigationCreditSchemePledgeAssetInfo();
				BeanCopier.staticCopy(doObj, info);
				info.setType(type);
				
				if (type == GuaranteeTypeEnum.PLEDGE) {
					pledges.add(info);
					pledgeAssessPrice.addTo(info.getEvaluationPrice());
					pledgePrice.addTo(
						info.getEvaluationPrice().multiply(info.getPledgeRate() / 100))
						.subtractFrom(info.getDebtedAmount());
				} else if (type == GuaranteeTypeEnum.MORTGAGE) {
					mortgages.add(info);
					mortgageAssessPrice.addTo(info.getEvaluationPrice());
					mortgagePrice.addTo(
						info.getEvaluationPrice().multiply(info.getPledgeRate() / 100))
						.subtractFrom(info.getDebtedAmount());
				}
			}
		}
		
		schemeInfo.setPledges(pledges);
		schemeInfo.setMortgages(mortgages);
		schemeInfo.setPledgeAssessPrice(pledgeAssessPrice);
		schemeInfo.setPledgePrice(pledgePrice);
		schemeInfo.setMortgageAssessPrice(mortgageAssessPrice);
		schemeInfo.setMortgagePrice(mortgagePrice);
		
		List<FInvestigationCreditSchemeGuarantorInfo> guarantors = new ArrayList<>();
		List<FInvestigationCreditSchemeGuarantorDO> guarantorDOs = FInvestigationCreditSchemeGuarantorDAO
			.findBySchemeId(schemeId);
		Money guarantorAmount = new Money(0L);
		for (FInvestigationCreditSchemeGuarantorDO guarantor : guarantorDOs) {
			FInvestigationCreditSchemeGuarantorInfo guarantorInfo = new FInvestigationCreditSchemeGuarantorInfo();
			BeanCopier.staticCopy(guarantor, guarantorInfo);
			guarantorInfo
				.setGuarantorType(GuarantorTypeEnum.getByCode(guarantor.getGuarantorType()));
			guarantors.add(guarantorInfo);
			guarantorAmount.addTo(guarantor.getGuaranteeAmount());
		}
		schemeInfo.setGuarantors(guarantors);
		if (guarantorAmount.greaterThan(schemeInfo.getCreditAmount())) {
			guarantorAmount = new Money(schemeInfo.getCreditAmount().getAmount());
		}
		schemeInfo.setGuarantorAmount(guarantorAmount);
		
		//查询过程控制内容
		findProcesses(scheme.getFormId(), schemeInfo);
		//查询资金渠道列表
		queryChannels(schemeInfo);
		
		return schemeInfo;
	}
	
	private void queryChannels(FInvestigationCreditSchemeInfo info) {
		if (null == info) {
			return;
		}
		//		FInvestigationDO investigation = FInvestigationDAO.findByFormId(info.getFormId());
		//		if (null == investigation) {
		//			return;
		//		}
		
		List<ProjectChannelRelationInfo> capitalChannel = projectChannelRelationService
			.queryByBizNoAndType(info.getFormId() + "", ChannelRelationEnum.CAPITAL_CHANNEL);
		info.setCapitalChannel(capitalChannel);
		if (ListUtil.isNotEmpty(capitalChannel)) {
			ProjectChannelRelationInfo cc = capitalChannel.get(0);
			info.setCapitalChannelId(cc.getChannelId());
			info.setCapitalChannelCode(cc.getChannelCode());
			info.setCapitalChannelName(cc.getChannelName());
			info.setCapitalChannelType(cc.getChannelType());
			info.setCapitalSubChannelId(cc.getSubChannelId());
			info.setCapitalSubChannelCode(cc.getSubChannelCode());
			info.setCapitalSubChannelName(cc.getSubChannelName());
			info.setCapitalSubChannelType(cc.getSubChannelType());
		}
		
		List<ProjectChannelRelationInfo> projectChannel = projectChannelRelationService
			.queryByBizNoAndType(info.getFormId() + "", ChannelRelationEnum.PROJECT_CHANNEL);
		//		info.setProjectChannel(projectChannel);
		if (ListUtil.isNotEmpty(projectChannel)) {
			ProjectChannelRelationInfo pc = projectChannel.get(0);
			info.setProjectChannelId(pc.getChannelId());
			info.setProjectChannelCode(pc.getChannelCode());
			info.setProjectChannelName(pc.getChannelName());
			info.setProjectChannelType(pc.getChannelType());
			info.setProjectSubChannelId(pc.getSubChannelId());
			info.setProjectSubChannelCode(pc.getSubChannelCode());
			info.setProjectSubChannelName(pc.getSubChannelName());
			info.setProjectSubChannelType(pc.getSubChannelType());
		}
		
	}
	
	private void findProcesses(long formId, FInvestigationCreditSchemeInfo schemeInfo) {
		if (null != schemeInfo) {
			List<FInvestigationCreditSchemeProcessControlDO> items = FInvestigationCreditSchemeProcessControlDAO
				.findByFormId(formId);
			if (ListUtil.isNotEmpty(items)) {
				Map<ProcessControlEnum, List<FInvestigationCreditSchemeProcessControlInfo>> map = new HashMap<>();
				for (FInvestigationCreditSchemeProcessControlDO doObj : items) {
					ProcessControlEnum type = ProcessControlEnum.getByCode(doObj.getType());
					List<FInvestigationCreditSchemeProcessControlInfo> list = map.get(type);
					if (null == list) {
						list = new ArrayList<>();
						map.put(type, list);
					}
					
					FInvestigationCreditSchemeProcessControlInfo info = new FInvestigationCreditSchemeProcessControlInfo();
					BeanCopier.staticCopy(doObj, info);
					info.setType(type);
					list.add(info);
				}
				
				schemeInfo.setCustomerGrades(map.get(ProcessControlEnum.CUSTOMER_GRADE));
				schemeInfo.setDebtRatios(map.get(ProcessControlEnum.DEBT_RATIO));
				schemeInfo.setOthers(map.get(ProcessControlEnum.OTHER));
			}
		}
	}
	
	@Override
	public FormBaseResult saveInvestigationMainlyReview(final FInvestigationMainlyReviewOrder order) {
		return commonFormSaveProcess(order, "保存客户主体评价", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				copyForAudit(order); //必须最先执行
				checkProjectPause(order.getProjectCode());
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				FInvestigationDO investigation = FInvestigationDAO.findByFormId(form.getFormId());
				if (null == investigation) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "没有声明数据");
				}
				
				OwnerEnum owner = OwnerEnum.CUSTOMER;
				long reviewId = order.getMReviewId();
				FInvestigationMainlyReviewDO review = FInvestigationMainlyReviewDAO
					.findByFormIdAndOwner(order.getFormId(), owner.code());
				if (null == review) {
					review = new FInvestigationMainlyReviewDO();
					BeanCopier.staticCopy(order, review, UnBoxingConverter.getInstance());
					review.setFormId(form.getFormId());
					review.setOwner(owner.code());
					review.setRawAddTime(now);
					reviewId = FInvestigationMainlyReviewDAO.insert(review);
				} else {
					//					if (reviewId != review.getMReviewId()) {
					//						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
					//							"表单已经过期，请刷新页面重新操作");
					//					}
					BeanCopier.staticCopy(order, review, UnBoxingConverter.getInstance());
					review.setOwner(owner.code());
					FInvestigationMainlyReviewDAO.updateByFormIdAndOwner(review);
					//删除原来资质证书的数据
					//					FInvestigationMainlyReviewCertificateDAO.deleteByOwnerAndReviewId(owner.code(),
					//						reviewId);
					//删除原来的开户情况数据
					FInvestigationMainlyReviewBankInfoDAO.deleteByReviewId(reviewId);
					//删除原来的主要股东情况
					FInvestigationMainlyReviewStockholderDAO.deleteByReviewId(reviewId);
					//删除实际控制人或自然人股东个人资产、负债状况（非国有必填）
					FInvestigationMainlyReviewAssetAndLiabilityDAO.deleteByReviewId(reviewId);
					//删除客户下属公司、全资和控股子公司情况|主要参股公司情况|其它关联公司情况
					FInvestigationMainlyReviewRelatedCompanyDAO.deleteByReviewId(reviewId);
					//删除客户/个人信用
					FInvestigationMainlyReviewCreditStatusDAO.deleteByReviewId(reviewId);
					//删除客户/个人对外担保的情况
					FInvestigationMainlyReviewExternalGuaranteeDAO.deleteByReviewId(reviewId);
					//删除其他信用信息（工商、税务、诉讼、环保等信息）
					FInvestigationMainlyReviewCreditInfoDAO.deleteByReviewId(reviewId);
				}
				
				//保存资质证书
				saveCertificates(reviewId, owner, order.getCertificates());
				//保存开户情况
				saveBanks(reviewId, order.getBanks(), now);
				//保存主要股东情况
				saveHolders(reviewId, order.getHolders(), now);
				//保存实际控制人或自然人股东个人资产、负债状况（非国有必填）
				saveAssetAndLiabilities(reviewId, order.getAssetAndLiabilities(), now);
				//保存客户下属公司、全资和控股子公司情况|主要参股公司情况|其它关联公司情况
				saveAssetRelateCompanies(reviewId, CompanyTypeEnum.SUBSIDIARY,
					order.getSubsidiaries(), now);
				saveAssetRelateCompanies(reviewId, CompanyTypeEnum.PARTICIPATION,
					order.getParticipations(), now);
				saveAssetRelateCompanies(reviewId, CompanyTypeEnum.CORRELATION,
					order.getCorrelations(), now);
				//保存客户/个人信用
				saveCreditStatus(reviewId, CreditTypeEnum.CUSTOMER, order.getCustomerCredits(), now);
				saveCreditStatus(reviewId, CreditTypeEnum.PERSONAL, order.getPersonalCredits(), now);
				//保存客户/个人对外担保的情况
				saveGuarantees(reviewId, CreditTypeEnum.CUSTOMER, order.getCustomerGuarantees(),
					now);
				saveGuarantees(reviewId, CreditTypeEnum.PERSONAL, order.getPersonalGuarantees(),
					now);
				//保存其他信用信息（工商、税务、诉讼、环保等信息）
				saveSites(reviewId, order.getSiteStatus(), now);
				return null;
			}
		}, null, null);
	}
	
	//保存资质证书
	private void saveCertificates(long reviewId, OwnerEnum owner,
									List<FInvestigationMainlyReviewCertificateOrder> certificates) {
		if (reviewId <= 0 || null == owner) {
			return;
		}
		
		if (ListUtil.isEmpty(certificates)) {
			FInvestigationMainlyReviewCertificateDAO.deleteByOwnerAndReviewId(owner.code(),
				reviewId);
			return;
		}
		
		List<FInvestigationMainlyReviewCertificateDO> items = FInvestigationMainlyReviewCertificateDAO
			.findByOwnerAndReviewId(owner.code(), reviewId);
		Map<Long, FInvestigationMainlyReviewCertificateDO> map = new HashMap<>();
		if (ListUtil.isNotEmpty(items)) {
			for (FInvestigationMainlyReviewCertificateDO doObj : items) {
				map.put(doObj.getId(), doObj);
			}
		}
		
		int sortOrder = 1;
		Date now = getSysdate();
		for (FInvestigationMainlyReviewCertificateOrder order : certificates) {
			if (order.isNull()) {
				continue;
			}
			
			order.setMReviewId(reviewId);
			order.setSortOrder(sortOrder++);
			FInvestigationMainlyReviewCertificateDO doObj = map.get(order.getId());
			if (null == doObj) {
				doObj = new FInvestigationMainlyReviewCertificateDO();
				BeanCopier.staticCopy(order, doObj);
				doObj.setOwner(owner.code());
				doObj.setRawAddTime(now);
				FInvestigationMainlyReviewCertificateDAO.insert(doObj);
			} else {
				if (!isEqual(order, doObj)) {
					BeanCopier.staticCopy(order, doObj);
					doObj.setOwner(owner.code());
					FInvestigationMainlyReviewCertificateDAO.update(doObj);
				}
			}
			
			map.remove(order.getId());
		}
		
		if (null != map && map.size() > 0) {
			for (long id : map.keySet()) {
				FInvestigationMainlyReviewCertificateDAO.deleteById(id);
			}
		}
	}
	
	private boolean isEqual(FInvestigationMainlyReviewCertificateOrder order,
							FInvestigationMainlyReviewCertificateDO doObj) {
		return StringUtil.equals(order.getCertificateName(), doObj.getCertificateName())
				&& StringUtil.equals(order.getCertificateCode(), doObj.getCertificateCode())
				&& isEqual(order.getValidDate(), doObj.getValidDate())
				&& StringUtil.equals(order.getCertificateUrl(), doObj.getCertificateUrl())
				&& order.getSortOrder() == doObj.getSortOrder();
	}
	
	//保存开启行信息
	private void saveBanks(long reviewId, List<FInvestigationMainlyReviewBankInfoOrder> banks,
							Date now) {
		if (ListUtil.isEmpty(banks) || reviewId <= 0) {
			return;
		}
		
		for (FInvestigationMainlyReviewBankInfoOrder order : banks) {
			if (!order.isNull()) {
				FInvestigationMainlyReviewBankInfoDO bank = new FInvestigationMainlyReviewBankInfoDO();
				BeanCopier.staticCopy(order, bank);
				bank.setMReviewId(reviewId);
				bank.setRawAddTime(now);
				FInvestigationMainlyReviewBankInfoDAO.insert(bank);
			}
		}
	}
	
	private void saveBanks(long reviewId, List<FInvestigationMainlyReviewBankInfoOrder> banks) {
		if (reviewId <= 0) {
			return;
		}
		
		if (ListUtil.isEmpty(banks)) {
			FInvestigationMainlyReviewBankInfoDAO.deleteByReviewId(reviewId);
			return;
		}
		
		List<FInvestigationMainlyReviewBankInfoDO> items = FInvestigationMainlyReviewBankInfoDAO
			.findByReviewId(reviewId);
		Map<Integer, FInvestigationMainlyReviewBankInfoDO> map = new HashMap<>();
		if (ListUtil.isNotEmpty(items)) {
			int i = 1;
			for (FInvestigationMainlyReviewBankInfoDO item : items) {
				map.put(i++, item);
			}
		}
		
		int i = 0;
		Date now = getSysdate();
		for (FInvestigationMainlyReviewBankInfoOrder order : banks) {
			i++;
			if (null == order || order.isNull()) {
				continue;
			}
			FInvestigationMainlyReviewBankInfoDO doObj = map.get(i);
			if (null == doObj) {
				doObj = new FInvestigationMainlyReviewBankInfoDO();
				BeanCopier.staticCopy(order, doObj);
				doObj.setMReviewId(reviewId);
				doObj.setRawAddTime(now);
				FInvestigationMainlyReviewBankInfoDAO.insert(doObj);
			} else {
				if (!isEqual(order, doObj)) {
					doObj.setAccountNo(order.getAccountNo());
					doObj.setBankName(order.getBankName());
					FInvestigationMainlyReviewBankInfoDAO.update(doObj);
				}
			}
			
		}
	}
	
	private boolean isEqual(FInvestigationMainlyReviewBankInfoOrder order,
							FInvestigationMainlyReviewBankInfoDO doObj) {
		return isEqual(order.getAccountNo(), doObj.getAccountNo())
				&& isEqual(order.getBankName(), doObj.getBankName());
	}
	
	//保存主要股东情况
	@SuppressWarnings("deprecation")
	private void saveHolders(long reviewId,
								List<FInvestigationMainlyReviewStockholderOrder> holders, Date now) {
		if (ListUtil.isEmpty(holders) || reviewId <= 0) {
			return;
		}
		
		for (FInvestigationMainlyReviewStockholderOrder order : holders) {
			if (!order.isNull()) {
				FInvestigationMainlyReviewStockholderDO holder = new FInvestigationMainlyReviewStockholderDO();
				BeanCopier.staticCopy(order, holder, UnBoxingConverter.getInstance());
				holder.setMReviewId(reviewId);
				holder.setHolderType(order.getHolderTypeStr());
				holder.setActualInvestment(order.getActualInvestment());
				holder.setCapitalScale(order.getCapitalScale());
				holder.setProduceScale(order.getProduceScale());
				holder.setIncomeScale(order.getIncomeScale());
				holder.setRawAddTime(now);
				FInvestigationMainlyReviewStockholderDAO.insert(holder);
			}
		}
	}
	
	//保存实际控制人或自然人股东个人资产、负债状况（非国有必填）
	@SuppressWarnings("deprecation")
	private void saveAssetAndLiabilities(	long reviewId,
											List<FInvestigationMainlyReviewAssetAndLiabilityOrder> assetAndLiabilities,
											Date now) {
		if (ListUtil.isEmpty(assetAndLiabilities) || reviewId <= 0) {
			return;
		}
		
		for (FInvestigationMainlyReviewAssetAndLiabilityOrder order : assetAndLiabilities) {
			if (!order.isNull()) {
				FInvestigationMainlyReviewAssetAndLiabilityDO doObj = new FInvestigationMainlyReviewAssetAndLiabilityDO();
				BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
				doObj.setMReviewId(reviewId);
				doObj.setInvestAmount(order.getInvestAmount());
				doObj.setDepositAmount(order.getDepositAmount());
				doObj.setBankLoanAmount(order.getBankLoanAmount());
				doObj.setFolkLoanAmount(order.getFolkLoanAmount());
				doObj.setConsumerLoanAmount(order.getConsumerLoanAmount());
				doObj.setBusinesLoanAmount(order.getBusinesLoanAmount());
				doObj.setMortgageLoanAmount(order.getMortgageLoanAmount());
				doObj.setRawAddTime(now);
				FInvestigationMainlyReviewAssetAndLiabilityDAO.insert(doObj);
			}
		}
	}
	
	//保存客户下属公司、全资和控股子公司情况|主要参股公司情况|其它关联公司情况
	@SuppressWarnings("deprecation")
	private void saveAssetRelateCompanies(	long reviewId,
											CompanyTypeEnum relation,
											List<FInvestigationMainlyReviewRelatedCompanyOrder> assetAndLiabilities,
											Date now) {
		if (ListUtil.isEmpty(assetAndLiabilities) || null == relation || reviewId <= 0) {
			return;
		}
		
		for (FInvestigationMainlyReviewRelatedCompanyOrder order : assetAndLiabilities) {
			if (!order.isNull()) {
				FInvestigationMainlyReviewRelatedCompanyDO doObj = new FInvestigationMainlyReviewRelatedCompanyDO();
				BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
				doObj.setMReviewId(reviewId);
				doObj.setRelation(relation.code());
				doObj.setRegisterCapital(order.getRegisterCapital());
				doObj.setNetProfitThisYear(order.getNetProfitThisYear());
				doObj.setNetProfitLastYear(order.getNetProfitLastYear());
				doObj.setRawAddTime(now);
				FInvestigationMainlyReviewRelatedCompanyDAO.insert(doObj);
			}
		}
	}
	
	//保存客户/个人信用
	@SuppressWarnings("deprecation")
	private void saveCreditStatus(long reviewId, CreditTypeEnum type,
									List<FInvestigationMainlyReviewCreditStatusOrder> creditStatus,
									Date now) {
		if (ListUtil.isEmpty(creditStatus) || null == type || reviewId <= 0) {
			return;
		}
		
		int sortOrder = 1;
		for (FInvestigationMainlyReviewCreditStatusOrder order : creditStatus) {
			if (!order.isNull()) {
				FInvestigationMainlyReviewCreditStatusDO doObj = new FInvestigationMainlyReviewCreditStatusDO();
				BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
				doObj.setMReviewId(reviewId);
				doObj.setType(type.code());
				doObj.setLoanBalance(order.getLoanBalance());
				doObj.setRawAddTime(now);
				doObj.setSortOrder(sortOrder++);
				FInvestigationMainlyReviewCreditStatusDAO.insert(doObj);
			}
		}
	}
	
	//保存客户/个人对外担保的情况
	private void saveGuarantees(long reviewId, CreditTypeEnum type,
								List<FInvestigationMainlyReviewExternalGuaranteeOrder> guarantees,
								Date now) {
		if (ListUtil.isEmpty(guarantees) || null == type || reviewId <= 0) {
			return;
		}
		
		for (FInvestigationMainlyReviewExternalGuaranteeOrder order : guarantees) {
			if (!order.isNull()) {
				FInvestigationMainlyReviewExternalGuaranteeDO doObj = new FInvestigationMainlyReviewExternalGuaranteeDO();
				BeanCopier.staticCopy(order, doObj);
				doObj.setMReviewId(reviewId);
				doObj.setType(type.code());
				doObj.setAmount(order.getAmount());
				doObj.setRawAddTime(now);
				FInvestigationMainlyReviewExternalGuaranteeDAO.insert(doObj);
			}
		}
	}
	
	//保存其他信用信息（工商、税务、诉讼、环保等信息）
	private void saveSites(long reviewId, List<FInvestigationMainlyReviewCreditInfoOrder> sites,
							Date now) {
		if (ListUtil.isEmpty(sites) || reviewId <= 0) {
			return;
		}
		
		int sortOrder = 1;
		for (FInvestigationMainlyReviewCreditInfoOrder order : sites) {
			if (!order.isNull()) {
				FInvestigationMainlyReviewCreditInfoDO doObj = new FInvestigationMainlyReviewCreditInfoDO();
				BeanCopier.staticCopy(order, doObj);
				doObj.setStatus(order.getStatusStr());
				doObj.setMReviewId(reviewId);
				doObj.setRawAddTime(now);
				doObj.setSortOrder(sortOrder++);
				FInvestigationMainlyReviewCreditInfoDAO.insert(doObj);
			}
		}
	}
	
	@Override
	public FInvestigationMainlyReviewInfo findInvestigationMainlyReviewByFormId(long formId) {
		OwnerEnum customer = OwnerEnum.CUSTOMER;
		FInvestigationMainlyReviewDO review = FInvestigationMainlyReviewDAO.findByFormIdAndOwner(
			formId, customer.code());
		if (null == review) {
			return null;
		}
		
		long reviewId = review.getMReviewId();
		FInvestigationMainlyReviewInfo reviewInfo = new FInvestigationMainlyReviewInfo();
		reviewInfo.setCurIndex(1);
		BeanCopier.staticCopy(review, reviewInfo);
		reviewInfo.setEnterpriseType(EnterpriseNatureEnum.getByCode(review.getEnterpriseType()));
		reviewInfo.setIsOneCert(BooleanEnum.getByCode(review.getIsOneCert()));
		//已获得的资质证书
		List<FInvestigationMainlyReviewCertificateInfo> certificates = new ArrayList<>();
		List<FInvestigationMainlyReviewCertificateDO> certificateDOs = FInvestigationMainlyReviewCertificateDAO
			.findByOwnerAndReviewId(customer.code(), reviewId);
		for (FInvestigationMainlyReviewCertificateDO certificate : certificateDOs) {
			FInvestigationMainlyReviewCertificateInfo certificateInfo = new FInvestigationMainlyReviewCertificateInfo();
			BeanCopier.staticCopy(certificate, certificateInfo);
			certificates.add(certificateInfo);
		}
		reviewInfo.setCertificates(certificates);
		//开户情况
		List<FInvestigationMainlyReviewBankInfo> banks = new ArrayList<>();
		List<FInvestigationMainlyReviewBankInfoDO> bankDOs = FInvestigationMainlyReviewBankInfoDAO
			.findByReviewId(reviewId);
		for (FInvestigationMainlyReviewBankInfoDO bank : bankDOs) {
			FInvestigationMainlyReviewBankInfo bankInfo = new FInvestigationMainlyReviewBankInfo();
			BeanCopier.staticCopy(bank, bankInfo);
			banks.add(bankInfo);
		}
		reviewInfo.setBanks(banks);
		//主要股东情况
		List<FInvestigationMainlyReviewStockholderInfo> holders = new ArrayList<>();
		List<FInvestigationMainlyReviewStockholderDO> holderDOs = FInvestigationMainlyReviewStockholderDAO
			.findByReviewId(reviewId);
		for (FInvestigationMainlyReviewStockholderDO holder : holderDOs) {
			FInvestigationMainlyReviewStockholderInfo holderInfo = new FInvestigationMainlyReviewStockholderInfo();
			BeanCopier.staticCopy(holder, holderInfo);
			holderInfo.setHolderType(HolderTypeEnum.getByCode(holder.getHolderType()));
			holders.add(holderInfo);
		}
		reviewInfo.setHolders(holders);
		//实际控制人或自然人股东个人资产、负债状况（非国有必填）
		List<FInvestigationMainlyReviewAssetAndLiabilityInfo> assetAndLiabilities = new ArrayList<>();
		List<FInvestigationMainlyReviewAssetAndLiabilityDO> assetAndLiabilitiesDOs = FInvestigationMainlyReviewAssetAndLiabilityDAO
			.findByReviewId(reviewId);
		for (FInvestigationMainlyReviewAssetAndLiabilityDO assetAndLiability : assetAndLiabilitiesDOs) {
			FInvestigationMainlyReviewAssetAndLiabilityInfo assetAndLiabilityInfo = new FInvestigationMainlyReviewAssetAndLiabilityInfo();
			BeanCopier.staticCopy(assetAndLiability, assetAndLiabilityInfo);
			assetAndLiabilities.add(assetAndLiabilityInfo);
		}
		reviewInfo.setAssetAndLiabilities(assetAndLiabilities);
		//客户下属公司、全资和控股子公司情况
		List<FInvestigationMainlyReviewRelatedCompanyInfo> subsidiaries = new ArrayList<>();
		//客户主要参股公司情况
		List<FInvestigationMainlyReviewRelatedCompanyInfo> participations = new ArrayList<>();
		//客户其它关联公司情况
		List<FInvestigationMainlyReviewRelatedCompanyInfo> correlations = new ArrayList<>();
		List<FInvestigationMainlyReviewRelatedCompanyDO> companies = FInvestigationMainlyReviewRelatedCompanyDAO
			.findByReviewId(reviewId);
		for (FInvestigationMainlyReviewRelatedCompanyDO company : companies) {
			FInvestigationMainlyReviewRelatedCompanyInfo conpanyInfo = new FInvestigationMainlyReviewRelatedCompanyInfo();
			BeanCopier.staticCopy(company, conpanyInfo);
			conpanyInfo.setRelation(CompanyTypeEnum.getByCode(company.getRelation()));
			if (CompanyTypeEnum.SUBSIDIARY == conpanyInfo.getRelation()) {
				subsidiaries.add(conpanyInfo);
			} else if (CompanyTypeEnum.PARTICIPATION == conpanyInfo.getRelation()) {
				participations.add(conpanyInfo);
			} else if (CompanyTypeEnum.CORRELATION == conpanyInfo.getRelation()) {
				correlations.add(conpanyInfo);
			}
		}
		reviewInfo.setSubsidiaries(subsidiaries);
		reviewInfo.setParticipations(participations);
		reviewInfo.setCorrelations(correlations);
		//客户信用
		List<FInvestigationMainlyReviewCreditStatusInfo> customerCredits = new ArrayList<>();
		//个人信用
		List<FInvestigationMainlyReviewCreditStatusInfo> personalCredits = new ArrayList<>();
		List<FInvestigationMainlyReviewCreditStatusDO> creditStatusDOs = FInvestigationMainlyReviewCreditStatusDAO
			.findByReviewId(reviewId);
		for (FInvestigationMainlyReviewCreditStatusDO creditStatus : creditStatusDOs) {
			FInvestigationMainlyReviewCreditStatusInfo creditStatusInfo = new FInvestigationMainlyReviewCreditStatusInfo();
			BeanCopier.staticCopy(creditStatus, creditStatusInfo);
			creditStatusInfo.setType(CreditTypeEnum.getByCode(creditStatus.getType()));
			if (CreditTypeEnum.CUSTOMER == creditStatusInfo.getType()) {
				customerCredits.add(creditStatusInfo);
			} else if (CreditTypeEnum.PERSONAL == creditStatusInfo.getType()) {
				personalCredits.add(creditStatusInfo);
			}
		}
		reviewInfo.setCustomerCredits(customerCredits);
		reviewInfo.setPersonalCredits(personalCredits);
		//客户对外担保的情况
		List<FInvestigationMainlyReviewExternalGuaranteeInfo> customerGuarantees = new ArrayList<>();
		//个人对外担保的情况
		List<FInvestigationMainlyReviewExternalGuaranteeInfo> personalGuarantees = new ArrayList<>();
		List<FInvestigationMainlyReviewExternalGuaranteeDO> guaranteeDOs = FInvestigationMainlyReviewExternalGuaranteeDAO
			.findByReviewId(reviewId);
		for (FInvestigationMainlyReviewExternalGuaranteeDO guarantee : guaranteeDOs) {
			FInvestigationMainlyReviewExternalGuaranteeInfo guaranteeInfo = new FInvestigationMainlyReviewExternalGuaranteeInfo();
			BeanCopier.staticCopy(guarantee, guaranteeInfo);
			guaranteeInfo.setType(CreditTypeEnum.getByCode(guarantee.getType()));
			if (CreditTypeEnum.CUSTOMER == guaranteeInfo.getType()) {
				customerGuarantees.add(guaranteeInfo);
			} else if (CreditTypeEnum.PERSONAL == guaranteeInfo.getType()) {
				personalGuarantees.add(guaranteeInfo);
			}
		}
		reviewInfo.setCustomerGuarantees(customerGuarantees);
		reviewInfo.setPersonalGuarantees(personalGuarantees);
		//其他信用信息（工商、税务、诉讼、环保等信息）
		List<FInvestigationMainlyReviewCreditInfo> siteStatus = new ArrayList<>();
		List<FInvestigationMainlyReviewCreditInfoDO> creditDOs = FInvestigationMainlyReviewCreditInfoDAO
			.findByReviewId(reviewId);
		for (FInvestigationMainlyReviewCreditInfoDO credit : creditDOs) {
			FInvestigationMainlyReviewCreditInfo creditInfo = new FInvestigationMainlyReviewCreditInfo();
			BeanCopier.staticCopy(credit, creditInfo);
			creditInfo.setStatus(SiteStatusEnum.getByCode(credit.getStatus()));
			siteStatus.add(creditInfo);
		}
		reviewInfo.setSiteStatus(siteStatus);
		
		queryCommon(reviewInfo, formId + "");
		return reviewInfo;
	}
	
	@Override
	public FormBaseResult saveFInvestigationMabilityReview(	final FInvestigationMabilityReviewOrder order) {
		return commonFormSaveProcess(order, "保存客户管理能力评价", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				copyForAudit(order); //必须最先执行
				checkProjectPause(order.getProjectCode());
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				FInvestigationDO investigation = FInvestigationDAO.findByFormId(form.getFormId());
				if (null == investigation) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "没有声明数据");
				}
				
				OwnerEnum owner = OwnerEnum.CUSTOMER;
				long maReviewId = order.getMaReviewId();
				FInvestigationMabilityReviewDO review = FInvestigationMabilityReviewDAO
					.findByFormId(order.getFormId());
				if (null == review) {
					review = new FInvestigationMabilityReviewDO();
					BeanCopier.staticCopy(order, review);
					review.setFormId(form.getFormId());
					review.setRawAddTime(now);
					maReviewId = FInvestigationMabilityReviewDAO.insert(review);
				} else {
					//					if (maReviewId != review.getMaReviewId()) {
					//						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
					//							"表单已经过期，请刷新页面重新操作");
					//					}
					BeanCopier.staticCopy(order, review);
					FInvestigationMabilityReviewDAO.updateByFormId(review);
					//删除客户主要高管人员
					//					FInvestigationMabilityReviewLeadingTeamDAO.deleteByOwnerAndReviewId(
					//						owner.code(), maReviewId);
				}
				
				//保存客户主要高管人员
				saveLeadingTeams(maReviewId, owner, order.getLeadingTeams());
				return null;
			}
		}, null, null);
	}
	
	//保存客户主要高管人员
	@SuppressWarnings("deprecation")
	private void saveLeadingTeams(long maReviewId, OwnerEnum ownerEnum,
									List<FInvestigationMabilityReviewLeadingTeamOrder> leadingTeams) {
		if (maReviewId <= 0 || null == ownerEnum) {
			return;
		}
		
		String owner = ownerEnum.code();
		if (ListUtil.isEmpty(leadingTeams)) {
			FInvestigationMabilityReviewLeadingTeamDAO.deleteByOwnerAndReviewId(owner, maReviewId);
			FInvestigationMabilityReviewLeadingTeamResumeDAO.deleteByReviewIdAndOwner(maReviewId,
				owner);
			return;
		}
		
		Map<Long, FInvestigationMabilityReviewLeadingTeamDO> map1 = new HashMap<>();
		Map<Long, FInvestigationMabilityReviewLeadingTeamResumeDO> map2 = new HashMap<>();
		List<FInvestigationMabilityReviewLeadingTeamDO> leaders = FInvestigationMabilityReviewLeadingTeamDAO
			.findByOwnerAndReviewId(owner, maReviewId);
		if (ListUtil.isNotEmpty(leaders)) {
			for (FInvestigationMabilityReviewLeadingTeamDO leader : leaders) {
				map1.put(leader.getId(), leader);
			}
			
			List<FInvestigationMabilityReviewLeadingTeamResumeDO> resumes = FInvestigationMabilityReviewLeadingTeamResumeDAO
				.findByReviewIdAndOwner(maReviewId, owner);
			if (ListUtil.isNotEmpty(resumes)) {
				for (FInvestigationMabilityReviewLeadingTeamResumeDO resume : resumes) {
					map2.put(resume.getId(), resume);
				}
			}
		}
		
		Date now = getSysdate();
		int sortOrder1 = 1;
		for (FInvestigationMabilityReviewLeadingTeamOrder order : leadingTeams) {
			if (order.isNull()) {
				continue;
			}
			
			order.setSortOrder(sortOrder1++);
			order.setMaReviewId(maReviewId);
			FInvestigationMabilityReviewLeadingTeamDO leading = map1.get(order.getId());
			if (null == leading) {
				leading = new FInvestigationMabilityReviewLeadingTeamDO();
				BeanCopier.staticCopy(order, leading, UnBoxingConverter.getInstance());
				leading.setOwner(owner);
				leading.setRawAddTime(now);
				long tid = FInvestigationMabilityReviewLeadingTeamDAO.insert(leading);
				
				if (ListUtil.isNotEmpty(order.getResumes())) {
					int sortOrder2 = 1;
					for (FInvestigationMabilityReviewLeadingTeamResumeOrder resume : order
						.getResumes()) {
						if (resume.isNull()) {
							continue;
						}
						
						resume.setSortOrder(sortOrder2++);
						resume.setMaReviewId(maReviewId);
						resume.setTid(tid);
						
						FInvestigationMabilityReviewLeadingTeamResumeDO resumeDO = new FInvestigationMabilityReviewLeadingTeamResumeDO();
						BeanCopier.staticCopy(resume, resumeDO);
						resumeDO.setOwner(owner);
						resumeDO.setRawAddTime(now);
						FInvestigationMabilityReviewLeadingTeamResumeDAO.insert(resumeDO);
					}
				}
			} else {
				if (!isEqual(order, leading)) {
					BeanCopier.staticCopy(order, leading, UnBoxingConverter.getInstance());
					leading.setOwner(owner);
					FInvestigationMabilityReviewLeadingTeamDAO.update(leading);
				}
				if (ListUtil.isNotEmpty(order.getResumes())) {
					long tid = order.getId();
					int sortOrder2 = 1;
					for (FInvestigationMabilityReviewLeadingTeamResumeOrder resume : order
						.getResumes()) {
						if (resume.isNull()) {
							continue;
						}
						
						resume.setMaReviewId(maReviewId);
						resume.setTid(tid);
						resume.setSortOrder(sortOrder2++);
						FInvestigationMabilityReviewLeadingTeamResumeDO resumeDO = map2.get(resume
							.getId());
						if (null == resumeDO) {
							resumeDO = new FInvestigationMabilityReviewLeadingTeamResumeDO();
							BeanCopier.staticCopy(resume, resumeDO);
							resumeDO.setOwner(owner);
							resumeDO.setRawAddTime(now);
							FInvestigationMabilityReviewLeadingTeamResumeDAO.insert(resumeDO);
						} else {
							if (!isEqual(resume, resumeDO)) {
								BeanCopier.staticCopy(resume, resumeDO);
								resumeDO.setOwner(owner);
								FInvestigationMabilityReviewLeadingTeamResumeDAO.update(resumeDO);
							}
						}
						map2.remove(resume.getId());
					}
				}
			}
			map1.remove(order.getId());
		}
		if (null != map1 && map1.size() > 0) {
			for (long id : map1.keySet()) {
				FInvestigationMabilityReviewLeadingTeamDAO.deleteById(id);
			}
		}
		if (null != map2 && map2.size() > 0) {
			for (long id : map2.keySet()) {
				FInvestigationMabilityReviewLeadingTeamResumeDAO.deleteById(id);
			}
		}
	}
	
	private boolean isEqual(FInvestigationMabilityReviewLeadingTeamOrder order,
							FInvestigationMabilityReviewLeadingTeamDO doObj) {
		return order.getSortOrder() == doObj.getSortOrder()
				&& isEqual(order.getName(), doObj.getName())
				&& isEqual(order.getSex(), doObj.getSex()) && order.getAge() == doObj.getAge()
				&& isEqual(order.getBirth(), doObj.getBirth())
				&& isEqual(order.getDegree(), doObj.getDegree());
	}
	
	private boolean isEqual(FInvestigationMabilityReviewLeadingTeamResumeOrder order,
							FInvestigationMabilityReviewLeadingTeamResumeDO doObj) {
		return order.getSortOrder() == doObj.getSortOrder()
				&& isEqual(order.getStartDate(), doObj.getStartDate())
				&& isEqual(order.getEndDate(), doObj.getEndDate())
				&& isEqual(order.getCompanyName(), doObj.getCompanyName())
				&& isEqual(order.getTitle(), doObj.getTitle());
	}
	
	@Override
	public FInvestigationMabilityReviewInfo findFInvestigationMabilityReviewByFormId(long formId) {
		FInvestigationMabilityReviewDO review = FInvestigationMabilityReviewDAO
			.findByFormId(formId);
		if (null == review) {
			return null;
		}
		
		FInvestigationMabilityReviewInfo reviewInfo = new FInvestigationMabilityReviewInfo();
		reviewInfo.setCurIndex(2);
		BeanCopier.staticCopy(review, reviewInfo);
		
		List<FInvestigationMabilityReviewLeadingTeamInfo> leadingTeams = queryLeadings(
			review.getMaReviewId(), OwnerEnum.CUSTOMER);
		reviewInfo.setLeadingTeams(leadingTeams);
		
		queryCommon(reviewInfo, formId + "");
		return reviewInfo;
	}
	
	private List<FInvestigationMabilityReviewLeadingTeamInfo> queryLeadings(long maReviewId,
																			OwnerEnum owner) {
		if (null == owner || maReviewId <= 0) {
			return null;
		}
		
		List<FInvestigationMabilityReviewLeadingTeamDO> leadingTeamDOs = FInvestigationMabilityReviewLeadingTeamDAO
			.findByOwnerAndReviewId(owner.code(), maReviewId);
		if (ListUtil.isEmpty(leadingTeamDOs)) {
			return null;
		}
		
		List<FInvestigationMabilityReviewLeadingTeamResumeDO> resumes = FInvestigationMabilityReviewLeadingTeamResumeDAO
			.findByReviewIdAndOwner(maReviewId, owner.code());
		Map<Long, List<FInvestigationMabilityReviewLeadingTeamResumeInfo>> map = new HashMap<>();
		if (ListUtil.isNotEmpty(resumes)) {
			for (FInvestigationMabilityReviewLeadingTeamResumeDO resume : resumes) {
				FInvestigationMabilityReviewLeadingTeamResumeInfo resumeInfo = new FInvestigationMabilityReviewLeadingTeamResumeInfo();
				BeanCopier.staticCopy(resume, resumeInfo);
				List<FInvestigationMabilityReviewLeadingTeamResumeInfo> list = map.get(resume
					.getTid());
				if (null == list) {
					list = new ArrayList<>();
					map.put(resume.getTid(), list);
				}
				list.add(resumeInfo);
			}
		}
		
		List<FInvestigationMabilityReviewLeadingTeamInfo> infos = new ArrayList<>();
		for (FInvestigationMabilityReviewLeadingTeamDO leading : leadingTeamDOs) {
			FInvestigationMabilityReviewLeadingTeamInfo info = new FInvestigationMabilityReviewLeadingTeamInfo();
			BeanCopier.staticCopy(leading, info);
			info.setResumes(map.get(leading.getId()));
			infos.add(info);
		}
		
		return infos;
	}
	
	@Override
	public FormBaseResult saveFInvestigationOpabilityReview(final FInvestigationOpabilityReviewOrder order) {
		return commonFormSaveProcess(order, "保存客户运营能力评价", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				copyForAudit(order); //必须最先执行
				checkProjectPause(order.getProjectCode());
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				FInvestigationDO investigation = FInvestigationDAO.findByFormId(form.getFormId());
				if (null == investigation) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "没有声明数据");
				}
				
				long opReviewId = order.getOpReviewId();
				FInvestigationOpabilityReviewDO review = FInvestigationOpabilityReviewDAO
					.findByFormId(order.getFormId());
				
				if (null == review) {
					review = new FInvestigationOpabilityReviewDO();
					BeanCopier.staticCopy(order, review);
					review.setFormId(form.getFormId());
					review.setRawAddTime(now);
					opReviewId = FInvestigationOpabilityReviewDAO.insert(review);
				} else {
					//					if (opReviewId != review.getOpReviewId()) {
					//						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
					//							"表单已经过期，请刷新页面重新操作");
					//					}
					BeanCopier.staticCopy(order, review);
					FInvestigationOpabilityReviewDAO.updateByFormId(review);
					//删除原来的数据
					FInvestigationOpabilityReviewUpdownStreamDAO.deleteByReviewId(opReviewId);
					FInvestigationOpabilityReviewProductStructureDAO.deleteByReviewId(opReviewId);
				}
				//保存客户主要上下游情况（余额前五大）（城投类客户根据实际情况可不填写）
				saveUpdownStreams(opReviewId, UpAndDownEnum.UP, order.getUpStreams(), now);
				saveUpdownStreams(opReviewId, UpAndDownEnum.DOWN, order.getDownStreams(), now);
				//保存保存客户主要产品结构、下游主要客户情况（城投类客户根据实际情况可不填写）
				saveProductStructures(opReviewId, order.getProductStructures(), now);
				return null;
			}
		}, null, null);
	}
	
	//保存客户主要上下游情况（余额前五大）（城投类客户根据实际情况可不填写）
	private void saveUpdownStreams(	long opReviewId,
									UpAndDownEnum upOrDown,
									List<FInvestigationOpabilityReviewUpdownStreamOrder> updownStreams,
									Date now) {
		if (ListUtil.isEmpty(updownStreams) || opReviewId <= 0) {
			return;
		}
		
		int sortOrder = 1;
		for (FInvestigationOpabilityReviewUpdownStreamOrder order : updownStreams) {
			if (order.isNull()) {
				continue;
			}
			FInvestigationOpabilityReviewUpdownStreamDO doObj = new FInvestigationOpabilityReviewUpdownStreamDO();
			BeanCopier.staticCopy(order, doObj);
			doObj.setUpOrDown(upOrDown.code());
			doObj.setOpReviewId(opReviewId);
			doObj.setEndingBalance(order.getEndingBalance());
			doObj.setSortOrder(sortOrder++);
			doObj.setRawAddTime(now);
			FInvestigationOpabilityReviewUpdownStreamDAO.insert(doObj);
		}
	}
	
	//保存客户主要产品结构、下游主要客户情况（城投类客户根据实际情况可不填写）
	@SuppressWarnings("deprecation")
	private void saveProductStructures(	long opReviewId,
										List<FInvestigationOpabilityReviewProductStructureOrder> productStructures,
										Date now) {
		if (ListUtil.isEmpty(productStructures) || opReviewId <= 0) {
			return;
		}
		
		for (FInvestigationOpabilityReviewProductStructureOrder order : productStructures) {
			FInvestigationOpabilityReviewProductStructureDO doObj = new FInvestigationOpabilityReviewProductStructureDO();
			BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
			doObj.setOpReviewId(opReviewId);
			doObj.setIncome1(order.getIncome1());
			doObj.setIncome2(order.getIncome2());
			doObj.setIncome3(order.getIncome3());
			doObj.setRawAddTime(now);
			FInvestigationOpabilityReviewProductStructureDAO.insert(doObj);
		}
	}
	
	@Override
	public FInvestigationOpabilityReviewInfo findFInvestigationOpabilityReviewByFormId(long formId) {
		FInvestigationOpabilityReviewDO review = FInvestigationOpabilityReviewDAO
			.findByFormId(formId);
		if (null == review) {
			return null;
		}
		
		long opReviewId = review.getOpReviewId();
		FInvestigationOpabilityReviewInfo reviewInfo = new FInvestigationOpabilityReviewInfo();
		reviewInfo.setCurIndex(3);
		BeanCopier.staticCopy(review, reviewInfo);
		
		//客户主要上下游情况（余额前五大）（城投类客户根据实际情况可不填写）
		List<FInvestigationOpabilityReviewUpdownStreamInfo> upStreams = new ArrayList<>();
		List<FInvestigationOpabilityReviewUpdownStreamInfo> downStreams = new ArrayList<>();
		List<FInvestigationOpabilityReviewUpdownStreamDO> updownStreamDOs = FInvestigationOpabilityReviewUpdownStreamDAO
			.findByReviewId(opReviewId);
		for (FInvestigationOpabilityReviewUpdownStreamDO doObj : updownStreamDOs) {
			FInvestigationOpabilityReviewUpdownStreamInfo updownStreamInfo = new FInvestigationOpabilityReviewUpdownStreamInfo();
			BeanCopier.staticCopy(doObj, updownStreamInfo);
			updownStreamInfo.setUpOrDown(UpAndDownEnum.getByCode(doObj.getUpOrDown()));
			if (updownStreamInfo.getUpOrDown() == UpAndDownEnum.UP) {
				upStreams.add(updownStreamInfo);
			} else {
				downStreams.add(updownStreamInfo);
			}
		}
		reviewInfo.setUpStreams(upStreams); //上游
		reviewInfo.setDownStreams(downStreams); //下游
		
		//客户主要产品结构、下游主要客户情况（城投类客户根据实际情况可不填写）
		List<FInvestigationOpabilityReviewProductStructureInfo> productStructures = new ArrayList<>();
		List<FInvestigationOpabilityReviewProductStructureDO> productStructureDOs = FInvestigationOpabilityReviewProductStructureDAO
			.findByReviewId(opReviewId);
		for (FInvestigationOpabilityReviewProductStructureDO doObj : productStructureDOs) {
			FInvestigationOpabilityReviewProductStructureInfo productStructureInfo = new FInvestigationOpabilityReviewProductStructureInfo();
			BeanCopier.staticCopy(doObj, productStructureInfo);
			productStructures.add(productStructureInfo);
		}
		reviewInfo.setProductStructures(productStructures);
		
		queryCommon(reviewInfo, formId + "");
		return reviewInfo;
	}
	
	@Override
	public FormBaseResult saveFInvestigationMajorEvent(final FInvestigationMajorEventOrder order) {
		return commonFormSaveProcess(order, "保存重大事项调查", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				copyForAudit(order); //必须最先执行
				checkProjectPause(order.getProjectCode());
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				FInvestigationDO investigation = FInvestigationDAO.findByFormId(form.getFormId());
				if (null == investigation) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "没有声明数据");
				}
				
				FInvestigationMajorEventDO event = FInvestigationMajorEventDAO.findByFormId(order
					.getFormId());
				if (null == event) {
					event = new FInvestigationMajorEventDO();
					BeanCopier.staticCopy(order, event);
					event.setFormId(form.getFormId());
					event.setRawAddTime(now);
					FInvestigationMajorEventDAO.insert(event);
				} else {
					//					if (order.getId() != event.getId()) {
					//						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
					//							"表单已经过期，请刷新页面重新操作");
					//					}
					BeanCopier.staticCopy(order, event);
					FInvestigationMajorEventDAO.updateByFormId(event);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FInvestigationMajorEventInfo findFInvestigationMajorEventByFormId(long formId) {
		FInvestigationMajorEventDO event = FInvestigationMajorEventDAO.findByFormId(formId);
		if (null == event) {
			return null;
		}
		
		FInvestigationMajorEventInfo eventInfo = new FInvestigationMajorEventInfo();
		eventInfo.setCurIndex(5);
		BeanCopier.staticCopy(event, eventInfo);
		
		queryCommon(eventInfo, formId + "");
		return eventInfo;
	}
	
	@Override
	public FormBaseResult saveFInvestigationProjectStatus(	final FInvestigationProjectStatusOrder order) {
		return commonFormSaveProcess(order, "保存项目情况调查", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				copyForAudit(order); //必须最先执行
				checkProjectPause(order.getProjectCode());
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				FInvestigationDO investigation = FInvestigationDAO.findByFormId(form.getFormId());
				if (null == investigation) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "没有声明数据");
				}
				
				long statusId = order.getStatusId();
				FInvestigationProjectStatusDO status = FInvestigationProjectStatusDAO
					.findByFormId(order.getFormId());
				if (null == status) {
					status = new FInvestigationProjectStatusDO();
					BeanCopier.staticCopy(order, status);
					status.setTotalCost(order.getTotalCost());
					status.setFormId(form.getFormId());
					status.setRawAddTime(now);
					statusId = FInvestigationProjectStatusDAO.insert(status);
				} else {
					//					if (statusId != status.getStatusId()) {
					//						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
					//							"表单已经过期，请刷新页面重新操作");
					//					}
					BeanCopier.staticCopy(order, status);
					status.setTotalCost(order.getTotalCost());
					FInvestigationProjectStatusDAO.updateByFormId(status);
					//删除原来的数据
					FInvestigationProjectStatusFundDAO.deleteByStatusId(statusId);
				}
				//保存项目投资与资金筹措
				saveFunds(statusId, order.getFunds(), now);
				return null;
			}
		}, null, null);
	}
	
	//保存项目投资与资金筹措
	@SuppressWarnings("deprecation")
	private void saveFunds(long statusId, List<FInvestigationProjectStatusFundOrder> funds, Date now) {
		if (ListUtil.isEmpty(funds) || statusId <= 0) {
			return;
		}
		
		int sortOrder = 1;
		for (FInvestigationProjectStatusFundOrder order : funds) {
			FInvestigationProjectStatusFundDO doObj = new FInvestigationProjectStatusFundDO();
			BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
			doObj.setStatusId(statusId);
			doObj.setItemAmount(order.getItemAmount());
			doObj.setFundAmount(order.getFundAmount());
			doObj.setSortOrder(sortOrder++);
			doObj.setRawAddTime(now);
			FInvestigationProjectStatusFundDAO.insert(doObj);
		}
	}
	
	@Override
	public FInvestigationProjectStatusInfo findFInvestigationProjectStatusByFormId(long formId) {
		FInvestigationProjectStatusDO status = FInvestigationProjectStatusDAO.findByFormId(formId);
		if (null == status) {
			return null;
		}
		FInvestigationProjectStatusInfo statusInfo = new FInvestigationProjectStatusInfo();
		statusInfo.setCurIndex(6);
		BeanCopier.staticCopy(status, statusInfo);
		
		//项目投资与资金筹措
		List<FInvestigationProjectStatusFundInfo> funds = new ArrayList<>();
		List<FInvestigationProjectStatusFundDO> fundDOs = FInvestigationProjectStatusFundDAO
			.findByStatusId(status.getStatusId());
		for (FInvestigationProjectStatusFundDO doObj : fundDOs) {
			FInvestigationProjectStatusFundInfo fundInfo = new FInvestigationProjectStatusFundInfo();
			BeanCopier.staticCopy(doObj, fundInfo);
			funds.add(fundInfo);
		}
		statusInfo.setFunds(funds);
		
		queryCommon(statusInfo, formId + "", CommonAttachmentTypeEnum.INVESTIGATION_6);
		return statusInfo;
	}
	
	@Override
	public FormBaseResult saveFInvestigationCsRationalityReview(final FInvestigationCsRationalityReviewOrder order) {
		return commonFormSaveProcess(order, "保存授信方案主要事项合理性评价", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				copyForAudit(order); //必须最先执行
				checkProjectPause(order.getProjectCode());
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				FInvestigationDO investigation = FInvestigationDAO.findByFormId(form.getFormId());
				if (null == investigation) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "没有声明数据");
				}
				
				//				OwnerEnum owner = OwnerEnum.GUARANTOR;
				long csrReviewId = order.getCsrReviewId();
				FInvestigationCsRationalityReviewDO csReview = FInvestigationCsRationalityReviewDAO
					.findByFormId(order.getFormId());
				if (null == csReview) {
					csReview = new FInvestigationCsRationalityReviewDO();
					BeanCopier.staticCopy(order, csReview);
					csReview.setFormId(form.getFormId());
					csReview.setCustomerId(getLong(order.getCustomerId()));
					csReview.setRawAddTime(now);
					csrReviewId = FInvestigationCsRationalityReviewDAO.insert(csReview);
				} else {
					//					if (order.getCsrReviewId() != csReview.getCsrReviewId()) {
					//						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
					//							"表单已经过期，请刷新页面重新操作");
					//					}
					BeanCopier.staticCopy(order, csReview);
					FInvestigationCsRationalityReviewDAO.updateByFormId(csReview);
					//删除原来的资质证书
					//					FInvestigationMainlyReviewCertificateDAO.deleteByOwnerAndReviewId(owner.code(),
					//						csrReviewId);
					//删除原来的客户高管人员列表
					//					FInvestigationMabilityReviewLeadingTeamDAO.deleteByOwnerAndReviewId(
					//						owner.code(), csrReviewId);
					//删除原来的保证人主要财务指标
					//					FInvestigationCsRationalityReviewFinancialKpiDAO.deleteByReviewId(csrReviewId);
					//删除保证人保证能力总体评价（非必填）
					//					FInvestigationCsRationalityReviewGuarantorAbilityDAO
					//						.deleteByReviewId(csrReviewId);
				}
				//保存保证人 - 授信方案合理性评价
				saveCsGuarantors(form.getFormId(), csrReviewId, order.getGuarantors());
				//保证人合法性评价（此处不含担保公司评价）
				//				saveOrUpdateMainlyReview(order, owner, form.getFormId(), now);
				//保存已获得的资质证书
				//				saveCertificates(csrReviewId, owner, order.getCertificates());
				//保存客户高管人员列表
				//				saveLeadingTeams(csrReviewId, owner, order.getLeadingTeams());
				//保存保证人主要财务指标
				//				saveFinancialKpis(csrReviewId, order.getKpies(), now);
				//				saveKpis(csrReviewId, order.getHeader(), order.getKpies(), now);
				//保存保证人保证能力总体评价（非必填）
				saveGuarantorAbilities(csrReviewId, order.getGuarantorAbilities());
				return null;
			}
		}, null, null);
	}
	
	/**
	 * 数据迁移之后，历史数据就会删除
	 * 
	 */
	private void moveCsData() {
		taskExecutor.execute(new Runnable() {
			
			@Override
			public void run() {
				logger.info("开始处理 授信方案主要事项合理性评价 历史数据");
				List<FInvestigationCsRationalityReviewDO> list = FInvestigationCsRationalityReviewDAO
					.findAll();
				if (ListUtil.isNotEmpty(list)) {
					for (FInvestigationCsRationalityReviewDO cs : list) {
						try {
							moveCsData(cs);
						} catch (Exception e) {
							logger.info("处理 授信方案主要事项合理性评价 历史数据 异常： " + cs.getCsrReviewId());
						}
					}
					logger.info("处理 授信方案主要事项合理性评价 历史数据 完成： " + list.size());
				} else {
					logger.info("处理 授信方案主要事项合理性评价 历史数据完成，没有数据");
				}
				
			}
		});
	}
	
	private void moveCsData(FInvestigationCsRationalityReviewDO cs) {
		boolean noData = true;
		List<FInvestigationCsRationalityReviewGuarantorOrder> guarantors = new ArrayList<>();
		FInvestigationCsRationalityReviewGuarantorOrder order = new FInvestigationCsRationalityReviewGuarantorOrder();
		order.setGuarantorType("LEGAL");
		order.setGuarantorName("");
		long csrReviewId = cs.getCsrReviewId();
		OwnerEnum guarantor = OwnerEnum.GUARANTOR;
		FInvestigationMainlyReviewDO mainlyReview = FInvestigationMainlyReviewDAO
			.findByFormIdAndOwner(cs.getFormId(), guarantor.code());
		if (null != mainlyReview) {
			order.setEstablishedTime(DateUtil.dtSimpleFormat(mainlyReview.getEstablishedTime()));
			order.setOperatingTerm(mainlyReview.getOperatingTerm());
			order.setLegalPersion(mainlyReview.getLegalPersion());
			order.setOrgCode(mainlyReview.getOrgCode());
			order.setLivingAddress(mainlyReview.getLivingAddress());
			order.setActualControlPerson(mainlyReview.getActualControlPerson());
			order.setEnterpriseType(mainlyReview.getEnterpriseType());
			order.setWorkAddress(mainlyReview.getWorkAddress());
			order.setIsOneCert(mainlyReview.getIsOneCert());
			order.setBusiLicenseNo(mainlyReview.getBusiLicenseNo());
			order.setTaxCertificateNo(mainlyReview.getTaxCertificateNo());
			order.setLocalTaxNo(mainlyReview.getLocalTaxNo());
			order.setLoanCardNo(mainlyReview.getLoanCardNo());
			order.setLastCheckYear(mainlyReview.getLastCheckYear());
			order.setBusiScope(mainlyReview.getBusiScope());
			order.setLeaderReview(mainlyReview.getCustomerDevEvolution());
			FInvestigationMainlyReviewDAO.deleteById(mainlyReview.getMReviewId());
			noData = false;
		}
		//已获得的资质证书
		List<FInvestigationMainlyReviewCertificateDO> certificateDOs = FInvestigationMainlyReviewCertificateDAO
			.findByOwnerAndReviewId(guarantor.code(), csrReviewId);
		if (ListUtil.isNotEmpty(certificateDOs)) {
			List<FInvestigationMainlyReviewCertificateOrder> certificates = new ArrayList<>();
			for (FInvestigationMainlyReviewCertificateDO doObj : certificateDOs) {
				FInvestigationMainlyReviewCertificateOrder certificate = new FInvestigationMainlyReviewCertificateOrder();
				certificate.setCertificateName(doObj.getCertificateName());
				certificate.setCertificateCode(doObj.getCertificateCode());
				certificate.setValidDate(doObj.getValidDate());
				certificate.setCertificateUrl(doObj.getCertificateUrl());
				certificates.add(certificate);
				FInvestigationMainlyReviewCertificateDAO.deleteById(doObj.getId());
			}
			order.setCertificates(certificates);
			noData = false;
		}
		//客户高管人员列表
		//		List<FInvestigationMabilityReviewLeadingTeamInfo> leadingTeams = new ArrayList<>();
		List<FInvestigationMabilityReviewLeadingTeamDO> leadingTeamDOs = FInvestigationMabilityReviewLeadingTeamDAO
			.findByOwnerAndReviewId(guarantor.code(), csrReviewId);
		if (ListUtil.isNotEmpty(leadingTeamDOs)) {
			List<FInvestigationMabilityReviewLeadingTeamOrder> leadingTeams = new ArrayList<>();
			for (FInvestigationMabilityReviewLeadingTeamDO leadingTeamDoObj : leadingTeamDOs) {
				FInvestigationMabilityReviewLeadingTeamOrder order1 = new FInvestigationMabilityReviewLeadingTeamOrder();
				order1.setName(leadingTeamDoObj.getName());
				order1.setSex(leadingTeamDoObj.getSex());
				order1.setBirth(leadingTeamDoObj.getBirth());
				order1.setAge(leadingTeamDoObj.getAge());
				order1.setDegree(leadingTeamDoObj.getDegree());
				order1.setTitle(leadingTeamDoObj.getTitle());
				order1.setResume(leadingTeamDoObj.getResume());
				List<FInvestigationMabilityReviewLeadingTeamResumeDO> resumes = FInvestigationMabilityReviewLeadingTeamResumeDAO
					.findByReviewIdOwnerTid(csrReviewId, guarantor.code(), leadingTeamDoObj.getId());
				if (ListUtil.isNotEmpty(resumes)) {
					List<FInvestigationMabilityReviewLeadingTeamResumeOrder> resumeOrders = new ArrayList<>();
					for (FInvestigationMabilityReviewLeadingTeamResumeDO resume : resumes) {
						FInvestigationMabilityReviewLeadingTeamResumeOrder resumeOrder = new FInvestigationMabilityReviewLeadingTeamResumeOrder();
						resumeOrder.setStartDate(resume.getStartDate());
						resumeOrder.setEndDate(resume.getEndDate());
						resumeOrder.setCompanyName(resume.getCompanyName());
						resumeOrder.setTitle(resume.getTitle());
						resumeOrders.add(resumeOrder);
						FInvestigationMabilityReviewLeadingTeamResumeDAO.deleteById(resume.getId());
					}
					order1.setResumes(resumeOrders);
				}
				leadingTeams.add(order1);
				FInvestigationMabilityReviewLeadingTeamDAO.deleteById(leadingTeamDoObj.getId());
			}
			order.setLeadingTeams(leadingTeams);
			noData = false;
		}
		
		//保证人主要财务指标
		List<FInvestigationCsRationalityReviewFinancialKpiDO> kpiDOs = FInvestigationCsRationalityReviewFinancialKpiDAO
			.findByReviewId(csrReviewId);
		if (ListUtil.isNotEmpty(kpiDOs)) {
			List<FinancialKpiOrder> kpies = new ArrayList<>();
			for (int i = 0; i < kpiDOs.size(); i += 4) {
				FinancialKpiOrder kpi = new FinancialKpiOrder();
				kpi.setKpiName(kpiDOs.get(i).getKpiName());
				kpi.setKpiCode(kpiDOs.get(i).getKpiCode());
				kpi.setKpiValue1(kpiDOs.get(i).getKpiValue());
				kpi.setKpiValue2(kpiDOs.get(i + 1).getKpiValue());
				kpi.setKpiValue3(kpiDOs.get(i + 2).getKpiValue());
				kpi.setKpiValue4(kpiDOs.get(i + 3).getKpiValue());
				kpies.add(kpi);
			}
			order.setKpies(kpies);
			FInvestigationCsRationalityReviewFinancialKpiDAO.deleteByReviewId(csrReviewId);
			noData = false;
		}
		
		guarantors.add(order);
		if (!noData) { //有数据才进行更新
			saveCsGuarantors(cs.getFormId(), csrReviewId, guarantors);
		}
	}
	
	/** 保存保证人 - 授信方案合理性评价 */
	private void saveCsGuarantors(long formId, long csrReviewId,
									List<FInvestigationCsRationalityReviewGuarantorOrder> guarantors) {
		if (csrReviewId <= 0) {
			return;
		}
		
		OwnerEnum owner = OwnerEnum.GUARANTOR_NEW;
		if (ListUtil.isEmpty(guarantors)) {
			//删除原来的数据
			List<FInvestigationCsRationalityReviewGuarantorDO> items = FInvestigationCsRationalityReviewGuarantorDAO
				.findByFormId(formId);
			if (ListUtil.isNotEmpty(items)) {
				for (FInvestigationCsRationalityReviewGuarantorDO doObj : items) {
					//删除资质证书
					saveCertificates(doObj.getGuarantorId(), owner, null);
					//删除 主要高管人员
					saveLeadingTeams(doObj.getGuarantorId(), owner, null);
					//删除 保证人财务主要指标
					financialKpiService.save(doObj.getGuarantorId(), KpiTypeEnum.CS_LEGAL_PERSON,
						null);
				}
			}
			
			// 删除数据
			FInvestigationCsRationalityReviewGuarantorDAO.deleteByFormId(formId);
			return;
		}
		
		List<FInvestigationCsRationalityReviewGuarantorDO> items = FInvestigationCsRationalityReviewGuarantorDAO
			.findByFormId(formId);
		Map<Long, FInvestigationCsRationalityReviewGuarantorDO> map = new HashMap<>();
		if (ListUtil.isNotEmpty(items)) {
			for (FInvestigationCsRationalityReviewGuarantorDO doObj : items) {
				map.put(doObj.getGuarantorId(), doObj);
			}
		}
		
		// 保存数据
		int sortOrder = 1;
		Date now = getSysdate();
		for (FInvestigationCsRationalityReviewGuarantorOrder order : guarantors) {
			if (order.isNull()) {
				continue;
			}
			
			order.setFormId(formId);
			order.setSortOrder(sortOrder++);
			FInvestigationCsRationalityReviewGuarantorDO doObj = map.get(order.getGuarantorId());
			long guarantorId = order.getGuarantorId();
			if (null == doObj) {
				doObj = new FInvestigationCsRationalityReviewGuarantorDO();
				BeanCopier.staticCopy(order, doObj);
				doObj.setRawAddTime(now);
				doObj.setBankLoanAmount(order.getBankLoanAmount());
				doObj.setFolkLoanAmount(order.getFolkLoanAmount());
				doObj.setConsumerLoanAmount(order.getConsumerLoanAmount());
				doObj.setBusinesLoanAmount(order.getBusinesLoanAmount());
				doObj.setMortgageLoanAmount(order.getMortgageLoanAmount());
				guarantorId = FInvestigationCsRationalityReviewGuarantorDAO.insert(doObj);
			} else {
				guarantorId = doObj.getGuarantorId();
				if (!isEqual(order, doObj)) {
					BeanCopier.staticCopy(order, doObj);
					doObj.setBankLoanAmount(order.getBankLoanAmount());
					doObj.setFolkLoanAmount(order.getFolkLoanAmount());
					doObj.setConsumerLoanAmount(order.getConsumerLoanAmount());
					doObj.setBusinesLoanAmount(order.getBusinesLoanAmount());
					doObj.setMortgageLoanAmount(order.getMortgageLoanAmount());
					FInvestigationCsRationalityReviewGuarantorDAO.update(doObj);
				}
			}
			//保存资质证书
			saveCertificates(guarantorId, owner, order.getCertificates());
			//保存高管人员列表
			saveLeadingTeams(guarantorId, owner, order.getLeadingTeams());
			//保存保证人主要财务指标
			financialKpiService.save(guarantorId, KpiTypeEnum.CS_LEGAL_PERSON, order.getKpies());
			
			map.remove(order.getGuarantorId());
		}
		
		if (null != map && map.size() > 0) {
			for (long id : map.keySet()) {
				FInvestigationCsRationalityReviewGuarantorDAO.deleteById(id);
				//移除对应的所有其它数据
				//删除资质证书
				saveCertificates(id, owner, null);
				//删除 主要高管人员
				saveLeadingTeams(id, owner, null);
				//删除 保证人财务主要指标
				financialKpiService.save(id, KpiTypeEnum.CS_LEGAL_PERSON, null);
			}
		}
		
	}
	
	private long getLong(Long value) {
		return (null == value) ? 0L : value.longValue();
	}
	
	private double getDouble(Double value) {
		return (null == value) ? 0d : value.doubleValue();
	}
	
	private boolean isEqual(FInvestigationCsRationalityReviewGuarantorOrder order,
							FInvestigationCsRationalityReviewGuarantorDO doObj) {
		// 比较order和doObj
		return order.getSortOrder() == doObj.getSortOrder()
				&& isEqual(order.getGuarantorName(), doObj.getGuarantorName())
				&& isEqual(order.getEstablishedTime(), doObj.getEstablishedTime())
				&& isEqual(order.getOperatingTerm(), doObj.getOperatingTerm())
				&& isEqual(order.getLegalPersion(), doObj.getLegalPersion())
				&& isEqual(order.getOrgCode(), doObj.getOrgCode())
				&& isEqual(order.getLivingAddress(), doObj.getLivingAddress())
				&& isEqual(order.getActualControlPerson(), doObj.getActualControlPerson())
				&& isEqual(order.getEnterpriseType(), doObj.getEnterpriseType())
				&& isEqual(order.getWorkAddress(), doObj.getWorkAddress())
				&& isEqual(order.getIsOneCert(), doObj.getIsOneCert())
				&& isEqual(order.getBusiLicenseNo(), doObj.getBusiLicenseNo())
				&& isEqual(order.getTaxCertificateNo(), doObj.getTaxCertificateNo())
				&& isEqual(order.getLocalTaxNo(), doObj.getLocalTaxNo())
				&& isEqual(order.getLoanCardNo(), doObj.getLoanCardNo())
				&& isEqual(order.getLastCheckYear(), doObj.getLastCheckYear())
				&& isEqual(order.getBusiScope(), doObj.getBusiScope())
				&& isEqual(order.getLeaderReview(), doObj.getLeaderReview())
				&& isEqual(order.getEventDesc(), doObj.getEventDesc())
				&& isEqual(order.getGuarantorCertType(), doObj.getGuarantorCertType())
				&& isEqual(order.getGuarantorSex(), doObj.getGuarantorSex())
				&& isEqual(order.getGuarantorCertNo(), doObj.getGuarantorCertNo())
				&& isEqual(order.getGuarantorContactNo(), doObj.getGuarantorContactNo())
				&& isEqual(order.getGuarantorAddress(), doObj.getGuarantorAddress())
				&& isEqual(order.getMaritalStatus(), doObj.getMaritalStatus())
				&& isEqual(order.getSpouseName(), doObj.getSpouseName())
				&& isEqual(order.getSpouseCertType(), doObj.getSpouseCertType())
				&& isEqual(order.getSpouseCertNo(), doObj.getSpouseCertNo())
				&& isEqual(order.getSpouseContactNo(), doObj.getSpouseContactNo())
				&& isEqual(order.getName(), doObj.getName())
				&& isEqual(order.getSex(), doObj.getSex())
				&& isEqual(order.getCertNo(), doObj.getCertNo())
				&& isEqual(order.getHouseNum(), doObj.getHouseNum())
				&& isEqual(order.getCarNum(), doObj.getCarNum())
				&& isEqual(order.getInvestAmount(), doObj.getInvestAmount())
				&& isEqual(order.getDepositAmount(), doObj.getDepositAmount())
				&& isEqual(order.getMarriage(), doObj.getMarriage())
				&& isEqual(order.getMateName(), doObj.getMateName())
				&& isEqual(order.getMateCertType(), doObj.getMateCertType())
				&& isEqual(order.getMateCertNo(), doObj.getMateCertNo())
				&& isEqual(order.getMateContactNo(), doObj.getMateContactNo())
				&& isEqual(order.getSpouseAddress(), doObj.getSpouseAddress())
				&& isEqual(order.getSpouseImmovableProperty(), doObj.getSpouseImmovableProperty())
				&& isEqual(order.getSpouseMovableProperty(), doObj.getSpouseMovableProperty())
				&& isEqual(order.getHasBankLoan(), doObj.getHasBankLoan())
				&& isEqual(order.getHasFolkLoan(), doObj.getHasFolkLoan())
				&& isEqual(order.getBankLoanAmount(), doObj.getBankLoanAmount())
				&& isEqual(order.getFolkLoanAmount(), doObj.getFolkLoanAmount())
				&& isEqual(order.getConsumerLoanBank(), doObj.getConsumerLoanBank())
				&& isEqual(order.getConsumerLoanAmount(), doObj.getConsumerLoanAmount())
				&& isEqual(order.getConsumerLoanStartDate(), doObj.getConsumerLoanStartDate())
				&& isEqual(order.getConsumerLoanEndDate(), doObj.getConsumerLoanEndDate())
				&& isEqual(order.getBusinesLoanBank(), doObj.getBusinesLoanBank())
				&& isEqual(order.getBusinesLoanAmount(), doObj.getBusinesLoanAmount())
				&& isEqual(order.getBusinesLoanStartDate(), doObj.getBusinesLoanStartDate())
				&& isEqual(order.getBusinesLoanEndDate(), doObj.getBusinesLoanEndDate())
				&& isEqual(order.getMortgageLoanBank(), doObj.getMortgageLoanBank())
				&& isEqual(order.getMortgageLoanAmount(), doObj.getMortgageLoanAmount())
				&& isEqual(order.getMortgageLoanStartDate(), doObj.getMortgageLoanStartDate())
				&& isEqual(order.getMortgageLoanEndDate(), doObj.getMortgageLoanEndDate());
	}
	
	//保存保证人保证能力总体评价（非必填）
	// 重新修改一下
	private void saveGuarantorAbilities(long csrReviewId,
										List<FInvestigationCsRationalityReviewGuarantorAbilityOrder> guarantorAbilities) {
		if (csrReviewId <= 0) {
			return;
		}
		
		if (ListUtil.isEmpty(guarantorAbilities)) {
			FInvestigationCsRationalityReviewGuarantorAbilityDAO.deleteByReviewId(csrReviewId);
			return;
		}
		
		List<FInvestigationCsRationalityReviewGuarantorAbilityDO> items = FInvestigationCsRationalityReviewGuarantorAbilityDAO
			.findByReviewId(csrReviewId);
		Map<Long, FInvestigationCsRationalityReviewGuarantorAbilityDO> map = new HashMap<>();
		for (FInvestigationCsRationalityReviewGuarantorAbilityDO doObj : items) {
			map.put(doObj.getId(), doObj);
		}
		
		Date now = getSysdate();
		int sortOrder = 1;
		for (FInvestigationCsRationalityReviewGuarantorAbilityOrder order : guarantorAbilities) {
			if (order.isNull()) {
				continue;
			}
			
			order.setCsrReviewId(csrReviewId);
			order.setSortOrder(sortOrder++);
			FInvestigationCsRationalityReviewGuarantorAbilityDO doObj = map.get(order.getId());
			if (null == doObj) {
				doObj = new FInvestigationCsRationalityReviewGuarantorAbilityDO();
				BeanCopier.staticCopy(order, doObj);
				doObj.setIntangibleAssets(order.getIntangibleAssets());
				doObj.setContingentLiability(order.getContingentLiability());
				doObj.setGuaranteeAmount(order.getGuaranteeAmount());
				doObj.setRawAddTime(now);
				FInvestigationCsRationalityReviewGuarantorAbilityDAO.insert(doObj);
			} else {
				if (!isEqual(order, doObj)) {
					BeanCopier.staticCopy(order, doObj);
					doObj.setIntangibleAssets(order.getIntangibleAssets());
					doObj.setContingentLiability(order.getContingentLiability());
					doObj.setGuaranteeAmount(order.getGuaranteeAmount());
					FInvestigationCsRationalityReviewGuarantorAbilityDAO.update(doObj);
				}
			}
			
			map.remove(order.getId());
		}
		
		if (null != map && map.size() > 0) {
			for (long id : map.keySet()) {
				FInvestigationCsRationalityReviewGuarantorAbilityDAO.deleteById(id);
			}
		}
	}
	
	private boolean isEqual(FInvestigationCsRationalityReviewGuarantorAbilityOrder order,
							FInvestigationCsRationalityReviewGuarantorAbilityDO doObj) {
		return order.getSortOrder() == doObj.getSortOrder()
				&& isEqual(order.getGuarantor(), doObj.getGuarantor())
				&& isEqual(order.getAbilityLevel(), doObj.getAbilityLevel())
				&& isEqual(order.getTotalCapital(), doObj.getTotalCapital())
				&& isEqual(order.getIntangibleAssets(), doObj.getIntangibleAssets())
				&& isEqual(order.getContingentLiability(), doObj.getContingentLiability())
				&& isEqual(order.getGuaranteeAmount(), doObj.getGuaranteeAmount());
	}
	
	@Override
	public FInvestigationCsRationalityReviewInfo findFInvestigationCsRationalityReviewByFormId(	long formId) {
		FInvestigationCsRationalityReviewDO review = FInvestigationCsRationalityReviewDAO
			.findByFormId(formId);
		if (null == review) {
			return null;
		}
		
		long csrReviewId = review.getCsrReviewId();
		FInvestigationCsRationalityReviewInfo reviewInfo = new FInvestigationCsRationalityReviewInfo();
		reviewInfo.setCurIndex(7);
		BeanCopier.staticCopy(review, reviewInfo);
		
		//保证人保证能力总体评价（非必填）
		List<FInvestigationCsRationalityReviewGuarantorAbilityInfo> guarantorAbilities = new ArrayList<>();
		List<FInvestigationCsRationalityReviewGuarantorAbilityDO> guarantorAbilitieDOs = FInvestigationCsRationalityReviewGuarantorAbilityDAO
			.findByReviewId(csrReviewId);
		for (FInvestigationCsRationalityReviewGuarantorAbilityDO doObj : guarantorAbilitieDOs) {
			FInvestigationCsRationalityReviewGuarantorAbilityInfo guarantorAbilitieInfo = new FInvestigationCsRationalityReviewGuarantorAbilityInfo();
			BeanCopier.staticCopy(doObj, guarantorAbilitieInfo);
			guarantorAbilities.add(guarantorAbilitieInfo);
		}
		reviewInfo.setGuarantorAbilities(guarantorAbilities);
		//担保人 - 授信方案合理性评价
		queryGuarantor(reviewInfo);
		
		queryCommon(reviewInfo, formId + "");
		return reviewInfo;
	}
	
	/** 担保人 - 授信方案合理性评价 */
	private void queryGuarantor(FInvestigationCsRationalityReviewInfo info) {
		if (null == info || info.getFormId() <= 0) {
			return;
		}
		
		long formId = info.getFormId();
		List<FInvestigationCsRationalityReviewGuarantorDO> items = FInvestigationCsRationalityReviewGuarantorDAO
			.findByFormId(formId);
		if (ListUtil.isEmpty(items)) {
			return;
		}
		
		OwnerEnum owner = OwnerEnum.GUARANTOR_NEW;
		List<FInvestigationCsRationalityReviewGuarantorInfo> guarantors = new ArrayList<>();
		for (FInvestigationCsRationalityReviewGuarantorDO doObj : items) {
			FInvestigationCsRationalityReviewGuarantorInfo guarantor = new FInvestigationCsRationalityReviewGuarantorInfo();
			BeanCopier.staticCopy(doObj, guarantor);
			
			//已获得的资质证书
			List<FInvestigationMainlyReviewCertificateInfo> certificates = new ArrayList<>();
			List<FInvestigationMainlyReviewCertificateDO> certificateDOs = FInvestigationMainlyReviewCertificateDAO
				.findByOwnerAndReviewId(owner.code(), doObj.getGuarantorId());
			if (ListUtil.isNotEmpty(certificateDOs)) {
				for (FInvestigationMainlyReviewCertificateDO certificate : certificateDOs) {
					FInvestigationMainlyReviewCertificateInfo certificateInfo = new FInvestigationMainlyReviewCertificateInfo();
					BeanCopier.staticCopy(certificate, certificateInfo);
					certificates.add(certificateInfo);
				}
				guarantor.setCertificates(certificates);
			}
			
			//高管人员
			List<FInvestigationMabilityReviewLeadingTeamInfo> leadingTeams = queryLeadings(
				doObj.getGuarantorId(), owner);
			guarantor.setLeadingTeams(leadingTeams);
			
			//保证人主要财务指标
			List<FinancialKpiInfo> kpies = financialKpiService.queryByFormIdAndType(
				doObj.getGuarantorId(), KpiTypeEnum.CS_LEGAL_PERSON);
			guarantor.setKpies(kpies);
			
			guarantors.add(guarantor);
		}
		
		info.setGuarantors(guarantors);
	}
	
	@Override
	public FormBaseResult saveFInvestigationRisk(final FInvestigationRiskOrder order) {
		return commonFormSaveProcess(order, "保存风险分析结论意见", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				copyForAudit(order); //必须最先执行
				checkProjectPause(order.getProjectCode());
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				FInvestigationDO investigation = FInvestigationDAO.findByFormId(form.getFormId());
				if (null == investigation) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "没有声明数据");
				}
				
				FInvestigationRiskDO risk = FInvestigationRiskDAO.findByFormId(order.getFormId());
				if (null == risk) {
					risk = new FInvestigationRiskDO();
					BeanCopier.staticCopy(order, risk);
					risk.setFormId(form.getFormId());
					risk.setRawAddTime(now);
					FInvestigationRiskDAO.insert(risk);
				} else {
					//					if (order.getRiskId() != risk.getRiskId()) {
					//						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
					//							"表单已经过期，请刷新页面重新操作");
					//					}
					BeanCopier.staticCopy(order, risk);
					FInvestigationRiskDAO.updateByFormId(risk);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FInvestigationRiskInfo findFInvestigationRiskByFormId(long formId) {
		FInvestigationRiskDO risk = FInvestigationRiskDAO.findByFormId(formId);
		if (null == risk) {
			return null;
		}
		
		FInvestigationRiskInfo riskInfo = new FInvestigationRiskInfo();
		riskInfo.setCurIndex(8);
		BeanCopier.staticCopy(risk, riskInfo);
		
		queryCommon(riskInfo, formId + "");
		return riskInfo;
	}
	
	@Override
	public FormBaseResult saveFInvestigationFinancialReview(final FInvestigationFinancialReviewOrder order) {
		return commonFormSaveProcess(order, "保存客户财务评价", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				copyForAudit(order); //必须最先执行
				checkProjectPause(order.getProjectCode());
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				FInvestigationDO investigation = FInvestigationDAO.findByFormId(form.getFormId());
				if (null == investigation) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "没有声明数据");
				}
				
				long reviewId = order.getFReviewId();
				FInvestigationFinancialReviewDO review = FInvestigationFinancialReviewDAO
					.findByFormIdAndSubindex(order.getFormId(), order.getSubIndex());
				if (order.isInit()) {
					//初始化财务评论页面，预生成基本数据及财务数据解释与说明(固定五条数据)
					if (null == review) {
						review = new FInvestigationFinancialReviewDO();
						BeanCopier.staticCopy(order, review);
						review.setFormId(form.getFormId());
						review.setRawAddTime(now);
						review.setSubIndex(0);
						review.setIsActive(BooleanEnum.IS.code());
						reviewId = FInvestigationFinancialReviewDAO.insert(review);
					}
					
					int sortOrder = 1;
					//审计信息
					for (int i = 0; i < 4; i++) {
						FInvestigationFinancialReviewKpiDO financialKpi = new FInvestigationFinancialReviewKpiDO();
						financialKpi.setFReviewId(reviewId);
						financialKpi.setSortOrder(sortOrder++);
						financialKpi.setKpiType(KpiTypeEnum.AUDIT_INFO.code());
						FInvestigationFinancialReviewKpiDAO.insert(financialKpi);
					}
					//财务数据
					sortOrder = 1;
					/* 不用初始化 页面动态添加 2016-9-9 wuzj
					for (int i = 0; i < 5; i++) {
						FInvestigationFinancialReviewKpiDO financialKpi = new FInvestigationFinancialReviewKpiDO();
						financialKpi.setFReviewId(reviewId);
						financialKpi.setSortOrder(sortOrder++);
						financialKpi.setKpiType(KpiTypeEnum.FINANCIAL_DATA.code());
						FInvestigationFinancialReviewKpiDAO.insert(financialKpi);
					}*/
					
					//初始化第2个数据
					init(order);
				} else {
					if (null == review) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"客户财务评价数据未初始化");
					}
					//					if (reviewId != review.getFReviewId()) {
					//						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
					//							"表单已经过期，请刷新页面重新操作");
					//					}
					reviewId = review.getFReviewId();
					order.setFReviewId(reviewId);
					BeanCopier.staticCopy(order, review);
					FInvestigationFinancialReviewDAO.update(review);
					//删除原来的数据
					//					FInvestigationFinancialReviewKpiDAO.deleteByReviewId(reviewId);
					FInvestigationFinancialReviewKpiDAO.deleteByReviewIdAndKpitype(reviewId,
						KpiTypeEnum.FINANCIAL_TABLE.code());
					FInvestigationFinancialReviewKpiDAO.deleteByReviewIdAndKpitype(reviewId,
						KpiTypeEnum.PROFIT_TABLE.code());
					FInvestigationFinancialReviewKpiDAO.deleteByReviewIdAndKpitype(reviewId,
						KpiTypeEnum.FLOW_TABLE.code());
					FInvestigationFinancialReviewKpiDAO.deleteByReviewIdAndKpitype(reviewId,
						KpiTypeEnum.PAY_ABILITY.code());
					FInvestigationFinancialReviewKpiDAO.deleteByReviewIdAndKpitype(reviewId,
						KpiTypeEnum.OPERATE_ABILITY.code());
					FInvestigationFinancialReviewKpiDAO.deleteByReviewIdAndKpitype(reviewId,
						KpiTypeEnum.BENIFIT_ABILITY.code());
					FInvestigationFinancialReviewKpiDAO.deleteByReviewIdAndKpitype(reviewId,
						KpiTypeEnum.CASH_FLOW.code());
					
					//审计信息
					saveFinancialDatas(reviewId, KpiTypeEnum.AUDIT_INFO, order.getAuditInfos(),
						order.getCheckStatus() == 1);
					//财务报表(资产负债)
					saveFinancialKpies(reviewId, KpiTypeEnum.FINANCIAL_TABLE,
						order.getFinancialHeader(), order.getFinancialTables(), now);
					//财务报表(利润)
					saveFinancialKpies(reviewId, KpiTypeEnum.PROFIT_TABLE, order.getProfitHeader(),
						order.getProfitTables(), now);
					//财务报表(现金流量)
					saveFinancialKpies(reviewId, KpiTypeEnum.FLOW_TABLE, order.getFlowHeader(),
						order.getFlowTables(), now);
					//财务数据 (数据解释的时候保存数据,此处再保存)
					saveFinancialDatas(reviewId, KpiTypeEnum.FINANCIAL_DATA,
						order.getFinancialDatas(), order.getCheckStatus() == 1);
					//偿债能力
					saveFinancialKpies(reviewId, KpiTypeEnum.PAY_ABILITY,
						order.getPayAblilitieHeader(), order.getPayAblilities(), now);
					//运营能力
					saveFinancialKpies(reviewId, KpiTypeEnum.OPERATE_ABILITY,
						order.getOperateAblilitieHeader(), order.getOperateAblilities(), now);
					//盈利能力
					saveFinancialKpies(reviewId, KpiTypeEnum.BENIFIT_ABILITY,
						order.getBenifitAblilitieHeader(), order.getBenifitAblilities(), now);
					//现金流
					saveFinancialKpies(reviewId, KpiTypeEnum.CASH_FLOW, order.getCashFlowHeader(),
						order.getCashFlows(), now);
				}
				
				List<FInvestigationFinancialReviewDO> reviews = FInvestigationFinancialReviewDAO
					.findByFormId(order.getFormId());
				if (ListUtil.isNotEmpty(reviews)) {
					for (FInvestigationFinancialReviewDO doObj : reviews) {
						if (doObj.getSubIndex() != order.getSubIndex()
							&& isEqual(BooleanEnum.IS.code(), doObj.getIsActive())) {
							//更新另一个数据状态
							doObj.setIsActive(BooleanEnum.NO.code());
							FInvestigationFinancialReviewDAO.update(doObj);
							
							//更新附件状态
							String suffix = "";
							if (doObj.getSubIndex() == 1) {
								suffix = "_" + doObj.getSubIndex();
							}
							commonAttachmentService.updateStatus(doObj.getFormId() + "_index4" + suffix, 
									CommonAttachmentTypeEnum.FORM_ATTACH, CheckStatusEnum.CHECK_NOT_PASS);
							commonAttachmentService.updateStatus(doObj.getFormId() + suffix, 
									CommonAttachmentTypeEnum.INVESTIGATION_FINANCIAL, CheckStatusEnum.CHECK_NOT_PASS);
						}
					}
				}
				return null;
			}
		}, null, null);
	}
	
	private void init(final FInvestigationFinancialReviewOrder order) {
		// 初始化第2个
		int subIndex = 1;
		FInvestigationFinancialReviewDO review = FInvestigationFinancialReviewDAO
			.findByFormIdAndSubindex(order.getFormId(), subIndex);
		long reviewId = 0L;
		if (null == review) {
			review = new FInvestigationFinancialReviewDO();
			BeanCopier.staticCopy(order, review);
			review.setFormId(order.getFormId());
			review.setRawAddTime(getSysdate());
			review.setSubIndex(subIndex);
			review.setIsActive(BooleanEnum.IS.code());
			reviewId = FInvestigationFinancialReviewDAO.insert(review);
		} else {
			reviewId = review.getFReviewId();
		}
		
		// 审计信息
		for (int i = 0; i < 4; i++) {
			FInvestigationFinancialReviewKpiDO financialKpi = new FInvestigationFinancialReviewKpiDO();
			financialKpi.setFReviewId(reviewId);
			financialKpi.setSortOrder(i + 1);
			financialKpi.setKpiType(KpiTypeEnum.AUDIT_INFO.code());
			FInvestigationFinancialReviewKpiDAO.insert(financialKpi);
		}
	}
	
	//保存财务数据解释与说明
	private void saveFinancialDatas(long FReviewId, KpiTypeEnum kpiType,
									List<FInvestigationFinancialReviewKpiOrder> orders,
									boolean checkExplain) {
		if (FReviewId <= 0 || kpiType == null) {
			return;
		}
		
		if (ListUtil.isEmpty(orders)) {
			List<FInvestigationFinancialReviewKpiDO> kpies = FInvestigationFinancialReviewKpiDAO
				.findByReviewIdAndKpitype(FReviewId, kpiType.code());
			if (ListUtil.isNotEmpty(kpies)) {
				for (FInvestigationFinancialReviewKpiDO doObj : kpies) {
					FInvestigationFinancialReviewKpiDAO.deleteByParentId(doObj.getId());
					FInvestigationFinancialReviewKpiDAO.deleteById(doObj.getId());
				}
			}
			
			return;
		}
		
		List<FInvestigationFinancialReviewKpiDO> kpies = FInvestigationFinancialReviewKpiDAO
			.findByReviewIdAndKpitype(FReviewId, kpiType.code());
		Map<Long, FInvestigationFinancialReviewKpiDO> map = new HashMap<>();
		if (ListUtil.isNotEmpty(kpies)) {
			for (FInvestigationFinancialReviewKpiDO doObj : kpies) {
				if (null != kpies) {
					map.put(doObj.getId(), doObj);
				}
			}
		}
		
		if (kpiType == KpiTypeEnum.FINANCIAL_DATA) { //重大科目异常变动提醒
		
			int sortOrder = 1;
			for (FInvestigationFinancialReviewKpiOrder order : orders) {
				long parentId = 0;
				FInvestigationFinancialReviewKpiDO doObj = null;
				if (order.getId() > 0)
					doObj = map.get(order.getId());
				if (doObj == null) { //不存在的异常科目，新增 
				
					if (StringUtil.isBlank(order.getKpiName())
						&& StringUtil.isBlank(order.getTermType())
						&& StringUtil.isBlank(order.getTermType())
						&& StringUtil.isBlank(order.getKpiValue())
						&& (StringUtil.isBlank(order.getKpiExplainJosn()) || StringUtil.equals(
							order.getKpiExplainJosn(), "[]")))
						continue; //没有任何数据就直接继续了
						
					doObj = new FInvestigationFinancialReviewKpiDO();
					doObj.setFReviewId(FReviewId);
					doObj.setKpiType(kpiType.code());
					doObj.setKpiName(order.getKpiName());
					doObj.setKpiCode(order.getKpiCode());
					doObj.setTermType(order.getTermType());
					doObj.setKpiValue(order.getKpiValue());
					doObj.setKpiUnit(order.getKpiUnit());
					doObj.setRemark(order.getExplaination());//数据解释
					doObj.setSortOrder(sortOrder);
					parentId = FInvestigationFinancialReviewKpiDAO.insert(doObj);
				} else {
					parentId = doObj.getId();
					//删掉原来的数据解释
					FInvestigationFinancialReviewKpiDAO.deleteByParentId(parentId);
					
					if (StringUtil.equals(doObj.getKpiName(), order.getKpiName())
						&& StringUtil.equals(doObj.getKpiValue(), order.getKpiValue())
						&& StringUtil.equals(doObj.getTermType(), order.getTermType())
						&& StringUtil.equals(doObj.getRemark(), order.getExplaination())) {
						//重大科目异常信息 数据没有变动，不需要保存
					} else {
						doObj.setKpiName(order.getKpiName());
						doObj.setKpiCode(order.getKpiCode());
						doObj.setTermType(order.getTermType());
						doObj.setKpiValue(order.getKpiValue());
						doObj.setKpiUnit(order.getKpiUnit());
						doObj.setRemark(order.getExplaination());//数据解释
						doObj.setSortOrder(sortOrder);
						FInvestigationFinancialReviewKpiDAO.update(doObj);
					}
					
					map.remove(doObj.getId());
				}
				
				//解释 [{explainName:'名称',explainValue:'金额',explainRate:'占比'}]
				if (StringUtil.isNotBlank(order.getKpiExplainJosn())) {
					Date now = getSysdate();
					JSONArray kpiExplain = null;
					//重大科目异常金额
					//					Money kpiMoney = Money.amout(doObj.getKpiValue());
					//数据解释金额
					Money explainMoney = Money.zero();
					try {
						kpiExplain = JSONArray.parseArray(order.getKpiExplainJosn());
						if (kpiExplain != null && !kpiExplain.isEmpty()) {
							sortOrder = 0;
							for (Object exKpi : kpiExplain) {
								JSONObject kpi = (JSONObject) exKpi;
								FInvestigationFinancialReviewKpiDO kpiDO = new FInvestigationFinancialReviewKpiDO();
								kpiDO.setKpiName(kpi.getString("explainName"));
								kpiDO.setKpiValue(kpi.getString("explainValue"));
								kpiDO.setKpiRatio(NumberUtil.parseDouble(kpi
									.getString("explainRate")));
								kpiDO.setRemark(doObj.getKpiName() + doObj.getTermType() + "数据解释");//数据解释
								kpiDO.setParentId(doObj.getId());
								kpiDO.setRawAddTime(now);
								kpiDO.setSortOrder(sortOrder++);
								FInvestigationFinancialReviewKpiDAO.insert(kpiDO);
								//累加数据解释的金额
								if (StringUtil.isNotBlank(kpiDO.getKpiValue()))
									explainMoney.addTo(Money.amout(kpiDO.getKpiValue()));
							}
						}
					} catch (Exception e) {
						logger.error("数据解释解析出错：{}", e);
					}
					//验证数据解释金额和科目金额
					//					if (checkExplain && !kpiMoney.equals(explainMoney)) {
					//						throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAILURE,
					//							doObj.getKpiName() + doObj.getTermType() + "数据解释金额不符");
					//					}
				}
				sortOrder++;
			}
			
			if (!map.isEmpty()) {
				for (Long id : map.keySet()) {
					FInvestigationFinancialReviewKpiDAO.deleteById(id);
				}
			}
		} else {
			//审计信息已经初始化
			for (FInvestigationFinancialReviewKpiOrder order : orders) {
				if (null == order || order.getId() <= 0) {
					continue;
				}
				FInvestigationFinancialReviewKpiDO doObj = map.get(order.getId());
				if (null == doObj) {
					continue;
				}
				
				//				if (StringUtil.equals(doObj.getKpiName(), order.getKpiName())
				//					&& StringUtil.equals(doObj.getKpiValue(), order.getKpiValue())
				//					&& StringUtil.equals(doObj.getTermType(), order.getTermType())) {
				//					continue;//数据没有变动，不需要保存
				//				}
				
				int sortOrder = doObj.getSortOrder();
				doObj.setRemark(order.getRemark());
				doObj.setKpiName(order.getKpiName());
				doObj.setKpiCode(order.getKpiCode());
				doObj.setTermType(order.getTermType());
				doObj.setKpiValue(order.getKpiValue());
				doObj.setKpiUnit(order.getKpiUnit());
				
				doObj.setSortOrder(sortOrder);
				FInvestigationFinancialReviewKpiDAO.update(doObj);
			}
		}
	}
	
	//保存财务报表数据
	private void saveFinancialKpies(long reviewId, KpiTypeEnum kpiType,
									FinancialReviewKpiOrder header,
									List<FinancialReviewKpiOrder> financialKpies, Date now) {
		if (ListUtil.isEmpty(financialKpies) || null == header || reviewId <= 0) {
			return;
		}
		
		int sortOrder = 1;
		saveFinancialHeaderKpies(reviewId, kpiType, header, now, sortOrder);
		sortOrder++;
		for (FinancialReviewKpiOrder kpi : financialKpies) {
			if (!kpi.isNull()) {
				saveFinancialKpies(reviewId, kpiType, header, kpi, now, sortOrder++);
			}
		}
	}
	
	//保存财务报表数据
	@SuppressWarnings("deprecation")
	private void saveFinancialHeaderKpies(long reviewId, KpiTypeEnum kpiType,
											FinancialReviewKpiOrder header, Date now, int sortOrder) {
		FInvestigationFinancialReviewKpiDO doObj = new FInvestigationFinancialReviewKpiDO();
		BeanCopier.staticCopy(header, doObj, UnBoxingConverter.getInstance());
		doObj.setFReviewId(reviewId);
		doObj.setKpiType(kpiType.code());
		doObj.setTermType(header.getKpiValue());
		doObj.setRawAddTime(now);
		doObj.setSortOrder(sortOrder);
		FInvestigationFinancialReviewKpiDAO.insert(doObj);
		
		FInvestigationFinancialReviewKpiDO doObj1 = new FInvestigationFinancialReviewKpiDO();
		BeanCopier.staticCopy(header, doObj1, UnBoxingConverter.getInstance());
		doObj1.setFReviewId(reviewId);
		doObj1.setKpiType(kpiType.code());
		doObj1.setTermType(header.getKpiValue1());
		doObj1.setKpiValue(header.getKpiValue1());
		doObj1.setRawAddTime(now);
		doObj1.setSortOrder(sortOrder);
		FInvestigationFinancialReviewKpiDAO.insert(doObj1);
		
		FInvestigationFinancialReviewKpiDO doObj2 = new FInvestigationFinancialReviewKpiDO();
		BeanCopier.staticCopy(header, doObj2, UnBoxingConverter.getInstance());
		doObj2.setFReviewId(reviewId);
		doObj2.setKpiType(kpiType.code());
		doObj2.setTermType(header.getKpiValue2());
		doObj2.setKpiValue(header.getKpiValue2());
		doObj2.setRawAddTime(now);
		doObj2.setSortOrder(sortOrder);
		FInvestigationFinancialReviewKpiDAO.insert(doObj2);
		
		FInvestigationFinancialReviewKpiDO doObj3 = new FInvestigationFinancialReviewKpiDO();
		BeanCopier.staticCopy(header, doObj3, UnBoxingConverter.getInstance());
		doObj3.setFReviewId(reviewId);
		doObj3.setKpiType(kpiType.code());
		doObj3.setTermType(header.getKpiValue3());
		doObj3.setKpiValue(header.getKpiValue3());
		doObj3.setRawAddTime(now);
		doObj3.setSortOrder(sortOrder);
		FInvestigationFinancialReviewKpiDAO.insert(doObj3);
	}
	
	//保存财务报表数据
	@SuppressWarnings("deprecation")
	private void saveFinancialKpies(long reviewId, KpiTypeEnum kpiType,
									FinancialReviewKpiOrder header, FinancialReviewKpiOrder kpi,
									Date now, int sortOrder) {
		FInvestigationFinancialReviewKpiDO doObj = new FInvestigationFinancialReviewKpiDO();
		if (StringUtil.isNotEmpty(kpi.getKpiName())) {
			kpi.setKpiCode(DataFinancialHelper.NAMES_KEY.get(kpi.getKpiName()));
		}
		BeanCopier.staticCopy(kpi, doObj, UnBoxingConverter.getInstance());
		doObj.setFReviewId(reviewId);
		doObj.setKpiType(kpiType.code());
		doObj.setTermType(header.getKpiValue());
		doObj.setRawAddTime(now);
		doObj.setSortOrder(sortOrder);
		FInvestigationFinancialReviewKpiDAO.insert(doObj);
		
		FInvestigationFinancialReviewKpiDO doObj1 = new FInvestigationFinancialReviewKpiDO();
		BeanCopier.staticCopy(kpi, doObj1, UnBoxingConverter.getInstance());
		doObj1.setFReviewId(reviewId);
		doObj1.setKpiType(kpiType.code());
		doObj1.setTermType(header.getKpiValue1());
		doObj1.setKpiValue(kpi.getKpiValue1());
		doObj1.setRawAddTime(now);
		doObj1.setSortOrder(sortOrder);
		FInvestigationFinancialReviewKpiDAO.insert(doObj1);
		
		FInvestigationFinancialReviewKpiDO doObj2 = new FInvestigationFinancialReviewKpiDO();
		BeanCopier.staticCopy(kpi, doObj2, UnBoxingConverter.getInstance());
		doObj2.setFReviewId(reviewId);
		doObj2.setKpiType(kpiType.code());
		doObj2.setTermType(header.getKpiValue2());
		doObj2.setKpiValue(kpi.getKpiValue2());
		doObj2.setRawAddTime(now);
		doObj2.setSortOrder(sortOrder);
		FInvestigationFinancialReviewKpiDAO.insert(doObj2);
		
		FInvestigationFinancialReviewKpiDO doObj3 = new FInvestigationFinancialReviewKpiDO();
		BeanCopier.staticCopy(kpi, doObj3, UnBoxingConverter.getInstance());
		doObj3.setFReviewId(reviewId);
		doObj3.setKpiType(kpiType.code());
		doObj3.setTermType(header.getKpiValue3());
		doObj3.setKpiValue(kpi.getKpiValue3());
		doObj3.setRawAddTime(now);
		doObj3.setSortOrder(sortOrder);
		FInvestigationFinancialReviewKpiDAO.insert(doObj3);
	}
	
	@Override
	public FInvestigationFinancialReviewInfo findFInvestigationFinancialReviewByFormId(long formId) {
		FInvestigationFinancialReviewDO review = findFinancial(formId);
		return convert2Info(review);
	}
	
	@Override
	public FInvestigationFinancialReviewInfo findFInvestigationFinancialReviewByFormIdAndSubindex(	long formId,
																									int subIndex) {
		FInvestigationFinancialReviewDO review = FInvestigationFinancialReviewDAO
			.findByFormIdAndSubindex(formId, subIndex);
		return convert2Info(review);
	}
	
	private FInvestigationFinancialReviewInfo convert2Info(FInvestigationFinancialReviewDO review) {
		if (null == review) {
			return null;
		}
		
		long formId = review.getFormId();
		FInvestigationFinancialReviewInfo reviewIno = new FInvestigationFinancialReviewInfo();
		reviewIno.setCurIndex(4);
		BeanCopier.staticCopy(review, reviewIno);
		reviewIno.setIsActive(BooleanEnum.getByCode(review.getIsActive()));
		reviewIno.setAmountUnit1(AmountUnitEnum.getByCode(review.getAmountUnit1()));
		reviewIno.setAmountUnit2(AmountUnitEnum.getByCode(review.getAmountUnit2()));
		reviewIno.setAmountUnit3(AmountUnitEnum.getByCode(review.getAmountUnit3()));
		
		installFinacialKpis(reviewIno, review.getFReviewId());
		
		queryCommon(reviewIno, formId + "", CommonAttachmentTypeEnum.INVESTIGATION_FINANCIAL);
		return reviewIno;
	}
	
	private void installFinacialKpis(FInvestigationFinancialReviewInfo reviewIno, long reveiwId) {
		//审计信息
		List<FInvestigationFinancialReviewKpiDO> auditInfos = new ArrayList<>();
		//财务报表
		List<FInvestigationFinancialReviewKpiDO> financialTables = new ArrayList<>();
		//财务报表
		List<FInvestigationFinancialReviewKpiDO> profitTables = new ArrayList<>();
		//财务报表
		List<FInvestigationFinancialReviewKpiDO> flowTables = new ArrayList<>();
		//财务数据（重大科目异常信息 ）
		List<FInvestigationFinancialReviewKpiDO> financialDatas = new ArrayList<>();
		//偿债能力
		List<FInvestigationFinancialReviewKpiDO> payAblilities = new ArrayList<>();
		//运营能力
		List<FInvestigationFinancialReviewKpiDO> operateAblilities = new ArrayList<>();
		//盈利能力
		List<FInvestigationFinancialReviewKpiDO> benifitAblilities = new ArrayList<>();
		//现金流
		List<FInvestigationFinancialReviewKpiDO> cashFlows = new ArrayList<>();
		List<FInvestigationFinancialReviewKpiDO> financialKpies = FInvestigationFinancialReviewKpiDAO
			.findByReviewId(reveiwId);
		for (FInvestigationFinancialReviewKpiDO kpi : financialKpies) {
			if (KpiTypeEnum.AUDIT_INFO.code().equals(kpi.getKpiType())) {
				auditInfos.add(kpi);
			} else if (KpiTypeEnum.FINANCIAL_TABLE.code().equals(kpi.getKpiType())) {
				financialTables.add(kpi);
			} else if (KpiTypeEnum.PROFIT_TABLE.code().equals(kpi.getKpiType())) {
				profitTables.add(kpi);
			} else if (KpiTypeEnum.FLOW_TABLE.code().equals(kpi.getKpiType())) {
				flowTables.add(kpi);
			} else if (KpiTypeEnum.FINANCIAL_DATA.code().equals(kpi.getKpiType())) {
				financialDatas.add(kpi);
			} else if (KpiTypeEnum.PAY_ABILITY.code().equals(kpi.getKpiType())) {
				payAblilities.add(kpi);
			} else if (KpiTypeEnum.OPERATE_ABILITY.code().equals(kpi.getKpiType())) {
				operateAblilities.add(kpi);
			} else if (KpiTypeEnum.BENIFIT_ABILITY.code().equals(kpi.getKpiType())) {
				benifitAblilities.add(kpi);
			} else if (KpiTypeEnum.CASH_FLOW.code().equals(kpi.getKpiType())) {
				cashFlows.add(kpi);
			}
		}
		
		reviewIno.setAuditInfos(convertList(auditInfos));
		installFinacialKpis(KpiTypeEnum.FINANCIAL_TABLE, reviewIno, financialTables);
		installFinacialKpis(KpiTypeEnum.PROFIT_TABLE, reviewIno, profitTables);
		installFinacialKpis(KpiTypeEnum.FLOW_TABLE, reviewIno, flowTables);
		//		installFinacialKpis(KpiTypeEnum.FINANCIAL_DATA, reviewIno, financialDatas);
		reviewIno.setFinancialDatas(convertList(financialDatas));
		installFinacialKpis(KpiTypeEnum.PAY_ABILITY, reviewIno, payAblilities);
		installFinacialKpis(KpiTypeEnum.OPERATE_ABILITY, reviewIno, operateAblilities);
		installFinacialKpis(KpiTypeEnum.BENIFIT_ABILITY, reviewIno, benifitAblilities);
		installFinacialKpis(KpiTypeEnum.CASH_FLOW, reviewIno, cashFlows);
	}
	
	private List<FInvestigationFinancialReviewKpiInfo> convertList(	List<FInvestigationFinancialReviewKpiDO> kpies) {
		if (ListUtil.isEmpty(kpies)) {
			return null;
		}
		
		List<FInvestigationFinancialReviewKpiInfo> infos = new ArrayList<>();
		for (FInvestigationFinancialReviewKpiDO kpi : kpies) {
			FInvestigationFinancialReviewKpiInfo info = new FInvestigationFinancialReviewKpiInfo();
			BeanCopier.staticCopy(kpi, info);
			info.setKpiType(KpiTypeEnum.getByCode(kpi.getKpiType()));
			if (info.getKpiType() == KpiTypeEnum.FINANCIAL_DATA) {//财务数据（重大科目异常信息）查询对应的数据解释
				List<FInvestigationFinancialReviewKpiDO> kpiExplain = FInvestigationFinancialReviewKpiDAO
					.findByParentId(info.getId());
				if (kpiExplain != null) {
					JSONArray kpiExplainJosn = new JSONArray();
					for (FInvestigationFinancialReviewKpiDO ke : kpiExplain) {
						//[{explainName:'名称',explainValue:'金额',explainRate:'占比'}]
						JSONObject explain = new JSONObject();
						explain.put("explainName", ke.getKpiName());
						explain.put("explainValue", ke.getKpiValue());
						explain.put("explainRate", ke.getKpiRatio());
						kpiExplainJosn.add(explain);
					}
					info.setKpiExplainJosn(kpiExplainJosn.toJSONString());
				}
			}
			infos.add(info);
		}
		return infos;
	}
	
	private void installFinacialKpis(KpiTypeEnum kpiType,
										FInvestigationFinancialReviewInfo reviewIno,
										List<FInvestigationFinancialReviewKpiDO> kpies) {
		if (ListUtil.isNotEmpty(kpies)) {
			List<FinancialReviewKpiInfo> infos = new ArrayList<>(kpies.size() / 2);
			for (int i = 0; i < kpies.size(); i += 4) {
				FinancialReviewKpiInfo kpi = new FinancialReviewKpiInfo();
				kpi.setKpiName(kpies.get(i).getKpiName());
				kpi.setKpiCode(kpies.get(i).getKpiCode());
				kpi.setKpiValue(kpies.get(i).getKpiValue());
				kpi.setKpiValue1(kpies.get(i + 1).getKpiValue());
				kpi.setKpiValue2(kpies.get(i + 2).getKpiValue());
				kpi.setKpiValue3(kpies.get(i + 3).getKpiValue());
				infos.add(kpi);
			}
			if (KpiTypeEnum.FINANCIAL_TABLE == kpiType) {
				reviewIno.setFinancialHeader(infos.get(0));
				reviewIno.setFinancialTables(infos);
			} else if (KpiTypeEnum.PROFIT_TABLE == kpiType) {
				reviewIno.setProfitHeader(infos.get(0));
				reviewIno.setProfitTables(infos);
			} else if (KpiTypeEnum.FLOW_TABLE == kpiType) {
				reviewIno.setFlowHeader(infos.get(0));
				reviewIno.setFlowTables(infos);
			} /*else if (KpiTypeEnum.FINANCIAL_DATA == kpiType) {
				reviewIno.setFinancialHeader(infos.get(0));
				reviewIno.setFinancialDatas(infos);
				} */else if (KpiTypeEnum.PAY_ABILITY == kpiType) {
				reviewIno.setPayAblilitieHeader(infos.get(0));
				reviewIno.setPayAblilities(infos);
			} else if (KpiTypeEnum.OPERATE_ABILITY == kpiType) {
				reviewIno.setOperateAblilitieHeader(infos.get(0));
				reviewIno.setOperateAblilities(infos);
			} else if (KpiTypeEnum.BENIFIT_ABILITY == kpiType) {
				reviewIno.setBenifitAblilitieHeader(infos.get(0));
				reviewIno.setBenifitAblilities(infos);
			} else if (KpiTypeEnum.CASH_FLOW == kpiType) {
				reviewIno.setCashFlowHeader(infos.get(0));
				reviewIno.setCashFlows(infos);
			}
		}
	}
	
	@Override
	public QueryBaseBatchResult<InvestigationInfo> queryInvestigation(	InvestigationQueryOrder queryOrder) {
		QueryBaseBatchResult<InvestigationInfo> baseBatchResult = new QueryBaseBatchResult<>();
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("projectCode", queryOrder.getProjectCode());
		paramMap.put("projectName", queryOrder.getProjectName());
		paramMap.put("customerName", queryOrder.getCustomerName());
		paramMap.put("customerId", queryOrder.getCustomerId());
		paramMap.put("formStatus", queryOrder.getFormStatus());
		paramMap.put("busiType", queryOrder.getBusiType());
		paramMap.put("bType", queryOrder.getbType());
		paramMap.put("review", queryOrder.getReview());
		paramMap.put("formStatusList", queryOrder.getFormStatusList());
		paramMap.put("loginUserId", queryOrder.getLoginUserId());
		paramMap.put("deptIdList", queryOrder.getDeptIdList());
		paramMap.put("draftUserId", queryOrder.getDraftUserId());
		
		long totalSize = extraDAO.searchInvestigationCount(paramMap);
		
		if (totalSize > 0) {
			PageComponent component = new PageComponent(queryOrder, totalSize);
			paramMap.put("limitStart", component.getFirstRecord());
			paramMap.put("pageSize", component.getPageSize());
			paramMap.put("sortCol", queryOrder.getSortCol());
			paramMap.put("sortOrder", queryOrder.getSortOrder());
			
			List<InvestigationDO> pageList = extraDAO.searchInvestigation(paramMap);
			
			List<InvestigationInfo> list = baseBatchResult.getPageList();
			if (totalSize > 0) {
				for (InvestigationDO sf : pageList) {
					InvestigationInfo info = new InvestigationInfo();
					VoConvert.convertSimpleFormProjectDO2VoInfo(sf, info);
					BeanCopier.staticCopy(sf, info);
					info.setFormCode(FormCodeEnum.getByCode(sf.getFormCode()));
					info.setFormStatus(FormStatusEnum.getByCode(sf.getFormStatus()));
					info.setAmount(sf.getCreditAmount());
					info.setProjectStatus(ProjectStatusEnum.getByCode(sf.getProjectStatus()));
					info.setCouncilBack(BooleanEnum.getByCode(sf.getCouncilBack()));
					list.add(info);
				}
			}
			baseBatchResult.initPageParam(component);
			baseBatchResult.setPageList(list);
		}
		
		baseBatchResult.setSuccess(true);
		
		if ("UPDATE".equals(queryOrder.getReview())) {
			//特殊参数用于更新数据
			moveCsData();
		}
		return baseBatchResult;
	}
	
	@Override
	public FormBaseResult saveFInvestigationLitigation(final FInvestigationLitigationOrder order) {
		return commonFormSaveProcess(order, "保存诉讼担保项目调查报告", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				copyForAudit(order); //必须最先执行
				checkProjectPause(order.getProjectCode());
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				FInvestigationDO investigation = FInvestigationDAO.findByFormId(form.getFormId());
				if (null == investigation) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "没有声明数据");
				}
				
				if (!investigation.getAmount().equals(order.getGuaranteeAmount())) {
					investigation.setAmount(order.getGuaranteeAmount());
					FInvestigationDAO.update(investigation);
				}
				
				FInvestigationLitigationDO doObj = FInvestigationLitigationDAO.findByFormId(order
					.getFormId());
				if (null == doObj) {
					//保存
					doObj = new FInvestigationLitigationDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					doObj.setFormId(form.getFormId());
					doObj.setGuaranteeAmount(order.getGuaranteeAmount());
					doObj.setRawAddTime(now);
					FInvestigationLitigationDAO.insert(doObj);
				} else {
					//更新
					//					if (order.getLitigationId() != doObj.getLitigationId()) {
					//						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
					//							"表单已经过期，请刷新页面重新操作");
					//					}
					
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					doObj.setGuaranteeAmount(order.getGuaranteeAmount());
					FInvestigationLitigationDAO.updateByFormId(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FInvestigationLitigationInfo findFInvestigationLitigationByFormId(long formId) {
		FInvestigationLitigationDO doObj = FInvestigationLitigationDAO.findByFormId(formId);
		if (null == doObj) {
			return null;
		} else {
			FInvestigationLitigationInfo info = new FInvestigationLitigationInfo();
			BeanCopier.staticCopy(doObj, info);
			info.setDepositType(ChargeTypeEnum.getByCode(doObj.getDepositType()));
			info.setGuaranteeType(ChargeTypeEnum.getByCode(doObj.getGuaranteeType()));
			info.setInformationFeeType(ChargeTypeEnum.getByCode(doObj.getInformationFeeType()));
			
			queryCommon(info);
			return info;
		}
	}
	
	@Override
	public FormBaseResult saveFInvestigationUnderwriting(final FInvestigationUnderwritingOrder order) {
		return commonFormSaveProcess(order, "保存承销项目情况", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				copyForAudit(order); //必须最先执行
				checkProjectPause(order.getProjectCode());
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				FInvestigationDO investigation = FInvestigationDAO.findByFormId(form.getFormId());
				if (null == investigation) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "没有声明数据");
				}
				
				if (!investigation.getAmount().equals(order.getFinancingAmount())) {
					investigation.setAmount(order.getFinancingAmount());
					FInvestigationDAO.update(investigation);
				}
				
				FInvestigationUnderwritingDO doObj = FInvestigationUnderwritingDAO
					.findByFormId(order.getFormId());
				if (null == doObj) {
					//保存
					doObj = new FInvestigationUnderwritingDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					doObj.setFormId(form.getFormId());
					doObj.setFinancingAmount(order.getFinancingAmount());
					doObj.setBalance(order.getBalance());
					doObj.setCollectScale(order.getCollectScale());
					doObj.setRawAddTime(now);
					long keyId = FInvestigationUnderwritingDAO.insert(doObj);
					FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					result.setKeyId(keyId);
				} else {
					//更新
					//					if (order.getUnderwritingId() != doObj.getUnderwritingId()) {
					//						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
					//							"表单已经过期，请刷新页面重新操作");
					//					}
					
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					doObj.setFinancingAmount(order.getFinancingAmount());
					doObj.setBalance(order.getBalance());
					doObj.setCollectScale(order.getCollectScale());
					FInvestigationUnderwritingDAO.updateByFormId(doObj);
					FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					result.setKeyId(doObj.getUnderwritingId());
					
					//先删除所有的附件,再添加新附件
					commonAttachmentDAO.deleteByBizNoModuleType(doObj.getUnderwritingUnit() + "",
						CommonAttachmentTypeEnum.UNDERWRITING_PROJECT.code());
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FInvestigationUnderwritingInfo findFInvestigationUnderwritingByFormId(long formId) {
		FInvestigationUnderwritingDO doObj = FInvestigationUnderwritingDAO.findByFormId(formId);
		if (null == doObj) {
			return null;
		} else {
			FInvestigationUnderwritingInfo info = new FInvestigationUnderwritingInfo();
			BeanCopier.staticCopy(doObj, info);
			info.setTimeUnit(TimeUnitEnum.getByCode(doObj.getTimeUnit()));
			info.setChargeUnit(ChargeTypeEnum.getByCode(doObj.getChargeUnit()));
			info.setChargeWay(UnderwritingChargeWaytEnum.getByCode(doObj.getChargeWay()));
			
			queryCommon(info, doObj.getUnderwritingId() + "",
				CommonAttachmentTypeEnum.UNDERWRITING_PROJECT);
			return info;
		}
	}
	
	@Override
	public FcsBaseResult saveFInvestigationFinancialDataExplain(final FInvestigationFinancialDataExplainOrder order) {
		return commonProcess(order, "保存财务数据解释说明", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				FInvestigationFinancialReviewKpiDO kpi = FInvestigationFinancialReviewKpiDAO
					.findById(order.getId());
				if (null == kpi) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
						"表单已经过期，请刷新页面重新操作");
				}
				
				if (StringUtil.notEquals(order.getExplaination(), kpi.getRemark())) {
					kpi.setRemark(order.getExplaination());
					kpi.setKpiName(order.getItemName());
					kpi.setTermType(order.getItemDate());
					kpi.setKpiValue(order.getItemValue());
					FInvestigationFinancialReviewKpiDAO.update(kpi);
				}
				
				FInvestigationFinancialReviewKpiDAO.deleteByParentId(order.getId());
				
				if (ListUtil.isNotEmpty(order.getKpiExplains())) {
					int sortOrder = 1;
					Date now = getSysdate();
					for (FInvestigationFinancialReviewKpiOrder explainOrder : order
						.getKpiExplains()) {
						FInvestigationFinancialReviewKpiDO doObj = new FInvestigationFinancialReviewKpiDO();
						BeanCopier.staticCopy(explainOrder, doObj, UnBoxingConverter.getInstance());
						doObj.setKpiName(explainOrder.getExplainName());
						doObj.setKpiValue(explainOrder.getExplainValue());
						doObj.setParentId(order.getId());
						doObj.setRawAddTime(now);
						doObj.setSortOrder(sortOrder++);
						FInvestigationFinancialReviewKpiDAO.insert(doObj);
					}
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FInvestigationFinancialDataExplainInfo findFInvestigationFinancialDataExplainById(long id) {
		FInvestigationFinancialReviewKpiDO kpi = FInvestigationFinancialReviewKpiDAO.findById(id);
		if (null == kpi) {
			return null;
		}
		
		FInvestigationFinancialDataExplainInfo info = new FInvestigationFinancialDataExplainInfo();
		info.setId(id);
		info.setExplaination(kpi.getRemark());
		
		List<FInvestigationFinancialReviewKpiDO> kpies = FInvestigationFinancialReviewKpiDAO
			.findByParentId(id);
		if (ListUtil.isNotEmpty(kpies)) {
			List<FInvestigationFinancialReviewKpiInfo> kpiInfos = new ArrayList<>();
			for (FInvestigationFinancialReviewKpiDO doObj : kpies) {
				FInvestigationFinancialReviewKpiInfo kpiInfo = new FInvestigationFinancialReviewKpiInfo();
				BeanCopier.staticCopy(doObj, kpiInfo);
				kpiInfos.add(kpiInfo);
			}
			info.setKpiExplains(kpiInfos);
		}
		return info;
	}
	
	@Override
	public QueryBaseBatchResult<ProjectInfo> querySelectableProject(InvestigationQueryOrder queryOrder) {
		QueryBaseBatchResult<ProjectInfo> baseBatchResult = new QueryBaseBatchResult<ProjectInfo>();
		if (null == queryOrder) {
			return baseBatchResult;
		}
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("projectName", queryOrder.getProjectName());
		paramMap.put("projectCode", queryOrder.getProjectCode());
		paramMap.put("customerName", queryOrder.getCustomerName());
		
		long totalSize = extraDAO.searchInvestigationSelectProjectCount(paramMap);
		if (totalSize > 0) {
			paramMap.put("sortCol", queryOrder.getSortCol());
			paramMap.put("sortOrder", queryOrder.getSortOrder());
			paramMap.put("limitStart", queryOrder.getLimitStart());
			paramMap.put("pageSize", queryOrder.getPageSize());
			
			PageComponent component = new PageComponent(queryOrder, totalSize);
			List<ProjectDO> pageList = extraDAO.searchInvestigationSelectProject(paramMap);
			
			List<ProjectInfo> list = baseBatchResult.getPageList();
			if (totalSize > 0) {
				for (ProjectDO project : pageList) {
					ProjectInfo info = DoConvert.convertProjectDO2Info(project);
					list.add(info);
				}
			}
			baseBatchResult.initPageParam(component);
			baseBatchResult.setPageList(list);
		}
		
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public long queryInvestigationFormIdByProjectCode(String projectCode) {
		InvestigationQueryOrder queryOrder = new InvestigationQueryOrder();
		queryOrder.setProjectCode(projectCode);
		QueryBaseBatchResult<InvestigationInfo> batchResult = this.queryInvestigation(queryOrder);
		if (null != batchResult) {
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				return batchResult.getPageList().get(0).getFormId();
			}
		}
		
		return 0L;
	}
	
	private FcsBaseResult copy(long newFormId, long oldFormId, FInvestigationDO fInvestigationDO) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			FormDO form = formDAO.findByFormId(oldFormId);
			FormCodeEnum formCode = FormCodeEnum.getByCode(form.getFormCode());
			if (formCode == FormCodeEnum.INVESTIGATION_BASE) {
				copyBase(oldFormId, newFormId, fInvestigationDO, true, true);
			} else if (formCode == FormCodeEnum.INVESTIGATION_LITIGATION) {
				copyLitigation(oldFormId, newFormId, fInvestigationDO);
			} else if (formCode == FormCodeEnum.INVESTIGATION_UNDERWRITING) {
				copyUnderwriting(oldFormId, newFormId, fInvestigationDO);
			} else {
				result.setSuccess(false);
				result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
				result.setMessage("不支持此类型项目");
				return result;
			}
			
			//更新审核状态
			FormDO newForm = formDAO.findByFormId(newFormId);
			String checkStatus = newForm.getCheckStatus().replaceAll("0", "1");
			checkStatus = checkStatus.replaceFirst("1", "0");
			newForm.setCheckStatus(checkStatus);
			formDAO.update(newForm);
			
			logger.info("尽调预设:已经完成对尽职调查的复制，表单ID：" + oldFormId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("尽调预设:复制异常，表单ID：" + oldFormId);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.EXECUTE_FAIL);
			result.setMessage("复制尽调异常");
		}
		return result;
	}
	
	/**
	 * 上会退回处理
	 * @param order
	 */
	private void processCouncilBack(final InvestigationBaseOrder order) {
		final FInvestigationCopyOrder copyOrder = new FInvestigationCopyOrder();
		copyOrder.setFormId(order.getFormId());
		copyOrder.setCouncilBack(BooleanEnum.YES.code());
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					//1.复制尽调(新表单默认草稿状态)
					FcsBaseResult result = copyHistory(copyOrder);
					logger.info("上会退回：复制尽调结果 " + result);
					
					if (result.isSuccess()) {
						// 2.历史表单设置为删除状态
						FormDO formDO = formDAO.findByFormId(order.getFormId());
						formDO.setStatus(FormStatusEnum.DELETED.code());
						formDAO.update(formDO);
						// 3. 项目更定为尽职阶段 状态更定为草稿
						ProjectDO project = projectDAO.findByProjectCodeForUpdate(order
							.getProjectCode());
						if (!ProjectStatusEnum.PAUSE.code().equals(project.getStatus())) {
							project.setStatus(ProjectStatusEnum.NORMAL.code());
							project.setPhases(ProjectPhasesEnum.INVESTIGATING_PHASES.code());
							project.setPhasesStatus(ProjectPhasesStatusEnum.DRAFT.code());
							projectDAO.update(project);
						} else {
							logger.info("上会退回：已暂缓的项目无法修订！" + order.getProjectCode());
						}
					}
					logger.info("上会退回处理结果：" + result);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("上会退回处理异常：" + e.getMessage(), e);
				}
			}
		});
	}
	
	private long processReview(final InvestigationBaseOrder order) {
		FInvestigationCopyOrder copyOrder = new FInvestigationCopyOrder();
		copyOrder.setFormId(order.getFormId());
		copyOrder.setReview(BooleanEnum.YES.code());
		
		//1.复制尽调(新表单默认草稿状态)
		FcsBaseResult result = copyHistory(copyOrder);
		logger.info("复议：复制尽调结果 " + result);
		
		if (result.isSuccess()) {
			// 2.历史表单设置为删除状态
			FormDO formDO = formDAO.findByFormId(order.getFormId());
			formDO.setStatus(FormStatusEnum.DELETED.code());
			formDAO.update(formDO);
			try {
				deleteInvestigationCommonAttachment(formDO.getFormId());
			} catch (Exception e) {
				logger.error("复议：删除尽调附件出错 form: {} {}", formDO, e);
			}
			// 3. 项目更定为尽职阶段 状态更定为草稿
			ProjectDO project = projectDAO.findByProjectCodeForUpdate(order.getProjectCode());
			if (!ProjectStatusEnum.PAUSE.code().equals(project.getStatus())) {
				project.setStatus(ProjectStatusEnum.NORMAL.code());
				project.setPhases(ProjectPhasesEnum.INVESTIGATING_PHASES.code());
				project.setPhasesStatus(ProjectPhasesStatusEnum.DRAFT.code());
				project.setIsRecouncil(BooleanEnum.NO.code());
				project.setLastRecouncilTime(getSysdate());
				projectDAO.update(project);
			} else {
				logger.info("复议：已暂缓的项目无法修订！" + order.getProjectCode());
			}
			
			return result.getKeyId();
		}
		logger.info("复议处理结果：" + result);
		return 0L;
	}
	
	private FcsBaseResult copyHistory(FInvestigationCopyOrder copyOrder) {
		FcsBaseResult result = new FcsBaseResult();
		long oldFormId = copyOrder.getFormId();
		FInvestigationDO oldInvestigation = FInvestigationDAO.findByFormId(oldFormId);
		if (null == oldInvestigation) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
			result.setMessage("未找到对应的尽职调查报告");
			logger.info("尽调复制：" + result);
			return result;
		}
		
		FInvestigationAddOrder order = new FInvestigationAddOrder();
		BeanCopier.staticCopy(oldInvestigation, order);
		order.setCustomerId(oldInvestigation.getCustomerId());
		order.setNewFormId(oldFormId); //关联原来的表单
		
		FormDO oldForm = formDAO.findByFormId(oldFormId);
		order.setFormCode(FormCodeEnum.getByCode(oldForm.getFormCode()));
		order.setCheckIndex(0);
		order.setCheckStatus(1);
		order.setUserId(oldForm.getUserId());
		order.setUserAccount(oldForm.getUserAccount());
		order.setUserName(oldForm.getUserName());
		order.setReview(copyOrder.getReview());
		order.setCouncilBack(copyOrder.getCouncilBack());
		if (StringUtil.equals(BooleanEnum.YES.code(), copyOrder.getReview())) {
			order.setCouncilBack(BooleanEnum.NO.code());
		}
		if (StringUtil.equals(BooleanEnum.YES.code(), oldInvestigation.getReview())) {
			order.setReview(BooleanEnum.YES.code());
		}
		
		//保存
		FormBaseResult newFormResult = this.addCopy(order);
		if (!newFormResult.isSuccess()) {
			result.setSuccess(false);
			result.setFcsResultEnum(newFormResult.getFcsResultEnum());
			result.setMessage(newFormResult.getMessage());
			return result;
		}
		
		long newFormId = newFormResult.getFormInfo().getFormId();
		//		oldInvestigation.setNewFormId(newFormId);//原报告(历史且删除状态)关联新报告
		//		FInvestigationDAO.update(oldInvestigation);
		
		//如果是复议或是上会退回的，需要复制人员
		if (StringUtil.equals(BooleanEnum.YES.code(), copyOrder.getCouncilBack())
			|| StringUtil.equals(BooleanEnum.YES.code(), copyOrder.getReview())) {
			copyPerson(oldFormId, newFormId);
		}
		
		if (order.getFormCode() == FormCodeEnum.INVESTIGATION_BASE || order.getFormCode() == FormCodeEnum.INVESTIGATION_INNOVATIVE_PRODUCT) {
			copyBase(oldFormId, newFormId, oldInvestigation, false, true, true);
		} else if (order.getFormCode() == FormCodeEnum.INVESTIGATION_LITIGATION) {
			copyLitigation(oldFormId, newFormId, oldInvestigation);
		} else if (order.getFormCode() == FormCodeEnum.INVESTIGATION_UNDERWRITING) {
			copyUnderwriting(oldFormId, newFormId, oldInvestigation);
		} else {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
			result.setMessage("尽调复制:不支持此类型项目");
			return result;
		}
		
		//新表单各项验证通过
		FormDO newForm = formDAO.findByFormId(newFormId);
		newForm.setCheckStatus(order.getFormCode().defaultCheckStatus().replaceAll("0", "1"));
		formDAO.update(newForm);
		
		logger.info("尽调复制:成功：" + newFormId);
		result.setSuccess(true);
		result.setMessage("尽调复制完成");
		result.setKeyId(newFormId);
		
		return result;
	}
	
	private void copyPerson(long oldFormId, long newFormId) {
		List<FInvestigationPersonDO> items = FInvestigationPersonDAO.findByFormId(oldFormId);
		if (ListUtil.isNotEmpty(items)) {
			Date now = new Date();
			for (FInvestigationPersonDO item : items) {
				FInvestigationPersonDO doObj = new FInvestigationPersonDO();
				BeanCopier.staticCopy(item, doObj);
				doObj.setId(0L);
				doObj.setFormId(newFormId);
				doObj.setRawAddTime(now);
				FInvestigationPersonDAO.insert(doObj);
			}
		}
	}
	
	private void copyBase(long oldFormId, long newFormId, FInvestigationDO fInvestigationDO,
							boolean updateProject, boolean updateCustomer) {
		//		copyCreditScheme(oldFormId, newFormId, fInvestigationDO, updateProject);
		//		copyMainlyReview(oldFormId, newFormId, fInvestigationDO, updateCustomer);
		//		copyMabilityReview(oldFormId, newFormId, fInvestigationDO);
		//		copyOpabilityReview(oldFormId, newFormId, fInvestigationDO);
		//		copyFinancialReview(oldFormId, newFormId, fInvestigationDO);
		//		copyMajorEvent(oldFormId, newFormId, fInvestigationDO);
		//		copyProjectStatus(oldFormId, newFormId, fInvestigationDO);
		//		copyCsRationalityReview(oldFormId, newFormId, fInvestigationDO);
		//		copyRisk(oldFormId, newFormId, fInvestigationDO);
		copyBase(oldFormId, newFormId, fInvestigationDO, updateProject, updateCustomer, false);
	}
	
	private void copyBase(long oldFormId, long newFormId, FInvestigationDO fInvestigationDO,
							boolean updateProject, boolean updateCustomer, boolean deleteAttach) {
		copyCreditScheme(oldFormId, newFormId, fInvestigationDO, updateProject,null);
		copyMainlyReview(oldFormId, newFormId, fInvestigationDO, updateCustomer);
		copyMabilityReview(oldFormId, newFormId, fInvestigationDO);
		copyOpabilityReview(oldFormId, newFormId, fInvestigationDO);
		copyFinancialReview(oldFormId, newFormId, fInvestigationDO);
		copyMajorEvent(oldFormId, newFormId, fInvestigationDO);
		copyProjectStatus(oldFormId, newFormId, fInvestigationDO);
		copyCsRationalityReview(oldFormId, newFormId, fInvestigationDO);
		copyRisk(oldFormId, newFormId, fInvestigationDO);
		
		if (deleteAttach) {
			deleteAttachments(oldFormId);
		}
	}
	
	private void deleteAttachments(long formId) {
		commonAttachmentService.updateStatus(formId + "",
			CommonAttachmentTypeEnum.COUNTER_GUARANTEE, CheckStatusEnum.CHECK_NOT_PASS);
		commonAttachmentService.updateStatus(formId + "_index0",
			CommonAttachmentTypeEnum.FORM_ATTACH, CheckStatusEnum.CHECK_NOT_PASS);
		commonAttachmentService.updateStatus(formId + "",
			CommonAttachmentTypeEnum.INVESTIGATION_REPORT, CheckStatusEnum.CHECK_NOT_PASS);
		
		commonAttachmentService.updateStatus(formId + "_index1",
			CommonAttachmentTypeEnum.FORM_ATTACH, CheckStatusEnum.CHECK_NOT_PASS);
		
		commonAttachmentService.updateStatus(formId + "_index2",
			CommonAttachmentTypeEnum.FORM_ATTACH, CheckStatusEnum.CHECK_NOT_PASS);
		
		commonAttachmentService.updateStatus(formId + "_index3",
			CommonAttachmentTypeEnum.FORM_ATTACH, CheckStatusEnum.CHECK_NOT_PASS);
		
		commonAttachmentService.updateStatus(formId + "",
			CommonAttachmentTypeEnum.INVESTIGATION_FINANCIAL, CheckStatusEnum.CHECK_NOT_PASS);
		commonAttachmentService.updateStatus(formId + "_index4",
			CommonAttachmentTypeEnum.FORM_ATTACH, CheckStatusEnum.CHECK_NOT_PASS);
		
		commonAttachmentService.updateStatus(formId + "_index5",
			CommonAttachmentTypeEnum.FORM_ATTACH, CheckStatusEnum.CHECK_NOT_PASS);
		
		commonAttachmentService.updateStatus(formId + "", CommonAttachmentTypeEnum.INVESTIGATION_6,
			CheckStatusEnum.CHECK_NOT_PASS);
		commonAttachmentService.updateStatus(formId + "_index6",
			CommonAttachmentTypeEnum.FORM_ATTACH, CheckStatusEnum.CHECK_NOT_PASS);
		
		commonAttachmentService.updateStatus(formId + "_index7",
			CommonAttachmentTypeEnum.FORM_ATTACH, CheckStatusEnum.CHECK_NOT_PASS);
		
		commonAttachmentService.updateStatus(formId + "_index8",
			CommonAttachmentTypeEnum.FORM_ATTACH, CheckStatusEnum.CHECK_NOT_PASS);
	}
	
	//诉讼担保项目调查报告
	private void copyLitigation(long oldFormId, long newFormId, FInvestigationDO fInvestigationDO) {
		FInvestigationLitigationDO litigation = FInvestigationLitigationDAO.findByFormId(oldFormId);
		if (null == litigation) {
			return;
		}
		FInvestigationLitigationDO doObj = new FInvestigationLitigationDO();
		BeanCopier.staticCopy(litigation, doObj);
		doObj.setLitigationId(0L);
		doObj.setFormId(newFormId);
		if (null != fInvestigationDO) {
			doObj.setProjectCode(fInvestigationDO.getProjectCode());
			doObj.setProjectName(fInvestigationDO.getProjectName());
			doObj.setCustomerId(fInvestigationDO.getCustomerId());
			doObj.setCustomerName(fInvestigationDO.getCustomerName());
		}
		doObj.setRawAddTime(getSysdate());
		FInvestigationLitigationDAO.insert(doObj);
	}
	
	//承销项目情况
	private void copyUnderwriting(long oldFormId, long newFormId, FInvestigationDO fInvestigationDO) {
		FInvestigationUnderwritingDO underwriting = FInvestigationUnderwritingDAO
			.findByFormId(oldFormId);
		if (null == underwriting) {
			return;
		}
		FInvestigationUnderwritingDO doObj = new FInvestigationUnderwritingDO();
		BeanCopier.staticCopy(underwriting, doObj);
		doObj.setUnderwritingId(0L);
		doObj.setFormId(newFormId);
		if (null != fInvestigationDO) {
			doObj.setProjectCode(fInvestigationDO.getProjectCode());
			doObj.setProjectName(fInvestigationDO.getProjectName());
			doObj.setCustomerId(fInvestigationDO.getCustomerId());
			doObj.setCustomerName(fInvestigationDO.getCustomerName());
		}
		doObj.setRawAddTime(getSysdate());
		long newId = FInvestigationUnderwritingDAO.insert(doObj);
		
		//复制附件
		copyAttachments(underwriting.getUnderwritingId() + "",
			CommonAttachmentTypeEnum.UNDERWRITING_PROJECT.code(), newId + "",
			fInvestigationDO.getProjectCode(), null);
	}
	
	//授信方案
	private void copyCreditScheme(long oldFormId, long newFormId,
									FInvestigationDO fInvestigationDO, boolean updateProject,String checkPoint) {
		//分管业务副总 不用copy
		if (StringUtils.isBlank(checkPoint)) {
			FormDO oldForm = formDAO.findByFormId(oldFormId);
			FormDO newForm = formDAO.findByFormId(newFormId);
			//旧的值(将原尽职报告 结论意见 和 评估机构名称、评估时间、评估方法、初评价值评价 复制到新的尽职报告中)
			FInvestigationCsRationalityReviewInfo fInvestigationCsRationalityReviewInfo = findFInvestigationCsRationalityReviewByFormId(oldFormId);
			FInvestigationRiskInfo fInvestigationRiskInfo = findFInvestigationRiskByFormId(oldFormId);
			if (fInvestigationRiskInfo == null && fInvestigationCsRationalityReviewInfo == null ) {
				newForm.setCustomizeField(oldForm.getCustomizeField());
			} else {
				String customizeField = newForm.getCustomizeField();
				if (!StringUtils.isBlank(customizeField)) {
					customizeField = customizeField.replaceAll("}", "");
					StringBuffer sb = new StringBuffer(customizeField);
					if (null != fInvestigationCsRationalityReviewInfo) {
						sb.append(",").append("\"assessment\":").append("\"").append(fInvestigationCsRationalityReviewInfo.getReviewSummary()).append("\"");
					}
					if (null != fInvestigationRiskInfo) {
						sb.append(",").append("\"opinion\":").append("\"").append(fInvestigationRiskInfo.getConclusion()).append("\"");
					}
					sb.append("}");
					customizeField = sb.toString();
					newForm.setCustomizeField(customizeField);
				}
			}
			formDAO.update(newForm);
		}
		Date now = getSysdate();
		FInvestigationCreditSchemeDO creditScheme = FInvestigationCreditSchemeDAO
			.findByFormId(oldFormId);
		if (null == creditScheme) {
			logger.info("尽调预设:已经完成对尽职调查的复制:授信方案为null");
			return;
		}
		FInvestigationCreditSchemeDO newCreditScheme = new FInvestigationCreditSchemeDO();
		BeanCopier.staticCopy(creditScheme, newCreditScheme);
		newCreditScheme.setSchemeId(0L);
		newCreditScheme.setFormId(newFormId);
		if (null != fInvestigationDO) {
			newCreditScheme.setProjectCode(fInvestigationDO.getProjectCode());
			newCreditScheme.setProjectName(fInvestigationDO.getProjectName());
			newCreditScheme.setCustomerId(fInvestigationDO.getCustomerId());
			newCreditScheme.setCustomerName(fInvestigationDO.getCustomerName());
		}
		
		List<CommonAttachmentDO> counterAttaches1 = null;
		if (updateProject) {
			ProjectInfo project = projectService.queryByCode(fInvestigationDO.getProjectCode(),
				false);
			if (null != project) {
				newCreditScheme.setCreditAmount(project.getAmount());
				newCreditScheme.setTimeLimit(project.getTimeLimit());
				if (null != project.getTimeUnit()) {
					newCreditScheme.setTimeUnit(project.getTimeUnit().code());
				}
				newCreditScheme.setIndustryCode(project.getIndustryCode());
				newCreditScheme.setIndustryName(project.getIndustryName());
			}
			
			FProjectGuaranteeEntrustedInfo guaranteeEntrustedInfo = projectSetupService
				.queryGuaranteeEntrustedProjectByCode(fInvestigationDO.getProjectCode());
			if (null != guaranteeEntrustedInfo) {
				newCreditScheme.setCapitalChannelId(guaranteeEntrustedInfo.getCapitalChannelId());
				newCreditScheme.setCapitalChannelName(guaranteeEntrustedInfo
					.getCapitalChannelName());
				//二级渠道
				newCreditScheme.setCapitalSubChannelId(guaranteeEntrustedInfo
					.getCapitalSubChannelId());
				newCreditScheme.setCapitalSubChannelName(guaranteeEntrustedInfo
					.getCapitalSubChannelName());
				newCreditScheme.setProjectChannelId(guaranteeEntrustedInfo.getProjectChannelId());
				newCreditScheme.setProjectChannelName(guaranteeEntrustedInfo
					.getProjectChannelName());
				newCreditScheme.setProjectSubChannelId(guaranteeEntrustedInfo
					.getProjectSubChannelId());
				newCreditScheme.setProjectSubChannelName(guaranteeEntrustedInfo
					.getProjectSubChannelName());
				
				newCreditScheme.setLoanPurpose(guaranteeEntrustedInfo.getLoanPurpose());
				
				newCreditScheme.setDeposit(guaranteeEntrustedInfo.getDeposit());
				if (null != guaranteeEntrustedInfo.getDepositType()) {
					newCreditScheme.setDepositType(guaranteeEntrustedInfo.getDepositType().code());
				}
				newCreditScheme.setDepositAccount(guaranteeEntrustedInfo.getDepositAccount());
				//从立项中带附件过来
				//			queryCommonAttachmentData(model, guaranteeEntrustedInfo.getFormId() + "",
				//				CommonAttachmentTypeEnum.COUNTER_GUARANTEE);
				CommonAttachmentDO commonAttachment = new CommonAttachmentDO();
				commonAttachment.setBizNo(guaranteeEntrustedInfo.getFormId() + "");
				commonAttachment.setModuleType(CommonAttachmentTypeEnum.COUNTER_GUARANTEE.code());
				counterAttaches1 = commonAttachmentDAO.findByBizNoModuleType(commonAttachment);
			}
			//从立项中带过来2016-07-21
			FProjectInfo fproject = projectSetupService.queryProjectByCode(fInvestigationDO
				.getProjectCode());
			if (null != fproject && StringUtil.isNotBlank(fproject.getOtherCounterGuarntee())) {
				newCreditScheme.setOther(fproject.getOtherCounterGuarntee());
			}
			
			// 复制渠道  从立项带过来
			if (null != fproject) {
				List<ProjectChannelRelationDO> channels = projectChannelRelationDAO
					.findByBizNo(fproject.getFormId() + "");
				if (ListUtil.isNotEmpty(channels)) {
					for (ProjectChannelRelationDO channel : channels) {
						ProjectChannelRelationDO doObj = new ProjectChannelRelationDO();
						BeanCopier.staticCopy(channel, doObj);
						doObj.setId(0L);
						doObj.setBizNo(newFormId + "");
						doObj.setPhases(ProjectPhasesEnum.INVESTIGATING_PHASES.code());
						doObj.setRawAddTime(now);
						projectChannelRelationDAO.insert(doObj);
					}
				}
			}
			
		} else {
			//复制渠道
			List<ProjectChannelRelationDO> channels = projectChannelRelationDAO
				.findByBizNo(oldFormId + "");
			if (ListUtil.isNotEmpty(channels)) {
				for (ProjectChannelRelationDO channel : channels) {
					ProjectChannelRelationDO doObj = new ProjectChannelRelationDO();
					BeanCopier.staticCopy(channel, doObj);
					doObj.setId(0L);
					doObj.setBizNo(newFormId + "");
					doObj.setRawAddTime(now);
					projectChannelRelationDAO.insert(doObj);
				}
			}
		}
		
		CommonAttachmentDO commonAttachment2 = new CommonAttachmentDO();
		commonAttachment2.setBizNo(oldFormId + "");
		commonAttachment2.setModuleType(CommonAttachmentTypeEnum.COUNTER_GUARANTEE.code());
		List<CommonAttachmentDO> counterAttaches2 = commonAttachmentDAO
			.findByBizNoModuleType(commonAttachment2);
		if (ListUtil.isNotEmpty(counterAttaches1)) {
			copyAttachment(counterAttaches1, newFormId + "",
				CommonAttachmentTypeEnum.COUNTER_GUARANTEE.code(), "",
				fInvestigationDO.getProjectCode(), "尽调-反担保附件");
		} else {
			copyAttachment(counterAttaches2, newFormId + "",
				CommonAttachmentTypeEnum.COUNTER_GUARANTEE.code(), "",
				fInvestigationDO.getProjectCode(), "尽调-反担保附件");
		}
		
		newCreditScheme.setRawAddTime(now);
		long schemeId = FInvestigationCreditSchemeDAO.insert(newCreditScheme);
		
		List<FInvestigationCreditSchemePledgeAssetDO> pledgeDOs = FInvestigationCreditSchemePledgeAssetDAO
			.findByFormId(oldFormId);
		for (FInvestigationCreditSchemePledgeAssetDO pledge : pledgeDOs) {
			FInvestigationCreditSchemePledgeAssetDO doObj = new FInvestigationCreditSchemePledgeAssetDO();
			BeanCopier.staticCopy(pledge, doObj);
			doObj.setId(0L);
			doObj.setFormId(newFormId);
			doObj.setRawAddTime(now);
			FInvestigationCreditSchemePledgeAssetDAO.insert(doObj);
		}
		
		List<FInvestigationCreditSchemeGuarantorDO> guarantorDOs = FInvestigationCreditSchemeGuarantorDAO
			.findBySchemeId(creditScheme.getSchemeId());
		for (FInvestigationCreditSchemeGuarantorDO guarantor : guarantorDOs) {
			FInvestigationCreditSchemeGuarantorDO doObj = new FInvestigationCreditSchemeGuarantorDO();
			BeanCopier.staticCopy(guarantor, doObj);
			doObj.setId(0L);
			doObj.setSchemeId(schemeId);
			doObj.setRawAddTime(now);
			FInvestigationCreditSchemeGuarantorDAO.insert(doObj);
		}
		
		List<FInvestigationCreditSchemeProcessControlDO> processDOs = FInvestigationCreditSchemeProcessControlDAO
			.findByFormId(oldFormId);
		for (FInvestigationCreditSchemeProcessControlDO process : processDOs) {
			FInvestigationCreditSchemeProcessControlDO doObj = new FInvestigationCreditSchemeProcessControlDO();
			BeanCopier.staticCopy(process, doObj);
			doObj.setId(0L);
			doObj.setFormId(newFormId);
			doObj.setRawAddTime(now);
			FInvestigationCreditSchemeProcessControlDAO.insert(doObj);
		}
		
		//复制附件
		copyAttachments(oldFormId + "_index0", CommonAttachmentTypeEnum.FORM_ATTACH.code(),
			newFormId + "_index0", fInvestigationDO.getProjectCode(), "尽调授信方案表单附件");
		copyAttachments(oldFormId + "", CommonAttachmentTypeEnum.INVESTIGATION_REPORT.code(),
			newFormId + "", /*fInvestigationDO.getProjectCode()*/"",
			CommonAttachmentTypeEnum.INVESTIGATION_REPORT.message());
		
	
		
		logger.info("尽调预设:已经完成对尽职调查的复制:授信方案");
	}
	
	private void copyAttachment(List<CommonAttachmentDO> attachments, String bizNo,
								String moduleType, String childId, String projectCode, String remark) {
		if (ListUtil.isNotEmpty(attachments)) {
			Date now = getSysdate();
			for (CommonAttachmentDO attachment : attachments) {
				CommonAttachmentDO doObj = new CommonAttachmentDO();
				BeanCopier.staticCopy(attachment, doObj);
				doObj.setAttachmentId(0L);
				doObj.setBizNo(bizNo);
				doObj.setModuleType(moduleType);
				doObj.setChildId(childId);
				doObj.setRawAddTime(now);
				doObj.setRemark(remark);
				doObj.setProjectCode(projectCode);
				commonAttachmentDAO.insert(doObj);
			}
		}
	}
	
	private void copyAttachments(String bizNoOld, String moduleTypeOld, String bizNo,
									String projectCode, String remark) {
		CommonAttachmentDO commonAttachment = new CommonAttachmentDO();
		commonAttachment.setBizNo(bizNoOld);
		commonAttachment.setModuleType(moduleTypeOld);
		List<CommonAttachmentDO> counterAttaches = commonAttachmentDAO
			.findByBizNoModuleType(commonAttachment);
		copyAttachment(counterAttaches, bizNo, moduleTypeOld, "", projectCode, remark);
	}
	
	//主体评价
	private void copyMainlyReview(long oldFormId, long newFormId,
									FInvestigationDO fInvestigationDO, boolean update) {
		OwnerEnum customer = OwnerEnum.CUSTOMER;
		FInvestigationMainlyReviewDO review = FInvestigationMainlyReviewDAO.findByFormIdAndOwner(
			oldFormId, customer.code());
		if (null == review) {
			logger.info("尽调预设:已经完成对尽职调查的复制:主体评价为null");
			return;
		}
		Date now = getSysdate();
		FInvestigationMainlyReviewDO mainlyReview = new FInvestigationMainlyReviewDO();
		BeanCopier.staticCopy(review, mainlyReview);
		if (null != fInvestigationDO) {
			mainlyReview.setProjectCode(fInvestigationDO.getProjectCode());
			mainlyReview.setProjectName(fInvestigationDO.getProjectName());
			mainlyReview.setCustomerId(fInvestigationDO.getCustomerId());
			mainlyReview.setCustomerName(fInvestigationDO.getCustomerName());
		}
		mainlyReview.setMReviewId(0L);
		mainlyReview.setFormId(newFormId);
		mainlyReview.setRawAddTime(now);
		CustomerDetailInfo customerInfo = customerServiceClient.queryByUserId(review
			.getCustomerId());
		if (update && customerInfo != null) {
			//客户信息取最新的
			mainlyReview.setEstablishedTime(customerInfo.getEstablishedTime());
			mainlyReview.setLegalPersion(customerInfo.getLegalPersion());
			mainlyReview.setLivingAddress(customerInfo.getLegalPersionAddress());
			mainlyReview.setBusiLicenseNo(customerInfo.getBusiLicenseNo());
			mainlyReview.setEnterpriseType(customerInfo.getEnterpriseType());
			mainlyReview.setWorkAddress(customerInfo.getAddress());
			// 设置是否三证合一
			mainlyReview.setIsOneCert(customerInfo.getIsOneCert());
			mainlyReview.setOrgCode(customerInfo.getOrgCode());
			mainlyReview.setTaxCertificateNo(customerInfo.getTaxCertificateNo());
			mainlyReview.setLocalTaxNo(customerInfo.getLocalTaxCertNo());
			mainlyReview.setBusiScope(customerInfo.getBusiScope());
			
			if (StringUtil.isNotBlank(customerInfo.getActualControlMan())) {
				mainlyReview.setActualControlPerson(customerInfo.getActualControlMan());
			}
			mainlyReview.setLoanCardNo(customerInfo.getLoanCardNo());
			mainlyReview.setLastCheckYear(customerInfo.getFinalYearCheck());
			mainlyReview.setLivingAddress(customerInfo.getLegalPersionAddress());
			
		}
		
		long MReviewId = FInvestigationMainlyReviewDAO.insert(mainlyReview);
		
		if (update && customerInfo != null) {
			//资质证书
			if (ListUtil.isNotEmpty(customerInfo.getCompanyQualification())) {
				int sortOrder = 1;
				for (CompanyQualificationInfo c : customerInfo.getCompanyQualification()) {
					FInvestigationMainlyReviewCertificateDO doObj = new FInvestigationMainlyReviewCertificateDO();
					doObj.setId(0L);
					doObj.setMReviewId(MReviewId);
					doObj.setCertificateCode(c.getQualificationCode());
					doObj.setCertificateName(c.getQualificationName());
					if (StringUtil.isNotBlank(c.getExperDate())) {
						doObj.setValidDate(DateUtil.parse(c.getExperDate()));
					}
					doObj.setOwner(customer.code());
					doObj.setRawAddTime(now);
					doObj.setSortOrder(sortOrder++);
					FInvestigationMainlyReviewCertificateDAO.insert(doObj);
				}
			}
			
			//基本开户行
			FInvestigationMainlyReviewBankInfoDO doObj1 = new FInvestigationMainlyReviewBankInfoDO();
			doObj1.setId(0L);
			doObj1.setMReviewId(MReviewId);
			doObj1.setBankName(customerInfo.getBankAccount());
			doObj1.setBankDesc("基本账户开户行");
			doObj1.setAccountNo(customerInfo.getAccountNo());
			doObj1.setBasicFlag("YES");
			doObj1.setRawAddTime(now);
			doObj1.setSortOrder(1);
			FInvestigationMainlyReviewBankInfoDAO.insert(doObj1);
			//开户行1
			FInvestigationMainlyReviewBankInfoDO doObj2 = new FInvestigationMainlyReviewBankInfoDO();
			doObj2.setId(0L);
			doObj2.setMReviewId(MReviewId);
			doObj2.setBankName(customerInfo.getSettleBankAccount1());
			doObj2.setBankDesc("主要结算账户开户行1");
			doObj2.setAccountNo(customerInfo.getSettleAccountNo1());
			doObj2.setBasicFlag("NO");
			doObj2.setRawAddTime(now);
			doObj2.setSortOrder(2);
			FInvestigationMainlyReviewBankInfoDAO.insert(doObj2);
			//开户行2
			FInvestigationMainlyReviewBankInfoDO doObj3 = new FInvestigationMainlyReviewBankInfoDO();
			doObj3.setId(0L);
			doObj3.setMReviewId(MReviewId);
			doObj3.setBankName(customerInfo.getSettleBankAccount2());
			doObj3.setBankDesc("主要结算账户开户行2");
			doObj3.setAccountNo(customerInfo.getSettleAccountNo2());
			doObj3.setBasicFlag("NO");
			doObj3.setRawAddTime(now);
			doObj3.setSortOrder(3);
			FInvestigationMainlyReviewBankInfoDAO.insert(doObj3);
			//开户行3
			FInvestigationMainlyReviewBankInfoDO doObj4 = new FInvestigationMainlyReviewBankInfoDO();
			doObj4.setId(0L);
			doObj4.setMReviewId(MReviewId);
			doObj4.setBankName(customerInfo.getSettleBankAccount3());
			doObj4.setBankDesc("其他结算账户开户行");
			doObj4.setAccountNo(customerInfo.getSettleAccountNo3());
			doObj4.setBasicFlag("NO");
			doObj4.setRawAddTime(now);
			doObj4.setSortOrder(4);
			FInvestigationMainlyReviewBankInfoDAO.insert(doObj4);
		} else {
			List<FInvestigationMainlyReviewCertificateDO> certificateDOs = FInvestigationMainlyReviewCertificateDAO
				.findByOwnerAndReviewId(customer.code(), review.getMReviewId());
			for (FInvestigationMainlyReviewCertificateDO certificate : certificateDOs) {
				FInvestigationMainlyReviewCertificateDO doObj = new FInvestigationMainlyReviewCertificateDO();
				BeanCopier.staticCopy(certificate, doObj);
				doObj.setId(0L);
				doObj.setMReviewId(MReviewId);
				doObj.setRawAddTime(now);
				FInvestigationMainlyReviewCertificateDAO.insert(doObj);
			}
			
			//开户情况
			List<FInvestigationMainlyReviewBankInfoDO> bankDOs = FInvestigationMainlyReviewBankInfoDAO
				.findByReviewId(review.getMReviewId());
			for (FInvestigationMainlyReviewBankInfoDO bank : bankDOs) {
				FInvestigationMainlyReviewBankInfoDO doObj = new FInvestigationMainlyReviewBankInfoDO();
				BeanCopier.staticCopy(bank, doObj);
				doObj.setId(0L);
				doObj.setMReviewId(MReviewId);
				doObj.setRawAddTime(now);
				FInvestigationMainlyReviewBankInfoDAO.insert(doObj);
			}
		}
		
		//主要股东情况
		List<FInvestigationMainlyReviewStockholderDO> holderDOs = FInvestigationMainlyReviewStockholderDAO
			.findByReviewId(review.getMReviewId());
		for (FInvestigationMainlyReviewStockholderDO holder : holderDOs) {
			FInvestigationMainlyReviewStockholderDO doObj = new FInvestigationMainlyReviewStockholderDO();
			BeanCopier.staticCopy(holder, doObj);
			doObj.setId(0L);
			doObj.setMReviewId(MReviewId);
			doObj.setRawAddTime(now);
			FInvestigationMainlyReviewStockholderDAO.insert(doObj);
		}
		
		//实际控制人或自然人股东个人资产、负债状况（非国有必填）
		List<FInvestigationMainlyReviewAssetAndLiabilityDO> assetAndLiabilitiesDOs = FInvestigationMainlyReviewAssetAndLiabilityDAO
			.findByReviewId(review.getMReviewId());
		for (FInvestigationMainlyReviewAssetAndLiabilityDO assetAndLiability : assetAndLiabilitiesDOs) {
			FInvestigationMainlyReviewAssetAndLiabilityDO doObj = new FInvestigationMainlyReviewAssetAndLiabilityDO();
			BeanCopier.staticCopy(assetAndLiability, doObj);
			doObj.setId(0L);
			doObj.setMReviewId(MReviewId);
			doObj.setRawAddTime(now);
			FInvestigationMainlyReviewAssetAndLiabilityDAO.insert(doObj);
		}
		
		List<FInvestigationMainlyReviewRelatedCompanyDO> companies = FInvestigationMainlyReviewRelatedCompanyDAO
			.findByReviewId(review.getMReviewId());
		for (FInvestigationMainlyReviewRelatedCompanyDO company : companies) {
			FInvestigationMainlyReviewRelatedCompanyDO doObj = new FInvestigationMainlyReviewRelatedCompanyDO();
			BeanCopier.staticCopy(company, doObj);
			doObj.setId(0L);
			doObj.setMReviewId(MReviewId);
			doObj.setRawAddTime(now);
			FInvestigationMainlyReviewRelatedCompanyDAO.insert(doObj);
		}
		
		List<FInvestigationMainlyReviewCreditStatusDO> creditStatusDOs = FInvestigationMainlyReviewCreditStatusDAO
			.findByReviewId(review.getMReviewId());
		for (FInvestigationMainlyReviewCreditStatusDO creditStatus : creditStatusDOs) {
			FInvestigationMainlyReviewCreditStatusDO doObj = new FInvestigationMainlyReviewCreditStatusDO();
			BeanCopier.staticCopy(creditStatus, doObj);
			doObj.setId(0L);
			doObj.setMReviewId(MReviewId);
			doObj.setRawAddTime(now);
			FInvestigationMainlyReviewCreditStatusDAO.insert(doObj);
		}
		
		//对外担保的情况
		List<FInvestigationMainlyReviewExternalGuaranteeDO> guaranteeDOs = FInvestigationMainlyReviewExternalGuaranteeDAO
			.findByReviewId(review.getMReviewId());
		for (FInvestigationMainlyReviewExternalGuaranteeDO guarantee : guaranteeDOs) {
			FInvestigationMainlyReviewExternalGuaranteeDO doObj = new FInvestigationMainlyReviewExternalGuaranteeDO();
			BeanCopier.staticCopy(guarantee, doObj);
			doObj.setId(0L);
			doObj.setMReviewId(MReviewId);
			doObj.setRawAddTime(now);
			FInvestigationMainlyReviewExternalGuaranteeDAO.insert(doObj);
		}
		
		//其他信用信息（工商、税务、诉讼、环保等信息）
		List<FInvestigationMainlyReviewCreditInfoDO> creditDOs = FInvestigationMainlyReviewCreditInfoDAO
			.findByReviewId(review.getMReviewId());
		for (FInvestigationMainlyReviewCreditInfoDO credit : creditDOs) {
			FInvestigationMainlyReviewCreditInfoDO doObj = new FInvestigationMainlyReviewCreditInfoDO();
			BeanCopier.staticCopy(credit, doObj);
			doObj.setId(0L);
			doObj.setMReviewId(MReviewId);
			doObj.setRawAddTime(now);
			FInvestigationMainlyReviewCreditInfoDAO.insert(doObj);
		}
		
		//复制附件
		copyAttachments(oldFormId + "_index1", CommonAttachmentTypeEnum.FORM_ATTACH.code(),
			newFormId + "_index1", fInvestigationDO.getProjectCode(), "尽调-客户主体评价附件");
		logger.info("尽调预设:已经完成对尽职调查的复制:主体评价");
	}
	
	//管理能力评价
	private void copyMabilityReview(long oldFormId, long newFormId,
									FInvestigationDO fInvestigationDO) {
		FInvestigationMabilityReviewDO review = FInvestigationMabilityReviewDAO
			.findByFormId(oldFormId);
		if (null == review) {
			logger.info("尽调预设:已经完成对尽职调查的复制:管理能力评价为null");
			return;
		}
		Date now = getSysdate();
		FInvestigationMabilityReviewDO mability = new FInvestigationMabilityReviewDO();
		BeanCopier.staticCopy(review, mability);
		mability.setMaReviewId(0L);
		mability.setFormId(newFormId);
		if (null != fInvestigationDO) {
			mability.setProjectCode(fInvestigationDO.getProjectCode());
			mability.setProjectName(fInvestigationDO.getProjectName());
			mability.setCustomerId(fInvestigationDO.getCustomerId());
			mability.setCustomerName(fInvestigationDO.getCustomerName());
		}
		mability.setRawAddTime(now);
		long maReviewId = FInvestigationMabilityReviewDAO.insert(mability);
		
		OwnerEnum ownerEnum = OwnerEnum.CUSTOMER;
		String owner = ownerEnum.code();
		List<FInvestigationMabilityReviewLeadingTeamDO> leadingTeamDOs = FInvestigationMabilityReviewLeadingTeamDAO
			.findByOwnerAndReviewId(owner, review.getMaReviewId());
		for (FInvestigationMabilityReviewLeadingTeamDO leadingTeam : leadingTeamDOs) {
			FInvestigationMabilityReviewLeadingTeamDO doObj = new FInvestigationMabilityReviewLeadingTeamDO();
			BeanCopier.staticCopy(leadingTeam, doObj);
			doObj.setId(0L);
			doObj.setMaReviewId(maReviewId);
			doObj.setRawAddTime(now);
			long tid = FInvestigationMabilityReviewLeadingTeamDAO.insert(doObj);
			List<FInvestigationMabilityReviewLeadingTeamResumeDO> resumes = FInvestigationMabilityReviewLeadingTeamResumeDAO
				.findByReviewIdOwnerTid(review.getMaReviewId(), owner, leadingTeam.getId());
			if (ListUtil.isNotEmpty(resumes)) {
				for (FInvestigationMabilityReviewLeadingTeamResumeDO resume : resumes) {
					FInvestigationMabilityReviewLeadingTeamResumeDO doObj2 = new FInvestigationMabilityReviewLeadingTeamResumeDO();
					BeanCopier.staticCopy(resume, doObj2);
					doObj2.setId(0L);
					doObj2.setMaReviewId(maReviewId);
					doObj2.setTid(tid);
					doObj2.setRawAddTime(now);
					FInvestigationMabilityReviewLeadingTeamResumeDAO.insert(doObj2);
				}
			}
		}
		
		//复制附件
		copyAttachments(oldFormId + "_index2", CommonAttachmentTypeEnum.FORM_ATTACH.code(),
			newFormId + "_index2", fInvestigationDO.getProjectCode(), "尽调客户管理能力评价附件");
		logger.info("尽调预设:已经完成对尽职调查的复制:管理能力评价");
	}
	
	//客户经营能力评价
	private void copyOpabilityReview(long oldFormId, long newFormId,
										FInvestigationDO fInvestigationDO) {
		FInvestigationOpabilityReviewDO review = FInvestigationOpabilityReviewDAO
			.findByFormId(oldFormId);
		if (null == review) {
			logger.info("尽调预设:已经完成对尽职调查的复制:客户经营能力评价为null");
			return;
		}
		Date now = getSysdate();
		FInvestigationOpabilityReviewDO opability = new FInvestigationOpabilityReviewDO();
		BeanCopier.staticCopy(review, opability);
		opability.setOpReviewId(0L);
		opability.setFormId(newFormId);
		if (null != fInvestigationDO) {
			opability.setProjectCode(fInvestigationDO.getProjectCode());
			opability.setProjectName(fInvestigationDO.getProjectName());
			opability.setCustomerId(fInvestigationDO.getCustomerId());
			opability.setCustomerName(fInvestigationDO.getCustomerName());
		}
		opability.setRawAddTime(now);
		long opReviewId = FInvestigationOpabilityReviewDAO.insert(opability);
		
		//客户主要上下游情况（余额前五大）（城投类客户根据实际情况可不填写）
		List<FInvestigationOpabilityReviewUpdownStreamDO> updownStreamDOs = FInvestigationOpabilityReviewUpdownStreamDAO
			.findByReviewId(review.getOpReviewId());
		for (FInvestigationOpabilityReviewUpdownStreamDO stream : updownStreamDOs) {
			FInvestigationOpabilityReviewUpdownStreamDO doObj = new FInvestigationOpabilityReviewUpdownStreamDO();
			BeanCopier.staticCopy(stream, doObj);
			doObj.setId(0L);
			doObj.setOpReviewId(opReviewId);
			doObj.setRawAddTime(now);
			FInvestigationOpabilityReviewUpdownStreamDAO.insert(doObj);
		}
		
		//客户主要产品结构、下游主要客户情况（城投类客户根据实际情况可不填写）
		List<FInvestigationOpabilityReviewProductStructureDO> productStructureDOs = FInvestigationOpabilityReviewProductStructureDAO
			.findByReviewId(review.getOpReviewId());
		for (FInvestigationOpabilityReviewProductStructureDO structure : productStructureDOs) {
			FInvestigationOpabilityReviewProductStructureDO doObj = new FInvestigationOpabilityReviewProductStructureDO();
			BeanCopier.staticCopy(structure, doObj);
			doObj.setId(0L);
			doObj.setOpReviewId(opReviewId);
			doObj.setRawAddTime(now);
			FInvestigationOpabilityReviewProductStructureDAO.insert(doObj);
		}
		
		//复制附件
		copyAttachments(oldFormId + "_index3", CommonAttachmentTypeEnum.FORM_ATTACH.code(),
			newFormId + "_index3", fInvestigationDO.getProjectCode(), "尽调-客户经营能力评价附");
		logger.info("尽调预设:已经完成对尽职调查的复制:客户经营能力评价");
	}
	
	//客户财务评价
	private void copyFinancialReview(long oldFormId, long newFormId,
										FInvestigationDO fInvestigationDO) {
		//		FInvestigationFinancialReviewDO review = findFinancial(oldFormId);
		List<FInvestigationFinancialReviewDO> reviews = FInvestigationFinancialReviewDAO
			.findByFormId(oldFormId);
		for (FInvestigationFinancialReviewDO review : reviews) {
			if (null == review) {
				logger.info("尽调预设:已经完成对尽职调查的复制:客户财务评价为null");
				return;
			}
			Date now = getSysdate();
			FInvestigationFinancialReviewDO financial = new FInvestigationFinancialReviewDO();
			BeanCopier.staticCopy(review, financial);
			financial.setFReviewId(0L);
			financial.setFormId(newFormId);
			if (null != fInvestigationDO) {
				financial.setProjectCode(fInvestigationDO.getProjectCode());
				financial.setProjectName(fInvestigationDO.getProjectName());
				financial.setCustomerId(fInvestigationDO.getCustomerId());
				financial.setCustomerName(fInvestigationDO.getCustomerName());
			}
			financial.setRawAddTime(now);
			long FReviewId = FInvestigationFinancialReviewDAO.insert(financial);
			
			List<FInvestigationFinancialReviewKpiDO> financialKpies = FInvestigationFinancialReviewKpiDAO
				.findByReviewId(review.getFReviewId());
			
			//		Map<Long, Long> map = new HashMap<>();
			//		List<FInvestigationFinancialReviewKpiDO> datas = new ArrayList<>();
			for (FInvestigationFinancialReviewKpiDO kpi : financialKpies) {
				if (kpi.getParentId() > 0) {
					//				datas.add(kpi);
					continue;
				}
				
				FInvestigationFinancialReviewKpiDO doObj = new FInvestigationFinancialReviewKpiDO();
				BeanCopier.staticCopy(kpi, doObj);
				doObj.setId(0L);
				doObj.setFReviewId(FReviewId);
				doObj.setRawAddTime(now);
				long id = FInvestigationFinancialReviewKpiDAO.insert(doObj);
				if (KpiTypeEnum.FINANCIAL_DATA.code().equals(kpi.getKpiType())) {
					List<FInvestigationFinancialReviewKpiDO> subDatas = FInvestigationFinancialReviewKpiDAO
						.findByParentId(kpi.getId());
					if (ListUtil.isNotEmpty(subDatas)) {
						for (FInvestigationFinancialReviewKpiDO sub : subDatas) {
							FInvestigationFinancialReviewKpiDO copySub = new FInvestigationFinancialReviewKpiDO();
							BeanCopier.staticCopy(sub, copySub);
							copySub.setId(0L);
							copySub.setFReviewId(FReviewId);
							copySub.setParentId(id);
							copySub.setRawAddTime(now);
							FInvestigationFinancialReviewKpiDAO.insert(copySub);
						}
					}
					//				map.put(kpi.getId(), id);
				}
			}
			
			// 数据解释说明的parentId需要重新设置
			//		if (ListUtil.isNotEmpty(datas)) {
			//			for (FInvestigationFinancialReviewKpiDO kpi : datas) {
			//				Long parentId = map.get(kpi.getParentId());
			//				if (null == parentId) {
			//					continue;
			//				}
			//				FInvestigationFinancialReviewKpiDO doObj = new FInvestigationFinancialReviewKpiDO();
			//				BeanCopier.staticCopy(kpi, doObj);
			//				doObj.setId(0L);
			//				doObj.setFReviewId(FReviewId);
			//				doObj.setParentId(parentId);
			//				doObj.setRawAddTime(now);
			//				FInvestigationFinancialReviewKpiDAO.insert(doObj);
			//			}
			//		}
			
			String suffix = "";
			if (review.getSubIndex() == 1) {
				suffix = "_" + review.getSubIndex();
			}
			//复制附件
			copyAttachments(oldFormId + "_index4" + suffix, CommonAttachmentTypeEnum.FORM_ATTACH.code(),
				newFormId + "_index4", fInvestigationDO.getProjectCode(), "尽调-客户财务评价附件");
			copyAttachments(oldFormId + suffix, CommonAttachmentTypeEnum.INVESTIGATION_FINANCIAL.code(),
				newFormId + suffix, fInvestigationDO.getProjectCode(), CommonAttachmentTypeEnum.INVESTIGATION_FINANCIAL.message());
		}
		logger.info("尽调预设:已经完成对尽职调查的复制:客户财务评价");
	}
	
	//重大事项调查
	private void copyMajorEvent(long oldFormId, long newFormId, FInvestigationDO fInvestigationDO) {
		FInvestigationMajorEventDO event = FInvestigationMajorEventDAO.findByFormId(oldFormId);
		if (null == event) {
			logger.info("尽调预设:已经完成对尽职调查的复制:重大事项调查为null");
			return;
		}
		FInvestigationMajorEventDO majorEvent = new FInvestigationMajorEventDO();
		BeanCopier.staticCopy(event, majorEvent);
		majorEvent.setId(0L);
		majorEvent.setFormId(newFormId);
		if (null != fInvestigationDO) {
			majorEvent.setProjectCode(fInvestigationDO.getProjectCode());
			majorEvent.setProjectName(fInvestigationDO.getProjectName());
			majorEvent.setCustomerId(fInvestigationDO.getCustomerId());
			majorEvent.setCustomerName(fInvestigationDO.getCustomerName());
		}
		majorEvent.setRawAddTime(getSysdate());
		FInvestigationMajorEventDAO.insert(majorEvent);
		
		//复制附件
		copyAttachments(oldFormId + "_index5", CommonAttachmentTypeEnum.FORM_ATTACH.code(),
			newFormId + "_index5", fInvestigationDO.getProjectCode(), "尽调-重大事项调查附件");
		logger.info("尽调预设:已经完成对尽职调查的复制:重大事项调查");
	}
	
	//项目情况调查
	private void copyProjectStatus(long oldFormId, long newFormId, FInvestigationDO fInvestigationDO) {
		Date now = getSysdate();
		FInvestigationProjectStatusDO status = FInvestigationProjectStatusDAO
			.findByFormId(oldFormId);
		if (null != status) {
			FInvestigationProjectStatusDO projectStatus = new FInvestigationProjectStatusDO();
			BeanCopier.staticCopy(status, projectStatus);
			projectStatus.setStatusId(0L);
			projectStatus.setFormId(newFormId);
			if (null != fInvestigationDO) {
				projectStatus.setProjectCode(fInvestigationDO.getProjectCode());
				projectStatus.setProjectName(fInvestigationDO.getProjectName());
				projectStatus.setCustomerId(fInvestigationDO.getCustomerId());
				projectStatus.setCustomerName(fInvestigationDO.getCustomerName());
			}
			projectStatus.setRawAddTime(now);
			long statusId = FInvestigationProjectStatusDAO.insert(projectStatus);
			
			//项目投资与资金筹措
			List<FInvestigationProjectStatusFundDO> fundDOs = FInvestigationProjectStatusFundDAO
				.findByStatusId(status.getStatusId());
			for (FInvestigationProjectStatusFundDO fund : fundDOs) {
				FInvestigationProjectStatusFundDO doObj = new FInvestigationProjectStatusFundDO();
				BeanCopier.staticCopy(fund, doObj);
				doObj.setId(0L);
				doObj.setStatusId(statusId);
				doObj.setRawAddTime(now);
				FInvestigationProjectStatusFundDAO.insert(doObj);
			}
		} else {
			FInvestigationProjectStatusDO newStatus = new FInvestigationProjectStatusDO();
			newStatus.setFormId(newFormId);
			if (null != fInvestigationDO) {
				newStatus.setProjectCode(fInvestigationDO.getProjectCode());
				newStatus.setProjectName(fInvestigationDO.getProjectName());
				newStatus.setCustomerId(fInvestigationDO.getCustomerId());
				newStatus.setCustomerName(fInvestigationDO.getCustomerName());
			}
			newStatus.setRawAddTime(now);
			FInvestigationProjectStatusDAO.insert(newStatus);
		}
		
		//复制附件
		copyAttachments(oldFormId + "", CommonAttachmentTypeEnum.INVESTIGATION_6.code(), newFormId
																							+ "",
			fInvestigationDO.getProjectCode(), "尽调-项目情况调查附件");
		//复制附件
		copyAttachments(oldFormId + "_index6", CommonAttachmentTypeEnum.FORM_ATTACH.code(),
			newFormId + "_index6", fInvestigationDO.getProjectCode(), "尽调-项目情况调查表单附件");
		logger.info("尽调预设:已经完成对尽职调查的复制:项目情况调查");
	}
	
	//授信方案合理性评价
	private void copyCsRationalityReview(long oldFormId, long newFormId,
											FInvestigationDO fInvestigationDO) {
		FInvestigationCsRationalityReviewDO review = FInvestigationCsRationalityReviewDAO
			.findByFormId(oldFormId);
		if (null == review) {
			logger.info("尽调预设:已经完成对尽职调查的复制:授信方案合理性评价为null");
			return;
		}
		Date now = getSysdate();
		FInvestigationCsRationalityReviewDO cs = new FInvestigationCsRationalityReviewDO();
		BeanCopier.staticCopy(review, cs);
		cs.setCsrReviewId(0L);
		cs.setFormId(newFormId);
		if (null != fInvestigationDO) {
			cs.setProjectCode(fInvestigationDO.getProjectCode());
			cs.setProjectName(fInvestigationDO.getProjectName());
			cs.setCustomerId(fInvestigationDO.getCustomerId());
			cs.setCustomerName(fInvestigationDO.getCustomerName());
		}
		cs.setRawAddTime(now);
		long csrReviewId = FInvestigationCsRationalityReviewDAO.insert(cs);
		
		FInvestigationCsRationalityReviewInfo reviewInfo = new FInvestigationCsRationalityReviewInfo();
		BeanCopier.staticCopy(review, reviewInfo);
		
		// 复制数据 
		OwnerEnum owner = OwnerEnum.GUARANTOR_NEW;
		
		List<FInvestigationCsRationalityReviewGuarantorDO> guarantors = FInvestigationCsRationalityReviewGuarantorDAO
			.findByFormId(oldFormId);
		if (ListUtil.isNotEmpty(guarantors)) {
			for (FInvestigationCsRationalityReviewGuarantorDO guarantor : guarantors) {
				FInvestigationCsRationalityReviewGuarantorDO doObj1 = new FInvestigationCsRationalityReviewGuarantorDO();
				BeanCopier.staticCopy(guarantor, doObj1);
				doObj1.setFormId(newFormId);
				doObj1.setGuarantorId(0L);
				doObj1.setRawAddTime(now);
				long guarantorId = FInvestigationCsRationalityReviewGuarantorDAO.insert(doObj1);
				
				//已获得的资质证书
				List<FInvestigationMainlyReviewCertificateDO> certificateDOs = FInvestigationMainlyReviewCertificateDAO
					.findByOwnerAndReviewId(owner.code(), guarantor.getGuarantorId());
				if (ListUtil.isNotEmpty(certificateDOs)) {
					for (FInvestigationMainlyReviewCertificateDO certificate : certificateDOs) {
						FInvestigationMainlyReviewCertificateDO doObj2 = new FInvestigationMainlyReviewCertificateDO();
						BeanCopier.staticCopy(certificate, doObj2);
						doObj2.setId(0L);
						doObj2.setMReviewId(guarantorId);
						doObj2.setRawAddTime(now);
						FInvestigationMainlyReviewCertificateDAO.insert(doObj2);
					}
				}
				
				//高管人员
				List<FInvestigationMabilityReviewLeadingTeamDO> leadingTeamDOs = FInvestigationMabilityReviewLeadingTeamDAO
					.findByOwnerAndReviewId(owner.code(), guarantor.getGuarantorId());
				if (ListUtil.isNotEmpty(leadingTeamDOs)) {
					for (FInvestigationMabilityReviewLeadingTeamDO leadingTeam : leadingTeamDOs) {
						FInvestigationMabilityReviewLeadingTeamDO doObj2 = new FInvestigationMabilityReviewLeadingTeamDO();
						BeanCopier.staticCopy(leadingTeam, doObj2);
						doObj2.setId(0L);
						doObj2.setMaReviewId(guarantorId);
						doObj2.setRawAddTime(now);
						long tid = FInvestigationMabilityReviewLeadingTeamDAO.insert(doObj2);
						List<FInvestigationMabilityReviewLeadingTeamResumeDO> resumes = FInvestigationMabilityReviewLeadingTeamResumeDAO
							.findByReviewIdOwnerTid(guarantor.getGuarantorId(), owner.code(),
								leadingTeam.getId());
						if (ListUtil.isNotEmpty(resumes)) {
							for (FInvestigationMabilityReviewLeadingTeamResumeDO resume : resumes) {
								FInvestigationMabilityReviewLeadingTeamResumeDO doObj3 = new FInvestigationMabilityReviewLeadingTeamResumeDO();
								BeanCopier.staticCopy(resume, doObj3);
								doObj3.setId(0L);
								doObj3.setMaReviewId(guarantorId);
								doObj3.setTid(tid);
								doObj3.setRawAddTime(now);
								FInvestigationMabilityReviewLeadingTeamResumeDAO.insert(doObj3);
							}
						}
					}
				}
				
				//保证人主要财务指标
				List<FFinancialKpiDO> kpies = FFinancialKpiDAO.findByFormIdAndKpitype(
					guarantor.getGuarantorId(), KpiTypeEnum.CS_LEGAL_PERSON.code());
				if (ListUtil.isNotEmpty(kpies)) {
					for (FFinancialKpiDO kpi : kpies) {
						FFinancialKpiDO doObj = new FFinancialKpiDO();
						BeanCopier.staticCopy(kpi, doObj);
						doObj.setKpiId(0L);
						doObj.setFormId(guarantorId);
						doObj.setRawAddTime(now);
						FFinancialKpiDAO.insert(doObj);
					}
				}
			}
		}
		
		//保证人保证能力总体评价（非必填）
		List<FInvestigationCsRationalityReviewGuarantorAbilityDO> guarantorAbilitieDOs = FInvestigationCsRationalityReviewGuarantorAbilityDAO
			.findByReviewId(review.getCsrReviewId());
		for (FInvestigationCsRationalityReviewGuarantorAbilityDO ability : guarantorAbilitieDOs) {
			FInvestigationCsRationalityReviewGuarantorAbilityDO doObj = new FInvestigationCsRationalityReviewGuarantorAbilityDO();
			BeanCopier.staticCopy(ability, doObj);
			doObj.setId(0L);
			doObj.setCsrReviewId(csrReviewId);
			doObj.setRawAddTime(now);
			FInvestigationCsRationalityReviewGuarantorAbilityDAO.insert(doObj);
		}
		
		//复制附件
		copyAttachments(oldFormId + "_index7", CommonAttachmentTypeEnum.FORM_ATTACH.code(),
			newFormId + "_index7", fInvestigationDO.getProjectCode(), "尽调-授信方案合理性评价附件");
		logger.info("尽调预设:已经完成对尽职调查的复制:授信方案合理性评价");
	}
	
	//风险点分析和结论意见
	private void copyRisk(long oldFormId, long newFormId, FInvestigationDO fInvestigationDO) {
		FInvestigationRiskDO risk = FInvestigationRiskDAO.findByFormId(oldFormId);
		if (null == risk) {
			logger.info("尽调预设:已经完成对尽职调查的复制:风险点分析和结论意见为null");
			return;
		}
		FInvestigationRiskDO newRisk = new FInvestigationRiskDO();
		BeanCopier.staticCopy(risk, newRisk);
		newRisk.setRiskId(0L);
		newRisk.setFormId(newFormId);
		if (null != fInvestigationDO) {
			newRisk.setProjectCode(fInvestigationDO.getProjectCode());
			newRisk.setProjectName(fInvestigationDO.getProjectName());
			newRisk.setCustomerId(fInvestigationDO.getCustomerId());
			newRisk.setCustomerName(fInvestigationDO.getCustomerName());
		}
		newRisk.setRawAddTime(getSysdate());
		FInvestigationRiskDAO.insert(newRisk);
		
		//复制附件
		copyAttachments(oldFormId + "_index8", CommonAttachmentTypeEnum.FORM_ATTACH.code(),
			newFormId + "_index8", fInvestigationDO.getProjectCode(), "尽调-风险点分析和结论意见附件");
		logger.info("尽调预设:已经完成对尽职调查的复制:风险点分析和结论意见");
	}
	
	@Override
	public long investigationUseAssetCount(long assetsId) {
		long temp = 0;
		try {
			Map<String, Object> paramMap = new HashMap<>();
			if (assetsId > 0) {
				paramMap.put("assetsId", assetsId);
			}
			
			temp = busiDAO.investigationUseAssetCount(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询资产出错");
		}
		return temp;
	}
	
	@Override
	public FcsBaseResult updateCustomerInfo(final UpdateInvestigationCustomerOrder order) {
		return commonProcess(order, "更新尽调客户信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				if (order.getFormId() <= 0) {
					logger.info("更新尽调客户信息：无需更新，formId <= 0");
					return null;
				}
				
				FInvestigationDO investigation = FInvestigationDAO.findByFormId(order.getFormId());
				if (null == investigation) {
					logger.info("更新尽调客户信息：无需更新，没有找到尽调报告[" + order.getFormId() + "]");
					return null;
				}
				
				CustomerDetailInfo customerInfo = customerServiceClient.queryByUserId(investigation
					.getCustomerId());
				if (null == customerInfo) {
					logger.info("更新尽调客户信息：无需更新，没有找到客户信息[" + investigation.getCustomerId() + "]");
					return null;
				}
				
				if (order.getToIndex() == 99) {
					//更新声明的客户信息
					if (!StringUtil.equals(investigation.getCustomerName(),
						customerInfo.getCustomerName())) {
						investigation.setCustomerName(customerInfo.getCustomerName());
						FInvestigationDAO.update(investigation);
					} else {
						logger.info("更新尽调客户信息：无需更新，声明的客户名称没有变化[" + order.getFormId() + "]");
					}
					return null;
				}
				
				if (ProjectUtil.isLitigation(investigation.getBusiType())) {
					//诉保类
					FInvestigationLitigationDO litigation = FInvestigationLitigationDAO
						.findByFormId(order.getFormId());
					if (null == litigation) {
						logger.info("更新尽调客户信息：无需更新，没有创建客户信息[" + order.getFormId() + "]");
						return null;
					}
					if (StringUtil.equals(customerInfo.getCustomerType(),
						CustomerTypeEnum.PERSIONAL.code())) {
						if (StringUtil.isNotBlank(customerInfo.getCustomerName())) {
							litigation.setContactPerson(customerInfo.getCustomerName());
						}
					} else {
						if (StringUtil.isNotBlank(customerInfo.getContactMan())) {
							litigation.setContactPerson(customerInfo.getContactMan());
						}
					}
					litigation.setContactNo(customerInfo.getContactNo());
					FInvestigationLitigationDAO.update(litigation);
				} else if (ProjectUtil.isUnderwriting(investigation.getBusiType())) {
					logger.info("更新尽调客户信息：此类型尽调无需更新，[" + order.getFormId() + "]");
				} else {
					if (order.getToIndex() == 0) {
						FInvestigationCreditSchemeDO scheme = FInvestigationCreditSchemeDAO
							.findByFormId(order.getFormId());
						if (null == scheme) {
							logger.info("更新尽调客户信息：无需更新，没有创建授信方案信息[" + order.getFormId() + "]");
							return null;
						}
						
						if (StringUtil.isNotBlank(customerInfo.getCustomerName())) {
							scheme.setCustomerName(customerInfo.getCustomerName());
						}
						if (StringUtil.isNotBlank(customerInfo.getIndustryCode())) {
							scheme.setIndustryCode(customerInfo.getIndustryCode());
						}
						if (StringUtil.isNotBlank(customerInfo.getIndustryName())) {
							scheme.setIndustryName(customerInfo.getIndustryName());
						}
						FInvestigationCreditSchemeDAO.update(scheme);
					} else if (order.getToIndex() == 1) {
						//担保委贷类
						FInvestigationMainlyReviewDO review = FInvestigationMainlyReviewDAO
							.findByFormId(order.getFormId());
						if (null == review) {
							logger.info("更新尽调客户信息：无需更新，没有创建客户主体评价信息[" + order.getFormId() + "]");
							return null;
						}
						
						review.setCustomerId(investigation.getCustomerId());
						if (null != customerInfo.getEstablishedTime()) {
							review.setEstablishedTime(customerInfo.getEstablishedTime());
						}
						if (StringUtil.isNotBlank(customerInfo.getLegalPersion())) {
							review.setLegalPersion(customerInfo.getLegalPersion());
						}
						if (StringUtil.isNotBlank(customerInfo.getLegalPersionAddress())) {
							review.setLivingAddress(customerInfo.getLegalPersionAddress());
						}
						if (StringUtil.isNotBlank(customerInfo.getBusiLicenseNo())) {
							review.setBusiLicenseNo(customerInfo.getBusiLicenseNo());
						}
						if (StringUtil.isNotBlank(customerInfo.getEnterpriseType())) {
							review.setEnterpriseType(customerInfo.getEnterpriseType());
						}
						if (StringUtil.isNotBlank(customerInfo.getAddress())) {
							review.setWorkAddress(customerInfo.getAddress());
						}
						// 设置是否三证合一
						if (StringUtil.isNotBlank(customerInfo.getIsOneCert())) {
							review.setIsOneCert(customerInfo.getIsOneCert());
						}
						if (StringUtil.isNotBlank(customerInfo.getOrgCode())) {
							review.setOrgCode(customerInfo.getOrgCode());
						}
						if (StringUtil.isNotBlank(customerInfo.getTaxCertificateNo())) {
							review.setTaxCertificateNo(customerInfo.getTaxCertificateNo());
						}
						if (StringUtil.isNotBlank(customerInfo.getLocalTaxCertNo())) {
							review.setLocalTaxNo(customerInfo.getLocalTaxCertNo());
						}
						if (StringUtil.isNotBlank(customerInfo.getBusiScope())) {
							review.setBusiScope(customerInfo.getBusiScope());
						}
						
						if (StringUtil.isNotBlank(customerInfo.getActualControlMan())) {
							review.setActualControlPerson(customerInfo.getActualControlMan());
						}
						if (StringUtil.isNotBlank(customerInfo.getLoanCardNo())) {
							review.setLoanCardNo(customerInfo.getLoanCardNo());
						}
						if (StringUtil.isNotBlank(customerInfo.getFinalYearCheck())) {
							review.setLastCheckYear(customerInfo.getFinalYearCheck());
						}
						if (StringUtil.isNotBlank(customerInfo.getLegalPersionAddress())) {
							review.setLivingAddress(customerInfo.getLegalPersionAddress());
						}
						FInvestigationMainlyReviewDAO.update(review);
						
						OwnerEnum owner = OwnerEnum.CUSTOMER;
						//资质证书
						if (ListUtil.isNotEmpty(customerInfo.getCompanyQualification())) {
							List<FInvestigationMainlyReviewCertificateOrder> certificateOrders = new ArrayList<>();
							for (CompanyQualificationInfo c : customerInfo
								.getCompanyQualification()) {
								FInvestigationMainlyReviewCertificateOrder order1 = new FInvestigationMainlyReviewCertificateOrder();
								order1.setCertificateCode(c.getQualificationCode());
								order1.setCertificateName(c.getQualificationName());
								if (StringUtil.isNotBlank(c.getExperDate())) {
									order1.setValidDate(DateUtil.parse(c.getExperDate()));
								}
								certificateOrders.add(order1);
							}
							//保存资质证书
							saveCertificates(review.getMReviewId(), owner, certificateOrders);
						}
						
						//开户行信息
						List<FInvestigationMainlyReviewBankInfoOrder> bankOrders = new ArrayList<>();
						//基本开户行
						if (StringUtil.isBlank(customerInfo.getBankAccount())
							&& StringUtil.isBlank(customerInfo.getAccountNo())) {
							bankOrders.add(null);
						} else {
							FInvestigationMainlyReviewBankInfoOrder bank1 = new FInvestigationMainlyReviewBankInfoOrder();
							bank1.setBankName(customerInfo.getBankAccount());
							bank1.setBankDesc("基本账户开户行");
							bank1.setAccountNo(customerInfo.getAccountNo());
							bank1.setBasicFlag("YES");
							bankOrders.add(bank1);
						}
						//开户行1
						if (StringUtil.isBlank(customerInfo.getSettleBankAccount1())
							&& StringUtil.isBlank(customerInfo.getSettleAccountNo1())) {
							bankOrders.add(null);
						} else {
							FInvestigationMainlyReviewBankInfoOrder bank2 = new FInvestigationMainlyReviewBankInfoOrder();
							bank2.setBankName(customerInfo.getSettleBankAccount1());
							bank2.setBankDesc("主要结算账户开户行1");
							bank2.setAccountNo(customerInfo.getSettleAccountNo1());
							bank2.setBasicFlag("NO");
							bankOrders.add(bank2);
						}
						//开户行2
						if (StringUtil.isBlank(customerInfo.getSettleBankAccount2())
							&& StringUtil.isBlank(customerInfo.getSettleAccountNo2())) {
							bankOrders.add(null);
						} else {
							FInvestigationMainlyReviewBankInfoOrder bank3 = new FInvestigationMainlyReviewBankInfoOrder();
							bank3.setBankName(customerInfo.getSettleBankAccount2());
							bank3.setBankDesc("主要结算账户开户行2");
							bank3.setAccountNo(customerInfo.getSettleAccountNo2());
							bank3.setBasicFlag("NO");
							bankOrders.add(bank3);
						}
						//开户行3
						if (StringUtil.isBlank(customerInfo.getSettleBankAccount3())
							&& StringUtil.isBlank(customerInfo.getSettleAccountNo3())) {
							bankOrders.add(null);
						} else {
							FInvestigationMainlyReviewBankInfoOrder bank4 = new FInvestigationMainlyReviewBankInfoOrder();
							bank4.setBankName(customerInfo.getSettleBankAccount3());
							bank4.setBankDesc("其他结算账户开户行");
							bank4.setAccountNo(customerInfo.getSettleAccountNo3());
							bank4.setBasicFlag("NO");
							bankOrders.add(bank4);
						}
						//保存开户情况
						saveBanks(review.getMReviewId(), bankOrders);
					}
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FcsBaseResult createChecking(final InvestigationCheckingOrder order) {
		return commonProcess(order, "审核修改-获取修改后的表单ID", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				//取得表单信息
				FormDO form = formDAO.findByFormId(order.getFormId());
				if (null == form) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				FInvestigationCheckingDO checkDO = FInvestigationCheckingDAO
					.findByRelatedFormIdAndCheckPoint(order.getFormId(), order.getCheckPoint());
				if (null != checkDO) {
					FcsBaseResult result = (FcsBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					result.setKeyId(checkDO.getFormId());
				} else {
					FormDO newForm = new FormDO();
					BeanCopier.staticCopy(form, newForm);
					newForm.setFormId(0L);
					newForm.setRawAddTime(getSysdate());
					newForm.setRawUpdateTime(null);
					//历史数据标记为删除状态，以防被列表查询出来
					newForm.setStatus(FormStatusEnum.DELETED.code());
					long newFormId = formDAO.insert(newForm);
					
					checkDO = new FInvestigationCheckingDO();
					BeanCopier.staticCopy(order, checkDO);
					checkDO.setFormCode(form.getCheckStatus().replaceAll("1", "0"));
					checkDO.setUserId(null == order.getUserId() ? 0L : order.getUserId()
						.longValue());
					checkDO.setFormId(newFormId); //源表单ID
					checkDO.setRelatedFormId(order.getFormId());
					checkDO.setRawAddTime(newForm.getRawAddTime());
					FInvestigationCheckingDAO.insert(checkDO);
				}
				FcsBaseResult result = (FcsBaseResult) FcsPmDomainHolder.get().getAttribute(
					"result");
				result.setKeyId(checkDO.getFormId());
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public InvestigationCheckingInfo findCheckingByFormId(long formId) {
		FInvestigationCheckingDO checkDO = FInvestigationCheckingDAO.findByFormId(formId);
		
		return convertToInfo(checkDO);
	}
	
	@Override
	public InvestigationCheckingInfo findCheckingByCheckPiontAndRelatedFormId(String checkPoint,
																				long relatedFormId) {
		FInvestigationCheckingDO checkDO = FInvestigationCheckingDAO
			.findByRelatedFormIdAndCheckPoint(relatedFormId, checkPoint);
		return convertToInfo(checkDO);
	}
	
	private InvestigationCheckingInfo convertToInfo(FInvestigationCheckingDO checkDO) {
		if (null == checkDO) {
			return null;
		}
		
		InvestigationCheckingInfo info = new InvestigationCheckingInfo();
		BeanCopier.staticCopy(checkDO, info);
		info.setCheckPoint(CheckPointEnum.getByCode(checkDO.getCheckPoint()));
		return info;
	}
	
	/**
	 * 审核过程中编辑数据保存时备份历史数据用于对比
	 * @param baseOrder
	 */
	private void copyForAudit2(FInvestigationBaseOrder baseOrder) {
		if (isEqual(baseOrder.getCheckPoint(), CheckPointEnum.TEAM_LEADER.code())
			|| isEqual(baseOrder.getCheckPoint(), CheckPointEnum.VICE_PRESIDENT_BUSINESS.code())) {
			FInvestigationCheckingDO checkDO = createCheck(baseOrder);
			if (null == checkDO) {
				//生成失败
				return;
			}
			String formCode = checkDO.getFormCode();
			if (hasData(formCode, baseOrder.getCheckIndex())) {
				return;
			}
			long oldFormId = checkDO.getRelatedFormId();
			long newFormId = checkDO.getFormId();
			FInvestigationDO investigation = FInvestigationDAO.findByFormId(oldFormId);
			switch (baseOrder.getCheckIndex()) {
				case 0:
					copyDeclare(oldFormId, newFormId);
					break;
				case 1:
					if (FormCodeEnum.INVESTIGATION_LITIGATION == baseOrder.getFormCode()) {
						copyLitigation(oldFormId, newFormId, investigation);
					} else if (FormCodeEnum.INVESTIGATION_UNDERWRITING == baseOrder.getFormCode()) {
						copyUnderwriting(oldFormId, newFormId, investigation);
					} else {
						copyCreditScheme(oldFormId, newFormId, investigation, false,baseOrder.getCheckPoint());
					}
					break;
				case 2:
					copyMainlyReview(oldFormId, newFormId, investigation, false);
					break;
				case 3:
					copyMabilityReview(oldFormId, newFormId, investigation);
					break;
				case 4:
					copyOpabilityReview(oldFormId, newFormId, investigation);
					break;
				case 5:
					copyFinancialReview(oldFormId, newFormId, investigation);
					break;
				case 6:
					copyMajorEvent(oldFormId, newFormId, investigation);
					break;
				case 7:
					copyProjectStatus(oldFormId, newFormId, investigation);
					break;
				case 8:
					copyCsRationalityReview(oldFormId, newFormId, investigation);
					break;
				case 9:
					copyRisk(oldFormId, newFormId, investigation);
					break;
				default:
					break;
			}
			
			char[] cs = formCode.toCharArray();
			cs[baseOrder.getCheckIndex()] = '1';
			checkDO.setFormCode(String.valueOf(cs));
			//			FInvestigationCheckingDAO.update(checkDO);
			FInvestigationCheckingDAO.updateByFormId(checkDO);
		}
	}
	
	/**
	 * 审核过程中编辑数据保存时备份历史数据用于对比
	 * @param baseOrder
	 */
	private void copyForAudit(FInvestigationBaseOrder baseOrder) {
		if (baseOrder.getFormId() <= 0) {
			return;
		}
		if (isEqual(baseOrder.getCheckPoint(), CheckPointEnum.TEAM_LEADER.code())
			|| isEqual(baseOrder.getCheckPoint(), CheckPointEnum.VICE_PRESIDENT_BUSINESS.code())) {
			FInvestigationCheckingDO sourceDO = FInvestigationCheckingDAO
				.findByRelatedFormIdAndCheckPoint(baseOrder.getFormId(),
					CheckPointEnum.SOURCE.code());
			FInvestigationBaseOrder sourceOrder = new FInvestigationBaseOrder();
			BeanCopier.staticCopy(baseOrder, sourceOrder);
			sourceOrder.setCheckPoint(CheckPointEnum.SOURCE.code());
			if (null == sourceDO) {
				sourceDO = createCheck(sourceOrder);
			}
			//复制源数据
			copyData(sourceDO, sourceOrder);
			
			if (isEqual(baseOrder.getCheckPoint(), CheckPointEnum.TEAM_LEADER.code())) {
				createCheck(baseOrder);
			} else if (isEqual(baseOrder.getCheckPoint(),
				CheckPointEnum.VICE_PRESIDENT_BUSINESS.code())) {
				FInvestigationCheckingDO checkDO = createCheck(baseOrder);
				if (null == checkDO) {
					//生成失败
					return;
				}
				FInvestigationCheckingDO preDO = FInvestigationCheckingDAO
					.findByRelatedFormIdAndCheckPoint(baseOrder.getFormId(),
						CheckPointEnum.TEAM_LEADER.code());
				if (null != preDO) {
					copyData(checkDO, baseOrder);
				} else {
					updateChecked(checkDO, baseOrder);
				}
			}
		}
	}
	
	private void copyData(FInvestigationCheckingDO checkDO, FInvestigationBaseOrder baseOrder) {
		String formCode = checkDO.getFormCode();
		if (hasData(formCode, baseOrder.getCheckIndex())) {
			return;
		}
		long oldFormId = checkDO.getRelatedFormId();
		long newFormId = checkDO.getFormId();
		FInvestigationDO investigation = FInvestigationDAO.findByFormId(oldFormId);
		switch (baseOrder.getCheckIndex()) {
			case 0:
				copyDeclare(oldFormId, newFormId);
				break;
			case 1:
				if (FormCodeEnum.INVESTIGATION_LITIGATION == baseOrder.getFormCode()) {
					copyLitigation(oldFormId, newFormId, investigation);
				} else if (FormCodeEnum.INVESTIGATION_UNDERWRITING == baseOrder.getFormCode()) {
					copyUnderwriting(oldFormId, newFormId, investigation);
				} else {
					copyCreditScheme(oldFormId, newFormId, investigation, false,baseOrder.getCheckPoint());
					commonAttachmentService.updateStatus(newFormId + "",
							CommonAttachmentTypeEnum.COUNTER_GUARANTEE, CheckStatusEnum.CHECK_NOT_PASS);
					commonAttachmentService.updateStatus(newFormId + "_index0",
						CommonAttachmentTypeEnum.FORM_ATTACH, CheckStatusEnum.CHECK_NOT_PASS);
					commonAttachmentService.updateStatus(newFormId + "",
						CommonAttachmentTypeEnum.INVESTIGATION_REPORT, CheckStatusEnum.CHECK_NOT_PASS);					
				}
				break;
			case 2:
				copyMainlyReview(oldFormId, newFormId, investigation, false);
				commonAttachmentService.updateStatus(newFormId + "_index1",
						CommonAttachmentTypeEnum.FORM_ATTACH, CheckStatusEnum.CHECK_NOT_PASS);
				break;
			case 3:
				copyMabilityReview(oldFormId, newFormId, investigation);
				commonAttachmentService.updateStatus(newFormId + "_index2",
						CommonAttachmentTypeEnum.FORM_ATTACH, CheckStatusEnum.CHECK_NOT_PASS);
				break;
			case 4:
				copyOpabilityReview(oldFormId, newFormId, investigation);
				commonAttachmentService.updateStatus(newFormId + "_index3",
						CommonAttachmentTypeEnum.FORM_ATTACH, CheckStatusEnum.CHECK_NOT_PASS);
				break;
			case 5:
				copyFinancialReview(oldFormId, newFormId, investigation);
				commonAttachmentService.updateStatus(newFormId + "",
						CommonAttachmentTypeEnum.INVESTIGATION_FINANCIAL, CheckStatusEnum.CHECK_NOT_PASS);
				commonAttachmentService.updateStatus(newFormId + "_index4",
						CommonAttachmentTypeEnum.FORM_ATTACH, CheckStatusEnum.CHECK_NOT_PASS);
				commonAttachmentService.updateStatus(newFormId + "_1",
						CommonAttachmentTypeEnum.INVESTIGATION_FINANCIAL, CheckStatusEnum.CHECK_NOT_PASS);
				commonAttachmentService.updateStatus(newFormId + "_index4_1",
						CommonAttachmentTypeEnum.FORM_ATTACH, CheckStatusEnum.CHECK_NOT_PASS);
				break;
			case 6:
				copyMajorEvent(oldFormId, newFormId, investigation);
				commonAttachmentService.updateStatus(newFormId + "_index5",
						CommonAttachmentTypeEnum.FORM_ATTACH, CheckStatusEnum.CHECK_NOT_PASS);
				break;
			case 7:
				copyProjectStatus(oldFormId, newFormId, investigation);
				commonAttachmentService.updateStatus(newFormId + "", CommonAttachmentTypeEnum.INVESTIGATION_6,
						CheckStatusEnum.CHECK_NOT_PASS);
				commonAttachmentService.updateStatus(newFormId + "_index6",
					CommonAttachmentTypeEnum.FORM_ATTACH, CheckStatusEnum.CHECK_NOT_PASS);
				break;
			case 8:
				copyCsRationalityReview(oldFormId, newFormId, investigation);
				commonAttachmentService.updateStatus(newFormId + "_index7",
						CommonAttachmentTypeEnum.FORM_ATTACH, CheckStatusEnum.CHECK_NOT_PASS);
				break;
			case 9:
				copyRisk(oldFormId, newFormId, investigation);
				commonAttachmentService.updateStatus(newFormId + "_index8",
						CommonAttachmentTypeEnum.FORM_ATTACH, CheckStatusEnum.CHECK_NOT_PASS);
				break;
			default:
				break;
		}
		
		updateChecked(checkDO, baseOrder);
	}
	
	private void updateChecked(FInvestigationCheckingDO checkDO, FInvestigationBaseOrder baseOrder) {
		String formCode = checkDO.getFormCode();
		char[] cs = formCode.toCharArray();
		cs[baseOrder.getCheckIndex()] = '1';
		checkDO.setFormCode(String.valueOf(cs));
		FInvestigationCheckingDAO.updateByFormId(checkDO);
	}
	
	private boolean hasData(String formCode, int checkIndex) {
		return "1".equals(formCode.charAt(checkIndex) + "");
	}
	
	/** 复制声明 */
	private void copyDeclare(long formId, long newFormId) {
		FInvestigationDO fInvestigationDO = FInvestigationDAO.findByFormId(formId);
		if (null == fInvestigationDO) {
			return;
		}
		
		Date now = getSysdate();
		FInvestigationDO doObj = new FInvestigationDO();
		BeanCopier.staticCopy(fInvestigationDO, doObj);
		doObj.setInvestigateId(0L);
		doObj.setFormId(newFormId);
		doObj.setRawAddTime(now);
		FInvestigationDAO.insert(doObj);
		//复制调查情况
		copyPersons(formId, newFormId, now);
	}
	
	private void copyPersons(long formId, long newFormId, Date now) {
		List<FInvestigationPersonDO> items = FInvestigationPersonDAO.findByFormId(formId);
		if (ListUtil.isEmpty(items)) {
			return;
		}
		
		for (FInvestigationPersonDO item : items) {
			FInvestigationPersonDO doObj = new FInvestigationPersonDO();
			BeanCopier.staticCopy(item, doObj);
			doObj.setId(0L);
			doObj.setFormId(newFormId);
			doObj.setRawAddTime(now);
			FInvestigationPersonDAO.insert(doObj);
		}
	}
	
	/** 将附件标记为删除状态 */
	@Override
	public void deleteInvestigationCommonAttachment(long formId) {
		FormDO form = formDAO.findByFormId(formId);
		if (null == form) {
			return;
		}
		
		String bizNo = form.getFormId() + "";
		FormCodeEnum formCode = FormCodeEnum.getByCode(form.getFormCode());
		if (FormCodeEnum.INVESTIGATION_BASE == formCode) {
			for (int i = 0; i <= 8; i++) {
				commonAttachmentService.updateStatus(bizNo + "_index" + i,
					CommonAttachmentTypeEnum.FORM_ATTACH, CheckStatusEnum.CHECK_NOT_PASS);
			}
			commonAttachmentService.updateStatus(bizNo, CommonAttachmentTypeEnum.COUNTER_GUARANTEE,
				CheckStatusEnum.CHECK_NOT_PASS);
			commonAttachmentService.updateStatus(bizNo,
				CommonAttachmentTypeEnum.INVESTIGATION_FINANCIAL, CheckStatusEnum.CHECK_NOT_PASS);
			commonAttachmentService.updateStatus(bizNo, CommonAttachmentTypeEnum.INVESTIGATION_6,
				CheckStatusEnum.CHECK_NOT_PASS);
		} else if (FormCodeEnum.INVESTIGATION_LITIGATION == formCode) {
			
		} else if (FormCodeEnum.INVESTIGATION_UNDERWRITING == formCode) {
			FInvestigationUnderwritingDO underwriting = FInvestigationUnderwritingDAO
				.findByFormId(form.getFormId());
			if (null != underwriting) {
				commonAttachmentService.updateStatus(underwriting.getUnderwritingId() + "",
					CommonAttachmentTypeEnum.UNDERWRITING_PROJECT, CheckStatusEnum.CHECK_NOT_PASS);
			}
		} else {
			return;
		}
	}
	
	@Override
	public List<InvestigationCheckingInfo> findCheckingByRelatedFormId(long relatedFormId) {
		List<FInvestigationCheckingDO> list = FInvestigationCheckingDAO
			.findByRelatedFormId(relatedFormId);
		if (ListUtil.isEmpty(list)) {
			return null;
		}
		
		List<InvestigationCheckingInfo> infos = new ArrayList<>(list.size());
		for (FInvestigationCheckingDO doObj : list) {
			InvestigationCheckingInfo info = convertToInfo(doObj);
			infos.add(info);
		}
		return infos;
	}
	
	private FInvestigationCheckingDO createCheck(FInvestigationBaseOrder order) {
		
		//取得表单信息
		FormDO form = formDAO.findByFormId(order.getFormId());
		if (null == form) {
			//throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
			logger.error("生成checking失败，原表单为空formId=" + order.getFormId());
			return null;
		}
		FInvestigationCheckingDO checkDO = FInvestigationCheckingDAO
			.findByRelatedFormIdAndCheckPoint(order.getFormId(), order.getCheckPoint());
		if (null != checkDO) {
			return checkDO;
		} else {
			FormDO newForm = new FormDO();
			BeanCopier.staticCopy(form, newForm);
			newForm.setFormId(0L);
			newForm.setRawAddTime(getSysdate());
			newForm.setRawUpdateTime(null);
			//历史数据标记为删除状态，以防被列表查询出来
			newForm.setStatus(FormStatusEnum.DELETED.code());
			if (order.getCheckPoint().equals(CheckPointEnum.SOURCE.getCode())) {
				newForm
				.setCustomizeField(JSONObject.toJSONString(order.getCopyCustomizeFieldMap()));
			} else {
				if (null != order.getFormCustomizeFieldMap()) {
					newForm
					.setCustomizeField(JSONObject.toJSONString(order.getFormCustomizeFieldMap()));
				}
			}
			long newFormId = formDAO.insert(newForm);
			
			checkDO = new FInvestigationCheckingDO();
			BeanCopier.staticCopy(order, checkDO);
			checkDO.setFormCode(form.getCheckStatus().replaceAll("1", "0"));
			checkDO.setUserId(null == order.getUserId() ? 0L : order.getUserId().longValue());
			checkDO.setFormId(newFormId); //源表单ID
			checkDO.setRelatedFormId(order.getFormId());
			checkDO.setRawAddTime(newForm.getRawAddTime());
			FInvestigationCheckingDAO.insert(checkDO);
		}
		
		return checkDO;
	}
	
	/**
	 * 查询公共内容 返回风险覆盖率
	 * @param baseInfo
	 */
	private void queryCommon(InvestigationBaseInfo baseInfo) {
		queryCommon(baseInfo, "");
	}
	
	/**
	 * 查询公共内容
	 * @param baseInfo 返回风险覆盖率
	 * @param attachmentKeyId 附件标识 (不为空则返回通用附件)
	 * @param moduleTypes 其它附件类型 (不为空则返回其它附件)
	 */
	private void queryCommon(InvestigationBaseInfo baseInfo, String attachmentKeyId,
								CommonAttachmentTypeEnum... moduleTypes) {
		if (null == baseInfo) {
			return;
		}
		
		//-----------------------------查询附件begin-----------------------------
		Map<String, AttachmentModuleType> attachmentMap = new HashMap<>();
		String suffix = "";
		if (baseInfo.getSubIndex() == 1) {
			suffix = "_" + baseInfo.getSubIndex();
		}
		
		if (StringUtil.isNotBlank(attachmentKeyId)) {
			CommonAttachmentQueryOrder attachQueryOrder = new CommonAttachmentQueryOrder();
			String bizNo = attachmentKeyId + "_index" + baseInfo.getCurIndex() + suffix;
			attachQueryOrder.setBizNo(bizNo);
			List<CommonAttachmentTypeEnum> typeList = new ArrayList<>();
			typeList.add(CommonAttachmentTypeEnum.FORM_ATTACH);
			attachQueryOrder.setModuleTypeList(typeList);
			List<AttachmentModuleType> list = commonAttachmentService
				.queryAttachment(attachQueryOrder);
			if (ListUtil.isNotEmpty(list)) {
				attachmentMap.put(CommonAttachmentTypeEnum.FORM_ATTACH.code(), list.get(0));
			}
		}
		
		if (null != moduleTypes && moduleTypes.length > 0) {
			CommonAttachmentQueryOrder attachQueryOrder = new CommonAttachmentQueryOrder();
			attachQueryOrder = new CommonAttachmentQueryOrder();
			attachQueryOrder.setBizNo(attachmentKeyId + suffix);
			List<CommonAttachmentTypeEnum> moduleTypeList = new ArrayList<>();
			for (CommonAttachmentTypeEnum type : moduleTypes) {
				moduleTypeList.add(type);
			}
			attachQueryOrder.setModuleTypeList(moduleTypeList);
			List<AttachmentModuleType> list = commonAttachmentService
				.queryAttachment(attachQueryOrder);
			if (ListUtil.isNotEmpty(list)) {
				for (AttachmentModuleType attach : list) {
					attachmentMap.put(attach.getModuleType().code(), attach);
				}
			}
		}
		
		baseInfo.setAttachmentMap(attachmentMap);
		//-----------------------------查询附件end-----------------------------
		
		//查询风险覆盖率
		queryRiskRate(baseInfo);
	}
	
	private void queryRiskRate(InvestigationBaseInfo baseInfo) {
		double rate = 0d;
		FInvestigationCreditSchemeInfo info = null;
		if (baseInfo instanceof FInvestigationCreditSchemeInfo) {
			info = (FInvestigationCreditSchemeInfo) baseInfo;
		} else {
			info = findInvestigationCreditSchemeByFormId(baseInfo.getFormId());
		}
		if (null != info && info.getCreditAmount().greaterThan(Money.zero())) {
			//清算值
			Money clearingAmount = new Money(0L);
			
			clearingAmount.addTo(info.getPledgePrice()).addTo(info.getMortgagePrice());
			
			//风险覆盖率
			rate = (100d * clearingAmount.getCent()) / info.getCreditAmount().getCent();
		}
		
		baseInfo.setRiskRate(rate);
	}
}
