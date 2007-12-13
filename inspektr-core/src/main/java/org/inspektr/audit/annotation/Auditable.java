/**
 *  Copyright 2007 Rutgers, the State University of New Jersey
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *      
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.inspektr.audit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.inspektr.audit.spi.AuditableActionResolver;
import org.inspektr.audit.spi.AuditableResourceResolver;
import org.inspektr.audit.spi.support.DefaultAuditableActionResolver;


/**
 * States that this method should be logged for auditing purposes.
 * 
 * @author Alice Leung
 * @author Dmitriy Kopylenko
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Auditable {
	
	/**
	 * Identifier for this particular application in the audit trail logs.  This attribute should only be used to override the basic application code when you want to differentiate a section of the code.
	 * @return the application code or an empty String if none is set.
	 */
	String applicationCode() default "";

	/**
	 * The action to write to the log when we audit this method.  Value must be defined.
	 * @return the action to write to the logs.
	 */
	String action();
	
	/**
	 * The suffix to append to the action when resolvers are used that can detect success.
	 * @return the suffix.
	 */
	String successSuffix() default "";
	
	/**
	 * The suffix to append to the action when resolvers are used that can detect failure.
	 * @return the suffix
	 */
	String failureSuffix() default "";
	
	/**
	 * Whether to use one of the built in resolvers or not.
	 * @return the resolver to use.  Defaults to the {@link DefaultAuditableActionResolver}, which means just return the action.  
	 */
	Class <? extends AuditableActionResolver> actionResolverClass() default DefaultAuditableActionResolver.class;
	
	/**
	 * Returns the AuditableResourceResolver that knows how to resolve the resource.
	 * 
	 * @return the {@link AuditableResourceResolver} that knows how to resolve the resource.
	 */
	Class <? extends AuditableResourceResolver> resourceResolverClass();
}
