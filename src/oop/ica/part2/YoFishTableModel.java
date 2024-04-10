package oop.ica.part2;

import javax.swing.table.DefaultTableModel;

public class YoFishTableModel extends DefaultTableModel {
    
    // Constructor to initialize the table model with column headers
    public YoFishTableModel() {
        // Call the constructor of DefaultTableModel with column headers and 0 rows initially
        super(new Object[]{"ID", "Item", "Price (£)", "Stock", "Max Size (cm)", "Low Temp (°C)", "High Temp (°C)"}, 0);
    }

    // Override isCellEditable method to make cells uneditable
    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // Make cells uneditable
    }
}
