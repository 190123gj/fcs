package com.born.fcs.pm.biz.service.basicmaintain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.SysDataDictionaryDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.enums.DataCodeEnum;
import com.born.fcs.pm.ws.info.basicmaintain.SysDataDictionaryInfo;
import com.born.fcs.pm.ws.order.basicmaintain.SysDataDictionaryDetailOrder;
import com.born.fcs.pm.ws.order.basicmaintain.SysDataDictionaryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.basicmaintain.SysDataDictionaryService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("sysDataDictionaryService")
public class SysDataDictionaryServiceImpl extends BaseAutowiredDomainService implements
																			SysDataDictionaryService {
	
	@Override
	public FcsBaseResult save(final SysDataDictionaryOrder order) {
		return commonProcess(order, "保存字典数据", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				DataCodeEnum dataCode = order.getDataCode();
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				//查询已经存在的
				List<SysDataDictionaryDO> exists = sysDataDictionaryDAO.findByDataCode(dataCode
					.code());
				Map<Long, SysDataDictionaryDO> existMap = Maps.newHashMap();
				if (ListUtil.isNotEmpty(exists)) {
					for (SysDataDictionaryDO exist : exists) {
						existMap.put(exist.getId(), exist);
					}
				}
				
				//保存数据
				for (SysDataDictionaryDetailOrder data : order.getDataOrder()) {
					SysDataDictionaryDO DO = null;
					if (data.getId() != null && data.getId() > 0) {
						DO = existMap.get(data.getId());
					}
					if (DO == null) {
						DO = new SysDataDictionaryDO();
						BeanCopier.staticCopy(data, DO);
						DO.setParentId(data.getParentId() == null ? 0 : data.getParentId());
						DO.setSortOrder(data.getSortOrder() == null ? 0 : data.getSortOrder());
						DO.setDataCode(dataCode.code());
						DO.setRawAddTime(now);
						sysDataDictionaryDAO.insert(DO);
					} else {
						BeanCopier.staticCopy(data, DO);
						DO.setParentId(data.getParentId() == null ? 0 : data.getParentId());
						DO.setSortOrder(data.getSortOrder() == null ? 0 : data.getSortOrder());
						DO.setDataCode(dataCode.code());
						sysDataDictionaryDAO.update(DO);
						existMap.remove(DO.getId());
					}
				}
				
				//删掉数据
				if (!existMap.isEmpty()) {
					for (Long id : existMap.keySet()) {
						sysDataDictionaryDAO.deleteById(id);
					}
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public SysDataDictionaryInfo findOne(long id) {
		logger.info("根据ID查询字典数据{}", id);
		SysDataDictionaryInfo info = null;
		if (id >= 0) {
			info = convertDO2Info(sysDataDictionaryDAO.findById(id));
		}
		return info;
	}
	
	@Override
	public List<SysDataDictionaryInfo> findByDataCode(DataCodeEnum dataCode) {
		logger.info("根据分类查询字典数据{}", dataCode);
		List<SysDataDictionaryInfo> infos = null;
		if (dataCode != null) {
			List<SysDataDictionaryDO> DOS = sysDataDictionaryDAO.findByDataCodeNoChild(dataCode
				.code());
			if (ListUtil.isNotEmpty(DOS)) {
				infos = Lists.newArrayList();
				for (SysDataDictionaryDO DO : DOS) {
					infos.add(convertDO2Info(DO));
				}
			}
		}
		return infos;
	}
	
	@Override
	public List<SysDataDictionaryInfo> findByParentId(long parentId) {
		logger.info("根据上级查询字典数据{}", parentId);
		List<SysDataDictionaryInfo> infos = null;
		if (parentId > 0) {
			List<SysDataDictionaryDO> DOS = sysDataDictionaryDAO.findByParentId(parentId);
			if (ListUtil.isNotEmpty(DOS)) {
				infos = Lists.newArrayList();
				for (SysDataDictionaryDO DO : DOS) {
					infos.add(convertDO2Info(DO));
				}
			}
		}
		return infos;
	}
	
	@Override
	public SysDataDictionaryInfo findOneCascade(long id) {
		logger.info("根据ID级联查询字典数据{}", id);
		SysDataDictionaryInfo info = null;
		if (id >= 0) {
			info = convertDO2Info(sysDataDictionaryDAO.findById(id));
		}
		if (info != null && info.getChildrenNum() > 0) {
			info.setChildren(findByParentIdCascade(info.getId()));
		}
		return info;
	}
	
	@Override
	public List<SysDataDictionaryInfo> findByDataCodeCascade(DataCodeEnum dataCode) {
		logger.info("根据分类级联查询字典数据{}", dataCode);
		List<SysDataDictionaryInfo> infos = null;
		if (dataCode != null) {
			List<SysDataDictionaryDO> DOS = sysDataDictionaryDAO.findByDataCodeNoChild(dataCode
				.code());
			if (ListUtil.isNotEmpty(DOS)) {
				infos = Lists.newArrayList();
				for (SysDataDictionaryDO DO : DOS) {
					infos.add(convertDO2Info(DO));
				}
			}
		}
		if (ListUtil.isNotEmpty(infos)) {
			for (SysDataDictionaryInfo info : infos) {
				if (info.getChildrenNum() > 0)
					info.setChildren(findByParentIdCascade(info.getId()));
			}
		}
		return infos;
	}
	
	@Override
	public List<SysDataDictionaryInfo> findByParentIdCascade(long parentId) {
		logger.info("根据上级级联查询字典数据{}", parentId);
		List<SysDataDictionaryInfo> infos = null;
		if (parentId > 0) {
			List<SysDataDictionaryDO> DOS = sysDataDictionaryDAO.findByParentId(parentId);
			if (ListUtil.isNotEmpty(DOS)) {
				infos = Lists.newArrayList();
				for (SysDataDictionaryDO DO : DOS) {
					infos.add(convertDO2Info(DO));
				}
			}
		}
		if (ListUtil.isNotEmpty(infos)) {
			for (SysDataDictionaryInfo info : infos) {
				if (info.getChildrenNum() > 0)
					info.setChildren(findByParentIdCascade(info.getId()));
			}
		}
		return infos;
	}
	
	/**
	 * DO转Info
	 * @param DO
	 * @return
	 */
	private SysDataDictionaryInfo convertDO2Info(SysDataDictionaryDO DO) {
		if (DO == null)
			return null;
		SysDataDictionaryInfo info = new SysDataDictionaryInfo();
		BeanCopier.staticCopy(DO, info);
		info.setDataCode(DataCodeEnum.getByCode(DO.getDataCode()));
		return info;
	}
}
