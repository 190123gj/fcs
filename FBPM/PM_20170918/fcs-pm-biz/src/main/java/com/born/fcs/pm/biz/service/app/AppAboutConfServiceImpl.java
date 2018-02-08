package com.born.fcs.pm.biz.service.app;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.daointerface.AppAboutConfDAO;
import com.born.fcs.pm.dal.dataobject.AppAboutConfDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.app.AppAboutConfInfo;
import com.born.fcs.pm.ws.app.AppAboutConfOrder;
import com.born.fcs.pm.ws.app.AppAboutConfService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("appAboutConfService")
public class AppAboutConfServiceImpl extends BaseAutowiredDomainService implements
																		AppAboutConfService {
	@Autowired
	AppAboutConfDAO appAboutConfDAO;
	
	@Override
	public FcsBaseResult save(final AppAboutConfOrder order) {
		return commonProcess(order, "保存APP后台参数配置", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				Date now = FcsPmDomainHolder.get().getSysDate();
				AppAboutConfDO conf = null;
				if (order.getConfId() > 0) {
					conf = appAboutConfDAO.findById(order.getConfId());
				}
				if (conf == null) {
					conf = new AppAboutConfDO();
					BeanCopier.staticCopy(order, conf);
					conf.setRawAddTime(now);
					appAboutConfDAO.insert(conf);
				} else {
					BeanCopier.staticCopy(order, conf);
					appAboutConfDAO.update(conf);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public AppAboutConfInfo get() {
		try {
			AppAboutConfDO conf = appAboutConfDAO.findOne();
			if (conf != null) {
				AppAboutConfInfo info = new AppAboutConfInfo();
				BeanCopier.staticCopy(conf, info);
				return info;
			}
		} catch (Exception e) {
			logger.error("查询APP后台配置出错{}", e);
		}
		return null;
	}
}
