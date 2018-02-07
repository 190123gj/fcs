package com.born.fcs.face.web.controller.bpm;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.web.controller.project.FormController;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.enums.bpm.BpmCallBackOpTypeEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.common.BpmCallBackOrder;
import com.born.fcs.pm.ws.order.common.FormQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.FormService;
import com.yjf.common.lang.util.StringUtil;

@Controller
@RequestMapping("bpm/callback/")
public class BpmCallBackController extends FormController {
	
	/**
	 * BPM回调 actInstId，runId 至少传一个
	 * @param actInstId 流程实例ID
	 * @param runId 流程运行ID
	 * @param nodeId 节点ID（自由跳转时传值）
	 * @param opType 操作类型 BACK2START/END/OTHER 驳回到发起人/终止/其他
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("doback.json")
	@ResponseBody
	public Object doback(String actInstId, String runId, String nodeId, String opType,
							HttpServletRequest request, Model model) {
		
		JSONObject jsonObject = new JSONObject();
		try {
			
			long insId = 0;
			if (StringUtil.isNotBlank(actInstId))
				NumberUtil.parseLong(actInstId);
			
			long rId = 0;
			if (StringUtil.isNotBlank(runId))
				rId = NumberUtil.parseLong(runId);
			
			if (insId > 0 || rId > 0) {
				
				BpmCallBackOrder order = new BpmCallBackOrder();
				order.setNodeId(nodeId);
				order.setOpType(BpmCallBackOpTypeEnum.getByCode(opType));
				
				FormService formService = formServiceClient;
				
				FormQueryOrder queryOrder = new FormQueryOrder();
				queryOrder.setActInstId(insId);
				queryOrder.setRunId(rId);
				
				QueryBaseBatchResult<FormInfo> formResult = formService.queryPage(queryOrder);
				
				if (formResult.getTotalCount() == 0) {
					formResult = formServiceFmClient.queryPage(queryOrder);
					if (formResult != null && formResult.getTotalCount() > 0) {
						formService = formServiceFmClient;
					}
				}
				
				if (formResult.getTotalCount() == 0) {
					formResult = formServiceAmClient.queryPage(queryOrder);
					if (formResult != null && formResult.getTotalCount() > 0) {
						formService = formServiceAmClient;
					}
				}
				
				if (formResult.getTotalCount() == 0) {
					formResult = formServiceCrmClient.queryPage(queryOrder);
					if (formResult != null && formResult.getTotalCount() > 0) {
						formService = formServiceCrmClient;
					}
				}
				
				if (formResult == null || formResult.getTotalCount() == 0) {
					jsonObject.put("code", "0");
					jsonObject.put("success", false);
					jsonObject.put("message", "未找到业务表单");
				}
				
				order.setFormId(formResult.getPageList().get(0).getFormId());
				FormBaseResult processResult = formService.bpmCallBackProcess(order);
				if (processResult != null && processResult.isSuccess()) {
					jsonObject.put("code", "1");
					jsonObject.put("success", true);
					if (StringUtil.isNotBlank(processResult.getUrl())) {
						jsonObject.put("message", "流程处理成功[" + processResult.getUrl() + "]");
					} else {
						jsonObject.put("message", "流程处理成功");
					}
				} else {
					jsonObject.put("code", "0");
					jsonObject.put("success", false);
					jsonObject.put("message", "流程处理失败");
				}
			} else {
				jsonObject.put("code", "0");
				jsonObject.put("success", false);
				jsonObject.put("message", "actInstId,runId至少需要一个值");
			}
			
		} catch (Exception e) {
			jsonObject.put("code", "0");
			jsonObject.put("message", "BPM回调处理出错");
			logger.error("BPM回调处理出错:actInstId:{} ,runId:{} opType:{} {}", actInstId, runId, opType,
				e);
		}
		logger.info("BPM回调处理结果:{} , actInstId:{} ,runId:{} opType:{}", jsonObject, actInstId,
			runId, opType);
		return jsonObject;
	}
}
