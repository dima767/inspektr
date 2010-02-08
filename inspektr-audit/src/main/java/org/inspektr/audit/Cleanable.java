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
package org.inspektr.audit;

/**
 * Describes a resource that supports purging auditing, statistics, or
 * error data that meets arbitrary criteria.
 *
 * @author Marvin S. Addison
 * @version $Revision: $
 *
 */
public interface Cleanable {
 
  /**
   * Purges records meeting arbitrary criteria defined by implementers.
   */
  void clean();

}
