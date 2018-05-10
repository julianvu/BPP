package views;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import models.Category;
import models.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
//TODO: an add task button?

public class CategoryView extends VBox {
    private ArrayList<TaskViewController> tasks;
    private Category cat;
    static private final String EDIT_IMG_URL = "/edit.png";
    static private final String ADD_IMG_URL = "/add.png";
    private static final String LOGIN_IMG_URL = "/login.png";
    static private final int BUTTON_SIZE = 12;

    public CategoryView(Category cat) {
        super();
        this.setStyle("-fx-padding: 10;\n" +
                "-fx-background-color: rgba(169, 169, 169, 0.5);\n" +
                "-fx-background-radius: 5;\n");

        tasks = new ArrayList<>();
		this.cat = cat;
		this.tasks = new ArrayList<>();
		this.setAllTasks(cat.getTasks());
		this.getChildren().addAll(tasks);
		this.setSpacing(10);
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

        //adding delete button
        Image deleteImg = new Image(getClass().getResourceAsStream(LOGIN_IMG_URL));
        ImageView deleteView = new ImageView(deleteImg);
        deleteView.setFitHeight(BUTTON_SIZE);
        deleteView.setFitWidth(BUTTON_SIZE);
        Button deleteButt = new Button("", deleteView);
        deleteButt.setTooltip(new Tooltip("Delete this category"));

        MenuItem newTaskItem = new MenuItem("Create new task");
        MenuItem moveTaskItem = new MenuItem("Move a task ...");
        MenuItem renameCategoryItem = new MenuItem("Rename category");
        MenuItem deleteCategoryItem = new MenuItem("Delete category");
        MenuButton menuButton = new MenuButton("•••", null, newTaskItem, moveTaskItem, renameCategoryItem, deleteCategoryItem);
        String menuStyle = this.getStyle();
        System.out.println(menuStyle);
        menuButton.getStylesheets().add("stylesheet.css");
        System.out.println(menuButton.getStyle());


        ToolBar toolbar = new ToolBar(nameLabel, buffer, menuButton);
        toolbar.setStyle("-fx-background-radius: 5;\n");
        TextField nameInput = new TextField(cat.getName());

        renameCategoryItem.setOnAction(event -> {
            toolbar.getItems().removeAll(nameLabel, buffer, menuButton);
            toolbar.getItems().add(nameInput);

            nameInput.setOnAction(e2 -> {
                cat.setName(nameInput.getText());
                toolbar.getItems().remove(nameInput);
                nameLabel.setText(cat.getName());
                toolbar.getItems().addAll(nameLabel, buffer, menuButton);
            });
        });

        nameLabel.setOnMouseClicked(e -> {
            toolbar.getItems().removeAll(nameLabel, buffer, menuButton);
            toolbar.getItems().add(nameInput);

            nameInput.setOnAction(e2 -> {
                cat.setName(nameInput.getText());
                toolbar.getItems().remove(nameInput);
                nameLabel.setText(cat.getName());
                toolbar.getItems().addAll(nameLabel, buffer, menuButton);
            });
        });

        deleteCategoryItem.setOnAction(event -> {
            ProjectView parent = (ProjectView) this.getParent();
            parent.getCategories().remove(this);
            parent.getProject().getCategories().remove(this.getCategory());
            parent.getChildren().remove(this);
        });

        moveTaskItem.setOnAction(event -> {
            MenuButton taskListMenu = new MenuButton();
            for (int i = 0; i < tasks.size(); i++) {
                taskListMenu.getItems().add(new MenuItem(tasks.get(i).getTask().getName()));
            }
            toolbar.getItems().remove(menuButton);
            toolbar.getItems().add(taskListMenu);
        });

        newTaskItem.setOnAction(e -> {
            //TODO: i want a popup window thatll create a new task + taskvc (make dummy vc while julian implements?)
            Dialog<Task> dialog = new Dialog<>();
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

            // Enable/Disable okay button depending on whether a name was entered.
            Node okayButton = dialog.getDialogPane().lookupButton(loginButtonType);
            okayButton.setDisable(true);

            // Do some validation (using the Java 8 lambda syntax).
            name.textProperty().addListener((observable, oldValue, newValue) -> {
                okayButton.setDisable(newValue.trim().isEmpty());
            });

            dialog.getDialogPane().setContent(grid);

            // Request focus on the name field by default.
            Platform.runLater(() -> name.requestFocus());

            // Convert the result to a Task when the okay button is clicked.
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == loginButtonType) {
                    return new Task(name.getText(), desc.getText(), dueDate.getValue());
                }
                dialog.close();
                return null;
            });

            Optional<Task> result = dialog.showAndWait();

            if (result.isPresent()) {
                Task task = result.get();
                System.out.println("Task name: " + task.getName() + " desc: " + task.getDescription() + " due date: " + task.getDate().toString());

                TaskViewController taskvc = new TaskViewController(task);
                //Save or something or another
                tasks.add(taskvc);

				this.getChildren().add(taskvc);
				this.cat.addTask(task);
				System.out.println(this.cat.getTasks());
// DRAG-AND-DROP WIP
//                DataFormat taskDataFormat = new DataFormat("Task");
//                taskvc.setOnDragDetected(event -> {
//                    System.out.println("setOnDragDetected");
//                    Dragboard dragboard = taskvc.startDragAndDrop(TransferMode.MOVE);
//                    ClipboardContent content = new ClipboardContent();
//                    TaskViewController taskToTransfer = taskvc;
//                    content.put(taskDataFormat, taskToTransfer);
//                    dragboard.setContent(content);
//                    this.getChildren().remove(taskvc);
//                });
//
//                this.setOnDragDone(event -> System.out.println("setOnDragDone"));
//                this.setOnDragEntered(event -> {
//                    System.out.println("setOnDragEntered");
//                    taskvc.setBlendMode(BlendMode.DIFFERENCE);
//                });
//                this.setOnDragExited(event -> {
//                    System.out.println("setOnDragExited");
//                    taskvc.setBlendMode(null);
//                });
//                this.setOnDragOver(event -> {
//                    System.out.println("setOnDragOver");
//                    event.acceptTransferModes(TransferMode.MOVE);
//                });
//                this.setOnDragDropped(event -> {
//                    System.out.println("setOnDragDropped");
//                    TaskViewController taskToConsume = (TaskViewController) event.getDragboard().getContent(taskDataFormat);
//                    this.getChildren().add(taskToConsume);
//                });
            }
        });

        //TODO: erase this before turning it in
        //as of rn im repeating that chunk of event handling cuz im dumb and bad at translating b/w lambda and anon inner classes

        //this.setMinSize(200, 100);
        this.setMaxHeight(100);
        this.getChildren().add(toolbar);
        //TODO: change taskvc to nodes (panes?) so i can use this
        //this.getChildren().addAll(tasks);
    }

	public Category getCategory() {
		return this.cat;
	}
	
	public void setAllTasks(ArrayList<Task> tasks) {
		for (Task t:cat.getTasks()) {
			TaskViewController tv = new TaskViewController(t);
			this.tasks.add(tv);
		}
    public ArrayList<TaskViewController> getTasks() {
        return tasks;
    }
	}

}
