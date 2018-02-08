package com.born.fcs.pm.ws.service.council;

import javax.jws.WebService;

import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.council.CouncilApplyRiskHandleInfo;
import com.born.fcs.pm.ws.info.council.FCouncilApplyRiskHandleInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.council.CouncilApplyRiskHandleQueryOrder;
import com.born.fcs.pm.ws.order.council.FCouncilApplyRiskHandleOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 项目上会申请 - 风险处置会/代偿项目上会
 * 
 * @author lirz
 *
 * 2016-4-19 下午3:44:54
 */
@WebService
public interface CouncilApplyRiskHandleService {
	
	/**
	 * 保存上会申报
	 * @param order
	 * @return
	 */
	FormBaseResult saveCouncilApplyRiskHandle(FCouncilApplyRiskHandleOrder order);
	
	/**
	 * 查询上会申报信息
	 * 
	 * @param id
	 * @return
	 */
	FCouncilApplyRiskHandleInfo findById(long id);
	
	/**
	 * 查询上会申报信息
	 * 
	 * @param fromId
	 * @return
	 */
	FCouncilApplyRiskHandleInfo findByFormId(long fromId);
	
	/**
	 * 分页查询待上会项目列表
	 * @param councilApplyOrder
	 * @return 待上会项目列表List
	 */
	QueryBaseBatchResult<CouncilApplyRiskHandleInfo> queryList(CouncilApplyRiskHandleQueryOrder queryOrder);
	
	/**
	 * 查询可代偿上会的项目
	 * 
	 * @param queryOrder
	 * @return
	 */
	QueryBaseBatchResult<ProjectInfo> queryProject(QueryProjectBase queryOrder);
	/**
	 * 查询非可代偿上会的项目
	 * 
	 * @param queryOrder
	 * @return
	 */
	QueryBaseBatchResult<ProjectInfo> queryNoRepayProject(ProjectQueryOrder queryOrder);
}
