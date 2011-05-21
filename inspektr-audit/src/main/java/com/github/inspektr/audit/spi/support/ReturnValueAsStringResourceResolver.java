/**
 * Licensed to Inspektr under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Inspektr licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.github.inspektr.audit.spi.support;

import java.util.Collection;
import java.util.Iterator;

import org.aspectj.lang.JoinPoint;
import com.github.inspektr.audit.spi.AuditResourceResolver;

/**
 * Implementation of {@link com.github.inspektr.audit.spi.AuditResourceResolver} that uses the toString version of the return value
 * as the resource.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
public class ReturnValueAsStringResourceResolver implements AuditResourceResolver {

	public String[] resolveFrom(final JoinPoint auditableTarget, final Object retval) {
		if (retval instanceof Collection) {
			final Collection c = (Collection) retval;
			final String[] retvals = new String[c.size()];
			
			int i = 0;
			for (final Iterator iter = c.iterator(); iter.hasNext() && i < c.size(); i++) {
				final Object o = iter.next();
				
				if (o != null) {
					retvals[i] = iter.next().toString();
				}
			}
			
			return retvals;
		}
		
		if (retval instanceof Object[]) {
			final Object[] vals = (Object[]) retval;
			final String[] retvals = new String[vals.length];
			for (int i = 0; i < vals.length; i++) {
				retvals[i] = vals[i].toString();
			}
			
			return retvals;
		}
		
		return new String[] {retval.toString()};
	}

	public String[] resolveFrom(final JoinPoint auditableTarget, final Exception exception) {
		final String message = exception.getMessage();
		if (message != null) {
			return new String[] {message};
		}
		return new String[] {exception.toString()};
	}
}
