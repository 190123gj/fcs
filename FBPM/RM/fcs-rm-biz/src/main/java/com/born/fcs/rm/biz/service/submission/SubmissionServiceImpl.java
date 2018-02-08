package com.born.fcs.rm.biz.service.submission;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.born.fcs.rm.dal.dataobject.AccountBalanceDO;
import com.yjf.common.lang.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.rm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.rm.biz.service.convert.UnBoxingConverter;
import com.born.fcs.rm.biz.service.exception.ExceptionFactory;
import com.born.fcs.rm.dal.dataobject.SubmissionDO;
import com.born.fcs.rm.dal.dataobject.SubmissionDataDO;
import com.born.fcs.rm.domain.context.FcsPmDomainHolder;
import com.born.fcs.rm.integration.bpm.BpmUserQueryService;
import com.born.fcs.rm.ws.enums.ReportStatusEnum;
import com.born.fcs.rm.ws.enums.SubmissionCodeEnum;
import com.born.fcs.rm.ws.info.submission.SubmissionDataInfo;
import com.born.fcs.rm.ws.info.submission.SubmissionInfo;
import com.born.fcs.rm.ws.order.submission.SubmissionDataOrder;
import com.born.fcs.rm.ws.order.submission.SubmissionOrder;
import com.born.fcs.rm.ws.order.submission.SubmissionQueryOrder;
import com.born.fcs.rm.ws.service.submission.SubmissionService;
import com.google.common.collect.Lists;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("submissionService")
public class SubmissionServiceImpl extends BaseAutowiredDomainService implements SubmissionService {

	@Autowired
	BpmUserQueryService bpmUserQueryService;

	@Override
	public SubmissionInfo findById(long id) {
		SubmissionInfo info = null;
		List<SubmissionDataDO> itemDOs = null;
		List<SubmissionDataInfo> itemInfos = Lists.newArrayList();
		if (id > 0) {
			SubmissionDO DO = submissionDAO.findById(id);
			itemDOs = submissionDataDAO.findBySubmissionId(id);
			info = new SubmissionInfo();
			BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
			info.setReportCode(SubmissionCodeEnum.getByCode(DO.getReportCode()));
			info.setReporterStatus(ReportStatusEnum.getByCode(DO.getReporterStatus()));
		}
		if (itemDOs.size() > 0) {
			for (SubmissionDataDO itemDO : itemDOs) {
				SubmissionDataInfo itemInfo = new SubmissionDataInfo();
				BeanCopier.staticCopy(itemDO, itemInfo, UnBoxingConverter.getInstance());
				itemInfos.add(itemInfo);
			}
		}
		info.setData(itemInfos);
		return info;
	}
	
