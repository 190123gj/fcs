package com.born.fcs.face.web.controller.project.riskcontrol;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.CouncilRiskTypeEnum;
import com.born.fcs.pm.ws.enums.CouncilStatusEnum;
import com.born.fcs.pm.ws.info.councilRisk.CouncilRiskInfo;
import com.born.fcs.pm.ws.info.councilRisk.CouncilRiskSummaryInfo;
import com.born.fcs.pm.ws.info.riskHandleTeam.RiskHandleTeamInfo;
import com.born.fcs.pm.ws.order.councilRisk.*;
import com.born.fcs.pm.ws.order.riskHandleTeam.RiskHandlerTeamQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.ArrayUtil;
import com.yjf.common.lang.util.ListUtil;
import org.apache.xmlbeans.impl.common.NameUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by wqh on 2016/9/9.
 */
@Controller
@RequestMapping("projectMg/councilRisk")
public class CouncilRiskControl extends BaseController {
	private static final String VM_PATH = "/projectMg/riskControl/councilRisk/";
	
	@Override
	protected String[] getDateInputNameArray() {
		return new String[] { "beginTime" };
	}
	
	/**
	 * 成立风险处置小组
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model) {
		
		try {
			CouncilRiskQueryOrder queryOrder = new CouncilRiskQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			String councilType = request.getParameter("councilType");
			String councilStatus = request.getParameter("councilStatus");
			queryOrder.setCouncilStatus(CouncilStatusEnum.getByCode(councilStatus));
			queryOrder.setCouncilType(CouncilRiskTypeEnum.getByCode(councilType));
			setSessionLocalInfo2Order(queryOrder);
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			QueryBaseBatchResult<CouncilRiskInfo> batchResult = councilRiskServiceClient
				.queryCouncilRiskInfo(queryOrder);
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (CouncilRiskInfo riskInfo : batchResult.getPageList()) {
					councilRiskServiceClient.findByCouncilId(riskInfo.getCouncilId());
					List<CouncilRiskSummaryInfo> summaryInfos = councilRiskServiceClient
						.findByCouncilId(riskInfo.getCouncilId()).getSummaryInfoList();
					if (ListUtil.isNotEmpty(summaryInfos)) {
						for (CouncilRiskSummaryInfo summaryInfo : summaryInfos) {
							if (StringUtil.isNotEmpty(summaryInfo.getConfirmManIds())) {
								String[] manIds = summaryInfo.getConfirmManIds().split(",");
								if (manIds != null && manIds.length > 0) {
									for (String id : manIds) {
										if (NumberUtil.parseLong(id) == sessionLocal.getUserId()) {
											if (!StringUtil.equalsIgnoreCase(
												riskInfo.getHasConfirm(), "YES")) {
												riskInfo.setHasConfirm("YES");
											}
										}
									}
									
								}
								
							}
						}
					}
				}
			}
			
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("councilType", councilType);
			model.addAttribute("councilStatus", councilStatus);
			model.addAttribute("councilStatusEnum", CouncilStatusEnum.getAllEnum());
			model.addAttribute("councilRiskTypeEnum", CouncilRiskTypeEnum.getAllEnum());
		} catch (Exception e) {
			logger.error("查询出错");
		}
		
		return VM_PATH + "list.vm";
	}
	
	/**
	 * 成立风险处置小组
	 * @param request
	 * @return
	 */
	@RequestMapping("toAdd.htm")
	public String toAdd(HttpServletRequest request, Model model) {
		String councilId = request.getParameter("councilId");
		CouncilRiskInfo riskInfo = null;
		String isEdit = "Y";
		if (StringUtil.isNotEmpty(councilId) && !StringUtil.equalsIgnoreCase(councilId,"0")) {
			riskInfo = councilRiskServiceClient.findByCouncilId(NumberUtil.parseLong(councilId));
			if (riskInfo.getCouncilStatus() != CouncilStatusEnum.NOT_BEGIN) {
				isEdit = "N";
			}
		} else {
			riskInfo = new CouncilRiskInfo();
		}
		model.addAttribute("councilRiskTypeEnum", CouncilRiskTypeEnum.getAllEnum());
		model.addAttribute("isEdit", isEdit);
		model.addAttribute("info", riskInfo);
		return VM_PATH + "add.vm";
	}
	
	/**
	 * 成立风险处置小组
	 * @param request
	 * @return
	 */
	@RequestMapping("info.htm")
	public String info(HttpServletRequest request, Model model) {
		String councilId = request.getParameter("councilId");
		CouncilRiskInfo riskInfo = null;
		String isEdit = "Y";
		if (StringUtil.isNotEmpty(councilId)) {
			riskInfo = councilRiskServiceClient.findByCouncilId(NumberUtil.parseLong(councilId));
			if (riskInfo.getCouncilStatus() != CouncilStatusEnum.NOT_BEGIN) {
				isEdit = "N";
			}
		} else {
			riskInfo = new CouncilRiskInfo();
		}
		model.addAttribute("councilRiskTypeEnum", CouncilRiskTypeEnum.getAllEnum());
		model.addAttribute("isEdit", isEdit);
		model.addAttribute("info", riskInfo);
		return VM_PATH + "info.vm";
	}
	
