package com.born.fcs.face.web.controller.fund.incomeconfirm;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.fm.ws.info.incomeconfirm.IncomeConfirmDetailInfo;
import com.born.fcs.fm.ws.info.incomeconfirm.IncomeConfirmDetailListInfo;
import com.born.fcs.fm.ws.info.incomeconfirm.IncomeConfirmInfo;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeBatchConfirmOrder;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeConfirmDetailListQueryOrder;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeConfirmDetailQueryOrder;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeConfirmOrder;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeConfirmQueryOrder;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.ProjectReportService;
import com.born.fcs.pm.ws.service.report.info.ProjectChargeDetailInfo;
import com.born.fcs.pm.ws.service.report.order.ProjectChargeDetailQueryOrder;
import com.yjf.common.lang.util.ListUtil;

@Controller
@RequestMapping("fundMg/incomeConfirm")
public class IncomeConfirmController extends WorkflowBaseController {
	
	private static final String vm_path = "/fundMg/fundMgModule/incomeAffirm/";
	@Autowired
	ProjectReportService projectReportServiceClient;
	
	/**
	 * 列表查询
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(IncomeConfirmQueryOrder order, Model model) {
		
		setSessionLocalInfo2Order(order);
		if (order.getIncomePeriod() != null) {
			order.setIncomePeriod(order.getIncomePeriod().replace("-", "年") + "月");
		}
		QueryBaseBatchResult<IncomeConfirmInfo> batchResult = incomeConfirmServiceClient
			.query(order);
		model.addAttribute("isFinancialYSG", isFinancialYSG());//财务应收岗
		if (order.getIncomePeriod() != null) {
			order.setIncomePeriod(order.getIncomePeriod().replace("年", "-").replace("月", ""));
		}
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		return vm_path + "list.vm";
	}
	
	/**
	 * 批量确认列表
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("batchList.htm")
	public String batchList(IncomeConfirmDetailListQueryOrder order, HttpServletRequest request,
							Model model) {
		
		setSessionLocalInfo2Order(order);
		
		//默认上月
		if (order.getIncomePeriod() == null
			&& !StringUtil.equals(request.getParameter("from"), "query")) {
			order.setIncomePeriod(DateUtil.format(DateUtil.getDateByMonth(new Date(), -1),
				"yyyy-MM"));
		}
		
		if (order.getIncomePeriod() != null) {
			order.setIncomePeriod(order.getIncomePeriod().replace("-", "年") + "月");
		}
		QueryBaseBatchResult<IncomeConfirmDetailListInfo> batchResult = incomeConfirmServiceClient
			.queryBatchConfirmList(order);
		model.addAttribute("isFinancialYSG", isFinancialYSG());//财务应收岗
		if (order.getIncomePeriod() != null) {
			order.setIncomePeriod(order.getIncomePeriod().replace("年", "-").replace("月", ""));
		}
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		return vm_path + "batchList.vm";
	}
	
	/**
	 * 查看
	 * @param incomeId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("view.htm")
	public String view(long incomeId, HttpServletRequest request, Model model) {
		IncomeConfirmInfo incomeConfirmInfo = incomeConfirmServiceClient.findById(incomeId);
		model.addAttribute("incomeConfirmInfo", incomeConfirmInfo);
		model.addAttribute("incomeConfirmDetailInfo",
			incomeConfirmInfo.getIncomeConfirmDetailInfos());
		if (incomeConfirmInfo != null) {
			model.addAttribute("feeList", queryFeeList(incomeConfirmInfo.getProjectCode()));//收费明细
		}
		return vm_path + "view.vm";
	}
	
	/**
	 * 编辑
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("edit.htm")
	public String edit(long incomeId, HttpServletRequest request, Model model) {
		IncomeConfirmInfo incomeConfirmInfo = incomeConfirmServiceClient.findById(incomeId);
		model.addAttribute("incomeConfirmInfo", incomeConfirmInfo);
		model.addAttribute("incomeConfirmDetailInfo",
			incomeConfirmInfo.getIncomeConfirmDetailInfos());
		if (incomeConfirmInfo != null) {
			model.addAttribute("feeList", queryFeeList(incomeConfirmInfo.getProjectCode()));//收费明细
		}
		//		//更新系统预估金额
		//		incomeConfirmServiceClient.updateIncomeConfirmDetail(incomeConfirmInfo.getProjectCode(),
		//			incomeId);
		return vm_path + "detail.vm";
	}
	
	/**
	 * 保存
	 * @return
	 */
	@RequestMapping("save.htm")
	@ResponseBody
	public JSONObject save(IncomeConfirmOrder order, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			setSessionLocalInfo2Order(order);
			order.setCheckIndex(0);
			order.setCheckStatus(0);
			FcsBaseResult sResult = incomeConfirmServiceClient.save(order);
			toJSONResult(result, sResult, "保存成功", null);
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "保存收入确认出错");
			logger.error("保存收入确认出错 {}", e);
		}
		return result;
	}
	
	/**
	 * 保存
	 * @return
	 */
	@RequestMapping("batchConfirm.json")
	@ResponseBody
	public JSONObject batchConfirm(IncomeBatchConfirmOrder order, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			setSessionLocalInfo2Order(order);
			FcsBaseResult sResult = incomeConfirmServiceClient.batchConfirm(order);
			toJSONResult(result, sResult, null, null);
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "批量确认收入出错");
			logger.error("批量确认收入出错 {}", e);
		}
		return result;
	}
	
	/**
	 * 收入确认-明细排序
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("detailSorts.htm")
	@ResponseBody
	public JSONObject detailSorts(IncomeConfirmDetailQueryOrder order, Model model) {
		JSONObject result = new JSONObject();
		try {
			order.setPageSize(999L);
			QueryBaseBatchResult<IncomeConfirmDetailInfo> batchResult = incomeConfirmServiceClient
				.queryDetail(order);
			
			List<IncomeConfirmDetailInfo> dataList = null;
			if (batchResult != null && batchResult.isSuccess()) {
				dataList = batchResult.getPageList();
			}
			List<JSONObject> incomeConfirmDetailInfo = Lists.newArrayList();
			if (dataList != null && dataList.size() > 0) {
				for (IncomeConfirmDetailInfo incomeConfirmDetailInfo2 : dataList) {
					JSONObject result1 = new JSONObject();
					result1.put("id", incomeConfirmDetailInfo2.getId());
					result1.put("incomePeriod", incomeConfirmDetailInfo2.getIncomePeriod());
					result1.put("confirmStatus", incomeConfirmDetailInfo2.getConfirmStatus()
						.message());
					result1.put("incomeConfirmedAmount", incomeConfirmDetailInfo2
						.getIncomeConfirmedAmount().getAmount());
					result1.put("systemEstimatedAmount", incomeConfirmDetailInfo2
						.getSystemEstimatedAmount().getAmount());
					result1.put("isConfirmed", incomeConfirmDetailInfo2.getIsConfirmed());
					
					incomeConfirmDetailInfo.add(result1);
				}
			}
			result.put("incomeConfirmDetailInfo", incomeConfirmDetailInfo);
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "保存收入确认出错");
			logger.error("收入确认-明细排序出错 {}", e);
		}
		return result;
	}
	
	public List<ProjectChargeDetailInfo> queryFeeList(String projectCode) {
		List<ProjectChargeDetailInfo> listChargeDetail = Lists.newArrayList();
		ProjectChargeDetailQueryOrder chargeOrder = new ProjectChargeDetailQueryOrder();
		chargeOrder.setProjectCode(projectCode);
		QueryBaseBatchResult<ProjectChargeDetailInfo> chargeDetail = projectReportServiceClient
			.projectChargeDetail(chargeOrder);
		if (chargeDetail != null && ListUtil.isNotEmpty(chargeDetail.getPageList())) {
			listChargeDetail = chargeDetail.getPageList();
		}
		
		return listChargeDetail;
	}
}
