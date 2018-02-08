package com.bornsoft.api.service.app.entity;

import com.bornsoft.utils.base.IBornEnum;

/**
 * @Description: 审核支持的按钮 
 * @author taibai@yiji.com
 * @date 2016-10-14 下午3:38:39
 * @version V1.0
 */
public enum AuditBtnEnum implements IBornEnum{
	
	// 默认借口
	fn_AuditBtnPass("1", "通过","/projectMg/form/workflow/processs/donext.json"), 
	fn_AuditBtnRefuse("2","反对", "/projectMg/form/workflow/processs/dorefuse.json"), 
	fn_AuditBtnNoPass("4,5","不通过", "/projectMg/form/workflow/processs/doback.json"), 
	fn_AuditBtnBack("fnAuditBtnBack","驳回", "/projectMg/form/workflow/processs/doGoToBack.json"), 
	fn_NoPassToNodeBtn("50","驳回到指定步骤","/projectMg/form/workflow/processs/doGoToBackNode.json"), // 
	fn_EndFormBtn("15,18","终止", "/projectMg/form/workflow/processs/doEndWorkflow.json"),
	fn_AssignBtn("6","交办","/projectMg/form/workflow/processs/doTaskAssign.json"),;
	
	/****#operatortype#*****/
	private String code;
	/*****按钮描述******/
	private String name;
	/*****提交地址******/
	private String url;
	
	private AuditBtnEnum(String code, String name, String url) {
		this.code = code;
		this.name = name;
		this.url = url;
	}
	
	@Override
	public String code() {
		return code;
	}
	@Override
	public String message() {
		return name;
	}
	public String getUrl() {
		return url;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 根据operateCode获取按钮
	 * @param operateCode
	 * @return
	 */
	public static AuditBtnEnum getByCode(String operateCode){
		String []tmp = null;
		for(AuditBtnEnum e : AuditBtnEnum.values()){
			tmp = e.code.split(",");
			for(String t : tmp){
				if(t.equals(operateCode)){
					return e;
				}
			}
		}
		return null;
	}
	
	
}
