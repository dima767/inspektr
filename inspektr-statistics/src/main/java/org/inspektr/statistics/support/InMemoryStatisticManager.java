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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.inspektr.common.annotation.NotNull;
import org.inspektr.statistics.StatisticActionContext;
import org.inspektr.statistics.annotation.Statistic.Precision;

/**
 * Example StatisticsManager that stores everything in memory.
 * <p>
 * Note that if this is running long enough, you could run out of memory, as it has no upper bound on the amount of statistics it stores.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
public final class InMemoryStatisticManager extends AbstractThreadExecutorBasedStatisticManager {
	
	private static final Log LOG = LogFactory.getLog(InMemoryStatisticManager.class);
	
	@NotNull
	private final List<StatisticValue> values = new ArrayList<StatisticValue>();

	protected Runnable newTask(final StatisticActionContext statisticActionContext) {
		return new StatisticGatheringTask(statisticActionContext, this.values);
	}
	
	protected static final class StatisticGatheringTask implements Runnable {
		
		private final StatisticActionContext context;
		
		private final List<StatisticValue> values;
		
		public StatisticGatheringTask(final StatisticActionContext context, final List<StatisticValue> values) {
			this.context = context;
			this.values = values;
		}

		public void run() {
			for (final Precision precision : this.context.getRequiredPrecision()) {
				boolean matched = false;
				for (final StatisticValue value : this.values) {
					if (value.matches(this.context.getWhen(), precision, this.context.getWhat())) {
						value.increment(context.getExecutionTime());
						matched = true;
						LOG.info(value.toString());
						break;
					}
				}
				
				if (!matched) {
					final StatisticValue value = new StatisticValue(precision, this.context.getWhen(), this.context.getWhat());
					value.increment(context.getExecutionTime());
					LOG.info(value.toString());
					values.add(value);
				}
			}
		}
	}
	
	protected static final class StatisticValue {
		private final Precision precision;
		
		private final Date date;
		
		private int count;
		
		private final String name;
		
		private long executionTime;
		
		public StatisticValue(final Precision precision, final Date date, final String name) {
			this.precision = precision;
			this.date = precision.normalize(date);
			this.name = name;
			this.count = 0;
			this.executionTime = 0;
		}
		
		public synchronized void increment(final long executionTime) {
			final long oldTotalTime = this.executionTime * count;
			final long newTotalTime = oldTotalTime + executionTime;
			this.count++;
			this.executionTime = newTotalTime / count;
		}
		
		public boolean matches(final Date date, final Precision precision, final String name) {
			return precision.same(this.date, date) && this.precision == precision && this.name.equals(name);
		}

		public Precision getPrecision() {
			return this.precision;
		}

		public Date getDate() {
			return this.date;
		}

		public int getCount() {
			return this.count;
		}

		public String getName() {
			return this.name;
		}
		
		public String toString() {
			return "name=[" + this.name + "],date=[" + this.date + "],precision=[" + this.precision + "],count=[" + count + "]";
		}
	}
}
