package com.born.fcs.crm.integration.risk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.integration.bpm.service.CallExternalInterface;
import com.born.fcs.crm.integration.bpm.service.ClientAutowiredBaseService;
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

@Service("kingdeeTogetheFacadeClient")
public class KingdeeTogetheFacadeClient extends ClientAutowiredBaseService implements
																			KingdeeTogetheFacade {
	@Autowired
	KingdeeTogetheFacade kingdeeTogetheFacade;
	
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
	
}
