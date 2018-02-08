/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 上午10:08:19 创建
 */
package com.born.fcs.fm.biz.service.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.fm.biz.convert.UnBoxingConverter;
import com.born.fcs.fm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.fm.dal.dataobject.CostCategoryDO;
import com.born.fcs.fm.dal.dataobject.FormPaymentFeeDO;
import com.born.fcs.fm.dal.queryCondition.BusiQueryCondition;
import com.born.fcs.fm.dal.queryCondition.CostCategoryQueryCondition;
import com.born.fcs.fm.dataobject.ViewOfficialCardsDO;
import com.born.fcs.fm.integration.pm.service.PmReportServiceClient;
import com.born.fcs.fm.ws.enums.CostCategoryStatusEnum;
import com.born.fcs.fm.ws.enums.CostDirectionEnum;
import com.born.fcs.fm.ws.enums.ForecastChildTypeOneEnum;
import com.born.fcs.fm.ws.enums.ForecastChildTypeTwoEnum;
import com.born.fcs.fm.ws.enums.ForecastTypeEnum;
import com.born.fcs.fm.ws.enums.ReportCompanyEnum;
import com.born.fcs.fm.ws.enums.SubjectAccountTypeEnum;
import com.born.fcs.fm.ws.enums.SubjectCostTypeEnum;
import com.born.fcs.fm.ws.info.bank.BankMessageInfo;
import com.born.fcs.fm.ws.info.forecast.AccountAmountDetailInfo;
import com.born.fcs.fm.ws.info.payment.CostCategoryInfo;
import com.born.fcs.fm.ws.info.payment.OfficialCardInfo;
import com.born.fcs.fm.ws.info.report.AccountTypeBankMessageInfo;
import com.born.fcs.fm.ws.info.report.DeptAccountTypeBankMessageInfo;
import com.born.fcs.fm.ws.order.bank.BankMessageQueryOrder;
import com.born.fcs.fm.ws.order.forecast.AccountAmountDetailQueryOrder;
import com.born.fcs.fm.ws.order.payment.AccountPayQueryOrder;
import com.born.fcs.fm.ws.order.payment.CostCategoryQueryOrder;
import com.born.fcs.fm.ws.order.payment.OfficialCardQueryOrder;
import com.born.fcs.fm.ws.order.payment.ReportAccountDetailOrder;
import com.born.fcs.fm.ws.order.report.ProjectDepositQueryOrder;
import com.born.fcs.fm.ws.result.report.AccountDetailResult;
import com.born.fcs.fm.ws.result.report.DeptAccountTypeBankMessageResult;
import com.born.fcs.fm.ws.result.report.ProjectFinancialDetailResult;
import com.born.fcs.fm.ws.service.bank.BankMessageService;
import com.born.fcs.fm.ws.service.forecast.ForecastService;
import com.born.fcs.fm.ws.service.payment.CostCategoryService;
import com.born.fcs.fm.ws.service.report.ReportFormAnalysisService;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.FinancialProjectStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.born.fcs.pm.ws.order.common.ProjectOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectService;
import com.born.fcs.pm.ws.service.report.field.DataTypeEnum;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 报表分析
 * 
 * @author hjiajie
 * 
 */
