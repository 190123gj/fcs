package com.bornsoft.api.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.born.fcs.face.integration.pm.service.common.SysParameterServiceClient;
import com.bornsoft.api.service.base.AbsBaseBornApiService;
import com.bornsoft.api.service.electric.ElectricInfoExportService;
import com.bornsoft.api.service.electric.ElectricQueryService;
import com.bornsoft.api.service.kingdee.KingdeeVoucherNoRecevieService;
import com.bornsoft.api.service.risk.LoginRiskSystemService;
import com.bornsoft.api.service.risk.RiskInfoQueryService;
import com.bornsoft.api.service.risk.RiskInfoRecieveService;

/**
 * @Description: DefaultApiServiceProvider 
 * @author taibai@yiji.com
 * @date 2016-10-7 下午3:08:36
 * @version V1.0
 */
@Component(value = "defaultApiServiceProvider")
public class BornApiServiceProvider implements BeanFactoryAware, InitializingBean, DisposableBean, IBornApiServiceProvider {
	private static final String BORN_NAME = "born";
	private static final Logger logger = LoggerFactory.getLogger(BornApiServiceProvider.class);

	private ConcurrentMap<String, AbsBaseBornApiService> cacheServices = new ConcurrentHashMap<>();

	private SpringBeanCreateHelper beanCreateHelper = new SpringBeanCreateHelper();
	
	@Autowired
	protected SysParameterServiceClient sysParameterServiceClient;

	/**
	 * 服务名称空间
	 */
	@Override
	public String getServiceNameSpace() {
		return BORN_NAME;
	}

	/**
	 * 发现服务
	 */
	@Override
	public IBornApiService findBornApiService(String name) {
		if (name == null || name.length() == 0) return null;
		return cacheServices.get(name);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanCreateHelper.setBeanFactory(beanFactory);
	}

	@Override
	public void destroy() throws Exception {
		cacheServices.clear();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>开始注册 born.*服务!");
		loadCoreService();
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>注册 born.*服务完成！");
	}


	/**
	 * 加载服务
	 */
	public void loadCoreService() {
		//电量
		loadBornApiServiceImpl(ElectricQueryService.class);
		loadBornApiServiceImpl(ElectricInfoExportService.class);
		//风险监控系统
		loadBornApiServiceImpl(RiskInfoRecieveService.class);
		loadBornApiServiceImpl(LoginRiskSystemService.class);
		loadBornApiServiceImpl(RiskInfoQueryService.class);
		//金蝶
		loadBornApiServiceImpl(KingdeeVoucherNoRecevieService.class);
		
		logger.info("加载完毕:{}", cacheServices);
	}

	private <T extends AbsBaseBornApiService> void loadBornApiServiceImpl(Class<T> bornApiClass) {
		T bornApiService = null;
		try {
			bornApiService = BeanUtils.instantiate(bornApiClass);
		} catch (BeanInstantiationException e) {
			logger.error("实例化出错：", e);
			return;
		}
		try {
			bornApiService = this.beanCreateHelper.createAutoInjectBean(bornApiService);
			bornApiService.setServiceNameSpace(this.getServiceNameSpace());
			this.cacheServices.putIfAbsent(bornApiService.getServiceCode(), bornApiService);
			logger.info(" 加载服务 [{},{},{}] 成功!", bornApiService.getServiceCode(), bornApiService.getServiceEnum().getDesc(), bornApiClass.getName());
		} catch (Exception e) {
			logger.error("加载服务 [{},{},{}] 出错:{}", bornApiService.getServiceCode(), bornApiService.getServiceEnum().getDesc(), bornApiClass.getName(), e);
		}
	}
}
