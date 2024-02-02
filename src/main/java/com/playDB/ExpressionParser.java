package com.playDB;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
* **********************************************************************
* Filename: ExpressionParse.java
* Author: Jonathan Kevin Selvadurai
* Date: February,2,2024
* Description: Validates and Parses Expressions
*  
* 
* **********************************************************************/


public class ExpressionParser {
    
	// data.json > table
    public static String  importExpression = "([^\\s]+\\.json)\\s*>\\s*([\\w.]+)";
    
    // table > data.json
    public static String  exportExpression = "([\\w./]+)\\s*>\\s*([\\w./]+\\.json)";
    
    //read(age>23,table) > table
    public static String readStatementToTable="^read\\(([^()]*(\\([^()]*\\)[^()]*)*)\\)\\s*>\\s*([\\w.]+)$";
    
    //read(age>23,table) > data.json
    public static String readExportJson="^read\\(([^()]*(\\([^()]*\\)[^()]*)*)\\)\\s*>\\s*([\\w./]+\\.json)";
    
    
    //Validates and Parses "read(age>23,table) > data.json"
    public static boolean isValidReadExportJsonValid(String input) {
		  
	       Pattern pattern = Pattern.compile(readExportJson);
	       Matcher matcher = pattern.matcher(input);
	       return matcher.matches();
	  } 
    
    //Validates and Parses "read(age>23,table) > table"
    public static boolean isValidReadToTableValid(String input) {
		  
	       Pattern pattern = Pattern.compile(readStatementToTable);
	       Matcher matcher = pattern.matcher(input);
	       return matcher.matches();
	  } 
	
    //Validates and Parses "data.json > table"
	 public static boolean isValidImportExpressionValid(String input) {
		  
	       Pattern pattern = Pattern.compile(importExpression);
	       Matcher matcher = pattern.matcher(input);
	       return matcher.matches();
	 }
	 //Validates and Parses table > data.json
	  public static boolean isValidExportExpressionValid(String input) {
		  
	       Pattern pattern = Pattern.compile(exportExpression);
	       Matcher matcher = pattern.matcher(input);
	       return matcher.matches();
	  }
	  
	  public static String parseFile( String statement) {
	        Pattern pattern = Pattern.compile(importExpression);
	        Matcher matcher = pattern.matcher(statement);

	        if (matcher.matches()) {
	            String jsonFile = matcher.group(1);

	            return jsonFile.trim();

	        } 
	        
	        return "";
	    }
	  
	  //Return Json file. Example data.json > table return "data.json". 
	  public static String parseTable( String statement) {
	        Pattern pattern = Pattern.compile(importExpression);
	        Matcher matcher = pattern.matcher(statement);

	        if (matcher.matches()) {
	            String table = matcher.group(2);
	            return table.trim();

	        } 
	        
	        return "";
	    }
	   
	  //Return Json file. Example table > data.json  return "data.json". 
	  public static String ExportParseFile( String statement) {
	        Pattern pattern = Pattern.compile(exportExpression);
	        Matcher matcher = pattern.matcher(statement);

	        if (matcher.matches()) {
	            String jsonFile = matcher.group(2);

	            return jsonFile.trim();

	        } 
	        
	        return "";
	    }
	  
	   //Return Json file. Example table > data.json  return table. 
	  public static String ExportParseTable( String statement) {
	        Pattern pattern = Pattern.compile(exportExpression);
	        Matcher matcher = pattern.matcher(statement);

	        if (matcher.matches()) {
	            String table = matcher.group(1);
	            return table.trim();

	        } 
	        
	        return "";
	    }
	    
	  //read(age>23,table) ret
	  static String getReadTable(String statement) {
		    Pattern pattern = Pattern.compile(readStatementToTable);
	        Matcher matcher = pattern.matcher(statement);

	        if (matcher.matches()) {
	            String parameters = matcher.group(1).trim();
	            String tableName = matcher.group(3).trim();

	            return tableName.trim();
	            
	            //System.out.println("Parameters: " + parameters);
	           // System.out.println("Table Name: " + tableName);
	        } else {
	            System.out.println("Statement does not match the pattern: " + statement);
	        }
			return "";
	  
	  }
	  
	  // read(age>23,table) returns [age>23,table] in an array
	  static String[] getReadParameters(String statement) {
		    Pattern pattern = Pattern.compile(readStatementToTable);
	        Matcher matcher = pattern.matcher(statement);

	        if (matcher.matches()) {
	            String parameters = matcher.group(1).trim();
	            return parameters.split(",");

	        } else {
	            System.out.println("Statement does not match the pattern: " + statement);
	        }
			return null;
	  
	  }
	     
	     
	

}
