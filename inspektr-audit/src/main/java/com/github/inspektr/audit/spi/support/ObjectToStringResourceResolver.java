package com.github.inspektr.audit.spi.support;

import com.github.inspektr.audit.spi.AuditResourceResolver;
import org.aspectj.lang.JoinPoint;

/**
 * Inspektr ResourceResolver that resolves resource as a target object's toString method call
 *
 * @author Dmitriy Kopylenko
 */
public class ObjectToStringResourceResolver implements AuditResourceResolver {

    public String[] resolveFrom(JoinPoint target, Object returnValue) {
        return new String[]{target.getTarget().toString()};
    }

    public String[] resolveFrom(JoinPoint target, Exception exception) {
        return new String[]{target.getTarget().toString() + "__EXCEPTION: [" + exception.getMessage() + "]"};
    }
}
