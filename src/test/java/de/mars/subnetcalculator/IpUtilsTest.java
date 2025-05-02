package de.mars.subnetcalculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Diese Testklasse prüft die Funktionalität der statischen Methoden der Hilfsklasse {@link IpUtils}.
 * Dabei werden Konvertierungen von IP-Adressen sowie die Subnetzmaskenerzeugung getestet.
 */
public class IpUtilsTest {

    /**
     * Testet die Umwandlung einer IP-Adresse in eine Ganzzahl und die Rückkonvertierung.
     * Erwartet, dass das Ergebnis der ursprünglichen IP-Adresse entspricht.
     */
    @Test
    public void testIpToIntAndBack() {
        String ip = "192.168.1.0";
        int ipInt = IpUtils.ipToInt(ip);
        String result = IpUtils.intToIp(ipInt);
        assertEquals(ip, result);
    }

    /**
     * Testet die korrekte Erzeugung einer Subnetzmaske aus einer gegebenen Präfixlänge.
     */
    @Test
    public void testPrefixLengthToMask() {
        int mask = IpUtils.prefixLengthToMask(24);
        assertEquals("255.255.255.0", IpUtils.intToIp(mask));
    }

    /**
     * Testet das Extrahieren von Präfixlängen aus verschiedenen Stringformaten.
     */
    @Test
    public void testExtractPrefixLength() {
        assertEquals(24, IpUtils.extractPrefixLength("/24"));
        assertEquals(16, IpUtils.extractPrefixLength("abc16"));
    }
}

