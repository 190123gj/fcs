package com.born.fcs.rm.biz.service.accountbalance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.born.fcs.rm.domain.context.FcsPmDomainHolder;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.rm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.rm.biz.service.exception.ExceptionFactory;
import com.born.fcs.rm.dal.dataobject.AccountBalanceDO;
import com.born.fcs.rm.dal.dataobject.AccountBalanceDataDO;
import com.born.fcs.rm.ws.enums.VersionEnum;
import com.born.fcs.rm.ws.info.accountbalance.AccountBalanceDataInfo;
import com.born.fcs.rm.ws.info.accountbalance.AccountBalanceInfo;
import com.born.fcs.rm.ws.order.accountbalance.AccountBalanceDataOrder;
import com.born.fcs.rm.ws.order.accountbalance.AccountBalanceDataQueryOrder;
import com.born.fcs.rm.ws.order.accountbalance.AccountBalanceOrder;
import com.born.fcs.rm.ws.order.accountbalance.AccountBalanceQueryOrder;
import com.born.fcs.rm.ws.service.accountbalance.AccountBalanceService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("accountBalanceService")
public class AccountBalanceServiceImpl extends BaseAutowiredDomainService implements
																			AccountBalanceService {
	
	protected FcsBaseResult createResult() {
		return new FcsBaseResult();
	}
	
	@Override
	public FcsBaseResult save(final AccountBalanceOrder order) {
		return commonProcess(order, "保存科目余额表", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				Date now = getSysdate();
				AccountBalanceQueryOrder queryOrder = new AccountBalanceQueryOrder();
				queryOrder.setReportYear(order.getReportYear());
				queryOrder.setReportMonth(order.getReportMonth());
				queryOrder.setVersion(VersionEnum.NOW.code());
				queryOrder.setPageNumber(1L);
				queryOrder.setPageSize(1L);
				QueryBaseBatchResult<AccountBalanceInfo> batchResult = queryList(queryOrder);
				if (null != batchResult && ListUtil.isNotEmpty(batchResult.getPageList())) {
					AccountBalanceInfo info = batchResult.getPageList().get(0);
					if(info.getStatus()!=null&&info.getStatus().equals("IS")){
						throw ExceptionFactory.newFcsException(
								FcsResultEnum.DO_ACTION_STATUS_ERROR, "数据已被引用，请勿重复报送! ");
					}
					AccountBalanceDO latestAc = extraDAO.queryLatestAccountBalance();
					if (null != latestAc) {
						if (info.getReportYear() != latestAc.getReportYear()
							|| info.getReportMonth() != latestAc.getReportMonth()) {
							throw ExceptionFactory.newFcsException(
								FcsResultEnum.DO_ACTION_STATUS_ERROR, "只能覆盖最近一期的数据 ");
						}
					}
				}
				
				AccountBalanceDO ac = new AccountBalanceDO();
				ac.setReportYear(order.getReportYear());
				ac.setReportMonth(order.getReportMonth());
				accountBalanceDAO.updateToHis(ac);
				
				AccountBalanceDO balance = new AccountBalanceDO();
				BeanCopier.staticCopy(order, balance);
				balance.setVersion(VersionEnum.NOW.code());
				balance.setRawAddTime(getSysdate());
				long accountBalanceId = accountBalanceDAO.insert(balance);
				
				if (ListUtil.isNotEmpty(order.getDatas())) {
					int sortOrder = 1;
					for (AccountBalanceDataOrder data : order.getDatas()) {
						AccountBalanceDataDO doObj = new AccountBalanceDataDO();
						BeanCopier.staticCopy(data, doObj);
						doObj.setAccountBalanceId(accountBalanceId);
						doObj.setSortOrder(sortOrder++);
						doObj.setRawAddTime(now);
						accountBalanceDataDAO.insert(doObj);
					}
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public AccountBalanceInfo findById(long id) {
		AccountBalanceDO ac = accountBalanceDAO.findById(id);
		if (null == ac) {
			return null;
		}
		
		AccountBalanceInfo info = new AccountBalanceInfo();
		BeanCopier.staticCopy(ac, info);
		
		List<AccountBalanceDataDO> items = accountBalanceDataDAO.findByAccountBalanceId(id);
		if (ListUtil.isNotEmpty(items)) {
			List<AccountBalanceDataInfo> datas = new ArrayList<>(items.size());
			for (AccountBalanceDataDO doObj : items) {
				AccountBalanceDataInfo data = new AccountBalanceDataInfo();
				BeanCopier.staticCopy(doObj, data);
				datas.add(data);
			}
			info.setDatas(datas);
		}
		
		return info;
	}
	
	@Override
	public QueryBaseBatchResult<AccountBalanceInfo> queryList(AccountBalanceQueryOrder queryOrder) {
		QueryBaseBatchResult<AccountBalanceInfo> batchResult = new QueryBaseBatchResult<>();
		
		AccountBalanceDO accountBalance = new AccountBalanceDO();
		BeanCopier.staticCopy(queryOrder, accountBalance);
		Date startTime = null;
		if (StringUtil.isNotBlank(queryOrder.getStartTime())) {
			startTime = DateUtil.parse(queryOrder.getStartTime(), null);
		}
		Date endTime = null;
		if (StringUtil.isNotBlank(queryOrder.getEndTime())) {
			endTime = DateUtil.parse(queryOrder.getEndTime(), null);
		}
		
		long totalSize = accountBalanceDAO.findByConditionCount(accountBalance, startTime, endTime);
		if (totalSize > 0) {
			PageComponent component = new PageComponent(queryOrder, totalSize);
			List<AccountBalanceDO> pageList = accountBalanceDAO.findByCondition(accountBalance,
				startTime, endTime, component.getFirstRecord(), component.getPageSize(),
				queryOrder.getSortCol(), queryOrder.getSortCol());
			List<AccountBalanceInfo> list = batchResult.getPageList();
			if (totalSize > 0) {
				for (AccountBalanceDO doObj : pageList) {
					AccountBalanceInfo info = new AccountBalanceInfo();
					BeanCopier.staticCopy(doObj, info);
					list.add(info);
				}
			}
			
			batchResult.initPageParam(component);
			batchResult.setPageList(list);
		}
		batchResult.setSuccess(true);
		return batchResult;
	}
	
	@Override
	public List<AccountBalanceDataInfo> queryDatas(AccountBalanceDataQueryOrder queryOrder) {
		AccountBalanceQueryOrder acQueryOrder = new AccountBalanceQueryOrder();
		acQueryOrder.setReportYear(queryOrder.getReportYear());
		acQueryOrder.setReportMonth(queryOrder.getReportMonth());
		acQueryOrder.setVersion(queryOrder.getVersion());
		acQueryOrder.setPageNumber(1L);
		acQueryOrder.setPageSize(1L);
		QueryBaseBatchResult<AccountBalanceInfo> abs = queryList(acQueryOrder);
		
		if (null != abs && ListUtil.isNotEmpty(abs.getPageList())) {
			AccountBalanceInfo abInfo = abs.getPageList().get(0);
			List<AccountBalanceDataDO> items = accountBalanceDataDAO.findByAccountBalanceId(abInfo
				.getAccountBalanceId());
			List<AccountBalanceDataInfo> list = new ArrayList<>();
			if (ListUtil.isNotEmpty(items)) {
				for (AccountBalanceDataDO doObj : items) {
					AccountBalanceDataInfo info = new AccountBalanceDataInfo();
					BeanCopier.staticCopy(doObj, info);
					list.add(info);
				}
			}
			return list;
		}
		
		return null;
	}
	
	@Override
	public AccountBalanceDataInfo queryData(AccountBalanceDataQueryOrder queryOrder) {
		AccountBalanceQueryOrder acQueryOrder = new AccountBalanceQueryOrder();
		acQueryOrder.setReportYear(queryOrder.getReportYear());
		acQueryOrder.setReportMonth(queryOrder.getReportMonth());
		acQueryOrder.setVersion(queryOrder.getVersion());
		acQueryOrder.setPageNumber(1L);
		acQueryOrder.setPageSize(1L);
		QueryBaseBatchResult<AccountBalanceInfo> abs = queryList(acQueryOrder);
		
		if (null != abs && ListUtil.isNotEmpty(abs.getPageList())) {
			AccountBalanceInfo abInfo = abs.getPageList().get(0);
			AccountBalanceDataDO accountBalanceData = new AccountBalanceDataDO();
			accountBalanceData.setAccountBalanceId(abInfo.getAccountBalanceId());
			List<AccountBalanceDataDO> items = accountBalanceDataDAO.findByCondition(
				accountBalanceData, queryOrder.getCodeFull(), 1, 1, queryOrder.getSortCol(),
				queryOrder.getSortOrder());
			if (ListUtil.isNotEmpty(items)) {
				for (AccountBalanceDataDO doObj : items) {
					AccountBalanceDataInfo info = new AccountBalanceDataInfo();
					BeanCopier.staticCopy(doObj, info);
					return info;
				}
			}
		}
		
		return null;
	}
}
