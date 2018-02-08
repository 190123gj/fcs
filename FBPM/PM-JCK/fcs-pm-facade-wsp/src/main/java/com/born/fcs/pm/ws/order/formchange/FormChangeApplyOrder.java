package com.born.fcs.pm.ws.order.formchange;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FormChangeApplyEnum;
import com.born.fcs.pm.ws.enums.FormChangeApplyTypeEnum;
import com.born.fcs.pm.ws.order.base.FormOrderBase;

/**
 * 签报申请
 * @author wuzj
 */
public class FormChangeApplyOrder extends FormOrderBase {
	
	private static final long serialVersionUID = -7799698370511188602L;
	
	/** 申请ID */
	private Long applyId;
	/** 申请表单ID */
	private Long formId;
	/** 申请类型 */
	private FormChangeApplyTypeEnum applyType;
	/** 项目编号 */
	private String projectCode;
	/** 签报编号 */
	private String applyCode;
	/** 客户ID */
	private long customerId;
	/** 签报表单 */
	private FormChangeApplyEnum changeForm;
	
	private Long changeFormId;
	
	private String changeRemark;
	
	/** 是否需要上会 */
	private BooleanEnum isNeedCouncil;
	
	/** 签报事项 */
	private String applyTitle;
	
	public Long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}
	
	public Long getFormId() {
		return this.formId;
	}
	
	public void setFormId(Long formId) {
		this.formId = formId;
	}
	
	public FormChangeApplyTypeEnum getApplyType() {
		return this.applyType;
	}
	
	public void setApplyType(FormChangeApplyTypeEnum applyType) {
		this.applyType = applyType;
	}
	
	public void setChangeForm(FormChangeApplyEnum changeForm) {
		this.changeForm = changeForm;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public Long getChangeFormId() {
		return this.changeFormId;
	}
	
	public void setChangeFormId(Long changeFormId) {
		this.changeFormId = changeFormId;
	}
	
	public String getChangeRemark() {
		return this.changeRemark;
	}
	
	public void setChangeRemark(String changeRemark) {
		this.changeRemark = changeRemark;
	}
	
	public long getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public FormChangeApplyEnum getChangeForm() {
		return this.changeForm;
	}
	
	public String getApplyCode() {
		return this.applyCode;
	}
	
	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}
	
	public BooleanEnum getIsNeedCouncil() {
		return this.isNeedCouncil;
	}
	
	public void setIsNeedCouncil(BooleanEnum isNeedCouncil) {
		this.isNeedCouncil = isNeedCouncil;
	}
	
	public String getApplyTitle() {
		return this.applyTitle;
	}
	
	public void setApplyTitle(String applyTitle) {
		this.applyTitle = applyTitle;
	}
	
}
