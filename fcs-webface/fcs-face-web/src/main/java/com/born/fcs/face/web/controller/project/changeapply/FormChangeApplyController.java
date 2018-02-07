package com.born.fcs.face.web.controller.project.changeapply;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.com.google.common.collect.Maps;

import com.alibaba.fastjson.JSONObject;
import com.born.bpm.service.client.user.SysUser;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.FormChangeApplyEnum;
import com.born.fcs.pm.ws.enums.FormChangeApplyStatusEnum;
import com.born.fcs.pm.ws.enums.FormChangeApplyTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.basicmaintain.DecisionInstitutionInfo;
import com.born.fcs.pm.ws.info.basicmaintain.DecisionMemberInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeApplyInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeApplySearchInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeRecordInfo;
import com.born.fcs.pm.ws.order.formchange.FormChangeApplyQueryOrder;
import com.born.fcs.pm.ws.order.formchange.FormChangeRecordOrder;
import com.born.fcs.pm.ws.order.formchange.FormChangeRecordQueryOrder;
import com.born.fcs.pm.ws.order.formchange.FormCheckCanChangeOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

/**
 * 签报申请
 * @author wuzj
 * 
 */
@Controller
@RequestMapping("projectMg/formChangeApply")
public class FormChangeApplyController extends WorkflowBaseController {
	
	private static final String vm_path = "/projectMg/assistSys/changeApply/";
	
