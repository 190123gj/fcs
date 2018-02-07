package com.born.fcs.face.web.controller.asset;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.am.ws.enums.AssetRemarkInfoEnum;
import com.born.fcs.am.ws.enums.AssetStatusEnum;
import com.born.fcs.am.ws.enums.FieldTypeEnum;
import com.born.fcs.am.ws.enums.LatestEntryFormEnum;
import com.born.fcs.am.ws.enums.TimeRangeEnum;
import com.born.fcs.am.ws.info.pledgeasset.AssetRelationProjectInfo;
import com.born.fcs.am.ws.info.pledgeasset.PledgeAssetInfo;
import com.born.fcs.am.ws.info.pledgeimage.PledgeImageCustomInfo;
import com.born.fcs.am.ws.info.pledgenetwork.PledgeNetworkCustomInfo;
import com.born.fcs.am.ws.info.pledgetext.PledgeTextCustomInfo;
import com.born.fcs.am.ws.info.pledgetype.PledgeTypeInfo;
import com.born.fcs.am.ws.info.pledgetypeattribute.PledgeTypeAttributeImageInfo;
import com.born.fcs.am.ws.info.pledgetypeattribute.PledgeTypeAttributeNetworkInfo;
import com.born.fcs.am.ws.info.pledgetypeattribute.PledgeTypeAttributeTextInfo;
import com.born.fcs.am.ws.order.pledgeasset.AssetRelationProjectQueryOrder;
import com.born.fcs.am.ws.order.pledgeasset.PledgeAssetOrder;
import com.born.fcs.am.ws.order.pledgeasset.PledgeAssetQueryOrder;
import com.born.fcs.am.ws.order.pledgeimage.PledgeImageCustomQueryOrder;
import com.born.fcs.am.ws.order.pledgetext.PledgeTextCustomQueryOrder;
import com.born.fcs.am.ws.order.pledgetypeattribute.PledgeTypeAttributeImageQueryOrder;
import com.born.fcs.am.ws.order.pledgetypeattribute.PledgeTypeAttributeNetworkQueryOrder;
import com.born.fcs.am.ws.order.pledgetypeattribute.PledgeTypeAttributeOrder;
import com.born.fcs.am.ws.order.pledgetypeattribute.PledgeTypeAttributeTextQueryOrder;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.ExcelData;
import com.born.fcs.face.web.util.ExcelUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.RegionInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCreditSchemePledgeAssetInfo;
import com.born.fcs.pm.ws.info.finvestigation.InvestigationInfo;
import com.born.fcs.pm.ws.order.common.RegionQueryOrder;
import com.born.fcs.pm.ws.order.finvestigation.InvestigationQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.google.common.collect.Lists;
import com.yjf.common.lang.util.ListUtil;

@Controller
@RequestMapping("assetMg")
public class AssetHomeController extends BaseController {
	final static String vm_path = "/assetMg/";
	
	@RequestMapping("index.htm")
	public String mainIndex(HttpServletRequest request, HttpServletResponse response, Model model) {
		buildSystemNameDefaultUrl(request, model);
		return vm_path + "index.vm";
	}
	
	@RequestMapping("list.htm")
	public String list(PledgeAssetQueryOrder order, String customerId, Model model) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		model.addAttribute("queryOrder", order);
		model.addAttribute("isBusiManager", isBusiManager() || isLegalManager());
		model.addAttribute("isBusiDirector", DataPermissionUtil.isBusiFZR());
		
		if (StringUtil.isBlank(order.getSortCol())) {
			order.setSortCol("assets_id");
			order.setSortOrder("DESC");
		}
		
