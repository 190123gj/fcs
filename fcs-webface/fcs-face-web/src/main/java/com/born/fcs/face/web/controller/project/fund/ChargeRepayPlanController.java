package com.born.fcs.face.web.controller.project.fund;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONObject;
import com.born.bpm.service.client.user.SysOrg;
import com.born.fcs.face.integration.bpm.service.info.UserInfo;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FormChangeApplyEnum;
import com.born.fcs.pm.ws.enums.PlanTypeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.fund.ChargeRepayPlanDetailInfo;
import com.born.fcs.pm.ws.info.fund.ChargeRepayPlanInfo;
import com.born.fcs.pm.ws.info.projectissueinformation.ProjectIssueInformationInfo;
import com.born.fcs.pm.ws.order.formchange.FormCheckCanChangeOrder;
import com.born.fcs.pm.ws.order.fund.ChargeRepayPlanDetailQueryOrder;
import com.born.fcs.pm.ws.order.fund.ChargeRepayPlanOrder;
import com.born.fcs.pm.ws.order.fund.ChargeRepayPlanQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

/**
 * 收费/还款计划
 * @author wuzj
 *
 */
@Controller
@RequestMapping("projectMg/chargeRepayPlan")
public class ChargeRepayPlanController extends BaseController {
	
	private final static String vm_path = "/projectMg/cashMg/costPlan/";
	
	@RequestMapping("queryForChangeApply.json")
	public String queryForChangeApply(HttpServletRequest request, String projectCode, Model model) {
		try {
			if (StringUtil.isNotBlank(projectCode)) {
				ChargeRepayPlanQueryOrder queryOrder = new ChargeRepayPlanQueryOrder();
				queryOrder.setProjectCode(projectCode);
				queryOrder.setIsRepayPlan(BooleanEnum.IS);
				queryOrder.setPageSize(200);
				QueryBaseBatchResult<ChargeRepayPlanInfo> batchResult = chargeRepayPlanServiceClient
					.queryPlan(queryOrder);
				if (batchResult != null && ListUtil.isNotEmpty(batchResult.getPageList()))
					model.addAttribute("plans", batchResult.getPageList());
			}
		} catch (Exception e) {
			logger.error("查询可签还款计划出错 {} ", e);
		}
		return vm_path + "forChangeApply.vm";
	}
	
