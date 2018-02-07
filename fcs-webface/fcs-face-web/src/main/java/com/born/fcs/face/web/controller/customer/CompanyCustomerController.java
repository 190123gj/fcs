package com.born.fcs.face.web.controller.customer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.crm.ws.service.info.ChannalInfo;
import com.born.fcs.crm.ws.service.info.CompanyCustomerInfo;
import com.born.fcs.crm.ws.service.info.CustomerBaseInfo;
import com.born.fcs.crm.ws.service.order.CustomerDetailOrder;
import com.born.fcs.crm.ws.service.order.ListOrder;
import com.born.fcs.crm.ws.service.order.query.ChannalQueryOrder;
import com.born.fcs.crm.ws.service.order.query.CustomerQueryOrder;
import com.born.fcs.crm.ws.service.result.ValidateCustomerResult;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.info.check.AfterwardsCheckInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckLitigationInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.projectissueinformation.ProjectIssueInformationInfo;
import com.born.fcs.pm.ws.order.check.AfterwordsCheckQueryOrder;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.google.common.collect.Lists;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 客户管理-企业
 * */
@Controller
@RequestMapping("/customerMg/companyCustomer")
public class CompanyCustomerController extends BaseController {
	
	String VM_PATH = "/customerMg/customer/company/";
	
	@RequestMapping("list.htm")
	public String getList(HttpServletRequest request, Long orgId, Boolean depQuery,
							CustomerQueryOrder queryOrder, Model model) {
		if (orgId != null) {
			List<Long> deptIdList = new ArrayList<>();
			deptIdList.add(orgId);
			queryOrder.setDeptIdList(deptIdList);
			depQuery = true;
		} else {
			queryCustomerPermissionSet(queryOrder);
		}
		
		queryOrder.setCustomerType(CustomerTypeEnum.ENTERPRISE.code());
		QueryBaseBatchResult<CustomerBaseInfo> batchResult = customerServiceClient.list(queryOrder);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		model.addAttribute("queryOrder", queryOrder);
		model.addAttribute("depQuery", depQuery);
		model.addAttribute("orgId", orgId);
		setCustomerEnums(model);
		return VM_PATH + "list.vm";
		
	}
	
	@RequestMapping("add.htm")
	public String addPage(HttpServletRequest request, Model model) {
		setCustomerEnums(model);
		return VM_PATH + "addCustomers.vm";
		
	}
	
	@RequestMapping("info.htm")
	public String getInfo(long userId, Boolean isUpdate, Model model) {
		CompanyCustomerInfo info = companyCustomerClient.queryByUserId(userId, null);
		
		if (info != null) {
			if (isUpdate != null && isUpdate && StringUtil.isNotBlank(info.getChannalType())) {
				ChannalQueryOrder queryChannalOrder = new ChannalQueryOrder();
				queryChannalOrder.setChannelType(info.getChannalType());
				queryChannalOrder.setStatus("on");
				QueryBaseBatchResult<ChannalInfo> channalList = channalClient
					.list(queryChannalOrder);
				model.addAttribute("channalList", channalList);
			}
			verifyMsgQuery(info.getCustomerName() + info.getUserId(), model);
		}
		
		model.addAttribute("isUpdate", isUpdate);
		model.addAttribute("customerBaseInfo", info);
		setCustomerEnums(model);
		return VM_PATH + "addCustomers.vm";
		
	}
	
	@ResponseBody
	@RequestMapping("add.json")
	public JSONObject add(HttpServletRequest request, ListOrder listOrder, Model model) {
		CustomerDetailOrder order = new CustomerDetailOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		if (ListUtil.isNotEmpty(listOrder.getCompanyQualification())) {
			order.setCompanyQualification(listOrder.getCompanyQualification());
		}
		if (ListUtil.isNotEmpty(listOrder.getCompanyOwnershipStructure())) {
			order.setCompanyOwnershipStructure(listOrder.getCompanyOwnershipStructure());
		}
		setRelation(request, order);
		order.setInputPerson(ShiroSessionUtils.getSessionLocal().getRealName());
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		//2017-09-12 所有人创建的都为公海客户
		//Org org = sessionLocal.getUserDetailInfo().getPrimaryOrg();
		//		if (DataPermissionUtil.isBusiFZR() || DataPermissionUtil.isXinHuiFzr()) {
		//			//业务总监添加
		//			order.setDepId(org.getId());
		//			order.setDepName(org.getName());
		//			order.setDepPath(org.getPath());
		//			order.setDirectorId(sessionLocal.getUserId());
		//			order.setDirector(sessionLocal.getRealName());
		//			order.setIsDistribution(BooleanEnum.NO.code());
		//		}
		// 
		//		if (DataPermissionUtil.isBusiManager() || DataPermissionUtil.isXinHuiBusiManager()) {
		//			//客户经理添加
		//			order.setDepId(org.getId());
		//			order.setDepName(org.getName());
		//			order.setDepPath(org.getPath());
		//			order.setCustomerManagerId(sessionLocal.getUserId());
		//			order.setCustomerManager(sessionLocal.getRealName());
		//			order.setIsDistribution(BooleanEnum.IS.code());
		//		}
		
		order.setInputPersonId(sessionLocal.getUserId());
		order.setInputPerson(sessionLocal.getRealName());
		order.setCustomerType(CustomerTypeEnum.ENTERPRISE.code());
		FcsBaseResult result = customerServiceClient.add(order);
		
		synWatchList(order);
		verifyMsgSave(request, order.getCustomerName() + result.getKeyId());
		return toJSONResult(result, "企业客户添加成功", null);
		
	}
	
