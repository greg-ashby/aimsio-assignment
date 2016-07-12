package com.gregashby.aimsio.components;

import java.util.ArrayList;
import java.util.List;

import com.gregashby.aimsio.database.SignalInfo;
import com.gregashby.aimsio.query.ISeriesFilter;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class Series extends CustomComponent implements ISeriesFilter {

	private static final long serialVersionUID = 8193463726890344529L;
	private VerticalLayout layout = new VerticalLayout();
	private SeriesManager manager = null;
	private String seriesName = null;
	private List<SignalInfo> dataCache = null;
	private ComboBox assetUNsComboBox = new ComboBox("Asset UN");
	private ComboBox statusesComboBox = new ComboBox("Status");

	public void setDataCache(List<SignalInfo> dataCache) {
		this.dataCache = dataCache;
	}

	public Series(String seriesName, SeriesManager manager) {
		this.seriesName = seriesName;
		this.manager = manager;
		
		setCompositionRoot(layout);

		List<String> assetUNs = manager.getAssetUNList();
		List<String> statuses = manager.getStatusList();
		
		HorizontalLayout titleLayout = new HorizontalLayout();
		titleLayout.addComponent(new Label(seriesName));
		Button refreshButton = new Button(FontAwesome.REFRESH);
		titleLayout.addComponent(refreshButton);
		Button deleteButton = new Button(FontAwesome.TRASH);
		titleLayout.addComponent(deleteButton);
		layout.addComponent(titleLayout);

		refreshButton.addClickListener(event -> {
			try {
				this.manager.loadOneDataSeries(this);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		deleteButton.addClickListener(event -> {
			this.manager.removeSeries(this);
		});
		
		assetUNsComboBox.addItems(assetUNs);
		assetUNsComboBox.setValue(assetUNs.get(0));
		layout.addComponent(assetUNsComboBox);

		statusesComboBox.addItems(statuses);
		statusesComboBox.setValue(statuses.get(0));
		layout.addComponent(statusesComboBox);

	}

	public static List<String> makeBlankList() {
		List<String> blankList = new ArrayList<String>();
		blankList.add("ALL");
		return blankList;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public List<SignalInfo> getDataCache() {
		return dataCache;
	}

	public void setAssetUN(String assetUN) {
		assetUNsComboBox.setValue(assetUN);
	}
	
	public String getAssetUN() {
		return assetUNsComboBox.getValue().toString();
	}

	@Override
	public String getStatus() {
		return statusesComboBox.getValue().toString();
	}

	@Override
	public void setStatus(String status) {
		statusesComboBox.setValue(status);
	}
	


}
