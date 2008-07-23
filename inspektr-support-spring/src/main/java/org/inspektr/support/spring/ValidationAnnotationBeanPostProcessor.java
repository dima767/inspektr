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
package org.inspektr.support.spring;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.inspektr.common.annotation.GreaterThan;
import org.inspektr.common.annotation.IsIn;
import org.inspektr.common.annotation.NotEmpty;
import org.inspektr.common.annotation.NotNull;
import org.inspektr.common.validation.AnnotationValidator;
import org.inspektr.common.validation.GreaterThanAnnotationValidator;
import org.inspektr.common.validation.IsInAnnotationValidator;
import org.inspektr.common.validation.NotEmptyAnnotationValidator;
import org.inspektr.common.validation.NotNullAnnotationValidator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

/**
 * BeanPostProcessor to assist in retrieving fields to check for annotations.
 * 
 * @author Scott Battaglia
 * @version $Revision: 1.5 $ $Date: 2007/04/13 20:01:22 $
 * @since 1.0
 */
public final class ValidationAnnotationBeanPostProcessor extends
    InstantiationAwareBeanPostProcessorAdapter {

    private final Log log = LogFactory.getLog(getClass());
    
    private final Map<Class<? extends Annotation>, AnnotationValidator> annotationMappings = new HashMap<Class<? extends Annotation>, AnnotationValidator>();
    
    private final Class<? extends Annotation>[] annotations;
    
    public ValidationAnnotationBeanPostProcessor() {
    	this.annotationMappings.put(GreaterThan.class, new GreaterThanAnnotationValidator());
    	this.annotationMappings.put(IsIn.class, new IsInAnnotationValidator());
    	this.annotationMappings.put(NotEmpty.class, new NotEmptyAnnotationValidator());
    	this.annotationMappings.put(NotNull.class, new NotNullAnnotationValidator());
    	
    	this.annotations = this.annotationMappings.keySet().toArray(new Class[this.annotationMappings.size()]);
    }

    public final Object postProcessBeforeInitialization(final Object bean,
        final String beanName) throws BeansException {

        final List<Field> fields = new ArrayList<Field>();
        final Class< ? > clazz = bean.getClass();
        final Class< ? >[] classes = clazz.getClasses();

        addDeclaredFields(clazz, fields);

        for (int i = 0; i < classes.length; i++) {
            addDeclaredFields(classes[i], fields);
        }

        try {
            for (final Field field : fields) {
                final boolean originalValue = field.isAccessible();
                field.setAccessible(true);
                
                for (final Class<? extends Annotation> annotationClass : this.annotations) {
                	final Annotation annotation = field.getAnnotation(annotationClass);
                	
                	if (annotation != null) {
                		final AnnotationValidator validator = this.annotationMappings.get(annotationClass);
                	
	                	if (validator != null) {
	                		validator.validate(field, annotation, bean, beanName);
	                	}
                	}
                }
                             
                field.setAccessible(originalValue);
            }
        } catch (final IllegalAccessException e) {
            log.warn("Could not access field: " + e.getMessage(), e);
        }

        return bean;
    }

    private final void addDeclaredFields(final Class< ? > clazz,
        final List<Field> fields) {
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
    }
}
