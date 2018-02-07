package com.born.fcs.face.web.controller.asset.assesscompany;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.am.ws.enums.AssetStatusEnum;
import com.born.fcs.am.ws.info.assesscompany.AssessCompanyEvaluateInfo;
import com.born.fcs.am.ws.info.assesscompany.AssetsAssessCompanyInfo;
import com.born.fcs.am.ws.info.assesscompany.FAssessCompanyApplyInfo;
import com.born.fcs.am.ws.order.assesscompany.AssessCompanyEvaluateOrder;
import com.born.fcs.am.ws.order.assesscompany.AssessCompanyManyEvaluateOrder;
import com.born.fcs.am.ws.order.assesscompany.FAssessCompanyApplyOrder;
import com.born.fcs.am.ws.order.assesscompany.FAssessCompanyApplyQueryOrder;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Controller
@RequestMapping("assetMg/assessCompanyApply")
public class AssessCompanyApplyController extends WorkflowBaseController {
	
	final static String vm_path = "/assetMg/assessmentCompany/";
	
	/**
	 * 评估公司申请单列表
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(FAssessCompanyApplyQueryOrder queryOrder, Model model) {
		try {
			if (queryOrder == null)
				queryOrder = new FAssessCompanyApplyQueryOrder();
			
			setSessionLocalInfo2Order(queryOrder);
			model.addAttribute("isBusiManager", isBusiManager() || isLegalManager());//是否客户经理
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (StringUtil.isEmpty(queryOrder.getSortCol())) {
				queryOrder.setSortCol("form_add_time");
				queryOrder.setSortOrder("DESC");
			}
			QueryBaseBatchResult<FAssessCompanyApplyInfo> batchResult = assessCompanyApplyServiceClient
				.query(queryOrder);
			List<FAssessCompanyApplyInfo> listInfo = batchResult.getPageList();
			for (FAssessCompanyApplyInfo info : listInfo) {
				ProjectRelatedUserInfo userInfo = projectRelatedUserServiceClient
					.getRiskManager(info.getProjectCode());
				ProjectRelatedUserInfo userInfo1 = projectRelatedUserServiceClient
					.getBusiManager(info.getProjectCode());
				if (userInfo != null) {
					info.setRiskManagerId(userInfo.getUserId());
					info.setRiskManagerName(userInfo.getUserName());
				}
				if (userInfo1 != null) {
					info.setBusiManagerId(userInfo1.getUserId());
					info.setBusiManagerName(userInfo1.getUserName());
				}
				//是否上会前
				ProjectSimpleDetailInfo projectInfo = projectServiceClient
					.querySimpleDetailInfo(info.getProjectCode());
				if (projectInfo != null
					&& (projectInfo.getPhases() == ProjectPhasesEnum.INVESTIGATING_PHASES || projectInfo
						.getPhases() == ProjectPhasesEnum.SET_UP_PHASES)) {
					info.setIsBeforeMeet("true");
				} else {
					info.setIsBeforeMeet("false");
				}
				//是否已评价
				Boolean isEvaluate = false;
				if (sessionLocal != null) {
					List<AssessCompanyEvaluateInfo> listEvaluateInfo = assessCompanyApplyServiceClient
						.findEvaluateByApplyId(info.getId());
					if (listEvaluateInfo != null) {
						for (AssessCompanyEvaluateInfo assessCompanyEvaluateInfo : listEvaluateInfo) {
							if (sessionLocal.getUserId() == assessCompanyEvaluateInfo
								.getEvaluatePerson()) {
								isEvaluate = true;
								break;
							}
						}
					}
					if (listEvaluateInfo == null || listEvaluateInfo.size() == 0) {
						info.setIsExistEvaluate("NO");
					} else {
						info.setIsExistEvaluate("IS");
					}
					if (isEvaluate) {
						info.setIsEvaluate("IS");//当前用户已评价
					} else {
						info.setIsEvaluate("NO");
					}
				}
			}
			model.addAttribute("conditions", queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("assetStatus", AssetStatusEnum.getAllEnum());
		} catch (Exception e) {
			logger.error("查询评估公司申请单列表出错");
		}
		
		return vm_path + "list.vm";
	}
	
	/**
	 * 查看评估公司申请单
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("view.htm")
	public String view(long formId, Model model) {
		FormInfo form = formServiceAmClient.findByFormId(formId);
		
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		FAssessCompanyApplyInfo assessCompanyApplyInfo = assessCompanyApplyServiceClient
			.findByFormId(formId);
		resetCityName(assessCompanyApplyInfo);
		ProjectSimpleDetailInfo projectInfo = projectServiceClient
			.querySimpleDetailInfo(assessCompanyApplyInfo.getProjectCode());
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("assessCompanyApplyInfo", assessCompanyApplyInfo);
		//本次指派的评估公司信息
		List<AssetsAssessCompanyInfo> listCompanyInfo = Lists.newArrayList();
		if (StringUtil.isNotEmpty(assessCompanyApplyInfo.getCompanyId())) {
			String[] companyId = assessCompanyApplyInfo.getCompanyId().split(",");
			for (String id : companyId) {
				listCompanyInfo.add(assessCompanyApplyServiceClient.findByApplyIdAndCompany(
					assessCompanyApplyInfo.getId(), Long.parseLong(id)));
				//				listCompanyInfo.add(assetsAssessCompanyServiceClient.findById(Long.parseLong(id)));
			}
		}
		model.addAttribute("listCompanyInfo", listCompanyInfo);
		model.addAttribute("form", form);
		model.addAttribute("isView", "true");//是否详情
		return vm_path + "view.vm";
	}
	
	/**
	 * 编辑评估公司申请单
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("edit.htm")
	public String edit(long formId, Model model) {
		FormInfo form = formServiceAmClient.findByFormId(formId);
		
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		FAssessCompanyApplyInfo assessCompanyApplyInfo = assessCompanyApplyServiceClient
			.findByFormId(formId);
		resetCityName(assessCompanyApplyInfo);
		ProjectSimpleDetailInfo projectInfo = projectServiceClient
			.querySimpleDetailInfo(assessCompanyApplyInfo.getProjectCode());
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("assessCompanyApplyInfo", assessCompanyApplyInfo);
		model.addAttribute("form", form);
		model.addAttribute("isEdit", "true");//是否编辑
		
		return vm_path + "Add.vm";
	}
	
	/**
	 * 新增评估公司申请单
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("add.htm")
	public String add(String projectCode, Model model) {
		ProjectSimpleDetailInfo projectInfo = projectServiceClient
			.querySimpleDetailInfo(projectCode);
		
		model.addAttribute("projectInfo", projectInfo);
		
		return vm_path + "Add.vm";
	}
	
	/**
	 * 去新增评估公司申请单
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("toAdd.htm")
	public String toAdd(Model model) {
		
		return vm_path + "Add.vm";
	}
	
	/**
	 * 保存
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("save.htm")
	@ResponseBody
	public JSONObject save(HttpServletRequest request, HttpServletResponse response,
							FAssessCompanyApplyOrder order, String isChange, Model model) {
		String tipPrefix = "保存评估公司申请单";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			// 初始化Form验证信息
			FormBaseResult result = null;
			if (!"true".equals(isChange)) {//判断是否是更换
				order.setCheckIndex(0);
				order.setRelatedProjectCode(order.getProjectCode());
				order.setFormId(order.getFormId());
				order.setFormCode(FormCodeEnum.ASSETS_COMPANY_APPLY);
			} else {
				order.setCheckIndex(0);
				order.setRelatedProjectCode(order.getProjectCode());
				order.setFormId(order.getFormId());
				order.setFormCode(FormCodeEnum.ASSETS_COMPANY_CHANGE);
			}
			order.setUserId(sessionLocal.getUserId());
			order.setUserName(sessionLocal.getRealName());
			order.setUserAccount(sessionLocal.getUserName());
			//			setSessionLocalInfo2Order(order);
			
			String cityCode = order.getCityCode();
			String cityName = order.getCityName();
			if ("市辖区".equals(order.getCityName()) || "县".equals(order.getCityName())
				|| "请选择".equals(order.getCityName())) {
				order.setCityCode(order.getProvinceCode());
				order.setCityName(order.getProvinceName());
				order.setProvinceCode(cityCode);
				order.setProvinceName(cityName);
			}
			
			result = assessCompanyApplyServiceClient.save(order);
			jsonObject = toJSONResult(jsonObject, result, "提交表单成功", null);
			if (result.getFormInfo() != null) {
				jsonObject.put("formId", result.getFormInfo().getFormId());
			}
			
			if (result.isSuccess() && order.getCheckStatus() == 1) {
				jsonObject.put("success", true);
				jsonObject.put("status", "SUBMIT");
				jsonObject.put("message", "提交成功");
			} else if (result.isSuccess() && order.getCheckStatus() == 0) {
				jsonObject.put("success", true);
				jsonObject.put("status", "hold");
				jsonObject.put("message", "保存成功");
			} else {
				jsonObject.put("success", false);
				jsonObject.put("message", result.getMessage());
			}
		} catch (Exception e) {
			jsonObject = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 评估公司指定
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("point.htm")
	public String point(long formId, Model model) {
		FormInfo form = formServiceAmClient.findByFormId(formId);
		
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		FAssessCompanyApplyInfo assessCompanyApplyInfo = assessCompanyApplyServiceClient
			.findByFormId(formId);
		resetCityName(assessCompanyApplyInfo);
		model.addAttribute("assessCompanyApplyInfo", assessCompanyApplyInfo);
		ProjectSimpleDetailInfo projectInfo = projectServiceClient
			.querySimpleDetailInfo(assessCompanyApplyInfo.getProjectCode());
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("isEdit", "true");//是否编辑
		return vm_path + "companyAppoint.vm";
	}
	
	/**
	 * 评估公司抽取-系统自动抽取
	 *
	 * @param companyName
	 * @param model
	 * @return
	 */
	@RequestMapping("assessCompanyeExtract.htm")
	@ResponseBody
	public JSONObject assessCompanyeExtract(long id, String cityName, String provinceName,
											Model model) {
		String tipPrefix = "评估公司系统自动抽取";
		JSONObject jsonObject = new JSONObject();
		try {
			
			//用来保存筛选符合要求的评估公司
			List<AssetsAssessCompanyInfo> listCompanyForRequired = new ArrayList<AssetsAssessCompanyInfo>();
			if (cityName != null || provinceName != null) {//目前不支持国外客户所在区域在国外
				listCompanyForRequired = assessCompanyApplyServiceClient.assessCompanyeExtract(id,
					cityName, provinceName);
				//				FAssessCompanyApplyInfo assessCompanyApplyInfo = assessCompanyApplyServiceClient
				//					.findById(id);
				//				resetCityName(assessCompanyApplyInfo);
				//				String qualityAssets = assessCompanyApplyInfo.getQualityAssets();//资产
				//				String qualityHouse = assessCompanyApplyInfo.getQualityHouse();//房屋
				//				String qualityLand = assessCompanyApplyInfo.getQualityLand();//土地  全国包括  全省
				//				String[] qualityHouses = { "一级", "二级", "三级" };
				//				listCompanyForRequired = conditionScreen(cityName, provinceName, qualityAssets,
				//					qualityHouse, qualityLand);
				//				if (StringUtil.isNotEmpty(qualityLand) && "全省".equals(qualityLand)
				//					&& listCompanyForRequired.size() == 0) {//全省筛选不出来
				//					qualityLand = "全";
				//					listCompanyForRequired = conditionScreen(cityName, provinceName, qualityAssets,
				//						qualityHouse, qualityLand);//再次筛选
				//				}
				//				if (StringUtil.isNotEmpty(qualityHouse) && listCompanyForRequired.size() == 0) {//房产 精确查询无结果，模糊查询
				//					if ("二级".equals(qualityHouse)) {
				//						String tempQualityHouse = qualityHouses[0];
				//						listCompanyForRequired = conditionScreen(cityName, provinceName,
				//							qualityAssets, tempQualityHouse, qualityLand);//再次筛选
				//					}
				//					if ("三级".equals(qualityHouse)) {
				//						String tempQualityHouse = qualityHouses[1];
				//						listCompanyForRequired = conditionScreen(cityName, provinceName,
				//							qualityAssets, tempQualityHouse, qualityLand);//再次筛选
				//						if (listCompanyForRequired.size() == 0) {
				//							tempQualityHouse = qualityHouses[0];
				//							listCompanyForRequired = conditionScreen(cityName, provinceName,
				//								qualityAssets, tempQualityHouse, qualityLand);//再次筛选
				//						}
				//					}
				//				}
			} else {
				jsonObject.put("success", false);
				jsonObject.put("message", "暂时不支持客户所属区域在国外");
			}
			if (listCompanyForRequired == null) {
				listCompanyForRequired = Lists.newArrayList();
			}
			jsonObject.put("listCompanyForRequired", listCompanyForRequired);
		} catch (Exception e) {
			jsonObject = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 评估公司评价
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("evaluate.htm")
	public String evaluate(long id, Model model) {
		
		FAssessCompanyApplyInfo assessCompanyApplyInfo = assessCompanyApplyServiceClient
			.findById(id);
		resetCityName(assessCompanyApplyInfo);
		//本次指派的评估公司信息
		List<AssetsAssessCompanyInfo> listCompanyInfo = Lists.newArrayList();
		if (StringUtil.isNotEmpty(assessCompanyApplyInfo.getCompanyId())) {
			String[] companyId = assessCompanyApplyInfo.getCompanyId().split(",");
			for (String id1 : companyId) {
				listCompanyInfo.add(assessCompanyApplyServiceClient.findByApplyIdAndCompany(
					assessCompanyApplyInfo.getId(), Long.parseLong(id1)));
				//				listCompanyInfo.add(assetsAssessCompanyServiceClient.findById(Long.parseLong(id1)));
			}
		}
		model.addAttribute("listCompanyInfo", listCompanyInfo);
		
		model.addAttribute("assessCompanyApplyInfo", assessCompanyApplyInfo);
		ProjectSimpleDetailInfo projectInfo = projectServiceClient
			.querySimpleDetailInfo(assessCompanyApplyInfo.getProjectCode());
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("isEdit", "true");//是否编辑
		return vm_path + "companyRated.vm";
	}
	
	/**
	 * 查看评估公司评价
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("evaluateView.htm")
	public String evaluateView(long applyId, long evaluateId, Model model) {
		
		FAssessCompanyApplyInfo assessCompanyApplyInfo = assessCompanyApplyServiceClient
			.findById(applyId);
		resetCityName(assessCompanyApplyInfo);
		//本次指派的评估公司信息
		List<AssetsAssessCompanyInfo> listCompanyInfo = Lists.newArrayList();
		if (StringUtil.isNotEmpty(assessCompanyApplyInfo.getCompanyId())) {
			String[] companyId = assessCompanyApplyInfo.getCompanyId().split(",");
			for (String id1 : companyId) {
				listCompanyInfo.add(assessCompanyApplyServiceClient.findByApplyIdAndCompany(
					assessCompanyApplyInfo.getId(), Long.parseLong(id1)));
				//				listCompanyInfo.add(assetsAssessCompanyServiceClient.findById(Long.parseLong(id1)));
			}
		}
		model.addAttribute("listCompanyInfo", listCompanyInfo);
		model.addAttribute("assessCompanyApplyInfo", assessCompanyApplyInfo);
		//当前人的评价信息
		List<AssessCompanyEvaluateInfo> listEvaluateInfo = new ArrayList<AssessCompanyEvaluateInfo>();
		
		List<AssessCompanyEvaluateInfo> listEvaluateInfo1 = assessCompanyApplyServiceClient
			.findEvaluateByApplyId(applyId);
		for (AssessCompanyEvaluateInfo assessCompanyEvaluateInfo : listEvaluateInfo1) {
			if (assessCompanyEvaluateInfo.getEvaluatePerson() == evaluateId) {
				listEvaluateInfo.add(assessCompanyEvaluateInfo);
			}
		}
		model.addAttribute("listEvaluateInfo", listEvaluateInfo);//所有评价信息
		for (AssetsAssessCompanyInfo companyInfo : listCompanyInfo) {
			for (AssessCompanyEvaluateInfo evaluateInfo : listEvaluateInfo) {
				if (companyInfo.getId() == evaluateInfo.getCompanyId()) {
					companyInfo.setWorkSituationEvaluate(evaluateInfo.getWorkSituation());
					companyInfo.setAttachmentEvaluate(evaluateInfo.getAttachment());
					companyInfo.setTechnicalLevelEvaluate(evaluateInfo.getTechnicalLevel());
					companyInfo.setEvaluationEfficiencyEvaluate(evaluateInfo
						.getEvaluationEfficiency());
					companyInfo.setCooperationSituationEvaluate(evaluateInfo
						.getCooperationSituation());
					companyInfo.setServiceAttitudeEvaluate(evaluateInfo.getServiceAttitude());
					companyInfo.setIsReviewEvaluate(evaluateInfo.getIsReview().code());
				}
			}
		}
		ProjectSimpleDetailInfo projectInfo = projectServiceClient
			.querySimpleDetailInfo(assessCompanyApplyInfo.getProjectCode());
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("isEdit", "true");//是否编辑
		return vm_path + "companyRatedView.vm";
	}
	
	/**
	 * 保存评估公司评价
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveAssessCompanyEvaluate.htm")
	@ResponseBody
	public JSONObject saveAssessCompanyEvaluate(HttpServletRequest request,
												HttpServletResponse response,
												AssessCompanyManyEvaluateOrder manyOrder,
												Model model) {
		String tipPrefix = "保存对评估公司的评价";
		JSONObject result = new JSONObject();
		try {
			FcsBaseResult saveResult = null;
			List<AssessCompanyEvaluateOrder> listOrder = manyOrder.getListManyOrders();
			if (listOrder != null && listOrder.size() > 0) {
				for (AssessCompanyEvaluateOrder order : listOrder) {
					
					// 初始化Form验证信息
					SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
					
					if (sessionLocal == null) {
						result.put("success", false);
						result.put("message", "您未登陆或登陆已失效");
						return result;
					}
					if (order.getIsReview() == null) {
						order.setIsReview(BooleanEnum.NO.code());
					}
					order.setUserId(sessionLocal.getUserId());
					order.setUserName(sessionLocal.getRealName());
					order.setUserAccount(sessionLocal.getUserName());
					
					order.setEvaluatePerson(sessionLocal.getUserId());//评价人id
					order.setEvaluatePersonAccount(sessionLocal.getUserName());//评价人账号
					order.setEvaluatePersonName(sessionLocal.getRealName());//评价人名称
					setSessionLocalInfo2Order(order);
					
					saveResult = assessCompanyApplyServiceClient.saveAssessCompanyEvaluate(order);
					
				}
				if (saveResult.isSuccess()) {
					result.put("success", true);
					result.put("message", "保存成功");
				} else {
					result.put("success", false);
					result.put("message", saveResult.getMessage());
				}
			}
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
	}
	
	/**
	 * 评估公司更换(新增)
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("change.htm")
	public String change(long formId, Model model) {
		FormInfo form = formServiceAmClient.findByFormId(formId);
		
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		FAssessCompanyApplyInfo assessCompanyApplyInfo = assessCompanyApplyServiceClient
			.findByFormId(formId);
		resetCityName(assessCompanyApplyInfo);
		model.addAttribute("assessCompanyApplyInfo", assessCompanyApplyInfo);
		
		//前次指派的评估公司信息
		List<AssetsAssessCompanyInfo> listCompanyInfo = Lists.newArrayList();
		if (StringUtil.isNotEmpty(assessCompanyApplyInfo.getCompanyId())) {
			if (assessCompanyApplyInfo.getCompanyId().contains(";")) {
				String[] companyId = assessCompanyApplyInfo.getCompanyId().split(";")[1].split(",");
				for (String id : companyId) {
					AssetsAssessCompanyInfo info = assessCompanyApplyServiceClient
						.findByApplyIdAndCompany(assessCompanyApplyInfo.getId(),
							NumberUtil.parseLong(id));
					listCompanyInfo.add(info);
					//				listCompanyInfo.add(assetsAssessCompanyServiceClient.findById(Long.parseLong(id)));
				}
			} else {
				String[] companyId = assessCompanyApplyInfo.getCompanyId().split(",");
				for (String id : companyId) {
					AssetsAssessCompanyInfo info = assessCompanyApplyServiceClient
						.findByApplyIdAndCompany(assessCompanyApplyInfo.getId(),
							NumberUtil.parseLong(id));
					listCompanyInfo.add(info);
					//				listCompanyInfo.add(assetsAssessCompanyServiceClient.findById(Long.parseLong(id)));
				}
			}
		}
		model.addAttribute("change", "true");
		model.addAttribute("listCompanyInfo", listCompanyInfo);
		
		ProjectSimpleDetailInfo projectInfo = projectServiceClient
			.querySimpleDetailInfo(assessCompanyApplyInfo.getProjectCode());
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("form", form);
		//		model.addAttribute("isEdit", "true");//是否编辑
		return vm_path + "companyChange.vm";
	}
	
	/**
	 * 评估公司更换 編輯
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("editChange.htm")
	public String editChange(long formId, Model model) {
		FormInfo form = formServiceAmClient.findByFormId(formId);
		
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		
		model.addAttribute("form", form);
		
		FAssessCompanyApplyInfo assessCompanyApplyInfo = assessCompanyApplyServiceClient
			.findByFormId(formId);
		resetCityName(assessCompanyApplyInfo);
		model.addAttribute("assessCompanyApplyInfo", assessCompanyApplyInfo);
		
		//前次指派的評估公司
		FAssessCompanyApplyInfo assessCompanyApplyLastInfo = assessCompanyApplyServiceClient
			.findById(assessCompanyApplyInfo.getReplacedId());
		List<AssetsAssessCompanyInfo> listLastCompanyInfo = Lists.newArrayList();
		if (StringUtil.isNotEmpty(assessCompanyApplyLastInfo.getCompanyId())) {
			String[] companyId = assessCompanyApplyLastInfo.getCompanyId().split(",");
			for (String id : companyId) {
				listLastCompanyInfo.add(assessCompanyApplyServiceClient.findByApplyIdAndCompany(
					assessCompanyApplyInfo.getReplacedId(), Long.parseLong(id)));
				//				listLastCompanyInfo.add(assetsAssessCompanyServiceClient.findById(Long
				//					.parseLong(id)));
			}
		}
		model.addAttribute("assessCompanyApplyLastInfo", assessCompanyApplyLastInfo);
		model.addAttribute("listLastCompanyInfo", listLastCompanyInfo);//上次指派的公司信息
		//本次指派的评估公司信息
		List<AssetsAssessCompanyInfo> listCompanyInfo = Lists.newArrayList();
		if (StringUtil.isNotEmpty(assessCompanyApplyInfo.getCompanyId())) {
			String[] companyId = assessCompanyApplyInfo.getCompanyId().split(",");
			for (String id : companyId) {
				listCompanyInfo.add(assessCompanyApplyServiceClient.findByApplyIdAndCompany(
					assessCompanyApplyInfo.getId(), Long.parseLong(id)));
				//				listCompanyInfo.add(assetsAssessCompanyServiceClient.findById(Long.parseLong(id)));
			}
		}
		model.addAttribute("listCompanyInfo", listCompanyInfo);
		ProjectSimpleDetailInfo projectInfo = projectServiceClient
			.querySimpleDetailInfo(assessCompanyApplyInfo.getProjectCode());
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("isEdit", "true");//是否编辑
		return vm_path + "editCompanyChange.vm";
	}
	
	/**
	 * 评估公司更换 詳情
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("viewChange.htm")
	public String viewChange(long formId, Model model) {
		FormInfo form = formServiceAmClient.findByFormId(formId);
		
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		FAssessCompanyApplyInfo assessCompanyApplyInfo = assessCompanyApplyServiceClient
			.findByFormId(formId);
		resetCityName(assessCompanyApplyInfo);
		model.addAttribute("assessCompanyApplyInfo", assessCompanyApplyInfo);
		
		//前次指派的評估公司
		FAssessCompanyApplyInfo assessCompanyApplyLastInfo = assessCompanyApplyServiceClient
			.findById(assessCompanyApplyInfo.getReplacedId());
		List<AssetsAssessCompanyInfo> listLastCompanyInfo = Lists.newArrayList();
		if (StringUtil.isNotEmpty(assessCompanyApplyLastInfo.getCompanyId())) {
			String[] companyId = assessCompanyApplyLastInfo.getCompanyId().split(",");
			for (String id : companyId) {
				listLastCompanyInfo.add(assessCompanyApplyServiceClient.findByApplyIdAndCompany(
					assessCompanyApplyInfo.getReplacedId(), Long.parseLong(id)));
				//				listLastCompanyInfo.add(assetsAssessCompanyServiceClient.findById(Long
				//					.parseLong(id)));
			}
		}
		model.addAttribute("listLastCompanyInfo", listLastCompanyInfo);//上次指派的公司信息
		model.addAttribute("assessCompanyApplyLastInfo", assessCompanyApplyLastInfo);
		//本次指派的评估公司信息
		List<AssetsAssessCompanyInfo> listCompanyInfo = Lists.newArrayList();
		if (StringUtil.isNotEmpty(assessCompanyApplyInfo.getCompanyId())) {
			String[] companyId = assessCompanyApplyInfo.getCompanyId().split(",");
			for (String id : companyId) {
				listCompanyInfo.add(assessCompanyApplyServiceClient.findByApplyIdAndCompany(
					assessCompanyApplyInfo.getId(), Long.parseLong(id)));
				//				listCompanyInfo.add(assetsAssessCompanyServiceClient.findById(Long.parseLong(id)));
			}
		}
		model.addAttribute("listCompanyInfo", listCompanyInfo);
		ProjectSimpleDetailInfo projectInfo = projectServiceClient
			.querySimpleDetailInfo(assessCompanyApplyInfo.getProjectCode());
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("isEdit", "true");//是否编辑
		model.addAttribute("form", form);
		return vm_path + "viewCompanyChange.vm";
	}
	
	/**
	 * 评估公司指定审核
	 */
	@RequestMapping("audit/chooseAssessCompany.htm")
	public String chooseAssessCompany(long formId, String toPage, HttpServletRequest request,
										Integer toIndex, Model model, HttpSession session) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		
		model.addAttribute("chooseAssessCompany", "YES");
		model.addAttribute("isEdit", "true");//是否编辑
		List<AssetsAssessCompanyInfo> listCompanyForRequired = new ArrayList<AssetsAssessCompanyInfo>();
		FAssessCompanyApplyInfo assessCompanyApplyInfo = assessCompanyApplyServiceClient
			.findByFormId(formId);
		if (assessCompanyApplyInfo != null
			&& StringUtil.isNotEmpty(assessCompanyApplyInfo.getCompanyId())) {
			String[] ids = assessCompanyApplyInfo.getCompanyId().split(",");
			for (int i = 0; i < ids.length; i++) {
				String string = ids[i];
				AssetsAssessCompanyInfo info = assetsAssessCompanyServiceClient.findById(NumberUtil
					.parseLong(string));
				if (info != null) {
					listCompanyForRequired.add(info);
				}
			}
			model.addAttribute("listCompanyForRequired", listCompanyForRequired);
		}
		audit(formId, toPage, null, request, toIndex, model, session);
		return vm_path + "companyAppoint.vm";
	}
	
	/**
	 * 评估公司申请单 更换评估公司审核
	 *
	 * @param formId
	 * @param model
	 * @return
	 */
	
	@RequestMapping("audit.htm")
	public String audit(long formId, String toPage, String isChange, HttpServletRequest request,
						Integer toIndex, Model model, HttpSession session) {
		FormInfo form = formServiceAmClient.findByFormId(formId);
		
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		// SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		FAssessCompanyApplyInfo assessCompanyApplyInfo = assessCompanyApplyServiceClient
			.findByFormId(formId);
		resetCityName(assessCompanyApplyInfo);
		ProjectSimpleDetailInfo projectInfo = projectServiceClient
			.querySimpleDetailInfo(assessCompanyApplyInfo.getProjectCode());
		model.addAttribute("projectInfo", projectInfo);
		
		model.addAttribute("assessCompanyApplyInfo", assessCompanyApplyInfo);
		//前次指派的評估公司
		FAssessCompanyApplyInfo assessCompanyApplyLastInfo = assessCompanyApplyServiceClient
			.findById(assessCompanyApplyInfo.getReplacedId());
		List<AssetsAssessCompanyInfo> listLastCompanyInfo = Lists.newArrayList();
		if (assessCompanyApplyLastInfo != null) {
			if (StringUtil.isNotEmpty(assessCompanyApplyLastInfo.getCompanyId())) {
				String[] companyId = assessCompanyApplyLastInfo.getCompanyId().split(",");
				for (String id : companyId) {
					listLastCompanyInfo.add(assessCompanyApplyServiceClient
						.findByApplyIdAndCompany(assessCompanyApplyInfo.getReplacedId(),
							Long.parseLong(id)));
					//					listLastCompanyInfo.add(assetsAssessCompanyServiceClient.findById(Long
					//						.parseLong(id)));
				}
			}
		}
		model.addAttribute("listLastCompanyInfo", listLastCompanyInfo);//上次指派的公司信息
		model.addAttribute("assessCompanyApplyLastInfo", assessCompanyApplyLastInfo);
		//本次指派的评估公司信息
		List<AssetsAssessCompanyInfo> listCompanyInfo = Lists.newArrayList();
		if (assessCompanyApplyInfo != null) {
			if (StringUtil.isNotEmpty(assessCompanyApplyInfo.getCompanyId())) {
				String[] companyId = assessCompanyApplyInfo.getCompanyId().split(",");
				for (String id : companyId) {
					listCompanyInfo.add(assessCompanyApplyServiceClient.findByApplyIdAndCompany(
						assessCompanyApplyInfo.getId(), Long.parseLong(id)));
					//					listCompanyInfo.add(assetsAssessCompanyServiceClient.findById(Long
					//						.parseLong(id)));
				}
			}
		}
		model.addAttribute("listCompanyInfo", listCompanyInfo);
		
		model.addAttribute("currPage", toPage);
		
		model.addAttribute("form", form);// 表单信息
		model.addAttribute("formCode", form.getFormCode());
		
		if (toIndex == null)
			toIndex = 0;
		model.addAttribute("toIndex", toIndex);
		//WebNodeInfo nodeInfo = initWorkflow(model, form, request.getParameter("taskId"));
		//if(nodeInfo.getFormUrl())
		initWorkflow(model, form, request.getParameter("taskId"));
		if (assessCompanyApplyInfo.getReplacedId() > 0) {
			return vm_path + "viewCompanyChange.vm";
		} else {
			return vm_path + "view.vm";
		}
	}
	
	/**
	 * 打印
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("print.htm")
	public String print(long formId, Model model) {
		FormInfo form = formServiceAmClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		FAssessCompanyApplyInfo assessCompanyApplyInfo = assessCompanyApplyServiceClient
			.findByFormId(formId);
		resetCityName(assessCompanyApplyInfo);
		ProjectSimpleDetailInfo projectInfo = projectServiceClient
			.querySimpleDetailInfo(assessCompanyApplyInfo.getProjectCode());
		model.addAttribute("projectInfo", projectInfo);
		
		model.addAttribute("assessCompanyApplyInfo", assessCompanyApplyInfo);
		
		model.addAttribute("form", form);
		
		return vm_path + "view.vm";
	}
	
	private void resetCityName(FAssessCompanyApplyInfo assessCompanyApplyInfo) {
		String cityCode = assessCompanyApplyInfo.getCityCode();
		String cityName = assessCompanyApplyInfo.getCityName();
		if ("市辖区".equals(assessCompanyApplyInfo.getProvinceName())
			|| "县".equals(assessCompanyApplyInfo.getProvinceName())
			|| "请选择".equals(assessCompanyApplyInfo.getProvinceName())) {
			assessCompanyApplyInfo.setCityCode(assessCompanyApplyInfo.getProvinceCode());
			assessCompanyApplyInfo.setCityName(assessCompanyApplyInfo.getProvinceName());
			assessCompanyApplyInfo.setProvinceCode(cityCode);
			assessCompanyApplyInfo.setProvinceName(cityName);
		}
	}
	
	public AssetsAssessCompanyInfo mapSort(Map<Long, Integer> map) {
		//这里将map.entrySet()转换成list
		AssetsAssessCompanyInfo assetsAssessCompanyInfo = null;
		List<Map.Entry<Long, Integer>> list = new ArrayList<Map.Entry<Long, Integer>>(
			map.entrySet());
		
		//然后通过比较器来实现排序
		
		Collections.sort(list, new Comparator<Map.Entry<Long, Integer>>() {
			
			//升序排序
			@Override
			public int compare(Entry<Long, Integer> o1, Entry<Long, Integer> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
			
		});
		Collections.shuffle(list);
		for (Map.Entry<Long, Integer> mapping : list) {
			
			assetsAssessCompanyInfo = assetsAssessCompanyServiceClient.findById(mapping.getKey());
			break;
		}
		return assetsAssessCompanyInfo;
	}
}
