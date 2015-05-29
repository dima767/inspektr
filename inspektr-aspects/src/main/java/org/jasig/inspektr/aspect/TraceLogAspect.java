package org.jasig.inspektr.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author Scott Battaglia
 * @author Misagh Moayyed
 * @since 1.2
 */
@Aspect
public class TraceLogAspect {

    /**
     * Added TRACE-level log entries for the executing target.
     *
     * @param proceedingJoinPoint the proceeding join point
     * @return the object
     * @throws Throwable the throwable
     */
    @Around("(execution (public * *(..))) && !(execution( * *.set*(..)))")
    public Object traceMethod(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object returnVal = null;
        final Logger logger = this.getLog(proceedingJoinPoint);
        final String methodName = proceedingJoinPoint.getSignature().getName();

        try {
            if (logger.isTraceEnabled()) {
                final Object[] args = proceedingJoinPoint.getArgs();
                final String arguments;
                if (args == null || args.length == 0) {
                    arguments = "";
                } else {
                    arguments = Arrays.deepToString(args);
                }
                logger.trace("Entering method [{}] with arguments [{}]", methodName, arguments);
            }
            returnVal = proceedingJoinPoint.proceed();
            return returnVal;
        } finally {
            logger.trace("Leaving method [{}] with return value [{}].", methodName,
                    (returnVal != null ? returnVal.toString() : "null"));
        }
    }

    /**
     * Gets the logger object for the join point target.
     *
     * @param joinPoint the join point
     * @return the log
     */
    protected Logger getLog(final JoinPoint joinPoint) {
        final Object target = joinPoint.getTarget();

        if (target != null) {
            return LoggerFactory.getLogger(target.getClass());
        }

        return LoggerFactory.getLogger(getClass());
    }

}
