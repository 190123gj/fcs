package com.born.fcs.face.integration.am.service.assesscompany;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.am.ws.info.assesscompany.AssessCompanyContactInfo;
import com.born.fcs.am.ws.info.assesscompany.AssetsAssessCompanyInfo;
import com.born.fcs.am.ws.order.assesscompany.AssetsAssessCompanyOrder;
import com.born.fcs.am.ws.order.assesscompany.AssetsAssessCompanyQueryOrder;
import com.born.fcs.am.ws.service.assesscompany.AssetsAssessCompanyService;
import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * @author jil
 *
 */
@Service("assetsAssessCompanyServiceClient")
public class AssetsAssessCompanyServiceClient extends ClientAutowiredBaseService implements
																				AssetsAssessCompanyService {
	
	@Override
	public AssetsAssessCompanyInfo findById(final long id) {
		return callInterface(new CallExternalInterface<AssetsAssessCompanyInfo>() {
			
			@Override
			public AssetsAssessCompanyInfo call() {
				return assetsAssessCompanyWebService.findById(id);
			}
		});
	}
	
	@Override
	public FcsBaseResult save(final AssetsAssessCompanyOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return assetsAssessCompanyWebService.save(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<AssetsAssessCompanyInfo> query(	final AssetsAssessCompanyQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<AssetsAssessCompanyInfo>>() {
			
			@Override
			public QueryBaseBatchResult<AssetsAssessCompanyInfo> call() {
				return assetsAssessCompanyWebService.query(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult deleteById(final AssetsAssessCompanyOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return assetsAssessCompanyWebService.deleteById(order);
			}
		});
	}
	
	@Override
	public long findByCompanyNameCount(final String companyName) {
		return callInterface(new CallExternalInterface<Long>() {
			
			@Override
			public Long call() {
				return assetsAssessCompanyWebService.findByCompanyNameCount(companyName);
			}
		});
	}
	
	@Override
	public List<AssessCompanyContactInfo> findContactByCompanyId(final long assessCompanyId) {
		return callInterface(new CallExternalInterface<List<AssessCompanyContactInfo>>() {
			
			@Override
			public List<AssessCompanyContactInfo> call() {
				return assetsAssessCompanyWebService.findContactByCompanyId(assessCompanyId);
			}
		});
	}
	
	@Override
	public List<String> findCities() {
		return callInterface(new CallExternalInterface<List<String>>() {
			
			@Override
			public List<String> call() {
				return assetsAssessCompanyWebService.findCities();
			}
		});
		
	}
	
}
