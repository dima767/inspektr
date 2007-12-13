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
package org.inspektr.audit;

import java.util.Date;

import org.springframework.util.Assert;

/**
 * Immutable container holding the core elements of an auditable action that need to be recorded
 * as an audit trail record.
 * 
 * @author Dmitriy Kopylenko
 * @version $Id: AuditableActionContext.java,v 1.2 2007/06/14 14:43:32 dkopylen Exp $
 * @since 1.0
 */
public final class AuditableActionContext {
    
    /** This is <i>WHO</i>*/
    private final String principal;
    
    /** This is <i>WHAT</i>*/
    private final String resourceOperatedUpon;
    
    /** This is <i>ACTION</i>*/
    private final String actionPerformed;
    
    /** This is <i>Application from which operation has been performed</i>*/
    private final String applicationCode;
    
    /** This is <i>WHEN</i>*/
    private final Date whenActionWasPerformed;
    
    /** Client IP Address */
    private final String clientIpAddress;
    
    /** Server IP Address */
    private final String serverIpAddress;

    public AuditableActionContext(final String principal, final String resourceOperatedUpon, final String actionPerformed, final String applicationCode,
        final Date whenActionWasPerformed, final String clientIpAddress, final String serverIpAddress) {
        Assert.notNull(principal, "principal cannot be null");
        Assert.notNull(resourceOperatedUpon, "resourceOperatedUpon cannot be null");
        Assert.notNull(actionPerformed, "actionPerformed cannot be null.");
        Assert.notNull(applicationCode, "applicationCode cannot be null.");
        Assert.notNull(whenActionWasPerformed, "whenActionPerformed cannot be null.");
        Assert.notNull(clientIpAddress, "clientIpAddress cannot be null.");
        Assert.notNull(serverIpAddress, "serverIpAddress cannot be null.");
        this.principal = principal;
        this.resourceOperatedUpon = resourceOperatedUpon;
        this.actionPerformed = actionPerformed;
        this.applicationCode = applicationCode;
        this.whenActionWasPerformed = new Date(whenActionWasPerformed.getTime());
        this.clientIpAddress = clientIpAddress;
        this.serverIpAddress = serverIpAddress;
    }

    public String getPrincipal() {
        return principal;
    }
    
    public String getResourceOperatedUpon() {
        return resourceOperatedUpon;
    }
    
    public String getActionPerformed() {
        return actionPerformed;
    }

    public String getApplicationCode() {
        return applicationCode;
    }
    
    public Date getWhenActionWasPerformed() {
        return new Date(whenActionWasPerformed.getTime());
    }

	public String getClientIpAddress() {
		return this.clientIpAddress;
	}

	public String getServerIpAddress() {
		return this.serverIpAddress;
	}
}
