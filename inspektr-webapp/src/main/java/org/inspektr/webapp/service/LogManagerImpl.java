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
package org.inspektr.webapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.inspektr.common.ioc.annotation.NotNull;
import org.inspektr.webapp.dao.LogEntryDao;
import org.inspektr.webapp.domain.LogEntry;
import org.inspektr.webapp.domain.LogSearchRequest;
import org.springframework.beans.support.PropertyComparator;

/**
 * Implementation of {@link LogManager} that delegates to a list of LogEntryDaos.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
public final class LogManagerImpl implements LogManager {
	
	@NotNull
	private final List<LogEntryDao> logEntryDaos;
	
	public LogManagerImpl(final List<LogEntryDao> logEntryDaos) {
		this.logEntryDaos = logEntryDaos;
	}

	public List<LogEntry> searchBy(final LogSearchRequest criteria) {
		final List<LogEntry> list = new ArrayList<LogEntry>();
		final String applicationCode = criteria.getApplicationCode();
		final Date endDate = criteria.getEndDate();
		final Date startDate = criteria.getStartDate();
		final String principal = criteria.getPrincipal();

		if (applicationCode == null && startDate == null && principal == null) {
			for (final LogEntryDao logEntryDao : this.logEntryDaos) {
				list.addAll(logEntryDao.findLogEntriesFor(endDate));
			}
		}
		
		if (applicationCode != null && startDate == null && principal == null) {
			for (final LogEntryDao logEntryDao : this.logEntryDaos) {
				list.addAll(logEntryDao.findLogEntriesFor(applicationCode, endDate));
			}
		}
		
		if (applicationCode != null && startDate != null && principal == null) {
			for (final LogEntryDao logEntryDao : this.logEntryDaos) {
				list.addAll(logEntryDao.findLogEntriesFor(applicationCode, startDate, endDate));
			}
		}
		
		if (applicationCode != null && startDate != null && principal != null) {
			for (final LogEntryDao logEntryDao : this.logEntryDaos) {
				list.addAll(logEntryDao.findLogEntriesFor(applicationCode, startDate, endDate, principal));
			}
		}
		
		final PropertyComparator propertyComparator = new PropertyComparator("entryDate", true, false);
		
		PropertyComparator.sort(list, propertyComparator.getSortDefinition());
		
		return list;
	}

	public Set<String> getApplicationCodes() {
		final Set<String> items = new TreeSet<String>();
		
		for (final LogEntryDao logEntryDao : this.logEntryDaos) {
			items.addAll(logEntryDao.getApplicationCodes());
		}
		
		return items;
	}	
}