	@RequestMapping("changeApply.htm")
	public String changeApply(Long planId, String projectCode, HttpServletRequest request,
								HttpSession session, Model model) {
		
		FormCheckCanChangeOrder checkOrder = new FormCheckCanChangeOrder();
		checkOrder.setProjectCode(projectCode);
		checkOrder.setChangeForm(FormChangeApplyEnum.CHARGE_REPAY_PLAY);
		checkCanApplyChange(checkOrder);
		setFormChangeApplyInfo(request, session, model);
		
		ChargeRepayPlanInfo plan = null;
		if (planId != null && planId > 0) {
			plan = chargeRepayPlanServiceClient.queryPlanById(planId);
		}
		if (plan == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "收费&还款计划不存在");
		}
		model.addAttribute("plan", plan);
		model.addAttribute("isQB", true);
		return vm_path + "add_apply.vm";
	}
	
	@RequestMapping("list.htm")
	public String list(ChargeRepayPlanQueryOrder order, Model model) {
		setSessionLocalInfo2Order(order);
		model.addAttribute("queryOrder", order);
		QueryBaseBatchResult<ChargeRepayPlanInfo> batchResult = chargeRepayPlanServiceClient
			.queryPlan(order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		return vm_path + "list.vm";
	}
	
	@RequestMapping("form.htm")
	public String form(String projectCode, BooleanEnum IsChargePlan, BooleanEnum isRepayPlan,
						Model model) {
		ChargeRepayPlanInfo plan = new ChargeRepayPlanInfo();
		if (StringUtil.isNotBlank(projectCode)) {
			ProjectInfo project = projectServiceClient.queryByCode(projectCode, false);
			BeanCopier.staticCopy(project, plan);
			model.addAttribute("project", project);
			if (endTime(project) != null) {
				model.addAttribute("endTime", endTime(project));
			}
			//查询历史消息提醒设置
			
			ChargeRepayPlanQueryOrder order = new ChargeRepayPlanQueryOrder();
			order.setProjectCode(projectCode);
			order.setIsAffirm(BooleanEnum.YES.code());
			order.setSortCol("plan_id");
			order.setSortOrder("desc");
			order.setPageSize(1);
			QueryBaseBatchResult<ChargeRepayPlanInfo> hisPlanResult = chargeRepayPlanServiceClient
				.queryPlan(order);
			if (hisPlanResult != null && hisPlanResult.getTotalCount() > 0) {
				ChargeRepayPlanInfo hisPlan = hisPlanResult.getPageList().get(0);
				plan.setBeforeDays(hisPlan.getBeforeDays());
				plan.setCycleDays(hisPlan.getCycleDays());
			}
			boolean hasRepayPlan = checkHasRepayPlan(0, projectCode);
			model.addAttribute("hasRepayPlan", hasRepayPlan);
			if (hasRepayPlan)
				isRepayPlan = BooleanEnum.NO;
		}
		if (IsChargePlan != null) {
			plan.setIsChargePlan(IsChargePlan);
		} else {
			plan.setIsChargePlan(BooleanEnum.IS);
		}
		
		if (isRepayPlan != null) {
			plan.setIsRepayPlan(isRepayPlan);
			
		} else {
			plan.setIsRepayPlan(BooleanEnum.IS);
		}
		model.addAttribute("plan", plan);
		return vm_path + "add_apply.vm";
	}
	
	@RequestMapping("edit.htm")
	public String edit(long planId, HttpServletRequest request, Model model) {
		view(planId, null, request, model);
		return vm_path + "add_apply.vm";
	}
	
	/**
	 * 检查是否已存在还款计划
	 * @param planId
	 * @param projectCode
	 * @return
	 */
	private boolean checkHasRepayPlan(long planId, String projectCode) {
		boolean hasRepayPlan = false;
		try {
			if (StringUtil.isBlank(projectCode) && planId > 0) {
				ChargeRepayPlanInfo plan = chargeRepayPlanServiceClient.queryPlanById(planId);
				if (plan != null)
					projectCode = plan.getProjectCode();
			}
			if (StringUtil.isBlank(projectCode))
				return hasRepayPlan;
			
			ChargeRepayPlanQueryOrder planOrder = new ChargeRepayPlanQueryOrder();
			planOrder.setProjectCode(projectCode);
			planOrder.setIsRepayPlan(BooleanEnum.IS);
			planOrder.setIsAffirm(BooleanEnum.YES.code());
			planOrder.setPageSize(200);
			QueryBaseBatchResult<ChargeRepayPlanInfo> plans = chargeRepayPlanServiceClient
				.queryPlan(planOrder);
			if (plans != null && plans.getTotalCount() > 0) {
				for (ChargeRepayPlanInfo plan : plans.getPageList()) {
					if (plan.getPlanId() != planId) {
						ChargeRepayPlanDetailQueryOrder detailOrder = new ChargeRepayPlanDetailQueryOrder();
						detailOrder.setPlanId(planId);
						detailOrder.setPlanType(PlanTypeEnum.REPAY_PLAN.code());
						detailOrder.setPageSize(200);
						QueryBaseBatchResult<ChargeRepayPlanDetailInfo> details = chargeRepayPlanServiceClient
							.queryPlanDetail(detailOrder);
						if (details != null && details.getTotalCount() > 0) {
							hasRepayPlan = true;
							break;
						}
					}
					if (hasRepayPlan)
						break;
				}
			}
		} catch (Exception e) {
			logger.error("检查是否有还款计划出错：{}", e);
		}
		return hasRepayPlan;
	}
	
	@RequestMapping("view.htm")
	public String view(Long planId, String projectCode, HttpServletRequest request, Model model) {
		
		if (StringUtil.isNotEmpty(projectCode)) {
			ProjectInfo project = projectServiceClient.queryByCode(projectCode, false);
			if (project == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "项目不存在");
			}
			if (endTime(project) != null) {
				model.addAttribute("endTime", endTime(project));
			}
			ChargeRepayPlanDetailQueryOrder order = new ChargeRepayPlanDetailQueryOrder();
			order.setProjectCode(projectCode);
			order.setPageSize(200);
			QueryBaseBatchResult<ChargeRepayPlanDetailInfo> batchResult = chargeRepayPlanServiceClient
				.queryPlanDetail(order);
			List<ChargeRepayPlanDetailInfo> chargeList = Lists.newArrayList();
			List<ChargeRepayPlanDetailInfo> repayList = Lists.newArrayList();
			if (batchResult != null && batchResult.isSuccess()) {
				for (ChargeRepayPlanDetailInfo detail : batchResult.getPageList()) {
					if (detail.getPlanType() == PlanTypeEnum.CHARGE_PLAN) {
						chargeList.add(detail);
					} else {
						repayList.add(detail);
					}
				}
			}
			ChargeRepayPlanInfo plan = new ChargeRepayPlanInfo();
			BeanCopier.staticCopy(project, plan);
			
			if (ListUtil.isNotEmpty(chargeList)) {
				plan.setIsChargePlan(BooleanEnum.IS);
			}
			if (ListUtil.isNotEmpty(repayList)) {
				plan.setIsRepayPlan(BooleanEnum.IS);
			}
			plan.setChargePlanList(chargeList);
			plan.setRepayPlanList(repayList);
			if (planId != null && planId > 0) {
				ChargeRepayPlanInfo planInfo = chargeRepayPlanServiceClient.queryPlanById(planId);
				plan.setIsAffirm(planInfo.getIsAffirm());
			}
			model.addAttribute("plan", plan);
			model.addAttribute("isShowAll", true);
			model.addAttribute("project", project);
		} else {
			ChargeRepayPlanInfo plan = chargeRepayPlanServiceClient.queryPlanById(planId);
			model.addAttribute("plan", plan);
			ProjectInfo project = projectServiceClient.queryByCode(plan.getProjectCode(), false);
			model.addAttribute("project", project);
			if (endTime(project) != null) {
				model.addAttribute("endTime", endTime(project));
			}
			if (plan != null)
				model
					.addAttribute("hasRepayPlan", checkHasRepayPlan(planId, plan.getProjectCode()));
		}
		if (StringUtil.equals(request.getParameter("showMessageSet"), "YES"))
			model.addAttribute("showMessageSet", true);
		
		return vm_path + "view_apply.vm";
	}
	
	@RequestMapping("save.htm")
	@ResponseBody
	public JSONObject save(ChargeRepayPlanOrder order, Model model) {
		JSONObject json = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal != null) {
				order.setUserId(sessionLocal.getUserId());
				order.setUserAccount(sessionLocal.getUserName());
				order.setUserName(sessionLocal.getRealName());
				UserInfo userInfo = sessionLocal.getUserInfo();
				if (userInfo != null) {
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
			FcsBaseResult result = chargeRepayPlanServiceClient.savePlan(order);
			toJSONResult(json, result, "保存成功", null);
		} catch (Exception e) {
			logger.error("保存收费/还款计划出错:{}", e);
		}
		return json;
	}
	
	private Date endTime(ProjectInfo project) {
		Date endTime = null;
		if (project != null) {
			//还款计划不能超过到期日，如果为发债项目，则已发债信息维护中的到期日为最后的还款计划可维护时间
			if (project.isBond()) {
				List<ProjectIssueInformationInfo> issueList = projectIssueInformationServiceClient
					.findProjectIssueInformationByProjectCode(project.getProjectCode());
				if (ListUtil.isNotEmpty(issueList)) {
					for (ProjectIssueInformationInfo issueInfo : issueList) {
						if (issueInfo.getExpireTime() == null)
							continue;
						if (endTime == null || issueInfo.getExpireTime().after(endTime))
							endTime = issueInfo.getExpireTime();
					}
				}
			} else {
				endTime = project.getEndTime();
			}
		}
		return endTime;
	}
	
}
