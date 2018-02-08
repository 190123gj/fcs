package com.born.fcs.pm.biz.service.check;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.convert.DoConvert;
import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.convert.VoConvert;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.daointerface.FFinancialKpiDAO;
import com.born.fcs.pm.dal.dataobject.CommonAttachmentDO;
import com.born.fcs.pm.dal.dataobject.FAfterwardsCheckBaseDO;
import com.born.fcs.pm.dal.dataobject.FAfterwardsCheckCollectionDO;
import com.born.fcs.pm.dal.dataobject.FAfterwardsCheckCommonDO;
import com.born.fcs.pm.dal.dataobject.FAfterwardsCheckDO;
import com.born.fcs.pm.dal.dataobject.FAfterwardsCheckLitigationDO;
import com.born.fcs.pm.dal.dataobject.FAfterwardsCheckLoanDO;
import com.born.fcs.pm.dal.dataobject.FAfterwardsCheckReportCapitalDO;
import com.born.fcs.pm.dal.dataobject.FAfterwardsCheckReportContentDO;
import com.born.fcs.pm.dal.dataobject.FAfterwardsCheckReportContentExtendDO;
import com.born.fcs.pm.dal.dataobject.FAfterwardsCheckReportFinancialDO;
import com.born.fcs.pm.dal.dataobject.FAfterwardsCheckReportIncomeDO;
import com.born.fcs.pm.dal.dataobject.FAfterwardsCheckReportItemDO;
import com.born.fcs.pm.dal.dataobject.FAfterwardsCheckReportProjectDO;
import com.born.fcs.pm.dal.dataobject.FFinancialKpiDO;
import com.born.fcs.pm.dal.dataobject.FormDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dataobject.AfterwardsCheckDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.DataFinancialHelper;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.AfterCheckItemTypeEnum;
import com.born.fcs.pm.ws.enums.AmountUnitEnum;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CapitalTypeEnum;
import com.born.fcs.pm.ws.enums.CaseStatusEnum;
import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.CheckReportEditionEnums;
import com.born.fcs.pm.ws.enums.CollectCodeEnum;
import com.born.fcs.pm.ws.enums.CollectTypeEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialTypeEnum;
import com.born.fcs.pm.ws.enums.KpiTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.check.AfterwardsCheckInfo;
import com.born.fcs.pm.ws.info.check.AfterwardsCheckReportFinancialInfo;
import com.born.fcs.pm.ws.info.check.AfterwardsCheckReportItemInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckBaseInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckCollectionInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckLitigationInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckLoanInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckReportCapitalInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckReportContentInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckReportFinancialInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckReportIncomeInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckReportItemInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckReportProjectInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckReportProjectSimpleInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectBondInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectEntrustedInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectGuaranteeInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectInfo;
import com.born.fcs.pm.ws.info.financialkpi.FinancialKpiInfo;
import com.born.fcs.pm.ws.order.check.AfterwordsCheckQueryOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckBaseOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckCollectionOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckLitigationOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckLoanOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckQueryOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckReportCapitalOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckReportContentOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckReportFinancialOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckReportIncomeOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckReportItemOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckReportProjectOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckReportProjectSimpleOrder;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.check.AfterwardsCheckService;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.council.CouncilSummaryService;
import com.born.fcs.pm.ws.service.financialkpi.FinancialKpiService;
import com.born.fcs.pm.ws.service.fund.ChargeNotificationService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * 保后检查报告
 * 
 * 
 * @author Fei
 * 
 */
