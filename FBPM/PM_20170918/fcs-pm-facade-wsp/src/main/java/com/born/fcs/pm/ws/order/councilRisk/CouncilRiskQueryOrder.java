package com.born.fcs.pm.ws.order.councilRisk;

import com.born.fcs.pm.ws.base.QueryPermissionPageBase;
import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.enums.CouncilRiskTypeEnum;
import com.born.fcs.pm.ws.enums.CouncilStatusEnum;
import com.born.fcs.pm.ws.order.base.FcsQueryPageBase;

import java.util.Date;

public class CouncilRiskQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = -1682497010477888001L;
	private Date startTimeBegin;
	private Date startTimeEnd;

	private CouncilRiskTypeEnum councilType;

	private CouncilStatusEnum councilStatus;

	public CouncilRiskTypeEnum getCouncilType() {
		return councilType;
	}

	public void setCouncilType(CouncilRiskTypeEnum councilType) {
		this.councilType = councilType;
	}

	public CouncilStatusEnum getCouncilStatus() {
		return councilStatus;
	}

	public void setCouncilStatus(CouncilStatusEnum councilStatus) {
		this.councilStatus = councilStatus;
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
		return "CouncilRiskQueryOrder{" +
				"startTimeBegin=" + startTimeBegin +
				", startTimeEnd=" + startTimeEnd +
				", councilType=" + councilType +
				", councilStatus=" + councilStatus +
				'}';
	}
}
