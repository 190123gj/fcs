package com.born.fcs.face.web.controller.main;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.bpm.service.WorkflowEngineWebClient;
import com.born.fcs.face.integration.bpm.service.info.BpmFinishTaskInfo;
import com.born.fcs.face.integration.bpm.service.info.TaskTypeViewInfo;
import com.born.fcs.face.integration.bpm.service.order.TaskSearchOrder;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.CouncilStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.council.CouncilInfo;
import com.born.fcs.pm.ws.order.bpm.WorkflowTaskInfo;
import com.born.fcs.pm.ws.order.common.FormQueryOrder;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.council.CouncilQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.common.ProjectBatchResult;
import com.yjf.common.lang.util.ListUtil;

@Controller
@RequestMapping("userHome")
public class UserMainPageController extends BaseController {
	
	final static String vm_path = "/mainPage/";
	
	@Autowired
	WorkflowEngineWebClient workflowEngineWebClient;
	
	/**
	 * 工作台、首页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("mainIndex.htm")
	public String mainIndex(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		//明细代办任务
		//taskList(null, model);
		
		// 客户经理以外 不能操作
		if (DataPermissionUtil.isBusiManager()) {
			model.addAttribute("isBusiManager", "yes");
		}
		
		// 管理人员 公司领导 展示图表查询
		if (DataPermissionUtil.isSystemAdministrator() || DataPermissionUtil.isCompanyLeader()) {
			ProjectBatchResult result = projectServiceClient.getMainCountMessage();
			if (result.isExecuted() && result.isSuccess()) {
				model.addAttribute("projectInfos", result.getProjectInfos());
				if (result.getProjectInfos() != null) {
					JSONArray jsons = new JSONArray();
					for (ProjectInfo info : result.getProjectInfos()) {
						JSONObject json = new JSONObject();
						json.put("phases", info.getPhases().code());
						json.put("phasesMessage", info.getPhases().message());
						json.put("count", info.getCount());
						jsons.add(json);
					}
					model.addAttribute("countJson", jsons.toJSONString().replaceAll("\"", "'"));
				}
			}
			
			//查询客户统计
			FcsBaseResult deptCustomerResult = customerServiceClient.statisticsCustomerDept();
			if (deptCustomerResult != null && deptCustomerResult.isSuccess()) {
				model.addAttribute("customerDeptJson", deptCustomerResult.getMessage());
			}
			FcsBaseResult regionCustomerResult = customerServiceClient.statisticsCustomerRegion();
			if (regionCustomerResult != null && regionCustomerResult.isSuccess()) {
				model.addAttribute("customerRegionJson", regionCustomerResult.getMessage());
			}
		} else {
			
			// 其他人展示客户经理查询
			ProjectQueryOrder projectQueryOrder = new ProjectQueryOrder();
			myProjectList(projectQueryOrder, model);
			//我参与的
			relatedProjectList(projectQueryOrder, model);
		}
		return vm_path + "mainIndex.vm";
	}
	
	/**
	 * 异步请求任务
	 * @param model
	 * @return
	 */
	@RequestMapping("ansycTask.htm")
	public String ansycTask(HttpServletRequest request, Model model) {
		
		//设置
		setTask(null, model);
		
		//代办任务分类 异步请求
		//taskGroup(null, model);
		
		//已办任务 异步请求
		//doneTaskList(null, model);
		
		return vm_path + "ansycTask.vm";
	}
	
