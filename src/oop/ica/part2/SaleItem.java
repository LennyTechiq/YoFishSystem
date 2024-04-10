package oop.ica.part2;

import java.text.DecimalFormat;

public class SaleItem {
    private int id; // Unique identifier for the item
    private String item; // Name of the item
    private double price; // Price of the item
    private int stock; // Current stock quantity
    private double maxSize; // Maximum size of the item
    private int lowTemp; // Lowest temperature at which the item can be stored
    private int highTemp; // Highest temperature at which the item can be stored

    public SaleItem() {
    }
    
    // Constructor to initialize SaleItem object with provided values
    public SaleItem(int id, String item, double price, int stock, double maxSize, int lowTemp, int highTemp) {
        this.id = id;
        this.item = item;
        this.price = price;
        this.stock = stock;
        this.maxSize = maxSize;
        this.lowTemp = lowTemp;
        this.highTemp = highTemp;
    }

    // Getters and Setters for private fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicFilename() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(double maxSize) {
        this.maxSize = maxSize;
    }

    public int getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(int lowTemp) {
        this.lowTemp = lowTemp;
    }

    public int getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(int highTemp) {
        this.highTemp = highTemp;
    }

    // Method to format price with £ symbol and fixed to two decimal places
    public String getFormattedPrice() {
        DecimalFormat df = new DecimalFormat("#0.00");
        return "£" + df.format(price);
    }

    // Override toString method to provide a string representation of SaleItem object
    @Override
    public String toString() {
        return "ID: " + id + ", Item: " + item + ", Price: " + getFormattedPrice() +
                ", Stock: " + stock + ", Max Size: " + maxSize + "cm, Low Temp: " + lowTemp +
                "°C, High Temp: " + highTemp + "°C";
    }
}
