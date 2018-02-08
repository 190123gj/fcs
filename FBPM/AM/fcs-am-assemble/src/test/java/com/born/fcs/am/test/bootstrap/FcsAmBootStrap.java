package com.born.fcs.am.test.bootstrap;

import com.yjf.common.test.TomcatBootstrapHelper;

/**
 * 
 * 
 * @Filename YRDLoanBootStrap.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2014-4-2</li> <li>Version: 1.0</li>
 *          <li>Content: create</li>
 * 
 */
public class FcsAmBootStrap {

	public static void main(String[] args) {
		new TomcatBootstrapHelper(8082, false, "dev").start();
	}

}