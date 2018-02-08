package com.born.fcs.pm.ws.service.virtualproject;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.virtualproject.VirtualProjectDetailInfo;
import com.born.fcs.pm.ws.info.virtualproject.VirtualProjectInfo;
import com.born.fcs.pm.ws.order.virtualproject.VirtualProjectDeleteOrder;
import com.born.fcs.pm.ws.order.virtualproject.VirtualProjectOrder;
import com.born.fcs.pm.ws.order.virtualproject.VirtualProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 虚拟项目
 *
 *
 * @author wuzj
 *
 */
@WebService
public interface VirtualProjectService {
	
	/**
	 * 保存
	 * @param order
	 * @return
	 */
	FcsBaseResult save(VirtualProjectOrder order);
	
	/**
	 * 删除
	 * @param order
	 * @return
	 */
	FcsBaseResult delete(VirtualProjectDeleteOrder order);
	
	/**
	 * 列表查询
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<VirtualProjectInfo> query(VirtualProjectQueryOrder order);
	
	/**
	 * 根据ID查询
	 * @param virtualId
	 * @return
	 */
	VirtualProjectInfo findById(long virtualId);
	
	/***
	 * 根据虚拟项目编号查询
	 * @param projectCode
	 * @return
	 */
	VirtualProjectInfo findByProjectCode(String projectCode);
	
	/**
	 * 根据虚拟ID查询所选项目明细
	 * @param virtualId
	 * @return
	 */
	List<VirtualProjectDetailInfo> findDetailByVirtualId(long virtualId);
	
	/***
	 * 根据虚拟项目编号查询所选项目明细
	 * @param virtualProjectCode
	 * @return
	 */
	List<VirtualProjectDetailInfo> findDetailByVirtualProjectCode(String virtualProjectCode);
}
