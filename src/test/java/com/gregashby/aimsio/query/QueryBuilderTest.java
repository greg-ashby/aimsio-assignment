package com.gregashby.aimsio.query;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class QueryBuilderTest {

	private QueryBuilder builder = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		builder = new QueryBuilder();
	}

	@After
	public void tearDown() throws Exception {
		builder = null;
	}

	@Test
	public void testNoFilters() {
		String expected = "SELECT date_format(entry_date, '%Y-%m-%d') AS 'entry_date', "
				+ "count(*) AS 'count' FROM SIGNALS "
				+ "GROUP BY date_format(entry_date, '%Y-%m-%d') ORDER BY entry_date;";
		String actual = builder.buildQuery(null, null);
		assertEquals(expected, actual);
	}

	@Test
	public void testDateRanges() throws ParseException {
		String[] fromDates = { "2015-01-01", "2015-01-01", null };
		String[] toDates = { "2015-01-31", null, "2015-01-31" };

		String[] expecteds = {
				"SELECT date_format(entry_date, '%Y-%m-%d') AS 'entry_date', " + "count(*) AS 'count' FROM SIGNALS "
						+ "WHERE entry_date BETWEEN '2015-01-01' AND '2015-01-31 23:59:59.999999' "
						+ "GROUP BY date_format(entry_date, '%Y-%m-%d') ORDER BY entry_date;",
				"SELECT date_format(entry_date, '%Y-%m-%d') AS 'entry_date', " + "count(*) AS 'count' FROM SIGNALS "
						+ "WHERE entry_date >= '2015-01-01' "
						+ "GROUP BY date_format(entry_date, '%Y-%m-%d') ORDER BY entry_date;",
				"SELECT date_format(entry_date, '%Y-%m-%d') AS 'entry_date', " + "count(*) AS 'count' FROM SIGNALS "
						+ "WHERE entry_date <= '2015-01-31 23:59:59.999999' "
						+ "GROUP BY date_format(entry_date, '%Y-%m-%d') ORDER BY entry_date;" };

		for (int x = 0; x < expecteds.length; x++) {
			IChartFilter chartFilter = new MockChartFilter();
			if (fromDates[x] != null) {
				chartFilter.setFromDate(new SimpleDateFormat(QueryBuilder.WHERE_DATE_FORMAT).parse(fromDates[x]));
			}
			if (toDates[x] != null) {
				chartFilter.setToDate(new SimpleDateFormat(QueryBuilder.WHERE_DATE_FORMAT).parse(toDates[x]));
			}
			String actual = builder.buildQuery(chartFilter, null);
			assertEquals("Failed on iteration: " + x, expecteds[x], actual);
		}
	}
	
	@Test
	public void testAssetNames(){
		String[] assetNames = { null, "3112", "ALL" };

		String[] expecteds = {
				"SELECT date_format(entry_date, '%Y-%m-%d') AS 'entry_date', " + "count(*) AS 'count' FROM SIGNALS "
						+ "GROUP BY date_format(entry_date, '%Y-%m-%d') ORDER BY entry_date;",
				"SELECT date_format(entry_date, '%Y-%m-%d') AS 'entry_date', " + "count(*) AS 'count' FROM SIGNALS "
						+ "WHERE AssetUN = '3112' "
						+ "GROUP BY date_format(entry_date, '%Y-%m-%d') ORDER BY entry_date;",
				"SELECT date_format(entry_date, '%Y-%m-%d') AS 'entry_date', " + "count(*) AS 'count' FROM SIGNALS "
						+ "GROUP BY date_format(entry_date, '%Y-%m-%d') ORDER BY entry_date;" };

		for (int x = 0; x < expecteds.length; x++) {
			ISeriesFilter seriesFilter = new MockSeriesFilter();
			seriesFilter.setAssetUN(assetNames[x]);

			String actual = builder.buildQuery(null, seriesFilter);
			assertEquals("Failed on iteration: " + x, expecteds[x], actual);
		}		
	}
	
	@Test
	public void testStatuses(){
		String[] statuses = { null, "Engaged", "ALL" };

		String[] expecteds = {
				"SELECT date_format(entry_date, '%Y-%m-%d') AS 'entry_date', " + "count(*) AS 'count' FROM SIGNALS "
						+ "GROUP BY date_format(entry_date, '%Y-%m-%d') ORDER BY entry_date;",
				"SELECT date_format(entry_date, '%Y-%m-%d') AS 'entry_date', " + "count(*) AS 'count' FROM SIGNALS "
						+ "WHERE status = 'Engaged' "
						+ "GROUP BY date_format(entry_date, '%Y-%m-%d') ORDER BY entry_date;",
				"SELECT date_format(entry_date, '%Y-%m-%d') AS 'entry_date', " + "count(*) AS 'count' FROM SIGNALS "
						+ "GROUP BY date_format(entry_date, '%Y-%m-%d') ORDER BY entry_date;" };

		for (int x = 0; x < expecteds.length; x++) {
			ISeriesFilter seriesFilter = new MockSeriesFilter();
			seriesFilter.setStatus(statuses[x]);

			String actual = builder.buildQuery(null, seriesFilter);
			assertEquals("Failed on iteration: " + x, expecteds[x], actual);
		}		
	}
}
