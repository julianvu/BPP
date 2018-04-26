package models; 

import java.io.*; 
import java.util.*; 

public class Project implements Serializable{ //TODO: whats serialization 
	private ArrayList<Category> categories; 
	private String name; 
	//private ArrayList<String> users; 

	public Project(String name) { 
		this.name = name; 
	} 
} 