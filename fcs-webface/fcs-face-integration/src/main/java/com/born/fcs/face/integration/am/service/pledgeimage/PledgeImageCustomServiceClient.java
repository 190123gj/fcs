package com.born.fcs.face.integration.am.service.pledgeimage;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.am.ws.info.pledgeimage.PledgeImageCustomInfo;
import com.born.fcs.am.ws.order.pledgeimage.PledgeImageCustomQueryOrder;
import com.born.fcs.am.ws.service.pledgeimage.PledgeImageCustomService;
import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * @author jil
 *
 */
@Service("pledgeImageCustomServiceClient")
public class PledgeImageCustomServiceClient extends ClientAutowiredBaseService implements
																				PledgeImageCustomService {
	
	@Override
	public PledgeImageCustomInfo findById(final long imageId) {
		return callInterface(new CallExternalInterface<PledgeImageCustomInfo>() {
			
			@Override
			public PledgeImageCustomInfo call() {
				return pledgeImageCustomWebService.findById(imageId);
			}
		});
	}
	
	@Override
	public List<PledgeImageCustomInfo> findByTypeId(final long typeId) {
		return callInterface(new CallExternalInterface<List<PledgeImageCustomInfo>>() {
			
			@Override
			public List<PledgeImageCustomInfo> call() {
				return pledgeImageCustomWebService.findByTypeId(typeId);
			}
		});
	}
	
	@Override
	public int deleteById(final long imageId) {
		return callInterface(new CallExternalInterface<Integer>() {
			
			@Override
			public Integer call() {
				return pledgeImageCustomWebService.deleteById(imageId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<PledgeImageCustomInfo> query(final PledgeImageCustomQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<PledgeImageCustomInfo>>() {
			
			@Override
			public QueryBaseBatchResult<PledgeImageCustomInfo> call() {
				return pledgeImageCustomWebService.query(order);
			}
		});
	}
	
}
