package com.born.fcs.pm.integration.am.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.am.ws.info.pledgetypeattribute.PledgeTypeAttributeImageInfo;
import com.born.fcs.am.ws.info.pledgetypeattribute.PledgeTypeAttributeInfo;
import com.born.fcs.am.ws.info.pledgetypeattribute.PledgeTypeAttributeNetworkInfo;
import com.born.fcs.am.ws.info.pledgetypeattribute.PledgeTypeAttributeTextInfo;
import com.born.fcs.am.ws.order.pledgetypeattribute.PledgeTypeAttributeImageQueryOrder;
import com.born.fcs.am.ws.order.pledgetypeattribute.PledgeTypeAttributeNetworkQueryOrder;
import com.born.fcs.am.ws.order.pledgetypeattribute.PledgeTypeAttributeOrder;
import com.born.fcs.am.ws.order.pledgetypeattribute.PledgeTypeAttributeQueryOrder;
import com.born.fcs.am.ws.order.pledgetypeattribute.PledgeTypeAttributeTextQueryOrder;
import com.born.fcs.am.ws.service.pledgetypeattribute.PledgeTypeAttributeService;
import com.born.fcs.pm.integration.common.CallExternalInterface;
import com.born.fcs.pm.integration.common.impl.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * @author jil
 *
 */
@Service("pledgeTypeAttributeServiceClient")
public class PledgeTypeAttributeServiceClient extends ClientAutowiredBaseService implements
																				PledgeTypeAttributeService {
	@Autowired
	PledgeTypeAttributeService pledgeTypeAttributeWebService;
	
	@Override
	public QueryBaseBatchResult<PledgeTypeAttributeTextInfo> queryAttributeText(final PledgeTypeAttributeTextQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<PledgeTypeAttributeTextInfo>>() {
			
			@Override
			public QueryBaseBatchResult<PledgeTypeAttributeTextInfo> call() {
				return pledgeTypeAttributeWebService.queryAttributeText(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<PledgeTypeAttributeImageInfo> queryAttributeImage(	final PledgeTypeAttributeImageQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<PledgeTypeAttributeImageInfo>>() {
			
			@Override
			public QueryBaseBatchResult<PledgeTypeAttributeImageInfo> call() {
				return pledgeTypeAttributeWebService.queryAttributeImage(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<PledgeTypeAttributeNetworkInfo> queryAttributeNetwork(	final PledgeTypeAttributeNetworkQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<PledgeTypeAttributeNetworkInfo>>() {
			
			@Override
			public QueryBaseBatchResult<PledgeTypeAttributeNetworkInfo> call() {
				return pledgeTypeAttributeWebService.queryAttributeNetwork(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<PledgeTypeAttributeInfo> query(	final PledgeTypeAttributeQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<PledgeTypeAttributeInfo>>() {
			
			@Override
			public QueryBaseBatchResult<PledgeTypeAttributeInfo> call() {
				return pledgeTypeAttributeWebService.query(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult save(final PledgeTypeAttributeOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return pledgeTypeAttributeWebService.save(order);
			}
		});
	}
	
	@Override
	public PledgeTypeAttributeInfo findByassetsIdAndAttributeKeyAndCustomType(	final long assetsId,
																				final String attributeKey,
																				final String customType) {
		return callInterface(new CallExternalInterface<PledgeTypeAttributeInfo>() {
			
			@Override
			public PledgeTypeAttributeInfo call() {
				return pledgeTypeAttributeWebService.findByassetsIdAndAttributeKeyAndCustomType(
					assetsId, attributeKey, customType);
			}
		});
	}
	
}
