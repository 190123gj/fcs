package com.born.fcs.pm.ws.order.council;

import com.born.fcs.pm.ws.order.base.FormOrderBase;

/**
 * 项目复议申请Order
 *
 *
 * @author wuzj
 *
 */
public class FReCouncilApplyOrder extends FormOrderBase {
	
	private static final long serialVersionUID = -1132195019815619097L;
	
	private long id;
	
	private String projectCode;
	
	private long oldSpId;
	
	private String oldSpCode;
	
	private String contentReason;
	
	private String overview;
	
	@Override
	public void check() {
		validateHasText(projectCode, "项目编号");
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public long getOldSpId() {
		return this.oldSpId;
	}
	
	public void setOldSpId(long oldSpId) {
		this.oldSpId = oldSpId;
	}
	
	public String getOldSpCode() {
		return this.oldSpCode;
	}
	
	public void setOldSpCode(String oldSpCode) {
		this.oldSpCode = oldSpCode;
	}
	
	public String getContentReason() {
		return this.contentReason;
	}
	
	public void setContentReason(String contentReason) {
		this.contentReason = contentReason;
	}
	
	public String getOverview() {
		return this.overview;
	}
	
	public void setOverview(String overview) {
		this.overview = overview;
	}
}
