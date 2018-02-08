package com.born.fcs.pm.test.fund;

import java.util.List;

import com.born.fcs.pm.ws.order.fund.FChargeNotificationFeeOrder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.test.SeviceTestBase;
import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationFeeInfo;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationInfo;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationOrder;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.fund.ChargeNotificationFeeService;
import com.born.fcs.pm.ws.service.fund.ChargeNotificationService;
import com.yjf.common.lang.util.money.Money;

public class OffineFundTest extends SeviceTestBase {
	
	@Autowired
	ChargeNotificationFeeService chargeNotificationFeeService;
	
	@Autowired
	ChargeNotificationService chargeNotificationService;
	
	@Test
	public void testSaveChargeNotification() {
		
		FChargeNotificationOrder order = new FChargeNotificationOrder();
		
		order.setFormCode(FormCodeEnum.CHARGE_NOTIFICATION);
		order.setCheckIndex(0);
		order.setCheckStatus(1);
		
		//order.setNotificationId(3);
		order.setProjectCode("20160405112001");
		order.setProjectName("我的承兑汇票担保项目2");
		order.setCustomerId(2);
		order.setCustomerName("李四2");
		order.setPayAccount("20145421351351");
		List<FChargeNotificationFeeOrder> feeList = Lists.newArrayList();
		FChargeNotificationFeeOrder feeInfo = new FChargeNotificationFeeOrder();
		feeInfo.setId(2);
		//feeInfo.setFeeType(FeeTypeEnum.FINANCE_CONSULT_FEE);
		//feeInfo.setChargeAmount(new Money(30000));
		feeList.add(feeInfo);
		order.setFeeList(feeList);
		
		FormBaseResult result = chargeNotificationService.saveChargeNotification(order);
		Assert.assertEquals(true, result.isSuccess());
	}
	
	@Test
	public void testQueryChargeNotification() {
		FChargeNotificationQueryOrder order = new FChargeNotificationQueryOrder();
		QueryBaseBatchResult<FChargeNotificationInfo> result = chargeNotificationService
			.queryChargeNotification(order);
		
		Assert.assertEquals(true, result.getPageSize() > 0);
		
	}
}
