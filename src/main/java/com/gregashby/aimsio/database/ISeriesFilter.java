package com.gregashby.aimsio.database;

import java.util.Date;

/**
 * An interface that represents the filters one can provide to the QueryBuilder.
 * This allows the input data to be handled by the UI, which can then be passed
 * directly to the QueryBuilder without it needing to be aware of the UI later
 * at all (or having to load data into other beans for transfering between pure
 * UI and Database layers). Also makes it easy to create mock filter objects for
 * unit testings.
 * 
 * @author gregashby
 *
 */
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
