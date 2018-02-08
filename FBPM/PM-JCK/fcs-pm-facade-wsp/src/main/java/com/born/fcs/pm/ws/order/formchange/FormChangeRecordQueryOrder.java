package com.born.fcs.pm.ws.order.formchange;

import java.util.List;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 签报记录
 * 
 * @author wuzj
 */
public class FormChangeRecordQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = 5362903944320123648L;
	
	/** 记录ID */
	private long recordId;
	
	/** 签报申请formId */
	private long applyFormId;
	
	/** 页面索引 */
	private int tabIndex;
	
	/** 当前会话ID（同一个会话只保存一次） */
	private String sessionId;
	
	private String status;
	
	private List<String> statusList;
	
	private long userId;
	
	private boolean queryDetail;
	
	public long getRecordId() {
		return this.recordId;
	}
	
	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}
	
	public long getApplyFormId() {
		return this.applyFormId;
	}
	
	public void setApplyFormId(long applyFormId) {
		this.applyFormId = applyFormId;
	}
	
	public int getTabIndex() {
		return this.tabIndex;
	}
	
	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}
	
	public String getSessionId() {
		return this.sessionId;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public long getUserId() {
		return this.userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public boolean isQueryDetail() {
		return this.queryDetail;
	}
	
	public void setQueryDetail(boolean queryDetail) {
		this.queryDetail = queryDetail;
	}
	
	public List<String> getStatusList() {
		return this.statusList;
	}
	
	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}
	
}
