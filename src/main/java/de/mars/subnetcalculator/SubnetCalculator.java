package de.mars.subnetcalculator;

/**
 * Diese Klasse enthält die zentrale Logik zur Berechnung von Subnetzen.
 * Sie unterstützt sowohl die Analyse eines einzelnen Subnetzes als auch die
 * automatische Aufteilung eines Netzwerks in mehrere Subnetze.
 */
public class SubnetCalculator {

    /**
     * Berechnet die Netzadresse, Broadcast-Adresse, ersten und letzten Host sowie
     * weitere relevante Informationen für ein einzelnes Subnetz.
     *
     * @param ip           Die IP-Adresse im Format "x.x.x.x"
     * @param prefixLength Die Präfixlänge (z. B. 24)
     * @return Ein formatierter Ergebnistext mit allen berechneten Subnetzinformationen
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
     * Teilt ein gegebenes Netzwerk in mehrere Subnetze auf und berechnet für jedes Subnetz
     * die Netzadresse, Broadcast-Adresse sowie den Hostbereich.
     *
     * @param ip           Die Ausgangs-IP-Adresse im Format "x.x.x.x"
     * @param prefixLength Die ursprüngliche Präfixlänge (z. B. 24)
     * @param subnetCount  Die gewünschte Anzahl an Subnetzen
     * @return Ein formatierter Text mit Informationen zu den berechneten Subnetzen
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

