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

import java.util.Calendar;
import java.util.Date;

/**
 * Implementation of {@link LogSearchRequest} that provides setters for 
 * Spring binding.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
public final class LogSearchRequestImpl implements LogSearchRequest {

	private String applicationCode;
	
	private Date endDate;
	
	private Date startDate;
	
	private String principal;
	
	public LogSearchRequestImpl() {
		this.applicationCode = null;
		this.principal = null;
		this.endDate = null;
		
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)-1);
        this.startDate = calendar.getTime();
        this.endDate = new Date();
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

	public void setPrincipal(final String principal) {
		this.principal = principal;
	}

	public String getApplicationCode() {
		return this.applicationCode;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public String getPrincipal() {
		return this.principal;
	}

	public Date getStartDate() {
		return this.startDate;
	}
}
