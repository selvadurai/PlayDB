package com.playDB;

import org.mapdb.DB;
import org.mapdb.DBMaker;

/*
* **********************************************************************
* Filename: ListCommand.java
* Author: Jonathan Kevin Selvadurai
* Date: February,2,2024
* Description: Is child the of Database object. The objects purpose is 
* to connect in-memory database.
*  
* 
* **********************************************************************/

public class MemoryDatabase  extends Database{
	
	
   private MemoryDatabase(DatabaseConnection DatabaseConnection) {   
		 db=DBMaker
		   .memoryDB()
	       .closeOnJvmShutdown()
		   .make();
   }
    
	@Override
	public String getString() {
		return db.toString();
	}
	
	//Class Connect to in-memory database
	public static  class  DatabaseConnection{
		public MemoryDatabase build() {
			return new MemoryDatabase(this);
	   }
		
		
	}
	
	@Override
	public Boolean isClosed(){
	    	return db.isClosed();
	}
	
	 @Override
	 public void close(){
	    	if(!this.isClosed()) {
	    		db.close();
	    	}
	 }
	@Override
	public DB getDB() {
		return db;
	}
	@Override
	public void commit() {
       db.commit();		
	}
	
	@Override
	public Iterable<String> getAllObjectNames() {
		return db.getAllNames();
	}

}
