package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;

/**
 * Data model for a single product row.
 * Holds parsed fields and transformation results.
 */
public class Product {
    private final int id;
    private String name;                 // uppercased during transform
    private BigDecimal finalPrice;       // after any discount, rounded HALF_UP
    private String category;             // possibly recategorized
    private String priceRange;           // derived from finalPrice
    private final boolean originalElectronics;

    /**
     * Construct a product from CSV fields.
     *
     * @param id        ProductID (integer)
     * @param name      Name (string)
     * @param price     Price (BigDecimal)
     * @param category  Category (string)
     */
    public Product(int id, String name, BigDecimal price, String category) {
        this.id = id;
        this.name = name;
        this.finalPrice = price;
        this.category = category;
        this.originalElectronics = "Electronics".equals(category);
    }

    // Getters / setters used by the transformer
    public int getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getFinalPrice() { return finalPrice; }
    public String getCategory() { return category; }
    public String getPriceRange() { return priceRange; }
    public boolean isOriginalElectronics() { return originalElectronics; }

    public void setName(String name) { this.name = name; }
    public void setFinalPrice(BigDecimal finalPrice) { this.finalPrice = finalPrice; }
    public void setCategory(String category) { this.category = category; }
    public void setPriceRange(String priceRange) { this.priceRange = priceRange; }
}
