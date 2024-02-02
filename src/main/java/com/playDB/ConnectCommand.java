package com.playDB;

/*
* Filename: ConnectCOmmand.java
* Author: Jonathan Kevin Selvadurai
* Date: February,1,2024
* Description: Command Connects to Database 
* Example:
* connect to database disk.db
* execute function connects to database the user types
*/



public class ConnectCommand implements Command{

	public boolean makeConnectionToDB;;
	private String [] tokens;
	
	public  ConnectCommand(String[] tokens) {
		 
		 this.tokens=tokens;
		 makeConnectionToDB=false;
		
		
		 if(this.tokens.length > 2) {
	          this.tokenizer(this.tokens[1]); 
	    }
	}
	
	public void tokenizer(String token) {
		
		switch(token) {
	      
	      case "to":       makeConnectionToDB=true;
	                       break; 
	                       
	      default:
	    	               System.out.println("Command Doesn't Exist!");   
	                       break;
	                     
 }

	}
	
	@Override
	public void execute() {
    	DiskAccess diskAccess = new DiskAccess();

		if(makeConnectionToDB) {
			if(diskAccess.diskExist(tokens[2]) || tokens[2].equals("memory"))
		     	DataSource.getConnection(tokens[2]);
			else
				System.out.println("Database Doesn't Exist");
		
		}
		else {
            System.out.println("Command Doesn't Exist!");   
		}
		
		
        System.out.println("Connect Command");		    

		
	}

}
