package view;

import javax.swing.*;
import java.awt.*;
import model.Customer;

public class AddCustomerDialog {
    private JDialog dialog;
    private JTextField nameField;
    private JTextField parcelIdField;
    private String[] result = null;

    public AddCustomerDialog(JFrame parent) {
        dialog = new JDialog(parent, "Add New Customer", true);
        dialog.setLayout(new BorderLayout(10, 10));
        
        // Create input panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField(20);
        inputPanel.add(nameField);
        
        inputPanel.add(new JLabel("Parcel ID:"));
        parcelIdField = new JTextField(20);
        inputPanel.add(parcelIdField);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        
        okButton.addActionListener(e -> {
            if (validateInput()) {
                result = new String[]{nameField.getText(), parcelIdField.getText()};
                dialog.dispose();
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
    }

    private boolean validateInput() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(dialog, "Please enter a name", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!parcelIdField.getText().matches("[CX]\\d{3}")) {
            JOptionPane.showMessageDialog(dialog, "Parcel ID must be in format 'C' or 'X' followed by 3 digits", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public String[] showDialog() {
        dialog.setVisible(true);
        return result;
    }
} 