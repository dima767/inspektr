/**
 * Licensed to Inspektr under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Inspektr licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.github.inspektr.audit.spi;

import com.github.inspektr.audit.annotation.Audit;
import org.aspectj.lang.JoinPoint;

/**
 * An SPI interface needed to be implemented by individual applications requiring an audit trail record keeping
 * functionality, to provide the action taken.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 */
public interface AuditActionResolver {
	

    /**
     * Resolve the action for the audit event.
     * 
     * @param auditableTarget
     * @param retval	The returned value
     * @param audit the Audit annotation that may contain additional information.
     * @return	The resource String
     */
    String resolveFrom(JoinPoint auditableTarget, Object retval, Audit audit);
    
    /**
     * Resolve the action for the audit event that has incurred
     * an exception.
     * 
     * @param auditableTarget
     * @param exception	The exception incurred when the join point proceeds.
     * @param audit the Audit annotation that may contain additional information.
     * @return	The resource String
     */
    String resolveFrom(JoinPoint auditableTarget, Exception exception, Audit audit);

}
