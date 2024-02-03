package com.playDB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;

import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.MapContext;
import org.mapdb.BTreeMap;
import org.mapdb.Serializer;

import com.google.common.io.Files;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/*
* Filename: BtreeSchema.java
* Author: Jonathan Kevin Selvadurai
* Date: February,  1, 2024
* Description: 
* The Btree Schema object is responsible for creating, updating, and removing all tables and records that represent 
* a B-tree in the database. It directly accesses the MapDB B-tree map to perform these operations.
* 
* 
*/

public class BtreeSchema extends Schema {
	
	private BTreeMap<Integer, String> map;
	
	public BtreeSchema() {
	}
	
	
	//Creates b-tree table
	@Override
	public void createTable(Database db, String tableName) {
		try{
		map= db
			.getDB()
			.treeMap(tableName,Serializer.INTEGER,Serializer.STRING)
            .create();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	//uses a btree table
	@Override
	public void useTable(Database db,String tableName) {
		
	     try {	
		    map= db
			.getDB()
			.treeMap(tableName,Serializer.INTEGER,Serializer.STRING)
			.createOrOpen();
		}catch(Exception e) {
			System.out.println(e);
		}    
	}
	
    //Inserts into Btree table
	@Override
	public void insertIntoTable(Database db,  String tableName,int id, String Json) {
		
		try{	
		    this.useTable(db,tableName);
			map.put(id, Json);
			db.commit();
		 }catch(Exception e) {
			 System.out.println(e);
		  }
	
   }
	
	//Remove record from Btree table
	@Override
	public void removeRecord(Database db,  String tableName,int id) {
		
		try{	
		    this.useTable(db,tableName);
			map.remove(id);
			db.commit();
		 }catch(Exception e) {
			 System.out.println(e);
		  }
	
   }
	//Remove record from Btree table by id and Json statement
	@Override
	public void removeRecord(Database db,  String tableName,int id,String json) {
		
		try{	
		    this.useTable(db,tableName);
			map.remove(id, json);
			db.commit();
		 }catch(Exception e) {
			 System.out.println(e);
		  }
	
   }
	
	//Insert record into btree table
	@Override
	public void insertIntoTable(Database db, String tableName, String Json) {
	   try{	
		    this.useTable(db,tableName);
			map.put(map.getSize()+1,Json);
			db.commit();
		}catch(Exception e) {
			System.out.println(e);
		 }
	} 	
	
	//Insert record into btree table
	public void insertRecord(Database db, String tableName, String Json) {
	   try{	
		    this.useTable(db,tableName);
			map.put(map.getSize()+1,Json);
		}catch(Exception e) {
			System.out.println(e);
		 }
	} 	
	
	//Output all records from table
	@Override
	public void readAllTable(Database db, String tableName) {
	
	   this.useTable(db,tableName);
	  
	   for(int i:map.descendingKeySet()) {
		   System.out.println(map.get(i));
	   }
	   
	   
		
	}
	// removes record from b-tree table
	public void remove(Database db,String condition ,String tableName){
		
		 ConcurrentHashMap<Integer,String>  searchResults=this.searchHashMap(db,condition ,tableName);
		
		 Enumeration<Integer> hashKeys=searchResults.keys();
		 
		 while(hashKeys.hasMoreElements()) {
			 map.remove(hashKeys.nextElement());
		 }
		 
		 db.commit();
		 
		 System.out.println("Successfully Removed");
		 
	}
	
	
	//Outputs all records that meet the condition requriments
	public void displaySearchResults(Database db,String condition ,String tableName,List<String>fieldNamesArray) {
		 ConcurrentHashMap<Integer,String>  searchResults=this.searchHashMap(db,condition ,tableName);
		   Set<String> fieldNamesSet = new HashSet<>(fieldNamesArray);
	       DynamicJsonTableFormatter.printHeaders(fieldNamesSet);
	       
	       
	       Enumeration<Integer> foundSearchKeys=searchResults.keys();
			 
		   	try { 
				while(foundSearchKeys.hasMoreElements()) {
					 DynamicJsonTableFormatter.printJsonObject(map.get(foundSearchKeys.nextElement()), fieldNamesSet);
				 }
				
		   	}catch(Exception e) {
		   		System.out.println(e);
		   	}

		  
			  
		
	}
	
	//Copies original table into a copy table
	public void copyTable(Database db,String condition,String orginalTable,String copyTable) {
		this.useTable(db,orginalTable);
		ConcurrentHashMap<Integer,String> copyList=this.searchHashMap(db,condition,orginalTable);
		this.useTable(db,copyTable);
		
		
	     Enumeration<Integer> i=copyList.keys();
			 
		   	try { 
				while(i.hasMoreElements()) {
					 map.put(map.getSize()+1,copyList.get(i.nextElement()));
				 }
				
				
		    System.out.println("Succesfully Copied Table");		
				
		   	}catch(Exception e) {
		   		System.out.println(e);
		   	}

		
	}
	
	
	//Stores all condition statements in the table into a hashmap
	public ConcurrentHashMap<Integer,String> searchHashMap(Database db,String condition ,String tableName){
		 try { 
				 this.useTable(db,tableName);
				 
				 //Validates condition 
			     JexlConditionValidator validator = new JexlConditionValidator(condition);
			     JexlContext context = new MapContext();
			     ConcurrentHashMap<Integer,String> searchHashMap= new   ConcurrentHashMap<Integer,String> ();
			     String jsonString="";
			 	
			     /*Extract variable names in the condition
			      *
			      *Example: if age>16 && name==="Bob"
			      *Extract age and name  
			      * 
			      */
		         Set<String> varList=new HashSet<>(FuncParser.extractVariables(condition));
		         
		         
				   for(int i:map.descendingKeySet()) {
					    //Get record from table
					    jsonString=map.get(i);
					   
					    //Convert record to JSON object
					    JsonElement jsonElement = JsonParser.parseString(jsonString);
					    JsonObject jsonObject = jsonElement.getAsJsonObject();
					    
					   
					    for(String var:varList) {
					    	
					       //Stores the variables into a context map
					    	
					       /*Get variable value in the JSON object
					         Example name:"Tom"
					         JsonObject gets Tom                             */
					       if(jsonObject.has(var)) {
					    	   context.set(var, jsonObject.get(var).getAsString());
					       }
					       else {
					    	   /*If JSON property has no property then assign the property
					    	    a empty string value*/
					    	  
					    	   context.set(var,"");
		
					       }
					    }
					    
					    
					    /*Context map is meets condition then store it 
					      in searchHashMap */
					    if (validator.validateCondition(context)) {
					    	searchHashMap.put(i, jsonString);
					    }
					       
				   }
		         
				
				
				  return searchHashMap;
				  
		 }
		 catch(Exception e) {
			System.out.println("Invalid Read Statement");
		    System.out.println(e);
		 }
		return null;			  
	}
	
	//Gets all Records from  tables and stores it in a hashmap
	public ConcurrentHashMap<Integer,String> getAllRecords(Database db,String tableName){
		 try { 
				 this.useTable(db,tableName);
			     ConcurrentHashMap<Integer,String> records= new   ConcurrentHashMap<Integer,String> ();
		         
				   for(int i:map.descendingKeySet()) {
					    records.put(i,map.get(i));
				   }
		         
				
				  return records;
				  
		 }
		 catch(Exception e) {
		    System.out.println(e);
		 }
		return null;			  
	}
	
	
	
	//Replaces record in database that meets condition
	public void replace(Database db,String condition,String json,String table) {
		this.useTable(db, table);
		ConcurrentHashMap<Integer,String> foundRecords= this.searchHashMap(db, condition, table);
		
		if(!foundRecords.isEmpty()) {
			 Enumeration<Integer> i=foundRecords.keys();
			
			 while(i.hasMoreElements()) {
				map.replace(i.nextElement() , json);
			 }
			 
		}
		else {
			System.out.println("No Records found to replace!");
		}
	}
	
	
	//Imports JSON file into database
	public void importJsonFile(Database db,String file, String table) throws JsonIOException, JsonSyntaxException, FileNotFoundException, IOException {
		 Path path = Paths.get(file);
		 this.useTable(db,table);
         
		 
	        try (FileReader reader = new FileReader(path.toFile())) {
	            JsonElement tree = JsonParser.parseReader(reader).getAsJsonArray(); 
	            
                if (tree.isJsonArray()) {
	                JsonArray array = tree.getAsJsonArray();

	                for (JsonElement element : array) {
	                    if (element.isJsonObject()) {
	                        JsonObject record = element.getAsJsonObject();
	                           map.put(map.getSize()+1,record.toString());
	                    }
	                }
	                
	                db.commit();
	                
	            } else {
	                System.out.println("File does not contain a JSON array.");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	  }     
	
	
		

	
	
	
	//Outputs record that meets condition in tabular format
	public void linearDescendingSearch(Database db,String condition ,String tableName) {
		  
	try {
		
		   this.useTable(db,tableName);
	       JexlConditionValidator validator = new JexlConditionValidator(condition);
	       JexlContext context = new MapContext();
	       LinkedList<String> foundList = new LinkedList<>();
           String jsonString="";
		    

			
           Set<String> varList=new HashSet<>(FuncParser.extractVariables(condition));
           
           
		   for(int i:map.descendingKeySet()) {
			    			    jsonString=map.get(i);
			   
			    JsonElement jsonElement = JsonParser.parseString(jsonString);
			    JsonObject jsonObject = jsonElement.getAsJsonObject();
			    
			   
			    for(String var:varList) {
			       if(jsonObject.has(var)) {
			    	   context.set(var, jsonObject.get(var).getAsString());
			       }
			       else {
			    	   context.set(var,"");

			       }
			    }
			    
			    
			    
			    if (validator.validateCondition(context)) {
				        foundList.add(jsonString);
			    }
			       
		   }
		   
		   if(!foundList.isEmpty()) {
			   
		        Set<String> fieldNames = varList;
		        DynamicJsonTableFormatter.printHeaders(fieldNames);

		      try {  
		        // Print data in tabular format
		        for (String found : foundList) {
		        	DynamicJsonTableFormatter.printJsonObject(found, fieldNames);
		        }
		      }catch(Exception e) {
		    	  
		      }
			  
			   
		   }
		   else {
			   System.out.println("Record or Records not found");

		   }
		   
	}catch(Exception e) {
		System.out.println("Invalid Read Statement");
		System.out.println(e);
	}	   
	
		   
			
	}
		
	//Stores all records in table in hashmap
	public  ConcurrentHashMap<Integer,String>  tableList(Database db, String tableName) {
		
		   this.useTable(db,tableName);
			
		   Iterator<Integer> itr = map.getKeys().iterator();
			   while(  itr.hasNext()) {
				  int num=itr.next();
				  System.out.println(num+" "+map.get(num) );
			   }
			   
			   
			   return null;
			
		}

	@Override
	public void close() {
       map.close();		
	}
	

}
