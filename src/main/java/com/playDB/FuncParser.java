package com.playDB;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
* **********************************************************************
* Filename: FuncParser.java
* Author: Jonathan Kevin Selvadurai
* Date: February,2,2024
* Description: Validates and Parses Functions
*  
* 
* **********************************************************************/



public class FuncParser {
	
	
	 public static final String INSERT_PATTERN = "^insert\\(\\{.*?\\},\\w+\\)$";
	 public static final String READ_PATTERN = "^read\\(\\s*\\(([^)]*)\\)\\s*,\\s*(\"[^\"]*\"|[^\\s,]+)\\s*\\)$";
	 public static final String REMOVE_PATTERN = "^replace\\(\\s*(.*)\\s*,\\{.*?\\},\\w+\\)$";

	 
	/*******INSERT PATTERN*****************************************************************************/

     //Validates insert function example insert({name:"Bob"},table1)
     public static boolean isValidInsertStatement(String input) {
         Pattern pattern = Pattern.compile(INSERT_PATTERN);
         Matcher matcher = pattern.matcher(input);
         return matcher.matches();
     }
     
     //insert({name:"Bob",table1) function and return JSON 
     //example returns {name:"Bob"}
     public static String getInsertJSONToken(String input) {
		 if(isValidInsertStatement(input)) {
			  int indexStart =input.indexOf("{");
		      int endStart =input.indexOf("}")+1;
		      
		      return input.substring(indexStart, endStart);
		 }
		 else {
			  return "";
		 }
    }
     
   //insert({name:"Bob",table1) function and return Tanle 
   //example returns table
   public static String getInsertTableToken(String input) {
		 if(isValidInsertStatement(input)) {
            int lastCommaIndex = input.lastIndexOf(',');
            String tableName = input.substring(lastCommaIndex + 1, input.length() - 1).trim();
            return tableName;
		 }    

		  return "";
   }  
   
   
   /*******************read()********************************************/
   /*  read()  function */
   
   //Parses and returns condition in read function
   //Example read(age>23,table) -> returns "age>23"
   public static String parseConditions(String readFunction) {
	    int startIndex = readFunction.indexOf("(")+1;
	    int endIndex = readFunction.lastIndexOf(")");

	    if (startIndex != -1 && endIndex != -1) {
	          String[] tokens=readFunction.substring(startIndex, endIndex + 1).trim().split(",");
	          return tokens[0];
	    } else {
	        throw new IllegalArgumentException("Invalid read function: " + readFunction);
	    }
	}

   //Parses and returns table in read function
   //Example read(age>23,table) -> return table
   public static String parseTable(String readFunction) {
	   int startIndex = readFunction.indexOf("(")+1;
	    int endIndex = readFunction.lastIndexOf(")")-1;

	    if (startIndex != -1 && endIndex != -1) {
	          String[] tokens=readFunction.substring(startIndex, endIndex + 1).trim().split(",");
	          return tokens[1];
	    } else {
	        throw new IllegalArgumentException("Invalid read function: " + readFunction);
	    }
   }
   
   
   //Validates read function example read(age>23,table)
   public static boolean isValidReadFunction(String input) {
       String readFunctionPattern = "^read\\(\\s*(.*)\\s*,\\s*\\w+\\)$";

       Pattern pattern = Pattern.compile(readFunctionPattern);
       Matcher matcher = pattern.matcher(input);

       if (matcher.matches()) {
           // If the basic structure matches, extract and validate the condition
           String condition = matcher.group(1);
           return isValidCondition(condition);
       }

       return false;
   }
   
   private static boolean isValidCondition(String condition) {
       // Your condition validation logic goes here
       // For simplicity, let's assume any non-empty string is valid
       return !condition.trim().isEmpty();
   }

   
   
   
   //Extract variables in read function and return in an array
   //Example read(age>23 && person=="Bob") -> returns [age,person]
   public static ArrayList<String> extractVariables(String condition) {
       ArrayList<String> variables = new ArrayList<>();

       // Regular expression to find variable names
       Pattern pattern = Pattern.compile("\\b([a-zA-Z_]+)\\b(?=(?:(?:[^'\"]*['\"]){2})*[^'\"]*$)");
       Matcher matcher = pattern.matcher(condition);

       // Iterate through matches and add to the list
       while (matcher.find()) {
           variables.add(matcher.group(1));
       }

       return variables;
   }
   
   //Validates function read(age>33,table,[person,age,city])
   public static Boolean isValidReadParamFunction(String input) {
       String readFunctionPattern = "read\\(([^,]+),([^,]+),\\[([^\\]]*)\\]\\)";
       
       
       Pattern pattern = Pattern.compile(readFunctionPattern);
       Matcher matcher = pattern.matcher(input);

       if (matcher.matches()) {
           // If the basic structure matches, extract and validate the condition
           String condition = matcher.group(1);
           return isValidCondition(condition);
       }

       return false;
       
   } 

   /**************remove function****************************************************************************/
   //Validates remove function, example remove(age>22,table) 
   public static boolean isValidRemoveFunction(String input) {
       // Regular expression to match a basic remove function pattern
       String readFunctionPattern = "^remove\\(\\s*(.*)\\s*,\\s*\\w+\\)$";
  
       Pattern pattern = Pattern.compile(readFunctionPattern);
       Matcher matcher = pattern.matcher(input);

       if (matcher.matches()) {
           // If the basic structure matches, extract and validate the condition
           String condition = matcher.group(1);
           return isValidCondition(condition);
       }

       return false;
   }
   
   
   /***************Replace Function************************************************************************************************/
   //Validates replace function, example replace(age>22,{age:33},table) 
   public static boolean isValidReplaceFunction(String input) {
       // Regular expression to match a basic remove function pattern
       //String readFunctionPattern = "^replace\\(\\s*(.*)\\s*,\\{.*?\\},\\w+\\)$";

	   
       Pattern pattern = Pattern.compile(REMOVE_PATTERN);
       Matcher matcher = pattern.matcher(input);

       if (matcher.matches()) {
           // If the basic structure matches, extract and validate the condition
           String condition = matcher.group(1);
           return isValidCondition(condition);
       }

       return false;
   }
   
   public static String getParametersReplace( String function) {
	   Pattern pattern = Pattern.compile(REMOVE_PATTERN);
       Matcher matcher = pattern.matcher(function);

       if (matcher.matches()) {
           String parameters = matcher.group(1).trim();
           return parameters;
       } 
       
       return null;
   }
   
   
   
}


