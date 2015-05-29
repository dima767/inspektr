package org.jasig.inspektr.audit.spi.support;

import org.aspectj.lang.JoinPoint;
import org.jasig.inspektr.audit.spi.AuditResourceResolver;
import org.jasig.inspektr.audit.util.AopUtils;

import java.util.Arrays;

/**
 * Converts the first argument object into a String resource identifier.
 * If the resource string is set, it will return the argument values into a list,
 * prefixed by the string. otherwise simply returns the argument value as a string.
 * @author Scott Battaglia
 * @author Misagh Moayyed
 */
public final class FirstParameterAuditResourceResolver implements AuditResourceResolver {

    private String resourceString;

    public void setResourceString(final String resourceString) {
        this.resourceString = resourceString;
    }

    @Override
    public String[] resolveFrom(final JoinPoint joinPoint, final Object retval) {
        return toResources(getArguments(joinPoint));
    }

    @Override
    public String[] resolveFrom(final JoinPoint joinPoint, final Exception exception) {
        return toResources(getArguments(joinPoint));
    }

    private Object[] getArguments(final JoinPoint joinPoint) {
        return AopUtils.unWrapJoinPoint(joinPoint).getArgs();
    }
    /**
     * Turn the arguments into a list.
     *
     * @param args the args
     * @return the string[]
     */
    private String[] toResources(final Object[] args) {
        if (this.resourceString != null) {
            return new String[]{this.resourceString + Arrays.asList((Object[]) args[0])};
        }
        return new String[] {args[0].toString()};
    }
}
