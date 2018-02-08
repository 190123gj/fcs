package com.born.fcs.pm.ws.app;

import javax.jws.WebService;

import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 *
 * APP 后台
 * @author wuzj
 */
@WebService
public interface AppAboutConfService {
	
	/**
	 * 保存
	 * @param order
	 * @return
	 */
	FcsBaseResult save(AppAboutConfOrder order);
	
	/**
	 * 查询
	 * @return
	 */
	AppAboutConfInfo get();
}
