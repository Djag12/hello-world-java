package org.howard.edu.lsp.finale.question1;

import java.util.Random;

/*
 * Design Patterns Used:
 * 1. Singleton — only one instance of the password generator exists.
 * 2. Strategy — algorithm behavior changes at runtime via setAlgorithm().
 */
public class PasswordGeneratorService {
    private static PasswordGeneratorService instance;
    private String algorithm = null;

    private final Random rand = new Random();
    private final String DIGITS = "0123456789";
    private final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final String ALL = LETTERS + DIGITS;

    private PasswordGeneratorService() {}

    public static PasswordGeneratorService getInstance() {
        if (instance == null) instance = new PasswordGeneratorService();
        return instance;
    }

    public void setAlgorithm(String name) {
        this.algorithm = name;
    }

    public String generatePassword(int length) {
        if (algorithm == null) throw new IllegalStateException("No algorithm selected.");

        String source = algorithm.equals("basic") ? DIGITS :
                        algorithm.equals("letters") ? LETTERS : ALL;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
            sb.append(source.charAt(rand.nextInt(source.length())));

        return sb.toString();
    }
}
