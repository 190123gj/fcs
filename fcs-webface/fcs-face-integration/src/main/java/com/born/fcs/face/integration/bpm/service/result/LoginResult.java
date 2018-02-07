package com.born.fcs.face.integration.bpm.service.result;

import com.born.fcs.face.integration.bpm.service.info.UserInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

public class LoginResult extends FcsBaseResult {
	private static final long serialVersionUID = 5977359625962122957L;
	
	private UserInfo userInfo;
	
	private long pwdErrorCount = 0;
	
	public UserInfo getUserInfo() {
		return this.userInfo;
	}
	
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	public long getPwdErrorCount() {
		return this.pwdErrorCount;
	}
	
	public void setPwdErrorCount(long pwdErrorCount) {
		this.pwdErrorCount = pwdErrorCount;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginResult [userInfo=");
		builder.append(userInfo);
		builder.append(", pwdErrorCount=");
		builder.append(pwdErrorCount);
		builder.append("]");
		return builder.toString();
	}
	
}
