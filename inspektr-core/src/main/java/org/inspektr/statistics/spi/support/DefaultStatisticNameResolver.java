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
package org.inspektr.statistics.spi.support;

import org.aspectj.lang.JoinPoint;
import org.inspektr.statistics.annotation.Statistic;
import org.inspektr.statistics.spi.StatisticNameResolver;

/**
 * Returns the static name for the Statistic.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 0.7
 *
 */
public final class DefaultStatisticNameResolver implements StatisticNameResolver {

	public String resolveFrom(final JoinPoint auditableTarget, final Object retval,
			final Statistic statistic) {
		return statistic.name();
	}

	public String resolveFrom(final JoinPoint auditableTarget, final Exception exception,
			final Statistic statistic) {
		return statistic.name();
	}
}
