package com.gregashby.aimsio.model;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gregashby.aimsio.components.SeriesListView;
import com.gregashby.aimsio.database.ISeriesFilter;
import com.gregashby.aimsio.database.SignalInfo;
import com.gregashby.aimsio.database.SignalsData;
import com.vaadin.ui.CustomComponent;

public class MySeries extends CustomComponent implements ISeriesFilter {

	private static final long serialVersionUID = 8193463726890344529L;
	
	private List<SignalInfo> dataCache = null;
	private SeriesListView listView = new SeriesListView();
	
	public MySeries(String seriesName, SeriesConfig config) {
		setName(seriesName);
		setAssetUnOptions(config.getAssetUNList());
		setAssetUN("ALL");
		setStatusOption(config.getStatusList());
		setStatus("ALL");
		setColour("Blue");
		
		initDateFields(config.getMaxDate());
	}
	
	private void initDateFields(Date maxDate) {
		listView.initDateFields(maxDate);
	}

	public void setStatusOption(List<String> statusList) {
		listView.setStatusOptions(statusList);
	}

	public void setAssetUnOptions(List<String> assetUNList) {
		listView.setAssetUnOptions(assetUNList);
	}

	public void setDataCache(List<SignalInfo> dataCache) {
		this.dataCache = dataCache;
	}

	public void setName(String seriesName) {
		listView.setName(seriesName);
	}

	public String getName() {
		return listView.getName();
	}
	
	public static List<String> makeBlankList() {
		List<String> blankList = new ArrayList<String>();
		blankList.add("ALL");
		return blankList;
	}

	public List<SignalInfo> getDataCache() {
		return dataCache;
	}

	public SeriesListView getListView() {
		return listView;
	}

	@Override
	public String getAssetUN() {
		return listView.getAssetUN();
	}

	@Override
	public void setAssetUN(String assetUN) {
		listView.setAssetUN(assetUN);
	}

	@Override
	public String getStatus() {
		return listView.getStatus();
	}

	@Override
	public void setStatus(String status) {
		listView.setStatus(status);
	}

	public void reloadData() {
		try {
			dataCache = SignalsData.getSignalInfo(this);
		} catch (SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Date getFromDate() {
		return listView.getFromDate();
	}

	@Override
	public void setFromDate(Date fromDate) {
		listView.setFromDate(fromDate);
	}

	@Override
	public Date getToDate() {
		return listView.getToDate();
	}

	@Override
	public void setToDate(Date toDate) {
		listView.setToDate(toDate);
	}

	@Override
	public String getDateResolution() {
		return listView.getDateResolution();
	}

	@Override
	public void setDateResolution(String dateResolution) {
		listView.setDateResolution(dateResolution);
	}

	public String getColour() {
		return listView.getColour();
	}
	
	public void setColour(String colour){
		listView.setColour(colour);
	}
	
}
