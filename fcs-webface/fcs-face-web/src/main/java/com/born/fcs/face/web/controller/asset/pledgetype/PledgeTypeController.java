package com.born.fcs.face.web.controller.asset.pledgetype;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.am.ws.enums.FieldTypeEnum;
import com.born.fcs.am.ws.enums.LatestEntryFormEnum;
import com.born.fcs.am.ws.enums.TimeRangeEnum;
import com.born.fcs.am.ws.info.pledgetext.PledgeTextCustomInfo;
import com.born.fcs.am.ws.info.pledgetype.PledgeTypeInfo;
import com.born.fcs.am.ws.info.pledgetypeattribute.PledgeTypeAttributeTextInfo;
import com.born.fcs.am.ws.order.pledgetype.PledgeTypeOrder;
import com.born.fcs.am.ws.order.pledgetype.PledgeTypeQueryOrder;
import com.born.fcs.am.ws.order.pledgetypeattribute.PledgeTypeAttributeTextQueryOrder;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Controller
@RequestMapping("assetMg/pledgeType")
public class PledgeTypeController extends BaseController {
	
	final static String vm_path = "/assetMg/assetType/";
	
	/**
	 * 抵质押品类型列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(PledgeTypeQueryOrder order, Model model) {
		order.setPageSize(1000);
		QueryBaseBatchResult<PledgeTypeInfo> batchResult = pledgeTypeServiceClient.query(order);
		model.addAttribute("conditions", order);
		//		model.addAttribute("listPledgeTypeInfo", listPledgeTypeInfo);
		//是否系统管理员
		boolean isAdmin = DataPermissionUtil.isSystemAdministrator();
		model.addAttribute("isAdmin", isAdmin);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		
		return vm_path + "assetTypeList.vm";
	}
	
	/**
	 * 去新增抵质押品类型
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("toAdd.htm")
	public String toAdd(HttpServletRequest request, HttpServletResponse response, String levelOne,
						String levelTwo, Model model) {
		List<PledgeTextCustomInfo> textInfoCommonList = pledgeTextCustomServiceClient
			.findByTypeId(-1);
		
		model.addAttribute("fieldType", FieldTypeEnum.getAllEnum());//字段类型
		
		List<FieldTypeEnum> fieldType = new ArrayList<FieldTypeEnum>();
		fieldType.add(FieldTypeEnum.DATE);
		fieldType.add(FieldTypeEnum.NUMBER);
		fieldType.add(FieldTypeEnum.SELECT);
		fieldType.add(FieldTypeEnum.TEXT);
		fieldType.add(FieldTypeEnum.ADMINISTRATIVE_PLAN);
		
		model.addAttribute("fieldType1", fieldType);//字段类型
		List<FieldTypeEnum> fieldType2 = new ArrayList<FieldTypeEnum>();
		fieldType2.add(FieldTypeEnum.NUMBER);
		fieldType2.add(FieldTypeEnum.TEXT);
		model.addAttribute("fieldType2", fieldType2);//字段类型
		model.addAttribute("textInfoCommonList", textInfoCommonList);
		
		List<PledgeTypeInfo> listInfo = new ArrayList<PledgeTypeInfo>();
		listInfo = pledgeTypeServiceClient.findByLevelOneAndLevelTwo(levelOne, levelTwo);
		model.addAttribute("listInfo", listInfo);
		return vm_path + "assetTypeAdd.vm";
	}
	
	/**
	 * 新增抵质押品类型(选择分类信息后加载数据)
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("add.htm")
	@ResponseBody
	public JSONObject add(String levelOne, String levelTwo, Model model) {
		String tipPrefix = " 加载抵押质类型";
		JSONObject jsonObject = new JSONObject();
		//		JSONArray dataList = new JSONArray();
		try {
			List<PledgeTypeInfo> listInfo = new ArrayList<PledgeTypeInfo>();
			listInfo = pledgeTypeServiceClient.findByLevelOneAndLevelTwo(levelOne, levelTwo);
			
			jsonObject.put("listInfo", listInfo);
		} catch (Exception e) {
			jsonObject = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		return jsonObject;
	}
	
	/**
	 * 保存抵押质类型
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("save.htm")
	@ResponseBody
	public JSONObject save(PledgeTypeOrder order, Model model) {
		String tipPrefix = " 保存抵押质类型";
		JSONObject result = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				result.put("success", false);
				result.put("message", "您未登陆或登陆已失效");
				return result;
			}
			setSessionLocalInfo2Order(order);
			FcsBaseResult saveResult = pledgeTypeServiceClient.save(order);
			
			if (saveResult.isSuccess()) {
				result.put("success", true);
				result.put("message", "保存成功");
			} else {
				result.put("success", false);
				result.put("message", saveResult.getMessage());
			}
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
	}
	
	/**
	 * 编辑抵押质类型
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("edit.htm")
	public String edit(Long id, Model model) {
		String tipPrefix = "编辑抵押质类型";
		try {
			PledgeTypeInfo info = pledgeTypeServiceClient.findById(id);
			List<PledgeTextCustomInfo> textInfoList = pledgeTextCustomServiceClient
				.findByTypeId(info.getTypeId());
			
			model.addAttribute("fieldType", FieldTypeEnum.getAllEnum());//字段类型
			
			List<FieldTypeEnum> fieldType = new ArrayList<FieldTypeEnum>();
			fieldType.add(FieldTypeEnum.DATE);
			fieldType.add(FieldTypeEnum.NUMBER);
			fieldType.add(FieldTypeEnum.SELECT);
			fieldType.add(FieldTypeEnum.TEXT);
			fieldType.add(FieldTypeEnum.ADMINISTRATIVE_PLAN);
			
			model.addAttribute("fieldType1", fieldType);//字段类型
			List<FieldTypeEnum> fieldType2 = new ArrayList<FieldTypeEnum>();
			fieldType2.add(FieldTypeEnum.NUMBER);
			fieldType2.add(FieldTypeEnum.TEXT);
			model.addAttribute("fieldType2", fieldType2);//字段类型
			model.addAttribute("textInfoList", textInfoList);
			//判断该类型是否被使用
			PledgeTypeAttributeTextQueryOrder textOrder = new PledgeTypeAttributeTextQueryOrder();
			textOrder.setTypeId(info.getTypeId());
			QueryBaseBatchResult<PledgeTypeAttributeTextInfo> result = pledgeTypeAttributeServiceClient
				.queryAttributeText(textOrder);
			if (result.getPageList().size() > 0) {
				model.addAttribute("isUsed", "true");
			} else {
				model.addAttribute("isUsed", "false");
			}
			model.addAttribute("imageInfoList",
				pledgeImageCustomServiceClient.findByTypeId(info.getTypeId()));
			model.addAttribute("networkInfoList",
				pledgeNetworkCustomServiceClient.findByTypeId(info.getTypeId()));
			
			model.addAttribute("timeRange", TimeRangeEnum.getAllEnum());//时间选择范围
			model.addAttribute("latestEntryForm", LatestEntryFormEnum.getAllEnum());//最迟录入表单
			model.addAttribute("isEdit", "true");//是否编辑
			model.addAttribute("info", info);
			
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		
		return vm_path + "assetTypeAdd.vm";
	}
	
	/**
	 * 删除抵押质类型
	 *
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping("delete.htm")
	@ResponseBody
	public JSONObject delete(Long id, String level, Model model) {
		JSONObject result = new JSONObject();
		if (id == null) {
			result.put("success", false);
			result.put("message", "抵押质类型不存在");
		} else {
			Boolean isCheck = pledgeTypeServiceClient.deleteCheck(id, level);//level="one" "two" "three"
			if (isCheck) {
				result.put("success", true);
				result.put("message", "删除成功");
			} else {
				result.put("success", false);
				result.put("message", "改类型已被使用,删除失败");
			}
		}
		return result;
	}
	
	/**
	 * 新增编辑时检查是否重名
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("checkName.htm")
	@ResponseBody
	public JSONObject checkName(String levelOne, Boolean isAddlevelOne, String levelTwo,
								Boolean isAddlevelTwo, String levelThree, Boolean isAddlevelThree,
								Model model) {
		String tipPrefix = " 抵押质类型是否重名";
		JSONObject result = new JSONObject();
		try {
			if (isAddlevelOne == null) {
				isAddlevelOne = false;
			}
			if (isAddlevelTwo == null) {
				isAddlevelTwo = false;
			}
			if (isAddlevelThree == null) {
				isAddlevelThree = false;
			}
			if (isAddlevelOne) {
				isAddlevelTwo = false;
				isAddlevelThree = false;
			}
			if (isAddlevelTwo) {
				isAddlevelOne = false;
				isAddlevelThree = false;
			}
			if (isAddlevelThree) {
				isAddlevelOne = false;
				isAddlevelTwo = false;
			}
			Boolean isSameName = pledgeTypeServiceClient.isSameNameCheck(levelOne, isAddlevelOne,
				levelTwo, isAddlevelTwo, levelThree, isAddlevelThree);
			if (!isSameName) {
				result.put("success", false);
				result.put("message", "抵押质类型名称已存在");
			} else {
				result.put("success", true);
				result.put("message", "抵押质类型名称可用");
			}
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
	}
}
