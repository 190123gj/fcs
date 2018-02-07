package com.born.fcs.face.web.controller.project.notice;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.born.fcs.face.web.util.DataPermissionUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.EscapeUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.info.notice.ConsentIssueNoticeInfo;
import com.born.fcs.pm.ws.order.notice.ConsentIssueNoticeOrder;
import com.born.fcs.pm.ws.order.notice.ConsentIssueNoticeQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Controller
@RequestMapping("projectMg/consentIssueNotice")
public class ConsentIssueNoticeController extends BaseController {
	
	final static String vm_path = "/projectMg/beforeLoanMg/notice/";
	
	/**
	 * 同意发行通知书列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("noticeList.htm")
	public String noticeList(ConsentIssueNoticeQueryOrder order, Model model) {
		setSessionLocalInfo2Order(order);
		QueryBaseBatchResult<ConsentIssueNoticeInfo> batchResult = consentIssueNoticeServiceClient
			.query(order);
		model.addAttribute("conditions", order);
		
		model.addAttribute("isBusiManager", isBusiManager());
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		
		return vm_path + "bondNoticeList.vm";
	}
	
	/**
	 * 去新增同意发行通知书
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("toAddNotice.htm")
	public String toAddNotice(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("date", new Date());
		model.addAttribute("isBusiManager", isBusiManager());
		return vm_path + "bondNoticeAdd.vm";
	}
	
	/**
	 * 新增同意发行通知书
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("addNotice.htm")
	public String addNotice(String projectCode, Model model) {
		ProjectSimpleDetailInfo projectInfo = projectServiceClient
			.querySimpleDetailInfo(projectCode);
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("date", new Date());
		model.addAttribute("isBusiManager", isBusiManager());
		return vm_path + "bondNoticeAdd.vm";
	}
	
	/**
	 * 保存同意发行通知书信息
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveNotice.htm")
	@ResponseBody
	public JSONObject saveNotice(HttpServletRequest request, HttpServletResponse response,
									ConsentIssueNoticeOrder order, Model model) {
		String tipPrefix = " 保存同意发行通知书信息";
		JSONObject result = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				result.put("success", false);
				result.put("message", "您未登陆或登陆已失效");
				return result;
			}
			order.setBusiManagerId(sessionLocal.getUserId());
			order.setBusiManagerAccount(sessionLocal.getUserName());
			order.setBusiManagerName(sessionLocal.getRealName());
			order.setUserId(sessionLocal.getUserId());
			order.setUserName(sessionLocal.getRealName());
			order.setUserAccount(sessionLocal.getUserName());
			setSessionLocalInfo2Order(order);
			// 模版内容转码
			String html = order.getHtml();
			if (StringUtil.isNotBlank(html)) {
				String STR2 = StringEscapeUtils.unescapeHtml(EscapeUtil.unescape(html));
				order.setHtml(STR2);
			}
			FcsBaseResult saveResult = null;
			ProjectSimpleDetailInfo projectInfo = projectServiceClient.querySimpleDetailInfo(order
				.getProjectCode());
			if (projectInfo.getStatus() == ProjectStatusEnum.PAUSE) {
				result.put("success", false);
				result.put("message", "项目暂缓，不能提交");
			} else {
				saveResult = consentIssueNoticeServiceClient.save(order);
				if (saveResult.isSuccess()) {
					result.put("id", saveResult.getKeyId());
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
	 * 查看同意发行通知书详情
	 *
	 * @param companyId
	 * @param model
	 * @return
	 */
	@RequestMapping("viewNotice.htm")
	public String viewNotice(Long id, Long stampFormId, Model model) {
		String tipPrefix = "查看决策机构详情";
		try {
			ConsentIssueNoticeInfo consentIssueNoticeInfo = consentIssueNoticeServiceClient
				.findById(id);
			
			model.addAttribute("consentIssueNoticeInfo", consentIssueNoticeInfo);
			model.addAttribute("isBusiManager", isBusiManager());
			model.addAttribute("isCanPrint", DataPermissionUtil.isCanPrint(stampFormId));
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		
		return vm_path + "bondNoticeView.vm";
	}
	
	/**
	 * 编辑同意发行通知书
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("editNotice.htm")
	public String editNotice(Long id, Model model) {
		String tipPrefix = "编辑决策机构详情";
		try {
			ConsentIssueNoticeInfo consentIssueNoticeInfo = consentIssueNoticeServiceClient
				.findById(id);
			model.addAttribute("consentIssueNoticeInfo", consentIssueNoticeInfo);
			model.addAttribute("isBusiManager", isBusiManager());
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		
		return vm_path + "bondNoticeAdd.vm";
	}
	
	/**
	 * 删除同意发行通知书
	 *
	 * @param noticeId
	 * @param model
	 * @return
	 */
	@RequestMapping("deleteNotice.htm")
	@ResponseBody
	public JSONObject deleteNotice(Long noticeId, Model model) {
		JSONObject result = new JSONObject();
		int num = consentIssueNoticeServiceClient.deleteById(noticeId);
		if (num > 0) {
			result.put("success", true);
			result.put("message", "删除成功");
		} else {
			result.put("false", false);
			result.put("message", "删除失败");
		}
		return result;
	}
	
	/**
	 * 上传回执
	 *
	 * @param noticeId
	 * @param model
	 * @return
	 */
	@RequestMapping("uploadReceipt.json")
	@ResponseBody
	public JSONObject uploadReceipt(ConsentIssueNoticeOrder order, Model model) {
		String tipPrefix = "上传回执";
		JSONObject json = new JSONObject();
		try {
			FcsBaseResult result = consentIssueNoticeServiceClient.uploadReceipt(order);
			json = toJSONResult(result, tipPrefix);
			return json;
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		
		return json;
	}
}
