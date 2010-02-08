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

import java.util.Collections;
import java.util.List;

/**
 * Constructs a where clause that matches no records.
 *
 * @author Marvin S. Addison
 * @version $Revision: $
 *
 */
public class NoMatchWhereClauseMatchCriteria extends AbstractWhereClauseMatchCriteria {
  
  public NoMatchWhereClauseMatchCriteria() {
    sbClause.append("WHERE 0=1");
  }

  /** {@inheritDoc} */
  public List<?> getParameterValues()
  {
    return Collections.emptyList();
  }

}
