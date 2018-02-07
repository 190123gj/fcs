package com.born.fcs.pm.test.bootstrap;

import java.text.ParseException;

import com.born.fcs.pm.util.HttpClientUtil;

public class test {
	public static void main(String[] args) throws ParseException, Exception {
		String resMessage=HttpClientUtil.post("https://www.baidu.com","");   
        System.out.println(resMessage); 
		
	}
}
