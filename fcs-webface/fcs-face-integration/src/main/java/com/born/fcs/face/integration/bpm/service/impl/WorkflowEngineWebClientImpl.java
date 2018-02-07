package com.born.fcs.face.integration.bpm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONObject;
import com.born.bpm.service.client.flow.FlowServiceProxy;
import com.born.bpm.service.client.flow.ProcessServiceProxy;
import com.born.fcs.face.integration.bpm.service.WorkflowBatchProcessService;
import com.born.fcs.face.integration.bpm.service.WorkflowEngineWebClient;
import com.born.fcs.face.integration.bpm.service.info.BpmButtonInfo;
import com.born.fcs.face.integration.bpm.service.info.BpmFinishTaskInfo;
import com.born.fcs.face.integration.bpm.service.info.TaskTypeViewInfo;
import com.born.fcs.face.integration.bpm.service.info.WebNodeInfo;
import com.born.fcs.face.integration.bpm.service.info.WorkflowProcessLog;
import com.born.fcs.face.integration.bpm.service.order.TaskSearchOrder;
import com.born.fcs.face.integration.bpm.service.order.WorkflowBatchProcessOrder;
import com.born.fcs.face.integration.bpm.service.order.WorkflowBatchProcessTaskOrder;
import com.born.fcs.face.integration.bpm.service.result.WorkflowBatchProcessResult;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.exception.FcsFaceBizException;
import com.born.fcs.face.integration.service.ClientBaseSevice;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.pm.biz.service.common.SysParameterService;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.XmlUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormExecuteInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.bpm.TaskNodeInfo;
import com.born.fcs.pm.ws.order.bpm.TaskOpinion;
import com.born.fcs.pm.ws.order.bpm.WorkflowTaskInfo;
import com.born.fcs.pm.ws.order.common.FormQueryOrder;
import com.born.fcs.pm.ws.order.common.SimpleFormAuditOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.FormService;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

