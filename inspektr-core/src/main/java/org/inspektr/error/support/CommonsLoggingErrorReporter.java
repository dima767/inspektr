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
package org.inspektr.error.support;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.inspektr.error.ErrorReporter;

/**
 * An <code>ErrorReporter</code> implementation that logs errors to a configured 
 * <code>log4j Appender</code>.
 * 
 * @author lleung
 * @version $Revision: 1.3 $ $Date: 2007/07/11 20:48:47 $
 * @since 1.0
 */
public final class CommonsLoggingErrorReporter implements ErrorReporter {
	
	private final Log log = LogFactory.getLog(this.getClass()); 

	public void reportError(final String applicationCode, final String principal,
			final String description) {
		logErrorRecord(applicationCode, principal, description);
	}

	public void reportError(final String applicationCode, final String principal,
			final Throwable throwable) {
		final StackTraceElement[] stackTraceElements = throwable.getStackTrace();
		final StringBuilder builder = new StringBuilder(512);
		builder.append("\n\t").append(throwable.toString());
		
		for (final StackTraceElement element : stackTraceElements) {
			builder.append("\n\tat ").append(element.toString());
		}

		logErrorRecord(applicationCode, principal, builder.toString());
		
	}
	
	private void logErrorRecord(final String applicationCode, final String principal, final String details) {
		final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");
		final StringBuilder builder = new StringBuilder(512);
		builder.append("\n\n===ERROR LOG record BEGIN==========================================")
               .append("\nAPPLICATION CODE: " + applicationCode)
               .append("\nPRINCIPAL: " + principal)
               .append("\nWHEN: " + datetimeFormat.format(new Date()))
               .append("\nDESCRIPTION: " + details)
               .append("\n===ERROR LOG record END============================================")
               .append("\n\n");
        log.error(builder.toString());
	}
	

}
