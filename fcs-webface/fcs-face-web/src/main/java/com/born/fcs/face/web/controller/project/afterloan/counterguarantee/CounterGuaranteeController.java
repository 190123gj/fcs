package com.born.fcs.face.web.controller.project.afterloan.counterguarantee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ReleaseStatusEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectChannelRelationInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.counterguarantee.FCounterGuaranteeReleaseInfo;
import com.born.fcs.pm.ws.info.counterguarantee.GuaranteeApplyCounterInfo;
import com.born.fcs.pm.ws.info.counterguarantee.ReleaseApplyInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.ProjectCreditConditionInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.counterguarantee.CounterGuaranteeQueryOrder;
import com.born.fcs.pm.ws.order.counterguarantee.FCounterGuaranteeReleaseOrder;
import com.born.fcs.pm.ws.order.counterguarantee.FCounterGuaranteeRepayOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.ProjectCreditConditionQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 解保申请
 * 
 * @author lirz
 * 
 * 2016-5-12 下午6:50:37
 */
@Controller
@RequestMapping("projectMg/counterGuarantee")
public class CounterGuaranteeController extends WorkflowBaseController {
	
	private static final String VM_PATH = "/projectMg/afterLoanMg/timeOutProject/";
	
	@Override
	protected String[] getMoneyInputNameArray() {
		return new String[] { "creditAmount", "releasedAmount", "releasingAmount", "releaseBalance" };
	}
	
	/**
	 * 查询申请列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model,
						CounterGuaranteeQueryOrder queryOrder) {
		
		try {
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<ReleaseApplyInfo> batchResult = counterGuaranteeServiceClient
				.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错");
		}
		
		return VM_PATH + "list.vm";
	}
	
	@ResponseBody
	@RequestMapping("queryProjects.json")
	public Object queryProjects(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String tipPrefix = "查询可申请解保的项目信息";
		
		try {
			QueryProjectBase queryOrder = new ProjectQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal != null
				&& (DataPermissionUtil.isBusiManager() || (DataPermissionUtil.isLegalManager() && StringUtil
					.equals("my", request.getParameter("select"))))) {
				queryOrder.setBusiManagerId(sessionLocal.getUserId());
			}
			
			queryOrder.setSortCol("p.raw_update_time");
			queryOrder.setSortOrder("DESC");
			
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			QueryBaseBatchResult<ProjectInfo> batchResult = counterGuaranteeServiceClient
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
	
	@RequestMapping("view.htm")
	public String view(@RequestParam(value = "formId", required = true) long formId,
						HttpServletRequest request, Model model) {
		
		try {
			viewCommon(model, formId, "");
		} catch (Exception e) {
			logger.error("查询出错");
		}
		
		return VM_PATH + "viewApply.vm";
	}
	
	private void viewCommon(Model model, long formId, String taskId) {
		FCounterGuaranteeReleaseInfo info = counterGuaranteeServiceClient.findByFormId(formId);
		model.addAttribute("info", info);
		FormInfo form = formServiceClient.findByFormId(formId);
		model.addAttribute("form", form);
		if (null != form && StringUtil.isNotBlank(taskId)) {
			model.addAttribute("form", form);// 表单信息
			model.addAttribute("formCode", form.getFormCode());
			initWorkflow(model, form, taskId);
		}
		boolean isBankFinancing = false;
		
		if (null != info) {
			ProjectCreditConditionQueryOrder queryOrder = new ProjectCreditConditionQueryOrder();
			queryOrder.setProjectCode(info.getProjectCode());
			QueryBaseBatchResult<ProjectCreditConditionInfo> batchResult = projectCreditConditionServiceClient
				.queryCreditCondition(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			if (null != form && form.getStatus() == FormStatusEnum.DENY) {
				QueryBaseBatchResult<GuaranteeApplyCounterInfo> batchResult2 = counterGuaranteeServiceClient
					.queryCounts(formId);
				model.addAttribute("page2", PageUtil.getCovertPage(batchResult2));
			}
			
			showFenbaoInfo(info.getProjectCode(), model);
			
			if (ProjectUtil.isBankFinancing(info.getBusiType())) {
				isBankFinancing = true;
			}
		}
		
		model.addAttribute("isBankFinancing", isBankFinancing);
		
		//附件列表
		queryCommonAttachmentData(model, info.getId() + "", CommonAttachmentTypeEnum.RELEASE_REPAY);
		queryCommonAttachmentData(model, info.getId() + "", CommonAttachmentTypeEnum.RELEASE_REPORT);
		queryCommonAttachmentData(model, info.getId() + "", CommonAttachmentTypeEnum.RELEASE_OTHER);
		
		ProjectInfo projectInfo = projectServiceClient.queryByCode(info.getProjectCode(), true);
		model.addAttribute("projectInfo", projectInfo);
		
		//审核记录
		setAuditHistory2Page(form, model);
	}
	
	@RequestMapping("edit.htm")
	public String edit(	@RequestParam(value = "formId", required = false, defaultValue = "0") long formId,
						HttpServletRequest request, Model model) {
		model.addAttribute("formId", formId);
		FCounterGuaranteeReleaseInfo info = null;
		
		boolean isLitigaion = false;
		boolean isBankFinancing = false;
		String projectCode = "";
		if (formId > 0) {
			info = counterGuaranteeServiceClient.findByFormId(formId);
			if (ProjectUtil.isLitigation(info.getBusiType())) {
				isLitigaion = true;
			}
			if (ProjectUtil.isBankFinancing(info.getBusiType())) {
				isBankFinancing = true;
			}


			model.addAttribute("info", info);
			//queryOrder.setReleaseId(info.getId());
			
			FormInfo form = formServiceClient.findByFormId(formId);
			model.addAttribute("form", form);
			
			ProjectInfo project = projectServiceClient.queryByCode(info.getProjectCode(), false);
			projectCode = info.getProjectCode();
			model.addAttribute("project", project);
		} else {
			projectCode = request.getParameter("projectCode");
			if (StringUtil.isNotBlank(projectCode)) {
				info = new FCounterGuaranteeReleaseInfo();
				info.setProjectCode(projectCode);
				ProjectInfo project = projectServiceClient.queryByCode(projectCode, false);
				model.addAttribute("project", project);
				if (null != project) {
					info.setProjectCode(projectCode);
					info.setProjectName(project.getProjectName());
					info.setCustomerId(project.getCustomerId());
					info.setCustomerName(project.getCustomerName());
					info.setCreditAmount(project.getReleasableAmount()); //授信金额 总的可解保金额
					info.setTimeLimit(project.getTimeLimit());
					info.setTimeUnit(project.getTimeUnit());
					info.setBusiType(project.getBusiType());
					info.setBusiTypeName(project.getBusiTypeName());
					info.setContractNumber(project.getContractNo());
					
					if (ProjectUtil.isLitigation(project.getBusiType())) {
						isLitigaion = true;
						info.setCreditAmount(project.getAmount());
						info.setApplyAmount(project.getAmount());
					}
					if (ProjectUtil.isBankFinancing(info.getBusiType())) {
						isBankFinancing = true;
					}
				}
				
				model.addAttribute("info", info);
			}
		}
		
		model.addAttribute("isLitigaion", isLitigaion);
		if (isLitigaion) {
			//诉保
		} else {
			// 得到最大可解保的金额
			Money availableReleaseAmount = new Money(0L);
			if (null != info) {
				Money releasedAmount = counterGuaranteeServiceClient.queryReleasedAmount(info
					.getProjectCode());
				info.setReleasedAmount(releasedAmount); //已解保金额
				Money releasingAmount = counterGuaranteeServiceClient.queryReleasingAmount(
					info.getProjectCode())/*.subtract(info.getApplyAmount())*/;
				info.setReleasingAmount(releasingAmount); //解保中的金额
				ProjectInfo project = projectServiceClient.queryByCode(info.getProjectCode(), false);
				availableReleaseAmount = info.getCreditAmount().subtract(releasedAmount)
					.subtract(releasingAmount);
				if(info.getFormId()>0){//编辑把本张申请单的钱还原回去
					 releasingAmount = counterGuaranteeServiceClient.queryReleasingAmount(
							info.getProjectCode()).subtract(info.getApplyAmount());
					info.setReleasingAmount(releasingAmount); //解保中的金额
					availableReleaseAmount = project.getReleasableAmount().subtract(releasedAmount)
							.subtract(releasingAmount);
					model.addAttribute("availableReleaseAmount", availableReleaseAmount);
				}


