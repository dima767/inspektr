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

import java.sql.Types;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.inspektr.audit.AuditTrailManager;
import org.inspektr.audit.AuditableActionContext;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link org.inspektr.audit.AuditTrailManager}
 * to persist the audit trail to the EAS-wide AUDIT_TRAIL table in the Oracle
 * data base.
 * 
 * @since 1.0
 * @author lleung
 * @version $Revision: 1.7 $ $Date: 2007/12/03 22:02:41 $
 */
public class JdbcAuditTrailManager extends StoredProcedure implements
		AuditTrailManager {
	
	private final Log log = LogFactory.getLog(this.getClass());
	
	private static final String PROCEDURE_NAME = "ESS_INSPEKTR.submit_audit_trail";
	
	private static final String WHO = "p_who";
	private static final String WHAT = "p_what";
	private static final String ACTION = "p_action";
	private static final String APPLICATION_CODE = "p_applic_cd";
	private static final String WHEN = "p_time_of_action";
	
	
	public void afterPropertiesSet() {
		setSql(PROCEDURE_NAME);
		declareParameter(new SqlParameter(WHO, Types.VARCHAR));
		declareParameter(new SqlParameter(WHAT, Types.VARCHAR));
		declareParameter(new SqlParameter(ACTION, Types.VARCHAR));
		declareParameter(new SqlParameter(APPLICATION_CODE, Types.VARCHAR));
		declareParameter(new SqlParameter(WHEN, Types.TIMESTAMP));
		
		setFunction(false);
		compile();	
		super.afterPropertiesSet();
	}

	@Transactional(propagation=Propagation.NESTED)
	public void record(final AuditableActionContext auditableActionContext) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put(WHO, auditableActionContext.getPrincipal());
		params.put(WHAT, auditableActionContext.getResourceOperatedUpon());
		params.put(ACTION, auditableActionContext.getActionPerformed());
		params.put(APPLICATION_CODE, auditableActionContext.getApplicationCode());
		params.put(WHEN, auditableActionContext.getWhenActionWasPerformed());

		try {
			execute(params);
		} catch (Throwable t) {
			log.error("Error persisting audit trail: " + t, t);
		}
	}

}
