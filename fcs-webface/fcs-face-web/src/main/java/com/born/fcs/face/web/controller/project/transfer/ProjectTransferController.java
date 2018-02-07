package com.born.fcs.face.web.controller.project.transfer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.ProjectTransferStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectTransferTypeEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.transfer.ProjectTransferDetailInfo;
import com.born.fcs.pm.ws.order.transfer.ProjectTransferAcceptOrder;
import com.born.fcs.pm.ws.order.transfer.ProjectTransferDetailQueryOrder;
import com.born.fcs.pm.ws.order.transfer.ProjectTransferOrder;
import com.born.fcs.pm.ws.order.transfer.ProjectTransferSelectOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;

/**
 * 项目移交
 *
 * @author wuzj
 */
@Controller
@RequestMapping("projectMg/transfer")
public class ProjectTransferController extends WorkflowBaseController {
	
	final static String vm_path = "/projectMg/transfer/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "transferTimeStart", "transferTimeEnd" };
	}
	
	@RequestMapping("edit.htm")
	public String edit(long formId, HttpServletRequest request, Model model) {
		if (formId > 0) {
			view(formId, model);
		}
		selectProject(null, request, model);
		return vm_path + "apply.vm";
	}
	
	@RequestMapping("audit.htm")
	public String audit(long formId, HttpServletRequest request, Model model) {
		if (StringUtil.equalsIgnoreCase("YES", request.getParameter("setAccept"))) {
			model.addAttribute("setAccept", true);
			model.addAttribute("legalDeptCode", sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_FW_DEPT_CODE.code()));
		}
		FormInfo form = view(formId, model);
		initWorkflow(model, form, request.getParameter("taskId"));
		return vm_path + "viewApply.vm";
	}
	
	@RequestMapping("view.htm")
	public String view(long formId, HttpServletRequest request, Model model) {
		view(formId, model);
		return vm_path + "viewApply.vm";
	}
	
	private FormInfo view(long formId, Model model) {
		FormInfo form = formServiceClient.findByFormId(formId);
		//		if (form == null) {
		//			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		//		}
		model.addAttribute("form", form);
		List<ProjectTransferDetailInfo> details = projectTransferServiceClient
			.queryByFormId(formId);
		model.addAttribute("transferDetails", details);
		return form;
	}
	
	@RequestMapping("list.htm")
	public String list(ProjectTransferDetailQueryOrder order, HttpServletRequest request,
						Model model) {
		if (order == null)
			order = new ProjectTransferDetailQueryOrder();
		
		if (StringUtil.isBlank(order.getSortCol())) {
			order.setSortCol("f.form_id");
			order.setSortOrder("desc");
		}
		
		if (order.getTransferTimeEnd() != null) {
			order.setTransferTimeEnd(DateUtil.getEndTimeOfTheDate(order.getTransferTimeEnd()));
		}
		
		setSessionLocalInfo2Order(order);
		model.addAttribute("transferStatus", ProjectTransferStatusEnum.getAllEnum());
		model.addAttribute("transferType", ProjectTransferTypeEnum.getAllEnum());
		model.addAttribute("queryOrder", order);
		model.addAttribute("page",
			PageUtil.getCovertPage(projectTransferServiceClient.queryDetail(order)));
		
		return vm_path + "list.vm";
	}
	
	/**
	 * 保存移交申请
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping("save.json")
	@ResponseBody
	public JSONObject save(ProjectTransferOrder order, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			order.setFormCode(FormCodeEnum.PROJECT_TRANSFER);
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			FormBaseResult result = projectTransferServiceClient.saveTransferApply(order);
			jsonObject = toJSONResult(jsonObject, result, "保存移交申请", null);
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存移交申请出错{}", e);
		}
		return jsonObject;
	}
	
	/**
	 * 设置接收人员
	 * @param order
	 * @return
	 */
	@RequestMapping("setAcceptUser.json")
	@ResponseBody
	public JSONObject setAcceptUser(ProjectTransferAcceptOrder order, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			setSessionLocalInfo2Order(order);
			FcsBaseResult result = projectTransferServiceClient.setAcceptUser(order);
			jsonObject = toJSONResult(jsonObject, result, "设置接收人员", null);
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("设置接收人员出错{}", e);
		}
		return jsonObject;
	}
	
	@RequestMapping("selectProject.json")
	public String selectProject(ProjectTransferSelectOrder queryOrder, HttpServletRequest request,
								Model model) {
		try {
			if (queryOrder == null)
				queryOrder = new ProjectTransferSelectOrder();
			
			//不包含的项目，去重
			String execudeProjects = request.getParameter("execudeProjects");
			model.addAttribute("execudeProjects", execudeProjects);
			if (StringUtil.isNotEmpty(execudeProjects)) {
				Set<String> excludeProjectSet = new HashSet<String>();
				String[] excludeProjectArr = execudeProjects.split(",");
				for (String excludeProject : excludeProjectArr) {
					if (StringUtil.isNotBlank(excludeProject)) {
						excludeProjectSet.add(excludeProject);
					}
				}
				if (!excludeProjectSet.isEmpty()) {
					List<String> exculdeProjectList = Lists.newArrayList();
					for (String excludeProject : excludeProjectSet) {
						exculdeProjectList.add(excludeProject);
					}
					queryOrder.setExcludeProjects(exculdeProjectList);
				}
			}
			queryOrder.setPageSize(5);
			setSessionLocalInfo2Order(queryOrder);
			queryOrder.setSortCol("p.raw_update_time");
			queryOrder.setSortOrder("DESC");
			model.addAttribute("queryOrder", queryOrder);
			model.addAttribute("page",
				PageUtil.getCovertPage(projectTransferServiceClient.selectProject(queryOrder)));
		} catch (Exception e) {
			logger.error("查询可移交项目信息出错{}", e);
		}
		return vm_path + "selectProject.vm";
	}
}