		boolean isRiskSecretary = DataPermissionUtil.isRiskSecretary();
		model.addAttribute("isRiskSecretary", isRiskSecretary);
		if (!DataPermissionUtil.isSystemAdministrator() && !DataPermissionUtil.isCompanyLeader()
			&& !isRiskSecretary) { //系统管理员
			order.setUserId(sessionLocal.getUserId());
			order.setUserAccount(sessionLocal.getUserName());
			order.setUserName(sessionLocal.getRealName());
		}
		if (customerId != null && !"".equals(customerId)) {
			List<Long> assetsIdList = Lists.newArrayList();
			List<AssetRelationProjectInfo> listRelationInfo = pledgeAssetServiceClient
				.findRelationByCustomerId(NumberUtil.parseLong(customerId));
			if (ListUtil.isNotEmpty(listRelationInfo)) {
				for (AssetRelationProjectInfo assetRelationProjectInfo : listRelationInfo) {
					assetsIdList.add(assetRelationProjectInfo.getAssetsId());
				}
			}
			order.setAssetsIdList(assetsIdList);
		}
		QueryBaseBatchResult<PledgeAssetInfo> batchResult = pledgeAssetServiceClient.query(order);
		List<PledgeAssetInfo> info = batchResult.getPageList();
		for (PledgeAssetInfo pledgeAssetInfo : info) {
			Boolean isEdit = isEditForInvestigation(pledgeAssetInfo);
			pledgeAssetInfo.setLongitude(isEdit + "");
			if (pledgeAssetInfo.getProjectCode() != null) {
				String[] projectCodeStr = pledgeAssetInfo.getProjectCode().split(",");
				String isHas = "NO";
				for (int i = 0; i < projectCodeStr.length; i++) {
					String string = projectCodeStr[i];
					isHas = isHaveApproval(string);
					if ("IS".equals(isHas)) {
						break;
					}
					
				}
				pledgeAssetInfo.setIsHasApproval(isHas);
			}
			String type = pledgeAssetInfo.getAssetType();
			pledgeAssetInfo.setAssetType(type.substring(type.lastIndexOf('-') + 1, type.length()));
		}
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		
		return vm_path + "asset/assetList.vm";
	}
	
	/**
	 * 去新增资产(加载所有的一级分类)
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("toAdd.htm")
	public String toAdd(HttpServletRequest request, HttpServletResponse response, String levelOne,
						String id, String isEdit, String isView, String isCopy, String levelTwo,
						Model model) {
		List<PledgeTypeInfo> listInfo = new ArrayList<PledgeTypeInfo>();
		listInfo = pledgeTypeServiceClient.findByLevelOneAndLevelTwoForAssets(levelOne, levelTwo);
		model.addAttribute("listInfo", listInfo);
		if (id == null) {
			id = "0";
		}
		//		isInvestigation = BooleanEnum.YES.code();
		//		model.addAttribute("isInvestigation", isInvestigation);//是否从尽职调查报告那边过来
		model.addAttribute("itype", request.getParameter("itype"));//尽调过来的
		model.addAttribute("iFormId", request.getParameter("iFormId"));//尽调过来的
		model.addAttribute("id", NumberUtil.parseLong(id));
		model.addAttribute("isEdit", isEdit);
		model.addAttribute("isView", isView);
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null
			&& "true".equals(isView)
			&& (request.getParameter("iFormId") == null || "".equals(request
				.getParameter("iFormId")))) {//非尽调过来的
			PledgeAssetInfo info = pledgeAssetServiceClient.findById(Long.parseLong(id));
			if (info != null) {
				if (info.getUserId() == sessionLocal.getUserId()) {
					model.addAttribute("canCopy", "true");
				}
			}
			model.addAttribute("pledgeAsset", info);
		}
		
		if (sessionLocal != null) {
			PledgeAssetInfo info = pledgeAssetServiceClient.findById(NumberUtil.parseLong(id));
			if (info != null) {//当前用户是否有编辑权限
				if (info.getUserId() == sessionLocal.getUserId()
					&& StringUtil.isEmpty(info.getProjectCode())) {
					model.addAttribute("isPermission", "true");
				} else {
					model.addAttribute("isPermission", "false");
				}
			} else {
				model.addAttribute("isPermission", "false");
			}
			model.addAttribute("pledgeAsset", info);
			
		}
		model.addAttribute("isCopy", isCopy);
		if (StringUtil.isNotBlank(request.getParameter("iid"))) {
			//复评意见id
			long iid = NumberUtil.parseLong(request.getParameter("iid"), 0L);
			if (iid > 0) {
				FInvestigationCreditSchemePledgeAssetInfo assetReviewInfo = assetReviewServiceClient
					.findAssetInfoById(iid);
				model.addAttribute("assetReviewInfo", assetReviewInfo);
			}
		}
		model.addAttribute("arid", request.getParameter("arid")); //资产复评id
		
		return vm_path + "asset/assetAdd.vm";
	}
	
	/**
	 * 选择分类信息后加载数据（ajax请求数据 层级加载方式）
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("loadType.htm")
	@ResponseBody
	public JSONObject loadType(String levelOne, String levelTwo, Model model) {
		String tipPrefix = " 加载抵押质类型";
		JSONObject jsonObject = new JSONObject();
		//		JSONArray dataList = new JSONArray();
		try {
			List<PledgeTypeInfo> listInfo = new ArrayList<PledgeTypeInfo>();
			listInfo = pledgeTypeServiceClient.findByLevelOneAndLevelTwoForAssets(levelOne,
				levelTwo);
			jsonObject.put("success", true);
			jsonObject.put("listInfo", listInfo);
		} catch (Exception e) {
			jsonObject = toJSONResult(tipPrefix, e);
			jsonObject.put("success", false);
			logger.error(tipPrefix, e);
		}
		return jsonObject;
	}
	
	/**
	 * 新增资产(选择分类后 信息带出)
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("add.htm")
	@ResponseBody
	public JSONObject add(HttpServletRequest request, HttpServletResponse response,
							String levelOne, String levelTwo, String levelThree, Model model) {
		String tipPrefix = " 加载抵押质资产信息";
		JSONObject jsonObject = new JSONObject();
		//		JSONArray dataList = new JSONArray();
		try {
			PledgeTypeInfo info = pledgeTypeServiceClient.findByLevelOneTwoThree(levelOne,
				levelTwo, levelThree);
			
			jsonObject.put("info", info);
			//无多行文本
			jsonObject.put("textInfoList",
				pledgeTextCustomServiceClient.findNotMtextByTypeId(info.getTypeId()));
			//只有多行文本
			jsonObject.put("mtextInfoList",
				pledgeTextCustomServiceClient.findMtextByTypeId(info.getTypeId()));
			PledgeImageCustomQueryOrder order = new PledgeImageCustomQueryOrder();
			order.setTypeId(info.getTypeId());
			order.setLatestEntryForm(LatestEntryFormEnum.INVESTIGATION.code());
			QueryBaseBatchResult<PledgeImageCustomInfo> batchResult = pledgeImageCustomServiceClient
				.query(order);
			jsonObject.put("imageInfoList", batchResult.getPageList());
			jsonObject.put("networkInfoList",
				pledgeNetworkCustomServiceClient.findByTypeId(info.getTypeId()));
			jsonObject.put("success", true);
		} catch (Exception e) {
			jsonObject = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
			jsonObject.put("success", false);
		}
		return jsonObject;
	}
	
	/**
	 * 保存资产
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("save.htm")
	@ResponseBody
	public JSONObject save(PledgeAssetOrder order, Model model) {
		String tipPrefix = " 保存资产信息";
		JSONObject result = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				result.put("success", false);
				result.put("message", "您未登陆或登陆已失效");
				return result;
			}
			setSessionLocalInfo2Order(order);
			FcsBaseResult saveResult = pledgeAssetServiceClient.save(order);
			
			if (saveResult.isSuccess()) {
				result.put("success", true);
				result.put("message", "保存成功");
				result.put("id", saveResult.getKeyId());
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
	 * 编辑资产
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("edit.htm")
	@ResponseBody
	public JSONObject edit(Long id, Model model) {
		JSONObject jsonObject = new JSONObject();
		String tipPrefix = "编辑资产";
		try {
			PledgeAssetInfo info = pledgeAssetServiceClient.findById(id);
			AssetRelationProjectQueryOrder order = new AssetRelationProjectQueryOrder();
			order.setAssetsId(id);
			QueryBaseBatchResult<AssetRelationProjectInfo> batchResult = pledgeAssetServiceClient
				.queryAssetRelationProject(order);
			List<AssetRelationProjectInfo> listRelationInfo = batchResult.getPageList();
			if (listRelationInfo != null && listRelationInfo.size() > 0) {
				for (AssetRelationProjectInfo assetRelationProjectInfo : listRelationInfo) {
					if (assetRelationProjectInfo.getAssetsStatus() != null
						&& assetRelationProjectInfo.getAssetsStatus() == AssetStatusEnum.DEBT_ASSET) {//抵债资产
						jsonObject.put("isDebtAsset", "true");//是否抵债资产
						break;
					} else {
						jsonObject.put("isDebtAsset", "false");
					}
					
				}
			}
			//文字信息 无多行文本
			PledgeTypeAttributeTextQueryOrder queryOrder = new PledgeTypeAttributeTextQueryOrder();
			queryOrder.setAssetsId(info.getAssetsId());
			
			queryOrder.setCustomType("TEXT");
			QueryBaseBatchResult<PledgeTypeAttributeTextInfo> result = pledgeTypeAttributeServiceClient
				.queryAttributeText(queryOrder);
			List<PledgeTypeAttributeTextInfo> textInfoList = result.getPageList();
			
			//			model.addAttribute("textInfoList", textInfoList);
			//文字信息 只有多行文本
			PledgeTypeAttributeTextQueryOrder queryOrder1 = new PledgeTypeAttributeTextQueryOrder();
			queryOrder1.setAssetsId(info.getAssetsId());
			
			queryOrder1.setCustomType("MTEXT_TEXT");
			QueryBaseBatchResult<PledgeTypeAttributeTextInfo> result1 = pledgeTypeAttributeServiceClient
				.queryAttributeText(queryOrder1);
			List<PledgeTypeAttributeTextInfo> mtextInfoList = result1.getPageList();
			
			jsonObject.put("mtextInfoList", mtextInfoList);
			
			//图像信息
			PledgeTypeAttributeImageQueryOrder queryImageOrder = new PledgeTypeAttributeImageQueryOrder();
			queryImageOrder.setAssetsId(info.getAssetsId());
			queryImageOrder.setCustomType("IMAGE");
			queryImageOrder.setLatestEntryForm(LatestEntryFormEnum.INVESTIGATION.code());//只查询 资产新增的图像信息
			QueryBaseBatchResult<PledgeTypeAttributeImageInfo> resultImage = pledgeTypeAttributeServiceClient
				.queryAttributeImage(queryImageOrder);
			//			model.addAttribute("imageInfoList", resultImage.getPageList());
			//网络信息
			PledgeTypeAttributeNetworkQueryOrder queryNetworkOrder = new PledgeTypeAttributeNetworkQueryOrder();
			queryNetworkOrder.setAssetsId(info.getAssetsId());
			queryNetworkOrder.setCustomType("NETWORK");
			QueryBaseBatchResult<PledgeTypeAttributeNetworkInfo> resultNetwork = pledgeTypeAttributeServiceClient
				.queryAttributeNetwork(queryNetworkOrder);
			
			jsonObject.put("textInfoList", textInfoList);
			jsonObject.put("imageInfoList", resultImage.getPageList());
			jsonObject.put("networkInfoList", resultNetwork.getPageList());
			
			PledgeTypeInfo typeInfo = pledgeTypeServiceClient.findById(info.getTypeId());
			jsonObject.put("isMapPosition", typeInfo.getIsMapPosition().code());
			jsonObject.put("pledgeRate", typeInfo.getPledgeRate());
			jsonObject.put("fieldType", FieldTypeEnum.getAllEnum());
			jsonObject.put("timeRange", TimeRangeEnum.getAllEnum());
			
			jsonObject.put("latestEntryForm", LatestEntryFormEnum.getAllEnum());//最迟录入表单
			jsonObject.put("isEdit", "true");//是否编辑
			jsonObject.put("info", info);
			jsonObject.put("success", true);
			
		} catch (Exception e) {
			jsonObject = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
			jsonObject.put("success", false);
		}
		
		return jsonObject;
	}
	
	/**
	 * 资产详情
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("view.htm")
	@ResponseBody
	public JSONObject view(Long id, Model model) {
		JSONObject jsonObject = new JSONObject();
		String tipPrefix = "详情资产";
		try {
			PledgeAssetInfo info = pledgeAssetServiceClient.findById(id);
			AssetRelationProjectQueryOrder order = new AssetRelationProjectQueryOrder();
			order.setAssetsId(id);
			QueryBaseBatchResult<AssetRelationProjectInfo> batchResult = pledgeAssetServiceClient
				.queryAssetRelationProject(order);
			List<AssetRelationProjectInfo> listRelationInfo = batchResult.getPageList();
			if (listRelationInfo != null && listRelationInfo.size() > 0) {
				for (AssetRelationProjectInfo assetRelationProjectInfo : listRelationInfo) {
					if (assetRelationProjectInfo.getAssetsStatus() != null
						&& assetRelationProjectInfo.getAssetsStatus() == AssetStatusEnum.DEBT_ASSET) {//抵债资产
						jsonObject.put("isDebtAsset", "true");//是否抵债资产
						break;
					} else {
						jsonObject.put("isDebtAsset", "false");
					}
					
				}
			}
			PledgeTypeAttributeTextQueryOrder queryOrder = new PledgeTypeAttributeTextQueryOrder();
			queryOrder.setAssetsId(info.getAssetsId());
			//			queryOrder.setIsByRelation("NO"); //查询没有被关联的文字信息数据
			//			queryOrder.setRelationFieldName(null);
			queryOrder.setCustomType("TEXT");
			QueryBaseBatchResult<PledgeTypeAttributeTextInfo> result = pledgeTypeAttributeServiceClient
				.queryAttributeText(queryOrder);
			List<PledgeTypeAttributeTextInfo> textInfoList = result.getPageList();
			
			//			model.addAttribute("textInfoList", textInfoList);
			//文字信息 只有多行文本
			PledgeTypeAttributeTextQueryOrder queryOrder1 = new PledgeTypeAttributeTextQueryOrder();
			queryOrder1.setAssetsId(info.getAssetsId());
			
			queryOrder1.setCustomType("MTEXT_TEXT");
			QueryBaseBatchResult<PledgeTypeAttributeTextInfo> result1 = pledgeTypeAttributeServiceClient
				.queryAttributeText(queryOrder1);
			List<PledgeTypeAttributeTextInfo> mtextInfoList = result1.getPageList();
			
			jsonObject.put("mtextInfoList", mtextInfoList);
			
			//图像信息
			PledgeTypeAttributeImageQueryOrder queryImageOrder = new PledgeTypeAttributeImageQueryOrder();
			queryImageOrder.setAssetsId(info.getAssetsId());
			queryImageOrder.setCustomType("IMAGE");
			QueryBaseBatchResult<PledgeTypeAttributeImageInfo> resultImage = pledgeTypeAttributeServiceClient
				.queryAttributeImage(queryImageOrder);
			//			model.addAttribute("imageInfoList", resultImage.getPageList());
			//网络信息
			PledgeTypeAttributeNetworkQueryOrder queryNetworkOrder = new PledgeTypeAttributeNetworkQueryOrder();
			queryNetworkOrder.setAssetsId(info.getAssetsId());
			queryNetworkOrder.setCustomType("NETWORK");
			QueryBaseBatchResult<PledgeTypeAttributeNetworkInfo> resultNetwork = pledgeTypeAttributeServiceClient
				.queryAttributeNetwork(queryNetworkOrder);
			jsonObject.put("textInfoList", textInfoList);
			jsonObject.put("imageInfoList", resultImage.getPageList());
			jsonObject.put("networkInfoList", resultNetwork.getPageList());
			
			PledgeTypeInfo typeInfo = pledgeTypeServiceClient.findById(info.getTypeId());
			jsonObject.put("pledgeRate", typeInfo.getPledgeRate());
			jsonObject.put("isMapPosition", typeInfo.getIsMapPosition().code());
			jsonObject.put("fieldType", FieldTypeEnum.getAllEnum());
			jsonObject.put("timeRange", TimeRangeEnum.getAllEnum());
			
			jsonObject.put("latestEntryForm", LatestEntryFormEnum.getAllEnum());//最迟录入表单
			jsonObject.put("isEdit", "true");//是否编辑
			jsonObject.put("info", info);
			//			model.addAttribute("networkInfoList", resultNetwork.getPageList());
			//			model.addAttribute("info", info);
			jsonObject.put("success", true);
		} catch (Exception e) {
			jsonObject = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
			jsonObject.put("success", false);
		}
		return jsonObject;
	}
	
	/**
	 * 资产删除
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("delete.htm")
	@ResponseBody
	public JSONObject delete(long assetsId, Model model) {
		JSONObject result = new JSONObject();
		if (assetsId <= 0) {
			result.put("success", false);
			result.put("message", "删除失败，资产不存在");
		} else {
			long count = investigationServiceClient.investigationUseAssetCount(assetsId);
			if (count > 0) {
				result.put("success", false);
				result.put("message", "删除失败，资产已经被使用");
			} else {
				int del = pledgeAssetServiceClient.deleteById(assetsId);
				if (del > 0) {
					result.put("success", true);
					result.put("message", "删除成功");
				} else {
					result.put("success", false);
					result.put("message", "删除失败，资产不存在");
				}
			}
			
		}
		return result;
	}
	
	private Boolean isEditForInvestigation(PledgeAssetInfo info) {//是否由于尽职调查报告可编辑
		Boolean isEdit = true;
		if (null != info.getProjectCode() && !"".equals(info.getProjectCode())) {
			String[] projectCodeArr = info.getProjectCode().split(",");
			for (String projectCode : projectCodeArr) {
				InvestigationQueryOrder queryOrder = new InvestigationQueryOrder();
				queryOrder.setProjectCode(projectCode);
				List<String> formStatusList = new ArrayList<String>();
				formStatusList.add(FormStatusEnum.SUBMIT.code());
				formStatusList.add(FormStatusEnum.AUDITING.code());
				formStatusList.add(FormStatusEnum.DENY.code());//审核中和审核不通过的，只要有一条数据，则不可编辑
				queryOrder.setFormStatusList(formStatusList);
				QueryBaseBatchResult<InvestigationInfo> batchResult1 = investigationServiceClient
					.queryInvestigation(queryOrder);
				List<InvestigationInfo> info1 = batchResult1.getPageList();
				if (info1 != null && info1.size() > 0) {
					isEdit = false;
					return isEdit;
				}
				
			}
		}
		return isEdit;
	}
	
	//判断项目是否有批复
	private String isHaveApproval(String projectCode) {
		ProjectInfo projectInfo = projectServiceClient.queryByCode(projectCode, false);
		if (projectInfo == null) {
			return null;
		}
		if (projectInfo.getIsApproval() == BooleanEnum.NO) {
			return "NO";
		}
		return "IS";
	}
	
	/**
	 * 资产类型模板下载
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("templateExport.json")
	public String templateExport(long typeId, HttpServletRequest request,
									HttpServletResponse response, Model model) {
		
		try {
			if (typeId <= 0) {
				logger.error("资产类型不存在");
			} else {
				PledgeTypeInfo pypeInfo = pledgeTypeServiceClient.findById(typeId);
				if (pypeInfo == null) {
					logger.error("资产类型不存在");
				}
				String keyInfo = "";
				//找出关键信息
				PledgeTextCustomQueryOrder queryOrder = new PledgeTextCustomQueryOrder();
				queryOrder.setTypeId(typeId);
				QueryBaseBatchResult<PledgeTextCustomInfo> mtextResult1 = pledgeTextCustomServiceClient
					.query(queryOrder);
				List<AssetRemarkInfoEnum> listEnum = AssetRemarkInfoEnum.getAllEnum();
				if (mtextResult1 != null) {
					List<PledgeTextCustomInfo> listInfo = mtextResult1.getPageList();
					for (PledgeTextCustomInfo pledgeTextCustomInfo : listInfo) {
						
						for (AssetRemarkInfoEnum assetRemarkInfoEnum : listEnum) {
							if (assetRemarkInfoEnum.getMessage().equals(
								pledgeTextCustomInfo.getFieldName())) {
								keyInfo = pledgeTextCustomInfo.getFieldName();
								break;
							}
						}
						if (!"".equals(keyInfo)) {
							break;
						}
					}
				}
				
				OutputStream os = response.getOutputStream();//获得输出流
				response.reset();// 清空输出流  
				response.setContentType("application/msexcel;charset=utf-8");// 定义输出类型 
				response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ new String((pypeInfo.getLevelThree() + ".xls").getBytes("utf-8"),
								"ISO8859-1"));
				
				WritableWorkbook workbook = Workbook.createWorkbook(os); // 建立excel文件  
				//无多行文本信息
				List<PledgeTextCustomInfo> listTextCustomInfo = pledgeTextCustomServiceClient
					.findNotMtextByTypeId(typeId);
				/** **********创建工作表************ */
				
				WritableSheet sheet = workbook.createSheet(pypeInfo.getLevelThree(), 0);//输出普通数据
				/** **********设置纵横打印（默认为纵打）、打印纸***************** */
				jxl.SheetSettings sheetset = sheet.getSettings();
				sheetset.setProtected(false);
				
				/** ************设置单元格字体************** */
				WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
				WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
				
				/** ************以下设置三种单元格样式，灵活备用************ */
				// 用于标题居中  
				WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
				wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条  
				wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐  
				wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐  
				wcf_center.setWrap(false); // 文字是否换行  
				
				// 用于正文居左  
				WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
				wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条  
				wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐  
				wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐  
				wcf_left.setWrap(false); // 文字是否换行 
				
				for (int j = 0; j < listTextCustomInfo.size(); j++) {
					if (listTextCustomInfo.get(j).getFieldType().code()
						.equals(FieldTypeEnum.ADMINISTRATIVE_PLAN.code())) {
						sheet.addCell(new Label(j, 0, listTextCustomInfo.get(j).getFieldName()
														+ "（xx省/xx市/xx区（县））", wcf_center));
					} else {
						sheet.addCell(new Label(j, 0, listTextCustomInfo.get(j).getFieldName()
														+ (listTextCustomInfo.get(j)
															.getMeasurementUnit() == null ? ""
															: "（"
																+ listTextCustomInfo.get(j)
																	.getMeasurementUnit() + "）"),
							wcf_center));
					}
				}
				
				PledgeTextCustomQueryOrder order = new PledgeTextCustomQueryOrder();
				order.setTypeId(typeId);
				order.setFieldType("MTEXT");
				QueryBaseBatchResult<PledgeTextCustomInfo> mtextResult = pledgeTextCustomServiceClient
					.query(order);//查询有几个多行文本
				if (mtextResult != null) {
					List<PledgeTextCustomInfo> listMtextCustomInfo = mtextResult.getPageList();
					for (int i = 0; i < listMtextCustomInfo.size(); i++) {
						String sheetName = listMtextCustomInfo.get(i).getFieldName();
						WritableSheet sheet1 = workbook.createSheet(sheetName, 1);
						/** **********设置纵横打印（默认为纵打）、打印纸***************** */
						jxl.SheetSettings sheetset1 = sheet1.getSettings();
						sheetset1.setProtected(false);
						
						/** ************设置单元格字体************** */
						WritableFont NormalFont1 = new WritableFont(WritableFont.ARIAL, 15);
						WritableFont BoldFont1 = new WritableFont(WritableFont.ARIAL, 15,
							WritableFont.BOLD);
						
						/** ************以下设置三种单元格样式，灵活备用************ */
						// 用于标题居中  
						WritableCellFormat wcf_center1 = new WritableCellFormat(BoldFont1);
						wcf_center1.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条  
						wcf_center1.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐  
						wcf_center1.setAlignment(Alignment.CENTRE); // 文字水平对齐  
						wcf_center1.setWrap(false); // 文字是否换行  
						
						// 用于正文居左  
						WritableCellFormat wcf_left1 = new WritableCellFormat(NormalFont1);
						wcf_left1.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条  
						wcf_left1.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐  
						wcf_left1.setAlignment(Alignment.LEFT); // 文字水平对齐  
						wcf_left1.setWrap(false); // 文字是否换行 
						PledgeTextCustomQueryOrder order2 = new PledgeTextCustomQueryOrder();
						order2.setTypeId(typeId);
						order2.setRelationFieldName(sheetName);
						QueryBaseBatchResult<PledgeTextCustomInfo> mtextResult2 = pledgeTextCustomServiceClient
							.query(order2);
						if (mtextResult2 != null && mtextResult2.getPageList().size() > 0) {
							for (int j = 0; j < mtextResult2.getPageList().size() + 1; j++) {
								if (j == 0) {
									sheet1.addCell(new Label(j, 0, keyInfo, wcf_center1));
								} else {
									sheet1.addCell(new Label(j, 0, mtextResult2.getPageList()
										.get(j - 1).getFieldName(), wcf_center1));
								}
							}
						}
						
					}
				}
				
				workbook.write();
				/** *********关闭文件************* */
				workbook.close();
			}
		} catch (Exception e) {
			logger.error("Excel文件模板导出失败：{}", e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * excel导入
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("excelImport.json")
	@ResponseBody
	public JSONObject excelImport(long typeId, String ownershipName, HttpServletRequest request,
									HttpServletResponse response, Model model) {
		String tipPrefix = "excel资产导入";
		FcsBaseResult saveResult = new FcsBaseResult();
		JSONObject json = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				json.put("success", false);
				json.put("message", "您未登陆或登陆已失效");
				return json;
			}
			
			ExcelData excelData = ExcelUtil.uploadExcel(request);
			// 抓取数据生成order并存库
			int columns = excelData.getColumns(); //列数
			int rows = excelData.getRows(); //行数
			String[][] datas = excelData.getDatas(); //数据
			
			PledgeTypeInfo info = pledgeTypeServiceClient.findById(typeId);
			List<PledgeTextCustomInfo> listTextInfo = pledgeTextCustomServiceClient
				.findByTypeId(typeId);
			if (columns > listTextInfo.size()) {
				columns = listTextInfo.size();
			}
			//			List<String> fields = Lists.newArrayList();//存储所有的字段名
			//循环得到每一行的数据
			for (int i = 0; i < rows; i++) {
				// 第一行代表类型
				if (i == 0) {
					boolean b = false;
					for (int j = 0; j < columns; j++) {//这儿需要验证是否 是 同一类型
						String str = datas[i][j];
						for (PledgeTextCustomInfo pledgeTextCustomInfo : listTextInfo) {
							if (str != null && str.contains(pledgeTextCustomInfo.getFieldName())) {
								b = true;
								break;
							}
						}
						if (!b) {//说明类型不一样
							json.put("success", false);
							json.put("message", "导入类型跟选择类型不一致");
							return json;
						}
					}
				}
				
				if (i >= 1) {
					String relationFieldFameValue = "";//临时存储关联项的值，用来验证必填
					
					List<PledgeTypeAttributeOrder> attributeOrders = new ArrayList<PledgeTypeAttributeOrder>(); // order
					PledgeAssetOrder assetOrder = new PledgeAssetOrder();
					assetOrder.setTypeId(typeId);
					assetOrder.setAssetType(info.getLevelOne() + " - " + info.getLevelTwo() + " - "
											+ info.getLevelThree());
					assetOrder.setOwnershipName(ownershipName);
					setSessionLocalInfo2Order(assetOrder);
					
					double pledgeRateDouble = 0;//抵质押率
					double pledgePrice = 0;//抵质价格
					double evaluatedPrice = 0;//拟质押金额或者 评估价格
					// 创建一个关系集合 
					for (int j = 0; j < columns; j++) {
						PledgeTypeAttributeOrder order = new PledgeTypeAttributeOrder();
						String str = datas[i][j];//value值
						String key = datas[0][j];
						if (key.contains("（") && key.contains("）")) {//去掉单位
							key = key.substring(0, key.indexOf("（"));
						}
						order.setAttributeKey(key);//key值
						
						PledgeTextCustomInfo textCustomInfo = pledgeTextCustomServiceClient
							.findByTypeIdAndFieldName(typeId, key);//用来数据验证
						
						logger.info("数据列定义：{} | {}", key, textCustomInfo);
						
						if (FieldTypeEnum.TEXT.code().equals(textCustomInfo.getFieldType().code())) {//文本型
							if (textCustomInfo.getRelationConditionItem() == null) {//无条件关联项
								if ("IS".equals(textCustomInfo.getIsRequired().code())
									&& (str == null || "".equals(str))) {//是否必填
									json.put("success", false);
									json.put("message", "导入资产失败,第" + (i + 1) + "行,第" + (j + 1)
														+ "列无数据 ,此字段必填");
									return json;
								}
							} else {
								if (relationFieldFameValue != null
									&& relationFieldFameValue.equals(textCustomInfo
										.getRelationConditionItem())) {
									if ("IS".equals(textCustomInfo.getIsRequired().code())
										&& (str == null || "".equals(str))) {//是否必填
										json.put("success", false);
										json.put("message", "导入资产失败,第" + (i + 1) + "行,第" + (j + 1)
															+ "列无数据 ,此字段必填");
										return json;
									}
								}
							}
							
							if (str != null && str.length() > textCustomInfo.getControlLength()) {
								json.put("success", false);
								json.put("message", "导入资产失败,第" + (i + 1) + "行,第" + (j + 1)
													+ "列数据有误,此字段是文本型");
								return json;
							}
						}
						if (FieldTypeEnum.SELECT.code()
							.equals(textCustomInfo.getFieldType().code())) {//选择型
							if (textCustomInfo.getRelationConditionItem() == null) {//无条件关联项
								if ("IS".equals(textCustomInfo.getIsRequired().code())
									&& (str == null || "".equals(str))) {//是否必填
									json.put("success", false);
									json.put("message", "导入资产失败,第" + (i + 1) + "行,第" + (j + 1)
														+ "列无数据 ,此字段必填");
									return json;
								}
							} else {
								if (relationFieldFameValue != null
									&& relationFieldFameValue.equals(textCustomInfo
										.getRelationConditionItem())) {
									if ("IS".equals(textCustomInfo.getIsRequired().code())
										&& (str == null || "".equals(str))) {//是否必填
										json.put("success", false);
										json.put("message", "导入资产失败,第" + (i + 1) + "行,第" + (j + 1)
															+ "列无数据 ,此字段必填");
										return json;
									}
								}
							}
							String[] selections = textCustomInfo.getMostCompleteSelection().split(
								",");
							if (str != null && !"".equals(str)) {
								boolean b = false;
								String temp = "";
								for (String string : selections) {
									temp += string + ",";
									if (str.equals(string)) {
										b = true;
										break;
									}
								}
								if (!b) {
									json.put("success", false);
									json.put(
										"message",
										"导入资产失败,第" + (i + 1) + "行,第" + (j + 1)
												+ "列数据有误,，需填写符合选填内容（"
												+ temp.substring(0, temp.length() - 1) + "）");
									return json;
								}
							}
						}
						
						if (FieldTypeEnum.ADMINISTRATIVE_PLAN.code().equals(
							textCustomInfo.getFieldType().code())) {//行政区划
							if (textCustomInfo.getRelationConditionItem() == null) {//无条件关联项
								if ("IS".equals(textCustomInfo.getIsRequired().code())
									&& (str == null || "".equals(str))) {//是否必填
									json.put("success", false);
									json.put("message", "导入资产失败,第" + (i + 1) + "行,第" + (j + 1)
														+ "列无数据 ,此字段必填");
									return json;
								}
							} else {
								if (relationFieldFameValue != null
									&& relationFieldFameValue.equals(textCustomInfo
										.getRelationConditionItem())) {
									if ("IS".equals(textCustomInfo.getIsRequired().code())
										&& (str == null || "".equals(str))) {//是否必填
										json.put("success", false);
										json.put("message", "导入资产失败,第" + (i + 1) + "行,第" + (j + 1)
															+ "列无数据 ,此字段必填");
										return json;
									}
								}
							}
							
							if (str != null && !"".equals(str)) {
								String[] plans = str.split("/");
								if (plans.length == 1) {
									if ("美国".equals(str) || "美国".equals(str) || "日本".equals(str)
										|| "韩国".equals(str) || "马来西亚".equals(str)
										|| "泰国".equals(str) || "新加坡".equals(str)) {//说明是国外
										RegionQueryOrder regionOrder = new RegionQueryOrder();
										regionOrder.setName(str);
										QueryBaseBatchResult<RegionInfo> result = basicDataServiceClient
											.queryRegion(regionOrder);
										if (result != null && result.getPageList() != null
											&& result.getPageList().size() == 1) {
											List<RegionInfo> listRegion = result.getPageList();
											for (RegionInfo regionInfo : listRegion) {
												str = regionInfo.getCode();
											}
										}
									} else {
										json.put("success", false);
										json.put("message", "导入资产失败,第" + (i + 1) + "行,第" + (j + 1)
															+ "列数据有误，国家不存在 ");
										return json;
									}
								} else {//非国外
									if (plans.length == 2) {//说明是直辖市
										String tempCode = "China,";
										if ("北京市".equals(plans[0]) || "天津市".equals(plans[0])
											|| "上海市".equals(plans[0]) || "重庆市".equals(plans[0])
											|| "北京市".equals(plans[0])) {
											RegionQueryOrder regionOrder = new RegionQueryOrder();
											regionOrder.setName(plans[0]);
											QueryBaseBatchResult<RegionInfo> result = basicDataServiceClient
												.queryRegion(regionOrder);
											if (result != null && result.getPageList() != null
												&& result.getPageList().size() == 1) {
												List<RegionInfo> listRegion = result.getPageList();
												for (RegionInfo regionInfo : listRegion) {
													tempCode += regionInfo.getCode() + ",";
												}
											} else {
												json.put("success", false);
												json.put("message", "导入资产失败,第" + (i + 1) + "行,第"
																	+ (j + 1) + "列数据有误 ");
												return json;
											}
											RegionQueryOrder regionOrder1 = new RegionQueryOrder();
											regionOrder1.setName(plans[1]);
											QueryBaseBatchResult<RegionInfo> result1 = basicDataServiceClient
												.queryRegion(regionOrder1);
											if (result1 != null && result1.getPageList() != null
												&& result1.getPageList().size() == 1) {
												List<RegionInfo> listRegion1 = result1
													.getPageList();
												for (RegionInfo regionInfo : listRegion1) {
													tempCode += regionInfo.getParentCode() + ","
																+ regionInfo.getCode();
												}
											} else {
												json.put("success", false);
												json.put("message", "导入资产失败,第" + (i + 1) + "行,第"
																	+ (j + 1) + "列数据有误 ");
												return json;
											}
											if (!"China,".equals(tempCode)) {
												str = tempCode;
											}
										} else {
											json.put("success", false);
											json.put("message", "导入资产失败,第" + (i + 1) + "行,第"
																+ (j + 1) + "列数据有误 ");
											return json;
										}
									} else if (plans.length == 3) {//非直辖市
										String tempCode = "China,";
										for (String string : plans) {
											
											RegionQueryOrder regionOrder = new RegionQueryOrder();
											regionOrder.setName(string);
											QueryBaseBatchResult<RegionInfo> result = basicDataServiceClient
												.queryRegion(regionOrder);
											if (result != null && result.getPageList() != null
												&& result.getPageList().size() == 1) {
												List<RegionInfo> listRegion = result.getPageList();
												for (RegionInfo regionInfo : listRegion) {
													tempCode += regionInfo.getCode() + ",";
												}
											} else {
												json.put("success", false);
												json.put("message", "导入资产失败,第" + (i + 1) + "行,第"
																	+ (j + 1) + "列数据有误 ");
												return json;
											}
										}
										if (!"China,".equals(tempCode)) {
											str = tempCode.substring(0, tempCode.length() - 1);
										}
										
									} else {//错误录入
										json.put("success", false);
										json.put("message", "导入资产失败,第" + (i + 1) + "行,第" + (j + 1)
															+ "列数据有误 ");
										return json;
									}
								}
							}
						}
						if (FieldTypeEnum.SELECT_CONTION_RELATION.code().equals(
							textCustomInfo.getFieldType().code())) {//条件关联
							if ("IS".equals(textCustomInfo.getIsRequired().code())
								&& (str == null || "".equals(str))) {//是否必填
								json.put("success", false);
								json.put("message", "导入资产失败,第" + (i + 1) + "行,第" + (j + 1)
													+ "列无数据 ,此字段必填");
								return json;
							}
							String[] conditionItems = textCustomInfo.getConditionItem().split(",");
							if (str != null && !"".equals(str)) {
								boolean b = false;
								String temp = "";
								for (String string : conditionItems) {
									temp += string + ",";
									if (str.equals(string)) {
										relationFieldFameValue = str;
										b = true;
										break;
									}
								}
								if (!b) {
									json.put("success", false);
									json.put("message",
										"导入资产失败,第" + (i + 1) + "行,第" + (j + 1) + "列数据有误，需填写符合选填内容（"
												+ temp.substring(0, temp.length() - 1) + "）");
									return json;
								}
							}
						}
						if (FieldTypeEnum.DATE.code().equals(textCustomInfo.getFieldType().code())) {//日期
							if (textCustomInfo.getRelationConditionItem() == null) {//无条件关联项
								if ("IS".equals(textCustomInfo.getIsRequired().code())
									&& (str == null || "".equals(str))) {//是否必填
									json.put("success", false);
									json.put("message", "导入资产失败,第" + (i + 1) + "行,第" + (j + 1)
														+ "列无数据 ,此字段必填");
									return json;
								}
							} else {
								if (relationFieldFameValue != null
									&& relationFieldFameValue.equals(textCustomInfo
										.getRelationConditionItem())) {
									if ("IS".equals(textCustomInfo.getIsRequired().code())
										&& (str == null || "".equals(str))) {//是否必填
										json.put("success", false);
										json.put("message", "导入资产失败,第" + (i + 1) + "行,第" + (j + 1)
															+ "列无数据 ,此字段必填");
										return json;
									}
								}
							}
							if (str != null && !"".equals(str)) {
								try {
									if (!isDate(str)) {//时间数据格式不对
										json.put("success", false);
										json.put("message", "导入资产失败,第" + (i + 1) + "行,第" + (j + 1)
															+ "列数据有误,此字段是日期型");
										return json;
									}
									if (TimeRangeEnum.SYSTEM_TIME_AFTER.code().equals(//系统时间后
										textCustomInfo.getTimeSelectionRange().code())) {
										if (DateUtil.calculateDecreaseDate(
											DateUtil.dtSimpleFormat(new Date()), str) <= 0) {
											json.put("success", false);
											json.put("message", "导入资产失败,第" + (i + 1) + "行,第"
																+ (j + 1) + "列数据有误,此字段是日期型且在系统时间后");
											return json;
										}
									}
									if (TimeRangeEnum.SYSTEM_TIME_BEFORE.code().equals(//系统时间前
										textCustomInfo.getTimeSelectionRange().code())) {
										if (DateUtil.calculateDecreaseDate(
											DateUtil.dtSimpleFormat(new Date()), str) >= 0) {
											json.put("success", false);
											json.put("message", "导入资产失败,第" + (i + 1) + "行,第"
																+ (j + 1) + "列数据有误,此字段是日期型且在系统时间前");
											return json;
										}
									}
								} catch (Exception e) {
									json.put("success", false);
									json.put("message", "导入资产失败,第" + (i + 1) + "行,第" + (j + 1)
														+ "列数据有误,此字段是日期型");
									return json;
								}
								
							}
						}
						if (FieldTypeEnum.NUMBER.code()
							.equals(textCustomInfo.getFieldType().code())) {//数值型
							if (textCustomInfo.getRelationConditionItem() == null) {//无条件关联项
								if ("IS".equals(textCustomInfo.getIsRequired().code())
									&& (str == null || "".equals(str))) {//是否必填
									json.put("success", false);
									json.put("message", "导入资产失败,第" + (i + 1) + "行,第" + (j + 1)
														+ "列无数据 ,此字段必填");
									return json;
								}
							} else {
								if (relationFieldFameValue != null
									&& relationFieldFameValue.equals(textCustomInfo
										.getRelationConditionItem())) {
									if ("IS".equals(textCustomInfo.getIsRequired().code())
										&& (str == null || "".equals(str))) {//是否必填
										json.put("success", false);
										json.put("message", "导入资产失败,第" + (i + 1) + "行,第" + (j + 1)
															+ "列无数据 ,此字段必填");
										return json;
									}
								}
							}
							if (str != null && !"".equals(str)) {
								if (isNum(str)) {
									
								} else {
									json.put("success", false);
									json.put("message", "导入资产失败,第" + (i + 1) + "行,第" + (j + 1)
														+ "列数据有误,此字段是数值型");
									return json;
								}
							}
							//抵质押率 的验证
							if (datas[0][j] != null && "抵质押率".equals(key)) {
								
								if (isNum(str)) {
									double pledgeRate = NumberUtil.parseDouble(str);
									pledgeRateDouble = pledgeRate;
									if (pledgeRate > info.getPledgeRate()) {
										json.put("success", false);
										json.put("message", "导入资产失败,抵质押率超过阀值");
										return json;
									}
									if (pledgeRate == 0 || pledgeRate == 0.0) {
										json.put("success", false);
										json.put("message", "导入资产失败,抵质押率必须大于0");
										return json;
									}
								} else {
									json.put("success", false);
									json.put("message", "导入资产失败,抵质押率数据有误");
									return json;
								}
							}
							if (datas[0][j] != null && "抵质价格".equals(key)) {
								if (isNum(str)) {
									double temp = NumberUtil.parseDouble(str);
									pledgePrice = temp;
								} else {
									json.put("success", false);
									json.put("message", "导入资产失败,抵质价格数据有误");
									return json;
								}
							}
							if (datas[0][j] != null && ("评估价格".equals(key) || "拟质押金额".equals(key))) {
								if (isNum(str)) {
									double temp = NumberUtil.parseDouble(str);
									evaluatedPrice = temp;
								} else {
									json.put("success", false);
									json.put("message", "导入资产失败,评估价格或者拟质押金额数据有误");
									return json;
								}
							}
							if (str != null && !"".equals(str) && !str.contains(".")) {
								str = str + ".00";
							}
							
						}
						order.setAttributeValue(str);
						order.setCustomType("TEXT");
						order.setTypeId(typeId);
						attributeOrders.add(order);
						
					}
					//如果存在图像信息和网络信息，也需要存到属性里面去   导入 图像和网络 信息不验证
					PledgeImageCustomQueryOrder order = new PledgeImageCustomQueryOrder();
					order.setTypeId(info.getTypeId());
					order.setLatestEntryForm(LatestEntryFormEnum.INVESTIGATION.code());
					QueryBaseBatchResult<PledgeImageCustomInfo> batchResult = pledgeImageCustomServiceClient
						.query(order);
					List<PledgeImageCustomInfo> listImageInfo = batchResult.getPageList();
					if (ListUtil.isNotEmpty(listImageInfo)) {
						for (PledgeImageCustomInfo pledgeImageCustomInfo : listImageInfo) {
							PledgeTypeAttributeOrder imageOrder = new PledgeTypeAttributeOrder();
							imageOrder.setAttributeKey(pledgeImageCustomInfo.getFieldName());
							imageOrder.setAttributeValue(null);
							imageOrder.setCustomType("IMAGE");
							imageOrder.setTypeId(typeId);
							attributeOrders.add(imageOrder);
						}
					}
					List<PledgeNetworkCustomInfo> listNetworkInfo = pledgeNetworkCustomServiceClient
						.findByTypeId(info.getTypeId());
					if (ListUtil.isNotEmpty(listNetworkInfo)) {
						for (PledgeNetworkCustomInfo pledgeNetworkCustomInfo : listNetworkInfo) {
							PledgeTypeAttributeOrder networkOrder = new PledgeTypeAttributeOrder();
							networkOrder.setAttributeKey(pledgeNetworkCustomInfo.getWebsiteName());
							networkOrder.setAttributeValue(null);
							networkOrder.setCustomType("NETWORK");
							networkOrder.setTypeId(typeId);
							attributeOrders.add(networkOrder);
						}
					}
					//验证评估价格*抵质押率 =  抵质价格 
					if (evaluatedPrice * pledgeRateDouble / 100 == pledgePrice) {
						
					} else {
						json.put("success", false);
						json.put("message", "导入资产失败,评估价格或者拟质押金额*抵质押率(%) 不等于 抵质价格");
						return json;
					}
					assetOrder.setPledgeTypeAttributeOrders(attributeOrders);
					saveResult = pledgeAssetServiceClient.save(assetOrder);
				}
				
			}
			json.put("success", true);
			json.put("message", "导入成功");
			
			return toJSONResult(saveResult, tipPrefix);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			json.put("success", false);
			json.put("message", "导入资产异常,导入失败" + e.getMessage());
		}
		
		return json;
	}
	
	public boolean isNum(String str) {
		try {
			new BigDecimal(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean isDate(String str) {
		try {
			DateUtil.string2Date(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
