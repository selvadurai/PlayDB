package com.playDB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
* **********************************************************************
* Filename: PlayCommand.java
* Author: Jonathan Kevin Selvadurai
* Date: February,2,2024
* Description: Purpose is to interpret and run *.play files.
* 
*  
* 
* **********************************************************************/


public class PlayCommand implements Command {
    
	private String [] tokens;
	private String playFile;
	private CommandInvoker invoker = CommandInvoker.getInstance();
	
	private ConcurrentLinkedQueue<String> commands = new ConcurrentLinkedQueue<String>();
	Database dbInstance;
	
	
	PlayCommand(String[] tokens){
	   this.tokens=tokens;
	   this.dbInstance=DataSource.getInstance();
	   
	   if(this.tokens.length > 1)
	   {
		 this.playFile=this.tokens[1].trim();   
    
	   
	   }
	}
	
	private boolean isPlayFile( String filename) {
            String regex = ".*\\.play$";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(filename);
	        return matcher.matches();
	}
	
	/*Reads from a play file and stores all command in the play
	 * in a queue*/
	private void playFileInterpeter(String file) {
		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
            
			while (line != null) {
				//.pushCommand(line.trim());
				//System.out.println(line);
				commands.offer(line.trim());
				// read next line
				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			 e.printStackTrace();
  	     }
	}
	
	
	@Override
	public void execute() {
		//Validates if it's a play file
		if(this.isPlayFile(playFile)) {
			 
			//Play file goes into interpreter
			this.playFileInterpeter(playFile);
			
		 	//Execute all commands stored
			while(!commands.isEmpty()) {
				 String test=commands.poll();
				 System.out.println(test); 
				 invoker.pushCommand(test);
		    }
			
           System.out.println("Play Script Succesfully Executed!");		    
		}
		else 
		{
		  System.out.println("File Not Compatible with Play Interperter!"); 	
		}

	}

}
