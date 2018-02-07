package com.born.fcs.face.integration.bpm.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.born.fcs.face.integration.info.MenuInfo;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.pm.util.StringUtil;

public class PermissionUtil {
	
	private static final String[] excludeUrls = { "/sso", "/uploadfile/", "/styles/", "/images/",
													"/resources/", "/anon/", "/test/", "/login/",
													"/help", "/upload/", "/staticFiles/",
													"/baseDataLoad/", "/favicon.ico", "/userHome/",
													"/JumpTrust/", "/projectMg/form/",
													"/projectMg/riskWarning/querySignal.json",
													"/projectMg/basicData/", "/projectMg/common/",
													"/systemMg/message/user/",
													"/userHome/modifyPassword.htm",
													"/userHome/taskDistributor.htm",
													"/projectMg/internalOpinionExchange/",
													"/projectMg/formChangeApply/",
													"/projectMg/financialProject/setUp/audit/",
													"/projectMg/financialProject/transfer/audit/",
													"/fundMg/receiptPaymentList.htm",
													"/fundMg/travelExpense/",
													"/fundMg/expenseApplication/",
													"/systemMg/instructions.htm" };
	
	static PermissionService permissionStaticService;
	
	public static synchronized void init(PermissionService permissionService) {
		if (permissionStaticService == null) {
			permissionStaticService = permissionService;
		}
	}
	
	public static boolean checkPermission(String url) {
		
		if (StringUtil.isBlank(url)) {
			return true;
		}
		
		//不验证权限
		for (String item : excludeUrls) {
			if (url.startsWith(item)) {
				return true;
			}
		}
		
		//.json请求不验证权限
		String newUrl = "";
		String queryString = "";
		int index = url.indexOf("?");
		if (index > 0) {
			newUrl = url.substring(0, index);
			queryString = url.substring(index + 1);
			if (newUrl.endsWith(".json")) {
				return true;
			}
		} else {
			newUrl = url;
			if (url.endsWith(".json")) {
				return true;
			}
		}
		
		//审核不验证权限(包含taskId参数)
		if (StringUtil.isNotBlank(queryString)) {
			String[] kvs = queryString.split("&");
			for (String kv : kvs) {
				if (kv.startsWith("taskId=")) {
					return true;
				}
			}
		}
		
		newUrl = filterUrl(newUrl);
		String[] stringSplit = newUrl.split("/");
		if (stringSplit.length > 1) {
			List<MenuInfo> permissionInfos = ShiroSessionUtils.getSessionLocal()
				.getUserMenuInfoListBySysAlias(stringSplit[1]);
			if (permissionInfos == null) {
				permissionStaticService.loadSystemPermission(stringSplit[1]);
				permissionInfos = ShiroSessionUtils.getSessionLocal()
					.getUserMenuInfoListBySysAlias(stringSplit[1]);
			}
			return checkUrl(newUrl, permissionInfos);
			
		} else
			return false;
	}
	
	public static boolean checkPermissionIncludeJson(String url) {
		
		if (StringUtil.isBlank(url)) {
			return true;
		}
		for (String item : excludeUrls) {
			if (url.startsWith(item)) {
				return true;
			}
		}
		url = filterUrl(url);
		String[] stringSplit = url.split("/");
		if (stringSplit.length > 1) {
			List<MenuInfo> permissionInfos = ShiroSessionUtils.getSessionLocal()
				.getUserMenuInfoListBySysAlias(stringSplit[1]);
			if (permissionInfos == null) {
				permissionStaticService.loadSystemPermission(stringSplit[1]);
				permissionInfos = ShiroSessionUtils.getSessionLocal()
					.getUserMenuInfoListBySysAlias(stringSplit[1]);
			}
			return checkUrl(url, permissionInfos);
			
		} else
			return false;
	}
	
	public static boolean checkUrl(String url, List<MenuInfo> permissionInfos) {
		if (permissionInfos == null)
			return false;
		for (MenuInfo permission : permissionInfos) {
			
			if (StringUtil.isEmpty(permission.getUrl()))
				continue;
			Pattern p = Pattern.compile(permission.getUrl().replace("*", ".*").replace("?", "\\?"));
			Matcher matcher = p.matcher(url);
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}
	
	public static String filterUrl(String url) {
		if (StringUtil.isEmpty(url))
			return "";
		if (url.startsWith("http://") || url.startsWith("https://")) {
			url = url.substring(url.indexOf("://") + 3);
			url = url.substring(url.indexOf("/"));
		}
		return url;
	}
	
	public static void main(String[] args) {
		String newUrl = "";
		String queryString = "";
		String url = "http://www.aa.com/aa.json?time=1203837&xxx=12321&xaaaa=323&taskId=1123";
		int index = url.indexOf("?");
		if (index > 0) {
			newUrl = url.substring(0, index);
			queryString = url.substring(index + 1);
			if (newUrl.endsWith(".json")) {
				System.out.println("OK");
			}
		}
		
		//审核不验证权限(包含taskId参数)
		System.out.println(queryString);
		if (StringUtil.isNotBlank(queryString)) {
			String[] kvs = queryString.split("&");
			for (String kv : kvs) {
				System.out.println(kv);
				if (kv.startsWith("taskId=")) {
					System.out.println("has");
				}
			}
		}
		
		System.out.println("END");
	}
}
