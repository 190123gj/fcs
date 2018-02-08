package com.born.fcs.pm.ws.app;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 *
 * APP 后台
 * @author wuzj
 */
public class AppAboutConfInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 7622561957658547992L;
	
	/** 主键 */
	private long confId;
	/** 是否上线 YES/NO */
	private String online;
	/** 关于我们内容 */
	private String content;
	/** logo地址 */
	private String logo;
	/** IOS包路径 */
	private String iosPath;
	/** IOS版本号 */
	private String iosVersion;
	/** 更新内容 */
	private String iosChangeLog;
	/** 强制升级 */
	private int iosForceUpdate;
	/** 可选升级 */
	private int iosOptionUpdate;
	/** IOS二维码 */
	private String iosTwoDimension;
	/** android包路径 */
	private String androidPath;
	/** android版本 */
	private String androidVersion;
	/** android更新日志 */
	private String androidChangeLog;
	/** 强制升级 */
	private int androidForceUpdate;
	/** 可选升级 */
	private int androidOptionUpdate;
	/** android二维码 */
	private String androidTwoDimension;
	/** 新增时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	
	public long getConfId() {
		return this.confId;
	}
	
	public void setConfId(long confId) {
		this.confId = confId;
	}
	
	public String getOnline() {
		return this.online;
	}
	
	public void setOnline(String online) {
		this.online = online;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getLogo() {
		return this.logo;
	}
	
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	public String getIosPath() {
		return this.iosPath;
	}
	
	public void setIosPath(String iosPath) {
		this.iosPath = iosPath;
	}
	
	public String getIosVersion() {
		return this.iosVersion;
	}
	
	public void setIosVersion(String iosVersion) {
		this.iosVersion = iosVersion;
	}
	
	public String getIosChangeLog() {
		return this.iosChangeLog;
	}
	
	public void setIosChangeLog(String iosChangeLog) {
		this.iosChangeLog = iosChangeLog;
	}
	
	public int getIosForceUpdate() {
		return this.iosForceUpdate;
	}
	
	public void setIosForceUpdate(int iosForceUpdate) {
		this.iosForceUpdate = iosForceUpdate;
	}
	
	public int getIosOptionUpdate() {
		return this.iosOptionUpdate;
	}
	
	public void setIosOptionUpdate(int iosOptionUpdate) {
		this.iosOptionUpdate = iosOptionUpdate;
	}
	
	public String getIosTwoDimension() {
		return this.iosTwoDimension;
	}
	
	public void setIosTwoDimension(String iosTwoDimension) {
		this.iosTwoDimension = iosTwoDimension;
	}
	
	public String getAndroidPath() {
		return this.androidPath;
	}
	
	public void setAndroidPath(String androidPath) {
		this.androidPath = androidPath;
	}
	
	public String getAndroidVersion() {
		return this.androidVersion;
	}
	
	public void setAndroidVersion(String androidVersion) {
		this.androidVersion = androidVersion;
	}
	
	public String getAndroidChangeLog() {
		return this.androidChangeLog;
	}
	
	public void setAndroidChangeLog(String androidChangeLog) {
		this.androidChangeLog = androidChangeLog;
	}
	
	public int getAndroidForceUpdate() {
		return this.androidForceUpdate;
	}
	
	public void setAndroidForceUpdate(int androidForceUpdate) {
		this.androidForceUpdate = androidForceUpdate;
	}
	
	public int getAndroidOptionUpdate() {
		return this.androidOptionUpdate;
	}
	
	public void setAndroidOptionUpdate(int androidOptionUpdate) {
		this.androidOptionUpdate = androidOptionUpdate;
	}
	
	public String getAndroidTwoDimension() {
		return this.androidTwoDimension;
	}
	
	public void setAndroidTwoDimension(String androidTwoDimension) {
		this.androidTwoDimension = androidTwoDimension;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
}
