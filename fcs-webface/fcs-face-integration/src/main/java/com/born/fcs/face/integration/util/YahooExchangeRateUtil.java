package com.born.fcs.face.integration.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.pm.util.HttpClientUtil;
import com.born.fcs.pm.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 雅虎汇率工具，外币转人民币
 *
 * @author wuzj
 */
public class YahooExchangeRateUtil {
	
	private static Logger logger = LoggerFactory.getLogger(YahooExchangeRateUtil.class);
	
	//雅虎财经api地址 
	//参数 s=${fromto}=x ${fromto} 表示币种 如：USDCNY(美元转人民币),=x必须
	//请求结果 "USDCNY=x",6.8926,"3/16/2017","6:09am"
	private final static String api = "http://download.finance.yahoo.com/d/quotes.csv?e=.csv&f=sl1d1t1";
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mma", Locale.ENGLISH);
	
	public static Map<String, List<String>> cnyExchangeRateMap = Maps.newHashMap();
	
	/**
	 * 转换成人民币元
	 * @param currencyCode 货币代码
	 * @param amount 金额 <br>
	 * api请求结果格式："USDCNY=x",6.8926,"3/16/2017","6:09am"
	 */
	public static CnyResult toCny(String currencyCode, String amount) {
		logger.info("转换成人民币开始：{},{}", currencyCode, amount);
		CnyResult exresult = new CnyResult();
		Date now = new Date();
		exresult.setCurrencyAmount(amount);
		exresult.setCurrencyCode(currencyCode);
		try {
			if (StringUtil.equals(currencyCode, "CNY")) {
				exresult.setSuccess(true);
				exresult.setExchangeRate("1");
				exresult.setExchangeTime(new Date());
				exresult.setAmount(Money.amout(amount));
				return exresult;
			}
			
			List<String> cells = null;
			
			//缓存的结果,距上次调用接口10分钟内，不再调用接口
			List<String> cacheCells = cnyExchangeRateMap.get(currencyCode);
			if (cacheCells != null && cacheCells.size() >= 5) {
				Date invokeTime = sdf.parse(cacheCells.get(4));
				if ((now.getTime() - invokeTime.getTime()) <= 1000 * 60 * 10) {
					cells = cacheCells;
				}
			}
			
			//没有缓冲或者超过1小时再调用接口
			if (cells == null) {
				Map<String, String> params = new HashMap<String, String>();
				params.put("s", currencyCode + "CNY=x");
				String result = HttpClientUtil.get(api, params);
				logger.info("汇率接口返回:{}", result);
				cells = parseCsvLine(result);
				
				//缓存结果
				if (cells != null && cells.size() >= 4) {
					cells.add(sdf.format(now));
					cnyExchangeRateMap.put(currencyCode, cells);
				}
			}
			
			//调用接口成功
			if (cells != null && cells.size() >= 4) {
				String exchangeTime = cells.get(2) + " " + cells.get(3);
				exresult.setExchangeTime(sdf.parse(exchangeTime));
				exresult.setExchangeRate(cells.get(1).replace(",", ""));
				BigDecimal amountDecimal = new BigDecimal(amount.replaceAll(",", ""));
				BigDecimal exchangeRate = new BigDecimal(exresult.getExchangeRate());
				exresult.setAmount(Money.amout(amountDecimal.multiply(exchangeRate).toString()));
			}
			
			exresult.setSuccess(true);
			exresult.setMessage("转换成人民币成功");
		} catch (Exception e) {
			exresult.setSuccess(false);
			exresult.setMessage("转换成人民币出错");
			logger.error("转换成人民币出错：{}", e);
		}
		return exresult;
	}
	
	/**
	 * 结息csv文件行
	 * @param line
	 * @return
	 */
	private static List<String> parseCsvLine(String line) {
		Pattern pCells = Pattern.compile("(\"[^\"]*(\"{2})*[^\"]*\")*[^,]*[^\n],?");
		Matcher mCells = pCells.matcher(line);
		List<String> cells = new ArrayList<String>();// 每行记录一个list  
		// 读取每个单元格  
		String str;// 一个单元格  
		while (mCells.find()) {
			str = mCells.group();
			str = str.replaceAll("(?sm)\"?([^\"]*(\"{2})*[^\"]*)\"?.*,?", "$1");
			str = str.replaceAll("(?sm)(\"(\"))", "$2");
			cells.add(str);
		}
		return cells;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(toCny("USD", "10000"));
	}
}
