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
package org.inspektr.webapp.domain;

import java.util.Date;

import org.inspektr.statistics.annotation.Statistic.Precision;

/**
 * Implementation of the {@link StatisticSearchRequest} 
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
public final class StatisticSearchRequestImpl implements StatisticSearchRequest {
	
	private String applicationCode;
	
	private Date endDate;
	
	private Date startDate;
	
	private SearchType searchType;
	
	private Precision[] requiredPrecisions;
	
	public StatisticSearchRequestImpl() {
		this.endDate = new Date();
		this.startDate = new Date();
		this.searchType = SearchType.DATE_RANGE;
		this.requiredPrecisions = new Precision[] {Precision.DAY};
	}

	public void setApplicationCode(final String applicationCode) {
		this.applicationCode = applicationCode;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public void setSearchType(final SearchType searchType) {
		this.searchType = searchType;
	}

	public void setRequiredPrecisions(Precision[] requiredPrecisions) {
		this.requiredPrecisions = requiredPrecisions;
	}

	public String getApplicationCode() {
		return this.applicationCode;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public Precision[] getRequiredPrecisions() {
		return this.requiredPrecisions;
	}

	public SearchType getSearchType() {
		return this.searchType;
	}

	public Date getStartDate() {
		return this.startDate;
	}
}
