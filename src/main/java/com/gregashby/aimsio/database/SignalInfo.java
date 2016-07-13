package com.gregashby.aimsio.database;

import java.io.Serializable;
import java.util.Date;

/**
 * Simple bean to represent each point on a chart - i.e. the date (which may be
 * grouped by the year, month, day) and the count of signals that occured at
 * that time
 * 
 * @author gregashby
 *
 */
public class SignalInfo implements Serializable {

	private static final long serialVersionUID = -4240937325048132497L;
	private Date entryDate = null;
	private int count = 0;

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
