package com.born.fcs.crm.ws.service;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.crm.ws.service.info.EvaluatingBaseQueryInfo;
import com.born.fcs.crm.ws.service.info.EvaluatingBaseSetInfo;
import com.born.fcs.crm.ws.service.order.EvaluatingBaseSetOrder;
import com.born.fcs.crm.ws.service.order.query.EvaluatingBaseSetQueryOrder;
import com.born.fcs.crm.ws.service.result.CityEvalueSetResult;
import com.born.fcs.crm.ws.service.result.PublicCauseSetResult;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 评价基础配置服务
 * */
@WebService
public interface EvaluatingBaseSetService {
	
	FcsBaseResult add(EvaluatingBaseSetOrder order);
	
	EvaluatingBaseSetInfo queryById(long id);
	
	FcsBaseResult updateById(EvaluatingBaseSetOrder order);
	
	FcsBaseResult delete(long id);
	
	/** 批量更新 */
	FcsBaseResult updateByList(List<EvaluatingBaseSetOrder> orderList);
	
	QueryBaseBatchResult<EvaluatingBaseSetInfo> list(EvaluatingBaseSetQueryOrder queryOrder);
	
	/**
	 * 3级 或 4级 指标合并查询
	 * @param type 指标类型
	 * @param level 合并等级 3/4
	 * */
	public List<EvaluatingBaseQueryInfo> levelConcat(String type, String level);
	
	/** 城市开发类指标查询,主观，标准值，财务部分 */
	public CityEvalueSetResult queryCityEvalueSet(long userId);
	
	/** 公用事业类指标查询 有/无标准值 */
	public PublicCauseSetResult publicCauseSet(long userId);
	
	/**
	 * 统计配置指标总分
	 * @param type 指标类型
	 * @param level 指标等级
	 * */
	public double countScoreByType(EvaluatingBaseSetQueryOrder queryOrder);
}
