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
 * Uses the success/failure suffixes when an object is returned (or NULL is returned)
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @version 1.0
 *
 */
public final class ObjectCreationAuditableActionResolver implements
		AuditableActionResolver {

	public String resolveFrom(final JoinPoint auditableTarget, final Object retval,
			final Auditable auditable) {
		final String action = auditable.action();
		
		return action + (retval == null ? auditable.failureSuffix() : auditable.successSuffix());
	}

	public String resolveFrom(final JoinPoint auditableTarget, final Exception exception,
			final Auditable auditable) {
		return auditable.action() + auditable.failureSuffix();
	}
}
