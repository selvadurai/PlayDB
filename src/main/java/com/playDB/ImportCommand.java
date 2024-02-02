package com.playDB;

import java.io.IOException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;


/*
* **********************************************************************
* Filename: ImportCommand.java
* Author: Jonathan Kevin Selvadurai
* Date: February,2,2024
* Description: Imports Json file into table
*  
* 
* **********************************************************************/


public class ImportCommand implements Command {

	private String file;
	private String table;
	private BtreeDAO btreeDAO;
	
	
	public ImportCommand(String file,String table) {
		this.file=file;
		this.table=table;
	}
	
	
	
	
	@Override
	public void execute() {
       try {
	   		btreeDAO= new BtreeDAO();
			btreeDAO.importJsonFile(file, table);
			System.out.println(file + " Successfully Imported! ");
	   } catch (JsonIOException | JsonSyntaxException | IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
	    }     		
	}

}
