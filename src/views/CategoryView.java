package views;

import java.util.ArrayList;
import javafx.event.*;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

//TODO: an add task button?

public class CategoryView extends VBox{
	private ArrayList<TaskView> tasks;
	private String catName;
	private String editImgURL = "/edit.png";
	static private final int BUTTON_SIZE = 12;

	public CategoryView(String name) {
		super(10.0);
		catName = name;
		Image image = new Image(getClass().getResourceAsStream(editImgURL));
		ImageView imgView = new ImageView(image);
		imgView.setFitHeight(BUTTON_SIZE);
		imgView.setFitWidth(BUTTON_SIZE);
		Button editButt = new Button("", imgView);
		Label nameLabel = new Label(catName);
		HBox buffer = new HBox();
		HBox.setHgrow(buffer, Priority.ALWAYS);

		ToolBar toolbar = new ToolBar(nameLabel, buffer, editButt);
		TextField nameInput = new TextField(catName);
		
		nameLabel.setOnMouseClicked(e -> {
			toolbar.getItems().removeAll(nameLabel, buffer, editButt);
			toolbar.getItems().add(nameInput);

			nameInput.setOnAction(e2 -> {
				catName = nameInput.getText();
				toolbar.getItems().remove(nameInput);
				nameLabel.setText(catName);
				toolbar.getItems().addAll(nameLabel, buffer, editButt);
			});
		});

		editButt.setOnMouseClicked(e -> {
			toolbar.getItems().removeAll(nameLabel, buffer, editButt);
			toolbar.getItems().add(nameInput);

			nameInput.setOnAction(e2 -> {
				catName = nameInput.getText();
				toolbar.getItems().remove(nameInput);
				nameLabel.setText(catName);
				toolbar.getItems().addAll(nameLabel, buffer, editButt);
			});
		});
		
		//TODO: erase this before turning it in
		//as of rn im repeating that chunk of event handling cuz im dumb and bad at translating b/w lambda and anon inner classes
		
		this.setMinSize(200, 100);
		this.getChildren().add(toolbar);
		//TODO: change taskvc to nodes (panes?) so i can use this
		//this.getChildren().addAll(tasks);
	}
}
