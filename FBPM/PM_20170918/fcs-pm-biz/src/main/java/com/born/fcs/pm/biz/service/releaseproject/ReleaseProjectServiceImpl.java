package com.born.fcs.pm.biz.service.releaseproject;

import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dal.dataobject.ReleaseProjectDO;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.enums.ReleaseProjectStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.order.release.UpdateReleaseBaseOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.releaseproject.ReleaseProjectService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * 
 * 解保项目
 * 
 * @author lirz
 * 
 * 2016-6-1 下午1:48:10
 */
@Service("releaseProjectService")
public class ReleaseProjectServiceImpl extends BaseFormAutowiredDomainService implements
																				ReleaseProjectService {
	
	@Override
	public FcsBaseResult add(String projectCode) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			ProjectDO project = projectDAO.findByProjectCode(projectCode);
			if (null == project) {
				result.setSuccess(false);
				result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
				result.setMessage("没有找到项目数据");
				return result;
			}
			
			if (ProjectUtil.isUnderwriting(project.getBusiType())) {
				//承销不需要解保
			} else if (ProjectUtil.isLitigation(project.getBusiType())) {
				//诉讼保函 主合同上传后才可解保
				save(projectCode, project.getProjectName());
			} else if (ProjectUtil.isBond(project.getBusiType())) {
				//发债业务需要授信到期才可以解保
			} else if (ProjectUtil.isEntrusted(project.getBusiType())) {
				//委货类项目:收费通知中，添加了收费种类为：委贷本金收回
			} else {
				save(projectCode, project.getProjectName());
			}
			
			result.setSuccess(true);
			return result;
		} catch (Exception e) {
			logger.error("添加到待解保列表异常: " + e.getMessage(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.EXECUTE_FAIL);
			result.setMessage("添加到待解保列表异常: " + e.getMessage());
		}
		return result;
	}
	
	private void save(String projectCode, String projectName) {
		String status = ReleaseProjectStatusEnum.AVAILABLE.code();
		ReleaseProjectDO doObj = releaseProjectDAO.findByProjectCode(projectCode);
		if (null == doObj) {
			doObj = new ReleaseProjectDO();
			doObj.setProjectCode(projectCode);
			doObj.setProjectName(projectName);
			doObj.setStatus(status);
			doObj.setRawAddTime(getSysdate());
			releaseProjectDAO.insert(doObj);
		} else {
			if (StringUtil.notEquals(doObj.getStatus(), status)) {
				doObj.setStatus(status);
				releaseProjectDAO.update(doObj);
			}
		}
	}
	
	@Override
	public FcsBaseResult addBond(String projectCode) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			ProjectDO project = projectDAO.findByProjectCode(projectCode);
			if (null == project) {
				result.setSuccess(false);
				result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
				result.setMessage("没有找到项目数据");
				return result;
			}
			
			if (ProjectUtil.isBond(project.getBusiType())) {
				//发债业务需要授信到期才可以解保
				save(projectCode, project.getProjectName());
			}
			
			result.setSuccess(true);
			return result;
		} catch (Exception e) {
			logger.error("添加到待解保列表异常: " + e.getMessage(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.EXECUTE_FAIL);
			result.setMessage("添加到待解保列表异常: " + e.getMessage());
		}
		return result;
	}
	
	@Override
	public FcsBaseResult addEntrusted(String projectCode) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			ProjectDO project = projectDAO.findByProjectCode(projectCode);
			if (null == project) {
				result.setSuccess(false);
				result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
				result.setMessage("没有找到项目数据");
				return result;
			}
			
			if (ProjectUtil.isEntrusted(project.getBusiType())) {
				//委货类项目:收费通知中，添加了收费种类为：委贷本金收回
				save(projectCode, project.getProjectName());
			}
			
			result.setSuccess(true);
			return result;
		} catch (Exception e) {
			logger.error("添加到待解保列表异常: " + e.getMessage(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.EXECUTE_FAIL);
			result.setMessage("添加到待解保列表异常: " + e.getMessage());
		}
		return result;
	}
	
	@Override
	public FcsBaseResult update(final UpdateReleaseBaseOrder order) {
		return commonProcess(order, "修改待解保项目", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				ReleaseProjectDO doObj = releaseProjectDAO.findByProjectCode(order.getProjectCode());
				if (null == doObj) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "未找到数据");
				}
				
				BeanCopier.staticCopy(order, doObj);
				releaseProjectDAO.update(doObj);
				return null;
			}
		}, null, null);
	}
	
}
