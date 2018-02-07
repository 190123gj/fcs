package com.born.fcs.face.web.controller.project.afterloan.risklevel;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.bpm.service.info.WorkflowProcessLog;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.pm.ws.enums.EvaluationTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.RiskLevelEnum;
import com.born.fcs.pm.ws.info.common.CollectionInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.risklevel.FRiskLevelInfo;
import com.born.fcs.pm.ws.info.risklevel.FRiskLevelScoreTemplateInfo;
import com.born.fcs.pm.ws.info.risklevel.RiskLevelInfo;
import com.born.fcs.pm.ws.order.bpm.TaskOpinion;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.risklevel.FRiskLevelOrder;
import com.born.fcs.pm.ws.order.risklevel.RiskLevelQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

/**
 * 
 * 项目风险等级评级(初评/复评)
 * 
 * @author lirz
 * 
 * 2016-5-16 下午3:32:41
 */
@Controller
@RequestMapping("projectMg/riskLevel")
public class RiskLevelController extends WorkflowBaseController {
	
	private static final String VM_PATH = "/projectMg/afterLoanMg/riskLevel/";
	
	@Override
	protected String[] getMoneyInputNameArray() {
		return new String[] { "creditAmount" };
	}
	
	/**
	 * 查询列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model, RiskLevelQueryOrder queryOrder) {
		
		try {
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<RiskLevelInfo> batchResult = riskLevelServiceClient
				.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错");
		}
		
		return VM_PATH + "list.vm";
	}
	
	@RequestMapping("view.htm")
	public String view(@RequestParam(value = "formId", required = true) long formId,
						HttpServletRequest request, Model model) {
		
		try {
			FRiskLevelInfo info = riskLevelServiceClient.findByFormId(formId);
			model.addAttribute("info", info);
			FormInfo form = formServiceClient.findByFormId(formId);
			model.addAttribute("form", form);
			
			model.addAttribute("levels", RiskLevelEnum.getAllEnum());
			
			//审核记录
			setAuditHistory2Page(form, model);
		} catch (Exception e) {
			logger.error("查询出错");
		}
		
		return VM_PATH + "addFormView.vm";
	}
	
	@RequestMapping("edit.htm")
	public String edit(	@RequestParam(value = "formId", required = false, defaultValue = "0") long formId,
						HttpServletRequest request, Model model) {
		model.addAttribute("formId", formId);
		
		FRiskLevelInfo info = null;
		if (formId > 0) {
			info = riskLevelServiceClient.findByFormId(formId);
			model.addAttribute("info", info);
			FormInfo form = formServiceClient.findByFormId(formId);
			model.addAttribute("form", form);
		} else {
			String projectCode = request.getParameter("projectCode");
			if (StringUtil.isNotBlank(projectCode)) {
				info = new FRiskLevelInfo();
				ProjectInfo project = projectServiceClient.queryByCode(projectCode, false);
				model.addAttribute("project", project);
				if (null != project) {
					info.setProjectCode(projectCode);
					info.setProjectName(project.getProjectName());
					info.setCustomerId(project.getCustomerId());
					info.setCustomerName(project.getCustomerName());
					info.setCreditAmount(project.getAmount());
					info.setBusiType(project.getBusiType());
					info.setBusiTypeName(project.getBusiTypeName());
				}
				model.addAttribute("info", info);
				addTemplates(info);
			}
		}
		
		model.addAttribute("levels", RiskLevelEnum.getAllEnum());
		
		return VM_PATH + "addForm.vm";
	}
	
	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, FRiskLevelOrder order) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		
		String tipPrefix = "保存项目风险等级评级-初评";
		try {
			order.setFormCode(FormCodeEnum.RISK_LEVEL);
			order.setCheckIndex(0);
			setSessionLocalInfo2Order(order);
			if ("流动资金贷款".equals(order.getProjectType())) {
				//不能修改，且初评复评时评分一致
				order.getProject().get(0)
					.setReevaluation(order.getProject().get(0).getEvaluation());
				order.getProject().get(1)
					.setReevaluation(order.getProject().get(1).getEvaluation());
			}
			
			FormBaseResult result = riskLevelServiceClient.save(order);
			//			if (result.isSuccess() && null != order.getCheckStatus() && order.getCheckStatus() == 1) {
			//				formSubmit(order, result.getFormInfo().getFormId(), json, "PM");
			//			} else {
			//前端保存成功后，再提交走流程
			json = toJSONResult(result, tipPrefix);
			//			}
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	@RequestMapping("riskEdit.htm")
	public String riskEdit(@RequestParam(value = "formId", required = true) long formId,
							HttpServletRequest request, Model model) {
		try {
			FormInfo form = formServiceClient.findByFormId(formId);
			if (null != form) {
				model.addAttribute("form", form);// 表单信息
				model.addAttribute("formCode", form.getFormCode());
				initWorkflow(model, form, request.getParameter("taskId"));
			}
			FRiskLevelInfo info = riskLevelServiceClient.findByFormId(formId);
			model.addAttribute("info", info);
		} catch (Exception e) {
			logger.error("查询出错");
		}
		
		model.addAttribute("levels", RiskLevelEnum.getAllEnum());
		
		return VM_PATH + "riskEdit.vm";
	}
	
	//	@ResponseBody
	@RequestMapping("riskEditSubmit.json")
	public String riskEditSubmit(HttpServletRequest request, HttpServletResponse response,
									FRiskLevelOrder order) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			printHttpResponse(response, json);
			return null;
		}
		
		String tipPrefix = "保存项目风险等级评级-复评";
		try {
			order.setFormCode(FormCodeEnum.RISK_LEVEL);
			order.setCheckIndex(0);
			//			order.setCheckStatus(1); //没有暂存，直接提交
			setSessionLocalInfo2Order(order);
			FormBaseResult result = riskLevelServiceClient.save(order);
			//二次走流程
			if (result.isSuccess() && order.getCheckStatus() != null && order.getCheckStatus() == 1) {
				//				formSubmit(order, result.getFormInfo().getFormId(), json);
				//TODO 提交流程
				doNext(request, response, TaskOpinion.STATUS_AGREE);
				return null;
			}
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		printHttpResponse(response, json);
		return null;
	}
	
	@RequestMapping("audit.htm")
	public String audit(@RequestParam(value = "formId", required = true) long formId,
						HttpServletRequest request, Model model) {
		try {
			FormInfo form = formServiceClient.findByFormId(formId);
			if (null != form) {
				model.addAttribute("form", form);// 表单信息
				model.addAttribute("formCode", form.getFormCode());
				initWorkflow(model, form, request.getParameter("taskId"));
				setAuditHistory2Page(form, model);
			}
			FRiskLevelInfo info = riskLevelServiceClient.findByFormId(formId);
			model.addAttribute("info", info);
		} catch (Exception e) {
			logger.error("查询出错");
		}
		
		return VM_PATH + "auditForm.vm";
	}
	
	@RequestMapping("audit2.htm")
	public String audit2(@RequestParam(value = "formId", required = true) long formId,
							HttpServletRequest request, Model model) {
		try {
			FormInfo form = formServiceClient.findByFormId(formId);
			if (null != form) {
				model.addAttribute("form", form);// 表单信息
				model.addAttribute("formCode", form.getFormCode());
				initWorkflow(model, form, request.getParameter("taskId"));
				model.addAttribute("check", request.getParameter("check"));
				setAuditHistory2Page(form, model);
			}
			
			model.addAttribute("levels", RiskLevelEnum.getAllEnum());
			FRiskLevelInfo info = riskLevelServiceClient.findByFormId(formId);
			model.addAttribute("info", info);
		} catch (Exception e) {
			logger.error("查询出错");
		}
		
		return VM_PATH + "auditFormSecond.vm";
	}
	
	private void addTemplates(FRiskLevelInfo info) {
		List<FRiskLevelScoreTemplateInfo> enterprise = new ArrayList<>();
		List<FRiskLevelScoreTemplateInfo> project = new ArrayList<>();
		List<FRiskLevelScoreTemplateInfo> counter = new ArrayList<>();
		
		List<FRiskLevelScoreTemplateInfo> list = riskLevelServiceClient.queryTemplates();
		
		FRiskLevelScoreTemplateInfo last = null;
		boolean begin = false;
		double evaluation = 0d;
		double reevaluation = 0d;
		for (FRiskLevelScoreTemplateInfo template : list) {
			EvaluationTypeEnum type = EvaluationTypeEnum.getByCode(template.getEvaluationType());
			if (type == EvaluationTypeEnum.ENTERPRISE) {
				enterprise.add(template);
			} else if (type == EvaluationTypeEnum.PROJECT) {
				project.add(template);
			} else if (type == EvaluationTypeEnum.COUNTER) {
				counter.add(template);
				// ---- 计算小计项总分 begin ----
				if (null != last && StringUtil.isBlank(template.getIndexNo())) {
					begin = true;
					evaluation += template.getEvaluation();
					reevaluation += template.getReevaluation();
					last.setEvaluation(evaluation);
					last.setReevaluation(reevaluation);
					last.setIdFlag("subth_" + last.getId());
					template.setClassFlag("subth_" + last.getId());
				}
				if (begin && StringUtil.isNotBlank(template.getIndexNo())) {
					begin = false;
					evaluation = 0d;
					reevaluation = 0d;
				}
				if (StringUtil.isNotBlank(template.getIndexNo())) {
					last = template;
				}
				// ---- 计算小计项总分 end ----
			}
		}
		
		info.setEnterprise(enterprise);
		// 计算倍数 webservice不能返回map
		CollectionInfo multiple = riskLevelServiceClient.queryMultiple(info.getProjectCode());
		project.get(0).setEvaluation(
			Double.parseDouble(multiple.get("CAPITAL_MULTIPLE").toString()));
		project.get(1)
			.setEvaluation(Double.parseDouble(multiple.get("INCOME_MULTIPLE").toString()));
		info.setProject(project);
		info.setCounter(counter);
	}
	
	@ResponseBody
	@RequestMapping("queryProjects.json")
	public Object queryProjects(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String tipPrefix = "查询项目信息";
		
		try {
			ProjectQueryOrder queryOrder = new ProjectQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal != null
				&& (DataPermissionUtil.isBusiManager() || (DataPermissionUtil.isLegalManager() && StringUtil
					.equals("my", request.getParameter("select"))))) {
				queryOrder.setBusiManagerId(sessionLocal.getUserId());
			}
			String phasesStrList = request.getParameter("phasesList");
			if (StringUtil.isNotBlank(phasesStrList)) {
				List<ProjectPhasesEnum> phasesList = new ArrayList<>();
				String[] phases = phasesStrList.split(",");
				for (String s : phases) {
					ProjectPhasesEnum e = ProjectPhasesEnum.getByCode(s);
					if (null != e) {
						phasesList.add(e);
					}
				}
				queryOrder.setPhasesList(phasesList);
			}
			String busiTypeStr = request.getParameter("busiTypes");
			if (StringUtil.isNotBlank(busiTypeStr)) {
				List<String> busiTypeList = new ArrayList<>();
				String[] busiTypes = busiTypeStr.split(",");
				for (String s : busiTypes) {
					if (StringUtil.isNotBlank(s)) {
						busiTypeList.add(s);
					}
				}
				queryOrder.setBusiTypeList(busiTypeList);
			}
			
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			QueryBaseBatchResult<ProjectInfo> batchResult = riskLevelServiceClient
				.queryProjects(queryOrder);
			
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (ProjectInfo info : batchResult.getPageList()) {
					JSONObject json = new JSONObject();
					json.put("projectCode", info.getProjectCode());
					json.put("projectName", info.getProjectName());
					json.put("customerId", info.getCustomerId());
					json.put("customerName", info.getCustomerName());
					json.put("amount", info.getAmount().toStandardString());
					json.put("busiType", info.getBusiType());
					json.put("busiTypeName", info.getBusiTypeName());
					dataList.add(json);
				}
			}
			data.put("pageCount", batchResult.getPageCount());
			data.put("pageNumber", batchResult.getPageNumber());
			data.put("pageSize", batchResult.getPageSize());
			data.put("totalCount", batchResult.getTotalCount());
			data.put("pageList", dataList);
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		return result;
	}
	
	// 导出word
	@RequestMapping("toExportDoc.htm")
	public String toExportDoc(HttpServletRequest request, Model model, long formId) {
		try {
			FRiskLevelInfo info = riskLevelServiceClient.findByFormId(formId);
			model.addAttribute("info", info);
			FormInfo form = formServiceClient.findByFormId(formId);
			model.addAttribute("form", form);
			
			model.addAttribute("levels", RiskLevelEnum.getAllEnum());
			
			//审核记录
			QueryBaseBatchResult<WorkflowProcessLog> workflows = workflowEngineWebClient
				.getProcessOpinionByActInstId(form.getActInstId() + "");
			model.addAttribute("workflows", workflows);
		} catch (Exception e) {
			logger.error("查询出错");
			return VM_PATH + "exportError.vm";
		}
		
		return VM_PATH + "viewReport.vm";
		
	}
	
}
