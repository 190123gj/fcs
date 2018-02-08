package com.bornsoft.api.service.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.util.StringUtil;
import com.bornsoft.facade.api.sms.SmsFacade;
import com.bornsoft.facade.order.SmsSendOrder;
import com.bornsoft.facade.result.SmsSendResult;
import com.bornsoft.integration.jck.config.SystemParamCacheHolder;
import com.bornsoft.integration.sms.EmaySmsClient;
import com.bornsoft.integration.sms.HuaruanDistanceClient;
import com.bornsoft.integration.sms.enums.SmsChannelEnum;
import com.bornsoft.jck.dal.daointerface.SmsSendRecordDAO;
import com.bornsoft.jck.dal.dataobject.SmsSendRecordDO;
import com.bornsoft.utils.base.BornResultBase;
import com.bornsoft.utils.constants.ApiSystemParamEnum;
import com.bornsoft.utils.constants.SwitchEnum;
import com.bornsoft.utils.enums.CommonResultEnum;
import com.bornsoft.utils.tool.CommonUtil;
import com.bornsoft.utils.tool.DateUtils;
import com.bornsoft.utils.tool.InstallCommonResultUtil;

/**
 * @Description: 短信服务 
 * @author taibai@yiji.com
 * @date 2016-12-1 上午10:53:01
 * @version V1.0
 */
@Service("smsFacadeApi")
public class SmsFacadeImpl implements SmsFacade {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SystemParamCacheHolder systemParamCacheHolder;
	
	@Autowired
	private HuaruanDistanceClient huaruanDistanceClient;
	@Autowired
	private EmaySmsClient emaySmsClient;
	
	@Autowired
	private SmsSendRecordDAO smsSendRecordDAO;
	
	@Override
	public SmsSendResult sendSms(SmsSendOrder order) {
		logger.info("调用发送短信接口，入参order={}", order);
		SmsSendResult result = new SmsSendResult();
		
		boolean mock = false;
		SmsChannelEnum channel = SmsChannelEnum.UnKnown;
		try {
			//校验参数
			order.validateOrder();
			
			if(StringUtil.equals(systemParamCacheHolder.getConfig(ApiSystemParamEnum.Sms_Switch),SwitchEnum.Off.code())){
				InstallCommonResultUtil.installCommonResult("模拟发送成功",CommonResultEnum.EXECUTE_SUCCESS, result, order);
				mock = true;
			}else{
				BornResultBase smsResult = null;
				channel = SmsChannelEnum.getByCode(systemParamCacheHolder.getConfig(ApiSystemParamEnum.Sms_Channel));
				
				switch (channel) {
					case Huaruan:
						smsResult = huaruanDistanceClient.send(order.getMobile(),order.getContent());
						break;
					case Emay:
						smsResult = emaySmsClient.send(order.getMobile(),order.getContent());
						break;
				}
				
				//结果判断
				if (smsResult.getResultCode() != CommonResultEnum.EXECUTE_SUCCESS) {
					InstallCommonResultUtil.installCommonResult(smsResult.getResultMessage(),CommonResultEnum.EXECUTE_FAILURE, result, order);
				}else{
					InstallCommonResultUtil.installCommonResult("发送成功",CommonResultEnum.EXECUTE_SUCCESS, result, order);
				}
			}
			
		} catch (Exception ex) {
			logger.error("短信发送失败", ex);
			InstallCommonResultUtil.installCommonResult(ex.getMessage(),CommonResultEnum.EXECUTE_FAILURE, result, order);
		}finally{
			if(!mock){
				saveRecord(order.getMobile(),order.getContent(),result,channel);
			}
		}
		logger.info("调用发送短信接口，出参result={}", result);
		return result;
	}
	
	/**
	 * 保存短信调用记录
	 * @param mobile
	 * @param content
	 * @param result
	 * @param channel 
	 */
	private void saveRecord(String mobile, String content, BornResultBase result, SmsChannelEnum channel) {
		try {
			SmsSendRecordDO smsSendRecord = new SmsSendRecordDO();
			smsSendRecord.setChannel(channel.code());
			smsSendRecord.setContent(CommonUtil.cutString(content, 200));
			smsSendRecord.setMobile(mobile);
			if(result.getResultCode() == CommonResultEnum.EXECUTE_SUCCESS){
				smsSendRecord.setStatus("1");
			}else{
				smsSendRecord.setStatus("0");
			}
			smsSendRecord.setRowAddTime(DateUtils.getCurrentDate());
			logger.info("保存短信调用记录:{}",smsSendRecord);
			smsSendRecordDAO.insert(smsSendRecord);
		} catch (Exception e) {
			logger.info("保存短信调用记录失败");
		}
	}
}
