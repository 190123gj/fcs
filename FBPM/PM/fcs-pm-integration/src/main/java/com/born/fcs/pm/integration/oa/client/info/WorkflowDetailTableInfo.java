/**
 * WorkflowDetailTableInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.born.fcs.pm.integration.oa.client.info;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

public class WorkflowDetailTableInfo extends BaseToStringInfo implements java.io.Serializable {
	private java.lang.String tableDBName;
	
	private java.lang.String[] tableFieldName;
	
	private java.lang.String tableTitle;
	
	private com.born.fcs.pm.integration.oa.client.info.WorkflowRequestTableRecord[] workflowRequestTableRecords;
	
	public WorkflowDetailTableInfo() {
	}
	
	public WorkflowDetailTableInfo(	java.lang.String tableDBName,
									java.lang.String[] tableFieldName,
									java.lang.String tableTitle,
									com.born.fcs.pm.integration.oa.client.info.WorkflowRequestTableRecord[] workflowRequestTableRecords) {
		this.tableDBName = tableDBName;
		this.tableFieldName = tableFieldName;
		this.tableTitle = tableTitle;
		this.workflowRequestTableRecords = workflowRequestTableRecords;
	}
	
	/**
	 * Gets the tableDBName value for this WorkflowDetailTableInfo.
	 * 
	 * @return tableDBName
	 */
	public java.lang.String getTableDBName() {
		return tableDBName;
	}
	
	/**
	 * Sets the tableDBName value for this WorkflowDetailTableInfo.
	 * 
	 * @param tableDBName
	 */
	public void setTableDBName(java.lang.String tableDBName) {
		this.tableDBName = tableDBName;
	}
	
	/**
	 * Gets the tableFieldName value for this WorkflowDetailTableInfo.
	 * 
	 * @return tableFieldName
	 */
	public java.lang.String[] getTableFieldName() {
		return tableFieldName;
	}
	
	/**
	 * Sets the tableFieldName value for this WorkflowDetailTableInfo.
	 * 
	 * @param tableFieldName
	 */
	public void setTableFieldName(java.lang.String[] tableFieldName) {
		this.tableFieldName = tableFieldName;
	}
	
	/**
	 * Gets the tableTitle value for this WorkflowDetailTableInfo.
	 * 
	 * @return tableTitle
	 */
	public java.lang.String getTableTitle() {
		return tableTitle;
	}
	
	/**
	 * Sets the tableTitle value for this WorkflowDetailTableInfo.
	 * 
	 * @param tableTitle
	 */
	public void setTableTitle(java.lang.String tableTitle) {
		this.tableTitle = tableTitle;
	}
	
	/**
	 * Gets the workflowRequestTableRecords value for this
	 * WorkflowDetailTableInfo.
	 * 
	 * @return workflowRequestTableRecords
	 */
	public com.born.fcs.pm.integration.oa.client.info.WorkflowRequestTableRecord[] getWorkflowRequestTableRecords() {
		return workflowRequestTableRecords;
	}
	
	/**
	 * Sets the workflowRequestTableRecords value for this
	 * WorkflowDetailTableInfo.
	 * 
	 * @param workflowRequestTableRecords
	 */
	public void setWorkflowRequestTableRecords(	com.born.fcs.pm.integration.oa.client.info.WorkflowRequestTableRecord[] workflowRequestTableRecords) {
		this.workflowRequestTableRecords = workflowRequestTableRecords;
	}
	
	private java.lang.Object __equalsCalc = null;
	
	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof WorkflowDetailTableInfo))
			return false;
		WorkflowDetailTableInfo other = (WorkflowDetailTableInfo) obj;
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
					&& ((this.tableDBName == null && other.getTableDBName() == null) || (this.tableDBName != null && this.tableDBName
						.equals(other.getTableDBName())))
					&& ((this.tableFieldName == null && other.getTableFieldName() == null) || (this.tableFieldName != null && java.util.Arrays
						.equals(this.tableFieldName, other.getTableFieldName())))
					&& ((this.tableTitle == null && other.getTableTitle() == null) || (this.tableTitle != null && this.tableTitle
						.equals(other.getTableTitle())))
					&& ((this.workflowRequestTableRecords == null && other
						.getWorkflowRequestTableRecords() == null) || (this.workflowRequestTableRecords != null && java.util.Arrays
						.equals(this.workflowRequestTableRecords,
							other.getWorkflowRequestTableRecords())));
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
		if (getTableDBName() != null) {
			_hashCode += getTableDBName().hashCode();
		}
		if (getTableFieldName() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getTableFieldName()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getTableFieldName(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (getTableTitle() != null) {
			_hashCode += getTableTitle().hashCode();
		}
		if (getWorkflowRequestTableRecords() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getWorkflowRequestTableRecords()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(
					getWorkflowRequestTableRecords(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		__hashCodeCalc = false;
		return _hashCode;
	}
	
	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
		WorkflowDetailTableInfo.class, true);
	
	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.workflow.weaver",
			"WorkflowDetailTableInfo"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("tableDBName");
		elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.workflow.weaver",
			"tableDBName"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
			"string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("tableFieldName");
		elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.workflow.weaver",
			"tableFieldName"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
			"string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		elemField.setItemQName(new javax.xml.namespace.QName("webservices.services.weaver.com.cn",
			"string"));
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("tableTitle");
		elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.workflow.weaver",
			"tableTitle"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
			"string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("workflowRequestTableRecords");
		elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.workflow.weaver",
			"workflowRequestTableRecords"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.workflow.weaver",
			"WorkflowRequestTableRecord"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		elemField.setItemQName(new javax.xml.namespace.QName("http://webservices.workflow.weaver",
			"WorkflowRequestTableRecord"));
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
