package com.born.fcs.pm.test.capitalappropriationapply;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.born.fcs.pm.test.SeviceTestBase;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyFeeOrder;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.service.capitalappropriationapply.FCapitalAppropriationApplyService;

/**
 * 
 * 
 * @author Ji
 * 
 */
public class FCapitalAppropriationApplyTest extends SeviceTestBase {
	
	@Autowired
	FCapitalAppropriationApplyService fCapitalAppropriationApplyService;
	
	@Test
	public void testSave() {
		
		FCapitalAppropriationApplyOrder order = new FCapitalAppropriationApplyOrder();
		
		order.setFormCode(FormCodeEnum.FCAPITAL_APPROPRIATION_APPLY);
		order.setCheckIndex(0);
		order.setCheckStatus(1);
		//		order.setApplyId(15L);
		//		order.setFormId(50L);
		order.setAttach("adsdad");
		order.setProjectCode("1459842767433");
		order.setProjectName("test22");
		order.setProjectType("理财类");
		
		List<FCapitalAppropriationApplyFeeOrder> listFeeOrder = new ArrayList<FCapitalAppropriationApplyFeeOrder>();
		FCapitalAppropriationApplyFeeOrder feeOrder = new FCapitalAppropriationApplyFeeOrder();
		feeOrder.setAppropriateAmount(20.0);
		feeOrder.setAppropriateReason("今天天气好");
		feeOrder.setRemark("今天天气真的很好");
		
		listFeeOrder.add(feeOrder);
		order.setfCapitalAppropriationApplyFeeOrders(listFeeOrder);
		FormBaseResult result = (FormBaseResult) fCapitalAppropriationApplyService.save(order);
		
		Assert.assertEquals(true, result.isSuccess());
	}
	
}
