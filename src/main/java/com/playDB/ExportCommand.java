package com.playDB;

/*
* **********************************************************************
* Filename: ExportCommand.java
* Author: Jonathan Kevin Selvadurai
* Date: February,2,2024
* Description: Exports table to JSON file
*  
* 
* **********************************************************************/

public class ExportCommand implements Command {

	private String table;
	private String file;
	private String condition;

	private BtreeDAO btreeDAO;
	private Boolean isReadExport;

	//Used in expression "table > data.json"
	public ExportCommand(String file, String table) {
		this.table=table;
		this.file=file;
		isReadExport=false;
		condition="";
	}
	
	//Used in expression "read(age>23) > data.json"
	public ExportCommand(String file, String condition, String table) {
		this.table=table;
		this.file=file;
		//this.condition=condition;
		btreeDAO= new BtreeDAO();
		isReadExport=true;

	}
	
	
	
	
	@Override
	public void execute() {
		
		try {
		
		 btreeDAO= new BtreeDAO();
         
		 if(isReadExport)
	        btreeDAO .exportJsonReadConditionFile( file, condition, table);
		 else
		    btreeDAO.exportJsonFile(file, table);
	  	
	  	 System.out.println(file+" Succesfully Exported!");
	  	 
		}catch(Exception e) {
			System.out.println(e);
		}
	}

}
