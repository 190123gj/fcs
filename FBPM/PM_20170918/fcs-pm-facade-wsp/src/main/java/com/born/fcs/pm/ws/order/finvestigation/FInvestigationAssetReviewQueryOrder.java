package com.born.fcs.pm.ws.order.finvestigation;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.base.QueryPermissionPageBase;

/**
 * 
 * 尽调-资产复评 查询
 * 
 * @author lirz
 * 
 * 2016-9-19 下午4:42:52
 * 
 */
public class FInvestigationAssetReviewQueryOrder extends QueryPermissionPageBase {
	
	private static final long serialVersionUID = 4569355941787798429L;
	
	//客户名称
	private String customerName;
	//项目名称
	private String projectName;
	//项目编号
	private String projectCode;
	private String status; // 状态
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
