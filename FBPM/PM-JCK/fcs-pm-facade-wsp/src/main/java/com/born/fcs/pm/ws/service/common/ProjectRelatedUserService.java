package com.born.fcs.pm.ws.service.common;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.enums.managerbchange.ChangeManagerbPhaseEnum;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserQueryOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 项目相关人员
 *
 * @author wuzj 2016年8月6日
 */
@WebService
public interface ProjectRelatedUserService {
	
	/**
	 * 分页查询相关人员
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ProjectRelatedUserInfo> queryPage(ProjectRelatedUserQueryOrder order);
	
	/**
	 * 设置相关人员
	 * @param order
	 * @return
	 */
	FcsBaseResult setRelatedUser(ProjectRelatedUserOrder order);
	
	/**
	 * 设置评委
	 * @param projectCode
	 * @param judgeIds
	 * @param councilId
	 * @return
	 */
	FcsBaseResult setCouncilJudges(String projectCode, List<Long> judgeIds, long councilId);
	
	/**
	 * 设置风险处置小组成员
	 * @param projectCode
	 * @param memberIds
	 * @return
	 */
	FcsBaseResult setRiskTeamMember(final String projectCode, final List<Long> memberIds);
	
	/**
	 * 项目授权
	 * @param projectCode 项目编号
	 * @param remark 备注
	 * @param users 用户详细信息如果是有的可以直接带过来 没有就直接设置 userId
	 * @return
	 */
	FcsBaseResult manualGrant(String projectCode, String remark, SimpleUserInfo... users);
	
	/**
	 * 取消项目授权
	 * @param projectCode
	 * @param userIds
	 */
	FcsBaseResult manualRevoke(String projectCode, long... userIds);
	
	/***
	 * 取消流程人员(未执行候选人)的授权
	 * @param projectCode
	 * @param userId
	 * @return
	 */
	FcsBaseResult revokeFlowRelated(String projectCode, long userId);
	
	/**
	 * 查询项目当前业务经理
	 * @param projectCode
	 * @return
	 */
	ProjectRelatedUserInfo getBusiManager(String projectCode);
	
	/**
	 * 查询项目当前业务经理B
	 * @param projectCode
	 * @return
	 */
	ProjectRelatedUserInfo getBusiManagerb(String projectCode);
	
	/**
	 * 根据阶段获取业务经理B
	 * @param projectCode
	 * @param phase
	 * @return
	 */
	ProjectRelatedUserInfo getBusiManagerbByPhase(String projectCode, ChangeManagerbPhaseEnum phase);
	
	/**
	 * 查询项目当前风险经理
	 * @param projectCode
	 * @return
	 */
	ProjectRelatedUserInfo getRiskManager(String projectCode);
	
	/**
	 * 查询项目原始的风险经理
	 * @param projectCode
	 * @return
	 */
	ProjectRelatedUserInfo getOrignalRiskManager(String projectCode);
	
	/**
	 * 查询项目当前法务经理
	 * @param projectCode
	 * @return
	 */
	ProjectRelatedUserInfo getLegalManager(String projectCode);
	
	/**
	 * 查询项目当前财务经理
	 * @param projectCode
	 * @return
	 */
	ProjectRelatedUserInfo getFinancialManager(String projectCode);
	
	/**
	 * 查询项目业务总监
	 * @param projectCode
	 * @return
	 */
	List<SimpleUserInfo> getBusiDirector(String projectCode);
	
	/**
	 * 查询部门总监
	 * @param deptCode
	 * @return
	 */
	List<SimpleUserInfo> getDirector(String deptCode);
	
	/**
	 * 查询部门分管副总
	 * @param deptCode
	 * @return
	 */
	List<SimpleUserInfo> getFgfz(String deptCode);
	
	/**
	 * 根据部门编号和角色编码获取指定部门指定角色的人员
	 * @param deptCode
	 * @param roleName
	 * @return
	 */
	List<SimpleUserInfo> getDeptRoleUser(String deptCode, String roleName);
	
	/**
	 * 根据部门ID和角色编码获取指定部门指定角色的人员
	 * @param deptCode
	 * @param roleName
	 * @return
	 */
	List<SimpleUserInfo> getRoleDeptUser(long deptId, String roleName);
	
	/**
	 * 指定人是否属于某部门
	 * @param userId
	 * @param deptCode
	 * @return
	 */
	boolean isBelong2Dept(long userId, String deptCode);
	
	/**
	 * 指定人是否属于某部门
	 * @param userDetail
	 * @param deptCode
	 * @return
	 */
	boolean isBelongToDept(UserDetailInfo userDetail, String deptCode);
}
