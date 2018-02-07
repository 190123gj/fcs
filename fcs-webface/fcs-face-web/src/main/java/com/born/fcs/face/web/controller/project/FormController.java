package com.born.fcs.face.web.controller.project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.bpm.service.client.user.SysOrg;
import com.born.fcs.face.integration.bpm.service.info.UserInfo;
import com.born.fcs.face.integration.bpm.service.order.WorkflowBatchProcessOrder;
import com.born.fcs.face.integration.bpm.service.order.WorkflowBatchProcessTaskOrder;
import com.born.fcs.face.integration.bpm.service.result.WorkflowBatchProcessResult;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.bpm.TaskOpinion;
import com.born.fcs.pm.ws.order.common.CancelFormOrder;
import com.born.fcs.pm.ws.order.common.FormQueryOrder;
import com.born.fcs.pm.ws.order.common.SimpleFormAuditOrder;
import com.born.fcs.pm.ws.order.common.SimpleFormOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.FormService;

@Controller
@RequestMapping("projectMg/form")
public class FormController extends WorkflowBaseController {
	
	@RequestMapping("submit.htm")
	@ResponseBody
	public JSONObject submit(SimpleFormOrder order, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				result.put("success", false);
				result.put("code", "0");
				result.put("message", "您未登陆或登陆已失效");
				return result;
			}
			
			setSessionLocalInfo2Order(order);
			
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (userInfo != null) {
				order.setUserEmail(userInfo.getEmail());
				order.setUserMobile(userInfo.getMoblie());
			}
			
			FormBaseResult submitResult = getSystemMatchedFormService(request).submit(order);
			
			if (sessionLocal != null)
				result.put("cancelAlert", sessionLocal.isCancelAlert());
			
