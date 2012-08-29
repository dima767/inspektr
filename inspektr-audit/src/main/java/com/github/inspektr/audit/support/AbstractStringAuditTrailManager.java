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

import com.github.inspektr.audit.AuditActionContext;
import com.github.inspektr.audit.AuditTrailManager;

/**
 * Abstract AuditTrailManager that turns the AuditActionContext into a printable String.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.1
 */
public abstract class AbstractStringAuditTrailManager implements AuditTrailManager {

    /** Use multi-line output by default **/
    private Boolean useSingleLine = false;

    /** Separator for single line log entries */
    private String entrySeparator = ",";

    protected String getEntrySeparator() {
            return this.entrySeparator;
    }

    public void setEntrySeparator(final String separator) {
        this.entrySeparator = separator;
    }

    public void setUseSingleLine(final Boolean useSingleLine) {
        this.useSingleLine = useSingleLine;
    }

    protected String toString(final AuditActionContext auditActionContext) {
        if(this.useSingleLine) {
            return getSingleLineAuditString(auditActionContext);
        } else {
            return getMultiLineAuditString(auditActionContext);
        }
    }
        
    protected String getMultiLineAuditString(final AuditActionContext auditActionContext) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Audit trail record BEGIN\n");
        builder.append("=============================================================\n");
        builder.append("WHO: ");
        builder.append(auditActionContext.getPrincipal());
        builder.append("\n");
        builder.append("WHAT: ");
        builder.append(auditActionContext.getResourceOperatedUpon());
        builder.append("\n");
        builder.append("ACTION: ");
        builder.append(auditActionContext.getActionPerformed());
        builder.append("\n");
        builder.append("APPLICATION: ");
        builder.append(auditActionContext.getApplicationCode());
        builder.append("\n");
        builder.append("WHEN: ");
        builder.append(auditActionContext.getWhenActionWasPerformed());
        builder.append("\n");
        builder.append("CLIENT IP ADDRESS: ");
        builder.append(auditActionContext.getClientIpAddress());
        builder.append("\n");
        builder.append("SERVER IP ADDRESS: ");
        builder.append(auditActionContext.getServerIpAddress());
        builder.append("\n");
        builder.append("=============================================================");
        builder.append("\n\n");

        return builder.toString();
    }

    protected String getSingleLineAuditString(final AuditActionContext auditActionContext) {
        final StringBuilder builder = new StringBuilder();
        builder.append(auditActionContext.getWhenActionWasPerformed());
        builder.append(getEntrySeparator());
        builder.append(auditActionContext.getApplicationCode());
        builder.append(getEntrySeparator());
        builder.append(auditActionContext.getResourceOperatedUpon());
        builder.append(getEntrySeparator());
        builder.append(auditActionContext.getActionPerformed());
        builder.append(getEntrySeparator());
        builder.append(auditActionContext.getPrincipal());
        builder.append(getEntrySeparator());
        builder.append(auditActionContext.getClientIpAddress());
        builder.append(getEntrySeparator());
        builder.append(auditActionContext.getServerIpAddress());

        return builder.toString();
    }

}