package myprojects.automation.assignment4.model;

import java.util.Random;

/**
 * Hold Product information that is used among tests.
 */
public class ProductData {
    private String name;
    private int qty;
    private float price;

    public ProductData(String name, int qty, Float price) {
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Integer getQty() {
        return qty;
    }

    public Float getPrice() {
        return price;
    }

    /**
     * @return New Product object with random name, quantity and price values.
     */
    public static ProductData generate() {
        Random random = new Random();
        return new ProductData(
                "New Product " + System.currentTimeMillis(),
                random.nextInt(100) + 1,
                (float) Math.round(random.nextInt(100_00) + 1) / 100);
    }
}
