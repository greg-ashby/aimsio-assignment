package com.gregashby.aimsio.components;

import java.util.Calendar;
import java.util.Date;

import com.gregashby.aimsio.MainUI;
import com.gregashby.aimsio.query.IChartFilter;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

public class ChartFilters extends CustomComponent implements IChartFilter {

	private static final long serialVersionUID = -2541403903435412434L;
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private DateField fromDate = new DateField();
	private DateField toDate = new DateField();
	private Button submitButton = new Button("Refresh Chart");
	private String dateResolution = null;
	
	public ChartFilters() {
		this(new Date());
	}

	public ChartFilters(Date toDate) {
		initDateFields(toDate);

		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);
		layout.addComponent(new Label("From: "));
		layout.addComponent(fromDate);
		layout.addComponent(new Label("To: "));
		layout.addComponent(this.toDate);
		layout.addComponent(submitButton);
		setCompositionRoot(layout);

		submitButton.addClickListener(e -> {
			getMainUI().getSeriesManager().loadAllDataSeries(this);
			getMainUI().updateChart();
		});
	}

	private void initDateFields(Date toDate) {
		this.toDate.setDateFormat(DATE_FORMAT);
		this.fromDate.setDateFormat(DATE_FORMAT);

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
		return dateResolution;
	}

	public void setDateResolution(String dateResolution) {
		this.dateResolution = dateResolution;
	}

	public MainUI getMainUI() {
		return (MainUI) UI.getCurrent();
	}

}
