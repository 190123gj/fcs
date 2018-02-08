package com.born.fcs.pm.biz.service.transfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.ws.enums.ProjectTransferStatusEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.service.common.ProjectTransferService;

/**
 * 项目移交审核处理
 *
 * @author wuzj
 */
@Service("projectTransferProcessService")
public class ProjectTransferProcessServiceImpl extends BaseProcessService {
	
	@Autowired
	ProjectTransferService projectTransferService;
	
	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		projectTransferService.updateStatusByFormId(ProjectTransferStatusEnum.TRANSFER_AUDITING,
			formInfo.getFormId());
	}
	
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		projectTransferService.updateStatusByFormId(ProjectTransferStatusEnum.TRANSFER_FAIL,
			formInfo.getFormId());
	}
	
	@Override
	public void selfEndProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		projectTransferService.updateStatusByFormId(ProjectTransferStatusEnum.TRANSFER_FAIL,
			formInfo.getFormId());
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		projectTransferService.doTransfer(formInfo.getFormId());
	}
}
