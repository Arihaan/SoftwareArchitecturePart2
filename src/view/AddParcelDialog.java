package view;

import javax.swing.*;
import java.awt.*;
import model.Parcel;

public class AddParcelDialog {
    private JDialog dialog;
    private JTextField idField;
    private JTextField daysField;
    private JTextField lengthField;
    private JTextField widthField;
    private JTextField heightField;
    private JTextField weightField;
    private Parcel result = null;

    public AddParcelDialog(JFrame parent) {
        dialog = new JDialog(parent, "Add New Parcel", true);
        dialog.setLayout(new BorderLayout(10, 10));
        
        // Create input panel
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        inputPanel.add(new JLabel("Parcel ID:"));
        idField = new JTextField(20);
        inputPanel.add(idField);
        
        inputPanel.add(new JLabel("Days in Depot:"));
        daysField = new JTextField(20);
        inputPanel.add(daysField);
        
        inputPanel.add(new JLabel("Length:"));
        lengthField = new JTextField(20);
        inputPanel.add(lengthField);
        
        inputPanel.add(new JLabel("Width:"));
        widthField = new JTextField(20);
        inputPanel.add(widthField);
        
        inputPanel.add(new JLabel("Height:"));
        heightField = new JTextField(20);
        inputPanel.add(heightField);
        
        inputPanel.add(new JLabel("Weight:"));
        weightField = new JTextField(20);
        inputPanel.add(weightField);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        
        okButton.addActionListener(e -> {
            if (validateInput()) {
                result = createParcel();
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
        try {
            if (!idField.getText().matches("[CX]\\d{3}")) {
                throw new IllegalArgumentException("Parcel ID must be in format 'C' or 'X' followed by 3 digits");
            }
            
            int days = Integer.parseInt(daysField.getText());
            double length = Double.parseDouble(lengthField.getText());
            double width = Double.parseDouble(widthField.getText());
            double height = Double.parseDouble(heightField.getText());
            double weight = Double.parseDouble(weightField.getText());
            
            if (days < 0 || length <= 0 || width <= 0 || height <= 0 || weight <= 0) {
                throw new IllegalArgumentException("All numerical values must be positive");
            }
            
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialog, "Please enter valid numbers", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(dialog, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private Parcel createParcel() {
        return new Parcel(
            idField.getText(),
            Integer.parseInt(daysField.getText()),
            Double.parseDouble(lengthField.getText()),
            Double.parseDouble(widthField.getText()),
            Double.parseDouble(heightField.getText()),
            Double.parseDouble(weightField.getText())
        );
    }

    public Parcel showDialog() {
        dialog.setVisible(true);
        return result;
    }
} 