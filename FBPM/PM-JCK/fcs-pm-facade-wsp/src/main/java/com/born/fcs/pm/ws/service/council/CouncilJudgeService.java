package com.born.fcs.pm.ws.service.council;

import java.util.List;

import com.born.fcs.pm.ws.info.council.CouncilJudgeInfo;

public interface CouncilJudgeService {
	
	/**
	 * 参会评委
	 * @param councilId
	 * @return
	 */
	List<CouncilJudgeInfo> queryByCouncilId(long councilId);
}
