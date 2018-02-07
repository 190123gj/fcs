package com.born.fcs.face.integration.bpm.service;

import com.born.fcs.face.integration.bpm.service.order.UpdatePasswordOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

public interface PasswordManagerService {
	
	FcsBaseResult updateUserPassword(UpdatePasswordOrder passwordOrder);
	
}
