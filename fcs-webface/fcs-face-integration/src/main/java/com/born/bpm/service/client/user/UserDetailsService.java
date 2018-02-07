/**
 * UserDetailsService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.born.bpm.service.client.user;

public interface UserDetailsService extends java.rmi.Remote {
    public com.born.bpm.service.client.user.SysOrg loadPrimaryOrgByUsername(java.lang.String userAccount) throws java.rmi.RemoteException;
    public java.lang.String findDeptInfoByDeptId(java.lang.String deptId) throws java.rmi.RemoteException;
    public java.lang.String findUserByDeptId(java.lang.String deptId) throws java.rmi.RemoteException;
    public java.lang.String findUserByDeptCode(java.lang.String deptCode) throws java.rmi.RemoteException;
    public com.born.bpm.service.client.user.Job[] loadJobsByUsername(java.lang.String userAccount) throws java.rmi.RemoteException;
    public com.born.bpm.service.client.user.SysUser loadUserByUsername(java.lang.String userAccount) throws java.rmi.RemoteException, com.born.bpm.service.client.user.Exception;
    public java.lang.String login(java.lang.String account, java.lang.String password, java.lang.String ip, java.lang.String source) throws java.rmi.RemoteException;
    public java.lang.String findUserByRoleAlias(java.lang.String roleAlias) throws java.rmi.RemoteException;
    public java.lang.String findDeptInfoByDeptCode(java.lang.String deptCode) throws java.rmi.RemoteException;
    public java.lang.String findUserRelatedInfoByUserId(java.lang.Long userId) throws java.rmi.RemoteException;
    public java.lang.String findUserByJobCode(java.lang.String jobCode) throws java.rmi.RemoteException;
    public java.lang.String getChargeByOrgId(java.lang.Long orgId) throws java.rmi.RemoteException;
    public java.lang.String loadUserOrgByFullName(java.lang.String fullName) throws java.rmi.RemoteException;
    public java.lang.String updatePwd(java.lang.String account, java.lang.String password) throws java.rmi.RemoteException;
    public java.lang.String searchLoginLog(java.lang.String json) throws java.rmi.RemoteException;
    public java.lang.String findUserByUserId(java.lang.Long userId) throws java.rmi.RemoteException;
    public java.lang.String loadOrgByName(java.lang.String orgName) throws java.rmi.RemoteException;
}
