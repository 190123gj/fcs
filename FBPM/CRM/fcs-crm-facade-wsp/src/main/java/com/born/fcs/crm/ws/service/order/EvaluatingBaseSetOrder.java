package com.born.fcs.crm.ws.service.order;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 评价基础配置
 * 
 * */
public class EvaluatingBaseSetOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -8186947486857577130L;
	/** 主键 */
	private long id;
	/** 1级指标 */
	private long level1Id;
	/** 1级指标名 */
	private String level1Name;
	/** 1级指标说明 */
	private String level1Description;
	/** 1级指标总分 */
	private String level1Score;
	/** 2级指标Id */
	private long level2Id;
	/** 2级指标名 */
	private String level2Name;
	/** 2级指标说明 */
	private String level2Description;
	/** 2级指标总分 */
	private String level2Score;
	/** 3级指标Id */
	private long level3Id;
	/** 3级指标名 */
	private String level3Name;
	/** 3级指标说明 */
	private String level3Description;
	/** 3级指标总分 */
	private String level3Score;
	/** 4级指标Id */
	private long level4Id;
	/** 4级指标名 */
	private String level4Name;
	/** 4级指标说明 */
	private String level4Description;
	/** 4级指标总分 */
	private String level4Score;
	/** 评价内容 */
	private String evaluatingContent;
	/** 评价结果 */
	private String evaluatingResult;
	/** 得分 */
	private String score;
	/** 标准值 */
	private String standardValue;
	/** 比较方式（大于，等于，小于..） */
	private String compareMethod;
	/** 比较方式2 */
	private String compareMethod2;
	/** 比较方式3 */
	private String compareMethod3;
	/** 与标准值比较值 */
	private String compareStandardValue;
	/** 评分标准值 */
	private String evaluatingStandardValue;
	/** 与评分标准值比较值 */
	private String compareEvaluatingStandardValue;
	/** 计算公式 */
	private String calculatingFormula;
	/** 指标等级 */
	private String level;
	/** 父类指标id */
	private long perentLevel;
	/** 指标类型 */
	private String type;
	/** 指标状态 */
	private String status;
	//	/** 正式数据id */
	//	private long perentId;
	//	/** 暂存数据 */
	//	private long childId;
	//	/** 是否暂存数据 */
	//	private String isTemporary;
	/** 创建时间 */
	private Date rawAddTime;
	/** 更新时间 */
	private Date rawUpdateTime;
	/** 1级排序 */
	private long orderNum1;
	/** 2级排序 */
	private long orderNum2;
	/** 3级排序 */
	private long orderNum3;
	/** 4级排序 */
	private long orderNum4;
	
	public long getOrderNum1() {
		return this.orderNum1;
	}
	
	public void setOrderNum1(long orderNum1) {
		this.orderNum1 = orderNum1;
	}
	
	public long getOrderNum2() {
		return this.orderNum2;
	}
	
	public void setOrderNum2(long orderNum2) {
		this.orderNum2 = orderNum2;
	}
	
	public long getOrderNum3() {
		return this.orderNum3;
	}
	
	public void setOrderNum3(long orderNum3) {
		this.orderNum3 = orderNum3;
	}
	
	public long getOrderNum4() {
		return this.orderNum4;
	}
	
	public void setOrderNum4(long orderNum4) {
		this.orderNum4 = orderNum4;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getLevel1Id() {
		return this.level1Id;
	}
	
	public void setLevel1Id(long level1Id) {
		this.level1Id = level1Id;
	}
	
	public String getLevel1Name() {
		return this.level1Name;
	}
	
	public void setLevel1Name(String level1Name) {
		this.level1Name = level1Name;
	}
	
	public String getLevel1Description() {
		return this.level1Description;
	}
	
	public void setLevel1Description(String level1Description) {
		this.level1Description = level1Description;
	}
	
	public String getLevel1Score() {
		return this.level1Score;
	}
	
	public void setLevel1Score(String level1Score) {
		this.level1Score = level1Score;
	}
	
	public long getLevel2Id() {
		return this.level2Id;
	}
	
	public void setLevel2Id(long level2Id) {
		this.level2Id = level2Id;
	}
	
	public String getLevel2Name() {
		return this.level2Name;
	}
	
	public void setLevel2Name(String level2Name) {
		this.level2Name = level2Name;
	}
	
	public String getLevel2Description() {
		return this.level2Description;
	}
	
	public void setLevel2Description(String level2Description) {
		this.level2Description = level2Description;
	}
	
	public String getLevel2Score() {
		return this.level2Score;
	}
	
	public void setLevel2Score(String level2Score) {
		this.level2Score = level2Score;
	}
	
	public long getLevel3Id() {
		return this.level3Id;
	}
	
	public void setLevel3Id(long level3Id) {
		this.level3Id = level3Id;
	}
	
	public String getLevel3Name() {
		return this.level3Name;
	}
	
	public void setLevel3Name(String level3Name) {
		this.level3Name = level3Name;
	}
	
	public String getLevel3Description() {
		return this.level3Description;
	}
	
	public void setLevel3Description(String level3Description) {
		this.level3Description = level3Description;
	}
	
	public String getLevel3Score() {
		return this.level3Score;
	}
	
	public void setLevel3Score(String level3Score) {
		this.level3Score = level3Score;
	}
	
	public long getLevel4Id() {
		return this.level4Id;
	}
	
	public void setLevel4Id(long level4Id) {
		this.level4Id = level4Id;
	}
	
	public String getLevel4Name() {
		return this.level4Name;
	}
	
	public void setLevel4Name(String level4Name) {
		this.level4Name = level4Name;
	}
	
	public String getLevel4Description() {
		return this.level4Description;
	}
	
	public void setLevel4Description(String level4Description) {
		this.level4Description = level4Description;
	}
	
	public String getLevel4Score() {
		return this.level4Score;
	}
	
	public void setLevel4Score(String level4Score) {
		this.level4Score = level4Score;
	}
	
	public String getEvaluatingContent() {
		return this.evaluatingContent;
	}
	
	public void setEvaluatingContent(String evaluatingContent) {
		this.evaluatingContent = evaluatingContent;
	}
	
	public String getEvaluatingResult() {
		return this.evaluatingResult;
	}
	
	public void setEvaluatingResult(String evaluatingResult) {
		this.evaluatingResult = evaluatingResult;
	}
	
	public String getScore() {
		return this.score;
	}
	
	public void setScore(String score) {
		this.score = score;
	}
	
	public String getStandardValue() {
		return this.standardValue;
	}
	
	public void setStandardValue(String standardValue) {
		this.standardValue = standardValue;
	}
	
	public String getCompareMethod() {
		return this.compareMethod;
	}
	
	public void setCompareMethod(String compareMethod) {
		this.compareMethod = compareMethod;
	}
	
	public String getCompareMethod2() {
		return this.compareMethod2;
	}
	
	public void setCompareMethod2(String compareMethod2) {
		this.compareMethod2 = compareMethod2;
	}
	
	public String getCompareMethod3() {
		return this.compareMethod3;
	}
	
	public void setCompareMethod3(String compareMethod3) {
		this.compareMethod3 = compareMethod3;
	}
	
	public String getCompareStandardValue() {
		return this.compareStandardValue;
	}
	
	public void setCompareStandardValue(String compareStandardValue) {
		this.compareStandardValue = compareStandardValue;
	}
	
	public String getEvaluatingStandardValue() {
		return this.evaluatingStandardValue;
	}
	
	public void setEvaluatingStandardValue(String evaluatingStandardValue) {
		this.evaluatingStandardValue = evaluatingStandardValue;
	}
	
	public String getCompareEvaluatingStandardValue() {
		return this.compareEvaluatingStandardValue;
	}
	
	public void setCompareEvaluatingStandardValue(String compareEvaluatingStandardValue) {
		this.compareEvaluatingStandardValue = compareEvaluatingStandardValue;
	}
	
	public String getCalculatingFormula() {
		return this.calculatingFormula;
	}
	
	public void setCalculatingFormula(String calculatingFormula) {
		this.calculatingFormula = calculatingFormula;
	}
	
	public String getLevel() {
		return this.level;
	}
	
	public void setLevel(String level) {
		this.level = level;
	}
	
	public long getPerentLevel() {
		return this.perentLevel;
	}
	
	public void setPerentLevel(long perentLevel) {
		this.perentLevel = perentLevel;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	//	public long getPerentId() {
	//		return this.perentId;
	//	}
	//	
	//	public void setPerentId(long perentId) {
	//		this.perentId = perentId;
	//	}
	//	
	//	public long getChildId() {
	//		return this.childId;
	//	}
	//	
	//	public void setChildId(long childId) {
	//		this.childId = childId;
	//	}
	//	
	//	public String getIsTemporary() {
	//		return this.isTemporary;
	//	}
	//	
	//	public void setIsTemporary(String isTemporary) {
	//		this.isTemporary = isTemporary;
	//	}
	
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
