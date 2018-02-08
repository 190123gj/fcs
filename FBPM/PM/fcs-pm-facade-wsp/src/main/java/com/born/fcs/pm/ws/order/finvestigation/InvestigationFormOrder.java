package com.born.fcs.pm.ws.order.finvestigation;

import com.born.fcs.pm.ws.order.base.FormOrderBase;

/**
 * 尽职调查报告
 * 
 * @author lirz
 *
 * 2016-3-9 下午4:48:50
 */
public class InvestigationFormOrder extends FormOrderBase {
	
	private static final long serialVersionUID = 4677571144276826447L;

	/**
	 * 风险经理
	 */
	private Long riskManagerId;
	
	private String riskManagerName;
	
	/**
	 * 客户经理（更换）
	 */
	private Long busiManagerId;
	
	private String busiManagerName;
	
	/**
	 * 客户经理B角
	 */
	private Long busiManagerbId;
	
	private String busiManagerbName;
	
	/**
	 * 评估公司
	 */
	private Long evaluateCompanyId;
	
	private String evaluateCompanyName;
	
	public Long getRiskManagerId() {
		return this.riskManagerId;
	}
	
	public void setRiskManagerId(Long riskManagerId) {
		this.riskManagerId = riskManagerId;
	}
	
	public String getRiskManagerName() {
		return this.riskManagerName;
	}
	
	public void setRiskManagerName(String riskManagerName) {
		this.riskManagerName = riskManagerName;
	}
	
	public Long getBusiManagerId() {
		return this.busiManagerId;
	}
	
	public void setBusiManagerId(Long busiManagerId) {
		this.busiManagerId = busiManagerId;
	}
	
	public String getBusiManagerName() {
		return this.busiManagerName;
	}
	
	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}
	
	public Long getBusiManagerbId() {
		return this.busiManagerbId;
	}
	
	public void setBusiManagerbId(Long busiManagerbId) {
		this.busiManagerbId = busiManagerbId;
	}
	
	public String getBusiManagerbName() {
		return this.busiManagerbName;
	}
	
	public void setBusiManagerbName(String busiManagerbName) {
		this.busiManagerbName = busiManagerbName;
	}
	
	public Long getEvaluateCompanyId() {
		return this.evaluateCompanyId;
	}
	
	public void setEvaluateCompanyId(Long evaluateCompanyId) {
		this.evaluateCompanyId = evaluateCompanyId;
	}
	
	public String getEvaluateCompanyName() {
		return this.evaluateCompanyName;
	}
	
	public void setEvaluateCompanyName(String evaluateCompanyName) {
		this.evaluateCompanyName = evaluateCompanyName;
	}
}
