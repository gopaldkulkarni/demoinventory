package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringUI
public class VaadinUI extends UI {
	private static final Logger log = LoggerFactory.getLogger(VaadinUI.class);

	VerticalLayout mContent;

	Grid<Customer> customerGrid;
	Grid<Trip> recentTripGrid;
	Grid<Sales> salesGrid;

	ComboBox<TripFilter> tripFilterCombo;
	TextField customerFilterText;
	Button tripBtn;
	Button customerBtn;
	Button analyticsBtn;
	Button vehicleBtn;

	HorizontalLayout menu;

	private List<Component> tripComponents;
	private List<Component> customerComponents;
	private List<Component> salesComponents;
	ComboBox<String> custTypeDropDown;
	HorizontalLayout salesHeader;
	HorizontalLayout vehiclesHeader;
	Label totalBill;

	Grid<Vehicle> vehicleGrid;
	private List<Component> vehicleComponents;

	private ComboBox<VehicleTypes> vehicleTypeCombo;

	private Button refreshCacheBtn;

	@Override
	protected void init(VaadinRequest request) {

		mContent = new VerticalLayout();
		mContent.setSpacing(true);

		menu = new HorizontalLayout();

		setContent(mContent);
		HorizontalLayout titleBar = new HorizontalLayout();
		titleBar.setWidth("100%");
		mContent.addComponent(titleBar);

		Label title = new Label("Car Rental Inventory System");
		title.setStyleName(ValoTheme.LABEL_H1);
		title.setSizeUndefined();
		titleBar.addComponent(title);
		titleBar.setExpandRatio(title, 1.0f); // Expand

		mContent.addComponent(menu);

		tripBtn = new Button("Trips");
		tripBtn.addClickListener(e -> showRecentTrips());
		tripBtn.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		tripBtn.setIcon(VaadinIcons.PAPERPLANE);
		tripBtn.setSizeUndefined();
		menu.addComponent(tripBtn);

		customerBtn = new Button("Customers");
		customerBtn.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		customerBtn.setIcon(VaadinIcons.USER);
		customerBtn.addClickListener(e -> showCustomers());
		menu.addComponent(customerBtn);

		analyticsBtn = new Button("Analytics");
		analyticsBtn.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		analyticsBtn.setIcon(VaadinIcons.CHART);
		menu.addComponent(analyticsBtn);
		analyticsBtn.addClickListener(e -> showSales());

		vehicleBtn = new Button("My Vehicles");
		vehicleBtn.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		vehicleBtn.setIcon(VaadinIcons.CAR);
		menu.addComponent(vehicleBtn);
		vehicleBtn.addClickListener(e -> showVehicles());

		custTypeDropDown = new ComboBox<>("Filter by Customer Type");
		custTypeDropDown.setItems(new String[] { "INDIVIDUAL", "CORPORATE" });
		custTypeDropDown.addValueChangeListener(e -> updateSales());

		vehicleTypeCombo = new ComboBox<>("Filter by vehicle");
		vehicleTypeCombo.setItems(VehicleTypes.indica, VehicleTypes.innova, VehicleTypes.bmw, VehicleTypes.mercedece);
		vehicleTypeCombo.addValueChangeListener(e -> updateSales());
		vehiclesHeader = new HorizontalLayout();
		refreshCacheBtn = new Button("RefreshCache");
		refreshCacheBtn.setIcon(VaadinIcons.REFRESH);
		refreshCacheBtn.addClickListener(e -> updateVehicles());

		salesGrid = new Grid<>(Sales.class);
		salesGrid.setColumns("id", "billedAmount", "customerType", "vehicleType");

		// Initialize Grids

		customerGrid = new Grid<>(Customer.class);
		customerGrid.setVisible(false);

		recentTripGrid = new Grid<>(Trip.class);
		recentTripGrid.setColumns("id", "tripStart", "tripEnd", "distanceCovered", "customerId", "vehicleId",
				"tripStatus");

		tripFilterCombo = new ComboBox<>();
		tripFilterCombo.setPlaceholder("Select Filter");
		tripFilterCombo.setItems(TripFilter.all, TripFilter.next7Days);
		tripFilterCombo.setCaption("Showing the next 7 days trip as default...");
		tripFilterCombo.addValueChangeListener(e -> showTrips());

		customerFilterText = new TextField();
		customerFilterText.setCaption("Showing all registered customer. You can filter by Customer Type");
		customerFilterText.setPlaceholder("filter by CustomerType");
		customerFilterText.addValueChangeListener(e -> showCustomers());
		customerFilterText.setValueChangeMode(ValueChangeMode.LAZY);
		salesHeader = new HorizontalLayout();
		totalBill = new Label("");
		totalBill.setIcon(VaadinIcons.MONEY);
		totalBill.setCaption("Total Sales");

		vehicleGrid = new Grid<>(Vehicle.class);
		vehicleLabel = new Label("Showing all your vehicles!");
		vehicleLabel.setStyleName(ValoTheme.LABEL_BOLD);
		analyticsLabel = new Label(
				"Showing billing information. The billed amount 0.0 indicates, these trips are in progress");
		analyticsLabel.setStyleName(ValoTheme.LABEL_BOLD);

		// By Default
		tripBtn.click();
		tripBtn.focus();

	}

	private Label vehicleLabel;
	private Label analyticsLabel;

	private void updateVehicles() {
		this.vehicleGrid.setItems(InvetoryService.getVehicles(true));
	}

