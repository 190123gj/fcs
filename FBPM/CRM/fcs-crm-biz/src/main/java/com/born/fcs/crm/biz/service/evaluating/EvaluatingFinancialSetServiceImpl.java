package com.born.fcs.crm.biz.service.evaluating;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.biz.service.customer.BaseAutowiredDAO;
import com.born.fcs.crm.dal.dataobject.EvaluatingFinancialSetDO;
import com.born.fcs.crm.ws.service.EvaluatingFinancialSetService;
import com.born.fcs.crm.ws.service.ListDataSaveService;
import com.born.fcs.crm.ws.service.info.FinancialSetInfo;
import com.born.fcs.crm.ws.service.order.ListOrder;
import com.born.fcs.crm.ws.service.order.query.FinancialSetQueryOrder;
import com.born.fcs.crm.ws.service.result.FinancialSetResult;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.StringUtil;

@Service("evaluatingFinancialSetService")
public class EvaluatingFinancialSetServiceImpl extends BaseAutowiredDAO implements
																		EvaluatingFinancialSetService {
	
	@Autowired
	private ListDataSaveService listDataSaveService;
	
	@Override
	public FcsBaseResult update(ListOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			List<FinancialSetInfo> financialList = order.getFinancialSetInfo();
			for (FinancialSetInfo info : financialList) {
				EvaluatingFinancialSetDO evaluatingFinancialSet = new EvaluatingFinancialSetDO();
				BeanCopier.staticCopy(info, evaluatingFinancialSet);
				setCalculat(evaluatingFinancialSet);
				if (info.getId() > 0) {
					evaluatingFinancialSetDAO.update(evaluatingFinancialSet);
				} else {
					evaluatingFinancialSetDAO.insert(evaluatingFinancialSet);
				}
			}
			long year = Long.parseLong(sqlDateService.getYear());
			listDataSaveService.updateByList(order.getListData1(), year, null);
			listDataSaveService.updateByList(order.getListData2(), year, null);
			result.setSuccess(true);
		} catch (IllegalArgumentException e) {
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(e.getMessage());
			logger.error("财务指标配置异常：", e);
		} catch (DataAccessException e) {
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage(e.getMessage());
			logger.error("财务指标配置异常：", e);
		} catch (Exception e) {
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage(e.getMessage());
			logger.error("财务指标配置异常：", e);
		}
		
		return result;
	}
	
	@Override
	public FinancialSetResult list(FinancialSetQueryOrder queryOrder) {
		FinancialSetResult result = new FinancialSetResult();
		try {
			EvaluatingFinancialSetDO evaluatingFinancialSet = new EvaluatingFinancialSetDO();
			BeanCopier.staticCopy(queryOrder, evaluatingFinancialSet);
			List<EvaluatingFinancialSetDO> list = evaluatingFinancialSetDAO
				.findWithCondition(evaluatingFinancialSet);
			Map<String, FinancialSetInfo> mapResult = new HashMap<String, FinancialSetInfo>();
			FinancialSetInfo info = null;
			for (EvaluatingFinancialSetDO DO : list) {
				info = new FinancialSetInfo();
				BeanCopier.staticCopy(DO, info);
				mapResult.put(info.getTypeChild(), info);
			}
			queryListData(result, queryOrder.getType());
			result.setMapResult(mapResult);
			result.setSuccess(true);
		} catch (IllegalArgumentException e) {
			result.setMessage(e.getMessage());
			logger.error("财务指标配置异常：", e);
		} catch (DataAccessException e) {
			result.setMessage(e.getMessage());
			logger.error("财务指标配置异常：", e);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			logger.error("财务指标配置异常：", e);
		}
		return result;
	}
	
	/**
	 * 计算公式设置
	 * @param ts_表示特殊计算公式
	 * */
	@SuppressWarnings("unchecked")
	private static void setCalculat(EvaluatingFinancialSetDO evaluatingFinancialSet) {
		String calculatingFormula = evaluatingFinancialSet.getCalculatingFormula();
		if (StringUtil.isNotBlank(calculatingFormula) && calculatingFormula.indexOf("ts_") == -1) {
			Map<String, String> map = BeanMap.create(evaluatingFinancialSet);
			Object mapArray[] = map.keySet().toArray();
			for (int i = 0; i < mapArray.length; i++) {
				String key = (String) mapArray[i];
				Object value = map.get(mapArray[i]);
				if (value != null) {
					calculatingFormula = calculatingFormula.replaceAll(key, String.valueOf(value));
				}
			}
			evaluatingFinancialSet.setCalculatingFormula(calculatingFormula);
		}
	}
	
	/** 一般企业财务指标配置都有两个集合信息：type=财务指标类型+1/2 */
	private void queryListData(FinancialSetResult result, String type) {
		try {
			result.setListData1(listDataSaveService.list(type + "1"));
			result.setListData2(listDataSaveService.list(type + "2"));
		} catch (Exception e) {
			logger.error("一般财务指标查询集合数据失败：", e);
		}
		
	}
}
