package com.born.fcs.pm.biz.service.forCrm.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.service.base.BaseAutowiredDAOService;
import com.born.fcs.pm.dal.dataobject.ViewChannelProjectAllPhasDO;
import com.born.fcs.pm.dal.dataobject.ViewProjectIndirectCustomerDO;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.info.forCrm.IndirectCustomerInfo;
import com.born.fcs.pm.ws.info.forCrm.ViewChannelProjectAllPhasInfo;
import com.born.fcs.pm.ws.order.forCrm.IndirectCustomerQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.forCrm.CrmUseService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;

@Service("crmUseService")
public class CrmUseServiceImpl extends BaseAutowiredDAOService implements CrmUseService {
	
	private ViewChannelProjectAllPhasInfo convertDO2Info(ViewChannelProjectAllPhasDO DO) {
		if (DO == null)
			return null;
		ViewChannelProjectAllPhasInfo info = new ViewChannelProjectAllPhasInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	@Override
	public FcsBaseResult channalUsed(Long id) {
		FcsBaseResult result = new FcsBaseResult();
		if (id != null && id > 0) {
			ViewChannelProjectAllPhasDO viewChannelProjectAllPha = new ViewChannelProjectAllPhasDO();
			viewChannelProjectAllPha.setCapitalChannelId(id);
			List<ViewChannelProjectAllPhasDO> list = viewChannelProjectAllPhasDAO.findByCondition(
				viewChannelProjectAllPha, 0, 1);
			if (ListUtil.isNotEmpty(list)) {
				result.setSuccess(true);
				result.setMessage("该渠道已关联项目");
			} else {
				result.setSuccess(false);
				result.setMessage("该渠道未关联项目");
			}
		} else {
			result.setSuccess(false);
			result.setMessage("查询渠道Id不能为空");
		}
		
		return result;
	}
	
	@Override
	public QueryBaseBatchResult<IndirectCustomerInfo> queryIndirectCustomer(IndirectCustomerQueryOrder order) {
		QueryBaseBatchResult<IndirectCustomerInfo> result = new QueryBaseBatchResult<IndirectCustomerInfo>();
		ViewProjectIndirectCustomerDO info = new ViewProjectIndirectCustomerDO();
		BeanCopier.staticCopy(order, info);
		long count = viewProjectIndirectCustomerDAO.findByConditionCount(info);
		PageComponent pageComponent = new PageComponent(order, count);
		List<ViewProjectIndirectCustomerDO> list = viewProjectIndirectCustomerDAO.findByCondition(
			info, pageComponent.getFirstRecord(), pageComponent.getPageSize());
		if (ListUtil.isNotEmpty(list)) {
			IndirectCustomerInfo indirectCustomerInfo = null;
			List<IndirectCustomerInfo> pageList = new ArrayList<IndirectCustomerInfo>();
			for (ViewProjectIndirectCustomerDO dos : list) {
				indirectCustomerInfo = new IndirectCustomerInfo();
				BeanCopier.staticCopy(dos, indirectCustomerInfo);
				pageList.add(indirectCustomerInfo);
			}
			result.setPageList(pageList);
			
		}
		result.initPageParam(pageComponent);
		result.setSuccess(true);
		return result;
	}
	
	@Override
	public ViewChannelProjectAllPhasInfo queryChannelByprojectCodeAndPhas(String projectCode,
																			long phas) {
		ViewChannelProjectAllPhasDO DO = viewChannelProjectAllPhasDAO.findByProjectCodeAndPhase(
			projectCode, phas);
		return convertDO2Info(DO);
	}
}
