package com.born.fcs.pm.ws.service.council;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.council.CouncilProjectVoteInfo;
import com.born.fcs.pm.ws.order.council.CouncilProjectVoteOrder;
import com.born.fcs.pm.ws.order.council.CouncilProjectVoteQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 项目投票
 *
 *
 * @author Fei
 *
 */
@WebService
public interface CouncilProjectVoteService {
	
	/**
	 * 保存初始投票记录
	 * @param voteOrder
	 * @return
	 */
	FcsBaseResult saveCouncilProjectVote(CouncilProjectVoteOrder voteOrder);
	
	/**
	 * 删除投票记录
	 * @param voteOrder
	 * @return
	 */
	FcsBaseResult destroyCouncilProjectVote(CouncilProjectVoteOrder voteOrder);
	
	/**
	 * 分页查询投票记录
	 * @param councilQueryOrder
	 * @return
	 */
	QueryBaseBatchResult<CouncilProjectVoteInfo> queryCouncilProjectVote(	CouncilProjectVoteQueryOrder councilProjectVoteQueryOrder);
	
	/**
	 * 查询某项目的投票记录
	 * @param councilApplyOrder
	 * @return 待上会项目列表List
	 */
	List<CouncilProjectVoteInfo> queryCouncilProjectVoteByProjectCode(String projectCode);
	
	/**
	 * 投票
	 * @param order
	 * @return
	 */
	FcsBaseResult updateCouncilProjectVote(CouncilProjectVoteOrder order);
	
	/**
	 * 催促还未投票的人
	 * @param order
	 * @return
	 */
	FcsBaseResult urgeVote(CouncilProjectVoteOrder order);
	
}
