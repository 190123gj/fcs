package com.born.fcs.fm.ws.order.common;

import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 同步金蝶基础数order
 * @author wuzj
 */
public class SyncKingdeeBasicDataOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 645954727618513769L;
	
	/** 组织架构部分 */
	
	/** 部门ID */
	private long deptId;
	/** 部门编码 */
	private String deptCode;
	/** 部门 方面外部已经查出来的部门传进来 */
	private Org dept;
	
	//	/** 合同部分 */
	//	
	//	/** 合同编号 */
	//	private String contractNo;
	//	/** 合同名称 */
	//	private String contractName;
	//	/** 是否系统合同 */
	//	private boolean isSystemContract = true;
	//	
	//	/** 职员部门 */
	//	private long userId;
	//	
	//	private String userName;
	//	
	//	
	//	/** 客户部分 **/
	//	private String customerId;
	//	
	//	private String customerName;
	
	public long getDeptId() {
		return deptId;
	}
	
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}
	
	public String getDeptCode() {
		return deptCode;
	}
	
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	public Org getDept() {
		return dept;
	}
	
	public void setDept(Org dept) {
		this.dept = dept;
	}
	
	//	public String getContractNo() {
	//		return contractNo;
	//	}
	//	
	//	public void setContractNo(String contractNo) {
	//		this.contractNo = contractNo;
	//	}
	//	
	//	public String getContractName() {
	//		return contractName;
	//	}
	//	
	//	public void setContractName(String contractName) {
	//		this.contractName = contractName;
	//	}
	//	
	//	public long getUserId() {
	//		return userId;
	//	}
	//	
	//	public void setUserId(long userId) {
	//		this.userId = userId;
	//	}
	//	
	//	public String getUserName() {
	//		return userName;
	//	}
	//	
	//	public void setUserName(String userName) {
	//		this.userName = userName;
	//	}
	//	
	//	public String getCustomerId() {
	//		return customerId;
	//	}
	//	
	//	public void setCustomerId(String customerId) {
	//		this.customerId = customerId;
	//	}
	//	
	//	public String getCustomerName() {
	//		return customerName;
	//	}
	//	
	//	public void setCustomerName(String customerName) {
	//		this.customerName = customerName;
	//	}
	//	
	//	public boolean isSystemContract() {
	//		return isSystemContract;
	//	}
	//	
	//	public void setSystemContract(boolean isSystemContract) {
	//		this.isSystemContract = isSystemContract;
	//	}
	//	
}
