package de.mars.subnetcalculator;

/**
 * Hilfsklasse mit statischen Methoden zur Verarbeitung und Umwandlung von IP-Adressen.
 * Unterstützt die Umrechnung zwischen String- und Ganzzahlformat, sowie das Ableiten von Subnetzmasken.
 */
public class IpUtils {

    /**
     * Wandelt eine IP-Adresse im Format "x.x.x.x" in eine 32-Bit-Ganzzahl um.
     *
     * @param ip IP-Adresse als String (z.B. "192.168.0.1")
     * @return Die entsprechende 32-Bit-Ganzzahl-Repräsentation der IP-Adresse
     */
    public static int ipToInt(String ip) {
        String[] parts = ip.split("\\.");
        int result = 0;
        for (String part : parts) {
            result = (result << 8) | Integer.parseInt(part);
        }
        return result;
    }

    /**
     * Wandelt eine 32-Bit-Ganzzahl in das IP-Format "x.x.x.x" um.
     *
     * @param ip Die IP-Adresse als 32-Bit-Ganzzahl
     * @return Die entsprechende IP-Adresse als String
     */
    public static String intToIp(int ip) {
        return String.format("%d.%d.%d.%d",
                (ip >>> 24) & 0xFF,
                (ip >>> 16) & 0xFF,
                (ip >>> 8) & 0xFF,
                ip & 0xFF);
    }

    /**
     * Erzeugt eine Subnetzmaske im Ganzzahlformat basierend auf einer gegebenen Präfixlänge.
     *
     * @param prefixLength Die Präfixlänge (zwischen 0 und 32)
     * @return Die entsprechende Subnetzmaske als 32-Bit-Ganzzahl
     */
    public static int prefixLengthToMask(int prefixLength) {
        return prefixLength == 0 ? 0 : 0xFFFFFFFF << (32 - prefixLength);
    }

    /**
     * Extrahiert die Präfixlänge (z. B. 24) aus einem Eingabestring wie "/24" oder "24".
     *
     * @param input Der Eingabestring, der eine Zahl enthält
     * @return Die extrahierte Präfixlänge als int
     */
    public static int extractPrefixLength(String input) {
        return Integer.parseInt(input.replaceAll("[^0-9]", ""));
    }
}

