package com.born.fcs.pm.integration.crm.service.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.ws.service.PersonalCompanyService;
import com.born.fcs.crm.ws.service.info.CompanyCustomerInfo;
import com.born.fcs.crm.ws.service.info.PersonalCompanyInfo;
import com.born.fcs.crm.ws.service.order.PersonalCompanyOrder;
import com.born.fcs.crm.ws.service.order.query.PersonalCompanyQueryOrder;
import com.born.fcs.pm.integration.common.CallExternalInterface;
import com.born.fcs.pm.integration.common.impl.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Service("personalCompanyServiceClient")
public class PersonalCompanyServiceClient extends ClientAutowiredBaseService implements
																			PersonalCompanyService {
	
	@Autowired
	protected PersonalCompanyService personalCompanyWebService;
	
	@Override
	public FcsBaseResult add(final PersonalCompanyOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return personalCompanyWebService.add(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateById(final PersonalCompanyOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return personalCompanyWebService.updateById(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateByList(final List<PersonalCompanyOrder> list, final String customerId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return personalCompanyWebService.updateByList(list, customerId);
			}
		});
	}
	
	@Override
	public CompanyCustomerInfo queryById(final long id) {
		return callInterface(new CallExternalInterface<CompanyCustomerInfo>() {
			
			@Override
			public CompanyCustomerInfo call() {
				return personalCompanyWebService.queryById(id);
			}
		});
	}
	
	@Override
	public FcsBaseResult delete(final long id) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return personalCompanyWebService.delete(id);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<PersonalCompanyInfo> list(final PersonalCompanyQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<PersonalCompanyInfo>>() {
			
			@Override
			public QueryBaseBatchResult<PersonalCompanyInfo> call() {
				return personalCompanyWebService.list(queryOrder);
			}
		});
	}
	
}
