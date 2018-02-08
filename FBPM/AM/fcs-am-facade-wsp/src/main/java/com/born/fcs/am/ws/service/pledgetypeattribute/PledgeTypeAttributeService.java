package com.born.fcs.am.ws.service.pledgetypeattribute;

import javax.jws.WebService;

import com.born.fcs.am.ws.info.pledgetypeattribute.PledgeTypeAttributeImageInfo;
import com.born.fcs.am.ws.info.pledgetypeattribute.PledgeTypeAttributeInfo;
import com.born.fcs.am.ws.info.pledgetypeattribute.PledgeTypeAttributeNetworkInfo;
import com.born.fcs.am.ws.info.pledgetypeattribute.PledgeTypeAttributeTextInfo;
import com.born.fcs.am.ws.order.pledgetypeattribute.PledgeTypeAttributeImageQueryOrder;
import com.born.fcs.am.ws.order.pledgetypeattribute.PledgeTypeAttributeNetworkQueryOrder;
import com.born.fcs.am.ws.order.pledgetypeattribute.PledgeTypeAttributeOrder;
import com.born.fcs.am.ws.order.pledgetypeattribute.PledgeTypeAttributeQueryOrder;
import com.born.fcs.am.ws.order.pledgetypeattribute.PledgeTypeAttributeTextQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 资产属性service
 *
 * @author Ji
 */
@WebService
public interface PledgeTypeAttributeService {
	
	/**
	 * 查询资产属性
	 * 
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<PledgeTypeAttributeInfo> query(PledgeTypeAttributeQueryOrder order);
	
	/**
	 * 根据ID查询分类信息
	 * 
	 * @param assetsId
	 * @param attributeKey
	 * @param customType
	 * @return
	 */
	PledgeTypeAttributeInfo findByassetsIdAndAttributeKeyAndCustomType(long assetsId,
																		String attributeKey,
																		String customType);
	
	/**
	 * 保存资产属性
	 * 
	 * @param order
	 * @return
	 */
	FcsBaseResult save(PledgeTypeAttributeOrder order);
	
	/**
	 * 查询资产属性 文字信息
	 * 
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<PledgeTypeAttributeTextInfo> queryAttributeText(	PledgeTypeAttributeTextQueryOrder order);
	
	/**
	 * 查询资产属性 图像信息
	 * 
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<PledgeTypeAttributeImageInfo> queryAttributeImage(	PledgeTypeAttributeImageQueryOrder order);
	
	/**
	 * 查询资产属性 网络信息
	 * 
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<PledgeTypeAttributeNetworkInfo> queryAttributeNetwork(	PledgeTypeAttributeNetworkQueryOrder order);
	
}
