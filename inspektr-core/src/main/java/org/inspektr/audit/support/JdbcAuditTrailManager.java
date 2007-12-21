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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.inspektr.audit.AuditTrailManager;
import org.inspektr.audit.AuditableActionContext;
import org.inspektr.common.ioc.annotation.NotNull;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Implementation of {@link org.inspektr.audit.AuditTrailManager} to persist the
 * audit trail to the  AUDIT_TRAIL table in the Oracle data base.
 * 
 * <pre>
 * CREATE TABLE COM_AUDIT_TRAIL
 * (
 *  AUD_USER        VARCHAR2(100)                 NOT NULL,
 *  AUD_CLIENT_IP	VARCHAR(15)						NOT NULL,
 *  AUD_SERVER_IP	VARCHAR(15)						NOT NULL,
 *  AUD_RESOURCE    VARCHAR2(100)                 NOT NULL,
 *  AUD_ACTION      VARCHAR2(100)                 NOT NULL,
 *  APPLIC_CD       VARCHAR2(5)                  	NOT NULL,
 *  AUD_DATE		  TIMESTAMP							NOT NULL
 * )
 * </pre>
 * 
 * @author Scott Battaglia
 * @version $Revision: 1.7 $ $Date: 2007/12/03 22:02:41 $
 * @since 1.0
 * 
 */
public final class JdbcAuditTrailManager extends SimpleJdbcDaoSupport implements
		AuditTrailManager {

	private static final String INSERT_SQL_STATEMENT = "Insert into COM_AUDIT_TRAIL(AUD_USER, AUD_CLIENT_IP, AUD_SERVER_IP, AUD_RESOURCE, AUD_ACTION, APPLIC_CD, AUD_DATE) Values(?, ?, ?, ?, ?, ?, ?)";

	/** ExecutorService that has one thread to asynchronously save requests. */
	@NotNull
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();

	/**
	 * Instance of TransactionTemplate to manually execute a transaction since
	 * threads are not in the same transaction.
	 */
	@NotNull
	private final TransactionTemplate transactionTemplate;

	public JdbcAuditTrailManager(final TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void record(final AuditableActionContext auditableActionContext) {
		this.executorService.execute(new LoggingTask(auditableActionContext,
				this.transactionTemplate));

	}

	protected class LoggingTask implements Runnable {

		private final AuditableActionContext auditableActionContext;

		private final TransactionTemplate transactionTemplate;

		public LoggingTask(final AuditableActionContext auditableActionContext, final TransactionTemplate transactionTemplate) {
			this.auditableActionContext = auditableActionContext;
			this.transactionTemplate = transactionTemplate;
		}

		public void run() {
			this.transactionTemplate
					.execute(new TransactionCallbackWithoutResult() {
						protected void doInTransactionWithoutResult(final TransactionStatus transactionStatus) {
							getSimpleJdbcTemplate()
									.update(
											INSERT_SQL_STATEMENT,
											auditableActionContext.getPrincipal(),
											auditableActionContext.getClientIpAddress(),
											auditableActionContext.getServerIpAddress(),
											auditableActionContext.getResourceOperatedUpon(),
											auditableActionContext.getActionPerformed(),
											auditableActionContext.getApplicationCode(),
											auditableActionContext.getWhenActionWasPerformed());
						}
					});
		}
	}

}
