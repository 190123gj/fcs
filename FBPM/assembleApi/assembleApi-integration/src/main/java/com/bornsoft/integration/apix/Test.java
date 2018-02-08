/*package com.bornsoft.integration.apix;

import com.bornsoft.pub.order.apix.ApixDishonestQueryOrder;
import com.bornsoft.pub.order.apix.ApixDishonestQueryOrder.ApixUserTypeEnum;
import com.bornsoft.pub.order.apix.ApixLocationBasedOrder;
import com.bornsoft.pub.order.apix.ApixLocationBasedOrder.ApixLocationBasedMobUnicOrder;
import com.bornsoft.pub.order.apix.ApixValidateBankCardOrder;
import com.bornsoft.pub.order.apix.ApixValidateBankCardOrder.ValidateTypeEnum;
import com.bornsoft.pub.order.apix.ApixValidateIdCardOrder;
import com.bornsoft.pub.order.apix.ApixValidateMobileOrder;
import com.bornsoft.pub.result.apix.ApixDishonestQueryResult;
import com.bornsoft.pub.result.apix.ApixLocationBasedResult;
import com.bornsoft.pub.result.apix.ApixValidateBankCardResult;
import com.bornsoft.pub.result.apix.ApixValidateIdCardResult;
import com.bornsoft.pub.result.apix.ApixValidateMobileResult;

public class Test {
	
	private static ApixDistanceClient apix= new ApixDistanceClient();
	public static void main(String[] args) {}
	
	@org.junit.Test
	public void vlidateMobile(){
		ApixValidateMobileOrder order = new ApixValidateMobileOrder();
		order.setApixKey("39afcb5385a6495d6e63da26a4e53116");
		order.setCardno("140522199301026835");
		order.setName("王育锋");
		order.setPhone("18523114705");
		System.out.println(apix.execute(order, ApixValidateMobileResult.class));;
	}
	
	@org.junit.Test
	public void vlidateBankCard(){
		ApixValidateBankCardOrder order = new ApixValidateBankCardOrder();
		order.setApixKey("39afcb5385a6495d6e63da26a4e53116");
		order.setBankcardno("6212261903000082705");
		order.setCertNo("140522199301026835");
		order.setName("王育锋");
		order.setValidateType(ValidateTypeEnum.BANK_CARD_FOUR);
		order.setPhone("18523114705");
		System.out.println(apix.execute(order, ApixValidateBankCardResult.class));
	}
	
	@org.junit.Test
	public void validateIdCard(){
		ApixValidateIdCardOrder order = new ApixValidateIdCardOrder();
		order.setApixKey("39afcb5385a6495d6e63da26a4e53116");
		order.setCertNo("140522199301026835");
		order.setName("王育锋");
		System.out.println(apix.execute(order, ApixValidateIdCardResult.class));
	}
	
	@org.junit.Test
	public void queryDishonest(){
		ApixDishonestQueryOrder order = new ApixDishonestQueryOrder();
		order.setApixKey("49ae351054cf4fa05d7830d3a9c201de");
		order.setCertNo("75009I992");
		order.setName("重庆市博恩软件有限公司");
//		order.setEmail("deeplan@foxmail.com");
//		order.setPhone("18523114705");
		order.setType(ApixUserTypeEnum.COMPANY);
		System.out.println(apix.execute(order, ApixDishonestQueryResult.class));
		
	}
	
	@org.junit.Test
	public void locationBasedService(){
		ApixLocationBasedMobUnicOrder order = new ApixLocationBasedOrder.ApixLocationBasedMobUnicOrder();
		order.setApixKey("fd9264b4b65049e447dd5b45119313ba");
		order.setCellId("29044");
		order.setIsHex("0");
		order.setLac("41745");
		order.setOperator(ApixLocationBasedOrder.OperatorEnum.Unicom);
		System.out.println(apix.execute(order, ApixLocationBasedResult.class));
		
	}
	
	
}
*/