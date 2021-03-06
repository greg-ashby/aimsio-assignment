package com.gregashby.aimsio.ui;

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

/**
 * Custom component to render each item in the "Series Filter" section of the
 * UI. Each Series has a "list view" that is shown with "edit" and "delete"
 * buttons in the main UI, and if "edit" is clicked, a popup view that shows a
 * larger "editForm".
 *
 */
public class SeriesView extends CustomComponent implements ISeriesFilter {

	private static final long serialVersionUID = -2698526323751753670L;
	private HorizontalLayout layout = new HorizontalLayout();
	private Label name = new Label("");
	private SeriesEditForm editForm = new SeriesEditForm();
	private PopupView editPopup = new PopupView("Edit", editForm);

	public SeriesView() {
		setCompositionRoot(layout);
		layout.setSpacing(true);
		layout.addComponent(name);
		layout.addComponent(editPopup);
		layout.addComponent(createDeleteWidget());

		name.setStyleName("series-filter-label", true);
		editPopup.setSizeUndefined();

		/*
		 * This is to keep track of what the original series name was before a
		 * user started editting it. If they exit the pop up without clicking
		 * "Update", the change will be discarded. If they do click "Update" a
		 * rename operation will be preformed, which will also update the fields
		 * correctly with the name it's now stored as.
		 */
		editPopup.addPopupVisibilityListener(event -> {
			// if the popup just became visible, grab the original name
			if (event.isPopupVisible()) {
				editForm.setStoredName(getName());
			} else { // popup is now hiding, reset the name so we don't lose
						// track of what it's currently stored as
				setName(editForm.getStoredName());
			}
		});

	}

	/**
	 * little confirmation dialog if someone clicks 'delete'
	 * 
	 * @return
	 */
	private PopupView createDeleteWidget() {
		VerticalLayout deleteLayout = new VerticalLayout();
		Button deleteButton = new Button("Confirm");
		deleteLayout.addComponent(new Label("Are you sure?"));
		deleteLayout.addComponent(deleteButton);
		PopupView deletePopup = new PopupView("Delete", deleteLayout);

		deleteButton.addClickListener(event -> {
			SeriesManager seriesManager = MainUI.getMainUI().getSeriesManager();
			seriesManager.removeSeries(this.getName());
		});
		return deletePopup;
	}

	public String getColour() {
		return editForm.getColour();
	}

	public void setColour(String colour) {
		editForm.setColour(colour);
	}

	/**
	 * Trims series names to 21 characters with ... to keep size manageable in the series filter section
	 * 
	 * @param name
	 */
	public void setName(String name) {
		if (name.length() > 24) {
			this.name.setValue(name.substring(0, 21) + "...");
		} else {
			this.name.setValue(name);
		}
		editForm.setName(name);
		editForm.setStoredName(name);
	}

	public String getName() {
		return editForm.getName();
	}

	public void showEdit() {
		editPopup.setPopupVisible(true);
	}

	public void hideEdit() {
		editPopup.setPopupVisible(false);
	}

	public void setAssetUnOptions(List<String> assetUNList) {
		editForm.setAssetUnOptions(assetUNList);
	}

	public void setAssetUN(String assetUN) {
		editForm.setAssetUN(assetUN);
	}

	public String getAssetUN() {
		return editForm.getAssetUN();
	}

	public void setStatusOptions(List<String> statusList) {
		editForm.setStatusOptions(statusList);
	}

	public void setStatus(String status) {
		editForm.setStatus(status);
	}

	public String getStatus() {
		return editForm.getStatus();
	}

	@Override
	public Date getFromDate() {
		return editForm.getFromDate();
	}

	@Override
	public void setFromDate(Date fromDate) {
		editForm.setFromDate(fromDate);
	}

	@Override
	public Date getToDate() {
		return editForm.getToDate();
	}

	@Override
	public void setToDate(Date toDate) {
		editForm.setToDate(toDate);
	}

	@Override
	public String getDateResolution() {
		return editForm.getDateResolution();
	}

	@Override
	public void setDateResolution(String dateResolution) {
		editForm.setDateResolution(dateResolution);
	}

	public void initDateFields(Date maxDate) {
		editForm.initDateFields(maxDate);
	}
}
