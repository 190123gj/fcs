package com.born.fcs.am.ws.service.pledgeasset;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.am.ws.info.pledgeasset.AssetRelationProjectInfo;
import com.born.fcs.am.ws.info.pledgeasset.AssetSimpleInfo;
import com.born.fcs.am.ws.info.pledgeasset.PledgeAssetInfo;
import com.born.fcs.am.ws.info.pledgeasset.PledgeTypeCommonInfo;
import com.born.fcs.am.ws.order.pledgeasset.AssetQueryOrder;
import com.born.fcs.am.ws.order.pledgeasset.AssetRelationProjectBindOrder;
import com.born.fcs.am.ws.order.pledgeasset.AssetRelationProjectQueryOrder;
import com.born.fcs.am.ws.order.pledgeasset.PledgeAssetOrder;
import com.born.fcs.am.ws.order.pledgeasset.PledgeAssetQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 资产列表service
 *
 * @author Ji
 */
@WebService
public interface PledgeAssetService {
	
	/**
	 * 根据ID查询资产信息
	 * 
	 * @param typeId
	 * @return
	 */
	PledgeAssetInfo findById(long typeId);
	
	/**
	 * 保存资产信息
	 * 
	 * @param order
	 * @return
	 */
	FcsBaseResult save(PledgeAssetOrder order);
	
	/**
	 * 查询资产信息
	 * 
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<PledgeAssetInfo> query(PledgeAssetQueryOrder order);
	
	/**
	 * 根据id删除资产信息
	 * 
	 * @param typeId
	 * @return
	 */
	int deleteById(long typeId);
	
	/**
	 * 根据id资产信息查询通用信息
	 * 
	 * @param assetsId
	 * @return
	 */
	PledgeTypeCommonInfo findAssetCommonByAssetsId(long assetsId);
	
	/**
	 * 尽调所需要列表
	 * 
	 * @param queryOrder
	 * @return
	 */
	QueryBaseBatchResult<AssetSimpleInfo> queryAssetSimple(AssetQueryOrder queryOrder);
	
	/**
	 * 查询资产关联项目信息
	 * 
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<AssetRelationProjectInfo> queryAssetRelationProject(	AssetRelationProjectQueryOrder order);
	
	/**
	 * 保存关联资产的项目信息
	 * 
	 * @param order
	 * @return
	 */
	FcsBaseResult assetRelationProject(AssetRelationProjectBindOrder bindOrder);
	
	/**
	 * 根据客户id查询关联的资产信息
	 * 
	 * @param customerId
	 * @return
	 */
	List<AssetRelationProjectInfo> findRelationByCustomerId(long customerId);
	
	/**
	 * 更新资产视频监控信息
	 * 
	 * @param assetsId
	 * @param ralateVideo
	 * @return
	 */
	FcsBaseResult update(long assetsId, String ralateVideo);
}
