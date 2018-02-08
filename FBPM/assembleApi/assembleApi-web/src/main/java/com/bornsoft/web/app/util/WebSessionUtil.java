package com.bornsoft.web.app.util;

import java.util.List;

import com.born.fcs.face.integration.bpm.service.PermissionUtil;
import com.born.fcs.face.integration.info.MenuInfo;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.yjf.common.lang.util.StringUtil;

public class WebSessionUtil {
	private final SessionLocal sessionLocal;
	
	public WebSessionUtil(SessionLocal sessionLocal) {
		this.sessionLocal = sessionLocal;
		
	}
	
	public boolean isLogin() {
		return sessionLocal != null && StringUtil.isNotBlank(sessionLocal.getUserName());
	}
	
	public boolean checkPermission(String url) {
		if (sessionLocal != null) {
			url = PermissionUtil.filterUrl(url);
			String[] stringSplit = url.split("/");
			String sysAlias = stringSplit[1];
			if (stringSplit.length > 1) {
				sysAlias = stringSplit[1];
			}
			List<MenuInfo> permissionInfos = sessionLocal.getUserMenuInfoListBySysAlias(sysAlias);
			return PermissionUtil.checkUrl(url, permissionInfos);
		}
		return false;
	}
	
	public String getUserViewName() {
		
		if (sessionLocal != null) {
			if (StringUtil.isNotBlank(sessionLocal.getRealName())) {
				return sessionLocal.getRealName();
			} else {
				return sessionLocal.getUserName();
			}
		}
		return "";
	}
	
	public static SessionLocal getCurrentSessionLocal() {
		return ShiroSessionUtils.getSessionLocal();
	}
	
}
