package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	TextField filterText;
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
	Label totalBill;

	Grid<Vehicle> vehicleGrid;
	private List<Component> vehicleComponents;

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
		tripBtn.setSizeUndefined();
		menu.addComponent(tripBtn);

		customerBtn = new Button("Customers");
		customerBtn.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		customerBtn.addClickListener(e -> showCustomers());
		menu.addComponent(customerBtn);

		analyticsBtn = new Button("Analytics");
		analyticsBtn.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		menu.addComponent(analyticsBtn);
		analyticsBtn.addClickListener(e -> showSales());

		vehicleBtn = new Button("My Vehicles");
		vehicleBtn.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		menu.addComponent(vehicleBtn);
		vehicleBtn.addClickListener(e -> showVehicles());

		custTypeDropDown = new ComboBox<>("Filter by Customer Type");
		custTypeDropDown.setItems(new String[] { "INDIVIDUAL", "CORPORATE" });
		custTypeDropDown.addValueChangeListener(e -> handleCustomerType());
		salesGrid = new Grid<>(Sales.class);

		// Initialize Grids

		customerGrid = new Grid<>(Customer.class);
		customerGrid.setVisible(false);

		recentTripGrid = new Grid<>(Trip.class);
		filterText = new TextField();
		filterText.setPlaceholder("filter by days");
		filterText.setCaption("Showing the last 7 days trip as default. Enter any intger to expand");
		filterText.addValueChangeListener(e -> showRecentTrips());
		filterText.setValueChangeMode(ValueChangeMode.LAZY);

		customerFilterText = new TextField();
		customerFilterText.setCaption("Showing all registered customer. You can filter by Customer Type");
		customerFilterText.setPlaceholder("filter by CustomerType");
		customerFilterText.addValueChangeListener(e -> showCustomers());
		customerFilterText.setValueChangeMode(ValueChangeMode.LAZY);
		salesHeader = new HorizontalLayout();
		totalBill = new Label("Total Sales :");

		vehicleGrid = new Grid<>(Vehicle.class);

		// By Default
		tripBtn.click();
		tripBtn.focus();

	}

	private void handleCustomerType() {
		String value = custTypeDropDown.getValue();
		if (value == null || value.trim().isEmpty()) {
			salesGrid.setItems(InvetoryService.getSalesItems(null, null));
		} else {
			salesGrid.setItems(InvetoryService.getSalesItems(value, null));
		}
		updateSales(value, null);
	}

	private ClickedComponent previousclicked;

	enum ClickedComponent {
		sales, trips, customers, vehicles
	}

	private void showVehicles() {
		removePreviousComponents();
		previousclicked = ClickedComponent.vehicles;
		mContent.addComponent(vehicleGrid);
		mContent.setExpandRatio(vehicleGrid, 1);
		vehicleGrid.setSizeFull();

		vehicleComponents = new ArrayList<>();
		vehicleComponents.add(vehicleGrid);
		vehicleGrid.setItems(InvetoryService.getVehicles());
	}

	private void showSales() {
		removePreviousComponents();
		previousclicked = ClickedComponent.sales;
		salesHeader.addComponent(custTypeDropDown);
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

		mContent.setExpandRatio(salesGrid, 1);
		salesGrid.setSizeFull();
		updateSales(null, null);
	}

	private void updateSales(String customerType, String vehicleType) {
		List<Sales> salesItems = InvetoryService.getSalesItems(customerType, vehicleType);
		double total = 0.0;
		for (Sales s : salesItems) {
			total += s.getBilledAmount().doubleValue();
		}
		this.totalBill.setValue("Total Sales :" + total);
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

	private void showRecentTrips() {
		removePreviousComponents();
		previousclicked = ClickedComponent.trips;
		mContent.addComponent(filterText);
		mContent.addComponent(recentTripGrid);
		recentTripGrid.setSizeFull();
		mContent.setExpandRatio(recentTripGrid, 1);
		recentTripGrid.setItems(InvetoryService.getRecentTrips(_toIntOrError(filterText.getValue())));
		tripComponents = new ArrayList<>();
		tripComponents.add(recentTripGrid);
		tripComponents.add(filterText);

	}

	private int _toIntOrError(String value) {
		try {
			int intValue = Integer.parseInt(value);
			return intValue;
		} catch (NumberFormatException ee) {
			log.error("Only integer value allowed. Fall backed to value 7");// TODO can be shown as a dialog
			return 7;
		}
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
