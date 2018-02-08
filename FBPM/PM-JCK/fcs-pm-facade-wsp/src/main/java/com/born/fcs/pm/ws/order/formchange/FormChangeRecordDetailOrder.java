package com.born.fcs.pm.ws.order.formchange;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 签报修改记录
 *
 * @author wuzj
 * 
 */
public class FormChangeRecordDetailOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 492865019907226846L;
	
	private long detailId;
	
	private long recordId;
	
	private String label;
	
	private String name;
	
	private String oldText;
	
	private String oldValue;
	
	private String newValue;
	
	private String newText;
	
	public long getDetailId() {
		return this.detailId;
	}
	
	public void setDetailId(long detailId) {
		this.detailId = detailId;
	}
	
	public long getRecordId() {
		return this.recordId;
	}
	
	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getOldText() {
		return this.oldText;
	}
	
	public void setOldText(String oldText) {
		this.oldText = oldText;
	}
	
	public String getOldValue() {
		return this.oldValue;
	}
	
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	
	public String getNewValue() {
		return this.newValue;
	}
	
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
	public String getNewText() {
		return this.newText;
	}
	
	public void setNewText(String newText) {
		this.newText = newText;
	}
}
