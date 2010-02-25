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
package com.github.inspektr.audit.spi;

import org.aspectj.lang.JoinPoint;

/**
 * An SPI interface needed to be implemented by individual applications requiring an audit trail record keeping
 * functionality, to provide a current resource on which an audit-able action is being performed.
 * 
 * @author Dmitriy Kopylenko
 * @version $Revision$ $Date$
 * @since 1.0
 */
public interface AuditResourceResolver {

    /**
     * Resolve the auditable resource.
     * 
     * @param target the join point that contains the arguments.
     * @param returnValue	The returned value
     * @return	The resource String.
     */
    String[] resolveFrom(JoinPoint target, Object returnValue);
    
    /**
     * Resolve the auditable resource for an audit-able action that has
     * incurred an exception.
     * 
     * @param target the join point that contains the arguments.
     * @param exception	The exception incurred when the join point proceeds.
     * @return	The resource String.
     */
    String[] resolveFrom(JoinPoint target, Exception exception);
}
