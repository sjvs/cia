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
package com.hack23.cia.web.impl.ui.application.views.user.home;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.hack23.cia.web.impl.ui.application.views.common.pagemode.PageModeContentFactory;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.UserViews;
import com.hack23.cia.web.impl.ui.application.views.user.common.AbstractUserView;
import com.vaadin.spring.annotation.SpringView;

/**
 * The Class PartyView.
 */
@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.INTERFACES)
@SpringView(name = UserHomeView.NAME)
public final class UserHomeView extends AbstractUserView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant NAME. */
	public static final String NAME = UserViews.USERHOME_VIEW_NAME;

	/**
	 * Instantiates a new user home view.
	 *
	 * @param context
	 *            the context
	 */
	public UserHomeView(final ApplicationContext context) {
		super(context.getBeansOfType(PageModeContentFactory.class), NAME);
	}

}