package com.born.fcs.pm.biz.service.financialkpi;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.daointerface.FFinancialKpiDAO;
import com.born.fcs.pm.dal.dataobject.FFinancialKpiDO;
import com.born.fcs.pm.util.DataFinancialHelper;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.KpiTypeEnum;
import com.born.fcs.pm.ws.info.financialkpi.FinancialKpiInfo;
import com.born.fcs.pm.ws.order.financialkpi.FinancialKpiOrder;
import com.born.fcs.pm.ws.service.financialkpi.FinancialKpiService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;

@Service("financialKpiService")
public class FinancialKpiServiceImpl extends BaseAutowiredDomainService implements
																		FinancialKpiService {
	
	@Autowired
	private FFinancialKpiDAO FFinancialKpiDAO;
	
	@Override
	public void save(long formId, KpiTypeEnum kpiType, List<FinancialKpiOrder> orders) {
		if (formId <= 0 || kpiType == null) {
			return;
		}
		
		if (ListUtil.isEmpty(orders)) {
			FFinancialKpiDAO.deleteByFormIdAndKpitype(formId, kpiType.code());
			return;
		}
		
		List<FFinancialKpiDO> kpies = FFinancialKpiDAO.findByFormIdAndKpitype(formId,
			kpiType.code());
		Map<Long, FFinancialKpiDO> map = new HashMap<Long, FFinancialKpiDO>();
		if (ListUtil.isNotEmpty(kpies)) {
			for (FFinancialKpiDO kpi : kpies) {
				map.put(kpi.getKpiId(), kpi);
			}
		}
		
		int sortOrder = 1;
		Date now = getSysdate();
		for (FinancialKpiOrder order : orders) {
			if (order.isNull()) {
				continue; //空数据不保存
			}
			order.setFormId(formId);
			order.setKpiType(kpiType.code());
			if (StringUtil.isNotEmpty(order.getKpiName())) {
				order.setKpiCode(DataFinancialHelper.NAMES_KEY.get(order.getKpiName()));
			}
			order.setSortOrder(sortOrder++);
			FFinancialKpiDO doObj = map.get(order.getKpiId());
			if (null == doObj) {
				doObj = new FFinancialKpiDO();
				BeanCopier.staticCopy(order, doObj);
				doObj.setRawAddTime(now);
				FFinancialKpiDAO.insert(doObj);
			} else {
				if (!isEquals(doObj, order)) {
					BeanCopier.staticCopy(order, doObj);
					FFinancialKpiDAO.update(doObj);
				}
			}
			map.remove(order.getKpiId());
		}
		
		if (null != map && map.size() > 0) {
			for (long kpiId : map.keySet()) {
				FFinancialKpiDAO.deleteById(kpiId);
			}
		}
	}
	
	private boolean isEquals(FFinancialKpiDO doObj, FinancialKpiOrder order) {
		return doObj != null && doObj.getKpiId() == order.getKpiId()
				&& doObj.getFormId() == order.getFormId()
				&& doObj.getParentId() == order.getParentId()
				&& StringUtil.equals(doObj.getKpiType(), order.getKpiType())
				&& StringUtil.equals(doObj.getKpiCode(), order.getKpiCode())
				&& StringUtil.equals(doObj.getKpiName(), order.getKpiName())
				&& StringUtil.equals(doObj.getKpiValue1(), order.getKpiValue1())
				&& StringUtil.equals(doObj.getKpiValue2(), order.getKpiValue2())
				&& StringUtil.equals(doObj.getKpiValue3(), order.getKpiValue3())
				&& StringUtil.equals(doObj.getKpiValue4(), order.getKpiValue4())
				&& StringUtil.equals(doObj.getKpiValue5(), order.getKpiValue5())
				&& StringUtil.equals(doObj.getKpiValue6(), order.getKpiValue6())
				&& StringUtil.equals(doObj.getKpiValue7(), order.getKpiValue7())
				&& StringUtil.equals(doObj.getRemark(), order.getRemark())
				&& doObj.getSortOrder() == order.getSortOrder();
	}
	
	@Override
	public void saveExplanation(FinancialKpiOrder data, List<FinancialKpiOrder> explanations) {
		if (null == data || data.getKpiId() <= 0) {
			return;
		}
		
		FFinancialKpiDO father = FFinancialKpiDAO.findById(data.getKpiId());
		if (null == father) {
			return;
		}
		
		if (!isEquals(father, data)) {
			BeanCopier.staticCopy(data, father);
			FFinancialKpiDAO.update(father);
		}
		
		if (ListUtil.isEmpty(explanations)) {
			return;
		}
		
		List<FFinancialKpiDO> kpies = FFinancialKpiDAO.findByParentId(father.getKpiId());
		Map<Long, FFinancialKpiDO> map = new HashMap<>();
		for (FFinancialKpiDO kpi : kpies) {
			map.put(kpi.getKpiId(), kpi);
		}
		
		int sortOrder = 1;
		Date now = getSysdate();
		for (FinancialKpiOrder child : explanations) {
			child.setFormId(father.getFormId());
			child.setSortOrder(sortOrder++);
			FFinancialKpiDO doObj = map.get(child.getKpiId());
			if (null == doObj) {
				doObj = new FFinancialKpiDO();
				BeanCopier.staticCopy(child, doObj);
				doObj.setRawAddTime(now);
				FFinancialKpiDAO.insert(doObj);
			} else {
				if (!isEquals(doObj, child)) {
					BeanCopier.staticCopy(child, doObj);
					FFinancialKpiDAO.update(doObj);
				}
			}
		}
	}

	@Override
	public FinancialKpiInfo queryById(long kpiId) {
		FFinancialKpiDO doObj = FFinancialKpiDAO.findById(kpiId);
		if (null == doObj) {
			return null;
		}
		
		FinancialKpiInfo info = new FinancialKpiInfo();
		BeanCopier.staticCopy(doObj, info);
		return info;
	}

	@Override
	public Map<KpiTypeEnum, List<FinancialKpiInfo>> queryByFormId(long formId) {
		List<FFinancialKpiDO> list = FFinancialKpiDAO.findByFormId(formId);
		if (ListUtil.isEmpty(list)) {
			return null;
		}
		
		Map<KpiTypeEnum, List<FinancialKpiInfo>> map = new HashMap<>();
		for (FFinancialKpiDO doObj : list) {
			KpiTypeEnum type = KpiTypeEnum.getByCode(doObj.getKpiType());
			List<FinancialKpiInfo> infos = map.get(type);
			if (null == infos) {
				infos = new ArrayList<>();
				map.put(type, infos);
			}
			
			FinancialKpiInfo info = new FinancialKpiInfo();
			BeanCopier.staticCopy(doObj, info);
			infos.add(info);
		}
		
		return map;
	}

}
