package models;

import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {
	private String name;
	private String description;
	private LocalDate date;
	private double colorR;
	private double colorG;
	private double colorB;
	private double colorO;
	
	public Task(String name, String description, LocalDate date) {
		if(name == null) {
			this.name = "";
		}
		else {
			this.name = name;
		}
		
		if(description == null) {
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
	}
    
    public String toString() {
    	return this.name;
    }



	public double getColorR() {
		return colorR;
	}



	public void setColorR(double colorR) {
		this.colorR = colorR;
	}



	public double getColorG() {
		return colorG;
	}



	public void setColorG(double colorG) {
		this.colorG = colorG;
	}



	public double getColorB() {
		return colorB;
	}



	public void setColorB(double colorB) {
		this.colorB = colorB;
	}



	public double getColorO() {
		return colorO;
	}



	public void setColorO(double colorO) {
		this.colorO = colorO;
	}

}
