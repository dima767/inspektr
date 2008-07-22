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
package org.inspektr.statistics;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.inspektr.common.annotation.NotEmpty;
import org.inspektr.common.annotation.NotNull;
import org.inspektr.common.spi.ClientInfoResolver;
import org.inspektr.common.spi.support.DefaultClientInfoResolver;
import org.inspektr.common.web.ClientInfo;
import org.inspektr.statistics.annotation.Statistic;
import org.inspektr.statistics.spi.StatisticNameResolver;
import org.inspektr.statistics.spi.support.DefaultStatisticNameResolver;
import org.springframework.util.StringUtils;

/**
 * A POJO style aspect modularizing management of a statistic data concern.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
@Aspect
public final class StatisticManagementAspect {
	
	/** The list of StatisticManagers to use to update statistics. */
	@NotNull
	private final List<StatisticManager> statisticManagers;
	
	/** The default application code. */
	@NotNull
	private final String applicationCode;
	
	@NotNull
	private ClientInfoResolver clientInfoResolver = new DefaultClientInfoResolver();
	
	@NotEmpty
	private Map<Class <? extends StatisticNameResolver>, StatisticNameResolver> nameResolvers = new HashMap<Class <? extends StatisticNameResolver>, StatisticNameResolver>();
	
	public StatisticManagementAspect(final List<StatisticManager> statisticManagers, final String applicationCode) {
		this.statisticManagers = statisticManagers;
		this.applicationCode = applicationCode;
		
		nameResolvers.put(DefaultStatisticNameResolver.class, new DefaultStatisticNameResolver());
	}
	
    @Around(value="@annotation(statistic)", argNames="statistic")
    public Object handleStatisticGathering(final ProceedingJoinPoint joinPoint, final Statistic statistic) throws Throwable {
    	Object retVal = null;
    	String name = null;
    	try {
    		retVal = joinPoint.proceed();
    		name = nameResolvers.get(statistic.nameResolverClass()).resolveFrom(joinPoint, retVal, statistic);
    		return retVal;
    	} catch (final Exception e) {
    		name = nameResolvers.get(statistic.nameResolverClass()).resolveFrom(joinPoint, e, statistic);
    		throw e;
    	} finally {
    		final ClientInfo clientInfo = this.clientInfoResolver.resolveFrom(joinPoint, retVal);
    		final String appCode = StringUtils.hasText(statistic.applicationCode()) ? statistic.applicationCode() : this.applicationCode;
	    	final StatisticActionContext statisticActionContext = new StatisticActionContext(new Date(), name, statistic.requiredPrecision(), clientInfo.getServerIpAddress(), appCode);
	    	for (final StatisticManager manager : this.statisticManagers) {
	    		manager.recalculate(statisticActionContext);
	    	}
    	}
    }

    public void setClientInfoResolver(final ClientInfoResolver clientInfoResolver) {
		this.clientInfoResolver = clientInfoResolver;
	}
    
    public void setAdditionalStatisticNameResolvers(final List<StatisticNameResolver> statisticNameResolvers) {
    	for (final StatisticNameResolver resolver : statisticNameResolvers) {
    		this.nameResolvers.put(resolver.getClass(), resolver);
    	}
    }
}
