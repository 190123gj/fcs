package com.born.fcs.pm.ws.order.financialproject;

import com.born.fcs.pm.ws.enums.ContractStatusEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 理财项目合同Order
 * @author wuzj
 */
public class ProjectFinancialContractStatusOrder extends ProcessOrder {
	
	private static final long serialVersionUID = -1067515265088760245L;
	/** 合同 */
	private long formId;
	/** 合同状态 （待定） */
	private ContractStatusEnum contractStatus;
	/** 回传文件 */
	private String contractReturn;
	
	@Override
	public void check() {
		validateGreaterThan(formId, "合同申请单ID");
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
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
}
