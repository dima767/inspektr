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

import org.inspektr.common.annotation.GreaterThan;

/**
 * Works in conjunction with the {@link GreaterThan} annotation to ensure that
 * fields have a proper value set.
 * 
 * @author Scott Battaglia
 * @version $Revision: 1.2 $ $Date: 2007/04/10 00:48:49 $
 * @since 1.0
 * <p>
 * TODO: make more robust to support things other than int.
 * </p>
 */
public final class GreaterThanAnnotationValidator extends AbstractAnnotationValidator {
	
    public Class<? extends Annotation> supports() {
		return GreaterThan.class;
	}

	protected void validateInternal(final Annotation annotation, final Object arg,
			final String type, final String fieldName, final String objectName) {
        final GreaterThan greaterThan = (GreaterThan) annotation;
        final int value = greaterThan.value();
        
        if (arg instanceof Integer) {
        	validateInt(((Integer) arg).intValue(), value, type, fieldName, objectName);
        }
        
        if (arg instanceof Integer[]) {
        	final Integer[] ints = (Integer[]) arg;
        	
        	for (int i = 0; i < ints.length; i++) {
        		validateInt(ints[i].intValue(), value, type, fieldName + "[" + i + "]", objectName);
        	}
        }

    }
	
	private void validateInt(final int currentValue, final int minimumValue, final String type, final String fieldName, final String objectName) {
        if (currentValue <= minimumValue) {
            throw new IllegalStateException(type +  " \"" + fieldName
                + "\" must be greater than \"" + minimumValue + "\" on \""
                + objectName + "\"");
        }		
	}
}
