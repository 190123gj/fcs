package com.bornsoft.utils.tool;

import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yjf.common.lang.util.StringUtil;

/**
 * @Description: 通用小工具 
 * @author taibai@yiji.com
 * @date 2016-10-31 上午11:10:08
 * @version V1.0
 */
public class CommonUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	
	private static String lineSeperate = System.getProperty("line.separator");
	
	public static String cutString(String src, int len){
		if(StringUtil.isBlank(src)){
			return "";
		}else if(src.length()>len){
			return src.substring(0, len);
		}else{
			return src;
		}
	}
	
	public static String getTextMsg(String content){
		try {
			if(StringUtil.isBlank(content)){
				return "";
			}
			Parser parser = new Parser();
			parser.setInputHTML(content);
			StringBean bean=new StringBean();
			bean.setLinks(false);
			bean.setReplaceNonBreakingSpaces(true);
			bean.setCollapse(true);
			parser.visitAllNodesWith(bean);
			parser.reset();
			content =  bean.getStrings();
			int lineS = content.indexOf(lineSeperate);
			if(lineS>-1){
				content = content.substring(0, lineS);
			}
			lineS = content.indexOf("|");
			if(lineS>-1){
				content = content.substring(0, lineS);
			}
		} catch (Exception e1) {
			logger.error("截取文本失败",e1);
		}
		return content;
	}

}
