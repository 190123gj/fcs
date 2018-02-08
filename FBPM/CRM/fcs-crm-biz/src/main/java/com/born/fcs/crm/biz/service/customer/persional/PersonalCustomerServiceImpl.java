package com.born.fcs.crm.biz.service.customer.persional;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.biz.service.customer.BaseAutowiredDAO;
import com.born.fcs.crm.dal.dataobject.CustomerBaseInfoDO;
import com.born.fcs.crm.dal.dataobject.CustomerPersonDetailDO;
import com.born.fcs.crm.dal.dataobject.CustomerRelationListDO;
import com.born.fcs.crm.ws.service.CustomerService;
import com.born.fcs.crm.ws.service.PersonalCompanyService;
import com.born.fcs.crm.ws.service.PersonalCustomerService;
import com.born.fcs.crm.ws.service.info.CustomerBaseInfo;
import com.born.fcs.crm.ws.service.info.PersonalCustomerInfo;
import com.born.fcs.crm.ws.service.order.DistributionOrder;
import com.born.fcs.crm.ws.service.order.PersonalCustomerDetailOrder;
import com.born.fcs.crm.ws.service.order.TransferAllocationOrder;
import com.born.fcs.crm.ws.service.order.query.CustomerQueryOrder;
import com.born.fcs.crm.ws.service.result.ValidateCustomerResult;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

