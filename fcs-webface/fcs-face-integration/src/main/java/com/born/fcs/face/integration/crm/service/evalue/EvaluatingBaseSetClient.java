package com.born.fcs.face.integration.crm.service.evalue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.ws.service.EvaluatingBaseSetService;
import com.born.fcs.crm.ws.service.info.EvaluatingBaseQueryInfo;
import com.born.fcs.crm.ws.service.info.EvaluatingBaseSetInfo;
import com.born.fcs.crm.ws.service.order.EvaluatingBaseSetOrder;
import com.born.fcs.crm.ws.service.order.query.EvaluatingBaseSetQueryOrder;
import com.born.fcs.crm.ws.service.result.CityEvalueSetResult;
import com.born.fcs.crm.ws.service.result.PublicCauseSetResult;
import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 分级指标配置
 * */
@Service("evaluatingBaseSetClient")
public class EvaluatingBaseSetClient extends ClientAutowiredBaseService implements
																		EvaluatingBaseSetService {
	
	@Autowired
	private EvaluatingBaseSetService evaluatingBaseSetService;
	
	@Override
	public FcsBaseResult add(final EvaluatingBaseSetOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return evaluatingBaseSetService.add(order);
			}
		});
	}
	
	@Override
	public EvaluatingBaseSetInfo queryById(final long id) {
		return callInterface(new CallExternalInterface<EvaluatingBaseSetInfo>() {
			
			@Override
			public EvaluatingBaseSetInfo call() {
				return evaluatingBaseSetService.queryById(id);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateById(final EvaluatingBaseSetOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return evaluatingBaseSetService.updateById(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult delete(final long id) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return evaluatingBaseSetService.delete(id);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<EvaluatingBaseSetInfo> list(final EvaluatingBaseSetQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<EvaluatingBaseSetInfo>>() {
			
			@Override
			public QueryBaseBatchResult<EvaluatingBaseSetInfo> call() {
				return evaluatingBaseSetService.list(queryOrder);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateByList(final List<EvaluatingBaseSetOrder> orderList) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return evaluatingBaseSetService.updateByList(orderList);
			}
		});
	}
	
	@Override
	public List<EvaluatingBaseQueryInfo> levelConcat(final String type, final String level) {
		return callInterface(new CallExternalInterface<List<EvaluatingBaseQueryInfo>>() {
			
			@Override
			public List<EvaluatingBaseQueryInfo> call() {
				return evaluatingBaseSetService.levelConcat(type, level);
			}
		});
	}
	
	/**
	 * 统计配置指标总分
	 * @param type 指标类型
	 * @param level 等级
	 * */
	@Override
	public double countScoreByType(final EvaluatingBaseSetQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<Double>() {
			
			@Override
			public Double call() {
				return evaluatingBaseSetService.countScoreByType(queryOrder);
			}
		});
	}
	
	@Override
	public CityEvalueSetResult queryCityEvalueSet(final long userId) {
		return callInterface(new CallExternalInterface<CityEvalueSetResult>() {
			
			@Override
			public CityEvalueSetResult call() {
				return evaluatingBaseSetService.queryCityEvalueSet(userId);
			}
		});
	}
	
	@Override
	public PublicCauseSetResult publicCauseSet(final long userId) {
		return callInterface(new CallExternalInterface<PublicCauseSetResult>() {
			
			@Override
			public PublicCauseSetResult call() {
				return evaluatingBaseSetService.publicCauseSet(userId);
			}
		});
	}
}
