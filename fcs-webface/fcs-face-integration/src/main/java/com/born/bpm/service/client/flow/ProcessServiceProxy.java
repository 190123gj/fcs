package com.born.bpm.service.client.flow;

public class ProcessServiceProxy implements com.born.bpm.service.client.flow.ProcessService {
	private String _endpoint = null;
	private com.born.bpm.service.client.flow.ProcessService processService = null;
	
	public ProcessServiceProxy() {
		_initProcessServiceProxy();
	}
	
	public ProcessServiceProxy(String endpoint) {
		_endpoint = endpoint;
		_initProcessServiceProxy();
	}
	
	private void _initProcessServiceProxy() {
		try {
			processService = (new com.born.bpm.service.client.flow.ProcessServiceImplServiceLocator())
				.getProcessServiceImplPort();
			if (processService != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub) processService)._setProperty(
						"javax.xml.rpc.service.endpoint.address", _endpoint);
				else
					_endpoint = (String) ((javax.xml.rpc.Stub) processService)
						._getProperty("javax.xml.rpc.service.endpoint.address");
			}
			
		} catch (javax.xml.rpc.ServiceException serviceException) {
		}
	}
	
	public String getEndpoint() {
		return _endpoint;
	}
	
	public void setEndpoint(String endpoint) {
		_endpoint = endpoint;
		if (processService != null)
			((javax.xml.rpc.Stub) processService)._setProperty(
				"javax.xml.rpc.service.endpoint.address", _endpoint);
		
	}
	
	public com.born.bpm.service.client.flow.ProcessService getProcessService() {
		if (processService == null)
			_initProcessServiceProxy();
		return processService;
	}
	
	@Override
	public java.lang.String getTaskByTaskId(java.lang.String taskId)
																	throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getTaskByTaskId(taskId);
	}
	
	@Override
	public java.lang.String getProCopyList(java.lang.String userId, java.lang.String json)
																							throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getProCopyList(userId, json);
	}
	
	@Override
	public java.lang.String getMyProcessRunByXml(java.lang.String xml)
																		throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getMyProcessRunByXml(xml);
	}
	
	@Override
	public java.lang.String defRecover(java.lang.String userId, java.lang.String json)
																						throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.defRecover(userId, json);
	}
	
	@Override
	public java.lang.String getProcessRunByRunId(java.lang.String runId)
																		throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getProcessRunByRunId(runId);
	}
	
	@Override
	public java.lang.String getDestNodeHandleUsers(java.lang.String taskId,
													java.lang.String destNodeId)
																				throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getDestNodeHandleUsers(taskId, destNodeId);
	}
	
	@Override
	public java.lang.String getCompletedMattersList(java.lang.String userId, java.lang.String json)
																									throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getCompletedMattersList(userId, json);
	}
	
	@Override
	public java.lang.String setTaskVars(java.lang.String xml) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.setTaskVars(xml);
	}
	
	@Override
	public java.lang.String getProcessOpinionByRunId(java.lang.String runId)
																			throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getProcessOpinionByRunId(runId);
	}
	
	@Override
	public java.lang.String getApprovalItems(java.lang.String taskId)
																		throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getApprovalItems(taskId);
	}
	
	@Override
	public java.lang.String taskEndProcess(java.lang.String userId, java.lang.String json)
																							throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.taskEndProcess(userId, json);
	}
	
	@Override
	public java.lang.String doNext(java.lang.String xml) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.doNext(xml);
	}
	
	@Override
	public java.lang.String addSignUsers(java.lang.String signUserIds, java.lang.String taskId)
																								throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.addSignUsers(signUserIds, taskId);
	}
	
	@Override
	public java.lang.String getPendingMattersList(java.lang.String userId, java.lang.String json)
																									throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getPendingMattersList(userId, json);
	}
	
	@Override
	public java.lang.String setDefOtherParam(java.lang.String defId, java.lang.String json)
																							throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.setDefOtherParam(defId, json);
	}
	
	@Override
	public java.lang.String canSelectedUser(java.lang.String taskId)
																	throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.canSelectedUser(taskId);
	}
	
	@Override
	public java.lang.String taskAssign(java.lang.String json) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.taskAssign(json);
	}
	
	@Override
	public java.lang.String isAllowAddSign(java.lang.String account, java.lang.String taskId)
																								throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.isAllowAddSign(account, taskId);
	}
	
	@Override
	public java.lang.String getTasksByAccount(java.lang.String xml) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getTasksByAccount(xml);
	}
	
	@Override
	public java.lang.String getTaskFormUrl(java.lang.String taskId) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getTaskFormUrl(taskId);
	}
	
	@Override
	public java.lang.String getBpmAllNode(java.lang.String defId) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getBpmAllNode(defId);
	}
	
	@Override
	public java.lang.String getProcessRunByTaskId(java.lang.String taskId)
																			throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getProcessRunByTaskId(taskId);
	}
	
	@Override
	public java.lang.String getTaskOutNodes(java.lang.String taskId)
																	throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getTaskOutNodes(taskId);
	}
	
	@Override
	public java.lang.String getProcessRun(java.lang.String xml) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getProcessRun(xml);
	}
	
	@Override
	public java.lang.String endProcessByTaskId(java.lang.String taskId)
																		throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.endProcessByTaskId(taskId);
	}
	
	@Override
	public java.lang.String getBpmDefinitionByDefId(java.lang.String defId)
																			throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getBpmDefinitionByDefId(defId);
	}
	
	@Override
	public java.lang.String getAlreadyMattersList(java.lang.String userId, java.lang.String json)
																									throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getAlreadyMattersList(userId, json);
	}
	
	@Override
	public java.lang.String getBpmDefinition(java.lang.String xml) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getBpmDefinition(xml);
	}
	
	@Override
	public java.lang.String getNextFlowNodes(java.lang.String taskId, java.lang.String account)
																								throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getNextFlowNodes(taskId, account);
	}
	
	@Override
	public java.lang.String saveNode(java.lang.String json) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.saveNode(json);
	}
	
	@Override
	public java.lang.String getVariablesByRunId(java.lang.String runId)
																		throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getVariablesByRunId(runId);
	}
	
	@Override
	public java.lang.String getStatusByRunidNodeId(java.lang.String runId, java.lang.String nodeId)
																									throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getStatusByRunidNodeId(runId, nodeId);
	}
	
	@Override
	public java.lang.String taskCountersign(java.lang.String userId, java.lang.String json)
																							throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.taskCountersign(userId, json);
	}
	
	@Override
	public java.lang.String getTaskNode(java.lang.String taskId) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getTaskNode(taskId);
	}
	
	@Override
	public java.lang.String setAgent(java.lang.String json) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.setAgent(json);
	}
	
	@Override
	public java.lang.String assignUsers(java.lang.String json) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.assignUsers(json);
	}
	
	@Override
	public java.lang.String getVariablesByTaskId(java.lang.String taskId)
																			throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getVariablesByTaskId(taskId);
	}
	
	@Override
	public java.lang.String start(java.lang.String xml) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.start(xml);
	}
	
	@Override
	public java.lang.String getFreeJump(java.lang.String taskId) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getFreeJump(taskId);
	}
	
	@Override
	public java.lang.String getTasksByRunId(java.lang.String runId) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getTasksByRunId(runId);
	}
	
	@Override
	public java.lang.String isSelectPath(java.lang.String taskId) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.isSelectPath(taskId);
	}
	
	@Override
	public java.lang.String getTasksByAccountGroupByProcessName(java.lang.String xml)
																						throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getTasksByAccountGroupByProcessName(xml);
	}
	
	@Override
	public java.lang.String getFinishTask(java.lang.String xml) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getFinishTask(xml);
	}
	
	@Override
	public java.lang.String isAllowBack(java.lang.String taskId) throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.isAllowBack(taskId);
	}
	
	@Override
	public java.lang.String getByBusinessKey(java.lang.String businessKey)
																			throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getByBusinessKey(businessKey);
	}
	
	@Override
	public java.lang.String getXml() throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getXml();
	}
	
	@Override
	public java.lang.String getTaskNameByTaskId(java.lang.String taskId)
																		throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getTaskNameByTaskId(taskId);
	}
	
	@Override
	public java.lang.String getProTransMattersList(java.lang.String userId, java.lang.String json)
																									throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getProTransMattersList(userId, json);
	}
	
	@Override
	public java.lang.String getNodesByDefKey(java.lang.String defKey)
																		throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getNodesByDefKey(defKey);
	}
	
	@Override
	public java.lang.String getFinishTaskDetailUrl(java.lang.String actInstId,
													java.lang.String nodeKey)
																				throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getFinishTaskDetailUrl(actInstId, nodeKey);
	}
	
	@Override
	public java.lang.String getAccordingMattersList(java.lang.String userId, java.lang.String json)
																									throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getAccordingMattersList(userId, json);
	}
	
	@Override
	public java.lang.String getProcessOpinionByActInstId(java.lang.String actInstId)
																					throws java.rmi.RemoteException {
		if (processService == null)
			_initProcessServiceProxy();
		return processService.getProcessOpinionByActInstId(actInstId);
	}
	
}