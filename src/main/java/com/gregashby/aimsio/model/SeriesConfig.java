package com.gregashby.aimsio.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.gregashby.aimsio.database.SignalsData;

/**
 * Reads data from the database and caches it to initialize each new Series.
 * This allows new Series to be created and added very quickly on the UI,
 * without needing to hit the database each time.
 * 
 * Call initFromDatabase() to initialize all the default field values.
 *
 */
public class SeriesConfig implements Serializable {

	private static final long serialVersionUID = 296343068340149341L;
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

	/**
	 * Initializes all the fields from the database. NOTE that it assumes the
	 * SignalData datasource connection has been initialized before calling.
	 */
	public void initFromDatabase() {
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

		try {
			maxDate = SignalsData.getMaxDate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
