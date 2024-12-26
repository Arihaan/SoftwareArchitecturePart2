package view;

import javax.swing.*;
import java.awt.*;
import model.*;
import java.util.List;
import java.util.Queue;

public class DepotView extends JFrame {
    private JTextArea parcelListArea;
    private JTextArea customerQueueArea;
    private JTextArea currentProcessArea;
    private JButton processButton;
    private JButton addCustomerButton;
    private JButton addParcelButton;

    public DepotView() {
        setTitle("Parcel Depot Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create main panels
        JPanel leftPanel = createParcelPanel();
        JPanel centerPanel = createCustomerPanel();
        JPanel rightPanel = createProcessPanel();
        JPanel buttonPanel = createButtonPanel();

        // Add panels to frame
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createParcelPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Parcels in Depot"));
        
        parcelListArea = new JTextArea(20, 30);
        parcelListArea.setEditable(false);
        
        panel.add(new JScrollPane(parcelListArea), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Customer Queue"));
        
        customerQueueArea = new JTextArea(20, 30);
        customerQueueArea.setEditable(false);
        
        panel.add(new JScrollPane(customerQueueArea), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createProcessPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Current Process"));
        
        currentProcessArea = new JTextArea(20, 30);
        currentProcessArea.setEditable(false);
        
        panel.add(new JScrollPane(currentProcessArea), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        processButton = new JButton("Process Next Customer");
        addCustomerButton = new JButton("Add Customer");
        addParcelButton = new JButton("Add Parcel");
        
        panel.add(processButton);
        panel.add(addCustomerButton);
        panel.add(addParcelButton);
        
        return panel;
    }

    // Methods to update the view
    public void updateParcelList(List<Parcel> parcels) {
        StringBuilder sb = new StringBuilder();
        for (Parcel p : parcels) {
            sb.append(p.toString()).append("\n");
        }
        parcelListArea.setText(sb.toString());
    }

    public void updateCustomerQueue(Queue<Customer> customers) {
        StringBuilder sb = new StringBuilder();
        for (Customer c : customers) {
            sb.append(c.toString()).append("\n");
        }
        customerQueueArea.setText(sb.toString());
    }

    public void updateCurrentProcess(ProcessResult result) {
        if (result == null) {
            currentProcessArea.setText("No customer being processed");
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Processing customer: ").append(result.getCustomer().getName())
          .append("\nParcel ID: ").append(result.getParcel().getId())
          .append("\nFee: £").append(String.format("%.2f", result.getFee()))
          .append("\nStatus: ").append(result.isSuccessful() ? "Success" : "Failed");
        
        currentProcessArea.setText(sb.toString());
    }

    // Getters for buttons to attach listeners
    public JButton getProcessButton() { return processButton; }
    public JButton getAddCustomerButton() { return addCustomerButton; }
    public JButton getAddParcelButton() { return addParcelButton; }

    // Temporary main method for testing the GUI layout
    public static void main(String[] args) {
        // Run GUI code on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            DepotView view = new DepotView();
            view.setVisible(true);
            
            // Add some dummy data for testing
            view.currentProcessArea.setText("Test processing area\nNo customer being processed");
            view.parcelListArea.setText("Test parcel list area\nNo parcels in depot");
            view.customerQueueArea.setText("Test customer queue area\nNo customers in queue");
        });
    }
} 