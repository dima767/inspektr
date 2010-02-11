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

import java.util.List;

/**
 * Interface describing match criteria in terms of a SQL select clause.
 *
 * @author Middleware
 * @version $Revision: $
 *
 */
public interface WhereClauseMatchCriteria {

  /**
   * @return Immutable list of parameter values for a parameterized query or
   * an empty list if the where clause is not parameterized.
   */
  List<?> getParameterValues();
  
  /**
   * @return The where clause text beginning with the string " WHERE" such that
   * the return value can be directly appended to a SQL statement with no
   * where clause.
   */
  String toString();

}
