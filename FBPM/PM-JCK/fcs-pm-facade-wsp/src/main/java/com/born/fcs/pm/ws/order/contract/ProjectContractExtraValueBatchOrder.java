/**
 * 
 */
package com.born.fcs.pm.ws.order.contract;

import java.util.List;

import com.born.fcs.pm.ws.info.contract.ProjectContractExtraValueInfo;
import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * @author jiajie
 * 
 */
public class ProjectContractExtraValueBatchOrder extends ProcessOrder {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 合同集id */
	private long contractId;
	/** 合同id */
	private long contractItemId;
	/** 合同编号 */
	private String contractCode;
	/** 合同模版id */
	private long templateId;

	private List<ProjectContractExtraValueInfo> projectContractExtraValue;

	public long getContractId() {
		return contractId;
	}

	public void setContractId(long contractId) {
		this.contractId = contractId;
	}

	public long getContractItemId() {
		return contractItemId;
	}

	public void setContractItemId(long contractItemId) {
		this.contractItemId = contractItemId;
	}

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	public List<ProjectContractExtraValueInfo> getProjectContractExtraValue() {
		return projectContractExtraValue;
	}

	public void setProjectContractExtraValue(
			List<ProjectContractExtraValueInfo> projectContractExtraValue) {
		this.projectContractExtraValue = projectContractExtraValue;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

}
