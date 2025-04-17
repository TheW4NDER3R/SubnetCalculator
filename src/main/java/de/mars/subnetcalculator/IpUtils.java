package de.mars.subnetcalculator;

/**
 * Diese Hilfsklasse stellt Methoden zur Umwandlung von IP-Adressen bereit.
 */
public class IpUtils {

    /**
     * Wandelt eine IP-Adresse im Format "x.x.x.x" in eine Ganzzahl um.
     *
     * @param ip IP-Adresse als String
     * @return IP-Adresse als 32-bit Ganzzahl
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
     * Wandelt eine Ganzzahl in das Format "x.x.x.x" um.
     *
     * @param ip IP-Adresse als 32-bit Ganzzahl
     * @return IP-Adresse als String
     */
    public static String intToIp(int ip) {
        return String.format("%d.%d.%d.%d",
                (ip >>> 24) & 0xFF,
                (ip >>> 16) & 0xFF,
                (ip >>> 8) & 0xFF,
                ip & 0xFF);
    }

    /**
     * Erzeugt eine Subnetzmaske aus einer Präfixlänge (z. B. 24).
     *
     * @param prefixLength Länge des Präfixes (zwischen 0 und 32)
     * @return Subnetzmaske als Ganzzahl
     */
    public static int prefixLengthToMask(int prefixLength) {
        return prefixLength == 0 ? 0 : 0xFFFFFFFF << (32 - prefixLength);
    }

    /**
     * Extrahiert die Präfixlänge aus einem Eingabestring (z. B. "/24").
     *
     * @param input Eingabetext mit Zahl
     * @return Extrahierte Zahl als int
     */
    public static int extractPrefixLength(String input) {
        return Integer.parseInt(input.replaceAll("[^0-9]", ""));
    }
}

