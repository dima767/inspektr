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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.inspektr.webapp.domain.LogEntry;
import org.inspektr.webapp.domain.LogEntryImpl;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
 * Implementation of {@link LogEntryDao} for databases.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
public final class JdbcLogEntryDao extends SimpleJdbcDaoSupport implements
		LogEntryDao {
	
	private final ParameterizedRowMapper<String> applicationCodeAsStringParameterizedRowMapper = new ParameterizedRowMapper<String>() {

		public String mapRow(final ResultSet rs, final int rownum) throws SQLException {
			return rs.getString("APPLIC_CD");
		}};
		
	private final ParameterizedRowMapper<LogEntry> logEntryParameterizedRowMapper = new ParameterizedRowMapper<LogEntry>() {

		public LogEntry mapRow(final ResultSet rs, final int rownum) throws SQLException {
			final LogEntryImpl impl = new LogEntryImpl();
			impl.setActionPerformed(rs.getString("AUD_ACTION"));
			impl.setApplicationCode(rs.getString("APPLIC_CD"));
			impl.setClientIpAddress(rs.getString("AUD_CLIENT_IP"));
			impl.setEntryDate(rs.getTimestamp("AUD_DATE"));
			impl.setPrincipal(rs.getString("AUD_USER"));
			impl.setResource(rs.getString("AUD_RESOURCE"));
			impl.setServerIpAddress(rs.getString("AUD_SERVER_IP"));
			
			return impl;
		}
	};
	
	private final String SQL_SELECT_START = "Select AUD_ACTION, APPLIC_CD, AUD_CLIENT_IP, AUD_DATE, AUD_USER, AUD_RESOURCE, AUD_SERVER_IP FROM COM_AUDIT_TRAIL WHERE ";
	private final String SQL_SELECT_END = " Order By AUD_DATE, APPLIC_CD";
	
	private String wildCard = "%";

	public List<LogEntry> findLogEntriesFor(final Date endDate) {
		return getSimpleJdbcTemplate().query(SQL_SELECT_START + "AUD_DATE <= ?" + SQL_SELECT_END, this.logEntryParameterizedRowMapper, endDate);
	}

	public List<LogEntry> findLogEntriesFor(final String applicationCode, final Date endDate) {
		return getSimpleJdbcTemplate().query(SQL_SELECT_START + "APPLIC_CD = ? AND AUD_DATE <= ?" + SQL_SELECT_END, this.logEntryParameterizedRowMapper, applicationCode, endDate);
	}

	public List<LogEntry> findLogEntriesFor(final String applicationCode,
			final Date endDate, final String principal) {
		return getSimpleJdbcTemplate().query(SQL_SELECT_START + "APPLIC_CD = ? AND AUD_DATE <= ? AND AUD_USER LIKE ?" + SQL_SELECT_END, this.logEntryParameterizedRowMapper, applicationCode, endDate, principal + this.wildCard);
	}

	public List<LogEntry> findLogEntriesFor(final String applicationCode,
			final Date startDate, final Date endDate) {
		return getSimpleJdbcTemplate().query(SQL_SELECT_START + "APPLIC_CD = ? AND AUD_DATE <= ? AND AUD_DATE >= ?" + SQL_SELECT_END, this.logEntryParameterizedRowMapper, applicationCode, endDate, startDate);
	}

	public List<LogEntry> findLogEntriesFor(final String applicationCode,
			final Date startDate, final Date endDate, final String principal) {
		return getSimpleJdbcTemplate().query(SQL_SELECT_START + "APPLIC_CD = ? AND AUD_DATE <= ? AND AUD_DATE >= ? AND AUD_USER LIKE ?" + SQL_SELECT_END, this.logEntryParameterizedRowMapper, applicationCode, endDate, startDate, principal + this.wildCard);
	}

	public List<String> getApplicationCodes() {
		return getSimpleJdbcTemplate().query("Select Distinct APPLIC_CD From COM_AUDIT_TRAIL Order By APPLIC_CD ASC", this.applicationCodeAsStringParameterizedRowMapper);
	}
}
