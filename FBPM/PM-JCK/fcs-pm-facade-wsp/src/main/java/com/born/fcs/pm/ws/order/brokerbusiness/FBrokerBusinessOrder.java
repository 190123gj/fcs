package com.born.fcs.pm.ws.order.brokerbusiness;

import com.born.fcs.pm.ws.order.base.FormOrderBase;

/**
 * 经纪业务
 * @author wuzj
 *
 */
public class FBrokerBusinessOrder extends FormOrderBase {
	
	private static final long serialVersionUID = -4333306864668409301L;
	
	/** 主键 */
	private long id;
	/** 项目编号 */
	private String projectCode;
	/** 客户名称 */
	private String customerName;
	/** 摘要 */
	private String summary;
	/** 是否需要上会 */
	private String isNeedCouncil;
	
	private String contractUrl;
	
	@Override
	public void check() {
		validateHasText(customerName, "项客户名称");
		validateHasText(summary, "摘要");
		validateHasText(isNeedCouncil, "是否申请上会");
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public String getIsNeedCouncil() {
		return isNeedCouncil;
	}
	
	public void setIsNeedCouncil(String isNeedCouncil) {
		this.isNeedCouncil = isNeedCouncil;
	}
	
	public String getContractUrl() {
		return contractUrl;
	}
	
	public void setContractUrl(String contractUrl) {
		this.contractUrl = contractUrl;
	}
	
}
