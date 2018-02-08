package com.born.fcs.pm.biz.service.managerbchange;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.FManagerbChangeApplyDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.enums.managerbchange.ChangeManagerbStatusEnum;
import com.born.fcs.pm.ws.enums.managerbchange.ChangeManagerbWayEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

@Service("managerbChangeProcessService")
public class ManagerbChangeProcessServiceImpl extends BaseProcessService {
	
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		try {
			FManagerbChangeApplyDO apply = FManagerbChangeApplyDAO.findByFormId(formInfo
				.getFormId());
			apply.setStatus(ChangeManagerbStatusEnum.DENY.code());
		} catch (Exception e) {
			logger.error("更换B角终止流程处理[修改申请单状态]出错,{}", e);
		}
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		try {
			FManagerbChangeApplyDO applyDO = FManagerbChangeApplyDAO.findByFormId(formInfo
				.getFormId());
			applyDO.setStatus(ChangeManagerbStatusEnum.APPROVAL.code());
			
			Date now = new Date();
			//直接变更 或者到达时间段变更
			if (StringUtil.equals(applyDO.getChangeWay(), ChangeManagerbWayEnum.DIRECT.code())
				|| (StringUtil.equals(applyDO.getChangeWay(), ChangeManagerbWayEnum.PERIOD.code()) && now
					.after(applyDO.getChangeStartTime()))) {
				ProjectRelatedUserOrder changeOrder = new ProjectRelatedUserOrder();
				changeOrder.setProjectCode(applyDO.getProjectCode());
				changeOrder.setUserId(applyDO.getNewBid());
				changeOrder.setUserName(applyDO.getNewBname());
				changeOrder.setUserAccount(applyDO.getNewBaccount());
				changeOrder.setUserType(ProjectRelatedUserTypeEnum.BUSI_MANAGERB);
				changeOrder.setDelOrinigal(true);
				changeOrder.setTransferTime(now);
				changeOrder.setRemark(formInfo.getUserName() + "申请变更B角");
				FcsBaseResult changeResult = projectRelatedUserService.setRelatedUser(changeOrder);
				if (!changeResult.isSuccess()) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL, "变更B角失败");
				}
				ProjectDO project = projectDAO.findByProjectCode(applyDO.getProjectCode());
				if (project != null) {
					project.setBusiManagerbId(applyDO.getNewBid());
					project.setBusiManagerbAccount(applyDO.getNewBaccount());
					project.setBusiManagerbName(applyDO.getNewBname());
					projectDAO.update(project);
				}
				
				if ((StringUtil.equals(applyDO.getChangeWay(), ChangeManagerbWayEnum.PERIOD.code()))) {
					applyDO.setStatus(ChangeManagerbStatusEnum.WAIT_RESTORE.code());
				} else {
					applyDO.setStatus(ChangeManagerbStatusEnum.APPLIED.code());
				}
			}
			FManagerbChangeApplyDAO.update(applyDO);
		} catch (Exception e) {
			logger.error("更换B角流程结束处理[修改申请单状态及执行B角变更]出错,{}", e);
		}
	}
}
