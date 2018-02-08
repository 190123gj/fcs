package com.born.fcs.pm.ws.order.riskwarning;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.base.QueryPageBase;
import com.born.fcs.pm.ws.enums.SignalLevelEnum;
import com.born.fcs.pm.ws.enums.SignalTypeEnum;

/**
 * 
 * 风险预警信号查询order
 * 
 * @author lirz
 *
 * 2016-4-18 下午5:11:42
 */
public class RiskWarningSignalQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -7846765270144515327L;
	
	private long id; //主键
	private SignalTypeEnum signalType; //信号分类
	private SignalLevelEnum signalLevel; //信号等级 
	private String signalTypeName; //信号名称
	
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
	
	/**
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
