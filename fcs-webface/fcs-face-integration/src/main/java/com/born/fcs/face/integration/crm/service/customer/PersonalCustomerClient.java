package com.born.fcs.face.integration.crm.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.ws.service.PersonalCustomerService;
import com.born.fcs.crm.ws.service.info.CustomerBaseInfo;
import com.born.fcs.crm.ws.service.info.PersonalCustomerInfo;
import com.born.fcs.crm.ws.service.order.DistributionOrder;
import com.born.fcs.crm.ws.service.order.PersonalCustomerDetailOrder;
import com.born.fcs.crm.ws.service.order.query.CustomerQueryOrder;
import com.born.fcs.crm.ws.service.result.ValidateCustomerResult;
import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Service("personalCustomerClient")
public class PersonalCustomerClient extends ClientAutowiredBaseService implements
																		PersonalCustomerService {
	@Autowired
	PersonalCustomerService personalCustomerService;
	
	@Override
	public FcsBaseResult add(final PersonalCustomerDetailOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return personalCustomerService.add(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult update(final PersonalCustomerDetailOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return personalCustomerService.update(order);
			}
		});
	}
	
	@Override
	public PersonalCustomerInfo queryById(final String customerId) {
		return callInterface(new CallExternalInterface<PersonalCustomerInfo>() {
			
			@Override
			public PersonalCustomerInfo call() {
				return personalCustomerService.queryById(customerId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<CustomerBaseInfo> list(final CustomerQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<CustomerBaseInfo>>() {
			
			@Override
			public QueryBaseBatchResult<CustomerBaseInfo> call() {
				return personalCustomerService.list(queryOrder);
			}
		});
	}
	
	@Override
	public FcsBaseResult delete(final Long userId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return personalCustomerService.delete(userId);
			}
		});
	}
	
	@Override
	public PersonalCustomerInfo queryByName(final String customerName) {
		return callInterface(new CallExternalInterface<PersonalCustomerInfo>() {
			
			@Override
			public PersonalCustomerInfo call() {
				return personalCustomerService.queryByName(customerName);
			}
		});
	}
	
	@Override
	public PersonalCustomerInfo queryByUserId(final Long userId) {
		return callInterface(new CallExternalInterface<PersonalCustomerInfo>() {
			
			@Override
			public PersonalCustomerInfo call() {
				return personalCustomerService.queryByUserId(userId);
			}
		});
	}
	
	@Override
	public ValidateCustomerResult ValidateCustomer(final String certNo, final String customerName,
													final Long inputPersonId) {
		return callInterface(new CallExternalInterface<ValidateCustomerResult>() {
			
			@Override
			public ValidateCustomerResult call() {
				return personalCustomerService
					.ValidateCustomer(certNo, customerName, inputPersonId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ProjectInfo> queryProject(final ProjectQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectInfo> call() {
				return personalCustomerService.queryProject(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult distribution(final DistributionOrder distributionOrder) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return personalCustomerService.distribution(distributionOrder);
			}
		});
	}
	
	@Override
	public PersonalCustomerInfo queryByCertNo(final String certNo) {
		return callInterface(new CallExternalInterface<PersonalCustomerInfo>() {
			
			@Override
			public PersonalCustomerInfo call() {
				return personalCustomerService.queryByCertNo(certNo);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<CustomerBaseInfo> customerList(final CustomerQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<CustomerBaseInfo>>() {
			
			@Override
			public QueryBaseBatchResult<CustomerBaseInfo> call() {
				return personalCustomerService.customerList(queryOrder);
			}
		});
	}
	
}
