package com.born.fcs.pm.biz.service.fund;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.ChargeRepayPlanDO;
import com.born.fcs.pm.dal.dataobject.ChargeRepayPlanDetailDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.PlanTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.fund.ChargeRepayPlanDetailInfo;
import com.born.fcs.pm.ws.info.fund.ChargeRepayPlanInfo;
import com.born.fcs.pm.ws.order.fund.ChargeRepayPlanDetailOrder;
import com.born.fcs.pm.ws.order.fund.ChargeRepayPlanDetailQueryOrder;
import com.born.fcs.pm.ws.order.fund.ChargeRepayPlanOrder;
import com.born.fcs.pm.ws.order.fund.ChargeRepayPlanQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.fund.ChargeRepayPlanService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("chargeRepayPlanService")
public class ChargeRepayPlanServiceImpl extends BaseAutowiredDomainService implements
																			ChargeRepayPlanService {
	
	@Autowired
	ProjectService projectService;
	
	@Override
	public FcsBaseResult savePlan(final ChargeRepayPlanOrder order) {
		return commonProcess(order, "保存收费/还款计划", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				ChargeRepayPlanDO plan = null;
				if (order.getPlanId() > 0) {
					plan = chargeRepayPlanDAO.findById(order.getPlanId());
					if (plan == null) {
						throw ExceptionFactory
							.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "计划不存在");
					}
				}
				
				//项目信息
				ProjectInfo project = projectService.queryByCode(order.getProjectCode(), false);
				if (project == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "项目不存在");
				}
				
				if (project.getStatus() == ProjectStatusEnum.PAUSE) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"项目已暂缓");
				}
				
				boolean isUpdate = false;
				if (plan == null) {
					plan = new ChargeRepayPlanDO();
					BeanCopier.staticCopy(order, plan, UnBoxingConverter.getInstance());
				} else {
					isUpdate = true;
					BeanCopier.staticCopy(order, plan, UnBoxingConverter.getInstance());
				}
				
				plan.setProjectName(project.getProjectName());
				plan.setCustomerId(project.getCustomerId());
				plan.setCustomerName(project.getCustomerName());
				plan.setBusiType(project.getBusiType());
				plan.setBusiTypeName(project.getBusiTypeName());
				if (isUpdate) {
					chargeRepayPlanDAO.update(plan);
					//删掉原来的新增
					chargeRepayPlanDetailDAO.deleteByPlanId(plan.getPlanId());
				} else {
					plan.setRawAddTime(now);
					plan.setPlanId(chargeRepayPlanDAO.insert(plan));
				}
				
				if ("IS".equals(order.getIsChargePlan())
					&& ListUtil.isNotEmpty(order.getChargeOrder())) {
					
					for (ChargeRepayPlanDetailOrder detail : order.getChargeOrder()) {
						if (detail.isNull())
							continue;
						//收费金额
						detail.setAmount(Money.amout(detail.getAmountStr()));
						
						detail.setStartTime(DateUtil.parse(detail.getStartTimeStr()));
						detail.setEndTime(DateUtil.parse(detail.getEndTimeStr()));
						
						try {
							detail.check();
						} catch (IllegalArgumentException ex) {
							throw ExceptionFactory.newFcsException(
								FcsResultEnum.INCOMPLETE_REQ_PARAM, ex.getLocalizedMessage());
						}
						
						ChargeRepayPlanDetailDO detailDO = new ChargeRepayPlanDetailDO();
						BeanCopier.staticCopy(detail, detailDO);
						detailDO.setPlanType(PlanTypeEnum.CHARGE_PLAN.code());
						detailDO.setProjectCode(plan.getProjectCode());
						detailDO.setPlanId(plan.getPlanId());
						detailDO.setIsNotify(BooleanEnum.NO.code());
						detailDO.setRawAddTime(now);
						chargeRepayPlanDetailDAO.insert(detailDO);
					}
				}
				
				if ("IS".equals(order.getIsRepayPlan())
					&& ListUtil.isNotEmpty(order.getRepayOrder())) {
					
					for (ChargeRepayPlanDetailOrder detail : order.getRepayOrder()) {
						if (detail.isNull())
							continue;
						//还款金额
						detail.setAmount(Money.amout(detail.getAmountStr()));
						
						detail.setStartTime(DateUtil.parse(detail.getStartTimeStr()));
						detail.setEndTime(DateUtil.parse(detail.getEndTimeStr()));
						
						detail.check();
						
						ChargeRepayPlanDetailDO detailDO = new ChargeRepayPlanDetailDO();
						BeanCopier.staticCopy(detail, detailDO);
						detailDO.setPlanType(PlanTypeEnum.REPAY_PLAN.code());
						detailDO.setProjectCode(plan.getProjectCode());
						detailDO.setPlanId(plan.getPlanId());
						detailDO.setIsNotify(BooleanEnum.NO.code());
						chargeRepayPlanDetailDAO.insert(detailDO);
					}
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public ChargeRepayPlanInfo queryPlanById(long planId) {
		ChargeRepayPlanDO plan = chargeRepayPlanDAO.findById(planId);
		if (plan != null) {
			ChargeRepayPlanInfo info = convertPlanDO2Info(plan);
			setPlanDetail(info);
			return info;
		}
		return null;
	}
	
	/**
	 * 设置计划详细
	 * @param info
	 */
	private void setPlanDetail(ChargeRepayPlanInfo info) {
		if (info.getIsChargePlan() == BooleanEnum.IS || info.getIsRepayPlan() == BooleanEnum.IS) {
			List<ChargeRepayPlanDetailDO> details = chargeRepayPlanDetailDAO.findByPlanId(info
				.getPlanId());
			if (ListUtil.isNotEmpty(details)) {
				List<ChargeRepayPlanDetailInfo> repayList = Lists.newArrayList();
				List<ChargeRepayPlanDetailInfo> chargeList = Lists.newArrayList();
				for (ChargeRepayPlanDetailDO detailDO : details) {
					ChargeRepayPlanDetailInfo detail = convertPlanDetailDO2Info(detailDO);
					if (detail.getPlanType() == PlanTypeEnum.REPAY_PLAN) {
						repayList.add(detail);
					} else {
						chargeList.add(detail);
					}
				}
				info.setRepayPlanList(repayList);
				info.setChargePlanList(chargeList);
			}
		}
	}
	
	@Override
	public QueryBaseBatchResult<ChargeRepayPlanInfo> queryPlan(ChargeRepayPlanQueryOrder order) {
		QueryBaseBatchResult<ChargeRepayPlanInfo> batchResult = new QueryBaseBatchResult<ChargeRepayPlanInfo>();
		
		try {
			
			ChargeRepayPlanDO plan = new ChargeRepayPlanDO();
			
			BeanCopier.staticCopy(order, plan);
			
			if (order.getIsChargePlan() != null) {
				plan.setIsChargePlan(order.getIsChargePlan().code());
			}
			
			if (order.getIsRepayPlan() != null) {
				plan.setIsRepayPlan(order.getIsRepayPlan().code());
			}
			
			if (StringUtil.isBlank(order.getSortCol())) {
				order.setSortCol("plan_id");
				order.setSortOrder("desc");
			}
			
			long totalCount = chargeRepayPlanDAO.findByConditionCount(plan, order.getLoginUserId(),
				order.getDeptIdList());
			PageComponent component = new PageComponent(order, totalCount);
			List<ChargeRepayPlanDO> dataList = chargeRepayPlanDAO.findByCondition(plan,
				order.getLoginUserId(), order.getDeptIdList(), order.getSortCol(),
				order.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			
			List<ChargeRepayPlanInfo> list = Lists.newArrayList();
			for (ChargeRepayPlanDO DO : dataList) {
				ChargeRepayPlanInfo info = convertPlanDO2Info(DO);
				if (order.isQueryDetail())
					setPlanDetail(info);
				list.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(list);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询收费/还款计划失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public QueryBaseBatchResult<ChargeRepayPlanDetailInfo> queryPlanDetail(	ChargeRepayPlanDetailQueryOrder order) {
		QueryBaseBatchResult<ChargeRepayPlanDetailInfo> batchResult = new QueryBaseBatchResult<ChargeRepayPlanDetailInfo>();
		try {
			ChargeRepayPlanDetailDO plan = new ChargeRepayPlanDetailDO();
			BeanCopier.staticCopy(order, plan);
			if (order.getIsNotify() != null) {
				plan.setIsNotify(order.getIsNotify().code());
			}
			if (StringUtil.isBlank(order.getSortCol())) {
				order.setSortCol("plan_detail_id");
				order.setSortOrder("desc");
			}
			long totalCount = chargeRepayPlanDetailDAO.findByConditionCount(plan);
			PageComponent component = new PageComponent(order, totalCount);
			List<ChargeRepayPlanDetailDO> dataList = chargeRepayPlanDetailDAO.findByCondition(plan,
				order.getSortCol(), order.getSortOrder(), component.getFirstRecord(),
				component.getPageSize());
			List<ChargeRepayPlanDetailInfo> list = Lists.newArrayList();
			for (ChargeRepayPlanDetailDO DO : dataList) {
				ChargeRepayPlanDetailInfo info = convertPlanDetailDO2Info(DO);
				list.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(list);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询收费/还款计划明细失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	private ChargeRepayPlanDetailInfo convertPlanDetailDO2Info(ChargeRepayPlanDetailDO DO) {
		if (DO == null)
			return null;
		ChargeRepayPlanDetailInfo info = new ChargeRepayPlanDetailInfo();
		BeanCopier.staticCopy(DO, info);
		info.setIsNotify(BooleanEnum.getByCode(DO.getIsNotify()));
		info.setPlanType(PlanTypeEnum.getByCode(DO.getPlanType()));
		return info;
	}
	
	private ChargeRepayPlanInfo convertPlanDO2Info(ChargeRepayPlanDO DO) {
		if (DO == null)
			return null;
		ChargeRepayPlanInfo info = new ChargeRepayPlanInfo();
		BeanCopier.staticCopy(DO, info);
		info.setIsChargePlan(BooleanEnum.getByCode(DO.getIsChargePlan()));
		info.setIsRepayPlan(BooleanEnum.getByCode(DO.getIsRepayPlan()));
		return info;
	}
}
