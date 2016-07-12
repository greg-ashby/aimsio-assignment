package com.gregashby.aimsio.components;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gregashby.aimsio.MainUI;
import com.gregashby.aimsio.database.SignalInfo;
import com.gregashby.aimsio.database.SignalsData;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class SeriesManager extends CustomComponent {

	private static final int MAX_SERIES = 10;

	private static final long serialVersionUID = -2541403903435412434L;

	private VerticalLayout layout = new VerticalLayout();
	private VerticalLayout seriesFiltersLayout = new VerticalLayout();
	private Map<String, Series> serieses = new LinkedHashMap<String, Series>();

	private Button button = new Button("Add New Series");
	private Label maxSeriesWarning = new Label("Maximum number of series reached");

	private List<String> assetUNList = null;
	public List<String> getAssetUNList() {
		return assetUNList;
	}

	public List<String> getStatusList() {
		return statusList;
	}

	private List<String> statusList = null;

	private int seriesNameCounter = 0;


	public SeriesManager() {
		setCompositionRoot(layout);
		layout.addComponent(seriesFiltersLayout);
		layout.addComponent(button);
		layout.addComponent(maxSeriesWarning);
		

		initListValues();

		addSeriesParameters();

		button.addClickListener(event -> {
			addSeriesParameters();
			updateAddButtonVisibility();
		});
		
		updateAddButtonVisibility();
	}

	private void updateAddButtonVisibility() {
		if(serieses.size() >= MAX_SERIES){
			button.setVisible(false);
			maxSeriesWarning.setVisible(true);
		} else {
			button.setVisible(true);
			maxSeriesWarning.setVisible(false);
		}
		
	}

	private void initListValues() {
		assetUNList = Series.makeBlankList();
		statusList = Series.makeBlankList();

		try {
			assetUNList.addAll(SignalsData.getAssetUNs());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			statusList.addAll(SignalsData.getStatuses());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addSeriesParameters() {
		seriesNameCounter += 1;
		String seriesName = "Series " + seriesNameCounter;
		Series parameters = new Series(seriesName, this);
		seriesFiltersLayout.addComponent(parameters);
		serieses.put(seriesName, parameters);
	}
	
	public void removeSeries(Series filterToRemove){
		serieses.remove(filterToRemove.getSeriesName());
		seriesFiltersLayout.removeComponent(filterToRemove);
		updateAddButtonVisibility();
		getMainUI().updateChart();
	}

	public Map<String, Series> getSeries() {
		return serieses;
	}

	public void loadAllDataSeries(ChartFilters chartFilter) {
		serieses.forEach((seriesName, series) -> {
			List<SignalInfo> data = null;
			try {
				data = SignalsData.getSignalInfo(chartFilter, series);
			} catch (Exception e) {
				e.printStackTrace();
			}
			series.setDataCache(data);
		});
		
	}
	
	public MainUI getMainUI(){
		return (MainUI) UI.getCurrent();
	}

	public void loadOneDataSeries(Series series) throws SQLException, ParseException {
		List<SignalInfo> data = SignalsData.getSignalInfo(getMainUI().getChartFilter(), series);
		series.setDataCache(data);
		getMainUI().updateChart();
	}


}
