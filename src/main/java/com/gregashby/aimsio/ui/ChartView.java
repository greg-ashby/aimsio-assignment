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

public class ChartView extends CustomComponent {

	private static final long serialVersionUID = -2158811024900625603L;

	private Chart chart = null;
	
	public ChartView(){
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
	
	public void update(SeriesManager seriesManager) {
		initChart();
		seriesManager.getSeries().forEach((seriesName, series) -> {
			this.addSeries(series);
		});
	}
	
	public void addSeries(Series series){
		DataSeries signals = new DataSeries(series.getName());
		series.getDataCache().forEach(signalInfo -> {
			signals.add(new DataSeriesItem(signalInfo.getEntryDate(), signalInfo.getCount()));

		});
		PlotOptionsLine plotOptions = new PlotOptionsLine();
		plotOptions.setColor(Colours.getClass(series.getView().getColour()));
		signals.setPlotOptions(plotOptions);
		chart.getConfiguration().addSeries(signals);
	}

	public void setDimensions(int width, int height){
		chart.setWidth(""+ width + "px");
		chart.setHeight("" + height +"px");
	}
}
