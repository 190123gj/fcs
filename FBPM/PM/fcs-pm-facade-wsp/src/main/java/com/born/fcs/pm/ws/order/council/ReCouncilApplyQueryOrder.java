package com.born.fcs.pm.ws.order.council;

import java.util.ArrayList;
import java.util.List;

import com.born.fcs.pm.ws.base.QueryProjectBase;

/**
 * 项目复议申请Order
 *
 *
 * @author wuzj
 *
 */
public class ReCouncilApplyQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = 2011860030809676051L;
	
	private long applyId;
	
	private long formId;
	
	private String status;
	
	private List<String> statusList = new ArrayList<String>();
	
	public long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public List<String> getStatusList() {
		return this.statusList;
	}
	
	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
}
