package oop.ica.part2;

public class PondlifeProductAdapter {
    private final PondlifeProduct product;

    public PondlifeProductAdapter(PondlifeProduct product) {
        this.product = product;
    }

    public int getId() {
        return product.getSkuNumber();
    }

    public String getPicFilename() {
        return product.getProduct();
    }

    public double getPrice() {
        return product.getCost();
    }

    public int getStock() {
        return product.getStock();
    }

    public void setStock(int newStock) {
        product.setStock(newStock);
    }
}
