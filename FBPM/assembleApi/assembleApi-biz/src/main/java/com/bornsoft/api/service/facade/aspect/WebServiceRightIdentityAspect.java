package com.bornsoft.api.service.facade.aspect;

import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceException;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bornsoft.integration.jck.config.SystemParamCacheHolder;
import com.bornsoft.utils.constants.ApiSystemParamEnum;
import com.bornsoft.utils.constants.SwitchEnum;
import com.bornsoft.utils.enums.BornApiExceptionEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.yjf.common.lang.ip.IPUtil;



/**
 * @Description:用于打印中servcies调用integration中接口参数及相关日志
 * @author xiaohui@yiji.com
 * @date 2015-11-28 下午1:46:27 
 * @version V1.0
 */
@Aspect
@Service("WebServiceRightIdentityAspect")
public class WebServiceRightIdentityAspect {
	protected final Logger logger = LoggerFactory.getLogger(WebServiceRightIdentityAspect.class);

	@Autowired
	private   SystemParamCacheHolder	systemParamCacheHolder;
	/**
	 * 记录integration调用的日志信息
	 */
	@Around("execution(public * com.bornsoft.api.service.facade.impl..*.*(..)) ")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		StringWriter loginfo = new StringWriter(1024);
		try {
			String callMethod = pjp.getTarget().getClass().getSimpleName() + "." + pjp.getSignature().getName() + "(*)" ;
			logger.info("检查方法 {} 调用权限..",callMethod);
			String config = systemParamCacheHolder.getConfig(ApiSystemParamEnum.Api_WebService_Identity_Switch);
			
			if((StringUtils.equals(config, SwitchEnum.Off.code()))||
					(belongToWhiteList())){
				long begin = System.nanoTime();
				Object retVal = pjp.proceed();
				long end = System.nanoTime();
				loginfo.append("远程cxf调用方法:").append(callMethod);
				loginfo.append("  耗时: ").append(String.valueOf(TimeUnit.MILLISECONDS.convert((end - begin), TimeUnit.NANOSECONDS))).append(" ms ");
				logger.info(loginfo.toString());
				logger.info("出参={}",retVal);
				return retVal;
			}else{
				throw new BornApiException("there is no right to call this service");
			}
		} catch (WebServiceException e) {
			loginfo.append("\n 远程调用cxf方法失败[WebServcieException]：");
			logger.error(loginfo.toString(), e);
			throw new BornApiException(BornApiExceptionEnum.SERVICE_NOT_ACCESS);
		} catch(BornApiException b){
			loginfo.append("\n 远程调用失败：");
			logger.error(loginfo.toString(), b);
			throw b;
		} catch (Exception t) {
			loginfo.append("\n 远程调用失败：");
			logger.error(loginfo.toString(), t);
			throw new BornApiException(BornApiExceptionEnum.SERVICE_CALL_FAILED);
		}
	}
	/**
	 * 是否属于IP白名单
	 * @return
	 */
	private boolean belongToWhiteList() {
		Message cxf = PhaseInterceptorChain.getCurrentMessage();
		if(cxf!=null){
			HttpServletRequest request = (HttpServletRequest) cxf
					.get(AbstractHTTPDestination.HTTP_REQUEST);
			String accessIP = IPUtil.getIpAddr(request);
			logger.info("called ip " + accessIP);
			
			String config = systemParamCacheHolder.getConfig(ApiSystemParamEnum.Api_WebService_Wihte);
			if(StringUtils.isNotBlank(config)){
				String [] tmpArr = config.split(",");
				for(String t : tmpArr){
					if(StringUtils.equals(t, accessIP)){
						return true;
					}
				}
			}
			return false;
		}else{
			logger.info("非webservice 调用不做限制");
			return true;
		}
	}
}
