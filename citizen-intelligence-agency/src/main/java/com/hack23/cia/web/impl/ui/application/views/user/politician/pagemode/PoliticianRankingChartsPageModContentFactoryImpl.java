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
package com.hack23.cia.web.impl.ui.application.views.user.politician.pagemode;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.hack23.cia.model.internal.application.system.impl.ApplicationEventGroup;
import com.hack23.cia.web.impl.ui.application.action.ViewAction;
import com.hack23.cia.web.impl.ui.application.views.common.chartfactory.ChartDataManager;
import com.hack23.cia.web.impl.ui.application.views.common.dataseriesfactory.PartyDataSeriesFactory;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.PageMode;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.UserViews;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * The Class PoliticianRankingChartsPageModContentFactoryImpl.
 */
@Service
public final class PoliticianRankingChartsPageModContentFactoryImpl
		extends AbstractPoliticianRankingPageModContentFactoryImpl {

	/** The Constant NAME. */
	public static final String NAME = UserViews.POLITICIAN_RANKING_VIEW_NAME;

	/** The Constant CHARTS. */
	private static final String CHARTS = "Charts:";

	/** The chart data manager. */
	@Autowired
	private ChartDataManager chartDataManager;

	/** The data series factory. */
	@Autowired
	private PartyDataSeriesFactory dataSeriesFactory;

	/**
	 * Instantiates a new politician ranking charts page mod content factory
	 * impl.
	 */
	public PoliticianRankingChartsPageModContentFactoryImpl() {
		super();
	}

	@Override
	public boolean matches(final String page, final String parameters) {
		return NAME.equals(page)
				&& (!StringUtils.isEmpty(parameters) && parameters.contains(PageMode.CHARTS.toString()));
	}

	@Secured({ "ROLE_ANONYMOUS", "ROLE_USER", "ROLE_ADMIN" })
	@Override
	public Layout createContent(final String parameters, final MenuBar menuBar, final Panel panel) {
		final VerticalLayout panelContent = createPanelContent();

		final String pageId = getPageId(parameters);

		getPoliticianRankingMenuItemFactory().createPoliticianRankingMenuBar(menuBar);

		final Layout chartLayout = new HorizontalLayout();
		chartLayout.setSizeFull();

		final Component chartPanelAll = chartDataManager
				.createChartPanel(dataSeriesFactory.createPartyChartTimeSeriesAll(), "All");
		if (chartPanelAll != null) {
			chartLayout.addComponent(chartPanelAll);
		}

		final Component chartPanelCurrent = chartDataManager
				.createChartPanel(dataSeriesFactory.createPartyChartTimeSeriesCurrent(), "Current");
		if (chartPanelCurrent != null) {
			chartLayout.addComponent(chartPanelCurrent);
		}
		panelContent.addComponent(chartLayout);

		panel.setCaption(CHARTS + parameters);

		getPageActionEventHelper().createPageEvent(ViewAction.VISIT_POLITICIAN_RANKING_VIEW, ApplicationEventGroup.USER,
				NAME, parameters, pageId);

		return panelContent;

	}

}
