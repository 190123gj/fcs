package com.born.fcs.face.integration.rm.service.submission;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.rm.ws.info.accountbalance.AccountBalanceDataInfo;
import com.born.fcs.rm.ws.info.accountbalance.AccountBalanceInfo;
import com.born.fcs.rm.ws.order.accountbalance.AccountBalanceDataQueryOrder;
import com.born.fcs.rm.ws.order.accountbalance.AccountBalanceOrder;
import com.born.fcs.rm.ws.order.accountbalance.AccountBalanceQueryOrder;
import com.born.fcs.rm.ws.service.accountbalance.AccountBalanceService;

/**
 * 
 * 科目余额表
 * 
 * @author lirz
 * 
 * 2016-8-15 上午10:55:43
 */
@Service("accountBalanceServiceClient")
public class AccountBalanceServiceClient extends ClientAutowiredBaseService implements
																			AccountBalanceService {
	
	@Override
	public AccountBalanceInfo findById(final long id) {
		return callInterface(new CallExternalInterface<AccountBalanceInfo>() {
			
			@Override
			public AccountBalanceInfo call() {
				return accountBalanceWebService.findById(id);
			}
		});
	}
	
	@Override
	public FcsBaseResult save(final AccountBalanceOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return accountBalanceWebService.save(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<AccountBalanceInfo> queryList(	final AccountBalanceQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<AccountBalanceInfo>>() {
			
			@Override
			public QueryBaseBatchResult<AccountBalanceInfo> call() {
				return accountBalanceWebService.queryList(queryOrder);
			}
		});
	}
	
	@Override
	public List<AccountBalanceDataInfo> queryDatas(	final AccountBalanceDataQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<List<AccountBalanceDataInfo>>() {
			
			@Override
			public List<AccountBalanceDataInfo> call() {
				return accountBalanceWebService.queryDatas(queryOrder);
			}
		});
	}

	@Override
	public AccountBalanceDataInfo queryData(final AccountBalanceDataQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<AccountBalanceDataInfo>() {
			
			@Override
			public AccountBalanceDataInfo call() {
				return accountBalanceWebService.queryData(queryOrder);
			}
		});
	}
	
}
