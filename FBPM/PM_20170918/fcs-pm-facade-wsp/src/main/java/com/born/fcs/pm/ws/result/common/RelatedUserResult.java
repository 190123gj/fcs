package com.born.fcs.pm.ws.result.common;

import com.born.fcs.pm.ws.info.common.RelatedUserInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 设置相关人员结果
 *
 *
 * @author wuzj
 *
 */
public class RelatedUserResult extends FcsBaseResult {
	
	private static final long serialVersionUID = -2431611369815479085L;
	
	private RelatedUserInfo relatedUser;
	
	public RelatedUserInfo getRelatedUser() {
		return this.relatedUser;
	}
	
	public void setRelatedUser(RelatedUserInfo relatedUser) {
		this.relatedUser = relatedUser;
	}
	
}
