package views;

import java.util.ArrayList;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class CategoryView extends VBox{
	private ArrayList<TaskView> tasks;
	private String name;

	public CategoryView(String name) {
		super(10.0);
		ToolBar title = new ToolBar(new Label(name));
		TextField titleInput = new TextField(name);
		title.setOnMouseClicked(e -> {
			Pane pane = new Pane(titleInput);
			titleInput.setOnAction(e2 -> {
				name = titleInput.getText();
			});
		});

		this.getChildren().add(title);
		for (TaskView t:tasks) {
			this.getChildren().add(t);
		}
	}
}
