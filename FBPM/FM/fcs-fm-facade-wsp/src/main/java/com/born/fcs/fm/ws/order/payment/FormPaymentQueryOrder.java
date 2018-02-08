package com.born.fcs.fm.ws.order.payment;

import com.born.fcs.fm.ws.enums.SourceFormEnum;
import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.enums.FormStatusEnum;

/**
 * 收款单信息
 * @author wuzj
 * 
 */
public class FormPaymentQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = -1323538293554991765L;
	/** 收款单ID */
	private long paymentId;
	/** 单据号 */
	private String billNo;
	/** 来源表单 */
	private SourceFormEnum sourceForm;
	/** 客户经理、经办人ID */
	private long applyUserId;
	/** 客户经理、经办人账号 */
	private String applyUserAccount;
	/** 客户经理、经办人名字 */
	private String applyUserName;
	/** 客户经理、经办人部门ID */
	private long applyDeptId;
	/** 客户经理、经办人部门编码 */
	private String applyDeptCode;
	/** 客户经理、经办人部门名字 */
	private String applyDeptName;
	/** 表单状态 */
	private FormStatusEnum formStatus;
	/** 是否查询费用明细 */
	private boolean queryFeeDetail;
	/** 凭证号 */
	private String voucherNo;
	/** 凭证号状态 */
	private String voucherStatus;
	/** 项目编号 */
	private String projectCode;
	/** 简要流程 IS/NO */
	private String isSimple;
	
	public long getPaymentId() {
		return this.paymentId;
	}
	
	public void setPaymentId(long paymentId) {
		this.paymentId = paymentId;
	}
	
	public String getBillNo() {
		return billNo;
	}
	
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	public String getVoucherStatus() {
		return this.voucherStatus;
	}
	
	public void setVoucherStatus(String voucherStatus) {
		this.voucherStatus = voucherStatus;
	}
	
	public SourceFormEnum getSourceForm() {
		return sourceForm;
	}
	
	public void setSourceForm(SourceFormEnum sourceForm) {
		this.sourceForm = sourceForm;
	}
	
	public long getApplyUserId() {
		return applyUserId;
	}
	
	public void setApplyUserId(long applyUserId) {
		this.applyUserId = applyUserId;
	}
	
	public String getApplyUserAccount() {
		return applyUserAccount;
	}
	
	public void setApplyUserAccount(String applyUserAccount) {
		this.applyUserAccount = applyUserAccount;
	}
	
	public String getApplyUserName() {
		return applyUserName;
	}
	
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	
	public long getApplyDeptId() {
		return applyDeptId;
	}
	
	public void setApplyDeptId(long applyDeptId) {
		this.applyDeptId = applyDeptId;
	}
	
	public String getApplyDeptCode() {
		return applyDeptCode;
	}
	
	public void setApplyDeptCode(String applyDeptCode) {
		this.applyDeptCode = applyDeptCode;
	}
	
	public String getApplyDeptName() {
		return applyDeptName;
	}
	
	public void setApplyDeptName(String applyDeptName) {
		this.applyDeptName = applyDeptName;
	}
	
	public FormStatusEnum getFormStatus() {
		return formStatus;
	}
	
	public void setFormStatus(FormStatusEnum formStatus) {
		this.formStatus = formStatus;
	}
	
	public boolean isQueryFeeDetail() {
		return queryFeeDetail;
	}
	
	public void setQueryFeeDetail(boolean queryFeeDetail) {
		this.queryFeeDetail = queryFeeDetail;
	}
	
	public String getVoucherNo() {
		return voucherNo;
	}
	
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getIsSimple() {
		return isSimple;
	}
	
	public void setIsSimple(String isSimple) {
		this.isSimple = isSimple;
	}
	
}
