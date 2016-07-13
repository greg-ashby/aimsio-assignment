package com.gregashby.aimsio.components;

import java.util.Date;
import java.util.List;

import com.gregashby.aimsio.database.ISeriesFilter;
import com.gregashby.aimsio.model.SeriesManager;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.VerticalLayout;

public class SeriesListView extends CustomComponent implements ISeriesFilter {

	private static final long serialVersionUID = -2698526323751753670L;
	private HorizontalLayout layout = new HorizontalLayout();
	private Label name = new Label("");
	private SeriesEditView editView = new SeriesEditView();
	private PopupView editPopup = new PopupView("Edit", editView);

	public SeriesListView() {
		setCompositionRoot(layout);
		layout.setSpacing(true);
		layout.addComponent(name);
		layout.addComponent(editPopup);

		VerticalLayout deleteLayout = new VerticalLayout();
		Button deleteButton = new Button("Confirm");
		deleteLayout.addComponent(new Label("Are you sure?"));
		deleteLayout.addComponent(deleteButton);
		PopupView deletePopup = new PopupView("Delete", deleteLayout);
		layout.addComponent(deletePopup);

		deleteButton.addClickListener(event -> {
			SeriesManager seriesManager = MainUI.getMainUI().getSeriesManager();
			seriesManager.removeSeries(this.getName());
		});

		editPopup.setSizeUndefined();
		editPopup.addPopupVisibilityListener(event -> {
			if (event.isPopupVisible()) {
				editView.setStoredName(getName());
			} else {
				setName(editView.getStoredName());
			}
		});
	}

	public String getColour() {
		return editView.getColour();
	}
	
	public void setColour(String colour){
		editView.setColour(colour);
	}
	
	public void setName(String name) {
		this.name.setValue(name);
		editView.setName(name);
		editView.setStoredName(name);
	}

	public String getName() {
		return editView.getName();
	}

	public void showEdit() {
		editPopup.setPopupVisible(true);
	}

	public void hideEdit() {
		editPopup.setPopupVisible(false);
	}

	public void setAssetUnOptions(List<String> assetUNList) {
		editView.setAssetUnOptions(assetUNList);
	}

	public void setAssetUN(String assetUN) {
		editView.setAssetUN(assetUN);
	}

	public String getAssetUN() {
		return editView.getAssetUN();
	}

	public void setStatusOptions(List<String> statusList) {
		editView.setStatusOptions(statusList);
	}

	public void setStatus(String status) {
		editView.setStatus(status);
	}

	public String getStatus() {
		return editView.getStatus();
	}

	@Override
	public Date getFromDate() {
		return editView.getFromDate();
	}

	@Override
	public void setFromDate(Date fromDate) {
		editView.setFromDate(fromDate);
	}

	@Override
	public Date getToDate() {
		return editView.getToDate();
	}

	@Override
	public void setToDate(Date toDate) {
		editView.setToDate(toDate);
		
	}

	@Override
	public String getDateResolution() {
		return editView.getDateResolution();
	}

	@Override
	public void setDateResolution(String dateResolution) {
		editView.setDateResolution(dateResolution);
	}

	public void initDateFields(Date maxDate) {
		editView.initDateFields(maxDate);	
	}
}
