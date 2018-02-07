package com.born.fcs.face.integration.pm.service.investigation;

import java.net.SocketTimeoutException;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationAssetReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCreditSchemePledgeAssetInfo;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationAssetReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationAssetReviewQueryOrder;
import com.born.fcs.pm.ws.order.finvestigation.UpdateInvestigationAssetReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.UpdateInvestigationCreditSchemePledgeAssetRemarkOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.investigation.AssetReviewService;

/**
 * 
 * 尽调-资产复评
 * 
 * @author lirz
 * 
 * 2016-9-19 下午6:32:00
 */
@Service("assetReviewServiceClient")
public class AssetReviewServiceClient extends ClientAutowiredBaseService implements
																		AssetReviewService {
	
	@Override
	public FcsBaseResult save(final FInvestigationAssetReviewOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() throws SocketTimeoutException {
				return assetReviewWebService.save(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult submit(final UpdateInvestigationAssetReviewOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() throws SocketTimeoutException {
				return assetReviewWebService.submit(order);
			}
		});
	}
	
	@Override
	public FInvestigationAssetReviewInfo findById(final long id) {
		return callInterface(new CallExternalInterface<FInvestigationAssetReviewInfo>() {
			
			@Override
			public FInvestigationAssetReviewInfo call() throws SocketTimeoutException {
				return assetReviewWebService.findById(id);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<FInvestigationAssetReviewInfo> queryList(	final FInvestigationAssetReviewQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FInvestigationAssetReviewInfo>>() {
			
			@Override
			public QueryBaseBatchResult<FInvestigationAssetReviewInfo> call()
																				throws SocketTimeoutException {
				return assetReviewWebService.queryList(queryOrder);
			}
		});
	}
	
	@Override
	public FcsBaseResult saveRemark(final UpdateInvestigationCreditSchemePledgeAssetRemarkOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() throws SocketTimeoutException {
				return assetReviewWebService.saveRemark(order);
			}
		});
	}

	@Override
	public FInvestigationCreditSchemePledgeAssetInfo findAssetInfoById(final long id) {
		return callInterface(new CallExternalInterface<FInvestigationCreditSchemePledgeAssetInfo>() {
			
			@Override
			public FInvestigationCreditSchemePledgeAssetInfo call() throws SocketTimeoutException {
				return assetReviewWebService.findAssetInfoById(id);
			}
		});
	}

}
