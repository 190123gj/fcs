package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Map;

import com.born.fcs.pm.ws.info.common.AttachmentModuleType;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;

/**
 * 
 * 尽调公用
 * 
 * @author lirz
 * 
 * 2017-4-28 下午4:04:49
 * 
 */
public class InvestigationBaseInfo extends SimpleFormProjectInfo {
	
	private static final long serialVersionUID = -4105872636177336408L;
	
	private int curIndex;
	//附件
	private Map<String, AttachmentModuleType> attachmentMap;
	
	private double riskRate; //风险覆盖率
	private int subIndex = 0;
	
	public int getCurIndex() {
		return this.curIndex;
	}
	
	public void setCurIndex(int curIndex) {
		this.curIndex = curIndex;
	}
	
	public Map<String, AttachmentModuleType> getAttachmentMap() {
		return this.attachmentMap;
	}
	
	public void setAttachmentMap(Map<String, AttachmentModuleType> attachmentMap) {
		this.attachmentMap = attachmentMap;
	}
	
	public double getRiskRate() {
		return this.riskRate;
	}
	
	public void setRiskRate(double riskRate) {
		this.riskRate = riskRate;
	}

	public int getSubIndex() {
		return subIndex;
	}

	public void setSubIndex(int subIndex) {
		this.subIndex = subIndex;
	}
	
}
