package com.born.fcs.pm.integration.crm.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.ws.service.PersonalCustomerService;
import com.born.fcs.crm.ws.service.info.CustomerBaseInfo;
import com.born.fcs.crm.ws.service.info.PersonalCustomerInfo;
import com.born.fcs.crm.ws.service.order.DistributionOrder;
import com.born.fcs.crm.ws.service.order.PersonalCustomerDetailOrder;
import com.born.fcs.crm.ws.service.order.query.CustomerQueryOrder;
import com.born.fcs.crm.ws.service.result.ValidateCustomerResult;
import com.born.fcs.pm.integration.common.CallExternalInterface;
import com.born.fcs.pm.integration.common.impl.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Service("personalCustomerServiceClient")
public class PersonalCustomerServiceClient extends ClientAutowiredBaseService implements
																				PersonalCustomerService {
	@Autowired
	PersonalCustomerService personalCustomerWebService;
	
	@Override
	public FcsBaseResult add(final PersonalCustomerDetailOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return personalCustomerWebService.add(order);
			}
		});
	}
	
	@Override
	public ValidateCustomerResult ValidateCustomer(final String certNo, final String customerName,
													final Long inputPersonId) {
		return callInterface(new CallExternalInterface<ValidateCustomerResult>() {
			
			@Override
			public ValidateCustomerResult call() {
				return personalCustomerWebService.ValidateCustomer(certNo, customerName,
					inputPersonId);
			}
		});
	}
	
	@Override
	public FcsBaseResult update(final PersonalCustomerDetailOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return personalCustomerWebService.update(order);
			}
		});
	}
	
	@Override
	public PersonalCustomerInfo queryById(final String customerId) {
		return callInterface(new CallExternalInterface<PersonalCustomerInfo>() {
			
			@Override
			public PersonalCustomerInfo call() {
				return personalCustomerWebService.queryById(customerId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<CustomerBaseInfo> list(final CustomerQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<CustomerBaseInfo>>() {
			
			@Override
			public QueryBaseBatchResult<CustomerBaseInfo> call() {
				return personalCustomerWebService.list(queryOrder);
			}
		});
	}
	
	@Override
	public FcsBaseResult delete(final Long userId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return personalCustomerWebService.delete(userId);
			}
		});
	}
	
	@Override
	public PersonalCustomerInfo queryByName(final String customerName) {
		return callInterface(new CallExternalInterface<PersonalCustomerInfo>() {
			
			@Override
			public PersonalCustomerInfo call() {
				return personalCustomerWebService.queryByName(customerName);
			}
		});
	}
	
	@Override
	public PersonalCustomerInfo queryByUserId(final Long userId) {
		return callInterface(new CallExternalInterface<PersonalCustomerInfo>() {
			
			@Override
			public PersonalCustomerInfo call() {
				return personalCustomerWebService.queryByUserId(userId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ProjectInfo> queryProject(final ProjectQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectInfo> call() {
				return personalCustomerWebService.queryProject(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult distribution(final DistributionOrder distributionOrder) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return personalCustomerWebService.distribution(distributionOrder);
			}
		});
	}
	
	@Override
	public PersonalCustomerInfo queryByCertNo(final String certNo) {
		return callInterface(new CallExternalInterface<PersonalCustomerInfo>() {
			
			@Override
			public PersonalCustomerInfo call() {
				return personalCustomerWebService.queryByCertNo(certNo);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<CustomerBaseInfo> customerList(final CustomerQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<CustomerBaseInfo>>() {
			
			@Override
			public QueryBaseBatchResult<CustomerBaseInfo> call() {
				return personalCustomerWebService.customerList(queryOrder);
			}
		});
	}
}