	@RequestMapping("form.htm")
	public String form(String projectCode, HttpServletRequest request, Model model) {
		//		if (!DataPermissionUtil.isBusiManager() && !DataPermissionUtil.isXinHuiBusiManager()) {
		//			throw ExceptionFactory.newFcsException(FcsResultEnum.NO_ACCESS, "无权发起签报");
		//		}
		model.addAttribute("applyForm", FormChangeApplyEnum.getAllEnum());
		model.addAttribute("applyType", FormChangeApplyTypeEnum.getAllEnum());
		FormInfo initForm = new FormInfo();
		model.addAttribute("form", initForm);
		return vm_path + "apply.vm";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("list.htm")
	public String list(FormChangeApplyQueryOrder order, HttpServletRequest request, Model model) {
		if (order.getSortCol() == null) {
			order.setSortCol("f.form_id");
			order.setSortOrder("desc");
		}
		setSessionLocalInfo2Order(order);
		model.addAttribute("queryOrder", order);
		List statusList = Lists.newArrayList();
		statusList.addAll(FormStatusEnum.getAllEnum());
		//		statusList.add(CommonUtil.newJson("{'code':'COUNCIL_WAITING','message':'上会中'}"));
		//		statusList.add(CommonUtil.newJson("{'code':'COUNCIL_APPROVAL','message':'上会通过'}"));
		//		statusList.add(CommonUtil.newJson("{'code':'COUNCIL_DENY','message':'上会未通过'}"));
		model.addAttribute("statusList", statusList);
		QueryBaseBatchResult<FormChangeApplySearchInfo> applys = formChangeApplyServiceClient
			.searchForm(order);
		model.addAttribute("page", PageUtil.getCovertPage(applys));
		return vm_path + "list.vm";
	}
	
	@RequestMapping("record.htm")
	public String changeRecord(String projectCode, Long customerId, FormChangeApplyEnum changeForm,
								Long changeFormId, Model model) {
		FormChangeApplyQueryOrder order = new FormChangeApplyQueryOrder();
		order.setProjectCode(projectCode);
		if (customerId != null)
			order.setCustomerId(customerId);
		if (changeForm != null)
			order.setChangeForm(changeForm.code());
		if (changeFormId != null)
			order.setChangeFormId(changeFormId);
		order.setStatus(FormChangeApplyStatusEnum.APPROVAL.code());
		order.setSortCol("apply_id");
		order.setSortOrder("desc");
		order.setQueryRecord(true);
		order.setPageSize(100);
		QueryBaseBatchResult<FormChangeApplyInfo> applys = formChangeApplyServiceClient
			.searchApply(order);
		if (applys != null && applys.isSuccess()) {
			model.addAttribute("applyRecordList", applys.getPageList());
		}
		
		return vm_path + "record.vm";
	}
	
	@RequestMapping("view.htm")
	public String view(long formId, HttpSession session, HttpServletRequest request, Model model) {
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		viewApply(form, model);
		setAuditHistory2Page(form, model);
		return vm_path + "auditViewApply.vm";
	}
	
	@RequestMapping("edit.htm")
	public String edit(long formId, HttpServletRequest request, HttpSession session, Model model) {
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		FormChangeApplyInfo apply = viewApply(form, model);
		if (apply != null && apply.getApplyType() == FormChangeApplyTypeEnum.FORM) {
			session.setAttribute("changeApply", apply);
			session.setAttribute("changeApplyForm", form);
			if (ListUtil.isNotEmpty(apply.getRecords())) {
				for (FormChangeRecordInfo record : apply.getRecords()) {
					if (sessionLocal.getUserId() == sessionLocal.getUserId()) {
						session.setAttribute("changeRecord", record);
					}
				}
			}
			if (apply.getChangeForm() == FormChangeApplyEnum.CHARGE_REPAY_PLAY) {
				model.addAttribute("planId", apply.getChangeFormId());
			}
			model.addAttribute("projectCode", apply.getProjectCode());
			model.addAttribute("customerId", apply.getCustomerId());
			//这里forward projectCode带不过去
			return "redirect:" + apply.getChangeForm().getFormUrl();
		}
		return vm_path + "apply.vm";
	}
	
	@RequestMapping("jump2Form.htm")
	public String jump2Form(FormChangeRecordOrder order, HttpSession session,
							HttpServletRequest request, Model model) {
		Long applyFormId = order.getApplyFormId();
		if (applyFormId != null && applyFormId > 0) {
			FormInfo form = formServiceClient.findByFormId(applyFormId);
			if (form == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
			}
			
			//SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			FormChangeApplyInfo apply = viewApply(form, model);
			if (apply != null && apply.getApplyType() == FormChangeApplyTypeEnum.FORM) {
				session.setAttribute("changeApply", null);
				session.setAttribute("changeApplyForm", null);
				session.setAttribute("changeRecord", null);
				model.addAttribute("changeApply", apply);
				model.addAttribute("changeApplyForm", form);
				if (ListUtil.isNotEmpty(apply.getRecords())) {
					for (FormChangeRecordInfo record : apply.getRecords()) {
						model.addAttribute("changeRecord", record);
					}
				}
				model.addAttribute("projectCode", apply.getProjectCode());
			}
			
			return vm_path + "viewChangeForm.vm";
		} else {
			FormInfo form = new FormInfo();
			FormChangeApplyInfo apply = new FormChangeApplyInfo();
			apply.setProjectCode(order.getProjectCode());
			apply.setCustomerId(order.getCustomerId());
			apply.setApplyType(FormChangeApplyTypeEnum.FORM);
			apply.setApplyId(0);
			apply.setChangeForm(order.getChangeForm());
			if (order.getChangeFormId() != null)
				apply.setChangeFormId(order.getChangeFormId());
			session.setAttribute("changeRecord", null);
			if (order.getChangeForm() == FormChangeApplyEnum.CHARGE_REPAY_PLAY) {
				apply.setChangeFormId(NumberUtil.parseLong(StringUtil.defaultIfBlank(request
					.getParameter("planId"))));
			}
			session.setAttribute("changeApply", apply);
			session.setAttribute("changeApplyForm", form);
			model.addAttribute("projectCode", apply.getProjectCode());
			if (StringUtil.isBlank(order.getChangeForm().getFormUrl())) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "尚不支持");
			}
			return "forward:" + apply.getChangeForm().getFormUrl();
		}
	}
	
	@RequestMapping("checkCanChange.htm")
	@ResponseBody
	public JSONObject checkCanChange(FormCheckCanChangeOrder checkOrder) {
		JSONObject result = new JSONObject();
		try {
			FcsBaseResult checkResult = formChangeApplyServiceClient.checkCanChange(checkOrder);
			if (checkResult.isSuccess()) {
				result.put("canChange", true);
				result.put("message", "检查是否可签报成功");
			} else {
				result.put("canChange", false);
				result.put("message", checkResult.getMessage());
			}
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("canChange", false);
			result.put("message", "检查是否可签报出错");
		}
		
		return result;
	}
	
	@RequestMapping("audit.htm")
	public String audit(long formId, HttpServletRequest request, Model model) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		initWorkflow(model, form, request.getParameter("taskId"));
		viewApply(form, model);
		return vm_path + "auditViewApply.vm";
	}
	
	/**
	 * 选择分支路径
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("audit/selPath.htm")
	public String selPath(long formId, HttpServletRequest request, Model model) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		
		initWorkflow(model, form, request.getParameter("taskId"));
		
		viewApply(form, model);
		
		//公司领导们
		String ldDeptCode = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.COMPANY_LEADER_DEPT_CODE.code());
		if (StringUtil.isNotBlank(ldDeptCode)) {
			List<SysUser> leaders = bpmUserQueryService.findUserByDeptCode(ldDeptCode);
			model.addAttribute("leaders", leaders);
		}
		//会签部门
		String signDepts = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.FORM_CHANGE_SIGN_DEPARTMENT.code());
		if (StringUtil.isNotEmpty(signDepts)) {
			signDepts = signDepts.replaceAll("\r", "").replaceAll("\n", "");
			String[] depts = signDepts.split(";");
			if (depts != null && depts.length > 0) {
				List<JSONObject> deptList = Lists.newArrayList();
				for (String dept : depts) {
					if (StringUtil.isBlank(dept))
						continue;
					String[] code_name = dept.split("_");
					if (code_name != null && code_name.length == 2) {
						JSONObject deptObj = new JSONObject();
						deptObj.put("deptCode", code_name[0].trim());
						deptObj.put("deptName", code_name[1]);
						deptList.add(deptObj);
					}
				}
				model.addAttribute("deptList", deptList);
			}
		}
		String leaderTaskNodeId = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.FORM_CHANGE_LEADER_TASK_NODE_ID.code());
		model.addAttribute("leaderTaskNodeId", leaderTaskNodeId);
		
		String deptTaskNodeId = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.FORM_CHANGE_SIGN_DEPARTMENT_TASK_NODE_ID.code());
		model.addAttribute("deptTaskNodeId", deptTaskNodeId);
		
		//nodeId,项目评审委员会,风险处置委员会
		String wyhTaskNode = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.FORM_CHANGE_WYH_TASK_NODE.code());
		if (StringUtil.isNotBlank(wyhTaskNode)) {
			//wyhTaskNode.replaceAll("，", ",");
			String[] taskNodeAndWyh = wyhTaskNode.split(",");
			model.addAttribute("wyhTaskNodeId", taskNodeAndWyh[0]);
			Map<String, List<DecisionMemberInfo>> wyhUserMap = Maps.newHashMap();
			for (String wyh : taskNodeAndWyh) {
				if (StringUtil.equals(taskNodeAndWyh[0], wyh))
					continue;
				//查询对应决策机构人员
				DecisionInstitutionInfo info = decisionInstitutionServiceClient
					.findDecisionInstitutionByInstitutionName(wyh);
				List<DecisionMemberInfo> members = null;
				if (info != null)
					members = decisionMemberServiceClient.queryDecisionMemberInfo(info
						.getInstitutionId());
				wyhUserMap.put(wyh, members);
			}
			model.addAttribute("wyhMemberMap", wyhUserMap);
		}
		return vm_path + "auditViewApply.vm";
	}
	
	/**
	 * 选择分支路径
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("audit/selManager.htm")
	public String selManager(long formId, HttpServletRequest request, Model model) {
		model.addAttribute("selManager", "YES");
		return audit(formId, request, model);
	}
	
	@RequestMapping("reExeChange.json")
	@ResponseBody
	public JSONObject reExeChange(long formId, HttpServletRequest request, Model model) {
		JSONObject result = new JSONObject();
		try {
			FormChangeApplyInfo applyInfo = formChangeApplyServiceClient.queryByFormId(formId);
			if (applyInfo == null) {
				result.put("success", false);
				result.put("message", "申请单未找到");
			}
			
			if (applyInfo.getApplyType() != FormChangeApplyTypeEnum.FORM) {
				result.put("success", false);
				result.put("message", "事项签报不支持");
			}
			
			FcsBaseResult exResult = formChangeApplyServiceClient.executeChange(formId);
			if (exResult != null && exResult.isSuccess()) {
				result.put("success", true);
				result.put("message", "执行成功");
			} else {
				result.put("success", false);
				result.put("message", exResult == null ? "执行出错" : exResult.getMessage());
			}
		} catch (Exception e) {
			logger.error("重新执行签报出错");
		}
		return result;
	}
	
	@RequestMapping("save.htm")
	@ResponseBody
	public JSONObject save(FormChangeRecordOrder order, HttpSession session,
							HttpServletRequest request, Model model) {
		JSONObject result = new JSONObject();
		try {
			setSessionLocalInfo2Order(order);
			order.setCheckIndex(0);
			order.setCheckStatus(1);
			
			//			boolean isUnderwriting = false;
			//			if (StringUtil.isNotBlank(order.getProjectCode())) {
			//				ProjectInfo project = projectServiceClient.queryByCode(order.getProjectCode(),
			//					false);
			//				if (project != null)
			//					isUnderwriting = ProjectUtil.isUnderwriting(project.getBusiType());
			//			}
			//			信惠的承销项目或者其他事项签报走信惠流程
			order.setFormCode(FormCodeEnum.FORM_CHANGE_APPLY);
			order.setSessionId(session.getId());
			FormBaseResult saveResult = formChangeApplyServiceClient.saveRecord(order);
			toJSONResult(result, saveResult, "保存签报申请单成功", null);
			if (saveResult != null && saveResult.isSuccess()) {
				addAttachfile(String.valueOf(saveResult.getFormInfo().getFormId()), request,
					order.getProjectCode(), "签报申请单附件", CommonAttachmentTypeEnum.FORM_CHANGE_APPLY);
				//客户资料签报申请成功后更改客户签报状态
				if (order.getChangeForm() == FormChangeApplyEnum.CUSTOMER_DATA)
					customerServiceClient.setChangeStatus(order.getCustomerId(), BooleanEnum.IS);
			}
		} catch (Exception e) {
			logger.error("保存签报申请出错");
		}
		return result;
	}
	
	private FormChangeApplyInfo viewApply(FormInfo formInfo, Model model) {
		
		model.addAttribute("form", formInfo);
		FormChangeApplyInfo applyInfo = formChangeApplyServiceClient.queryByFormId(formInfo
			.getFormId());
		if (applyInfo != null) {
			
			if (applyInfo.getApplyType() == FormChangeApplyTypeEnum.FORM) {
				FormChangeRecordQueryOrder recordOrder = new FormChangeRecordQueryOrder();
				recordOrder.setApplyFormId(formInfo.getFormId());
				recordOrder.setLimitStart(0);
				recordOrder.setPageSize(999);
				recordOrder.setQueryDetail(true);
				QueryBaseBatchResult<FormChangeRecordInfo> records = formChangeApplyServiceClient
					.searchRecord(recordOrder);
				if (records != null) {
					applyInfo.setRecords(records.getPageList());
				}
			}
			
			queryCommonAttachmentData(model, String.valueOf(formInfo.getFormId()),
				CommonAttachmentTypeEnum.FORM_CHANGE_APPLY);
			
			model.addAttribute("apply", applyInfo);
		}
		
		return applyInfo;
	}
}
