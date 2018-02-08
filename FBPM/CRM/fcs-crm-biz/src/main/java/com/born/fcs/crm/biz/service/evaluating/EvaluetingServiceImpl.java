package com.born.fcs.crm.biz.service.evaluating;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.biz.service.customer.BaseAutowiredDAO;
import com.born.fcs.crm.biz.service.evaluating.process.EvaluetingAuditService;
import com.born.fcs.crm.dal.dataobject.CustomerCompanyDetailDO;
import com.born.fcs.crm.dal.dataobject.EvaluatingBaseDO;
import com.born.fcs.crm.dal.dataobject.EvaluetingListForAuditDO;
import com.born.fcs.crm.ws.service.EvaluetingService;
import com.born.fcs.crm.ws.service.enums.StandardTypeEnums;
import com.born.fcs.crm.ws.service.info.EvaluetingInfo;
import com.born.fcs.crm.ws.service.info.EvaluetingListAuditInfo;
import com.born.fcs.crm.ws.service.order.EvaluetingOrder;
import com.born.fcs.crm.ws.service.order.query.EvaluetingListQueryOrder;
import com.born.fcs.crm.ws.service.order.query.EvaluetingQueryOrder;
import com.born.fcs.crm.ws.service.result.EvalutingResult;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

@Service("evaluetingService")
public class EvaluetingServiceImpl extends BaseAutowiredDAO implements EvaluetingService {
	
	@Autowired
	private EvaluetingAuditService evaluetingAuditService;
	
