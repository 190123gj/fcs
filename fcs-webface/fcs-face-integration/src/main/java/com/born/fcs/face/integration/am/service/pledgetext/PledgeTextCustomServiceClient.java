package com.born.fcs.face.integration.am.service.pledgetext;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.am.ws.info.pledgetext.PledgeTextCustomInfo;
import com.born.fcs.am.ws.order.pledgetext.PledgeTextCustomQueryOrder;
import com.born.fcs.am.ws.service.pledgetext.PledgeTextCustomService;
import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * @author jil
 *
 */
@Service("pledgeTextCustomServiceClient")
public class PledgeTextCustomServiceClient extends ClientAutowiredBaseService implements
																				PledgeTextCustomService {
	
	@Override
	public PledgeTextCustomInfo findById(final long textId) {
		return callInterface(new CallExternalInterface<PledgeTextCustomInfo>() {
			
			@Override
			public PledgeTextCustomInfo call() {
				return pledgeTextCustomWebService.findById(textId);
			}
		});
	}
	
	@Override
	public List<PledgeTextCustomInfo> findByTypeId(final long typeId) {
		return callInterface(new CallExternalInterface<List<PledgeTextCustomInfo>>() {
			
			@Override
			public List<PledgeTextCustomInfo> call() {
				return pledgeTextCustomWebService.findByTypeId(typeId);
			}
		});
	}
	
	@Override
	public int deleteById(final long textId) {
		return callInterface(new CallExternalInterface<Integer>() {
			
			@Override
			public Integer call() {
				return pledgeTextCustomWebService.deleteById(textId);
			}
		});
	}
	
	@Override
	public List<PledgeTextCustomInfo> findMtextByTypeId(final long typeId) {
		return callInterface(new CallExternalInterface<List<PledgeTextCustomInfo>>() {
			
			@Override
			public List<PledgeTextCustomInfo> call() {
				return pledgeTextCustomWebService.findMtextByTypeId(typeId);
			}
		});
	}
	
	@Override
	public List<PledgeTextCustomInfo> findNotMtextByTypeId(final long typeId) {
		return callInterface(new CallExternalInterface<List<PledgeTextCustomInfo>>() {
			
			@Override
			public List<PledgeTextCustomInfo> call() {
				return pledgeTextCustomWebService.findNotMtextByTypeId(typeId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<PledgeTextCustomInfo> query(final PledgeTextCustomQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<PledgeTextCustomInfo>>() {
			
			@Override
			public QueryBaseBatchResult<PledgeTextCustomInfo> call() {
				return pledgeTextCustomWebService.query(order);
			}
		});
	}
	
	@Override
	public PledgeTextCustomInfo findByTypeIdAndFieldName(final long typeId, final String fieldName) {
		return callInterface(new CallExternalInterface<PledgeTextCustomInfo>() {
			
			@Override
			public PledgeTextCustomInfo call() {
				return pledgeTextCustomWebService.findByTypeIdAndFieldName(typeId, fieldName);
			}
		});
	}
	
}
