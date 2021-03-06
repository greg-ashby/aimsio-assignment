package com.gregashby.aimsio.ui;

/**
 * Simple bean specifying chart size and whether it should be rendered beside or below the series filter.
 * 
 */
public class PageLayoutConfig {
	
	private boolean beside = true;
	private int chartWidth = 600;
	private int chartHeight = 400;
	
	public boolean isBeside() {
		return beside;
	}
	public void setBeside(boolean beside) {
		this.beside = beside;
	}
	public int getChartWidth() {
		return chartWidth;
	}
	public void setChartWidth(int chartWidth) {
		this.chartWidth = chartWidth;
	}
	public int getChartHeight() {
		return chartHeight;
	}
	public void setChartHeight(int chartHeight) {
		this.chartHeight = chartHeight;
	}

}
