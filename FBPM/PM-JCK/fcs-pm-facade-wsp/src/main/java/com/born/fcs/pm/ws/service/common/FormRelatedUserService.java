package com.born.fcs.pm.ws.service.common;

import javax.jws.WebService;

import com.born.fcs.pm.ws.enums.ExeStatusEnum;
import com.born.fcs.pm.ws.info.common.FormRelatedUserInfo;
import com.born.fcs.pm.ws.order.common.FormRelatedUserOrder;
import com.born.fcs.pm.ws.order.common.FormRelatedUserQueryOrder;
import com.born.fcs.pm.ws.order.common.TaskAssignFormOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 表单相关人员
 *
 * @author wuzj 2016年8月6日
 */
@WebService
public interface FormRelatedUserService {
	
	/**
	 * 分页查询相关人员
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FormRelatedUserInfo> queryPage(FormRelatedUserQueryOrder order);
	
	/**
	 * 统计数量
	 * @param order
	 * @return
	 */
	long queryCount(FormRelatedUserQueryOrder order);
	
	/**
	 * 更新任务执行状态
	 * @param exeStatus
	 * @param taskOpinion
	 * @param remark
	 * @param taskId
	 * @return
	 */
	FcsBaseResult updateExeStatus(ExeStatusEnum exeStatus, String taskOpinion, String remark,
									long taskId, long userId);
	
	/**
	 * 更新提交人的状态
	 * @param exeStatus
	 * @param taskOpinion
	 * @param formId
	 * @return
	 */
	FcsBaseResult updateSubmitExeStatus(ExeStatusEnum exeStatus, String taskOpinion, long formId);
	
	/**
	 * 删掉未执行的任务
	 * @param formId
	 * @return
	 */
	FcsBaseResult deleteWaiting(long formId);
	
	/**
	 * 删掉不是执行人的候选人
	 * @param processTaskId
	 * @param processManId
	 * @return
	 */
	FcsBaseResult deleteNotExecutor(long processTaskId, long processManId);
	
	/**
	 * 设置相关人员
	 * @param order
	 * @return
	 */
	FcsBaseResult setRelatedUser(FormRelatedUserOrder order);
	
	/**
	 * 任务交办
	 */
	FcsBaseResult taskAssign(TaskAssignFormOrder order);
}
