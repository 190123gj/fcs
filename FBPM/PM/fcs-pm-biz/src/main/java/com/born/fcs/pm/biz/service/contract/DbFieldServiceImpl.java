package com.born.fcs.pm.biz.service.contract;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.DbFieldDO;
import com.born.fcs.pm.dataobject.DbFieldProjectDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.contract.DbFieldInfo;
import com.born.fcs.pm.ws.info.contract.DbFieldListInfo;
import com.born.fcs.pm.ws.order.contract.DbFieldOrder;
import com.born.fcs.pm.ws.order.contract.DbFieldQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.contract.DbFieldService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("dbFieldService")
public class DbFieldServiceImpl extends BaseAutowiredDomainService implements DbFieldService {
	
	private DbFieldInfo convertDO2Info(DbFieldDO DO) {
		if (DO == null)
			return null;
		DbFieldInfo info = new DbFieldInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	private DbFieldListInfo convertDO2Info(DbFieldProjectDO DO) {
		if (DO == null)
			return null;
		DbFieldListInfo info = new DbFieldListInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	protected FcsBaseResult createResult() {
		return new FcsBaseResult();
	}
	
	@Override
	public DbFieldInfo findById(long id) {
		DbFieldInfo info = null;
		if (id > 0) {
			DbFieldDO DO = dbFieldDAO.findById(id);
			info = convertDO2Info(DO);
		}
		return info;
	}
	
	@Override
	public FcsBaseResult save(final DbFieldOrder order) {
		return commonProcess(order, "保存常用数据库字段", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				DbFieldDO Field = null;
				final Date now = FcsPmDomainHolder.get().getSysDate();
				if (order.getFieldId() != null && order.getFieldId() > 0) {
					Field = dbFieldDAO.findById(order.getFieldId());
					if (Field == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"常用数据库字段不存在");
					}
				}
				
				if (Field == null) { //新增
					Field = new DbFieldDO();
					BeanCopier.staticCopy(order, Field, UnBoxingConverter.getInstance());
					Field.setRawAddTime(now);
					Field.setIsDelete("NO");
					dbFieldDAO.insert(Field);
				} else { //修改
					BeanCopier.staticCopy(order, Field, UnBoxingConverter.getInstance());
					Field.setIsDelete("NO");
					dbFieldDAO.update(Field);
					
				}
				
				return null;
			}
		}, null, null);
		
	}
	
	@Override
	public QueryBaseBatchResult<DbFieldListInfo> query(DbFieldQueryOrder order) {
		QueryBaseBatchResult<DbFieldListInfo> baseBatchResult = new QueryBaseBatchResult<DbFieldListInfo>();
		
		DbFieldProjectDO queryCondition = new DbFieldProjectDO();
		
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);
		
		if (order.getFieldName() != null)
			queryCondition.setFieldName(order.getFieldName());
		if (order.getTableName() != null)
			queryCondition.setTableName(order.getTableName());
		if (order.getProjectPhase() != null)
			queryCondition.setProjectPhase(order.getProjectPhase());
		long totalSize = extraDAO.searchDbFieldListCount(queryCondition);
		
		PageComponent component = new PageComponent(order, totalSize);
		
		List<DbFieldProjectDO> pageList = extraDAO.searchDbFieldList(queryCondition,
			component.getFirstRecord(), component.getPageSize(),order.getSortCol(),order.getSortOrder());
		
		List<DbFieldListInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (DbFieldProjectDO Field : pageList) {
				list.add(convertDO2Info(Field));
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public FcsBaseResult deleteById(final DbFieldOrder order) {
		return commonProcess(order, "删除常用数据库字段", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				if (order.getFieldId() <= 0) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
						"未找到常用数据库字段");
				} else {
					DbFieldDO DO = dbFieldDAO.findById(order.getFieldId());
					DO.setIsDelete("IS");
					dbFieldDAO.update(DO);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public long findByFieldName(long tableId, String fieldName) {
		DbFieldDO DO = new DbFieldDO();
		DO.setTableId(tableId);
		DO.setFieldName(fieldName);
		return dbFieldDAO.findByFieldName(DO);
	}
	
	@Override
	public long findByFieldShortName(long tableId, String shortForName) {
		
		DbFieldDO DO = new DbFieldDO();
		DO.setTableId(tableId);
		DO.setFieldForShort(shortForName);
		return dbFieldDAO.findByFieldShortName(DO);
	}
	
	@Override
	public QueryBaseBatchResult<DbFieldInfo> queryByTableId(DbFieldQueryOrder order) {
		QueryBaseBatchResult<DbFieldInfo> baseBatchResult = new QueryBaseBatchResult<DbFieldInfo>();
		
		if (order == null || order.getTableId() <= 0) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.INCOMPLETE_REQ_PARAM, "表ID不能为空！");
		}
		DbFieldDO dbFieldDO = new DbFieldDO();
		dbFieldDO.setTableId(order.getTableId());
		long totalSize = dbFieldDAO.findByConditionCount(dbFieldDO);
		
		PageComponent component = new PageComponent(order, totalSize);
		
		List<DbFieldDO> pageList = dbFieldDAO.findByCondition(dbFieldDO,
			component.getFirstRecord(), component.getPageSize());
		
		List<DbFieldInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (DbFieldDO Field : pageList) {
				list.add(convertDO2Info(Field));
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
}
