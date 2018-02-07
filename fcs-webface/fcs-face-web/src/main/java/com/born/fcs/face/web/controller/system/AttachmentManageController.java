package com.born.fcs.face.web.controller.system;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.info.common.CommonAttachmentInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.order.common.CommonAttachmentOrder;
import com.born.fcs.pm.ws.order.common.CommonAttachmentQueryOrder;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 附件管理controller
 * @author ji
 *
 */
@Controller
@RequestMapping("systemMg/attachmentManage")
public class AttachmentManageController extends BaseController {
	
	private final String vm_path = "/systemMg/attachmentManage/";
	
	@RequestMapping("list.htm")
	public String list(ProjectQueryOrder order, HttpServletRequest request, Model model) {
		
		setSessionLocalInfo2Order(order);
		
		model.addAttribute("queryOrder", order);
		
		//		//是否系统管理员
		//		boolean isAdmin = DataPermissionUtil.isSystemAdministrator();
		//		model.addAttribute("isAdmin", isAdmin);
		
		if (StringUtil.isBlank(order.getSortCol())) {
			order.setSortCol("project_id");
			order.setSortOrder("DESC");
		}
		
		QueryBaseBatchResult<ProjectInfo> pList = projectServiceClient.queryProject(order);
		model.addAttribute("page", PageUtil.getCovertPage(pList));
		
		return vm_path + "list.vm";
	}
	
	/**
	 * 项目附件详情列表
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("viewList.htm")
	public String viewList(CommonAttachmentQueryOrder order, Model model) {
		QueryBaseBatchResult<CommonAttachmentInfo> pList = commonAttachmentServiceClient
			.queryPage(order);
		model.addAttribute("page", PageUtil.getCovertPage(pList));
		model.addAttribute("queryOrder", order);
		return vm_path + "viewList.vm";
	}
	
	/**
	 * 删除附件
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("delete.htm")
	@ResponseBody
	public JSONObject delete(Long id, String moduleType, String bizNo, Model model) {
		JSONObject result = new JSONObject();
		if (id == null || id <= 0) {
			result.put("success", false);
			result.put("message", "删除失败，附件不存在");
		} else {
			commonAttachmentServiceClient.deleteAttachment(id, bizNo, moduleType);
			result.put("success", true);
			result.put("message", "删除成功");
			
		}
		return result;
	}
	
	/**
	 * 重新上传
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("reUpload.htm")
	@ResponseBody
	public JSONObject reUpload(CommonAttachmentOrder order, String file, Model model) {
		JSONObject result = new JSONObject();
		if (order.getAttachmentId() <= 0) {
			result.put("success", false);
			result.put("message", "重新上传失败，附件不存在");
		} else {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				result.put("success", false);
				result.put("message", "您未登陆或登陆已失效");
				
			} else {
				order.setUploaderId(sessionLocal.getUserId());
				order.setUploaderName(sessionLocal.getRealName());
				order.setUploaderAccount(sessionLocal.getUserName());
			}
			commonAttachmentServiceClient.updateAttachment(order);
			result.put("success", true);
			result.put("message", "重新上传成功");
			
		}
		return result;
	}
}
