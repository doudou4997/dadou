/**
 * 
 */
package com.dadou.sys.login;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * 不需要登录即可执行的Action方法.
 * 
 * 在Action中处理请求的方法上加上此注解后,不会判断必须登录.
 * 
 * @author uu
 * @see {@link com.xq.core.web.action.Action#execute()}
 */
@Documented
@Target({TYPE, METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNecessaryLogin {}

