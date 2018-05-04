package models; 

import java.io.*; 
import java.util.*;

import views.CategoryView; 

public class Project implements Serializable{ //TODO: whats serialization 
	private ArrayList<Category> categories; 
	private String name; 
	//private ArrayList<String> users; 

	public Project(String name) { 
		this.name = name; 
		this.categories = new ArrayList<>();
	}

	public ArrayList<Category> getCategories() {
		return this.categories;
	}

	public String getName() { return this.name; }
} 