	@Override
	public FcsBaseResult save(final SubmissionOrder order) {
		return commonProcess(order, "保存数据报送", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				if (order.getSubmissionId() <= 0) {
					//删除相同报送期间没有被引用的记录
					deleteOld(order);

					final Date now = FcsPmDomainHolder.get().getSysDate();
					//保存
					SubmissionDO submissionDO = new SubmissionDO();
					BeanCopier.staticCopy(order, submissionDO, UnBoxingConverter.getInstance());
					submissionDO.setRawAddTime(now);
					submissionDO.setReporterStatus(order.getReporterStatus().code());
					submissionDO.setReportCode(order.getReportCode().code());
					long submissionId = submissionDAO.insert(submissionDO);
					
					for (SubmissionDataOrder itemOrder : order.getData()) {
						SubmissionDataDO itemDO = new SubmissionDataDO();
						BeanCopier.staticCopy(itemOrder, itemDO, UnBoxingConverter.getInstance());
						itemDO.setSubmissionId(submissionId);
						itemDO.setRawAddTime(now);
						submissionDataDAO.insert(itemDO);
					}
				} else {
					//更新
					SubmissionDO submissionDO = submissionDAO.findById(order.getSubmissionId());
					if (null == submissionDO) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到数据报送记录");
					}
					BeanCopier.staticCopy(order, submissionDO, UnBoxingConverter.getInstance());
					submissionDO.setReporterStatus(order.getReporterStatus().code());
					submissionDO.setReportCode(order.getReportCode().code());
					submissionDAO.update(submissionDO);
					List<SubmissionDataDO> inDataBaseList = submissionDataDAO
						.findBySubmissionId(order.getSubmissionId());
					Map<Long, SubmissionDataDO> inDataBaseMap = new HashMap<Long, SubmissionDataDO>();
					for (SubmissionDataDO DO : inDataBaseList) {
						inDataBaseMap.put(DO.getSubmissionDataId(), DO);
					}
					for (SubmissionDataOrder itemOrder : order.getData()) {
						if (itemOrder.getSubmissionDataId() <= 0) {//不存在就新增
							final Date now = FcsPmDomainHolder.get().getSysDate();
							SubmissionDataDO itemDO = new SubmissionDataDO();
							BeanCopier.staticCopy(itemOrder, itemDO,
								UnBoxingConverter.getInstance());
							itemDO.setSubmissionId(order.getSubmissionId());
							itemDO.setRawAddTime(now);
							submissionDataDAO.insert(itemDO);
						} else {
							SubmissionDataDO itemDO = submissionDataDAO.findById(itemOrder
								.getSubmissionDataId());
							if (null == itemDO) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
									"未找到明细记录");
							}
							BeanCopier.staticCopy(itemOrder, itemDO,
								UnBoxingConverter.getInstance());
							itemDO.setSubmissionId(order.getSubmissionId());
							submissionDataDAO.update(itemDO);
							inDataBaseMap.remove(itemDO.getSubmissionDataId());
						}
					}
					if (inDataBaseMap.size() > 0) {//删除
						for (long id : inDataBaseMap.keySet()) {
							submissionDataDAO.deleteById(id);
						}
					}
					
				}
				
				return null;
			}
		}, null, null);
	}

	private void deleteOld(SubmissionOrder order){
		SubmissionDO queryCondition = new SubmissionDO();
		queryCondition.setDeptCode(order.getDeptCode());
		queryCondition.setReportCode(order.getReportCode().code());
		queryCondition.setReportYear(order.getReportYear());
		queryCondition.setReportMonth(order.getReportMonth());
		List<SubmissionDO> pageList = submissionDAO.findByCondition(queryCondition, null,
				null, 0, 999, null,null,null,null,order.getReporterId());
		if(pageList!=null&&pageList.size()>0){
			for(SubmissionDO DO:pageList){
				if(DO.getReporterStatus().equals(ReportStatusEnum.IN_USE.code())){
						throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL,
								"该报送数据已被引用，不能覆盖");
				}else{
					DO.setReporterStatus(ReportStatusEnum.DELETED.code());
					submissionDAO.update(DO);
				}
			}
		}
	}
	
	@Override
	public QueryBaseBatchResult<SubmissionInfo> query(SubmissionQueryOrder order) {
		QueryBaseBatchResult<SubmissionInfo> baseBatchResult = new QueryBaseBatchResult<SubmissionInfo>();

		SubmissionDO queryCondition = new SubmissionDO();
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);

		if (order.getStatus() != null)
			queryCondition.setReporterStatus(order.getStatus().code());

		long totalSize = submissionDAO.findByConditionCount(queryCondition, order.getStartDate(),
			order.getEndDate(),order.getStatusList(),order.getDeptIdList(),order.getDraftUserId());

		PageComponent component = new PageComponent(order, totalSize);
		List<SubmissionDO> pageList = submissionDAO.findByCondition(queryCondition, order.getStartDate(),
				order.getEndDate(), component.getFirstRecord(), component.getPageSize(), order.getSortCol(),
			order.getSortOrder(),order.getStatusList(),order.getDeptIdList(),order.getDraftUserId());
		List<SubmissionInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (SubmissionDO DO : pageList) {
				SubmissionInfo info = new SubmissionInfo();
				BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
				info.setReportCode(SubmissionCodeEnum.getByCode(DO.getReportCode()));
				info.setReporterStatus(ReportStatusEnum.getByCode(DO.getReporterStatus()));
				list.add(info);
			}
		}

		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}

	@Override
	public FcsBaseResult updateStatus(String submissionCodes, int year, int month) {
		FcsBaseResult result = new FcsBaseResult();
		try {
		String codes[]=submissionCodes.split(",");
		for(String code : codes){
			if(code.equals("ACCOUNT_BALANCE")){//科目余额表
				AccountBalanceDO queryCondition = new AccountBalanceDO();
				queryCondition.setReportYear(year);
				queryCondition.setReportMonth(month);
				List<AccountBalanceDO> list = accountBalanceDAO.findByCondition(queryCondition,
						null, null, 0, 10, null,null);
				if (null != list && ListUtil.isNotEmpty(list)) {
					AccountBalanceDO DO = list.get(0);
					DO.setStatus("IS");
					accountBalanceDAO.update(DO);
				}
			}else {
				//查询当期记录
				SubmissionDO queryCondition = new SubmissionDO();
				queryCondition.setReportCode(code);
				queryCondition.setReportYear(year);
				if(!code.equals(SubmissionCodeEnum.ANNUAL_OBJECTIVE.code())){
					queryCondition.setReportMonth(month);
				}
				queryCondition.setReporterStatus(ReportStatusEnum.SUBMITTED.code());
				List<SubmissionDO> list = submissionDAO.findByCondition(queryCondition, null,
						null, 0, 999, null, null, null,null,0l);
				if (null != list && ListUtil.isNotEmpty(list)) {
					for(SubmissionDO DO:list){
						DO.setReporterStatus(ReportStatusEnum.IN_USE.code());
						submissionDAO.update(DO);
					}
				}
			}
		}
			result.setSuccess(true);
			result.setMessage("更新上报记录状态成功！");
			result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
		} catch (DataAccessException e) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage(e.getMessage());
			logger.error("更新上报记录状态异常", e);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage(e.getMessage());
			logger.error("更新上报记录状态异常", e);
		}
		return result;
	}

	@Override
	public FcsBaseResult deleteById(final SubmissionOrder order) {
		return commonProcess(order, "刪除报送记录", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				SubmissionDO submissionDO = submissionDAO.findById(order.getSubmissionId());
				
				if (submissionDO == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "报送记录不存在");
				}
				ReportStatusEnum status = ReportStatusEnum.getByCode(submissionDO
					.getReporterStatus());
				//草稿状态才能删除
				if (status != ReportStatusEnum.DRAFT) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"当前状态不允许删除");
				}
				
				submissionDO.setReporterStatus(ReportStatusEnum.DELETED.code());
				submissionDAO.update(submissionDO);
				
				return null;
			}
		}, null, null);
	}

	@Override
	public String findDeptInfoByDeptCode(String deptCode) {
		return bpmUserQueryService.findDeptInfoByDeptCode(deptCode);
	}

	@Override
	public List<String> getDepts() {
		return submissionDAO.findDepts();
	}

	protected FcsBaseResult createResult() {
		return new FcsBaseResult();
	}
}
