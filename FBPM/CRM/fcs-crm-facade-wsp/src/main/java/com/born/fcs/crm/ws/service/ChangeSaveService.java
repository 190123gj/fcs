package com.born.fcs.crm.ws.service;

import java.util.HashMap;

import javax.jws.WebService;

import com.born.fcs.crm.ws.service.info.ChangeListInfo;
import com.born.fcs.crm.ws.service.order.ChangeListOrder;
import com.born.fcs.crm.ws.service.order.query.ChangeListQueryOrder;
import com.born.fcs.crm.ws.service.result.ChangeResult;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 修改记录
 * */

@WebService
public interface ChangeSaveService {
	/**
	 * 存储修改记录
	 * */
	FcsBaseResult save(ChangeListOrder changeListOrder);
	
	/**
	 * 修改详情查询
	 * */
	ChangeListInfo queryChange(Long changeId);
	
	/** 修改记录列表 **/
	QueryBaseBatchResult<ChangeListInfo> list(ChangeListQueryOrder queryOrder);
	
	/**
	 * 创建修改记录order bean类型相同 都不为空
	 * @param newInfo 修改后 不为空
	 * @param oldInfo 修改前 不为空
	 * @param lableNames <lableKey,lableName> 字段名注释
	 * @param onlyContaint 仅比较lableNames里面包含的字段
	 * 
	 * */
	ChangeResult createChangeOrder(Object newInfo, Object oldInfo, HashMap<String, String> lableNames,
									Boolean onlyContaint);
}
