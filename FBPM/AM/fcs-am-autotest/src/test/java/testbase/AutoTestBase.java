/**
 * 
 */
package testbase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;

import com.yjf.common.lang.result.StandardResultInfo;
import com.yjf.common.lang.result.Status;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * @author Peng
 * 
 */
@ContextConfiguration(locations = { "/spring/applicationContext-test.xml" })
public class AutoTestBase {
	/**
	 * 该层为基础校验、打印测试日志和基础通用方法层
	 */
	
	static {
		System.setProperty("spring.profiles.active", "test");
	}
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * @param sleepTime
	 */
	protected void sleepTime(int time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @生成自增序列
	 */
	private static int count = 0;
	
	protected static String indenty() {
		long num = System.currentTimeMillis();
		num = num + (count++);
		String temp = num + "";
		temp = temp.substring(temp.length() - 8);
		return temp;
	}
	
	/**
	 * @param result
	 */
	//	protected void assertResultTrue(int testId, Result result) {
	//		print("执行到第[" + testId + "]条用例");
	//		print(result);
	//		assertTrue(result != null);
	//		assertTrue(result.isExecuted());
	//		assertTrue(result.isSuccess());
	//	}
	
	/**
	 * @param result
	 */
	//	protected void assertResultFalse(int testId, Result result) {
	//		print("执行到第[" + testId + "]条用例");
	//		print(result);
	//		assertTrue(result != null);
	//		assertTrue(!result.isExecuted());
	//		assertTrue(!result.isSuccess());
	//	}
	
	/**
	 * @param result
	 */
	//	protected void assertExecutedFalse(int testId, Result result) {
	//		print("执行到第[" + testId + "]条用例");
	//		print(result);
	//		assertTrue(result != null);
	//		assertTrue(!result.isExecuted());
	//		assertTrue(result.isSuccess());
	//	}
	
	/**
	 * @param result
	 */
	//	protected void assertResultFalseForFinance(int testId, Result result) {
	//		print("执行到第[" + testId + "]条用例");
	//		print(result);
	//		assertTrue(result != null);
	//		assertTrue(!result.isSuccess());
	//	}
	
	/**
	 * @param result
	 */
	//	protected void assertResultTrueForFinance(int testId, Result result) {
	//		print("执行到第[" + testId + "]条用例");
	//		print(result);
	//		assertTrue(result != null);
	//		assertTrue(result.isSuccess());
	//	}
	
	/**
	 * @param result
	 */
	protected void assertResultTrueForFinance(int testId, StandardResultInfo result) {
		print("执行到第[" + testId + "]条用例");
		print(result);
		assertTrue(result != null);
		assertTrue(result.getStatus() == Status.SUCCESS);
	}
	
	protected void equalsTrue(String errCode, String resultCode) {
		assertEquals(errCode, resultCode);
	}
	
	/**
	 * @param result
	 */
	//	protected void assertSuccessFalse(int testId, Result result) {
	//		print("执行到第[" + testId + "]条用例");
	//		print(result);
	//		assertTrue(result != null);
	//		assertTrue(result.isExecuted());
	//		assertTrue(!result.isSuccess());
	//	}
	
	protected void assertNotNull(Object object) {
		Assert.assertNotNull(object);
	}
	
	protected void assertTrue(boolean ble) {
		Assert.assertTrue(ble);
	}
	
	protected void assertFalse(boolean ble) {
		Assert.assertFalse(ble);
	}
	
	protected void assertEquals(Object obj1, Object obj2) {
		Assert.assertEquals(obj1, obj2);
	}
	
	protected void assertNotEquals(Object obj1, Object obj2) {
		Assert.assertNotEquals(obj1, obj2);
	}
	
	protected void assertNull(Object object) {
		Assert.assertNull(object);
	}
	
	//	protected void assertIsBlank(String str) {
	//		Assert.assertTrue(StringUtils.isBlank(str));
	//	}
	//	
	//	protected void assertIsNotBlank(String str) {
	//		Assert.assertTrue(StringUtils.isNotBlank(str));
	//	}
	
	protected void assertMoney(Money m1, Money m2) {
		
		Assert
			.assertTrue(m1.getCent() == m2.getCent() && m1.getCurrency().equals(m2.getCurrency()));
	}
	
	protected void assertMoneyNo(Money m1, Money m2) {
		
		Assert.assertTrue(m1.getCent() == m2.getCent());
	}
	
	protected boolean equalDay(Date date1, Date date2) {
		return date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth()
				&& date1.getDate() == date2.getDate();
	}
	
	// 数据库服务器当前时间
	protected Date getSysDate() {
		return DateUtil.now();
	}
	
	// 客户端当前时间
	protected Date now() {
		return DateUtil.now();
	}
	
	// 昨天
	protected Date yesterday() {
		return DateUtil.increaseDate(now(), -1);
	}
	
	// 前天
	protected Date qiantianday() {
		return DateUtil.increaseDate(now(), -2);
	}
	
	// 明天
	protected Date tomorrow() {
		return DateUtil.tomorrow();
	}
	
	// 200天前
	protected Date before200() {
		
		Date now = new Date();
		
		Calendar calendar = new GregorianCalendar();
		
		calendar.setTime(now);
		
		calendar.add(Calendar.DAY_OF_YEAR, -200);
		
		return calendar.getTime();
	}
	
	protected String genId() {
		return String.valueOf(System.currentTimeMillis());
	}
	
	// 下一个月
	protected Date nextMonth(Date Now) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(Now);
		cal.add(Calendar.MONTH, 1);
		return cal.getTime();
	}
	
