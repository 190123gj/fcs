package com.born.fcs.pm.ws.service.expireproject;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.expireproject.ExpireFormProjectInfo;
import com.born.fcs.pm.ws.info.expireproject.ExpireProjectInfo;
import com.born.fcs.pm.ws.info.expireproject.ExpireProjectNoticeTemplateInfo;
import com.born.fcs.pm.ws.order.expireproject.ExpireProjectNoticeTemplateOrder;
import com.born.fcs.pm.ws.order.expireproject.ExpireProjectOrder;
import com.born.fcs.pm.ws.order.expireproject.ExpireProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * 到期逾期项目
 * 
 * @author lirz
 * 
 * 2016-4-14 下午1:46:42
 */
@WebService
public interface ExpireProjectService {
	
	/**
	 * 
	 * 添加到期逾期项目
	 * 
	 * @param order
	 * @return 成功/失败
	 */
	FcsBaseResult add(ExpireProjectOrder order);
	
	/**
	 * 更新项目状态为到期
	 * 
	 * @param order
	 * @return 成功/失败
	 */
	FcsBaseResult updateToExpire(ExpireProjectOrder order);
	
	/**
	 * 更新项目状态为逾期
	 * 
	 * @param order
	 * @return 成功/失败
	 */
	FcsBaseResult updateToOverdue(ExpireProjectOrder order);
	
	/**
	 * 更新项目状态为完成
	 * 
	 * @param order
	 * @return 成功/失败
	 */
	FcsBaseResult updateToDone(ExpireProjectOrder order);
	
	/**
	 * 
	 * 查询到期逾期项目信息
	 * 
	 * @param projectCode 项目编号
	 * @return 项目信息
	 */
	ExpireProjectInfo queryExpireProjectByProjectCode(String projectCode);
	
	/**
	 * 查询到期逾期项目列表信息(job更新使用)
	 * 
	 * @param queryOrder
	 * @return 项目列表
	 */
	QueryBaseBatchResult<ExpireProjectInfo> queryExpireProjectInfo(	ExpireProjectQueryOrder queryOrder);
	
	/**
	 * 查询到期逾期项目列表信息
	 * 
	 * @param queryOrder
	 * @return 项目列表
	 */
	QueryBaseBatchResult<ExpireFormProjectInfo> queryExpireFormProjectInfo(	ExpireProjectQueryOrder queryOrder);

	/**
	 * 保存通知书模板
	 *
	 * @param order
	 * @return 成功/失败
	 */
	FcsBaseResult saveNoticeTemplate(ExpireProjectNoticeTemplateOrder order);

	/**
	 * 查询通知书模板
	 *
	 * @param expireId
	 */
	ExpireProjectNoticeTemplateInfo findTemplateByExpireIdAndStatus(String expireId,String status);

	/**
	 * 根据id查询通知书模板
	 *
	 * @param templateId
	 */
	ExpireProjectNoticeTemplateInfo findTemplateById(long templateId);

	/**
	 * 根据id查项目信息
	 *
	 * @param expireId
	 */
	ExpireProjectInfo queryExpireProjectByExpireId(String expireId);
}
