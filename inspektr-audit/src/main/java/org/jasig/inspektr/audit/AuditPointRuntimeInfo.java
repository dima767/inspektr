/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.inspektr.audit;

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
