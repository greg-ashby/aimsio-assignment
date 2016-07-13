package com.gregashby.aimsio.database;

import java.util.Date;

import com.gregashby.aimsio.database.ISeriesFilter;

public class MockSeriesFilter implements ISeriesFilter {
	
	private String assetUN = null;
	private String status = null;
	private String dateResolution = null;
	private Date fromDate = null;
	private Date toDate = null;
			
			
	public String getDateResolution() {
		return dateResolution;
	}
	public void setDateResolution(String dateResolution) {
		this.dateResolution = dateResolution;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getAssetUN() {
		return assetUN;
	}
	public void setAssetUN(String assetUN) {
		this.assetUN = assetUN;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

}
