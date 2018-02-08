package com.bornsoft.api.service.app;

import com.born.fcs.pm.util.StringUtil;
import com.bornsoft.utils.base.IBornEnum;

/**
 * @Description: 项目模块枚举 
 * @author taibai@yiji.com
 * @date 2016-11-7 下午2:45:52
 * @version V1.0
 */
public enum ModuleEnum implements IBornEnum{
	Fund("fundMg","资金模块"),
	Asset("assetMg","资产模块"),
	Report("reportMg","报表模块"),
	Customer("customerMg","客户管理模块"),
	
	Project("projectMg","项目[默认]"),
	;

	private String code;
	private String message;
	
	private ModuleEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String code() {
		return code;
	}
	@Override
	public String message() {
		return message;
	}
	
	public static ModuleEnum getByCode(String module){
		if(StringUtil.isBlank(module)){
			return Project;
		}
		for(ModuleEnum m : values()){
			if(m.code.equals(module)){
				return m;
			}
		}
		throw null;
	}
}
