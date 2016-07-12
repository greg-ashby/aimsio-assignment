package com.gregashby.aimsio.query;

import java.util.Date;

public interface IChartFilter {

	public Date getFromDate();
//	public String getFromDateAsString();
	public void setFromDate(Date fromDate);
//	public void setFromDate(String fromDate);
	
	public Date getToDate();
//	public String getToDateAsString();
	public void setToDate(Date toDate);
//	public void setToDate(String toDate);
	
	public String getDateResolution();
	public void setDateResolution(String dateResolution);
	
}
