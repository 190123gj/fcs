package com.born.fcs.face.web.controller.project.financialproject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.pm.util.CommonUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductInterestTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductTermTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProjectStatusEnum;
import com.born.fcs.pm.ws.enums.InterestSettlementWayEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FinancialProductInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectRedeemFormInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectTransferFormInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectWithdrawingInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialSettlementInfo;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectRedeemFormQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectTransferFormQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectWithdrawingQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialSettlementOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialSettlementQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryAmountBatchResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.financialproject.FinancialProjectInterestResult;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 理财项目
 * @author wuzj
 */
@Controller
@RequestMapping("projectMg/financialProject")
public class FinancialProjectController extends BaseController {
	
	private final String vm_path = "/projectMg/financialMg/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "actualBuyDate", "actualExpireDate", "buyDateStart", "buyDateEnd",
								"expireDateStart", "expireDateEnd", "settlementTime" };
	}
	
	@Override
	protected String[] getMoneyInputNameArray() {
		return new String[] { "actualPrincipal", "actualInterest", "settlementAmount" };
	}
	
	/**
	 * 列表
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(FinancialProjectQueryOrder order, Model model) {
		
		//		setSessionLocalInfo2Order(order);
		//		order.setCreateUserId(order.getBusiManagerId());
		if (order.getBuyDateEnd() != null) {
			order.setBuyDateStart(order.getBuyDateEnd());
		}
		model.addAttribute("queryOrder", order);
		model.addAttribute("statusList", FinancialProjectStatusEnum.getAllEnum());
		model.addAttribute("isFinancialPersonnel", DataPermissionUtil.isFinancialZjlc()
													|| DataPermissionUtil.isXinHuiBusiManager());
		
		model.addAttribute("page",
			PageUtil.getCovertPage(financialProjectServiceClient.query(order)));
		
		return vm_path + "itemList.vm";
	}
	
	/**
	 * 应收计提台帐列表
	 * @param queryDate
	 * @return
	 */
	@RequestMapping("receiptWithdrawingList.htm")
	public String receiptWithdrawingList(FinancialProjectWithdrawingQueryOrder order,
											HttpServletRequest request, Model model) {
		String vm = null;
		order.setWithdrawType("RECEIPT");
		if (StringUtil.equals(request.getParameter("queryHistory"), "yes")) {
			if (StringUtil.isNotBlank(order.getProjectCode())) {
				DateFormat monthFormat = DateUtil.getFormat("yyyy-MM");
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MONTH, -1);
				String lastMonth = monthFormat.format(calendar.getTime());
				//查询最近2个月的 - 2016-11-1 查询最新
				FinancialProjectWithdrawingQueryOrder latestOrder = new FinancialProjectWithdrawingQueryOrder();
				//latestOrder.setWithdrawMonthStart(lastMonth);
				latestOrder.setProjectCode(order.getProjectCode());
				latestOrder.setWithdrawType("RECEIPT");
				latestOrder.setSortCol("id");
				latestOrder.setSortOrder("desc");
				latestOrder.setPageSize(1);
				QueryAmountBatchResult<FinancialProjectWithdrawingInfo> batchResult = financialProjectServiceClient
					.queryWithdraw(latestOrder);
				if (batchResult != null && batchResult.isSuccess()) {
					if (batchResult.getTotalCount() > 0) {
						model.addAttribute("withdrawProject", batchResult.getPageList().get(0));
					}
					//					else {
					//						FinancialProjectWithdrawingInfo withdrawProject = null;
					//						for (FinancialProjectWithdrawingInfo wp : batchResult.getPageList()) {
					//							if (wp.getWithdrawFinish() == BooleanEnum.YES) {
					//								withdrawProject = wp;
					//								break;
					//							}
					//						}
					//						if (withdrawProject == null)
					//							withdrawProject = batchResult.getPageList().get(0);
					//						model.addAttribute("withdrawProject", withdrawProject);
					//					}
				}
				//查询所有历史
				order.setPageSize(999);
				if (StringUtil.isBlank(order.getWithdrawMonthEnd()))
					order.setWithdrawMonthEnd(lastMonth);
			}
			vm = vm_path + "queryCollectHistory.vm";
		} else {
			if (StringUtil.isBlank(order.getWithdrawMonth())) {//默认查询当月
				order.setWithdrawMonth(DateUtil.getFormat("yyyy-MM").format(new Date()));
			}
			vm = vm_path + "queryCollect.vm";
		}
		model.addAttribute("queryOrder", order);
		QueryAmountBatchResult<FinancialProjectWithdrawingInfo> batchResult = financialProjectServiceClient
			.queryWithdraw(order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		model.addAttribute("totalInterest", batchResult.getTotalAmount());
		
		return vm;
	}
	
	/**
	 * 应付计提台帐列表
	 * @param queryDate
	 * @return
	 */
	@RequestMapping("paymentWithdrawingList.htm")
	public String paymentWithdrawingList(FinancialProjectWithdrawingQueryOrder order,
											HttpServletRequest request, Model model) {
		
		String vm = null;
		order.setWithdrawType("PAYMENT");
		if (StringUtil.equals(request.getParameter("queryHistory"), "yes")) {
			if (order.getTransferTradeId() != null && order.getTransferTradeId() > 0) {
				DateFormat monthFormat = DateUtil.getFormat("yyyy-MM");
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MONTH, -1);
				String lastMonth = monthFormat.format(calendar.getTime());
				//查询最近2个月的 - 2016-11-1需求 查询最新
				FinancialProjectWithdrawingQueryOrder latestOrder = new FinancialProjectWithdrawingQueryOrder();
				//latestOrder.setWithdrawMonthStart(lastMonth);
				latestOrder.setProjectCode(order.getProjectCode());
				latestOrder.setTransferTradeId(order.getTransferTradeId());
				latestOrder.setWithdrawType("PAYMENT");
				latestOrder.setSortCol("id");
				latestOrder.setSortOrder("desc");
				latestOrder.setPageSize(1);
				QueryAmountBatchResult<FinancialProjectWithdrawingInfo> batchResult = financialProjectServiceClient
					.queryWithdraw(latestOrder);
				if (batchResult != null && batchResult.isSuccess()) {
					if (batchResult.getTotalCount() > 0) {
						model.addAttribute("withdrawTransfer", batchResult.getPageList().get(0));
					}
					//					else {
					//						FinancialProjectWithdrawingInfo withdrawTransfer = null;
					//						for (FinancialProjectWithdrawingInfo wt : batchResult.getPageList()) {
					//							if (wt.getWithdrawFinish() == BooleanEnum.YES) {
					//								withdrawTransfer = wt;
					//								break;
					//							}
					//						}
					//						if (withdrawTransfer == null)
					//							withdrawTransfer = batchResult.getPageList().get(0);
					//						model.addAttribute("withdrawTransfer", withdrawTransfer);
					//					}
				}
				//查询所有历史
				order.setPageSize(999);
				if (StringUtil.isBlank(order.getWithdrawMonthEnd()))
					order.setWithdrawMonthEnd(lastMonth);
			}
			vm = vm_path + "queryPayHistory.vm";
		} else {
			if (StringUtil.isBlank(order.getWithdrawMonth())) {//默认查询当月
				order.setWithdrawMonth(DateUtil.getFormat("yyyy-MM").format(new Date()));
			}
			vm = vm_path + "queryPay.vm";
		}
		model.addAttribute("queryOrder", order);
		QueryAmountBatchResult<FinancialProjectWithdrawingInfo> batchResult = financialProjectServiceClient
			.queryWithdraw(order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		model.addAttribute("totalInterest", batchResult.getTotalAmount());
		
		return vm;
	}
	
	/**
	 * 查看项目
	 * @param projectCode
	 * @return
	 */
	@RequestMapping("view.htm")
	public String view(String projectCode, Model model) {
		
		ProjectFinancialInfo project = financialProjectServiceClient.queryByCode(projectCode);
		
		//没有查到项目去立项那里看看
		if (project == null) {
			FProjectFinancialInfo applyInfo = financialProjectSetupServiceClient
				.queryByProjectCode(projectCode);
			if (applyInfo != null) {
				if (applyInfo.getProductId() > 0) {
					FinancialProductInfo product = financialProductServiceClient.findById(applyInfo
						.getProductId());
					model.addAttribute("product", product);
				}
				FormInfo form = formServiceClient.findByFormId(applyInfo.getFormId());
				model.addAttribute("form", form);
				model.addAttribute("applyInfo", applyInfo);
				model.addAttribute("productTypeList", FinancialProductTypeEnum.getAllEnum());
				model.addAttribute("termTypeList", FinancialProductTermTypeEnum.getAllEnum());
				model.addAttribute("interestTypeList",
					FinancialProductInterestTypeEnum.getAllEnum());
				model.addAttribute("interestSettlementWayList",
					InterestSettlementWayEnum.getAllEnum());
				if (form != null)
					queryCommonAttachmentData(model, "PM_" + form.getFormId(),
						CommonAttachmentTypeEnum.FORM_ATTACH);
				return vm_path + "applyView.vm";
			}
		}
		
		model.addAttribute("project", project);
		model.addAttribute("transferingNum",
			financialProjectServiceClient.transferingNum(projectCode, 0));
		model.addAttribute("redeemingNum",
			financialProjectServiceClient.redeemingNum(projectCode, 0));
		FinancialProjectInterestResult cResult = financialProjectServiceClient.caculateInterest(
			projectCode, null);
		if (cResult != null && cResult.isSuccess()) {
			model.addAttribute("holdingPeriodInterest", cResult.getHoldingPeriodInterest());
		}
		//查询附件
		if (project != null)
			queryCommonAttachmentData(model, String.valueOf(project.getProjectId()),
				CommonAttachmentTypeEnum.FINANCIAL_PROJECT_MAINTAIN);
		
		//		//转让交易
		//		ProjectFinancialTradeTansferQueryOrder transferQueryOrder = new ProjectFinancialTradeTansferQueryOrder();
		//		transferQueryOrder.setProjectCode(projectCode);
		//		QueryBaseBatchResult<ProjectFinancialTradeTansferInfo> transferList = financialProjectTransferServiceClient
		//			.queryTrade(transferQueryOrder);
		//		if (transferList != null)
		//			model.addAttribute("transferList", transferList.getPageList());
		//		
		//		//赎回交易
		//		ProjectFinancialTradeRedeemQueryOrder redeemQueryOrder = new ProjectFinancialTradeRedeemQueryOrder();
		//		redeemQueryOrder.setProjectCode(projectCode);
		//		QueryBaseBatchResult<ProjectFinancialTradeRedeemInfo> redeemList = financialProjectRedeemServiceClient
		//			.queryTrade(redeemQueryOrder);
		//		if (redeemList != null)
		//			model.addAttribute("redeemList", redeemList.getPageList());
		
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			//转让列表
			FinancialProjectTransferFormQueryOrder transferQueryOrder = new FinancialProjectTransferFormQueryOrder();
			transferQueryOrder.setProjectCode(projectCode);
			transferQueryOrder.setPageSize(999);
			transferQueryOrder.setDraftUserId(sessionLocal.getUserId());
			QueryBaseBatchResult<FinancialProjectTransferFormInfo> transferList = financialProjectTransferServiceClient
				.queryPage(transferQueryOrder);
			if (transferList != null)
				model.addAttribute("transferList", transferList.getPageList());
			
			//赎回列表
			FinancialProjectRedeemFormQueryOrder redeemQueryOrder = new FinancialProjectRedeemFormQueryOrder();
			redeemQueryOrder.setProjectCode(projectCode);
			redeemQueryOrder.setPageSize(999);
			redeemQueryOrder.setDraftUserId(sessionLocal.getUserId());
			QueryBaseBatchResult<FinancialProjectRedeemFormInfo> redeemList = financialProjectRedeemServiceClient
				.queryPage(redeemQueryOrder);
			if (redeemList != null)
				model.addAttribute("redeemList", redeemList.getPageList());
		}
		return vm_path + "itemView.vm";
	}
	
	/**
	 * 理财项目信息维护
	 * @param applyId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("maintain.htm")
	public String maintain(String projectCode, HttpServletRequest request, Model model) {
		
		if (StringUtil.isNotBlank(projectCode)) {
			ProjectFinancialInfo project = financialProjectServiceClient.queryByCode(projectCode);
			if (project == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "理财项目不存在");
			}
			model.addAttribute("project", project);
		}
		return vm_path + "applyMaintain.vm";
	}
	
	/**
	 * 信息维护保存
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("confirm.htm")
	@ResponseBody
	public JSONObject confirm(FinancialProjectOrder order, HttpServletRequest request, Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			setSessionLocalInfo2Order(order);
			order.setIsConfirm(BooleanEnum.IS);
			order.setIsConfirmExpire(BooleanEnum.NO);
			FcsBaseResult result = financialProjectServiceClient.confirm(order);
			
			jsonObject = toJSONResult(jsonObject, result, "保存成功", null);
			if (result != null && result.isSuccess()) {
				addAttachfile(String.valueOf(result.getKeyId()), request, order.getProjectCode(),
					"理财项目信息维护", CommonAttachmentTypeEnum.FINANCIAL_PROJECT_MAINTAIN);
			}
			
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", "信息维护出错");
			logger.error("信息维护出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 理财项目到期信息维护
	 * @param applyId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("maintainExpire.htm")
	public String maintainExpire(String projectCode, HttpServletRequest request, Model model) {
		
		if (StringUtil.isNotBlank(projectCode)) {
			ProjectFinancialInfo project = financialProjectServiceClient.queryByCode(projectCode);
			if (project == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "理财项目不存在");
			}
			if (!project.isAfterExipreDate()
				&& project.getStatus() != FinancialProjectStatusEnum.PURCHASED
				&& project.getStatus() != FinancialProjectStatusEnum.EXPIRE) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
					"当前状态不允许信息维护");
			}
			model.addAttribute("project", project);
			model.addAttribute("transferingNum",
				financialProjectServiceClient.transferingNum(projectCode, 0));
			model.addAttribute("redeemingNum",
				financialProjectServiceClient.redeemingNum(projectCode, 0));
			FinancialProjectInterestResult cResult = financialProjectServiceClient
				.caculateInterest(projectCode, null);
			if (cResult != null && cResult.isSuccess()) {
				model.addAttribute("holdingPeriodInterest", cResult.getHoldingPeriodInterest());
			}
		}
		
		return vm_path + "timeOutMaintain.vm";
	}
	
	/**
	 * 信息维护保存
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("confirmExpire.htm")
	@ResponseBody
	public JSONObject confirmExpire(FinancialProjectOrder order, Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			setSessionLocalInfo2Order(order);
			order.setIsConfirm(BooleanEnum.NO);
			order.setIsConfirmExpire(BooleanEnum.IS);
			FcsBaseResult result = financialProjectServiceClient.confirm(order);
			
			jsonObject = toJSONResult(jsonObject, result, "保存成功", null);
			
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", "到期维护出错");
			logger.error("到期维护出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 计算到期日期
	 * @param projectCode
	 * @param transferPrice
	 * @param transferNum
	 * @param transferDate
	 * @return
	 */
	@RequestMapping("caculateExpireDate.htm")
	@ResponseBody
	public JSONObject caculateExpireDate(String projectCode, Long productId, String buyDate,
											Integer timeLimit, String timeUnit) {
		JSONObject json = new JSONObject();
		try {
			
			if (StringUtil.isNotBlank(buyDate)
				&& ((timeLimit != null && timeLimit > 0 && timeUnit != null)
					|| (productId != null && productId > 0) || StringUtil.isNotBlank(projectCode))) {
				
				if (timeLimit == null)
					timeLimit = 0;
				if (StringUtil.isBlank(timeUnit))
					timeUnit = TimeUnitEnum.DAY.code();
				
				TimeUnitEnum timeUnitEnum = TimeUnitEnum.getByCode(timeUnit);
				
				if (StringUtil.isNotBlank(projectCode)) {
					ProjectFinancialInfo project = financialProjectServiceClient
						.queryByCode(projectCode);
					if (project == null) {
						json.put("success", false);
						json.put("message", "理财项目不存在");
						return json;
					} else {
						timeLimit = project.getTimeLimit();
						timeUnitEnum = project.getTimeUnit();
					}
				} else if (productId != null && productId > 0) {
					FinancialProductInfo product = financialProductServiceClient
						.findById(productId);
					if (product == null) {
						json.put("success", false);
						json.put("message", "理财产品不存在");
						return json;
					} else {
						timeLimit = product.getTimeLimit();
						timeUnitEnum = product.getTimeUnit();
					}
				}
				
				Date bDate = DateUtil.string2DateTimeByAutoZero(buyDate);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(bDate);
				if (TimeUnitEnum.YEAR == timeUnitEnum) {
					calendar.add(Calendar.YEAR, timeLimit);
				} else if (TimeUnitEnum.MONTH == timeUnitEnum) {
					calendar.add(Calendar.MONTH, timeLimit);
				} else {
					calendar.add(Calendar.DAY_OF_MONTH, timeLimit);
				}
				json.put("success", true);
				json.put("expireDate", DateUtil.dtSimpleFormat(calendar.getTime()));
				json.put("message", "计算成功");
			} else {
				json.put("success", false);
				json.put("message", "计算到期日参数不完整");
			}
			
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "计算到期日出错");
		}
		
		return json;
	}
	
	/**
	 * 计算转让收益
	 * @param projectCode
	 * @param transferPrice
	 * @param transferNum
	 * @param transferDate
	 * @return
	 */
	@RequestMapping("caculateInterestRate.htm")
	@ResponseBody
	public JSONObject caculateInterestRate(String projectCode, String interest, String principal,
											String caculateDate) {
		JSONObject json = new JSONObject();
		try {
			Date cDate = DateUtil.parse(caculateDate);
			if (StringUtil.isNotBlank(projectCode) && StringUtil.isNotBlank(interest)
				&& StringUtil.isNotBlank(principal)) {
				Money mInterest = Money.amout(interest);
				Money mPrincipal = Money.amout(principal);
				double rate = financialProjectServiceClient.caculateInterestRate(projectCode,
					mInterest, mPrincipal, cDate);
				json.put("success", true);
				json.put("message", "计算成功");
				json.put("interestRate", CommonUtil.numberFormat(rate, 2));
				json.put("interestRatePercent", CommonUtil.numberFormat(rate * 100, 2));
			} else {
				json.put("success", false);
				json.put("message", "计算收益率参数不完整");
			}
			
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "计算收益率出错");
		}
		
		return json;
	}
	
	/**
	 * 计算预期收益和本金
	 * @param projectCode
	 * @param transferPrice
	 * @param transferNum
	 * @param transferDate
	 * @return
	 */
	@RequestMapping("expectPrincipalAndInterest.htm")
	@ResponseBody
	public JSONObject expectPrincipalAndInterest(String projectCode, Double buyNum,
													String caculateDate) {
		JSONObject json = new JSONObject();
		try {
			Date cDate = DateUtil.parse(caculateDate);
			if (StringUtil.isNotBlank(projectCode) && buyNum != null && buyNum > 0) {
				ProjectFinancialInfo project = financialProjectServiceClient
					.queryByCode(projectCode);
				if (project != null) {
					Money principal = Money.zero();
					principal = project.getActualPrice().multiply(buyNum);
					json.put("success", true);
					json.put("message", "计算成功");
					json.put("principal", principal.getAmount());
					json.put(
						"interest",
						financialProjectServiceClient.caculateRedeemInterest(projectCode,
							project.getActualPrice(), buyNum, cDate).getAmount());
				} else {
					json.put("success", false);
					json.put("message", "理财项目不存在");
				}
			} else {
				json.put("success", false);
				json.put("message", "计算应收本金参数不完整");
			}
			
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "计算收益率出错");
		}
		
		return json;
	}
	
	/**
	 * 结息页面
	 * @param queryDate
	 * @return
	 */
	@RequestMapping("toSettlement.htm")
	public String toSettlement(String projectCode, HttpServletRequest request, Model model) {
		if (StringUtil.isNotBlank(projectCode)) {
			ProjectFinancialInfo project = financialProjectServiceClient.queryByCode(projectCode);
			model.addAttribute("project", project);
		}
		return vm_path + "settlement.vm";
	}
	
	/**
	 * 结息
	 * @param order
	 * @param request
	 * @return
	 */
	@RequestMapping("settlement.json")
	@ResponseBody
	public JSONObject settlement(ProjectFinancialSettlementOrder order, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {
			FcsBaseResult result = financialProjectServiceClient.settlement(order);
			return toJSONResult(result, "结息");
		} catch (Exception e) {
			logger.error("结息出错：{}", e);
			json.put("success", false);
			json.put("message", "结息出错");
		}
		return json;
	}
	
	/**
	 * 列表
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("settlementList.htm")
	public String settlementList(FinancialProjectQueryOrder order, Model model) {
		
		//		setSessionLocalInfo2Order(order);
		//		order.setCreateUserId(order.getBusiManagerId());
		if (order.getBuyDateEnd() != null) {
			order.setBuyDateStart(order.getBuyDateEnd());
		}
		List<String> statusList = Lists.newArrayList();
		statusList.add(FinancialProjectStatusEnum.PURCHASED.code());
		statusList.add(FinancialProjectStatusEnum.EXPIRE.code());
		statusList.add(FinancialProjectStatusEnum.FINISH.code());
		order.setStatusList(statusList);
		model.addAttribute("queryOrder", order);
		model.addAttribute("isFinancialPersonnel", DataPermissionUtil.isFinancialZjlc()
													|| DataPermissionUtil.isXinHuiBusiManager());
		model.addAttribute("page",
			PageUtil.getCovertPage(financialProjectServiceClient.query(order)));
		
		return vm_path + "settlementList.vm";
	}
	
	/**
	 * 结息记录
	 * @param queryDate
	 * @return
	 */
	@RequestMapping("settlementHisList.htm")
	public String settlementHisList(HttpServletRequest request, Model model) {
		try {
			
			ProjectFinancialSettlementQueryOrder order = new ProjectFinancialSettlementQueryOrder();
			WebUtil.setPoPropertyByRequest(order, request);
			
			DateFormat df = DateUtil.getFormat("yyyy-MM");
			String settlementStartTime = request.getParameter("settlementStartTime");
			String settlementEndTime = request.getParameter("settlementEndTime");
			Calendar c = Calendar.getInstance();
			if (StringUtil.isNotBlank(settlementStartTime)) {
				c.setTime(df.parse(settlementStartTime));
				c.set(Calendar.DAY_OF_MONTH, 1);
				c.set(Calendar.HOUR_OF_DAY, 0);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
				c.set(Calendar.MILLISECOND, 0);
				order.setSettlementTimeStart(c.getTime());
				model.addAttribute("settlementStartTime", settlementStartTime);
			}
			if (StringUtil.isNotBlank(settlementEndTime)) {
				c.setTime(df.parse(settlementEndTime));
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
				c.set(Calendar.HOUR_OF_DAY, 23);
				c.set(Calendar.MINUTE, 59);
				c.set(Calendar.SECOND, 59);
				c.set(Calendar.MILLISECOND, 0);
				order.setSettlementTimeEnd(c.getTime());
				model.addAttribute("settlementEndTime", settlementEndTime);
			}
			
			if (StringUtil.isNotBlank(order.getProjectCode())) {
				ProjectFinancialInfo project = financialProjectServiceClient.queryByCode(order
					.getProjectCode());
				model.addAttribute("project", project);
			}
			
			model.addAttribute("queryOrder", order);
			QueryAmountBatchResult<ProjectFinancialSettlementInfo> batchResult = financialProjectServiceClient
				.querySettlement(order);
			order.setPageSize(999);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("totalInterest", batchResult.getTotalAmount());
		} catch (Exception e) {
			logger.error("查询结息记录出错：{}", e);
		}
		return vm_path + "querySettlement.vm";
	}
}
