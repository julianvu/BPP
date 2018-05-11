package views;

import java.util.Comparator;

import models.TaskModelComparator;

public class TaskViewComparator implements Comparator<TaskViewController>{

	@Override
	public int compare(TaskViewController x, TaskViewController y) {
		TaskModelComparator comp = new TaskModelComparator();
		return comp.compare(x.getTask(), y.getTask());
	}

}
