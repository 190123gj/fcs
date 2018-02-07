package com.born.fcs.face.integration.risk.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.pm.service.common.SysParameterServiceClient;
import com.born.fcs.face.integration.risk.service.RiskPageService;
import com.born.fcs.face.integration.util.ApiDigestUtil;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

@Service("riskPageService")
public class RiskPageServiceImpl implements RiskPageService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	SysParameterServiceClient sysParameterServiceClient;
	
	@Override
	public FcsBaseResult loginRiskSystem(String operator, String toUrl) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("service", "loginRiskSystem");
			paramMap.put("operator", operator);
			if (StringUtil.isNotBlank(toUrl)) {
				paramMap.put("toUrl", toUrl);
			}
			
			String url = getUrl(paramMap);
			if (StringUtil.isNotBlank(url)) {
				result.setSuccess(true);
				result.setUrl(url);
				result.setMessage("获取跳转链接成功");
			} else {
				result.setSuccess(false);
				result.setMessage("获取跳转链接失败");
			}
			
		} catch (IllegalArgumentException e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			logger.error(e.getLocalizedMessage(), e);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult queryRiskInfo(QueryRiskOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			order.check();
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("service", "queryRiskInfo");
			paramMap.put("customName", order.getCustomName());
			paramMap.put("licenseNo", order.getLicenseNo());
			paramMap.put("operator", order.getOperator());
			String url = getUrl(paramMap);
			if (StringUtil.isNotBlank(url)) {
				result.setSuccess(true);
				result.setUrl(url);
				result.setMessage("获取跳转链接成功");
			} else {
				result.setSuccess(false);
				result.setMessage("获取跳转链接失败");
			}
			
		} catch (IllegalArgumentException e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			logger.error(e.getLocalizedMessage(), e);
		}
		return result;
	}
	
	/**
	 * 获取跳转链接
	 * */
	private String getUrl(Map<String, String> paramMap) {
		String url = "";
		try {
			paramMap.put("partnerId", StringUtil.defaultIfBlank(
				sysParameterServiceClient.getSysParameterValue("RISK_PARTNER_ID"),
				"20160526144452113627"));
			paramMap.put("orderNo", BusinessNumberUtil.gainOutBizNoNumber());
			url = ApiDigestUtil.makeUrl(paramMap,
				sysParameterServiceClient.getSysParameterValue("RISK_HTTP_URL"),
				sysParameterServiceClient.getSysParameterValue("RISK_PARTNER_KEY"));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		return url;
		
	}
	
}
