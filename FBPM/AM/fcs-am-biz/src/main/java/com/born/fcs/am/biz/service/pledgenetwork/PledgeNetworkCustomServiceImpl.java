package com.born.fcs.am.biz.service.pledgenetwork;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.am.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.am.dal.dataobject.PledgeNetworkCustomDO;
import com.born.fcs.am.ws.info.pledgenetwork.PledgeNetworkCustomInfo;
import com.born.fcs.am.ws.service.pledgenetwork.PledgeNetworkCustomService;
import com.yjf.common.lang.beans.cglib.BeanCopier;

@Service("pledgeNetworkCustomService")
public class PledgeNetworkCustomServiceImpl extends
		BaseFormAutowiredDomainService implements PledgeNetworkCustomService {
	private PledgeNetworkCustomInfo convertDO2Info(PledgeNetworkCustomDO DO) {
		if (DO == null)
			return null;
		PledgeNetworkCustomInfo info = new PledgeNetworkCustomInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}

	@Override
	public PledgeNetworkCustomInfo findById(long textId) {
		PledgeNetworkCustomInfo info = null;
		if (textId > 0) {
			PledgeNetworkCustomDO DO = pledgeNetworkCustomDAO.findById(textId);
			info = convertDO2Info(DO);
		}
		return info;
	}

	@Override
	public int deleteById(long typeId) {
		int num = 0;
		if (typeId != 0) {
			num = pledgeNetworkCustomDAO.deleteById(typeId);
		}
		return num;

	}

	@Override
	public List<PledgeNetworkCustomInfo> findByTypeId(long typeId) {
		List<PledgeNetworkCustomInfo> networkInfoList = new ArrayList<PledgeNetworkCustomInfo>();
		if (typeId != 0) {
			List<PledgeNetworkCustomDO> listNetworkDO = pledgeNetworkCustomDAO
					.findByTypeId(typeId);
			for (PledgeNetworkCustomDO pledgeNetworkCustomDO : listNetworkDO) {
				PledgeNetworkCustomInfo networkInfo = convertDO2Info(pledgeNetworkCustomDO);
				networkInfoList.add(networkInfo);
			}
		}
		return networkInfoList;
	}
}
