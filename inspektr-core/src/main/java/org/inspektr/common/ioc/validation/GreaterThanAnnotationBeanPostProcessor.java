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

import org.inspektr.common.ioc.annotation.GreaterThan;
import org.springframework.beans.FatalBeanException;

/**
 * Works in conjunction with the {@link GreaterThan} annotation to ensure that
 * fields have a proper value set.
 * 
 * @author Scott Battaglia
 * @version $Revision: 1.2 $ $Date: 2007/04/10 00:48:49 $
 * @since 3.1
 * <p>
 * TODO: make more robust to support things other than int.
 * </p>
 */
public final class GreaterThanAnnotationBeanPostProcessor extends
    AbstractAnnotationBeanPostProcessor {

    protected void processField(final Field field, final Annotation annotation,
        final Object bean, final String beanName) throws IllegalAccessException {
        final GreaterThan greaterThan = (GreaterThan) annotation;

        final int value = greaterThan.value();
        final int val = field.getInt(bean);

        if (val <= value) {
            throw new FatalBeanException("value of field \"" + field.getName()
                + "\" must be greater than \"" + value + "\" on bean \""
                + beanName + "\"");
        }
    }

    protected Class< ? extends Annotation> getSupportedAnnotation() {
        return GreaterThan.class;
    }
}
