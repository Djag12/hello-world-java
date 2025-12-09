package org.howard.edu.lsp.finale.question1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PasswordGeneratorServiceTest {

    private PasswordGeneratorService service;

    @BeforeEach
    public void setup() {
        service = PasswordGeneratorService.getInstance();
    }

    @Test
    public void checkInstanceNotNull() {
        assertNotNull(service);
    }

    @Test
    public void checkSingleInstanceBehavior() {
        PasswordGeneratorService second = PasswordGeneratorService.getInstance();
        assertSame(service, second);
    }

    @Test
    public void generateWithoutSettingAlgorithmThrowsException() {
        service.setAlgorithm(null);
        assertThrows(IllegalStateException.class, () -> {
            service.generatePassword(5);
        });
    }

    @Test
    public void basicAlgorithmGeneratesCorrectLengthAndDigitsOnly() {
        service.setAlgorithm("basic");
        String p = service.generatePassword(10);
        assertEquals(10, p.length());
        p.chars().forEach(c -> assertTrue(Character.isDigit(c)));
    }

    @Test
    public void enhancedAlgorithmGeneratesCorrectCharactersAndLength() {
        service.setAlgorithm("enhanced");
        String p = service.generatePassword(12);
        assertEquals(12, p.length());
        p.chars().forEach(c ->
            assertTrue(Character.isDigit(c) || Character.isLetter(c)));
    }

    @Test
    public void lettersAlgorithmGeneratesLettersOnly() {
        service.setAlgorithm("letters");
        String p = service.generatePassword(8);
        assertEquals(8, p.length());
        p.chars().forEach(c -> assertTrue(Character.isLetter(c)));
    }

    @Test
    public void switchingAlgorithmsChangesBehavior() {
        service.setAlgorithm("basic");
        String p1 = service.generatePassword(6);
        p1.chars().forEach(c -> assertTrue(Character.isDigit(c)));

        service.setAlgorithm("letters");
        String p2 = service.generatePassword(6);
        p2.chars().forEach(c -> assertTrue(Character.isLetter(c)));

        service.setAlgorithm("enhanced");
        String p3 = service.generatePassword(6);
        p3.chars().forEach(c ->
            assertTrue(Character.isDigit(c) || Character.isLetter(c)));
    }
}
