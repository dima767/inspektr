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
package org.inspektr.error.spi;

/**
 * An SPI interface to be implemented by applications using the
 * {@link edu.rutgers.enterprise.inspektr.errorlog.support.DefaultErrorLogManager}.
 * This interface will provide contextual information regarding the principal incurring
 * the error condition to be reported.
 * 
 * @author lleung
 * @version $Revision: 1.2 $ $Date: 2007/07/11 20:48:47 $
 * @since 1.0
 */
public interface CurrentContextPrincipalResolver {
	
	/**
	 * Resolve the principal involved in the context incurring the error condition.
	 * 
	 * @return	A description of the principal.
	 */
	String resolve();

}
