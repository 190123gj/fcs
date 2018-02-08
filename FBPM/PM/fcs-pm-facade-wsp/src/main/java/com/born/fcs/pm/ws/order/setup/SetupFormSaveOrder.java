package com.born.fcs.pm.ws.order.setup;

import com.born.fcs.pm.ws.order.base.ProjectFormOrderBase;

/**
 * 
 * 项目立项审核Order
 *
 * @author wuzj
 *
 */
public class SetupFormSaveOrder extends ProjectFormOrderBase {
	
	private static final long serialVersionUID = -82440298718562849L;
	
	//业务类型
	protected String busiType;
	
	//业务类型名称（其他填写）
	protected String busiTypeName;
	
	//客户类型
	protected String customerType;
	
	//是否从反担保过来的
	private boolean isSaveCounterGuarantee;
	
	//其他反担保措施
	private String otherCounterGuarantee;
	
	@Override
	public void check() {
		validateHasText(busiType, "业务类型");
	}
	
	public String getBusiType() {
		return this.busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getBusiTypeName() {
		return this.busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	public String getCustomerType() {
		return this.customerType;
	}
	
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	public boolean isSaveCounterGuarantee() {
		return this.isSaveCounterGuarantee;
	}
	
	public void setSaveCounterGuarantee(boolean isSaveCounterGuarantee) {
		this.isSaveCounterGuarantee = isSaveCounterGuarantee;
	}
	
	public String getOtherCounterGuarantee() {
		return this.otherCounterGuarantee;
	}
	
	public void setOtherCounterGuarantee(String otherCounterGuarantee) {
		this.otherCounterGuarantee = otherCounterGuarantee;
	}
	
}
