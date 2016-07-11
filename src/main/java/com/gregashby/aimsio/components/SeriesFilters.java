package com.gregashby.aimsio.components;

import java.util.ArrayList;
import java.util.List;

import com.gregashby.aimsio.query.ISeriesFilters;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class SeriesFilters extends CustomComponent implements ISeriesFilters {

	private static final long serialVersionUID = 8193463726890344529L;
	private VerticalLayout layout = new VerticalLayout();
	private SeriesFiltersManager manager = null;
	private String seriesName = null;

	public SeriesFilters(String seriesName, SeriesFiltersManager manager) {
		this.seriesName = seriesName;
		this.manager = manager;
		
		setCompositionRoot(layout);

		List<String> assetUNs = manager.getAssetUNList();
		List<String> statuses = manager.getStatusList();
		
		HorizontalLayout titleLayout = new HorizontalLayout();
		titleLayout.addComponent(new Label(seriesName));
		Button deleteButton = new Button(FontAwesome.TRASH);
		titleLayout.addComponent(deleteButton);
		layout.addComponent(titleLayout);

		deleteButton.addClickListener(event -> {
			this.manager.removeSeriesFilter(this);
		});

		ComboBox assetUNsComboBox = new ComboBox("Asset UN");
		assetUNsComboBox.addItems(assetUNs);
		assetUNsComboBox.setValue(assetUNs.get(0));
		layout.addComponent(assetUNsComboBox);

		ComboBox statusesComboBox = new ComboBox("Status");
		statusesComboBox.addItems(statuses);
		statusesComboBox.setValue(statuses.get(0));
		layout.addComponent(statusesComboBox);

	}

	public static List<String> makeBlankList() {
		List<String> blankList = new ArrayList<String>();
		blankList.add("ALL");
		return blankList;
	}

	public String getSeriesName() {
		return seriesName;
	}
}
