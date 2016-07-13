package com.gregashby.aimsio.ui;

import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gregashby.aimsio.database.SignalsData;
import com.gregashby.aimsio.model.SeriesManager;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Main Application Class, provides the User Interface, Servlet, logger, and
 * references to objects that need to be communicated with throughout the
 * application (primarily seriesManager and chartView).
 */
@Theme("mytheme")
@Widgetset("org.test.MyAppWidgetset")
public class MainUI extends UI implements IRedrawableUI {

	private static final long serialVersionUID = -3079757697754396044L;

	/**
	 * Use this for any logging.
	 * TODO: add a better logger than the simple default.
	 */
	public static Logger logger = LoggerFactory.getLogger("default");
	private SeriesManager seriesManager = null;
	private ChartView chartView = new ChartView();
	private PageResizeHandler resizeHandler = null;
	private VerticalLayout responsiveSection = null;
	private Layout widgetLayout = null;

	/**
	 * Initialize the application and user interface
	 */
	@Override
	protected void init(VaadinRequest vaadinRequest) {

		initDatabaseConnection();
		seriesManager = new SeriesManager();

		initResizeHandler();

		layout();
		redraw(resizeHandler.calculateNewConfig());

		seriesManager.getSeries().forEach((seriesName, series) -> {
			series.reloadData();
			getChart().addSeries(series);
		});
	}
	private void initResizeHandler() {
		resizeHandler = new PageResizeHandler(this, getPage().getBrowserWindowWidth(),
				getPage().getBrowserWindowHeight());
		Page.getCurrent().addBrowserWindowResizeListener(event -> {
			resizeHandler.observeChange(getPage().getBrowserWindowWidth(), getPage().getBrowserWindowHeight());
		});
	}

	private void layout() {
		VerticalLayout topLayout = new VerticalLayout();
		setContent(topLayout);

		Label header = new Label("TODO: Put a real nice header here");
		header.setStyleName("header", true);
		topLayout.addComponent(header);

		responsiveSection = new VerticalLayout();
		widgetLayout = new VerticalLayout();
		responsiveSection.addComponent(widgetLayout);
		topLayout.addComponent(responsiveSection);

		Label footer = new Label("TODO: Put a real nice footer here");
		footer.setStyleName("footer", true);
		topLayout.addComponent(footer);
	}

	private void initDatabaseConnection() {
		try {
			SignalsData.initConnection();
		} catch (NamingException e) {
			// TODO Display error message that database cannot be reached
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

	/**
	 * Determines how to layout the series filter and chart based on a provided pageConfig
	 */
	@Override
	public void redraw(PageLayoutConfig config) {
		getChart().setDimensions(config.getChartWidth(), config.getChartHeight());

		responsiveSection.removeAllComponents();
		if (config.isBeside()) {
			widgetLayout = new HorizontalLayout();
			widgetLayout.setSizeUndefined();
		} else {
			widgetLayout = new VerticalLayout();
			widgetLayout.setSizeUndefined();
		}
		responsiveSection.addComponent(widgetLayout);
		widgetLayout.addComponent(seriesManager.getView());
		widgetLayout.addComponent(chartView);
	}
	
	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MainUI.class, productionMode = true)
	@SuppressWarnings("serial")
	public static class MyUIServlet extends VaadinServlet {
	}

}
