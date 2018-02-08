package com.born.fcs.pm.biz.service.riskwarning;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.dal.daointerface.RiskWarningSignalDAO;
import com.born.fcs.pm.dal.dataobject.RiskWarningSignalDO;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.SignalLevelEnum;
import com.born.fcs.pm.ws.enums.SignalTypeEnum;
import com.born.fcs.pm.ws.info.riskwarning.RiskWarningSignalInfo;
import com.born.fcs.pm.ws.order.riskwarning.RiskWarningSignalQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.riskwarning.RiskWarningSignalService;
import com.yjf.common.lang.beans.cglib.BeanCopier;

@Service("riskWarningSignalService")
public class RiskWarningSignalServiceImpl implements RiskWarningSignalService {
	
	@Autowired
	private RiskWarningSignalDAO riskWarningSignalDAO;
	
	@Override
	public QueryBaseBatchResult<RiskWarningSignalInfo> findByCondition(	RiskWarningSignalQueryOrder queryOrder) {
		QueryBaseBatchResult<RiskWarningSignalInfo> baseBatchResult = new QueryBaseBatchResult<>();
		RiskWarningSignalDO riskWarningSignal = new RiskWarningSignalDO();
		BeanCopier.staticCopy(queryOrder, riskWarningSignal);
		if (null != queryOrder.getSignalType()) {
			riskWarningSignal.setSignalType(queryOrder.getSignalType().code());
		}
		if (null != queryOrder.getSignalLevel()) {
			riskWarningSignal.setSignalLevel(queryOrder.getSignalLevel().code());
		}
		
		long totalSize = riskWarningSignalDAO.findByConditionCount(riskWarningSignal);
		PageComponent component = new PageComponent(queryOrder, totalSize);
		
		if (totalSize > 0) {
			List<RiskWarningSignalDO> pageList = riskWarningSignalDAO.findByCondition(
				riskWarningSignal, component.getFirstRecord(), component.getPageSize());
			
			List<RiskWarningSignalInfo> list = baseBatchResult.getPageList();
			for (RiskWarningSignalDO doObj : pageList) {
				RiskWarningSignalInfo info = new RiskWarningSignalInfo();
				BeanCopier.staticCopy(doObj, info);
				info.setSignalType(SignalTypeEnum.getByCode(doObj.getSignalType()));
				info.setSignalLevel(SignalLevelEnum.getByCode(doObj.getSignalLevel()));
				list.add(info);
			}
			baseBatchResult.initPageParam(component);
			baseBatchResult.setPageList(list);
		}
		
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}

	@Override
	public QueryBaseBatchResult<RiskWarningSignalInfo> findCompanySpecial() {
		RiskWarningSignalQueryOrder queryOrder = new RiskWarningSignalQueryOrder();
		queryOrder.setSignalType(SignalTypeEnum.COMPANY);
		queryOrder.setSignalLevel(SignalLevelEnum.SPECIAL);
		queryOrder.setPageNumber(1L);
		queryOrder.setPageSize(999L);
		return findByCondition(queryOrder);
	}

	@Override
	public QueryBaseBatchResult<RiskWarningSignalInfo> findCompanyNomal() {
		RiskWarningSignalQueryOrder queryOrder = new RiskWarningSignalQueryOrder();
		queryOrder.setSignalType(SignalTypeEnum.COMPANY);
		queryOrder.setSignalLevel(SignalLevelEnum.NOMAL);
		queryOrder.setPageNumber(1L);
		queryOrder.setPageSize(999L);
		return findByCondition(queryOrder);
	}
	
}
