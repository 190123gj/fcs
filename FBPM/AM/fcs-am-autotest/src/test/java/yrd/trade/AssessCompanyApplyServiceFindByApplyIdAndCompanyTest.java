/**
 * 
 */
package yrd.trade;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import testbase.ServiceAutoTestBase;

import com.autotest.annotation.AutoTestCase;
import com.autotest.runner.AutoTestJUnit4ClassRunner;
import com.born.fcs.am.ws.info.assesscompany.AssetsAssessCompanyInfo;
import com.bornsoft.utils.enums.CommonResultEnum;

/**
 * @author xiaoqiu.peng
 * 
 */
@RunWith(AutoTestJUnit4ClassRunner.class)
public class AssessCompanyApplyServiceFindByApplyIdAndCompanyTest extends ServiceAutoTestBase {
	// 测试方向：
	// 1001、评估公司抽取
	@Test
	@AutoTestCase(
			file = "yrd/bornfinance/assessCompanyApplyServiceFindByApplyIdAndCompanyTestSuccess.csv",
			description = "评估公司抽取-成功用例")
	public void AssessCompanyApplyServiceFindByApplyIdAndCompanyTestSuccess(int testId,
																			CommonResultEnum resultCode) {
		
		// 清除数据
		
		// 准备数据(addLoanDemand.sql执行)
		//		Date systemDate = new Date();
		//		tradeExpireTime = systemDate;// 交易到期日期
		//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//		String formatString = "yyyy-MM-dd HH:mm:ss";
		//		// 通过日历获取下一天日期(可投资时间大于当前日期小于截止日期)
		//		Calendar cal = Calendar.getInstance();
		//		cal.setTime(tradeExpireTime);
		//		cal.add(Calendar.DAY_OF_YEAR, +1);
		//		tradeExpireTime = DateUtil.string2Date(sf.format(cal.getTime()), formatString);
		
		//		partnerId
		//		loanUserId
		//		guaranteeUser
		//		sponsorUser
		//		guaranteePayeeUser
		
		long id = 196;
		String cityName = "长沙市";
		String provinceName = "湖南省";
		
		//用来保存筛选符合要求的评估公司
		List<AssetsAssessCompanyInfo> listCompanyForRequired = null;
		// 调用接口
		listCompanyForRequired = assessCompanyApplyService.assessCompanyeExtract(id, cityName,
			provinceName);
		// 结果验证
		//assertEquals(true, result.isSuccess());
		// 检查提示信息是否与期望一致
		//assertEquals(result.getResultCode(), resultCode);
		
		// 数据验证
		
		// 清除数据
		
	}
	
	// 测试方向：
	// 1001 
	// 1006.Order为空
	@Test
	@AutoTestCase(file = "yrd/bornfinance/p2pCoreTradeCommonFacadeCreateTradeTestFailOne.csv",
			description = "发布借款需求-请求参数不完整")
	public void p2pCoreTradeCommonFacadeCreateTradeTestFailOne() {
	}
	
	// 测试方向：
	// 1001 
	@Test
	@AutoTestCase(file = "yrd/bornfinance/p2pCoreTradeCommonFacadeCreateTradeTestFailTwo.csv",
			description = "发布借款需求-成功和银行卡信息已存在用例")
	public void p2pCoreTradeCommonFacadeCreateTradeTestFailTwo() {
	}
}
