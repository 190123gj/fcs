package com.born.fcs.pm.biz.service.common;

public interface AsynchronousService {
	/**
	 * 异步服务执行
	 */
	void execute(Object[] objects);
}
