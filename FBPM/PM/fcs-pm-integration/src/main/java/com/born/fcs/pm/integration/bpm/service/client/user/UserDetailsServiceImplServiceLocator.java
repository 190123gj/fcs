/**
 * UserDetailsServiceImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.born.fcs.pm.integration.bpm.service.client.user;

public class UserDetailsServiceImplServiceLocator extends org.apache.axis.client.Service implements com.born.fcs.pm.integration.bpm.service.client.user.UserDetailsServiceImplService {

    public UserDetailsServiceImplServiceLocator() {
    }


    public UserDetailsServiceImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public UserDetailsServiceImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for UserDetailsServiceImplPort
    private java.lang.String UserDetailsServiceImplPort_address = "http://127.0.0.1:29440/bornbpm/service/UserDetailsService";

    public java.lang.String getUserDetailsServiceImplPortAddress() {
        return UserDetailsServiceImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String UserDetailsServiceImplPortWSDDServiceName = "UserDetailsServiceImplPort";

    public java.lang.String getUserDetailsServiceImplPortWSDDServiceName() {
        return UserDetailsServiceImplPortWSDDServiceName;
    }

    public void setUserDetailsServiceImplPortWSDDServiceName(java.lang.String name) {
        UserDetailsServiceImplPortWSDDServiceName = name;
    }

    public com.born.fcs.pm.integration.bpm.service.client.user.UserDetailsService getUserDetailsServiceImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(UserDetailsServiceImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getUserDetailsServiceImplPort(endpoint);
    }

    public com.born.fcs.pm.integration.bpm.service.client.user.UserDetailsService getUserDetailsServiceImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.born.fcs.pm.integration.bpm.service.client.user.UserDetailsServiceImplServiceSoapBindingStub _stub = new com.born.fcs.pm.integration.bpm.service.client.user.UserDetailsServiceImplServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getUserDetailsServiceImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setUserDetailsServiceImplPortEndpointAddress(java.lang.String address) {
        UserDetailsServiceImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.born.fcs.pm.integration.bpm.service.client.user.UserDetailsService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.born.fcs.pm.integration.bpm.service.client.user.UserDetailsServiceImplServiceSoapBindingStub _stub = new com.born.fcs.pm.integration.bpm.service.client.user.UserDetailsServiceImplServiceSoapBindingStub(new java.net.URL(UserDetailsServiceImplPort_address), this);
                _stub.setPortName(getUserDetailsServiceImplPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("UserDetailsServiceImplPort".equals(inputPortName)) {
            return getUserDetailsServiceImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://impl.webservice.platform.gcdata.com/", "UserDetailsServiceImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://impl.webservice.platform.gcdata.com/", "UserDetailsServiceImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("UserDetailsServiceImplPort".equals(portName)) {
            setUserDetailsServiceImplPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