	@Override
	public FcsBaseResult baseEvalueting(EvaluetingOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			order.check();
			ProjectInfo project = projectServiceClient.queryByCode(
					order.getProjectCode(), false);
			if (null != project && "122".equals(project.getBusiType())) {
				// 企业债类型 必须选择外部评级
				if (!StringUtil.equals("outer", order.getEvaluetingType())) {
					result.setSuccess(false);
					result.setFcsResultEnum(FcsResultEnum.DO_ACTION_STATUS_ERROR);
					result.setMessage("企业债项目必须选择外部评级");
					return result;
				}
			}
			String editStatus = order.getEditStatus();
			if (StringUtil.equals("IS", order.getIsProsecute())
				|| StringUtil.equals("outer", order.getEvaluetingType()) || StringUtil.equals("none", order.getEvaluetingType()) ) {//外部评级或起诉客户
				if ("IS".equals(order.getIsTemporary())) {
					editStatus = "0";
				} else {
					editStatus = "1";
				}
				
			}
			if (StringUtil.isNotBlank(editStatus)
				&& (StringUtil.equals(editStatus, "1") || editStatus.indexOf("0") == -1)) {
				order.setCheckStatus(1);
			} else {
				order.setCheckStatus(0);
			}
			FormBaseResult formInfo = evaluetingAuditService.save(order);
			if (formInfo.isSuccess() && formInfo.getFormInfo() != null) {
				long formId = formInfo.getFormInfo().getFormId();
				order.setFormId(formId);
				saveEvalueInfo(order, formId);
				result.setKeyId(formId);
			}
			
			if (StringUtil.equals("KHGK", order.getEvalue_type())) {//客户概况
			
				khgk(order);
			}
			result.setMessage(formInfo.getMessage());
			result.setSuccess(formInfo.isSuccess());
		} catch (IllegalArgumentException e) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(e.getMessage());
			logger.error("评价异常", e);
		} catch (DataAccessException e) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage(e.getMessage());
			logger.error("评价异常", e);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage(e.getMessage());
			logger.error("评价异常", e);
		}
		
		return result;
	}
	
	@Override
	public EvalutingResult queryEvalueResult(EvaluetingQueryOrder order) {
		EvalutingResult result = new EvalutingResult();
		try {
			EvaluatingBaseDO evaluatingBase = new EvaluatingBaseDO();
			BeanCopier.staticCopy(order, evaluatingBase);
			List<EvaluatingBaseDO> list = evaluatingBaseDAO.findAllWithCondition(evaluatingBase);
			if (ListUtil.isNotEmpty(list)) {
				Map<String, EvaluetingInfo> evalutingMap = new HashMap<String, EvaluetingInfo>();
				List<EvaluetingInfo> evalutingList = new ArrayList<EvaluetingInfo>();
				EvaluetingInfo evaluetingInfo = null;
				for (EvaluatingBaseDO DO : list) {
					evaluetingInfo = new EvaluetingInfo();
					BeanCopier.staticCopy(DO, evaluetingInfo);
					evalutingList.add(evaluetingInfo);
					String key = StringUtil.isNotBlank(DO.getEvaluetingKey()) ? DO
						.getEvaluetingKey() : String.valueOf(DO.getEvaluetingId());
					evalutingMap.put(key, evaluetingInfo);
				}
				result.setEvalutingList(evalutingList);
				result.setEvalutingMap(evalutingMap);
				result.setSuccess(true);
			}
		} catch (Exception e) {
			logger.error("查询评价结果异常：", e);
		}
		return result;
	}
	
	@Override
	public EvalutingResult evalueCount(EvaluetingQueryOrder order) {
		EvalutingResult result = new EvalutingResult();
		try {
			EvaluatingBaseDO evaluatingBase = new EvaluatingBaseDO();
			BeanCopier.staticCopy(order, evaluatingBase);
			List<EvaluatingBaseDO> list = evaluatingBaseDAO.findAllWithCondition(evaluatingBase);
			if (ListUtil.isNotEmpty(list)) {
				Map<String, Object> evalueCount = new HashMap<String, Object>();
				double yb_total = 0;//一般评价总分
				double cw_total = 0;//财务评价总分
				double tz_total = 0;//调整事项总分
				double zb_total = 0;//资本信用评价总分
				double gy_total = 0;//公用事业类总分
				double ct_total = 0;//城市开发类总分
				boolean isYB = false;//是一般企业
				for (EvaluatingBaseDO DO : list) {
					double value = chageToDouble(DO.getThisScore());
					if (StringUtil.equals(DO.getEvalueType(), StandardTypeEnums.YBQY.code())) {//一般企业指标
						isYB = true;
						String key = DO.getEvalueTypeChild();//按小类统计
						
						if (StringUtil.isNotBlank(key)) {
							if (evalueCount.containsKey(key)) {
								value += (double) evalueCount.get(key);
							}
							evalueCount.put(key, value);
						} else if (StringUtil.isNotBlank(DO.getEvaluetingKey())) {
							evalueCount.put(DO.getEvaluetingKey(), value);
							yb_total = chageToDouble(DO.getThisScore()); //一般评价总分
						}
						
					} else if (StringUtil.equals(DO.getEvalueType(), StandardTypeEnums.ZBXY.code())) {//资本信用
						String key = DO.getEvaluetingKey();
						if (StringUtil.isNotBlank(key)) {
							//							double value = chageToDouble(DO.getThisScore());
							evalueCount.put(key, DO.getThisScore());
							if (StringUtil.equals(key, "cphj")) {
								zb_total = chageToDouble(DO.getThisScore());
							}
						}
					} else if (StringUtil.equals(DO.getEvalueType(), StandardTypeEnums.TZSX.code())) {//调整事项评价
						String key = DO.getEvaluetingKey();
						if (StringUtil.isNotBlank(key)) {
							evalueCount.put("tz_" + key, DO.getThisScore());
							if (StringUtil.equals(key, "cphj")) {
								tz_total = chageToDouble(DO.getThisScore());
							}
						}
					} else if (StringUtil.isNotBlank(DO.getEvalueType())
								&& DO.getEvalueType().indexOf("CW") > -1) {//财务指标
						String key = DO.getEvaluetingKey();
						if (StringUtil.isNotBlank(key)) {
							evalueCount.put(key, value);
							cw_total += value;//这里统计的数据是财务总分的2倍，使用的时候除以2
						}
						
					} else if (StringUtil.equals(DO.getEvalueType(), "GY")) {//公用事业类
						String key = DO.getEvalueTypeChild();//按小类统计
						if (StringUtil.isNotBlank(key)) {
							if (evalueCount.containsKey(key)) {
								value += (double) evalueCount.get(key);
							}
							evalueCount.put(key, value);
						} else if (StringUtil.isNotBlank(DO.getEvaluetingKey())) {
							evalueCount.put(DO.getEvaluetingKey(), value);
							gy_total = chageToDouble(DO.getThisScore());
						}
						
					} else if (StringUtil.equals(DO.getEvalueType(), "CT")) {
						long key = DO.getEvaluetingId();//按小类统计
						if (key > 0) {
							ct_total += value;
							logger.info("key={},value={},step={}", key, value, DO.getStep());
						}
					}
				}
				evalueCount.put("cwTotal", Math.round(cw_total));
				//一般企业评价总分
				if (isYB) {
					evalueCount
						.put(
							"total",
							Math.round((yb_total * 0.4 + cw_total * 0.4 + tz_total + zb_total) * 100) / 100.0); //综合评价得分=总体评价指标总分*40%+财务评价指标总分*40%+资本信用得分+调整得分					
				} else {
					evalueCount.put("total",
						Math.round((gy_total + tz_total + zb_total + ct_total) * 100) / 100.0);//指标+调整事项+资本信用										
				}
				result.setEvalueCount(evalueCount);
				result.setSuccess(true);
			}
		} catch (Exception e) {
			logger.error("查询评价结果异常：", e);
		}
		return result;
	}
	
	private double chageToDouble(String s) {
		double d = 0;
		
		try {
			if (StringUtil.isNotBlank(s))
				d = Math.round(Double.parseDouble(s) * 100) / 100.0;
		} catch (Exception e) {
			logger.error("评分统计异常：{},{}", s, e);
		}
		
		return d;
		
	}
	
	@Override
	public QueryBaseBatchResult<EvaluetingListAuditInfo> list(EvaluetingListQueryOrder order) {
		QueryBaseBatchResult<EvaluetingListAuditInfo> batchResult = new QueryBaseBatchResult<EvaluetingListAuditInfo>();
		EvaluetingListForAuditDO evaluetingDo = new EvaluetingListForAuditDO();
		BeanCopier.staticCopy(order, evaluetingDo);
		long count = evaluetingListForAuditDAO.countWithCondition(evaluetingDo,
			order.getLoginUserId(), order.getDeptIdList(), order.getLikeCustomerName(),
			order.getLikeBusiLicenseNo());
		PageComponent component = new PageComponent(order, count);
		
		List<EvaluetingListForAuditDO> list = evaluetingListForAuditDAO.findWithCondition(
			evaluetingDo, order.getLoginUserId(), order.getDeptIdList(),
			component.getFirstRecord(), component.getPageSize(), order.getLikeCustomerName(),
			order.getLikeBusiLicenseNo());
		List<EvaluetingListAuditInfo> pageList = new ArrayList<>();
		if (ListUtil.isNotEmpty(list)) {
			EvaluetingListAuditInfo info = null;
			for (EvaluetingListForAuditDO DO : list) {
				info = new EvaluetingListAuditInfo();
				BeanCopier.staticCopy(DO, info);
				pageList.add(info);
			}
			
		}
		batchResult.setTotalCount(count);
		batchResult.initPageParam(component);
		batchResult.setSuccess(true);
		batchResult.setPageList(pageList);
		
		return batchResult;
	}
	
	/** 客户概况:更新 */
	private void khgk(EvaluetingOrder order) {
		//直接修改客户信息中的隶属关系
		CustomerCompanyDetailDO info = customerCompanyDetailDAO.findByCustomerId(order
			.getCustomerId());
		info.setSubordinateRelationship(order.getSubordinateRelationship());
		customerCompanyDetailDAO.updateByCustomerId(info);
		
		// 	评价暂存数据
		//		CustomerInfoForEvalueDO customerInfoForEvalue = new CustomerInfoForEvalueDO();
		//		BeanCopier.staticCopy(order, customerInfoForEvalue);
		//		customerInfoForEvalue.setFormId(order.getFormId());
		//		CustomerInfoForEvalueDO info0 = customerInfoForEvalueDAO.findById(order.getFormId());
		//		if (info0 != null) {
		//			customerInfoForEvalueDAO.update(customerInfoForEvalue);
		//			
		//		} else {
		//			customerInfoForEvalueDAO.insert(customerInfoForEvalue);
		//			
		//		}
		
	}
	
	/** 评价详情保存 */
	private void saveEvalueInfo(EvaluetingOrder order, long formId) {
		EvaluatingBaseDO evaluatingBase = new EvaluatingBaseDO();
		Date nowDate = sqlDateService.getSqlDate();
		String customerId = order.getCustomerId();
		String year = DateUtil.dtSimpleFormat(nowDate).substring(0, 4);
		if (order.getEvaluetingInfo() != null) {
			for (EvaluetingInfo info : order.getEvaluetingInfo()) {
				if (checkEvalueType(info)) {
					BeanCopier.staticCopy(info, evaluatingBase);
					evaluatingBase.setCustomerId(customerId);
					evaluatingBase.setYear(year);
					evaluatingBase.setFormId(formId);
					if (info.getId() > 0) {
						evaluatingBaseDAO.updateById(evaluatingBase);
					} else {
						evaluatingBase.setRawAddTime(nowDate);
						evaluatingBaseDAO.insert(evaluatingBase);
					}
				}
				
			}
		}
		
	}
	
	@Override
	public EvalutingResult financeInfoFromPro(long userId) {
		EvalutingResult result = new EvalutingResult();
		result.setCustomerFinanInfo(queryFinanceFormPm(userId));
		result.setSuccess(true);
		//		logger.info("map={}", customerFinanInfo);
		//		logger.info("yszkzzcs={}", customerFinanInfo.getYszkzzcs());
		//		logger.info("cdzzcs={}", customerFinanInfo.getCdzzcs());
		//		logger.info("ldzczzcs={}", customerFinanInfo.getLdzczzcs());
		//		logger.info("zyywlrl={}", customerFinanInfo.getZyywlrl());
		//		logger.info("nzyywsr_avg={}", customerFinanInfo.getNzyywsr_avg());
		//		logger.info("nzyywsr={}", customerFinanInfo.getNzyywsr());
		//		logger.info("nlrzegm={}", customerFinanInfo.getNlrzegm());
		//		logger.info("nlrzegm_avg={}", customerFinanInfo.getNlrzegm_avg());
		return result;
	}
	
	@Override
	public EvaluetingListAuditInfo queryByFormId(long formId) {
		EvaluetingListAuditInfo info = null;
		try {
			EvaluetingListForAuditDO doInfo = evaluetingListForAuditDAO.findByFormId(formId);
			if (doInfo != null) {
				info = new EvaluetingListAuditInfo();
				BeanCopier.staticCopy(doInfo, info);
			}
		} catch (Exception e) {
			logger.error("查询评级信息失败", e);
		}
		return info;
	}
	
	@Override
	public FcsBaseResult aditMakeData(EvaluetingQueryOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			EvaluatingBaseDO evaluatingBase = new EvaluatingBaseDO();
			BeanCopier.staticCopy(order, evaluatingBase);
			evaluatingBase.setStep("1");
			evaluatingBase.setEvalueType(null);
			evaluatingBase.setYear(sqlDateService.getYear());
			List<EvaluatingBaseDO> list1 = evaluatingBaseDAO.findAllWithCondition(evaluatingBase);
			//审核时如果审核数据为空，则复制原数据一份
			if (ListUtil.isEmpty(list1)) {
				evaluatingBase.setStep("0");
				List<EvaluatingBaseDO> list0 = evaluatingBaseDAO
					.findAllWithCondition(evaluatingBase);
				for (EvaluatingBaseDO info : list0) {
					info.setStep("1");
					info.setId(0);
					evaluatingBaseDAO.insert(info);
				}
				result.setSuccess(true);
			}
		} catch (Exception e) {
			logger.error("查询评价结果异常：", e);
		}
		return result;
	}
	
}
