package org.test;

import javax.servlet.annotation.WebServlet;

import org.test.ChartsData.ShoeSizeInfo;

import com.vaadin.addon.charts.Chart;
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
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Widgetset("org.test.MyAppWidgetset")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	CustomLayout layout = new CustomLayout("layout");
    	setContent(layout);
    	
    	
    	Chart chart = new Chart();
		Configuration conf = chart.getConfiguration();
		conf.setTitle("Hello Charts!");
		layout.addComponent(chart, "chart");
    	
		conf.getChart().setType(ChartType.LINE);
		ChartsData data = new ChartsData();
		DataSeries girls = new DataSeries("Girls");
		for (ShoeSizeInfo shoeSizeInfo : data.getGirlsData()) {
			// Shoe size on the X-axis, age on the Y-axis
			girls.add(new DataSeriesItem(shoeSizeInfo.getSize(), shoeSizeInfo.getAgeMonths() / 12.0f));
		}
		conf.addSeries(girls);

		DataSeries boys = new DataSeries("Boys");
		for (ShoeSizeInfo shoeSizeInfo : data.getBoysData()) {
			// Shoe size on the X-axis, age on the Y-axis
			boys.add(new DataSeriesItem(shoeSizeInfo.getSize(), shoeSizeInfo.getAgeMonths() / 12.0f));
		}
		conf.addSeries(boys);
  	
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
