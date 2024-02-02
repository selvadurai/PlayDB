package com.playDB;

import org.mapdb.DB;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* **********************************************************************
* Filename: ReadFunctionParser.java
* Author: Jonathan Kevin Selvadurai
* Date: February,2,2024
* Description: Parses and returns table and condition from read statement
* 
* **********************************************************************/




public class ReadFunctionParser {

    private static final String READ_PATTERN = "^read\\(\\s*\\(([^)]*)\\)\\s*,\\s*(\"[^\"]*\"|[^\\s,]+)\\s*\\)$";

    public static void main(String[] args) {
        // Test case
        String readFunction = "read((x==\"hello\" && x>7) && (y==\"Science\"),table)";

        // Parse conditions
        String conditions = parseConditions(readFunction);
        System.out.println("Conditions: " + conditions);

        // Parse table
        String table = parseTable(readFunction);
        System.out.println("Table: " + table);
    }

    public static String parseConditions(String readFunction) {
        Pattern pattern = Pattern.compile(READ_PATTERN);
        Matcher matcher = pattern.matcher(readFunction);

        if (matcher.matches()) {
            return matcher.group(1);
        } else {
            throw new IllegalArgumentException("Invalid read function: " + readFunction);
        }
    }

    public static String parseTable(String readFunction) {
        Pattern pattern = Pattern.compile(READ_PATTERN);
        Matcher matcher = pattern.matcher(readFunction);

        if (matcher.matches()) {
            return matcher.group(2);
        } else {
            throw new IllegalArgumentException("Invalid read function: " + readFunction);
        }
    }
}
