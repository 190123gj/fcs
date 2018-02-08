package com.bornsoft.utils.tool;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 序列化别名注解
 * @author taibai@yiji.com
 * @date 2016-8-26 上午9:24:40
 * @version V1.0
 */
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SerializeAlias {
	String alias();
	/***是否忽略该字段***/
	boolean ignore() default false;
}
