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

import java.util.Date;
import java.util.List;

import org.inspektr.webapp.domain.LogEntry;

/**
 * Implements the various ways one can search for a LogEntry.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
public interface LogEntryDao {
	
	List<String> getApplicationCodes();
	
	List<LogEntry> findLogEntriesFor(Date endDate);
	
	List<LogEntry> findLogEntriesFor(String applicationCode, Date endDate);
	
	List<LogEntry> findLogEntriesFor(String applicationCode, Date endDate, String principal);
	
	List<LogEntry> findLogEntriesFor(String applicationCode, Date startDate, Date endDate);
	
	List<LogEntry> findLogEntriesFor(String applicationCode, Date startDate, Date endDate, String principal);

}
