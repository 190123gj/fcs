package com.born.fcs.pm.ws.order.setup;

import com.born.fcs.pm.ws.order.base.FormOrderBase;

/**
 * 
 * 项目立项审核Order
 *
 * @author wuzj
 *
 */
public class SetupFormAuditOrder extends FormOrderBase {
	
	private static final long serialVersionUID = -82440298718562849L;
	
	/**
	 * 风险经理
	 */
	private Long riskManagerId;
	
	private String riskManagerAccount;
	
	private String riskManagerName;
	
	/**
	 * 客户经理（更换）
	 */
	private Long busiManagerId;
	
	private String busiManagerAccount;
	
	private String busiManagerName;
	
	/**
	 * 客户经理B角
	 */
	private Long busiManagerbId;
	
	private String busiManagerbAccount;
	
	private String busiManagerbName;
	
	/**
	 * 评估公司
	 */
	private Long evaluateCompanyId;
	
	private String evaluateCompanyName;
	
	@Override
	public void check() {
		validateNotNull(userId, "用户");
		validateHasZore(userId, "用户");
		validateNotNull(formId, "表单ID");
		validateHasZore(formId, "表单ID");
	}
	
	public Long getRiskManagerId() {
		return this.riskManagerId;
	}
	
	public void setRiskManagerId(Long riskManagerId) {
		this.riskManagerId = riskManagerId;
	}
	
	public String getRiskManagerAccount() {
		return this.riskManagerAccount;
	}
	
	public void setRiskManagerAccount(String riskManagerAccount) {
		this.riskManagerAccount = riskManagerAccount;
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
	
	public String getBusiManagerAccount() {
		return this.busiManagerAccount;
	}
	
	public void setBusiManagerAccount(String busiManagerAccount) {
		this.busiManagerAccount = busiManagerAccount;
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
	
	public String getBusiManagerbAccount() {
		return this.busiManagerbAccount;
	}
	
	public void setBusiManagerbAccount(String busiManagerbAccount) {
		this.busiManagerbAccount = busiManagerbAccount;
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
