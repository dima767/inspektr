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
package org.inspektr.webapp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.inspektr.common.ioc.annotation.NotNull;
import org.inspektr.webapp.domain.LogEntry;
import org.inspektr.webapp.domain.LogSearchRequestImpl;
import org.inspektr.webapp.service.LogManager;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Searches for and returns the results for specific Logs.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
public final class LogSearchRequestController extends AbstractController {
	
	@NotNull
	private final LogManager logRequestManager;
	
	public LogSearchRequestController(final LogManager logRequestManager) {
		this.logRequestManager = logRequestManager;
	}

	protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		
		final LogSearchRequestImpl logSearchRequest = new LogSearchRequestImpl();
        final ServletRequestDataBinder dataBinder = new ServletRequestDataBinder(logSearchRequest);
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm"),false));
        
        dataBinder.bind(request);
        
        final List<LogEntry> logRequests = this.logRequestManager.searchBy(logSearchRequest);
        
        final ModelAndView modelAndView = new ModelAndView("viewLogEntries");
        modelAndView.addAllObjects(referenceData(request));
        modelAndView.addObject("logEntries", logRequests);
		modelAndView.addObject("pageTitle", modelAndView.getViewName());
		modelAndView.addObject("logSearchRequest", logSearchRequest);
        
		return modelAndView;
	}
	
	protected Map<String, Object> referenceData(final HttpServletRequest request) {
		final Map<String, Object> model = new HashMap<String, Object>();
		
		final Set<String> applicationCodes = this.logRequestManager.getApplicationCodes();
		
		model.put("applicationCodes", applicationCodes);
		return model;
	}
}
