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
package com.github.inspektr.audit;

import java.io.Serializable;
import java.util.Date;

/**
 * Immutable container holding the core elements of an audit-able action that need to be recorded
 * as an audit trail record.
 * 
 * @author Dmitriy Kopylenko
 * @version $Id: AuditActionContext.java,v 1.2 2007/06/14 14:43:32 dkopylen Exp $
 * @since 1.0
 */
public final class AuditActionContext implements Serializable {
	
    /**
	 * Unique Id for serialization.
	 */
	private static final long serialVersionUID = -3530737409883959089L;

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

    public AuditActionContext(final String principal, final String resourceOperatedUpon, final String actionPerformed, final String applicationCode,
        final Date whenActionWasPerformed, final String clientIpAddress, final String serverIpAddress) {
        assertNotNull(principal, "principal cannot be null");
        assertNotNull(resourceOperatedUpon, "resourceOperatedUpon cannot be null");
        assertNotNull(actionPerformed, "actionPerformed cannot be null.");
        assertNotNull(applicationCode, "applicationCode cannot be null.");
        assertNotNull(whenActionWasPerformed, "whenActionPerformed cannot be null.");
        assertNotNull(clientIpAddress, "clientIpAddress cannot be null.");
        assertNotNull(serverIpAddress, "serverIpAddress cannot be null.");
        this.principal = principal;
        this.resourceOperatedUpon = resourceOperatedUpon;
        this.actionPerformed = actionPerformed;
        this.applicationCode = applicationCode;
        this.whenActionWasPerformed = new Date(whenActionWasPerformed.getTime());
        this.clientIpAddress = clientIpAddress;
        this.serverIpAddress = serverIpAddress;
    }

    protected void assertNotNull(final Object o, final String message) {
    	if (o == null) {
    		throw new IllegalArgumentException(message);
    	}
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
