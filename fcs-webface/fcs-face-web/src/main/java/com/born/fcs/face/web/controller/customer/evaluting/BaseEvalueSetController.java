package com.born.fcs.face.web.controller.customer.evaluting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.crm.ws.service.info.EvaluatingBaseSetInfo;
import com.born.fcs.crm.ws.service.order.ListOrder;
import com.born.fcs.crm.ws.service.order.query.EvaluatingBaseSetQueryOrder;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

/**
 * 客户管理-分级评价配置
 * @Description: type 描述
 * @param: 一般企业类：YBQY:一般企业;
 * @param: 城市开发类： CTZG:主观;CTBZ:标准值比较;CTCW:财务;
 * @param: 公用事业类：GYWBZ:无标准部分;GYYBZ:有标准部分
 * */
@Controller
@RequestMapping("/customerMg/evalueSet")
public class BaseEvalueSetController extends BaseController {
	private static String VM_PATH = "/customerMg/clientRate/ordinary/";//一般企业类指标评分表配置
	private static String VM_PATH_CTZG = "/customerMg/clientRate/cityEnterprise/subjectivity/";
	private static String VM_PATH_CTBZ = "/customerMg/clientRate/cityEnterprise/standardVal/";
	private static String VM_PATH_CTCW = "/customerMg/clientRate/cityEnterprise/terraceFinance/";
	private static String VM_PATH_GYWBZ = "/customerMg/clientRate/publicCause/notHaveStandardVal/";
	private static String VM_PATH_GYYBZ = "/customerMg/clientRate/publicCause/haveStandardVal/";
	
	//页面选项
	private static Map<String, String> PAGE_MAP = new HashMap<String, String>();
	static {
		//一般企业类配置页面   key= type + level   ||  type + V 
		PAGE_MAP.put("YBQY1", VM_PATH + "ordinaryNormGradeOneHierarchy.vm");
		PAGE_MAP.put("YBQY2", VM_PATH + "ordinaryNormGradeTwoHierarchy.vm");
		PAGE_MAP.put("YBQY3", VM_PATH + "ordinaryNormGradeThreeHierarchy.vm");
		PAGE_MAP.put("YBQY4", VM_PATH + "ordinaryNormGradeFourHierarchy.vm");
		PAGE_MAP.put("YBQYV", VM_PATH + "ordinaryNormGradePreview.vm");
		//城市开发企业类-主观部分
		PAGE_MAP.put("CTZG1", VM_PATH_CTZG + "cityEnterpriseNormOneHierarchy.vm");
		PAGE_MAP.put("CTZG2", VM_PATH_CTZG + "cityEnterpriseNormTwoHierarchy.vm");
		PAGE_MAP.put("CTZG3", VM_PATH_CTZG + "cityEnterpriseNormThreeHierarchy.vm");
		PAGE_MAP.put("CTZGV", VM_PATH_CTZG + "cityEnterpriseNormPreview.vm");
		//城市开发企业类-标准值比较部分
		PAGE_MAP.put("CTBZ1", VM_PATH_CTBZ + "cityEnterpriseNormOneHierarchy.vm");
		PAGE_MAP.put("CTBZ2", VM_PATH_CTBZ + "cityEnterpriseNormTwoHierarchy.vm");
		PAGE_MAP.put("CTBZ3", VM_PATH_CTBZ + "cityEnterpriseNormThreeHierarchy.vm");
		PAGE_MAP.put("CTBZV", VM_PATH_CTBZ + "cityEnterpriseNormPreview.vm");
		//城市开发企业类-财务部分
		PAGE_MAP.put("CTCW1", VM_PATH_CTCW + "cityEnterpriseNormOneHierarchy.vm");
		PAGE_MAP.put("CTCW2", VM_PATH_CTCW + "cityEnterpriseNormTwoHierarchy.vm");
		PAGE_MAP.put("CTCW3", VM_PATH_CTCW + "cityEnterpriseNormThreeHierarchy.vm");
		PAGE_MAP.put("CTCWV", VM_PATH_CTCW + "cityEnterpriseNormPreview.vm");
		//公用事业类-无标准
		PAGE_MAP.put("GYWBZ1", VM_PATH_GYWBZ + "publicCauseNormOneHierarchy.vm");
		PAGE_MAP.put("GYWBZ2", VM_PATH_GYWBZ + "publicCauseNormTwoHierarchy.vm");
		PAGE_MAP.put("GYWBZ3", VM_PATH_GYWBZ + "publicCauseNormThreeHierarchy.vm");
		PAGE_MAP.put("GYWBZV", VM_PATH_GYWBZ + "publicCauseNormPreview.vm");
		//公用事业类-有标准
		PAGE_MAP.put("GYYBZ1", VM_PATH_GYYBZ + "publicCauseNormOneHierarchy.vm");
		PAGE_MAP.put("GYYBZ2", VM_PATH_GYYBZ + "publicCauseNormTwoHierarchy.vm");
		PAGE_MAP.put("GYYBZ3", VM_PATH_GYYBZ + "publicCauseNormThreeHierarchy.vm");
		PAGE_MAP.put("GYYBZV", VM_PATH_GYYBZ + "publicCauseNormPreview.vm");
		
	}
	
