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

import java.util.List;

import org.inspektr.common.ioc.annotation.NotNull;
import org.inspektr.webapp.dao.StatisticDao;
import org.inspektr.webapp.domain.Statistic;
import org.inspektr.webapp.domain.StatisticSearchRequest;

/**
 * Implementation of the {@link StatisticManager} that delegates to the
 * {@link StatisticDao} configured, based on the search criteria.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
public final class StatisticManagerImpl implements StatisticManager {
	
	@NotNull
	private StatisticDao statisticDao;
	
	public StatisticManagerImpl(final StatisticDao statisticDao) {
		this.statisticDao = statisticDao;
	}

	public List<Statistic> findStatisticsBy(final StatisticSearchRequest request) {
		switch (request.getSearchType()) {
		
		case DATE_COMPARISON:
			return this.statisticDao.findComparisonStatistics(request.getStartDate(), request.getEndDate(), request.getApplicationCode(), request.getRequiredPrecisions());
			
		case DATE_RANGE:
			return this.statisticDao.findStatisticsForDateRange(request.getStartDate(), request.getEndDate(), request.getApplicationCode(), request.getRequiredPrecisions());
		}

		return null;
	}

	public List<String> getApplicationCodes() {
		return this.statisticDao.getApplicationCodes();
	}
}
