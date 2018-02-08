/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午6:37:53 创建
 */
package com.born.fcs.fm.ws.service.forecast;

import javax.jws.WebService;

import com.born.fcs.fm.ws.info.forecast.AccountAmountDetailInfo;
import com.born.fcs.fm.ws.info.forecast.ForecastAccountInfo;
import com.born.fcs.fm.ws.info.forecast.SysForecastParamInfo;
import com.born.fcs.fm.ws.order.forecast.AccountAmountDetailQueryOrder;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountChangeOrder;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountOrder;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountQueryOrder;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountUpdateOrder;
import com.born.fcs.fm.ws.order.forecast.SysForecastParamBatchOrder;
import com.born.fcs.fm.ws.order.forecast.SysForecastParamQueryOrder;
import com.born.fcs.fm.ws.result.forecast.AccountAmountDetailMainIndexResult;
import com.born.fcs.fm.ws.result.forecast.ForecastAccountResult;
import com.born.fcs.fm.ws.result.forecast.SysForecastParamResult;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
@WebService
public interface ForecastService {
	
	// 能力0.列表查询
	// 能力1.查询所有配置，用于页面展示，
	// 能力2.修改所有配置，用于页面修改
	// 能力3.单条写入预测数据(带修改功能，审核后的修订审核前的预测)
	// 能力4.预测查询
	
	// 0.
	/**
	 * 列表查询 预测基础配置数据,用于维护预测配置信息
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<SysForecastParamInfo> queryBankMessageInfo(SysForecastParamQueryOrder order);
	
	// 1.
	/***
	 * 查询所有配置，用于页面展示和维护，返回结果是所有预测集合
	 * @return
	 */
	SysForecastParamResult findAll();
	
	// 2.
	/**
	 * 修改所有配置，用于页面修改和维护所有预测配置信息
	 * @param order
	 * @return
	 */
	FcsBaseResult modifyAll(SysForecastParamBatchOrder order);
	
	// 3. 单挑插入预测数据
	/**
	 * 插入预测数据
	 * @param order
	 * @return
	 */
	ForecastAccountResult add(ForecastAccountOrder order);
	
	/**
	 * 实际发生后修改预测数据
	 * @param order
	 * @return
	 */
	FcsBaseResult change(ForecastAccountChangeOrder order);
	
	// 4. 预测列表查询
	/**
	 * 列表查询预测数据
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ForecastAccountInfo> queryForecastAccountInfo(	ForecastAccountQueryOrder order);
	
	/**
	 * 单挑修改预测数据[用户自定义维护预测数据][只修改时间和金额]
	 */
	FcsBaseResult modifyForecastAccount(ForecastAccountUpdateOrder order);
	
	/**
	 * 删除预测数据[用户自定义维护预测数据] 删除主键为【来源系统】和【来源系统唯一标识 】组合
	 */
	FcsBaseResult delete(ForecastAccountOrder order);
	
	/**
	 * 资金余额明细
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<AccountAmountDetailInfo> queryAccountAmountDetail(	AccountAmountDetailQueryOrder order);
	
	/**
	 * 首页使用的统计查询
	 * @return
	 */
	AccountAmountDetailMainIndexResult queryMainIndex();
}
