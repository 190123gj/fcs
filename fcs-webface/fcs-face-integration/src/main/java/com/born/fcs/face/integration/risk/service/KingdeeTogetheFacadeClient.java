package com.born.fcs.face.integration.risk.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.bpm.service.BpmUserQueryService;
import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.fm.ws.order.common.SyncKingdeeBasicDataOrder;
import com.born.fcs.fm.ws.service.common.SyncKingdeeBasicDataService;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.bornsoft.facade.api.kingdee.KingdeeTogetheFacade;
import com.bornsoft.pub.order.kingdee.KingdeeClerkaOrder;
import com.bornsoft.pub.order.kingdee.KingdeeContractOrder;
import com.bornsoft.pub.order.kingdee.KingdeeCustomerOrder;
import com.bornsoft.pub.order.kingdee.KingdeeExpensesRequisitionsOrder;
import com.bornsoft.pub.order.kingdee.KingdeeGatheringOrder;
import com.bornsoft.pub.order.kingdee.KingdeeOrganizationOrder;
import com.bornsoft.pub.order.kingdee.KingdeePaymentOrder;
import com.bornsoft.pub.order.kingdee.KingdeeQueryAccountOrder;
import com.bornsoft.pub.order.kingdee.KingdeeQueryGuaranteeOrder;
import com.bornsoft.pub.order.kingdee.KingdeeTernalTransOrder;
import com.bornsoft.pub.order.kingdee.KingdeeTravelExpenseOrder;
import com.bornsoft.pub.result.kingdee.KingdeeClerkaResult;
import com.bornsoft.pub.result.kingdee.KingdeeContractResult;
import com.bornsoft.pub.result.kingdee.KingdeeCustomerResult;
import com.bornsoft.pub.result.kingdee.KingdeeExpensesRequisitionsResult;
import com.bornsoft.pub.result.kingdee.KingdeeGatheringResult;
import com.bornsoft.pub.result.kingdee.KingdeeOrganizationResult;
import com.bornsoft.pub.result.kingdee.KingdeePaymentResult;
import com.bornsoft.pub.result.kingdee.KingdeeQueryAccountResult;
import com.bornsoft.pub.result.kingdee.KingdeeQueryGuaranteeResult;
import com.bornsoft.pub.result.kingdee.KingdeeTernalTransResult;
import com.bornsoft.pub.result.kingdee.KingdeeTravelExpenseResult;
import com.google.common.collect.Maps;
import com.yjf.common.lang.util.StringUtil;