				Map<Long, GuaranteeApplyCounterInfo> map = new HashMap<>();
				if (ListUtil.isNotEmpty(info.getCounters())) {
					for (GuaranteeApplyCounterInfo counter : info.getCounters()) {
						map.put(counter.getReleaseId(), counter);
					}
				}
				ProjectCreditConditionQueryOrder queryOrder = new ProjectCreditConditionQueryOrder();
				//				queryOrder.setStatus(CreditCheckStatusEnum.CHECK_PASS.code());
				queryOrder.setIsConfirm(BooleanEnum.IS.code());
				queryOrder.setProjectCode(info.getProjectCode());
				queryOrder.setPageNumber(1L);
				queryOrder.setPageSize(999L);
				QueryBaseBatchResult<ProjectCreditConditionInfo> batchResult = projectCreditConditionServiceClient
					.queryCreditCondition(queryOrder);
				if (map.size() > 0 && null != batchResult
					&& ListUtil.isNotEmpty(batchResult.getPageList())) {
					for (ProjectCreditConditionInfo condition : batchResult.getPageList()) {
						if (condition.getReleaseStatus() == ReleaseStatusEnum.RELEASED) {
							continue;
						}
						GuaranteeApplyCounterInfo counter = map.get(condition.getId());
						if (null != counter) {
							condition.setReleaseId(counter.getId());
							condition.setReleaseStatus(counter.getReleaseStatus());
							condition.setReleaseGist(counter.getReleaseRemark());
						} else {
							condition.setReleaseId(0L);
							condition.setReleaseStatus(ReleaseStatusEnum.WAITING);
							condition.setReleaseGist("");
						}
					}
				}
				model.addAttribute("page", PageUtil.getCovertPage(batchResult));
				
			}
			model.addAttribute("availableReleaseAmount", availableReleaseAmount);
			
			if (isBankFinancing && StringUtil.isNotBlank(projectCode)) {
				//查询资金渠道
				List<ProjectChannelRelationInfo> capitalChannels = projectChannelRelationServiceClient
					.queryCapitalChannel(projectCode);
				model.addAttribute("capitalChannels", capitalChannels);
			}
		}
		
		//是否银行融资渠道
		model.addAttribute("isBankFinancing", isBankFinancing);
		
		if (null != info) {
			//附件列表
			queryCommonAttachmentData(model, info.getId() + "",
				CommonAttachmentTypeEnum.RELEASE_REPAY);
			queryCommonAttachmentData(model, info.getId() + "",
				CommonAttachmentTypeEnum.RELEASE_REPORT);
			queryCommonAttachmentData(model, info.getId() + "",
				CommonAttachmentTypeEnum.RELEASE_OTHER);
		}
		
		showFenbaoInfo(projectCode, model);
		
		if (StringUtil.isNotBlank(projectCode)) {
			ProjectInfo projectInfo = projectServiceClient.queryByCode(projectCode, true);
			model.addAttribute("projectInfo", projectInfo);
		}
		
		return VM_PATH + "addApply.vm";
	}
	
	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, FCounterGuaranteeReleaseOrder order) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		
		String tipPrefix = "解保申请";
		try {
			order.setFormCode(FormCodeEnum.COUNTER_GUARANTEE);
			if (ProjectUtil.isLitigation(order.getBusiType())) {
				order.setFormCode(FormCodeEnum.COUNTER_GUARANTEE_LITIGATION);
			}
			order.setCheckIndex(0);
			order.setCheckStatus(1); //没有暂存，直接提交
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			//非银团项目，设置资金渠道，方便查询
			setCapitalChannels(order);
			FormBaseResult result = counterGuaranteeServiceClient.save(order);
			if (result.isSuccess()) {
				long keyId = result.getKeyId();
				//添加附件
				addAttachfile(keyId + "", request, order.getProjectCode(), null,
					CommonAttachmentTypeEnum.RELEASE_REPAY);
				addAttachfile(keyId + "", request, order.getProjectCode(), null,
					CommonAttachmentTypeEnum.RELEASE_REPORT);
				addAttachfile(keyId + "", request, order.getProjectCode(), null,
					CommonAttachmentTypeEnum.RELEASE_OTHER);
			}
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	private void setCapitalChannels(FCounterGuaranteeReleaseOrder order) {
		if (null == order || ListUtil.isEmpty(order.getRepayes())) {
			return;
		}
		ProjectInfo project = projectServiceClient.queryByCode(order.getProjectCode(), false);
		if (null != project && !ProjectUtil.isBankFinancing(project.getBusiType())) {
			//非银团项目
			//查询资金渠道
			List<ProjectChannelRelationInfo> capitalChannels = projectChannelRelationServiceClient
				.queryCapitalChannel(order.getProjectCode());
			if (ListUtil.isNotEmpty(capitalChannels)) {
				ProjectChannelRelationInfo channel = capitalChannels.get(0);
				for (FCounterGuaranteeRepayOrder repay : order.getRepayes()) {
					repay.setCapitalChannelCode(channel.getChannelCode());
					repay.setCapitalChannelId(channel.getChannelId());
					repay.setCapitalChannelName(channel.getChannelName());
					repay.setCapitalChannelType(channel.getChannelType());
					repay.setCapitalSubChannelCode(channel.getSubChannelCode());
					repay.setCapitalSubChannelId(channel.getSubChannelId());
					repay.setCapitalSubChannelName(channel.getSubChannelName());
					repay.setCapitalSubChannelType(channel.getSubChannelType());
				}
			}
		}
	}
	
	@ResponseBody
	@RequestMapping("canRelease.json")
	public Object canRelease(HttpServletRequest request, String projectCode, double releaseAmount) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		
		String tipPrefix = ""; //验证金额是否可申请解保
		try {
			FCounterGuaranteeReleaseOrder order = new FCounterGuaranteeReleaseOrder();
			order.setProjectCode(projectCode);
			releaseAmount *= 10000d; //单位:万元
			order.setApplyAmountStr(releaseAmount + "");
			FcsBaseResult result = counterGuaranteeServiceClient.canRelease(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	@RequestMapping("audit.htm")
	public String audit(@RequestParam(value = "formId", required = true) long formId,
						HttpServletRequest request, Model model) {
		try {
			viewCommon(model, formId, request.getParameter("taskId"));
		} catch (Exception e) {
			logger.error("查询出错");
		}
		
		return VM_PATH + "auditApply.vm";
	}
	
}
