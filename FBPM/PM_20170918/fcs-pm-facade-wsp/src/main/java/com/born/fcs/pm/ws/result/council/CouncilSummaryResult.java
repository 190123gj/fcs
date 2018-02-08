package com.born.fcs.pm.ws.result.council;

import com.born.fcs.pm.ws.info.council.FCouncilSummaryInfo;
import com.born.fcs.pm.ws.result.base.FormBaseResult;

public class CouncilSummaryResult extends FormBaseResult {
	
	private static final long serialVersionUID = -5140037241201152011L;
	
	//会议纪要
	FCouncilSummaryInfo summary;
	
	public FCouncilSummaryInfo getSummary() {
		return this.summary;
	}
	
	public void setSummary(FCouncilSummaryInfo summary) {
		this.summary = summary;
	}
	
}