			if (submitResult.isSuccess()) {
				result.put("code", "1");
				result.put("success", true);
				
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
					if (StringUtil.isNotBlank(nextAuditor)) {
						message += "<br> 待处理人：" + nextAuditor + " ";
					}
					if (StringUtil.isNotBlank(nextNode)) {
						message += "[ " + nextNode + " ]";
					}
				}
				result.put("message", message);
				result.put("nextAuditor", nextAuditor);
				result.put("nextNode", nextNode);
				result.put("form", submitResult.getFormInfo().toJson());
			} else {
				result.put("code", "0");
				result.put("success", false);
				result.put("message", submitResult.getMessage());
				result.put("form", submitResult.getFormInfo().toJson());
			}
			
		} catch (Exception e) {
			logger.error("提交表单出错", e);
		}
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
			
			FcsBaseResult cancelResult = getSystemMatchedFormService(request).cancel(order);
			
			if (sessionLocal != null)
				result.put("cancelAlert", sessionLocal.isCancelAlert());
			
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
	
	@RequestMapping("workflow/processs/donext.json")
	public String workflowProcesssDoNext(HttpServletRequest request, HttpServletResponse response,
											Model model) {
		doNext(request, response, TaskOpinion.STATUS_AGREE);
		return null;
	}
	
	@RequestMapping("workflow/processs/dorefuse.json")
	public String workflowProcesssDoFefuse(HttpServletRequest request,
											HttpServletResponse response, Model model) {
		doNext(request, response, TaskOpinion.STATUS_REFUSE);
		return null;
	}
	
	@RequestMapping("workflow/processs/doback.json")
	public String workflowProcesssDoBack(HttpServletRequest request, HttpServletResponse response,
											Model model) {
		doNext(request, response, TaskOpinion.STATUS_REJECT_TOSTART);
		return null;
	}
	
	@RequestMapping("workflow/processs/doGoToBack.json")
	public String workflowProcesssDoGoToBack(HttpServletRequest request,
												HttpServletResponse response, Model model) {
		doNext(request, response, TaskOpinion.STATUS_REJECT_TOSTART);
		return null;
	}
	
	@RequestMapping("workflow/processs/doGoToBackNode.json")
	public String workflowProcesssDoGoToBackNode(HttpServletRequest request,
													HttpServletResponse response, Model model) {
		doNext(request, response, TaskOpinion.STATUS_REJECT_TO_TASK_NODE);
		return null;
	}
	
	@RequestMapping("workflow/processs/doGoToBackNode1.json")
	public String workflowProcesssDoGoToBackNode1(HttpServletRequest request,
													HttpServletResponse response, Model model) {
		doNext(request, response, TaskOpinion.STATUS_REJECT_TO_TASK_NODE_1);
		return null;
	}
	
	@RequestMapping("workflow/processs/doGoToBackNode2.json")
	public String workflowProcesssDoGoToBackNode2(HttpServletRequest request,
													HttpServletResponse response, Model model) {
		doNext(request, response, TaskOpinion.STATUS_REJECT_TO_TASK_NODE_2);
		return null;
	}
	
	@RequestMapping("workflow/processs/doGoToBackNode3.json")
	public String workflowProcesssDoGoToBackNode3(HttpServletRequest request,
													HttpServletResponse response, Model model) {
		doNext(request, response, TaskOpinion.STATUS_REJECT_TO_TASK_NODE_3);
		return null;
	}
	
	@RequestMapping("workflow/processs/doTaskAssign.json")
	public String workflowProcesssDoTaskAss(HttpServletRequest request,
											HttpServletResponse response, Model model) {
		doTaskAssign(request, response);
		return null;
	}
	
	@RequestMapping("workflow/processs/doEndWorkflow.json")
	public String workflowProcesssDoEnd(HttpServletRequest request, HttpServletResponse response,
										Model model) {
		doNext(request, response, TaskOpinion.STATUS_ENDPROCESS);
		return null;
	}
	
	@RequestMapping("end.htm")
	@ResponseBody
	public JSONObject end(long formId, HttpServletRequest request, Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			FormInfo formInfo = null;
			if (formId > 0)
				formInfo = getSystemMatchedFormService(request).findByFormId(formId);
			if (formInfo == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "表单不存在");
				return jsonObject;
			} else if (formInfo.getStatus() != FormStatusEnum.BACK
						&& formInfo.getStatus() != FormStatusEnum.CANCEL) {
				jsonObject.put("success", false);
				jsonObject.put("message", "当前状态不允许作废");
				return jsonObject;
			}
			SimpleFormAuditOrder auditOrder = makeAuditOrder(request, formId, formInfo.getTaskId());
			auditOrder.setVoteAgree(String.valueOf(TaskOpinion.STATUS_ENDPROCESS));
			auditOrder.setNextNodeId("workflowFinished"); //终止
			FcsBaseResult baseResult = getSystemMatchedFormService(request).endWorkflow(auditOrder);
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal != null)
				jsonObject.put("cancelAlert", sessionLocal.isCancelAlert());
			if (baseResult.isSuccess()) {
				jsonObject.put("success", true);
				jsonObject.put("message", "作废成功");
			} else {
				jsonObject.put("success", false);
				jsonObject.put("message", "作废失败[" + baseResult.getMessage() + "]");
			}
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", "作废出错");
		}
		return jsonObject;
	}
	
	/**
	 * 批量审核
	 * @param order
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("batchProcess.json")
	@ResponseBody
	public JSONObject batchProcess(WorkflowBatchProcessOrder order, HttpServletRequest request,
									Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal != null) {
				order.setUserId(sessionLocal.getUserId());
				order.setUserAccount(sessionLocal.getUserName());
				order.setUserName(sessionLocal.getRealName());
				order.setUserIp(sessionLocal.getRemoteAddr());
				
				UserInfo userInfo = sessionLocal.getUserInfo();
				if (userInfo != null) {
					order.setUserMobile(userInfo.getMoblie());
					order.setUserEmail(userInfo.getEmail());
					SysOrg dept = sessionLocal.getUserInfo().getDept();
					if (dept != null) {
						order.setDeptId(dept.getOrgId());
						order.setDeptCode(dept.getCode());
						order.setDeptName(dept.getOrgName());
						order.setDeptPath(dept.getPath());
						order.setDeptPathName(dept.getOrgPathname());
					}
				}
			}
			WorkflowBatchProcessResult result = workflowEngineWebClient.batchProcess(order);
			if (sessionLocal != null)
				jsonObject.put("cancelAlert", sessionLocal.isCancelAlert());
			if (result.isSuccess()) {
				jsonObject.put("success", true);
				jsonObject.put("message", "批量处理成功");
				jsonObject.put("result", result.toJson());
			} else {
				jsonObject.put("success", false);
				jsonObject.put("message", "批量处理失败");
			}
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", "批量处理异常");
		}
		return jsonObject;
	}
	
	/**
	 * 批量处理代办任务
	 * @param order
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("batchProcessTask.json")
	@ResponseBody
	public JSONObject batchProcessTask(WorkflowBatchProcessTaskOrder order,
										HttpServletRequest request, Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal != null) {
				order.setUserId(sessionLocal.getUserId());
				order.setUserAccount(sessionLocal.getUserName());
				order.setUserName(sessionLocal.getRealName());
				order.setUserIp(sessionLocal.getRemoteAddr());
				
				UserInfo userInfo = sessionLocal.getUserInfo();
				if (userInfo != null) {
					order.setUserMobile(userInfo.getMoblie());
					order.setUserEmail(userInfo.getEmail());
					SysOrg dept = sessionLocal.getUserInfo().getDept();
					if (dept != null) {
						order.setDeptId(dept.getOrgId());
						order.setDeptCode(dept.getCode());
						order.setDeptName(dept.getOrgName());
						order.setDeptPath(dept.getPath());
						order.setDeptPathName(dept.getOrgPathname());
					}
				}
			}
			WorkflowBatchProcessResult result = workflowEngineWebClient.batchProcessTask(order);
			if (sessionLocal != null)
				jsonObject.put("cancelAlert", sessionLocal.isCancelAlert());
			if (result.isSuccess()) {
				jsonObject.put("success", true);
				jsonObject.put("message", "批量处理成功");
				jsonObject.put("result", result.toJson());
			} else {
				jsonObject.put("success", false);
				jsonObject.put("message", "批量处理失败");
			}
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", "批量处理异常");
		}
		return jsonObject;
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
			FcsBaseResult cancelResult = getSystemMatchedFormService(request).delete(order);
			if (sessionLocal != null)
				result.put("cancelAlert", sessionLocal.isCancelAlert());
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
	
	/**
	 * 作废并删除
	 * @param order
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("endAndDelete.json")
	@ResponseBody
	public JSONObject endAndDelete(SimpleFormOrder order, HttpServletRequest request, Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				jsonObject.put("code", 0);
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			
			FormInfo formInfo = null;
			long formId = (null == order.getFormId()) ? 0L : order.getFormId();
			if (formId > 0) {
				formInfo = getSystemMatchedFormService(request).findByFormId(formId);
			}
			if (formInfo == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "表单不存在");
				return jsonObject;
			} else if (formInfo.getStatus() == FormStatusEnum.BACK
						|| formInfo.getStatus() == FormStatusEnum.CANCEL) {
				//先作废
				SimpleFormAuditOrder auditOrder = makeAuditOrder(request, formId,
					formInfo.getTaskId());
				auditOrder.setVoteAgree(String.valueOf(TaskOpinion.STATUS_ENDPROCESS));
				auditOrder.setNextNodeId("workflowFinished"); //终止
				FcsBaseResult baseResult = getSystemMatchedFormService(request).endWorkflow(
					auditOrder);
				if (sessionLocal != null) {
					jsonObject.put("cancelAlert", sessionLocal.isCancelAlert());
				}
				if (!baseResult.isSuccess()) {
					//作废失败
					jsonObject.put("success", false);
					jsonObject.put("message", "删除失败[" + baseResult.getMessage() + "]");
					return jsonObject;
				}
			}
			
			setSessionLocalInfo2Order(order);
			//再删除
			FcsBaseResult cancelResult = getSystemMatchedFormService(request).delete(order);
			if (sessionLocal != null) {
				jsonObject.put("cancelAlert", sessionLocal.isCancelAlert());
			}
			if (cancelResult.isSuccess()) {
				jsonObject.put("code", 1);
				jsonObject.put("success", true);
				jsonObject.put("message", "删除成功");
			} else {
				jsonObject.put("code", 0);
				jsonObject.put("success", false);
				jsonObject.put("message", cancelResult.getMessage());
			}
			
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", "删除表单出错");
		}
		return jsonObject;
	}
	
	/**
	 * 根据 actInstId 或者 formId 和 sysName 查看表单
	 * @param actInstId
	 * @param formId
	 * @param sysName
	 * @return
	 */
	@RequestMapping("view.htm")
	public String view(String actInstId, Long formId, String sysName, Model model) {
		
		if (StringUtil.isNotEmpty(sysName) && formId != null && formId > 0) {
			FormService formService = getSystemMatchedFormService(sysName);
			FormInfo formInfo = formService.findByFormId(formId);
			if (formInfo == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
			}
			model.addAttribute("formId", formId);
			return "redirect:" + getRedirectUrl(formInfo.getFormUrl(), model);
		} else if (StringUtil.isNotEmpty(actInstId)) {
			long insId = NumberUtil.parseLong(actInstId);
			if (insId > 0) {
				
				FormQueryOrder queryOrder = new FormQueryOrder();
				queryOrder.setActInstId(insId);
				QueryBaseBatchResult<FormInfo> formResult = formServiceClient.queryPage(queryOrder);
				if (formResult.getTotalCount() == 0) {
					formResult = formServiceFmClient.queryPage(queryOrder);
				}
				if (formResult.getTotalCount() == 0) {
					formResult = formServiceAmClient.queryPage(queryOrder);
				}
				if (formResult.getTotalCount() == 0) {
					formResult = formServiceCrmClient.queryPage(queryOrder);
				}
				if (formResult.getTotalCount() == 0) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
				}
				FormInfo formInfo = formResult.getPageList().get(0);
				model.addAttribute("formId", formInfo.getFormId());
				return "redirect:" + getRedirectUrl(formInfo.getFormUrl(), model);
			}
		} else {
			throw ExceptionFactory.newFcsException(FcsResultEnum.INCOMPLETE_REQ_PARAM, "请求参数不完整");
		}
		return null;
	}
	
	/**
	 * 获取跳转地址
	 * @param url
	 * @param model
	 * @return
	 */
	private String getRedirectUrl(String url, Model model) {
		String module = "projectMg";
		String[] stringSplit = url.split("/");
		if (stringSplit.length > 0)
			module = stringSplit[1];
		model.addAttribute("systemNameDefautUrl", url);
		return "/" + module + "/index.htm";
	}
}
