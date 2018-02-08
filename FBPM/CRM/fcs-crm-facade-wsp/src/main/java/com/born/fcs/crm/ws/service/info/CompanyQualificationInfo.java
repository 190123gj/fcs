package com.born.fcs.crm.ws.service.info;

/**
 * 公司客户获得的资质证书
 * */
public class CompanyQualificationInfo {
	
	/** 主键ID */
	private Long id;
	/** 客户查询ID */
	private String customerId;
	/** 资质证书名 */
	private String qualificationName;
	/** 资质证书编号 */
	private String qualificationCode;
	/** 证书有效期 */
	private String experDate;
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public String getQualificationName() {
		return this.qualificationName;
	}
	
	public void setQualificationName(String qualificationName) {
		this.qualificationName = qualificationName;
	}
	
	public String getQualificationCode() {
		return this.qualificationCode;
	}
	
	public void setQualificationCode(String qualificationCode) {
		this.qualificationCode = qualificationCode;
	}
	
	public String getExperDate() {
		return this.experDate;
	}
	
	public void setExperDate(String experDate) {
		this.experDate = experDate;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CompanyQualificationInfo [id=");
		builder.append(id);
		builder.append(", customerId=");
		builder.append(customerId);
		builder.append(", qualificationName=");
		builder.append(qualificationName);
		builder.append(", qualificationCode=");
		builder.append(qualificationCode);
		builder.append(", experDate=");
		builder.append(experDate);
		builder.append("]");
		return builder.toString();
	}
	
}
