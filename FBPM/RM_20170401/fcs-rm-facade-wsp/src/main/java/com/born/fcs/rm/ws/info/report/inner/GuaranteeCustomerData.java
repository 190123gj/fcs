package com.born.fcs.rm.ws.info.report.inner;

import com.born.fcs.rm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

public class GuaranteeCustomerData extends BaseToStringInfo {
	
	private static final long serialVersionUID = -709839218282831256L;
	/** 大分类 */
	private String types;
	/** 小分类 */
	private String classify;
	/** 是否主分类 */
	private boolean title = false;
	
	private boolean children = false;
	/** 新增客户 */
	private String xzhs = "0";
	/** 新增项目 */
	private String xzxm = "0";
	/** 在保客户 */
	private String zbhs = "0";
	/** 在保项目 */
	private String zbxm = "0";
	/** 担保合同金额 */
	private Money htAmount = Money.zero();
	/** 期末在保余额 */
	private Money zbAmount = Money.zero();
	/** 年初在保余额 */
	private Money nczbAmount = Money.zero();
	/** 本年累计发生额 */
	private Money fsAmount = Money.zero();
	/** 本年累计解保额 */
	private Money jbAmount = Money.zero();
	/** 发生额占比 */
	private double fszb = 0;
	/** 余额占比 */
	private double yezb = 0;
	
	public boolean isChildren() {
		return this.children;
	}
	
	public void setChildren(boolean children) {
		this.children = children;
	}
	
	public String getTypes() {
		return this.types;
	}
	
	public void setTypes(String types) {
		this.types = types;
	}
	
	public String getClassify() {
		return this.classify;
	}
	
	public void setClassify(String classify) {
		this.classify = classify;
	}
	
	public boolean isTitle() {
		return this.title;
	}
	
	public void setTitle(boolean title) {
		this.title = title;
	}
	
	public String getXzhs() {
		return this.xzhs;
	}
	
	public void setXzhs(String xzhs) {
		this.xzhs = xzhs;
	}
	
	public String getXzxm() {
		return this.xzxm;
	}
	
	public void setXzxm(String xzxm) {
		this.xzxm = xzxm;
	}
	
	public String getZbhs() {
		return this.zbhs;
	}
	
	public void setZbhs(String zbhs) {
		this.zbhs = zbhs;
	}
	
	public String getZbxm() {
		return this.zbxm;
	}
	
	public void setZbxm(String zbxm) {
		this.zbxm = zbxm;
	}
	
	public Money getHtAmount() {
		return this.htAmount;
	}
	
	public void setHtAmount(Money htAmount) {
		this.htAmount = htAmount;
	}
	
	public Money getZbAmount() {
		return this.zbAmount;
	}
	
	public void setZbAmount(Money zbAmount) {
		this.zbAmount = zbAmount;
	}
	
	public Money getNczbAmount() {
		return this.nczbAmount;
	}
	
	public void setNczbAmount(Money nczbAmount) {
		this.nczbAmount = nczbAmount;
	}
	
	public Money getFsAmount() {
		return this.fsAmount;
	}
	
	public void setFsAmount(Money fsAmount) {
		this.fsAmount = fsAmount;
	}
	
	public Money getJbAmount() {
		return this.jbAmount;
	}
	
	public void setJbAmount(Money jbAmount) {
		this.jbAmount = jbAmount;
	}
	
	public double getFszb() {
		return this.fszb;
	}
	
	public void setFszb(double fszb) {
		this.fszb = fszb;
	}
	
	public double getYezb() {
		return this.yezb;
	}
	
	public void setYezb(double yezb) {
		this.yezb = yezb;
	}
	
}