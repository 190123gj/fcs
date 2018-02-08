/*package com.bornsoft.integration.risk;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bornsoft.pub.enums.BudgetRelationEnum;
import com.bornsoft.pub.enums.CreditLevelEnum;
import com.bornsoft.pub.enums.RiskTypeEnum;
import com.bornsoft.pub.enums.UserTypeEnum;
import com.bornsoft.pub.order.risk.LoginRiskSystemOrder;
import com.bornsoft.pub.order.risk.QuerySimilarEnterpriseOrder;
import com.bornsoft.pub.order.risk.RiskInfoQueryOrder;
import com.bornsoft.pub.order.risk.SynCustomLevelOrder;
import com.bornsoft.pub.order.risk.SynCustomLevelOrder.CustomLevelInfo;
import com.bornsoft.pub.order.risk.SynOperatorInfoOrder;
import com.bornsoft.pub.order.risk.SynOperatorInfoOrder.OperatorInfo;
import com.bornsoft.pub.order.risk.SynRiskInfoOrder;
import com.bornsoft.pub.order.risk.SynRiskInfoOrder.RiskInfo;
import com.bornsoft.pub.order.risk.SynWatchListOrder;
import com.bornsoft.pub.order.risk.SynWatchListOrder.WatchListInfo;
import com.bornsoft.pub.order.risk.SynsCustomInfoOrder;
import com.bornsoft.pub.order.risk.SynsCustomInfoOrder.BusinessRelationShipInfo;
import com.bornsoft.pub.order.risk.SynsCustomInfoOrder.CreditStatusInfo;
import com.bornsoft.pub.order.risk.SynsCustomInfoOrder.DebtInfo;
import com.bornsoft.pub.order.risk.SynsCustomInfoOrder.GuaranteeInfo;
import com.bornsoft.pub.order.risk.VerifyOrganizationOrder;
import com.bornsoft.pub.result.risk.QueryRiskInfoResult;
import com.bornsoft.pub.result.risk.QuerySimilarEnterpriseResult;
import com.bornsoft.pub.result.risk.VerifyOrganizationResult;
import com.bornsoft.utils.base.BornOutOrderBase;
import com.bornsoft.utils.base.BornRedirectResult;
import com.bornsoft.utils.base.BornSynResultBase;
import com.bornsoft.utils.config.AppConstantsUtil;
import com.bornsoft.utils.config.SystemConfig;
import com.bornsoft.utils.tool.DateUtils;
import com.bornsoft.utils.tool.SingleOrderUtil;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.money.Money;

public class Test {
	
	private static RiskSystemDistanceClient  risk = new RiskSystemDistanceClient();
	static {
		try {
			SystemConfig staticConfig = new SystemConfig();
			Method field = AppConstantsUtil.class.getDeclaredMethod("init", SystemConfig.class);
			field.setAccessible(true);
			field.invoke(new AppConstantsUtil(), staticConfig);
			staticConfig.setRiskSecurityKey("d5da48767d8c49f3834324a1607c226c");
			staticConfig.setRiskMerchantCode("10864182364");
			staticConfig.setRiskSystemUrl("http://101.200.182.178/business/{serviceName}.json");
			field.setAccessible(false);
			System.out.println("加载配置：" + staticConfig);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	
	
	@org.junit.Test
	public void synWatchList(){
		SynWatchListOrder order = new SynWatchListOrder();
		createOrder(order);
		
		List<WatchListInfo> list = new ArrayList<>();
		WatchListInfo info = new WatchListInfo();
		info.setCustomName("王晓文");
		info.setLicenseNo("140522199301026836");
		info.setOperator("wangyufeng77");
		info.setUserType(UserTypeEnum.PERSONAL);
		list.add(info);
		order.setList(list);
		System.out.println(risk.execute(order, BornSynResultBase.class));;
	}
	
	@org.junit.Test
	public void synCustomLevel(){
		SynCustomLevelOrder order = new SynCustomLevelOrder();
		createOrder(order);
		
		List<CustomLevelInfo> list = new ArrayList<>();
		CustomLevelInfo info = new CustomLevelInfo();
		info.setCreditLevel(CreditLevelEnum.A2);
		info.setCustomName("王晓文");
		info.setLicenseNo("dfdfdf");
		list.add(info);
		order.setList(list);
		order.setOrderNo(SingleOrderUtil.getInstance().createOrderNo());
		System.out.println(risk.execute(order, BornSynResultBase.class));;
	}
	
	@org.junit.Test
	public void synRiskInfo(){
		SynRiskInfoOrder order = new SynRiskInfoOrder();
		createOrder(order);
		
		List<RiskInfo> list = new ArrayList<>();
		RiskInfo info = new RiskInfo();
		info.setCreditAmount(new Money(10));
		info.setCreditStartTime(DateUtil.getFormat(DateUtil.dtSimple).format(new Date()));
		info.setCreditEndTime(DateUtil.getFormat(DateUtil.dtSimple).format(new Date()));
		info.setCustomName("王晓文");
		info.setLicenseNo("140522199301026836");
		info.setRiskType(RiskTypeEnum.OVERDUE);
		list.add(info);
		order.setList(list );
		System.out.println(risk.execute(order, BornSynResultBase.class));
	}
	
	@org.junit.Test
	public void synOperatorInfo(){
		SynOperatorInfoOrder order = new SynOperatorInfoOrder();
		createOrder(order);
		
		List<OperatorInfo> list = new ArrayList<>();
		OperatorInfo o = new OperatorInfo();
		o.setEmail("taibai@yiji.com");
		o.setMobile("18523114706");
		o.setOperator("wangyufeng3");
		list.add(o);
		order.setList(list );
		System.out.println(risk.execute(order, BornSynResultBase.class));;
	}

	
	@org.junit.Test
	public void  synCustomerInfo(){
		SynsCustomInfoOrder order = new SynsCustomInfoOrder();
		createOrder(order);
		
		order.setCustomName("客户名称");
		order.setLicenseNo("1234567894");
		//经营关系
		List<BusinessRelationShipInfo> businessRelationship = new ArrayList<>();
		BusinessRelationShipInfo relation = new BusinessRelationShipInfo();
		relation.setBudgetRelation(BudgetRelationEnum.UP);
		relation.setEnterpriseName("DFDFD");
		relation.setTradeAmount(new Money("100"));
		businessRelationship.add(relation);
		order.setBusinessRelationship(businessRelationship);
		//信用状态
		List<CreditStatusInfo> creditStatus = new ArrayList<>();
		CreditStatusInfo info = new CreditStatusInfo();
		info.setConsideration(new Money("12323"));
		info.setEndTime(DateUtils.getCurrentDate());
		info.setFinanceAmount(new Money("111"));
		info.setFinanceCost(new Money("111"));
		info.setGuaranteeWay("1232");
		info.setOrganization("2323");
		info.setStartTime(DateUtils.getCurrentDate());
		info.setVouchee("1212");
		creditStatus.add(info);
		order.setCreditStatus(creditStatus);
		
		//负债状况
		List<DebtInfo> debtInfoList = new ArrayList<>();
		DebtInfo debtInfo = new DebtInfo();
		debtInfo.setBank("美联储");
		debtInfo.setDebtType("XXXX");
		debtInfo.setEndTime(DateUtils.getCurrentDate());
		debtInfo.setLoanAmount(new Money(12));
		debtInfo.setStartTime(DateUtils.getCurrentDate());
		debtInfoList.add(debtInfo);
		order.setDebtInfo(debtInfoList);
		
		//对外担保信息
		List<GuaranteeInfo> guaranteeInfoList = new ArrayList<>();
		GuaranteeInfo guaranteeInfo = new GuaranteeInfo();
		guaranteeInfo.setConsideration(new Money(12));
		guaranteeInfo.setExpiryDate(DateUtil.getFormat(DateUtil.dtSimple).format(new Date()));
		guaranteeInfo.setGuaranteeAmount(new Money(12));
		guaranteeInfo.setGuaranteeWay("DFD");
		guaranteeInfo.setVouchee("WYF");
		guaranteeInfoList.add(guaranteeInfo);
		order.setGuaranteeInfo(guaranteeInfoList);
		
		System.out.println(risk.execute(order, BornSynResultBase.class));;
	}
	
	@org.junit.Test
	public void  verifyOrganizationInfo(){
		VerifyOrganizationOrder order = new VerifyOrganizationOrder();
		createOrder(order);
		order.setOrderNo("3160819162715122");
		order.setCustomName("北京市热水锅炉厂一分厂");
		order.setLicenseNo("91110112802471314D");
		order.setUserType(UserTypeEnum.BUSINESS);
		System.out.println(risk.execute(order, VerifyOrganizationResult.class));;
	}
	
	@org.junit.Test
	public void loginRiskSystem(){
		LoginRiskSystemOrder order = new LoginRiskSystemOrder();
		createOrder(order);
		order.setOperator("wangyufeng");
		System.out.println(risk.execute(order, BornRedirectResult.class));
	}
	
	@org.junit.Test
	public void queryRiskInfoOrder(){
		RiskInfoQueryOrder order = new RiskInfoQueryOrder();
		createOrder(order);
		order.setCustomName("北京市热水锅炉厂一分厂");
		order.setLicenseNo("91110112802471314D");
		System.out.println(risk.execute(order, QueryRiskInfoResult.class));
	}
	
	@org.junit.Test
	public void querySimilarEnterprise(){
		QuerySimilarEnterpriseOrder order = new QuerySimilarEnterpriseOrder();
		createOrder(order);
		order.setCustomName("北京市热水锅炉厂一分厂");
		order.setLicenseNo("91110112802471314D");
		
		System.out.println(risk.execute(order, QuerySimilarEnterpriseResult.class));
	}
	
	private static void createOrder(BornOutOrderBase order) {
		order.setOrderNo(SingleOrderUtil.getInstance().createOrderNo());
		order.setPartnerId(AppConstantsUtil.getRiskMerchantCode());
	}

}
*/