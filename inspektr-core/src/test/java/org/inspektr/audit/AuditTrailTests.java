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

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;


public class AuditTrailTests extends AbstractDependencyInjectionSpringContextTests {
    
    AuditableBean theBean;
       
    public void setTheBean(AuditableBean theBean) {
        this.theBean = theBean;
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[] {"classpath:/testContext.xml"};
    }
    
    public void testAuditingMachinery() {
        this.theBean.doSomething("String arg");
        this.theBean.returnSomething(new Object());
        try {
        	this.theBean.throwAnException();
        } catch (RuntimeException e) {}
    }
}
