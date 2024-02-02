package com.playDB;

import java.util.ArrayList;
import java.util.List;


/* **********************************************************************
* Filename: RemoveCommand.java
* Author: Jonathan Kevin Selvadurai
* Date: February,2,2024
* Description: Remove Command removes records or records from table
* 
* **********************************************************************/

public class RemoveCommand implements Command{

	private String  table;
	private String  condition;
	private BtreeDAO btreeDAO;
	
	
	public RemoveCommand(String condition,String table) {
		this.table=table;
		this.condition=condition;
	}
	
	
	
	
	@Override
	public void execute() {
		btreeDAO= new BtreeDAO();	
		btreeDAO.remove(condition, table);
	}

}
