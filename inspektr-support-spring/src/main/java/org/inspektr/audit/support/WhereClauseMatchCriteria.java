/*
  $Id: $

  Copyright (C) 2008-2009 Virginia Tech.
  All rights reserved.

  SEE LICENSE FOR MORE INFORMATION

  Author:  Middleware
  Email:   middleware@vt.edu
  Version: $Revision: $
  Updated: $Date: $
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
