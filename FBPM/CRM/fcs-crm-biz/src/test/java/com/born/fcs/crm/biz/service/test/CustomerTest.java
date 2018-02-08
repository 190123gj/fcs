package com.born.fcs.crm.biz.service.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.born.fcs.crm.dal.daointerface.CustomerBaseInfoDAO;
import com.born.fcs.crm.dal.daointerface.CustomerRelationDAO;
import com.born.fcs.crm.dal.dataobject.CustomerBaseInfoDO;
import com.born.fcs.crm.dal.dataobject.CustomerRelationDO;
import com.born.fcs.crm.integration.bpm.BpmUserQueryService;
import com.born.fcs.crm.ws.service.CompanyCustomerService;
import com.born.fcs.crm.ws.service.CustomerService;
import com.born.fcs.crm.ws.service.PersonalCustomerService;
import com.born.fcs.crm.ws.service.enums.ChangeTypeEnum;
import com.born.fcs.crm.ws.service.info.CustomerBaseInfo;
import com.born.fcs.crm.ws.service.info.PersonalCompanyInfo;
import com.born.fcs.crm.ws.service.order.PersonalCompanyOrder;
import com.born.fcs.crm.ws.service.order.TransferAllocationOrder;
import com.born.fcs.crm.ws.service.order.UpdateFromCreditOrder;
import com.born.fcs.crm.ws.service.order.query.CustomerQueryOrder;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;

public class CustomerTest extends BaseTest {
	
	@Autowired
	CustomerService customerService;
	@Autowired
	CompanyCustomerService companyCustomerService;
	@Autowired
	CustomerBaseInfoDAO customerBaseInfoDAO;
	
	@Test
	public void test() {
		//		CustomerDetailOrder customerDetailOrder = new CustomerDetailOrder();
		//		CustomerDetailInfo info = customerService.queryByUserId(618L);
		//		BeanCopier.staticCopy(info, customerDetailOrder);
		//		customerDetailOrder.setCompanyOwnershipStructure(info.getCompanyOwnershipStructure());
		//		customerDetailOrder.setReqList(personalComInfo2Order(info.getReqList()));
		//		customerDetailOrder.setMemo("sdds2ssd");
		//		customerDetailOrder.setAddress("sdsd0054ss");
		//		customerDetailOrder.setChangeType(ChangeTypeEnum.QB);
		//		customerDetailOrder.setOperName("模拟签报");
		//		customerDetailOrder.setOperId(00);
		//		customerService.updateByUserId(customerDetailOrder);
		
		FcsBaseResult result = customerService.allCustomerDept();
		logger.info("范德萨范德萨：{}", result);
		
	}
	
	//征信
	@Test
	public void test1() {
		//更新到客户关系系统
		UpdateFromCreditOrder order = new UpdateFromCreditOrder();
		order.setUserId(616L);
		order.setLegalPersion("发送");
		order.setEstablishedTime(new Date());
		order.setRegisterCapital(Money.amout(String.valueOf("10000")));
		order.setBusiScope("455555555");
		order.setAddress("dsfdsd的地方都");
		order.setLoanCardNo("555555555555555");
		order.setOrgCode("22222");
		order.setTaxRegCertificateNo("5sddsd");
		order.setLocalTaxCertNo("5554545454");
		//征信查询客户修改留痕
		order.setOperId(1L);
		order.setOperName("模拟征信");
		order.setChangeType(ChangeTypeEnum.ZX);
		companyCustomerService.updateFromCredit(order);
		
	}
	
