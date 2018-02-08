package com.born.fcs.pm.ws.info.basicmaintain;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 系统配置
 * @author wuzj
 */
public class SysConfigInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 225670477420119075L;
	
	private int configId;
	
	private String faq;
	
	private String manual;
	
	private String video;
	
	private String backgroundImage;
	
	private String info1;
	
	private String info2;
	
	private String info3;
	
	private String info4;
	
	private String info5;
	
	public int getConfigId() {
		return this.configId;
	}
	
	public void setConfigId(int configId) {
		this.configId = configId;
	}
	
	public String getFaq() {
		return this.faq;
	}
	
	public void setFaq(String faq) {
		this.faq = faq;
	}
	
	public String getManual() {
		return this.manual;
	}
	
	public void setManual(String manual) {
		this.manual = manual;
	}
	
	public String getVideo() {
		return this.video;
	}
	
	public void setVideo(String video) {
		this.video = video;
	}
	
	public String getBackgroundImage() {
		return this.backgroundImage;
	}
	
	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
	
	public String getInfo1() {
		return this.info1;
	}
	
	public void setInfo1(String info1) {
		this.info1 = info1;
	}
	
	public String getInfo2() {
		return this.info2;
	}
	
	public void setInfo2(String info2) {
		this.info2 = info2;
	}
	
	public String getInfo3() {
		return this.info3;
	}
	
	public void setInfo3(String info3) {
		this.info3 = info3;
	}
	
	public String getInfo4() {
		return this.info4;
	}
	
	public void setInfo4(String info4) {
		this.info4 = info4;
	}
	
	public String getInfo5() {
		return this.info5;
	}
	
	public void setInfo5(String info5) {
		this.info5 = info5;
	}
}
