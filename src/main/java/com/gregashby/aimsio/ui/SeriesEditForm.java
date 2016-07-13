package com.gregashby.aimsio.ui;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.gregashby.aimsio.model.Series;
import com.gregashby.aimsio.model.SeriesManager;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;

public class SeriesEditForm extends CustomComponent {

	private static final long serialVersionUID = 7587816212756872418L;
	private static final String DATE_FIELD_DISPLAY_FORMAT = "yyyy-MM-dd";

	private FormLayout layout = new FormLayout();
	private TextField name = new TextField("Series Name");
	private ComboBox colours = new ComboBox("Series Colour");
	private ComboBox assetUNs = new ComboBox("Asset UN");
	private ComboBox statuses = new ComboBox("Status");
	private DateField fromDate = new DateField("From");
	private DateField toDate = new DateField("To");
	private OptionGroup dateResolutionGroup = new OptionGroup("Date Resolution");

	private Button updateButton = new Button("Update Series");

	private String storedName = null;

	public SeriesEditForm() {
		layout();

		updateButton.addClickListener(event -> {
			SeriesManager seriesManager = MainUI.getMainUI().getSeriesManager();
			Series updatedSeries = seriesManager.renameSeries(getStoredName(), getName());
			updatedSeries.reloadData();
			updatedSeries.getView().hideEdit();
			MainUI.getMainUI().getChart().update(seriesManager);
		});

		// TODO add hour, minute... should be easy to do by changing DateHandler
		dateResolutionGroup.addItems("Year", "Month", "Day");
		dateResolutionGroup.setValue("Day");

		setColourOptions(Colours.getColours());
		setColour("");
	}

	private void layout() {
		setCompositionRoot(layout);
		layout.setSizeUndefined();
		layout.addComponent(name);
		layout.addComponent(colours);
		layout.addComponent(assetUNs);
		layout.addComponent(statuses);
		layout.addComponent(fromDate);
		layout.addComponent(toDate);
		layout.addComponent(dateResolutionGroup);
		layout.addComponent(updateButton);
	}

	public void initDateFields(Date toDate) {
		this.fromDate.setDateFormat(DATE_FIELD_DISPLAY_FORMAT);
		this.toDate.setDateFormat(DATE_FIELD_DISPLAY_FORMAT);
		// Default fromDate is 3 months before toDate
		Calendar c = Calendar.getInstance();
		c.setTime(toDate);
		c.add(Calendar.MONTH, -1);
		Date fromDate = c.getTime();

		setFromDate(fromDate);
		setToDate(toDate);
	}

	public void setName(String name) {
		this.name.setValue(name);
	}

	public String getName() {
		return this.name.getValue();
	}

	public Date getFromDate() {
		return fromDate.getValue();
	}

	public void setFromDate(Date fromDate) {
		this.fromDate.setValue(fromDate);
	}

	public Date getToDate() {
		return toDate.getValue();
	}

	public void setToDate(Date toDate) {
		this.toDate.setValue(toDate);
	}

	public void setColourOptions(List<String> colours) {
		this.colours.removeAllItems();
		this.colours.addItems(colours);
	}

	public void setColour(String colour) {
		colours.setValue(colour);
	}

	public String getColour() {
		return colours.getValue().toString();
	}

	public void setAssetUnOptions(List<String> assetUNs) {
		this.assetUNs.removeAllItems();
		this.assetUNs.addItems(assetUNs);
	}

	public void setAssetUN(String assetUN) {
		assetUNs.setValue(assetUN);
	}

	public String getAssetUN() {
		return assetUNs.getValue().toString();
	}

	public void setStatusOptions(List<String> statuses) {
		this.statuses.removeAllItems();
		this.statuses.addItems(statuses);
	}

	public void setStatus(String status) {
		statuses.setValue(status);
	}

	public String getStatus() {
		return statuses.getValue().toString();
	}

	public String getStoredName() {
		return storedName;
	}

	public void setStoredName(String storedName) {
		this.storedName = storedName;
	}

	public String getDateResolution() {
		return dateResolutionGroup.getValue().toString();
	}

	public void setDateResolution(String dateResolution) {
		dateResolutionGroup.setValue(dateResolution);
	}

}
