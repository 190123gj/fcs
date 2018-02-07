package com.born.fcs.face.web.controller.project;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.bpm.service.OrgService;
import com.born.fcs.face.integration.bpm.service.info.UserInfo;
import com.born.fcs.face.integration.info.OrgInfo;
import com.born.fcs.face.integration.service.BasicDataCacheService;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.ws.info.common.FinancialProductInfo;
import com.born.fcs.pm.ws.info.setup.FProjectInfo;
import com.born.fcs.pm.ws.order.basicmaintain.FinancialProductQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.basicmaintain.FinancialProductService;
import com.born.fcs.pm.ws.service.common.BasicDataService;

/**
 * 项目管理中需要权限才能访问的数据
 * @author wuzj
 */
@Controller
@RequestMapping("projectMg/basicData")
public class BasicDataController extends BaseController {
	
	@Autowired
	private BasicDataService basicDataServiceClient;
	
	@Autowired
	private BasicDataCacheService basicDataCacheService;
	
	@Autowired
	private FinancialProductService financialProductServiceClient;
	
	@Autowired
	protected OrgService orgService;
	
	/**
	 * 获取项目信息
	 * @param projectCode
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("loadFProjectData.json")
	@ResponseBody
	public JSONObject loadFProjectData(String projectCode, HttpServletRequest request,
										HttpServletResponse response, Model model) {
		String tipPrefix = "查询项目信息";
		JSONObject result = new JSONObject();
		try {
			FProjectInfo project = projectSetupServiceClient.queryProjectByCode(projectCode);
			JSONObject json = toJsonObj(project);
			result = toStandardResult(json, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		return result;
	}
	
	/**
	 * 选择理财产品
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("financialProduct.json")
	@ResponseBody
	public JSONObject financialProduct(FinancialProductQueryOrder order, Model model) {
		
		String tipPrefix = "理财产品";
		JSONObject result = new JSONObject();
		try {
			QueryBaseBatchResult<FinancialProductInfo> batchResult = financialProductServiceClient
				.query(order);
			
			if (batchResult.isSuccess()) {
				JSONObject data = new JSONObject();
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("list", batchResult.getPageList());
				result = toStandardResult(data, tipPrefix);
			} else {
				result = toStandardResult(null, tipPrefix);
			}
			
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		return result;
	}
	
	/**
	 * 获取部门/组织结构下的人员
	 * 
	 * @param deptCode
	 * @param model
	 * @return
	 */
	@RequestMapping("loadOrgMenbers.json")
	@ResponseBody
	public JSONObject loadOrgMenbersJson(String orgCode, Model model) {
		String tipPrefix = "获取部门/组织结构下的人员";
		JSONObject result = new JSONObject();
		try {
			List<UserInfo> userList = orgService.findOrgMenbersByCode(orgCode);
			result = toStandardResult(makeOrgMenbersJson(userList), tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		
		return result;
	}
	
	/**
	 * 获取部门/组织结构
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("loadOrgData.json")
	public String loadOrgJson(HttpServletRequest request, HttpServletResponse response,
								String orgCode, Model model) {
		String tipPrefix = "获取部门/组织结构";
		JSONObject jsonObject = new JSONObject();
		List<OrgInfo> orgInfos = Lists.newArrayList();
		
		OrgInfo orgInfo = orgService.findOrgInSystemByCode(orgCode);
		orgInfos.add(orgInfo);
		
		JSONObject dataObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		makeOrgJsonTree(orgInfos, jsonArray, 0);
		dataObject.put("list", jsonArray);
		
		jsonObject = toStandardResult(dataObject, tipPrefix);
		
		printHttpResponse(response, jsonObject);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private void makeOrgJsonTree(List<OrgInfo> orgInfos, JSONArray jsonArray, long index) {
		for (OrgInfo orgInfo : orgInfos) {
			orgInfo.setRank(index);
			Map<String, Object> map = MiscUtil.covertPoToMap(orgInfo);
			List<OrgInfo> childOrgInfos = (List<OrgInfo>) map.get("subOrg");
			// Collections.sort(childOrgInfos, new MenuComparator<OrgInfo>());
			JSONArray childJsonArray = new JSONArray();
			makeOrgJsonTree(childOrgInfos, childJsonArray, index + 1);
			map.put("subOrg", childOrgInfos);
			jsonArray.add(map);
		}
	}
	
	private JSONArray makeOrgMenbersJson(List<UserInfo> userList) {
		JSONArray jsonArray = new JSONArray();
		for (UserInfo user : userList) {
			JSONObject jso = new JSONObject();
			jso.put("userId", user.getUserId());
			jso.put("userName", user.getUserName());
			jso.put("realName", user.getRealName());
			jsonArray.add(jso);
		}
		return jsonArray;
	}
	
	private JSONObject toJsonObj(Object object) {
		String jsonString = JSON.toJSONString(object);
		JSONObject json = JSON.parseObject(jsonString);
		return json;
	}
}
