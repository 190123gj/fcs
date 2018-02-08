package com.bornsoft.facade.api.kingdee;

import javax.jws.WebService;

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

/**
 * @Description: 金蝶相关接口
 * @author:      xiaohui@yiji.com
 * @date         2016-9-1 上午11:00:04
 * @version:     v1.0
 */
@WebService
public interface KingdeeTogetheFacade {
	
	/**
	 * 收款单
	 * @param order
	 * @return
	 */
	public KingdeeGatheringResult gathering(KingdeeGatheringOrder order);
	
	/**
	 * 付款单
	 * @param order
	 * @return
	 */
	public KingdeePaymentResult payment(KingdeePaymentOrder order);
	
	/**
	 * 差旅费报销单
	 * @param order
	 * @return
	 */
	public KingdeeTravelExpenseResult travelExpense(KingdeeTravelExpenseOrder order);
	
	/**
	 * 费用申请单
	 * @param order
	 * @return
	 */
	public KingdeeExpensesRequisitionsResult expensesRequisitions(KingdeeExpensesRequisitionsOrder order);
	
	/**
	 * 内部转账单
	 * @param order
	 * @return
	 */
	public KingdeeTernalTransResult ternalTrans(KingdeeTernalTransOrder order);
	
	/**
	 * 会计科目查询
	 * @param order
	 * @return
	 */
	public KingdeeQueryAccountResult queryAccount(KingdeeQueryAccountOrder order);

	/**
	 * 保证金账户查询
	 * @param order
	 * @return
	 */
	@Deprecated
	public KingdeeQueryGuaranteeResult queryGuarantee(KingdeeQueryGuaranteeOrder order);

	/**
	 * 同步组织架构
	 * @param order
	 * @return
	 */
	public KingdeeOrganizationResult synOrganization(KingdeeOrganizationOrder order);
	
	/**
	 * 同步职工信息
	 * @param order
	 * @return
	 */
	@Deprecated
	public KingdeeClerkaResult synClearka(KingdeeClerkaOrder order);
	
	/**
	 * 同步客户信息
	 * @param order
	 * @return
	 */
	@Deprecated
	public KingdeeCustomerResult synCustomer(KingdeeCustomerOrder order);
	
	/**
	 * 同步合同信息
	 * @param order
	 * @return
	 */
	@Deprecated
	public KingdeeContractResult synContract(KingdeeContractOrder order);
}
