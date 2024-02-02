package com.playDB;

import com.google.gson.*;

import java.util.Set;

public class JsonTableFormatter {

    public static void formatJsonTable(String jsonString) {
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        if (jsonArray.size() > 0) {
            // Print headers
            Set<String> headers = jsonArray.get(0).getAsJsonObject().keySet();
            printTableRow(headers);

            // Print data rows
            for (JsonElement jsonElement : jsonArray) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                Set<String> fieldNames = jsonObject.keySet();
                printTableRow(fieldNames, jsonObject);
            }
        }
    }

    private static void printTableRow(Set<String> fieldNames) {
        // Print header row
        System.out.println("--------------------------------------------");
        for (String fieldName : fieldNames) {
            System.out.printf("| %-15s ", fieldName);
        }
        System.out.println("|");
        System.out.println("--------------------------------------------");
    }

    private static void printTableRow(Set<String> fieldNames, JsonObject jsonObject) {
        // Print data row
        for (String fieldName : fieldNames) {
            String value = jsonObject.get(fieldName).getAsString();
            System.out.printf("| %-15s ", value);
        }
        System.out.println("|");
        System.out.println("--------------------------------------------");
    }

    public static void main(String[] args) {
        // Example JSON string with multiple records
        String jsonString = "[{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}, " +
                            "{\"name\":\"Alice\",\"age\":25,\"city\":\"London\"}]";

        // Format JSON table
        formatJsonTable(jsonString);
    }
}
