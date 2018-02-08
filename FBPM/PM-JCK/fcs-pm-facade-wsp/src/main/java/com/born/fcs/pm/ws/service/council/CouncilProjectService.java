package com.born.fcs.pm.ws.service.council;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.council.CouncilProjectInfo;
import com.born.fcs.pm.ws.info.council.CouncilProjectVoteResultInfo;
import com.born.fcs.pm.ws.order.council.CouncilProjectOrder;
import com.born.fcs.pm.ws.order.council.CouncilVoteProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * 
 * 某次会议讨论涉及的项目
 * @author Fei
 * 
 */
@WebService
public interface CouncilProjectService {
	
	/**
	 * 获取会议讨论项目列表
	 * @param councilId
	 * @return
	 */
	List<CouncilProjectInfo> queryByCouncilId(long councilId);
	
	/**
	 * 获取会议讨论项目（项目信息更完备）
	 * @param councilId
	 * @return
	 */
	CouncilProjectVoteResultInfo query(long councilId, String projectCode);
	
	/**
	 * 一票否决
	 * @param councilId
	 * @return
	 */
	FcsBaseResult oneVoteDown(CouncilProjectOrder order);
	
	/**
	 * 获取会议讨论项目列表（项目信息更完备）
	 * @param councilId
	 * @return
	 */
	List<CouncilProjectVoteResultInfo> queryProjectVoteResultByCouncilId(long councilId);
	
	/**
	 * 分页获取会议讨论项目列表（项目信息更完备）
	 * @param councilId
	 * @return
	 */
	QueryBaseBatchResult<CouncilProjectVoteResultInfo> queryProjectVoteResult(	CouncilVoteProjectQueryOrder order);
	
	/**
	 * 根据申请id或者此项目最新的信息
	 * @param applyId
	 * @return
	 */
	CouncilProjectInfo getLastInfoByApplyId(Long applyId);
	
	/**
	 * 保存 主持人本次不议后续信息
	 * @param order
	 * @return
	 */
	FcsBaseResult saveCompereAfterMessage(CouncilProjectOrder order);
}
