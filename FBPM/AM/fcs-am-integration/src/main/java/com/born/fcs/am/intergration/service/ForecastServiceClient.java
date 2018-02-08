package com.born.fcs.am.intergration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.born.fcs.fm.ws.service.forecast.ForecastService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Service("forecastServiceClient")
public class ForecastServiceClient extends ClientAutowiredBaseService implements ForecastService {
	
	@Autowired
	ForecastService forecastWebService;
	
	@Override
	public QueryBaseBatchResult<SysForecastParamInfo> queryBankMessageInfo(	final SysForecastParamQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<SysForecastParamInfo>>() {
			
			@Override
			public QueryBaseBatchResult<SysForecastParamInfo> call() {
				return forecastWebService.queryBankMessageInfo(order);
			}
		});
	}
	
	@Override
	public SysForecastParamResult findAll() {
		return callInterface(new CallExternalInterface<SysForecastParamResult>() {
			
			@Override
			public SysForecastParamResult call() {
				return forecastWebService.findAll();
			}
		});
	}
	
	@Override
	public FcsBaseResult modifyAll(final SysForecastParamBatchOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return forecastWebService.modifyAll(order);
			}
		});
	}
	
	@Override
	public ForecastAccountResult add(final ForecastAccountOrder order) {
		return callInterface(new CallExternalInterface<ForecastAccountResult>() {
			
			@Override
			public ForecastAccountResult call() {
				return forecastWebService.add(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ForecastAccountInfo> queryForecastAccountInfo(	final ForecastAccountQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ForecastAccountInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ForecastAccountInfo> call() {
				return forecastWebService.queryForecastAccountInfo(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult delete(final ForecastAccountOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return forecastWebService.delete(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult modifyForecastAccount(final ForecastAccountUpdateOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return forecastWebService.modifyForecastAccount(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<AccountAmountDetailInfo> queryAccountAmountDetail(	final AccountAmountDetailQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<AccountAmountDetailInfo>>() {
			
			@Override
			public QueryBaseBatchResult<AccountAmountDetailInfo> call() {
				return forecastWebService.queryAccountAmountDetail(order);
			}
		});
	}
	
	@Override
	public AccountAmountDetailMainIndexResult queryMainIndex() {
		return null;
	}
	
	@Override
	public FcsBaseResult change(final ForecastAccountChangeOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return forecastWebService.change(order);
			}
		});
	}
}
