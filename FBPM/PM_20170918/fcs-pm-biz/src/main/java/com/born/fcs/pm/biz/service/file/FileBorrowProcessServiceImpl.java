package com.born.fcs.pm.biz.service.file;

import java.util.Date;
import java.util.Map;

import com.born.fcs.pm.ws.order.file.FileOrder;
import com.born.fcs.pm.ws.service.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.FFileBorrowDO;
import com.born.fcs.pm.dal.dataobject.FFileDO;
import com.born.fcs.pm.dal.dataobject.FFileInputListDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.ws.enums.FileStatusEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.google.common.collect.Maps;

/**
 * 文档借阅库流程处理
 *
 * @author heh
 *
 * 2016-4-23 下午1:46:05
 */
@Service("fileBorrowApplyProcessService")
public class FileBorrowProcessServiceImpl extends BaseProcessService {

	@Autowired
	FileService fileService;
	@Override
	public Map<String, String> makeMessageVar(FormInfo formInfo) {
		Map<String, String> vars = Maps.newHashMap();
		FFileBorrowDO borrowDO = fileBorrowDAO.findByFormId(formInfo.getFormId());
		vars.put("档案编号", borrowDO.getFileCode());
		return vars;
	}

	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			FFileBorrowDO DO = fileBorrowDAO.findByFormId(order.getFormInfo().getFormId());
			//自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
					.getAttribute("startOrder");
			if (startOrder != null) {
				startOrder.setCustomTaskName(DO.getProjectName() + "-借阅申请");
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("借阅申请流程启动前置处理出错 ： {}", e);
		}
		return result;
	}

	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 启动流程后业务处理(BASE)
		final Date nowDate = FcsPmDomainHolder.get().getSysDate();
		FFileBorrowDO DO = fileBorrowDAO.findByFormId(formInfo.getFormId());
		String ids[] = DO.getBorrowDetailId().split(",");
		for (String id : ids) {
			FFileInputListDO listDO = fileInputListDAO.findById(Long.parseLong(id));
			listDO.setStatus(FileStatusEnum.WAITING_BORROW.code());
			fileInputListDAO.update(listDO);
		}
		FFileDO fileDO = fileDAO.findById(DO.getFileId());
		DO.setHandOverManId(fileDO.getReceiveManId());
		DO.setHandOverMan(fileDO.getReceiveMan());
		DO.setHandOverTime(nowDate);
		DO.setHandOverDept(fileDO.getReceiveDept());
		fileBorrowDAO.update(DO);
		//更新档案项目操作时间
		updateTime(DO.getFileId());
	}

	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 结束流程后业务处理(BASE)
		final Date nowDate = FcsPmDomainHolder.get().getSysDate();
		SimpleUserInfo currentUser = (SimpleUserInfo) FcsPmDomainHolder.get().getAttribute(
				"currentUser");
		FFileBorrowDO DO = fileBorrowDAO.findByFormId(formInfo.getFormId());
		//        DO.setHandOverManId(Long.parseLong(currentUser.get("userId").toString()));
		//        DO.setHandOverMan(currentUser.get("userName").toString());
		//        DO.setHandOverTime(nowDate);
		//        DO.setHandOverDept(currentUser.get("deptName").toString());
		DO.setReceiveManId(workflowResult.getCreatorId());
		DO.setReceiveMan(workflowResult.getCreator());
		DO.setReceiveDept(workflowResult.getStartOrgName());
		DO.setReceiveTime(nowDate);
		fileBorrowDAO.update(DO);
		String ids[] = DO.getBorrowDetailId().split(",");
		for (String id : ids) {
			FFileInputListDO listDO = fileInputListDAO.findById(Long.parseLong(id));
			listDO.setStatus(FileStatusEnum.BORROW.code());
			listDO.setCurrBorrowManId(formInfo.getUserId());
			listDO.setCurrBorrowId(DO.getId());
			fileInputListDAO.update(listDO);
		}
		//更新档案项目操作时间
		updateTime(DO.getFileId());
	}

	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 手动结束流程后业务处理(BASE)
		FFileBorrowDO DO = fileBorrowDAO.findByFormId(formInfo.getFormId());
		String ids[] = DO.getBorrowDetailId().split(",");
		for (String id : ids) {
			FFileInputListDO listDO = fileInputListDAO.findById(Long.parseLong(id));
			listDO.setStatus(FileStatusEnum.INPUT.code());
			fileInputListDAO.update(listDO);
		}
		//更新档案项目操作时间
		updateTime(DO.getFileId());
	}

	@Override
	public void deleteAfterProcess(FormInfo formInfo) {
		FFileBorrowDO DO = fileBorrowDAO.findByFormId(formInfo.getFormId());
		String ids[] = DO.getBorrowDetailId().split(",");
		for (String id : ids) {
			FFileInputListDO listDO = fileInputListDAO.findById(Long.parseLong(id));
			listDO.setStatus(FileStatusEnum.INPUT.code());
			listDO.setCurrBorrowManId(0);
			listDO.setCurrBorrowId(0);
			fileInputListDAO.update(listDO);
		}
			//更新档案项目操作时间
			updateTime(DO.getFileId());
	}
	//更新档案项目操作时间
	public void updateTime(Long fileId){
		FileOrder fileOrder=new FileOrder();
		fileOrder.setFileId(fileId);
		fileService.updateFileProjectTime(fileOrder);
	}
}
