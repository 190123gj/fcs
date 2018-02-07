package com.born.fcs.face.web.controller.project.council;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.enums.AdjustTypeEnum;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ChargePhaseEnum;
import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.ChargeWayEnum;
import com.born.fcs.pm.ws.enums.ChargeWayPhaseEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.CompareEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.CreditLevelEnum;
import com.born.fcs.pm.ws.enums.FormChangeApplyEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.GradeLevelEnum;
import com.born.fcs.pm.ws.enums.GuarantorTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.enums.RepayWayEnum;
import com.born.fcs.pm.ws.enums.RepayWayPhaseEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.UpAndDownEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.council.CouncilInfo;
import com.born.fcs.pm.ws.info.council.CouncilProjectInfo;
import com.born.fcs.pm.ws.info.council.CouncilSummaryRiskHandleInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectBondInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectEntrustedInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectGuaranteeInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectLgLitigationInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectUnderwritingInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryRiskHandleInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeApplyInfo;
import com.born.fcs.pm.ws.info.riskHandleTeam.RiskHandleTeamInfo;
import com.born.fcs.pm.ws.order.bpm.TaskOpinion;
import com.born.fcs.pm.ws.order.council.CouncilSummaryRiskHandleQueryOrder;
import com.born.fcs.pm.ws.order.council.CouncilSummarySubmitOrder;
import com.born.fcs.pm.ws.order.council.CouncilSummaryUploadOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectBondOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectEntrustedOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectGuaranteeOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectLgLitigationOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectUnderwritingOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryRiskHandleOrder;
import com.born.fcs.pm.ws.order.council.OneVoteDownOrder;
import com.born.fcs.pm.ws.order.formchange.FormCheckCanChangeOrder;
import com.born.fcs.pm.ws.order.riskHandleTeam.RiskHandlerTeamQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.council.CouncilSummaryResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

/**
 * 会议纪要
 * 
 * @author wuzj
 */
@Controller
@RequestMapping("projectMg/meetingMg/summary")
public class CouncilSummaryController extends WorkflowBaseController {
	
	final static String vm_path = "/projectMg/assistSys/summary/";
	
	@Override
	protected String[] getMoneyInputNameArray() {
		return new String[] { "amount" };
	}
	
	@RequestMapping("initSummary.htm")
	@ResponseBody
	public JSONObject initSummary(long councilId, HttpServletRequest request, Model model) {
		JSONObject result = new JSONObject();
		try {
			model.addAttribute("councilId", councilId);
			FCouncilSummaryInfo summary = councilSummaryServiceClient
				.queryCouncilSummaryByCouncilId(councilId);
			if (summary == null) {
				FCouncilSummaryOrder order = new FCouncilSummaryOrder();
				setSessionLocalInfo2Order(order);
				order.setCouncilId(councilId);
				CouncilSummaryResult initResult = councilSummaryServiceClient
					.initCouncilSummary(order);
				if (initResult == null || !initResult.isSuccess()) {
					result.put("success", false);
					result.put("message", "初始化会议纪要失败");
					return result;
				}
				result.put("success", true);
				result.put("formId", initResult.getFormInfo().getFormId());
				result.put("message", "初始化会议纪要成功");
			} else {
				result.put("success", false);
				result.put("message", "会议纪要已存在");
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "初始化会议纪要出错");
			logger.error("初始化会议纪要失败{}", e);
		}
		return result;
	}
	
