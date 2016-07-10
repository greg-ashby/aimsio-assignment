package com.gregashby.aimsio;

import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gregashby.aimsio.components.ChartFilter;
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


	private ChartFilter chartFilter = null;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		CustomLayout layout = new CustomLayout("layout");
		setContent(layout);

		try {
			SignalsData.initConnection();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		chartFilter = new ChartFilter(); // TODO initialize this with the max
											// date in the signals db
		layout.addComponent(chartFilter, "chart-filter");


		Chart chart = new Chart();
		Configuration config = chart.getConfiguration();
		config.setTitle("Signal Counts");
		layout.addComponent(chart, "chart");

		config.getChart().setType(ChartType.LINE);
		config.getxAxis().setType(AxisType.DATETIME);

		DataSeries signals = new DataSeries("Signals");
		try {
			for (SignalInfo signalInfo : SignalsData.getSignalInfo(null, null)) {
				// Shoe size on the X-axis, age on the Y-axis
				signals.add(new DataSeriesItem(signalInfo.getEntryDate(), signalInfo.getCount()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		config.addSeries(signals);

	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}
