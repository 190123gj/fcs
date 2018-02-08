package com.born.fcs.pm.biz.service.risklevel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.convert.DoConvert;
import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.convert.VoConvert;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FInvestigationFinancialReviewKpiDO;
import com.born.fcs.pm.dal.dataobject.FRiskLevelDO;
import com.born.fcs.pm.dal.dataobject.FRiskLevelDetailDO;
import com.born.fcs.pm.dal.dataobject.FRiskLevelScoreTemplateDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dataobject.RiskLevelDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.DataFinancialHelper;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CheckPhaseEnum;
import com.born.fcs.pm.ws.enums.EvaluationTypeEnum;
import com.born.fcs.pm.ws.enums.KpiTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.RiskLevelEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.CollectionInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.risklevel.FRiskLevelInfo;
import com.born.fcs.pm.ws.info.risklevel.FRiskLevelScoreTemplateInfo;
import com.born.fcs.pm.ws.info.risklevel.RiskLevelInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.risklevel.FRiskLevelDetailOrder;
import com.born.fcs.pm.ws.order.risklevel.FRiskLevelOrder;
import com.born.fcs.pm.ws.order.risklevel.RiskLevelQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.risklevel.RiskLevelService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * 风险评级
 * 
 * @author lirz
 * 
 * 2016-5-19 上午9:35:58
 */