	/**
	 * 驳回的单据
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("backTaskList.htm")
	public String backTaskList(HttpServletRequest request, Model model) {
		
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		//被驳回的任务
		List<WorkflowTaskInfo> backTaskList = Lists.newArrayList();
		
		//项目管理被驳回单据
		List<WorkflowTaskInfo> pmList = formServiceClient.backTaskList(sessionLocal.getUserId());
		if (ListUtil.isNotEmpty(pmList))
			backTaskList.addAll(pmList);
		//资金管理被驳回的单据
		List<WorkflowTaskInfo> fmList = formServiceFmClient.backTaskList(sessionLocal.getUserId());
		if (ListUtil.isNotEmpty(fmList))
			backTaskList.addAll(fmList);
		//资产管理被驳回的单据
		List<WorkflowTaskInfo> amList = formServiceAmClient.backTaskList(sessionLocal.getUserId());
		if (ListUtil.isNotEmpty(amList))
			backTaskList.addAll(amList);
		//客户管理被驳回的单据
		List<WorkflowTaskInfo> crmList = formServiceCrmClient
			.backTaskList(sessionLocal.getUserId());
		if (ListUtil.isNotEmpty(crmList))
			backTaskList.addAll(crmList);
		
		//排序
		Collections.sort(backTaskList, new Comparator<WorkflowTaskInfo>() {
			@Override
			public int compare(WorkflowTaskInfo o1, WorkflowTaskInfo o2) {
				if (o1.getCreateTime() != null && o2.getCreateTime() != null)
					return o2.getCreateTime().compareTo(o1.getCreateTime());
				return 0;
			}
		});
		
		model.addAttribute("backTaskList", backTaskList);
		
		return vm_path + "backTaskList.vm";
	}
	
	//我参与的
	@RequestMapping("myProjectList.htm")
	public String myProjectList(ProjectQueryOrder projectQueryOrder, Model model) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		projectQueryOrder.setSortCol("phases");
		projectQueryOrder.setSortOrder("asc");
		List<ProjectStatusEnum> statusList = Lists.newArrayList();
		statusList.add(ProjectStatusEnum.NORMAL);
		statusList.add(ProjectStatusEnum.RECOVERY);
		//statusList.add(ProjectStatusEnum.PAUSE);
		statusList.add(ProjectStatusEnum.TRANSFERRED);
		statusList.add(ProjectStatusEnum.SELL_FINISH);
		statusList.add(ProjectStatusEnum.EXPIRE);
		statusList.add(ProjectStatusEnum.OVERDUE);
		projectQueryOrder.setStatusList(statusList);
		projectQueryOrder.setPageSize(5L);
		projectQueryOrder.setBusiManagerId(sessionLocal.getUserId());
		QueryBaseBatchResult<ProjectInfo> pList = projectServiceClient
			.queryProject(projectQueryOrder);
		//我发起的
		model.addAttribute("myProject", PageUtil.getCovertPage(pList));
		return vm_path + "myProjectList.vm";
	}
	
	//和我相关的
	@RequestMapping("relatedProjectList.htm")
	public String relatedProjectList(ProjectQueryOrder projectQueryOrder, Model model) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		projectQueryOrder.setSortCol("project_id");
		projectQueryOrder.setSortOrder("DESC");
		List<ProjectStatusEnum> statusList = Lists.newArrayList();
		statusList.add(ProjectStatusEnum.NORMAL);
		statusList.add(ProjectStatusEnum.RECOVERY);
		//		statusList.add(ProjectStatusEnum.PAUSE);
		statusList.add(ProjectStatusEnum.TRANSFERRED);
		statusList.add(ProjectStatusEnum.SELL_FINISH);
		statusList.add(ProjectStatusEnum.EXPIRE);
		statusList.add(ProjectStatusEnum.OVERDUE);
		projectQueryOrder.setPageSize(5L);
		projectQueryOrder.setBusiManagerId(0);
		projectQueryOrder.setStatusList(statusList);
		projectQueryOrder.setLoginUserId(sessionLocal.getUserId());
		List<ProjectRelatedUserTypeEnum> relatedRoleList = Lists.newArrayList();
		relatedRoleList.add(ProjectRelatedUserTypeEnum.BUSI_MANAGERB);
		relatedRoleList.add(ProjectRelatedUserTypeEnum.COUNCIL_JUDGE);
		relatedRoleList.add(ProjectRelatedUserTypeEnum.LEGAL_MANAGER);
		relatedRoleList.add(ProjectRelatedUserTypeEnum.RISK_MANAGER);
		projectQueryOrder.setRelatedRoleList(relatedRoleList);
		QueryBaseBatchResult<ProjectInfo> pList = projectServiceClient
			.queryProject(projectQueryOrder);
		model.addAttribute("relatedProject", PageUtil.getCovertPage(pList));
		return vm_path + "relatedProjectList.vm";
	}
	
	/**
	 * 区分资金和业务代办
	 * @param taskSearchOrder
	 * @param model
	 */
	private void setTask(TaskSearchOrder taskSearchOrder, Model model) {
		if (taskSearchOrder == null)
			taskSearchOrder = new TaskSearchOrder();
		taskSearchOrder.setUserName(ShiroSessionUtils.getSessionLocal().getUserName());
		taskSearchOrder.setPageSize(1000);
		QueryBaseBatchResult<WorkflowTaskInfo> baseBatchResult = workflowEngineWebClient
			.getTasksByAccount(taskSearchOrder);
		if (baseBatchResult != null && ListUtil.isNotEmpty(baseBatchResult.getPageList())) {
			//业务代办
			List<WorkflowTaskInfo> busiTask = Lists.newArrayList();
			//资金代办
			List<WorkflowTaskInfo> fundTask = Lists.newArrayList();
			for (WorkflowTaskInfo task : baseBatchResult.getPageList()) {
				String taskUrl = task.getTaskUrl();
				String taskName = task.getSubjectView();
				if (taskUrl == null) {
					if (taskName.indexOf("差旅费报销") != -1 || taskName.indexOf("费用支付") != -1
						|| taskName.indexOf("劳资及税金") != -1) {
						fundTask.add(task);
					} else {
						busiTask.add(task);
					}
				} else {
					if (taskUrl.startsWith("/fundMg/expenseApplication")
						|| taskUrl.startsWith("/fundMg/travelExpense")
						|| taskUrl.startsWith("/fundMg/labourCapital")) {
						fundTask.add(task);
					} else {
						busiTask.add(task);
					}
				}
			}
			model.addAttribute("busiTask", busiTask);
			model.addAttribute("fundTask", fundTask);
		}
	}
	
