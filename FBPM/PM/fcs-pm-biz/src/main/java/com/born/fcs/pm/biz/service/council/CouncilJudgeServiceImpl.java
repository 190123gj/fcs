package com.born.fcs.pm.biz.service.council;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.CouncilJudgeDO;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.council.CouncilJudgeInfo;
import com.born.fcs.pm.ws.service.council.CouncilJudgeService;
import com.yjf.common.lang.beans.cglib.BeanCopier;

@Service("councilJudgeService")
public class CouncilJudgeServiceImpl extends BaseAutowiredDomainService implements
																		CouncilJudgeService {
	
	@Override
	public List<CouncilJudgeInfo> queryByCouncilId(long councilId) {
		List<CouncilJudgeInfo> rtList = new ArrayList<CouncilJudgeInfo>();
		List<CouncilJudgeDO> list = councilJudgeDAO.findByCouncilId(councilId);
		for (CouncilJudgeDO item : list) {
			CouncilJudgeInfo info = new CouncilJudgeInfo();
			BeanCopier.staticCopy(item, info);
			info.setCompere(BooleanEnum.getByCode(item.getCompere()));
			
			rtList.add(info);
		}
		return rtList;
	}
}
