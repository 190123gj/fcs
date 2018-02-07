package com.born.fcs.face.integration.crm.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.ws.service.CustomerService;
import com.born.fcs.crm.ws.service.info.CustomerBaseInfo;
import com.born.fcs.crm.ws.service.info.CustomerDetailInfo;
import com.born.fcs.crm.ws.service.order.CustomerDetailOrder;
import com.born.fcs.crm.ws.service.order.ResotrePublicOrder;
import com.born.fcs.crm.ws.service.order.TransferAllocationOrder;
import com.born.fcs.crm.ws.service.order.query.CustomerQueryOrder;
import com.born.fcs.crm.ws.service.result.ChangeResult;
import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Service("customerServiceClient")
public class CustomerServiceClient extends ClientAutowiredBaseService implements CustomerService {
	
	@Autowired
	protected CustomerService customerService;
	
	@Override
	public QueryBaseBatchResult<CustomerBaseInfo> list(final CustomerQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<CustomerBaseInfo>>() {
			
			@Override
			public QueryBaseBatchResult<CustomerBaseInfo> call() {
				return customerService.list(queryOrder);
			}
		});
	}
	
	@Override
	public CustomerDetailInfo queryByUserId(final Long userId) {
		return callInterface(new CallExternalInterface<CustomerDetailInfo>() {
			
			@Override
			public CustomerDetailInfo call() {
				return customerService.queryByUserId(userId);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateByUserId(final CustomerDetailOrder customerDetailOrder) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return customerService.updateByUserId(customerDetailOrder);
			}
		});
	}
	
	@Override
	public FcsBaseResult add(final CustomerDetailOrder customerDetailOrder) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return customerService.add(customerDetailOrder);
			}
		});
	}
	
	@Override
	public FcsBaseResult delete(final Long userId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return customerService.delete(userId);
			}
		});
	}
	
	@Override
	public FcsBaseResult setChangeStatus(final Long userId, final BooleanEnum status) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return customerService.setChangeStatus(userId, status);
			}
		});
	}
	
	@Override
	public ChangeResult isChange(final CustomerDetailOrder customerDetailOrder) {
		return callInterface(new CallExternalInterface<ChangeResult>() {
			
			@Override
			public ChangeResult call() {
				return customerService.isChange(customerDetailOrder);
			}
		});
	}
	
	@Override
	public FcsBaseResult statisticsCustomerDept() {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return customerService.statisticsCustomerDept();
			}
		});
	}
	
	@Override
	public FcsBaseResult statisticsCustomerRegion() {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return customerService.statisticsCustomerRegion();
			}
		});
	}
	
	@Override
	public FcsBaseResult transferAllocation(final TransferAllocationOrder transferAllocationOrder) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return customerService.transferAllocation(transferAllocationOrder);
			}
		});
	}
	
	@Override
	public FcsBaseResult allCustomerDept() {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return customerService.allCustomerDept();
			}
		});
	}
	
	@Override
	public FcsBaseResult resotrePublic(final ResotrePublicOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return customerService.resotrePublic(order);
			}
		});
	}
}
