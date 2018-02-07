package com.born.fcs.face.integration.pm.service.form;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.bpm.WorkflowTaskInfo;
import com.born.fcs.pm.ws.order.common.BpmCallBackOrder;
import com.born.fcs.pm.ws.order.common.CancelFormOrder;
import com.born.fcs.pm.ws.order.common.FormQueryOrder;
import com.born.fcs.pm.ws.order.common.SimpleFormAuditOrder;
import com.born.fcs.pm.ws.order.common.SimpleFormOrder;
import com.born.fcs.pm.ws.order.common.TaskAssignFormOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.FormService;

/**
 * 表单客户端
 * @author wuzj
 * 
 */
@Service("formServiceClient")
public class FormServiceClient extends ClientAutowiredBaseService implements FormService {
	
	@Override
	public FormInfo findByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FormInfo>() {
			
			@Override
			public FormInfo call() {
				return formPmWebService.findByFormId(formId);
			}
		});
	}
	
	@Override
	public FormBaseResult submit(final SimpleFormOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return formPmWebService.submit(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult delete(final SimpleFormOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return formPmWebService.delete(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult cancel(final CancelFormOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return formPmWebService.cancel(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult auditProcess(final SimpleFormAuditOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return formPmWebService.auditProcess(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<FormInfo> queryPage(final FormQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FormInfo>>() {
			
			@Override
			public QueryBaseBatchResult<FormInfo> call() {
				return formPmWebService.queryPage(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult taskAssign(final TaskAssignFormOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return formPmWebService.taskAssign(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult endWorkflow(final SimpleFormAuditOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return formPmWebService.endWorkflow(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateForm(final FormInfo formInfo) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return formPmWebService.updateForm(formInfo);
			}
		});
	}
	
	@Override
	public List<WorkflowTaskInfo> backTaskList(final long userId) {
		return callInterface(new CallExternalInterface<List<WorkflowTaskInfo>>() {
			
			@Override
			public List<WorkflowTaskInfo> call() {
				return formPmWebService.backTaskList(userId);
			}
		});
	}
	
	@Override
	public FormBaseResult bpmCallBackProcess(final BpmCallBackOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return formPmWebService.bpmCallBackProcess(order);
			}
		});
	}
}
