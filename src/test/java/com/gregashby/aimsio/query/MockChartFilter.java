package com.gregashby.aimsio.query;

import java.util.Date;

public class MockChartFilter implements IChartFilter {

	private Date fromDate = null;
	private Date toDate = null;
	private String dateResolution = null;

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

	public String getDateResolution() {
		return dateResolution;
	}

	public void setDateResolution(String dateResolution) {
		this.dateResolution = dateResolution;
	}

}
