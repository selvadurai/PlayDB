package com.playDB;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch; 


public class PlayCommandLine {

    public static void main(String[] args) {
        try (Scanner myObj = new Scanner(System.in)) {
            CommandInvoker invoker = CommandInvoker.getInstance();
            String command = "";
            
          

            while (!command.equalsIgnoreCase("EXIT")) {
                System.out.print("PlayDB> ");
                command = myObj.nextLine();

                invoker.pushCommand(command);
                invoker.evoke();
                
                
            }

            System.out.println("Goodbye :) ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

