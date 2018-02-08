package com.born.fcs.fm.biz.service.incomeconfirm;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.fm.biz.convert.UnBoxingConverter;
import com.born.fcs.fm.biz.exception.ExceptionFactory;
import com.born.fcs.fm.biz.job.AsynchronousTaskJob;
import com.born.fcs.fm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.fm.dal.dataobject.IncomeConfirmDO;
import com.born.fcs.fm.dal.dataobject.IncomeConfirmDetailDO;
import com.born.fcs.fm.dataobject.IncomeConfirmDetailListDO;
import com.born.fcs.fm.dataobject.IncomeConfirmListDO;
import com.born.fcs.fm.domain.context.FcsFmDomainHolder;
import com.born.fcs.fm.integration.pm.service.AfterwardsProjectSummaryServiceClient;
import com.born.fcs.fm.integration.pm.service.ProjectServiceClient;
import com.born.fcs.fm.ws.enums.ConfirmStatusEnum;
import com.born.fcs.fm.ws.enums.IncomeConfirmStatusEnum;
import com.born.fcs.fm.ws.info.incomeconfirm.IncomeConfirmDetailInfo;
import com.born.fcs.fm.ws.info.incomeconfirm.IncomeConfirmDetailListInfo;
import com.born.fcs.fm.ws.info.incomeconfirm.IncomeConfirmInfo;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeBatchConfirmDetailOrder;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeBatchConfirmOrder;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeConfirmDetailListQueryOrder;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeConfirmDetailOrder;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeConfirmDetailQueryOrder;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeConfirmOrder;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeConfirmQueryOrder;
import com.born.fcs.fm.ws.result.incomeconfirm.IncomeDetailBatchResult;
import com.born.fcs.fm.ws.service.incomeconfirm.IncomeConfirmService;
import com.born.fcs.pm.biz.service.common.AsynchronousService;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.summary.AfterwardsProjectSummaryResult;
import com.born.fcs.pm.ws.service.financeaffirm.FinanceAffirmService;
import com.born.fcs.pm.ws.service.fund.ChargeNotificationService;
import com.born.fcs.pm.ws.service.report.ProjectReportService;
import com.born.fcs.pm.ws.service.report.info.ProjectChargeDetailInfo;
import com.born.fcs.pm.ws.service.report.order.ProjectChargeDetailQueryOrder;
import com.google.common.collect.Lists;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("incomeConfirmService")
public class IncomeConfirmServiceImpl extends BaseFormAutowiredDomainService implements
																			IncomeConfirmService,
																			AsynchronousService {
	@Autowired
	protected ChargeNotificationService chargeNotificationWebService;
	@Autowired
	ProjectServiceClient projectServiceClient;
	@Autowired
	FinanceAffirmService financeAffirmWebService;
	@Autowired
	ProjectReportService projectReportServiceClient;
	
	@Autowired
	AsynchronousTaskJob asynchronousTaskJob;
	@Autowired
	AfterwardsProjectSummaryServiceClient afterwardsProjectSummaryServiceClient;
	
	@Override
	public FcsBaseResult save(final IncomeConfirmOrder order) {
		return commonProcess(order, "保存收入确认单", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				IncomeConfirmDO incomeConfirm = null;
				Date now = FcsFmDomainHolder.get().getSysDate();
				if (order.getProjectCode() != null) {
					incomeConfirm = incomeConfirmDAO.findByProjectCode(order.getProjectCode());
				}
				if (incomeConfirm == null) { //新增
					incomeConfirm = new IncomeConfirmDO();
					BeanCopier.staticCopy(order, incomeConfirm, UnBoxingConverter.getInstance());
					incomeConfirm.setRawAddTime(now);
					incomeConfirm
						.setIncomeConfirmStatus(IncomeConfirmStatusEnum.NOT_COMPLETE_CONFIRM.code());
					long id = incomeConfirmDAO.insert(incomeConfirm);
					incomeConfirm.setIncomeId(id);
					List<IncomeConfirmDetailOrder> orderList = order.getIncomeConfirmDetailOrders();
					if (ListUtil.isNotEmpty(orderList)) {
						for (IncomeConfirmDetailOrder detailOrder : orderList) {
							IncomeConfirmDetailDO detailDO = new IncomeConfirmDetailDO();
							BeanCopier.staticCopy(detailOrder, detailDO,
								UnBoxingConverter.getInstance());
							detailDO.setIncomeId(id);
							detailDO.setRawAddTime(now);
							incomeConfirmDetailDAO.insert(detailDO);
						}
					}
				} else { //修改
				
					if (order.getNotConfirmedIncomeAmount() != null
						&& order.getNotConfirmedIncomeAmount().greaterThan(Money.zero())) {
						incomeConfirm.setNotConfirmedIncomeAmount(order
							.getNotConfirmedIncomeAmount());
					}
					if (order.getConfirmedIncomeAmount() != null
						&& order.getConfirmedIncomeAmount().greaterThan(Money.zero())) {
						incomeConfirm.setConfirmedIncomeAmount(order.getConfirmedIncomeAmount());
					}
					if (order.getThisMonthConfirmedIncomeAmount() != null
						&& order.getThisMonthConfirmedIncomeAmount().greaterThan(Money.zero())) {
						incomeConfirm.setThisMonthConfirmedIncomeAmount(order
							.getThisMonthConfirmedIncomeAmount());
					}
					if (order.getChargedAmount() != null
						&& order.getChargedAmount().greaterThan(Money.zero())) {
						incomeConfirm.setChargedAmount(order.getChargedAmount());
					}
					//					incomeConfirmDetailDAO.deleteByIncomeId(incomeConfirm.getIncomeId());
					List<IncomeConfirmDetailOrder> orderList = order.getIncomeConfirmDetailOrders();
					
					if (ListUtil.isNotEmpty(orderList)) {
						//计算已确认收入金额
						Money confirmedIncomeAmount = Money.zero();
						//未确认金额
						Money notConfirmedIncomeAmount = Money.zero();
						//本月确认收入金额
						Money thisMonthConfirmedIncomeAmount = Money.zero();
						
						for (IncomeConfirmDetailOrder detailOrder : orderList) {
							if (BooleanEnum.IS.code().equals(detailOrder.getIsConfirmed())) {
								confirmedIncomeAmount = confirmedIncomeAmount.add(detailOrder
									.getIncomeConfirmedAmount());
							}
							String day = DateUtil.dtSimpleChineseFormat(DateUtil.getDateByMonth(
								new Date(), -1));
							if (day.contains(detailOrder.getIncomePeriod())) {
								thisMonthConfirmedIncomeAmount = detailOrder
									.getIncomeConfirmedAmount();
							}
							IncomeConfirmDetailDO detailDO = null;
							if (detailOrder.getId() != null) {
								detailDO = incomeConfirmDetailDAO.findById(detailOrder.getId());
							}
							if (detailOrder.getIncomeId() > 0
								&& detailOrder.getIncomePeriod() != null) {
								detailDO = incomeConfirmDetailDAO.findByIncomeIdAndIncomePeriod(
									detailOrder.getIncomeId(), detailOrder.getIncomePeriod());
							}
							if (detailDO != null) {
								if (detailOrder.getIncomePeriod() != null) {
									detailDO.setIncomePeriod(detailOrder.getIncomePeriod());
								}
								if (detailOrder.getIsConfirmed() == null
									|| BooleanEnum.NO.code().equals(detailOrder.getIsConfirmed())) {
									detailDO.setConfirmStatus(ConfirmStatusEnum.NO_CONFIRM.code());
									detailDO.setIsConfirmed(BooleanEnum.NO.code());
								} else {
									detailDO.setIsConfirmed(BooleanEnum.IS.code());
									detailDO.setConfirmStatus(ConfirmStatusEnum.IS_CONFIRM.code());
								}
								if (detailOrder.getSystemEstimatedAmount() != null
									&& detailOrder.getSystemEstimatedAmount().greaterThan(
										Money.zero())) {
									detailDO.setSystemEstimatedAmount(detailOrder
										.getSystemEstimatedAmount());
								}
								if (detailOrder.getIncomeConfirmedAmount() != null
									&& detailOrder.getIncomeConfirmedAmount().greaterThan(
										Money.zero())) {
									detailDO.setIncomeConfirmedAmount(detailOrder
										.getIncomeConfirmedAmount());
								}
								detailDO.setIncomeId(incomeConfirm.getIncomeId());
								incomeConfirmDetailDAO.update(detailDO);
							} else {
								IncomeConfirmDetailDO detailDO1 = new IncomeConfirmDetailDO();
								BeanCopier.staticCopy(detailOrder, detailDO1,
									UnBoxingConverter.getInstance());
								detailDO1.setIncomeId(incomeConfirm.getIncomeId());
								detailDO1.setRawAddTime(now);
								if (detailOrder.getIsConfirmed() == null
									|| BooleanEnum.NO.code().equals(detailOrder.getIsConfirmed())) {
									detailDO1.setConfirmStatus(ConfirmStatusEnum.NO_CONFIRM.code());
									detailDO1.setIsConfirmed(BooleanEnum.NO.code());
								} else {
									detailDO1.setConfirmStatus(ConfirmStatusEnum.IS_CONFIRM.code());
									detailDO1.setIsConfirmed(BooleanEnum.IS.code());
								}
								incomeConfirmDetailDAO.insert(detailDO1);
							}
							
						}
						if (incomeConfirm.getChargedAmount() != null) {
							notConfirmedIncomeAmount = incomeConfirm.getChargedAmount().subtract(
								confirmedIncomeAmount);
						}
						incomeConfirm.setConfirmedIncomeAmount(confirmedIncomeAmount);
						incomeConfirm.setNotConfirmedIncomeAmount(notConfirmedIncomeAmount);
						incomeConfirm
							.setThisMonthConfirmedIncomeAmount(thisMonthConfirmedIncomeAmount);
						boolean isConfirm = true;//是否完全确认
						for (IncomeConfirmDetailOrder detailOrder : orderList) {
							if (detailOrder.getConfirmStatus() != null
								&& detailOrder.getConfirmStatus().equals(
									ConfirmStatusEnum.NO_CONFIRM.code())) {
								isConfirm = false;
								break;
							}
						}
						if (isConfirm) {
							incomeConfirm
								.setIncomeConfirmStatus(IncomeConfirmStatusEnum.COMPLETE_CONFIRM
									.code());
						} else {
							incomeConfirm
								.setIncomeConfirmStatus(IncomeConfirmStatusEnum.NOT_COMPLETE_CONFIRM
									.code());
						}
					}
					
					incomeConfirmDAO.update(incomeConfirm);
					if (order.isCalculate()) {
						asynchronousTaskJob.addAsynchronousService(IncomeConfirmServiceImpl.this,
							new Object[] { incomeConfirm });
					}
					
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<IncomeConfirmInfo> query(IncomeConfirmQueryOrder order) {
		QueryBaseBatchResult<IncomeConfirmInfo> batchResult = new QueryBaseBatchResult<IncomeConfirmInfo>();
		try {
			IncomeConfirmListDO incomeConfirmList = new IncomeConfirmListDO();
			if (StringUtil.isBlank(order.getSortCol())) {
				order.setSortCol("p.raw_add_time");
				order.setSortOrder("desc");
			}
			BeanCopier.staticCopy(order, incomeConfirmList);
			long totalCount = busiDAO.searchIncomeConfirmListCount(incomeConfirmList);
			PageComponent component = new PageComponent(order, totalCount);
			List<IncomeConfirmListDO> dataList = busiDAO.searchIncomeConfirmList(incomeConfirmList);
			
			List<IncomeConfirmInfo> list = Lists.newArrayList();
			for (IncomeConfirmListDO DO : dataList) {
				IncomeConfirmInfo info = new IncomeConfirmInfo();
				BeanCopier.staticCopy(DO, info);
				info.setIncomeConfirmStatus(IncomeConfirmStatusEnum.getByCode(DO
					.getIncomeConfirmStatus()));
				info.setIncomeConfirmDetailInfos(findIncomeConfirmDetailById(DO.getIncomeId()));
				list.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(list);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询收入确认表失败 {}", e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public IncomeDetailBatchResult<IncomeConfirmDetailListInfo> queryBatchConfirmList(	IncomeConfirmDetailListQueryOrder order) {
		IncomeDetailBatchResult<IncomeConfirmDetailListInfo> batchResult = new IncomeDetailBatchResult<IncomeConfirmDetailListInfo>();
		try {
			IncomeConfirmDetailListDO incomeConfirmList = new IncomeConfirmDetailListDO();
			
			if (StringUtil.isBlank(order.getSortCol())) {
				order.setSortCol("d.income_period");
				order.setSortOrder("desc");
			}
			
			BeanCopier.staticCopy(order, incomeConfirmList);
			
			HashMap countMap = busiDAO.searchIncomeConfirmDetailListCount(incomeConfirmList);
			long totalCount = NumberUtil.parseLong(String.valueOf(countMap.get("countNum")));
			//			//已确认金额合计
			//			Money sumConfirmedAmount = Money.cent(NumberUtil.parseLong(String.valueOf(countMap
			//				.get("sumConfirmedAmount"))));
			//			//系统预估金额合计
			//			Money sumEstimatedAmount = Money.cent(NumberUtil.parseLong(String.valueOf(countMap
			//				.get("sumEstimatedAmount"))));
			PageComponent component = new PageComponent(order, totalCount);
			List<IncomeConfirmDetailListDO> dataList = busiDAO
				.searchIncomeConfirmDetailList(incomeConfirmList);
			
			List<IncomeConfirmDetailListInfo> list = Lists.newArrayList();
			for (IncomeConfirmDetailListDO DO : dataList) {
				IncomeConfirmDetailListInfo info = new IncomeConfirmDetailListInfo();
				BeanCopier.staticCopy(DO, info);
				info.setIncomeConfirmStatus(IncomeConfirmStatusEnum.getByCode(DO
					.getIncomeConfirmStatus()));
				info.setConfirmStatus(ConfirmStatusEnum.getByCode(DO.getConfirmStatus()));
				info.setIsConfirmed(BooleanEnum.getByCode(DO.getIsConfirmed()));
				list.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(list);
			batchResult.initPageParam(component);
			//			batchResult.setSumConfirmedAmount(sumConfirmedAmount);
			//			batchResult.setSumEstimatedAmount(sumEstimatedAmount);
		} catch (Exception e) {
			logger.error("查询收入确认表明细失败 {}", e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public IncomeConfirmInfo findById(long incomeId) {
		IncomeConfirmInfo info = new IncomeConfirmInfo();
		IncomeConfirmDO DO = incomeConfirmDAO.findById(incomeId);
		BeanCopier.staticCopy(DO, info);
		info.setIncomeConfirmStatus(IncomeConfirmStatusEnum.getByCode(DO.getIncomeConfirmStatus()));
		//更新系统预估金额
		//updateIncomeConfirmDetail(DO.getProjectCode(), incomeId);
		info.setIncomeConfirmDetailInfos(findIncomeConfirmDetailById(incomeId));
		return info;
	}
	
	@Override
	public IncomeConfirmInfo findByProjectCode(String projectCode) {
		IncomeConfirmInfo info = new IncomeConfirmInfo();
		IncomeConfirmDO DO = incomeConfirmDAO.findByProjectCode(projectCode);
		BeanCopier.staticCopy(DO, info);
		//更新系统预估金额
		//updateIncomeConfirmDetail(DO.getProjectCode(), DO.getIncomeId());
		info.setIncomeConfirmStatus(IncomeConfirmStatusEnum.getByCode(DO.getIncomeConfirmStatus()));
		info.setIncomeConfirmDetailInfos(findIncomeConfirmDetailById(DO.getIncomeId()));
		return info;
	}
	
	@Override
	public List<IncomeConfirmDetailInfo> findIncomeConfirmDetailById(long incomeId) {
		List<IncomeConfirmDetailDO> detailList = incomeConfirmDetailDAO.findByIncomeId(incomeId);
		List<IncomeConfirmDetailInfo> list = Lists.newArrayList();
		if (ListUtil.isNotEmpty(detailList)) {
			for (IncomeConfirmDetailDO DO : detailList) {
				IncomeConfirmDetailInfo info = new IncomeConfirmDetailInfo();
				BeanCopier.staticCopy(DO, info);
				info.setConfirmStatus(ConfirmStatusEnum.getByCode(DO.getConfirmStatus()));
				info.setIsConfirmed(BooleanEnum.getByCode(DO.getIsConfirmed()));
				list.add(info);
			}
		}
		return list;
	}
	
	@Override
	public List<ProjectChargeDetailInfo> queryFeeList(String projectCode) {
		List<ProjectChargeDetailInfo> listChargeDetail = Lists.newArrayList();
		ProjectChargeDetailQueryOrder chargeOrder = new ProjectChargeDetailQueryOrder();
		chargeOrder.setProjectCode(projectCode);
		QueryBaseBatchResult<ProjectChargeDetailInfo> chargeDetail = projectReportServiceClient
			.projectChargeDetail(chargeOrder);
		if (chargeDetail != null && ListUtil.isNotEmpty(chargeDetail.getPageList())) {
			listChargeDetail = chargeDetail.getPageList();
		}
		
		return listChargeDetail;
	}
	
	@Override
	public QueryBaseBatchResult<IncomeConfirmDetailInfo> queryDetail(	IncomeConfirmDetailQueryOrder order) {
		QueryBaseBatchResult<IncomeConfirmDetailInfo> batchResult = new QueryBaseBatchResult<IncomeConfirmDetailInfo>();
		try {
			IncomeConfirmDetailDO detailDO = new IncomeConfirmDetailDO();
			if (StringUtil.isBlank(order.getSortCol())) {
				order.setSortCol("income_period");
				order.setSortOrder("desc");
			}
			BeanCopier.staticCopy(order, detailDO);
			long totalSize = incomeConfirmDetailDAO.findByConditionCount(detailDO);
			PageComponent component = new PageComponent(order, totalSize);
			
			List<IncomeConfirmDetailDO> pageList = incomeConfirmDetailDAO.findByCondition(detailDO,
				component.getFirstRecord(), component.getPageSize(), order.getSortCol(),
				order.getSortOrder());
			
			List<IncomeConfirmDetailInfo> list = batchResult.getPageList();
			if (totalSize > 0) {
				for (IncomeConfirmDetailDO detail : pageList) {
					IncomeConfirmDetailInfo info = new IncomeConfirmDetailInfo();
					BeanCopier.staticCopy(detail, info);
					info.setConfirmStatus(ConfirmStatusEnum.getByCode(detail.getConfirmStatus()));
					info.setIsConfirmed(BooleanEnum.getByCode(detail.getIsConfirmed()));
					list.add(info);
				}
			}
			
			batchResult.initPageParam(component);
			batchResult.setPageList(list);
			batchResult.setSuccess(true);
			return batchResult;
		} catch (Exception e) {
			logger.error("查询收入确认表明细失败 {}", e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public FcsBaseResult updateIncomeConfirmDetail(String projectCode, long incomeId,
													String incomePeriod) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			IncomeConfirmDetailQueryOrder order = new IncomeConfirmDetailQueryOrder();
			order.setIncomeId(incomeId);
			order.setConfirmStatus(ConfirmStatusEnum.NO_CONFIRM.code());
			order.setIncomePeriod(incomePeriod);
			order.setPageSize(9999L);
			if (StringUtil.isBlank(order.getSortCol())) {
				order.setSortCol("income_period");
				order.setSortOrder("asc");
			}
			QueryBaseBatchResult<IncomeConfirmDetailInfo> batchResult = queryDetail(order);
			List<IncomeConfirmDetailInfo> listDetailInfo = batchResult.getPageList();
			//			List<FChargeNotificationFeeInfo> listFeeInfo = queryFeeList(projectCode);
			if (ListUtil.isNotEmpty(listDetailInfo)) {
				for (IncomeConfirmDetailInfo incomeConfirmDetailInfo : listDetailInfo) {
					IncomeConfirmDO DO = incomeConfirmDAO.findByProjectCode(projectCode);
					if (DO != null) {
						//当前期间
						IncomeConfirmDetailDO detailDO1 = incomeConfirmDetailDAO
							.findByIncomeIdAndIncomePeriod(DO.getIncomeId(),
								incomeConfirmDetailInfo.getIncomePeriod());
						
						Money systemEstimatedAmount = calculateNewSystemEstimatedAmount(
							projectCode, incomeConfirmDetailInfo.getIncomePeriod());
						//计算上个月
						if (incomeConfirmDetailInfo.getIncomePeriod() != null) {
							String incomePeriodToDate = incomeConfirmDetailInfo.getIncomePeriod()
								.replace("年", "-").replace("月", "-01");
							String lastMonth = DateUtil.dtSimpleFormat(DateUtil.getDateByMonth(
								DateUtil.strToDtSimpleFormat(incomePeriodToDate), -1));//上个月期间
							
							String lastMonthPeriod = lastMonth.replace("-01", "月")
								.replace("-", "年");
							
							IncomeConfirmDetailDO detailDO = incomeConfirmDetailDAO
								.findByIncomeIdAndIncomePeriod(DO.getIncomeId(), lastMonthPeriod);
							if (detailDO != null) {
								if (ConfirmStatusEnum.IS_CONFIRM.code().equals(
									detailDO.getConfirmStatus())) {
									if (!detailDO.getIncomeConfirmedAmount().equals(//确认金额与预估金额不符
										detailDO.getSystemEstimatedAmount())) {
										detailDO1.setSystemEstimatedAmount(detailDO
											.getIncomeConfirmedAmount().add(systemEstimatedAmount));
									}
								}
							}
							detailDO1.setSystemEstimatedAmount(systemEstimatedAmount);
							
						}
						incomeConfirmDetailDAO.update(detailDO1);
					}
					
				}
			}
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("更新收入确认表明细失败 {}", e.getMessage(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return result;
	}
	
	//计算上月的系统分摊金额
	//计算规则：
	//未填写收费期间的：所有的收费金额累计/授信期间（按月计算，年换算成12个月）；
	//填写了收费期间的，按收费期间计算；如收到担保费，收费期间为2016-10-10日到2017年-10-10，收费金额为120万；
	//收到其他费用12万，项目授信期间为2年，收费期间默认为项目开始时间到项目截止时间（项目开始时间为2016-10-10，项目结束时间为2018-10-10）；
	private Money calculateSystemEstimatedAmount(String projectCode, String incomePeriod) {
		
		Money systemEstimatedAmount = Money.zero();
		if (incomePeriod != null && !"".equals(incomePeriod)) {
			try {
				String incomePeriodToDate = incomePeriod.replace("年", "-").replace("月", "-01");
				List<ProjectChargeDetailInfo> listFeeInfo = queryFeeList(projectCode);
				if (listFeeInfo != null) {
					for (ProjectChargeDetailInfo feeInfo : listFeeInfo) {
						Money payAmount = Money.zero();
						
						payAmount = feeInfo.getChargeAmount();
						
						if (feeInfo.getFeeType() != null
							&& feeInfo.getFeeType() != FeeTypeEnum.GUARANTEE_DEPOSIT) {//存入保证金不计入
							//上个月的开始时间必须小于收费期间结束时间 或者 上个月的结束时间必须大于收费期间的开始时间
							String monthStart = incomePeriodToDate;//本月开始时间
							
							//						String monthEnd = DateUtil.dtSimpleFormat(new Date());//本月结束时间
							
							String monthEnd = DateUtil.dtSimpleFormat(DateUtil.getDateByMonth(
								DateUtil.strToDtSimpleFormat(incomePeriodToDate), 1));//本月结束时间
							
							Date feeEndTimeDate = feeInfo.getChargeEndTime();
							Date feeStartTimeDate = feeInfo.getChargeStartTime();
							if (feeEndTimeDate != null && feeStartTimeDate != null) {
								int monthStartFeeEndTime = DateUtil.calculateDecreaseDate(
									DateUtil.dtSimpleFormat(feeEndTimeDate), monthStart);
								int monthEndFeeStartTime = DateUtil.calculateDecreaseDate(
									DateUtil.dtSimpleFormat(feeStartTimeDate), monthEnd);
								
								int monthStartFeeStartTime = DateUtil.calculateDecreaseDate(
									DateUtil.dtSimpleFormat(feeStartTimeDate), monthStart);
								
								int monthEndFeeEndTime = DateUtil.calculateDecreaseDate(
									DateUtil.dtSimpleFormat(feeEndTimeDate), monthEnd);
								
								if ((monthStartFeeEndTime <= 0 && monthStartFeeEndTime >= -31)
									|| (monthEndFeeStartTime >= 0 && monthEndFeeStartTime <= 31)
									|| (monthStartFeeStartTime >= 0) && monthEndFeeEndTime <= 0) {//有收费期间
								
									String feeStartTime = DateUtil.dtSimpleFormat(feeStartTimeDate);
									String feeEndTime = DateUtil.dtSimpleFormat(feeEndTimeDate);
									//收费期间天数
									int day1 = DateUtil.calculateDecreaseDate(feeStartTime,
										feeEndTime) + 1;
									int day2 = DateUtil.calculateDecreaseDate(monthStart, monthEnd);
									if (monthEndFeeEndTime <= 0 && monthStartFeeStartTime <= 0) {
										day2 = DateUtil.calculateDecreaseDate(feeStartTime,
											monthEnd);
										//要是跨月的再减一天
										//										if (DateUtil.strToDtSimpleFormat(monthEnd).getMonth() != feeStartTimeDate
										//											.getMonth()) {
										//											day2 = day2 - 1;
										//										}
									}
									if (monthStartFeeEndTime <= 0 && monthEndFeeEndTime >= 0) {
										day2 = (-monthStartFeeEndTime) + 1;
										//要是跨月的再减一天
										//										if (DateUtil.strToDtSimpleFormat(monthStart).getMonth() != feeEndTimeDate
										//											.getMonth()) {
										//											day2 = day2 - 1;
										//										}
									}
									
									if (day1 <= 0) {
										day1 = 1;
									}
									if (monthStartFeeStartTime <= 0 && monthEndFeeEndTime >= 0) {
										day2 = day1;
									}
									if (day2 > 30) {
										day2 = 31;
									} else if (day2 <= 0) {
										day2 = 0;
									}
									
									systemEstimatedAmount = systemEstimatedAmount.add(payAmount
										.multiply(day2).divide(day1));
								}
							}
							if (feeEndTimeDate == null && feeStartTimeDate == null) {//无收费区间
							
								if (feeInfo.getChargeTime() != null) {
									
									String period = DateUtil
										.dtSimpleFormat(feeInfo.getChargeTime());
									if (period.contains(incomePeriod.replace("年", "-").replace("月",
										""))) {
										systemEstimatedAmount = systemEstimatedAmount
											.add(payAmount);
									}
									
								}
							}
						}
					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return systemEstimatedAmount;
	}
	
	private Money calculateNewSystemEstimatedAmount(String projectCode, String incomePeriod) {
		
		Money systemEstimatedAmount = Money.zero();
		if (incomePeriod != null && !"".equals(incomePeriod)) {
			try {
				
				String incomePeriodToDate = incomePeriod.replace("年", "-").replace("月", "-01");
				ProjectInfo projectInfo = projectServiceClient.queryByCode(projectCode, true);
				Date endDate = DateUtil.parse(incomePeriodToDate);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(endDate);
				int month = calendar.get(Calendar.MONTH);
				Money monthIncome = Money.zero();
				int yearDay = 365;
				while (month == calendar.get(Calendar.MONTH)) {
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					logger.info("收入计算到天调用开始 projectCode={},Time={},", projectCode,
						DateUtil.simpleDate(calendar.getTime()));
					AfterwardsProjectSummaryResult summaryResult = afterwardsProjectSummaryServiceClient
						.queryProjectSummaryInfoByCondition(calendar.getTime(), projectCode);
					
					logger.info("收入计算到天 projectCode={},Time={},Amount={}", projectCode,
						calendar.getTime(), summaryResult.getAmount());
					Money money = summaryResult.getAmount();
					if (ProjectUtil.isBond(projectInfo.getBusiType())) {
						if (projectInfo.getBondDetailInfo().getGuaranteeFeeType() == ChargeTypeEnum.AMOUNT) {
							Money moneyFee = new Money(new BigDecimal(projectInfo
								.getBondDetailInfo().getGuaranteeFee()));
							monthIncome.addTo(moneyFee.divide(new BigDecimal(yearDay)));
						} else if (projectInfo.getBondDetailInfo().getGuaranteeFeeType() == ChargeTypeEnum.PERCENT) {
							monthIncome.addTo(money.multiply(
								projectInfo.getBondDetailInfo().getGuaranteeFee() * (0.01)).divide(
								new BigDecimal(yearDay)));
						}
					}
					if (ProjectUtil.isEntrusted(projectInfo.getBusiType())) {
						monthIncome.addTo(money.multiplyBy(
							new BigDecimal(
								projectInfo.getEntrustedDetailInfo().getInterestRate() * (0.01)))
							.divide(new BigDecimal(yearDay)));
					}
					if (ProjectUtil.isGuarantee(projectInfo.getBusiType())) {
						
						if (projectInfo.getGuaranteeDetailInfo().getGuaranteeFeeType() == ChargeTypeEnum.AMOUNT) {
							Money moneyFee = new Money(new BigDecimal(projectInfo
								.getGuaranteeDetailInfo().getGuaranteeFee()));
							monthIncome.addTo(moneyFee.divide(new BigDecimal(yearDay)));
						} else if (projectInfo.getGuaranteeDetailInfo().getGuaranteeFeeType() == ChargeTypeEnum.PERCENT) {
							monthIncome.addTo(money.multiply(
								projectInfo.getGuaranteeDetailInfo().getGuaranteeFee() * (0.01))
								.divide(new BigDecimal(yearDay)));
						}
					}
				}
				return monthIncome;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return systemEstimatedAmount;
	}
	
	@Override
	public FcsBaseResult batchConfirm(final IncomeBatchConfirmOrder order) {
		return commonProcess(order, "批量确认收入", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				//按照确认单ID分组
				Map<Long, List<IncomeBatchConfirmDetailOrder>> incomeMap = Maps.newHashMap();
				for (IncomeBatchConfirmDetailOrder detailOrder : order.getConfirmOrder()) {
					if (detailOrder.getIncomeId() <= 0 || detailOrder.getDetailId() <= 0) {
						continue;
					}
					if (!StringUtil.equals(detailOrder.getIsConfirmed(), "IS")) {//不确认
						detailOrder.setIncomeConfirmedAmount(Money.zero());
						detailOrder.setIsConfirmed("NO");
					}
					List<IncomeBatchConfirmDetailOrder> details = incomeMap.get(detailOrder
						.getIncomeId());
					if (details == null) {
						details = Lists.newArrayList();
						incomeMap.put(detailOrder.getIncomeId(), details);
					}
					details.add(detailOrder);
				}
				
				String message = "";
				
				for (long incomeId : incomeMap.keySet()) {
					
					IncomeConfirmDO confirmDO = incomeConfirmDAO.findById(incomeId);
					if (confirmDO == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"收入确认单不存在");
					}
					
					//所有确认明细
					List<IncomeConfirmDetailDO> detialList = incomeConfirmDetailDAO
						.findByIncomeId(incomeId);
					if (detialList == null || detialList.size() == 0) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"收入确认明细不存在");
					}
					
					List<IncomeConfirmDetailDO> updateList = Lists.newArrayList();
					Money confirmedAmount = Money.zero(); //所有已确认金额
					Money lastMonthAmount = Money.zero(); //本月确认金额
					for (IncomeConfirmDetailDO detailDO : detialList) {
						for (IncomeBatchConfirmDetailOrder detailOrder : incomeMap.get(incomeId)) {
							if (detailDO.getId() == detailOrder.getDetailId()) {
								
								//检查是否有改动(确认金额、确认状态)
								if (!detailDO.getIncomeConfirmedAmount().equals(
									detailOrder.getIncomeConfirmedAmount())
									|| !StringUtil.equals(detailDO.getIsConfirmed(),
										detailOrder.getIsConfirmed())) {
									detailDO.setIncomeConfirmedAmount(detailOrder
										.getIncomeConfirmedAmount());
									detailDO.setIsConfirmed(detailOrder.getIsConfirmed());
									detailDO.setConfirmStatus(StringUtil.equals(
										detailOrder.getIsConfirmed(), "IS") ? ConfirmStatusEnum.IS_CONFIRM
										.code() : ConfirmStatusEnum.NO_CONFIRM.code());
									updateList.add(detailDO);
								}
							}
						}
						
						//已确认金额
						if (StringUtil.equals(detailDO.getIsConfirmed(), "IS")) {
							confirmedAmount.addTo(detailDO.getIncomeConfirmedAmount());
						}
						
						//上月月确认金额
						String day = DateUtil.dtSimpleChineseFormat(DateUtil.getDateByMonth(
							new Date(), -1));
						if (day.contains(detailDO.getIncomePeriod())) {
							lastMonthAmount = detailDO.getIncomeConfirmedAmount();
						}
					}
					
					//没有任何改动
					if (ListUtil.isEmpty(updateList))
						continue;
					
					if (confirmedAmount.greaterThan(confirmDO.getChargedAmount())) {
						message += "[ 处理失败 ]确认总金额（" + confirmedAmount.toStandardString()
									+ "）超过收费总金额，" + confirmDO.getProjectCode() + "<br>";
						continue;
					}
					
					confirmDO.setThisMonthConfirmedIncomeAmount(lastMonthAmount);
					confirmDO.setConfirmedIncomeAmount(confirmedAmount);
					confirmDO.setNotConfirmedIncomeAmount(confirmDO.getChargedAmount().subtract(
						confirmedAmount));
					
					//更新主表
					incomeConfirmDAO.update(confirmDO);
					
					//更新明细
					for (IncomeConfirmDetailDO detailDO : updateList) {
						incomeConfirmDetailDAO.update(detailDO);
					}
					message += "[ 处理成功 ]，" + confirmDO.getProjectCode() + "<br>";
				}
				
				FcsBaseResult result = (FcsBaseResult) FcsFmDomainHolder.get().getAttribute(
					"result");
				if (StringUtil.isNotBlank(message)) {
					result.setMessage(message);
				} else {
					result.setSuccess(false);
					result.setMessage("数据未改变");
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public void execute(Object[] objects) {
		if (objects != null && objects.length > 0) {
			IncomeConfirmDO incomeConfirm = (IncomeConfirmDO) objects[0];
			logger.info("该项目projectCode进行收入确认=={} ", incomeConfirm.getProjectCode());
			String incomePeriod = DateUtil.dtSimpleChineseFormat(DateUtil.getDateByMonth(
				new Date(), -1));//获取上个月
			incomePeriod = incomePeriod.substring(0, incomePeriod.indexOf("月") + 1);
			updateIncomeConfirmDetail(incomeConfirm.getProjectCode(), incomeConfirm.getIncomeId(),
				incomePeriod);
			
		}
	}
}
