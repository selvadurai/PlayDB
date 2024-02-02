package com.playDB;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
* **********************************************************************
* Filename: ReadArrayFunctionParser.java
* Author: Jonathan Kevin Selvadurai
* Date: February,2,2024
* Description: Validates and Parses read(age>23,table,[person,city,age])
* **********************************************************************/



public class ReadArrayFunctionParser {
    public static void main(String[] args) {
        String statement = "read((x==1)||y>32,table,[x,y,z,a])";

        // Parse the statement
        ReadFunctionParams params = parseReadFunction(statement);

        // Display parsed parameters
        System.out.println("Condition: " + params.getCondition());
        System.out.println("Table: " + params.getTable());
        System.out.println("Array: " + params.getArray());
    }

     /*Returns ReadFunctionParams object and users can the condition,table, values list 
      from the ReadFunctionParams object
      example ReadFunctionParams.getCondition()*/
    public static ReadFunctionParams parseReadFunction(String statement) {
        String regex = "read\\(([^,]+),([^,]+),\\[([^\\]]*)\\]\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(statement);

        if (matcher.matches()) {
            String condition = matcher.group(1);
            String table = matcher.group(2);
            String array = matcher.group(3);

            // Parse array elements
            List<String> arrayElements = parseArray(array);

            return new ReadFunctionParams(condition, table, arrayElements);
        }

        throw new IllegalArgumentException("Invalid read function statement: " + statement);
    }

    public static List<String> parseArray(String array) {
        String[] elements = array.split(",");
        List<String> arrayElements = new ArrayList<>();

        for (String element : elements) {
            arrayElements.add(element.trim());
        }

        return arrayElements;
    }
}

class ReadFunctionParams {
    private String condition;
    private String table;
    private List<String> array;

    public ReadFunctionParams(String condition, String table, List<String> array) {
        this.condition = condition;
        this.table = table;
        this.array = array;
    }

    public String getCondition() {
        return condition;
    }

    public String getTable() {
        return table;
    }

    public List<String> getArray() {
        return array;
    }
}

