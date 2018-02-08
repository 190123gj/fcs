package com.born.fcs.pm.ws.order.basicmaintain;

import java.util.List;

import com.born.fcs.pm.ws.enums.DataCodeEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 数据字典order
 * 
 * @author wuzj
 */
public class SysDataDictionaryOrder extends ProcessOrder {
	
	private static final long serialVersionUID = -6758947982905470824L;
	
	/** 数据分类 */
	private DataCodeEnum dataCode;
	
	/** 数据明细 */
	List<SysDataDictionaryDetailOrder> dataOrder;
	
	@Override
	public void check() {
		validateNotNull(dataCode, "数据分类");
		validateNotNull(dataOrder, "数据明细");
	}
	
	public DataCodeEnum getDataCode() {
		return this.dataCode;
	}
	
	public void setDataCode(DataCodeEnum dataCode) {
		this.dataCode = dataCode;
	}
	
	public List<SysDataDictionaryDetailOrder> getDataOrder() {
		return this.dataOrder;
	}
	
	public void setDataOrder(List<SysDataDictionaryDetailOrder> dataOrder) {
		this.dataOrder = dataOrder;
	}
	
}
