package com.playDB;

import java.util.Set;

/*
* **********************************************************************
* Filename: ListCommand.java
* Author: Jonathan Kevin Selvadurai
* Date: February,2,2024
* Description: Execute List Commands for example "list tables" or "list databases"
*  
* 
* **********************************************************************/


public class ListCommand implements Command{

	private String tokenAction;
	
    public  ListCommand(){ }
   
    
    
    public ListCommand(String[] tokens) {
    	
    	if(tokens.length > 1) {
          this.tokenizer(tokens[1]); 
    	}
    	
    }
    
    public synchronized void tokenizer(String token) {
    	
    	switch(token) {
    	      
    	      case "databases":
    	                       tokenAction=token;
    	                       break; 
    	      case "tables":

                               tokenAction=token;
    	    	               break;
    	      default:
    	    	               System.out.println("Command Doesn't Exist!");   
    	                       break;
    	                     
       }
    
    }
	
    private synchronized void displayDbTable() {
    	DiskAccess diskAccess = new DiskAccess();
    	System.out.println("------------------------");
    	System.out.printf("%13s ", "DATABASE LIST");
    	System.out.println();
    	System.out.println("------------------------");



    	for( String disk:diskAccess.diskNameSet()) {
            System.out.format("%13s",disk);
        	System.out.println();

    	}
    	
    	System.out.println("------------------------");

    }
	
	
	public synchronized void execute() {
	      switch(tokenAction) {
	      
	          case "databases":
	        	                displayDbTable();
	        	                break;
	          case "tables":  	               
	                           DataSource.displayAllTables();
	                           break;
	      }
	
	}

}
