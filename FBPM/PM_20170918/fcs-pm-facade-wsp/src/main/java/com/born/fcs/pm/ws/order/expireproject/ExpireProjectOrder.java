package com.born.fcs.pm.ws.order.expireproject;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.ExpireProjectStatusEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;

public class ExpireProjectOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 9127508306666387516L;
	
	private long id; //主键
	private String projectCode; //项目编号
	private String projectName; //项目名称
	private Date expireDate; //项目到期时间(还款)
	private ExpireProjectStatusEnum status; //项目状态
	private String repayCertificate; //还款回执
	private String isDONE;//是否结束项目
	private String  receipt;//回执

	@Override
	public void check() {
//		validateHasText(projectCode, "项目编号");
//		validateHasText(projectName, "项目名称");
//		validateNotNull(expireDate, "项目到期时间");
//		validateNotNull(status, "项目到期状态");
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public String getIsDONE() {
		return isDONE;
	}

	public void setIsDONE(String isDONE) {
		this.isDONE = isDONE;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
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
	
	public Date getExpireDate() {
		return expireDate;
	}
	
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	
	public ExpireProjectStatusEnum getStatus() {
		return status;
	}
	
	public void setStatus(ExpireProjectStatusEnum status) {
		this.status = status;
	}
	
	public String getRepayCertificate() {
		return repayCertificate;
	}
	
	public void setRepayCertificate(String repayCertificate) {
		this.repayCertificate = repayCertificate;
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
