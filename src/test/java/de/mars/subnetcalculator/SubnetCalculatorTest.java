package de.mars.subnetcalculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Diese Testklasse überprüft die statischen Methoden der Klasse {@link SubnetCalculator},
 * insbesondere die Ausgabeinhalte bei der Berechnung einzelner oder mehrerer Subnetze.
 */
public class SubnetCalculatorTest {

    /**
     * Testet, ob die Methode {@link SubnetCalculator#calculateSingleSubnet(String, int)}
     * grundlegende Informationen wie "Netzadresse", "Broadcast" und "CIDR-Notation"
     * korrekt in der Ausgabe enthält.
     */
    @Test
    public void testCalculateSingleSubnetIncludesExpectedStrings() {
        String result = SubnetCalculator.calculateSingleSubnet("192.168.1.0", 24);
        assertTrue(result.contains("Netzadresse:"));
        assertTrue(result.contains("Broadcast:"));
        assertTrue(result.contains("CIDR-Notation:"));
    }

    /**
     * Testet, ob die Methode {@link SubnetCalculator#calculateMultipleSubnets(String, int, int)}
     * exakt die erwartete Anzahl an Subnetzen generiert.
     * Es wird gezählt, wie oft der Begriff "Subnetz" in der Ausgabe vorkommt.
     */
    @Test
    public void testCalculateMultipleSubnetsCount() {
        String result = SubnetCalculator.calculateMultipleSubnets("192.168.1.0", 24, 4);
        long count = result.lines().filter(line -> line.contains("Subnetz ")).count();
        assertEquals(4, count);
    }
}

