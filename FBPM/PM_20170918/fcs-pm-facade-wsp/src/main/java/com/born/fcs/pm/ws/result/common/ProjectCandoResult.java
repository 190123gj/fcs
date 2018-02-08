package com.born.fcs.pm.ws.result.common;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 
 * 项目操作引导结果
 * 
 * 0：当前不可做，1：当前可做，2：当前可重复做，3：已完成
 * 
 * @author wuzj
 */
public class ProjectCandoResult extends FcsBaseResult {
	
	private static final long serialVersionUID = 107929047701277823L;
	
	ProjectInfo project;
	
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("success", isSuccess());
		json.put("message", getMessage());
		json.put("intro", "0：当前不可做，1：当前可做，2：可重复做，3：已完成");
		json.put("projectCode", project.getProjectCode());
		json.put("projectName", project.getProjectName());
		json.put("templete", getTemplete());
		json.put("setup", setup);
		json.put("investigation", investigation);
		json.put("council", council);
		json.put("contract", contract);
		json.put("contract_apply", contract_apply);
		json.put("contract_stamp", contract_stamp);
		json.put("contract_receipt", contract_receipt);
		json.put("creditConfirm", creditConfirm);
		json.put("chargeNotice", chargeNotice);
		json.put("sellConfirm", sellConfirm);
		json.put("loanUse", loanUse);
		json.put("loanUse_apply", loanUse_apply);
		json.put("loanUse_receipt", loanUse_receipt);
		json.put("afterCheck", afterCheck);
		json.put("riskLevel", riskLevel);
		json.put("release", release);
		json.put("expireNotice", expireNotice);
		json.put("riskHandle", riskHandle);
		json.put("riskReport", riskReport);
		json.put("crediInfo", crediInfo);
		json.put("fileInput", fileInput);
		json.put("paper", paper);
		json.put("letter", letter);
		json.put("capital", capital);
		json.put("recovery", recovery);
		return json;
	}
	
	/** 立项申请 */
	private int setup;
	/** 尽职调查报告 */
	private int investigation;
	/** 会议评审 */
	private int council;
	/** 合同管理 */
	private int contract;
	//申请
	private int contract_apply;
	//用印
	private int contract_stamp;
	//回传
	private int contract_receipt;
	/** 授信落实 */
	private int creditConfirm;
	/** 费用收取通知 */
	private int chargeNotice;
	/** 发售信息维护 */
	private int sellConfirm;
	/** 放用款申请 */
	private int loanUse;
	//申请
	private int loanUse_apply;
	//回传
	private int loanUse_receipt;
	/** 保后检查报告 */
	private int afterCheck;
	/** 项目风险等级评定 */
	private int riskLevel;
	/** 解保申请 */
	private int release;
	/** 到期通知 */
	private int expireNotice;
	/** 风险处理 */
	private int riskHandle;
	/** 风险上会申报 */
	private int riskReport;
	/** 征信查询 */
	private int crediInfo = 1;
	/** 资料归档 */
	private int fileInput = 1;
	/** 文书申请 */
	private int paper = 1;
	/** 函、通知书申请 */
	private int letter;
	
	/** 代偿条线 */
	
	/** 资金划付 */
	private int capital;
	/** 追偿 */
	private int recovery;
	
	/**
	 * flow模板
	 * @return
	 */
	public String getTemplete() {
		String templete = "DB"; //担保委贷
		if (project.isUnderwriting()) {
			templete = "CX";
		} else if (project.isBond()) {
			templete = "FZ";
		} else if (project.isLitigation()) {
			templete = "SB";
		}
		return templete;
	}
	
	/**
	 * 禁用所有
	 */
	public void forbiddenAll() {
		setup = 0;
		investigation = 0;
		council = 0;
		contract = 0;
		contract_apply = 0;
		contract_stamp = 0;
		contract_receipt = 0;
		creditConfirm = 0;
		chargeNotice = 0;
		sellConfirm = 0;
		loanUse = 0;
		loanUse_apply = 0;
		loanUse_receipt = 0;
		afterCheck = 0;
		riskLevel = 0;
		release = 0;
		expireNotice = 0;
		riskHandle = 0;
		riskReport = 0;
		crediInfo = 0;
		fileInput = 0;
		paper = 0;
		letter = 0;
		capital = 0;
		recovery = 0;
	}
	
	public ProjectInfo getProject() {
		return this.project;
	}
	
	public void setProject(ProjectInfo project) {
		this.project = project;
	}
	
	public int getSetup() {
		return this.setup;
	}
	
	public void setSetup(int setup) {
		this.setup = setup;
	}
	
	public int getInvestigation() {
		return this.investigation;
	}
	
	public void setInvestigation(int investigation) {
		this.investigation = investigation;
	}
	
	public int getCouncil() {
		return this.council;
	}
	
	public void setCouncil(int council) {
		this.council = council;
	}
	
	public int getContract() {
		return this.contract;
	}
	
	public void setContract(int contract) {
		this.contract = contract;
	}
	
	public int getContract_apply() {
		return this.contract_apply;
	}
	
	public void setContract_apply(int contract_apply) {
		this.contract_apply = contract_apply;
	}
	
	public int getContract_stamp() {
		return this.contract_stamp;
	}
	
	public void setContract_stamp(int contract_stamp) {
		this.contract_stamp = contract_stamp;
	}
	
	public int getContract_receipt() {
		return this.contract_receipt;
	}
	
	public void setContract_receipt(int contract_receipt) {
		this.contract_receipt = contract_receipt;
	}
	
	public int getCreditConfirm() {
		return this.creditConfirm;
	}
	
	public void setCreditConfirm(int creditConfirm) {
		this.creditConfirm = creditConfirm;
	}
	
	public int getChargeNotice() {
		return this.chargeNotice;
	}
	
	public void setChargeNotice(int chargeNotice) {
		this.chargeNotice = chargeNotice;
	}
	
	public int getSellConfirm() {
		return this.sellConfirm;
	}
	
	public void setSellConfirm(int sellConfirm) {
		this.sellConfirm = sellConfirm;
	}
	
	public int getLoanUse() {
		return this.loanUse;
	}
	
	public void setLoanUse(int loanUse) {
		this.loanUse = loanUse;
	}
	
	public int getLoanUse_apply() {
		return this.loanUse_apply;
	}
	
	public void setLoanUse_apply(int loanUse_apply) {
		this.loanUse_apply = loanUse_apply;
	}
	
	public int getLoanUse_receipt() {
		return this.loanUse_receipt;
	}
	
	public void setLoanUse_receipt(int loanUse_receipt) {
		this.loanUse_receipt = loanUse_receipt;
	}
	
	public int getAfterCheck() {
		return this.afterCheck;
	}
	
	public void setAfterCheck(int afterCheck) {
		this.afterCheck = afterCheck;
	}
	
	public int getRiskLevel() {
		return this.riskLevel;
	}
	
	public void setRiskLevel(int riskLevel) {
		this.riskLevel = riskLevel;
	}
	
	public int getRelease() {
		return this.release;
	}
	
	public void setRelease(int release) {
		this.release = release;
	}
	
	public int getExpireNotice() {
		return this.expireNotice;
	}
	
	public void setExpireNotice(int expireNotice) {
		this.expireNotice = expireNotice;
	}
	
	public int getRiskHandle() {
		return this.riskHandle;
	}
	
	public void setRiskHandle(int riskHandle) {
		this.riskHandle = riskHandle;
	}
	
	public int getRiskReport() {
		return this.riskReport;
	}
	
	public void setRiskReport(int riskReport) {
		this.riskReport = riskReport;
	}
	
	public int getCrediInfo() {
		return this.crediInfo;
	}
	
	public void setCrediInfo(int crediInfo) {
		this.crediInfo = crediInfo;
	}
	
	public int getFileInput() {
		return this.fileInput;
	}
	
	public void setFileInput(int fileInput) {
		this.fileInput = fileInput;
	}
	
	public int getPaper() {
		return this.paper;
	}
	
	public void setPaper(int paper) {
		this.paper = paper;
	}
	
	public int getLetter() {
		return this.letter;
	}
	
	public void setLetter(int letter) {
		this.letter = letter;
	}
	
	public int getCapital() {
		return this.capital;
	}
	
	public void setCapital(int capital) {
		this.capital = capital;
	}
	
	public int getRecovery() {
		return this.recovery;
	}
	
	public void setRecovery(int recovery) {
		this.recovery = recovery;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
