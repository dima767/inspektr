/**
 * Copyright (C) 2009 Rutgers, the State University of New Jersey.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.inspektr.error.support;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.inspektr.error.ErrorReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An <code>ErrorReporter</code> implementation that logs errors to a configured 
 * <code>log4j Appender</code>.
 * 
 * @author lleung
 * @version $Revision: 1.3 $ $Date: 2007/07/11 20:48:47 $
 * @since 1.0
 */
public final class Slf4jLoggingErrorReporter implements ErrorReporter {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass()); 

	public void reportError(final String applicationCode, final String principal,
			final String description) {
		logErrorRecord(applicationCode, principal, description);
	}

	public void reportError(final String applicationCode, final String principal, final Throwable throwable) {
		final StackTraceElement[] stackTraceElements = throwable.getStackTrace();
		final StringBuilder builder = new StringBuilder(512);
		builder.append("\n\t").append(throwable.toString());
		
		for (final StackTraceElement element : stackTraceElements) {
			builder.append("\n\tat ").append(element.toString());
		}

		logErrorRecord(applicationCode, principal, builder.toString());
		
	}
	
	private void logErrorRecord(final String applicationCode, final String principal, final String details) {
		final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");
		final StringBuilder builder = new StringBuilder(512);
		builder.append("\n\n===ERROR LOG record BEGIN==========================================")
               .append("\nAPPLICATION CODE: ")
               .append(applicationCode)
               .append("\nPRINCIPAL: ")
               .append(principal)
               .append("\nWHEN: ")
               .append(dateTimeFormat.format(new Date()))
               .append("\nDESCRIPTION: ")
               .append(details)
               .append("\n===ERROR LOG record END============================================")
               .append("\n\n");
        log.error(builder.toString());
	}
	

}
