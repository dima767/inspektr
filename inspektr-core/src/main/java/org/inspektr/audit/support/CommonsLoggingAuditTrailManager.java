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
package org.inspektr.audit.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.inspektr.audit.AuditTrailManager;
import org.inspektr.audit.AuditableActionContext;

/**
 * <code>AuditTrailManager</code> that dumps auditable information to a configured logger.
 * 
 * @author Dmitriy Kopylenko
 * @version $Id: Log4jAuditTrailManager.java,v 1.1 2007/06/12 15:18:43 dkopylen Exp $
 * @since 1.0
 * @see AuditTrailManager
 */
public final class CommonsLoggingAuditTrailManager implements AuditTrailManager {
    
    private final Log log = LogFactory.getLog(this.getClass());

    public void record(final AuditableActionContext auditableActionContext) {
        log.info("Audit trail record BEGIN");
        log.info("=============================================================");
        log.info("WHO: " + auditableActionContext.getPrincipal());
        log.info("WHAT: " + auditableActionContext.getResourceOperatedUpon());
        log.info("ACTION: " + auditableActionContext.getActionPerformed());
        log.info("APPLICATION: " + auditableActionContext.getApplicationCode());
        log.info("WHEN: " + auditableActionContext.getWhenActionWasPerformed());
        log.info("=============================================================");
        log.info("\n");
        log.info("\n");
    }
}
