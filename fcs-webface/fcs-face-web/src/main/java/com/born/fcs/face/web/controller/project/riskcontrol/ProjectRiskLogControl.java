package com.born.fcs.face.web.controller.project.riskcontrol;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.info.projectRiskLog.ProjectRiskLogInfo;
import com.born.fcs.pm.ws.order.projectRiskLog.ProjectRiskLogProcessOrder;
import com.born.fcs.pm.ws.order.projectRiskLog.ProjectRiskLogQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.StringUtil;

/**
 * Created by wqh on 2016/9/19.
 */
@Controller
@RequestMapping("projectMg/projectRiskLog")
public class ProjectRiskLogControl extends BaseController {
	private static final String VM_PATH = "/projectMg/riskControl/projectRiskLog/";
	
	@Override
	protected String[] getDateInputNameArray() {
		return new String[] { "startTimeBegin", "startTimeEnd", "occurTime", "occurTimeEnd",
								"occurTimeBegin" };
	}
	
	/**
	 * 成立风险处置小组
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model, ProjectRiskLogQueryOrder queryOrder) {
		
		try {
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<ProjectRiskLogInfo> batchResult = projectRiskLogClient
				.queryProjectRiskLogInfo(queryOrder);
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
		String logId = request.getParameter("logId");
		ProjectRiskLogInfo teamInfo = null;
		if (StringUtil.isNotEmpty(logId)) {
			teamInfo = projectRiskLogClient.findByLogId(NumberUtil.parseLong(logId));
			//附件列表
			queryCommonAttachmentData(model, teamInfo.getLogId() + "",
				CommonAttachmentTypeEnum.PROJECT_RISK_LOG);
		} else {
			teamInfo = new ProjectRiskLogInfo();
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
		String logId = request.getParameter("logId");
		ProjectRiskLogInfo teamInfo = null;
		if (StringUtil.isNotEmpty(logId)) {
			teamInfo = projectRiskLogClient.findByLogId(NumberUtil.parseLong(logId));
			//附件列表
			queryCommonAttachmentData(model, teamInfo.getLogId() + "",
				CommonAttachmentTypeEnum.PROJECT_RISK_LOG);
		} else {
			teamInfo = new ProjectRiskLogInfo();
		}
		model.addAttribute("info", teamInfo);
		return VM_PATH + "add.vm";
	}
	
	/**
	 * 成立风险处置小组
	 * @param request
	 * @param processOrder
	 * @return
	 */
	@RequestMapping("save.json")
	@ResponseBody
	public JSONObject save(HttpServletRequest request, ProjectRiskLogProcessOrder processOrder) {
		String tipPrefix = " 新增日志";
		JSONObject result = new JSONObject();
		try {
			setSessionLocalInfo2Order(processOrder);
			FcsBaseResult saveResult = projectRiskLogClient.save(processOrder);
			if (saveResult.isSuccess()) {
				long keyId = saveResult.getKeyId();
				//添加附件
				addAttachfile(keyId + "", request, processOrder.getProjectCode(), null,
					CommonAttachmentTypeEnum.PROJECT_RISK_LOG);
			}
			
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
	 * @return
	 */
	@RequestMapping("delete.json")
	@ResponseBody
	public JSONObject delete(HttpServletRequest request) {
		String tipPrefix = " 删除日志";
		JSONObject result = new JSONObject();
		try {
			String logId = request.getParameter("logId");
			FcsBaseResult baseResult = projectRiskLogClient.deleteByLogId(NumberUtil
				.parseLong(logId));
			return toJSONResult(baseResult, tipPrefix);
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
	}
}
