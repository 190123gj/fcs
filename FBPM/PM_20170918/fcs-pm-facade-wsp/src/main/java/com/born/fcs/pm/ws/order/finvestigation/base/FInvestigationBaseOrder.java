package com.born.fcs.pm.ws.order.finvestigation.base;

import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.CheckPointEnum;
import com.born.fcs.pm.ws.enums.InvestigationEnum;
import com.born.fcs.pm.ws.order.base.ProjectFormOrderBase;

/**
 * 
 * 尽调base order
 * 
 * @author lirz
 * 
 * 2017-3-24 上午10:52:15
 * 
 */
public class FInvestigationBaseOrder extends ProjectFormOrderBase {
	
	private static final long serialVersionUID = -4683586180354187158L;
	
	private String checkPoint = CheckPointEnum.SOURCE.code(); //审核节点
	private InvestigationEnum pageIndex; //页签标识 InvestigationEnum
	
	private Map<String, Object> formCustomizeFieldMap; //用以保存源数据
	
	public String getCheckPoint() {
		return this.checkPoint;
	}
	
	public void setCheckPoint(String checkPoint) {
		this.checkPoint = checkPoint;
	}
	
	public InvestigationEnum getPageIndex() {
		return this.pageIndex;
	}
	
	public void setPageIndex(InvestigationEnum pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	public Map<String, Object> getFormCustomizeFieldMap() {
		return this.formCustomizeFieldMap;
	}

	public void setFormCustomizeFieldMap(Map<String, Object> formCustomizeFieldMap) {
		this.formCustomizeFieldMap = formCustomizeFieldMap;
	}

	/**
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
