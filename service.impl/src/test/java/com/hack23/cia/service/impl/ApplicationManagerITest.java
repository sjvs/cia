/*
 * Copyright 2010 James Pether Sörling
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
package com.hack23.cia.service.impl;

import java.io.Serializable;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hack23.cia.model.external.riksdagen.person.impl.PersonData;
import com.hack23.cia.model.internal.application.data.committee.impl.ViewRiksdagenCommittee;
import com.hack23.cia.service.api.ApplicationManager;
import com.hack23.cia.service.api.DataContainer;
import com.hack23.cia.service.api.DataSummary;

/**
 * The Class ApplicationManagerITest.
 */
@PerfTest(threads = 10, duration = 3000, warmUp = 1500)
@Required(max = 800, average = 25, percentile95 = 30, throughput = 2000)
public final class ApplicationManagerITest extends AbstractServiceFunctionalIntegrationTest {

	/** The i. */
	@Rule
	public ContiPerfRule i = new ContiPerfRule();

	/** The application manager. */
	@Autowired
	private ApplicationManager applicationManager;

	/**
	 * Gets the data container success test.
	 *
	 * @return the data container success test
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void getDataContainerSuccessTest() throws Exception {
		setAuthenticatedAnonymousUser();

		final DataContainer<ViewRiksdagenCommittee, Serializable> committeeDataContainer = applicationManager
				.getDataContainer(ViewRiksdagenCommittee.class);
		assertNotNull(EXPECT_A_RESULT, committeeDataContainer);

		final DataContainer<DataSummary, Serializable> dataSummarydataContainer = applicationManager
				.getDataContainer(DataSummary.class);
		assertNotNull(EXPECT_A_RESULT, dataSummarydataContainer);

		final DataContainer<PersonData, Serializable> personDataContainer = applicationManager
				.getDataContainer(PersonData.class);
		assertNotNull(EXPECT_A_RESULT, personDataContainer);
	}

}