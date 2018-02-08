package com.bornsoft.web.security.permission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.born.fcs.face.integration.bpm.service.PermissionService;
import com.born.fcs.face.integration.info.MenuInfo;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.pm.util.StringUtil;
import com.bornsoft.api.service.app.DataPermissionUtil;
import com.bornsoft.utils.exception.BornApiException;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * @Description: 此处继承纯粹为了使用PC端的权限，静态方法不能重写 
 * @author taibai@yiji.com
 * @date 2016-12-7 上午10:08:29
 * @version V1.0
 */
public class PermissionUtilLocal {
	
	private static final Logger logger = LoggerFactory.getLogger(PermissionUtilLocal.class);
	
	private static final String[] excludeUrls = { "/uploadfile/", "/styles/", "/images/",
													"/resources/", "/anon/", "/test/", "/login/",
													"/help", "/upload/", "/staticFiles/",
													"/baseDataLoad/", "/favicon.ico", "/userHome/",
													"/JumpTrust/", "/projectMg/form/",
													"/projectMg/riskWarning/querySignal.json",
													"/projectMg/basicData/", "/projectMg/common/",
													"/systemMg/message/user/",
													"/userHome/modifyPassword.htm",
													"/projectMg/internalOpinionExchange/",
													"/projectMg/formChangeApply/",
													"/projectMg/financialProject/setUp/audit/",
													"/projectMg/financialProject/transfer/audit/",
													"/fundMg/receiptPaymentList.htm",
													"/fundMg/travelExpense/",
													"/fundMg/expenseApplication/",
													"/systemMg/instructions.htm",
													"/gateway.html","/userHome/aboutUs.html",
													"/userHome/checkUpdate.json",
													"/postRedirect.vm",
													"/userHome/toRisk.html",
													"/css",
													"/services",
													"/system/failure.html",
													"/system/success.html",
													//项目
													"/projectMg/setUp/info.json",
													"/projectMg/setUp/list.json",
													"/projectMg/setUp/form.json",
													
													"/projectMg/getTransferUserType.json",
													"/projectMg/list.json",
													//风险
													"/projectMg/riskWarning/editInit.json",
													"/projectMg/riskWarning/warningCount.json",
													"/projectMg/riskWarning/info.json",
													"/projectMg/riskWarning/list.json",
											        "/projectMg/projectCreditCondition/queryCreditCondtions.json",
											        "/projectMg/projectCreditCondition/loadAssetAtachment.json",
											        "/projectMg/projectCreditCondition/initProjectCreditCondition.json",
											        "/projectMg/riskWarning/queryProjects.json",
											        //资金
											        "/fundMg/expenseApplication/init.json",
											        "/fundMg/expenseApplication/getCostTypeList.json",
											        "/fundMg/expenseApplication/checkExpenseType.json",
											        "/fundMg/expenseApplication/expenseApplicationReverse.json",
											        
											        //放用款
											        "/projectMg/loanUseApply/form.json",//允许点进来看
											        "/projectMg/loanUseApply/loanUseProject.json",
											        "/projectMg/loanUseApply/queryCreditConditions.json",
											        //新增收费通知
											        "/projectMg/chargeNotification/queryProjects.json",
											        "/projectMg/chargeNotification/contractChoose.json",
											        "/projectMg/chargeNotification/loadFeeType.json",
											        //会议
											        "/projectMg/meetingMg/awaitCouncilProjectList.json",
											        "/projectMg/meetingMg/allCouncilProjectList.json",
											        "/projectMg/meetingMg/councilCount.json",
											        "/projectMg/meetingMg/queryCouncilCount.json",
											        "/projectMg/meetingMg/councilList.json",
											        //合同
											        "/projectMg/contract/list.json",
											        "/projectMg/contract/queryMainContract.json",
											        //用户
											        "/login.json",
											        "/logout.json",
											        "/validateCode.json",
											        "/sendMobileCode.json",
											        "/checkUserAndMobile.json",
											        "/modifyPassword.json",
											        "/activeUser.json",
											        "/forgetPwd.json",
											        
											        "/userHome/createToken.json",
											        "/userHome/taskList.json",
											        "/userHome/toWorkStation.html",
											        "/userHome/taskDistributor.json",
											        "/userHome/queryChartData.json",
											        "/userHome/mainMsgCount.json",
											        "/userHome/taskCount.json",
											        "/userHome/toRisk.html",
											        "/userHome/getMenus.json",
											        "/userHome/checkUpdate.json",
											        "/userHome/aboutUs.html",
											        //渠道
											        "/customerMg/channal/list.json",
											        //客户
											        "/customerMg/validata.json",
											        "/customerMg/list.json",
											        "/customerMg/companyCustomer/info.json",
											        "/customerMg/personalCustomer/info.json",
													};
	
