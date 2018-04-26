package models;

public class Task {
	private String name;
	private String description;
	private String date;
	
	public Task(String name, String description, String date) {
		this.name = name;
		this.description = description;
		this.date = date;
	}
	
	public String getName() {
		return name;
	}

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
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