	/**
	 * 代办任务列表
	 * @param taskSearchOrder
	 * @param model
	 * @return
	 */
	@RequestMapping("taskList.htm")
	public String taskList(TaskSearchOrder taskSearchOrder, Model model) {
		if (taskSearchOrder == null)
			taskSearchOrder = new TaskSearchOrder();
		taskSearchOrder.setUserName(ShiroSessionUtils.getSessionLocal().getUserName());
		QueryBaseBatchResult<WorkflowTaskInfo> baseBatchResult = workflowEngineWebClient
			.getTasksByAccount(taskSearchOrder);
		model.addAttribute("page", PageUtil.getCovertPage(baseBatchResult));
		return vm_path + "taskList.vm";
	}
	
	/**
	 * 代办任务分类列表
	 * @param taskSearchOrder
	 * @param model
	 * @return
	 */
	@RequestMapping("taskGroup.htm")
	public String taskGroup(TaskSearchOrder taskSearchOrder, Model model) {
		if (taskSearchOrder == null)
			taskSearchOrder = new TaskSearchOrder();
		taskSearchOrder.setUserName(ShiroSessionUtils.getSessionLocal().getUserName());
		QueryBaseBatchResult<TaskTypeViewInfo> baseBatchResult = workflowEngineWebClient
			.getTaskTypeView(taskSearchOrder);
		if (baseBatchResult != null && baseBatchResult.getTotalCount() > 0) {
			Collections.sort(baseBatchResult.getPageList(), new Comparator<TaskTypeViewInfo>() {
				@Override
				public int compare(TaskTypeViewInfo o1, TaskTypeViewInfo o2) {
					return o2.getProcessName().compareTo(o1.getProcessName());
				}
			});
		}
		model.addAttribute("pageTaskGroup", PageUtil.getCovertPage(baseBatchResult));
		return vm_path + "taskGroup.vm";
	}
	
