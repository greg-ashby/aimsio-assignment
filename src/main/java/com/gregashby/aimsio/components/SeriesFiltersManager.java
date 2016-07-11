package com.gregashby.aimsio.components;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gregashby.aimsio.database.SignalsData;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class SeriesFiltersManager extends CustomComponent {

	private static final long serialVersionUID = -2541403903435412434L;

	private VerticalLayout layout = new VerticalLayout();
	private VerticalLayout seriesFiltersLayout = new VerticalLayout();
	private Map<String, SeriesFilters> seriesFilters = new LinkedHashMap<String, SeriesFilters>();

	private Button button = new Button("Add New Series");
	private Label maxSeries = new Label("Maximum number of series reached");

	private List<String> assetUNList = null;
	public List<String> getAssetUNList() {
		return assetUNList;
	}

	public List<String> getStatusList() {
		return statusList;
	}

	private List<String> statusList = null;

	private int seriesNameCounter = 0;


	public SeriesFiltersManager() {
		setCompositionRoot(layout);
		layout.addComponent(seriesFiltersLayout);
		layout.addComponent(button);
		layout.addComponent(maxSeries);
		

		initListValues();

		addSeriesParameters();

		button.addClickListener(event -> {
			addSeriesParameters();
			updateAddButtonVisibility();
		});
		
		updateAddButtonVisibility();
	}

	private void updateAddButtonVisibility() {
		if(seriesFilters.size() >= 10){
			button.setVisible(false);
			maxSeries.setVisible(true);
		} else {
			button.setVisible(true);
			maxSeries.setVisible(false);
		}
		
	}

	private void initListValues() {
		assetUNList = SeriesFilters.makeBlankList();
		statusList = SeriesFilters.makeBlankList();

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
		SeriesFilters parameters = new SeriesFilters(seriesName, this);
		seriesFiltersLayout.addComponent(parameters);
		seriesFilters.put(seriesName, parameters);
	}
	
	public void removeSeriesFilter(SeriesFilters filterToRemove){
		seriesFilters.remove(filterToRemove.getSeriesName());
		seriesFiltersLayout.removeComponent(filterToRemove);
		updateAddButtonVisibility();
	}

	public Map<String, SeriesFilters> getSeriesParameters() {
		return seriesFilters;
	}

	public void setSeriesFilters(Map<String, SeriesFilters> seriesFilters) {
		this.seriesFilters = seriesFilters;
	}

}
