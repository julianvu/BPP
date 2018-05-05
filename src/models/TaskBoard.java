package models;

public class TaskBoard {
	private Project proj;
	public TaskBoard(Project proj) {
		this.setProj(proj);
	}
	public TaskBoard() {
	}
	public Project getProj() {
		return proj;
	}
	public void setProj(Project proj) {
		this.proj = proj;
	}
}
