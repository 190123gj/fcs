package com.born.fcs.pm.ws.order.finvestigation.checking;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ProjectFormOrderBase;

/**
 * 
 * 尽调 - 审核中修改数据
 *
 * @author lirz
 * 
 * 2017-3-20 下午3:13:25
 *
 */
public class InvestigationCheckingOrder extends ProjectFormOrderBase{

	private static final long serialVersionUID = 3695202519944828693L;
	
	private String checkPoint;
	
	@Override
	public void check () {
		validateHasText(checkPoint, "审核节点");
	}

	public String getCheckPoint() {
		return this.checkPoint;
	}

	public void setCheckPoint(String checkPoint) {
		this.checkPoint = checkPoint;
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
