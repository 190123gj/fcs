package com.born.fcs.pm.ws.order.finvestigation;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.finvestigation.base.FInvestigationBaseOrder;

/**
 * 风险分析 结论意见
 * 
 * @author lirz
 *
 * 2016-3-11 上午9:32:47
 */
public class FInvestigationRiskOrder extends FInvestigationBaseOrder{

	private static final long serialVersionUID = 8311191315432035245L;

	private long riskId;
	private String riskAnalysis; //风险点分析
	private String conclusion; //结论意见

	public long getRiskId() {
		return riskId;
	}
	
	public void setRiskId(long riskId) {
		this.riskId = riskId;
	}

	public String getRiskAnalysis() {
		return riskAnalysis;
	}
	
	public void setRiskAnalysis(String riskAnalysis) {
		this.riskAnalysis = riskAnalysis;
	}

	public String getConclusion() {
		return conclusion;
	}
	
	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
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
