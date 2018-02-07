package com.born.fcs.face.integration.exception;

import com.born.fcs.pm.ws.enums.base.FcsResultEnum;

public class ExceptionFactory {
	public static FcsFaceBizException newFcsException(FcsResultEnum resultCode, String errorMsg) {
		return new FcsFaceBizException(resultCode, errorMsg);
	}
}
