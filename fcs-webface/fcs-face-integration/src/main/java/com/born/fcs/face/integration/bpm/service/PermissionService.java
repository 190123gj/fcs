package com.born.fcs.face.integration.bpm.service;

import com.born.fcs.pm.ws.result.base.FcsBaseResult;

public interface PermissionService {
	
	FcsBaseResult loadSystemPermission(String systemAlias);
	
}
