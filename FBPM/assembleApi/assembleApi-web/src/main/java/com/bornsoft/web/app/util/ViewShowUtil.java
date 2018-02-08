package com.bornsoft.web.app.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.info.contract.ProjectContractItemInfo;
import com.born.fcs.pm.ws.info.council.CouncilJudgeInfo;
import com.born.fcs.pm.ws.info.council.CouncilProjectInfo;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

public class ViewShowUtil {
	
	public static boolean findJusgeIn(List<CouncilJudgeInfo> judges, Long userId) {
		boolean in = false;
		if (ListUtil.isEmpty(judges)) {
			return false;
		}
		for (CouncilJudgeInfo info : judges) {
			if (info.getJudgeId() > 0 && info.getJudgeId() == userId) {
				return true;
			}
		}
		return in;
	}
	
	public static boolean checkVoteAllDown(List<CouncilProjectInfo> projects) {
		boolean allCheck = true;
		for (CouncilProjectInfo info : projects) {
			// 若风控委秘书已执行本次不议，代表投票已完成
			if (BooleanEnum.YES == info.getRiskSecretaryQuit()) {
				break;
			}
			// 20160926添加判断 主持人本次不议后[投票结果为本次不议]，需要执行后续操作后才能结束会议
			if (ProjectVoteResultEnum.END_QUIT == info.getProjectVoteResult()) {
				// 若未执行后续操作，不允许结束会议
				if (info.getCompereMessage() == null) {
					allCheck = false;
					
					break;
				}
			}
			
			// 未开始或进行中代表未投票完成
			if (ProjectVoteResultEnum.NOT_BEGIN == info.getProjectVoteResult()
				|| ProjectVoteResultEnum.IN_VOTE == info.getProjectVoteResult()) {
				allCheck = false;
				
				break;
			}
		}
		return allCheck;
	}
	
	/** 相加 str1 + str2 + str3 */
	public static double strAdd(String str1, String str2, String str3) {
		double d1 = NumberUtil.parseDouble(str1, 0);
		double d2 = NumberUtil.parseDouble(str2, 0);
		double d3 = NumberUtil.parseDouble(str3, 0);
		return Math.round((d1 + d2 + d3) * 100) / 100.0;
		
	}
	
	/**
	 * 传入 子合同info跳转只读展示页面
	 * @param info
	 * @return
	 */
	public static String getReadExcelUrl(ProjectContractItemInfo info, String requestPath) {
		String url = getExcelUrl(info, requestPath);
		if (StringUtil.isNotBlank(url)) {
			//			url = url.substring(0, url.length() - 4) + "&read=read$id:";
			url = url + "&read=read";
		}
		return url;
	}
	
	/**
	 * 传入子合同Info跳转修改页面
	 * @param info
	 * @return
	 */
	public static String getExcelUrl(ProjectContractItemInfo info, String requestPath) {
		if (info == null) {
			return "";
		}
		//url2 = "http://127.0.0.1:8096";
		//KGBrowser://$link:http://127.0.0.1:8080/word/NewFile2009.jsp$id:
		// 拆分url
		String url = info.getFileUrl();
		StringBuilder sb = new StringBuilder(
		//			"KGBrowser://$link:" + requestPath
		//					+
			"/projectMg/contract/excelMessage.htm?contractItemId=");
		sb.append(info.getId());
		// 模版id代表在子合同没有url的时候 使用模版来打开页面
		sb.append("&templateId=");
		sb.append(info.getTemplateId());
		getInfoFromUrl(url, sb);
		//		sb.append("$id:");
		return sb.toString();
	}
	
	/**
	 * 传入子合同Info跳转修改页面
	 * @param info
	 * @return
	 */
	public static String getExcelUrl(ProjectContractItemInfo info) {
		if (info == null) {
			return "";
		}
		// 拆分url
		String url = info.getFileUrl();
		StringBuilder sb = new StringBuilder("/projectMg/contract/excelMessage.htm?contractItemId=");
		sb.append(info.getId());
		// 模版id代表在子合同没有url的时候 使用模版来打开页面
		sb.append("&templateId=");
		sb.append(info.getTemplateId());
		getInfoFromUrl(url, sb);
		return sb.toString();
	}
	
