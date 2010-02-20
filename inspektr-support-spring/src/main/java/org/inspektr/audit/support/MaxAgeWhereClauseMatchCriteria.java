/**
 * Copyright (C) 2010 Virginia Tech.
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
package org.inspektr.audit.support;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Produces a where clause to select audit records older than some arbitrary
 * cutoff age in days.
 *
 * @author Marvin S. Addison
 * @version $Revision: $
 * @since 1.0
 *
 */
public final class MaxAgeWhereClauseMatchCriteria extends AbstractWhereClauseMatchCriteria {
 
  /** Name of creation date column name in audit record table */
  private static final String DATE_COLUMN = "AUD_DATE";

  /** Maximum age of records */
  protected int maxAge;


  /**
   * Creates a new instance that selects audit records older than the given
   * number of days as measured from the present time.
   *
   * @param maxAgeDays Cutoff age of records in days.
   */
  public MaxAgeWhereClauseMatchCriteria(final int maxAgeDays) {
    this.maxAge = maxAgeDays;
    addCriteria(DATE_COLUMN, "<");
  }
  
 
  /** {@inheritDoc} */
  public List<?> getParameterValues() {
    final Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DAY_OF_MONTH, -this.maxAge);
    return Collections.singletonList(cal.getTime());
  }
  
  /** {@inheritDoc} */
  protected String getDateColumn() {
    return DATE_COLUMN;
  }
}
