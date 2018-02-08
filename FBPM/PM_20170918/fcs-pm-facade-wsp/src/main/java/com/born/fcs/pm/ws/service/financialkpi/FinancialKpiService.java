package com.born.fcs.pm.ws.service.financialkpi;

import java.util.List;
import java.util.Map;

import com.born.fcs.pm.ws.enums.KpiTypeEnum;
import com.born.fcs.pm.ws.info.financialkpi.FinancialKpiInfo;
import com.born.fcs.pm.ws.order.financialkpi.FinancialKpiOrder;

/**
 * 
 * 财务指标
 * 
 * @author lirz
 *
 * 2016-6-3 上午10:39:02
 */
public interface FinancialKpiService {
	void save(long formId, KpiTypeEnum kpiType, List<FinancialKpiOrder> orders);
	
	void saveExplanation(FinancialKpiOrder data, List<FinancialKpiOrder> explanations);
	
	FinancialKpiInfo queryById(long kpiId);
	
	Map<KpiTypeEnum, List<FinancialKpiInfo>> queryByFormId(long formId);
	
	List<FinancialKpiInfo> queryByFormIdAndType(long formId, KpiTypeEnum type);
}
