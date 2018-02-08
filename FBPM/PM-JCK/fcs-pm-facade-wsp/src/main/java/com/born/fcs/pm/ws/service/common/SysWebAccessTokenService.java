package com.born.fcs.pm.ws.service.common;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.common.SysWebAccessTokenInfo;
import com.born.fcs.pm.ws.order.common.SysWebAccessTokenOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 访问web端的密钥
 *
 * @author wuzj
 */
@WebService
public interface SysWebAccessTokenService {
	
	/**
	 * 查询用户访问密钥
	 * @param accessToken
	 * @param userId
	 * @return
	 */
	SysWebAccessTokenInfo query(String accessToken);
	
	/**
	 * 使用访问密钥（变成无效）
	 * @param userId
	 * @param accessToken
	 * @return
	 */
	FcsBaseResult use(String accessToken);
	
	/**
	 * 激活（变成有效）
	 * @param userId
	 * @param accessToken
	 * @return
	 */
	FcsBaseResult active(String accessToken);
	
	/**
	 * 新增用户访问密钥
	 * @param userId
	 * @return 生成的密钥通过url返回
	 */
	FcsBaseResult addUserAccessToken(SysWebAccessTokenOrder order);
	
}
