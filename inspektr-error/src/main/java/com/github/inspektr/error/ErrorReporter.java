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

package com.github.inspektr.error;

/**
 * Interface for reporting an error. Concrete implementations may choose to report
 * errors via different means, e.g. log files, database, email.
 * 
 * @author Alice Leung
 * @version $Revision: 1.3 $ $Date: 2007/07/11 20:48:46 $
 * @since 1.0
 */
public interface ErrorReporter {
	
	/**
	 * Reports the specified error.
	 * 
	 * @param applicationCode	The application identifier.
	 * @param principal			Optional application-specific context information on the
	 *      principal incurring the error. 
	 * @param errorDescription 	Details on the error.
	 */
	void reportError(String applicationCode, String principal, String errorDescription);
	
	/**
	 * Reports the specified <code>Throwable</code>.
	 * 
	 * @param applicationCode	The application identifier.
	 * @param principal			Optional application-specific context information on the 
	 * 		principal incurring the error.
	 * @param throwable			The <code>Throwable</code> to be reported.
	 */
	void reportError(String applicationCode, String principal, Throwable throwable);

}
