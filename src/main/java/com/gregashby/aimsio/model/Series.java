package com.gregashby.aimsio.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.gregashby.aimsio.database.SignalInfo;
import com.gregashby.aimsio.database.SignalsData;
import com.gregashby.aimsio.ui.SeriesView;

/**
 * Class that represents a Series which will be rendered edited by a user and
 * rendered on the chart. Has ability to load and cache it's own data, as well
 * as a view for rendering itself on the UI to allow users to specify parameters
 * for what data to load.
 * 
 * @author gregashby
 *
 */
public class Series implements Serializable {

	private static final long serialVersionUID = 3565558513108558595L;
	private List<SignalInfo> dataCache = null;
	private SeriesView view = new SeriesView();

	/**
	 * Construct a new series for a given name (any string) and a config
	 * (initial data for things like possible assetUNs, etc)
	 * 
	 * @param seriesName
	 * @param config
	 */
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

	/**
	 * provides whatever data is currently cached. If not already cached, call
	 * reloadData() to initialize it
	 * 
	 * @return
	 */
	public List<SignalInfo> getDataCache() {
		return dataCache;
	}

	/**
	 * reloads data based on whatever values are currently entered in it's view
	 * object
	 */
	public void reloadData() {
		try {
			dataCache = SignalsData.getSignalInfo(getView());
		} catch (SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * helper for constructing a list of options (i.e. providing an "ALL" option
	 * that you can append additional options to).
	 * 
	 * @return
	 */
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

	/**
	 * Access to the view that represents this Series (the UI for editing the
	 * series, not what's rendered on the chart). All the information specific
	 * to this series (e.g. date range, assetUNs, etc) is stored in the View.
	 * 
	 * @return
	 */
	public SeriesView getView() {
		return view;
	}

}
