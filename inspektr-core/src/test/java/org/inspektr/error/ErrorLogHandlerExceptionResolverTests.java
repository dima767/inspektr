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
package org.inspektr.error;

import org.inspektr.error.web.ErrorLogHandlerExceptionResolver;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;


/**
 * Test suite for {@link org.inspektr.error.web.ErrorLogHandlerExceptionResolver}.
 * 
 * @author lleung
 * @version $Revision: 1.1 $ $Date: 2007/06/19 19:30:10 $
 */
public class ErrorLogHandlerExceptionResolverTests extends
		AbstractDependencyInjectionSpringContextTests {
	
	private ErrorLogHandlerExceptionResolver errorLogHandlerExceptionResolver;
	
	@Override
    protected String[] getConfigLocations() {
        return new String[] {"classpath:/testContext.xml"};
    }
	
	public void testReportException() throws Exception {
		this.errorLogHandlerExceptionResolver.resolveException(null, null, null, new Exception("Testing Resolver"));
	}
	
	public void setErrorLogHandlerExceptionResolver(final ErrorLogHandlerExceptionResolver resolver) {
		this.errorLogHandlerExceptionResolver = resolver;
	}

}
