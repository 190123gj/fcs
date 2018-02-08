package com.born.fcs.crm.ws.service.result;

import java.util.List;

import com.born.fcs.crm.ws.service.info.EvaluatingBaseQueryInfo;
import com.yjf.common.lang.result.ResultBase;

/**
 * 城市开发类指标查询：包含主观，标准值，财务部分
 * */
public class CityEvalueSetResult extends ResultBase {
	
	private static final long serialVersionUID = -1460897644358706752L;
	/** 主观评价 */
	private List<EvaluatingBaseQueryInfo> subjectivity;
	/** 有标准值部分 */
	private List<EvaluatingBaseQueryInfo> standardVal;
	/** 财务评价部分 */
	private List<EvaluatingBaseQueryInfo> terraceFinance;
	
	public List<EvaluatingBaseQueryInfo> getSubjectivity() {
		return this.subjectivity;
	}
	
	public void setSubjectivity(List<EvaluatingBaseQueryInfo> subjectivity) {
		this.subjectivity = subjectivity;
	}
	
	public List<EvaluatingBaseQueryInfo> getStandardVal() {
		return this.standardVal;
	}
	
	public void setStandardVal(List<EvaluatingBaseQueryInfo> standardVal) {
		this.standardVal = standardVal;
	}
	
	public List<EvaluatingBaseQueryInfo> getTerraceFinance() {
		return this.terraceFinance;
	}
	
	public void setTerraceFinance(List<EvaluatingBaseQueryInfo> terraceFinance) {
		this.terraceFinance = terraceFinance;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CityEvalueSetResult [subjectivity=");
		builder.append(subjectivity);
		builder.append(", standardVal=");
		builder.append(standardVal);
		builder.append(", terraceFinance=");
		builder.append(terraceFinance);
		builder.append("]");
		return builder.toString();
	}
	
}
