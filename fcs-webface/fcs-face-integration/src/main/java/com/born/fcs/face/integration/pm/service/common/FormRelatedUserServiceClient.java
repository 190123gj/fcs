package com.born.fcs.face.integration.pm.service.common;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.enums.ExeStatusEnum;
import com.born.fcs.pm.ws.info.common.FormRelatedUserInfo;
import com.born.fcs.pm.ws.order.common.FormRelatedUserOrder;
import com.born.fcs.pm.ws.order.common.FormRelatedUserQueryOrder;
import com.born.fcs.pm.ws.order.common.TaskAssignFormOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.FormRelatedUserService;

@Service("formRelatedUserServiceClient")
public class FormRelatedUserServiceClient extends ClientAutowiredBaseService implements
																			FormRelatedUserService {
	
	@Override
	public QueryBaseBatchResult<FormRelatedUserInfo> queryPage(final FormRelatedUserQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FormRelatedUserInfo>>() {
			
			@Override
			public QueryBaseBatchResult<FormRelatedUserInfo> call() {
				return formRelatedUserWebService.queryPage(order);
			}
		});
	}
	
	@Override
	public long queryCount(final FormRelatedUserQueryOrder order) {
		return callInterface(new CallExternalInterface<Long>() {
			
			@Override
			public Long call() {
				return formRelatedUserWebService.queryCount(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateExeStatus(final ExeStatusEnum exeStatus, final String taskOpinion,
											final String remark, final long taskId,
											final long userId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return formRelatedUserWebService.updateExeStatus(exeStatus, taskOpinion, remark,
					taskId, userId);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateSubmitExeStatus(final ExeStatusEnum exeStatus,
												final String taskOpinion, final long formId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return formRelatedUserWebService.updateSubmitExeStatus(exeStatus, taskOpinion,
					formId);
			}
		});
	}
	
	@Override
	public FcsBaseResult deleteWaiting(final long formId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return formRelatedUserWebService.deleteWaiting(formId);
			}
		});
	}
	
	@Override
	public FcsBaseResult deleteNotExecutor(final long processTaskId, final long processManId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return formRelatedUserWebService.deleteNotExecutor(processTaskId, processManId);
			}
		});
	}
	
	@Override
	public FcsBaseResult setRelatedUser(final FormRelatedUserOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return formRelatedUserWebService.setRelatedUser(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult taskAssign(final TaskAssignFormOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return formRelatedUserWebService.taskAssign(order);
			}
		});
	}
}
