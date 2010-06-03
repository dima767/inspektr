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

package com.github.inspektr.audit.support;

import com.github.inspektr.audit.AuditTrailManager;
import com.github.inspektr.audit.AuditActionContext;

/**
 * Simple <code>AuditTrailManager</code> that dumps auditable information to output stream.
 * <p>
 * Useful for testing.
 * 
 * @author Dmitriy Kopylenko
 * @author Scott Battaglia
 * @version $Id: ConsoleAuditTrailManager.java,v 1.2 2007/06/12 15:18:43 dkopylen Exp $
 * @since 1.0
 * @see AuditTrailManager
 */
public final class ConsoleAuditTrailManager extends AbstractStringAuditTrailManager {

    public void record(final AuditActionContext auditActionContext) {
        System.out.println(toString(auditActionContext));
    }
}
