package com.gregashby.aimsio.ui;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PageSizeRehandlerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRedrawThreshhold() {
		MockUI ui = new MockUI();
		PageResizeHandler handler = new PageResizeHandler(ui, 1000, 1000);
		handler.setRedrawSizeChangeThreshold(5);

		int[] widths = { handler.getLastRedrawWidth(), 500, 502, 502, 504, 506, 505, 505 };
		int[] heights = { handler.getLastRedrawHeight(), 500, 500, 502, 504, 504, 505, 520 };
		boolean[] expecteds = { false, true, false, false, false, true, false, true };

		for (int x = 0; x < widths.length; x++) {
			assertFalse(ui.isRedrawn());
			handler.observeChange(widths[x], heights[x]);
			assertEquals("Failed on iteration: " + x, expecteds[x], ui.isRedrawn());
			if (ui.isRedrawn()) {
				ui.setRedrawn(false);
			}
		}

	}

}
