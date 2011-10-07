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

/**
 * Copyright (C) 2010 Rutgers, the State University of New Jersey.
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
package com.github.inspektr.audit.support;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.github.inspektr.audit.AuditTrailManager;
import com.github.inspektr.audit.AuditActionContext;
import com.github.inspektr.common.Cleanable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Implementation of {@link com.github.inspektr.audit.AuditTrailManager} to persist the
 * audit trail to the  AUDIT_TRAIL table in the Oracle data base.
 * <p/>
 * <pre>
 * CREATE TABLE COM_AUDIT_TRAIL
 * (
 *  AUD_USER      VARCHAR2(100) NOT NULL,
 *  AUD_CLIENT_IP VARCHAR(15)   NOT NULL,
 *  AUD_SERVER_IP VARCHAR(15)   NOT NULL,
 *  AUD_RESOURCE  VARCHAR2(100) NOT NULL,
 *  AUD_ACTION    VARCHAR2(100) NOT NULL,
 *  APPLIC_CD     VARCHAR2(5)   NOT NULL,
 *  AUD_DATE      TIMESTAMP     NOT NULL
 * )
 * </pre>
 *
 * @author Scott Battaglia
 * @author Marvin S. Addison
 * @version $Revision: 1.7 $ $Date: 2007/12/03 22:02:41 $
 * @since 1.0
 */
public final class JdbcAuditTrailManager extends SimpleJdbcDaoSupport implements AuditTrailManager, Cleanable {

    private static final String INSERT_SQL_TEMPLATE = "INSERT INTO %s " +
            "(AUD_USER, AUD_CLIENT_IP, AUD_SERVER_IP, AUD_RESOURCE, AUD_ACTION, APPLIC_CD, AUD_DATE) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String DELETE_SQL_TEMPLATE = "DELETE FROM %s %s";

    private static final int DEFAULT_COLUMN_LENGTH = 100;

    /**
     * Logger instance
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Instance of TransactionTemplate to manually execute a transaction since
     * threads are not in the same transaction.
     */
    @NotNull
    private final TransactionTemplate transactionTemplate;

    @NotNull
    @Size(min = 1)
    private String tableName = "COM_AUDIT_TRAIL";

    @Min(50)
    private int columnLength = DEFAULT_COLUMN_LENGTH;

    /**
     * ExecutorService that has one thread to asynchronously save requests.
     */
    @NotNull
    private ExecutorService executorService = Executors.newSingleThreadExecutor(new ThreadFactory() {

      public Thread newThread(Runnable r) {
        Thread ret = new Thread(r, "JdbcAuditTrailManagerThread");
        ret.setDaemon(true);
        return ret;
      }
    });

    /**
     * Criteria used to determine records that should be deleted on cleanup
     */
    private WhereClauseMatchCriteria cleanupCriteria = new NoMatchWhereClauseMatchCriteria();


    public JdbcAuditTrailManager(final TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public void record(final AuditActionContext auditActionContext) {
        this.executorService.execute(new LoggingTask(auditActionContext, this.transactionTemplate, this.columnLength));
    }

    protected class LoggingTask implements Runnable {

        private final AuditActionContext auditActionContext;

        private final TransactionTemplate transactionTemplate;

        private final int columnLength;

        public LoggingTask(final AuditActionContext auditActionContext, final TransactionTemplate transactionTemplate, final int columnLength) {
            this.auditActionContext = auditActionContext;
            this.transactionTemplate = transactionTemplate;
            this.columnLength = columnLength;
        }

        public void run() {
            this.transactionTemplate
                    .execute(new TransactionCallbackWithoutResult() {
                        protected void doInTransactionWithoutResult(final TransactionStatus transactionStatus) {
                            final String userId = auditActionContext.getPrincipal().length() <= columnLength ? auditActionContext.getPrincipal() : auditActionContext.getPrincipal().substring(0, columnLength);
                            final String resource = auditActionContext.getResourceOperatedUpon().length() <= columnLength ? auditActionContext.getResourceOperatedUpon() : auditActionContext.getResourceOperatedUpon().substring(0, columnLength);
                            final String action = auditActionContext.getActionPerformed().length() <= columnLength ? auditActionContext.getActionPerformed() : auditActionContext.getActionPerformed().substring(0, columnLength);

                            getSimpleJdbcTemplate()
                                    .update(
                                            String.format(INSERT_SQL_TEMPLATE, tableName),
                                            userId,
                                            auditActionContext.getClientIpAddress(),
                                            auditActionContext.getServerIpAddress(),
                                            resource,
                                            action,
                                            auditActionContext.getApplicationCode(),
                                            auditActionContext.getWhenActionWasPerformed());
                        }
                    });
        }
    }

    public void setTableName(final String tableName) {
        this.tableName = tableName;
    }

    public void setCleanupCriteria(final WhereClauseMatchCriteria criteria) {
        this.cleanupCriteria = criteria;
    }

    public void setExecutorService(final ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setColumnLength(final int columnLength) {
        this.columnLength = columnLength;
    }

    /**
     * {@inheritDoc}
     */
    public void clean() {
        this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            protected void doInTransactionWithoutResult(final TransactionStatus transactionStatus) {
                final String sql = String.format(DELETE_SQL_TEMPLATE, tableName, cleanupCriteria);
                final List<?> params = cleanupCriteria.getParameterValues();
                JdbcAuditTrailManager.this.logger.info("Cleaning audit records with query " + sql);
                JdbcAuditTrailManager.this.logger.debug("Query parameters: " + params);
                final int count = getSimpleJdbcTemplate().update(sql, params.toArray());
                JdbcAuditTrailManager.this.logger.info(count + " records deleted.");
            }
        });
    }
}
