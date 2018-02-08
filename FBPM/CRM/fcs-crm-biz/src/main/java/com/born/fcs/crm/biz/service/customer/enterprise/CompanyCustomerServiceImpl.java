package com.born.fcs.crm.biz.service.customer.enterprise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.biz.service.customer.BaseAutowiredDAO;
import com.born.fcs.crm.dal.dataobject.CustomerBaseInfoDO;
import com.born.fcs.crm.dal.dataobject.CustomerCompanyDetailDO;
import com.born.fcs.crm.dal.dataobject.CustomerRelationListDO;
import com.born.fcs.crm.ws.service.ChangeSaveService;
import com.born.fcs.crm.ws.service.CompanyCustomerService;
import com.born.fcs.crm.ws.service.CompanyOwnershipStructureService;
import com.born.fcs.crm.ws.service.CompanyQualificationService;
import com.born.fcs.crm.ws.service.CustomerService;
import com.born.fcs.crm.ws.service.enums.ChangeTypeEnum;
import com.born.fcs.crm.ws.service.info.CompanyCustomerInfo;
import com.born.fcs.crm.ws.service.info.CompanyOwnershipStructureInfo;
import com.born.fcs.crm.ws.service.info.CompanyQualificationInfo;
import com.born.fcs.crm.ws.service.info.CustomerBaseInfo;
import com.born.fcs.crm.ws.service.order.ChangeListOrder;
import com.born.fcs.crm.ws.service.order.CompanyCustomerDetailOrder;
import com.born.fcs.crm.ws.service.order.TransferAllocationOrder;
import com.born.fcs.crm.ws.service.order.UpdateFromCreditOrder;
import com.born.fcs.crm.ws.service.order.query.CustomerQueryOrder;
import com.born.fcs.crm.ws.service.result.ChangeResult;
import com.born.fcs.crm.ws.service.result.ValidateCustomerResult;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.forCrm.IndirectCustomerInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.forCrm.IndirectCustomerQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

@Service("companyCustomerService")
public class CompanyCustomerServiceImpl extends BaseAutowiredDAO implements CompanyCustomerService {
	
	@Autowired
	private CompanyQualificationService companyQualificationService;
	@Autowired
	private CompanyOwnershipStructureService companyOwnershipStructureService;
	@Autowired
	private ChangeSaveService changeSaveService;
	@Autowired
	private CustomerService customerService;
	
