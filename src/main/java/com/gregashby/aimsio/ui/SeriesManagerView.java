package com.gregashby.aimsio.ui;

import com.gregashby.aimsio.model.Series;
import com.gregashby.aimsio.model.SeriesManager;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class SeriesManagerView extends CustomComponent {

	private static final long serialVersionUID = 7327705988530185755L;

	private VerticalLayout layout = new VerticalLayout();
	private VerticalLayout seriesLayout = new VerticalLayout();
	private Label header = new Label("Series Filters");
	private Button addSeriesbutton = new Button("Add New Series");
	private Label maxSeriesWarning = new Label("Maximum number of series reached");
	
	private int newSeriesCount = 0;
	
	public SeriesManagerView() {
		layout();
		
		addSeriesbutton.addClickListener(event -> {
			SeriesManager seriesManager = MainUI.getMainUI().getSeriesManager();
			newSeriesCount += 1;
			Series newSeries = seriesManager.createNewSeries("New Series ("+ newSeriesCount + ")");
			seriesManager.addSeries(newSeries);
			newSeries.getView().showEdit();
		});
	}

	private void layout() {
		setCompositionRoot(layout);
		layout.addComponent(header);
		layout.addComponent(seriesLayout);
		layout.addComponent(addSeriesbutton);
		layout.addComponent(maxSeriesWarning);
		
		layout.setStyleName("series-filter-section", true);
		header.setStyleName("series-filter-header", true);
	}
	
	public void updateAddButtonVisibility(boolean maxReached) {
		if(maxReached){
			addSeriesbutton.setVisible(false);
			maxSeriesWarning.setVisible(true);
		} else {
			addSeriesbutton.setVisible(true);
			maxSeriesWarning.setVisible(false);
		}
		
	}

	public void addSeries(Series seriesToAdd) {
		seriesLayout.addComponent(seriesToAdd.getView());
	}
	
	public void removeSeries(Series seriesToRemove){
		seriesLayout.removeComponent(seriesToRemove.getView());
	}
	

	
}
