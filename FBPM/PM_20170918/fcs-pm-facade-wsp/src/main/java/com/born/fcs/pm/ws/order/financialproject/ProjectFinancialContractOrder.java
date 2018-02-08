package com.born.fcs.pm.ws.order.financialproject;

import com.born.fcs.pm.ws.enums.ContractStatusEnum;
import com.born.fcs.pm.ws.order.base.FormOrderBase;

/**
 * 理财项目合同Order
 * @author wuzj
 */
public class ProjectFinancialContractOrder extends FormOrderBase {
	
	private static final long serialVersionUID = 5440048129024836482L;
	/** 合同ID */
	private long contractId;
	/** 项目编号 */
	private String projectCode;
	/** 合同内容 */
	private String contract;
	/** 附件 */
	private String attach;
	/** 合同状态 （待定） */
	private ContractStatusEnum contractStatus;
	/** 回传文件 */
	private String contractReturn;
	/** 备注 */
	private String remark;
	
	public long getContractId() {
		return this.contractId;
	}
	
	public void setContractId(long contractId) {
		this.contractId = contractId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getContract() {
		return this.contract;
	}
	
	public void setContract(String contract) {
		this.contract = contract;
	}
	
	public String getAttach() {
		return this.attach;
	}
	
	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	public ContractStatusEnum getContractStatus() {
		return this.contractStatus;
	}
	
	public void setContractStatus(ContractStatusEnum contractStatus) {
		this.contractStatus = contractStatus;
	}
	
	public String getContractReturn() {
		return this.contractReturn;
	}
	
	public void setContractReturn(String contractReturn) {
		this.contractReturn = contractReturn;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
