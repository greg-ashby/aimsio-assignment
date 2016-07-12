package com.gregashby.aimsio.components;

import java.util.Calendar;
import java.util.Date;

import com.gregashby.aimsio.MainUI;
import com.gregashby.aimsio.query.IChartFilter;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ChartFilters extends CustomComponent implements IChartFilter {

	private static final long serialVersionUID = -2541403903435412434L;
	private static final String DATE_FIELD_DISPLAY_FORMAT = "yyyy-MM-dd";
	private DateField fromDate = new DateField();
	private DateField toDate = new DateField();
	private Button refreshButton = new Button("Refresh Chart");
	private OptionGroup dateResolutionGroup = new OptionGroup();

	public ChartFilters() {
		this(new Date());
	}

	public ChartFilters(Date toDate) {
		initDateFields(toDate);

		HorizontalLayout layout = new HorizontalLayout();
		VerticalLayout filtersLayout = new VerticalLayout();
		HorizontalLayout datesLayout = new HorizontalLayout();

		layout.setSpacing(true);
		layout.addComponent(filtersLayout);
		layout.addComponent(refreshButton);

		filtersLayout.addComponent(datesLayout);

		datesLayout.addComponent(new Label("From: "));
		datesLayout.addComponent(fromDate);
		datesLayout.addComponent(new Label("To: "));
		datesLayout.addComponent(this.toDate);

		dateResolutionGroup.addItems("Year", "Month", "Day");
		dateResolutionGroup.setValue("Day");

		filtersLayout.addComponent(dateResolutionGroup);

		setCompositionRoot(layout);

		refreshButton.addClickListener(e -> {
			getMainUI().getSeriesManager().loadAllDataSeries(this);
			getMainUI().updateChart();
		});
	}

	private void initDateFields(Date toDate) {
		this.toDate.setDateFormat(DATE_FIELD_DISPLAY_FORMAT);
		this.fromDate.setDateFormat(DATE_FIELD_DISPLAY_FORMAT);

		// Default fromDate is 3 months before toDate
		Calendar c = Calendar.getInstance();
		c.setTime(toDate);
		c.add(Calendar.MONTH, -3);
		Date fromDate = c.getTime();

		setFromDate(fromDate);
		setToDate(toDate);
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

	public String getDateResolution() {
		return dateResolutionGroup.getValue().toString();
	}

	public void setDateResolution(String dateResolution) {
		dateResolutionGroup.setValue(dateResolution);
	}

	public MainUI getMainUI() {
		return (MainUI) UI.getCurrent();
	}

}