	/**
	 * 填写会议纪要
	 * 
	 * @param councilId
	 * @param spId
	 * @return
	 */
	@RequestMapping("form.htm")
	public String form(long councilId, Long spId, HttpServletRequest request, Model model) {
		
		model.addAttribute("councilId", councilId);
		
		FCouncilSummaryInfo summary = councilSummaryServiceClient
			.queryCouncilSummaryByCouncilId(councilId);
		
		FormInfo form = null;
		if (summary == null) {
			FCouncilSummaryOrder order = new FCouncilSummaryOrder();
			setSessionLocalInfo2Order(order);
			order.setCouncilId(councilId);
			CouncilSummaryResult initResult = councilSummaryServiceClient.initCouncilSummary(order);
			if (!initResult.isSuccess()) {
				throw ExceptionFactory.newFcsException(initResult.getFcsResultEnum(),
					initResult.getMessage());
			}
			
			summary = initResult.getSummary();
			form = initResult.getFormInfo();
		}
		
		if (form == null || form.getStatus() == FormStatusEnum.DELETED) {
			form = formServiceClient.findByFormId(summary.getFormId());
		}
		
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		
		//以下状态跳转到查看页面
		if (form.getStatus() == FormStatusEnum.AUDITING
			|| form.getStatus() == FormStatusEnum.APPROVAL
			|| form.getStatus() == FormStatusEnum.SUBMIT) {
			return view(form.getFormId(), spId, request, model);
		}
		
		CouncilTypeEnum councilType = summary.getCouncilType();
		
		if (councilType == CouncilTypeEnum.PROJECT_REVIEW) {
			viewProjectReview(form, summary, spId == null ? 0 : spId, model);
			return vm_path + "fillReview.vm";
			
		} else if (councilType == CouncilTypeEnum.RISK_HANDLE) {
			viewRiskHandle(form, summary, spId == null ? 0 : spId, model);
			return vm_path + "RiskDisposition.vm";
		} else {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "无法匹配会议纪要模板");
		}
	}
	
	/**
	 * 修改会议纪要
	 * 
	 * @param councilId
	 * @param spId
	 * @param model
	 * @return
	 */
	@RequestMapping("edit.htm")
	public String edit(long formId, Long spId, HttpServletRequest request, Model model) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		
		FCouncilSummaryInfo summary = councilSummaryServiceClient
			.queryCouncilSummaryByFormId(formId);
		
		if (summary == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "会议纪要不存在");
		}
		
		CouncilTypeEnum councilType = summary.getCouncilType();
		
		model.addAttribute("submitedAll", request.getAttribute("submitedAll"));
		model.addAttribute("isEdit", true);
		
		if (councilType == CouncilTypeEnum.PROJECT_REVIEW) {
			viewProjectReview(form, summary, spId == null ? 0 : spId, model);
			FCouncilSummaryProjectInfo projectInfo = (FCouncilSummaryProjectInfo) model.asMap()
				.get("project");
			if (ProjectUtil.isInnovativeProduct(projectInfo.getBusiType())) {
				queryCommonAttachmentData(model, "SUMMARY_1_" + projectInfo.getSpId(),
					CommonAttachmentTypeEnum.SUMMARY_INNOVATIVE_PRODUCT);
				return vm_path + "innovativeProductFillReview.vm";
			}
			return vm_path + "fillReview.vm";
			
		} else if (councilType == CouncilTypeEnum.RISK_HANDLE) {
			viewRiskHandle(form, summary, spId == null ? 0 : spId, model);
			return vm_path + "RiskDisposition.vm";
		} else {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "无法匹配会议纪要模板");
		}
	}
	
	/**
	 * 进入上传会议纪要
	 * 
	 * @param councilId
	 * @param spId
	 * @param model
	 * @return
	 */
	@RequestMapping("uploadMessage.htm")
	public String uploadMessage(Long councilId, String type, HttpServletRequest request, Model model) {
		// 查询会议信息
		CouncilInfo councilInfo = councilServiceClient.queryCouncilById(councilId);
		// 查询项目评审信息
		List<CouncilProjectInfo> councilProjects = councilProjectServiceClient
			.queryByCouncilId(councilId);
		model.addAttribute("council", councilInfo);
		model.addAttribute("councilProjects", councilProjects);
		if ("read".equals(type)) {
			return vm_path + "upLoadFillReviewMessage.vm";
		}
		return vm_path + "upLoadFillReview.vm";
	}
	
	//	/**
	//	 * 上传会议纪要详情
	//	 * 
	//	 * @param councilId
	//	 * @param spId
	//	 * @param model
	//	 * @return
	//	 */
	//	@RequestMapping("uploadShowMessage.htm")
	//	public String uploadShowMessage(Long councilId, HttpServletRequest request, Model model) {
	//		// 查询会议信息
	//		CouncilInfo councilInfo = councilServiceClient.queryCouncilById(councilId);
	//		// 查询项目评审信息
	//		List<CouncilProjectInfo> councilProjects = councilProjectServiceClient
	//			.queryByCouncilId(councilId);
	//		model.addAttribute("council", councilInfo);
	//		model.addAttribute("councilProjects", councilProjects);
	//		
	//		return vm_path + "upLoadFillReviewMessage.vm";
	//	}
	
	/**
	 * 上传会议纪要
	 * 
	 * @param councilId
	 * @param spId
	 * @param model
	 * @return
	 */
	@RequestMapping("upload.json")
	@ResponseBody
	public JSONObject upload(CouncilSummaryUploadOrder order, String submitStatus,
								HttpServletRequest request, Model model) {
		
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("submitStatus", submitStatus);
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			
			setSessionLocalInfo2Order(order);
			// 这两个参数不需要，给默认值过验证
			order.setCheckIndex(0);
			order.setCheckStatus(1);
			CouncilSummaryResult councilSummaryResult = councilSummaryServiceClient
				.saveGmworkingCouncilSummary(order);
			
			jsonObject = toJSONResult(jsonObject, councilSummaryResult, "会议纪要上传成功", null);
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("会议纪要上传出错", e);
		}
		
		return jsonObject;
		
	}
	
	/**
	 * 查看项目批复
	 * 
	 * @param projectCode 项目编号
	 * @param spCode 批复编号
	 * @return
	 */
	@RequestMapping("approval.htm")
	public String approval(String projectCode, String spCode, Long spId,
							HttpServletRequest request, Model model) {
		
		if (spId == null)
			spId = 0l;
		FCouncilSummaryProjectInfo councilSummaryProject = null;
		if (StringUtil.isNotBlank(projectCode)) {
			councilSummaryProject = councilSummaryServiceClient
				.queryProjectCsByProjectCode(projectCode);
			if (councilSummaryProject != null) {
				spId = councilSummaryProject.getSpId();
			}
		} else if (StringUtil.isNotBlank(spCode)) {
			councilSummaryProject = councilSummaryServiceClient.queryProjectCsBySpCode(spCode);
			spId = councilSummaryProject.getSpId();
		} else if (spId > 0) {
			councilSummaryProject = councilSummaryServiceClient.queryProjectCsBySpId(spId);
			spId = councilSummaryProject.getSpId();
		}
		
		if (councilSummaryProject == null) {
			//查询是否重新授信的项目
			if (StringUtil.isNotBlank(projectCode)) {
				ProjectInfo project = projectServiceClient.queryByCode(projectCode, false);
				if (project != null && project.getIsRedoProject() == BooleanEnum.IS) {
					//跳转到对应的风险处置会
					model.addAttribute("projectCode", project.getRedoFrom());
					model.addAttribute("riskHandleId", project.getRedoRiskHandleId());
					return "redirect:/projectMg/meetingMg/summary/scheme.htm";
				} else {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "未找到 ");
				}
			} else {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "未找到 ");
			}
		}
		
		if (StringUtil.equalsIgnoreCase(request.getParameter("viewType"), "RED")) {
			ProjectInfo project = projectServiceClient.queryByCode(
				councilSummaryProject.getProjectCode(), false);
			model.addAttribute("approval", councilSummaryProject);
			model.addAttribute("project", project);
			return "/projectMg/project/approval.vm";
		}
		
		// 会议纪要
		FCouncilSummaryInfo summary = councilSummaryServiceClient
			.queryCouncilSummaryById(councilSummaryProject.getSummaryId());
		
		FormInfo form = formServiceClient.findByFormId(summary == null ? 0 : summary.getFormId());
		
		//签报传过来的
		FormChangeApplyInfo changeApply = (FormChangeApplyInfo) request.getSession().getAttribute(
			"changeApply");
		if (changeApply != null) {
			changeApply.setChangeFormId(spId);
		}
		
		model.addAttribute("isApproval", true);
		model.addAttribute("footer", request.getParameter("footer"));
		return viewProjectReview(form, summary, spId, model);
	}
	
	@RequestMapping("changeApply.htm")
	public String changeApply(String projectCode, HttpServletRequest request, HttpSession session,
								Model model) {
		FormCheckCanChangeOrder checkOrder = new FormCheckCanChangeOrder();
		checkOrder.setProjectCode(projectCode);
		checkOrder.setChangeForm(FormChangeApplyEnum.PROJECT_APPROVAL);
		checkCanApplyChange(checkOrder);
		setFormChangeApplyInfo(request, session, model);
		approval(projectCode, null, null, request, model);
		return vm_path + "fillReview_changeApply.vm";
	}
	
	@RequestMapping("changeApply/view.htm")
	public String changeApplyView(String projectCode, HttpSession session,
									HttpServletRequest request, Model model) {
		setFormChangeApplyInfo(request, session, model);
		ProjectInfo project = projectServiceClient.queryByCode(projectCode, false);
		if (project == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "项目不存在");
		}
		approval(null, null, project.getSpId(), request, model);
		return vm_path + "auditFillReviewType_changeApply.vm";
	}
	
	/**
	 * 查看风控委纪要
	 * 
	 * @param projectCode 项目编号
	 * @param spCode 批复编号
	 * @return
	 */
	@RequestMapping("summary.htm")
	public String summary(String spCode, Long spId, HttpServletRequest request, Model model) {
		
		FCouncilSummaryProjectInfo councilSummaryProject = null;
		if (spId != null) {
			councilSummaryProject = councilSummaryServiceClient.queryProjectCsBySpId(spId);
		} else {
			councilSummaryProject = councilSummaryServiceClient.queryProjectCsBySpCode(spCode);
		}
		
		if (councilSummaryProject == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "风控委纪要不存在");
		}
		
		// 会议纪要
		FCouncilSummaryInfo summary = null;
		if (councilSummaryProject.getSummaryId() > 0)
			summary = councilSummaryServiceClient.queryCouncilSummaryById(councilSummaryProject
				.getSummaryId());
		
		// 会议纪要表单
		FormInfo form = null;
		if (summary != null)
			form = formServiceClient.findByFormId(summary.getFormId());
		
		model.addAttribute("isApproval", true);
		model.addAttribute("footer", request.getParameter("footer"));
		model.addAttribute("from", request.getParameter("from"));
		return viewProjectReview(form, summary, councilSummaryProject.getSpId(), model);
	}
	
	/**
	 * 查看风险处置方案
	 * 
	 * @param projectCode
	 * @param councilId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("scheme.htm")
	public String scheme(String projectCode, Long councilId, Long handleId,
							HttpServletRequest request, Model model) {
		
		model.addAttribute("timeUnit", TimeUnitEnum.getAllEnum());
		model.addAttribute("chargeTypeList", ChargeTypeEnum.getAllEnum());
		// 押品类型
		//model.addAttribute("pledgeTypeList", PledgeTypeEnum.getAllEnum());
		// 押品性质
		//model.addAttribute("pledgePropertyList", PledgePropertyEnum.getAllEnum());
		model.addAttribute("types", pledgeTypeServiceClient.queryAll().getPageList());
		// 评级
		model.addAttribute("levelList", GradeLevelEnum.getAllEnum());
		// 评级列表
		model.addAttribute("creditLevelList", CreditLevelEnum.getAllEnum());
		// 评级调整方式
		model.addAttribute("adjustTypeList", AdjustTypeEnum.getAllEnum());
		// 保证类型
		model.addAttribute("guarantorTypeList", GuarantorTypeEnum.getAllEnum());
		// 评级
		model.addAttribute("updownList", UpAndDownEnum.getAllEnum());
		
		CouncilSummaryRiskHandleQueryOrder riskHandleQOrder = new CouncilSummaryRiskHandleQueryOrder();
		riskHandleQOrder.setProjectCode(projectCode);
		QueryBaseBatchResult<CouncilSummaryRiskHandleInfo> riskHandles = councilSummaryServiceClient
			.queryApprovalRiskHandleCs(riskHandleQOrder);
		List<FCouncilSummaryRiskHandleInfo> projectList = Lists.newArrayList();
		if (riskHandles != null && riskHandles.isSuccess()) {
			for (FCouncilSummaryRiskHandleInfo project : riskHandles.getPageList()) {
				projectList.add(project);
			}
			model.addAttribute("projectCode", projectCode);
		}
		
		if ((councilId != null && councilId > 0)) {
			FCouncilSummaryInfo summary = councilSummaryServiceClient
				.queryCouncilSummaryByCouncilId(councilId);
			projectList = Lists.newArrayList();
			if (summary != null) {
				model.addAttribute("summary", summary);
				for (FCouncilSummaryRiskHandleInfo info : projectList) {
					if (info.getSummaryId() == summary.getSummaryId()) {// 当次会议的处置方案
						projectList.add(info);
					}
				}
			}
			model.addAttribute("projectCode", "");
			model.addAttribute("councilId", councilId);
		}
		
		if (request.getParameter("riskHandleId") != null) {
			long riskHandleId = NumberUtil.parseLong(request.getParameter("riskHandleId"));
			if (riskHandleId > 0) {
				projectList = Lists.newArrayList();
				for (FCouncilSummaryRiskHandleInfo info : projectList) {
					if (info.getHandleId() == riskHandleId) {// 当次会议的处置方案
						projectList.add(info);
					}
				}
				model.addAttribute("riskHandleId", riskHandleId);
			}
		}
		
		model.addAttribute("projectList", projectList);
		
		boolean hasMatch = false;
		if (handleId != null && handleId > 0) {// 看是否有匹配的项目没有则默认查询第一个
			for (FCouncilSummaryRiskHandleInfo project : projectList) {
				if (project.getHandleId() == handleId)
					hasMatch = true;
			}
			
		}
		if (!hasMatch)
			handleId = 0L;
		
		for (FCouncilSummaryRiskHandleInfo project : projectList) {
			
			if (handleId > 0 && project.getHandleId() != handleId) {
				continue;
			}
			
			handleId = project.getHandleId();
			
			model.addAttribute("project", project);
			model.addAttribute("info", project);
			
			//查询反担保附件
			queryCommonAttachmentData(model, "spId_" + project.getSpId(),
				CommonAttachmentTypeEnum.COUNTER_GUARANTEE);
			
			break;
		}
		
		model.addAttribute("handleId", handleId);
		
		//处置会附件
		queryCommonAttachmentData(model, handleId + "",
			CommonAttachmentTypeEnum.RISK_HANDLE_ATTACH, "");
		
		model.addAttribute("isScheme", true);
		model.addAttribute("footer", request.getParameter("footer"));
		
		return vm_path + "auditRiskDisposition.vm";
	}
	
	/**
	 * 修改会议纪要
	 * 
	 * @param formId
	 * @param spId
	 * @param model
	 * @return
	 */
	@RequestMapping("view.htm")
	public String view(long formId, Long spId, HttpServletRequest request, Model model) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		
		FCouncilSummaryInfo summary = councilSummaryServiceClient
			.queryCouncilSummaryByFormId(formId);
		
		if (summary == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "会议纪要不存在");
		}
		
		model.addAttribute("councilId", summary.getCouncilId());
		
		CouncilTypeEnum councilType = summary.getCouncilType();
		
		model.addAttribute("submitedAll", request.getAttribute("submitedAll"));
		
		model.addAttribute("isView", true);
		
		if (councilType == CouncilTypeEnum.PROJECT_REVIEW) {
			return viewProjectReview(form, summary, spId == null ? 0 : spId, model);
			
		} else if (councilType == CouncilTypeEnum.RISK_HANDLE) {
			return viewRiskHandle(form, summary, spId == null ? 0 : spId, model);
		} else {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "无法匹配会议纪要模板");
		}
	}
	
	/**
	 * 查询项目评审会信息
	 * 
	 * @param summaryId 会议纪要ID
	 * @param model
	 */
	private String viewProjectReview(FormInfo form, FCouncilSummaryInfo summary, long spId,
										Model model) {
		
		model.addAttribute("compareList", CompareEnum.getAllEnum());
		model.addAttribute("chargeTypeList", ChargeTypeEnum.getAllEnum());
		model.addAttribute("timeUnitList", TimeUnitEnum.getAllEnum());
		model.addAttribute("repayWayList", RepayWayEnum.getAllEnum());
		model.addAttribute("chargeWayList", ChargeWayEnum.getAllEnum());
		model.addAttribute("chargePhaseList", ChargePhaseEnum.getAllEnum());
		model.addAttribute("repayWayPhaseList", RepayWayPhaseEnum.getAllEnum());
		model.addAttribute("chargeWayPhaseList", ChargeWayPhaseEnum.getAllEnum());
		// 押品类型
		//model.addAttribute("pledgeTypeList", PledgeTypeEnum.getAllEnum());
		// 押品性质
		//model.addAttribute("pledgePropertyList", PledgePropertyEnum.getAllEnum());
		model.addAttribute("types", pledgeTypeServiceClient.queryAll().getPageList());
		// 评级
		model.addAttribute("levelList", GradeLevelEnum.getAllEnum());
		// 评级列表
		model.addAttribute("creditLevelList", CreditLevelEnum.getAllEnum());
		// 评级调整方式
		model.addAttribute("adjustTypeList", AdjustTypeEnum.getAllEnum());
		// 保证类型
		model.addAttribute("guarantorTypeList", GuarantorTypeEnum.getAllEnum());
		
		// 评级
		model.addAttribute("updownList", UpAndDownEnum.getAllEnum());
		model.addAttribute("summary", summary);
		model.addAttribute("form", form);
		
		// 项目评审会所有项目
		List<FCouncilSummaryProjectInfo> projectList = null;
		if (summary == null) {
			if (spId > 0) {
				projectList = Lists.newArrayList();
				FCouncilSummaryProjectInfo project = councilSummaryServiceClient
					.queryProjectCsBySpId(spId);
				projectList.add(project);
			}
		} else {
			projectList = councilSummaryServiceClient.queryProjectCsBySummaryId(summary
				.getSummaryId());
		}
		
		List<FCouncilSummaryProjectInfo> newList = Lists.newArrayList();
		
		if (spId > 0) {// 看是否有匹配的项目没有则默认查询第一个
			boolean hasMatch = false;
			for (FCouncilSummaryProjectInfo project : projectList) {
				if (project.getSpId() == spId)
					hasMatch = true;
			}
			if (!hasMatch)
				spId = 0;
		}
		
		int checkIndex = -1;
		for (FCouncilSummaryProjectInfo project : projectList) {
			checkIndex++;
			if (spId > 0 && project.getSpId() != spId) {
				continue;
			}
			
			//	编辑反担保措施，项目客户经理只能编辑自己的
			if (model.containsAttribute("editCredit") && DataPermissionUtil.isBusiManager()
				&& !DataPermissionUtil.isBusiManager(project.getProjectCode())) {
				continue;
			}
			
			spId = project.getSpId();
			
			if (ProjectUtil.isFinancial(project.getProjectCode())) {
				project.setTemplete("LC");
				model.addAttribute("project", project);
				model.addAttribute("info", project);
			} else if (isBond(project.getBusiType())) {// 发债业务
				FCouncilSummaryProjectBondInfo info = councilSummaryServiceClient
					.queryBondProjectCsBySpId(project.getSpId(), true);
				info.setTemplete("FZ");
				model.addAttribute("project", info);
				model.addAttribute("info", info);
				if ((info.getVoteResult() == ProjectVoteResultEnum.END_PASS || info.getVoteResult() == ProjectVoteResultEnum.END_NOPASS)
					&& (form == null || form.getStatus() == FormStatusEnum.APPROVAL)) {
					model.addAttribute("approvalCode", info.getSpCode());
				}
				
				//查询反担保附件
				queryCommonAttachmentData(model, "spId_" + spId,
					CommonAttachmentTypeEnum.COUNTER_GUARANTEE);
				
			} else if (isLitigation(project.getBusiType())) {// 诉讼保函
				FCouncilSummaryProjectLgLitigationInfo info = councilSummaryServiceClient
					.queryLgLitigationProjectCsBySpId(project.getSpId(), true);
				info.setTemplete("SB");
				model.addAttribute("project", info);
				model.addAttribute("info", info);
				if ((info.getVoteResult() == ProjectVoteResultEnum.END_PASS || info.getVoteResult() == ProjectVoteResultEnum.END_NOPASS)
					&& (form == null || form.getStatus() == FormStatusEnum.APPROVAL)) {
					model.addAttribute("approvalCode", info.getSpCode());
				}
				
			} else if (isUnderwriting(project.getBusiType())) { // 承销业务
				FCouncilSummaryProjectUnderwritingInfo info = councilSummaryServiceClient
					.queryUnderwritingProjectCsBySpId(project.getSpId(), true);
				info.setTemplete("CX");
				model.addAttribute("project", info);
				model.addAttribute("info", info);
				if ((info.getVoteResult() == ProjectVoteResultEnum.END_PASS || info.getVoteResult() == ProjectVoteResultEnum.END_NOPASS)
					&& (form == null || form.getStatus() == FormStatusEnum.APPROVAL)) {
					model.addAttribute("approvalCode", info.getSpCode());
				}
				
			} else if (isEntrusted(project.getBusiType())) { // 委贷
				FCouncilSummaryProjectEntrustedInfo info = councilSummaryServiceClient
					.queryEntrustedProjectCsBySpId(project.getSpId(), true);
				info.setTemplete("WD");
				model.addAttribute("project", info);
				model.addAttribute("info", info);
				if ((info.getVoteResult() == ProjectVoteResultEnum.END_PASS || info.getVoteResult() == ProjectVoteResultEnum.END_NOPASS)
					&& (form == null || form.getStatus() == FormStatusEnum.APPROVAL)) {
					model.addAttribute("approvalCode", info.getSpCode());
				}
				
				//查询反担保附件
				queryCommonAttachmentData(model, "spId_" + spId,
					CommonAttachmentTypeEnum.COUNTER_GUARANTEE);
				
			} else { // 其他(担保)通用
				FCouncilSummaryProjectGuaranteeInfo info = councilSummaryServiceClient
					.queryGuaranteeProjectCsBySpId(project.getSpId(), true);
				info.setTemplete("DB");
				model.addAttribute("project", info);
				model.addAttribute("info", info);
				if ((info.getVoteResult() == ProjectVoteResultEnum.END_PASS || info.getVoteResult() == ProjectVoteResultEnum.END_NOPASS)
					&& (form == null || form.getStatus() == FormStatusEnum.APPROVAL)) {
					model.addAttribute("approvalCode", info.getSpCode());
				}
				
				//查询反担保附件
				queryCommonAttachmentData(model, "spId_" + spId,
					CommonAttachmentTypeEnum.COUNTER_GUARANTEE);
			}
			
			model.addAttribute("checkIndex", checkIndex);
			model.addAttribute("checkStatus",
				form == null ? "1" : form.getCheckStatus().charAt(checkIndex));
			
			break;
		}
		
		model.addAttribute("spId", spId);
		if (model.containsAttribute("editCredit") && DataPermissionUtil.isBusiManager()) {
			
			for (FCouncilSummaryProjectInfo project : projectList) {
				if (DataPermissionUtil.isBusiManager(project.getProjectCode())) {
					newList.add(project);
				}
			}
			if (ListUtil.isEmpty(newList)) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "只有项目客户经理才能修改");
			} else {
				model.addAttribute("projectList", newList);
			}
		} else {
			model.addAttribute("projectList", projectList);
		}
		
		//评审会附件
		queryCommonAttachmentData(model, spId + "", CommonAttachmentTypeEnum.PROJECT_REVIEW_ATTACH,
			"");
		
		//作废了后就没入口重新编辑了
		showNoEndForm(model);
		FCouncilSummaryProjectInfo project = (FCouncilSummaryProjectInfo) model.asMap().get(
			"project");
		if (ProjectUtil.isInnovativeProduct(project.getBusiType())) {
			project.setOverview(summary.getOverview());
			queryCommonAttachmentData(model, "SUMMARY_1_" + project.getSpId(),
				CommonAttachmentTypeEnum.SUMMARY_INNOVATIVE_PRODUCT);
			return vm_path + "auditInnovativeProductFillReviewType.vm";
		}
		return vm_path + "auditFillReviewType.vm";
	}
	
	/**
	 * 查看风险审查会议纪要
	 * 
	 * @param summaryId
	 * @param model
	 */
	private String viewRiskHandle(FormInfo form, FCouncilSummaryInfo summary, long handleId,
									Model model) {
		
		model.addAttribute("timeUnit", TimeUnitEnum.getAllEnum());
		model.addAttribute("chargeTypeList", ChargeTypeEnum.getAllEnum());
		// 押品类型
		//model.addAttribute("pledgeTypeList", PledgeTypeEnum.getAllEnum());
		// 押品性质
		//model.addAttribute("pledgePropertyList", PledgePropertyEnum.getAllEnum());
		model.addAttribute("types", pledgeTypeServiceClient.queryAll().getPageList());
		// 评级
		model.addAttribute("levelList", GradeLevelEnum.getAllEnum());
		// 评级列表
		model.addAttribute("creditLevelList", CreditLevelEnum.getAllEnum());
		// 评级调整方式
		model.addAttribute("adjustTypeList", AdjustTypeEnum.getAllEnum());
		// 保证类型
		model.addAttribute("guarantorTypeList", GuarantorTypeEnum.getAllEnum());
		// 评级
		model.addAttribute("updownList", UpAndDownEnum.getAllEnum());
		
		model.addAttribute("summary", summary);
		model.addAttribute("form", form);
		List<FCouncilSummaryRiskHandleInfo> projectList = councilSummaryServiceClient
			.queryRiskHandleCsBySummaryId(summary.getSummaryId());
		if (ListUtil.isNotEmpty(projectList)) {
			
			//List<FCouncilSummaryRiskHandleInfo> newList = Lists.newArrayList();
			for (FCouncilSummaryRiskHandleInfo handle : projectList) {
				
				ProjectInfo project = projectServiceClient.queryByCode(handle.getProjectCode(),
					false);
				if (project != null) {
					//在保金额
					handle.setInGuaranteeAmount(project.getBalance());
				}
				//				if (handleId == handle.getHandleId()) {
				//					newList.add(handle);
				//				}
			}
			
			//			if (handleId > 0) {
			//				model.addAttribute("projectList", newList);
			//			} else {
			model.addAttribute("projectList", projectList);
			//			}
			
			if (handleId > 0) {// 看是否有匹配的项目没有则默认查询第一个
				boolean hasMatch = false;
				for (FCouncilSummaryRiskHandleInfo project : projectList) {
					if (project.getHandleId() == handleId)
						hasMatch = true;
				}
				if (!hasMatch)
					handleId = 0;
			}
			
			int checkIndex = -1;
			for (FCouncilSummaryRiskHandleInfo project : projectList) {
				checkIndex++;
				if (handleId > 0 && project.getHandleId() != handleId) {
					continue;
				}
				
				handleId = project.getHandleId();
				
				model.addAttribute("project", project);
				model.addAttribute("info", project);
				
				model.addAttribute("checkIndex", checkIndex);
				model.addAttribute("checkStatus", form == null ? "1" : form.getCheckStatus()
					.charAt(checkIndex));
				
				//查询反担保附件
				queryCommonAttachmentData(model, "spId_" + project.getSpId(),
					CommonAttachmentTypeEnum.COUNTER_GUARANTEE);
				
				break;
			}
			
			model.addAttribute("handleId", handleId);
		}
		
		//处置会附件
		queryCommonAttachmentData(model, handleId + "",
			CommonAttachmentTypeEnum.RISK_HANDLE_ATTACH, "");
		
		return vm_path + "auditRiskDisposition.vm";
	}
	
	/**
	 * 董事长审核会议纪要
	 * 
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("audit/president.htm")
	public String president(long formId, Long spId, HttpServletRequest request, Model model) {
		model.addAttribute("presidentAudit", true);
		return audit(formId, spId, request, model);
	}
	
	/**
	 * 审核
	 * @param formId
	 * @param spId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("audit.htm")
	public String audit(long formId, Long spId, HttpServletRequest request, Model model) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		
		FCouncilSummaryInfo summary = councilSummaryServiceClient
			.queryCouncilSummaryByFormId(formId);
		
		if (summary == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "会议纪要不存在");
		}
		
		model.addAttribute("councilId", summary.getCouncilId());
		
		CouncilTypeEnum councilType = summary.getCouncilType();
		
		initWorkflow(model, form, request.getParameter("taskId"));
		
		model.addAttribute("submitedAll", request.getAttribute("submitedAll"));
		
		model.addAttribute("isAudit", true);
		model.addAttribute("auditTaskId", request.getParameter("taskId"));
		
		//是否可编辑授信措施
		boolean editCredit = StringUtil.equalsIgnoreCase(request.getParameter("editCredit"), "YES");
		if (councilType == CouncilTypeEnum.PROJECT_REVIEW) {
			String vmPath = vm_path + "auditFillReviewType.vm";
			if (editCredit && !model.containsAttribute("notExecutor")) {
				vmPath = vm_path + "fillReview.vm";
				model.addAttribute("editCredit", editCredit);
			}
			
			viewProjectReview(form, summary, spId == null ? 0 : spId, model);
			FCouncilSummaryProjectInfo projectInfo = (FCouncilSummaryProjectInfo) model.asMap()
				.get("project");
			if (ProjectUtil.isInnovativeProduct(projectInfo.getBusiType())) {
				queryCommonAttachmentData(model, "SUMMARY_1_" + projectInfo.getSpId(),
					CommonAttachmentTypeEnum.SUMMARY_INNOVATIVE_PRODUCT);
				vmPath = vm_path + "auditInnovativeProductFillReviewType.vm";
			}
			
			if (editCredit && !model.containsAttribute("notExecutor")) {
				if (ProjectUtil.isInnovativeProduct(projectInfo.getBusiType())) {
					
					vmPath = vm_path + "innovativeProductFillReview.vm";
				}
			}
			return vmPath;
		} else if (councilType == CouncilTypeEnum.RISK_HANDLE) {
			viewRiskHandle(form, summary, spId == null ? 0 : spId, model);
			return vm_path + "auditRiskDisposition.vm";
		} else {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "无法匹配会议纪要模板");
		}
	}
	
	/**
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveCouncilSummary.htm")
	@ResponseBody
	public JSONObject saveCouncilSummary(FCouncilSummaryOrder order, HttpServletRequest request,
											Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			
			setSessionLocalInfo2Order(order);
			
			FcsBaseResult result = councilSummaryServiceClient.saveCouncilSummary(order);
			
			jsonObject = toJSONResult(jsonObject, result, "会议纪要保存成功", null);
			jsonObject.put("tabId", request.getParameter("tabId"));
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			jsonObject.put("tabId", request.getParameter("tabId"));
			logger.error("保存会议纪要出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 保存项目会议纪要通用部分
	 * 
	 * @return
	 */
	@RequestMapping("saveProjectCsCommon.htm")
	@ResponseBody
	public JSONObject saveProjectCsCommon(FCouncilSummaryProjectOrder order,
											HttpServletRequest request, Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			setSessionLocalInfo2Order(order);
			
			FcsBaseResult result = councilSummaryServiceClient.saveProjectCsCommon(order);
			
			if (result != null && result.isSuccess()) {
				addAttachfile("" + result.getKeyId(), request, order.getProjectCode(), null,
					CommonAttachmentTypeEnum.PROJECT_REVIEW_ATTACH);
			}
			
			jsonObject = toJSONResult(jsonObject, result, "保存成功", null);
			jsonObject.put("tabId", request.getParameter("tabId"));
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			jsonObject.put("tabId", request.getParameter("tabId"));
			logger.error("保存项目会议纪要出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 保存发债项目会议纪要
	 * 
	 * @return
	 */
	@RequestMapping("saveBondProjectCs.htm")
	@ResponseBody
	public JSONObject saveBondProjectCs(FCouncilSummaryProjectBondOrder order,
										HttpServletRequest request, Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			
			setSessionLocalInfo2Order(order);
			
			FcsBaseResult result = councilSummaryServiceClient.saveBondProjectCs(order);
			
			jsonObject = toJSONResult(jsonObject, result, "发债项目会议纪要保存成功", null);
			
			jsonObject.put("tabId", request.getParameter("tabId"));
			
			//保存附件
			if (result != null && result.isSuccess()) {
				addAttachfile("spId_" + result.getKeyId(), request, order.getProjectCode(),
					"反担保附件", CommonAttachmentTypeEnum.COUNTER_GUARANTEE);
				
				addAttachfile("" + result.getKeyId(), request, order.getProjectCode(), null,
					CommonAttachmentTypeEnum.PROJECT_REVIEW_ATTACH);
			}
			
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			jsonObject.put("tabId", request.getParameter("tabId"));
			logger.error("保存发债项目会议纪要出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 保存担保项目会议纪要
	 * 
	 * @return
	 */
	@RequestMapping("saveGuaranteeProjectCs.htm")
	@ResponseBody
	public JSONObject saveGuaranteeProjectCs(FCouncilSummaryProjectGuaranteeOrder order,
												HttpServletRequest request, Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			
			setSessionLocalInfo2Order(order);
			
			FcsBaseResult result = councilSummaryServiceClient.saveGuaranteeProjectCs(order);
			
			jsonObject = toJSONResult(jsonObject, result, "保存成功", null);
			jsonObject.put("tabId", request.getParameter("tabId"));
			
			//保存附件
			if (result != null && result.isSuccess()) {
				addAttachfile("spId_" + result.getKeyId(), request, order.getProjectCode(),
					"反担保附件", CommonAttachmentTypeEnum.COUNTER_GUARANTEE);
				
				addAttachfile("SUMMARY_1_" + result.getKeyId(), request, "", "创新项目上会会议纪要附件",
					CommonAttachmentTypeEnum.SUMMARY_INNOVATIVE_PRODUCT);
				
				addAttachfile("" + result.getKeyId(), request, order.getProjectCode(), null,
					CommonAttachmentTypeEnum.PROJECT_REVIEW_ATTACH);
			}
			
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			jsonObject.put("tabId", request.getParameter("tabId"));
			logger.error("保担保项目会议纪要出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 保存委贷项目会议纪要
	 * 
	 * @return
	 */
	@RequestMapping("saveEntrustedProjectCs.htm")
	@ResponseBody
	public JSONObject saveEntrustedProjectCs(FCouncilSummaryProjectEntrustedOrder order,
												HttpServletRequest request, Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			
			setSessionLocalInfo2Order(order);
			
			FcsBaseResult result = councilSummaryServiceClient.saveEntrustedProjectCs(order);
			
			jsonObject = toJSONResult(jsonObject, result, "委贷项目会议纪要保存成功", null);
			jsonObject.put("tabId", request.getParameter("tabId"));
			
			//保存附件
			if (result != null && result.isSuccess()) {
				addAttachfile("spId_" + result.getKeyId(), request, order.getProjectCode(),
					"反担保附件", CommonAttachmentTypeEnum.COUNTER_GUARANTEE);
				
				addAttachfile("" + result.getKeyId(), request, order.getProjectCode(), null,
					CommonAttachmentTypeEnum.PROJECT_REVIEW_ATTACH);
			}
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			jsonObject.put("tabId", request.getParameter("tabId"));
			logger.error("保委贷项目会议纪要出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 保存承销项目会议纪要
	 * 
	 * @return
	 */
	@RequestMapping("saveUnderwritingProjectCs.htm")
	@ResponseBody
	public JSONObject saveUnderwritingProjectCs(FCouncilSummaryProjectUnderwritingOrder order,
												HttpServletRequest request, Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			
			setSessionLocalInfo2Order(order);
			
			FcsBaseResult result = councilSummaryServiceClient.saveUnderwritingProjectCs(order);
			
			if (result != null && result.isSuccess()) {
				addAttachfile("" + result.getKeyId(), request, order.getProjectCode(), null,
					CommonAttachmentTypeEnum.PROJECT_REVIEW_ATTACH);
			}
			
			jsonObject = toJSONResult(jsonObject, result, "保存成功", null);
			jsonObject.put("tabId", request.getParameter("tabId"));
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			jsonObject.put("tabId", request.getParameter("tabId"));
			logger.error("保承销项目会议纪要出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 保存诉讼担保项目会议纪要
	 * 
	 * @return
	 */
	@RequestMapping("saveLgLitigationProjectCs.htm")
	@ResponseBody
	public JSONObject saveLgLitigationProjectCs(FCouncilSummaryProjectLgLitigationOrder order,
												HttpServletRequest request, Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			
			setSessionLocalInfo2Order(order);
			
			FcsBaseResult result = councilSummaryServiceClient.saveLgLitigationProjectCs(order);
			
			if (result != null && result.isSuccess()) {
				addAttachfile("" + result.getKeyId(), request, order.getProjectCode(), null,
					CommonAttachmentTypeEnum.PROJECT_REVIEW_ATTACH);
			}
			
			jsonObject = toJSONResult(jsonObject, result, "保存成功", null);
			jsonObject.put("tabId", request.getParameter("tabId"));
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			jsonObject.put("tabId", request.getParameter("tabId"));
			logger.error("保存诉讼担保项目会议纪要出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 保存风险处置方案
	 * 
	 * @return
	 */
	@RequestMapping("saveRiskHandleCs.htm")
	@ResponseBody
	public JSONObject saveRiskHandleCs(FCouncilSummaryRiskHandleOrder order,
										HttpServletRequest request, Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			
			setSessionLocalInfo2Order(order);
			
			FcsBaseResult result = councilSummaryServiceClient.saveRiskHandleCs(order);
			
			//保存附件
			if (result != null && result.isSuccess()) {
				
				addAttachfile("spId_" + result.getKeyId(), request, null, "反担保附件",
					CommonAttachmentTypeEnum.COUNTER_GUARANTEE);
				
				//url返回handleId
				addAttachfile("" + result.getUrl(), request, order.getProjectCode(), null,
					CommonAttachmentTypeEnum.RISK_HANDLE_ATTACH);
			}
			
			jsonObject = toJSONResult(jsonObject, result, "保存成功", null);
			jsonObject.put("tabId", request.getParameter("tabId"));
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			jsonObject.put("tabId", request.getParameter("tabId"));
			logger.error("保存风险处置方案出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("oneVoteDown.htm")
	@ResponseBody
	public JSONObject oneVoteDown(OneVoteDownOrder order, HttpServletRequest request, Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			
			setSessionLocalInfo2Order(order);
			
			FcsBaseResult result = councilSummaryServiceClient.oneVoteDown(order);
			
			jsonObject = toJSONResult(jsonObject, result, "处理成功", null);
			jsonObject.put("tabId", order.getSpId());
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			jsonObject.put("tabId", order.getSpId());
			logger.error("一票权执行出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 检查会议纪要中没有建立风险处置小组的项目
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("checkNoRiskTeamProject.json")
	@ResponseBody
	public JSONObject checkNoRiskTeamProject(long formId, HttpServletRequest request, Model model) {
		
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			
			FCouncilSummaryInfo summaryInfo = councilSummaryServiceClient
				.queryCouncilSummaryByFormId(formId);
			
			if (summaryInfo == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "未找到会议纪要");
				return jsonObject;
			}
			
			//本次填写的会议纪要
			List<FCouncilSummaryRiskHandleInfo> riskHandles = councilSummaryServiceClient
				.queryRiskHandleCsBySummaryId(summaryInfo.getSummaryId());
			Set<String> projectCodes = new HashSet<String>();
			for (FCouncilSummaryRiskHandleInfo riskHandle : riskHandles) {
				projectCodes.add(riskHandle.getProjectCode());
			}
			
			//查看没有风险处置小组的
			String noRiskTeamProject = "";
			RiskHandlerTeamQueryOrder queryOrder = new RiskHandlerTeamQueryOrder();
			for (String projectCode : projectCodes) {
				queryOrder.setProjectCode(projectCode);
				QueryBaseBatchResult<RiskHandleTeamInfo> riskTeam = riskHandleTeamServiceClient
					.queryRiskHandleTeam(queryOrder);
				if (riskTeam != null && riskTeam.isSuccess() && riskTeam.getTotalCount() == 0) {
					noRiskTeamProject += projectCode + ",";
				}
			}
			if (StringUtil.isNotBlank(noRiskTeamProject)) {
				noRiskTeamProject = noRiskTeamProject.substring(0, noRiskTeamProject.length() - 1);
			}
			jsonObject.put("success", true);
			jsonObject.put("noRiskTeamProject", noRiskTeamProject);
			jsonObject.put("message", "查询成功");
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", "查询出错");
			logger.error("第一次风险处置会检查是否创建风险处置小组出错：{}", e);
		}
		
		return jsonObject;
		
	}
	
	@RequestMapping("presidentComment.json")
	public String presidentComment(HttpServletRequest request, Model model) {
		long summaryId = NumberUtil.parseLong(request.getParameter("summaryId"));
		if (summaryId > 0) {
			//尽调附件管理 (项目编号：2017-0300-011-058)
			List<FCouncilSummaryProjectInfo> projects = councilSummaryServiceClient
				.queryProjectCsBySummaryId(summaryId);
			model.addAttribute("comments", projects);
		}
		return vm_path + "presidentComment.vm";
	}
	
	/**
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("submit.json")
	@ResponseBody
	public JSONObject submit(CouncilSummarySubmitOrder order, HttpServletRequest request,
								HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		try {
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
				doNext(request, response, TaskOpinion.STATUS_AGREE);
				return null;
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
	
	/**
	 * 风险处置会议纪要填写时加载授信前提条件页面
	 * @param handleId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("pageCredit.json")
	public String pageCredit(long handleId, HttpServletRequest request, Model model) {
		
		model.addAttribute("timeUnit", TimeUnitEnum.getAllEnum());
		model.addAttribute("chargeTypeList", ChargeTypeEnum.getAllEnum());
		// 押品类型
		//model.addAttribute("pledgeTypeList", PledgeTypeEnum.getAllEnum());
		// 押品性质
		//model.addAttribute("pledgePropertyList", PledgePropertyEnum.getAllEnum());
		model.addAttribute("types", pledgeTypeServiceClient.queryAll().getPageList());
		// 评级
		model.addAttribute("levelList", GradeLevelEnum.getAllEnum());
		// 评级列表
		model.addAttribute("creditLevelList", CreditLevelEnum.getAllEnum());
		// 评级调整方式
		model.addAttribute("adjustTypeList", AdjustTypeEnum.getAllEnum());
		// 保证类型
		model.addAttribute("guarantorTypeList", GuarantorTypeEnum.getAllEnum());
		// 评级
		model.addAttribute("updownList", UpAndDownEnum.getAllEnum());
		
		FCouncilSummaryRiskHandleInfo info = councilSummaryServiceClient
			.queryRiskHandleCsByHandleId(handleId);
		if (info != null) {
			model.addAttribute("info", info);
			if (info.getSpId() == 0) {//还没有填写，查询项目批复中的授信信息
				ProjectInfo projectInfo = projectServiceClient.queryByCode(info.getProjectCode(),
					false);
				if (ProjectUtil.isBond(projectInfo.getBusiType())) {
					model.addAttribute("info",
						councilSummaryServiceClient.queryBondProjectCsBySpId(info.getSpId(), true));
				} else if (ProjectUtil.isEntrusted(projectInfo.getBusiType())) {
					model.addAttribute("info", councilSummaryServiceClient
						.queryEntrustedProjectCsBySpId(info.getSpId(), true));
				} else {
					model.addAttribute("info", councilSummaryServiceClient
						.queryGuaranteeProjectCsBySpId(info.getSpId(), true));
				}
			}
			
			//查询反担保附件
			queryCommonAttachmentData(model, "spId_" + info.getSpId(),
				CommonAttachmentTypeEnum.COUNTER_GUARANTEE);
		}
		return vm_path + "pageCredit.vm";
	}
}
