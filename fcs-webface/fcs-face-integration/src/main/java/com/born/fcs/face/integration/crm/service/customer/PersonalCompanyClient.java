package com.born.fcs.face.integration.crm.service.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.ws.service.PersonalCompanyService;
import com.born.fcs.crm.ws.service.info.CompanyCustomerInfo;
import com.born.fcs.crm.ws.service.info.PersonalCompanyInfo;
import com.born.fcs.crm.ws.service.order.PersonalCompanyOrder;
import com.born.fcs.crm.ws.service.order.query.PersonalCompanyQueryOrder;
import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Service("personalCompanyClient")
public class PersonalCompanyClient extends ClientAutowiredBaseService implements
																		PersonalCompanyService {
	
	@Autowired
	protected PersonalCompanyService personalCompanyService;
	
	@Override
	public FcsBaseResult add(final PersonalCompanyOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return personalCompanyService.add(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateById(final PersonalCompanyOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return personalCompanyService.updateById(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateByList(final List<PersonalCompanyOrder> list, final String customerId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return personalCompanyService.updateByList(list, customerId);
			}
		});
	}
	
	@Override
	public CompanyCustomerInfo queryById(final long id) {
		return callInterface(new CallExternalInterface<CompanyCustomerInfo>() {
			
			@Override
			public CompanyCustomerInfo call() {
				return personalCompanyService.queryById(id);
			}
		});
	}
	
	@Override
	public FcsBaseResult delete(final long id) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return personalCompanyService.delete(id);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<PersonalCompanyInfo> list(final PersonalCompanyQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<PersonalCompanyInfo>>() {
			
			@Override
			public QueryBaseBatchResult<PersonalCompanyInfo> call() {
				return personalCompanyService.list(queryOrder);
			}
		});
	}
	
}
