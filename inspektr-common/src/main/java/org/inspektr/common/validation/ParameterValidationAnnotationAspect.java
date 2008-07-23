/**
 *  Copyright 2007 Rutgers, the State University of New Jersey
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *      
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.inspektr.common.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.inspektr.common.annotation.GreaterThan;
import org.inspektr.common.annotation.IsIn;
import org.inspektr.common.annotation.NotEmpty;
import org.inspektr.common.annotation.NotNull;

/**
 * Aspect that will read the parameters for the method and validate them against the known validators.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
@Aspect
public final class ParameterValidationAnnotationAspect {
	
	private final Map<Class<? extends Annotation>, AnnotationValidator> annotationMappings = new HashMap<Class<? extends Annotation>, AnnotationValidator>();
	
	public ParameterValidationAnnotationAspect() {
    	this.annotationMappings.put(GreaterThan.class, new GreaterThanAnnotationValidator());
    	this.annotationMappings.put(IsIn.class, new IsInAnnotationValidator());
    	this.annotationMappings.put(NotEmpty.class, new NotEmptyAnnotationValidator());
    	this.annotationMappings.put(NotNull.class, new NotNullAnnotationValidator());
	}
	
	@Before(value="")
	public void doValidationCheck(final JoinPoint joinPoint) {
		final Object[] args = joinPoint.getArgs();
		final MethodSignature m = (MethodSignature) joinPoint.getStaticPart().getSignature();
		final Method method = m.getMethod();
		
		final Annotation[][] annotations = method.getParameterAnnotations();
		
		for (int i = 0; i < annotations.length; i++) {
			final Annotation[] annotationsForParam = annotations[i];
			for (int j = 0; j < annotationsForParam.length; i++) {
				final Annotation annotation = annotationsForParam[j];
				final AnnotationValidator validator = annotationMappings.get(annotation.getClass());
				
				if (validator != null) {
					validator.validate(method, annotation, args[i], i);
				}
			}
		}	
	}
}
