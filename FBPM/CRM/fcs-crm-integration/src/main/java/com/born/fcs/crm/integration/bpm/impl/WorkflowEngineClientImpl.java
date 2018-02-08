package com.born.fcs.crm.integration.bpm.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.crm.integration.bpm.FlowServiceProxy;
import com.born.fcs.crm.integration.bpm.TaskEntity;
import com.born.fcs.crm.integration.bpm.WorkflowEngineClient;
import com.born.fcs.crm.integration.bpm.process.ProcessServiceProxy;
import com.born.fcs.crm.integration.bpm.result.StartFlowResult;
import com.born.fcs.crm.integration.bpm.result.WorkflowResult;
import com.born.fcs.crm.integration.common.PropertyConfigService;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.XmlUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.order.bpm.BpmFinishTaskInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.TaskNodeInfo;
import com.born.fcs.pm.ws.order.bpm.TaskOpinion;
import com.born.fcs.pm.ws.order.bpm.TaskSearchOrder;
import com.born.fcs.pm.ws.order.bpm.WorkflowAssignOrder;
import com.born.fcs.pm.ws.order.bpm.WorkflowDoNextOrder;
import com.born.fcs.pm.ws.order.bpm.WorkflowProcessLog;
import com.born.fcs.pm.ws.order.bpm.WorkflowRecoverOrder;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.WorkflowTaskInfo;
import com.born.fcs.pm.ws.order.bpm.enums.RecoverStatusEnum;
import com.born.fcs.pm.ws.order.bpm.enums.WorkflowStatusEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

