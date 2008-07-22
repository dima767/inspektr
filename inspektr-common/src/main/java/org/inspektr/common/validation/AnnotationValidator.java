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
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Interface for usage with the {@link ValidationAnnotationBeanPostProcessor}.  Each AnnotationValidator
 * can ensure that the rules of the particular annotation are followed.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
public interface AnnotationValidator {
	
	void validate(Field field, Annotation annotation, Object bean, String beanName) throws IllegalAccessException;
	
	void validate(Method method, Annotation annotation, Object arg, int argIndex);
	
	Class<? extends Annotation> supports();
}
