//package com.bornsoft.api.service;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.bornsoft.api.service.facade.impl.KingdeeTogetheFacadeImpl;
//import com.bornsoft.pub.order.kingdee.KingdeeQueryAccountOrder;
//import com.bornsoft.pub.result.kingdee.KingdeeQueryAccountResult;
//
///**
// * @Description: 金蝶测试类
// * @author:      xiaohui@yiji.com
// * @date         2016-10-25 下午3:13:33
// * @version:     v1.0
// */
//public class KingdeeTest extends ServiceTestBase {
//
//	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//	
//	@Autowired
//	KingdeeTogetheFacadeImpl kingdeeTogetheFacade;
//	
//	@Test
//	public void test() {
//		//查询会计科目
//		queryAccount();
//	}
//	
//	/**
//	 * 查询会计科目
//	 */
//	private void queryAccount() {
//		KingdeeQueryAccountOrder order = new KingdeeQueryAccountOrder();
//		order.setOrderNo(createOrderNo());
//		order.setDeptCode("1111111");
//		order.setPageNumber(10);
//		order.setPageSize(1);
//		KingdeeQueryAccountResult result =	kingdeeTogetheFacade.queryAccount(order);
//		System.out.println(result);
//	}
//	
//	private String createOrderNo() {
//		return format.format(new Date());
//	}
//}
