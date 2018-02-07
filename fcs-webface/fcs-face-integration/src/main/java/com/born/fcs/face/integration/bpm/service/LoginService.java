package com.born.fcs.face.integration.bpm.service;

import com.born.fcs.face.integration.bpm.service.order.LoginOrder;
import com.born.fcs.face.integration.bpm.service.result.LoginResult;

public interface LoginService {
	
	LoginResult login(LoginOrder loginData);
	
	LoginResult validateLoginInfo(LoginOrder userLoginOrder);
	
}
