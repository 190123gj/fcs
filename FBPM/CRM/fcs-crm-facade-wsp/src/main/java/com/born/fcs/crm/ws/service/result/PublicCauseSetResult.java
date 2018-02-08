package com.born.fcs.crm.ws.service.result;

import java.util.List;

import com.born.fcs.crm.ws.service.info.EvaluatingBaseQueryInfo;
import com.yjf.common.lang.result.ResultBase;

/**
 * 公用事业类配置查询
 * */

public class PublicCauseSetResult extends ResultBase {
	
	private static final long serialVersionUID = 5252651650059378377L;
	/** 无标准值部分 */
	private List<EvaluatingBaseQueryInfo> noStandardVal;
	/** 有标准值部分 */
	private List<EvaluatingBaseQueryInfo> standardVal;
	
	public List<EvaluatingBaseQueryInfo> getNoStandardVal() {
		return this.noStandardVal;
	}
	
	public void setNoStandardVal(List<EvaluatingBaseQueryInfo> noStandardVal) {
		this.noStandardVal = noStandardVal;
	}
	
	public List<EvaluatingBaseQueryInfo> getStandardVal() {
		return this.standardVal;
	}
	
	public void setStandardVal(List<EvaluatingBaseQueryInfo> standardVal) {
		this.standardVal = standardVal;
	}
	
}
