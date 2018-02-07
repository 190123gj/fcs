package com.born.fcs.face.integration.am.service.pledgeasset;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.am.ws.info.pledgeasset.AssetRelationProjectInfo;
import com.born.fcs.am.ws.info.pledgeasset.AssetSimpleInfo;
import com.born.fcs.am.ws.info.pledgeasset.PledgeAssetInfo;
import com.born.fcs.am.ws.info.pledgeasset.PledgeTypeCommonInfo;
import com.born.fcs.am.ws.order.pledgeasset.AssetQueryOrder;
import com.born.fcs.am.ws.order.pledgeasset.AssetRelationProjectBindOrder;
import com.born.fcs.am.ws.order.pledgeasset.AssetRelationProjectQueryOrder;
import com.born.fcs.am.ws.order.pledgeasset.PledgeAssetOrder;
import com.born.fcs.am.ws.order.pledgeasset.PledgeAssetQueryOrder;
import com.born.fcs.am.ws.service.pledgeasset.PledgeAssetService;
import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * @author jil
 *
 */
@Service("pledgeAssetServiceClient")
public class PledgeTypeServiceClient extends ClientAutowiredBaseService implements
																		PledgeAssetService {
	
	@Override
	public PledgeAssetInfo findById(final long typeId) {
		return callInterface(new CallExternalInterface<PledgeAssetInfo>() {
			
			@Override
			public PledgeAssetInfo call() {
				return pledgeAssetWebService.findById(typeId);
			}
		});
	}
	
	@Override
	public FcsBaseResult save(final PledgeAssetOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return pledgeAssetWebService.save(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<PledgeAssetInfo> query(final PledgeAssetQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<PledgeAssetInfo>>() {
			
			@Override
			public QueryBaseBatchResult<PledgeAssetInfo> call() {
				return pledgeAssetWebService.query(order);
			}
		});
	}
	
	@Override
	public int deleteById(final long typeId) {
		return callInterface(new CallExternalInterface<Integer>() {
			
			@Override
			public Integer call() {
				return pledgeAssetWebService.deleteById(typeId);
			}
		});
	}
	
	@Override
	public PledgeTypeCommonInfo findAssetCommonByAssetsId(final long assetsId) {
		return callInterface(new CallExternalInterface<PledgeTypeCommonInfo>() {
			
			@Override
			public PledgeTypeCommonInfo call() {
				return pledgeAssetWebService.findAssetCommonByAssetsId(assetsId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<AssetSimpleInfo> queryAssetSimple(final AssetQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<AssetSimpleInfo>>() {
			
			@Override
			public QueryBaseBatchResult<AssetSimpleInfo> call() {
				return pledgeAssetWebService.queryAssetSimple(queryOrder);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<AssetRelationProjectInfo> queryAssetRelationProject(final AssetRelationProjectQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<AssetRelationProjectInfo>>() {
			
			@Override
			public QueryBaseBatchResult<AssetRelationProjectInfo> call() {
				return pledgeAssetWebService.queryAssetRelationProject(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult assetRelationProject(final AssetRelationProjectBindOrder bindOrder) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return pledgeAssetWebService.assetRelationProject(bindOrder);
			}
		});
	}
	
	@Override
	public List<AssetRelationProjectInfo> findRelationByCustomerId(final long customerId) {
		return callInterface(new CallExternalInterface<List<AssetRelationProjectInfo>>() {
			
			@Override
			public List<AssetRelationProjectInfo> call() {
				return pledgeAssetWebService.findRelationByCustomerId(customerId);
			}
		});
	}
	
	@Override
	public FcsBaseResult update(final long assetsId, final String ralateVideo) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return pledgeAssetWebService.update(assetsId, ralateVideo);
			}
		});
	}
}
