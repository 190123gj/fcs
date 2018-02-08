package com.born.fcs.pm.biz.service.file;

import java.util.Map;

import com.born.fcs.pm.dal.dataobject.FFileOutputExtensionDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.file.FileService;
import com.google.common.collect.Maps;

/**
 * 文档出库展期流程处理
 *
 * @author heh
 *
 * 2017年7月8日13:33:27
 */
@Service("fileOutputExtensionApplyProcessService")
public class FileOutputExtensionProcessServiceImpl extends BaseProcessService {

	@Autowired
	FileService fileService;

	@Override
	public Map<String, String> makeMessageVar(FormInfo formInfo) {
		Map<String, String> vars = Maps.newHashMap();
		FFileOutputExtensionDO DO = fileOutputExtensionDAO.findByFormId(formInfo.getFormId());
		vars.put("项目编号", DO.getProjectCode());
		return vars;
	}

	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			FFileOutputExtensionDO DO = fileOutputExtensionDAO.findByFormId(order.getFormInfo().getFormId());
			//自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
					.getAttribute("startOrder");
			if (startOrder != null) {
				startOrder.setCustomTaskName(DO.getProjectName() + "-权利凭证出库展期申请");
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("权利凭证出库展期申请流程启动前置处理出错 ： {}", e);
		}
		return result;
	}

	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 启动流程后业务处理(BASE)

	}

	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 结束流程后业务处理(BASE)
		FFileOutputExtensionDO DO = fileOutputExtensionDAO.findByFormId(formInfo.getFormId());
		ProjectDO projectDO=projectDAO.findByProjectCode(DO.getProjectCode());
		projectDO.setRightVoucherExtenDate(DO.getExtensionDate());
		projectDAO.update(projectDO);
	}

	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 手动结束流程后业务处理(BASE)

	}
	@Override
	public void deleteAfterProcess(FormInfo formInfo) {

	}

}
