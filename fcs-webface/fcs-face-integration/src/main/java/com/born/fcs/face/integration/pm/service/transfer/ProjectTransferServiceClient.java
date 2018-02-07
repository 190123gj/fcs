package com.born.fcs.face.integration.pm.service.transfer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.enums.ProjectTransferStatusEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.transfer.ProjectTransferDetailFormInfo;
import com.born.fcs.pm.ws.info.transfer.ProjectTransferDetailInfo;
import com.born.fcs.pm.ws.order.common.TransferProjectOrder;
import com.born.fcs.pm.ws.order.transfer.ProjectTransferAcceptOrder;
import com.born.fcs.pm.ws.order.transfer.ProjectTransferDetailOrder;
import com.born.fcs.pm.ws.order.transfer.ProjectTransferDetailQueryOrder;
import com.born.fcs.pm.ws.order.transfer.ProjectTransferOrder;
import com.born.fcs.pm.ws.order.transfer.ProjectTransferSelectOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.ProjectTransferService;

/**
 * 项目移交
 * @author wuzj
 * 
 */
@Service("projectTransferServiceClient")
public class ProjectTransferServiceClient extends ClientAutowiredBaseService implements
																			ProjectTransferService {
	@Autowired
	ProjectTransferService projectTransferWebService;
	
	@Override
	public QueryBaseBatchResult<ProjectInfo> selectProject(final ProjectTransferSelectOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectInfo> call() {
				return projectTransferWebService.selectProject(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult transferProject(final TransferProjectOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectTransferWebService.transferProject(order);
			}
		});
	}
	
	@Override
	public FormBaseResult saveTransferApply(final ProjectTransferOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return projectTransferWebService.saveTransferApply(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult setAcceptUser(final ProjectTransferAcceptOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectTransferWebService.setAcceptUser(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult logTransferDetail(final ProjectTransferDetailOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectTransferWebService.logTransferDetail(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ProjectTransferDetailFormInfo> queryDetail(	final ProjectTransferDetailQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectTransferDetailFormInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectTransferDetailFormInfo> call() {
				return projectTransferWebService.queryDetail(order);
			}
		});
	}
	
	@Override
	public List<ProjectTransferDetailInfo> queryByFormId(final long formId) {
		return callInterface(new CallExternalInterface<List<ProjectTransferDetailInfo>>() {
			
			@Override
			public List<ProjectTransferDetailInfo> call() {
				return projectTransferWebService.queryByFormId(formId);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateStatusByFormId(final ProjectTransferStatusEnum status,
												final long formId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectTransferWebService.updateStatusByFormId(status, formId);
			}
		});
	}
	
	@Override
	public FcsBaseResult doTransfer(final long formId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectTransferWebService.doTransfer(formId);
			}
		});
	}
	
	@Override
	public List<ProjectTransferDetailInfo> queryByProjectCode(final String projectCode) {
		return callInterface(new CallExternalInterface<List<ProjectTransferDetailInfo>>() {
			
			@Override
			public List<ProjectTransferDetailInfo> call() {
				return projectTransferWebService.queryByProjectCode(projectCode);
			}
		});
	}
	
}
