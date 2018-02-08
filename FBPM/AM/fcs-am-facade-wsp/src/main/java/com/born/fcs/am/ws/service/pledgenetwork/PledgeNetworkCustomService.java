package com.born.fcs.am.ws.service.pledgenetwork;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.am.ws.info.pledgenetwork.PledgeNetworkCustomInfo;

/**
 * 抵质押品-网络信息-自定义service
 *
 * @author Ji
 */
@WebService
public interface PledgeNetworkCustomService {

	/**
	 * 根据ID查询网络信息
	 * 
	 * @param typeId
	 * @return
	 */
	PledgeNetworkCustomInfo findById(long textId);

	/**
	 * 根据typeId查询文字信息
	 * 
	 * @param typeId
	 * @return
	 */
	List<PledgeNetworkCustomInfo> findByTypeId(long typeId);

	// /**
	// * 保存文字信息
	// *
	// * @param order
	// * @return
	// */
	// FcsBaseResult save(PledgeTextCustomOrder order);
	//
	// /**
	// * 查询文字信息
	// *
	// * @param order
	// * @return
	// */
	// QueryBaseBatchResult<PledgeTextCustomInfo> query(
	// PledgeTextCustomQueryOrder order);

	/**
	 * 根据id删除
	 * 
	 * @param typeId
	 * @return
	 */
	int deleteById(long textId);

}
