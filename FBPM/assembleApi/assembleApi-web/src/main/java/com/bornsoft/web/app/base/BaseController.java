package com.bornsoft.web.app.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.alibaba.fastjson.JSONObject;
import com.born.bpm.service.client.user.SysOrg;
import com.born.fcs.crm.ws.service.enums.AuditStatusEnum;
import com.born.fcs.crm.ws.service.enums.CertTypesEnum;
import com.born.fcs.crm.ws.service.enums.ChanalTypeEnum;
import com.born.fcs.crm.ws.service.enums.CitizenTypeEnum;
import com.born.fcs.crm.ws.service.enums.ComparEnums;
import com.born.fcs.crm.ws.service.enums.CustomerRelationEnum;
import com.born.fcs.crm.ws.service.enums.CustomerSourceEnum;
import com.born.fcs.crm.ws.service.enums.EvaluetingLevelEnum;
import com.born.fcs.crm.ws.service.enums.FinancialInstitutionsEnum;
import com.born.fcs.crm.ws.service.enums.MaritalStatusEnum;
import com.born.fcs.crm.ws.service.enums.SexEnum;
import com.born.fcs.crm.ws.service.order.CustomerBaseOrder;
import com.born.fcs.crm.ws.service.order.VerifyMessageOrder;
import com.born.fcs.crm.ws.service.result.ValidateCustomerResult;
import com.born.fcs.face.integration.bpm.service.info.UserInfo;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.base.QueryFormBase;
import com.born.fcs.pm.ws.base.QueryPermissionPageBase;
import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CheckStatusEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.CouncilStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.EnterpriseNatureEnum;
import com.born.fcs.pm.ws.enums.EnterpriseScaleEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.council.CouncilJudgeInfo;
import com.born.fcs.pm.ws.order.base.FormOrderBase;
import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.born.fcs.pm.ws.order.base.ProjectFormOrderBase;
import com.born.fcs.pm.ws.order.common.CommonAttachmentOrder;
import com.born.fcs.pm.ws.order.common.SimpleFormOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.SysWebAccessTokenOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.FormService;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.bornsoft.api.service.app.DataPermissionUtil;
import com.bornsoft.api.service.app.ModuleEnum;
import com.bornsoft.api.service.app.util.WebUtil;
import com.bornsoft.pub.enums.UserTypeEnum;
import com.bornsoft.pub.order.risk.SynWatchListOrder;
import com.bornsoft.pub.order.risk.SynWatchListOrder.WatchListInfo;
import com.bornsoft.utils.base.BornSynResultBase;
import com.bornsoft.utils.constants.ApiSystemParamEnum;
import com.bornsoft.utils.constants.BornApiConstants;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.AesUtil;
import com.bornsoft.utils.tool.GsonUtil;
import com.bornsoft.utils.tool.JsonParseUtil;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.yjf.common.lang.result.ResultBase;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class BaseController extends BaseAutowiredController {

	protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	public static final String CODE = "code";
	public static final String MESSAGE = "message";
	public static final String LIKE_CODE_OR_NAME = "likeCodeOrName";
	public static final String TOTAL = "total";
	private static String lineSeperate = System.getProperty("line.separator");

	/**
	 * 授信用途
	 */
	private volatile static JSONObject loanPurposeJson;

	static {

		GsonUtil.builder.registerTypeAdapter(CouncilTypeEnum.class,
				new TypeAdapter<CouncilTypeEnum>() {

					@Override
					public CouncilTypeEnum read(JsonReader reader)
							throws IOException {
						if (reader.peek() == JsonToken.NULL) {
							reader.nextNull();
							return null;
						} else {
							return CouncilTypeEnum.getByCode(reader
									.nextString());
						}
					}

					@Override
					public void write(JsonWriter writer, CouncilTypeEnum value)
							throws IOException {
						if (value == null) {
							writer.nullValue();
							return;
						} else {
							writer.value(value.message());
						}
					}
				});

		GsonUtil.builder.registerTypeAdapter(CouncilStatusEnum.class,
				new TypeAdapter<CouncilStatusEnum>() {

					@Override
					public CouncilStatusEnum read(JsonReader reader)
							throws IOException {
						if (reader.peek() == JsonToken.NULL) {
							reader.nextNull();
							return null;
						} else {
							return CouncilStatusEnum.getByCode(reader
									.nextString());
						}
					}

					@Override
					public void write(JsonWriter writer, CouncilStatusEnum value)
							throws IOException {
						if (value == null) {
							writer.nullValue();
							return;
						} else {
							writer.value(value.message());
						}
					}
				});
		GsonUtil.builder.registerTypeAdapter(CouncilJudgeInfo.class,
				new TypeAdapter<CouncilJudgeInfo>() {

					@Override
					public CouncilJudgeInfo read(JsonReader reader)
							throws IOException {
						CouncilJudgeInfo info = new CouncilJudgeInfo();
						info.setJudgeName(reader.nextString());
						return info;
					}

					@Override
					public void write(JsonWriter writer, CouncilJudgeInfo value)
							throws IOException {
						if (value == null) {
							writer.nullValue();
							return;
						} else {
							writer.value(value.getJudgeName());
						}
					}
				});

	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		String[] nameArray = getDateInputNameArray();
		if (nameArray != null && nameArray.length > 0) {
			for (int i = 0; i < nameArray.length; i++) {
				binder.registerCustomEditor(Date.class, nameArray[i],
						new CustomDateEditor(new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss"), true));

			}
		}
		String[] dateDayNameArray = getDateInputDayNameArray();
		if (dateDayNameArray != null && dateDayNameArray.length > 0) {
			for (int i = 0; i < dateDayNameArray.length; i++) {
				binder.registerCustomEditor(Date.class, dateDayNameArray[i],
						new CustomDateEditor(
								new SimpleDateFormat("yyyy-MM-dd"), true));

			}
		}
		String[] moneyNameArray = getMoneyInputNameArray();
		if (dateDayNameArray != null && moneyNameArray.length > 0) {
			for (int i = 0; i < moneyNameArray.length; i++) {
				binder.registerCustomEditor(Money.class, moneyNameArray[i],
						new CommonBindingInitializer());
			}
		}
	}

	
	/**
	 * 获取站外访问密钥
	 * @param userInfo
	 * @return
	 */
	public String getAccessToken() {
		SysWebAccessTokenOrder tokenOrder = new SysWebAccessTokenOrder();
		tokenOrder.setUserId(ShiroSessionUtils.getSessionLocal().getUserId());
		tokenOrder.setUserAccount(ShiroSessionUtils.getSessionLocal().getUserName());
		tokenOrder.setUserName(ShiroSessionUtils.getSessionLocal().getRealName());
		tokenOrder.setRemark("站外访问链接");
		FcsBaseResult tokenResult = sysWebAccessTokenServiceClient.addUserAccessToken(tokenOrder);
		if (tokenResult != null && tokenResult.isSuccess()) {
			return tokenResult.getUrl();
		} else {
			return "";
		}
	}
	
	/**
	 * 拼接PC链接
	 * @param url
	 * @param userInfo
	 * @return
	 */
	protected String visitPcUrl(String url){
		return url+="accessToken="+getAccessToken();
	}
	
	/**
	 * 授信用途
	 * 
	 * @param parentCode
	 * @param model
	 * @return
	 */
	public JSONObject getLoanPurpose() {
		if (loanPurposeJson == null) {
			synchronized (this) {
				if (loanPurposeJson == null) {
					try {
						Scanner scan = new Scanner(
								BaseDataLoadController.class
										.getResourceAsStream("/json/loanPurpose.json"));
						StringBuilder line = new StringBuilder();
						while (scan.hasNextLine()) {
							line.append(scan.nextLine());
						}
						loanPurposeJson = JSONObject.parseObject(line
								.toString());
						scan.close();
					} catch (Exception e) {
						logger.error("读取授信用途失败", e);
					}
				}
			}
		}
		return loanPurposeJson;
	}

	protected String[] getDateInputNameArray() {
		return new String[0];
	}

	protected String[] getDateInputDayNameArray() {
		return new String[0];
	}

	protected String[] getMoneyInputNameArray() {
		return new String[0];
	}

	protected String sendUrl(HttpServletResponse response, String url) {
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	protected String sendUrl(HttpServletResponse response, String url, Map<String, String> params) {
		try {
			if (params != null && !params.isEmpty()) {
				int i = 0;
				for (String key : params.keySet()) {
					if (i == 0) {
						url += "?" + key + "=" + params.get(key);
					} else {
						url += "&" + key + "=" + params.get(key);
					}
					i++;
				}
			}
			response.sendRedirect(url);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}



	/**
	 * APP返回结果构建
	 * @param result
	 * @param resultCode
	 * @param message
	 * @return
	 */
	public JSONObject toJSONResult(final JSONObject result, AppResultCodeEnum resultCode, String message) {
		return jckFormService.toJSONResult(result, resultCode, message);
	}
	/**
	 * APP返回结果构建
	 * @param json
	 * @param baseResult
	 * @param extraTrueMsg
	 * @param extraFalseMsg
	 * @return
	 */
	protected JSONObject toJSONResult(JSONObject json, ResultBase baseResult,
			String extraTrueMsg, String extraFalseMsg) {
		return jckFormService.toJSONResult(json, baseResult, extraTrueMsg, extraFalseMsg);
	}

	public JSONObject toJSONResult(AppResultCodeEnum resultCode, String message) {
		return jckFormService.toJSONResult(resultCode, message);
	}

	/**
	 * ResultBase 转换为 JSONObject Result
	 * 
	 * @param baseResult
	 * @param extraTrueMsg
	 *            格外指定成功消息
	 * @param extraFalseMsg
	 *            格外指定失败消息
	 * @return
	 */
	protected JSONObject toJSONResult(ResultBase baseResult,
			String extraTrueMsg, String extraFalseMsg) {
		return this.jckFormService.toJSONResult(baseResult, extraTrueMsg, extraFalseMsg);
	}

	/**
	 * ResultBase 转换为 JSONObject Result
	 * 
	 * @param baseResult
	 * @param extraTrueMsg
	 *            格外指定成功消息
	 * @param extraFalseMsg
	 *            格外指定失败消息
	 * @return
	 */
	protected JSONObject toJSONResult(ResultBase baseResult) {
		return this.jckFormService.toJSONResult(baseResult);
	}

	
	/**
	 * 将登陆的信息设置到Order
	 * @param sessionLocal
	 * @param order
	 */
	protected void setSessionLocalInfo2Order(FormOrderBase order) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			order.setUserId(sessionLocal.getUserId());
			order.setUserAccount(sessionLocal.getUserName());
			order.setUserName(sessionLocal.getRealName());
			order.setUserIp(sessionLocal.getRemoteAddr());
			
			UserDetailInfo userInfo = sessionLocal.getUserDetailInfo();
			if (userInfo != null) {
				order.setUserMobile(userInfo.getMobile());
				order.setUserEmail(userInfo.getEmail());
				Org dept = userInfo.getPrimaryOrg();
				if (dept != null) {
					order.setDeptId(dept.getId());
					order.setDeptCode(dept.getCode());
					order.setDeptName(dept.getName());
					order.setDeptPath(dept.getPath());
					order.setDeptPathName(dept.getPathName());
				}
			}
		}
	}
	
	/**
	 * 将登陆的信息设置到Order
	 * @param sessionLocal
	 * @param order
	 */
	protected void setSessionLocalInfo2Order(SimpleFormOrder order) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			order.setUserId(sessionLocal.getUserId());
			order.setUserAccount(sessionLocal.getUserName());
			order.setUserName(sessionLocal.getRealName());
			order.setUserIp(sessionLocal.getRemoteAddr());
			
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (userInfo != null) {
				order.setUserMobile(userInfo.getMoblie());
				order.setUserEmail(userInfo.getEmail());
				SysOrg dept = sessionLocal.getUserInfo().getDept();
				if (dept != null) {
					order.setDeptId(dept.getOrgId());
					order.setDeptCode(dept.getCode());
					order.setDeptName(dept.getOrgName());
					order.setDeptPath(dept.getPath());
					order.setDeptPathName(dept.getOrgPathname());
				}
			}
		}
	}
	
	/**
	 * 将登陆的信息设置到Order
	 * @param order
	 */
	protected void setSessionLocalInfo2Order(ProcessOrder order) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			order.setUserId(sessionLocal.getUserId());
			order.setUserAccount(sessionLocal.getUserName());
			order.setUserName(sessionLocal.getRealName());
			order.setUserIp(sessionLocal.getRemoteAddr());
		}
	}
	
	/**
	 * 将登陆的信息设置到Order
	 * @param order
	 */
	protected void setSessionLocalInfo2Order(QueryProjectBase order) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (hasAllDataPermission()) {//拥有所有数据权限
				order.setLoginUserId(0);
				order.setDeptIdList(null);
			} else if (hasPrincipalDataPermission() && userInfo != null) { //拥有所负责的数据权限，按照部门维度查询数据
				order.setLoginUserId(0);
				order.setDeptIdList(userInfo.getDeptIdList());
			} else { //按照人员维度查询数据
				order.setLoginUserId(sessionLocal.getUserId());
				order.setDeptIdList(null);
			}
			//查看草稿的人员
			order.setDraftUserId(sessionLocal.getUserId());
		}
	}
	
	/**
	 * 将登陆的信息设置到Order
	 * @param order
	 */
	protected void setSessionLocalInfo2Order(QueryFormBase order) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (hasAllDataPermission()) {//拥有所有数据权限
				order.setLoginUserId(0);
				order.setDeptIdList(null);
			} else if (hasPrincipalDataPermission() && userInfo != null) { //拥有所负责的数据权限，按照部门维度查询数据
				order.setLoginUserId(0);
				order.setDeptIdList(userInfo.getDeptIdList());
			} else { //按照人员维度查询数据
				order.setLoginUserId(sessionLocal.getUserId());
				order.setDeptIdList(null);
			}
			//查看草稿的人员
			order.setDraftUserId(sessionLocal.getUserId());
		}
	}
	
	/**
	 * 将登陆的信息设置到Order
	 * @param order
	 */
	protected void setSessionLocalInfo2Order(QueryPermissionPageBase order) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (hasAllDataPermission()) {//拥有所有数据权限
				order.setLoginUserId(0);
				order.setDeptIdList(null);
			} else if (hasPrincipalDataPermission() && userInfo != null) { //拥有所负责的数据权限，按照部门维度查询数据
				order.setLoginUserId(0);
				order.setDeptIdList(userInfo.getDeptIdList());
			} else { //按照人员维度查询数据
				order.setLoginUserId(sessionLocal.getUserId());
				order.setDeptIdList(null);
			}
			//查看草稿的人员
			order.setDraftUserId(sessionLocal.getUserId());
		}
	}
	
	/**
	 * 将登陆的信息设置到Order
	 * @param sessionLocal
	 * @param order
	 */
	protected void setSessionLocalInfo2Order(ReportOrder order) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			order.setOperatorId(sessionLocal.getUserId());
			order.setOperatorAccount(sessionLocal.getUserName());
			order.setOperatorName(sessionLocal.getRealName());
			//			order.setUserIp(sessionLocal.getRemoteAddr());
			
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (userInfo != null) {
				//				order.setUserMobile(userInfo.getMoblie());
				//				order.setUserEmail(userInfo.getEmail());
				SysOrg dept = sessionLocal.getUserInfo().getDept();
				if (dept != null) {
					order.setDeptId(dept.getOrgId());
					order.setDeptCode(dept.getCode());
					order.setDeptName(dept.getOrgName());
					order.setDeptPath(dept.getPath());
					order.setDeptPathName(dept.getOrgPathname());
				}
			}
		}
	}

	/**
	 * 是否业务经理
	 * 
	 * @return
	 */
	protected boolean isBusiManager() {
		return DataPermissionUtil.isBusiManager();

	}
	
	/**
	 * 是否项目业务经理
	 * @param projectCode
	 * @return
	 */
	public static boolean isBusiManager(String projectCode) {
		return DataPermissionUtil.isBusiManager(projectCode);
	}

	/**
	 * 是否风险经理
	 * 
	 * @return
	 */
	protected boolean isRiskManager() {
		return DataPermissionUtil.isRiskManager();

	}

	/**
	 * 是否风控秘书
	 * 
	 * @return
	 */
	protected boolean isRiskSecretary() {
		return DataPermissionUtil.isRiskSecretary();

	}

	/**
	 * 是否总经理秘书
	 * 
	 * @return
	 */
	protected boolean isManagerSecretary() {
		return DataPermissionUtil.isManagerSecretary();

	}

	/**
	 * 是否信汇总经理秘书
	 * 
	 * @return
	 */
	protected boolean isManagerSecretaryXH() {
		return DataPermissionUtil.isManagerSecretaryXH();

	}

	/**
	 * 是否法务经理
	 * 
	 * @return
	 */
	protected boolean isLegalManager() {
		return DataPermissionUtil.isLegalManager();
	}

	/**
	 * 是否财务人员
	 * 
	 * @return
	 */
	protected boolean isFinancialPersonnel() {
		return DataPermissionUtil.isFinancialPersonnel();
	}

	/**
	 * 是否信惠人员
	 * 
	 * @return
	 */
	protected boolean isXinHuiPersonnel() {
		return DataPermissionUtil.isXinHuiPersonnel();
	}

	/**
	 * 是否业务总监
	 * 
	 * @return
	 */
	protected boolean isBusinessDirector() {
		return DataPermissionUtil.isBusiFZR();
	}

	/**
	 * 是否拥有所有数据权限
	 * 
	 * @return
	 */
	protected boolean hasAllDataPermission() {
		return DataPermissionUtil.hasAllDataPermission();
	}

	/**
	 * 是拥有所负责数据权限
	 * 
	 * @return
	 */
	protected boolean hasPrincipalDataPermission() {
		return DataPermissionUtil.hasPrincipalDataPermission();
	}

	/**
	 * 业务类型是否是承销
	 * 
	 * @param busiType
	 * @return 是：true
	 */
	protected boolean isUnderwriting(String busiType) {
		return ProjectUtil.isUnderwriting(busiType);
	}

	/**
	 * 业务类型是否是诉讼保函
	 * 
	 * @param busiType
	 * @return 是：true
	 */
	protected boolean isLitigation(String busiType) {
		return ProjectUtil.isLitigation(busiType);

	}

	/**
	 * 业务类型是否发债
	 * 
	 * @param busiType
	 * @return
	 */
	protected boolean isBond(String busiType) {
		return ProjectUtil.isBond(busiType);

	}

	/**
	 * 业务类型是否委贷
	 * 
	 * @param busiType
	 * @return
	 */
	protected boolean isEntrusted(String busiType) {
		return ProjectUtil.isEntrusted(busiType);
	}


	/**
	 * 添加附件
	 * @param keyId
	 * @param requestJson
	 * @param types
	 * @return
	 */
	protected FcsBaseResult addAttachfile(String keyId, JSONObject requestJson,
											String projectCode, String remark,
											CommonAttachmentTypeEnum... types) {
		FcsBaseResult result = new FcsBaseResult();
		if (null == types || types.length <= 0) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
			result.setMessage("没有附件数据");
			return result;
		}
		
		long uploaderId = 0L;
		String uploaderAccount = "";
		String uploaderName = "";
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			uploaderId = sessionLocal.getUserId();
			uploaderAccount = sessionLocal.getUserName();
			uploaderName = sessionLocal.getRealName();
		}
		
		List<CommonAttachmentOrder> orders = new ArrayList<CommonAttachmentOrder>();
		//先删除，再保存
		commonAttachmentServiceClient.deleteByBizNoModuleType(keyId, types);
		
		for (CommonAttachmentTypeEnum type : types) {
			if (null == type) {
				continue;
			}
			String pathValues = requestJson.getString("pathName_" + type.code());
			if (StringUtil.isNotBlank(pathValues)) {
				String[] attachPaths = pathValues.split(";");
				int j = 1;
				for (String path : attachPaths) {
					String paths[] = path.split(",");
					if (null != paths && paths.length >= 3) {
						CommonAttachmentOrder commonAttachmentOrder = new CommonAttachmentOrder();
						commonAttachmentOrder.setUploaderId(uploaderId);
						commonAttachmentOrder.setUploaderAccount(uploaderAccount);
						commonAttachmentOrder.setUploaderName(uploaderName);
						commonAttachmentOrder.setBizNo(keyId);
						commonAttachmentOrder.setModuleType(type);
						commonAttachmentOrder.setIsort(j++);
						commonAttachmentOrder.setFileName(paths[0]);
						commonAttachmentOrder.setFilePhysicalPath(paths[1]);
						commonAttachmentOrder.setRequestPath(paths[2]);
						commonAttachmentOrder.setCheckStatus(CheckStatusEnum.CHECK_PASS.code());
						if (StringUtil.isBlank(projectCode)) {
							projectCode = requestJson.getString("projectCode");
						}
						if (StringUtil.isBlank(projectCode)) {
							projectCode = requestJson.getString("relatedProjectCode");
						}
						commonAttachmentOrder.setProjectCode(projectCode);
						commonAttachmentOrder.setRemark(remark);
						orders.add(commonAttachmentOrder);
					}
				}
			}
		}
		
		if (ListUtil.isNotEmpty(orders)) {
			return commonAttachmentServiceClient.insertAll(orders);
		} else {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
			result.setMessage("没有附件数据");
			return result;
		}
	}
	
	
	/**
	 * 添加附件
	 * @param keyId
	 * @param childId
	 * @param request
	 * @param types
	 * @return
	 */
	protected FcsBaseResult addAttachfile(String keyId, String childId, HttpServletRequest request,
											String projectCode, String remark,
											CommonAttachmentTypeEnum... types) {
		FcsBaseResult result = new FcsBaseResult();
		if (null == types || types.length <= 0) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
			result.setMessage("没有附件数据");
			return result;
		}
		
		long uploaderId = 0L;
		String uploaderAccount = "";
		String uploaderName = "";
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			uploaderId = sessionLocal.getUserId();
			uploaderAccount = sessionLocal.getUserName();
			uploaderName = sessionLocal.getRealName();
		}
		
		List<CommonAttachmentOrder> orders = new ArrayList<CommonAttachmentOrder>();
		//先删除，再保存
		commonAttachmentServiceClient.deleteByBizNoAndChildIdModuleType(keyId, childId, types);
		
		for (CommonAttachmentTypeEnum type : types) {
			if (null == type) {
				continue;
			}
			String pathValues = request.getParameter("pathName_" + type.code());
			if (StringUtil.isNotBlank(pathValues)) {
				String[] attachPaths = pathValues.split(";");
				int j = 1;
				for (String path : attachPaths) {
					String paths[] = path.split(",");
					if (null != paths && paths.length >= 3) {
						CommonAttachmentOrder commonAttachmentOrder = new CommonAttachmentOrder();
						commonAttachmentOrder.setUploaderId(uploaderId);
						commonAttachmentOrder.setUploaderAccount(uploaderAccount);
						commonAttachmentOrder.setUploaderName(uploaderName);
						commonAttachmentOrder.setBizNo(keyId);
						commonAttachmentOrder.setChildId(childId);
						commonAttachmentOrder.setModuleType(type);
						commonAttachmentOrder.setIsort(j++);
						commonAttachmentOrder.setFileName(paths[0]);
						commonAttachmentOrder.setFilePhysicalPath(paths[1]);
						commonAttachmentOrder.setRequestPath(paths[2]);
						commonAttachmentOrder.setCheckStatus(CheckStatusEnum.CHECK_PASS.code());
						if (StringUtil.isBlank(projectCode)) {
							projectCode = request.getParameter("projectCode");
						}
						if (StringUtil.isBlank(projectCode)) {
							projectCode = request.getParameter("relatedProjectCode");
						}
						commonAttachmentOrder.setProjectCode(projectCode);
						commonAttachmentOrder.setRemark(remark);
						orders.add(commonAttachmentOrder);
					}
				}
			}
		}
		
		if (ListUtil.isNotEmpty(orders)) {
			return commonAttachmentServiceClient.insertAll(orders);
		} else {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
			result.setMessage("没有附件数据");
			return result;
		}
	}

	/**
	 * 构建跳转地址
	 * 
	 * @param request
	 * @param model
	 */
	protected void buildSystemNameDefaultUrl(HttpServletRequest request,
			Model model) {
		Map<String, String> params = WebUtil.getRequestMap(request);
		if (params.containsKey("systemNameDefautUrl")) {
			int index = 0;
			String systemNameDefautUrl = params.get("systemNameDefautUrl");
			if (StringUtil.isNotBlank(systemNameDefautUrl)) {
				for (String pName : params.keySet()) {
					if ("systemNameDefautUrl".equals(pName))
						continue;
					String pValue = params.get(pName);
					if (StringUtil.isNotBlank(pValue)) {
						if (index == 0
								&& systemNameDefautUrl.indexOf("?") == -1) {
							systemNameDefautUrl += "?" + pName + "=" + pValue;
						} else {
							systemNameDefautUrl += "&" + pName + "=" + pValue;
						}
						index++;
					}
				}
			}
			params.put("systemNameDefautUrl", systemNameDefautUrl);
		}
		model.addAllAttributes(params);
	}

	/**
	 * 验证登录状态
	 * 
	 * @param json
	 *            失效提示信息
	 * @return 未登录或失效返回true
	 */
	protected boolean isLoginExpire(JSONObject json) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (null == sessionLocal) {
			json.put("success", false);
			json.put("message", "您未登陆或登陆已失效");
			return true;
		}
		return false;
	}
	
	public String getLoginName(){
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (null == sessionLocal) {
			throw new BornApiException("会话失效,请重新登陆");
		}
		return sessionLocal.getUserName();
	}
	
	public SessionLocal getLocalSession(){
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (null == sessionLocal) {
			throw new BornApiException("会话失效,请重新登陆");
		}
		return sessionLocal;
	}

	protected boolean isNomalProject(JSONObject json, ProjectFormOrderBase order) {
		if (null != order && StringUtil.isNotBlank(order.getProjectCode())) {
			ProjectInfo project = projectServiceClient.queryByCode(
					order.getProjectCode(), false);
			if (null != project
					&& ProjectStatusEnum.NORMAL == project.getStatus()) {
				return true;
			}

			json.put("success", false);
			json.put("message", "该项目不能修改");
			logger.error("该项目不能修改：" + order);
			return false;
		}
		return true;
	}

	/**
	 * 返回和子系统匹配的表单客户端
	 * @param request
	 * @return
	 */
	protected FormService getSystemMatchedFormServiceByModule(String module) {
		return jckFormService.getSystemMatchedFormServiceByModule(ModuleEnum.getByCode(module));
	}

	/**
	 * 
	 * 获取近几年的年份值:2016,2015...
	 * 
	 * @param n
	 * @return
	 */
	protected int[] getYears(int n) {
		int[] years = new int[n];
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int currentYear = c.get(Calendar.YEAR);
		for (int y = currentYear, i = 0; i < n; i++) {
			years[i] = y--;
		}
		return years;
	}

	protected void returnJson(HttpServletResponse response, boolean isIE,
			JSONObject jsonobj) throws IOException {
		response.reset();
		if (isIE) {
			response.setHeader("ContentType", "text/html");
			response.setContentType("text/html");
		} else {
			response.setHeader("ContentType", "application/json");
			response.setContentType("application/json");
		}

		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonobj.toJSONString());
	}
	
	/**
	 * 将json流映射到对象
	 * @param request
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public <T> T parseStreamToObject(HttpServletRequest request,Class<T> clazz)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		StringBuffer sb = new StringBuffer();
		String line = null;
		while((line=reader.readLine())!=null){
			sb.append(line);
		}
		logger.info("json入参={}",sb.toString());
		T order = JsonParseUtil.parseObject(sb.toString(), clazz);
		logger.info("解析order={}",ToStringBuilder.reflectionToString(order,
						ToStringStyle.SHORT_PREFIX_STYLE)
			);
		return order;
	}
	
	/**
	 * 将json流映射到对象
	 * @param request
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public JSONObject parseStreamToJson(HttpServletRequest request)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		StringBuffer sb = new StringBuffer();
		String line = null;
		while((line=reader.readLine())!=null){
			sb.append(line);
		}
		logger.info("json入参={}",sb.toString());
		JSONObject json = JSONObject.parseObject(sb.toString());
		logger.info("json入参={}",sb.toString());
		return json;
	}
	
	/**
	 * 将json流映射到对象
	 * @param request
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public String parseStreamToString(HttpServletRequest request)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		StringBuffer sb = new StringBuffer();
		String line = null;
		while((line=reader.readLine())!=null){
			sb.append(line);
		}
		logger.info("json入参={}",sb.toString());
		return sb.toString();
	}

	/**
	 * AES 解密
	 * 
	 * @param encryptStr
	 * @return
	 */
	public String decryptAes(String encryptStr) {
		try {
			return AesUtil.decrypt(encryptStr,
					getConfigValue(ApiSystemParamEnum.App_Secrity_Key));
		} catch (Exception e) {
			logger.error("解密失败:", e);
			throw new BornApiException("解密失败:" + e.getMessage());
		}
	}

	/** 客户系统加载需要的枚举 */
	protected void setCustomerEnums(Model model) {
		// 评级审核状态
		model.addAttribute("auditStatusEnum", AuditStatusEnum.getAllEnum());
		// 证件类型
		model.addAttribute("certTypesEnum", CertTypesEnum.getAllEnum());
		// 公民类型
		model.addAttribute("citizenTypeEnum", CitizenTypeEnum.getAllEnum());
		// 客户类型
		model.addAttribute("customerTypeEnum", CustomerTypeEnum.getAllEnum());
		// 客户与我公司关系
		model.addAttribute("customerRelationEnum",
				CustomerRelationEnum.getAllEnum());
		// 客户来源
		model.addAttribute("customerSourceEnum",
				CustomerSourceEnum.getAllEnum());
		// 企业性质
		model.addAttribute("enterpriseNatureEnum",
				EnterpriseNatureEnum.getAllEnum());
		// 企业规模
		model.addAttribute("enterpriseScaleEnum",
				EnterpriseScaleEnum.getAllEnum());

		// 婚姻状况
		model.addAttribute("maritalStatusEnum", MaritalStatusEnum.getAllEnum());
		// 性别
		model.addAttribute("sexEnum", SexEnum.getAllEnum());
		// 客户来源
		model.addAttribute("customerSourceEnum",
				CustomerSourceEnum.getAllEnum());
		// 渠道分类
		model.addAttribute("chanalTypeEnum", ChanalTypeEnum.getAllEnum());
		// 金融机构属性
		model.addAttribute("financialInstitutionsEnum",
				FinancialInstitutionsEnum.getAllEnum());

		// 审核状态枚举
		model.addAttribute("auditStatusEnum", AuditStatusEnum.getAllEnum());
		// 评价等级枚举
		model.addAttribute("evaluetingLevelEnum",
				EvaluetingLevelEnum.getAllEnum());
		// 比较枚举
		model.addAttribute("comparEnums", ComparEnums.getAllEnum());

	}
	
	/**
	 * 显示项目批复侧边栏
	 * @param projectCode
	 */
	protected String showProjectApproval(String projectCode) {
		ProjectInfo projectInfo = projectServiceClient.queryByCode(projectCode, false);
		if (projectInfo != null && projectInfo.getIsApproval() == BooleanEnum.IS) {
			return projectInfo.getSpCode();
		}else{
			return null;
		}
	}
	
	/** 被监控名录同步接口 */
	public BornSynResultBase synWatchList(CustomerBaseOrder customerBaseOrder) {
		try {
			SynWatchListOrder order = new SynWatchListOrder();
			List<WatchListInfo> list = new ArrayList<>();
			WatchListInfo info = new WatchListInfo();
			info.setCustomName(customerBaseOrder.getCustomerName());
			info.setOperator(ShiroSessionUtils.getSessionLocal().getUserName());
			if (CustomerTypeEnum.PERSIONAL.code().equals(customerBaseOrder.getCustomerType())) {
				info.setUserType(UserTypeEnum.PERSONAL);
				info.setLicenseNo(customerBaseOrder.getCertNo());
			} else {
				info.setUserType(UserTypeEnum.BUSINESS);
				info.setLicenseNo(customerBaseOrder.getBusiLicenseNo());
			}
			list.add(info);
			order.setList(list);
			order.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
			BornSynResultBase result = riskSystemFacadeApi.synWatchList(order);
			logger.info("被监控名录同步接口：同步客户={},result={}", info, result);
		} catch (Exception e) {
			logger.info("被监控名录同步接口异常：", e);
			
		}
		return null;
		
	}
	
	protected int getVersion(HttpServletRequest request) {
		return NumberUtil.parseInt(request.getParameter(BornApiConstants.VERSION));
	}
	
	/** 风险验证消息保存 */
	public void verifyMsgSave(HttpServletRequest request, String errorKey) {
		VerifyMessageOrder order = new VerifyMessageOrder();
		try {
			String[] messages = request.getParameterValues("messageInfo");
			if(messages!=null){
				String message = "";
				int i = 1;
				for (String s : messages) {
					if (i == 1) {
						message += s;
						i++;
					} else {
						message += "&" + s;
					}
					
				}
				order.setCardMessage(message);
				order.setErrorKey(errorKey);
				FcsBaseResult result = verifyMessageSaveClient.save(order);
				logger.info("风险验证消息保存结果：order={},result={}", order, result);
			}else{
				logger.info("未找到风险验证消息,不予保存");
			}
		} catch (Exception e) {
			logger.info("风险验证消息保存结果：order={},e:", order, e);
		}
		
	}
	
	public static String getTextMsg(String content){
		try {
			if(StringUtil.isBlank(content)){
				return "";
			}
			Parser parser = new Parser();
			parser.setInputHTML(content);
			StringBean bean=new StringBean();
			bean.setLinks(false);
			bean.setReplaceNonBreakingSpaces(true);
			bean.setCollapse(true);
			parser.visitAllNodesWith(bean);
			parser.reset();
			content =  bean.getStrings();
			int lineS = content.indexOf(lineSeperate);
			if(lineS>-1){
				content = content.substring(0, lineS);
			}
			lineS = content.indexOf("|");
			if(lineS>-1){
				content = content.substring(0, lineS);
			}
		} catch (Exception e1) {
			logger.error("解析消息失败",e1);
		}
		return content;
	}
	
	protected JSONObject makePage(
			QueryBaseBatchResult<?> councilProjects) {
		JSONObject page = new JSONObject();
		page.put("totalCount", councilProjects.getTotalCount());
		page.put("totalPageCount", councilProjects.getPageCount());
		page.put("currentPageNo", councilProjects.getPageNumber());
		return page;
	}
	
	/** 查看客户权限设置 */
	protected void queryCustomerPermissionSet(QueryPermissionPageBase order) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (hasAllDataPermission() || DataPermissionUtil.isBelong2Xinhui()) {//拥有所有数据权限
				order.setLoginUserId(0);
				order.setDeptIdList(null);
			} else if (DataPermissionUtil.isBusinessFgfz()
						|| DataPermissionUtil.isXinHuiFGFZ(sessionLocal.getUserDetailInfo())
						|| DataPermissionUtil.isBusiFZR() || DataPermissionUtil.isXinHuiFzr()) { //分管副总和总监
				order.setLoginUserId(0);
				order.setDeptIdList(userInfo.getDeptIdList());
			} else { //按照人员维度查询数据
				order.setLoginUserId(sessionLocal.getUserId());
				order.setDeptIdList(null);
			}
			
		}
	}
	
	protected JSONObject makePage(final JSONObject result,
			QueryBaseBatchResult<?> councilProjects) {
		JSONObject page = new JSONObject();
		page.put("totalCount", councilProjects.getTotalCount());
		page.put("totalPageCount", councilProjects.getPageCount());
		page.put("currentPageNo", councilProjects.getPageNumber());
		result.put("page", page);
		return page;
	}
	
	/**
	 * @param deptId
	 * @param deptCode
	 * @param result  为空则重新创建
	 * @return
	 */
	public JSONObject getDeptHead(Long deptId, String deptCode,  JSONObject result) {
		if(result==null){
			result = new JSONObject();
		}
		Org org = null;
		if (deptId != null && deptId > 0) {
			org = bpmUserQueryService.findDeptByOrgId(deptId);
		} else if (StringUtil.isNotEmpty(deptCode)) {
			org = bpmUserQueryService.findDeptByOrgCode(deptCode);
		}
		
		List<SimpleUserInfo> fzr = null;
		if (org != null)
			fzr = projectRelatedUserServiceClient.getDeptRoleUser(org.getCode(),
				sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code()));
		
		if (fzr != null && fzr.size() > 0) {
			SimpleUserInfo userInfo = fzr.get(0);
			result.put("id", userInfo.getUserId());
			result.put("name", userInfo.getUserName());
			result.put("account", userInfo.getUserAccount());
			result.put("mobile", userInfo.getMobile());
			result.put("email", userInfo.getEmail());
		} else {
			result.put("id", "");
			result.put("name", "");
			result.put("account", "");
			result.put("mobile", "");
			result.put("email", "");
		}
		return result;
	}
	
	/**
	 * 通过异常传递信息
	 * @param customerType
	 * @param certNo
	 * @param customerName
	 */
	public void checkExist(CustomerTypeEnum customerType, String certNo,String customerName) 
			throws BornApiException{
		//校验是否存在
		ValidateCustomerResult result = null;
		
		if(CustomerTypeEnum.PERSIONAL.equals(customerType)){
			result = personalCustomerClient.ValidateCustomer(certNo,
					customerName, ShiroSessionUtils.getSessionLocal().getUserId());
		}else if(CustomerTypeEnum.ENTERPRISE.equals(customerType)){
			result = companyCustomerClient.ValidateCustomer(certNo,
					customerName, ShiroSessionUtils.getSessionLocal().getUserId());
		}else{
			throw new BornApiException("用户类型不正确");
		}
		if (result != null) {
			boolean success = result.isSuccess() && StringUtil.isBlank(result.getType());
			if(!success){
				throw new BornApiException(result.getMessage());
			}
		}else{
			throw new BornApiException("客户信息校验失败");
		}
	}
	
	public boolean checkBoolean(String boolStr){
		return BooleanEnum.YES.code().equals(boolStr)||BooleanEnum.IS.code().equals(boolStr);
	}
}
