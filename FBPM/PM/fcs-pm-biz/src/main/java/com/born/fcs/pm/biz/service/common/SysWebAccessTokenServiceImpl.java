package com.born.fcs.pm.biz.service.common;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.daointerface.SysWebAccessTokenDAO;
import com.born.fcs.pm.dal.dataobject.SysWebAccessTokenDO;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.common.SysWebAccessTokenInfo;
import com.born.fcs.pm.ws.order.common.SysWebAccessTokenOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.common.SysWebAccessTokenService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;

@Service("sysWebAccessTokenService")
public class SysWebAccessTokenServiceImpl extends BaseAutowiredDomainService implements
																			SysWebAccessTokenService {
	
	@Autowired
	private SysWebAccessTokenDAO sysWebAccessTokenDAO;
	
	@Override
	public SysWebAccessTokenInfo query(String accessToken) {
		if (StringUtil.isBlank(accessToken))
			return null;
		SysWebAccessTokenDO token = new SysWebAccessTokenDO();
		token.setAccessToken(accessToken);
		//token.setIsValid(BooleanEnum.IS.code());
		List<SysWebAccessTokenDO> tokens = sysWebAccessTokenDAO.findList(token);
		if (ListUtil.isNotEmpty(tokens)) {
			token = tokens.get(0);
			SysWebAccessTokenInfo info = new SysWebAccessTokenInfo();
			BeanCopier.staticCopy(token, info);
			info.setIsValid(BooleanEnum.getByCode(token.getIsValid()));
			return info;
		}
		return null;
	}
	
	@Override
	public FcsBaseResult use(String accessToken) {
		
		FcsBaseResult result = new FcsBaseResult();
		try {
			sysWebAccessTokenDAO.invalidByToken(accessToken);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("禁用密钥出错 {}", e);
		}
		
		return result;
	}
	
	@Override
	public FcsBaseResult active(String accessToken) {
		
		FcsBaseResult result = new FcsBaseResult();
		try {
			sysWebAccessTokenDAO.validByToken(accessToken);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("激活密钥出错 {}", e);
		}
		
		return result;
	}
	
	@Override
	public FcsBaseResult addUserAccessToken(SysWebAccessTokenOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			//2018-02-05 密钥生成后不再失效
			SysWebAccessTokenDO tokenDO = sysWebAccessTokenDAO.findValid(order.getUserId());
			if (tokenDO == null) {
				SysWebAccessTokenDO token = new SysWebAccessTokenDO();
				BeanCopier.staticCopy(order, token);
				if (StringUtil.isBlank(token.getAccessToken())) {
					token.setAccessToken(UUID.randomUUID().toString());
					token.setIsValid(BooleanEnum.IS.code());
				}
				token.setRawAddTime(new Date());
				sysWebAccessTokenDAO.insert(token);
				result.setUrl(token.getAccessToken());
			} else {
				result.setUrl(tokenDO.getAccessToken());
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("新增密钥出错 {}", e);
		}
		
		return result;
	}
	
}
