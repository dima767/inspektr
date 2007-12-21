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
package org.inspektr.statistics.support;

import java.util.Date;

import javax.sql.DataSource;

import org.inspektr.common.ioc.annotation.NotNull;
import org.inspektr.statistics.StatisticActionContext;
import org.inspektr.statistics.annotation.Statistic.Precision;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * <pre> 
 * CREATE TABLE COM_STATISTICS
 * (
 *     STAT_SERVER_IP VARCHAR2(15) NOT NULL,
 *     STAT_DATE DATE NOT NULL,
 *     APPLIC_CD VARCHAR2(5) NOT NULL,
 *     STAT_PRECISION VARCHAR2(6) NOT NULL,
 *     STAT_COUNT NUMBER NOT NULL,
 *     STAT_NAME VARCHAR2(100)
 * )
 * </pre>
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
public final class JdbcStatisticManager extends AbstractThreadExecutorBasedStatisticManager {

	@NotNull
	private final SimpleJdbcTemplate jdbcTemplate;
	
	@NotNull
	private final TransactionTemplate transactionTemplate;
		
	public JdbcStatisticManager(final DataSource dataSource, final TransactionTemplate transactionTemplate) {
		this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
		this.transactionTemplate = transactionTemplate;
	}

	protected Runnable newTask(final StatisticActionContext statisticActionContext) {
		return new JdbcStatisticGatheringTask(this.jdbcTemplate, this.transactionTemplate, statisticActionContext);
	}
	
	protected static final class JdbcStatisticGatheringTask implements Runnable {
		
		private final TransactionTemplate transactionTemplate;
		
		private final SimpleJdbcTemplate jdbcTemplate;
		
		private final StatisticActionContext statisticActionContext;
		
		public JdbcStatisticGatheringTask(final SimpleJdbcTemplate jdbcTemplate, final TransactionTemplate transactionTemplate, final StatisticActionContext context) {
			this.transactionTemplate = transactionTemplate;
			this.jdbcTemplate = jdbcTemplate;
			this.statisticActionContext = context;
		}

		public void run() {
			this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				protected void doInTransactionWithoutResult(
						final TransactionStatus transactionStatus) {
					
					for (final Precision precision : statisticActionContext.getRequiredPrecision()) {
						final Date date = precision.normalize(statisticActionContext.getWhen());
						final String name = statisticActionContext.getWhat();
						final String applicationCode = statisticActionContext.getApplicationCode();
						final String serverIpAddress = statisticActionContext.getServerIpAddress();
						
					}
					
					// TODO Auto-generated method stub
					
				}				
			});
		}		
	}
}