@Service("personalCustomerService")
public class PersonalCustomerServiceImpl extends BaseAutowiredDAO implements
																	PersonalCustomerService {
	
	@Autowired
	private PersonalCompanyService personalCompanyService;
	@Autowired
	private CustomerService customerService;
	
	@Override
	public FcsBaseResult add(PersonalCustomerDetailOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			String customerId = BusinessNumberUtil.gainNumber();
			order.setCustomerId(customerId);
			order.setCustomerType(com.born.fcs.pm.ws.enums.CustomerTypeEnum.PERSIONAL.code());
			order.setRawAddTime(sqlDateService.getSqlDate());
			order.setIsDistribution(StringUtil.defaultIfBlank(order.getIsDistribution(), "NO"));
			CustomerBaseInfoDO customerBaseInfo = new CustomerBaseInfoDO();
			CustomerPersonDetailDO customerPersonDetail = new CustomerPersonDetailDO();
			BeanCopier.staticCopy(order, customerBaseInfo);
			BeanCopier.staticCopy(order, customerPersonDetail);
			if (customerBaseInfo.getCustomerManagerId() > 0) {
				customerBaseInfo.setIsDistribution(BooleanEnum.IS.code());
			}
			long userId = customerBaseInfoDAO.insert(customerBaseInfo);
			customerPersonDetailDAO.insert(customerPersonDetail);
			personalCompanyService.updateByList(order.getReqList(), customerId);
			
			if ((order.getDirectorId() > 0 || order.getCustomerManagerId() > 0)
				&& StringUtil.isNotBlank(order.getDepPath())) {
				//总监或客户经理添加客户
				TransferAllocationOrder transferAllocationOrder = new TransferAllocationOrder();
				BeanCopier.staticCopy(order, transferAllocationOrder);
				transferAllocationOrder.setUserId(userId);
				customerService.transferAllocation(transferAllocationOrder);
			}
			
			result.setSuccess(true);
			result.setKeyId(userId);
			result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
		} catch (IllegalArgumentException e) {
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(e.getMessage());
			logger.error("新增个人客户异常", e);
		} catch (DataAccessException e) {
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("新增个人客户异常", e);
		} catch (Exception e) {
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage("未知异常");
			logger.error("新增个人客户异常", e);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult update(PersonalCustomerDetailOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			order.check();
			order.setCustomerType(com.born.fcs.pm.ws.enums.CustomerTypeEnum.PERSIONAL.code());
			PersonalCustomerInfo personalCustomerInfo = queryById(order.getCustomerId());
			BeanCopier.staticCopy(order, personalCustomerInfo);
			CustomerBaseInfoDO customerBaseInfoDO = new CustomerBaseInfoDO();
			CustomerPersonDetailDO customerPersonDetailDO = new CustomerPersonDetailDO();
			BeanCopier.staticCopy(personalCustomerInfo, customerBaseInfoDO);
			BeanCopier.staticCopy(personalCustomerInfo, customerPersonDetailDO);
			if (customerBaseInfoDO.getCustomerManagerId() > 0) {
				customerBaseInfoDO.setIsDistribution(BooleanEnum.IS.code());
			}
			customerBaseInfoDAO.updateByCustomerId(customerBaseInfoDO);
			customerPersonDetailDAO.updateByCustomerId(customerPersonDetailDO);
			personalCompanyService.updateByList(order.getReqList(), order.getCustomerId());
			result.setSuccess(true);
			result.setMessage("更新客户成功！");
			result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
		} catch (IllegalArgumentException e) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(e.getMessage());
			logger.error("更新个人客户异常", e);
		} catch (DataAccessException e) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("更新个人客户异常", e);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage("未知异常");
			logger.error("更新个人客户异常", e);
		}
		return result;
	}
	
	@Override
	public PersonalCustomerInfo queryById(String customerId) {
		PersonalCustomerInfo personalCustomerInfo = new PersonalCustomerInfo();
		CustomerBaseInfoDO customerBaseInfoDO = customerBaseInfoDAO.findByCustomerId(customerId);
		CustomerPersonDetailDO customerPersonDetailDO = customerPersonDetailDAO
			.findByCustomerId(customerId);
		BeanCopier.staticCopy(customerPersonDetailDO, personalCustomerInfo);
		BeanCopier.staticCopy(customerBaseInfoDO, personalCustomerInfo);
		return personalCustomerInfo;
	}
	
	@Override
	public PersonalCustomerInfo queryByUserId(Long userId) {
		PersonalCustomerInfo personalCustomerInfo = new PersonalCustomerInfo();
		CustomerBaseInfoDO customerBaseInfoDO = customerBaseInfoDAO.findByUserId(userId);
		CustomerPersonDetailDO customerPersonDetailDO = customerPersonDetailDAO
			.findByCustomerId(customerBaseInfoDO.getCustomerId());
		BeanCopier.staticCopy(customerPersonDetailDO, personalCustomerInfo);
		BeanCopier.staticCopy(customerBaseInfoDO, personalCustomerInfo);
		return personalCustomerInfo;
	}
	
	@Override
	public QueryBaseBatchResult<CustomerBaseInfo> list(CustomerQueryOrder queryOrder) {
		queryOrder.setCustomerType(com.born.fcs.pm.ws.enums.CustomerTypeEnum.PERSIONAL.code());
		return customerList(queryOrder);
	}
	
	@Override
	public FcsBaseResult delete(Long userId) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			PersonalCustomerInfo personalCustomerInfo = queryByUserId(userId);
			if (personalCustomerInfo != null) {
				ProjectQueryOrder projectQueryOrder = new ProjectQueryOrder();
				projectQueryOrder.setCustomerId(userId);
				QueryBaseBatchResult<ProjectInfo> project = projectServiceClient
					.queryProject(projectQueryOrder);
				if (project.isSuccess()) {
					if (ListUtil.isNotEmpty(project.getPageList())) {
						result.setSuccess(false);
						result.setMessage("该客户有关联项目，不允许删除！");
					} else {
						customerBaseInfoDAO.deleteByUserId(userId);
						customerPersonDetailDAO.deleteByCustomerId(personalCustomerInfo
							.getCustomerId());
						result.setSuccess(true);
						result.setMessage("删除客户成功！");
					}
				} else {
					result.setSuccess(false);
					result.setMessage("查询关联项目失败，请稍后在试！");
				}
			} else {
				result.setSuccess(false);
				result.setMessage("未找到该用户！");
			}
			
		} catch (DataAccessException e) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage(e.getMessage());
			logger.error("删除个人客户异常", e);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage("未知异常");
			logger.error("删除个人客户异常", e);
		}
		return result;
	}
	
	@Override
	public PersonalCustomerInfo queryByName(String customerName) {
		PersonalCustomerInfo personalCustomerInfo = null;
		try {
			CustomerBaseInfoDO customerBaseInfo = new CustomerBaseInfoDO();
			customerBaseInfo.setCustomerName(customerName);
			List<CustomerBaseInfoDO> list = customerBaseInfoDAO.findWithCondition(customerBaseInfo,
				0, 100, null, null, null, null, null, null, 0, null, null);
			if (ListUtil.isNotEmpty(list)) {
				personalCustomerInfo = new PersonalCustomerInfo();
				CustomerPersonDetailDO customerPersonDetailDO = customerPersonDetailDAO
					.findByCustomerId(list.get(0).getCustomerId());
				BeanCopier.staticCopy(customerPersonDetailDO, personalCustomerInfo);
				BeanCopier.staticCopy(list.get(0), personalCustomerInfo);
			}
			
		} catch (Exception e) {
			logger.error("查询客户详情异常：", e);
			
		}
		
		return personalCustomerInfo;
	}
	
	@Override
	public PersonalCustomerInfo queryByCertNo(String certNo) {
		PersonalCustomerInfo personalCustomerInfo = null;
		try {
			CustomerBaseInfoDO customerBaseInfo = new CustomerBaseInfoDO();
			customerBaseInfo.setCertNo(certNo);
			List<CustomerBaseInfoDO> list = customerBaseInfoDAO.findWithCondition(customerBaseInfo,
				0, 100, null, null, null, null, null, null, 0, null, null);
			if (ListUtil.isNotEmpty(list)) {
				personalCustomerInfo = new PersonalCustomerInfo();
				CustomerPersonDetailDO customerPersonDetailDO = customerPersonDetailDAO
					.findByCustomerId(list.get(0).getCustomerId());
				BeanCopier.staticCopy(customerPersonDetailDO, personalCustomerInfo);
				BeanCopier.staticCopy(list.get(0), personalCustomerInfo);
			}
			
		} catch (Exception e) {
			logger.error("查询客户详情异常：", e);
			
		}
		
		return personalCustomerInfo;
	}
	
	@Override
	public ValidateCustomerResult ValidateCustomer(String certNo, String customerName,
													Long inputPersonId) {
		ValidateCustomerResult result = new ValidateCustomerResult();
		CustomerQueryOrder queryOrder = new CustomerQueryOrder();
		queryOrder.setCertNo(certNo);
		queryOrder.setCustomerName(customerName);
		QueryBaseBatchResult<CustomerBaseInfo> batchResult = list(queryOrder);
		if (batchResult.isSuccess() && ListUtil.isNotEmpty(batchResult.getPageList())) {
			for (CustomerBaseInfo info : batchResult.getPageList()) {
				
				if (StringUtil.isBlank(info.getCustomerManager())) {
					//1.公海客户
					result.setType("pub");
					result.setMessage("您所添加的客户系统中已存在，属于公海客户！");
				} else if (inputPersonId != null && inputPersonId == info.getCustomerManagerId()) {
					//3.自己的客户
					result.setType("per");
					result.setMessage("该客户是您的历史客户");
					ProjectQueryOrder projectQueryOrder = new ProjectQueryOrder();
					projectQueryOrder.setCustomerId(info.getUserId());
					QueryBaseBatchResult<ProjectInfo> project = projectServiceClient
						.queryProject(projectQueryOrder);
					if (project.isSuccess() && ListUtil.isNotEmpty(project.getPageList())) {
						result.setHaveProject(true);
						result.setList(project.getPageList());
					}
				} else {
					//4.别人的客户
					result.setType("other");
					result.setCustomerManager(info.getCustomerManager());
					result.setMessage("该客户已存在，对应客户经理为:" + info.getDepName()
										+ info.getCustomerManager());
				}
				result.setUserId(info.getUserId());
			}
			result.setSuccess(false);
			
		} else {
			result.setSuccess(true);
			result.setMessage("客户可用");
			
		}
		
		return result;
	}
	
	@Override
	public QueryBaseBatchResult<ProjectInfo> queryProject(ProjectQueryOrder order) {
		QueryBaseBatchResult<ProjectInfo> project = projectServiceClient.queryProject(order);
		//		//诉讼类项目授信开始时间取合同签订时间 (合同回传的时候已经写进去了，这里不用在处理)
		//		if (project != null && ListUtil.isNotEmpty(project.getPageList())) {
		//			List<ProjectInfo> list = new ArrayList<>();
		//			for (ProjectInfo info : project.getPageList()) {
		//				if (ProjectUtil.isLitigation(info.getBusiType())) {
		//					info.setStartTime(info.getContractTime());
		//				}
		//				list.add(info);
		//			}
		//			project.setPageList(list);
		//		}
		return project;
	}
	
	@Override
	public FcsBaseResult distribution(DistributionOrder distributionOrder) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			distributionOrder.check();
			CustomerBaseInfoDO customerBaseInfo = new CustomerBaseInfoDO();
			BeanCopier.staticCopy(distributionOrder, customerBaseInfo);
			if (distributionOrder.getDirectorId() > 0) {
				customerBaseInfoDAO.updateDirector(customerBaseInfo);
			}
			if (distributionOrder.getCustomerManagerId() > 0) {
				customerBaseInfo.setIsDistribution(BooleanEnum.IS.code());
				customerBaseInfoDAO.updateManager(customerBaseInfo);
			}
			result.setSuccess(true);
			result.setMessage("操作成功");
		} catch (IllegalArgumentException e) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(e.getMessage());
			logger.error("移交或分配客户异常", e);
		} catch (DataAccessException e) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("移交或分配客户异常", e);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage("未知异常");
			logger.error("移交或分配客户异常", e);
		}
		return result;
	}
	
	@Override
	public QueryBaseBatchResult<CustomerBaseInfo> customerList(CustomerQueryOrder queryOrder) {
		QueryBaseBatchResult<CustomerBaseInfo> result = new QueryBaseBatchResult<CustomerBaseInfo>();
		CustomerRelationListDO customerBaseInfo = new CustomerRelationListDO();
		BeanCopier.staticCopy(queryOrder, customerBaseInfo);
		long count = customerRelationListDAO.countWithCondition(customerBaseInfo,
			queryOrder.getCustomerLevelList(), queryOrder.getIndustryCodeList(),
			queryOrder.getCityCodeList(), queryOrder.getBeginDate(), queryOrder.getEndDate(),
			queryOrder.getLikeCustomerName(), queryOrder.getLoginUserId(),
			queryOrder.getDeptIdList(), queryOrder.getLikeNameOrId(), queryOrder.getMobileQuery(),
			queryOrder.getLikeCertNo(), queryOrder.getLikeBusiLicenseNo(),queryOrder.getIncludePublic());
		PageComponent component = new PageComponent(queryOrder, count);
		List<CustomerRelationListDO> list = customerRelationListDAO.findWithCondition(
			customerBaseInfo, component.getFirstRecord(), component.getPageSize(),
			queryOrder.getCustomerLevelList(), queryOrder.getIndustryCodeList(),
			queryOrder.getCityCodeList(), queryOrder.getBeginDate(), queryOrder.getEndDate(),
			queryOrder.getLikeCustomerName(), queryOrder.getLoginUserId(),
			queryOrder.getDeptIdList(), queryOrder.getLikeNameOrId(), queryOrder.getMobileQuery(),
			queryOrder.getLikeCertNo(), queryOrder.getLikeBusiLicenseNo(),queryOrder.getIncludePublic());
		List<CustomerBaseInfo> rsList = new ArrayList<CustomerBaseInfo>();
		if (ListUtil.isNotEmpty(list)) {
			CustomerBaseInfo rsInfo = null;
			for (CustomerRelationListDO info : list) {
				rsInfo = new CustomerBaseInfo();
				BeanCopier.staticCopy(info, rsInfo);
				if (info.getCustomerManagerId() > 0) {
					rsInfo.setIsDistribution("IS");
				} else {
					rsInfo.setIsDistribution("NO");
				}
				
				rsList.add(rsInfo);
				checkProjecteStatus(info.getUserId(), info.getProjectStatus());
			}
			result.initPageParam(component);
		}
		result.setSuccess(true);
		result.setPageList(rsList);
		return result;
	}
}
