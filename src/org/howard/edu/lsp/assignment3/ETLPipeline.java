package org.howard.edu.lsp.assignment3;

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

/**
 * Assignment 3 entry point.
 * Performs Extract -> Transform -> Load using Product and ProductTransformer.
 * Behavior and outputs match Assignment 2 exactly.
 */
public class ETLPipeline {
    private static final String DATA_DIR = "data";
    private static final String INPUT_FILE = "products.csv";
    private static final String OUTPUT_FILE = "transformed_products.csv";

    /** Simple run counters for the summary. */
    private static class Counters {
        int rowsRead = 0;
        int transformed = 0;
        int skipped = 0;
    }

    /**
     * Main entrypoint.
     * Reads data/products.csv, applies transforms, writes data/transformed_products.csv, prints a summary.
     */
    public static void main(String[] args) {
        Path input = Paths.get(DATA_DIR, INPUT_FILE);
        Path output = Paths.get(DATA_DIR, OUTPUT_FILE);
        Counters c = new Counters();

        // Extract
        List<Product> products = extract(input, c);
        if (products == null) return; // missing file or read failure already reported

        // Transform
        ProductTransformer transformer = new ProductTransformer();
        transformer.transform(products);
        c.transformed = products.size();

        // Load
        boolean ok = load(output, products);
        if (!ok) {
            System.err.println("ERROR: Failed to write output file: " + output);
            return;
        }

        // Summary
        System.out.println("---- Run Summary ----");
        System.out.println("Rows read:        " + c.rowsRead);
        System.out.println("Rows transformed: " + c.transformed);
        System.out.println("Rows skipped:     " + c.skipped);
        System.out.println("Output path:      " + output);
    }

    /**
     * Extract: parse the CSV into Product objects.
     * Requirements:
     *  - If input file is missing: print clear error and exit gracefully (return null).
     *  - If file contains only a header: return empty list (we will still produce header-only output).
     */
    private static List<Product> extract(Path input, Counters c) {
        if (!Files.exists(input)) {
            System.err.println("ERROR: Input file not found: " + input);
            System.err.println("Ensure the file exists at 'data/products.csv' relative to the project root.");
            return null;
        }
        List<Product> out = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(input)) {
            String line = br.readLine(); // header
            if (line == null) return out;  // empty file (no rows)
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                c.rowsRead++;
                String[] parts = line.split(",", -1); // spec: no commas/quotes inside fields
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

    /**
     * Load: write header + transformed rows to the output CSV.
     * Always creates the file with a header row, even if there are zero rows.
     */
    private static boolean load(Path output, List<Product> products) {
        try {
            Files.createDirectories(output.getParent());
            try (BufferedWriter bw = Files.newBufferedWriter(output)) {
                // header
                bw.write("ProductID,Name,Price,Category,PriceRange");
                bw.newLine();

                for (Product p : products) {
                    String priceStr = p.getFinalPrice().setScale(2, RoundingMode.HALF_UP).toPlainString();
                    bw.write(p.getId() + "," + p.getName() + "," + priceStr + "," + p.getCategory() + "," + p.getPriceRange());
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