@Service("workflowEngineClient")
public class WorkflowEngineClientImpl implements WorkflowEngineClient {
	@Autowired
	PropertyConfigService propertyConfigService;
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public StartFlowResult startFlow(WorkflowStartOrder startOrder) {
		StartFlowResult flowResult = new StartFlowResult();
		try {
			startOrder.check();
			
			FlowServiceProxy flowServiceProxy = new FlowServiceProxy(
				propertyConfigService.getBmpServiceFlowService());
			JSONObject json = new JSONObject();
			json.put("typeId", startOrder.getFormCodeEnum().flowCode());
			json.put("account", startOrder.getProcessUserName());
			logger.info("加载可以用的流程传人参数:" + json.toJSONString());
			String jsonString = flowServiceProxy.getMyFlowListJson(json.toJSONString());
			logger.info("加载可以用的流程:" + jsonString);
			Map<String, Object> resultMap = MiscUtil.parseJSON(jsonString);
			if (resultMap == null) {
				flowResult.setFcsResultEnum(FcsResultEnum.CALL_REMOTE_SERVICE_ERROR);
				flowResult.setSuccess(false);
			} else {
				long totalCount = NumberUtil.parseLong(String.valueOf(resultMap.get("totalCount")));
				if (totalCount <= 0) {
					flowResult.setFcsResultEnum(FcsResultEnum.WORKFLOW_NO_FUND);
					flowResult.setSuccess(false);
				} else {
					List flowList = (List) resultMap.get("list");
					Map flowMap = (Map) flowList.get(0);
					Map bpmDefinitionMap = (Map) flowMap.get("obj");
					Long defId = (Long) bpmDefinitionMap.get("defId");
					String defKey = (String) bpmDefinitionMap.get("defKey");
					String subject = (String) bpmDefinitionMap.get("subject");
					if (StringUtil.isNotBlank(startOrder.getCustomTaskName())) {
						subject = startOrder.getCustomTaskName() + "|"
									+ DateUtil.simpleDate(new Date());
					} else {
						subject = subject + "-" + startOrder.getProcessRealName() + "|"
									+ DateUtil.simpleDate(new Date());
					}
					
					//返回回去更新form
					flowResult.setSubject(subject);
					
					//特殊字符处理,防止bpm xml解析出错
					subject = subject.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
						.replaceAll(">", "&gt;").replaceAll("\"", "&quot;")
						.replaceAll("'", "&apos;");
					
					ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
						propertyConfigService.getBmpServiceProcessService());
					StringBuffer xmlBuffer = new StringBuffer();
					xmlBuffer
						.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><req actDefId=\"\" flowKey=\""
								+ defKey
								+ "\" subject=\""
								+ subject
								+ "\" account=\""
								+ startOrder.getProcessUserName()
								+ "\" businessKey=\""
								+ startOrder.getFormInfo().getUserId() + "\">");
					if (ListUtil.isNotEmpty(startOrder.getFields())) {
						for (FlowVarField field : startOrder.getFields()) {
							if (field.getVarName() == null || field.getVarType() == null)
								continue;
							xmlBuffer.append("<var varName=\"" + field.getVarName()
												+ "\" varVal=\"" + field.getVarVal()
												+ "\" varType=\"" + field.getVarType().code()
												+ "\" dateFormat=\"\"/>");
						}
					}
					xmlBuffer.append("</req>");
					logger.info("开始流程启动发送的xml:{}", xmlBuffer);
					String xml = processServiceProxy.start(xmlBuffer.toString());
					logger.info("开始流程启动结果:" + xml);
					Document doc = XmlUtil.parseXML(xml);
					if (doc != null) {
						Element root = doc.getRootElement();
						long runId = NumberUtil.parseLong(root.attributeValue("runId"), -1);
						flowResult.setRunId(runId);
						long actInstId = NumberUtil.parseLong(root.attributeValue("actInstId"), -1);
						flowResult.setActInstId(actInstId);
						
						flowResult.setActDefId(root.attributeValue("actDefId"));
						long defId1 = NumberUtil.parseLong(root.attributeValue("defId"), -1);
						flowResult.setDefId(defId1);
						String businessUrl = root.attributeValue("businessUrl");
						flowResult.setBusinessUrl(businessUrl);
						String businessKey = root.attributeValue("businessKey");
						flowResult.setBusinessKey(businessKey);
						String creator = root.attributeValue("creator");
						flowResult.setCreator(creator);
						String startOrgName = root.attributeValue("startOrgName");
						flowResult.setStartOrgName(startOrgName);
						String processName = root.attributeValue("processName");
						flowResult.setProcessName(processName);
						String subject1 = root.attributeValue("subject");
						flowResult.setSubject(subject1);
						String strCreatetime = root.attributeValue("createtime");
						if (StringUtil.isNotBlank(strCreatetime)) {
							Date createtime = DateUtil.parse(strCreatetime);
							flowResult.setCreatetime(createtime);
							
						}
						long creatorId = NumberUtil.parseLong(root.attributeValue("creatorId"), -1);
						flowResult.setCreatorId(creatorId);
						long duration = NumberUtil.parseLong(root.attributeValue("duration"), -1);
						flowResult.setDuration(duration);
						if (StringUtil.isNotBlank(root.attributeValue("endTime"))) {
							Date endTime = DateUtil.parse(root.attributeValue("endTime"));
							flowResult.setEndTime(endTime);
						}
						long parentId = NumberUtil.parseLong(root.attributeValue("parentId"), -1);
						flowResult.setParentId(parentId);
						
						RecoverStatusEnum recover = RecoverStatusEnum.getByBpmStatus(NumberUtil
							.parseInt(root.attributeValue("recover")));
						flowResult.setRecover(recover);
						WorkflowStatusEnum status = WorkflowStatusEnum.getByBpmStatus(NumberUtil
							.parseInt(root.attributeValue("status")));
						flowResult.setStatus(status);
						List<Element> taskList = root.elements("task");
						List<TaskEntity> taskEntities = Lists.newArrayList();
						if (ListUtil.isNotEmpty(taskList)) {
							for (Element e : taskList) {
								TaskEntity taskInfo = new TaskEntity();
								List attributes = e.attributes();
								Map<String, Object> datamap = new HashMap<String, Object>();
								for (Iterator it = attributes.iterator(); it.hasNext();) {
									Attribute attribute = (Attribute) it.next();
									if (StringUtil.equals(attribute.getName(), "taskId")) {
										taskInfo.setId(attribute.getValue());
									} else
										datamap.put(attribute.getName(), attribute.getValue());
									
								}
								
								MiscUtil.setInfoPropertyByMap(datamap, taskInfo);
								taskEntities.add(taskInfo);
							}
						}
						if (flowResult.getRunId() > 0) {
							flowResult.setSuccess(true);
							flowResult.setSubject(subject1);
							flowResult.setNextTaskList(taskEntities);
						}
					}
					
				}
			}
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			flowResult.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			flowResult.setSuccess(false);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			flowResult.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			flowResult.setSuccess(false);
		}
		return flowResult;
	}
	
	@Override
	public List<WorkflowTaskInfo> getTaskList(String runId) {
		List<WorkflowTaskInfo> taskInfos = Lists.newArrayList();
		try {
			ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
				propertyConfigService.getBmpServiceProcessService());
			String xmlString = processServiceProxy.getTasksByRunId(runId);
			Document doc = XmlUtil.parseXML(xmlString);
			if (doc != null) {
				Element root = doc.getRootElement();
				List<Element> elements = root.elements("task");
				if (ListUtil.isNotEmpty(elements)) {
					for (Element e : elements) {
						WorkflowTaskInfo taskInfo = new WorkflowTaskInfo();
						List attributes = e.attributes();
						Map<String, Object> datamap = new HashMap<String, Object>();
						for (Iterator it = attributes.iterator(); it.hasNext();) {
							Attribute attribute = (Attribute) it.next();
							datamap.put(attribute.getName(), attribute.getValue());
						}
						MiscUtil.setInfoPropertyByMap(datamap, taskInfo);
						//						if (StringUtil.isBlank(taskInfo.getAssignee())
						//							&& "0".equals(taskInfo.getAssignee()))
						taskInfos.add(taskInfo);
					}
				}
				
			}
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
		}
		return taskInfos;
	}
	
	@Override
	public WorkflowTaskInfo getTaskInfo(String taskId) {
		WorkflowTaskInfo taskInfo = new WorkflowTaskInfo();
		try {
			if (StringUtil.isEmpty(taskId))
				return null;
			ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
				propertyConfigService.getBmpServiceProcessService());
			String xmlString = processServiceProxy.getTaskByTaskId(taskId);
			logger.info("获取流程接点:" + xmlString);
			Document doc = XmlUtil.parseXML(xmlString);
			if (doc != null) {
				Element root = doc.getRootElement();
				List attributes = root.attributes();
				Map<String, Object> datamap = new HashMap<String, Object>();
				for (Iterator it = attributes.iterator(); it.hasNext();) {
					Attribute attribute = (Attribute) it.next();
					datamap.put(attribute.getName(), attribute.getValue());
				}
				MiscUtil.setInfoPropertyByMap(datamap, taskInfo);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		}
		return taskInfo;
	}
	
	@Override
	public TaskNodeInfo getTaskNode(String taskId) {
		TaskNodeInfo taskNodeInfo = new TaskNodeInfo();
		try {
			if (StringUtil.isEmpty(taskId))
				return null;
			ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
				propertyConfigService.getBmpServiceProcessService());
			String xmlString = processServiceProxy.getTaskNode(taskId);
			logger.info("获取流程接点:" + xmlString);
			Document doc = XmlUtil.parseXML(xmlString);
			if (doc != null) {
				Element root = doc.getRootElement();
				addNodeAttributeValue(root, taskNodeInfo);
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
	
	private void addNodeAttributeValue(Element taskNode, TaskNodeInfo childNodeInfo) {
		childNodeInfo.setNodeId(taskNode.attributeValue("nodeId"));
		childNodeInfo.setNodeName(taskNode.attributeValue("nodeName"));
		childNodeInfo.setNodeType(taskNode.attributeValue("nodeType"));
		childNodeInfo.setFormUrl(taskNode.attributeValue("formUrl"));
		childNodeInfo.setInformType(taskNode.attributeValue("informType"));
		childNodeInfo.setCanSelectUser("true".equals(taskNode.attributeValue("canSelectedUser")));
	}
	
	@Override
	public WorkflowResult doNext(WorkflowDoNextOrder doNextOrder) {
		WorkflowResult flowResult = new WorkflowResult();
		try {
			doNextOrder.check();
			
			ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
				propertyConfigService.getBmpServiceProcessService());
			Element doc = XmlUtil.createDoc(null, "req");
			doc.addAttribute("taskId", doNextOrder.getTaskId());
			doc.addAttribute("account", doNextOrder.getUserAccount());
			doc.addAttribute("voteAgree", doNextOrder.getVoteAgree());
			doc.addAttribute("nextNodeId", StringUtil.defaultIfBlank(doNextOrder.getNextNodeId()));
			doc.addAttribute("voteContent", doNextOrder.getVoteContent());
			doc.addAttribute("isBack", StringUtil.defaultIfBlank(doNextOrder.getIsBack()));
			StringBuffer xmlBuffer = new StringBuffer();
			xmlBuffer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><req taskId=\""
								+ doNextOrder.getTaskId() + "\" account=\""
								+ doNextOrder.getUserAccount() + "\" voteAgree=\""
								+ doNextOrder.getVoteAgree() + "\" nextNodeId=\""
								+ StringUtil.defaultIfBlank(doNextOrder.getNextNodeId())
								+ "\" voteContent=\"" + doNextOrder.getVoteContent()
								+ "\" isBack=\""
								+ StringUtil.defaultIfBlank(doNextOrder.getIsBack()) + "\">");
			if (ListUtil.isNotEmpty(doNextOrder.getFields())) {
				for (FlowVarField field : doNextOrder.getFields()) {
					if (field.getVarName() == null || field.getVarType() == null)
						continue;
					Element docTag = XmlUtil.createDoc(doc, "var");
					docTag.addAttribute("varName", field.getVarName());
					docTag.addAttribute("varVal", StringUtil.defaultIfBlank(field.getVarVal()));
					docTag.addAttribute("varType", field.getVarType().code());
					docTag.addAttribute("dateFormat",
						StringUtil.defaultIfBlank(field.getVarType().getDateFormat()));
					
				}
			}
			String sendXml = doc.asXML();
			logger.info("流程处理发送:" + sendXml);
			String xml = processServiceProxy.doNext(sendXml);
			logger.info("流程处理接收:" + xml);
			Document xmlTarge = XmlUtil.parseXML(xml);
			if (xmlTarge != null) {
				if (doc != null) {
					Element root = xmlTarge.getRootElement();
					if (!StringUtil.equals(root.getName(), "run")) {
						flowResult.setFcsResultEnum(FcsResultEnum.DO_ACTION_STATUS_ERROR);
						flowResult.setSuccess(false);
						return flowResult;
					}
					long runId = NumberUtil.parseLong(root.attributeValue("runId"), -1);
					
					String businessUrl = root.attributeValue("businessUrl");
					flowResult.setBusinessUrl(businessUrl);
					String businessKey = root.attributeValue("businessKey");
					flowResult.setBusinessKey(businessKey);
					String creator = root.attributeValue("creator");
					flowResult.setCreator(creator);
					String startOrgName = root.attributeValue("startOrgName");
					flowResult.setStartOrgName(startOrgName);
					String processName = root.attributeValue("processName");
					flowResult.setProcessName(processName);
					String subject1 = root.attributeValue("subject");
					flowResult.setSubject(subject1);
					
					Date createtime = DateUtil.parse(root.attributeValue("createtime"));
					flowResult.setCreatetime(createtime);
					long creatorId = NumberUtil.parseLong(root.attributeValue("creatorId"), -1);
					flowResult.setCreatorId(creatorId);
					long duration = NumberUtil.parseLong(root.attributeValue("duration"), -1);
					flowResult.setDuration(duration);
					if (StringUtil.isNotBlank(root.attributeValue("endTime"))) {
						Date endTime = DateUtil.parse(root.attributeValue("endTime"));
						flowResult.setEndTime(endTime);
					}
					long parentId = NumberUtil.parseLong(root.attributeValue("parentId"), -1);
					flowResult.setParentId(parentId);
					
					RecoverStatusEnum recover = RecoverStatusEnum.getByBpmStatus(NumberUtil
						.parseInt(root.attributeValue("recover")));
					flowResult.setRecover(recover);
					WorkflowStatusEnum status = WorkflowStatusEnum.getByBpmStatus(NumberUtil
						.parseInt(root.attributeValue("status")));
					flowResult.setStatus(status);
					WorkflowStatusEnum processAfterStatus = WorkflowStatusEnum
						.getByBpmStatus(NumberUtil.parseInt(root
							.attributeValue("processAfterStatus")));
					flowResult.setProcessAfterStatus(processAfterStatus);
					List<Element> taskList = root.elements("task");
					List<TaskEntity> taskEntities = Lists.newArrayList();
					if (ListUtil.isNotEmpty(taskList)) {
						for (Element e : taskList) {
							TaskEntity taskInfo = new TaskEntity();
							List attributes = e.attributes();
							Map<String, Object> datamap = new HashMap<String, Object>();
							for (Iterator it = attributes.iterator(); it.hasNext();) {
								Attribute attribute = (Attribute) it.next();
								if (StringUtil.equals(attribute.getName(), "taskId")) {
									taskInfo.setId(attribute.getValue());
								} else
									datamap.put(attribute.getName(), attribute.getValue());
								
							}
							
							MiscUtil.setInfoPropertyByMap(datamap, taskInfo);
							taskEntities.add(taskInfo);
						}
					}
					
					flowResult.setSuccess(true);
					flowResult.setNextTaskList(taskEntities);
				}
			}
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			flowResult.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			flowResult.setSuccess(false);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			flowResult.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			flowResult.setSuccess(false);
		}
		return flowResult;
		
	}
	
	@Override
	public WorkflowResult defRecover(WorkflowRecoverOrder recoverOrder) {
		WorkflowResult flowResult = new WorkflowResult();
		try {
			recoverOrder.check();
			
			ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
				propertyConfigService.getBmpServiceProcessService());
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("runId", recoverOrder.getRunId());
			jsonObject.put("informType", recoverOrder.getInformType());
			jsonObject.put("opinion", recoverOrder.getOpinion());
			jsonObject.put("backToStart", recoverOrder.getBackToStart());
			
			String json = jsonObject.toJSONString();
			logger.info("流程处理发送:userId={},json={}", recoverOrder.getUserId(), json);
			String returnJson = processServiceProxy.defRecover(
				String.valueOf(recoverOrder.getUserId()), json);
			logger.info("流程处理接收:" + returnJson);
			HashMap<String, Object> dataMap = MiscUtil.parseJSON(returnJson);
			if (dataMap != null) {
				if (StringUtil.equals("0", String.valueOf(dataMap.get("state")))) {
					flowResult.setSuccess(true);
					flowResult.setMessage((String) dataMap.get("msg"));
					List<TaskEntity> nextTaskList = Lists.newArrayList();
					if (dataMap.get("taskId") != null) {
						TaskEntity taskEntity = new TaskEntity();
						taskEntity.setId(String.valueOf(dataMap.get("taskId")));
						nextTaskList.add(taskEntity);
					}
					flowResult.setNextTaskList(nextTaskList);
				} else {
					flowResult.setSuccess(false);
					flowResult.setMessage((String) dataMap.get("msg"));
				}
			} else {
				flowResult.setSuccess(false);
				flowResult.setMessage("撤回失败");
			}
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			flowResult.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			flowResult.setSuccess(false);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			flowResult.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			flowResult.setSuccess(false);
		}
		return flowResult;
		
	}
	
	@Override
	public WorkflowResult taskAssign(WorkflowAssignOrder workflowAssignOrder) {
		WorkflowResult flowResult = new WorkflowResult();
		try {
			workflowAssignOrder.check();
			
			ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
				propertyConfigService.getBmpServiceProcessService());
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("taskId", workflowAssignOrder.getTaskId());
			jsonObject.put("assigneeId", workflowAssignOrder.getAssigneeId());
			jsonObject.put("assigneeName", workflowAssignOrder.getAssigneeName());
			jsonObject.put("memo", workflowAssignOrder.getMemo());
			jsonObject.put("informType", workflowAssignOrder.getInformType());
			jsonObject.put("userId", workflowAssignOrder.getUserId());
			String json = jsonObject.toJSONString();
			logger.info("流程处理发送:userId={},json={}", json);
			String returnJson = processServiceProxy.taskAssign(json);
			logger.info("流程处理接收:" + returnJson);
			HashMap<String, Object> dataMap = MiscUtil.parseJSON(returnJson);
			if (dataMap != null) {
				if (StringUtil.equals("0", String.valueOf(dataMap.get("state")))) {
					flowResult.setSuccess(true);
					flowResult.setMessage((String) dataMap.get("msg"));
				} else {
					flowResult.setSuccess(false);
					flowResult.setMessage((String) dataMap.get("msg"));
				}
			} else {
				flowResult.setSuccess(false);
				flowResult.setMessage("交办失败");
			}
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			flowResult.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			flowResult.setSuccess(false);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			flowResult.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			flowResult.setSuccess(false);
		}
		return flowResult;
		
	}
	
	@Override
	public WorkflowResult endingWorkflow(WorkflowDoNextOrder doNextOrder) {
		WorkflowResult flowResult = new WorkflowResult();
		try {
			doNextOrder.check();
			
			ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
				propertyConfigService.getBmpServiceProcessService());
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("taskId", doNextOrder.getTaskId());
			jsonObject.put("memo", doNextOrder.getVoteContent());
			String userId = String.valueOf(doNextOrder.getUserId());
			String json = jsonObject.toJSONString();
			logger.info("流程处理发送:userId={} {}", userId, json);
			String returnJson = processServiceProxy.taskEndProcess(userId, json);
			logger.info("流程处理接收:" + returnJson);
			HashMap<String, Object> dataMap = MiscUtil.parseJSON(returnJson);
			if (dataMap != null) {
				if (StringUtil.equals("0", String.valueOf(dataMap.get("state")))) {
					flowResult.setSuccess(true);
					flowResult.setMessage((String) dataMap.get("msg"));
				} else {
					flowResult.setSuccess(false);
					flowResult.setMessage((String) dataMap.get("msg"));
				}
			} else {
				flowResult.setSuccess(false);
				flowResult.setMessage("结束流程失败");
			}
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			flowResult.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			flowResult.setSuccess(false);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			flowResult.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			flowResult.setSuccess(false);
		}
		return flowResult;
		
	}
	
	@Override
	public List<TaskOpinion> getTaskUsers(String strTaskId) {
		List<TaskOpinion> taskOpinions = Lists.newArrayList();
		try {
			Assert.hasText(strTaskId);
			logger.info("获取步骤执行人:strTaskId={}", strTaskId);
			FlowServiceProxy flowServiceProxy = new FlowServiceProxy(
				propertyConfigService.getBmpServiceFlowService());
			String returnJson = flowServiceProxy.getTaskUsers(strTaskId);
			
			logger.info("获取步骤执行人:" + returnJson);
			HashMap<String, Object> dataMap = MiscUtil.parseJSON(returnJson);
			if (dataMap != null) {
				if (StringUtil.equals("1", String.valueOf(dataMap.get("result")))) {
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
	public List<TaskOpinion> getTaskUsers(String instanceId, String nodeId) {
		List<TaskOpinion> taskOpinions = Lists.newArrayList();
		try {
			Assert.hasText(instanceId);
			Assert.hasText(nodeId);
			logger.info("获取步骤执行人:instanceId={},nodeId={}", instanceId, nodeId);
			FlowServiceProxy flowServiceProxy = new FlowServiceProxy(
				propertyConfigService.getBmpServiceFlowService());
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
							Map taskExeStatus = (Map) item.get("taskExeStatus");
							if (taskExeStatus != null) {
								Boolean isRead = (Boolean) taskExeStatus.get("read");
								if (isRead != null)
									taskOpinion.setRead(isRead);
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
	public FcsBaseResult readTask(String actInstId, String taskId) {
		FcsBaseResult baseResult = new FcsBaseResult();
		try {
			Assert.hasText(actInstId);
			Assert.hasText(taskId);
			logger.info("获取步骤执行人:actInstId={},taskId={}", actInstId, taskId);
			FlowServiceProxy flowServiceProxy = new FlowServiceProxy(
				propertyConfigService.getBmpServiceFlowService());
			String returnJson = flowServiceProxy.readTask(actInstId, taskId);
			logger.info("获取步骤执行人:" + returnJson);
			HashMap<String, Object> dataMap = MiscUtil.parseJSON(returnJson);
			if (dataMap != null) {
				if (StringUtil.equals("1", String.valueOf(dataMap.get("code")))) {
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
	
	@Override
	public QueryBaseBatchResult<WorkflowTaskInfo> getTasksByAccount(TaskSearchOrder taskSearchOrder) {
		QueryBaseBatchResult<WorkflowTaskInfo> batchResult = new QueryBaseBatchResult<WorkflowTaskInfo>();
		try {
			taskSearchOrder.check();
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
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		}
		return batchResult;
	}
	
	@Override
	public QueryBaseBatchResult<BpmFinishTaskInfo> getFinishTask(TaskSearchOrder taskSearchOrder) {
		QueryBaseBatchResult<BpmFinishTaskInfo> batchResult = new QueryBaseBatchResult<BpmFinishTaskInfo>();
		try {
			taskSearchOrder.check();
			ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
				propertyConfigService.getBmpServiceProcessService());
			String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><req account=\""
							+ taskSearchOrder.getUserName() + "\" taskName=\""
							+ StringUtil.defaultIfBlank(taskSearchOrder.getTaskNodeName())
							+ "\" subject=\""
							+ StringUtil.defaultIfBlank(taskSearchOrder.getSubject())
							+ "\" pageSize=\"" + taskSearchOrder.getPageSize()
							+ "\" currentPage= \"" + taskSearchOrder.getPageNumber() + "\"></req>";
			logger.info("获取代办任务发送xml:{}", xml);
			String xmlString = processServiceProxy.getFinishTask(xml);
			logger.info("获取代办任务结果:{}" + xmlString);
			Document doc = XmlUtil.parseXML(xmlString);
			if (doc != null) {
				
				Element root = doc.getRootElement();
				List<Element> tasks = root.elements("task");
				PageComponent component = new PageComponent(taskSearchOrder, tasks.size());
				Map<String, String> map = null;
				List<BpmFinishTaskInfo> list = new ArrayList<BpmFinishTaskInfo>();
				for (Element element : tasks) {
					BpmFinishTaskInfo taskInfo = new BpmFinishTaskInfo();
					List attributes = element.attributes();
					Map<String, Object> datamap = new HashMap<String, Object>();
					for (Iterator it = attributes.iterator(); it.hasNext();) {
						Attribute attribute = (Attribute) it.next();
						datamap.put(attribute.getName(), attribute.getValue());
					}
					MiscUtil.setInfoPropertyByMap(datamap, taskInfo);
					if (datamap.get("startTime") != null) {
						taskInfo.setStartTime(DateUtil.parse((String) datamap.get("startTime")));
						
					}
					if (datamap.get("endTime") != null) {
						taskInfo.setEndTime(DateUtil.parse((String) datamap.get("endTime")));
						
					}
					if (datamap.get("durTime") != null) {
						taskInfo.setDurTime(NumberUtil.parseLong(String.valueOf(datamap
							.get("durTime"))));
					}
					list.add(taskInfo);
					
				}
				batchResult.initPageParam(component);
				batchResult.setPageList(list);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		}
		return batchResult;
	}
	
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
					if (taskInfo.getCheckStatus() == (int) TaskOpinion.STATUS_AGREE) {
						taskInfo.setCheckStatusMessage("同意");
					} else if (taskInfo.getCheckStatus() == (int) TaskOpinion.STATUS_REFUSE) {
						taskInfo.setCheckStatusMessage("反对");
					} else if (taskInfo.getCheckStatus() == (int) TaskOpinion.STATUS_REJECT
								|| taskInfo.getCheckStatus() == (int) TaskOpinion.STATUS_REJECT_TO_TASK_NODE
								|| taskInfo.getCheckStatus() == (int) TaskOpinion.STATUS_REJECT_TOSTART) {
						taskInfo.setCheckStatusMessage("驳回");
					} else if (taskInfo.getCheckStatus() == (int) TaskOpinion.STATUS_RECOVER
								|| taskInfo.getCheckStatus() == (int) TaskOpinion.STATUS_RECOVER_TOSTART
								|| taskInfo.getCheckStatus() == (int) TaskOpinion.STATUS_REVOKED
								|| taskInfo.getCheckStatus() == (int) TaskOpinion.STATUS_RETRIEVE) {
						taskInfo.setCheckStatusMessage("撤销");
					} else if (taskInfo.getCheckStatus() == (int) TaskOpinion.STATUS_ENDPROCESS) {
						taskInfo.setCheckStatusMessage("终止流程");
					} else if (taskInfo.getCheckStatus() == (int) TaskOpinion.STATUS_RESUBMIT) {
						taskInfo.setCheckStatusMessage("重新提交");
					}
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
}
