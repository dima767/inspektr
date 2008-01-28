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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.inspektr.common.ioc.annotation.NotNull;
import org.inspektr.statistics.annotation.Statistic.Precision;
import org.inspektr.webapp.domain.Statistic;
import org.inspektr.webapp.domain.StatisticSearchRequest.SearchType;
import org.inspektr.webapp.domain.StatisticSearchRequestImpl;
import org.inspektr.webapp.service.StatisticManager;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
public final class StatisticSearchRequestController extends AbstractController {
	
	@NotNull
	private StatisticManager statisticManager;
	
	public StatisticSearchRequestController(final StatisticManager statisticManager) {
		this.statisticManager = statisticManager;
	}

	protected ModelAndView handleRequestInternal(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		final StatisticSearchRequestImpl statisticSearchRequest = new StatisticSearchRequestImpl();
        final ServletRequestDataBinder dataBinder = new ServletRequestDataBinder(statisticSearchRequest);
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),false));
        dataBinder.registerCustomEditor(Precision.class, new PrecisionEnumPropertyEditor());
        dataBinder.registerCustomEditor(SearchType.class, new SearchTypeEnumPropertyEditor());
        
        dataBinder.bind(request);
		
        final List<Statistic> statistics = this.statisticManager.findStatisticsBy(statisticSearchRequest);
        final ModelAndView modelAndView = new ModelAndView("viewStatisticEntries");
        modelAndView.addAllObjects(referenceData(request));
        modelAndView.addObject("statistics", statistics);
		modelAndView.addObject("pageTitle", modelAndView.getViewName());
		modelAndView.addObject("statisticSearchRequest", statisticSearchRequest);
        
		return modelAndView;
	}

	protected Map<String, Object> referenceData(final HttpServletRequest request) {
		final Map<String, Object> model = new HashMap<String, Object>();
		
		final List<String> applicationCodes = this.statisticManager.getApplicationCodes();
		
		model.put("applicationCodes", applicationCodes);
		model.put("precisions", Precision.values());
		return model;
	}
	
}
