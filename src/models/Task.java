package models;

public class Task {
	String name = "";
	String description = "";
	String date = "";
	
	public Task(String name, String description, String date) {
		this.name = name;
		this.description = description;
		this.date = date;
	}
	
	public void editTask(String name, String description, String date) {
		
		if(!name.equals("")) {
			this.name = name;
		}
		
		if(!description.equals("")) {
			this.description = description;
		}
		
		if(!date.equals("")) {
			this.date = date;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public int compareTo(Task other) {
		if(name.toUpperCase().equals(other.name.toUpperCase())) {
			return 0;
		}
		else {
			return 1;
		}
	}

}
