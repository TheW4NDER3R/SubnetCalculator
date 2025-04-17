package de.mars.subnetcalculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Diese Testklasse prüft die Methoden der Hilfsklasse IpUtils.
 */
public class IpUtilsTest {

    /**
     * Testet, ob eine IP-Adresse korrekt in eine Ganzzahl umgewandelt
     * und zurückgewandelt wird.
     */
    @Test
    public void testIpToIntAndBack() {
        String ip = "192.168.1.0";
        int ipInt = IpUtils.ipToInt(ip);
        String result = IpUtils.intToIp(ipInt);
        assertEquals(ip, result);
    }

    /**
     * Testet die Erstellung einer Subnetzmaske aus einem Präfix.
     */
    @Test
    public void testPrefixLengthToMask() {
        int mask = IpUtils.prefixLengthToMask(24);
        assertEquals("255.255.255.0", IpUtils.intToIp(mask));
    }

    /**
     * Testet das Extrahieren von Präfixzahlen aus einem String.
     */
    @Test
    public void testExtractPrefixLength() {
        assertEquals(24, IpUtils.extractPrefixLength("/24"));
        assertEquals(16, IpUtils.extractPrefixLength("abc16"));
    }
}

