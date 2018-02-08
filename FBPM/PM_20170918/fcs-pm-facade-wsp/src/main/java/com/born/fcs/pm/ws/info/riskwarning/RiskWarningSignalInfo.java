package com.born.fcs.pm.ws.info.riskwarning;

import java.util.Date;

import com.born.fcs.pm.ws.enums.SignalLevelEnum;
import com.born.fcs.pm.ws.enums.SignalTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 
 * 预警信号
 * 
 * @author lirz
 *
 * 2016-4-15 下午5:00:55
 */
public class RiskWarningSignalInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -2425133012834856086L;
	
	private long id; //主键
	private SignalTypeEnum signalType; //信号分类
	private SignalLevelEnum signalLevel; //信号等级 
	private String signalTypeName; //信号名称
	private Date rawAddTime;
	private Date rawUpdateTime;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public SignalTypeEnum getSignalType() {
		return signalType;
	}
	
	public void setSignalType(SignalTypeEnum signalType) {
		this.signalType = signalType;
	}
	
	public SignalLevelEnum getSignalLevel() {
		return signalLevel;
	}
	
	public void setSignalLevel(SignalLevelEnum signalLevel) {
		this.signalLevel = signalLevel;
	}
	
	public String getSignalTypeName() {
		return signalTypeName;
	}
	
	public void setSignalTypeName(String signalTypeName) {
		this.signalTypeName = signalTypeName;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
}
