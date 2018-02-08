package com.born.fcs.crm.biz.service.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.born.fcs.crm.integration.risk.service.RiskSystemFacadeClient;
import com.born.fcs.crm.ws.service.BusyRegionService;
import com.born.fcs.crm.ws.service.info.BusyRegionInfo;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.bornsoft.pub.enums.CreditLevelEnum;
import com.bornsoft.pub.order.risk.SynCustomLevelOrder;
import com.bornsoft.pub.order.risk.SynCustomLevelOrder.CustomLevelInfo;
import com.bornsoft.utils.base.BornSynResultBase;

public class SeviceTestBase extends BaseTest {
	
	@Autowired
	RiskSystemFacadeClient riskSystemFacadeClient;
	@Autowired
	BusyRegionService busyRegionService;
	
	@Test
	public void test() {
		SynCustomLevelOrder arg0 = new SynCustomLevelOrder();
		CustomLevelInfo levelInfo = new CustomLevelInfo();
		levelInfo.setLicenseNo("913101153244723624");
		levelInfo.setCustomName("上海吾步信息技术有限公司");
		levelInfo.setCreditLevel(CreditLevelEnum.A);
		List<CustomLevelInfo> list = new ArrayList<>();
		list.add(levelInfo);
		arg0.setList(list);
		arg0.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
		BornSynResultBase synResult = riskSystemFacadeClient.synCustomLevel(arg0);
		logger.info("评级审核通过后同步等级到风险系统结果:{}", synResult);
		
	}
	
	@Test
	public void test1() {
		//		FcsBaseResult result = busyRegionService.queryStatus("1.20000006810016.20000006810018.");
		//		logger.info("启用状态：result={}", result);
		//		result = busyRegionService.changeStatus(null, BooleanEnum.IS);
		//		logger.info("修改状态：result={}", result);
		//		BusyRegionQueryOrder order = new BusyRegionQueryOrder();
		//		order.setDepPath("1.20000006810016.20000006810018.");
		//		QueryBaseBatchResult<BusyRegionInfo> result1 = busyRegionService.list(order);
		//		logger.info("区域查询：result={}", result1);
		
		List<BusyRegionInfo> list = busyRegionService.queryByDepPath("");
		logger.info("区域查询：result={}", list);
	}
}
