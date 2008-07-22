package org.inspektr.common.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Abstract class that provides most of the boilerplate code for validating the annotations.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
public abstract class AbstractAnnotationValidator implements
		AnnotationValidator {

	public final void validate(final Field field, final Annotation annotation, final Object bean,
			final String beanName) throws IllegalAccessException {
		validateInternal(annotation, field.get(bean), "Field", field.getName(), beanName);
	}

	public final void validate(final Method method, final Annotation annotation, final Object arg, final int argIndex) {
		validateInternal(annotation, arg, "Parameter", "argument[" + argIndex + "]", method.getName());
	}
	
	protected abstract void validateInternal(Annotation annotation, Object arg, String type, String fieldName, String objectName);
}
