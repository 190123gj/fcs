package com.born.fcs.pm.biz.service.shortmessage;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.ShortMessageDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.shortmessage.ShortMessageInfo;
import com.born.fcs.pm.ws.order.shortmessage.ShortMessageOrder;
import com.born.fcs.pm.ws.order.shortmessage.ShortMessageQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.shortmessage.ShortMessageService;
import com.born.fcs.pm.ws.service.sms.SMSService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("shortMessageService")
public class ShortMessageServiceImpl extends BaseFormAutowiredDomainService implements
																			ShortMessageService {
	
	@Autowired
	protected SMSService sMSService;
	
	private ShortMessageInfo convertDO2Info(ShortMessageDO DO) {
		if (DO == null)
			return null;
		ShortMessageInfo info = new ShortMessageInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	@Override
	public FcsBaseResult save(final ShortMessageOrder order) {
		
		return commonProcess(order, "保存短信信息并发送短信", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				ShortMessageDO DO = null;
				if (order.getId() != null && order.getId() > 0) {
					DO = shortMessageDAO.findById(order.getId());
					if (DO == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"发送的短信信息不存在");
					}
				}
				
				if (DO == null) { //新增
				
					DO = new ShortMessageDO();
					BeanCopier.staticCopy(order, DO, UnBoxingConverter.getInstance());
					
					DO.setRawAddTime(now);
					//发送短信
					String messageContent = DO.getMessageContent();
					if (DO.getMessageReceiver() != null) {
						String[] receivers = DO.getMessageReceiver().split(",");//接收人 手机号码，多个以逗号隔开
						for (String s : receivers) {
							//发送短信
							sMSService.sendSMSWithSwitch(s, messageContent, true);
						}
					}
					shortMessageDAO.insert(DO);
					
				} else { //修改
					BeanCopier.staticCopy(order, DO, UnBoxingConverter.getInstance());
					shortMessageDAO.update(DO);
				}
				FcsPmDomainHolder.get().addAttribute("shortMessageDO", DO);
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				ShortMessageDO DO = (ShortMessageDO) FcsPmDomainHolder.get().getAttribute(
					"shortMessageDO");
				if (null == DO) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
						"发送的短信信息不存在");
				}
				return null;
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ShortMessageInfo> query(ShortMessageQueryOrder order) {
		QueryBaseBatchResult<ShortMessageInfo> baseBatchResult = new QueryBaseBatchResult<ShortMessageInfo>();
		
		ShortMessageDO queryCondition = new ShortMessageDO();
		
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);
		String beginMessageSendDate = null;
		if (StringUtil.isNotEmpty(order.getBeginMessageSendDate())) {
			beginMessageSendDate = order.getBeginMessageSendDate();
		}
		String endMessageSendDate = null;
		if (StringUtil.isNotEmpty(order.getEndMessageSendDate())) {
			endMessageSendDate = order.getEndMessageSendDate();
		}
		long totalSize = shortMessageDAO.findByConditionCount(queryCondition, beginMessageSendDate,
			endMessageSendDate);
		
		PageComponent component = new PageComponent(order, totalSize);
		
		List<ShortMessageDO> pageList = shortMessageDAO.findByCondition(queryCondition,
			component.getFirstRecord(), component.getPageSize(), beginMessageSendDate,
			endMessageSendDate);
		
		List<ShortMessageInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (ShortMessageDO DO : pageList) {
				list.add(convertDO2Info(DO));
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public ShortMessageInfo findById(long id) {
		ShortMessageDO DO = shortMessageDAO.findById(id);
		return convertDO2Info(DO);
	}
}
