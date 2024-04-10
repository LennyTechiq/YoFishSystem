package oop.ica.part2;

/**
 *
 * @author U0018369
 */
public class PondlifeProduct {
    //fields
    private final int skuNumber;
    private final String product;
    private final String notes;
    private final double maxLength;
    private final int minTemp;
    private final int maxTemp;
    private final double cost;
    private int stock;
    
    //constructor
    public PondlifeProduct(int num, String product, String notes, double maxLength, int minTemp, int maxTemp, double cost, int stock){
        this.skuNumber = num;
        this.product = product;
        this.notes = notes;
        this.maxLength = maxLength;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.cost = cost;
        this.stock = stock;
    }
    
    //accessors
    public int getSkuNumber(){ return skuNumber;}
    public String getProduct(){ return product;}
    public String getNotes(){ return notes;}
    public double getMaxLength(){ return maxLength;}
    public int getMinTemp(){ return minTemp;}
    public int getMaxTemp(){ return maxTemp;}
    public double getCost(){ return cost;}
    public int getStock(){ return stock;}

    
    //services
    public void increaseStock(){this.stock++;}
    public void decreaseStock(){this.stock--;}

    void setStock(int newStock) {
        this.stock = newStock;
    }
}
