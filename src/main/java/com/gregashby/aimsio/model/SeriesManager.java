package com.gregashby.aimsio.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.gregashby.aimsio.ui.MainUI;
import com.gregashby.aimsio.ui.SeriesManagerView;

public class SeriesManager implements Serializable{

	private static final long serialVersionUID = 6784811501504997239L;
	private static final int MAX_SERIES = 10;
	private Map<String, Series> serieses = new LinkedHashMap<String, Series>();
	private SeriesConfig seriesConfig = new SeriesConfig();

	private SeriesManagerView view = new SeriesManagerView();

	public SeriesManager() {
		
		seriesConfig.initFromDatabase();
		addSeries(createNewSeries("All assets and signals"));

	} 

	public Series createNewSeries(String name){
		Series newSeries = new Series(name, seriesConfig);
		return newSeries;
	}

	public void addSeries(Series seriesToAdd) {
		serieses.put(seriesToAdd.getName(), seriesToAdd);
		view.addSeries(seriesToAdd);
		updateViewButtons();
	}
	
	public void removeSeries(String seriesName){
		Series seriesToRemove = serieses.get(seriesName);
		serieses.remove(seriesName);
		view.removeSeries(seriesToRemove);
		updateViewButtons();
		MainUI.getMainUI().getChart().update(this);;
	}

	private void updateViewButtons(){
		view.updateAddButtonVisibility(MAX_SERIES == serieses.size());
	}
	
	public Map<String, Series> getSeries() {
		return serieses;
	}

	public SeriesManagerView getView() {
		return view;
	}

	public Series renameSeries(String oldName, String newName) {
		Series series = serieses.get(oldName);
		removeSeries(oldName);
		series.setName(newName);
		addSeries(series);
		return series;
	}


}
