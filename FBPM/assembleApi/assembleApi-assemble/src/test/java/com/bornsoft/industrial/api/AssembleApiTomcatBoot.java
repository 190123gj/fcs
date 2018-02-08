/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.bornsoft.industrial.api;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardThreadExecutor;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.scan.StandardJarScanner;

/**
 * @Description:industrialApi启动
 * @author xiaohui@yiji.com
 * @date 2015-11-10 下午2:31:44 
 * @version V1.0
 */
public final class AssembleApiTomcatBoot {
	private static int TOMCAT_PORT = 8001;

	private static boolean SERVLET_SCAN_ENABLE = false;

	public static void main(String[] args) throws UnsupportedEncodingException {
		System.setProperty("spring.profiles.active", "test"); // net, online
		new AssembleApiTomcatBoot().start();
	}

	public void start() {

		try {
			long begin = System.currentTimeMillis();
			Tomcat tomcat = new Tomcat();
			configTomcat(tomcat);
			tomcat.start();
			long end = System.currentTimeMillis();
			log(end - begin);
			do {
				int c = System.in.read();
				if (c == '\n') {
					tomcat.stop();
					tomcat.start();
				} else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
					tomcat.stop();
					break;
				}
			} while (true);
		} catch (Exception e) {
			System.err.println("非常抱歉，貌似启动挂了 :<");
		}

	}

	private void configTomcat(final Tomcat tomcat) throws ServletException, URISyntaxException {
		//设置tomcat工作目录，maven工程里面就放到target目录下，看起来爽点，注意，这行代码不要随便移动位置，不然你可以have a try。
		String webAppPath = getWebAppsPath();
		String targetPath = getTomcatTargetPath();
		System.out.println("tomcat target 目录：" + targetPath);
		System.out.println("   webAppPath 目录：" + webAppPath);

		tomcat.setBaseDir(targetPath);
		tomcat.setPort(TOMCAT_PORT);

		StandardThreadExecutor executor = new StandardThreadExecutor();
		executor.setName("tomcat-thread-pool");
		executor.setMaxThreads(50);
		executor.setMinSpareThreads(4);
		executor.setNamePrefix("catalina-exec-");
		executor.setMaxQueueSize(1024);
		executor.setMaxIdleTime(1000 * 120);

		tomcat.getService().addExecutor(executor);

		Connector connector = new Connector("HTTP/1.1");
		connector.setPort(TOMCAT_PORT);
		connector.setURIEncoding("UTF-8");
		connector.setEnableLookups(false);

		tomcat.setConnector(connector);
		tomcat.getService().addConnector(connector);

		Context ctx = tomcat.addWebapp("/", webAppPath);

		if (!SERVLET_SCAN_ENABLE) {
			StandardJarScanner scanner = (StandardJarScanner) ctx.getJarScanner();
			scanner.setScanAllDirectories(false);
			scanner.setScanClassPath(false);
		}
		tomcat.setSilent(true);
		System.setProperty("org.apache.catalina.SESSION_COOKIE_NAME", "JSESSIONID" + TOMCAT_PORT);
	}

	private void log(long time) {
		System.out.println("==========================================================================");
		System.out.println("启动成功: http://127.0.0.1:" + TOMCAT_PORT + "   use :" + time + "ms");
		System.out.println("==========================================================================");
	}

	public String getWebAppsPath() throws URISyntaxException {
		Path path = Paths.get(AssembleApiTomcatBoot.class.getResource("/").toURI());
		return path.getParent().resolveSibling("src/main/webapp").toString();    // ../../src/main/webapp
	}

	public String getTomcatTargetPath() throws URISyntaxException {
		Path path = Paths.get(AssembleApiTomcatBoot.class.getResource("/").toURI());  // ..
		return path.getParent().toString();
	}
}
