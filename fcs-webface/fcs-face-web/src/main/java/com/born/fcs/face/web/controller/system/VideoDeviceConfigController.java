package com.born.fcs.face.web.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.am.ws.info.pledgeasset.PledgeAssetInfo;
import com.born.fcs.am.ws.order.pledgeasset.PledgeAssetQueryOrder;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 视频监控设备配置controller
 * @author ji
 *
 */
@Controller
@RequestMapping("systemMg/videoDevice")
public class VideoDeviceConfigController extends BaseController {
	
	private final String vm_path = "/assetMg/assetVideo/";
	
	@RequestMapping("list.htm")
	public String list(PledgeAssetQueryOrder order, Model model) {
		if (StringUtil.isBlank(order.getSortCol())) {
			order.setSortCol("assets_id");
			order.setSortOrder("DESC");
		}
		order.setRalateVideo("IS");//查询有视频监控设备配置的资产
		QueryBaseBatchResult<PledgeAssetInfo> batchResult = pledgeAssetServiceClient.query(order);
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		return vm_path + "assetVideoList.vm";
	}
	
	/**
	 * 去分配
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("toAdd.htm")
	public String toAdd(Model model) {
		return vm_path + "assetVideo.vm";
	}
	
	/**
	 * 分配
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("edit.htm")
	public String edit(Long id, Model model) {
		if (id == null || id <= 0) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资产ID有误");
		} else {
			PledgeAssetInfo info = pledgeAssetServiceClient.findById(id);
			
			if (info == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资产信息不存在");
			}
			model.addAttribute("isEdit", "true");
			model.addAttribute("info", info);
		}
		return vm_path + "assetVideo.vm";
	}
	
	/**
	 * 分配详情
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("view.htm")
	public String view(Long id, Model model) {
		if (id == null || id <= 0) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资产ID有误");
		} else {
			PledgeAssetInfo info = pledgeAssetServiceClient.findById(id);
			
			if (info == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资产信息不存在");
			}
			model.addAttribute("info", info);
		}
		return vm_path + "assetVideoView.vm";
	}
	
	/**
	 * 关联
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("related.htm")
	@ResponseBody
	public JSONObject related(Long id, String ralateVideo, Model model) {
		JSONObject result = new JSONObject();
		if (id == null || id <= 0) {
			result.put("success", false);
			result.put("message", "关联失败，资产不存在");
		} else {
			pledgeAssetServiceClient.update(id, ralateVideo);
			result.put("success", true);
			result.put("message", "关联成功");
			
		}
		return result;
	}
}
