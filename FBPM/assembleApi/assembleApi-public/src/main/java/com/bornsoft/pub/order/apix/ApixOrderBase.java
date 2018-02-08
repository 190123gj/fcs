package com.bornsoft.pub.order.apix;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.bornsoft.utils.base.BornOrderBase;
import com.bornsoft.utils.tool.SerializeAlias;

/**
 * @Description: Apix 基础order
 * @author taibai@yiji.com
 * @date 2016-8-25 上午11:29:54
 * @version V1.0
 */
public abstract  class ApixOrderBase extends BornOrderBase {
	
	/**
	 */
	private static final long serialVersionUID = -6305141192584920010L;

	@SerializeAlias(alias = "apix-key")
	private String apixKey;
	
	public String getApixKey() {
		return apixKey;
	}

	public void setApixKey(String apixKey) {
		this.apixKey = apixKey;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public abstract String getUrl();
}
