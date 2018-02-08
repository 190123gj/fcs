//package com.born.fcs.pm.ws.service.common;
//
//import java.util.List;
//
//import javax.jws.WebService;
//
//import com.born.fcs.pm.ws.enums.ExeStatusEnum;
//import com.born.fcs.pm.ws.enums.RelatedUserTypeEnum;
//import com.born.fcs.pm.ws.info.common.RelatedUserInfo;
//import com.born.fcs.pm.ws.order.common.RelatedUserOrder;
//import com.born.fcs.pm.ws.order.common.RelatedUserQueryOrder;
//import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
//import com.born.fcs.pm.ws.result.base.FcsBaseResult;
//import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
//
///**
// * 查询项目/表单相关人员
// *
// * @author wuzj 2016年4月29日
// */
//@WebService
//public interface RelatedUserService {
//	
//	/**
//	 * 查询相关人员
//	 * @param order
//	 * @return
//	 */
//	List<RelatedUserInfo> query(RelatedUserQueryOrder order);
//	
//	/**
//	 * 更新任务执行状态
//	 * @param exeStatus
//	 * @param remark
//	 * @param taskId
//	 * @return
//	 */
//	FcsBaseResult updateExeStatus(ExeStatusEnum exeStatus, String remark, long taskId, long userId);
//	
//	/**
//	 * 更新提交人的状态
//	 * @param exeStatus
//	 * @param formId
//	 * @return
//	 */
//	FcsBaseResult updateSubmitExeStatus(ExeStatusEnum exeStatus, long formId);
//	
//	/**
//	 * 撤销表单删除执行中的任务
//	 * @param exeStatus
//	 * @param formId
//	 * @return
//	 */
//	FcsBaseResult deleteWhenCancel(long formId);
//	
//	/**
//	 * 分页查询相关人员
//	 * @param order
//	 * @return
//	 */
//	QueryBaseBatchResult<RelatedUserInfo> queryPage(RelatedUserQueryOrder order);
//	
//	/**
//	 * 设置相关人员
//	 * @param order
//	 * @return
//	 */
//	FcsBaseResult setRelatedUser(RelatedUserOrder order);
//	
//	/**
//	 * 根据类型设置项目相关人员（每次删除后重新添加）
//	 * @return
//	 */
//	FcsBaseResult setRelatedUserByType(String projectCode, RelatedUserTypeEnum userType,
//										List<SimpleUserInfo> users);
//	
//	/**
//	 * 查询项目当前业务经理
//	 * @param projectCode
//	 * @return
//	 */
//	RelatedUserInfo getBusiManager(String projectCode);
//	
//	/**
//	 * 查询项目当前业务经理B
//	 * @param projectCode
//	 * @return
//	 */
//	RelatedUserInfo getBusiManagerb(String projectCode);
//	
//	/**
//	 * 查询项目当前风险经理
//	 * @param projectCode
//	 * @return
//	 */
//	RelatedUserInfo getRiskManager(String projectCode);
//	
//	/**
//	 * 查询项目原始的风险经理
//	 * @param projectCode
//	 * @return
//	 */
//	RelatedUserInfo getOrignalRiskManager(String projectCode);
//	
//	/**
//	 * 查询项目当前法务经理
//	 * @param projectCode
//	 * @return
//	 */
//	RelatedUserInfo getLegalManager(String projectCode);
//	
//	/**
//	 * 查询项目当前财务经理
//	 * @param projectCode
//	 * @return
//	 */
//	RelatedUserInfo getFinancialManager(String projectCode);
//	
//	/**
//	 * 查询项目业务总监
//	 * @param projectCode
//	 * @return
//	 */
//	List<SimpleUserInfo> getBusiDirector(String projectCode);
//	
//	/**
//	 * 查询部门总监
//	 * @param deptCode
//	 * @return
//	 */
//	List<SimpleUserInfo> getDirector(String deptCode);
//	
//	/**
//	 * 查询部门分管副总
//	 * @param deptCode
//	 * @return
//	 */
//	List<SimpleUserInfo> getFgfz(String deptCode);
//}
