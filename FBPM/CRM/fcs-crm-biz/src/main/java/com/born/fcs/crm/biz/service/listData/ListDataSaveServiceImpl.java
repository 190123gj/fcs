package com.born.fcs.crm.biz.service.listData;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.crm.biz.service.customer.BaseAutowiredDAO;
import com.born.fcs.crm.dal.dataobject.ListDataSaveDO;
import com.born.fcs.crm.ws.service.ListDataSaveService;
import com.born.fcs.crm.ws.service.info.ListDataInfo;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;

@Service("listDataSaveService")
public class ListDataSaveServiceImpl extends BaseAutowiredDAO implements ListDataSaveService {
	
	@Override
	public FcsBaseResult add(ListDataInfo info) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			ListDataSaveDO listDataSave = new ListDataSaveDO();
			BeanCopier.staticCopy(info, listDataSave);
			listDataSaveDAO.insert(listDataSave);
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("新增集合数据异常：", e);
		}
		
		return result;
	}
	
	@Override
	public FcsBaseResult update(ListDataInfo info) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			ListDataSaveDO listDataSave = new ListDataSaveDO();
			BeanCopier.staticCopy(info, listDataSave);
			listDataSaveDAO.update(listDataSave);
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("更新集合数据异常：", e);
		}
		
		return result;
	}
	
	@Override
	public FcsBaseResult delete(long id) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			
			listDataSaveDAO.deleteById(id);
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("删除集合数据异常id={}：", id, e);
		}
		
		return result;
	}
	
	@Override
	public FcsBaseResult deleteByType(String type) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			
			listDataSaveDAO.deleteByType(type);
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("删除集合数据异常type={}：", type, e);
		}
		
		return result;
	}
	
	@Override
	public FcsBaseResult updateByList(List<ListDataInfo> list, long id, String types) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			List<Long> ids = new ArrayList<Long>();
			if (ListUtil.isNotEmpty(list)) {
				String des = String.valueOf(id);
				
				String type = "";
				for (ListDataInfo info : list) {
					info.setDescription(info.getDataType() + "_" + des);//用description区分当前集合是属于哪条数据,功能满足目前需要，暂不使用
					if (StringUtil.notEquals(type, info.getDataType())) {
						List<Long> typeId = listDataSaveDAO.findByAllIds(info.getDataType());
						ids.addAll(typeId);
					}
					type = info.getDataType();
					if (info.getId() > 0) {
						update(info);
						ids.remove(info.getId());
					} else {
						add(info);
					}
				}
				
			} else if (StringUtil.isNotBlank(types)) {
				List<Long> typeId = listDataSaveDAO.findByAllIds(types);
				ids.addAll(typeId);
			}
			if (ListUtil.isNotEmpty(ids)) {
				for (Long deId : ids) {
					delete(deId);
				}
			}
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("批量更新集合数据异常：", e);
		}
		return result;
	}
	
	@Override
	public Long countByType(String type) {
		long count = 0;
		try {
			ListDataSaveDO listDataSave = new ListDataSaveDO();
			listDataSave.setDataType(type);
			count = listDataSaveDAO.countWithCondition(listDataSave);
		} catch (Exception e) {
			logger.error("按类型统计集合数据异常：", e);
		}
		return count;
	}
	
	@Override
	public List<ListDataInfo> list(String type) {
		List<ListDataInfo> list = new ArrayList<>();
		try {
			ListDataSaveDO listDataSave = new ListDataSaveDO();
			listDataSave.setDataType(type);
			List<ListDataSaveDO> result = listDataSaveDAO.findWithCondition(listDataSave, 0, 200);
			ListDataInfo info = null;
			for (ListDataSaveDO Do : result) {
				info = new ListDataInfo();
				BeanCopier.staticCopy(Do, info);
				list.add(info);
			}
		} catch (Exception e) {
			logger.error("按类型统计集合数据异常：", e);
		}
		return list;
	}
}
