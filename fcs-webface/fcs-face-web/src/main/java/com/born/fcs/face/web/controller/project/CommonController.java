package com.born.fcs.face.web.controller.project;

import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Maps;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.EnterpriseScaleEnum;
import com.born.fcs.pm.ws.enums.ScaleRuleKpiTypeEnum;
import com.born.fcs.pm.ws.info.common.EnterpriseScaleRuleInfo;
import com.yjf.common.lang.util.ListUtil;

/**
 * 一些同样的方法可以放这里面
 * 
 * @author wuzj
 */
@Controller
@RequestMapping("projectMg/common")
public class CommonController extends BaseController {
	
	/**
	 * 计算企业规模
	 * 
	 * @param industryCode 行业编码
	 * @param inCome 营业收入 万元
	 * @param employeeNum 从业人数
	 * @param totalAsset 资产总额 万元
	 * @param model
	 * @return
	 */
	@RequestMapping("calculateEnterpriseScale.htm")
	@ResponseBody
	public JSONObject calculateEnterpriseScale(String industryCode, Double inCome,
												Integer employeeNum, Double totalAsset, Model model) {
		JSONObject result = new JSONObject();
		if (inCome != null)
			inCome = inCome / 10000;
		if (totalAsset != null)
			totalAsset = totalAsset / 10000;
		try {
			if (StringUtil.isNotEmpty(industryCode)) {
				List<EnterpriseScaleRuleInfo> rules = basicDataCacheService
					.queryEnterpriseScaleRule();
				
				if (ListUtil.isEmpty(rules)) {
					result.put("success", false);
					result.put("message", "无法匹配规则");
				}
				
				// 匹配规则
				Map<String, EnterpriseScaleRuleInfo> matchedRules = Maps.newHashMap();
				Map<String, EnterpriseScaleRuleInfo> defaultRules = Maps.newHashMap();
				for (EnterpriseScaleRuleInfo rule : rules) {
					if ("DEFAULT".equals(rule.getIndustryCode())) {
						defaultRules.put(rule.getKpiType().code(), rule);
					}
					String[] ruleIndustryCode = rule.getIndustryCode().split("\\|");
					for (String code : ruleIndustryCode) {
						if (industryCode.contains(code)) {
							matchedRules.put(rule.getKpiType().code(), rule);
						}
					}
				}
				
				// 无法匹配的时候使用默认规则
				if (matchedRules.isEmpty() && !defaultRules.isEmpty()) {
					matchedRules.putAll(defaultRules);
				}
				
				// 计算企业规模
				if (matchedRules.isEmpty()) {
					result.put("success", false);
					result.put("message", "无法匹配规则");
				} else {
					EnterpriseScaleEnum scale = null;
					ScriptEngineManager sem = new ScriptEngineManager();
					ScriptEngine se = sem.getEngineByName("js");
					for (String key : matchedRules.keySet()) {
						EnterpriseScaleEnum ruleScale = null;
						EnterpriseScaleRuleInfo rule = matchedRules.get(key);
						String hs = rule.getScaleHugeScript();
						String bs = rule.getScaleBigScript();
						String ms = rule.getScaleMediumScript();
						String ss = rule.getScaleSmallScript();
						
						String variable = null;
						if (rule.getKpiType() == ScaleRuleKpiTypeEnum.IN_COME) { // 营业收入
							variable = String.valueOf(inCome == null ? 0 : inCome);
						} else if (rule.getKpiType() == ScaleRuleKpiTypeEnum.EMPLOYEE_NUM) { // 从业人数
							variable = String.valueOf(employeeNum == null ? 0 : employeeNum);
						} else {
							variable = String.valueOf(totalAsset == null ? 0 : totalAsset); // 资产总额
						}
						
						if (StringUtil.isNotBlank(hs))
							hs = hs.replaceAll(rule.getKpiVariable(), variable);
						if (StringUtil.isNotBlank(bs))
							bs = bs.replaceAll(rule.getKpiVariable(), variable);
						if (StringUtil.isNotBlank(ms))
							ms = ms.replaceAll(rule.getKpiVariable(), variable);
						if (StringUtil.isNotBlank(ss))
							ss = ss.replaceAll(rule.getKpiVariable(), variable);
						
						if (StringUtil.isNotBlank(hs) && (Boolean) se.eval(hs)) {
							ruleScale = EnterpriseScaleEnum.HUGE;
						} else if (StringUtil.isNotBlank(bs) && (Boolean) se.eval(bs)) {
							ruleScale = EnterpriseScaleEnum.BIG;
						} else if (StringUtil.isNotBlank(ms) && (Boolean) se.eval(ms)) {
							ruleScale = EnterpriseScaleEnum.MEDIUM;
						} else if (StringUtil.isNotBlank(ss) && (Boolean) se.eval(ss)) {
							ruleScale = EnterpriseScaleEnum.SMALL;
						} else {
							ruleScale = EnterpriseScaleEnum.TINY;
						}
						// 取最小的规模
						if (scale == null || scale.getValue() > ruleScale.getValue()) {
							scale = ruleScale;
						}
					}
					
					if (scale != null) {
						result.put("success", true);
						result.put("message", "计算成功");
						result.put("scale", scale);
					} else {
						result.put("success", false);
						result.put("message", "计算出错");
					}
				}
				
			} else {
				result.put("success", false);
				result.put("message", "行业编码为空,无法匹配规则");
			}
			
		} catch (Exception e) {
			logger.error("计算企业规模出错", e);
		}
		
		return result;
	}
}
