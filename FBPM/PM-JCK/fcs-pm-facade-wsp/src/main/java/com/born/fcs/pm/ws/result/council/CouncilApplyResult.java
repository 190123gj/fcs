package com.born.fcs.pm.ws.result.council;

import com.born.fcs.pm.ws.result.base.FormBaseResult;

public class CouncilApplyResult extends FormBaseResult {
	
	private static final long serialVersionUID = 4216697960142274010L;
	
	private long applyId;
	
	public long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	
}
