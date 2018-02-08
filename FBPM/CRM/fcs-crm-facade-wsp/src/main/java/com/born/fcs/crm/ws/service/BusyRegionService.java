package com.born.fcs.crm.ws.service;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.crm.ws.service.info.BusyRegionInfo;
import com.born.fcs.crm.ws.service.order.BusyRegionOrder;
import com.born.fcs.crm.ws.service.order.query.BusyRegionQueryOrder;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/** 业务部门区域维护 */
@WebService
public interface BusyRegionService {
	
	/** 保存信息 */
	FcsBaseResult save(BusyRegionOrder order);
	
	/** 区域列表查询 **/
	QueryBaseBatchResult<BusyRegionInfo> list(BusyRegionQueryOrder order);
	
	/** 修改启用状态 */
	FcsBaseResult changeStatus(String depPath, BooleanEnum status);
	
	/** 启用状态查询 */
	FcsBaseResult queryStatus(String depPath);
	
	/** 按部门查询区域 使用 */
	List<BusyRegionInfo> queryByDepPath(String depPath);
	
	/** 按部门查询区域 修改 */
	List<BusyRegionInfo> queryAllByDepPath(String depPath);
}
