package com.born.fcs.pm.biz.service.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.ProjectExtendInfoDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.ProjectExtendPropertyEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectExtendInfo;
import com.born.fcs.pm.ws.order.common.ProjectExtendFormOrder;
import com.born.fcs.pm.ws.order.common.ProjectExtendOrder;
import com.born.fcs.pm.ws.order.common.ProjectExtendQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.ProjectExtendService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("projectExtendService")
public class ProjectExtendServiceImpl extends BaseFormAutowiredDomainService implements
																			ProjectExtendService {
	
	@Override
	public FcsBaseResult saveExtendInfo(final ProjectExtendOrder order) {
		return commonProcess(order, "保存项目扩展信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				ProjectExtendInfoDO extendDO = null;
				if (order.getExtendId() != null && order.getExtendId() > 0) {
					extendDO = projectExtendInfoDAO.findById(order.getExtendId());
					if (extendDO == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"扩展属性不存在");
					}
					
					BeanCopier.staticCopy(order, extendDO);
					extendDO.setProperty(order.getProperty().code());
					extendDO.setPropertyName(order.getProperty().message());
					projectExtendInfoDAO.update(extendDO);
				} else {
					if (order.isUpdateOld() || order.isOnlyOne()) {
						List<ProjectExtendInfoDO> extendInfos = projectExtendInfoDAO
							.findByProjectCodeAndProperty(order.getProjectCode(), order
								.getProperty().code());
						if (ListUtil.isNotEmpty(extendInfos)) {
							extendDO = extendInfos.get(0);
						}
						
						if (extendDO == null) {
							extendDO = new ProjectExtendInfoDO();
							BeanCopier.staticCopy(order, extendDO);
							extendDO.setProperty(order.getProperty().code());
							extendDO.setPropertyName(order.getProperty().message());
							projectExtendInfoDAO.insert(extendDO);
						} else if (order.isUpdateOld()) {
							BeanCopier.staticCopy(order, extendDO);
							extendDO.setProperty(order.getProperty().code());
							extendDO.setPropertyName(order.getProperty().message());
							projectExtendInfoDAO.update(extendDO);
						} else if (order.isOnlyOne()) {
							throw ExceptionFactory.newFcsException(
								FcsResultEnum.DO_ACTION_STATUS_ERROR, "扩展属性已存在");
						}
						
					} else {
						extendDO = new ProjectExtendInfoDO();
						BeanCopier.staticCopy(order, extendDO);
						extendDO.setProperty(order.getProperty().code());
						extendDO.setPropertyName(order.getProperty().message());
						projectExtendInfoDAO.insert(extendDO);
					}
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FormBaseResult saveExtendInfoWithForm(final ProjectExtendFormOrder order) {
		return commonFormSaveProcess(order, "提交项目扩展信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
				}
				
				boolean isUpdate = false;
				ProjectExtendInfoDO extendDO = projectExtendInfoDAO.findByFormId(form.getFormId());
				if (extendDO == null) {
					extendDO = new ProjectExtendInfoDO();
				} else {
					isUpdate = true;
				}
				
				BeanCopier.staticCopy(order, extendDO);
				extendDO.setProperty(order.getProperty().code());
				extendDO.setPropertyName(order.getProperty().message());
				extendDO.setFormId(form.getFormId());
				
				if (isUpdate) {
					projectExtendInfoDAO.update(extendDO);
				} else {
					extendDO.setRawAddTime(now);
					projectExtendInfoDAO.insert(extendDO);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<ProjectExtendInfo> query(ProjectExtendQueryOrder order) {
		QueryBaseBatchResult<ProjectExtendInfo> batchResult = new QueryBaseBatchResult<ProjectExtendInfo>();
		
		try {
			ProjectExtendInfoDO extendDO = new ProjectExtendInfoDO();
			BeanCopier.staticCopy(order, extendDO);
			extendDO.setExtendId(order.getExtendId() == null ? 0 : order.getExtendId());
			extendDO.setFormId(order.getFormId() == null ? 0 : order.getFormId());
			extendDO.setProperty(order.getProperty() == null ? null : order.getProperty().code());
			
			long totalCount = projectExtendInfoDAO.findByConditionCount(extendDO,
				order.getLoginUserId(), order.getDeptIdList());
			PageComponent component = new PageComponent(order, totalCount);
			
			List<ProjectExtendInfoDO> list = projectExtendInfoDAO.findByCondition(extendDO,
				order.getLoginUserId(), order.getDeptIdList(), order.getSortCol(),
				order.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			
			List<ProjectExtendInfo> pageList = new ArrayList<ProjectExtendInfo>(list.size());
			for (ProjectExtendInfoDO item : list) {
				ProjectExtendInfo info = new ProjectExtendInfo();
				BeanCopier.staticCopy(item, info);
				info.setProperty(ProjectExtendPropertyEnum.getByCode(item.getProperty()));
				pageList.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询项目扩展信息失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public List<ProjectExtendInfo> findApprovalProjectExtendInfo(ProjectExtendQueryOrder order) {
		
		ProjectExtendInfoDO extendDO = new ProjectExtendInfoDO();
		BeanCopier.staticCopy(order, extendDO);
		extendDO.setExtendId(order.getExtendId() == null ? 0 : order.getExtendId());
		extendDO.setFormId(order.getFormId() == null ? 0 : order.getFormId());
		extendDO.setProperty(order.getProperty() == null ? null : order.getProperty().code());
		List<ProjectExtendInfoDO> extendInfos = busiDAO.findApprovalProjectExtendInfo(extendDO);
		
		List<ProjectExtendInfo> dataList = Lists.newArrayList();
		if (ListUtil.isNotEmpty(extendInfos)) {
			for (ProjectExtendInfoDO extend : extendInfos) {
				ProjectExtendInfo info = new ProjectExtendInfo();
				BeanCopier.staticCopy(extend, info);
				info.setProperty(ProjectExtendPropertyEnum.getByCode(extend.getProperty()));
				dataList.add(info);
			}
		}
		return dataList;
	}
	
	@Override
	public FcsBaseResult deleteByProjectCode(String projectCode) {
		FcsBaseResult result = createResult();
		try {
			projectExtendInfoDAO.deleteByProjectCode(projectCode);
			result.setSuccess(true);
			result.setMessage("删除成功");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("删除项目扩展属性出错");
			logger.error("删除项目扩展属性出错{}", e);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult deleteByProjectCodeAndProperty(String projectCode,
														ProjectExtendPropertyEnum property) {
		FcsBaseResult result = createResult();
		try {
			projectExtendInfoDAO.deleteByProjectCodeAndProperty(projectCode, property.code());
			result.setSuccess(true);
			result.setMessage("删除成功");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("删除项目扩展属性出错");
			logger.error("删除项目扩展属性出错{}", e);
		}
		return result;
	}
	
}
