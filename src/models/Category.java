package models;
import java.util.*;

public class Category {
	private TreeSet<Task> tasks;
	private String name;
	public Category(String name) {
		this.setName(name);
	}
	
	public void addTask(Task newTask) {
		this.tasks.add(newTask);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
