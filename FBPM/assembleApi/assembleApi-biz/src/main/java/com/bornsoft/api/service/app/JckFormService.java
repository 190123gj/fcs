package com.bornsoft.api.service.app;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.bpm.service.info.UserInfo;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.order.common.SimpleFormOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.service.common.FormService;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * @Description: 表单流程通用service 
 * @author taibai@yiji.com
 * @date 2016-9-26 上午10:35:30
 * @version V1.0
 */
@Service
public class JckFormService extends JckBaseService{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 提交
	 * @param formId
	 * @param request
	 * @return
	 */
	public JSONObject submit(long formId, JSONObject request) {
		return submit(formId, getSystemMatchedFormService(request));
	}
	
	/**
	 * 公用提交方法
	 * @param formId
	 * @param formService
	 * @return
	 */
	private  JSONObject submit(long formId, FormService formService){

		JSONObject result = new JSONObject();
		try {
			if(formService==null){
				toJSONResult(result, AppResultCodeEnum.FAILED, "未找到对应服务");
				return result;
			}
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				toJSONResult(result,AppResultCodeEnum.FAILED, "登录已失效");
				return result;
			}
			
			SimpleFormOrder order = new SimpleFormOrder();
			order.setFormId(formId);
			setSessionLocalInfo2Order(order);
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (userInfo != null) {
				order.setUserEmail(userInfo.getEmail());
				order.setUserMobile(userInfo.getMoblie());
			}
			FormBaseResult submitResult = formService.submit(order);
			
			if (submitResult.isSuccess()) {
				toJSONResult(result,AppResultCodeEnum.SUCCESS, "提交成功");

				String nextAuditor = "";
				String nextNode = "";
				String nextInfo = submitResult.getUrl();
				if (StringUtil.isNotBlank(nextInfo)) {
					String[] next = nextInfo.split(";");
					if (next.length > 0)
						nextNode = next[0];
					if (next.length > 1)
						nextAuditor = next[1];
				}
				
				String message = "提交成功 ";
				if ("FLOW_FINISH".equals(nextNode)) {
					message = "流程处理完成";
				} else {
					if (StringUtil.isNotBlank(nextNode)) {
						message += "[ " + nextNode + " ]";
					}
					if (StringUtil.isNotBlank(nextAuditor)) {
						message += "[ 待执行人：" + nextAuditor + " ]";
					}
				}
				result.put("message", message);
				result.put("nextAuditor", nextAuditor);
				result.put("nextNode", nextNode);
				
				result.put("form", submitResult.getFormInfo().toJson());
			} else {
				toJSONResult(result,AppResultCodeEnum.FAILED, submitResult.getMessage());
				result.put("form", submitResult.getFormInfo());
			}
		} catch (Exception e) {
			logger.error("提交表单出错", e);
			toJSONResult(result,AppResultCodeEnum.FAILED, "提交表单出错：" + e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 返回和子系统匹配的表单客户端
	 * @param request
	 * @return
	 */
	public FormService getSystemMatchedFormService(JSONObject request) {
		return super.getSystemMatchedFormServiceByModule(ModuleEnum.getByCode(request.getString(MODULE)));
	}
	
	/**
	 * 返回和子系统匹配的表单客户端
	 * @param request
	 * @return
	 */
	public FormService getSystemMatchedFormService(HttpServletRequest request) {
		return super.getSystemMatchedFormServiceByModule(ModuleEnum.getByCode(request.getParameter(MODULE)));
	}
	
	/**
	 * 提交
	 * @param formId
	 * @param request
	 * @return
	 */
	public JSONObject submit(long formId, HttpServletRequest request) {
		logger.info("提交,formId={}",formId);
		JSONObject result = new JSONObject();
		try {
			SimpleFormOrder order = new SimpleFormOrder();
			order.setFormId(formId);
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				toJSONResult(result,AppResultCodeEnum.FAILED, "登录已失效");
				return result;
			}
			
			setSessionLocalInfo2Order(order);
			
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (userInfo != null) {
				order.setUserEmail(userInfo.getEmail());
				order.setUserMobile(userInfo.getMoblie());
			}
			
			FormBaseResult submitResult = getSystemMatchedFormService(request).submit(order);
			
			if (submitResult.isSuccess()) {
				toJSONResult(result,AppResultCodeEnum.SUCCESS, "提交成功");

				String nextAuditor = "";
				String nextNode = "";
				String nextInfo = submitResult.getUrl();
				if (StringUtil.isNotBlank(nextInfo)) {
					String[] next = nextInfo.split(";");
					if (next.length > 0)
						nextNode = next[0];
					if (next.length > 1)
						nextAuditor = next[1];
				}
				
				String message = "提交成功 ";
				if ("FLOW_FINISH".equals(nextNode)) {
					message = "流程处理完成";
				} else {
					if (StringUtil.isNotBlank(nextNode)) {
						message += "[ " + nextNode + " ]";
					}
					if (StringUtil.isNotBlank(nextAuditor)) {
						message += "[ 待执行人：" + nextAuditor + " ]";
					}
				}
				result.put("message", message);
				result.put("nextAuditor", nextAuditor);
				result.put("nextNode", nextNode);
				
				result.put("form", submitResult.getFormInfo().toJson());
			} else {
				toJSONResult(result,AppResultCodeEnum.FAILED, submitResult.getMessage());
				result.put("form", submitResult.getFormInfo());
			}
			
		} catch (Exception e) {
			logger.error("提交表单出错", e);
			toJSONResult(result,AppResultCodeEnum.FAILED, "提交表单出错：" + e.getMessage());
		}
		logger.info("提交,出参={}",result);
		return result;
	}
}
