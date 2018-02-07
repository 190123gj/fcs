package com.born.fcs.face.web.controller.fund.payment;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.fm.ws.enums.CostCategoryStatusEnum;
import com.born.fcs.fm.ws.enums.PeriodEnum;
import com.born.fcs.fm.ws.info.payment.BudgetDetailInfo;
import com.born.fcs.fm.ws.info.payment.BudgetInfo;
import com.born.fcs.fm.ws.info.payment.CostCategoryInfo;
import com.born.fcs.fm.ws.order.payment.BudgetOrder;
import com.born.fcs.fm.ws.order.payment.BudgetQueryOrder;
import com.born.fcs.fm.ws.order.payment.CostCategoryQueryOrder;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.money.Money;

@Controller
@RequestMapping("fundMg/budget/")
public class BudgetController extends BaseController {
	final static String vm_path = "fundMg/paymentMg/budget/";
	
	@RequestMapping("list.htm")
	public String budgetList(HttpServletRequest request, Model model) {
		buildSystemNameDefaultUrl(request, model);
		BudgetQueryOrder order = new BudgetQueryOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		setSessionLocalInfo2Order(order);
		String budgetDeptIds = request.getParameter("budgetDeptIds");
		if (StringUtil.isNotBlank(budgetDeptIds)) {
			String[] budgetDepts = budgetDeptIds.split(",");
			List<String> budgetDeptList = new ArrayList<String>();
			for (String budgetDeptId : budgetDepts) {
				budgetDeptList.add(budgetDeptId);
			}
			order.setBudgetDeptList(budgetDeptList);
		}
		QueryBaseBatchResult<BudgetInfo> queryResult = budgetServiceClient.queryPage(order);
		model.addAttribute("page", PageUtil.getCovertPage(queryResult));
		model.addAttribute("conditions", order);
		model.addAttribute("budgetDeptIds", budgetDeptIds);
		model.addAttribute("budgetDeptNms", request.getParameter("budgetDeptNms"));
		model.addAttribute("dateSel", request.getParameter("dateSel"));
		
		return vm_path + "list.vm";
	}
	
	@RequestMapping("detailList.htm")
	public String budgetDetailList(long budgetId, HttpServletRequest request, Model model) {
		buildSystemNameDefaultUrl(request, model);
		if (budgetId > 0) {
			BudgetInfo budgetInfo = budgetServiceClient.queryById(budgetId);
			model.addAttribute("info", budgetInfo);
			if (PeriodEnum.YEAR == budgetInfo.getPeriod()) {
				model.addAttribute("year", budgetInfo.getPeriodValue());
			}
			if (PeriodEnum.SEASON == budgetInfo.getPeriod()) {
				model.addAttribute("year", budgetInfo.getPeriodValue().substring(0, 4));
				model.addAttribute("season", budgetInfo.getPeriodValue().substring(4, 5));
			}
			if (PeriodEnum.MONTH == budgetInfo.getPeriod()) {
				model.addAttribute("year", budgetInfo.getPeriodValue().substring(0, 4));
				model.addAttribute("month",
					Integer.valueOf(budgetInfo.getPeriodValue().substring(4, 6)));
			}
			queryCommonAttachmentData(model, String.valueOf(budgetInfo.getBudgetId()),
				CommonAttachmentTypeEnum.BUDGET_ATTACH);
		} else {
			CostCategoryQueryOrder order = new CostCategoryQueryOrder();
			order.setStatusList(new ArrayList<CostCategoryStatusEnum>());
			order.getStatusList().add(CostCategoryStatusEnum.NORMAL);
			order.setPageSize(1000);
			QueryBaseBatchResult<CostCategoryInfo> costCategorys = costCategoryServiceClient
				.queryPage(order);
			List<CostCategoryInfo> costCategoryList = costCategorys.getPageList();
			if (costCategoryList != null && !costCategoryList.isEmpty()) {
				BudgetInfo budgetInfo = new BudgetInfo();
				List<BudgetDetailInfo> detailList = new ArrayList<BudgetDetailInfo>();
				budgetInfo.setDetailList(detailList);
				for (CostCategoryInfo costCategory : costCategoryList) {
					BudgetDetailInfo dInfo = new BudgetDetailInfo();
					dInfo.setAmount(new Money(0, 0));
					dInfo.setCategoryId(costCategory.getCategoryId());
					dInfo.setCategoryNm(costCategory.getName());
					dInfo.setIsContrl("NO");
					detailList.add(dInfo);
				}
				model.addAttribute("info", budgetInfo);
			}
			
		}
		model.addAttribute("budgetId", budgetId);
		
		String isView = request.getParameter("isView");
		if ("Y".equals(isView)) {
			return vm_path + "viewForm.vm";
		}
		//		model.addAttribute("budgetCycle", PeriodEnum.getAllEnum());
		return vm_path + "addForm.vm";
	}
	
