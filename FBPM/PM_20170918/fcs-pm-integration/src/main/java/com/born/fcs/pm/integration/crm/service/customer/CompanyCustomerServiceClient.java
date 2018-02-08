package com.born.fcs.pm.integration.crm.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.ws.service.CompanyCustomerService;
import com.born.fcs.crm.ws.service.info.CompanyCustomerInfo;
import com.born.fcs.crm.ws.service.info.CustomerBaseInfo;
import com.born.fcs.crm.ws.service.order.CompanyCustomerDetailOrder;
import com.born.fcs.crm.ws.service.order.UpdateFromCreditOrder;
import com.born.fcs.crm.ws.service.order.query.CustomerQueryOrder;
import com.born.fcs.crm.ws.service.result.ValidateCustomerResult;
import com.born.fcs.pm.integration.common.CallExternalInterface;
import com.born.fcs.pm.integration.common.impl.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Service("companyCustomerServiceClient")
public class CompanyCustomerServiceClient extends ClientAutowiredBaseService implements
																			CompanyCustomerService {
	@Autowired
	CompanyCustomerService companyCustomerWebService;
	
	@Override
	public FcsBaseResult add(final CompanyCustomerDetailOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return companyCustomerWebService.add(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult update(final CompanyCustomerDetailOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return companyCustomerWebService.update(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateFromCredit(final UpdateFromCreditOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return companyCustomerWebService.updateFromCredit(order);
			}
		});
	}
	
	@Override
	public CompanyCustomerInfo queryById(final String customerId, final Long formId) {
		return callInterface(new CallExternalInterface<CompanyCustomerInfo>() {
			
			@Override
			public CompanyCustomerInfo call() {
				return companyCustomerWebService.queryById(customerId, formId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<CustomerBaseInfo> list(final CustomerQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<CustomerBaseInfo>>() {
			
			@Override
			public QueryBaseBatchResult<CustomerBaseInfo> call() {
				return companyCustomerWebService.list(queryOrder);
			}
		});
	}
	
	@Override
	public FcsBaseResult delete(final Long userId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return companyCustomerWebService.delete(userId);
			}
		});
	}
	
	@Override
	public FcsBaseResult deleteQualificationById(final long id) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return companyCustomerWebService.deleteQualificationById(id);
			}
		});
	}
	
	@Override
	public FcsBaseResult deleteOwnershipById(final long id) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return companyCustomerWebService.deleteOwnershipById(id);
			}
		});
	}
	
	@Override
	public CompanyCustomerInfo queryByName(final String customerName) {
		return callInterface(new CallExternalInterface<CompanyCustomerInfo>() {
			
			@Override
			public CompanyCustomerInfo call() {
				return companyCustomerWebService.queryByName(customerName);
			}
		});
	}
	
	@Override
	public CompanyCustomerInfo queryByUserId(final Long userId, final Long formId) {
		return callInterface(new CallExternalInterface<CompanyCustomerInfo>() {
			
			@Override
			public CompanyCustomerInfo call() {
				return companyCustomerWebService.queryByUserId(userId, formId);
			}
		});
	}
	
	@Override
	public CompanyCustomerInfo queryByBusiLicenseNo(final String busiLicenseNo) {
		return callInterface(new CallExternalInterface<CompanyCustomerInfo>() {
			
			@Override
			public CompanyCustomerInfo call() {
				return companyCustomerWebService.queryByBusiLicenseNo(busiLicenseNo);
			}
		});
	}
	
	@Override
	public ValidateCustomerResult ValidateCustomer(final String busiLicenseNo,
													final String customerName,
													final Long inputPersonId) {
		return callInterface(new CallExternalInterface<ValidateCustomerResult>() {
			
			@Override
			public ValidateCustomerResult call() {
				return companyCustomerWebService.ValidateCustomer(busiLicenseNo, customerName,
					inputPersonId);
			}
		});
	}
}
