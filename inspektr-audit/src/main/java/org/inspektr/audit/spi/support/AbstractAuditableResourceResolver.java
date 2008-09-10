package org.inspektr.audit.spi.support;

import org.aspectj.lang.JoinPoint;
import org.inspektr.audit.spi.AuditableResourceResolver;

/**
 * Abstract AuditableResourceResolver for when the resource is the same regardless of an exception or not.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
public abstract class AbstractAuditableResourceResolver implements
		AuditableResourceResolver {

	public final String[] resolveFrom(final JoinPoint joinPoint, final Object retVal) {
		return createResource(joinPoint);
	}

	public final String[] resolveFrom(final JoinPoint joinPoint, final Exception e) {
		return createResource(joinPoint);
	}
	
	protected abstract String[] createResource(final JoinPoint joinPoint);
}
