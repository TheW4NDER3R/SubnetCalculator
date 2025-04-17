package de.mars.subnetcalculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Diese Testklasse überprüft die Methoden zur Subnetzberechnung
 * in der Klasse SubnetCalculator.
 */
public class SubnetCalculatorTest {

    /**
     * Testet, ob die Ausgabe der Methode calculateSingleSubnet()
     * wichtige Begriffe wie "Netzadresse", "Broadcast" und "CIDR-Notation"
     * enthält. Damit wird sichergestellt, dass die grundlegenden Informationen
     * eines Subnetzes angezeigt werden.
     */
    @Test
    public void testCalculateSingleSubnetIncludesExpectedStrings() {
        String result = SubnetCalculator.calculateSingleSubnet("192.168.1.0", 24);
        assertTrue(result.contains("Netzadresse:"));
        assertTrue(result.contains("Broadcast:"));
        assertTrue(result.contains("CIDR-Notation:"));
    }

    /**
     * Testet, ob die Methode calculateMultipleSubnets() genau so viele Subnetze
     * berechnet, wie im Parameter übergeben wurden (in diesem Fall 4).
     * Es wird gezählt, wie oft das Wort "Subnetz" in der Ausgabe vorkommt.
     */
    @Test
    public void testCalculateMultipleSubnetsCount() {
        String result = SubnetCalculator.calculateMultipleSubnets("192.168.1.0", 24, 4);
        long count = result.lines().filter(line -> line.contains("Subnetz ")).count();
        assertEquals(4, count);
    }
}

