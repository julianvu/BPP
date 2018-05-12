package views;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Task;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class TaskViewController extends GridPane implements Serializable {

    private Task task;
    private Text taskName, taskDesc, taskDueDate;
    private static final String LOGIN_IMG_URL = "/login.png";
    static private final int BUTTON_SIZE = 12;
    private Scene scene = new Scene(this, 300, 100);
    private Color taskColor = new Color(0, 0, 0, 0);
    private BackgroundFill taskBackgroundFill = new BackgroundFill(taskColor, null, null);
    private Background taskBackground = new Background(taskBackgroundFill);

    public TaskViewController(Task task) {
        this.task = task;
        taskName = new Text(this.task.getName());
        taskName.setTextAlignment(TextAlignment.CENTER);
        taskDesc = new Text("Description: \n" + this.task.getDescription());
        taskDueDate = new Text("Due date: " + getDate(this.task));

        // Wrap Text in StackPanes
        StackPane namePane = wrapText(taskName);
        namePane.setPadding(new Insets(2));
        StackPane descPane = wrapText(taskDesc);
        StackPane datePane = wrapText(taskDueDate);

        String style = "-fx-border-radius: 5;\n" +
                "-fx-border-style: solid;\n" +
                "-fx-border-color: darkgrey;\n" +
                "-fx-padding: 10 10 10 0;\n";
        this.setStyle(style);
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(5), null)));
        this.setAlignment(Pos.TOP_LEFT);
        this.setHgap(10);
        this.setVgap(0);
        this.setPadding(new Insets(0));

        this.add(namePane, 1, 1);
        this.add(descPane, 1, 2);
        this.add(datePane, 1, 3);

        // MenuButton
        MenuItem deleteTaskItem = new MenuItem("Delete");
        MenuItem moveTaskItem = new MenuItem("Move task");
        Image ellipsisImage = new Image(getClass().getResourceAsStream("/dotdotdot.png"));
        ImageView ellipsis = new ImageView(ellipsisImage);
        ellipsis.setFitWidth(25);
        ellipsis.setFitHeight(25);
        MenuButton menuButton = new MenuButton(null, ellipsis, moveTaskItem, deleteTaskItem);
        menuButton.getStylesheets().add("stylesheet.css");

        this.add(menuButton, 2, 1);

        //delete button
