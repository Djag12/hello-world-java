package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Applies the assignment's required transformations to products.
 * Transform order:
 *   1) Uppercase name
 *   2) 10% discount if original category == "Electronics"; round HALF_UP to 2 decimals
 *   3) If final price > 500.00 AND original category == "Electronics", set category to "Premium Electronics"
 *   4) Compute PriceRange from final price
 */
public class ProductTransformer {

    /**
     * Transform all products in-place following the required order.
     *
     * @param products list of parsed Product objects
     */
    public void transform(List<Product> products) {
        for (Product p : products) {
            // (1) uppercase name
            p.setName(p.getName().toUpperCase());

            // (2) discount for Electronics; always round to 2 dp (HALF_UP)
            BigDecimal price = p.getFinalPrice();
            if (p.isOriginalElectronics()) {
                price = price.multiply(BigDecimal.valueOf(0.90));
            }
            price = price.setScale(2, RoundingMode.HALF_UP);
            p.setFinalPrice(price);

            // (3) recategorize if needed
            if (p.isOriginalElectronics() && price.compareTo(new BigDecimal("500.00")) > 0) {
                p.setCategory("Premium Electronics");
            }

            // (4) price range
            p.setPriceRange(priceRangeFor(price));
        }
    }

    private String priceRangeFor(BigDecimal price) {
        if (price.compareTo(new BigDecimal("0.00")) >= 0 &&
            price.compareTo(new BigDecimal("10.00")) <= 0) return "Low";
        if (price.compareTo(new BigDecimal("10.00")) > 0 &&
            price.compareTo(new BigDecimal("100.00")) <= 0) return "Medium";
        if (price.compareTo(new BigDecimal("100.00")) > 0 &&
            price.compareTo(new BigDecimal("500.00")) <= 0) return "High";
        return "Premium"; // > 500.00
    }
}
