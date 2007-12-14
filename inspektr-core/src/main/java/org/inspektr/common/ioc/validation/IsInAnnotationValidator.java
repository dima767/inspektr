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

import org.inspektr.common.ioc.annotation.IsIn;
import org.springframework.beans.FatalBeanException;

/**
 * Checks whether a value is in an array of values.
 * 
 * @author Scott Battaglia
 * @version $Revision: 1.1 $ $Date: 2007/04/10 20:02:51 $
 * @since 1.0
 */
public final class IsInAnnotationValidator implements AnnotationValidator {

    public void validate(final Field field, final Annotation annotation,
        final Object bean, final String beanName) throws IllegalAccessException {
        final IsIn isIn = (IsIn) annotation;

        final int val = field.getInt(bean);

        for (int i = 0; i < isIn.value().length; i++) {
            if (val == isIn.value()[i]) {
                return;
            }
        }

        throw new FatalBeanException("field '" + field.getName()
            + "' does not contain a value of '" + isIn.value() + "' on bean '"
            + beanName + "'");
    }
    
    

    public Class<? extends Annotation> supports() {
    	return IsIn.class;
    }
}
