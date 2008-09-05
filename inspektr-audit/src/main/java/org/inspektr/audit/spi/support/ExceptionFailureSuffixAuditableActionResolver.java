package org.inspektr.audit.spi.support;

import org.aspectj.lang.JoinPoint;
import org.inspektr.audit.annotation.Auditable;
import org.inspektr.audit.spi.AuditableActionResolver;

/**
 * Similar to the {@link DefaultAuditableActionResolver} but it appends the correct suffix
 * depending on whether an exception was thrown or not.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
public final class ExceptionFailureSuffixAuditableActionResolver implements
		AuditableActionResolver {

	public String resolveFrom(JoinPoint auditableTarget, Object retval,
			Auditable auditable) {
		return auditable.action() + auditable.successSuffix();
	}

	public String resolveFrom(JoinPoint auditableTarget, Exception exception,
			Auditable auditable) {
		return auditable.action() + auditable.failureSuffix();
	}
}