@Service("reportFormAnalysisService")
public class ReportFormAnalysisServiceImpl extends BaseAutowiredDomainService
		implements ReportFormAnalysisService {
	@Autowired
	private BankMessageService bankMessageService;

	@Autowired
	private PmReportServiceClient pmReportServiceClient;

	@Value("${pm.database.title}")
	private String dataPmTitle;

	@Value("${fm.database.title}")
	private String dataFmTitle;

	@Autowired
	private ForecastService forecastService;
	@Autowired
	private FinancialProjectService financialProjectWebService;

	@Autowired
	private CostCategoryService costCategoryService;

	/**
	 * 
	 * @see com.born.fcs.fm.ws.service.report.ReportFormAnalysisService#usedAccountDetail()
	 */
	@Override
	public DeptAccountTypeBankMessageResult usedAccountDetail() {
		DeptAccountTypeBankMessageResult result = new DeptAccountTypeBankMessageResult();

		// 1.查询母公司，子公司名字，并排序
		// 2.寻找母公司活期，形成info,
		// 3.寻找母公司理财，形成info,
		// 4.寻找母公司存出保证金，形成info,
		// 5.寻找母公司委托贷款，形成info,
		// 6.寻找母公司资金占用额，形成info,
		// 7.将母公司所有info放入list，再以母公司名为key，放入map<母公司名，Info所组成的list>，定义为主map
		// 8.循环子公司，寻找子公司活期，形成info，放入子公司list
		// 9.寻找子公司理财，形成info，放入子公司list
		// 10.将子公司list以子公司名为key放入主map
		// 11.返回主map，作为结果集
		// other.info中应该放入公司名，方便嗮选，页面上可以使用keyset循环，再getkey获取list，循环list抓后续内容
		// other.每个info都包含一个资金类型和一个子info的list。子info的list最后一项为小计 .
		// other.统计完成之后应该在母公司名位置放入 合计,或者合计单独抓取，因为页面不相同

		// 除去北分，信惠，spc，都是母公司
		long XHid = 0;
		List<Long> SPCids = new ArrayList<Long>();
		long BEIJINGid = 0;
		Org org = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.XINHUI
				.getDeptCode());
		if (org != null) {
			XHid = org.getId();
		}
		// org =
		// bpmUserQueryService.findDeptByCode(ReportCompanyEnum.SPC.getDeptCode());
		// if (org != null) {
		// SPCid = org.getId();
		// }
		Long CBid = getOrgId(ReportCompanyEnum.XHTZ_CB);
		if (CBid != null) {
			SPCids.add(CBid);
		}
		Long CXid = getOrgId(ReportCompanyEnum.XHTZ_CX);
		if (CXid != null) {
			SPCids.add(CXid);
		}
		Long CRid = getOrgId(ReportCompanyEnum.XHTZ_CR);
		if (CRid != null) {
			SPCids.add(CRid);
		}
		Long CZid = getOrgId(ReportCompanyEnum.XHTZ_CZ);
		if (CZid != null) {
			SPCids.add(CZid);
		}
		Long CYid = getOrgId(ReportCompanyEnum.XHTZ_CY);
		if (CYid != null) {
			SPCids.add(CYid);
		}
		org = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.BEIJING
				.getDeptCode());
		if (org != null) {
			BEIJINGid = org.getId();
		}

		List<DeptAccountTypeBankMessageInfo> itemList = new ArrayList<DeptAccountTypeBankMessageInfo>();
		// title
		DeptAccountTypeBankMessageInfo titleInfo = new DeptAccountTypeBankMessageInfo();
		titleInfo.setCompany("公司");
		BankMessageInfo titleBank = new BankMessageInfo();
		titleBank.setBankName("开户银行");
		titleBank.setAccountNo("银行帐号");
		titleInfo.setCount(1);
		Date date = getSysdate();
		titleBank.setShowAmount(new SimpleDateFormat("yyyy年MM月").format(date)
				+ "（元）");
		List<BankMessageInfo> bankMessages = new ArrayList<BankMessageInfo>();
		bankMessages.add(titleBank);
		AccountTypeBankMessageInfo accountTypeBank = new AccountTypeBankMessageInfo();
		accountTypeBank.setAccountType("资金类型");
		accountTypeBank.setBankMessages(bankMessages);
		accountTypeBank.setCount(1);
		List<AccountTypeBankMessageInfo> accountTypeInfo = new ArrayList<AccountTypeBankMessageInfo>();
		accountTypeInfo.add(accountTypeBank);
		titleInfo.setAccountBanks(accountTypeInfo);
		// 母公司
		DeptAccountTypeBankMessageInfo motherInfo = new DeptAccountTypeBankMessageInfo();
		motherInfo.setCompany("母公司");
		List<AccountTypeBankMessageInfo> motherAccountBanks = new ArrayList<AccountTypeBankMessageInfo>();
		// 母公司活期
		AccountTypeBankMessageInfo motherCurrentInfo = new AccountTypeBankMessageInfo();
		motherCurrentInfo.setAccountType(SubjectAccountTypeEnum.CURRENT
				.message());
		motherCurrentInfo.setBankMessages(new ArrayList<BankMessageInfo>());
		// 母公司理财

		// SELECT
		// p.dept_id,
		// p.dept_code,
		// p.dept_name,
		// SUM(p.actual_price * p.actual_buy_num) '购买金额',
		// SUM(p.actual_price * p.original_hold_num) '持有金额'
		// FROM project_financial p
		// WHERE p.status = 'PURCHASED'
		// GROUP BY p.dept_id

		HashMap<String, FcsField> fieldMap = new HashMap<String, FcsField>();
		fieldMap.put("dept_id", new FcsField("dept_id", "部门id", null,
				DataTypeEnum.STRING));
		fieldMap.put("dept_code", new FcsField("dept_code", "部门code", null,
				DataTypeEnum.STRING));
		fieldMap.put("dept_name", new FcsField("dept_name", "部门名", null,
				DataTypeEnum.STRING));
		fieldMap.put("add_amount", new FcsField("add_amount", "购买金额", null,
				DataTypeEnum.MONEY));
		fieldMap.put("has_amount", new FcsField("has_amount", "持有金额", null,
				DataTypeEnum.MONEY));
		String sql = "SELECT  p.dept_id dept_id,  p.dept_code dept_code,  p.dept_name dept_name,  "
				+ "SUM(p.actual_price * p.actual_buy_num) 'add_amount',  "
				+ "SUM(p.actual_price * p.original_hold_num) 'has_amount' "
				+ " FROM "
				+ dataPmTitle
				+ "."
				+ " project_financial p "
				+ " WHERE p.status = 'PURCHASED' " + " GROUP BY p.dept_id ";
		List<DataListItem> items = pmReportServiceClient.doQuery(sql, 0, 0,
				fieldMap);
		Money XHhasAmount = Money.zero();
		Money SPChasAmount = Money.zero();
		Money BEIJINGhasAmount = Money.zero();
		Money motherHasAmount = Money.zero();

		// spc和分公司暂无理财
		if (items != null && ListUtil.isNotEmpty(items)) {
			for (DataListItem item : items) {
				HashMap<String, Object> itemMap = item.getMap();
				String dept_id = (String) itemMap.get("dept_id");
				Long deptId = 0L;
				if (StringUtil.isNotBlank(dept_id)) {
					deptId = Long.valueOf(dept_id);
				}
				String deptCode = (String) itemMap.get("dept_code");
				Money hasAmount = (Money) itemMap.get("has_amount");
				if (StringUtil.equals(deptCode,
						ReportCompanyEnum.XINHUI.getDeptCode())) {
					// 信汇
					XHhasAmount.addTo(hasAmount);
				} else if (SPCids.contains(deptId)) {
					// spc
					SPChasAmount.addTo(hasAmount);
				} else if (StringUtil.equals(deptCode,
						ReportCompanyEnum.XINHUI.getDeptCode())) {
					// 北京分公司
					BEIJINGhasAmount.addTo(hasAmount);
				} else {
					// 母公司
					motherHasAmount.addTo(hasAmount);
				}
			}
		}

		AccountTypeBankMessageInfo motherLCInfo = new AccountTypeBankMessageInfo();
		motherLCInfo.setAccountType("理财");
		List<BankMessageInfo> banksLC = new ArrayList<BankMessageInfo>();
		BankMessageInfo bankInfoLC = new BankMessageInfo();
		bankInfoLC.setBankName("--");
		bankInfoLC.setAccountNo("--");
		bankInfoLC.setAmount(motherHasAmount);
		banksLC.add(bankInfoLC);
		motherLCInfo.setBankMessages(banksLC);

		// 存出保证金
		AccountTypeBankMessageInfo motherDPInfo = new AccountTypeBankMessageInfo();
		motherDPInfo.setAccountType("存出保证金");
		// 目测是寻找付款单
		FormPaymentFeeDO formPaymentFee = new FormPaymentFeeDO();
		formPaymentFee.setFeeType(SubjectCostTypeEnum.DEPOSIT_PAID.code());
		List<FormPaymentFeeDO> depositPaids = formPaymentFeeDAO
				.findByCondition(formPaymentFee, 0, 999);
		Money allDP = Money.zero();
		for (FormPaymentFeeDO fp : depositPaids) {
			allDP.addTo(fp.getAmount());
		}
		List<BankMessageInfo> banks = new ArrayList<BankMessageInfo>();
		BankMessageInfo bankInfo = new BankMessageInfo();
		bankInfo.setBankName("--");
		bankInfo.setAccountNo("--");
		bankInfo.setAmount(allDP);
		banks.add(bankInfo);
		motherDPInfo.setBankMessages(banks);
		// 委托贷款
		AccountTypeBankMessageInfo motherCLInfo = new AccountTypeBankMessageInfo();
		motherCLInfo.setAccountType("委托贷款");
		FormPaymentFeeDO formPaymentFee2 = new FormPaymentFeeDO();
		formPaymentFee2.setFeeType(SubjectCostTypeEnum.COMMISSION_LOAN.code());
		List<FormPaymentFeeDO> commissionLoans = formPaymentFeeDAO
				.findByCondition(formPaymentFee2, 0, 999);
		Money allCL = Money.zero();
		for (FormPaymentFeeDO fp : commissionLoans) {
			allCL.addTo(fp.getAmount());
		}
		List<BankMessageInfo> banksCL = new ArrayList<BankMessageInfo>();
		BankMessageInfo bankInfoCL = new BankMessageInfo();
		bankInfoCL.setBankName("--");
		bankInfoCL.setAccountNo("--");
		bankInfoCL.setAmount(allCL);
		banksCL.add(bankInfoCL);
		motherCLInfo.setBankMessages(banksCL);
		// TODO 资金占用额
		Money amountZY = Money.zero();
		Calendar calStart = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();
		calStart.setTime(date);
		calEnd.setTime(date);
		calStart.set(Calendar.DAY_OF_MONTH, 1);
		calEnd.set(Calendar.DAY_OF_MONTH,
				calEnd.getActualMaximum(Calendar.DAY_OF_MONTH));
		AccountAmountDetailQueryOrder queryOrder = new AccountAmountDetailQueryOrder();
		queryOrder.setForecastTimeStart(calStart.getTime());
		queryOrder.setForecastTimeEnd(calEnd.getTime());
		queryOrder.setPageNumber(1);
		queryOrder.setPageSize(40);
		QueryBaseBatchResult<AccountAmountDetailInfo> batchAccountResult = forecastService
				.queryAccountAmountDetail(queryOrder);
		if (batchAccountResult != null && batchAccountResult.isSuccess()) {
			for (AccountAmountDetailInfo detail : batchAccountResult
					.getPageList()) {
				amountZY.addTo(detail.getForecastOutAmount());
			}
		}

		AccountTypeBankMessageInfo motherZYInfo = new AccountTypeBankMessageInfo();
		motherZYInfo.setAccountType("资金占用额");
		List<BankMessageInfo> banksZY = new ArrayList<BankMessageInfo>();
		BankMessageInfo bankInfoZY = new BankMessageInfo();
		bankInfoZY.setBankName("--");
		bankInfoZY.setAccountNo("--");
		bankInfoZY.setAmount(amountZY);
		banksZY.add(bankInfoZY);
		motherZYInfo.setBankMessages(banksZY);

		// 资金流入（出）预测 -----出入预测表中本月流出的总计

		DeptAccountTypeBankMessageInfo XHInfo = new DeptAccountTypeBankMessageInfo();
		XHInfo.setCompany("信惠公司");
		List<AccountTypeBankMessageInfo> XHAccountBanks = new ArrayList<AccountTypeBankMessageInfo>();
		// 信惠公司活期
		AccountTypeBankMessageInfo XHCurrentInfo = new AccountTypeBankMessageInfo();
		XHCurrentInfo.setAccountType(SubjectAccountTypeEnum.CURRENT.message());
		XHCurrentInfo.setBankMessages(new ArrayList<BankMessageInfo>());
		// 信惠公司理财
		AccountTypeBankMessageInfo XHLCInfo = new AccountTypeBankMessageInfo();
		XHLCInfo.setAccountType("理财");
		List<BankMessageInfo> banksXH = new ArrayList<BankMessageInfo>();
		BankMessageInfo bankInfoXH = new BankMessageInfo();
		bankInfoXH.setBankName("--");
		bankInfoXH.setAccountNo("--");
		bankInfoXH.setAmount(XHhasAmount);
		banksXH.add(bankInfoXH);
		XHLCInfo.setBankMessages(banksXH);

		DeptAccountTypeBankMessageInfo BFInfo = new DeptAccountTypeBankMessageInfo();
		BFInfo.setCompany("北分公司");
		List<AccountTypeBankMessageInfo> BFAccountBanks = new ArrayList<AccountTypeBankMessageInfo>();
		// 北分公司活期
		AccountTypeBankMessageInfo BFCurrentInfo = new AccountTypeBankMessageInfo();
		BFCurrentInfo.setAccountType(SubjectAccountTypeEnum.CURRENT.message());
		BFCurrentInfo.setBankMessages(new ArrayList<BankMessageInfo>());
		// 北分公司理财
		AccountTypeBankMessageInfo BFLCInfo = new AccountTypeBankMessageInfo();
		BFLCInfo.setAccountType("理财");

		DeptAccountTypeBankMessageInfo SPCInfo = new DeptAccountTypeBankMessageInfo();
		SPCInfo.setCompany("SPC公司");
		List<AccountTypeBankMessageInfo> SPCAccountBanks = new ArrayList<AccountTypeBankMessageInfo>();
		// SPC公司活期
		AccountTypeBankMessageInfo SPCCurrentInfo = new AccountTypeBankMessageInfo();
		SPCCurrentInfo.setAccountType(SubjectAccountTypeEnum.CURRENT.message());
		SPCCurrentInfo.setBankMessages(new ArrayList<BankMessageInfo>());
		// SPC公司理财
		AccountTypeBankMessageInfo SPCLCInfo = new AccountTypeBankMessageInfo();
		SPCLCInfo.setAccountType("理财");

		// List<BankMessageDO> bankDOs = bankMessageDAO.findByCondition(new
		// BankMessageDO(), null, 1,
		// 500, null, null);
		BankMessageQueryOrder bankMessageQueryOrder = new BankMessageQueryOrder();
		bankMessageQueryOrder.setPageSize(999);
		QueryBaseBatchResult<BankMessageInfo> bankInfos = bankMessageService
				.queryBankMessageInfo(bankMessageQueryOrder);
		for (BankMessageInfo bank : bankInfos.getPageList()) {
			// 先判断是哪个公司的
			boolean isXH = false;
			boolean isSPC = false;
			boolean isBEIJING = false;
			if (XHid > 0) {
				if (XHid == bank.getDeptId()) {
					isXH = true;
				}
			} else if ("信惠公司".equals(bank.getDeptName())) {
				isXH = true;
			}

			// if (SPCid > 0) {
			if (ListUtil.isNotEmpty(SPCids)) {
				// if (SPCid == bank.getDeptId()) {
				Long bankId = bank.getDeptId();
				if (SPCids.contains(bankId)) {
					isSPC = true;
				}
			} else if ("北分公司".equals(bank.getDeptName())) {
				isSPC = true;
			}

			if (BEIJINGid > 0) {
				if (BEIJINGid == bank.getDeptId()) {
					isBEIJING = true;
				}
			} else if ("SPC公司".equals(bank.getDeptName())) {
				isBEIJING = true;
			}
			// if ("信惠公司".equals(bank.getDeptName())) {
			if (isXH) {
				if (SubjectAccountTypeEnum.CURRENT == bank.getAccountType()) {
					XHCurrentInfo.getBankMessages().add(bank);
				}
				// } else if ("北分公司".equals(bank.getDeptName())) {
			} else if (isBEIJING) {
				if (SubjectAccountTypeEnum.CURRENT == bank.getAccountType()) {
					BFCurrentInfo.getBankMessages().add(bank);
				}
				// } else if ("SPC公司".equals(bank.getDeptName())) {
			} else if (isSPC) {
				if (SubjectAccountTypeEnum.CURRENT == bank.getAccountType()) {
					SPCCurrentInfo.getBankMessages().add(bank);
				}
			} else {
				// 其余的代表母公司
				if (SubjectAccountTypeEnum.CURRENT == bank.getAccountType()) {
					motherCurrentInfo.getBankMessages().add(bank);
				}
			}
		}
		// 活期
		motherAccountBanks.add(motherCurrentInfo);
		// 理财
		motherAccountBanks.add(motherLCInfo);
		// 存出保证金
		motherAccountBanks.add(motherDPInfo);
		// 委托贷款
		motherAccountBanks.add(motherCLInfo);
		// TODO 资金占用额

		motherAccountBanks.add(motherZYInfo);

		motherInfo.setAccountBanks(motherAccountBanks);
		// 活期
		XHAccountBanks.add(XHCurrentInfo);
		// 理财
		XHAccountBanks.add(XHLCInfo);
		XHInfo.setAccountBanks(XHAccountBanks);
		// 活期
		BFAccountBanks.add(BFCurrentInfo);
		// 理财
		// BFAccountBanks.add(BFLCInfo);
		BFInfo.setAccountBanks(BFAccountBanks);
		// 活期
		SPCAccountBanks.add(SPCCurrentInfo);
		// 理财
		// SPCAccountBanks.add(SPCLCInfo);
		SPCInfo.setAccountBanks(SPCAccountBanks);

		// 金额总和
		Money all = Money.zero();
		// 拼接结果集
		itemList.add(titleInfo);
		// 母公司
		recount(motherInfo, all);
		itemList.add(motherInfo);
		// 信惠
		recount(XHInfo, all);
		itemList.add(XHInfo);
		// 北分
		recount(BFInfo, all);
		itemList.add(BFInfo);

		// spc
		recount(SPCInfo, all);
		itemList.add(SPCInfo);
		// 添加总计

		DeptAccountTypeBankMessageInfo recountInfo = new DeptAccountTypeBankMessageInfo();
		recountInfo.setCompany("总计");
		BankMessageInfo recountBank = new BankMessageInfo();
		recountBank.setBankName("--");
		recountBank.setAccountNo("--");
		recountBank.setAmount(all);
		List<BankMessageInfo> recountMessages = new ArrayList<BankMessageInfo>();
		recountMessages.add(recountBank);
		AccountTypeBankMessageInfo recountTypeBank = new AccountTypeBankMessageInfo();
		// recountTypeBank.setAccountType("资金类型");
		recountTypeBank.setBankMessages(recountMessages);
		recountTypeBank.setCount(1);
		List<AccountTypeBankMessageInfo> recountTypeInfo = new ArrayList<AccountTypeBankMessageInfo>();
		recountTypeInfo.add(recountTypeBank);
		recountInfo.setAccountBanks(recountTypeInfo);
		recountInfo.setCount(1);
		itemList.add(recountInfo);

		result.setSuccess(true);
		result.setList(itemList);
		return result;

	}

	private void recount(DeptAccountTypeBankMessageInfo info, Money all) {

		int allCount = 0;
		for (AccountTypeBankMessageInfo bank : info.getAccountBanks()) {
			// 如果没有数据
			if (ListUtil.isEmpty(bank.getBankMessages())) {
				// 直接添加空白小计
				List<BankMessageInfo> banks = new ArrayList<BankMessageInfo>();
				BankMessageInfo bankInfo = new BankMessageInfo();
				bankInfo.setBankName("小计");
				bankInfo.setAccountNo("--");
				banks.add(bankInfo);
				allCount++;
				bank.setCount(1);
				bank.setBankMessages(banks);
			} else {
				// 有数据就计算小计集合
				Money allAmount = Money.zero();
				int count = 0;
				for (BankMessageInfo bankMessage : bank.getBankMessages()) {
					allAmount.addTo(bankMessage.getAmount());
					count++;
					allCount++;
				}
				BankMessageInfo bankInfo = new BankMessageInfo();
				bankInfo.setBankName("小计");
				bankInfo.setAccountNo("--");
				bankInfo.setAmount(allAmount);
				bank.getBankMessages().add(bankInfo);
				// 小计也算一行，增加数目1
				count++;
				allCount++;
				bank.setCount(count);
				// 累加总额
				all.addTo(allAmount);
			}
		}
		info.setCount(allCount);
	}

	@SuppressWarnings("deprecation")
	@Override
	public QueryBaseBatchResult<OfficialCardInfo> queryOfficialCards(
			OfficialCardQueryOrder order) {
		QueryBaseBatchResult<OfficialCardInfo> batchResult = new QueryBaseBatchResult<OfficialCardInfo>();

		try {
			BusiQueryCondition condition = new BusiQueryCondition();
			if (order != null) {
				BeanCopier.staticCopy(order, condition,
						UnBoxingConverter.getInstance());
			}
			long totalCount = busiDAO.searchViewOfficialCardsCount(condition);
			PageComponent component = new PageComponent(order, totalCount);

			List<ViewOfficialCardsDO> list = busiDAO
					.searchViewOfficialCards(condition);

			List<OfficialCardInfo> pageList = new ArrayList<OfficialCardInfo>(
					list.size());
			for (ViewOfficialCardsDO DO : list) {
				OfficialCardInfo info = new OfficialCardInfo();
				BeanCopier
						.staticCopy(DO, info, UnBoxingConverter.getInstance());
				pageList.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询公务卡报销明细失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}

	@Override
	public AccountDetailResult accountDetail(ReportAccountDetailOrder order) {
		AccountDetailResult result = new AccountDetailResult();
		List<List<String>> lists = new ArrayList<List<String>>();
		// / string,list<string> 构成一个info
		// / list<string> 中有24条数据 基础数据为日期，用于获取index

		// 获取当下
		Date now = getSysdate();
		int num = 24;

		// 获取months
		List<String> allMonths = getList(now, "用于抓去index的months", num, "YYYYMM");

		// 获取行
		List<String> intitle = getList(now, "资金流入项目", num, true);

		List<String> inyjqckyje = getList(now, "预计期初可用金额", num, false);
		List<String> inqtck = getList(now, "其他存款", num, false);
		List<String> inccdbbzj = getList(now, "存出担保保证金", num, false);
		List<String> inwtdk = getList(now, "委托贷款", num, false);
		List<String> indqlc = getList(now, "短期理财", num, false);
		List<String> inzcqlc = getList(now, "中长期理财", num, false);
		List<String> innbzjcj = getList(now, "内部资金拆借", num, false);
		List<String> indcksh = getList(now, "代偿款收回", num, false);
		List<String> inqt = getList(now, "其他", num, false);

		List<String> inzjlrhj = getList(now, "资金流入合计", num, false);

		List<String> outzjzfxm = getList(now, "资金支付项目", num, true);
		List<String> outqtck = getList(now, "其他存款", num, false);
		List<String> outccdbbzj = getList(now, "存出担保保证金", num, false);
		List<String> outwtdk = getList(now, "委托贷款", num, false);
		List<String> outdqlc = getList(now, "短期理财", num, false);
		List<String> outzcqlc = getList(now, "中长期理财", num, false);
		List<String> outnbzjcj = getList(now, "内部资金拆借", num, false);
		List<String> outdcksh = getList(now, "代偿款划出", num, false);
		List<String> outqt = getList(now, "其他", num, false);

		List<String> outjhykhj = getList(now, "计划用款合计", num, false);

		List<String> yjqmky = getList(now, "预计期末可用", num, false);

		// 计算期初可用金额
		AccountAmountDetailQueryOrder queryOrder = new AccountAmountDetailQueryOrder();
		queryOrder.setPageNumber(1);
		queryOrder.setPageSize(1);
		queryOrder.setForecastTimeStart(now);
		queryOrder.setForecastTimeEnd(now);
		QueryBaseBatchResult<AccountAmountDetailInfo> accounts = forecastService
				.queryAccountAmountDetail(queryOrder);
		if (accounts.isSuccess() && ListUtil.isNotEmpty(accounts.getPageList())) {
			inyjqckyje.set(1, accounts.getPageList().get(0)
					.getForecastLastAmount().toString());
		}
		// Calendar c = Calendar.getInstance();
		// c.setTime(now);
		// c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
		// Date monthBeginDay = c.getTime();
		// String monthBeginDayStr = DateUtil.simpleFormat(DateUtil
		// .getStartTimeOfTheDate(monthBeginDay));
		// 出入金明细
		HashMap<String, FcsField> fieldMap = new HashMap<String, FcsField>();
		fieldMap.put("month", new FcsField("month", "月", null,
				DataTypeEnum.STRING));
		fieldMap.put("forecast_type", new FcsField("forecast_type", "类型", null,
				DataTypeEnum.STRING));
		fieldMap.put("forecast_child_type_one", new FcsField(
				"forecast_child_type_one", "子类型一", null, DataTypeEnum.STRING));
		fieldMap.put("forecast_child_type_two", new FcsField(
				"forecast_child_type_two", "子类型二", null, DataTypeEnum.STRING));
		fieldMap.put("amount", new FcsField("amount", "金额", null,
				DataTypeEnum.MONEY));
		String sql = "SELECT DATE_FORMAT(forecast_start_time,'%Y%m') month, forecast_type,forecast_child_type_one,forecast_child_type_two, SUM(amount) as amount FROM "
				+ dataFmTitle
				+ "."
				+ "forecast_account "
				+ " where forecast_start_time >= '"
				+ DateUtil.simpleFormat(DateUtil.getStartTimeOfTheDate(now))
				+ "' ";
		// 添加判定 添加公司条件
		String companySqlStr = getCompanySqlStr(order.getCompany());
		if (StringUtil.isNotBlank(companySqlStr)) {
			sql += " and used_dept_id  " + companySqlStr;
		}
		// 添加sql语句
		sql += " GROUP BY MONTH,forecast_type,forecast_child_type_one,forecast_child_type_two ";
		List<DataListItem> items = pmReportServiceClient.doQuery(sql, 0, 0,
				fieldMap);
		if (items != null && ListUtil.isNotEmpty(items)) {

			for (DataListItem item : items) {
				HashMap<String, Object> itemMap = item.getMap();
				String month = (String) itemMap.get("month");
				String forecast_type = (String) itemMap.get("forecast_type");
				String forecast_child_type_one = (String) itemMap
						.get("forecast_child_type_one");
				String forecast_child_type_two = (String) itemMap
						.get("forecast_child_type_two");
				Money amount = (Money) itemMap.get("amount");
				if (allMonths.contains(month)) {
					// 通过月份获取对应数据
					int monthNum = allMonths.indexOf(month);
					if (monthNum > 0) {
						// 若抓取到月份，判定是何种资金类型，然后设值

						if (ForecastTypeEnum.IN_LCYW.code().equals(
								forecast_type)) {
							// 先拆分理财产品
							if (ForecastChildTypeTwoEnum.CUSTOMIZED_DEPOSIT
									.code().equals(forecast_child_type_two)
									|| ForecastChildTypeTwoEnum.STRUCTURAL_DEPOSIT
											.code().equals(
													forecast_child_type_two)
									|| ForecastChildTypeTwoEnum.PROTOCOL_DEPOSIT
											.code().equals(
													forecast_child_type_two)) {
								// 其他存款 取理财产品的定期存款、协议存款、结构性存款，预计到期日对应的金额取到本表中
								add(monthNum, inqtck, amount, inzjlrhj);
							} else if (ForecastChildTypeOneEnum.SHORT_TERM
									.code().equals(forecast_child_type_one)) {
								// 短期理财 预计到期的，取理财产品中的短期出去定制存款、协议存款、结构性存款以外的本息
								add(monthNum, indqlc, amount, inzjlrhj);
							} else if (ForecastChildTypeOneEnum.LONG_TERM
									.code().equals(forecast_child_type_one)) {
								// 中长期理财 预计到期的，取理财产品中的中长期除去定制存款、协议存款、结构性存款的本息
								add(monthNum, inzcqlc, amount, inzjlrhj);
							} else {
								// 进入未分类其他
								add(monthNum, inqt, amount, inzjlrhj);
							}
						} else if (ForecastTypeEnum.IN_YHRZDB.code().equals(
								forecast_type)
								|| ForecastTypeEnum.IN_ZJRZDB.code().equals(
										forecast_type)
								|| ForecastTypeEnum.IN_JRCPDB.code().equals(
										forecast_type)
								|| ForecastTypeEnum.IN_FRZDBYW.code().equals(
										forecast_type)
								|| ForecastTypeEnum.IN_ZDBYW.code().equals(
										forecast_type)
								|| ForecastTypeEnum.IN_XDYW.code().equals(
										forecast_type)) {
							// 预计到期的保证金本息划回累计
							add(monthNum, inccdbbzj, amount, inzjlrhj);
						} else if (ForecastTypeEnum.IN_WTDKYW.code().equals(
								forecast_type)) {
							// 预计到期的委贷本金收回
							add(monthNum, inwtdk, amount, inzjlrhj);
						} else if (ForecastTypeEnum.IN_INNER_LOAN.code()
								.equals(forecast_type)) {
							// 去内部资金拆解申请单中的还款日期到期的金额
							// add(monthNum, innbzjcj, amount, inzjlrhj);
						} else if (ForecastTypeEnum.IN_DCKSH.code().equals(
								forecast_type)) {
							// 项目代偿后预计三年后时间
							add(monthNum, indcksh, amount, inzjlrhj);
						} else if (forecast_type.startsWith("IN")) {
							// 流入其他
							add(monthNum, inqt, amount, inzjlrhj);
						}
						// /////////////////////////////////////// 以上为流入以下为流出
						else if (ForecastTypeEnum.OUT_LCYW.code().equals(
								forecast_type)) {
							// 先拆分理财产品
							if (ForecastChildTypeTwoEnum.CUSTOMIZED_DEPOSIT
									.code().equals(forecast_child_type_two)
									|| ForecastChildTypeTwoEnum.STRUCTURAL_DEPOSIT
											.code().equals(
													forecast_child_type_two)
									|| ForecastChildTypeTwoEnum.PROTOCOL_DEPOSIT
											.code().equals(
													forecast_child_type_two)) {
								// 其他存款 取理财产品的定期存款、协议存款、结构性存款，预计到期日对应的金额取到本表中
								add(monthNum, outqtck, amount, outjhykhj);
							} else if (ForecastChildTypeOneEnum.SHORT_TERM
									.code().equals(forecast_child_type_one)) {
								// 短期理财 预计到期的，取理财产品中的短期出去定制存款、协议存款、结构性存款以外的本息
								add(monthNum, outdqlc, amount, outjhykhj);
							} else if (ForecastChildTypeOneEnum.LONG_TERM
									.code().equals(forecast_child_type_one)) {
								// 中长期理财 预计到期的，取理财产品中的中长期除去定制存款、协议存款、结构性存款的本息
								add(monthNum, outzcqlc, amount, outjhykhj);
							} else {
								// 进入未分类其他
								add(monthNum, outqt, amount, outjhykhj);
							}
						} else if (ForecastTypeEnum.OUT_YHRZDB.code().equals(
								forecast_type)
								|| ForecastTypeEnum.OUT_ZJRZDB.code().equals(
										forecast_type)
								|| ForecastTypeEnum.OUT_JRCPDB.code().equals(
										forecast_type)
								|| ForecastTypeEnum.OUT_FRZDBYW.code().equals(
										forecast_type)
								|| ForecastTypeEnum.OUT_ZDBYW.code().equals(
										forecast_type)
								|| ForecastTypeEnum.OUT_XDYW.code().equals(
										forecast_type)
								|| ForecastTypeEnum.OUT_QTYW.code().equals(
										forecast_type)) {
							// 预计到期的保证金本息划回累计
							add(monthNum, outccdbbzj, amount, outjhykhj);
						} else if (ForecastTypeEnum.OUT_WTDKYW.code().equals(
								forecast_type)) {
							// 预计到期的委贷本金收回
							add(monthNum, outwtdk, amount, outjhykhj);
						} else if (ForecastTypeEnum.OUT_INNER_LOAN.code()
								.equals(forecast_type)) {
							// 去内部资金拆解申请单中的还款日期到期的金额
							add(monthNum, outnbzjcj, amount, outjhykhj);
						} else if (ForecastTypeEnum.OUT_DCKHC.code().equals(
								forecast_type)) {
							// 项目代偿后预计三年后时间
							add(monthNum, outdcksh, amount, outjhykhj);
						} else if (forecast_type.startsWith("OUT")) {
							// 流入其他
							add(monthNum, outqt, amount, outjhykhj);
						}

					} else {
						// 未抓取到月份代表不是预定月份内的，不计算
						continue;
					}
				} else {
					continue;
				}
			}
		}

		// 20161209 内部资金拆解流入和流出重新计算
		// 用款时间 还款时间
		// 借款单位 流入 流出
		// 债券人 流出 流入

		// 20161115 单独抓取内部借款单流出，按照XX时间抓取
		// 出入金明细
		fieldMap = new HashMap<String, FcsField>();
		fieldMap.put("month", new FcsField("month", "月", null,
				DataTypeEnum.STRING));
		fieldMap.put("amount", new FcsField("amount", "金额", null,
				DataTypeEnum.MONEY));
		String sqlPart1 = "SELECT DATE_FORMAT(l.use_time,'%Y%m') month, SUM(l.loan_amount) AS amount FROM "
				+ dataFmTitle
				+ "."
				+ "form_inner_loan l join "
				+ dataFmTitle
				+ "." + "form f on  f.form_id = l.form_id ";

		String useTimePart2 = " WHERE l.use_time >='";
		String backTimePart2 = " WHERE l.back_time >='";
		String sqlPart3 = DateUtil.simpleFormat(DateUtil
				.getStartTimeOfTheDate(now))
				+ "' and f.status = 'APPROVAL' and l.inner_loan_type='LOAN_AGREEMENT' ";
		// 债权人
		String creditorPart4 = "";
		if (StringUtil.isNotBlank(companySqlStr)) {
			creditorPart4 += " and creditor_id  " + companySqlStr;
		}
		// 借款单位
		String deptPart4 = "";
		if (StringUtil.isNotBlank(companySqlStr)) {
			deptPart4 += " and apply_dept_id  " + companySqlStr;
		}

		String sqlPart5 = " GROUP BY month ";

		// 1. 借款单位+用款时间= 流入
		String innerSql = sqlPart1 + useTimePart2 + sqlPart3 + deptPart4
				+ sqlPart5;
		List<DataListItem> InnerItems = pmReportServiceClient.doQuery(innerSql,
				0, 0, fieldMap);
		if (InnerItems != null && ListUtil.isNotEmpty(InnerItems)) {

			for (DataListItem item : InnerItems) {
				HashMap<String, Object> itemMap = item.getMap();
				String month = (String) itemMap.get("month");
				Money amount = (Money) itemMap.get("amount");
				if (allMonths.contains(month)) {
					// 通过月份获取对应数据
					int monthNum = allMonths.indexOf(month);
					if (monthNum > 0) {
						add(monthNum, innbzjcj, amount, outjhykhj);
					}
				}
			}
		}
		// 2. 借款单位+还款时间= 流出
		innerSql = sqlPart1.replace("use_time", "back_time") + backTimePart2
				+ sqlPart3 + deptPart4 + sqlPart5;
		InnerItems = pmReportServiceClient.doQuery(innerSql, 0, 0, fieldMap);
		if (InnerItems != null && ListUtil.isNotEmpty(InnerItems)) {

			for (DataListItem item : InnerItems) {
				HashMap<String, Object> itemMap = item.getMap();
				String month = (String) itemMap.get("month");
				Money amount = (Money) itemMap.get("amount");
				if (allMonths.contains(month)) {
					// 通过月份获取对应数据
					int monthNum = allMonths.indexOf(month);
					if (monthNum > 0) {
						add(monthNum, outnbzjcj, amount, outjhykhj);
					}
				}
			}
		}
		// 3. 债券单位+用款时间= 流出
		innerSql = sqlPart1 + useTimePart2 + sqlPart3 + creditorPart4
				+ sqlPart5;
		InnerItems = pmReportServiceClient.doQuery(innerSql, 0, 0, fieldMap);
		if (InnerItems != null && ListUtil.isNotEmpty(InnerItems)) {

			for (DataListItem item : InnerItems) {
				HashMap<String, Object> itemMap = item.getMap();
				String month = (String) itemMap.get("month");
				Money amount = (Money) itemMap.get("amount");
				if (allMonths.contains(month)) {
					// 通过月份获取对应数据
					int monthNum = allMonths.indexOf(month);
					if (monthNum > 0) {
						add(monthNum, outnbzjcj, amount, outjhykhj);
					}
				}
			}
		}
		// 4. 债券单位+还款时间= 流入
		innerSql = sqlPart1.replace("use_time", "back_time") + backTimePart2
				+ sqlPart3 + creditorPart4 + sqlPart5;
		InnerItems = pmReportServiceClient.doQuery(innerSql, 0, 0, fieldMap);
		if (InnerItems != null && ListUtil.isNotEmpty(InnerItems)) {

			for (DataListItem item : InnerItems) {
				HashMap<String, Object> itemMap = item.getMap();
				String month = (String) itemMap.get("month");
				Money amount = (Money) itemMap.get("amount");
				if (allMonths.contains(month)) {
					// 通过月份获取对应数据
					int monthNum = allMonths.indexOf(month);
					if (monthNum > 0) {
						add(monthNum, innbzjcj, amount, outjhykhj);
					}
				}
			}
		}

		// 计算 预计期末可用
		for (int i = 1; i <= num; i++) {
			Money start = StringUtil.isBlank(inyjqckyje.get(i)) ? Money.zero()
					: new Money(inyjqckyje.get(i));
			Money in = StringUtil.isBlank(inzjlrhj.get(i)) ? Money.zero()
					: new Money(inzjlrhj.get(i));
			Money out = StringUtil.isBlank(outjhykhj.get(i)) ? Money.zero()
					: new Money(outjhykhj.get(i));
			Money moneyStr = start.add(in).subtract(out);
			yjqmky.set(i, moneyStr.toString());
			if (i + 1 <= num) {
				inyjqckyje.set(i + 1, moneyStr.toString());
			}
		}

		lists.add(intitle);
		lists.add(inyjqckyje);
		lists.add(inqtck);
		lists.add(inccdbbzj);
		lists.add(inwtdk);
		lists.add(indqlc);
		lists.add(inzcqlc);
		lists.add(innbzjcj);
		lists.add(indcksh);
		lists.add(inqt);
		lists.add(inzjlrhj);

		lists.add(outzjzfxm);
		lists.add(outqtck);
		lists.add(outccdbbzj);
		lists.add(outwtdk);
		lists.add(outdqlc);
		lists.add(outzcqlc);
		lists.add(outnbzjcj);
		lists.add(outdcksh);
		lists.add(outqt);
		lists.add(outjhykhj);
		lists.add(yjqmky);

		result.setIntitle(intitle);
		result.setInyjqckyje(inyjqckyje);
		result.setInqtck(inqtck);
		result.setInccdbbzj(inccdbbzj);
		result.setInwtdk(inwtdk);
		result.setIndqlc(indqlc);
		result.setInzcqlc(inzcqlc);
		result.setInnbzjcj(innbzjcj);
		result.setIndcksh(indcksh);
		result.setInqt(inqt);
		result.setInzjlrhj(inzjlrhj);

		result.setOutzjzfxm(outzjzfxm);
		result.setOutqtck(outqtck);
		result.setOutccdbbzj(outccdbbzj);
		result.setOutwtdk(outwtdk);
		result.setOutdqlc(outdqlc);
		result.setOutzcqlc(outzcqlc);
		result.setOutnbzjcj(outnbzjcj);
		result.setOutdcksh(outdcksh);
		result.setOutqt(outqt);
		result.setOutjhykhj(outjhykhj);
		result.setYjqmky(yjqmky);

		// result.setLists(lists);
		result.setRows(num);
		result.setSuccess(true);
		return result;
	}

	private void add(int monthNum, List<String> item, Money amount) {
		String itemVal = item.get(monthNum);
		Money itemAmount = StringUtil.isBlank(itemVal) ? Money.zero()
				: new Money(itemVal);
		itemAmount.addTo(amount);
		item.set(monthNum, itemAmount.toString());
	}

	private void add(int monthNum, List<String> item, Money amount,
			List<String> item2) {
		if (item != null) {
			add(monthNum, item, amount);
		}
		if (item2 != null) {
			add(monthNum, item2, amount);
		}
	}

	@SuppressWarnings("unused")
	private List<String> getList(Date now, String str, int num) {
		return getList(now, str, num, false);
	}

	private List<String> getList(Date now, String str, int num, boolean getTime) {
		List<String> list = new ArrayList<String>();
		list.add(str);
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		for (int i = 0; i < num; i++) {
			// 若需要gettime, 给出 xxxx年xxyue 格式的信息，如果不需要，插入空信息
			if (getTime) {
				String dateStr = new SimpleDateFormat("yyyy年MM月").format(cal
						.getTime());
				list.add(dateStr);
				cal.add(Calendar.MONTH, 1);
			} else {
				list.add("");
			}
		}
		return list;
	}

	private List<String> getList(Date now, String str, int num, String formatStr) {
		List<String> list = new ArrayList<String>();
		list.add(str);
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		for (int i = 0; i < num; i++) {
			// 若需要gettime, 给出 xxxx年xxyue 格式的信息，如果不需要，插入空信息
			if (StringUtil.isNotBlank(formatStr)) {
				String dateStr = new SimpleDateFormat(formatStr).format(cal
						.getTime());
				list.add(dateStr);
				cal.add(Calendar.MONTH, 1);
			} else {
				list.add("");
			}
		}
		return list;
	}

	private String getCompanySqlStr(ReportCompanyEnum companyEnum) {
		if (companyEnum == null
				|| (ReportCompanyEnum.MOTHER != companyEnum && StringUtil
						.isBlank(companyEnum.getDeptCode()))) {
			return "";
		}
		String str = "";
		if (ReportCompanyEnum.MOTHER == companyEnum) {
			// 母公司使用not in 查询所有其他的
			Org org = bpmUserQueryService
					.findDeptByCode(ReportCompanyEnum.XINHUI.getDeptCode());
			if (org != null) {
				// 代表需要插入code
				// 判定是否添加逗号
				if (StringUtil.isNotBlank(str)) {
					str += ",";
				}
				str += String.valueOf(org.getId());
			}
			org = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.SPC
					.getDeptCode());
			if (org != null) {
				// 代表需要插入code
				// 判定是否添加逗号
				if (StringUtil.isNotBlank(str)) {
					str += ",";
				}
				str += String.valueOf(org.getId());
			}
			str = setStr(str, ReportCompanyEnum.BEIJING);
			// 20161206 添加spc

			str = setStr(str, ReportCompanyEnum.XHTZ_CB);

			str = setStr(str, ReportCompanyEnum.XHTZ_CX);

			str = setStr(str, ReportCompanyEnum.XHTZ_CR);

			str = setStr(str, ReportCompanyEnum.XHTZ_CZ);

			str = setStr(str, ReportCompanyEnum.XHTZ_CY);
		} else if (ReportCompanyEnum.XINHUI == companyEnum) {
			Org org = bpmUserQueryService
					.findDeptByCode(ReportCompanyEnum.XINHUI.getDeptCode());
			if (org != null) {
				str = String.valueOf(org.getId());
			}
		} else if (ReportCompanyEnum.SPC == companyEnum) {
			// Org org =
			// bpmUserQueryService.findDeptByCode(ReportCompanyEnum.SPC.getDeptCode());
			// if (org != null) {
			// str = String.valueOf(org.getId());
			// }

			// 20161206 添加spc

			str = setStr(str, ReportCompanyEnum.XHTZ_CB);

			str = setStr(str, ReportCompanyEnum.XHTZ_CX);

			str = setStr(str, ReportCompanyEnum.XHTZ_CR);

			str = setStr(str, ReportCompanyEnum.XHTZ_CZ);

			str = setStr(str, ReportCompanyEnum.XHTZ_CY);
		} else if (ReportCompanyEnum.BEIJING == companyEnum) {
			Org org = bpmUserQueryService
					.findDeptByCode(ReportCompanyEnum.BEIJING.getDeptCode());
			if (org != null) {
				str = String.valueOf(org.getId());
			}
		}

		if (StringUtil.isNotBlank(str)) {
			if (ReportCompanyEnum.MOTHER == companyEnum) {
				// 母公司使用not in
				str = " not in ( " + str + " ) ";
			} else {
				str = "  in ( " + str + " ) ";
			}

		}
		return str;
	}

	private String setStr(String str, ReportCompanyEnum company) {
		Org org;
		org = bpmUserQueryService.findDeptByCode(company.getDeptCode());
		if (org != null) {
			// 代表需要插入code
			// 判定是否添加逗号
			if (StringUtil.isNotBlank(str)) {
				str += ",";
			}
			str += String.valueOf(org.getId());
		}
		return str;
	}

	private Long getOrgId(ReportCompanyEnum company) {
		Org org;
		org = bpmUserQueryService.findDeptByCode(company.getDeptCode());
		if (org != null) {
			return org.getId();
		}
		return null;
	}

	@Override
	public ProjectFinancialDetailResult projectFinancialDetail(
			FinancialProjectQueryOrder order) {
		ProjectFinancialDetailResult result = new ProjectFinancialDetailResult();
		// FinancialProjectQueryOrder order = new FinancialProjectQueryOrder();
		// order.setExpireDateStart(new Date());//显示未到期的项目
		order.setStatus(FinancialProjectStatusEnum.PURCHASED.code());
		order.setPageSize(999);
		List<ProjectFinancialInfo> list = Lists.newArrayList();
		QueryBaseBatchResult<ProjectFinancialInfo> batchResult = financialProjectWebService
				.query(order);
		if (batchResult != null && batchResult.isSuccess()) {
			list = batchResult.getPageList();
		}
		result.setSuccess(true);
		result.setList(list);
		return result;
	}

	@Override
	public ReportDataResult getEntrustedLoanDetail(ProjectOrder order) {
		ReportDataResult result = new ReportDataResult();
		result.getFcsFields().add(new FcsField("project_code", "项目编码", null));

		result.getFcsFields().add(new FcsField("project_name", "项目名称", null));
		// result.getFcsFields().add(new FcsField("customer_name", "客户名称",
		// null));
		result.getFcsFields().add(new FcsField("dept_name", "业务部门", null));
		result.getFcsFields().add(
				new FcsField("busi_manager_name", "客户经理", null));

		result.getFcsFields()
				.add(new FcsField("customer_name", "贷款单位名称", null));
		result.getFcsFields().add(
				new FcsField("capital_channel_name", "委托放款银行", null));
		result.getFcsFields().add(
				new FcsField("actual_loan_time", "发放贷款日期", null));
		result.getFcsFields().add(new FcsField("end_time", "到期日", null));
		result.getFcsFields().add(
				new FcsField("interest_rate", "利率（%/年）", null));
		result.getFcsFields().add(
				new FcsField("actual_amount", "贷款金额（元）", null));

		HashMap<String, FcsField> fieldMap = new HashMap<String, FcsField>();
		List<FcsField> fields = new ArrayList<FcsField>();

		FcsField field = new FcsField("project_code", "项目编码", null,
				DataTypeEnum.STRING);
		fieldMap.put("project_code", field);
		fields.add(field);

		field = new FcsField("project_name", "项目名称", null, DataTypeEnum.STRING);
		fieldMap.put("project_name", field);
		fields.add(field);
		field = new FcsField("customer_name", "贷款单位名称/客户名称", null,
				DataTypeEnum.STRING);
		fieldMap.put("customer_name", field);
		fields.add(field);
		field = new FcsField("dept_name", "业务部门", null, DataTypeEnum.STRING);
		fieldMap.put("dept_name", field);
		fields.add(field);
		field = new FcsField("busi_manager_name", "客户经理", null,
				DataTypeEnum.STRING);
		fieldMap.put("busi_manager_name", field);
		fields.add(field);

		// field = new FcsField("customer_name", "贷款单位名称", null,
		// DataTypeEnum.STRING);
		// fieldMap.put("customer_name", field);
		// fields.add(field);

		field = new FcsField("capital_channel_name", "委托放款银行", null,
				DataTypeEnum.STRING);
		fieldMap.put("capital_channel_name", field);
		fields.add(field);

		field = new FcsField("actual_loan_time", "发放贷款日期", null,
				DataTypeEnum.DATE);
		fieldMap.put("actual_loan_time", field);
		fields.add(field);

		field = new FcsField("end_time", "到期日", null, DataTypeEnum.DATE);
		fieldMap.put("end_time", field);
		fields.add(field);

		field = new FcsField("interest_rate", "利率（%/年）", null,
				DataTypeEnum.BIGDECIMAL);
		fieldMap.put("interest_rate", field);
		fields.add(field);

		field = new FcsField("actual_amount", "贷款金额（元）", null,
				DataTypeEnum.MONEY);
		fieldMap.put("actual_amount", field);
		fields.add(field);

		// field = new FcsField("busi_manager_name", "客户经理", null,
		// DataTypeEnum.STRING);
		// fieldMap.put("busi_manager_name", field);
		// fields.add(field);
		//
		// field = new FcsField("dept_name", "部门", null, DataTypeEnum.STRING);
		// fieldMap.put("dept_name", field);
		// fields.add(field);

		String sql = "SELECT"
				+ "  p.project_code 'project_code', p.project_name 'project_name' ,p.dept_name,p.busi_manager_name ,   "
				+ "  p.customer_name 'customer_name',  "
				+ "  e.capital_channel_name 'capital_channel_name', "
				+ "  r.actual_loan_time 'actual_loan_time', "
				+ "  p.end_time 'end_time', "
				+ "  e.interest_rate 'interest_rate',  "
				+ "  r.actual_amount  'actual_amount' "
				+ " FROM "
				+ dataPmTitle
				+ "."
				+ "project p "
				+ "  JOIN "
				+ dataPmTitle
				+ "."
				+ "f_council_summary_project_entrusted e "
				+ "    ON p.sp_id = e.sp_id "
				+ "  JOIN "
				+ dataPmTitle
				+ "."
				+ "f_loan_use_apply_receipt r "
				+ "    ON p.project_code = r.project_code JOIN "
				+ dataPmTitle
				+ "."
				+ " f_loan_use_apply a ON r.apply_id = a.apply_id  AND a.apply_type IN ('LOAN','BOTH') "
				+ " WHERE  (p.loaned_amount - p.released_amount - p.comp_principal_amount) > 0  ";

		if (StringUtil.isNotBlank(order.getProjectCode())) {
			sql += " and p.project_code = '" + order.getProjectCode() + "'";
		}
		if (StringUtil.isNotBlank(order.getProjectName())) {
			sql += " and p.project_name like '%" + order.getProjectName()
					+ "%'";
		}
		if (StringUtil.isNotBlank(order.getCustomerName())) {
			sql += " and p.customer_name like '%" + order.getCustomerName()
					+ "%'";
		}
		if (order.getDeptId() > 0) {
			sql += " and p.dept_id = '" + order.getDeptId() + "'";
		}
		if (StringUtil.isNotBlank(order.getBusiManagerName())) {
			sql += " and p.busi_manager_name like '%"
					+ order.getBusiManagerName() + "%'";
		}

		sql += " ORDER BY p.approval_time, r.actual_loan_time ";
		// 添加判定 添加公司条件
		List<DataListItem> items = pmReportServiceClient.doQuery(sql, 0, 0,
				fieldMap);

		result.setDataList(items);
		result.setFcsFields(fields);
		result.setSuccess(true);
		return result;
	}

	@Override
	public ReportDataResult getProjectDepositDetail(
			ProjectDepositQueryOrder order) {

		logger.info("开始查询项目保证金明细，order : {}", order);

		ReportDataResult result = new ReportDataResult();
		List<FcsField> fields = new ArrayList<FcsField>();
		fields.add(new FcsField("project_code", "项目编码", null));
		fields.add(new FcsField("project_name", "项目名称", null));
		fields.add(new FcsField("customer_name", "客户名称", null));
		fields.add(new FcsField("busi_type_name", "业务品种", null));
		fields.add(new FcsField("busi_manager_name", "客户经理", null));
		fields.add(new FcsField("dept_name", "部门", null));
		fields.add(new FcsField("pay_amount", "保证金金额(元)", null));
		if (order.isOut()) {
			fields.add(new FcsField("pay_time", "保证金存出时间", null));
		} else {
			fields.add(new FcsField("pay_time", "保证金收取时间", null));
		}

		HashMap<String, FcsField> fieldMap = new HashMap<String, FcsField>();
		fieldMap.put("project_code", new FcsField("project_code", "项目编码", null));
		fieldMap.put("project_name", new FcsField("project_name", "项目名称", null));
		fieldMap.put("customer_name", new FcsField("customer_name", "客户名称",
				null));
		fieldMap.put("busi_type_name", new FcsField("busi_type_name", "业务品种",
				null));
		fieldMap.put("busi_manager_name", new FcsField("busi_manager_name",
				"客户经理", null));
		fieldMap.put("dept_name", new FcsField("dept_name", "部门", null));
		fieldMap.put("pay_amount", new FcsField("pay_amount", "保证金金额(元)", null,
				DataTypeEnum.MONEY));
		if (order.isOut()) {
			fieldMap.put("pay_time", new FcsField("pay_time", "保证金存出时间", null,
					DataTypeEnum.DATE));
		} else {
			fieldMap.put("pay_time", new FcsField("pay_time", "保证金收取时间", null,
					DataTypeEnum.DATE));
		}

		String sql = "SELECT  p.project_code, p.project_name,p.customer_name,"
				+ "p.busi_type_name,p.busi_manager_name,p.dept_name,d.pay_amount, d.pay_time"
				+ " FROM project p JOIN f_finance_affirm a ON p.project_code = a.project_code"
				+ " JOIN f_finance_affirm_detail d  ON a.affirm_id = d.affirm_id AND d.fee_type = '"
				+ (order.isOut() ? "DEPOSIT_PAID" : "GUARANTEE_DEPOSIT") + "' ";

		int conditionCount = 0;
		if (StringUtil.isNotBlank(order.getProjectCode())) {
			sql += (conditionCount == 0 ? "where" : "and")
					+ " p.project_code = '" + order.getProjectCode() + "'";
			conditionCount++;
		}
		if (StringUtil.isNotBlank(order.getProjectName())) {
			sql += (conditionCount == 0 ? "where" : "and")
					+ " p.project_name like '%" + order.getProjectName() + "%'";
			conditionCount++;
		}
		if (StringUtil.isNotBlank(order.getCustomerName())) {
			sql += (conditionCount == 0 ? "where" : "and")
					+ " p.customer_name like '%" + order.getCustomerName()
					+ "%'";
			conditionCount++;
		}
		if (StringUtil.isNotBlank(order.getBusiType())) {
			sql += (conditionCount == 0 ? "where" : "and")
					+ " p.busi_type like '" + order.getBusiType() + "%'";
			conditionCount++;
		}
		if (order.getStartTime() != null) {
			sql += (conditionCount == 0 ? "where" : "and") + " d.pay_time >= '"
					+ DateUtil.simpleFormat(order.getStartTime()) + "'";
			conditionCount++;
		}
		if (order.getEndTime() != null) {
			sql += (conditionCount == 0 ? "where" : "and") + " d.pay_time <= '"
					+ DateUtil.simpleFormat(order.getEndTime()) + "'";
			conditionCount++;
		}
		if (order.getBusiManagerId() != null && order.getBusiManagerId() > 0) {
			sql += (conditionCount == 0 ? "where" : "and")
					+ " p.busi_manager_id = '" + order.getBusiManagerId() + "'";
			conditionCount++;
		}
		if (order.getDeptId() != null && order.getDeptId() > 0) {
			sql += (conditionCount == 0 ? "where" : "and") + " p.dept_id = '"
					+ order.getDeptId() + "'";
			conditionCount++;
		}

		sql += " ORDER BY p.approval_time, p.project_code, d.pay_time";
		List<DataListItem> items = pmReportServiceClient.doQuery(sql, 0, 0,
				fieldMap);

		result.setDataList(items);
		result.setFcsFields(fields);
		result.setSuccess(true);

		return result;
	}

	@Override
	public ReportDataResult getProjectDepositInterest(
			ProjectDepositQueryOrder order) {
		logger.info("开始查询项目保证金明细，order : {}", order);

		ReportDataResult result = new ReportDataResult();
		List<FcsField> fields = new ArrayList<FcsField>();
		fields.add(new FcsField("project_code", "项目编码", null));
		fields.add(new FcsField("project_name", "项目名称", null));
		fields.add(new FcsField("customer_name", "客户名称", null));
		fields.add(new FcsField("busi_type_name", "业务品种", null));
		fields.add(new FcsField("busi_manager_name", "客户经理", null));
		fields.add(new FcsField("dept_name", "部门", null));
		if (order.isOut()) {
			fields.add(new FcsField("pay_time", "保证金存出时间", null));
			fields.add(new FcsField("pay_amount", "保证金存出金额(元)", null));
		} else {
			fields.add(new FcsField("pay_time", "保证金收取时间", null));
			fields.add(new FcsField("pay_amount", "保证金存入金额(元)", null));
		}
		fields.add(new FcsField("left_amount", "保证金剩余金额(元)", null));
		fields.add(new FcsField("margin_rate", "保证金利率(%/年)", null));
		fields.add(new FcsField("period", "保证金期限", null));
		fields.add(new FcsField("accrued_interest", "保证金计提利息(元)", null));
		HashMap<String, FcsField> fieldMap = new HashMap<String, FcsField>();
		fieldMap.put("project_code", new FcsField("project_code", "项目编码", null));
		fieldMap.put("project_name", new FcsField("project_name", "项目名称", null));
		fieldMap.put("customer_name", new FcsField("customer_name", "客户名称",
				null));
		fieldMap.put("busi_type_name", new FcsField("busi_type_name", "业务品种",
				null));
		fieldMap.put("busi_manager_name", new FcsField("busi_manager_name",
				"客户经理", null));
		fieldMap.put("dept_name", new FcsField("dept_name", "部门", null));
		if (order.isOut()) {
			fieldMap.put("pay_time", new FcsField("pay_time", "保证金存出时间", null,
					DataTypeEnum.DATE));
			fieldMap.put("pay_amount", new FcsField("pay_amount", "保证金存出金额(元)",
					null, DataTypeEnum.MONEY));
		} else {
			fieldMap.put("pay_time", new FcsField("pay_time", "保证金收取时间", null,
					DataTypeEnum.DATE));
			fieldMap.put("pay_amount", new FcsField("pay_amount", "保证金出入金额(元)",
					null, DataTypeEnum.MONEY));
		}
		fieldMap.put("left_amount", new FcsField("left_amount", "保证金剩余金额(元)",
				null, DataTypeEnum.MONEY));
		fieldMap.put("margin_rate", new FcsField("margin_rate", "保证金利率(%/年)",
				null, DataTypeEnum.STRING));
		fieldMap.put("period", new FcsField("period", "保证金期限", null,
				DataTypeEnum.STRING));
		fieldMap.put("accrued_interest", new FcsField("accrued_interest",
				"保证金计提利息(元)", null, DataTypeEnum.MONEY));
		String sql = "SELECT  p.project_code, p.project_name,p.customer_name,"
				+ "p.busi_type_name,p.busi_manager_name,p.dept_name,d.pay_time,d.pay_amount,d.margin_rate,"
				+ "CONCAT (d.period,"
				+ "  CASE WHEN d.period_unit='Y'   THEN  '年' "
				+ "WHEN d.period_unit='M'   THEN  '月'  "
				+ "   ELSE  '日' END  ) AS period,d.return_customer_amount as left_amount,IFNULL(d.accrued_interest,0) accrued_interest"
				+ " FROM project p JOIN f_finance_affirm a ON p.project_code = a.project_code"
				+ " JOIN f_finance_affirm_detail d  ON a.affirm_id = d.affirm_id AND d.fee_type = '"
				+ (order.isOut() ? "DEPOSIT_PAID" : "GUARANTEE_DEPOSIT") + "' ";

		int conditionCount = 0;
		if (StringUtil.isNotBlank(order.getProjectCode())) {
			sql += (conditionCount == 0 ? "where" : "and")
					+ " p.project_code = '" + order.getProjectCode() + "'";
			conditionCount++;
		}
		if (StringUtil.isNotBlank(order.getProjectName())) {
			sql += (conditionCount == 0 ? "where" : "and")
					+ " p.project_name like '%" + order.getProjectName() + "%'";
			conditionCount++;
		}
		if (StringUtil.isNotBlank(order.getCustomerName())) {
			sql += (conditionCount == 0 ? "where" : "and")
					+ " p.customer_name like '%" + order.getCustomerName()
					+ "%'";
			conditionCount++;
		}
		if (StringUtil.isNotBlank(order.getBusiType())) {
			sql += (conditionCount == 0 ? "where" : "and")
					+ " p.busi_type like '" + order.getBusiType() + "%'";
			conditionCount++;
		}
		if (order.getStartTime() != null) {
			sql += (conditionCount == 0 ? "where" : "and") + " d.pay_time >= '"
					+ DateUtil.simpleFormat(order.getStartTime()) + "'";
			conditionCount++;
		}
		if (order.getEndTime() != null) {
			sql += (conditionCount == 0 ? "where" : "and") + " d.pay_time <= '"
					+ DateUtil.simpleFormat(order.getEndTime()) + "'";
			conditionCount++;
		}
		if (order.getBusiManagerId() != null && order.getBusiManagerId() > 0) {
			sql += (conditionCount == 0 ? "where" : "and")
					+ " p.busi_manager_id = '" + order.getBusiManagerId() + "'";
			conditionCount++;
		}
		if (order.getDeptId() != null && order.getDeptId() > 0) {
			sql += (conditionCount == 0 ? "where" : "and") + " p.dept_id = '"
					+ order.getDeptId() + "'";
			conditionCount++;
		}

		sql += " ORDER BY p.approval_time, p.project_code, d.pay_time";
		List<DataListItem> items = pmReportServiceClient.doQuery(sql, 0, 0,
				fieldMap);
		result.setDataList(items);
		result.setFcsFields(fields);
		result.setSuccess(true);

		return result;
	}

	@Override
	public ReportDataResult getAccountPay(AccountPayQueryOrder order) {
		ReportDataResult result = new ReportDataResult();
		result.getFcsFields().add(new FcsField("bill_no", "报销单号", null));

		result.getFcsFields().add(new FcsField("reason", "事由", null));
		result.getFcsFields().add(new FcsField("apply_name", "报销人", null));// formUserName
																			// ,agent
		result.getFcsFields().add(new FcsField("dept_name", "部门", null));
		result.getFcsFields().add(new FcsField("payee", "收款人", null));

		result.getFcsFields().add(new FcsField("bank", "开户行", null));
		result.getFcsFields().add(new FcsField("bank_account", "账号", null));
		result.getFcsFields().add(new FcsField("direction", "费用方向", null)); // 差旅费没有
																			// 展示对私
																			// PRIVATE
		result.getFcsFields().add(new FcsField("expense_type", "费用种类", null)); // 差旅费没有
																				// 展示
																				// 差旅费999
		result.getFcsFields().add(new FcsField("amount", "金额（元）", null));
		result.getFcsFields().add(
				new FcsField("application_time", "申请时间", null));
		result.getFcsFields().add(new FcsField("finish_time", "审核通过时间", null));
		result.getFcsFields().add(new FcsField("account_status", "单据状态", null));
		result.getFcsFields().add(
				new FcsField("voucher_sync_finish_time", "过账时间", null));
		// result.getFcsFields().add(new FcsField("raw_add_time", "发起时间",
		// null));
		// result.getFcsFields().add(new FcsField("is_official_card", "是否公务卡",
		// null));

		HashMap<String, FcsField> fieldMap = new HashMap<String, FcsField>();
		List<FcsField> fields = new ArrayList<FcsField>();

		FcsField field = new FcsField("bill_no", "报销单号", null,
				DataTypeEnum.STRING);
		fieldMap.put("bill_no", field);
		fields.add(field);

		field = new FcsField("voucher_no", "凭证号", null, DataTypeEnum.STRING);
		fieldMap.put("voucher_no", field);
		fields.add(field);

		field = new FcsField("reason", "事由", null, DataTypeEnum.STRING);
		fieldMap.put("reason", field);
		fields.add(field);

		field = new FcsField("agent_id", "报销人id", null, DataTypeEnum.STRING);
		fieldMap.put("agent_id", field);

		field = new FcsField("apply_name", "报销人", null, DataTypeEnum.STRING);
		fieldMap.put("apply_name", field);
		fields.add(field);

		field = new FcsField("dept_name", "部门", null, DataTypeEnum.STRING);
		fieldMap.put("dept_name", field);
		fields.add(field);

		field = new FcsField("dept_id", "部门id", null, DataTypeEnum.STRING);
		fieldMap.put("dept_id", field);

		field = new FcsField("payee", "收款人", null, DataTypeEnum.STRING);
		fieldMap.put("payee", field);
		fields.add(field);

		field = new FcsField("bank", "开户行", null, DataTypeEnum.STRING);
		fieldMap.put("bank", field);
		fields.add(field);

		field = new FcsField("bank_account", "账号", null, DataTypeEnum.STRING);
		fieldMap.put("bank_account", field);
		fields.add(field);

		field = new FcsField("direction", "费用方向", null, DataTypeEnum.STRING);
		fieldMap.put("direction", field);
		fields.add(field);

		field = new FcsField("expense_type", "费用种类", null, DataTypeEnum.STRING);
		fieldMap.put("expense_type", field);
		fields.add(field);

		field = new FcsField("amount", "金额（元）", null, DataTypeEnum.MONEY);
		fieldMap.put("amount", field);
		fields.add(field);

		field = new FcsField("application_time", "申请时间", null,
				DataTypeEnum.DATE);
		fieldMap.put("application_time", field);
		fields.add(field);

		field = new FcsField("finish_time", "审核通过时间", null, DataTypeEnum.DATE);
		fieldMap.put("finish_time", field);
		fields.add(field);

		field = new FcsField("account_status", "单据状态", null,
				DataTypeEnum.STRING);
		fieldMap.put("account_status", field);
		fields.add(field);

		field = new FcsField("wait_pay_time", "待打款时间", null, DataTypeEnum.DATE);
		fieldMap.put("wait_pay_time", field);
		fields.add(field);

		field = new FcsField("voucher_sync_finish_time", "过账时间", null,
				DataTypeEnum.DATE);
		fieldMap.put("voucher_sync_finish_time", field);
		fields.add(field);

		field = new FcsField("raw_add_time", "发起时间", null, DataTypeEnum.DATE);
		fieldMap.put("raw_add_time", field);

		field = new FcsField("selectId", "主键", null, DataTypeEnum.STRING);
		fieldMap.put("selectId", field);
		//
		// field = new FcsField("is_official_card", "是否公务卡", null,
		// DataTypeEnum.STRING);
		// fieldMap.put("is_official_card", field);

		// SELECT * FROM (
		// SELECT e.bill_no 'bill_no', e.reimburse_reason 'reason', e.agent
		// 'apply_name' ,e.dept_name 'dept_name',e.payee 'payee' ,
		// e.bank 'bank',e.bank_account 'bank_account',e.direction 'direction'
		// ,d.expense_type 'expense_type',d.amount 'amount',
		// e.account_status 'account_status', e.is_official_card
		// 'is_official_card',d.raw_add_time 'raw_add_time',
		// e.application_time 'application_time' , f.finish_time 'finish_time',
		// e.voucher_sync_finish_time 'voucher_sync_finish_time'
		//
		// FROM
		// form_expense_application_detail d LEFT JOIN form_expense_application
		// e ON d.expense_application_id = e.expense_application_id
		// JOIN form f ON f.form_id = e.form_id
		// WHERE 1= 1 AND f.status != 'DELETED'
		//
		// UNION
		//
		// SELECT e.bill_no 'bill_no', e.reasons 'reason', f.user_name
		// 'apply_name' ,e.dept_name 'dept_name',e.payee 'payee' ,
		// e.bank 'bank',e.bank_account 'bank_account','PRIVATE' AS 'direction'
		// ,'-1' AS 'expense_type',e.amount 'amount',
		// e.account_status 'account_status', e.is_official_card
		// 'is_official_card',e.raw_add_time 'raw_add_time',
		// e.application_time 'application_time' , f.finish_time 'finish_time',
		// e.voucher_sync_finish_time 'voucher_sync_finish_time'
		//
		// FROM form_travel_expense e JOIN form f ON f.form_id = e.form_id
		// WHERE 1= 1 AND f.status != 'DELETED' ) tab
		// ORDER BY tab.raw_add_time DESC

		List<CostCategoryInfo> costCategorys = getCategoryList();
		String repaySql = "";
		if (ListUtil.isNotEmpty(costCategorys)) {
			for (CostCategoryInfo cost : costCategorys) {
				if (StringUtil.equals(cost.getName(), "还款")
						&& cost.getCategoryId() > 0) {
					repaySql = " AND d.expense_type <> " + cost.getCategoryId();
					break;
				}
			}
		}

		String sql = "SELECT * FROM ( "
				+ " SELECT e.bill_no 'bill_no', e.voucher_no 'voucher_no' , e.reimburse_reason 'reason', e.agent_id  'agent_id' , e.agent  'apply_name' ,e.expense_dept_id 'dept_id' , e.dept_name 'dept_name',e.payee 'payee' , "
				+ " e.bank 'bank',e.bank_account 'bank_account',e.direction 'direction' ,d.expense_type 'expense_type',d.amount 'amount', "
				+ " e.account_status 'account_status', e.is_official_card 'is_official_card',d.raw_add_time 'raw_add_time', e.wait_pay_time 'wait_pay_time' ,  "
				+ " e.application_time 'application_time' , f.finish_time 'finish_time', e.voucher_sync_finish_time 'voucher_sync_finish_time' , d.detail_id 'selectId' "
				+ " FROM  "
				+ dataFmTitle
				+ "."
				+ "  form_expense_application_detail d LEFT JOIN "
				+ dataFmTitle
				+ "."
				+ " form_expense_application e ON d.expense_application_id = e.expense_application_id "
				+ "		  JOIN "
				+ dataFmTitle
				+ "."
				+ "  form f ON f.form_id = e.form_id  "
				+ "		   WHERE f.status != 'DELETED' "
				+ repaySql

				+ "		   UNION "

				+ " SELECT e.bill_no 'bill_no', e.voucher_no 'voucher_no' , e.reimburse_reason 'reason', e.agent_id  'agent_id' , e.agent  'apply_name' ,e.expense_dept_id 'dept_id' , e.dept_name 'dept_name',e.payee 'payee' , "
				+ " e.bank 'bank',e.bank_account 'bank_account',e.direction 'direction' ,d.expense_type 'expense_type',d.amount 'amount', "
				+ " e.account_status 'account_status', e.is_official_card 'is_official_card',d.raw_add_time 'raw_add_time', e.wait_pay_time 'wait_pay_time' ,  "
				+ " e.application_time 'application_time' , f.finish_time 'finish_time', e.voucher_sync_finish_time 'voucher_sync_finish_time' , d.detail_id 'selectId' "
				+ " FROM  "
				+ dataFmTitle
				+ "."
				+ "  form_labour_capital_detail d LEFT JOIN "
				+ dataFmTitle
				+ "."
				+ " form_labour_capital e ON d.labour_capital_id = e.labour_capital_id "
				+ "		  JOIN "
				+ dataFmTitle
				+ "."
				+ "  form f ON f.form_id = e.form_id  "
				+ "		   WHERE f.status != 'DELETED' "

				+ "		   UNION "

				+ "		   SELECT e.bill_no 'bill_no', e.voucher_no 'voucher_no' , e.reasons 'reason',  f.user_id  'agent_id' , f.user_name  'apply_name' ,e.expense_dept_id 'dept_id' ,e.dept_name 'dept_name',e.payee 'payee' , "
				+ " e.bank 'bank',e.bank_account 'bank_account','PRIVATE' AS 'direction' ,'-1' AS 'expense_type',e.amount 'amount', "
				+ " e.account_status 'account_status', e.is_official_card 'is_official_card',e.raw_add_time 'raw_add_time', e.wait_pay_time 'wait_pay_time' , "
				+ " e.application_time 'application_time' , f.finish_time 'finish_time', e.voucher_sync_finish_time 'voucher_sync_finish_time' , e.travel_id 'selectId' "
				+ " FROM  "
				+ dataFmTitle
				+ "."
				+ " 	form_travel_expense e JOIN  "
				+ dataFmTitle
				+ "."
				+ "  form f ON f.form_id = e.form_id  "
				+ "		   WHERE f.status != 'DELETED' ) tab  where 1=1 ";
		if (StringUtil.isNotBlank(order.getApplyTimeStart())) {
			sql += " AND tab.raw_add_time >= '" + order.getApplyTimeStart()
					+ "' ";
		}
		if (StringUtil.isNotBlank(order.getApplyTimeEnd())) {
			sql += " AND tab.raw_add_time <= '" + order.getApplyTimeEnd()
					+ " 23:59:59" + "' ";
		}
		if (ListUtil.isNotEmpty(order.getExpenseTypes())) {
			List<String> strs = order.getExpenseTypes();
			sql += " and tab.expense_type IN (";
			for (int i = 0; i < strs.size(); i++) {
				if (i > 0) {
					sql += ",";
				}
				sql += "'";
				sql += strs.get(i);
				sql += "'";
			}
			sql += ") ";
		}
		if (StringUtil.isNotBlank(order.getDirection())) {
			if (CostDirectionEnum.PUBLIC.code().equals(order.getDirection())
					|| CostDirectionEnum.PRIVATE.code().equals(
							order.getDirection())) {
				sql += " AND tab.is_official_card <> 'IS'  ";
				sql += " AND tab.direction = '" + order.getDirection() + "' ";
			} else if ("officialCard".equals(order.getDirection())) {
				sql += " AND tab.is_official_card = 'IS' ";
			}
		}
		if (order.getAccountStatus() != null) {
			sql += " AND tab.account_status = '"
					+ order.getAccountStatus().code() + "' ";
		}
		if (StringUtil.isNotBlank(order.getBillNo())) {
			sql += " AND tab.bill_no = '" + order.getBillNo() + "' ";
		}
		if (order.getAmountStart() != null
				&& order.getAmountStart().greaterThan(Money.zero())) {
			sql += " AND tab.amount >= '" + order.getAmountStart().getCent()
					+ "' ";
		}
		if (order.getAmountEnd() != null
				&& order.getAmountEnd().greaterThan(Money.zero())) {
			sql += " AND tab.amount <= '" + order.getAmountEnd().getCent()
					+ "' ";
		}
		if (StringUtil.isNotBlank(order.getExpenseDeptId())) {
			sql += " AND tab.dept_id = '" + order.getExpenseDeptId() + "' ";
		}
		if (StringUtil.isNotBlank(order.getAgentId())) {
			sql += " AND tab.agent_id = '" + order.getAgentId() + "' ";
		}
		if (StringUtil.isNotBlank(order.getVoucherSyncFinishTimeStart())) {
			sql += " AND tab.voucher_sync_finish_time >= '"
					+ order.getVoucherSyncFinishTimeStart() + "' ";
		}
		if (StringUtil.isNotBlank(order.getVoucherSyncFinishTimeEnd())) {
			sql += " AND tab.voucher_sync_finish_time <= '"
					+ order.getVoucherSyncFinishTimeEnd() + " 23:59:59" + "' ";
		}
		if (StringUtil.isNotBlank(order.getPayee())) {
			sql += " AND tab.payee = '" + order.getPayee() + "' ";
		}
		if (StringUtil.isNotBlank(order.getBankAccount())) {
			sql += " AND tab.bank_account like '%" + order.getBankAccount()
					+ "%' ";
		}
		if (StringUtil.isNotBlank(order.getReason())) {
			sql += " AND tab.reason like '%" + order.getReason() + "%' ";
		}
		if (StringUtil.isNotBlank(order.getVoucherNo())) {
			sql += " AND tab.voucher_no = '" + order.getVoucherNo() + "' ";
		}

		// 20170116 添加排序判定
		if (StringUtil.isNotBlank(order.getSortCol())) {
			if (order.getSortCol().indexOf(",") > 0) {
				String col[] = order.getSortCol().split(",");
				for (int i = 0; i < col.length; i++) {
					if (i == 0) {
						sql += " ORDER BY ";
					}
					sql += "tab." + col[i];
					sql += " ";
					sql += order.getSortOrder();
					sql += " ";
					if (i != col.length - 1) {
						sql += " , ";
					}
				}
			} else {
				sql += " ORDER BY tab.";
				sql += order.getSortCol();
				sql += " ";
				sql += order.getSortOrder();
				sql += " ";
			}
		} else {
			sql += " ORDER BY tab.raw_add_time DESC ";
		}

		// 20170616 8.支付明细表去掉报表期间，最大显示500条
		// 添加判定 添加公司条件
		List<DataListItem> items = pmReportServiceClient.doQuery(sql, 0, 500,
				fieldMap);

		// 20161114 修订为主表为主进行展示
		List<DataListItem> newItems = new ArrayList<DataListItem>();

		// map无法排序，放弃
		Map<String, DataListItem> maps = new TreeMap<String, DataListItem>(
		// new Comparator<String>() {
		// public int compare(String obj1, String obj2) {
		// // 降序排序
		// return Integer.valueOf(obj1) - Integer.valueOf(obj2);
		// // return obj2.compareTo(obj1);
		// }
		// }
		);
		if (items != null && ListUtil.isNotEmpty(items)) {
			for (DataListItem item : items) {
				String billNo = (String) item.getMap().get("bill_no");

				if (maps.get(billNo) != null) {
					// / item信息
					String expenseType = (String) item.getMap().get(
							"expense_type");
					for (CostCategoryInfo cost : costCategorys) {
						if ((cost.getCategoryId() > 0 || cost.getCategoryId() == -1)
								&& String.valueOf(cost.getCategoryId()).equals(
										expenseType)) {
							expenseType = cost.getName();
							break;
						}
					}
					Money mon = (Money) item.getMap().get("amount");
					// map 原信息
					DataListItem mapItem = maps.get(billNo);
					String mapExpenseType = (String) mapItem.getMap().get(
							"expense_type");
					// 计算新值
					Money mapMon = (Money) mapItem.getMap().get("amount");
					mapItem.getMap().put("expense_type",
							mapExpenseType + "," + expenseType);
					mapItem.getMap().put("amount", mapMon.add(mon));
					// 放入map
					maps.put(billNo, mapItem);
				} else {
					// 放入基础值
					maps.put(billNo, item);
				}
			}
		}
		List<Map.Entry<String, DataListItem>> itemList = new ArrayList<Map.Entry<String, DataListItem>>(
				maps.entrySet());
		Collections.sort(itemList,
				new Comparator<Map.Entry<String, DataListItem>>() {
					@Override
					public int compare(Entry<String, DataListItem> o1,
							Entry<String, DataListItem> o2) {
						// Date addTime1 = (Date)
						// o1.getValue().getMap().get("raw_add_time");
						// Date addTime2 = (Date)
						// o2.getValue().getMap().get("raw_add_time");
						// String addtime1Str =
						// DateUtil.dtSimpleFormat(addTime1);
						// String addtime2Str =
						// DateUtil.dtSimpleFormat(addTime2);
						// return Integer.valueOf(addtime1Str) -
						// Integer.valueOf(addtime2Str);
						return ((Date) o2.getValue().getMap()
								.get("raw_add_time")).compareTo(((Date) o1
								.getValue().getMap().get("raw_add_time")));
					}
				});

		// 将maps放入list
		for (Map.Entry<String, DataListItem> mapping : itemList) {
			newItems.add(maps.get(mapping.getKey()));
		}

		result.setDataList(items);
		result.setFcsFields(fields);
		result.setSuccess(true);
		return result;
	}

	private List<CostCategoryInfo> getCategoryList() {
		CostCategoryQueryOrder order2 = new CostCategoryQueryOrder();
		order2.setStatusList(new ArrayList<CostCategoryStatusEnum>());
		order2.getStatusList().add(CostCategoryStatusEnum.NORMAL);
		order2.setPageSize(10000);
		QueryBaseBatchResult<CostCategoryInfo> batchResult = costCategoryService
				.queryPage(order2);
		List<CostCategoryInfo> categoryList = new ArrayList<>();
		if (batchResult != null
				&& ListUtil.isNotEmpty(batchResult.getPageList())) {
			categoryList = batchResult.getPageList();
		}

		// 差率费换算为-1，避免数据库更定之后数据丢失
		boolean hasCLF = false;
		for (CostCategoryInfo info : batchResult.getPageList()) {
			if (info.getName().equals("差旅费")) {
				info.setCategoryId(-1L);
				hasCLF = true;
				break;
			}
		}
		if (!hasCLF) {
			CostCategoryInfo clInfo = new CostCategoryInfo();
			clInfo.setCategoryId(-1L);
			clInfo.setName("差旅费");
			categoryList.add(clInfo);
		}

		return categoryList;
	}
}
