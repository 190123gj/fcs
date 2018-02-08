package com.born.fcs.am.intergration.bpm.service.client.user;

public class UserDetailsServiceProxy implements com.born.fcs.am.intergration.bpm.service.client.user.UserDetailsService {
  private String _endpoint = null;
  private com.born.fcs.am.intergration.bpm.service.client.user.UserDetailsService userDetailsService = null;
  
  public UserDetailsServiceProxy() {
    _initUserDetailsServiceProxy();
  }
  
  public UserDetailsServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initUserDetailsServiceProxy();
  }
  
  private void _initUserDetailsServiceProxy() {
    try {
      userDetailsService = (new com.born.fcs.am.intergration.bpm.service.client.user.UserDetailsServiceImplServiceLocator()).getUserDetailsServiceImplPort();
      if (userDetailsService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)userDetailsService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)userDetailsService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (userDetailsService != null)
      ((javax.xml.rpc.Stub)userDetailsService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.born.fcs.am.intergration.bpm.service.client.user.UserDetailsService getUserDetailsService() {
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService;
  }
  
  public com.born.fcs.am.intergration.bpm.service.client.user.SysOrg loadPrimaryOrgByUsername(java.lang.String userAccount) throws java.rmi.RemoteException{
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService.loadPrimaryOrgByUsername(userAccount);
  }
  
  public java.lang.String findUserByDeptId(java.lang.String deptId) throws java.rmi.RemoteException{
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService.findUserByDeptId(deptId);
  }
  
  public java.lang.String findUserByDeptCode(java.lang.String deptCode) throws java.rmi.RemoteException{
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService.findUserByDeptCode(deptCode);
  }
  
  public com.born.fcs.am.intergration.bpm.service.client.user.Job[] loadJobsByUsername(java.lang.String userAccount) throws java.rmi.RemoteException{
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService.loadJobsByUsername(userAccount);
  }
  
  public com.born.fcs.am.intergration.bpm.service.client.user.SysUser loadUserByUsername(java.lang.String userAccount) throws java.rmi.RemoteException, com.born.fcs.am.intergration.bpm.service.client.user.Exception{
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService.loadUserByUsername(userAccount);
  }
  
  public java.lang.String login(java.lang.String account, java.lang.String password, java.lang.String ip, java.lang.String source) throws java.rmi.RemoteException{
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService.login(account, password, ip, source);
  }
  
  public java.lang.String findUserByRoleAlias(java.lang.String roleAlias) throws java.rmi.RemoteException{
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService.findUserByRoleAlias(roleAlias);
  }
  
  public java.lang.String findUserByJobCode(java.lang.String jobCode) throws java.rmi.RemoteException{
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService.findUserByJobCode(jobCode);
  }
  
  public java.lang.String updatePwd(java.lang.String account, java.lang.String password) throws java.rmi.RemoteException{
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService.updatePwd(account, password);
  }
  
  public java.lang.String findUserByUserId(java.lang.Long userId) throws java.rmi.RemoteException{
    if (userDetailsService == null)
      _initUserDetailsServiceProxy();
    return userDetailsService.findUserByUserId(userId);
  }
  
  
}