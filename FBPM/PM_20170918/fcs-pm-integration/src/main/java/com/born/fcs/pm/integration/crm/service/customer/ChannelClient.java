package com.born.fcs.pm.integration.crm.service.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.ws.service.ChannalService;
import com.born.fcs.crm.ws.service.info.ChannalInfo;
import com.born.fcs.crm.ws.service.order.ChannalOrder;
import com.born.fcs.crm.ws.service.order.query.ChannalQueryOrder;
import com.born.fcs.pm.integration.common.CallExternalInterface;
import com.born.fcs.pm.integration.common.impl.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.money.Money;

@Service("channelClient")
public class ChannelClient extends ClientAutowiredBaseService implements ChannalService {
	
	@Autowired
	ChannalService channelWebService;
	
	@Override
	public FcsBaseResult add(final ChannalOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return channelWebService.add(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult update(final ChannalOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return channelWebService.update(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateStatus(final long id, final String status) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return channelWebService.updateStatus(id, status);
			}
		});
	}
	
	@Override
	public ChannalInfo queryById(final long id) {
		return callInterface(new CallExternalInterface<ChannalInfo>() {
			
			@Override
			public ChannalInfo call() {
				return channelWebService.queryById(id);
			}
		});
	}
	
	@Override
	public ChannalInfo queryByChannalCode(final String channalCode) {
		return callInterface(new CallExternalInterface<ChannalInfo>() {
			
			@Override
			public ChannalInfo call() {
				return channelWebService.queryByChannalCode(channalCode);
			}
		});
	}
	
	@Override
	public ChannalInfo queryByChannalName(final String channelName) {
		return callInterface(new CallExternalInterface<ChannalInfo>() {
			
			@Override
			public ChannalInfo call() {
				return channelWebService.queryByChannalName(channelName);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ChannalInfo> list(final ChannalQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ChannalInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ChannalInfo> call() {
				return channelWebService.list(queryOrder);
			}
		});
	}
	
	@Override
	public FcsBaseResult delete(final long id) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return channelWebService.delete(id);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateCreditAmountUsedById(final long id, final Money creditAmountUsed) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return channelWebService.updateCreditAmountUsedById(id, creditAmountUsed);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateRemindStatus(final long id, final String isRemind) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				
				return channelWebService.updateRemindStatus(id, isRemind);
			}
		});
	}
	
	@Override
	public List<ChannalInfo> queryAll(final Long id, final String channalCode) {
		return callInterface(new CallExternalInterface<List<ChannalInfo>>() {
			
			@Override
			public List<ChannalInfo> call() {
				
				return channelWebService.queryAll(id, channalCode);
			}
		});
	}
	
	@Override
	public String createChannalCode(final String channalType) {
		return callInterface(new CallExternalInterface<String>() {
			
			@Override
			public String call() {
				
				return channelWebService.createChannalCode(channalType);
			}
		});
	}
	
}