	@ResponseBody
	@RequestMapping("update.json")
	public JSONObject update(HttpServletRequest request, ListOrder listOrder, Model model) {
		
		CustomerDetailOrder order = new CustomerDetailOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		if (ListUtil.isNotEmpty(listOrder.getCompanyQualification())) {
			order.setCompanyQualification(listOrder.getCompanyQualification());
		}
		if (ListUtil.isNotEmpty(listOrder.getCompanyOwnershipStructure())) {
			order.setCompanyOwnershipStructure(listOrder.getCompanyOwnershipStructure());
		}
		setRelation(request, order);
		order.setCustomerType(CustomerTypeEnum.ENTERPRISE.code());
		FcsBaseResult result = customerServiceClient.updateByUserId(order);
		synWatchList(order);
		verifyMsgSave(request, order.getCustomerName() + order.getUserId());
		return toJSONResult(result, "企业客户更新成功", null);
		
	}
	
	/** 删除个人客户 **/
	@ResponseBody
	@RequestMapping("delete.json")
	public JSONObject delete(Long userId, Model model) {
		FcsBaseResult result = customerServiceClient.delete(userId);
		return toJSONResult(result, "客户删除成功", null);
		
	}
	
	/** 校验企业客户是否可用 **/
	@ResponseBody
	@RequestMapping("validata.json")
	public JSONObject validata(String busiLicenseNo, String customerName, Model model) {
		JSONObject json = new JSONObject();
		ValidateCustomerResult result = companyCustomerClient.ValidateCustomer(busiLicenseNo,
			customerName, ShiroSessionUtils.getSessionLocal().getUserId());
		if (result != null) {
			json.put("success", result.isSuccess());
			json.put("message", result.getMessage());
			json.put("type", result.getType());
			json.put("customerManager", result.getCustomerManager());
			json.put("userId", result.getUserId());
			json.put("list", result.getList());
			json.put("memo", "type字段：pub :公海 ，per：历史客户，perHs：历史间接客户，other:别人的");
		} else {
			json.put("success", false);
			json.put("message", "查询失败");
		}
		return json;
		
	}
	
	/** 查询客户项目 **/
	@ResponseBody
	@RequestMapping("queryProject.json")
	public JSONObject queryProject(ProjectQueryOrder order, long userId) {
		JSONObject josn = new JSONObject();
		order.setCustomerId(userId);
		List<ProjectPhasesEnum> phasesList = Lists.newArrayList();
		phasesList.add(ProjectPhasesEnum.AFTERWARDS_PHASES);
		phasesList.add(ProjectPhasesEnum.CONTRACT_PHASES);
		phasesList.add(ProjectPhasesEnum.COUNCIL_PHASES);
		phasesList.add(ProjectPhasesEnum.FINISH_PHASES);
		phasesList.add(ProjectPhasesEnum.FUND_RAISING_PHASES);
		//		phasesList.add(ProjectPhasesEnum.INVESTIGATING_PHASES);
		phasesList.add(ProjectPhasesEnum.LOAN_USE_PHASES);
		//phasesList.add(ProjectPhasesEnum.RE_COUNCIL_PHASES);
		phasesList.add(ProjectPhasesEnum.RECOVERY_PHASES);
		order.setPageSize(1000);
		QueryBaseBatchResult<ProjectInfo> result = personalCustomerClient.queryProject(order);
		
		for (ProjectInfo info : result.getPageList()) {
			//历史发售信息
			if (ProjectUtil.isBond(info.getBusiType())) {
				List<ProjectIssueInformationInfo> listProjectIssueInformationinfo = projectIssueInformationServiceClient
					.findProjectIssueInformationByProjectCode(info.getProjectCode());
				//计算实际发售金额总和
				Money sumActualAmount = new Money(0, 0);
				if (listProjectIssueInformationinfo != null) {
					for (ProjectIssueInformationInfo projectIssueInformationInfo : listProjectIssueInformationinfo) {
						//					projectIssueInformationInfo.setActualAmount(new Money(MoneyUtil
						//						.getMoneyByw2(projectIssueInformationInfo.getActualAmount())));
						sumActualAmount.addTo(projectIssueInformationInfo.getActualAmount());
					}
				}
				info.setAmount(sumActualAmount);
			} else if (ProjectUtil.isLitigation(info.getBusiType())) {
				AfterwordsCheckQueryOrder orders = new AfterwordsCheckQueryOrder();
				orders.setPageSize(1);
				orders.setProjectCode(info.getProjectCode());
				orders.setFormStatus(FormStatusEnum.APPROVAL.code());
				QueryBaseBatchResult<AfterwardsCheckInfo> batchResult = afterwardsCheckServiceClient
					.list(orders);
				if (batchResult.isSuccess() && ListUtil.isNotEmpty(batchResult.getPageList())) {
					FAfterwardsCheckLitigationInfo litigation = afterwardsCheckServiceClient
						.findLitigationByFormId(batchResult.getPageList().get(0).getFormId());
					if (litigation.getJudgeDate() != null) {
						info.setEndTime(litigation.getJudgeDate());
					}
				}
			}
			
		}
		if (result.isSuccess()) {
			josn.put("success", true);
			josn.put("message", "查询成功");
			josn.put("list", result.getPageList());
			josn.put("result", result);
		} else {
			josn.put("success", false);
			josn.put("success", StringUtil.defaultIfBlank(result.getMessage(), "查询失败"));
		}
		return josn;
		
	}
	
	/** 关系转化成一个字段 */
	private void setRelation(HttpServletRequest request, CustomerDetailOrder order) {
		String[] ralations = request.getParameterValues("relation");
		if (ralations != null && ralations.length > 0) {
			String ralation = ralations[0];
			for (int i = 1; i < ralations.length; i++) {
				ralation += "," + ralations[i];
			}
			order.setRelation(ralation);
		}
		
	}
	
}
