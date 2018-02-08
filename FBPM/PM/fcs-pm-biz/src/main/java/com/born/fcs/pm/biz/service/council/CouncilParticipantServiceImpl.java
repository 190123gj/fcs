package com.born.fcs.pm.biz.service.council;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.CouncilParticipantDO;
import com.born.fcs.pm.ws.info.council.CouncilParticipantInfo;
import com.born.fcs.pm.ws.service.council.CouncilParticipantService;
import com.yjf.common.lang.beans.cglib.BeanCopier;

@Service("councilParticipantService")
public class CouncilParticipantServiceImpl extends BaseAutowiredDomainService implements
																				CouncilParticipantService {
	
	@Override
	public List<CouncilParticipantInfo> queryByCouncilId(long councilId) {
		List<CouncilParticipantInfo> rtList = new ArrayList<CouncilParticipantInfo>();
		List<CouncilParticipantDO> list = councilParticipantDAO.findByCouncilId(councilId);
		for (CouncilParticipantDO item : list) {
			CouncilParticipantInfo info = new CouncilParticipantInfo();
			BeanCopier.staticCopy(item, info);
			rtList.add(info);
		}
		return rtList;
	}
	
}
