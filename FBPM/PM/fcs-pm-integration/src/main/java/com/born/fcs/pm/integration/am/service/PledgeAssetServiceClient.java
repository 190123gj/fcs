package com.born.fcs.pm.integration.am.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.born.fcs.pm.integration.common.CallExternalInterface;
import com.born.fcs.pm.integration.common.impl.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Service("pledgeAssetServiceClient")
public class PledgeAssetServiceClient extends ClientAutowiredBaseService implements
																		PledgeAssetService {
	@Autowired
	PledgeAssetService pledgeAssetWebService;
	
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
	
	/**
	 * // 拟抵（质）押的资产：在尽职调查报告中的资产信息,到授信条件落实审核通过前 ，为拟抵（质）押的资产；
	 * 说明：一个资产的状态不可能同时存在该表中的情况：当项目为抵债资产了，不能与其他状态共存，
	 * 即资产状态一旦变成抵债资产，则不能在尽职调查报告中选择重新关联新的项目了； 已转让的资产不能在新的尽职调查报告中关联到项目中；
	 * QUASI_MORTGAGE_PLEDGE("QUASI_MORTGAGE_PLEDGE", "拟抵（质）押"), //
	 * 已落实授信条件，授信条件落实单据审核通过 SECURED_MORTGAGE("SECURED_MORTGAGE", "已办理抵押"), //
	 * 已落实授信条件，授信条件落实单据审核通过 SECURED_PLEDGE("SECURED_PLEDGE", "已办理质押"), //
	 * 解保申请单审核通过； SOLUTION("SOLUTION", "已解保"), //
	 * 该项目在追偿跟踪表中标记为抵债资产的；该资产不能做重复的抵质押； DEBT_ASSET("DEBT_ASSET", "抵债资产"), //
	 * 在资产转让申请单中，该资产关联的项目为转让的，（转让确认的条件：该项目不上会，转让申请单审核通过； //
	 * 或者该项目转让需要上会，上会的会议纪要审核通过）； // 当资产受让 中流程通过后，该资产恢复为抵债资产；
	 * TRANSFERRED("TRANSFERRED", "已转让"), NOT_USED("NOT_USED", "未使用");
	 */
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
