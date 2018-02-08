package com.born.fcs.crm.biz.service.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.crm.dal.daointerface.CustomerRelationDAO;
import com.born.fcs.crm.dal.daointerface.CustomerRelationListDAO;
import com.born.fcs.crm.dal.dataobject.CustomerBaseInfoDO;
import com.born.fcs.crm.dal.dataobject.CustomerCompanyDetailDO;
import com.born.fcs.crm.dal.dataobject.CustomerPersonDetailDO;
import com.born.fcs.crm.dal.dataobject.CustomerRelationDO;
import com.born.fcs.crm.dal.dataobject.CustomerRelationListDO;
import com.born.fcs.crm.integration.bpm.BpmUserQueryService;
import com.born.fcs.crm.ws.service.ChangeSaveService;
import com.born.fcs.crm.ws.service.CompanyOwnershipStructureService;
import com.born.fcs.crm.ws.service.CompanyQualificationService;
import com.born.fcs.crm.ws.service.CustomerService;
import com.born.fcs.crm.ws.service.PersonalCompanyService;
import com.born.fcs.crm.ws.service.enums.ChangeTypeEnum;
import com.born.fcs.crm.ws.service.info.CompanyOwnershipStructureInfo;
import com.born.fcs.crm.ws.service.info.CompanyQualificationInfo;
import com.born.fcs.crm.ws.service.info.CustomerBaseInfo;
import com.born.fcs.crm.ws.service.info.CustomerDetailInfo;
import com.born.fcs.crm.ws.service.info.PersonalCompanyInfo;
import com.born.fcs.crm.ws.service.order.ChangeListOrder;
import com.born.fcs.crm.ws.service.order.CustomerDetailOrder;
import com.born.fcs.crm.ws.service.order.PersonalCompanyOrder;
import com.born.fcs.crm.ws.service.order.ResotrePublicOrder;
import com.born.fcs.crm.ws.service.order.TransferAllocationOrder;
import com.born.fcs.crm.ws.service.order.query.CustomerQueryOrder;
import com.born.fcs.crm.ws.service.order.query.PersonalCompanyQueryOrder;
import com.born.fcs.crm.ws.service.result.ChangeResult;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;

