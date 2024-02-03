PlayDB
====

Introduction
--
PlayDB is a straightforward NoSQL JSON document database, similar to MongoDB, created to help developers understand how databases work. 

**PlayDB Features**

   - PlayDB users can utilize in disk or in-memory database     
   - Import Json file into table
   - Export table into Json file
   - Runs on all operating Systems.
   - Play Scripting language enables uses to execute play commands in one file

 **Installation Instructions**

   1. Download PlayDB project
   2. Go into PlayDB/PlayDB folder
   3. Type the following command to run program "java -jar playDB.jar"

   ![image](https://github.com/selvadurai/PlayDB/assets/4705770/13df0137-db13-4af3-b43b-7dad9dc0be9d)

  **Play Commands**
   
         read(<condition>,<table>)- output data from table that matches condition in tabular format
         Example: read(age>23 && name=="bob",table) 

         read(<condition>,<table>,[value1,value2,value2]) - output records that match condition in table and the values in the list of the record. 
         Example: read(age>23 && name=="bob",table,[age,name,occupation,department])

         insert(<json>,table) - Inserts Json data into table 
         Example: insert({name:"Stacy",age:26,occupation:"accountant"},employee)


         output <table>- outputs all records in table in json format
         Example: output table


         replace(<condition>,<json>,<table>) - replaces records in table with json statement that matches condition 
         Example:replace(occupation=="programmer" && name=="kevin",{name:"Kevin",age:23,occupation:"dreamer"},employee)
  

         remove(<condition>,<table>) - removes records in table with json statement that matches condition 
         Example:remove(name=="Bom" && occupation=="sales",employee) 

         list <tables> || <databases> - list can commands lists tables or database disks
         Example: list tables | list databases

         connect  to <database> - connects to database disk
         Example: connect to disk1.db

         create database <database> - creates disk database
         Example: create database disk.db 
         Import note always put *.db at the end of the database name!!!!!!!!


         <json file> > table - imports json file to table
         Example: data.json > table 

         
         <table> > <json file>  - exports  table to json
         Example table > data.json

         <table> > <json file>  - exports  table to json
         Example table > data.json

         read(<condition>,<table>) > table - copies read statement to table
         Example read(age>23,table) > copyTable

         read(<condition>,<table>) > data.json - exports read statment to json file
         Example read(age>23,table) > data.json


         
 
         
     

         


Requirments
---
Java 1.7 or higher

PlayDB Under the Hood
---
**Code Design:**

![image](https://github.com/selvadurai/PlayDB/assets/4705770/51f9f0ba-c188-44ee-9ccd-2629b051d476)