	private List<PersonalCompanyOrder> personalComInfo2Order(List<PersonalCompanyInfo> list) {
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
	
	//客户查询加权限
	@Test
	public void queryList() {
		CustomerQueryOrder queryOrder = new CustomerQueryOrder();
		//		queryOrder.setLoginUserId(1); 
		List<Long> deptIdList = new ArrayList<Long>();
		deptIdList.add(20000006810019L);
		queryOrder.setDeptIdList(deptIdList);
		QueryBaseBatchResult<CustomerBaseInfo> list = customerService.list(queryOrder);
		for (CustomerBaseInfo info : list.getPageList()) {
			logger.info("权限查询结果:customer={} ,customerManager={}，depName={}",
				info.getCustomerName(), info.getCustomerManager(), info.getDepName());
		}
	}
	
	//新移交分配列表查询
	@Test
	public void test3() {
		//列表查询
		//		CustomerQueryOrder queryOrder = new CustomerQueryOrder();
		//		queryOrder.setCustomerManagerId(20000006810055L);
		//		QueryBaseBatchResult<CustomerBaseInfo> list = customerService.list(queryOrder);
		//		logger.info("客户列表：{}", list);
		
		//移交分配
		TransferAllocationOrder transferAllocationOrder = new TransferAllocationOrder();
		transferAllocationOrder.setUserId(801);
		transferAllocationOrder.setRelationId(1);
		transferAllocationOrder.setType("FP");
		transferAllocationOrder.setCustomerManagerId(20000006810055L);
		
		FcsBaseResult result = customerService.transferAllocation(transferAllocationOrder);
		logger.info("移交分配：{}", result);
	}
	
	//客户关系清理
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	@Autowired
	CustomerRelationDAO customerRelationDAO;
	
	@Test
	public void test4() {
		CustomerBaseInfoDO customerBaseInfo = new CustomerBaseInfoDO();
		List<CustomerBaseInfoDO> list = customerBaseInfoDAO.findWithCondition(customerBaseInfo, 0,
			1000, null, null, null, null, null, null, 0, null, null);
		UserDetailInfo userDetail = null;
		for (CustomerBaseInfoDO info : list) {
			try {
				if (info.getCustomerManagerId() > 0) {
					userDetail = new UserDetailInfo();
					//有客户经理
					userDetail = bpmUserQueryService.findUserDetailByUserId(info
						.getCustomerManagerId());
					CustomerRelationDO customerRelation = new CustomerRelationDO();
					
					customerRelation.setUserId(info.getUserId());
					
					customerRelation.setOperId(0);
					customerRelation.setOperName("自动处理");
					
					customerRelation.setCustomerManager(userDetail.getName());
					customerRelation.setCustomerManagerId(userDetail.getId());
					customerRelation.setDepId(userDetail.getPrimaryOrg().getId());
					customerRelation.setDepName(userDetail.getPrimaryOrg().getName());
					customerRelation.setDepPath(userDetail.getPrimaryOrg().getPath());
					
					customerRelation.setDirector(info.getDirector());
					customerRelation.setDirectorId(info.getDirectorId());
					
					customerRelationDAO.insert(customerRelation);
					
				} else if (info.getDirectorId() > 0) {
					userDetail = new UserDetailInfo();
					//有总监
					userDetail = bpmUserQueryService.findUserDetailByUserId(info.getDirectorId());
					
					CustomerRelationDO customerRelation = new CustomerRelationDO();
					
					customerRelation.setUserId(info.getUserId());
					
					customerRelation.setOperId(0);
					customerRelation.setOperName("自动处理");
					
					//				customerRelation.setCustomerManager(userDetail.getName());
					//				customerRelation.setCustomerManagerId(userDetail.getId());
					customerRelation.setDepId(userDetail.getPrimaryOrg().getId());
					customerRelation.setDepName(userDetail.getPrimaryOrg().getName());
					customerRelation.setDepPath(userDetail.getPrimaryOrg().getPath());
					
					customerRelation.setDirector(userDetail.getName());
					customerRelation.setDirectorId(userDetail.getId());
					
					customerRelationDAO.insert(customerRelation);
					
				}
			} catch (Exception e) {
				logger.error("出现异常：", e);
			}
			
		}
		
	}
	
	@Autowired
	PersonalCustomerService personalCustomerService;
	
	@Test
	public void test5() {
		CustomerQueryOrder queryOrder = new CustomerQueryOrder();
		queryOrder.setLikeNameOrId("s");
		QueryBaseBatchResult<CustomerBaseInfo> list1 = companyCustomerService.list(queryOrder);
		logger.info("企业客户模糊查询{}", list1);
		
		QueryBaseBatchResult<CustomerBaseInfo> list2 = personalCustomerService.list(queryOrder);
		logger.info("个人客户模糊查询{}", list2);
		
	}
}
