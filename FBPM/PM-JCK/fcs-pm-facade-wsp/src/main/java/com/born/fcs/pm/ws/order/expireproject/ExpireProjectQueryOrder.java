package com.born.fcs.pm.ws.order.expireproject;

import java.util.Date;

import com.born.fcs.pm.ws.base.QueryProjectBase;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.base.QueryPageBase;
import com.born.fcs.pm.ws.enums.ExpireProjectStatusEnum;

public class ExpireProjectQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = 789516442005343743L;
	
	private String projectCode; //项目编号
	private String projectName; //项目名称
	private String customerName; //客户名称
	private ExpireProjectStatusEnum status; //项目状态
	private Date expireDateBegin; //过期时间起
	private Date expireDateEnd; //过期时间止
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public ExpireProjectStatusEnum getStatus() {
		return status;
	}
	
	public void setStatus(ExpireProjectStatusEnum status) {
		this.status = status;
	}
	
	public String getExpireStatus() {
		return (null == this.status) ? "" : this.status.code();
	}
	
	public void setExpireStatus(String code) {
		this.status = ExpireProjectStatusEnum.getByCode(code);
	}
	
	public Date getExpireDateBegin() {
		return expireDateBegin;
	}

	public void setExpireDateBegin(Date expireDateBegin) {
		this.expireDateBegin = expireDateBegin;
	}

	public Date getExpireDateEnd() {
		return expireDateEnd;
	}

	public void setExpireDateEnd(Date expireDateEnd) {
		this.expireDateEnd = expireDateEnd;
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
