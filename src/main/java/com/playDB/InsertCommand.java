package com.playDB;

/*
* **********************************************************************
* Filename: InsertCommand.java
* Author: Jonathan Kevin Selvadurai
* Date: February,2,2024
* Description: Inserts Json into table
*  
* 
* **********************************************************************/

public class InsertCommand implements Command{

	private String json;
	private String table;
	private BtreeDAO btreeDAO; 
	
	public InsertCommand(String json, String table) {
	      this.json=json;
	      this.table=table;
	}
	

	
	@Override
	public void execute() {
	     try{ 	
		
	    	btreeDAO = new BtreeDAO();
		    btreeDAO.insertStatement(json, table);
	     }
	     catch(Exception e) {
	    	System.out.println(e);
	     }
	}

}
