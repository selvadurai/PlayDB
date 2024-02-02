package com.playDB;

import org.mapdb.DB;


/*
* 
* Filename: Database.java
* Author: Jonathan Kevin Selvadurai
* Date: February,2,2024
* Description: Abstract Database Object. User can choose disk or in-memory database. 
* 
* */



public abstract class Database {

	protected DB db;
	
	public  abstract String getString();
	public  abstract Boolean isClosed();
	public  abstract void close();
	public  abstract DB getDB();
	public  abstract void commit();
	public  abstract  Iterable<String> getAllObjectNames();
	
	
}
