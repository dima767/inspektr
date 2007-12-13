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
package org.inspektr.audit.spi.support;

import org.aspectj.lang.JoinPoint;
import org.inspektr.audit.annotation.Auditable;
import org.inspektr.audit.spi.AuditableActionResolver;

/**
 * Implementation of {@link AuditableActionResolver} that can process boolean return values.
 * <p>
 * Return values are basically action + either the success or failure suffix based on the boolean
 * value.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
public final class BooleanAuditableActionResolver implements AuditableActionResolver {

	public String resolveFrom(final JoinPoint auditableTarget, final Object retval,
			final Auditable auditable) {
		final Boolean bool = (Boolean) retval;
		final String action = auditable.action();
		
		return action + (bool ? auditable.successSuffix() : auditable.failureSuffix());
	}

	public String resolveFrom(final JoinPoint auditableTarget, final Exception exception,
			Auditable auditable) {
		return auditable.action() + auditable.failureSuffix();
	}
}
