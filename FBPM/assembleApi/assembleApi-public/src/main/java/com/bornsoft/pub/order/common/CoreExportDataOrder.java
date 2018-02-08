package com.bornsoft.pub.order.common;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.bornsoft.pub.enums.CoreExportTypeEnum;
import com.bornsoft.utils.base.BornOutOrderBase;
import com.bornsoft.utils.exception.BornApiException;

/**
 * 导出报表
 * @Title: ExportDataOrder.java 
 * @Package com.bornsoft.bornfinance.bankroll.order 
 * @author xiaohui@yiji.com   
 * @date 2014-12-17 下午5:21:09 
 * @version V1.0
 */
public class CoreExportDataOrder extends BornOutOrderBase{

	/**
	 */
	private static final long serialVersionUID = 1L;
	
	private CoreExportTypeEnum exportType;

	

	public CoreExportTypeEnum getExportType() {
		return exportType;
	}

	public void setExportType(CoreExportTypeEnum exportType) {
		this.exportType = exportType;
	}


	/**
	 * 参数合规性检查
	 * @throws Exception
	 */
	public void validateOrder()   {
		super.validateOrder();
		if(exportType == null){
			throw new BornApiException("导出数据类型[exportType]超出指定值范围!");
		}
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