	// 下一个季度
	protected Date quarterOfAYear(Date Now) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(Now);
		cal.add(Calendar.MONTH, 3);
		return cal.getTime();
	}
	
	// 明年
	protected Date nextYear(Date Now) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(Now);
		cal.add(Calendar.YEAR, 1);
		return cal.getTime();
	}
	
	/*
	 * MM大写表示月，mm小写表示分钟 大写 HH 意思是二十四小时制，相反，小写hh表示十二小时制 获取对应格式的日期
	 * 
	 * @param Date 需要获取的日期
	 * 
	 * @param format 需要的日期格式，如“YYYY-MM-dd HH:mm:ss”
	 */
	public String getDateFormat(Date date, String format) {
		
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String formatDate = formatter.format(date);
		
		return formatDate;
	}
	
	/**
	 * 以yyyy-MM-dd HH:mm:ss格式化时间
	 * 
	 * @param date
	 * @return
	 */
	public String getDateFormat(Date date) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatDate = formatter.format(date);
		
		return formatDate;
	}
	
	/*
	 * 获取格式yyyy-MM-dd HH:mm:ss任意时间
	 * 
	 * @param strDate 如"2012-12-12 12:12:12"
	 */
	public static Date getFormatDate(String strDate) {
		
		Date wannaDate = null;
		try {
			wannaDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate);
		} catch (ParseException e) {
		}
		
		return wannaDate;
	}
	
	public static Date simpleLongStr2Date(String strDate) {
		Date wannaDate = null;
		try {
			wannaDate = new SimpleDateFormat("yyyyMMddHHmmss").parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return wannaDate;
	}
	
	/*
	 * 把字符串转换成指定格式date
	 * 
	 * @param strDate 如"2012-12-12 12:12:12"
	 */
	public static Date getFormatDate(String strDate, String fomat) {
		
		Date wannaDate = null;
		try {
			wannaDate = new SimpleDateFormat(fomat).parse(strDate);
		} catch (ParseException e) {
		}
		
		return wannaDate;
	}
	
	/*
	 * 返回一个随机的大/小写字母
	 * 
	 * @param flag 是否大写 True:为大写 false:为小写
	 */
	
	public String getRandomCharacter(boolean flag) {
		Random rd = new Random();
		
		int ret2 = Math.abs(rd.nextInt()) % 26 + 97;
		char ch = (char) ret2;
		String a = Character.toString(ch);
		if (flag) {
			a = a.toUpperCase();
		} else {
			a = a.toLowerCase();
		}
		return a;
	}
	
	/**
	 * 打印输出
	 * 
	 * @param object
	 */
	@SuppressWarnings("rawtypes")
	protected void print(Object object) {
		String str = "";
		
		if (object instanceof List) {
			logger.info("=====================================");
			List list = (List) object;
			logger.info("List size:" + list.size());
			for (int i = 0; i < list.size(); i++) {
				
				str = list.get(i).toString();
				logger.info(str + "\n");
			}
			logger.info("=====================================");
			return;
		}
		
		else if (object == null) {
			
			str = "null";
		} else {
			str = object.toString();
		}
		
		logger.error("=====================================");
		logger.error(str);
		logger.error("=====================================");
	}
	
}
