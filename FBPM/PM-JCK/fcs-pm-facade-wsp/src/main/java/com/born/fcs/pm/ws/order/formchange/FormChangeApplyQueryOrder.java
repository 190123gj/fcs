package com.born.fcs.pm.ws.order.formchange;

import java.util.List;

import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.enums.BooleanEnum;

/**
 * 项目签报查询
 * @author wuzj
 */
public class FormChangeApplyQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = -4771850133705425573L;
	
	private Long formId;
	
	private Long applyId;
	
	private String formStatus;
	
	private List<String> statusList;
	
	/** 申请类型 */
	private String applyType;
	
	/** 签报表单 */
	private String changeForm;
	
	/** 签报表单ID */
	private Long changeFormId;
	
	/** 签报事项 */
	private String applyTitle;
	
	/** 签报编号 */
	private String applyCode;
	
	/** 状态 */
	private String status;
	
	/** 是否选择收费 */
	private BooleanEnum isSelForCharge;
	
	/** 是否需要上会 */
	private BooleanEnum isNeedCouncil;
	
	/** 是否查询信惠流程 */
	private BooleanEnum isSelXinHui;;
	
	/** 是否查询修改记录 */
	private boolean queryRecord;
	
	public Long getFormId() {
		return this.formId;
	}
	
	public void setFormId(Long formId) {
		this.formId = formId;
	}
	
	public Long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}
	
	public Long getChangeFormId() {
		return this.changeFormId;
	}
	
	public void setChangeFormId(Long changeFormId) {
		this.changeFormId = changeFormId;
	}
	
	public String getFormStatus() {
		return this.formStatus;
	}
	
	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}
	
	public List<String> getStatusList() {
		return this.statusList;
	}
	
	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}
	
	public String getApplyType() {
		return this.applyType;
	}
	
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	
	public String getChangeForm() {
		return this.changeForm;
	}
	
	public void setChangeForm(String changeForm) {
		this.changeForm = changeForm;
	}
	
	public String getApplyTitle() {
		return this.applyTitle;
	}
	
	public void setApplyTitle(String applyTitle) {
		this.applyTitle = applyTitle;
	}
	
	public String getApplyCode() {
		return this.applyCode;
	}
	
	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}
	
	public BooleanEnum getIsSelForCharge() {
		return this.isSelForCharge;
	}
	
	public void setIsSelForCharge(BooleanEnum isSelForCharge) {
		this.isSelForCharge = isSelForCharge;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public BooleanEnum getIsNeedCouncil() {
		return this.isNeedCouncil;
	}
	
	public void setIsNeedCouncil(BooleanEnum isNeedCouncil) {
		this.isNeedCouncil = isNeedCouncil;
	}
	
	public BooleanEnum getIsSelXinHui() {
		return this.isSelXinHui;
	}
	
	public void setIsSelXinHui(BooleanEnum isSelXinHui) {
		this.isSelXinHui = isSelXinHui;
	}
	
	public boolean isQueryRecord() {
		return this.queryRecord;
	}
	
	public void setQueryRecord(boolean queryRecord) {
		this.queryRecord = queryRecord;
	}
	
}
