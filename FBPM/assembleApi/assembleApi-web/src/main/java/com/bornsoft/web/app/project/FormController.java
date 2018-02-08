package com.bornsoft.web.app.project;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.bpm.service.info.UserInfo;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectInfo;
import com.born.fcs.pm.ws.order.bpm.TaskOpinion;
import com.born.fcs.pm.ws.order.common.CancelFormOrder;
import com.born.fcs.pm.ws.order.common.SimpleFormOrder;
import com.born.fcs.pm.ws.order.council.CouncilSummarySubmitOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.bornsoft.web.app.base.WorkflowBaseController;
import com.yjf.common.lang.util.ListUtil;

@Controller
@RequestMapping("projectMg/form")
public class FormController extends WorkflowBaseController {
	
	@RequestMapping("submit.json")
	@ResponseBody
	public JSONObject submit(SimpleFormOrder order, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				toJSONResult(result, AppResultCodeEnum.FAILED, "登录已失效");
				return result;
			}
			//module参数校验
			if (!validateModule(request)) {
				toJSONResult(result, AppResultCodeEnum.FAILED, "module参数非法");
				return result;
			}
			setSessionLocalInfo2Order(order);
			
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (userInfo != null) {
				order.setUserEmail(userInfo.getEmail());
				order.setUserMobile(userInfo.getMoblie());
			}
			
			FormBaseResult submitResult = jckFormService.getSystemMatchedFormService(request)
				.submit(order);
			
