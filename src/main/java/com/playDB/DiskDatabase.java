package com.playDB;

import org.mapdb.DB;
import org.mapdb.DBMaker;


/*
* **********************************************************************
* Filename: DiskDatabase.java
* Author: Jonathan Kevin Selvadurai
* Date: February,2,2024
* Description: Is child the of Database object. The objects purpose is 
* to connect and build disk database.
* 
* **********************************************************************/


public class DiskDatabase extends Database{
	
	private  String diskName; 
	//public static String dbDirectory="Database/";
	//public static DiskDatabase instance;
	
	//Builds Disk Database
	private DiskDatabase(DatabaseBuilder DatabaseBuilder) {
		this.diskName=DatabaseBuilder.diskName;
		 db=DBMaker
		   .fileDB(DiskAccess.diskDirectory+this.diskName)
		   .closeOnJvmShutdown()
		   .fileMmapEnableIfSupported()
		   //.fileLockWait()
		   .make();
		 
		 this.close();
		
	}
	//Connects to Disk Database
	private DiskDatabase(DatabaseConnection DatabaseConnection) {
		    this.diskName=DatabaseConnection.diskName;
			 db=DBMaker
					   .fileDB(DiskAccess.diskDirectory+this.diskName)
					   .closeOnJvmShutdown()
					   .fileMmapEnableIfSupported()
					   //.fileLockWait()				   
					   .make();
			 
			 
	}
	

	public DiskDatabase(String dbFile) {
		this.diskName=dbFile;
	}
	
	@Override

	public String getString() {
		return db.toString();
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
	public Iterable<String> getAllObjectNames() {
		return db.getAllNames();
	}
	
	
	//Disk Database Builder Class
	public static class DatabaseBuilder{
		private String diskName;
		
		public DatabaseBuilder setDisk(String diskName) {
			this.diskName=diskName;
			return this;
		}
		
		public DiskDatabase build() {
			/*if(instance!=null) {
				if(!instance.isClosed()) {
					instance.close();
					System.out.println("Database closed");

				}
			}
			
			instance=new DiskDatabase(this);
			*/
			return new DiskDatabase(this);
	   }
		
		
	}
	
	//Disk Database Connection
	public static class DatabaseConnection{
		private String diskName;
		
		public DatabaseConnection getDisk(String diskName) {
			this.diskName=diskName;
			return this;
		}
		
		public DiskDatabase connect() {
			//if(instance!=null) {
				//if(!instance.isClosed()) {
					//instance.close();
					//System.out.println("Database closed");
				//}
			//}
			
            //instance=new DiskDatabase(this);
			return new DiskDatabase(this);
			//return instance;
			
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

	

}
