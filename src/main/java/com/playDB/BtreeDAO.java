package com.playDB;


import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;


/*
* Filename: BtreeDAO.java
* Author: Jonathan Kevin Selvadurai
* Date: February,  1, 2024
* Description: 
* 
* Btree Data Access Object is tasked with overseeing a B-tree schema object, which, in turn, regulates all B-trees within the database. 
* The primary function of the Btree DAO is to facilitate access to B-tree database objects within the MapDB through command objects and 
* to instantiate the database connection
*
*/


public class BtreeDAO extends DAO {
       private Database database;
       private BtreeSchema btreeSchema;
       
       
       //Initialize database and btreeSchema
       public BtreeDAO(){
    	   database=DataSource.getInstance();
    	   btreeSchema=new BtreeSchema();    	   
       }
       
       //Insert JSON to the table 
       //Example {firstName:Jonathan LastName:Selvadurai}
       public void insertStatement(String json,String tableName) {
    	   if(database!=null ) {
	    	   if(this.isJsonValid(json)) {
	    	      btreeSchema.insertIntoTable(database, tableName, json);
	    	      System.out.println("Insert Successfully");  
	    	   }
	    	   else {
	    		   System.out.println("Invalid JSON Syntax");  
	    	   }
    	   }
    	   
    	   else {
    	    	 System.out.println("Can't insert statement because it's not connect to database ");  
    	   }	   
      }
       
      
       /*Removes Data from the B-tree table*
       * Example condition:age>11, table1*
       *Removes all records that is greater than 11 in table1*/
       public void remove(String condition,String tableName) {
    	   if(database.getDB().get(tableName)!=null)
               btreeSchema.remove(database,condition ,tableName);
    	   else
     		  System.out.println("Table Doesn't Exist"); 
       }
       
       //Display all Records in Table Format that have read value list
       //Example read(age>16,table1,[firstName,lastName,state])
       //The function will output in table format:
       // |firstNAME|lastName |state |
       // |--------------------------|
       
       public void displayReadSearch(String condition,String table,List<String> valueList) {
    	   btreeSchema.displaySearchResults(database, condition, table, valueList);
       } 
       
       
       //Output records in table format for read function with no valueList
       //Example read(age>16,table)
       public void displayReadSearch(String condition,String table) {
    	   if(database!=null)
    	     btreeSchema.linearDescendingSearch(database, condition, table);
    	   else
    		  System.out.println("Table Doesn't Exist"); 
       }
       
       
        //Used in the Expression command to copy <read statement> output to <table>
       // read(age>16,table) -> copyTable
       public void copyTable(String condition,String orginalTable,String copyTable) {
    	  btreeSchema.copyTable(database,condition, orginalTable, copyTable);
       }
       
       
       //replace function is used in the replace command
       //Example replace(age>16,{age:23},table)
       public void replace(String condition,String json,String table) {
    	   
    	   if(this.isJsonValid(json)) {
    		   btreeSchema.replace(database,condition,json,table);
    		   database.commit();
    	   }
    	   else
    		   System.out.println("Json is invalid!");
       } 
       
       /* This function is used in the import expression which is "data.json > table1" *
        *  The purpose of this function is to import a json file into the database     *
        */
       public void importJsonFile(String file,String table) throws JsonIOException, JsonSyntaxException, FileNotFoundException, IOException {
    	     btreeSchema.importJsonFile(database,file, table);
       }
       
       
       /* This function is used in the export expression "read(age>16,table1) > data.json"
       *  The purpose of this function is export btree table into a json file  
       */
       public void exportJsonReadConditionFile(String file,String condition,String table) {
           
    	   JsonArray jsonArray = new JsonArray();
            
    	    //Puts all records the meet the conditions requirments into a hashmap
    	    ConcurrentHashMap<Integer,String> tableList =btreeSchema.searchHashMap(database, condition , table) ;
		    
            Enumeration<Integer> recordKeys=tableList.keys();
            
            //Load the hashmap into an JSON array
            while(recordKeys.hasMoreElements()) {
              JsonElement jsonElement = JsonParser.parseString(tableList.get(recordKeys.nextElement()));
		      JsonObject jsonObject = jsonElement.getAsJsonObject();
		      jsonArray.add(jsonObject);
            }
            
            //Write the JSON Array into a json file 
            try (FileWriter writer = new FileWriter(file)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(jsonArray, writer);
                System.out.println("JSON array written to file: " + file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
             	   
       }
       
       
       
       /*This function is used in the export expression "table > data.json"
       * The purpose of this function is export btree table into a json file  
       */ 
       public void exportJsonFile(String file,String table) {
            JsonArray jsonArray = new JsonArray();
            
            //Gets all Records from the table and loads into a hashmap                                  
            ConcurrentHashMap<Integer,String> tableList =btreeSchema.getAllRecords(database, table) ;
		    
            Enumeration<Integer> recordKeys=tableList.keys();
            
            //Load the hashmap into an JSON array
            while(recordKeys.hasMoreElements()) {
              JsonElement jsonElement = JsonParser.parseString(tableList.get(recordKeys.nextElement()));
		      JsonObject jsonObject = jsonElement.getAsJsonObject();
		      jsonArray.add(jsonObject);
            }
            
            //Write the JSON Array into a json file 
            try (FileWriter writer = new FileWriter(file)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(jsonArray, writer);
                System.out.println("JSON array written to file: " + file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            
    	   
       }
       
       
       
       //Outputs all the records in the table in JSON format
       public void outputTable(String tableName) {
    	   btreeSchema.readAllTable(database,tableName);
       }
       
       
       
  
       //Validates JSON
       public boolean isJsonValid(String json) {
    	    try {
    	        JsonParser.parseString(json);
    	    } catch (JsonSyntaxException e) {
    	        return false;
    	    }
    	    return true;
    	}

	



}
