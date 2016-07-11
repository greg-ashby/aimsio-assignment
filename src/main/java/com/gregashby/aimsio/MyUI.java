package com.gregashby.aimsio;

import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gregashby.aimsio.components.ChartFilters;
import com.gregashby.aimsio.components.SeriesFiltersManager;
import com.gregashby.aimsio.components.SeriesFilters;
import com.gregashby.aimsio.database.SignalInfo;
import com.gregashby.aimsio.database.SignalsData;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.UI;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
@Widgetset("org.test.MyAppWidgetset")
public class MyUI extends UI {

	private static final long serialVersionUID = -3079757697754396044L;
	public static Logger logger = LoggerFactory.getLogger("default");

	private CustomLayout layout = null;
	private ChartFilters chartFilter = null;
	private SeriesFiltersManager seriesFiltersManager = null;

	private Map<String, List<SignalInfo>> seriesDataCache = new LinkedHashMap<String, List<SignalInfo>>();

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		initDatabaseConnection();
		initLayout();
		initChartFilter();
		initSeriesFilters();

		loadAllData();
		updateChart();

	}

	public void loadAllData() {
		seriesFiltersManager.getSeriesParameters().forEach((seriesName, seriesParameters) -> {
			List<SignalInfo> data = null;
			try {
				data = SignalsData.getSignalInfo(chartFilter, seriesParameters);
			} catch (Exception e) {
				e.printStackTrace();
			}
			seriesDataCache.put(seriesName, data);
		});

	}

	private void initLayout() {
		layout = new CustomLayout("layout");
		setContent(layout);
	}

	private void initChartFilter() {
		Date maxDate = new Date();
		try {
			maxDate = SignalsData.getMaxDate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		chartFilter = new ChartFilters(maxDate);
		layout.addComponent(chartFilter, "chart-filter");
		chartFilter.setMyUI(this);
	}

	private void initSeriesFilters() {

		seriesFiltersManager = new SeriesFiltersManager();
		layout.addComponent(seriesFiltersManager, "series-filter");

	}

	private void initDatabaseConnection() {
		try {
			SignalsData.initConnection();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateChart() {
		Chart chart = new Chart();
		Configuration config = chart.getConfiguration();
		config.setTitle("Signal Counts");
		layout.addComponent(chart, "chart");

		config.getChart().setType(ChartType.LINE);
		config.getxAxis().setType(AxisType.DATETIME);

		seriesDataCache.forEach((seriesName, seriesData) -> {
			DataSeries signals = new DataSeries(seriesName);
			seriesData.forEach(signalInfo -> {
				signals.add(new DataSeriesItem(signalInfo.getEntryDate(), signalInfo.getCount()));

			});
			config.addSeries(signals);
		});

	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}
