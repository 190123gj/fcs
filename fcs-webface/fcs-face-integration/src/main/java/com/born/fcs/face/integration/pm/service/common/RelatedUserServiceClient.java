//package com.born.fcs.face.integration.pm.service.common;
//
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.born.fcs.face.integration.service.CallExternalInterface;
//import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
//import com.born.fcs.pm.ws.enums.ExeStatusEnum;
//import com.born.fcs.pm.ws.enums.RelatedUserTypeEnum;
//import com.born.fcs.pm.ws.info.common.RelatedUserInfo;
//import com.born.fcs.pm.ws.order.common.RelatedUserOrder;
//import com.born.fcs.pm.ws.order.common.RelatedUserQueryOrder;
//import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
//import com.born.fcs.pm.ws.result.base.FcsBaseResult;
//import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
//import com.born.fcs.pm.ws.service.common.RelatedUserService;
//
//@Service("relatedUserServiceClient")
//public class RelatedUserServiceClient extends ClientAutowiredBaseService implements
//																		RelatedUserService {
//	
//	@Override
//	public List<RelatedUserInfo> query(final RelatedUserQueryOrder order) {
//		return callInterface(new CallExternalInterface<List<RelatedUserInfo>>() {
//			
//			@Override
//			public List<RelatedUserInfo> call() {
//				return relatedUserWebService.query(order);
//			}
//		});
//	}
//	
//	@Override
//	public QueryBaseBatchResult<RelatedUserInfo> queryPage(final RelatedUserQueryOrder order) {
//		return callInterface(new CallExternalInterface<QueryBaseBatchResult<RelatedUserInfo>>() {
//			
//			@Override
//			public QueryBaseBatchResult<RelatedUserInfo> call() {
//				return relatedUserWebService.queryPage(order);
//			}
//		});
//	}
//	
//	@Override
//	public FcsBaseResult setRelatedUser(final RelatedUserOrder order) {
//		return callInterface(new CallExternalInterface<FcsBaseResult>() {
//			
//			@Override
//			public FcsBaseResult call() {
//				return relatedUserWebService.setRelatedUser(order);
//			}
//		});
//	}
//	
//	@Override
//	public FcsBaseResult setRelatedUserByType(final String projectCode,
//												final RelatedUserTypeEnum userType,
//												final List<SimpleUserInfo> users) {
//		return callInterface(new CallExternalInterface<FcsBaseResult>() {
//			
//			@Override
//			public FcsBaseResult call() {
//				return relatedUserWebService.setRelatedUserByType(projectCode, userType, users);
//			}
//		});
//	}
//	
//	@Override
//	public RelatedUserInfo getBusiManager(final String projectCode) {
//		return callInterface(new CallExternalInterface<RelatedUserInfo>() {
//			
//			@Override
//			public RelatedUserInfo call() {
//				return relatedUserWebService.getBusiManager(projectCode);
//			}
//		});
//	}
//	
//	@Override
//	public RelatedUserInfo getBusiManagerb(final String projectCode) {
//		return callInterface(new CallExternalInterface<RelatedUserInfo>() {
//			
//			@Override
//			public RelatedUserInfo call() {
//				return relatedUserWebService.getBusiManagerb(projectCode);
//			}
//		});
//	}
//	
//	@Override
//	public RelatedUserInfo getRiskManager(final String projectCode) {
//		return callInterface(new CallExternalInterface<RelatedUserInfo>() {
//			
//			@Override
//			public RelatedUserInfo call() {
//				return relatedUserWebService.getRiskManager(projectCode);
//			}
//		});
//	}
//	
//	@Override
//	public RelatedUserInfo getLegalManager(final String projectCode) {
//		return callInterface(new CallExternalInterface<RelatedUserInfo>() {
//			
//			@Override
//			public RelatedUserInfo call() {
//				return relatedUserWebService.getLegalManager(projectCode);
//			}
//		});
//	}
//	
//	@Override
//	public RelatedUserInfo getFinancialManager(final String projectCode) {
//		return callInterface(new CallExternalInterface<RelatedUserInfo>() {
//			
//			@Override
//			public RelatedUserInfo call() {
//				return relatedUserWebService.getFinancialManager(projectCode);
//			}
//		});
//	}
//	
//	@Override
//	public RelatedUserInfo getOrignalRiskManager(final String projectCode) {
//		return callInterface(new CallExternalInterface<RelatedUserInfo>() {
//			
//			@Override
//			public RelatedUserInfo call() {
//				return relatedUserWebService.getOrignalRiskManager(projectCode);
//			}
//		});
//	}
//	
//	@Override
//	public List<SimpleUserInfo> getBusiDirector(final String projectCode) {
//		return callInterface(new CallExternalInterface<List<SimpleUserInfo>>() {
//			
//			@Override
//			public List<SimpleUserInfo> call() {
//				return relatedUserWebService.getBusiDirector(projectCode);
//			}
//		});
//	}
//	
//	@Override
//	public List<SimpleUserInfo> getDirector(final String deptCode) {
//		return callInterface(new CallExternalInterface<List<SimpleUserInfo>>() {
//			
//			@Override
//			public List<SimpleUserInfo> call() {
//				return relatedUserWebService.getDirector(deptCode);
//			}
//		});
//	}
//	
//	@Override
//	public List<SimpleUserInfo> getFgfz(final String deptCode) {
//		return callInterface(new CallExternalInterface<List<SimpleUserInfo>>() {
//			
//			@Override
//			public List<SimpleUserInfo> call() {
//				return relatedUserWebService.getFgfz(deptCode);
//			}
//		});
//	}
//	
//	@Override
//	public FcsBaseResult updateExeStatus(final ExeStatusEnum exeStatus, final String remark,
//											final long taskId, final long userId) {
//		return callInterface(new CallExternalInterface<FcsBaseResult>() {
//			
//			@Override
//			public FcsBaseResult call() {
//				return relatedUserWebService.updateExeStatus(exeStatus, remark, taskId, userId);
//			}
//		});
//	}
//	
//	@Override
//	public FcsBaseResult updateSubmitExeStatus(final ExeStatusEnum exeStatus, final long formId) {
//		return callInterface(new CallExternalInterface<FcsBaseResult>() {
//			
//			@Override
//			public FcsBaseResult call() {
//				return relatedUserWebService.updateSubmitExeStatus(exeStatus, formId);
//			}
//		});
//	}
//	
//	@Override
//	public FcsBaseResult deleteWhenCancel(final long formId) {
//		return callInterface(new CallExternalInterface<FcsBaseResult>() {
//			
//			@Override
//			public FcsBaseResult call() {
//				return relatedUserWebService.deleteWhenCancel(formId);
//			}
//		});
//	}
//}