	static Map<String,String> rightUrlMap = new HashMap<>();
	static String[] adminUrls = new String[]{
		"/api/allUrl.json",
	};
	static{
		rightUrlMap.put("/projectMg/riskWarning/save.json", "/projectMg/riskWarning/editSubmit.json");
		rightUrlMap.put("/projectMg/riskWarning/read.json", "/projectMg/riskWarning/editSubmit.json");
	}
	static PermissionService permissionStaticService;
	
	public static synchronized void init(PermissionService permissionService) {
		if (permissionStaticService == null) {
			permissionStaticService = permissionService;
		}
	}
	
	public static boolean checkPermission(String url) {
		
		try {
			//转换链接
			url = getRightUrl(url);
			
			if (StringUtil.isBlank(url)) {
				return true;
			}
			//admin访问url
			if(adminAccessUrl(url)){
				return true;
			}
			//无权限链接
			for (String item : excludeUrls) {
				if (url.startsWith(item)) {
					return true;
				}
			}
			//特殊url权限拦截
			checkSpecialUrl(url);
			
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
				
			} else{
				return false;
			}
		} catch (AccessDeniedException e) {
			logger.error("权限检查未通过",e);
			return false;
		}
	}
	
	/**
	 * 管理员可直接访问  url isAdmin&&isAdminUrl
	 * @param url
	 * @return
	 */
	private static boolean adminAccessUrl(String url) {
		boolean access = false;
		
		boolean adminUrl = false;
		for(String u : adminUrls){
			if(u.equals(url)){
				adminUrl = true;
				break;
			}
		}
		if(adminUrl){
			SessionLocal local = ShiroSessionUtils.getSessionLocal();
			access = adminUrl && local!=null && local.getRolesName()!=null && StringUtils.contains(local.getRolesName(), "管理员") ;
		}else{
			access = false;
		}
		return access;
	}

	/**
	 * 检查特殊url权限
	 * @param url
	 */
	private static void checkSpecialUrl(String url) {
		boolean result = true;
		
		//客户 /customerMg/personalCustomer/add.htm 不需要由权限统一控制
		// 合同回传 /projectMg/contract/saveContractBack.json
		//授信落实
		if(StringUtils.contains("/projectMg/projectCreditCondition/saveProjectCreditCondition.json", url)){
			result= DataPermissionUtil.isBusiManager();
		}else if(StringUtils.contains("/projectMg/loanUseApply/saveApply.json", url)){
			result= DataPermissionUtil.isBusiManager();
		}else if(StringUtils.contains("/projectMg/chargeNotification/saveChargeNotification.json", url)){
			result= DataPermissionUtil.isBusiManager();
		}else if(StringUtils.contains("/projectMg/riskWarning/save.json", url)){
			result= DataPermissionUtil.isBusiManager();
		}else if(
				StringUtils.contains("/projectMg/setUp/saveCustomerBaseInfo.json", url)
				||StringUtils.contains("/projectMg/setUp/saveGuaranteeEntrustedProject.json", url)
				||StringUtils.contains("/projectMg/setUp/saveUnderwritingProject.json", url)
				||StringUtils.contains("/projectMg/setUp/saveLgLitigationProject.json", url)
				//StringUtils.contains("/projectMg/setUp/form.json", url)
				){
			result = DataPermissionUtil.isBusiManager();
		}else {

		}
		
		if(!result){
			throw new AccessDeniedException("没有权限访问资源：" + url);
		}
	}

	/**
	 * 获取权限URL
	 * @param url
	 * @return
	 */
	private static String getRightUrl(String url) {
		String tmp = rightUrlMap.get(url);
		if(StringUtils.isBlank(tmp)){
			if(url.indexOf(0) == '/' && url.indexOf(1) == '/'){
				url = url.substring(1, url.length());
			}
			return url;
		}else{
			return tmp;
		}
	}

	public static boolean checkUrl(String url, List<MenuInfo> permissionInfos) {
		if (permissionInfos == null)
			return false;
		url = cutExtension(url);
		for (MenuInfo permission : permissionInfos) {
			if(permission.getUrl().contains("projectMg/list.htm")){
				System.out.println(permission);
			}
			if (StringUtil.isEmpty(permission.getUrl()))
				continue;
			Pattern p = Pattern.compile(permission.getUrl().replace("*", ".*").replace("?", "\\?"));
			
			if (p.matcher(url +"json").matches() || p.matcher(url + "htm").matches() || p.matcher(url + "html").matches()) {
				return true;
			}
		}
		return false;
	}
	
	private static String cutExtension(String url) {
		if(StringUtils.isNotBlank(url)){
			int index = url.lastIndexOf(".");
			url = url.substring(0, index+1);
		}
		return url;
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
	
	public static class AccessDeniedException extends BornApiException{

		/**
		 */
		private static final long serialVersionUID = 1L;

		public AccessDeniedException(String msg) {
			super(msg);
		}
	}
}
