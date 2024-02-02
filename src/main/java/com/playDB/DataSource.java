package com.playDB;


/*
* **********************************************************************
* Filename: DataSource.java
* Author: Jonathan Kevin Selvadurai
* Date: February,2,2024
* Description: The DataSource object uses the singleton pattern, meaning the object can only be instantiated once. 
* If the user wishes to connect to another database, the user will have to change the reference in the instance.
* 
* **********************************************************************/

public class DataSource{
	
  public static Database dbInstance;
	
  //Connect to Database disk or in-memory
  public static Database getConnection(String diskName) {
	   if(diskName.equals("memory".trim())) {
		   System.out.println("Connected to Memory database");
		   return connectToMemoryDatabase();
	   }
	   else {
		   System.out.println("Connected to Disk Database");
		    return connectDiskDB(diskName);
	   }
	   //return dbInstance;
  }  
  
  //Get database instance
  public static Database getInstance() {
	  return dbInstance;
  }
  
  //Display all objects or tables in the database in tabular format.
  public static void displayAllTables() {
	  
	  if(dbInstance!=null) {
    	  if(!dbInstance.isClosed()) {
    		  
    	    System.out.println("------------------------");
    	    System.out.printf("%13s ", "TABLES LIST");
    	    System.out.println();
    		for( String table: dbInstance.getAllObjectNames()) {
    		    System.out.format("%13s",table);
            	System.out.println(); 
    		}
    		System.out.println();
    	    System.out.println("------------------------");
 		  		
    	  }
     }
	  else {
		  System.out.println("Not connected to Database!");
	  } 
	  
  } 
  
  //Connect to Disk Database
  public static Database connectDiskDB(String diskName) {
	  
	  try {  
	  
	      //Safely close Current Database
		  if(dbInstance!=null) {
	    	  if(!dbInstance.isClosed()) {
	    		 dbInstance.close();
	    	  }
	     }
		  
		 //Safely connect to new Database
		  dbInstance= new  DiskDatabase
                     .DatabaseConnection()
                     .getDisk(diskName)
                     .connect();
		 
		  return dbInstance; 
	 
	  } catch(Exception e) {
			 System.out.println(e);
			 System.out.println("Failed to connect to "+diskName+" Database!");
			 return dbInstance;
		 }
  
  
  
  
  }
	
  //Create Database Disk
  public static Database createDiskDatabase(String diskName) {
	  //Safely close Current Database
	  if(dbInstance!=null) {
    	  if(!dbInstance.isClosed()) {
    		 dbInstance.close();
    	  }
     }
	  
	 dbInstance= new DiskDatabase
                 .DatabaseBuilder()
                 .setDisk(diskName)
                 .build();
	 
	 return dbInstance;
	 
	  
  }	
  
  //Connect to in-memory database
  public static Database connectToMemoryDatabase() {
	  
	try {
		
	    if(dbInstance!=null) {
    	    if(!dbInstance.isClosed()) {
    		 dbInstance.close();
    	  }
        }
	  
	    dbInstance= new MemoryDatabase
			     .DatabaseConnection()
			     .build();
	 
 	    return dbInstance;
	 }
	 catch(Exception e) {
		 System.out.println(e);
		 System.out.println("Database Connection Failed!");
		 return dbInstance;
	 }
  }
	
	
  	
	

}
