package com.born.fcs.pm.ws.order.setup;


/**
 * 项目立项 - 承销项目详情Order
 *
 * @author wuzj
 *
 */
public class FProjectUnderwritingOrder extends FProjectOrder {
	
	private static final long serialVersionUID = 1045604327579180902L;

	private Long id;
	
	private Long coInstitutionId;
	
	private String coInstitutionName;
	
	private Long letterInstitutionId;
	
	private String letterInstitutionName;
	
	private String repaySource;
	
	private String hasFinancialSupport;
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getCoInstitutionId() {
		return this.coInstitutionId;
	}
	
	public void setCoInstitutionId(Long coInstitutionId) {
		this.coInstitutionId = coInstitutionId;
	}
	
	public String getCoInstitutionName() {
		return this.coInstitutionName;
	}
	
	public void setCoInstitutionName(String coInstitutionName) {
		this.coInstitutionName = coInstitutionName;
	}
	
	public Long getLetterInstitutionId() {
		return this.letterInstitutionId;
	}
	
	public void setLetterInstitutionId(Long letterInstitutionId) {
		this.letterInstitutionId = letterInstitutionId;
	}
	
	public String getLetterInstitutionName() {
		return this.letterInstitutionName;
	}
	
	public void setLetterInstitutionName(String letterInstitutionName) {
		this.letterInstitutionName = letterInstitutionName;
	}
	
	public String getRepaySource() {
		return this.repaySource;
	}
	
	public void setRepaySource(String repaySource) {
		this.repaySource = repaySource;
	}
	
	public String getHasFinancialSupport() {
		return this.hasFinancialSupport;
	}
	
	public void setHasFinancialSupport(String hasFinancialSupport) {
		this.hasFinancialSupport = hasFinancialSupport;
	}
}
