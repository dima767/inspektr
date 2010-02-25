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
package com.github.inspektr.common.spi;

import org.aspectj.lang.JoinPoint;

/**
 * An SPI interface needed to be implemented by individual applications requiring an audit trail record keeping
 * functionality, to provide a currently authenticated principal performing an audit-able action.
 * 
 * @author Dmitriy Kopylenko
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 */
public interface PrincipalResolver {

    /**
     * Default String that can be used when the user is anonymous.
     */
    final String ANONYMOUS_USER = "audit:anonymous";

    /**
     * Default String that can be used when the user cannot be determined.
     */
    final String UNKNOWN_USER = "audit:unknown";

    /**
     * Resolve the principal performing an audit-able action.
     * <p>
     * Note, this method should NEVER throw an exception *unless* the expectation is that a failed resolution causes
     * the entire transaction to fail.  Otherwise use {@link com.github.inspektr.common.spi.PrincipalResolver#UNKNOWN_USER}.
     * 
     * @param auditTarget the join point where we're auditing.
     * @param returnValue the returned value
     * @return	The principal as a String. CANNOT be NULL.
     */
    String resolveFrom(JoinPoint auditTarget, Object returnValue);
    
    /**
     * Resolve the principal performing an audit-able action that has incurred
     * an exception.
     * <p>
     * Note, this method should NEVER throw an exception *unless* the expectation is that a failed resolution causes
     * the entire transaction to fail.  Otherwise use {@link com.github.inspektr.common.spi.PrincipalResolver#UNKNOWN_USER}.
     * 
     * @param auditTarget the join point where we're auditing.
     * @param exception	The exception incurred when the join point proceeds.
     * @return	The principal as a String. CANNOT be NULL.
     */
    String resolveFrom(JoinPoint auditTarget, Exception exception);

    /**
     * Called when there is no other way to resolve the principal (i.e. an error was captured, auditing was not
     * called, etc.)
     * <p>
     * Note, this method should NEVER throw an exception *unless* the expectation is that a failed resolution causes
     * the entire transaction to fail.  Otherwise use {@link com.github.inspektr.common.spi.PrincipalResolver#UNKNOWN_USER}.
     *
     * @return the principal.  CANNOT be NULL.
     */
    String resolve();
}
