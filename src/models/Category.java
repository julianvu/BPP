package models;
import java.io.Serializable;
import java.util.*;

public class Category implements Serializable{
	private ArrayList<Task> tasks;
	private String name;
	public Category(String name) {
		this.setName(name);
		this.tasks = new ArrayList<Task>();
	}
	
	public void addTask(Task newTask) {
		this.tasks.add(newTask);
		Collections.sort(tasks, new TaskModelComparator());
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
	
	public ArrayList<Task> getTasks(){
		return this.tasks;
	}

	public void setTasks(ArrayList<Task> newTasks) {
		// TODO Auto-generated method stub
		this.tasks = newTasks;
	}

}