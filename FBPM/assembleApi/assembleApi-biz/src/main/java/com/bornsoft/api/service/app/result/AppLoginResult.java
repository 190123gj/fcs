package com.bornsoft.api.service.app.result;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.face.integration.bpm.service.result.LoginResult;

public class AppLoginResult extends LoginResult {

	/**
	 */
	private static final long serialVersionUID = -448412047709982138L;

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
