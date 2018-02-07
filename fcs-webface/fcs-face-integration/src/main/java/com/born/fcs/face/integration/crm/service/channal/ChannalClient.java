package com.born.fcs.face.integration.crm.service.channal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.ws.service.ChannalService;
import com.born.fcs.crm.ws.service.info.ChannalInfo;
import com.born.fcs.crm.ws.service.order.ChannalOrder;
import com.born.fcs.crm.ws.service.order.query.ChannalQueryOrder;
import com.born.fcs.face.integration.service.BasicDataCacheService;
import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.money.Money;

@Service("channalClient")
public class ChannalClient extends ClientAutowiredBaseService implements ChannalService {
	
	@Autowired
	ChannalService channalService;
	
	@Autowired
	BasicDataCacheService basicDataCacheService;
	
	@Override
	public FcsBaseResult add(final ChannalOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				FcsBaseResult result = channalService.add(order);
				if (result != null && result.isSuccess()) {
					basicDataCacheService.clearCache();
				}
				return result;
			}
		});
	}
	
	@Override
	public FcsBaseResult update(final ChannalOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				FcsBaseResult result = channalService.update(order);
				if (result != null && result.isSuccess()) {
					basicDataCacheService.clearCache();
				}
				return result;
			}
		});
	}
	
	@Override
	public FcsBaseResult updateStatus(final long id, final String status) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				FcsBaseResult result = channalService.updateStatus(id, status);
				if (result != null && result.isSuccess()) {
					basicDataCacheService.clearCache();
				}
				return result;
			}
		});
	}
	
	@Override
	public ChannalInfo queryById(final long id) {
		return callInterface(new CallExternalInterface<ChannalInfo>() {
			
			@Override
			public ChannalInfo call() {
				return channalService.queryById(id);
			}
		});
	}
	
	@Override
	public ChannalInfo queryByChannalCode(final String channalCode) {
		return callInterface(new CallExternalInterface<ChannalInfo>() {
			
			@Override
			public ChannalInfo call() {
				return channalService.queryByChannalCode(channalCode);
			}
		});
	}
	
	@Override
	public ChannalInfo queryByChannalName(final String channelName) {
		return callInterface(new CallExternalInterface<ChannalInfo>() {
			
			@Override
			public ChannalInfo call() {
				return channalService.queryByChannalName(channelName);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ChannalInfo> list(final ChannalQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ChannalInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ChannalInfo> call() {
				return channalService.list(queryOrder);
			}
		});
	}
	
	@Override
	public FcsBaseResult delete(final long id) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				FcsBaseResult result = channalService.delete(id);
				if (result != null && result.isSuccess()) {
					basicDataCacheService.clearCache();
				}
				return result;
			}
		});
	}
	
	@Override
	public FcsBaseResult updateCreditAmountUsedById(final long id, final Money creditAmountUsed) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				
				return channalService.updateCreditAmountUsedById(id, creditAmountUsed);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateRemindStatus(final long id, final String isRemind) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				
				return channalService.updateRemindStatus(id, isRemind);
			}
		});
	}
	
	@Override
	public List<ChannalInfo> queryAll(final Long id, final String channalCode) {
		return callInterface(new CallExternalInterface<List<ChannalInfo>>() {
			
			@Override
			public List<ChannalInfo> call() {
				
				return channalService.queryAll(id, channalCode);
			}
		});
	}
	
	@Override
	public String createChannalCode(final String channalType) {
		return callInterface(new CallExternalInterface<String>() {
			
			@Override
			public String call() {
				
				return channalService.createChannalCode(channalType);
			}
		});
	}
	
}
