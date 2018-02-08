/**
 * FlowService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.born.fcs.pm.integration.bpm;

public interface FlowService extends java.rmi.Remote {
    public java.lang.String getTaskUsersByInstanceIdAndNodeId(java.lang.String instanceId, java.lang.String nodeId) throws java.rmi.RemoteException;
    public java.lang.String getMyFlowListJson(java.lang.String json) throws java.rmi.RemoteException;
    public java.lang.String getFlowTypeList() throws java.rmi.RemoteException;
    public java.lang.String getTaskUsers(java.lang.String strTaskId) throws java.rmi.RemoteException;
    public java.lang.String delDraftByRunIds(java.lang.String runIds) throws java.rmi.RemoteException;
    public java.lang.String getAlreadyMattersListJson(java.lang.String json) throws java.rmi.RemoteException;
    public java.lang.String getMyRequestListJson(java.lang.String json) throws java.rmi.RemoteException;
    public java.lang.String getMyCompletedListJson(java.lang.String json) throws java.rmi.RemoteException;
    public java.lang.String getMyDraftListJson(java.lang.String json) throws java.rmi.RemoteException;
    public java.lang.String readTask(java.lang.String actInstId, java.lang.String taskId) throws java.rmi.RemoteException;
    public java.lang.String saveDraftByForm(java.lang.String json) throws java.rmi.RemoteException, com.born.fcs.pm.integration.bpm.Exception;
}
