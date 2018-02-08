package com.born.fcs.crm.ws.service.info;

import java.util.Map;

public class EvaluatingBaseQueryInfo extends EvaluatingBaseSetInfo {
	
	private static final long serialVersionUID = -2393550416034831851L;
	
	/** level2_id相同的项合并 */
	
	//	private String[] level3NameList;
	//	private String[] level3ScoreList;
	Map<String, String> evalue3;
	
	/** level3_id相同的项合并 */
	//	private String[] level4NameList;
	//	private String[] level4ScoreList;
	Map<String, String> evalue4;
	
	/** 公用事业类有标准值部分计算 ,有行业区分的key为行业，没行业的key值为why */
	Map<String, String> calculatingFormula_gybz;
	
	/** 实际值 */
	private String actualValue;
	/** 计算得分 */
	private String thisScore;
	
	public Map<String, String> getEvalue3() {
		return this.evalue3;
	}
	
	public void setEvalue3(Map<String, String> evalue3) {
		this.evalue3 = evalue3;
	}
	
	public Map<String, String> getEvalue4() {
		return this.evalue4;
	}
	
	public void setEvalue4(Map<String, String> evalue4) {
		this.evalue4 = evalue4;
	}
	
	public String getActualValue() {
		return this.actualValue;
	}
	
	public void setActualValue(String actualValue) {
		this.actualValue = actualValue;
	}
	
	public String getThisScore() {
		return this.thisScore;
	}
	
	public void setThisScore(String thisScore) {
		this.thisScore = thisScore;
	}
	
	public Map<String, String> getCalculatingFormula_gybz() {
		return this.calculatingFormula_gybz;
	}
	
	public void setCalculatingFormula_gybz(Map<String, String> calculatingFormula_gybz) {
		this.calculatingFormula_gybz = calculatingFormula_gybz;
	}
	
}