	@Override
	public FcsBaseResult add(CompanyCustomerDetailOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			String customerId = BusinessNumberUtil.gainNumber();
			order.setCustomerId(customerId);
			order.setCustomerType(com.born.fcs.pm.ws.enums.CustomerTypeEnum.ENTERPRISE.code());
			order.setRawAddTime(sqlDateService.getSqlDate());
			CustomerBaseInfoDO customerBaseInfo = new CustomerBaseInfoDO();
			CustomerCompanyDetailDO customerCompanyDetailDO = new CustomerCompanyDetailDO();
			BeanCopier.staticCopy(order, customerBaseInfo);
			BeanCopier.staticCopy(order, customerCompanyDetailDO);
			if (customerBaseInfo.getCustomerManagerId() > 0) {
				customerBaseInfo.setIsDistribution(BooleanEnum.IS.code());
			}
			long userId = customerBaseInfoDAO.insert(customerBaseInfo);
			customerCompanyDetailDAO.insert(customerCompanyDetailDO);
			companyQualificationService.updateByList(order.getCompanyQualification(), customerId);
			companyOwnershipStructureService.updateByList(order.getCompanyOwnershipStructure(),
				customerId);
			
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
			result.setMessage("数据库异常");
			logger.error("新增公司客户异常", e);
		} catch (DataAccessException e) {
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage(e.getMessage());
			logger.error("新增公司客户异常", e);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult update(CompanyCustomerDetailOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			order.check();
			order.setCustomerType(com.born.fcs.pm.ws.enums.CustomerTypeEnum.ENTERPRISE.code());
			CompanyCustomerInfo companyCustomerInfo = queryByUserId(order.getUserId(), null);
			BeanCopier.staticCopy(order, companyCustomerInfo);
			CustomerBaseInfoDO customerBaseInfoDO = new CustomerBaseInfoDO();
			CustomerCompanyDetailDO customerCompanyDetailDO = new CustomerCompanyDetailDO();
			BeanCopier.staticCopy(companyCustomerInfo, customerBaseInfoDO);
			BeanCopier.staticCopy(companyCustomerInfo, customerCompanyDetailDO);
			if (customerBaseInfoDO.getCustomerManagerId() > 0) {
				customerBaseInfoDO.setIsDistribution(BooleanEnum.IS.code());
			}
			customerBaseInfoDAO.updateByCustomerId(customerBaseInfoDO);
			customerCompanyDetailDAO.updateByCustomerId(customerCompanyDetailDO);
			companyQualificationService.updateByList(order.getCompanyQualification(),
				order.getCustomerId());
			companyOwnershipStructureService.updateByList(order.getCompanyOwnershipStructure(),
				order.getCustomerId());
			result.setSuccess(true);
			result.setMessage("更新客户成功！");
			result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
		} catch (IllegalArgumentException e) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(e.getMessage());
			logger.error("更新公司客户异常", e);
		} catch (DataAccessException e) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("更新公司客户异常", e);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage(e.getMessage());
			logger.error("更新公司客户异常", e);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult updateFromCredit(UpdateFromCreditOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			CompanyCustomerInfo companyCustomerInfo = queryByUserId(order.getUserId(), null);
			UpdateFromCreditOrder oldOrder = new UpdateFromCreditOrder();
			BeanCopier.staticCopy(companyCustomerInfo, oldOrder);
			oldOrder.setTaxRegCertificateNo(companyCustomerInfo.getTaxCertificateNo());
			ChangeResult change = changeSaveService.createChangeOrder(order, oldOrder,
				order.changeMap(), true);
			
			companyCustomerInfo.setLegalPersion(order.getLegalPersion());
			companyCustomerInfo.setEstablishedTime(order.getEstablishedTime());
			companyCustomerInfo.setRegisterCapital(order.getRegisterCapital());
			companyCustomerInfo.setBusiScope(order.getBusiScope());
			companyCustomerInfo.setAddress(order.getAddress());
			companyCustomerInfo.setOrgCode(order.getOrgCode());
			companyCustomerInfo.setLocalTaxCertNo(order.getLocalTaxCertNo());
			companyCustomerInfo.setTaxCertificateNo(order.getTaxRegCertificateNo());
			companyCustomerInfo.setLoanCardNo(order.getLoanCardNo());
			CustomerBaseInfoDO customerBaseInfoDO = new CustomerBaseInfoDO();
			CustomerCompanyDetailDO customerCompanyDetailDO = new CustomerCompanyDetailDO();
			BeanCopier.staticCopy(companyCustomerInfo, customerBaseInfoDO);
			BeanCopier.staticCopy(companyCustomerInfo, customerCompanyDetailDO);
			customerBaseInfoDAO.updateByCustomerId(customerBaseInfoDO);
			customerCompanyDetailDAO.updateByCustomerId(customerCompanyDetailDO);
			result.setSuccess(true);
			result.setMessage("更新客户成功！");
			result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
			if (change != null && change.isSuccess()) {
				ChangeListOrder changeListOrder = new ChangeListOrder();
				BeanCopier.staticCopy(order, changeListOrder);
				changeListOrder.setCustomerUserId(order.getUserId());
				changeListOrder.setChageDetailList(change.getChangeOrder());
				changeListOrder.setChangeType(ChangeTypeEnum.ZX);
				changeSaveService.save(changeListOrder);
			}
		} catch (IllegalArgumentException e) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(e.getMessage());
			logger.error("更新公司客户异常", e);
		} catch (DataAccessException e) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("更新公司客户异常", e);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage(e.getMessage());
			logger.error("更新公司客户异常", e);
		}
		return result;
	}
	
	@Override
	public CompanyCustomerInfo queryByUserId(Long userId, Long formId) {
		try {
			CompanyCustomerInfo companyCustomerInfo = new CompanyCustomerInfo();
			CustomerBaseInfoDO customerBaseInfoDO = customerBaseInfoDAO.findByUserId(userId);
			String crm_customerId = customerBaseInfoDO.getCustomerId();
			CustomerCompanyDetailDO customerCompanyDetailDO = customerCompanyDetailDAO
				.findByCustomerId(crm_customerId);
			BeanCopier.staticCopy(customerBaseInfoDO, companyCustomerInfo);
			BeanCopier.staticCopy(customerCompanyDetailDO, companyCustomerInfo);
			List<CompanyQualificationInfo> companyQualification = companyQualificationService
				.list(crm_customerId);
			List<CompanyOwnershipStructureInfo> companyOwnershipStructure = companyOwnershipStructureService
				.list(crm_customerId);
			companyCustomerInfo.setCompanyOwnershipStructure(companyOwnershipStructure);
			companyCustomerInfo.setCompanyQualification(companyQualification);
			companyCustomerInfo.setEvalueInfo(getEvalueInfo(formId));
			companyCustomerInfo.setLastLevel(lastEvalueLevel(userId));
			return companyCustomerInfo;
			
		} catch (Exception e) {
			logger.error("查询公司客户详情异常：", e);
		}
		return null;
		
	}
	
	@Override
	public CompanyCustomerInfo queryById(String customerId, Long formId) {
		try {
			CompanyCustomerInfo companyCustomerInfo = new CompanyCustomerInfo();
			CustomerBaseInfoDO customerBaseInfoDO = customerBaseInfoDAO
				.findByCustomerId(customerId);
			String crm_customerId = customerBaseInfoDO.getCustomerId();
			CustomerCompanyDetailDO customerCompanyDetailDO = customerCompanyDetailDAO
				.findByCustomerId(crm_customerId);
			BeanCopier.staticCopy(customerBaseInfoDO, companyCustomerInfo);
			BeanCopier.staticCopy(customerCompanyDetailDO, companyCustomerInfo);
			List<CompanyQualificationInfo> companyQualification = companyQualificationService
				.list(crm_customerId);
			List<CompanyOwnershipStructureInfo> companyOwnershipStructure = companyOwnershipStructureService
				.list(crm_customerId);
			companyCustomerInfo.setCompanyOwnershipStructure(companyOwnershipStructure);
			companyCustomerInfo.setCompanyQualification(companyQualification);
			companyCustomerInfo.setEvalueInfo(getEvalueInfo(formId));
			companyCustomerInfo.setLastLevel(lastEvalueLevel(companyCustomerInfo.getUserId()));
			return companyCustomerInfo;
			
		} catch (Exception e) {
			logger.error("查询公司客户详情异常：", e);
		}
		return null;
		
	}
	
	@Override
	public QueryBaseBatchResult<CustomerBaseInfo> list(CustomerQueryOrder queryOrder) {
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
	
	@Override
	public FcsBaseResult delete(Long userId) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			CompanyCustomerInfo companyCustomerInfo = queryByUserId(userId, null);
			if (companyCustomerInfo != null) {
				
				ProjectQueryOrder projectQueryOrder = new ProjectQueryOrder();
				projectQueryOrder.setCustomerId(userId);
				QueryBaseBatchResult<ProjectInfo> project = projectServiceClient
					.queryProject(projectQueryOrder);
				if (project.isSuccess()) {
					if (ListUtil.isNotEmpty(project.getPageList())) {
						result.setSuccess(false);
						result.setMessage("该客户有关联项目，不允许删除！");
					} else {
						String customerId = companyCustomerInfo.getCustomerId();
						customerBaseInfoDAO.deleteByUserId(userId);
						customerCompanyDetailDAO.deleteByCustomerId(customerId);
						companyQualificationDAO.deleteByCustomerId(customerId);
						companyOwnershipStructureDAO.deleteByCustomerId(customerId);
						result.setSuccess(true);
						result.setMessage("删除公司客户成功！");
					}
				} else {
					result.setSuccess(false);
					result.setMessage("查询关联项目失败，请稍后在试！");
				}
				
			} else {
				result.setSuccess(false);
				result.setMessage("未找到该客户！");
			}
		} catch (DataAccessException e) {
			result.setSuccess(false);
			result.setMessage("数据库异常");
			logger.error("删除公司客户异常", e);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知异常");
			logger.error("删除公司客户异常", e);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult deleteQualificationById(long id) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			companyQualificationDAO.deleteById(id);
			result.setSuccess(true);
			result.setMessage("删除公司客户成功！");
			result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
		} catch (DataAccessException e) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("删除公司客户资质证书异常", e);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage("未知异常");
			logger.error("删除公司客户资质证书异常", e);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult deleteOwnershipById(long id) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			companyOwnershipStructureDAO.deleteById(id);
			result.setSuccess(true);
			result.setMessage("删除公司股东成功！");
			result.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
		} catch (DataAccessException e) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("删除公司股东异常", e);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage("未知异常");
			logger.error("删除公司股东异常", e);
		}
		return result;
	}
	
	@Override
	public CompanyCustomerInfo queryByName(String customerName) {
		CompanyCustomerInfo companyCustomerInfo = null;
		try {
			CustomerBaseInfoDO customerBaseInfo = new CustomerBaseInfoDO();
			customerBaseInfo.setCustomerName(customerName);
			List<CustomerBaseInfoDO> list = customerBaseInfoDAO.findWithCondition(customerBaseInfo,
				0, 100, null, null, null, null, null, null, 0, null, null);
			if (ListUtil.isNotEmpty(list)) {
				companyCustomerInfo = new CompanyCustomerInfo();
				CustomerBaseInfoDO customerBaseInfoDO = list.get(0);
				CustomerCompanyDetailDO customerCompanyDetailDO = customerCompanyDetailDAO
					.findByCustomerId(customerBaseInfoDO.getCustomerId());
				BeanCopier.staticCopy(customerBaseInfoDO, companyCustomerInfo);
				BeanCopier.staticCopy(customerCompanyDetailDO, companyCustomerInfo);
				List<CompanyQualificationInfo> companyQualification = companyQualificationService
					.list(customerBaseInfoDO.getCustomerId());
				List<CompanyOwnershipStructureInfo> companyOwnershipStructure = companyOwnershipStructureService
					.list(customerBaseInfoDO.getCustomerId());
				companyCustomerInfo.setCompanyOwnershipStructure(companyOwnershipStructure);
				companyCustomerInfo.setCompanyQualification(companyQualification);
			}
			return companyCustomerInfo;
			
		} catch (Exception e) {
			logger.error("查询公司客户详情异常：", e);
		}
		return null;
		
	}
	
	@Override
	public CompanyCustomerInfo queryByBusiLicenseNo(String busiLicenseNo) {
		CompanyCustomerInfo companyCustomerInfo = null;
		try {
			
			CustomerBaseInfoDO customerBaseInfo = new CustomerBaseInfoDO();
			customerBaseInfo.setBusiLicenseNo(busiLicenseNo);
			List<CustomerBaseInfoDO> list = customerBaseInfoDAO.findWithCondition(customerBaseInfo,
				0, 100, null, null, null, null, null, null, 0, null, null);
			if (ListUtil.isNotEmpty(list)) {
				companyCustomerInfo = new CompanyCustomerInfo();
				CustomerBaseInfoDO customerBaseInfoDO = list.get(0);
				CustomerCompanyDetailDO customerCompanyDetailDO = customerCompanyDetailDAO
					.findByCustomerId(customerBaseInfoDO.getCustomerId());
				BeanCopier.staticCopy(customerBaseInfoDO, companyCustomerInfo);
				BeanCopier.staticCopy(customerCompanyDetailDO, companyCustomerInfo);
				List<CompanyQualificationInfo> companyQualification = companyQualificationService
					.list(customerBaseInfoDO.getCustomerId());
				List<CompanyOwnershipStructureInfo> companyOwnershipStructure = companyOwnershipStructureService
					.list(customerBaseInfoDO.getCustomerId());
				companyCustomerInfo.setCompanyOwnershipStructure(companyOwnershipStructure);
				companyCustomerInfo.setCompanyQualification(companyQualification);
			}
			return companyCustomerInfo;
			
		} catch (Exception e) {
			logger.error("查询公司客户详情异常：", e);
		}
		return null;
		
	}
	
	@Override
	public ValidateCustomerResult ValidateCustomer(String busiLicenseNo, String customerName,
													Long inputPersonId) {
		ValidateCustomerResult result = new ValidateCustomerResult();
		CustomerQueryOrder queryOrder = new CustomerQueryOrder();
		queryOrder.setBusiLicenseNo(busiLicenseNo);
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
		} else {//2.历史间接客户
			if (StringUtil.isNotBlank(customerName)) {
				IndirectCustomerQueryOrder order = new IndirectCustomerQueryOrder();
				order.setIndirectCustomerName(customerName);
				QueryBaseBatchResult<IndirectCustomerInfo> dirictResult = crmUseServiceClient
					.queryIndirectCustomer(order);
				if (dirictResult.isSuccess() && ListUtil.isNotEmpty(dirictResult.getPageList())) {
					
					Map<String, IndirectCustomerInfo> map = new HashMap<>();
					for (IndirectCustomerInfo info : dirictResult.getPageList()) {
						if (map.containsKey(info.getProjectCode())) {
							String rols = map.get(info.getProjectCode()).getCustomerRole();
							if (rols.indexOf(info.getCustomerRole()) > -1) {
								continue;
							} else {
								map.get(info.getProjectCode()).setCustomerRole(
									rols + "," + info.getCustomerRole());
							}
						} else {
							ProjectInfo project = projectServiceClient.queryByCode(
								info.getProjectCode(), false);
							info.setProjectName(project.getProjectName());
							map.put(info.getProjectCode(), info);
						}
						
					}
					StringBuilder message = new StringBuilder();
					message.append("该客户曾参与过的项目:</br>");
					for (IndirectCustomerInfo infos : map.values()) {
						message.append("项目名:" + infos.getProjectName() + ", ");
						message.append("项目中角色:" + infos.getCustomerRole() + ";</br>");
					}
					result.setMessage(message.toString());
					result.setSuccess(false);
					result.setType("perHs");
				} else {
					result.setSuccess(true);
					result.setMessage("客户可用");
				}
				
			} else {
				result.setSuccess(true);
				result.setMessage("客户可用");
			}
		}
		
		return result;
	}
	
}
