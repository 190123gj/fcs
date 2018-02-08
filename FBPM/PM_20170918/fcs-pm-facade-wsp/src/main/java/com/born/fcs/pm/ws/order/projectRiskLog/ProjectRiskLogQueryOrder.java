package com.born.fcs.pm.ws.order.projectRiskLog;

import java.util.Date;

import com.born.fcs.pm.ws.base.QueryPermissionPageBase;
import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.order.base.FcsQueryPageBase;

public class ProjectRiskLogQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = -1682497010477888001L;
	
	private String projectCode;
	
	private String customerName;
	
	private String busiManagerName;

	private String eventDetail;

	private String eventTitle;

	private String logStatus;

	private String createManName;
	
	private Date startTimeBegin;

	private Date startTimeEnd;

	private String titleDetail;


	private Date occurTimeBegin;
	private Date occurTimeEnd;


	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getBusiManagerName() {
		return busiManagerName;
	}

	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}

	public String getEventDetail() {
		return eventDetail;
	}

	public void setEventDetail(String eventDetail) {
		this.eventDetail = eventDetail;
	}

	public String getLogStatus() {
		return logStatus;
	}

	public void setLogStatus(String logStatus) {
		this.logStatus = logStatus;
	}

	public String getCreateManName() {
		return createManName;
	}

	public void setCreateManName(String createManName) {
		this.createManName = createManName;
	}

	public Date getStartTimeBegin() {
		return startTimeBegin;
	}

	public void setStartTimeBegin(Date startTimeBegin) {
		this.startTimeBegin = startTimeBegin;
	}

	public Date getStartTimeEnd() {
		return startTimeEnd;
	}

	public void setStartTimeEnd(Date startTimeEnd) {
		this.startTimeEnd = startTimeEnd;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public String getTitleDetail() {
		return titleDetail;
	}

	public void setTitleDetail(String titleDetail) {
		this.titleDetail = titleDetail;
	}

	public Date getOccurTimeBegin() {
		return occurTimeBegin;
	}

	public void setOccurTimeBegin(Date occurTimeBegin) {
		this.occurTimeBegin = occurTimeBegin;
	}

	public Date getOccurTimeEnd() {
		return occurTimeEnd;
	}

	public void setOccurTimeEnd(Date occurTimeEnd) {
		this.occurTimeEnd = occurTimeEnd;
	}

	@Override
	public String toString() {
		return "ProjectRiskLogQueryOrder{" +
				"projectCode='" + projectCode + '\'' +
				", customerName='" + customerName + '\'' +
				", busiManagerName='" + busiManagerName + '\'' +
				", eventDetail='" + eventDetail + '\'' +
				", eventTitle='" + eventTitle + '\'' +
				", logStatus='" + logStatus + '\'' +
				", createManName='" + createManName + '\'' +
				", startTimeBegin=" + startTimeBegin +
				", startTimeEnd=" + startTimeEnd +
				", titleDetail='" + titleDetail + '\'' +
				", occurTimeBegin=" + occurTimeBegin +
				", occurTimeEnd=" + occurTimeEnd +
				'}';
	}
}
