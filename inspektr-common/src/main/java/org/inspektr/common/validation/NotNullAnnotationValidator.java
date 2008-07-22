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

import org.inspektr.common.annotation.NotNull;

/**
 * Works in conjunction with the {@link NotNull} annotation to ensure that all
 * fields are properly set.
 * 
 * @author Scott Battaglia
 * @version $Revision: 1.1 $ $Date: 2007/04/09 04:30:31 $
 * @since 1.0
 */
public final class NotNullAnnotationValidator extends AbstractAnnotationValidator {
  
	protected void validateInternal(final Annotation annotation, final Object arg,
			final String type, final String fieldName, final String objectName) {
		if (arg == null) {
			throw new IllegalStateException(type + " " + fieldName + " cannot be null on " + objectName);
		}
	}

	public Class< ? extends Annotation> supports() {
        return NotNull.class;
    }
}
