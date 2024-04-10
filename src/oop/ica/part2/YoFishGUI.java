package oop.ica.part2;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class YoFishGUI extends JFrame {

    private final List<SaleItem> stock = new ArrayList<>(); // List to store sale items
    private JTable yoFishTable; // Table to display sale items
    private final JLabel picLabel; // Label to display item photo
    private final JLabel photoLabel; // Label to display photo
    private final JLabel itemLabel; // Label to display item name
    private final JButton buyButton; // Button to buy items
    private final JButton addButton; // Button to add items
    private final JButton quitButton; // Button to quit application

    public YoFishGUI() {
        setTitle("Yo-Fish"); // Set window title
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Set default close operation
        setSize(1000, 450); // Set window size
        setLayout(new BorderLayout()); // Set layout of the frame

        // Margin Panel
        JPanel marginPanel = new JPanel(new BorderLayout()); // Create margin panel
        marginPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add margin

        // Stock List Table
        YoFishTableModel model = new YoFishTableModel(); // Create table model
        yoFishTable = new JTable(model); // Create table with the model
        yoFishTable.setRowHeight(25); // Increased row height
        yoFishTable.setFocusable(false); // Disable cell focus
        yoFishTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow only single row selection
        yoFishTable.setDefaultRenderer(Object.class, new CustomTableCellRenderer()); // Custom renderer for row colors
        JScrollPane scrollPane = new JScrollPane(yoFishTable); // Create scroll pane for the table
        scrollPane.setPreferredSize(new Dimension(700, 600)); // Increased width
        marginPanel.add(scrollPane, BorderLayout.WEST); // Center the table

        // Image Panel
        JPanel picPanel = new JPanel(new BorderLayout()); // Create image panel
        picPanel.setPreferredSize(new Dimension(300, 600)); // Reduced height
        picPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add margin inside the panel
        picPanel.add(picLabel = new JLabel("Item Photo", SwingConstants.CENTER), BorderLayout.NORTH); // Add label for item photo
        picLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font for the label
        photoLabel = new JLabel(); // Create label for photo
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER); // Set alignment of the label
        picPanel.add(photoLabel, BorderLayout.CENTER); // Add photo label to the panel

        // New JPanel for the item label and button panel
        JPanel bottomPanel = new JPanel(new BorderLayout()); // Create bottom panel

        // Add itemLabel above the button panel
        itemLabel = new JLabel(); // Create label for item name
        itemLabel.setHorizontalAlignment(SwingConstants.CENTER); // Set alignment of the label
        bottomPanel.add(itemLabel, BorderLayout.NORTH); // Add item label to the bottom panel

        // New JPanel for the button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Create button panel
        buyButton = new JButton("Buy"); // Create buy button
        addButton = new JButton("Add"); // Create add button
        quitButton = new JButton("Quit"); // Create quit button
        quitButton.setBackground(Color.RED); // Set background color of quit button
        quitButton.setForeground(Color.WHITE); // Set text color of quit button
        buttonPanel.add(buyButton); // Add buy button to the panel
        buttonPanel.add(addButton); // Add add button to the panel
        buttonPanel.add(quitButton); // Add quit button to the panel
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH); // Add button panel to the bottom panel

        picPanel.add(bottomPanel, BorderLayout.SOUTH); // Add bottom panel to the image panel
        picPanel.setBorder(new LineBorder(Color.GRAY)); // Add line border
        marginPanel.add(picPanel, BorderLayout.EAST); // Moved the image panel to the right
        marginPanel.add(scrollPane, BorderLayout.CENTER); // Center the table

        add(marginPanel, BorderLayout.CENTER); // Add the margin panel to the frame

        // Load products from text file
        loadProductsFromFile("yo-fish.txt"); // Load products from file
        loadPondlifeProductsFromFile("pondlife.txt");
        updateProductTable(); // Update product table

        // Listen for item selection changes
        yoFishTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = yoFishTable.getSelectedRow();
                if (selectedRow >= 0) {
                    displayItemImage((int) yoFishTable.getValueAt(selectedRow, 0)); // Display item image
                    displayItemName(selectedRow); // Display item name
                    checkStockQuantity(selectedRow); // Check stock quantity when an item is selected
                }
            }
        });

        // Custom renderer for row colors
        yoFishTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    cellComponent.setBackground(Color.BLUE); // Change background color for selected row
                } else {
                    cellComponent.setBackground(table.getBackground()); // Reset background color for other rows
                }
                return cellComponent;
            }
        });

        // Action Listeners
        addButton.addActionListener(e -> addItemToStock()); // Add action listener for add button
        buyButton.addActionListener(e -> buySelectedItem());
        quitButton.addActionListener(e -> { // Add action listener for quit button
            saveStockToFile("output.txt"); // Save stock to file
            dispose(); // Close the application
        });
    }

    // Method to load products from file
    private void loadProductsFromFile(String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) { // Open file for reading
            while (scanner.hasNextLine()) { // Loop through each line
                String line = scanner.nextLine(); // Read a line
                String[] parts = line.split(","); // Split the line by comma
                if (parts.length == 7) { // Check if there are 7 parts
                    int id = Integer.parseInt(parts[0].trim()); // Parse ID
                    String item = parts[1].trim(); // Get item name
                    double price = Double.parseDouble(parts[2].trim()); // Parse price
                    int stock1 = Integer.parseInt(parts[3].trim()); // Parse stock
                    double maxSize = Double.parseDouble(parts[4].trim()); // Parse max size
                    int lowTemp = Integer.parseInt(parts[5].trim()); // Parse low temperature
                    int highTemp = Integer.parseInt(parts[6].trim()); // Parse high temperature
                    stock.add(new SaleItem(id, item, price, stock1, maxSize, lowTemp, highTemp)); // Create SaleItem and add to stock list
                } else {
                    System.err.println("Invalid line: " + line); // Print error message for invalid line
                }
            }
        } catch (FileNotFoundException e) { // Handle file not found exception
            System.err.println("Error reading file: " + e.getMessage()); // Print error message
        }
    }
    
    private void loadPondlifeProductsFromFile(String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 8) {
                    int skuNumber = Integer.parseInt(parts[0].trim());
                    String product = parts[1].trim();
                    String notes = parts[2].trim();
                    double maxLength = Double.parseDouble(parts[3].trim());
                    int minTemp = Integer.parseInt(parts[4].trim());
                    int maxTemp = Integer.parseInt(parts[5].trim());
                    double cost = Double.parseDouble(parts[6].trim());
                    int stock1 = Integer.parseInt(parts[7].trim());

                    // Create PondlifeProduct instance
                    PondlifeProduct pondlifeProduct = new PondlifeProduct(skuNumber, product, notes, maxLength, minTemp, maxTemp, cost, stock1);

                    // Create SaleItem instance from PondlifeProduct
                    SaleItem saleItem = new SaleItem() {
                        @Override
                        public int getId() {
                            return pondlifeProduct.getSkuNumber();
                        }

                        @Override
                        public String getPicFilename() {
                            return pondlifeProduct.getProduct();
                        }

                        @Override
                        public double getPrice() {
                            return pondlifeProduct.getCost();
                        }

                        @Override
                        public int getStock() {
                            return pondlifeProduct.getStock();
                        }

                        @Override
                        public void setStock(int newStock) {
                            pondlifeProduct.setStock(newStock);
                        }
                    };

                    // Add SaleItem instance to stock list
                    stock.add(saleItem);
                } else {
                    System.err.println("Invalid line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    
    // Method to check stock quantity
    private void checkStockQuantity(int selectedRow) {
        String itemFileName = (String) yoFishTable.getValueAt(selectedRow, 1); // Get item filename
        int stockQuantity = (int) yoFishTable.getValueAt(selectedRow, 3); // Get stock quantity

        if (stockQuantity < 5) { // Check if stock quantity is less than 5
            String message = "\'" + itemFileName + "\' has only " + stockQuantity + " stock units."; // Create message
            JOptionPane.showMessageDialog(this, message, "Stock Alert", JOptionPane.WARNING_MESSAGE); // Show warning message
        }
    }

    // Method to update product table
    private void updateProductTable() {
        YoFishTableModel model = (YoFishTableModel) yoFishTable.getModel(); // Get table model
        for (SaleItem item : stock) { // Loop through each item in stock
            model.addRow(new Object[]{item.getId(), item.getPicFilename(), item.getPrice(), item.getStock(), item.getMaxSize(), item.getLowTemp(), item.getHighTemp()}); // Add row to table model
        }
    }

    // Method to add selected item to stock
    private void addItemToStock() {
        int selectedRow = yoFishTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Request quantity to be added to stock
            String quantityStr = JOptionPane.showInputDialog(this, "Enter quantity to add to stock (5-20):", "Add to Stock", JOptionPane.PLAIN_MESSAGE);
            if (quantityStr != null && !quantityStr.isEmpty()) {
                try {
                    int quantityToAdd = Integer.parseInt(quantityStr);
                    if (quantityToAdd >= 5 && quantityToAdd <= 20) {
                        // Update stock quantity
                        int currentStock = (int) yoFishTable.getValueAt(selectedRow, 3); // Current stock
                        int newStock = currentStock + quantityToAdd; // New stock after addition
                        yoFishTable.setValueAt(newStock, selectedRow, 3); // Update table
                        stock.get(selectedRow).setStock(newStock); // Update list

                        // Inform the user
                        JOptionPane.showMessageDialog(this, "Item: " + yoFishTable.getValueAt(selectedRow, 1) +
                                "\nUnits Added: " + quantityToAdd +
                                "\nNew Stock Quantity: " + newStock, "Confirmation of Added Stock", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Please enter a quantity between 5 and 20.", "Invalid Quantity", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid number for quantity.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                }
            }
        } else {
            // If no item is selected, show a warning message
            JOptionPane.showMessageDialog(this, "Please select an item to add to the stock.", "No Item Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Method to display item image
    private void displayItemImage(int itemId) {
        String imagePath = "pics/" + itemId + ".jpg"; // Get image path
        try {
            Image image = new ImageIcon(imagePath).getImage(); // Create image icon
            Image scaledImage = image.getScaledInstance(250, 250, Image.SCALE_SMOOTH); // Scale image
            photoLabel.setIcon(new ImageIcon(scaledImage)); // Set scaled image to photo label
        } catch (Exception e) { // Handle exception
            System.err.println("Image file not found: " + imagePath); // Print error message
            photoLabel.setIcon(null); // Set photo label icon to null
        }
    }

    // Method to display item name
    private void displayItemName(int selectedRow) {
        String itemName = (String) yoFishTable.getValueAt(selectedRow, 1); // Get item name
        itemLabel.setText(itemName); // Set item name to item label
    }
    
    // Method to buy selected item
    private void buySelectedItem() {
        int selectedRow = yoFishTable.getSelectedRow();
        if (selectedRow >= 0) {
            int stockQuantity = (int) yoFishTable.getValueAt(selectedRow, 3); // Get stock quantity
            if (stockQuantity > 0) {
                // Request quantity to be bought
                String quantityStr = JOptionPane.showInputDialog(this, "Enter quantity to buy (available: " + stockQuantity + ")", "Quantity", JOptionPane.PLAIN_MESSAGE);
                if (quantityStr != null && !quantityStr.isEmpty()) {
                    try {
                        int quantityToBuy = Integer.parseInt(quantityStr);
                        if (quantityToBuy > 0 && quantityToBuy <= stockQuantity) {
                            // Update stock quantity
                            int newStockQuantity = stockQuantity - quantityToBuy;
                            yoFishTable.setValueAt(newStockQuantity, selectedRow, 3); // Update table
                            stock.get(selectedRow).setStock(newStockQuantity); // Update list

                            // Display confirmation message
                            String itemName = (String) yoFishTable.getValueAt(selectedRow, 1); // Get item name
                            double itemPrice = (double) yoFishTable.getValueAt(selectedRow, 2); // Get item price

                            StringBuilder confirmationMessage = new StringBuilder();
                            confirmationMessage.append("Sale completed.\n")
                                    .append("Item: ").append(itemName).append("\n")
                                    .append("Price: ").append(itemPrice).append("\n")
                                    .append("Units Bought: ").append(quantityToBuy).append("\n")
                                    .append("Stock Remaining: ").append(newStockQuantity);

                            JOptionPane.showMessageDialog(this, confirmationMessage.toString(), "Confirmation of Sale", JOptionPane.INFORMATION_MESSAGE);

                            // Check low stock after sale is completed
                            checkStockQuantity(selectedRow);
                        } else {
                            // Show warning if the entered quantity is invalid
                            JOptionPane.showMessageDialog(this, "Please enter a valid quantity between 1 and " + stockQuantity + ".", "Invalid Quantity", JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (NumberFormatException e) {
                        // Show warning if the entered value is not a number
                        JOptionPane.showMessageDialog(this, "Please enter a valid number for quantity.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else {
                // If stock quantity is zero, show a warning message
                JOptionPane.showMessageDialog(this, "Selected item is out of stock.", "Out of Stock", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            // If no item is selected, show a warning message
            JOptionPane.showMessageDialog(this, "Please select an item to buy.", "No Item Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Method to save stock to file
    private void saveStockToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) { // Open file for writing
            for (SaleItem item : stock) { // Loop through each item in stock
                writer.write(item.getId() + "," + item.getPicFilename() + "," + item.getPrice() + ","
                        + item.getStock() + "," + item.getMaxSize() + "," + item.getLowTemp() + ","
                        + item.getHighTemp()); // Write item details to file
                writer.newLine(); // Write new line
            }
        } catch (IOException e) { // Handle IO exception
            System.err.println("Error saving stock to file: " + e.getMessage()); // Print error message
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            YoFishGUI yoFishGUI = new YoFishGUI(); // Create YoFishGUI object
            yoFishGUI.setLocationRelativeTo(null); // Center the window on the screen
            yoFishGUI.setVisible(true); // Make the window visible
        });
    }

    private class CustomTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Set foreground color for selected row text
            if (isSelected) {
                cellComponent.setForeground(Color.WHITE); // Set the text color of selected row to white
                cellComponent.setBackground(Color.BLUE); // Change background color for selected row
            } else {
                cellComponent.setForeground(table.getForeground()); // Reset text color for other rows
                cellComponent.setBackground(table.getBackground()); // Reset background color for other rows
            }

            return cellComponent;
        }
    }

}
