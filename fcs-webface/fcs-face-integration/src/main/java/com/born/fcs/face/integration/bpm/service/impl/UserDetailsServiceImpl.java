package com.born.fcs.face.integration.bpm.service.impl;

import java.rmi.RemoteException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.bpm.service.client.user.Exception;
import com.born.bpm.service.client.user.Job;
import com.born.bpm.service.client.user.SysOrg;
import com.born.bpm.service.client.user.SysUser;
import com.born.bpm.service.client.user.UserDetailsService;
import com.born.bpm.service.client.user.UserDetailsServiceProxy;
import com.born.fcs.face.integration.service.ClientBaseSevice;
import com.born.fcs.face.integration.service.PropertyConfigService;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

@Service("userDetailsService")
public class UserDetailsServiceImpl extends ClientBaseSevice implements UserDetailsService {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	PropertyConfigService propertyConfigService;
	
	@Override
	public SysOrg loadPrimaryOrgByUsername(String userAccount) throws RemoteException {
		return null;
	}
	
	@Override
	public String findDeptInfoByDeptId(String deptId) throws RemoteException {
		return null;
	}
	
	@Override
	public String findUserByDeptId(String deptId) throws RemoteException {
		return null;
	}
	
	@Override
	public String findUserByDeptCode(String deptCode) throws RemoteException {
		return null;
	}
	
	@Override
	public Job[] loadJobsByUsername(String userAccount) throws RemoteException {
		return null;
	}
	
	@Override
	public SysUser loadUserByUsername(String userAccount) throws RemoteException, Exception {
		return null;
	}
	
	@Override
	public String login(String account, String password, String ip, String source)
																					throws RemoteException {
		return null;
	}
	
	@Override
	public String findUserByRoleAlias(String roleAlias) throws RemoteException {
		return null;
	}
	
	@Override
	public String findDeptInfoByDeptCode(String deptCode) throws RemoteException {
		return null;
	}
	
	@Override
	public String findUserRelatedInfoByUserId(Long userId) throws RemoteException {
		return null;
	}
	
	@Override
	public String findUserByJobCode(String jobCode) throws RemoteException {
		return null;
	}
	
	@Override
	public String loadUserOrgByFullName(String fullName) throws RemoteException {
		return null;
	}
	
	@Override
	public String updatePwd(String account, String password) throws RemoteException {
		return null;
	}
	
	@Override
	public String searchLoginLog(String json) throws RemoteException {
		UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
			propertyConfigService.getBmpServiceUserDetailsService());
		
		try {
			String result = serviceProxy.searchLoginLog(json);
			//			List<SysUser> userList = Lists.newArrayList();
			//			HashMap<String, Object> resultMap = MiscUtil.parseJSON(result);
			return result;
		} catch (Exception e) {
			logger.info("根据部门ID查询用户集出错 {}", e);
			return null;
		}
	}
	
	@Override
	public String findUserByUserId(Long userId) throws RemoteException {
		return null;
	}
	
	@Override
	public String loadOrgByName(String orgName) throws RemoteException {
		return null;
	}
	
	@Override
	public String getChargeByOrgId(Long orgId) throws RemoteException {
		UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
			propertyConfigService.getBmpServiceUserDetailsService());
		
		try {
			String result = serviceProxy.getChargeByOrgId(orgId);
			//			List<SysUser> userList = Lists.newArrayList();
			//			HashMap<String, Object> resultMap = MiscUtil.parseJSON(result);
			return result;
		} catch (Exception e) {
			logger.info("根据部门ID查询负责人 {}", e);
			return null;
		}
	}
	
}