	/**
	 * 代办任务列表
	 * @param taskSearchOrder
	 * @param model
	 * @return
	 */
	@RequestMapping("taskGroupList.htm")
	public String taskGroupList(TaskSearchOrder taskSearchOrder, Model model) {
		if (taskSearchOrder == null)
			taskSearchOrder = new TaskSearchOrder();
		taskSearchOrder.setUserName(ShiroSessionUtils.getSessionLocal().getUserName());
		QueryBaseBatchResult<WorkflowTaskInfo> baseBatchResult = workflowEngineWebClient
			.getTasksByAccount(taskSearchOrder);
		model.addAttribute("processName", taskSearchOrder.getProcessName());
		model.addAttribute("taskNodeName", taskSearchOrder.getTaskNodeName());
		model.addAttribute("pageGroupTask", PageUtil.getCovertPage(baseBatchResult));
		return vm_path + "taskGroupList.vm";
	}
	
	/**
	 * 已办任务列表
	 * @param taskSearchOrder
	 * @param model
	 * @return
	 */
	@RequestMapping("doneTaskList.htm")
	public String doneTaskList(TaskSearchOrder taskSearchOrder, Model model) {
		if (taskSearchOrder == null)
			taskSearchOrder = new TaskSearchOrder();
		taskSearchOrder.setUserId(ShiroSessionUtils.getSessionLocal().getUserId());
		QueryBaseBatchResult<BpmFinishTaskInfo> baseBatchResult = workflowEngineWebClient
			.getFinishTask(taskSearchOrder);
		model.addAttribute("pageDoneTask", PageUtil.getCovertPage(baseBatchResult));
		model.addAttribute("taskSearchOrder", taskSearchOrder);
		return vm_path + "doneTaskList.vm";
	}
	
	/**
	 * 会议列表
	 * @param taskSearchOrder
	 * @param model
	 * @return
	 */
	@RequestMapping("deskCouncilList.htm")
	public String deskCouncilList(CouncilQueryOrder queryOrder, Model model) {
		if (queryOrder == null)
			queryOrder = new CouncilQueryOrder();
		List<String> statuss = Lists.newArrayList();
		statuss.add(CouncilStatusEnum.NOT_BEGIN.code());
		statuss.add(CouncilStatusEnum.IN_PROGRESS.code());
		queryOrder.setStatuss(statuss);
		QueryBaseBatchResult<CouncilInfo> batchResult = councilListQuery(queryOrder, model);
		model.addAttribute("pageCouncil", PageUtil.getCovertPage(batchResult));
		model.addAttribute("isRiskSecretary", isRiskSecretary());
		return vm_path + "deskCouncilList.vm";
	}
	
	/**
	 * 处理任务
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("taskDistributor.htm")
	public String taskDistributor(HttpServletRequest request, HttpServletResponse response,
									Model model) {
		FormQueryOrder formQueryOrder = new FormQueryOrder();
		formQueryOrder.setActDefId(request.getParameter("processDefinitionId"));
		formQueryOrder
			.setActInstId(NumberUtil.parseLong(request.getParameter("processInstanceId")));
		String module = "projectMg";
		String url = request.getParameter("taskUrl");
		if (StringUtil.isNotEmpty(url)) {
			String[] stringSplit = url.split("/");
			if (stringSplit.length > 0)
				module = stringSplit[1];
		}
		QueryBaseBatchResult<FormInfo> batchResult = getSystemMatchedFormServiceByModule(module)
			.queryPage(formQueryOrder);
		if (ListUtil.isNotEmpty(batchResult.getPageList())) {
			FormInfo formInfo = batchResult.getPageList().get(0);
			String taskId = request.getParameter("taskId");
			model.addAttribute("systemNameDefautUrl", url);
			model.addAttribute("formId", formInfo.getFormId());
			model.addAttribute("taskId", taskId);
		} else {
			logger.error("taskDistributor.htm调用结果", batchResult);
		}
		
		return "redirect:/" + module + "/index.htm";
	}
	
}
