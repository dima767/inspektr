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
import org.springframework.util.Assert;

/**
 * Represents a statistic to be stored in the database.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 *
 */
public final class StatisticActionContext {
	
	private final Date when;
	
	private final String what;
	
	private final Precision[] requiredPrecision;
	
	public StatisticActionContext(final Date when, final String what, final Precision[] requiredPrecision) {
		this.when = new Date(when.getTime());
		this.what = what;
		this.requiredPrecision = requiredPrecision;
		
		Assert.notNull(when, "when cannot be null");
		Assert.notNull(what, "what cannot be null.");
		Assert.notNull(requiredPrecision, "requiredPrecision cannot be null");
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
	
	

}
