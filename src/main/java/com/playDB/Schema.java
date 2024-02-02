package com.playDB;

import org.mapdb.BTreeMap;


/* **********************************************************************
* Filename: Schema.java
* Author: Jonathan Kevin Selvadurai
* Date: February,2,2024
* Description: Abstract Schema object
* 
* **********************************************************************/

public abstract class Schema {

	public abstract void createTable(Database db,String tableName);
	public abstract void insertIntoTable(Database db,String tableName,int id,String Json );
	public abstract void insertIntoTable(Database db,String tableName,String Json );

	public abstract void readAllTable(Database db,String tableName);
	
	
	public abstract void close();
	public abstract void useTable(Database db, String tableName);
	public abstract void removeRecord(Database db, String tableName, int id);
	public abstract void removeRecord(Database db, String tableName, int id, String json);
	

	
}
