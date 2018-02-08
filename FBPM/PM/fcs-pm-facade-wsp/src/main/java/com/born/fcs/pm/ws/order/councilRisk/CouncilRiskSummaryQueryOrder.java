package com.born.fcs.pm.ws.order.councilRisk;

import com.born.fcs.pm.ws.base.QueryProjectBase;
import java.util.List;

public class CouncilRiskSummaryQueryOrder extends QueryProjectBase {
	private long councilId;
	private List<String> summaryIds;

	public long getCouncilId() {
		return councilId;
	}

	public void setCouncilId(long councilId) {
		this.councilId = councilId;
	}

	public List<String> getSummaryIds() {
		return summaryIds;
	}

	public void setSummaryIds(List<String> summaryIds) {
		this.summaryIds = summaryIds;
	}

	@Override
	public java.lang.String toString() {
		return "CouncilRiskSummaryQueryOrder{" +
				"councilId=" + councilId +
				", summaryIds=" + summaryIds +
				'}';
	}
}