@Service("workflowEngineWebClient")
public class WorkflowEngineWebClientImpl extends ClientBaseSevice implements
																	WorkflowEngineWebClient,
																	ApplicationContextAware {
	@Autowired
	private FormService formServiceClient;
	@Autowired
	private FormService formServiceFmClient;
	@Autowired
	private FormService formServiceAmClient;
	@Autowired
	private FormService formServiceCrmClient;
	@Autowired
	private ProjectRelatedUserService projectRelatedUserServiceClient;
	@Autowired
	private SysParameterService sysParameterServiceClient;
	
	@SuppressWarnings("unchecked")
	@Override
	public WebNodeInfo getTaskNode(String taskId) {
		WebNodeInfo taskNodeInfo = new WebNodeInfo();
		try {
			if (StringUtil.isEmpty(taskId))
				return null;
			logger.info("获取流程接点 , taskId : {}" + taskId);
			ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
				propertyConfigService.getBmpServiceProcessService());
			String xmlString = processServiceProxy.getTaskNode(taskId);
			logger.info("获取流程接点 , taskId {}: {}", taskId, xmlString);
			Document doc = XmlUtil.parseXML(xmlString);
			if (doc != null) {
				Element root = doc.getRootElement();
				addNodeAttributeValue(root, taskNodeInfo);
				List<Element> elementButtons = root.elements("bpmNodeButton");
				if (ListUtil.isNotEmpty(elementButtons)) {
					List<BpmButtonInfo> buttonInfos = Lists.newArrayList();
					for (Element bpmNodeButton : elementButtons) {
						BpmButtonInfo bpmNodeButtonInfo = new BpmButtonInfo();
						bpmNodeButtonInfo.setButtonName(bpmNodeButton.attributeValue("buttonName"));
						bpmNodeButtonInfo.setOperatortype(NumberUtil.parseInt(bpmNodeButton
							.attributeValue("operatortype")));
						buttonInfos.add(bpmNodeButtonInfo);
					}
					taskNodeInfo.setBpmButtonInfos(buttonInfos);
				}
				List<Element> elements = root.elements("nextFlowNode");
				if (ListUtil.isNotEmpty(elements)) {
					List<TaskNodeInfo> nodeInfos = Lists.newArrayList();
					for (Element taskNode : elements) {
						TaskNodeInfo childNodeInfo = new TaskNodeInfo();
						addNodeAttributeValue(taskNode, childNodeInfo);
						nodeInfos.add(childNodeInfo);
					}
					taskNodeInfo.setNextFlowNode(nodeInfos);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		}
		return taskNodeInfo;
	}
	
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Override
	public QueryBaseBatchResult<WorkflowTaskInfo> getTasksByAccount(TaskSearchOrder taskSearchOrder) {
		QueryBaseBatchResult<WorkflowTaskInfo> batchResult = new QueryBaseBatchResult<WorkflowTaskInfo>();
		try {
			taskSearchOrder.check();
//			taskSearchOrder.setBillNo("单据号");
//			taskSearchOrder.setSubject("单据号：ZF001414-费用支付(劳资类)审核流程-齐悦");
			ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
				propertyConfigService.getBmpServiceProcessService());
			String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><req account=\""
							+ taskSearchOrder.getUserName() + "\" taskNodeName=\""
							+ StringUtil.defaultIfBlank(taskSearchOrder.getTaskNodeName())
							+ "\" subject=\""
							+ StringUtil.defaultIfBlank(taskSearchOrder.getSubject())
							+ "\" processName=\""
							+ StringUtil.defaultIfBlank(taskSearchOrder.getProcessName())
							+ "\" orderField=\""
							+ StringUtil.defaultIfBlank(taskSearchOrder.getOrderField())
							+ "\" orderSeq=\""
							+ StringUtil.defaultIfBlank(taskSearchOrder.getProcessName())
							+ "\" pageSize=\"" + taskSearchOrder.getPageSize()
							+ "\" currentPage= \"" + taskSearchOrder.getPageNumber() + "\"></req>";
			logger.info("获取代办任务发送xml:{}", xml);
			String xmlString = processServiceProxy.getTasksByAccount(xml);
			logger.info("获取代办任务结果:{}" + xmlString);
			Document doc = XmlUtil.parseXML(xmlString);
			if (doc != null) {
				
				Element root = doc.getRootElement();
				//root.attributeValue("notRead");
				//root.attributeValue("read");
				PageComponent component = new PageComponent(taskSearchOrder,
					NumberUtil.parseLong(root.attributeValue("total")));
				List<Element> tasks = root.elements("task");
				Map<String, String> map = null;
				List<WorkflowTaskInfo> list = new ArrayList<WorkflowTaskInfo>();
				for (Element element : tasks) {
					WorkflowTaskInfo taskInfo = new WorkflowTaskInfo();
					List attributes = element.attributes();
					Map<String, Object> datamap = new HashMap<String, Object>();
					for (Iterator it = attributes.iterator(); it.hasNext();) {
						Attribute attribute = (Attribute) it.next();
						datamap.put(attribute.getName(), attribute.getValue());
					}
					MiscUtil.setInfoPropertyByMap(datamap, taskInfo);
					if (datamap.get("createDate") != null) {
						taskInfo.setCreateDate(DateUtil.parse((String) datamap.get("createDate")));
						taskInfo.setCreateDateStr((String) datamap.get("createDate"));
					} else {
						if (StringUtil.isNotBlank(taskInfo.getCreateDateStr())) {
							taskInfo.setCreateDate(DateUtil.parse(taskInfo.getCreateDateStr()));
						} else {
							String[] tempArray = taskInfo.getSubject().split("-");
							if (tempArray.length == 5) {
								String createDateStr = tempArray[2] + "-" + tempArray[3] + "-"
														+ tempArray[4];
								taskInfo.setCreateDateStr(createDateStr);
								taskInfo.setCreateDate(DateUtil.parse(createDateStr));
							}
						}
					}
					taskInfo.setCreateTime(DateUtil.parse((String) datamap.get("createTime")));
					//					if (StringUtil.isNotBlank(taskInfo.getTaskUrl())) {
					list.add(taskInfo);
					//					}
					
				}
				batchResult.initPageParam(component);
				batchResult.setPageList(list);
				
				//按财务单据号筛选
				queryByBillNo(taskSearchOrder, batchResult);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		}
		return batchResult;
	}
	
	/**
	 * 按财务单据号筛选
	 * @param taskSearchOrder
	 * @param batchResult
	 */
	private void queryByBillNo(TaskSearchOrder taskSearchOrder,
								QueryBaseBatchResult<WorkflowTaskInfo> batchResult) {
		String billNo = taskSearchOrder.getBillNo();
		if (StringUtil.isBlank(billNo) || null == batchResult
			|| ListUtil.isEmpty(batchResult.getPageList())) {
			return;
		}
		
		List<WorkflowTaskInfo> list = new ArrayList<>();
		for (WorkflowTaskInfo task : batchResult.getPageList()) {
			if (StringUtil.isNotBlank(task.getSubject()) && task.getSubject().contains(billNo)) {
				list.add(task);
			}
		}
		
		PageComponent component = new PageComponent(taskSearchOrder, list.size());
		batchResult.initPageParam(component);
		batchResult.setPageList(list);
	}
	
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Override
	public QueryBaseBatchResult<TaskTypeViewInfo> getTaskTypeView(TaskSearchOrder taskSearchOrder) {
		QueryBaseBatchResult<TaskTypeViewInfo> batchResult = new QueryBaseBatchResult<TaskTypeViewInfo>();
		try {
			taskSearchOrder.check();
			ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
				propertyConfigService.getBmpServiceProcessService());
			
			String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><req account=\""
							+ taskSearchOrder.getUserName() + "\" taskNodeName=\"" + ""
							+ "\" subject=\"" + "" + "\" processName=\"\"" + "></req>";
			logger.info("获取代办任务发送xml:{}", xml);
			String xmlString = processServiceProxy.getTasksByAccountGroupByProcessName(xml);
			logger.info("获取代办任务结果:{}" + xmlString);
			Document doc = XmlUtil.parseXML(xmlString);
			if (doc != null) {
				
				Element root = doc.getRootElement();
				//root.attributeValue("notRead");
				//root.attributeValue("read");
				
				List<Element> tasks = root.elements("task");
				PageComponent component = new PageComponent(taskSearchOrder, tasks.size());
				Map<String, String> map = null;
				List<TaskTypeViewInfo> list = new ArrayList<TaskTypeViewInfo>();
				for (Element element : tasks) {
					TaskTypeViewInfo taskInfo = new TaskTypeViewInfo();
					List attributes = element.attributes();
					Map<String, Object> datamap = new HashMap<String, Object>();
					for (Iterator it = attributes.iterator(); it.hasNext();) {
						Attribute attribute = (Attribute) it.next();
						datamap.put(attribute.getName(), attribute.getValue());
					}
					MiscUtil.setInfoPropertyByMap(datamap, taskInfo);
					list.add(taskInfo);
					//					}
					
				}
				batchResult.initPageParam(component);
				batchResult.setPageList(list);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		}
		return batchResult;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public QueryBaseBatchResult<BpmFinishTaskInfo> getFinishTask(TaskSearchOrder taskSearchOrder) {
		QueryBaseBatchResult<BpmFinishTaskInfo> batchResult = new QueryBaseBatchResult<BpmFinishTaskInfo>();
		try {
			taskSearchOrder.check();
//			long pageNumber = taskSearchOrder.getPageNumber();
//			long pageSize = taskSearchOrder.getPageSize();
//			if (StringUtil.isNotBlank(taskSearchOrder.getBillNo())) {
//				taskSearchOrder.setPageNumber(1L);
//				taskSearchOrder.setPageSize(1000L);
//			}
			ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
				propertyConfigService.getBmpServiceProcessService());
			logger.info("获取已办任务:{}", taskSearchOrder);
			JSONObject queryJson = new JSONObject();
			queryJson.put("d-636069-p", taskSearchOrder.getPageNumber());
			queryJson.put("d-636069-z", taskSearchOrder.getPageSize());
			queryJson.put("d-636069-oz", taskSearchOrder.getPageSize());
			queryJson.put("Q_subject_SUPL", taskSearchOrder.getSubject());
			queryJson.put("Q_creator_SL", taskSearchOrder.getCreator());
			queryJson.put("Q_begincreatetime_DL", taskSearchOrder.getBeginCreateTime());
			queryJson.put("Q_endcreatetime_DG", taskSearchOrder.getEndCreateTime());
			String jsonString = processServiceProxy.getAlreadyMattersList(
				String.valueOf(taskSearchOrder.getUserId()), queryJson.toString());
			logger.info("获取已办任务结果:{}" + jsonString);
			HashMap<String, Object> taskMap = MiscUtil.parseJSON(jsonString);
			if (taskMap == null || !StringUtil.equals("0", (String) taskMap.get("state"))) {
				batchResult.setSuccess(false);
				batchResult.setMessage("获取已办任务失败");
			} else {
				
				batchResult.setSuccess(true);
				batchResult.setTotalCount((Long) taskMap.get("totalCount"));
				batchResult.setPageCount((Long) taskMap.get("totalPage"));
				List<HashMap<String, Object>> taskList = (List<HashMap<String, Object>>) taskMap
					.get("list");
				
				List<BpmFinishTaskInfo> dataList = Lists.newArrayList();
				if (taskList != null) {
					for (HashMap<String, Object> task : taskList) {
						BpmFinishTaskInfo finishTask = new BpmFinishTaskInfo();
						finishTask.setActDefId((String) task.get("actInstId"));
						finishTask.setActInstId((String) task.get("actInstId"));
						finishTask.setBusinessUrl((String) task.get("businessUrl"));
						
						HashMap<String, Object> ctMap = (HashMap<String, Object>) task
							.get("createtime");
						if (ctMap != null) {
							Date createTime = new Date(((Long) ctMap.get("time")));
							finishTask.setCreatetime(createTime);
						}
						finishTask.setCreator((String) task.get("creator"));
						finishTask.setCreatorId((Long) task.get("creatorId"));
						finishTask.setDefId((Long) task.get("defId"));
						
						HashMap<String, Object> etMap = (HashMap<String, Object>) task
							.get("endTime");
						if (etMap != null) {
							Date endTime = new Date(((Long) etMap.get("time")));
							finishTask.setEndTime(endTime);
						}
						finishTask.setProcessName((String) task.get("processName"));
						finishTask.setRunId((Long) task.get("runId"));
						finishTask.setSubject((String) task.get("subject"));
						finishTask.setStatus((Long) task.get("status"));
						dataList.add(finishTask);
					}
				}
				batchResult.setPageList(dataList);
				batchResult.setPageNumber(taskSearchOrder.getPageNumber());
				batchResult.setPageSize(taskSearchOrder.getPageSize());
				// 按财务单据号筛选
//				findFinishTaskByBillNo(batchResult, taskSearchOrder, pageNumber, pageSize);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setMessage("查询已办任务出错");
		}
		return batchResult;
	}
	
	/**
	 * 按财务单据号筛选
	 * @param batchResult
	 * @param taskSearchOrder
	 * @param pageNumber
	 * @param pageSize
	 */
	private void findFinishTaskByBillNo(QueryBaseBatchResult<BpmFinishTaskInfo> batchResult,
										TaskSearchOrder taskSearchOrder, long pageNumber,
										long pageSize) {
		String billNo = taskSearchOrder.getBillNo();
		if (StringUtil.isBlank(billNo) || null == batchResult
			|| ListUtil.isEmpty(batchResult.getPageList())) {
			return;
		}
		
		List<BpmFinishTaskInfo> list = new ArrayList<>();
		for (BpmFinishTaskInfo task : batchResult.getPageList()) {
			if (StringUtil.isNotBlank(task.getSubject()) && task.getSubject().contains(billNo)) {
				list.add(task);
			}
		}
		
		long start = (pageNumber-1) * pageSize;
		long end = start + pageSize;
		long total = list.size();
		start = start >= total ? 0L : start;
		end = end >= total ? 0L : end;
		if (start < end) {
			taskSearchOrder.setPageNumber(pageNumber);
			taskSearchOrder.setPageSize(pageSize);
			PageComponent component = new PageComponent(taskSearchOrder, total);
			batchResult.initPageParam(component);
			batchResult.setPageList(list.subList((int)start, (int)end));
		}
	}
	
	private void addNodeAttributeValue(Element taskNode, TaskNodeInfo childNodeInfo) {
		childNodeInfo.setNodeId(taskNode.attributeValue("nodeId"));
		childNodeInfo.setNodeName(taskNode.attributeValue("nodeName"));
		childNodeInfo.setNodeType(taskNode.attributeValue("nodeType"));
		childNodeInfo.setFormUrl(taskNode.attributeValue("formUrl"));
		childNodeInfo.setInformType(taskNode.attributeValue("informType"));
		childNodeInfo.setCanSelectUser("true".equals(taskNode.attributeValue("canSelectedUser")));
	}
	
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Override
	public QueryBaseBatchResult<WorkflowProcessLog> getProcessOpinionByActInstId(String actInstId) {
		QueryBaseBatchResult<WorkflowProcessLog> batchResult = new QueryBaseBatchResult<WorkflowProcessLog>();
		try {
			
			ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
				propertyConfigService.getBmpServiceProcessService());
			
			logger.info("获取审核历史发送xml:{}", actInstId);
			String xmlString = processServiceProxy.getProcessOpinionByActInstId(actInstId);
			logger.info("获取审核历史结果:{}" + xmlString);
			Document doc = XmlUtil.parseXML(xmlString);
			if (doc != null) {
				
				Element root = doc.getRootElement();
				//root.attributeValue("notRead");
				//root.attributeValue("read");
				
				List<Element> tasks = root.elements("task");
				PageComponent component = new PageComponent(1, tasks.size());
				Map<String, String> map = null;
				List<WorkflowProcessLog> list = new ArrayList<WorkflowProcessLog>();
				for (Element element : tasks) {
					WorkflowProcessLog taskInfo = new WorkflowProcessLog();
					List attributes = element.attributes();
					Map<String, Object> datamap = new HashMap<String, Object>();
					for (Iterator it = attributes.iterator(); it.hasNext();) {
						Attribute attribute = (Attribute) it.next();
						datamap.put(attribute.getName(), attribute.getValue());
					}
					MiscUtil.setInfoPropertyByMap(datamap, taskInfo);
					//taskInfo.setCheckStatus(checkStatus)
					if (StringUtil.isBlank(taskInfo.getEndTime())) {
						list.add(0, taskInfo);
					} else
						list.add(taskInfo);
					
				}
				batchResult.initPageParam(component);
				batchResult.setSuccess(true);
				batchResult.setPageList(list);
				logger.info(list.toString());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		}
		return batchResult;
	}
	
	@Override
	public FcsBaseResult readTask(String actInstId, String taskId) {
		FcsBaseResult baseResult = new FcsBaseResult();
		try {
			Assert.hasText(actInstId);
			Assert.hasText(taskId);
			logger.info("获取步骤执行人:actInstId={},taskId={}", actInstId, taskId);
			FlowServiceProxy flowServiceProxy = new FlowServiceProxy(
				propertyConfigService.getBmpServiceProcessService());
			String returnJson = flowServiceProxy.readTask(actInstId, taskId);
			logger.info("获取步骤执行人:" + returnJson);
			HashMap<String, Object> dataMap = MiscUtil.parseJSON(returnJson);
			if (dataMap != null) {
				if (StringUtil.equals("1", String.valueOf(dataMap.get("result")))) {
					baseResult.setSuccess(true);
				} else {
					baseResult.setSuccess(false);
					baseResult.setMessage(String.valueOf(dataMap.get("message")));
				}
			}
			
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		}
		return baseResult;
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<TaskOpinion> getTaskUsers(String instanceId, String nodeId) {
		List<TaskOpinion> taskOpinions = Lists.newArrayList();
		try {
			Assert.hasText(instanceId);
			Assert.hasText(nodeId);
			logger.info("获取步骤执行人:instanceId={},nodeId={}", instanceId, nodeId);
			FlowServiceProxy flowServiceProxy = new FlowServiceProxy(
				propertyConfigService.getBmpServiceFlowProcessService());
			String returnJson = flowServiceProxy.getTaskUsersByInstanceIdAndNodeId(instanceId,
				nodeId);
			logger.info("获取步骤执行人:" + returnJson);
			HashMap<String, Object> dataMap = MiscUtil.parseJSON(returnJson);
			if (dataMap != null) {
				if (StringUtil.equals("1", String.valueOf(dataMap.get("code")))) {
					List<Map> taskOpinionMapList = (List<Map>) dataMap.get("taskOpinionList");
					if (taskOpinionMapList != null) {
						for (Map item : taskOpinionMapList) {
							TaskOpinion taskOpinion = new TaskOpinion();
							MiscUtil.setInfoPropertyByMap(item, taskOpinion);
							
							//更改执行人后
							//							"taskExeStatus": {
							//				                "candidateUser": "",
							//				                "executor": "支溧",
							//				                "executorAllId": "",
							//				                "executorId": "20000006810072",
							//				                "mainOrgName": "",
							//				                "read": false,
							//				                "type": ""
							//				            }
							Map taskExeStatus = (Map) item.get("taskExeStatus");
							if (taskExeStatus != null) {
								Boolean isRead = (Boolean) taskExeStatus.get("read");
								if (isRead != null)
									taskOpinion.setRead(isRead);
								
								Object executorId = taskExeStatus.get("executorId");
								if (StringUtil.isNotBlank(String.valueOf(executorId))
									&& !StringUtil.equals(String.valueOf(executorId), "null")) {
									taskOpinion.setExeUserId(NumberUtil.parseLong(String
										.valueOf(executorId)));
									taskOpinion.setExeFullname(String.valueOf(taskExeStatus
										.get("executor")));
								}
							}
							
							//查询候选人列表
							if (taskOpinion.getExeUserId() == null
								|| taskOpinion.getExeUserId() == 0) {
								
								List<Map> candidateUserStatusList = (List<Map>) item
									.get("candidateUserStatusList");
								if (ListUtil.isNotEmpty(candidateUserStatusList)) {
									List<Long> candidateUserList = Lists.newArrayList();
									for (Map candidateUser : candidateUserStatusList) {
										String executorIds = (String) candidateUser
											.get("executorAllId");
										if (executorIds != null) {
											String[] executorIdArr = executorIds.split(",");
											for (String excutorId : executorIdArr) {
												candidateUserList.add(NumberUtil
													.parseLong(excutorId));
											}
										}
									}
									taskOpinion.setCandidateUserList(candidateUserList);
								}
							}
							
							taskOpinions.add(taskOpinion);
						}
					}
					
				}
			}
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		}
		return taskOpinions;
		
	}
	
	@Override
	public WorkflowBatchProcessResult batchProcess(WorkflowBatchProcessOrder order) {
		WorkflowBatchProcessResult result = new WorkflowBatchProcessResult();
		try {
			if (order == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM, "请求参数错误");
			}
			//检查order
			order.check();
			FormService formService = getSystemMatchedFormService(order.getSysName());
			
			WorkflowBatchProcessService processService = null;
			if (StringUtil.isNotBlank(order.getProcessServiceName())) {
				processService = getBean(order.getProcessServiceName());
			}
			
			//成功
			List<Long> successFormIdList = Lists.newArrayList();
			List<String> successMessageList = Lists.newArrayList();
			//失败
			List<Long> failureFormIdList = Lists.newArrayList();
			//失败消息
			List<String> failureMessageList = Lists.newArrayList();
			//不支持
			List<Long> nonSupportFormIdList = Lists.newArrayList();
			
			//去重
			Set<Long> formIds = new HashSet<Long>();
			for (long formId : order.getProcessFormIds()) {
				formIds.add(formId);
			}
			
			for (long formId : formIds) { //批量处理开始
			
				try {
					
					FormInfo form = formService.findByFormId(formId);
					if (form == null) {
						failureFormIdList.add(formId);
						failureMessageList.add("未匹配到表单");
						continue;
					}
					
					//解析当前人员的任务
					String[] task = getUserTask(form, order.getUserId());
					String taskId = task[0];
					String taskUrl = task[1];
					if (StringUtil.isBlank(taskId) || StringUtil.isBlank(taskUrl)) { //任务不存在 
						failureFormIdList.add(formId);
						failureMessageList.add("不是当前处理人");
						continue;
					}
					
					SimpleFormAuditOrder auditOrder = new SimpleFormAuditOrder();
					BeanCopier.staticCopy(order, auditOrder); //主要设置处理人员等信息
					auditOrder.setTaskId(NumberUtil.parseLong(taskId));
					auditOrder.setFormId(formId);
					auditOrder.setVoteContent(order.getProcessContent());
					
					/*驳回到发起人*/
					if ("back2start".equals(order.getProcessType())) {
						
						//查询是否有按钮
						WebNodeInfo node = getTaskNode(taskId);
						if (node == null) {
							failureFormIdList.add(formId);
							failureMessageList.add("查询任务节点出错");
							continue;
						}
						List<BpmButtonInfo> buttonList = node.getBpmButtonInfos();
						if (ListUtil.isEmpty(buttonList)) {
							failureFormIdList.add(formId);
							failureMessageList.add("无驳回权限");
							continue;
						} else {
							boolean hasBackButton = false;
							for (BpmButtonInfo button : buttonList) {
								//驳回按钮
								if (button.getOperatortype() == 4 || button.getOperatortype() == 5) {
									hasBackButton = true;
									break;
								}
							}
							if (!hasBackButton) {
								failureFormIdList.add(formId);
								failureMessageList.add("无驳回权限");
								continue;
							}
						}
						auditOrder.setVoteAgree(String.valueOf(TaskOpinion.STATUS_REJECT_TOSTART));
						auditOrder.setIsBack("2");
						if (StringUtil.isBlank(auditOrder.getVoteContent())) {
							auditOrder.setVoteContent("驳回");
						}
					} else {//通过	
					
						//审核前自定义参数
						Map<String, Object> customizeMap = null;
						//自定义处理类
						if (processService != null) {
							//检查是否支持批量审核
							boolean isSupport = true;
							String[] nonSupportTask = processService.nonSupportTask();
							if (nonSupportTask != null) {
								for (String t : nonSupportTask) {
									if (taskUrl.startsWith(t)) {
										isSupport = false;
										break;
									}
								}
							}
							if (!isSupport) {
								nonSupportFormIdList.add(formId);
								continue;
							}
							
							//设置当前执行人信息
							SimpleUserInfo excutor = new SimpleUserInfo();
							excutor.setUserId(order.getUserId());
							excutor.setUserName(order.getUserName());
							excutor.setUserAccount(order.getUserAccount());
							excutor.setMobile(order.getUserMobile());
							excutor.setEmail(order.getUserEmail());
							excutor.setDeptId(order.getDeptId());
							excutor.setDeptName(order.getDeptName());
							excutor.setDeptPath(order.getDeptPath());
							excutor.setDeptPathName(order.getDeptPathName());
							
							//验证是否支持
							isSupport = processService.isSupport(form, excutor);
							if (!isSupport) {
								nonSupportFormIdList.add(formId);
								continue;
							}
							//添加审核自定义参数
							customizeMap = processService.makeCustomizeMap(form, excutor);
						}
						
						auditOrder.setCustomizeMap(customizeMap);
						auditOrder.setVoteAgree(String.valueOf(TaskOpinion.STATUS_AGREE));
						if (StringUtil.isBlank(auditOrder.getVoteContent())) {
							auditOrder.setVoteContent("同意");
						}
					}
					
					FcsBaseResult auditResult = formService.auditProcess(auditOrder);
					if (auditResult != null && auditResult.isSuccess()) {
						successFormIdList.add(formId);
						
						//查看下步执行人
						String nextAuditor = "";
						String nextNode = "";
						String nextInfo = auditResult.getUrl();
						if (StringUtil.isNotBlank(nextInfo)) {
							String[] next = nextInfo.split(";");
							if (next.length > 0)
								nextNode = next[0];
							if (next.length > 1)
								nextAuditor = next[1];
						}
						
						String message = "处理成功 ";
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
						successMessageList.add(message);
					} else {
						failureFormIdList.add(formId);
						if (StringUtil.isNotBlank(auditResult.getMessage())) {
							failureMessageList.add("处理失败[ " + auditResult.getMessage() + " ]");
						} else {
							failureMessageList.add("处理失败");
						}
					}
				} catch (Exception e) {
					logger.error("批量审核出错 formId：{}", formId);
				}
				
			}//循环处理结束
			result.setSuccessFormIdList(successFormIdList);
			result.setSuccessMessageList(successMessageList);
			result.setFailureFormIdList(failureFormIdList);
			result.setFailureMessageList(failureMessageList);
			result.setNonSupportFormIdList(nonSupportFormIdList);
			result.setSuccess(true);
			result.setMessage("批量处理成功");
		} catch (FcsFaceBizException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
			logger.error("批量处理异常：{}", be);
		} catch (IllegalArgumentException ll) {
			result.setSuccess(false);
			result.setMessage(ll.getMessage());
			logger.error("批量处理异常：{}", ll);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("批量处理异常");
			logger.error("批量处理异常：{}", e);
		}
		return result;
	}
	
	@Override
	public WorkflowBatchProcessResult batchProcessTask(WorkflowBatchProcessTaskOrder order) {
		
		WorkflowBatchProcessResult result = new WorkflowBatchProcessResult();
		try {
			
			if (order == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM, "请求参数错误");
			}
			
			//检查order
			order.check();
			
			//成功
			List<Long> successActInstIdList = Lists.newArrayList();
			List<String> successMessageList = Lists.newArrayList();
			//失败
			List<Long> failureActInstIdList = Lists.newArrayList();
			//失败消息
			List<String> failureMessageList = Lists.newArrayList();
			//不支持
			List<Long> nonSupportActInstIdList = Lists.newArrayList();
			
			for (int i = 0; i < order.getActInstIds().length; i++) { //批量处理开始
				long actInstId = NumberUtil.parseLong(order.getActInstIds()[i]);
				String taskId = "";
				String taskUrl = "";
				if (order.getTaskIds().length >= i)
					taskId = order.getTaskIds()[i];
				if (order.getTaskUrls().length >= i)
					taskUrl = order.getTaskUrls()[i];
				
				if (actInstId == 0 || StringUtil.isBlank(taskId) || StringUtil.isBlank(taskUrl)) {
					failureActInstIdList.add(actInstId);
					failureMessageList.add("任务未找到");
					continue;
				}
				
				try {
					
					String module = "projectMg";
					if (StringUtil.isNotBlank(taskUrl)) {
						String[] stringSplit = taskUrl.split("/");
						if (stringSplit.length > 0)
							module = stringSplit[1];
					}
					
					FormService formService = getSystemMatchedFormService(module);
					
					FormInfo form = null;
					FormQueryOrder queryOrder = new FormQueryOrder();
					queryOrder.setActInstId(actInstId);
					QueryBaseBatchResult<FormInfo> formResult = formService.queryPage(queryOrder);
					if (formResult != null && formResult.getTotalCount() > 0)
						form = formResult.getPageList().get(0);
					
					if (form == null) {
						failureActInstIdList.add(actInstId);
						failureMessageList.add("未匹配到表单");
						continue;
					}
					
					SimpleFormAuditOrder auditOrder = new SimpleFormAuditOrder();
					BeanCopier.staticCopy(order, auditOrder); //主要设置处理人员等信息
					auditOrder.setTaskId(NumberUtil.parseLong(taskId));
					auditOrder.setFormId(form.getFormId());
					auditOrder.setVoteContent(order.getProcessContent());
					
					/*驳回到发起人*/
					if ("back2start".equals(order.getProcessType())) {
						
						//查询是否有按钮
						WebNodeInfo node = getTaskNode(taskId);
						if (node == null) {
							failureActInstIdList.add(actInstId);
							failureMessageList.add("查询任务节点出错");
							continue;
						}
						List<BpmButtonInfo> buttonList = node.getBpmButtonInfos();
						if (ListUtil.isEmpty(buttonList)) {
							failureActInstIdList.add(actInstId);
							failureMessageList.add("无驳回权限");
							continue;
						} else {
							boolean hasBackButton = false;
							for (BpmButtonInfo button : buttonList) {
								//驳回按钮
								if (button.getOperatortype() == 4 || button.getOperatortype() == 5) {
									hasBackButton = true;
									break;
								}
							}
							if (!hasBackButton) {
								failureActInstIdList.add(actInstId);
								failureMessageList.add("无驳回权限");
								continue;
							}
						}
						auditOrder.setVoteAgree(String.valueOf(TaskOpinion.STATUS_REJECT_TOSTART));
						auditOrder.setIsBack("2");
						if (StringUtil.isBlank(auditOrder.getVoteContent())) {
							auditOrder.setVoteContent("驳回");
						}
					} else {//通过	
					
						//审核前自定义参数
						Map<String, Object> customizeMap = null;
						
						//编辑地址都不可批量处理
						if (StringUtil.equals(form.getFormCode().getEditUrl(), taskUrl)) {
							nonSupportActInstIdList.add(actInstId);
							continue;
						}
						
						//理财立项、理财转让信惠分管副总需要选择是否上会
						if ("/projectMg/financialProject/setUp/audit/XHChooseCouncil.htm"
							.equals(taskUrl)
							|| "/projectMg/financialProject/transfer/audit/XHChooseCouncil.htm"
								.equals(taskUrl)) {
							if (isBelong2Xinhui()) {
								nonSupportActInstIdList.add(actInstId);
								continue;
							}
						}
						
						auditOrder.setCustomizeMap(customizeMap);
						auditOrder.setVoteAgree(String.valueOf(TaskOpinion.STATUS_AGREE));
						if (StringUtil.isBlank(auditOrder.getVoteContent())) {
							auditOrder.setVoteContent("同意");
						}
					}
					
					FcsBaseResult auditResult = formService.auditProcess(auditOrder);
					if (auditResult != null && auditResult.isSuccess()) {
						successActInstIdList.add(actInstId);
						
						//查看下步执行人
						String nextAuditor = "";
						String nextNode = "";
						String nextInfo = auditResult.getUrl();
						if (StringUtil.isNotBlank(nextInfo)) {
							String[] next = nextInfo.split(";");
							if (next.length > 0)
								nextNode = next[0];
							if (next.length > 1)
								nextAuditor = next[1];
						}
						
						String message = "处理成功 ";
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
						successMessageList.add(message);
					} else {
						failureActInstIdList.add(actInstId);
						if (StringUtil.isNotBlank(auditResult.getMessage())) {
							failureMessageList.add("处理失败[ " + auditResult.getMessage() + " ]");
						} else {
							failureMessageList.add("处理失败");
						}
					}
				} catch (Exception e) {
					logger.error("批量审核出错 actInstId：{}", actInstId);
				}
				
			}//循环处理结束
			result.setSuccessFormIdList(successActInstIdList);
			result.setSuccessMessageList(successMessageList);
			result.setFailureFormIdList(failureActInstIdList);
			result.setFailureMessageList(failureMessageList);
			result.setNonSupportFormIdList(nonSupportActInstIdList);
			result.setSuccess(true);
			result.setMessage("批量处理成功");
		} catch (FcsFaceBizException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
			logger.error("批量处理异常：{}", be);
		} catch (IllegalArgumentException ll) {
			result.setSuccess(false);
			result.setMessage(ll.getMessage());
			logger.error("批量处理异常：{}", ll);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("批量处理异常");
			logger.error("批量处理异常：{}", e);
		}
		return result;
	}
	
	/**
	 * 当前登陆人员是否属于信惠人员
	 * @return
	 */
	public boolean isBelong2Xinhui() {
		boolean isXinHui = false;
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			//是否属于信惠人员
			isXinHui = projectRelatedUserServiceClient.isBelongToDept(sessionLocal
				.getUserDetailInfo(), sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_XINHUI_DEPT_CODE.code()));
		}
		return isXinHui;
	}
	
	/**
	 * 获取当前执行人任务
	 * @param form
	 * @param executor
	 * @return
	 */
	private String[] getUserTask(FormInfo form, long executor) {
		String[] task = new String[2];
		try {
			List<FormExecuteInfo> exeList = form.getFormExecuteInfo();
			if (ListUtil.isNotEmpty(exeList)) {
				for (FormExecuteInfo exeInfo : exeList) {
					if (StringUtil.isBlank(exeInfo.getTaskId()))
						continue;
					if (exeInfo.getUserId() > 0) {
						if (exeInfo.getUserId() == executor) {
							task[0] = exeInfo.getTaskId();
							task[1] = exeInfo.getTaskUrl();
							break;
						}
					} else if (ListUtil.isNotEmpty(exeInfo.getCandidateUserList())) {
						for (SimpleUserInfo user : exeInfo.getCandidateUserList()) {
							if (user.getUserId() == executor) {
								task[0] = exeInfo.getTaskId();
								task[1] = exeInfo.getTaskUrl();
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取执行人任务出错", e);
		}
		return task;
	}
	
	/**
	 * 返回和子系统匹配的表单客户端
	 * @param request
	 * @return
	 */
	private FormService getSystemMatchedFormService(String sysNameOrMoudle) {
		//默认PM 项目管理客户端
		FormService formService = formServiceClient;
		if ("FM".equals(sysNameOrMoudle) || "fundMg".equals(sysNameOrMoudle)) {//资金
			formService = formServiceFmClient;
		} else if ("AM".equals(sysNameOrMoudle) || "assetMg".equals(sysNameOrMoudle)) {//资产
			formService = formServiceAmClient;
		} else if ("RM".equals(sysNameOrMoudle) || "reportMg".equals(sysNameOrMoudle)) { //报表
		} else if ("CRM".equals(sysNameOrMoudle) || "customerMg".equals(sysNameOrMoudle)) { //客户管理
			formService = formServiceCrmClient;
		}
		return formService;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T getBean(String beanName) {
		if (null == context) {
			throw new NullPointerException("spring 上下文对象未初始化，无法完成bean的查找！");
		}
		if (null != context) {
			Object obj = context.getBean(beanName);
			if (null != obj) {
				return (T) obj;
			}
		}
		return null;
	}
	
	private static ApplicationContext context = null;
	
	public ApplicationContext getApplicationContext() {
		return context;
	}
}
