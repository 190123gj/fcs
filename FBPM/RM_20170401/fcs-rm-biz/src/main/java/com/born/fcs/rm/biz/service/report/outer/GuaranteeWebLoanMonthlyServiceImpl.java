package com.born.fcs.rm.biz.service.report.outer;

import org.springframework.stereotype.Service;

import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.rm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;
import com.born.fcs.rm.ws.service.report.ReportProcessService;

/**
 * 
 * 外部报表<br />
 * 市金融办月/季报<br />
 * （市金融办）融资担保机构互联网融资担保月报表
 * 
 * @author lirz
 *
 * 2016-8-26 下午2:09:24
 */
@Service("guaranteeWebLoanMonthlyService")
public class GuaranteeWebLoanMonthlyServiceImpl extends BaseAutowiredDomainService implements
																					ReportProcessService {
	@Override
	public FcsBaseResult save(ReportOrder order) {
		return null;
	}
	
	@Override
	public Object findByAccountPeriod(ReportQueryOrder queryOrder) {
		//该报表暂不取数据
		return null;
	}
	
}
