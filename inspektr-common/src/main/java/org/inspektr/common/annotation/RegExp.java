package org.inspektr.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows you to specify a regular expression rule.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RegExp {

	String rule();
}
