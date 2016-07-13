package com.gregashby.aimsio.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.gregashby.aimsio.database.SignalInfo;
import com.gregashby.aimsio.database.SignalsData;
import com.gregashby.aimsio.ui.SeriesView;

public class Series implements Serializable {

	private static final long serialVersionUID = 3565558513108558595L;
	private List<SignalInfo> dataCache = null;
	private SeriesView view = new SeriesView();
	
	public Series(String seriesName, SeriesConfig config) {
		setName(seriesName);
		view.setAssetUnOptions(config.getAssetUNList());
		view.setAssetUN("ALL");
		view.setStatusOptions(config.getStatusList());
		view.setStatus("ALL");
		view.setColour("Blue");
		view.initDateFields(config.getMaxDate());
		view.setDateResolution("Day");
	}
	
	public List<SignalInfo> getDataCache() {
		return dataCache;
	}
	
	public void reloadData() {
		try {
			dataCache = SignalsData.getSignalInfo(this.view);
		} catch (SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public static List<String> makeBlankList() {
		List<String> blankList = new ArrayList<String>();
		blankList.add("ALL");
		return blankList;
	}

	public void setName(String seriesName) {
		view.setName(seriesName);
	}

	public String getName() {
		return view.getName();
	}

	public SeriesView getView() {
		return view;
	}
	
}
