package com.github.inspektr.audit;

import java.io.Serializable;

/**
 * Encapsulates a runtime execution context at advised audit points. Used for diagnostic purposes
 * to provide clear contextual information about any given audit point in case of runtime failures
 * during audit capturing operation, e.g. any assert failures, etc.
 *
 * @author Dmitriy Kopylenko
 * @since 1.0.6
 */
public interface AuditPointRuntimeInfo extends Serializable {

    /**
     * @return String representation of this audit point runtime execution context
     */
    String asString();
}