	/**
	 * 一般企业类
	 * */
	@RequestMapping("ordinary.htm")
	public String ordinary(EvaluatingBaseSetQueryOrder order, Boolean view, HttpSession session,
							Model model) {
		if (StringUtil.isBlank(order.getType())) {
			order.setType("YBQY");
		}
		if (StringUtil.isBlank(order.getLevel())) {
			order.setLevel("1");
		}
		return evalueList(order, view, session, model);
	}
	
	/**
	 * 公用事业类
	 * */
	@RequestMapping("publicCause.htm")
	public String publicCause(EvaluatingBaseSetQueryOrder order, Boolean view, HttpSession session,
								Model model) {
		if (StringUtil.isBlank(order.getType())) {
			order.setType("GYWBZ");
		}
		if (StringUtil.isBlank(order.getLevel())) {
			order.setLevel("1");
		}
		//一级指标页面 统计本大类下其他小类总分
		if (StringUtil.equals("1", order.getLevel())) {
			List<String> typeList = new ArrayList<>();
			typeList.add("GYWBZ");
			typeList.add("GYYBZ");
			typeList.remove(order.getType());//除去本类型
			countScore(typeList, model);
		}
		
		return evalueList(order, view, session, model);
	}
	
	/**
	 * 城市开发类
	 * */
	@RequestMapping("cityEnterprise.htm")
	public String cityEnterprise(EvaluatingBaseSetQueryOrder order, Boolean view,
									HttpSession session, Model model) {
		if (StringUtil.isBlank(order.getType())) {
			order.setType("CTZG");
		}
		if (StringUtil.isBlank(order.getLevel())) {
			order.setLevel("1");
		}
		//一级指标页面 统计本大类下其他小类总分
		if (StringUtil.equals("1", order.getLevel())) {
			List<String> typeList = new ArrayList<>();
			typeList.add("CTZG");
			typeList.add("CTBZ");
			typeList.add("CTCW");
			typeList.remove(order.getType());//除去本类型
			countScore(typeList, model);
		}
		return evalueList(order, view, session, model);
	}
	
	/**
	 * @param view 是否预览，是预览的话，leve设为当前指标最后一级
	 * 
	 * **/
	public String evalueList(EvaluatingBaseSetQueryOrder queryOrder, Boolean view,
								HttpSession session, Model model) {
		String page = "";
		if (view != null && view) {
			page = PAGE_MAP.get(queryOrder.getType() + "V");
		} else {
			page = PAGE_MAP.get(queryOrder.getType() + queryOrder.getLevel());
		}
		queryOrder.setPageSize(1000);
		QueryBaseBatchResult<EvaluatingBaseSetInfo> batchResult = evaluatingBaseSetClient
			.list(queryOrder);
		model.addAttribute("page", batchResult.getPageList());
		model.addAttribute("type", queryOrder.getType());
		model.addAttribute("view", view);
		setCustomerEnums(model);
		return page;
		
	}
	
	@ResponseBody
	@RequestMapping("delete.htm")
	public JSONObject delete(long id) {
		return toJSONResult(evaluatingBaseSetClient.delete(id), "删除成功", null);
		
	}
	
	@ResponseBody
	@RequestMapping("update.htm")
	public JSONObject update(ListOrder order) {
		
		return toJSONResult(evaluatingBaseSetClient.updateByList(order.getEvaluatingBaseSet()),
			"更新成功", null);
		
	}
	
	/**
	 * 统计已有指标总分
	 * @param typeList 需要统计指标的类型
	 * */
	private void countScore(List<String> typeList, Model model) {
		int score = 0;
		if (ListUtil.isNotEmpty(typeList)) {
			EvaluatingBaseSetQueryOrder queryOrder = new EvaluatingBaseSetQueryOrder();
			queryOrder.setLevel("1");
			for (String s : typeList) {
				queryOrder.setType(s);
				score += evaluatingBaseSetClient.countScoreByType(queryOrder);
			}
		}
		model.addAttribute("otherScore", score);
	}
}
