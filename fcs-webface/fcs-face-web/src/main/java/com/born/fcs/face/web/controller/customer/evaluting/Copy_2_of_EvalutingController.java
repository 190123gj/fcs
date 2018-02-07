//package com.born.fcs.face.web.controller.customer.evaluting;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import javax.script.ScriptEngine;
//import javax.script.ScriptEngineManager;
//import javax.script.ScriptException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.apache.commons.beanutils.BeanUtils;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSONObject;
//import com.born.fcs.crm.ws.service.enums.EvaluetingLevelEnum;
//import com.born.fcs.crm.ws.service.enums.StandardTypeEnums;
//import com.born.fcs.crm.ws.service.enums.StandardUseEnums;
//import com.born.fcs.crm.ws.service.info.CompanyCustomerInfo;
//import com.born.fcs.crm.ws.service.info.CustomerBaseInfo;
//import com.born.fcs.crm.ws.service.info.CustomerInfoForEvalue;
//import com.born.fcs.crm.ws.service.info.EvaluatingBaseQueryInfo;
//import com.born.fcs.crm.ws.service.info.EvaluetingListAuditInfo;
//import com.born.fcs.crm.ws.service.info.FinancialSetInfo;
//import com.born.fcs.crm.ws.service.info.ListDataInfo;
//import com.born.fcs.crm.ws.service.order.EvaluetingOrder;
//import com.born.fcs.crm.ws.service.order.ListOrder;
//import com.born.fcs.crm.ws.service.order.query.CustomerQueryOrder;
//import com.born.fcs.crm.ws.service.order.query.EvaluetingListQueryOrder;
//import com.born.fcs.crm.ws.service.order.query.EvaluetingQueryOrder;
//import com.born.fcs.crm.ws.service.order.query.FinancialSetQueryOrder;
//import com.born.fcs.crm.ws.service.result.CityEvalueSetResult;
//import com.born.fcs.crm.ws.service.result.EvalutingResult;
//import com.born.fcs.crm.ws.service.result.FinancialSetResult;
//import com.born.fcs.crm.ws.service.result.PublicCauseSetResult;
//import com.born.fcs.face.integration.session.SessionLocal;
//import com.born.fcs.face.integration.util.ShiroSessionUtils;
//import com.born.fcs.face.web.controller.base.WorkflowBaseController;
//import com.born.fcs.face.web.util.PageUtil;
//import com.born.fcs.face.web.util.WebUtil;
//import com.born.fcs.pm.ws.enums.BooleanEnum;
//import com.born.fcs.pm.ws.enums.FormStatusEnum;
//import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
//import com.born.fcs.pm.ws.info.common.FormInfo;
//import com.born.fcs.pm.ws.info.common.ProjectInfo;
//import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
//import com.born.fcs.pm.ws.result.base.FcsBaseResult;
//import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
//import com.google.common.collect.Lists;
//import com.yjf.common.lang.beans.cglib.BeanCopier;
//import com.yjf.common.lang.util.DateUtil;
//import com.yjf.common.lang.util.ListUtil;
//import com.yjf.common.lang.util.StringUtil;
//
///**
// * 评价客户
// * */
//@Controller
//@RequestMapping("/customerMg/evaluting/")
//public class Copy_2_of_EvalutingController extends WorkflowBaseController {
//	protected static ScriptEngine calculatingStr = new ScriptEngineManager()
//		.getEngineByName("JavaScript");
//	private static String VM_PATH = "/customerMg/grade/";
//	
//	/** 评级列表查询 */
//	@RequestMapping("list.htm")
//	public String list(EvaluetingListQueryOrder queryOrder, Model model) {
//		setSessionLocalInfo2Order(queryOrder);
//		//		queryOrder.setLoginUserId(ShiroSessionUtils.getSessionLocal().getUserId());
//		QueryBaseBatchResult<EvaluetingListAuditInfo> batchResult = evaluetingClient
//			.list(queryOrder);
//		setCustomerEnums(model);
//		model.addAttribute("queryOrder", queryOrder);
//		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
//		return VM_PATH + "list.vm";
//	}
//	
//	/** 查询客户项目 **/
//	@ResponseBody
//	@RequestMapping("queryProject.json")
//	public JSONObject queryProject(ProjectQueryOrder order, long userId) {
//		JSONObject josn = new JSONObject();
//		order.setCustomerId(userId);
//		List<ProjectPhasesEnum> phasesList = Lists.newArrayList();
//		phasesList.add(ProjectPhasesEnum.AFTERWARDS_PHASES);
//		phasesList.add(ProjectPhasesEnum.CONTRACT_PHASES);
//		phasesList.add(ProjectPhasesEnum.COUNCIL_PHASES);
//		phasesList.add(ProjectPhasesEnum.FINISH_PHASES);
//		phasesList.add(ProjectPhasesEnum.FUND_RAISING_PHASES);
//		//		phasesList.add(ProjectPhasesEnum.INVESTIGATING_PHASES);
//		phasesList.add(ProjectPhasesEnum.LOAN_USE_PHASES);
//		phasesList.add(ProjectPhasesEnum.RE_COUNCIL_PHASES);
//		phasesList.add(ProjectPhasesEnum.RECOVERY_PHASES);
//		order.setPhasesList(phasesList);
//		QueryBaseBatchResult<ProjectInfo> result = personalCustomerClient.queryProject(order);
//		if (result.isSuccess()) {
//			josn.put("success", true);
//			josn.put("message", "查询成功");
//			josn.put("list", result.getPageList());
//			josn.put("result", result);
//		} else {
//			josn.put("success", false);
//			josn.put("success", StringUtil.defaultIfBlank(result.getMessage(), "查询失败"));
//		}
//		return josn;
//		
//	}
//	
//	/** 可评级客户查询 */
//	@ResponseBody
//	@RequestMapping("selectCustomer.json")
//	public JSONObject list(CustomerQueryOrder queryOrder) {
//		JSONObject json = new JSONObject();
//		queryOrder.setStatus("on");
//		queryOrder.setEvalueStatus(BooleanEnum.NO.code());
//		queryOrder.setCustomerManager(ShiroSessionUtils.getSessionLocal().getRealName());
//		QueryBaseBatchResult<CustomerBaseInfo> batchResult = companyCustomerClient.list(queryOrder);
//		json.put("queryOrder", queryOrder);
//		if (batchResult.isSuccess()) {
//			json.put("success", true);
//			json.put("message", "查询成功！");
//			json.put("page", PageUtil.getCovertPage(batchResult));
//		} else {
//			json.put("success", false);
//			json.put("message", "查询失败！");
//		}
//		
//		return json;
//	}
//	
//	/**
//	 * 评级查看
//	 * */
//	@RequestMapping("view.htm")
//	public String view(HttpSession session, String showType, String evaluetingType, long formId,
//						String step, Model model) {
//		
//		FormInfo formInfo = formServiceCrmClient.findByFormId(formId);
//		model.addAttribute("form", formInfo);
//		model.addAttribute("formId", formInfo.getFormId());
//		setCustomerEnums(model);
//		CompanyCustomerInfo info = null;
//		
//		EvaluetingListAuditInfo evaluetingListAuditInfo = evaluetingClient.queryByFormId(formId);
//		model.addAttribute("form", formInfo);
//		if (evaluetingListAuditInfo != null) {
//			info = companyCustomerClient.queryById(evaluetingListAuditInfo.getCustomerId());
//			if (evaluetingListAuditInfo.getStatus() == FormStatusEnum.APPROVAL.getCode()) {
//				//评级通过，查看审核数据
//				step = "1";
//			}
//			
//		}
//		
//		if (info != null) {
//			CustomerInfoForEvalue eInfo = info.getEvalueInfo();
//			if (eInfo != null) {
//				BeanCopier.staticCopy(eInfo, info);
//			}
//			StandardUseEnums standardUse = StandardUseEnums.getByCode(info.getIndustryCode()
//				.substring(0, 1));
//			
//			queryUser(session, info, showType, true, StringUtil.defaultIfBlank(step, "0"),
//				standardUse, evaluetingListAuditInfo.getYear(), model);
//			yearEvalue(null, info.getCustomerId(), standardUse, null, model);
//		} else {
//			model.addAttribute("message", "未查到该用户！");
//		}
//		
//		model.addAttribute("isView", true);
//		model.addAttribute("showType", StringUtil.defaultIfBlank(showType, "KHGK"));
//		model.addAttribute("evaluetingType", evaluetingType);
//		
//		return VM_PATH + "viewGradeIndex.vm";
//		
//	}
//	
//	/**
//	 * 编辑
//	 * */
//	@RequestMapping("edit.htm")
//	public String edit(HttpServletRequest request, HttpSession session, Long formId,
//						String yearEvalueId, String customerName, String customerId, Long userId,
//						String evaluetingType, String showType, Boolean isView, String step,
//						Model model) {
//		model.addAttribute("audit", true);
//		FormInfo formInfo = formServiceCrmClient.findByFormId(formId);
//		try {
//			initWorkflow(model, formInfo, request.getParameter("taskId"));
//			step = "1";
//		} catch (Exception e) {
//			model.addAttribute("customerManagerAdit", true);
//			step = "0";
//		}
//		
//		model.addAttribute("formId", formInfo.getFormId());
//		return add(request, session, formId, yearEvalueId, customerName, customerId, userId,
//			evaluetingType, StringUtil.defaultIfBlank(showType, "KHGK"), isView, step, null, model);
//	}
//	
//	/**
//	 * 审核
//	 * */
//	@RequestMapping("audit.htm")
//	public String audit(HttpServletRequest request, HttpSession session, long formId, Model model) {
//		model.addAttribute("audit", true);
//		FormInfo formInfo = formServiceCrmClient.findByFormId(formId);
//		model.addAttribute("form", formInfo);
//		model.addAttribute("formId", formInfo.getFormId());
//		initWorkflow(model, formInfo, request.getParameter("taskId"));
//		
//		return add(request, session, formId, null, null, null, null, null, "KHGK", false, "1",
//			null, model);
//	}
//	
//	/**
//	 * 新增评价
//	 * @param customerName 企业客户名
//	 * @param showType 页面展示选项卡
//	 * */
//	@RequestMapping("add.htm")
//	public String add(HttpServletRequest request, Long formId, String customerName, Long userId,
//						String evaluetingType, String customerId, String showType, Boolean isView,
//						String projectCode, Model model) {
//		setCustomerEnums(model);
//		
//		CompanyCustomerInfo customerIfo = queryUserInfo(userId, customerId, formId, model);
//		
//		StandardUseEnums standardUse = StandardUseEnums.getByCode(customerIfo.getIndustryCode()
//			.substring(0, 1));
//		
//		String types = standardUse.getType();
//		if (StringUtil.equals(showType, "ZBPF")) {
//			//一般指标评分
//			queryYBZBSet(types, userId, model);
//			if (formId != null && formId > 0) {
//				if (StringUtil.equals(types, "YB")) {
//					
//					evalueResultQuery(formId, StandardTypeEnums.YBQY.getCode(), "0", isView, model);
//				} else if (StringUtil.equals(types, "GY")) {
//					//公用事业类无标准
//					evalueResultQuery(formId, "GY", "0", isView, model);
//					
//				}
//				
//			}
//		} else if (StringUtil.equals(showType, "CWZB")) {
//			//财务指标评分
//			model.addAttribute("financeType", standardUse.getFinanceType());
//			model.addAttribute("financeName",
//				StandardTypeEnums.getMsgByCode(standardUse.getFinanceType()));
//			queryFinaceDataFormPm(queryFinaceSet(standardUse.getFinanceType(), model),
//				customerIfo.getUserId(), model);
//		} else if (StringUtil.equals(showType, "ZBXY") && formId != null) {
//			//资本信用指标结果
//			evalueResultQuery(formId, "ZBXY", "0", isView, model);
//		} else if (StringUtil.equals(showType, "TZSX") && formId != null) {
//			//调整事项结果
//			evalueResultQuery(formId, "TZSX", "0", isView, model);
//		} else if (StringUtil.equals(showType, "JFZB")) {
//			//计分总表
//			if (StringUtil.equals(standardUse.getType(), "CT")) {
//				queryYBZBSet(types, userId, model);
//				if (formId != null && formId > 0) {
//					evalueResultQuery(formId, "CT", "0", isView, model);
//				}
//			}
//			if (formId != null && formId > 0) {
//				countevalue(formId, "0", model);
//			}
//			
//		}
//		
//		model.addAttribute("showType", showType);
//		model.addAttribute("evaluetingType", evaluetingType);
//		
//		return VM_PATH + "gradeIndex.vm";
//		
//	}
//	
//	/**
//	 * 新增评价提交
//	 * @param zbType 指标类型
//	 * 
//	 * */
//	@ResponseBody
//	@RequestMapping("update.htm")
//	public Object update(HttpServletRequest request, String isTemporary, ListOrder listOrder) {
//		JSONObject json = new JSONObject();
//		SessionLocal local = ShiroSessionUtils.getSessionLocal();
//		EvaluetingOrder order = new EvaluetingOrder();
//		WebUtil.setPoPropertyByRequest(order, request);
//		setSessionLocalInfo2Order(order);
//		order.setEvaluetingInfo(listOrder.getEvaluetingInfo());
//		order.setOperator(local.getRealName());
//		FcsBaseResult result = evaluetingClient.baseEvalueting(order);
//		json.put("success", result.isSuccess());
//		json.put("message", result.getMessage());
//		json.put("formId", result.getKeyId());
//		return json;
//	}
//	
//	/**
//	 * 审核编辑提交评价提交
//	 * @param zbType 指标类型
//	 * 
//	 * */
//	@ResponseBody
//	@RequestMapping("auditUpdate.htm")
//	public Object auditUpdate(HttpServletRequest request, String isTemporary, ListOrder listOrder) {
//		JSONObject json = new JSONObject();
//		//		SessionLocal local = ShiroSessionUtils.getSessionLocal();
//		EvaluetingOrder order = new EvaluetingOrder();
//		setSessionLocalInfo2Order(order);
//		WebUtil.setPoPropertyByRequest(order, request);
//		order.setEvaluetingInfo(listOrder.getEvaluetingInfo());
//		//		order.setOperator(local.getRealName());
//		
//		FcsBaseResult result = evaluetingClient.baseEvalueting(order);
//		json.put("success", result.isSuccess());
//		json.put("message", result.getMessage());
//		json.put("formId", result.getKeyId());
//		return json;
//	}
//	
//	/** 查询客户基本信息 */
//	private CompanyCustomerInfo queryUserInfo(Long userId, String customerId, Long formId,
//												Model model) {
//		CompanyCustomerInfo info = null;
//		if (formId != null) {
//			EvaluetingListAuditInfo evaluetingListAuditInfo = evaluetingClient
//				.queryByFormId(formId);
//			if (evaluetingListAuditInfo != null) {
//				customerId = evaluetingListAuditInfo.getCustomerId();
//				model.addAttribute("formId", formId);
//			}
//		}
//		if (userId != null) {
//			info = companyCustomerClient.queryByUserId(userId, formId);
//			
//		} else if (StringUtil.isNotBlank(customerId)) {
//			info = companyCustomerClient.queryById(customerId, null);
//		}
//		if (info != null) {
//			model.addAttribute("info", info);
//			model.addAttribute("customerName", info.getCustomerName());
//			model.addAttribute("customerId", info.getCustomerId());
//		}
//		return info;
//	}
//	
//	/** 财务指标部分查询 **/
//	private FinancialSetResult queryFinaceSet(String type, Model model) {
//		FinancialSetQueryOrder queryOrder = new FinancialSetQueryOrder();
//		queryOrder.setType(type);
//		FinancialSetResult result = evaluatingFinancialSetClient.list(queryOrder);
//		if (result != null && result.getMapResult() != null) {
//			model.addAttribute("mapInfo", result.getMapResult());
//			model.addAttribute("listData1", result.getListData1());
//			model.addAttribute("listData2", result.getListData2());
//			return result;
//		}
//		return null;
//	}
//	
//	/** 财务指标取pm数据 */
//	private void queryFinaceDataFormPm(FinancialSetResult result, long userId, Model model) {
//		if (result.isSuccess() && !result.getMapResult().isEmpty() && userId > 0) {
//			//评价时读取pm中财务数据
//			EvalutingResult fianceResult = evaluetingClient.financeInfoFromPro(userId);//获取财务数据
//			if (fianceResult != null && fianceResult.isSuccess()
//				&& fianceResult.getCustomerFinanInfo() != null) {
//				try {
//					Map<String, FinancialSetInfo> mapInfo = result.getMapResult();
//					Map<String, String> map = BeanUtils.describe(fianceResult
//						.getCustomerFinanInfo());
//					Set<String> mapKey = mapInfo.keySet();
//					for (String s : mapKey) {
//						if (map.containsKey(s)) {
//							String score = "";
//							FinancialSetInfo info = mapInfo.get(s);
//							String actualValue = checkValue(map.get(s), 2);
//							if (StringUtil.isBlank(actualValue)) {
//								continue;
//							}
//							String calculatingFormula = info.getCalculatingFormula();
//							if (StringUtil.equals(s, "xjllqk")) {//现金流量情况
//								calculatingFormula = calculatingFormula.replaceAll("jxjll",
//									actualValue);//替换净现金流量
//								model.addAttribute("jxjll", actualValue);
//							} else if (StringUtil.equals(s, "nzyywsr")) {//年主营业务收入规模及变动趋势
//								String score1 = cal1(map.get("data1"), info);//年主营业务收入
//								String score2 = cal2(actualValue, info);//近3年最小增长幅度
//								String score3 = cal3(map.get("nzyywsr_avg"), result.getListData1());//近3年平均增长幅度
//								String thisScore = score1 + "+" + score2 + "+" + score3;
//								score = String.valueOf(calculatingStr.eval(thisScore));
//							} else if (StringUtil.equals(s, "nlrzegm")) {//年利润总额规模及变动趋势
//								String score1 = cal1(map.get("data14"), info);//年利润总额
//								String score2 = cal2(actualValue, info);
//								String score3 = cal3(map.get("nlrzegm_avg"), result.getListData2());
//								String thisScore = score1 + "+" + score2 + "+" + score3;
//								score = String.valueOf(calculatingStr.eval(thisScore));
//							} else if (StringUtil.equals(s, "yhlxbs")) {//已或利息倍数
//								//手动输入实际值
//							} else {//可直接计算的
//								calculatingFormula = calculatingFormula.replaceAll("actualValue",
//									actualValue).replaceAll("--", "+");
//								double claScore = (double) calculatingStr.eval(calculatingFormula);
//								if (claScore < 0) {
//									claScore = 0;
//								} else if (claScore > Double.parseDouble(info.getString3())) {
//									claScore = Double.parseDouble(info.getString3());
//								}
//								score = String.valueOf(claScore);
//							}
//							info.setCalculatingFormula(calculatingFormula);
//							info.setActualValue(actualValue);
//							info.setScore(checkValue(score, 1));
//							mapInfo.put(s, info);
//						}
//					}
//					model.addAttribute("mapInfo", mapInfo);
//					model.addAttribute("listData1", result.getListData1());
//					model.addAttribute("listData2", result.getListData2());
//				} catch (IllegalAccessException e) {
//					logger.error(e.getMessage(), e);
//				} catch (InvocationTargetException e) {
//					logger.error(e.getMessage(), e);
//				} catch (NoSuchMethodException e) {
//					logger.error(e.getMessage(), e);
//				} catch (ScriptException e) {
//					logger.error(e.getMessage(), e);
//				} catch (Exception e) {
//					logger.error(e.getMessage(), e);
//				}
//			}
//			
//		}
//	}
//	
//	/** 当前指标评价结果 */
//	private void evalueResultQuery(long formId, String evalueType, String step, Boolean isView,
//									Model model) {
//		EvaluetingQueryOrder order = new EvaluetingQueryOrder();
//		order.setEvalueType(evalueType);
//		order.setStep(step);
//		order.setFormId(formId);
//		if (isView != null && isView) {
//			queryEvalueResult(order, "evalutingMap", model);
//		} else {
//			if (StringUtil.equals(step, "1")) {
//				queryEvalueResult(order, "evalutingMap1", model);
//				
//				order.setStep("0");
//				queryEvalueResult(order, "evalutingMap", model);
//				
//			} else {
//				order.setStep("0");
//				queryEvalueResult(order, "evalutingMap", model);
//			}
//		}
//		
//	}
//	
//	/** 评价结果查询 */
//	private void queryEvalueResult(EvaluetingQueryOrder order, String mapKey, Model model) {
//		EvalutingResult list1 = evaluetingClient.queryEvalueResult(order);
//		model.addAttribute(StringUtil.defaultIfBlank(mapKey, "evalutingMap"),
//			list1.getEvalutingMap());
//	}
//	
//	/** 一般指标配置查询 **/
//	private void queryYBZBSet(String types, long userId, Model model) {
//		
//		//一般指标评分
//		if (StringUtil.equals(types, "YB")) {
//			List<EvaluatingBaseQueryInfo> list = evaluatingBaseSetClient.levelConcat(
//				StandardTypeEnums.YBQY.getCode(), "4");
//			model.addAttribute("list", list);
//		} else if (StringUtil.equals(types, "GY")) {
//			//公用事业类无标准
//			PublicCauseSetResult gysy = evaluatingBaseSetClient.publicCauseSet(userId);
//			if (gysy != null && gysy.isSuccess()) {
//				model.addAttribute("list", gysy);
//			}
//			
//		} else if (StringUtil.equals(types, "CT")) {
//			
//			CityEvalueSetResult ctResulty = evaluatingBaseSetClient.queryCityEvalueSet(userId);
//			if (ctResulty.isSuccess()) {
//				model.addAttribute("ctzg", ctResulty.getSubjectivity());
//				model.addAttribute("ctbz", ctResulty.getStandardVal());
//				model.addAttribute("ctcw", ctResulty.getTerraceFinance());
//			}
//			
//		}
//		
//	}
//	
//	/** 计分总表 */
//	private void countevalue(long formId, String step, Model model) {
//		EvaluetingQueryOrder order = new EvaluetingQueryOrder();
//		order.setFormId(formId);
//		order.setStep(step);
//		if (StringUtil.equals(step, "1")) {
//			EvalutingResult result = evaluetingClient.evalueCount(order);
//			if (result.isSuccess()) {
//				model.addAttribute("evalueCount1", result.getEvalueCount());
//				model.addAttribute("level1",
//					EvaluetingLevelEnum.getLevel((double) result.getEvalueCount().get("total")));
//			}
//			order.setStep("0");
//			EvalutingResult result0 = evaluetingClient.evalueCount(order);
//			if (result.isSuccess()) {
//				model.addAttribute("evalueCount", result0.getEvalueCount());
//				model.addAttribute("level",
//					EvaluetingLevelEnum.getLevel((double) result0.getEvalueCount().get("total")));
//			}
//		} else {
//			EvalutingResult result = evaluetingClient.evalueCount(order);
//			if (result.isSuccess()) {
//				model.addAttribute("evalueCount", result.getEvalueCount());
//				String level = EvaluetingLevelEnum.getLevel((double) result.getEvalueCount().get(
//					"total"));
//				model.addAttribute("level", level);
//			}
//		}
//		
//	}
//	
//	@SuppressWarnings("unchecked")
//	@ResponseBody
//	@RequestMapping("calculating.json")
//	public JSONObject calculating(HttpSession session, String calculatingFormula, String actualValue) {
//		
//		JSONObject json = new JSONObject();
//		String thisScore = "0";
//		try {
//			//获取评价规则
//			Map<String, FinancialSetInfo> cw_mapInfo = (Map<String, FinancialSetInfo>) session
//				.getAttribute("cw_mapInfo");
//			if (StringUtil.equals(calculatingFormula, "ts_1")) {
//				// 年主营业务收入规模及变动趋势
//				FinancialSetInfo financialSetInfo = cw_mapInfo.get("nzyywsr");
//				List<ListDataInfo> cw_listData1 = (List<ListDataInfo>) session
//					.getAttribute("cw_listData1");
//				String score1 = cal1(actualValue, financialSetInfo);
//				String score2 = cal2(actualValue, financialSetInfo);
//				String score3 = cal3(actualValue, cw_listData1);
//				String score = score1 + "+" + score2 + "+" + score3;
//				thisScore = String.valueOf(calculatingStr.eval(score));
//			} else if (StringUtil.equals(calculatingFormula, "ts_2")) {
//				// 年利润总额规模及变动趋势
//				FinancialSetInfo financialSetInfo = cw_mapInfo.get("nlrzegm");
//				List<ListDataInfo> cw_listData2 = (List<ListDataInfo>) session
//					.getAttribute("cw_listData2");
//				String score1 = cal1(actualValue, financialSetInfo);
//				String score2 = cal2(actualValue, financialSetInfo);
//				String score3 = cal3(actualValue, cw_listData2);
//				String score = score1 + "+" + score2 + "+" + score3;
//				thisScore = String.valueOf(calculatingStr.eval(score));
//				
//			} else {
//				//通用计算公式
//				if (calculatingFormula.indexOf(";") > -1)
//					//多个公式
//					thisScore = getMaxScore(calculatingFormula.replaceAll("actualValue",
//						actualValue));
//				else {
//					//单个公式
//					String cal = calculatingFormula.replaceAll("actualValue", actualValue);
//					thisScore = String.valueOf(calculatingStr.eval(cal)).replaceAll("-999", "");
//				}
//			}
//			json.put("score", checkValue(thisScore, 1));
//			json.put("success", true);
//			json.put("message", "计算成功");
//		} catch (Exception e) {
//			logger.error("计算财务评分异常", e);
//			json.put("success", false);
//			json.put("message", "计算财务评分失败");
//		}
//		
//		return json;
//		
//	}
//	
//	/**
//	 * 特殊财务指标计算 :年主营业务收入/年利润总额
//	 * @actualValue 实际值
//	 * */
//	private String cal1(String actualValue, FinancialSetInfo financialSetInfo) {
//		String calRes = "";
//		try {
//			actualValue = String.valueOf(calculatingStr.eval(actualValue + "/100000000"));
//			String cal = actualValue + financialSetInfo.getString7()
//							+ financialSetInfo.getString8() + "?" + financialSetInfo.getString9()
//							+ financialSetInfo.getString10() + ":000";
//			calRes = String.valueOf(calculatingStr.eval(cal)); //年主营业务收入得分
//			if (StringUtil.equals(calRes, "0.0")) {
//				double dis = 0;
//				if (StringUtil.equals(financialSetInfo.getString11(), "up")) {//如果比标准值上涨
//					dis = Double.parseDouble(actualValue)
//							- Double.parseDouble(financialSetInfo.getString8());//计算上涨量
//				} else {
//					dis = Double.parseDouble(financialSetInfo.getString8())
//							- Double.parseDouble(actualValue);//计算下降量
//				}
//				double count = dis / Double.parseDouble(financialSetInfo.getString12()); // 上涨量是衡量值的几倍		
//				if (count > 0) {//若果上涨倍数为正
//					double a = count * Double.parseDouble(financialSetInfo.getString14()); //总修正分值=上涨倍数*没上涨一倍分值
//					cal = financialSetInfo.getString10() + financialSetInfo.getString13()
//							+ String.valueOf(a); //修正分值计算：总分±总修正分值
//					double calRes1 = (double) calculatingStr.eval(cal);
//					String min = financialSetInfo.getString15();
//					if (calRes1 < Integer.parseInt(min)) {//判断最后得分是否小于最低分
//						calRes1 = Integer.parseInt(min);//最后得分不得小于最低分
//					}
//					calRes = String.valueOf(calRes1);
//				}
//				
//			}
//		} catch (Exception e) {
//			logger.error("计算特殊财务评分1异常", e);
//		}
//		return calRes;
//		
//	}
//	
//	/**
//	 * 特殊指标计算2 ：同时最近三个会计年度连续增长幅度
//	 * @param actualValue 最近三个会计年度连续增长幅度最小值
//	 * 
//	 * */
//	private String cal2(String actualValue, FinancialSetInfo financialSetInfo) {
//		String score = "";
//		try {
//			String cal = actualValue + financialSetInfo.getString16()
//							+ financialSetInfo.getString17() + "?" + financialSetInfo.getString18()
//							+ ":0";
//			score = String.valueOf(calculatingStr.eval(cal));
//		} catch (Exception e) {
//			logger.error("计算特殊财务评分2异常", e);
//		}
//		return score;
//		
//	}
//	
//	/**
//	 * 最近三个会计年度平均增长减少幅度
//	 * @param actualValue 增长减少幅度
//	 * @param upOrDown 计算上升/下降幅度 up/down
//	 * */
//	private String cal3(String actualValue, List<ListDataInfo> listData) {
//		String score = "";
//		try {
//			double sco = 0;//上升加分 与0比较，取最大的
//			if (StringUtil.isBlank(actualValue)) {
//				return "0";
//			}
//			String upOrDown = "up";
//			if ((boolean) calculatingStr.eval(actualValue + "<0")) {
//				upOrDown = "down";
//				actualValue = actualValue.replace("-", "");
//				sco = 100;//下降与100比较，取最小的值，
//			}
//			
//			if (ListUtil.isNotEmpty(listData)) {
//				for (ListDataInfo info : listData) {
//					if (StringUtil.equals(upOrDown, info.getStr1())) {
//						String cal = actualValue + info.getStr2() + info.getDouble1() + "?"
//										+ info.getStr3() + info.getInteger1() + ":0";
//						double s = (double) calculatingStr.eval(cal);
//						if (StringUtil.equals("up", upOrDown)) {
//							//上升取大值
//							if (s > sco)
//								sco = s;
//							
//						} else {
//							//下降取小值
//							if (s < sco)
//								sco = s;
//							
//						}
//						
//					}
//				}
//				if (sco == 100)//是否还是初始值
//					sco = 0;
//				score = String.valueOf(sco);
//				
//			}
//		} catch (Exception e) {
//			logger.error("计算特殊财务评分3异常", e);
//		}
//		
//		return score;
//		
//	}
//	
//	/** 以分号隔开的多个计算公式，取最大值 */
//	private String getMaxScore(String calculatingFormula) {
//		String[] calculatingFormulas = calculatingFormula.split(";");
//		double score = -100;
//		for (String s : calculatingFormulas) {
//			try {
//				double d = (double) calculatingStr.eval(s);
//				if (d > score) {
//					score = d;
//				}
//			} catch (ScriptException e) {
//				logger.error(e.getMessage(), e);
//			}
//		}
//		if (score == -100) {
//			return "0";
//		}
//		return String.valueOf(score);
//		
//	}
//	
//	//四舍五入  num:保留几位小数
//	public String checkValue(String s, int num) {
//		try {
//			if (StringUtil.isBlank(s))
//				return s;
//			
//			//			if (s.indexOf("E") > -1) {
//			//				s = String.valueOf(calculatingStr.eval(s + "/100000000"));
//			//			}
//			if (num == 2) {
//				double d = Double.parseDouble(s);
//				double rs = Math.round(d * 100) / 100.0;
//				s = String.valueOf(rs);
//			} else {
//				double d = Double.parseDouble(s);
//				double rs = Math.round(d * 10) / 10.0;
//				s = String.valueOf(rs);
//			}
//			
//		} catch (Exception e) {
//			logger.error("四舍五入失败：" + s);
//		}
//		
//		return s;
//		
//	}
//	
//	/** 获取年 */
//	protected static String getYear() {
//		Date date = new Date();
//		
//		return DateUtil.simpleFormat(date).substring(0, 4);
//		
//	}
//}