@Service("customerService")
public class CustomerServiceImpl extends BaseAutowiredDAO implements
		CustomerService {

	@Autowired
	private CompanyOwnershipStructureService companyOwnershipStructureService;
	@Autowired
	private CompanyQualificationService companyQualificationService;

	@Autowired
	private PersonalCompanyService personalCompanyService;

	@Autowired
	private ChangeSaveService changeSaveService;
	@Autowired
	private CustomerRelationListDAO customerRelationListDAO;

	@Autowired
	private CustomerRelationDAO customerRelationDAO;

	@Autowired
	private BpmUserQueryService bpmUserQueryService;

	@Override
	public QueryBaseBatchResult<CustomerBaseInfo> list(
			CustomerQueryOrder queryOrder) {
		QueryBaseBatchResult<CustomerBaseInfo> result = new QueryBaseBatchResult<CustomerBaseInfo>();
		CustomerRelationListDO customerBaseInfo = new CustomerRelationListDO();
		BeanCopier.staticCopy(queryOrder, customerBaseInfo);
		long count = customerRelationListDAO.countWithCondition(
				customerBaseInfo, queryOrder.getCustomerLevelList(),
				queryOrder.getIndustryCodeList(), queryOrder.getCityCodeList(),
				queryOrder.getBeginDate(), queryOrder.getEndDate(),
				queryOrder.getLikeCustomerName(), queryOrder.getLoginUserId(),
				queryOrder.getDeptIdList(), queryOrder.getLikeNameOrId(),
				queryOrder.getMobileQuery(), queryOrder.getLikeCertNo(),
				queryOrder.getLikeBusiLicenseNo(),
				queryOrder.getIncludePublic());
		PageComponent component = new PageComponent(queryOrder, count);
		List<CustomerRelationListDO> list = customerRelationListDAO
				.findWithCondition(customerBaseInfo,
						component.getFirstRecord(), component.getPageSize(),
						queryOrder.getCustomerLevelList(),
						queryOrder.getIndustryCodeList(),
						queryOrder.getCityCodeList(),
						queryOrder.getBeginDate(), queryOrder.getEndDate(),
						queryOrder.getLikeCustomerName(),
						queryOrder.getLoginUserId(),
						queryOrder.getDeptIdList(),
						queryOrder.getLikeNameOrId(),
						queryOrder.getMobileQuery(),
						queryOrder.getLikeCertNo(),
						queryOrder.getLikeBusiLicenseNo(),
						queryOrder.getIncludePublic());
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
	public CustomerDetailInfo queryByUserId(Long userId) {
		try {
			CustomerDetailInfo customerDetailInfo = new CustomerDetailInfo();
			CustomerBaseInfoDO customerBaseInfoDO = customerBaseInfoDAO
					.findByUserId(userId);
			if (customerBaseInfoDO != null) {
				BeanCopier.staticCopy(customerBaseInfoDO, customerDetailInfo);
				String customerId = customerBaseInfoDO.getCustomerId();

				if (customerDetailInfo.isPersional()) {
					// 个人客户
					CustomerPersonDetailDO customerPersonDetailDO = customerPersonDetailDAO
							.findByCustomerId(customerBaseInfoDO
									.getCustomerId());
					BeanCopier.staticCopy(customerPersonDetailDO,
							customerDetailInfo);
					PersonalCompanyQueryOrder queryOrder = new PersonalCompanyQueryOrder();
					queryOrder.setCustomerId(customerId);
					queryOrder.setPageSize(50);
					QueryBaseBatchResult<PersonalCompanyInfo> perList = personalCompanyService
							.list(queryOrder);
					customerDetailInfo.setReqList(perList.getPageList());
				} else {
					// 企业客户
					CustomerCompanyDetailDO customerCompanyDetailDO = customerCompanyDetailDAO
							.findByCustomerId(customerId);

					BeanCopier.staticCopy(customerCompanyDetailDO,
							customerDetailInfo);
					List<CompanyQualificationInfo> companyQualification = companyQualificationService
							.list(customerId);
					List<CompanyOwnershipStructureInfo> companyOwnershipStructure = companyOwnershipStructureService
							.list(customerId);
					customerDetailInfo
							.setCompanyOwnershipStructure(companyOwnershipStructure);
					customerDetailInfo
							.setCompanyQualification(companyQualification);
					customerDetailInfo.setLastLevel(lastEvalueLevel(userId));
				}
				
				List<CustomerRelationDO> relations = customerRelationDAO
						.findByUserId(userId);
				if (ListUtil.isNotEmpty(relations)) {
					List<Long> managerIds = Lists.newArrayList();
					for (CustomerRelationDO relation : relations) {
						if (relation.getCustomerManagerId() > 0)
							managerIds.add(relation.getCustomerManagerId());
					}
					customerDetailInfo.setCustomerManagerIds(managerIds);
				}
				return customerDetailInfo;
			}

		} catch (Exception e) {
			logger.error("查询公司客户详情异常：", e);
		}
		return null;

	}

	@Override
	public FcsBaseResult updateByUserId(CustomerDetailOrder customerDetailOrder) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			customerDetailOrder.detailCheck();
			ChangeResult chage = null;
			if (customerDetailOrder.needSaveChange()) {
				chage = isChange(customerDetailOrder);
			}
			CustomerDetailInfo customerDetailInfo = queryByUserId(customerDetailOrder
					.getUserId());

			CustomerBaseInfoDO customerBaseInfoDO = new CustomerBaseInfoDO();
			BeanCopier.staticCopy(customerDetailInfo, customerBaseInfoDO);
			BeanCopier.staticCopy(customerDetailOrder, customerBaseInfoDO);
			customerBaseInfoDO.setCustomerType(customerDetailInfo
					.getCustomerType());
			if (StringUtil.isNotBlank(customerBaseInfoDO.getCustomerManager())) {
				customerBaseInfoDO.setIsDistribution(BooleanEnum.IS.code());
			}
			customerBaseInfoDAO.updateByCustomerId(customerBaseInfoDO);
			String customerId = customerDetailInfo.getCustomerId();
			if (customerDetailInfo.isPersional()) {
				// 个人更新
				CustomerPersonDetailDO customerPersonDetailDO = new CustomerPersonDetailDO();
				BeanCopier.staticCopy(customerDetailOrder,
						customerPersonDetailDO);
				customerPersonDetailDAO
						.updateByCustomerId(customerPersonDetailDO);
				personalCompanyService.updateByList(
						customerDetailOrder.getReqList(), customerId);
			} else {
				// 企业更新
				CustomerCompanyDetailDO customerCompanyDetailDO = new CustomerCompanyDetailDO();
				BeanCopier.staticCopy(customerDetailOrder,
						customerCompanyDetailDO);
				customerCompanyDetailDAO
						.updateByCustomerId(customerCompanyDetailDO);
				companyQualificationService.updateByList(
						customerDetailOrder.getCompanyQualification(),
						customerId);
				companyOwnershipStructureService.updateByList(
						customerDetailOrder.getCompanyOwnershipStructure(),
						customerId);
			}
			// 保存修改记录
			if (customerDetailOrder.needSaveChange()) {
				ChangeListOrder changeListOrder = new ChangeListOrder();
				BeanCopier.staticCopy(customerDetailOrder, changeListOrder);
				changeListOrder.setCustomerUserId(customerDetailOrder
						.getUserId());
				changeListOrder.setChageDetailList(chage.getChangeOrder());
				changeListOrder.setChangeType(customerDetailOrder
						.getChangeType());
				changeSaveService.save(changeListOrder);
			}
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
			result.setMessage("未知异常");
			logger.error("更新公司客户异常", e);
		}
		return result;
	}

	@Override
	public FcsBaseResult add(CustomerDetailOrder customerDetailOrder) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			customerDetailOrder.newCheck();
			String customerId = BusinessNumberUtil.gainNumber();
			customerDetailOrder.setCustomerId(customerId);
			CustomerBaseInfoDO customerBaseInfo = new CustomerBaseInfoDO();
			Date rawAddTime = sqlDateService.getSqlDate();
			BeanCopier.staticCopy(customerDetailOrder, customerBaseInfo);
			customerBaseInfo.setRawAddTime(rawAddTime);

			long userId = customerBaseInfoDAO.insert(customerBaseInfo);

			if (CustomerTypeEnum.ENTERPRISE.code().equals(
					customerDetailOrder.getCustomerType())) {
				// 企业客户
				CustomerCompanyDetailDO customerCompanyDetailDO = new CustomerCompanyDetailDO();
				BeanCopier.staticCopy(customerDetailOrder,
						customerCompanyDetailDO);
				customerCompanyDetailDO.setRawAddTime(rawAddTime);

				customerCompanyDetailDAO.insert(customerCompanyDetailDO);
				companyQualificationService.updateByList(
						customerDetailOrder.getCompanyQualification(),
						customerId);
				companyOwnershipStructureService.updateByList(
						customerDetailOrder.getCompanyOwnershipStructure(),
						customerId);
			} else {
				// 个人客户
				CustomerPersonDetailDO customerPersonDetail = new CustomerPersonDetailDO();
				BeanCopier
						.staticCopy(customerDetailOrder, customerPersonDetail);
				customerPersonDetail.setRawAddTime(rawAddTime);
				customerPersonDetailDAO.insert(customerPersonDetail);
				personalCompanyService.updateByList(
						customerDetailOrder.getReqList(), customerId);
			}

			if ((customerDetailOrder.getDirectorId() > 0 || customerDetailOrder
					.getCustomerManagerId() > 0)
					&& StringUtil.isNotBlank(customerDetailOrder.getDepPath())) {
				// 总监或客户经理添加客户
				TransferAllocationOrder transferAllocationOrder = new TransferAllocationOrder();
				BeanCopier.staticCopy(customerDetailOrder,
						transferAllocationOrder);
				transferAllocationOrder.setUserId(userId);
				transferAllocation(transferAllocationOrder);
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
			logger.error("新增公司客户异常", e);
		} catch (Exception e) {
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage(e.getMessage());
			logger.error("新增公司客户异常", e);
		}
		return result;
	}

	@Override
	public FcsBaseResult delete(Long userId) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			CustomerBaseInfoDO customerInfo = customerBaseInfoDAO
					.findByUserId(userId);
			// CustomerDetailInfo customerInfo = queryByUserId(userId);
			if (customerInfo != null) {
				ProjectQueryOrder projectQueryOrder = new ProjectQueryOrder();
				projectQueryOrder.setCustomerId(userId);
				QueryBaseBatchResult<ProjectInfo> project = projectServiceClient
						.queryProject(projectQueryOrder);
				if (project.isSuccess()) {
					if (ListUtil.isNotEmpty(project.getPageList())) {
						result.setSuccess(false);
						result.setMessage("该客户有关联项目，不允许删除！");
					} else {
						String customerId = customerInfo.getCustomerId();

						customerBaseInfoDAO.deleteByUserId(userId);
						if (CustomerTypeEnum.ENTERPRISE.code().equals(
								customerInfo.getCustomerType())) {
							customerCompanyDetailDAO
									.deleteByCustomerId(customerId);
							companyQualificationDAO
									.deleteByCustomerId(customerId);
							companyOwnershipStructureDAO
									.deleteByCustomerId(customerId);
						} else {
							customerPersonDetailDAO
									.deleteByCustomerId(customerId);
							personalCompanyDAO.deleteByCustomerId(customerId);
						}
						customerRelationDAO.deleteByUserId(userId);
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
			logger.error("删除客户异常", e);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知异常");
			logger.error("删除客户异常", e);
		}
		return result;
	}

	@Override
	public FcsBaseResult setChangeStatus(Long userId, BooleanEnum status) {
		FcsBaseResult result = new FcsBaseResult();
		if (status != null) {
			CustomerBaseInfoDO customerBaseInfoDO = customerBaseInfoDAO
					.findByUserId(userId);
			if (customerBaseInfoDO != null
					&& StringUtil.notEquals(
							customerBaseInfoDO.getChangeStatus(),
							status.getCode())) {
				customerBaseInfoDO.setChangeStatus(status.getCode());
				customerBaseInfoDAO.updateByUserId(customerBaseInfoDO);
			}
			result.setSuccess(true);
			result.setMessage("更新状态成功");
		} else {
			result.setSuccess(false);
			result.setMessage("更新状态不能为空");
		}

		return result;
	}

	private List<PersonalCompanyOrder> personalComInfo2Order(
			List<PersonalCompanyInfo> list) {
		List<PersonalCompanyOrder> rsList = null;
		if (ListUtil.isNotEmpty(list)) {
			PersonalCompanyOrder order = null;
			rsList = new ArrayList<>();
			for (PersonalCompanyInfo info : list) {
				order = new PersonalCompanyOrder();
				BeanCopier.staticCopy(info, order);
				rsList.add(order);
			}
		}
		return rsList;

	}

	@Override
	public ChangeResult isChange(CustomerDetailOrder customerDetailOrder) {
		CustomerDetailOrder oldOrder = new CustomerDetailOrder();
		CustomerDetailInfo oldInfo = queryByUserId(customerDetailOrder
				.getUserId());
		BeanCopier.staticCopy(oldInfo, oldOrder);
		oldOrder.setCompanyQualification(oldInfo.getCompanyQualification());
		oldOrder.setCompanyOwnershipStructure(oldInfo
				.getCompanyOwnershipStructure());
		oldOrder.setReqList(personalComInfo2Order(oldInfo.getReqList()));
		if (ListUtil.isNotEmpty(customerDetailOrder
				.getCompanyOwnershipStructure())) {
			List<CompanyOwnershipStructureInfo> list = new ArrayList<>();
			for (CompanyOwnershipStructureInfo info : customerDetailOrder
					.getCompanyOwnershipStructure()) {
				info.setAmountString(null);
				if (allValueIsNull(info)
						|| StringUtil.isBlank(info.getShareholdersName()))
					continue;
				list.add(info);
			}
			customerDetailOrder.setCompanyOwnershipStructure(list);
		}
		if (customerDetailOrder.getChangeType() == ChangeTypeEnum.LX) {
			if (ListUtil.isNotEmpty(oldOrder.getCompanyOwnershipStructure())) {
				for (CompanyOwnershipStructureInfo info : oldOrder
						.getCompanyOwnershipStructure()) {
					info.setAmountString(null);
					info.setCustomerId(null);
					info.setId(null);
				}
			}
		}
		if (ListUtil.isNotEmpty(customerDetailOrder.getReqList())) {
			List<PersonalCompanyOrder> list = new ArrayList<>();
			for (PersonalCompanyOrder info : customerDetailOrder.getReqList()) {
				if (StringUtil.isNotBlank(info.getPerRegistAmountString())) {
					info.setPerRegistAmount(Money.amout(info
							.getPerRegistAmountString()));

				}
				if (StringUtil.isNotBlank(info.getPerRegistDateString())) {
					info.setPerRegistDate(DateUtil.parse(info
							.getPerRegistDateString()));

				}
				info.setPerRegistAmountString(null);
				info.setPerRegistDateString(null);
				if (allValueIsNull(info)
						|| StringUtil.isBlank(info.getPerCompany()))
					continue;
				list.add(info);
			}
			customerDetailOrder.setReqList(list);
		}
		if (ListUtil.isNotEmpty(customerDetailOrder.getCompanyQualification())) {
			List<CompanyQualificationInfo> list = new ArrayList<>();
			for (CompanyQualificationInfo info : customerDetailOrder
					.getCompanyQualification()) {
				if (allValueIsNull(info)
						|| StringUtil.isBlank(info.getQualificationName()))
					continue;
				list.add(info);
			}
			customerDetailOrder.setCompanyQualification(list);
		}
		if (StringUtil.equals(customerDetailOrder.getCustomerType(),
				CustomerTypeEnum.PERSIONAL.code())) {
			// 个人客户无此字段
			customerDetailOrder.setContactNo(null);
		}
		ChangeResult cahnge = changeSaveService.createChangeOrder(
				customerDetailOrder, oldOrder, CustomerDetailOrder.changeMap(),
				true);
		return cahnge;
	}

	@Override
	@SuppressWarnings("rawtypes")
	@Autowired
	public FcsBaseResult statisticsCustomerDept() {
		FcsBaseResult result = new FcsBaseResult();
		try {
			Map data = extraDAO.statisticsCustomerDept();
			if (data != null) {
				result.setMessage(JSONObject.toJSONString(data));
			} else {
				result.setMessage(new JSONObject().toJSONString());
			}
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("按部门统计客户出错");
			result.setSuccess(false);
			result.setMessage("按部门统计客户出错");
		}
		return result;
	}

	@Override
	@SuppressWarnings("rawtypes")
	@Autowired
	public FcsBaseResult statisticsCustomerRegion() {
		FcsBaseResult result = new FcsBaseResult();
		try {
			Map data = extraDAO.statisticsCustomerRegion();
			if (data != null) {
				result.setMessage(JSONObject.toJSONString(data));
			} else {
				result.setMessage(new JSONObject().toJSONString());
			}
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("按地域统计客户出错");
			result.setSuccess(false);
			result.setMessage("按地域统计客户出错");
		}
		return result;
	}

	@Override
	public FcsBaseResult transferAllocation(
			TransferAllocationOrder transferAllocationOrder) {
		FcsBaseResult result = new FcsBaseResult();
		logger.info("客户移交分配入参transferAllocationOrder={}",
				transferAllocationOrder);
		try {
			// transferAllocationOrder.check();
			CustomerRelationDO customerRelation = new CustomerRelationDO();
			BeanCopier.staticCopy(transferAllocationOrder, customerRelation);

			CustomerBaseInfoDO customer = customerBaseInfoDAO
					.findByUserId(transferAllocationOrder.getUserId());

			if (customer == null) {
				result.setSuccess(false);
				result.setMessage("客户不存在");
				return result;
			} else {

				if (transferAllocationOrder.getRelationId() > 0) {
					// 分配 ：接收者只能是客户经理
					// 移交：接受者只能是客户经理
					long customerManagerId = customerRelation
							.getCustomerManagerId();
					if (customerManagerId > 0) {
						CustomerRelationDO countCondition = new CustomerRelationDO();
						countCondition.setUserId(customerRelation.getUserId());
						countCondition.setCustomerManagerId(customerManagerId);
						long count = customerRelationDAO
								.countWithCondition(countCondition);
						if (count > 0) {
							if ("FP".equals(transferAllocationOrder.getType())
									|| customerManagerId == customer
											.getCustomerManagerId()) {
								result.setSuccess(false);
								result.setMessage("选择的客户经理已经拥有该客户");
								return result;
							}
						} else {
							// 原信息
							CustomerRelationDO info = customerRelationDAO
									.findById(transferAllocationOrder
											.getRelationId());
							if ("FP".equals(transferAllocationOrder.getType())) {
								if (info.getCustomerManagerId() > 0) {
									// 多次分配 ，新生成一条数据
									customerRelation.setRelationId(0);
								}
							}
							if (customerRelation.getRelationId() > 0) {
								customerRelationDAO.updateId(customerRelation);
							} else {
								countCondition = new CustomerRelationDO();
								countCondition.setUserId(customerRelation
										.getUserId());
								countCondition
										.setCustomerManagerId(customerManagerId);
								count = customerRelationDAO
										.countWithCondition(countCondition);
								if (count == 0)
									customerRelationDAO
											.insert(customerRelation);
							}
							result.setSuccess(true);
							result.setMessage("操作成功");
						}

					} else {
						result.setSuccess(false);
						result.setMessage("客户经理不能为空");
						return result;
					}
				} else {
					customerRelation.setRawAddTime(sqlDateService.getSqlDate());
					// 分配 接收者为 总监 或 客户经理
					if (customerRelation.getDirectorId() > 0
							|| customerRelation.getCustomerManagerId() > 0) {

						CustomerRelationDO countCondition = new CustomerRelationDO();
						countCondition.setUserId(customerRelation.getUserId());

						if (customerRelation.getCustomerManagerId() > 0) {
							countCondition
									.setCustomerManagerId(customerRelation
											.getCustomerManagerId());
						} else {
							countCondition.setDirectorId(customerRelation
									.getDirectorId());
						}
						long count = customerRelationDAO
								.countWithCondition(customerRelation);

						if (count == 0) {
							customerRelationDAO.insert(customerRelation);
							result.setSuccess(true);
							result.setMessage("操作成功");
						} else {
							result.setSuccess(false);
							result.setMessage("已经拥有该客户");
							return result;
						}
					} else {
						result.setSuccess(false);
						result.setMessage("总监和客户经理不能同时为空");
						return result;
					}
				}

				// 移交 或者 基础表中没有客户经理信息
				if (transferAllocationOrder.getCustomerManagerId() > 0
						&& ("YJ".equals(transferAllocationOrder.getType()))
						|| customer.getCustomerManagerId() <= 0) {

					if ("YJ".equals(transferAllocationOrder.getType())) {
						// 删掉原客户经理关系
						customerRelationDAO.deleteByUserAndManagerId(
								customer.getUserId(),
								customer.getCustomerManagerId());
					}

					// 更新基础信息表中客户经理
					UserDetailInfo userDetail = bpmUserQueryService
							.findUserDetailByUserId(transferAllocationOrder
									.getCustomerManagerId());
					if (userDetail != null) {
						customer.setCustomerManagerId(userDetail.getId());
						customer.setCustomerManager(userDetail.getName());
						Org dep = userDetail.getPrimaryOrg();
						if (dep != null) {
							customer.setDepId(dep.getId());
							customer.setDepName(dep.getName());
							customer.setDepPath(dep.getPath());
						}
						customerBaseInfoDAO.updateByUserId(customer);
					}
				}

				// 表示立项独占
				if (transferAllocationOrder.getCustomerManagerId() > 0
						&& transferAllocationOrder.getOperId() == transferAllocationOrder
								.getCustomerManagerId()) {
					customer.setCustomerManagerId(transferAllocationOrder
							.getCustomerManagerId());
					customer.setCustomerManager(transferAllocationOrder
							.getCustomerManager());
					customer.setDepId(transferAllocationOrder.getDepId());
					customer.setDepName(transferAllocationOrder.getDepName());
					customer.setDepPath(transferAllocationOrder.getDepPath());
					customer.setDirectorId(transferAllocationOrder
							.getDirectorId());
					customer.setDirector(transferAllocationOrder.getDirector());
					customer.setIsDistribution("IS");
					customerBaseInfoDAO.updateByUserId(customer);
				}
			}

			result.setSuccess(true);
			result.setMessage("操作成功");

		} catch (IllegalArgumentException e) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(e.getMessage());
			logger.error("更新客户关系异常", e);
		} catch (DataAccessException e) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("更新客户关系异常", e);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage("未知异常");
			logger.error("更新客户关系异常", e);
		}
		logger.info("客户移交分配结束出参result={}", result);

		return result;

	}

	@Override
	public FcsBaseResult resotrePublic(ResotrePublicOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {

			CustomerBaseInfoDO customer = customerBaseInfoDAO
					.findByUserId(order.getUserId());

			if (customer == null) {
				result.setSuccess(false);
				result.setMessage("客户不存在");
				return result;
			}

			customer.setIsDistribution("NO");
			customer.setCustomerManagerId(0);
			customer.setCustomerManager(null);
			customer.setDepId(0);
			customer.setDepName(null);
			customer.setDepPath(null);
			customer.setDirectorId(0);
			customer.setDirector(null);

			// 情况分配信息
			customerBaseInfoDAO.updateByUserId(customer);

			// 删掉客户关系
			customerRelationDAO.deleteByUserId(order.getUserId());

			result.setSuccess(true);
			result.setMessage("操作成功");
		} catch (IllegalArgumentException e) {
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(e.getMessage());
			logger.error("还原成公海客户异常", e);
		} catch (DataAccessException e) {
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("数据库异常");
			logger.error("保还原成公海客户异常", e);
		} catch (Exception e) {
			result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage("未知异常");
			logger.error("保还原成公海客户异常", e);
		}

		return result;
	}

	@Override
	public FcsBaseResult allCustomerDept() {
		FcsBaseResult result = new FcsBaseResult();
		try {
			Map data = extraDAO.allCustomerDept();
			if (data != null) {
				result.setMessage(JSONObject.toJSONString(data));
			} else {
				result.setMessage(new JSONObject().toJSONString());
			}
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("按部门统计客户出错");
			result.setSuccess(false);
			result.setMessage("按部门统计客户出错");
		}
		return result;
	}

}
