package views;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import models.Category;

//TODO: an add task button?

public class CategoryView extends VBox {
	private ArrayList<TaskViewController> tasks;
	private Category cat;
	static private final String EDIT_IMG_URL = "/edit.png";
	static private final String ADD_IMG_URL = "/add.png";
	static private final int BUTTON_SIZE = 12;

	public CategoryView(Category cat) {
		super(10.0);
		this.cat = cat;
		Image editImg = new Image(getClass().getResourceAsStream(EDIT_IMG_URL));
		ImageView editView = new ImageView(editImg);
		editView.setFitHeight(BUTTON_SIZE);
		editView.setFitWidth(BUTTON_SIZE);
		Button editButt = new Button("", editView);
		editButt.setTooltip(new Tooltip("Change category name"));

		Image addImg = new Image(getClass().getResourceAsStream(ADD_IMG_URL));
		ImageView addView = new ImageView(addImg);
		addView.setFitHeight(BUTTON_SIZE);
		addView.setFitWidth(BUTTON_SIZE);
		Button addButt = new Button("", addView);
		addButt.setTooltip(new Tooltip("Add new task to this category"));
		Label nameLabel = new Label(cat.getName());
		HBox buffer = new HBox();
		HBox.setHgrow(buffer, Priority.ALWAYS);

		ToolBar toolbar = new ToolBar(nameLabel, buffer, editButt, addButt);
		TextField nameInput = new TextField(cat.getName());

		nameLabel.setOnMouseClicked(e -> {
			toolbar.getItems().removeAll(nameLabel, buffer, editButt, addButt);
			toolbar.getItems().add(nameInput);

			nameInput.setOnAction(e2 -> {
				cat.setName(nameInput.getText());
				toolbar.getItems().remove(nameInput);
				nameLabel.setText(cat.getName());
				toolbar.getItems().addAll(nameLabel, buffer, editButt, addButt);
			});
		});

		editButt.setOnMouseClicked(e -> {
			toolbar.getItems().removeAll(nameLabel, buffer, editButt, addButt);
			toolbar.getItems().add(nameInput);

			nameInput.setOnAction(e2 -> {
				cat.setName(nameInput.getText());
				toolbar.getItems().remove(nameInput);
				nameLabel.setText(cat.getName());
				toolbar.getItems().addAll(nameLabel, buffer, editButt, addButt);
			});
			
			
		});

		addButt.setOnMouseClicked(e -> {
			//TODO: i want a popup window thatll create a new task + taskvc (make dummy vc while julian implements?)	
			//I can just made a new task using taskvc directly and then add it to tasks 
			//right here
		});

		//TODO: erase this before turning it in
		//as of rn im repeating that chunk of event handling cuz im dumb and bad at translating b/w lambda and anon inner classes

		this.setMinSize(200, 100);
		this.getChildren().add(toolbar);
		//TODO: change taskvc to nodes (panes?) so i can use this
		//this.getChildren().addAll(tasks);
	}

	public Category getCategory() {
		return this.cat;
	}
}
