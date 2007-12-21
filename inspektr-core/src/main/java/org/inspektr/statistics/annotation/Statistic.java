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
package org.inspektr.statistics.annotation;

import java.util.Calendar;
import java.util.Date;

/**
 * Notes that statistics about the number of times this method has been called should be logged.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
public @interface Statistic {

	// TODO provides a method on Precision to check equality of two dates based on precision.
	public static enum Precision {MINUTE
		{
			public Date normalize(final Date date) {
				final Calendar c1 = Calendar.getInstance();
				c1.setTime(date);
				
				c1.set(Calendar.SECOND, 0);
				c1.set(Calendar.MILLISECOND, 0);
				
				return c1.getTime();
			}		
		}, HOUR {
			public Date normalize(final Date date) {
				final Calendar c1 = Calendar.getInstance();
				c1.setTime(date);
				
				c1.set(Calendar.SECOND, 0);
				c1.set(Calendar.MILLISECOND, 0);
				c1.set(Calendar.MINUTE, 0);
				
				return c1.getTime();
			}
		}, DAY {
			public Date normalize(final Date date) {
				final Calendar c1 = Calendar.getInstance();
				c1.setTime(date);
				
				c1.set(Calendar.SECOND, 0);
				c1.set(Calendar.MILLISECOND, 0);
				c1.set(Calendar.MINUTE, 0);
				c1.set(Calendar.HOUR_OF_DAY, 0);
				
				return c1.getTime();
			}			
		};
		
		public abstract Date normalize(Date date);
	
		public final boolean same(final Date normalizedDate, final Date date2) {
			final Date normalizedDate2 = normalize(date2);
			
			return normalizedDate.equals(normalizedDate2);
		}
	}
	
	String applicationCode() default "";
	
	Precision[] requiredPrecision() default Precision.HOUR;
	
	String name();	
}
