package com.born.fcs.crm.ws.service.info;

import java.util.Date;

/** 渠道信息 */
public class ChannalContractInfo extends FormInfo {
	
	private static final long serialVersionUID = 7839576210759869385L;
	/** 合同编号 */
	private String contractNo;
	/** 渠道号 */
	private String channalCode;
	/** 渠道名 */
	private String channalName;
	/** 渠道类型 */
	private String channalType;
	/** 合同 */
	private String contract;
	/** 回传合同 */
	private String contractReturn;
	/** 备用 */
	private String info1;
	/** 备用 */
	private String info2;
	
	/** 提交时间 */
	private Date rawAddTime;
	
	public String getContractReturn() {
		return this.contractReturn;
	}
	
	public void setContractReturn(String contractReturn) {
		this.contractReturn = contractReturn;
	}
	
	public String getInfo1() {
		return this.info1;
	}
	
	public void setInfo1(String info1) {
		this.info1 = info1;
	}
	
	public String getInfo2() {
		return this.info2;
	}
	
	public void setInfo2(String info2) {
		this.info2 = info2;
	}
	
	public String getContractNo() {
		return this.contractNo;
	}
	
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	public String getChannalCode() {
		return this.channalCode;
	}
	
	public void setChannalCode(String channalCode) {
		this.channalCode = channalCode;
	}
	
	public String getChannalName() {
		return this.channalName;
	}
	
	public void setChannalName(String channalName) {
		this.channalName = channalName;
	}
	
	public String getChannalType() {
		return this.channalType;
	}
	
	public void setChannalType(String channalType) {
		this.channalType = channalType;
	}
	
	public String getContract() {
		return this.contract;
	}
	
	public void setContract(String contract) {
		this.contract = contract;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChannalContractInfo [contractNo=");
		builder.append(contractNo);
		builder.append(", channalCode=");
		builder.append(channalCode);
		builder.append(", channalName=");
		builder.append(channalName);
		builder.append(", channalType=");
		builder.append(channalType);
		builder.append(", contract=");
		builder.append(contract);
		builder.append(", contractReturn=");
		builder.append(contractReturn);
		builder.append(", info1=");
		builder.append(info1);
		builder.append(", info2=");
		builder.append(info2);
		builder.append(", rawAddTime=");
		builder.append(rawAddTime);
		builder.append("]");
		return builder.toString();
	}
	
}
