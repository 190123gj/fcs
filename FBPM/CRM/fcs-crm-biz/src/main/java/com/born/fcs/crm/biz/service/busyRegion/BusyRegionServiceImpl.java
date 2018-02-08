package com.born.fcs.crm.biz.service.busyRegion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.biz.service.customer.BaseAutowiredDAO;
import com.born.fcs.crm.dal.dataobject.BusyRegionDO;
import com.born.fcs.crm.ws.service.BusyRegionService;
import com.born.fcs.crm.ws.service.info.BusyRegionInfo;
import com.born.fcs.crm.ws.service.order.BusyRegionOrder;
import com.born.fcs.crm.ws.service.order.BusyRegionOrder.region;
import com.born.fcs.crm.ws.service.order.query.BusyRegionQueryOrder;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

@Service("busyRegionService")
public class BusyRegionServiceImpl extends BaseAutowiredDAO implements BusyRegionService {
	
	@Override
	public FcsBaseResult save(BusyRegionOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			order.check();
			busyRegionDAO.deleteByDepPath(order.getDepPath());
			if (ListUtil.isNotEmpty(order.getRegion())) {
				for (region reg : order.getRegion()) {
					BusyRegionDO busyRegion = new BusyRegionDO();
					BeanCopier.staticCopy(order, busyRegion);
					BeanCopier.staticCopy(reg, busyRegion);
					busyRegionDAO.insert(busyRegion);
				}
			}
			result.setSuccess(true);
			result.setMessage("部门区域保存成功！");
			result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
		} catch (IllegalArgumentException e) {
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(e.getMessage());
			logger.error("部门区域保存异常", e);
		} catch (DataAccessException e) {
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("部门区域保存异常", e);
		} catch (Exception e) {
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage("未知异常");
			logger.error("部门区域保存异常", e);
		}
		return result;
	}
	
	@Override
	public QueryBaseBatchResult<BusyRegionInfo> list(BusyRegionQueryOrder order) {
		QueryBaseBatchResult<BusyRegionInfo> result = new QueryBaseBatchResult<BusyRegionInfo>();
		BusyRegionDO busyRegion = new BusyRegionDO();
		BeanCopier.staticCopy(order, busyRegion);
		busyRegion.setDepName(null);
		long count = busyRegionDAO.countWithCondition(busyRegion);
		PageComponent component = new PageComponent(order, count);
		List<BusyRegionDO> list = busyRegionDAO.findWithCondition(busyRegion,
			component.getFirstRecord(), component.getPageSize());
		BusyRegionInfo busyRegionInfo = null;
		List<BusyRegionInfo> pageList = new ArrayList<>();
		for (BusyRegionDO info : list) {
			busyRegionInfo = new BusyRegionInfo();
			BeanCopier.staticCopy(info, busyRegionInfo);
			if (StringUtil.isNotBlank(info.getName()))
				busyRegionInfo.setName(info.getName().replaceAll(";", ","));
			if (StringUtil.isNotBlank(info.getCode()))
				busyRegionInfo.setCode(info.getCode().replaceAll(";", ","));
			pageList.add(busyRegionInfo);
		}
		result.setPageList(pageList);
		result.initPageParam(component);
		result.setSuccess(true);
		return result;
	}
	
	@Override
	public FcsBaseResult changeStatus(String depPath, BooleanEnum status) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			if (status != null) {
				BusyRegionDO busyRegion = new BusyRegionDO();
				busyRegion.setDepPath(depPath);
				busyRegion.setStatus(status.code());
				busyRegionDAO.updateStatus(busyRegion);
				result.setSuccess(true);
				result.setMessage("状态修改成功！");
			}
			
			result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
		} catch (IllegalArgumentException e) {
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(e.getMessage());
			logger.error("状态修改异常", e);
		} catch (DataAccessException e) {
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("状态修改异常", e);
		} catch (Exception e) {
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage("未知异常");
			logger.error("状态修改异常", e);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult queryStatus(String depPath) {
		FcsBaseResult result = new FcsBaseResult();
		BusyRegionDO busyRegion = new BusyRegionDO();
		busyRegion.setDepPath(depPath);
		List<BusyRegionDO> list = busyRegionDAO.findWithCondition(busyRegion, 0, 1);
		result.setMessage("未启用");
		if (ListUtil.isNotEmpty(list)) {
			if (BooleanEnum.IS.code().equals(list.get(0).getStatus())) {
				result.setSuccess(true);
				result.setMessage("已启用");
			}
		}
		return result;
	}
	
	@Override
	public List<BusyRegionInfo> queryByDepPath(String depPath) {
		List<BusyRegionInfo> list = new ArrayList<>();
		try {
			List<BusyRegionDO> data = extraDAO.queryByDepPath(depPath);
			if (ListUtil.isNotEmpty(data)) {
				for (BusyRegionDO doInfo : data) {
					//					if (StringUtil.equalsIgnoreCase(doInfo.getCode(), "China")) {
					//						list = new ArrayList<>();
					//						break;
					//					}
					BusyRegionInfo info = new BusyRegionInfo();
					BeanCopier.staticCopy(doInfo, info);
					list.add(info);
				}
			}
		} catch (Exception e) {
			logger.error("按部门查询业务区域异常", e);
			
		}
		return list;
	}
	
	@Override
	public List<BusyRegionInfo> queryAllByDepPath(String depPath) {
		List<BusyRegionInfo> list = new ArrayList<>();
		try {
			BusyRegionDO busyRegion = new BusyRegionDO();
			busyRegion.setDepPath(depPath);
			List<BusyRegionDO> data = busyRegionDAO.findAllByPath(busyRegion);
			if (ListUtil.isNotEmpty(data)) {
				for (BusyRegionDO doInfo : data) {
					//					if (StringUtil.equalsIgnoreCase(doInfo.getCode(), "China")) {
					//						list = new ArrayList<>();
					//						break;
					//					}
					BusyRegionInfo info = new BusyRegionInfo();
					BeanCopier.staticCopy(doInfo, info);
					list.add(info);
				}
			}
		} catch (Exception e) {
			logger.error("按部门查询业务区域异常", e);
			
		}
		return list;
	}
}
