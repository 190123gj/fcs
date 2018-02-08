package com.born.fcs.pm.ws.order.projectissueinformation;

import com.born.fcs.pm.ws.base.QueryProjectBase;

/**
 * 承销/发债类项目发行信息查询Order
 * @author Ji
 */
public class ProjectIssueInformationQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = 8675876314920798614L;
	private Long id;
	/**
	 * 项目编号
	 */
	private String projectCode;
	/**
	 * 对应客户ID
	 */
	private long customerId;
	/**
	 * 对应客户名称
	 */
	private String customerName;
	/**
	 * 业务类型
	 */
	private String busiType;
	/**
	 * 业务类型名称
	 */
	private String busiTypeName;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getBusiType() {
		return busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getBusiTypeName() {
		return busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
}
