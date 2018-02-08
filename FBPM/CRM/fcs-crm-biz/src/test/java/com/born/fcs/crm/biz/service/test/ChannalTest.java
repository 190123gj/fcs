package com.born.fcs.crm.biz.service.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.born.fcs.crm.ws.service.ChannalContractService;
import com.born.fcs.crm.ws.service.ChannalService;
import com.born.fcs.crm.ws.service.enums.ChanalTypeEnum;
import com.born.fcs.crm.ws.service.info.ChannalInfo;
import com.born.fcs.crm.ws.service.order.ChannalOrder;
import com.born.fcs.crm.ws.service.order.query.ChannalQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;

public class ChannalTest extends BaseTest {
	
	@Autowired
	ChannalContractService channalContractService;
	@Autowired
	ChannalService channalService;
	
	@Test
	public void test() {
		//		ChannalContractOrder order = new ChannalContractOrder();
		//		order.setChannalCode("");
		//		order.setChannalName("测试保存sss");
		//		order.setChannalType(ChanalTypeEnum.YH.code());
		//		order.setCheckStatus(1);
		//		order.setContract("dsdd");
		//		order.setContractNo("44444");
		//		order.setFormId(288L);
		//		FcsBaseResult result = channalContractService.save(order);
		//		logger.info("合同保存结果result={}", result);
		//      // 更新合同状态
		//		channalContractService.updateStatus("010101001", ContractStatusEnum.RETURN);
		
		String code = channalService.createChannalCode(ChanalTypeEnum.JJ.code());
		logger.info("新增渠道生成渠道号：channalCode={}", code);
		code = channalService.createChannalCode(ChanalTypeEnum.YH.code());
		logger.info("新增渠道生成渠道号：channalCode={}", code);
		code = channalService.createChannalCode(ChanalTypeEnum.JYPT.code());
		logger.info("新增渠道生成渠道号：channalCode={}", code);
	}
	
	@Test
	public void test1() {
		for (ChanalTypeEnum type : ChanalTypeEnum.getAllEnum()) {
			ChannalQueryOrder queryOrder = new ChannalQueryOrder();
			queryOrder.setChannelType(type.getCode());
			queryOrder.setPageSize(1000);
			QueryBaseBatchResult<ChannalInfo> list = channalService.list(queryOrder);
			long count = 1;
			String code = type.getPreCode();
			for (ChannalInfo info : list.getPageList()) {
				String thisCode = code;
				ChannalOrder order = new ChannalOrder();
				BeanCopier.staticCopy(info, order);
				order.setListData(info.getListData());
				for (int i = String.valueOf(count).length(); i < 5; i++) {
					thisCode += "0";
				}
				thisCode += String.valueOf(count);
				order.setChannelCode(thisCode);
				channalService.update(order);
				count++;
			}
		}
		
	}
}
