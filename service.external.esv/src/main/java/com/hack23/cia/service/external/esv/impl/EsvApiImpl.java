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
package com.hack23.cia.service.external.esv.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hack23.cia.service.external.esv.api.EsvApi;
import com.hack23.cia.service.external.esv.api.GovernmentBodyAnnualSummary;

/**
 * The Class EsvApiImpl.
 */
@Component
public final class EsvApiImpl implements EsvApi {

	/** The government body name set. */
	private static final Set<String> governmentBodyNameSet = new HashSet<>();

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(EsvApiImpl.class);

	/** The ministry name set. */
	private static final Set<String> ministryNameSet = new HashSet<>();

	/**
	 * Instantiates a new val api impl.
	 */
	public EsvApiImpl() {
		super();
	}

	@Override
	public Map<Integer, List<GovernmentBodyAnnualSummary>> getData() {
		return getDataPerMinistry(null);
	}

	@Override
	public Map<Integer, List<GovernmentBodyAnnualSummary>> getDataPerMinistry(final String name) {
		final Map<Integer, List<GovernmentBodyAnnualSummary>> map = new TreeMap<>();
		try {
			final HSSFWorkbook myWorkBook = new HSSFWorkbook(
					EsvApiImpl.class.getResourceAsStream("/Myndighetsinformation.xls"));

			for (int sheetNr = 0; sheetNr < myWorkBook.getNumberOfSheets(); sheetNr++) {
				final HSSFSheet mySheet = myWorkBook.getSheetAt(sheetNr);

				if (mySheet.getSheetName().chars().allMatch(Character::isDigit)) {

					final int year = Integer.valueOf(mySheet.getSheetName());

					final List<GovernmentBodyAnnualSummary> yearList = new ArrayList<>();
					final Iterator<Row> rowIterator = mySheet.iterator();

					rowIterator.next();

					while (rowIterator.hasNext()) {
						final Row row = rowIterator.next();
						final short maxColIx = row.getLastCellNum();

						if (maxColIx == 10) {
							final GovernmentBodyAnnualSummary governmentBodyAnnualSummary = new GovernmentBodyAnnualSummary(
									year, row.getCell(0).toString(), row.getCell(1).toString(),
									row.getCell(2).toString(), row.getCell(3).toString(), row.getCell(4).toString(),
									row.getCell(5).toString(), row.getCell(6).toString(), row.getCell(7).toString(),
									row.getCell(8).toString(), row.getCell(9).toString());
							row.getCell(9).toString();

							if (name == null || name.equalsIgnoreCase(governmentBodyAnnualSummary.getMinistry())) {
								yearList.add(governmentBodyAnnualSummary);
							}

						}

					}
					map.put(year, yearList);
				}
			}

			myWorkBook.close();
		} catch (

		final IOException e) {
			LOGGER.warn("Problem loading", e);
		}

		return map;
	}

	@Override
	public List<GovernmentBodyAnnualSummary> getDataPerMinistryAndYear(final String name, final int year) {
		final Map<Integer, List<GovernmentBodyAnnualSummary>> map = getDataPerMinistry(name);

		if (map.containsKey(year)) {
			return map.get(year);
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public List<String> getGovernmentBodyNames() {
		if (governmentBodyNameSet.isEmpty()) {

			final Map<Integer, List<GovernmentBodyAnnualSummary>> data = getData();

			for (final List<GovernmentBodyAnnualSummary> list : data.values()) {
				for (final GovernmentBodyAnnualSummary governmentBodyAnnualSummary : list) {
					if (!governmentBodyNameSet.contains(governmentBodyAnnualSummary.getName())) {
						governmentBodyNameSet.add(governmentBodyAnnualSummary.getName());
					}
				}
			}
		}
		return new ArrayList<>(governmentBodyNameSet);
	}

	@Override
	public List<String> getMinistryNames() {
		if (ministryNameSet.isEmpty()) {
			final Map<Integer, List<GovernmentBodyAnnualSummary>> data = getData();

			for (final List<GovernmentBodyAnnualSummary> list : data.values()) {
				for (final GovernmentBodyAnnualSummary governmentBodyAnnualSummary : list) {
					if (!ministryNameSet.contains(governmentBodyAnnualSummary.getMinistry())) {
						ministryNameSet.add(governmentBodyAnnualSummary.getMinistry());
					}
				}
			}
		}
		return new ArrayList<>(ministryNameSet);
	}

}
