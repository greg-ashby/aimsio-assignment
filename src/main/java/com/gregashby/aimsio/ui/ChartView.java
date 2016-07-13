package com.gregashby.aimsio.ui;

import com.gregashby.aimsio.model.Series;
import com.gregashby.aimsio.model.SeriesManager;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.PlotOptionsLine;
import com.vaadin.server.Responsive;
import com.vaadin.ui.CustomComponent;

/**
 * Custom component for rendering a chart, including adjusting it's size, adding
 * new series, and re-drawing as needed (when any series changes).
 *
 */
public class ChartView extends CustomComponent {

	private static final long serialVersionUID = -2158811024900625603L;

	private Chart chart = null;

	public ChartView() {
		initChart();
		setStyleName("my-chart-view", true);
	}

	private void initChart() {
		chart = new Chart();
		Responsive.makeResponsive(chart);
		Configuration config = chart.getConfiguration();
		config.setTitle("Signal Counts by Date");
		config.getChart().setType(ChartType.LINE);
		config.getxAxis().setType(AxisType.DATETIME);
		config.getyAxis().setTitle("Signal Counts");
		setCompositionRoot(chart);
	}

	/**
	 * Redraws the whole chart using all the series in the Series Manager.
	 * 
	 * @param seriesManager
	 */
	public void update(SeriesManager seriesManager) {
		int width = (int) chart.getWidth();
		int height = (int) chart.getHeight();
		
		initChart();
		
		setDimensions(width, height);
		seriesManager.getSeries().forEach((seriesName, series) -> {
			this.addSeries(series);
		});
	}

	/**
	 * Adds a single Series to the chart and draws it. Note that if a series has
	 * been updated, you'll need to call the update() instead to clear all the
	 * series and then re-add them all.
	 * 
	 * TODO: add ability to remove a series so you can remove and redraw 1
	 * series without needing to redraw all of them. From what I can tell, the
	 * set of DataSeries on a chart is only mutable by providing a new series or
	 * resetting them all (which would require redrawing them all anyway). So it
	 * would probably be necessary to get a dataSeries, delete all datapoints
	 * from it, then readd data points from a Series. A "redraw" method might be
	 * a better option instead. In any case, the full update happens pretty
	 * quick since each series cache's it's data, so probably not worth the
	 * hassle for a sample application :)
	 * 
	 * @param series
	 */
	public void addSeries(Series series) {
		DataSeries signals = new DataSeries(series.getName());
		series.getDataCache().forEach(signalInfo -> {
			signals.add(new DataSeriesItem(signalInfo.getEntryDate(), signalInfo.getCount()));

		});
		PlotOptionsLine plotOptions = new PlotOptionsLine();
		plotOptions.setColor(Colours.getClass(series.getView().getColour()));
		signals.setPlotOptions(plotOptions);
		chart.getConfiguration().addSeries(signals);
	}

	/**
	 * Changes the charts size (which triggers a redraw)
	 * 
	 * @param width
	 * @param height
	 */
	public void setDimensions(int width, int height) {
		chart.setWidth("" + width + "px");
		chart.setHeight("" + height + "px");
	}
}
