package views;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import models.Category;
import models.Task;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class CategoryView extends VBox {
	private ArrayList<TaskViewController> tasks;
	private Category cat;

    private Text quickAddText;
    private StackPane quickAddPane;
    private TextField quickAddField;

	public CategoryView(Category cat) {
		super();
		this.setStyle("-fx-padding: 10;\n" +
				"-fx-background-color: rgba(169, 169, 169, 0.5);\n" +
				"-fx-background-radius: 5;\n");

		tasks = new ArrayList<>();
		this.cat = cat;
		this.tasks = new ArrayList<>();
		this.setAllTasks(cat.getTasks());
		
		this.setSpacing(10);
		Label nameLabel = new Label(cat.getName());
		HBox buffer = new HBox();
		HBox.setHgrow(buffer, Priority.ALWAYS);

		// Category's move buttons
		Image leftArrowImage = new Image(getClass().getResourceAsStream("/arrow-left.gif"));
		Image rightArrowImage = new Image(getClass().getResourceAsStream("/arrow-right.gif"));
		ImageView leftArrow = new ImageView(leftArrowImage);
		leftArrow.setFitHeight(15);
		leftArrow.setFitWidth(15);
		ImageView rightArrow  = new ImageView(rightArrowImage);
		rightArrow.setFitHeight(15);
		rightArrow.setFitWidth(15);
		Button moveLeft = new Button(null, leftArrow);
		moveLeft.setBackground(null);
		moveLeft.setTooltip(new Tooltip("Move category to the left"));
		Button moveRight = new Button(null, rightArrow);
		moveRight.setBackground(null);
		moveRight.setTooltip(new Tooltip("Move category to the right"));


        moveLeft.setOnMouseEntered(event -> {
            moveLeft.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, new CornerRadii(5), null)));
            moveLeft.setOnAction(event1 -> moveCategoryLeft());

            moveLeft.setOnMouseExited(event1 -> moveLeft.setBackground(null));
        });
        moveRight.setOnMouseEntered(event -> {

            moveRight.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, new CornerRadii(5), null)));
            moveRight.setOnAction(event1 -> moveCategoryRight());
            moveRight.setOnMouseExited(event1 -> moveRight.setBackground(null));
        });

		// Drop-down menu for creating task, renaming category, deleting category
        MenuItem newTaskItem = new MenuItem("Create new task");
        MenuItem renameCategoryItem = new MenuItem("Rename category");
        MenuItem deleteCategoryItem = new MenuItem("Delete category");
        Image ellipsisImage = new Image(getClass().getResourceAsStream("/dotdotdot.png"));
        ImageView ellipsis = new ImageView(ellipsisImage);
        ellipsis.setFitHeight(25);
        ellipsis.setFitWidth(25);
        MenuButton menuButton = new MenuButton(null, ellipsis, newTaskItem, renameCategoryItem, deleteCategoryItem);
        
       // String menuStyle = this.getStyle();
        menuButton.getStylesheets().add("stylesheet.css");

		ToolBar toolbar = new ToolBar(moveLeft, nameLabel, buffer, menuButton, moveRight);
		toolbar.setStyle("-fx-background-radius: 5;\n");
		TextField nameInput = new TextField(cat.getName());

		renameCategoryItem.setOnAction(event -> {
			toolbar.getItems().removeAll(moveLeft, nameLabel, buffer, menuButton, moveRight);
			toolbar.getItems().add(nameInput);

			nameInput.setOnAction(e2 -> {
				cat.setName(nameInput.getText());
				toolbar.getItems().remove(nameInput);
				nameLabel.setText(cat.getName());
				toolbar.getItems().addAll(moveLeft, nameLabel, buffer, menuButton, moveRight);
			});
		});

		nameLabel.setOnMouseClicked(e -> {
            toolbar.getItems().removeAll(moveLeft, nameLabel, buffer, menuButton, moveRight);
            toolbar.getItems().add(nameInput);

            nameInput.setOnAction(e2 -> {
                cat.setName(nameInput.getText());
                toolbar.getItems().remove(nameInput);
                nameLabel.setText(cat.getName());
                toolbar.getItems().addAll(moveLeft, nameLabel, buffer, menuButton, moveRight);
            });
		});

		deleteCategoryItem.setOnAction(event -> {
			deleteCategory();
		});

		newTaskItem.setOnAction(e -> {
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
				Collections.sort(tasks, new TaskViewComparator());
				this.getChildren().add(tasks.indexOf(taskvc) + 1, taskvc);

				this.cat.addTask(task);
				
			}
		});

		
		this.setMaxHeight(100);
		this.getChildren().add(toolbar);
		for (TaskViewController tv:tasks) {
			this.getChildren().add(tasks.indexOf(tv) + 1, tv);
		}

        quickAddText = new Text("Quick-add a task...");
        quickAddText.setWrappingWidth(150);
        quickAddPane = new StackPane(quickAddText);
        quickAddPane.setPadding(new Insets(10));
		quickAddPane.setAlignment(Pos.CENTER_LEFT);
		this.getChildren().add(quickAddPane);

		quickAddPane.setOnMouseEntered(event -> {
			quickAddPane.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, new CornerRadii(5), null)));
			quickAddPane.setOnMouseExited(event1 -> quickAddPane.setBackground(null));
		});

        quickAddField = new TextField();
        quickAddPane.setOnMouseClicked(event -> {
			this.getChildren().remove(quickAddPane);
			this.getChildren().add(quickAddField);
			quickAddField.requestFocus();
		});
		quickAddField.setOnAction(event -> {
			this.getChildren().remove(quickAddField);
			Task quickAddTask = new Task(quickAddField.getText(), null, null);
			TaskViewController quickAddTaskVC = new TaskViewController(quickAddTask);
			tasks.add(quickAddTaskVC);
			cat.addTask(quickAddTaskVC.getTask());
			this.getChildren().addAll(quickAddTaskVC, quickAddPane);
		});
	}

	public Category getCategory() {
		return this.cat;
	}

	public void setAllTasks(ArrayList<Task> tasks) {
		for (Task t:tasks) {
			TaskViewController tv = new TaskViewController(t);
			tv.setTaskColor();
			this.tasks.add(tv);
		}
	}

	private void deleteCategory() {
        ProjectView parent = (ProjectView) this.getParent();
        parent.getCategories().remove(this);
        parent.getProject().getCategories().remove(this.getCategory());
        parent.getChildren().remove(this);
    }

    private void moveCategoryLeft() {
        ProjectView parent = (ProjectView) this.getParent();
        int indexOfThis = parent.getChildren().indexOf(this);
        if (indexOfThis != 0) {
            int indexOfDestination = indexOfThis - 1;
            CategoryView temp = (CategoryView)parent.getChildren().get(indexOfDestination);
            parent.getChildren().set(indexOfDestination, new CategoryView(new Category("dummy")));
            parent.getChildren().set(indexOfThis, temp);
            parent.getChildren().set(indexOfDestination, this);
        }
    }

    private void moveCategoryRight() {
        ProjectView parent = (ProjectView) this.getParent();
        int indexOfThis = parent.getChildren().indexOf(this);
        int indexOfDestination = indexOfThis + 1;
        while (indexOfThis != parent.getChildren().size() - 1) {
            if (parent.getChildren().get(indexOfDestination) instanceof CategoryView) {
                CategoryView temp = (CategoryView) parent.getChildren().get(indexOfDestination);
                parent.getChildren().set(indexOfDestination, new CategoryView(new Category("dummy")));
                parent.getChildren().set(indexOfThis, temp);
                parent.getChildren().set(indexOfDestination, this);
                break;
            }
            else {
                break;
            }
        }
    }

    public void addTask(TaskViewController taskToAdd) {
		tasks.add(taskToAdd);
		cat.getTasks().add(taskToAdd.getTask());
		this.getChildren().remove(quickAddPane);
		this.getChildren().addAll(taskToAdd, quickAddPane);
	}

	ArrayList<TaskViewController> getTasks() {
		return tasks;
	}
}
