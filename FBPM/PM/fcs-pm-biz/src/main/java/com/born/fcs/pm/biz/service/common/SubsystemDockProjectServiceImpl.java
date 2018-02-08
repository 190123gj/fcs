package com.born.fcs.pm.biz.service.common;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.SubsystemDockProjectDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.enums.SubsystemDockFormTypeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.SubsystemDockProjectInfo;
import com.born.fcs.pm.ws.order.common.SubsystemDockProjectOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.common.SubsystemDockProjectService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("subsystemDockProjectService")
public class SubsystemDockProjectServiceImpl extends BaseAutowiredDomainService implements
																				SubsystemDockProjectService {
	private SubsystemDockProjectInfo convertDO2Info(SubsystemDockProjectDO DO) {
		if (DO == null)
			return null;
		SubsystemDockProjectInfo info = new SubsystemDockProjectInfo();
		BeanCopier.staticCopy(DO, info);
		info.setDockFormType(SubsystemDockFormTypeEnum.getByCode(DO.getDockFormType()));
		return info;
	}
	
	@Override
	protected FcsBaseResult createResult() {
		return new FcsBaseResult();
	}
	
	@Override
	public SubsystemDockProjectInfo findByProjectCodeAndDockFormType(String projectCode,
																		String dockFormType) {
		SubsystemDockProjectDO DO = subsystemDockProjectDAO.findByProjectCodeAndDockFormType(
			projectCode, dockFormType);
		return convertDO2Info(DO);
	}
	
	@Override
	public int deleteByProjectCodeAndDockFormType(String projectCode, String dockFormType) {
		int temp = subsystemDockProjectDAO.deleteByProjectCodeAndDockFormType(projectCode,
			dockFormType);
		return temp;
	}
	
	@Override
	public FcsBaseResult save(final SubsystemDockProjectOrder order) {
		return commonProcess(order, "保存子系统对接项目信息", new BeforeProcessInvokeService() {
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				SubsystemDockProjectDO subsystem = null;
				if (order.getId() != null && order.getId() > 0) {
					subsystem = subsystemDockProjectDAO.findById(order.getId());
					if (subsystem == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"子系统对接项目信息不存在");
					}
				}
				
				if (subsystem == null) { // 新增
					subsystem = new SubsystemDockProjectDO();
					BeanCopier.staticCopy(order, subsystem);
					subsystem.setRawAddTime(now);
					
					subsystemDockProjectDAO.insert(subsystem);// 主键
					
				} else { // 修改
					SubsystemDockProjectDO subsystemDO = subsystemDockProjectDAO.findById(order
						.getId());
					BeanCopier.staticCopy(order, subsystemDO);
					subsystemDockProjectDAO.update(subsystemDO);// 主键
				}
				
				FcsBaseResult result = (FcsBaseResult) FcsPmDomainHolder.get().getAttribute(
					"result");
				
				return null;
			}
		}, null, null);
	}
}
