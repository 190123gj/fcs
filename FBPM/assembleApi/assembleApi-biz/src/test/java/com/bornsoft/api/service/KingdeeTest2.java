//package com.bornsoft.api.service;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import com.bornsoft.api.service.facade.impl.KingdeeTogetheFacadeImpl;
//import com.bornsoft.facade.api.kingdee.KingdeeTogetheFacade;
//import com.bornsoft.pub.order.kingdee.KingdeeExpensesRequisitionsOrder;
//import com.bornsoft.pub.order.kingdee.KingdeeExpensesRequisitionsOrder.ExpensesRequisitionsInfo;
//import com.bornsoft.pub.order.kingdee.KingdeeGatheringOrder;
//import com.bornsoft.pub.order.kingdee.KingdeeGatheringOrder.GatheringInfo;
//import com.bornsoft.pub.order.kingdee.KingdeeOrganizationOrder;
//import com.bornsoft.pub.order.kingdee.KingdeePaymentOrder;
//import com.bornsoft.pub.order.kingdee.KingdeePaymentOrder.PaymentInfo;
//import com.bornsoft.pub.order.kingdee.KingdeeQueryAccountOrder;
//import com.bornsoft.pub.order.kingdee.KingdeeTernalTransOrder;
//import com.bornsoft.pub.order.kingdee.KingdeeTravelExpenseOrder;
//import com.bornsoft.pub.order.kingdee.KingdeeTernalTransOrder.TernalTransInfo;
//import com.bornsoft.pub.order.kingdee.KingdeeTravelExpenseOrder.TravelExpenseInfo;
//import com.bornsoft.pub.result.kingdee.KingdeeOrganizationResult;
//import com.bornsoft.pub.result.kingdee.KingdeeQueryAccountResult;
//import com.bornsoft.utils.base.BornSynResultBase;
//import com.bornsoft.utils.enums.CommonResultEnum;
//
///**
// * @Description: 金蝶测试类
// * @author:      xiaohui@yiji.com
// * @date         2016-10-25 下午3:13:33
// * @version:     v1.0
// */
//public class KingdeeTest2{
//
//	private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//	
//	private static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
//
//	//@Autowired
//	static KingdeeTogetheFacade kingdeeTogetheFacade = new KingdeeTogetheFacadeImpl();
//
//	public static void main(String[] args) {
//		//同步组织架构
//		synOrganization();
//
//		//查询会计科目
//		queryAccount();
//
//		// 收款单
//		gathering();
//
//		//付款单
//		payment();
//
//		//差旅费报销单
//		travelExpense();
//
//		//费用申请单
//		expensesRequisitions();
//
//		//内部转账单
//		ternalTrans();
//	}
//
//	/**
//	 * 差旅费报销单
//	 */
//	private static void travelExpense() {
//		KingdeeTravelExpenseOrder order = new KingdeeTravelExpenseOrder();
//		order.setOrderNo(createOrderNo());
//		order.setBizNo("123456");
//		order.setDealMan("2345678");
//		order.setDealManDeptCode("012");
//		order.setDealManName("sssss");
//		order.setGwk(false);
//		List<TravelExpenseInfo> list = new ArrayList<TravelExpenseInfo>();
//		TravelExpenseInfo info = new TravelExpenseInfo();
//		info.setFeeType("差旅费");
//		info.setAmount("300");
//		info.setBxDeptCode("012");
//		info.setTaxAmount("200");
//		info.setCredit("1001");
//		list.add(info);
//		order.setFeeDetail(list);
//		checkResult(kingdeeTogetheFacade.travelExpense(order));
//	}
//
//	/**
//	 * 费用申请单
//	 */
//	private static void expensesRequisitions() {
//		KingdeeExpensesRequisitionsOrder order = new KingdeeExpensesRequisitionsOrder();
//		order.setOrderNo(createOrderNo());
//		order.setBizNo("11111");
//		order.setDealMan("1111111");
//		order.setDealManName("李");
//		order.setDealManDeptCode("012");
//		order.setGwk(false);
//		
//		List<ExpensesRequisitionsInfo> list = new ArrayList<ExpensesRequisitionsInfo>();
//		ExpensesRequisitionsInfo info = new ExpensesRequisitionsInfo();
//		
//		info.setFeeType("差旅费");
//		info.setAmount("10");
//		info.setBxDeptCode("012");
//		info.setTaxAmount("30");
//		info.setCredit("1001");
//
//		
//		list.add(info);
//		order.setFeeDetail(list);
//		checkResult(kingdeeTogetheFacade.expensesRequisitions(order));
//	}
//
//	/**
//	 * 内部转账单
//	 */
//	private static void ternalTrans() {
//		KingdeeTernalTransOrder order = new KingdeeTernalTransOrder();
//		order.setOrderNo(createOrderNo());
//		order.setBizNo("1111");
//		order.setDealMan("11111");
//		order.setDealManName("222222");
//		order.setDealManDeptCode("012");
//
//		List<TernalTransInfo> list = new ArrayList<TernalTransInfo>();
//		TernalTransInfo info = new TernalTransInfo();
//		info.setSkAccount("20");
//		info.setFkAccount("30");
//		info.setAmount("40");
//		info.setDebit("1001");
//		info.setCredit("1001");
//
//		list.add(info);
//		order.setTransDetail(list);
//		
//		checkResult(kingdeeTogetheFacade.ternalTrans(order));
//	}
//
//	/**
//	 * 付款单
//	 */
//	private static void payment() {
//		KingdeePaymentOrder order = new KingdeePaymentOrder();
//		order.setOrderNo(createOrderNo());
//		order.setBusiType("111111111");
//		order.setBizNo("2222222222");
//		order.setDeptCode("012");
//		List<PaymentInfo> list = new ArrayList<PaymentInfo>();
//		PaymentInfo info = new PaymentInfo();
//		info.setFeeType("12345");
//		info.setAmount("100");
//		info.setFkDate(format2.format(new Date()));
//		info.setDebit("1001");
//		info.setCredit("1001");
//		
//		list.add(info);
//		order.setFeeDetail(list);
//		checkResult(kingdeeTogetheFacade.payment(order));
//	}
//
//	/**
//	 * 收款单
//	 */
//	private static void gathering() {
//		KingdeeGatheringOrder order = new KingdeeGatheringOrder();
//		order.setOrderNo(createOrderNo());
//		order.setBusiType("fffffff");
//		order.setBizNo("2222222222");
//		order.setDeptCode("012");
//		List<GatheringInfo> list = new ArrayList<GatheringInfo>();
//		GatheringInfo info = new GatheringInfo();
//		info.setFeeType("12345");
//		info.setAmount("100");
//		info.setSkDate(format2.format(new Date()));
//		info.setDebit("1001");
//		info.setCredit("1001");
//		list.add(info);
//		order.setFeeDetail(list);
//		checkResult(kingdeeTogetheFacade.gathering(order));
//	}
//
//	/**
//	 * 同步组织架构
//	 */
//	private static void synOrganization() {
//		KingdeeOrganizationOrder order = new KingdeeOrganizationOrder();
//		order.setOrderNo(createOrderNo());
//		order.setDeptCode("22222223");
//		order.setDeptName("parent test account");
//		//注： 首次同步时，parent设为空
//		order.setParentDeptCode("1111111");
//		order.setParentDeptName("test account");
//		KingdeeOrganizationResult result = kingdeeTogetheFacade.synOrganization(order);
//		checkResult(result);
//	}
//
//	/**
//	 * 查询会计科目
//	 */
//	private static void queryAccount() {
//		KingdeeQueryAccountOrder order = new KingdeeQueryAccountOrder();
//		order.setOrderNo(createOrderNo());
//		order.setDeptCode("012");
//		order.setPageNumber(10);
//		order.setPageSize(1);
//		KingdeeQueryAccountResult result =	kingdeeTogetheFacade.queryAccount(order);
//		checkResult(result);
//	}
//
//	/**
//	 * 检查结果
//	 * @param result
//	 */
//	private static void checkResult(BornSynResultBase result) {
//		if (result.getResultCode() != CommonResultEnum.EXECUTE_SUCCESS) {
//			throw new IllegalArgumentException(result.getResultMessage());
//		}
//	}
//
//	/**
//	 * 生成流水号
//	 * @return
//	 */
//	private static String createOrderNo() {
//		return format.format(new Date());
//	}
//}
