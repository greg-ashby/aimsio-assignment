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
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

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

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		HorizontalLayout subLayout = layout();

		initDatabaseConnection();

		seriesManager = new SeriesManager();
		subLayout.addComponent(seriesManager.getView());
		subLayout.addComponent(chartView);
		
		seriesManager.getSeries().forEach((seriesName, series) -> {
			series.reloadData();
			getChart().addSeries(series);
		});
	}

	private HorizontalLayout layout() {
		VerticalLayout topLayout = new VerticalLayout();
		setContent(topLayout);
		Label header = new Label("TODO: Put a real nice header here");
		header.setStyleName("header", true);
		topLayout.addComponent(header);
		HorizontalLayout subLayout = new HorizontalLayout();
		topLayout.addComponent(subLayout);
		Label footer = new Label("TODO: Put a real nice footer here");
		footer.setStyleName("footer", true);
		topLayout.addComponent(footer);
		return subLayout;
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

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MainUI.class, productionMode = true)
	@SuppressWarnings("serial")
	public static class MyUIServlet extends VaadinServlet {
	}

}
