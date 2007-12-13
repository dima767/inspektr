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
package org.inspektr.error.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.inspektr.common.ioc.annotation.NotNull;
import org.inspektr.error.ErrorLogManager;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;


/**
 * A <code>HandlerExceptionResolver</code> implementation that reports uncaught
 * exceptions during handler mapping or execution to a configured
 * {@link org.inspektr.error.ErrorLogManager}. 
 * <p>
 * <code>HanderExceptionResolvers</code> should be ordered. If the application has
 * other resolvers in addition to the <code>ErrorLogHandlerExceptionResolver</code>,
 * it should make sure that the <code>ErrorLogHandlerExceptionResolver</code> has
 * the lowest order value (i.e. highest priority) so as to make sure that this
 * resolver is always invoked.
 * 
 * @author Alice Leung
 * @version $Revision: 1.3 $ $Date: 2007/07/11 20:48:46 $
 * @since 1.0
 */
public class ErrorLogHandlerExceptionResolver implements
		HandlerExceptionResolver, Ordered {
	
	@NotNull
	private ErrorLogManager errorLogManager;
	
	private int order = 0;
	
	/**
	 * Constructs a new <code>ErrorLogHandlerExceptionResolver</code>.
	 * 
	 * @param errorlogManager the ErrorLogManager facility to use
	 */
	public ErrorLogHandlerExceptionResolver(final ErrorLogManager errorlogManager) {
		this.errorLogManager = errorlogManager;
	}

	public ModelAndView resolveException(final HttpServletRequest request,
			final HttpServletResponse response, final Object handler, final Exception exception) {
		
		this.errorLogManager.recordError(exception);
		
		// Return null so that further processing can take over.
		return null;	
	}
	
	/**
	 * Sets the order value for this resolver.
	 * 
	 * @param order	The order value for this resolver.
	 */
	public void setOrder(final int order) {
		this.order = order;
	}

	public int getOrder() {
		return this.order;
	}
}
