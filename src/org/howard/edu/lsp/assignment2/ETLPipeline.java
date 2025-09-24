package org.howard.edu.lsp.assignment2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ETLPipeline {
    private static final String DATA_DIR = "data";
    private static final String INPUT_FILE = "products.csv";
    private static final String OUTPUT_FILE = "transformed_products.csv";

    private static class Counters {
        int rowsRead = 0;
        int transformed = 0;
        int skipped = 0;
    }

    private static class Product {
        final int id;
        String name;
        BigDecimal priceFinal;
        String category;
        String priceRange;
        final boolean originalElectronics;

        Product(int id, String name, BigDecimal price, String category) {
            this.id = id;
            this.name = name;
            this.priceFinal = price;
            this.category = category;
            this.originalElectronics = "Electronics".equals(category);
        }
    }

    public static void main(String[] args) {
        Path input = Paths.get(DATA_DIR, INPUT_FILE);
        Path output = Paths.get(DATA_DIR, OUTPUT_FILE);
        Counters c = new Counters();

        List<Product> products = extract(input, c);
        if (products == null) return;

        transform(products, c);

        boolean ok = load(output, products);
        if (!ok) {
            System.err.println("ERROR: Failed to write output file: " + output);
            return;
        }

        System.out.println("---- Run Summary ----");
        System.out.println("Rows read:        " + c.rowsRead);
        System.out.println("Rows transformed: " + c.transformed);
        System.out.println("Rows skipped:     " + c.skipped);
        System.out.println("Output path:      " + output);
    }

    private static List<Product> extract(Path input, Counters c) {
        if (!Files.exists(input)) {
            System.err.println("ERROR: Input file not found: " + input);
            return null;
        }
        List<Product> out = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(input)) {
            String line = br.readLine();
            if (line == null) return out;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                c.rowsRead++;
                String[] parts = line.split(",", -1);
                if (parts.length != 4) { c.skipped++; continue; }
                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    BigDecimal price = new BigDecimal(parts[2].trim());
                    String category = parts[3].trim();
                    out.add(new Product(id, name, price, category));
                } catch (Exception e) {
                    c.skipped++;
                }
            }
        } catch (IOException e) {
            System.err.println("ERROR: Unable to read input: " + e.getMessage());
            return null;
        }
        return out;
    }

    private static void transform(List<Product> products, Counters c) {
        for (Product p : products) {
            p.name = p.name.toUpperCase();

            if (p.originalElectronics) {
                p.priceFinal = p.priceFinal.multiply(BigDecimal.valueOf(0.90));
            }
            p.priceFinal = p.priceFinal.setScale(2, RoundingMode.HALF_UP);

            if (p.originalElectronics && p.priceFinal.compareTo(new BigDecimal("500.00")) > 0) {
                p.category = "Premium Electronics";
            }

            p.priceRange = computePriceRange(p.priceFinal);

            c.transformed++;
        }
    }

    private static String computePriceRange(BigDecimal price) {
        if (price.compareTo(new BigDecimal("0.00")) >= 0 &&
            price.compareTo(new BigDecimal("10.00")) <= 0) return "Low";
        if (price.compareTo(new BigDecimal("10.00")) > 0 &&
            price.compareTo(new BigDecimal("100.00")) <= 0) return "Medium";
        if (price.compareTo(new BigDecimal("100.00")) > 0 &&
            price.compareTo(new BigDecimal("500.00")) <= 0) return "High";
        return "Premium";
    }

    private static boolean load(Path output, List<Product> products) {
        try {
            Files.createDirectories(output.getParent());
            try (BufferedWriter bw = Files.newBufferedWriter(output)) {
                bw.write("ProductID,Name,Price,Category,PriceRange");
                bw.newLine();
                for (Product p : products) {
                    String priceStr = p.priceFinal.setScale(2, RoundingMode.HALF_UP).toPlainString();
                    bw.write(p.id + "," + p.name + "," + priceStr + "," + p.category + "," + p.priceRange);
                    bw.newLine();
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("ERROR: Unable to write output: " + e.getMessage());
            return false;
        }
    }
}

