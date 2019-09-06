package org.yash.rms.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {

	/**
	 * @return Column Name which is first row of excel
	 */
	public String columnName() default "";

	/**
	 * @return return field name which needs to be mapped with this excel column
	 */
	public String fieldMapTo() default "";
	
}
