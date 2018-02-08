package com.born.fcs.pm.ws.order.finvestigation.checking;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.base.QueryPermissionPageBase;

/**
 * 
 * 尽调 - 审核中修改数据 查询
 *
 * @author lirz
 * 
 * 2017-3-20 下午3:13:25
 *
 */
public class InvestigationCheckingQueryOrder extends QueryPermissionPageBase{

	private static final long serialVersionUID = -3194753903995290846L;

	private long relatedFormId;

	private String checkPoint;
	
	public long getRelatedFormId() {
		return this.relatedFormId;
	}

	public void setRelatedFormId(long relatedFormId) {
		this.relatedFormId = relatedFormId;
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
