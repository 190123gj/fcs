package com.born.fcs.pm.biz.service.investigation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.crm.ws.service.CompanyCustomerService;
import com.born.fcs.crm.ws.service.info.CompanyCustomerInfo;
import com.born.fcs.crm.ws.service.info.CompanyQualificationInfo;
import com.born.fcs.pm.biz.convert.DoConvert;
import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.convert.VoConvert;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.CommonAttachmentDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationCreditSchemeDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationCreditSchemeGuarantorDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationCreditSchemePledgeAssetDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationCreditSchemeProcessControlDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationCsRationalityReviewDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationCsRationalityReviewFinancialKpiDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationCsRationalityReviewGuarantorAbilityDO;
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
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dataobject.InvestigationDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.DataFinancialHelper;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.AmountUnitEnum;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ChargePhaseEnum;
import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.ChargeUnitEnum;
import com.born.fcs.pm.ws.enums.ChargeWayEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.CompanyTypeEnum;
import com.born.fcs.pm.ws.enums.CreditTypeEnum;
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
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectVOInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCreditSchemeGuarantorInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCreditSchemeInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCreditSchemePledgeAssetInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCreditSchemeProcessControlInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCsRationalityReviewGuarantorAbilityInfo;
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
import com.born.fcs.pm.ws.info.finvestigation.InvestigationFinancialKpiInfo;
import com.born.fcs.pm.ws.info.finvestigation.InvestigationInfo;
import com.born.fcs.pm.ws.info.setup.FProjectGuaranteeEntrustedInfo;
import com.born.fcs.pm.ws.info.setup.FProjectInfo;
import com.born.fcs.pm.ws.order.common.ChangeProjectStatusOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationCreditSchemeGuarantorOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationCreditSchemeOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationCreditSchemePledgeAssetOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationCreditSchemeProcessControlOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationCsRationalityReviewGuarantorAbilityOrder;
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
import com.born.fcs.pm.ws.order.finvestigation.InvestigationFinancialKpiOrder;
import com.born.fcs.pm.ws.order.finvestigation.InvestigationQueryOrder;
import com.born.fcs.pm.ws.order.finvestigation.base.InvestigationBaseOrder;
import com.born.fcs.pm.ws.order.finvestigation.declare.DeclareBaseOrder;
import com.born.fcs.pm.ws.order.finvestigation.declare.FInvestigationPersonOrder;
import com.born.fcs.pm.ws.order.project.UpdateProjectRecouncilOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.common.ProjectResult;
import com.born.fcs.pm.ws.service.common.ProjectService;
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
	private CompanyCustomerService companyCustomerServiceClient;
	@Autowired
	private ProjectSetupService projectSetupService;
	
	@Override
	protected FormBaseResult createResult() {
		return new FormBaseResult();
	}
	
	@Override
	public FcsBaseResult updateInvestigation(final InvestigationBaseOrder order) {
		return commonProcess(order, "修改尽调基本信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				FInvestigationDO investigation = FInvestigationDAO.findByFormId(order.getFormId());
				if (null == investigation) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
						"未找到表单对应的尽调数据");
				}
				
				BeanCopier.staticCopy(order, investigation);
				FInvestigationDAO.update(investigation);
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FormBaseResult saveInvestigation(final FInvestigationOrder order) {
		return commonFormSaveProcess(order, "保存尽职调查申明", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
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
	
	protected boolean isEqual(Date d1, Date d2) {
		long t1 = (null == d1) ? 0L : d1.getTime();
		long t2 = (null == d2) ? 0L : d2.getTime();
		return t1 == t2;
	}
	
	protected boolean isEqual(String s1, String s2) {
		return StringUtil.equals(s1, s2);
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
	
	private FormBaseResult add(final FInvestigationOrder order) {
		return commonFormSaveProcess(order, "复议添加尽职调查申明", new BeforeProcessInvokeService() {
			
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
				fInvestigationDO.setRawAddTime(now);
				ProjectDO project = projectDAO.findByProjectCodeForUpdate(order.getProjectCode());
				fInvestigationDO.setAmount(project.getAmount()); //授信金额
				FInvestigationDAO.insert(fInvestigationDO);
				
				//				project.setPhases(ProjectPhasesEnum.INVESTIGATING_PHASES.code());
				//				project.setStatus(ProjectStatusEnum.NORMAL.code());
				//				project.setPhasesStatus(ProjectPhasesStatusEnum.DRAFT.code());
				//				projectDAO.update(project);
				
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
				checkProjectPause(order.getProjectCode());
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				FInvestigationDO investigation = FInvestigationDAO.findByFormId(form.getFormId());
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
			map.get(ProcessControlEnum.CUSTOMER_GRADE));
		saveProcesses(formId, ProcessControlEnum.DEBT_RATIO, order.getDebtRatios(),
			map.get(ProcessControlEnum.DEBT_RATIO));
		saveProcesses(formId, ProcessControlEnum.OTHER, order.getOthers(),
			map.get(ProcessControlEnum.OTHER));
	}
	
	private void saveProcesses(long formId, ProcessControlEnum type,
								List<FInvestigationCreditSchemeProcessControlOrder> orders,
								List<FInvestigationCreditSchemeProcessControlDO> items) {
		if (formId <= 0 || type == null) {
			return;
		}
		
		if (ListUtil.isEmpty(orders)) {
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
		return findInvestigationCreditScheme(FInvestigationCreditSchemeDAO.findByFormId(formId));
	}
	
	private FInvestigationCreditSchemeInfo findInvestigationCreditScheme(	FInvestigationCreditSchemeDO scheme) {
		if (null == scheme) {
			return null;
		}
		long schemeId = scheme.getSchemeId();
		FInvestigationCreditSchemeInfo schemeInfo = new FInvestigationCreditSchemeInfo();
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
		schemeInfo.setGuarantorAmount(guarantorAmount);
		
		//查询过程控制内容
		findProcesses(scheme.getFormId(), schemeInfo);
		
		return schemeInfo;
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
			
			@Override
			public Domain before() {
				checkProjectPause(order.getProjectCode());
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
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
					FInvestigationMainlyReviewCertificateDAO.deleteByOwnerAndReviewId(owner.code(),
						reviewId);
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
				saveCertificates(reviewId, owner, order.getCertificates(), now);
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
									List<FInvestigationMainlyReviewCertificateOrder> certificates,
									Date now) {
		if (ListUtil.isEmpty(certificates) || reviewId <= 0 || null == owner) {
			return;
		}
		
		for (FInvestigationMainlyReviewCertificateOrder order : certificates) {
			if (!order.isNull()) {
				FInvestigationMainlyReviewCertificateDO certificate = new FInvestigationMainlyReviewCertificateDO();
				BeanCopier.staticCopy(order, certificate);
				certificate.setOwner(owner.code());
				certificate.setMReviewId(reviewId);
				certificate.setRawAddTime(now);
				FInvestigationMainlyReviewCertificateDAO.insert(certificate);
			}
		}
	}
	
	//保存资质证书
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
	
	//保存主要股东情况
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
	private void saveCreditStatus(long reviewId, CreditTypeEnum type,
									List<FInvestigationMainlyReviewCreditStatusOrder> creditStatus,
									Date now) {
		if (ListUtil.isEmpty(creditStatus) || null == type || reviewId <= 0) {
			return;
		}
		
		for (FInvestigationMainlyReviewCreditStatusOrder order : creditStatus) {
			if (!order.isNull()) {
				FInvestigationMainlyReviewCreditStatusDO doObj = new FInvestigationMainlyReviewCreditStatusDO();
				BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
				doObj.setMReviewId(reviewId);
				doObj.setType(type.code());
				doObj.setLoanBalance(order.getLoanBalance());
				doObj.setRawAddTime(now);
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
		
		for (FInvestigationMainlyReviewCreditInfoOrder order : sites) {
			if (!order.isNull()) {
				FInvestigationMainlyReviewCreditInfoDO doObj = new FInvestigationMainlyReviewCreditInfoDO();
				BeanCopier.staticCopy(order, doObj);
				doObj.setStatus(order.getStatusStr());
				doObj.setMReviewId(reviewId);
				doObj.setRawAddTime(now);
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
		
		return reviewInfo;
	}
	
	@Override
	public FormBaseResult saveFInvestigationMabilityReview(	final FInvestigationMabilityReviewOrder order) {
		return commonFormSaveProcess(order, "保存客户管理能力评价", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				checkProjectPause(order.getProjectCode());
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
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
		BeanCopier.staticCopy(review, reviewInfo);
		
		//		List<FInvestigationMabilityReviewLeadingTeamInfo> leadingTeams = new ArrayList<>();
		//		List<FInvestigationMabilityReviewLeadingTeamDO> leadingTeamDOs = FInvestigationMabilityReviewLeadingTeamDAO
		//			.findByOwnerAndReviewId(OwnerEnum.CUSTOMER.code(), review.getMaReviewId());
		//		for (FInvestigationMabilityReviewLeadingTeamDO doObj : leadingTeamDOs) {
		//			FInvestigationMabilityReviewLeadingTeamInfo leadingTeamInfo = new FInvestigationMabilityReviewLeadingTeamInfo();
		//			BeanCopier.staticCopy(doObj, leadingTeamInfo);
		//			leadingTeams.add(leadingTeamInfo);
		//		}
		List<FInvestigationMabilityReviewLeadingTeamInfo> leadingTeams = queryLeadings(
			review.getMaReviewId(), OwnerEnum.CUSTOMER);
		reviewInfo.setLeadingTeams(leadingTeams);
		
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
				checkProjectPause(order.getProjectCode());
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
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
		
		return reviewInfo;
	}
	
	@Override
	public FormBaseResult saveFInvestigationMajorEvent(final FInvestigationMajorEventOrder order) {
		return commonFormSaveProcess(order, "保存重大事项调查", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				checkProjectPause(order.getProjectCode());
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
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
		BeanCopier.staticCopy(event, eventInfo);
		return eventInfo;
	}
	
	@Override
	public FormBaseResult saveFInvestigationProjectStatus(	final FInvestigationProjectStatusOrder order) {
		return commonFormSaveProcess(order, "保存项目情况调查", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				checkProjectPause(order.getProjectCode());
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
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
		return statusInfo;
	}
	
	@Override
	public FormBaseResult saveFInvestigationCsRationalityReview(final FInvestigationCsRationalityReviewOrder order) {
		return commonFormSaveProcess(order, "保存授信方案主要事项合理性评价", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				checkProjectPause(order.getProjectCode());
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				OwnerEnum owner = OwnerEnum.GUARANTOR;
				long csrReviewId = order.getCsrReviewId();
				FInvestigationCsRationalityReviewDO csReview = FInvestigationCsRationalityReviewDAO
					.findByFormId(order.getFormId());
				if (null == csReview) {
					csReview = new FInvestigationCsRationalityReviewDO();
					BeanCopier.staticCopy(order, csReview);
					csReview.setFormId(form.getFormId());
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
					FInvestigationMainlyReviewCertificateDAO.deleteByOwnerAndReviewId(owner.code(),
						csrReviewId);
					//删除原来的客户高管人员列表
					//					FInvestigationMabilityReviewLeadingTeamDAO.deleteByOwnerAndReviewId(
					//						owner.code(), csrReviewId);
					//删除原来的保证人主要财务指标
					FInvestigationCsRationalityReviewFinancialKpiDAO.deleteByReviewId(csrReviewId);
					//删除保证人保证能力总体评价（非必填）
					FInvestigationCsRationalityReviewGuarantorAbilityDAO
						.deleteByReviewId(csrReviewId);
				}
				//保证人合法性评价（此处不含担保公司评价）
				saveOrUpdateMainlyReview(order, owner, form.getFormId(), now);
				//保存已获得的资质证书
				saveCertificates(csrReviewId, owner, order.getCertificates(), now);
				//保存客户高管人员列表
				saveLeadingTeams(csrReviewId, owner, order.getLeadingTeams());
				//保存保证人主要财务指标
				//				saveFinancialKpis(csrReviewId, order.getKpies(), now);
				saveKpis(csrReviewId, order.getHeader(), order.getKpies(), now);
				//保存保证人保证能力总体评价（非必填）
				saveGuarantorAbilities(csrReviewId, order.getGuarantorAbilities(), now);
				return null;
			}
		}, null, null);
	}
	
	private void saveOrUpdateMainlyReview(final FInvestigationCsRationalityReviewOrder order,
											OwnerEnum owner, long formId, Date now) {
		List<FInvestigationMainlyReviewOrder> mainlyReviews = order.getMainlyReviews();
		if (ListUtil.isNotEmpty(mainlyReviews)) {
			FInvestigationMainlyReviewOrder mainlyReview = mainlyReviews.get(0);
			if (null != mainlyReview) {
				FInvestigationMainlyReviewDO review = FInvestigationMainlyReviewDAO
					.findById(mainlyReview.getMReviewId());
				if (null == review) {
					review = new FInvestigationMainlyReviewDO();
					BeanCopier.staticCopy(mainlyReview, review);
					review.setFormId(formId);
					review.setProjectCode(order.getProjectCode());
					review.setOwner(owner.code());
					review.setRawAddTime(now);
					review.setCustomerDevEvolution(order.getLeadersAppraise());
					FInvestigationMainlyReviewDAO.insert(review);
				} else {
					BeanCopier.staticCopy(mainlyReview, review);
					review.setFormId(formId);
					review.setProjectCode(order.getProjectCode());
					review.setOwner(owner.code());
					review.setCustomerDevEvolution(order.getLeadersAppraise());
					FInvestigationMainlyReviewDAO.update(review);
				}
			}
		}
	}
	
	//保存保证人主要财务指标
	private void saveKpis(long csrReviewId, InvestigationFinancialKpiOrder header,
							List<InvestigationFinancialKpiOrder> kpies, Date now) {
		if (ListUtil.isEmpty(kpies) || null == header || csrReviewId <= 0) {
			return;
		}
		
		int sortOrder = 1;
		saveHeaderKpis(header, sortOrder, csrReviewId, now);
		for (InvestigationFinancialKpiOrder order : kpies) {
			sortOrder++;
			saveKpis(header, order, sortOrder, csrReviewId, now);
		}
	}
	
	private void saveHeaderKpis(InvestigationFinancialKpiOrder header, int sortOrder,
								long csrReviewId, Date now) {
		//最近一期
		FInvestigationCsRationalityReviewFinancialKpiDO doObj = new FInvestigationCsRationalityReviewFinancialKpiDO();
		doObj.setCsrReviewId(csrReviewId);
		doObj.setKpiName(header.getItemName());
		doObj.setKpiCode(header.getItemCode());
		doObj.setKpiValue(header.getValue());
		doObj.setTermType(header.getValue());
		doObj.setRawAddTime(now);
		doObj.setSortOrder(sortOrder);
		FInvestigationCsRationalityReviewFinancialKpiDAO.insert(doObj);
		
		//前一年
		FInvestigationCsRationalityReviewFinancialKpiDO doObj1 = new FInvestigationCsRationalityReviewFinancialKpiDO();
		doObj1.setCsrReviewId(csrReviewId);
		doObj1.setKpiName(header.getItemName());
		doObj1.setKpiCode(header.getItemCode());
		doObj1.setKpiValue(header.getValue1());
		doObj1.setTermType(header.getValue1());
		doObj1.setRawAddTime(now);
		doObj1.setSortOrder(sortOrder);
		FInvestigationCsRationalityReviewFinancialKpiDAO.insert(doObj1);
		
		//前二年
		FInvestigationCsRationalityReviewFinancialKpiDO doObj2 = new FInvestigationCsRationalityReviewFinancialKpiDO();
		doObj2.setCsrReviewId(csrReviewId);
		doObj2.setKpiName(header.getItemName());
		doObj2.setKpiCode(header.getItemCode());
		doObj2.setKpiValue(header.getValue2());
		doObj2.setTermType(header.getValue2());
		doObj2.setRawAddTime(now);
		doObj2.setSortOrder(sortOrder);
		FInvestigationCsRationalityReviewFinancialKpiDAO.insert(doObj2);
		
		//前三年
		FInvestigationCsRationalityReviewFinancialKpiDO doObj3 = new FInvestigationCsRationalityReviewFinancialKpiDO();
		doObj3.setCsrReviewId(csrReviewId);
		doObj3.setKpiName(header.getItemName());
		doObj3.setKpiCode(header.getItemCode());
		doObj3.setKpiValue(header.getValue3());
		doObj3.setTermType(header.getValue3());
		doObj3.setRawAddTime(now);
		doObj3.setSortOrder(sortOrder);
		FInvestigationCsRationalityReviewFinancialKpiDAO.insert(doObj3);
	}
	
	private void saveKpis(InvestigationFinancialKpiOrder header,
							InvestigationFinancialKpiOrder order, int sortOrder, long csrReviewId,
							Date now) {
		//最近一期
		FInvestigationCsRationalityReviewFinancialKpiDO doObj = new FInvestigationCsRationalityReviewFinancialKpiDO();
		doObj.setCsrReviewId(csrReviewId);
		doObj.setKpiName(order.getItemName());
		doObj.setKpiCode(order.getItemCode());
		doObj.setKpiValue(order.getValue());
		doObj.setTermType(header.getValue());
		doObj.setRawAddTime(now);
		doObj.setSortOrder(sortOrder);
		FInvestigationCsRationalityReviewFinancialKpiDAO.insert(doObj);
		
		//前一年
		FInvestigationCsRationalityReviewFinancialKpiDO doObj1 = new FInvestigationCsRationalityReviewFinancialKpiDO();
		doObj1.setCsrReviewId(csrReviewId);
		doObj1.setKpiName(order.getItemName());
		doObj1.setKpiCode(order.getItemCode());
		doObj1.setKpiValue(order.getValue1());
		doObj1.setTermType(header.getValue1());
		doObj1.setRawAddTime(now);
		doObj1.setSortOrder(sortOrder);
		FInvestigationCsRationalityReviewFinancialKpiDAO.insert(doObj1);
		
		//前二年
		FInvestigationCsRationalityReviewFinancialKpiDO doObj2 = new FInvestigationCsRationalityReviewFinancialKpiDO();
		doObj2.setCsrReviewId(csrReviewId);
		doObj2.setKpiName(order.getItemName());
		doObj2.setKpiCode(order.getItemCode());
		doObj2.setKpiValue(order.getValue2());
		doObj2.setTermType(header.getValue2());
		doObj2.setRawAddTime(now);
		doObj2.setSortOrder(sortOrder);
		FInvestigationCsRationalityReviewFinancialKpiDAO.insert(doObj2);
		
		//前三年
		FInvestigationCsRationalityReviewFinancialKpiDO doObj3 = new FInvestigationCsRationalityReviewFinancialKpiDO();
		doObj3.setCsrReviewId(csrReviewId);
		doObj3.setKpiName(order.getItemName());
		doObj3.setKpiCode(order.getItemCode());
		doObj3.setKpiValue(order.getValue3());
		doObj3.setTermType(header.getValue3());
		doObj3.setRawAddTime(now);
		doObj3.setSortOrder(sortOrder);
		FInvestigationCsRationalityReviewFinancialKpiDAO.insert(doObj3);
		
	}
	
	//保存保证人主要财务指标
	//	private void saveFinancialKpis(long csrReviewId, List<FInvestigationCsRationalityReviewFinancialKpiOrder> kpies, Date now) {
	//		if (ListUtil.isEmpty(kpies) || csrReviewId <= 0) {
	//			return;
	//		}
	//		
	//		for (FInvestigationCsRationalityReviewFinancialKpiOrder order : kpies) {
	//			FInvestigationCsRationalityReviewFinancialKpiDO doObj = new FInvestigationCsRationalityReviewFinancialKpiDO();
	//			BeanCopier.staticCopy(order, doObj);
	//			doObj.setCsrReviewId(csrReviewId);
	//			doObj.setRawAddTime(now);
	//			FInvestigationCsRationalityReviewFinancialKpiDAO.insert(doObj);
	//		}
	//	}
	
	//保存保证人保证能力总体评价（非必填）
	private void saveGuarantorAbilities(long csrReviewId,
										List<FInvestigationCsRationalityReviewGuarantorAbilityOrder> guarantorAbilities,
										Date now) {
		if (ListUtil.isEmpty(guarantorAbilities) || csrReviewId <= 0) {
			return;
		}
		
		for (FInvestigationCsRationalityReviewGuarantorAbilityOrder order : guarantorAbilities) {
			if (!order.isNull()) {
				FInvestigationCsRationalityReviewGuarantorAbilityDO doObj = new FInvestigationCsRationalityReviewGuarantorAbilityDO();
				BeanCopier.staticCopy(order, doObj);
				doObj.setTotalCapital(order.getTotalCapital());
				doObj.setIntangibleAssets(order.getIntangibleAssets());
				doObj.setContingentLiability(order.getContingentLiability());
				doObj.setGuaranteeAmount(order.getGuaranteeAmount());
				doObj.setCsrReviewId(csrReviewId);
				doObj.setRawAddTime(now);
				FInvestigationCsRationalityReviewGuarantorAbilityDAO.insert(doObj);
			}
		}
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
		BeanCopier.staticCopy(review, reviewInfo);
		
		OwnerEnum guarantor = OwnerEnum.GUARANTOR;
		
		FInvestigationMainlyReviewDO mainlyReview = FInvestigationMainlyReviewDAO
			.findByFormIdAndOwner(formId, guarantor.code());
		if (null != mainlyReview) {
			FInvestigationMainlyReviewInfo mainlyReviewInfo = new FInvestigationMainlyReviewInfo();
			BeanCopier.staticCopy(mainlyReview, mainlyReviewInfo);
			mainlyReviewInfo.setEnterpriseType(EnterpriseNatureEnum.getByCode(mainlyReview
				.getEnterpriseType()));
			reviewInfo.setMainlyReview(mainlyReviewInfo);
		}
		
		//已获得的资质证书
		List<FInvestigationMainlyReviewCertificateInfo> certificates = new ArrayList<>();
		List<FInvestigationMainlyReviewCertificateDO> certificateDOs = FInvestigationMainlyReviewCertificateDAO
			.findByOwnerAndReviewId(guarantor.code(), csrReviewId);
		for (FInvestigationMainlyReviewCertificateDO certificate : certificateDOs) {
			FInvestigationMainlyReviewCertificateInfo certificateInfo = new FInvestigationMainlyReviewCertificateInfo();
			BeanCopier.staticCopy(certificate, certificateInfo);
			certificates.add(certificateInfo);
		}
		reviewInfo.setCertificates(certificates);
		//客户高管人员列表
		//		List<FInvestigationMabilityReviewLeadingTeamInfo> leadingTeams = new ArrayList<>();
		//		List<FInvestigationMabilityReviewLeadingTeamDO> leadingTeamDOs = FInvestigationMabilityReviewLeadingTeamDAO
		//			.findByOwnerAndReviewId(guarantor.code(), csrReviewId);
		//		for (FInvestigationMabilityReviewLeadingTeamDO doObj : leadingTeamDOs) {
		//			FInvestigationMabilityReviewLeadingTeamInfo leadingTeamInfo = new FInvestigationMabilityReviewLeadingTeamInfo();
		//			BeanCopier.staticCopy(doObj, leadingTeamInfo);
		//			leadingTeams.add(leadingTeamInfo);
		//		}
		List<FInvestigationMabilityReviewLeadingTeamInfo> leadingTeams = queryLeadings(csrReviewId,
			guarantor);
		reviewInfo.setLeadingTeams(leadingTeams);
		
		//保证人主要财务指标
		List<InvestigationFinancialKpiInfo> kpies = new ArrayList<>();
		List<FInvestigationCsRationalityReviewFinancialKpiDO> kpiDOs = FInvestigationCsRationalityReviewFinancialKpiDAO
			.findByReviewId(csrReviewId);
		if (ListUtil.isNotEmpty(kpiDOs)) {
			for (int i = 0; i < kpiDOs.size(); i += 4) {
				InvestigationFinancialKpiInfo kpi = new InvestigationFinancialKpiInfo();
				kpi.setItemName(kpiDOs.get(i).getKpiName());
				kpi.setItemCode(kpiDOs.get(i).getKpiCode());
				kpi.setValue(kpiDOs.get(i).getKpiValue());
				kpi.setValue1(kpiDOs.get(i + 1).getKpiValue());
				kpi.setValue2(kpiDOs.get(i + 2).getKpiValue());
				kpi.setValue3(kpiDOs.get(i + 3).getKpiValue());
				kpies.add(kpi);
			}
			reviewInfo.setHeader(kpies.get(0));
		}
		reviewInfo.setKpies(kpies);
		
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
		
		return reviewInfo;
	}
	
	@Override
	public FormBaseResult saveFInvestigationRisk(final FInvestigationRiskOrder order) {
		return commonFormSaveProcess(order, "保存风险分析结论意见", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				checkProjectPause(order.getProjectCode());
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
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
		BeanCopier.staticCopy(risk, riskInfo);
		return riskInfo;
	}
	
	@Override
	public FormBaseResult saveFInvestigationFinancialReview(final FInvestigationFinancialReviewOrder order) {
		return commonFormSaveProcess(order, "保存客户财务评价", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				checkProjectPause(order.getProjectCode());
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				long reviewId = order.getFReviewId();
				FInvestigationFinancialReviewDO review = FInvestigationFinancialReviewDAO
					.findByFormId(order.getFormId());
				if (order.isInit()) {
					//初始化财务评论页面，预生成基本数据及财务数据解释与说明(固定五条数据)
					if (null == review) {
						review = new FInvestigationFinancialReviewDO();
						BeanCopier.staticCopy(order, review);
						review.setFormId(form.getFormId());
						review.setRawAddTime(now);
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
				} else {
					if (null == review) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"客户财务评价数据未初始化");
					}
					//					if (reviewId != review.getFReviewId()) {
					//						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
					//							"表单已经过期，请刷新页面重新操作");
					//					}
					
					BeanCopier.staticCopy(order, review);
					FInvestigationFinancialReviewDAO.updateByFormId(review);
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
				return null;
			}
		}, null, null);
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
					Money kpiMoney = Money.amout(doObj.getKpiValue());
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
					if (checkExplain && !kpiMoney.equals(explainMoney)) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAILURE,
							doObj.getKpiName() + doObj.getTermType() + "数据解释金额不符");
					}
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
				
				if (StringUtil.equals(doObj.getKpiName(), order.getKpiName())
					&& StringUtil.equals(doObj.getKpiValue(), order.getKpiValue())
					&& StringUtil.equals(doObj.getTermType(), order.getTermType())) {
					continue;//数据没有变动，不需要保存
				}
				
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
	
	//保存财务报表数据
	//	private void saveFinancialKpies(long reviewId, KpiTypeEnum kpiType, List<FInvestigationFinancialReviewKpiOrder> financialKpies, Date now) {
	//		if (ListUtil.isEmpty(financialKpies) || reviewId <= 0) {
	//			return;
	//		}
	//		
	//		for (FInvestigationFinancialReviewKpiOrder order : financialKpies) {
	//			FInvestigationFinancialReviewKpiDO doObj = new FInvestigationFinancialReviewKpiDO();
	//			BeanCopier.staticCopy(order, doObj);
	//			doObj.setFReviewId(reviewId);
	//			doObj.setKpiType(kpiType.code());
	//			doObj.setRawAddTime(now);
	//			FInvestigationFinancialReviewKpiDAO.insert(doObj);
	//		}
	//	}
	
	@Override
	public FInvestigationFinancialReviewInfo findFInvestigationFinancialReviewByFormId(long formId) {
		FInvestigationFinancialReviewDO review = FInvestigationFinancialReviewDAO
			.findByFormId(formId);
		if (null == review) {
			return null;
		}
		
		FInvestigationFinancialReviewInfo reviewIno = new FInvestigationFinancialReviewInfo();
		BeanCopier.staticCopy(review, reviewIno);
		reviewIno.setAmountUnit1(AmountUnitEnum.getByCode(review.getAmountUnit1()));
		reviewIno.setAmountUnit2(AmountUnitEnum.getByCode(review.getAmountUnit2()));
		reviewIno.setAmountUnit3(AmountUnitEnum.getByCode(review.getAmountUnit3()));
		
		installFinacialKpis(reviewIno, review.getFReviewId());
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
		paramMap.put("review", queryOrder.getReview());
		paramMap.put("formStatusList", queryOrder.getFormStatusList());
		paramMap.put("loginUserId", queryOrder.getLoginUserId());
		paramMap.put("deptIdList", queryOrder.getDeptIdList());
		
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
		return baseBatchResult;
	}
	
	@Override
	public FormBaseResult saveFInvestigationLitigation(final FInvestigationLitigationOrder order) {
		return commonFormSaveProcess(order, "保存诉讼担保项目调查报告", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				checkProjectPause(order.getProjectCode());
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				FInvestigationDO investigation = FInvestigationDAO.findByFormId(form.getFormId());
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
			return info;
		}
	}
	
	@Override
	public FormBaseResult saveFInvestigationUnderwriting(final FInvestigationUnderwritingOrder order) {
		return commonFormSaveProcess(order, "保存承销项目情况", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				checkProjectPause(order.getProjectCode());
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				FInvestigationDO investigation = FInvestigationDAO.findByFormId(form.getFormId());
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
			info.setChargeUnit(ChargeUnitEnum.getByCode(doObj.getChargeUnit()));
			info.setChargeWay(UnderwritingChargeWaytEnum.getByCode(doObj.getChargeWay()));
			return info;
		}
	}
	
	@Override
	public FcsBaseResult saveFInvestigationFinancialDataExplain(final FInvestigationFinancialDataExplainOrder order) {
		return commonProcess(order, "保存财务数据解释说明", new BeforeProcessInvokeService() {
			
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
	
	@Override
	public void copyInvestigation(final String projectCode) {
		
		// 复制尽职调查表
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					FcsBaseResult result = copy(projectCode);
					logger.info("复制尽职调查报告结果：" + result);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("复制尽职调查报告异常：" + e.getMessage(), e);
				}
			}
		});
	}
	
	private FcsBaseResult copy(long newFormId, long oldFormId, FInvestigationDO fInvestigationDO) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			FormDO form = formDAO.findByFormId(oldFormId);
			FormCodeEnum formCode = FormCodeEnum.getByCode(form.getFormCode());
			if (formCode == FormCodeEnum.INVESTIGATION_BASE) {
				copyBase(oldFormId, newFormId, fInvestigationDO);
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
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.EXECUTE_FAIL);
			result.setMessage("复制尽调异常");
		}
		return result;
	}
	
	private FcsBaseResult copy(String projectCode) {
		FcsBaseResult result = new FcsBaseResult();
		if (StringUtil.isBlank(projectCode)) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
			result.setMessage("尽调预设:项目编号为空");
			return result;
		}
		
		InvestigationQueryOrder queryOrder = new InvestigationQueryOrder();
		queryOrder.setProjectCode(projectCode);
		queryOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
		queryOrder.setPageSize(1L);
		queryOrder.setPageNumber(1L);
		QueryBaseBatchResult<InvestigationInfo> batchResult = this.queryInvestigation(queryOrder);
		
		if (ListUtil.isEmpty(batchResult.getPageList())) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
			result.setMessage("尽调预设  " + projectCode + ":没有审核通过的尽职调查报告");
			return result;
		}
		
		//审核通过后的尽调表单formId
		SimpleFormProjectVOInfo info = batchResult.getPageList().get(0);
		long oldFormId = info.getFormId();
		FInvestigationDO investigation = FInvestigationDAO.findByFormId(oldFormId);
		FInvestigationOrder order = new FInvestigationOrder();
		order.setProjectCode(investigation.getProjectCode());
		order.setProjectName(investigation.getProjectName());
		order.setCustomerId(investigation.getCustomerId());
		order.setCustomerName(investigation.getCustomerName());
		order.setBusiType(investigation.getBusiType());
		order.setBusiTypeName(investigation.getBusiTypeName());
		//		order.setAmountStr(investigation.getAmount().toStandardString());
		order.setDeclares(investigation.getDeclares());
		order.setInvestigatePlace(investigation.getInvestigatePlace());
		order.setInvestigateDate(investigation.getInvestigateDate());
		order.setInvestigatePersion(investigation.getInvestigatePersion());
		order.setInvestigatePersionId(investigation.getInvestigatePersionId());
		order.setReceptionPersion(investigation.getReceptionPersion());
		order.setReceptionDuty(investigation.getReceptionDuty());
		
		order.setFormCode(info.getFormCode());
		order.setCheckIndex(0);
		order.setCheckStatus(1);
		
		order.setUserId(info.getFormUserId());
		order.setUserAccount(info.getFormUserAccount());
		order.setUserName(info.getFormUserName());
		
		order.setReview(BooleanEnum.IS.code()); //表示隐藏
		
		FormBaseResult newFormResult = this.add(order);
		if (!newFormResult.isSuccess()) {
			result.setSuccess(false);
			result.setFcsResultEnum(newFormResult.getFcsResultEnum());
			result.setMessage(newFormResult.getMessage());
			return result;
		}
		
		long formId = newFormResult.getFormInfo().getFormId();
		if (info.getFormCode() == FormCodeEnum.INVESTIGATION_BASE) {
			copyBase(oldFormId, formId, null);
		} else if (info.getFormCode() == FormCodeEnum.INVESTIGATION_LITIGATION) {
			copyLitigation(oldFormId, formId, null);
		} else if (info.getFormCode() == FormCodeEnum.INVESTIGATION_UNDERWRITING) {
			copyUnderwriting(oldFormId, formId, null);
		} else {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
			result.setMessage("尽调预设:不支持此类型项目");
			return result;
		}
		
		//更新审核状态
		FormDO form = formDAO.findByFormId(formId);
		form.setCheckStatus(info.getFormCode().defaultCheckStatus().replaceAll("0", "1"));
		formDAO.update(form);
		
		ChangeProjectStatusOrder changeOrder = new ChangeProjectStatusOrder();
		changeOrder.setPhase(ProjectPhasesEnum.INVESTIGATING_PHASES);
		changeOrder.setPhaseStatus(ProjectPhasesStatusEnum.WAITING);
		changeOrder.setProjectCode(projectCode);
		changeOrder.setStatus(ProjectStatusEnum.NORMAL);
		ProjectResult presult = projectService.changeProjectStatus(changeOrder);
		if (!presult.isSuccess()) {
			logger.error("更新项目状态出错：" + presult.getMessage());
		}
		
		logger.info("尽调预设:已经完成对尽职调查的复制，项目编辑：" + projectCode);
		
		return result;
	}
	
	private void copyBase(long oldFormId, long newFormId, FInvestigationDO fInvestigationDO) {
		copyCreditScheme(oldFormId, newFormId, fInvestigationDO);
		copyMainlyReview(oldFormId, newFormId, fInvestigationDO);
		copyMabilityReview(oldFormId, newFormId, fInvestigationDO);
		copyOpabilityReview(oldFormId, newFormId, fInvestigationDO);
		copyFinancialReview(oldFormId, newFormId, fInvestigationDO);
		copyMajorEvent(oldFormId, newFormId, fInvestigationDO);
		copyProjectStatus(oldFormId, newFormId, fInvestigationDO);
		copyCsRationalityReview(oldFormId, newFormId, fInvestigationDO);
		copyRisk(oldFormId, newFormId, fInvestigationDO);
	}
	
	//诉讼担保项目调查报告
	private void copyLitigation(long oldFormId, long newFormId, FInvestigationDO fInvestigationDO) {
		FInvestigationLitigationDO litigation = FInvestigationLitigationDAO.findByFormId(oldFormId);
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
		FInvestigationUnderwritingDAO.insert(doObj);
	}
	
	//授信方案
	private void copyCreditScheme(long oldFormId, long newFormId, FInvestigationDO fInvestigationDO) {
		Date now = getSysdate();
		FInvestigationCreditSchemeDO creditScheme = FInvestigationCreditSchemeDAO
			.findByFormId(oldFormId);
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
		
		ProjectInfo project = projectService.queryByCode(fInvestigationDO.getProjectCode(), false);
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
		List<CommonAttachmentDO> counterAttaches1 = null;
		if (null != guaranteeEntrustedInfo) {
			newCreditScheme.setCapitalChannelId(guaranteeEntrustedInfo.getCapitalChannelId());
			newCreditScheme.setCapitalChannelName(guaranteeEntrustedInfo.getCapitalChannelName());
			//二级渠道
			newCreditScheme.setCapitalSubChannelId(guaranteeEntrustedInfo.getCapitalSubChannelId());
			newCreditScheme
				.setCapitalSubChannelName(guaranteeEntrustedInfo.getCapitalSubChannelName());
			newCreditScheme.setProjectChannelId(guaranteeEntrustedInfo.getProjectChannelId());
			newCreditScheme.setProjectChannelName(guaranteeEntrustedInfo.getProjectChannelName());
			newCreditScheme.setProjectSubChannelId(guaranteeEntrustedInfo.getProjectSubChannelId());
			newCreditScheme
				.setProjectSubChannelName(guaranteeEntrustedInfo.getProjectSubChannelName());
			
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
		
		CommonAttachmentDO commonAttachment2 = new CommonAttachmentDO();
		commonAttachment2.setBizNo(oldFormId + "");
		commonAttachment2.setModuleType(CommonAttachmentTypeEnum.COUNTER_GUARANTEE.code());
		List<CommonAttachmentDO> counterAttaches2 = commonAttachmentDAO
			.findByBizNoModuleType(commonAttachment2);
		if (ListUtil.isNotEmpty(counterAttaches1)) {
			copyAttachment(counterAttaches1, newFormId + "",
				CommonAttachmentTypeEnum.COUNTER_GUARANTEE.code(), "");
		} else {
			copyAttachment(counterAttaches2, newFormId + "",
				CommonAttachmentTypeEnum.COUNTER_GUARANTEE.code(), "");
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
			newFormId + "_index0");
		
		logger.info("尽调预设:已经完成对尽职调查的复制:授信方案");
	}
	
	private void copyAttachment(List<CommonAttachmentDO> attachments, String bizNo,
								String moduleType, String childId) {
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
				commonAttachmentDAO.insert(doObj);
			}
		}
	}
	
	private void copyAttachments(String bizNoOld, String moduleTypeOld, String bizNo) {
		CommonAttachmentDO commonAttachment = new CommonAttachmentDO();
		commonAttachment.setBizNo(bizNoOld);
		commonAttachment.setModuleType(moduleTypeOld);
		List<CommonAttachmentDO> counterAttaches = commonAttachmentDAO
			.findByBizNoModuleType(commonAttachment);
		copyAttachment(counterAttaches, bizNo, moduleTypeOld, "");
	}
	
	//主体评价
	private void copyMainlyReview(long oldFormId, long newFormId, FInvestigationDO fInvestigationDO) {
		Date now = getSysdate();
		OwnerEnum customer = OwnerEnum.CUSTOMER;
		FInvestigationMainlyReviewDO review = FInvestigationMainlyReviewDAO.findByFormIdAndOwner(
			oldFormId, customer.code());
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
		CompanyCustomerInfo customerInfo = companyCustomerServiceClient.queryByUserId(
			review.getCustomerId(), null);
		if (customerInfo != null) {
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
		
		if (customerInfo != null) {
			//资质证书
			if (ListUtil.isNotEmpty(customerInfo.getCompanyQualification())) {
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
			newFormId + "_index1");
		logger.info("尽调预设:已经完成对尽职调查的复制:主体评价");
	}
	
	//管理能力评价
	private void copyMabilityReview(long oldFormId, long newFormId,
									FInvestigationDO fInvestigationDO) {
		Date now = getSysdate();
		FInvestigationMabilityReviewDO review = FInvestigationMabilityReviewDAO
			.findByFormId(oldFormId);
		
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
			newFormId + "_index2");
		logger.info("尽调预设:已经完成对尽职调查的复制:管理能力评价");
	}
	
	//客户经营能力评价
	private void copyOpabilityReview(long oldFormId, long newFormId,
										FInvestigationDO fInvestigationDO) {
		Date now = getSysdate();
		FInvestigationOpabilityReviewDO review = FInvestigationOpabilityReviewDAO
			.findByFormId(oldFormId);
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
			newFormId + "_index3");
		logger.info("尽调预设:已经完成对尽职调查的复制:客户经营能力评价");
	}
	
	//客户财务评价
	private void copyFinancialReview(long oldFormId, long newFormId,
										FInvestigationDO fInvestigationDO) {
		Date now = getSysdate();
		FInvestigationFinancialReviewDO review = FInvestigationFinancialReviewDAO
			.findByFormId(oldFormId);
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
		
		Map<Long, Long> map = new HashMap<>();
		List<FInvestigationFinancialReviewKpiDO> datas = new ArrayList<>();
		for (FInvestigationFinancialReviewKpiDO kpi : financialKpies) {
			if (kpi.getParentId() > 0) {
				datas.add(kpi);
				continue;
			}
			
			FInvestigationFinancialReviewKpiDO doObj = new FInvestigationFinancialReviewKpiDO();
			BeanCopier.staticCopy(kpi, doObj);
			doObj.setId(0L);
			doObj.setFReviewId(FReviewId);
			doObj.setRawAddTime(now);
			long id = FInvestigationFinancialReviewKpiDAO.insert(doObj);
			if (KpiTypeEnum.FINANCIAL_DATA.code().equals(kpi.getKpiType())) {
				List<FInvestigationFinancialReviewKpiDO> subDatas = FInvestigationFinancialReviewKpiDAO.findByParentId(kpi.getId());
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
				map.put(kpi.getId(), id);
			}
		}
		
		// 数据解释说明的parentId需要重新设置
		if (ListUtil.isNotEmpty(datas)) {
			for (FInvestigationFinancialReviewKpiDO kpi : datas) {
				Long parentId = map.get(kpi.getParentId());
				if (null == parentId) {
					continue;
				}
				FInvestigationFinancialReviewKpiDO doObj = new FInvestigationFinancialReviewKpiDO();
				BeanCopier.staticCopy(kpi, doObj);
				doObj.setId(0L);
				doObj.setFReviewId(FReviewId);
				doObj.setParentId(parentId);
				doObj.setRawAddTime(now);
				FInvestigationFinancialReviewKpiDAO.insert(doObj);
			}
		}
		
		//复制附件
		copyAttachments(oldFormId + "_index4", CommonAttachmentTypeEnum.FORM_ATTACH.code(),
			newFormId + "_index4");
		logger.info("尽调预设:已经完成对尽职调查的复制:客户财务评价");
	}
	
	//重大事项调查
	private void copyMajorEvent(long oldFormId, long newFormId, FInvestigationDO fInvestigationDO) {
		FInvestigationMajorEventDO event = FInvestigationMajorEventDAO.findByFormId(oldFormId);
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
			newFormId + "_index5");
		logger.info("尽调预设:已经完成对尽职调查的复制:重大事项调查");
	}
	
	//项目情况调查
	private void copyProjectStatus(long oldFormId, long newFormId, FInvestigationDO fInvestigationDO) {
		Date now = getSysdate();
		FInvestigationProjectStatusDO status = FInvestigationProjectStatusDAO
			.findByFormId(oldFormId);
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
		
		//复制附件
		copyAttachments(oldFormId + "", CommonAttachmentTypeEnum.INVESTIGATION_6.code(), newFormId
																							+ "");
		//复制附件
		copyAttachments(oldFormId + "_index6", CommonAttachmentTypeEnum.FORM_ATTACH.code(),
			newFormId + "_index6");
		logger.info("尽调预设:已经完成对尽职调查的复制:项目情况调查");
	}
	
	//授信方案合理性评价
	private void copyCsRationalityReview(long oldFormId, long newFormId,
											FInvestigationDO fInvestigationDO) {
		Date now = getSysdate();
		FInvestigationCsRationalityReviewDO review = FInvestigationCsRationalityReviewDAO
			.findByFormId(oldFormId);
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
		
		OwnerEnum guarantor = OwnerEnum.GUARANTOR;
		
		FInvestigationMainlyReviewDO mainlyReview = FInvestigationMainlyReviewDAO
			.findByFormIdAndOwner(oldFormId, guarantor.code());
		FInvestigationMainlyReviewDO newMainlyReview = new FInvestigationMainlyReviewDO();
		BeanCopier.staticCopy(mainlyReview, newMainlyReview);
		newMainlyReview.setMReviewId(0L);
		newMainlyReview.setFormId(newFormId);
		newMainlyReview.setRawAddTime(now);
		FInvestigationMainlyReviewDAO.insert(newMainlyReview);
		
		//已获得的资质证书
		List<FInvestigationMainlyReviewCertificateDO> certificateDOs = FInvestigationMainlyReviewCertificateDAO
			.findByOwnerAndReviewId(guarantor.code(), review.getCsrReviewId());
		for (FInvestigationMainlyReviewCertificateDO certificate : certificateDOs) {
			FInvestigationMainlyReviewCertificateDO doObj = new FInvestigationMainlyReviewCertificateDO();
			BeanCopier.staticCopy(certificate, doObj);
			doObj.setMReviewId(0L);
			doObj.setMReviewId(csrReviewId);
			doObj.setRawAddTime(now);
			FInvestigationMainlyReviewCertificateDAO.insert(doObj);
		}
		
		//客户高管人员列表
		List<FInvestigationMabilityReviewLeadingTeamDO> leadingTeamDOs = FInvestigationMabilityReviewLeadingTeamDAO
			.findByOwnerAndReviewId(guarantor.code(), review.getCsrReviewId());
		for (FInvestigationMabilityReviewLeadingTeamDO leadingTeam : leadingTeamDOs) {
			FInvestigationMabilityReviewLeadingTeamDO doObj = new FInvestigationMabilityReviewLeadingTeamDO();
			BeanCopier.staticCopy(leadingTeam, doObj);
			doObj.setId(0L);
			doObj.setMaReviewId(csrReviewId);
			doObj.setRawAddTime(now);
			long tid = FInvestigationMabilityReviewLeadingTeamDAO.insert(doObj);
			List<FInvestigationMabilityReviewLeadingTeamResumeDO> resumes = FInvestigationMabilityReviewLeadingTeamResumeDAO
				.findByReviewIdOwnerTid(review.getCsrReviewId(), guarantor.code(),
					leadingTeam.getId());
			if (ListUtil.isNotEmpty(resumes)) {
				for (FInvestigationMabilityReviewLeadingTeamResumeDO resume : resumes) {
					FInvestigationMabilityReviewLeadingTeamResumeDO doObj2 = new FInvestigationMabilityReviewLeadingTeamResumeDO();
					BeanCopier.staticCopy(resume, doObj2);
					doObj2.setId(0L);
					doObj2.setMaReviewId(csrReviewId);
					doObj2.setTid(tid);
					doObj2.setRawAddTime(now);
					FInvestigationMabilityReviewLeadingTeamResumeDAO.insert(doObj2);
				}
			}
		}
		
		//保证人主要财务指标
		List<FInvestigationCsRationalityReviewFinancialKpiDO> kpiDOs = FInvestigationCsRationalityReviewFinancialKpiDAO
			.findByReviewId(review.getCsrReviewId());
		for (FInvestigationCsRationalityReviewFinancialKpiDO kpi : kpiDOs) {
			FInvestigationCsRationalityReviewFinancialKpiDO doObj = new FInvestigationCsRationalityReviewFinancialKpiDO();
			BeanCopier.staticCopy(kpi, doObj);
			doObj.setId(0L);
			doObj.setCsrReviewId(csrReviewId);
			doObj.setRawAddTime(now);
			FInvestigationCsRationalityReviewFinancialKpiDAO.insert(doObj);
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
			newFormId + "_index7");
		logger.info("尽调预设:已经完成对尽职调查的复制:授信方案合理性评价");
	}
	
	//风险点分析和结论意见
	private void copyRisk(long oldFormId, long newFormId, FInvestigationDO fInvestigationDO) {
		FInvestigationRiskDO risk = FInvestigationRiskDAO.findByFormId(oldFormId);
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
			newFormId + "_index8");
		logger.info("尽调预设:已经完成对尽职调查的复制:风险点分析和结论意见");
	}
	
}
