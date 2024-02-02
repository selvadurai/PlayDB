package com.playDB;

import java.util.List;


/*
* **********************************************************************
* Filename: PlayCommand.java
* Author: Jonathan Kevin Selvadurai
* Date: February,2,2024
* Description: Reads condition and outputs results
* 
*  
* 
* **********************************************************************/

public class ReadCommand  implements Command{

	BtreeDAO btreeDAO; 
	String conditions;
	String table;
    Boolean isReadCommandOne;
    List<String> valueList;
	
	//read(age>23,table)
	public ReadCommand(String conditions, String table) {
		//btreeDAO = new BtreeDAO();	
		this.conditions=conditions;
		this.table=table;
		isReadCommandOne=true;
		valueList=null;
	}
	
	//read(age>23,table,[person,name,age,city])
	public ReadCommand(String conditions,String table,List<String> valueList) {
		this.table=table;
		this.conditions=conditions;
		this.valueList=valueList;
		isReadCommandOne=false;
		btreeDAO= new BtreeDAO();

		
	}
		
	@Override
	public void execute() {
		//Instaniate btreeDOA in execution function. To prevent it from being executed early.
		btreeDAO = new BtreeDAO();
		
		if(isReadCommandOne) 
           btreeDAO.displayReadSearch(conditions, table);		
		else
		   btreeDAO.displayReadSearch(conditions, table,valueList);	
		
			
   }

}
