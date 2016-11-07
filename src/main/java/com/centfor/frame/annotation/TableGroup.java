
/**
 * 
 */
package com.centfor.frame.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *  用于Entity的注解,主要是用于映射表名和设置扩展后缀
 * @copyright {@link 9iu.org}
 * @author centfor<9iuorg@gmail.com>
 * @version  2013-03-19 11:08:15
 * @see com.centfor.frame.annotation.TableGroup
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TableGroup  {
	/**
	 * 扩展后缀,默认为空,可以指定字段 例如 ext 的getExt() 返回值是 _history_2013
	 * @return
	 */
	String name() default "";  

}
