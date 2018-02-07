/**
 * UserOrgService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.born.bpm.service.client.org;

public interface UserOrgService extends java.rmi.Remote {
    public java.lang.String removeUserOrgRel(java.lang.String userAccount, java.lang.String orgCode) throws java.rmi.RemoteException;
    public java.lang.String addUserRoleRel(java.lang.String userAccount, java.lang.String roleAlias) throws java.rmi.RemoteException;
    public java.lang.String addOrUpdatePos(java.lang.String posInfo) throws java.rmi.RemoteException;
    public java.lang.String addOrUpdateJob(java.lang.String jobInfo) throws java.rmi.RemoteException;
    public java.lang.String deleteParam(java.lang.String paramKey) throws java.rmi.RemoteException;
    public java.lang.String addUserPosRel(java.lang.String userAccount, java.lang.String posCode, java.lang.String orgType, java.lang.String posType) throws java.rmi.RemoteException;
    public java.lang.String addOrUpdateUser(java.lang.String userInfo) throws java.rmi.RemoteException;
    public java.lang.String addUserOrgRel(java.lang.String userAccount, java.lang.String orgCode, java.lang.String userPerms, java.lang.String orgPerms, java.lang.String roleAlias) throws java.rmi.RemoteException;
    public java.lang.String removeUserRel(java.lang.String upUserAccount, java.lang.String downUserAccount) throws java.rmi.RemoteException;
    public java.lang.String deleteOrg(java.lang.String code) throws java.rmi.RemoteException;
    public java.lang.String removeUserPosRel(java.lang.String userAccount, java.lang.String posCode) throws java.rmi.RemoteException;
    public java.lang.String addUserRel(java.lang.String upUserAccount, java.lang.String downUserAccount) throws java.rmi.RemoteException;
    public java.lang.String addOrUpdateRole(java.lang.String roleInfo) throws java.rmi.RemoteException;
    public java.lang.String editParamValue(java.lang.String type, java.lang.String key, java.lang.String paramValue) throws java.rmi.RemoteException;
    public java.lang.String deleteJob(java.lang.String jobcode) throws java.rmi.RemoteException;
    public java.lang.String removeUserRoleRel(java.lang.String userAccount, java.lang.String roleAlias) throws java.rmi.RemoteException;
    public java.lang.String removeOrgRoleRel(java.lang.String orgCode, java.lang.String roleAlias) throws java.rmi.RemoteException;
    public java.lang.String deleteRole(java.lang.String alias) throws java.rmi.RemoteException;
    public java.lang.String deletePos(java.lang.String posCode) throws java.rmi.RemoteException;
    public java.lang.String addOrUpdateOrg(java.lang.String orgInfo) throws java.rmi.RemoteException;
    public java.lang.String addOrUpdateParam(java.lang.String paramInfo) throws java.rmi.RemoteException;
    public java.lang.String getParam() throws java.rmi.RemoteException;
    public java.lang.String addOrgRoleRel(java.lang.String orgCode, java.lang.String roleAlias) throws java.rmi.RemoteException;
    public java.lang.String deleteUser(java.lang.String account) throws java.rmi.RemoteException;
}
