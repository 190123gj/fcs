package com.born.bpm.service.client.resources;

public class SystemResourcesServiceProxy implements com.born.bpm.service.client.resources.SystemResourcesService {
  private String _endpoint = null;
  private com.born.bpm.service.client.resources.SystemResourcesService systemResourcesService = null;
  
  public SystemResourcesServiceProxy() {
    _initSystemResourcesServiceProxy();
  }
  
  public SystemResourcesServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initSystemResourcesServiceProxy();
  }
  
  private void _initSystemResourcesServiceProxy() {
    try {
      systemResourcesService = (new com.born.bpm.service.client.resources.SystemResourcesServiceImplServiceLocator()).getSystemResourcesServiceImplPort();
      if (systemResourcesService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)systemResourcesService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)systemResourcesService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (systemResourcesService != null)
      ((javax.xml.rpc.Stub)systemResourcesService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.born.bpm.service.client.resources.SystemResourcesService getSystemResourcesService() {
    if (systemResourcesService == null)
      _initSystemResourcesServiceProxy();
    return systemResourcesService;
  }
  
  public java.lang.String getMenuResByAccount(java.lang.String sysAlias, java.lang.String account) throws java.rmi.RemoteException{
    if (systemResourcesService == null)
      _initSystemResourcesServiceProxy();
    return systemResourcesService.getMenuResByAccount(sysAlias, account);
  }
  
  public java.lang.String getMenuFunctionResByAccount(java.lang.String sysAlias, java.lang.String account) throws java.rmi.RemoteException{
    if (systemResourcesService == null)
      _initSystemResourcesServiceProxy();
    return systemResourcesService.getMenuFunctionResByAccount(sysAlias, account);
  }
  
  public java.lang.String getAllResByAccount(java.lang.String sysAlias, java.lang.String account) throws java.rmi.RemoteException{
    if (systemResourcesService == null)
      _initSystemResourcesServiceProxy();
    return systemResourcesService.getAllResByAccount(sysAlias, account);
  }
  
  public java.lang.String getRoleByFunc(java.lang.String sysAlias, java.lang.String func) throws java.rmi.RemoteException{
    if (systemResourcesService == null)
      _initSystemResourcesServiceProxy();
    return systemResourcesService.getRoleByFunc(sysAlias, func);
  }
  
  public java.lang.String getRoleByUrl(java.lang.String sysAlias, java.lang.String url) throws java.rmi.RemoteException{
    if (systemResourcesService == null)
      _initSystemResourcesServiceProxy();
    return systemResourcesService.getRoleByUrl(sysAlias, url);
  }
  
  
}