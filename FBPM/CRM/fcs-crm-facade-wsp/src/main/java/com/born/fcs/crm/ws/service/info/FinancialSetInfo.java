package com.born.fcs.crm.ws.service.info;

import java.io.Serializable;
import java.util.Date;

/**
 * 一般企业财务指标配置
 * */
public class FinancialSetInfo implements Serializable {
	private static final long serialVersionUID = -1665856605894756839L;
	/** 主键 */
	private long id;
	/** 配置年限 */
	private String year;
	/** 字段1 */
	private String string1;
	/** 字段2 */
	private String string2;
	/** 字段3 */
	private String string3;
	/** 字段4 */
	private String string4;
	/** 字段5 */
	private String string5;
	/** 字段6 */
	private String string6;
	/** 字段7 */
	private String string7;
	/** 字段8 */
	private String string8;
	/** 字段9 */
	private String string9;
	/** 字段10 */
	private String string10;
	/** 字段11 */
	private String string11;
	/** 字段12 */
	private String string12;
	/** 字段13 */
	private String string13;
	/** 字段14 */
	private String string14;
	/** 字段15 */
	private String string15;
	/** 字段16 */
	private String string16;
	/** 字段17 */
	private String string17;
	/** 字段18 */
	private String string18;
	/** 字段19 */
	private String string19;
	/** 字段20 */
	private String string20;
	/** 字段21 */
	private String string21;
	/** 计算公式 */
	private String calculatingFormula;
	/** 改类型下小分类 */
	private String typeChild;
	/** 财务指标类型 */
	private String type;
	/***/
	private Date rowAddTime;
	/***/
	private Date rawUpdateTime;
	
	/** 实际值 */
	private String actualValue;
	/** 当前得分 */
	private String score;
	
	public String getActualValue() {
		return this.actualValue;
	}
	
	public void setActualValue(String actualValue) {
		this.actualValue = actualValue;
	}
	
	public String getScore() {
		return this.score;
	}
	
	public void setScore(String score) {
		this.score = score;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getYear() {
		return this.year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getString1() {
		return this.string1;
	}
	
	public void setString1(String string1) {
		this.string1 = string1;
	}
	
	public String getString2() {
		return this.string2;
	}
	
	public void setString2(String string2) {
		this.string2 = string2;
	}
	
	public String getString3() {
		return this.string3;
	}
	
	public void setString3(String string3) {
		this.string3 = string3;
	}
	
	public String getString4() {
		return this.string4;
	}
	
	public void setString4(String string4) {
		this.string4 = string4;
	}
	
	public String getString5() {
		return this.string5;
	}
	
	public void setString5(String string5) {
		this.string5 = string5;
	}
	
	public String getString6() {
		return this.string6;
	}
	
	public void setString6(String string6) {
		this.string6 = string6;
	}
	
	public String getString7() {
		return this.string7;
	}
	
	public void setString7(String string7) {
		this.string7 = string7;
	}
	
	public String getString8() {
		return this.string8;
	}
	
	public void setString8(String string8) {
		this.string8 = string8;
	}
	
	public String getString9() {
		return this.string9;
	}
	
	public void setString9(String string9) {
		this.string9 = string9;
	}
	
	public String getString10() {
		return this.string10;
	}
	
	public void setString10(String string10) {
		this.string10 = string10;
	}
	
	public String getString11() {
		return this.string11;
	}
	
	public void setString11(String string11) {
		this.string11 = string11;
	}
	
	public String getString12() {
		return this.string12;
	}
	
	public void setString12(String string12) {
		this.string12 = string12;
	}
	
	public String getString13() {
		return this.string13;
	}
	
	public void setString13(String string13) {
		this.string13 = string13;
	}
	
	public String getString14() {
		return this.string14;
	}
	
	public void setString14(String string14) {
		this.string14 = string14;
	}
	
	public String getString15() {
		return this.string15;
	}
	
	public void setString15(String string15) {
		this.string15 = string15;
	}
	
	public String getString16() {
		return this.string16;
	}
	
	public void setString16(String string16) {
		this.string16 = string16;
	}
	
	public String getString17() {
		return this.string17;
	}
	
	public void setString17(String string17) {
		this.string17 = string17;
	}
	
	public String getString18() {
		return this.string18;
	}
	
	public void setString18(String string18) {
		this.string18 = string18;
	}
	
	public String getString19() {
		return this.string19;
	}
	
	public void setString19(String string19) {
		this.string19 = string19;
	}
	
	public String getString20() {
		return this.string20;
	}
	
	public void setString20(String string20) {
		this.string20 = string20;
	}
	
	public String getString21() {
		return this.string21;
	}
	
	public void setString21(String string21) {
		this.string21 = string21;
	}
	
	public String getCalculatingFormula() {
		return this.calculatingFormula;
	}
	
	public void setCalculatingFormula(String calculatingFormula) {
		this.calculatingFormula = calculatingFormula;
	}
	
	public String getTypeChild() {
		return this.typeChild;
	}
	
	public void setTypeChild(String typeChild) {
		this.typeChild = typeChild;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Date getRowAddTime() {
		return this.rowAddTime;
	}
	
	public void setRowAddTime(Date rowAddTime) {
		this.rowAddTime = rowAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FinancialSetInfo [id=");
		builder.append(id);
		builder.append(", year=");
		builder.append(year);
		builder.append(", string1=");
		builder.append(string1);
		builder.append(", string2=");
		builder.append(string2);
		builder.append(", string3=");
		builder.append(string3);
		builder.append(", string4=");
		builder.append(string4);
		builder.append(", string5=");
		builder.append(string5);
		builder.append(", string6=");
		builder.append(string6);
		builder.append(", string7=");
		builder.append(string7);
		builder.append(", string8=");
		builder.append(string8);
		builder.append(", string9=");
		builder.append(string9);
		builder.append(", string10=");
		builder.append(string10);
		builder.append(", string11=");
		builder.append(string11);
		builder.append(", string12=");
		builder.append(string12);
		builder.append(", string13=");
		builder.append(string13);
		builder.append(", string14=");
		builder.append(string14);
		builder.append(", string15=");
		builder.append(string15);
		builder.append(", string16=");
		builder.append(string16);
		builder.append(", string17=");
		builder.append(string17);
		builder.append(", string18=");
		builder.append(string18);
		builder.append(", string19=");
		builder.append(string19);
		builder.append(", string20=");
		builder.append(string20);
		builder.append(", string21=");
		builder.append(string21);
		builder.append(", calculatingFormula=");
		builder.append(calculatingFormula);
		builder.append(", typeChild=");
		builder.append(typeChild);
		builder.append(", type=");
		builder.append(type);
		builder.append(", rowAddTime=");
		builder.append(rowAddTime);
		builder.append(", rawUpdateTime=");
		builder.append(rawUpdateTime);
		builder.append(", actualValue=");
		builder.append(actualValue);
		builder.append(", score=");
		builder.append(score);
		builder.append("]");
		return builder.toString();
	}
	
}
