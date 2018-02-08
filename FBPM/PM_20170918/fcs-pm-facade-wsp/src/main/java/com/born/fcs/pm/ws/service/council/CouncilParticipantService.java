package com.born.fcs.pm.ws.service.council;

import java.util.List;

import com.born.fcs.pm.ws.info.council.CouncilParticipantInfo;

public interface CouncilParticipantService {
	
	/**
	 * 获取会议列席人员列表
	 * @param councilId
	 * @return
	 */
	List<CouncilParticipantInfo> queryByCouncilId(long councilId);
	
}
