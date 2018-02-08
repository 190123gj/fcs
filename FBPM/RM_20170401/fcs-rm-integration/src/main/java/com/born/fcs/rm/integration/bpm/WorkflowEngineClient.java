package com.born.fcs.rm.integration.bpm;

import java.util.List;

import com.born.fcs.rm.integration.bpm.result.StartFlowResult;
import com.born.fcs.rm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.ws.order.bpm.TaskNodeInfo;
import com.born.fcs.pm.ws.order.bpm.TaskOpinion;
import com.born.fcs.pm.ws.order.bpm.WorkflowAssignOrder;
import com.born.fcs.pm.ws.order.bpm.WorkflowDoNextOrder;
import com.born.fcs.pm.ws.order.bpm.WorkflowRecoverOrder;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.WorkflowTaskInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

public interface WorkflowEngineClient {
	
	StartFlowResult startFlow(WorkflowStartOrder startOrder);
	
	List<WorkflowTaskInfo> getTaskList(String runId);
	
	TaskNodeInfo getTaskNode(String taskId);
	
	WorkflowResult doNext(WorkflowDoNextOrder doNextOrder);
	
	WorkflowResult defRecover(WorkflowRecoverOrder recoverOrder);
	
	WorkflowResult taskAssign(WorkflowAssignOrder workflowAssignOrder);
	
	WorkflowResult endingWorkflow(WorkflowDoNextOrder doNextOrder);
	
	WorkflowTaskInfo getTaskInfo(String taskId);
	
	/**
	 * 获取已处理或当前任务的执行人
	 * @param strTaskId
	 * @return
	 */
	List<TaskOpinion> getTaskUsers(String strTaskId);
	
	/**
	 * 获取已处理或当前步骤执行人
	 * @param strTaskId
	 * @return
	 */
	List<TaskOpinion> getTaskUsers(String instanceId, String nodeId);
	
	FcsBaseResult readTask(String actInstId, String taskId);
	
}
