/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午2:12:59 创建
 */
package com.born.fcs.fm.biz.service.forecast;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.fm.biz.exception.ExceptionFactory;
import com.born.fcs.fm.biz.job.AccountAmountDetailJob;
import com.born.fcs.fm.biz.job.AsynchronousTaskJob;
import com.born.fcs.fm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.fm.dal.dataobject.AccountAmountDetailDO;
import com.born.fcs.fm.dal.dataobject.ForecastAccountChangeDetailDO;
import com.born.fcs.fm.dal.dataobject.ForecastAccountDO;
import com.born.fcs.fm.dal.dataobject.SysForecastParamDO;
import com.born.fcs.fm.domain.context.FcsFmDomainHolder;
import com.born.fcs.fm.integration.pm.service.PmReportServiceClient;
import com.born.fcs.fm.ws.enums.ForecastChildTypeOneEnum;
import com.born.fcs.fm.ws.enums.ForecastChildTypeTwoEnum;
import com.born.fcs.fm.ws.enums.ForecastFeeTypeEnum;
import com.born.fcs.fm.ws.enums.ForecastTypeEnum;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.info.forecast.AccountAmountDetailInfo;
import com.born.fcs.fm.ws.info.forecast.ForecastAccountChangeDetailInfo;
import com.born.fcs.fm.ws.info.forecast.ForecastAccountInfo;
import com.born.fcs.fm.ws.info.forecast.SysForecastParamAllInfo;
import com.born.fcs.fm.ws.info.forecast.SysForecastParamInfo;
import com.born.fcs.fm.ws.order.forecast.AccountAmountDetailQueryOrder;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountChangeOrder;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountOrder;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountQueryOrder;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountUpdateOrder;
import com.born.fcs.fm.ws.order.forecast.SysForecastParamBatchOrder;
import com.born.fcs.fm.ws.order.forecast.SysForecastParamOrder;
import com.born.fcs.fm.ws.order.forecast.SysForecastParamQueryOrder;
import com.born.fcs.fm.ws.result.forecast.AccountAmountDetailMainIndexResult;
import com.born.fcs.fm.ws.result.forecast.ForecastAccountResult;
import com.born.fcs.fm.ws.result.forecast.SysForecastParamResult;
import com.born.fcs.fm.ws.service.forecast.ForecastService;
import com.born.fcs.pm.biz.service.common.AsynchronousService;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.field.DataTypeEnum;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * 
 * 预测配置
 * @author hjiajie
 * 
 */
