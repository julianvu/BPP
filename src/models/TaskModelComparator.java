package models;

import java.util.Comparator;

public class TaskModelComparator implements Comparator<Task> {
	public int compare(Task task1, Task task2)
	{
		return task1.getDate().compareTo(task2.getDate());
	}
}
