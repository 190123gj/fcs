package com.born.fcs.pm.ws.order.check;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.base.QueryProjectBase;

/**
 * 
 * 保后检查报告查询
 * 
 * @author lirz
 * 
 * 2016-6-15 下午2:02:02
 */
public class AfterwordsCheckQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = 545269586001450019L;
	
	private String formStatus; // 表单状态
	private String edition; //版本
	private String isLatest; //版本
	
	public String getFormStatus() {
		return formStatus;
	}
	
	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}
	

	public String getEdition() {
		return this.edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}
	
	public String getIsLatest() {
		return this.isLatest;
	}

	public void setIsLatest(String isLatest) {
		this.isLatest = isLatest;
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
