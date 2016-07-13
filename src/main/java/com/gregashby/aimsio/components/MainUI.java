package com.gregashby.aimsio.components;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gregashby.aimsio.database.SignalsData;
import com.gregashby.aimsio.model.SeriesManager;
import com.gregashby.aimsio.model.MySeries;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.Series;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.UI;

/**
 *
 */
@Theme("mytheme")
@Widgetset("org.test.MyAppWidgetset")
public class MainUI extends UI {

	private static final long serialVersionUID = -3079757697754396044L;

	public static Logger logger = LoggerFactory.getLogger("default");
	private SeriesManager seriesManager = null;
	private ChartView chartView = new ChartView();
	
	private CustomLayout layout = new CustomLayout("layout");

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		setContent(layout);

		layout.addComponent(chartView, "chart");

		initDatabaseConnection();

		seriesManager = new SeriesManager();
		layout.addComponent(seriesManager.getView(), "series-filter");
		seriesManager.getSeries().forEach((seriesName, series) -> {
			series.reloadData();
			getChart().addSeries(series);
		});
	}

	private void initDatabaseConnection() {
		try {
			SignalsData.initConnection();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ChartView getChart() {
		return chartView;
	}
	
	public SeriesManager getSeriesManager() {
		return seriesManager;
	}

	public static MainUI getMainUI() {
		return (MainUI) UI.getCurrent();
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MainUI.class, productionMode = true)
	public static class MyUIServlet extends VaadinServlet {
	}

}