	private void updateSales() {
		String vehicleType = null;
		if (this.vehicleTypeCombo.getValue() == VehicleTypes.indica) {
			vehicleType = "indica";
		} else if (this.vehicleTypeCombo.getValue() == VehicleTypes.innova) {
			vehicleType = "innova";
		} else if (this.vehicleTypeCombo.getValue() == VehicleTypes.bmw) {
			vehicleType = "bmw";
		} else if (this.vehicleTypeCombo.getValue() == VehicleTypes.mercedece) {
			vehicleType = "mercedece";
		}
		String customerType = custTypeDropDown.getValue();
		salesGrid.setItems(InvetoryService.getSalesItems(customerType, vehicleType));
		updateBilledAmount(customerType, vehicleType);
	}

	private ClickedComponent previousclicked;

	enum ClickedComponent {
		sales, trips, customers, vehicles
	}

	private void showVehicles() {
		removePreviousComponents();
		previousclicked = ClickedComponent.vehicles;
		vehiclesHeader.addComponent(vehicleLabel);
		vehiclesHeader.addComponent(refreshCacheBtn);
		vehiclesHeader.setComponentAlignment(vehicleLabel, Alignment.BOTTOM_RIGHT);
		vehiclesHeader.setComponentAlignment(refreshCacheBtn, Alignment.BOTTOM_RIGHT);
		mContent.addComponent(vehiclesHeader);
		mContent.addComponent(vehicleGrid);
		mContent.setExpandRatio(vehicleGrid, 1);
		vehicleGrid.setSizeFull();

		vehicleComponents = new ArrayList<>();
		vehicleComponents.add(vehiclesHeader);
		vehicleComponents.add(vehicleGrid);
		vehicleGrid.setItems(InvetoryService.getVehicles(false));
	}

	private void showSales() {
		removePreviousComponents();
		previousclicked = ClickedComponent.sales;
		mContent.addComponent(analyticsLabel);
		salesHeader.addComponent(custTypeDropDown);
		salesHeader.addComponent(vehicleTypeCombo);
		salesHeader.addComponent(totalBill);
		totalBill.setStyleName(ValoTheme.LABEL_HUGE);
		salesHeader.setComponentAlignment(totalBill, Alignment.MIDDLE_RIGHT);
		mContent.addComponent(salesHeader);
		mContent.addComponent(salesGrid);
		salesGrid.setItems(InvetoryService.getSalesItems(null, null));
		salesComponents = new ArrayList<>();
		salesComponents.add(custTypeDropDown);
		salesComponents.add(salesGrid);
		salesComponents.add(salesHeader);
		salesComponents.add(analyticsLabel);

		mContent.setExpandRatio(salesGrid, 1);
		salesGrid.setSizeFull();
		updateBilledAmount(null, null);
	}

	// TODO move to the analytics and make it plugable
	private void updateBilledAmount(String customerType, String vehicleType) {
		List<Sales> salesItems = InvetoryService.getSalesItems(customerType, vehicleType);
		double total = 0.0;
		for (Sales s : salesItems) {
			total += s.getBilledAmount().doubleValue();
		}
		this.totalBill.setValue("Rs   " + total);
	}

	private void removePreviousComponents() {
		if (previousclicked == ClickedComponent.trips) {
			if (tripComponents != null) {
				for (Component c : tripComponents) {
					this.mContent.removeComponent(c);
				}
			}
		} else if (previousclicked == ClickedComponent.customers) {
			if (customerComponents != null) {
				for (Component c : customerComponents) {
					this.mContent.removeComponent(c);
				}
			}
		} else if (previousclicked == ClickedComponent.sales) {
			if (salesComponents != null) {
				for (Component c : salesComponents) {
					this.mContent.removeComponent(c);
				}
			}
		} else {
			if (vehicleComponents != null) {
				for (Component v : vehicleComponents) {
					this.mContent.removeComponent(v);
				}
			}
		}

	}

	private void showTrips() {
		if (this.tripFilterCombo.getValue() == TripFilter.next7Days) {
			showRecentTrips();
		} else {
			removePreviousComponents();
			previousclicked = ClickedComponent.trips;
			mContent.addComponent(tripFilterCombo);
			mContent.addComponent(recentTripGrid);
			recentTripGrid.setSizeFull();
			mContent.setExpandRatio(recentTripGrid, 1);
			recentTripGrid.setItems(InvetoryService.getAllTrips());
			tripComponents = new ArrayList<>();
			tripComponents.add(recentTripGrid);
			tripComponents.add(tripFilterCombo);
		}
	}

	private void showRecentTrips() {
		removePreviousComponents();
		previousclicked = ClickedComponent.trips;
		mContent.addComponent(tripFilterCombo);
		mContent.addComponent(recentTripGrid);
		recentTripGrid.setSizeFull();
		mContent.setExpandRatio(recentTripGrid, 1);
		recentTripGrid.setItems(InvetoryService.getRecentTrips(7));
		tripComponents = new ArrayList<>();
		tripComponents.add(recentTripGrid);
		tripComponents.add(tripFilterCombo);

	}

	private void showCustomers() {
		removePreviousComponents();
		previousclicked = ClickedComponent.customers;
		mContent.addComponent(customerFilterText);
		mContent.addComponent(customerGrid);
		this.customerGrid.setVisible(true);
		this.customerGrid.setEnabled(true);
		this.customerGrid.setItems(InvetoryService.getCustomers(customerFilterText.getValue()));
		mContent.setExpandRatio(customerGrid, 1);
		customerGrid.setSizeFull();
		customerComponents = new ArrayList<>();
		customerComponents.add(customerGrid);
		customerComponents.add(customerFilterText);
	}

}
