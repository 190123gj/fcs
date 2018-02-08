package com.bornsoft.pub.result.risk;

import com.alibaba.fastjson.JSONObject;
import com.bornsoft.utils.base.BornSynResultBase;
import com.bornsoft.utils.tool.JsonParseUtil;

/**
  * @Description: 企业信息校验校验接口 
  * @author taibai@yiji.com 
  * @date  2016-8-6 下午3:40:54
  * @version V1.0
 */
public class VerifyOrganizationResult extends BornSynResultBase {
	/**
	 */
	private static final long serialVersionUID = 1L;
	
	/**校验结果**/
	private boolean verySuccess;
	
	@Override
	protected void setData(String data) {
		JSONObject json = JsonParseUtil.parseObject(data, JSONObject.class);
		this.verySuccess = json.getBooleanValue("veryFlag");
	}

	public boolean isVerySuccess() {
		return verySuccess;
	}

	public void setVerySuccess(boolean verySuccess) {
		this.verySuccess = verySuccess;
	}

}
