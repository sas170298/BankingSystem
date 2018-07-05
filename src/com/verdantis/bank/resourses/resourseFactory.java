package com.verdantis.bank.resourses;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class resourseFactory {
	static HashMap<String, String>  hm;	
	static 
	{
		 hm=new HashMap<>();

	Properties prop=new Properties();	
	InputStream input = null;
	
	try {
		input = new FileInputStream("config.properties");
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	try {
		prop.load(input);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	 String dbname=	 prop.getProperty("database");
     String dbuser=(prop.getProperty("dbuser"));
     String dbpass=(prop.getProperty("dbpassword"));
     String host=prop.getProperty("host");
     
     hm.put("dbName",dbname);
     hm.put("dbUser", dbuser);
     hm.put("dbPass", dbpass);
     hm.put("dbHost", host); 
	 
   //  System.out.println(hm.get(1)); working	
	}
	
	public static String getDbDetails(String dbKey)
	{
	
		return hm.get(dbKey);

	}
	
	
}

