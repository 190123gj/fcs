package com.born.fcs.am.ws.service.pledgetext;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.am.ws.info.pledgetext.PledgeTextCustomInfo;
import com.born.fcs.am.ws.order.pledgetext.PledgeTextCustomQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 抵质押品-文字信息-自定义service
 *
 * @author Ji
 */
@WebService
public interface PledgeTextCustomService {
	
	/**
	 * 根据ID查询文字信息
	 * 
	 * @param textId
	 * @return
	 */
	PledgeTextCustomInfo findById(long textId);
	
	/**
	 * 根据ID查询文字信息
	 * 
	 * @param typeId
	 * @return
	 */
	PledgeTextCustomInfo findByTypeIdAndFieldName(long typeId, String fieldName);
	
	/**
	 * 根据typeId查询文字信息
	 * 
	 * @param typeId
	 * @return
	 */
	List<PledgeTextCustomInfo> findByTypeId(long typeId);
	
	// /**
	// * 保存文字信息
	// *
	// * @param order
	// * @return
	// */
	// FcsBaseResult save(PledgeTextCustomOrder order);
	//
	/**
	 * 查询文字信息
	 *
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<PledgeTextCustomInfo> query(PledgeTextCustomQueryOrder order);
	
	/**
	 * 根据id删除文字信息
	 * 
	 * @param typeId
	 * @return
	 */
	int deleteById(long textId);
	
	/**
	 * 根据typeId查询文字信息 多行文本
	 * 
	 * @param typeId
	 * @return
	 */
	List<PledgeTextCustomInfo> findMtextByTypeId(long typeId);
	
	/**
	 * 根据typeId查询文字信息 无多行文本
	 * 
	 * @param typeId
	 * @return
	 */
	List<PledgeTextCustomInfo> findNotMtextByTypeId(long typeId);
	
}
