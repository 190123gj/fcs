package com.born.bpm.service.client.org;

public class UserOrgServiceProxy implements com.born.bpm.service.client.org.UserOrgService {
  private String _endpoint = null;
  private com.born.bpm.service.client.org.UserOrgService userOrgService = null;
  
  public UserOrgServiceProxy() {
    _initUserOrgServiceProxy();
  }
  
  public UserOrgServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initUserOrgServiceProxy();
  }
  
  private void _initUserOrgServiceProxy() {
    try {
      userOrgService = (new com.born.bpm.service.client.org.UserOrgServiceImplServiceLocator()).getUserOrgServiceImplPort();
      if (userOrgService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)userOrgService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)userOrgService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (userOrgService != null)
      ((javax.xml.rpc.Stub)userOrgService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.born.bpm.service.client.org.UserOrgService getUserOrgService() {
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService;
  }
  
  public java.lang.String removeUserOrgRel(java.lang.String userAccount, java.lang.String orgCode) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.removeUserOrgRel(userAccount, orgCode);
  }
  
  public java.lang.String addUserRoleRel(java.lang.String userAccount, java.lang.String roleAlias) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.addUserRoleRel(userAccount, roleAlias);
  }
  
  public java.lang.String addOrUpdatePos(java.lang.String posInfo) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.addOrUpdatePos(posInfo);
  }
  
  public java.lang.String addOrUpdateJob(java.lang.String jobInfo) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.addOrUpdateJob(jobInfo);
  }
  
  public java.lang.String deleteParam(java.lang.String paramKey) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.deleteParam(paramKey);
  }
  
  public java.lang.String addUserPosRel(java.lang.String userAccount, java.lang.String posCode, java.lang.String orgType, java.lang.String posType) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.addUserPosRel(userAccount, posCode, orgType, posType);
  }
  
  public java.lang.String addOrUpdateUser(java.lang.String userInfo) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.addOrUpdateUser(userInfo);
  }
  
  public java.lang.String addUserOrgRel(java.lang.String userAccount, java.lang.String orgCode, java.lang.String userPerms, java.lang.String orgPerms, java.lang.String roleAlias) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.addUserOrgRel(userAccount, orgCode, userPerms, orgPerms, roleAlias);
  }
  
  public java.lang.String removeUserRel(java.lang.String upUserAccount, java.lang.String downUserAccount) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.removeUserRel(upUserAccount, downUserAccount);
  }
  
  public java.lang.String deleteOrg(java.lang.String code) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.deleteOrg(code);
  }
  
  public java.lang.String removeUserPosRel(java.lang.String userAccount, java.lang.String posCode) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.removeUserPosRel(userAccount, posCode);
  }
  
  public java.lang.String addUserRel(java.lang.String upUserAccount, java.lang.String downUserAccount) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.addUserRel(upUserAccount, downUserAccount);
  }
  
  public java.lang.String addOrUpdateRole(java.lang.String roleInfo) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.addOrUpdateRole(roleInfo);
  }
  
  public java.lang.String editParamValue(java.lang.String type, java.lang.String key, java.lang.String paramValue) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.editParamValue(type, key, paramValue);
  }
  
  public java.lang.String deleteJob(java.lang.String jobcode) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.deleteJob(jobcode);
  }
  
  public java.lang.String removeUserRoleRel(java.lang.String userAccount, java.lang.String roleAlias) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.removeUserRoleRel(userAccount, roleAlias);
  }
  
  public java.lang.String removeOrgRoleRel(java.lang.String orgCode, java.lang.String roleAlias) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.removeOrgRoleRel(orgCode, roleAlias);
  }
  
  public java.lang.String deleteRole(java.lang.String alias) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.deleteRole(alias);
  }
  
  public java.lang.String deletePos(java.lang.String posCode) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.deletePos(posCode);
  }
  
  public java.lang.String addOrUpdateOrg(java.lang.String orgInfo) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.addOrUpdateOrg(orgInfo);
  }
  
  public java.lang.String addOrUpdateParam(java.lang.String paramInfo) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.addOrUpdateParam(paramInfo);
  }
  
  public java.lang.String getParam() throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.getParam();
  }
  
  public java.lang.String addOrgRoleRel(java.lang.String orgCode, java.lang.String roleAlias) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.addOrgRoleRel(orgCode, roleAlias);
  }
  
  public java.lang.String deleteUser(java.lang.String account) throws java.rmi.RemoteException{
    if (userOrgService == null)
      _initUserOrgServiceProxy();
    return userOrgService.deleteUser(account);
  }
  
  
}