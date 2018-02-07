package com.born.fcs.face.web.controller.project.riskcontrol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.riskHandleTeam.RiskHandleTeamInfo;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.riskHandleTeam.RiskHandleTeamProcessOrder;
import com.born.fcs.pm.ws.order.riskHandleTeam.RiskHandlerTeamQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.ListUtil;

/**
 * Created by wqh on 2016/9/7.
 */
@Controller
@RequestMapping("projectMg/riskHandleTeam")
public class RiskHandleTeamController extends BaseController {
	
	private static final String VM_PATH = "/projectMg/riskControl/riskHandleTeam/";
	
	@Override
	protected String[] getDateInputNameArray() {
		return new String[] { "startTimeBegin", "startTimeEnd" };
	}
	
	/**
	 * 成立风险处置小组
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model, RiskHandlerTeamQueryOrder queryOrder) {
		
		try {
			setSessionLocalInfo2Order(queryOrder);
			
			// 风控委秘书，拥有全部查看权限
			if (DataPermissionUtil.isRiskSecretary()) {
				queryOrder.setLoginUserId(0);
			}
			
			QueryBaseBatchResult<RiskHandleTeamInfo> batchResult = riskHandleTeamServiceClient
				.queryRiskHandleTeam(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
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
	@RequestMapping("info.htm")
	public String info(HttpServletRequest request, Model model) {
		String teamId = request.getParameter("teamId");
		RiskHandleTeamInfo teamInfo = null;
		if (StringUtil.isNotEmpty(teamId)) {
			teamInfo = riskHandleTeamServiceClient.findByTeamId(NumberUtil.parseLong(teamId));
		} else {
			teamInfo = new RiskHandleTeamInfo();
		}
		model.addAttribute("info", teamInfo);
		return VM_PATH + "info.vm";
	}
	
	/**
	 * 成立风险处置小组
	 * @param request
	 * @return
	 */
	@RequestMapping("toAdd.htm")
	public String toAdd(HttpServletRequest request, Model model) {
		String teamId = request.getParameter("teamId");
		RiskHandleTeamInfo teamInfo = null;
		if (StringUtil.isNotEmpty(teamId)) {
			teamInfo = riskHandleTeamServiceClient.findByTeamId(NumberUtil.parseLong(teamId));
		} else {
			teamInfo = new RiskHandleTeamInfo();
		}
		model.addAttribute("info", teamInfo);
		return VM_PATH + "add.vm";
	}
	
	/**
	 * 成立风险处置小组
	 * @param request
	 * @return
	 */
	@RequestMapping("queryTeamInfo.json")
	@ResponseBody
	public JSONObject queryTeamInfo(HttpServletRequest request, Model model) {
		String projectCode = request.getParameter("projectCode");
		String deptCode = request.getParameter("deptCode");
		JSONObject result = new JSONObject();
		RiskHandleTeamInfo teamInfo = null;
		List<SimpleUserInfo> simpleUserInfoList = projectRelatedUserServiceClient.getFgfz(deptCode);
		if (ListUtil.isNotEmpty(simpleUserInfoList)) {
			result.put("viceLeaderId", simpleUserInfoList.get(0).getUserId());
			result.put("viceLeaderName", simpleUserInfoList.get(0).getUserName());
		}
		
		List<ProjectRelatedUserInfo> list = new ArrayList<ProjectRelatedUserInfo>();
		ProjectRelatedUserInfo userInfoA = projectRelatedUserServiceClient
			.getBusiManager(projectCode);
		list.add(userInfoA);
		ProjectRelatedUserInfo userInfoB = projectRelatedUserServiceClient
			.getBusiManagerb(projectCode);
		list.add(userInfoB);
		ProjectRelatedUserInfo userInfoC = projectRelatedUserServiceClient
			.getRiskManager(projectCode);
		list.add(userInfoC);
		
		ProjectRelatedUserInfo userInfoD = projectRelatedUserServiceClient
			.getLegalManager(projectCode);
		list.add(userInfoD);
		StringBuilder memberIds = new StringBuilder();
		StringBuilder memberNames = new StringBuilder();
		
		boolean isFirst = true;
		Map<Long, ProjectRelatedUserInfo> existMap = new HashMap<Long, ProjectRelatedUserInfo>();
		for (ProjectRelatedUserInfo userInfo : list) {
			if (userInfo == null) {
				continue;
			}
			if (existMap.get(userInfo.getUserId()) != null) {
				continue;
			}
			existMap.put(userInfo.getUserId(), userInfo);
			if (isFirst) {
				memberIds.append(userInfo.getUserId());
				memberNames.append(userInfo.getUserName());
			} else {
				memberIds.append(",").append(userInfo.getUserId());
				memberNames.append(",").append(userInfo.getUserName());
			}
			isFirst = false;
		}
		result.put("memberIds", memberIds);
		result.put("memberNames", memberNames);
		result.put("success", true);
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
	public JSONObject save(HttpServletRequest request, RiskHandleTeamProcessOrder processOrder) {
		String tipPrefix = " 新增风险处置小组";
		JSONObject result = new JSONObject();
		try {
			setSessionLocalInfo2Order(processOrder);
			FcsBaseResult saveResult = riskHandleTeamServiceClient.save(processOrder);
			return toJSONResult(saveResult, tipPrefix);
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
	}
}
