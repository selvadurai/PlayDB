package com.playDB;

/*
* Filename: CopyTableCommand.java
* Author: Jonathan Kevin Selvadurai
* Date: February,2,2024
* Description: Copies read function to table 
* Example: "read(age>23) > table"
* 
*/




public class CopyTableCommand  implements Command{
	
	private String conditions;
	private String orginalTable;
	private String copyTable;
	private BtreeDAO btreeDAO;

	
	
	public CopyTableCommand(String conditions,String orginalTable,String copyTable) {
		this.conditions=conditions;
		this.orginalTable=orginalTable;
		this.copyTable=copyTable;

	}
	

	@Override
	public void execute() {
		btreeDAO= new BtreeDAO();
		btreeDAO.copyTable( conditions, orginalTable, copyTable);
		
	}

}
