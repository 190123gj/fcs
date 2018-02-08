package com.born.fcs.pm.ws.order.riskHandleTeam;

import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.order.base.FcsQueryPageBase;

import java.util.Date;

public class RiskHandlerTeamQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = -1682497010477888001L;
	
	private String projectCode;
	
	private String customerName;
	
	private String busiManagerName;
	
	private String chiefLeaderName;
	
	private String createManName;
	
	private Date startTimeBegin;
	private Date startTimeEnd;
	
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
	
	public String getChiefLeaderName() {
		return chiefLeaderName;
	}
	
	public void setChiefLeaderName(String chiefLeaderName) {
		this.chiefLeaderName = chiefLeaderName;
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

	@Override
	public String toString() {
		return "RiskHandlerTeamQueryOrder{" +
				"projectCode='" + projectCode + '\'' +
				", customerName='" + customerName + '\'' +
				", busiManagerName='" + busiManagerName + '\'' +
				", chiefLeaderName='" + chiefLeaderName + '\'' +
				", createManName='" + createManName + '\'' +
				", startTimeBegin=" + startTimeBegin +
				", startTimeEnd=" + startTimeEnd +
				'}';
	}
}
