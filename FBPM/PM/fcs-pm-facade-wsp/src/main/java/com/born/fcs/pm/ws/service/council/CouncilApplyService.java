package com.born.fcs.pm.ws.service.council;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.enums.CompanyNameEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.info.council.CouncilApplyInfo;
import com.born.fcs.pm.ws.order.council.CouncilApplyOrder;
import com.born.fcs.pm.ws.order.council.CouncilApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 项目上会申请
 * 
 * 
 * @author Fei
 * 
 */
@WebService
public interface CouncilApplyService {
	
	/**
	 * 保存待上会项目
	 * @param order
	 * @return
	 */
	FcsBaseResult saveCouncilApply(CouncilApplyOrder order);
	
	/**
	 * 根据项目编号查询待上会项目
	 * @param formId 对应的表单ID
	 * @return 待上会项目
	 */
	List<CouncilApplyInfo> queryCouncilApplyByProjectCode(String projectCode);
	
	/**
	 * 根据申请ID查询待上会项目
	 * @param formId 对应的表单ID
	 * @return 待上会项目
	 */
	CouncilApplyInfo queryCouncilApplyByApplyId(long applyId);
	
	/**
	 * 分页查询待上会项目列表
	 * @param councilApplyOrder
	 * @return 待上会项目列表List
	 */
	QueryBaseBatchResult<CouncilApplyInfo> queryCouncilApply(	CouncilApplyQueryOrder councilApplyQueryOrder);
	
	/**
	 * 获取下一个可用的会议序列号
	 * @return
	 */
	String getAvailableCouncilCodeSeq(CompanyNameEnum company, CouncilTypeEnum councilType);
	
}
