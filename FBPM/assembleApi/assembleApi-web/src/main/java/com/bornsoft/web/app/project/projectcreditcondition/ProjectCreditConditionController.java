package com.bornsoft.web.app.project.projectcreditcondition;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.am.ws.enums.LatestEntryFormEnum;
import com.born.fcs.am.ws.info.pledgeasset.PledgeAssetInfo;
import com.born.fcs.am.ws.info.pledgeimage.PledgeImageCustomInfo;
import com.born.fcs.am.ws.order.pledgeimage.PledgeImageCustomQueryOrder;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CheckStatusEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractItemInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.ProjectCreditConditionInfo;
import com.born.fcs.pm.ws.order.projectcreditcondition.FCreditConditionConfirmOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.ProjectCreditAssetAttachmentOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.ProjectCreditConditionOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.ProjectCreditConditionQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.bornsoft.api.service.app.JckFormService;
import com.bornsoft.utils.constants.ApiSystemParamEnum;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.GsonUtil;
import com.bornsoft.utils.tool.JsonParseUtil;
import com.bornsoft.web.app.base.WorkflowBaseController;
import com.bornsoft.web.app.util.PageUtil;

@Controller
@RequestMapping("projectMg/projectCreditCondition")
public class ProjectCreditConditionController extends WorkflowBaseController {
	
