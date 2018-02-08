package com.bornsoft.api.service.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bornsoft.facade.api.kingdee.KingdeeTogetheFacade;
import com.bornsoft.integration.kingdee.KingdeeDistanceClient;
import com.bornsoft.pub.enums.KingdeeServiceEnum;
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
import com.bornsoft.pub.result.kingdee.KingdeeQueryAccountResult.AccountInfo;
import com.bornsoft.pub.result.kingdee.KingdeeQueryGuaranteeResult;
import com.bornsoft.pub.result.kingdee.KingdeeTernalTransResult;
import com.bornsoft.pub.result.kingdee.KingdeeTravelExpenseResult;
import com.bornsoft.utils.enums.CommonResultEnum;
import com.yjf.common.env.Env;

/**
 * @Description: 金蝶接口实现类
 * @author:      xiaohui@yiji.com
 * @date         2016-9-1 上午11:18:36
 * @version:     v1.0
 */
@Service("kingdeeTogetheFacadeImplApi")
public class KingdeeTogetheFacadeImpl implements KingdeeTogetheFacade {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private KingdeeDistanceClient	kingdeeDistanceClient;
	
	@Override
	public KingdeeGatheringResult gathering(KingdeeGatheringOrder order) {
		logger.info("金蝶接口请求入参={}",order);
		KingdeeGatheringResult result =  kingdeeDistanceClient.execute(KingdeeGatheringResult.class,
				order, KingdeeServiceEnum.GATHERING);
		logger.info("金蝶接口请求出参={}",result);
		return result;
	}

	@Override
	public KingdeePaymentResult payment(KingdeePaymentOrder order) {
		logger.info("金蝶接口请求入参={}",order);
		KingdeePaymentResult result =  kingdeeDistanceClient.execute(KingdeePaymentResult.class, order,
				KingdeeServiceEnum.PAYMENT);
		logger.info("金蝶接口请求出参={}",result);
		return result;
	}

	@Override
	public KingdeeTravelExpenseResult travelExpense(
			KingdeeTravelExpenseOrder order) {
		logger.info("金蝶接口请求入参={}",order);
		KingdeeTravelExpenseResult result = kingdeeDistanceClient.execute(KingdeeTravelExpenseResult.class,
				order, KingdeeServiceEnum.TRAVEL_EXPENSE);
		logger.info("金蝶接口请求出参={}",result);
		return result;
	}

	@Override
	public KingdeeExpensesRequisitionsResult expensesRequisitions(
			KingdeeExpensesRequisitionsOrder order) {
		logger.info("金蝶接口请求入参={}",order);
		KingdeeExpensesRequisitionsResult result = kingdeeDistanceClient.execute(
				KingdeeExpensesRequisitionsResult.class, order,
				KingdeeServiceEnum.EXPENSES_REQUISITIONS);
		logger.info("金蝶接口请求出参={}",result);
		return result;
	}

	@Override
	public KingdeeTernalTransResult ternalTrans(KingdeeTernalTransOrder order) {
		logger.info("金蝶接口请求入参={}",order);
		KingdeeTernalTransResult result =  kingdeeDistanceClient.execute(KingdeeTernalTransResult.class,
				order, KingdeeServiceEnum.TERNAL_TRANS);
		logger.info("金蝶接口请求出参={}",result);
		return result;
	}

	@Override
	public KingdeeQueryAccountResult queryAccount(KingdeeQueryAccountOrder order) {
		logger.info("金蝶接口请求入参={}",order);
		KingdeeQueryAccountResult result = null;
		if(Env.isTest()){
			result = new KingdeeQueryAccountResult();
			result.setResultCode(CommonResultEnum.EXECUTE_SUCCESS);
			result.setOrderNo(order.getOrderNo());
			result.setResultMessage("模拟成功");
			List<AccountInfo> dataList = new ArrayList<>();
			AccountInfo info = new AccountInfo();
			dataList.add(info);
			info.setCode("1001");
			info.setName("应付工资");
			result.setDataList(dataList);
			result.setPageNumber(1);
			result.setPageSize(order.getPageSize());
			result.setTotalCount(1);
			result.setTotalPage(1);
		}else{
			result = kingdeeDistanceClient.execute(KingdeeQueryAccountResult.class,
					order, KingdeeServiceEnum.QUERY_ACCOUNT);
		}
		logger.info("金蝶接口请求出参={}",result);
		return result;
	}

	@Override
	public KingdeeQueryGuaranteeResult queryGuarantee(
			KingdeeQueryGuaranteeOrder order) {
		logger.info("金蝶接口请求入参={}",order);
		KingdeeQueryGuaranteeResult result =  kingdeeDistanceClient.execute(KingdeeQueryGuaranteeResult.class,
				order, KingdeeServiceEnum.QUERY_GUARANTEE);
		logger.info("金蝶接口请求出参={}",result);
		return result;
	}

	@Override
	public KingdeeOrganizationResult synOrganization(
			KingdeeOrganizationOrder order) {
		logger.info("金蝶接口请求入参={}",order);
		KingdeeOrganizationResult result =  kingdeeDistanceClient.execute(KingdeeOrganizationResult.class,
				order, KingdeeServiceEnum.SYN_ORGANIZATION);
		logger.info("金蝶接口请求出参={}",result);
		return result;
	}

	@Override
	public KingdeeClerkaResult synClearka(KingdeeClerkaOrder order) {
		logger.error("金蝶接口请求入参={}",order);
		KingdeeClerkaResult result =  kingdeeDistanceClient.execute(KingdeeClerkaResult.class, order,
				KingdeeServiceEnum.SYN_CLEARKA);
		logger.error("金蝶接口请求出参={}",result);
		return result;
	}

	@Override
	public KingdeeCustomerResult synCustomer(KingdeeCustomerOrder order) {
		logger.info("金蝶接口请求入参={}",order);
		KingdeeCustomerResult result =  kingdeeDistanceClient.execute(KingdeeCustomerResult.class,
				order, KingdeeServiceEnum.SYN_CUSTOMER);
		logger.info("金蝶接口请求出参={}",result);
		return result;
	}

	@Override
	public KingdeeContractResult synContract(KingdeeContractOrder order) {
		logger.info("金蝶接口请求入参={}",order);
		KingdeeContractResult result = kingdeeDistanceClient.execute(KingdeeContractResult.class,
				order, KingdeeServiceEnum.SYN_CONTRACT);
		logger.info("金蝶接口请求出参={}",result);
		return result;
	}
}
