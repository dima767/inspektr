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

import org.inspektr.audit.annotation.Auditable;

/**
 * Useful for testing.
 * 
 * @author Dmitriy Kopylenko
 * @version $Id: SimpleAuditableBean.java,v 1.3 2007/12/03 22:01:59 lleung Exp $
 * @since 1.0
 */
public class SimpleAuditableBean implements AuditableBean {

    @Auditable(applicationCode = "T_APP", action = "performing 'doSomething...'", resourceResolverClass=SimpleAuditableResourceResolver.class)
    public void doSomething(String stringArg) {
    }

    @Auditable(applicationCode = "T_APP", action = "performing 'returnSomething...'", resourceResolverClass=SimpleAuditableResourceResolver.class)
    public String returnSomething(Object o) {
        return "Hello";
    }
    
    @Auditable(applicationCode = "T_APP", action = "performing 'throwAnException...'", resourceResolverClass=SimpleAuditableResourceResolver.class)
    public void throwAnException() {
    	throw new RuntimeException();
    }
}
