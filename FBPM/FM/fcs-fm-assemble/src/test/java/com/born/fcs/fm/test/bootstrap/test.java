package com.born.fcs.fm.test.bootstrap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class test {
	public static void main(String[] args) {
		
		/*String code = "/backstage/offlineLoanDemand";
		Pattern p = Pattern.compile(code+"*".replace("*", ".*").replace("?", "\\?"));
		Matcher matcher = p.matcher(code);
		if (matcher.matches()) {
			System.out.println(1);
		} else {
			System.out.println(0);
		}*/
		
		/*	List<String> councilJudges = Lists.newArrayList();
			
			councilJudges.add("one");
			councilJudges.add("two");
			councilJudges.add("three");
			councilJudges.add("four");
			
			Iterator<String> iter = councilJudges.iterator();
			while (iter.hasNext()) {
				String judgeInfo = iter.next();
				
				if ("three".endsWith(judgeInfo)) {
					iter.remove();
				}
			}
			iter = councilJudges.iterator();
			while (iter.hasNext()) {
				String judgeInfo = iter.next();
				
				if ("four".endsWith(judgeInfo)) {
					iter.remove();
				}
			}
			
			System.out.println(councilJudges);*/
		
		HashMap<String, Map<String, Object>> names = new HashMap<String, Map<String, Object>>();
		
		Map<String, Object> yearValueList = new HashMap<String, Object>();
		
		yearValueList.put("2015", "X");
		yearValueList.put("2016", "Y");
		yearValueList.put("2017", "Z");
		
		names.put("纳税", yearValueList);
		
		Map<String, Object> yearValueList2 = new HashMap<String, Object>();
		
		yearValueList2.put("2015", "Z");
		yearValueList2.put("2016", "X");
		yearValueList2.put("2017", "Y");
		
		names.put("银行流入", yearValueList2);
		
		Set<String> keys = names.keySet();
		
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String name = it.next();
			Map<String, Object> yearValues = names.get(name);
			System.out.print(name + " ");
			Set<String> keys2 = yearValues.keySet();
			Iterator<String> it2 = keys2.iterator();
			while (it2.hasNext()) {
				String year = it2.next();
				System.out.print(yearValues.get(year) + " ");
			}
			System.out.println();
			
		}
		
	}
}
