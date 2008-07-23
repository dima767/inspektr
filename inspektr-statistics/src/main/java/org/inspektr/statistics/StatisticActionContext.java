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
package org.inspektr.statistics;

import java.util.Date;

import org.inspektr.statistics.annotation.Statistic.Precision;

/**
 * Represents a statistic to be stored in the database.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
public final class StatisticActionContext {
	
	private final Date when;
	
	private final String what;
	
	private final Precision[] requiredPrecision;
	
	private final String serverIpAddress;
	
	private final String applicationCode;
	
	private final long startTime;
	
	private final long endTime;
	
	public StatisticActionContext(final Date when, final String what, final Precision[] requiredPrecision, final String serverIpAddress, final String applicationCode, final long startTime, final long endTime) {
		this.when = new Date(when.getTime());
		this.what = what;
		this.requiredPrecision = requiredPrecision;
		this.serverIpAddress = serverIpAddress;
		this.applicationCode = applicationCode;
		this.startTime = startTime;
		this.endTime = endTime;
		
		assertNotNull(this.when, "when cannot be null");
		assertNotNull(this.what, "what cannot be null.");
		assertNotNull(this.requiredPrecision, "requiredPrecision cannot be null");
		assertNotNull(this.serverIpAddress, "serverIpAddress is a required field.");
		assertNotNull(this.applicationCode, "applicationCode is a required field.");
	}
	
	protected void assertNotNull(final Object o, final String message) {
		if (o == null) {
			throw new IllegalArgumentException(message);
		}
	}
	
	public String getWhat() {
		return this.what;
	}

	public Date getWhen() {
		return new Date(when.getTime());
	}

	public Precision[] getRequiredPrecision() {
		return this.requiredPrecision;
	}

	public String getServerIpAddress() {
		return this.serverIpAddress;
	}

	public String getApplicationCode() {
		return this.applicationCode;
	}
	
	public long getExecutionTime() {
		return this.endTime - this.startTime;
	}
}
