package com.bornsoft.api.service.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bornsoft.facade.api.umeng.UmenMessageSendFacade;
import com.bornsoft.facade.order.UMengSendOrder;
import com.bornsoft.integration.jck.config.SystemParamCacheHolder;
import com.bornsoft.integration.umeng.UmenMessageSendClient;
import com.bornsoft.pub.order.umeng.UMengOrder;
import com.bornsoft.pub.order.umeng.UMengOrder.MessageTypeEnum;
import com.bornsoft.pub.result.umeng.UmengSendResult;
import com.bornsoft.utils.constants.ApiSystemParamEnum;
import com.bornsoft.utils.constants.SwitchEnum;
import com.bornsoft.utils.enums.CommonResultEnum;
import com.bornsoft.utils.exception.BornApiException;

@Service("umenMessageSendFacadeApi")
public class UmenMessageSendFacadeImpl implements UmenMessageSendFacade {

	private static final Logger logger = LoggerFactory
			.getLogger(UmenMessageSendFacadeImpl.class);
	@Autowired
	private UmenMessageSendClient umenMessageSendClient;
	@Autowired
	private SystemParamCacheHolder systemParamCacheHolder;

	@Override
	public UmengSendResult send(UMengSendOrder order) {
		logger.info("发送通知,入参={}", order);
		UmengSendResult result = null;
		try {
			order.validateOrder();
			String umSwitch = systemParamCacheHolder.getConfig(ApiSystemParamEnum.UMeng_Switch);
			if(SwitchEnum.Off.code().equals(umSwitch)){
				result =  new UmengSendResult();
				result.setResultCode(CommonResultEnum.EXECUTE_SUCCESS);
				result.setResultMessage("友盟开关关闭,模拟成功");
			}else{
				UMengOrder umOrder = new UMengOrder();
				umOrder.setOrderNo(order.getOrderNo());
				umOrder.setText(order.getText());
				umOrder.setTicker(order.getTicker());
				umOrder.setTitle(order.getTitle());
				umOrder.setUserName(order.getUserName());
				if(umOrder.getText().contains("点击处理")){
					umOrder.setMessageType(MessageTypeEnum.Wait);
				}else{
					umOrder.setMessageType(MessageTypeEnum.Message);
				}
				result = umenMessageSendClient.send(umOrder);
			}
		}catch(BornApiException e){
			logger.error("发送异常", e);
			result =  new UmengSendResult();
			result.setResultCode(CommonResultEnum.EXECUTE_FAILURE);
			result.setResultMessage(e.getMessage());
		} catch (Exception e) {
			logger.error("发送异常", e);
			result =  new UmengSendResult();
			result.setResultCode(CommonResultEnum.UN_KNOWN_EXCEPTION);
			result.setResultMessage(e.getMessage());
		}
		result.setOrderNo(order.getOrderNo());
		logger.info("发送通知,出参={}", result);
		return result;
	}

}
