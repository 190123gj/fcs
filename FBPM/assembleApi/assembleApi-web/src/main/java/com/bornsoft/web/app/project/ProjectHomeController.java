package com.bornsoft.web.app.project;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Maps;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.common.TransferProjectOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.bornsoft.api.service.app.DataPermissionUtil;
import com.bornsoft.utils.constants.ApiSystemParamEnum;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.AppUtils;
import com.bornsoft.utils.tool.GsonUtil;
import com.bornsoft.web.app.base.WorkflowBaseController;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

@Controller
@RequestMapping("projectMg")
public class ProjectHomeController extends WorkflowBaseController {
	
	final static String vm_path = "/projectMg/";
	
	static{
		GsonUtil.builder
		.registerTypeAdapter(ProjectPhasesEnum.class, new TypeAdapter<ProjectPhasesEnum>(){

			@Override
			public ProjectPhasesEnum read(JsonReader reader) throws IOException {
				if (reader.peek() == JsonToken.NULL) {
					reader.nextNull();
					return null;
				}else{
					return ProjectPhasesEnum.getByCode(reader.nextString());
				}
			}

			@Override
			public void write(JsonWriter writer, ProjectPhasesEnum value) throws IOException {
				if (value == null) {
					writer.nullValue();
					return;
				}else{
					writer.value(value.message());
				}
			}
		});

	}
	
	@RequestMapping("pause.json")
	@ResponseBody
	public JSONObject pause(String projectCode, HttpServletRequest request) {
		JSONObject result = null;
		try {
			if (DataPermissionUtil.isBusiManager(projectCode)) {
				FcsBaseResult pauseResult = projectServiceClient.pauseProject(projectCode);
				result = toJSONResult(pauseResult);
			} else {
				result = toJSONResult(AppResultCodeEnum.FAILED, "业务经理才能暂缓项目");
			}
		} catch (Exception e) {
			result = toJSONResult(AppResultCodeEnum.FAILED, "暂缓出错：" + e.getMessage());
			logger.error("项目暂缓出错：{}", e);
		}
		return result;
	}
	
