package com.born.fcs.rm.ws.info.report.outer;

import com.born.fcs.rm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * W5-（中担协）融资性担保业务情况（表二）<br>
 * W8-（工信部网上直报）业绩财务信息
 * @author wuzj
 */
public class GuaranteeBusiInfoW5Info extends BaseToStringInfo {
	
	private static final long serialVersionUID = 336065869871242045L;
	
	public GuaranteeBusiInfoW5Info() {
	}
	
	public GuaranteeBusiInfoW5Info(String clasifyby) {
		this.clasifyby = clasifyby;
	}
	
	public GuaranteeBusiInfoW5Info(String clasifyby, String classifyType) {
		this.clasifyby = clasifyby;
		this.classifyType = classifyType;
	}
	
	public GuaranteeBusiInfoW5Info(String clasifyby, String classifyType, String classify) {
		this.clasifyby = clasifyby;
		this.classifyType = classifyType;
		this.classify = classify;
	}
	
	/** 是否标题 */
	private boolean title;
	
	/** 大类 */
	private String clasifyby;
	/** 中类 */
	private String classifyType;
	/** 分类小类 */
	private String classify;
	/** 期初在保余额 */
	private Money balanceBeginning = Money.zero();
	/** 期初在保户数 */
	private long zbhsBeginning;
	/** 期初在保笔数 */
	private long zbbsBeginning;
	/** 本期新增 */
	private Money increase = Money.zero();
	/** 本期担保笔数 */
	private long bs;
	/** 本期新增户数 */
	private long hs;
	/** 本期解除担保额 */
	private Money decrease = Money.zero();
	/** 本期减少户数 */
	private long decreaseHs;
	/** 期末在保余额 */
	private Money balanceEnding = Money.zero();
	/** 期末在保户数 */
	private long zbhsEnding;
	/** 期末在保笔数 */
	private long zbbsEnding;
	
	//W8-（工信部网上直报）业绩财务信息
	private String beginning;
	private String ending;
	/** 期初 */
	private Money moneyBeginning = Money.zero();
	/** 期末 */
	private Money moneyEnding = Money.zero();
	/** 新增 */
	private Money moneyIncrease = Money.zero();
	/** 期初 */
	private long numBeginning;
	/** 期末 */
	private long numEnding;
	/** 新增 */
	private long numIncrease;
	
	public String getClasifyby() {
		return clasifyby;
	}
	
	public void setClasifyby(String clasifyby) {
		this.clasifyby = clasifyby;
	}
	
	public String getClassifyType() {
		return classifyType;
	}
	
	public void setClassifyType(String classifyType) {
		this.classifyType = classifyType;
	}
	
	public String getClassify() {
		return classify;
	}
	
	public void setClassify(String classify) {
		this.classify = classify;
	}
	
	public Money getBalanceBeginning() {
		return balanceBeginning;
	}
	
	public void setBalanceBeginning(Money balanceBeginning) {
		this.balanceBeginning = balanceBeginning;
	}
	
	public long getZbhsBeginning() {
		return zbhsBeginning;
	}
	
	public void setZbhsBeginning(long zbhsBeginning) {
		this.zbhsBeginning = zbhsBeginning;
	}
	
	public long getZbbsBeginning() {
		return zbbsBeginning;
	}
	
	public void setZbbsBeginning(long zbbsBeginning) {
		this.zbbsBeginning = zbbsBeginning;
	}
	
	public Money getIncrease() {
		return increase;
	}
	
	public void setIncrease(Money increase) {
		this.increase = increase;
	}
	
	public long getBs() {
		return bs;
	}
	
	public void setBs(long bs) {
		this.bs = bs;
	}
	
	public long getHs() {
		return hs;
	}
	
	public void setHs(long hs) {
		this.hs = hs;
	}
	
	public Money getDecrease() {
		return decrease;
	}
	
	public void setDecrease(Money decrease) {
		this.decrease = decrease;
	}
	
	public long getDecreaseHs() {
		return decreaseHs;
	}
	
	public void setDecreaseHs(long decreaseHs) {
		this.decreaseHs = decreaseHs;
	}
	
	public Money getBalanceEnding() {
		return balanceEnding;
	}
	
	public void setBalanceEnding(Money balanceEnding) {
		this.balanceEnding = balanceEnding;
	}
	
	public long getZbhsEnding() {
		return zbhsEnding;
	}
	
	public void setZbhsEnding(long zbhsEnding) {
		this.zbhsEnding = zbhsEnding;
	}
	
	public long getZbbsEnding() {
		return zbbsEnding;
	}
	
	public void setZbbsEnding(long zbbsEnding) {
		this.zbbsEnding = zbbsEnding;
	}
	
	public String getBeginning() {
		return beginning;
	}
	
	public void setBeginning(String beginning) {
		this.beginning = beginning;
	}
	
	public String getEnding() {
		return ending;
	}
	
	public void setEnding(String ending) {
		this.ending = ending;
	}
	
	public Money getMoneyBeginning() {
		return moneyBeginning;
	}
	
	public void setMoneyBeginning(Money moneyBeginning) {
		this.moneyBeginning = moneyBeginning;
	}
	
	public Money getMoneyEnding() {
		return moneyEnding;
	}
	
	public void setMoneyEnding(Money moneyEnding) {
		this.moneyEnding = moneyEnding;
	}
	
	public Money getMoneyIncrease() {
		return moneyIncrease;
	}
	
	public void setMoneyIncrease(Money moneyIncrease) {
		this.moneyIncrease = moneyIncrease;
	}
	
	public long getNumBeginning() {
		return numBeginning;
	}
	
	public void setNumBeginning(long numBeginning) {
		this.numBeginning = numBeginning;
	}
	
	public long getNumEnding() {
		return numEnding;
	}
	
	public void setNumEnding(long numEnding) {
		this.numEnding = numEnding;
	}
	
	public long getNumIncrease() {
		return numIncrease;
	}
	
	public void setNumIncrease(long numIncrease) {
		this.numIncrease = numIncrease;
	}
	
	public boolean isTitle() {
		return title;
	}
	
	public void setTitle(boolean title) {
		this.title = title;
	}
}
