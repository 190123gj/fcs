package com.born.fcs.pm.ws.service.investigation;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.finvestigation.FInvestigationAssetReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCreditSchemePledgeAssetInfo;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationAssetReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationAssetReviewQueryOrder;
import com.born.fcs.pm.ws.order.finvestigation.UpdateInvestigationAssetReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.UpdateInvestigationCreditSchemePledgeAssetRemarkOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * 尽调-资产复评
 * 
 * @author lirz
 * 
 * 2016-9-19 下午4:59:54
 * 
 */
@WebService
public interface AssetReviewService {
	
	/**
	 * 保存
	 * @param order
	 * @return
	 */
	FcsBaseResult save(FInvestigationAssetReviewOrder order);
	
	/**
	 * 提交(成功后，不能再填写意见)
	 * @param order
	 * @return
	 */
	FcsBaseResult submit(UpdateInvestigationAssetReviewOrder order);
	
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
	FInvestigationAssetReviewInfo findById(long id);
	
	/**
	 * 查询列表
	 * @param queryOrder
	 * @return
	 */
	QueryBaseBatchResult<FInvestigationAssetReviewInfo> queryList(	FInvestigationAssetReviewQueryOrder queryOrder);
	
	/**
	 * 保存复评意见
	 * @param order
	 * @return
	 */
	FcsBaseResult saveRemark(UpdateInvestigationCreditSchemePledgeAssetRemarkOrder order);
	
	/**
	 * 根据id查询资产信息
	 * @param id
	 * @return
	 */
	FInvestigationCreditSchemePledgeAssetInfo findAssetInfoById(long id);
}
