/**
 * SystemResourcesService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.born.bpm.service.client.resources;

public interface SystemResourcesService extends java.rmi.Remote {
    public java.lang.String getMenuResByAccount(java.lang.String sysAlias, java.lang.String account) throws java.rmi.RemoteException;
    public java.lang.String getMenuFunctionResByAccount(java.lang.String sysAlias, java.lang.String account) throws java.rmi.RemoteException;
    public java.lang.String getAllResByAccount(java.lang.String sysAlias, java.lang.String account) throws java.rmi.RemoteException;
    public java.lang.String getRoleByFunc(java.lang.String sysAlias, java.lang.String func) throws java.rmi.RemoteException;
    public java.lang.String getRoleByUrl(java.lang.String sysAlias, java.lang.String url) throws java.rmi.RemoteException;
}
