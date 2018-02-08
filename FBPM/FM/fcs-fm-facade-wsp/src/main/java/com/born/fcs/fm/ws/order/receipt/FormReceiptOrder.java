package com.born.fcs.fm.ws.order.receipt;

import java.util.Date;
import java.util.List;

import org.springframework.util.Assert;

import com.born.fcs.fm.ws.enums.FormSourceEnum;
import com.born.fcs.fm.ws.enums.SourceFormEnum;
import com.born.fcs.pm.ws.order.base.FormOrderBase;

/**
 * 收款单信息
 * @author wuzj
 * 
 */
public class FormReceiptOrder extends FormOrderBase {
	
	private static final long serialVersionUID = -1323538293554991765L;
	/** 收款单ID */
	private Long receiptId;
	/** 表单来源 */
	private FormSourceEnum formSource;
	/** 来源表单 */
	private SourceFormEnum sourceForm;
	/** 表单来源ID */
	private String sourceFormId;
	/** 收款日期 */
	private Date receiptDate;
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
	/** 收款事由、备注 */
	private String remark;
	
	/** 收款明细 */
	List<FormReceiptFeeOrder> feeOrder;
	
	@Override
	public void check() {
		if (checkStatus == 1) {
			validateNotNull(formSource, "单据来源");
			//validateNotNull(receiptDate, "收款日期");
			if (formSource == FormSourceEnum.FORM) {
				validateNotNull(sourceForm, "来源单据");
				Assert.isTrue(sourceFormId != null, "来源单据不能为空");
			} else {
				Assert.isTrue(applyUserId > 0, "经办人不能为空");
				Assert.isTrue(applyDeptId > 0, "经办部门不能为空");
			}
			Assert.isTrue(feeOrder != null && feeOrder.size() > 0, "收款明细不能为空");
		}
	}
	
	public Long getReceiptId() {
		return receiptId;
	}
	
	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}
	
	public FormSourceEnum getFormSource() {
		return formSource;
	}
	
	public void setFormSource(FormSourceEnum formSource) {
		this.formSource = formSource;
	}
	
	public SourceFormEnum getSourceForm() {
		return sourceForm;
	}
	
	public void setSourceForm(SourceFormEnum sourceForm) {
		this.sourceForm = sourceForm;
	}
	
	public String getSourceFormId() {
		return this.sourceFormId;
	}
	
	public void setSourceFormId(String sourceFormId) {
		this.sourceFormId = sourceFormId;
	}
	
	public Date getReceiptDate() {
		return receiptDate;
	}
	
	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
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
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public List<FormReceiptFeeOrder> getFeeOrder() {
		return feeOrder;
	}
	
	public void setFeeOrder(List<FormReceiptFeeOrder> feeOrder) {
		this.feeOrder = feeOrder;
	}
	
}
