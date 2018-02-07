package com.born.fcs.face.integration.pm.service.common;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.enums.CheckStatusEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.info.common.AttachmentModuleType;
import com.born.fcs.pm.ws.info.common.CommonAttachmentInfo;
import com.born.fcs.pm.ws.order.common.CommonAttachmentBatchOrder;
import com.born.fcs.pm.ws.order.common.CommonAttachmentDeleteOrder;
import com.born.fcs.pm.ws.order.common.CommonAttachmentOrder;
import com.born.fcs.pm.ws.order.common.CommonAttachmentQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.common.CommonAttachmentResult;
import com.born.fcs.pm.ws.service.common.CommonAttachmentService;

/**
 * 
 * 附件上传
 * 
 * 
 * @author lirz
 * 
 * 2016-4-25 下午4:08:37
 */
@Service("commonAttachmentServiceClient")
public class CommonAttachmentServiceClient extends ClientAutowiredBaseService implements
																				CommonAttachmentService {
	
	@Override
	public QueryBaseBatchResult<CommonAttachmentInfo> queryCommonAttachment(final CommonAttachmentQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<CommonAttachmentInfo>>() {
			@Override
			public QueryBaseBatchResult<CommonAttachmentInfo> call() {
				return commonAttachmentWebService.queryCommonAttachment(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult insert(final CommonAttachmentOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return commonAttachmentWebService.insert(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult insertAll(final List<CommonAttachmentOrder> CommonAttachments) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return commonAttachmentWebService.insertAll(CommonAttachments);
			}
		});
	}
	
	@Override
	public FcsBaseResult deleteById(final long id) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return commonAttachmentWebService.deleteById(id);
			}
		});
	}
	
	@Override
	public CommonAttachmentResult deleteByIdAllSame(final long id) {
		return callInterface(new CallExternalInterface<CommonAttachmentResult>() {
			@Override
			public CommonAttachmentResult call() {
				return commonAttachmentWebService.deleteByIdAllSame(id);
			}
		});
	}
	
	@Override
	public CommonAttachmentResult deletePicture(final CommonAttachmentDeleteOrder order) {
		return callInterface(new CallExternalInterface<CommonAttachmentResult>() {
			@Override
			public CommonAttachmentResult call() {
				return commonAttachmentWebService.deletePicture(order);
			}
		});
	}
	
	@Override
	public CommonAttachmentResult findById(final long id) {
		return callInterface(new CallExternalInterface<CommonAttachmentResult>() {
			@Override
			public CommonAttachmentResult call() {
				return commonAttachmentWebService.findById(id);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateStatusById(final long id, final CheckStatusEnum status) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return commonAttachmentWebService.updateStatusById(id, status);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateStatus(final String bizNo,
										final CommonAttachmentTypeEnum moduleType,
										final CheckStatusEnum status) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return commonAttachmentWebService.updateStatus(bizNo, moduleType, status);
			}
		});
	}
	
	@Override
	public CommonAttachmentResult deleteByBizNoModuleType(final String bizNo,
															final CommonAttachmentTypeEnum... types) {
		return callInterface(new CallExternalInterface<CommonAttachmentResult>() {
			@Override
			public CommonAttachmentResult call() {
				return commonAttachmentWebService.deleteByBizNoModuleType(bizNo, types);
			}
		});
	}
	
	@Override
	public FcsBaseResult deleteByBizNoAndChildIdModuleType(	final String bizNo,
															final String childId,
															final CommonAttachmentTypeEnum... moduleType) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return commonAttachmentWebService.deleteByBizNoAndChildIdModuleType(bizNo, childId,
					moduleType);
			}
		});
	}
	
	@Override
	public FcsBaseResult addNewDelOldByMoudleAndBizNo(final CommonAttachmentBatchOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return commonAttachmentWebService.addNewDelOldByMoudleAndBizNo(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<CommonAttachmentInfo> queryPage(final CommonAttachmentQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<CommonAttachmentInfo>>() {
			@Override
			public QueryBaseBatchResult<CommonAttachmentInfo> call() {
				return commonAttachmentWebService.queryPage(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateStatusCondition(final CheckStatusEnum checkStatus,
												final CommonAttachmentOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return commonAttachmentWebService.updateStatusCondition(checkStatus, order);
			}
		});
	}
	
	@Override
	public FcsBaseResult deleteAttachment(final long id, final String bizNO, final String moduleType) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return commonAttachmentWebService.deleteAttachment(id, bizNO, moduleType);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateAttachment(final CommonAttachmentOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return commonAttachmentWebService.updateAttachment(order);
			}
		});
	}

	@Override
	public List<AttachmentModuleType> queryAttachment(final CommonAttachmentQueryOrder order) {
		return callInterface(new CallExternalInterface<List<AttachmentModuleType>>() {
			@Override
			public List<AttachmentModuleType> call() {
				return commonAttachmentWebService.queryAttachment(order);
			}
		});
	}
}
