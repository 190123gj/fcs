package com.bornsoft.web.app.customer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.crm.ws.service.info.CustomerBaseInfo;
import com.born.fcs.crm.ws.service.order.query.CustomerQueryOrder;
import com.born.fcs.crm.ws.service.result.ValidateCustomerResult;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.info.common.IndustryInfo;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.bornsoft.utils.constants.ApiSystemParamEnum;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.DateUtils;
import com.bornsoft.utils.tool.ReflectUtils;
import com.bornsoft.web.app.base.BaseController;
import com.yjf.common.lang.util.StringUtil;

@Controller
@RequestMapping("customerMg")
public class CustomerHomeController extends BaseController {
	
	@ResponseBody
	@RequestMapping("list.json")
	public JSONObject getList(HttpServletRequest request,
			CustomerQueryOrder queryOrder, Model model) {
		logger.info("查询客户列表，入参={}", ReflectUtils.toString(queryOrder));
		JSONObject result = null;
		try {
			String url = sysParameterServiceClient.getSysParameterValue(ApiSystemParamEnum.FACE_URL.code());
			if(StringUtil.isBlank(url)){
				throw new BornApiException("未配置face访问地址");
			}
			String likeCodeOrName = request.getParameter(LIKE_CODE_OR_NAME);
			if(StringUtils.isNotBlank(likeCodeOrName)){
				queryOrder.setLikeCustomerName(likeCodeOrName);
			}
			//设置权限
			queryCustomerPermissionSet(queryOrder);
			QueryBaseBatchResult<CustomerBaseInfo> batchResult = 
					personalCustomerClient.customerList(queryOrder);
			result = toJSONResult(batchResult);
			JSONObject page = makePage(batchResult);
			JSONArray dataList = new JSONArray();
			page.put("result", dataList);
			String token = getAccessToken();
			for(CustomerBaseInfo info : batchResult.getPageList()){
				JSONObject entity = new JSONObject();
				dataList.add(entity);
				entity.put("customerId", info.getCustomerId());
				entity.put("customerName", info.getCustomerName());
				entity.put("certType", info.getCertType());
				entity.put("enterpriseType", info.getEnterpriseType());
				entity.put("busiLicenseNo", info.getBusiLicenseNo());
				entity.put("certNo", info.getCertNo());
				entity.put("customerType", info.getCustomerType());
				entity.put("sex", info.getSex());
				try {
					entity.put("addTime", DateUtils.toString(info.getRawAddTime(), DateUtils.PATTERN2));
				} catch (Exception e) {
					logger.error("addTime",e);
				}
				if(StringUtil.equals(info.getCustomerType(), "ENTERPRISE")){
					IndustryInfo industryInfo = basicDataCacheService.querySingleIndustry(info.getIndustryCode());
					entity.put("industryName", industryInfo.getName());
				}
				entity.put("industryCode", info.getIndustryCode());
				entity.put("status", info.getStatus());
				String tmpUrl = null;
				if(CustomerTypeEnum.ENTERPRISE.code().equals(entity.get("customerType"))){
					tmpUrl = url+"/customerMg/companyCustomer/info.htm?userId="+info.getUserId()+"&accessToken="+token;
				}else if(CustomerTypeEnum.PERSIONAL.code().equals(info.getCustomerType())){
					tmpUrl = url+"/customerMg/personalCustomer/info.htm?userId="+info.getUserId()+"&accessToken="+token;
				}
				entity.put("url", tmpUrl);
			}
			result.put("page", page);
		} catch(BornApiException e1){
			logger.error("查询客户列表失败： ", e1);
			result = toJSONResult(AppResultCodeEnum.FAILED, e1.getMessage());
		}catch (Exception e) {
			logger.error("查询客户列表失败： ", e);
			result = toJSONResult(AppResultCodeEnum.FAILED, "查询");
		}
		return result;
	}
	

	/** 校验企业客户是否可用 **/
	@ResponseBody
	@RequestMapping("validata.json")
	public JSONObject validata(String certNo, String customerName, String customerType) {
		JSONObject json = new JSONObject();
		try {
			ValidateCustomerResult result = null;
			if(CustomerTypeEnum.PERSIONAL.code().equals(customerType)){
				result = personalCustomerClient.ValidateCustomer(certNo,
						customerName, ShiroSessionUtils.getSessionLocal().getUserId());
			}else if(CustomerTypeEnum.ENTERPRISE.code().equals(customerType)){
				result = companyCustomerClient.ValidateCustomer(certNo,
						customerName, ShiroSessionUtils.getSessionLocal().getUserId());
			}else{
				throw new BornApiException("用户类型不正确");
			}
			if (result != null) {
				boolean success = result.isSuccess() && StringUtil.isBlank(result.getType());
				toJSONResult(json, success ?AppResultCodeEnum.SUCCESS : AppResultCodeEnum.FAILED , getTextMsg(result.getMessage()));
				json.put("type", result.getType());
				json.put("customerManager", result.getCustomerManager());
				json.put("userId", result.getUserId());
				json.put("memo", "type字段：pub :公海 ，per：历史客户，perHs：历史间接客户，other:别人的");
			} else {
				toJSONResult(json, AppResultCodeEnum.FAILED, "查询失败");
			}
		} catch (Exception e) {
			logger.error("查询失败",e);
			toJSONResult(json, AppResultCodeEnum.FAILED, "查询失败");
		}
		return json;
	}

}
