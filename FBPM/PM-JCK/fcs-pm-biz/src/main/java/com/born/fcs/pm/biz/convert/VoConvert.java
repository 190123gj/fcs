package com.born.fcs.pm.biz.convert;

import com.born.fcs.pm.dataobject.SimpleFormProjectDO;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectVOInfo;
import com.yjf.common.lang.beans.cglib.BeanCopier;

/**
 * 将VO转化为VoInfo
 * 
 * @author lirz
 * 
 * 2016-5-19 下午2:31:13
 */
public class VoConvert {
	
	public static void convertSimpleFormProjectDO2VoInfo(SimpleFormProjectDO DO,
															SimpleFormProjectVOInfo info) {
		if (DO == null) {
			return;
		}
		
		BeanCopier.staticCopy(DO, info);
		//FormVOInfo
		info.setFormCode(FormCodeEnum.getByCode(DO.getFormCode()));
		info.setFormStatus(FormStatusEnum.getByCode(DO.getFormStatus()));
		//SimpleFormProjectVOInfo
		info.setCustomerType(CustomerTypeEnum.getByCode(DO.getCustomerType()));
		info.setTimeUnit(TimeUnitEnum.getByCode(DO.getTimeUnit()));
	}
}
