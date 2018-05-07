package models;

import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {
	private String name;
	private String description;
	private LocalDate date;
	
	public Task(String name, String description, LocalDate date) {
		if(name.equals(null)) {
			this.name = "";
		}
		else {
			this.name = name;
		}
		
		if(description.equals(null)) {
			this.description = "";
		}
		else {
			this.description = description;
		}
		
		if(date == null) {
			this.date = LocalDate.now();
		}
		else {
			this.date = date;
		}
	}

	
	
	public String getName() {
		return name;
	}

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int compareTo(Task other) {
		return this.date.compareTo(other.date);
    	
    	/*if(name.toUpperCase().equals(other.name.toUpperCase())) {
			return 0;
		}
		else {
			return 1;
		}*/
	}

}
