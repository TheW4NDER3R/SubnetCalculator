package de.mars.subnetcalculator;

/**
 * Diese Klasse enthält die Logik zur Berechnung von Subnetzen.
 */
public class SubnetCalculator {

    /**
     * Berechnet ein einzelnes Subnetz und gibt die Informationen als Text zurück.
     *
     * @param ip           IP-Adresse als String
     * @param prefixLength Länge des Präfixes (z. B. 24)
     * @return Ergebnistext zur Anzeige
     */
    public static String calculateSingleSubnet(String ip, int prefixLength) {
        int ipInt = IpUtils.ipToInt(ip);
        int subnetMask = IpUtils.prefixLengthToMask(prefixLength);
        int network = ipInt & subnetMask;
        int broadcast = network | ~subnetMask;

        StringBuilder sb = new StringBuilder();
        sb.append("=== Ergebnis ===\n");
        sb.append(String.format("%-20s %s%n", "Netzadresse:", IpUtils.intToIp(network)));
        sb.append(String.format("%-20s %s%n", "Broadcast:", IpUtils.intToIp(broadcast)));
        sb.append(String.format("%-20s %s%n", "Erster Host:", IpUtils.intToIp(network + 1)));
        sb.append(String.format("%-20s %s%n", "Letzter Host:", IpUtils.intToIp(broadcast - 1)));
        sb.append(String.format("%-20s %s%n", "Subnetzmaske:", IpUtils.intToIp(subnetMask)));
        sb.append(String.format("%-20s %d%n", "Anzahl Hosts:", (1L << (32 - prefixLength)) - 2));
        sb.append(String.format("%-20s /%d%n", "CIDR-Notation:", prefixLength));
        return sb.toString();
    }

    /**
     * Berechnet mehrere Subnetze und gibt die Informationen als Text zurück.
     *
     * @param ip           IP-Adresse als String
     * @param prefixLength Länge des Präfixes (z. B. 24)
     * @param subnetCount  Anzahl der gewünschten Subnetze
     * @return Ergebnistext zur Anzeige
     */
    public static String calculateMultipleSubnets(String ip, int prefixLength, int subnetCount) {
        int additionalBits = (int) Math.ceil(Math.log(subnetCount) / Math.log(2));
        int newPrefix = prefixLength + additionalBits;
        int totalSubnets = (int) Math.pow(2, additionalBits);
        int ipInt = IpUtils.ipToInt(ip);
        int subnetMask = IpUtils.prefixLengthToMask(newPrefix);

        StringBuilder sb = new StringBuilder();
        sb.append("=== Subnetze ===\n");
        sb.append(String.format("Neue Subnetzmaske: %s (/%d)\n\n", IpUtils.intToIp(subnetMask), newPrefix));

        for (int i = 0; i < totalSubnets && i < subnetCount; i++) {
            int network = (ipInt & IpUtils.prefixLengthToMask(prefixLength)) | (i << (32 - newPrefix));
            int broadcast = network | ~subnetMask;

            sb.append(String.format("Subnetz %d:\n", i + 1));
            sb.append(String.format("  Netzadresse:  %s%n", IpUtils.intToIp(network)));
            sb.append(String.format("  Broadcast:    %s%n", IpUtils.intToIp(broadcast)));
            sb.append(String.format("  Hosts:        %s - %s%n", IpUtils.intToIp(network + 1), IpUtils.intToIp(broadcast - 1)));
            sb.append("\n");
        }
        return sb.toString();
    }
}

