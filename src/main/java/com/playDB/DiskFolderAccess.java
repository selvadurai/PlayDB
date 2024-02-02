package com.playDB;
import java.util.ArrayList;

/*
* **********************************************************************
* Filename: DiskFolderAccess.java
* Author: Jonathan Kevin Selvadurai
* Date: February,2,2024
* Description: Disk Folder Access Interface
* 
* **********************************************************************/

public interface DiskFolderAccess {
	
	public ArrayList<String> listAllDisks();
	public String getDiskURI();
	public Boolean diskExist(String dbName);
	
}
