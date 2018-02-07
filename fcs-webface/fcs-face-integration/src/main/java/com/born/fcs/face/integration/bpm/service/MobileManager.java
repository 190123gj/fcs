/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.face.integration.bpm.service;

import com.born.fcs.face.integration.enums.FunctionalModulesEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.yjf.common.lang.result.Result;

/**
 * 
 * @Filename MobileManager.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2013-2-24</li> <li>Version: 1.0
 * </li> <li>Content: create</li>
 * 
 */
public interface MobileManager {
	public Result sendMobileValidateCode(String mobileNumber,
											FunctionalModulesEnum functionalModulesEnum);
	
	public FcsBaseResult equalValidateCodeUseResult(String validateCode,
													FunctionalModulesEnum functionalModulesEnum);
	
	public boolean equalValidateCode(String validateCode,
										FunctionalModulesEnum functionalModulesEnum);
	
	public FcsBaseResult equalValidateCode(String validateCode,
											FunctionalModulesEnum functionalModulesEnum,
											boolean isRemove);
	
	public FcsBaseResult clearValidateCode();
}
