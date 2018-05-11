package models;

import java.util.Comparator;

public class TaskModelComparator implements Comparator<Task> {
	public int compare(Task task1, Task task2)
	{
		//if (task1.getDate().compareTo(task2.getDate()) != 0)
		//	return task1.getName().compareTo(task2.getName());
		//return task1.getName().compareToIgnoreCase(task2.getName());
		return (task1.getDate().compareTo(task2.getDate()));
	}
}
