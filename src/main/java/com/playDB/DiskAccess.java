package com.playDB;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/*
* **********************************************************************
* Filename: DiskAccess.java
* Author: Jonathan Kevin Selvadurai
* Date: February,2,2024
* Description: The purpose of this object to create, and access database disks
* in the ./Databases folder in the program
* 
* **********************************************************************/

public class DiskAccess {
	
	//Directory in the program where all the database disks are stored
	public static final String diskDirectory="Database/";
	
    private File disk;
    private File[] diskArray; 
    
    private Map<String, String> diskMap;;


	
	public DiskAccess(){
	   try {
		    disk = new File(diskDirectory);
		    diskArray=disk.listFiles();
		    diskMap=new ConcurrentHashMap<String, String>();
	
		    //Store all disks in the folder in a hashmap
            for(File disk:diskArray) {
            	diskMap.put(disk.getName(), disk.getAbsolutePath());
            }
		      
	   }
	   catch(Exception e) {
		   System.out.println(e);
		   System.out.println("Couldn't Open Database Folder");
	   }
    }
	
	//Get hashmap
	public Map<String, String> getDiskMap() {
		  return diskMap;
	}
	
	//Check if the database exists in the folder
	public Boolean diskExist(String diskName) {
		return diskMap.containsKey(diskName);
	}
	
	//Get absolute path for disk file
	public String diskAbsPath(String diskName) {
		return diskMap.get(diskName);
	}
	
	//List all disks in the folder
	public void listAllDisks() {
		System.out.println(diskMap.keySet());
	}
	
	//Get the name for all disks
	public Set<String> diskNameSet() {
		 return diskMap.keySet();
	}
	


}
