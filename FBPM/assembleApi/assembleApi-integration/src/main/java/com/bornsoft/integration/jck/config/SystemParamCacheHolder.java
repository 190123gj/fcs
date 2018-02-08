package com.bornsoft.integration.jck.config;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.pm.service.common.SysParameterServiceClient;
import com.bornsoft.utils.base.MerchantInfo;
import com.bornsoft.utils.constants.ApiSystemParamEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.yjf.common.lang.util.StringUtil;

@Service
public class SystemParamCacheHolder implements  InitializingBean, DisposableBean{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected SysParameterServiceClient sysParameterServiceClient;
	
	private ConcurrentMap<ApiSystemParamEnum,SimpleCache<String>> caches = new ConcurrentHashMap<>();
	
	private ConcurrentMap<String,SimpleCache<MerchantInfo>> merchantCaches = new ConcurrentHashMap<>();
	
	/**
	 * 获取系统参数
	 * @param paramType
	 * @return
	 */
	public String getConfig(ApiSystemParamEnum paramType){
		SimpleCache<String> config = getCache(paramType);
		if(config==null){
			return null;
		}else{
			return config.getData();
		}
	}
	
	/**
	 * 获取系统参数
	 * @param paramType
	 * @return
	 */
	public String getConfigNoCache(ApiSystemParamEnum paramType){
		return sysParameterServiceClient.getSysParameterValue(paramType.code());
	}
	
	/**
	 * 获取系统参数
	 * @param paramType
	 * @return
	 */
	public String getConfigNotNull(ApiSystemParamEnum paramType) throws BornApiException{
		SimpleCache<String> config = getCache(paramType);
		if(config==null){
			throw new BornApiException("未找到配置:" + paramType.message());
		}else{
			String value =  config.getData();
			if(StringUtil.isBlank(value)){
				throw new BornApiException("配置 "+paramType.message()+" 为空");	
			}else{
				return value;
			}
		}
	}
	
	/**
	 * 获取商户信息
	 * @param merchantNo
	 * @return
	 */
	public MerchantInfo getMerchantInfo(String merchantNo) {
		if(StringUtil.isBlank(merchantNo)){
			return null;
		}else{
			SimpleCache<MerchantInfo> cache =  merchantCaches.get(merchantNo);
			if(cache==null || cache.isEmpty() || cache.isOutOfDate()){
				initMerchant();
				cache =  merchantCaches.get(merchantNo);
				if(cache == null){
					return null;
				}else if(cache.isEmpty() || cache.isOutOfDate()){
					throw new BornApiException("获取商户密钥失败");
				}else{
					return cache.getData();
				}
			}else{
				return cache.getData();
			}
		}
	}
	
	/**
	 * 兼容老版本
	 * @return
	 */
	public  String getRiskMerchantCode() {
		return getConfig(ApiSystemParamEnum.Risk_Api_Partner_Id);
	}
	/**
	 * 兼容老版本
	 * @return
	 */
	public  String getRiskSecurityKey() {
		return getConfig(ApiSystemParamEnum.Risk_Api_Partner_Key);
	}
	/**
	 * 兼容老版本
	 * @return
	 */
	public  String getRiskSystemUrl() {
		return getConfig(ApiSystemParamEnum.Risk_Api_System_Url);
	}
	/**
	 * 兼容老版本
	 * @return
	 */
	public  String getRiskSystemLogOutUrl() {
		return getRiskSystemUrl() + "logoutRiskSystem.json";
	}
	
	/**
	 * 获取缓存
	 * @param paramType
	 * @return
	 */
	private SimpleCache<String> getCache(ApiSystemParamEnum paramType){
		try {
			if(paramType==null){
				return null;
			}else{
				SimpleCache<String> cache =  caches.get(paramType);
				String value = null;
				if(cache == null){
					logger.info("刷新配置 {},start",paramType.message());
					value = sysParameterServiceClient.getSysParameterValue(paramType.code());
					cache = new SimpleCache<String>(value);
					this.caches.put(paramType,cache);
					cache = this.caches.get(paramType);
					logger.info("刷新配置 {},end",paramType.message());
				}else if(cache.isEmpty()||cache.isOutOfDate()){
					synchronized (cache) {
						logger.info("刷新配置 {},start",paramType.message());
						value = sysParameterServiceClient
								.getSysParameterValue(paramType.code());
						if(StringUtil.isNotBlank(value)){
							cache.updateCache(value);
							logger.info("刷新配置 {},end",paramType.message());
						}else{
							logger.info("配置{}未刷新",paramType.message());
						}
					}
				}
				return cache;
			}
		} catch (Exception e) {
			logger.error("获取参数paramType失败：",e);
			return null;
		}
	}
	
	public void clear(){
		if(caches!=null){
			caches.clear();
		}
	}
	
	public SimpleCache<String> addCache(ApiSystemParamEnum key,SimpleCache<String> cache){
		return caches.put(key, cache);
	}

	@Override
	public void destroy() throws Exception {
		if(caches!=null){
			caches.clear();
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	private void initMerchant() {
		logger.info("刷新商户配置,start");
		makeMerchant(ApiSystemParamEnum.Born_Partner_Id,ApiSystemParamEnum.Born_Partner_Key);
//		makeMerchant(ApiSystemParamEnum.Risk_Api_Partner_Id,ApiSystemParamEnum.Risk_Api_Partner_Key);
		makeMerchant(ApiSystemParamEnum.Risk_Partner_Id,ApiSystemParamEnum.Risk_Partner_Key);
		makeMerchant(ApiSystemParamEnum.Kingdee_Api_Partner_Id,ApiSystemParamEnum.Kingdee_Api_Partner_Key);
		makeMerchant(ApiSystemParamEnum.Kingdee_Partner_Id,ApiSystemParamEnum.Kingdee_Partner_Key);
		logger.info("刷新商户配置,end");
	}

	private void makeMerchant(ApiSystemParamEnum partnerIdEnum, ApiSystemParamEnum partnerKeyEnum) {
		try {
			if(partnerIdEnum==null){
				return;
			}else{
				String tmpPartnerId = sysParameterServiceClient.getSysParameterValue(partnerIdEnum.code());
				String tmpPartnerKey = sysParameterServiceClient.getSysParameterValue(partnerKeyEnum.code());
				if(StringUtil.isNotBlank(tmpPartnerId) && StringUtil.isNotBlank(tmpPartnerKey)){
					SimpleCache<MerchantInfo> tmpCache = new SimpleCache<MerchantInfo>(new MerchantInfo(tmpPartnerId,tmpPartnerKey),10);
					merchantCaches.put(tmpPartnerId, tmpCache);
				}
			}
		} catch (Exception e) {
			logger.error("加载商户{}配置失败",partnerIdEnum.message());
		}
	} 
	

}
