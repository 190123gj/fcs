package com.born.fcs.face.integration.am.service.pledgenetwork;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.am.ws.info.pledgenetwork.PledgeNetworkCustomInfo;
import com.born.fcs.am.ws.service.pledgenetwork.PledgeNetworkCustomService;
import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;

/**
 * 
 * @author jil
 *
 */
@Service("pledgeNetworkCustomServiceClient")
public class PledgeNetworkCustomServiceClient extends ClientAutowiredBaseService implements
																				PledgeNetworkCustomService {
	
	@Override
	public PledgeNetworkCustomInfo findById(final long textId) {
		return callInterface(new CallExternalInterface<PledgeNetworkCustomInfo>() {
			
			@Override
			public PledgeNetworkCustomInfo call() {
				return pledgeNetworkCustomWebService.findById(textId);
			}
		});
	}
	
	@Override
	public List<PledgeNetworkCustomInfo> findByTypeId(final long typeId) {
		return callInterface(new CallExternalInterface<List<PledgeNetworkCustomInfo>>() {
			
			@Override
			public List<PledgeNetworkCustomInfo> call() {
				return pledgeNetworkCustomWebService.findByTypeId(typeId);
			}
		});
	}
	
	@Override
	public int deleteById(final long textId) {
		return callInterface(new CallExternalInterface<Integer>() {
			
			@Override
			public Integer call() {
				return pledgeNetworkCustomWebService.deleteById(textId);
			}
		});
	}
	
}
