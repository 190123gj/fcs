package com.born.fcs.face.web.controller.basedata;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.bpm.service.WorkflowEngineWebClient;
import com.born.fcs.face.integration.bpm.service.info.WorkflowProcessLog;
import com.born.fcs.face.web.controller.base.FrontAutowiredBaseController;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Controller
@RequestMapping("baseDataLoad")
public class WorkflowBaseDataController extends FrontAutowiredBaseController {
	
	@Autowired
	WorkflowEngineWebClient workflowEngineWebClient;
	
	@ResponseBody
	@RequestMapping("loadWorkflowProcessList.json")
	public JSONObject loadWorkflowProcessList(HttpServletRequest request, String actInstId) {
		JSONObject jsonObject = new JSONObject();
		QueryBaseBatchResult<WorkflowProcessLog> batchResult = workflowEngineWebClient
			.getProcessOpinionByActInstId(actInstId);
		jsonObject.put("success", true);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", batchResult.getPageList());
		jsonObject.put("data", map);
		return jsonObject;
	}
}
