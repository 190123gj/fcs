package com.born.fcs.pm.biz.service.contract;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.DbTableDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.contract.DbTableInfo;
import com.born.fcs.pm.ws.order.contract.DbTableOrder;
import com.born.fcs.pm.ws.order.contract.DbTableQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.contract.DbTableService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("dbTableService")
public class DbTableServiceImpl extends BaseAutowiredDomainService implements DbTableService {
	
	private DbTableInfo convertDO2Info(DbTableDO DO) {
		if (DO == null)
			return null;
		DbTableInfo info = new DbTableInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	protected FcsBaseResult createResult() {
		return new FcsBaseResult();
	}
	
	@Override
	public DbTableInfo findById(long id) {
		DbTableInfo info = null;
		if (id > 0) {
			DbTableDO DO = dbTableDAO.findById(id);
			info = convertDO2Info(DO);
		}
		return info;
	}
	
	@Override
	public FcsBaseResult save(final DbTableOrder order) {
		return commonProcess(order, "保存常用数据库表", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				DbTableDO table = null;
				final Date now = FcsPmDomainHolder.get().getSysDate();
				if (order.getTableId() != null && order.getTableId() > 0) {
					table = dbTableDAO.findById(order.getTableId());
					if (table == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"常用数据库表不存在");
					}
				}
				
				if (table == null) { //新增
					table = new DbTableDO();
					BeanCopier.staticCopy(order, table, UnBoxingConverter.getInstance());
					table.setRawAddTime(now);
					table.setIsDelete("NO");
					dbTableDAO.insert(table);
				} else { //修改
					BeanCopier.staticCopy(order, table, UnBoxingConverter.getInstance());
					table.setIsDelete("NO");
					dbTableDAO.update(table);
					
				}
				
				return null;
			}
		}, null, null);
		
	}
	
	@Override
	public QueryBaseBatchResult<DbTableInfo> query(DbTableQueryOrder order) {
		QueryBaseBatchResult<DbTableInfo> baseBatchResult = new QueryBaseBatchResult<DbTableInfo>();
		
		DbTableDO queryCondition = new DbTableDO();
		
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);
		
		if (order.getTableName() != null)
			queryCondition.setTableName(order.getTableName());
		if (order.getTableForShort() != null)
			queryCondition.setTableForShort(order.getTableForShort());
		if (order.getProjectPhase() != null)
			queryCondition.setProjectPhase(order.getProjectPhase());
		long totalSize = dbTableDAO.findByConditionCount(queryCondition);
		
		PageComponent component = new PageComponent(order, totalSize);
		
		List<DbTableDO> pageList = dbTableDAO.findByCondition(queryCondition,
			component.getFirstRecord(), component.getPageSize(), order.getSortCol(),
			order.getSortOrder());
		
		List<DbTableInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (DbTableDO table : pageList) {
				list.add(convertDO2Info(table));
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public FcsBaseResult deleteById(final DbTableOrder order) {
		return commonProcess(order, "删除常用数据库表", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				if (order.getTableId() <= 0) {
					throw ExceptionFactory
						.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "未找到常用数据库表");
				} else {
					DbTableDO DO = dbTableDAO.findById(order.getTableId());
					DO.setIsDelete("IS");
					dbTableDAO.update(DO);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public long findByName(String tableName) {
		DbTableDO DO = new DbTableDO();
		DO.setTableName(tableName);
		return dbTableDAO.findByName(DO);
	}
	
}
