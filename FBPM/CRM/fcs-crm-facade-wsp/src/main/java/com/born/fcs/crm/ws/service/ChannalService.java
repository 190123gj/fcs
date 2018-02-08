package com.born.fcs.crm.ws.service;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.crm.ws.service.info.ChannalInfo;
import com.born.fcs.crm.ws.service.order.ChannalOrder;
import com.born.fcs.crm.ws.service.order.query.ChannalQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.money.Money;

/**
 * 渠道接口
 * */
@WebService
public interface ChannalService {
	
	/** 添加渠道 */
	FcsBaseResult add(ChannalOrder order);
	
	/** 生成渠道编号 */
	String createChannalCode(String channalType);
	
	/** 更新渠道信息 */
	FcsBaseResult update(ChannalOrder order);
	
	/** 更新渠道状态 */
	FcsBaseResult updateStatus(long id, String status);
	
	/** 查询渠道详情 */
	ChannalInfo queryById(long id);
	
	/** 查询单个渠道详情 */
	ChannalInfo queryByChannalCode(String channalCode);
	
	/** 查询渠道详情 */
	ChannalInfo queryByChannalName(String channelName);
	
	/** 查询当前渠道号的所有数据，包括历史数据 */
	List<ChannalInfo> queryAll(Long id, String channalCode);
	
	/** 渠道列表查询 */
	QueryBaseBatchResult<ChannalInfo> list(ChannalQueryOrder queryOrder);
	
	/** 删除渠道 */
	FcsBaseResult delete(long id);
	
	/** 更新渠道 已使用信用额度 */
	FcsBaseResult updateCreditAmountUsedById(long id, Money creditAmountUsed);
	
	/**
	 * 更新渠道授信到期提醒状态
	 * @param isRemind BooleanEnum IS/NO =已/未提醒
	 * */
	FcsBaseResult updateRemindStatus(long id, String isRemind);
	
}
