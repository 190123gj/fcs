package com.born.fcs.pm.ws.order.transfer;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 选择项目接收人
 * @author wuzj
 */
public class ProjectTransferAcceptOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 3961261462997193272L;
	
	/** 主键 */
	private long id;
	/** 接受人员 */
	private long acceptUserId;
	private String acceptUserAccount;
	private String acceptUserName;
	/** 接受人员部门 */
	private long acceptUserDeptId;
	private String acceptUserDeptName;
	
	/** 接受人员B门 */
	private long acceptUserbId;
	private String acceptUserbAccount;
	private String acceptUserbName;
	/** 接受人员B部门 */
	private long acceptUserbDeptId;
	private String acceptUserbDeptName;
	
	@Override
	public void check() {
		validateGreaterThan(id, "移交ID");
		validateGreaterThan(acceptUserId, "接收人员 ID");
		validateGreaterThan(acceptUserbId, "接收人员B ID");
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getAcceptUserId() {
		return this.acceptUserId;
	}
	
	public void setAcceptUserId(long acceptUserId) {
		this.acceptUserId = acceptUserId;
	}
	
	public String getAcceptUserAccount() {
		return this.acceptUserAccount;
	}
	
	public void setAcceptUserAccount(String acceptUserAccount) {
		this.acceptUserAccount = acceptUserAccount;
	}
	
	public String getAcceptUserName() {
		return this.acceptUserName;
	}
	
	public void setAcceptUserName(String acceptUserName) {
		this.acceptUserName = acceptUserName;
	}
	
	public long getAcceptUserDeptId() {
		return this.acceptUserDeptId;
	}
	
	public void setAcceptUserDeptId(long acceptUserDeptId) {
		this.acceptUserDeptId = acceptUserDeptId;
	}
	
	public String getAcceptUserDeptName() {
		return this.acceptUserDeptName;
	}
	
	public void setAcceptUserDeptName(String acceptUserDeptName) {
		this.acceptUserDeptName = acceptUserDeptName;
	}
	
	public long getAcceptUserbId() {
		return this.acceptUserbId;
	}
	
	public void setAcceptUserbId(long acceptUserbId) {
		this.acceptUserbId = acceptUserbId;
	}
	
	public String getAcceptUserbAccount() {
		return this.acceptUserbAccount;
	}
	
	public void setAcceptUserbAccount(String acceptUserbAccount) {
		this.acceptUserbAccount = acceptUserbAccount;
	}
	
	public String getAcceptUserbName() {
		return this.acceptUserbName;
	}
	
	public void setAcceptUserbName(String acceptUserbName) {
		this.acceptUserbName = acceptUserbName;
	}
	
	public long getAcceptUserbDeptId() {
		return this.acceptUserbDeptId;
	}
	
	public void setAcceptUserbDeptId(long acceptUserbDeptId) {
		this.acceptUserbDeptId = acceptUserbDeptId;
	}
	
	public String getAcceptUserbDeptName() {
		return this.acceptUserbDeptName;
	}
	
	public void setAcceptUserbDeptName(String acceptUserbDeptName) {
		this.acceptUserbDeptName = acceptUserbDeptName;
	}
}
