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
package org.inspektr.common.ioc.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.inspektr.common.ioc.annotation.NotNull;
import org.springframework.beans.FatalBeanException;

/**
 * Works in conjunction with the {@link NotNull} annotation to ensure that all
 * fields are properly set.
 * 
 * @author Scott Battaglia
 * @version $Revision: 1.1 $ $Date: 2007/04/09 04:30:31 $
 * @since 3.1
 */
public final class NotNullAnnotationBeanPostProcessor extends
    AbstractAnnotationBeanPostProcessor {

    protected void processField(final Field field, final Annotation annotation,
        final Object bean, String beanName) throws IllegalAccessException {
        if (field.get(bean) == null) {
            throw new FatalBeanException("Field " + field.getName()
                + " cannot be null on bean: " + beanName);
        }
    }

    protected Class< ? extends Annotation> getSupportedAnnotation() {
        return NotNull.class;
    }
}
