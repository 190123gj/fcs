package com.born.fcs.fm.integration.risk.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.fm.integration.bpm.BpmUserQueryService;
import com.born.fcs.fm.integration.bpm.service.CallExternalInterface;
import com.born.fcs.fm.integration.bpm.service.ClientAutowiredBaseService;
import com.born.fcs.fm.ws.order.common.SyncKingdeeBasicDataOrder;
import com.born.fcs.fm.ws.service.common.SyncKingdeeBasicDataService;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.contract.ProjectContractService;
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
import com.yjf.common.lang.util.StringUtil;

@Service("kingdeeTogetheFacadeClient")
public class KingdeeTogetheFacadeClient extends ClientAutowiredBaseService implements
																			KingdeeTogetheFacade,
																			SyncKingdeeBasicDataService {
	@Autowired
	private KingdeeTogetheFacade kingdeeTogetheFacade;
	
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	
	@Autowired
	ProjectContractService projectContractServiceClient;
	
	@Override
	public KingdeeExpensesRequisitionsResult expensesRequisitions(	final KingdeeExpensesRequisitionsOrder order) {
		return callInterface(new CallExternalInterface<KingdeeExpensesRequisitionsResult>() {
			
			@Override
			public KingdeeExpensesRequisitionsResult call() {
				logger.info("费用支付单同步到金碟  ：{}", order);
				// 若userId大于八位取后8位
				String dealMan = order.getDealMan();
				if (StringUtil.isNotBlank(dealMan) && dealMan.length() > 8) {
					order.setDealMan(dealMan.substring(dealMan.length() - 8, dealMan.length()));
				}
				return kingdeeTogetheFacade.expensesRequisitions(order);
			}
		});
	}
	
	@Override
	public KingdeeGatheringResult gathering(final KingdeeGatheringOrder order) {
		return callInterface(new CallExternalInterface<KingdeeGatheringResult>() {
			
			@Override
			public KingdeeGatheringResult call() {
				logger.info("收款单同步到金碟  ：{}", order);
				return kingdeeTogetheFacade.gathering(order);
			}
		});
	}
	
	@Override
	public KingdeePaymentResult payment(final KingdeePaymentOrder order) {
		return callInterface(new CallExternalInterface<KingdeePaymentResult>() {
			
			@Override
			public KingdeePaymentResult call() {
				logger.info("付款单同步到金碟  ：{}", order);
				return kingdeeTogetheFacade.payment(order);
			}
		});
	}
	
	@Override
	public KingdeeQueryAccountResult queryAccount(final KingdeeQueryAccountOrder order) {
		return callInterface(new CallExternalInterface<KingdeeQueryAccountResult>() {
			
			@Override
			public KingdeeQueryAccountResult call() {
				logger.info("查询会计科目  ：{}", order);
				return kingdeeTogetheFacade.queryAccount(order);
			}
		});
	}
	
	@Override
	public KingdeeQueryGuaranteeResult queryGuarantee(final KingdeeQueryGuaranteeOrder order) {
		return callInterface(new CallExternalInterface<KingdeeQueryGuaranteeResult>() {
			
			@Override
			public KingdeeQueryGuaranteeResult call() {
				return kingdeeTogetheFacade.queryGuarantee(order);
			}
		});
	}
	
	@Override
	public KingdeeClerkaResult synClearka(final KingdeeClerkaOrder order) {
		return callInterface(new CallExternalInterface<KingdeeClerkaResult>() {
			
			@Override
			public KingdeeClerkaResult call() {
				return kingdeeTogetheFacade.synClearka(order);
			}
		});
	}
	
	@Override
	public KingdeeContractResult synContract(final KingdeeContractOrder order) {
		return callInterface(new CallExternalInterface<KingdeeContractResult>() {
			
			@Override
			public KingdeeContractResult call() {
				return kingdeeTogetheFacade.synContract(order);
			}
		});
	}
	
	@Override
	public KingdeeCustomerResult synCustomer(final KingdeeCustomerOrder order) {
		return callInterface(new CallExternalInterface<KingdeeCustomerResult>() {
			
			@Override
			public KingdeeCustomerResult call() {
				return kingdeeTogetheFacade.synCustomer(order);
			}
		});
	}
	
	@Override
	public KingdeeOrganizationResult synOrganization(final KingdeeOrganizationOrder order) {
		return callInterface(new CallExternalInterface<KingdeeOrganizationResult>() {
			
			@Override
			public KingdeeOrganizationResult call() {
				logger.info("同步组织架构  ：{}", order);
				return kingdeeTogetheFacade.synOrganization(order);
			}
		});
	}
	
	@Override
	public KingdeeTernalTransResult ternalTrans(final KingdeeTernalTransOrder order) {
		return callInterface(new CallExternalInterface<KingdeeTernalTransResult>() {
			
			@Override
			public KingdeeTernalTransResult call() {
				logger.info("转账单同步到金碟  ：{}", order);
				// 若userId大于八位取后8位
				String dealMan = order.getDealMan();
				if (StringUtil.isNotBlank(dealMan) && dealMan.length() > 8) {
					order.setDealMan(dealMan.substring(dealMan.length() - 8, dealMan.length()));
				}
				return kingdeeTogetheFacade.ternalTrans(order);
			}
		});
	}
	
	@Override
	public KingdeeTravelExpenseResult travelExpense(final KingdeeTravelExpenseOrder order) {
		return callInterface(new CallExternalInterface<KingdeeTravelExpenseResult>() {
			
			@Override
			public KingdeeTravelExpenseResult call() {
				logger.info("差旅费报销单同步到金碟  ：{}", order);
				// 若userId大于八位取后8位
				String dealMan = order.getDealMan();
				if (StringUtil.isNotBlank(dealMan) && dealMan.length() > 8) {
					order.setDealMan(dealMan.substring(dealMan.length() - 8, dealMan.length()));
				}
				return kingdeeTogetheFacade.travelExpense(order);
			}
		});
	}
	
	public static void main(String[] args) {
		String str = "89223456";
		System.out.println(str.length());
		System.out.println(str.substring(str.length() - 8, str.length()));
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
						//if (areadySyncDeptMap.get(org.getCode()) == null) { //未同步的才同步
						
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
						//}
					}
				} catch (Exception e) {
					logger.error("同步组织架构出错 {} ", e);
				}
			}
			
			//			//同步客户
			//			if (StringUtil.isNotEmpty(order.getCustomerId())
			//				&& StringUtil.isNotEmpty(order.getCustomerName())) {
			//				try {
			//					KingdeeCustomerOrder customerOrder = new KingdeeCustomerOrder();
			//					customerOrder.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
			//					customerOrder.setCustomerCode(order.getCustomerId());
			//					customerOrder.setCustomerName(order.getCustomerName());
			//					logger.info("同步客户数据开始 {}", customerOrder);
			//					KingdeeCustomerResult customerResult = synCustomer(customerOrder);
			//					logger.info("同步客户数据结果 {}", customerResult);
			//				} catch (Exception e) {
			//					logger.error("同步客户数据出错", e);
			//				}
			//			}
			//			
			//			//同步合同
			//			try {
			//				if (StringUtil.isNotBlank(order.getContractNo())) {
			//					KingdeeContractOrder contractOrder = new KingdeeContractOrder();
			//					contractOrder.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
			//					contractOrder.setContractCode(order.getContractNo());
			//					if (order.isSystemContract()) {
			//						ProjectContractItemInfo contract = projectContractServiceClient
			//							.findByContractCode(order.getContractNo());
			//						if (contract != null) {
			//							contractOrder.setContractName(contract.getContractName());
			//						}
			//					} else {
			//						contractOrder.setContractName(order.getContractName());
			//					}
			//					if (StringUtil.isBlank(contractOrder.getContractName()))
			//						contractOrder.setContractName(contractOrder.getContractCode());
			//					logger.info("同步合同数据开始 {}", contractOrder);
			//					KingdeeContractResult contractrResult = synContract(contractOrder);
			//					logger.info("同步合同数据结果 {}", contractrResult);
			//					
			//				}
			//			} catch (Exception e) {
			//				logger.error("同步合同数据出错:{}", e);
			//			}
			//			
			//			//同步职员
			//			try {
			//				if (order.getUserId() > 0) {
			//					KingdeeClerkaOrder userOrder = new KingdeeClerkaOrder();
			//					userOrder.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
			//					userOrder.setUserCode(String.valueOf(order.getUserId()));
			//					if (StringUtil.isNotBlank(order.getUserName())) {
			//						userOrder.setUserName(order.getUserName());
			//					} else {
			//						SysUser user = bpmUserQueryService.findUserByUserId(order.getUserId());
			//						if (user != null) {
			//							userOrder.setUserName(user.getFullname());
			//						}
			//					}
			//					if (StringUtil.isBlank(userOrder.getUserName())) {
			//						userOrder.setUserName(userOrder.getUserCode());
			//					}
			//					logger.info("同步职员信息开始：{}", userOrder);
			//					KingdeeClerkaResult userResult = synClearka(userOrder);
			//					logger.info("同步职员信息结果：{}", userResult);
			//					
			//				}
			//			} catch (Exception e) {
			//				logger.error("同步职员信息出错：{}", e);
			//			}
			
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("同步基础数据到金蝶出错");
			logger.error("同步基础数据到金蝶出错{}", e);
		}
		return result;
	}
}
