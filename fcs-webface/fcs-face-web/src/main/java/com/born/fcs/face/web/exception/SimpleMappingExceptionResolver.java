package com.born.fcs.face.web.exception;

import javax.servlet.http.HttpServletRequest;

import com.born.fcs.face.integration.exception.FcsFaceBizException;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class SimpleMappingExceptionResolver
											extends
											org.springframework.web.servlet.handler.SimpleMappingExceptionResolver {
	
	protected static final Logger logger = LoggerFactory
		.getLogger(SimpleMappingExceptionResolver.class);
	
	@Override
	protected void logException(Exception ex, HttpServletRequest request) {
		if (ex != null) {
			boolean logerr = true;
			if (ex instanceof FcsFaceBizException) {
				FcsFaceBizException e = (FcsFaceBizException) ex;
				if (e.getResultCode() == FcsResultEnum.ILLEGAL_SIGN) {
					logerr = false;
				}
			}
			if (logerr)
				logger.error(ex.getMessage(), ex);
		}
	}
}
