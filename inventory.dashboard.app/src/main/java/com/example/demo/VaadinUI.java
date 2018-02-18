package com.example.demo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.vaadin.server.VaadinRequest;
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

	private List<Component> tripComponents;
	private List<Component> customerComponents;
	private List<Component> salesComponents;
	ComboBox<String> custTypeDropDown;
	HorizontalLayout salesHeader;
	Label totalBill;

	@Override
	protected void init(VaadinRequest request) {
		// TODO Auto-generated method stub
		mContent = new VerticalLayout();
		mContent.setSpacing(true);
		setContent(mContent);
		HorizontalLayout titleBar = new HorizontalLayout();
		titleBar.setWidth("100%");
		mContent.addComponent(titleBar);

		Label title = new Label("Car Rental Inventory System");
		title.setStyleName(ValoTheme.LABEL_H1);
		title.setSizeUndefined();
		titleBar.addComponent(title);
		titleBar.setExpandRatio(title, 1.0f); // Expand

		tripBtn = new Button("Trips");
		tripBtn.addClickListener(e -> showRecentTrips());
		tripBtn.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		tripBtn.setSizeUndefined();
		mContent.addComponent(tripBtn);

		customerBtn = new Button("Customers");
		customerBtn.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		customerBtn.addClickListener(e -> showCustomers());
		mContent.addComponent(customerBtn);

		analyticsBtn = new Button("Analytics");
		analyticsBtn.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		mContent.addComponent(analyticsBtn);
		analyticsBtn.addClickListener(e -> showSales());

		custTypeDropDown = new ComboBox<>("Filter by Customer Type");
		custTypeDropDown.setItems(new String[] { "INDIVIDUAL", "CORPORATE" });
		custTypeDropDown.addValueChangeListener(e -> handleCustomerType());
		salesGrid = new Grid<>(Sales.class);

		// Initialize Grids

		customerGrid = new Grid<>(Customer.class);
		customerGrid.setVisible(false);

		recentTripGrid = new Grid<>(Trip.class);
		filterText = new TextField();
		filterText.setPlaceholder("filter by number of days : Ener any Int value..");
		filterText.addValueChangeListener(e -> showRecentTrips());

		customerFilterText = new TextField();
		customerFilterText.setPlaceholder("filter by Customer Type");
		customerFilterText.addValueChangeListener(e -> showCustomers());
		salesHeader = new HorizontalLayout();
		totalBill = new Label("Total Sales :");
		// By Default
		tripBtn.click();
		tripBtn.focus();

	}

	private void handleCustomerType() {
		String value = custTypeDropDown.getValue();
		if (value == null || value.trim().isEmpty()) {
			salesGrid.setItems(getSalesItems(null, null));
		} else {
			salesGrid.setItems(getSalesItems(value, null));
		}
		updateSales(value, null);
	}

	private ClickedComponent previousclicked;

	enum ClickedComponent {
		sales, trips, customers
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
		salesGrid.setItems(getSalesItems(null, null));
		salesComponents = new ArrayList<>();
		salesComponents.add(custTypeDropDown);
		salesComponents.add(salesGrid);
		salesComponents.add(salesHeader);

		mContent.setExpandRatio(salesGrid, 1);
		salesGrid.setSizeFull();
		updateSales(null, null);
	}

	private void updateSales(String customerType, String vehicleType) {
		List<Sales> salesItems = getSalesItems(customerType, vehicleType);
		double total = 0.0;
		for (Sales s : salesItems) {
			total += s.getBilledAmount().doubleValue();
		}
		this.totalBill.setValue("Total Sales :" + total);
	}

	private List<Sales> getSalesItems(String customerType, String vehicleType) {
		String url = "http://localhost:9091/sales/";
		RestTemplate restTemplate = new RestTemplate();
		List<Sales> sales = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			try {
				Sales s = restTemplate.getForObject(url + i, Sales.class);
				System.out.println("Got the one sale " + s);
				if (s != null) {
					// TODO write REST API to handle the customerType
					if (customerType != null && vehicleType != null) {
						if (s.getCustomerType().equalsIgnoreCase(customerType)
								&& s.getVehicleType().equalsIgnoreCase(vehicleType))
							sales.add(s);
					} else if (customerType != null) {
						if (s.getCustomerType().equalsIgnoreCase(customerType)) {
							sales.add(s);
						}
					} else if (vehicleType != null) {
						if (s.getVehicleType().equalsIgnoreCase(vehicleType)) {
							sales.add(s);
						}
					} else {
						sales.add(s);// add all
					}
				}
			} catch (Exception e) {
				System.out.println("May be no more elements! " + e.toString());
				break;
			}
		}
		return sales;
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
		} else {
			if (salesComponents != null) {
				for (Component c : salesComponents) {
					this.mContent.removeComponent(c);
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
		recentTripGrid.setItems(getRecentTrips(_toIntOrError(filterText.getValue())));
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
		this.customerGrid.setItems(getCustomers(customerFilterText.getValue()));
		mContent.setExpandRatio(customerGrid, 1);
		customerGrid.setSizeFull();
		customerComponents = new ArrayList<>();
		customerComponents.add(customerGrid);
		customerComponents.add(customerFilterText);
	}

	private Collection<Customer> getCustomers(String filterTxt) {
		String url = "http://localhost:8080/customer/";
		RestTemplate restTemplate = new RestTemplate();
		List<Customer> customers = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			try {
				Customer customer = restTemplate.getForObject(url + i, Customer.class);
				System.out.println("Got the one " + customer);
				if (customer != null) {
					// TODO write REST API to handle the customerType
					if (filterTxt.trim().isEmpty())
						customers.add(customer);
					else if (customer.getCustomerType().equalsIgnoreCase(filterTxt))
						customers.add(customer);
				}
			} catch (Exception e) {
				System.out.println("May be no more elements! " + e.toString());
				break;
			}
		}
		return customers;
	}

	private List<Trip> getRecentTrips(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -days);
		Date time = cal.getTime();
		System.out.println("Date = " + time);

		String url = "http://localhost:9001/trip/";
		RestTemplate restTemplate = new RestTemplate();
		List<Trip> trips = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			try {
				Trip t = restTemplate.getForObject(url + i, Trip.class);
				System.out.println("Got the one Trip " + t);
				if (t != null) {
					if (t.getTripStart().getTime() >= time.getTime())
						trips.add(t);
				}
			} catch (Exception e) {
				System.out.println("May be no more trips! " + e.toString());
				break;
			}
		}
		return trips;
	}

}
