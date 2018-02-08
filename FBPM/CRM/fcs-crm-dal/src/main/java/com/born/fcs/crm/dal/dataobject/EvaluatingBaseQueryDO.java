package com.born.fcs.crm.dal.dataobject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EvaluatingBaseQueryDO extends EvaluatingBaseSetDO {
	
	private static final long serialVersionUID = 6991349685972603271L;
	private String level3Names;
	private String level3Descriptions;
	private String level3Scores;
	private String level4Names;
	private String level4Descriptions;
	private String level4Scores;
	private String compareMethods;
	private String compareStandardValues;
	private String evaluatingContents;
	/** 合并第三级 */
	private String[] level3NameList;
	private String[] level3DescriptionList;
	private String[] level3ScoreList;
	/** 合并第四级 */
	private String[] level4NameList;
	private String[] level4DescriptionList;
	private String[] level4ScoreList;
	
	//公用事业类 行业 
	private String[] evaluatingContentList;
	//比较符号
	private String[] compareMethodsList;
	
	//比较值
	private String[] compareStandardValuesList;
	
	/** 比较公式： 实际值 比较符号 比较值 ？得分 结果为-999.0 表示比较部分没有满足的，需要使用计算公式 ；多个公式以;隔开 */
	private String calculatingFormula_ctbz;
	
	/** 城市开发类有标准值部分计算 ,有行业区分的key为行业，没行业的key值为why */
	private Map<String, String> calculatingFormula_gybz;
	
	public String getEvaluatingContents() {
		return this.evaluatingContents;
	}
	
	public void setEvaluatingContents(String evaluatingContents) {
		this.evaluatingContents = evaluatingContents;
	}
	
	public String[] getCompareMethodsList() {
		String[] list = null;
		String datas = getCompareMethods();
		if (datas != null && datas != "") {
			list = datas.split(";");
		}
		return list;
	}
	
	public void setCompareMethodsList(String[] compareMethodsList) {
		this.compareMethodsList = compareMethodsList;
	}
	
	public String[] getCompareStandardValuesList() {
		String[] list = null;
		String datas = getCompareStandardValues();
		if (datas != null && datas != "") {
			list = datas.replaceAll("万元", "").replaceAll("%", "").replaceAll("元", "").split(";");
		}
		return list;
	}
	
	public void setCompareStandardValuesList(String[] compareStandardValuesList) {
		this.compareStandardValuesList = compareStandardValuesList;
	}
	
	public String[] getEvaluatingContentList() {
		String[] list = null;
		String datas = getEvaluatingContents();
		if (datas != null && datas != "") {
			list = datas.split(";");
		}
		return list;
	}
	
	public void setEvaluatingContentList(String[] evaluatingContentList) {
		this.evaluatingContentList = evaluatingContentList;
	}
	
	public String getLevel3Names() {
		return this.level3Names;
	}
	
	public void setLevel3Names(String level3Names) {
		this.level3Names = level3Names;
	}
	
	public String getLevel3Descriptions() {
		return this.level3Descriptions;
	}
	
	public void setLevel3Descriptions(String level3Descriptions) {
		this.level3Descriptions = level3Descriptions;
	}
	
	public String getLevel3Scores() {
		return this.level3Scores;
	}
	
	public void setLevel3Scores(String level3Scores) {
		this.level3Scores = level3Scores;
	}
	
	public String getLevel4Names() {
		return this.level4Names;
	}
	
	public void setLevel4Names(String level4Names) {
		this.level4Names = level4Names;
	}
	
	public String getLevel4Descriptions() {
		return this.level4Descriptions;
	}
	
	public void setLevel4Descriptions(String level4Descriptions) {
		this.level4Descriptions = level4Descriptions;
	}
	
	public String getLevel4Scores() {
		return this.level4Scores;
	}
	
	public void setLevel4Scores(String level4Scores) {
		this.level4Scores = level4Scores;
	}
	
	public String getCompareMethods() {
		return this.compareMethods;
	}
	
	public void setCompareMethods(String compareMethods) {
		this.compareMethods = compareMethods;
	}
	
	public String getCompareStandardValues() {
		return this.compareStandardValues;
	}
	
	public void setCompareStandardValues(String compareStandardValues) {
		this.compareStandardValues = compareStandardValues;
	}
	
	public void setLevel3NameList(String[] level3NameList) {
		this.level3NameList = level3NameList;
	}
	
	public void setLevel3DescriptionList(String[] level3DescriptionList) {
		this.level3DescriptionList = level3DescriptionList;
	}
	
	public void setLevel3ScoreList(String[] level3ScoreList) {
		this.level3ScoreList = level3ScoreList;
	}
	
	public void setLevel4NameList(String[] level4NameList) {
		this.level4NameList = level4NameList;
	}
	
	public void setLevel4DescriptionList(String[] level4DescriptionList) {
		this.level4DescriptionList = level4DescriptionList;
	}
	
	public void setLevel4ScoreList(String[] level4ScoreList) {
		this.level4ScoreList = level4ScoreList;
	}
	
	public void setCalculatingFormula_ctbz(String calculatingFormula_ctbz) {
		this.calculatingFormula_ctbz = calculatingFormula_ctbz;
	}
	
	public String[] getLevel3NameList() {
		String[] list = null;
		String datas = getLevel3Names();
		if (datas != null && datas != "") {
			list = datas.split(";");
		}
		return list;
	}
	
	public String[] getLevel3DescriptionList() {
		String[] list = null;
		String datas = getLevel3Descriptions();
		if (datas != null && datas != "") {
			list = datas.split(";");
		}
		return list;
	}
	
	public String[] getLevel3ScoreList() {
		String[] list = null;
		String datas = getLevel3Scores();
		if (datas != null && datas != "") {
			list = datas.split(";");
		}
		return list;
	}
	
	public String[] getLevel4NameList() {
		String[] list = null;
		String datas = getLevel4Names();
		if (datas != null && datas != "") {
			list = datas.split(";");
		}
		return list;
	}
	
	public String[] getLevel4DescriptionList() {
		String[] list = null;
		String datas = getLevel4Descriptions();
		if (datas != null && datas != "") {
			list = datas.split(";");
		}
		return list;
	}
	
	public String[] getLevel4ScoreList() {
		String[] list = null;
		String datas = getLevel4Scores();
		if (datas != null && datas != "") {
			list = datas.split(";");
		}
		return list;
	}
	
	/** 城市开发类，标准值/财务部分 多个公式以;隔开 */
	public String getCalculatingFormula_ctbz() {
		String calF = "";//计算公式
		
		String a = getCompareMethods();
		String b = getCompareStandardValues();
		String c = getLevel3Scores();
		if (getCompareMethods() != null && getCompareStandardValues() != null
			&& getLevel3Scores() != null) {
			String[] compareMethod = compareMethods.split(";");
			String[] compareStandardValue = compareStandardValues.split(";");
			String[] level3Score = level3Scores.split(";");
			if (compareMethod.length == compareStandardValue.length
				&& compareMethod.length == level3Score.length) {
				
				for (int i = 0; i < compareMethod.length; i++) {
					//比较公式： 实际值 比较符号 比较值 ？得分     结果为999.0 表示比较部分没有满足的，需要使用计算公式
					String cal = "(actualValue)compareMethod compareStandardValue?level3Score:-999.0";
					cal = cal.replace("compareMethod", compareMethod[i])
						.replace("compareStandardValue", compareStandardValue[i])
						.replace("level3Score", level3Score[i]).replace("%", "");
					if (calF.length() > 0)
						calF += ";" + cal;
					else
						calF += cal;
				}
				if ("CTCW".equals(getType()) && calF.length() > 0
					&& getCalculatingFormula() != null && getCalculatingFormula() != ""
					&& compareStandardValue.length > 0) {
					//城市开发财务类没标准值，取比较值最小的一个
					String[] csValue0 = compareStandardValues.replaceAll("%", "").split(";");
					double d = 99999;
					for (String s : csValue0) {
						d = Math.min(d, Double.parseDouble(s));
					}
					calF += ";" + getCalculatingFormula().replaceAll("标准值", String.valueOf(d));
				}
				
			}
		}
		
		return calF.replaceAll("÷", "/").replaceAll("×", "*").replaceAll("X", "*")
			.replaceAll("（", "(").replaceAll("）", ")").replaceAll("实际值", "actualValue")
			.replaceAll("标准值", getStandardValue()).replaceAll("≤", "<=").replaceAll("≥", ">=")
			.replaceAll("【", "[").replaceAll("】", "]").replaceAll("标准分", getLevel2Score());
	}
	
	public Map<String, String> getCalculatingFormula_gybz() {
		
		Map<String, String> map = new HashMap<>();
		//行业
		String[] a = getEvaluatingContentList();
		//比较符号
		String[] b = getCompareMethodsList();
		//比较值
		String[] c = getCompareStandardValuesList();
		//得分
		String[] d = getLevel3ScoreList();
		
		if (a != null && a.length > 1) {
			//多行业
			if (b != null && c != null && d != null && a.length == b.length && b.length == c.length
				&& c.length == d.length) {
				String cal = "";
				for (int i = 0; i < b.length && i < c.length && i < c.length; i++) {
					cal += "actualValue" + b[i] + c[i] + "?" + d[i] + ":-999";
					if (getCalculatingFormula() != null
						&& (getCalculatingFormula().indexOf("标准值") > -1 || getCalculatingFormula()
							.indexOf("满分值") > -1)) {
						//计算公式
						String cal1 = getCalculatingFormula().replaceAll("÷", "/")
							.replaceAll("×", "*").replaceAll("X", "*").replaceAll("（", "(")
							.replaceAll("）", ")").replaceAll("实际值", "actualValue")
							.replaceAll("满分值", c[i]).replaceAll("标准值", c[i]).replaceAll("≤", "<=")
							.replaceAll("≥", ">=").replaceAll("【", "[").replaceAll("】", "]")
							.replaceAll("标准分", getLevel2Score());
						
						cal += ";" + cal1;
					}
					map.put(a[i], cal);
					cal = "";
				}
				
			}
		} else {
			//无行业区分
			if (b != null && c != null && d != null && b.length == c.length && c.length == d.length) {
				//多行业
				String cal = "";
				for (int i = 0; i < b.length && i < c.length && i < c.length; i++) {
					if (cal != "") {
						cal += ";actualValue" + b[i] + c[i] + "?" + d[i] + ":-999";
					} else {
						cal += "actualValue" + b[i] + c[i] + "?" + d[i] + ":-999";
					}
					
				}
				if (getCalculatingFormula() != null
					&& (getCalculatingFormula().indexOf("标准值") > -1 || getCalculatingFormula()
						.indexOf("满分值") > -1)) {
					//计算公式
					String cal1 = getCalculatingFormula().replaceAll("÷", "/").replaceAll("×", "*")
						.replaceAll("X", "*").replaceAll("（", "(").replaceAll("）", ")")
						.replaceAll("实际值", "actualValue").replaceAll("标准值", c[0])
						.replaceAll("≤", "<=").replaceAll("≥", ">=").replaceAll("【", "[")
						.replaceAll("】", "]").replaceAll("标准分", getLevel2Score())
						.replaceAll("满分值", c[0]);
					
					cal += ";" + cal1;
				}
				map.put("why", cal);
				
			}
			
		}
		
		return map;
	}
	
	public void setCalculatingFormula_gybz(Map<String, String> calculatingFormula_gybz) {
		this.calculatingFormula_gybz = calculatingFormula_gybz;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EvaluatingBaseQueryDO [level3Names=");
		builder.append(level3Names);
		builder.append(", level3Descriptions=");
		builder.append(level3Descriptions);
		builder.append(", level3Scores=");
		builder.append(level3Scores);
		builder.append(", level4Names=");
		builder.append(level4Names);
		builder.append(", level4Descriptions=");
		builder.append(level4Descriptions);
		builder.append(", level4Scores=");
		builder.append(level4Scores);
		builder.append(", compareMethods=");
		builder.append(compareMethods);
		builder.append(", compareStandardValues=");
		builder.append(compareStandardValues);
		builder.append(", level3NameList=");
		builder.append(Arrays.toString(level3NameList));
		builder.append(", level3DescriptionList=");
		builder.append(Arrays.toString(level3DescriptionList));
		builder.append(", level3ScoreList=");
		builder.append(Arrays.toString(level3ScoreList));
		builder.append(", level4NameList=");
		builder.append(Arrays.toString(level4NameList));
		builder.append(", level4DescriptionList=");
		builder.append(Arrays.toString(level4DescriptionList));
		builder.append(", level4ScoreList=");
		builder.append(Arrays.toString(level4ScoreList));
		builder.append(", calculatingFormula_ctbz=");
		builder.append(calculatingFormula_ctbz);
		builder.append("]");
		return builder.toString();
	}
	
}
