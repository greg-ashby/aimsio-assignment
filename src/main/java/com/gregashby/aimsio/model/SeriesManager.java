package com.gregashby.aimsio.model;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gregashby.aimsio.components.MainUI;
import com.gregashby.aimsio.components.SeriesManagerView;
import com.gregashby.aimsio.database.SignalInfo;
import com.gregashby.aimsio.database.SignalsData;

public class SeriesManager {

	private static final int MAX_SERIES = 10;
	private Map<String, MySeries> serieses = new LinkedHashMap<String, MySeries>();
	private SeriesConfig seriesConfig = new SeriesConfig();


	private SeriesManagerView view = new SeriesManagerView();

	public SeriesManager() {
		
		seriesConfig.initFromDatabase();
		addSeries(createNewSeries("All assets and signals"));

	} 

	public MySeries createNewSeries(String name){
		MySeries newSeries = new MySeries(name, seriesConfig);
		return newSeries;
	}

	public void addSeries(MySeries seriesToAdd) {
		serieses.put(seriesToAdd.getName(), seriesToAdd);
		view.addSeries(seriesToAdd);
		updateViewButtons();
	}
	
	public void removeSeries(String seriesName){
		MySeries seriesToRemove = serieses.get(seriesName);
		serieses.remove(seriesName);
		view.removeSeries(seriesToRemove);
		updateViewButtons();
		MainUI.getMainUI().getChart().removeSeries(seriesToRemove.getName());;
	}

	private void updateViewButtons(){
		view.updateAddButtonVisibility(MAX_SERIES == serieses.size());
	}
	public Map<String, MySeries> getSeries() {
		return serieses;
	}

	public void loadAllDataSeries() {
		serieses.forEach((seriesName, series) -> {
			List<SignalInfo> data = null;
			try {
				data = SignalsData.getSignalInfo(series);
			} catch (Exception e) {
				e.printStackTrace();
			}
			series.setDataCache(data);
		});
		
	}
	


	public void loadOneDataSeries(MySeries series) throws SQLException, ParseException {
		//List<SignalInfo> data = SignalsData.getSignalInfo(getMainUI().getChartFilter(), series);
		//series.setDataCache(data);
		//getMainUI().updateChart();
	}

	public SeriesManagerView getView() {
		return view;
	}

	public MySeries renameSeries(String oldName, String newName) {
		MySeries series = serieses.get(oldName);
		removeSeries(oldName);
		series.setName(newName);
		addSeries(series);
		return series;
	}


}
