package com.bornsoft.pub.result.common;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.bornsoft.utils.base.BornSynResultBase;

/**
 * 导出提数据
 * 
 * @Title: ExportDataResult.java
 * @Package com.bornsoft.bornfinance.bankroll.result
 * @author xiaohui@yiji.com
 * @date 2014-12-17 下午5:20:27
 * @version V1.0
 */
public class CoreExportDataResult extends BornSynResultBase {

	/**
	 */
	private static final long serialVersionUID = 1L;

	/** 下载地址 **/
	private String downloadUrl;

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
