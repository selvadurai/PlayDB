package com.playDB;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class PlayCommandLine {
   public static String command = "";


    public static void main(String[] args) {
            //String command = "";

                //System.out.print("PlayDB> ");
                //command = myObj.nextLine();

                    // Start a new thread
            Thread newThread = new Thread(() -> {
                    
                  try (Scanner myObj = new Scanner(System.in)) {
                    	
                        CommandInvoker invoker = CommandInvoker.getInstance();

                        while (!command.equalsIgnoreCase("EXIT")) {

	                    
                            try {
                        	   System.out.print("PlayDB> ");
	                           command = myObj.nextLine();
	                           invoker.pushCommand(command);
	                           invoker.evoke();
                        	  }
                        	catch(Exception e) {
                        		System.out.println(e);
                        	}
                        
                        
                        
                        
                        }
                          System.out.println("Goodbye :) ");
                    }  catch (Exception e) {
                               e.printStackTrace();
                        }
                     
               });

                
                newThread.start();

               // invoker.pushCommand(command);
                //invoker.evoke();
            

    }
}
