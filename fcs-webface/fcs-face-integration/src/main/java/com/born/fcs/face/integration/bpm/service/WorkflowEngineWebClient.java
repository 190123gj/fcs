package com.born.fcs.face.integration.bpm.service;

import java.util.List;

import com.born.fcs.face.integration.bpm.service.info.BpmFinishTaskInfo;
import com.born.fcs.face.integration.bpm.service.info.TaskTypeViewInfo;
import com.born.fcs.face.integration.bpm.service.info.WebNodeInfo;
import com.born.fcs.face.integration.bpm.service.info.WorkflowProcessLog;
import com.born.fcs.face.integration.bpm.service.order.TaskSearchOrder;
import com.born.fcs.face.integration.bpm.service.order.WorkflowBatchProcessOrder;
import com.born.fcs.face.integration.bpm.service.order.WorkflowBatchProcessTaskOrder;
import com.born.fcs.face.integration.bpm.service.result.WorkflowBatchProcessResult;
import com.born.fcs.pm.ws.order.bpm.TaskOpinion;
import com.born.fcs.pm.ws.order.bpm.WorkflowTaskInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

public interface WorkflowEngineWebClient {
	
	/**
	 * 获取流程节点
	 * @param taskId
	 * @return
	 */
	WebNodeInfo getTaskNode(String taskId);
	
	/**
	 * 查询代办任务
	 * @param taskSearchOrder
	 * @return
	 */
	QueryBaseBatchResult<WorkflowTaskInfo> getTasksByAccount(TaskSearchOrder taskSearchOrder);
	
	/**
	 * 查询审核历史
	 * @param actInstId
	 * @return
	 */
	QueryBaseBatchResult<WorkflowProcessLog> getProcessOpinionByActInstId(String actInstId);
	
	/**
	 * 查看任务
	 * @param actInstId
	 * @param taskId
	 * @return
	 */
	FcsBaseResult readTask(String actInstId, String taskId);
	
	/**
	 * 查询历史审核任务
	 * @param taskSearchOrder
	 * @return
	 */
	QueryBaseBatchResult<BpmFinishTaskInfo> getFinishTask(TaskSearchOrder taskSearchOrder);
	
	/**
	 * 获取步骤执行人
	 * @param instanceId
	 * @param nodeId
	 * @return
	 */
	List<TaskOpinion> getTaskUsers(String instanceId, String nodeId);
	
	/**
	 * 获取分类试图
	 * @param taskSearchOrder
	 * @return
	 */
	QueryBaseBatchResult<TaskTypeViewInfo> getTaskTypeView(TaskSearchOrder taskSearchOrder);
	
	/**
	 * 批量审核
	 * @param order
	 * @return
	 */
	WorkflowBatchProcessResult batchProcess(WorkflowBatchProcessOrder order);
	
	/***
	 * 代办任务批量审核
	 * @param order
	 * @return
	 */
	WorkflowBatchProcessResult batchProcessTask(WorkflowBatchProcessTaskOrder order);
}
