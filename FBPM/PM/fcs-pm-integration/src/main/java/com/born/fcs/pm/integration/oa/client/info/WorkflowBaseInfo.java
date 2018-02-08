/**
 * WorkflowBaseInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.born.fcs.pm.integration.oa.client.info;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

public class WorkflowBaseInfo extends BaseToStringInfo implements java.io.Serializable {
	private java.lang.String workflowId;
	
	private java.lang.String workflowName;
	
	private java.lang.String workflowTypeId;
	
	private java.lang.String workflowTypeName;
	
	public WorkflowBaseInfo() {
	}
	
	public WorkflowBaseInfo(java.lang.String workflowId, java.lang.String workflowName,
							java.lang.String workflowTypeId, java.lang.String workflowTypeName) {
		this.workflowId = workflowId;
		this.workflowName = workflowName;
		this.workflowTypeId = workflowTypeId;
		this.workflowTypeName = workflowTypeName;
	}
	
	/**
	 * Gets the workflowId value for this WorkflowBaseInfo.
	 * 
	 * @return workflowId
	 */
	public java.lang.String getWorkflowId() {
		return workflowId;
	}
	
	/**
	 * Sets the workflowId value for this WorkflowBaseInfo.
	 * 
	 * @param workflowId
	 */
	public void setWorkflowId(java.lang.String workflowId) {
		this.workflowId = workflowId;
	}
	
	/**
	 * Gets the workflowName value for this WorkflowBaseInfo.
	 * 
	 * @return workflowName
	 */
	public java.lang.String getWorkflowName() {
		return workflowName;
	}
	
	/**
	 * Sets the workflowName value for this WorkflowBaseInfo.
	 * 
	 * @param workflowName
	 */
	public void setWorkflowName(java.lang.String workflowName) {
		this.workflowName = workflowName;
	}
	
	/**
	 * Gets the workflowTypeId value for this WorkflowBaseInfo.
	 * 
	 * @return workflowTypeId
	 */
	public java.lang.String getWorkflowTypeId() {
		return workflowTypeId;
	}
	
	/**
	 * Sets the workflowTypeId value for this WorkflowBaseInfo.
	 * 
	 * @param workflowTypeId
	 */
	public void setWorkflowTypeId(java.lang.String workflowTypeId) {
		this.workflowTypeId = workflowTypeId;
	}
	
	/**
	 * Gets the workflowTypeName value for this WorkflowBaseInfo.
	 * 
	 * @return workflowTypeName
	 */
	public java.lang.String getWorkflowTypeName() {
		return workflowTypeName;
	}
	
	/**
	 * Sets the workflowTypeName value for this WorkflowBaseInfo.
	 * 
	 * @param workflowTypeName
	 */
	public void setWorkflowTypeName(java.lang.String workflowTypeName) {
		this.workflowTypeName = workflowTypeName;
	}
	
	private java.lang.Object __equalsCalc = null;
	
	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof WorkflowBaseInfo))
			return false;
		WorkflowBaseInfo other = (WorkflowBaseInfo) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true
					&& ((this.workflowId == null && other.getWorkflowId() == null) || (this.workflowId != null && this.workflowId
						.equals(other.getWorkflowId())))
					&& ((this.workflowName == null && other.getWorkflowName() == null) || (this.workflowName != null && this.workflowName
						.equals(other.getWorkflowName())))
					&& ((this.workflowTypeId == null && other.getWorkflowTypeId() == null) || (this.workflowTypeId != null && this.workflowTypeId
						.equals(other.getWorkflowTypeId())))
					&& ((this.workflowTypeName == null && other.getWorkflowTypeName() == null) || (this.workflowTypeName != null && this.workflowTypeName
						.equals(other.getWorkflowTypeName())));
		__equalsCalc = null;
		return _equals;
	}
	
	private boolean __hashCodeCalc = false;
	
	public synchronized int hashCode() {
		if (__hashCodeCalc) {
			return 0;
		}
		__hashCodeCalc = true;
		int _hashCode = 1;
		if (getWorkflowId() != null) {
			_hashCode += getWorkflowId().hashCode();
		}
		if (getWorkflowName() != null) {
			_hashCode += getWorkflowName().hashCode();
		}
		if (getWorkflowTypeId() != null) {
			_hashCode += getWorkflowTypeId().hashCode();
		}
		if (getWorkflowTypeName() != null) {
			_hashCode += getWorkflowTypeName().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}
	
	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
		WorkflowBaseInfo.class, true);
	
	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.workflow.weaver",
			"WorkflowBaseInfo"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("workflowId");
		elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.workflow.weaver",
			"workflowId"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
			"string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("workflowName");
		elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.workflow.weaver",
			"workflowName"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
			"string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("workflowTypeId");
		elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.workflow.weaver",
			"workflowTypeId"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
			"string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("workflowTypeName");
		elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.workflow.weaver",
			"workflowTypeName"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
			"string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}
	
	/**
	 * Return type metadata object
	 */
	public static org.apache.axis.description.TypeDesc getTypeDesc() {
		return typeDesc;
	}
	
	/**
	 * Get Custom Serializer
	 */
	public static org.apache.axis.encoding.Serializer getSerializer(java.lang.String mechType,
																	java.lang.Class _javaType,
																	javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanSerializer(_javaType, _xmlType, typeDesc);
	}
	
	/**
	 * Get Custom Deserializer
	 */
	public static org.apache.axis.encoding.Deserializer getDeserializer(java.lang.String mechType,
																		java.lang.Class _javaType,
																		javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType, _xmlType, typeDesc);
	}
	
}
