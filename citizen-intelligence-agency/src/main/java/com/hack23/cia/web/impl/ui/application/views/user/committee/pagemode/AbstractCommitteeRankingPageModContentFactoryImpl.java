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

import org.springframework.beans.factory.annotation.Autowired;

import com.hack23.cia.web.impl.ui.application.views.common.menufactory.api.CommitteeRankingMenuItemFactory;
import com.hack23.cia.web.impl.ui.application.views.common.pagemode.AbstractPageModContentFactoryImpl;

/**
 * The Class AbstractRankingPageModContentFactoryImpl.
 */
public abstract class AbstractCommitteeRankingPageModContentFactoryImpl extends AbstractPageModContentFactoryImpl {

	/** The committee ranking menu item factory. */
	@Autowired
	private CommitteeRankingMenuItemFactory committeeRankingMenuItemFactory;


	/**
	 * Instantiates a new abstract ranking page mod content factory impl.
	 */
	protected AbstractCommitteeRankingPageModContentFactoryImpl() {
		super();
	}


	/**
	 * Gets the committee ranking menu item factory.
	 *
	 * @return the committee ranking menu item factory
	 */
	protected final CommitteeRankingMenuItemFactory getCommitteeRankingMenuItemFactory() {
		return committeeRankingMenuItemFactory;
	}

}
