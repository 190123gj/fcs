package com.born.fcs.crm.biz.service.evaluating;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptException;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.biz.service.customer.BaseAutowiredDAO;
import com.born.fcs.crm.dal.dataobject.EvaluatingBaseQueryDO;
import com.born.fcs.crm.dal.dataobject.EvaluatingBaseSetDO;
import com.born.fcs.crm.ws.service.EvaluatingBaseSetService;
import com.born.fcs.crm.ws.service.enums.StandardTypeEnums;
import com.born.fcs.crm.ws.service.info.CustomerFinanInfo;
import com.born.fcs.crm.ws.service.info.EvaluatingBaseQueryInfo;
import com.born.fcs.crm.ws.service.info.EvaluatingBaseSetInfo;
import com.born.fcs.crm.ws.service.order.EvaluatingBaseSetOrder;
import com.born.fcs.crm.ws.service.order.query.EvaluatingBaseSetQueryOrder;
import com.born.fcs.crm.ws.service.result.CityEvalueSetResult;
import com.born.fcs.crm.ws.service.result.PublicCauseSetResult;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;

@Service("evaluatingBaseSetService")
public class EvaluatingBaseSetServiceImpl extends BaseAutowiredDAO implements
																	EvaluatingBaseSetService {
	
	@Override
	public FcsBaseResult add(EvaluatingBaseSetOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			EvaluatingBaseSetDO evaluatingBaseSet = new EvaluatingBaseSetDO();
			BeanCopier.staticCopy(order, evaluatingBaseSet);
			evaluatingBaseSet.setRawAddTime(sqlDateService.getSqlDate());
			long id = evaluatingBaseSetDAO.insert(evaluatingBaseSet);
			if (StringUtil.equals(evaluatingBaseSet.getLevel(), "1")) {
				evaluatingBaseSet.setId(0);
				evaluatingBaseSet.setLevel1Id(id);
				evaluatingBaseSet.setLevel("2");
				evaluatingBaseSetDAO.insert(evaluatingBaseSet);
			} else if (StringUtil.equals(evaluatingBaseSet.getLevel(), "2")
						&& StringUtil.notEquals(StandardTypeEnums.GYYBZ.code(), order.getType())
						&& StringUtil.notEquals(StandardTypeEnums.CTCW.code(), order.getType())) {
				evaluatingBaseSet.setId(0);
				evaluatingBaseSet.setLevel2Id(id);
				evaluatingBaseSet.setLevel("3");
				evaluatingBaseSetDAO.insert(evaluatingBaseSet);
			} else if (StringUtil.equals(evaluatingBaseSet.getLevel(), "3")
						&& StringUtil.equals(StandardTypeEnums.YBQY.code(), order.getType())) {
				evaluatingBaseSet.setId(0);
				evaluatingBaseSet.setLevel3Id(id);
				evaluatingBaseSet.setLevel("4");
				evaluatingBaseSetDAO.insert(evaluatingBaseSet);
			}
			result.setKeyId(id);
			result.setSuccess(true);
			result.setMessage("增加成功");
		} catch (IllegalArgumentException e) {
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(e.getMessage());
			logger.error("新增指标配置异常：", e);
		} catch (DataAccessException e) {
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("新增指标配置异常：", e);
		} catch (Exception e) {
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage("未知异常");
			logger.error("新增指标配置异常：", e);
		}
		return result;
	}
	
	@Override
	public EvaluatingBaseSetInfo queryById(long id) {
		EvaluatingBaseSetInfo info = null;
		try {
			info = new EvaluatingBaseSetInfo();
			EvaluatingBaseSetDO doInfo = evaluatingBaseSetDAO.findById(id);
			BeanCopier.staticCopy(doInfo, info);
		} catch (Exception e) {
			logger.error("查询异常：", e);
		}
		return info;
	}
	
	@Override
	public FcsBaseResult updateById(EvaluatingBaseSetOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			EvaluatingBaseSetDO evaluatingBaseSet = new EvaluatingBaseSetDO();
			BeanCopier.staticCopy(order, evaluatingBaseSet);
			//先更新自己
			evaluatingBaseSetDAO.updateById(evaluatingBaseSet);
			long updateResult = 0;//批量更新子级数量
			//更新子级
			if (StringUtil.equals(evaluatingBaseSet.getLevel(), "1")) {
				//如果是1级指标 则更新levelId与当前id值相同的所有项
				evaluatingBaseSet.setLevel1Id(evaluatingBaseSet.getId());
				updateResult = evaluatingBaseSetDAO.updateByLevel1Id(evaluatingBaseSet);
				//若批量更新数量为0,表示没有子类，则添加1个子类
				if (updateResult == 0) {
					//将子类对应等级的id设置成父类Id
					order.setLevel1Id(order.getId());
					order.setId(0);
					order.setLevel("2");
					add(order);
				}
			} else if (StringUtil.equals(evaluatingBaseSet.getLevel(), "2")
						&& StringUtil.notEquals(StandardTypeEnums.GYYBZ.code(), order.getType())
						&& StringUtil.notEquals(StandardTypeEnums.CTCW.code(), order.getType())) {
				// 城市开发类 公用有标准 第3级指标合并到2级，无子级
				//需要更新的字段不一样，根据类型选择
				evaluatingBaseSet.setLevel2Id(evaluatingBaseSet.getId());
				if (StringUtil.equals(StandardTypeEnums.CTBZ.code(), order.getType())) {
					updateResult = evaluatingBaseSetDAO.updateByLevel2IdForCtBz(evaluatingBaseSet);
				} else {
					updateResult = evaluatingBaseSetDAO.updateByLevel2Id(evaluatingBaseSet);
				}
				if (updateResult == 0) {
					order.setLevel2Id(order.getId());
					order.setId(0);
					order.setLevel("3");
					add(order);
				}
			} else if (StringUtil.equals(evaluatingBaseSet.getLevel(), "3")
						&& StringUtil.equals(StandardTypeEnums.YBQY.code(), order.getType())) {
				//仅一般企业有4级指标
				evaluatingBaseSet.setLevel3Id(evaluatingBaseSet.getId());
				updateResult = evaluatingBaseSetDAO.updateByLevel3Id(evaluatingBaseSet);
				if (updateResult == 0) {
					order.setLevel3Id(order.getId());
					order.setId(0);
					order.setLevel("4");
					add(order);
				}
			}
			
			result.setSuccess(true);
			result.setMessage("更新成功");
		} catch (IllegalArgumentException e) {
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(e.getMessage());
			logger.error("更新异常：", e);
		} catch (DataAccessException e) {
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("更新异常：", e);
		} catch (Exception e) {
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage("未知异常");
			logger.error("更新异常：", e);
		}
		return result;
	}
	
	@Override
	public QueryBaseBatchResult<EvaluatingBaseSetInfo> list(EvaluatingBaseSetQueryOrder queryOrder) {
		QueryBaseBatchResult<EvaluatingBaseSetInfo> result = new QueryBaseBatchResult<EvaluatingBaseSetInfo>();
		EvaluatingBaseSetDO evaluatingBaseSet = new EvaluatingBaseSetDO();
		BeanCopier.staticCopy(queryOrder, evaluatingBaseSet);
		long count = evaluatingBaseSetDAO.countWithCondition(evaluatingBaseSet);
		PageComponent component = new PageComponent(queryOrder, count);
		
		List<EvaluatingBaseSetDO> list = evaluatingBaseSetDAO.findWithCondition(evaluatingBaseSet,
			component.getFirstRecord(), component.getPageSize(), "");
		List<EvaluatingBaseSetInfo> pageList = new ArrayList<EvaluatingBaseSetInfo>();
		EvaluatingBaseSetInfo info = null;
		for (EvaluatingBaseSetDO doInfo : list) {
			info = new EvaluatingBaseSetInfo();
			BeanCopier.staticCopy(doInfo, info);
			pageList.add(info);
		}
		result.setSuccess(true);
		result.setPageList(pageList);
		result.initPageParam(component);
		return result;
	}
	
	@Override
	public FcsBaseResult delete(long id) {
		FcsBaseResult result = new FcsBaseResult();
		EvaluatingBaseSetInfo info = null;
		try {
			info = queryById(id);
			if (info != null) {
				evaluatingBaseSetDAO.deleteById(id);
				if (StringUtil.equals("1", info.getLevel())) {
					evaluatingBaseSetDAO.deleteByLevel1Id(id);
				} else if (StringUtil.equals("2", info.getLevel())) {
					evaluatingBaseSetDAO.deleteByLevel2Id(id);
				}
				if (StringUtil.equals("3", info.getLevel())) {
					evaluatingBaseSetDAO.deleteByLevel3Id(id);
				}
				if (StringUtil.equals("4", info.getLevel())) {
					evaluatingBaseSetDAO.deleteByLevel4Id(id);
				}
				result.setSuccess(true);
			} else {
				result.setMessage("未找到该条记录");
			}
		} catch (Exception e) {
			logger.error("删除异常：", e);
		}
		
		return result;
	}
	
	@Override
	public FcsBaseResult updateByList(List<EvaluatingBaseSetOrder> orderList) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			if (ListUtil.isNotEmpty(orderList)) {
				EvaluatingBaseSetDO evaluatingBaseSet = new EvaluatingBaseSetDO();
				evaluatingBaseSet.setLevel(orderList.get(0).getLevel());
				evaluatingBaseSet.setType(orderList.get(0).getType());
				List<Long> ids = evaluatingBaseSetDAO.findAllIds(evaluatingBaseSet);
				for (EvaluatingBaseSetOrder order : orderList) {
					if (order.getId() > 0) {
						ids.remove(order.getId());
						updateById(order);
					} else if (StringUtil.isNotBlank(order.getLevel1Name())) {
						add(order);
					}
				}
				if (ListUtil.isNotEmpty(ids)) {
					for (long id : ids) {
						delete(id);
					}
				}
			}
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("批量更新评价配置失败", e);
		}
		
		return result;
	}
	
	@Override
	public List<EvaluatingBaseQueryInfo> levelConcat(String type, String level) {
		List<EvaluatingBaseQueryInfo> result = null;
		try {
			if (StringUtil.isBlank(type) || StringUtil.isBlank(level)) {
				return result;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", type);
			map.put("level", level);
			List<EvaluatingBaseQueryDO> list = null;
			if (StringUtil.equals("3", level)) {
				list = evaluatingBaseQueryDAO.queryLeve3Concat(map);
			} else {
				list = evaluatingBaseQueryDAO.queryLeve4Concat(map);
			}
			
			if (list != null) {
				EvaluatingBaseQueryInfo info = null;
				result = new ArrayList<EvaluatingBaseQueryInfo>();
				for (EvaluatingBaseQueryDO DO : list) {
					info = new EvaluatingBaseQueryInfo();
					BeanCopier.staticCopy(DO, info);
					if (StringUtil.equals(level, "3")) {
						Map<String, String> evalue3 = new HashMap<String, String>();
						for (int i = 0; i < DO.getLevel3NameList().length
										&& i < DO.getLevel3ScoreList().length; i++) {
							evalue3.put(DO.getLevel3NameList()[i], DO.getLevel3ScoreList()[i]);
						}
						info.setEvalue3(evalue3);
					} else {
						Map<String, String> evalue4 = new HashMap<String, String>();
						for (int i = 0; i < DO.getLevel4NameList().length
										&& i < DO.getLevel4ScoreList().length; i++) {
							evalue4.put(DO.getLevel4NameList()[i], DO.getLevel4ScoreList()[i]);
						}
						info.setEvalue4(evalue4);
					}
					result.add(info);
				}
			}
		} catch (Exception e) {
			logger.error("使用指标查询异常：", e);
		}
		
		return result;
	}
	
	@Override
	public double countScoreByType(EvaluatingBaseSetQueryOrder queryOrder) {
		try {
			EvaluatingBaseSetDO evaluatingBaseSet = new EvaluatingBaseSetDO();
			BeanCopier.staticCopy(queryOrder, evaluatingBaseSet);
			String score = evaluatingBaseSetDAO.countScoreByType(evaluatingBaseSet);
			if (StringUtil.isNotBlank(score)) {
				return Double.parseDouble(score);
			}
			return 0;
		} catch (Exception e) {
			logger.error("统计配置指标总分异常：", e);
		}
		
		return 0;
	}
	
	@Override
	public CityEvalueSetResult queryCityEvalueSet(long userId) {
		CityEvalueSetResult result = new CityEvalueSetResult();
		//查询主观部分
		result.setSubjectivity(queryCityZg());
		
		//查询标准值部分
		
		result.setStandardVal(queryCityBz());
		
		//查询财务评价部分
		
		result.setTerraceFinance(queryCityCw(userId));
		
		result.setSuccess(true);
		result.setMessage("查询成功");
		return result;
	}
	
	@Override
	public PublicCauseSetResult publicCauseSet(long userId) {
		PublicCauseSetResult result = new PublicCauseSetResult();
		//无标准值部分
		result.setNoStandardVal(levelConcat(StandardTypeEnums.GYWBZ.getCode(), "3"));
		result.setStandardVal(queryGyBz(userId));
		result.setSuccess(true);
		result.setMessage("查询成功");
		return result;
	}
	
	private List<EvaluatingBaseQueryInfo> queryGyBz(long userId) {
		List<EvaluatingBaseQueryInfo> list = null;
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("level", "2");
			map.put("type", StandardTypeEnums.GYYBZ.code());
			List<EvaluatingBaseQueryDO> gyBz = evaluatingBaseQueryDAO.queryGyBz(map);
			if (gyBz != null) {
				//查询pm中的财务信息
				CustomerFinanInfo pmFinance = queryFinanceFormPm(userId);
				EvaluatingBaseQueryInfo info = null;
				list = new ArrayList<EvaluatingBaseQueryInfo>();
				for (EvaluatingBaseQueryDO dos : gyBz) {
					info = new EvaluatingBaseQueryInfo();
					BeanCopier.staticCopy(dos, info);
					
					String level2Name = info.getLevel2Name();
					// 没有具体标志区分，仅根据指标名判断
					if (StringUtil.isNotBlank(level2Name)) {
						String actualValue = "0";
						if (level2Name.indexOf("资产负债率") > -1) {
							actualValue = pmFinance.getZcfzl();
							if (StringUtil.isNotBlank(actualValue)) {
								info.setActualValue(actualValue);
								info.setThisScore(getScore(actualValue, dos
									.getCalculatingFormula_gybz().get("why")));
							}
						} else if (level2Name.indexOf("流动比率") > -1) {
							actualValue = pmFinance.getLdbl();
							if (StringUtil.isNotBlank(actualValue)) {
								info.setActualValue(actualValue);
								info.setThisScore(getScore(actualValue, dos
									.getCalculatingFormula_gybz().get("why")));
							}
							
						} else if ("净资产".equals(level2Name)) {
							actualValue = pmFinance.getJzcze();
							if (StringUtil.isNotBlank(actualValue)) {
								info.setActualValue(actualValue);
								info.setThisScore(getScore(actualValue, dos
									.getCalculatingFormula_gybz().get("why")));
							}
							
						}
						
					}
					
					list.add(info);
				}
			}
		} catch (Exception e) {
			logger.error("查询公用事业类标准值部分指标异常", e);
			
		}
		
		return list;
	}
	
	/** 查询主观部分 */
	private List<EvaluatingBaseQueryInfo> queryCityZg() {
		//查询主观部分
		List<EvaluatingBaseQueryInfo> list = null;
		Map<String, Object> map = new HashMap<>();
		map.put("level", "3");
		map.put("type", StandardTypeEnums.CTZG.code());
		List<EvaluatingBaseQueryDO> ctzg = evaluatingBaseQueryDAO.queryLeve3Concat(map);
		if (ListUtil.isNotEmpty(ctzg)) {
			EvaluatingBaseQueryInfo info = null;
			list = new ArrayList<EvaluatingBaseQueryInfo>();
			Map<String, String> evalue3 = null;
			for (EvaluatingBaseQueryDO dos : ctzg) {
				evalue3 = new HashMap<>();
				info = new EvaluatingBaseQueryInfo();
				BeanCopier.staticCopy(dos, info);
				String[] level3Name = dos.getLevel3NameList();
				String[] level3Score = dos.getLevel3ScoreList();
				for (int i = 0; i < level3Name.length && i < level3Score.length; i++) {
					evalue3.put(level3Name[i], level3Score[i]);
				}
				info.setEvalue3(evalue3);
				list.add(info);
			}
			
		}
		return list;
	}
	
	/**
	 * 查询城市开发类 标准值部分
	 * */
	private List<EvaluatingBaseQueryInfo> queryCityBz() {
		Map<String, Object> map = new HashMap<>();
		map.put("level", "3");
		map.put("type", StandardTypeEnums.CTBZ.code());
		List<EvaluatingBaseQueryDO> ctBz = evaluatingBaseQueryDAO.queryCityBz(map);
		List<EvaluatingBaseQueryInfo> list = null;
		if (ListUtil.isNotEmpty(ctBz)) {
			list = new ArrayList<EvaluatingBaseQueryInfo>();
			for (EvaluatingBaseQueryDO dos : ctBz) {
				EvaluatingBaseQueryInfo info = null;
				info = new EvaluatingBaseQueryInfo();
				BeanCopier.staticCopy(dos, info);
				//计算后的公式赋值给公式字段
				info.setCalculatingFormula(dos.getCalculatingFormula_ctbz());
				list.add(info);
			}
		}
		return list;
	}
	
	/**
	 * 查询城市开发类 财务部分
	 * @param userId 需要查财务信息时传
	 * */
	private List<EvaluatingBaseQueryInfo> queryCityCw(long userId) {
		Map<String, Object> map = new HashMap<>();
		map.put("level", "2");
		map.put("type", StandardTypeEnums.CTCW.code());
		List<EvaluatingBaseQueryDO> ctCw = evaluatingBaseQueryDAO.queryCityCw(map);
		List<EvaluatingBaseQueryInfo> list = null;
		if (ListUtil.isNotEmpty(ctCw)) {
			EvaluatingBaseQueryInfo info = null;
			list = new ArrayList<EvaluatingBaseQueryInfo>();
			//查询pm中的财务信息
			CustomerFinanInfo pmFinance = queryFinanceFormPm(userId);
			for (EvaluatingBaseQueryDO dos : ctCw) {
				info = new EvaluatingBaseQueryInfo();
				BeanCopier.staticCopy(dos, info);
				//计算后的公式赋值给公式字段
				info.setCalculatingFormula(dos.getCalculatingFormula_ctbz());
				String level2Name = info.getLevel2Name();
				// 没有具体标志区分，仅根据指标名判断
				if (StringUtil.isNotBlank(level2Name)) {
					
					if (level2Name.indexOf("资产负债率") > -1) {
						String actualValue = pmFinance.getZcfzl();
						if (StringUtil.isNotBlank(actualValue)) {
							info.setActualValue(actualValue);
							info.setThisScore(getScore(actualValue,
								dos.getCalculatingFormula_ctbz()));
						}
						
					} else if (level2Name.indexOf("业务利润率") > -1) {
						//TODO 业务利润率
						//						info.setActualValue("暂无");
						//						info.setThisScore();
					} else if (level2Name.indexOf("净资产收益率") > -1) {
						String actualValue = pmFinance.getJzcsyl();
						if (StringUtil.isNotBlank(actualValue)) {
							info.setActualValue(actualValue);
							info.setThisScore(getScore(actualValue,
								dos.getCalculatingFormula_ctbz()));
						}
						
					}
					
				}
				
				list.add(info);
			}
		}
		return list;
	}
	
	/** 根据公式和实际值计算得分 */
	private String getScore(String actualValue, String calculatingFormula) {
		double score = 0;
		if (StringUtil.isNotBlank(calculatingFormula)
			&& calculatingFormula.indexOf("actualValue") > -1) {
			String[] cals = calculatingFormula.split(";");
			if (cals.length > 0) {
				for (String s : cals) {
					try {
						double thisScore = (double) calculatingStr.eval(s.replaceAll("actualValue",
							actualValue));
						//取所有计算公式得出的最大值
						if (thisScore > score)
							score = thisScore;
					} catch (ScriptException e) {
						logger.error("计算得分失败：calculatingFormula={}，actualValue={}",
							calculatingFormula, actualValue);
						logger.error(e.getMessage(), e);
						return "";
					}
				}
			}
		}
		
		return String.valueOf(Math.round(score * 10) / 10.0);
		
	}
	
}
