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
package org.inspektr.webapp.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.inspektr.webapp.domain.LogEntry;
import org.inspektr.webapp.domain.LogEntryImpl;

/**
 * Mock implementation of the LogEntryDao.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
public class MockLogEntryDao implements LogEntryDao {

	public List<LogEntry> findLogEntriesFor(final Date endDate) {
		return constructLogEntries();
	}

	public List<LogEntry> findLogEntriesFor(final String applicationCode, final Date endDate) {
		return constructLogEntries();
	}

	public List<LogEntry> findLogEntriesFor(final String applicationCode,
			final Date endDate, final String principal) {
		return constructLogEntries();
	}

	public List<LogEntry> findLogEntriesFor(final String applicationCode,
			final Date startDate, final Date endDate) {
		return constructLogEntries();
	}

	public List<LogEntry> findLogEntriesFor(final String applicationCode,
			final Date startDate, final Date endDate, final String principal) {
		return constructLogEntries();
	}

	public List<String> getApplicationCodes() {
		final List<String> codesAsList = new ArrayList<String>();
		codesAsList.add("CAS");
		return codesAsList;
	}
	
	protected List<LogEntry> constructLogEntries() {
		final List<LogEntry> logList = new ArrayList<LogEntry>();
		
		final LogEntryImpl impl = new LogEntryImpl();
		impl.setActionPerformed("action");
		impl.setApplicationCode("CAS");
		impl.setClientIpAddress("127.0.0.1");
		impl.setEntryDate(new Date());
		impl.setPrincipal("principal");
		impl.setResource("resource");
		impl.setServerIpAddress("server");
		
		logList.add(impl);
		return logList;
	}
}
