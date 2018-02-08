/**
 * 
 */
package com.born.fcs.pm.biz.service.contract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FProjectContractExtraValueDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.contract.ProjectContractExtraValueInfo;
import com.born.fcs.pm.ws.order.contract.ProjectContractExtraValueBatchOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.contract.ProjectContractExtraValueInfoResult;
import com.born.fcs.pm.ws.service.contract.ProjectContractExtraValueService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * @author jiajie
 * 
 */
@Service("projectContractExtraValueService")
public class ProjectContractExtraValueServiceImpl extends BaseAutowiredDomainService implements
																					ProjectContractExtraValueService {
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.born.fcs.pm.ws.service.contract.ProjectContractExtraValueService#
	 * extraValueSave
	 * (com.born.fcs.pm.ws.order.contract.ProjectContractExtraValueBatchOrder)
	 */
	@Override
	public FcsBaseResult extraValueSave(final ProjectContractExtraValueBatchOrder order) {
		return commonProcess(order, "保存合同额外信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				if (order.getContractItemId() <= 0) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
						"合同信息参数不正确【id=" + order.getContractItemId() + "】");
				}
				// 添加判断，如果没有参数信息，直接跳转
				if (ListUtil.isEmpty(order.getProjectContractExtraValue())) {
					return null;
				}
				// 获取时间
				final Date now = FcsPmDomainHolder.get().getSysDate();
				projectContractExtraMessageSave(order, now, false);
				return null;
			}
			
		}, null, null);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.born.fcs.pm.ws.service.contract.ProjectContractExtraValueService#
	 * findByContractItemId(java.lang.Long)
	 */
	@Override
	public ProjectContractExtraValueInfoResult findByContractItemId(Long id) {
		ProjectContractExtraValueInfoResult result = new ProjectContractExtraValueInfoResult();
		List<ProjectContractExtraValueInfo> values = new ArrayList<ProjectContractExtraValueInfo>();
		if (id <= 0) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "合同信息参数不正确【id="
																				+ id + "】");
		}
		List<FProjectContractExtraValueDO> infos = fProjectContractExtraValueDAO
			.findByContractItemId(id);
		// if (ListUtil.isEmpty(infos)) {
		// throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
		// "未查询到指定合同数据");
		// }
		if (ListUtil.isNotEmpty(infos)) {
			for (FProjectContractExtraValueDO infoDO : infos) {
				ProjectContractExtraValueInfo info = new ProjectContractExtraValueInfo();
				MiscUtil.copyPoObject(info, infoDO);
				values.add(info);
			}
		}
		result.setSuccess(true);
		return result;
	}
	
}
