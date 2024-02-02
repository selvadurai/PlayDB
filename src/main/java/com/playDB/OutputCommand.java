package com.playDB;

/*
* **********************************************************************
* Filename: Output.java
* Author: Jonathan Kevin Selvadurai
* Date: February,2,2024
* Description: Output Commands output all records in the table
*  
* 
* **********************************************************************/


public class OutputCommand implements Command {
    
	private String[] tokens;
	private BtreeDAO btreeDAO;
	
	OutputCommand(String[] tokens){
		this.tokens=tokens;
	} 
	
	
	
	@Override
	public void execute() {
		btreeDAO=new BtreeDAO();
		btreeDAO.outputTable(tokens[1].trim());	
	}

}