@Service("kingdeeTogetheFacadeClient")
public class KingdeeTogetheFacadeClient extends ClientAutowiredBaseService implements
																			KingdeeTogetheFacade,
																			SyncKingdeeBasicDataService {
	
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	
	@Override
	public KingdeeExpensesRequisitionsResult expensesRequisitions(	final KingdeeExpensesRequisitionsOrder arg0) {
		return callInterface(new CallExternalInterface<KingdeeExpensesRequisitionsResult>() {
			
			@Override
			public KingdeeExpensesRequisitionsResult call() {
				return kingdeeTogetheFacade.expensesRequisitions(arg0);
			}
		});
	}
	
	@Override
	public KingdeeGatheringResult gathering(final KingdeeGatheringOrder arg0) {
		return callInterface(new CallExternalInterface<KingdeeGatheringResult>() {
			
			@Override
			public KingdeeGatheringResult call() {
				return kingdeeTogetheFacade.gathering(arg0);
			}
		});
	}
	
	@Override
	public KingdeePaymentResult payment(final KingdeePaymentOrder arg0) {
		return callInterface(new CallExternalInterface<KingdeePaymentResult>() {
			
			@Override
			public KingdeePaymentResult call() {
				return kingdeeTogetheFacade.payment(arg0);
			}
		});
	}
	
	@Override
	public KingdeeQueryAccountResult queryAccount(final KingdeeQueryAccountOrder arg0) {
		return callInterface(new CallExternalInterface<KingdeeQueryAccountResult>() {
			
			@Override
			public KingdeeQueryAccountResult call() {
				return kingdeeTogetheFacade.queryAccount(arg0);
			}
		});
	}
	
	@Override
	public KingdeeQueryGuaranteeResult queryGuarantee(final KingdeeQueryGuaranteeOrder arg0) {
		return callInterface(new CallExternalInterface<KingdeeQueryGuaranteeResult>() {
			
			@Override
			public KingdeeQueryGuaranteeResult call() {
				return kingdeeTogetheFacade.queryGuarantee(arg0);
			}
		});
	}
	
	@Override
	public KingdeeClerkaResult synClearka(final KingdeeClerkaOrder arg0) {
		return callInterface(new CallExternalInterface<KingdeeClerkaResult>() {
			
			@Override
			public KingdeeClerkaResult call() {
				return kingdeeTogetheFacade.synClearka(arg0);
			}
		});
	}
	
	@Override
	public KingdeeContractResult synContract(final KingdeeContractOrder arg0) {
		return callInterface(new CallExternalInterface<KingdeeContractResult>() {
			
			@Override
			public KingdeeContractResult call() {
				return kingdeeTogetheFacade.synContract(arg0);
			}
		});
	}
	
	@Override
	public KingdeeCustomerResult synCustomer(final KingdeeCustomerOrder arg0) {
		return callInterface(new CallExternalInterface<KingdeeCustomerResult>() {
			
			@Override
			public KingdeeCustomerResult call() {
				return kingdeeTogetheFacade.synCustomer(arg0);
			}
		});
	}
	
	@Override
	public KingdeeOrganizationResult synOrganization(final KingdeeOrganizationOrder arg0) {
		return callInterface(new CallExternalInterface<KingdeeOrganizationResult>() {
			
			@Override
			public KingdeeOrganizationResult call() {
				return kingdeeTogetheFacade.synOrganization(arg0);
			}
		});
	}
	
	@Override
	public KingdeeTernalTransResult ternalTrans(final KingdeeTernalTransOrder arg0) {
		return callInterface(new CallExternalInterface<KingdeeTernalTransResult>() {
			
			@Override
			public KingdeeTernalTransResult call() {
				return kingdeeTogetheFacade.ternalTrans(arg0);
			}
		});
	}
	
	@Override
	public KingdeeTravelExpenseResult travelExpense(final KingdeeTravelExpenseOrder arg0) {
		return callInterface(new CallExternalInterface<KingdeeTravelExpenseResult>() {
			
			@Override
			public KingdeeTravelExpenseResult call() {
				return kingdeeTogetheFacade.travelExpense(arg0);
			}
		});
	}
	
	/***
	 * 已同步过的部门
	 */
	private static Map<String, Org> areadySyncDeptMap = Maps.newHashMap();
	
	@Override
	public FcsBaseResult syncBasicData(SyncKingdeeBasicDataOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		//logger.info("开始同步基础数据到金蝶 {} ", order);
		try {
			//同步组织架构
			if (order.getDeptId() > 0 || order.getDept() != null
				|| StringUtil.isNotBlank(order.getDeptCode())) {
				try {
					Org org = order.getDept();
					
					if (org == null && order.getDeptId() > 0) {
						org = bpmUserQueryService.findDeptById(order.getDeptId());
					}
					
					if (org == null && StringUtil.isNotBlank(order.getDeptCode()))
						org = bpmUserQueryService.findDeptByCode(order.getDeptCode());
					
					if (org != null) {
						//						if (areadySyncDeptMap.get(org.getCode()) == null) { //未同步的才同步
						
						KingdeeOrganizationOrder orgOrder = new KingdeeOrganizationOrder();
						orgOrder.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
						orgOrder.setDeptCode(String.valueOf(org.getCode()));
						orgOrder.setDeptName(org.getName());
						Org parentOrg = bpmUserQueryService.findDeptById(org.getParentId());
						if (parentOrg != null && !"总部".equals(parentOrg.getName())) {
							orgOrder.setParentDeptCode(String.valueOf(parentOrg.getCode()));
							orgOrder.setParentDeptName(parentOrg.getName());
						}
						logger.info("同步组织架构开始{}", orgOrder);
						KingdeeOrganizationResult orgResult = synOrganization(orgOrder);
						logger.info("同步组织架构结果{}", orgResult);
						//无法判断系统是否已经同步过
						//areadySyncDeptMap.put(org.getCode(), org);
						//						} else {
						//							logger.info("组织架构已同步{}", org);
						//						}
					}
				} catch (Exception e) {
					logger.error("同步组织架构出错 {} ", e);
				}
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("同步基础数据到金蝶出错");
			logger.error("同步基础数据到金蝶出错{}", e);
		}
		return result;
	}
}
