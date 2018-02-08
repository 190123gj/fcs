package com.bornsoft.utils.base;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @Description: 商户信息结果
 * @author taibai@yiji.com
 * @date 2016-1-27 下午2:20:28
 * @version V1.0
 */
public class MerchantInfo extends BornSynResultBase {
    /**
	 */
	private static final long serialVersionUID = 1L;

    /**商户号*/
    private String merchantNo;
    /**商户秘钥*/
    private String merchantKey;

    public MerchantInfo(String merchantNo, String merchantKey) {
		this.merchantNo = merchantNo;
		this.merchantKey = merchantKey;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getMerchantKey() {
		return merchantKey;
	}
	public void setMerchantKey(String merchantKey) {
		this.merchantKey = merchantKey;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
