package com.born.fcs.fm.ws.order.payment;

import java.util.Date;
import java.util.List;

import org.springframework.util.Assert;

import com.born.fcs.fm.ws.enums.FormSourceEnum;
import com.born.fcs.fm.ws.enums.SourceFormEnum;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.order.base.FormOrderBase;

/**
 * 付款单信息
 * @author wuzj
 * 
 */
public class FormPaymentOrder extends FormOrderBase {
	
	private static final long serialVersionUID = -1323538293554991765L;
	/** 收款单ID */
	private Long paymentId;
	/** 表单来源 */
	private FormSourceEnum formSource;
	/** 来源表单 */
	private SourceFormEnum sourceForm;
	/** 表单来源ID */
	private String sourceFormId;
	/** 付款日期 */
	private Date paymentDate;
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
	/**
	 * 简单流程（IS/NO）
	 */
	private String isSimple;
	/** 收款明细 */
	List<FormPaymentFeeOrder> feeOrder;
	
	/** 是否只修改明细 */
	private BooleanEnum onlyChangeDetailList;
	
	@Override
	public void check() {
		if (checkStatus == 1) {
			validateNotNull(formSource, "单据来源");
			//validateNotNull(paymentDate, "付款日期");
			if (formSource == FormSourceEnum.FORM) {
				Assert.isTrue(sourceFormId != null, "表单来源不能为空");
			} else {
				Assert.isTrue(applyUserId > 0, "经办人不能为空");
				Assert.isTrue(applyDeptId > 0, "经办部门不能为空");
			}
		}
	}
	
	public Long getPaymentId() {
		return this.paymentId;
	}
	
	public BooleanEnum getOnlyChangeDetailList() {
		return this.onlyChangeDetailList;
	}
	
	public void setOnlyChangeDetailList(BooleanEnum onlyChangeDetailList) {
		this.onlyChangeDetailList = onlyChangeDetailList;
	}
	
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	
	public FormSourceEnum getFormSource() {
		return formSource;
	}
	
	public void setFormSource(FormSourceEnum formSource) {
		this.formSource = formSource;
	}
	
	public SourceFormEnum getSourceForm() {
		return this.sourceForm;
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
	
	public Date getPaymentDate() {
		return this.paymentDate;
	}
	
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
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
	
	public List<FormPaymentFeeOrder> getFeeOrder() {
		return this.feeOrder;
	}
	
	public void setFeeOrder(List<FormPaymentFeeOrder> feeOrder) {
		this.feeOrder = feeOrder;
	}

	public String getIsSimple() {
		return isSimple;
	}

	public void setIsSimple(String isSimple) {
		this.isSimple = isSimple;
	}
	
}
