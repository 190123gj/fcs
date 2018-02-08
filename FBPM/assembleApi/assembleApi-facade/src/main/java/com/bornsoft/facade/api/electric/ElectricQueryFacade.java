package com.bornsoft.facade.api.electric;

import com.bornsoft.pub.order.electric.ElectricInfoExportOrder;
import com.bornsoft.pub.order.electric.ElectricQueryOrder;
import com.bornsoft.pub.result.common.CoreExportDataResult;
import com.bornsoft.pub.result.electric.ElectricQueryResult;

/**
  * @Description:电量查询接口 
  * @author taibai@yiji.com 
  * @date  2016-8-15 下午5:28:39
  * @version V1.0
 */
public interface ElectricQueryFacade {
	/**
	 * 电量查询
	 * @param order
	 * @return
	 */
	public  ElectricQueryResult queryElectric(ElectricQueryOrder order);
	/**
	 * 用电量报表导出
	 * @param order
	 * @return
	 */
	public  CoreExportDataResult exportElectricInfo(ElectricInfoExportOrder order);
}
