package com.playDB;


/* **********************************************************************
* Filename: ReplaceCommand.java
* Author: Jonathan Kevin Selvadurai
* Date: February,2,2024
* Description: Replace Command updates records from table
* 
* **********************************************************************/


public class ReplaceCommand  implements Command{

	private String condition;
	private String json;
	private String table;
	
	private BtreeDAO btreeDAO;
	
	ReplaceCommand(String condition,String json,String table){
		this.condition=condition;
		this.json=json;
		this.table=table;
		
	}
	
	@Override
	public void execute() {
	 
		try {
			btreeDAO=new BtreeDAO();
	     	btreeDAO.replace(condition, json, table);
		}catch(Exception e) {
			System.out.println(e);
		}
	}

}
