package com.born.fcs.pm.ws.order.council;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 会议纪要 - 文字授信条件
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryProjectTextCreditConditionOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -354614497579082048L;
	/** 主键 */
	private Long id;
	/** 会议纪要项目ID */
	private Long spId;
	/** 文字授信条件 */
	private String content;
	/** 备注 */
	private String remark;
	
	public boolean isNull() {
		return isNull(content);
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getSpId() {
		return this.spId;
	}
	
	public void setSpId(Long spId) {
		this.spId = spId;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
