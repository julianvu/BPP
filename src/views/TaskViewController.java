package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import models.Task;

import java.io.Serializable;
import java.time.LocalDate;

    public class TaskViewController extends GridPane implements Serializable {

    private Task task;
    private Text taskName, taskDesc, taskDueDate;
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

        TextField nameField = new TextField(taskName.getText());
        ColorPicker colorPicker = new ColorPicker(Color.web("ff000000"));
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
                this.getChildren().remove(dp);
                this.add(datePane, 1, 3);
            });
        });

        datePane.setOnMouseEntered(event -> {
            datePane.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, new CornerRadii(5), null)));

            datePane.setOnMouseExited(event1 -> datePane.setBackground(null));
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

        private StackPane wrapText(Text text) {
        text.setWrappingWidth(150);
        StackPane toReturn = new StackPane(text);
        toReturn.setPadding(new Insets(10));
        toReturn.setAlignment(Pos.CENTER_LEFT);
        return toReturn;
    }

    private String getDate(Task task) {
        return task.getDate().getMonthValue() + "/" + task.getDate().getDayOfMonth() + "/" + task.getDate().getYear();
    }
}
