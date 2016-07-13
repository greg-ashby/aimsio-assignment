package com.gregashby.aimsio.components;

import com.gregashby.aimsio.model.MySeries;
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
		setCompositionRoot(layout);
		layout.addComponent(header);
		layout.addComponent(seriesLayout);
		layout.addComponent(addSeriesbutton);
		layout.addComponent(maxSeriesWarning);
		
		addSeriesbutton.addClickListener(event -> {
			SeriesManager seriesManager = MainUI.getMainUI().getSeriesManager();
			newSeriesCount += 1;
			MySeries newSeries = seriesManager.createNewSeries("New Series ("+ newSeriesCount + ")");
			seriesManager.addSeries(newSeries);
			newSeries.getListView().showEdit();
		});
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

	public void addSeries(MySeries seriesToAdd) {
		seriesLayout.addComponent(seriesToAdd.getListView());
	}
	
	public void removeSeries(MySeries seriesToRemove){
		seriesLayout.removeComponent(seriesToRemove.getListView());
	}
	

	
}
