/*
 * Copyright 2014 James Pether Sörling
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *	$Id$
 *  $HeadURL$
*/
package com.hack23.cia.web.impl.ui.application.views.user.committee.pagemode;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import com.hack23.cia.model.internal.application.data.committee.impl.ViewRiksdagenCommittee;
import com.hack23.cia.model.internal.application.system.impl.ApplicationEventGroup;
import com.hack23.cia.service.api.DataContainer;
import com.hack23.cia.web.impl.ui.application.action.ViewAction;
import com.hack23.cia.web.impl.ui.application.views.common.chartfactory.api.DecisionFlowChartManager;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.ChartIndicators;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.PageMode;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * The Class ParliamentDecisionFlowPageModContentFactoryImpl.
 */
@Component
public final class CommitteeDecisionFlowPageModContentFactoryImpl extends AbstractCommitteePageModContentFactoryImpl {


	/** The Constant PARLIAMENT_DECISION_FLOW. */
	private static final String COMMITTEE_DECISION_FLOW = "Committee decision flow";

	@Autowired
	private DecisionFlowChartManager decisionFlowChartManager;
	
	/**
	 * Instantiates a new parliament decision flow page mod content factory impl.
	 */
	public CommitteeDecisionFlowPageModContentFactoryImpl() {
		super();
	}

	@Override
	public boolean matches(final String page, final String parameters) {
		return NAME.equals(page) && !StringUtils.isEmpty(parameters) && parameters.contains(PageMode.CHARTS.toString())
				&& parameters.contains(ChartIndicators.DECISION_FLOW_CHART.toString());
	}

	@Secured({ "ROLE_ANONYMOUS", "ROLE_USER", "ROLE_ADMIN" })
	@Override
	public Layout createContent(final String parameters, final MenuBar menuBar, final Panel panel) {
		final VerticalLayout panelContent = createPanelContent();
		final String pageId = getPageId(parameters);
		
		getCommitteeMenuItemFactory().createCommitteeeMenuBar(menuBar, pageId);
		
		final DataContainer<ViewRiksdagenCommittee, String> dataContainer = getApplicationManager()
				.getDataContainer(ViewRiksdagenCommittee.class);

		final ViewRiksdagenCommittee viewRiksdagenCommittee = dataContainer.load(pageId);

		if (viewRiksdagenCommittee != null) {	
			
			final Map<String, List<ViewRiksdagenCommittee>> committeeMap = dataContainer.getAll().stream().collect(Collectors.groupingBy(c -> c.getEmbeddedId().getOrgCode().toUpperCase(Locale.ENGLISH)));			
		
			panelContent.addComponent(decisionFlowChartManager.createCommitteeDecisionFlow(viewRiksdagenCommittee, committeeMap));		

		}
        
		getPageActionEventHelper().createPageEvent(ViewAction.VISIT_COMMITTEE_VIEW, ApplicationEventGroup.USER, NAME,
				parameters, pageId);
		panel.setCaption(new StringBuilder().append(NAME).append("::").append(COMMITTEE_DECISION_FLOW).toString());

		return panelContent;

	}

}