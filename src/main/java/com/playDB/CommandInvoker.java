package com.playDB;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/*
* Filename: CommandInvokers.java
* Author: Jonathan Kevin Selvadurai
* Date: February,1,2024
* Description: 
* Purpose is store the command objects in the queue after type in the command line.
* Command Invokers also is a Lexer and parser for the play db commands. In simple terms
* it is queue for play commands and a play command interpreter. 
* 
* Notes:
* Object is singleton object
*/



public class CommandInvoker {
	
	private static CommandInvoker invokerInstance;
	
	private ConcurrentLinkedQueue<Command> queue = new ConcurrentLinkedQueue<Command>();

	
	private CommandInvoker() {
		
	}
	
	//Object can only be instantiated once
	public static CommandInvoker getInstance() {
         if(invokerInstance==null) {
        	 invokerInstance=new CommandInvoker();
         }		
         
         return invokerInstance;
	}
	
	/*Command type from command line is sent to 
	  lexer for Lexical analysis             */
	public void pushCommand(String command) {
		    this.commmandLexer(command);
	}
	
    /*
     *Commandlexer determines if the command is a function,command or expression and 
     *send them to correct parser.
     * 
     *Command Examples 
     *list tables
     *create database disk.db
     *connect to disk.db
     * 
     *Function Command Examples
     * read(age>16,table)
     * insert({name:"Bob".table1}
     *
     *Expressions
     *data.json > table1
     *read(age>16,table) > copyTable
     *
     ***********************************************************************/
	public void commmandLexer(String command) {
           String[] tokens = command.split(" ");
          //Lexical analysis first word
           this.commandsLex(tokens[0],tokens);
           this.functionLexer(command); 
           this.expressionLex(command);
	}
	
	//Function does lexical analysis on "Function"
	public void functionLexer (String function) {
		
		/* If insert statement is valid, parse function and store command in queue
		 * Example "insert({age:16,fname:"Bob",lname:"Smith"})"*/
		if(FuncParser.isValidInsertStatement(function)) {
			   String json=FuncParser.getInsertJSONToken(function);
			   String table=FuncParser.getInsertTableToken(function);
			   queue.offer(new InsertCommand(json,table));   
		}
		
		
		/*If read statement is valid, parse function and store command in queue
		 *Example "read(age>16,table)"                             */
		if(FuncParser.isValidReadFunction(function)){
			   String conditions=FuncParser.parseConditions(function).trim();
			   String table=FuncParser.parseTable(function).trim();
			   queue.offer(new ReadCommand(conditions,table));
		}
		
		
		/*If remove statement is valid, parse function and store command in queue
		 *Example "remove(age>23,table)" */
		if(FuncParser.isValidRemoveFunction(function)) {
			String conditions=FuncParser.parseConditions(function);
			String table=FuncParser.parseTable(function);
			queue.offer(new RemoveCommand(conditions,table));
		}
		
	   /*If readAll statement is valid, parse function and store command in queue
		*Example "read(age>23,table,[person,fname,lname])" */
		if(FuncParser.isValidReadParamFunction(function)) {
			ReadFunctionParams params = ReadArrayFunctionParser.parseReadFunction(function);
	        String conditions=params.getCondition();
			String table=params.getTable();
			List<String> valuesList = params.getArray();

			queue.offer(new ReadCommand(conditions,table,valuesList) );
		}
		
		/*If replace statement is valid, parse function and store command in queue
		 *Example "replace(age>36,{age:100})" */
		if(FuncParser.isValidReplaceFunction(function)) {
			String parameter=(String) function.subSequence(function.indexOf("(")+1, function.indexOf(")"));
			String[] parameterList = parameter.split(",");
			System.out.println(parameterList[0]+" "+parameterList[1]+" "+parameterList[2]);
			queue.offer(new ReplaceCommand(parameterList[0].trim(),parameterList[1].trim(),parameterList[2].trim() ));
		}
		
			
		
	}
	
	//Looks for expressions type command
	public void expressionLex(String expression) {
		
		 //If import expression is valid then parse function and store command in queue
		 //Example "data.json > table" 
		 if(ExpressionParser.isValidImportExpressionValid(expression)) {
			 String file=ExpressionParser.parseFile(expression);
			 String table=ExpressionParser.parseTable(expression);
			 
	         queue.offer(new ImportCommand(file,table) );
		 }
		 
		 
		 //If export expression is valid then parse function and store command in queue
		 //Example "table > data.json"
		 if(ExpressionParser.isValidExportExpressionValid(expression)) {
			 System.out.println(expression+ " is valid");
			 String file=ExpressionParser.ExportParseFile(expression);
			 String table=ExpressionParser.ExportParseTable(expression);
			 
	         queue.offer(new ExportCommand(file,table) );
		 }
		 
		 //If read copy to table expression  or read to json file is valid then parse function and store command in queue
		 //Example "read(age>23) > table" or "read(age>23) > data.json"
		 if(ExpressionParser.isValidReadToTableValid(expression)) {
			 
			 if(ExpressionParser.isValidReadExportJsonValid(expression)) {
				 String jsonFile=ExpressionParser.getReadTable(expression).trim();
				 String[] parameters =ExpressionParser.getReadParameters(expression);
				 
				 String condition=parameters[0].trim();
				 String table=parameters[1].trim();
				 
				 queue.offer(new ExportCommand( jsonFile,  condition, table) );
	
			 }
			 else {
			 
				 String copyTable=ExpressionParser.getReadTable(expression).trim();
				 String[] parameters =ExpressionParser.getReadParameters(expression);
				 
				 String condition=parameters[0].trim();
				 String orginalTable=parameters[1].trim();
	
				 
				 queue.offer(new CopyTableCommand( condition, orginalTable, copyTable));
			 }
			 
		 }
		 
		 
		 
		 
		
	}
	
	
	
	
	//Tokens execute store the commands 
	public void commandsLex(String command,String[] tokens) {
		  switch(command) {
         
		  
          case "list":
         	            queue.offer(new ListCommand(tokens));
     	                break;
     	  
          case "connect":
         	             queue.offer(new ConnectCommand(tokens));
	                     break;
	                     
          case "play":  
        	            queue.offer(new PlayCommand(tokens));
        	            break;
          
          case "create":  
	                    queue.offer(new CreateCommand(tokens));
	                    break;            
	       
	      case "output":  
	                    queue.offer(new OutputCommand(tokens));
	                    break;                 
	                    
	                    
          default:
                     break;
     	         
       }
	}
	
	//Evoke all Command objects from queue
	public void evoke()  {
		while(!queue.isEmpty()) {
			queue.poll().execute();
		}
	}
	


}
