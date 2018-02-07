package com.born.fcs.face.web.controller.project.virtual;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Maps;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.VirtualCustomerEnum;
import com.born.fcs.pm.ws.enums.VirtualProjectStatusEnum;
import com.born.fcs.pm.ws.info.virtualproject.VirtualProjectDetailInfo;
import com.born.fcs.pm.ws.info.virtualproject.VirtualProjectInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.virtualproject.VirtualProjectDeleteOrder;
import com.born.fcs.pm.ws.order.virtualproject.VirtualProjectOrder;
import com.born.fcs.pm.ws.order.virtualproject.VirtualProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 项目移交
 *
 * @author wuzj
 */
@Controller
@RequestMapping("projectMg/virtual")
public class VirtualProjectController extends WorkflowBaseController {
	
	final static String vm_path = "/projectMg/virtual/";
	
	@RequestMapping("form.htm")
	public String form(long virtualId, HttpServletRequest request, Model model) {
		if (virtualId > 0) {
			view(virtualId, model);
		}
		selectProject(null, request, model);
		model.addAttribute("virtualCustomer", VirtualCustomerEnum.getAllEnum());
		return vm_path + "apply.vm";
	}
	
	@RequestMapping("view.htm")
	public String view(long virtualId, HttpServletRequest request, Model model) {
		view(virtualId, model);
		model.addAttribute("virtualCustomer", VirtualCustomerEnum.getAllEnum());
		return vm_path + "viewApply.vm";
	}
	
	private void view(long virtualId, Model model) {
		VirtualProjectInfo virtual = virtualProjectServiceClient.findById(virtualId);
		model.addAttribute("virtual", virtual);
		if (virtual != null) {
			model.addAttribute("detailList", virtual.getDetailList());
			if (virtual.getDetailList() != null) {
				Map<String, String> detailMap = Maps.newHashMap();
				for (VirtualProjectDetailInfo detail : virtual.getDetailList()) {
					detailMap.put(detail.getProjectCode(), detail.getProjectCode());
				}
				model.addAttribute("detailMap", detailMap);
			}
		}
	}
	
	@RequestMapping("list.htm")
	public String list(VirtualProjectQueryOrder order, HttpServletRequest request, Model model) {
		if (order == null)
			order = new VirtualProjectQueryOrder();
		
		if (StringUtil.isBlank(order.getSortCol())) {
			order.setSortCol("virtual_id");
			order.setSortOrder("desc");
		}
		setSessionLocalInfo2Order(order);
		model.addAttribute("statusList", VirtualProjectStatusEnum.getAllEnum());
		model.addAttribute("queryOrder", order);
		model
			.addAttribute("page", PageUtil.getCovertPage(virtualProjectServiceClient.query(order)));
		
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
	public JSONObject save(VirtualProjectOrder order, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			setSessionLocalInfo2Order(order);
			if (sessionLocal.getUserDetailInfo() != null
				&& sessionLocal.getUserDetailInfo().getPrimaryOrg() != null) {
				order.setApplyDeptId(sessionLocal.getUserDetailInfo().getPrimaryOrg().getId());
				order.setApplyDeptName(sessionLocal.getUserDetailInfo().getPrimaryOrg().getName());
			}
			FcsBaseResult result = virtualProjectServiceClient.save(order);
			jsonObject = toJSONResult(jsonObject, result,
				order.getIsSubmit() == BooleanEnum.IS ? "提交成功" : "保存成功", null);
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", order.getIsSubmit() == BooleanEnum.IS ? "提交异常" : "保存异常");
			logger.error(order.getIsSubmit() == BooleanEnum.IS ? "提交异常{}" : "保存异常{}", e);
		}
		return jsonObject;
	}
	
	/**
	 * 保存移交申请
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping("delete.json")
	@ResponseBody
	public JSONObject delete(VirtualProjectDeleteOrder order, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			setSessionLocalInfo2Order(order);
			FcsBaseResult result = virtualProjectServiceClient.delete(order);
			jsonObject = toJSONResult(jsonObject, result, "删除虚拟项目成功", null);
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", "删除虚拟项目异常");
			logger.error("删除虚拟项目异常 {}", e);
		}
		return jsonObject;
	}
	
	@RequestMapping("selectProject.json")
	public String selectProject(ProjectQueryOrder queryOrder, HttpServletRequest request,
								Model model) {
		try {
			if (queryOrder == null)
				queryOrder = new ProjectQueryOrder();
			
			queryOrder.setPageSize(5);
			setSessionLocalInfo2Order(queryOrder);
			queryOrder.setHasContract(BooleanEnum.YES);
			queryOrder.setSortCol("project_id");
			queryOrder.setSortOrder("DESC");
			model.addAttribute("queryOrder", queryOrder);
			model.addAttribute("page",
				PageUtil.getCovertPage(projectServiceClient.queryProject(queryOrder)));
		} catch (Exception e) {
			logger.error("查询项目信息出错{}", e);
		}
		return vm_path + "selectProject.vm";
	}
}