	@RequestMapping("detailMessage.htm")
	public String detailMessage(long budgetId, HttpServletRequest request, Model model) {
		buildSystemNameDefaultUrl(request, model);
		if (budgetId > 0) {
			BudgetInfo budgetInfo = budgetServiceClient.queryById(budgetId);
			model.addAttribute("info", budgetInfo);
			if (PeriodEnum.YEAR == budgetInfo.getPeriod()) {
				model.addAttribute("year", budgetInfo.getPeriodValue());
			}
			if (PeriodEnum.SEASON == budgetInfo.getPeriod()) {
				model.addAttribute("year", budgetInfo.getPeriodValue().substring(0, 4));
				model.addAttribute("season", budgetInfo.getPeriodValue().substring(4, 5));
			}
			if (PeriodEnum.MONTH == budgetInfo.getPeriod()) {
				model.addAttribute("year", budgetInfo.getPeriodValue().substring(0, 4));
				model.addAttribute("month",
					Integer.valueOf(budgetInfo.getPeriodValue().substring(4, 6)));
			}
			queryCommonAttachmentData(model, String.valueOf(budgetInfo.getBudgetId()),
				CommonAttachmentTypeEnum.BUDGET_ATTACH);
		} else {
			CostCategoryQueryOrder order = new CostCategoryQueryOrder();
			order.setStatusList(new ArrayList<CostCategoryStatusEnum>());
			order.getStatusList().add(CostCategoryStatusEnum.NORMAL);
			order.setPageSize(1000);
			QueryBaseBatchResult<CostCategoryInfo> costCategorys = costCategoryServiceClient
				.queryPage(order);
			List<CostCategoryInfo> costCategoryList = costCategorys.getPageList();
			if (costCategoryList != null && !costCategoryList.isEmpty()) {
				BudgetInfo budgetInfo = new BudgetInfo();
				List<BudgetDetailInfo> detailList = new ArrayList<BudgetDetailInfo>();
				budgetInfo.setDetailList(detailList);
				for (CostCategoryInfo costCategory : costCategoryList) {
					BudgetDetailInfo dInfo = new BudgetDetailInfo();
					dInfo.setAmount(new Money(0, 0));
					dInfo.setCategoryId(costCategory.getCategoryId());
					dInfo.setCategoryNm(costCategory.getName());
					dInfo.setIsContrl("NO");
					detailList.add(dInfo);
				}
				model.addAttribute("info", budgetInfo);
			}
			
		}
		model.addAttribute("budgetId", budgetId);
		
		String isView = request.getParameter("isView");
		if ("Y".equals(isView)) {
			return vm_path + "viewForm.vm";
		}
		//		model.addAttribute("budgetCycle", PeriodEnum.getAllEnum());
		return vm_path + "viewForm.vm";
	}
	
	@ResponseBody
	@RequestMapping("save.json")
	public JSONObject save(HttpServletRequest request, Model model, BudgetOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		String period = request.getParameter("period");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String season = request.getParameter("season");
		if (PeriodEnum.YEAR.code().equalsIgnoreCase(period)) {
			order.setPeriod(PeriodEnum.YEAR);
			order.setPeriodValue(year);
		} else if (PeriodEnum.SEASON.code().equalsIgnoreCase(period)) {
			order.setPeriod(PeriodEnum.SEASON);
			order.setPeriodValue(year + season);
		} else if (PeriodEnum.MONTH.code().equalsIgnoreCase(period)) {
			order.setPeriod(PeriodEnum.MONTH);
			order.setPeriodValue(year + StringUtil.alignRight(month, 2, '0'));
		}
		;
		result = budgetServiceClient.save(order);
		if (result != null && result.isSuccess()) {
			addAttachfile(result.getMessage(), request, null, null,
				CommonAttachmentTypeEnum.BUDGET_ATTACH);
		}
		return toJSONResult(result, "预算保存");
	}
}
