package com.born.fcs.face.web.controller.project.beforeloan.investigation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationAssetReviewInfo;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationAssetReviewQueryOrder;
import com.born.fcs.pm.ws.order.finvestigation.UpdateInvestigationAssetReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.UpdateInvestigationCreditSchemePledgeAssetRemarkOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * 资产复评
 * 
 * @author lirz
 * 
 * 2016-9-19 下午6:25:22
 */
@Controller
@RequestMapping("projectMg/assetReview")
public class AssetReviewController extends BaseController {
	
	private static final String VM_PATH = "/projectMg/beforeLoanMg/assetReview/";
	
	/**
	 * 列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model) {
		
		try {
			FInvestigationAssetReviewQueryOrder queryOrder = new FInvestigationAssetReviewQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<FInvestigationAssetReviewInfo> batchResult = assetReviewServiceClient
				.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错");
		}
		
		return VM_PATH + "list.vm";
	}
	
	@RequestMapping("edit.htm")
	public String edit(HttpServletRequest request, Model model, long id) {
		FInvestigationAssetReviewInfo info = assetReviewServiceClient.findById(id);
		model.addAttribute("info", info);
		return VM_PATH + "editAssetReview.vm";
	}
	
	@ResponseBody
	@RequestMapping("submit.json")
	public Object submit(HttpSession session, HttpServletRequest request,
	                     UpdateInvestigationAssetReviewOrder order) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		
		String tipPrefix = "提交资产复评";
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal != null) {
				order.setReviewerId(sessionLocal.getUserId());
				order.setReviewerAccount(sessionLocal.getUserName());
				order.setReviewerName(sessionLocal.getRealName());
			}
			FcsBaseResult result = assetReviewServiceClient.submit(order);
			json = toJSONResult(result, tipPrefix);
			
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	@RequestMapping("view.htm")
	public String view(HttpServletRequest request, Model model, long id) {
		FInvestigationAssetReviewInfo info = assetReviewServiceClient.findById(id);
		model.addAttribute("info", info);
		return VM_PATH + "viewAssetReview.vm";
	}
	
	@RequestMapping("editRemark.htm")
	public String editRemark(HttpServletRequest request, Model model, long id) {
		FInvestigationAssetReviewInfo info = assetReviewServiceClient.findById(id);
		model.addAttribute("info", info);
		return VM_PATH + "editRemark.vm";
	}
	
	@ResponseBody
	@RequestMapping("saveRemark.json")
	public Object saveRemark(HttpSession session, HttpServletRequest request,
								UpdateInvestigationCreditSchemePledgeAssetRemarkOrder order) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		
		String tipPrefix = "保存复评意见";
		try {
			FcsBaseResult result = assetReviewServiceClient.saveRemark(order);
			json = toJSONResult(result, tipPrefix);
			
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	@RequestMapping("viewRemark.htm")
	public String viewRemark(HttpServletRequest request, Model model, long id) {
		FInvestigationAssetReviewInfo info = assetReviewServiceClient.findById(id);
		model.addAttribute("info", info);
		return VM_PATH + "editRemark.vm";
	}
	
}
