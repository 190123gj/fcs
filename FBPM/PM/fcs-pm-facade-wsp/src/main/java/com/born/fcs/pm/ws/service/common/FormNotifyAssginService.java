package com.born.fcs.pm.ws.service.common;

import java.util.Map;

import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.FormMessageTempleteInfo;
import com.born.fcs.pm.ws.info.common.FormRelatedUserInfo;

/**
 * 通知交办人Service
 *
 *
 * @author wuzj
 *
 */
public interface FormNotifyAssginService {
	
	/**
	 * 通知交办人
	 * @param formInfo
	 * @param assignMan
	 * @param templete
	 * @param messageVars
	 */
	void notifyAssign(FormInfo formInfo, FormRelatedUserInfo assignMan,
						FormMessageTempleteInfo templete, Map<String, String> messageVars);
}
