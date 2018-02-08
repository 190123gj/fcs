package com.bornsoft.integration.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description:需要打印IntegrationLog的日志定义(如果方法返回void，就要将returnVoid=true)
 * @author xiaohui@yiji.com
 * @date 2015-11-28 下午1:45:26 
 * @version V1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IntegrationLog {
	
	/**
	 * 拦载的方法，是否返回值为void
	 */
	boolean returnVoid() default false;
}