	/**
	 * 传入 “，”隔开的url进入修改页面 【此方法基本不可能用到，没有后续修改方法，需要使用请咨询 hjiajie@yiji.com】
	 * @param url
	 * @param sb
	 */
	private static void getInfoFromUrl(String url, StringBuilder sb) {
		String fileName = ""; // 文件名字
		String fileUrl = ""; // 物理地址
		@SuppressWarnings("unused")
		String requestPath = ""; // 访问地址
		if (StringUtil.isNotBlank(url) && url.indexOf(",") > 0) {
			String[] pathArray = url.split(",");
			fileName = pathArray[0];
			fileUrl = pathArray[1];
			requestPath = pathArray[2];
		}
		sb.append("&fileName=");
		sb.append(com.born.fcs.pm.util.StringUtil.encodeURI(fileName));
		sb.append("&fileUrl=");
		sb.append(fileUrl);
	}
	
	/**
	 * 添加通用参数
	 * @param url
	 * @return
	 */
	public static String getExcelUrlByStr(String url, String requestPath) {
		if (StringUtil.isBlank(url)) {
			return "";
		}
		StringBuilder sb = new StringBuilder(
		//			"KGBrowser://$link:" + requestPath
		//					+ 
			"/projectMg/contract/excelMessage.htm?contractItemId=0");
		
		getInfoFromUrl(url, sb);
		//		sb.append("$id:");
		return sb.toString();
	}
	
	/**
	 * 添加通用参数
	 * @param url
	 * @return
	 */
	@Deprecated
	public static String getExcelUrlByStrAndId(Long id, String url) {
		if (StringUtil.isBlank(url)) {
			return "";
		}
		if (id == null) {
			id = 0L;
		}
		StringBuilder sb = new StringBuilder("/projectMg/contract/excelMessage.htm?contractItemId=");
		sb.append(id);
		getInfoFromUrl(url, sb);
		return sb.toString();
	}
	
	/**
	 * 传入 “，”隔开的url进入查看页面
	 * @param urlIn
	 * @return
	 */
	public static String getExcelUrlByStrRead(String urlIn, String requestPath) {
		String url = getExcelUrlByStr(urlIn, requestPath);
		if (StringUtil.isNotBlank(url)) {
			//			url = url.substring(0, url.length() - 4) + "&read=read$id:";
			url = url + "&read=read";
		}
		return url;
	}
	
	/** 特殊计算公式单独处理下：CT 全部债务/EBITDA(倍) */
	public static String setCals(String calculatingFormula) {
		//(actualValue)<=5?0:-999.0;(actualValue)>150?4:-999.0;(actualValue)>200?3:-999.0;(actualValue)>20?0:-999.0;(actualValue)>250?2:-999.0;(actualValue)==200?1:-999.0;(actualValue)<100?5:-999.0
		if (StringUtil.isNotBlank(calculatingFormula)) {
			String[] calculatingFormulas = calculatingFormula.split(";");
			Map<Long, String> map = new HashMap<>();
			for (String s : calculatingFormulas) {
				if (StringUtil.isBlank(s))
					continue;
				if (s.indexOf(">") > -1) {
					set(s, ">", map);
				} else if (s.indexOf(">=") > -1) {
					set(s, ">=", map);
				} else if (s.indexOf("==") > -1) {
					set(s, "==", map);
				} else if (s.indexOf("<") > -1) {
					set(s, "<", map);
				} else if (s.indexOf("<=") > -1) {
					set(s, "<=", map);
				}
			}
			if (!map.isEmpty()) {
				
				Object[] keySet = map.keySet().toArray();
				Arrays.sort(keySet);
				StringBuilder s = new StringBuilder();
				if (map.size() > 2) {
					s.append(map.get(keySet[0]));
					s.append(map.get(keySet[1]));
				}
				for (int i = map.size() - 1; i > 1; i--) {
					s.append(map.get(keySet[i]).replaceAll("actualValue",
						"actualValue/" + keySet[0] + "*100"));
				}
				s.append("0");
				calculatingFormula = s.toString();
			}
		}
		return calculatingFormula.replaceAll("-999.0", "");
		
	}
	
	/** js转码后解码 */
	public static String URLDecoder(String s) {
		if (StringUtil.isNotBlank(s)) {
			try {
				s = java.net.URLDecoder.decode(s, "UTF-8");
				
			} catch (UnsupportedEncodingException e) {
				
			}
		}
		return s;
		
	}
	
	private static void set(String s, String symble, Map<Long, String> map) {
		
		String[] thisSpl = s.split(symble);
		if (thisSpl.length == 2) {
			if (StringUtil.isNotBlank(thisSpl[0]) && thisSpl[0].indexOf("actualValue") > -1) {
				if (StringUtil.isNotBlank(thisSpl[1]) && thisSpl[1].indexOf("?") > -1) {
					String a = thisSpl[1].replace("=", "");
					String b = a.substring(0, a.indexOf("?")).replaceAll(" ", "");
					map.put(Long.parseLong(b), s);
				}
			}
			
		}
		
	}
	
}
