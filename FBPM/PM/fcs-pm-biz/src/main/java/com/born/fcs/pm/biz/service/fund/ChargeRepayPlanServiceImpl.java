package com.born.fcs.pm.biz.service.fund;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.fm.ws.enums.ForecastFeeTypeEnum;
import com.born.fcs.fm.ws.enums.ForecastTypeEnum;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountOrder;
import com.born.fcs.fm.ws.service.forecast.ForecastService;
import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.exception.FcsPmBizException;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.ChargeRepayPlanDO;
import com.born.fcs.pm.dal.dataobject.ChargeRepayPlanDetailDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.PlanTypeEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeApplyInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeRecordInfo;
import com.born.fcs.pm.ws.info.fund.ChargeRepayPlanDetailInfo;
import com.born.fcs.pm.ws.info.fund.ChargeRepayPlanInfo;
import com.born.fcs.pm.ws.order.formchange.FormCheckCanChangeOrder;
import com.born.fcs.pm.ws.order.fund.ChargeRepayPlanDetailOrder;
import com.born.fcs.pm.ws.order.fund.ChargeRepayPlanDetailQueryOrder;
import com.born.fcs.pm.ws.order.fund.ChargeRepayPlanOrder;
import com.born.fcs.pm.ws.order.fund.ChargeRepayPlanQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.formchange.FormChangeProcessService;
import com.born.fcs.pm.ws.service.fund.ChargeRepayPlanService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("chargeRepayPlanService")
public class ChargeRepayPlanServiceImpl extends BaseAutowiredDomainService implements
																			ChargeRepayPlanService,
																			FormChangeProcessService {
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	ForecastService forecastServiceClient;
	
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
				
				//				if (project.getStatus() == ProjectStatusEnum.PAUSE) {
				//					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
				//						"项目已暂缓");
				//				}
				
				boolean isUpdate = false;
				if (!StringUtil.equals(order.getIsAffirm(), BooleanEnum.YES.code())) {
					order.setIsAffirm(BooleanEnum.NO.code());
				}
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
				
				if (!"IS".equals(order.getIsChargePlan())) {
					plan.setIsChargePlan(BooleanEnum.NO.code());
				}
				if (!"IS".equals(order.getIsRepayPlan())) {
					plan.setIsRepayPlan(BooleanEnum.NO.code());
				}
				
				List<ChargeRepayPlanDetailDO> oldList = null;
				if (isUpdate) {
					chargeRepayPlanDAO.update(plan);
					//删掉原来的新增
					oldList = chargeRepayPlanDetailDAO.findByPlanId(plan.getPlanId());
					chargeRepayPlanDetailDAO.deleteByPlanId(plan.getPlanId());
				} else {
					plan.setRawAddTime(now);
					plan.setPlanId(chargeRepayPlanDAO.insert(plan));
				}
				
				boolean isConfirm = StringUtil.equals(plan.getIsAffirm(), BooleanEnum.YES.code());
				
				if (isConfirm) {
					
					if (order.getBeforeDays() <= 0 || order.getCycleDays() <= 0) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.INCOMPLETE_REQ_PARAM,
							"提前天数、频率天数必须大于0");
					}
					
					//检查是否已有还款计划
					if (order.getIsFormChangeApply() != BooleanEnum.IS
						&& StringUtil.equals(order.getIsRepayPlan(), BooleanEnum.IS.code())) {
						List<ChargeRepayPlanDO> plans = chargeRepayPlanDAO.findByProjectCode(plan
							.getProjectCode());
						boolean hasRepayPlan = false;
						if (plans != null) {
							
							for (ChargeRepayPlanDO p : plans) {
								
								if (p.getPlanId() != plan.getPlanId()
									&& StringUtil.equals(p.getIsRepayPlan(), BooleanEnum.IS.code())
									&& StringUtil.equals(p.getIsAffirm(), BooleanEnum.YES.code())) {
									
									List<ChargeRepayPlanDetailDO> details = chargeRepayPlanDetailDAO
										.findByPlanId(p.getPlanId());
									
									if (details != null) {
										
										for (ChargeRepayPlanDetailDO detail : details) {
											if (StringUtil.equals(detail.getPlanType(),
												PlanTypeEnum.REPAY_PLAN.code())) {
												hasRepayPlan = true;
												break;
											}
										}
									}
								}
								if (hasRepayPlan)
									break;
							}
						}
						
						if (hasRepayPlan) {
							throw ExceptionFactory.newFcsException(
								FcsResultEnum.INCOMPLETE_REQ_PARAM, "还款计划已存在");
						}
					}
				}
				Calendar calendar = Calendar.getInstance();
				List<ChargeRepayPlanDetailDO> newList = Lists.newArrayList();
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
						detailDO.setCycleDays(plan.getCycleDays());
						if (isConfirm) {
							calendar.setTime(detailDO.getEndTime());
							calendar.add(Calendar.DAY_OF_MONTH, -1 * plan.getBeforeDays());
							Date nextNotifyTime = getNextNotifyTime(calendar,
								DateUtil.getStartTimeOfTheDate(now), detailDO.getCycleDays());
							if (nextNotifyTime.before(detailDO.getEndTime())) {
								detailDO.setNextNotifyTime(nextNotifyTime);
							} else {
								detailDO.setNextNotifyTime(null);
							}
						} else {
							detailDO.setNextNotifyTime(null);
						}
						newList.add(detailDO);
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
						
						try {
							detail.check();
						} catch (IllegalArgumentException ex) {
							throw ExceptionFactory.newFcsException(
								FcsResultEnum.INCOMPLETE_REQ_PARAM, ex.getLocalizedMessage());
						}
						
						ChargeRepayPlanDetailDO detailDO = new ChargeRepayPlanDetailDO();
						BeanCopier.staticCopy(detail, detailDO);
						detailDO.setPlanType(PlanTypeEnum.REPAY_PLAN.code());
						detailDO.setProjectCode(plan.getProjectCode());
						detailDO.setPlanId(plan.getPlanId());
						detailDO.setIsNotify(BooleanEnum.NO.code());
						detailDO.setCycleDays(plan.getCycleDays());
						detailDO.setRawAddTime(now);
						if (isConfirm) {
							calendar.setTime(detailDO.getEndTime());
							calendar.add(Calendar.DAY_OF_MONTH, -1 * plan.getBeforeDays());
							Date nextNotifyTime = getNextNotifyTime(calendar,
								DateUtil.getStartTimeOfTheDate(now), detailDO.getCycleDays());
							if (nextNotifyTime.before(detailDO.getEndTime())) {
								detailDO.setNextNotifyTime(nextNotifyTime);
							} else {
								detailDO.setNextNotifyTime(null);
							}
						} else {
							detailDO.setNextNotifyTime(null);
						}
						newList.add(detailDO);
						chargeRepayPlanDetailDAO.insert(detailDO);
					}
				}
				if (isConfirm)
					syncForecast(plan, newList, oldList);
				return null;
			}
		}, null, null);
	}
	
	/**
	 * 获取下次提醒时间
	 * @param nextNotifyTime
	 * @param now
	 * @param cycleDays
	 * @return
	 */
	private Date getNextNotifyTime(Calendar nextNotifyTime, Date now, int cycleDays) {
		//下次通知时间在当前时间前，这继续往前推算
		if (nextNotifyTime.getTime().before(now)) {
			nextNotifyTime.add(Calendar.DAY_OF_MONTH, cycleDays);
			return getNextNotifyTime(nextNotifyTime, now, cycleDays);
		}
		return nextNotifyTime.getTime();
	}
	
	/**
	 * 同步预测数据
	 * @param plan
	 * @param newList
	 * @param oldList
	 */
	private void syncForecast(ChargeRepayPlanDO plan, List<ChargeRepayPlanDetailDO> newList,
								List<ChargeRepayPlanDetailDO> oldList) {
		try {
			
			//委贷资金预测
			ProjectInfo project = projectService.queryByCode(plan.getProjectCode(), false);
			if (project != null && ProjectUtil.isEntrusted(project.getBusiType())) {
				if (ListUtil.isNotEmpty(oldList)) {//删除原来的预测
					int repaySeq = 1; //还款序号
					int chargeSeq = 1; //收费序号
					ForecastAccountOrder delOrder = new ForecastAccountOrder();
					delOrder.setSystemForm(SystemEnum.PM);
					for (ChargeRepayPlanDetailDO old : oldList) {
						if (StringUtil.equals(old.getPlanType(), PlanTypeEnum.REPAY_PLAN.code())) {//还款计划
							delOrder.setOrderNo(project.getProjectCode() + "_REPLAYPLAN_"
												+ plan.getPlanId() + "_" + repaySeq);
							repaySeq++;
						} else { //收费计划
							delOrder.setOrderNo(project.getProjectCode() + "_CHARGEPLAN_"
												+ plan.getPlanId() + "_" + chargeSeq);
							chargeSeq++;
						}
						forecastServiceClient.delete(delOrder);
					}
				}
				
				//新增预测
				if (ListUtil.isNotEmpty(newList)) {
					int repaySeq = 1; //还款序号
					int chargeSeq = 1; //收费序号
					for (ChargeRepayPlanDetailDO nw : newList) {
						ForecastAccountOrder addOrder = new ForecastAccountOrder();
						addOrder.setSystemForm(SystemEnum.PM);
						addOrder.setFundDirection(FundDirectionEnum.IN);
						addOrder.setUsedDeptId(String.valueOf(project.getDeptId()));
						addOrder.setUsedDeptName(project.getDeptName());
						addOrder.setForecastType(ForecastTypeEnum.IN_WTDKYW);
						addOrder.setAmount(nw.getAmount());
						addOrder.setForecastStartTime(nw.getEndTime());
						if (StringUtil.equals(nw.getPlanType(), PlanTypeEnum.REPAY_PLAN.code())) {//还款计划
							addOrder.setOrderNo(project.getProjectCode() + "_"
												+ ForecastFeeTypeEnum.REPLAYPLAN.code() + "_"
												+ plan.getPlanId() + "_" + repaySeq);
							addOrder.setForecastMemo("委贷本金收回（" + project.getProjectCode() + "）");
							addOrder.setFeeType(ForecastFeeTypeEnum.REPLAYPLAN);
							addOrder.setProjectCode(project.getProjectCode());
							addOrder.setCustomerId(project.getCustomerId());
							addOrder.setCustomerName(project.getCustomerName());
							repaySeq++;
						} else {//收费计划
							addOrder.setOrderNo(project.getProjectCode() + "_"
												+ ForecastFeeTypeEnum.CHARGEPLAN.code() + "_"
												+ plan.getPlanId() + "_" + chargeSeq);
							addOrder.setForecastMemo("委贷利息收回（" + project.getProjectCode() + "）");
							addOrder.setFeeType(ForecastFeeTypeEnum.CHARGEPLAN);
							addOrder.setProjectCode(project.getProjectCode());
							addOrder.setCustomerId(project.getCustomerId());
							addOrder.setCustomerName(project.getCustomerName());
							chargeSeq++;
						}
						forecastServiceClient.add(addOrder);
					}
				}
			}
			
		} catch (Exception e) {
			logger.error("同步收费/还款计划预测数据到资金出错 {} ", e);
		}
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
			
			if (order.getBusiManagerId() > 0) {
				plan.setUserId(order.getBusiManagerId());
				
			}
			if (StringUtil.isBlank(order.getSortCol())) {
				order.setSortCol("plan_id");
				order.setSortOrder("desc");
			}
			
			long totalCount = chargeRepayPlanDAO.findByConditionCount(plan, order.getLoginUserId(),
				order.getDraftUserId(), order.getDeptIdList());
			PageComponent component = new PageComponent(order, totalCount);
			List<ChargeRepayPlanDO> dataList = chargeRepayPlanDAO.findByCondition(plan,
				order.getLoginUserId(), order.getDraftUserId(), order.getDeptIdList(),
				order.getSortCol(), order.getSortOrder(), component.getFirstRecord(),
				component.getPageSize());
			
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
	
	private ChargeRepayPlanInfo convertPlanDO2Info(ChargeRepayPlanDO DO) {
		if (DO == null)
			return null;
		ChargeRepayPlanInfo info = new ChargeRepayPlanInfo();
		BeanCopier.staticCopy(DO, info);
		info.setIsChargePlan(BooleanEnum.getByCode(DO.getIsChargePlan()));
		info.setIsRepayPlan(BooleanEnum.getByCode(DO.getIsRepayPlan()));
		info.setIsAffirm(BooleanEnum.getByCode(DO.getIsAffirm()));
		return info;
	}
	
	@Override
	public FcsBaseResult checkCanChange(FormCheckCanChangeOrder checkOrder) {
		FcsBaseResult result = createResult();
		try {
			ProjectInfo project = checkOrder.getProject();
			if (project == null && StringUtil.isNotEmpty(checkOrder.getProjectCode())) {
				project = projectService.queryByCode(checkOrder.getProjectCode(), false);
			}
			
			if (project == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "项目不存在");
			}
			
			ChargeRepayPlanQueryOrder queryOrder = new ChargeRepayPlanQueryOrder();
			queryOrder.setProjectCode(project.getProjectCode());
			queryOrder.setIsAffirm(BooleanEnum.YES.code());
			QueryBaseBatchResult<ChargeRepayPlanInfo> queryResult = queryPlan(queryOrder);
			if (queryResult == null || queryResult.getTotalCount() == 0) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "收费&还款计划不存在");
			}
			
			result.setSuccess(true);
			result.setMessage("可以签报");
		} catch (FcsPmBizException be) {
			result.setSuccess(false);
			result.setFcsResultEnum(be.getResultCode());
			result.setMessage(be.getErrorMsg());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("检查收费&还款计划是否可签报出错");
			logger.error("检查费还款&计划是否可签报出错:{}", e);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult processChange(FormChangeApplyInfo applyInfo, FormChangeRecordInfo record) {
		FcsBaseResult result = createResult();
		//DO NOTHING
		result.setSuccess(true);
		return result;
	}
}
