package com.bornsoft.facade.result;

import com.bornsoft.utils.base.BornResultBase;

/**
 * @Description: APP 最新版本result,如果未找到则resultCode返回失败即可
 * @author taibai@yiji.com
 * @date 2016-10-27 上午10:14:30
 * @version V1.0
 */
public class AppCheckUpdateResult extends BornResultBase{

	/**
	 */
	private static final long serialVersionUID = 1L;
	
	/**强制升级版本*/
	private String forceUpdateCode;			
	/**可选升级版本*/
	private String optionalUpdateCode;	
	/**更新提示信息*/
	private String updateInfo;	
	/**更新地址*/
	private String updateUrl;
	
	public String getForceUpdateCode() {
		return forceUpdateCode;
	}
	public void setForceUpdateCode(String forceUpdateCode) {
		this.forceUpdateCode = forceUpdateCode;
	}
	public String getOptionalUpdateCode() {
		return optionalUpdateCode;
	}
	public void setOptionalUpdateCode(String optionalUpdateCode) {
		this.optionalUpdateCode = optionalUpdateCode;
	}
	public String getUpdateInfo() {
		return updateInfo;
	}
	public void setUpdateInfo(String updateInfo) {
		this.updateInfo = updateInfo;
	}
	public String getUpdateUrl() {
		return updateUrl;
	}
	public void setUpdateUrl(String updateUrl) {
		this.updateUrl = updateUrl;
	}

}
