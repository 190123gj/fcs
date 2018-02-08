package com.born.fcs.pm.biz.service.forCrm.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.ws.service.CustomerService;
import com.born.fcs.crm.ws.service.enums.ProjecteStatusEnum;
import com.born.fcs.crm.ws.service.info.CustomerDetailInfo;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDAOService;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeApplyInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeRecordInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.formchange.FormCheckCanChangeOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.formchange.FormChangeProcessService;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

@Service("cutomerChangeProcessService")
public class CutomerChangeProcessImpl extends BaseAutowiredDAOService implements
																		FormChangeProcessService {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ProjectService projectService;
	
	@Override
	public FcsBaseResult processChange(FormChangeApplyInfo applyInfo, FormChangeRecordInfo record) {
		FcsBaseResult result = new FcsBaseResult();
		result.setSuccess(true);
		return result;
	}
	
	@Override
	public FcsBaseResult checkCanChange(FormCheckCanChangeOrder checkOrder) {
		FcsBaseResult result = new FcsBaseResult();
		if (checkOrder.getCustomerId() > 0) {
			CustomerDetailInfo customerDetailInfo = customerService.queryByUserId(checkOrder
				.getCustomerId());
			if (customerDetailInfo == null) {
				result.setSuccess(false);
				result.setMessage("客户系统中无此客户");
				return result;
			}
			if (customerDetailInfo.isCanChange()
				|| checkProjecteStatus(checkOrder.getCustomerId(),
					customerDetailInfo.getProjectStatus())) {
				result.setSuccess(true);
				result.setMessage("当前客户可以修改签报");
			} else {
				result.setSuccess(false);
				result.setMessage("当前客户不可以修改签报");
			}
		} else {
			result.setSuccess(false);
			result.setMessage("客户ID不能为空");
		}
		
		return result;
	}
	
	/**
	 * 客户上会状态
	 * @param nowStatus 当前状态
	 * @return 是否可签报
	 * */
	protected boolean checkProjecteStatus(long userId, String nowStatus) {
		
		boolean canChange = false;
		if (StringUtil.notEquals(nowStatus, ProjecteStatusEnum.IS.code())) {
			ProjectQueryOrder projectQueryOrder = new ProjectQueryOrder();
			projectQueryOrder.setCustomerId(userId);
			projectQueryOrder.setPageSize(100);
			QueryBaseBatchResult<ProjectInfo> project = projectService
				.queryProject(projectQueryOrder);
			if (project != null && project.isSuccess()
				&& ListUtil.isNotEmpty(project.getPageList())) {
				boolean is = false;
				for (ProjectInfo info : project.getPageList()) {
					//项目阶段
					ProjectPhasesEnum status = info.getPhases();
					if (status == ProjectPhasesEnum.CONTRACT_PHASES
						|| status == ProjectPhasesEnum.LOAN_USE_PHASES
						|| status == ProjectPhasesEnum.FUND_RAISING_PHASES
						|| status == ProjectPhasesEnum.AFTERWARDS_PHASES
						|| status == ProjectPhasesEnum.RECOVERY_PHASES
						|| status == ProjectPhasesEnum.FINISH_PHASES) {
						//上会后
						is = true;
						break;
					}
				}
				
				if (is) {
					canChange = true;
				}
				
			}
		} else {
			canChange = true;
		}
		return canChange;
		
	}
}
