package com.born.fcs.pm.biz.service.common;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.biz.service.common.info.SysParamInfo;
import com.born.fcs.pm.ws.order.sysParam.SysParamOrder;
import com.born.fcs.pm.ws.order.sysParam.SysParamQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 
 * @Filename SysParamServcie.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2013-2-22</li> <li>Version: 1.0
 * </li> <li>Content: create</li>
 * 
 */
@WebService
public interface SysParameterService {
	public String getSysParameterValue(String paramName);
	
	public SysParamInfo getSysParameterValueDO(String paramName);
	
	public FcsBaseResult updateSysParameterValueDO(SysParamOrder sysParamOrder);
	
	public FcsBaseResult insertSysParameterValueDO(SysParamOrder sysParamOrder);
	
	public void deleteSysParameterValue(String paramName);
	
	public List<SysParamInfo> getSysParameterValueList(String paramName);
	
	public QueryBaseBatchResult<SysParamInfo> querySysPram(SysParamQueryOrder sysParamQueryOrder);
	
	public void clearCache();
	
}
