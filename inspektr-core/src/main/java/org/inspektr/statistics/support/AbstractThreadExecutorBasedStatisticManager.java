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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.inspektr.common.annotation.NotNull;
import org.inspektr.statistics.StatisticActionContext;
import org.inspektr.statistics.StatisticManager;
import org.springframework.beans.factory.DisposableBean;

/**
 * Abstract class to handle the multithreading capability required by most implementations
 * of the StatisticManager.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
public abstract class AbstractThreadExecutorBasedStatisticManager implements
		StatisticManager, DisposableBean {

	/** ExecutorService that has one thread to asynchronously save requests. */
	@NotNull
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();

	public final void recalculate(final StatisticActionContext statisticActionContext) {
		this.executorService.execute(newTask(statisticActionContext));
	}

	public void destroy() throws Exception {
		this.executorService.shutdown();
	}

	protected abstract Runnable newTask(StatisticActionContext statisticActionContext);
}
