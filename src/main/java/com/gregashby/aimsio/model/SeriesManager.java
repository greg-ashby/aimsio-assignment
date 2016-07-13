package com.gregashby.aimsio.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.gregashby.aimsio.ui.MainUI;
import com.gregashby.aimsio.ui.SeriesManagerView;

/**
 * Class for managing all the series that are currently included in the chart.
 * Creates new series (and manages their initial settings), tracks the
 * addition/deletion of each series, and provides a view to render UI components
 * for editing each series.
 *
 */
public class SeriesManager implements Serializable {

	private static final long serialVersionUID = 6784811501504997239L;
	private static final int MAX_SERIES = 10;
	private Map<String, Series> serieses = new LinkedHashMap<String, Series>();
	private SeriesConfig seriesConfig = new SeriesConfig();

	private SeriesManagerView view = new SeriesManagerView();

	/**
	 * Initializes default series configurations, and adds a default series
	 */
	public SeriesManager() {

		seriesConfig.initFromDatabase();
		addSeries(createNewSeries("All assets and signals"));

	}

	/**
	 * Factory method to create a default series. Note that the series data is
	 * not loaded from the database, and the series is not yet added to the
	 * chart (need to call addSeries() when ready).
	 * 
	 * @param name
	 * @return
	 */
	public Series createNewSeries(String name) {
		Series newSeries = new Series(name, seriesConfig);
		return newSeries;
	}

	/**
	 * Adds a series to the chart and updates the views accordingly.
	 * 
	 * @param seriesToAdd
	 */
	public void addSeries(Series seriesToAdd) {
		serieses.put(seriesToAdd.getName(), seriesToAdd);
		view.addSeries(seriesToAdd);
		updateViewButtons();
	}

	/**
	 * Removes a series from the chart and updates the views accordingly.
	 * 
	 * @param seriesName
	 */
	public void removeSeries(String seriesName) {
		Series seriesToRemove = serieses.get(seriesName);
		serieses.remove(seriesName);
		view.removeSeries(seriesToRemove);
		updateViewButtons();
		MainUI.getMainUI().getChart().update(this);
		;
	}

	/**
	 * Hides the "Add New Series" button if the maximum allowed has been
	 * reached. The maximum setting is somewhat arbitrary, can be adjusted by
	 * change MAX_SERIES, but should be set to a number that would not make the
	 * chart impossible to interpret.
	 */
	private void updateViewButtons() {
		view.updateAddButtonVisibility(MAX_SERIES == serieses.size());
	}

	/**
	 * Accessor for the current series.
	 * 
	 * @return
	 */
	public Map<String, Series> getSeries() {
		return serieses;
	}

	/**
	 * Accessor for the view (i.e. the Series Filter section on the UI)
	 * 
	 * @return
	 */
	public SeriesManagerView getView() {
		return view;
	}

	/**
	 * Allows a series to be renamed. Each series is stored by name, so renaming
	 * requires knowing both the old and new name in order to update the series
	 * and the manager correctly.
	 * 
	 * @param oldName
	 * @param newName
	 * @return
	 */
	public Series renameSeries(String oldName, String newName) {
		Series series = serieses.get(oldName);
		removeSeries(oldName);
		series.setName(newName);
		addSeries(series);
		return series;
	}

}
