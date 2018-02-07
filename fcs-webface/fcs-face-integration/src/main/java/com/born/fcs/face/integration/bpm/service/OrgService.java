package com.born.fcs.face.integration.bpm.service;

import java.util.List;

import com.born.fcs.face.integration.bpm.service.info.UserInfo;
import com.born.fcs.face.integration.info.OrgInfo;

public interface OrgService {
	
	/**
	 * 查询某个组织机构及其下属机构
	 * 
	 * @param orgCode
	 * @return
	 */
	OrgInfo findOrgInSystemByCode(String orgCode);
	
	/**
	 * 获取部门/组织结构下的人员
	 * 
	 * @param orgCode
	 * @return
	 */
	List<UserInfo> findOrgMenbersByCode(String orgCode);
	
}
