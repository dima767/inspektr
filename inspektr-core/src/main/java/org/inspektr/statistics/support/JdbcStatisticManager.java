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

import org.inspektr.statistics.StatisticActionContext;
import org.inspektr.statistics.StatisticManager;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcStatisticManager extends SimpleJdbcDaoSupport implements
		StatisticManager {

	public void recalculate(final StatisticActionContext statisticActionContext) {
		// TODO Auto-generated method stub

	}

}
