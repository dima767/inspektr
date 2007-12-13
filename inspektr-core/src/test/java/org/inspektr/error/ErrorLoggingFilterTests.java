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
package org.inspektr.error;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.inspektr.error.web.ErrorLoggingFilter;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.springframework.test.AssertThrows;


/**
 * Test suite for {@link org.inspektr.errors.web.ErrorLoggingFilter}
 * 
 * @author lleung
 * @version $Revision: 1.1 $ $Date: 2007/06/21 19:25:20 $
 */
public class ErrorLoggingFilterTests extends
		AbstractDependencyInjectionSpringContextTests {
	
	private ErrorLoggingFilter errorLoggingFilter;
	
	@Override
    protected String[] getConfigLocations() {
        return new String[] {"classpath:/testContext.xml"};
    }
	
	public void setErrorLoggingFilter(ErrorLoggingFilter filter) {
		this.errorLoggingFilter = filter;
	}
	
	public void testDoFilter() throws Exception {
		
		this.errorLoggingFilter.init(new MockFilterConfig());
		
		new AssertThrows(ServletException.class) {
			public void test() throws Exception{
				errorLoggingFilter.doFilter(new MockHttpServletRequest(), new MockHttpServletResponse(), new MockFilterChain(new ServletException("Testing ServletException")));
			}
		}.runTest();
		
		new AssertThrows(IOException.class) {
			public void test() throws Exception{
				errorLoggingFilter.doFilter(new MockHttpServletRequest(), new MockHttpServletResponse(), new MockFilterChain(new IOException("Testing IOException")));
			}
		}.runTest();
		
		new AssertThrows(ServletException.class) {
			public void test() throws Exception{
				errorLoggingFilter.doFilter(new MockHttpServletRequest(), new MockHttpServletResponse(), new MockFilterChain(new RuntimeException("Testing RuntimeException")));
			}
		}.runTest();
		
		
		errorLoggingFilter.doFilter(new MockHttpServletRequest(), new MockHttpServletResponse(), new MockFilterChain(null));
		
		this.errorLoggingFilter.destroy();
		
	}
	
	
	private class MockFilterChain implements FilterChain {
		
		private Throwable throwable;
		
		public MockFilterChain(Throwable t) {
			this.throwable = t;
		}

		public void doFilter(ServletRequest arg0, ServletResponse arg1) throws IOException, ServletException {

			  if (this.throwable != null) {
				  if (this.throwable instanceof ServletException) throw (ServletException)this.throwable;
				  if (this.throwable instanceof IOException) throw (IOException)throwable;
				  if (this.throwable instanceof RuntimeException) throw (RuntimeException)this.throwable;
				  throw new ServletException(this.throwable);
			  }
		}
		
	}
}