//        Button deleteButt = new Button("X");
//        deleteButt.setTooltip(new Tooltip("Delete this task"));
//        this.add(deleteButt, 2, 1);

        TextField nameField = new TextField(taskName.getText());
        ColorPicker colorPicker = new ColorPicker(Color.web("ff000000"));
        System.out.println(colorPicker.getStyleClass());
        System.out.println(colorPicker.getStyleClass().add("button"));
        colorPicker.setStyle(".combo-box .arrow -fx-background-color: transparent;");
        colorPicker.setPrefWidth(30);

        namePane.setOnMouseClicked(event -> {

            this.getChildren().remove(namePane);
            this.add(nameField, 1, 1);
            this.add(colorPicker, 2, 1);

            nameField.setOnAction(event1 -> {
                String newName = nameField.getText();
                this.task.setName(newName);
                taskName.setText(newName);
                taskColor = colorPicker.getValue();
                namePane.setBackground(new Background(new BackgroundFill(taskColor, new CornerRadii(25), null)));
                this.getChildren().removeAll(nameField, colorPicker);
                this.add(namePane, 1, 1); //}
            });
        });

        namePane.setOnMouseEntered(event -> {
            namePane.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, new CornerRadii(25), null)));

            namePane.setOnMouseExited(event1 -> {
                namePane.setBackground(new Background(new BackgroundFill(taskColor, new CornerRadii(25), null)));
            });
        });

        TextArea descField = new TextArea(task.getDescription());
        descPane.setOnMouseClicked(event -> {
            descField.setMaxWidth(this.getWidth() - 20);
            descField.setMinHeight(descPane.getHeight());
            this.getChildren().remove(descPane);
            this.add(descField, 1, 2);

            descField.setOnKeyPressed(event1 -> {
                if (event1.getCode() == KeyCode.ENTER) {
                    String newDesc = descField.getText();
                    this.task.setDescription(newDesc);
                    taskDesc.setText("Description: \n" + newDesc);
                    this.getChildren().remove(descField);
                    this.add(descPane, 1, 2);
                }
            });
        });

        descPane.setOnMouseEntered(event -> {
            descPane.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, new CornerRadii(5), null)));

            descPane.setOnMouseExited(event1 -> descPane.setBackground(null));
        });

        DatePicker dp = new DatePicker(LocalDate.now());
        datePane.setOnMouseClicked(event -> {
            this.getChildren().remove(datePane);
            this.add(dp, 1, 3);

            dp.setOnAction(event1 -> {
                this.task.setDate(dp.getValue());
                taskDueDate.setText("Due date: " + getDate(this.task));
                CategoryView cv = (CategoryView) this.getParent();
                Collections.sort(cv.getTasks(), new TaskViewComparator());
                this.getChildren().remove(dp);
                this.add(datePane, 1, 3);
                cv.getChildren().remove(1, cv.getChildren().size());
                cv.getChildren().addAll(cv.getTasks());
            });
        });

        datePane.setOnMouseEntered(event -> {
            datePane.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, new CornerRadii(5), null)));

            datePane.setOnMouseExited(event1 -> datePane.setBackground(null));
        });

        deleteTaskItem.setOnAction(e -> deleteTask());

        moveTaskItem.setOnAction(event -> {
            ProjectView grandparent = (ProjectView)this.getParent().getParent();
            ArrayList<VBox> categories = grandparent.getCategories();

            final Stage dialog = new Stage();
            dialog.setTitle("Choose a category");
            dialog.initModality(Modality.APPLICATION_MODAL);

            ListView listView = new ListView();
            for (VBox v : grandparent.getCategories()) {
                if (v instanceof CategoryView) {
                    listView.getItems().add(((CategoryView)v).getCategory().getName());
                }
            }

            VBox dialogVbox = new VBox(listView);
            Scene dialogScene = new Scene(dialogVbox, 300, 200);
            dialog.setScene(dialogScene);
            dialog.show();

            listView.setOnMouseClicked(event1 -> {
                String selected = (String)listView.getSelectionModel().getSelectedItem();
                int indexOfCategory = grandparent.findCategoryView(selected);
                CategoryView selectedCategory = (CategoryView)grandparent.getCategories().get(indexOfCategory);
                if (selected != null) {
                    moveTask(selectedCategory);
                    dialog.close();
                }
            });
        });
    }

    public Task getTask() {
        return task;
    }

    public Text getTaskName() {
        return taskName;
    }

    public Text getTaskDesc() {
        return taskDesc;
    }

    public Text getTaskDueDate() {
        return taskDueDate;
    }

    public Color getTaskColor() {
        return taskColor;
    }

    private StackPane wrapText(Text text){
        text.setWrappingWidth(150);
        StackPane toReturn = new StackPane(text);
        toReturn.setPadding(new Insets(10));
        toReturn.setAlignment(Pos.CENTER_LEFT);
        return toReturn;
    }

    private void deleteTask() {
        CategoryView parent = (CategoryView)this.getParent();
        parent.getTasks().remove(this);
        parent.getCategory().getTasks().remove(this.getTask());
        parent.getChildren().remove(this);
    }

    private void moveTask(CategoryView otherCategory) {
        deleteTask();
        otherCategory.getTasks().add(this);
        otherCategory.getCategory().getTasks().add(this.getTask());
        otherCategory.getChildren().add(this);
    }

    private String getDate(Task task) {
        return task.getDate().getMonthValue() + "/" + task.getDate().getDayOfMonth() + "/" + task.getDate().getYear();
    }
}
