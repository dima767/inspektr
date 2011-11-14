package com.github.inspektr.audit;

import org.aspectj.lang.JoinPoint;

/**
 * Wrapper around AspectJ's JoinPoint containing the runtime execution info for current audit points
 *
 * @author Dmitriy Kopylenko
 *
 * @since 1.0.6
 */
public class AspectJAuditPointRuntimeInfo  implements AuditPointRuntimeInfo {

    private JoinPoint currentJoinPoint;

    public AspectJAuditPointRuntimeInfo(JoinPoint currentJoinPoint) {
        this.currentJoinPoint = currentJoinPoint;
    }

    public String asString() {
        return this.currentJoinPoint.toLongString();
    }
}
