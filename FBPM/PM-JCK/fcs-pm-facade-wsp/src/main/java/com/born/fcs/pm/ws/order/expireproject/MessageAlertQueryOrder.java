package com.born.fcs.pm.ws.order.expireproject;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.base.QueryProjectBase;

public class MessageAlertQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = -422611887936760221L;
	
	private String alertPhrase;
	private String active;
	
	public String getAlertPhrase() {
		return this.alertPhrase;
	}


	public void setAlertPhrase(String alertPhrase) {
		this.alertPhrase = alertPhrase;
	}


	public String getActive() {
		return this.active;
	}


	public void setActive(String active) {
		this.active = active;
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
