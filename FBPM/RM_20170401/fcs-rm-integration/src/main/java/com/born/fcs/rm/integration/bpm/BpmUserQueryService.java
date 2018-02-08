package com.born.fcs.rm.integration.bpm;

import java.util.List;

import com.born.fcs.rm.integration.bpm.service.client.user.SysOrg;
import com.born.fcs.rm.integration.bpm.service.client.user.SysUser;

public interface BpmUserQueryService {
	
	List<SysUser> findUserByDeptCode(String deptCode);
	
	List<SysUser> findUserByDeptId(String deptId);
	
	List<SysUser> findUserByRoleAlias(String roleAlias);
	
	List<SysUser> findUserByJobCode(String jobCode);
	
	SysUser findUserByUserId(long userId);

	String findDeptInfoByDeptCode(String deptCode);
}
