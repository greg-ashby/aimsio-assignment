package com.gregashby.aimsio.database;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gregashby.aimsio.database.ISeriesFilter;
import com.gregashby.aimsio.database.QueryBuilder;
import com.gregashby.aimsio.utils.DateHandler;

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
		String actual = builder.buildQuery(null);
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
			ISeriesFilter seriesFilter = new MockSeriesFilter();
			seriesFilter.setFromDate(DateHandler.convertDayString(fromDates[x]));
			seriesFilter.setToDate(DateHandler.convertDayString(toDates[x]));
			String actual = builder.buildQuery(seriesFilter);
			assertEquals("Failed on iteration: " + x, expecteds[x], actual);
		}
	}

	@Test
	public void testAssetNames() {
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

			String actual = builder.buildQuery(seriesFilter);
			assertEquals("Failed on iteration: " + x, expecteds[x], actual);
		}
	}

	@Test
	public void testStatuses() {
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

			String actual = builder.buildQuery(seriesFilter);
			assertEquals("Failed on iteration: " + x, expecteds[x], actual);
		}
	}

	@Test
	public void testResolution() {
		String[] resolutions = { null, "Year", "Month", "Day" };

		String[] expecteds = {
				"SELECT date_format(entry_date, '%Y-%m-%d') AS 'entry_date', " + "count(*) AS 'count' FROM SIGNALS "
						+ "GROUP BY date_format(entry_date, '%Y-%m-%d') ORDER BY entry_date;",
				"SELECT date_format(entry_date, '%Y') AS 'entry_date', " + "count(*) AS 'count' FROM SIGNALS "
						+ "GROUP BY date_format(entry_date, '%Y') ORDER BY entry_date;",
				"SELECT date_format(entry_date, '%Y-%m') AS 'entry_date', " + "count(*) AS 'count' FROM SIGNALS "
						+ "GROUP BY date_format(entry_date, '%Y-%m') ORDER BY entry_date;",
				"SELECT date_format(entry_date, '%Y-%m-%d') AS 'entry_date', " + "count(*) AS 'count' FROM SIGNALS "
						+ "GROUP BY date_format(entry_date, '%Y-%m-%d') ORDER BY entry_date;" };

		for (int x = 0; x < expecteds.length; x++) {
			ISeriesFilter seriesFilter = new MockSeriesFilter();
			seriesFilter.setDateResolution(resolutions[x]);

			String actual = builder.buildQuery(seriesFilter);
			assertEquals("Failed on iteration: " + x, expecteds[x], actual);
		}
	}

	@Test
	public void testFilterCombinations() throws ParseException {
		String[] fromDates = { "2014-01-01", "2014-01-01" };
		String[] toDates = { "2015-01-31", "2015-02-28" };
		String[] resolutions = { "Day", "Month" };
		String[] assetUNs = { "5007A", "5007A" };
		String[] statuses = { "Active", "Active" };

		String[] expecteds = {
				"SELECT date_format(entry_date, '%Y-%m-%d') AS 'entry_date', " + "count(*) AS 'count' FROM SIGNALS "
						+ "WHERE entry_date BETWEEN '2014-01-01' AND '2015-01-31 23:59:59.999999' "
						+ " AND AssetUN = '5007A'  AND status = 'Active' "
						+ "GROUP BY date_format(entry_date, '%Y-%m-%d') ORDER BY entry_date;",
				"SELECT date_format(entry_date, '%Y-%m') AS 'entry_date', " + "count(*) AS 'count' FROM SIGNALS "
						+ "WHERE entry_date BETWEEN '2014-01-01' AND '2015-02-28 23:59:59.999999' "
						+ " AND AssetUN = '5007A'  AND status = 'Active' "
						+ "GROUP BY date_format(entry_date, '%Y-%m') ORDER BY entry_date;" };

		for (int x = 0; x < expecteds.length; x++) {
			ISeriesFilter seriesFilter = new MockSeriesFilter();
			seriesFilter.setFromDate(DateHandler.convertDayString(fromDates[x]));
			seriesFilter.setToDate(DateHandler.convertDayString(toDates[x]));
			seriesFilter.setDateResolution(resolutions[x]);
			seriesFilter.setAssetUN(assetUNs[x]);
			seriesFilter.setStatus(statuses[x]);

			String actual = builder.buildQuery(seriesFilter);
			assertEquals("Failed on iteration: " + x, expecteds[x], actual);
		}
	}
}
