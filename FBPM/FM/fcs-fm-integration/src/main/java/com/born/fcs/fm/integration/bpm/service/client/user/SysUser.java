/**
 * SysUser.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.born.fcs.fm.integration.bpm.service.client.user;

public class SysUser extends BaseModel implements java.io.Serializable {
	private java.lang.String account;
	
	private GrantedAuthorityValue[] authorities;
	
	private java.lang.String email;
	
	private java.util.Calendar entryDate;
	
	private java.lang.Short fromType;
	
	private java.lang.String fullname;
	
	private java.lang.Short isExpired;
	
	private java.lang.Short isLock;
	
	private java.lang.String mobile;
	
	private java.lang.String orgName;
	
	private java.lang.String password;
	
	private java.lang.String phone;
	
	private java.lang.String picture;
	
	private java.lang.String retype;
	
	private java.lang.String sex;
	
	private java.lang.Short status;
	
	private java.lang.Long[] superiorIds;
	
	private java.lang.Long userId;
	
	private java.lang.Long userOrgId;
	
	private java.lang.String userStatus;
	private Long seqNum;
	
	public SysUser() {
	}
	
	public SysUser(	java.lang.Long createBy,
					java.util.Calendar createtime,
					java.lang.Long updateBy,
					java.util.Calendar updatetime,
					java.lang.String account,
					com.born.fcs.fm.integration.bpm.service.client.user.GrantedAuthorityValue[] authorities,
					java.lang.String email, java.util.Calendar entryDate, java.lang.Short fromType,
					java.lang.String fullname, java.lang.Short isExpired, java.lang.Short isLock,
					java.lang.String mobile, java.lang.String orgName, java.lang.String password,
					java.lang.String phone, java.lang.String picture, java.lang.String retype,
					java.lang.String sex, java.lang.Short status, java.lang.Long[] superiorIds,
					java.lang.Long userId, java.lang.Long userOrgId, java.lang.String userStatus) {
		super(createBy, createtime, updateBy, updatetime);
		this.account = account;
		this.authorities = authorities;
		this.email = email;
		this.entryDate = entryDate;
		this.fromType = fromType;
		this.fullname = fullname;
		this.isExpired = isExpired;
		this.isLock = isLock;
		this.mobile = mobile;
		this.orgName = orgName;
		this.password = password;
		this.phone = phone;
		this.picture = picture;
		this.retype = retype;
		this.sex = sex;
		this.status = status;
		this.superiorIds = superiorIds;
		this.userId = userId;
		this.userOrgId = userOrgId;
		this.userStatus = userStatus;
	}
	
	/**
	 * Gets the account value for this SysUser.
	 * 
	 * @return account
	 */
	public java.lang.String getAccount() {
		return account;
	}
	
	/**
	 * Sets the account value for this SysUser.
	 * 
	 * @param account
	 */
	public void setAccount(java.lang.String account) {
		this.account = account;
	}
	
	/**
	 * Gets the authorities value for this SysUser.
	 * 
	 * @return authorities
	 */
	public com.born.fcs.fm.integration.bpm.service.client.user.GrantedAuthorityValue[] getAuthorities() {
		return authorities;
	}
	
	/**
	 * Sets the authorities value for this SysUser.
	 * 
	 * @param authorities
	 */
	public void setAuthorities(	com.born.fcs.fm.integration.bpm.service.client.user.GrantedAuthorityValue[] authorities) {
		this.authorities = authorities;
	}
	
	public com.born.fcs.fm.integration.bpm.service.client.user.GrantedAuthorityValue getAuthorities(int i) {
		return this.authorities[i];
	}
	
	public void setAuthorities(	int i,
								com.born.fcs.fm.integration.bpm.service.client.user.GrantedAuthorityValue _value) {
		this.authorities[i] = _value;
	}
	
	/**
	 * Gets the email value for this SysUser.
	 * 
	 * @return email
	 */
	public java.lang.String getEmail() {
		return email;
	}
	
	/**
	 * Sets the email value for this SysUser.
	 * 
	 * @param email
	 */
	public void setEmail(java.lang.String email) {
		this.email = email;
	}
	
	/**
	 * Gets the entryDate value for this SysUser.
	 * 
	 * @return entryDate
	 */
	public java.util.Calendar getEntryDate() {
		return entryDate;
	}
	
	/**
	 * Sets the entryDate value for this SysUser.
	 * 
	 * @param entryDate
	 */
	public void setEntryDate(java.util.Calendar entryDate) {
		this.entryDate = entryDate;
	}
	
	/**
	 * Gets the fromType value for this SysUser.
	 * 
	 * @return fromType
	 */
	public java.lang.Short getFromType() {
		return fromType;
	}
	
	/**
	 * Sets the fromType value for this SysUser.
	 * 
	 * @param fromType
	 */
	public void setFromType(java.lang.Short fromType) {
		this.fromType = fromType;
	}
	
	/**
	 * Gets the fullname value for this SysUser.
	 * 
	 * @return fullname
	 */
	public java.lang.String getFullname() {
		return fullname;
	}
	
	/**
	 * Sets the fullname value for this SysUser.
	 * 
	 * @param fullname
	 */
	public void setFullname(java.lang.String fullname) {
		this.fullname = fullname;
	}
	
	/**
	 * Gets the isExpired value for this SysUser.
	 * 
	 * @return isExpired
	 */
	public java.lang.Short getIsExpired() {
		return isExpired;
	}
	
	/**
	 * Sets the isExpired value for this SysUser.
	 * 
	 * @param isExpired
	 */
	public void setIsExpired(java.lang.Short isExpired) {
		this.isExpired = isExpired;
	}
	
	/**
	 * Gets the isLock value for this SysUser.
	 * 
	 * @return isLock
	 */
	public java.lang.Short getIsLock() {
		return isLock;
	}
	
	/**
	 * Sets the isLock value for this SysUser.
	 * 
	 * @param isLock
	 */
	public void setIsLock(java.lang.Short isLock) {
		this.isLock = isLock;
	}
	
	/**
	 * Gets the mobile value for this SysUser.
	 * 
	 * @return mobile
	 */
	public java.lang.String getMobile() {
		return mobile;
	}
	
	/**
	 * Sets the mobile value for this SysUser.
	 * 
	 * @param mobile
	 */
	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}
	
	/**
	 * Gets the orgName value for this SysUser.
	 * 
	 * @return orgName
	 */
	public java.lang.String getOrgName() {
		return orgName;
	}
	
	/**
	 * Sets the orgName value for this SysUser.
	 * 
	 * @param orgName
	 */
	public void setOrgName(java.lang.String orgName) {
		this.orgName = orgName;
	}
	
	/**
	 * Gets the password value for this SysUser.
	 * 
	 * @return password
	 */
	public java.lang.String getPassword() {
		return password;
	}
	
	/**
	 * Sets the password value for this SysUser.
	 * 
	 * @param password
	 */
	public void setPassword(java.lang.String password) {
		this.password = password;
	}
	
	/**
	 * Gets the phone value for this SysUser.
	 * 
	 * @return phone
	 */
	public java.lang.String getPhone() {
		return phone;
	}
	
	/**
	 * Sets the phone value for this SysUser.
	 * 
	 * @param phone
	 */
	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}
	
	/**
	 * Gets the picture value for this SysUser.
	 * 
	 * @return picture
	 */
	public java.lang.String getPicture() {
		return picture;
	}
	
	/**
	 * Sets the picture value for this SysUser.
	 * 
	 * @param picture
	 */
	public void setPicture(java.lang.String picture) {
		this.picture = picture;
	}
	
	/**
	 * Gets the retype value for this SysUser.
	 * 
	 * @return retype
	 */
	public java.lang.String getRetype() {
		return retype;
	}
	
	/**
	 * Sets the retype value for this SysUser.
	 * 
	 * @param retype
	 */
	public void setRetype(java.lang.String retype) {
		this.retype = retype;
	}
	
	/**
	 * Gets the sex value for this SysUser.
	 * 
	 * @return sex
	 */
	public java.lang.String getSex() {
		return sex;
	}
	
	/**
	 * Sets the sex value for this SysUser.
	 * 
	 * @param sex
	 */
	public void setSex(java.lang.String sex) {
		this.sex = sex;
	}
	
	/**
	 * Gets the status value for this SysUser.
	 * 
	 * @return status
	 */
	public java.lang.Short getStatus() {
		return status;
	}
	
	/**
	 * Sets the status value for this SysUser.
	 * 
	 * @param status
	 */
	public void setStatus(java.lang.Short status) {
		this.status = status;
	}
	
	/**
	 * Gets the superiorIds value for this SysUser.
	 * 
	 * @return superiorIds
	 */
	public java.lang.Long[] getSuperiorIds() {
		return superiorIds;
	}
	
	/**
	 * Sets the superiorIds value for this SysUser.
	 * 
	 * @param superiorIds
	 */
	public void setSuperiorIds(java.lang.Long[] superiorIds) {
		this.superiorIds = superiorIds;
	}
	
	public java.lang.Long getSuperiorIds(int i) {
		return this.superiorIds[i];
	}
	
	public void setSuperiorIds(int i, java.lang.Long _value) {
		this.superiorIds[i] = _value;
	}
	
	/**
	 * Gets the userId value for this SysUser.
	 * 
	 * @return userId
	 */
	public java.lang.Long getUserId() {
		return userId;
	}
	
	/**
	 * Sets the userId value for this SysUser.
	 * 
	 * @param userId
	 */
	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}
	
	/**
	 * Gets the userOrgId value for this SysUser.
	 * 
	 * @return userOrgId
	 */
	public java.lang.Long getUserOrgId() {
		return userOrgId;
	}
	
	/**
	 * Sets the userOrgId value for this SysUser.
	 * 
	 * @param userOrgId
	 */
	public void setUserOrgId(java.lang.Long userOrgId) {
		this.userOrgId = userOrgId;
	}
	
	/**
	 * Gets the userStatus value for this SysUser.
	 * 
	 * @return userStatus
	 */
	public java.lang.String getUserStatus() {
		return userStatus;
	}
	
	/**
	 * Sets the userStatus value for this SysUser.
	 * 
	 * @param userStatus
	 */
	public void setUserStatus(java.lang.String userStatus) {
		this.userStatus = userStatus;
	}
	
	public Long getSeqNum() {
		return this.seqNum;
	}
	
	public void setSeqNum(Long seqNum) {
		this.seqNum = seqNum;
	}
	
	private java.lang.Object __equalsCalc = null;
	
	@Override
	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof SysUser))
			return false;
		SysUser other = (SysUser) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = super.equals(obj)
					&& ((this.account == null && other.getAccount() == null) || (this.account != null && this.account
						.equals(other.getAccount())))
					&& ((this.authorities == null && other.getAuthorities() == null) || (this.authorities != null && java.util.Arrays
						.equals(this.authorities, other.getAuthorities())))
					&& ((this.email == null && other.getEmail() == null) || (this.email != null && this.email
						.equals(other.getEmail())))
					&& ((this.entryDate == null && other.getEntryDate() == null) || (this.entryDate != null && this.entryDate
						.equals(other.getEntryDate())))
					&& ((this.fromType == null && other.getFromType() == null) || (this.fromType != null && this.fromType
						.equals(other.getFromType())))
					&& ((this.fullname == null && other.getFullname() == null) || (this.fullname != null && this.fullname
						.equals(other.getFullname())))
					&& ((this.isExpired == null && other.getIsExpired() == null) || (this.isExpired != null && this.isExpired
						.equals(other.getIsExpired())))
					&& ((this.isLock == null && other.getIsLock() == null) || (this.isLock != null && this.isLock
						.equals(other.getIsLock())))
					&& ((this.mobile == null && other.getMobile() == null) || (this.mobile != null && this.mobile
						.equals(other.getMobile())))
					&& ((this.orgName == null && other.getOrgName() == null) || (this.orgName != null && this.orgName
						.equals(other.getOrgName())))
					&& ((this.password == null && other.getPassword() == null) || (this.password != null && this.password
						.equals(other.getPassword())))
					&& ((this.phone == null && other.getPhone() == null) || (this.phone != null && this.phone
						.equals(other.getPhone())))
					&& ((this.picture == null && other.getPicture() == null) || (this.picture != null && this.picture
						.equals(other.getPicture())))
					&& ((this.retype == null && other.getRetype() == null) || (this.retype != null && this.retype
						.equals(other.getRetype())))
					&& ((this.sex == null && other.getSex() == null) || (this.sex != null && this.sex
						.equals(other.getSex())))
					&& ((this.status == null && other.getStatus() == null) || (this.status != null && this.status
						.equals(other.getStatus())))
					&& ((this.superiorIds == null && other.getSuperiorIds() == null) || (this.superiorIds != null && java.util.Arrays
						.equals(this.superiorIds, other.getSuperiorIds())))
					&& ((this.userId == null && other.getUserId() == null) || (this.userId != null && this.userId
						.equals(other.getUserId())))
					&& ((this.userOrgId == null && other.getUserOrgId() == null) || (this.userOrgId != null && this.userOrgId
						.equals(other.getUserOrgId())))
					&& ((this.userStatus == null && other.getUserStatus() == null) || (this.userStatus != null && this.userStatus
						.equals(other.getUserStatus())));
		__equalsCalc = null;
		return _equals;
	}
	
	private boolean __hashCodeCalc = false;
	
	@Override
	public synchronized int hashCode() {
		if (__hashCodeCalc) {
			return 0;
		}
		__hashCodeCalc = true;
		int _hashCode = super.hashCode();
		if (getAccount() != null) {
			_hashCode += getAccount().hashCode();
		}
		if (getAuthorities() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getAuthorities()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getAuthorities(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (getEmail() != null) {
			_hashCode += getEmail().hashCode();
		}
		if (getEntryDate() != null) {
			_hashCode += getEntryDate().hashCode();
		}
		if (getFromType() != null) {
			_hashCode += getFromType().hashCode();
		}
		if (getFullname() != null) {
			_hashCode += getFullname().hashCode();
		}
		if (getIsExpired() != null) {
			_hashCode += getIsExpired().hashCode();
		}
		if (getIsLock() != null) {
			_hashCode += getIsLock().hashCode();
		}
		if (getMobile() != null) {
			_hashCode += getMobile().hashCode();
		}
		if (getOrgName() != null) {
			_hashCode += getOrgName().hashCode();
		}
		if (getPassword() != null) {
			_hashCode += getPassword().hashCode();
		}
		if (getPhone() != null) {
			_hashCode += getPhone().hashCode();
		}
		if (getPicture() != null) {
			_hashCode += getPicture().hashCode();
		}
		if (getRetype() != null) {
			_hashCode += getRetype().hashCode();
		}
		if (getSex() != null) {
			_hashCode += getSex().hashCode();
		}
		if (getStatus() != null) {
			_hashCode += getStatus().hashCode();
		}
		if (getSuperiorIds() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getSuperiorIds()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getSuperiorIds(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (getUserId() != null) {
			_hashCode += getUserId().hashCode();
		}
		if (getUserOrgId() != null) {
			_hashCode += getUserOrgId().hashCode();
		}
		if (getUserStatus() != null) {
			_hashCode += getUserStatus().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}
	
	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
		SysUser.class, true);
	
	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName(
			"http://impl.webservice.platform.gcdata.com/", "sysUser"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("account");
		elemField.setXmlName(new javax.xml.namespace.QName("", "account"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
			"string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("authorities");
		elemField.setXmlName(new javax.xml.namespace.QName("", "authorities"));
		elemField.setXmlType(new javax.xml.namespace.QName(
			"http://impl.webservice.platform.gcdata.com/", "grantedAuthorityValue"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		elemField.setMaxOccursUnbounded(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("email");
		elemField.setXmlName(new javax.xml.namespace.QName("", "email"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
			"string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("entryDate");
		elemField.setXmlName(new javax.xml.namespace.QName("", "entryDate"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
			"dateTime"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("fromType");
		elemField.setXmlName(new javax.xml.namespace.QName("", "fromType"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
			"short"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("fullname");
		elemField.setXmlName(new javax.xml.namespace.QName("", "fullname"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
			"string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("isExpired");
		elemField.setXmlName(new javax.xml.namespace.QName("", "isExpired"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
			"short"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("isLock");
		elemField.setXmlName(new javax.xml.namespace.QName("", "isLock"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
			"short"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("mobile");
		elemField.setXmlName(new javax.xml.namespace.QName("", "mobile"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
			"string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("orgName");
		elemField.setXmlName(new javax.xml.namespace.QName("", "orgName"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
			"string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("password");
		elemField.setXmlName(new javax.xml.namespace.QName("", "password"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
			"string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("phone");
		elemField.setXmlName(new javax.xml.namespace.QName("", "phone"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
			"string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("picture");
		elemField.setXmlName(new javax.xml.namespace.QName("", "picture"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
			"string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("retype");
		elemField.setXmlName(new javax.xml.namespace.QName("", "retype"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
			"string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("sex");
		elemField.setXmlName(new javax.xml.namespace.QName("", "sex"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
			"string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("status");
		elemField.setXmlName(new javax.xml.namespace.QName("", "status"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
			"short"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("superiorIds");
		elemField.setXmlName(new javax.xml.namespace.QName("", "superiorIds"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
			"long"));
		elemField.setMinOccurs(0);
		elemField.setNillable(true);
		elemField.setMaxOccursUnbounded(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("userId");
		elemField.setXmlName(new javax.xml.namespace.QName("", "userId"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
			"long"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("userOrgId");
		elemField.setXmlName(new javax.xml.namespace.QName("", "userOrgId"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
			"long"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("userStatus");
		elemField.setXmlName(new javax.xml.namespace.QName("", "userStatus"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
			"string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
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