	/**
	 * 成立风险处置小组
	 * @param request
	 * @return
	 */
	@RequestMapping("queryRiskTeam.json")
	@ResponseBody
	public JSONObject queryRiskTeam(HttpServletRequest request, Model model) {
		
		JSONObject result = new JSONObject();
		RiskHandlerTeamQueryOrder teamQueryOrder = new RiskHandlerTeamQueryOrder();
		teamQueryOrder.setProjectCode(request.getParameter("projectCode"));
		List<RiskHandleTeamInfo> teamInfoList = riskHandleTeamServiceClient.queryRiskHandleTeam(
			teamQueryOrder).getPageList();
		if (ListUtil.isNotEmpty(teamInfoList)) {
			RiskHandleTeamInfo teamInfo = teamInfoList.get(0);
			result.put("memberIds", teamInfo.getMemberIds() + "," + teamInfo.getChiefLeaderId()
									+ "," + teamInfo.getViceLeaderId());
			result.put(
				"memberNames",
				teamInfo.getMemberNames() + "," + teamInfo.getChiefLeaderName() + ","
						+ teamInfo.getViceLeaderName());
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		return result;
	}
	
	/**
	 * 成立风险处置小组
	 * @param request
	 * @return
	 */
	@RequestMapping("toAddSummary.htm")
	public String toAddSummary(HttpServletRequest request, Model model) {
		String councilId = request.getParameter("councilId");
		CouncilRiskInfo riskInfo = councilRiskServiceClient.findByCouncilId(NumberUtil
			.parseLong(councilId));
		String isEdit = "Y";
		if (StringUtil.equals(riskInfo.getIsSummary(), "Y")) {
			isEdit = "N";
		}
		
		model.addAttribute("info", riskInfo);
		return VM_PATH + "addSummary.vm";
	}
	
	/**
	 * 成立风险处置小组
	 * @param request
	 * @return
	 */
	@RequestMapping("toConfirmSummary.htm")
	public String toConfirmSummary(HttpServletRequest request, Model model) {
		String councilId = request.getParameter("councilId");
		CouncilRiskInfo riskInfo = councilRiskServiceClient.findByCouncilIdAndUserId(NumberUtil
			.parseLong(councilId),ShiroSessionUtils.getSessionLocal().getUserId());
		String isEdit = "YES";
		if (StringUtil.equals(riskInfo.getIsConfirm(), "YES")) {
			isEdit = "NO";
		}
		model.addAttribute("isEdit", isEdit);
		model.addAttribute("info", riskInfo);
		if (StringUtil.equalsIgnoreCase("NO", isEdit)) {
			return VM_PATH + "confirmSummaryInfo.vm";
		}
		return VM_PATH + "confirmSummary.vm";
	}


	/**
	 * 成立风险处置小组
	 * @param request
	 * @return
	 */
	@RequestMapping("confirmSummaryInfo.htm")
	public String confirmSummaryInfo(HttpServletRequest request, Model model) {
		String councilId = request.getParameter("councilId");
		CouncilRiskSummaryQueryOrder queryOrder = new CouncilRiskSummaryQueryOrder();
		queryOrder.setCouncilId(NumberUtil.parseLong(councilId));
		String summaryIds = request.getParameter("summaryIds");
		if(StringUtil.isNotEmpty(summaryIds)){
			queryOrder.setSummaryIds(ArrayUtil.toList(summaryIds.split(",")));
		}
		CouncilRiskInfo riskInfo = councilRiskServiceClient.findBySummaryQueryOrder(queryOrder);
		String isEdit = "YES";
		if (StringUtil.equals(riskInfo.getIsConfirm(), "YES")) {
			isEdit = "NO";
		}
		model.addAttribute("isEdit", isEdit);
		model.addAttribute("info", riskInfo);
		return VM_PATH + "confirmSummaryInfo.vm";
	}
	
	/**
	 * 成立风险处置小组
	 * @param request
	 * @return
	 */
	@RequestMapping("summaryInfo.htm")
	public String summaryInfo(HttpServletRequest request, Model model) {
		String councilId = request.getParameter("councilId");
		CouncilRiskInfo riskInfo = councilRiskServiceClient.findByCouncilId(NumberUtil
			.parseLong(councilId));
		model.addAttribute("info", riskInfo);
		String isEdit = "YES";
		if (StringUtil.equals(riskInfo.getIsConfirm(), "YES")) {
			isEdit = "NO";
		}
		model.addAttribute("isEdit", isEdit);
		return VM_PATH + "confirmSummaryInfo.vm";
	}
	
	/**
	 * 保存会议纪要
	 * @param request
	 * @param summaryOrder
	 * @return
	 */
	@RequestMapping("saveSummary.json")
	@ResponseBody
	public JSONObject saveSummary(HttpServletRequest request,
									AddCouncilRiskSummaryOrder summaryOrder) {
		String tipPrefix = " 保存会议纪要";
		JSONObject result = new JSONObject();
		try {
			setSessionLocalInfo2Order(summaryOrder);
			FcsBaseResult saveResult = councilRiskServiceClient.saveSummary(summaryOrder);
			return toJSONResult(saveResult, tipPrefix);
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
	}
	
	/**
	 * 成立风险处置小组
	 * @param request
	 * @param processOrder
	 * @return
	 */
	@RequestMapping("save.json")
	@ResponseBody
	public JSONObject save(HttpServletRequest request, CouncilRiskProcessOrder processOrder) {
		String tipPrefix = " 新增会议";
		JSONObject result = new JSONObject();
		try {
			setSessionLocalInfo2Order(processOrder);
			FcsBaseResult saveResult = councilRiskServiceClient.save(processOrder);
			return toJSONResult(saveResult, tipPrefix);
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
	}
	
	/**
	 * 成立风险处置小组
	 * @param request
	 * @param processOrder
	 * @return
	 */
	@RequestMapping("end.json")
	@ResponseBody
	public JSONObject end(HttpServletRequest request, EndCouncilRiskProcessOrder processOrder) {
		String tipPrefix = " 结束会议";
		JSONObject result = new JSONObject();
		try {
			setSessionLocalInfo2Order(processOrder);
			FcsBaseResult saveResult = councilRiskServiceClient.endCouncilRisk(processOrder);
			return toJSONResult(saveResult, tipPrefix);
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
	}
}
