package com.bornsoft.facade.result;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.bornsoft.utils.base.BornSynResultBase;

/**
 * Description: 短信发送结果
 * @author:fruxue@yiji.com
 * @date:2016-2-5上午10:00:57 
 * @version:V1.0
 */
public class SmsSendResult extends BornSynResultBase{

	/**
	 */
	private static final long serialVersionUID = 1L;
	/** 描述 **/
	private String description;
	
	public SmsSendResult() {}
	
	public SmsSendResult(int totalCount, int successCount, int failureCount, String description) {
		super();
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
