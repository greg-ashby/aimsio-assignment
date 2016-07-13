package com.gregashby.aimsio.ui;

/**
 * Simple interface to specify a redraw method so other classes can tell a UI to
 * "redraw" itself based on a PageLayoutConfi
 *
 */
public interface IRedrawableUI {

	public void redraw(PageLayoutConfig config);
}
