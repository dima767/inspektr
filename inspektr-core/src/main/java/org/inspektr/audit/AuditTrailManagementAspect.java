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
package org.inspektr.audit;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.inspektr.audit.annotation.Auditable;
import org.inspektr.audit.spi.AuditableActionResolver;
import org.inspektr.audit.spi.AuditablePrincipalResolver;
import org.inspektr.audit.spi.AuditableResourceResolver;
import org.inspektr.audit.spi.support.BooleanAuditableActionResolver;
import org.inspektr.audit.spi.support.DefaultAuditableActionResolver;
import org.inspektr.audit.spi.support.ObjectCreationAuditableActionResolver;
import org.inspektr.audit.spi.support.ReturnValueAsStringResourceResolver;
import org.inspektr.common.ioc.annotation.NotEmpty;
import org.inspektr.common.ioc.annotation.NotNull;
import org.inspektr.common.spi.ClientInfoResolver;
import org.inspektr.common.spi.support.DefaultClientInfoResolver;
import org.inspektr.common.web.ClientInfo;
import org.inspektr.common.web.ClientInfoHolder;
import org.springframework.util.StringUtils;

/**
 * A POJO style aspect modularizing management of an audit trail data concern.
 * 
 * @author Dmitriy Kopylenko
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 */
@Aspect
public final class AuditTrailManagementAspect {
	
	private final Log log = LogFactory.getLog(this.getClass());

	@NotNull
    private final AuditablePrincipalResolver auditablePrincipalResolver;
    
	@NotEmpty
    private final Map<Class <? extends AuditableActionResolver>, AuditableActionResolver> auditableActionResolvers = new HashMap<Class <? extends AuditableActionResolver>, AuditableActionResolver>();

	@NotEmpty
	private final Map<Class <? extends AuditableResourceResolver>, AuditableResourceResolver> auditableResourceResolvers = new HashMap<Class <? extends AuditableResourceResolver>, AuditableResourceResolver>();

	@NotEmpty
    private final List<AuditTrailManager> auditTrailManagers;
	
	@NotNull
	private final String applicationCode;

	@NotNull
	private ClientInfoResolver clientInfoResolver = new DefaultClientInfoResolver();
    
	/**
	 * Constructs an AuditTrailManagementAspect with the following parameters.  Also, registers some default AuditableActionResolvers including the
	 * {@link DefaultAuditableActionResolver}, the {@link BooleanAuditableActionResolver} and the {@link ObjectCreationAuditableActionResolver}.
	 * 
	 * @param auditablePrincipalResolver
	 * @param auditableResourceResolvers
	 * @param auditTrailManagers
	 */
    public AuditTrailManagementAspect(final AuditablePrincipalResolver auditablePrincipalResolver, final List<AuditableResourceResolver> auditableResourceResolvers, final List<AuditTrailManager> auditTrailManagers, final String applicationCode) {
    	this.auditablePrincipalResolver = auditablePrincipalResolver;
    	this.auditTrailManagers = auditTrailManagers;
    	
    	this.auditableActionResolvers.put(DefaultAuditableActionResolver.class, new DefaultAuditableActionResolver());
    	this.auditableActionResolvers.put(BooleanAuditableActionResolver.class, new BooleanAuditableActionResolver());
    	this.auditableActionResolvers.put(ObjectCreationAuditableActionResolver.class, new ObjectCreationAuditableActionResolver());
    	this.applicationCode = applicationCode;
    	
    	for (final AuditableResourceResolver resolver : auditableResourceResolvers) {
    		this.auditableResourceResolvers.put(resolver.getClass(), resolver);
    	}
    	
    	this.auditableResourceResolvers.put(ReturnValueAsStringResourceResolver.class, new ReturnValueAsStringResourceResolver());
    }
    
    @Around(value="@annotation(auditable)", argNames="auditable")
    public Object handleAuditTrail(final ProceedingJoinPoint joinPoint, final Auditable auditable) throws Throwable {
    	
    	String currentPrincipal = null;
    	String auditableResource = null;
    	String action = null;
    	try {
	    	final Object retval = joinPoint.proceed();
	    	
	        currentPrincipal = this.auditablePrincipalResolver.resolveFrom(joinPoint, retval);
	        if (currentPrincipal != null) {
	        	auditableResource = this.auditableResourceResolvers.get(auditable.resourceResolverClass()).resolveFrom(joinPoint, retval);		        
		        action = auditableActionResolvers.get(auditable.actionResolverClass()).resolveFrom(joinPoint, retval, auditable);
	        }
	        
	        return retval;
    	} catch (Exception e) {
    		currentPrincipal = this.auditablePrincipalResolver.resolveFrom(joinPoint, e);
	        if (currentPrincipal != null) {
	        	auditableResource = this.auditableResourceResolvers.get(auditable.resourceResolverClass()).resolveFrom(joinPoint, e);
		        action = auditableActionResolvers.get(auditable.actionResolverClass()).resolveFrom(joinPoint, e, auditable);
	        }
    		throw e;
    	} finally {
    		if (currentPrincipal == null) {
	            log.warn("Recording of audit trail information did not succeed: cannot resolve the principal.");
	        } else if (auditableResource == null) {
	            log.warn("Recording of audit trail information did not succeed: cannot resolve the auditable resource.");
	        } else {
	        	final String applicationCode = StringUtils.hasText(auditable.applicationCode()) ? auditable.applicationCode() : this.applicationCode;
	        	final ClientInfo clientInfo = ClientInfoHolder.getClientInfo();
	        	final AuditableActionContext auditContext = new AuditableActionContext(currentPrincipal, auditableResource, action, applicationCode, new Date(), clientInfo.getClientIpAddress(), clientInfo.getServerIpAddress());
    	        // Finally record the audit trail info
    	        for(AuditTrailManager manager : auditTrailManagers) {
    	            manager.record(auditContext);
    	        }
	        }
    	}
    }
    
    public void setAdditionalAuditableActionResolvers(final List<AuditableActionResolver> auditableActionResolvers) {
    	for (final AuditableActionResolver resolver : auditableActionResolvers) {
    		this.auditableActionResolvers.put(resolver.getClass(), resolver);
    	}
    }

	public void setClientInfoResolver(final ClientInfoResolver factory) {
		this.clientInfoResolver = factory;
	} 
}
