package com.gregashby.aimsio.components;

import java.util.ArrayList;
import java.util.List;

import com.gregashby.aimsio.model.MySeries;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.PlotOptionsLine;
import com.vaadin.addon.charts.model.Series;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.ui.CustomComponent;

public class ChartView extends CustomComponent {

	private static final long serialVersionUID = -2158811024900625603L;

	private Chart chart = new Chart();
	
	public ChartView(){
		Configuration config = chart.getConfiguration();
		config.setTitle("Signal Counts");
		config.getChart().setType(ChartType.LINE);
		config.getxAxis().setType(AxisType.DATETIME);
		setCompositionRoot(chart);
	}
	
	public void addSeries(MySeries series){
		DataSeries signals = new DataSeries(series.getName());
		series.getDataCache().forEach(signalInfo -> {
			signals.add(new DataSeriesItem(signalInfo.getEntryDate(), signalInfo.getCount()));

		});
		PlotOptionsLine plotOptions = new PlotOptionsLine();
		plotOptions.setColor(Colours.getClass(series.getColour()));
		signals.setPlotOptions(plotOptions);
		chart.getConfiguration().addSeries(signals);
	}
	
	public void removeSeries(String seriesName){
		List<Series> s = new ArrayList<Series>();
		// Use another list - the configuration sends an unmodifiable list, so
		// initialize with a copy of it
		for (Series i : chart.getConfiguration().getSeries())
			s.add(i);

		for (int i = 0; i < s.size(); i++) {
			DataSeries ds = (DataSeries) chart.getConfiguration().getSeries().get(i);

			if (ds.getName().equalsIgnoreCase(seriesName)) {
				s.remove(i);
				// Update the chart with the new list
				chart.getConfiguration().setSeries(s);
				chart.drawChart();
				break;
			}
		}
	}
}
