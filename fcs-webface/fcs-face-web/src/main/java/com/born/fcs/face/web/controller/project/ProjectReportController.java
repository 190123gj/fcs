package com.born.fcs.face.web.controller.project;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.ProjectReportService;
import com.born.fcs.pm.ws.service.report.info.ProjectChargeDetailInfo;
import com.born.fcs.pm.ws.service.report.info.ProjectPayDetailInfo;
import com.born.fcs.pm.ws.service.report.info.ProjectRepayDetailInfo;
import com.born.fcs.pm.ws.service.report.order.ProjectChargeDetailQueryOrder;
import com.born.fcs.pm.ws.service.report.order.ProjectPayDetailQueryOrder;
import com.born.fcs.pm.ws.service.report.order.ProjectRepayDetailQueryOrder;

@Controller
@RequestMapping("projectMg/report")
public class ProjectReportController extends WorkflowBaseController {
	
	final static String vm_path = "/projectMg/report/";
	
	@Autowired
	ProjectReportService projectReportServiceClient;
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "chargeTimeStart", "chargeTimeEnd", "payTimeStart", "payTimeEnd" };
	}
	
	@RequestMapping("chargeDetail.htm")
	public String chargeDetail(HttpServletRequest request, Model model) {
		ProjectChargeDetailQueryOrder order = new ProjectChargeDetailQueryOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		setSessionLocalInfo2Order(order);
		model.addAttribute("queryOrder", order);
		model.addAttribute("busiTypeName", request.getParameter("busiTypeName"));
		QueryBaseBatchResult<ProjectChargeDetailInfo> chargeResult = projectReportServiceClient
			.projectChargeDetail(order);
		if (chargeResult != null && chargeResult.isSuccess())
			model.addAttribute("page", PageUtil.getCovertPage(chargeResult));
		return vm_path + "chargeDetail.vm";
	}
	
	@RequestMapping("payDetail.htm")
	public String payDetail(HttpServletRequest request, Model model) {
		ProjectPayDetailQueryOrder order = new ProjectPayDetailQueryOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		setSessionLocalInfo2Order(order);
		model.addAttribute("queryOrder", order);
		model.addAttribute("busiTypeName", request.getParameter("busiTypeName"));
		QueryBaseBatchResult<ProjectPayDetailInfo> chargeResult = projectReportServiceClient
			.projectPayDetail(order);
		if (chargeResult != null && chargeResult.isSuccess())
			model.addAttribute("page", PageUtil.getCovertPage(chargeResult));
		return vm_path + "payDetail.vm";
	}
	
	@RequestMapping("repayDetail.htm")
	public String repayDetail(HttpServletRequest request, Model model) {
		ProjectRepayDetailQueryOrder order = new ProjectRepayDetailQueryOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		setSessionLocalInfo2Order(order);
		model.addAttribute("queryOrder", order);
		model.addAttribute("busiTypeName", request.getParameter("busiTypeName"));
		QueryBaseBatchResult<ProjectRepayDetailInfo> chargeResult = projectReportServiceClient
			.projectRepayDetail(order);
		if (chargeResult != null && chargeResult.isSuccess())
			model.addAttribute("page", PageUtil.getCovertPage(chargeResult));
		return vm_path + "repayDetail.vm";
	}
	
}