@Service("afterwardsCheckService")
public class AfterwardsCheckServiceImpl extends BaseFormAutowiredDomainService implements
																				AfterwardsCheckService {
	
	@Autowired
	private FinancialKpiService financialKpiService;
	
	@Autowired
	private FFinancialKpiDAO FFinancialKpiDAO;
	
	@Autowired
	private ProjectService projectService;
	@Autowired
	private CouncilSummaryService councilSummaryService;
	@Autowired
	private ProjectRelatedUserService projectRelatedUserService;
	@Autowired
	private ChargeNotificationService chargeNotificationService;
	
	@Override
	public QueryBaseBatchResult<AfterwardsCheckInfo> list(AfterwordsCheckQueryOrder queryOrder) {
		QueryBaseBatchResult<AfterwardsCheckInfo> baseBatchResult = new QueryBaseBatchResult<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("projectCode", queryOrder.getProjectCode());
		paramMap.put("projectName", queryOrder.getProjectName());
		paramMap.put("customerId", queryOrder.getCustomerId());
		paramMap.put("customerName", queryOrder.getCustomerName());
		paramMap.put("edition", queryOrder.getEdition());
		paramMap.put("isLatest", queryOrder.getIsLatest());
		paramMap.put("busiManagerName", queryOrder.getBusiManagerName());
		if (null != queryOrder.getProjectStatus()) {
			paramMap.put("projectStatus", queryOrder.getProjectStatus().code());
		}
		paramMap.put("formStatus", queryOrder.getFormStatus());
		paramMap.put("loginUserId", queryOrder.getLoginUserId());
		paramMap.put("deptIdList", queryOrder.getDeptIdList());
		
		long totalSize = extraDAO.searchAfterwardsCheckCount(paramMap);
		
		PageComponent component = new PageComponent(queryOrder, totalSize);
		if (totalSize > 0) {
			paramMap.put("sortCol", queryOrder.getSortCol());
			paramMap.put("sortOrder", queryOrder.getSortOrder());
			paramMap.put("limitStart", queryOrder.getLimitStart());
			paramMap.put("pageSize", queryOrder.getPageSize());
		}
		List<AfterwardsCheckDO> pageList = extraDAO.searchAfterwardsCheck(paramMap);
		
		List<AfterwardsCheckInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (AfterwardsCheckDO doObj : pageList) {
				AfterwardsCheckInfo info = new AfterwardsCheckInfo();
				VoConvert.convertSimpleFormProjectDO2VoInfo(doObj, info);
				BeanCopier.staticCopy(doObj, info);
				info.setCheckId(doObj.getCheckId());
				info.setRoundYear(doObj.getRoundYear());
				info.setRoundTime(doObj.getRoundTime());
				info.setEdition(CheckReportEditionEnums.getByCode(doObj.getEdition()));
				list.add(info);
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public FAfterwardsCheckInfo findByFormId(long formId) {
		FAfterwardsCheckDO check = FAfterwardsCheckDAO.findByFormId(formId);
		return convertToInfo(check);
	}
	
	@Override
	public FormBaseResult save(final FAfterwardsCheckOrder order) {
		return commonFormSaveProcess(order, "保后检查报告", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				final Date now = FcsPmDomainHolder.get().getSysDate();
				// 取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				long id = order.getId();
				FAfterwardsCheckDO checkDO = FAfterwardsCheckDAO.findByFormId(form.getFormId());
				if (null == checkDO) {
					checkDO = new FAfterwardsCheckDO();
					BeanCopier.staticCopy(order, checkDO, UnBoxingConverter.getInstance());
					checkDO.setFormId(form.getFormId());
					checkDO.setRawAddTime(now);
					
					// String rounds =
					// dateSeqService.getNextAfterwardsCheckSeq(checkDO
					// .getProjectCode());
					// checkDO.setRounds(rounds);
					
					id = FAfterwardsCheckDAO.insert(checkDO);
					if (order.getCopiedFormId() > 0) {
						copy(checkDO, order.getCopiedFormId());
					}
				} else {
					if (checkDO.getId() != order.getId()) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到保后检查报告 ");
					}
					
					BeanCopier.staticCopy(order, checkDO, UnBoxingConverter.getInstance());
					FAfterwardsCheckDAO.update(checkDO);
					
					id = checkDO.getId();
				}
				
				FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
					"result");
				result.setKeyId(id);
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FAfterwardsCheckBaseInfo findBaseInfoByFormId(long formId) {
		FAfterwardsCheckDO check = FAfterwardsCheckDAO.findByFormId(formId);
		if (null == check) {
			return null;
		}
		
		FAfterwardsCheckBaseDO base = FAfterwardsCheckBaseDAO.findByFormId(formId);
		if (null == base) {
			return null;
		}
		
		FAfterwardsCheckBaseInfo baseInfo = new FAfterwardsCheckBaseInfo();
		BeanCopier.staticCopy(base, baseInfo);
		baseInfo.setTimeUnit(TimeUnitEnum.getByCode(base.getTimeUnit()));
		
		queryCollections(formId, baseInfo);
		
		return baseInfo;
	}
	
	/**
	 * 
	 * 查询反担保方式及收集的资料
	 * 
	 * @param formId
	 * @param baseInfo
	 */
	private void queryCollections(long formId, FAfterwardsCheckBaseInfo baseInfo) {
		List<FAfterwardsCheckCollectionDO> items = FAfterwardsCheckCollectionDAO
			.findByFormId(formId);
		if (ListUtil.isEmpty(items)) {
			return;
		}
		
		List<FAfterwardsCheckCollectionInfo> counters = new ArrayList<>();
		List<FAfterwardsCheckCollectionInfo> collections = new ArrayList<>();
		
		for (FAfterwardsCheckCollectionDO item : items) {
			FAfterwardsCheckCollectionInfo collection = new FAfterwardsCheckCollectionInfo();
			BeanCopier.staticCopy(item, collection);
			collection.setCollectType(CollectTypeEnum.getByCode(item.getCollectType()));
			collection.setCollectCode(CollectCodeEnum.getByCode(item.getCollectCode()));
			if (CollectTypeEnum.COUNTER == collection.getCollectType()) {
				counters.add(collection);
			} else if (CollectTypeEnum.COLLECTION == collection.getCollectType()) {
				collections.add(collection);
			}
		}
		
		baseInfo.setCounters(counters);
		baseInfo.setCollections(collections);
	}
	
	@Override
	public FormBaseResult saveBaseInfo(final FAfterwardsCheckBaseOrder order) {
		return commonFormSaveProcess(order, "保存保后检查报告-项目基本信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = FcsPmDomainHolder.get().getSysDate();
				// 取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				order.setFormId(form.getFormId());
				
				FAfterwardsCheckBaseDO base = FAfterwardsCheckBaseDAO.findByFormId(order
					.getFormId());
				if (null == base) {
					base = new FAfterwardsCheckBaseDO();
					BeanCopier.staticCopy(order, base);
					base.setFormId(form.getFormId());
					base.setRawAddTime(now);
					FAfterwardsCheckBaseDAO.insert(base);
				} else {
					order.setBaseId(base.getBaseId());
					// if (order.getBaseId() != base.getBaseId()) {
					// throw
					// ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
					// "未找到保后检查报告-项目基本信息");
					// }
					
					BeanCopier.staticCopy(order, base);
					base.setFormId(form.getFormId());
					FAfterwardsCheckBaseDAO.update(base);
				}
				
				saveCollections(order);
				
				FcsBaseResult result = (FcsBaseResult) FcsPmDomainHolder.get().getAttribute(
					"result");
				result.setKeyId(base.getBaseId());
				
				return null;
			}
			
		}, null, null);
	}
	
	/**
	 * 
	 * 保存反担保方式及收集的资料
	 * 
	 * @param order
	 */
	private void saveCollections(FAfterwardsCheckBaseOrder order) {
		saveCollections(order.getFormId(), CollectTypeEnum.COUNTER, order.getCounters());
		saveCollections(order.getFormId(), CollectTypeEnum.COLLECTION, order.getCollections());
	}
	
	private void saveCollections(long formId, CollectTypeEnum type,
									List<FAfterwardsCheckCollectionOrder> orders) {
		if (formId <= 0 || type == null) {
			return;
		}
		
		if (ListUtil.isEmpty(orders)) {
			FAfterwardsCheckCollectionDAO.deleteByFormIdAndType(formId, type.code());
			return;
		}
		
		List<FAfterwardsCheckCollectionDO> items = FAfterwardsCheckCollectionDAO
			.findByFormIdAndType(formId, type.code());
		Map<Long, FAfterwardsCheckCollectionDO> map = new HashMap<>();
		if (ListUtil.isNotEmpty(items)) {
			for (FAfterwardsCheckCollectionDO item : items) {
				map.put(item.getCollectId(), item);
			}
		}
		
		int sortOrder = 1;
		Date now = getSysdate();
		for (FAfterwardsCheckCollectionOrder order : orders) {
			order.setFormId(formId);
			order.setSortOrder(sortOrder++);
			order.setCollectType(type.code());
			FAfterwardsCheckCollectionDO item = map.get(order.getCollectId());
			if (null == item) {
				item = new FAfterwardsCheckCollectionDO();
				BeanCopier.staticCopy(order, item);
				item.setRawAddTime(now);
				FAfterwardsCheckCollectionDAO.insert(item);
			} else {
				if (!isEqual(order, item)) {
					BeanCopier.staticCopy(order, item);
					FAfterwardsCheckCollectionDAO.update(item);
				}
			}
			
			map.remove(order.getCollectId());
		}
		
		if (null != map && map.size() > 0) {
			for (long collectId : map.keySet()) {
				FAfterwardsCheckCollectionDAO.deleteById(collectId);
			}
		}
	}
	
	private boolean isEqual(FAfterwardsCheckCollectionOrder order, FAfterwardsCheckCollectionDO item) {
		return order.getSortOrder() == item.getSortOrder()
				&& StringUtil.equals(order.getCollectItem(), item.getCollectItem())
				&& StringUtil.equals(order.getCollectCode(), item.getCollectCode())
				&& StringUtil.equals(order.getCollected(), item.getCollected());
	}
	
	@Override
	public FAfterwardsCheckReportContentInfo findContentByFomrId(long formId) {
		FAfterwardsCheckDO check = FAfterwardsCheckDAO.findByFormId(formId);
		if (null == check) {
			return null;
		}
		
		CheckReportEditionEnums edition = CheckReportEditionEnums.getByCode(check.getEdition());
		FAfterwardsCheckReportContentInfo contentInfo = new FAfterwardsCheckReportContentInfo();
		
		FAfterwardsCheckReportContentDO contentDO = FAfterwardsCheckReportContentDAO
			.findByFormId(formId);
		if (null == contentDO) {
			// 做一些初始化数据
			if (CheckReportEditionEnums.V_REAL_ESTATE == edition) {
				initProjects(formId, check.getProjectCode());
			}
			contentInfo.setFormId(formId);
		} else {
			BeanCopier.staticCopy(contentDO, contentInfo);
			contentInfo.setAmountUnit1(AmountUnitEnum.getByCode(contentDO.getAmountUnit1()));
			contentInfo.setAmountUnit2(AmountUnitEnum.getByCode(contentDO.getAmountUnit2()));
			contentInfo.setAmountUnit3(AmountUnitEnum.getByCode(contentDO.getAmountUnit3()));
		}
		
		contentInfo.setProjectCode(check.getProjectCode());
		long oldFormId = 0L;
		FAfterwardsCheckInfo lastEdition = queryLastEdition(check.getProjectCode());
		if (null != lastEdition) {
			oldFormId = lastEdition.getFormId();
		}
		
		// 查询财务报表数据
		queryFinancialKpiInfo(contentInfo);
		// 获取财务报表科目数据
		queryFinancialItemInfo(formId, contentInfo, edition);
		// 查询银行贷款及其他融资
		queryLoans(contentInfo);
		// 查询核实收入,反担保措施等公共数据
		queryItems(contentInfo);
		
		queryCapitals(contentInfo);
		
		if (CheckReportEditionEnums.V_COMMON == edition) {
			
		} else if (CheckReportEditionEnums.V_MANUFACTURING == edition) {
			// 生产制造业
			// 企业收入核实情况工作底稿（调查科目、核实过程可根据实际情况进行调整填写）
			queryCheckIncomes(contentInfo, check.getProjectCode());
			// （4）企业成本核实情况工作底稿（调查科目、核实过程可根据实际情况进行调整填写）
			contentInfo.setCostInfos(convert(contentInfo.getCosts()));
			// 其他重要事项核查（根据项目具体情况可做调整）
			contentInfo.setOtherInfos(convert(contentInfo.getOthers()));
		} else if (CheckReportEditionEnums.V_REAL_ESTATE == edition) {
			// 房地产开发
			// 开发项目完成情况检查（项目较多的列举前五户）
			// 项目内容 初始化
			queryProjectsSimple(contentInfo);
			contentInfo.setCostInfos(convert(contentInfo.getCosts()));
			// 带出历史企业信用状况
			if (contentInfo.getContentId() == 0) {
				List<FAfterwardsCheckReportCapitalInfo> creditDetails = queryCapital(oldFormId,
					formId, CapitalTypeEnum.CREDIT_DETAIL);
				if (ListUtil.isNotEmpty(creditDetails)) {
					contentInfo.setCreditDetails(creditDetails);
				}
			}
		} else if (CheckReportEditionEnums.V_CONSTRUCTION == edition) {
			// 建筑行业 带出历史数据
			if (contentInfo.getContentId() == 0) {
				//（3）项目分包商
				List<FAfterwardsCheckReportCapitalInfo> subContractors = queryCapital(oldFormId, formId,
					CapitalTypeEnum.SUB_CONTRACTOR);
				if (ListUtil.isNotEmpty(subContractors)) {
					contentInfo.setSubContractors(subContractors);
				}
				//（5）在建项目情况表
				List<FAfterwardsCheckReportCapitalInfo> projectings = queryCapital(oldFormId,
					formId, CapitalTypeEnum.PROJECTING);
				if (ListUtil.isNotEmpty(projectings)) {
					contentInfo.setProjectings(projectings);
				}
				//（6）已中标未开工项目情况表
				List<FAfterwardsCheckReportCapitalInfo> successfulProjects = queryCapital(
					oldFormId, formId, CapitalTypeEnum.SUCCESSFUL_PROJECT);
				if (ListUtil.isNotEmpty(successfulProjects)) {
					contentInfo.setSuccessfulProjects(successfulProjects);
				}
			}
		} else if (CheckReportEditionEnums.V_CITY == edition) {
			// 带出历史企业信用状况
			if (contentInfo.getContentId() == 0) {
				//（3）企业资产负债划转明细
				List<FAfterwardsCheckReportCapitalInfo> debtDetails = queryCapital(oldFormId,
					formId, CapitalTypeEnum.DEBT_DETAIL);
				if (ListUtil.isNotEmpty(debtDetails)) {
					contentInfo.setDebtDetails(debtDetails);
				}
				//（4）在建项目进度及投入情况调查工作底稿
				List<FAfterwardsCheckReportCapitalInfo> projectings = queryCapital(oldFormId,
					formId, CapitalTypeEnum.PROJECTING);
				if (ListUtil.isNotEmpty(projectings)) {
					contentInfo.setProjectings(projectings);
				}
			}
			
		} else if (CheckReportEditionEnums.V_LOAN == edition) {
			FAfterwardsCheckCommonDO common = FAfterwardsCheckCommonDAO.findByProjectCode(check
				.getProjectCode());
			if (null != common) {
				contentInfo.setLoanLimitDataCommon(common.getLoanLimitData());
				contentInfo.setLoanAmountDataCommon(common.getLoanAmountData());
			}
			FAfterwardsCheckReportContentExtendDO extend = FAfterwardsCheckReportContentExtendDAO
				.findByFormId(formId);
			if (null != extend) {
				contentInfo.setLoanLimitData(extend.getValue1());
				contentInfo.setLoanAmountData(extend.getValue2());
			}
		}
		
		return contentInfo;
	}
	
	/**
	 * 
	 * 开发项目完成情况检查(5条数据)
	 * 
	 * @param formId
	 */
	private void initProjects(long formId, String projectCode) {
		if (formId <= 0) {
			return;
		}
		List<FAfterwardsCheckReportProjectDO> items = FAfterwardsCheckReportProjectDAO
			.findByFormId(formId);
		if (ListUtil.isEmpty(items)) {
			FAfterwardsCheckInfo checkInfo = this.queryLastEdition(projectCode);
			if (null == checkInfo) {
				// 没有历史通过审核的数据
				Date now = getSysdate();
				for (int i = 1; i <= 5; i++) {
					FAfterwardsCheckReportProjectDO item = new FAfterwardsCheckReportProjectDO();
					item.setFormId(formId);
					item.setSortOrder(i);
					item.setRawAddTime(now);
					item.setDelAble(BooleanEnum.NO.code()); // 前五条数据默认不可删除
					FAfterwardsCheckReportProjectDAO.insert(item);
				}
			} else {
				copyProject(checkInfo.getFormId(), formId, getSysdate());
			}
		}
		
	}
	
	/**
	 * 
	 * 查询财务报表数据
	 * 
	 * @param info
	 */
	private void queryFinancialKpiInfo(FAfterwardsCheckReportContentInfo info) {
		Map<KpiTypeEnum, List<FinancialKpiInfo>> map = financialKpiService.queryByFormId(info
			.getFormId());
		if (null != map) {
			info.setFinancials(map.get(KpiTypeEnum.FINANCIAL_TABLE));
			addClass(info.getFinancials());
			info.setProfits(map.get(KpiTypeEnum.PROFIT_TABLE));
			addClass(info.getProfits());
			info.setFlows(map.get(KpiTypeEnum.FLOW_TABLE));
			addClass(info.getFlows());
			
			info.setContractProjects(map.get(KpiTypeEnum.CONTRACT_PROJECT));
			
			info.setHolderBackgrounds(map.get(KpiTypeEnum.HOLDER_BACKGROUND));
			info.setLoanIndusties(map.get(KpiTypeEnum.LOAN_INDUSTY));
			info.setCreditStructures(map.get(KpiTypeEnum.CREDIT_STRUCTURE));
			info.setLoanTimeLimits(map.get(KpiTypeEnum.LOAN_TIME_LIMIT));
			info.setLoanAmounts(map.get(KpiTypeEnum.LOAN_AMOUNT));
			info.setAverageCapitals(map.get(KpiTypeEnum.AVERAGE_CAPITAL));
			info.setLoanQualityLevels(map.get(KpiTypeEnum.LOAN_QUALITY_LEVEL));
			info.setBadLoans(map.get(KpiTypeEnum.BAD_LOAN));
		}
	}
	
	private void addClass(List<FinancialKpiInfo> kpies) {
		if (ListUtil.isNotEmpty(kpies)) {
			for (FinancialKpiInfo kpi : kpies) {
				kpi.setKpiClass(DataFinancialHelper.getKpiClassByCode(kpi.getKpiCode()));
			}
		}
	}
	
	private void queryFinancialItemInfo(long formId, FAfterwardsCheckReportContentInfo contentInfo,
										CheckReportEditionEnums edition) {
		// 获取财务报表科目数据
		Map<FinancialTypeEnum, List<AfterwardsCheckReportFinancialInfo>> map = queryFinancialInfos(formId);
		if (null != map) {
			contentInfo.setCapitals(map.get(FinancialTypeEnum.CAPITAL));
			contentInfo.setDebts(map.get(FinancialTypeEnum.DEBT));
			contentInfo.setFothers(map.get(FinancialTypeEnum.OTHER));
			contentInfo.setSubjects(map.get(FinancialTypeEnum.SUBJECTS));
			return;
		}
		
		//		if (CheckReportEditionEnums.V_COMMON == edition || CheckReportEditionEnums.V_CITY == edition) {
		// 获取最近一期的保后检查报告
		FAfterwardsCheckDO check = extraDAO.searchAfterCheckEdition(contentInfo.getProjectCode());
		if (null == check) {
			return;
		}
		map = queryFinancialInfos(check.getFormId());
		if (null == map) {
			return;
		}
		
		//			if (CheckReportEditionEnums.V_COMMON == edition) {
		List<AfterwardsCheckReportFinancialInfo> capitals = convertToNew(map
			.get(FinancialTypeEnum.CAPITAL));
		if (ListUtil.isNotEmpty(capitals)) {
			contentInfo.setCapitals(capitals);
		}
		List<AfterwardsCheckReportFinancialInfo> debts = convertToNew(map
			.get(FinancialTypeEnum.DEBT));
		if (ListUtil.isNotEmpty(debts)) {
			contentInfo.setDebts(debts);
		}
		//			} 
		if (CheckReportEditionEnums.V_CITY == edition) {
			List<AfterwardsCheckReportFinancialInfo> subjects = convertToNew(map
				.get(FinancialTypeEnum.SUBJECTS));
			if (ListUtil.isNotEmpty(subjects)) {
				contentInfo.setSubjects(subjects);
			}
		}
		//		} 
		
	}
	
	private List<AfterwardsCheckReportFinancialInfo> convertToNew(	List<AfterwardsCheckReportFinancialInfo> list) {
		if (ListUtil.isEmpty(list)) {
			return null;
		}
		List<AfterwardsCheckReportFinancialInfo> newList = new ArrayList<>();
		for (AfterwardsCheckReportFinancialInfo info : list) {
			AfterwardsCheckReportFinancialInfo newInfo = new AfterwardsCheckReportFinancialInfo();
			newInfo.setFinancialItem(info.getFinancialItem());
			FAfterwardsCheckReportFinancialInfo finfo = new FAfterwardsCheckReportFinancialInfo();
			finfo.setFinancialItem(info.getFinancialItem());
			finfo.setDelAble(BooleanEnum.NO); // 获取上一期的数据，都不可删除
			newInfo.add(finfo);
			newList.add(newInfo);
		}
		
		return newList;
	}
	
	/**
	 * 
	 * 获取财务报表科目数据
	 * 
	 * @param formId
	 * @return
	 */
	// 合并空行的
	// private Map<FinancialTypeEnum, List<AfterwardsCheckReportFinancialInfo>>
	// queryFinancialInfos( long formId) {
	// List<FAfterwardsCheckReportFinancialDO> items =
	// FAfterwardsCheckReportFinancialDAO
	// .findByFormId(formId);
	// if (ListUtil.isEmpty(items)) {
	// return null;
	// }
	//
	// Map<FinancialTypeEnum, Map<String, AfterwardsCheckReportFinancialInfo>>
	// map = new HashMap<>();
	// //Map<String, AfterwardsCheckReportFinancialInfo> typeMap = new
	// LinkedHashMap<>();
	// for (FAfterwardsCheckReportFinancialDO item : items) {
	// FinancialTypeEnum type =
	// FinancialTypeEnum.getByCode(item.getFinancialType());
	// Map<String, AfterwardsCheckReportFinancialInfo> typeMap = map.get(type);
	// if (null == typeMap) {
	// typeMap = new LinkedHashMap<>();
	// map.put(type, typeMap);
	// }
	// AfterwardsCheckReportFinancialInfo info =
	// typeMap.get(item.getFinancialItem());
	// if (null == info) {
	// info = new AfterwardsCheckReportFinancialInfo();
	// info.setFinancialItem(item.getFinancialItem());
	// typeMap.put(item.getFinancialItem(), info);
	// }
	//
	// FAfterwardsCheckReportFinancialInfo finfo = new
	// FAfterwardsCheckReportFinancialInfo();
	// BeanCopier.staticCopy(item, finfo);
	// info.add(finfo);
	// }
	//
	// Map<FinancialTypeEnum, List<AfterwardsCheckReportFinancialInfo>> ret =
	// new HashMap<>();
	// for (Map.Entry<FinancialTypeEnum, Map<String,
	// AfterwardsCheckReportFinancialInfo>> entry : map
	// .entrySet()) {
	// ret.put(entry.getKey(), new ArrayList<>(entry.getValue().values()));
	// }
	//
	// return ret;
	// }
	
	/**
	 * 
	 * 获取财务报表科目数据
	 * 
	 * @param formId
	 * @return
	 */
	private Map<FinancialTypeEnum, List<AfterwardsCheckReportFinancialInfo>> queryFinancialInfos(	long formId) {
		List<FAfterwardsCheckReportFinancialDO> items = FAfterwardsCheckReportFinancialDAO
			.findByFormId(formId);
		if (ListUtil.isEmpty(items)) {
			return null;
		}
		
		// Map<FinancialTypeEnum, Map<String,
		// AfterwardsCheckReportFinancialInfo>> map = new HashMap<>();
		Map<FinancialTypeEnum, List<AfterwardsCheckReportFinancialInfo>> map = new HashMap<>();
		for (FAfterwardsCheckReportFinancialDO item : items) {
			FinancialTypeEnum type = FinancialTypeEnum.getByCode(item.getFinancialType());
			List<AfterwardsCheckReportFinancialInfo> infoList = map.get(type);
			if (ListUtil.isEmpty(infoList)) {
				infoList = new ArrayList<>();
				map.put(type, infoList);
			}
			
			FAfterwardsCheckReportFinancialInfo finfo = new FAfterwardsCheckReportFinancialInfo();
			BeanCopier.staticCopy(item, finfo);
			finfo.setDelAble(BooleanEnum.getByCode(item.getDelAble()));
			
			if (StringUtil.isBlank(item.getFinancialItem())) {
				// 名称为空的不合并
				AfterwardsCheckReportFinancialInfo info = new AfterwardsCheckReportFinancialInfo();
				info.setFinancialItem(item.getFinancialItem());
				info.add(finfo);
				infoList.add(info);
			} else {
				// 名称相同的合并
				boolean exist = false;
				if (ListUtil.isNotEmpty(infoList)) {
					for (AfterwardsCheckReportFinancialInfo info : infoList) {
						if (StringUtil.equals(info.getFinancialItem(), item.getFinancialItem())) {
							info.add(finfo);
							exist = true;
							break;
						}
					}
				}
				
				if (!exist) {
					AfterwardsCheckReportFinancialInfo info = new AfterwardsCheckReportFinancialInfo();
					info.setFinancialItem(item.getFinancialItem());
					info.add(finfo);
					infoList.add(info);
				}
			}
		}
		
		return map;
	}
	
	/**
	 * 查询银行贷款及其他融资
	 * 
	 * @param content
	 */
	private void queryLoans(FAfterwardsCheckReportContentInfo content) {
		List<FAfterwardsCheckLoanDO> items = FAfterwardsCheckLoanDAO.findByFormId(content
			.getFormId());
		if (ListUtil.isNotEmpty(items)) {
			List<FAfterwardsCheckLoanInfo> infos = new ArrayList<>();
			for (FAfterwardsCheckLoanDO item : items) {
				FAfterwardsCheckLoanInfo info = new FAfterwardsCheckLoanInfo();
				BeanCopier.staticCopy(item, info);
				info.setDelAble(BooleanEnum.getByCode(item.getDelAble()));
				infos.add(info);
			}
			
			content.setLoans(infos);
			return;
		}
		
		FAfterwardsCheckDO check = extraDAO.searchAfterCheckEdition(content.getProjectCode());
		if (null == check) {
			return;
		}
		items = FAfterwardsCheckLoanDAO.findByFormId(check.getFormId());
		
		if (ListUtil.isNotEmpty(items)) {
			List<FAfterwardsCheckLoanInfo> infos = new ArrayList<>();
			for (FAfterwardsCheckLoanDO item : items) {
				FAfterwardsCheckLoanInfo info = new FAfterwardsCheckLoanInfo();
				BeanCopier.staticCopy(item, info);
				info.setId(0L);
				info.setFormId(content.getFormId());
				info.setDelAble(BooleanEnum.NO);
				infos.add(info);
			}
			
			content.setLoans(infos);
			return;
		}
		
	}
	
	/**
	 * 
	 * 获取科目信息
	 * 
	 * @param content
	 */
	private void queryItems(FAfterwardsCheckReportContentInfo content) {
		if (null == content || content.getFormId() <= 0) {
			return;
		}
		
		List<FAfterwardsCheckReportItemDO> list = FAfterwardsCheckReportItemDAO
			.findByFormId(content.getFormId());
		if (ListUtil.isEmpty(list)) {
			return;
		}
		
		List<FAfterwardsCheckReportItemInfo> incomes = new ArrayList<>(); // 核实收入
		List<FAfterwardsCheckReportItemInfo> costs = new ArrayList<>(); // 核实成本
		List<FAfterwardsCheckReportItemInfo> counters = new ArrayList<>(); // 反担保措施
		List<FAfterwardsCheckReportItemInfo> others = new ArrayList<>(); // 其他重要事项检查
		List<FAfterwardsCheckReportItemInfo> warnings = new ArrayList<>(); // 预警信号或关注事项
		
		List<FAfterwardsCheckReportItemInfo> costConsists1 = new ArrayList<>(); // 成本机构及变动情况1
		List<FAfterwardsCheckReportItemInfo> costConsists2 = new ArrayList<>(); // 成本机构及变动情况2
		List<FAfterwardsCheckReportItemInfo> costConsists3 = new ArrayList<>(); // 成本机构及变动情况3
		List<FAfterwardsCheckReportItemInfo> inventories1 = new ArrayList<>(); //存货1
		List<FAfterwardsCheckReportItemInfo> inventories2 = new ArrayList<>(); //存货2
		List<FAfterwardsCheckReportItemInfo> inventories3 = new ArrayList<>(); //存货3
		
		for (FAfterwardsCheckReportItemDO item : list) {
			FAfterwardsCheckReportItemInfo info = new FAfterwardsCheckReportItemInfo();
			BeanCopier.staticCopy(item, info);
			info.setItemType(AfterCheckItemTypeEnum.getByCode(item.getItemType()));
			if (AfterCheckItemTypeEnum.INCOME == info.getItemType()) {
				incomes.add(info);
			} else if (AfterCheckItemTypeEnum.COST == info.getItemType()) {
				costs.add(info);
			} else if (AfterCheckItemTypeEnum.COUNTER == info.getItemType()) {
				counters.add(info);
			} else if (AfterCheckItemTypeEnum.OTHER == info.getItemType()) {
				others.add(info);
			} else if (AfterCheckItemTypeEnum.WARNING == info.getItemType()) {
				warnings.add(info);
			} else if (AfterCheckItemTypeEnum.COST_CONSIST_1 == info.getItemType()) {
				costConsists1.add(info);
			} else if (AfterCheckItemTypeEnum.COST_CONSIST_2 == info.getItemType()) {
				costConsists2.add(info);
			} else if (AfterCheckItemTypeEnum.COST_CONSIST_3 == info.getItemType()) {
				costConsists3.add(info);
			} else if (AfterCheckItemTypeEnum.INVENTORY_1 == info.getItemType()) {
				inventories1.add(info);
			} else if (AfterCheckItemTypeEnum.INVENTORY_2 == info.getItemType()) {
				inventories2.add(info);
			} else if (AfterCheckItemTypeEnum.INVENTORY_3 == info.getItemType()) {
				inventories3.add(info);
			}
		}
		
		content.setIncomes(incomes);
		content.setCosts(costs);
		content.setCounters(counters);
		content.setOthers(others);
		content.setWarnings(warnings);
		content.setCostConsists1(costConsists1);
		content.setCostConsists2(costConsists2);
		content.setCostConsists3(costConsists3);
		content.setInventories1(inventories1);
		content.setInventories2(inventories2);
		content.setInventories3(inventories3);
	}
	
	private void queryCapitals(FAfterwardsCheckReportContentInfo content) {
		if (null == content || content.getFormId() <= 0) {
			return;
		}
		
		List<FAfterwardsCheckReportCapitalDO> list = FAfterwardsCheckReportCapitalDAO
			.findByFormId(content.getFormId());
		if (ListUtil.isEmpty(list)) {
			return;
		}
		
		List<FAfterwardsCheckReportCapitalInfo> incomeDetails = new ArrayList<>(); // 企业收入调查工作底稿
		List<FAfterwardsCheckReportCapitalInfo> projectings = new ArrayList<>(); // 在建项目情况表
		List<FAfterwardsCheckReportCapitalInfo> debtDetails = new ArrayList<>(); // 企业资产负债划转明细
		List<FAfterwardsCheckReportCapitalInfo> creditDetails = new ArrayList<>(); // 信用状况
		List<FAfterwardsCheckReportCapitalInfo> tenCustomers = new ArrayList<>(); // 前十大客户
		List<FAfterwardsCheckReportCapitalInfo> holderLoans = new ArrayList<>(); // 股东担保贷款（或关联企业贷款）明细
		List<FAfterwardsCheckReportCapitalInfo> backOnTimes = new ArrayList<>(); // 按期收回情况
		List<FAfterwardsCheckReportCapitalInfo> successfulProjects = new ArrayList<>(); // 已中标未开工项目情况表
		List<FAfterwardsCheckReportCapitalInfo> subContractors = new ArrayList<>(); // 项目分包商
		
		for (FAfterwardsCheckReportCapitalDO item : list) {
			FAfterwardsCheckReportCapitalInfo info = new FAfterwardsCheckReportCapitalInfo();
			BeanCopier.staticCopy(item, info);
			info.setCapitalType(CapitalTypeEnum.getByCode(item.getCapitalType()));
			if (CapitalTypeEnum.INCOME_DETAIL == info.getCapitalType()) {
				incomeDetails.add(info);
			} else if (CapitalTypeEnum.PROJECTING == info.getCapitalType()) {
				projectings.add(info);
			} else if (CapitalTypeEnum.DEBT_DETAIL == info.getCapitalType()) {
				debtDetails.add(info);
			} else if (CapitalTypeEnum.CREDIT_DETAIL == info.getCapitalType()) {
				creditDetails.add(info);
			} else if (CapitalTypeEnum.TEN_CUSTOMER == info.getCapitalType()) {
				tenCustomers.add(info);
			} else if (CapitalTypeEnum.HOLDER_LOAN == info.getCapitalType()) {
				holderLoans.add(info);
			} else if (CapitalTypeEnum.BACK_ON_TIME == info.getCapitalType()) {
				backOnTimes.add(info);
			} else if (CapitalTypeEnum.SUCCESSFUL_PROJECT == info.getCapitalType()) {
				successfulProjects.add(info);
			} else if (CapitalTypeEnum.SUB_CONTRACTOR == info.getCapitalType()) {
				subContractors.add(info);
			}
		}
		
		content.setIncomeDetails(incomeDetails);
		content.setProjectings(projectings);
		content.setDebtDetails(debtDetails);
		content.setCreditDetails(creditDetails);
		content.setTenCustomers(tenCustomers);
		content.setHolderLoans(holderLoans);
		content.setBackOnTimes(backOnTimes);
		content.setSuccessfulProjects(successfulProjects);
		content.setSubContractors(subContractors);
	}
	
	/**
	 * 
	 * 查询 企业收入核实情况工作底稿
	 * 
	 * @param content
	 * @param projectCode
	 */
	private void queryCheckIncomes(FAfterwardsCheckReportContentInfo content, String projectCode) {
		List<FAfterwardsCheckReportIncomeDO> items = FAfterwardsCheckReportIncomeDAO
			.findByFormId(content.getFormId());
		if (ListUtil.isNotEmpty(items)) {
			List<FAfterwardsCheckReportIncomeInfo> infos = new ArrayList<>();
			for (FAfterwardsCheckReportIncomeDO item : items) {
				FAfterwardsCheckReportIncomeInfo info = new FAfterwardsCheckReportIncomeInfo();
				BeanCopier.staticCopy(item, info);
				infos.add(info);
			}
			content.setCheckIncomes(infos);
			return;
		}
		long formId = extraDAO.queryCheckReportIncomeFormId(projectCode);
		items = FAfterwardsCheckReportIncomeDAO.findByFormId(formId);
		if (ListUtil.isNotEmpty(items)) {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			int currentYear = c.get(Calendar.YEAR);
			int lastYear = currentYear - 1;
			List<FAfterwardsCheckReportIncomeInfo> infos = new ArrayList<>();
			for (FAfterwardsCheckReportIncomeDO item : items) {
				FAfterwardsCheckReportIncomeInfo info = new FAfterwardsCheckReportIncomeInfo();
				info.setLastYear(lastYear);
				if (item.getLastYear() == lastYear) {
					info.setLastAccumulation(item.getLastAccumulation());
					info.setLastAverageDay(item.getLastAverageDay());
					info.setLastTotalAmount(item.getLastTotalAmount());
					info.setLastTotalDay(item.getLastTotalDay());
				}
				info.setCurrentYear(currentYear);
				if (item.getCurrentYear() == currentYear) {
					info.setCurrentAccumulation(item.getCurrentAccumulation());
					info.setCurrentAverageDay(item.getCurrentAverageDay());
					info.setCurrentTotalAmount(item.getCurrentTotalAmount());
					info.setCurrentTotalDay(item.getCurrentTotalDay());
				} else if (item.getCurrentYear() == lastYear) {
					info.setLastAccumulation(item.getCurrentAccumulation());
					info.setLastAverageDay(item.getCurrentAverageDay());
					info.setLastTotalAmount(item.getCurrentTotalAmount());
					info.setLastTotalDay(item.getCurrentTotalDay());
				}
				info.setIncomeName(item.getIncomeName());
				info.setIncomeId(0L);
				infos.add(info);
			}
			content.setCheckIncomes(infos);
			return;
		}
	}
	
	private List<AfterwardsCheckReportItemInfo> convert(List<FAfterwardsCheckReportItemInfo> items) {
		if (ListUtil.isEmpty(items)) {
			return null;
		}
		
		List<AfterwardsCheckReportItemInfo> list = new ArrayList<>();
		Map<String, AfterwardsCheckReportItemInfo> map = new LinkedHashMap<>();
		for (FAfterwardsCheckReportItemInfo item : items) {
			if (StringUtil.isBlank(item.getItemName())) {
				AfterwardsCheckReportItemInfo itemInfo = new AfterwardsCheckReportItemInfo();
				itemInfo.setItemName(item.getItemName());
				itemInfo.add(item);
				list.add(itemInfo);
			} else {
				AfterwardsCheckReportItemInfo itemInfo = map.get(item.getItemName());
				if (null == itemInfo) {
					itemInfo = new AfterwardsCheckReportItemInfo();
					itemInfo.setItemName(item.getItemName());
					map.put(item.getItemName(), itemInfo);
					list.add(itemInfo);
				}
				itemInfo.add(item);
			}
		}
		
		return list;
	}
	
	/**
	 * 
	 * 查询项目信息(房地产开发业)
	 * 
	 * @param content
	 */
	private void queryProjectsSimple(FAfterwardsCheckReportContentInfo content) {
		List<FAfterwardsCheckReportProjectDO> items = FAfterwardsCheckReportProjectDAO
			.findByFormId(content.getFormId());
		if (ListUtil.isNotEmpty(items)) {
			List<FAfterwardsCheckReportProjectSimpleInfo> infos1 = new ArrayList<>();
			List<FAfterwardsCheckReportProjectSimpleInfo> infos2 = new ArrayList<>();
			for (FAfterwardsCheckReportProjectDO item : items) {
				FAfterwardsCheckReportProjectSimpleInfo info = new FAfterwardsCheckReportProjectSimpleInfo();
				BeanCopier.staticCopy(item, info);
				if (item.getSortOrder() == 0) {
					infos2.add(info);
				} else {
					infos1.add(info);
				}
				
			}
			List<FAfterwardsCheckReportProjectSimpleInfo> infos = new ArrayList<>();
			if (ListUtil.isNotEmpty(infos1)) {
				infos.addAll(infos1);
			}
			if (ListUtil.isNotEmpty(infos2)) {
				infos.addAll(infos2);
			}
			content.setProjects(infos);
		}
	}
	
	@Override
	public FormBaseResult saveContent(final FAfterwardsCheckReportContentOrder order) {
		return commonFormSaveProcess(order, "保后检查报告-保存监管内容", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = FcsPmDomainHolder.get().getSysDate();
				// 取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				order.setFormId(form.getFormId());
				
				long formId = form.getFormId();
				FAfterwardsCheckDO check = FAfterwardsCheckDAO.findByFormId(formId);
				if (null == check) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
						"没有找到对应的检查报告");
				}
				
				CheckReportEditionEnums edition = CheckReportEditionEnums.getByCode(check
					.getEdition());
				String projectCode = check.getProjectCode();
				
				FAfterwardsCheckReportContentDO content = FAfterwardsCheckReportContentDAO
					.findByFormId(formId);
				long contentId = order.getContentId();
				if (null == content) {
					content = new FAfterwardsCheckReportContentDO();
					BeanCopier.staticCopy(order, content);
					content.setRawAddTime(now);
					content.setFormId(form.getFormId());
					contentId = FAfterwardsCheckReportContentDAO.insert(content);
				} else {
					order.setContentId(content.getContentId());
					BeanCopier.staticCopy(order, content);
					content.setFormId(form.getFormId());
					FAfterwardsCheckReportContentDAO.update(content);
					contentId = content.getContentId();
				}
				
				FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
					"result");
				result.setKeyId(contentId);
				
				// 财务报表数据及相关存储的数据
				saveKpies(formId, order);
				
				// 科目数据
				saveFinancialItems(formId, order);
				
				// loans
				saveLoans(formId, order.getLoans());
				
				// 公共数据
				saveItems(order);
				
				saveCapital(formId, order);
				
				if (CheckReportEditionEnums.V_MANUFACTURING == edition) {
					// 生产制造业
					// 企业收入核实情况工作底稿（调查科目、核实过程可根据实际情况进行调整填写）
					saveIncomes(formId, projectCode, order.getCheckIncomes());
				} else if (CheckReportEditionEnums.V_REAL_ESTATE == edition) {
					updateProject(formId, order.getProjects());
				} else if (CheckReportEditionEnums.V_CONSTRUCTION == edition) {
					
				} else if (CheckReportEditionEnums.V_CITY == edition) {
					
				} else if (CheckReportEditionEnums.V_LOAN == edition) {
					//
					FAfterwardsCheckCommonDO common = FAfterwardsCheckCommonDAO
						.findByProjectCode(projectCode);
					if (null == common) {
						common = new FAfterwardsCheckCommonDO();
						common.setProjectCode(projectCode);
						common.setLoanLimitData(order.getLoanLimitDataCommon());
						common.setLoanAmountData(order.getLoanAmountDataCommon());
						common.setRawAddTime(now);
						FAfterwardsCheckCommonDAO.insert(common);
					} else {
						if (StringUtil.notEquals(order.getLoanLimitDataCommon(),
							common.getLoanLimitData())
							|| StringUtil.notEquals(order.getLoanAmountDataCommon(),
								common.getLoanAmountData())) {
							common.setLoanLimitData(order.getLoanLimitDataCommon());
							common.setLoanAmountData(order.getLoanAmountDataCommon());
							FAfterwardsCheckCommonDAO.update(common);
						}
					}
					
					FAfterwardsCheckReportContentExtendDO extend = FAfterwardsCheckReportContentExtendDAO
						.findByFormId(formId);
					if (null == extend) {
						extend = new FAfterwardsCheckReportContentExtendDO();
						extend.setFormId(formId);
						extend.setValue1(order.getLoanLimitData());
						extend.setValue2(order.getLoanAmountData());
						extend.setRawAddTime(now);
						FAfterwardsCheckReportContentExtendDAO.insert(extend);
					} else {
						if (StringUtil.notEquals(order.getLoanLimitData(), extend.getValue1())
							|| StringUtil.notEquals(order.getLoanAmountData(), extend.getValue2())) {
							extend.setValue1(order.getLoanLimitData());
							extend.setValue2(order.getLoanAmountData());
							FAfterwardsCheckReportContentExtendDAO.updateByFormId(extend);
						}
					}
				}
				return null;
			}
		}, null, null);
	}
	
	private void saveKpies(long formId, FAfterwardsCheckReportContentOrder order) {
		// 财务报表数据
		financialKpiService.save(formId, KpiTypeEnum.FINANCIAL_TABLE, order.getFinancials());
		financialKpiService.save(formId, KpiTypeEnum.PROFIT_TABLE, order.getProfits());
		financialKpiService.save(formId, KpiTypeEnum.FLOW_TABLE, order.getFlows());
		
		// （1）主要承包项目结构
		financialKpiService.save(formId, KpiTypeEnum.CONTRACT_PROJECT, order.getContractProjects());
		
		// （1）股东背景
		financialKpiService.save(formId, KpiTypeEnum.HOLDER_BACKGROUND,
			order.getHolderBackgrounds());
		// ①贷款行业 ②贷款信用结构 ①五级分类情况
		financialKpiService.save(formId, KpiTypeEnum.LOAN_INDUSTY, order.getLoanIndusties());
		financialKpiService.save(formId, KpiTypeEnum.CREDIT_STRUCTURE, order.getCreditStructures());
		financialKpiService.save(formId, KpiTypeEnum.LOAN_TIME_LIMIT, order.getLoanTimeLimits());
		// ③贷款期限集中度 ④贷款金额集中度
		financialKpiService.save(formId, KpiTypeEnum.LOAN_AMOUNT, order.getLoanAmounts());
		financialKpiService.save(formId, KpiTypeEnum.AVERAGE_CAPITAL, order.getAverageCapitals());
		// 平均资产情况调查
		financialKpiService.save(formId, KpiTypeEnum.LOAN_QUALITY_LEVEL,
			order.getLoanQualityLevels());
		// ③不良贷款情况
		financialKpiService.save(formId, KpiTypeEnum.BAD_LOAN, order.getBadLoans());
		
	}
	
	private void saveFinancialItems(long formId, FAfterwardsCheckReportContentOrder order) {
		saveFinancialItems(formId, FinancialTypeEnum.CAPITAL, order.getCapitals());
		saveFinancialItems(formId, FinancialTypeEnum.DEBT, order.getDebts());
		saveFinancialItems(formId, FinancialTypeEnum.OTHER, order.getFothers());
		saveFinancialItems(formId, FinancialTypeEnum.SUBJECTS, order.getSubjects());
	}
	
	/**
	 * 
	 * 保存财务报表科目数据
	 * 
	 * @param formId
	 * @param type
	 * @param orders
	 */
	@SuppressWarnings("deprecation")
	private void saveFinancialItems(long formId, FinancialTypeEnum type,
									List<FAfterwardsCheckReportFinancialOrder> orders) {
		if (formId <= 0 || type == null) {
			return;
		}
		
		if (ListUtil.isEmpty(orders)) {
			FAfterwardsCheckReportFinancialDAO.deleteByFormIdAndFinancilType(formId, type.code());
			return;
		}
		
		List<FAfterwardsCheckReportFinancialDO> items = FAfterwardsCheckReportFinancialDAO
			.findByFormIdAndFinancilType(formId, type.code());
		Map<Long, FAfterwardsCheckReportFinancialDO> map = new HashMap<>();
		if (ListUtil.isNotEmpty(items)) {
			for (FAfterwardsCheckReportFinancialDO doObj : items) {
				map.put(doObj.getFinancialId(), doObj);
			}
		}
		
		int sortOrder = 1;
		Date now = getSysdate();
		for (FAfterwardsCheckReportFinancialOrder order : orders) {
			if (order.isNull()) {
				continue;
			}
			
			order.setFormId(formId);
			order.setFinancialType(type.code());
			order.setSortOrder(sortOrder++);
			FAfterwardsCheckReportFinancialDO item = map.get(order.getFinancialId());
			if (null == item) {
				item = new FAfterwardsCheckReportFinancialDO();
				BeanCopier.staticCopy(order, item, UnBoxingConverter.getInstance());
				item.setRawAddTime(now);
				FAfterwardsCheckReportFinancialDAO.insert(item);
			} else {
				if (!isEqual(order, item)) {
					BeanCopier.staticCopy(order, item, UnBoxingConverter.getInstance());
					FAfterwardsCheckReportFinancialDAO.update(item);
				}
			}
			map.remove(order.getFinancialId());
		}
		
		if (null != map && map.size() > 0) {
			for (long financialId : map.keySet()) {
				FAfterwardsCheckReportFinancialDAO.deleteById(financialId);
			}
		}
	}
	
	private boolean isEqual(FAfterwardsCheckReportFinancialOrder order,
							FAfterwardsCheckReportFinancialDO item) {
		return null != order
				&& order.getFormId() == item.getFormId()
				&& StringUtil.equals(order.getFinancialType(), item.getFinancialType())
				&& StringUtil.equals(order.getFinancialItem(), item.getFinancialItem())
				&& StringUtil.equals(order.getFinancialName(), item.getFinancialName())
				&& order.getOrigialAmount().equals(item.getOrigialAmount())
				&& order.getOrigialCount() == item.getOrigialCount()
				&& StringUtil.equals(order.getOrigialAge(), item.getOrigialAge())
				&& order.getBadDebtAmount().equals(item.getBadDebtAmount())
				&& StringUtil.equals(order.getRemark(), item.getRemark())
				&& StringUtil.equals(order.getConstructionContract(),
					item.getConstructionContract())
				&& StringUtil.equals(order.getRefundCertificate(), item.getRefundCertificate())
				&& order.getSortOrder() == item.getSortOrder();
	}
	
	/**
	 * 
	 * 银行贷款及其他融资
	 * 
	 * @param formId
	 * @param orders
	 */
	@SuppressWarnings("deprecation")
	private void saveLoans(long formId, List<FAfterwardsCheckLoanOrder> orders) {
		if (formId <= 0) {
			return;
		}
		
		if (ListUtil.isEmpty(orders)) {
			FAfterwardsCheckLoanDAO.deleteByFormId(formId);
			return;
		}
		
		List<FAfterwardsCheckLoanDO> items = FAfterwardsCheckLoanDAO.findByFormId(formId);
		Map<Long, FAfterwardsCheckLoanDO> map = new HashMap<>();
		if (ListUtil.isNotEmpty(items)) {
			for (FAfterwardsCheckLoanDO item : items) {
				map.put(item.getId(), item);
			}
		}
		
		int sortOrder = 1;
		Date now = getSysdate();
		for (FAfterwardsCheckLoanOrder order : orders) {
			FAfterwardsCheckLoanDO item = map.get(order.getId());
			order.setFormId(formId);
			order.setSortOrder(sortOrder++);
			if (null == item) {
				item = new FAfterwardsCheckLoanDO();
				BeanCopier.staticCopy(order, item, UnBoxingConverter.getInstance());
				item.setLoanExpireDate(DateUtil.strToDtSimpleFormat(order.getLoanExpireDate()));
				item.setRawAddTime(now);
				FAfterwardsCheckLoanDAO.insert(item);
			} else {
				if (!isEqual(order, item)) {
					BeanCopier.staticCopy(order, item, UnBoxingConverter.getInstance());
					item.setLoanExpireDate(DateUtil.strToDtSimpleFormat(order.getLoanExpireDate()));
					FAfterwardsCheckLoanDAO.update(item);
					
				}
			}
			map.remove(order.getId());
		}
		
		if (null != map && map.size() > 0) {
			// 移除删除的数据
			for (long id : map.keySet()) {
				FAfterwardsCheckLoanDAO.deleteById(id);
			}
		}
	}
	
	private boolean isEqual(FAfterwardsCheckLoanOrder order, FAfterwardsCheckLoanDO item) {
		return null != order
				&& order.getFormId() == item.getFormId()
				&& StringUtil.equals(order.getLoanInstitution(), item.getLoanInstitution())
				&& StringUtil.equals(order.getLoanType(), item.getLoanType())
				&& order.getLoanAmount().equals(item.getLoanAmount())
				&& order.getLoanBalance().equals(item.getLoanBalance())
				&& StringUtil.equals(order.getLoanTimeLimit(), item.getLoanTimeLimit())
				&& new BigDecimal(order.getInterestRate()).equals(new BigDecimal(item
					.getInterestRate()))
				&& new BigDecimal(order.getCashDepositRate()).equals(new BigDecimal(item
					.getCashDepositRate()))
				&& StringUtil.equals(order.getLoanExpireDate(),
					DateUtil.dtSimpleSlashFormat(item.getLoanExpireDate()))
				&& StringUtil.equals(order.getGuaranteeWay(), item.getGuaranteeWay())
				&& StringUtil.equals(order.getRemark(), item.getRemark())
				&& order.getSortOrder() == item.getSortOrder();
	}
	
	/**
	 * 
	 * 保存所有科目
	 * 
	 * @param order
	 */
	private void saveItems(FAfterwardsCheckReportContentOrder order) {
		if (null == order) {
			return;
		}
		
		saveItems(order.getFormId(), AfterCheckItemTypeEnum.INCOME, order.getIncomes());
		saveItems(order.getFormId(), AfterCheckItemTypeEnum.COST, order.getCosts());
		saveItems(order.getFormId(), AfterCheckItemTypeEnum.COUNTER, order.getCounters());
		saveItems(order.getFormId(), AfterCheckItemTypeEnum.OTHER, order.getOthers());
		saveItems(order.getFormId(), AfterCheckItemTypeEnum.WARNING, order.getWarnings());
		
		saveItems(order.getFormId(), AfterCheckItemTypeEnum.COST_CONSIST_1,
			order.getCostConsists1());
		saveItems(order.getFormId(), AfterCheckItemTypeEnum.COST_CONSIST_2,
			order.getCostConsists2());
		saveItems(order.getFormId(), AfterCheckItemTypeEnum.COST_CONSIST_3,
			order.getCostConsists3());
		saveItems(order.getFormId(), AfterCheckItemTypeEnum.INVENTORY_1, order.getInventories1());
		saveItems(order.getFormId(), AfterCheckItemTypeEnum.INVENTORY_2, order.getInventories2());
		saveItems(order.getFormId(), AfterCheckItemTypeEnum.INVENTORY_3, order.getInventories3());
	}
	
	/**
	 * 
	 * 保存科目
	 * 
	 * @param formId 关联的表单ID
	 * @param type 科目类型
	 * @param orders 科目数据order
	 */
	@SuppressWarnings("deprecation")
	private void saveItems(long formId, AfterCheckItemTypeEnum type,
							List<FAfterwardsCheckReportItemOrder> orders) {
		if (formId <= 0 || null == type) {
			return;
		}
		
		if (ListUtil.isEmpty(orders)) {
			FAfterwardsCheckReportItemDAO.deleteByFormIdAndItemType(formId, type.code());
			return;
		}
		
		Map<Long, FAfterwardsCheckReportItemDO> map = new HashMap<>();
		List<FAfterwardsCheckReportItemDO> list = FAfterwardsCheckReportItemDAO
			.findByFormIdAndItemType(formId, type.code());
		if (ListUtil.isNotEmpty(list)) {
			for (FAfterwardsCheckReportItemDO item : list) {
				map.put(item.getItemId(), item);
			}
		}
		
		int sortOrder = 1;
		Date now = getSysdate();
		for (FAfterwardsCheckReportItemOrder order : orders) {
			order.setFormId(formId);
			order.setItemType(type.code());
			order.setSortOrder(sortOrder++);
			FAfterwardsCheckReportItemDO item = map.get(order.getItemId());
			if (null == item) {
				// 新增
				item = new FAfterwardsCheckReportItemDO();
				BeanCopier.staticCopy(order, item, UnBoxingConverter.getInstance());
				item.setRawAddTime(now);
				FAfterwardsCheckReportItemDAO.insert(item);
			} else {
				if (!isEqual(order, item)) {
					// 如果数据有不同，则更新
					BeanCopier.staticCopy(order, item, UnBoxingConverter.getInstance());
					FAfterwardsCheckReportItemDAO.update(item);
				}
			}
			map.remove(order.getItemId());
		}
		
		if (null != map && map.size() > 0) {
			// 删除的数据
			for (Long itemId : map.keySet()) {
				FAfterwardsCheckReportItemDAO.deleteById(itemId);
			}
		}
	}
	
	private boolean isEqual(FAfterwardsCheckReportItemOrder order, FAfterwardsCheckReportItemDO item) {
		return null != order && order.getFormId() == item.getFormId()
				&& StringUtil.equals(order.getItemType(), item.getItemType())
				&& StringUtil.equals(order.getItemName(), item.getItemName())
				&& StringUtil.equals(order.getItemValue1(), item.getItemValue1())
				&& StringUtil.equals(order.getItemValue2(), item.getItemValue2())
				&& StringUtil.equals(order.getItemValue3(), item.getItemValue3())
				&& StringUtil.equals(order.getItemDesc(), item.getItemDesc())
				&& order.getSortOrder() == item.getSortOrder();
	}
	
	private void saveCapital(long formId, FAfterwardsCheckReportContentOrder order) {
		// 企业收入调查工作底稿
		saveCapital(formId, CapitalTypeEnum.INCOME_DETAIL, order.getIncomeDetails());
		// 在建项目情况表
		saveCapital(formId, CapitalTypeEnum.PROJECTING, order.getProjectings());
		// 企业资产负债划转明细
		saveCapital(formId, CapitalTypeEnum.DEBT_DETAIL, order.getDebtDetails());
		// 信用状况
		saveCapital(formId, CapitalTypeEnum.CREDIT_DETAIL, order.getCreditDetails());
		// 前十大客户
		saveCapital(formId, CapitalTypeEnum.TEN_CUSTOMER, order.getTenCustomers());
		// 股东担保贷款（或关联企业贷款）明细
		saveCapital(formId, CapitalTypeEnum.HOLDER_LOAN, order.getHolderLoans());
		// 按期收回情况
		saveCapital(formId, CapitalTypeEnum.BACK_ON_TIME, order.getBackOnTimes());
		//已中标未开工项目情况表
		saveCapital(formId, CapitalTypeEnum.SUCCESSFUL_PROJECT, order.getSuccessfulProjects());
		//项目分包商
		saveCapital(formId, CapitalTypeEnum.SUB_CONTRACTOR, order.getSubContractors());
	}
	
	@SuppressWarnings("deprecation")
	private void saveCapital(long formId, CapitalTypeEnum type,
								List<FAfterwardsCheckReportCapitalOrder> orders) {
		if (formId <= 0 || type == null) {
			return;
		}
		
		if (ListUtil.isEmpty(orders)) {
			FAfterwardsCheckReportCapitalDAO.deleteByFormIdAndCapitalType(formId, type.code());
			return;
		}
		
		List<FAfterwardsCheckReportCapitalDO> items = FAfterwardsCheckReportCapitalDAO
			.findByFormIdAndCapitalType(formId, type.code());
		Map<Long, FAfterwardsCheckReportCapitalDO> map = new HashMap<>();
		if (ListUtil.isNotEmpty(items)) {
			for (FAfterwardsCheckReportCapitalDO item : items) {
				map.put(item.getCapitalId(), item);
			}
		}
		
		int sortOrder = 1;
		Date now = getSysdate();
		for (FAfterwardsCheckReportCapitalOrder order : orders) {
			if (order.isNull()) {
				continue;
			}
			order.setFormId(formId);
			order.setCapitalType(type.code());
			order.setSortOrder(sortOrder++);
			FAfterwardsCheckReportCapitalDO item = map.get(order.getCapitalId());
			if (null == item) {
				item = new FAfterwardsCheckReportCapitalDO();
				BeanCopier.staticCopy(order, item, UnBoxingConverter.getInstance());
				item.setCapitalType(type.code());
				item.setRawAddTime(now);
				FAfterwardsCheckReportCapitalDAO.insert(item);
			} else {
				if (!isEqual(order, item)) {
					BeanCopier.staticCopy(order, item, UnBoxingConverter.getInstance());
					FAfterwardsCheckReportCapitalDAO.update(item);
				}
			}
			map.remove(order.getCapitalId());
		}
		
		if (null != map && map.size() > 0) {
			for (long capitalId : map.keySet()) {
				FAfterwardsCheckReportCapitalDAO.deleteById(capitalId);
			}
		}
	}
	
	private boolean isEqual(FAfterwardsCheckReportCapitalOrder order,
							FAfterwardsCheckReportCapitalDO item) {
		return StringUtil.equals(order.getCapitalItem(), item.getCapitalItem())
				&& StringUtil.equals(order.getCapitalValue1(), item.getCapitalValue1())
				&& StringUtil.equals(order.getCapitalValue2(), item.getCapitalValue2())
				&& StringUtil.equals(order.getCapitalValue3(), item.getCapitalValue3())
				&& StringUtil.equals(order.getCapitalValue4(), item.getCapitalValue4())
				&& StringUtil.equals(order.getCapitalValue5(), item.getCapitalValue5())
				&& StringUtil.equals(order.getCapitalValue6(), item.getCapitalValue6())
				&& StringUtil.equals(order.getCapitalValue7(), item.getCapitalValue7())
				&& StringUtil.equals(order.getCapitalValue8(), item.getCapitalValue8())
				&& StringUtil.equals(order.getCapitalValue9(), item.getCapitalValue9())
				&& StringUtil.equals(order.getCapitalValue10(), item.getCapitalValue10())
				&& order.getSortOrder() == item.getSortOrder();
	}
	
	/**
	 * 保存 企业收入核实情况工作底稿
	 * 
	 * @param formId
	 * @param projectCode
	 * @param orders
	 */
	@SuppressWarnings("deprecation")
	private void saveIncomes(long formId, String projectCode,
								List<FAfterwardsCheckReportIncomeOrder> orders) {
		if (formId <= 0) {
			return;
		}
		
		if (ListUtil.isEmpty(orders)) {
			FAfterwardsCheckReportIncomeDAO.deleteByFormId(formId);
			return;
		}
		
		List<FAfterwardsCheckReportIncomeDO> items = FAfterwardsCheckReportIncomeDAO
			.findByFormId(formId);
		Map<Long, FAfterwardsCheckReportIncomeDO> map = new HashMap<>();
		if (ListUtil.isNotEmpty(items)) {
			for (FAfterwardsCheckReportIncomeDO item : items) {
				map.put(item.getIncomeId(), item);
			}
		}
		
		int sortOrder = 1;
		Date now = getSysdate();
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		int currentYear = c.get(Calendar.YEAR);
		int lastYear = currentYear - 1;
		for (FAfterwardsCheckReportIncomeOrder order : orders) {
			order.setFormId(formId);
			order.setSortOrder(sortOrder++);
			FAfterwardsCheckReportIncomeDO item = map.get(order.getIncomeId());
			if (null == item) {
				item = new FAfterwardsCheckReportIncomeDO();
				BeanCopier.staticCopy(order, item, UnBoxingConverter.getInstance());
				item.setProjectCode(projectCode);
				item.setLastYear(lastYear);
				item.setCurrentYear(currentYear);
				item.setRawAddTime(now);
				FAfterwardsCheckReportIncomeDAO.insert(item);
			} else {
				if (!isEqual(order, item)) {
					BeanCopier.staticCopy(order, item, UnBoxingConverter.getInstance());
					FAfterwardsCheckReportIncomeDAO.update(item);
				}
			}
			map.remove(order.getIncomeId());
		}
		
		if (null != map && map.size() > 0) {
			for (long incomeId : map.keySet()) {
				FAfterwardsCheckReportIncomeDAO.deleteById(incomeId);
			}
		}
	}
	
	private boolean isEqual(FAfterwardsCheckReportIncomeOrder order,
							FAfterwardsCheckReportIncomeDO item) {
		return order.getSortOrder() == item.getSortOrder()
				&& StringUtil.equals(order.getIncomeName(), item.getIncomeName())
				&& order.getLastYear() == item.getLastYear()
				&& order.getLastTotalAmount().equals(item.getLastTotalAmount())
				&& order.getLastTotalDay() == item.getLastTotalDay()
				&& order.getLastAverageDay().equals(item.getLastAverageDay())
				&& order.getLastAccumulation().equals(item.getLastAccumulation())
				&& order.getCurrentYear() == item.getCurrentYear()
				&& order.getCurrentTotalAmount().equals(item.getCurrentTotalAmount())
				&& order.getCurrentTotalDay() == item.getCurrentTotalDay()
				&& order.getCurrentAverageDay().equals(item.getCurrentAverageDay())
				&& order.getCurrentAccumulation().equals(item.getCurrentAccumulation());
	}
	
	@SuppressWarnings("deprecation")
	private void updateProject(long formId, List<FAfterwardsCheckReportProjectSimpleOrder> orders) {
		if (formId <= 0) {
			return;
		}
		
		if (ListUtil.isEmpty(orders)) {
			FAfterwardsCheckReportProjectDAO.deleteByFormId(formId);
		}
		
		List<FAfterwardsCheckReportProjectDO> items = FAfterwardsCheckReportProjectDAO
			.findByFormId(formId);
		Map<Long, FAfterwardsCheckReportProjectDO> map = new HashMap<>();
		if (ListUtil.isNotEmpty(items)) {
			for (FAfterwardsCheckReportProjectDO item : items) {
				map.put(item.getId(), item);
			}
		}
		
		int sortOrder = 1;
		Date now = getSysdate();
		for (FAfterwardsCheckReportProjectSimpleOrder order : orders) {
			order.setFormId(formId);
			order.setSortOrder(sortOrder++);
			FAfterwardsCheckReportProjectDO item = map.get(order.getId());
			if (null == item) {
				item = new FAfterwardsCheckReportProjectDO();
				BeanCopier.staticCopy(order, item, UnBoxingConverter.getInstance());
				item.setRawAddTime(now);
				FAfterwardsCheckReportProjectDAO.insert(item);
			} else {
				if (StringUtil.notEquals(order.getProjectName(), item.getProjectName())
					|| StringUtil.notEquals(order.getProjectType(), item.getProjectType())
					|| order.getSortOrder() != item.getSortOrder()) {
					item.setProjectName(order.getProjectName());
					item.setProjectType(order.getProjectType());
					item.setSortOrder(order.getSortOrder());
					FAfterwardsCheckReportProjectDAO.update(item);
				}
			}
			
			map.remove(order.getId());
		}
		
		if (null != map && map.size() > 0) {
			for (long id : map.keySet()) {
				FAfterwardsCheckReportProjectDAO.deleteById(id);
			}
		}
	}
	
	@Override
	public FAfterwardsCheckReportProjectInfo findContentProjectById(long id) {
		FAfterwardsCheckReportProjectDO item = FAfterwardsCheckReportProjectDAO.findById(id);
		if (null == item) {
			return null;
		}
		
		FAfterwardsCheckReportProjectInfo info = new FAfterwardsCheckReportProjectInfo();
		BeanCopier.staticCopy(item, info);
		return info;
	}
	
	@Override
	public FcsBaseResult saveContentProject(final FAfterwardsCheckReportProjectOrder order) {
		return commonProcess(order, "保后检查报告-保存房地产开发业-项目详情", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				long formId = order.getFormId();
				long projectId = order.getId();
				
				order.setFormId(formId);
				FAfterwardsCheckReportProjectDO item = FAfterwardsCheckReportProjectDAO
					.findById(order.getId());
				if (null == item) {
					item = new FAfterwardsCheckReportProjectDO();
					BeanCopier.staticCopy(order, item, UnBoxingConverter.getInstance());
					item.setRawAddTime(getSysdate());
					projectId = FAfterwardsCheckReportProjectDAO.insert(item);
				} else {
					int sortOrder = item.getSortOrder();
					BeanCopier.staticCopy(order, item, UnBoxingConverter.getInstance());
					item.setSortOrder(sortOrder);
					FAfterwardsCheckReportProjectDAO.update(item);
					projectId = item.getId();
				}
				
				FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
					"result");
				result.setKeyId(projectId);
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FcsBaseResult checkContentProject(long formId) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			List<FAfterwardsCheckReportProjectDO> items = FAfterwardsCheckReportProjectDAO
				.findByFormId(formId);
			if (ListUtil.isEmpty(items)) {
				result.setSuccess(false);
				result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
				result.setMessage("没有找到相关数据");
				return result;
			}
			
			StringBuilder sb = new StringBuilder();
			for (FAfterwardsCheckReportProjectDO item : items) {
				if (item.getCheckStatus() != 1) {
					sb.append(item.getId()).append(",");
				}
			}
			
			if (StringUtil.isBlank(sb.toString())) {
				result.setSuccess(true);
				result.setMessage("验证通过");
				return result;
			}
			
			sb.deleteCharAt(sb.length() - 1);
			
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.FORM_CHECK_ERROR);
			result.setMessage("数据不完整");
			result.setUrl(sb.toString());
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("异常：" + e.getMessage());
		}
		return result;
	}
	
	@Override
	public QueryBaseBatchResult<ProjectInfo> queryProjects(ProjectQueryOrder queryOrder) {
		QueryBaseBatchResult<ProjectInfo> baseBatchResult = new QueryBaseBatchResult<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("projectCode", queryOrder.getProjectCode());
		paramMap.put("projectName", queryOrder.getProjectName());
		paramMap.put("customerName", queryOrder.getCustomerName());
		paramMap.put("busiManagerId", queryOrder.getBusiManagerId());
		paramMap.put("busiType", queryOrder.getBusiType());
		List<String> phases = Lists.newArrayList();
		if (ListUtil.isNotEmpty(queryOrder.getPhasesList())) {
			for (ProjectPhasesEnum s : queryOrder.getPhasesList()) {
				phases.add(s.getCode());
			}
			paramMap.put("phases", phases);
		}
		
		long totalSize = 0L;
		if (ProjectUtil.isLitigation(queryOrder.getBusiType())) {
			totalSize = extraDAO.searchAfterCheckLitigationProjectCount(paramMap);
		} else {
			totalSize = extraDAO.searchAfterCheckProjectCount(paramMap);
		}
		
		PageComponent component = new PageComponent(queryOrder, totalSize);
		if (totalSize > 0) {
			paramMap.put("sortCol", queryOrder.getSortCol());
			paramMap.put("sortOrder", queryOrder.getSortOrder());
			paramMap.put("limitStart", queryOrder.getLimitStart());
			paramMap.put("pageSize", queryOrder.getPageSize());
			List<ProjectDO> pageList = null;
			if (ProjectUtil.isLitigation(queryOrder.getBusiType())) {
				pageList = extraDAO.searchAfterCheckLitigationProjectList(paramMap);
			} else {
				pageList = extraDAO.searchAfterCheckProjectList(paramMap);
			}
			
			List<ProjectInfo> list = baseBatchResult.getPageList();
			if (totalSize > 0) {
				for (ProjectDO doObj : pageList) {
					ProjectInfo info = DoConvert.convertProjectDO2Info(doObj);
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
	public FAfterwardsCheckInfo queryLastEdition(String projectCode) {
		FAfterwardsCheckDO check = extraDAO.searchAfterCheckEdition(projectCode);
		return convertToInfo(check);
	}
	
	private FAfterwardsCheckInfo convertToInfo(FAfterwardsCheckDO check) {
		if (null == check) {
			return null;
		}
		
		FAfterwardsCheckInfo checkInfo = new FAfterwardsCheckInfo();
		BeanCopier.staticCopy(check, checkInfo);
		checkInfo.setEdition(CheckReportEditionEnums.getByCode(check.getEdition()));
		checkInfo.setIsLastest(BooleanEnum.getByCode(check.getIsLastest()));
		
		return checkInfo;
	}
	
	@Override
	public FormBaseResult copy(FAfterwardsCheckOrder order) {
		FormBaseResult result = new FormBaseResult();
		long formId = order.getFormId();
		FAfterwardsCheckDO check = FAfterwardsCheckDAO.findByFormId(order.getFormId());
		if (null == check) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
			result.setMessage("没有找到对应期数据");
			return result;
		}
		
		try {
			order.setFormId(0L);
			order.setProjectCode(check.getProjectCode());
			order.setProjectName(check.getProjectName());
			order.setCustomerId(check.getCustomerId());
			order.setCustomerName(check.getCustomerName());
			order.setCheckDate(check.getCheckDate());
			order.setCheckAddress(check.getCheckAddress());
			order.setEdition(check.getEdition());
			// 基数已经设置好了
			order.setAmount(check.getAmount());
			order.setUsedAmount(check.getUsedAmount());
			order.setRepayedAmount(check.getRepayedAmount());
			order.setUseWay(check.getUseWay());
			result = this.save(order);
			if (!result.isSuccess()) {
				return result;
			}
			
			// 复制项目基本信息
			long newFormId = result.getFormInfo().getFormId();
			Date rawAddTime = getSysdate();
			FAfterwardsCheckBaseDO base = FAfterwardsCheckBaseDAO.findByFormId(formId);
			FAfterwardsCheckBaseDO newBase = new FAfterwardsCheckBaseDO();
			BeanCopier.staticCopy(base, newBase);
			newBase.setBaseId(0L);
			newBase.setFormId(newFormId);
			newBase.setRawAddTime(rawAddTime);
			FAfterwardsCheckBaseDAO.insert(newBase);
			
			// 复制收集信息
			copyColletions(formId, newFormId, rawAddTime);
			// 复制基本的监管内容
			saveContent(formId, newFormId, rawAddTime);
			// 复制财务报表数据及相关存储的数据
			saveKpies(formId, newFormId, rawAddTime);
			// 复制科目数据
			copyFinancial(formId, newFormId, rawAddTime);
			// 复制loan
			copyLoan(formId, newFormId, rawAddTime);
			// 复制公共数据
			copyItem(formId, newFormId, rawAddTime);
			// 复制capital
			copyCapital(formId, newFormId, rawAddTime);
			
			CheckReportEditionEnums edition = CheckReportEditionEnums.getByCode(check.getEdition());
			if (CheckReportEditionEnums.V_MANUFACTURING == edition) {
				// 生产制造业
				// 企业收入核实情况工作底稿（调查科目、核实过程可根据实际情况进行调整填写）
				copyIncome(formId, newFormId, rawAddTime);
			} else if (CheckReportEditionEnums.V_REAL_ESTATE == edition) {
				copyProject(formId, newFormId, rawAddTime);
			} else if (CheckReportEditionEnums.V_CONSTRUCTION == edition) {
				
			} else if (CheckReportEditionEnums.V_CITY == edition) {
				
			} else if (CheckReportEditionEnums.V_LOAN == edition) {
				//复制 content extend
				copyContenExtend(formId, newFormId, rawAddTime);
			}
			
			// 更新审核状态
			FormDO newForm = formDAO.findByFormId(newFormId);
			newForm.setCheckStatus(order.getFormCode().defaultCheckStatus().replaceAll("0", "1"));
			formDAO.update(newForm);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("复制历史报告失败：" + order.getFormId(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.EXECUTE_FAILURE);
			result.setMessage("复制历史报告失败：" + e.getMessage());
		}
		
		logger.info("复制历史报告完成：" + order.getFormId());
		
		return result;
	}
	
	
	private FcsBaseResult copy(FAfterwardsCheckDO checkDO, long oldFormId) {
		FormBaseResult result = new FormBaseResult();
		try {
			String projectCode = checkDO.getProjectCode();
			FAfterwardsCheckDO oldCheck = FAfterwardsCheckDAO.findByFormId(oldFormId);
			checkDO.setEdition(oldCheck.getEdition());
			checkDO.setCheckDate(oldCheck.getCheckDate());
			checkDO.setCheckAddress(oldCheck.getCheckAddress());
			
			FAfterwardsCheckInfo oldInfo = queryLastEdition(projectCode);
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			int year = c.get(Calendar.YEAR);
			int time = 1;
			if (null != oldInfo) {
				if (oldInfo.getRoundYear() == year) {
					time = oldInfo.getRoundTime() + 1;
				}
			}
			checkDO.setRoundYear(year);
			checkDO.setRoundTime(time);
			
			checkDO.setUseWay(BooleanEnum.YES.code());
				
			FAfterwardsCheckDAO.update(checkDO);
			long newFormId = checkDO.getFormId();
			
			Date rawAddTime = getSysdate();
			FAfterwardsCheckBaseDO base = FAfterwardsCheckBaseDAO.findByFormId(oldFormId);
			FAfterwardsCheckBaseDO newBase = new FAfterwardsCheckBaseDO();
			BeanCopier.staticCopy(base, newBase);
			newBase.setBaseId(0L);
			newBase.setFormId(newFormId);
			newBase.setRawAddTime(rawAddTime);
			
			ProjectInfo project = projectService.queryByCode(projectCode, false);
			if (null != project) {
				newBase.setProjectCode(projectCode);
				newBase.setProjectName(project.getProjectName());
				newBase.setCustomerId(project.getCustomerId());
				newBase.setCustomerName(project.getCustomerName());
				newBase.setBusiType(project.getBusiType());
				newBase.setBusiTypeName(project.getBusiTypeName());
				newBase.setAmount(project.getAmount());
				newBase.setStartTime(project.getStartTime());
				newBase.setEndTime(project.getEndTime());
				newBase.setBusiManagerName(project.getBusiManagerName());
				newBase.setLoanPurpose(project.getLoanPurpose());
				newBase.setTimeLimit(project.getTimeLimit());
				if (null != project.getTimeUnit()) {
					newBase.setTimeUnit(project.getTimeUnit().code());
				}
				newBase.setLoanedAmount(project.getLoanedAmount());
				newBase.setRepayedAmount(project.getReleasedAmount());
			}
			
			//授信机构
			String guaranteeInstitution = "";
			if (ProjectUtil.isBond(project.getBusiType())) {
				FCouncilSummaryProjectBondInfo giInfo = councilSummaryService
					.queryBondProjectCsByProjectCode(projectCode, false);
				if (null != giInfo) {
					guaranteeInstitution = giInfo.getCapitalChannelName();
				}
			} else if (ProjectUtil.isEntrusted(project.getBusiType())) {
				FCouncilSummaryProjectEntrustedInfo giInfo = councilSummaryService
					.queryEntrustedProjectCsByProjectCode(projectCode, false);
				if (null != giInfo) {
					guaranteeInstitution = giInfo.getCapitalChannelName();
				}
			} else if (ProjectUtil.isGuarantee(project.getBusiType())) {
				FCouncilSummaryProjectGuaranteeInfo giInfo = councilSummaryService
					.queryGuaranteeProjectCsByProjectCode(projectCode, false);
				if (null != giInfo) {
					guaranteeInstitution = giInfo.getCapitalChannelName();
				}
			}
			newBase.setCapitalChannelName(guaranteeInstitution);
			
			FCouncilSummaryProjectInfo summary = councilSummaryService
				.queryProjectCsByProjectCode(projectCode);
			if (null != summary) {
				newBase.setAmount(summary.getAmount()); //授信金额：取自会议纪要
				newBase.setLoanPurpose(summary.getLoanPurpose());
			}
			
			Money availableAmount = new Money(0L);
			//		在保金额：放款金额累计-解保金额累计
			Money releaseBalance = project.getLoanedAmount().subtract(project.getReleasedAmount());
			if (BooleanEnum.IS == project.getIsMaximumAmount()) {
				availableAmount = project.getAmount().subtract(releaseBalance);
			} else {
				availableAmount = project.getAmount().subtract(project.getLoanedAmount());
			}
			
			newBase.setAvailableAmount(availableAmount);
			newBase.setReleaseBalance(releaseBalance);
			//		授信金额：取自会议纪要
			//		累计使用金额：放款金额累计
			//		累计还款金额：解保金额累计
			//		可用金额：（最高授信）授信额度-在保金额   （else）授信额度-累计使用金额
			//		在保金额：放款金额累计-解保金额累计
			//累计还款金额：（担保）解保金额累计；（委贷）收款通知单中委贷本金收回累计
			ProjectRelatedUserInfo riskManager = projectRelatedUserService
				.getOrignalRiskManager(projectCode);
			if (null != riskManager) {
				newBase.setRiskManagerName(riskManager.getUserName());
			}
			
			//担保费
			Money guaranteeAmount = chargeNotificationService.queryChargeTotalAmount(projectCode,
				FeeTypeEnum.GUARANTEE_FEE).getOther();
			newBase.setGuaranteeAmount(guaranteeAmount);
			//存入保证金
			Money guaranteeDeposit = chargeNotificationService.queryChargeTotalAmount(
				projectCode, FeeTypeEnum.GUARANTEE_DEPOSIT).getOther();
			newBase.setGuaranteeDeposit(guaranteeDeposit);
			
			long baseId = FAfterwardsCheckBaseDAO.insert(newBase);
			
			//复制附件
			copyAttachments(base.getBaseId() + "", CommonAttachmentTypeEnum.CUSTOMER_OPINION.code(),
				baseId + "");
			copyAttachments(base.getBaseId() + "", CommonAttachmentTypeEnum.AFTER_DATA_COLLECT.code(),
				baseId + "");
			
			// 复制收集信息
			copyColletions(oldFormId, newFormId, rawAddTime);
			// 复制基本的监管内容
			saveContent(oldFormId, newFormId, rawAddTime);
			// 复制财务报表数据及相关存储的数据
			saveKpies(oldFormId, newFormId, rawAddTime);
			// 复制科目数据
			copyFinancial(oldFormId, newFormId, rawAddTime);
			// 复制loan
			copyLoan(oldFormId, newFormId, rawAddTime);
			// 复制公共数据
			copyItem(oldFormId, newFormId, rawAddTime);
			// 复制capital
			copyCapital(oldFormId, newFormId, rawAddTime);
			
			CheckReportEditionEnums edition = CheckReportEditionEnums.getByCode(checkDO.getEdition());
			if (CheckReportEditionEnums.V_MANUFACTURING == edition) {
				// 生产制造业
				// 企业收入核实情况工作底稿（调查科目、核实过程可根据实际情况进行调整填写）
				copyIncome(oldFormId, newFormId, rawAddTime);
			} else if (CheckReportEditionEnums.V_REAL_ESTATE == edition) {
				copyProject(oldFormId, newFormId, rawAddTime);
			} else if (CheckReportEditionEnums.V_CONSTRUCTION == edition) {
				
			} else if (CheckReportEditionEnums.V_CITY == edition) {
				
			} else if (CheckReportEditionEnums.V_LOAN == edition) {
				//复制 content extend
				copyContenExtend(oldFormId, newFormId, rawAddTime);
			}
			
			// 更新验证状态
			FormDO newForm = formDAO.findByFormId(newFormId);
			newForm.setCheckStatus(newForm.getCheckStatus().replaceAll("0", "1"));
			formDAO.update(newForm);
			
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.EXECUTE_FAIL);
			result.setMessage("复制历史报告异常");
		}
		return result;
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
	
	/**
	 * 复制收集信息
	 * 
	 * @param formId
	 * @param newFormId
	 * @param now
	 */
	private void copyColletions(long formId, long newFormId, Date now) {
		List<FAfterwardsCheckCollectionDO> items = FAfterwardsCheckCollectionDAO
			.findByFormId(formId);
		for (FAfterwardsCheckCollectionDO item : items) {
			FAfterwardsCheckCollectionDO doObj = new FAfterwardsCheckCollectionDO();
			BeanCopier.staticCopy(item, doObj);
			doObj.setCollectId(0L);
			doObj.setFormId(newFormId);
			doObj.setRawAddTime(now);
			FAfterwardsCheckCollectionDAO.insert(doObj);
		}
	}
	
	/**
	 * 
	 * 复制基本的监管内容
	 * 
	 * @param formId
	 * @param newFormId
	 * @param now
	 */
	private void saveContent(long formId, long newFormId, Date now) {
		FAfterwardsCheckReportContentDO content = FAfterwardsCheckReportContentDAO
			.findByFormId(formId);
		FAfterwardsCheckReportContentDO doObj = new FAfterwardsCheckReportContentDO();
		BeanCopier.staticCopy(content, doObj);
		doObj.setContentId(0L);
		doObj.setFormId(newFormId);
		doObj.setRawAddTime(now);
		
		long contentId = FAfterwardsCheckReportContentDAO.insert(doObj);
		
		copyAttachments(content.getContentId() + "", CommonAttachmentTypeEnum.CONTENT_ATTACHMENT.code(),
			contentId + "");
		copyAttachments(content.getContentId() + "", CommonAttachmentTypeEnum.TAX_CERTIFICATE.code(),
			contentId + "");
		copyAttachments(content.getContentId() + "", CommonAttachmentTypeEnum.BANK_STATEMENT.code(),
			contentId + "");
		
	}
	
	/**
	 * 
	 * 复制kpi信息
	 * 
	 * @param formId
	 * @param newFormId
	 * @param now
	 */
	private void saveKpies(long formId, long newFormId, Date now) {
		List<FFinancialKpiDO> kpies = FFinancialKpiDAO.findByFormId(formId);
		for (FFinancialKpiDO kpi : kpies) {
			FFinancialKpiDO doObj = new FFinancialKpiDO();
			BeanCopier.staticCopy(kpi, doObj);
			doObj.setKpiId(0L);
			doObj.setFormId(newFormId);
			doObj.setRawAddTime(now);
			FFinancialKpiDAO.insert(doObj);
		}
	}
	
	/**
	 * 复制科目数据
	 * 
	 * @param formId
	 * @param newFormId
	 * @param now
	 */
	private void copyFinancial(long formId, long newFormId, Date now) {
		List<FAfterwardsCheckReportFinancialDO> items = FAfterwardsCheckReportFinancialDAO
			.findByFormId(formId);
		Map<String, Set<String>> map = new HashMap<>();
		for (FAfterwardsCheckReportFinancialDO item : items) {
			FAfterwardsCheckReportFinancialDO doObj = new FAfterwardsCheckReportFinancialDO();
			BeanCopier.staticCopy(item, doObj);
			doObj.setFinancialId(0L);
			doObj.setFormId(newFormId);
			doObj.setRawAddTime(now);
			Set<String> set = map.get(item.getFinancialType());
			if (null == set) {
				set = new HashSet<>();
				map.put(item.getFinancialType(), set);
			}
			if (!BooleanEnum.NO.code().equals(doObj.getDelAble())
				&& !set.contains(item.getFinancialItem())) {
				doObj.setDelAble(BooleanEnum.NO.code());
				set.add(item.getFinancialItem());
			}
			FAfterwardsCheckReportFinancialDAO.insert(doObj);
		}
	}
	
	/**
	 * 复制loan
	 * 
	 * @param formId
	 * @param newFormId
	 * @param now
	 */
	private void copyLoan(long formId, long newFormId, Date now) {
		List<FAfterwardsCheckLoanDO> items = FAfterwardsCheckLoanDAO.findByFormId(formId);
		for (FAfterwardsCheckLoanDO item : items) {
			FAfterwardsCheckLoanDO doObj = new FAfterwardsCheckLoanDO();
			BeanCopier.staticCopy(item, doObj);
			doObj.setId(0L);
			doObj.setFormId(newFormId);
			doObj.setRawAddTime(now);
			FAfterwardsCheckLoanDAO.insert(doObj);
		}
	}
	
	/**
	 * 复制item
	 * 
	 * @param formId
	 * @param newFormId
	 * @param now
	 */
	private void copyItem(long formId, long newFormId, Date now) {
		List<FAfterwardsCheckReportItemDO> items = FAfterwardsCheckReportItemDAO
			.findByFormId(formId);
		for (FAfterwardsCheckReportItemDO item : items) {
			FAfterwardsCheckReportItemDO doObj = new FAfterwardsCheckReportItemDO();
			BeanCopier.staticCopy(item, doObj);
			doObj.setItemId(0L);
			doObj.setFormId(newFormId);
			doObj.setRawAddTime(now);
			FAfterwardsCheckReportItemDAO.insert(doObj);
		}
	}
	
	private List<FAfterwardsCheckReportItemInfo> queryItem(long formId, long newFormId,
															AfterCheckItemTypeEnum itemType) {
		List<FAfterwardsCheckReportItemInfo> list = new ArrayList<>();
		List<FAfterwardsCheckReportItemDO> items = FAfterwardsCheckReportItemDAO
			.findByFormIdAndItemType(formId, itemType.code());
		if (ListUtil.isNotEmpty(items)) {
			for (FAfterwardsCheckReportItemDO item : items) {
				FAfterwardsCheckReportItemInfo info = new FAfterwardsCheckReportItemInfo();
				BeanCopier.staticCopy(item, info);
				info.setItemType(AfterCheckItemTypeEnum.getByCode(item.getItemType()));
				info.setItemId(0L);
				info.setFormId(newFormId);
				info.setDelAble(BooleanEnum.NO.code());
				list.add(info);
			}
		}
		return list;
	}
	
	/**
	 * 复制capital
	 * 
	 * @param formId
	 * @param newFormId
	 * @param now
	 */
	private void copyCapital(long formId, long newFormId, Date now) {
		List<FAfterwardsCheckReportCapitalDO> items = FAfterwardsCheckReportCapitalDAO
			.findByFormId(formId);
		for (FAfterwardsCheckReportCapitalDO item : items) {
			FAfterwardsCheckReportCapitalDO doObj = new FAfterwardsCheckReportCapitalDO();
			BeanCopier.staticCopy(item, doObj);
			doObj.setCapitalId(0L);
			doObj.setFormId(newFormId);
			doObj.setRawAddTime(now);
			FAfterwardsCheckReportCapitalDAO.insert(doObj);
		}
	}
	
	private List<FAfterwardsCheckReportCapitalInfo> queryCapital(long formId, long newFormId,
																	CapitalTypeEnum type) {
		List<FAfterwardsCheckReportCapitalInfo> capitals = new ArrayList<>();
		List<FAfterwardsCheckReportCapitalDO> items = FAfterwardsCheckReportCapitalDAO
			.findByFormIdAndCapitalType(formId, type.code());
		if (ListUtil.isNotEmpty(items)) {
			for (FAfterwardsCheckReportCapitalDO item : items) {
				FAfterwardsCheckReportCapitalInfo info = new FAfterwardsCheckReportCapitalInfo();
				BeanCopier.staticCopy(item, info);
				info.setCapitalType(CapitalTypeEnum.getByCode(item.getCapitalType()));
				info.setCapitalId(0L);
				info.setFormId(newFormId);
				info.setDelAble(BooleanEnum.NO.code());
				capitals.add(info);
			}
		}
		return capitals;
	}
	
	/**
	 * 复制income
	 * 
	 * @param formId
	 * @param newFormId
	 * @param now
	 */
	private void copyIncome(long formId, long newFormId, Date now) {
		List<FAfterwardsCheckReportIncomeDO> items = FAfterwardsCheckReportIncomeDAO
			.findByFormId(formId);
		for (FAfterwardsCheckReportIncomeDO item : items) {
			FAfterwardsCheckReportIncomeDO doObj = new FAfterwardsCheckReportIncomeDO();
			BeanCopier.staticCopy(item, doObj);
			doObj.setIncomeId(0L);
			doObj.setFormId(newFormId);
			doObj.setRawAddTime(now);
			FAfterwardsCheckReportIncomeDAO.insert(doObj);
		}
	}
	
	/**
	 * 复制project
	 * 
	 * @param formId
	 * @param newFormId
	 * @param now
	 */
	private void copyProject(long formId, long newFormId, Date now) {
		List<FAfterwardsCheckReportProjectDO> items = FAfterwardsCheckReportProjectDAO
			.findByFormId(formId);
		for (FAfterwardsCheckReportProjectDO item : items) {
			FAfterwardsCheckReportProjectDO doObj = new FAfterwardsCheckReportProjectDO();
			BeanCopier.staticCopy(item, doObj);
			doObj.setId(0L);
			doObj.setFormId(newFormId);
			doObj.setDelAble(BooleanEnum.NO.code());
			doObj.setRawAddTime(now);
			FAfterwardsCheckReportProjectDAO.insert(doObj);
		}
	}
	
	/**
	 * 复制content extend
	 * 
	 * @param formId
	 * @param newFormId
	 * @param now
	 */
	private void copyContenExtend(long formId, long newFormId, Date now) {
		FAfterwardsCheckReportContentExtendDO item = FAfterwardsCheckReportContentExtendDAO
			.findByFormId(formId);
		if (null != item) {
			FAfterwardsCheckReportContentExtendDO doObj = new FAfterwardsCheckReportContentExtendDO();
			BeanCopier.staticCopy(item, doObj);
			doObj.setId(0L);
			doObj.setFormId(newFormId);
			doObj.setRawAddTime(now);
			FAfterwardsCheckReportContentExtendDAO.insert(doObj);
		}
	}
	
	@Override
	public QueryBaseBatchResult<FAfterwardsCheckInfo> queryAfterwardsCheckReport(	FAfterwardsCheckQueryOrder queryOrder) {
		QueryBaseBatchResult<FAfterwardsCheckInfo> batchResult = new QueryBaseBatchResult<FAfterwardsCheckInfo>();
		
		try {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("projectCode", queryOrder.getProjectCode());
			paramMap.put("projectName", queryOrder.getProjectName());
			paramMap.put("customerId", queryOrder.getCustomerId());
			paramMap.put("customerName", queryOrder.getCustomerName());
			paramMap.put("roundYear", queryOrder.getRoundYear());
			paramMap.put("roundTime", queryOrder.getRoundTime());
			paramMap.put("edition", queryOrder.getEdition());
			paramMap.put("isLatest", queryOrder.getIsLatest());
			
			long totalSize = extraDAO.searchAfterCheckReportCount(paramMap);
			
			PageComponent component = new PageComponent(queryOrder, totalSize);
			if (totalSize > 0) {
				paramMap.put("sortCol", queryOrder.getSortCol());
				paramMap.put("sortOrder", queryOrder.getSortOrder());
				paramMap.put("limitStart", queryOrder.getLimitStart());
				paramMap.put("pageSize", queryOrder.getPageSize());
				List<FAfterwardsCheckDO> pageList = extraDAO.searchAfterCheckReportList(paramMap);
				
				List<FAfterwardsCheckInfo> list = batchResult.getPageList();
				if (totalSize > 0) {
					for (FAfterwardsCheckDO doObj : pageList) {
						FAfterwardsCheckInfo info = convertToInfo(doObj);
						list.add(info);
					}
				}
				
				batchResult.initPageParam(component);
				batchResult.setPageList(list);
			}
			batchResult.setSuccess(true);
			return batchResult;
		} catch (Exception e) {
			logger.error("查询银行贷款及其他融资失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			return batchResult;
		}
	}
	
	@Override
	public FormBaseResult saveLitigation(final FAfterwardsCheckLitigationOrder order) {
		return commonFormSaveProcess(order, "保存保后检查报告-诉讼保函类", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				final Date now = FcsPmDomainHolder.get().getSysDate();
				// 取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				long id = order.getId();
				long formId = form.getFormId();
				FAfterwardsCheckDO checkDO = FAfterwardsCheckDAO.findByFormId(formId);
				if (null == checkDO) {
					checkDO = new FAfterwardsCheckDO();
					BeanCopier.staticCopy(order, checkDO, UnBoxingConverter.getInstance());
					checkDO.setFormId(formId);
					checkDO.setRawAddTime(now);
					id = FAfterwardsCheckDAO.insert(checkDO);
				} else {
					//					if (checkDO.getId() != order.getId()) {
					//						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
					//							"未找到保后检查报告 ");
					//					}
					//					
					//					BeanCopier.staticCopy(order, checkDO, UnBoxingConverter.getInstance());
					//					FAfterwardsCheckDAO.update(checkDO);
					//					
					//					id = checkDO.getId();
				}
				
				FAfterwardsCheckLitigationDO litigation = FAfterwardsCheckLitigationDAO
					.findByFormId(formId);
				if (null == litigation) {
					litigation = new FAfterwardsCheckLitigationDO();
					BeanCopier.staticCopy(order, litigation);
					litigation.setFormId(formId);
					litigation.setRawAddTime(now);
					FAfterwardsCheckLitigationDAO.insert(litigation);
				} else {
					BeanCopier.staticCopy(order, litigation);
					litigation.setFormId(formId);
					FAfterwardsCheckLitigationDAO.update(litigation);
				}
				
				FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
					"result");
				result.setKeyId(id);
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FAfterwardsCheckLitigationInfo findLitigationByFormId(long formId) {
		FAfterwardsCheckLitigationDO litigation = FAfterwardsCheckLitigationDAO
			.findByFormId(formId);
		if (null == litigation) {
			return null;
		}
		
		FAfterwardsCheckLitigationInfo info = new FAfterwardsCheckLitigationInfo();
		BeanCopier.staticCopy(litigation, info);
		info.setCaseStatus(CaseStatusEnum.getByCode(litigation.getCaseStatus()));
		info.setCoInstitutionChargeType(ChargeTypeEnum.getByCode(litigation
			.getCoInstitutionChargeType()));
		info.setGuaranteeFeeType(ChargeTypeEnum.getByCode(litigation.getGuaranteeFeeType()));
		return info;
	}
	
	@Override
	public FAfterwardsCheckReportContentInfo queryFinancilInfo(String projectCode) {
		FAfterwardsCheckQueryOrder queryOrder = new FAfterwardsCheckQueryOrder();
		queryOrder.setProjectCode(projectCode);
		queryOrder.setPageNumber(1L);
		queryOrder.setPageSize(1L);
		QueryBaseBatchResult<FAfterwardsCheckInfo> batchResult = queryAfterwardsCheckReport(queryOrder);
		
		if (null == batchResult || ListUtil.isEmpty(batchResult.getPageList())) {
			return null;
		}
		
		long formId = batchResult.getPageList().get(0).getFormId();
		FAfterwardsCheckReportContentInfo info = new FAfterwardsCheckReportContentInfo();
		
		Map<KpiTypeEnum, List<FinancialKpiInfo>> map = financialKpiService.queryByFormId(formId);
		if (null != map) {
			info.setFinancials(map.get(KpiTypeEnum.FINANCIAL_TABLE));
			addClass(info.getFinancials());
			info.setProfits(map.get(KpiTypeEnum.PROFIT_TABLE));
			addClass(info.getProfits());
			info.setFlows(map.get(KpiTypeEnum.FLOW_TABLE));
			addClass(info.getFlows());
		}
		return info;
	}
}
