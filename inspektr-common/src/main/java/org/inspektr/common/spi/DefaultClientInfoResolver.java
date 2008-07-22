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
package org.inspektr.common.spi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.inspektr.common.web.ClientInfo;
import org.inspektr.common.web.ClientInfoHolder;

/**
 * Default implementation that gets it from the ThreadLocal.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
public final class DefaultClientInfoResolver implements ClientInfoResolver {
	
	private final Log log = LogFactory.getLog(getClass());

	public ClientInfo resolveFrom(final JoinPoint joinPoint, final Object retVal) {
		final ClientInfo clientInfo = ClientInfoHolder.getClientInfo();
		
		if (clientInfo != null) {
			return clientInfo;
		}
		
		log.warn("No ClientInfo could be found.  Returning empty ClientInfo object.");
		
		return new ClientInfo();
	}
}