@Service("forecastService")
public class ForecastServiceImpl extends BaseAutowiredDomainService implements ForecastService,
																	AsynchronousService {
	
	private static SysForecastParamAllInfo info = null;
	@Autowired
	private AsynchronousTaskJob asynchronousTaskJob;
	
	@Autowired
	private AccountAmountDetailJob accountAmountDetailJob;
	@Autowired
	private PmReportServiceClient pmReportServiceClient;
	
	@Value("${pm.database.title}")
	private String dataPmTitle;
	
	@Value("${fm.database.title}")
	private String dataFmTitle;
	
	/**
	 * @param order
	 * @return
	 * @see com.born.fcs.fm.ws.service.forecast.ForecastService#queryBankMessageInfo(com.born.fcs.fm.ws.order.forecast.SysForecastParamQueryOrder)
	 */
	@Override
	public QueryBaseBatchResult<SysForecastParamInfo> queryBankMessageInfo(	SysForecastParamQueryOrder order) {
		logger.info("进入ForecastServiceImpl的queryBankMessageInfo方法，入参：{}", order);
		QueryBaseBatchResult<SysForecastParamInfo> result = new QueryBaseBatchResult<SysForecastParamInfo>();
		try {
			SysForecastParamDO queryDO = new SysForecastParamDO();
			BeanCopier.staticCopy(order, queryDO);
			if (order.getFundDirection() != null) {
				queryDO.setFundDirection(order.getFundDirection().code());
			}
			if (order.getForecastType() != null) {
				queryDO.setForecastType(order.getForecastType().code());
			}
			long totalCount = 0;
			// 查询totalcount
			totalCount = sysForecastParamDAO.findByConditionCount(queryDO, null);
			
			PageComponent component = new PageComponent(order, totalCount);
			// 查询list
			List<SysForecastParamDO> list = Lists.newArrayList();
			list = sysForecastParamDAO.findByCondition(queryDO, component.getFirstRecord(),
				component.getPageSize(), order.getSortCol(), order.getSortOrder());
			
			List<SysForecastParamInfo> pageList = new ArrayList<SysForecastParamInfo>();
			for (SysForecastParamDO item : list) {
				SysForecastParamInfo info = new SysForecastParamInfo();
				convertDO2Info(item, info);
				pageList.add(info);
				
			}
			result.setSuccess(true);
			result.setPageList(pageList);
			result.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询预测配置信息失败" + e.getMessage(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return result;
		
	}
	
	private void convertDO2Info(SysForecastParamDO item, SysForecastParamInfo info) {
		MiscUtil.copyPoObject(info, item);
		info.setFundDirection(FundDirectionEnum.getByCode(item.getFundDirection()));
		info.setForecastType(ForecastTypeEnum.getByCode(item.getForecastType()));
		info.setForecastTimeType(TimeUnitEnum.getByCode(item.getForecastTimeType()));
		info.setForecastOtherTimeType(TimeUnitEnum.getByCode(item.getForecastOtherTimeType()));
	}
	
	/**
	 * @return
	 * @see com.born.fcs.fm.ws.service.forecast.ForecastService#findAll()
	 */
	@Override
	public SysForecastParamResult findAll() {
		logger.info("进入ForecastServiceImpl的findAll方法");
		SysForecastParamResult result = new SysForecastParamResult();
		try {
			List<SysForecastParamDO> paramDOs = sysForecastParamDAO.findAll();
			if (info == null) {
				info = new SysForecastParamAllInfo();
				for (SysForecastParamDO paramDO : paramDOs) {
					ForecastTypeEnum _enum = ForecastTypeEnum.getByCode(paramDO.getForecastType());
					if (_enum != null) {
						/** 预测时间 */
						if (StringUtil.isNotBlank(_enum.getForecastTime())) {
							PropertyUtils.setProperty(info, _enum.getForecastTime(),
								paramDO.getForecastTime());
						}
						/** 预测时间单位 */
						if (StringUtil.isNotBlank(_enum.getForecastTimeType())) {
							PropertyUtils.setProperty(info, _enum.getForecastTimeType(),
								TimeUnitEnum.getByCode(paramDO.getForecastTimeType()));
						}
						/** 额外预测时间 */
						if (StringUtil.isNotBlank(_enum.getForecastOtherTime())) {
							PropertyUtils.setProperty(info, _enum.getForecastOtherTime(),
								paramDO.getForecastOtherTime());
						}
						/** 额外预测时间单位 */
						if (StringUtil.isNotBlank(_enum.getForecastOtherTimeType())) {
							PropertyUtils.setProperty(info, _enum.getForecastOtherTimeType(),
								TimeUnitEnum.getByCode(paramDO.getForecastOtherTimeType()));
						}
					}
				}
			}
			result.setSuccess(true);
			result.setParamAllInfo(info);
		} catch (Exception e) {
			logger.error("查询预测配置所有信息失败" + e.getMessage(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return result;
	}
	
	//	/**
	//	 * 测试反射调用
	//	 * @param args
	//	 * @throws Exception
	//	 */
	//	public static void main(String[] args) throws Exception {
	//		SysForecastParamInfo info = new SysForecastParamInfo();
	//		System.out.println(info);
	//		PropertyUtils.setProperty(info, "forecastTime", "134");
	//		System.out.println(info);
	//	}
	
	/**
	 * @param order
	 * @return
	 * @see com.born.fcs.fm.ws.service.forecast.ForecastService#modifyAll(com.born.fcs.fm.ws.order.forecast.SysForecastParamBatchOrder)
	 */
	@Override
	public FcsBaseResult modifyAll(SysForecastParamBatchOrder order) {
		logger.info("进入ForecastServiceImpl的modifyAll方法，入参：{}", order);
		FcsBaseResult result = new FcsBaseResult();
		try {
			if (order == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "入参不能为空！");
			}
			for (SysForecastParamOrder paramOrder : order.getParamOrders()) {
				if (paramOrder.getFundDirection() == null) {
					throw ExceptionFactory
						.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "流动方向不能为空！");
				}
				if (paramOrder.getForecastType() == null) {
					throw ExceptionFactory
						.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "预测类型不能为空！");
				}
				SysForecastParamDO paramDO = sysForecastParamDAO
					.findByFundDirectionAndForecastType(paramOrder.getFundDirection().code(),
						paramOrder.getForecastType().code());
				if (paramDO == null) {
					
					paramDO = new SysForecastParamDO();
					convertOrder2DO(paramOrder, paramDO);
					sysForecastParamDAO.insert(paramDO);
				} else {
					convertOrder2DO(paramOrder, paramDO);
					sysForecastParamDAO.update(paramDO);
				}
				//  清空缓存
				info = null;
				result.setSuccess(true);
			}
			
		} catch (Exception e) {
			logger.error("查询预测配置所有信息失败" + e.getMessage(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return result;
	}
	
	private void convertOrder2DO(SysForecastParamOrder paramOrder, SysForecastParamDO paramDO) {
		MiscUtil.copyPoObject(paramDO, paramOrder);
		if (paramOrder.getFundDirection() != null) {
			paramDO.setFundDirection(paramOrder.getFundDirection().code());
		}
		if (paramOrder.getForecastType() != null) {
			paramDO.setForecastType(paramOrder.getForecastType().code());
		}
		if (paramOrder.getForecastTimeType() != null) {
			paramDO.setForecastTimeType(paramOrder.getForecastTimeType().code());
		}
		if (paramOrder.getForecastOtherTimeType() != null) {
			paramDO.setForecastOtherTimeType(paramOrder.getForecastOtherTimeType().code());
		}
	}
	
	@Override
	public ForecastAccountResult add(final ForecastAccountOrder order) {
		logger.info("进入ForecastServiceImpl的add方法，入参：{}", order);
		final AsynchronousService asy = this;
		ForecastAccountResult result = new ForecastAccountResult();
		final Date nowDate = getSysdate();
		try {
			
			checkOrder(order);
			result = transactionTemplate.execute(new TransactionCallback<ForecastAccountResult>() {
				
				@Override
				public ForecastAccountResult doInTransaction(TransactionStatus status) {
					ForecastAccountResult result = new ForecastAccountResult();
					checkOrder(order);
					if (order == null) {
						result.setSuccess(false);
						result.setMessage("order不能为空！");
					}
					if (order.getSystemForm() == null) {
						result.setSuccess(false);
						result.setMessage("来源项目不能为空！");
					}
					if (StringUtil.isEmpty(order.getOrderNo())) {
						result.setSuccess(false);
						result.setMessage("orderNo不能为空！");
					}
					
					// 查询是否存在
					ForecastAccountDO forecastAccountDO = forecastAccountDAO
						.findBysyStemFormAndOrderNo(order.getSystemForm().code(),
							order.getOrderNo());
					// 更新或插入
					if (forecastAccountDO == null) {
						forecastAccountDO = new ForecastAccountDO();
						convertOrder2DO(order, forecastAccountDO);
						forecastAccountDO.setLastUpdateTime(nowDate);
						forecastAccountDO.setRawAddTime(nowDate);
						forecastAccountDO.setUpdateFrom("系统预测");
						forecastAccountDAO.insert(forecastAccountDO);
						logger.info("插入完成！");
					} else {
						
						//记录一下
						ForecastAccountChangeDetailDO changeDetail = new ForecastAccountChangeDetailDO();
						changeDetail.setForecastId(forecastAccountDO.getId());
						changeDetail.setIsManual(BooleanEnum.NO.code());
						changeDetail.setUserId(order.getUserId() == null ? 0 : order.getUserId());
						changeDetail.setUserAccount(order.getUserAccount());
						changeDetail.setUserName(order.getUserName());
						changeDetail.setForecastMemo("系统预测");
						changeDetail.setRawAddTime(nowDate);
						changeDetail.setOriginalAmount(Money.cent(forecastAccountDO.getAmount()
							.getCent()));
						changeDetail.setOrignalDate(forecastAccountDO.getForecastStartTime());
						
						convertOrder2DO(order, forecastAccountDO);
						forecastAccountDO.setLastUpdateTime(nowDate);
						forecastAccountDO.setUpdateFrom("系统预测");
						forecastAccountDAO.update(forecastAccountDO);
						
						changeDetail.setNewDate(forecastAccountDO.getForecastStartTime());
						changeDetail.setAmount(forecastAccountDO.getAmount());
						changeDetail.setOccurAmount(changeDetail.getAmount().subtract(
							changeDetail.getOriginalAmount()));
						if (changeDetail.getAmount().equals(changeDetail.getOriginalAmount())
							&& changeDetail.getOrignalDate().compareTo(changeDetail.getNewDate()) == 0) {
							//没有任何变化doNothing
						} else {
							forecastAccountChangeDetailDAO.insert(changeDetail);
						}
						
						logger.info("修改完成！");
					}
					
					// 触发异步计算
					asynchronousTaskJob.addAsynchronousService(asy, new Object[1]);
					
					result.setSuccess(true);
					
					return result;
				}
			});
		} catch (Exception ex) {
			setUnknownException(result, ex);
			logger.error("保存预测信息失败:" + ex.getMessage());
		} catch (Throwable e) {
			setUnknownException(result, e);
			logger.error("保存预测信息失败:" + e.getMessage());
		}
		return result;
	}
	
	@Override
	public FcsBaseResult change(final ForecastAccountChangeOrder order) {
		logger.info("进入ForecastServiceImpl的change方法，入参：{}", order);
		final AsynchronousService asy = this;
		FcsBaseResult result = new FcsBaseResult();
		final Date nowDate = getSysdate();
		try {
			
			checkOrder(order);
			
			result = transactionTemplate.execute(new TransactionCallback<ForecastAccountResult>() {
				
				@Override
				public ForecastAccountResult doInTransaction(TransactionStatus status) {
					ForecastAccountResult result = new ForecastAccountResult();
					
					Money occurAmount = order.getAmount();
					if (occurAmount.greaterThan(ZERO_MONEY)) {//有发生
					
						List<ForecastAccountDO> futureList = forecastAccountDAO
							.findFutureByProjectCodeAndFeeType(order.getProjectCode(), order
								.getFeeType().code());
						if (ListUtil.isNotEmpty(futureList)) {
							
							ForecastAccountChangeDetailDO changeDetail = new ForecastAccountChangeDetailDO();
							changeDetail.setIsManual(BooleanEnum.NO.code());
							changeDetail.setUserId(order.getUserId() == null ? 0 : order
								.getUserId());
							changeDetail.setUserAccount(order.getUserAccount());
							changeDetail.setUserName(order.getUserName());
							changeDetail.setForecastMemo(order.getForecastMemo());
							changeDetail.setRawAddTime(nowDate);
							
							for (int i = 0; i < futureList.size(); i++) {
								
								changeDetail.setId(0);
								
								if (ZERO_MONEY.greaterThan(occurAmount)) //已经扣完了
									break;
								
								ForecastAccountDO forecast = futureList.get(i);
								//记录日志
								changeDetail.setForecastId(forecast.getId());
								changeDetail.setOrignalDate(forecast.getForecastStartTime());
								changeDetail.setNewDate(forecast.getForecastStartTime());//不修改时间
								changeDetail.setOriginalAmount(Money.cent(forecast.getAmount()
									.getCent()));
								
								if (i == (futureList.size() - 1)) {//最后一个
									forecast.getAmount().subtractFrom(occurAmount);//扣减发生额
									changeDetail.setAmount(forecast.getAmount());
									changeDetail.setOccurAmount(changeDetail.getAmount().subtract(
										changeDetail.getOriginalAmount()));
									
								} else {//不是最后一个
								
									//预测已经扣完了不管
									if (ZERO_MONEY.greaterThan(forecast.getAmount()))
										continue;
									
									//如果发生额大于预测额
									if (occurAmount.greaterThan(forecast.getAmount())) {
										occurAmount.subtractFrom(forecast.getAmount());//全部扣掉
										forecast.setAmount(Money.zero());
										changeDetail.setAmount(forecast.getAmount());
										changeDetail.setOccurAmount(changeDetail.getAmount()
											.subtract(changeDetail.getOriginalAmount()));
									} else {//够扣
										occurAmount.subtractFrom(forecast.getAmount());//扣成负了上面直接break掉了
										forecast.getAmount().subtractFrom(occurAmount);
										changeDetail.setAmount(forecast.getAmount());
										changeDetail.setOccurAmount(changeDetail.getAmount()
											.subtract(changeDetail.getOriginalAmount()));
									}
								}
								forecastAccountDAO.update(forecast);
								if (changeDetail.getForecastId() > 0)
									forecastAccountChangeDetailDAO.insert(changeDetail);
							}
							// 触发异步计算
							asynchronousTaskJob.addAsynchronousService(asy, new Object[1]);
						}
					}
					
					result.setSuccess(true);
					result.setMessage("预测转现实成功");
					
					return result;
				}
			});
		} catch (Exception ex) {
			setUnknownException(result, ex);
			logger.error("预测转现实失败:" + ex.getMessage());
		} catch (Throwable e) {
			setUnknownException(result, e);
			logger.error("预测转现实失败:" + e.getMessage());
		}
		return result;
	}
	
	private void convertOrder2DO(ForecastAccountOrder order, ForecastAccountDO forecastAccountDO) {
		MiscUtil.copyPoObject(forecastAccountDO, order);
		
		if (order.getSystemForm() != null) {
			forecastAccountDO.setSystemForm(order.getSystemForm().code());
		}
		if (order.getFeeType() != null) {
			forecastAccountDO.setFeeType(order.getFeeType().code());
		}
		if (order.getFundDirection() != null) {
			forecastAccountDO.setFundDirection(order.getFundDirection().code());
		}
		if (order.getForecastType() != null) {
			forecastAccountDO.setForecastType(order.getForecastType().code());
		}
		if (order.getForecastChildTypeOne() != null) {
			forecastAccountDO.setForecastChildTypeOne(order.getForecastChildTypeOne().code());
		}
		if (order.getForecastChildTypeTwo() != null) {
			forecastAccountDO.setForecastChildTypeTwo(order.getForecastChildTypeTwo().code());
		}
		
	}
	
	private void convertDO2Info(ForecastAccountDO item, ForecastAccountInfo info) {
		MiscUtil.copyPoObject(info, item);
		info.setSystemForm(SystemEnum.getByCode(item.getSystemForm()));
		info.setFundDirection(FundDirectionEnum.getByCode(item.getFundDirection()));
		info.setForecastType(ForecastTypeEnum.getByCode(item.getForecastType()));
		info.setForecastChildTypeOne(ForecastChildTypeOneEnum.getByCode(item
			.getForecastChildTypeOne()));
		info.setForecastChildTypeTwo(ForecastChildTypeTwoEnum.getByCode(item
			.getForecastChildTypeTwo()));
		info.setFeeType(ForecastFeeTypeEnum.getByCode(item.getFeeType()));
	}
	
	@Override
	public QueryBaseBatchResult<ForecastAccountInfo> queryForecastAccountInfo(	final ForecastAccountQueryOrder order) {
		logger.info("进入ForecastServiceImpl的queryBankMessageInfo方法，入参：{}", order);
		QueryBaseBatchResult<ForecastAccountInfo> result = new QueryBaseBatchResult<ForecastAccountInfo>();
		try {
			ForecastAccountDO queryDO = new ForecastAccountDO();
			BeanCopier.staticCopy(order, queryDO);
			if (order.getSystemForm() != null) {
				queryDO.setSystemForm(order.getSystemForm().code());
			}
			if (order.getFundDirection() != null) {
				queryDO.setFundDirection(order.getFundDirection().code());
			}
			if (order.getForecastType() != null) {
				queryDO.setForecastType(order.getForecastType().code());
			}
			// 预计开始时间计算
			Date now = getSysdate();
			Date timeStart = order.getForecastTimeStart();
			if (timeStart != null) {
				// 判断预计开始时间不能比今日早
				Calendar calNow = Calendar.getInstance();
				calNow.setTime(now);
				Calendar orderTime = Calendar.getInstance();
				orderTime.setTime(timeStart);
				if (orderTime.getTimeInMillis() < calNow.getTimeInMillis()) {
					// 如果入参时间比当前时间小，就让当前时间替换入参时间
					timeStart = calNow.getTime();
					timeStart = now;
				} else {
					timeStart = DateUtil.getStartTimeOfTheDate(timeStart);
				}
			} else {
				timeStart = DateUtil.getStartTimeOfTheDate(now);
				//timeStart = now;
			}
			Date timeEnd = order.getForecastTimeEnd();
			if (timeEnd != null) {
				timeEnd = DateUtil.getEndTimeOfTheDate(timeEnd);
			}
			long totalCount = 0;
			// 查询totalcount
			totalCount = forecastAccountDAO.findByConditionCount(queryDO, timeStart, timeEnd,
				order.getLoginUserId(), order.getDeptIdList(), order.getRelatedRoleList(),
				dataPmTitle);
			
			PageComponent component = new PageComponent(order, totalCount);
			// 查询list
			List<ForecastAccountDO> list = Lists.newArrayList();
			list = forecastAccountDAO.findByCondition(queryDO, timeStart, timeEnd,
				order.getLoginUserId(), order.getDeptIdList(), order.getRelatedRoleList(),
				component.getFirstRecord(), component.getPageSize(), order.getSortCol(),
				order.getSortOrder(), dataPmTitle);
			
			List<ForecastAccountInfo> pageList = new ArrayList<ForecastAccountInfo>();
			for (ForecastAccountDO item : list) {
				ForecastAccountInfo info = new ForecastAccountInfo();
				convertDO2Info(item, info);
				//查询修改明细
				List<ForecastAccountChangeDetailDO> changeDetailDOs = forecastAccountChangeDetailDAO
					.findByForecastId(info.getId());
				if (changeDetailDOs != null && changeDetailDOs.size() > 0) {
					List<ForecastAccountChangeDetailInfo> changeDetail = new ArrayList<ForecastAccountChangeDetailInfo>(
						changeDetailDOs.size());
					for (ForecastAccountChangeDetailDO detailDO : changeDetailDOs) {
						ForecastAccountChangeDetailInfo detailInfo = new ForecastAccountChangeDetailInfo();
						BeanCopier.staticCopy(detailDO, detailInfo);
						detailInfo.setIsManual(BooleanEnum.getByCode(detailDO.getIsManual()));
						changeDetail.add(detailInfo);
					}
					info.setChangeDetail(changeDetail);
				}
				pageList.add(info);
				
			}
			result.setSuccess(true);
			result.setPageList(pageList);
			result.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询预测信息失败" + e.getMessage(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult modifyForecastAccount(final ForecastAccountUpdateOrder order) {
		return commonProcess(order, "修改预测信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				Date now = FcsFmDomainHolder.get().getSysDate();
				if (order.getId() <= 0) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.INCOMPLETE_REQ_PARAM,
						"入参id不能为空！");
				}
				
				// 查询是否存在
				ForecastAccountDO forecastAccountDO = forecastAccountDAO.findById(order.getId());
				if (forecastAccountDO == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "未查询到数据！");
				}
				
				//记录日志
				ForecastAccountChangeDetailDO changeDetail = new ForecastAccountChangeDetailDO();
				changeDetail.setForecastId(forecastAccountDO.getId());
				changeDetail.setIsManual(BooleanEnum.IS.code());
				changeDetail.setUserId(order.getUserId() == null ? 0 : order.getUserId());
				changeDetail.setUserAccount(order.getUserAccount());
				changeDetail.setUserName(order.getUserName());
				changeDetail.setForecastMemo("人为调整");
				changeDetail.setRawAddTime(now);
				
				//原来的情况
				changeDetail.setOriginalAmount(forecastAccountDO.getAmount());
				changeDetail.setOrignalDate(forecastAccountDO.getForecastStartTime());
				
				// 修改金额和时间
				forecastAccountDO.setAmount(order.getAmount());
				forecastAccountDO.setForecastStartTime(order.getForecastStartTime());
				
				forecastAccountDO.setLastUpdateTime(now);
				forecastAccountDO.setUpdateFrom(order.getUserName());
				
				changeDetail.setAmount(order.getAmount());
				changeDetail.setNewDate(forecastAccountDO.getForecastStartTime());
				changeDetail.setOccurAmount(changeDetail.getAmount().subtract(
					changeDetail.getOriginalAmount()));
				
				if (changeDetail.getAmount().equals(changeDetail.getOriginalAmount())
					&& changeDetail.getOrignalDate().compareTo(changeDetail.getNewDate()) == 0) {
					//没有任何变化doNothing
				} else {
					forecastAccountDAO.update(forecastAccountDO);
					forecastAccountChangeDetailDAO.insert(changeDetail);
				}
				return null;
			}
			
		}, null, null);
	}
	
	@Override
	public FcsBaseResult delete(ForecastAccountOrder order) {
		logger.info("进入ForecastServiceImpl的delete方法，入参：{}", order);
		FcsBaseResult result = new FcsBaseResult();
		
		try {
			// 查询是否存在
			ForecastAccountDO forecastAccountDO = forecastAccountDAO.findBysyStemFormAndOrderNo(
				order.getSystemForm().code(), order.getOrderNo());
			
			if (forecastAccountDO == null) {
				result.setSuccess(false);
				result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
				result.setMessage("未查询到数据！");
				logger.info("未查询到数据！");
			} else {
				forecastAccountDAO.deleteById(forecastAccountDO.getId());
				result.setSuccess(true);
				result.setMessage("删除成功！");
				logger.info("删除成功，id=" + forecastAccountDO.getId());
			}
			result.setSuccess(true);
			// 触发异步计算
			asynchronousTaskJob.addAsynchronousService(this, new Object[1]);
		} catch (Exception e) {
			logger.error("查询预测信息失败" + e.getMessage(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return result;
		
	}
	
	@Override
	public QueryBaseBatchResult<AccountAmountDetailInfo> queryAccountAmountDetail(	AccountAmountDetailQueryOrder order) {
		logger.info("进入ForecastServiceImpl的queryAccountAmountDetail方法，入参：{}", order);
		QueryBaseBatchResult<AccountAmountDetailInfo> result = new QueryBaseBatchResult<AccountAmountDetailInfo>();
		try {
			AccountAmountDetailDO queryDO = new AccountAmountDetailDO();
			BeanCopier.staticCopy(order, queryDO);
			// 预计开始时间计算
			Date timeStart = order.getForecastTimeStart();
			if (timeStart != null) {
				timeStart = DateUtil.getStartTimeOfTheDate(timeStart);
			}
			Date timeEnd = order.getForecastTimeEnd();
			if (timeEnd != null) {
				timeEnd = DateUtil.getEndTimeOfTheDate(timeEnd);
			}
			long totalCount = 0;
			// 查询totalcount
			totalCount = accountAmountDetailDAO.findByConditionCount(queryDO, timeStart, timeEnd);
			
			PageComponent component = new PageComponent(order, totalCount);
			// 查询list
			List<AccountAmountDetailDO> list = Lists.newArrayList();
			list = accountAmountDetailDAO.findByCondition(queryDO, component.getFirstRecord(),
				component.getPageSize(), timeStart, timeEnd, order.getSortCol(),
				order.getSortOrder());
			
			List<AccountAmountDetailInfo> pageList = new ArrayList<AccountAmountDetailInfo>();
			for (AccountAmountDetailDO item : list) {
				AccountAmountDetailInfo info = new AccountAmountDetailInfo();
				convertDO2Info(item, info);
				pageList.add(info);
				
			}
			result.setSuccess(true);
			result.setPageList(pageList);
			result.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询资金余额明细信息失败" + e.getMessage(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return result;
	}
	
	private void convertDO2Info(AccountAmountDetailDO item, AccountAmountDetailInfo info) {
		MiscUtil.copyPoObject(info, item);
	}
	
	@Override
	public void execute(Object[] objects) {
		try {
			accountAmountDetailJob.doJob();
		} catch (Exception e) {
			logger.error("异步执行余额计算失败:" + e.getMessage(), e);
		}
	}
	
	@Override
	public AccountAmountDetailMainIndexResult queryMainIndex() {
		logger.info("进入ForecastServiceImpl的AccountAmountDetailMainIndexResult方法");
		AccountAmountDetailMainIndexResult result = new AccountAmountDetailMainIndexResult();
		List<AccountAmountDetailInfo> weekDetail = new ArrayList<AccountAmountDetailInfo>();
		
		List<AccountAmountDetailInfo> monthDetail = new ArrayList<AccountAmountDetailInfo>();
		
		List<AccountAmountDetailInfo> fourmohthDetail = new ArrayList<AccountAmountDetailInfo>();
		Date now = getSysdate();
		try {
			// 1.查询日数据
			AccountAmountDetailQueryOrder queryOrder = new AccountAmountDetailQueryOrder();
			queryOrder.setPageNumber(1);
			queryOrder.setPageSize(7);
			queryOrder.setForecastTimeStart(now);
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			cal.add(Calendar.DAY_OF_YEAR, 7);
			queryOrder.setForecastTimeEnd(cal.getTime());
			QueryBaseBatchResult<AccountAmountDetailInfo> accounts = queryAccountAmountDetail(queryOrder);
			// 执行预测
			if (ListUtil.isEmpty(accounts.getPageList())) {
				accountAmountDetailJob.doJob();
				accounts = queryAccountAmountDetail(queryOrder);
			}
			// 设置datestr
			for (AccountAmountDetailInfo info : accounts.getPageList()) {
				info.setDateStr(new SimpleDateFormat("MM/dd").format(info.getTime()));
			}
			
			result.setDayDetail(accounts.getPageList());
			// 2.查询周数据
			Date weekLastDay = null;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			int week = calendar.get(Calendar.DAY_OF_WEEK);
			// 1 代表周末，7代表周六
			if (week == 1) {
				// 若是周末，今日就是这周最后一天
				weekLastDay = now;
			} else {
				// 不是周末用8-X来计算差几天
				int wait = 8 - week;
				calendar.add(Calendar.DAY_OF_YEAR, wait);
				weekLastDay = calendar.getTime();
			}
			queryOrder.setPageSize(1);
			queryOrder.setForecastTimeStart(weekLastDay);
			queryOrder.setForecastTimeEnd(weekLastDay);
			QueryBaseBatchResult<AccountAmountDetailInfo> weekResult = queryAccountAmountDetail(queryOrder);
			AccountAmountDetailInfo week1 = new AccountAmountDetailInfo();
			if (weekResult.isSuccess() && weekResult.getTotalCount() > 0)
				week1 = weekResult.getPageList().get(0);
			
			calendar.add(Calendar.DAY_OF_YEAR, 7);
			weekLastDay = calendar.getTime();
			queryOrder.setForecastTimeStart(weekLastDay);
			queryOrder.setForecastTimeEnd(weekLastDay);
			weekResult = queryAccountAmountDetail(queryOrder);
			AccountAmountDetailInfo week2 = new AccountAmountDetailInfo();
			if (weekResult.isSuccess() && weekResult.getTotalCount() > 0)
				week2 = weekResult.getPageList().get(0);
			
			calendar.add(Calendar.DAY_OF_YEAR, 7);
			weekLastDay = calendar.getTime();
			queryOrder.setForecastTimeStart(weekLastDay);
			queryOrder.setForecastTimeEnd(weekLastDay);
			weekResult = queryAccountAmountDetail(queryOrder);
			AccountAmountDetailInfo week3 = new AccountAmountDetailInfo();
			if (weekResult.isSuccess() && weekResult.getTotalCount() > 0)
				week3 = weekResult.getPageList().get(0);
			
			calendar.add(Calendar.DAY_OF_YEAR, 7);
			weekLastDay = calendar.getTime();
			queryOrder.setForecastTimeStart(weekLastDay);
			queryOrder.setForecastTimeEnd(weekLastDay);
			weekResult = queryAccountAmountDetail(queryOrder);
			AccountAmountDetailInfo week4 = new AccountAmountDetailInfo();
			if (weekResult.isSuccess() && weekResult.getTotalCount() > 0)
				week4 = weekResult.getPageList().get(0);
			
			HashMap<String, FcsField> fieldMap = new HashMap<String, FcsField>();
			//			tring colName, String name, FcsFunctionEnum function, DataTypeEnum dataTypeEnum
			fieldMap.put("weeks", new FcsField("weeks", "周", null, DataTypeEnum.STRING));
			fieldMap.put("forecast_in_amount", new FcsField("forecast_in_amount", "流入金额", null,
				DataTypeEnum.MONEY));
			fieldMap.put("forecast_out_amount", new FcsField("forecast_out_amount", "流出金额", null,
				DataTypeEnum.MONEY));
			String sql = "SELECT DATE_FORMAT(TIME,'%Y%u') weeks,SUM(forecast_in_amount) forecast_in_amount,SUM(forecast_out_amount) forecast_out_amount  FROM "
							+ dataFmTitle
							+ ".account_amount_detail  WHERE TIME >= '"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtil
								.getStartTimeOfTheDate(now))
							+ "'  GROUP BY weeks order by weeks ASC";
			PmReportDOQueryOrder qOrder = new PmReportDOQueryOrder();
			qOrder.setSql(sql);
			qOrder.setLimitStart(0);
			qOrder.setPageSize(4);
			qOrder.setFieldMap(fieldMap);
			List<DataListItem> items = pmReportServiceClient.doQuery(qOrder);
			if (items != null) {
				for (int i = 1; i <= 4; i++) {
					DataListItem item = items.get(i - 1);
					if (i == 1) {
						//					week1.setDateStr((String) item.getMap().get("weeks"));
						week1.setDateStr("第一周");
						week1.setForecastInAmount((Money) item.getMap().get("forecast_in_amount"));
						week1
							.setForecastOutAmount((Money) item.getMap().get("forecast_out_amount"));
					} else if (i == 2) {
						//					week2.setDateStr((String) item.getMap().get("weeks"));
						week2.setDateStr("第二周");
						week2.setForecastInAmount((Money) item.getMap().get("forecast_in_amount"));
						week2
							.setForecastOutAmount((Money) item.getMap().get("forecast_out_amount"));
						
					} else if (i == 3) {
						//					week3.setDateStr((String) item.getMap().get("weeks"));
						week3.setDateStr("第三周");
						week3.setForecastInAmount((Money) item.getMap().get("forecast_in_amount"));
						week3
							.setForecastOutAmount((Money) item.getMap().get("forecast_out_amount"));
						
					} else if (i == 4) {
						//					week4.setDateStr((String) item.getMap().get("weeks"));
						week4.setDateStr("第四周");
						week4.setForecastInAmount((Money) item.getMap().get("forecast_in_amount"));
						week4
							.setForecastOutAmount((Money) item.getMap().get("forecast_out_amount"));
						
					}
				}
				
				weekDetail.add(week1);
				weekDetail.add(week2);
				weekDetail.add(week3);
				weekDetail.add(week4);
				result.setWeekDetail(weekDetail);
			}
			
			// 3.查询月数据
			calendar.setTime(now);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			//			// +5月
			//			calendar.add(Calendar.MONTH, 5);
			Date monthLastDay = calendar.getTime();
			queryOrder.setPageSize(1);
			queryOrder.setForecastTimeStart(monthLastDay);
			queryOrder.setForecastTimeEnd(monthLastDay);
			QueryBaseBatchResult<AccountAmountDetailInfo> monthResult = queryAccountAmountDetail(queryOrder);
			AccountAmountDetailInfo month1 = new AccountAmountDetailInfo();
			if (monthResult != null && ListUtil.isNotEmpty(monthResult.getPageList())) {
				month1 = monthResult.getPageList().get(0);
			}
			month1.setDateStr(DateUtil.simpleFormatYM(monthLastDay));
			
			calendar.add(Calendar.MONTH, 1);
			monthLastDay = calendar.getTime();
			queryOrder.setForecastTimeStart(monthLastDay);
			queryOrder.setForecastTimeEnd(monthLastDay);
			monthResult = queryAccountAmountDetail(queryOrder);
			AccountAmountDetailInfo month2 = new AccountAmountDetailInfo();
			if (monthResult != null && ListUtil.isNotEmpty(monthResult.getPageList())) {
				month2 = monthResult.getPageList().get(0);
			}
			month2.setDateStr(DateUtil.simpleFormatYM(monthLastDay));
			
			calendar.add(Calendar.MONTH, 1);
			monthLastDay = calendar.getTime();
			queryOrder.setForecastTimeStart(monthLastDay);
			queryOrder.setForecastTimeEnd(monthLastDay);
			monthResult = queryAccountAmountDetail(queryOrder);
			AccountAmountDetailInfo month3 = new AccountAmountDetailInfo();
			if (monthResult != null && ListUtil.isNotEmpty(monthResult.getPageList())) {
				month3 = monthResult.getPageList().get(0);
			}
			month3.setDateStr(DateUtil.simpleFormatYM(monthLastDay));
			
			calendar.add(Calendar.MONTH, 1);
			monthLastDay = calendar.getTime();
			queryOrder.setForecastTimeStart(monthLastDay);
			queryOrder.setForecastTimeEnd(monthLastDay);
			monthResult = queryAccountAmountDetail(queryOrder);
			AccountAmountDetailInfo month4 = new AccountAmountDetailInfo();
			if (monthResult != null && ListUtil.isNotEmpty(monthResult.getPageList())) {
				month4 = monthResult.getPageList().get(0);
			}
			month4.setDateStr(DateUtil.simpleFormatYM(monthLastDay));
			
			calendar.add(Calendar.MONTH, 1);
			monthLastDay = calendar.getTime();
			queryOrder.setForecastTimeStart(monthLastDay);
			queryOrder.setForecastTimeEnd(monthLastDay);
			monthResult = queryAccountAmountDetail(queryOrder);
			AccountAmountDetailInfo month5 = new AccountAmountDetailInfo();
			if (monthResult != null && ListUtil.isNotEmpty(monthResult.getPageList())) {
				month5 = monthResult.getPageList().get(0);
			}
			month5.setDateStr(DateUtil.simpleFormatYM(monthLastDay));
			
			calendar.add(Calendar.MONTH, 1);
			monthLastDay = calendar.getTime();
			queryOrder.setForecastTimeStart(monthLastDay);
			queryOrder.setForecastTimeEnd(monthLastDay);
			monthResult = queryAccountAmountDetail(queryOrder);
			AccountAmountDetailInfo month6 = new AccountAmountDetailInfo();
			if (monthResult != null && ListUtil.isNotEmpty(monthResult.getPageList())) {
				month6 = monthResult.getPageList().get(0);
			}
			month6.setDateStr(DateUtil.simpleFormatYM(monthLastDay));
			
			HashMap<String, FcsField> fieldMapMonth = new HashMap<String, FcsField>();
			fieldMapMonth.put("months", new FcsField("months", "月", null, DataTypeEnum.STRING));
			fieldMapMonth.put("forecast_in_amount", new FcsField("forecast_in_amount", "流入金额",
				null, DataTypeEnum.MONEY));
			fieldMapMonth.put("forecast_out_amount", new FcsField("forecast_out_amount", "流出金额",
				null, DataTypeEnum.MONEY));
			String sqlMonth = "SELECT DATE_FORMAT(TIME,'%Y%m') months,SUM(forecast_in_amount) forecast_in_amount,SUM(forecast_out_amount) forecast_out_amount  FROM "
								+ dataFmTitle
								+ ".account_amount_detail WHERE TIME >= '"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtil
									.getStartTimeOfTheDate(now))
								+ "' GROUP BY months order by months ASC";
			
			PmReportDOQueryOrder monthOrder = new PmReportDOQueryOrder();
			monthOrder.setSql(sqlMonth);
			monthOrder.setLimitStart(0);
			monthOrder.setPageSize(12);
			monthOrder.setFieldMap(fieldMapMonth);
			List<DataListItem> itemsMonth = pmReportServiceClient.doQuery(monthOrder);
			if (itemsMonth != null) {
				
				for (int i = 1; i <= 6; i++) {
					DataListItem item = itemsMonth.get(i - 1);
					if (i == 1) {
						month1.setForecastInAmount((Money) item.getMap().get("forecast_in_amount"));
						month1.setForecastOutAmount((Money) item.getMap()
							.get("forecast_out_amount"));
					} else if (i == 2) {
						month2.setForecastInAmount((Money) item.getMap().get("forecast_in_amount"));
						month2.setForecastOutAmount((Money) item.getMap()
							.get("forecast_out_amount"));
						
					} else if (i == 3) {
						month3.setForecastInAmount((Money) item.getMap().get("forecast_in_amount"));
						month3.setForecastOutAmount((Money) item.getMap()
							.get("forecast_out_amount"));
						
					} else if (i == 4) {
						month4.setForecastInAmount((Money) item.getMap().get("forecast_in_amount"));
						month4.setForecastOutAmount((Money) item.getMap()
							.get("forecast_out_amount"));
						
					} else if (i == 5) {
						month5.setForecastInAmount((Money) item.getMap().get("forecast_in_amount"));
						month5.setForecastOutAmount((Money) item.getMap()
							.get("forecast_out_amount"));
						
					} else if (i == 6) {
						month6.setForecastInAmount((Money) item.getMap().get("forecast_in_amount"));
						month6.setForecastOutAmount((Money) item.getMap()
							.get("forecast_out_amount"));
						
					}
				}
			}
			
			monthDetail.add(month1);
			monthDetail.add(month2);
			monthDetail.add(month3);
			monthDetail.add(month4);
			monthDetail.add(month5);
			monthDetail.add(month6);
			result.setMonthDetail(monthDetail);
			
			// 4. 查询季度数据 获取当月，到3的倍数停，然后计算总体
			calendar.setTime(now);
			
			// 结果是0-11 所以加一
			int todayMonth = calendar.get(Calendar.MONTH) + 1;
			int selectTodayMonth = calendar.get(Calendar.MONTH) + 1;
			int lastCount = todayMonth % 3;
			int selectCount = 0;
			// 获取季度最后一月的那天
			for (int i = 0; i <= 6; i++) {
				if (selectTodayMonth % 3 == 0) {
					break;
				}
				selectCount++;
				selectTodayMonth++;
			}
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			calendar.add(Calendar.MONTH, selectCount);
			Date fourMonthLastDay = calendar.getTime();
			queryOrder.setPageSize(1);
			queryOrder.setForecastTimeStart(fourMonthLastDay);
			queryOrder.setForecastTimeEnd(fourMonthLastDay);
			monthResult = queryAccountAmountDetail(queryOrder);
			AccountAmountDetailInfo fourMonth1 = new AccountAmountDetailInfo();
			if (monthResult != null && ListUtil.isNotEmpty(monthResult.getPageList())) {
				fourMonth1 = monthResult.getPageList().get(0);
			}
			fourMonth1.setDateStr(getFourMohthStr(selectTodayMonth));
			
			calendar.add(Calendar.MONTH, 3);
			fourMonthLastDay = calendar.getTime();
			queryOrder.setForecastTimeStart(fourMonthLastDay);
			queryOrder.setForecastTimeEnd(fourMonthLastDay);
			monthResult = queryAccountAmountDetail(queryOrder);
			AccountAmountDetailInfo fourMonth2 = new AccountAmountDetailInfo();
			if (monthResult != null && ListUtil.isNotEmpty(monthResult.getPageList())) {
				fourMonth2 = monthResult.getPageList().get(0);
			}
			fourMonth2.setDateStr(getFourMohthStr(selectTodayMonth));
			
			calendar.add(Calendar.MONTH, 3);
			fourMonthLastDay = calendar.getTime();
			queryOrder.setForecastTimeStart(fourMonthLastDay);
			queryOrder.setForecastTimeEnd(fourMonthLastDay);
			monthResult = queryAccountAmountDetail(queryOrder);
			AccountAmountDetailInfo fourMonth3 = new AccountAmountDetailInfo();
			if (monthResult != null && ListUtil.isNotEmpty(monthResult.getPageList())) {
				fourMonth3 = monthResult.getPageList().get(0);
			}
			fourMonth3.setDateStr(getFourMohthStr(selectTodayMonth));
			
			calendar.add(Calendar.MONTH, 3);
			fourMonthLastDay = calendar.getTime();
			queryOrder.setForecastTimeStart(fourMonthLastDay);
			queryOrder.setForecastTimeEnd(fourMonthLastDay);
			monthResult = queryAccountAmountDetail(queryOrder);
			AccountAmountDetailInfo fourMonth4 = new AccountAmountDetailInfo();
			if (monthResult != null && ListUtil.isNotEmpty(monthResult.getPageList())) {
				fourMonth4 = monthResult.getPageList().get(0);
			}
			fourMonth4.setDateStr(getFourMohthStr(selectTodayMonth));
			
			// 判定执行了多少次
			int count = 0;
			if (itemsMonth != null) {
				for (int i = 1; i <= 12; i++) {
					DataListItem item = itemsMonth.get(i - 1);
					if (count == 0) {
						fourMonth1.getForecastInAmount().addTo(
							((Money) item.getMap().get("forecast_in_amount")));
						fourMonth1.getForecastOutAmount().addTo(
							((Money) item.getMap().get("forecast_out_amount")));
						lastCount = todayMonth % 3;
						if (lastCount == 0) {
							count++;
						}
						todayMonth++;
					} else if (count == 1) {
						fourMonth2.getForecastInAmount().addTo(
							((Money) item.getMap().get("forecast_in_amount")));
						fourMonth2.getForecastOutAmount().addTo(
							((Money) item.getMap().get("forecast_out_amount")));
						lastCount = todayMonth % 3;
						if (lastCount == 0) {
							count++;
						}
						todayMonth++;
					} else if (count == 2) {
						fourMonth3.getForecastInAmount().addTo(
							((Money) item.getMap().get("forecast_in_amount")));
						fourMonth3.getForecastOutAmount().addTo(
							((Money) item.getMap().get("forecast_out_amount")));
						lastCount = todayMonth % 3;
						if (lastCount == 0) {
							count++;
						}
						todayMonth++;
					} else if (count == 3) {
						fourMonth4.getForecastInAmount().addTo(
							((Money) item.getMap().get("forecast_in_amount")));
						fourMonth4.getForecastOutAmount().addTo(
							((Money) item.getMap().get("forecast_out_amount")));
						lastCount = todayMonth % 3;
						if (lastCount == 0) {
							count++;
						}
						todayMonth++;
					} else {
						break;
					}
				}
			}
			
			fourmohthDetail.add(fourMonth1);
			fourmohthDetail.add(fourMonth2);
			fourmohthDetail.add(fourMonth3);
			fourmohthDetail.add(fourMonth4);
			result.setFourmohthDetail(fourmohthDetail);
			
			//////////////////////////////////// 添加主要资金构成情况
			/** 资金总额 */
			Money totalamount = Money.zero();
			
			/*** 可用资金总计【银行账户余额加总】 */
			Money bankAccountAmount = Money.zero();
			
			HashMap<String, FcsField> fieldMapBankAccountAmount = new HashMap<String, FcsField>();
			fieldMapBankAccountAmount.put("bankAccountAmount", new FcsField("bankAccountAmount",
				"统计金额", null, DataTypeEnum.MONEY));
			String sqlBankAccountAmount = "SELECT IFNULL(SUM(amount),0) bankAccountAmount FROM "
											+ dataFmTitle
											+ "."
											+ "bank_message  WHERE account_type = 'CURRENT' AND STATUS = 'NORMAL'";
			List<DataListItem> itemsBankAccountAmount = pmReportServiceClient.doQuery(
				sqlBankAccountAmount, 0, 0, fieldMapBankAccountAmount);
			if (itemsBankAccountAmount != null) {
				DataListItem item = itemsBankAccountAmount.get(0);
				bankAccountAmount = (Money) item.getMap().get("bankAccountAmount");
			}
			totalamount.addTo(bankAccountAmount);
			result.setBankAccountAmount(bankAccountAmount);
			
			/** 理财产品：持有的理财产品总额 */
			Money financialAmount = Money.zero();
			
			HashMap<String, FcsField> fieldMapFinancialAmount = new HashMap<String, FcsField>();
			fieldMapFinancialAmount.put("financialAmount", new FcsField("financialAmount", "统计金额",
				null, DataTypeEnum.BIGDECIMAL));
			String sqlFinancialAmount = "SELECT IFNULL(SUM(actual_price * original_hold_num),0)  AS  financialAmount  FROM "
										+ dataPmTitle
										+ ".project_financial  WHERE STATUS = 'PURCHASED'";
			List<DataListItem> itemsFinancialAmount = pmReportServiceClient.doQuery(
				sqlFinancialAmount, 0, 0, fieldMapFinancialAmount);
			DataListItem itemFinancialAmount = itemsFinancialAmount.get(0);
			financialAmount = new Money((BigDecimal) itemFinancialAmount.getMap().get(
				"financialAmount")).divide(100);
			totalamount.addTo(financialAmount);
			result.setFinancialAmount(financialAmount);
			
			/** 股权投资 */
			Money equityInvestmentAmount = Money.zero();
			
			HashMap<String, FcsField> fieldMapEquityInvestmentAmount = new HashMap<String, FcsField>();
			fieldMapEquityInvestmentAmount.put("equityInvestmentAmount", new FcsField(
				"equityInvestmentAmount", "统计金额", null, DataTypeEnum.MONEY));
			String sqlEquityInvestmentAmount = "SELECT IFNULL(CASE WHEN out_amount.amount < in_amount.amount THEN 0 ELSE out_amount.amount - in_amount.amount END,0) AS equityInvestmentAmount FROM (SELECT          IFNULL(SUM(fee.amount), 0) amount  FROM "
												+ dataFmTitle
												+ "."
												+ "form_payment p          JOIN "
												+ dataFmTitle
												+ "."
												+ "form f          ON p.form_id = f.form_id  AND f.status = 'APPROVAL'  JOIN "
												+ dataFmTitle
												+ "."
												+ "form_payment_fee fee     ON p.form_id = fee.form_id   AND fee_type = 'LONG_TERM_EQUITY_INVESTMENT_EXPENDITURE') in_amount, (SELECT  IFNULL(SUM(fee.amount), 0) amount FROM "
												+ dataFmTitle
												+ "."
												+ "form_receipt r  JOIN "
												+ dataFmTitle
												+ "."
												+ "form f  ON r.form_id = f.form_id  AND f.status = 'APPROVAL'  JOIN "
												+ dataFmTitle
												+ "."
												+ "form_receipt_fee fee    ON r.form_id = fee.form_id   AND fee_type = 'EQUITY_INVESTMENT_CAPITAL_BACK') out_amount ";
			List<DataListItem> itemsEquityInvestmentAmount = pmReportServiceClient.doQuery(
				sqlEquityInvestmentAmount, 0, 0, fieldMapEquityInvestmentAmount);
			DataListItem itemEquityInvestmentAmount = itemsEquityInvestmentAmount.get(0);
			equityInvestmentAmount = (Money) itemEquityInvestmentAmount.getMap().get(
				"equityInvestmentAmount");
			totalamount.addTo(equityInvestmentAmount);
			result.setEquityInvestmentAmount(equityInvestmentAmount);
			
			/** 应收代偿款：资金划付申请单审核通过的代偿本金额代偿利息加总 */
			Money compensatoryAmount = Money.zero();
			
			HashMap<String, FcsField> fieldMapCompensatoryAmount = new HashMap<String, FcsField>();
			fieldMapCompensatoryAmount.put("compensatoryAmount", new FcsField("compensatoryAmount",
				"统计金额", null, DataTypeEnum.MONEY));
			String sqlCompensatoryAmount = "SELECT"
											+ " IFNULL(CASE WHEN out_amount.amount < in_amount.amount THEN 0 ELSE out_amount.amount - in_amount.amount"
											+ " END,0) as compensatoryAmount " + " FROM (SELECT"
											+ "        SUM(fee.appropriate_amount) amount"
											+ "     FROM "
											+ dataPmTitle
											+ "."
											+ "f_capital_appropriation_apply a"
											+ "      JOIN "
											+ dataPmTitle
											+ "."
											+ "f_capital_appropriation_apply_fee fee"
											+ "       ON a.apply_id = fee.apply_id"
											+ "  WHERE a.is_receipt = 'YES'"
											+ "       AND fee.appropriate_reason IN ('COMPENSATORY_PRINCIPAL', 'COMPENSATORY_INTEREST', 'COMPENSATORY_PENALTY', 'COMPENSATORY_LIQUIDATED_DAMAGES', 'COMPENSATORY_OTHER')) out_amount,"
											+ "    (SELECT"
											+ "       SUM(fee.charge_amount) amount"
											+ "    FROM "
											+ dataPmTitle
											+ "."
											+ "f_charge_notification n"
											+ "     JOIN "
											+ dataPmTitle
											+ "."
											+ "f_charge_notification_fee fee"
											+ "      ON n.notification_id = fee.notification_id"
											+ "      WHERE n.status = 'APPROVAL'"
											+ "     AND fee.fee_type IN ('COMPENSATORY_PRINCIPAL_WITHDRAWAL', 'COMPENSATORY_INTEREST_WITHDRAWAL', 'COMPENSATORY_DEDIT_WITHDRAWAL', 'COMPENSATORY_PENALTY_INTEREST_WITHDRAWAL', 'COMPENSATORY_OTHER_WITHDRAWAL')) in_amount;";
			List<DataListItem> itemsCompensatoryAmount = pmReportServiceClient.doQuery(
				sqlCompensatoryAmount, 0, 0, fieldMapCompensatoryAmount);
			DataListItem itemCompensatoryAmount = itemsCompensatoryAmount.get(0);
			compensatoryAmount = (Money) itemCompensatoryAmount.getMap().get("compensatoryAmount");
			totalamount.addTo(compensatoryAmount);
			result.setCompensatoryAmount(compensatoryAmount);
			
			/*** 存出保证金 */
			Money refundableDepositsAmount = Money.zero();
			
			HashMap<String, FcsField> fieldMapRefundableDepositsAmount = new HashMap<String, FcsField>();
			fieldMapRefundableDepositsAmount.put("refundableDepositsAmount", new FcsField(
				"refundableDepositsAmount", "统计金额", null, DataTypeEnum.MONEY));
			String sqlRefundableDepositsAmount = "SELECT  IFNULL(SUM(p.self_deposit_amount),0) as refundableDepositsAmount FROM "
													+ dataPmTitle + "." + "project p";
			List<DataListItem> itemsRefundableDepositsAmount = pmReportServiceClient.doQuery(
				sqlRefundableDepositsAmount, 0, 0, fieldMapRefundableDepositsAmount);
			DataListItem itemRefundableDepositsAmount = itemsRefundableDepositsAmount.get(0);
			refundableDepositsAmount = (Money) itemRefundableDepositsAmount.getMap().get(
				"refundableDepositsAmount");
			totalamount.addTo(refundableDepositsAmount);
			result.setRefundableDepositsAmount(refundableDepositsAmount);
			
			/*** 委托贷款 */
			Money entrustLoanAmount = Money.zero();
			
			HashMap<String, FcsField> fieldMapEntrustLoanAmount = new HashMap<String, FcsField>();
			fieldMapEntrustLoanAmount.put("entrustLoanAmount", new FcsField("entrustLoanAmount",
				"统计金额", null, DataTypeEnum.MONEY));
			String sqlEntrustLoanAmount = "SELECT IFNULL(SUM(CASE WHEN (loaned_amount - released_amount - comp_principal_amount) < 0 THEN 0 ELSE (loaned_amount - released_amount - comp_principal_amount)  END),0) entrustLoanAmount  FROM "
											+ dataPmTitle
											+ "."
											+ "project WHERE busi_type LIKE '4%'";
			List<DataListItem> itemsEntrustLoanAmount = pmReportServiceClient.doQuery(
				sqlEntrustLoanAmount, 0, 0, fieldMapEntrustLoanAmount);
			DataListItem itemEntrustLoanAmount = itemsEntrustLoanAmount.get(0);
			entrustLoanAmount = (Money) itemEntrustLoanAmount.getMap().get("entrustLoanAmount");
			totalamount.addTo(entrustLoanAmount);
			result.setEntrustLoanAmount(entrustLoanAmount);
			
			result.setTotalamount(totalamount);
			
			result.setSuccess(true);
			
		} catch (Exception e) {
			logger.error("查询首页统计信息失败" + e.getMessage(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return result;
	}
	
	private String getFourMohthStr(int selectCount) {
		String fourMohthStr = "";
		if (selectCount == 3) {
			fourMohthStr = "第一季度";
		} else if (selectCount == 6) {
			fourMohthStr = "第二季度";
		} else if (selectCount == 9) {
			fourMohthStr = "第三季度";
		} else if (selectCount == 12) {
			fourMohthStr = "第四季度";
			
		}
		selectCount = selectCount + 3;
		if (selectCount > 12) {
			selectCount = selectCount - 12;
		}
		return fourMohthStr;
		
	}
	
	public static void main(String[] args) {
		int a = 2;
		int b = a % 3;
		System.out.println(b);
	}
}
