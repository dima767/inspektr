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
package org.inspektr.audit.spi.support;

import org.inspektr.audit.spi.AuditActionResolver;

/**
 * Abstract class that encapsulates the required suffixes.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public abstract class AbstractSuffixAwareAuditActionResolver implements AuditActionResolver {

    private final String successSuffix;

    private final String failureSuffix;

    protected AbstractSuffixAwareAuditActionResolver(final String successSuffix, final String failureSuffix) {

        if (successSuffix == null) {
            throw new IllegalArgumentException("successSuffix cannot be null.");
        }

        if (failureSuffix == null) {
            throw new IllegalArgumentException("failureSuffix cannot be null.");
        }


        this.successSuffix = successSuffix;
        this.failureSuffix = failureSuffix;
    }

    protected final String getSuccessSuffix() {
        return this.successSuffix;
    }

    protected final String getFailureSuffix() {
        return this.failureSuffix;
    }
}
