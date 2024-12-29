package controller;

import javax.swing.*;
import java.awt.event.*;
import model.*;
import view.*;

public class DepotController {
    private ParcelMap parcelMap;
    private QueueOfCustomers customerQueue;
    private Worker worker;
    private DepotView view;

    public DepotController(ParcelMap parcelMap, QueueOfCustomers customerQueue, Worker worker, DepotView view) {
        this.parcelMap = parcelMap;
        this.customerQueue = customerQueue;
        this.worker = worker;
        this.view = view;

        initializeListeners();
        updateView();
    }

    private void initializeListeners() {
        view.getProcessButton().addActionListener(e -> processNextCustomer());
        view.getAddCustomerButton().addActionListener(e -> showAddCustomerDialog());
        view.getAddParcelButton().addActionListener(e -> showAddParcelDialog());
    }

    private void processNextCustomer() {
        ProcessResult result = worker.processNextCustomer();
        view.updateCurrentProcess(result);
        updateView();
    }

    private void showAddCustomerDialog() {
        AddCustomerDialog dialog = new AddCustomerDialog(view);
        String[] result = dialog.showDialog();
        
        if (result != null) {
            customerQueue.addCustomer(result[0], result[1]);  // name, parcelId
            updateView();
        }
    }

    private void showAddParcelDialog() {
        AddParcelDialog dialog = new AddParcelDialog(view);
        Parcel parcel = dialog.showDialog();
        
        if (parcel != null) {
            parcelMap.addParcel(parcel);
            updateView();
        }
    }

    private void updateView() {
        view.updateParcelList(parcelMap.getUncollectedParcels());
        view.updateCustomerQueue(customerQueue.getQueue());
    }
} 