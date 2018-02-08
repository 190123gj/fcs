package com.born.fcs.rm.integration.bpm.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.born.fcs.rm.integration.bpm.service.client.user.SysOrg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.rm.integration.bpm.BpmUserQueryService;
import com.born.fcs.rm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.rm.integration.bpm.service.client.user.UserDetailsServiceProxy;
import com.born.fcs.rm.integration.common.PropertyConfigService;
import com.born.fcs.pm.util.MiscUtil;
import com.google.common.collect.Lists;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

@Service("bpmUserQueryService")
public class BpmUserQueryServiceImpl implements BpmUserQueryService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	PropertyConfigService propertyConfigService;
	
	@Override
	public List<SysUser> findUserByDeptId(String deptId) {
		UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
			propertyConfigService.getBmpServiceUserDetailsService());
		
		try {
			String result = serviceProxy.findUserByDeptId(deptId);
			List<SysUser> userList = Lists.newArrayList();
			HashMap<String, Object> resultMap = MiscUtil.parseJSON(result);
			return makeUserList(userList, resultMap);
		} catch (Exception e) {
			logger.info("根据部门ID查询用户集出错 {}", e);
			return null;
		}
	}
	
	@Override
	public List<SysUser> findUserByDeptCode(String deptCode) {
		UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
			propertyConfigService.getBmpServiceUserDetailsService());
		
		try {
			String result = serviceProxy.findUserByDeptCode(deptCode);
			List<SysUser> userList = Lists.newArrayList();
			HashMap<String, Object> resultMap = MiscUtil.parseJSON(result);
			return makeUserList(userList, resultMap);
		} catch (Exception e) {
			logger.info("根据部门编号查询用户集出错 {}", e);
			return null;
		}
	}
	
	@Override
	public List<SysUser> findUserByJobCode(String jobCode) {
		UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
			propertyConfigService.getBmpServiceUserDetailsService());
		
		try {
			String result = serviceProxy.findUserByJobCode(jobCode);
			List<SysUser> userList = Lists.newArrayList();
			HashMap<String, Object> resultMap = MiscUtil.parseJSON(result);
			return makeUserList(userList, resultMap);
		} catch (Exception e) {
			logger.info("根据岗位编码查询用户集出错 {}", e);
			return null;
		}
	}
	
	@Override
	public List<SysUser> findUserByRoleAlias(String roleAlias) {
		UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
			propertyConfigService.getBmpServiceUserDetailsService());
		
		try {
			String result = serviceProxy.findUserByRoleAlias(roleAlias);
			List<SysUser> userList = Lists.newArrayList();
			HashMap<String, Object> resultMap = MiscUtil.parseJSON(result);
			return makeUserList(userList, resultMap);
		} catch (Exception e) {
			logger.info("根据角色别名查询用户集出错 {}", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<SysUser> makeUserList(List<SysUser> userList, HashMap<String, Object> resultMap) {
		if ("1".equals(String.valueOf(resultMap.get("result")))) {
			List<Map<String, Object>> sysUserMapList = (List<Map<String, Object>>) resultMap
				.get("sysUsers");
			
			for (Map<String, Object> sysUserMap : sysUserMapList) {
				if (sysUserMap == null || sysUserMap.size() == 0)
					continue;
				SysUser sysUser = new SysUser();
				sysUser.setAccount(String.valueOf(sysUserMap.get("account")));
				sysUser.setEmail(String.valueOf(sysUserMap.get("email")));
				sysUser.setIsLock(Short.valueOf(String.valueOf(sysUserMap.get("isLock"))));
				sysUser.setStatus(Short.valueOf(String.valueOf(sysUserMap.get("status"))));
				sysUser.setMobile(String.valueOf(sysUserMap.get("mobile")));
				sysUser.setFullname(String.valueOf(sysUserMap.get("fullname")));
				sysUser.setUserId(Long.valueOf(String.valueOf(sysUserMap.get("userId"))));
				sysUser.setSex(String.valueOf(sysUserMap.get("sex")));
				userList.add(sysUser);
			}
			
			return userList;
		} else {
			return userList;
		}
	}
	
	@SuppressWarnings("unchecked")
	private SysUser makeUser(HashMap<String, Object> resultMap) {
		if ("1".equals(String.valueOf(resultMap.get("result")))) {
			Map<String, Object> sysUserMap = (Map<String, Object>) resultMap.get("sysUsers");
			SysUser sysUser = new SysUser();
			sysUser.setAccount(String.valueOf(sysUserMap.get("account")));
			sysUser.setEmail(String.valueOf(sysUserMap.get("email")));
			sysUser.setIsLock(Short.valueOf(String.valueOf(sysUserMap.get("isLock"))));
			sysUser.setStatus(Short.valueOf(String.valueOf(sysUserMap.get("status"))));
			sysUser.setMobile(String.valueOf(sysUserMap.get("mobile")));
			sysUser.setFullname(String.valueOf(sysUserMap.get("fullname")));
			sysUser.setUserId(Long.valueOf(String.valueOf(sysUserMap.get("userId"))));
			sysUser.setSex(String.valueOf(sysUserMap.get("sex")));
			
			return sysUser;
		} else {
			return null;
		}
	}

	private SysOrg makeOrg(HashMap<String, Object> resultMap) {
		if ("1".equals(String.valueOf(resultMap.get("result")))) {
			Map<String, Object> sysOrgMap = (Map<String, Object>) resultMap.get("sysOrg");
			SysOrg sysOrg = new SysOrg();
			sysOrg.setCode(String.valueOf(sysOrgMap.get("code")));
            sysOrg.setCreateBy(Long.parseLong(String.valueOf(sysOrgMap.get("createBy"))));
			sysOrg.setOrgId(Long.parseLong(String.valueOf(sysOrgMap.get("orgId"))));
			sysOrg.setOrgName(String.valueOf(sysOrgMap.get("orgName")));
			return sysOrg;
		} else {
			return null;
		}
	}
	
	@Override
	public SysUser findUserByUserId(long userId) {
		UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
			propertyConfigService.getBmpServiceUserDetailsService());
		
		try {
			String result = serviceProxy.findUserByUserId(userId);
			HashMap<String, Object> resultMap = MiscUtil.parseJSON(result);
			return makeUser(resultMap);
		} catch (Exception e) {
			logger.info("根据用户ID查询用户出错 userId {} ， {}", userId, e);
			return null;
		}
	}


		@Override
		public String findDeptInfoByDeptCode(String deptCode) {
			UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
					propertyConfigService.getBmpServiceUserDetailsService());

			try {
				String result = serviceProxy.findDeptInfoByDeptCode(deptCode);
				return result;
			} catch (Exception e) {
				logger.info("根据部门编号查询部门信息出错 deptCode {} ， {}", deptCode, e);
				return null;
			}
		}
}
