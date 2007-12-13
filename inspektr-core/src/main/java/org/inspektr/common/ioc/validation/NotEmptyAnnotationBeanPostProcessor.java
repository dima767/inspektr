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
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import org.inspektr.common.ioc.annotation.NotEmpty;
import org.springframework.beans.FatalBeanException;

/**
 * @author Scott Battaglia
 * @version $Revision: 1.3 $ $Date: 2007/04/24 18:11:45 $
 * @since 3.1
 */
public final class NotEmptyAnnotationBeanPostProcessor extends
    AbstractAnnotationBeanPostProcessor {

    protected void processField(final Field field, final Annotation annotation,
        final Object bean, final String beanName) throws IllegalAccessException {

        final Object obj = field.get(bean);
        
        if (obj == null) {
            throw new FatalBeanException(constructMessage(field, beanName));
        }
        
        if (obj instanceof Collection) {
            final Collection< ? > c = (Collection< ? >) obj;

            if (c.isEmpty()) {
                throw new FatalBeanException(constructMessage(field, beanName));
            }
        }
        
        if (obj.getClass().isArray()) {
            if (Array.getLength(obj) == 0) {
                throw new FatalBeanException(constructMessage(field, beanName));
            }
        }

        if (obj instanceof Map) {
            final Map< ? , ? > m = (Map< ? , ? >) obj;

            if (m.isEmpty()) {
                throw new FatalBeanException(constructMessage(field, beanName));
            }
        }
    }

    protected String constructMessage(final Field field, final String beanName) {
        return "Field '" + field.getName() + "' on bean '" + beanName
            + "' cannot be empty.";

    }

    protected Class<? extends Annotation> getSupportedAnnotation() {
        return NotEmpty.class;
    }
}
