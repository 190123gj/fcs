package com.born.fcs.pm.ws.order.risklevel;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.base.QueryProjectBase;

/**
 * 风险评级查询
 * 
 * @author lirz
 * 
 * 2016-5-18 下午8:54:20
 */
public class RiskLevelQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = -3801710746933608076L;
	
	private String formStatus; // 表单状态
	private String riskLevel; //风险评级分类
	private String phases; //评级阶段(初评/复评)  E 初评,RE 表复评 
	
	public String getFormStatus() {
		return formStatus;
	}
	
	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}
	
	public String getRiskLevel() {
		return riskLevel;
	}
	
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	
	public String getPhases() {
		return phases;
	}
	
	public void setPhases(String phases) {
		this.phases = phases;
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
