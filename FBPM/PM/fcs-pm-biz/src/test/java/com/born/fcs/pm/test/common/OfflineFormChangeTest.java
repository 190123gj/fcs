package com.born.fcs.pm.test.common;

import java.util.Date;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import rop.thirdparty.com.google.common.collect.Maps;

import com.alibaba.druid.util.HttpClientUtils;
import com.born.fcs.pm.biz.service.common.SysParameterService;
import com.born.fcs.pm.test.SeviceTestBase;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.service.common.FormService;
import com.born.fcs.pm.ws.service.common.SysWebAccessTokenService;

public class OfflineFormChangeTest extends SeviceTestBase {
	
	@Autowired
	FormService formService;
	
	@Autowired
	SysWebAccessTokenService sysWebAccessTokenService;
	
	@Autowired
	SysParameterService sysParameterService;
	
	@Test
	public void test() {
		
		System.out.println("====================" + new Date());
		
		String formData = "id=11&spId=201&formId=3613&formCode=COUNCIL_SUMMARY_PROJECT_REVIEW&summaryId=209&checkIndex=3&checkStatus=0&projectCode=2016-YWB-411-001&tabId=201&initiatorName=&cancel=&initiatorId=0&initiatorAccount=&overview=&cancel=%E8%B4%AD%E7%BD%AE%E5%9B%BA%E5%AE%9A%E8%B5%84%E4%BA%A7%E3%80%81%E5%8F%8A%E5%85%B6%E8%AE%BE%E5%A4%87%E7%AD%89&loanPurpose=%E8%B4%AD%E7%BD%AE%E5%9B%BA%E5%AE%9A%E8%B5%84%E4%BA%A7%E3%80%81%E5%8F%8A%E5%85%B6%E8%AE%BE%E5%A4%87%E7%AD%89&timeLimit=2&timeUnit=Y&amount=123456789088.12&capitalChannelName=%E8%B5%84%E9%87%91%E6%B8%A0%E9%81%93&capitalChannelId=0&capitalSubChannelName=%E4%BA%8C%E7%BA%A7%E8%B5%84%E9%87%91%E6%B8%A0%E9%81%93&capitalSubChannelId=0&interestRate=12&otherFee=123&otherFeeType=AMOUNT&chargeWay=ONE&chargePhase=AFTER&chargeWayOrder%5B0%5D.phase=BEFORE_FIRST_LOAN&chargeWayOrder%5B0%5D.beforeDay=1&chargeWayOrder%5B0%5D.chargeOrder_destroyName=1&chargeWayOrder%5B0%5D.phase_destroyName=BEFORE_FIRST_LOAN&chargeWayOrder%5B0%5D.beforeDay_destroyName=&chargeWayOrder%5B0%5D.chargeOrder_destroyName=1&chargeWayOrder%5B0%5D.phase_destroyName=BEFORE_FIRST_LOAN&chargeWayOrder%5B0%5D.beforeDay_destroyName=&pledgeOrders%5B0%5D.pledgeType=LAND&pledgeOrders%5B0%5D.pledgeProperty=SELL&pledgeOrders%5B0%5D.ownership=123&pledgeOrders%5B0%5D.address=123&pledgeOrders%5B0%5D.warrantNo=123&pledgeOrders%5B0%5D.num=12&pledgeOrders%5B0%5D.amountStr=123123&pledgeOrders%5B0%5D.ratio=31&mortgageOrders%5B0%5D.pledgeType=HOUSE&mortgageOrders%5B0%5D.pledgeProperty=HOUSE&mortgageOrders%5B0%5D.ownership=123&mortgageOrders%5B0%5D.address=123&mortgageOrders%5B0%5D.warrantNo=123&mortgageOrders%5B0%5D.num=11&mortgageOrders%5B0%5D.amountStr=11&mortgageOrders%5B0%5D.ratio=22&guarantorOrders%5B0%5D.guarantor=12313&guarantorOrders%5B0%5D.guaranteeAmountStr=111&guarantorOrders%5B0%5D.guaranteeWay=11231&processFlag=C&processFlag=D&creditLevel=A1&customerGrades%5B0%5D.adjustType=UP_1_LEVEL&customerGrades%5B0%5D.upDown=DOWN&customerGrades%5B0%5D.upDownRate=12&assetLiabilityRatio=123&debtRatios%5B0%5D.thresholdRatio=123&debtRatios%5B0%5D.adjustRatio=123&debtRatios%5B0%5D.upDown=UP&debtRatios%5B0%5D.upDownRate=123&others%5B0%5D.content=&remark=qwqweqweqweqewqweqew";
		String url = sysParameterService.getSysParameterValue(SysParamEnum.SYS_PARAM_FACE_WEB_URL
			.code()) + "/projectMg/meetingMg/summary/saveEntrustedProjectCs.htm";
		
		Map<String, String> params = Maps.newHashMap();
		String[] parameters = formData.split("&");
		for (String parameter : parameters) {
			String[] param = parameter.split("=");
			params.put(param[0], param.length > 1 ? param[1] : null);
		}
		
		HttpClientUtils.post(url,
			formData + "&accessToken=69109a48-2527-4acc-acf6-09ddc37fdbbf&isChangeRecord=IS", 3000);
		
		System.out.println("====================" + new Date());
	}
}
