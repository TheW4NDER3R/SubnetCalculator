package de.mars.subnetcalculator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Diese Klasse verwaltet das Speichern von Subnetzberechnungen in einer JSON-Datei.
 */
public class JsonStorage {

    private static final String FILE_NAME = "calculations.json";

    /**
     * Speichert eine neue Subnetzberechnung in der JSON-Datei.
     *
     * @param inputIp       Die eingegebene IP-Adresse
     * @param prefixLength  Die eingegebene Präfixlänge
     * @param subnetCount   Die Anzahl der Subnetze (0 bei Einzelberechnung)
     * @param resultText    Der Ergebnistext
     */
    public static void saveCalculation(String inputIp, int prefixLength, int subnetCount, String resultText) {
        try {
            JSONArray allCalculations;

            if (Files.exists(Paths.get(FILE_NAME))) {
                String content = new String(Files.readAllBytes(Paths.get(FILE_NAME)));
                allCalculations = new JSONArray(content);
            } else {
                allCalculations = new JSONArray();
            }

            JSONObject calc = new JSONObject();
            calc.put("ip", inputIp);
            calc.put("prefix", prefixLength);
            calc.put("subnets", subnetCount);
            calc.put("result", resultText);
            calc.put("timestamp", System.currentTimeMillis());

            allCalculations.put(calc);

            try (FileWriter file = new FileWriter(FILE_NAME)) {
                file.write(allCalculations.toString(2));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Lädt alle gespeicherten Berechnungen aus der JSON-Datei.
     *
     * @return Zusammengefasste Ergebnisse als String zur Anzeige.
     */
    public static String loadAllCalculations() {
        StringBuilder sb = new StringBuilder();
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                return "Keine gespeicherten Berechnungen gefunden.";
            }

            String content = new String(Files.readAllBytes(Paths.get(FILE_NAME)));
            JSONArray allCalculations = new JSONArray(content);

            for (int i = 0; i < allCalculations.length(); i++) {
                JSONObject obj = allCalculations.getJSONObject(i);
                sb.append("=== Berechnung ").append(i + 1).append(" ===\n");
                sb.append("IP: ").append(obj.getString("ip")).append("\n");
                sb.append("Präfix: /").append(obj.getInt("prefix")).append("\n");
                sb.append("Subnets: ").append(obj.getInt("subnets")).append("\n");
                sb.append("Zeitstempel: ").append(new java.util.Date(obj.getLong("timestamp"))).append("\n");
                sb.append(obj.getString("result")).append("\n\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Fehler beim Laden der Berechnungen.";
        }

        return sb.toString();
    }

}
