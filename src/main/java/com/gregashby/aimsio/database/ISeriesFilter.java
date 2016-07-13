package com.gregashby.aimsio.database;

import java.util.Date;

public interface ISeriesFilter {

	public String getAssetUN();
	public void setAssetUN(String assetUN);
	public String getStatus();
	public void setStatus(String string);
	public Date getFromDate();
	public void setFromDate(Date fromDate);
	public Date getToDate();
	public void setToDate(Date toDate);
	public String getDateResolution();
	public void setDateResolution(String dateResolution);

}
