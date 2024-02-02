package com.playDB;

/*
* 
* Filename: CreateCommand.java
* Author: Jonathan Kevin Selvadurai
* Date: February,2,2024
* Description: Creates <token_action> <name_creation>
* Example: "create database disk.db" 
* 
*/




public class CreateCommand implements Command{

  private String [] tokens;
  private Boolean isCreateDatabase;
  private String databaseName;
  
	
  public  CreateCommand(String[] tokens) {
		 
		 this.tokens=tokens;
		
		 if(this.tokens.length > 2) {
	          this.tokenizer(this.tokens[1]); 
	    }
	}
	
	public void tokenizer(String token) {
		
		switch(token) {
	      
	        case "database": 
	        	            databaseName=tokens[2].trim();
	        	            isCreateDatabase=true;
	        	            break; 
	                       
	         default:       
	        	            isCreateDatabase=false; 
	    	                System.out.println("Command Doesn't Exist!");   
	                        break;
	                     
          }

  }
	

	
	@Override
	public void execute() {
		//if create database token select then create database!
		if(isCreateDatabase) 
		{
			DataSource.createDiskDatabase(databaseName);
			System.out.println("Database Created !");
		}
		
	}

}
