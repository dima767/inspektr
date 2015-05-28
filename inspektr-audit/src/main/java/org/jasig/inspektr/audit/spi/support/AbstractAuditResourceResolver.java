/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.inspektr.audit.spi.support;

import org.aspectj.lang.JoinPoint;
import org.jasig.inspektr.audit.spi.AuditResourceResolver;

/**
 * Abstract AuditResourceResolver for when the resource is the same regardless of an exception or not.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
public abstract class AbstractAuditResourceResolver implements
        AuditResourceResolver {

    public final String[] resolveFrom(final JoinPoint joinPoint, final Object retVal) {
        return createResource(joinPoint.getArgs());
    }

    public final String[] resolveFrom(final JoinPoint joinPoint, final Exception e) {
        return createResource(joinPoint.getArgs());
    }

    protected abstract String[] createResource(final Object[] args);
}
