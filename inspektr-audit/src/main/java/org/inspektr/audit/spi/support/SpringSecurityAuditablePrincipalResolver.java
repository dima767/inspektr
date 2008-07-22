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
import org.inspektr.audit.spi.AuditablePrincipalResolver;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;

/**
 * Resolves the principal name to the one provided by Spring Security.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 0.7.0
 *
 */
public final class SpringSecurityAuditablePrincipalResolver implements
		AuditablePrincipalResolver {

	public String resolveFrom(final JoinPoint auditableTarget, final Object retval) {
		return getFromSecurityContext();
	}

	public String resolveFrom(final JoinPoint auditableTarget, final Exception exception) {
		return getFromSecurityContext();
	}
	
	private String getFromSecurityContext() {
		final SecurityContext securityContext = SecurityContextHolder.getContext();
		
		if (securityContext == null) {
			return null;
		}
		
		return securityContext.getAuthentication().getName();
	}

}
