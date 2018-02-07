package com.born.fcs.face.web.controller.customer.evaluting;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.born.fcs.crm.ws.service.enums.StandardTypeEnums;
import com.born.fcs.crm.ws.service.info.FinancialSetInfo;
import com.born.fcs.crm.ws.service.order.ListOrder;
import com.born.fcs.crm.ws.service.order.query.FinancialSetQueryOrder;
import com.born.fcs.crm.ws.service.result.FinancialSetResult;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

/**
 * 财务指标配置
 * @Description 类型说明
 * @param type {综合类： CW_ZH;工业类： CW_GY;农业类： CW_NY;商贸类： CW_SM;建安类: CW_JA;交通运输类:
 * CW_JT;房产开发类: CW_FC}
 * */
@Controller
@RequestMapping("/customerMg/financialEvalueSet")
public class FinancialSetController extends BaseController {
	
	private static String VM_PATH = "/customerMg/clientRate/finance/";
	
	@RequestMapping("info.htm")
	public String getInfo(FinancialSetQueryOrder queryOrder, Model model) {
		if (StringUtil.isBlank(queryOrder.getType())) {
			queryOrder.setType("CW_ZH");
		}
		
		FinancialSetResult result = evaluatingFinancialSetClient.list(queryOrder);
		if (result.isSuccess()) {
			model.addAttribute("mapInfo", result.getMapResult());
			model.addAttribute("listData1", result.getListData1());
			model.addAttribute("listData2", result.getListData2());
		}
		setCustomerEnums(model);
		model.addAttribute("type", queryOrder.getType());
		model.addAttribute("name", StandardTypeEnums.getMsgByCode(queryOrder.getType()));
		
		return VM_PATH + "ordinaryNormGradeFinance.vm";
		
	}
	
	/**
	 * 特殊配置部分为financialSetInfoTs 取出来放进financialSetInfo中
	 * */
	@ResponseBody
	@RequestMapping("update.htm")
	public Object update(ListOrder list) {
		FcsBaseResult result = new FcsBaseResult();
		List<FinancialSetInfo> tsList = list.getFinancialSetInfoTs();
		if (ListUtil.isNotEmpty(tsList)) {
			list.getFinancialSetInfo().addAll(tsList);
		}
		if (ListUtil.isNotEmpty(list.getFinancialSetInfo())) {
			result = evaluatingFinancialSetClient.update(list);
		}
		return toJSONResult(result, "更新");
		
	}
	
}