			if (submitResult.isSuccess()) {
				toJSONResult(result, AppResultCodeEnum.SUCCESS, "提交成功");
				
				String nextAuditor = "";
				String nextNode = "";
				String nextInfo = submitResult.getUrl();
				if (StringUtil.isNotBlank(nextInfo)) {
					String[] next = nextInfo.split(";");
					if (next.length > 0)
						nextNode = next[0];
					if (next.length > 1)
						nextAuditor = next[1];
				}
				
				String message = "提交成功 ";
				if ("FLOW_FINISH".equals(nextNode)) {
					message = "流程处理完成";
				} else {
					if (StringUtil.isNotBlank(nextNode)) {
						message += "[ " + nextNode + " ]";
					}
					if (StringUtil.isNotBlank(nextAuditor)) {
						message += "[ 待执行人：" + nextAuditor + " ]";
					}
				}
				result.put("message", message);
				result.put("nextAuditor", nextAuditor);
				result.put("nextNode", nextNode);
				
				result.put("form", submitResult.getFormInfo().toJson());
			} else {
				toJSONResult(result, AppResultCodeEnum.FAILED, submitResult.getMessage());
				result.put("form", submitResult.getFormInfo());
			}
			
		} catch (Exception e) {
			logger.error("提交表单出错", e);
		}
		logger.info("提交,出参={}", result);
		return result;
	}
	
	@RequestMapping("cancel.htm")
	@ResponseBody
	public JSONObject cancel(CancelFormOrder order, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				result.put("success", false);
				result.put("message", "您未登陆或登陆已失效");
				return result;
			}
			
			setSessionLocalInfo2Order(order);
			
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (userInfo != null) {
				order.setUserEmail(userInfo.getEmail());
				order.setUserMobile(userInfo.getMoblie());
			}
			
			FcsBaseResult cancelResult = jckFormService.getSystemMatchedFormService(request)
				.cancel(order);
			if (cancelResult.isSuccess()) {
				result.put("code", "1");
				result.put("success", true);
				result.put("message", "撤回成功");
			} else {
				result.put("code", "0");
				result.put("success", false);
				result.put("message", cancelResult.getMessage());
			}
			
		} catch (Exception e) {
			logger.error("撤回表单出错", e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("workflow/processs/donext.json")
	public JSONObject workflowProcesssDoNext(HttpServletRequest request,
												HttpServletResponse response, Model model) {
		return doNext(request, response, TaskOpinion.STATUS_AGREE);
	}
	
	@ResponseBody
	@RequestMapping("meetingMg/summary/submit.json")
	public JSONObject meetingMgSummarySubmit(HttpServletRequest request,
												HttpServletResponse response, Model model) {
		CouncilSummarySubmitOrder order = new CouncilSummarySubmitOrder();
		
		JSONObject jsonObject = new JSONObject();
		try {
			order.setSummaryId(NumberUtil.parseLong(request.getParameter("summaryId")));
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			
			if (order.getSummaryId() <= 0) {
				jsonObject.put("success", false);
				jsonObject.put("message", "会议纪要ID参数异常");
				return jsonObject;
			}
			
			List<FCouncilSummaryProjectInfo> projects = councilSummaryServiceClient
				.queryProjectCsBySummaryId(order.getSummaryId());
			
			if (ListUtil.isEmpty(projects)) {
				jsonObject.put("success", false);
				jsonObject.put("message", "会议纪要项目查询异常");
				return jsonObject;
			}
			
			//是否全部发表意见（投票通过的才需要发表意见）
			boolean allComment = true;
			for (FCouncilSummaryProjectInfo project : projects) {
				if (project.getVoteResult() == ProjectVoteResultEnum.END_PASS
					&& project.getOneVoteDown() == null) {
					allComment = false;
					break;
				}
			}
			
			if (allComment) {//全部发表意见就走流程
			
				return doNext(request, response, TaskOpinion.STATUS_AGREE);
			} else {//没全部发表意见流程依然挂起
				setSessionLocalInfo2Order(order);
				FcsBaseResult result = councilSummaryServiceClient.submitSummary(order);
				jsonObject = toJSONResult(jsonObject, result, "处理成功", null);
			}
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("董事长提交出错", e);
		}
		return jsonObject;
	}
	
	@ResponseBody
	@RequestMapping("workflow/processs/dorefuse.json")
	public JSONObject workflowProcesssDoFefuse(HttpServletRequest request,
												HttpServletResponse response, Model model) {
		return doNext(request, response, TaskOpinion.STATUS_REFUSE);
	}
	
	@ResponseBody
	@RequestMapping("workflow/processs/doback.json")
	public JSONObject workflowProcesssDoBack(HttpServletRequest request,
												HttpServletResponse response, Model model) {
		return doNext(request, response, TaskOpinion.STATUS_REJECT_TOSTART);
	}
	
	@ResponseBody
	@RequestMapping("workflow/processs/doGoToBack.json")
	public JSONObject workflowProcesssDoGoToBack(HttpServletRequest request,
													HttpServletResponse response, Model model) {
		return doNext(request, response, TaskOpinion.STATUS_REJECT_TOSTART);
	}
	
	@ResponseBody
	@RequestMapping("workflow/processs/doGoToBackNode.json")
	public JSONObject workflowProcesssDoGoToBackNode(HttpServletRequest request,
														HttpServletResponse response, Model model) {
		return doNext(request, response, TaskOpinion.STATUS_REJECT_TO_TASK_NODE);
	}
	
	@ResponseBody
	@RequestMapping("workflow/processs/doTaskAssign.json")
	public JSONObject workflowProcesssDoTaskAss(HttpServletRequest request,
												HttpServletResponse response, Model model) {
		return doTaskAssign(request, response);
	}
	
	@ResponseBody
	@RequestMapping("workflow/processs/doEndWorkflow.json")
	public JSONObject workflowProcesssDoEnd(HttpServletRequest request,
											HttpServletResponse response, Model model) {
		return doNext(request, response, TaskOpinion.STATUS_ENDPROCESS);
	}
	
	@RequestMapping("delete.htm")
	@ResponseBody
	public JSONObject delete(SimpleFormOrder order, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				result.put("code", 0);
				result.put("success", false);
				result.put("message", "您未登陆或登陆已失效");
				return result;
			}
			setSessionLocalInfo2Order(order);
			FcsBaseResult cancelResult = jckFormService.getSystemMatchedFormService(request)
				.delete(order);
			if (cancelResult.isSuccess()) {
				result.put("code", 1);
				result.put("success", true);
				result.put("message", "删除成功");
			} else {
				result.put("code", 0);
				result.put("success", false);
				result.put("message", cancelResult.getMessage());
			}
			
		} catch (Exception e) {
			logger.error("删除表单出错", e);
		}
		return result;
	}
}
