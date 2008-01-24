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
package org.inspektr.webapp.domain;

import java.util.Date;

import org.inspektr.statistics.annotation.Statistic.Precision;

/**
 * Implementation of {@link Statistic}
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
public class StatisticImpl implements Statistic {

	/** Unique Id for Serialization */
	private static final long serialVersionUID = -6987781722258007646L;
	
	private String applicationCode;
	
	private int count;
	
	private Precision precision;
	
	private String what;
	
	private Date when;


	public void setApplicationCode(final String applicationCode) {
		this.applicationCode = applicationCode;
	}

	public void setCount(final int count) {
		this.count = count;
	}

	public void setPrecision(final Precision precision) {
		this.precision = precision;
	}

	public void setWhat(final String what) {
		this.what = what;
	}

	public void setWhen(final Date when) {
		this.when = when;
	}

	public String getApplicationCode() {
		return this.applicationCode;
	}

	public int getCount() {
		return this.count;
	}

	public Precision getPrecision() {
		return this.precision;
	}

	public String getWhat() {
		return this.what;
	}

	public Date getWhen() {
		return this.when;
	}
}
