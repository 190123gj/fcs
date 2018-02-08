package com.bornsoft.web.app.main;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import rop.thirdparty.com.google.common.collect.Lists;

import com.bornsoft.api.service.app.DataPermissionUtil;
import com.bornsoft.utils.base.BornInfoBase;
import com.bornsoft.utils.base.IBornEnum;

/**
 * @Description: APP 菜单枚举 
 * @author taibai@yiji.com
 * @date 2016-9-21 下午4:16:59
 * @version V1.0
 */
public enum AppMenuEnum implements IBornEnum {

	ToDo("待办事项","todo") {
		@Override
		public boolean canAccess() {
			return true;
		}
	},
	ToOnPc("PC待办","toOnPc") {
		@Override
		public boolean canAccess() {
			return true;
		}
	},
	RiskList("风险列表","riskList") {
		@Override
		public boolean canAccess() {
			return DataPermissionUtil.isBusiManager();
		}
	},
	MeetingList("待参会议、我的会议列表","meetingList") {
		@Override
		public boolean canAccess() {
			return true;
		}
	},
	CustomList("客户列表","customList") {
		@Override
		public boolean canAccess() {
			return true;
		}
	},
	AddCustom("新增客户","addCustom") {
		@Override
		public boolean canAccess() {
			return true;
		}
	},
	ProjectList("项目列表","projectList") {
		@Override
		public boolean canAccess() {
			return true;
		}
	},
	RiskManage("风险监控","riskManage") {
		@Override
		public boolean canAccess() {
			return true;
		}
	},
	SetUp("立项申请","setUp") {
		@Override
		public boolean canAccess() {
			return DataPermissionUtil.isBusiManager();
		}
	},
	RiskWarn("风险预警","riskWarn") {
		@Override
		public boolean canAccess() {
			return DataPermissionUtil.isBusiManager();
		}
	},
	CreditImplement("授信落实","creditImplement") {
		@Override
		public boolean canAccess() {
			return DataPermissionUtil.isBusiManager();
		}
	},
	ContractBack("合同回传","contractBack") {
		@Override
		public boolean canAccess() {
			return DataPermissionUtil.isBusiManager();
		}
	},
	ApplyLend("申请放用款","applyLend") {
		@Override
		public boolean canAccess() {
			return DataPermissionUtil.isBusiManager();
		}
	},
	ChargeNotice("收费通知","chargeNotice") {
		@Override
		public boolean canAccess() {
			return DataPermissionUtil.isBusiManager()|| DataPermissionUtil.isFinancialZjlc();
		}
	},
	MessageList("消息界面","messageList") {
		@Override
		public boolean canAccess() {
			return true;
		}
	},
	PieChart("饼图","pieChart") {
		@Override
		public boolean canAccess() {
			return DataPermissionUtil.isSystemAdministrator()||DataPermissionUtil.isCompanyLeader();
		}
	},
	;

	private String code;
	private String message;
	
	private AppMenuEnum(String message,String code) {
		this.message = message;
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public  abstract boolean canAccess();

	@Override
	public String code() {
		return code;
	}
	
	/**
	 * 获取当前用户可访问的菜单
	 * @return
	 */
	public static List<Menu> getOwnMenus(){
		List<Menu>  menuList = Lists.newArrayList();
		AppMenuEnum [] menus = values();
		for(AppMenuEnum menu : menus){
			menuList.add(new Menu( menu.getCode(), menu.canAccess()));
		}
		return menuList;
	}
	
	@Override
	public String message() {
		return message;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	/**
	 * @Description: 菜单实体 
	 * @author taibai@yiji.com
	 * @date 2016-9-21 下午3:21:55
	 * @version V1.0
	 */
	public static class Menu extends BornInfoBase{
		private String code;
		private boolean isAllowed;
		
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public boolean isAllowed() {
			return isAllowed;
		}
		public void setAllowed(boolean isAllowed) {
			this.isAllowed = isAllowed;
		}
		private Menu(String code, boolean isAllowed) {
			super();
			this.code = code;
			this.isAllowed = isAllowed;
		}
	}
	
}
