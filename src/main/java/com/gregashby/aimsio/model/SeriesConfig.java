package com.gregashby.aimsio.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.gregashby.aimsio.database.SignalsData;

public class SeriesConfig {
	
	private List<String> assetUNList = null;
	private List<String> statusList = null;
	private Date maxDate = new Date();
		
	public List<String> getAssetUNList() {
		return assetUNList;
	}
	public void setAssetUNList(List<String> assetUNList) {
		this.assetUNList = assetUNList;
	}
	public List<String> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}
	public Date getMaxDate() {
		return maxDate;
	}
	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}
	
	public void initFromDatabase() {
		assetUNList = MySeries.makeBlankList();
		statusList = MySeries.makeBlankList();

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
		
		try {
			maxDate = SignalsData.getMaxDate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
