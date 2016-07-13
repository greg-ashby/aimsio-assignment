package com.gregashby.aimsio.ui;

import java.io.Serializable;

/**
 * Class that observes changes in browser size, calculates how a page layout
 * should be configured, and notifies the MainUI to redraw itself when needed.
 */
public class PageResizeHandler implements Serializable {

	private static final long serialVersionUID = 189606969584140866L;
	private final static int MIN_CHART_WIDTH = 600;
	private final static int MIN_CHART_HEIGHT = 400;
	private final static int MAX_CHART_WIDTH = MIN_CHART_WIDTH * 2;
	private final static int MAX_CHART_HEIGHT = MIN_CHART_HEIGHT * 2;

	private int redrawSizeChangeThreshold = 50;
	private int viewWidthPadding = 300 + redrawSizeChangeThreshold;
	private int viewHeightPadding = 300 + redrawSizeChangeThreshold;
	private int lastRedrawWidth = 0;
	private int lastRedrawHeight = 0;
	private IRedrawableUI ui = null;

	public PageResizeHandler(IRedrawableUI ui, int startWidth, int startHeight) {
		this.ui = ui;
		lastRedrawWidth = startWidth;
		lastRedrawHeight = startHeight;
	}

	/**
	 * size change threshold is used to determine how much a browser size has
	 * changed since the last redraw of the UI. This allows redraws to only
	 * occur when the browser window has changed significantly enough to warrant
	 * it, to avoid performance and usability issues constantly redrawing while
	 * a user is adjusting the window size.
	 * 
	 * @return
	 */
	public int getRedrawSizeChangeThreshold() {
		return redrawSizeChangeThreshold;
	}

	public void setRedrawSizeChangeThreshold(int pageRedrawSizeChangeThreshold) {
		this.redrawSizeChangeThreshold = pageRedrawSizeChangeThreshold;
	}

	/**
	 * Used to keep track of the browser size at last redraw.
	 * 
	 * @return
	 */
	public int getLastRedrawWidth() {
		return lastRedrawWidth;
	}

	public void setLastRedrawWidth(int lastRedrawWidth) {
		this.lastRedrawWidth = lastRedrawWidth;
	}

	public int getLastRedrawHeight() {
		return lastRedrawHeight;
	}

	public void setLastRedrawHeight(int lastRedrawHeight) {
		this.lastRedrawHeight = lastRedrawHeight;
	}

	/**
	 * method to be called each time the browser is resized. Will keep track of
	 * how much the browser has changed in size, and only call redraw on the UI
	 * if needed.
	 * 
	 * @param newWidth
	 * @param newHeight
	 */
	public void observeChange(int newWidth, int newHeight) {
		if (Math.abs(newWidth - lastRedrawWidth) >= redrawSizeChangeThreshold
				|| Math.abs(newHeight - lastRedrawHeight) >= redrawSizeChangeThreshold) {
			lastRedrawHeight = newHeight;
			lastRedrawWidth = newWidth;
			PageLayoutConfig layoutConfig = calculateNewConfig();
			ui.redraw(layoutConfig);
		}
	}

	/**
	 * Calculates how large a chart should be and where it should be placed
	 * based on the size of the browser.
	 * 
	 * Note that this and PageLayoutConfig are pretty specific to this current
	 * UI... I think the general pattern is useful but would need to be
	 * generalized before it could be re-used.
	 * 
	 * @return
	 */
	protected PageLayoutConfig calculateNewConfig() {
		PageLayoutConfig config = new PageLayoutConfig();

		config.setBeside(true);
		int chartWidth = lastRedrawWidth - viewWidthPadding;
		if (chartWidth < MIN_CHART_WIDTH) {
			chartWidth = MIN_CHART_WIDTH;
			config.setBeside(false);
		} else if (chartWidth > MAX_CHART_WIDTH) {
			chartWidth = MAX_CHART_WIDTH;
		}

		int chartHeight = lastRedrawHeight - viewHeightPadding;
		if (chartHeight < MIN_CHART_HEIGHT) {
			chartHeight = MIN_CHART_HEIGHT;
		} else if (chartHeight > MAX_CHART_HEIGHT) {
			chartHeight = MAX_CHART_HEIGHT;
		}

		config.setChartWidth(chartWidth);
		config.setChartHeight(chartHeight);

		return config;
	}

}