	@Autowired
	private JckFormService jckFormService;
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "confirmDate", "projectCreditConditionOrders[0].confirmDate",
								"projectCreditConditionOrders[1].confirmDate",
								"projectCreditConditionOrders[2].confirmDate",
								"projectCreditConditionOrders[3].confirmDate",
								"projectCreditConditionOrders[4].confirmDate",
								"projectCreditConditionOrders[5].confirmDate" };
	}
	
	@ResponseBody
	@RequestMapping("queryCreditCondtions.json")
	public JSONObject queryCreditCondtions(String projectCode){
		JSONObject result = new JSONObject(); 
		try {
			ProjectCreditConditionQueryOrder order = new ProjectCreditConditionQueryOrder();
			order.setProjectCode(projectCode);
			QueryBaseBatchResult<ProjectCreditConditionInfo> pageResult = projectCreditConditionServiceClient.queryCreditCondition(order);
			JSON page =  GsonUtil.toJSONObject(PageUtil.getCovertPage(pageResult));
			result.put("page", page);
		} catch (Exception e) {
			toJSONResult(result, AppResultCodeEnum.FAILED,"授信落实查询:" + e.getMessage());
			logger.error("授信落实查询失败:",e);
		}
		return result;
	}
	
	
	/**
	 * 新增授信条件落实情况[初始化]
	 * @param order
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("initProjectCreditCondition.json")
	public JSONObject addProjectCreditCondition(String projectCode) {
		
		JSONObject result = new JSONObject(); 
		try {
			String spCode = showProjectApproval(projectCode);
			String url = sysParameterServiceClient.getSysParameterValue(ApiSystemParamEnum.FACE_URL.code());
			if(StringUtil.isBlank(url)){
				throw new BornApiException("未配置face访问地址");
			}
			ProjectSimpleDetailInfo projectInfo = projectServiceClient
				.querySimpleDetailInfo(projectCode);
			//未申请的授信条件落实情况
			List<ProjectCreditConditionInfo> listProjectCreditConditioninfo = projectCreditConditionServiceClient
				.findProjectCreditConditionByProjectCodeAndStatus(projectCode,
					CheckStatusEnum.NOT_APPLY.code());
			for (ProjectCreditConditionInfo projectCreditConditionInfo : listProjectCreditConditioninfo) {
				//客户经理A角信息
				long busiManagerId = projectInfo.getBusiManagerId();
				String busiManagerName = projectInfo.getBusiManagerName();
				String busiManagerAccount = projectInfo.getBusiManagerAccount();
				//客户经理B角信息
				long busiManagerbId = projectInfo.getBusiManagerbId();
				String busiManagerbName = projectInfo.getBusiManagerbName();
				String busiManagerbAccount = projectInfo.getBusiManagerbAccount();
				projectCreditConditionInfo.setConfirmManId(busiManagerId + "," + busiManagerbId);
				projectCreditConditionInfo.setConfirmManName(busiManagerName + "," + busiManagerbName);
				projectCreditConditionInfo.setConfirmManAccount(busiManagerAccount + ","
																+ busiManagerbAccount);
				
				if (projectCreditConditionInfo.getContractNo() != null) {
					ProjectContractItemInfo contractInfo = projectContractServiceClient
						.findByContractCodeProjectCreditConditionUse(projectCreditConditionInfo
							.getContractNo());
					String contractScanUrl = contractInfo.getContractScanUrl();
					if (null != contractInfo && null != contractScanUrl) {
						projectCreditConditionInfo.setContractAgreementUrl(contractScanUrl);
						projectCreditConditionInfo.setConfirmDate(contractInfo.getSignedTime());
					}
				}
			}

			JSONArray  conditionList = (JSONArray) GsonUtil.toJSONObject(listProjectCreditConditioninfo);
			//特殊处理
			if(StringUtil.isNotBlank(spCode)){
				String token = getAccessToken();
				for(Object o : conditionList){
					JSONObject e = (JSONObject) o;
					@SuppressWarnings("deprecation")
					String tmpUrl = url+"/projectMg/meetingMg/summary/approval.htm?spCode="+ URLEncoder.encode(spCode)+"&accessToken="+token;
					e.put("url", tmpUrl);
					//附件处理
/*					String assetId = e.getString("assetId");
					if(!StringUtils.equals(assetId, "0")){
						//资产附件
						e.put("attachType", "assets");
					}else{
						//简单附件
						e.put("labelName", "");
						e.put("attachType", "guarantee");
					}*/
				}
			}
			
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
			result.put("projectInfo", GsonUtil.toJSONObject(projectInfo));
			result.put("conditionList", conditionList);
		} catch (Exception e) {
			toJSONResult(result, AppResultCodeEnum.FAILED,"授信落实初始化失败:" + e.getMessage());
			logger.error("授信落实新增初始化",e);
		}
		return result;
	}
	
	public static boolean checkBool(String bool){
		return BooleanEnum.IS.code().equals(bool) || BooleanEnum.YES.code().equals(bool);
	}
	
	/**
	 * 保存授信条件落实情况
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveProjectCreditCondition.json")
	@ResponseBody
	public JSONObject saveProjectCreditCondition(HttpServletRequest request,
													HttpServletResponse response) {
		JSONObject result = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				result.put("success", false);
				result.put("message", "您未登陆或登陆已失效");
				return result;
			}
			String reqJson = parseStreamToString(request);
			JSONObject json = JsonParseUtil.parseObject(reqJson,JSONObject.class);
			LocalFCreditConditionConfirmOrder order = JsonParseUtil.parseObject(reqJson, LocalFCreditConditionConfirmOrder.class);
			//附件设值
			if(order.getProjectCreditConditionOrders()!=null){
				JSONArray conditionOrders = json.getJSONArray("projectCreditConditionOrders");
				for(int i=0;i<order.getProjectCreditConditionOrders().size();i++){
					ProjectCreditConditionOrder condition = order.getProjectCreditConditionOrders().get(i);
					if(condition.getAttachmentOrders()!=null){
						JSONObject conditionJson = conditionOrders.getJSONObject(i);
						if(StringUtil.equals("guarantee", conditionJson.getString("attachType"))){
							if(condition.getAttachmentOrders()!=null && condition.getAttachmentOrders().size()>0){
								condition.setTextInfo(condition.getAttachmentOrders().get(0).getImageTextValue());
								condition.setRightVouche(condition.getAttachmentOrders().get(0).getImageValue());
								condition.setRightVouche(trimLastBranche(condition.getRightVouche()));
								condition.getAttachmentOrders().clear();
							}else{
								toJSONResult(result, AppResultCodeEnum.FAILED, "请上传附件");
								return result;
							}
						}else if(StringUtil.equals("assets", conditionJson.getString("attachType"))){
							for(ProjectCreditAssetAttachmentOrder attach : condition.getAttachmentOrders()){
								attach.setAssetId(condition.getAssetId());
								attach.setCreditId(condition.getId());
								attach.setImageValue(trimLastBranche(attach.getImageValue()));
							}
						}
						
					}
				}
			}
			//保证金设置
			if(checkBool(order.getIsMargin())){
				if(order.getProjectCreditMarginOrder()!=null){
					order.setIsMargin(BooleanEnum.IS.code());
				}else{
					toJSONResult(result, AppResultCodeEnum.FAILED, "保证金落实情况未填写");
					return result;
				}
			}
			// 初始化Form验证信息
			FormBaseResult saveResult = null;
			FcsBaseResult holdResult = null;
			if (order.getProjectCreditMarginOrder() != null) {
				order.getProjectCreditMarginOrder().setPeriod(
					NumberUtil.parseInt(order.getProjectCreditMarginOrder().getStrPeriod()));
			}
			List<ProjectCreditConditionOrder> listCreditOrder = order
				.getProjectCreditConditionOrders();
			if (listCreditOrder != null) {
				JSONArray listCreditOrderJson = json.getJSONArray("projectCreditConditionOrders");
				for (int i=0;i<listCreditOrder.size();i++) {
					ProjectCreditConditionOrder projectCreditConditionOrder = listCreditOrder.get(i);
					JSONObject credit = (JSONObject) listCreditOrderJson.get(i);
					projectCreditConditionOrder.setConfirmDateStr(credit.getString("confirmDate"));
					//YES IS转换
					if(checkBool(projectCreditConditionOrder.getIsConfirm())){
						projectCreditConditionOrder.setIsConfirm(BooleanEnum.IS.code());
					}
				}
			}
			ProjectSimpleDetailInfo projectInfo = projectServiceClient.querySimpleDetailInfo(order
				.getProjectCode());
			if (projectInfo != null && projectInfo.getStatus() != ProjectStatusEnum.PAUSE) {
				//隐藏域设置
				order.setCheckStatus(1);
				order.setCheckIndex(0);
				order.setRelatedProjectCode(order.getProjectCode());
				order.setFormCode(FormCodeEnum.PROJECT_CREDIT_CONDITION_CONFIRM);
				order.setCustomerName(projectInfo.getCustomerName());
				order.setCustomerType(projectInfo.getCustomerType().code());
				order.setProjectName(projectInfo.getProjectName());
				order.setContractNo(projectInfo.getContractNo());
				order.setBusiType(projectInfo.getBusiType());
				order.setBusiTypeName(projectInfo.getBusiTypeName());
				order.setTimeLimit(projectInfo.getTimeLimit());
				order.setTimeUnit(projectInfo.getTimeUnit().code());
				order.setInstitutionId(projectInfo.getInstitutionId());
				order.setInstitutionName(projectInfo.getInstitutionName());
				order.setAmount(projectInfo.getAmount().getAmount().doubleValue());
				
				
				setSessionLocalInfo2Order(order);
				//				order.setAmount(order.getAmount() * 10000);
				//用来确定提交和暂存操作，提交操作需要将勾选已落实数据拷贝数据到f_credit_condition_confirm_item中并保存数据
				List<ProjectCreditConditionOrder> conditionOrders = order
					.getProjectCreditConditionOrders();
				String creditIds = "";
				for (ProjectCreditConditionOrder projectCreditConditionOrder : conditionOrders) {
					projectCreditConditionOrder.setProjectCode(order.getProjectCode());
					if (projectCreditConditionOrder.getIsConfirm() == null) {
						projectCreditConditionOrder.setIsConfirm("NO");
					}
					if (checkBool(projectCreditConditionOrder.getIsConfirm())) {
						creditIds += projectCreditConditionOrder.getId() + ",";
					}
				}
				if(StringUtils.isBlank(creditIds)){
					throw new BornApiException("至少需要落实一个授信条件");
				}
				creditIds = creditIds.substring(0, creditIds.length() - 1);
				if (order.getProjectCreditMarginOrder() != null) {
					order.getProjectCreditMarginOrder().setCreditId(creditIds);
				}
				order.setProjectCreditConditionOrders(conditionOrders);
				//				if(order.getCheckStatus() == 1){
				saveResult = projectCreditConditionServiceClient.saveProjectCreditCondition(order);
				result = toJSONResult(result, saveResult, "提交授信落实条件成功", null);
				result.put("formId", saveResult.getFormInfo().getFormId());
				result.put("status", "SUBMIT");
				if (saveResult.isSuccess()) {
					toJSONResult(result, AppResultCodeEnum.SUCCESS, "提交成功");
				} else {
					toJSONResult(result, AppResultCodeEnum.FAILED, saveResult.getMessage());
				}
				//				}
				for (ProjectCreditConditionOrder projectCreditConditionOrder : conditionOrders) {
					if (order.getCheckStatus() == 1) {//提交状态
						if (checkBool(projectCreditConditionOrder.getIsConfirm())) {//将勾选已落实数据拷贝到f_credit_condition_confirm_item中并更新状态
							projectCreditConditionOrder.setCheckStatus(0);
							projectCreditConditionOrder.setCheckIndex(0);
							if (saveResult.getKeyId() != 0) {
								projectCreditConditionOrder.setConfirmId(saveResult.getKeyId());
							}
							projectCreditConditionServiceClient
								.saveFCreditConditionConfirmItem(projectCreditConditionOrder);
						}
						//重新编辑的时候，如果改成未落实,则保存的时候  还原这条数据
						if (order.getConfirmId() != null
							&& order.getConfirmId() > 0
							&& projectCreditConditionOrder.getIsConfirm() == BooleanEnum.NO
								.getCode()) {
							
							projectCreditConditionOrder.setCheckStatus(0);
							projectCreditConditionOrder.setCheckIndex(0);
							projectCreditConditionServiceClient
								.saveFCreditConditionConfirmItem(projectCreditConditionOrder);
						}
						
					} else {//暂存状态
						if (checkBool(projectCreditConditionOrder.getIsConfirm())) {//将勾选已落实数据拷贝到f_credit_condition_confirm_item中并更新状态
							projectCreditConditionOrder.setCheckStatus(0);
							projectCreditConditionOrder.setCheckIndex(0);
							if (saveResult.getKeyId() != 0) {
								projectCreditConditionOrder.setConfirmId(saveResult.getKeyId());
							}
							holdResult = projectCreditConditionServiceClient
								.saveFCreditConditionConfirmItem(projectCreditConditionOrder);
							result.put("status", "HOLD");
							if (holdResult.isSuccess()) {
								toJSONResult(result, AppResultCodeEnum.SUCCESS, "暂存成功");
							} else {
								toJSONResult(result, AppResultCodeEnum.FAILED, saveResult.getMessage());
							}
						}
						if (order.getConfirmId() != null
							&& order.getConfirmId() > 0
							&& projectCreditConditionOrder.getIsConfirm() == BooleanEnum.NO
								.getCode()) {
							projectCreditConditionOrder.setCheckStatus(0);
							projectCreditConditionOrder.setCheckIndex(0);
							projectCreditConditionServiceClient
								.saveFCreditConditionConfirmItem(projectCreditConditionOrder);
						}
						
					}
				}
				if (StringUtils.isBlank(order.getStatus())||StringUtils.equals(order.getStatus(), "on")) {
					//自动提交
					result = jckFormService.submit(saveResult.getFormInfo().getFormId(), request);
					result.put("status", "submit");
				} else {
					toJSONResult(result,saveResult, "暂存成功", "暂存失败");
					result.put("status", "hold");
				}
			} else {
				toJSONResult(result, AppResultCodeEnum.FAILED, "项目暂缓,不能提交");
			}
			
		} catch (Exception e) {
			toJSONResult(result, AppResultCodeEnum.FAILED, e.getMessage());
			logger.error("授信落实失败：", e);
		}
		logger.error("授信落实保存,出参={}", result);
		return result;
	}

	/**
	 * 去除最后一个分号
	 * @param tmp
	 * @return
	 */
	private String trimLastBranche(String tmp) {
		if(StringUtil.isNotBlank(tmp)){
			String lastChar = tmp.substring(tmp.length()-1, tmp.length());
			if(StringUtil.equals(lastChar, ";")){
				return tmp.substring(0, tmp.length()-1);
			}
		}
		return tmp;
	}

	/**
	 * 加载资产附件信息
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("loadAssetAtachment.json")
	@ResponseBody
	public JSONObject loadAssetAtachment(Long assetId) {
		JSONObject jsonObject = new JSONObject();
		try {
			if (assetId != null && assetId > 0) {
				PledgeAssetInfo assetInfo = pledgeAssetServiceClient.findById(assetId);
				if(assetInfo!=null){
					PledgeImageCustomQueryOrder order = new PledgeImageCustomQueryOrder();
					order.setTypeId(assetInfo.getTypeId());
					order.setLatestEntryForm(LatestEntryFormEnum.CREDIT.code());
					order.setPageSize(99999l);
					QueryBaseBatchResult<PledgeImageCustomInfo> resultImage = pledgeImageCustomServiceClient
							.query(order);
					if(resultImage!=null && resultImage.isSuccess()){
						JSONArray listImageInfo = new JSONArray();
						if(resultImage.getPageList().size()>0){
							for(PledgeImageCustomInfo image : resultImage.getPageList()){
								JSONObject e = new JSONObject();
								listImageInfo.add(e);
								e.put("imageKey", image.getFieldName());
								e.put("isRequired", (image.getIsRequired()==null)?BooleanEnum.NO:image.getIsRequired().code());
								e.put("format", image.getAttachmentFormat());
							}
							jsonObject.put("attachType", "assets");
							jsonObject.put("listImageInfo", listImageInfo);
							toJSONResult(jsonObject, AppResultCodeEnum.SUCCESS, "");
						}else{
							makeNormal(jsonObject, listImageInfo);
							toJSONResult(jsonObject, AppResultCodeEnum.SUCCESS, "资产附件不存在,请上传普通附件");
						}

					} else{
						toJSONResult(jsonObject, AppResultCodeEnum.FAILED, "资产附件不存在");
					}
				}else{
					toJSONResult(jsonObject, AppResultCodeEnum.FAILED, "资产附件不存在");
				}
			} else {
				JSONArray listImageInfo = new JSONArray();
				makeNormal(jsonObject, listImageInfo);
				toJSONResult(jsonObject, AppResultCodeEnum.SUCCESS, "");
			}
			
		} catch (Exception e) {
			logger.error("加载资产附件信息", e);
			toJSONResult(jsonObject, AppResultCodeEnum.FAILED, "加载资产附件信息失败："+e.getMessage());
		}
		
		return jsonObject;
	}

	/**
	 * 普通附件
	 * @param jsonObject
	 * @param listImageInfo
	 */
	private void makeNormal(JSONObject jsonObject, JSONArray listImageInfo) {
		JSONObject e = new JSONObject();
		listImageInfo.add(e);
		e.put("imageKey", "");
		e.put("isRequired", BooleanEnum.IS.code());
		e.put("format", "all");
		jsonObject.put("attachType", "guarantee");
		jsonObject.put("listImageInfo", listImageInfo);
	}

	public static class LocalFCreditConditionConfirmOrder extends FCreditConditionConfirmOrder{
		/**
		 */
		private static final long serialVersionUID = 1L;
		private String status;

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		
	}
	
}