	@RequestMapping("getTransferUserType.json")
	@ResponseBody
	public JSONObject getTransferUserType(){
		JSONObject result = new JSONObject();
		try {
			// 是否业务总监
			boolean isBusiDirector = DataPermissionUtil.isBusiFZR();
			// 是否风险总监
			boolean isRiskDirector = DataPermissionUtil.isRiskFZR();
			// 是否系统管理员
			boolean isAdmin = DataPermissionUtil.isSystemAdministrator();
			JSONArray btnList = new JSONArray();
			JSONObject e = null;
			if (isBusiDirector || isAdmin) {
				e = new JSONObject();
				e.put("value", "BUSI_MANAGER");
				e.put("name", "客户经理");
				btnList.add(e);
			}
			if (isRiskDirector || isAdmin) {
				e = new JSONObject();
				e.put("value", "RISK_MANAGER");
				e.put("name", "风险经理");
				btnList.add(e);
			}
			result.put("btnList", btnList);
			return result = toJSONResult(AppResultCodeEnum.SUCCESS, "");
		} catch (Exception e) {
			logger.error("项目移交出错：{}", e);
			result = toJSONResult(AppResultCodeEnum.FAILED, "项目移交出错：" + e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("transfer.json")
	@ResponseBody
	public JSONObject transfer(TransferProjectOrder order, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			setSessionLocalInfo2Order(order);
			FcsBaseResult pauseResult = projectServiceClient.transferProject(order);
			return result = toJSONResult(pauseResult);
		} catch (Exception e) {
			logger.error("项目移交出错：{}", e);
			result = toJSONResult(AppResultCodeEnum.FAILED, "项目移交出错：" + e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("restart.json")
	@ResponseBody
	public JSONObject restart(String projectCode, HttpServletRequest request, Model model) {
		JSONObject result = new JSONObject();
		try {
			if (DataPermissionUtil.isBusiManager(projectCode)) {
				FcsBaseResult pauseResult = projectServiceClient.restartProject(projectCode);
				result = toJSONResult(pauseResult);
			} else {
				result = toJSONResult(AppResultCodeEnum.FAILED, "业务经理才能重启项目");
			}
		} catch (Exception e) {
			logger.error("项目重启出错：{}", e);
			result = toJSONResult(AppResultCodeEnum.FAILED, "重启出错：" + e.getMessage());
		}
		return result;
	}
	

	@ResponseBody
	@RequestMapping("list.json")
	public  Object list(HttpServletRequest request,ProjectQueryOrder order) {
		
		String url = sysParameterServiceClient.getSysParameterValue(ApiSystemParamEnum.FACE_URL.code());
		if(StringUtil.isBlank(url)){
			throw new BornApiException("未配置face访问地址");
		}
		String likeCodeOrName = request.getParameter("likeCodeOrName");
		order.setProjectCodeOrName(likeCodeOrName);
		setSessionLocalInfo2Order(order);
		//是否业务总监
		boolean isBusiDirector = DataPermissionUtil.isBusiFZR();
		//是否风险总监
		boolean isRiskDirector = DataPermissionUtil.isRiskFZR();
		//是否系统管理员
		boolean isAdmin = DataPermissionUtil.isSystemAdministrator();
		if (StringUtil.isBlank(order.getSortCol())) {
			order.setSortCol("project_id");
			order.setSortOrder("DESC");
		}
		
		QueryBaseBatchResult<ProjectInfo> pList = projectServiceClient.queryProject(order);
		JSONObject page = makePage(pList);
		JSONArray pageList = new JSONArray();
		page.put("result", pageList);
		String token = getAccessToken();
		for(ProjectInfo info : pList.getPageList()){
			JSONObject entity = new JSONObject();
			pageList.add(entity);
			entity.put("projectId", info.getProjectId());
			entity.put("projectCode", info.getProjectCode());
			entity.put("projectName", info.getProjectName());
			entity.put("busiManagerName", info.getBusiManagerName());
			entity.put("busiTypeName", info.getBusiTypeName());
			entity.put("phases", info.getPhases()!=null?info.getPhases().message():"");
			entity.put("amount", AppUtils.toString(info.getAmount()));
			entity.put("timeLimit", info.getTimeLimit());
			entity.put("timeUnit", info.getTimeUnit()!=null?info.getTimeUnit().message():"");
			entity.put("busiTypeName", info.getBusiTypeName());
			entity.put("status", info.getStatus()!=null?info.getStatus().message():"");
			entity.put("customerName", info.getCustomerName());
			entity.put("interestRate", info.getGuaranteeFeeString());
			String tmpUrl = url+"/projectMg/viewProjectAllMessage.htm?projectCode="+info.getProjectCode()+"&accessToken="+token;
			entity.put("url", tmpUrl);
			if(DataPermissionUtil.isBusiManager(info.getProjectCode())){
				if(info.getStatus() == ProjectStatusEnum.NORMAL &&(info.getPhases() == ProjectPhasesEnum.SET_UP_PHASES||
						info.getPhases() == ProjectPhasesEnum.INVESTIGATING_PHASES||
						info.getPhases() == ProjectPhasesEnum.COUNCIL_PHASES||
//						info.getPhases() == ProjectPhasesEnum.RE_COUNCIL_PHASES
						info.getPhases() == ProjectPhasesEnum.CONTRACT_PHASES||
						info.getPhases() == ProjectPhasesEnum.FUND_RAISING_PHASES
						)){
					entity.put("operateStatus", "pause");
				}else if(info.getStatus() == ProjectStatusEnum.PAUSE){
					entity.put("operateStatus", "restart");
				}
			}
		}
		Map<String, String> riskManagerMap = Maps.newHashMap();
		if (pList != null) {
			for (ProjectInfo project : pList.getPageList()) {
				//查询风险经理
				if ((isRiskDirector || isAdmin)) {
					ProjectRelatedUserInfo relatedUser = projectRelatedUserServiceClient
						.getRiskManager(project.getProjectCode());
					if (relatedUser != null) {
						riskManagerMap.put(project.getProjectCode(), relatedUser.getUserName());
					}
				}
			}
		}
		for(Object e : pageList){
			JSONObject json = (JSONObject) e;
			if(isBusiDirector|| isRiskDirector||isAdmin){
				if(isRiskDirector && riskManagerMap.get(json.getString("projectCode"))!=null){
					
				}else{
					String  operate = json.getString("operateStatus");
					if(StringUtils.isBlank(operate)){
						json.put("operateStatus", "transfer");
					}else{
						json.put("operateStatus", operate+",transfer");
					}
				}
			}
		}

		//风险经理
		JSONObject result = toJSONResult(pList);
		result.put("isBusiManager", isBusiManager());
		result.put("isBusiDirector", isBusiDirector);
		result.put("isRiskDirector", isRiskDirector);
		result.put("isAdmin", isAdmin);
		result.put("page", page);
		return result;
	}
	
	
}
