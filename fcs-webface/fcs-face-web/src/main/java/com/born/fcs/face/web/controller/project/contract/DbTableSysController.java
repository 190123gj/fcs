package com.born.fcs.face.web.controller.project.contract;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.ws.info.contract.DbFieldInfo;
import com.born.fcs.pm.ws.info.contract.DbTableInfo;
import com.born.fcs.pm.ws.order.contract.DbFieldQueryOrder;
import com.born.fcs.pm.ws.order.contract.DbTableOrder;
import com.born.fcs.pm.ws.order.contract.DbTableQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Controller
@RequestMapping("systemMg/contract")
public class DbTableSysController extends BaseController {
	
	final static String vm_path = "/projectMg/assistSys/ContractMg/";
	
	/**
	 * 常用数据库表列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("dbTableList.htm")
	public String dbTableList(DbTableQueryOrder order, Model model) {
		QueryBaseBatchResult<DbTableInfo> batchResult = dbTableServiceClient.query(order);
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		return vm_path + "list_Database.vm";
	}
	
	/**
	 * 新增常用数据库表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("addDbTable.htm")
	public String addDbTable(HttpServletRequest request, HttpServletResponse response, Model model) {
		DbTableInfo Info = new DbTableInfo();
		model.addAttribute("info", Info);
		return vm_path + "add_Database.vm";
	}
	
	/**
	 * 保存常用数据库表
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveDbTable.htm")
	@ResponseBody
	public JSONObject saveDbTable(DbTableOrder order, Model model) {
		String tipPrefix = " 保存常用数据库表";
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			setSessionLocalInfo2Order(order);
			FcsBaseResult saveResult = dbTableServiceClient.save(order);
			jsonObject = toJSONResult(jsonObject, saveResult, "保存常用数据库表", null);
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存合同模板出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 常用数据库表详情
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("viewDbTable.htm")
	public String viewDbTable(Long id, Model model) {
		String tipPrefix = "常用数据库表详情";
		try {
			DbTableInfo info = dbTableServiceClient.findById(id);
			model.addAttribute("info", info);
			
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		return vm_path + "add_Database.vm";
	}
	
	/**
	 * 删除常用数据库表
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("deleteTable.htm")
	@ResponseBody
	public JSONObject deleteTable(Long id, Model model) {
		String tipPrefix = "删除常用数据库表";
		DbTableOrder order = new DbTableOrder();
		order.setTableId(id);
		JSONObject result = new JSONObject();
		try {
			FcsBaseResult delResult = dbTableServiceClient.deleteById(order);
			if (delResult.isSuccess()) {
				result.put("success", true);
				result.put("message", "删除成功");
			} else {
				result.put("success", false);
				result.put("message", delResult.getMessage());
			}
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		
		return result;
	}
	
	/**
	 * 检查表名是否重复
	 * 
	 * @param tableName
	 * @param model
	 * @return
	 */
	@RequestMapping("checkTableName.htm")
	@ResponseBody
	public JSONObject checkTableName(String tableName, Model model) {
		String tipPrefix = "检查表名是否重复";
		JSONObject result = new JSONObject();
		try {
			long count = dbTableServiceClient.findByName(tableName);
			if (count > 0) {
				result.put("success", true);
			} else {
				result.put("success", false);
			}
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		return result;
	}
	
	/**
	 * 选择数据库表
	 * @param order
	 * @return
	 */
	@RequestMapping("loadDBTable.json")
	@ResponseBody
	public JSONObject loadDBTable(DbTableQueryOrder order) {
		String tipPrefix = "选择数据库表";
		JSONObject result = new JSONObject();
		try {
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			QueryBaseBatchResult<DbTableInfo> batchResult = dbTableServiceClient.query(order);
			List<DbTableInfo> list = batchResult.getPageList();
			for (DbTableInfo info : list) {
				JSONObject json = new JSONObject();
				json.put("tableId", info.getTableId());
				json.put("tableName", info.getTableName());
				json.put("tableForShort", info.getTableForShort());
				json.put("remark", info.getRemark());
				json.put("className", info.getClassName());
				json.put("projectPhase", info.getProjectPhase());
				dataList.add(json);
			}
			data.put("pageCount", batchResult.getPageCount());
			data.put("pageNumber", batchResult.getPageNumber());
			data.put("pageSize", batchResult.getPageSize());
			data.put("totalCount", batchResult.getTotalCount());
			data.put("pageList", dataList);
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		
		return result;
		
	}
	
	/**
	 * 表根据表选择字段
	 * @param order
	 * @return
	 */
	@RequestMapping("loadDBFieldfromTable.json")
	@ResponseBody
	public JSONObject loadDBFieldfromTable(DbFieldQueryOrder order) {
		JSONObject json = new JSONObject();
		String tipPrefix = "选择数据库字段";
		QueryBaseBatchResult<DbFieldInfo> batchResult = dbFieldServiceClient.queryByTableId(order);
		List<DbFieldInfo> filelds = batchResult.getPageList();
		//if (ListUtil.isNotEmpty(filelds)) {
		json.put("pageList", filelds);
		//}
		json.put("pageCount", batchResult.getPageCount());
		json.put("pageNumber", batchResult.getPageNumber());
		json.put("pageSize", batchResult.getPageSize());
		json.put("totalCount", batchResult.getTotalCount());
		json = toStandardResult(json, tipPrefix);
		return json;
	}
}
