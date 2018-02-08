package com.born.fcs.rm.ws.order.report.project;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 
 * 查询客户信息
 * 
 * @author lirz
 * 
 * 2016-8-9 下午6:00:15
 */
public class ProjectCustomerQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = 1719887179271746799L;
	
	/** 查询方式：按客户查(CUSTOMER)/按项目查(PROJECT) */
	private String queyWay;
	/** 报告ID */
	private long reportId;
	/** 报送年 */
	private int reportYear;
	/** 报送月 */
	private int reportMonth;
	/** 对应客户ID */
	private String customerId;
	/** 对应客户名称 */
	private String customerName;
	/** 营业执照号 */
	private String busiLicenseNo;
	
	public String getQueyWay() {
		return queyWay;
	}

	public void setQueyWay(String queyWay) {
		this.queyWay = queyWay;
	}

	public long getReportId() {
		return reportId;
	}
	
	public void setReportId(long reportId) {
		this.reportId = reportId;
	}
	
	public int getReportYear() {
		return reportYear;
	}
	
	public void setReportYear(int reportYear) {
		this.reportYear = reportYear;
	}
	
	public int getReportMonth() {
		return reportMonth;
	}
	
	public void setReportMonth(int reportMonth) {
		this.reportMonth = reportMonth;
	}
	
	public String getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getBusiLicenseNo() {
		return busiLicenseNo;
	}
	
	public void setBusiLicenseNo(String busiLicenseNo) {
		this.busiLicenseNo = busiLicenseNo;
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
