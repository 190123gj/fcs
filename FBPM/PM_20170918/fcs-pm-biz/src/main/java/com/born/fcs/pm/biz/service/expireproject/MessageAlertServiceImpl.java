package com.born.fcs.pm.biz.service.expireproject;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.MessageAlertDO;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.expireproject.MessageAlertInfo;
import com.born.fcs.pm.ws.order.expireproject.MessageAlertOrder;
import com.born.fcs.pm.ws.order.expireproject.MessageAlertQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.expireproject.MessageAlertService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * 
 * 保后，等级评定
 * 
 * @author lirz
 * 
 * 2016-7-29 上午10:47:08
 * 
 */
@Service("messageAlertService")
public class MessageAlertServiceImpl extends BaseAutowiredDomainService implements
																		MessageAlertService {
	
	@Override
	public FcsBaseResult add(final MessageAlertOrder order) {
		return commonProcess(order, "增加提醒项目", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				MessageAlertDO alert = new MessageAlertDO();
				alert.setProjectCode(order.getProjectCode());
				alert.setAlertPhrase(order.getAlertPhrase());
				List<MessageAlertDO> list = messageAlertDAO.findByCondition(alert, 0L, 1L);
				if (ListUtil.isEmpty(list)) {
					MessageAlertDO doObj = new MessageAlertDO();
					BeanCopier.staticCopy(order, doObj);
					doObj.setActive(BooleanEnum.YES.code());
					doObj.setRawAddTime(getSysdate());
					messageAlertDAO.insert(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<MessageAlertInfo> queryList(MessageAlertQueryOrder queryOrder) {
		QueryBaseBatchResult<MessageAlertInfo> baseBatchResult = new QueryBaseBatchResult<>();
		
		MessageAlertDO alert = new MessageAlertDO();
		BeanCopier.staticCopy(queryOrder, alert);
		
		long totalSize = messageAlertDAO.findByConditionCount(alert);
		if (totalSize > 0) {
			PageComponent component = new PageComponent(queryOrder, totalSize);
			List<MessageAlertDO> pageList = messageAlertDAO.findByCondition(alert, 0, 999L);
			
			List<MessageAlertInfo> list = baseBatchResult.getPageList();
			if (totalSize > 0) {
				for (MessageAlertDO doObj : pageList) {
					MessageAlertInfo info = new MessageAlertInfo();
					BeanCopier.staticCopy(doObj, info);
					list.add(info);
				}
			}
			
			baseBatchResult.initPageParam(component);
			baseBatchResult.setPageList(list);
		}
		
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public FcsBaseResult stopAlert(final MessageAlertOrder order) {
		return commonProcess(order, "取消提醒", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				MessageAlertDO messageAlert = new MessageAlertDO();
				messageAlert.setProjectCode(order.getProjectCode());
				messageAlert.setAlertPhrase(order.getAlertPhrase());
				messageAlertDAO.updateToStop(messageAlert);
				return null;
			}
		}, null, null);
	}

	@Override
	public FcsBaseResult updateSend(final MessageAlertOrder order) {
		return commonProcess(order, "更新", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				MessageAlertDO alert = messageAlertDAO.findById(order.getAlertId());
				if (null != alert) {
					alert.setLastAlertTime(getSysdate());
					messageAlertDAO.update(alert);
				}
				return null;
			}
		}, null, null);
	}
	
}
