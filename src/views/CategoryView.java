package views;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.util.Pair;
import models.Category;
import models.Project;
import models.*;
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
			Dialog<Task> dialog = new Dialog<Task>();
	        dialog.setTitle("New Task");
	        dialog.setHeaderText(null);


	        // Set the button types.
	        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
	        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

	        // Create the name, desc, dueDate labels and fields.
	        GridPane grid = new GridPane();
	        grid.setHgap(10);
	        grid.setVgap(10);
	        grid.setPadding(new Insets(20, 150, 10, 10));

	        TextField name = new TextField();
	        name.setPromptText("Name");
	        TextArea desc = new TextArea();
	        desc.setPromptText("Description");
	        DatePicker dueDate = new DatePicker();
	        dueDate.setPromptText(LocalDate.now().toString());
	        

	        grid.add(new Label("Name:"), 0, 0);
	        grid.add(name, 1, 0);
	        grid.add(new Label("Description:"), 0, 1);
	        grid.add(desc, 1, 1);
	        grid.add(new Label("Date Due:"), 0, 2);
	        grid.add(dueDate, 1, 2);
//	        grid.add(signInMessage, 1, 2);

	        // Enable/Disable login button depending on whether a username was entered.
	        Node okayButton = dialog.getDialogPane().lookupButton(loginButtonType);
	        okayButton.setDisable(true);

	        // Do some validation (using the Java 8 lambda syntax).
	        name.textProperty().addListener((observable, oldValue, newValue) -> {
	            okayButton.setDisable(newValue.trim().isEmpty());
	        });

	        dialog.getDialogPane().setContent(grid);

	        // Request focus on the username field by default.
//	        Platform.runLater(() -> name.requestFocus());

	        // Convert the result to a username-password-pair when the login button is clicked.
	        dialog.setResultConverter(dialogButton -> {
	            if (dialogButton == loginButtonType) {
	                return new Task(name.getText(), desc.getText(), dueDate.getValue());
	            }
	            dialog.close();
	            return null;
	        });

	        AtomicReference<Optional<Task>> result = new AtomicReference<>(dialog.showAndWait());

	        Task task = result.get().get();
            System.out.println("Task name: " + task.getName() + " desc: " + task.getDescription() + " due date: " + task.getDate().toString());
            
            TaskViewController taskvc = new TaskViewController(task);
            //Save or something or another
//            this.tasks.add(taskvc);
            
            this.getChildren().addAll(taskvc);
            
            
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
