/**
 * ProcessService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.born.fcs.crm.integration.bpm.process;

public interface ProcessService extends java.rmi.Remote {
    public java.lang.String getTaskByTaskId(java.lang.String taskId) throws java.rmi.RemoteException;
    public java.lang.String getProCopyList(java.lang.String userId, java.lang.String json) throws java.rmi.RemoteException;
    public java.lang.String getMyProcessRunByXml(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String defRecover(java.lang.String userId, java.lang.String json) throws java.rmi.RemoteException;
    public java.lang.String getProcessRunByRunId(java.lang.String runId) throws java.rmi.RemoteException;
    public java.lang.String getDestNodeHandleUsers(java.lang.String taskId, java.lang.String destNodeId) throws java.rmi.RemoteException;
    public java.lang.String getCompletedMattersList(java.lang.String userId, java.lang.String json) throws java.rmi.RemoteException;
    public java.lang.String setTaskVars(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getProcessOpinionByRunId(java.lang.String runId) throws java.rmi.RemoteException;
    public java.lang.String getApprovalItems(java.lang.String taskId) throws java.rmi.RemoteException;
    public java.lang.String taskEndProcess(java.lang.String userId, java.lang.String json) throws java.rmi.RemoteException;
    public java.lang.String doNext(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String addSignUsers(java.lang.String signUserIds, java.lang.String taskId) throws java.rmi.RemoteException;
    public java.lang.String getPendingMattersList(java.lang.String userId, java.lang.String json) throws java.rmi.RemoteException;
    public java.lang.String setDefOtherParam(java.lang.String defId, java.lang.String json) throws java.rmi.RemoteException;
    public java.lang.String canSelectedUser(java.lang.String taskId) throws java.rmi.RemoteException;
    public java.lang.String taskAssign(java.lang.String json) throws java.rmi.RemoteException;
    public java.lang.String isAllowAddSign(java.lang.String account, java.lang.String taskId) throws java.rmi.RemoteException;
    public java.lang.String getTasksByAccount(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getTaskFormUrl(java.lang.String taskId) throws java.rmi.RemoteException;
    public java.lang.String getBpmAllNode(java.lang.String defId) throws java.rmi.RemoteException;
    public java.lang.String getProcessRunByTaskId(java.lang.String taskId) throws java.rmi.RemoteException;
    public java.lang.String getTaskOutNodes(java.lang.String taskId) throws java.rmi.RemoteException;
    public java.lang.String getProcessRun(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String endProcessByTaskId(java.lang.String taskId) throws java.rmi.RemoteException;
    public java.lang.String getBpmDefinitionByDefId(java.lang.String defId) throws java.rmi.RemoteException;
    public java.lang.String getAlreadyMattersList(java.lang.String userId, java.lang.String json) throws java.rmi.RemoteException;
    public java.lang.String getBpmDefinition(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getNextFlowNodes(java.lang.String taskId, java.lang.String account) throws java.rmi.RemoteException;
    public java.lang.String saveNode(java.lang.String json) throws java.rmi.RemoteException;
    public java.lang.String getVariablesByRunId(java.lang.String runId) throws java.rmi.RemoteException;
    public java.lang.String getStatusByRunidNodeId(java.lang.String runId, java.lang.String nodeId) throws java.rmi.RemoteException;
    public java.lang.String taskCountersign(java.lang.String userId, java.lang.String json) throws java.rmi.RemoteException;
    public java.lang.String getTaskNode(java.lang.String taskId) throws java.rmi.RemoteException;
    public java.lang.String setAgent(java.lang.String json) throws java.rmi.RemoteException;
    public java.lang.String assignUsers(java.lang.String json) throws java.rmi.RemoteException;
    public java.lang.String getVariablesByTaskId(java.lang.String taskId) throws java.rmi.RemoteException;
    public java.lang.String start(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getFreeJump(java.lang.String taskId) throws java.rmi.RemoteException;
    public java.lang.String getTasksByRunId(java.lang.String runId) throws java.rmi.RemoteException;
    public java.lang.String isSelectPath(java.lang.String taskId) throws java.rmi.RemoteException;
    public java.lang.String getFinishTask(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String isAllowBack(java.lang.String taskId) throws java.rmi.RemoteException;
    public java.lang.String getByBusinessKey(java.lang.String businessKey) throws java.rmi.RemoteException;
    public java.lang.String getXml() throws java.rmi.RemoteException;
    public java.lang.String getTaskNameByTaskId(java.lang.String taskId) throws java.rmi.RemoteException;
    public java.lang.String getProTransMattersList(java.lang.String userId, java.lang.String json) throws java.rmi.RemoteException;
    public java.lang.String getNodesByDefKey(java.lang.String defKey) throws java.rmi.RemoteException;
    public java.lang.String getFinishTaskDetailUrl(java.lang.String actInstId, java.lang.String nodeKey) throws java.rmi.RemoteException;
    public java.lang.String getAccordingMattersList(java.lang.String userId, java.lang.String json) throws java.rmi.RemoteException;
    public java.lang.String getProcessOpinionByActInstId(java.lang.String actInstId) throws java.rmi.RemoteException;
}
