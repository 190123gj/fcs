package com.born.fcs.face.web.controller.project.projectissueinformation;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.SellStatusEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractResultInfo;
import com.born.fcs.pm.ws.info.projectissueinformation.ProjectIssueInformationInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.contract.ProjectContractQueryOrder;
import com.born.fcs.pm.ws.order.projectissueinformation.ProjectIssueInformationOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;

@Controller
@RequestMapping("projectMg/projectIssueInformation")
public class ProjectIssueInformationController extends BaseController {
	
	final static String vm_path = "/projectMg/beforeLoanMg/projectIssueInformation/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "issueDate", "expireTime" };
	}
	
	/**
	 * 承销/发债信息维护 列表
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("projectIssueInformationList.htm")
	public String projectIssueInformationList(ProjectQueryOrder order, Model model) {
		//业务类型
		//		List<String> busiTypeList = Lists.newArrayList();
		//		busiTypeList.add(order.getBusiType());
		//		busiTypeList.add("12");
		//		order.setBusiTypeList(busiTypeList);
		setSessionLocalInfo2Order(order);
		if (StringUtil.isEmpty(order.getSortCol())) {
			order.setSortCol("raw_add_time");
			order.setSortOrder("DESC");
		}
		model.addAttribute("isXinHuiBusiManager", DataPermissionUtil.isXinHuiBusiManager());//是否信惠业务人员
		model.addAttribute("isBusiManager", isBusiManager() || isLegalManager());//客户经理
		QueryBaseBatchResult<ProjectInfo> batchResult = projectIssueInformationServiceClient
			.queryIssueProject(order);
		List<ProjectInfo> info = batchResult.getPageList();
		for (ProjectInfo projectInfo : info) {
			List<ProjectIssueInformationInfo> listInfo = projectIssueInformationServiceClient
				.findProjectIssueInformationByProjectCode(projectInfo.getProjectCode());
			if (listInfo != null && listInfo.size() > 0) {
				for (ProjectIssueInformationInfo projectIssueInformationInfo : listInfo) {
					
					if (projectIssueInformationInfo != null
						&& ProjectUtil.isBond(projectInfo.getBusiType())) {
						projectInfo.setAmount(projectIssueInformationInfo.getAmount());
						break;
					}
					
				}
			}
			
			//没有手动停止  担保类  计算发售状态
			if (BooleanEnum.NO == projectInfo.getIsContinue() && ProjectUtil.isBond("12")
				&& BooleanEnum.IS == projectInfo.getIsMaximumAmount()) {
				projectIssueInformationServiceClient.updateStatusByProjectCode(projectInfo
					.getProjectCode());
			}
			
		}
		
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		
		return vm_path + "list.vm";
	}
	
	/**
	 * 查看项目信息维护
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("viewProjectIssueInformation.htm")
	public String viewProjectIssueInformation(String projectCode, Model model) {
		List<ProjectIssueInformationInfo> listProjectIssueInformationinfo = projectIssueInformationServiceClient
			.findProjectIssueInformationByProjectCode(projectCode);
		ProjectSimpleDetailInfo projectInfo = projectServiceClient
			.querySimpleDetailInfo(projectCode);
		//		projectInfo.setAmount(new Money(MoneyUtil.getMoneyByw2(projectInfo.getAmount())));
		//		
		//		projectInfo.setReleasedAmount(new Money(MoneyUtil.getMoneyByw2(projectInfo
		//			.getReleasedAmount())));
		
		//发售信息
		ProjectIssueInformationInfo issueInfo = new ProjectIssueInformationInfo();
		if (ListUtil.isNotEmpty(listProjectIssueInformationinfo)) {
			for (ProjectIssueInformationInfo projectIssueInformationInfo : listProjectIssueInformationinfo) {
				issueInfo = projectIssueInformationInfo;
			}
		}
		model.addAttribute("issueInfo", issueInfo);
		//计算实际发售金额总和
		Money sumActualAmount = new Money(0, 0);
		//项目依据
		String projectGist = "";
		//项目状态
		String status = "";
		//监管机构批复金额
		Money regulatoryAgenciesApprovalAmount = new Money(0, 0);
		if (listProjectIssueInformationinfo != null) {
			for (ProjectIssueInformationInfo projectIssueInformationInfo : listProjectIssueInformationinfo) {
				//				projectIssueInformationInfo.setActualAmount(new Money(MoneyUtil
				//					.getMoneyByw2(projectIssueInformationInfo.getActualAmount())));
				sumActualAmount.addTo(projectIssueInformationInfo.getActualAmount());
				//取最后一次发售信息
				projectGist = projectIssueInformationInfo.getProjectGist();
				regulatoryAgenciesApprovalAmount = projectIssueInformationInfo.getAmount();
			}
		}
		if (listProjectIssueInformationinfo != null) {
			for (ProjectIssueInformationInfo projectIssueInformationInfo : listProjectIssueInformationinfo) {
				//取最后一次发售信息
				if (SellStatusEnum.SELL_FINISH == projectIssueInformationInfo.getStatus()) {
					status = projectIssueInformationInfo.getStatus().code();
					break;
				}
				status = projectIssueInformationInfo.getStatus().code();
			}
		}
		model.addAttribute("status", status);
		model.addAttribute("regulatoryAgenciesApprovalAmount", regulatoryAgenciesApprovalAmount);
		model.addAttribute("projectGist", projectGist);
		model.addAttribute("sumActualAmount", sumActualAmount);
		if (projectInfo.getBusiType().startsWith("51")) {
			//剩余可发售金额
			model.addAttribute("surplusAmount", projectInfo.getAmount().subtract(sumActualAmount));
			model.addAttribute("type", 511);
			model.addAttribute("projectInfo", projectInfo);
			model.addAttribute("pCouncilSummaryInfo", councilSummaryServiceClient
				.queryUnderwritingProjectCsBySpId(projectInfo.getSpId(), false));
			model.addAttribute("listProjectIssueInformationinfo", listProjectIssueInformationinfo);
			return vm_path + "viewMaintenance.vm";//承销类型
		} else {
			//			ProjectContractItemInfo contractItemInfo = projectContractServiceClient
			//				.findByContractCode(projectInfo.getContractNo());
			//剩余可发售金额
			if (projectInfo.getIsMaximumAmount() != null
				&& BooleanEnum.IS == projectInfo.getIsMaximumAmount()) {
				
				model.addAttribute(
					"surplusAmount",
					projectInfo.getAmount().subtract(sumActualAmount)
						.add(projectInfo.getReleasedAmount()));
			} else {
				//				model.addAttribute("approvedAmount", contractItemInfo.getApprovedAmount());
				model.addAttribute("surplusAmount",
					regulatoryAgenciesApprovalAmount.subtract(sumActualAmount));
			}
			model.addAttribute("type", 111);
			model.addAttribute("projectInfo", projectInfo);
			model.addAttribute("pCouncilSummaryInfo",
				councilSummaryServiceClient.queryBondProjectCsBySpId(projectInfo.getSpId(), false));
			model.addAttribute("listProjectIssueInformationinfo", listProjectIssueInformationinfo);
			
			model.addAttribute("listBondReinsurance",
				projectBondReinsuranceInformationServiceClient.findByProjectCode(projectCode));
			return vm_path + "viewMaintenance.vm";//发债类型  
		}
	}
	
	/**
	 * 发售项目信息维护
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("editProjectIssueInformation.htm")
	public String eidtProjectIssueInformation(String projectCode, String type, boolean isEdit,
												Model model) {
		ProjectSimpleDetailInfo projectInfo = projectServiceClient
			.querySimpleDetailInfo(projectCode);
		if (projectInfo != null) {
			//			projectInfo.setAmount(new Money(MoneyUtil.getMoneyByw2(projectInfo.getAmount())));
			//			projectInfo.setReleasedAmount(new Money(MoneyUtil.getMoneyByw2(projectInfo
			//				.getReleasedAmount())));
			//历史发售信息
			List<ProjectIssueInformationInfo> listProjectIssueInformationinfo = projectIssueInformationServiceClient
				.findProjectIssueInformationByProjectCode(projectCode);
			//发售信息
			ProjectIssueInformationInfo issueInfo = new ProjectIssueInformationInfo();
			if (ListUtil.isNotEmpty(listProjectIssueInformationinfo)) {
				for (ProjectIssueInformationInfo projectIssueInformationInfo : listProjectIssueInformationinfo) {
					issueInfo = projectIssueInformationInfo;
				}
			}
			model.addAttribute("issueInfo", issueInfo);
			//第一次发售时间
			Date firstTime = null;
			//截止时间
			Date endTime = null;
			//同编号签订日期  
			Date contractSignTime = null;
			if (projectInfo.getStartTime() != null && projectInfo.getEndTime() != null) {//不是第一次发售
				firstTime = projectInfo.getStartTime();
				endTime = projectInfo.getEndTime();
			}
			//没有历史发售信息 //第一次发售
			if (ListUtil.isEmpty(listProjectIssueInformationinfo)) {
				ProjectContractQueryOrder order = new ProjectContractQueryOrder();
				order.setContractCode(projectInfo.getContractNo());
				QueryBaseBatchResult<ProjectContractResultInfo> batchResult = projectContractServiceClient
					.query(order);
				List<ProjectContractResultInfo> list = batchResult.getPageList();
				for (ProjectContractResultInfo projectContractResultInfo : list) {
					if (projectContractResultInfo.getSignedTime() != null) {
						contractSignTime = projectContractResultInfo.getSignedTime();
					}
				}
				model.addAttribute("isFirst", "true");
			}
			
			model.addAttribute("contractSignTime", DateUtil.dtSimpleFormat(contractSignTime));
			model.addAttribute("firstTime", DateUtil.dtSimpleFormat(firstTime));
			
			model.addAttribute("endTime", DateUtil.dtSimpleFormat(endTime));
			//计算实际发售金额总和
			Money sumActualAmount = new Money(0, 0);
			//项目依据
			String projectGist = "";
			//监管机构批复金额
			Money regulatoryAgenciesApprovalAmount = new Money(0, 0);
			if (listProjectIssueInformationinfo != null) {
				for (ProjectIssueInformationInfo projectIssueInformationInfo : listProjectIssueInformationinfo) {
					//					projectIssueInformationInfo.setActualAmount(new Money(MoneyUtil
					//						.getMoneyByw2(projectIssueInformationInfo.getActualAmount())));
					sumActualAmount.addTo(projectIssueInformationInfo.getActualAmount());
					projectGist = projectIssueInformationInfo.getProjectGist();
					regulatoryAgenciesApprovalAmount = projectIssueInformationInfo.getAmount();
				}
			}
			model
				.addAttribute("regulatoryAgenciesApprovalAmount", regulatoryAgenciesApprovalAmount);
			model.addAttribute("projectGist", projectGist);
			model.addAttribute("sumActualAmount", sumActualAmount);
			
			//剩余可发售金额
			model.addAttribute("isEdit", isEdit);
			
			if (projectInfo.getBusiType().startsWith("51")) {
				model.addAttribute("surplusAmount",
					projectInfo.getAmount().subtract(sumActualAmount));
				model.addAttribute("type", 511);
				model.addAttribute("projectInfo", projectInfo);
				model.addAttribute(
					"pCouncilSummaryInfo",
					councilSummaryServiceClient.queryUnderwritingProjectCsBySpId(
						projectInfo.getSpId(), false));
				model.addAttribute("listProjectIssueInformationinfo",
					listProjectIssueInformationinfo);
				return vm_path + "maintenance.vm";//承销类型
			} else {
				//				ProjectContractItemInfo contractItemInfo = projectContractServiceClient
				//					.findByContractCode(projectInfo.getContractNo());
				//剩余可发售金额
				if (projectInfo.getIsMaximumAmount() != null
					&& BooleanEnum.IS == projectInfo.getIsMaximumAmount()) {
					model.addAttribute(
						"surplusAmount",
						projectInfo.getAmount().subtract(sumActualAmount)
							.add(projectInfo.getReleasedAmount()));
				} else {
					//					model.addAttribute("approvedAmount", contractItemInfo.getApprovedAmount());
					
					model.addAttribute("surplusAmount",
						regulatoryAgenciesApprovalAmount.subtract(sumActualAmount));
				}
				model.addAttribute("type", 111);
				model.addAttribute("projectInfo", projectInfo);
				model.addAttribute("pCouncilSummaryInfo", councilSummaryServiceClient
					.queryBondProjectCsBySpId(projectInfo.getSpId(), false));
				model.addAttribute("listProjectIssueInformationinfo",
					listProjectIssueInformationinfo);
				
				model.addAttribute("listBondReinsurance",
					projectBondReinsuranceInformationServiceClient.findByProjectCode(projectCode));
				return vm_path + "maintenance.vm";//发债类型  
			}
		} else {//新增时
			model.addAttribute("type", type);
			return vm_path + "maintenance.vm";
		}
		
	}
	
	/**
	 * 新增承销项目情况表
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("addConsignmentSales.htm")
	public String toAddConsignmentSales(Model model) {
		return vm_path + "addConsignmentSales.vm";
	}
	
	/**
	 * 新增发债项目情况表
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("addBond.htm")
	public String toAddBond(Model model) {
		return vm_path + "addBond.vm";
	}
	
	/**
	 * 保存承销或发债项目情况表
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveConsignmentSalesOrBond.htm")
	@ResponseBody
	public JSONObject saveConsignmentSalesOrBond(HttpServletRequest request,
													HttpServletResponse response,
													ProjectIssueInformationOrder order, Model model) {
		String tipPrefix = "保存承销或发债项目情况表";
		JSONObject result = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				result.put("success", false);
				result.put("message", "您未登陆或登陆已失效");
				return result;
			}
			// 初始化Form验证信息
			order.setCheckStatus(0);
			order.setCheckIndex(0);
			order.setRelatedProjectCode(order.getProjectCode());
			order.setUserId(sessionLocal.getUserId());
			order.setUserName(sessionLocal.getRealName());
			order.setUserAccount(sessionLocal.getUserName());
			//			order.setAmount(order.getAmount() * 10000);
			//			order.setActualAmount(order.getActualAmount() * 10000);//转换成万元
			FcsBaseResult saveResult = null;
			ProjectSimpleDetailInfo projectInfo = projectServiceClient.querySimpleDetailInfo(order
				.getProjectCode());
			if (projectInfo.getStartTime() == null && projectInfo.getEndTime() == null) {//第一次发售
				if (projectInfo.getStatus() == ProjectStatusEnum.PAUSE) {
					result.put("success", false);
					result.put("message", "项目暂缓，不能提交");
				} else {
					saveResult = projectIssueInformationServiceClient.save(order);
					if (saveResult.isSuccess()) {
						result.put("success", true);
						result.put("message", "新增成功");
					} else {
						result.put("success", false);
						result.put("message", saveResult.getMessage());
					}
				}
			} else {
				saveResult = projectIssueInformationServiceClient.save(order);
				if (saveResult.isSuccess()) {
					result.put("success", true);
					result.put("message", "新增成功");
				} else {
					result.put("success", false);
					result.put("message", saveResult.getMessage());
				}
			}
			
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
	}
	
	/**
	 * 终止发售
	 *
	 * @param projectCode
	 * @param model
	 * @return
	 */
	@RequestMapping("stopSell.htm")
	@ResponseBody
	public JSONObject stopSell(String projectCode, Model model) {
		JSONObject result = new JSONObject();
		int num = projectServiceClient.updateIsContinueByProjectCode(projectCode);
		if (num > 0) {
			result.put("success", true);
			result.put("message", "终止发售成功");
		} else {
			result.put("false", false);
			result.put("message", "终止发售失败");
		}
		return result;
	}
}
