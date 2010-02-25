/**
 * Copyright (C) 2009 Rutgers, the State University of New Jersey.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.inspektr.audit.spi.support;

import org.aspectj.lang.JoinPoint;
import com.github.inspektr.audit.annotation.Audit;

/**
 * Default resolver.  If a suffix is defined for success and failure, the failure suffix is appended if an exception is
 * thrown.  Otherwise, the success suffix is used.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
public final class DefaultAuditActionResolver extends AbstractSuffixAwareAuditActionResolver {

    /**
     * Constructs the resolver with empty values for the two suffixes.
     */
    public DefaultAuditActionResolver() {
        this("","");
    }

    /**
     * Constructs the {@link com.github.inspektr.audit.spi.support.DefaultAuditActionResolver} with a success suffix and failure
     * suffix.  CANNOT be NULL.
     * @param successSuffix the suffix to use in the event of a success.
     * @param failureSuffix the suffix to use in the event of a failure.
     */
    public DefaultAuditActionResolver(final String successSuffix, final String failureSuffix) {
        super(successSuffix, failureSuffix);
    }

	public String resolveFrom(final JoinPoint auditableTarget, final Object retval, final Audit audit) {
		return audit.action() + getSuccessSuffix();
	}

	public String resolveFrom(final JoinPoint auditableTarget, final Exception exception, final Audit audit) {
		return audit.action() + getFailureSuffix();
	}
}