@Service("riskLevelService")
public class RiskLevelServiceImpl extends BaseFormAutowiredDomainService implements
																		RiskLevelService {
	
	@Override
	protected FormBaseResult createResult() {
		return new FormBaseResult();
	}
	
	@Override
	public FormBaseResult save(final FRiskLevelOrder order) {
		return commonFormSaveProcess(order, "项目风险等级评级-初评", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				final Date now = FcsPmDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				long riskLevelId = order.getId();
				FRiskLevelDO doObj = FRiskLevelDAO.findByFormId(form.getFormId());
				if (null == doObj) {
					doObj = new FRiskLevelDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					doObj.setFormId(form.getFormId());
					doObj.setRawAddTime(now);
					
					calculateRiskLevel(order, doObj);
					doObj.setCheckPhase(order.getPhases());
					riskLevelId = FRiskLevelDAO.insert(doObj);
				} else {
					if (riskLevelId != doObj.getId()) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"表单已经过期，请刷新页面重新操作");
					}
					
					calculateRiskLevel(order, doObj);
					doObj.setCheckPhase(order.getPhases());
					FRiskLevelDAO.update(doObj);
				}
				
				//保存评分数据
				saveDetails(order, riskLevelId, now);
				
				return null;
			}
		}, null, null);
	}
	
	private void calculateRiskLevel(final FRiskLevelOrder order, FRiskLevelDO doObj) {
		if (order.isRe()) {
			//复评
			RiskLevelEnum riskLevel = calculateRiskLevel(order.getReevaluation());
			doObj.setReevaluation(order.getReevaluation());
			if (StringUtil.equals(order.getHasReevaluationDesc(), BooleanEnum.YES.code())) {
				//选择复评的等级
				doObj.setReevaluationLevel(order.getReevaluationLevel());
				doObj.setHasReevaluationDesc(order.getHasReevaluationDesc());
				doObj.setReevaluationDesc(order.getReevaluationDesc());
			} else {
				doObj.setReevaluationLevel(riskLevel.code());
			}
			doObj.setReevaluationId(order.getUserId());
			doObj.setReevaluationAccount(order.getUserAccount());
			doObj.setReevaluationName(order.getUserName());
			doObj.setCheckLevel(doObj.getReevaluationLevel());
		} else {
			RiskLevelEnum riskLevel = calculateRiskLevel(order.getEvaluation());
			doObj.setEvaluation(order.getEvaluation());
			if (StringUtil.equals(order.getHasEvaluationDesc(), BooleanEnum.YES.code())) {
				//选择初评的等级
				doObj.setEvaluationLevel(order.getEvaluationLevel());
				doObj.setHasEvaluationDesc(order.getHasEvaluationDesc());
				doObj.setEvaluationDesc(order.getEvaluationDesc());
			} else {
				doObj.setEvaluationLevel(riskLevel.code());
			}
			//初评时 设置复评等级和初评等级一样 方便查询列表
			doObj.setReevaluationLevel(doObj.getEvaluationLevel());
			doObj.setEnterpriseType(order.getEnterpriseType());
			doObj.setProjectType(order.getProjectType());
			doObj.setCheckLevel(doObj.getEvaluationLevel());
		}
	}
	
	/**
	 * 
	 * 计算风险等级
	 * 
	 * @param score
	 * @return
	 */
	private RiskLevelEnum calculateRiskLevel(double score) {
		BigDecimal totalScore = new BigDecimal(score);
		if (totalScore.compareTo(new BigDecimal(RiskLevelEnum.NOMAL.minScore())) >= 0) {
			return RiskLevelEnum.NOMAL;
		} else if (totalScore.compareTo(new BigDecimal(RiskLevelEnum.ATTENTION.minScore())) >= 0) {
			return RiskLevelEnum.ATTENTION;
		} else if (totalScore.compareTo(new BigDecimal(RiskLevelEnum.SECONDARY.minScore())) >= 0) {
			return RiskLevelEnum.SECONDARY;
		} else if (totalScore.compareTo(new BigDecimal(RiskLevelEnum.SUSPICIOUS.minScore())) >= 0) {
			return RiskLevelEnum.SUSPICIOUS;
		} else {
			return RiskLevelEnum.LOSE;
		}
	}
	
	/** 保存detail数据 */
	private void saveDetails(final FRiskLevelOrder order, final long riskLevelId, Date now) {
		boolean isRe = order.isRe();
		List<FRiskLevelDetailDO> datas = FRiskLevelDetailDAO.findByRiskLevelId(riskLevelId);
		Map<Long, FRiskLevelDetailDO> map = new HashMap<>();
		if (ListUtil.isNotEmpty(datas)) {
			for (FRiskLevelDetailDO data : datas) {
				map.put(data.getScoreTemplateId(), data);
			}
		}
		saveDetails(riskLevelId, now, EvaluationTypeEnum.ENTERPRISE, order.getEnterprise(), isRe,
			map);
		saveDetails(riskLevelId, now, EvaluationTypeEnum.PROJECT, order.getProject(), isRe, map);
		saveDetails(riskLevelId, now, EvaluationTypeEnum.COUNTER, order.getCounter(), isRe, map);
	}
	
	/** 保存detail数据 */
	@SuppressWarnings("deprecation")
	private void saveDetails(long riskLevelId, Date now, EvaluationTypeEnum type,
								List<FRiskLevelDetailOrder> orders, boolean isRe,
								Map<Long, FRiskLevelDetailDO> map) {
		if (ListUtil.isEmpty(orders)) {
			return;
		}
		
		for (FRiskLevelDetailOrder order : orders) {
			if (null == order) {
				continue;
			}
			
			FRiskLevelDetailDO data = map.get(order.getScoreTemplateId());
			if (null == data) {
				data = new FRiskLevelDetailDO();
				BeanCopier.staticCopy(order, data, UnBoxingConverter.getInstance());
				data.setRiskLevelId(riskLevelId);
				data.setRawAddTime(now);
				data.setScoreTemplateId(order.getScoreTemplateId());
				FRiskLevelDetailDAO.insert(data);
			} else {
				if (isRe) {
					if (new BigDecimal(data.getReevaluation()).compareTo(new BigDecimal(order
						.getReevaluation())) == 0
						&& StringUtil.equals(data.getEvaluationReason(),
							order.getEvaluationReason())) {
						continue;
					}
					data.setReevaluation(order.getReevaluation());
					data.setEvaluationReason(order.getEvaluationReason());
				} else {
					if (new BigDecimal(data.getEvaluation())
							.compareTo(new BigDecimal(order.getEvaluation())) == 0
							&& new BigDecimal(data.getReevaluation())
									.compareTo(new BigDecimal(order
											.getReevaluation())) == 0) {
						continue;
					}
					data.setEvaluation(order.getEvaluation());
				}
				FRiskLevelDetailDAO.update(data);
			}
		}
	}
	
	@Override
	public FRiskLevelInfo findByFormId(long formId) {
		FRiskLevelDO doObj = FRiskLevelDAO.findByFormId(formId);
		if (null == doObj) {
			return null;
		}
		
		FRiskLevelInfo info = new FRiskLevelInfo();
		BeanCopier.staticCopy(doObj, info);
		info.setEvaluationLevel(RiskLevelEnum.getByCode(doObj.getEvaluationLevel()));
		info.setReevaluationLevel(RiskLevelEnum.getByCode(doObj.getReevaluationLevel()));
		info.setCheckLevel1(RiskLevelEnum.getByCode(doObj.getCheckLevel1()));
		info.setCheckLevel2(RiskLevelEnum.getByCode(doObj.getCheckLevel2()));
		info.setCheckLevel3(RiskLevelEnum.getByCode(doObj.getCheckLevel3()));
		info.setCheckLevel(RiskLevelEnum.getByCode(doObj.getCheckLevel()));
		info.setCheckPhase(CheckPhaseEnum.getByCode(doObj.getCheckPhase()));
		findDetails(info);
		
		return info;
	}
	
	/** 设置detail数据 */
	private void findDetails(FRiskLevelInfo info) {
		List<FRiskLevelScoreTemplateInfo> enterprise = new ArrayList<>();
		List<FRiskLevelScoreTemplateInfo> project = new ArrayList<>();
		List<FRiskLevelScoreTemplateInfo> counter = new ArrayList<>();
		
		Map<Long, FRiskLevelDetailDO> scoreMap = new HashMap<>();
		List<FRiskLevelDetailDO> list = FRiskLevelDetailDAO.findByRiskLevelId(info.getId());
		if (ListUtil.isNotEmpty(list)) {
			for (FRiskLevelDetailDO doObj : list) {
				scoreMap.put(doObj.getScoreTemplateId(), doObj);
			}
		}
		
		List<FRiskLevelScoreTemplateDO> templateDOs = FRiskLevelScoreTemplateDAO.findAll();
		
		FRiskLevelScoreTemplateInfo last = null;
		boolean begin = false;
		double evaluation = 0d;
		double reevaluation = 0d;
		for (FRiskLevelScoreTemplateDO doObj : templateDOs) {
			FRiskLevelScoreTemplateInfo template = new FRiskLevelScoreTemplateInfo();
			BeanCopier.staticCopy(doObj, template);
			FRiskLevelDetailDO score = scoreMap.get(doObj.getId());
			if (null != score) {
				template.setEvaluation(score.getEvaluation());
				template.setReevaluation(score.getReevaluation());
				template.setEvaluationReason(score.getEvaluationReason());
			}
			
			EvaluationTypeEnum type = EvaluationTypeEnum.getByCode(template.getEvaluationType());
			if (type == EvaluationTypeEnum.ENTERPRISE) {
				enterprise.add(template);
			} else if (type == EvaluationTypeEnum.PROJECT) {
				project.add(template);
			} else if (type == EvaluationTypeEnum.COUNTER) {
				counter.add(template);
				// ---- 计算小计项总分 begin ----
				if (null != last && StringUtil.isBlank(template.getIndexNo())) {
					begin = true;
					evaluation += template.getEvaluation();
					reevaluation += template.getReevaluation();
					last.setEvaluation(evaluation);
					last.setReevaluation(reevaluation);
					last.setIdFlag("subth_" + last.getId());
					template.setClassFlag("subth_" + last.getId());
				}
				if (begin && StringUtil.isNotBlank(template.getIndexNo())) {
					begin = false;
					evaluation = 0d;
					reevaluation = 0d;
				}
				if (StringUtil.isNotBlank(template.getIndexNo())) {
					last = template;
				}
				// ---- 计算小计项总分 end ----
			}
		}
		
		info.setEnterprise(enterprise);
		info.setProject(project);
		info.setCounter(counter);
	}
	
	@Override
	public QueryBaseBatchResult<RiskLevelInfo> queryList(RiskLevelQueryOrder queryOrder) {
		QueryBaseBatchResult<RiskLevelInfo> baseBatchResult = new QueryBaseBatchResult<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("projectCode", queryOrder.getProjectCode());
		paramMap.put("projectName", queryOrder.getProjectName());
		paramMap.put("customerName", queryOrder.getCustomerName());
		paramMap.put("busiManagerName", queryOrder.getBusiManagerName());
		if (null != queryOrder.getProjectStatus()) {
			paramMap.put("projectStatus", queryOrder.getProjectStatus().code());
		}
		paramMap.put("formStatus", queryOrder.getFormStatus());
		paramMap.put("phases", queryOrder.getPhases());
		paramMap.put("riskLevel", queryOrder.getRiskLevel());
		paramMap.put("loginUserId", queryOrder.getLoginUserId());
		paramMap.put("draftUserId", queryOrder.getDraftUserId());
		paramMap.put("deptIdList", queryOrder.getDeptIdList());
		
		long totalSize = extraDAO.searchRiskLevelCount(paramMap);
		
		PageComponent component = new PageComponent(queryOrder, totalSize);
		if (totalSize > 0) {
			paramMap.put("sortCol", queryOrder.getSortCol());
			paramMap.put("sortOrder", queryOrder.getSortOrder());
			paramMap.put("limitStart", queryOrder.getLimitStart());
			paramMap.put("pageSize", queryOrder.getPageSize());
		}
		List<RiskLevelDO> pageList = extraDAO.searchRiskLevel(paramMap);
		
		List<RiskLevelInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (RiskLevelDO doObj : pageList) {
				RiskLevelInfo info = new RiskLevelInfo();
				VoConvert.convertSimpleFormProjectDO2VoInfo(doObj, info);
				BeanCopier.staticCopy(doObj, info);
				info.setRiskLevelId(doObj.getRiskLevelId());
				info.setReevaluationId(doObj.getReevaluationId());
				info.setEvaluationLevel(RiskLevelEnum.getByCode(doObj.getEvaluationLevel()));
				info.setReevaluationLevel(RiskLevelEnum.getByCode(doObj.getReevaluationLevel()));
				info.setCheckLevel(RiskLevelEnum.getByCode(doObj.getCheckLevel()));
				info.setCheckPhase(CheckPhaseEnum.getByCode(doObj.getCheckPhase()));
				list.add(info);
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public List<FRiskLevelScoreTemplateInfo> queryTemplates() {
		List<FRiskLevelScoreTemplateDO> list = FRiskLevelScoreTemplateDAO.findAll();
		
		List<FRiskLevelScoreTemplateInfo> infos = new ArrayList<>();
		for (FRiskLevelScoreTemplateDO doObj : list) {
			FRiskLevelScoreTemplateInfo info = new FRiskLevelScoreTemplateInfo();
			BeanCopier.staticCopy(doObj, info);
			infos.add(info);
		}
		return infos;
	}
	
	//净资产=资产合计-负债合计（数据来源资产负债表中）
	//担保金额为当前在保余额 在保余额=放款金额合计-还款金额合计
	//净资产保障倍数=最新净资产额/担保金额
	
	//销售收入取利润表中“主营业务收入”，取上一年年报
	//销售收入保障倍数=上一年年报主营销售收入/担保金额
	
	@Override
	public CollectionInfo queryMultiple(String projectCode) {
		CollectionInfo info = new CollectionInfo();
		Map<String, Object> map = new HashMap<>();
		String capitalMultiple = "CAPITAL_MULTIPLE";
		String incomeMultiple = "INCOME_MULTIPLE";
		map.put(capitalMultiple, 0d);
		map.put(incomeMultiple, 0d);
		info.setMap(map);
		long reviewId = extraDAO.queryNewestFinancialKpiReviewId(projectCode);
		if (reviewId <= 0) {
			// 没有数据
			return info;
		}
		
		ProjectDO project = projectDAO.findByProjectCode(projectCode);
		//担保金额
		Money releasingAmount = new Money(0L);
		if (null != project) {
			releasingAmount = project.getLoanedAmount().subtract(project.getRepayedAmount());
		}
		
		if (releasingAmount.equals(Money.zero())) {
			//担保金额为0
			return info;
		}
		//资产合计
		Money totalCapital = queryKpiData(reviewId, KpiTypeEnum.FINANCIAL_TABLE,
			DataFinancialHelper.BALANCE_TOTAL_CAPITAL, 0);
		//负债合计
		Money totalLiability = queryKpiData(reviewId, KpiTypeEnum.FINANCIAL_TABLE,
			DataFinancialHelper.BALANCE_TOTAL_LIABILITY, 0);
		//销售收入取利润表中“主营业务收入”，取上一年年报
		Money mainBusinessIncome = queryKpiData(reviewId, KpiTypeEnum.PROFIT_TABLE,
			DataFinancialHelper.PROFIT_MAIN_BUSINESS_INCOME, 1);
		
		//		1．	净资产保障倍数（2分）
		//		1）	净资产保障倍数≥3（2分）
		//		2）	净资产保障倍数≥2（1.5分）
		//		3）	净资产保障倍数≥1.5（1分）
		//		4）	净资产保障倍数≥1（0.5分）
		double d = (1.0d * totalCapital.subtract(totalLiability).getCent())
					/ releasingAmount.getCent();
		long multiple = (long) (d * 10);
		if (multiple >= 30L) {
			map.put(capitalMultiple, 2d);
		} else if (multiple >= 20L) {
			map.put(capitalMultiple, 1.5d);
		} else if (multiple >= 15L) {
			map.put(capitalMultiple, 1d);
		} else if (multiple >= 10L) {
			map.put(capitalMultiple, 0.5d);
		}
		
		//		2．	销售收入保障倍数（2分）
		//		1）	销售收入保障倍数≥5（2分）
		//		2）	销售收入保障倍数≥4（1.5分）
		//		3）	销售收入保障倍数≥3（1分）
		//		4）	销售收入保障倍数≥2（0.5分）
		multiple = mainBusinessIncome.getCent() / releasingAmount.getCent();
		if (multiple >= 5L) {
			map.put(incomeMultiple, 2d);
		} else if (multiple >= 4L) {
			map.put(incomeMultiple, 1.5d);
		} else if (multiple >= 3L) {
			map.put(incomeMultiple, 1d);
		} else if (multiple >= 2L) {
			map.put(incomeMultiple, 0.5d);
		}
		
		return info;
	}
	
	private Money queryKpiData(long reviewId, KpiTypeEnum kpiType, String kpiCode, int size) {
		FInvestigationFinancialReviewKpiDO kpi = new FInvestigationFinancialReviewKpiDO();
		kpi.setFReviewId(reviewId);
		kpi.setKpiCode(kpiCode);
		kpi.setKpiType(kpiType.code());
		int pageSize = size + 1;
		List<FInvestigationFinancialReviewKpiDO> datas = FInvestigationFinancialReviewKpiDAO
			.findByCondition(kpi, 0, pageSize);
		Money money = new Money(0L);
		if (ListUtil.isNotEmpty(datas) && datas.size() >= pageSize) {
			String s = datas.get(size).getKpiValue();
			if (StringUtil.isNotBlank(s)) {
				money = Money.amout(s);
			}
		} else {
			logger.error("没有找到数据kpi数据：[{}]-[{}]-[{}]-[{}]", new Object[] { reviewId, kpiType,
																			kpiCode, pageSize });
		}
		
		return money;
	}
	
	@Override
	public QueryBaseBatchResult<ProjectInfo> queryProjects(ProjectQueryOrder queryOrder) {
		QueryBaseBatchResult<ProjectInfo> baseBatchResult = new QueryBaseBatchResult<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("projectCode", queryOrder.getProjectCode());
		paramMap.put("projectName", queryOrder.getProjectName());
		paramMap.put("customerName", queryOrder.getCustomerName());
		paramMap.put("busiManagerId", queryOrder.getBusiManagerId());
		List<String> phases = Lists.newArrayList();
		if (ListUtil.isNotEmpty(queryOrder.getPhasesList())) {
			for (ProjectPhasesEnum s : queryOrder.getPhasesList()) {
				phases.add(s.getCode());
			}
			paramMap.put("phases", phases);
		}
		paramMap.put("busiTypeList", queryOrder.getBusiTypeList());
		
		long totalSize = extraDAO.searchRiskLevelProjectCount(paramMap);
		
		PageComponent component = new PageComponent(queryOrder, totalSize);
		if (totalSize > 0) {
			paramMap.put("sortCol", queryOrder.getSortCol());
			paramMap.put("sortOrder", queryOrder.getSortOrder());
			paramMap.put("limitStart", queryOrder.getLimitStart());
			paramMap.put("pageSize", queryOrder.getPageSize());
			List<ProjectDO> pageList = extraDAO.searchRiskLevelProjectList(paramMap);
			
			List<ProjectInfo> list = baseBatchResult.getPageList();
			for (ProjectDO doObj : pageList) {
				ProjectInfo info = DoConvert.convertProjectDO2Info(doObj);
				list.add(info);
			}
			
			baseBatchResult.initPageParam(component);
			baseBatchResult.setPageList(list);
		}
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
}
