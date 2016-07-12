package com.gregashby.aimsio.utils;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DateHandlerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConvertStringToDate() throws ParseException {
		final String FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSSSS";
		SimpleDateFormat sdf = new SimpleDateFormat(FULL_DATE_FORMAT);
		String[] resolutions = { "Day", "Month" };
		String[] dateStrings = { "2015-04-01", "2015-07" };
		Date[] expecteds = { sdf.parse("2015-04-01 00:00:00.000000"), sdf.parse("2015-07-01 00:00:00.000000") };

		for (int x = 0; x < dateStrings.length; x++) {
			Date actual = DateHandler.convertStringToDate(resolutions[x], dateStrings[x]);
			assertEquals("Failed on iteration: " + x, expecteds[x], actual);
		}
	}
}
