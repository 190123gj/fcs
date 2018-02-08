package com.born.fcs.fm.integration.bpm;

public class FlowServiceProxy implements com.born.fcs.fm.integration.bpm.FlowService {
  private String _endpoint = null;
  private com.born.fcs.fm.integration.bpm.FlowService flowService = null;
  
  public FlowServiceProxy() {
    _initFlowServiceProxy();
  }
  
  public FlowServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initFlowServiceProxy();
  }
  
  private void _initFlowServiceProxy() {
    try {
      flowService = (new com.born.fcs.fm.integration.bpm.FlowServiceImplServiceLocator()).getFlowServiceImplPort();
      if (flowService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)flowService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)flowService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (flowService != null)
      ((javax.xml.rpc.Stub)flowService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.born.fcs.fm.integration.bpm.FlowService getFlowService() {
    if (flowService == null)
      _initFlowServiceProxy();
    return flowService;
  }
  
  public java.lang.String getTaskUsersByInstanceIdAndNodeId(java.lang.String instanceId, java.lang.String nodeId) throws java.rmi.RemoteException{
    if (flowService == null)
      _initFlowServiceProxy();
    return flowService.getTaskUsersByInstanceIdAndNodeId(instanceId, nodeId);
  }
  
  public java.lang.String getMyFlowListJson(java.lang.String json) throws java.rmi.RemoteException{
    if (flowService == null)
      _initFlowServiceProxy();
    return flowService.getMyFlowListJson(json);
  }
  
  public java.lang.String getFlowTypeList() throws java.rmi.RemoteException{
    if (flowService == null)
      _initFlowServiceProxy();
    return flowService.getFlowTypeList();
  }
  
  public java.lang.String getTaskUsers(java.lang.String strTaskId) throws java.rmi.RemoteException{
    if (flowService == null)
      _initFlowServiceProxy();
    return flowService.getTaskUsers(strTaskId);
  }
  
  public java.lang.String delDraftByRunIds(java.lang.String runIds) throws java.rmi.RemoteException{
    if (flowService == null)
      _initFlowServiceProxy();
    return flowService.delDraftByRunIds(runIds);
  }
  
  public java.lang.String getAlreadyMattersListJson(java.lang.String json) throws java.rmi.RemoteException{
    if (flowService == null)
      _initFlowServiceProxy();
    return flowService.getAlreadyMattersListJson(json);
  }
  
  public java.lang.String getMyRequestListJson(java.lang.String json) throws java.rmi.RemoteException{
    if (flowService == null)
      _initFlowServiceProxy();
    return flowService.getMyRequestListJson(json);
  }
  
  public java.lang.String getMyCompletedListJson(java.lang.String json) throws java.rmi.RemoteException{
    if (flowService == null)
      _initFlowServiceProxy();
    return flowService.getMyCompletedListJson(json);
  }
  
  public java.lang.String getMyDraftListJson(java.lang.String json) throws java.rmi.RemoteException{
    if (flowService == null)
      _initFlowServiceProxy();
    return flowService.getMyDraftListJson(json);
  }
  
  public java.lang.String readTask(java.lang.String actInstId, java.lang.String taskId) throws java.rmi.RemoteException{
    if (flowService == null)
      _initFlowServiceProxy();
    return flowService.readTask(actInstId, taskId);
  }
  
  public java.lang.String saveDraftByForm(java.lang.String json) throws java.rmi.RemoteException, com.born.fcs.fm.integration.bpm.Exception{
    if (flowService == null)
      _initFlowServiceProxy();
    return flowService.saveDraftByForm(json);
  }
  
  
}