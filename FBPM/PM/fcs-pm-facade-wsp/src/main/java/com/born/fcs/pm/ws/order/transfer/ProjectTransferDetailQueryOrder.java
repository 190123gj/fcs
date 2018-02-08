package com.born.fcs.pm.ws.order.transfer;

import java.util.Date;

import com.born.fcs.pm.ws.base.QueryPermissionPageBase;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectTransferStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectTransferTypeEnum;

/**
 * 项目移交详细查询Order
 * @author wuzj
 */
public class ProjectTransferDetailQueryOrder extends QueryPermissionPageBase {
	
	private static final long serialVersionUID = 8276779908823091983L;
	
	/** 表单ID */
	private long formId;
	/** 客户ID */
	private long customerId;
	/** 单据状态 */
	private FormStatusEnum formStatus;
	/** 移交状态 */
	private ProjectTransferStatusEnum transferStatus;
	/** 移交类型 */
	private ProjectTransferTypeEnum transferType;
	/** 项目编号 */
	private String projectCode;
	/** 项目名称 */
	private String projectName;
	/** 客户名称 */
	private String customerName;
	/** 业务类型 */
	private String busiType;
	private String busiTypeName;
	
	/** 移交时间 */
	private Date transferTimeStart;
	private Date transferTimeEnd;
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public long getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public FormStatusEnum getFormStatus() {
		return this.formStatus;
	}
	
	public void setFormStatus(FormStatusEnum formStatus) {
		this.formStatus = formStatus;
	}
	
	public ProjectTransferStatusEnum getTransferStatus() {
		return this.transferStatus;
	}
	
	public void setTransferStatus(ProjectTransferStatusEnum transferStatus) {
		this.transferStatus = transferStatus;
	}
	
	public ProjectTransferTypeEnum getTransferType() {
		return this.transferType;
	}
	
	public void setTransferType(ProjectTransferTypeEnum transferType) {
		this.transferType = transferType;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getBusiType() {
		return this.busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getBusiTypeName() {
		return this.busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	public Date getTransferTimeStart() {
		return this.transferTimeStart;
	}
	
	public void setTransferTimeStart(Date transferTimeStart) {
		this.transferTimeStart = transferTimeStart;
	}
	
	public Date getTransferTimeEnd() {
		return this.transferTimeEnd;
	}
	
	public void setTransferTimeEnd(Date transferTimeEnd) {
		this.transferTimeEnd = transferTimeEnd;
	}
	
}
