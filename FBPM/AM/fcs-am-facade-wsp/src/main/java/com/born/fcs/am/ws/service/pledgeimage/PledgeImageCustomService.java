package com.born.fcs.am.ws.service.pledgeimage;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.am.ws.info.pledgeimage.PledgeImageCustomInfo;
import com.born.fcs.am.ws.order.pledgeimage.PledgeImageCustomQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 抵质押品-图像信息-自定义service
 *
 * @author Ji
 */
@WebService
public interface PledgeImageCustomService {
	
	/**
	 * 根据ID查询图像信息
	 * 
	 * @param imageId
	 * @return
	 */
	PledgeImageCustomInfo findById(long imageId);
	
	/**
	 * 根据typeId查询文字信息
	 * 
	 * @param typeId
	 * @return
	 */
	List<PledgeImageCustomInfo> findByTypeId(long typeId);
	
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
	QueryBaseBatchResult<PledgeImageCustomInfo> query(PledgeImageCustomQueryOrder order);
	
	/**
	 * 根据id删除图像信息
	 * 
	 * @param imageId
	 * @return
	 */
	int deleteById(long imageId);
	
}
