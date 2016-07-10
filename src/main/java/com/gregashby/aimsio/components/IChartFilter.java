package com.gregashby.aimsio.components;

import java.util.Date;

public interface IChartFilter {

	public Date getFromDate();
	public void setFromDate(Date fromDate);
	public Date getToDate();
	public void setToDate(Date toDate);
	public String getDateResolution();
	public void setDateResolution(String dateResolution);
}
