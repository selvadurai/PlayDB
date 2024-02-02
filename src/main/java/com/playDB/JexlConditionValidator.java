package com.playDB;

import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.MapContext;

/*
* **********************************************************************
* Filename: JexlConditionValidator.java
* Author: Jonathan Kevin Selvadurai
* Date: February,2,2024
* Description: Validates condition statement 
* Uses the Jexl Apache Engine
* Example validates: age>23 && person=="Bob" is true or false
*  
* 
* **********************************************************************/



public class JexlConditionValidator {
    private final JexlEngine jexlEngine;
    private JexlExpression expression;
   // private JexlContext context; 

    public JexlConditionValidator(String condition) {
        
    	this.jexlEngine = new JexlBuilder().create();
        this.expression = jexlEngine.createExpression(condition);
        //JexlContext context = new MapContext();

        
    }

    public boolean validateCondition(JexlContext context) {
        try {
            Object result = expression.evaluate(context);
            return (result instanceof Boolean) && ((Boolean) result);
        } catch (Exception e) {
            // Handle expression evaluation errors
            e.printStackTrace();
            return false;
        }
    }
    
    

    /*
   
    public static void main(String[] args) {
        // Example usage
        String condition = "age > 18 && name == 'John'";
        JexlConditionValidator validator = new JexlConditionValidator(condition);

        // Create a context with variables
        JexlContext context = new MapContext();
        
        for(int i=0;i<100;i++) {
           String x="age"+i;
        	context.set(x, 16);
        }
        // context.set("name", "John");
        
        System.out.println(context.has("age1"));
        System.out.println(context.has("age2"));
        
       // validator.setStringContext("name", "Brick");
       // System.out.println(validator.getJexlContext().has("name"));
        

       /* if (validator.validateCondition(context)) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }
    }
    */
    
}
