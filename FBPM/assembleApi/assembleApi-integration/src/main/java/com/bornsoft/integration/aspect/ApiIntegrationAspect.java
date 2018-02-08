package com.bornsoft.integration.aspect;

import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import javax.xml.ws.WebServiceException;

import org.apache.commons.io.IOUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.bornsoft.utils.enums.BornApiExceptionEnum;
import com.bornsoft.utils.exception.BornApiException;



/**
 * @Description:用于打印中servcies调用integration中接口参数及相关日志
 * @author xiaohui@yiji.com
 * @date 2015-11-28 下午1:46:27 
 * @version V1.0
 */
@Aspect
@Service("integrationAspectLog")
public class ApiIntegrationAspect {
	protected final Logger logger = LoggerFactory.getLogger(ApiIntegrationAspect.class);

	private boolean dumpParam = true;
	private boolean dumpResult = true;
	private boolean dumpTime = true;

	public ApiIntegrationAspect() {
	}

	public boolean isDumpParam() {
		return dumpParam;
	}

	public void setDumpParam(boolean dumpParam) {
		this.dumpParam = dumpParam;
	}

	public boolean isDumpResult() {
		return dumpResult;
	}

	public void setDumpResult(boolean dumpResult) {
		this.dumpResult = dumpResult;
	}

	public boolean isDumpTime() {
		return dumpTime;
	}

	public void setDumpTime(boolean dumpTime) {
		this.dumpTime = dumpTime;
	}

	/**
	 * 记录integration调用的日志信息
	 */
	@Around("within(com.bornsoft.integration..*) && @annotation(log) && execution(public * *(..)) ")
	public Object doBasicProfiling(ProceedingJoinPoint pjp, IntegrationLog log) throws Throwable {
		StringWriter loginfo = new StringWriter(1024);
		try {
			String callMethod = pjp.getTarget().getClass().getSimpleName() + "." + pjp.getSignature().getName() + "(*)" ;
			loginfo.append("远程cxf调用方法:").append(callMethod);
			long begin = System.nanoTime();
			Object retVal = pjp.proceed();
			long end = System.nanoTime();
			if (isDumpTime()) {
				loginfo.append("  耗时: ").append(String.valueOf(TimeUnit.MILLISECONDS.convert((end - begin), TimeUnit.NANOSECONDS))).append(" ms ");
			}
			if (isDumpParam()) {
				Object[] objects = pjp.getArgs();
				loginfo.append("\n 请求参数:");
				if (objects != null && objects.length > 0) {
					SerializeWriter out = new SerializeWriter(loginfo);
					try {
						new JSONSerializer(out).write(objects);
					} catch (Exception ignore) {
					}
					out.flush();
					IOUtils.closeQuietly(out);
				} else {
					loginfo.append("null");
				}
			}

			if (isDumpResult()) {
				if (retVal == null && log.returnVoid()) {
					loginfo.append("\n 请求结果: void ");
				} else if (retVal == null) {
					loginfo.append("\n 请求结果: null ");
				} else {
					loginfo.append("\n 请求结果：");
					SerializeWriter out = new SerializeWriter(loginfo);
					try {
						new JSONSerializer(out).write(retVal);
					} catch (Exception ignore) {
					}
					out.flush();
					IOUtils.closeQuietly(out);
				}
			}
			logger.info(loginfo.toString());

			// 远程调用，有可能返回对象为空
			if (!log.returnVoid() && retVal == null) {
				throw new BornApiException(BornApiExceptionEnum.SERVICE_CALL_FAILED);
			}
			return retVal;
		} catch (WebServiceException e) {
			loginfo.append("\n 远程调用cxf方法失败[WebServcieException]：");
			logger.error(loginfo.toString(), e);
			throw new BornApiException(BornApiExceptionEnum.SERVICE_NOT_ACCESS);
		} catch (Exception t) {
			loginfo.append("\n 远程调用失败：");
			logger.error(loginfo.toString(), t);
			throw new BornApiException(BornApiExceptionEnum.SERVICE_CALL_FAILED);
		}
	}
}
